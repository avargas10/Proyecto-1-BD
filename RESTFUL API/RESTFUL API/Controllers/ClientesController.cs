using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Data.SqlClient;
using Newtonsoft.Json;
using System.Web.Script.Serialization;
using RESTFUL_API.Models;
using System.Net.Mail;

namespace RESTFUL_API.Controllers
{
    public class ClientesController : ApiController
    {
        JSONSerializer serial = new JSONSerializer();
        string DatabaseConnectionString = System.Configuration.ConfigurationManager.ConnectionStrings["GasStationPharmacyDB"].ConnectionString;

        [HttpGet]
        public IEnumerable<Dictionary<string, object>> getAll()
        {

            using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
            {
                SqlCommand cmd = new SqlCommand("SELECT Cedula,Nombre,pApellido,sApellido,Password,Username,Email,Nacimiento,Penalizacion,Direccion FROM CLIENTE", conn);
                cmd.Connection = conn;
                conn.Open();
                using (var reader = cmd.ExecuteReader())
                {
                    var r = serial.Serialize(reader);
                    return r;
                }

            }
        }

        [HttpGet]
        public HttpResponseMessage getUserById(int id)
        {
            using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
            {
                SqlCommand cmd = new SqlCommand("SELECT Cedula, Nombre, pApellido, sApellido, Password, Username, Email, Nacimiento, Penalizacion, Direccion FROM CLIENTE WHERE Cedula=@id", conn);
                cmd.Parameters.AddWithValue("@id",id);
                cmd.Connection = conn;
                conn.Open();
                using (var reader = cmd.ExecuteReader())
                {
                    if (reader.Read())
                    {
                        reader.Close();
                        var r = serial.singleserialize(cmd.ExecuteReader());
                        return Request.CreateResponse(HttpStatusCode.OK, r);
                    }
                    else {
                        return Request.CreateErrorResponse(HttpStatusCode.NotFound, "Usuario con Cedula: " + id + " no encontrado.");
                    }
                }
            }
        }
        [HttpGet]
        public HttpResponseMessage getUserByUsername([FromUri] string username)
        {
            using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
            {
                SqlCommand cmd = new SqlCommand("SELECT Cedula, Nombre, pApellido, sApellido, Password, Username, Email, Nacimiento, Penalizacion, Direccion, Estado, Telefono FROM CLIENTE WHERE Username=@user", conn);
                cmd.Parameters.AddWithValue("@user", username);
                cmd.Connection = conn;
                conn.Open();
                using (var reader = cmd.ExecuteReader())
                {
                    if (reader.Read())
                    {
                        reader.Close();
                        var r = serial.singleserialize(cmd.ExecuteReader());
                        return Request.CreateResponse(HttpStatusCode.OK, r);
                    }
                    else
                    {

                        return Request.CreateErrorResponse(HttpStatusCode.NotFound, "Usuario con Username: " + username + " no encontrado.");
                    }
                    
                }
            }
        }

        [HttpPost]
        public bool clientLogin([FromUri]string username, [FromUri]string pass)
        {
            using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
            {
                SqlCommand cmd = new SqlCommand("SELECT * FROM CLIENTE WHERE Username=@user AND Password=@pass AND Estado=1", conn);
                cmd.Parameters.AddWithValue("@user", username);
                cmd.Parameters.AddWithValue("@pass", pass);
                cmd.Connection = conn;
                conn.Open();
                using (var reader = cmd.ExecuteReader())
                {
                    if (reader.Read())
                    {
                        reader.Close();
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }

            }
        }

        [HttpPost]
        public HttpResponseMessage regCliente([FromBody] clienteModel cliente)
        {
            try
            {
                using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
                {
                    object user, cedula, estado;
                    SqlCommand cmd1;
                    
                    cmd1 =new SqlCommand("SELECT Estado, Cedula, Username FROM CLIENTE WHERE Cedula=@id OR Username=@user");
                    cmd1.Parameters.AddWithValue("@id", cliente.Cedula);
                    cmd1.Parameters.AddWithValue("@user", cliente.Username);
                    cmd1.Connection = conn;
                    conn.Open();
                    var reader = cmd1.ExecuteReader();
                    if (reader.Read())
                    {
                        reader.Close();
                        var data = serial.singleserialize(cmd1.ExecuteReader());
                        data.TryGetValue("Estado", out estado);
                       data.TryGetValue("Cedula", out cedula);
                        data.TryGetValue("Username", out user);
                        conn.Close();
                        if ((Convert.ToString(user).Equals(cliente.Username)) &&(Convert.ToInt32(cedula)==cliente.Cedula) &&(Convert.ToInt32(estado)==1))
                        {
                            return Request.CreateResponse(HttpStatusCode.Conflict, "This user already exist!");
                        }
                        else if((Convert.ToString(user).Equals(cliente.Username)) && (Convert.ToInt32(cedula) == cliente.Cedula) && (Convert.ToInt32(estado) == 0))
                        {
                            cmd1= new SqlCommand("UPDATE  CLIENTE SET  Nombre=@nombre, pApellido=@papellido, sApellido=@sapellido, Password=@password, Email=@email, Nacimiento=@nacimiento, Telefono=@telefono, Estado=1 WHERE Cedula=@id", conn);
                            cmd1.Parameters.AddWithValue("@id", cliente.Cedula);
                            cmd1.Parameters.AddWithValue("@nombre", cliente.Nombre);
                            cmd1.Parameters.AddWithValue("@papellido", cliente.pApellido);
                            cmd1.Parameters.AddWithValue("@sapellido", cliente.sApellido);
                            cmd1.Parameters.AddWithValue("@password", cliente.Password);
                            cmd1.Parameters.AddWithValue("@email", cliente.Email);
                            cmd1.Parameters.AddWithValue("@nacimiento", cliente.Nacimiento);
                            cmd1.Parameters.AddWithValue("@telefono", cliente.Telefono);
                            cmd1.Connection = conn;
                            conn.Open();
                            cmd1.ExecuteReader();
                            var message = Request.CreateResponse(HttpStatusCode.Created, cliente);
                            conn.Close();
                            return message;
                        }
                        else if(Convert.ToInt32(cedula)==cliente.Cedula)
                        {
                            return Request.CreateResponse(HttpStatusCode.BadRequest, "Identification already exist!");
                        }else if (Convert.ToString(user).Equals(cliente.Username))
                        {
                            return Request.CreateResponse(HttpStatusCode.BadGateway, "Username already exist!");
                        }
                        
                        return null;
                    }
                    else
                    {
                        conn.Close();
                        SqlCommand cmd = new SqlCommand("INSERT INTO CLIENTE(Cedula, Nombre, pApellido, sApellido, Password, Username, Email, Nacimiento, Penalizacion, Direccion, Estado, Telefono) VALUES (@cedula,@nombre,@papellido,@sapellido,@password,@username,@email,@nacimiento,@penalizacion,@direccion,0, @telefono)", conn);
                        cmd.Parameters.AddWithValue("@cedula", cliente.Cedula);
                        cmd.Parameters.AddWithValue("@nombre", cliente.Nombre);
                        cmd.Parameters.AddWithValue("@papellido", cliente.pApellido);
                        cmd.Parameters.AddWithValue("@sapellido", cliente.sApellido);
                        cmd.Parameters.AddWithValue("@password", cliente.Password);
                        cmd.Parameters.AddWithValue("@username", cliente.Username);
                        cmd.Parameters.AddWithValue("@email", cliente.Email);
                        cmd.Parameters.AddWithValue("@nacimiento", cliente.Nacimiento);
                        cmd.Parameters.AddWithValue("@penalizacion", cliente.Penalizacion);
                        cmd.Parameters.AddWithValue("@direccion", cliente.Direccion);
                        cmd.Parameters.AddWithValue("@telefono", cliente.Telefono);
                        cmd.Connection = conn;
                        conn.Open();
                        cmd.ExecuteReader();
                        var message = Request.CreateResponse(HttpStatusCode.Created, cliente);
                        return message;
                    }
                }
            }
            catch (Exception ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ex);
            }
        }

        [HttpPost]
        public HttpResponseMessage UpdateEstado([FromUri] string cedEstado)
        {
            try
            {
                using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
                {
                    SqlCommand cmd = new SqlCommand("UPDATE  CLIENTE SET  Estado=1 WHERE Cedula=@id", conn);
                    cmd.Parameters.AddWithValue("@id", cedEstado);
                    cmd.Connection = conn;
                    conn.Open();
                    cmd.ExecuteReader();
                    var message = Request.CreateResponse(HttpStatusCode.Created, "true");
                    return message;
                }
            }
            catch (Exception ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, "false");
            }
        }

        [HttpPut]
        public HttpResponseMessage updateCliente([FromBody] clienteModel cliente)
        {
            try
            {
                using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
                {
                    SqlCommand cmd = new SqlCommand("UPDATE  CLIENTE SET  Nombre=@nombre, pApellido=@papellido, sApellido=@sapellido, Password=@password, Username=@username, Email=@email, Nacimiento=@nacimiento, Telefono=@telefono WHERE Cedula=@id", conn);
                    cmd.Parameters.AddWithValue("@id", cliente.Cedula);
                    cmd.Parameters.AddWithValue("@nombre", cliente.Nombre);
                    cmd.Parameters.AddWithValue("@papellido", cliente.pApellido);
                    cmd.Parameters.AddWithValue("@sapellido", cliente.sApellido);
                    cmd.Parameters.AddWithValue("@password", cliente.Password);
                    cmd.Parameters.AddWithValue("@username", cliente.Username);
                    cmd.Parameters.AddWithValue("@email", cliente.Email);
                    cmd.Parameters.AddWithValue("@nacimiento", cliente.Nacimiento);
                    cmd.Parameters.AddWithValue("@telefono", cliente.Telefono);
                    cmd.Connection = conn;
                    conn.Open();
                    cmd.ExecuteReader();
                    var message = Request.CreateResponse(HttpStatusCode.Created, cliente);
                    return message;
                }
            }
            catch (Exception ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ex);
            }
        }


        [HttpDelete]
        public HttpResponseMessage deleteCliente([FromBody] clienteModel del)
        {
            try
            {
                using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
                {
                    SqlCommand cmd = new SqlCommand("UPDATE  CLIENTE SET  Estado=0 WHERE Cedula=@id", conn);
                    cmd.Parameters.AddWithValue("@id", del.Cedula);
                    cmd.Connection = conn;
                    conn.Open();
                    cmd.ExecuteReader();
                    var message = Request.CreateResponse(HttpStatusCode.Created, del);
                    return message;
                }
            }
            catch (Exception ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ex);
            }
        }

        [HttpPost]
        public bool senEmail([FromUri]string username)
        {
            object pass, email;
            using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
            {
                SqlCommand cmd = new SqlCommand("SELECT Password, Email FROM CLIENTE WHERE Username=@user AND Estado=1", conn);
                cmd.Parameters.AddWithValue("@user", username);
                cmd.Connection = conn;
                conn.Open();
                using (var reader = cmd.ExecuteReader())
                {
                    if (reader.Read())
                    {
                        reader.Close();
                        var reader1 = cmd.ExecuteReader();
                        serial.singleserialize(reader1).TryGetValue("Password", out pass);
                        reader1.Close();
                        serial.singleserialize(cmd.ExecuteReader()).TryGetValue("Email", out email);
                         try
                         {
                             MailMessage mail = new MailMessage();
                             SmtpClient SmtpServer = new SmtpClient("smtp.gmail.com");

                             mail.From = new MailAddress("gsppassrecovery@gmail.com");
                             mail.To.Add(email.ToString());
                             mail.Subject = "Your Password for GSP";
                             mail.Body = "This is your password for all GSP platforms, Mobile App and Web Page. Password: "+pass.ToString();

                             SmtpServer.Port = 587;
                             SmtpServer.Credentials = new System.Net.NetworkCredential("gsppassrecovery@gmail.com", "bases2017");
                             SmtpServer.EnableSsl = true;

                             SmtpServer.Send(mail);
                            return true;
                         }
                         catch (Exception ex)
                         {
                            return false;
                         }
                    }
                    else
                    {
                        return false;
                    }
                }

            }
        }

        [HttpPost]
        public bool sendActivationEmail([FromUri]int cedula)
        {
            object pass, email;
            using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
            {
                SqlCommand cmd = new SqlCommand("SELECT Password, Email FROM CLIENTE WHERE Cedula=@cedula", conn);
                cmd.Parameters.AddWithValue("@cedula", cedula);
                cmd.Connection = conn;
                conn.Open();
                using (var reader = cmd.ExecuteReader())
                {
                    if (reader.Read())
                    {
                        reader.Close();
                        var reader1 = cmd.ExecuteReader();
                        serial.singleserialize(reader1).TryGetValue("Password", out pass);
                        reader1.Close();
                        serial.singleserialize(cmd.ExecuteReader()).TryGetValue("Email", out email);
                        try
                        {
                            MailMessage mail = new MailMessage();
                            SmtpClient SmtpServer = new SmtpClient("smtp.gmail.com");

                            mail.From = new MailAddress("gspcontrollerconsole@gmail.com");
                            mail.To.Add(email.ToString());
                            mail.Subject = "Your Password for GSP";
                            mail.Body = "Follow this link to confirm your gsp account: " + "http://localhost:21039/WebPage/Front-End/Views/#!/accountVer";
                            SmtpServer.Port = 587;
                            SmtpServer.Credentials = new System.Net.NetworkCredential("gspcontrollerconsole@gmail.com", "gspadmin2017");
                            SmtpServer.EnableSsl = true;

                            SmtpServer.Send(mail);
                            return true;
                        }
                        catch (Exception ex)
                        {
                            return false;
                        }
                    }
                    else
                    {
                        return false;
                    }
                }

            }
        }

    }
}

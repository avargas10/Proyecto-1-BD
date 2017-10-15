using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using RESTFUL_API.Models;
using System.Data.SqlClient;

namespace RESTFUL_API.Controllers
{
    public class EmpleadosController : ApiController
    {
        JSONSerializer serial = new JSONSerializer();
        string DatabaseConnectionString = System.Configuration.ConfigurationManager.ConnectionStrings["GasStationPharmacyDB"].ConnectionString;

        [HttpGet]
        public IEnumerable<Dictionary<string, object>> getAll()
        {

            using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
            {
                SqlCommand cmd = new SqlCommand("SELECT idEmpleado,Nombre,pApellido,sApellido,Password,Username,Email,Nacimiento,Direccion FROM EMPLEADO", conn);
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
                SqlCommand cmd = new SqlCommand("SELECT idEmpleado,Nombre,pApellido,sApellido,Password,Username,Email,Nacimiento,Direccion FROM EMPLEADO WHERE idEmpleado=@id", conn);
                cmd.Parameters.AddWithValue("@id", id);
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
                SqlCommand cmd = new SqlCommand("SELECT idEmpleado,Nombre,pApellido,sApellido,Password,Username,Email,Nacimiento,Direccion FROM EMPLEADO WHERE Username=@user", conn);
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
        public bool EmpleadoLogin([FromUri]string username, [FromUri]string pass)
        {
            using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
            {
                SqlCommand cmd = new SqlCommand("SELECT * FROM EMPLEADO WHERE Username=@user AND Password=@pass", conn);
                cmd.Parameters.AddWithValue("@user", username);
                cmd.Parameters.AddWithValue("@pass", pass);
                cmd.Connection = conn;
                conn.Open();
                using (var reader = cmd.ExecuteReader())
                {
                    if (reader.Read())
                    {
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
        public HttpResponseMessage regEmpleado([FromBody] empleadosModel empleado)
        {
            try
            {
                using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
                {
                    object user, cedula, estado;
                    SqlCommand cmd1;

                    cmd1 = new SqlCommand("SELECT Estado, Cedula, Username FROM EMPLEADO WHERE idEmpleado=@id OR Username=@user");
                    cmd1.Parameters.AddWithValue("@id", empleado.idEmpleado);
                    cmd1.Parameters.AddWithValue("@user", empleado.Username);
                    cmd1.Connection = conn;
                    conn.Open();
                    var reader = cmd1.ExecuteReader();
                    if (reader.Read())
                    {
                        reader.Close();
                        var data = serial.singleserialize(cmd1.ExecuteReader());
                        data.TryGetValue("Estado", out estado);
                        data.TryGetValue("idEmpleado", out cedula);
                        data.TryGetValue("Username", out user);
                        conn.Close();
                        if ((Convert.ToString(user).Equals(empleado.Username)) && (Convert.ToInt32(cedula) == empleado.idEmpleado) && (Convert.ToInt32(estado) == 1))
                        {
                            return Request.CreateResponse(HttpStatusCode.Conflict, "This user already exist!");
                        }
                        else if ((Convert.ToString(user).Equals(empleado.Username)) && (Convert.ToInt32(cedula) == empleado.idEmpleado) && (Convert.ToInt32(estado) == 0))
                        {
                            cmd1 = new SqlCommand("UPDATE EMPLEADO SET  Nombre=@nombre, pApellido=@papellido, sApellido=@sapellido, Password=@password, Email=@email, Nacimiento=@nacimiento WHERE idEmpleado=@id", conn);
                            cmd1.Parameters.AddWithValue("@id", empleado.idEmpleado);
                            cmd1.Parameters.AddWithValue("@nombre", empleado.Nombre);
                            cmd1.Parameters.AddWithValue("@papellido", empleado.pApellido);
                            cmd1.Parameters.AddWithValue("@sapellido", empleado.sApellido);
                            cmd1.Parameters.AddWithValue("@password", empleado.Password);
                            cmd1.Parameters.AddWithValue("@email", empleado.Email);
                            cmd1.Parameters.AddWithValue("@nacimiento", empleado.Nacimiento);
                            cmd1.Connection = conn;
                            conn.Open();
                            cmd1.ExecuteReader();
                            var message = Request.CreateResponse(HttpStatusCode.Created, empleado);
                            conn.Close();
                            return message;
                        }
                        else if (Convert.ToInt32(cedula) == empleado.idEmpleado)
                        {
                            return Request.CreateResponse(HttpStatusCode.BadRequest, "Identification already exist!");
                        }
                        else if (Convert.ToString(user).Equals(empleado.Username))
                        {
                            return Request.CreateResponse(HttpStatusCode.BadGateway, "Username already exist!");
                        }

                        return null;
                    }
                    else
                    {
                        SqlCommand cmd = new SqlCommand("INSERT INTO EMPLEADO(idEmpleado, Nombre, pApellido, sApellido, Password, Username, Email, Nacimiento, Direccion ) VALUES (@cedula,@nombre,@papellido,@sapellido,@password,@username,@email,@nacimiento,@direccion)", conn);
                        cmd.Parameters.AddWithValue("@cedula", empleado.idEmpleado);
                        cmd.Parameters.AddWithValue("@nombre", empleado.Nombre);
                        cmd.Parameters.AddWithValue("@papellido", empleado.pApellido);
                        cmd.Parameters.AddWithValue("@sapellido", empleado.sApellido);
                        cmd.Parameters.AddWithValue("@password", empleado.Password);
                        cmd.Parameters.AddWithValue("@username", empleado.Username);
                        cmd.Parameters.AddWithValue("@email", empleado.Email);
                        cmd.Parameters.AddWithValue("@nacimiento", empleado.Nacimiento);
                        cmd.Parameters.AddWithValue("@direccion", empleado.Direccion);
                        cmd.Connection = conn;
                        conn.Open();
                        cmd.ExecuteReader();
                        var message = Request.CreateResponse(HttpStatusCode.Created, empleado);
                        return message;
                    }
                }
            }
            catch (Exception ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ex);
            }


        }

        [HttpDelete]
        public HttpResponseMessage deleteEmpleado([FromBody] empleadosModel del)
        {
            try
            {
                using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
                {
                    SqlCommand cmd = new SqlCommand("UPDATE  EMPLEADO SET  Estado=0 WHERE idEmpleado=@id", conn);
                    cmd.Parameters.AddWithValue("@id", del.idEmpleado);
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

    }
}

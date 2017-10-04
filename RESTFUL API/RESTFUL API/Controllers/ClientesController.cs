using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Data.SqlClient;
using Newtonsoft.Json;


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
                SqlCommand cmd = new SqlCommand("SELECT Cedula, Nombre, pApellido, sApellido, Password, Username, Email, Nacimiento, Penalizacion, Direccion FROM CLIENTE WHERE Cedula='@id'", conn);
                cmd.Parameters.AddWithValue("@id",id);
                cmd.Connection = conn;
                conn.Open();
                using (var reader = cmd.ExecuteReader())
                {
                    var r = serial.Serialize(reader);
                    if (reader.Read())
                    {
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
                SqlCommand cmd = new SqlCommand("SELECT Cedula, Nombre, pApellido, sApellido, Password, Username, Email, Nacimiento, Penalizacion, Direccion FROM CLIENTE WHERE Username=@user", conn);
                cmd.Parameters.AddWithValue("@user", username);
                cmd.Connection = conn;
                conn.Open();
                using (var reader = cmd.ExecuteReader())
                {
                    Console.Out.WriteLine(reader.Read() + "............................this is text.....");
                    var r = serial.Serialize(reader);
                    if (reader.Read())
                    {
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
                SqlCommand cmd = new SqlCommand("SELECT * FROM CLIENTE WHERE Username=@user AND Password=@pass", conn);
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
        public HttpResponseMessage regCliente([FromBody] String cliente)
        {
            try
            {
                using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
                {
                    SqlCommand cmd = new SqlCommand("INSERT INTO CLIENTE(Cedula, Nombre, pApellido, sApellido, Password, Username, Email, Nacimiento, Penalizacion, Direccion) VALUES (@cedula,@nombre,@papillido,@sapellido,@password,@username,@email,@nacimiento,@penalizacion,@direccion)", conn);
                    cmd.Parameters.AddWithValue("@cedula", JsonConvert.DeserializeObject("Cedula"));
                    cmd.Parameters.AddWithValue("@nombre", JsonConvert.DeserializeObject("Nombre"));
                    cmd.Parameters.AddWithValue("@papellido", JsonConvert.DeserializeObject("Papellido"));
                    cmd.Parameters.AddWithValue("@sapellido", JsonConvert.DeserializeObject("sApellido"));
                    cmd.Parameters.AddWithValue("@password", JsonConvert.DeserializeObject("Password"));
                    cmd.Parameters.AddWithValue("@username", JsonConvert.DeserializeObject("Username"));
                    cmd.Parameters.AddWithValue("@email", JsonConvert.DeserializeObject("Email"));
                    cmd.Parameters.AddWithValue("@nacimiento", JsonConvert.DeserializeObject("Nacimiento"));
                    cmd.Parameters.AddWithValue("@penalizacion", JsonConvert.DeserializeObject("Penalizacion"));
                    cmd.Parameters.AddWithValue("@direccion", JsonConvert.DeserializeObject("Direccion"));
                    cmd.Connection = conn;
                    conn.Open();
                    var message = Request.CreateResponse(HttpStatusCode.Created, cliente);
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

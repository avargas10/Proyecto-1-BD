using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Data.SqlClient;
using Newtonsoft.Json;
using System.Web.Script.Serialization;
using RESTFUL_API.Models;

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
                SqlCommand cmd = new SqlCommand("SELECT Cedula, Nombre, pApellido, sApellido, Password, Username, Email, Nacimiento, Penalizacion, Direccion FROM CLIENTE WHERE Username=@user", conn);
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
                    SqlCommand cmd = new SqlCommand("INSERT INTO CLIENTE(Cedula, Nombre, pApellido, sApellido, Password, Username, Email, Nacimiento, Penalizacion, Direccion, Estado) VALUES (@cedula,@nombre,@papellido,@sapellido,@password,@username,@email,@nacimiento,@penalizacion,@direccion,1)", conn);
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

    }
}

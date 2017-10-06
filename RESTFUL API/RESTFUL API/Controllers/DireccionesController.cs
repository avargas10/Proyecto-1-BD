using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Data.SqlClient;
using RESTFUL_API.Models;
using System.Collections.Generic;
using System;

namespace RESTFUL_API.Controllers
{
    public class DireccionesController : ApiController
    {
        JSONSerializer serial = new JSONSerializer();
        string DatabaseConnectionString = System.Configuration.ConfigurationManager.ConnectionStrings["GasStationPharmacyDB"].ConnectionString;

        [HttpGet]
        public IEnumerable<Dictionary<string, object>> getAll()
        {

            using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
            {
                SqlCommand cmd = new SqlCommand("SELECT idDireccion,Provincia,Canton,Distrito,Descripcion FROM DIRECCIONES", conn);
                cmd.Connection = conn;
                conn.Open();
                using (var reader = cmd.ExecuteReader())
                {
                    var r = serial.Serialize(reader);
                    conn.Close();
                    return r;
                }

            }
        }

        [HttpGet]
        public HttpResponseMessage getDirById(int id)
        {
            using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
            {
                SqlCommand cmd = new SqlCommand("SELECT idDireccion,Provincia,Canton,Distrito,Descripcion FROM DIRECCIONES WHERE idDireccion=@id", conn);
                cmd.Parameters.AddWithValue("@id", id);
                cmd.Connection = conn;
                conn.Open();
                using (var reader = cmd.ExecuteReader())
                {
                    if (reader.Read())
                    {
                        reader.Close();
                        var r = serial.singleserialize(cmd.ExecuteReader());
                        conn.Close();
                        return Request.CreateResponse(HttpStatusCode.OK, r);
                    }
                    else
                    {
                        conn.Close();
                        return Request.CreateErrorResponse(HttpStatusCode.NotFound, "La direccion con Id: " + id + " no encontrado.");
                    }
                }
            }
        }

       [HttpPost]
        public HttpResponseMessage regDireccion([FromBody] direccionesModel direccion)
        {

            try
            {
                using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
                {
                    SqlCommand cmd = new SqlCommand("INSERT INTO DIRECCIONES(Provincia, Canton, Distrito, Descripcion) OUTPUT INSERTED.idDireccion VALUES (@provincia,@canton,@distrito,@descripcion)", conn);
                    cmd.Parameters.AddWithValue("@provincia", direccion.Provincia);
                    cmd.Parameters.AddWithValue("@canton", direccion.Canton);
                    cmd.Parameters.AddWithValue("@distrito", direccion.Distrito);
                    cmd.Parameters.AddWithValue("@descripcion", direccion.Descripcion);
                    cmd.Connection = conn;
                    conn.Open();
                    var message = Request.CreateResponse(HttpStatusCode.Created, serial.singleserialize(cmd.ExecuteReader()));
                    conn.Close();
                    return message;
                }
            }
            catch (Exception ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ex);
            }
        }

        [HttpPut]
        public HttpResponseMessage updateDireccion([FromBody] direccionesModel direccion)
        {
            try
            {
                using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
                {
                    SqlCommand cmd = new SqlCommand("UPDATE  DIRECCIONES SET Provincia=@provincia, Canton=@canton, Distrito=@distrito, Descripcion=@descripcion WHERE idDireccion=@id", conn);
                    cmd.Parameters.AddWithValue("@id", direccion.idDireccion);
                    cmd.Parameters.AddWithValue("@provincia", direccion.Provincia);
                    cmd.Parameters.AddWithValue("@canton", direccion.Canton);
                    cmd.Parameters.AddWithValue("@distrito", direccion.Distrito);
                    cmd.Parameters.AddWithValue("@descripcion", direccion.Descripcion);
                    cmd.Connection = conn;
                    conn.Open();
                    cmd.ExecuteReader();
                    var message = Request.CreateResponse(HttpStatusCode.Created, direccion);
                    conn.Close();
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

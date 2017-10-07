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
    public class PadecimientosController : ApiController
    {
        JSONSerializer serial = new JSONSerializer();
        string DatabaseConnectionString = System.Configuration.ConfigurationManager.ConnectionStrings["GasStationPharmacyDB"].ConnectionString;

        [HttpGet]
        public IEnumerable<Dictionary<string, object>> getAll()
        {
            using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
            {
                SqlCommand cmd = new SqlCommand("SELECT idPadecimiento,idUsuario,Fecha,Nombre,Descripcion FROM PADECIMIENTOS", conn);
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

        [HttpPost]
        public HttpResponseMessage regPadecimiento([FromBody] padecimientosModel padecimiento)
        {
            try
            {
                using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
                {
                    SqlCommand cmd = new SqlCommand("INSERT INTO PADECIMIENTOS(idUsuario,Fecha,Nombre,Descripcion) VALUES (@cedula,@fecha,@nombre,@descripcion)", conn);
                    cmd.Parameters.AddWithValue("@cedula", padecimiento.idUsuario);
                    cmd.Parameters.AddWithValue("@fecha", padecimiento.Fecha);
                    cmd.Parameters.AddWithValue("@nombre", padecimiento.Nombre);
                    cmd.Parameters.AddWithValue("@descripcion", padecimiento.Descripcion);
                    conn.Open();
                    cmd.ExecuteReader();
                    var message = Request.CreateResponse(HttpStatusCode.Created, padecimiento);
                    return message;
                }
            }
            catch (Exception ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ex);
            }
        }

        [HttpPut]
        public HttpResponseMessage updatePadecimiento([FromBody] padecimientosModel padecimiento)
        {
            try
            {
                using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
                {
                    SqlCommand cmd = new SqlCommand("UPDATE  PADECIMIENTOS  SET idUsuario=@cedula, Fecha=@fecha, Nombre=@nombre, Descripcion=@descripcion WHERE idPadecimiento=@id", conn);
                    cmd.Parameters.AddWithValue("@id", padecimiento.idPadecimiento);
                    cmd.Parameters.AddWithValue("@cedula", padecimiento.idUsuario);
                    cmd.Parameters.AddWithValue("@fecha", padecimiento.Fecha);
                    cmd.Parameters.AddWithValue("@nombre", padecimiento.Nombre);
                    cmd.Parameters.AddWithValue("@descripcion", padecimiento.Descripcion);
                    conn.Open();
                    cmd.ExecuteReader();
                    var message = Request.CreateResponse(HttpStatusCode.Created, padecimiento);
                    return message;
                }
            }
            catch (Exception ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ex);
            }
        }

        [HttpGet]
        public IEnumerable<Dictionary<string, object>> getPadbyUserid(int id)
        {
            using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
            {
                SqlCommand cmd = new SqlCommand("SELECT idPadecimiento,idUsuario,Fecha,Nombre,Descripcion FROM PADECIMIENTOS" +
                    " WHERE idUsuario=@id", conn);
                cmd.Parameters.AddWithValue("@id", id);
                cmd.Connection = conn;
                conn.Open();
                using (var reader = cmd.ExecuteReader())
                {

                    reader.Close();
                    var r = serial.Serialize(cmd.ExecuteReader());
                    return r;

                }
            }
        }
        [HttpGet]
        public Dictionary<string, object> getPadbyPadid(int idPad)
        {
            using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
            {
                SqlCommand cmd = new SqlCommand("SELECT idPadecimiento,idUsuario,Fecha,Nombre,Descripcion FROM PADECIMIENTOS" +
                    " WHERE idPadecimiento=@id", conn);
                cmd.Parameters.AddWithValue("@id", idPad);
                cmd.Connection = conn;
                conn.Open();
                using (var reader = cmd.ExecuteReader())
                {

                    reader.Close();
                    var r = serial.singleserialize(cmd.ExecuteReader());
                    return r;

                }
            }
        }
    }
}

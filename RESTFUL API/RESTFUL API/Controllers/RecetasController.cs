using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using RESTFUL_API.Models;
using System.Data.SqlClient;
using System.Diagnostics;

namespace RESTFUL_API.Controllers
{
    public class RecetasController : ApiController
    {
        JSONSerializer serial = new JSONSerializer();
        string DatabaseConnectionString = System.Configuration.ConfigurationManager.ConnectionStrings["GasStationPharmacyDB"].ConnectionString;

        [HttpGet]
        public IEnumerable<Dictionary<string, object>> getAll()
        {
            using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
            {
                SqlCommand cmd = new SqlCommand("SELECT idReceta,idCliente,Imagen FROM RECETAS", conn);
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
        public HttpResponseMessage regReceta([FromBody] recetasModel receta)
        {
            try
            {
                using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
                {
                    SqlCommand cmd = new SqlCommand("INSERT INTO RECETAS(idCliente,Imagen,Estado, idDoctor) OUTPUT INSERTED.idReceta VALUES (@cliente,@imagen,@estado,@doctor)", conn);
                    cmd.Parameters.AddWithValue("@cliente", receta.idCliente);
                    cmd.Parameters.AddWithValue("@imagen", receta.Imagen);
                    cmd.Parameters.AddWithValue("@estado", 1);
                    cmd.Parameters.AddWithValue("@doctor", receta.idDoctor);
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

        [HttpGet]
        public IEnumerable<Dictionary<string, object>> getRecetabyidCliente([FromUri] int id)
        {
            using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
            {
                SqlCommand cmd = new SqlCommand("SELECT idReceta,idCliente,Imagen,Estado,idDoctor FROM RECETAS WHERE idCliente=@id AND Estado=1", conn);
                cmd.Parameters.AddWithValue("@id", id);
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
        public HttpResponseMessage getRecetasbyidReceta(int idReceta)
        {
            using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
            {
                try
                {
                    SqlCommand cmd = new SqlCommand("SELECT idReceta,idCliente,Imagen,Estado,idDoctor FROM RECETAS WHERE idReceta=@id", conn);
                    cmd.Parameters.AddWithValue("@id", idReceta);
                    cmd.Connection = conn;
                    conn.Open();
                    using (var reader = cmd.ExecuteReader())
                    {
                        var r = serial.singleserialize(reader);
                        var message = Request.CreateResponse(HttpStatusCode.Accepted, r);
                        conn.Close();
                        return message;
                    }
                }
                catch (Exception ex)
                {
                    return Request.CreateResponse(HttpStatusCode.BadRequest, ex);
                }

            }
        }

        [HttpPut]
        public HttpResponseMessage updatePedido(recetasModel receta)
        {
            try
            {
                using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
                {
                    SqlCommand cmd = new SqlCommand("UPDATE RECETAS SET Imagen=@imagen, idDoctor=@doctor WHERE idReceta=@id", conn);
                    cmd.Parameters.AddWithValue("@id", receta.idReceta);
                    cmd.Parameters.AddWithValue("@imagen", receta.Imagen);
                    cmd.Parameters.AddWithValue("@doctor", receta.idDoctor);
                    cmd.Connection = conn;
                    conn.Open();
                    cmd.ExecuteReader();
                    var message = Request.CreateResponse(HttpStatusCode.Created, receta);
                    return message;
                }
            }
            catch (Exception ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ex);
            }
        }

        [HttpDelete]
        public HttpResponseMessage deleteReceta([FromUri] int id)
        {
            try
            {
                using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
                {
                    SqlCommand cmd = new SqlCommand("UPDATE  RECETAS SET  Estado=0 WHERE idReceta=@id", conn);
                    cmd.Parameters.AddWithValue("@id", id);
                    cmd.Connection = conn;
                    conn.Open();
                    cmd.ExecuteReader();
                    var message = Request.CreateResponse(HttpStatusCode.Created, id);
                    return message;
                }
            }
            catch (Exception ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ex);
            }
        }

        [HttpGet]
        public IEnumerable<Dictionary<string, object>> getProductosReceta(int idRec)
        {
            using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
            {
                SqlCommand cmd = new SqlCommand("SELECT RECETAS.idReceta,RECETAS.idCliente,RECETAS.Imagen,RECETAS.Estado,RECETAS.idDoctor,"+
                     " DETALLERECETA.idMedicamento, PRODUCTOS.Nombre, PRODUCTOS.Image"+
                      " FROM[PRODUCTOS] INNER JOIN[DETALLERECETA]"+
                     " ON PRODUCTOS.idProducto = DETALLERECETA.idMedicamento INNER JOIN[RECETAS]"+
                    " ON DETALLERECETA.idReceta = RECETAS.idReceta"+
                     " WHERE(((RECETAS.idReceta) = @id))", conn);
                cmd.Parameters.AddWithValue("@id", idRec);
                cmd.Connection = conn;
                conn.Open();
                using (var reader = cmd.ExecuteReader())
                {
                    if (reader.Read())
                    {
                        reader.Close();
                        var r = serial.Serialize(cmd.ExecuteReader());
                        conn.Close();
                        return r;
                    }
                    else {
                        return null;
                    }
                }

            }
        }
    }
}

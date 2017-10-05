using RESTFUL_API.Models;
using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace RESTFUL_API.Controllers
{
    public class DetallePedidoController : ApiController
    {
        JSONSerializer serial = new JSONSerializer();
        string DatabaseConnectionString = System.Configuration.ConfigurationManager.ConnectionStrings["GasStationPharmacyDB"].ConnectionString;

        [HttpGet]
        public IEnumerable<Dictionary<string, object>> getAll()
        {

            using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
            {
                SqlCommand cmd = new SqlCommand("SELECT idProducto,idPedido,Cantidad FROM DETALLEPEDIDO", conn);
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
        public HttpResponseMessage getDetById(int id)
        {
            using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
            {
                SqlCommand cmd = new SqlCommand("SELECT idProducto,idPedido,Cantidad FROM DETALLEPEDIDO WHERE idPedido=@id", conn);
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
                        return Request.CreateErrorResponse(HttpStatusCode.NotFound, "El detalle con Id: " + id + " no encontrado.");
                    }
                }
            }
        }

        [HttpPost]
        public HttpResponseMessage regDetalle([FromBody] DetallePedidoModel pedido)
        {
            try
            {
                using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
                {
                    SqlCommand cmd = new SqlCommand("INSERT INTO DETALLEPEDIDO(idProducto,idPedido,Cantidad) VALUES (@producto,@pedido,@cantidad)", conn);
                    cmd.Parameters.AddWithValue("@producto", pedido.idProducto);
                    cmd.Parameters.AddWithValue("@pedido", pedido.idPedido);
                    cmd.Parameters.AddWithValue("@cantidad", pedido.idCantidad);
                    cmd.Connection = conn;
                    conn.Open();
                    cmd.ExecuteReader();
                    var message = Request.CreateResponse(HttpStatusCode.Created, pedido);
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

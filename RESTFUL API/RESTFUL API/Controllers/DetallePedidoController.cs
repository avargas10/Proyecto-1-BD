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
                        var r = serial.Serialize(cmd.ExecuteReader());
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
                    //SqlCommand cmd = new SqlCommand("INSERT INTO DETALLEPEDIDO(idProducto,idPedido,Cantidad) VALUES (@producto,@pedido,@cantidad)", conn);
                    SqlCommand cmd = new SqlCommand("EXEC CREATEDETALLEPEDIDO @producto, @pedido, @sucursal, @cantidad", conn);
                    cmd.Parameters.AddWithValue("@producto", pedido.idProducto);
                    cmd.Parameters.AddWithValue("@pedido", pedido.idPedido);
                    cmd.Parameters.AddWithValue("@cantidad", pedido.Cantidad);
                    cmd.Parameters.AddWithValue("@sucursal", pedido.idSucursal);
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

        [HttpPut]
        public HttpResponseMessage deleteDetalle(int idPedido)
        {
            try
            {
                using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
                {
                    SqlCommand cmd = new SqlCommand("DELETE FROM DETALLEPEDIDO WHERE idPedido=@id", conn);
                    cmd.Parameters.AddWithValue("@id", idPedido);
                    cmd.Connection = conn;
                    conn.Open();
                    cmd.ExecuteReader();
                    var message = Request.CreateResponse(HttpStatusCode.Accepted, idPedido);
                    conn.Close();
                    return message;
                }
            }
            catch (Exception ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ex);
            }


        }

        [HttpDelete]
        public HttpResponseMessage deleteDetalle(int idPedido, int idProducto, int idSucursal)
        {
            try
            {
                using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
                {
                    SqlCommand cmd = new SqlCommand("EXEC DELETEDETALLEPEDIDO @id, @producto, @sucursal", conn);
                    cmd.Parameters.AddWithValue("@id", idPedido);
                    cmd.Parameters.AddWithValue("@producto", idProducto);
                    cmd.Parameters.AddWithValue("@sucursal", idSucursal);
                    cmd.Connection = conn;
                    conn.Open();
                    cmd.ExecuteReader();
                    var message = Request.CreateResponse(HttpStatusCode.Accepted, idPedido);
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
        public HttpResponseMessage updateDetalle(DetallePedidoModel detalle)
        {
            try
            {
                using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
                {
                    SqlCommand cmd = new SqlCommand("EXEC UPDATEDETALLEPEDIDO @producto, @pedido, @sucursal, @cantidad", conn);
                    cmd.Parameters.AddWithValue("@producto", detalle.idProducto);
                    cmd.Parameters.AddWithValue("@pedido", detalle.idPedido);
                    cmd.Parameters.AddWithValue("@sucursal", detalle.idSucursal);
                    cmd.Parameters.AddWithValue("@cantidad", detalle.Cantidad);
                    cmd.Connection = conn;
                    conn.Open();
                    cmd.ExecuteReader();
                    var message = Request.CreateResponse(HttpStatusCode.Accepted, detalle);
                    conn.Close();
                    return message;
                }
                }catch(Exception e)
            {
                return Request.CreateResponse(HttpStatusCode.BadRequest, "Error");
            }
        }
    }
}

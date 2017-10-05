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
    public class PedidosController : ApiController
    {
        JSONSerializer serial = new JSONSerializer();
        string DatabaseConnectionString = System.Configuration.ConfigurationManager.ConnectionStrings["GasStationPharmacyDB"].ConnectionString;

        [HttpGet]
        public IEnumerable<Dictionary<string, object>> getAll()
        {
            using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
            {
                SqlCommand cmd = new SqlCommand("SELECT idPedido,sucursalRecojo,idCliente,horaRecojo,Telefono,Imagen,Estado FROM PEDIDOS", conn);
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
        public HttpResponseMessage regPedido([FromBody] pedidosModel pedido)
        {
            try
            {
                using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
                {
                    SqlCommand cmd = new SqlCommand("INSERT INTO PEDIDOS(sucursalRecojo,idCliente,horaRecojo,Telefono,Imagen,Estado) VALUES (@sucursal,@cliente,@hora,@telefono,@imagen,@estado)", conn);
                    cmd.Parameters.AddWithValue("@sucursal", pedido.sucursalRecojo);
                    cmd.Parameters.AddWithValue("@cliente", pedido.idCliente);
                    cmd.Parameters.AddWithValue("@hora", pedido.horaRecojo);
                    cmd.Parameters.AddWithValue("@telefono", pedido.Telefono);
                    cmd.Parameters.AddWithValue("@imagen", pedido.Imagen);
                    cmd.Parameters.AddWithValue("@estado", pedido.Estado);
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
        public IEnumerable<Dictionary<string, object>> getPedidosbyId([FromUri] int id)
        {
            using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
            {
                SqlCommand cmd = new SqlCommand("SELECT idPedido,sucursalRecojo,idCliente,horaRecojo,Telefono,Imagen,Estado FROM PEDIDOS WHERE idCliente=@id", conn);
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
    }
}

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

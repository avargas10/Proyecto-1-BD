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
    public class EstadosPedidoController : ApiController
    {
        JSONSerializer serial = new JSONSerializer();
        string DatabaseConnectionString = System.Configuration.ConfigurationManager.ConnectionStrings["GasStationPharmacyDB"].ConnectionString;


        [HttpPost]
        public HttpResponseMessage updateEstadoPedido(pedidosModel EstadoPedido)
        {
            try
            {
                using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
                {
                    SqlCommand cmd = new SqlCommand("UPDATE PEDIDOS SET Estado=@estado WHERE idPedido=@id", conn);
                    cmd.Parameters.AddWithValue("@id", EstadoPedido.idPedido);
                    cmd.Parameters.AddWithValue("@estado", EstadoPedido.Estado);
                    cmd.Connection = conn;
                    conn.Open();
                    cmd.ExecuteReader();
                    var message = Request.CreateResponse(HttpStatusCode.Created, EstadoPedido);
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

using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using dataAcces;

namespace gspREST.Controllers
{
    public class PedidosController : ApiController
    {
        [HttpGet]
        public IEnumerable<PEDIDO> getAll()
        {

            using (GasStationPharmacyDBEntities entities = new GasStationPharmacyDBEntities())
            {
                entities.Configuration.LazyLoadingEnabled = false;
                return entities.PEDIDOS.ToList();
            }
        }

        [HttpPost]
        public HttpResponseMessage regPedido([FromBody] PEDIDO pedido)
        {
            try
            {
                using (GasStationPharmacyDBEntities entities = new GasStationPharmacyDBEntities())
                {
                    entities.Configuration.LazyLoadingEnabled = false;
                    entities.PEDIDOS.Add(pedido);
                    entities.SaveChanges();
                    var message = Request.CreateResponse(HttpStatusCode.Created, pedido.idPedido);
                    return message;
                }
            }
            catch (Exception ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ex);
            }
        }

        public IEnumerable<PEDIDO> getPedidosbyId([FromUri] int id)
        {
            try
            {
                using (GasStationPharmacyDBEntities entities = new GasStationPharmacyDBEntities())
                {
                    entities.Configuration.LazyLoadingEnabled = false;
                    var entity = entities.PEDIDOS.ToList().Where(e => e.idCliente == id);
                    return entity;
                }
            }
            catch (Exception ex)
            {
                return null;
            }
        }
    }
}

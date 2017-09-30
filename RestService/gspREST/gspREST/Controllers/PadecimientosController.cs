using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using dataAcces;

namespace gspREST.Controllers
{
    public class PadecimientosController : ApiController
    {
        [HttpGet]
        public IEnumerable<PADECIMIENTO> getAll()
        {

            using (GasStationPharmacyDBEntities entities = new GasStationPharmacyDBEntities())
            {
                entities.Configuration.LazyLoadingEnabled = false;
                return entities.PADECIMIENTOS.ToList();
            }
        }

        [HttpPost]
        public HttpResponseMessage regCliente([FromBody] PADECIMIENTO padecimiento)
        {
            try
            {
                using (GasStationPharmacyDBEntities entities = new GasStationPharmacyDBEntities())
                {
                    entities.Configuration.LazyLoadingEnabled = false;
                    entities.PADECIMIENTOS.Add(padecimiento);
                    entities.SaveChanges();
                    var message = Request.CreateResponse(HttpStatusCode.Created, padecimiento);
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

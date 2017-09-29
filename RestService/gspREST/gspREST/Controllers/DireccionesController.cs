using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using dataAcces;

namespace gspREST.Controllers
{
    public class DireccionesController : ApiController
    {
        [HttpGet]
        public IEnumerable<DIRECCIONE> getAll()
        {

            using (GasStationPharmacyDBEntities entities = new GasStationPharmacyDBEntities())
            {
                entities.Configuration.LazyLoadingEnabled = false;
                return entities.DIRECCIONES.ToList();
            }
        }

        [HttpGet]
        public HttpResponseMessage getUserById(int id)
        {

            using (GasStationPharmacyDBEntities entities = new GasStationPharmacyDBEntities())
            {
                entities.Configuration.LazyLoadingEnabled = false;
                var entity = entities.DIRECCIONES.FirstOrDefault(e => e.idDireccion == id);
                if (entity != null)
                {
                    return Request.CreateResponse(HttpStatusCode.OK, entity);
                }
                else
                {
                    return Request.CreateErrorResponse(HttpStatusCode.NotFound, "Usuario con Cedula: " + id + " no encontrado.");
                }
            }
        }

        [HttpPost]
        public HttpResponseMessage regDireccion([FromBody] DIRECCIONE direccion)
        {
            try
            {
                using (GasStationPharmacyDBEntities entities = new GasStationPharmacyDBEntities())
                {
                    entities.Configuration.LazyLoadingEnabled = false;
                    entities.DIRECCIONES.Add(direccion);
                    entities.SaveChanges();
                    var message = Request.CreateResponse(HttpStatusCode.Created, direccion);
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

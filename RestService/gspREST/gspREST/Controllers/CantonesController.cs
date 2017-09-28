using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using dataAcces;

namespace gspREST.Controllers
{
    public class CantonesController : ApiController
    {
        [HttpGet]
        public IEnumerable<CANTON> getAll()
        {

            using (GasStationPharmacyDBEntities entities = new GasStationPharmacyDBEntities())
            {
                entities.Configuration.LazyLoadingEnabled = false;
                return entities.CANTONs.ToList();
            }
        }
        public IEnumerable<CANTON> getDistritos([FromUri] int Provincia)
        {
            using (GasStationPharmacyDBEntities entities = new GasStationPharmacyDBEntities())
            {
                entities.Configuration.LazyLoadingEnabled = false;
                return entities.CANTONs.ToList().Where(e => e.idProvincia == Provincia);
            }
        }
    }
}

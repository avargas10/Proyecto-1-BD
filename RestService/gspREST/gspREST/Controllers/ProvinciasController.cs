using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using dataAcces;

namespace gspREST.Controllers
{
    public class ProvinciasController : ApiController
    {
        [HttpGet]
        public IEnumerable<PROVINCIA> getAll()
        {

            using (GasStationPharmacyDBEntities entities = new GasStationPharmacyDBEntities())
            {
                entities.Configuration.LazyLoadingEnabled = false;
                return entities.PROVINCIAs.ToList();
            }
        }
    }
}

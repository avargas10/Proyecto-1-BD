using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using dataAcces;
using System.Data.SqlClient;

namespace gspREST.Controllers
{
    public class DistritoController : ApiController
    {
        [HttpGet]
        public IEnumerable<DISTRITO> getAll()
        {

            using (GasStationPharmacyDBEntities entities = new GasStationPharmacyDBEntities())
            {
                entities.Configuration.LazyLoadingEnabled = false;
                return entities.DISTRITOes.ToList();
            }
        }
        public IEnumerable<DISTRITO> getDistritos([FromUri] int canton)
        {
            using (GasStationPharmacyDBEntities entities = new GasStationPharmacyDBEntities())
            {
                entities.Configuration.LazyLoadingEnabled = false;
                return entities.DISTRITOes.ToList().Where(e=>e.idCanton == canton);
            }
        }
    }
}

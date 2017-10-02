using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using dataAcces;

namespace gspREST.Controllers
{
    public class ProductosController : ApiController
    {
        [HttpGet]
        public IEnumerable<PRODUCTO> getAll()
        {

            using (GasStationPharmacyDBEntities entities = new GasStationPharmacyDBEntities())
            {
                entities.Configuration.LazyLoadingEnabled = false;
                return entities.PRODUCTOS.ToList();
            }
        }
        public IEnumerable<PRODUCTOXSUCURSAL> ProductosxSucursal([FromUri] int id)
        {
            using (GasStationPharmacyDBEntities entities = new GasStationPharmacyDBEntities())
            {
                entities.Configuration.LazyLoadingEnabled = false;
                return entities.PRODUCTOXSUCURSALs.ToList().Where(e => e.idSucursal == id);
            }
        }
    }
}

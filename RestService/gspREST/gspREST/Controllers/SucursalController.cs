﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using dataAcces;

namespace gspREST.Controllers
{
    public class SucursalController : ApiController
    {
        [HttpGet]
        public IEnumerable<SUCURSAL> getAll()
        {

            using (GasStationPharmacyDBEntities entities = new GasStationPharmacyDBEntities())
            {
                entities.Configuration.LazyLoadingEnabled = false;
                return entities.SUCURSALs.ToList();
            }
        }
        public IEnumerable<SUCURSAL> getDistritos([FromUri] int empresa)
        {
            using (GasStationPharmacyDBEntities entities = new GasStationPharmacyDBEntities())
            {
                entities.Configuration.LazyLoadingEnabled = false;
                return entities.SUCURSALs.ToList().Where(e => e.idEmpresa == empresa);
            }
        }
    }
}

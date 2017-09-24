using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using dataAcces;

namespace gspREST.Controllers
{
    public class EmpleadosController : ApiController
    {
        [HttpGet]
        public IEnumerable<EMPLEADO> getAll()
        {
            using(GasStationPharmacyDBEntities entities=new GasStationPharmacyDBEntities())
            {
                entities.Configuration.LazyLoadingEnabled = false;
                return entities.EMPLEADOes.ToList();
            }
        }
        [HttpGet]
        public HttpResponseMessage getUserById(int id)
        {
            using (GasStationPharmacyDBEntities entities = new GasStationPharmacyDBEntities())
            {
                entities.Configuration.LazyLoadingEnabled = false;
                var entity = entities.EMPLEADOes.FirstOrDefault(e => e.idEmpleado == id);
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

        public HttpResponseMessage Post([FromBody] EMPLEADO empleado)
        {
            try
            {
                using (GasStationPharmacyDBEntities entities = new GasStationPharmacyDBEntities())
                {
                    entities.Configuration.LazyLoadingEnabled = false;
                    entities.EMPLEADOes.Add(empleado);
                    entities.SaveChanges();
                    var message = Request.CreateResponse(HttpStatusCode.Created, empleado);
                    return message;
                }
            }
            catch (Exception ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ex);
            }
        }

        public HttpResponseMessage Delete(int id)
        {
            using (GasStationPharmacyDBEntities entities = new GasStationPharmacyDBEntities())
            {
                entities.Configuration.LazyLoadingEnabled = false;
                try
                {
                    var entity = entities.EMPLEADOes.FirstOrDefault(e => e.idEmpleado == id);
                    if (entity == null)
                    {
                        return Request.CreateErrorResponse(HttpStatusCode.NotFound, "Usuario con Cedula: " + id + " no encontrado.");
                    }
                    else
                    {
                        entities.EMPLEADOes.Remove(entity);
                        entities.SaveChanges();
                        return Request.CreateResponse(HttpStatusCode.OK);
                    }
                }
                catch (Exception ex)
                {
                    return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ex);
                }
            }
        }

        public HttpResponseMessage Put(int id, [FromBody]EMPLEADO user)
        {
            try
            {
                using (GasStationPharmacyDBEntities entities = new GasStationPharmacyDBEntities())
                {
                    entities.Configuration.LazyLoadingEnabled = false;
                    var entity = entities.EMPLEADOes.FirstOrDefault(e => e.idEmpleado == id);
                    if (entity == null)
                    {
                        return Request.CreateErrorResponse(HttpStatusCode.NotFound, "Empleado con Cedula: " + id.ToString() + ", no encontrado.");
                    }
                    else
                    {
                        entity.Nombre = user.Nombre;
                        entity.idRol = user.idRol;
                        entity.idSucursal = user.idSucursal;
                        entity.idEmpleado = user.idEmpleado;
                        entity.Username = user.Username;
                        entity.Password = user.Password;
                        entities.SaveChanges();
                        return Request.CreateResponse(HttpStatusCode.OK, entity);
                    }

                }
            }
            catch (Exception ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ex);
            }
        }
    }
}


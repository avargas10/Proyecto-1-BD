using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Data.SqlClient;
using dataAcces;
using System.Collections;
using Newtonsoft.Json;

namespace gspREST.Controllers
{
    public class ClientesController : ApiController
    {
        public IEnumerable<Dictionary<string, object>> Serialize(SqlDataReader reader)
        {
            var results = new List<Dictionary<string, object>>();
            var cols = new List<string>();
            for (var i = 0; i < reader.FieldCount; i++)
                cols.Add(reader.GetName(i));

            while (reader.Read())
                results.Add(SerializeRow(cols, reader));

            return results;
        }
        private Dictionary<string, object> SerializeRow(IEnumerable<string> cols,
                                                        SqlDataReader reader)
        {
            var result = new Dictionary<string, object>();
            foreach (var col in cols)
                result.Add(col, reader[col]);
            return result;
        }

        [HttpGet]
        public IEnumerable<Dictionary<string, object>> getAll()
        {

            /*using (GasStationPharmacyDBEntities entities = new GasStationPharmacyDBEntities())
            {
                entities.Configuration.LazyLoadingEnabled = false;
                return entities.CLIENTEs.ToList();
            }*/
            string DatabaseConnectionString = System.Configuration.ConfigurationManager.ConnectionStrings["GasStationPharmacyDB"].ConnectionString;

            using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
            {
                SqlCommand cmd = new SqlCommand("SELECT * FROM CLIENTE WHERE Nombre='Rodolfo' AND pApellido='Solano'", conn);
                cmd.Connection = conn;
                conn.Open();
                String[] mylist;
                using (var reader = cmd.ExecuteReader())
                {
                    
                    var r = Serialize(reader);
                    string json = JsonConvert.SerializeObject(r);
                    return r;
                }

            }
        }
        [HttpGet]
        public HttpResponseMessage getUserById(int id)
        {
            
            using (GasStationPharmacyDBEntities entities = new GasStationPharmacyDBEntities())
            {
                entities.Configuration.LazyLoadingEnabled = false;
                var entity = entities.CLIENTEs.FirstOrDefault(e => e.Cedula == id);
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

        [HttpGet]
        public HttpResponseMessage getUserByUsername([FromUri] string username)
        {
            using (GasStationPharmacyDBEntities entities = new GasStationPharmacyDBEntities())
            {
                entities.Configuration.LazyLoadingEnabled = false;
                var entity = entities.CLIENTEs.FirstOrDefault(e => e.Username == username);
                if (entity != null)
                {
                    return Request.CreateResponse(HttpStatusCode.OK, entity);
                }
                else
                {
                    return Request.CreateErrorResponse(HttpStatusCode.NotFound, "Usuario con Username: " + username + " no encontrado.");
                }
            }
        }
        [HttpPost]
        public bool clientLogin([FromUri]string username, [FromUri]string pass)
        {
            string DatabaseConnectionString = System.Configuration.ConfigurationManager.ConnectionStrings["GasStationPharmacyDB"].ConnectionString;

            using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
            {
                SqlCommand cmd = new SqlCommand("SELECT * FROM CLIENTE WHERE Username=@user AND Password=@pass", conn);
                cmd.Parameters.AddWithValue("@user",username);
                cmd.Parameters.AddWithValue("@pass", pass);
                cmd.Connection = conn;
                conn.Open();
                String[] mylist;
                using (var reader = cmd.ExecuteReader())
                {
                    if (reader.Read())
                    {
                        return true;
                    }
                    else {
                        return false;
                    }
                }

            }
        }

        public HttpResponseMessage regCliente([FromBody] CLIENTE cliente)
        {
            try
            {
                using (GasStationPharmacyDBEntities entities = new GasStationPharmacyDBEntities())
                {
                    entities.Configuration.LazyLoadingEnabled = false;
                    entities.CLIENTEs.Add(cliente);
                    entities.SaveChanges();
                    var message = Request.CreateResponse(HttpStatusCode.Created, cliente);
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
                try
                {
                    entities.Configuration.LazyLoadingEnabled = false;
                    var entity = entities.CLIENTEs.FirstOrDefault(e => e.Cedula == id);
                    if (entity == null)
                    {
                        return Request.CreateErrorResponse(HttpStatusCode.NotFound, "Usuario con Cedula: " + id + " no encontrado.");
                    }
                    else
                    {
                        entities.CLIENTEs.Remove(entity);
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

        public HttpResponseMessage Put(int id, [FromBody]CLIENTE user)
        {
            try
            {
                using (GasStationPharmacyDBEntities entities = new GasStationPharmacyDBEntities())
                {
                    entities.Configuration.LazyLoadingEnabled = false;
                    var entity = entities.CLIENTEs.FirstOrDefault(e => e.Cedula == id);
                    if (entity == null)
                    {
                        return Request.CreateErrorResponse(HttpStatusCode.NotFound, "Usuario con Cedula: " + id.ToString() + ", no encontrado.");
                    }
                    else
                    {
                        entity.Nombre = user.Nombre;
                        entity.pApellido = user.pApellido;
                        entity.sApellido = user.sApellido;
                        entity.Nacimiento = user.Nacimiento;
                        entity.Username = user.Username;
                        entity.Password = user.Password;
                        entity.Cedula = user.Cedula;
                        entity.Direccion = user.Direccion;
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


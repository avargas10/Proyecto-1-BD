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
    public class ProductosController : ApiController
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
                return entities.PRODUCTOS.ToList();
            }*/
            string DatabaseConnectionString = System.Configuration.ConfigurationManager.ConnectionStrings["GasStationPharmacyDB"].ConnectionString;

            using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
            {
                SqlCommand cmd = new SqlCommand("SELECT * FROM PRODUCTOS", conn);
                cmd.Connection = conn;
                conn.Open();
                String[] mylist;
                using (var reader = cmd.ExecuteReader())
                {

                    var r = Serialize(reader);
                    return r;
                }

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

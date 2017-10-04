using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace RESTFUL_API.Controllers
{
    public class DistritoController : ApiController
    {
        JSONSerializer serial = new JSONSerializer();
        string DatabaseConnectionString = System.Configuration.ConfigurationManager.ConnectionStrings["GasStationPharmacyDB"].ConnectionString;

        [HttpGet]
        public IEnumerable<Dictionary<string, object>> getAll()
        {

            using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
            {
                SqlCommand cmd = new SqlCommand("SELECT idDistrito,idCanton,Nombre FROM DISTRITO", conn);
                cmd.Connection = conn;
                conn.Open();
                using (var reader = cmd.ExecuteReader())
                {
                    var r = serial.Serialize(reader);
                    conn.Close();
                    return r;
                }

            }
        }

        [HttpGet]
        public IEnumerable<Dictionary<string, object>> getDisbyCanton(int idCanton)
        {
            using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
            {
                SqlCommand cmd = new SqlCommand("SELECT idDistrito,idCanton,Nombre FROM DISTRITO WHERE idCanton=@id", conn);
                cmd.Parameters.AddWithValue("@id", idCanton);
                cmd.Connection = conn;
                conn.Open();
                using (var reader = cmd.ExecuteReader())
                {

                    reader.Close();
                    var r = serial.Serialize(cmd.ExecuteReader());
                    return r;

                }
            }
        }
    }

}

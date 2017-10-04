using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using RESTFUL_API.Models;
using System.Data.SqlClient;

namespace RESTFUL_API.Controllers
{
    public class CantonesController : ApiController
    {
        JSONSerializer serial = new JSONSerializer();
        string DatabaseConnectionString = System.Configuration.ConfigurationManager.ConnectionStrings["GasStationPharmacyDB"].ConnectionString;

        [HttpGet]
        public IEnumerable<Dictionary<string, object>> getAll()
        {

            using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
            {
                SqlCommand cmd = new SqlCommand("SELECT idCanton,idProvincia,Nombre FROM CANTON", conn);
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
        public IEnumerable<Dictionary<string, object>> getCanbyidProvincia(int idProvincia)
        {
            using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
            {
                SqlCommand cmd = new SqlCommand("SELECT idCanton,idProvincia,Nombre FROM CANTON WHERE idProvincia=@id", conn);
                cmd.Parameters.AddWithValue("@id", idProvincia);
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

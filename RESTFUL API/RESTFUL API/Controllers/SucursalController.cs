using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace RESTFUL_API.Controllers
{
    public class SucursalController : ApiController
    {
        JSONSerializer serial = new JSONSerializer();
        string DatabaseConnectionString = System.Configuration.ConfigurationManager.ConnectionStrings["GasStationPharmacyDB"].ConnectionString;

        [HttpGet]
        public IEnumerable<Dictionary<string, object>> getAll()
        {
            using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
            {
                SqlCommand cmd = new SqlCommand("SELECT idSucursal,idEmpresa,idProvincia,idCanton,idDistrito,Latitud,Longitud, detalleDireccion,Nombre FROM SUCURSAL", conn);
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
        public IEnumerable<Dictionary<string, object>> getSucursalbyEmpresa([FromUri] int empresa)
        {
            using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
            {
                SqlCommand cmd = new SqlCommand("SELECT idSucursal,idEmpresa,idProvincia,idCanton,idDistrito,Latitud,Longitud, detalleDireccion,Nombre FROM SUCURSAL WHERE idEmpresa=@id", conn);
                cmd.Parameters.AddWithValue("@id", empresa);
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
    }
}

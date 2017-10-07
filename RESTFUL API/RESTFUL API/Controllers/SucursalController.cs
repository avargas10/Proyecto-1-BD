using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using RESTFUL_API.Models;

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
        [HttpGet]
        public IEnumerable<Dictionary<string, object>> getSucursalbyid([FromUri] int id)
        {
            using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
            {
                SqlCommand cmd = new SqlCommand("SELECT idSucursal,idEmpresa,idProvincia,idCanton,idDistrito,Latitud,Longitud, detalleDireccion,Nombre FROM SUCURSAL WHERE idSucursal=@id", conn);
                cmd.Parameters.AddWithValue("@id", id);
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
        [HttpPost]
        public HttpResponseMessage regSucursal([FromBody] sucursalModel suc)
        {
            try
            {
                using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
                {
                    SqlCommand cmd = new SqlCommand("INSERT INTO SUCURSAL (idEmpresa,idProvincia,idCanton,idDistrito,Latitud,Longitud, detalleDireccion,Nombre,Estado) VALUES (@empresa,@provincia,@canton,@distrito,@lat,@long,@detalle,@nombre,@estado)", conn);
                    cmd.Parameters.AddWithValue("@empresa", suc.idEmpresa);
                    cmd.Parameters.AddWithValue("@provincia", suc.idProvincia);
                    cmd.Parameters.AddWithValue("@canton", suc.idCanton);
                    cmd.Parameters.AddWithValue("@distrito", suc.idDistrito);
                    cmd.Parameters.AddWithValue("@lat", suc.Latitud);
                    cmd.Parameters.AddWithValue("@long", suc.Longitud);
                    cmd.Parameters.AddWithValue("@detalle", suc.detalleDireccion);
                    cmd.Parameters.AddWithValue("@nombre", suc.Nombre);
                    cmd.Parameters.AddWithValue("@estado", suc.Estado);
                    cmd.Connection = conn;
                    conn.Open();
                    cmd.ExecuteReader();
                    var message = Request.CreateResponse(HttpStatusCode.Created, suc);
                    return message;
                }
            }
            catch (Exception ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ex);
            }
        }
    }
}

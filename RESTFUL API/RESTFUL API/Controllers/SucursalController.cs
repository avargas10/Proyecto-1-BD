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
                SqlCommand cmd = new SqlCommand("SELECT idSucursal,idEmpresa,idProvincia,idCanton,idDistrito, detalleDireccion,Nombre, Imagen FROM SUCURSAL WHERE Estado!=0", conn);
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
                SqlCommand cmd = new SqlCommand("SELECT idSucursal,idEmpresa,idProvincia,idCanton,idDistrito, detalleDireccion,Nombre, Imagen FROM SUCURSAL WHERE idEmpresa=@id AND Estado!=0", conn);
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
                SqlCommand cmd = new SqlCommand("SELECT idSucursal,idEmpresa,idProvincia,idCanton,idDistrito, detalleDireccion,Nombre FROM SUCURSAL WHERE idSucursal=@id AND Estado!=0", conn);
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
                    SqlCommand cmd1 = new SqlCommand("SELECT Nombre FROM SUCURSAL WHERE Nombre=@nombre");
                    cmd1.Parameters.AddWithValue("@nombre", suc.Nombre);
                    cmd1.Connection = conn;
                    conn.Open();
                    var reader = cmd1.ExecuteReader();
                    if (reader.Read())
                    {
                        conn.Close();
                        return Request.CreateResponse(HttpStatusCode.Conflict, "That Store name already exist!");
                    }
                    else
                    {
                        conn.Close();
                        SqlCommand cmd = new SqlCommand("INSERT INTO SUCURSAL (idEmpresa,idProvincia,idCanton,idDistrito, detalleDireccion,Nombre,Estado) OUTPUT INSERTED.idSucursal VALUES (@empresa,@provincia,@canton,@distrito,@detalle,@nombre,@estado)", conn);
                        cmd.Parameters.AddWithValue("@empresa", suc.idEmpresa);
                        cmd.Parameters.AddWithValue("@provincia", suc.idProvincia);
                        cmd.Parameters.AddWithValue("@canton", suc.idCanton);
                        cmd.Parameters.AddWithValue("@distrito", suc.idDistrito);
                        cmd.Parameters.AddWithValue("@detalle", suc.detalleDireccion);
                        cmd.Parameters.AddWithValue("@nombre", suc.Nombre);
                        cmd.Parameters.AddWithValue("@estado", suc.Estado);
                        cmd.Connection = conn;
                        conn.Open();
                        //cmd.ExecuteReader();
                        var message = Request.CreateResponse(HttpStatusCode.Created, serial.singleserialize(cmd.ExecuteReader()));
                        return message;
                    }
                }
            }
            catch (Exception ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ex);
            }
        }

        [HttpPut]
        public HttpResponseMessage updateSucursal(sucursalModel sucursal)
        {
            try
            {
                using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
                {
                    SqlCommand cmd = new SqlCommand("UPDATE  SUCURSAL SET  idEmpresa=@empresa, idProvincia=@provincia, idCanton=@canton, idDistrito=@distrito, detalleDireccion=@direccion, Nombre=@nombre, Estado=@estado, Imagen=@imagen WHERE idSucursal=@id", conn);
                    cmd.Parameters.AddWithValue("@id", sucursal.idSucursal);
                    cmd.Parameters.AddWithValue("@empresa", sucursal.idEmpresa);
                    cmd.Parameters.AddWithValue("@provincia", sucursal.idProvincia);
                    cmd.Parameters.AddWithValue("@canton", sucursal.idCanton);
                    cmd.Parameters.AddWithValue("@distrito", sucursal.idDistrito);
                    cmd.Parameters.AddWithValue("@direccion", sucursal.detalleDireccion);
                    cmd.Parameters.AddWithValue("@nombre", sucursal.Nombre);
                    cmd.Parameters.AddWithValue("@estado", sucursal.Estado);
                    cmd.Parameters.AddWithValue("@imagen", sucursal.Imagen);
                    cmd.Connection = conn;
                    conn.Open();
                    cmd.ExecuteReader();
                    var message = Request.CreateResponse(HttpStatusCode.Created, sucursal);
                    return message;
                }
            }
            catch (Exception ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ex);
            }

        }

        [HttpDelete]
        public HttpResponseMessage deleteSucursal([FromBody] sucursalModel del)
        {
            try
            {
                using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
                {
                    SqlCommand cmd = new SqlCommand("UPDATE  SUCURSAL SET  Estado=0 WHERE idSucursal=@id", conn);
                    cmd.Parameters.AddWithValue("@id", del.idSucursal);
                    cmd.Connection = conn;
                    conn.Open();
                    cmd.ExecuteReader();
                    var message = Request.CreateResponse(HttpStatusCode.Created, del);
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

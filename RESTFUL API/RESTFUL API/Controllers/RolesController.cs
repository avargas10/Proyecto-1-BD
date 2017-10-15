using RESTFUL_API.Models;
using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace RESTFUL_API.Controllers
{
    public class RolesController : ApiController
    {

        JSONSerializer serial = new JSONSerializer();
        string DatabaseConnectionString = System.Configuration.ConfigurationManager.ConnectionStrings["GasStationPharmacyDB"].ConnectionString;

        [HttpGet]
        public IEnumerable<Dictionary<string, object>> getAll()
        {
            using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
            {
                SqlCommand cmd = new SqlCommand("SELECT idRol,Nombre,Descripcion FROM ROLES", conn);
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
        public IEnumerable<Dictionary<string, object>> getRolbyEmpresa(int id)
        {
            using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
            {
                SqlCommand cmd = new SqlCommand("SELECT idRol,Nombre,Descripcion,Estado, Empresa FROM ROLES WHERE Empresa=@id AND Estado!=0", conn);
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
        public HttpResponseMessage regRol([FromBody] RolesModel rol)
        {
            try
            {
                using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
                {
                    SqlCommand cmd1 = new SqlCommand("SELECT Nombre FROM ROLES WHERE Nombre=@nombre");
                    cmd1.Parameters.AddWithValue("@nombre", rol.Nombre);
                    cmd1.Connection = conn;
                    conn.Open();
                    var reader = cmd1.ExecuteReader();
                    if (reader.Read())
                    {
                        conn.Close();
                        return Request.CreateResponse(HttpStatusCode.Conflict, "That rol name already exist!");
                    }
                    else
                    {
                        conn.Close();
                        SqlCommand cmd = new SqlCommand("INSERT INTO ROLES(Nombre,Descripcion) VALUES (@nombre,@descripcion)", conn);
                        cmd.Parameters.AddWithValue("@nombre", rol.Nombre);
                        cmd.Parameters.AddWithValue("@descripcion", rol.Descripcion);
                        cmd.Connection = conn;
                        conn.Open();
                        cmd.ExecuteReader();
                        var message = Request.CreateResponse(HttpStatusCode.Created, rol);
                        return message;
                    }
                }
            }
            catch (Exception ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ex);
            }
        }

        [HttpDelete]
        public HttpResponseMessage deleteRol([FromBody] RolesModel del)
        {
            try
            {
                using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
                {
                    SqlCommand cmd = new SqlCommand("UPDATE  ROLES SET  Estado=0 WHERE idRol=@id", conn);
                    cmd.Parameters.AddWithValue("@id", del.idRol);
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

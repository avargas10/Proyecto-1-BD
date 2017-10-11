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
    public class ProductosController : ApiController
    {
        JSONSerializer serial = new JSONSerializer();
        string DatabaseConnectionString = System.Configuration.ConfigurationManager.ConnectionStrings["GasStationPharmacyDB"].ConnectionString;

        [HttpGet]
        public IEnumerable<Dictionary<string, object>> getAll()
        {
            using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
            {
                SqlCommand cmd = new SqlCommand("SELECT idProducto,Proveedor,Nombre,esMedicamento,reqPrescripcion,Image FROM PRODUCTOS", conn);
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
        public IEnumerable<Dictionary<string, object>> getMedicamentos(int id)
        {
            using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
            {
                SqlCommand cmd = new SqlCommand("SELECT idProducto,Proveedor,Nombre,esMedicamento,reqPrescripcion,Image FROM PRODUCTOS WHERE esMedicamento=1", conn);
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
        public  IEnumerable<Dictionary<string, object>> getProdictosSucursal(int idSucursal)
        {
            using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
            {
                SqlCommand cmd = new SqlCommand("SELECT PRODUCTOXSUCURSAL.idSucursal,PRODUCTOXSUCURSAL.Precio , PRODUCTOS.idProducto,PRODUCTOS.Proveedor,PRODUCTOS.Nombre," +
                     "PRODUCTOS.esMedicamento,PRODUCTOS.reqPrescripcion,PRODUCTOS.Image " +
                     "FROM [PRODUCTOXSUCURSAL] INNER JOIN [PRODUCTOS] ON PRODUCTOXSUCURSAL.codProducto = PRODUCTOS.idProducto WHERE (((PRODUCTOXSUCURSAL.idSucursal)=@id));", conn);
                cmd.Parameters.AddWithValue("@id", idSucursal);
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
        public IEnumerable<Dictionary<string, object>> ProductosxSucursal([FromUri] int id)
        {
            using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
            {
                SqlCommand cmd = new SqlCommand("SELECT idSucursal,codProducto,Cantidad,Precio FROM PRODUCTOXSUCURSAL WHERE idSucursal=@id", conn);
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
        public Dictionary<string, object> ProductobyCod([FromUri] int Cod)
        {
            using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
            {
                SqlCommand cmd = new SqlCommand("SELECT idProducto,Proveedor,Nombre,esMedicamento,reqPrescripcion,Image FROM PRODUCTOS WHERE idProducto=@cod", conn);
                cmd.Parameters.AddWithValue("@cod", Cod);
                cmd.Connection = conn;
                conn.Open();
                using (var reader = cmd.ExecuteReader())
                {
                    var r = serial.singleserialize(reader);
                    conn.Close();
                    return r;
                }

            }
        }
        [HttpPost]
        public Dictionary<string, object> ProductoSucursalbyCod([FromUri] int Suc, [FromUri] int CodProd)
        {
            using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
            {
                SqlCommand cmd = new SqlCommand("SELECT idSucursal,codProducto,Cantidad,Precio FROM PRODUCTOXSUCURSAL WHERE idSucursal=@suc AND codProducto=@cod", conn);
                cmd.Parameters.AddWithValue("@suc", Suc);
                cmd.Parameters.AddWithValue("@cod", CodProd);
                cmd.Connection = conn;
                conn.Open();
                using (var reader = cmd.ExecuteReader())
                {
                    var r = serial.singleserialize(reader);
                    conn.Close();
                    return r;
                }

            }
        }
    }
}

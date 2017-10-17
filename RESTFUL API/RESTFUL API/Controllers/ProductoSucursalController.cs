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
    public class ProductoSucursalController : ApiController
    {
        JSONSerializer serial = new JSONSerializer();
        string DatabaseConnectionString = System.Configuration.ConfigurationManager.ConnectionStrings["GasStationPharmacyDB"].ConnectionString;


        [HttpPost]
        public HttpResponseMessage regProductoSucursal(productoSucursalModel ProdSucursal)
        {
            try
            {
                using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
                {
                    SqlCommand cmd = new SqlCommand("INSERT INTO PRODUCTOXSUCURSAL(idSucursal, codProducto, Cantidad, Precio) VALUES (@id,@producto,@cantidad,@precio)", conn);
                    cmd.Parameters.AddWithValue("@id", ProdSucursal.idSucursal);
                    cmd.Parameters.AddWithValue("@producto", ProdSucursal.codProducto);
                    cmd.Parameters.AddWithValue("@cantidad", ProdSucursal.Cantidad);
                    cmd.Parameters.AddWithValue("@precio", ProdSucursal.Precio);
                    cmd.Connection = conn;
                    conn.Open();
                    cmd.ExecuteReader();
                    var message = Request.CreateResponse(HttpStatusCode.Created, ProdSucursal);
                    return message;
                }

            }
            catch (Exception e)
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, e);
            }

        }

        [HttpPut]
        public HttpResponseMessage updateProducto(productoSucursalModel medicamento)
        {
            try
            {
                using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
                {
                    SqlCommand cmd = new SqlCommand("EXEC UPDATEPRODUCTO @id, @sucursal, @cantidad, @precio", conn);
                    cmd.Parameters.AddWithValue("@id", medicamento.codProducto);
                    cmd.Parameters.AddWithValue("@sucursal", medicamento.idSucursal);
                    cmd.Parameters.AddWithValue("@cantidad", medicamento.Cantidad);
                    cmd.Parameters.AddWithValue("@precio", medicamento.Precio);

                    cmd.Connection = conn;
                    conn.Open();
                    cmd.ExecuteReader();
                    var message = Request.CreateResponse(HttpStatusCode.Created, medicamento);
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

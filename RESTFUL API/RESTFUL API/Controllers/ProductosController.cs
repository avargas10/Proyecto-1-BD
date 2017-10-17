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
                SqlCommand cmd = new SqlCommand("SELECT idProducto,Proveedor,Nombre,esMedicamento,reqPrescripcion,Image FROM PRODUCTOS WHERE Estado!=0", conn);
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
                SqlCommand cmd = new SqlCommand("SELECT idProducto,Proveedor,Nombre,esMedicamento,reqPrescripcion,Image FROM PRODUCTOS WHERE esMedicamento=1 AND Estado!=0", conn);
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
                     "FROM [PRODUCTOXSUCURSAL] INNER JOIN [PRODUCTOS] ON PRODUCTOXSUCURSAL.codProducto = PRODUCTOS.idProducto WHERE (((PRODUCTOXSUCURSAL.idSucursal)=@id AND PRODUCTOS.Estado!=0));", conn);
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

        [HttpGet]
        public  IEnumerable<Dictionary<string, object>> getEstadistica(int idEmpresa){
            
                using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
                {

                    SqlCommand cmd = new SqlCommand("EXEC GETESTADISTICA @id", conn);
                    cmd.Parameters.AddWithValue("@id", idEmpresa);
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
        public IEnumerable<Dictionary<string, object>> getProductosPedido(int idPed)
        {
            using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
            {
                SqlCommand cmd = new SqlCommand("SELECT PEDIDOS.idPedido,PEDIDOS.sucursalRecojo,PEDIDOS.Imagen,PEDIDOS.idCliente,PEDIDOS.horaRecojo, PEDIDOS.Telefono,PEDIDOS.Estado,"+
                    " DETALLEPEDIDO.idProducto, DETALLEPEDIDO.Cantidad, PRODUCTOS.Nombre, PRODUCTOS.Image"+
                     " FROM [PRODUCTOS] INNER JOIN[DETALLEPEDIDO]"+
                     " ON PRODUCTOS.idProducto = DETALLEPEDIDO.idProducto INNER JOIN[PEDIDOS]"+
                    " ON DETALLEPEDIDO.idPedido = PEDIDOS.idPedido"+
                     " WHERE(((PEDIDOS.idPedido) = @id))", conn);
                cmd.Parameters.AddWithValue("@id", idPed);
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
        public IEnumerable<Dictionary<string, object>> getProdPed(int Ped)
        {
            using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
            {
                SqlCommand cmd = new SqlCommand("SELECT PEDIDOS.idPedido,PEDIDOS.sucursalRecojo,PEDIDOS.idCliente,PEDIDOS.horaRecojo, PEDIDOS.Telefono,PEDIDOS.Estado," +
                    " DETALLEPEDIDO.idProducto, DETALLEPEDIDO.Cantidad, PRODUCTOS.Nombre, PRODUCTOS.Image" +
                     " FROM [PRODUCTOS] INNER JOIN[DETALLEPEDIDO]" +
                     " ON PRODUCTOS.idProducto = DETALLEPEDIDO.idProducto INNER JOIN[PEDIDOS]" +
                    " ON DETALLEPEDIDO.idPedido = PEDIDOS.idPedido" +
                     " WHERE(((PEDIDOS.idPedido) = @id))", conn);
                cmd.Parameters.AddWithValue("@id", Ped);
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
        public IEnumerable<Dictionary<string, object>> getProductosPedidoSucursal([FromUri] int suc)
        {
            using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
            {
                SqlCommand cmd = new SqlCommand("SELECT PEDIDOS.idPedido,PEDIDOS.sucursalRecojo,PEDIDOS.Imagen,PEDIDOS.idCliente,PEDIDOS.horaRecojo, PEDIDOS.Telefono,PEDIDOS.Estado," +
                    " DETALLEPEDIDO.idProducto, DETALLEPEDIDO.Cantidad, PRODUCTOS.Nombre, PRODUCTOS.Image" +
                     " FROM [PRODUCTOS] INNER JOIN[DETALLEPEDIDO]" +
                     " ON PRODUCTOS.idProducto = DETALLEPEDIDO.idProducto INNER JOIN[PEDIDOS]" +
                    " ON DETALLEPEDIDO.idPedido = PEDIDOS.idPedido" +
                     " WHERE(((PEDIDOS.sucursalRecojo) = @suc))", conn);
                cmd.Parameters.AddWithValue("@suc", suc);
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

        [HttpPut]
        public HttpResponseMessage verifCantidad(productoSucursalModel detalle)
        {
            try
            {
                using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
                {
                    SqlCommand cmd = new SqlCommand("EXEC VERIFCANTIDAD @producto, @sucursal, @cantidad", conn);
                    cmd.Parameters.AddWithValue("@producto", detalle.codProducto);
                    cmd.Parameters.AddWithValue("@sucursal", detalle.idSucursal);
                    cmd.Parameters.AddWithValue("@cantidad", detalle.Cantidad);
                    cmd.Connection = conn;
                    conn.Open();
                    var reader=cmd.ExecuteReader();
                    if (reader.Read())
                    {
                        return Request.CreateResponse(HttpStatusCode.Accepted, "true");
                    }
                    else
                    {
                        return Request.CreateErrorResponse(HttpStatusCode.BadRequest, "false");
                    }
                }
            }
            catch (Exception ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ex);
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
                    if (reader.Read())
                    {
                        reader.Close();
                        var r = serial.singleserialize(cmd.ExecuteReader());
                        conn.Close();
                        return r;
                    }
                    else
                    {
                        return null;
                    }
                }

            }
        }

        [HttpPost]
        public HttpResponseMessage regProducto(productosModel producto)
        {
            try
            {
                using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
                {
                    object nombre, cod, estado;
                    SqlCommand cmd1;

                    cmd1 = new SqlCommand("SELECT Nombre, idProducto, Estado FROM PRODUCTOS WHERE idProducto=@id OR Nombre=@user");
                    cmd1.Parameters.AddWithValue("@id", producto.idProducto);
                    cmd1.Parameters.AddWithValue("@user", producto.Nombre);
                    cmd1.Connection = conn;
                    conn.Open();
                    var reader = cmd1.ExecuteReader();
                    if (reader.Read())
                    {
                        reader.Close();
                        var data = serial.singleserialize(cmd1.ExecuteReader());
                        data.TryGetValue("Nombre", out nombre);
                        data.TryGetValue("idProducto", out cod);
                        data.TryGetValue("Estado", out estado);
                        conn.Close();
                        if ((Convert.ToString(nombre).Equals(producto.Nombre)) && (Convert.ToInt32(cod) == producto.idProducto) && (Convert.ToInt32(estado) == 1))
                        {
                            return Request.CreateResponse(HttpStatusCode.Conflict, "This product already exist!");
                        }
                        else if ((Convert.ToString(nombre).Equals(producto.Nombre)) && (Convert.ToInt32(cod) == producto.idProducto) && (Convert.ToInt32(estado) == 0))
                        {
                            cmd1 = new SqlCommand("UPDATE  PRODUCTOS SET  Nombre=@nombre, Proveedor=@proveedor, esMedicamento=@medicamento, reqPrescripcion=@prescripcion, Image=@image, Estado=1 WHERE idProducto=@id", conn);
                            cmd1.Parameters.AddWithValue("@id", producto.idProducto);
                            cmd1.Parameters.AddWithValue("@nombre", producto.Nombre);
                            cmd1.Parameters.AddWithValue("@proveedor", producto.Proveedor);
                            cmd1.Parameters.AddWithValue("@medicamento", producto.esMedicamento);
                            cmd1.Parameters.AddWithValue("@prescripcion", producto.reqPrescripcion);
                            cmd1.Parameters.AddWithValue("@image", producto.Image);
                            cmd1.Connection = conn;
                            conn.Open();
                            cmd1.ExecuteReader();
                            var message = Request.CreateResponse(HttpStatusCode.Created, producto);
                            conn.Close();
                            return message;
                        }
                        else if (Convert.ToInt32(cod) == producto.idProducto)
                        {
                            return Request.CreateResponse(HttpStatusCode.BadRequest, "Product code already exist!");
                        }
                        else if (Convert.ToString(nombre).Equals(producto.Nombre))
                        {
                            return Request.CreateResponse(HttpStatusCode.BadGateway, "Product name already exist!");
                        }
                        return null;
                    }
                    else
                    {
                        conn.Close();
                        SqlCommand cmd = new SqlCommand("INSERT INTO PRODUCTOS(idProducto, Proveedor, Nombre, esMedicamento, reqPrescripcion, Image, Estado) VALUES (@id,@proveedor,@nombre,@medicamento,@prescripcion,@image,1)", conn);
                        cmd.Parameters.AddWithValue("@id", producto.idProducto);
                        cmd.Parameters.AddWithValue("@proveedor", producto.Proveedor);
                        cmd.Parameters.AddWithValue("@nombre", producto.Nombre);
                        cmd.Parameters.AddWithValue("@medicamento", producto.esMedicamento);
                        cmd.Parameters.AddWithValue("@prescripcion", producto.reqPrescripcion);
                        cmd.Parameters.AddWithValue("@image", producto.Image);
                        cmd.Connection = conn;
                        conn.Open();
                        cmd.ExecuteReader();
                        var message = Request.CreateResponse(HttpStatusCode.Created, producto);
                        return message;
                    }

                }
            }
            catch (Exception e)
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, e);
            }
            
        }

        [HttpDelete]
        public HttpResponseMessage deleteProducto([FromBody] productosModel del)
        {
            try
            {
                using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
                {
                    SqlCommand cmd = new SqlCommand("UPDATE  PRODUCTOS SET  Estado=0 WHERE idProducto=@id", conn);
                    cmd.Parameters.AddWithValue("@id", del.idProducto);
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

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
    public class EmpleadosController : ApiController
    {
        JSONSerializer serial = new JSONSerializer();
        string DatabaseConnectionString = System.Configuration.ConfigurationManager.ConnectionStrings["GasStationPharmacyDB"].ConnectionString;

        [HttpGet]
        public IEnumerable<Dictionary<string, object>> getAll()
        {

            using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
            {
                SqlCommand cmd = new SqlCommand("SELECT idEmpleado,Nombre,pApellido,sApellido,Password,Username,Email,Nacimiento,Direccion FROM EMPLEADO WHERE Estado!=0", conn);
                cmd.Connection = conn;
                conn.Open();
                using (var reader = cmd.ExecuteReader())
                {
                    var r = serial.Serialize(reader);
                    return r;
                }

            }
        }

        [HttpGet]
        public HttpResponseMessage getUserById(int id)
        {
            using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
            {
                SqlCommand cmd = new SqlCommand("SELECT idEmpleado,Nombre,pApellido,sApellido,Password,Username,Email,Nacimiento,Direccion FROM EMPLEADO WHERE idEmpleado=@id AND Estado!=0", conn);
                cmd.Parameters.AddWithValue("@id", id);
                cmd.Connection = conn;
                conn.Open();
                using (var reader = cmd.ExecuteReader())
                {
                    if (reader.Read())
                    {
                        reader.Close();
                        var r = serial.singleserialize(cmd.ExecuteReader());
                        return Request.CreateResponse(HttpStatusCode.OK, r);
                    }
                    else
                    {
                        return Request.CreateErrorResponse(HttpStatusCode.NotFound, "Usuario con Cedula: " + id + " no encontrado.");
                    }
                }
            }
        }
        [HttpGet]
        public HttpResponseMessage getUserByUsername([FromUri] string username)
        {
            using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
            {
                SqlCommand cmd = new SqlCommand("SELECT EMPLEADO.idEmpleado,EMPLEADO.Nombre,EMPLEADO.pApellido,EMPLEADO.sApellido,EMPLEADO.Password,EMPLEADO.Username,"+
                " EMPLEADO.Email, EMPLEADO.Nacimiento, EMPLEADO.Direccion, EMPLEADOXSUCURSAL.idRol, Roles.Nombre AS nombreRol FROM EMPLEADO INNER JOIN"+
                " EMPLEADOXSUCURSAL ON EMPLEADOXSUCURSAL.idEmpleado = EMPLEADO.idEmpleado INNER JOIN ROLES ON ROLES.idRol = EMPLEADOXSUCURSAL.idRol WHERE EMPLEADO.Username = @user AND EMPLEADO.Estado!=0", conn);
                cmd.Parameters.AddWithValue("@user", username);
                cmd.Connection = conn;
                conn.Open();
                using (var reader = cmd.ExecuteReader())
                {
                    if (reader.Read())
                    {
                        reader.Close();
                        var r = serial.singleserialize(cmd.ExecuteReader());
                        return Request.CreateResponse(HttpStatusCode.OK, r);
                    }
                    else
                    {

                        return Request.CreateErrorResponse(HttpStatusCode.NotFound, "Usuario con Username: " + username + " no encontrado.");
                    }

                }
            }
        }

        [HttpGet]
        public IEnumerable<Dictionary<string, object>> getEmpleadosxSucursal(int idSuc)
        {
            using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
            {
                SqlCommand cmd = new SqlCommand("SELECT EMPLEADOXSUCURSAL.idSucursal,EMPLEADOXSUCURSAL.idEmpleado,EMPLEADOXSUCURSAL.idRol , EMPLEADO.Email,EMPLEADO.Username,EMPLEADO.Password," +
                     "EMPLEADO.Nombre,EMPLEADO.pApellido,EMPLEADO.sApellido,EMPLEADO.Nacimiento,EMPLEADO.Direccion,EMPLEADO.Estado " +
                     "FROM [EMPLEADOXSUCURSAL] INNER JOIN [EMPLEADO] ON EMPLEADOXSUCURSAL.idEmpleado = EMPLEADO.idEmpleado WHERE (((EMPLEADOXSUCURSAL.idSucursal)=@id AND EMPLEADO.Estado!=0));", conn);
                cmd.Parameters.AddWithValue("@id", idSuc);
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
        public HttpResponseMessage EmpleadoLogin([FromUri]string username, [FromUri]string pass)
        {
            using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
            {
                SqlCommand cmd = new SqlCommand("EXEC EMPLEADOLOGIN @user, @pass", conn);
                cmd.Parameters.AddWithValue("@user", username);
                cmd.Parameters.AddWithValue("@pass", pass);
                cmd.Connection = conn;
                conn.Open();
                using (var reader = cmd.ExecuteReader())
                {
                    if (reader.Read())
                    {
                        reader.Close();
                        return Request.CreateResponse(HttpStatusCode.Created, serial.Serialize(cmd.ExecuteReader()));
                    }
                    else
                    {
                        reader.Close();
                        return Request.CreateResponse(HttpStatusCode.BadRequest, false);
                    }
                }

            }
        }

        [HttpPost]
        public HttpResponseMessage regEmpleado([FromBody] empleadosModel empleado)
        {
            try
            {
                using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
                {
                    object user, cedula, estado;
                    SqlCommand cmd1;

                    cmd1 = new SqlCommand("SELECT Estado, idEmpleado, Username FROM EMPLEADO WHERE idEmpleado=@id OR Username=@user");
                    cmd1.Parameters.AddWithValue("@id", empleado.idEmpleado);
                    cmd1.Parameters.AddWithValue("@user", empleado.Username);
                    cmd1.Connection = conn;
                    conn.Open();
                    var reader = cmd1.ExecuteReader();
                    if (reader.Read())
                    {
                        reader.Close();
                        var data = serial.singleserialize(cmd1.ExecuteReader());
                        data.TryGetValue("Estado", out estado);
                        data.TryGetValue("idEmpleado", out cedula);
                        data.TryGetValue("Username", out user);
                        conn.Close();
                        if ((Convert.ToString(user).Equals(empleado.Username)) && (Convert.ToInt32(cedula) == empleado.idEmpleado) && (Convert.ToInt32(estado) == 1))
                        {
                            return Request.CreateResponse(HttpStatusCode.Conflict, "This user already exist!");
                        }
                        else if ((Convert.ToString(user).Equals(empleado.Username)) && (Convert.ToInt32(cedula) == empleado.idEmpleado) && (Convert.ToInt32(estado) == 0))
                        {
                            cmd1 = new SqlCommand("UPDATE EMPLEADO SET  Nombre=@nombre, pApellido=@papellido, sApellido=@sapellido, Password=@password, Email=@email, Nacimiento=@nacimiento, Estado=1 WHERE idEmpleado=@id", conn);
                            cmd1.Parameters.AddWithValue("@id", empleado.idEmpleado);
                            cmd1.Parameters.AddWithValue("@nombre", empleado.Nombre);
                            cmd1.Parameters.AddWithValue("@papellido", empleado.pApellido);
                            cmd1.Parameters.AddWithValue("@sapellido", empleado.sApellido);
                            cmd1.Parameters.AddWithValue("@password", empleado.Password);
                            cmd1.Parameters.AddWithValue("@email", empleado.Email);
                            cmd1.Parameters.AddWithValue("@nacimiento", empleado.Nacimiento);
                            cmd1.Connection = conn;
                            conn.Open();
                            cmd1.ExecuteReader();
                            var message = Request.CreateResponse(HttpStatusCode.Created, empleado);
                            conn.Close();
                            return message;
                        }
                        else if (Convert.ToInt32(cedula) == empleado.idEmpleado)
                        {
                            return Request.CreateResponse(HttpStatusCode.BadRequest, "Identification already exist!");
                        }
                        else if (Convert.ToString(user).Equals(empleado.Username))
                        {
                            return Request.CreateResponse(HttpStatusCode.BadGateway, "Username already exist!");
                        }

                        return null;
                    }
                    else
                    {
                        conn.Close();
                        SqlCommand cmd = new SqlCommand("EXEC REGEMPLEADO @cedula, @email, @username, @password, @nombre, @papellido, @sapellido, @nacimiento, @direccion, @estado, @rol, @sucursal", conn);
                        cmd.Parameters.AddWithValue("@cedula", empleado.idEmpleado);
                        cmd.Parameters.AddWithValue("@nombre", empleado.Nombre);
                        cmd.Parameters.AddWithValue("@papellido", empleado.pApellido);
                        cmd.Parameters.AddWithValue("@sapellido", empleado.sApellido);
                        cmd.Parameters.AddWithValue("@password", empleado.Password);
                        cmd.Parameters.AddWithValue("@username", empleado.Username);
                        cmd.Parameters.AddWithValue("@email", empleado.Email);
                        cmd.Parameters.AddWithValue("@nacimiento", empleado.Nacimiento);
                        cmd.Parameters.AddWithValue("@direccion", empleado.Direccion);
                        cmd.Parameters.AddWithValue("@estado", empleado.Estado);
                        cmd.Parameters.AddWithValue("@rol", empleado.idRol);
                        cmd.Parameters.AddWithValue("@sucursal", empleado.idSucursal);
                        cmd.Connection = conn;
                        conn.Open();
                        cmd.ExecuteReader();
                        var message = Request.CreateResponse(HttpStatusCode.Created, empleado);
                        return message;
                    }
                }
            }
            catch (Exception ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.NotFound, ex);
            }


        }
        [HttpPut]
        public HttpResponseMessage updateEmpleados(empleadosModel empleado)
        {
            try
            {
                using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
                {
                    SqlCommand cmd = new SqlCommand("UPDATE EMPLEADO SET Email=@email, Username=@username, Password=@password, Nombre=@nombre, pApellido=@papellido, sApellido=@sapellido, Nacimiento=@nacimiento, Direccion=@direccion WHERE idEmpleado=@id", conn);
                    cmd.Parameters.AddWithValue("@id", empleado.idEmpleado);
                    cmd.Parameters.AddWithValue("@email", empleado.Email);
                    cmd.Parameters.AddWithValue("@username", empleado.Username);
                    cmd.Parameters.AddWithValue("@password", empleado.Password);
                    cmd.Parameters.AddWithValue("@nombre", empleado.Nombre);
                    cmd.Parameters.AddWithValue("@papellido", empleado.pApellido);
                    cmd.Parameters.AddWithValue("@sapellido", empleado.sApellido);
                    cmd.Parameters.AddWithValue("@nacimiento", empleado.Nacimiento);
                    cmd.Parameters.AddWithValue("@direccion", empleado.Direccion);

                    cmd.Connection = conn;
                    conn.Open();
                    cmd.ExecuteReader();
                    var message = Request.CreateResponse(HttpStatusCode.Created, empleado);
                    return message;
                }
            }
            catch (Exception ex)
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, ex);
            }
        }

        [HttpDelete]
        public HttpResponseMessage deleteEmpleado([FromBody] empleadosModel del)
        {
            try
            {
                using (SqlConnection conn = new SqlConnection(DatabaseConnectionString))
                {
                    SqlCommand cmd = new SqlCommand("UPDATE  EMPLEADO SET  Estado=0 WHERE idEmpleado=@id", conn);
                    cmd.Parameters.AddWithValue("@id", del.idEmpleado);
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

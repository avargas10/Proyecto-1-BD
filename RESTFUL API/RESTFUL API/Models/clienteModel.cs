using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace RESTFUL_API.Models
{
    public class clienteModel
    {
        public int Cedula { get; set; }
        public string Nombre { get; set; }
        public string pApellido { get; set; }
        public string sApellido { get; set; }
        public string Password { get; set; }
        public string Username { get; set; }
        public string Email { get; set; }
        public Nullable<System.DateTime> Nacimiento { get; set; }
        public Nullable<int> Penalizacion { get; set; }
        public Nullable<int> Direccion { get; set; }
        public int Estado { get; set; }
        public int Telefono { get; set; }

    }
}
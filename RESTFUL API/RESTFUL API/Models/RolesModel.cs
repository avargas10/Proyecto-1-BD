using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace RESTFUL_API.Models
{
    public class RolesModel
    {
        public Nullable<int> idRol { get; set; }
        public String Nombre { get; set; }
        public String Descripcion { get; set; }
        public Nullable<int> Estado { get; set; }
        public Nullable<int> Empresa { get; set; }
    }
}
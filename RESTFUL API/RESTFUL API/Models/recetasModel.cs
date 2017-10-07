using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace RESTFUL_API.Models
{
    public class recetasModel
    {
        public int idReceta { get; set; }
        public Nullable<int> idCliente { get; set; }
        public string Imagen { get; set; }
        public int Estado { get; set; }
        public int idDoctor { get; set; }
    }
}
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace RESTFUL_API.Models
{
    public class padecimientosModel
    {
        public int idPadecimiento { get; set; }
        public int idUsuario { get; set; }
        public Nullable<System.DateTime> Fecha { get; set; }
        public string Nombre { get; set; }
        public string Descripcion { get; set; }
    }
}
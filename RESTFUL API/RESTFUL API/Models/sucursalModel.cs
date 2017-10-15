using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace RESTFUL_API.Models
{
    public class sucursalModel
    {
        public int idSucursal { get; set; }
        public Nullable<int> idEmpresa { get; set; }
        public Nullable<int> idProvincia { get; set; }
        public Nullable<int> idCanton { get; set; }
        public Nullable<int> idDistrito { get; set; }
        public Nullable<double> Latitud { get; set; }
        public Nullable<double> Longitud { get; set; }
        public string detalleDireccion { get; set; }
        public string Nombre { get; set; }
        public int Estado { get; set; }
        public string Imagen { get; set; }
    }
}
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace RESTFUL_API.Models
{
    public class productosModel
    {
        public int idProducto { get; set; }
        public Nullable<int> Proveedor { get; set; }
        public string Nombre { get; set; }
        public Nullable<int> esMedicamento { get; set; }
        public Nullable<int> reqPrescripcion { get; set; }
        public string Image { get; set; }
        public Nullable<int> Precio { get; set; }
        public int Estado { get; set; }
        public int Sucursal { get; set; }
        public int Cantidad { get; set; }
    }
}
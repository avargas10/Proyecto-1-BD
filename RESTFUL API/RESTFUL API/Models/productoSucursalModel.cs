using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace RESTFUL_API.Models
{
    public class productoSucursalModel
    {
        public int idSucursal { get; set; }
        public int codProducto { get; set; }
        public int Cantidad { get; set; }
        public int Precio { get; set; }
    }
}
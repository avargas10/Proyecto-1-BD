using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace RESTFUL_API.Models
{
    public class DetallePedidoModel
    {
        public Nullable<int> idProducto { get; set; }
        public Nullable<int> idPedido { get; set; }
        public Nullable<int> Cantidad { get; set; }
        public Nullable<int> idSucursal { get; set; }
    }
}
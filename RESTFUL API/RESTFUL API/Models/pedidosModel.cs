using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace RESTFUL_API.Models
{
    public class pedidosModel
    {
        public int idPedido { get; set; }
        public Nullable<int> sucursalRecojo { get; set; }
        public Nullable<int> idCliente { get; set; }
        public Nullable<System.DateTime> horaRecojo { get; set; }
        public Nullable<int> Telefono { get; set; }
        public String Imagen { get; set; }
        public Nullable<int> Estado { get; set; }
    }
}
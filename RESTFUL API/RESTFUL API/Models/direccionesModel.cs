using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace RESTFUL_API.Models
{
    public class direccionesModel
    {
        public int idDireccion { get; set; }
        public Nullable<int> Provincia { get; set; }
        public Nullable<int> Canton { get; set; }
        public Nullable<int> Distrito { get; set; }
        public string Descripcion { get; set; }
    }
}
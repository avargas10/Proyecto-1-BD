using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace RESTFUL_API.Models
{
    public class modelDistrito
    {
        public int idDistrito { get; set; }
        public Nullable<int> idCanton { get; set; }
        public string Nombre { get; set; }
    }
}
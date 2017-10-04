using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace RESTFUL_API.Models
{
    public class cantonModel
    {
        public int idCanton { get; set; }
        public Nullable<int> idProvincia { get; set; }
        public string Nombre { get; set; }
    }
}
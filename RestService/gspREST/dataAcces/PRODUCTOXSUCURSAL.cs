//------------------------------------------------------------------------------
// <auto-generated>
//     Este código se generó a partir de una plantilla.
//
//     Los cambios manuales en este archivo pueden causar un comportamiento inesperado de la aplicación.
//     Los cambios manuales en este archivo se sobrescribirán si se regenera el código.
// </auto-generated>
//------------------------------------------------------------------------------

namespace dataAcces
{
    using System;
    using System.Collections.Generic;
    
    public partial class PRODUCTOXSUCURSAL
    {
        public int idSucursal { get; set; }
        public int codProducto { get; set; }
        public Nullable<int> Cantidad { get; set; }
    
        public virtual PRODUCTO PRODUCTO { get; set; }
        public virtual SUCURSAL SUCURSAL { get; set; }
    }
}

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
    
    public partial class PEDIDO
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public PEDIDO()
        {
            this.DETALLEPEDIDOes = new HashSet<DETALLEPEDIDO>();
            this.DETALLEPEDIDOes1 = new HashSet<DETALLEPEDIDO>();
        }
    
        public int idPedido { get; set; }
        public Nullable<int> sucursalRecojo { get; set; }
        public Nullable<int> idCliente { get; set; }
        public Nullable<System.DateTime> horaRecojo { get; set; }
        public Nullable<int> Telefono { get; set; }
        public byte[] Imagen { get; set; }
        public string Estado { get; set; }
    
        public virtual CLIENTE CLIENTE { get; set; }
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<DETALLEPEDIDO> DETALLEPEDIDOes { get; set; }
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<DETALLEPEDIDO> DETALLEPEDIDOes1 { get; set; }
        public virtual SUCURSAL SUCURSAL { get; set; }
    }
}

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
    
    public partial class CANTON
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public CANTON()
        {
            this.DIRECCIONES = new HashSet<DIRECCIONE>();
            this.DISTRITOes = new HashSet<DISTRITO>();
            this.SUCURSALs = new HashSet<SUCURSAL>();
        }
    
        public int idCanton { get; set; }
        public Nullable<int> idProvincia { get; set; }
        public string Nombre { get; set; }
    
        public virtual PROVINCIA PROVINCIA { get; set; }
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<DIRECCIONE> DIRECCIONES { get; set; }
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<DISTRITO> DISTRITOes { get; set; }
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<SUCURSAL> SUCURSALs { get; set; }
    }
}

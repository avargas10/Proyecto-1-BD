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
    
    public partial class EMPLEADO
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public EMPLEADO()
        {
            this.EMPLEADOXSUCURSALs = new HashSet<EMPLEADOXSUCURSAL>();
        }
    
        public int idEmpleado { get; set; }
        public string Email { get; set; }
        public string Username { get; set; }
        public string Password { get; set; }
        public string Nombre { get; set; }
        public string pApellido { get; set; }
        public string sApellido { get; set; }
        public Nullable<System.DateTime> Nacimiento { get; set; }
        public Nullable<int> Direccion { get; set; }
    
        public virtual DIRECCIONE DIRECCIONE { get; set; }
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<EMPLEADOXSUCURSAL> EMPLEADOXSUCURSALs { get; set; }
    }
}

﻿//------------------------------------------------------------------------------
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
    using System.Data.Entity;
    using System.Data.Entity.Infrastructure;
    
    public partial class GasStationPharmacyDBEntities : DbContext
    {
        public GasStationPharmacyDBEntities()
            : base("name=GasStationPharmacyDBEntities")
        {
        }
    
        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            throw new UnintentionalCodeFirstException();
        }
    
        public virtual DbSet<CANTON> CANTONs { get; set; }
        public virtual DbSet<CLIENTE> CLIENTEs { get; set; }
        public virtual DbSet<DETALLEPEDIDO> DETALLEPEDIDOes { get; set; }
        public virtual DbSet<DETALLERECETA> DETALLERECETAs { get; set; }
        public virtual DbSet<DIRECCIONE> DIRECCIONES { get; set; }
        public virtual DbSet<DISTRITO> DISTRITOes { get; set; }
        public virtual DbSet<EMPLEADO> EMPLEADOes { get; set; }
        public virtual DbSet<EMPLEADOXSUCURSAL> EMPLEADOXSUCURSALs { get; set; }
        public virtual DbSet<EMPRESA> EMPRESAs { get; set; }
        public virtual DbSet<ESTADO> ESTADOS { get; set; }
        public virtual DbSet<PADECIMIENTO> PADECIMIENTOS { get; set; }
        public virtual DbSet<PEDIDO> PEDIDOS { get; set; }
        public virtual DbSet<PRODUCTO> PRODUCTOS { get; set; }
        public virtual DbSet<PRODUCTOXSUCURSAL> PRODUCTOXSUCURSALs { get; set; }
        public virtual DbSet<PROVEEDORE> PROVEEDORES { get; set; }
        public virtual DbSet<PROVINCIA> PROVINCIAs { get; set; }
        public virtual DbSet<RECETA> RECETAS { get; set; }
        public virtual DbSet<ROLE> ROLES { get; set; }
        public virtual DbSet<SUCURSAL> SUCURSALs { get; set; }
    }
}

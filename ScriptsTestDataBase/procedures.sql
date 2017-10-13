CREATE PROC VERIFCANTIDAD(
@idProducto int,
@Sucursal int,
@cantidad int
)
as
if (select Cantidad from PRODUCTOXSUCURSAL Where idSucursal=@Sucursal AND codProducto=@idProducto)<(@cantidad)
 BEGIN
  select Cantidad from PRODUCTOXSUCURSAL Where idSucursal=@Sucursal AND codProducto=@idProducto AND Cantidad>=@cantidad
  END
 else
 BEGIN
 select Cantidad from PRODUCTOXSUCURSAL Where idSucursal=@Sucursal AND codProducto=@idProducto AND Cantidad>=@cantidad
 END
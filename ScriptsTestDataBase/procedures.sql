CREATE PROC VERIFCANTIDAD(
@idProducto int,
@Sucursal int,
@cantidad int
)
AS
BEGIN
if (SELECT Cantidad FROM PRODUCTOXSUCURSAL WHERE idSucursal=@Sucursal AND codProducto=@idProducto)<(@cantidad)
BEGIN
SELECT Cantidad FROM PRODUCTOXSUCURSAL WHERE idSucursal=@Sucursal AND codProducto=@idProducto AND Cantidad>=@cantidad
END
else
BEGIN
SELECT Cantidad FROM PRODUCTOXSUCURSAL WHERE idSucursal=@Sucursal AND codProducto=@idProducto AND Cantidad>=@cantidad
END
END

CREATE PROC CREATEDETALLEPEDIDO(
@producto int,
@pedido int,
@sucursal int,
@cantidad int
)
AS
BEGIN
INSERT INTO DETALLEPEDIDO (idProducto, idPedido, Cantidad) VALUES (@producto, @pedido, @cantidad)
UPDATE PRODUCTOXSUCURSAL SET Cantidad=(Cantidad-@cantidad) WHERE idSucursal=@sucursal AND codProducto=@producto
END
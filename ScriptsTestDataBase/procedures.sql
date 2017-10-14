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


CREATE PROC DELETEPEDIDO(
@idPedido int
)
AS
BEGIN
DECLARE @sucursal int
DECLARE @RowCnt int
DECLARE @tableSize int 
DECLARE @cedula int
DECLARE @estado int
SET @estado = (SELECT Estado FROM PEDIDOS WHERE idPedido=@idPedido)
if((@estado!=5) AND (@estado!=4))
BEGIN
SET @RowCnt=0
CREATE TABLE #tempTable (producto int , cantidad int)
SELECT @sucursal=sucursalRecojo FROM PEDIDOS WHERE idPedido=@idPedido
INSERT INTO #tempTable (producto, cantidad) (SELECT idProducto, Cantidad FROM DETALLEPEDIDO WHERE idPedido=@idPedido)
SET @tableSize = (SELECT COUNT(*) FROM #tempTable) 
WHILE @RowCnt<@tableSize
BEGIN
SET @RowCnt=@RowCnt+1
UPDATE PRODUCTOXSUCURSAL SET Cantidad=Cantidad+(SELECT TOP 1 cantidad FROM #tempTable) WHERE idSucursal=@sucursal AND codProducto=(SELECT TOP 1 producto FROM #tempTable)
DELETE TOP (1) FROM #tempTable 
END 
UPDATE PEDIDOS SET Estado=5 WHERE idPedido=@idPedido
IF ((@estado=2) OR (@estado=3)) 
BEGIN
SET @cedula= (SELECT idCliente FROM PEDIDOS WHERE idPedido=@idPedido)
UPDATE CLIENTE SET Penalizacion=(Penalizacion+1) WHERE Cedula=@cedula
END
END
END
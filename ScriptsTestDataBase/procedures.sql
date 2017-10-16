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


CREATE PROC UPDATEDETALLEPEDIDO(
@producto int,
@pedido int,
@sucursal int,
@cantidad int
)
AS
BEGIN
DECLARE @cant int
SELECT @cant=Cantidad FROM DETALLEPEDIDO WHERE idPedido=@pedido AND idProducto=@producto
UPDATE DETALLEPEDIDO SET Cantidad=@cantidad WHERE idPedido=@pedido AND idProducto=@producto
UPDATE PRODUCTOXSUCURSAL SET Cantidad=(Cantidad+@cant) WHERE idSucursal=@sucursal AND codProducto=@producto
UPDATE PRODUCTOXSUCURSAL SET Cantidad=(Cantidad-@cantidad) WHERE idSucursal=@sucursal AND codProducto=@producto
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


CREATE PROC REGEMPLEADO(
@idEmpleado int,
@Email varchar(80),
@Username varchar(50),
@Password varchar(50),
@Nombre varchar(50),
@pApellido varchar(50),
@sApellido varchar(50),
@Nacimiento varchar(50),
@Direccion int,
@Estado int,
@idRol int,
@idSucursal int
)
AS
BEGIN
INSERT INTO EMPLEADO(idEmpleado, Email, Username, Password, Nombre, pApellido, sApellido, Nacimiento, Direccion, Estado ) VALUES (@idEmpleado, @Email, @Username, @Password, @Nombre, @pApellido, @sApellido, @Nacimiento,@Direccion,@Estado)
INSERT INTO EMPLEADOXSUCURSAL(idSucursal, idEmpleado, idRol) VALUES (@idSucursal, @idEmpleado, @idRol)
END


CREATE PROC EMPLEADOLOGIN(
@Username varchar(50),
@Password varchar(50)
)
AS
BEGIN
IF EXISTS (SELECT Username FROM EMPLEADO WHERE Username=@Username AND Password=@Password)
BEGIN
DECLARE @sucursal int
DECLARE @empleado int
SELECT @empleado=idEmpleado FROM EMPLEADO WHERE Username=@Username
SELECT @sucursal=idSucursal FROM EMPLEADOXSUCURSAL WHERE idEmpleado=@empleado
SELECT idEmpresa FROM SUCURSAL WHERE idSucursal=@sucursal
END
END

CREATE PROC GETESTADISTICA(
@idEmpresa int
)
AS
BEGIN
SELECT EMPRESA.Nombre AS nombreEmpresa,  SUCURSAL.Nombre AS nombreSucursal, PRODUCTOS.Nombre AS nombreProducto, PRODUCTOS.idProducto, SUM(DETALLEPEDIDO.Cantidad) AS sumaCantidad FROM PRODUCTOS INNER JOIN DETALLEPEDIDO
ON PRODUCTOS.idProducto=DETALLEPEDIDO.idProducto INNER JOIN PEDIDOS ON DETALLEPEDIDO.idPedido=PEDIDOS.idPedido INNER JOIN SUCURSAL ON SUCURSAL.idSucursal=PEDIDOS.sucursalRecojo 
INNER JOIN EMPRESA ON SUCURSAL.idEmpresa=EMPRESA.idEmpresa WHERE(EMPRESA.idEmpresa=@idEmpresa) GROUP BY EMPRESA.Nombre, SUCURSAL.Nombre, PRODUCTOS.Nombre, PRODUCTOS.idProducto 
END
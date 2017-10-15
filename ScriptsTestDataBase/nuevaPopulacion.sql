INSERT INTO PROVINCIA ( Nombre) VALUES ('San Jose');
INSERT INTO PROVINCIA ( Nombre) VALUES ('Cartago');
INSERT INTO PROVINCIA ( Nombre) VALUES ('Heredia');
INSERT INTO PROVINCIA ( Nombre) VALUES ('Alajuela');

INSERT INTO CANTON ( idProvincia, Nombre) VALUES ( 1, 'Escazu');
INSERT INTO CANTON ( idProvincia, Nombre) VALUES ( 1, 'Desamparados');
INSERT INTO CANTON ( idProvincia, Nombre) VALUES ( 2,'Paraiso');
INSERT INTO CANTON ( idProvincia, Nombre) VALUES ( 2,'El Guarco');
INSERT INTO CANTON ( idProvincia, Nombre) VALUES ( 3,'Flores');
INSERT INTO CANTON ( idProvincia, Nombre) VALUES ( 3,'Santo Domingo');
INSERT INTO CANTON ( idProvincia, Nombre) VALUES ( 4,'San Ramon');
INSERT INTO CANTON ( idProvincia, Nombre) VALUES ( 4,'Grecia');

INSERT INTO DISTRITO (idCanton, Nombre) VALUES ( 1, 'San Antonio');
INSERT INTO DISTRITO ( idCanton, Nombre) VALUES ( 1, 'San Rafael');
INSERT INTO DISTRITO ( idCanton, Nombre) VALUES ( 2, 'San Miguel');
INSERT INTO DISTRITO ( idCanton, Nombre) VALUES ( 2, 'Patarra');
INSERT INTO DISTRITO ( idCanton, Nombre) VALUES ( 3, 'Cachi');
INSERT INTO DISTRITO ( idCanton, Nombre) VALUES ( 3, 'Orosi');
INSERT INTO DISTRITO ( idCanton, Nombre) VALUES ( 4, 'Tejar');
INSERT INTO DISTRITO ( idCanton, Nombre) VALUES ( 4, 'San Isidro');
INSERT INTO DISTRITO ( idCanton, Nombre) VALUES ( 5, 'San Joaquin');
INSERT INTO DISTRITO ( idCanton, Nombre) VALUES ( 5, 'Barrantes');
INSERT INTO DISTRITO ( idCanton, Nombre) VALUES ( 6, 'Santa Rosa');
INSERT INTO DISTRITO ( idCanton, Nombre) VALUES ( 6, 'Santo Tomas');
INSERT INTO DISTRITO ( idCanton, Nombre) VALUES ( 7, 'Santiago');
INSERT INTO DISTRITO ( idCanton, Nombre) VALUES ( 7, 'Piedades');
INSERT INTO DISTRITO ( idCanton, Nombre) VALUES ( 8, 'Rio Cuarto');
INSERT INTO DISTRITO ( idCanton, Nombre) VALUES ( 8, 'Bolivar');

INSERT INTO EMPRESA(Nombre) VALUES ('Phischel');
INSERT INTO EMPRESA(Nombre) VALUES ('BombaTica');

INSERT INTO SUCURSAL ( idEmpresa,idProvincia, idCanton, idDistrito, detalleDireccion, Nombre, Estado) VALUES (2,1, 1, 1, 'Centro Escazu', 'Bomba Escazu',1);
INSERT INTO SUCURSAL ( idEmpresa,idProvincia, idCanton, idDistrito, detalleDireccion, Nombre, Estado) VALUES (2,2, 3, 5,'Centro de Desamparados', 'Bomba Alajuela',1);
INSERT INTO SUCURSAL ( idEmpresa,idProvincia, idCanton, idDistrito, detalleDireccion, Nombre, Estado) VALUES (1,3, 5, 9,'Phischel Mall', 'Phischel Mall San Pedro',1);
INSERT INTO SUCURSAL ( idEmpresa,idProvincia, idCanton, idDistrito, detalleDireccion, Nombre, Estado) VALUES (1,4, 7, 14,'Phischel Central', 'Phischel San Jose', 1);

INSERT INTO DIRECCIONES(Provincia, Canton, Distrito, Descripcion) VALUES ( 1,1,2, 'Residencial Monterrey');
INSERT INTO DIRECCIONES(Provincia, Canton, Distrito, Descripcion) VALUES ( 2,3,5,'Condomio los Pirulos');
INSERT INTO DIRECCIONES(Provincia, Canton, Distrito, Descripcion) VALUES ( 3,5,9,'100 mts al sur de la reforma');
INSERT INTO DIRECCIONES(Provincia, Canton, Distrito, Descripcion) VALUES ( 4,7,13,'Residencial el rey');
INSERT INTO DIRECCIONES(Provincia, Canton, Distrito, Descripcion) VALUES ( 1,2,3, 'Residencial Nuevo Milenio');
INSERT INTO DIRECCIONES(Provincia, Canton, Distrito, Descripcion) VALUES ( 2,4,7,'contiguo a Tango india');
INSERT INTO DIRECCIONES(Provincia, Canton, Distrito, Descripcion) VALUES ( 3,6,11, 'residencial Cartago');
INSERT INTO DIRECCIONES(Provincia, Canton, Distrito, Descripcion) VALUES ( 4,8,15,'Residencial el rey');

INSERT INTO ROLES ( Nombre, Descripcion, Empresa, Estado) VALUES ('Ingeniero','Admin de la app', 1, 1);
INSERT INTO ROLES ( Nombre, Descripcion, Empresa, Estado) VALUES ('Cajero','Maneja diner en la farmacia', 2, 1);
INSERT INTO ROLES ( Nombre, Descripcion, Empresa, Estado) VALUES ('Farmaceutico','Vendedores especializados en medicina', 1, 1);
INSERT INTO ROLES ( Nombre, Descripcion, Empresa, Estado) VALUES ('Administrador','Administra farmacia', 2, 1);
INSERT INTO ROLES ( Nombre, Descripcion, Empresa, Estado) VALUES ('Doctor','Medico general', 1, 1);

INSERT INTO EMPLEADO (idEmpleado, Email, Nombre, pApellido, sApellido, Password, Username, Nacimiento, Direccion, Estado) VALUES (1, 'rsolano1996@gmail.com','Rodolfo','Solano','Asenjo','123', 'rsolano', '1996-12-12', 1,1 );
INSERT INTO EMPLEADO (idEmpleado, Email, Nombre, pApellido, sApellido, Password, Username, Nacimiento, Direccion, Estado) VALUES (2,'marcfg29@gmail.com','Marco','Fernandez','Apellido2','123', 'mfernandez', '1996-12-12', 2, 1);
INSERT INTO EMPLEADO (idEmpleado, Email, Nombre, pApellido, sApellido, Password, Username, Nacimiento, Direccion, Estado) VALUES (3,'blabla@gmail.com','Gafeth','Rodriguez','Sanchez','123', 'ghernandez', '1996-12-12', 3, 1);
INSERT INTO EMPLEADO (idEmpleado, Email, Nombre, pApellido, sApellido, Password, Username, Nacimiento, Direccion, Estado) VALUES (4,'adriansanchez2015101969@gmail.com','Adrian','Sanchez','Anderson','pato123', 'asanchez', '1994-03-12', 4, 1);
INSERT INTO EMPLEADO (idEmpleado, Email, Nombre, pApellido, sApellido, Password, Username, Nacimiento, Direccion, Estado) VALUES (5,'afelipe.vargas.r@gmail.com','Andres','Vargas','Rivera','123', 'avargas', '1996-12-12', 5, 1);


INSERT INTO EMPLEADOXSUCURSAL(idSucursal, idEmpleado, idRol) VALUES (1, 1, 1);
INSERT INTO EMPLEADOXSUCURSAL(idSucursal, idEmpleado, idRol) VALUES (2, 2, 2);
INSERT INTO EMPLEADOXSUCURSAL(idSucursal, idEmpleado, idRol) VALUES (3, 3, 3);
INSERT INTO EMPLEADOXSUCURSAL(idSucursal, idEmpleado, idRol) VALUES (4, 4, 1);
INSERT INTO EMPLEADOXSUCURSAL(idSucursal, idEmpleado, idRol) VALUES (1, 5, 2);
INSERT INTO EMPLEADOXSUCURSAL(idSucursal, idEmpleado, idRol) VALUES (2, 1, 3);
INSERT INTO EMPLEADOXSUCURSAL(idSucursal, idEmpleado, idRol) VALUES (3, 2, 1);
INSERT INTO EMPLEADOXSUCURSAL(idSucursal, idEmpleado, idRol) VALUES (4, 3, 2);
INSERT INTO EMPLEADOXSUCURSAL(idSucursal, idEmpleado, idRol) VALUES (1, 4, 3);
INSERT INTO EMPLEADOXSUCURSAL(idSucursal, idEmpleado, idRol) VALUES (2, 5, 1);

INSERT INTO PROVEEDORES (idProveedor, Nombre, Telefono) VALUES (0,'Bayern', 22550890);
INSERT INTO PROVEEDORES (idProveedor, Nombre, Telefono) VALUES (1,'Playboy', 22733665);
INSERT INTO PROVEEDORES (idProveedor, Nombre, Telefono) VALUES (2,'Distribuidor X', 22360911);
INSERT INTO PROVEEDORES (idProveedor, Nombre, Telefono) VALUES (3,'GSK', 22795413);


INSERT INTO PRODUCTOS(idProducto,Proveedor,Nombre,esMedicamento,reqPrescripcion, Estado) VALUES (0,0,'talerdin',1,0,1);
INSERT INTO PRODUCTOS(idProducto,Proveedor,Nombre,esMedicamento,reqPrescripcion, Estado) VALUES (1,0,'antifludes',1,0, 1);
INSERT INTO PRODUCTOS(idProducto,Proveedor,Nombre,esMedicamento,reqPrescripcion, Estado) VALUES (2,0,'tabcin',1,0, 1);
INSERT INTO PRODUCTOS(idProducto,Proveedor,Nombre,esMedicamento,reqPrescripcion, Estado) VALUES (3,0,'gex',1,0, 1);
INSERT INTO PRODUCTOS(idProducto,Proveedor,Nombre,esMedicamento,reqPrescripcion, Estado) VALUES (4,1,'condones',0,0, 1);
INSERT INTO PRODUCTOS(idProducto,Proveedor,Nombre,esMedicamento,reqPrescripcion, Estado) VALUES (5,2,'bendas',0,0, 1);
INSERT INTO PRODUCTOS(idProducto,Proveedor,Nombre,esMedicamento,reqPrescripcion, Estado) VALUES (6,3,'suero x',1,1, 1);
INSERT INTO PRODUCTOS(idProducto,Proveedor,Nombre,esMedicamento,reqPrescripcion, Estado) VALUES (7,3,'Ibuprofeno',1,1, 1);
INSERT INTO PRODUCTOS(idProducto,Proveedor,Nombre,esMedicamento,reqPrescripcion, Estado) VALUES (8,1,'cigarros',0,0, 1);
INSERT INTO PRODUCTOS(idProducto,Proveedor,Nombre,esMedicamento,reqPrescripcion, Estado) VALUES (9,2,'chicles',0,0, 1);
INSERT INTO PRODUCTOS(idProducto,Proveedor,Nombre,esMedicamento,reqPrescripcion, Estado) VALUES (10,0,'cataflan',1,0, 1);

INSERT INTO PRODUCTOXSUCURSAL(idSucursal,codProducto,Cantidad,Precio) VALUES (1,1,10,500);
INSERT INTO PRODUCTOXSUCURSAL(idSucursal,codProducto,Cantidad,Precio) VALUES (1,2,10,1200);
INSERT INTO PRODUCTOXSUCURSAL(idSucursal,codProducto,Cantidad,Precio) VALUES (1,3,10,250);
INSERT INTO PRODUCTOXSUCURSAL(idSucursal,codProducto,Cantidad,Precio) VALUES (1,4,10,2200);
INSERT INTO PRODUCTOXSUCURSAL(idSucursal,codProducto,Cantidad,Precio) VALUES (1,5,10,100);
INSERT INTO PRODUCTOXSUCURSAL(idSucursal,codProducto,Cantidad,Precio) VALUES (1,6,10,400);
INSERT INTO PRODUCTOXSUCURSAL(idSucursal,codProducto,Cantidad,Precio) VALUES (1,7,10,120);
INSERT INTO PRODUCTOXSUCURSAL(idSucursal,codProducto,Cantidad,Precio) VALUES (1,8,10,875);
INSERT INTO PRODUCTOXSUCURSAL(idSucursal,codProducto,Cantidad,Precio) VALUES (1,9,10,525);
INSERT INTO PRODUCTOXSUCURSAL(idSucursal,codProducto,Cantidad,Precio) VALUES (1,10,10,330);
INSERT INTO PRODUCTOXSUCURSAL(idSucursal,codProducto,Cantidad,Precio) VALUES (2,0,10,1200);


INSERT INTO CLIENTE(Cedula,Nombre,pApellido,sApellido,Password,Username,Email,Nacimiento,Penalizacion, Direccion, Estado, Telefono) VALUES (100020354,'Marvin','Fonseca','Fernandez','123','mfonseca','a@gmail.com','2000-09-10',0,1, 1,8888888);
INSERT INTO CLIENTE(Cedula,Nombre,pApellido,sApellido,Password,Username,Email,Nacimiento,Penalizacion, Direccion, Estado, Telefono) VALUES (113459876,'Manuel','Calderon','Arango','123','mcalderon','a@gmail.com','2001-10-10',0,2,1, 8888888);
INSERT INTO CLIENTE(Cedula,Nombre,pApellido,sApellido,Password,Username,Email,Nacimiento,Penalizacion, Direccion, Estado, Telefono) VALUES (309990888,'Valeria','Artavia','Meneces','123','vartavia','a@gmail.com','1988-04-09',0,3,1, 8888888);
INSERT INTO CLIENTE(Cedula,Nombre,pApellido,sApellido,Password,Username,Email,Nacimiento,Penalizacion, Direccion, Estado, Telefono) VALUES (234789465,'Felix','Meneces','Garzona','123','FMG','a@gmail.com','1999-03-21',0,4,1, 8888888);
INSERT INTO CLIENTE(Cedula,Nombre,pApellido,sApellido,Password,Username,Email,Nacimiento,Penalizacion, Direccion, Estado, Telefono) VALUES (203940576,'Alejandra','Rivas','Vega','123','ARV','a@gmail.com','1976-01-11',0,5,1, 8888888);


INSERT INTO PADECIMIENTOS( idUsuario, Fecha, Nombre, Descripcion) VALUES ( 100020354, '2017-09-20', 'Asma', 'me ahogue');
INSERT INTO PADECIMIENTOS( idUsuario, Fecha, Nombre, Descripcion) VALUES ( 113459876, '2009-09-22', 'Fiebre', 'temblaba de frio');
INSERT INTO PADECIMIENTOS( idUsuario, Fecha, Nombre, Descripcion) VALUES ( 309990888, '2011-02-07', 'Varicela', 'me picaba mucho');
INSERT INTO PADECIMIENTOS( idUsuario, Fecha, Nombre, Descripcion) VALUES ( 234789465, '2005-12-03', 'Pie de Atleta', 'me picaba mucho');
INSERT INTO PADECIMIENTOS( idUsuario, Fecha, Nombre, Descripcion) VALUES ( 203940576, '2008-04-30', 'Esclerodermia', 'me dolia la espalda');

INSERT INTO ESTADOS(Nombre) VALUES ('Nuevo');
INSERT INTO ESTADOS(Nombre) VALUES ('Preparado');
INSERT INTO ESTADOS(Nombre) VALUES ('Facturado');
INSERT INTO ESTADOS(Nombre) VALUES ('Retirado');
INSERT INTO ESTADOS(Nombre) VALUES ('Inactivo');
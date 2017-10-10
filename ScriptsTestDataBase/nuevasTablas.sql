create table CLIENTE(
  Cedula INT NOT NULL,
  Nombre VARCHAR(30),
  pApellido VARCHAR(30),
  sApellido VARCHAR(30),
  Password VARCHAR(50),
  Username VARCHAR(50),
  Email VARCHAR(80),
  Nacimiento DATE,
  Penalizacion INT,
  Direccion INT,
  Estado INT,
  PRIMARY KEY (Cedula),
);

create table PADECIMIENTOS(
	idPadecimiento INT NOT NULL IDENTITY(1,1),
  idUsuario INT NOT NULL,
  Fecha DATE,
  Nombre VARCHAR(50),
  Descripcion VARCHAR(200),
  PRIMARY KEY (idPadecimiento)

);

create table ESTADOS(
	idEstado INT NOT NULL IDENTITY(1,1),
  Nombre VARCHAR(20),
  PRIMARY KEY (idEstado)

);

create table DIRECCIONES(
  idDireccion INT NOT NULL IDENTITY(1,1),
  Provincia INT,
  Canton INT,
  Distrito INT,
  Descripcion VARCHAR(200),
  PRIMARY KEY(idDireccion),
);

create table PEDIDOS(			
	idPedido INT NOT NULL IDENTITY(1,1),
  sucursalRecojo INT,
  idCliente INT,
  horaRecojo DATETIME,
  Telefono INT,
  Imagen VARCHAR(MAX),
  Estado INT,
	PRIMARY KEY (idPedido)
);

create table SUCURSAL(
	idSucursal INT NOT NULL IDENTITY(1,1),
  idEmpresa INT,
  idProvincia INT,
  idCanton INT,
  idDistrito INT,
  Latitud FLOAT,
  Longitud FLOAT,
  detalleDireccion VARCHAR(200),
  Nombre VARCHAR(80),
  Estado INT,
  Imagen VARCHAR(MAX),
  PRIMARY KEY(idSucursal)
);

create table PRODUCTOS(
	idProducto INT NOT NULL,
  Proveedor INT,
  Nombre VARCHAR(80),
  esMedicamento INT,
  reqPrescripcion INT,
  Image VARCHAR(MAX),
  Estado INT,
  PRIMARY KEY(idProducto)
);

create table PROVEEDORES(
  idProveedor INT NOT NULL,
  Nombre VARCHAR(80),
  Telefono INT,
  PRIMARY KEY(idProveedor)
);

create table RECETAS(
	idReceta INT NOT NULL IDENTITY(1,1),
  idCliente INT,
  Imagen VARCHAR(MAX),
  Estado INT,
  idDoctor INT,
  PRIMARY KEY(idReceta)
);

create table EMPRESA(
	idEmpresa INT NOT NULL IDENTITY(1,1),
  Nombre VARCHAR(50),
  PRIMARY KEY(idEmpresa)
);

create table EMPLEADO(
	idEmpleado INT NOT NULL,
  Email VARCHAR(80),
  Username VARCHAR(50),
  Password VARCHAR(50),
  Nombre VARCHAR(50),
  pApellido VARCHAR(50),
  sApellido VARCHAR(50),
  Nacimiento DATE,
  Direccion INT,
  Estado INT,
  PRIMARY KEY(idEmpleado)
);

create table ROLES(
	idRol INT NOT NULL IDENTITY(1,1),
  Nombre VARCHAR(20),
  Descripcion VARCHAR(100),
  PRIMARY KEY(idRol)
);

create table DISTRITO(
	idDistrito INT NOT NULL IDENTITY(1,1),
  idCanton INT,
  Nombre VARCHAR(40),
  PRIMARY KEY(idDistrito)
);

create table CANTON(
	idCanton INT NOT NULL IDENTITY(1,1),
  idProvincia INT,
  Nombre VARCHAR(40),
  PRIMARY KEY(idCanton)
);

create table PROVINCIA(
	idProvincia INT NOT NULL IDENTITY(1,1),
  Nombre VARCHAR(40),
  PRIMARY KEY(idProvincia)
);







create table EMPLEADOXSUCURSAL(
	idSucursal INT NOT NULL,
  idEmpleado INT NOT NULL,
  idRol INT NOT NULL,
  PRIMARY KEY(idSucursal, idEmpleado)
);

create table PRODUCTOXSUCURSAL(
	idSucursal INT NOT NULL,
  codProducto INT NOT NULL,
  Cantidad INT,
  Precio INT,
  PRIMARY KEY(idSucursal, codProducto)
);

create table DETALLEPEDIDO(
	idProducto INT NOT NULL,
  idPedido INT NOT NULL,
  Cantidad INT,
  PRIMARY KEY(idProducto, idPedido)
);

create table DETALLERECETA(
	idMedicamento INT NOT NULL,
  idReceta INT NOT NULL,
  PRIMARY KEY(idMedicamento, idReceta)
);









ALTER TABLE EMPLEADOXSUCURSAL
ADD FOREIGN KEY(idSucursal)
REFERENCES SUCURSAL(idSucursal)

ALTER TABLE EMPLEADOXSUCURSAL
ADD FOREIGN KEY(idEmpleado)
REFERENCES EMPLEADO(idEmpleado)

ALTER TABLE EMPLEADOXSUCURSAL
ADD FOREIGN KEY(idRol)
REFERENCES ROLES(idRol)

ALTER TABLE PRODUCTOXSUCURSAL
ADD FOREIGN KEY(idSucursal)
REFERENCES SUCURSAL(idSucursal)

ALTER TABLE PRODUCTOXSUCURSAL
ADD FOREIGN KEY(codProducto)
REFERENCES PRODUCTOS(idProducto)

ALTER TABLE DETALLEPEDIDO
ADD FOREIGN KEY(idProducto)
REFERENCES PRODUCTOS(idProducto)

ALTER TABLE DETALLEPEDIDO
ADD FOREIGN KEY(idPedido)
REFERENCES PEDIDOS(idPedido)

ALTER TABLE DETALLERECETA
ADD FOREIGN KEY(idMedicamento)
REFERENCES PRODUCTOS(idProducto)

ALTER TABLE DETALLERECETA
ADD FOREIGN KEY(idReceta)
REFERENCES RECETAS(idReceta)

ALTER TABLE PEDIDOS
ADD FOREIGN KEY(Estado)
REFERENCES ESTADOS(idEstado)





ALTER TABLE EMPLEADO
ADD FOREIGN KEY(Direccion)
REFERENCES DIRECCIONES(idDireccion)

ALTER TABLE PADECIMIENTOS
ADD FOREIGN KEY(idUsuario)
REFERENCES CLIENTE(Cedula)

ALTER TABLE DIRECCIONES
ADD FOREIGN KEY(Distrito)
REFERENCES DISTRITO(idDistrito)

ALTER TABLE DIRECCIONES
ADD FOREIGN KEY(Canton)
REFERENCES CANTON(idCanton)

ALTER TABLE DIRECCIONES
ADD FOREIGN KEY(Provincia)
REFERENCES Provincia(idProvincia)

ALTER TABLE CLIENTE
ADD FOREIGN KEY(Direccion)
REFERENCES DIRECCIONES(idDireccion)

ALTER TABLE PEDIDOS
ADD FOREIGN KEY(sucursalRecojo)
REFERENCES SUCURSAL(idSucursal)

ALTER TABLE PEDIDOS
ADD FOREIGN KEY(idCliente)
REFERENCES CLIENTE(Cedula)

ALTER TABLE SUCURSAL
ADD FOREIGN KEY(idEmpresa)
REFERENCES EMPRESA(idEmpresa)

ALTER TABLE SUCURSAL
ADD FOREIGN KEY(idProvincia)
REFERENCES Provincia(idProvincia)

ALTER TABLE SUCURSAL
ADD FOREIGN KEY(idCanton)
REFERENCES CANTON(idCanton)

ALTER TABLE SUCURSAL
ADD FOREIGN KEY(idDistrito)
REFERENCES DISTRITO(idDistrito)

ALTER TABLE PRODUCTOS
ADD FOREIGN KEY(Proveedor)
REFERENCES PROVEEDORES(idProveedor)

ALTER TABLE RECETAS
ADD FOREIGN KEY(IdCliente)
REFERENCES CLIENTE(Cedula)

ALTER TABLE DISTRITO
ADD FOREIGN KEY(idCanton)
REFERENCES CANTON(idCanton)

ALTER TABLE CANTON
ADD FOREIGN KEY(idProvincia)
REFERENCES PROVINCIA(idProvincia)

var globalImage="";
var app = angular.module("mainModule",
  ["ngRoute",
    'vcRecaptcha']);

app.config(function ( $routeProvider, $locationProvider) {
  $routeProvider.
    when('/Home', { templateUrl: '../Views/home-view.html', controller: 'mainController' }).
    when('/login', { templateUrl: '../Views/login-view.html', controller: 'userController' }).
    when('/userInfo', { templateUrl: '../Views/userInfo.html', controller: 'userController' }).
    when('/register', { templateUrl: '../Views/register.html', controller: 'userController' }).
    when('/pedido', { templateUrl: '../Views/pedido.html', controller: 'userController' }).
    when('/products', { templateUrl: '../Views/client.html', controller: 'userController' }).
    when('/stores', { templateUrl: '../Views/stores.html', controller: 'storeController' }).
    when('/storeProducts', { templateUrl: '../Views/storeProducts.html', controller: 'storeController' }).
    when('/myBag', { templateUrl: '../Views/myBag.html', controller: 'storeController' }).
    when('/order', { templateUrl: '../Views/order.html', controller: 'orderController' }).
    when('/allOrders', { templateUrl: '../Views/allOrders.html', controller: 'orderController' }).
    when('/accountVer', { templateUrl: '../Views/account.html', controller: 'userController' }).
    when('/prescription', { templateUrl: '../Views/prescription.html', controller: 'receipeController' }).
    when('/allForms', { templateUrl: '../Views/allForms.html', controller: 'receipeController' }).
    otherwise({ redirectTo: '/Home' });
  // $locationProvider.html5Mode(true);
});

app.service('userService', function () {
  var currentUser;
  var logged;
  this.getUser = function () {
    return currentUser;
  }
  this.getActive = function () {
    return logged;
  }

  this.setUser = function (user) {
    currentUser = user;
    logged = true;
  }
  this.logout = function () {
    console.log(currentUser, " logged out");
    currentUser = null;
    logged = false;
  }
});

app.service('directionService', function () {
  var state;
  var district;
  var city;
  var id;

  this.setId = function (pId) {
    console.log("id: " + pId);
    id = pId;
  }
  this.getId = function () { return id; }
  this.setState = function (pState) {
    state = pState;
  }
  this.setCity = function (pCity) {
    city = pCity;
  }
  this.setDisctrict = function (pDistrict) {
    district = pDistrict;
  }
  this.getState = function () { return state; }
  this.getCity = function () { return city; }
  this.getDistrict = function () { return district; }

});
app.service('storeService', function () {
  var store;
  var bag = [];
  var totalPrice;
  this.setTotalPrice = function (pPrice) {
    totalPrice = pPrice;
  }
  this.cleanBag= function(){bag = [];}
  this.isEmpty = function(){
    if(bag.length==0){return true;}
    return false;
  }
  this.setStore = function (pStore) {
    store = pStore;
  }
  this.setBag = function (pBag) {
    console.log("bag: "+pBag);
    bag = pBag;
  }
  this.getBag=function(){
    console.log(bag);
    return bag;}
  
    this.getTotalPrice=function(){return totalPrice;}
  
  this.addToBag=function(product){
    bag.push(product);
    console.log(bag);
  }
  this.getStore = function () { return store; }
});
app.service('orderService',function(){
 var newOrder = 
 {
   idSucursal:null,
   phoneNumber:null,
   idClient:null,
   image:null,
   date:null,
 };
 this.setImage=function(image){
    newOrder.image = image;
 }

 this.clean=function(){
   newOrder= {
    idSucursal:null,
    phoneNumber:null,
    idClient:nul,
    image:null,
    date:null
  };
  imageActive=false;
 }

 this.getOrder=function(){return newOrder;}

 this.setValues = function(Sucursal,phoneNumber,idClient,date){
   newOrder.idSucursal=Sucursal;
   newOrder.phoneNumber=phoneNumber;
   newOrder.idClient=idClient;
   newOrder.date=date;
 }


});

app.service('receipeService',function(){
  var meds=[];
  this.medsEmpty = function(){
    if(meds.length==0){
      return true;
    }
    else{return false;}
  }
  this.addMed=function(med){
     meds.push(med);
     console.log("Med "+med);
  }
 
  this.cleanMeds=function(){
    meds = [];
    console.log("meds clean");
  }
 
  this.setMeds=function(pMeds){
    meds = pMeds; 
  }

  this.getMeds=function(){return meds;}
  
 });


app.controller("mainController", ["$scope", "$http", "$location", "$routeParams", 'userService',

  function ($scope, $http, $location, $routeParams, userService) {

    $scope.isActive = function () {
      return userService.getActive();
    }
    $scope.getUser = function () {
      return userService.getUser();
    }
    $scope.logOutUser = function () {
      userService.logout();
      $location.path("/Home");
    };

    $scope.goTo = function (option) {
      switch (option) {
        case 'home':
          $location.path("/Home");
          break;
        case 'verify':
          $location.path("/accountVer");
          break;
        case 'order':
          $location.path("/order");
          break;
        case 'products':
          $location.path("/products");
          break;
        case 'userInfo':
          $location.path("/userInfo");
          break;
        case 'login':
          $location.path("/login");
          break;
        case 'mybag':
          $location.path("/myBag");
          break;
        case 'allForms':
          $location.path("/allForms");
          break;
          case 'allOrders':
          $location.path("/allOrders");
          break;
        case 'storeProducts':
          $location.path("/storeProducts");
          break;
        case 'prescription':
          $location.path("/prescription");
          break;
        case 'signup':
          $location.path("/register");
          break;
        case 'stores':
          $location.path("/stores");
          break;
        default:
      }
    };


    $scope.getHttp = function (url, callback) {
      var httpObject = $http.get(url);
      httpObject.then(function (promise) {
        callback(promise.data);
      }, function (error) { console.log(error); })
    }

    $scope.postHttp = function (url, data, callback) {
      var httpObject = $http.post(url, data);
      httpObject.then(function (promise) {
        callback(promise.data);
      }, function (error) { console.log(error); })
    }
  }
]);

app.controller("userController", ["$scope", "$http", "$location", "$routeParams", "userService", 'directionService',
  function ($scope, $http, $location, $routeParams, userService, directionService) {
    var states;
    var cities;
    var districts;
    $scope.getHttp = function (url, callback) {
      var httpObject = $http.get(url);
      httpObject.then(function (promise) {
        callback(promise.data);
      }, function (error) { console.log(error); })
    }

    $scope.postHttp = function (url, data, callback) {
      var httpObject = $http.post(url, data);
      httpObject.then(function (promise) {
        callback(promise.data);
      }, function (error) { console.log(error); })
    }

    $scope.updateInfo=function(username, password, conPassword, name, surname, sSurname, id, dir, date, email){
      console.log("username "+username);
      console.log("name "+name);
      console.log("surname "+surname);
      console.log("email "+email);
    }

    function isBlank(str) {
      return (!str || /^\s*$/.test(str));
    }


    $scope.loginUser = function (username, password, getCaptcha) {
      if (!isBlank(username) && !isBlank(password)) {
        var url = 'http://localhost:58706/api/Clientes?username=' + username + '&pass=' + password;
        $http.post(url).then(function (msg) {
          if (msg.data) {
            var url = 'http://localhost:58706/api/Clientes?username='+username;
            $scope.getHttp(url,(data)=>{
              userService.setUser(data);
              console.log(data);
              $location.path("/Home");
            })
            
          }
          else {
            alert("Error(02): Can't sign in, username or password incorrect");
          }
        });
      }
      else {
        alert("Error(01): Can't sign in, space in blank or not getCaptcha");
      }
    };
    $scope.UpdateDirection = function () {
      console.log("direction update");
      var url = 'http://localhost:58706/api/Provincias';
      $http.get(url)
        .then(function successCallback(data) {
          console.log(data);
          $scope.states = data.data;
        },
        function errorCallback(response) {
          alert(response);
        });
    };
    $scope.UpdateCities = function (_id) {
      directionService.setState(_id);
      var url = 'http://localhost:58706/api/Cantones?idProvincia=' + _id;
      $http.get(url)
        .then(function successCallback(data) {
          console.log(data);
          $scope.cities = data.data;
        },
        function errorCallback(response) {
          alert(response);
        });
    };
    $scope.UpdateDistricts = function (_id) {
      directionService.setCity(_id);
      var url = 'http://localhost:58706/api/Distrito?idCanton=' + _id;
      $http.get(url)
        .then(function successCallback(data) {
          console.log(data);
          $scope.districts = data.data;
        },
        function errorCallback(response) {
          alert(response);
        });
    };
    $scope.setDistrict = function (_id) {
      directionService.setDisctrict(_id);
    };
    $scope.createUser = function (username, password, conPassword, name, surname, sSurname, id, dir, date, email) {

      if (createValidation(username, password, conPassword, name, surname, sSurname, id, date, email)) {
        if (password == conPassword) {
          var url = 'http://localhost:58706/api/Clientes';
          var sendData = {
            "Cedula": parseInt(id),
            "Nombre": name,
            "pApellido": surname,
            "sApellido": sSurname,
            "Password": password,
            "Username": username,
            "Email": email,
            "Nacimiento": date,
            "Penalizacion": 0,
            "Direccion": dir,
            "Estado": 0
          };
          console.log("user: "+sendData.Direccion);
          $scope.postHttp(url,sendData,(data)=>{
            if (data.Cedula == sendData.Cedula) {
              sendMail(data.Cedula);
            }
          })
        }
        else {
          alert("Error(03): Passwords not match");
        }
      }
      else { alert("Error(01): Can't sign in, space in blank"); }


    };

    function sendMail(id){
      var url = 'http://localhost:58706/api/Clientes?cedula='+id;
      var send;
      $scope.postHttp(url,send,(data)=>{  
        if(data){     
          $location.path("/Home");
        }
        })
    }

    $scope.verify=function(id){
      var url = 'http://localhost:58706/api/Clientes?cedEstado='+parseInt(id);
      var send;
        $scope.postHttp(url,send,(data)=>{  
        if(data){     
          $location.path("/login");
        }
        })
    }
    $scope.setDirection = function (username, password, conPassword, name, surname, sSurname, id, dirSpec, date, email) {
      var url = 'http://localhost:58706/api/Direcciones';
      var sendData = {
        "Provincia": directionService.getState(),
        "Canton": directionService.getCity(),
        "Distrito": directionService.getDistrict(),
        "Descripcion": dirSpec
      };
      $scope.postHttp(url,sendData,(data)=>{
        console.log("dir: "+data.idDireccion);
        $scope.createUser(username, password, conPassword, name, surname, sSurname, id, data.idDireccion, date, email);
      })
    };

    function createValidation(username, password, conPassword, name, surname, sSurname, id, date, email) {
      if (!isBlank(username) && !isBlank(password) && !isBlank(conPassword) && !isBlank(name) && !isBlank(surname) &&
        !isBlank(sSurname) && !isBlank(id) && !isBlank(date) && !isBlank(email)) { return true; }
      else {
        return false;
      }
    };
  }
]);

app.controller("storeController", ["$scope", "$http", "$location", "$routeParams", "userService", 'storeService',
  function ( $scope, $http, $location, $routeParams, userService, storeService) {
    $scope.map = { center: { latitude: 10.9517300, longitude: -85.1361000 }, zoom: 14 };
    $scope.options = { scrollwheel: false };


    $scope.getStores = function () {
      var url = 'http://localhost:58706/api/Sucursal';
      $http.get(url)
        .then(function successCallback(data) {
          console.log(data);
          $scope.stores = data.data;
        },
        function errorCallback(response) {
          alert(response);
        });
    };
    $scope.setStore = function (store) {
      storeService.setStore(store);
      storeService.cleanBag();
      $location.path("/storeProducts");
    };
    $scope.getStore = function () { return storeService.getStore() };



    $scope.getProducts = function () {
      var url = 'http://localhost:58706/api/Productos?idSucursal=' + storeService.getStore().idSucursal;
      $http.get(url)
      .then(function successCallback(data) {
        console.log(data);
        $scope.products = data.data;
      },
      function errorCallback(response) {
        alert(response);
      });
    }
    $scope.addToBag = function(pProduct){
      var product= {product:pProduct,totalPrice:pProduct.Precio};
      if(!alreadyInBag(product)){
      console.log("added to bag: "+product );
      storeService.addToBag(product);
    }}
    $scope.getBag=function(){
      $scope.bag = storeService.getBag();
    }
    $scope.getPrice =function(product,number){
      if(Number.isInteger(number)){
      var cost =number*product.product.Precio;
      var bag = storeService.getBag();
      bag[productIndexBag(product)].totalPrice=cost;
      storeService.setBag(bag);
      return cost;}
      else{return product.Precio; }
    }
    function alreadyInBag(product){
      var bag = storeService.getBag();
      for (var index = 0; index < bag.length; index++) {
        if(product.product.idProducto==bag[index].product.idProducto)
        {return true;}
      }
      return false;
    }
    function productIndexBag(product){
      var bag = storeService.getBag();
      for (var index = 0; index < bag.length; index++) {
        if(product.product.idProducto==bag[index].product.idProducto)
        {return index;}
      }
      return -1;
    }
    $scope.deleteFromBag=function(product){
      var bag = storeService.getBag();
      bag.splice(productIndexBag(product),1);
      storeService.setBag(bag);
    }
    $scope.getFinalPrice=function(){
      var bag = storeService.getBag();
      var total=0;
      for (var index = 0; index < bag.length; index++) {
        total = total + bag[index].totalPrice;
      }
      return total;
    }
  }]);




  
  
app.controller("orderController", [ "orderService","$scope", "$http", "$location", "$routeParams", "userService", 'storeService',

  function (orderService,$scope, $http, $location, $routeParams, userService, storeService) {
    
    $scope.placeOrder=function(phoneNumber,date){

      var url = 'http://localhost:58706/api/Pedidos';
      var sendData={
        sucursalRecojo: storeService.getStore().idSucursal,
        idCliente: userService.getUser().Cedula,
        horaRecojo: date,
        Telefono: parseInt(phoneNumber),
        Imagen: globalImage,
        Estado: 1
      };
      console.log("image: "+needPres());
      if(!storeService.isEmpty()){
      if(needPres()){
        if(!isEmpty(globalImage)){
          sendData.Imagen= globalImage.split(",")[1]
          $scope.postHttp(url,sendData,(data)=>{
              addOrderDetail(data);
              globalImage="";
          })
        }
        else{alert("Prescription Image is require");}
      }
      else{
        $scope.postHttp(url,sendData,(data)=>{
          addOrderDetail(data);
          globalImage="";
      })
      }
     }
      else{alert("Shopping bag is empty");
     }}

    function addOrderDetail(id){
      var url = 'http://localhost:58706/api/DetallePedido';
      var bag = storeService.getBag();
      for (var index = 0; index < bag.length; index++) {
        var sendData = {
          idProducto: bag[index].product.idProducto,
          idPedido: id.idPedido,
          idCantidad: (bag[index].totalPrice/bag[index].product.Precio)
        };
        console.log("idProducto: "+sendData.idProducto);
        console.log("idPedido: "+sendData.idPedido);
        console.log("idCantidad: "+sendData.idCantidad);
        $scope.postHttp(url,sendData,(data)=>{
          console.log("added order detail");
        })
      }
      storeService.cleanBag();
    }

    function isEmpty(str) {
      return (!str || 0 === str.length);
    }
    function needPres(){
      var bag  = storeService.getBag();
      for (var index = 0; index < bag.length; index++) {
        if(bag[index].product.reqPrescripcion==1){
          return true;
        }
      }
      return false;
    }

    $scope.getAllOrders=function(){
        var url = 'http://localhost:58706/api/Pedidos/'+userService.getUser().Cedula;
        $http.get(url)
          .then(function successCallback(data) {
            console.log(data);
            $scope.Orders = data.data;
          },
          function errorCallback(response) {
            alert(response);
          });
      };
  }]);







app.controller("receipeController", [ "orderService","receipeService","$scope", "$http", "$location", "$routeParams", "userService", 'storeService',  
    function (orderService,receipeService,$scope, $http, $location, $routeParams, userService, storeService) {
    $scope.addReceipe=function(){
      var url = 'http://localhost:58706/api/Recetas';
      var sendData= 
      {
        idCliente: userService.getUser().Cedula,
        Imagen: globalImage,
        Estado: 1,
      };
      if(!receipeService.medsEmpty()){
      if(!isEmpty(globalImage)){
      sendData.Imagen= globalImage.split(",")[1];
      $scope.postHttp(url,sendData,(data)=>{
        console.log("Receipe id: "+data.idReceta);
        globalImage="";
        addReceipeDetail(data.idReceta);
      })
      }
      else{
      alert("Form Photo is requiere");
      }}
      else{
      alert("No Medicines Added to Form");
      }
    }

    $scope.goBack=function(){
      receipeService.cleanMeds();
      $location.path("/myBag");
    }

    $scope.getAllForms=function(){
      var url = 'http://localhost:58706/api/Recetas/'+userService.getUser().Cedula;
      $http.get(url)
        .then(function successCallback(data) {
          console.log(data);
          $scope.forms = data.data;
        },
        function errorCallback(response) {
          alert(response);
        });
    };

    function productIndexMeds(product){
      var meds = receipeService.getMeds();
      for (var index = 0; index < meds.length; index++) {
        if(product.idProducto==meds[index].idProducto)
        {return index;}
      }
      return -1;
    }
    $scope.deleteFromMeds=function(product){
      var meds = receipeService.getMeds();
      meds.splice(productIndexMeds(product),1);
      receipeService.setMeds(meds);
    }

    function addReceipeDetail(id){
      var url = 'http://localhost:58706/api/DetalleReceta';
      var meds = receipeService.getMeds();
      for (var index = 0; index < meds.length; index++) {
        var sendData = {
          idMedicamento: meds[index].idProducto,
          idReceta: id
        };
        $scope.postHttp(url,sendData,(data)=>{
          console.log("added form detail");
        })
      }
      receipeService.cleanMeds();
    }

    $scope.addProduct=function(product){
      receipeService.addMed(product);
      
    }
    function isEmpty(str) {
      return (!str || 0 === str.length);
    }
    $scope.getAllProducts = function () {
      var url = 'http://localhost:58706/api/Productos';
      $http.get(url)
      .then(function successCallback(data) {
        console.log("all products: "+data.data);
        $scope.AllProducts = data.data;
      },
      function errorCallback(response) {
        alert(response);
      });
    }

     $scope.alreadyInForm=function(product){
      var meds = receipeService.getMeds();
      for (var index = 0; index < meds.length; index++) {
        if(product.idProducto==meds[index].idProducto)
        {return true;}
      }
      return false;
    }
    }]);
  var openFile = function(event) {
    var input = event.target;
    var reader = new FileReader();
    reader.onload = function(){
      var dataURL = reader.result;
      var output = document.getElementById('output');
      output.src = dataURL;
      globalImage = dataURL;
      
    };
    reader.readAsDataURL(input.files[0]);
 }
 


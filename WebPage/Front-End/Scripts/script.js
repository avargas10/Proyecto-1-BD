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
    when('/accountVer', { templateUrl: '../Views/account.html', controller: 'userController' }).
    when('/prescription', { templateUrl: '../Views/prescription.html', controller: 'orderController' }).
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
        var url = 'http://192.168.1.59:58706/api/Clientes?username=' + username + '&pass=' + password;
        $http.post(url).then(function (msg) {
          if (msg.data) {
            var url = 'http://192.168.1.59:58706/api/Clientes?username='+username;
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
      var url = 'http://192.168.1.59:58706/api/Provincias';
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
      var url = 'http://192.168.1.59:58706/api/Cantones?idProvincia=' + _id;
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
      var url = 'http://192.168.1.59:58706/api/Distrito?idCanton=' + _id;
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
          var url = 'http://192.168.1.59:58706/api/Clientes';
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
      var url = 'http://192.168.1.59:58706/api/Clientes?cedula='+id;
      var send;
      $scope.postHttp(url,send,(data)=>{  
        if(data){     
          $location.path("/Home");
        }
        })
    }

    $scope.verify=function(id){
      var url = 'http://192.168.1.59:58706/api/Clientes?cedEstado='+parseInt(id);
      var send;
        $scope.postHttp(url,send,(data)=>{  
        if(data){     
          $location.path("/login");
        }
        })
    }
    $scope.setDirection = function (username, password, conPassword, name, surname, sSurname, id, dirSpec, date, email) {
      var url = 'http://192.168.1.59:58706/api/Direcciones';
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
      var url = 'http://192.168.1.59:58706/api/Sucursal';
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

    $scope.getAllProducts = function () {
      var url = 'http://192.168.1.59:58706/api/Productos';
      $http.get(url)
      .then(function successCallback(data) {
        console.log("all products: "+data.data);
        $scope.AllProducts = data.data;
      },
      function errorCallback(response) {
        alert(response);
      });
    }

    $scope.getProducts = function () {
      var url = 'http://192.168.1.59:58706/api/Productos?idSucursal=' + storeService.getStore().idSucursal;
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

      var url = 'http://192.168.1.59:58706/api/Pedidos';
      var sendData={
        sucursalRecojo: storeService.getStore().idSucursal,
        idCliente: userService.getUser().Cedula,
        horaRecojo: date,
        Telefono: parseInt(phoneNumber),
        Imagen: globalImage,
        Estado: 1
      };
      console.log("image: "+needPres());
      if(needPres()){
        if(!isEmpty(globalImage)){
          $scope.postHttp(url,sendData,(data)=>{
              console.log("order id: "+data);
              storeService.cleanBag();
              globalImage="";
          })
        }
        else{alert("Image is require");}
      }
      else{
        $scope.postHttp(url,sendData,(data)=>{
          console.log("order id: "+data);
          storeService.cleanBag();
          globalImage="";
      })
      }
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
 


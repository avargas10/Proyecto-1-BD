var app = angular.module("mainModule",
  ["ngRoute",
    'vcRecaptcha',
    'uiGmapgoogle-maps']);

app.config(function (uiGmapGoogleMapApiProvider, $routeProvider, $locationProvider) {
  $routeProvider.
    when('/Home', { templateUrl: '../Views/home-view.html', controller: 'mainController' }).
    when('/login', { templateUrl: '../Views/login-view.html', controller: 'userController' }).
    when('/register', { templateUrl: '../Views/register.html', controller: 'userController' }).
    when('/pedido', { templateUrl: '../Views/pedido.html', controller: 'userController' }).
    when('/products', { templateUrl: '../Views/client.html', controller: 'userController' }).
    when('/stores', { templateUrl: '../Views/stores.html', controller: 'storeController' }).
    when('/storeProducts', { templateUrl: '../Views/storeProducts.html', controller: 'storeController' }).
    when('/myBag', { templateUrl: '../Views/myBag.html', controller: 'storeController' }).
    otherwise({ redirectTo: '/Home' });
  // $locationProvider.html5Mode(true);
  uiGmapGoogleMapApiProvider.configure({
    key: 'AIzaSyAIPxUS3YTey5p2garlnWopeev1MW5gtfY',
    v: 3,
    libraries: 'weather,geometry,visualization'
  });
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
  this.setStore = function (pStore) {
    store = pStore;
  }
  this.getStore = function () { return store; }
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
        case 'products':
          $location.path("/products");
          break;
        case 'login':
          $location.path("/login");
          break;
        case 'mybag':
          $location.path("/myBag");
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

    function isBlank(str) {
      return (!str || /^\s*$/.test(str));
    }


    $scope.loginUser = function (username, password, getCaptcha) {
      if (!isBlank(username) && !isBlank(password)) {
        var url = 'http://192.168.1.210:58706/api/Clientes?username=' + username + '&pass=' + password;
        $http.post(url).then(function (msg) {
          if (msg.data) {
            userService.setUser(username);
            $location.path("/Home");
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
      var url = 'http://192.168.1.210:58706/api/Provincias';
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
      var url = 'http://192.168.1.210:58706/api/Cantones?idProvincia=' + _id;
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
      var url = 'http://192.168.1.210:58706/api/Distrito?idCanton=' + _id;
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
          var url = 'http://192.168.1.210:58706/api/Clientes';
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
            "Estado": 1
          };
          $http.post(url, sendData)
            .success(function (data) {
              console.log(data);
              console.log(sendData);
              if (data.Cedula == sendData.Cedula) {
                $location.path("/login");
              }
            })
            .error(function (data, status) {
              console.error('Repos error', status, data);
            })
            .finally(function () {
              console.log("finally finished repos");
            });
        }
        else {
          alert("Error(03): Passwords not match");
        }
      }
      else { alert("Error(01): Can't sign in, space in blank"); }


    };

    $scope.setDirection = function (username, password, conPassword, name, surname, sSurname, id, dirSpec, date, email) {
      var url = 'http://192.168.1.210:58706/api/Direcciones';
      var sendData = {
        "Provincia": directionService.getState(),
        "Canton": directionService.getCity(),
        "Distrito": directionService.getDistrict(),
        "Descripcion": dirSpec
      };
      $http.post(url, sendData)
        .success(function (data) {
          $scope.createUser(username, password, conPassword, name, surname, sSurname, id, data.idDireccion, date, email);
        })
        .error(function (data, status) {
          console.error('Repos error', status, data);
        })
        .finally(function () {
          console.log("finally finished evaluating");
        });
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

app.controller("storeController", ["uiGmapGoogleMapApi", "$scope", "$http", "$location", "$routeParams", "userService", 'storeService',
  function (uiGmapGoogleMapApi, $scope, $http, $location, $routeParams, userService, storeService) {
    $scope.map = { center: { latitude: 10.9517300, longitude: -85.1361000 }, zoom: 14 };
    $scope.options = { scrollwheel: false };


    $scope.getStores = function () {
      var url = 'http://192.168.1.210:58706/api/Sucursal';
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
      $location.path("/storeProducts");
    };
    $scope.getStore = function () { return storeService.getStore() };

    $scope.getProducts = function () {
      var url = 'http://192.168.1.210:58706/api/Productos?idSucursal=' + storeService.getStore().idSucursal;
      $http.get(url)
      .then(function successCallback(data) {
        console.log(data);
        $scope.products = data.data;
      },
      function errorCallback(response) {
        alert(response);
      });

    }
  }]);
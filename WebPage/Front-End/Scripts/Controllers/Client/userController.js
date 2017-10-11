angular.module("mainModule").controller("userController", ["$scope", "$http", "$location", "$routeParams", "userService", 'directionService',
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
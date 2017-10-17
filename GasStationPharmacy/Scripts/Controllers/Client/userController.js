angular.module("mainModule").controller("userController", ["$scope", "$http", "$location", 
"$routeParams", "userService", 'directionService',"orderService","receipeService","storeService",
function ($scope, $http, $location, $routeParams, userService, directionService,orderService,receipeService,storeService) {
  var states;
  var cities;
  var districts;
  var EmployeeCheck;
  $scope.getHttp = function (url, callback) {
    var httpObject = $http.get(url);
    httpObject.then(function (promise) {
      callback(promise.data);
    }, function (error) {alert(error.data); console.log(error); })
  }

  $scope.postHttp = function (url, data, callback) {
    var httpObject = $http.post(url, data);
    httpObject.then(function (promise) {
      callback(promise.data);
    }, function (error) {alert(error.data); console.log(error); })
  }

  $scope.updateInfo=function(username, password, conPassword, name, surname, sSurname, id, dirSpec, date, email,phone,idDir){
    if (createValidation(username, password, conPassword, name, surname, sSurname, id, date, email,phone)) {
    if(password==conPassword){
    if(directionService.update()){
      UpdateDirection(idDir,dirSpec);
    }
    var url = 'http://'+getIp()+':58706/api/Clientes';
    var sendData = {
      "Cedula": parseInt(id),
      "Nombre": name,
      "pApellido": surname,
      "sApellido": sSurname,
      "Password": password,
      "Username": username,
      "Email": email,
      "Nacimiento": date,
      "Direccion": idDir,
      "Telefono":parseInt(phone)
    };
    $http.put(url,sendData)
    .then(
        function(response){
          // success callback
          console.log("update");
          directionService.setUpdate();
          userService.logout();
          storeService.cleanBag();
          orderService.clean();
          receipeService.cleanMeds();
          $location.path("/Home");

        }, 
        function(response){
          // failure callback
        }
     );
  }
  else {
    alert("Error(03): Passwords not match");
  }
}
else { alert("Error(01): Can't sign in, space in blank"); }
  
}

  function isBlank(str) {
    return (!str || /^\s*$/.test(str));
  }
  


   function UpdateDirection(id,dirspec){
    var url = 'http://'+getIp()+':58706/api/Direcciones';
    var sendData = {
      "idDireccion": id ,
      "Provincia": directionService.getState(),
      "Canton": directionService.getCity(),
      "Distrito": directionService.getDistrict(),
      "Descripcion": dirspec
    }
    $http.put(url,sendData)
    .then(
        function(response){
          console.log("update");
        }, 
        function(response){
          // failure callback
        }
     );
    }

 
$scope.getDireccion=function(id){
    console.log("id: "+id);
    var url = 'http://'+getIp()+':58706/api/Direcciones?id='+id;
        $scope.getHttp(url,(data)=>{
          $scope.direction = data;
          console.log(data);
        })
      }

  $scope.loginUser = function (username, password, getCaptcha) {
    if (!isBlank(username) && !isBlank(password)) {
      console.log("state of checkbox: " + this.EmployeeCheck);
      if(this.EmployeeCheck == false){
        var url = 'http://'+getIp()+':58706/api/Clientes?username=' + username + '&pass=' + password;
        $http.post(url).then(function (msg) {
          if (msg.data) {
            var url = 'http://'+getIp()+':58706/api/Clientes?username='+username;
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
      //else del empleyee check
      else{
        var url = 'http://'+getIp()+':58706/api/Empleados?username=' + username + '&pass=' + password;
        $http.post(url).then(function (msg) {
            var url = 'http://'+getIp()+':58706/api/Empleados?username='+username;
            var idsuc = msg.data.idSucursal;
            $scope.getHttp(url,(data1)=>{
              userService.setUser(data1);
              userService.setSucursal(idsuc);
              console.log(msg.data.idSucursal);
              $location.path("/Home");
            })
        });
      }
    }
    else {
      alert("Error(01): Can't sign in, space in blank or not getCaptcha");
    }
  };

  $scope.deleteUser=function(id){
    if (confirm('Sure you want to delete?')) {
    console.log("id: "+id);
    var url = 'http://'+getIp()+':58706/api/Clientes';
    var sendData = {
      Cedula: id
    };
    $http({
      method: 'DELETE',
      url: url,
      data: sendData,
      headers: {
          'Content-type': 'application/json;charset=utf-8'
      }
  })
  .then(function successCallback(data) {
    alert("Client "+userService.getUser().Nombre+" deleted");
    userService.logout();
    storeService.cleanBag();
    orderService.clean();
    receipeService.cleanMeds();
    $location.path("/Home");
    
  },
  function errorCallback(response) {
    alert(response);
  });
    }
  }

  $scope.UpdateDirection = function () {
    console.log("direction update");
    var url = 'http://'+getIp()+':58706/api/Provincias';
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
    var url = 'http://'+getIp()+':58706/api/Cantones?idProvincia=' + _id;
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
    var url = 'http://'+getIp()+':58706/api/Distrito?idCanton=' + _id;
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
  $scope.createUser = function (username, password, conPassword, name, surname, sSurname, id, dir, date, email,phone) {

    if (createValidation(username, password, conPassword, name, surname, sSurname, id, date, email,phone)) {
      if (password == conPassword) {
        var url = 'http://'+getIp()+':58706/api/Clientes';
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
          "Estado": 0,
          "Telefono":parseInt(phone)
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
    var url = 'http://'+getIp()+':58706/api/Clientes?cedula='+id;
    var send;
    $scope.postHttp(url,send,(data)=>{  
      if(data){     
        $location.path("/Home");
      }
      })
  }

  $scope.verify=function(id){
    var url = 'http://'+getIp()+':58706/api/Clientes?cedEstado='+parseInt(id);
    var send;
      $scope.postHttp(url,send,(data)=>{  
      if(data){     
        $location.path("/login");
      }
      })
  }
  $scope.setDirection = function (username, password, conPassword, name, surname, sSurname, id, dirSpec, date, email,phone) {
    var url = 'http://'+getIp()+':58706/api/Direcciones';
    var sendData = {
      "Provincia": directionService.getState(),
      "Canton": directionService.getCity(),
      "Distrito": directionService.getDistrict(),
      "Descripcion": dirSpec
    };
    $scope.postHttp(url,sendData,(data)=>{
      console.log("dir: "+data.idDireccion);
      $scope.createUser(username, password, conPassword, name, surname, sSurname, id, data.idDireccion, date, email,phone);
    })
  };

  function createValidation(username, password, conPassword, name, surname, sSurname, id, date, email,phone) {
    if (!isBlank(username) && !isBlank(password) && !isBlank(conPassword) && !isBlank(name) && !isBlank(surname) &&
      !isBlank(sSurname) && !isBlank(id) && !isBlank(date) && !isBlank(email) && !isBlank(phone)) { return true; }
    else {
      return false;
    }
  };
}
]);
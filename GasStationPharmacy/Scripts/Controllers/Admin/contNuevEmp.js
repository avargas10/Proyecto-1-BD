angular.module("mainModule").controller("contNuevEmp", ["$scope","$http","directionService",'storeService','$location',

  function($scope,$http,directionService,storeService,$location) {
    var states;
    var cities;
    var districts;

    $scope.id;
    $scope.name;
    $scope.empEmail;
    $scope.empUser;
    $scope.userPass;
    $scope.empSur;
    $scope.empSecSur;
    $scope.empDate;
    $scope.dirSpec;

    $scope.createEmp=function(id,mail,user,pass,nme,pAp,sAp,date,dir){
      alert(id);
      alert(mail);
      alert(user);
      alert(pass);
      alert(nme);
      alert(pAp);
      alert(sAp);
      alert(date);
      alert(dir);
      var url='http://'+getIp()+':58706/api/Sucursal';
      var sendData={
        "idEmpleado": parseInt(id),
        "Email": mail,
        "Username":user,
        "Password":pass,
        "Nombre":nme,
        "pApellido":pAp,
        "sApellido":sAp,
        "Nacimientos":date,
        "Direccion": dir,
        "Estado":1
      };
      
      $scope.postHttp(url,sendData,(data)=>{
          if(data){

          }
        });
        
      }

      $scope.setDirection = function (id,mail,user,pass,nme,pAp,sAp,date,dirSpec) {
        var url = 'http://'+getIp()+':58706/api/Direcciones';
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
      $scope.setDirection = function (username, password, conPassword, name, surname, sSurname, id, dirSpec, date, email) {
        var url = 'http://'+getIp()+':58706/api/Direcciones';
        var sendData = {
          "Provincia": directionService.getState(),
          "Canton": directionService.getCity(),
          "Distrito": directionService.getDistrict(),
          "Descripcion": dirSpec
        };
        $scope.postHttp(url,sendData,(data)=>{
          console.log("dir: "+data.idDireccion);
          $scope.createEmployee(username, password, conPassword, name, surname, sSurname, id, data.idDireccion, date, email);
        })
      };

      $scope.createEmployee = function (username, password, conPassword, name, surname, sSurname, id, dir, date, email) {
              if (password == conPassword) {
                var url = 'http://'+getIp()+':58706/api/Empleados';
                var sendData = {
                  "idEmpleado": parseInt(id),
                  "Email": email,
                  "Username": username,
                  "Password": password,
                  "Nombre": name,
                  "pApellido": surname,
                  "sApellido": sSurname,
                  "Nacimiento": date,
                  "Direccion": dir,
                  "Estado": 1,
                  "idRol": 1,
                  "idSucursal": storeService.getID()
                }
                console.log("employee: "+sendData);
                $scope.postHttp(url,sendData,(data)=>{
                  alert('created Admin');
                  $location.path('/Admin/gsucursales');
                })
              }
              else {
                alert("Error(03): Passwords not match");
              }
        
          };
      }]);
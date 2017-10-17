angular.module("mainModule").controller("contNuevEmp", ["$scope","$http","directionService",'storeService','$location','userService',

  function($scope,$http,directionService,storeService,$location,userService) {
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
    var rol;


      $scope.getRols=function(){
        var url = 'http://'+getIp()+':58706/api/Roles/'+userService.getCompany();
        $http.get(url).then(function(msg){
          $scope.rolList= msg.data;
        });
      }
      $scope.setRol=function(pRol){
          rol = pRol;
      }

      $scope.UpdateDirection = function () {
        console.log("direction update");
        var url = 'http://'+getIp()+':58706/api/Provincias';
        $http.get(url)
        .then(function successCallback(data) {
          console.log(data);
          $scope.states = data.data;
          $scope.getRols();
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
      $scope.setDirection = function (username, password, conPassword, name, surname, sSurname, id, dirSpec, date, email,admin,sucAd) {
        var url = 'http://'+getIp()+':58706/api/Direcciones';
        var sendData = {
          "Provincia": directionService.getState(),
          "Canton": directionService.getCity(),
          "Distrito": directionService.getDistrict(),
          "Descripcion": dirSpec
        };
        $scope.postHttp(url,sendData,(data)=>{
          console.log("dir: "+data.idDireccion);
          $scope.createEmployee(username, password, conPassword, name, surname, sSurname, id, data.idDireccion, date, email,admin,sucAd);
        })
      };

      $scope.createEmployee = function (username, password, conPassword, name, surname, sSurname, id, dir, date, email,admin,sucAd) {
        if(admin){rol=1;} 
        if(!sucAd){storeService.setID(userService.getSucursal());}
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
                  "idRol": rol,
                  "idSucursal": storeService.getID()
                }
                console.log("employee: "+sendData);
                $scope.postHttp(url,sendData,(data)=>{
                  alert('created Admin');
                  storeService.cleanStore();
                  $location.path('/Admin/gsucursales');
                })
              }
              else {
                alert("Error(03): Passwords not match");
              }
        
          };
      }]);
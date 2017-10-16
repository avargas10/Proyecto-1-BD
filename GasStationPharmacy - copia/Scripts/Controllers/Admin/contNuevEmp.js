angular.module("mainModule").controller("contNuevEmp", ["$scope","$http",

  function($scope,$http) {
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
      /**
      $scope.postHttp(url,sendData,(data)=>{
          if(data){

          }
        });
        **/
        animation();
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


      }]);
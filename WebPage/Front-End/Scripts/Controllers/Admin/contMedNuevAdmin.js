
angular.module("mainModule").controller("contMedNuevAdmin", ["$scope","$http",
function($scope,$http) {
  $scope.id = "";
  $scope.name="";
  $scope.presc="";
  $scope.house="";
  $scope.qty="";
  $scope.suc="";
  $scope.sucList;
  $scope.idAdmin=empresaAdmin;


  $scope.getHttp= function(url , callback){
    var httpObject = $http.get(url);
    httpObject.then(function(promise){
      callback(promise.data);
    }, function(error){ console.log(error);})}

    $scope.postHttp = function(url,data,callback){
      var httpObject = $http.post(url,data);
      httpObject.then(function(promise){
        callback(promise.data);
      }, function(error){ console.log(error);})}

      $scope.init = function(){
        var url = 'http://'+getIp()+':58706/api/Sucursal';
        $http.get(url).then(function(msg){
          $scope.sucList = msg.data;
        });

      }


      $scope.createProduct=function(){
        var url='http://'+getIp()+':58706/api/Roles';
        var sendData={"Nombre": nme, "Descripcion":descr};

        alert(nme+descr);

        $http.post(url,sendData)
        .then(
          function successCallback(response){

          },function errorCallBack(response){

          });
        animation();
      };

    }]);

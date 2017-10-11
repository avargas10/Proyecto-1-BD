angular.module("mainModule").controller("receipeController", [ "orderService","receipeService","$scope", "$http", "$location", "$routeParams", "userService", 'storeService',  
function (orderService,receipeService,$scope, $http, $location, $routeParams, userService, storeService) {
$scope.addReceipe=function(){
  var url = 'http://'+getIp()+':58706/api/Recetas';
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
  var url = 'http://'+getIp()+':58706/api/Recetas/'+userService.getUser().Cedula;
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
  var url = 'http://'+getIp()+':58706/api/DetalleReceta';
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
  var url = 'http://'+getIp()+':58706/api/Productos';
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
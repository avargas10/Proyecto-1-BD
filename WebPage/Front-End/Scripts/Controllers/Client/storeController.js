angular.module("mainModule").controller("storeController", ["$scope", "$http", "$location", "$routeParams", "userService", 'storeService',
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
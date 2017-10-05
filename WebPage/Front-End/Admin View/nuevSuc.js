angular.module("nuevSucAdmin", []).controller("contSucAdmin", 
  function($scope) {
    $scope.id = "";
    $scope.name="";
    $scope.desc="";
    $scope.admin="";
    $scope.dir="";


    $scope.createProduct=function(){
      alert($scope.id+','+$scope.name+','+$scope.presc);
      animation();
    }

  }

);



 function animation() {
    var x = document.getElementById("snackbar")
    x.className = "show";
    setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
}
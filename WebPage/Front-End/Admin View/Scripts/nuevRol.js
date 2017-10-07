angular.module("adminRol", []).controller("contRolAdmin", 
  function($scope) {
    $scope.id = "";
    $scope.name="";
    $scope.desc="";


    $scope.createProduct=function(){
      alert($scope.id+','+$scope.name+','+$scope.desc);
      animation();
    }

  }

);


 function animation() {
    var x = document.getElementById("snackbar")
    x.className = "show";
    setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
}
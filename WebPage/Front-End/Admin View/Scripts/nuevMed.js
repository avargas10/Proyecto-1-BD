angular.module("nuevMedAdmin", []).controller("contMedAdmin", 
  function($scope) {
    $scope.id = "";
    $scope.name="";
    $scope.presc="";
    $scope.house="";
    $scope.qty="";


    $scope.createProduct=function(){
      alert($scope.id+','+$scope.name+','+$scope.presc);
      animation();
    }

  }

);


var openFile = function(event) {
    var input = event.target;

    var reader = new FileReader();
    reader.onload = function(){
      var dataURL = reader.result;
      var output = document.getElementById('output');
      output.src = dataURL;
      
    };
    reader.readAsDataURL(input.files[0]);
 }



 function animation() {
    var x = document.getElementById("snackbar")
    x.className = "show";
    setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
}
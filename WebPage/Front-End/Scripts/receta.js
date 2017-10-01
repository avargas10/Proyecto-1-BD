angular.module("recetasClient", []).controller("contRecetas", 
  function($scope) {
    $scope.id = "";
    $scope.doctor="";
    $scope.lista="";


    $scope.submitReceta=function(){
      alert($scope.id+','+$scope.doctor+','+$scope.lista);
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
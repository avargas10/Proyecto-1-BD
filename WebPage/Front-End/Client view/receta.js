angular.module("recetasClient", []).controller("contRecetas", 
  function($scope) {
    $scope.id = "";
    $scope.doctor="";
    $scope.lista="";


    $scope.submitReceta=function(){
      alert($scope.id+','+$scope.doctor+','+$scope.lista);
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
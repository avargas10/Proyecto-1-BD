angular.module("emplAdmin", []).controller("contEmplAdmin", 
  function($scope) {
    $scope.employeelist=[
        {'name':'Marco', 'tel': '2222222' ,'id': '40', 'rol':'Cajero'},
        {'name':'Adrian', 'tel': '8888888888' ,'id': '10', 'rol':'Farmaceutico'}, 
        {'name':'Andres', 'tel': '2333333333' ,'id': '30', 'rol':'Cajero'},
        {'name':'Rodolfo', 'tel': '8999999999' ,'id': '100', 'rol':'Farmaceutico'},
    ];



    $scope.edit=function(){
    }
    $scope.delete=function(){
        this.employeelist.pop();
        animation();
    }

  }
);

function animation() {
    var x = document.getElementById("snackbar")
    x.className = "show";
    setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
}
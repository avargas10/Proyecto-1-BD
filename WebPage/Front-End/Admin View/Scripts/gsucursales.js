angular.module("sucAdmin", []).controller("contSucAdmin", 
  function($scope) {
    $scope.sucList=[
        {'name':'San Pedro', 'desc': 'Blanca' ,'admin': 'Marco'},
        {'name':'San Jose', 'desc': 'Verde' ,'admin': 'Fofo'},
        {'name':'Heredia', 'desc': 'Rojo' ,'admin': 'Vargas'},
        {'name':'Alajuela', 'desc': 'Negro' ,'admin': 'Pato'},
    ];



    $scope.edit=function(name,id){
    }
    $scope.delete=function(){
        this.medList.pop();
        animation();
    }

  }
);

function animation() {
    var x = document.getElementById("snackbar")
    x.className = "show";
    setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
}


function newSuc(){
    window.location.href = "nuevSuc.html";
}
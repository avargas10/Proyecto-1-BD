angular.module("medAdmin", []).controller("contMedAdmin", 
  function($scope) {
    $scope.medList=[
        {'name':'Tabcin', 'price': '50' ,'id': '1212'},
        {'name':'Talerdin', 'price': '20' ,'id': '2121'},
        {'name':'Condones', 'price': '30' ,'id': '4343'},
        {'name':'Suero', 'price': '60' ,'id': '3434'},
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


function newProd(){
    window.location.href = "nuevMed.html";
}
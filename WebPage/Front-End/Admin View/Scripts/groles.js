angular.module("rolAdmin", []).controller("contRolAdmin", 
  function($scope) {
    $scope.rollist=[
        {'name':'Cashier', 'desc': 'pay' ,'id': '40'},
        {'name':'Pharmacist', 'desc': 'medicaments' ,'id': '10'},
        {'name':'Cashier', 'desc': 'pay' ,'id': '30'},
        {'name':'Pharmacist', 'desc': 'medicaments' ,'id': '100'},
    ];



    $scope.edit=function(){
    }
    $scope.delete=function(){
        this.rollist.pop();
        animation();
    }

  }
);

function animation() {
    var x = document.getElementById("snackbar")
    x.className = "show";
    setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
}

function newRol(){
    window.location.href="../Views/nuevRol.html";
}
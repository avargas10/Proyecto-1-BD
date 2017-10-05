angular.module("clientAdmin", []).controller("contClientAdmin", 
  function($scope) {
    $scope.clientlist=[
        {'name':'Marco', 'tel': '2222222' ,'id': '40'},
        {'name':'Adrian', 'tel': '8888888888' ,'id': '10'},
        {'name':'Andres', 'tel': '2333333333' ,'id': '30'},
        {'name':'Rodolfo', 'tel': '8999999999' ,'id': '100'},
    ];



    $scope.edit=function(p,cant){
    }
    $scope.delete=function(){
        this.clientlist.pop();
        animation();
    }

  }
);

function animation() {
    var x = document.getElementById("snackbar")
    x.className = "show";
    setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
}


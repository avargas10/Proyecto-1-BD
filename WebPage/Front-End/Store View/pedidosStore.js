angular.module("pedidoStore", []).controller("contPedidoStore", 
  function($scope) {
    $scope.state = "Nuevo";
    $scope.listOrders=[
    	{'cliente':'Marco', 'productos': 'Tabcin \n Talerdin \n Gex \n Inyeccion','date':'19/10/2017 05:50'},
    	{'cliente':'Rodolfo', 'productos': 'Gex','date':'19/10/2017 03:50'},
    	{'cliente':'Adiran', 'productos': 'Talerdin','date':'19/10/2017 08:50'},
    	{'cliente':'Andres', 'productos': 'Inyeccion','date':'18/10/2017 09:50'}
    ];



    $scope.update=function(ste){
        alert(ste);
    	animation();
    }

  }
);


function animation() {
    var x = document.getElementById("snackbar")
    x.className = "show";
    setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
}
angular.module("productosClient", []).controller("contProductos", 
  function($scope) {
    $scope.prod = "";
    $scope.cantidad ="";
    $scope.listProducts=[
    	{'img':'img/med.png', 'producto': 'Tabcin' ,'precio': '40', 'prescripcion': 'No' },
    	{'img':'img/iny.png', 'producto': 'Gex' ,'precio': '10', 'prescripcion': 'No' },
    	{'img':'img/med.png', 'producto': 'Talerdin' ,'precio': '30', 'prescripcion': 'No' },
    	{'img':'img/iny.png', 'producto': 'Inyeccion' ,'precio': '100', 'prescripcion': 'Si' },
    	{'img':'img/med.png', 'producto': 'Suero' ,'precio': '60', 'prescripcion': 'Si' }
    ];

    $scope.listCart=[];


    $scope.add=function(p,cant){
    	this.listCart.push({'producto':p, 'cantidad':cant});
    	alert(p+cant)
    	animation();
    }

  }
);


function shopCart() {
    window.location.href = "pedido.html";

}


function animation() {
    var x = document.getElementById("snackbar")
    x.className = "show";
    setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
}

angular.module("pedidoClient", []).controller("contPedido", 
  function($scope) {
    $scope.id = "";
    $scope.tel="";
    $scope.sucursal="";
    $scope.fecha="";
    $scope.listProducts=[
    	{'img':'img/med.png', 'producto': 'Tabcin' ,'precio': '40', 'prescripcion': 'No' },
    	{'img':'img/iny.png', 'producto': 'Gex' ,'precio': '10', 'prescripcion': 'No' },
    	{'img':'img/med.png', 'producto': 'Talerdin' ,'precio': '30', 'prescripcion': 'No' },
    	{'img':'img/iny.png', 'producto': 'Inyeccion' ,'precio': '100', 'prescripcion': 'Si' },
    	{'img':'img/med.png', 'producto': 'Suero' ,'precio': '60', 'prescripcion': 'Si' }
    ];


    $scope.submitCart=function(){
      alert($scope.sucursal+','+$scope.fecha);
      animation();
    }

  }

);



function animation() {
    var x = document.getElementById("snackbar")
    x.className = "show";
    setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
}
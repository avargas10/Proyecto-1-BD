//Probar la creacion creacion de sucursales


var app= angular.module("mainModule",[]);


//Controldor empleados vista administrador

app.controller("contEmplAdmin",["$scope","$http",
  function($scope,$http) {
    $scope.employeelist;
    var emp;


    $scope.getHttp= function(url , callback){
      var httpObject = $http.get(url);
      httpObject.then(function(promise){
        callback(promise.data);
      }, function(error){ console.log(error);})}

      $scope.postHttp = function(url,data,callback){
        var httpObject = $http.post(url,data);
        httpObject.then(function(promise){
          callback(promise.data);
        }, function(error){ console.log(error);})}

        $scope.init = function(){
          var url = 'http://192.168.1.203:58706/api/Empleados';
          $http.get(url).then(function(msg){
            emp= msg.data;
            $scope.employeelist = emp;
          });

        };

        $scope.edit=function(){
        }

        $scope.delete=function(){
          this.employeelist.pop();
          animation();
        }

      }]);

function newEmploy(){
  alert('Nuevo empleado');
  window.location.href="../Views/nuevEmpleado.html";
}






//controlador roles vista administrador


app.controller("contRolAdmin", ["$scope","$http",
  function($scope,$http) {
    $scope.rollist;
    $scope.getHttp= function(url , callback){
      var httpObject = $http.get(url);
      httpObject.then(function(promise){
        callback(promise.data);
      }, function(error){ console.log(error);})}

      $scope.postHttp = function(url,data,callback){
        var httpObject = $http.post(url,data);
        httpObject.then(function(promise){
          callback(promise.data);
        }, function(error){ console.log(error);})}

        $scope.init = function(){
          var url = 'http://192.168.1.203:58706/api/Roles';
          $http.get(url).then(function(msg){
            $scope.rollist= msg.data;
          });

        };


        $scope.edit=function(){
        }
        $scope.delete=function(){
          this.rollist.pop();
          animation();
        }

      }]);

function newRol(){
    window.location.href="../Views/nuevRol.html";
}



//controlador clientes vista administrador

app.controller("contClientAdmin",["$scope","$http",
  function($scope,$http) {
    $scope.clientlist;
    var client;

    $scope.getHttp= function(url , callback){
      var httpObject = $http.get(url);
      httpObject.then(function(promise){
        callback(promise.data);
      }, function(error){ console.log(error);})}

      $scope.postHttp = function(url,data,callback){
        var httpObject = $http.post(url,data);
        httpObject.then(function(promise){
          callback(promise.data);
        }, function(error){ console.log(error);})}

        $scope.init = function(){
          var url = 'http://192.168.1.203:58706/api/Clientes';
          $http.get(url).then(function(msg){
            client= msg.data;
            $scope.clientlist = client;
          });

        };


    $scope.edit=function(){
    }
    $scope.delete=function(){
        this.clientlist.pop();
        animation();
    }

  }]);

//controlador sucursales vista administrador

app.controller("contSucAdmin", ["$scope","$http",
  function($scope,$http) {
    $scope.sucList;



    $scope.getHttp= function(url , callback){
      var httpObject = $http.get(url);
      httpObject.then(function(promise){
        callback(promise.data);
      }, function(error){ console.log(error);})}

      $scope.postHttp = function(url,data,callback){
        var httpObject = $http.post(url,data);
        httpObject.then(function(promise){
          callback(promise.data);
        }, function(error){ console.log(error);})}

        $scope.init = function(){
          var url = 'http://192.168.1.203:58706/api/Sucursal';
          $http.get(url).then(function(msg){
            $scope.sucList= msg.data;
          });

        };



    $scope.edit=function(name,id){
    }
    $scope.delete=function(){
        this.sucList.pop();
        animation();
    }

  }]);

function newSuc(){
    window.location.href = "../Views/nuevSuc.html";
}

 //gestion medicamentos vista administrador

 app.controller("contMedAdmin",["$scope","$http",
  function($scope,$http) {
    $scope.medList;
    var client;
    $scope.getHttp= function(url , callback){
      var httpObject = $http.get(url);
      httpObject.then(function(promise){
        callback(promise.data);
      }, function(error){ console.log(error);})}

      $scope.postHttp = function(url,data,callback){
        var httpObject = $http.post(url,data);
        httpObject.then(function(promise){
          callback(promise.data);
        }, function(error){ console.log(error);})}

        $scope.init = function(){
          var url = 'http://192.168.1.203:58706/api/Productos';
          $http.get(url).then(function(msg){
            client = msg.data;
            $scope.medList = client;
          });

        }



        $scope.edit=function(name,id){
        }

        $scope.delete=function(){
          this.medList.pop();
          animation();
        }

      //this.getProducts();


      }]);

 function newProd(){
  window.location.href = "../Views/nuevMed.html";
}



//nuevo rol vista administrador

app.controller("contNuevRolAdmin", ["$scope","$http",
  function($scope,$http) {
    $scope.id = "";
    $scope.name="";
    $scope.desc="";

    $scope.createRol=function(nme,descr){
      var url='http://192.168.1.203:58706/api/Roles';
      var sendData={"Nombre": nme, "Descripcion":descr};

      alert(nme+descr);

      $http.post(url,sendData)
      .then(
        function successCallback(response){

        },function errorCallBack(response){

        });
      animation();
    };

  }]);



//controlador nuevo medicamento vista administrador


app.controller("contMedNuevAdmin", ["$scope","$http",
  function($scope,$http) {
    $scope.id = "";
    $scope.name="";
    $scope.presc="";
    $scope.house="";
    $scope.qty="";
    $scope.suc="";
    $scope.sucList;


    $scope.getHttp= function(url , callback){
      var httpObject = $http.get(url);
      httpObject.then(function(promise){
        callback(promise.data);
      }, function(error){ console.log(error);})}

      $scope.postHttp = function(url,data,callback){
        var httpObject = $http.post(url,data);
        httpObject.then(function(promise){
          callback(promise.data);
        }, function(error){ console.log(error);})}

        $scope.init = function(){
          var url = 'http://192.168.1.203:58706/api/Sucursal';
          $http.get(url).then(function(msg){
            $scope.sucList = msg.data;
          });

        }


        $scope.createProduct=function(){
          var url='http://192.168.1.203:58706/api/Roles';
          var sendData={"Nombre": nme, "Descripcion":descr};

          alert(nme+descr);

          $http.post(url,sendData)
          .then(
            function successCallback(response){

            },function errorCallBack(response){

            });
          animation();
        };

      }]);


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

//nueva sucursal vista administrador


 app.controller("contNuevSucAdmin", ["$scope","$http",

  function($scope,$http) {
    $scope.idEmp = "";
    $scope.name="";
    $scope.idCant="";
    $scope.idProv="";
    $scope.idDist="";
    $scope.dir="";
    $scope.lat="";
    $scope.lng="";

    $scope.empList;
    $scope.provList;
    $scope.cantList;
    $scope.distList;


    $scope.init = function(){
      var url = 'http://192.168.1.203:58706/api/Distrito';
      $http.get(url).then(function(msg){
        $scope.distList = msg.data;
        
      });
      url = 'http://192.168.1.203:58706/api/Cantones';
      $http.get(url).then(function(msg){
        $scope.cantList = msg.data;
      });
      url = 'http://192.168.1.203:58706/api/Empresa';
      $http.get(url).then(function(msg){
        $scope.empList = msg.data;
      });
      url = 'http://192.168.1.203:58706/api/Provincias';
      $http.get(url).then(function(msg){
        $scope.provList = msg.data;
      });

    }


    $scope.createStore=function(idE,nme,idC,idP,idD,dr,lt,ln){
      idE=idE.split("",1);
      idC=idC.split("",1);
      idP=idP.split("",1);
      idD=idD.split("",1);
      var url='http://192.168.1.203:58706/api/Sucursal';
      var sendData={"Nombre": nme, "detalleDireccion":dr, "idEmpresa":idE, "idCanton":idC, "idProvincia": idP, "idDistrito":idD, "Latitud": lt, "Longitud": ln , "Estado":1};


      $http.post(url,sendData)
      .then(
        function successCallback(response){

        },function errorCallBack(response){

        });
      animation();
    }

  }]);

//Vista estadistica

var chart = AmCharts.makeChart("chartdiv", {
  "type": "serial",
  "theme": "light",
  "marginRight": 70,
  "dataProvider": [{
    "country": "Talerdin",
    "visits": 200,
    "color": "#FF0F00"
  }, {
    "country": "Tabcin",
    "visits": 450,
    "color": "#FF6600"
  }, {
    "country": "Antifludes",
    "visits": 100,
    "color": "#FF9E01"
  }, {
    "country": "Condones",
    "visits": 1000,
    "color": "#FCD202"
  }, {
    "country": "Suero",
    "visits": 20,
    "color": "#F8FF01"
  }],
  "valueAxes": [{
    "axisAlpha": 0,
    "position": "left",
    "title": "Quantity"
  }],
  "startDuration": 1,
  "graphs": [{
    "balloonText": "<b>[[category]]: [[value]]</b>",
    "fillColorsField": "color",
    "fillAlphas": 0.9,
    "lineAlpha": 0.2,
    "type": "column",
    "valueField": "visits"
  }],
  "chartCursor": {
    "categoryBalloonEnabled": false,
    "cursorAlpha": 0,
    "zoomable": false
  },
  "categoryField": "country",
  "categoryAxis": {
    "gridPosition": "start",
    "labelRotation": 45
  },
  "export": {
    "enabled": true
  }

});






var chart = AmCharts.makeChart( "chartdivr", {
  "type": "pie",
  "theme": "light",
  "dataProvider": [ {
    "company": "BombaTica",
    "sell": 5000
  }, {
    "company": "Phischell",
    "sell": 3590
  }],
  "valueField": "sell",
  "titleField": "company",
   "balloon":{
   "fixedPosition":true
  },
  "export": {
    "enabled": true
  }
} );



  function isBlank(str) {
    return (!str || /^\s*$/.test(str));
  }





function animation() {
    var x = document.getElementById("snackbar")
    x.className = "show";
    setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
}
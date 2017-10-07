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
          var url = 'http://192.168.1.204:58706/api/Empleados';
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







//controlador roles vista administrador


app.controller("contRolAdmin", ["$scope","$http",
  function($scope,$http) {


    $scope.rollist=[
    {'name':'Cashier', 'desc': 'pay' ,'id': '40'},
    {'name':'Pharmacist', 'desc': 'medicaments' ,'id': '10'},
    {'name':'Cashier', 'desc': 'pay' ,'id': '30'},
    {'name':'Pharmacist', 'desc': 'medicaments' ,'id': '100'},
    ];


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
          var url = 'http://192.168.1.204:58706/api/Clientes';
          $http.get(url).then(function(msg){
            client= msg.data;
            $scope.clientlist = client;
          });

        };







        $scope.edit=function(){
        }
        $scope.delete=function(){
          this.rollist.pop();
          animation();
        }

      }]);


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
          var url = 'http://192.168.1.204:58706/api/Clientes';
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
    $scope.sucList=[
        {'name':'San Pedro', 'desc': 'Blanca' ,'admin': 'Marco'},
        {'name':'San Jose', 'desc': 'Verde' ,'admin': 'Fofo'},
        {'name':'Heredia', 'desc': 'Rojo' ,'admin': 'Vargas'},
        {'name':'Alajuela', 'desc': 'Negro' ,'admin': 'Pato'},
    ];



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
          var url = 'http://192.168.1.204:58706/api/Sucursal';
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
          var url = 'http://192.168.1.204:58706/api/Productos';
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


//controlador rol vista de administrador

 app.controller("contRolAdmin", 
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

  });

function newRol(){
    window.location.href="../Views/nuevRol.html";
}


//nuevo rol vista administrador

 app.controller("contNuevRolAdmin", 
  function($scope) {
    $scope.id = "";
    $scope.name="";
    $scope.desc="";


    $scope.createProduct=function(){
      alert($scope.id+','+$scope.name+','+$scope.desc);
      animation();
    }
  });



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
          var url = 'http://192.168.1.204:58706/api/Sucursal';
          $http.get(url).then(function(msg){
            $scope.sucList = msg.data;
          });

        }





        $scope.createProduct=function(){
          alert($scope.id+','+$scope.suc+','+$scope.presc);
          animation();
        }

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
    $scope.id = "";
    $scope.name="";
    $scope.desc="";
    $scope.admin="";
    $scope.dir="";
    $scope.lat="";
    $scope.lng="";



    $scope.createProduct=function(){
      alert($scope.id+','+$scope.lat+','+$scope.lng);
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









function animation() {
    var x = document.getElementById("snackbar")
    x.className = "show";
    setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
}
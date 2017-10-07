var app= angular.module("mainModule",[]);


//Controldor empleados vista administrador

app.controller("contEmplAdmin", 
  function($scope) {
    $scope.employeelist=[
        {'name':'Marco', 'tel': '2222222' ,'id': '40', 'rol':'Cajero'},
        {'name':'Adrian', 'tel': '8888888888' ,'id': '10', 'rol':'Farmaceutico'}, 
        {'name':'Andres', 'tel': '2333333333' ,'id': '30', 'rol':'Cajero'},
        {'name':'Rodolfo', 'tel': '8999999999' ,'id': '100', 'rol':'Farmaceutico'}
    ];



    $scope.edit=function(){
    }

    $scope.delete=function(){
        this.employeelist.pop();
        animation();
    }

  });







//controlador roles vista administrador


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


//controlador clientes vista administrador

app.controller("contClientAdmin",
  function($scope) {
    $scope.clientlist=[
        {'name':'Marco', 'tel': '2222222' ,'id': '40'},
        {'name':'Adrian', 'tel': '8888888888' ,'id': '10'},
        {'name':'Andres', 'tel': '2333333333' ,'id': '30'},
        {'name':'Rodolfo', 'tel': '8999999999' ,'id': '100'},
    ];



    $scope.edit=function(){
    }
    $scope.delete=function(){
        this.clientlist.pop();
        animation();
    }

  });

//controlador sucursales vista administrador

app.controller("contSucAdmin", 
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

  });

function newSuc(){
    window.location.href = "../Views/nuevSuc.html";
}

 //gestion medicamentos vista administrador

 app.controller("contMedAdmin", 
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

  });

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


app.controller("contMedNuevAdmin", 
  function($scope) {
    $scope.id = "";
    $scope.name="";
    $scope.presc="";
    $scope.house="";
    $scope.qty="";


    $scope.createProduct=function(){
      alert($scope.id+','+$scope.name+','+$scope.presc);
      animation();
    }


  });


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


 app.controller("contNuevSucAdmin", 
  function($scope) {
    $scope.id = "";
    $scope.name="";
    $scope.desc="";
    $scope.admin="";
    $scope.dir="";


    $scope.createProduct=function(){
      alert($scope.id+','+$scope.name+','+$scope.presc);
      animation();
    }

  });

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
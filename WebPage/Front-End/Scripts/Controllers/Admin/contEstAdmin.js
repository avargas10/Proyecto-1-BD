var productos=[];
angular.module("mainModule").controller("contEstAdmin",["$scope","$http",
function($scope,$http) {


  $scope.getHttp= function(url , callback){
    $scope.prod;
    var httpObject = $http.get(url);
    httpObject.then(function(promise){
      callback(promise.data);
    }, function(error){ console.log(error);})}

    $scope.postHttp = function(url,data,callback){
      var httpObject = $http.post(url,data);
      httpObject.then(function(promise){
        callback(promise.data);
      }, function(error){ console.log(error);})}

      $scope.init=function(){
        //var url = 'http://'+getIp()+':58706/api/Productos?idEmpresa='+empresaAdmin;
        //$http.get(url).then(function(msg){
        //  $scope.prod = msg.data;
        //});
        
        this.prod=[
            {"nombreEmpresa":"Bomba","nombreProducto":"talerdin","sumaCantidad":50},
            {"nombreEmpresa":"Bomba","nombreProducto":"gex","sumaCantidad":20},
            {"nombreEmpresa":"Bomba","nombreProducto":"suero","sumaCantidad":10},
            {"nombreEmpresa":"Bomba","nombreProducto":"condones","sumaCantidad":5}
          ];
          var sub=[];

          for(var i=0;i<this.prod.length;i++){
            sub=[];
            var nme=this.prod[i].nombreProducto;
            var qty=this.prod[i].sumaCantidad;
            sub.push(nme);
            sub.push(qty);
            alert(nme+qty);
            productos.push(sub);
            
          }

    chart.dataProvider = AmCharts.parseJSON(productos);
    chart.validateData();




      }

   //Vista estadistica
   alert('fuera');
      var chart = AmCharts.makeChart("chartdiv", {
      "type": "serial",
      "theme": "light",
      "marginRight": 70,
      "dataProvider": [{
        "country": productos[0][0],
        "visits": productos[0][1],
        "color": "#FF0F00"
      }, {
        "country": productos[1][0],
        "visits": productos[1][1],
        "color": "#FF6600"
      }, {
        "country": productos[2][0],
        "visits": productos[2][1],
        "color": "#FF9E01"
      }, {
        "country": productos[3][0],
        "visits": productos[3][1],
        "color": "#FCD202"
      }, {
        "country": productos[4][0],
        "visits": productos[4][1],
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


    

  





    }]);


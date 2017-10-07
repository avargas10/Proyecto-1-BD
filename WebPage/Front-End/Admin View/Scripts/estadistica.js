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
var globalImage="";
var app = angular.module("mainModule",
  ["ngRoute",
    'vcRecaptcha']);

app.config(function ( $routeProvider, $locationProvider) {
  $routeProvider.
    when('/Home', { templateUrl: '../Views/home-view.html', controller: 'mainController' }).
    when('/login', { templateUrl: '../Views/login-view.html', controller: 'userController' }).
    when('/userInfo', { templateUrl: '../Views/userInfo.html', controller: 'userController' }).
    when('/register', { templateUrl: '../Views/register.html', controller: 'userController' }).
    when('/pedido', { templateUrl: '../Views/pedido.html', controller: 'userController' }).
    when('/products', { templateUrl: '../Views/client.html', controller: 'userController' }).
    when('/stores', { templateUrl: '../Views/stores.html', controller: 'storeController' }).
    when('/storeProducts', { templateUrl: '../Views/storeProducts.html', controller: 'storeController' }).
    when('/myBag', { templateUrl: '../Views/myBag.html', controller: 'storeController' }).
    when('/order', { templateUrl: '../Views/order.html', controller: 'orderController' }).
    when('/allOrders', { templateUrl: '../Views/allOrders.html', controller: 'orderController' }).
    when('/accountVer', { templateUrl: '../Views/account.html', controller: 'userController' }).
    when('/prescription', { templateUrl: '../Views/prescription.html', controller: 'receipeController' }).
    when('/allForms', { templateUrl: '../Views/allForms.html', controller: 'receipeController' }).
    otherwise({ redirectTo: '/Home' });
  // $locationProvider.html5Mode(true);
});

var openFile = function(event) {
    var input = event.target;
    var reader = new FileReader();
    reader.onload = function(){
      var dataURL = reader.result;
      var output = document.getElementById('output');
      output.src = dataURL;
      globalImage = dataURL;
      
    };
    reader.readAsDataURL(input.files[0]);
 }
 


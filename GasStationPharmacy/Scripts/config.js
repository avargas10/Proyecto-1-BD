angular.module("mainModule").config(function ( $routeProvider, $locationProvider) {
    $routeProvider.
    //----------------------Shared Views------------------------------------------------------------
      when('/Home', { templateUrl: '../Views/Shared/home-view.html', controller: 'mainController' }).
      when('/login', { templateUrl: '../Views/Shared/login-view.html', controller: 'userController' }).
      when('/userInfo', { templateUrl: '../Views/Shared/editInfo.html', controller: 'userController' }).
      when('/register', { templateUrl: '../Views/Shared/register.html', controller: 'userController' }).
   //----------------------Client Views------------------------------------------------------------
      when('/products', { templateUrl: '../Views/Client/client.html', controller: 'userController' }).
      when('/stores', { templateUrl: '../Views/Client/stores.html', controller: 'storeController' }).
      when('/storeProducts', { templateUrl: '../Views/Client/storeProducts.html', controller: 'storeController' }).
      when('/myBag', { templateUrl: '../Views/Client/myBag.html', controller: 'storeController' }).
      when('/order', { templateUrl: '../Views/Client/order.html', controller: 'orderController' }).
      when('/newOrder', { templateUrl: '../Views/Client/newOrder.html', controller: 'orderController' }).
      when('/allOrders', { templateUrl: '../Views/Client/allOrders.html', controller: 'orderController' }).
      when('/accountVer', { templateUrl: '../Views/Client/account.html', controller: 'userController' }).
      when('/prescription', { templateUrl: '../Views/Client/prescription.html', controller: 'receipeController' }).
      when('/allForms', { templateUrl: '../Views/Client/allForms.html', controller: 'receipeController' }).
      when('/editForm', { templateUrl: '../Views/Client/editForm.html', controller: 'receipeController' }).
      when('/newIllness', { templateUrl: '../Views/Client/newIllness.html', controller: 'illController'}).
      when('/illnesses', { templateUrl: '../Views/Client/illnesses.html', controller: 'illController'}).
      when('/editIllness', { templateUrl: '../Views/Client/editIllness.html', controller: 'illController'}).
  //----------------------Admin Views------------------------------------------------------------
      when('/Admin/estadistica', { templateUrl: '../Views/Admin/estadistica.html', controller: 'userController' }).
      when('/Admin/gclientes', { templateUrl: '../Views/Admin/gclientes.html', controller: 'userController' }).
      when('/Admin/gempleados', { templateUrl: '../Views/Admin/gempleados.html', controller: 'userController' }).
      when('/Admin/gmedicamentos', { templateUrl: '../Views/Admin/gmedicamentos.html', controller: 'userController' }).
      when('/Admin/groles', { templateUrl: '../Views/Admin/groles.html', controller: 'userController' }).
      when('/Admin/gsucursales', { templateUrl: '../Views/Admin/gsucursales.html', controller: 'userController' }).
      when('/Admin/nuevCliente', { templateUrl: '../Views/Admin/nuevCliente.html', controller: 'userController' }).
      when('/Admin/nuevEmpleado', { templateUrl: '../Views/Admin/nuevEmpleado.html', controller: 'userController' }).
      when('/Admin/nuevMed', { templateUrl: '../Views/Admin/nuevMed.html', controller: 'userController' }).
      when('/Admin/nuevRol', { templateUrl: '../Views/Admin/nuevRol.html', controller: 'userController' }).
      when('/Admin/nuevSuc', { templateUrl: '../Views/Admin/nuevSuc.html', controller: 'userController' }).
      when('/Stores/pedidosStore', { templateUrl: '../Views/Stores/pedidosStore.html', controller: 'userController' }).
      when('/Stores/ProductsxOrder', { templateUrl: '../Views/Stores/ProductsxOrder.html', controller: 'userController' }).
  //----------------------Main View------------------------------------------------------------
      otherwise({ redirectTo: '/Home' });
    // $locationProvider.html5Mode(true);
  });
angular.module("mainModule").controller("mainController", ["$scope", "$http", "$location",
 "$routeParams", 'userService','storeService','orderService','receipeService',

  function ($scope, $http, $location, $routeParams, userService,storeService,orderService,receipeService) {

    $scope.isActive = function () {
      return userService.getActive();
    }
    $scope.isEmpActive = function () {
      return userService.getEmpActive();
    }
    $scope.isAdmin=function(){return userService.getAdmin();}
    $scope.getUser = function () {
      return userService.getUser();
    }
    $scope.logOutUser = function () {
      userService.logout();
      storeService.cleanBag();
      orderService.clean();
      receipeService.cleanMeds();
      $location.path("/Home");
    };

    $scope.goTo = function (option) {
      switch (option) {
        case 'home':
          $location.path("/Home");
          break; 
        case 'illnesses':
          $location.path("/illnesses");
          break;
        case 'newIllness':
          $location.path("/newIllness");
          break;
        case 'editIllnesses':
          $location.path("/editIllnesses");
          break;
          
        case 'verify':
          $location.path("/accountVer");
          break;
        case 'order':
          $location.path("/order");
          break;
        case 'products':
          $location.path("/products");
          break;
        case 'userInfo':
          $location.path("/userInfo");
          break;
        case 'login':
          $location.path("/login");
          break;
        case 'mybag':
          $location.path("/myBag");
          break;
        case 'allForms':
          $location.path("/allForms");
          break;
          case 'allOrders':
          $location.path("/allOrders");
          break;
        case 'storeProducts':
          $location.path("/storeProducts");
          break;
        case 'prescription':
          $location.path("/prescription");
          break;
        case 'signup':
          $location.path("/register");
          break;
        case 'stores':
          $location.path("/stores");
          break;
        case 'gmedicamentos':
          $location.path("/Admin/gmedicamentos");
          break;
        case 'gclientes':
          $location.path("/Admin/gclientes");
          break;
        case 'gempleados':
          $location.path("/Admin/gempleados");
          break;
        case 'groles':
          $location.path("/Admin/groles");
          break;
        case 'gsucursales':
          $location.path("/Admin/gsucursales");
          break;
        case 'nuevMed':
          $location.path("/Admin/nuevMed");
          break;
        case 'nuevSuc':
          $location.path("/Admin/nuevSuc");
          break;
        case 'nuevRol':
          $location.path("/Admin/nuevRol");
          break;
        case 'estadistica':
          $location.path("/Admin/estadistica");
          break;
        case 'newOrder':
          $location.path("/newOrder");
          break;
        case 'nuevEmpleado':
          $location.path("/Admin/nuevEmpleado");
          break;
        case 'pedidosStore':
          $location.path("/Stores/pedidosStore");
          break;

        default:
      }
    };


    $scope.getHttp = function (url, callback) {
      var httpObject = $http.get(url);
      httpObject.then(function (promise) {
        callback(promise.data);
      }, function (error) { alert(error.data); console.log(error); })
    }

    $scope.postHttp = function (url, data, callback) {
      var httpObject = $http.post(url, data);
      httpObject.then(function (promise) {
        callback(promise.data);
      }, function (error) { alert(error.data);  console.log(error); })
    }
  }
]);
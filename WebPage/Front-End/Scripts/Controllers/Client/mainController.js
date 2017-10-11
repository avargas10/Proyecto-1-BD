angular.module("mainModule").controller("mainController", ["$scope", "$http", "$location", "$routeParams", 'userService',

  function ($scope, $http, $location, $routeParams, userService) {

    $scope.isActive = function () {
      return userService.getActive();
    }
    $scope.getUser = function () {
      return userService.getUser();
    }
    $scope.logOutUser = function () {
      userService.logout();
      $location.path("/Home");
    };

    $scope.goTo = function (option) {
      switch (option) {
        case 'home':
          $location.path("/Home");
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
        default:
      }
    };


    $scope.getHttp = function (url, callback) {
      var httpObject = $http.get(url);
      httpObject.then(function (promise) {
        callback(promise.data);
      }, function (error) { console.log(error); })
    }

    $scope.postHttp = function (url, data, callback) {
      var httpObject = $http.post(url, data);
      httpObject.then(function (promise) {
        callback(promise.data);
      }, function (error) { console.log(error); })
    }
  }
]);
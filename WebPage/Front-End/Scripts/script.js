var app = angular.module("mainModule",["ngRoute",'vcRecaptcha']);

app.config(function($routeProvider, $locationProvider){
	$routeProvider.
	when('/Home', {templateUrl: '../Views/home-view.html', controller: 'mainController'}).
	when('/login',{templateUrl: '../Views/login-view.html',controller: 'userController'}).
	when('/register',{templateUrl: '../Views/register.html',controller: 'userController'}).
	when('/pedido',{templateUrl: '../Views/pedido.html',controller: 'userController'}).
		otherwise({ redirectTo: '/pedido'});
	// $locationProvider.html5Mode(true);
});

app.service('userService', function() {
	var currentUser;
	var logged;
	this.getUser = function() {
		return currentUser;		
		}
	this.getActive = function() {
		return logged;		
		}

	this.setUser = function(user) {
			currentUser = user;
			logged = true;
		}
	this.logout = function(){
		console.log(currentUser," logged out");
		currentUser = null;
		logged = false;
	}
});


app.controller("mainController",["$scope","$http","$location","$routeParams",'userService',

function($scope,$http,$location,$routeParams,userService){

  $scope.isActive = function(){
  	return userService.getActive();
  }
  $scope.getUser=function(){
  	return userService.getUser();
  }
  $scope.logOutUser = function(){
	  	userService.logout();
	};
  $scope.login = function(){ 
    $location.path("/login");
  };
    $scope.register = function(){ 

    $location.path("/register");
  };
  $scope.forgot = function(){ 
    $location.path("/Home");
  };
  $scope.chooseOption = function(option){ 
    switch (option) {
            case 'createUser':
                $location.path("/register");
                break;
            case 'deleteUser':
                $location.path("/deleteUser");
                break;
            default:
        }
  };
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
  }
 
]);
app.controller("userController",["$scope","$http","$location","$routeParams","userService",
function($scope,$http,$location,$routeParams,userService){
  var states;
  var cities;
  var districts;
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
  
  function isBlank(str) {
    return (!str || /^\s*$/.test(str));
  }


  $scope.loginUser = function(username,password,getCaptcha){
  	if(!isBlank(username)  && !isBlank(password) ){
    var url = 'http://10.0.1.17:58706/api/Clientes?username=' + username + '&pass=' + password;
    $http.post(url).then(function(msg){
    	if(msg){
    		userService.setUser(username);
    		$location.path("/Home");
    	}
    }); }
    else{
    	alert("Error(01): Can't sign in, space in blank or not getCaptcha");
    }
};

$scope.UpdateDirection = function(){
    var url = 'http://10.0.1.17:58706/api/Provincias';
    $http.get(url).then(function(msg){
    		states = msg.data;
    	});
    $scope.states = states;
};
$scope.UpdateCities = function(_id){
    var url = 'http://10.0.1.17:58706/api/Cantones?Provincia='+_id;
    $http.get(url).then(function(msg){
    		cities = msg.data;
    	});
    $scope.cities = cities;
};


}]);

app.controller("contPedido", 
  function($scope) {
    $scope.id = "";
    $scope.tel="";
    $scope.sucursal="";
    $scope.fecha="";
    $scope.listProducts=[
      {'img':'../images/med.png', 'producto': 'Tabcin' ,'precio': '40', 'prescripcion': 'No' },
      {'img':'../images/iny.png', 'producto': 'Gex' ,'precio': '10', 'prescripcion': 'No' },
      {'img':'../images/med.png', 'producto': 'Talerdin' ,'precio': '30', 'prescripcion': 'No' },
      {'img':'../images/iny.png', 'producto': 'Inyeccion' ,'precio': '100', 'prescripcion': 'Si' },
      {'img':'../images/med.png', 'producto': 'Suero' ,'precio': '60', 'prescripcion': 'Si' }
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
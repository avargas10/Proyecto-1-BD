angular.module('myAppUserCtrl', []).controller("userController",["$scope","$http","$location","$routeParams","userService",
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
    var url = 'http://10.0.1.20:58706/api/Clientes?username=' + username + '&pass=' + password;
    $http.post(url).then(function(msg){
    	if(msg.data){
    		userService.setUser(username);
    		$location.path("/Home");
      }
      else{
        alert("Error(02): Can't sign in, username or password incorrect");
      }
    }); }
    else{
    	alert("Error(01): Can't sign in, space in blank or not getCaptcha");
    }
};

$scope.UpdateDirection = function(){
  console.log("direction update");
    var url = 'http://10.0.1.20:58706/api/Provincias';
    $http.get(url).then(function(msg){
    		states = msg.data;
    	});
    $scope.states = states;

};

$scope.UpdateCities = function(_id){
    var url = 'http://10.0.1.20:58706/api/Cantones?idProvincia='+_id;
    $http.get(url).then(function(msg){
        cities = msg.data;
        console.log(cities);
    	});
    $scope.cities = cities;
};


}]);
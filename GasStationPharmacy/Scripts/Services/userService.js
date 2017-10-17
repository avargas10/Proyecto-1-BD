angular.module("mainModule").service('userService', function () {
	var currentUser;
	var logged;
	var sucursal;
	this.getUser = function () {
	  return currentUser;
	}
	this.getActive = function () {
	  return logged;
	}
  
	this.setUser = function (user) {
	  currentUser = user;
	  logged = true;
	}
	this.getSucursal= function () {
		return sucursal
	}
	this.setSucursal = function (suc) {
	  sucursal = suc;
	}
	this.logout = function () {
	  console.log(currentUser, " logged out");
	  currentUser = null;
	  logged = false;
	}
  });
angular.module("mainModule").service('userService', function () {
	var currentUser;
	var logged;
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
	this.logout = function () {
	  console.log(currentUser, " logged out");
	  currentUser = null;
	  logged = false;
	}
  });
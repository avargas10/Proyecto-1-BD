angular.module("mainModule").service('userService', function () {
	var currentUser;
	var logged;
	var empLogged;
	var sucursal;
	var company;
	this.getUser = function () {
	  return currentUser;
	}
	this.getActive = function () {
	  return logged;
	}
	this.getEmpActive = function () {
	  return empLogged;
	}
	this.setEmpActive=function(){empLogged=true;}
	this.setActive=function(){logged=true;}
	this.setUser = function (user) {
	  currentUser = user;
	}
	this.getSucursal= function () {
		return sucursal
	}
	this.setSucursal = function (suc) {
	  sucursal = suc;
	}
	this.setCompany=function(pCom){
		company=pCom;
	}
	this.getCompany=function(){return company;}
	
	this.logout = function () {
	  console.log(currentUser, " logged out");
		currentUser = null;
		sucursal= null;
		company=null;
		logged = false;
		empLogged=false;
	}
  });
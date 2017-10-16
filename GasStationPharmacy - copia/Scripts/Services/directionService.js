
angular.module("mainModule").service('directionService', function () {
    var state;
    var district;
    var city;
    var id;
    var update=false;
  
    this.setId = function (pId) {
      console.log("id: " + pId);
      id = pId;
    }
    this.setUpdate=function(){update=false;}
    this.getId = function () { return id; }
    this.setState = function (pState) {
      state = pState;
    }
    this.setCity = function (pCity) {
      city = pCity;
    }
    this.setDisctrict = function (pDistrict) {
      district = pDistrict;
      update=true;
    }
    this.update=function(){return update;}
    this.getState = function () { return state; }
    this.getCity = function () { return city; }
    this.getDistrict = function () { return district; }
  
  });
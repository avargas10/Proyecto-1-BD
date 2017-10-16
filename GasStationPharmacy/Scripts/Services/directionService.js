
angular.module("mainModule").service('directionService', function () {
    var state;
    var district;
    var city;
    var id;
  
    this.setId = function (pId) {
      console.log("id: " + pId);
      id = pId;
    }
    this.getId = function () { return id; }
    this.setState = function (pState) {
      state = pState;
    }
    this.setCity = function (pCity) {
      city = pCity;
    }
    this.setDisctrict = function (pDistrict) {
      district = pDistrict;
    }
    this.getState = function () { return state; }
    this.getCity = function () { return city; }
    this.getDistrict = function () { return district; }
  
  });
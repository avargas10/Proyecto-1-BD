
angular.module("mainModule").service('illnessService', function () {
    var ill;
    var update=false;
  
    this.setIll = function (pIll) { 
      ill = pIll;
    }
    this.getIll = function () { return ill;}
  
  });
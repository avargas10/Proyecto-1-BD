angular.module("mainModule").service('storeService', function () {
    var store;
    var bag = [];
    var totalPrice;
    this.setTotalPrice = function (pPrice) {
      totalPrice = pPrice;
    }
    this.cleanBag= function(){bag = [];}
    this.isEmpty = function(){
      if(bag.length==0){return true;}
      return false;
    }
    this.setStore = function (pStore) {
      store = pStore;
    }
    this.setBag = function (pBag) {
      console.log("bag: "+pBag);
      bag = pBag;
    }
    this.getBag=function(){
      console.log(bag);
      return bag;}
    
      this.getTotalPrice=function(){return totalPrice;}
    
    this.addToBag=function(product){
      bag.push(product);
      console.log(bag);
    }
    this.getStore = function () { return store; }
  });
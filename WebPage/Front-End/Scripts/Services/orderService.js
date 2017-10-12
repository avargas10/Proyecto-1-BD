angular.module("mainModule").service('orderService',function(){
    var newOrder = 
    {
      idSucursal:null,
      phoneNumber:null,
      idClient:null,
      image:null,
      date:null,
      products:null
    };
    this.setImage=function(image){
       newOrder.image = image;
    }
   
    this.clean=function(){
      newOrder= {
       idSucursal:null,
       phoneNumber:null,
       idClient:nul,
       image:null,
       date:null,
       products:null
     };
     imageActive=false;
    }
   
    this.getOrder=function(){return newOrder;}
   
    this.setValues = function(Sucursal,phoneNumber,idClient,date){
      newOrder.idSucursal=Sucursal;
      newOrder.phoneNumber=phoneNumber;
      newOrder.idClient=idClient;
      newOrder.date=date;
    }
    this.setTempValues = function(Sucursal,phoneNumber,idClient,date,products,image){
      newOrder.idSucursal=Sucursal;
      newOrder.phoneNumber=phoneNumber;
      newOrder.idClient=idClient;
      newOrder.date=date;
      newOrder.image=image;
      newOrder.products=products;
    }
   
   
   });
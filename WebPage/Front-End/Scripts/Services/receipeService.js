angular.module("mainModule").service('receipeService',function(){
    var meds=[];
    this.medsEmpty = function(){
      if(meds.length==0){
        return true;
      }
      else{return false;}
    }
    this.addMed=function(med){
       meds.push(med);
       console.log("Med "+med);
    }
   
    this.cleanMeds=function(){
      meds = [];
      console.log("meds clean");
    }
   
    this.setMeds=function(pMeds){
      meds = pMeds; 
    }
  
    this.getMeds=function(){return meds;}
    
   });
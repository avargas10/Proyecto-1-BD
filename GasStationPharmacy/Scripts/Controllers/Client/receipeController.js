angular.module("mainModule").controller("receipeController", [ "orderService","receipeService","$scope", "$http", "$location", "$routeParams", "userService", 'storeService',  
function (orderService,receipeService,$scope, $http, $location, $routeParams, userService, storeService) {
  $scope.addReceipe=function(){
    var url = 'http://'+getIp()+':58706/api/Recetas';
    var sendData= 
    {
      idCliente: userService.getUser().Cedula,
      Imagen: globalImage,
      Estado: 1,
    };
    if(!receipeService.medsEmpty()){
      if(!isEmpty(globalImage)){
        sendData.Imagen= globalImage.split(",")[1];
        $scope.postHttp(url,sendData,(data)=>{
          console.log("Receipe id: "+data.idReceta);
          globalImage="";
          addReceipeDetail(data.idReceta);
          $location.path("/allForms");
        })
      }
      else{
        alert("Form Photo is requiere");
      }}
      else{
        alert("No Medicines Added to Form");
      }
    }

    function getProSucursal(pro,suc,id){
      var url = 'http://'+getIp()+':58706/api/Productos?Suc='+suc+'&CodProd='+id;
      var send;
      $scope.postHttp(url,send,(data)=>{
        if(data==null){alert(pro.Nombre+' Out Stock'); return null; }
        console.log("producto: "+data);
        pro['Precio']=data.Precio;
        var product= {product:pro,totalPrice:data.Precio};
        storeService.addToBag(product);
      })
    
    }

    function getProduct(id,suc){
      console.log("idMedicamento "+id);
      var url = 'http://'+getIp()+':58706/api/Productos?Cod='+id;
      var send;
      $scope.postHttp(url,send,(data)=>{
        console.log("producto: "+data.data);
        getProSucursal(data,suc,id);
      })
    }
    
    $scope.addToBag=function(form){
      if(isBlank(storeService.getStore())){
        alert("First select a store");
        return null;
      }
      for (var index = 0; index < form.length; index++) {
         if(!alreadyInBag(form[index])){
            getProduct(form[index].idMedicamento,storeService.getStore().idSucursal);
         }
        
      }
      $location.path('/myBag');
    }

    $scope.goBack=function(){
      receipeService.cleanMeds();
      $location.path("/myBag");
    }
    
    $scope.getAllForms=function(){
      var url = 'http://'+getIp()+':58706/api/Recetas/'+userService.getUser().Cedula;
      $http.get(url)
      .then(function successCallback(data) {
        console.log(data);
        $scope.forms = data.data;
      },
      function errorCallback(response) {
        alert(response);
      });
    };
    

    $scope.updateForm=function(form){
      var url = 'http://'+getIp()+':58706/api/Recetas';
      var sendData={
        idReceta: form[0].idReceta,
        idCliente: form[0].idCliente,
        Imagen: currentImage,
        Estado: 3,
        idDoctor: 0
      }
      
      if(!isEmpty(globalImage)){
        sendData.Imagen=globalImage.split(",")[1];
      }
      console.log(sendData);
      $http.put(url,sendData)
      .then(
          function(response){
            // success callback
            console.log("update");
          }, 
          function(response){
            // failure callback
          }
       );
    }


    function productIndexMeds(product){
      var meds = receipeService.getMeds();
      for (var index = 0; index < meds.length; index++) {
        if(product.idProducto==meds[index].idProducto)
        {return index;}
      }
      return -1;
    }
    $scope.deleteFromMeds=function(product){
      var meds = receipeService.getMeds();
      meds.splice(productIndexMeds(product),1);
      receipeService.setMeds(meds);
    }
    
    function addReceipeDetail(id){
      var url = 'http://'+getIp()+':58706/api/DetalleReceta';
      var meds = receipeService.getMeds();
      for (var index = 0; index < meds.length; index++) {
        var sendData = {
          idMedicamento: meds[index].idProducto,
          idReceta: id
        };
        $scope.postHttp(url,sendData,(data)=>{
          console.log("added form detail");
        })
      }
      receipeService.cleanMeds();
      
    }
    
    $scope.addProduct=function(product){
      receipeService.addMed(product);
      
    }
    function isEmpty(str) {
      return (!str || 0 === str.length);
    }
    
    function confirmDelete(form){
      url= 'http://'+getIp()+':58706/api/Recetas/'+form;
      $http.delete(url)
      .then(function successCallback(data) {
        alert("Form "+form+" deleted");
        $scope.getAllForms();
      },
      function errorCallback(response) {
        alert(response);
      });
    }
    
    
    $scope.deleteForm=function(form){
      confirmDelete(form.idReceta);
    }
    
    $scope.getAllProducts = function () {
      var url = 'http://'+getIp()+':58706/api/Productos';
      $http.get(url)
      .then(function successCallback(data) {
        console.log("all products: "+data.data);
        $scope.AllProducts = data.data;
      },
      function errorCallback(response) {
        alert(response);
      });
    }
    
    $scope.myForm =function(form){
      console.log("order "+form);
      url= 'http://'+getIp()+':58706/api/Recetas?idRec='+form;
      $http.get(url)
      .then(function successCallback(data) {
        console.log(data);
        receipeService.setForm(data.data);
        $scope.getEditForm();
      },
      function errorCallback(response) {
        alert(response);
      });
    }

    $scope.editMyForm=function(form){
      console.log("order "+form);
      url= 'http://'+getIp()+':58706/api/Recetas?idRec='+form;
      $http.get(url)
      .then(function successCallback(data) {
        console.log(data);
        receipeService.setForm(data.data);
        $location.path('/editForm'); 
      },
      function errorCallback(response) {
        alert(response);
      });
    }
    
    function alreadyInBag(product){
      var bag = storeService.getBag();
      for (var index = 0; index < bag.length; index++) {
        if(product.idMedicamento==bag[index].product.idProducto)
        {return true;}
      }
      return false;
    }
    
    $scope.deleteDetail=function(detail,form,products){
      console.log("detail: "+detail);
      if(products.length==1){
        alert("Form can't be empty");
        return null;
      }
      url= 'http://'+getIp()+':58706/api/DetalleReceta?idReceta='+form+'&idProducto='+detail;
      $http.delete(url)
      .then(function successCallback(data) {
        alert("Product "+detail+" deleted");
        $scope.myForm(form);
      },
      function errorCallback(response) {
        alert(response);
      });
    }
    
    $scope.getEditForm=function(){
      currentImage = receipeService.getForm()[0].Imagen;
      $scope.editForm = receipeService.getForm();
    }
    
    $scope.alreadyInForm=function(product){
      var meds = receipeService.getMeds();
      for (var index = 0; index < meds.length; index++) {
        if(product.idProducto==meds[index].idProducto)
        {return true;}
      }
      return false;
    }
  }]);
var globalImage="";
var empresaAdmin="1";
var app = angular.module("mainModule",
  ["ngRoute",
    'vcRecaptcha']);

var openFile = function(event) {
    var input = event.target;
    var reader = new FileReader();
    reader.onload = function(){
      var dataURL = reader.result;
      var output = document.getElementById('output');
      output.src = dataURL;
      globalImage = dataURL;
      
    };
    reader.readAsDataURL(input.files[0]);
 }
        
      function isBlank(str) {
        return (!str || /^\s*$/.test(str));
      }
    
    function animation() {
        var x = document.getElementById("snackbar")
        x.className = "show";
        setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
    }

    function getIp(){
      var ipPato = "192.168.1.68";
      var ipVargas = "localhost";
      return ipVargas;
    }
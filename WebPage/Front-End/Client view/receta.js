function submitReceta(){
	var id, num, list, img, imgb;
	var reader  = new FileReader();


	id=document.getElementById("idUser").value;
	num=document.getElementById("numDoctor").value;
	list=document.getElementById("inputList").value;
	img=document.getElementById("inputFile").value;

}


var openFile = function(event) {
    var input = event.target;

    var reader = new FileReader();
    reader.onload = function(){
      var dataURL = reader.result;
      var output = document.getElementById('output');
      output.src = dataURL;
      
    };
    reader.readAsDataURL(input.files[0]);
 }
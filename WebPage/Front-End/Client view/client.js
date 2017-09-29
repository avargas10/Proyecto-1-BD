var productos=[a,b,c];

function shopCart() {
    window.location.href = "pedido.html";

}

function cart(){
	productos=mp.innerText;
	alert(productos);
}

function add() {
    var x = document.getElementById("snackbar")
    x.className = "show";
    setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
}
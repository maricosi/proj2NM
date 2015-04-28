/**
 * 
 */

function changeFormAction(){
	var tipo = "";
	if (document.getElementById("calctypeform:tipo:0").checked){
		tipo = document.getElementById("calctypeform:tipo:0").value;
	}else if (document.getElementById("calctypeform:tipo:1").checked){
		tipo = document.getElementById("calctypeform:tipo:1").value;
	}
	
	
	
	document.getElementById("calctypeform").action =  tipo+".xhtml";
	
}
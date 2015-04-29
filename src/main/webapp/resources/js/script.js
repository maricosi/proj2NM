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


function reactToChangedRadio(){
    alert("I'm in!");
    var myval;
     for(i=0;i<3;i++){
             if(document.forms['myFormId']['myFormId:myRadio'][i].checked == true ){
                 myval = document.forms['myFormId']['myFormId:myRadio'].text/value;
             }
         }
         alert( "val = " + myval );
 }



function uncheckRadioButtons(radioButton) {
	var selectedRadioButton;
	   if (selectedRadioButton != null) {
	      selectedRadioButton.checked = false;
	   }

	   selectedRadioButton = radioButton;
	   selectedRadioButton.checked = true;
	}
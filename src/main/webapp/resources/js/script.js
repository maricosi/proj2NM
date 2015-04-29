/**
 * 
 */

function redirect(){
	if(document.forms['header']['header:tipo'][0].checked == true){

		document.location.href = '../pages/basic.xhtml'
	}else if(document.forms['header']['header:tipo'][1].checked == true){
		document.location.href = '../pages/scientific.xhtml'
	}
}
function mostrarFondeo(operacion){
	url = contextPath+'/match/fondearOperacion.faces'
	+ '?idOperacion=' + operacion;
	mostrarDetalle('Fondeo de Valores',url);
	
}

function deshabilitaBtnFondeoGuardar() {
	var divItems=document.getElementById("divBtnGuardar").childNodes;																
	for(var j=0; j<divItems.length ; j++){
		if(divItems[j].type)
		  divItems[j].disabled=true;		  				  								
	}
}

function habilitaBtnFondeoGuardar() {
	var divItems=document.getElementById("divBtnGuardar").childNodes;																
	for(var j=0; j<divItems.length ; j++){
		if(divItems[j].type)
		  divItems[j].disabled=false;		  				  								
	}
}



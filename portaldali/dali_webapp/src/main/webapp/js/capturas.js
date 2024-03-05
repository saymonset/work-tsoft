


function removeApplet(){
	   var divApplet=document.getElementById('firmaDivId');
	   var divItems=null;
	   
	   if(divApplet != null && divApplet.childNodes !=null){
	   		divItems=divApplet.childNodes;	   	      
		   for(var j=0; j < divItems.length ; j++){
			   divApplet.removeChild(divItems[j]);
		   }	
	   }
}

function bloquearSeccionBotones(btnGuardar, btnLimpiar) {
    deshabilitarBoton(btnGuardar); deshabilitarBoton(btnLimpiar);
    $j("[id$='botones']").css("display", "none");
    $j("#mensajeEnviando").css("display", "inline");
}

function desbloquearSeccionBotones(btnGuardar, btnLimpiar) {
    habilitarBoton(btnGuardar); habilitarBoton(btnLimpiar);
    $j("[id$='botones']").css("display", "inline");
    $j("#mensajeEnviando").css("display", "none");
}
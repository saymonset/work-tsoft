function imagenCargaBeneTemp(){
	$j("#image_carga").css("display", "inline");		
}

function lockBotonIniciarProceso(){
	var boton = $j("input[id*='iniciarProceso']").get();		
	boton.disabled=true;	
}

function unlockBotonIniciarProceso(){
	var boton = $j("input[id*='iniciarProceso']").get();			
	boton.disabled=false;		
}

function ocultarImagenCarga(){
	$j("#image_carga").css("display", "none");
}
/**
 * @author Jacito
 */

/**
	Funcion que coloca el mensaje ISO del registro seleccionado en el text area para su visualizacion.
 */
function actualizaMensaje(nombreForma, idmensaje) {
	var elementoMensajeSwift = nombreForma + ':swiftMensaje';
	 var areaMensaje=document.getElementById(elementoMensajeSwift);	 
	 var swift = $j("input[id*="+idmensaje+"]").get();
	 areaMensaje.value=swift[0].value;
	 window.scrollTo(0,0);
}

/** 
	Funcion que marca el registro del cual se esta consultando el mensaje ISO para distinguirlo de los demas.
*/
function marcar(id) {	
	$j(".renglonTablaUno").css("background","white");
	$j(".renglonTablaDos").css("background","LightGrey ");	
	$j("."+id).parents("tr:eq(0)").css("background","yellow");
}
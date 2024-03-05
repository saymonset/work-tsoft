
/**
 * 
 */

function validarResultados(link) { 
	var msg = jQuery("#mensaje").text();
	if(msg != null && msg != ""){
		jQuery('#divWarningsPopUp').fadeIn(1000);
		setTimeout("limpiarMensajesAlert()",4000);
	}
	jQuery(link).show();
}

function limpiarMensajesAlert() {
	jQuery('#divWarningsPopUp').fadeOut("slow");
}

function deshabilitarLinkCancelar(link) {
	jQuery(link).hide();
} 

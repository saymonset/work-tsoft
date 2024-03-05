function validaCambioPassword() {
	var $j = jQuery.noConflict();
	var passwordActual = $j('input[id$=passwordActual]').prop('value');
	var passwordNuevo1 = $j('input[id$=passwordNuevo]').prop('value');
	var passwordNuevo2 = $j('input[id$=repeticionPasswordNuevo]').prop('value');


	if(passwordActual === undefined || passwordActual === "") {
		$j('.warning').html('Debe proporcionar la contrase&#241;a actual.');
		$j('#mensajes').show(500);
		$j('input[id$=passwordActual]').focus();
		return false;
	}

	if(passwordNuevo1 === undefined || passwordNuevo1 === "") {
		$j('.warning').html('Debe proporcionar la nueva contrase&#241;a.');
		$j('#mensajes').show(500);
		$j('input[id$=passwordNuevo]').focus();
		return false;
	}

	if(passwordNuevo2 === undefined || passwordNuevo2 === "") {
		$j('.warning').html('Debe confirmar la nueva contraseña.');
		$j('#mensajes').show(500);
		$j('input[id$=repeticionPasswordNuevo]').focus();
		return false;
	}

	if(passwordNuevo1 != passwordNuevo2) {
		$j('.warning').html('Las contrase&#241;as nuevas no conciden.');
		$j('#mensajes').show(500);
		$j('input[id$=passwordNuevo]').focus();
		return false;
	}

	$j('#mensajes').hide(500);
	return true;
}

function confirmarCambioPassword(diasPorVencer) {
	if(confirm('Su contraseña vence en ' + diasPorVencer + ' días. Si desea cambiarla ahora,\nhaga clic en Aceptar; si quiere cambiarla después haga clic en Cancelar. ')) {
		document.getElementById('loginForm:btnCambiarPassword').click();		
	}
	else {
		document.getElementById('loginForm:btnNavegarIndex').click();
	}
}

function avisoCambioPassword() {
		alert('Su contraseña ya venció. Debe de cambiarla.');
		document.getElementById('loginForm:btnCambiarPassword').click();
}

$j(document).ready(function () {
		$j(".borrar").prop("value","");
		$j("input[id$='inputCaptcha']").prop("value","");
});

/**
 * Función que da click al boton oculto para iniciar sesión en el sistema.
 */
function initLogin() {
	var tipo = $j("input[id$='tipoAutenticacion']").val();
	//login con pwd solamente
	if(tipo == 0) {
		$j("input[id$='btnIniciaSesion']").click();
	}
}
	function validarForma() {
		
		var $j = jQuery.noConflict();
		var usuario = $j('input[id$=usuario]').attr('value');
		var password = $j('input[id$=password]').attr('value');
		var inputCaptcha = $j('input[id$=inputCaptcha]').attr('value');
		
		if(usuario === undefined || usuario === "") {
			
			$j('.warning').html('Debe proporcionar el usuario para poder iniciar sesi&oacute;n.');
			$j('#mensajes').show(500);
			$j('input[id$=usuario]').focus();
			
			return false;
		}
		
		if(password === undefined || password === "") {
			
			$j('.warning').html('Debe proporcionar su contrase&ntilde;a para poder iniciar sesi&oacute;n.');
			$j('#mensajes').show(500);
			$j('input[id$=password]').focus();
			
			return false;
		}	
		
		
		$j('#mensajes').hide(500);
		return true;	
	}
	
function rederigirPortalDali() {
	window.parent.location.href='/dali/login.faces';
}
	
$j(document).ready(function () {
		$j(".borrar").attr("value","");
});
	

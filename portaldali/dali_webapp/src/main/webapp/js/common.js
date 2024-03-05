var iniciarMonitorPeticionReporte;

$j(document).on('ready',function(){
		$j('#usrmessages').dialog(
				{ 
					autoOpen:false,
					height:650,
					maxheight:1200,
					maxWidth:1200,
					modal: true,
					resizable: false,
					title:'Mensajes',
					width:950,
					zIndex:10003
				 }
			);
	});

function mostrarMensajesUsr(url){	
	
		$j('#usrmessages').dialog('open');
		$j('#usrmessages').prop('src',url);
		$j('#usrmessages').css('width','99%');
		
		$j('.ui-widget-overlay') .on("click", function() {
		    $j('#usrmessages').dialog('close');
		});
	
}

function addLoader(id) {
	if ($j("#loader" + id).prop("id") === undefined) {
		$j("input[id$='" + id + "']").after("<img src=\"" + contextPath + "/images/loading1.gif\" border=\"0\" id=\"loader" + id + "\" />");
	}
}
function removeLoader(id) {
	if ($j("#loader" + id)) {
		$j("#loader" + id).remove();
	}
}

function establecerFoco(id) {
	$j("input[id$='" + id + "']").focus();
}

window.alert = function(txt) {
	mostrarMensajeAlert(txt);
};
function confirmarOperacionTesoreria(){
var output="Se Proceder\u00e1 a Efectuar el Retiro. \u00bfDesea Continuar?";
if(!confirm(output))
return false;
return true;
}
function confirmarOperacionGeneral(){
var output="\u00bfEst\u00e1 seguro de que desea capturar esta operaci\u00F3n?";
this.scroll(0,0);
if(!confirm(output))
return false;
return true;
}

function confirmarGuardarCuenta(){
	var output="\u00bfEst\u00e1 seguro de que desea guardar esta cuenta?";
	this.scroll(0,0);
	if(!confirm(output)) {
		habilitarBoton('btnGuardar');
		return false;
	} else {
		removeAppletAltaCuenta();
		habilitarBoton('btnGuardar');
		return true;
	}
}

function isLongitudValidaMensaje() {
	var cadena = document.getElementById('daliForm:txtMensajePortal').value;
	if( cadena.length > 255 ) {
		alert('El mensaje puede tener una longitud m\u00e1xima de 255 carateres longitud actual ' + cadena.length + '.');
		return false;
	}
	return true;
}

function confirmarOperacionTesoreriaDeposito(idFolio,cuenta){
	if(idFolio.length == 5 && cuenta.length > 0) {
		var output="\u00bfDesea Traspasar el Efectivo al Cliente " + idFolio + " " + cuenta + "?";
		if(!confirm(output))
			return false;
		return true;
	} else {
		alert('Un Participante Receptor v\u00e1lido y su cuenta son requeridos');
		return false;
	}
}
function mostrarMensajeAlert(mensaje) {
	$j('#textoWarning').text(mensaje);
	$j('#divWarnings').fadeIn(1000);
	setTimeout("limpiarMensajesAlert()",4000);
}

function limpiarMensajesAlert() {
	$j('#divWarnings').fadeOut("slow");
}

function disparaOnkeypress(nombreCampo, event) {
	var campo = $j("input[id$='" + nombreCampo + "']");
	campo.attr("value", campo.attr("value") + "%");
	//campo.get(0).value = campo.get(0).value + '*';
	campo.keypress();
	
//	var fireOnThis = campo.get(0);
//	if( window.KeyEvent ) {
//	  var evObj = document.createEvent('KeyEvents');
//	  evObj.initKeyEvent( 'keyup', true, true, window, false, false, false, false, 13, 0 );
//	} else {
//	  var evObj = document.createEvent('UIEvents');
//	  evObj.initUIEvent( 'keyup', true, true, window, 1 );
//	  evObj.keyCode = 13;
//	}
//	fireOnThis.dispatchEvent(evObj);
}
function restringirCapturasNumericas() {
	$j(".campoNumerico").each(function () {
		$j("input[id=\"" + this.id + "\"]").after($j("<div id=\"errorNumerico_" + this.id + "\" style=\"position:relative;\"><div style=\"position:absolute;\"><span id=\"spanNumerico_" + this.id + "\" class=\"textoError\"></span></div></div>"));
		
	});
	$j(".campoNumerico").unbind("keypress");
	$j(".campoNumerico").bind("keypress",function (e) {
			if ((e.shiftKey && e.which == 45) || e.which != 8 && e.which !== 0&& e.which !== 9&& (e.which < 48 || e.which > 57) && e.which && (e.which < 48 || e.which > 57)) {
				$j("span[id=\"spanNumerico_" + this.id + "\"]").html("S\xf3lo n\xfameros.").show().fadeOut("slow");
				return false;
			}
		
			if ((e.shiftKey && e.keyCode == 45) || e.keyCode != 8 && e.keyCode !== 0&& e.keyCode !== 9&& (e.keyCode < 48 || e.keyCode > 57) && e.keyCode && (e.keyCode < 48 || e.keyCode > 57)) {
				$j("span[id=\"spanNumerico_" + this.id + "\"]").html("S\xf3lo n\xfameros.").show().fadeOut("slow");
				return false;
			}
	});
	
	$j(".campoNumericoSinComas").bind("focus",function (e) {
		var nt="";
		var i=0;

		for(i=0;i<this.value.length;i++)
		{

		(this.value.charAt(i)!=' '&&this.value.charAt(i)!='$'&&this.value.charAt(i)!=',')?nt+=this.value.charAt(i):null;
		}
		this.value=nt;
		});
}

function restringirCapturasNumericasValor() {
	$j(".campoNumericoValor").each(function () {
		$j("input[id=\"" + this.id + "\"]").after($j("<div id=\"errorNumerico_" + this.id + "\" style=\"position:relative;\"><div style=\"position:absolute;\"><span id=\"spanNumerico_" + this.id + "\" class=\"textoError\"></span></div></div>"));
		
	});
	$j(".campoNumericoValor").unbind("keypress");
	$j(".campoNumericoValor").bind("keypress",function (e) {
			if ((e.shiftKey && e.which == 45) || e.which != 8 && e.which !== 0&& e.which !== 9&& (e.which < 48 || e.which > 57) && e.which && (e.which < 48 || e.which > 57)) {
				$j("span[id=\"spanNumerico_" + this.id + "\"]").html("S\xf3lo n\xfameros.").show().fadeOut("slow");
				return false;
			}
		
			if ((e.shiftKey && e.keyCode == 45) || e.keyCode != 8 && e.keyCode !== 0&& e.keyCode !== 9&& (e.keyCode < 48 || e.keyCode > 57) && e.keyCode && (e.keyCode < 48 || e.keyCode > 57)) {
				$j("span[id=\"spanNumerico_" + this.id + "\"]").html("S\xf3lo n\xfameros.").show().fadeOut("slow");
				return false;
			}
	});
	
		
}

function restringirCapturasNumericasDecimales() {
	$j(".campoNumericoDecimal").each(function () {
		$j("input[id=\"" + this.id + "\"]").after($j("<div id=\"errorNumericoDecimal_" + this.id + "\" style=\"position:relative;\"><div style=\"position:absolute;\"><span id=\"spanNumerico_" + this.id + "\" class=\"textoError\"></span></div></div>"));
	});
	
	$j(".campoNumericoDecimal").unbind("keypress");
	$j(".campoNumericoDecimal").bind("keypress",function (e) {
				
			if(e.which==46&&this.value.indexOf(".") != -1) {
			
				$j("span[id=\"spanNumericoDecimal_" + this.id + "\"]").html("S\xf3lo n\xfameros y punto decimal.").show().fadeOut("slow");
				return false;
			}
		
			if ((e.shiftKey && e.which == 45) || e.which != 8 && e.which !== 0 && e.which !== 46 && e.which !== 9 && (e.which < 48 || e.which > 57)) {
				$j("span[id=\"spanNumericoDecimal_" + this.id + "\"]").html("S\xf3lo n\xfameros.").show().fadeOut("slow");
				return false;
			}
	});
		
	
	$j(".campoNumericoDecimalDosDecimales").bind("keypress",function (e) {
		if ((e.shiftKey && e.which == 45) || e.which != 8 && e.which !== 0 && e.which !== 46 && e.which !== 9 && (e.which < 48 || e.which > 57)) {
				
				return false;
			}	
			if(this.value.indexOf(".") != -1){
		if(((this.value.length-this.value.indexOf(".")) >2)&&e.which!=46&&e.which!=8&&e.which!=9&&e.which !== 0 )
		return false;
		}	 
		if((this.value.length>12&&(this.value.indexOf(".") == -1)&&e.which!=46&&e.which!=8&&e.which!=9&&e.which !== 0))
		{ 
		
		return false;}
		
		});
		$j(".campoNumericoDecimalDosDecimales").bind("focus",function (e) {
		var nt="";
		var i=0;
		
		for(i=0;i<this.value.length;i++)
		{
	
		(this.value.charAt(i)!=' '&&this.value.charAt(i)!='$'&&this.value.charAt(i)!=',')?nt+=this.value.charAt(i):null;
		}
		this.value=nt;
		});
		
		$j("this").unbind("blur");
	var error="La cantidad es demasiado grande, por favor verifique";
	$j(".campoNumericoDecimalDosDecimales").bind("blur",function (e) {
		if(this.value.length>21)
		{ 
			
		this.value="";
			this.focus();
		
		alert(error);
		
		}else{
		if(this.value.length>12&&this.value.indexOf(".") == -1)
		{ 
		this.value="";
			thoddRowis.focus();
		
		alert(error);
	
	
	
		
		}}
		
		
		});
		
	
ochoDecimales();
seisDecimales();
}
function ochoDecimales(){

	$j(".campoNumericoDecimalOchoDecimales").unbind("keypress");
	
	$j(".campoNumericoDecimalOchoDecimales").bind("keypress",function (e) {
	
			if ((e.shiftKey && e.which == 45) || e.which != 8 && e.which !== 0 && e.which !== 46 && e.which !== 9 && (e.which < 48 || e.which > 57)) {
				
				return false;
			}
	if(e.which==46&&this.value.indexOf(".") != -1) {
			
				$j("span[id=\"spanNumericoDecimal_" + this.id + "\"]").html("S\xf3lo n\xfameros y punto decimal.").show().fadeOut("slow");
				return false;
			}
			
			//verica que sean doce enteros
		if((this.value.length>12&&(this.value.indexOf(".") == -1)&&e.which!=46&&e.which!=8&&e.which!=9&&e.which !== 0 ))
		{ 
		
		return false;}
		
		if(this.value.indexOf(".") != -1){
		//valida ochodecimales
		if(((this.value.length-this.value.indexOf(".")) >8)&&e.which!=46&&e.which!=8&&e.which!=9&&e.which !== 0 )
		return false;
		}		
		});
		restringirCapturasAlphaEspeciales();
}

function seisDecimales(){

	$j(".campoNumericoDecimalSeisDecimales").unbind("keypress");
	
	$j(".campoNumericoDecimalSeisDecimales").bind("keypress",function (e) {
	
			if ((e.shiftKey && e.which == 45) || e.which != 8 && e.which !== 0 && e.which !== 46 && e.which !== 9 && (e.which < 48 || e.which > 57)) {
				
				return false;
			}
	if(e.which==46&&this.value.indexOf(".") != -1) {
			
				$j("span[id=\"spanNumericoDecimal_" + this.id + "\"]").html("S\xf3lo n\xfameros y punto decimal.").show().fadeOut("slow");
				return false;
			}
			
			//verica que sean tres enteros
		if((this.value.length>2&&(this.value.indexOf(".") == -1)&&e.which!=46&&e.which!=8&&e.which!=9&&e.which !== 0 ))
		{ 
		
		return false;}
		
		if(this.value.indexOf(".") != -1){
		//valida seis decimales
		if(((this.value.length-this.value.indexOf(".")) >6)&&e.which!=46&&e.which!=8&&e.which!=9&&e.which !== 0 )
		return false;
		}		
		});
		restringirCapturasAlphaEspeciales();
}


function deformater(){
	
}
function restringirCapturasAlphaNumericas() {
	$j(".campoAlphaNumerico").each(function () {
		$j("input[id=\"" + this.id + "\"]").after($j("<div id=\"erroraAlphaNumerico_" + this.id + "\" style=\"position:relative;\"><div style=\"position:absolute;\"><span id=\"spanAlphaNumerico_" + this.id + "\" class=\"textoError\"></span></div></div>"));
	});
	
	$j(".campoAlphaNumerico").unbind("keypress");
	$j(".campoAlphaNumerico").bind("keypress",function (e) {
		this.value.toUpperCase();
			if ((e.which && e.which == 45) || e.which != 8 && e.which !== 0  && e.which !== 9  && (e.which < 48 || e.which > 57) && (e.which < 64 || e.which > 90)&& (e.which < 96 || e.which > 122)) {
				$j("span[id=\"spanAlphaNumerico_" + this.id + "\"]").html("S\xf3lo n\xfameros y letras.").show().fadeOut("slow");
				return false;
			}
	});
	
	$j(".campoAlphaNumerico").unbind("blur");
	$j(".campoAlphaNumerico").bind("blur",function (e) {
		this.value.toUpperCase();
		
	});
}

function restringirMayusculas() {
	
	
	$j(".textoMayusculas").unbind("blur");
	$j(".textoMayusculas").bind("blur",function (e) {
		this.value.toUpperCase();
		
	});
}
function deshabilitarBoton(nombreBoton) {
	
	$j("input[id$=\"" + nombreBoton + "\"]").prop("disabled", true);
}

function habilitarBoton(nombreBoton) {
	$j("input[id$=\"" + nombreBoton + "\"]").removeAttr("disabled");
}

/**
 * Muestra un boton en la pantalla.
 * @param idBoton Id del boton a mostrar.
 */
function mostrarBoton(idBoton) {
	$j("input[id$=\"" + idBoton + "\"]").show();
}

/**
 * Oculta un boton en la pantalla.
 * @param idBoton Id del boton a ocultar.
 */
function ocultarBoton(idBoton) {
	$j("input[id$=\"" + idBoton + "\"]").hide();
}

/**
 * Oculta un boton en la pantalla por un tiempo especificado.
 * @param idBoton Id del boton a ocultar
 * @param delay Tiempo que será ocultado el botón
 */
function ocultarBotonDelay(idBoton, delay) {
	ocultarBoton(idBoton);
	setTimeout(
		function() {
			mostrarBoton(idBoton);
		}, 
		delay);
}

/**
 * Oculta los botones que generan los reportes y los muestra nuevamente despues de n segundos.
 * @param botones Arreglo con los id's de los botones a ocultar y mostrar.
 * @param delay Tiempo en que seran mostrados nuevamente los botones.
 */
function ocultarMostrarBotonesReportes(botones, delay) {
	for(var i = 0; i < botones.length; i++){
    	ocultarBoton(botones[i]);
	}
	setTimeout(function(){
		for(var i = 0; i < botones.length; i++){
			mostrarBoton(botones[i]);
		}
		}, delay);
}

function setMaxLengths()
{
$j(".textoCupon").each(function () {
		$j(".textoCuponone").prop("maxlength","4");
		
	});
	$j(".textoTipoValor").each(function () {
		$j(".textoTipoValor").prop("maxlength","4");
		
	});
		$j(".textoEmisora").each(function () {
		$j(".textoEmisora").prop("maxlength","7");
		
	});
	$j(".campoAlphaNumericoEmisora").each(function () {
		$j(".campoAlphaNumericoEmisora").prop("maxlength","7");
		
	});
	$j(".textoSerie").each(function () {
		$j(".textoSerie").prop("maxlength","6");
		
	});
	$j(".campoAlphaNumericoSerie").each(function () {
		$j(".campoAlphaNumericoSerie").prop("maxlength","6");
		
	});
	$j(".textoParticipante").each(function () {
		$j(".textoParticipante").prop("maxlength","5");
		
	});
	$j(".textoCuenta").each(function () {
		$j(".textoCuenta").prop("maxlength","4");
		
	});
}

function restringirCapturasNumericasAsterisco() {
	//$j(".tooltipNumericoAsterisco").remove();
	$j(".campoNumericoAsterisco").each(function () {
		//$j("input[id=\"" + this.id + "\"]").after($j("<div style='position:relative;'><img class='tooltipNumericoAsterisco' src='" + contextPath + "/images/001_50.gif' border='0' height='16px' width='16px'/></div>"));
		$j("input[id=\"" + this.id + "\"]").after($j("<div id=\"errorNumerico_" + this.id + "\" style=\"position:relative;\"><div style=\"position:absolute;\"><span id=\"spanNumerico_" + this.id + "\" class=\"textoError\"></span></div></div>"));
	});
	$j(".campoNumericoAsterisco").unbind("keypress");
	$j(".campoNumericoAsterisco").bind("keypress",function (e) {
		if(e.keyCode != 13 ) {
			if ((e.shiftKey && e.keyCode == 45) || e.which != 8 && e.which !== 0&& e.which !== 9&& e.which !== 42  && e.which !== 32 && e.which !== 17 && (e.which < 48 || e.which > 57) && (e.which < 48 || e.which > 57)) {
				$j("span[id=\"spanNumerico_" + this.id + "\"]").html("S\xf3lo n\xfameros").show().fadeOut("slow");
				return false;
			}
		}
	});
	
	/*$j('.tooltipNumericoAsterisco').tooltip({
		bodyHandler: function() {
			return 'Digite s\u00F3lo n\u00FAmeros en este campo. Si desea ver <br/>todas las opciones posibles, escriba un * en el campo.';
		},
		showURL: false
	});*/
}

function restringirCapturasNumericasAsterisco() {
	//$j(".tooltipNumericoAsterisco").remove();
	$j(".campoNumericoAsterisco").each(function () {
		//$j("input[id=\"" + this.id + "\"]").after($j("<div style='position:relative;'><img class='tooltipNumericoAsterisco' src='" + contextPath + "/images/001_50.gif' border='0' height='16px' width='16px'/></div>"));
		$j("input[id=\"" + this.id + "\"]").after($j("<div id=\"errorNumerico_" + this.id + "\" style=\"position:relative;\"><div style=\"position:absolute;\"><span id=\"spanNumerico_" + this.id + "\" class=\"textoError\"></span></div></div>"));
	});
	$j(".campoNumericoAsterisco").unbind("keypress");
	$j(".campoNumericoAsterisco").bind("keypress",function (e) {
		if(e.keyCode != 13 ) {
			if ((e.shiftKey && e.keyCode == 45) || e.which != 8 && e.which !== 0&& e.which !== 9&& e.which !== 42  && e.which !== 32 && e.which !== 17 && (e.which < 48 || e.which > 57) && (e.which < 48 || e.which > 57)) {
				$j("span[id=\"spanNumerico_" + this.id + "\"]").html("S\xf3lo n\xfameros").show().fadeOut("slow");
				return false;
			}
		}
	});
	
	/*$j('.tooltipNumericoAsterisco').tooltip({
		bodyHandler: function() {
			return 'Digite s\u00F3lo n\u00FAmeros en este campo. Si desea ver <br/>todas las opciones posibles, escriba un * en el campo.';
		},
		showURL: false
	});*/
}

function restringirCapturasNumericasCantidad() {
	$j(".campoNumericoCantidad").each(function () {
		$j("input[id=\"" + this.id + "\"]").after($j("<div id=\"errorNumerico_" + this.id + "\" style=\"position:relative;\"><div style=\"position:absolute;\"><span id=\"spanNumerico_" + this.id + "\" class=\"textoError\"></span></div></div>"));
	});
	$j(".campoNumericoCantidad").unbind("keypress");
	$j(".campoNumericoCantidad").bind("keypress",function (e) {
		if(e.keyCode != 13 ) {
			if ((e.shiftKey && e.keyCode == 45) || e.which != 8 && e.which !== 0&& e.which !== 9&& e.which !== 42  && e.which !== 32 && e.which !== 17 && (e.which < 48 || e.which > 57) && (e.which < 48 || e.which > 57)) {
				$j("span[id=\"spanNumerico_" + this.id + "\"]").html("S\xf3lo n\xfameros").show().fadeOut("slow");
				return false;
			}
		}
	});
}

function restringirCapturasAlphaNumericasAsterisco() {
	//$j(".tooltipAlfaNumericoAsterisco").remove();
	$j(".campoAlphaNumericoAsterisco").each(function () {
		//$j("input[id=\"" + this.id + "\"]").after($j("<div style='position:relative;'><img class='tooltipAlfaNumericoAsterisco' src='" + contextPath + "/images/001_50.gif' border='0' height='16px' width='16px'/></div>"));
		$j("input[id=\"" + this.id + "\"]").after($j("<div id=\"erroraAlphaNumerico_" + this.id + "\" style=\"position:relative;\"><div style=\"position:absolute;\"><span id=\"spanAlphaNumerico_" + this.id + "\" class=\"textoError\"></span></div></div>"));
	});
	$j(".campoAlphaNumericoAsterisco").unbind("keypress");
	$j(".campoAlphaNumericoAsterisco").bind("keypress",function (e) {
		this.value.toUpperCase();
		
		if(e.keyCode != 13) {
			if ((e.shiftKey && e.keyCode == 45) || e.which != 8 && e.which !== 0 && e.which !== 9  && e.which !== 42  && e.which !== 32 && e.which !== 17 && e.which !== 13  && (e.which < 48 || e.which > 57) && (e.which < 64 || e.which > 90)&& (e.which < 96 || e.which > 122)) {
				$j("span[id=\"spanAlphaNumerico_" + this.id + "\"]").html("S\xf3lo n\xfameros letras").show().fadeOut("slow");
				return false;
			}
		}
	});
	$j(".campoAlphaNumericoAsterisco").unbind("blur");
	$j(".campoAlphaNumericoAsterisco").bind("blur",function (e) {
		this.value.toUpperCase();
	
		
		
	});
	/*$j('.tooltipAlfaNumericoAsterisco').tooltip({
		bodyHandler: function() {
			return 'Digite s\u00F3lo n\u00FAmeros y letras en este campo. Si desea ver <br/>todas las opciones posibles, escriba un * en el campo.';
		},
		showURL: false
	});*/
}


function restringirCapturasAlphaNumericasEspacio() {
	//$j(".tooltipAlfaNumericoAsterisco").remove();
	$j(".campoAlphaNumericoEspacio").each(function () {
		//$j("input[id=\"" + this.id + "\"]").after($j("<div style='position:relative;'><img class='tooltipAlfaNumericoAsterisco' src='" + contextPath + "/images/001_50.gif' border='0' height='16px' width='16px'/></div>"));
		$j("input[id=\"" + this.id + "\"]").after($j("<div id=\"erroraAlphaNumerico_" + this.id + "\" style=\"position:relative;\"><div style=\"position:absolute;\"><span id=\"spanAlphaNumerico_" + this.id + "\" class=\"textoError\"></span></div></div>"));
	});
	$j(".campoAlphaNumericoEspacio").unbind("keypress");
	$j(".campoAlphaNumericoEspacio").bind("keypress",function (e) {
		this.value.toUpperCase();
		
		if(e.keyCode != 13) {
			if ((e.shiftKey && e.keyCode == 45) || e.which != 8 && e.which !== 0 && e.which !== 9 && e.which !== 32 && e.which !== 17 && e.which !== 13  && (e.which < 48 || e.which > 57) && (e.which < 64 || e.which > 90)&& (e.which < 96 || e.which > 122)) {
				$j("span[id=\"spanAlphaNumerico_" + this.id + "\"]").html("S\xf3lo n\xfameros letras espacio").show().fadeOut("slow");
				return false;
			}
		}
	});
	$j(".campoAlphaNumericoEspacio").unbind("blur");
	$j(".campoAlphaNumericoEspacio").bind("blur",function (e) {
		this.value.toUpperCase();
	
		
		
	});
	/*$j('.tooltipAlfaNumericoAsterisco').tooltip({
		bodyHandler: function() {
			return 'Digite s\u00F3lo n\u00FAmeros y letras en este campo. Si desea ver <br/>todas las opciones posibles, escriba un * en el campo.';
		},
		showURL: false
	});*/
}


function restringirCapturasAlphaNumericasEmisora() {

	$j(".campoAlphaNumericoEmisora").each(function () {
		
	$j("input[id=\"" + this.id + "\"]").after($j("<div id=\"erroraAlphaNumerico_" + this.id + "\" style=\"position:relative;\"><div style=\"position:absolute;\"><span id=\"spanAlphaNumerico_" + this.id + "\" class=\"textoError\"></span></div></div>"));
	});
	$j(".campoAlphaNumericoEmisora").unbind("keypress");
	$j(".campoAlphaNumericoEmisora").bind("keypress",function (e) {
		this.value.toUpperCase();
		
		if(e.keyCode != 13) {
			if ((e.shiftKey && e.keyCode == 45) || e.which != 8 && e.which != 43 && e.which != 45 && e.which != 38 && e.which !== 0 && e.which !== 9 && e.which !== 42 && e.which !== 32 && e.which !== 42 && e.which !== 13  && (e.which < 48 || e.which > 57) && (e.which < 64 || e.which > 90)&& (e.which < 96 || e.which > 122)) {
				$j("span[id=\"spanAlphaNumerico_" + this.id + "\"]").html("S\xf3lo n\xfameros letras").show().fadeOut("slow");
				return false;
			}
		}
	});
	$j(".campoAlphaNumericoEmisora").unbind("blur");
	$j(".campoAlphaNumericoEmisora").bind("blur",function (e) {
		this.value.toUpperCase();
	
		
		
	});
	
}

function restringirCapturasAlphaNumericasSerie() {

	$j(".campoAlphaNumericoSerie").each(function () {
		
	$j("input[id=\"" + this.id + "\"]").after($j("<div id=\"erroraAlphaNumerico_" + this.id + "\" style=\"position:relative;\"><div style=\"position:absolute;\"><span id=\"spanAlphaNumerico_" + this.id + "\" class=\"textoError\"></span></div></div>"));
	});
	$j(".campoAlphaNumericoSerie").unbind("keypress");
	$j(".campoAlphaNumericoSerie").bind("keypress",function (e) {
		this.value.toUpperCase();
		
		if(e.keyCode != 13) {
			if ((e.shiftKey && e.keyCode == 45) || e.which != 8 && e.which != 42 && e.which != 45 && e.which !== 0 && e.which !== 9 && e.which !== 13  && (e.which < 48 || e.which > 57) && (e.which < 64 || e.which > 90)&& (e.which < 96 || e.which > 122)) {
				$j("span[id=\"spanAlphaNumerico_" + this.id + "\"]").html("S\xf3lo n\xfameros letras").show().fadeOut("slow");
				return false;
			}
		}
	});
	$j(".campoAlphaNumericoSerie").unbind("blur");
	$j(".campoAlphaNumericoSerie").bind("blur",function (e) {
		this.value.toUpperCase();
	
		
		
	});
	
}


function restringirCapturasAlphaEspeciales() {
	//$j(".tooltipAlfaAsterisco").remove();
	$j(".campoAlphaEspeciales").each(function () {
		//$j("input[id=\"" + this.id + "\"]").after($j("<div style='position:relative;'><div style='position:absolute;'><img class='tooltipAlfaAsterisco' src='" + contextPath + "/images/001_50.gif' border='0' height='16px' width='16px'/></div></div>"));
		$j("input[id=\"" + this.id + "\"]").after($j("<div id=\"erroraAlpha_" + this.id + "\" style=\"position:relative;\"><div style=\"position:absolute;\"><span id=\"spanAlpha_" + this.id + "\" class=\"textoError\"></span></div></div>"));
	});
	$j(".campoAlphaEspeciales").unbind("keypress");
	$j(".campoAlphaEspeciales").bind("keypress",function (e) {
		this.value.toUpperCase();
		if(e.keyCode != 13) {
			if ((e.shiftKey && e.keyCode == 45) || e.which != 8 && e.which !== 0 && e.which !== 46 && e.which !== 9  && e.which !== 32 && e.which !== 42&& e.which !== 13 && (e.which < 48 || e.which > 57)  && (e.which < 64 || e.which > 90)&& (e.which < 96 || e.which > 122)) {
				$j("span[id=\"spanAlpha_" + this.id + "\"]").html("S\xf3lo letras").show().fadeOut("slow");
				return false;
			}
		}
	});
	$j(".campoAlphaEspeciales").unbind("blur");
	$j(".campoAlphaEspeciales").bind("blur",function (e) {
		this.value.toUpperCase();
		
	});
	/*$j('.tooltipAlfaAsterisco').tooltip({
		bodyHandler: function() {
			return 'Digite s\u00F3lo letras en este campo. Si desea ver <br/>todas las opciones posibles, escriba un * en el campo.';
		},
		showURL: false
	});*/
}
function restringirCapturasAlphaAsterisco() {
	//$j(".tooltipAlfaAsterisco").remove();
	$j(".campoAlphaAsterisco").each(function () {
		//$j("input[id=\"" + this.id + "\"]").after($j("<div style='position:relative;'><div style='position:absolute;'><img class='tooltipAlfaAsterisco' src='" + contextPath + "/images/001_50.gif' border='0' height='16px' width='16px'/></div></div>"));
		$j("input[id=\"" + this.id + "\"]").after($j("<div id=\"erroraAlpha_" + this.id + "\" style=\"position:relative;\"><div style=\"position:absolute;\"><span id=\"spanAlpha_" + this.id + "\" class=\"textoError\"></span></div></div>"));
	});
	$j(".campoAlphaAsterisco").unbind("keypress");
	$j(".campoAlphaAsterisco").bind("keypress",function (e) {
		this.value.toUpperCase();
		if(e.keyCode != 13) {
			if ((e.shiftKey && e.keyCode == 45) || e.which != 8 && e.which !== 0 && e.which !== 46 && e.which !== 9  && e.which !== 42&& e.which !== 13  && (e.which < 64 || e.which > 90)&& (e.which < 96 || e.which > 122)) {
				$j("span[id=\"spanAlpha_" + this.id + "\"]").html("S\xf3lo letras").show().fadeOut("slow");
				return false;
			}
		}
	});
	$j(".campoAlphaAsterisco").unbind("blur");
	$j(".campoAlphaAsterisco").bind("blur",function (e) {
		this.value.toUpperCase();
		
	});
	/*$j('.tooltipAlfaAsterisco').tooltip({
		bodyHandler: function() {
			return 'Digite s\u00F3lo letras en este campo. Si desea ver <br/>todas las opciones posibles, escriba un * en el campo.';
		},
		showURL: false
	});*/
}

var existioHotKeyAutocomplete = false;

function habilitarHotKeysAutoCompletes() {
	$j(document).bind('keypress', {combi:'Alt+k', propagate: true} , function () {
		if(existioHotKeyAutocomplete) {
			existioHotKeyAutocomplete = false;
		} else {
			existioHotKeyAutocomplete = true;
		}
	});
	
} 

function validarSubmitAutocomplete() {
	return existioHotKeyAutocomplete;
}

function habilitarAyudaContextoAutoCompletes() {
	$j('.campoAutocomplete').each(function() {
		$j("input[id=\"" + this.id + "\"]").after($j('<img src=' + contextPath + '/images/'));
	});
}

function animarMensajesDeUsuario() {
	$j('#mensajesUsuario').fadeIn(100).fadeOut(100).fadeIn(100).fadeOut(100).fadeIn(100).fadeOut(100).fadeIn(100);
}

function animarMensajePortal() {
	$j('#mensajePortalDiv').fadeIn(100).fadeOut(100).fadeIn(100).fadeOut(100).fadeIn(100).fadeOut(100).fadeIn(100);
}

function limpiarMensajesDeUsuario() {
	$j('#mensajesUsuario').remove();
}

function checaHeader() {
	var local = new String(top.frames['topFrame'].location);
	if( local.indexOf('divint',0) != -1 ) {
		top.frames['topFrame'].location = "/dali/header.faces";
		//top.frames['mainFrame'].location = top.frames['mainFrame'].location;
	}
}

$j(document).ready(function () {
	$j(document).unbind("keypress");
	$j(document).bind("keypress", function(e) {
	  	if (e.keyCode == 13) { 
	  		return false;
	  	}
	});
	
	configurarMenu();
	
	decorarCamposCaptura();
});

function decorarCamposCaptura() {
	
	habilitarHotKeysAutoCompletes();
	restringirCapturasNumericas();
	restringirCapturasNumericasValor();
	restringirCapturasNumericasDecimales();
	restringirCapturasAlphaNumericas();
	restringirCapturasNumericasAsterisco();
	restringirCapturasAlphaNumericasAsterisco();
	restringirCapturasAlphaNumericasEspacio();
	restringirCapturasAlphaNumericasEmisora();
	restringirCapturasAlphaNumericasSerie();
	restringirCapturasAlphaAsterisco();
	restringirMayusculas();
	//habilitarHotKeysAutoCompletes();
	setMaxLengths();
	
}

function restringeReporte(){
	if($j("input[id$='suggestInstitucion']").length != 0){
		if ($j("input[id$='suggestInstitucion']").prop('value') === undefined || 
			$j("input[id$='suggestInstitucion']").prop('value') == ""){
			self.scroll(0,0);
			alert("Por Favor seleccione una instituci\xf3n.");
			return false;
		}else{
			return true;
		}
	}
	return true;
}

function navegacionMenuIngresos(url) {
	top.frames['mainFrame'].location = '/ingresos' + url;
}


function isDayEnabled(day){
  var date = new Date(day.date);
  return (date.getDay() != 6 && date.getDay() != 0);
}

function getDisabledStyle(day){
  if (!isDayEnabled(day)) return 'rich-calendar-boundary-dates disabledDay';
}

/**
 * Funcion que valida que se haya elegido una institucion para la generacion de los
 * reportes. Si eligio una institucion, procede a bloquear los botones que generan
 * los reportes.
 * @param botonesBloquear Arreglo con los botones a bloquear.
 * @returns {Boolean}
 */
function validarInstitucionBloquearBotonesReportes(botonesBloquear) {
	var resultadoValidacion;
	if($j("input[id$='suggestInstitucion']").length != 0){
		if ($j("input[id$='suggestInstitucion']").prop('value') === undefined || 
			$j("input[id$='suggestInstitucion']").prop('value') == ""){
			self.scroll(0,0);
			alert("Por Favor seleccione una instituci\xf3n.");
			resultadoValidacion = false;
		}
		else{
			bloquearBotonesReportes(botonesBloquear);
			resultadoValidacion = true;
		}
	}
	return resultadoValidacion;
}



/**
 * Funcion que bloquea los botones para generar los reportes mientras se
 * esta 
 */
function bloquearBotonesReportes(botonesBloquear) {
	iniciarMonitorPeticionReporte = true;
	for(i=0; i < botonesBloquear.length; i++) {
		$j("input[id$=\"" + botonesBloquear[i] + "\"]").hide();
	}
	$j("#monitorPeticionReporte").css("display", "inline");
	setTimeout("refrescarMonitor()", 5000);
}


/**
 * Funcion que bloquea los botones para generar los reportes mientras se
 * esta 
 */
function bloquearBotonesReportesMostrarSegundoDiv(botonesBloquear) {
	iniciarMonitorPeticionReporte = true;
	for(i=0; i < botonesBloquear.length; i++) {
		$j("input[id$=\"" + botonesBloquear[i] + "\"]").hide();
	}
	$j("#monitorPeticionReporte").css("display", "inline");
	$j("#monitorPeticionReporte1").css("display", "inline");
	setTimeout("refrescarMonitor()", 5000);
}


function desbloquearBotonesReportes(estatusPeticion, botonesDesbloquear) {
	if(estatusPeticion === true) {		
		for(i=0; i < botonesDesbloquear.length; i++) {
			$j("input[id$=\"" + botonesDesbloquear[i] + "\"]").show();
		}
		$j("#monitorPeticionReporte").css("display", "none");
		iniciarMonitorPeticionReporte = false;
	}
	else {
		setTimeout("refrescarMonitor()", 5000);
	}
}

function desbloquearBotonesReportesOcultarSegundoDiv(estatusPeticion, botonesDesbloquear) {
	if(estatusPeticion === true) {		
		for(i=0; i < botonesDesbloquear.length; i++) {
			$j("input[id$=\"" + botonesDesbloquear[i] + "\"]").show();
		}
		$j("#monitorPeticionReporte").css("display", "none");
		$j("#monitorPeticionReporte1").css("display", "none");
		iniciarMonitorPeticionReporte = false;
	}
	else {
		setTimeout("refrescarMonitor()", 5000);
	}
}

						
function refrescarMonitor() {
	if(iniciarMonitorPeticionReporte === true) {
    	$j("input[id$='btnActualizarEstadoPeticion']").click();
	}
}


/**
 * Muestra la sección con los criterios de busqueda y
 * oculta el resumen de criterios y el boton de editar.
 */
function mostrarCriteriosBusqueda() {
	$j("#divCriterios").css("display", "inline");
	$j("#divResumen").css("display", "none");
	$j("#botonEditarCriterios").css("display", "none");
}

/**
 * Muestra el resumen de criterios y el boton de editar
 * y oculta la sección con los criterios de busqueda. 
 */
function mostrarResumenCriteriosBusqueda() {
	$j("#divCriterios").css("display", "none");
	$j("#divResumen").css("display", "inline");
	$j("#divResumen").css("opacity", "1");	
	$j("#botonEditarCriterios").css("display", "inline");
}
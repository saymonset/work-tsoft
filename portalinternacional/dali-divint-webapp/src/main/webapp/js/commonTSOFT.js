var iniciarMonitorPeticionReporte;

$j(document).on('ready',function(){
	$j('#detalleFileTransfer').dialog(
			{ 
				autoOpen:false,
				height:650,
				maxheight:1200,
				maxWidth:1200,
				modal: true,
				resizable: false,
				width:950,
				zIndex:10003
			 }
		);
});

function mostrarDetalle(titulo,url){
	$j('#detalleFileTransfer').dialog('open');
	$j('#detalleFileTransfer').dialog('option','title',titulo);
	$j('#detalleFileTransfer').prop('src',url);
	$j('#detalleFileTransfer').css('width','99%');
}


function ltrim(s) {
   return s.replace(/^\s+/, "");
}

function rtrim(s) {
   return s.replace(/\s+$/, "");
}

function trim(s) {
   return rtrim(ltrim(s));
}

function addLoader(id) {
	if ($j("#loader" + id).attr("id") === undefined) {
		$j("input[id$='" + id + "']").after("<img src=\"" + contextPath + "/images/loading1.gif\" border=\"0\" id=\"loader" + id + "\" />");
	}
}
function removeLoader(id) {
	if ($j("#loader" + id)) {
		$j("#loader" + id).remove();
	}
}

function animarMensajePortalDivInt() {
	$j('#mensajePortalDivInt').fadeIn(100).fadeOut(100).fadeIn(100).fadeOut(100).fadeIn(100).fadeOut(100).fadeIn(100);
}

window.alert = function(txt) {
	mostrarMensajeAlert(txt);
};

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
}

function restringirCapturasNumericasEnter() {
	$j(".campoNumericoEnter").each(function () {
		$j("input[id=\"" + this.id + "\"]").after($j("<div id=\"errorNumerico_" + this.id + "\" style=\"position:relative;\"><div style=\"position:absolute;\"><span id=\"spanNumerico_" + this.id + "\" class=\"textoError\"></span></div></div>"));
		
	});
	$j(".campoNumericoEnter").unbind("keypress");
	$j(".campoNumericoEnter").bind("keypress",function (e) {
			if ((e.shiftKey && e.which == 45) || e.which != 8 && e.which !== 0&& e.which !== 9&& e.which !== 13&&(e.which < 48 || e.which > 57) && e.which && (e.which < 48 || e.which > 57)) {
				$j("span[id=\"spanNumerico_" + this.id + "\"]").html("S\xf3lo n\xfameros.").show().fadeOut("slow");
				return false;
			}
		
			if ((e.shiftKey && e.keyCode == 45) || e.keyCode != 8 && e.keyCode !== 0&& e.keyCode !== 9&& e.which !== 13&& (e.keyCode < 48 || e.keyCode > 57) && e.keyCode && (e.keyCode < 48 || e.keyCode > 57)) {
				$j("span[id=\"spanNumerico_" + this.id + "\"]").html("S\xf3lo n\xfameros.").show().fadeOut("slow");
				return false;
			}
	});		
}

function restringirCapturasNumericasComa() {
	$j(".campoNumericoComa").each(function () {
		$j("input[id=\"" + this.id + "\"]").after($j("<div id=\"errorNumerico_" + this.id + "\" style=\"position:relative;\"><div style=\"position:absolute;\"><span id=\"spanNumerico_" + this.id + "\" class=\"textoError\"></span></div></div>"));

	});
	$j(".campoNumericoComa").unbind("keypress");
	$j(".campoNumericoComa").bind("keypress",function (e) {
			if ((e.shiftKey && e.which == 45) || e.which != 8 && e.which !== 0&& e.which !== 9& e.which !== 44& e.which !== 13&& (e.which < 48 || e.which > 57) && e.which && (e.which < 48 || e.which > 57)) {
				alert("S\xf3lo n\xfameros o comas.");
				return false;
			}

			if ((e.shiftKey && e.keyCode == 45) || e.keyCode != 8 && e.keyCode !== 0&& e.keyCode !== 9&& e.keyCode !== 44&& e.keyCode !== 13&& (e.keyCode < 48 || e.keyCode > 57) && e.keyCode && (e.keyCode < 48 || e.keyCode > 57)) {
				alert("S\xf3lo n\xfameros o comas.");
				return false;
			}
	});
}

function restringirCapturasNumericasGuion() {
	$j(".campoNumericoGuion").each(function () {
		$j("input[id=\"" + this.id + "\"]").after($j("<div id=\"errorNumerico_" + this.id + "\" style=\"position:relative;\"><div style=\"position:absolute;\"><span id=\"spanNumerico_" + this.id + "\" class=\"textoError\"></span></div></div>"));

	});
	$j(".campoNumericoGuion").unbind("keypress");
	$j(".campoNumericoGuion").bind("keypress",function (e) {
			if ((e.shiftKey && e.which == 45) || e.which != 8 && e.which !== 0&& e.which !== 9& e.which !== 45&& (e.which < 48 || e.which > 57) && e.which && (e.which < 48 || e.which > 57)) {
				$j("span[id=\"spanNumerico_" + this.id + "\"]").html("S\xf3lo n\xfameros o guiones.").show().fadeOut("slow");
				return false;
			}

			if ((e.shiftKey && e.keyCode == 45) || e.keyCode != 8 && e.keyCode !== 0&& e.keyCode !== 9&& e.keyCode !== 45&& (e.keyCode < 48 || e.keyCode > 57) && e.keyCode && (e.keyCode < 48 || e.keyCode > 57)) {
				$j("span[id=\"spanNumerico_" + this.id + "\"]").html("S\xf3lo n\xfameros o guiones.").show().fadeOut("slow");
				return false;
			}
	});


}

function restringirCapturasNumericasDecimales() {
	$j(".campoNumericoDecimal").each(function () {
		$j("input[id=\"" + this.id + "\"]").after($j("<div id=\"errorNumericoDecimal_" + this.id + "\" style=\"position:relative;\"><div style=\"position:absolute;\"><span id=\"spanNumericoDecimal_" + this.id + "\" class=\"textoError\"></span></div></div>"));
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
		if((this.value.length>11&&(this.value.indexOf(".") == -1)&&e.which!=46&&e.which!=8&&e.which!=9&&e.which !== 0))
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
			this.focus();
		
		alert(error);
	
	
	
		
		}}
		
		
		});
		
	
ochoDecimales();
}
function ochoDecimales(){

	$j(".campoNumericoDecimalCuatroDecimales").unbind("keypress");
	
	$j(".campoNumericoDecimalCuatroDecimales").bind("keypress",function (e) {
	
			if ((e.shiftKey && e.which == 45) || e.which != 8 && e.which !== 0 && e.which !== 46 && e.which !== 9 && (e.which < 48 || e.which > 57)) {
				
				return false;
			}
	if(e.which==46&&this.value.indexOf(".") != -1) {
			
				$j("span[id=\"spanNumericoDecimal_" + this.id + "\"]").html("S\xf3lo n\xfameros y punto decimal.").show().fadeOut("slow");
				return false;
			}
		if((this.value.length>15&&(this.value.indexOf(".") == -1)&&e.which!=46&&e.which!=8&&e.which!=9&&e.which !== 0 ))
		{ 
		
		return false;}
		
		if(this.value.indexOf(".") != -1){
		if(((this.value.length-this.value.indexOf(".")) >2)&&e.which!=46&&e.which!=8&&e.which!=9&&e.which !== 0 )
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
}

function deshabilitarBoton(nombreBoton) {
	
	$j("input[id$=\"" + nombreBoton + "\"]").attr("disabled", true);
}

function habilitarBoton(nombreBoton) {
	$j("input[id$=\"" + nombreBoton + "\"]").removeAttr("disabled");
}
// Cambio Multidivisas
function evitarPegar() {
	$j(".notPaste").unbind("paste");
	$j(".notPaste").bind("paste", function (e) {
		e.preventDefault();
	});
}
// Fin Cambio Multidivisas
function setMaxLengths()
{
$j(".textoCupon").each(function () {
		$j(".textoCuponone").attr("maxlength","4");
		
	});
	$j(".textoTipoValor").each(function () {
		$j(".textoTipoValor").attr("maxlength","4");
		
	});
		$j(".textoEmisora").each(function () {
		$j(".textoEmisora").attr("maxlength","7");
		
	});
$j(".textoSerie").each(function () {
		$j(".textoSerie").attr("maxlength","6");
		
	});
	$j(".textoParticipante").each(function () {
		$j(".textoParticipante").attr("maxlength","5");
		
	});
	$j(".textoCuenta").each(function () {
		$j(".textoCuenta").attr("maxlength","4");
		
	});
	// Cambio Multidivisas
	$j(".textoComentarios").each(function () {
		$j(".textoComentarios").attr("maxlength","140");

	});
	$j(".textoInfoRemesas").each(function () {
		$j(".textoInfoRemesas").attr("maxlength","250");

	});
	// Fin Cambio Multidivisas
}

function restringirCapturasNumericasAsterisco() {
	//$j(".tooltipNumericoAsterisco").remove();
	$j(".campoNumericoAsterisco").each(function () {
		//$j("input[id=\"" + this.id + "\"]").after($j("<div style='position:relative;'><img class='tooltipNumericoAsterisco' src='" + contextPath + "/images/001_50.gif' border='0' height='16px' width='16px'/></div>"));
		$j("input[id=\"" + this.id + "\"]").after($j("<div id=\"errorNumerico_" + this.id + "\" style=\"position:relative;\"><div style=\"position:absolute;\"><span id=\"spanNumerico_" + this.id + "\" class=\"textoError\"></span></div></div>"));
	});
	$j(".campoNumericoAsterisco").unbind("keypress");
	$j(".campoNumericoAsterisco").bind("keypress",function (e) {
		if(e.keyCode != 13 && !e.altKey) {
			if ((e.shiftKey && e.keyCode == 45) || e.which != 8 && e.which !== 0&& e.which !== 9&& e.which !== 42   && (e.which < 48 || e.which > 57) && (e.which < 48 || e.which > 57)) {
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
			if ((e.shiftKey && e.keyCode == 45) || e.which != 8 && e.which !== 0 && e.which !== 46 && e.which !== 9  && e.which !== 42&& e.which !== 13  && (e.which < 48 || e.which > 57) && (e.which < 64 || e.which > 90)&& (e.which < 96 || e.which > 122)) {
				$j("span[id=\"spanAlphaNumerico_" + this.id + "\"]").html("S\xf3lo n\xfameros letras").show().fadeOut("slow");
				return false;
			}
		}
	});
	/*$j('.tooltipAlfaNumericoAsterisco').tooltip({
		bodyHandler: function() {
			return 'Digite s\u00F3lo n\u00FAmeros y letras en este campo. Si desea ver <br/>todas las opciones posibles, escriba un * en el campo.';
		},
		showURL: false
	});*/
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
			if ((e.shiftKey && e.keyCode == 45) || e.which != 8 && e.which !== 0 && e.which !== 46 && e.which !== 9  && e.which !== 32 && e.which !== 42&& e.which !== 13  && (e.which < 64 || e.which > 90)&& (e.which < 96 || e.which > 122)) {
				$j("span[id=\"spanAlpha_" + this.id + "\"]").html("S\xf3lo letras").show().fadeOut("slow");
				return false;
			}
		}
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
	/*$j('.tooltipAlfaAsterisco').tooltip({
		bodyHandler: function() {
			return 'Digite s\u00F3lo letras en este campo. Si desea ver <br/>todas las opciones posibles, escriba un * en el campo.';
		},
		showURL: false
	});*/
}

function restringirCapturasAlfabeticas() {
	$j(".campoAlfabeticos").each(function () {
		$j("input[id=\"" + this.id + "\"]").after($j("<div id=\"erroraAlpha_" + this.id + "\" style=\"position:relative;\"><div style=\"position:absolute;\"><span id=\"spanAlpha_" + this.id + "\" class=\"textoError\"></span></div></div>"));
	});
	$j(".campoAlfabeticos").unbind("keypress");
	$j(".campoAlfabeticos").bind("keypress",function (e) {
		this.value.toUpperCase();
		if(e.keyCode != 13) {
			if ((e.shiftKey && e.keyCode == 45) || e.which != 8 && e.which !== 0 && e.which !== 9  && e.which !== 13  && (e.which < 64 || e.which > 90)&& (e.which < 96 || e.which > 122)) {
				$j("span[id=\"spanAlpha_" + this.id + "\"]").html("S\xf3lo letras").show().fadeOut("slow");
				return false;
			}
		}
	});
}

function restringirMayusculas() {
	$j(".textoMayusculas").unbind("blur");
	$j(".textoMayusculas").bind("blur",function (e) {
		this.value.toUpperCase();
		
	});
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

function limpiarMensajesDeUsuario() {
	$j('#mensajesUsuario').remove();
}

function checaHeader() {
	var local = new String(top.frames['topFrame'].location);
	if( local.indexOf('divint',0) == -1 ) {
		top.frames['topFrame'].location = "/dali-divint/header.faces";
		//top.frames['mainFrame'].location = top.frames['mainFrame'].location;
	}
}

$j(document).ready(function () {
	$j(document).unbind("keypress");
	$j(document).bind("keypress", function(e) {
		var pagina = document.location.href;		
		if(pagina.indexOf('cambiaEstadosSIC') == -1 && pagina.indexOf('detalleNarrativasCapitales') == -1 && pagina.indexOf('cambiaEdoCambioBov') == -1){
			if (e.keyCode == 13) { 
				return false;
			}
		}	   	
	});

	habilitarHotKeysAutoCompletes();
	restringirCapturasNumericas();
    restringirCapturasNumericasGuion();
    restringirCapturasNumericasComa();
	restringirCapturasNumericasDecimales();
	restringirCapturasAlphaNumericas();
	restringirCapturasNumericasAsterisco();
	restringirCapturasAlphaNumericasAsterisco();
	restringirCapturasAlphaNumericasEmisora();
	restringirCapturasAlphaNumericasSerie();
	restringirCapturasAlphaAsterisco();
    restringirCapturasAlfabeticas();
	restringirCapturasNumericasEnter();
	restringirMayusculas();
	restringirDia();
	restringirMes();
	restringirAnio();
	restringirCapturaNarrativa();
	//habilitarHotKeysAutoCompletes();
	setMaxLengths();
	// Cambio Multidivisas
	evitarPegar();
	// Fin Cambio Multidivisas
});


function muestraErroresFileTransfer() {
    mostrarDetalle('Errores',contextPath + '/fileTransfer/fileTransferDetalleErrores.faces?');
}

function muestraErroresFileTransferInversionExtranjera() {
	mostrarDetalle('Errores',contextPath + '/inversionextranjera/fileTransfer/fileTransferInversionExtrDetalleErrores.faces?TB_iframe=true&height=550&width=925');
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
			if ((e.shiftKey && e.keyCode == 45) || e.which != 8 && e.which != 42 && e.which != 43 && e.which != 45 && e.which !== 0 && e.which !== 9 && e.which !== 13  && (e.which < 48 || e.which > 57) && (e.which < 64 || e.which > 90)&& (e.which < 96 || e.which > 122)) {
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

function confirmarGuardarStatements() {
    if(!$j('input[id$=chbSimularStatement]').attr('checked') ) {
        if(!confirm('\xbf Estas seguro que deseas guardar los datos y no simular \x3f')) {
            return false;
        }
    }
    return true;
}


function isDayEnabled(day){
  var date = new Date(day.date);
  return (date.getDay() != 6 && date.getDay() != 0);
}

function getDisabledStyle(day){
  if (!isDayEnabled(day)) return 'rich-calendar-boundary-dates disabledDay';
}

function restringirDia() {

	$j(".campoNumericoDia").each(function () {
		
	$j("input[id=\"" + this.id + "\"]").after($j("<div id=\"erroraAlphaNumerico_" + this.id + "\" style=\"position:relative;\"><div style=\"position:absolute;\"><span id=\"spanNumerico_" + this.id + "\" class=\"textoError\"></span></div></div>"));
	});
	$j(".campoNumericoDia").unbind("keypress");
	$j(".campoNumericoDia").bind("keypress",function (e) {
		var valor = new Number(this.value);
		var tope = new Number(31);
		
		if ((e.shiftKey && e.which == 45) || e.which != 8 && e.which !== 0&& e.which !== 9&& (e.which < 48 || e.which > 57) && e.which && (e.which < 48 || e.which > 57)) {
				$j("span[id=\"spanNumerico_" + this.id + "\"]").html("S\xf3lo n\xfameros.").show().fadeOut("slow");
				return false;
			}
		
			if ((e.shiftKey && e.keyCode == 45) || e.keyCode != 8 && e.keyCode !== 0&& e.keyCode !== 9&& (e.keyCode < 48 || e.keyCode > 57) && e.keyCode && (e.keyCode < 48 || e.keyCode > 57)) {
				$j("span[id=\"spanNumerico_" + this.id + "\"]").html("S\xf3lo n\xfameros.").show().fadeOut("slow");
				return false;
			}
			
		if(e.keyCode != 13 || e.keyCode != 8) {
			if (valor > tope ){
				$j("span[id=\"spanNumerico_" + this.id + "\"]").html("S\xf3lo dias").show().fadeOut("slow");
				this.value="";
				return false;
			}
		}
	});
}

function restringirMes() {

	$j(".campoNumericoMes").each(function () {
		
	$j("input[id=\"" + this.id + "\"]").after($j("<div id=\"erroraAlphaNumerico_" + this.id + "\" style=\"position:relative;\"><div style=\"position:absolute;\"><span id=\"spanNumerico_" + this.id + "\" class=\"textoError\"></span></div></div>"));
	});
	$j(".campoNumericoMes").unbind("keypress");
	$j(".campoNumericoMes").bind("keypress",function (e) {
		var valor = new Number(this.value);
		var tope = new Number(12);
		
		if ((e.shiftKey && e.which == 45) || e.which != 8 && e.which !== 0&& e.which !== 9&& (e.which < 48 || e.which > 57) && e.which && (e.which < 48 || e.which > 57)) {
				$j("span[id=\"spanNumerico_" + this.id + "\"]").html("S\xf3lo n\xfameros.").show().fadeOut("slow");
				return false;
			}
		
			if ((e.shiftKey && e.keyCode == 45) || e.keyCode != 8 && e.keyCode !== 0&& e.keyCode !== 9&& (e.keyCode < 48 || e.keyCode > 57) && e.keyCode && (e.keyCode < 48 || e.keyCode > 57)) {
				$j("span[id=\"spanNumerico_" + this.id + "\"]").html("S\xf3lo n\xfameros.").show().fadeOut("slow");
				return false;
			}
			
		if(e.keyCode != 13 || e.keyCode != 8) {
			if (valor > tope ){
				$j("span[id=\"spanNumerico_" + this.id + "\"]").html("S\xf3lo meses").show().fadeOut("slow");
				this.value="";
				return false;
			}
		}
	});
}

function restringirAnio() {

	$j(".campoNumericoAnio").each(function () {
		
	$j("input[id=\"" + this.id + "\"]").after($j("<div id=\"erroraAlphaNumerico_" + this.id + "\" style=\"position:relative;\"><div style=\"position:absolute;\"><span id=\"spanNumerico_" + this.id + "\" class=\"textoError\"></span></div></div>"));
	});
	$j(".campoNumericoAnio").unbind("keypress");
	$j(".campoNumericoAnio").bind("keypress",function (e) {
		var valor = new Number(this.value);
		var tope = new Number(1900);
		
		if ((e.shiftKey && e.which == 45) || e.which != 8 && e.which !== 0&& e.which !== 9&& (e.which < 48 || e.which > 57) && e.which && (e.which < 48 || e.which > 57)) {
				$j("span[id=\"spanNumerico_" + this.id + "\"]").html("S\xf3lo n\xfameros.").show().fadeOut("slow");
				return false;
			}
		
			if ((e.shiftKey && e.keyCode == 45) || e.keyCode != 8 && e.keyCode !== 0&& e.keyCode !== 9&& (e.keyCode < 48 || e.keyCode > 57) && e.keyCode && (e.keyCode < 48 || e.keyCode > 57)) {
				$j("span[id=\"spanNumerico_" + this.id + "\"]").html("S\xf3lo n\xfameros.").show().fadeOut("slow");
				return false;
			}
			
		if(e.keyCode != 13 || e.keyCode != 8) {
			if (valor.toString().length == 4 && valor < tope){
				$j("span[id=\"spanNumerico_" + this.id + "\"]").html("S\xf3lo fechas reales").show().fadeOut("slow");
				this.value="";
				return false;
			}
		}
		if(e.keyCode != 13 || e.keyCode != 8) {
			if (valor.toString().length < 4 ){
				$j("span[id=\"spanNumerico_" + this.id + "\"]").html("fecha cuatro digitos").show().fadeOut("slow");
				//this.value="";
				return true;
			}
		}
	});
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



function bloquearSeccionReportes() {
	iniciarMonitorPeticionReporte = true;
	$j("#divControlesReportes").css("display", "none");
	$j("#monitorPeticionReporte").css("display", "inline");
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



function desbloquearSeccionReportes(estatusPeticion) {
	if(estatusPeticion === true) {		
		$j("#divControlesReportes").css("display", "inline");
		$j("#monitorPeticionReporte").css("display", "none");
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

function restringirCapturaNarrativa() {
	$j(".campoAlphaNumericoNarrativa").each(function () {
		$j("input[id=\"" + this.id + "\"]").after($j("<div id=\"erroraAlphaNumerico_" + this.id + "\" style=\"position:relative;\"><div style=\"position:absolute;\"><span id=\"spanAlphaNumerico_" + this.id + "\" class=\"textoError\"></span></div></div>"));
	});
	
	$j(".campoAlphaNumericoNarrativa").unbind("keypress");
	$j(".campoAlphaNumericoNarrativa").bind("keypress",function (e) {			
		if (e.which != 8 && e.which !== 0  && e.which !== 9 && e.which !== 13 && (e.which < 32 || e.which > 59) && (e.which < 63 || e.which > 95)&& (e.which < 96 || e.which > 125)) {
			$j("span[id=\"spanAlphaNumerico_" + this.id + "\"]").html("Car\u00e1cter no permitido.").show().fadeOut("slow");
			return false;
		}
		if (e.keyCode != 8 && e.keyCode !== 0  && e.keyCode !== 9 && e.which !== 13 && (e.keyCode < 32 || e.keyCode > 59) && (e.keyCode < 63 || e.keyCode > 95)&& (e.keyCode < 96 || e.keyCode > 125)) {
			$j("span[id=\"spanAlphaNumerico_" + this.id + "\"]").html("Car\u00e1cter no permitido.").show().fadeOut("slow");
			return false;
		}
						
	});
	
}


$j(document).ready(function() {
    if($j("input[id$='produccion']").val()){
    	llenaAbreviacion();
    }
});

/**
* Valida los campos de la forma de alta de depositante
*/
function validaAltaDepositante() {
	var claveParticipante = $j("input[id$='idFolioTraspasante']");
	var cuentaParticipante = $j("input[id$='cuentaTraspasante']");
	var bicDepLiq = $j("input[id$='bicDepLiq']");
	var idDepLiq = $j("input[id$='idDepLiq']");
	var depLiq = $j("input[id$='depLiq']");
	
	if(claveParticipante.attr('value') === undefined || trim(claveParticipante.attr('value')).length != 5) {
		mostrarMensajeAlert("Debe ingresar una clave de participante v\u00E1lida");
		return false;
	}
	
	if(cuentaParticipante.attr('value') === undefined || trim(cuentaParticipante.attr('value')).length != 4) {
		mostrarMensajeAlert("Debe ingresar una cuenta de participante v\u00E1lida");
		return false;
	}
	
	if(bicDepLiq.attr('value') === undefined || trim(bicDepLiq.attr('value')).length == 0) {
		mostrarMensajeAlert("El campo BIC Dep\u00F3sito Liquidador es requerido");
		return false;
	}
	
	if(depLiq.attr('value') === undefined || trim(depLiq.attr('value')).length == 0) {
		mostrarMensajeAlert("El campo Dep\u00F3sito Liquidador es requerido");
		return false;
	}
	
	if(idDepLiq.attr('value') === undefined || trim(idDepLiq.attr('value')).length == 0) {
		mostrarMensajeAlert("El campo ID Dep\u00F3sito Liquidador es requerido");
		return false;
	}
	
	return true;
}

/**
* Valida los campos de la forma de alta de emision
*/
function validaAltaEmision() {
	var claveParticipante = $j("input[id$='idFolioTraspasante']");
	var cuentaParticipante = $j("input[id$='cuentaTraspasante']");
	var tv = $j("input[id$='tv']");
	var emisora = $j("input[id$='emisora']");
	var serie = $j("input[id$='serie']");
	var formaOperar = $j("input[id$='formaOperar']");
	
	if(claveParticipante.attr('value') === undefined || trim(claveParticipante.attr('value')).length != 5) {
		mostrarMensajeAlert("Debe ingresar una clave de participante v\u00E1lida");
		return false;
	}
	
	if(cuentaParticipante.attr('value') === undefined || trim(cuentaParticipante.attr('value')).length != 4) {
		mostrarMensajeAlert("Debe ingresar una cuenta de participante v\u00E1lida");
		return false;
	}
	
	if(tv.attr('value') === undefined || trim(tv.attr('value')).length == 0) {
		mostrarMensajeAlert("El campo TV es requerido");
		return false;
	}	
	
	if(formaOperar.attr('value') === undefined || trim(formaOperar.attr('value')).length == 0) {
		mostrarMensajeAlert("El campo Forma de Operar es requerido");
		return false;
	}
	
	return true;
}

/**
* Valida los campos de la forma de alta de custodio
*/
function validaAltaCustodio() {
	var claveParticipante = $j("input[id$='idFolioTraspasante']");
	var cuentaParticipante = $j("input[id$='cuentaTraspasante']");
	var produccion = $j("input[id$='produccion']");
	var entrenamiento = $j("input[id$='entrenamiento']");
	var cuentaIndeval = $j("input[id$='cuentaIndeval']");
	var pais = $j("input[id$='pais']");
	var abreviacion = $j("input[id$='abreviacion']");
	var mercado = $j("input[id$='mercado']");
	var nombrecorto = $j("input[id$='nombrecorto']");
	var estatus = $j("input[id$='estatus']");
	var detalleCustodio = $j("input[id$='detalleCustodio']");
	
	if(claveParticipante.attr('value') === undefined || trim(claveParticipante.attr('value')).length != 5) {
		mostrarMensajeAlert("Debe ingresar una Clave de Participante v\u00E1lida");
		return false;
	}
	
	if(cuentaParticipante.attr('value') === undefined || trim(cuentaParticipante.attr('value')).length != 4) {
		mostrarMensajeAlert("Debe ingresar una Cuenta de Participante v\u00E1lida");
		return false;
	}
	
	if(produccion.attr('value') === undefined || trim(produccion.attr('value')).length == 0) {
		mostrarMensajeAlert("El campo Producci\u00F3n es requerido");
		return false;
	}
	
		
	if(cuentaIndeval.attr('value') === undefined || trim(cuentaIndeval.attr('value')).length == 0) {
		mostrarMensajeAlert("El campo Cuenta Indeval es requerido");
		return false;
	}
	
	if(pais.attr('value') === undefined || trim(pais.attr('value')).length == 0) {
		mostrarMensajeAlert("El campo Pais es requerido");
		return false;
	}
	
	if(abreviacion.attr('value') === undefined || trim(abreviacion.attr('value')).length == 0) {
		mostrarMensajeAlert("El campo Abreviaci\u00F3n Custodio es requerido");
		return false;
	}
	
	if(mercado.attr('value') === undefined || trim(mercado.attr('value')).length == 0) {
		mostrarMensajeAlert("El campo Mercado es requerido");
		return false;
	}
	
	if(nombrecorto.attr('value') === undefined || trim(nombrecorto.attr('value')).length == 0) {
		mostrarMensajeAlert("El campo Nombre Corto es requerido");
		return false;
	}
	
	if(estatus.attr('value') === undefined || trim(estatus.attr('value')).length == 0) {
		mostrarMensajeAlert("El campo Estatus es requerido");
		return false;
	}
	
	if(detalleCustodio.attr('value') === undefined || trim(detalleCustodio.attr('value')).length == 0) {
		mostrarMensajeAlert("El campo Descripci\u00F3n Custodio es requerido");
		return false;
	}
	
	return true;
}
/**
* presenta los criterios para volver a editarlos despues de la consulta
*/
function presentarCriterios() {
	$j("#divCriterios").css("display", "inline");
	$j("#divResumen").css("display", "none");
	$j("#botonEditarCriterios").css("display", "none");
}

function confirmarBorrarEmision(){
	var output="\u00bfEst\u00e1 seguro de que desea eliminar \u00E9sta Emisi\u00F3n?";
	if(!confirm(output))
	return false;
	return true;
}

function confirmarBorrarDeposito(){
	var output="\u00bfEst\u00e1 seguro de que desea eliminar \u00E9ste Dep\u00F3sito?";
	if(!confirm(output))
	return false;
	return true;
}

function confirmarBorrarCustodio(){
	var output="\u00bfEst\u00e1 seguro de que desea eliminar \u00E9ste Custodio?";
	if(!confirm(output))
	return false;
	return true;
}

/**
 * Valida el campo de Tipo Valor a asignar al Custodio.
 */
function validaTipoValorDeCustodio() {
	var tvDC = $j("input[id$='tipoValorDeCustodio']");

	//Validacion de nulo y longitud
	if (tvDC.attr('value') === undefined || trim(tvDC.attr('value')).length <= 0 || trim(tvDC.attr('value')).length > 4) {
		mostrarMensajeAlert("Debe ingresar un Tipo Valor v\u00E1lido");
		return false;
	}

	//Validacion de caracteres no validos
	var cad = trim(tvDC.attr('value'));
	var len = cad.length;
	var err = false;
	var car;
	var resexp;
	for (var i = 0; i < len; i++) {
		car = cad.charAt(i);
		resexp = car.match(/^[A-Za-z0-9]*/i);
		if (resexp === undefined || resexp == "" || resexp.length == 0) {
			err = true;
		}
	}
	if (err) {
		mostrarMensajeAlert("Caracter no v\u00E1lido en el Tipo Valor");
		return false;
	}

	//Validacion de datos repetidos en el select box
	var selectTVs = document.getElementById("daliForm:idsTiposValorDeCustodio");
	var textoDeSelect;
	var textoInsertado = trim(tvDC.attr('value')).toUpperCase();
	if (selectTVs.length > 0) {
		for (var i = 0; i < selectTVs.length; i++) {
			textoDeSelect = selectTVs.options[i].text;
			if (textoDeSelect == textoInsertado) {
				mostrarMensajeAlert("Tipo Valor repetido");
				return false;
			}
		}
	}

	return true;
}

/**
 * Valida el campo de porcentaje de retencion de un tipo de formato.
 */
function validaPorcentajeRetencion() {
	var porcentajeRetencion = $j("input[id$='porcentajeRetencion']");

	//Validacion de nulo y longitud
	if (porcentajeRetencion.attr('value') === undefined || trim(porcentajeRetencion.attr('value')).length <= 0 || trim(porcentajeRetencion.attr('value')).length > 6) {
		mostrarMensajeAlert("Debe ingresar un Porcentaje de Retenci\u00F3n v\u00E1lido");
		return false;
	}

	//Validacion de caracteres no validos
	var porcret = trim(porcentajeRetencion.attr('value'));
	var isValid = porcret.match(/^((100(\.0{1,2})?)|(\d{1,2}(\.\d{1,2})?))$/) == null ? false : true;
	if (!isValid) {
		mostrarMensajeAlert("Porcentaje de Retenci\u00F3n no v\u00E1lido");
		return false;
	}

	//Validacion de datos repetidos en el select box
	var selectDeFormatos = document.getElementById("daliForm:idsFormatosDeCustodio");
	var selectBeneficiarios = document.getElementById("daliForm:selectTiposBeneficiarios");
	var porcentajeInsertado = parseFloat(trim(porcentajeRetencion.attr('value')));
	var idSelectDeBenef = selectBeneficiarios.value;
	var porcentajeDeSelectBox;
	var idSelectBox;
	var splitTextSelectBox;
	var textSelectBox;
	if (selectDeFormatos.length > 0) {
		for (var i = 0; i < selectDeFormatos.length; i++) {
			idSelectBox = selectDeFormatos.options[i].value;
			textSelectBox = selectDeFormatos.options[i].text;
			splitTextSelectBox = textSelectBox.split('-');
			porcentajeDeSelectBox = parseFloat(splitTextSelectBox[1]);
			if (idSelectDeBenef == idSelectBox && porcentajeInsertado == porcentajeDeSelectBox) {
				mostrarMensajeAlert("Formato repetido");
				return false;
			}
		}
	}

	return true;
}

function llenaAbreviacion(){

	var abreviacion = $j("input[id$='abreviacion']");
	
	abreviacion.attr('value',$j("input[id$='produccion']").val().substring(0,4));
	
	$j("input[id$='abreviacionHidden']").attr('value',abreviacion.attr('value'));
	
} 



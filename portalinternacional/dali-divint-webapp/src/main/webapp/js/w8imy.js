/**
 * Inicializa la forma de captura W8IMY
 * @param apendice4 Apendice a mostrar
 */
function initCapturaW8IMY(apendice4) {
	//Si hay un apendice del campo 4, lo muestra
	if(!isBlank(apendice4)) {		
		$j(apendice4).toggle();
	}
}

/**
 * Inicializa la forma para la modificación
 * @param apendice4 Apendice asociado al campo 4
 * @param apendice5 Apendice asociado al campo 5
 * @param tipoBeneficiario Tipo de beneficiario
 */
function initModificacionW8IMY(apendice4, apendice5, tipoBeneficiario) {
	//Inicializa la parte 4
	var opcionesCmp4Vacias = true;
	$j('input[id*=partIcmp4]').attr('disabled',true);
	$j('input[id*=partIcmp4]').attr('readonly',true);
	if(tipoBeneficiario === "16") {		
		$j('input[id*=partIcmp4]:checked').each(function() {
			if (this.checked) {
				$j(this).attr('disabled',false);
				$j(this).attr('readonly',false);
				opcionesCmp4Vacias = false;
			}
		});
		if(opcionesCmp4Vacias) {
			$j('input[id*=partIcmp4]').attr('disabled',false);
			$j('input[id*=partIcmp4]').attr('readonly',false);
		}
	}
	//Inicializa la parte 5
	var opcionesCmp5Vacias = true;
	$j('input[id*=partIcmp5]').attr('disabled',true);
	$j('input[id*=partIcmp5]').attr('readonly',true);
	$j('input[id*=partIcmp5]:checked').each(function() {
		if (this.checked) {
			$j(this).attr('disabled',false);
			$j(this).attr('readonly',false);
			opcionesCmp5Vacias = false;
        }
	});
	if(opcionesCmp5Vacias) {
		$j('input[id*=partIcmp5]').attr('disabled',false);
		$j('input[id*=partIcmp5]').attr('readonly',false);
	}
	//Valida los apendices
	if((!isBlank(apendice4) && apendice4 === '.partV') && (!isBlank(apendice5) && apendice5 === '.partV') ) {
		if($j("input[id$=partIcmp4c]").attr('checked') || $j("input[id$=partIcmp5f]").attr('checked')) {
			$j(".partV").show();
		}
		else {
			$j(".partV").hide();
		}
	}
	else {		
		if(!isBlank(apendice4)) {		
			$j(apendice4).toggle();
		}
		if(!isBlank(apendice5)) {		
			$j(apendice5).toggle();
		}
	}
}

/**
 * Muestra los diferentes divs que contienen los apendices del formato W8IMY
 * @param nombreDiv Nombre del div a mostrar
 */
function mostrarDivW8IMY(nombreDiv) {
	classDiv = '.' + nombreDiv;
	if(nombreDiv === 'partV') {
		if($j("input[id$=partIcmp4c]").attr('checked') || $j("input[id$=partIcmp5f]").attr('checked')) {
			$j(classDiv).show();
		}
		else {
			$j(classDiv).hide();
		}
	}
	else {
		$j(classDiv).toggle();
	}
}

/**
 * Función que habilita o deshabilita los checkbox asociados
 * al campo 4.
 * @param object Objeto checkbox que al que se le dio click.
 */
function habilitarDeshabilitarChecksCampo4(object) {
	var id = $j(object).attr('id');
	var id2 = id.split(":");
	var checado = document.getElementById(id).checked;
	$j('input[id*=partIcmp4]').attr('disabled',checado);
	$j('input[id*=partIcmp4]').attr('readonly',checado);
	$j("input[id$=" + id2[1] + "]").attr('disabled',false);
	$j("input[id$=" + id2[1] + "]").attr('readonly',false);
}

/**
 * Función para hacer excluyentes las opciones del campo 5
 * @param object El campo de la forma
 */
function habilitarDeshabilitarCampo5(object) {
	var id = $j(object).attr('id');
	var id2 = id.split(":");
	var checado = document.getElementById(id).checked;
	$j('input[id*=partIcmp5]').attr('disabled',checado);
	$j('input[id*=partIcmp5]').attr('readonly',checado);
	$j("input[id$=" + id2[1] + "]").attr('disabled',false);
	$j("input[id$=" + id2[1] + "]").attr('readonly',false);
}

/**
 * Valida si es cadena blanca.
 * @param str Cadena a validar
 * @returns true si es cadena blanca
 */
function isBlank(str) {
    return (!str || /^\s*$/.test(str));
}
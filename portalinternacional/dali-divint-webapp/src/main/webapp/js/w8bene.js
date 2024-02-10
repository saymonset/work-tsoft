/**
 * Muestra los diferentes divs que contienen los apendices del formato W8BEN-E
 * @param nombreDiv Nombre del div a mostrar
 */
function mostrarDivW8BENE(nombreDiv) {
	classDiv = '.' + nombreDiv;
	$j(classDiv).toggle();
}

/**
 * Inicializa la forma para la modificación
 */
function initModificacion(apendice, tipoBeneficiario) {	
	//Inicializa la parte 4
	$j('input[id*=chap3Stat_]').attr('disabled',true);
	$j('input[id*=chap3Stat_]').attr('readonly',true);
	if(tipoBeneficiario === "15") {		
		$j('input[id*=chap3Stat_]:checked').each(function() {
			if (this.checked) {
				$j(this).attr('disabled',false);
				$j(this).attr('readonly',false);
			}
		});
	}
	var habilitarDisclaimer = true;
	$j('input[id*=disclaimer_]').attr('disabled',true);
	$j('input[id*=disclaimer_]').attr('readonly',true);
	$j('input[id*=disclaimer_]:checked').each(function() {
		if (this.checked) {
			$j(this).attr('disabled',false);
			$j(this).attr('readonly',false);
			habilitarDisclaimer = false;
        }
	});
	if(habilitarDisclaimer) {
		$j('input[id*=disclaimer_]').attr('disabled',false);
		$j('input[id*=disclaimer_]').attr('readonly',false);
	}
	
	
	//Inicializa la parte 5
	$j('input[id*=partIcmp5]').attr('disabled',true);
	$j('input[id*=partIcmp5]').attr('readonly',true);
	$j('input[id*=partIcmp5]:checked').each(function() {
		if (this.checked) {
			$j(this).attr('disabled',false);
			$j(this).attr('readonly',false);			
        }
	});
	

	//inicializa campo 14 checks
	$j('input[id*=partIIIcmp14bo]').attr('disabled',true);
	$j('input[id*=partIIIcmp14bo]').attr('readonly',true);
	$j('input[id*=partIIIcmp14bo]:checked').each(function() {
		if (this.checked) {
			$j(this).attr('disabled',false);
			$j(this).attr('readonly',false);
			if($j(this).attr('id').indexOf('partIIIcmp14boj') != -1 ){
				$j("input[id*=partIIIcmp14bok]").attr('disabled',false);
				$j("input[id*=partIIIcmp14bok]").attr('readonly',false);
			}
        }
	});
	
	//Si hay un apendice, lo muestra
	if(!isBlank(apendice)) {		
		$j(apendice).toggle();
	}
	if(document.getElementById('daliForm:partXXVIcmp40c').checked){
		mostrarDivW8BENE('partXXIX');
	}
	
}



/**
 * 
 * @param object
 */
function habilitarDeshabilitarOpcionesCampo4BenefOtros(object) {
	var id = $j(object).attr('id');
	var id2 = id.split(":");
	var checado = document.getElementById(id).checked;
	$j('input[id*=chap3Stat_]').attr('disabled',checado);
	$j('input[id*=chap3Stat_]').attr('readonly',checado);
	$j("input[id$=" + id2[1] + "]").attr('disabled',false);
	$j("input[id$=" + id2[1] + "]").attr('readonly',false);
}

/**
 * 
 * @param object
 */
function habilitarDeshabilitarCampo4(object) {
	var id = $j(object).attr('id');
	var id2 = id.split(":");
	var checado = document.getElementById(id).checked;
	document.getElementById('daliForm:disclaimer_cmp4l').disabled = checado;
	document.getElementById('daliForm:disclaimer_cmp4m').disabled = checado;
	document.getElementById('daliForm:disclaimer_cmp4l').readonly = checado;
	document.getElementById('daliForm:disclaimer_cmp4m').readonly = checado;
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
	
	if(!(document.getElementById('daliForm:partIcmp5ab').checked )){
		$j('.partXXIX').hide();
	}
	if(document.getElementById('daliForm:partIcmp5ab').checked &&  document.getElementById('daliForm:partXXVIcmp40c').checked){
		$j('.partXXIX').show();
	}
	
}

function habilitarDeshabilitarCampos14b(object){
	var id = $j(object).attr('id');
	var id2 = id.split(":");
	var checado = document.getElementById(id).checked;
	
	$j('input[id*=partIIIcmp14bo]').attr('disabled',checado);
	$j('input[id*=partIIIcmp14bo]').attr('readonly',checado);
	$j("input[id$=" + id2[1] + "]").attr('disabled',false);
	$j("input[id$=" + id2[1] + "]").attr('readonly',false);
	if(id2[1]=='partIIIcmp14boj'){		
		$j("input[id*=partIIIcmp14bok]").attr('disabled',false);
		$j("input[id*=partIIIcmp14bok]").attr('readonly',false);
	}else{
		$j("input[id*=partIIIcmp14bok]").val("");
	}
	if(!document.getElementById('daliForm:partIIIcmp14b').checked &&  checado){
		document.getElementById('daliForm:partIIIcmp14b').checked = true;
	}else if(!checado){
		document.getElementById('daliForm:partIIIcmp14b').checked = false;
	}
	
}

function habilitarDeshabilitarCampos26(object){
	var id = $j(object).attr('id');
	var id2 = id.split(":");
	var checado = document.getElementById(id).checked;
	
	$j('input[id*=partXIIcmp26bo]').attr('disabled',checado);
	$j('input[id*=partXIIcmp26bo]').attr('readonly',checado);
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
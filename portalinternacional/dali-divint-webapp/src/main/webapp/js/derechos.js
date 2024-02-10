$j(document).on('ready',function(){
		$j('#beneficiarios').dialog(
				{ 
					autoOpen:false,
					height:650,
					maxheight:1200,
					maxWidth:1200,
					modal: true,
					resizable: false,
					width:950,
					zIndex:10003,
					close: function(ev,ui) {
						   document.getElementById('daliForm:botonBuscarBenef').click();	
					}
				 }
			);
	});




function agregarBeneficiario(idDerecho,idEstatusDerecho,idCuenta,idEmision,porcentajeRet,tipoValor,emisora,serie,claveInstitucion,cuenta){
    var fechaCorte = document.getElementById('daliForm:fechaCorte').value;
    var url = contextPath+'/derechos/agregarBeneficiarioDerecho.faces';
    url += '?idDerecho='+idDerecho
    + '&idEstatusDerecho='+idEstatusDerecho
    + '&idCuentaNombrada='+idCuenta
    + '&idEmision='+idEmision
    + '&porcentajeDefault='+porcentajeRet
    + '&tipoValor='+tipoValor
    + '&emisora='+emisora
    + '&serie='+serie
    + '&fechaCorte='+fechaCorte
    + '&claveInstitucion='+claveInstitucion
    + '&cuenta='+cuenta;
    
    $j('#beneficiarios').dialog( "option", "title", "Asignacion de Beneficiarios");
    $j('#beneficiarios').dialog('open');
	$j('#beneficiarios').attr('src',url);
	$j('#beneficiarios').css('width','99%');

	$j('.ui-widget-overlay') .on("click", function() {
	    $j('#beneficiarios').dialog('close');
	});


}

function closeAgregaBeneficiarioDerecho() {
	$j('#beneficiarios').dialog('close');   
} 


function checkControls(){
	var checkPrincipal = window.document.getElementById('daliForm:resultados:checkPrincipal');
	var c = new Array();
	c = window.document.getElementsByTagName('input');
	for (var i = 0; i < c.length; i++){
		if (c[i].type == 'checkbox'){
			if(checkPrincipal.checked){
				c[i].checked = true;
			}else{
				c[i].checked = false;
			}	      
	    }
	}
}


function closeMuestraBeneficiario() {
	parent.$j("input[id$='botonBuscar']").click();
	parent.$j('#beneficiarios').dialog('close');
     
}

function disableSubmit(){	
	$j("input[id$='daliForm:btnProcesar']").attr("disabled", true);
}

function validateSizeFile(archivo){
	var tamanioKb = archivo.files[0].size/1024;
	if(tamanioKb > 10240){
		alert('El tamaño del archivo debe ser menor a 10,240 Kb');
		document.getElementById('daliForm:beneficiarios').value = '';
	}
	
}


/**
 * Confirmacion del cambio de estado e inabilitado de botones.
 */
function confirmacionEInabilitadoBotones(mensaje, estatusDerecho) {
    var botonesABloquear;

    if (!confirm(mensaje)) {
        return false;
    }

    switch(estatusDerecho) {
    case 1:
        botonesABloquear = ['botonSimular', 'botonRegresar'];
        break;
    case 2:
        botonesABloquear = ['botonSimular', 'botonAutorizar', 'botonRegresar'];
        break;
    case 3:
        botonesABloquear = ['botonCambiarPrevio', 'botonConfirmar', 'botonRegresar'];
        break;
    case 4:
        botonesABloquear = ['botonCambiarAutorizado', 'botonPagar', 'botonRegresar'];
        break;
    }

    for (i = 0; i < botonesABloquear.length; i++) {
    	$j("input[id$=\"" + botonesABloquear[i] + "\"]").hide();
	}
    $j("#monitorPeticionCambioEstado").css("display", "inline");

    return true;
}


/**
 * Agregar beneficiarios Popup - deshabilitar botones
 */
function agregarBeneficiariosInabilitadoBotones(mensaje, estatusDerecho) {
    var botonesABloquear;
    botonesABloquear = ['botonProcesar', 'botonGuardar', 'panelDetalleCarga'];
    for (i = 0; i < botonesABloquear.length; i++) {
    	$j("input[id$=\"" + botonesABloquear[i] + "\"]").hide();
	}
    $j("#monitorPeticionCambioEstado").css("display", "inline");

    return true;
}



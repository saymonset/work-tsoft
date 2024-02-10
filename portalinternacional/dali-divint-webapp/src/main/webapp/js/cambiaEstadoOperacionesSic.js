combosEstatus = new Array();
checkAutorizar = new Array();
checkAutorizarParcialidades = new Array();
checkCancelar = new Array();
checkCancelarParcialidades = new Array();
checkActualizarMotivo = new Array();
checkActualizarMotivoParcialidades = new Array();
iCombos = 0;
iCombosParcialidades = 0;
iAutorizar = 0;
iAutorizarParcialidades = 0;
iCancelar = 0;
iCancelarParcialidades = 0;
iActMotivo = 0;
iActMotivoParcialidades = 0;

$j(document).on('ready',function(){
	$j('#detalle').dialog(
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

function validarNumeroConComas(numero) {
    if (!/([0-9])+/.test(numero)) {
    	alert("El campo debe contener solo numeros");
    }
}

function cambiaEstatusTodos(componentSelect) {
    for (var i = 0 ; i < iCombos ; i++ ) {
    	if(typeof combosEstatus[i].attr('disabled')=="undefined")
    		combosEstatus[i].val(componentSelect.selectedIndex);
    }
}

function cambiaEstatusParcialidadesTodos(componentSelect) {
    for (var i = 0 ; i < iCombosParcialidades ; i++ ) {
    	if(typeof combosEstatusParcialidades[i].attr('disabled')=="undefined")
    		combosEstatusParcialidades[i].val(componentSelect.selectedIndex);
    }
}

function autorizarTodos(componentSelect) {
    for (var i = 0 ; i < iAutorizar ; i++ ) {
    	checkAutorizar[i].attr('checked', componentSelect.checked);
    }
}

function autorizarTodosParcialidades(componentSelect) {
    for (var i = 0 ; i < iAutorizarParcialidades ; i++ ) {
    	checkAutorizarParcialidades[i].attr('checked', componentSelect.checked);
    }
}

function cancelarTodos(componentSelect) {
    for (var i = 0 ; i < iCancelar ; i++ ) {
    	if(typeof checkCancelar[i].attr('disabled')=="undefined")
    		checkCancelar[i].attr('checked', componentSelect.checked);
    }
}

function cancelarTodosParcialidades(componentSelect) {
    for (var i = 0 ; i < iCancelarParcialidades ; i++ ) {
    	if(typeof checkCancelarParcialidades[i].attr('disabled')=="undefined")
    		checkCancelarParcialidades[i].attr('checked', componentSelect.checked);
    }
}

function actualizarMTodos(componentSelect) {
    for (var i = 0 ; i < iActMotivo ; i++ ) {
    	if(typeof checkActualizarMotivo[i].attr('disabled')=="undefined")
    		checkActualizarMotivo[i].attr('checked', componentSelect.checked);
    }
}

function actualizarMTodosParcialidades(componentSelect) {
    for (var i = 0 ; i < iActMotivoParcialidades ; i++ ) {
    	if(typeof checkActualizarMotivoParcialidades[i].attr('disabled')=="undefined")
    		checkActualizarMotivoParcialidades[i].attr('checked', componentSelect.checked);
    }
}

function mensajeConfirmacion(e) {
    var len = $j("#daliForm .grupoAutoriza").size();
	var folios = "";
	for (var i = 0 ; i < len ; i++ ) {
		if($j("#daliForm .grupoAutoriza input")[i].checked){
			folios = folios.concat($j("#daliForm .grupoAutoriza input")[i].className.split("_")[1]).concat(", ");
		}		
	}	
	if(folios!=""){
		folios = folios.substring(0, folios.length-2);
		var mensaje = confirm('¿Esta usted seguro de autorizar los folios? \n'+folios)
		return mensaje;
	}else{
		return mensajeConfirmacionParcialidad();
//		alert('No hay folios seleccionados');
	}
}

function mensajeConfirmacionParcialidad(e) {
    var len = $j("#daliForm .grupoAutorizaParcialidad").size();
	var folios = "";
	for (var i = 0 ; i < len ; i++ ) {
		if($j("#daliForm .grupoAutorizaParcialidad input")[i].checked){
			folios = folios.concat($j("#daliForm .grupoAutorizaParcialidad input")[i].className.split("_")[1]).concat(", ");
		}		
	}	
	if(folios!=""){
		folios = folios.substring(0, folios.length-2);
		var mensaje = confirm('¿Esta usted seguro de autorizar los folios? \n'+folios)
		return mensaje;
	}else{
		alert('No hay folios seleccionados');
	}
}

function validoAplicarCambios(nombreBoton) {
	//Se deshabilita el boton
	$j("input[id$=\"" + nombreBoton + "\"]").attr("disabled", true);

	var motivoDelCambio = $j("textarea[id$='idInputMotivoCambio']");

	//Validacion de nulo y vacio
	if (motivoDelCambio.attr('value') === undefined || trim(motivoDelCambio.attr('value')).length <= 0) {
		$j("input[id$=\"" + nombreBoton + "\"]").attr("disabled", false);
		mostrarMensajeAlert("El motivo del cambio no puede estar vac\u00EDo");
		return false;
	}

	return true;
}


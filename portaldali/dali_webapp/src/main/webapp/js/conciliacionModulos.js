function configuraTabs(idTabSeleccionada) {
	$j.each($j('#tabs ul li a'), function(index, linkTab) {
		$j(linkTab).attr('href', '#tab' + $j(linkTab).parent().data('idTab'));
	});
	
	$j('#tabs').tabs();
	onChangeChk();
	
	if(idTabSeleccionada != null) {
		$j('#tabs li.ui-tabs-selected').removeClass("ui-tabs-selected ui-state-active");
		$j('#tabs a[href="#tab'+ idTabSeleccionada +'"]').parent().addClass("ui-tabs-selected ui-state-active");
		$j('#tabs div#tab'+ idTabSeleccionada).removeClass('ui-tabs-hide');
	}
}

function configuraResumen() {
	$j('#divCriterios').hide();
	$j('#resTipoInstruccion').html($j('#daliForm\\:selectTipoInstruccion').find('option:selected').html());
//	$j('#resTipoOperacion').html($j('#daliForm\\:selectTipoOperacion').find('option:selected').html());
}

function editarCriterios() {
	$j('#divResumen').hide();
	$j("#botonEditarCriterios").hide();
	$j('#divCriterios').show();
}

function seleccionaTab(tab) {
	$j('#daliForm\\:idTabHidden').val(tab);
}

function seleccionarTodosChk(chkMaster) {
	$j('input[type=checkbox].chk').prop('checked', $j(chkMaster).prop('checked'));
}

function onChangeChk() {
	var checkedChkMaster = true;
	
	$j('input[type=checkbox].chk').each(function(index, chk) {
		if(!$j(chk).prop('checked')) {
			checkedChkMaster = false;
		}
	});
	
	$j('input[type=checkbox].chkMaster').prop('checked', checkedChkMaster);	
}

function onChangeSelect(select) {
	var selectedText = $j(select).find('option:selected').html();
	$j($j(select).parent('td')).find('input[type=hidden]').val(selectedText);
}

function confirmarReenvio() {
	if($j('input[type=checkbox].chk:checked').length > 0){
		var textoConfirmacion="\u00bfEst\u00e1 seguro(a) de que desea reenviar los "+ $j('input[type=checkbox].chk:checked').length +" registros seleccionados?";
		this.scroll(0,0);
		
		if(!confirm(textoConfirmacion)) {
			return false;
		} else {
			reenviarMensajes();
			return true;
		}
	}

}

function alertarOperacionesreenviadas(idTabSeleccionada){
	alert("Reenvío de operaciones exitoso.");	
	configuraTabs(idTabSeleccionada);
}

function alertarReenvioRete(idTabSeleccionada, mensaje){	
//	alert(mensaje);
	configuraTabs(idTabSeleccionada);
}

function confirmarReenvioRete() {
	if($j('input[type=checkbox].chk:checked').length > 0){
		var textoConfirmacion="\u00bfEst\u00e1 seguro(a) de que desea reenviar "+ $j('input[type=checkbox].chk:checked').length +" confirmacion(es) de liquidacion ?";
		this.scroll(0,0);
		
		if(!confirm(textoConfirmacion)) {
			return false;
		} else {
			reenviarMensajesRete();
			return true;
		}
	} else {
		alert('Por favor seleccione por lo menos un retiro');
	}
			
}

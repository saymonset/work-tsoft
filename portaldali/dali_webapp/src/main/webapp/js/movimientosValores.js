// JavaScript Document
var $j = jQuery.noConflict();

$j(document).on('ready',function(){
	$j('#detalleMovimiento').dialog(
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
	
function colapsar(idEmision, idBoveda) {
	if($j('#icon_open_' + idEmision + '_' + idBoveda ).css('display') != 'none') {
		$j('#renglon_' + idEmision + '_' + idBoveda ).show();
	}
	$j('#detalleEmision_' + idEmision + '_' + idBoveda ).slideToggle('normal', function(){
		$j('#icon_open_' + idEmision + '_' + idBoveda ).toggle();
		$j('#icon_close_' + idEmision + '_' + idBoveda ).toggle();
		if($j('#icon_open_' + idEmision + '_' + idBoveda ).css('display') != 'none') {
			$j('#renglon_' + idEmision + '_' + idBoveda ).hide();
		} else {
			$j('#renglon_' + idEmision + '_' + idBoveda ).show();	
		}
	});
}

function buscarRegistros() {
    $j('input[id$=botonBuscar]').prop("disabled", "disabled");
    return true;
}

function presentarResultados() { 
						
	$j('#contenido').css('display','block');
	
	$j("#divCriterios").css('display','none');
	$j("#divResumen").css('display','inline');
	$j("#botonEditarCriterios").css('display','inline');
	
	$j('input[id$=botonBuscar]').removeAttr('disabled');
}

function presentarCriterios() {

	$j("#divCriterios").css('display','inline');
	$j("#divResumen").css('display','none');
	$j("#botonEditarCriterios").css('display','none');
}

function addLoader(id) {

	if($j('#loader' + id).prop('id') === undefined) {
		$j("input[id$='" + id + "']").after('<img src="'+contextPath+'/images/loading1.gif" border="0" id="loader' + id + '" />');
	}
}

function removeLoader(id) {
	if($j('#loader' + id)) {
		$j("#loader" + id).remove();
	}
}

function mostrarDetalle(titulo,url){
	$j('#detalleMovimiento').dialog('open');
	$j('#detalleMovimiento').dialog('option','title',titulo);
	$j('#detalleMovimiento').prop('src',url);
	$j('#detalleMovimiento').css('width','99%');	
	$j('.ui-widget-overlay') .on("click", function() {
	    $j('#detalleMovimiento').dialog('close');
	});
}



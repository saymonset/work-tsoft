// JavaScript Document
var $j = jQuery.noConflict();

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
	
function buscarRegistros() {
	console.log("Buscar Registros");
	$j('input[id$=botonBuscar]').prop('disabled','disabled');
	return true;
}

function presentarResultados() { 
console.log("Presentar Resultados")
	$j('#contenido').css('display','block');
	//$j('#contenido').css('opacity','0');
	//$j('#contenido').animate({
	//	opacity: 1
	//}, 500);
	
	$j("#divCriterios").css('display','none');
	$j("#divResumen").css('display','inline');
	$j("#botonEditarCriterios").css('display','inline');
	  
	$j('input[id$=botonBuscar]').removeAttr('disabled');
}

function presentarCriterios(){
	console.log("presentarCriterios ... ")
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
	$j('#detalle').dialog('open');
	$j('#detalle').dialog('option','title',titulo);
	$j('#detalle').prop('src',url);
	$j('#detalle').css('width','99%');
	$j('.ui-widget-overlay') .on("click", function() {
	    $j('#detalle').dialog('close');
	});
}
																				

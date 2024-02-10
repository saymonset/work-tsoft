$j(document).on('ready',function(){
	$j('#noScrolling').dialog(
		{ 
			autoOpen:false,
			height:600,
			maxheight:600,
			maxWidth:960,
			modal: true,
			resizable: false,
			width:960,
			zIndex:10003
		 }
	);
});

function detalleResultadoMensaje(folio){
    var url = contextPath+'/conciliacionInternacional/mensajeConfirmacion.faces';
    url += '?idConfirmacion=' + folio   
    + '&idMuestraConsulta=false'
    + '&TB_iframe=true'
    +'&height=500&width=950';
    mostrarDetalle('Mensaje Confirmacion',url);
}

function presentarCriterios() {
	$j("#divCriterios").css("display", "inline");
	$j("#divResumen").css("display", "none");
	$j("#botonEditarCriterios").css("display", "none");
}

function presentarResumen() {
	$j("#divCriterios").css("display", "none");
	$j("#divResumen").css("display", "inline");
	$j("#divResumen").css("opacity", "1");	
	$j("#botonEditarCriterios").css("display", "inline");
}

function mostrarDetalle(titulo,url){
	$j('#noScrolling').dialog('open');
	$j('#noScrolling').dialog('option','title',titulo);
	$j('#noScrolling').prop('src',url);
	$j('#noScrolling').css('width','99%');
	
	$j('#noScrolling').dialog({
	     close: function(event, ui) {
	     $j("input[id$='control_bitacora_actual']").click();    
	     }
	 });
	
	$j('.ui-widget-overlay') .on("click", function() {
	    $j('#noScrolling').dialog('close');
	});
}

function printTextareaContent(textarea_field,printing_div) {
	//document.getElementById(printing_div).innerHTML = document.getElementById(textarea_field).value;
	document.getElementById(printing_div).innerHTML = document.getElementById(textarea_field).value;
	window.focus();
	window.print();	
}

function actualizaComentarioDali(folio, comentarios){
    var url = contextPath+'/conciliacionInternacional/confirmacionIntActComenDali.faces';
    url += '?idConfirmacion=' + folio   
    + '&comentariosDali=' + comentarios
    + '&TB_iframe=true'
    +'&height=600&width=960';
    mostrarDetalle('Agrega / Actualiza Comentario Dali',url);
}

function cierraDialog(){
	parent.$j('#noScrolling').dialog('close');
}

function confirmaComentarioDali(){
	return (confirm("\u00bfEsta seguro de actualizar el comentario Dali para este registro?"));
}

function actualizaIdFolio(folio, idFolio){
    var url = contextPath+'/conciliacionInternacional/confirmacionIntIdFolio.faces';
    url += '?idConfirmacion=' + folio   
    + '&idFolio=' + idFolio
    + '&TB_iframe=true'
    +'&height=250&width=960';
    mostrarDetalle('Agrega / Actualiza Id Folio',url);
}

function confirmaIdFolio(){
	return (confirm("\u00bfEsta seguro de actualizar el Id Folio para este registro?"));
}
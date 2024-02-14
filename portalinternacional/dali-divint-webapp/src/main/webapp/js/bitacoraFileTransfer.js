function mostrarDetalle(titulo,url){
	$j('#detalle').dialog('option','title',titulo);	
	$j('#detalle').dialog('option','height',650);
	$j('#detalle').dialog('option','width',950);	
	$j('#detalle').dialog('open');
	$j('#detalle').prop('src',url);
	$j('#detalle').css('width','99%');
	$j('.ui-widget-overlay') .on("click", function() {
	    $j('#detalle').dialog('close');
	});
}

function detalleFileTransferDivisas(idFileTransferDivisasInt) {

    url = contextPath + '/divinternacional/detalleFileTransferDivisas.faces';
    url += '?idFileTransferDivisasInt=' + idFileTransferDivisasInt;
	mostrarDetalle('Detalle',encodeURI(url),'');
    
}
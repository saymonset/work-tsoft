$j(document).on('ready',function(){
	$j('#detalleFileTransfer').dialog(
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

function mostrardetalleFileTransfer(titulo,url){
	$j('#detalleFileTransfer').dialog('open');
	$j('#detalleFileTransfer').dialog('option','title',titulo);
	$j('#detalleFileTransfer').prop('src',url);
	$j('#detalleFileTransfer').css('width','99%');
	$j('.ui-widget-overlay') .on("click", function() {
	    $j('#detalleFileTransfer').dialog('close');
	});
}
//Javascript para mostrar el detalle de CFI

$j(document).on('ready',function(){
	$j('#detalleCfi').dialog(
			{ 
				autoOpen:false,
				height:150,
				maxheight:150,
				maxWidth:950,
				modal: true,
				resizable: false,
				width:950,
				zIndex:10003
			 }
		);
});

function mostrarDetalleCfi(titulo,url){
	$j('#detalleCfi').dialog('open');
	$j('#detalleCfi').dialog('option','title',titulo);
	$j('#detalleCfi').prop('src',url);
	$j('#detalleCfi').css('width','99%');
	$j('.ui-widget-overlay') .on("click", function() {
	    $j('#detalleCfi').dialog('close');
	});
}
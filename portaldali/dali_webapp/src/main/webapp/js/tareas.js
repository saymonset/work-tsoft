$j(document).on('ready',function(){
	$j('#modalFrame').dialog(
			{ 
				autoOpen:false,
				resizable:false,
				height:650,
				maxheight:1200,
				maxWidth:1200,
				modal: true,
				
				width:950,
				zIndex:10003
			 }
		);
});

function buscarRegistros(){
	//deshabilitarBoton('botonBuscar');
	//deshabilitarBoton('botonEditarCriterios');
	//$j("#botonEditarCriterios").css('display','none');
}

function presentarCriterios(){
	//$j("#divCriterios").css('display','inline');
	//$j("#divResumen").css('display','none');
	//$j("#botonEditarCriterios").css('display','none');

}
function presentarResultados(){


}
function presentarResumen(){

	//$j("#divCriterios").css('display','none');
	//$j("#divResumen").css('display','inline');

	//$j("#divResumen").css('opacity','0');
	//$j("#divResumen").animate({ opacity:1 }, 1000);
//	$j("#botonEditarCriterios").css('display','inline');
	//habilitarBoton('botonBuscar');
	//habilitarBoton('botonEditarCriterios');
}

function mostrarModal(idTarea){
	url = contextPath+'/indeval/tareas/detalleTarea.faces?idTarea=' + idTarea;
	mostrarIframe('Detalle Tarea',url);
}

function mostrarIframe(titulo, url){
	$j('#modalFrame').dialog('open');
	$j('#modalFrame').dialog('option','title',titulo);
	$j('#modalFrame').prop('src',url);
	$j('#modalFrame').css('width','99%');
	$j('.ui-widget-overlay') .on("click", function() {
	    $j('#modalFrame').dialog('close');
	});
}

function closeIframe(){
	 $j('#modalFrame').dialog('close');
}

function actualizarGrid(){
	btnBuscar = $j('#daliForm\\:btnBuscar');
	btnBuscar.trigger("click");
	
	$j('#modalFrame').dialog('close');
}
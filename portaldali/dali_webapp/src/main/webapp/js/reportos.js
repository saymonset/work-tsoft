$j(document).on('ready',function(){
	$j('#vtoReportosFrame').dialog(
			{ 
				autoOpen:false,
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
	deshabilitarBoton('botonEditarCriterios');
	$j("#botonEditarCriterios").css('display','none');
}

function presentarCriterios(){
	$j("#divCriterios").css('display','inline');
	$j("#divResumen").css('display','none');
	$j("#botonEditarCriterios").css('display','none');

}
function presentarResultados(){


}
function presentarResumen(){

	$j("#divCriterios").css('display','none');
	$j("#divResumen").css('display','inline');

   $j("#divResumen").css('opacity','0');
   $j("#divResumen").animate({
				      		opacity:1
				      	}, 1000);
	$j("#botonEditarCriterios").css('display','inline');
	habilitarBoton('botonBuscar');
	habilitarBoton('botonEditarCriterios');
}

function hasErrors(){
	var errors = document.getElementById("daliForm:validationErrors");
	
	
	return errors.value == "true";
}

function mostrarNuevaSolicitud(idInstruccion){
	url = contextPath+'/mercadoDinero/vencimientoReportos/nuevaSolicitud.faces?idInstruccion=' + idInstruccion;
	mostrarIframe('Nueva Solicitud',url);
}

function mostrarAutorizacionSolicitud(idVencimiento){
	url = contextPath+'/mercadoDinero/vencimientoReportos/detalleSolicitud.faces?idVencimiento=' + idVencimiento + '&consulta=false';
	mostrarIframe('Detalle Solicitud', url);
}

function mostrarDetalleSolicitud(idVencimiento){
	url = contextPath+'/mercadoDinero/vencimientoReportos/detalleSolicitud.faces?idVencimiento=' + idVencimiento + '&consulta=true';
	mostrarIframe('Detalle Solicitud', url);
}

function mostrarIframe(titulo, url){
	$j('#vtoReportosFrame').dialog('open');
	$j('#vtoReportosFrame').dialog('option','title',titulo);
	$j('#vtoReportosFrame').prop('src',url);
	$j('#vtoReportosFrame').css('width','99%');
	$j('.ui-widget-overlay') .on("click", function() {
	    $j('#vtoReportosFrame').dialog('close');
	});
}

function closeIframe(){
	 $j('#vtoReportosFrame').dialog('close');
}

function actualizarGrid(){
	btnBuscar = $j('#daliForm\\:btnBuscar');
	btnBuscar.trigger("click");
	
	$j('#vtoReportosFrame').dialog('close');
}

function limpiarConsulta(){
	btn3 = $j('#daliForm\\:btnLimpiar');
	//btn3.trigger("click");
}
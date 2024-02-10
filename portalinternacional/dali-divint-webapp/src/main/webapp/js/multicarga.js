////// MULTICARGA ADP /////////

function confirmarCargaAdp(){
	var output="\u00bfEst\u00e1 seguro de que desea iniciar la carga del archivo?";
	if(!confirm(output,"titulo ventana"))
	return false;
	return true;
}

function muestraBoton(valor){		
	if(valor == ''){
		$j("#botonOculto").css("display", "none");				
	}else{
		$j("#botonOculto").css("display", "inline");
		$j("#resumenCargaDiv").css("display", "none");
	}	
}

function imagenCargaAdp(){
	$j("#image_carga").css("display", "inline");		
}

////// CONSOLA DE AUTORIZACION /////////


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


function detalleArchivo(idMulticarga, nombreArchivo) {
	url = contextPath+'/cargaMultiple/detalleArchivoMulticarga.faces';
	url += '?idMulticarga=' + idMulticarga;
	url += '&nombreArchivo=' + nombreArchivo;
	mostrarDetalle('Detalle',encodeURI(url),'');
}


function confirmarBorrarArchivo(idEstadoOperacion){
	if(idEstadoOperacion == 1){
		var output="El archivo ser\u00E1 borrado de manera permanente y NO PODR\u00C1 SER RECUPERADO. \u00bfDesea continuar?";		
	}else if(idEstadoOperacion == 2 || idEstadoOperacion == 4 ){
		var output="Al borrar el archivo, se perder\u00E1n los valores de los registros. \u00bfDesea continuar?";
	}	
	if(!confirm(output,"titulo ventana"))
	return false;
	return true;
}


function confirmarAutorizacionArchivo(){
	var output="\u00bfEst\u00e1 seguro de que desea autorizar el archivo?";
	if(!confirm(output,"titulo ventana"))
	return false;
	return true;
}



///// MULTIEMPRESA /////////

function confirmarCargaMultiempresa(){
	var output="\u00bfEst\u00e1 seguro de que desea iniciar la carga del archivo?";
	if(!confirm(output,"titulo ventana"))
	return false;
	return true;
}

function muestraBotonInstitucion(){	
	var valorInstitucion =  $j("input[id$='nombreInstitucion']").attr('value');
	var valorArchivo =  $j("input[id$='archivoOperaciones']").attr('value');
	if(!valorArchivo == '' &&  !valorInstitucion == ''){
		$j("#botonOculto").css("display", "inline");
		$j("#resumenCargaDiv").css("display", "none");							
	}else{
		$j("#botonOculto").css("display", "none");			
	}	
}

function imagenCargaMultiempresa(){
	$j("#image_carga").css("display", "inline");		
}


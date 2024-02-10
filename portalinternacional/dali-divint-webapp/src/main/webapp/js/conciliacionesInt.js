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
	
}

function presentarResultados() {
	
}

function limpiarTodos() {
	
}

function detalleResultadoMensaje(folio){
    var url = contextPath+'/conciliacionInternacional/consultaMensajesConciliacionInternacional.faces';
    url += '?idConciliacion=' + folio   
    + '&idMuestraConsulta=false'
    + '&TB_iframe=true'
    +'&height=550&width=925';
    mostrarDetalle('Conciliacion',url);
}
function detalleResultado(folio){
    var url = contextPath+'/conciliacionInternacional/consultaDetalleConciliacionInternacionalPopUp.faces';
    url += '?folio=' + folio   
    + '&idMuestraConsulta=false'
    + '&TB_iframe=true'
    +'&height=550&width=925';
    mostrarDetalle('Conciliacion',url);
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


function lockBotones(){
	var botones = $j("input[id*='botonConciliar']").get();
	for(var i=0; i<botones.length; i++){		
		botones[i].disabled=true;
	}		
}

function unlockBotones(){
	var botones = $j("input[id*='botonConciliar']").get();
	for(var i=0; i<botones.length; i++){		
		botones[i].disabled=false;
	}	
}

function refresco(){
	document.getElementById('daliForm:botonBuscar').click();
	alert("Actualizado");	
}

function capturaConciliacion(id){
	//idConciliacionHidden
	document.getElementById('daliForm:idConciliacionHidden').value=id;		
}

function validarAccion(){	
	var valor=document.getElementById('daliForm:idConciliacionHidden').value;
	return confirm("Instruir la Conciliación con folio: "+valor+"\n ¿Está usted seguro de querer realizar esta acción?");	
}

function actualizaMensaje(idmensaje){
	 var areaMensaje=document.getElementById('formaBitacoraConciliacion:swiftMensaje');	 
	 var swift = $j("input[id*="+idmensaje+"]").get();
	 areaMensaje.value=swift[0].value;
	 document.getElementById("printing_div_id").innerHTML = swift[0].value;
	 window.scrollTo(0,0);
}

function marcar(id){	
	$j(".renglonTablaUno").css("background","white");
	$j(".renglonTablaDos").css("background","LightGrey ");	
	$j("."+id).parents("tr:eq(0)").css("background","yellow");
}

function printTextareaContent(textarea_field,printing_div) {
	//document.getElementById(printing_div).innerHTML = document.getElementById(textarea_field).value;
	document.getElementById(printing_div).innerHTML = document.getElementById(textarea_field).value;
	window.focus();
	window.print();	
}

function marcaReporteAuditoria(){	
	if(confirm("¿Desea guardar este reporte de auditoria?")){
		document.getElementById('daliForm:reporteAuditoria').value="true";
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

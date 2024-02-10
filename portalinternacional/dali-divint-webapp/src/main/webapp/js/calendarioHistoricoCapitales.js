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



//Muestra bitacora Mensaje
function detalleDerecho(idDerecho){
    var url = contextPath+'/calendarioCapitalesDistribucion/bitacoraCalendarioCapitalesHistorica.faces';
    url += '?idCalendario=' + idDerecho;
    mostrarDetalle('Derecho',url);
}

function seleccionBoveda(idDerecho){
    var url = contextPath+'/ejercicioDerechosInt/bovedaCalendarioEmisionesDeudaExt.faces';
    url += '?idCalendario=' + idDerecho;  
    mostrarDetalle('Derecho',url);
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

function agregar(id , skip, hiddenField) {
	var tipo=id.replace("chk_","");
	tipo=tipo.replace(/_[0-9]*/,"");
	var num=id.replace("chk_"+tipo+"_","");	
	var ids=document.getElementById(hiddenField).value;
	
	if ($j("#"+id).is(':checked') || skip){
		if(skip){
			$j("#"+id).attr('checked', true);
		}
		if(ids.search(num) == -1 ){
			ids=ids+","+num;
		}						
	}else if (!($j("#"+id).is(':checked')) ) {	
		ids=ids.replace(","+num,"");		
	}	
	quitarTodos(id)
	document.getElementById(hiddenField).value=ids;	
}

function actualizaMensaje(idmensaje){
	 var areaMensaje=document.getElementById('formaBitacoraCalendario:swiftMensaje');	 
	 var swift = $j("input[id*="+idmensaje+"]").get();
	 areaMensaje.value=swift[0].value;
	 window.scrollTo(0,0);
}

function marcar(id){	
	$j(".renglonTablaUno").css("background","white");
	$j(".renglonTablaDos").css("background","LightGrey ");	
	$j("."+id).parents("tr:eq(0)").css("background","yellow");
}

function lockBotones(tipoBoton){
	var botones = $j("input[id*="+tipoBoton+"]").get();
	for(var i=0; i<botones.length; i++){
		botones[i].disabled=true;
	}	
}

function unlockBotones(tipoBoton){
	var botones = $j("input[id*="+tipoBoton+"]").get();
	for(var i=0; i<botones.length; i++){
		botones[i].disabled=false;
	}
}

function cerrarVentanaAux(){	
	parent.tb_remove();	
	parent.$j("input[id$='botonBuscar']").click();
}


/**funcion que muestra un  pop up , para las narrativas
 * @param idCalendario
 */
function verNarrativasCapitales(idCalendario,idHistorico){
  
	var url = contextPath+'/calendarioCapitalesDistribucion/detalleNarrativasCapitales.faces';    
    url += '?idCalendario=' + idCalendario +'&idHistorico=' + idHistorico + '&origen=false';        
    $j('#detalle').dialog( "option", "title", "Narrativas");   
    $j('#detalle').dialog('open');
	$j('#detalle').attr('src',url);

	$j('#detalle').dialog({
		width:950,
		   close: function(event, ui) {		 	  
		   }
	});
	$j('.ui-widget-overlay') .on("click", function() {							
		$j('#detalle').dialog('close');	    		
	});
	$j('#detalle').css('width','99%');
	$j('#detalle').css('height','600px');	
}

///MANDAR MENSAJE

function getCambiosDestinatario(){
	var valores="";
	$j("select[id^=slc]").each(function(){
		valores= valores+$j(this).attr("id") +":"+$j(this).val()+"|";
	});
	$j("input[id*=envio_mensaje_H]").val(valores);
	var valor=$j("input[id*=envio_mensaje_H]").val();
	valor+"1";
}

function confirmaEjecutar(){ 
	var ids="";
	var count=0;
	$j("select[id*=slc]").each(function(){
		var id =""+$j(this).attr("id")
		var destino = $j(this).val();
		if( id != 'slc_none' && destino != '-1'){		
			var fin = id.indexOf("cal_");			
			if(count == 0){				
				ids+=" "+id.substring(4,fin)+" - "+destino;			
			}else{
				ids+=", "+id.substring(4,fin)+" - "+destino;
			}			
			count++;
			if(count % 10 == 0){
				ids+="\n";
			}
		}
	});
	if(count >0){
		if(count == 1){
			return confirm("El siguiente mensaje ser\u00E1 enviado para su autorizaci\u00F3n "+"\u00bfEst\u00E1 de acuerdo?\n"+"Datos Env\u00EDo (Folio - Destino): "+ids);
		}else{
			return confirm("Los siguientes "+count+" mensajes ser\u00E1n enviados para su autorizaci\u00F3n: "+"\u00bfEst\u00E1 de acuerdo?\n"+"Datos Env\u00EDo (Folio - Destino): "+ids);
		}
		
	}
}

function limpiarTodos(){
	
	$j("input[id*=envio_mensaje_H]").val("");
}

//lo utiliza el comboprincipal para cambiar los demas combos
function cambiarComboAll(elem){        
	var combos = $j("select[id*=slc]").each(function(){
		var test = $j(this).children('option[value="'+elem.value+'"]');
		if(test != null && test != undefined && test.val() == elem.value){
			this.value=elem.value;
			$j(this).change();
		}
	});		 
}

function ponNombreId(ele,id,idCal){
	$j(ele).attr('id','slc_'+id+'cal_'+idCal);	
}

/**
 * Muestra los envios pendientes de autorizar
 */
function muestraAutorizacion(){
	var url = contextPath+'/calendarioCapitalesDistribucion/autorizacionesCalendarioCapitalesPopUp.faces';
    mostrarDetalle('Autorizaciones',url);
}

/**funcion que muestra pop up , para la consulta de los envios
 * @param idHistorico, bandera = false
 */
function detalleEnvios(folio){
    var url = contextPath+'/calendarioCapitalesDistribucion/envioMensajeCalendarioCapitalesPopUp.faces';
    url += '?folio=' + folio + '&bandera=false';
    mostrarDetalle('Env\u00EDos',url);
}

function verInstrucciones(idHistorico,idBitacora){
	var url = contextPath+'/calendarioCapitalesDistribucion/detalleInstrucciones.faces';    
    url += '?idHistorico=' + idHistorico +
    '&idBitacora=' + idBitacora;
    $j('#detalle').dialog( "option", "title", "Instrucciones");   
    $j('#detalle').dialog('open');
	$j('#detalle').attr('src',url);

	$j('#detalle').dialog({
		width:950,
		   close: function(event, ui) {		 	  
		   }
	});
	$j('.ui-widget-overlay') .on("click", function() {							
		$j('#detalle').dialog('close');	    		
	});
	$j('#detalle').css('width','99%');
	$j('#detalle').css('height','400px');	
}

function buscaIsin(){
	$j("input[id$='buscarIsin']").click();
}

function consultaGeneral(){	
	var texto=document.getElementById('instruccionesForm:indicadorCambio');
	if(texto.value === 'true'){
		parent.$j("input[id$='botonBuscarInstruccion']").click();
	}				
}

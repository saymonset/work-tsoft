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
	$j('#detallepopup').dialog(
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
function limpiar(control){		
	var idCtrl=control.replace("chk_","");
	idCtrl=idCtrl.replace(/_[0-9]*/,"");
	var hiddenField=""
	
	if(idCtrl == "registra"){
		hiddenField="calIdDerechoRegistrar";
	}else if(idCtrl == "autoriza"){
		hiddenField="calIdDerechoAutorizar";
	}else if(idCtrl == "previo"){
		hiddenField="calIdDerechoPrevio";
	}else if(idCtrl == "confirma"){
		hiddenField="calIdDerechoConfirmar";
	}else if(idCtrl == "preliquida"){
		hiddenField="calIdDerechoPreliquidar";
	}else if(idCtrl == "liquida"){
		hiddenField="calIdDerechoLiquidar";
	}else if(idCtrl == "corrige"){
		hiddenField="calIdDerechoCorregir";
	}else if(idCtrl == "suspende"){
		hiddenField="calIdDerechoSuspender";
	}else if(idCtrl == "cancela"){
		hiddenField="calIdDerechoCancelar";
	}else{
		hiddenField="";
	}
	if(hiddenField != ""){
		document.getElementById('daliForm:'+hiddenField).value="";		
	}	
}


function quitarMarca(control){		
	var idCtrl=control.replace("chk_","");
	idCtrl=idCtrl.replace(/_[0-9]*/,"");
	var hiddenField=""
	
	if(idCtrl == "registra"){
		hiddenField="calIdDerechoRegistrar";
	}else if(idCtrl == "autoriza"){
		hiddenField="calIdDerechoAutorizar";
	}else if(idCtrl == "previo"){
		hiddenField="calIdDerechoPrevio";
	}else if(idCtrl == "confirma"){
		hiddenField="calIdDerechoConfirmar";
	}else if(idCtrl == "preliquida"){
		hiddenField="calIdDerechoPreliquidar";
	}else if(idCtrl == "liquida"){
		hiddenField="calIdDerechoLiquidar";
	}else if(idCtrl == "corrige"){
		hiddenField="calIdDerechoCorregir";
	}else if(idCtrl == "suspende"){
		hiddenField="calIdDerechoSuspender";
	}else if(idCtrl == "cancela"){
		hiddenField="calIdDerechoCancelar";
	}else{
		hiddenField="";
	}
	if(hiddenField != ""){
		var ids=document.getElementById('daliForm:'+hiddenField).value;
		var idOper=control.replace("chk_"+idCtrl+"_","");
		
		if(ids.search(idOper)  >= 0 ){
			ids=ids.replace(","+idOper,"");
			document.getElementById('daliForm:'+hiddenField).value=ids;
		}						
		$j("input[id="+control+"]").attr('checked',false);
	}	
}

function quitarTodos(id){
	var tipo=id.replace("chk_","");
	tipo=tipo.replace(/_[0-9]*/,"");
	var num=id.replace("chk_"+tipo+"_","");
	
	if(tipo != "registra"){
		quitarMarca("chk_registra_"+num);
	}
	if(tipo != "autoriza"){
		quitarMarca("chk_autoriza_"+num);
	}
	if(tipo != "previo"){
		quitarMarca("chk_previo_"+num);
	}
	if(tipo != "confirma"){
		quitarMarca("chk_confirma_"+num);
	}
	if(tipo != "preliquida"){
		quitarMarca("chk_preliquida_"+num);
	}
	if(tipo != "liquida"){
		quitarMarca("chk_liquida_"+num);
	}
	if(tipo != "suspende"){
		quitarMarca("chk_suspende_"+num);
	}
	if(tipo != "cancela"){
		quitarMarca("chk_cancela_"+num);
	}
}


function buscarRegistros() {
}

function presentarResultados() {
}

/**funcion que muestra un  pop up , para la consulta de la bitacora
 * @param idCalendario
 */
function detalleDerecho(idDerecho){
    var url = contextPath+'/ejercicioDerechosInt/bitacoraCalendarioEmisionesDeudaExt.faces';
    url += '?idCalendario=' + idDerecho;
    mostrarDetalle('Derecho',url);
}

/**funcion que muestra un  pop up , para las narrativas
 * @param idCalendario
 */
function verNarrativasCapitales(idCalendario){
	  
	var url = contextPath+'/calendarioCapitalesDistribucion/detalleNarrativasCapitales.faces';    
    url += '?idCalendario=' + idCalendario + '&origen=true';    
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
	$j('#detalle').css('height','400px');	
}

function seleccionBoveda(idDerecho){
    var url = contextPath+'/ejercicioDerechosInt/bovedaCalendarioEmisionesDeudaExt.faces';
    url += '?idCalendario=' + idDerecho;
    mostrarDetalle('Derecho',url);
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
	//$j("#divResumen").animate({opacity:1}, 1000);
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

function agregaRegistrar(id , skip) {	
	agregar(id,skip,'daliForm:calIdDerechoRegistrar');	
}
function agregaAutorizar(id , skip) {	
	agregar(id,skip,'daliForm:calIdDerechoAutorizar');	
}
function agregaPrevio(id , skip) {	
	agregar(id,skip,'daliForm:calIdDerechoPrevio');	
}
function agregaConfirma(id , skip) {	
	agregar(id,skip,'daliForm:calIdDerechoConfirmar');	
}
function agregaPreliquida(id , skip) {	
	agregar(id,skip,'daliForm:calIdDerechoPreliquidar');		
}
function agregaLiquida(id , skip) {	
	agregar(id,skip,'daliForm:calIdDerechoLiquidar');	
}
function agregaCorregir(id) {		
	var hiddenField = 'daliForm:calIdDerechoCorregir';	
	document.getElementById(hiddenField).value=id;
}
function agregaSuspende(id , skip) {		
	agregar(id,skip,'daliForm:calIdDerechoSuspender');	
}
function agregaCancela(id , skip) { 
	agregar(id,skip,'daliForm:calIdDerechoCancelar');	
}

function todos(idcont,hiddenField){
	var tipo=idcont.replace("chk_","");
	tipo=tipo.replace("_todos","");
	if ($j("#"+idcont).is(':checked') ){	
		var todos=$j("input[id^=chk_"+tipo+"][id!=chk_"+tipo+"_todos]").get();
		for(var i=0; i<todos.length; i++){
			var nom=todos[i].id;
			quitarTodos(nom);
			agregar(nom,true,hiddenField);			
		}						
	}else if (!($j("#"+idcont).is(':checked')) ) {
		document.getElementById(hiddenField).value="";		
		var todos=$j("input[id^=chk_"+tipo+"][id!=chk_"+tipo+"_todos]").get();
		for(var i=0; i<todos.length; i++){
			todos[i].checked=false;
		}					
	}		
}

function registrarTodos(campo){		
	todos(campo.id,'daliForm:calIdDerechoRegistrar');
	//campo.checked=false;
}

function autorizarTodos(campo){		
	todos(campo.id,'daliForm:calIdDerechoAutorizar');
	//campo.checked=false;
}

function previoTodos(campo){		
	todos(campo.id,'daliForm:calIdDerechoPrevio');
	//campo.checked=false;
}

function confirmaTodos(campo){
	todos(campo.id,'daliForm:calIdDerechoConfirmar');
	//campo.checked=false;
}

function preliquidaTodos(campo){
	todos(campo.id,'daliForm:calIdDerechoPreliquidar');
	//campo.checked=false;
}
function liquidaTodos(campo){
	todos(campo.id,'daliForm:calIdDerechoLiquidar');
	//campo.checked=false;
}
function suspendeTodos(campo){
	todos(campo.id,'daliForm:calIdDerechoSuspender');
	//campo.checked=false;
}
function cancelaTodos(campo){
	todos(campo.id,'daliForm:calIdDerechoCancelar');
	//campo.checked=false;
}
function refresco(){
	document.getElementById('daliForm:botonBuscar').click();
	alert("Actualizado");
	
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

function validarInstruirMensaje(){
	//var boveda = $j(".selectBoveda").value
	var check = document.getElementById('formaBovedaCalendario:radioValue').value	
	return confirm("se va a realizar en automatico = "+check)	
}

function escondeColumnas(){	
       // $('td:nth-child(2)').hide();
        // if your table has header(th), use this
        $j('td:nth-child(22),th:nth-child(22)').hide();
        $j('td:nth-child(23),th:nth-child(23)').hide();
   
}
function validarAccion(accion){
	var hiddenField="adm"
	var registros=0
	if(accion == "registrar"){
		hiddenField="calIdDerechoRegistrar";
	}else if(accion == "autorizar"){
		hiddenField="calIdDerechoAutorizar";
	}else if(accion == "previo"){
		hiddenField="calIdDerechoPrevio";
	}else if(accion == "confirmar"){
		hiddenField="calIdDerechoConfirmar";
	}else if(accion == "preliquidar"){
		hiddenField="calIdDerechoPreliquidar";
	}else if(accion == "liquidar"){
		hiddenField="calIdDerechoLiquidar";
	}else if(accion == "corregir"){
		hiddenField="calIdDerechoCorregir";
	}else if(accion == "suspender"){
		hiddenField="calIdDerechoSuspender";
	}else if(accion == "cancelar"){
		hiddenField="calIdDerechoCancelar";
	}else{
		hiddenField="";
	}
	if(hiddenField != ""){
		var elementos=document.getElementById('daliForm:'+hiddenField).value;
		registros =elementos.split(",")
	}
	return confirm("Se van a "+accion+" "+(registros.length - 1)+" derechos.\n \u00bfEst\u00E1 usted seguro de querer realizar esta acci\u00F3n?")
}

/**funcion que muestra un  pop up , para la consulta del historico
 * @param idCalendario
 */
function detalleHistorico(folio){
    var url = contextPath+'/calendarioCapitalesDistribucion/consultaHistoricoCalendarioCapitalesPopUp.faces';
    url += '?folio=' + folio;
    mostrarDetalle('Hist\u00F3rico',url);
}
/**funcion que muestra un  pop up , para la consulta de los envios
 * @param idCalendario
 */
function detalleEnvios(folio, bandera){
    var url = contextPath+'/calendarioCapitalesDistribucion/envioMensajeCalendarioCapitalesPopUp.faces';
    url += '?folio=' + folio
    + '&bandera=' + bandera;
    mostrarDetalle('Env\u00EDos',url);
}

/**funcion que muestra un  pop up , para las autorizaciones
 * 
 */
function autorizacion(){
	var url = contextPath+'/calendarioCapitalesDistribucion/autorizacionesCalendarioCapitalesPopUp.faces';
    + '&idMuestraConsulta=false';
    mostrarDetalle('Autorizaciones',url);
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
	
function getCambiosDestinatario() {
		var valores = "";
		$j("select[id^=slc]").each(
				function() {
					valores = valores + $j(this).attr("id") + ":"
							+ $j(this).val() + "|";
				});
		$j("input[id*=envio_mensaje_C]").val(valores);
		var valor = $j("input[id*=envio_mensaje_C]").val();
		valor + "1";
	}

function confirmaEjecutar(){ 
	var ids="";
	var count=0;
	$j("select[id*=slc]").each(function(){
		var id =""+$j(this).attr("id")
		var destino = $j(this).val();
		if( id != 'slc_none' && destino != '-1'){					
			if(count == 0){				
				ids+=" "+id.substring(4)+" - "+destino;			
			}else{
				ids+=", "+id.substring(4)+" - "+destino;
			}			
			count++;
			if(count % 10 == 0){
				ids+="\n";
			}
		}
	});
	if(count >0){
		if(count == 1){
			return confirm("El siguiente mensaje ser\u00E1 enviado para su autorizaci\u00F3n "+"\u00bfEst\u00E1 de acuerdo?\n"+"Datos Env\u00EDo (Folio Indeval - Destino): "+ids);
		}else{
			return confirm("Los siguientes "+count+" mensajes ser\u00E1n enviados para su autorizaci\u00F3n: "+"\u00bfEst\u00E1 de acuerdo?\n"+"Datos Env\u00EDo (Folio Indeval - Destino): "+ids);
		}
		
	}
}

	

	// lo utiliza el combo principal para cambiar los demas combos
	function cambiarComboAll(elem) {
		var combos = $j("select[id*=slc]").each(function() {
			var test = $j(this).children('option[value="' + elem.value + '"]');
			if (test != null && test != undefined && test.val() == elem.value) {
				this.value = elem.value;
				$j(this).change();
			}
		});
	}

	function ponNombreId(ele,id){
		$j(ele).attr('id','slc_'+id);	
	}
	
	function limpiarTodos() {
		$j("input[id*=envio_mensaje_C]").val("");
	}
	
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Muestra bitacora Mensaje
	function detalleDerecho_popup(idDerecho){	    
	    var url = contextPath+'/calendarioCapitalesDistribucion/bitacoraCalendarioCapitalesHistorica.faces';
	    url += '?idCalendario=' + idDerecho;
	    $j('#detallepopup').dialog( "option", "title", "Derecho");  
	    $j('#detallepopup').dialog('open');
		$j('#detallepopup').attr('src',url);
		$j('#detallepopup').dialog({
			width:870,
			height:600,
			   close: function(event, ui) {	
				   
			   }
		});
		$j('.ui-widget-overlay') .on("click", function() {							
			$j('#detallepopup').dialog('close');	    
			
		});
		
		$j('#detallepopup').css('width','99%');
		$j('#detallepopup').css('height','400px');
	}

	/**funcion que muestra un  pop up , para las narrativas
	 * @param idCalendario
	 */
	function verNarrativasCapitales_popup(idCalendario,idHistorico){		
		var url = contextPath+'/calendarioCapitalesDistribucion/detalleNarrativasCapitales.faces';    
		url += '?idCalendario=' + idCalendario +'&idHistorico=' + idHistorico + '&origen=false';  
	    $j('#detallepopup').dialog( "option", "title", "Narrativas");  
	    $j('#detallepopup').dialog('open');
		$j('#detallepopup').attr('src',url);
		$j('#detallepopup').dialog({
			width:950,
			height:600,
			   close: function(event, ui) {	
				   
			   }
		});
		$j('.ui-widget-overlay') .on("click", function() {							
			$j('#detallepopup').dialog('close');	    
			
		});
		
		$j('#detallepopup').css('width','99%');
		$j('#detallepopup').css('height','600px');
	}
	
	function ponNombreId_popup(ele,id,idCal){
		$j(ele).attr('id','slc_'+id+'cal_'+idCal);	
	}
	
	//lo utiliza el comboprincipal para cambiar los demas combos
	function cambiarComboAll_popup(elem){        
		var combos = $j("select[id*=slc]").each(function(){
			var test = $j(this).children('option[value="'+elem.value+'"]');
			if(test != null && test != undefined && test.val() == elem.value){
				this.value=elem.value;
				$j(this).change();
			}
		});		 
	}
	
	function confirmaEjecutar_popup(){ 
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
				return confirm("El siguiente mensaje ser\u00E1 enviado para su autorizaci\u00F3n "+"\u00bfEst\u00E1 de acuerdo?\n"+"Datos Env\u00EDo (Folio Indeval - Destino): "+ids);
			}else{
				return confirm("Los siguientes "+count+" mensajes ser\u00E1n enviados para su autorizaci\u00F3n: "+"\u00bfEst\u00E1 de acuerdo?\n"+"Datos Env\u00EDo (Folio Indeval - Destino): "+ids);
			}
			
		}
	}
	
	function limpiarTodos_popup() {

		$j("input[id*=envio_mensaje_H]").val("");
	}
	
	function getCambiosDestinatario_popup() {
		var valores = "";
		$j("select[id^=slc]").each(
				function() {
					valores = valores + $j(this).attr("id") + ":"
							+ $j(this).val() + "|";
				});
		$j("input[id*=envio_mensaje_H]").val(valores);
		var valor = $j("input[id*=envio_mensaje_H]").val();
		valor + "1";
	}
	
	/**funcion que muestra pop up , para la consulta de los envios
	 * @param idHistorico, bandera = false
	 */
function detalleEnviosPopup(folio){
	    var url = contextPath+'/calendarioCapitalesDistribucion/envioMensajeCalendarioCapitalesPopUp.faces';
	    url += '?folio=' + folio + '&bandera=false';
	    $j('#detallepopup').dialog( "option", "title", "Env\u00EDos");  
	    $j('#detallepopup').dialog('open');
		$j('#detallepopup').attr('src',url);
		$j('#detallepopup').dialog({
			width:870,
			height:600,
			   close: function(event, ui) {	
				   
			   }
		});
		$j('.ui-widget-overlay') .on("click", function() {							
			$j('#detallepopup').dialog('close');	    
			
		});
		
		$j('#detallepopup').css('width','99%');
		$j('#detallepopup').css('height','400px');
}

function verInstruccionesPopup(idHistorico,idBitacora){
	var url = contextPath+'/calendarioCapitalesDistribucion/detalleInstrucciones.faces';    
    url += '?idHistorico=' + idHistorico +
    '&idBitacora=' + idBitacora;        
    $j('#detallepopup').dialog( "option", "title", "Instrucciones");   
    $j('#detallepopup').dialog('open');
	$j('#detallepopup').attr('src',url);

	$j('#detallepopup').dialog({
		width:950,
		   close: function(event, ui) {		 	  
		   }
	});
	$j('.ui-widget-overlay') .on("click", function() {							
		$j('#detallepopup').dialog('close');	    		
	});
	$j('#detallepopup').css('width','99%');
	$j('#detallepopup').css('height','400px');	
}

/**funcion que muestra las narrativas de usuario externo
 * @param idCalendario
 */
function verNarrativasCapitalesExt(idCalendario){		
	var url = contextPath+'/calendarioCapitalesDistribucion/detalleNarrativasConsultaExt.faces';    
	url += '?idCalendario=' + idCalendario;  
    $j('#detalle').dialog( "option", "title", "Narrativas");  
    $j('#detalle').dialog('open');
	$j('#detalle').attr('src',url);
	$j('#detalle').dialog({
		width:950,
		height:600,
		   close: function(event, ui) {	
			   
		   }
	});
	$j('.ui-widget-overlay') .on("click", function() {							
		$j('#detalle').dialog('close');	    
		
	});
	
	$j('#detalle').css('width','99%');
	$j('#detalle').css('height','600px');
}
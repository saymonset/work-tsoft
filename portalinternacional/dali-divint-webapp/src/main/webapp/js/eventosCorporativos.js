$j(document).on('ready',function(){
	$j('#detalle').dialog(
			{ 
				autoOpen:false,
				height:670,
				maxheight:1200,
				maxWidth:1200,
				modal: true,
				resizable: false,
				width:950,
				zIndex:10003
			 }
		);
	$j('#bitacora').dialog(
			{ 
				autoOpen:false,
				height:670,
				maxheight:1200,
				maxWidth:1200,
				modal: true,
				resizable: false,
				width:950,
				zIndex:10003
			 }
		);

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

function limpiar(control){		
	$j("input[id*=cambiosEstado_H]").val("");
}
function limpiarTodos(){
	
	$j("input[id*=cambiosEstado_H]").val("");
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

function detalleDerecho(idDerecho){
    var url = contextPath+'/ejercicioDerechosInt/bitacoraCalendarioEmisionesDeudaExt.faces';
    url += '?idCalendario=' + idDerecho   
    + '&idMuestraConsulta=false'
    + '&TB_iframe=true'
    +'&height=550&width=925';
    mostrarDetalle('Derecho',url);
}

function seleccionBoveda(idDerecho){
    var url = contextPath+'/ejercicioDerechosInt/bovedaCalendarioEmisionesDeudaExt.faces';
    url += '?idCalendario=' + idDerecho   
    + '&idMuestraConsulta=false'
    + '&TB_iframe=true'
    +'&height=400&width=600';
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
	var hiddenField=""
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
	return confirm("Se van a "+accion+" "+(registros.length - 1)+" derechos.\n ¿Está usted seguro de querer realizar esta acción?")
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

function cambia(elem){
var ap = "|"+$j(elem).attr('id')+":"+elem.value;		
$j("input[id=texto]").val($j("input[id=texto]").val()+ap);
}

function getCambiosEstado(){
	var valores="";
	$j("select[id^=slc]").each(function(){
		valores= valores+$j(this).attr("id") +":"+$j(this).val()+"|";
	});
	$j("input[id*=cambiosEstado_H]").val(valores);
	var valor=$j("input[id*=cambiosEstado_H]").val();
	valor+"1";
}

function ponNombreId(ele,id){
	$j(ele).attr('id','slc_'+id);	
}

function confirmaEjecutar(){ 
	var ids="";
	var count=0;
	$j("select[id*=slc]").each(function(){
		var id =""+$j(this).attr("id")
		if( id != 'slc_none' && $j(this).val() != '-1'){		
			ids+=" "+id.substring(4)+",";
			count++;
			if(count % 10 == 0){
				ids+="\n";
			}
		}
	});
	if(count >0){
		return confirm("Los siguientes "+count+" folio(s) van a cambiar de estado "+"¿Está de acuerdo?\n"+ids);
	}
}
/**funcion que manda a imprimir  el html.
 * @param div_field contenedor principal de loo que se desea imprimir
 * @param printing_div contenedor de impresion
 */
function printBitacoraContent(div_field,printing_div)
{
	//console.log("ID: "+ div_field +" Imp:" +printing_div);
	//document.getElementById(printing_div).innerHTML = document.getElementById(textarea_field).value;
	document.getElementById(printing_div).innerHTML = document.getElementById(div_field).value;
	window.focus();
	window.print();	
}
/**funcion que muestra un  pop up , para mostrar Evento Corporativo
 * @param idEvento indica el evento a consultar
 */
function verParticipanteBitacora(idEvento, isIndeval){
  
	var url = contextPath+'/eventosCorporativos/bitacora/bitacoraEventoCorporativo.faces';    
    url += '?idEventoCorporativo=' + idEvento;
    url += '&isIndeval=' + isIndeval;
    $j('#detalle').dialog( "option", "title", "Vista Participante");   
    $j('#detalle').dialog('open');
	$j('#detalle').attr('src',url);

	$j('#detalle').dialog({
		width:950,
		   close: function(event, ui) {
		  // $j("input[id$='control_bitacora_actual']").click();	   
		   }
	});
	$j('.ui-widget-overlay') .on("click", function() {							
		$j('#detalle').dialog('close');	    		
	});
	$j('#detalle').css('width','99%');
	$j('#detalle').css('height','400px');
	
}

function exportarAWordCont()
{		
	$j("div[id*=contenedorGeneralBitacoraEvcoDiv]").wordExport();
}
function exportarAWord()
{		
	$j("div[id*=contenedorGeneralBitacoraEvco2]").wordExport();
}


/**funcion que muestra un  pop up , para mostrar pantalla Control de Cambios
 * @param idEvento indica el evento a consultar
 */
function verControlDeCambios(idEvento){
   console.log("idEvento:"+ idEvento);
	var url = contextPath+'/eventosCorporativos/bitacora/controlCambiosEventoCorporativo.faces';    
    url += '?idEventoCorporativo=' + idEvento;
    $j('#detalle').dialog( "option", "title", "Control de Cambios de Evento Corporativo");   
    $j('#detalle').dialog('open');
	$j('#detalle').attr('src',url);

	$j('#detalle').dialog({
		height:670,
		width:1000,
		   close: function(event, ui) {	   
		   }
	});

	$j('#detalle').css('width','99%');	
}



/**funcion que valida los checkbos para  construir cadena con ids de bitacora para cambiar estaus  
 * @param checado estatus de checkbox
 * @param id indica el idCambioBitacora
 */
function seleccionarCheckBitacora(checado,id)
{

	var ids = [];
	var idAux='';
	if(checado == true)
	{
	    $j('input[id*=idsCambioEstatus]').val($j('input[id*=idsCambioEstatus]').val()+id+",");
	    
	    ids= $j('input[id*=idsCambiosEstatusFalse]').val().split(",");
		  for(var i=0;i < ids.length-1;i++)
		  {
			if(ids[i] != id)
			{
				idAux=idAux+ids[i]+",";	
			}
		  }
		    $j('input[id*=idsCambiosEstatusFalse]').val(idAux); 
	}
	else
	{
	    $j('input[id*=idsCambiosEstatusFalse]').val($j('input[id*=idsCambiosEstatusFalse]').val()+id+",");
		
	    ids= $j('input[id*=idsCambioEstatus]').val().split(",");
		  for(var i=0;i < ids.length-1;i++)
		  {
			if(ids[i] != id)
			{
				idAux=idAux+ids[i]+",";	
			}
		  }
		    $j('input[id*=idsCambioEstatus]').val(idAux);     
	}
}



/**funcion que muestra un  pop up , para mostrar un registro Evco
 * @param idEvento indica el evento a consultar
 */
function verEventoCorporativoOriginal(idBitacoraCambio){

	var url = contextPath+'/eventosCorporativos/bitacora/controlCambiosEvcoOriginal.faces';    
    url += '?idBitacoraCambio=' + idBitacoraCambio;
    $j('#bitacora').dialog( "option", "title", "Control de Cambios de Evento Corporativo");   
    $j('#bitacora').dialog('open');
	$j('#bitacora').attr('src',url);
	$j('#bitacora').dialog({
		width:870,
		height:600,
		   close: function(event, ui) {	
			   
		   }
	});
	$j('.ui-widget-overlay') .on("click", function() {							
		$j('#bitacora').dialog('close');	    
		
	});
	
	$j('#bitacora').css('width','99%');
	$j('#bitacora').css('height','400px');
	
}

function confirmarCambioEstatus()
{			
	return confirm("Los registros habilitados  seran ignorados al generar   el Aviso Corporativo "+"¿Está de acuerdo?\n ");	
}

function ponNombreIdCheckBitacora(ele,id){
	$j(ele).attr('id','check'+id);	
}




/*
 * Funciones para visualizar el resumen del evco
 * 
 * */

function verResumen(idEvento, isIndeval)
{
	var url = contextPath+'/eventosCorporativos/resumenEventoCorporativo.faces';    
    url += '?idEventoCorporativo=' + idEvento;
    url += '&isIndeval=' + isIndeval;
    $j('#detalle').dialog( "option", "title", "Resumen Evento Corporativo");   
    $j('#detalle').dialog('open');
	$j('#detalle').attr('src',url);

	$j('#detalle').dialog({
		width:950,
		   close: function(event, ui) {
			   //$(this).dialog("destroy");
		   }
	});
	$j('.ui-widget-overlay') .on("click", function() {							
		$j('#detalle').dialog('close');	    		
	});
	$j('#detalle').css('width','99%');
	$j('#detalle').css('height','400px');

}





function mostrarDetalle(titulo,url,height,width){
	$j('#noScrolling').dialog('open');
	$j('#noScrolling').dialog('option','title',titulo);
	$j('#noScrolling').dialog('option','height',height);
	$j('#noScrolling').dialog('option','width',width);
	$j('#noScrolling').dialog('option','position','top');
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


function editaCuenta(idTipoDerecho){
    var url = contextPath+'/eventosCorporativos/catalogos/editaTipoDerecho.faces';
    url += '?idTipoDerecho=' + idTipoDerecho;
    
    var titulo = (idTipoDerecho == 0) ? 'Nuevo Tipo Derecho' : 'Editar Tipo Derecho';
    mostrarDetalle(titulo, url, 300, 900);
}

function cierraDialog(){
	parent.$j('#noScrolling').dialog('close');
}

function cierraDialogValidado(){
	var cadena = $j("input[id$='validaDialog']").val();
	if(cadena.trim() == "true"){	
		//cierro el dialogo
		parent.$j('#noScrolling').dialog('close');	
	}
}

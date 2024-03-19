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
function limpiarTodos(){
	
	document.getElementById('daliForm:calIdDerechoRegistrar').value="";
	document.getElementById('daliForm:calIdDerechoAutorizar').value="";
	document.getElementById('daliForm:calIdDerechoPrevio').value="";
	document.getElementById('daliForm:calIdDerechoConfirmar').value="";
	document.getElementById('daliForm:calIdDerechoPreliquidar').value="";
	document.getElementById('daliForm:calIdDerechoLiquidar').value="";
	document.getElementById('daliForm:calIdDerechoCorregir').value="";
	document.getElementById('daliForm:calIdDerechoSuspender').value="";
	document.getElementById('daliForm:calIdDerechoCancelar').value="";
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
function validarAccionLiquidar(accion, montoConfirmado , citiBank, euroclearAndFechaPagoValor, puedePagar, hasM567, euroclear, custodioId ){

	console.log('custodioId: '+custodioId);
	console.log('montoConfirmado: '+montoConfirmado);
	console.log('citiBank: '+ citiBank);
	console.log('euroclearAndFechaPagoValor: '+ euroclearAndFechaPagoValor);
	console.log('puedePagar: '+puedePagar);
	console.log('hasM567: '+hasM567);
	console.log('euroclear: '+euroclear);
	if (citiBank){
		console.log('-------------1--------------------');
		alert('Es Citi Bankd, debp salir!');
		console.log('-------------1A--------------------');
		return false;
	}else if (euroclear){
		console.log('-------------2--------------------');
		alert('Es EUROCLEAR debp salir!');
		console.log('-------------2B--------------------');
		return false;
	}else{
		console.log('-------------3--------------------');
		console.log('-------------3C--------------------');
		return validarAccion(accion);

	}
	console.log('-------------4--------------------');
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
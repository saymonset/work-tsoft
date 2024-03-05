$j(document).on('ready',function(){
	$j('#posiciones').dialog(
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

$j(document).on('ready',function(){
	$j('#detalleEdoCta').dialog(
			{ 
				autoOpen:false,
				height:160,
				maxWidth:1200,
				modal: true,
				resizable: false,
				width:950,
				zIndex:10003
			 }
		);
});


function habilitaPorDivisa(divisa) {
	if(divisa=="1"){
		$j("input[id$='aliasCuenta']").prop('readonly',true);
		$j("input[id$='aliasCuenta']").prop('value','');
		$j("input[id$='cuentaIntermediario']").prop('readonly',true);
		$j("input[id$='cuentaIntermediario']").prop('value','');
		$j("input[id$='bancoIntermediario']").prop('readonly',true);
		$j("input[id$='bancoIntermediario']").prop('value','');
		$j("input[id$='nombreBancoIntermediario']").prop('readonly',true);
		$j("input[id$='nombreBancoIntermediario']").prop('value','');
		$j("input[id$='detallesPago']").prop('readonly',true);
		$j("input[id$='detallesPago']").prop('value','');
		$j("input[id$='montoMaximoMensual']").prop('readonly',false);
		$j("input[id$='montoMaximoDiario']").prop('readonly',false);
		$j("input[id$='montoMaximoXTran']").prop('readonly',false);
		$j("input[id$='numMaximoMovsXMes']").prop('readonly',false);
	}else{
		$j("input[id$='aliasCuenta']").prop('readonly',false);
		$j("input[id$='cuentaIntermediario']").prop('readonly',false);
		$j("input[id$='bancoIntermediario']").prop('readonly',false);
		$j("input[id$='nombreBancoIntermediario']").prop('readonly',false);
		$j("input[id$='detallesPago']").prop('readonly',false);
		$j("input[id$='montoMaximoMensual']").prop('readonly',true);
		$j("input[id$='montoMaximoMensual']").prop('value','');
		$j("input[id$='montoMaximoDiario']").prop('readonly',true);
		$j("input[id$='montoMaximoDiario']").prop('value','');
		$j("input[id$='montoMaximoXTran']").prop('readonly',true);
		$j("input[id$='montoMaximoXTran']").prop('value','');
		$j("input[id$='numMaximoMovsXMes']").prop('readonly',true);
		$j("input[id$='montoMaximoXTran']").prop('value','');		
	}	
}

function confirmarCrearCuenta(){
	var output="Se Proceder\u00e1 a Efectuar la Creaci\u00F3n de la Cuenta. \u00bfDesea Continuar?";
	if(!confirm(output))
		return false;
	return true;
}

function buscarRegistros(){
	removeAppletEfectivo();
	deshabilitarBoton('botonBuscar');
	deshabilitarBoton('botonEditarCriterios');
	$j("#botonEditarCriterios").css('display','none');	
}


function presentarCriterios(){
	$j("#divCriterios").css('display','inline');
	$j("#divResumen").css('display','none');
	$j("#botonEditarCriterios").css('display','none');				
}

function presentarResumen(){		
	$j("#divCriterios").css('display','none');
	$j("#divResumen").css('display','inline');					
	$j("#divResumen").css('opacity','0');
	$j("#divResumen").animate({
		opacity:1}, 1000);
	$j("#botonEditarCriterios").css('display','inline');
	habilitarBoton('botonBuscar');
	habilitarBoton('botonEditarCriterios');
}


function checkActualizar(){
	checkIds('daliForm:cuentasIdActualizar','chk_act_');	
	document.getElementById('daliForm:cuentasIdLiberar').value="";	
	document.getElementById('daliForm:cuentasIdAprobar').value="";	
	document.getElementById('daliForm:cuentasIdCancelar').value="";	
}

function checkLiberar(){
	checkIds('daliForm:cuentasIdLiberar','chk_lib_');
	document.getElementById('daliForm:cuentasIdActualizar').value="";		
	document.getElementById('daliForm:cuentasIdAprobar').value="";	
	document.getElementById('daliForm:cuentasIdCancelar').value="";
}

function checkCancelar(){
	checkIds('daliForm:cuentasIdCancelar','chk_can_');
	document.getElementById('daliForm:cuentasIdActualizar').value="";	
	document.getElementById('daliForm:cuentasIdLiberar').value="";	
	document.getElementById('daliForm:cuentasIdAprobar').value="";	
}

function checkIds(elems , prefix){
	var confirmar=document.getElementById(elems);	
	var idsConfirmar=confirmar.value;	
	if (idsConfirmar){
		var elems=idsConfirmar.split(',');
		for(var i=0; i< elems.length;i++){
			if(elems[i].length > 1){				
				document.getElementById(prefix+elems[i]).checked=true;
			}
		}	
	}	
}	


function agregaActualizar(id, valor, cve, skip) {
	var idOper=id.replace(cve,"");
	var idsConfirmar;
	
	if(cve == 'chk_act_'){
		idsConfirmar=document.getElementById('daliForm:cuentasIdActualizar').value;
	}else if(cve == 'chk_lib_'){
		idsConfirmar=document.getElementById('daliForm:cuentasIdLiberar').value;
		
	}else if(cve == 'chk_apr_'){
		idsConfirmar=document.getElementById('daliForm:cuentasIdAprobar').value;
	}else if(cve == 'chk_can_'){
		idsConfirmar=document.getElementById('daliForm:cuentasIdCancelar').value;
	}
	
	if ($j("#"+id).is(':checked') || skip){
		if(skip){
			$j("#"+id).prop('checked', true);
		}
		if(idsConfirmar.search(valor) == -1 ){
			idsConfirmar=idsConfirmar+","+valor;
		}					
	}else{		
		idsConfirmar=idsConfirmar.replace(","+valor,"");		
	}
	
	
	if(!($j("#"+id).is(':checked'))){
		if(cve == 'chk_act_'){
			document.getElementById('todas_chk_act_').checked=false;
		}else if(cve == 'chk_lib_'){
			document.getElementById('todas_chk_lib_').checked=false;
		}else if(cve == 'chk_apr_'){
			document.getElementById('todas_chk_apr_').checked=false;
		}else if(cve == 'chk_can_'){
			document.getElementById('todas_chk_can_').checked=false;
		}
	}
	
	if(cve == 'chk_act_'){
		document.getElementById('daliForm:cuentasIdActualizar').value=idsConfirmar;
	}else if(cve == 'chk_lib_'){
		document.getElementById('daliForm:cuentasIdLiberar').value=idsConfirmar;
	}else if(cve == 'chk_apr_'){
		document.getElementById('daliForm:cuentasIdAprobar').value=idsConfirmar;
	}else if(cve == 'chk_can_'){
		document.getElementById('daliForm:cuentasIdCancelar').value=idsConfirmar;
	}
	
}


function marcarTodas(tipo, valor){
	var idsMarcar="";
	var ids="";
	
	idsMarcar=document.getElementsByName(tipo);
	for(var i=0; i<idsMarcar.length; i++){
		idsMarcar[i].checked=valor;
		ids=ids+","+idsMarcar[i].id.replace(tipo+"_","");				
	}
	if(tipo == 'chk_act'){
		ids=document.getElementById('daliForm:cuentasIdActualizar').value=(valor == true?ids:"");
	}else if(tipo == 'chk_lib'){
		ids=document.getElementById('daliForm:cuentasIdLiberar').value=(valor == true?ids:"");
	}else if(tipo == 'chk_apr'){
		ids=document.getElementById('daliForm:cuentasIdAprobar').value=(valor == true?ids:"");
	}else if(tipo == 'chk_can'){
		ids=document.getElementById('daliForm:cuentasIdCancelar').value=(valor == true?ids:"");
	}
}



function agregaIntlAprobar(id , skip) {
	var idOper=id.replace("chk_firmar_","");
	var idsEliminar=document.getElementById('daliForm:intlIdOperacionesFirmar').value;		
	if ($j("#"+id).is(':checked') || skip){
		if(skip){
			$j("#"+id).prop('checked', true);
		}
		if(idsEliminar.search(idOper) == -1 ){
			idsEliminar=idsEliminar+","+idOper;
		}						
	}else{	
		idsEliminar=idsEliminar.replace(","+idOper,"");		
	}	
	document.getElementById('daliForm:intlIdOperacionesFirmar').value=idsEliminar;
}





function removeAppletEfectivo(){
	//esta funcion se agrego para compatibilidad con ie7 y ie8
	//debido a que mandaba una excepcion al hacer encode de este objeto
	   var divApplet=document.getElementById('firmaId');
	   var divItems=null;
	   
	   if(divApplet != null && divApplet.childNodes !=null){
	   		divItems=divApplet.childNodes;	   	      
		   for(var j=0; j < divItems.length ; j++){
			   divApplet.removeChild(divItems[j]);
		   }	
	   }
}

function removeAppletAltaCuenta(){
	   var divApplet=document.getElementById('firmaDivId');
	   var divItems=null;

	   if(divApplet != null && divApplet.childNodes !=null){
	   		divItems=divApplet.childNodes;
		   for(var j=0; j < divItems.length ; j++){
			   divApplet.removeChild(divItems[j]);
		   }
	   }
}


function deshabilitarIntlGuardar(){  
	alert('Se guardo la cuenta');
}

function limpiarSeleccionados(){
	document.getElementById('daliForm:cuentasIdActualizar').value='';
	document.getElementById('daliForm:cuentasIdLiberar').value='';
	document.getElementById('daliForm:cuentasIdAprobar').value='';
	document.getElementById('daliForm:cuentasIdCancelar').value='';
}

function mostrarposiciones(titulo,url){
	$j('#posiciones').dialog('open');
	$j('#posiciones').dialog('option','title',titulo);
	$j('#posiciones').prop('src',url);
	$j('#posiciones').css('width','99%');
	$j('.ui-widget-overlay') .on("click", function() {
	    $j('#posiciones').dialog('close');
	});
}

function mostrarDetallePopUp(titulo,url){
	$j('#detalleEdoCta').dialog('open');
	$j('#detalleEdoCta').dialog('option','title',titulo);
	$j('#detalleEdoCta').prop('src',url);
	$j('#detalleEdoCta').css('width','99%');
	$j('.ui-widget-overlay') .on("click", function() {
	    $j('#detalleEdoCta').dialog('close');
	});
}


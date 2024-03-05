$j(document).on('ready',function(){
	$j('#posiciones').dialog(
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
	// var IdOperacionesLiberarNal=document.getElementById('daliForm:IdOperacionesLiberarNal');
	// IdOperacionesLiberarNal.value="";
	// var IdOperacionesAprobarNal=document.getElementById('daliForm:IdOperacionesAprobarNal');
	// IdOperacionesAprobarNal.value="";
	
	deshabilitarBoton('botonBuscar');
	deshabilitarBoton('botonEditarCriterios');
	$j("#botonEditarCriterios").css('display','none');	
}

function buscarRegistrosIntl(){
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
function agregaCancelar(id , skip) {
	var idOper=id.replace("chk_canc_","");
	var idsEliminar=document.getElementById('daliForm:matchIdOperacionesCancelar').value;		
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
	document.getElementById('daliForm:matchIdOperacionesCancelar').value=idsEliminar;	
}

function agregaConfirmarEfectivo(id, skip) {
	
	var idOper=id.replace("chk_conf_","");	
	var idsConfirmar=document.getElementById('daliForm:matchIdOperacionesConfirmar').value;
	
	 
	
	if ($j("#"+id).is(':checked') || skip){
		if(skip){
			$j("#"+id).prop('checked', true);
		}
		if(idsConfirmar.search(idOper) == -1 ){
			idsConfirmar=idsConfirmar+","+idOper;
		}					
	}else{	
		idsConfirmar=idsConfirmar.replace(","+idOper,"");		
	}	
	document.getElementById('daliForm:matchIdOperacionesConfirmar').value=idsConfirmar;
	
}


function deshabilitarConfirmarEfectivo(){
	var confirmar=document.getElementById('daliForm:matchIdOperacionesConfirmar');
	var idsConfirmar=confirmar.value;	
	confirmar.value="";			
	if (idsConfirmar){
		var elems=idsConfirmar.split(',');		
		for(var i=0; i< elems.length;i++){
			if(elems[i].length >= 1){				
				var divItems=document.getElementById("conf_"+elems[i]).childNodes;																
				for(var j=0; j<divItems.length ; j++){
					  divItems[j].disabled=true;
					  if(divItems[j].src){
					  	divItems[j].src="../images/exclamation.png";
					  }	
				}		
			}	
		}		
	}
	alert('Se confirmaron las Operaciones');
}

function deshabilitarCancelar(){	
	var cancelar=document.getElementById('daliForm:matchIdOperacionesCancelar');
	var idsEliminar=cancelar.value;	
	cancelar.value="";			
	if (idsEliminar){
		var elems=idsEliminar.split(',');		
		for(var i=0; i< elems.length;i++){
			if(elems[i].length >= 1){				
				var divItems=document.getElementById("canc_"+elems[i]).childNodes;																
				for(var j=0; j<divItems.length ; j++){
					  divItems[j].disabled=true;
					  if(divItems[j].src)
					  	divItems[j].src="../images/exclamation.png";			  				  								
				}		
			}	
		}	
	}
	alert('Se cancelaron las Operaciones');
}

function deshabilitarIntlLiberar(){
	var confirmar=document.getElementById('daliForm:intlIdOperacionesLiberar');
	var idsConfirmar=confirmar.value;	
	confirmar.value="";			
	if (idsConfirmar){
		var elems=idsConfirmar.split(',');		
		for(var i=0; i< elems.length;i++){
			if(elems[i].length >= 1){				
				var divItems=document.getElementById("libe_"+elems[i]).childNodes;																
				for(var j=0; j<divItems.length ; j++){
					  divItems[j].disabled=true;
					  if(divItems[j].src){
					  	divItems[j].src="../images/exclamation.png";
					  }	
				}		
			}	
		}		
	}
	alert('Se liberaron las Operaciones');
}


function deshabilitarLiberarNal(){
	var confirmar=document.getElementById('daliForm:IdOperacionesLiberarNal');	
	var idsConfirmar=confirmar.value;	
	confirmar.value="";			
	if (idsConfirmar){
		var elems=idsConfirmar.split(',');		
		for(var i=0; i< elems.length;i++){
			if(elems[i].length >= 1){				
				var divItems=document.getElementById("libe_nal_"+elems[i]).childNodes;																
				for(var j=0; j<divItems.length ; j++){
					  divItems[j].disabled=true;
					  if(divItems[j].src){
					  	divItems[j].src="../images/exclamation.png";
					  }	
				}		
			}	
		}		
	}
}

function deshabilitarAprobarNal(){
	var confirmar=document.getElementById('daliForm:IdOperacionesAprobarNal');
	var idsConfirmar=confirmar.value;	
	confirmar.value="";	
	
	if (idsConfirmar){
		var elems=idsConfirmar.split(',');		
		for(var i=0; i< elems.length;i++){
			if(elems[i].length >= 1){				
				var divItems=document.getElementById("aprob_nal_"+elems[i]).childNodes;																
				for(var j=0; j<divItems.length ; j++){
					  divItems[j].disabled=true;
					  if(divItems[j].src){
					  	divItems[j].src="../images/exclamation.png";
					  }	
				}		
			}	
		}		
	}
	alert('Se aprobaron las operaciones');
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


//Seccion para el modulo de internacional
// 2009 - 11 - 09


function agregaIntlAprobar(id , skip) {
	var idOper=id.replace("chk_aprob_","");
	var idsEliminar=document.getElementById('daliForm:intlIdOperacionesAprobar').value;		
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
	document.getElementById('daliForm:intlIdOperacionesAprobar').value=idsEliminar;
}



function agregaIntlLiberar(id, skip) {
	
	var idOper=id.replace("chk_libe_","");	
	var idsConfirmar=document.getElementById('daliForm:intlIdOperacionesLiberar').value;
	if ($j("#"+id).is(':checked') || skip){
		if(skip){
			$j("#"+id).prop('checked', true);
		}
		if(idsConfirmar.search(idOper) == -1 ){
			idsConfirmar=idsConfirmar+","+idOper;
		}					
	}else{	
		idsConfirmar=idsConfirmar.replace(","+idOper,"");		
	}	
	document.getElementById('daliForm:intlIdOperacionesLiberar').value=idsConfirmar;
	
}

function agregaAprobarNal(id, skip) {
	
	
	var idOper=id.replace("chk_aprobar_nal_","");	
	var idsConfirmar=document.getElementById('daliForm:IdOperacionesAprobarNal').value;
	if ($j("#"+id).is(':checked') || skip){
		if(skip){
			$j("#"+id).prop('checked', true);
		}
		if(idsConfirmar.search(idOper) == -1 ){
			idsConfirmar=idsConfirmar+","+idOper;
		}					
	}else{	
		idsConfirmar=idsConfirmar.replace(","+idOper,"");		
	}	
	document.getElementById('daliForm:IdOperacionesAprobarNal').value=idsConfirmar;
	
}

function agregaLiberarNal(id, skip) {
	
	
	var idOper=id.replace("chk_liberar_nal_","");	
	var idsConfirmar=document.getElementById('daliForm:IdOperacionesLiberarNal').value;
	if ($j("#"+id).is(':checked') || skip){
		if(skip){
			$j("#"+id).prop('checked', true);
		}
		if(idsConfirmar.search(idOper) == -1 ){
			idsConfirmar=idsConfirmar+","+idOper;
		}					
	}else{	
		idsConfirmar=idsConfirmar.replace(","+idOper,"");		
	}	
	document.getElementById('daliForm:IdOperacionesLiberarNal').value=idsConfirmar;
	
}


function deshabilitarIntlAprobar(){	
	var cancelar=document.getElementById('daliForm:intlIdOperacionesAprobar');
	var idsEliminar=cancelar.value;	
	cancelar.value="";			
	if (idsEliminar){
		var elems=idsEliminar.split(',');		
		for(var i=0; i< elems.length;i++){
			if(elems[i].length >= 1){				
				var divItems=document.getElementById("aprob_"+elems[i]).childNodes;																
				for(var j=0; j<divItems.length ; j++){
					  divItems[j].disabled=true;
					  if(divItems[j].src)
					  	divItems[j].src="../images/exclamation.png";			  				  								
				}		
			}	
		}	
	}
	alert('Se aprobaron las operaciones');
}


function agregaAprobarNalAll(tipo) {	
	var checkboxes = document.getElementById("daliForm").checkbox;	
	var cont = 0; 	
    var todo = "";
		for (i = 0; i < document.forms["daliForm"].elements.length; i++){
			var cadena = document.forms[0].elements[i].id;
			todo  = todo  +  cadena;
			if (cadena .indexOf("chk_aprobar_nal") > -1){
				document.forms[0].elements[i].checked = tipo;
				agregaAprobarNal(cadena,false);
				
			}
		}
   
}

function agregaLiberarNalAll(tipo) {	
	var checkboxes = document.getElementById("daliForm").checkbox;	
	var cont = 0; 	
    var todo = "";
		for (i = 0; i < document.forms["daliForm"].elements.length; i++){
			var cadena = document.forms[0].elements[i].id;
			if (cadena .indexOf("chk_liberar_nal") > -1){				
				document.forms[0].elements[i].checked = tipo;
				agregaLiberarNal(cadena,false);
				
			}
		}
}

function validarSiNumero(numero){
    if (!/^([0-9])*$/.test(numero)){
    	$j("input[name$='referenciaOperacion']").prop('value','');
    }
}

function mostrarDetalle(titulo,url){
	$j('#posiciones').dialog('open');
	$j('#posiciones').dialog('option','title',titulo);
	$j('#posiciones').prop('src',url);
	$j('#posiciones').css('width','99%');
	$j('.ui-widget-overlay') .on("click", function() {
	    $j('#posiciones').dialog('close');
	});
}

function actualizarGrid(){
	btnBuscar = $j('#daliForm\\:botonBuscar');
	btnBuscar.trigger("click");
}

function seleccionarTodosChk(chkMaster) {
	$j('input[type=checkbox].chk').prop('checked', $j(chkMaster).prop('checked'));
}



function hasErrors(){
	var errors = document.getElementById("daliForm:validationErrors");
	return errors.value == "true";
}

function seleccionarTodosAutoriza(value){
	// console.log('seleccionarTodosAutoriza');
	var idsAutoriza = document.getElementById("daliForm:idsAutorizar").value;
	var ids = idsAutoriza.split(',');
	
	if(value){
		for( var i = 0; i < ids.length; i++){
			var chkId = "input[type=checkbox].chk_aprobar_nal_" + ids[i];
			// console.log(chkId);
			var chk = $j(chkId);
			
			if(chk){
				chk.prop('checked', value);
			}
		}
	}else{
		$j('input[type=checkbox].chkAutoriza').prop('checked', value);
	}
}
function onChangeChkAutoriza() {
	var checkedChkMaster = true;
	
	$j('input[type=checkbox].chkAutoriza').each(function(index, chk) {
		if(!$j(chk).prop('checked')) {
			checkedChkMaster = false;
		}
	});
	
	$j('input[type=checkbox].chkAllAutoriza').prop('checked', checkedChkMaster);	
}
function seleccionarTodosLibera(value){
	// console.log('seleccionarTodosLibera');
	
	var idsLibera = document.getElementById("daliForm:idsLiberar").value;
	var ids = idsLibera.split(',');
	
	if(value){
		for( var i = 0; i < ids.length; i++){
			var chkId = "input[type=checkbox].chk_liberar_nal_" + ids[i];
			// console.log(chkId);
			var chk = $j(chkId);
			
			if(chk){
				chk.prop('checked', value);
			}
		}
	}else{
		$j('input[type=checkbox].chkLibera').prop('checked', value);
	}
}

function onChangeChkLibera() {
	var checkedChkMaster = true;
	
	$j('input[type=checkbox].chkLibera').each(function(index, chk) {
		if(!$j(chk).prop('checked')) {
			checkedChkMaster = false;
		}
	});
	
	$j('input[type=checkbox].chkAllLibera').prop('checked', checkedChkMaster);	
}

function irAlPrimerRegistro(){
	btnBuscar = $j('#daliForm\\:control_posicion_primero');
	btnBuscar.trigger("click");
}

function disableAllButtons(){
    
    $j("a[id$='btnAutorizaSeleccion']").hide();
    $j("a[id$='btnLiberaSeleccion']").hide();
    
    $j("a[class^='btnAprobarNacional_']").each(function(index, btn) {
        $j(btn).hide();
    });
    
    $j("a[class^='btnLiberarNacional_']").each(function(index, btn) {
        $j(btn).hide();
    });
}

function enableAllButtons(){
    //var checkAllAutorizaEnabled = document.getElementById("daliForm:checkAllAutorizaEnabled").value;
    //var checkAllLiberaEnabled = document.getElementById("daliForm:checkAllLiberaEnabled").value;
    
    
    $j("a[id$='btnAutorizaSeleccion']").show();
    $j("a[id$='btnLiberaSeleccion']").show();
    
    $j("a[class^='btnAprobarNacional_']").each(function(index, btn) {
        $j(btn).show();
    });
    
    $j("a[class^='btnLiberarNacional_']").each(function(index, btn) {
        $j(btn).show();
    });
}

function bloquearSeccionBotones() {
    $j("[id$='panelBotones']").css("display", "none");
    $j("#mensajeEnviando").css("display", "inline");
}

function desbloquearSeccionBotones() {
    $j("[id$='panelBotones']").css("display", "inline");
    $j("#mensajeEnviando").css("display", "none");
}
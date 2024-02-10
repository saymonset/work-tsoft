	$j(document).on('ready',function(){
		$j('#posiciones').dialog(
				{ 
					autoOpen:false,
					height:650,
					maxheight:1200,
					maxWidth:1200,
					modal: true,
					resizable: false,
					width:970,
					zIndex:10003
				 }
			);
	});

	$j(document).on('ready',function(){
		$j('#retencion').dialog(
				{ 
					autoOpen:false,
					height:400,
					maxheight:1200,
					maxWidth:1200,
					modal: true,
					resizable: false,
					width:900,
					zIndex:10003
				 }
			);
	});
function bloqueaDireccionPostalW8(deshabilitado) {
    document.getElementById("daliForm:callePostal").readOnly=deshabilitado;
    document.getElementById("daliForm:numeroExteriorPostal").readOnly=deshabilitado;
    document.getElementById("daliForm:numeroInteriorPostal").readOnly=deshabilitado;
    document.getElementById("daliForm:codigoPostalPostal").readOnly=deshabilitado;
    document.getElementById("daliForm:cityTownPostal").readOnly=deshabilitado;
    document.getElementById("daliForm:stateProvincePostal").readOnly=deshabilitado;
    document.getElementById("daliForm:paisPostal").readOnly=deshabilitado;
    document.getElementById("daliForm:callePostal").value='';
    document.getElementById("daliForm:numeroExteriorPostal").value='';
    document.getElementById("daliForm:numeroInteriorPostal").value='';
    document.getElementById("daliForm:codigoPostalPostal").value='';
    document.getElementById("daliForm:cityTownPostal").value='';
    document.getElementById("daliForm:stateProvincePostal").value='';
    document.getElementById("daliForm:paisPostal").value='';
}

function habilitaAbc(habilita){
	
	if (habilita){
		document.getElementById("daliForm:divAbc").style.display = 'inline'; 
		document.getElementById("daliForm:divRadios").style.display = 'inline';
		document.getElementById("daliForm:divReportes").style.display = 'none';
		}
	else {
		document.getElementById("daliForm:divAbc").style.display = 'none';
		document.getElementById("daliForm:divRadios").style.display = 'none';
		document.getElementById("daliForm:divReportes").style.display = 'inline';
    }
}

function verificaBloqueoDireccionPostal() {

    var valorCasilla = document.getElementById("daliForm:direccionPostalDeshabilitada").checked;
    // alert('Valor checked: ' + document.getElementById("daliForm:direccionPostalDeshabilitada").checked );
    if(valorCasilla) {
        bloqueaDireccionPostalW8(valorCasilla);
    }
}

function verificaValorW9() {
    var valores = $j("input[name='daliForm:tipoPropietario']:radio");
    var i;
    var seleccionado = 0;
    for(i=0; i < valores.size(); i++) {
        if(valores[i].checked) {
            seleccionado = valores[i].value;
        }
    }
    if(seleccionado == 4){
        $j("input[name$='taxClassification']").attr('readonly',false);
    } else {
        $j("input[name$='taxClassification']").attr('readonly',true);
        $j("input[name$='taxClassification']").attr('value','');
    }
    if(seleccionado == 5){
    	$j("input[name$='otherDescription']").attr('readonly',false);
    } else {
        $j("input[name$='otherDescription']").attr('readonly',true);
        $j("input[name$='otherDescription']").attr('value','');
    
    }
}

function bloqueaReferenceNumber(deshabilitado) {
    document.getElementById("daliForm:numeroReferencia").readOnly=deshabilitado;
    document.getElementById("daliForm:numeroReferencia").value='';
}

function bloqueaNumeroIdentificacion(deshabilitado) {
    document.getElementById("daliForm:numeroIdentificacion").readOnly=deshabilitado;
    document.getElementById("daliForm:numeroIdentificacion").value='';
}

function verificaReferenceNumber() {
    var valorCasilla = document.getElementById("daliForm:referenceNumberDeshabilitada").checked;
    // alert('Valor checked: ' + document.getElementById("daliForm:referenceNumberDeshabilitada").checked );
    if(valorCasilla) {
        bloqueaReferenceNumber(valorCasilla);
    }
}

function mostrarBenefExistentes(personaFisica){
    var nombre = '', apPaterno = '', apMaterno = '', razonSocial = '', rfc = '';
    var custodio = 0, tipoBeneficiario = 0;
	
    if($j("input[id$='RFC']").attr('value') === undefined ){
        rfc = '';
    }else{
        rfc = $j("input[id$='RFC']").attr('value');
    }
	
    custodio = document.getElementById('daliForm:idCustodio').value;
	
    tipoBeneficiario = document.getElementById('daliForm:idTipoBenef').value;
	
    if( personaFisica ) {
        if($j("input[id$='nombre']").attr('value') === undefined ){
            nombre = '';
        }else{
            nombre = $j("input[id$='nombre']").attr('value');
        }
		
        if($j("input[id$='apPaterno']").attr('value') === undefined ){
            apPaterno = '';
        }else{
            apPaterno = $j("input[id$='apPaterno']").attr('value');
        }
		
        if($j("input[id$='apMaterno']").attr('value') === undefined ){
            apMaterno = '';
        }else{
            apMaterno = $j("input[id$='apMaterno']").attr('value');
        }
    } else {
        if($j("input[id$='razonSocial']").attr('value') === undefined ){
            razonSocial = '';
        }else{
            razonSocial = $j("input[id$='razonSocial']").attr('value');
        }
    }
		 
	
	
    url = contextPath+'/beneficiarios/mostrarBeneficiariosExistentes.faces';
    url += '?nombre=' + nombre
    + '&apPaterno=' + apPaterno
    + '&apMaterno=' + apMaterno
    + '&razonSocial=' + razonSocial
    + '&rfc=' + rfc
    + '&personaFisica=' + personaFisica
    + '&custodio=' + custodio
    + '&tipoBeneficiario=' + tipoBeneficiario;
    $j('#posiciones').dialog( "option", "title","Beneficiarios Existentes");
    $j('#posiciones').dialog('open');
	$j('#posiciones').attr('src',url);
	$j('#posiciones').css('width','99%');
	
	$j('.ui-widget-overlay') .on("click", function() {
	    $j('#posiciones').dialog('close');
	});

	
}

function seleccionarBeneficiario(idBeneficiario,formato){
    var url = contextPath+'/beneficiarios/mostrarBeneficiarios' + formato + '.faces';
    url += '?idBeneficiario=' + idBeneficiario
    + '&formato=' + formato
    + '&idMuestraConsulta=true';
    muestraActivo(url);
}

function detalleBeneficiario(idBeneficiario,formato){
    var url = contextPath+'/beneficiarios/mostrarBeneficiarios' + formato + '.faces';
    url += '?idBeneficiario=' + idBeneficiario
    + '&formato=' + formato
    + '&idMuestraConsulta=false';
    muestraActivo(url);
}

function muestraActivo(url) {
    $j('#posiciones').dialog( "option", "title",  "Beneficiario", "title" );
    $j('#posiciones').dialog('open');
	$j('#posiciones').attr('src',url);
	$j('#posiciones').css('width','99%');
	
	$j('.ui-widget-overlay') .on("click", function() {
	    $j('#posiciones').dialog('close');
	});

}



function confirmMostrarBeneficiariosExistentes(personaFisica) {
    if( confirm('Existen Beneficiarios con el mismo nombre. \xBFDeseas verlos (Aceptar) o capturar este beneficiario (Cancelar)\x3F') ) {
        mostrarBenefExistentes(personaFisica);
    } else {
        $j("input[id$='flagBuscarBeneficiarios']").attr('value',false);
        window.scrollTo(0,0);
        $j("input[id$='botonGuardar']").click();
    }
}

function closeMuestraBeneficiario() {
	$j('#posiciones').dialog('close');
    $j("input[id$='botonBuscar']").click();
}

function muestraModificaBeneficiario(idBeneficiario,formato){
    var url = contextPath+'/beneficiarios/modificaBeneficiario' + formato + '.faces';
    url += '?idBeneficiario=' + idBeneficiario
    + '&formato=' + formato;
    $j('#posiciones').dialog( "option", "title",  "Beneficiario");
    $j('#posiciones').dialog('open');
	$j('#posiciones').attr('src',url);
	$j('#posiciones').css('width','99%');
}

function muestraAsignaAdpBeneficiario(idBeneficiario){
    var url = contextPath+'/beneficiarios/asignarAdpBeneficiario.faces';
    url += '?idBeneficiario=' + idBeneficiario;
    $j('#posiciones').dialog( "option", "title", "Asignar Adp a Beneficiario");
    $j('#posiciones').dialog('open');
	$j('#posiciones').attr('src',url);
	$j('#posiciones').css('width','99%');

	$j('.ui-widget-overlay') .on("click", function() {
	    $j('#posiciones').dialog('close');
	});

}

function muestraBusquedaBeneficiarioRFC(){
	
	
    if(document.getElementById('daliForm:lstFormato').value === undefined ){
        formato = '';
    }else{
    	formato = document.getElementById('daliForm:lstFormato').value;
    }

    if(($j("input[id$='inputRfcW8Ben']").attr('value').trim() != '' && !($j("input[id$='inputRfcW8Ben']").attr('value') === undefined))
    		&& (formato == 'W8BEN' || formato == 'W8BENE')){
        if(document.getElementById('daliForm:lstCustodios').value === undefined ){
            custodio = '';
        }else{
        	custodio = document.getElementById('daliForm:lstCustodios').value;
        }
        
        if(document.getElementById('daliForm:lstTipoBeneficiario').value === undefined ){
        	tipoBeneficiario = '';
        }else{
        	tipoBeneficiario = document.getElementById('daliForm:lstTipoBeneficiario').value;
        }
        
        if($j("input[id$='idFolioInstitucion']").attr('value') === undefined ){
        	idFolioInstitucion = '';
        }else{
        	idFolioInstitucion = $j("input[id$='idFolioInstitucion']").attr('value');
        }
        
    /*    if(document.getElementById('daliForm:lstFormato').value === undefined ){
            formato = '';
        }else{
        	formato = document.getElementById('daliForm:lstFormato').value;
        } */
        
        if(document.getElementById('daliForm:estatusBenef').value === undefined ){
        	estatusBenef = '';
        }else{
        	estatusBenef = document.getElementById('daliForm:estatusBenef').value;
        }
        
        if($j("input[id$='uoiNumber']").attr('value') === undefined ){
        	uoiNumber = '';
        }else{
        	uoiNumber = $j("input[id$='uoiNumber']").attr('value');
        }
        
        if($j("input[id$='fechaRegistroInicioInputDate']").attr('value') === undefined){
        	fechaRegistroInicio = '';
        }else{
        	fechaRegistroInicio = $j("input[id$='fechaRegistroInicioInputDate']").attr('value');
        }
        
        if($j("input[id$='fechaRegistroFinInputDate']").attr('value') === undefined){
        	fechaRegistroFin = '';
        }else{
        	fechaRegistroFin = $j("input[id$='fechaRegistroFinInputDate']").attr('value');
        }
        
        if($j("input[id$='fechaFormatoInicioInputDate']").attr('value') === undefined){
        	fechaFormatoInicio = '';
        }else{
        	fechaFormatoInicio = $j("input[id$='fechaFormatoInicioInputDate']").attr('value');
        }
        
        if($j("input[id$='fechaFormatoFinInputDate']").attr('value') === undefined ){
        	fechaFormatoFin = '';
        }else{
        	fechaFormatoFin = $j("input[id$='fechaFormatoFinInputDate']").attr('value');
        }
        
        if($j("input[id$='fechaAutorizacionInicioInputDate']").attr('value') === undefined ){
        	fechaAutorizacionInicio = '';
        }else{
        	fechaAutorizacionInicio = $j("input[id$='fechaAutorizacionInicioInputDate']").attr('value');
        }
        
        if($j("input[id$='fechaAutorizacionFinInputDate']").attr('value') === undefined ){
        	fechaAutorizacionFin = '';
        }else{
        	fechaAutorizacionFin = $j("input[id$='fechaAutorizacionFinInputDate']").attr('value');
        }
        
        if($j("input[id$='nombreBenef']").attr('value') === undefined ){
        	nombreBenef = '';
        }else{
        	nombreBenef = $j("input[id$='nombreBenef']").attr('value');
        }
        
        if($j("input[id$='razonSocial']").attr('value') === undefined ){
        	referenceNumber = '';
        }else{
        	referenceNumber = $j("input[id$='razonSocial']").attr('value');
        }
        
        if($j("input[id$='adp']").attr('value') === undefined ){
        	adp = '';
        }else{
        	adp = $j("input[id$='adp']").attr('value');
        }
        
        if(document.getElementById('daliForm:activo').value === undefined ){
        	activo = '';
        }else{
        	activo = document.getElementById('daliForm:activo').value;
        }
        
        if($j("input[id$='inputRfcW8Ben']").attr('value') === undefined ){
        	rfc = '';
        }else{
        	rfc = $j("input[id$='inputRfcW8Ben']").attr('value');
        }

        var url = contextPath+'/beneficiarios/mostrarRFCBeneficiariosW8BENE.faces';
        url += '?custodio=' + custodio;
        url += '&tipoBeneficiario=' + tipoBeneficiario;
        url += '&idFolioInstitucion=' + idFolioInstitucion;
        url += '&formato=' + formato;
        url += '&estatusBenef=' + estatusBenef;
        url += '&uoiNumber=' + uoiNumber;
        url += '&fechaRegistroInicio=' + fechaRegistroInicio;
        url += '&fechaRegistroFin=' + fechaRegistroFin;
        url += '&fechaFormatoInicio=' + fechaFormatoInicio;
        url += '&fechaFormatoFin=' + fechaFormatoFin;
        url += '&fechaAutorizacionInicio=' + fechaAutorizacionInicio;
        url += '&fechaAutorizacionFin=' + fechaAutorizacionFin;
        url += '&nombreBenef=' + nombreBenef;
        url += '&referenceNumber=' + referenceNumber;
        url += '&adp=' + adp;
        url += '&activo=' + activo;
        url += '&rfc=' + rfc;
        
        $j('#posiciones').dialog( "option", "title", "Busqueda Beneficiario por RFC");
        $j('#posiciones').dialog('open');
    	$j('#posiciones').attr('src',url);
    	$j('#posiciones').css('width','99%');

    	$j('.ui-widget-overlay') .on("click", function() {
    	    $j('#posiciones').dialog('close');
    	});
    }

}

function closeModificaBeneficiario() {
    parent.$j('#posiciones').dialog('close');
    parent.$j("input[id$='botonBuscar']").click();
}

function validaUsuarioExistente(beneficiariosExistentes, personaFisica) {
    if(beneficiariosExistentes) {
        setTimeout("confirmMostrarBeneficiariosExistentes("+personaFisica+")",500);
    }
}

function setParametersReporte(letra,pagina){
	var objLetra = $j("input[id$='letraHiden']");
	var objPagina = $j("input[id$='paginaHiden']");
	objLetra.val(letra);
	objPagina.val(pagina);		
}

function muestraModificaPorcentajeRetencion(idBeneficiario,uoi,nombreBeneficiario,isPersonaFisica,razonSocial,pais,porcentajeRetencion,tipoBeneficiario,nombreCustodio,tipoFormato){
    var url = contextPath+'/beneficiarios/modificaPorcentajeRetencion.faces';    
    url += '?idBeneficiario=' + idBeneficiario;
    url += '&uoi=' + uoi;
    url += '&nombreBeneficiario=' + nombreBeneficiario; 
    url += '&isPersonaFisica=' + isPersonaFisica;
    url += '&razonSocial=' + razonSocial;
    url += '&pais=' + pais;
    url += '&porcentajeRetencion=' + porcentajeRetencion;
    url += '&tipoBeneficiario=' + tipoBeneficiario;
    url += '&nombreCustodio=' + nombreCustodio;
    url += '&tipoFormato=' + tipoFormato;
    $j('#retencion').dialog( "option", "title", "Modificar Porcentaje de Retenci\u00F3n");   
    $j('#retencion').dialog('open');
	$j('#retencion').attr('src',url);
	$j('#retencion').css('width','99%');
	
	$j('#retencion').dialog({
		   close: function(event, ui) {
		   $j("input[id$='control_bitacora_actual']").click();	   
		   }
	});
	$j('.ui-widget-overlay') .on("click", function() {							
		$j('#retencion').dialog('close');	    
		
	});
	
	
	
}

function confirmarGuardarPorcentaje(){
	var output="\u00bfEst\u00e1 seguro de que desea cambiar el porcentaje de retenci\u00F3n?";
	if(!confirm(output,"titulo ventana"))
	return false;
	return true;
}

function closeModificaPorcentajeRetencion() {	  
	parent.$j('#retencion').dialog('close'); 	
}

function resetearValorPorcentajeRetencion(){
	$j("input[id$='valorPorcentaje']").attr('value','');			
}

function bloqueaSeccion10(deshabilitado) {
    document.getElementById("daliForm:txtLine10p1").readOnly=deshabilitado;    
    document.getElementById("daliForm:txtLine10p2").readOnly=deshabilitado;    
    document.getElementById("daliForm:txtLine10p3").readOnly=deshabilitado;    
    document.getElementById("daliForm:txtLine10p4").readOnly=deshabilitado;
    document.getElementById("daliForm:txtLine10p1").value='';
    document.getElementById("daliForm:txtLine10p2").value='';
    document.getElementById("daliForm:txtLine10p3").value='';
    document.getElementById("daliForm:txtLine10p4").value='';
}
function verificaBloqueoSeccion10() {
    var valorCasilla = document.getElementById("daliForm:seccion10DeshabilitadaChk").checked;
    // alert('Valor checked: ' + document.getElementById("daliForm:seccion10DeshabilitadaChk").checked );
    if(valorCasilla) {
    	bloqueaSeccion10(valorCasilla);
    }
}

function validaConTope(objeto,tope){
	var dia = new Number(objeto.value);
	var tope = new Number(tope);
	if(dia > tope){
		objeto.value="";
	}	
}

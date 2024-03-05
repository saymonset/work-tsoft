	
	var $j = jQuery.noConflict();
	var consultaEnProceso=false;
	
	
	
	$j(document).ready(function(){
		
		$j('#desplegarCriterioUno').click(function ()
		{	
			$j('#panelCriteriosCriterioUno').slideToggle('normal');
		});
		
		
		$j('#desplegarCriterioDos').click(function()
		{
			$j('#panelCriteriosCriterioDos').slideToggle('normal');
		});
		
		
		$j('#desplegarCriterioTres').click(function()
		{
			$j("#panelCriteriosCriterioTres").slideToggle('normal');
		});
     });

	function buscarRegistros() {
		if($j('input[id$=traspasarValores]').prop("checked") == true){
			if(! $j('input[id$=tv]').prop("value") != ""){
				alert("El campo T.V. es obligatorio");
				$j('input[id$=tv]').focus();
				return false;
			}
			else
				if(! $j('input[id$=suggest]').prop("value") != ""){
					alert("El campo emisora es obligatorio");
					$j('input[id$=suggest]').focus();
					return false;
				}
				else
					if(! $j('input[id$=serie]').prop("value") != ""){
						alert("El campo Serie es obligatorio");
						$j('input[id$=serie]').focus();
						return false;
					}
					
			window.scrollTo(0,0);
		}
				
   		$j('input[id$=botonBuscar]').prop('disabled','disabled');
   		$j('input[id$=guardar]').prop('disabled','disabled');
      	
      	//return false;
      	return true;
	}
	
	function presentarResultados() { 
	
		$j("#divCriterios").css('display','none');
		$j("#divResumen").css('display','inline');
		
		$j("#divResumen").css('opacity','0');
		$j("#divResumen").animate({
					      		opacity:1
					      	}, 1000);
		$j("#botonEditarCriterios").css('display','inline');
		
		$j('#contenido').css('display','block');
      	$j('#divTablaResultados').css('display','block');
		
		$j('#divTablaResultados').css('opacity','0');

      	$j('#divTablaResultados').animate({
      		opacity: 1
      	}, 500);
		$j('input[id$=botonBuscar]').removeAttr('disabled');
		$j('input[id$=guardar]').removeAttr('disabled');
	}
	
	function presentarCriterios(){
		$j("#divCriterios").css('display','inline');
		$j("#divResumen").css('display','none');
		$j("#botonEditarCriterios").css('display','none');
	}
	
	function addHrefLoader(id) {
		if($j('#loader' + id).prop('id') === undefined) {
			$j("a[id$='" + id + "']").after('<img src="'+contextPath+'/images/loading2.gif" border="0" id="loader' + id + '" />');
		}
	}
	
	function addLoader(id) {

		if($j('#loader' + id).prop('id') === undefined) {
			$j("input[id$='" + id + "']").after('<img src="'+contextPath+'/images/loading1.gif" border="0" id="loader' + id + '" />');
		}
	}
	
	function removeLoader(id) {
		if($j('#loader' + id)) {
			$j("#loader" + id).remove();
		}
	}
	
	function bloquearInput(){
		var receptor = $j("input[id$='idFolioTraspasante']").val() + $j("input[id$='cuentaTraspasante']").val();
		var receptorPrevio = $j("input[id$='cuentaPrevia']");
		
		$j("#input_"+receptorPrevio.val()).css('display','inline');
		$j("#input_"+receptor).css('display','none');
		receptorPrevio.val(receptor);
	}
	
	function formatoNumerico(obj){
		//confirm("obj:: " + obj);
		var formato = "";
		var aux = "";
		var cont = 0;
		var valor = "";// = obj.value;
		
		for(var inx = 0; inx < obj.value.length; inx++){
			if(obj.value.charAt(inx) != ',')
				valor += obj.value.charAt(inx);
		}
		
		if(valor.length > 3){
			for(var inx = valor.length-1; inx >= 0; inx--){
				cont++;
				if(cont == 4){
					aux += ",";
					cont = 1;
				}
				aux += valor.charAt(inx);
			}
			
			for(var inx = aux.length-1; inx >= 0; inx--)
				formato += aux.charAt(inx);
		}
		else
			formato = valor;
		//confirm("formato:: " + formato);
		
		obj.value = formato;
	}

function removeApplet(){
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
	
	
	function deshabilitarIntlGuardar(){
		
		var divItems=document.getElementById("daliForm:btnguardar");																
	    //divItems.disabled=true;  
	    //divItems.image="../images/exclamation.png";
	    
		//alert('Se guardo el retiro');
	}
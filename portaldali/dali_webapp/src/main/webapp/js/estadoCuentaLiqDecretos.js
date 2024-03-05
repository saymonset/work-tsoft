$j(document).on('ready',function(){
	$j('#detalleEdoCta').dialog(
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
function buscarRegistros(){
}

function presentarResultados(){
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
				      		opacity:1
				      	}, 1000);
	$j("#botonEditarCriterios").css('display','inline');
}

function mostrarDetalle(titulo,url){
	$j('#detalleEdoCta').dialog('open');
	$j('#detalleEdoCta').dialog('option','title',titulo);
	$j('#detalleEdoCta').prop('src',url);
	$j('#detalleEdoCta').css('width','99%');
	$j('.ui-widget-overlay') .on("click", function() {
	    $j('#detalleEdoCta').dialog('close');
	});
}

function ocultarCampoCuenta() {
	var radios = jQuery("input[type='radio']");
	var radioOption = radios.filter(":checked");
	var valor = radioOption.val();
	if (valor == 'PAGOS') {
		var inp = document.getElementById("daliForm:cuentaTraspasante");
		inp.value = "";
		inp.setAttribute("readOnly", "true");
	}
	else if (valor == 'COBROS') {
		var inp = document.getElementById("daliForm:cuentaTraspasante");
		inp.value = "";
		inp.removeAttribute("readOnly");
	}
}


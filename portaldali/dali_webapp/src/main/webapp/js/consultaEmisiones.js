
function presentarEmisiones() { 
	confirm(".:CONSULTA EMISIONES:.");
	/*$j("#divCriterios").css('display','none');
	$j("#divResumen").css('display','inline');
	
	$j("#divResumen").css('opacity','0');
	$j("#divResumen").animate({
				      		opacity:1
				      	}, 1000);
	$j("#botonEditarCriterios").css('display','inline');
	
	$j('#contenido').css('display','block');*/
	
   	$j('#divTablaResultados').css('display','block');
	$j('#divTablaResultados').css('opacity','0');
   	$j('#divTablaResultados').animate({opacity: 1}, 500);
   	
	//$j('input[id$=botonBuscar]').removeAttr('disabled');
	//$j('input[id$=guardar]').removeAttr('disabled');
}
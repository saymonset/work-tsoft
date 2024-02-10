$j(document).on('ready',function(){
	$j('#detalle').dialog(
			{ 
				autoOpen:false,
				height:480,
				maxheight:1200,
				maxWidth:1200,
				modal: true,
				resizable: false,
				width:650,
				zIndex:10003
			 }
		);

});

function detalleResultadoMensaje(dbkey){
    var url = contextPath+'/divinternacional/consultaFastworkIsoDetalle.faces';
    url += '?dbkey=' + dbkey
    +'&TB_iframe=true'
    +'&height=550&width=925';
    mostrarDetalle('Mensaje ISO',url);
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

function bloquearBotonesReportesFast(botonesBloquear) {
	iniciarMonitorPeticionReporte = true;
	for(i=0; i < botonesBloquear.length; i++) {
		$j("input[id$=\"" + botonesBloquear[i] + "\"]").hide();
	}
	$j("#monitorPeticionReporte").css("display", "inline");
	setTimeout("actualizaEstadoF();", 5000);
}

function desbloquearBotonesReportesFast(estatusPeticion, botonesDesbloquear) {
	if(estatusPeticion === true) {		
		for(i=0; i < botonesDesbloquear.length; i++) {
			$j("input[id$=\"" + botonesDesbloquear[i] + "\"]").show();
		}
		$j("#monitorPeticionReporte").css("display", "none");
		iniciarMonitorPeticionReporte = false;
	}
	else {
		setTimeout("actualizaEstadoF();", 5000);
	}
}
				
function refrescarMonitorFast() {
	if(iniciarMonitorPeticionReporte === true) {
    	$j("input[id$='btnActualizarEstadoPeticion']").click();
	}
}


function validarTotalRegistros(totalRegistros, tipoReporte){
	if(totalRegistros > 10000){
		return confirm('Se exportaran un número limitado de datos en el reporte, la cantidad máxima a exportar es de 10,000 registros. ¿Desea continuar?')
	}else{
		return true;		
	}
}

function validateDecimal(element)    {
	var ex = /^[0-9]+\.?[0-9]{0,8}$/;
	 if(!ex.test(element.value)){
		 element.value = element.value.substring(0,element.value.length - 1);
	  }
}

function textAreaMaxLength(element) {
    var max = 60;
    if (element.value.length > max) {
    	element.value = element.value.substr(0, max);
    }
}
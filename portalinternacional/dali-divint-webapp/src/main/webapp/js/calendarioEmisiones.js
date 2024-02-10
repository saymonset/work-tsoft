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

function presentarResumen() {
	$j("#divCriterios").css("display", "none");
	$j("#divResumen").css("display", "inline");
	$j("#divResumen").css("opacity", "1");
	//$j("#divResumen").animate({opacity:1}, 1000);
	$j("#botonEditarCriterios").css("display", "inline");
}

function presentarCriterios() {
	$j("#divCriterios").css("display", "inline");
	$j("#divResumen").css("display", "none");
	$j("#botonEditarCriterios").css("display", "none");
}

function assetManager(folioEmision, isin, idCatbic){
    var url = contextPath+'/calendarioCapitalesDistribucion/assetManagerCalendarioCapitalesPopUp.faces';
    url += '?folioEmision=' + folioEmision
    + '&isin=' + isin
    + '&idCatbic=' + idCatbic;
    mostrarDetalle('Asset Manager Emisiones',url);
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

function consultaGeneral(){	
	parent.$j("input[id$='botonBuscarEmisiones']").click();
}

function consultaGuardar(){
	var texto=document.getElementById('assetManagerForm:textArea_1');
		if(texto.value != ''){
			parent.$j("input[id$='botonBuscarEmisiones']").click();
		}		
}

function cerrarSinRefrescar(){
	parent.$j('#detalle').dialog('close');
}


$j(document).on('ready',function(){
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

function presentarCriterios() {
	$j("#divCriterios").css("display", "inline");
	$j("#divResumen").css("display", "none");
	$j("#botonEditarCriterios").css("display", "none");
}

function presentarResumen() {
	$j("#divCriterios").css("display", "none");
	$j("#divResumen").css("display", "inline");
	$j("#divResumen").css("opacity", "1");	
	$j("#botonEditarCriterios").css("display", "inline");
	$j("#botonNuevaCuenta").css("display", "inline");
}

function editaCuenta(idCuenta){
    var url = contextPath+'/conciliacionInternacional/nuevaEditaCuenta.faces';
    url += '?idCuenta=' + idCuenta;
    
    var titulo = (idCuenta == 0) ? 'Nueva Cuenta' : 'Edita Cuenta';
    mostrarDetalle(titulo, url, 500, 900);
}

function cierraDialog(){
	parent.$j('#noScrolling').dialog('close');
}

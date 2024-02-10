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
	
	$j( '#cuerpoEvco' ).ckeditor();	
	$j( '#piePagina' ).ckeditor();
	$j('.classOpcion').ckeditor();
	$j('.classNota').ckeditor();
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
	$j(document).on("keydown", function (e) {
	    if (e.which === 8 && !$j(e.target).is("input, textarea")) {
	        e.preventDefault();
	    }
	});
});

//defino un array global que me permite guardar en forma temporal las opciones que se van mostrando en la tabla
var arrayOpciones = new Array();
var numeroOpcion;
var tipoAccion;

function openOptions(tipo){
	$j( 'input[id*=tipoOperacion]' ).val(tipo);
	/*
	 * exploro en esta función que abre el dialogo si la tabla de opciones tiene algun elemento,
	 * se hace en esta parte porque aqui la tabla ya existe y esta rendereada con los ultimos
	 * valores que contiene
	 */
	var elementosTable = $j('table[id*=table-opciones]').find('span[id*=id-cpo-opcion_]').length;
	
	//determino si la opcion tiene el valor por default activado
	var valDefault = $j('table[id*=table-opciones]').find('div[id*=valor-default]').text();
	
	/*
	 * pregunto si la tabla contiene elementos, si es asi, ingreso los valores que contiene el campo
	 * del texto de la opcion en el arrayOpciones
	 */
	if(elementosTable > 0){
		for(var i = 0; i < elementosTable; i++){
			arrayOpciones.push($j('table[id*=table-opciones]').find('span[id*=id-cpo-opcion_' + (i + 1)+ ']').html());
		}
	}
	
	if(valDefault.length > 0){
		if("add" == tipo){
			var url = contextPath+'/eventosCorporativos/captura/capturaOpcionEvcoNoDefault.faces';
		}else{
			var url = contextPath+'/eventosCorporativos/captura/capturaOpcionEventoCorporativo.faces';
		}
	}else{
		var url = contextPath+'/eventosCorporativos/captura/capturaOpcionEventoCorporativo.faces';
	}

    var titulo = 'Captura Opción Evento Corporativo';
    //mostrarDetalle(titulo, url, 300, 900);
	
    
	$j('#noScrolling').dialog('open');
	$j('#noScrolling').dialog('option','title',titulo);
	$j('#noScrolling').dialog('option','height',620);
	$j('#noScrolling').dialog('option','width',900);
	$j('#noScrolling').prop('src',url);
	$j('#noScrolling').css('width','99%');
	$j('#noScrolling').dialog({
	     close: function(event, ui) {
			true;
		}
	});
}


function delOpcion(numOpcion){
	var elementosTable = $j('table[id*=table-opciones]').find('span[id*=id-cpo-opcion_]').length;
	
	for(var i = 0; i < elementosTable; i++){
		if(numOpcion != (i + 1)){
			arrayOpciones.push($j('table[id*=table-opciones]').find('span[id*=id-cpo-opcion_' + (i + 1)+ ']').html());
		}
	}
	$j( 'input[id*=numOpcion]' ).val(numOpcion);
}

function editaOpcion(numOpcion, tipo){
	numeroOpcion = numOpcion;
	tipoAccion = tipo;
	$j( 'input[id*=tipoOperacion]' ).val(tipo);
	$j( 'input[id*=numOpcion]' ).val(numOpcion);
	$j( 'input[id*=txt-area-dialog]' ).val($j( 'span[id*=id-cpo-opcion_' + numOpcion + ']').html());
	
	/*
	 * exploro en esta función que abre el dialogo si la tabla de opciones tiene algun elemento,
	 * se hace en esta parte porque aqui la tabla ya existe y esta rendereada con los ultimos
	 * valores que contiene
	 */
	var elementosTable = $j('table[id*=table-opciones]').find('span[id*=id-cpo-opcion_]').length;
	
	/*
	 * pregunto si la tabla contiene elementos, si es asi, ingreso los valores que contiene el campo
	 * del texto de la opcion en el arrayOpciones
	 */
	if(elementosTable > 0){
		for(var i = 0; i < elementosTable; i++){
			arrayOpciones.push($j('table[id*=table-opciones]').find('span[id*=id-cpo-opcion_' + (i + 1)+ ']').html());
			
		}
	}
	
	//determino si la opcion tiene el valor por default activado
	var valDefault = $j('table[id*=table-opciones]').find('div[id*=valor-default_' + numOpcion + ']').text();
	
	var numDefault = $j('table[id*=table-opciones]').find('div[id*=valor-default_]').length;
	
	var isDefault = $j('table[id*=table-opciones]').find('div[id*=valor-default_]').text();
	
	if(valDefault.length > 0 && isDefault.length > 0){
		var url = contextPath+'/eventosCorporativos/captura/capturaOpcionEventoCorporativo.faces';
	}
	
	if(valDefault.length == 0 && isDefault.length == 0){
		var url = contextPath+'/eventosCorporativos/captura/capturaOpcionEventoCorporativo.faces';
	}
	
	if(valDefault.length == 0 && isDefault.length > 0){
		var url = contextPath+'/eventosCorporativos/captura/capturaOpcionEvcoNoDefault.faces';
	}
	
//	$j('#detalle').data('objectEdit',object).dialog('open');
	//$('#modal-div').data('mydata', data).dialog('open');
    var titulo = 'Captura Opción Evento Corporativo';
        //mostrarDetalle(titulo, url, 300, 900);
	


    	$j('#noScrolling').dialog('open');
    	$j('#noScrolling').dialog('option','title',titulo);
    	$j('#noScrolling').dialog('option','height',650);
    	$j('#noScrolling').dialog('option','width',900);
    	$j('#noScrolling').prop('src',url);
    	$j('#noScrolling').css('width','99%');
    	$j('#noScrolling').dialog({
    	     close: function(event, ui) {
			true;
		}
	});
}

/* ************************************************* NOTAS ***************************************************** */
var arrayNotas = new Array();
var numeroNota;
var accion;
function openNotDialog(tipoAccion){
	$j( 'input[id*=tipoOp]' ).val(tipoAccion);
	var elementosTable = $j('table[id*=table-notas]').find('span[id*=id-cpo-nota_]').length;
	
	
	if(elementosTable > 0){
		for(var i = 0; i < elementosTable; i++){
			arrayNotas.push($j('table[id*=table-notas]').find('span[id*=id-cpo-nota_' + (i + 1)+ ']').html());
		}
	}
	var url = contextPath+'/eventosCorporativos/captura/capturaNotaEventoCorporativo.faces';
    var titulo = 'Captura Notas Evento Corporativo';
    //mostrarDetalle(titulo, url, 300, 900);

	$j('#noScrolling').dialog('open');
	$j('#noScrolling').dialog('option','title',titulo);
	$j('#noScrolling').dialog('option','height',530);
	$j('#noScrolling').dialog('option','width',900);
	$j('#noScrolling').prop('src',url);
	$j('#noScrolling').css('width','99%');
	$j('#noScrolling').dialog({
	     close: function(event, ui) {
			true;
		}
	});
}


function delNota(numOpcion){
	var elementosTable = $j('table[id*=table-notas]').find('span[id*=id-cpo-nota_]').length;
	
	for(var i = 0; i < elementosTable; i++){
		if(numOpcion != (i + 1)){
			arrayNotas.push($j('table[id*=table-notas]').find('span[id*=id-cpo-nota_' + (i + 1)+ ']').html());
		}
	}
	
	$j( 'input[id*=numNota]' ).val(numOpcion);
}

/* ******************************** ADJUNTOS ************************************************** */
var arrayAdjuntos = new Array();
function deleteAdjuntos(numAdjunto){
	var elementosTable = $j('table[id*=table-adjuntos]').find('span[id*=id-adj_]').length;
	
	for(var i = 0; i < elementosTable; i++){
		if(numAdjunto != (i + 1)){
			arrayAdjuntos.push($j('table[id*=table-adjuntos]').find('div[id*=id-adj_' + (i + 1)+ ']').html());
		}
	}
}

/* ************************************* VALIDACIONES ************************************************** */
function openDValidaciones(tipo){
	$j('input[id*=tipoOpVal]').val(tipo);
	
	var url = contextPath+'/eventosCorporativos/captura/capturaValidacionesEventoCorporativo.faces';
	
    $j('#noScrolling').dialog('open');
    $j('#noScrolling').dialog( "option", "title", "Captura Validaciones Evento Corporativo");
	$j('#noScrolling').dialog('option','height',280);
	$j('#noScrolling').dialog('option','width',800);
	$j('#noScrolling').prop('src',url);
	$j('#noScrolling').css('width','99%');
	$j('#noScrolling').dialog({
		close: function(event, ui){
			true;
		}
	});
}
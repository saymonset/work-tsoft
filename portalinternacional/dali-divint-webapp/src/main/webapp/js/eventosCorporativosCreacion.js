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

//CKEDITOR.disableAutoInline = true; 
//CKEDITOR.readOnly = false;

//defino un array global que me permite guardar en forma temporal las opciones que se van mostrando en la tabla
var arrayOpciones = new Array();
var numeroOpcion;
var tipoAccion;

function openDialogOptions(tipo){
	$j( 'input[id*=tipoOperacion]' ).val(tipo);
	/*
	 * exploro en esta función que abre el dialogo si la tabla de opciones tiene algun elemento,
	 * se hace en esta parte porque aqui la tabla ya existe y esta rendereada con los ultimos
	 * valores que contiene
	 */
	var elementosTable = $j('table[id*=table-opciones]').find('div[id*=id-cpo-opcion_]').length;
	
	//determino si la opcion tiene el valor por default activado
	var valDefault = $j('table[id*=table-opciones]').find('div[id*=valor-default]').text();
	
	/*
	 * pregunto si la tabla contiene elementos, si es asi, ingreso los valores que contiene el campo
	 * del texto de la opcion en el arrayOpciones
	 */
	if(elementosTable > 0){
		for(var i = 0; i < elementosTable; i++){
			arrayOpciones.push($j('table[id*=table-opciones]').find('div[id*=id-cpo-opcion_' + (i + 1)+ ']').html());
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

function insertarValor()
{
	$j( 'input[id*=txtHtmlTextOpc]'  ).val( $j( 'textarea[id*=txtOpciones]' ).val());
}


function cierraDialogOptions(){
	//lleno los hiddens con los valores capturados en el dialogo
	if($j( 'input[id*=validacion]'  ).val() == "true")
	{
	$j( 'input[id*=txtHtmlTextOpc]'  ).val( $j( 'textarea[id*=txtOpciones]' ).val());
	
	//para el valor del campo Default determino si el usuario selecciono el campo
	//$("#checkbox").is(':checked')
	
	var campoDefault = $j( 'input[id*=chkDefault]' ).is(' :checked' );
	if(campoDefault){
		$j( 'input[id*=chkDefaultOpc]'   ).val(true);
	}else{
		$j( 'input[id*=chkDefaultOpc]'   ).val(false);
	}
	$j( 'input[id*=txtFechaLimOpc]'  ).text( $j( 'input[id*=fecha-cli]'      ).val());
	$j( 'input[id*=txtFechaIndOpc]'  ).text( $j( 'input[id*=fecha-ind]'      ).val());
	$j( 'input[id*=txtFechaPagoOpc]' ).text( $j( 'input[id*=fecha-pago]'     ).val());
	
	//ingreso los valores en los campos hidden del parent que mapean las propiedades del controller
	parent.$j( 'input[id$=txt-area-dialog]'   ).val($j( 'input[id*=txtHtmlTextOpc]'  ).val());
	parent.$j( 'input[id*=chk-dialog]'        ).val($j( 'input[id*=chkDefaultOpc]'   ).val());
	parent.$j( 'input[id*=fecha-lim-dialog]'  ).val($j( 'input[id*=txtFechaLimOpc]'  ).text());
	parent.$j( 'input[id*=fecha-Ind-dialog]'  ).val($j( 'input[id*=txtFechaIndOpc]'  ).text());
	parent.$j( 'input[id*=fecha-Pago-dialog]' ).val($j( 'input[id*=txtFechaPagoOpc]' ).text());
	
	//cierro el dialogo
		parent.$j('#noScrolling').dialog('close');
	parent.$j("input[id$='controlOpciones']").click(); 
	}
}


function asignarHtml(){
	var tipoOpera = $j('input[id*=capturaEdicion]').val();
	/*
	 * obtengo el numero total de elementos div con id = id-cpo-opcion_, esto con el proposito de tener
	 * el valor del indice actual que se va a renderear en el div de la tabla
	 * 
	 */

	if(tipoOpera == "captura"){
	var numDiv = $j('table[id*=table-opciones]').find('div[id*=id-cpo-opcion_]').length;
	$j( 'div[id*=id-cpo-opcion_' + numDiv +']' ).append($j( 'input[id$=txt-area-dialog]' ).val());
	/*
	 * para no perder los elementos que se capturaron inicialmente durante el rerender de la tabla,
	 * recorro el array en donde los tengo almacenados temporalmente, los reinserto en la tabla
	 */
	if(arrayOpciones.length > 0){
		$j.each(arrayOpciones,function(indice,valor){
			if("edit" == tipoAccion){
				if(numeroOpcion == (indice + 1)){
					$j( 'div[id*=id-cpo-opcion_' + numDiv +']' ).empty();
					$j( 'div[id*=id-cpo-opcion_' + numeroOpcion +']' ).append($j( 'input[id$=txt-area-dialog]' ).val());
				}else{
					$j( 'div[id*=id-cpo-opcion_' + (indice + 1) +']' ).append(valor);
				}
			}else{
				$j( 'div[id*=id-cpo-opcion_' + (indice + 1) +']' ).append(valor);
			}
		});
	}
	
	$j('span[id*=resumenOpciones]').show();
	
	//reseteo el arrayOpciones que almacena temporalmente los valores anteriores
	arrayOpciones.length = 0;
	numeroOpcion = 0;
	tipoAccion = "";
	}else{
		/*
		 * obtengo el numero total de elementos div con id = id-cpo-opcion_, esto con el proposito de tener
		 * el valor del indice actual que se va a renderear en el div de la tabla
		 */

		var numDiv = $j('table[id*=table-opciones]').find('span[id*=id-cpo-opcion_]').length;
		$j( 'span[id*=id-cpo-opcion_' + numDiv +']' ).append($j( 'input[id$=txt-area-dialog]' ).val());
		/*
		 * para no perder los elementos que se capturaron inicialmente durante el rerender de la tabla,
		 * recorro el array en donde los tengo almacenados temporalmente, los reinserto en la tabla
		 */
		if(arrayOpciones.length > 0){
			$j.each(arrayOpciones,function(indice,valor){
				if("edit" == tipoAccion){
					if(numeroOpcion == (indice + 1)){
						$j( 'span[id*=id-cpo-opcion_' + numDiv +']' ).empty();
						$j( 'span[id*=id-cpo-opcion_' + numeroOpcion +']' ).append($j( 'input[id$=txt-area-dialog]' ).val());
					}else{
						$j( 'span[id*=id-cpo-opcion_' + (indice + 1) +']' ).append(valor);
					}
				}else{
					$j( 'span[id*=id-cpo-opcion_' + (indice + 1) +']' ).append(valor);
				}
			});
		}
		
		$j('span[id*=resumenOpciones]').show();
		
		//reseteo el arrayOpciones que almacena temporalmente los valores anteriores
		arrayOpciones.length = 0;
		numeroOpcion = 0;
		tipoAccion = "";
	}
}

function deleteOpcion(numOpcion){
	var elementosTable = $j('table[id*=table-opciones]').find('div[id*=id-cpo-opcion_]').length;
	
	for(var i = 0; i < elementosTable; i++){
		if(numOpcion != (i + 1)){
			arrayOpciones.push($j('table[id*=table-opciones]').find('div[id*=id-cpo-opcion_' + (i + 1)+ ']').html());
		}
	}
	$j( 'input[id*=numOpcion]' ).val(numOpcion);
}

function setTextOpciones(){
	/*
	 * para no perder los elementos que se capturaron inicialmente durante el rerender de la tabla,
	 * recorro el array en donde los tengo almacenados temporalmente, los reinserto en la tabla
	 */
	if(arrayOpciones.length > 0){
		$j.each(arrayOpciones,function(indice,valor){
			$j( 'div[id*=id-cpo-opcion_' + (indice + 1) +']' ).append(valor);
		});
	}
	$j('span[id*=resumenOpciones]').show();
	//reseteo el arrayOpciones que almacena temporalmente los valores anteriores
	arrayOpciones.length = 0;
}

function editOption(numOpcion, tipo){
	numeroOpcion = numOpcion;
	tipoAccion = tipo;
	$j( 'input[id*=tipoOperacion]' ).val(tipo);
	$j( 'input[id*=numOpcion]' ).val(numOpcion);
	$j( 'input[id*=txt-area-dialog]' ).val($j( 'div[id*=id-cpo-opcion_' + numOpcion + ']').html());
	
	/*
	 * exploro en esta función que abre el dialogo si la tabla de opciones tiene algun elemento,
	 * se hace en esta parte porque aqui la tabla ya existe y esta rendereada con los ultimos
	 * valores que contiene
	 */
	var elementosTable = $j('table[id*=table-opciones]').find('div[id*=id-cpo-opcion_]').length;
	
	/*
	 * pregunto si la tabla contiene elementos, si es asi, ingreso los valores que contiene el campo
	 * del texto de la opcion en el arrayOpciones
	 */
	if(elementosTable > 0){
		for(var i = 0; i < elementosTable; i++){
			arrayOpciones.push($j('table[id*=table-opciones]').find('div[id*=id-cpo-opcion_' + (i + 1)+ ']').html());
			
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

/************************************************************* SECCION DE NOTAS ***********************************************************************************/

//defino un array global que me permite guardar en forma temporal las opciones que se van mostrando en la tabla
var arrayNotas = new Array();
var numeroNota;
var accion;

function openNotasDialog(tipoAccion){
	$j( 'input[id*=tipoOp]' ).val(tipoAccion);
	var elementosTable = $j('table[id*=table-notas]').find('div[id*=id-cpo-nota_]').length;
	
	
	if(elementosTable > 0){
		for(var i = 0; i < elementosTable; i++){
			arrayNotas.push($j('table[id*=table-notas]').find('div[id*=id-cpo-nota_' + (i + 1)+ ']').html());
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

function cierraDialogNotas(){
	$j("input[id$='validaNotas']").click();
	cadena=$j("textarea[id*=txtNota]").val().replace(/<[^>]*>/g,"").replace(/(&nbsp;)*/g,"");

	if(cadena.trim() != "")
	{
	//lleno los hiddens con los valores capturados en el dialogo
	$j( 'input[id*=htmlTxtNota]'  ).val( $j( 'textarea[id*=txtNota]' ).val());
	
	//ingreso los valores en los campos hidden del parent que mapean las propiedades del controller
	parent.$j( 'input[id$=txt-area-nota]'   ).val($j( 'input[id*=htmlTxtNota]'  ).val());
	
	//cierro el dialogo
	parent.$j('#noScrolling').dialog('close');
	parent.$j("input[id$='controlNotas']").click(); 
	}
}

function editNota(numOpcion, tipo){
	numeroNota = numOpcion;
	accion = tipo;
	$j( 'input[id*=tipoOp]' ).val(accion);
	$j( 'input[id*=numOpcion]' ).val(numeroNota);
	$j( 'input[id*=txt-area-nota]' ).val($j( 'div[id*=id-cpo-nota_' + numeroNota + ']').html());
	
	/*
	 * exploro en esta función que abre el dialogo si la tabla de opciones tiene algun elemento,
	 * se hace en esta parte porque aqui la tabla ya existe y esta rendereada con los ultimos
	 * valores que contiene
	 */
	var elementosTable = $j('table[id*=table-notas]').find('div[id*=id-cpo-nota_]').length;
	
	/*
	 * pregunto si la tabla contiene elementos, si es asi, ingreso los valores que contiene el campo
	 * del texto de la opcion en el arrayOpciones
	 */
	if(elementosTable > 0){
		for(var i = 0; i < elementosTable; i++){
			arrayNotas.push($j('table[id*=table-notas]').find('div[id*=id-cpo-nota_' + (i + 1)+ ']').html());
			
		}
	}
	
	var url = contextPath+'/eventosCorporativos/captura/capturaNotaEventoCorporativo.faces';
    $j('#noScrolling').dialog('open');
	$j('#noScrolling').dialog('option','height',530);
	$j('#noScrolling').dialog('option','width',900);
	$j('#noScrolling').prop('src',url);
	$j('#noScrolling').css('width','99%');
	$j('#noScrolling').dialog({
		close: function(event, ui){
			true;
		}
	});
}

function populateTableNotes(){
	var tipoOpera = $j('input[id*=capturaEdicion]').val();
	/*
	 * obtengo el numero total de elementos div con id = id-cpo-opcion_, esto con el proposito de tener
	 * el valor del indice actual que se va a renderear en el div de la tabla
	 */

	if(tipoOpera == "captura"){
	var numDiv = $j('table[id*=table-notas]').find('div[id*=id-cpo-nota_]').length;
	$j( 'div[id*=id-cpo-nota_' + numDiv +']' ).append($j( 'input[id$=txt-area-nota]' ).val());
	/*
	 * para no perder los elementos que se capturaron inicialmente durante el rerender de la tabla,
	 * recorro el array en donde los tengo almacenados temporalmente, los reinserto en la tabla
	 */
	if(arrayNotas.length > 0){
		$j.each(arrayNotas,function(indice,valor){
			if("edit" == accion){
				if(numeroNota == (indice + 1)){
					$j( 'div[id*=id-cpo-nota_' + numDiv +']' ).empty();
					$j( 'div[id*=id-cpo-nota_' + numeroNota +']' ).append($j( 'input[id$=txt-area-nota]' ).val());
				}else{
					$j( 'div[id*=id-cpo-nota_' + (indice + 1) +']' ).append(valor);
				}
			}else{
				$j( 'div[id*=id-cpo-nota_' + (indice + 1) +']' ).append(valor);
			}
		});
	}
	
	$j('span[id*=resumenNotas]').show();
	
	//reseteo el arrayOpciones que almacena temporalmente los valores anteriores
	arrayNotas.length = 0;
	numeroNota = 0;
	accion = "";
	}else{
		var numDiv = $j('table[id*=table-notas]').find('span[id*=id-cpo-nota_]').length;
		$j( 'span[id*=id-cpo-nota_' + numDiv +']' ).append($j( 'input[id$=txt-area-nota]' ).val());
		/*
		 * para no perder los elementos que se capturaron inicialmente durante el rerender de la tabla,
		 * recorro el array en donde los tengo almacenados temporalmente, los reinserto en la tabla
		 */
		if(arrayNotas.length > 0){
			$j.each(arrayNotas,function(indice,valor){
				if("edit" == accion){
					if(numeroNota == (indice + 1)){
						$j( 'span[id*=id-cpo-nota_' + numDiv +']' ).empty();
						$j( 'span[id*=id-cpo-nota_' + numeroNota +']' ).append($j( 'input[id$=txt-area-nota]' ).val());
					}else{
						$j( 'span[id*=id-cpo-nota_' + (indice + 1) +']' ).append(valor);
					}
				}else{
					$j( 'span[id*=id-cpo-nota_' + (indice + 1) +']' ).append(valor);
				}
			});
		}
		
		$j('span[id*=resumenNotas]').show();
		
		//reseteo el arrayOpciones que almacena temporalmente los valores anteriores
		arrayNotas.length = 0;
		numeroNota = 0;
		accion = "";
	}
}

function deleteNota(numOpcion){
	var elementosTable = $j('table[id*=table-notas]').find('div[id*=id-cpo-nota_]').length;
	
	for(var i = 0; i < elementosTable; i++){
		if(numOpcion != (i + 1)){
			arrayNotas.push($j('table[id*=table-notas]').find('div[id*=id-cpo-nota_' + (i + 1)+ ']').html());
		}
	}
	
	$j( 'input[id*=numNota]' ).val(numOpcion);
}

function setTextNotas(){
	/*
	 * para no perder los elementos que se capturaron inicialmente durante el rerender de la tabla,
	 * recorro el array en donde los tengo almacenados temporalmente, los reinserto en la tabla
	 */
	if(arrayNotas.length > 0){
		$j.each(arrayNotas,function(indice,valor){
			$j( 'div[id*=id-cpo-nota_' + (indice + 1) +']' ).append(valor);
		});
	}
	$j('span[id*=resumenNotas]').show();
	//reseteo el arrayOpciones que almacena temporalmente los valores anteriores
	arrayNotas.length = 0;
}

function enviar() {	
	//capturo en los elementos tipo hidden los valores del cuerpo y del pie del evento corporativo
	$j('input[id*=cuerpoEvento_htmlH]').val($j('#cuerpoEvco').val());
	$j('input[id*=piePagina_htmlH]').val($j('#piePagina').val());
}

/************************************************************* SECCION DE NOTIFICACIONES ****************************************************/

function capturaExitosa()
{
	console.log($j('input[id*=capturaCorrecta]').val());
	if($j('input[id*=capturaCorrecta]').val() == 'true')
	{
		var url = contextPath+'/eventosCorporativos/consultaEventosCorporativosIndeval.faces';
	    url += '?return=' + true 
		$j(location).attr('href',url); 
		//"/dali-divint/eventosCorporativos/consultaEventosCorporativosIndeval.faces"
	}
	else
	{
		$j("[id*=botonGuardarEvento]").prop("disabled",false);
		$j( "[id*=tipoEvento]" ).focus();
		//document.getElementById("cuerpoEvco").focus();
	}
}

//defino un array global que me permite guardar en forma temporal las opciones que se van mostrando en la tabla
var arrayNotificaciones = new Array();
var arrayDiasSemana = new Array();
var accionNotificacion;
var numNotificacion;
var tipoAccion;

function openNotificaciones(tipo){
	$j('input[id*=tipoO]').val(tipo);
	
	var url = contextPath+'/eventosCorporativos/captura/notificacionesEventoCorporativo.faces';
	
    $j('#noScrolling').dialog('open');
    $j('#noScrolling').dialog( "option", "title", "Captura Notificaciones Evento Corporativo");
	$j('#noScrolling').dialog('option','height',625);
	$j('#noScrolling').dialog('option','width',950);
	$j('#noScrolling').prop('src',url);
	$j('#noScrolling').css('width','99%');
	$j('#noScrolling').dialog({
		close: function(event, ui){
				true;
			}
	});
}

function sendDataNotificacion(){

	var validaPesatanas=false;
	if($j( 'input[id*=validacion]'  ).val() == "true")
	{
		//identifico la opcion del acordeon que estoy eligiendo
		var opcion = $j('div[id*=accordion]').accordion('option','active');
		
		//ingreso el numero de opcion que estoy eligiendo del componente acordeon
		parent.$j('input[id*=numOpcAcc]').val(opcion);
		
		//determino en base a la opcion lo que voy a hacer
		switch(opcion){
		
		//Elige la pestaña de configuracion en minutos
		case 0:
			$j('input[id*=minutosSeccMin]').val($j('input[id*=id-minutos]').val());
			//paso el valor al padre
			if($j('input[id*=minutosSeccMin]').val() == "" )
			{
				$j('#validacionCron').css('display','block');
				$j('#validacionCron').text("Debe llenar los campos de la pestaña seleccionada");
				validaPesatanas=true;
			}else if($j('input[id*=minutosSeccMin]').val() == "0" || !($j.isNumeric($j('input[id*=minutosSeccMin]').val()) && parseInt($j('input[id*=minutosSeccMin]').val()) >= 30 )){
				$j('#validacionCron').css('display','block');
				$j('#validacionCron').text("Los minutos deben ser iguales o mayores a 30");
				validaPesatanas=true;
			}else{
				parent.$j( 'input[id$=minSeccMin]'   ).val($j( 'input[id*=minutosSeccMin]'  ).val());
			}
				break;
			
		//Elige la pestaña de configuracion en horas
		case 1:
			//verifico cual de las dos opciones de configuración en minutos se eligio
			
			if($j('input[id*=chk-cada]').is(':checked'))
			{
				$j('input[id*=horasSeccMin]').val($j('input[name*=txt-cadaCap]').val());
				if($j('input[id*=horasSeccMin]').val() == ""){
					$j('#validacionCron').css('display','block');
					$j('#validacionCron').text("Debe llenar los campos de la pestaña seleccionada");
					validaPesatanas=true;
				}else if ($j('input[id*=horasSeccMin]').val() == "0" || !($j.isNumeric($j('input[id*=horasSeccMin]').val()) && parseInt($j('input[id*=horasSeccMin]').val()) > 0 )  ){
					$j('#validacionCron').css('display','block');
					$j('#validacionCron').text("El intervalo de horas debe ser mayor a cero");
					validaPesatanas=true;
				}else{
					parent.$j('input[id*=hrsSeccMin]').val($j('input[id*=horasSeccMin]').val());
					//ingreso el tipo de configuracion
					parent.$j('input[id*=config]').val('hora');
				}
			}
			else
			{
				$j('input[id*=horasSeccMin]').val($j('input[name*=desde-hora]').val());
				$j('input[id*=minutosSeccMin]').val($j('input[name*=desde-minutos]').val());
				//paso el valor al padre
				if($j('input[id*=horasSeccMin]').val() == "" || $j('input[id*=minutosSeccMin]').val() == "")
				{
					$j('#validacionCron').css('display','block');
					$j('#validacionCron').text("Debe llenar los campos de la pestaña seleccionada");
					validaPesatanas=true;
				}
				else
				{
					parent.$j('input[id*=hrsSeccMin]').val($j('input[id*=horasSeccMin]').val());
					parent.$j('input[id*=minSeccMin]').val($j('input[id*=minutosSeccMin]').val());
					//ingreso el tipo de configuracion
					parent.$j('input[id*=config]').val('horasMin');
				}
			}
			
			break;
			
		//Elige la pestaña de configuracion en dias
		case 2:
			//verifico cual de las dos opciones de configuracion en dias se ha seleccionado
			
			if($j('input[id*=chk-dias-diario]').is(':checked')){
				$j('input[id*=idNumDias]').val($j('input[id*=txt-semana-diario]').val());
				if($j('input[id*=idNumDias]').val() == "" )
				{
					$j('#validacionCron').css('display','block');
					$j('#validacionCron').text("Debe llenar los campos de la pestaña seleccionada");
					validaPesatanas=true;
				}else if($j('input[id*=idNumDias]').val() == "0" || !($j.isNumeric($j('input[id*=idNumDias]').val()) && parseInt($j('input[id*=idNumDias]').val()) > 0 )){
					$j('#validacionCron').css('display','block');
					$j('#validacionCron').text("Los días deben ser mayores a cero");
					validaPesatanas=true;
				}else if($j('input[name*=hora-diario]').val() == "" || $j('input[name*=minutos-diario]').val() == ""){
					$j('#validacionCron').css('display','block');
					$j('#validacionCron').text("Debe llenar los campos de la pestaña seleccionada");
					validaPesatanas=true;			
				
				}else{
					parent.$j('input[id*=config]').val('dia');
					parent.$j('input[id*=numDias]').val($j('input[id*=txt-semana-diario]').val());
					parent.$j('input[id*=hrsSeccMin]').val($j('input[name*=hora-diario]').val());
					parent.$j('input[id*=minSeccMin]').val($j('input[name*=minutos-diario]').val());
					validaPesatanas=false;
				}
			}else
			{
				//mapeo solo la propiedad que distingue el tipo de transaccion	
				
					parent.$j('input[id*=config]').val('todos');
	
					if($j('input[name*=hora-diario]').val() == "" || $j('input[name*=minutos-diario]').val() == "")
					{
						validaPesatanas=true;
					}
					else
						{
						validaPesatanas=false;
						}
			
	
			if(validaPesatanas == true )
			{
				
				$j('#validacionCron').css('display','block');
				$j('#validacionCron').text("Debe llenar los campos de la pestaña seleccionada");
			}
			else
			{
				//$j('input[id*=hrDias]').val($j('input[name*=hora-diario]').val());
				//$j('input[id*=minDias]').val($j('input[name*=minutos-diario]').val());
			//mapeo las propiedades al padre
			//parent.$j('input[id*=hrsSeccMin]').val($j('input[id*=hrDias]').val());
			//parent.$j('input[id*=minSeccMin]').val($j('input[id*=minDias]').val());
				parent.$j('input[id*=hrsSeccMin]').val($j('input[name*=hora-diario]').val());
				parent.$j('input[id*=minSeccMin]').val($j('input[name*=minutos-diario]').val());
			}
		}
			
			break;
		
		//Elige la pestaña de configuracion por semana
		case 3:
			//obtengo los dias de la semana seleccionados
			var diaSelecionado=false;
			$j.each($j("input[name*=dias-semana]:checked"), function() {
				  arrayDiasSemana.push($j(this).val());
				  diaSelecionado=true;
			});
			
			parent.$j('input[id*=diasSemanaSel]').val(arrayDiasSemana);
			
			$j('input[id*=hrDias]').val($j('input[name*=hora-semana]').val());
			$j('input[id*=minDias]').val($j('input[name*=minutos-semana]').val());
			if(diaSelecionado == false || $j('input[id*=hrDias]').val() == "" || $j('input[id*=minDias]').val() == "")
			{
				$j('#validacionCron').css('display','block');
				$j('#validacionCron').text("Debe llenar los campos de la pestaña seleccionada");
				validaPesatanas=true;
			}
			else
			{
			
				//mapeo las propiedades al padre
				parent.$j('input[id*=hrsSeccMin]').val($j('input[id*=hrDias]').val());
				parent.$j('input[id*=minSeccMin]').val($j('input[id*=minDias]').val());
			}
			break;
		
		//Elige la pestaña de configuracion por mes
		case 4:
			//verifico cual de las dos opciones eligio el usuario
			$j('input[id*=diaMes]').val($j('input[id*=txt-dia-mes]').val());
			$j('input[id*=mesMes]').val($j('input[id*=txt-mes-mes]').val());
				
			$j('input[id*=hrDias]').val($j('input[name*=hora-mes]').val());
			$j('input[id*=minDias]').val($j('input[name*=minutos-mes]').val());
			
			if( $j('input[id*=hrDias]').val() == "" || $j('input[id*=minDias]').val() == "" || $j('input[id*=diaMes]').val() == "" || $j('input[id*=mesMes]').val() == "")
			{
				$j('#validacionCron').css('display','block');
				$j('#validacionCron').text("Debe llenar los campos de la pestaña seleccionada");
				validaPesatanas=true;
			}
			else if(!($j.isNumeric($j('input[id*=diaMes]').val()) && parseInt($j('input[id*=diaMes]').val()) > 0 && parseInt($j('input[id*=diaMes]').val()) <=31 )){ 
				
				$j('#validacionCron').css('display','block');
				$j('#validacionCron').text("valor maximo permitido en días 31, revisa tu calendario ");
				validaPesatanas=true;
			}else if(!($j.isNumeric($j('input[id*=mesMes]').val()) && parseInt($j('input[id*=mesMes]').val()) > 0 && parseInt($j('input[id*=mesMes]').val()) <=12 )){ 
				$j('#validacionCron').css('display','block');
				$j('#validacionCron').text("Valor maximo permitido en meses: 12");
				validaPesatanas=true;
			}
			else if(!($j.isNumeric($j('input[id*=hrDias]').val()) && parseInt($j('input[id*=hrDias]').val()) >= 0 && parseInt($j('input[id*=hrDias]').val()) < 24 )){ 
				$j('#validacionCron').css('display','block');
				$j('#validacionCron').text("Las horas son desde 0 hasta 23 ");
				validaPesatanas=true;
			}
			else if(!($j.isNumeric($j('input[id*=minDias]').val()) && parseInt($j('input[id*=minDias]').val()) >= 0 && parseInt($j('input[id*=minDias]').val()) < 60 )){ 
				$j('#validacionCron').css('display','block');
				$j('#validacionCron').text("Las minutos son desde 0 hasta 59 ");
				validaPesatanas=true;
			}else{
				parent.$j('input[id*=diaM]').val($j('input[id*=diaMes]').val());
				parent.$j('input[id*=mesM]').val($j('input[id*=mesMes]').val());
				
				//mapeo las propiedades al padre
				parent.$j('input[id*=hrsSeccMin]').val($j('input[id*=hrDias]').val());
				parent.$j('input[id*=minSeccMin]').val($j('input[id*=minDias]').val());
			}
			
			break;
		}
		
		if(validaPesatanas == false)
		{
			$j('input[id*=idTxtDestinatario]').val($j('select[id*=id-txt-destinatario]').val());
			$j('input[id*=idTextoNotificacion]').val($j('input[id*=id-texto-Notificacion]').val());
			$j('input[id*=idTxtFechaInicio]').text($j('input[id*=id-txt-fecha-inicio]').val());
			$j('input[id*=idTxtFechaFinal]').text($j('input[id*=id-txt-fecha-final]').val());
			
			//ingreso los valores en los campos hidden del parent que mapean las propiedades del controller
			parent.$j( 'input[id*=txtDestinatario]'   ).val($j( 'input[id*=idTxtDestinatario]'   ).val());
			parent.$j( 'input[id*=txtNotificacion]'  ).val($j( 'input[id*=idTextoNotificacion]'  ).val());
			parent.$j( 'input[id*=txtFechaInicio]'  ).val($j( 'input[id*=idTxtFechaInicio]'  ).text());
			parent.$j( 'input[id*=txtFechaFin]'  ).val($j( 'input[id*=idTxtFechaFinal]' ).text());
			parent.$j( 'input[id*=peridiocidad]' ).val(opcion);
			
			//cierro el dialogo
			parent.$j('#noScrolling').dialog('close');
			parent.$j("input[id$='controlNotificacion']").click();
		}
	
	}
	else
	{
		$j('#validacionCron').css("display","none");
	}

}

function showPanelNotificacion(){

	$j('span[id*=resumenNotificaciones]').show();
}

function deleteNotificacion(numNotificacion){
	$j( 'input[id*=numNotificacion]' ).val(numNotificacion);
}

function editNotificacion(numeroNotificacion, numOpcAcc, tipo){
	numNotificacion = numeroNotificacion;
	tipoAccion = tipo;
	
	$j('input[id*=tipoO]').val(tipoAccion);
	$j('input[id*=numNotificacion]').val(numNotificacion);
	$j('input[id*=peridiocidad]').val(numOpcAcc);
	var url = contextPath+'/eventosCorporativos/captura/notificacionesEventoCorporativo.faces';

    $j('#noScrolling').dialog('open');
    $j('#noScrolling').dialog( "option", "title", "Captura Notificaciones Evento Corporativo");
	$j('#noScrolling').dialog('option','height',625);
	$j('#noScrolling').dialog('option','width',950);
	$j('#noScrolling').prop('src',url);
	$j('#noScrolling').css('width','99%');
	$j('#noScrolling').dialog({
		close: function(event, ui){
			true;
		}
	});
}

/*************************************************** VALIDACIONES EVENTO CORPORATIVO **********************************/
function openDialogValidaciones(tipo){
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

function sendDataValidacion(){
	//mapeo el valor de los elementos capturados a los campos hidden
	if($j( 'input[id*=validacion]'  ).val() == "true")
	{
		$j('input[id*=validacionSelect]').val($j('select[id*=id-validacion]').val());
		$j('input[id*=operadorSelect]').val($j('select[id*=id-operador]').val());
		$j('input[id*=valorCantidad]').val($j('input[id*=id-valor]').val());
		
		//los mapeo al padre
		parent.$j('input[id*=validacionVal]').val($j('input[id*=validacionSelect]').val());
		parent.$j('input[id*=operadorVal]').val($j('input[id*=operadorSelect]').val());
		parent.$j('input[id*=cantidadValidacion]').val($j('input[id*=valorCantidad]').val());
		
		//cierro el dialogo
		parent.$j('#noScrolling').dialog('close');
		parent.$j("input[id$=controlValidaciones]").click(); 
	}
}

function deleteValidacion(numValidacion){
	$j('input[id*=numeroValidacion]').val(numValidacion);
}

function showPanelValidaciones(){
	$j('span[id*=resumenValidaciones]').show();
}


function checkedCada(){
	if($j('input[id*=chk-cada').is(':checked')){
		$j('input[id*=chk-desde]').attr('checked',false);
	}
}

function checkedDesde(){
	if($j('input[id*=chk-desde]').is(':checked')){
		$j('input[id*=chk-cada]').attr('checked',false);
	}
}

function uncheckSemana(){
	if($j('input[id*=chk-dias-diario]').is(':checked')){
		$j('input[id*=chk-sem-diario]').attr('checked',false);
	}
}

function uncheckDias(){
	if($j('input[id*=chk-sem-diario]').is(':checked')){
		$j('input[id*=chk-dias-diario]').attr('checked',false);
	}
}

function checkDias(){
	$j('input[id*=chk-sem-diario]').attr('checked',false);
	$j('input[id*=chk-dias-diario]').attr('checked',true);

}

function uncheckedDiaMes(){
	if($j('input[id*=chk-dias-meses]').is(':checked')){
		$j('input[id*=chk-dia-mes]').attr('checked',false);
	}
}

function uncheckedDiasMeses(){
	if($j('input[id*=chk-dia-mes]').is(':checked')){
		$j('input[id*=chk-dias-meses]').attr('checked',false);
	}
}
	

var tituloRegistrarListaDistribucion = "Registrar Lista de Distribución";
var tituloModificarListaDistribucion = "Modificar Lista de Distribución";

var tituloRegistrarPersonas = "Registrar Personas";
var tituloModificarPersonas = "Modificar Personas";

var tituloRegistrarGrupos = "Registrar Grupos";
var tituloModificarGrupos = "Modificar Grupos";

/**
 * Inicializa el documento
 */
$j(document).on('ready', function() {
	$j('#ventanaModal').dialog({
		autoOpen : false,
		height : 600,
		maxheight : 600,
		maxWidth : 960,
		modal : true,
		resizable : false,
		width : 960,
		zIndex : 10003
	});
});

/**
 * Muestra una ventana modal.
 * 
 * @param titulo
 *            Título de la ventana
 * @param url
 *            URL de la ventana modal
 * @param height
 *            Alto de la venta modal
 * @param width
 *            Ancho de la ventana modal
 */
function mostrarVentanaModal(titulo, url, height, width) {
	$j('#ventanaModal').dialog('open');
	$j('#ventanaModal').dialog('option','title',titulo);
	$j('#ventanaModal').dialog('option','height',height);
	$j('#ventanaModal').dialog('option','width',width);
	$j('#ventanaModal').dialog('option','position','top');
	$j('#ventanaModal').prop('src',url);
	$j('#ventanaModal').css('width','99%');
	
	$j('#ventanaModal').dialog({
	     close: function(event, ui) {
//	     $j("input[id$='control_bitacora_actual']").click();    
	     }
	 });
	
	$j('.ui-widget-overlay') .on("click", function() {
	    $j('#ventanaModal').dialog('close');
	});

}

/**
 * Cierra una ventana modal.
 */
function cerrarVentaModal(resultadoRegMod) {
	//cierro el dialogo
	parent.$j("input[id$='resultadoRegMod']").attr('value', resultadoRegMod);
	parent.$j('#ventanaModal').dialog('close');
	parent.$j("input[id$='btnConsultaAltaModificacion']").click();
}

/**
 * Registra o modifica una lista de distribución.
 * @param idListaDistribucion Id de la lista de distribución en caso de modificar.
 */
function registrarModificarListaDistribucion(idListaDistribucion) {
	var titulo = null;
	var url = contextPath+'/eventosCorporativos/catalogos/altaModificacionListaDistribucion.faces';

    if(idListaDistribucion != null) {
    	url += '?idListaDistribucion=' + idListaDistribucion;
    	titulo = tituloModificarListaDistribucion;
    }
    else {
    	titulo = tituloRegistrarListaDistribucion;
    }
    mostrarVentanaModal(titulo, url, 500, 960);
}

/**
 * Registra o modifica una persona.
 * @param idPersona Id de la lista de distribución en caso de modificar.
 */
function registrarModificarPersona(idPersona) {
    var titulo = null;
	var url = contextPath+'/eventosCorporativos/catalogos/altaModificacionPersonas.faces';
    if(idPersona != null) {    	
    	url += '?idPersona=' + idPersona;
    	titulo = tituloModificarPersonas;
    }
    else {
    	titulo = tituloRegistrarPersonas;
    }
    mostrarVentanaModal(titulo, url, 500, 960);
}

/**
 * Registra o modifica una persona.
 * @param idPersona Id de la lista de distribución en caso de modificar.
 */
function registrarModificarGrupo(idGrupo) {
    var titulo = null;
	var url = contextPath+'/eventosCorporativos/catalogos/altaModificacionGrupos.faces';
    if(idGrupo != null) {    	
    	url += '?idGrupo=' + idGrupo;
    	titulo = tituloModificarGrupos;
    }
    else {
    	titulo = tituloRegistrarGrupos;
    }
    mostrarVentanaModal(titulo, url, 500, 960);
}
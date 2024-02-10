checkRegresaPosicion = new Array();
checkLibera = new Array();
checkLiberaParcialidad = new Array();
checkAutoriza = new Array();
checkCancela = new Array();
checkCancelaParcialidad = new Array();
checkConfirma = new Array();
checkHabilitar = new Array();
checkRegresa = new Array();
checkAsigna = new Array();

iRegresaPosicion = 0;
iLibera = 0;
iLiberaParcialidad = 0;
iAutoriza = 0;
iCancela = 0;
iCancelaParcialidad = 0;
iConfirma = 0;
iHabilitar = 0;
iRegresa = 0;
iAsigna = 0;

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

function mostrarDetalle(titulo,url){
	$j('#detalle').dialog('option','title',titulo);	
	$j('#detalle').dialog('option','height',650);
	$j('#detalle').dialog('option','width',950);	
	$j('#detalle').dialog('open');
	$j('#detalle').prop('src',url);
	$j('#detalle').css('width','99%');
	$j('.ui-widget-overlay') .on("click", function() {
	    $j('#detalle').dialog('close');
	});
}

function mostrarDetalleSettlementPartial(titulo,url){
	$j('#detalle').dialog('option','title',titulo);	
	$j('#detalle').dialog('option','height',350);
	$j('#detalle').dialog('option','width',950);	
	$j('#detalle').dialog('open');
	$j('#detalle').prop('src',url);
	$j('#detalle').css('width','99%');
	$j('.ui-widget-overlay') .on("click", function() {
	    $j('#detalle').dialog('close');
	});
}

function mostrarDetalleError(titulo,url){
	
	$j('#detalle').dialog('option','title',titulo);
	$j('#detalle').dialog('option','height',200);
	$j('#detalle').dialog('option','width',500);
	$j('#detalle').dialog('open');
	$j('#detalle').prop('src',url);
	$j('#detalle').css('width','99%');
	
	$j('.ui-widget-overlay') .on("click", function() {
	    $j('#detalle').dialog('close');
	});
}

function quitarMarca(control){
	$j('.'+control).attr('checked',false);
}

function regresaPosicionTodos(campo) {
	for (var i=0 ; i < iRegresaPosicion ; i++) {
		if (checkRegresaPosicion[i].attr('disabled') === undefined || checkRegresaPosicion[i].attr('disabled') == false) {
			checkRegresaPosicion[i].attr('checked', campo.checked);
		}
		checkLibera[i].attr('checked', false);
		checkAutoriza[i].attr('checked', false);
		checkCancela[i].attr('checked', false);
		checkConfirma[i].attr('checked', false);
		checkHabilitar[i].attr('checked', false);
		if ((typeof checkRegresa[i] !== "undefined") && (checkRegresa[i] !== null)) {
			checkRegresa[i].attr('checked', false);
		}
	}
}

function liberarTodos(campo){
		for(var i=0 ; i < iLibera ; i++ ){
			if( checkLibera[i].attr('disabled') === undefined || checkLibera[i].attr('disabled') == false ){
				checkLibera[i].attr('checked',campo.checked);
				
			}
				checkAutoriza[i].attr('checked',false);
				checkCancela[i].attr('checked',false);
                checkConfirma[i].attr('checked',false);
                checkHabilitar[i].attr('checked',false);
                if ((typeof checkRegresa[i] !== "undefined") && (checkRegresa[i] !== null)){
                	checkRegresa[i].attr('checked',false);
                }
		}
}

function liberarTodasParcialidades(campo){
	for(var i=0 ; i < iLiberaParcialidad ; i++ ){
		if( checkLiberaParcialidad[i].attr('disabled') === undefined || checkLiberaParcialidad[i].attr('disabled') == false ){
			checkLiberaParcialidad[i].attr('checked',campo.checked);
		}
//		checkCancelaParcialidad[i].attr('checked',false);
	}
}

function cancelarTodasParcialidades(campo){
	for(var i=0 ; i < iCancelaParcialidad ; i++ ){
			if( checkCancelaParcialidad[i].attr('disabled') === undefined || checkCancelaParcialidad[i].attr('disabled') == false ){
				checkCancelaParcialidad[i].attr('checked',campo.checked);
			}
			checkLiberaParcialidad[i].attr('checked',false);
		}
}

function autorizarTodos(campo){
		
		for(var i=0 ; i < iAutoriza ; i++ ){
			if( checkAutoriza[i].attr('disabled') === undefined || checkAutoriza[i].attr('disabled') == false ){
				checkAutoriza[i].attr('checked',campo.checked);
				
			}
				checkLibera[i].attr('checked',false);
				checkCancela[i].attr('checked',false);
                checkConfirma[i].attr('checked',false);
                checkHabilitar[i].attr('checked',false);
                if ((typeof checkRegresa[i] !== "undefined") && (checkRegresa[i] !== null)){
                	checkRegresa[i].attr('checked',false);
                }
		}
}

function cancelarTodos(campo){
	for(var i=0 ; i < iCancela ; i++ ){
			if( checkCancela[i].attr('disabled') === undefined || checkCancela[i].attr('disabled') == false ){
				checkCancela[i].attr('checked',campo.checked);
			}
				checkLibera[i].attr('checked',false);
				checkAutoriza[i].attr('checked',false);
                checkConfirma[i].attr('checked',false);
                checkHabilitar[i].attr('checked',false);
                if ((typeof checkRegresa[i] !== "undefined") && (checkRegresa[i] !== null)){
                	checkRegresa[i].attr('checked',false);
                }
		}
}

function confirmaTodos(campo){
	for(var i=0 ; i < iConfirma ; i++ ){
			if( checkConfirma[i].attr('disabled') === undefined || checkConfirma[i].attr('disabled') == false ){
				checkConfirma[i].attr('checked',campo.checked);
			}
				checkLibera[i].attr('checked',false);
				checkAutoriza[i].attr('checked',false);
                checkCancela[i].attr('checked',false);
                checkHabilitar[i].attr('checked',false);
                if ((typeof checkRegresa[i] !== "undefined") && (checkRegresa[i] !== null)){
                	checkRegresa[i].attr('checked',false);
                }
		}
}
function regresaTodos(campo){
	for(var i=0 ; i < iConfirma ; i++ ){
			if( checkRegresa[i].attr('disabled') === undefined || checkRegresa[i].attr('disabled') == false ){
				checkRegresa[i].attr('checked',campo.checked);
			}
				checkLibera[i].attr('checked',false);
				checkAutoriza[i].attr('checked',false);
                checkCancela[i].attr('checked',false);
                checkHabilitar[i].attr('checked',false);
                checkConfirma[i].attr('checked',false);
		}
}
function habilitarTodos(campo){
	for(var i=0 ; i < iHabilitar ; i++ ){
		if( checkHabilitar[i].attr('disabled') === undefined || checkHabilitar[i].attr('disabled') == false ){
			checkHabilitar[i].attr('checked',campo.checked);
		}
		if(checkLibera.length > 0){
			checkLibera[i].attr('checked',false);
		}
		if(checkAutoriza.length > 0){
			checkAutoriza[i].attr('checked',false);
		}
		if(checkCancela.length > 0){
            checkCancela[i].attr('checked',false);
		}
		if(checkConfirma.length > 0){
            checkConfirma[i].attr('checked',false);
		}
		if(checkRegresa.length > 0){
            if ((typeof checkRegresa[i] !== "undefined") && (checkRegresa[i] !== null)){
				 checkRegresa[i].attr('checked',false);
            }
		}
}
}

function buscarRegistros() {
}

function presentarResultados() {
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
	//$j("#divResumen").css("opacity", "0");
	//$j("#divResumen").animate({opacity:1}, 1000);
	$j("#botonEditarCriterios").css("display", "inline");
}
function detalleResultado(idOperacionesSic) {
	url = contextPath+'/divinternacional/detalleConsultaOperacionesSIC.faces';
	url += '?idOperacionesSic=' + idOperacionesSic; 
	mostrarDetalle('Detalle',encodeURI(url),'');
}

function detalleError(idOperacionesSic) {
	url = contextPath+'/divinternacional/detalleErrorConsultaOperacionesSic.faces';
	url += '?idOperacionesSic=' + idOperacionesSic;
	mostrarDetalleError('Detalle',encodeURI(url),'');
}

  function validarSiNumero(numero){
    if (!/^([0-9])*$/.test(numero))
    	$j("input[name$='folioControl']").attr('value','');
  }

function asignarTodos(campo) {
    for (var i=0 ; i < iAsigna ; i++ ) {
        if (checkAsigna[i].attr('disabled') === undefined || checkAsigna[i].attr('disabled') == false) {
        	checkAsigna[i].attr('checked', campo.checked);
        }
    }
}

function detalleSettlementPartial(folioControlParcialidad) {
	url = contextPath+'/divinternacional/detalleSettlementPartial.faces';
	url += '?folioControlParcialidad=' + folioControlParcialidad; 
	mostrarDetalleSettlementPartial('Detalle',encodeURI(url),'');
}
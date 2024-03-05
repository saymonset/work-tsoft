$j(document).on('ready',function(){
	$j('#posiciones').dialog(
			{ 
				autoOpen:false,
				height:650,
				maxheight:1200,
				maxWidth:1200,
				modal: true,
				
				width:950,
				zIndex:10003
			 }
		);
});


function buscarRegistros(){
	deshabilitarBoton('botonBuscar');
	deshabilitarBoton('botonEditarCriterios');
	$j("#botonEditarCriterios").css('display','none');
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
	habilitarBoton('botonBuscar');
	habilitarBoton('botonEditarCriterios');
}

function cambiaIconoConfirmacion(id) {
	$j("#"+id).prop("src",contextPath + "/images/exclamation.png");
}

function deshabilitarConfirmar(){
	var confirmar=document.getElementById('daliForm:matchIdOperacionesConfirmar');
	var idsConfirmar=confirmar.value;
	var divItems;
	confirmar.value="";
	if (idsConfirmar){
		var elems=idsConfirmar.split(',');
		for(var i=0; i< elems.length;i++){
			if(elems[i].length > 1){
				elemSeparado = elems[i].split('-');
				if(elemSeparado != null){
					divItems=document.getElementById("conf_"+elemSeparado[0]).childNodes;
				}else{
					divItems=document.getElementById("conf_"+elems[i]).childNodes;
					}
				for(var j=0; j<divItems.length ; j++){
					  divItems[j].disabled=true;
					  if(divItems[1].src){
					  	divItems[1].src="../images/exclamation.png";
					  }
				}
			}
		}
	}
	alert('Se confirmaron las Operaciones');
}

function deshabilitarCancelar(){
	var cancelar=document.getElementById('daliForm:matchIdOperacionesCancelar');
	var idsEliminar=cancelar.value;
	cancelar.value="";
	if (idsEliminar){
		var elems=idsEliminar.split(',');
		for(var i=0; i< elems.length;i++){
			if(elems[i].length > 1){
				var divItems=document.getElementById("canc_"+elems[i]).childNodes;
				for(var j=0; j<divItems.length ; j++){
					  divItems[j].disabled=true;
					  if(divItems[1].src)
					  	divItems[1].src="../images/cancel2.png";
				}
			}
		}
	}
	alert('Se cancelaron las Operaciones');
}

function agregaCancelar(id , skip) {
	var idOper=id.replace("chk_canc_","");
	var idsEliminar=document.getElementById('daliForm:matchIdOperacionesCancelar').value;
	if ($j("#"+id).is(':checked') || skip){
		if(skip){
			$j("#"+id).prop('checked', true);
		}
		if(idsEliminar.search(idOper) == -1 ){
			idsEliminar=idsEliminar+","+idOper;
		}
	}else{
		idsEliminar=idsEliminar.replace(","+idOper,"");
	}
	document.getElementById('daliForm:matchIdOperacionesCancelar').value=idsEliminar;
}


function limpiarMatch() {
	document.getElementById('daliForm:matchIdOperacionesConfirmar').value='';
	document.getElementById('daliForm:matchIdOperacionesCancelar').value='';
}

function agregaConfirmar(id, skip) {

	var idOper=id.replace("chk_conf_","");
	var idsConfirmar=document.getElementById('daliForm:matchIdOperacionesConfirmar').value;
	var idBoveda = document.getElementsByClassName("bovedaEfectivoClass_"+idOper)[0];
	if(idBoveda != null){
		if ($j("#"+id).is(':checked') || skip){
			if(skip){
				$j("#"+id).prop('checked', true);
			}
			if(idsConfirmar.search(idOper) == -1 ){
				idsConfirmar=idsConfirmar+","+idOper+"-"+idBoveda.value;
			}
		}else{
			idsConfirmar=idsConfirmar.replace(","+idOper+"-"+idBoveda.value,"");
		}
	}else{
		if ($j("#"+id).is(':checked') || skip){
			if(skip){
				$j("#"+id).prop('checked', true);
			}
			if(idsConfirmar.search(idOper) == -1 ){
				idsConfirmar=idsConfirmar+","+idOper;
			}					
		}else{	
			idsConfirmar=idsConfirmar.replace(","+idOper,"");		
			}	
	}
	document.getElementById('daliForm:matchIdOperacionesConfirmar').value=idsConfirmar;
}

function cambiarBoveda(id, idBoveda){
	var idOper=id.replace("chk_conf_","");
	var idsConfirmar=document.getElementById('daliForm:matchIdOperacionesConfirmar').value;
	var idBovedaSeparada;

	var idsSeparados = idsConfirmar.split(",");
	for (var i=0;i<idsSeparados.length;i++){
		if(idsSeparados[i].search(idOper) != -1){
			idBovedaSeparada = idsSeparados[i].split("-");
			idsSeparados[i] = idBovedaSeparada[0] + "-" + idBoveda.value;
			break;
		}
	}

	idsConfirmar = idsSeparados.join();
	document.getElementById('daliForm:matchIdOperacionesConfirmar').value=idsConfirmar;
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

function mostrarDetalle(titulo,url){
	$j('#posiciones').dialog('open');
	$j('#posiciones').dialog('option','title',titulo);
	$j('#posiciones').prop('src',url);
	$j('#posiciones').css('width','99%');
	$j('.ui-widget-overlay') .on("click", function() {
	    $j('#posiciones').dialog('close');
	});
}

function limpiarFechaHoraConcertacion() {
	document.getElementById('daliForm:fechaHoraCierreOperInputDate').value=null;
}
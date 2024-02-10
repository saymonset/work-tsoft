function getCambiosDestinatario(){
	var valores="";
	$j("select[id^=slc]").each(function(){
		valores= valores+$j(this).attr("id") +":"+$j(this).val()+"|";
	});
	$j("input[id*=autoriza_mensaje_H]").val(valores);
	var valor=$j("input[id*=autoriza_mensaje_H]").val();
	valor+"1";
}

function confirmaEjecutar(){ 
	var idsAutoriza="";
	var countAutoriza=0;
	var idsCancela="";
	var countCancela=0;
	$j("select[id*=slc]").each(function(){
		var id =""+$j(this).attr("id");
		var valorSelect = $j(this).val();
		if( id != 'slc_none_2' && $j(this).val() != '-1'){					
			var inicioIdHist = id.indexOf("his_")+4;
			var inicioDestino = id.indexOf("des_");
			var destino = id.substring(inicioDestino+4);
			if(valorSelect == 1){
				if(countAutoriza == 0){					
					idsAutoriza+=" "+id.substring(inicioIdHist,inicioDestino)+" - "+destino;			
				}else{
					idsAutoriza+=", "+id.substring(inicioIdHist,inicioDestino)+" - "+destino;
				}			
				countAutoriza++;
				if(countAutoriza % 10 == 0){
					idsAutoriza+="\n";
				}				
			}else if(valorSelect == 2){
				if(countCancela == 0){					
					idsCancela+=" "+id.substring(inicioIdHist,inicioDestino)+" - "+destino;			
				}else{
					idsCancela+=", "+id.substring(inicioIdHist,inicioDestino)+" - "+destino;
				}			
				countCancela++;
				if(countCancela % 10 == 0){
					idsCancela+="\n";
				}
			}
		}
	});
	if(countAutoriza > 0 || countCancela > 0){
		var resumen = "";
		if(countAutoriza >0){
				resumen = "El/Los siguiente(s) "+countAutoriza+" mensaje(s) ser\u00E1(n) Autorizado(s). Datos Autorizaci\u00F3n (Folio - Destino): "+"\n"+idsAutoriza+"\n";						
		}
		if(countCancela > 0){
			resumen+="El/Los siguiente(s) "+countCancela+" mensaje(s) ser\u00E1(n) Cancelado(s). Datos Cancelaci\u00F3n (Folio - Destino): "+"\n"+idsCancela+"\n";
		}
		resumen+="\u00bfEst\u00E1 de acuerdo?";
		return confirm(resumen);
	}
}

function limpiarTodos(){
	
	$j("input[id*=autoriza_mensaje_H]").val("");
}

//lo utiliza el comboprincipal para cambiar los demas combos
function cambiarComboAll(elem){        
	var combos = $j("select[id*=slc]").each(function(){
		var test = $j(this).children('option[value="'+elem.value+'"]');
		if(test != null && test != undefined && test.val() == elem.value){
			this.value=elem.value;
			$j(this).change();
		}
	});		 
}

function ponNombreId(ele,id,idHist,destinatario){
	$j(ele).attr('id','slc_'+id+'his_'+idHist +'des_'+destinatario);
}


function presentarTextArea(){
	$j("#divTextArea").css("display", "inline");
	$j("#divBotones").css("display", "inline");
	$j("#divBotonInicio").css("display", "none");
}

function ocultarTextArea(){
	$j("#divTextArea").css("display", "none");
	$j("#divBotones").css("display", "none");
	$j("#divBotonInicio").css("display", "inline");
}

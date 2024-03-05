
var functionSubmit = null;

var functionLoginSubmit = null;





function mostrarFirma(valueToSign,signatureFieldName,serialFieldName,userSerialFieldName,functionSubmitName,signButtonCaption){
	mostrarFirma(valueToSign,signatureFieldName,serialFieldName,userSerialFieldName,functionSubmitName,signButtonCaption,null,null);
	
}

function mostrarFirma(valueToSign,signatureFieldName,serialFieldName,userSerialFieldName,functionSubmitName,signButtonCaption,TotalOperaciones){
	mostrarFirma(valueToSign,signatureFieldName,serialFieldName,userSerialFieldName,functionSubmitName,signButtonCaption,TotalOperaciones,null);
}


function mostrarFirma(valueToSign,signatureFieldName,serialFieldName,userSerialFieldName,functionSubmitName,signButtonCaption,TotalOperaciones,parametrosExtras){

	functionSubmit=functionSubmitName;
	
	var uri=contextPath+'/firma.html?valueToSign='+valueToSign+'&signatureFieldName='+signatureFieldName+'&serialFieldName='+serialFieldName+'&userSerialFieldName='+userSerialFieldName
								     +'&functionToCloseName='+'cierraFirma';
	if(parametrosExtras!=null){
		uri=uri+'&paramsSingles=true';
	}
	if(TotalOperaciones!=null){
		uri=uri+'&TotalOperaciones='+TotalOperaciones;
	}
	
	uri=uri+'&signButtonCaption='+signButtonCaption;
	
	var res=encodeURI(uri);
	
	var clone=$j("#firmaDigital").dialog({ 
		cache: false,
		autoOpen:false,
		height:480,				
		resizable: false,
		modal : true,
		width:475,
		margin:0,		
		zIndex:10003
	 });
	 
	clone.dialog('open');
	clone.prop('src',res);	
	clone.css('width','99%');	
	
}

function cierraFirma(llamadoFirma){
	$j("#firmaDigital").dialog('close');
	if(llamadoFirma)
		window[functionSubmit]();	
}



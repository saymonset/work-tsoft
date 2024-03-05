
var functionLoginSubmit = null;


$j(document).on('ready',function(){
		
	mostrarFirmaLogin('ticketSinFirmar','ticketFirmado','firmarOperacion','Aceptar');

});







function mostrarFirmaLogin(encryptedFieldName,dencryptedFieldName,functionSubmitName,signButtonCaption){
	functionLoginSubmit=functionSubmitName;
	
	var uri=contextPath+'/firma_ticket.html?encryptedFieldName='+encryptedFieldName+'&dencryptedFieldName='+dencryptedFieldName
	  +'&functionToCloseName='+'cierraFirmaLogin'+'&signButtonCaption='+signButtonCaption;
	
	var res=encodeURI(uri);
	
	var clone=$j('#firmaDigitalLogin').dialog(
			{   cache: false,
				autoOpen:false,
				height:330,				
				resizable: false,
				modal : true,
				width:475,
				margin:0
				
			 }
		);



	clone.dialog('open');
	clone.prop('src',res);
	clone.css('width','99%');

	
}

function cierraFirmaLogin(llamadoFirma){
	$j("#firmaDigitalLogin").dialog('close');
	if(llamadoFirma)
		window[functionLoginSubmit]();	
}

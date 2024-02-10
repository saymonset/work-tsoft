function runScript(event) {
	if (event.which == 13 || event.keyCode == 13) {
		cargarCertificado();
        return false;
    }
    return true;
}
function stringToArrayBuffer(data){
     var arrBuff = new ArrayBuffer(data.length);
     var writer = new Uint8Array(arrBuff);
     for (var i = 0, len = data.length; i < len; i++) {
         writer[i] = data.charCodeAt(i);
     }
     return arrBuff;
}

function toUTF8Array(str) {
    var utf8= unescape(encodeURIComponent(str));
    var arr= new Array(utf8.length);
    for (var i= 0; i<utf8.length; i++)
        arr[i]= utf8.charCodeAt(i);
    return arr;
}

function startsWith(str, word) {
    return str.lastIndexOf(word, 0) === 0;
}

function endsWith(str, word) {
    return str.indexOf(word, str.length - word.length) !== -1;
}

function getQueryVariable(variable) {
	  var query = window.location.search.substring(1);
	  var vars = query.split("&");
	  for (var i=0;i<vars.length;i++) {
	    var pair = vars[i].split("=");
	    if (pair[0] == variable) {
	      return pair[1];
	    }
	  } 
	  console.log('Query Variable ' + variable + ' not found');
}

	
String.prototype.endsWith = function(suffix) {
		return this.indexOf(suffix, this.length - suffix.length) !== -1;
}
	
function _arrayBufferToBase64( buffer ) {
		var binary = '';
		var bytes = new Uint8Array( buffer );
		var len = bytes.byteLength;
		for (var i = 0; i < len; i++) {
			binary += String.fromCharCode( bytes[ i ] );
		}
		return window.btoa( binary );
}
	
	
$( document ).ready(function() {
		
		// valida si el navegador soporta File API
		if (window.File && window.FileReader && window.FileList && window.Blob) {
			console.log('Great success! All the File APIs are supported.');
		} else {
			console.error('Test rapido de API de manejo de archivos no superada, puede haber problemas de firmado.');
		}
		
		
		
		var crypto = window.crypto || window.msCrypto;
			
		// valida si el navegador soporta Crypto API
		if(crypto != null) {
			console.log('Cryptography API Supported');	 
		} else {
			console.error('Test rapido de API de Criptografia no superada, puede haber problemas de firmado.');
		}
		
		
		
		
		
		document.getElementById("btnFirmar").value = decodeURI(getQueryVariable("signButtonCaption"));
		
		var nameFieldTotalOperaciones = getQueryVariable("TotalOperaciones");
		
		var nameFieldToSign = getQueryVariable("valueToSign");
		
		var fieldToSing=window.parent.document.getElementById(nameFieldToSign);
		
		var TotalOperaciones=null;
		
		
		
		
		if(nameFieldTotalOperaciones!=null){
			
			TotalOperaciones=window.parent.document.getElementById(nameFieldTotalOperaciones).value;
			
			var AllOperation='';
			for(i=1; i<=TotalOperaciones;i++){
				
				AllOperation=AllOperation+window.parent.document.getElementById(nameFieldToSign+i).value+'\n';
			}
			
			$('#hash_archivo').text(AllOperation);
			
			
//			$("#dialog-message" ).dialog({ modal: true,
//				resizable: false,
//				height:110,
//				autoOpen:false,
//				margin:0});
			
			
//			$("#progressbar").progressbar({ value: 0,max:TotalOperaciones });
			
			
			
			
		}else{
		
			$('#hash_archivo').text(fieldToSing.value);
		}
		
		var userSerialFieldName = getQueryVariable("userSerialFieldName");
		
		var userSerialField= window.parent.document.getElementById(userSerialFieldName);
		
		$('#userSerialFieldName').text(userSerialField.value);
		
		$("#btnClose").click(function () {

			var functionToCloseName = getQueryVariable("functionToCloseName");
			
			
			window.parent.window[functionToCloseName](false);
	    });
		
		
		
		
});
	
	
function cargarCertificado(){
		
				if($('#pfx').val() == ''){
					alert('Debe seleccionar su certificado .PFX/.P12');
					return;
				}
				
				if($('#password').val() == ''){
					alert('No se puede cargar el certificado si no ingresa la contraseña de su llave privada');
					return;
				}
				
				
			
				
				var fileToLoad = document.getElementById("pfx").files[0];
				
				
				var firmar=true;
				var reader = new FileReader();
				var keyArrayBuffer = null;
				var certArrayBuffer = null;
				var pfxArrayBuffer = null;
				
				// Closure to capture the file information.
				reader.onload = (function(theFile) {
					return function(e) {
					
						
						
//						if (theFile.name.endsWith('.key')){
//
//							keyArrayBuffer = e.target.result; 
//
//							$('#key_b64').val("-----BEGIN ENCRYPTED PRIVATE KEY-----\r\n" +
//									  _arrayBufferToBase64(keyArrayBuffer)+
//						 		          "\n-----END ENCRYPTED PRIVATE KEY-----\r\n");
//
//							$('#key_file_name').val(theFile.name);
//
//
//						} else if (theFile.name.endsWith('.cer')){
//							certArrayBuffer = e.target.result;
//							$('#cert_b64').val("-----BEGIN CERTIFICATE-----\n"+
//									    _arrayBufferToBase64(certArrayBuffer)+
//									   "\n-----END CERTIFICATE-----");
//
//							$('#cert_file_name').val(theFile.name);
//
//						} else 
							
							if (theFile.name.endsWith('.pfx')||theFile.name.endsWith('.p12')){

							pfxArrayBuffer = e.target.result; 
							try{
								
								var privateKeyP12Pem = null;
								var b64Cert = null;
								var serialNumber=null;
							
								//$('#pfx_b64').val(_arrayBufferToBase64(pfxArrayBuffer));
	
								var p12Der = forge.util.decode64(_arrayBufferToBase64(pfxArrayBuffer));
	
								var p12Asn1 = forge.asn1.fromDer(p12Der);
	
								var pkcs12 = forge.pkcs12.pkcs12FromAsn1(p12Asn1,false, $('#password').val());
	
								var map = {};
								// load keypair and cert chain from safe content(s) 
	
								for(var sci = 0; sci < pkcs12.safeContents.length; ++sci) {
								    var safeContents = pkcs12.safeContents[sci];
									
									//console.log('safeContents ' + (sci + 1));
	
								    for(var sbi = 0; sbi < safeContents.safeBags.length; ++sbi) {
									
									var safeBag = safeContents.safeBags[sbi];
	
									//console.log('safeBag.type: ' + safeBag.type);
									
									 var localKeyId = null;
	
								      if(safeBag.attributes.localKeyId) {
									localKeyId = forge.util.bytesToHex(
									  safeBag.attributes.localKeyId[0]);
									//console.log('localKeyId: ' + localKeyId);
									if(!(localKeyId in map)) {
									  map[localKeyId] = {
									    privateKey: null,
									    certChain: []
									  };
									}
								      } else {
									// no local key ID, skip bag
									continue;
								      }
	
	
	
									// this bag has a private key
									if(safeBag.type === forge.pki.oids.keyBag) {
									    //Found plain private key
									    //alert('Found plain private key '+safeBag.key);
									} else if(safeBag.type === forge.pki.oids.pkcs8ShroudedKeyBag) {
										//console.log('found private key');
									    // found encrypted private key
										 map[localKeyId].privateKey = safeBag.key;
	
										
	
									    privateKeyP12Pem = forge.pki.privateKeyToPem(safeBag.key);
										
										var encryptedPrivateKeyP12Pem = forge.pki.encryptRsaPrivateKey(
												safeBag.key, $('#password').val(),{algorithm: '3des'});
	
										//$('#key_b64').val(privateKeyP12Pem);
	
										
									} else if(safeBag.type === forge.pki.oids.certBag) {
										
										map[localKeyId].certChain.push(safeBag.cert);
									    // this bag has a certificate...  
									    var cert = safeBag.cert;
	   								    
									 	var asn1 = forge.pki.certificateToAsn1(cert);
	
										var der = forge.asn1.toDer(asn1);
										
										
										
										
										if($('#userSerialFieldName').text()!=forge.util.hexToBytes(cert.serialNumber)){
											alert('El numero de serie del certificado no coincide con el del usuario.');
											firmar=false;
										}
										
										serialNumber= forge.util.hexToBytes(cert.serialNumber);
										
										b64Cert = forge.util.encode64(der.getBytes());
	
										b64Cert="-----BEGIN CERTIFICATE-----\n"+
												b64Cert+
												"\n-----END CERTIFICATE-----";
	
										
									}   
								    }
								}
	
	
								$('#pfx_file_name').val(theFile.name);
								if(firmar){
									
									var nameFieldTotalOperaciones = getQueryVariable("TotalOperaciones");
									
									var nameFieldToSign    = getQueryVariable("valueToSign");
									
									var signatureFieldName = getQueryVariable("signatureFieldName");
									
									var userSerialFieldName = $('#userSerialFieldName').text();
									
									var textToSing= null;
									
									var textSigned= null;
									
									var algoritmo = $('#hash_alg').val();
									
									var key = extractKey(privateKeyP12Pem);
									
									if(nameFieldTotalOperaciones!=null){
										
										var paramsSingles = getQueryVariable("paramsSingles");
										
										TotalOperaciones=window.parent.document.getElementById(nameFieldTotalOperaciones).value;
										
//										$("#dialog-message" ).dialog('open');
										
//										alert("Va a comenzar a firmar");
										
										for(i=1; i<=TotalOperaciones;i++){
																						
											
											textToSing=window.parent.document.getElementById(nameFieldToSign+i).value;
											
											textSigned=singText(key,algoritmo,textToSing);
											
											if(paramsSingles==null){
											
												window.parent.document.getElementById(signatureFieldName+i).value=textSigned;
											
											}else{
												
												window.parent.document.getElementById(nameFieldToSign+i).value=textToSing.replace("\r\n","\n")+userSerialFieldName+'\n'+'{'+algoritmo+'}'+textSigned.replace("\r\n","\n");
												
											}
											
//											$("#progressbar").progressbar( "value", i );

										}
										
//										alert("Acabo de firmar");
										
//										$( "#dialog-message" ).dialog("close");
									
									}else{
										
										textToSing=window.parent.document.getElementById(nameFieldToSign).value;
										
										textSigned=singText(key,algoritmo,textToSing);
										
										window.parent.document.getElementById(signatureFieldName).value=textSigned;
										
									}
									var serialFieldName = getQueryVariable("serialFieldName");
									
									var serialField=window.parent.document.getElementById(serialFieldName);
									
									serialField.value=serialNumber;
									
									var functionToCloseName = getQueryVariable("functionToCloseName");
									
									window.parent.window[functionToCloseName](true);

								}
							}catch(e){
								
								console.log("Ocurrio un error:"+e);
								
								alert("Error: No se puede leer el certificado "+"("+theFile.name+")."+
										"\nEl archivo no esta en formato PKCS12(.P12 or PFX) el archivo esta corrupto o el password es invalido.");
								
							}




							
						} else {
							console.log("Ocurrio un error:" + theFile.name);
						}
						
					};
				})(fileToLoad);

				function errorHandler(evt) {
				    switch(evt.target.error.code) {
				      case evt.target.error.NOT_FOUND_ERR:
				        alert('No se encontro el archivo!');
				        break;
				      case evt.target.error.NOT_READABLE_ERR:
				        alert('No se puede leer el archivo');
				        break;
				      case evt.target.error.ABORT_ERR:
				        break; // noop
				      default:
				        alert('Ocurrio un error al leer el archivo.');
				    };
				}

				reader.onerror = errorHandler;

				// LOS MODOS DE LECTURA DE HTML5
				reader.readAsArrayBuffer(fileToLoad);
							
}


function extractKey(pkcs8PEM){
	
	var h = null;
	
	if(startsWith(pkcs8PEM,"-----BEGIN ENCRYPTED PRIVATE KEY"))
	 	h =KEYUTIL.getKey(pkcs8PEM,$('#password').val());
	
	else if(startsWith(pkcs8PEM,"-----BEGIN RSA PRIVATE KEY"))	
		h=KEYUTIL.getKey(pkcs8PEM);
	
	return h;
	
}

function singText(key,algoritmo,textToSing){	
			
	var hSig = null;
		
	var sig = new KJUR.crypto.Signature({"alg" : algoritmo });
					
		sig.init(key);
	
		sig.updateString(textToSing);
	
		hSig = sig.sign();

	return hex2b64(hSig);
		
}

	
	

	
	

	
	
	

	
	
	
		

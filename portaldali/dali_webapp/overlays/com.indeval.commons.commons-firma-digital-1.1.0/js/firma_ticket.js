function runScript(event) {
	if (event.which == 13 || event.keyCode == 13) {
		cargarCertificado();
		return false;
	}
	return true;
}
function stringToArrayBuffer(data) {
	var arrBuff = new ArrayBuffer(data.length);
	var writer = new Uint8Array(arrBuff);
	for (var i = 0, len = data.length; i < len; i++) {
		writer[i] = data.charCodeAt(i);
	}
	return arrBuff;
}

function toUTF8Array(str) {
	var utf8 = unescape(encodeURIComponent(str));
	var arr = new Array(utf8.length);
	for (var i = 0; i < utf8.length; i++)
		arr[i] = utf8.charCodeAt(i);
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
	for (var i = 0; i < vars.length; i++) {
		var pair = vars[i].split("=");
		if (pair[0] == variable) {
			return pair[1];
		}
	}	
}

String.prototype.endsWith = function(suffix) {
	return this.indexOf(suffix, this.length - suffix.length) !== -1;
}

function _arrayBufferToBase64(buffer) {
	var binary = '';
	var bytes = new Uint8Array(buffer);
	var len = bytes.byteLength;
	for (var i = 0; i < len; i++) {
		binary += String.fromCharCode(bytes[i]);
	}
	return window.btoa(binary);
}

$(document)
		.ready(
				function() {

					// valida si el navegador soporta File API
					if (window.File && window.FileReader && window.FileList
							&& window.Blob) {
						console.log('Great success! All the File APIs are supported.');
					} else {
						console.error('Test rapido de API de manejo de archivos no superada, puede haber problemas de firmado.');
					}

					
											
					var crypto = window.crypto || window.msCrypto;
	
					// valida si el navegador soporta Crypto API
					if (crypto!=null) {
						console.log('Cryptography API Supported');
					} else {
						console.error('Test rapido de API de Criptografia no superada, puede haber problemas de firmado.');
					}

					
				
					
					
					
					document.getElementById("btnFirmar").value = decodeURI(getQueryVariable("signButtonCaption"));

					$("#btnClose")
							.click(
									function() {

										var functionToCloseName = getQueryVariable("functionToCloseName");

										window.parent.window[functionToCloseName]
												(false);
									});

				});

function cargarCertificado() {

	if ($('#pfx').val() == '') {
		alert('Debe seleccionar su certificado .PFX/.P12');
		return;
	}

	if ($('#password').val() == '') {
		alert('No se puede cargar el certificado si no ingresa la contraseña de su llave privada');
		return;
	}

	var fileToLoad = document.getElementById("pfx").files[0];
	var firmar = true;
	var reader = new FileReader();

	var keyArrayBuffer = null;
	var certArrayBuffer = null;
	var pfxArrayBuffer = null;

	// Closure to capture the file information.
	reader.onload = (function(theFile) {
		return function(e) {

			if (theFile.name.endsWith('.pfx') || theFile.name.endsWith('.p12')) {

				pfxArrayBuffer = e.target.result;
				try {

					var privateKeyP12Pem = null;
					var b64Cert = null;
					var serialNumber = null;

					var p12Der = forge.util
							.decode64(_arrayBufferToBase64(pfxArrayBuffer));

					var p12Asn1 = forge.asn1.fromDer(p12Der);

					var pkcs12 = forge.pkcs12.pkcs12FromAsn1(p12Asn1, false, $(
							'#password').val());

					var map = {};

					for (var sci = 0; sci < pkcs12.safeContents.length; ++sci) {
						var safeContents = pkcs12.safeContents[sci];

						for (var sbi = 0; sbi < safeContents.safeBags.length; ++sbi) {

							var safeBag = safeContents.safeBags[sbi];

							var localKeyId = null;

							if (safeBag.attributes.localKeyId) {
								localKeyId = forge.util
										.bytesToHex(safeBag.attributes.localKeyId[0]);
								// console.log('localKeyId: ' + localKeyId);
								if (!(localKeyId in map)) {
									map[localKeyId] = {
										privateKey : null,
										certChain : []
									};
								}
							} else {
								// no local key ID, skip bag
								continue;
							}

							// this bag has a private key
							if (safeBag.type === forge.pki.oids.keyBag) {
								// Found plain private key
								// alert('Found plain private key
								// '+safeBag.key);
							} else if (safeBag.type === forge.pki.oids.pkcs8ShroudedKeyBag) {
								// console.log('found private key');
								// found encrypted private key
								map[localKeyId].privateKey = safeBag.key;

								privateKeyP12Pem = forge.pki
										.privateKeyToPem(safeBag.key);

								var encryptedPrivateKeyP12Pem = forge.pki
										.encryptRsaPrivateKey(safeBag.key, $(
												'#password').val(), {
											algorithm : '3des'
										});

								// $('#key_b64').val(privateKeyP12Pem);

							} else if (safeBag.type === forge.pki.oids.certBag) {

								map[localKeyId].certChain.push(safeBag.cert);
								// this bag has a certificate...
								var cert = safeBag.cert;

								var asn1 = forge.pki.certificateToAsn1(cert);

								var der = forge.asn1.toDer(asn1);

								serialNumber = forge.util
										.hexToBytes(cert.serialNumber);

								// console.log("serialNumber="+serialNumber);

								var userSerialFieldName = getQueryVariable("userSerialFieldName");
								if(userSerialFieldName!=null){
									var userSerialNumber = window.parent.document
											.getElementById(userSerialFieldName).value;
		
									if (serialNumber
											.localeCompare(userSerialNumber) != 0) {
										alert('El numero de serie del certificado no coincide con el del usuario.');
										firmar = false;
										break;
									}
								}

								b64Cert = forge.util.encode64(der.getBytes());

								b64Cert = "-----BEGIN CERTIFICATE-----\n"
										+ b64Cert
										+ "\n-----END CERTIFICATE-----";

							}
						}
					}

					$('#pfx_file_name').val(theFile.name);
					if (firmar) {

						var encryptedFieldName = getQueryVariable("encryptedFieldName");

						var dencryptedFieldName = getQueryVariable("dencryptedFieldName");

						var encryptedText = window.parent.document
								.getElementById(encryptedFieldName).value;

						var decryptedText = decrypt(privateKeyP12Pem,
								encryptedText);

						var signatureField = window.parent.document
								.getElementById(dencryptedFieldName);

						signatureField.value = decryptedText;

						var functionToCloseName = getQueryVariable("functionToCloseName");

						window.parent.window[functionToCloseName](true);

					}
				} catch (e) {
					console.error(e);
					alert("Error: No se puede leer el certificado "
							+ "("
							+ theFile.name
							+ ")."
							+ "\nEl archivo no esta en formato PKCS12(.P12 or PFX) el archivo esta corrupto o el password es invalido.");
				}

			} else {
				console.log(" ERROR! the name was :" + theFile.name);
			}

		};
	})(fileToLoad);

	function errorHandler(evt) {
		switch (evt.target.error.code) {
		case evt.target.error.NOT_FOUND_ERR:
			alert('File Not Found!');
			break;
		case evt.target.error.NOT_READABLE_ERR:
			alert('File is not readable');
			break;
		case evt.target.error.ABORT_ERR:
			break; // noop
		default:
			alert('An error occurred reading this file.');
		}
		;
	}

	reader.onerror = errorHandler;

	// LOS MODOS DE LECTURA DE HTML5
	reader.readAsArrayBuffer(fileToLoad);

}

function decrypt(pkcs8PEM, textSign) {

	if (pkcs8PEM != null && textSign != null) {

		// console.log("* * * * * * * * * * * * * * * *");
		// console.log("* DESENCRIPTANDO TEXTO! *");

		var plaintext = null;

		var privKey = forge.pki.privateKeyFromPem(pkcs8PEM);

		var ciphertext = forge.util.decode64(textSign);

		plaintext = privKey.decrypt(ciphertext);
		// console.log("* "+plaintext+" *");
		// console.log("* TERMINO DE DESENCRIPTAR *");
		// console.log("* * * * * * * * * * * * * * * *");
		return plaintext;

	}

}

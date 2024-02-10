package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.seguridadMensajeria.exception.DigitalSignException;
import com.indeval.seguridadMensajeria.sign.GenerateSignatureService;

public class SignMensajeImpl implements SignMensaje {
	
	private static final Logger log = LoggerFactory.getLogger(SignMensajeImpl.class);
	
	private GenerateSignatureService generateSignatureService;

	@Override
	public String signMensaje(String mensaje) throws DigitalSignException{
		String mensajeFirmado = null;
		try {
			mensajeFirmado = generateSignatureService.signMessageXml(mensaje);
		} catch (DigitalSignException dse) {
		    dse.getCause().printStackTrace();
		    log.error(dse.getCause().getMessage(), dse.getCause());
			throw new DigitalSignException(dse.getCodigoError(), dse.getMessage());
		}
		return mensajeFirmado;
	}

	public void setGenerateSignatureService(GenerateSignatureService generateSignatureService) {
		this.generateSignatureService = generateSignatureService;
	}

}

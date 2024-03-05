package com.indeval.portaldali.middleware.services.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.seguridadMensajeria.exception.DigitalSignException;
import com.indeval.seguridadMensajeria.sign.GenerateSignatureService;

public class SignMensajeImpl implements SignMensaje {
	
	private static final Logger logger= LoggerFactory.getLogger(SignMensajeImpl.class);
	private GenerateSignatureService generateSignatureService;

	@Override
	public String signMensaje(String mensaje) throws DigitalSignException{
		String mensajeFirmado = null;
		try {
			mensajeFirmado = generateSignatureService.signMessageXml(mensaje);
		} catch (DigitalSignException e) {
		    e.getCause().printStackTrace();
		    logger.error(e.getCause().getMessage(), e.getCause());
			throw e;
		}
		return mensajeFirmado;
	}

	public void setGenerateSignatureService(GenerateSignatureService generateSignatureService) {
		this.generateSignatureService = generateSignatureService;
	}

}

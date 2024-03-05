package com.indeval.portaldali.middleware.services.common;

import com.indeval.seguridadMensajeria.exception.DigitalSignException;

public interface SignMensaje {
	
	public String signMensaje(String xml) throws DigitalSignException;

}

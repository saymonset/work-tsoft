package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import com.indeval.seguridadMensajeria.exception.DigitalSignException;

public interface SignMensaje {

	public String signMensaje(String mensaje) throws DigitalSignException;
	
}

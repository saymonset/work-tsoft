package com.indeval.portalinternacional.persistence.dao;

import java.io.Serializable;

import com.indeval.portalinternacional.middleware.servicios.modelo.MensajePortal;


/**
 * Interface que expone los metodos para los mensajes para el portal.
 * Existe un unico registro con id de 1.
 * 
 * @author Rafael Ibarra Zendejas
 */
public interface MensajePortalDaliDAO {

	/**
	 * Metodo para obtener el mensaje del portal
	 * 
	 * @return MensajePortal
	 */
	public MensajePortal getMensajePortal();

}

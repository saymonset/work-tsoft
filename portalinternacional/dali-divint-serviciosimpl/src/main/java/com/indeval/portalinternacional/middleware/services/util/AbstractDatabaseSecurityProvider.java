package com.indeval.portalinternacional.middleware.services.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.persistence.dao.protocolofinanciero.SecurityDao;

/**
 * Clase abstracta que contiene las propiedades comunes de los proveedores de
 * seguridad (PrivateKeyProvicer y CertificateProvider).
 * 
 * @author Alejandro Rodr&iacute;guez
 */
public abstract class AbstractDatabaseSecurityProvider
{
	protected final Logger log = LoggerFactory.getLogger(getClass());

    protected SecurityDao securityDao;

    /**
         * Asigna el valor de la propiedad <code>securityDao</code>.
         * 
         * @param securityDao
         *                Componente que provee los servicios de acceso a BD
         *                para obtener la informaci&oacute;n de seguridad.
         */
    public void setSecurityDao(SecurityDao securityDao)
    {
    	this.securityDao = securityDao;
    }
}

package com.indeval.portalinternacional.persistence.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.indeval.portalinternacional.middleware.servicios.modelo.MensajePortal;
import com.indeval.portalinternacional.persistence.dao.MensajePortalDaliDAO;


/**
 * Objeto que implementa la interface para el mensaje del portal
 * 
 * @author Rafael Ibarra
 * 
 */
public class MensajePortalDaliDAOImpl extends HibernateDaoSupport implements
		MensajePortalDaliDAO {
	
	/**
	 * Objeto para loggear suceso o eventos
	 */
	private static final Logger log = LoggerFactory.getLogger(MensajePortalDaliDAOImpl.class);
	
	/**
	 * Constante que representa el ID de registro del unico mensaje
	 * del Portal
	 */
	private static Integer ID_MENSAJE = 1;
	
	/**
	 * @see com.indeval.portaldali.persistence.dao.common.MensajePortalDaliDAO#getMensajePortal()
	 */
	public MensajePortal getMensajePortal() {
		log.info("Entrando a MensajePortalDaliDAOImpl.getMensajePortal");
		MensajePortal mensajePortal = (MensajePortal)getHibernateTemplate().get(MensajePortal.class, ID_MENSAJE);
		return mensajePortal;
	}

}

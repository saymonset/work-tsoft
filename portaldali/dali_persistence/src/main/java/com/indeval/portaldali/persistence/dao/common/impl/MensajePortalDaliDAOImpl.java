/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * CuentaDaliDAOImpl.java
 * 29/02/2008
 */
package com.indeval.portaldali.persistence.dao.common.impl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.indeval.portaldali.persistence.dao.common.MensajePortalDaliDAO;
import com.indeval.portaldali.persistence.model.MensajePortal;

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
	private static Logger logger = LoggerFactory.getLogger(MensajePortalDaliDAOImpl.class);
	
	/**
	 * Constante que representa el ID de registro del unico mensaje
	 * del Portal
	 */
	private static Integer ID_MENSAJE = 1;
	
	/**
	 * @see com.indeval.portaldali.persistence.dao.common.MensajePortalDaliDAO#actualizaMensaje(com.indeval.portaldali.persistence.model.MensajePortal)
	 */
	public void actualizaMensaje(MensajePortal mensajePortal) {
		logger.trace("Entrando a MensajePortalDaliDAOImpl.actualizaMensaje");
		if( mensajePortal != null ) {
			if( StringUtils.isNotBlank(mensajePortal.getMensaje()) && 
					mensajePortal.getMensaje().length() > 255 ) {
				mensajePortal.setMensaje( mensajePortal.getMensaje().substring(0, 255));
			}
			if( mensajePortal.getIdMensajePortal() != ID_MENSAJE ) {
				mensajePortal.setIdMensajePortal(ID_MENSAJE);
			}
			getHibernateTemplate().saveOrUpdate(mensajePortal);
		}
	}

	/**
	 * @see com.indeval.portaldali.persistence.dao.common.MensajePortalDaliDAO#getMensajePortal()
	 */
	public MensajePortal getMensajePortal() {
		logger.trace("Entrando a MensajePortalDaliDAOImpl.getMensajePortal");
		MensajePortal mensajePortal = (MensajePortal)getHibernateTemplate().get(MensajePortal.class, ID_MENSAJE);
		return mensajePortal;
	}

}

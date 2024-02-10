package com.indeval.portalinternacional.middleware.services.divisioninternacional;


import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.persistence.dao.util.DateUtilsDao;
import com.indeval.portalinternacional.middleware.servicios.modelo.MensajePortal;
import com.indeval.portalinternacional.persistence.dao.MensajePortalDaliDAO;

/**
 * Implementación de la interface MensajePortalService
 * 
 * @author Rafael Ibarra
 * 
 */
public class MensajePortalServiceImpl implements MensajePortalService {

	/**
	 * Objeto para loggear suceso o eventos
	 */
	private static final Logger log = LoggerFactory.getLogger(MensajePortalServiceImpl.class);

	/**
	 * Mensaje del portal
	 */
	private String mensajeWeb;
	
	/**
	 * Objeto de Acceso a Base de Datos para el Mensaje del Portal
	 */
	private MensajePortalDaliDAO mensajePortalDaliDao;
	
	/**
	 * Tiempo de retraso desde que se crea el Timer hasta que se 
	 * lanza la primera vez la tarea
	 */
	private long delay;
	
	/**
	 * Intervalo para correr la tarea
	 */
	private long period;
	
	/**
	 * Dao para obtener la fecha actual del sistema
	 */
	private DateUtilsDao dateDao;
	

	/**
	 * Metodo de inicializacion del Timer
	 */
	public void init() {
		log.info("Entrando a MensajePortalServiceImpl.init");
		mensajeWeb = this.getOnlyMessage();
		
		Timer timer = new Timer("MensajesDemonio",true);
		log.info("TIMER: Se creo con " +
				"delay de [" + delay + "] y periodo de [" + period + "] a las " +
				"[" + new Date() + "]");
		timer.schedule(new TimerTask(){

			@Override
			public void run() {
				try {
					log.info("TIMER: Actualizando el mensaje del portal a las " +
						"[" + new Date() + "]");
					mensajeWeb = getOnlyMessage();
					log.info("Mensaje: [" + mensajeWeb + "]");
				} catch( Exception ex ) {
					log.error("Error al consultar el mensaje de BD");
				}
			}
		}
		
		, delay, period);
		
	}
	
	/**
	 * @return the mensajePortal
	 */
	public String getMensajeWeb() {
		return mensajeWeb;
	}
	
	/**
	 * @see com.indeval.portaldali.middleware.services.common.MensajePortalService#getMensajeWeb()
	 */
	private String getOnlyMessage() {
		log.debug("Entrando a MensajePortalServiceImpl.getMensajeWeb");
		MensajePortal mensajePortal = mensajePortalDaliDao.getMensajePortal();
		Date fechaActual = dateDao.getDateFechaDB();
		if( fechaActual == null ) fechaActual = new Date();
		if (mensajePortal != null
				&& StringUtils.isNotBlank(mensajePortal.getMensaje())
				&& mensajePortal.isHabilitado()
				&& DateUtils.isSameDay( mensajePortal.getFecha(), fechaActual ) ) {
			return mensajePortal.getMensaje();
		}
		return null;
	}

	/**
	 * @param mensajePortalDaliDao
	 *            the mensajePortalDaliDao to set
	 */
	public void setMensajePortalDaliDao(
			MensajePortalDaliDAO mensajePortalDaliDao) {
		this.mensajePortalDaliDao = mensajePortalDaliDao;
	}

	/**
	 * @param delay the delay to set
	 */
	public void setDelay(long delay) {
		this.delay = delay;
	}

	/**
	 * @param period the period to set
	 */
	public void setPeriod(long period) {
		this.period = period;
	}

	/**
	 * @param dateDao the dateDao to set
	 */
	public void setDateDao(DateUtilsDao dateDao) {
		this.dateDao = dateDao;
	}
}

/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * ConsultaDivisaServiceImpl.java
 * 06/03/2008
 */
package com.indeval.portaldali.middleware.services.common;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.dto.MensajePortalDTO;
import com.indeval.portaldali.persistence.dao.common.DateUtilsDao;
import com.indeval.portaldali.persistence.dao.common.MensajePortalDaliDAO;
import com.indeval.portaldali.persistence.model.MensajePortal;

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
	private static Logger logger = LoggerFactory.getLogger(MensajePortalServiceImpl.class);

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
		logger.debug("Entrando a MensajePortalServiceImpl.init");
		mensajeWeb = this.getOnlyMessage();
		
		Timer timer = new Timer("MensajesDemonio",true);
		logger.debug("TIMER: Se creo con " +
				"delay de [" + delay + "] y periodo de [" + period + "] a las " +
				"[" + new Date() + "]");
		timer.schedule(new TimerTask(){

			@Override
			public void run() {
				try {
					logger.debug("TIMER: Actualizando el mensaje del portal a las " +
						"[" + new Date() + "]");
					mensajeWeb = getOnlyMessage();
					logger.debug("Mensaje: [" + mensajeWeb + "]");
				} catch( Exception ex ) {
					logger.error("Error al consultar el mensaje de BD");
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
	 * @see com.indeval.portaldali.middleware.services.common.MensajePortalService#getMensaje()
	 */
	public MensajePortalDTO getMensaje() {
		logger.trace("Entrando a MensajePortalServiceImpl.getMensaje");
		MensajePortal mensajePortal = mensajePortalDaliDao.getMensajePortal();
		return convertMensajeBOToMensajeDTO(mensajePortal);
	}

	/**
	 * @see com.indeval.portaldali.middleware.services.common.MensajePortalService#guardaMensaje(com.indeval.portaldali.middleware.dto.MensajePortalDTO)
	 */
	public void guardaMensaje(MensajePortalDTO dto) {
		logger.trace("Entrando a MensajePortalServiceImpl.guardaMensaje");
		if (dto != null) {
			MensajePortal mensajePortal = mensajePortalDaliDao.getMensajePortal();
			if (StringUtils.isNotBlank(dto.getMensaje())) {
				mensajePortal.setMensaje(dto.getMensaje());
			} else {
				mensajePortal.setMensaje(null);
			}
			mensajePortal.setHabilitado(dto.isHabilitado());
			Date fechaActual = dateDao.getDateFechaDB();
			if( fechaActual == null ) fechaActual = new Date();
			mensajePortal.setFecha(fechaActual);
			mensajePortalDaliDao.actualizaMensaje(mensajePortal);
		}
	}
	
	/**
	 * @see com.indeval.portaldali.middleware.services.common.MensajePortalService#getMensajeWeb()
	 */
	private String getOnlyMessage() {
		logger.trace("Entrando a MensajePortalServiceImpl.getMensajeWeb");
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
	 * Metodo privado para convertir de MensajePortal(BO) hacia
	 * MensajePortalDTO (DTO)
	 * @param MensajePortal
	 * @return MensajePortalDTO
	 */
	private MensajePortalDTO convertMensajeBOToMensajeDTO(MensajePortal bo) {
		logger.trace("Entrando a MensajePortalServiceImpl.convertMensajeBOToMensajeDTO");
		MensajePortalDTO dto = new MensajePortalDTO();
		if (bo != null) {
			dto.setMensaje(bo.getMensaje());
			dto.setHabilitado(bo.isHabilitado());
		}
		return dto;
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

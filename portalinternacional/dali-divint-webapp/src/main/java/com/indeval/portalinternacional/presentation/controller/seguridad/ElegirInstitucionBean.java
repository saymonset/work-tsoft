/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 */
package com.indeval.portalinternacional.presentation.controller.seguridad;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.math.NumberUtils;

import com.bursatec.seguridad.middleware.ejb.SeguridadServiceLocator;
import com.bursatec.seguridad.middleware.service.SeguridadService;
import com.bursatec.seguridad.presentation.constants.SeguridadConstants;
import com.bursatec.seguridad.vo.InstitucionVO;
import com.indeval.portaldali.middleware.services.util.DateUtilService;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.persistence.dao.common.InstitucionDao;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * Backing bean para la eleccion de una institucion.
 * 
 * @author Emigdio
 * 
 */
public class ElegirInstitucionBean extends ControllerBase {

	/** Clase de utileria para fechas */
	private DateUtilService dateUtilService;

	private String idInstitucionSeleccionada = null;

	/** DAO para la consulta de instituciones */
	private InstitucionDao institucionDao = null;


    

	/**
	 * Obtiene las opciones a presentar en el combo de instituciones dependiendo
	 * de las instituciones disponibles para el perfil del usuario que inicio
	 * sesion.
	 * 
	 * @return Lista de {@link SelectItem} con las opciones a mostrar
	 */
	public List<SelectItem> getOpcionesComboInstitucion() {

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		InstitucionVO voActual = (InstitucionVO) session
				.getAttribute(SeguridadConstants.INSTITUCION_VO_ACTUAL);

		if (voActual != null) {

			idInstitucionSeleccionada = voActual.getId() + "";
		}

		Object[] instituciones = (Object[]) session
				.getAttribute(SeguridadConstants.INSTITUCIONES_DISPONIBLES);

		List<SelectItem> selects = new ArrayList<SelectItem>();

		if (instituciones != null) {
			for (Object oVo : instituciones) {

				InstitucionVO vo = (InstitucionVO) oVo;

				selects.add(new SelectItem(String.valueOf(vo.getId()), vo
						.getClave()
						+ vo.getFolio() + " " + vo.getNombre()));
			}
		}

		return selects;
	}

	/**
	 * Cambia la institucion actual del usuario
	 */
	public void asignarInstitucion() {
		SeguridadService seguridadExposedService = SeguridadServiceLocator.obtenerSeguridadExposedService();
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		Object[] instituciones = (Object[]) session
				.getAttribute(SeguridadConstants.INSTITUCIONES_DISPONIBLES);
		long idInstitucionSeleccionada = NumberUtils
				.toLong(getIdInstitucionSeleccionada());
		InstitucionVO vo = null;
		Institucion institucion = null;
		for (Object oVo : instituciones) {
			vo = (InstitucionVO) oVo;
			if (vo.getId().longValue() == idInstitucionSeleccionada) {
				institucion = institucionDao.findInstitucionById(vo.getId()
						.longValue());
				break;
			} else {
				vo = null;
			}

		}

		AgenteVO dto = new AgenteVO();

		dto.setFolio(vo.getFolio());
		dto.setId(vo.getClave());
		dto.setNombreCorto(vo.getNombre().toString());
		dto.setFirmado(true);
		dto.setRazon(institucion.getRazonSocial());

		String ticket=(String)session.getAttribute(SeguridadConstants.TICKET_SESION);
		try {
			seguridadExposedService.cambiaInstitucionTicket(ticket, vo.getId());
			addToCache(ticket,"CAMBIO_INSTITUCION_ACTUAL", vo.getClave()+vo.getFolio(),Long.valueOf(session.getMaxInactiveInterval()),TimeUnit.SECONDS);
			session.setAttribute(SeguridadConstants.INSTITUCION_ACTUAL, dto);
			session.setAttribute(SeguridadConstants.INSTITUCION_VO_ACTUAL, vo);
			
			ELContext elContext = FacesContext.getCurrentInstance().getELContext();		
			InicioSesionBean inicioSesionBean = (InicioSesionBean)FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext,null,"inicioSesionBean");
			inicioSesionBean.cargarInfoUsuario(seguridadExposedService, ticket);
		} catch (Exception e) {
			
			session.invalidate();
			return;
		}
	}

	/**
	 * Obtiene el campo idInstitucionSeleccionada
	 * 
	 * @return idInstitucionSeleccionada
	 */
	public String getIdInstitucionSeleccionada() {
		return idInstitucionSeleccionada;
	}

	/**
	 * Asigna el valor del campo idInstitucionSeleccionada
	 * 
	 * @param idInstitucionSeleccionada
	 *            el valor de idInstitucionSeleccionada a asignar
	 */
	public void setIdInstitucionSeleccionada(String idInstitucionSeleccionada) {
		this.idInstitucionSeleccionada = idInstitucionSeleccionada;
	}

	/**
	 * Obtiene la fecha actual del server
	 */
	public String getCurrentDate() {
		return String.valueOf(dateUtilService.getCurrentDate().getTime());
	}

	public Date getFechaActual() {
		return dateUtilService.getCurrentDate();
	}

	/**
	 * Retorna una cadena con el id , folio y el nombre de la institucion actual
	 * 
	 * @return
	 */
	public String getDescripcionInstitucion() {
		return getAgenteFirmado().getId() + getAgenteFirmado().getFolio() + " "
				+ getAgenteFirmado().getRazon();
	}

	/**
	 * @return the dateUtilService
	 */
	public DateUtilService getDateUtilService() {
		return dateUtilService;
	}

	/**
	 * @param dateUtilService
	 *            the dateUtilService to set
	 */
	public void setDateUtilService(DateUtilService dateUtilService) {
		this.dateUtilService = dateUtilService;
	}

	/**
	 * Obtiene el valor del atributo institucionDao
	 *
	 * @return el valor del atributo institucionDao
	 */
	public InstitucionDao getInstitucionDao() {
		return institucionDao;
	}

	/**
	 * Establece el valor del atributo institucionDao
	 *
	 * @param institucionDao el valor del atributo institucionDao a establecer
	 */
	public void setInstitucionDao(InstitucionDao institucionDao) {
		this.institucionDao = institucionDao;
	}


}

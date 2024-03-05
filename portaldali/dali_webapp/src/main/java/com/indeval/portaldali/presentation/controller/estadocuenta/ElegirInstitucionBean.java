/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 */
package com.indeval.portaldali.presentation.controller.estadocuenta;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.math.NumberUtils;

import com.bursatec.seguridad.middleware.ejb.SeguridadServiceLocator;
import com.bursatec.seguridad.middleware.service.SeguridadService;
import com.bursatec.seguridad.presentation.constants.SeguridadConstants;
import com.bursatec.seguridad.vo.InstitucionVO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.services.common.ConsultaInstitucionService;
import com.indeval.portaldali.middleware.services.util.UtilServices;
import com.indeval.portaldali.presentation.controller.common.ControllerBase;
import com.indeval.portaldali.presentation.jsf.menunavegacion.constants.MenuNavegacionConstants;
import com.indeval.portaldali.presentation.jsf.menunavegacion.vo.ElementoMenu;
import com.indeval.portaldali.presentation.util.MenuNavegacionHelper;

/**
 * Backing bean para la elección de una institucion.
 * @author Emigdio
 *
 */
public class ElegirInstitucionBean extends ControllerBase{
	
	/** El elemento del menu que representa la raiz del menu de navegacion */
	private ElementoMenu elementoMenuRaiz = null;
	
	private ConsultaInstitucionService consultaInstitucionService = null;
	
	private UtilServices utilService = null;
	
	private String idInstitucionSeleccionada = null;
	
		
	/**
	 * Obtiene el campo consultaInstitucionService
	 * @return  consultaInstitucionService
	 */
	public ConsultaInstitucionService getConsultaInstitucionService() {
		return consultaInstitucionService;
	}

	/**
	 * Asigna el valor del campo consultaInstitucionService
	 * @param consultaInstitucionService el valor de consultaInstitucionService a asignar
	 */
	public void setConsultaInstitucionService(
			ConsultaInstitucionService consultaInstitucionService) {
		this.consultaInstitucionService = consultaInstitucionService;
	}
	
	
	/**
	 * Obtiene las opciones a presentar en el combo de instituciones dependiendo de las instituciones
	 * disponibles para el perfil del usuario que inici sesión.
	 * @return Lista de {@link SelectItem} con las opciones a mostrar
	 */
	public List<SelectItem> getOpcionesComboInstitucion(){
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		InstitucionVO voActual = (InstitucionVO)session.getAttribute(SeguridadConstants.INSTITUCION_VO_ACTUAL);
		if(voActual!= null){
			
			idInstitucionSeleccionada = voActual.getId()+"";
		}
		
		
		Object []instituciones = (Object [])session.getAttribute(SeguridadConstants.INSTITUCIONES_DISPONIBLES);
		
		
		List<SelectItem> selects = new ArrayList<SelectItem>();
		
		if(instituciones != null){
			for(Object oVo:instituciones){
				
				InstitucionVO vo = (InstitucionVO)oVo;
				InstitucionDTO dto = new InstitucionDTO();
				dto.setClaveTipoInstitucion(vo.getClave());
				dto.setFolioInstitucion(vo.getFolio());
				dto.setNombreCorto(vo.getNombre());
				dto.setId(vo.getId());
				
				selects.add(new SelectItem(String.valueOf(dto.getId()),
						 dto.getClaveTipoInstitucion()+dto.getFolioInstitucion()+ " " + dto.getNombreCorto()));
			}
		}
		
		
		
		
		return selects;
	}
	/**
	 * Cambia la institución actual del usuario
	 */
	public void asignarInstitucion(){
		SeguridadService seguridadExposedService = SeguridadServiceLocator.obtenerSeguridadExposedService();
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Object []instituciones = (Object [])session.getAttribute(SeguridadConstants.INSTITUCIONES_DISPONIBLES);
		long idInstitucionSeleccionada = NumberUtils.toLong(getIdInstitucionSeleccionada());
		InstitucionVO vo = null;
		for(Object oVo:instituciones){
			vo = (InstitucionVO)oVo;
			if(vo.getId().longValue() == idInstitucionSeleccionada){
				break;
			}else{
				vo = null;
			}
				
		}
		
//		Cookie cookie = new Cookie("renuevaSesion", "renuevaSesion");
//		cookie.setComment("Aviso de Renovacion de Sesion");
//		cookie.setPath("/");
//		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
//		response.addCookie(cookie);

		InstitucionDTO dto = null;
		
		if(vo != null) {
			dto = consultaInstitucionService.buscarInstitucionPorClaveYFolio(vo.getClave() + vo.getFolio());
		}
		
		if(dto != null){
						
			String ticket=(String)session.getAttribute(SeguridadConstants.TICKET_SESION);
			logger.debug("ticket ["+ticket+"]");
			
			try {
				seguridadExposedService.cambiaInstitucionTicket(ticket, dto.getId());
				
				if(SeguridadConstants.NOMBRE_INSTITUCION_INDEVAL.equals(dto.getNombreCorto())){
					dto.setId(SeguridadConstants.ID_INSTITUCION_INDEVAL);
				}
				addToCache(ticket,"CAMBIO_INSTITUCION_ACTUAL", vo.getClave()+vo.getFolio(),Long.valueOf(session.getMaxInactiveInterval()),TimeUnit.SECONDS);
			
				session.setAttribute(SeguridadConstants.INSTITUCION_ACTUAL, dto);
				session.setAttribute(SeguridadConstants.INSTITUCION_VO_ACTUAL, vo);

				ElementoMenu menuUsuario = MenuNavegacionHelper.obtenerMenuNavegacionDeUsuario(elementoMenuRaiz, ticket);
				session.setAttribute(MenuNavegacionConstants.MENU_NAVEGACION_SESSION_KEY, menuUsuario);
				
			} catch (Exception e) {
				logger.error("Ha ocurrido una excpecion de seguridad, cerrando sesion", e);
				session.invalidate();
			}
		}
		
	}

	/**
	 * Obtiene el campo idInstitucionSeleccionada
	 * @return  idInstitucionSeleccionada
	 */
	public String getIdInstitucionSeleccionada() {
		return idInstitucionSeleccionada;
	}

	/**
	 * Asigna el valor del campo idInstitucionSeleccionada
	 * @param idInstitucionSeleccionada el valor de idInstitucionSeleccionada a asignar
	 */
	public void setIdInstitucionSeleccionada(String idInstitucionSeleccionada) {
		this.idInstitucionSeleccionada = idInstitucionSeleccionada;
	}

	/**
	 * Obtiene la fecha actual del server
	 */
	public String getCurrentDate(){
		return String.valueOf(utilService.getCurrentDate().getTime());
	}

	public Date getFechaActual(){
		return utilService.getCurrentDate();
	}
	/**
	 * Retorna una cadena con el id , folio y el nombre de la institucion actual
	 * @return
	 */
	public String getDescripcionInstitucion(){
		return getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion() + " " + getInstitucionActual().getRazonSocial();
	}
	/**
	 * @return the utilService
	 */
	public UtilServices getUtilService() {
		return utilService;
	}

	/**
	 * @param utilService the utilService to set
	 */
	public void setUtilService(UtilServices utilService) {
		this.utilService = utilService;
	}

	public void setElementoMenuRaiz(ElementoMenu elementoMenuRaiz) {
		this.elementoMenuRaiz = elementoMenuRaiz;
	}
	
}

/**
 * Sistema Internacional de Cotizaciones
 * 
 * Bursatec - 2H Software SA de CV
 *
 * Creado: Nov 5, 2008
 */
package com.indeval.portalinternacional.presentation.controller.divinternacional;

import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;
import com.indeval.portalinternacional.common.util.AgenteViewHelper;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * Controller para la consulta de Tope Circulante de Fideicomisos
 * 
 * @author Erik Vera Montoya.
 * @version 1.0
 * 
 */
public class TopeCirculanteController extends ControllerBase {

	/** Emision a consultar */
	private EmisionVO emision;

	/** Servicio para la consulta del tope circulante */
	private DivisionInternacionalService divisionInternacionalService;
	
	/** El identificador y el folio del traspasante */
	private String idFolioTraspasante;

	/** Alta del combo desde la pantalla */
	private String altaCombo;

	/** Bandera que define el estado de la consulta */
	private boolean consultaEjecutada;
	
	
	/** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(TopeCirculanteController.class);

	/**
	 * Ejecuta las actividades necesarias de inicialización de la pantalla.
	 * 
	 * @return <code>null</code>. No es necesario un valor dev retorno.
	 */
	public String getInit() {
		if (idFolioTraspasante == null || StringUtils.isEmpty(idFolioTraspasante)) {
			if (getAgenteFirmado() != null
					&& getAgenteFirmado().getId() != null
					&& getAgenteFirmado().getFolio() != null) {
				idFolioTraspasante = AgenteViewHelper
						.obtenerClaveTipoInstitucionYFolio(
								getAgenteFirmado().getId(),
								getAgenteFirmado().getFolio());

			}
		}
		emision = new EmisionVO();
		altaCombo = new String();
		consultaEjecutada=false;
		return null;
	}

	/**
	 * Invoca la consulta principal de la pantalla
	 * 
	 * @param e
	 *            Evento generado durante la solicitud
	 */
	public void buscarTopesCirculantes(ActionEvent e) {
		try {
			crearCriterio();
			ejecutarConsulta();
			consultaEjecutada = true;			
		} catch (BusinessException exc) {
			log.error("Ocurrio un error:",e);
			agregarMensaje(exc);			
		} catch (Exception exc) {
			log.error("Ocurrio un error:",exc);
			agregarMensaje(exc);
			
		} catch (Throwable exc) {
			log.error("Ocurrio un error:",exc);
			agregarMensaje(exc);			
		}
	}
	/**
	 * Crea el objeto de criterio de consulta
	 */
	public void crearCriterio() {
		emision.setAlta(altaCombo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portalinternacional.presentation.controller.common.ControllerBase#ejecutarConsulta()
	 */
	@Override
	public String ejecutarConsulta() {
		paginaVO = divisionInternacionalService.getTopeCirculanteFidecomiso(emision, paginaVO);
		consultaEjecutada = true;
		return null;
	}

	/**
	 * Método que limpia la pantalla de Tope Circulante
	 * 
	 * @param e
	 */
	public void limpiar(ActionEvent e) {
		getInit();
	}
	
	//etiquetas para el panel de criterios y los reportes
	/**
	 * Metodo que retorna el tipo valor selecccionado en la consulta
	 * @return el tipo valor
	 */
	public String getTipoValorSeleccionado(){		
		String descripcion ="";
		if(emision != null && emision.getTv() != null && !StringUtils.isBlank(emision.getTv())){
			descripcion = emision.getTv();				
		}else {
			descripcion = Constantes.OPCION_TODOS_CRITERIO;
		}
		return descripcion;
	}
	/**
	 * Metodo que retorna la emisora selecccionado en la consulta
	 * @return la emisora seleccionada
	 */
	public String getEmisoraSeleccionada(){
		String descripcion ="";
		if(emision != null &&emision.getEmisora()!=null && !StringUtils.isBlank(emision.getEmisora())){
			descripcion = emision.getEmisora();				
		}else {
			descripcion = Constantes.OPCION_TODAS_CRITERIO;
		}
		return descripcion;
	}
	
	/**
	 * Metodo que retorna la serie selecccionado en la consulta
	 * @return la serie seleccionada
	 */
	public String getSerieSeleccionada(){
		String descripcion ="";
		if(emision != null && emision.getSerie()!= null && !StringUtils.isBlank(emision.getSerie())){
			descripcion = emision.getSerie();				
		}else {
			descripcion = Constantes.OPCION_TODAS_CRITERIO;
		}
		return descripcion;
	}
	/**
	 * Metodo que retorna el cupon selecccionado en la consulta
	 * @return el cupon seleccionada
	 */
	public String getCuponSeleccionado(){
		String descripcion ="";
		if(emision != null && emision.getCupon() != null && !StringUtils.isBlank(emision.getCupon())){
			descripcion = emision.getCupon();				
		}else {
			descripcion = Constantes.OPCION_TODOS_CRITERIO;
		}
		return descripcion;
	}
	
	/**
	 * Metodo que retorna la serie selecccionado en la consulta
	 * @return la serie seleccionada
	 */
	public String getIsinSeleccionado(){
		String descripcion ="";
		if(emision != null && emision.getIsin()!= null && !StringUtils.isBlank(emision.getIsin())){
			descripcion = emision.getIsin();				
		}else {
			descripcion = Constantes.OPCION_TODOS_CRITERIO;
		}
		return descripcion;
	}

	/**
	 * Obtiene el valor del atributo emision
	 * 
	 * @return el valor del atributo emision
	 */
	public EmisionVO getEmision() {
		return emision;
	}

	/**
	 * Establece el valor del atributo emision
	 * 
	 * @param emision
	 *            el valor del atributo emision a establecer
	 */
	public void setEmision(EmisionVO emision) {
		this.emision = emision;
	}

	/**
	 * Obtiene el valor del atributo divisionInternacionalService
	 * 
	 * @return el valor del atributo divisionInternacionalService
	 */
	public DivisionInternacionalService getDivisionInternacionalService() {
		return divisionInternacionalService;
	}

	/**
	 * Establece el valor del atributo divisionInternacionalService
	 * 
	 * @param divisionInternacionalService
	 *            el valor del atributo divisionInternacionalService a
	 *            establecer
	 */
	public void setDivisionInternacionalService(
			DivisionInternacionalService divisionInternacionalService) {
		this.divisionInternacionalService = divisionInternacionalService;
	}

	/**
	 * Obtiene el valor del atributo altaCombo
	 * 
	 * @return el valor del atributo altaCombo
	 */
	public String getAltaCombo() {
		return altaCombo;
	}

	/**
	 * Establece el valor del atributo altaCombo
	 * 
	 * @param altaCombo
	 *            el valor del atributo altaCombo a establecer
	 */
	public void setAltaCombo(String altaCombo) {
		this.altaCombo = altaCombo;
	}

	/**
	 * Obtiene el valor del atributo consultaEjecutada
	 * 
	 * @return el valor del atributo consultaEjecutada
	 */
	public boolean isConsultaEjecutada() {
		return consultaEjecutada;
	}

	/**
	 * Establece el valor del atributo consultaEjecutada
	 * 
	 * @param consultaEjecutada
	 *            el valor del atributo consultaEjecutada a establecer
	 */
	public void setConsultaEjecutada(boolean consultaEjecutada) {
		this.consultaEjecutada = consultaEjecutada;
	}

	

	/**
	 * Obtiene el valor del atributo idFolioTraspasante
	 *
	 * @return el valor del atributo idFolioTraspasante
	 */
	public String getIdFolioTraspasante() {
		return idFolioTraspasante;
	}

	/**
	 * Establece el valor del atributo idFolioTraspasante
	 *
	 * @param idFolioTraspasante el valor del atributo idFolioTraspasante a establecer
	 */
	public void setIdFolioTraspasante(String idFolioTraspasante) {
		this.idFolioTraspasante = idFolioTraspasante;
	}

}

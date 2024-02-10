/**
 * Sistema Internacional de Cotizaciones
 * 
 * Bursatec - 2H Software SA de CV
 *
 * Creado: Nov 27, 2008
 */
package com.indeval.portalinternacional.presentation.controller.administracion;

import javax.faces.event.ActionEvent;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.util.ConsultaCatalogoService;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.SicEmision;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaCustodiosVO;
import com.indeval.portalinternacional.persistence.dao.SicEmisionDao;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * 
 * Controller para la consulta de Emisiones dentro de la administracion de
 * Custodios y Depositantes
 * 
 * @author Erik Vera Montoya.
 * @version 1.0
 * 
 */
public class AdmonCustodiosDepositantesConsultaEmiController extends
		ControllerBase {
	/** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(AdmonCustodiosDepositantesConsultaEmiController.class);

	/** Objeto para reprentar la emision seleccionada */
	private SicEmision emisionActual;

	/** Cadena de idFolio para Agente */
	private String claveParticipante;

	/** Cadena para la cuenta de Agente */
	private String cuentaParticipante;

	/** Cadena para nombre Corto del Agente */
	private String nombreCortoParticipante;

	/** Cadenas que representan los datos de la emision */
	private EmisionVO emisionVO;

	/** Forma de Operar */
	private String formaOperar;
	
	/**Objeto para indicar el custodio seleccionado*/
	private ConsultaCustodiosVO custodioActual;

	/** Servicio para traer los datos del participante */
	private ConsultaCatalogoService consultaCatalogoService;
	
	private boolean consultaEmisionesEjecutada = false;

	/** Servicio para la consulta de fideicomiso */
	private DivisionInternacionalService divisionInternacionalService;
	
	private SicEmisionDao sicEmisionDao;
	/**
	 * Invoca el metodo que limpia la pantalla de buscar Emision
	 * @param event Evento generado durante la solicitud
	 */
	public void limpiarForma( ActionEvent event ) {
		paginaVO = new PaginaVO();
		paginaVO.setOffset(Constantes.CERO_INTEGER);
		paginaVO.setRegistrosXPag(50);
		emisionVO = new EmisionVO();
		formaOperar = new String();
		consultaEmisionesEjecutada = false;
	}	

	/**
	 * Invoca la consulta principal de la pantalla
	 * 
	 * @param e
	 *            Evento generado durante la solicitud
	 */
	public void buscarEmisiones(ActionEvent e) {
		
		ejecutarConsulta();
		consultaEmisionesEjecutada = true;
	}

	public String ejecutarConsulta() {
		log.info("Entrando a AdmonCustodiosDepositantesConsultaEmiController.ejecutarConsulta");
		//paginaVO = new PaginaVO();
		//paginaVO.setRegistrosXPag(50);
		paginaVO = divisionInternacionalService.getListaSicEmisiones(
				custodioActual.getCatBic(), emisionVO, formaOperar, paginaVO);
		if (paginaVO == null && paginaVO.getRegistros()== null && paginaVO.getRegistros().isEmpty()) {
			paginaVO= new PaginaVO();
		}
		return null;
	}
	
	/**
	 * Metodo que borra una Emision a partir de su Id
	 * @param event
	 */
	public void borrarEmisionById(ActionEvent event){
		Long idEmision = (Long)event.getComponent().getAttributes().get("idSicEmision");
		System.out.println("Id a Borrar--->"+idEmision);
		try{
			
			if (idEmision != null) {
			
				SicEmision sicEmision = sicEmisionDao.findSicEmsionByPk(idEmision);
				System.out.println("Emision encontrada---->"+ReflectionToStringBuilder.toString(sicEmision));
				if (sicEmision != null) {
					
					divisionInternacionalService.cancelaSicEmision(sicEmision);
					System.out.println("Borrando la pantalla");
					limpiarForma(event);
				}
			}
		} catch(BusinessException ex) {
			ex.printStackTrace();
			agregarMensaje(ex);		
		} catch(Throwable ex) {
			ex.printStackTrace();
			agregarMensaje(ex);
		
		}
	}

	/**
	 * Obtiene el valor del atributo emisionActual
	 * 
	 * @return el valor del atributo emisionActual
	 */
	public SicEmision getEmisionActual() {
		return emisionActual;
	}

	/**
	 * Establece el valor del atributo emisionActual
	 * 
	 * @param emisionActual
	 *            el valor del atributo emisionActual a establecer
	 */
	public void setEmisionActual(SicEmision emisionActual) {
		this.emisionActual = emisionActual;
	}

	/**
	 * Obtiene el valor del atributo claveParticipante
	 * 
	 * @return el valor del atributo claveParticipante
	 */
	public String getClaveParticipante() {
		return claveParticipante;
	}

	/**
	 * Establece el valor del atributo claveParticipante
	 * 
	 * @param claveParticipante
	 *            el valor del atributo claveParticipante a establecer
	 */
	public void setClaveParticipante(String claveParticipante) {
		this.claveParticipante = claveParticipante;
	}

	/**
	 * Obtiene el valor del atributo cuentaParticipante
	 * 
	 * @return el valor del atributo cuentaParticipante
	 */
	public String getCuentaParticipante() {
		return cuentaParticipante;
	}

	/**
	 * Establece el valor del atributo cuentaParticipante
	 * 
	 * @param cuentaParticipante
	 *            el valor del atributo cuentaParticipante a establecer
	 */
	public void setCuentaParticipante(String cuentaParticipante) {
		this.cuentaParticipante = cuentaParticipante;
	}

	/**
	 * Obtiene el valor del atributo nombreCortoParticipante
	 * 
	 * @return el valor del atributo nombreCortoParticipante
	 */
	public String getNombreCortoParticipante() {
		return nombreCortoParticipante;
	}

	/**
	 * Establece el valor del atributo nombreCortoParticipante
	 * 
	 * @param nombreCortoParticipante
	 *            el valor del atributo nombreCortoParticipante a establecer
	 */
	public void setNombreCortoParticipante(String nombreCortoParticipante) {
		this.nombreCortoParticipante = nombreCortoParticipante;
	}

	/**
	 * Obtiene el valor del atributo emisionVO
	 * 
	 * @return el valor del atributo emisionVO
	 */
	public EmisionVO getEmisionVO() {
		return emisionVO;
	}

	/**
	 * Establece el valor del atributo emisionVO
	 * 
	 * @param emisionVO
	 *            el valor del atributo emisionVO a establecer
	 */
	public void setEmisionVO(EmisionVO emisionVO) {
		this.emisionVO = emisionVO;
	}

	/**
	 * Obtiene el valor del atributo formaOperar
	 * 
	 * @return el valor del atributo formaOperar
	 */
	public String getFormaOperar() {
		return formaOperar;
	}

	/**
	 * Establece el valor del atributo formaOperar
	 * 
	 * @param formaOperar
	 *            el valor del atributo formaOperar a establecer
	 */
	public void setFormaOperar(String formaOperar) {
		this.formaOperar = formaOperar;
	}

	/**
	 * Obtiene el valor del atributo consultaCatalogoService
	 * 
	 * @return el valor del atributo consultaCatalogoService
	 */
	public ConsultaCatalogoService getConsultaCatalogoService() {
		return consultaCatalogoService;
	}

	/**
	 * Establece el valor del atributo consultaCatalogoService
	 * 
	 * @param consultaCatalogoService
	 *            el valor del atributo consultaCatalogoService a establecer
	 */
	public void setConsultaCatalogoService(
			ConsultaCatalogoService consultaCatalogoService) {
		this.consultaCatalogoService = consultaCatalogoService;
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
	 * Obtiene el valor del atributo consultaEmisionesEjecutada
	 *
	 * @return el valor del atributo consultaEmisionesEjecutada
	 */
	public boolean isConsultaEmisionesEjecutada() {
		return consultaEmisionesEjecutada;
	}

	/**
	 * Establece el valor del atributo consultaEmisionesEjecutada
	 *
	 * @param consultaEmisionesEjecutada el valor del atributo consultaEmisionesEjecutada a establecer
	 */
	public void setConsultaEmisionesEjecutada(boolean consultaEmisionesEjecutada) {
		this.consultaEmisionesEjecutada = consultaEmisionesEjecutada;
	}

	/**
	 * Obtiene el valor del atributo custodioActual
	 *
	 * @return el valor del atributo custodioActual
	 */
	public ConsultaCustodiosVO getCustodioActual() {
		return custodioActual;
	}

	/**
	 * Establece el valor del atributo custodioActual
	 *
	 * @param custodioActual el valor del atributo custodioActual a establecer
	 */
	public void setCustodioActual(ConsultaCustodiosVO custodioActual) {
		this.custodioActual = custodioActual;
	}

	/**
	 * Obtiene el valor del atributo sicEmisionDao
	 *
	 * @return el valor del atributo sicEmisionDao
	 */
	public SicEmisionDao getSicEmisionDao() {
		return sicEmisionDao;
	}

	/**
	 * Establece el valor del atributo sicEmisionDao
	 *
	 * @param sicEmisionDao el valor del atributo sicEmisionDao a establecer
	 */
	public void setSicEmisionDao(SicEmisionDao sicEmisionDao) {
		this.sicEmisionDao = sicEmisionDao;
	}

	

}

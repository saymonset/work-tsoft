/**
 * Sistema Internacional de Cotizaciones
 * 
 * Bursatec - 2H Software SA de CV
 *
 * Creado: Dec 2, 2008
 */
package com.indeval.portalinternacional.presentation.controller.administracion;

import javax.faces.event.ActionEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.SicDetalle;
import com.indeval.portalinternacional.middleware.servicios.modelo.SicEmision;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaCustodiosVO;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * Controller para la busqueda de Depositantes en la Administracion de Custodios
 *
 * @author Erik Vera Montoya.
 * @version 1.0
 *
 */
public class AdmonCustodiosDepositantesConsultaDepLiqController extends ControllerBase {

	/** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(AdmonCustodiosDepositantesConsultaDepLiqController.class);
	
	/** Objeto para reprentar la emision seleccionada */
	private SicEmision emisionActual;
	
	/**Objeto para indicar el depositante a dar de alta*/
	private SicDetalle depositante;
	
	/**Cadena de idFolio para Agente*/
	private String claveParticipante;
	
	/**Cadena para la cuenta de Agente*/
	private String cuentaParticipante;
	
	/**Cadena para nombre Corto del Agente*/
	private String nombreCortoParticipante;
	
	/**Objeto para indicar el custodio seleccionado*/
	private ConsultaCustodiosVO custodioActual;
	
	/** Servicio para la consulta de fideicomiso */
	private DivisionInternacionalService divisionInternacionalService;
	
	/** bandera para la consulta Ejecutada de Depositantes*/
	private boolean consultaDepLiqEjecutada;
	
	/**
	 * Invoca el metodo que limpia la pantalla de buscar Emision
	 * @param event Evento generado durante la solicitud
	 */
	public void limpiarForma(ActionEvent event){
		depositante= new SicDetalle();
		paginaVO= new PaginaVO();
		paginaVO.setOffset(Constantes.CERO_INTEGER);
		paginaVO.setRegistrosXPag(50);
		consultaDepLiqEjecutada = false;
		
	}
	
	public void buscarDepositantes(ActionEvent event){
		ejecutarConsulta();
		consultaDepLiqEjecutada=true;
	}
	
	@Override
	public String ejecutarConsulta() {
		log.info("Entrando a AdmonCustodiosDepositantesConsultaDepLiqController.ejecutarConsulta");
		paginaVO = divisionInternacionalService.getListaSicDetalle(custodioActual.getCatBic(), depositante.getBicDepLiq(), depositante.getIdDepLiq(), depositante.getDepLiq(), paginaVO);
		if (paginaVO == null && paginaVO.getRegistros() == null && paginaVO.getRegistros().isEmpty()) {
			paginaVO = new PaginaVO();			
		}
		return null;
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
	 * @param emisionActual el valor del atributo emisionActual a establecer
	 */
	public void setEmisionActual(SicEmision emisionActual) {
		this.emisionActual = emisionActual;
	}

	/**
	 * Obtiene el valor del atributo depositante
	 *
	 * @return el valor del atributo depositante
	 */
	public SicDetalle getDepositante() {
		return depositante;
	}

	/**
	 * Establece el valor del atributo depositante
	 *
	 * @param depositante el valor del atributo depositante a establecer
	 */
	public void setDepositante(SicDetalle depositante) {
		this.depositante = depositante;
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
	 * @param claveParticipante el valor del atributo claveParticipante a establecer
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
	 * @param cuentaParticipante el valor del atributo cuentaParticipante a establecer
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
	 * @param nombreCortoParticipante el valor del atributo nombreCortoParticipante a establecer
	 */
	public void setNombreCortoParticipante(String nombreCortoParticipante) {
		this.nombreCortoParticipante = nombreCortoParticipante;
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
	 * @param divisionInternacionalService el valor del atributo divisionInternacionalService a establecer
	 */
	public void setDivisionInternacionalService(
			DivisionInternacionalService divisionInternacionalService) {
		this.divisionInternacionalService = divisionInternacionalService;
	}

	/**
	 * Obtiene el valor del atributo consultaDepLiqEjecutada
	 *
	 * @return el valor del atributo consultaDepLiqEjecutada
	 */
	public boolean isConsultaDepLiqEjecutada() {
		return consultaDepLiqEjecutada;
	}

	/**
	 * Establece el valor del atributo consultaDepLiqEjecutada
	 *
	 * @param consultaDepLiqEjecutada el valor del atributo consultaDepLiqEjecutada a establecer
	 */
	public void setConsultaDepLiqEjecutada(boolean consultaDepLiqEjecutada) {
		this.consultaDepLiqEjecutada = consultaDepLiqEjecutada;
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
}

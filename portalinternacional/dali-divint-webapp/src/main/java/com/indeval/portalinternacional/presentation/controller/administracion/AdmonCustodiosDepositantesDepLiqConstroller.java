/**
 * Sistema Internacional de Cotizaciones
 * 
 * Bursatec - 2H Software SA de CV
 *
 * Creado: Nov 8, 2008
 */
package com.indeval.portalinternacional.presentation.controller.administracion;

import java.util.List;

import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.util.ConsultaCatalogoService;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portalinternacional.middleware.servicios.modelo.SicDetalle;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * Controller para la Administracion de Custodios y Depositantes
 *
 * @author Erik Vera Montoya.
 * @version 1.0
 *
 */
public class AdmonCustodiosDepositantesDepLiqConstroller extends ControllerBase {
	/** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(AdmonCustodiosDepositantesDepLiqConstroller.class);
	
	/**Objeto para indicar el detalle actual*/
	private SicDetalle detalleActual;
	
	/**Objeto para indicar el depositante a dar de alta*/
	private SicDetalle depositante;
	
	/**Cadena de idFolio para Agente*/
	private String claveParticipante;
	
	/**Cadena para la cuenta de Agente*/
	private String cuentaParticipante;
	
	/**Cadena para nombre Corto del Agente*/
	private String nombreCortoParticipante;
	
	/**Servicio para traer los datos del participante*/
	private ConsultaCatalogoService consultaCatalogoService;
	
	@SuppressWarnings("unchecked")
	public void setLista( List lista ) {
		log.info("Entrando a AdmonCustodiosDepositantesDepLiqConstroller.setLista");
		paginaVO = new PaginaVO();
		if( lista != null && lista.size() > 0 ) {
			paginaVO.setOffset(0);
			paginaVO.setRegistros(lista);
			paginaVO.setRegistrosXPag(1);
			paginaVO.setTotalRegistros(lista.size());
		} 
		ejecutarConsulta();
	}
	
	public void limpiarForma( ActionEvent event ) {
		//claveParticipante = null;
		//cuentaParticipante = null;
		//nombreCortoParticipante = null;
		depositante = new SicDetalle();
	}
	
	public void limpiar() {
		log.info("Entrando a AdmonCustodiosDepositantesDepLiqConstroller.limpiar");
		paginaVO = new PaginaVO();
		detalleActual = new SicDetalle();
	}
	
	public String ejecutarConsulta() {
		log.info("Entrando a AdmonCustodiosDepositantesDepLiqConstroller.ejecutarConsulta");
		if( paginaVO.getRegistros() != null && 
				paginaVO.getRegistros().size() > 0 ) {
			detalleActual = (SicDetalle)paginaVO.getRegistros().get(paginaVO.getOffset());
		} else {
			detalleActual = new SicDetalle();
		}
		return null;
	}
	
	public AgenteVO construyeAgente() {
		AgenteVO retorno =  new AgenteVO();
		if( isIdFolioValido() ) {
			retorno.setId(claveParticipante.substring(0,2));
			retorno.setFolio(claveParticipante.substring(2));
		}
		if( isCuentaValida() ) {
			retorno.setCuenta(cuentaParticipante);
		}
		return retorno;
	}
	
	private boolean isIdFolioValido() {
		if( StringUtils.isNotBlank(claveParticipante) &&
				claveParticipante.length() == 5 &&
				StringUtils.isNumeric(claveParticipante) ) {
			return true;
		}
		return false;
	}
	
	private boolean isCuentaValida() {
		if( StringUtils.isNotBlank(cuentaParticipante) &&
				cuentaParticipante.length() == 4 &&
				StringUtils.isNumeric(cuentaParticipante) ) {
			return true; 
		}
		return false;
	}
	
	public void validaParticipante(ActionEvent event) {
		log.info("Entrando a AdmonCustodiosDepositantesConstroller.validaParticipante");
		if( isIdFolioValido() ) {
			Institucion ins = consultaCatalogoService.findInstitucionByClaveFolio(claveParticipante);
			if( ins != null ) {
				nombreCortoParticipante = ins.getRazonSocial();
				log.info("INSTITUCION: [" + nombreCortoParticipante + "]");
			} else {
				log.info("NO HAY INSTITUCION");
			}
		}
	}

	/**
	 * @param detalleActual the detalleActual to set
	 */
	public void setDetalleActual(SicDetalle detalleActual) {
		this.detalleActual = detalleActual;
	}

	/**
	 * @return the detalleActual
	 */
	public SicDetalle getDetalleActual() {
		return detalleActual;
	}

	/**
	 * @return the claveParticipante
	 */
	public String getClaveParticipante() {
		return claveParticipante;
	}

	/**
	 * @param claveParticipante the claveParticipante to set
	 */
	public void setClaveParticipante(String claveParticipante) {
		this.claveParticipante = claveParticipante;
	}

	/**
	 * @return the cuentaParticipante
	 */
	public String getCuentaParticipante() {
		return cuentaParticipante;
	}

	/**
	 * @param cuentaParticipante the cuentaParticipante to set
	 */
	public void setCuentaParticipante(String cuentaParticipante) {
		this.cuentaParticipante = cuentaParticipante;
	}

	/**
	 * @return the nombreCortoParticipante
	 */
	public String getNombreCortoParticipante() {
		return nombreCortoParticipante;
	}

	/**
	 * @param nombreCortoParticipante the nombreCortoParticipante to set
	 */
	public void setNombreCortoParticipante(String nombreCortoParticipante) {
		this.nombreCortoParticipante = nombreCortoParticipante;
	}

	/**
	 * @return the depositante
	 */
	public SicDetalle getDepositante() {
		return depositante;
	}

	/**
	 * @param depositante the depositante to set
	 */
	public void setDepositante(SicDetalle depositante) {
		this.depositante = depositante;
	}

	/**
	 * @return the consultaCatalogoService
	 */
	public ConsultaCatalogoService getConsultaCatalogoService() {
		return consultaCatalogoService;
	}

	/**
	 * @param consultaCatalogoService the consultaCatalogoService to set
	 */
	public void setConsultaCatalogoService(
			ConsultaCatalogoService consultaCatalogoService) {
		this.consultaCatalogoService = consultaCatalogoService;
	}
}

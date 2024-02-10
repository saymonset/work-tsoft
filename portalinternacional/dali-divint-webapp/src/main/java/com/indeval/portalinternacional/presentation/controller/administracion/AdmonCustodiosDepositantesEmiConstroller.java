/**
 * Sistema Internacional de Cotizaciones
 * 
 * Bursatec - 2H Software SA de CV
 *
 * Creado: Nov 8, 2008
 */
package com.indeval.portalinternacional.presentation.controller.administracion;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.util.ConsultaCatalogoService;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portalinternacional.middleware.servicios.modelo.SicEmision;
import com.indeval.portalinternacional.persistence.dao.SicEmisionDao;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * Controller para la Administracion de Custodios y Depositantes
 *
 * @author Erik Vera Montoya.
 * @version 1.0
 *
 */
public class AdmonCustodiosDepositantesEmiConstroller extends ControllerBase {
	/** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(AdmonCustodiosDepositantesEmiConstroller.class);
	
	/**Objeto para reprentar la emision seleccionada*/
	private SicEmision emisionActual;
	
	/**Cadena de idFolio para Agente*/
	private String claveParticipante;
	
	/**Cadena para la cuenta de Agente*/
	private String cuentaParticipante;
	
	/**Cadena para nombre Corto del Agente*/
	private String nombreCortoParticipante;
	
	/**Cadenas que representan los datos de la emision*/
	private EmisionVO emisionVO;
	
	/**Forma de Operar*/
	private String formaOperar;
	
	/**Servicio para traer los datos del participante*/
	private ConsultaCatalogoService consultaCatalogoService;
	
	/**Dao para la busqueda de la emision*/
	private SicEmisionDao sicEmisionDao;
	
	private String idEmision;
	
	/**
	 * Asigna las opciones predeterminadas para cuando se carga la página por
	 * primerva vez.
	 * 
	 * @return nulo, este método no requiere retornar un valor
	 */
	public String getInit(){		
		FacesContext ctx = FacesContext.getCurrentInstance();
		String id= ctx.getExternalContext().getRequestParameterMap().get("idEmision");		
		if (claveParticipante == null) {
			claveParticipante = ctx.getExternalContext().getRequestParameterMap().get("clave") != null ? ctx.getExternalContext().getRequestParameterMap().get("clave") : null;			
		}
		if (cuentaParticipante == null) {			
			cuentaParticipante = ctx.getExternalContext().getRequestParameterMap().get("cuenta") != null ? ctx.getExternalContext().getRequestParameterMap().get("cuenta") : null;
		}
		if (nombreCortoParticipante == null) {			
			nombreCortoParticipante = ctx.getExternalContext().getRequestParameterMap().get("nombre") != null ? ctx.getExternalContext().getRequestParameterMap().get("nombre") : null;
		}
		if (id != null) {
			
			SicEmision emisionEditar = sicEmisionDao.findSicEmsionByPk(new Long(id));
			if (emisionEditar != null ) {
				emisionActual = emisionEditar;
			}
			
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public void setLista( List lista ) {
		log.info("Entrando a AdmonCustodiosDepositantesEmiConstroller.setLista");
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
		emisionVO = new EmisionVO();
		formaOperar = null;
	}
	
	public void limpiar() {
		log.info("Entrando a AdmonCustodiosDepositantesDepLiqConstroller.limpiar");
		paginaVO = new PaginaVO();
		emisionActual = new SicEmision();
	}
	
	public String ejecutarConsulta() {
		log.info("Entrando a AdmonCustodiosDepositantesDepLiqConstroller.ejecutarConsulta");
		if( paginaVO.getRegistros() != null && 
				paginaVO.getRegistros().size() > 0 ) {
			emisionActual = (SicEmision)paginaVO.getRegistros().get(paginaVO.getOffset());
		} else {
			emisionActual = new SicEmision();
		}
		return null;
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

	/**
	 * @param emisionActual the emisionActual to set
	 */
	public void setEmisionActual(SicEmision emisionActual) {
		this.emisionActual = emisionActual;
	}

	/**
	 * @return the emisionActual
	 */
	public SicEmision getEmisionActual() {
		return emisionActual;
	}

	/**
	 * @return the claveParticipante
	 */
	public String getClaveParticipante() {
		return claveParticipante;
	}

	/**
	 * @return the cuentaParticipante
	 */
	public String getCuentaParticipante() {
		return cuentaParticipante;
	}

	/**
	 * @return the nombreCortoParticipante
	 */
	public String getNombreCortoParticipante() {
		return nombreCortoParticipante;
	}

	/**
	 * @param claveParticipante the claveParticipante to set
	 */
	public void setClaveParticipante(String claveParticipante) {
		this.claveParticipante = claveParticipante;
	}

	/**
	 * @param cuentaParticipante the cuentaParticipante to set
	 */
	public void setCuentaParticipante(String cuentaParticipante) {
		this.cuentaParticipante = cuentaParticipante;
	}

	/**
	 * @param nombreCortoParticipante the nombreCortoParticipante to set
	 */
	public void setNombreCortoParticipante(String nombreCortoParticipante) {
		this.nombreCortoParticipante = nombreCortoParticipante;
	}

	/**
	 * @param consultaCatalogoService the consultaCatalogoService to set
	 */
	public void setConsultaCatalogoService(
			ConsultaCatalogoService consultaCatalogoService) {
		this.consultaCatalogoService = consultaCatalogoService;
	}

	/**
	 * @param emisionVO the emisionVO to set
	 */
	public void setEmisionVO(EmisionVO emisionVO) {
		this.emisionVO = emisionVO;
	}

	/**
	 * @return the emisionVO
	 */
	public EmisionVO getEmisionVO() {
		return emisionVO;
	}

	/**
	 * @param formaOperar the formaOperar to set
	 */
	public void setFormaOperar(String formaOperar) {
		this.formaOperar = formaOperar;
	}

	/**
	 * @return the formaOperar
	 */
	public String getFormaOperar() {
		return formaOperar;
	}

	/**
	 * Obtiene el valor del atributo idEmision
	 *
	 * @return el valor del atributo idEmision
	 */
	public String getIdEmision() {
		return idEmision;
	}

	/**
	 * Establece el valor del atributo idEmision
	 *
	 * @param idEmision el valor del atributo idEmision a establecer
	 */
	public void setIdEmision(String idEmision) {
		this.idEmision = idEmision;
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

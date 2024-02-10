/**
 * 2H Software - Bursatec - INDEVAL Portal DALI
 * 
 * Jul 2, 2008
 */
package com.indeval.portalinternacional.presentation.controller.beneficiarios;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaNombreBeneficiarioParam;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * Controller para la Captura de Beneficiarios de Division Internacional
 * 
 * @author Rafael Ibarra
 * @version 1.0
 */
public class MostrarBeneficiariosController extends ControllerBase {

	/** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(MostrarBeneficiariosController.class);
	
	/** Servicio de Beneficiarios */
	private ControlBeneficiariosService controlBeneficiariosService;
	/** Parametros enviados por el Request */
	private Map<String, String> params = null;
	/** Mapa que contiene la lista de custodios para la tabla */
	private Map<Long, String> mapaCustodios;
	
	
	/**
	 * Constructor de Captura Operaciones
	 */
	public MostrarBeneficiariosController() {
		
	}
	
	public String ejecutarConsulta() {
		ConsultaNombreBeneficiarioParam paramConsulta = new ConsultaNombreBeneficiarioParam();
		paramConsulta.setNombres(params.get("nombre"));
		paramConsulta.setApellidoPaterno(params.get("apPaterno"));
		paramConsulta.setApellidoMaterno(params.get("apMaterno"));
		paramConsulta.setRazonSocial(params.get("razonSocial"));
		paramConsulta.setRFC(params.get("rfc"));
		Long idCustodio = 0l;
		Long idTipoBeneficiario = 0l;
		
		try {
			idCustodio = Long.valueOf(params.get("custodio"));
			idTipoBeneficiario = Long.valueOf(params.get("tipoBeneficiario"));
		} catch (NumberFormatException e) {
			log.error("Error al convertir los parametros", e);
		}
		
		paramConsulta.setCustodio(idCustodio);
		paramConsulta.setIdTipoBeneficiario(idTipoBeneficiario);
		
//		paginaVO = controlBeneficiariosService.consultaBeneficiariosByName(paramConsulta);
		inicializaMapaCustodios();
		return null;
	}
	
	/**
	 * Inicializa mapa de custodios
	 */
	private void inicializaMapaCustodios() {
		mapaCustodios = new HashMap<Long, String>();
		
		List<Object[]> lista = controlBeneficiariosService.obtieneCatBic();
		
		for( Object[] bene : lista ) {
			mapaCustodios.put((Long)bene[0], (String)bene[1]);
		}
	}

	/**
	 * Asigna las opciones predeterminadas para cuando se carga la pagina por
	 * primerva vez.
	 * 
	 * @return nulo, este metodo no requiere retornar un valor
	 */
	public String getInit() {
		params = new HashMap<String, String>();
        Set<String> keys = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().keySet();
        
        for (String key : keys) {
            params.put(key, FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key));
        }
        
        FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        ejecutarConsulta();
		return null;
	}
	
	/**
	 * @param controlBeneficiariosService the controlBeneficiariosService to set
	 */
	public void setControlBeneficiariosService(
			ControlBeneficiariosService controlBeneficiariosService) {
		this.controlBeneficiariosService = controlBeneficiariosService;
	}

	 /**
     * @return the params
     */
    public Map<String, String> getParams() {
        return params;
    }

    /**
     * @param params the params to set
     */
    public void setParams(Map<String, String> params) {
        this.params = params;
    }

	/**
	 * @param mapaCustodios the mapaCustodios to set
	 */
	public void setMapaCustodios(Map<Long, String> mapaCustodios) {
		this.mapaCustodios = mapaCustodios;
	}

	/**
	 * @return the mapaCustodios
	 */
	public Map<Long, String> getMapaCustodios() {
		return mapaCustodios;
	}


}

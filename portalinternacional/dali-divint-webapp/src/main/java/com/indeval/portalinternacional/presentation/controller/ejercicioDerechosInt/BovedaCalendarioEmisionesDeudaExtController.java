/**
 * Bursatec - INDEVAL Portal DALI
 * 
 * Ago 12, 2011
 */
package com.indeval.portalinternacional.presentation.controller.ejercicioDerechosInt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portaldali.persistence.modelo.TipoBoveda;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService;
import com.indeval.portalinternacional.middleware.servicios.modelo.CalendarioDerechos;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * Controller crear mensajes 103/202 e instruirlos swift del calendario de emisiones de deuda ext
 * 
 * @author Luis Roberto Munoz
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class BovedaCalendarioEmisionesDeudaExtController extends ControllerBase {

	/** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(BovedaCalendarioEmisionesDeudaExtController.class);
	
	/** Servicio  */
	private DivisionInternacionalService divisionInternacionalService;
	/** Parametros enviados por el Request */
	private Map<String, String> params;
	
	private String boveda;
	private Boolean automatico;
	
	private CalendarioDerechos derecho;
	
	private List<SelectItem> bovedas;
	
	public BovedaCalendarioEmisionesDeudaExtController() {
		
	}
	
	public String ejecutarConsulta() {
		Long id=null;
		if(params.get("idCalendario")!=null ){
			id=Long.valueOf(params.get("idCalendario"));
		}
		if(id != null){
			this.derecho=divisionInternacionalService.consultaCalendarioDerechosById(id);
		}
		return null;
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
     * Obtiene una lista de <code>SelectItem</code> para popular el combo box de bovedas de efectivo internacional
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<SelectItem> getBovedas(){
    	if(this.bovedas != null && this.bovedas.size() > 0){
			return this.bovedas;
		}
    	List<Boveda> bovedas = divisionInternacionalService.consultaBovedas(TipoBoveda.INTERNACIONAL_EFECTIVO);
    	List<SelectItem> listaSelectBovedas = new ArrayList<SelectItem>();
    	for (Boveda boveda : bovedas){
    		listaSelectBovedas.add(new SelectItem(String.valueOf(boveda.getIdBoveda()), boveda.getNombreCorto()));
    	}
    	this.bovedas = listaSelectBovedas;
    	return listaSelectBovedas;
    }

    
    public String instruirMensaje(){
    	log.debug("======================== INSTRUYENDO MENSAJE 103/202 =================================");
    	//Long idBoveda = Long.valueOf(boveda); //no se requiere en este momento seleccionar la boveda
    	if( !automatico){
    		return null;
    	}
    	divisionInternacionalService.instruyeMensajeRetiro(derecho.getIdCalendario(),0l);
    	return null;
    }
	/**
	 * @param divisionInternacionalService the divisionInternacionalService to set
	 */
	public void setDivisionInternacionalService(
			DivisionInternacionalService divisionInternacionalService) {
		this.divisionInternacionalService = divisionInternacionalService;
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
	 * @return the derecho
	 */
	public CalendarioDerechos getDerecho() {
		return derecho;
	}

	/**
	 * @param derecho the derecho to set
	 */
	public void setDerecho(CalendarioDerechos derecho) {
		this.derecho = derecho;
	}

	/**
	 * @return the boveda
	 */
	public String getBoveda() {
		return boveda;
	}

	/**
	 * @param boveda the boveda to set
	 */
	public void setBoveda(String boveda) {
		this.boveda = boveda;
	}

	/**
	 * @return the automatico
	 */
	public Boolean getAutomatico() {
		return automatico;
	}

	/**
	 * @param automatico the automatico to set
	 */
	public void setAutomatico(Boolean automatico) {
		this.automatico = automatico;
	}	
	
}

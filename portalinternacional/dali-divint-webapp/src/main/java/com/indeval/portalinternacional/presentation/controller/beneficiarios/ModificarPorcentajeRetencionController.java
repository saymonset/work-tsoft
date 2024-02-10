package com.indeval.portalinternacional.presentation.controller.beneficiarios;

import javax.ejb.EJBException;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

public class ModificarPorcentajeRetencionController extends ControllerBase implements Constantes{
	
	/** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(ModificarPorcentajeRetencionController.class);
    
    /** Porcentaje de Retencion a guardar*/
    private String nuevoPorcentajeRetencion;
    
    /** id del Beneficiario*/
    private String idBeneficiario;
    
    /** uoi del Beneficiario*/
    private String uoi;
    
    /** nombre completo del Beneficiario*/
    private String nombreCompletoBeneficiario;
    
    /** especifica si es persona fisica el Beneficiario*/
    private Boolean isPersonaFisica;
    
    /** razon social del Beneficiario*/
    private String razonSocial;
    
    /** Pais de incorporacion*/
    private String paisIncorporacion;
    
    /** Porcentaje de retencion actual del Beneficiario*/
    private String porcentajeRetencionActual;
    
    /** Tipo Beneficairio*/
    private String tipoBeneficiario;
    
    /** Nombre del Custodio*/
    private String nombreCustodio;
    
    /**Tipo de Formato*/
    private String tipoFormato;
        
	/** Servicio de Beneficiarios */
    private ControlBeneficiariosService controlBeneficiariosService;
    
    /**indica si ya se ejecuto el init*/
    private Boolean ejecutaInit=true;
  
	/**Constructor*/
    public  ModificarPorcentajeRetencionController(){
    	
    }
    /**
     * Asigna las opciones predeterminadas para cuando se carga la página por
     * primerva vez.
     *
     * @return nulo, este método no requiere retornar un valor
     */
    public String getInit() {
    	if(ejecutaInit){
    		    ejecutaInit=false;
    		    FacesContext facesContext = FacesContext.getCurrentInstance();
    	        idBeneficiario = facesContext.getExternalContext().getRequestParameterMap().get("idBeneficiario");
    	        uoi = facesContext.getExternalContext().getRequestParameterMap().get("uoi");
    	        String nombreCompletoBeneficiarioCadena = facesContext.getExternalContext().getRequestParameterMap().get("nombreBeneficiario");
    	        String isPersonaFisicaCadena = facesContext.getExternalContext().getRequestParameterMap().get("isPersonaFisica");
    	        String razonSocialCadena = facesContext.getExternalContext().getRequestParameterMap().get("razonSocial");
    	        paisIncorporacion = facesContext.getExternalContext().getRequestParameterMap().get("pais");
    	        porcentajeRetencionActual = facesContext.getExternalContext().getRequestParameterMap().get("porcentajeRetencion");
    	        tipoBeneficiario = facesContext.getExternalContext().getRequestParameterMap().get("tipoBeneficiario");
    	        nombreCustodio = facesContext.getExternalContext().getRequestParameterMap().get("nombreCustodio");
    	        tipoFormato = facesContext.getExternalContext().getRequestParameterMap().get("tipoFormato");                     
    	        
    	        try {        	
    	        	validaParametros();
    	        	isPersonaFisica = Boolean.valueOf(isPersonaFisicaCadena);
    	        	
    	        	if(!tipoFormato.trim().equalsIgnoreCase("W9") && isPersonaFisica){
    	        		nombreCompletoBeneficiario = nombreCompletoBeneficiarioCadena;
    	        	}else if(tipoFormato.trim().equalsIgnoreCase("W9") && StringUtils.isNotBlank(razonSocialCadena)){
    	        		nombreCompletoBeneficiario = razonSocialCadena;
    	        		isPersonaFisica = true;
    	        	}else if(!tipoFormato.trim().equalsIgnoreCase("W9") && !isPersonaFisica){
    	        		razonSocial = razonSocialCadena;
    	        	}
    	        		           
    	        } catch (Exception e) {
    	        	String messageError = "Hubo un error al recibir los parámetros";
    	            log.error(messageError, e);
    	            addWarnMessage(messageError);    	            
    	            return null;
    	        }
    	}
     
        return null;
    }
    
    /**
     * Metodo para validar los paremtros
     * */
           
    private void validaParametros() throws Exception{    	    	    	    	
    	if(StringUtils.isBlank(idBeneficiario) || StringUtils.isBlank(porcentajeRetencionActual) || StringUtils.isBlank(tipoFormato)
    			|| StringUtils.isBlank(tipoBeneficiario)){
    		throw new Exception();    		    	    		    		
    	}    
    }
    
    /**
     * Metodo para guardar el nuevo Porcentaje de Retencion
     * @return nulo, este método no requiere retornar un valor
     * */
    public String guardar(){
    	ejecutaInit=false;
    	    	
    	try{     		
    		Double nuevoPorcentaje = null;
    		
    		// se valida el nuevo porcentaje de retencion
    		if(StringUtils.isBlank(nuevoPorcentajeRetencion)){
    			addErrorMessage("Error: No ha ingresado valor");    			
    			throw new Exception();
    		}else{
    			if(!nuevoPorcentajeRetencion.matches(PATRON_PORCENTAJE_RETENCION)){
    				addErrorMessage("Error: El patrón numérico es ###.##");    				
    				throw new Exception();
    			}
    			nuevoPorcentaje = Double.valueOf(nuevoPorcentajeRetencion.trim());
    			if(nuevoPorcentaje > VALOR_MAXIMO_PORCENTAJE_RETENCION || nuevoPorcentaje < VALOR_MINIMO_PORCENTAJE_RETENCION){
    				addErrorMessage("Error: Valor fuera del rango permitido 0-100");    				
    				throw new Exception();
    			}    		    		
    		}
    		//se llama al servicio para realizar la actualizacion del porcentaje de retencion
    		Long idBene = Long.valueOf(idBeneficiario.trim());    		
    		controlBeneficiariosService.modificaPorcentajeRetencion(idBene,nuevoPorcentaje);
    		String mensajeExito = "Porcentaje modificado con éxito";
    		addMessage(mensajeExito);            
            porcentajeRetencionActual = nuevoPorcentaje.toString();            
    	} catch (EJBException ejbException) {
            log.error("ERROR", ejbException);
            trataExcepcion(ejbException);
        } catch (Exception ex) {
        	log.error("ERROR", ex);        	
        }    	    	        
    	return null;    	    	    
    }
    
    /**
     * Metodo para cerrar la ventana emergente
     * @return nulo, este método no requiere retornar un valor
     * */
    public String cancelar(){
    	ejecutaInit=false;
    	return null;
    }
    
    /**geters and seters*/
    
    public String getNuevoPorcentajeRetencion() {
  		return nuevoPorcentajeRetencion;
  	}


  	public String getIdBeneficiario() {
		return idBeneficiario;
	}
	public void setIdBeneficiario(String idBeneficiario) {
		this.idBeneficiario = idBeneficiario;
	}
	public String getUoi() {
		return uoi;
	}
	public void setUoi(String uoi) {
		this.uoi = uoi;
	}
	public String getNombreCompletoBeneficiario() {
		return nombreCompletoBeneficiario;
	}
	public void setNombreCompletoBeneficiario(String nombreCompletoBeneficiario) {
		this.nombreCompletoBeneficiario = nombreCompletoBeneficiario;
	}
	public Boolean getIsPersonaFisica() {
		return isPersonaFisica;
	}
	public void setIsPersonaFisica(Boolean isPersonaFisica) {
		this.isPersonaFisica = isPersonaFisica;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getPaisIncorporacion() {
		return paisIncorporacion;
	}
	public void setPaisIncorporacion(String paisIncorporacion) {
		this.paisIncorporacion = paisIncorporacion;
	}
	public String getPorcentajeRetencionActual() {
		return porcentajeRetencionActual;
	}
	public void setPorcentajeRetencionActual(String porcentajeRetencionActual) {
		this.porcentajeRetencionActual = porcentajeRetencionActual;
	}
	public String getTipoBeneficiario() {
		return tipoBeneficiario;
	}
	public void setTipoBeneficiario(String tipoBeneficiario) {
		this.tipoBeneficiario = tipoBeneficiario;
	}
	public String getNombreCustodio() {
		return nombreCustodio;
	}
	public void setNombreCustodio(String nombreCustodio) {
		this.nombreCustodio = nombreCustodio;
	}
	public String getTipoFormato() {
		return tipoFormato;
	}
	public void setTipoFormato(String tipoFormato) {
		this.tipoFormato = tipoFormato;
	}
	public void setNuevoPorcentajeRetencion(String nuevoPorcentajeRetencion) {
  		this.nuevoPorcentajeRetencion = nuevoPorcentajeRetencion;
  	}
	  public Boolean getEjecutaInit() {
			return ejecutaInit;
		}
		public void setEjecutaInit(Boolean ejecutaInit) {
			this.ejecutaInit = ejecutaInit;
		}
	
    /** se inyecta el servicio */
    public void setControlBeneficiariosService(ControlBeneficiariosService controlBeneficiariosService){
    	this.controlBeneficiariosService = controlBeneficiariosService;
    	
    }
	
}

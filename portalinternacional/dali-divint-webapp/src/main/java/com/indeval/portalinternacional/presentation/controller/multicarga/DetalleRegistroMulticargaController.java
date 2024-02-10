package com.indeval.portalinternacional.presentation.controller.multicarga;

import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.MulticargaInternacionalService;
import com.indeval.portalinternacional.middleware.servicios.modelo.RegistroMulticarga;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

public class DetalleRegistroMulticargaController extends ControllerBase{

	/** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(DetalleRegistroMulticargaController.class);
	
    private RegistroMulticarga registroMulticarga;
    
    private String nombreArchivo;
    
    private MulticargaInternacionalService multicargaInternacionalService;
    
    
    /**
     * m&eacute;todo de inicio, toma los parametros y realiza la consulta
     * */
	public String getInit() {
		this.registroMulticarga = null;
		this.nombreArchivo = null;	
		FacesContext facesContext = FacesContext.getCurrentInstance();
		String idMulticarga = facesContext.getExternalContext().getRequestParameterMap().get("idMulticarga");
		String nombreArchivo = facesContext.getExternalContext().getRequestParameterMap().get("nombreArchivo");		
		try{
			this.nombreArchivo = nombreArchivo; 
			Long id = Long.valueOf(idMulticarga);
			log.debug("idMulticarga"+idMulticarga);
			ejecutaConsulta(id);
		}catch(BusinessException be){
			addErrorMessage(be.getMessage());
			be.printStackTrace();			
		}catch(Exception e){
			log.error(e.getMessage(), e);
			addMessage("Hubo un error al recibir los parametros");
	        return null;			
		}	
        return null;
    }
	
	/**
	 * m&eacute;todo que ejecuta la consulta por el id de la operaci&oacute;n Multicarga
	 * */	
    private void ejecutaConsulta(Long idMulticarga) throws BusinessException{    	
    	this.registroMulticarga = multicargaInternacionalService.consultaRegistrosByIdMulticarga(idMulticarga);    	
    }


	public RegistroMulticarga getRegistroMulticarga() {
		return registroMulticarga;
	}


	public void setRegistroMulticarga(RegistroMulticarga registroMulticarga) {
		this.registroMulticarga = registroMulticarga;
	}


	public String getNombreArchivo() {
		return nombreArchivo;
	}


	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	//se inyecta el servicio
	public void setMulticargaInternacionalService(
			MulticargaInternacionalService multicargaInternacionalService) {
		this.multicargaInternacionalService = multicargaInternacionalService;
	}
    			
}

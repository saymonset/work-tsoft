/**
 * CMMV - Portal Dali
 * Copyrigth (c) 2017 CMMV. All Rights Reserved.
 */
package com.indeval.portaldali.presentation.controller.estadocuenta;

import javax.faces.context.FacesContext;

import com.indeval.portaldali.middleware.services.cfi.CfiService;
import com.indeval.portaldali.modelo.to.cfi.DetalleCfiTO;

/**
 * Controller para la consulta del detalle del CFI.
 * 
 * @author Pablo Balderas
 */
public class ConsultaDetalleCfiBean {
	
	/** Servicio para la consulta */
	private CfiService cfiService;

	/** Cfi para el detalle */
	private String cfi;
	
	/** Detalle del cfi */
	private DetalleCfiTO detalleCfiTO;
	
	/**
	 * Inicializa la pantalla.
	 * @return null
	 */
	public String getInit(){
		FacesContext fc = FacesContext.getCurrentInstance();
		cfi = (String) fc.getExternalContext().getRequestParameterMap().get("cfi"); 
		detalleCfiTO = cfiService.findDetalleCfi(cfi);		
		return null;
	}
	
	/**
	 * Método para establecer el atributo cfiService
	 * @param cfiService El valor del atributo cfiService a establecer.
	 */
	public void setCfiService(CfiService cfiService) {
		this.cfiService = cfiService;
	}

	/**
	 * Método para obtener el atributo detalleCfiTO
	 * @return El atributo detalleCfiTO
	 */
	public DetalleCfiTO getDetalleCfiTO() {
		return detalleCfiTO;
	}

	/**
	 * Método para establecer el atributo detalleCfiTO
	 * @param detalleCfiTO El valor del atributo detalleCfiTO a establecer.
	 */
	public void setDetalleCfiTO(DetalleCfiTO detalleCfiTO) {
		this.detalleCfiTO = detalleCfiTO;
	}
	
}

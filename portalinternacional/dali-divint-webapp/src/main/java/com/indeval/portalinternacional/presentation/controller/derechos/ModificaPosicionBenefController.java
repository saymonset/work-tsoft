package com.indeval.portalinternacional.presentation.controller.derechos;

import javax.faces.context.FacesContext;

import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlDerechosService;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

public class ModificaPosicionBenefController extends ControllerBase{
	
	private Long asignacion;
	private Long idBeneficiarioDerecho;
	private ControlDerechosService derechosService;
	private long asignacionActual;
	private long posicicionNoAsignada;
	
	public ModificaPosicionBenefController(){
		FacesContext facesContext = FacesContext.getCurrentInstance();		
		idBeneficiarioDerecho = Long.valueOf(facesContext.getExternalContext().getRequestParameterMap().get("idBeneficiarioDerecho"));
		asignacionActual = Long.parseLong(facesContext.getExternalContext().getRequestParameterMap().get("asignacionActual"));
		posicicionNoAsignada = Long.parseLong(facesContext.getExternalContext().getRequestParameterMap().get("posicicionNoAsignada"));
	}
	
	
	public String modificarAsignacionBeneficiario(){
		boolean puedeModificar = false;
		String toSend = null;
		long posicionTotal = 0;
		
		if(asignacion == null || asignacion.longValue() < 0 ){
			addErrorMessage("La asignacion es requerida.");
		}
		
		posicionTotal = posicicionNoAsignada + asignacionActual;
		
		if((posicionTotal-asignacion.longValue()) >= 0){
			puedeModificar = true;
		}else{
			addErrorMessage("La asignacion no puede se mayor a ["+posicionTotal+"]");
		}
		
		if(puedeModificar){
			derechosService.actualizaPosicionBeneficiario(idBeneficiarioDerecho,asignacion);
			toSend = "asignacionActualizada";
		}
		
		return toSend;
	}

	public Long getAsignacion() {
		return asignacion;
	}

	public void setAsignacion(Long asignacion) {
		this.asignacion = asignacion;
	}

	public Long getIdBeneficiarioDerecho() {
		return idBeneficiarioDerecho;
	}

	public void setIdBeneficiarioDerecho(Long idBeneficiarioDerecho) {
		this.idBeneficiarioDerecho = idBeneficiarioDerecho;
	}


	public ControlDerechosService getDerechosService() {
		return derechosService;
	}


	public void setDerechosService(ControlDerechosService derechosService) {
		this.derechosService = derechosService;
	}

}

/**
 * 2H Software - Bursatec
 * 
 * Sistema de Consulta de Estados de Cuenta
 *
 * Dec 31, 2007
 *
 */
package com.indeval.portaldali.presentation.controller.estadocuenta;

import javax.faces.context.FacesContext;

import com.indeval.portaldali.middleware.dto.DetalleMovimientoEfectivoDTO;
import com.indeval.portaldali.middleware.services.estadocuenta.ConsultaDetalleMovimientoEfectivoService;


/**
 * Backing bean asociado a la pantalla de consulta de detalle de movimientos de 
 * efectivo. Esta clase se encarga de la invocación a los servicios de negocio 
 * relacionados con la consulta.
 * 
 * @author Pablo Julián Balderas Méndez
 * 
 */
public class ConsultaDetalleMovimientoEfectivoBean {

	/** DTO con la información del detalle del movimiento de efectivo */
	private DetalleMovimientoEfectivoDTO detalleMovimientoEfectivoDTO = null;
	
	/** Servicio de negocio para efectuar la consulta de de detalle */
	private ConsultaDetalleMovimientoEfectivoService consultaDetalleMovimientoEfectivoService = null;
	
	/**
	 * Constructor para la clase ConsultaDetalleMovimientoEfectivoBean.java
	 */
	public ConsultaDetalleMovimientoEfectivoBean() {
	}
	
	/**
	 * Ejecuta la consulta del detalle cuando la página se carga por pirmera vez.
	 * 
	 * @return nulo, este método no requiere retornar un valor
	 */
	public String getInit(){
		
		FacesContext fc = FacesContext.getCurrentInstance();
		String idRegistro = (String) fc.getExternalContext().getRequestParameterMap().get("idRegistro");
		Boolean isHistorico = new Boolean((String) fc.getExternalContext().getRequestParameterMap().get("isHistorico"));  
		detalleMovimientoEfectivoDTO =
			consultaDetalleMovimientoEfectivoService
				.consultarDetalleMovimientoEfectivo(new Long(idRegistro).longValue(),isHistorico);		
		return null;
	}	
	
	/**
	 * Obtiene el atributo detalleMovimientoEfectivoDTO
	 *
	 * @return El atrubuto detalleMovimientoEfectivoDTO
	 */
	public DetalleMovimientoEfectivoDTO getDetalleMovimientoEfectivoDTO() {
		return detalleMovimientoEfectivoDTO;
	}

	/**
	 * Establece la propiedad detalleMovimientoEfectivoDTO
	 *
	 * @param detalleMovimientoEfectivoDTO el campo detalleMovimientoEfectivoDTO a establecer
	 */
	public void setDetalleMovimientoEfectivoDTO(
			DetalleMovimientoEfectivoDTO detalleMovimientoEfectivoDTO) {
		this.detalleMovimientoEfectivoDTO = detalleMovimientoEfectivoDTO;
	}

	/**
	 * Obtiene el atributo consultaDetalleMovimientoEfectivoService
	 *
	 * @return El atrubuto consultaDetalleMovimientoEfectivoService
	 */
	public ConsultaDetalleMovimientoEfectivoService getConsultaDetalleMovimientoEfectivoService() {
		return consultaDetalleMovimientoEfectivoService;
	}

	/**
	 * Establece la propiedad consultaDetalleMovimientoEfectivoService
	 *
	 * @param consultaDetalleMovimientoEfectivoService el campo consultaDetalleMovimientoEfectivoService a establecer
	 */
	public void setConsultaDetalleMovimientoEfectivoService(
			ConsultaDetalleMovimientoEfectivoService consultaDetalleMovimientoEfectivoService) {
		this.consultaDetalleMovimientoEfectivoService = consultaDetalleMovimientoEfectivoService;
	}
	
}

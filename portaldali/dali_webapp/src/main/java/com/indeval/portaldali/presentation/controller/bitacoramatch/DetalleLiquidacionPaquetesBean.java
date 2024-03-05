package com.indeval.portaldali.presentation.controller.bitacoramatch;

import java.math.BigDecimal;
import java.util.Properties;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.bursatec.seguridad.presentation.constants.SeguridadConstants;
import com.indeval.portaldali.middleware.dto.MopInstruccionLiquidacionDTO;
import com.indeval.portaldali.middleware.dto.MopPaqueteDTO;
import com.indeval.portaldali.middleware.services.liquidacionpaquetes.DetalleLiquidacionPaqService;
import com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.BackingBeanBase;

public class DetalleLiquidacionPaquetesBean extends BackingBeanBase{
	/**
	 * Detalle de la operacion a mostrar
	 */ 
	private DetalleLiquidacionPaqService liquidacionPaqueteService;
	private Properties properties;
	private String mensaje;
	private MopPaqueteDTO paquete;
	/**
	 * Metodo de inicializacion de propiedades
	 * @return null
	 */
	public String getInit(){
		try {
			mensaje="";
			FacesContext ctx = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
			HttpSession session = request.getSession(false);
			String ticket = (String) session.getAttribute(SeguridadConstants.TICKET_SESION);
			String referenciaPaquete = ctx.getExternalContext().getRequestParameterMap().get("referenciaPaquete");
			String totalOperacionesPaquete = ctx.getExternalContext().getRequestParameterMap().get("totalOperacionesPaquete");
			String numeroOperacionPaquete = ctx.getExternalContext().getRequestParameterMap().get("numeroOperacionPaquete");
			String totalTitulosPaquete = ctx.getExternalContext().getRequestParameterMap().get("totalTitulosPaquete");
			String totalImportePaquete = ctx.getExternalContext().getRequestParameterMap().get("totalImportePaquete");
			String folioControl = ctx.getExternalContext().getRequestParameterMap().get("folioControl");
			if(referenciaPaquete!=null){
				MopPaqueteDTO paqueteDTO=new MopPaqueteDTO();
				paqueteDTO.setReferenciaPaquete(referenciaPaquete);
				try{
					if(totalOperacionesPaquete!=null && totalOperacionesPaquete.length()>0){
						paqueteDTO.setTotalOperacionesPaquete(Integer.valueOf(totalOperacionesPaquete));
					}
				}catch(NumberFormatException e){
					e.printStackTrace();
					logger.error(e.getMessage(), e);
				}
				try{
					if(totalImportePaquete!=null && totalImportePaquete.length()>0){
						paqueteDTO.setTotalImportePaquete(new BigDecimal(totalImportePaquete));
					}
				}catch(NumberFormatException e){
					e.printStackTrace();
					logger.error(e.getMessage(), e);
				}
				try{
					if(totalTitulosPaquete!=null && totalTitulosPaquete.length()>0){
						paqueteDTO.setTotalTitulosPaquete(Long.valueOf(totalTitulosPaquete));
					}
				}catch(NumberFormatException e){
					e.printStackTrace();
					logger.error(e.getMessage(), e);
				}
				try{
					if(numeroOperacionPaquete!=null && numeroOperacionPaquete.length()>0){
						paqueteDTO.setNumeroOperacionPaquete(Integer.valueOf(numeroOperacionPaquete));
					}
				}catch(NumberFormatException e){
					e.printStackTrace();
					logger.error(e.getMessage(), e);
				}
				try{
					if(folioControl!=null && folioControl.length()>0){
						paqueteDTO.setFolioControl(Long.valueOf(folioControl));
					}
				}catch(NumberFormatException e){
					e.printStackTrace();
					logger.error(e.getMessage(), e);
				}
				Long idInstitucionActual = getInstitucionActual().getId();
				paquete = liquidacionPaqueteService.getPaquete(paqueteDTO, ticket, idInstitucionActual);
				mensaje = properties.getProperty(paquete.getMensaje());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	public void cancelarInstruccionLiquidacion(ActionEvent event){
		try {
			mensaje="";
			FacesContext ctx = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
			HttpSession session = request.getSession(false);
			String ticket = (String) session.getAttribute(SeguridadConstants.TICKET_SESION);
			String referenciaPaquete = (String) event.getComponent().getAttributes().get("referenciaPaquete");
			Integer totalOperacionesPaquete = (Integer) event.getComponent().getAttributes().get("totalOperacionesPaquete");
			Integer numeroOperacionPaquete = (Integer) event.getComponent().getAttributes().get("numeroOperacionPaquete");
			Long totalTitulosPaquete = (Long) event.getComponent().getAttributes().get("totalTitulosPaquete");
			BigDecimal totalImportePaquete = (BigDecimal) event.getComponent().getAttributes().get("totalImportePaquete");
			Long idInstruccion = (Long) event.getComponent().getAttributes().get("idInstruccion");
			Boolean cancelada = (Boolean) event.getComponent().getAttributes().get("cancelada");
			MopPaqueteDTO paqueteDTO=new MopPaqueteDTO();
			paqueteDTO.setReferenciaPaquete(referenciaPaquete);
			paqueteDTO.setTotalOperacionesPaquete(totalOperacionesPaquete);
			paqueteDTO.setNumeroOperacionPaquete(numeroOperacionPaquete);
			paqueteDTO.setTotalImportePaquete(totalImportePaquete);
			paqueteDTO.setTotalTitulosPaquete(totalTitulosPaquete);
			MopInstruccionLiquidacionDTO instruccionLiquidacionDTO = new MopInstruccionLiquidacionDTO();
			instruccionLiquidacionDTO.setIdInstruccion(idInstruccion);
			instruccionLiquidacionDTO.setCancelada(cancelada);
			String ret = liquidacionPaqueteService.cancelInstruccionLiquidacionPaquete(instruccionLiquidacionDTO, paqueteDTO, ticket);
			mensaje = properties.getProperty(ret);
			if(ret.startsWith("S")){
				Long idInstitucionActual = getInstitucionActual().getId();
				paquete = liquidacionPaqueteService.getPaquete(paqueteDTO, ticket, idInstitucionActual);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	
	public DetalleLiquidacionPaqService getLiquidacionPaqueteService() {
		return liquidacionPaqueteService;
	}

	public void setLiquidacionPaqueteService(DetalleLiquidacionPaqService liquidacionPaqueteService) {
		this.liquidacionPaqueteService = liquidacionPaqueteService;
	}

	public MopPaqueteDTO getPaquete() {
		return paquete;
	}

	public void setPaquete(MopPaqueteDTO paquete) {
		this.paquete = paquete;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	
}

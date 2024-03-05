/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * ConfirmarOperacionBean.java
 * 31/03/2008
 */
package com.indeval.portaldali.presentation.controller.bitacoramatch;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Map;

import javax.faces.context.FacesContext;

import com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.presentation.controller.common.ControllerBase;

/**
 * Backing bean para realizar la confirmación de una operación de match.
 * 
 * @author Emigio Hernández
 * 
 */
public class ConfirmarOperacionBean extends ControllerBase {
	/**
	 * Servicio para la confirmación de la operación.
	 */
	EnvioOperacionesService envioOperacionService = null;
	/**
	 * Mensaje de resultado
	 */
	String mensajeResultado = "";
	/**
	 * método de inicialización de propiedades
	 * @return null
	 */
	public String getInit(){
		FacesContext ctx = FacesContext.getCurrentInstance();
		
		String idBitacoraString = ctx.getExternalContext().getRequestParameterMap().get("idBitacoraMatch");
		AgenteVO agenteFirmado = new AgenteVO();
		agenteFirmado.setId(getInstitucionActual().getClaveTipoInstitucion());
		agenteFirmado.setFolio(getInstitucionActual().getFolioInstitucion());
		BigInteger resultado = null;
		try{
			resultado = envioOperacionService.confirmaOperacionMatch(new BigInteger(idBitacoraString), 
					agenteFirmado,null,null,null, null);
			mensajeResultado = "Confirmacion realizada con exito: folio " + resultado.longValue();
		}catch (Exception e) {
			mensajeResultado = e.getMessage();
			e.printStackTrace();
		}
		
		
		
		return null;
	}


	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.presentation.controller.common.ControllerBase#ejecutarConsultaReporte()
	 */
	@Override
	protected Collection<? extends Object> ejecutarConsultaReporte() {
		// TODO Auto-generated method stub
		return null;
	}
/*
 * (non-Javadoc)
 * @see com.indeval.portaldali.presentation.controller.common.ControllerBase#getNombreReporte()
 */
	@Override
	protected String getNombreReporte() {
		// TODO Auto-generated method stub
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.presentation.controller.common.ControllerBase#llenarParametros()
	 */
	@Override
	protected Map<String, Object> llenarParametros() {
		// TODO Auto-generated method stub
		return null;
	}


	/**
	 * Obtiene el campo envioOperacionService
	 * @return  envioOperacionService
	 */
	public EnvioOperacionesService getEnvioOperacionService() {
		return envioOperacionService;
	}


	/**
	 * Asigna el campo envioOperacionService
	 * @param envioOperacionService el valor de envioOperacionService a asignar
	 */
	public void setEnvioOperacionService(
			EnvioOperacionesService envioOperacionService) {
		this.envioOperacionService = envioOperacionService;
	}


	/**
	 * Obtiene el campo mensajeResultado
	 * @return  mensajeResultado
	 */
	public String getMensajeResultado() {
		return mensajeResultado;
	}


	/**
	 * Asigna el campo mensajeResultado
	 * @param mensajeResultado el valor de mensajeResultado a asignar
	 */
	public void setMensajeResultado(String mensajeResultado) {
		this.mensajeResultado = mensajeResultado;
	}


	
}
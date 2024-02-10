package com.indeval.portalinternacional.presentation.controller.seguridad;

/**
 * @author laviles
 *
 */
import org.apache.commons.lang.StringUtils;

import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * Clase para que se pueda autenticar con firma digital
 * @author laviles
 */
public class InicioSesionFirmaDigital extends ControllerBase {
	/** Ticket firmado */
	private String ticketSinFirmar = "";
	/** Ticket sin firmar */
	private String ticketFirmado = "";
    
    /**
	 * Limpia los campos referentes a la firma electr&aacute;nica
	 */
	protected void limpiarCampos(){
		ticketFirmado = ""; 
		ticketSinFirmar = "";
	}
	
	/**
	 * Toma del request los valores de los campos referentes a la firma electr&aacute;nica
	 */
	protected void recuperarCamposFirma(){
		final String SALTO_RETORNO = "\r\n";
		final String SALTO = "\n";
		ticketFirmado = getParameterMap().get("ticketFirmado") != null ? getParameterMap().get("ticketFirmado").replace(SALTO_RETORNO,SALTO) : null;
		ticketSinFirmar = getParameterMap().get("ticketSinFirmar") != null ? getParameterMap().get("ticketSinFirmar").replace(SALTO_RETORNO,SALTO) : null;
	}
	
    /**
     * Indica si ya se firmó el ISO en pantalla.
     * 
     * @return <code>true</code> si se firmó el ISO en pantalla.
     */
    public boolean isIsoYaFirmado() {    	
    	return !StringUtils.isEmpty(ticketSinFirmar);
    }

	/**
	 * @return the ticketSinFirmar
	 */
	public String getTicketSinFirmar() {
		return ticketSinFirmar;
	}

	/**
	 * @param ticketSinFirmar the ticketSinFirmar to set
	 */
	public void setTicketSinFirmar(String ticketSinFirmar) {
		this.ticketSinFirmar = ticketSinFirmar;
	}

	/**
	 * @return the ticketFirmado
	 */
	public String getTicketFirmado() {
		return ticketFirmado;
	}

	/**
	 * @param ticketFirmado the ticketFirmado to set
	 */
	public void setTicketFirmado(String ticketFirmado) {
		this.ticketFirmado = ticketFirmado;
	}
	
}


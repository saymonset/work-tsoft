package com.indeval.portalinternacional.presentation.controller.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.util.DateUtilService;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.BitacoraFileTransferService;

@SuppressWarnings({"unchecked"})
public class DetalleFileTransferController extends ControllerBase {
	
    private Map<String, String> parametros = null;
    
    private Long idFileTransferDivisasInt;
	private boolean debeDejarBitacora;
	private boolean consultaEjecutada = false;
	
	private BitacoraFileTransferService bitacoraFileTransferService;
	private DateUtilService dateUtilService;
	
	/** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(DetalleFileTransferController.class);
	
	public DetalleFileTransferController () {
		
		
		
	}

	public String getInit() {
		
		parametros = new HashMap<String, String>(); 
	    Set<String> keys = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().keySet();
	        
	    for (String key : keys) {
	    	parametros.put(key, FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key));
	    }
	      
	    FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
	    ejecutarConsulta();
	    
	    return null;

	}
	
	public String ejecutarConsulta () {

		String idFileTransferDivisasIntTexto = parametros.get("idFileTransferDivisasInt");
		if (StringUtils.isNotBlank(idFileTransferDivisasIntTexto)) {

			idFileTransferDivisasInt = Long.valueOf(idFileTransferDivisasIntTexto);
			
			try {
				paginaVO = bitacoraFileTransferService.consultarDetalleFileTransfer(idFileTransferDivisasInt, paginaVO, true);
				debeDejarBitacora = false;
				consultaEjecutada = true;
			} catch (BusinessException e) {
				log.error(e.getMessage());
				agregarMensajeWarn(e.getMessage());
			} catch (Exception e) {
				log.error(e.getMessage());
				agregarMensaje(e);
			} catch (Throwable e) {
				log.error(e.getMessage());
				agregarMensaje(e);
			}
				
		}
		
		return null;
		
	}

	public Map<String, String> getParametros() {
		return parametros;
	}

	public void setParametros(Map<String, String> parametros) {
		this.parametros = parametros;
	}
	
	public Long getIdFileTransferDivisasInt() {
		return idFileTransferDivisasInt;
	}

	public void setIdFileTransferDivisasInt(Long idFileTransferDivisasInt) {
		this.idFileTransferDivisasInt = idFileTransferDivisasInt;
	}

	public boolean isDebeDejarBitacora() {
		return debeDejarBitacora;
	}

	public void setDebeDejarBitacora(boolean debeDejarBitacora) {
		this.debeDejarBitacora = debeDejarBitacora;
	}

	public boolean isConsultaEjecutada() {
		return consultaEjecutada;
	}

	public void setConsultaEjecutada(boolean consultaEjecutada) {
		this.consultaEjecutada = consultaEjecutada;
	}

	public BitacoraFileTransferService getBitacoraFileTransferService() {
		return bitacoraFileTransferService;
	}

	public void setBitacoraFileTransferService(BitacoraFileTransferService bitacoraFileTransferService) {
		this.bitacoraFileTransferService = bitacoraFileTransferService;
	}

	public DateUtilService getDateUtilService() {
		return dateUtilService;
	}

	public void setDateUtilService(DateUtilService dateUtilService) {
		this.dateUtilService = dateUtilService;
	}

}

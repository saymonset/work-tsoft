/**
 * 
 */
package com.indeval.portalinternacional.presentation.controller.eventosCorporativos;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ConsultaEventosCorporativosService;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.AdjuntosEventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.EventoCorporativoFullVistaDTO;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * @author kode-
 *
 */
public class ResumenEventoCorporativoController extends ControllerBase{

	@SuppressWarnings("unused")
	private Pattern pattern;
    private PaginaVO paginaReportes;
    private static final Logger log = LoggerFactory.getLogger(ResumenEventoCorporativoController.class);
	private EventoCorporativoFullVistaDTO evcoResumen;
	private String idEventoCorporativo;
	private ConsultaEventosCorporativosService consultaEventosCorporativosService;
	private String idArchivo;
	private boolean consultaIndeval;
	
	public ResumenEventoCorporativoController() {
		// TODO Auto-generated constructor stub
		log.info("CONTROLLER RESUMEN MVC");
	}
	
	/**
	 * Inicializa el bean
	 * @return String
	 */
	public String getInit()
	{
		log.info("Entra a Metodo Init");
		paginaReportes = new PaginaVO();
		paginaReportes.setOffset(0);
		paginaReportes.setRegistrosXPag(PaginaVO.TODOS);
		paginaReportes.setRegistros(null);
	    FacesContext facesContext = FacesContext.getCurrentInstance();
	    this.consultaIndeval = 	    Boolean.parseBoolean(facesContext.getExternalContext().getRequestParameterMap().get("isIndeval"));
	    this.idEventoCorporativo = facesContext.getExternalContext().getRequestParameterMap().get("idEventoCorporativo")==null?this.idEventoCorporativo:facesContext.getExternalContext().getRequestParameterMap().get("idEventoCorporativo");
	    this.evcoResumen=consultaEventosCorporativosService.getResumenEventoCorporativo(Long.parseLong(idEventoCorporativo));
	    
		log.info(" RESUMEN COMPLETO  CC");
		return "";
	}
	
	/**
	 * Genera los reportes de Consulta de Emisiones
	 */
	public String obtenerArchivos()
	{	
		//OutputStream out = null;
		AdjuntosEventoCorporativo archivoAdjunto =consultaEventosCorporativosService.getArchivoAdjuntoPorId(Long.parseLong(idArchivo));
	    if(archivoAdjunto != null){
			FacesContext context = FacesContext.getCurrentInstance();
	
		
			try {
		        HttpServletResponse response =(HttpServletResponse)context.getExternalContext().getResponse();
			    response.setContentType("application/octet-stream");
			    response.addHeader("Content-Disposition", "attachment; filename=\""+archivoAdjunto.getDescripcion()+"\"");      
	
			    response.getOutputStream().write(archivoAdjunto.getArchivo());
			    response.getOutputStream().flush();
			    response.getOutputStream().close();
			    FacesContext.getCurrentInstance().responseComplete();
			
			} catch (IOException e) {
			 log.debug("NO se pudo regresar el archivo requerido",e);
		    }
		
	    }else{
	    	addErrorMessage("No se encuentra el archivo");
	    }
		return null;
	}
	/**
	 * @return the evcoResumen
	 */
	public EventoCorporativoFullVistaDTO getEvcoResumen() {
		return evcoResumen;
	}
	/**
	 * @param evcoResumen the evcoResumen to set
	 */
	public void setEvcoResumen(EventoCorporativoFullVistaDTO evcoResumen) {
		this.evcoResumen = evcoResumen;
	}
	/**
	 * @return the idEventoCorporativo
	 */
	public String getIdEventoCorporativo() {
		return idEventoCorporativo;
	}
	/**
	 * @param idEventoCorporativo the idEventoCorporativo to set
	 */
	public void setIdEventoCorporativo(String idEventoCorporativo) {
		this.idEventoCorporativo = idEventoCorporativo;
	}
	/**
	 * @return the consultaEventosCorporativosService
	 */
	public ConsultaEventosCorporativosService getConsultaEventosCorporativosService() {
		return consultaEventosCorporativosService;
	}
	
	/**
	 * @param consultaEventosCorporativosService the consultaEventosCorporativosService to set
	 */
	public void setConsultaEventosCorporativosService(
			ConsultaEventosCorporativosService consultaEventosCorporativosService) {
		this.consultaEventosCorporativosService = consultaEventosCorporativosService;
	}

	/**
	 * @return the idArchivo
	 */
	public String getIdArchivo() {
		return idArchivo;
	}

	/**
	 * @param idArchivo the idArchivo to set
	 */
	public void setIdArchivo(String idArchivo) {
		this.idArchivo = idArchivo;
	}

	/**
	 * @return the consultaIndeval
	 */
	public boolean isConsultaIndeval() {
		return consultaIndeval;
	}

	/**
	 * @param consultaIndeval the consultaIndeval to set
	 */
	public void setConsultaIndeval(boolean consultaIndeval) {
		this.consultaIndeval = consultaIndeval;
	}
	
	
}

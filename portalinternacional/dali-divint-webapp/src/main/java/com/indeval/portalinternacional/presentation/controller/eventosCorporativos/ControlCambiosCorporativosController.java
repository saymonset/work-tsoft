/**
 * 
 */
package com.indeval.portalinternacional.presentation.controller.eventosCorporativos;


import java.util.List;
import java.util.regex.Pattern;

import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ConsultaEventosCorporativosService;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.BitacoraCambiosDto;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.EventoCorporativoFullVistaDTO;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * @author kode-
 *
 */
public class ControlCambiosCorporativosController extends ControllerBase{

	@SuppressWarnings("unused")
	private Pattern pattern;
	private static final Logger log = LoggerFactory.getLogger(ControlCambiosCorporativosController.class);
	
	private List<BitacoraCambiosDto> listaBitacora;
    private PaginaVO paginaReportes;
	private ConsultaEventosCorporativosService consultaEventosCorporativosService;
	EventoCorporativoFullVistaDTO bitacoraEvcoOriginal;
	private String idEventoCorporativo;
	private String idBitacoraCambio;
	private String idsCambioEstatus;
	private String idsCambiosEstatusFalse;

	
	public ControlCambiosCorporativosController()
	{
		// TODO Auto-generated constructor stub
		log.info("ingresamos a controller de ControlCambiosEvco");
	}
	
	/**
	 * Inicializa el bean
	 * @return String
	 */
	public String getInit()
	{
		paginaReportes = new PaginaVO();
		paginaReportes.setOffset(0);
		paginaReportes.setRegistrosXPag(PaginaVO.TODOS);
		paginaReportes.setRegistros(null);
		//this.cuerpoVarianteBitacora= bitacoraObjeto.getCuerpoEnVistaLista();
	    FacesContext facesContext = FacesContext.getCurrentInstance();

	    this.idEventoCorporativo = facesContext.getExternalContext().getRequestParameterMap().get("idEventoCorporativo")==null?this.idEventoCorporativo:facesContext.getExternalContext().getRequestParameterMap().get("idEventoCorporativo");
		this.listaBitacora=	consultaEventosCorporativosService.getBitacoraControlCambios(Long.parseLong(idEventoCorporativo));

		log.info(" Salio bien de la consulta de CC");
		return "";
	}
	/**
	 * Inicializa el objeto Evco a mostrar un evento corporativo original
	 * @return String
	 */
	public String getInitEvcoOriginal()
	{
		paginaReportes = new PaginaVO();
		paginaReportes.setOffset(0);
		paginaReportes.setRegistrosXPag(PaginaVO.TODOS);
		paginaReportes.setRegistros(null);

		FacesContext facesContext = FacesContext.getCurrentInstance();
	    this.idBitacoraCambio = facesContext.getExternalContext().getRequestParameterMap().get("idBitacoraCambio");
	    log.info("(idBitacoraCambio):  "+idBitacoraCambio);
	    this.bitacoraEvcoOriginal =	consultaEventosCorporativosService.getBitacoraPorRegistro(Long.parseLong(idBitacoraCambio));

		log.info(" Salio bien de la consulta de CC");
		return "";
	}
	
	/**
	 * Genera los reportes de Consulta de Emisiones
	 */
	public String cambiarEstatus()
	{	
		log.info(" Entro correctamente e hizo Refresh");
	    log.info(this.idsCambioEstatus);
	    
		 Long estado = new Long(1);
		 Long estadoFalse = new Long(0);
	    consultaEventosCorporativosService.updateCambiosDeEstadoBitacora(this.idsCambiosEstatusFalse,this.idsCambioEstatus,estado,estadoFalse);
	    
		return "success";
	}
	

	/**
	 * @return the listaBitacora
	 */
	public List<BitacoraCambiosDto> getListaBitacora() {
		return listaBitacora;
	}

	/**
	 * @param listaBitacora the listaBitacora to set
	 */
	public void setListaBitacora(List<BitacoraCambiosDto> listaBitacora) {
		this.listaBitacora = listaBitacora;
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
	 * @return the bitacoraEvcoOriginal
	 */
	public EventoCorporativoFullVistaDTO getBitacoraEvcoOriginal() {
		return bitacoraEvcoOriginal;
	}

	/**
	 * @param bitacoraEvcoOriginal the bitacoraEvcoOriginal to set
	 */
	public void setBitacoraEvcoOriginal(EventoCorporativoFullVistaDTO bitacoraEvcoOriginal) {
		this.bitacoraEvcoOriginal = bitacoraEvcoOriginal;
	}

	/**
	 * @return the idBitacoraCambio
	 */
	public String getIdBitacoraCambio() {
		return idBitacoraCambio;
	}

	/**
	 * @param idBitacoraCambio the idBitacoraCambio to set
	 */
	public void setIdBitacoraCambio(String idBitacoraCambio) {
		this.idBitacoraCambio = idBitacoraCambio;
	}

	/**
	 * @return the idsCambioEstatus
	 */
	public String getIdsCambioEstatus() {
		return idsCambioEstatus;
	}

	/**
	 * @param idsCambioEstatus the idsCambioEstatus to set
	 */
	public void setIdsCambioEstatus(String idsCambioEstatus) {
		this.idsCambioEstatus = idsCambioEstatus;
	}

	/**
	 * @return the idsCambiosEstatusFalse
	 */
	public String getIdsCambiosEstatusFalse() {
		return idsCambiosEstatusFalse;
	}

	/**
	 * @param idsCambiosEstatusFalse the idsCambiosEstatusFalse to set
	 */
	public void setIdsCambiosEstatusFalse(String idsCambiosEstatusFalse) {
		this.idsCambiosEstatusFalse = idsCambiosEstatusFalse;
	}

	

}

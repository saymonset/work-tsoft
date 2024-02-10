/**
 * 
 */
package com.indeval.portalinternacional.presentation.controller.eventosCorporativos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ConsultaEventosCorporativosService;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.AdjuntosEventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.AtributosEmbebidosEvcoDto;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.EventoCorporativoConsultaDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.EventoCorporativoFullVistaDTO;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;




/**
 * @author kode-
 *
 */
public class BitacoraEventosCorporativosController extends ControllerBase{
	
	private Pattern pattern; 

	private static final Logger log = LoggerFactory.getLogger(BitacoraEventosCorporativosController.class);
	private ConsultaEventosCorporativosService consultaEventosCorporativosService;
	private EventoCorporativoConsultaDTO eventoCorporativoConsulta;
	
	private String idEventoCorporativo;
	private String hayRegistro;
	private Date fechaCreacion;	
	private Date fechaRegistro;	
	private String tipoValor;
	private String emisora;
	private String serie;
	private String isin;
	private String tipoDerechoMO;
	private String tipoDerechoML;
	private String tipoMercado;
	private String estado;
	private Date fechaIndeval;	
	private Date fechaCliente;	
	private Date fechaPago;	
	private String custodio;	
	private String tipoEvento;	
	private boolean isEdicion;
	private String idArchivo;
	private boolean consultaIndeval;

	private List<AtributosEmbebidosEvcoDto> cuerpoVarianteBitacora= new ArrayList<AtributosEmbebidosEvcoDto>();
	/** Pagina para los reportes*/
    private PaginaVO paginaReportes;
	
	public BitacoraEventosCorporativosController() 
	{
		log.debug(" MVC de la bitacora");
	}
	
	/**
	 * Inicializa el bean
	 * @return
	 */
	public String getInit()
	{
		//this.idEventoCorporativo=null;

	    FacesContext facesContext = FacesContext.getCurrentInstance();
	    this.idEventoCorporativo = facesContext.getExternalContext().getRequestParameterMap().get("idEventoCorporativo");
	    this.consultaIndeval = 	    Boolean.parseBoolean(facesContext.getExternalContext().getRequestParameterMap().get("isIndeval"));
	
		eventoCorporativoConsulta= new EventoCorporativoConsultaDTO();
		eventoCorporativoConsulta.setIdEventoCorporativo(this.idEventoCorporativo); 
		paginaReportes = new PaginaVO();
		paginaReportes.setOffset(0);
		paginaReportes.setRegistrosXPag(PaginaVO.TODOS);
		paginaReportes.setRegistros(null);

		paginaReportes =consultaEventosCorporativosService.getBitacoraPorEventoCorportativo(eventoCorporativoConsulta, paginaReportes);
		@SuppressWarnings("unchecked")
		List<EventoCorporativoFullVistaDTO> bitacoraLista=paginaReportes.getRegistros();
		
		EventoCorporativoFullVistaDTO  bitacoraObjeto =(EventoCorporativoFullVistaDTO )bitacoraLista.get(0);

		this.idEventoCorporativo= bitacoraObjeto.getIdEventoCorporativo();
		this.fechaCreacion=bitacoraObjeto.getFechaCreacion();
		this.fechaRegistro=bitacoraObjeto.getFechaRegistro();
		this.tipoValor=bitacoraObjeto.getTipoValor();
		this.emisora=bitacoraObjeto.getEmisora();
		this.serie=bitacoraObjeto.getSerie();
		this.isin=bitacoraObjeto.getIsin();
		this.tipoDerechoMO= bitacoraObjeto.getTipoDerechoMO();
		this.tipoDerechoML= bitacoraObjeto.getTipoDerechoML();
		this.tipoMercado= bitacoraObjeto.getTipoMercado();
		this.estado= bitacoraObjeto.getEstado();
		this.fechaCliente=bitacoraObjeto.getFechaCliente();
		this.fechaIndeval=bitacoraObjeto.getFechaIndeval();
		this.fechaPago=bitacoraObjeto.getFechaPago();
		this.custodio=bitacoraObjeto.getCustodio();
		this.tipoEvento=bitacoraObjeto.getTipoEvento();
		
		this.cuerpoVarianteBitacora= bitacoraObjeto.getCuerpoEnVistaLista();

		return "";
	}

	
	
	/**
	 * Genera los reportes de Consulta de Emisiones
	 */
	
	public String getObtenerArchivos(){
		return obtenerArchivos();
	}
	public String obtenerArchivos()
	{	
		//OutputStream out = null;
		AdjuntosEventoCorporativo archivoAdjunto =consultaEventosCorporativosService.getArchivoAdjuntoPorId(Long.parseLong(idArchivo));
	    if(archivoAdjunto != null){
			FacesContext context = FacesContext.getCurrentInstance();
	
		
			try {
				//out = response.getOutputStream();
	
		        HttpServletResponse response =(HttpServletResponse)context.getExternalContext().getResponse();
		        log.info("nombre_Archivo:"+archivoAdjunto.getDescripcion());
			    response.setContentType("application/octet-stream");
			    response.addHeader("Content-Disposition", "attachment; filename=\""+archivoAdjunto.getDescripcion()+"\"");      
		     
		                response.getOutputStream().write(archivoAdjunto.getArchivo());
		                response.getOutputStream().flush();
		                response.getOutputStream().close();
	
		 
		        FacesContext.getCurrentInstance().responseComplete();
		    } catch (IOException e) {
		    	if(archivoAdjunto != null){
		    		log.debug("Error al obtener archivo para servirlo "+archivoAdjunto.getIdAdjuntos());
		    	}
		    }	
	    }else{
    		log.debug("Error al obtener archivo para servirlo Archivo adjunto nulo");
    	}
		return null;
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
	 * @return the hayRegistro
	 */
	public String getHayRegistro() {
		return hayRegistro;
	}

	/**
	 * @param hayRegistro the hayRegistro to set
	 */
	public void setHayRegistro(String hayRegistro) {
		this.hayRegistro = hayRegistro;
	}

	/**
	 * @param consultaEventosCorporativosService the consultaEventosCorporativosService to set
	 */
	public void setConsultaEventosCorporativosService(
			ConsultaEventosCorporativosService consultaEventosCorporativosService) {
		this.consultaEventosCorporativosService = consultaEventosCorporativosService;
	}

	/**
	 * @return the eventoCorporativoConsulta
	 */
	public EventoCorporativoConsultaDTO getEventoCorporativoConsulta() {
		return eventoCorporativoConsulta;
	}

	/**
	 * @param eventoCorporativoConsulta the eventoCorporativoConsulta to set
	 */
	public void setEventoCorporativoConsulta(
			EventoCorporativoConsultaDTO eventoCorporativoConsulta) {
		this.eventoCorporativoConsulta = eventoCorporativoConsulta;
	}

	/**
	 * @return the fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * @param fechaCreacion the fechaCreacion to set
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * @return the fechaRegistro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * @param fechaRegistro the fechaRegistro to set
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	/**
	 * @return the tipoValor
	 */
	public String getTipoValor() {
		return tipoValor;
	}

	/**
	 * @param tipoValor the tipoValor to set
	 */
	public void setTipoValor(String tipoValor) {
		this.tipoValor = tipoValor;
	}

	/**
	 * @return the emisora
	 */
	public String getEmisora() {
		return emisora;
	}

	/**
	 * @param emisora the emisora to set
	 */
	public void setEmisora(String emisora) {
		this.emisora = emisora;
	}

	/**
	 * @return the serie
	 */
	public String getSerie() {
		return serie;
	}

	/**
	 * @param serie the serie to set
	 */
	public void setSerie(String serie) {
		this.serie = serie;
	}

	/**
	 * @return the isin
	 */
	public String getIsin() {
		return isin;
	}

	/**
	 * @param isin the isin to set
	 */
	public void setIsin(String isin) {
		this.isin = isin;
	}

	/**
	 * @return the tipoDerechoMO
	 */
	public String getTipoDerechoMO() {
		return tipoDerechoMO;
	}

	/**
	 * @param tipoDerechoMO the tipoDerechoMO to set
	 */
	public void setTipoDerechoMO(String tipoDerechoMO) {
		this.tipoDerechoMO = tipoDerechoMO;
	}

	/**
	 * @return the tipoDerechoML
	 */
	public String getTipoDerechoML() {
		return tipoDerechoML;
	}

	/**
	 * @param tipoDerechoML the tipoDerechoML to set
	 */
	public void setTipoDerechoML(String tipoDerechoML) {
		this.tipoDerechoML = tipoDerechoML;
	}

	/**
	 * @return the tipoMercado
	 */
	public String getTipoMercado() {
		return tipoMercado;
	}

	/**
	 * @param tipoMercado the tipoMercado to set
	 */
	public void setTipoMercado(String tipoMercado) {
		this.tipoMercado = tipoMercado;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return the fechaIndeval
	 */
	public Date getFechaIndeval() {
		return fechaIndeval;
	}

	/**
	 * @param fechaIndeval the fechaIndeval to set
	 */
	public void setFechaIndeval(Date fechaIndeval) {
		this.fechaIndeval = fechaIndeval;
	}

	/**
	 * @return the fechaCliente
	 */
	public Date getFechaCliente() {
		return fechaCliente;
	}

	/**
	 * @param fechaCliente the fechaCliente to set
	 */
	public void setFechaCliente(Date fechaCliente) {
		this.fechaCliente = fechaCliente;
	}

	/**
	 * @return the fechaPago
	 */
	public Date getFechaPago() {
		return fechaPago;
	}

	/**
	 * @param fechaPago the fechaPago to set
	 */
	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	/**
	 * @return the custodio
	 */
	public String getCustodio() {
		return custodio;
	}

	/**
	 * @param custodio the custodio to set
	 */
	public void setCustodio(String custodio) {
		this.custodio = custodio;
	}

	/**
	 * @return the tipoEvento
	 */
	public String getTipoEvento() {
		return tipoEvento;
	}

	/**
	 * @param tipoEvento the tipoEvento to set
	 */
	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}



	/**
	 * @return the isEdicion
	 */
	public boolean isEdicion() {
		return isEdicion;
	}

	/**
	 * @param isEdicion the isEdicion to set
	 */
	public void setEdicion(boolean isEdicion) {
		this.isEdicion = isEdicion;
	}

	/**
	 * @return the paginaReportes
	 */
	public PaginaVO getPaginaReportes() {
		return paginaReportes;
	}

	/**
	 * @param paginaReportes the paginaReportes to set
	 */
	public void setPaginaReportes(PaginaVO paginaReportes) {
		this.paginaReportes = paginaReportes;
	}

	/**
	 * @return the consultaEventosCorporativosService
	 */
	public ConsultaEventosCorporativosService getConsultaEventosCorporativosService() {
		return consultaEventosCorporativosService;
	}

	/**
	 * @return the cuerpoVarianteBitacora
	 */
	public List<AtributosEmbebidosEvcoDto> getCuerpoVarianteBitacora() {
		return cuerpoVarianteBitacora;
	}

	/**
	 * @param cuerpoVarianteBitacora the cuerpoVarianteBitacora to set
	 */
	public void setCuerpoVarianteBitacora(
			List<AtributosEmbebidosEvcoDto> cuerpoVarianteBitacora) {
		this.cuerpoVarianteBitacora = cuerpoVarianteBitacora;
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

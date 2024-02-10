package com.indeval.portalinternacional.presentation.controller.calendarioHistoricoCapitales;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.EnvioLegislacionSicDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaCapitalesParamsTO;
import com.indeval.portalinternacional.presentation.controller.common.CalendarioCapitalesBase;


/**
 * Controller para la pantalla de Consulta Hist&oacute;rica de capitales (calendarioHistoricoCapitales.xhtml)
 * @author javier.perez
 *
 */
public class CalendarioCapitalesHistoricoController extends CalendarioCapitalesBase {
	  
	private static final Logger LOG = LoggerFactory.getLogger(CalendarioCapitalesHistoricoController.class);
    

    private boolean consultaEjecutada  = false;
    private boolean banderaBitacoraConsulta = false;
    
    /** control paginacion */
    private int totalPaginas = 1;   
    private int totalRegistros = 0;
    private int totalRegistrosReporte = 0;
    
    /** Pagina para los reportes */
    private PaginaVO paginaReportes;
    
    /** Patron numerico */
    private Pattern pattern = Pattern.compile("[0-9]*");
    
    /** Parametros enviados por el Request */
	private Map<String, String> params = new HashMap<String, String>();
    
	private String folio;
	
	private String idsDestinatarios;
	
	private Map<String, String> tipoValorPermitidos;
	
	private List<Long> idsCaevs;
	
	String origen;
	
    /**Constructor por default*/
    public CalendarioCapitalesHistoricoController() {

    }

    /**
     * Inicializa el bean
     * 
     * @return
     */
    public String getInit() {
    	try{
    		inicializaPagina();
            cargaMapaDescFechasAd();
            cargaMapaImagenesEstadosEnvio();
            limpiarFiltro();
    	}catch(BusinessException e){
    		LOG.error("Ocurrio un error:",e);
    		this.addErrorMessage("Error al Iniciar la pantalla.");
    	}
                
        return null;
    }
    
    /**
     *Inicializa la ventana emergente detalle historico que es invocada desde la Calendario Capitales
     * 
     */
    public String getInitPopUp() {
    	try{
    	 	inicializaPagina();
        	cargaMapaDescFechasAd();
            cargaMapaImagenesEstadosEnvio();
            limpiarFiltro();
            Set<String> keys = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().keySet();        
            for (String key : keys) {
                params.put(key, FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key));
            }            
            FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            ejecutarConsulta();
    	}catch(BusinessException e){
    		LOG.error("Ocurrio un error:",e);
    		this.addErrorMessage("Error al Iniciar la pantalla.");
    	}
		return null;
	}
 
    /**
     * Limpia/inicializa los filtros
     */
    private void limpiarFiltro(){
    	limpiaIdentificadores();
 	    getCaevsSelected().clear();
    	this.calendario = new ConsultaCapitalesParamsTO();
    }

	 private void inicializaPagina(){
		 if (this.paginaVO == null) {
	            this.paginaVO = new PaginaVO();
	        }     
		    getTiposPagoCAEV();
	        this.paginaVO.setRegistrosXPag(50);
	        this.paginaVO.setOffset(0);
	        this.banderaBitacoraConsulta = false;
	        if(tipoValorPermitidos == null){
	        	tipoValorPermitidos = capitalesDistribucionService.getTipoValorLegislacionSicMap();
	        }	     
	 }
    /**
     * Buscar las emisiones
     * 
     * @param evt
     */
    public void buscarDerechos(ActionEvent evt) {
    	LOG.info("Inica busqueda de informacion...");
    	this.getPaginaVO().setOffset(0);
    	this.getPaginaVO().setRegistrosXPag(50);
    	this.getPaginaVO().setRegistros(null);        
    	this.ejecutarConsulta();

    }
    
    /**
     * Busca los registros por filtro
     * 
     * @param evt
     */
    public void buscarDerechosConsulta(final ActionEvent evt) {
    	this.paginaVO.setRegistrosXPag(50);
    	this.paginaVO.setOffset(0);
    	this.getPaginaVO().setRegistros(null);
    	boolean isError = false;
    	this.setConsultaEjecutada(false);
    	procesaFechasExterno();
    	try{
    		validaFechasExterno();	
    	}
    	catch (BusinessException be){
    		LOG.info("Error Fechas");
    		isError = true;
    		this.setConsultaEjecutada(true);
    	}
    	if(!isError){
    	this.ejecutarConsulta();
    	}
    	
    }
    
    /**
     * Consulta los historicos al cerrar las instrucciones
     * 
     * @param evt
     */
    public void buscarDerechosInstruccion(ActionEvent evt) {
    	LOG.info("Inica busqueda de informacion...");    	
    	this.getPaginaVO().setRegistros(null);        
    	this.ejecutarConsulta();
    }


    /**
     * Ejecuta la consulta y calcula el numero de pagina para la paginacion. Este metodo es un
     * overide de la clasmerge y e padre
     */
    
    public String ejecutarConsulta() {
    	this.limpiaIdentificadores();
    	try{
    		if(params.get("folio")!= null ){
        		calendario.setFolioIndeval(Long.valueOf(params.get("folio")));    		
    		}
        	isTipoValorValido();      		
    		this.setParams();
    		idsCaevs = cargaListaIdsCaevs();
    	    validaNarrativaFechaRecepcion();
    	    if(CollectionUtils.isNotEmpty(idsCaevs)){
    	    	calendario.setIdsCaevs(idsCaevs);
    	    }
            paginaVO = capitalesDistribucionService.consultaHistoricoCapitales(calendario, paginaVO, origen);
            LOG.info(("Numero de Registros" + paginaVO.getRegistros().size()));
            //calendario.setFechaRecepcionInicio(this.getFechaFormato(calendario.getFechaRecepcionInicio()));
            this.totalPaginas = this.paginaVO.getTotalRegistros() / this.paginaVO.getRegistrosXPag();
            if (this.paginaVO.getTotalRegistros() % this.paginaVO.getRegistrosXPag() > 0) {
                this.totalPaginas++;
            }
            this.totalPaginas = this.totalPaginas <= 0 ? 1 : this.totalPaginas;
            totalRegistros = paginaVO.getTotalRegistros();           
    	}catch(BusinessException eb){
    		LOG.error("Error al consultar:",eb);
    		addErrorMessage(eb.getMessage());
    	}catch(Exception e){
    		LOG.error("Error al consultar:",e);
    		addErrorMessage("Ocurri\u00F3 un error al realizar la consulta.");
    	}finally{
    		this.consultaEjecutada = true;
    	}
    	return null;
        
    }
    
    /**
     * Se valida el TV ingresado en los filtros
     * @return
     */
    private void isTipoValorValido() throws BusinessException{    	
    	if(StringUtils.isNotBlank(this.calendario.getTipoValor())){
    		if(!tipoValorPermitidos.containsKey(this.calendario.getTipoValor())){
    			throw new BusinessException("Tipo Valor Inv\u00E1lido. Valores permitidos: "+StringUtils.join(this.tipoValorPermitidos.values().toArray(), ','));    			
    		}
    	}else if(this.calendario.getTipoValorPermitidos() == null){
    		this.calendario.setTipoValorPermitidos(new ArrayList<String>(tipoValorPermitidos.values()));
    	}    	
    }
        
    
    /**
     * Limpia el valor asignado por los combos
     */
    private void limpiaIdentificadores(){
    	this.calendario.setEstadoInt(null);
    	this.calendario.setBovedaInt(null);
    	this.calendario.setCustodioInt(null);
    	this.calendario.setTipoPagoCAEVInt(null);
    	this.calendario.setTipoPagoCAMVInt(null);
    	this.calendario.setFolioMensaje(null);
    	this.calendario.setFolioIndeval(null); 
    	this.calendario.setConNarrativa(false);
    	this.calendario.setIdsCaevs(null);
    	
    }

    private void setParams() throws BusinessException{    
    	if (StringUtils.isNotBlank(this.calendario.getEstado()) && this.pattern.matcher(this.calendario.getEstado()).matches()) {
           this.calendario.setEstadoInt(Long.valueOf(this.calendario.getEstado()));
        }
    	if (StringUtils.isNotBlank(this.calendario.getBoveda()) && this.pattern.matcher(this.calendario.getBoveda()).matches()) {
             this.calendario.setBovedaInt(Long.valueOf(this.calendario.getBoveda()));
        }    	
        if (StringUtils.isNotBlank(this.calendario.getCustodio()) && this.pattern.matcher(this.calendario.getCustodio()).matches()) {
            this.calendario.setCustodioInt(Integer.valueOf(this.calendario.getCustodio()));
        }     
        if (StringUtils.isNotBlank(this.calendario.getTipoPagoCAEV()) && this.pattern.matcher(this.calendario.getTipoPagoCAEV()).matches()) {
            this.calendario.setTipoPagoCAEVInt(Long.valueOf(this.calendario.getTipoPagoCAEV()));
        }
        if (StringUtils.isNotBlank(this.calendario.getTipoPagoCAMV()) && this.pattern.matcher(this.calendario.getTipoPagoCAMV()).matches()) {
            this.calendario.setTipoPagoCAMVInt(Long.valueOf(this.calendario.getTipoPagoCAMV()));
        }
        validaFolios();
    }
    
    private void validaFolios() throws BusinessException{
    	if(StringUtils.isNotBlank(this.calendario.getFolioIndevalCadena())){
    		if(this.pattern.matcher(this.calendario.getFolioIndevalCadena()).matches()){
    			this.calendario.setFolioIndeval(Long.valueOf(this.calendario.getFolioIndevalCadena()));
    		}else{
    			this.calendario.setFolioIndevalCadena(null);
    			throw new BusinessException("Folio Indeval Inv\u00E1lido");
    		}
    	}
    	if(StringUtils.isNotBlank(this.calendario.getFolioMensajeCadena())){
    		if(this.pattern.matcher(this.calendario.getFolioMensajeCadena()).matches()){
    			this.calendario.setFolioMensaje(Long.valueOf(this.calendario.getFolioMensajeCadena()));
    		}else{
    			this.calendario.setFolioMensajeCadena(null);
    			throw new BusinessException("Folio Mensaje Inv\u00E1lido");
    		}
    	}	
    }
    
    /**
     * Valida y/o asigna el rango de la fecha recepci&oacute;n 
     * @throws BusinessException
     */
    private void validaNarrativaFechaRecepcion()  throws BusinessException{
    	if(StringUtils.isNotBlank(this.calendario.getNarrativa())){
    		validaFechas();
    		procesaFechas();
    		this.calendario.setConNarrativa(true);
    	}
    }
    
    /**
     * valida el rango de Fecha Recepci&oacute;n
     * @throws BusinessException
     */
	private void validaFechas() throws BusinessException {
		if(calendario.getFechaRecepcionInicio() != null && calendario.getFechaRecepcionFin() != null){
			if(calcularDiferenciaFechasEnDias(calendario.getFechaRecepcionInicio(), calendario.getFechaRecepcionFin()) > 90){
				addErrorMessage("Error: Al ingresar el filtro narrativa la fecha Recepci\u00F3n no debe exceder el rango de 90 d\u00EDas.");
				throw new BusinessException("Error: Fecha"); 		
			}
		}
	}
	
	public void validaFechasExterno() throws BusinessException {
		if(calendario.getFechaEnvioConsInicio() != null && calendario.getFechaEnvioConsFin() != null){
	if(calendario.getFechaEnvioConsInicio().compareTo(calendario.getFechaEnvioConsFin()) > 0){
		addErrorMessage("Error: La fecha inicial no debe ser mayor a la fecha final.");
		throw new BusinessException("Error: Fecha"); 
	
	}
	else if(calcularDiferenciaFechasEnDias(calendario.getFechaEnvioConsInicio(), calendario.getFechaEnvioConsFin()) > 180){
		addErrorMessage("Error: La diferencia entre las fechas de inicio y fin no debe ser mayor a 180 d\u00EDas.");
		throw new BusinessException("Error: Fecha"); 
		
	}
	
		}
	}
    
	/**
	 * Procesa rango FechaRecepcion
	 * Caso 1: Si no se capturo ninguna fecha recepci&oacute;n, se establecen las fechas.	
	 *  Fecha final = fecha actual
	 *  Fecha inicial = fecha actual - 90 d&iacute;as
	 * Caso 2: Si se captura la fecha inicial y no se captura la Fecha final
	 *  La fecha final ser&aacute; igual a la fecha inicial + 90 d&iacute;as
	 * Caso 3: Si no se captura la fecha inicial y se captura la fecha final	
	 *   La fecha inicial ser&aacute; igual a la fecha inicial - 90 d&iacute;as
	 */
	private void procesaFechas() {		
		if (calendario.getFechaRecepcionInicio() == null && calendario.getFechaRecepcionFin() == null) {
			calendario.setFechaRecepcionFin(new Date());
			calendario.setFechaRecepcionInicio(modificaFecha(new Date(), -90));
		}		
		else if (calendario.getFechaRecepcionInicio() != null && calendario.getFechaRecepcionFin() == null) {
			calendario.setFechaRecepcionFin(modificaFecha(calendario.getFechaRecepcionInicio(), 90));
		}
		else if (calendario.getFechaRecepcionInicio() == null && calendario.getFechaRecepcionFin() != null) {
			calendario.setFechaRecepcionInicio(modificaFecha(calendario.getFechaRecepcionFin(), -90));
		}
	}
	
	/**
	 * Procesa fechas de envio inicial y final para evitar colapsar la aplicacion en la peticion del reporte excel con las narrativas
	 * 
	 */
	public void procesaFechasExterno() {
		// Caso 1: Si no se capturo ninguna fecha, se establecen la fechas de
		// envio
		// Fecha final = fecha actual
		// Fecha inicial = fecha actual - 180 dias
		if (calendario.getFechaEnvioConsInicio() == null && calendario.getFechaEnvioConsFin() == null) {
			calendario.setFechaEnvioConsFin(new Date());
			calendario.setFechaEnvioConsInicio(modificaFecha(new Date(), -180));
		}
		// Caso 2: Si se captura la fecha inicial (envio) y no se captura la
		// fecha final (envio)
		// la fecha final ser� igual a la fecha inicial + 180 d�as
		else if (calendario.getFechaEnvioConsInicio() != null && calendario.getFechaEnvioConsFin() == null) {
			calendario.setFechaEnvioConsFin(modificaFecha(calendario.getFechaEnvioConsInicio(), 180));
		}
		// Caso 3: Si no se captura la fecha inicial (envio) y se captura la
		// fecha final (envio)
		// la fecha inicial ser� igual a la fecha inicial - 180 d�as
		else if (calendario.getFechaEnvioConsInicio() == null && calendario.getFechaEnvioConsFin() != null) {
			calendario.setFechaEnvioConsInicio(modificaFecha(calendario.getFechaEnvioConsFin(), -180));
		}
	}
	


    /**
     * Limpia todos los campos
     * 
     * @param evt
     */
    public void limpiar(ActionEvent evt) {
        this.banderaBitacoraConsulta = false;        
        limpiarFiltro();     
        if (this.paginaVO != null && paginaVO.getRegistros() != null) {
            this.paginaVO.getRegistros().clear();
        }
        this.paginaVO.setRegistrosXPag(50);
        this.paginaVO.setOffset(0);

        this.consultaEjecutada = false;
    }
    

    /**
     * Genera los reportes de Consulta de Emisiones
     * 
     * @param evt
     */
    public void generarReportes(ActionEvent evt) {
    	try{
    		reiniciarEstadoPeticion();
    		validaNarrativaFechaRecepcion();
        	paginaReportes = new PaginaVO();
        	paginaReportes.setOffset(0);
        	paginaReportes.setRegistrosXPag(PaginaVO.TODOS);				
    		paginaReportes = capitalesDistribucionService.consultaHistoricoCapitales(calendario, paginaReportes, origen);
    		this.totalRegistrosReporte = paginaVO.getTotalRegistros();
    	}catch(Exception e){
    		LOG.error("Error al generar reporte:",e);
    		addErrorMessage("Ocurrio un error al generar el reporte.");
    	}
    	
    }
    
    /**
     * Genera los reportes de Consulta de Calendario Capitales Consulta (Procesa Fechas)
     * 
     * @param evt
     */
	private void generarReportesConsultas(ActionEvent evt) {
		try {
			this.reiniciarEstadoPeticion();
			paginaReportes = new PaginaVO();
			paginaReportes.setOffset(0);
			paginaReportes.setRegistrosXPag(PaginaVO.TODOS);
			procesaFechas();
			paginaReportes = capitalesDistribucionService.consultaHistoricoCapitales(calendario, paginaReportes,
					origen);
			this.totalRegistrosReporte = paginaReportes.getTotalRegistros();
		} catch (Exception e) {
			LOG.error("Error al generar reporte:", e);
			addErrorMessage("Ocurri\u00F3 un error al generar el reporte.");
		}
	}
    /**
     * Genera el reporte PDF para calendario Capitales Consulta
     * @param evt
     */
	public void generarReportesPDF(ActionEvent evt) {
		try {
			generarReportesConsultas(evt);
		} catch (Exception e) {
			LOG.error("Error al generar reporte:", e);
			addErrorMessage("Ocurri\u00F3 un error al generar el reporte.");
		}
	}

    /**
     * Genera el reporte PDF para calendario Capitales Consulta
     * @param evt
     */
    public void generarReportesXLS(ActionEvent evt){
    	try {
    	calendario.setXLS(true);    	
    	generarReportesConsultas(evt);
    	calendario.setXLS(false);
    	} catch (Exception e) {
			LOG.error("Error al generar reporte:", e);
			addErrorMessage("Ocurri\u00F3 un error al generar el reporte.");
		}
    }
	/**
	 * Toma el evento de enviar mensajes
	 * @param evt
	 */
	public void ejecutarEnvioMensaje(ActionEvent evt){
		try{
			String destinatarios = getIdsDestinatarios();		
			List<EnvioLegislacionSicDTO> cambios = formateaEnvioDestinatarios(destinatarios);			
			if(!cambios.isEmpty()){									
				capitalesDistribucionService.procesaEnvioLegMoi(cambios);
				agregarInfoMensaje("El proceso se realiz\u00f3 de manera exitosa");	
				ejecutarConsulta();           		  															
			}							
	  	}catch(Exception e){
    		LOG.error("Error al autorizar envio:",e);
    		addErrorMessage("Ocurri\u00f3 un error en el proceso, intente m\u00E1s tarde o comun\u00CDquese con el operador");
    	}finally{
    		setIdsDestinatarios(null);
    		this.consultaEjecutada = true;
    	}

	}
	
	/**
	 * Regresa un mapa con la informaci&oacute;n requerida para guardar el env&iacute;o
	 * @param destinatarios
	 * @return
	 */
	private List<EnvioLegislacionSicDTO> formateaEnvioDestinatarios(String destinatarios){
		List<EnvioLegislacionSicDTO> listaEnvio = Collections.emptyList();
		if(StringUtils.isNotEmpty(destinatarios)){			
			String[] buff = destinatarios.split("\\|");	
			listaEnvio = new ArrayList<EnvioLegislacionSicDTO>();
			String usuario = getNombreUsuarioSesion();
			for (String linea : buff){
				String[] datos = linea.split(":");								
				if(datos.length == 2){
					String valorIds = datos[0];
					int posicionCal = valorIds.indexOf("cal_");					
					String idHistorico= valorIds.substring(4,posicionCal);
					String idCalendario = valorIds.substring(posicionCal+4);
					String destinatario=datos[1];
					if(StringUtils.isNotEmpty(destinatario) && !destinatario.equals("-1")){
						EnvioLegislacionSicDTO envioLegislacionSicDTO = new EnvioLegislacionSicDTO();
						envioLegislacionSicDTO.setIdCalendario(Long.valueOf(idCalendario));
						envioLegislacionSicDTO.setIdHistorico(Long.valueOf(idHistorico));
						envioLegislacionSicDTO.setDestinatario(destinatario);
						envioLegislacionSicDTO.setUsuario(usuario);						
						listaEnvio.add(envioLegislacionSicDTO);
					}
				}
			}			
		}
		return listaEnvio; 
	}

  /////// SECCION GETERS Y SETERS ////////////
    /**
     * @return the consultaEjecutada
     */
    public boolean isConsultaEjecutada() {
        return this.consultaEjecutada;
    }

    /**
     * @param consultaEjecutada the consultaEjecutada to set
     */
    public void setConsultaEjecutada(boolean consultaEjecutada) {
        this.consultaEjecutada = consultaEjecutada;
    }

    /**
     * @return the totalPaginas
     */
    @Override
    public int getTotalPaginas() {
        return this.totalPaginas;
    }

    /**
     * @param totalPaginas the totalPaginas to set
     */
    @Override
    public void setTotalPaginas(int totalPaginas) {
        this.totalPaginas = totalPaginas;
    }

    /**
     * @return the totalRegistros
     */
    public int getTotalRegistros() {
        return this.totalRegistros;
    }

    /**
     * @param totalRegistros the totalRegistros to set
     */
    public void setTotalRegistros(int totalRegistros) {
        this.totalRegistros = totalRegistros;
    }

    public boolean isBanderaBitacoraConsulta() {
        return this.banderaBitacoraConsulta;
    }

    public void setBanderaBitacoraConsulta(boolean banderaBitacoraConsulta) {
        this.banderaBitacoraConsulta = banderaBitacoraConsulta;
    }

    /**
     * @return the calendario
     */
    public ConsultaCapitalesParamsTO getCalendario() {
        return this.calendario;
    }

    /**
     * @param calendario the calendario to set
     */
    public void setCalendario(ConsultaCapitalesParamsTO calendario) {
        this.calendario = calendario;
    }   

    /**
     * @return the paginaReportes
     */
    public PaginaVO getPaginaReportes() {
        return this.paginaReportes;
    }

    /**
     * @param paginaReportes the paginaReportes to set
     */
    public void setPaginaReportes(PaginaVO paginaReportes) {
        this.paginaReportes = paginaReportes;
    }

	/**
	 * @return the params
	 */
	public Map<String, String> getParams() {
		return params;
	}

	/**
	 * @param params the params to set
	 */
	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	/**
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}

	/**
	 * @param folio the folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}

	/**
	 * @return the idsDestinatarios
	 */
	public String getIdsDestinatarios() {
		return idsDestinatarios;
	}

	/**
	 * @param idsDestinatarios the idsDestinatarios to set
	 */
	public void setIdsDestinatarios(String idsDestinatarios) {
		this.idsDestinatarios = idsDestinatarios;
	}

	/**
	 * @return the tipoValorPermitidos
	 */
	public Map<String, String> getTipoValorPermitidos() {
		return tipoValorPermitidos;
	}

	/**
	 * @param tipoValorPermitidos the tipoValorPermitidos to set
	 */
	public void setTipoValorPermitidos(Map<String, String> tipoValorPermitidos) {
		this.tipoValorPermitidos = tipoValorPermitidos;
	}

	/**
	 * @return the totalRegistrosReporte
	 */
	public int getTotalRegistrosReporte() {
		return totalRegistrosReporte;
	}

	/**
	 * @param totalRegistrosReporte the totalRegistrosReporte to set
	 */
	public void setTotalRegistrosReporte(int totalRegistrosReporte) {
		this.totalRegistrosReporte = totalRegistrosReporte;
	}

	/**
	 * @return the origen
	 */
	public String getOrigen() {
		return origen;
	}

	/**
	 * @param origen the origen to set
	 */
	public void setOrigen(String origen) {
		this.origen = origen;
	}
    
}

package com.indeval.portalinternacional.presentation.controller.calendarioCapitales;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.faces.event.ActionEvent;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.EnvioLegislacionSicDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaCapitalesParamsTO;
import com.indeval.portalinternacional.presentation.controller.common.CalendarioCapitalesBase;


/** * Controlador para la pantalla de Consulta Calendario capitales (calendarioCapitales.xhtml)
 * 
 * @author Luis R Munoz y Omar Gtz
 * 
 */
public class CalendarioCapitalesController extends CalendarioCapitalesBase {

	private static final Logger log = LoggerFactory.getLogger(CalendarioCapitalesController.class);

 

    private boolean consultaEjecutada;

    private int totalPaginas = 1;

    private PaginaVO resultados = null;

    private boolean banderaBitacoraConsulta = false;

    private int totalRegistros = 0;
    
    private int totalRegistrosReportes = 0;
    
    /** Patron numerico */
    private Pattern pattern = Pattern.compile("[0-9]*");
    
    
    /** Pagina para los reportes */
    private PaginaVO paginaReportes;
    
    private String idsDestinatarios;
    
    Boveda boveda;
    
    private Map<String, String> tipoValorPermitidos;
    
    private boolean admin = false;
    
    private List<Long> idsCaevs;
    
    
    public CalendarioCapitalesController() {

    }

    /**
     * Inicializa el bean
     * 
     * @return
     */
    public String getInit() {
        try{
    	if (this.paginaVO == null) {
            this.paginaVO = new PaginaVO();
            
        }
    	cargaMapaImagenesEstadosEnvio();
        cargaMapaDescFechasAd();
        getTiposPagoCAEV();
        limpiarFiltro();
        if(tipoValorPermitidos == null){
        	tipoValorPermitidos = capitalesDistribucionService.getTipoValorLegislacionSicMap();
        }
        } catch(BusinessException be){
        	log.error("Ocurrio un error:",be);
    		this.addErrorMessage("Error al Iniciar la pantalla.");
        }
        admin = true;
        paginaVO.setRegistrosXPag(50);
        paginaVO.setOffset(0);
        banderaBitacoraConsulta = false;
       
        return null;
    }
       
    /**
     * Busca los registros por filtro
     * 
     * @param evt
     */
    public void buscarDerechos(final ActionEvent evt) {
        this.paginaVO.setRegistrosXPag(50);
        this.paginaVO.setOffset(0);
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
    	procesaFechas();
    	try{
    		validaFechas();	
    	}
    	catch (BusinessException be){
    		log.info("Error Fechas");
    		isError = true;
    		this.setConsultaEjecutada(true);
    	}
    	if(!isError){
    	this.ejecutarConsulta();
    	}
    	
    }

    /**
     * Genera los reportes de Consulta de Calendario Capitales
     * 
     * @param evt
     */
    public void generarReportes(ActionEvent evt) {
    	try{
    	this.reiniciarEstadoPeticion();
		paginaReportes = new PaginaVO();
		paginaReportes.setOffset(0);
		paginaReportes.setRegistrosXPag(PaginaVO.TODOS);	
		paginaReportes = capitalesDistribucionService.consultaCalendarioCapitales(calendario, paginaReportes);
		this.totalRegistrosReportes = paginaReportes.getTotalRegistros();
    	}catch(Exception e){
    	log.error("Error al generar reporte:",e);
    	addErrorMessage("Ocurri\u00F3 un error al generar el reporte.");
    	}
	
    	
    }
    
    /**
     * Ejecuta la consulta y calcula el numero de pagina para la paginacion. Este metodo es un
     * overide de la clasmerge y e padre
     */
    @Override
    public String ejecutarConsulta() {
    	limpiaIdentificadores();
    	try{
        this.setParams();
        isTipoValorValido();
        idsCaevs = cargaListaIdsCaevs();
        if(CollectionUtils.isNotEmpty(idsCaevs)){
	    	calendario.setIdsCaevs(idsCaevs);
	    }
        paginaVO = capitalesDistribucionService.consultaCalendarioCapitales(calendario, paginaVO);
        log.info(("Numero de Registros" + paginaVO.getRegistros().size()));
        this.totalPaginas = this.paginaVO.getTotalRegistros() / this.paginaVO.getRegistrosXPag();

        if (this.paginaVO.getTotalRegistros() % this.paginaVO.getRegistrosXPag() > 0) {
            this.totalPaginas++;
        }
        this.totalPaginas = this.totalPaginas <= 0 ? 1 : this.totalPaginas;
        totalRegistros = paginaVO.getTotalRegistros();
    	} catch(BusinessException be){
    		log.error("Error al consultar:",be);
    		addErrorMessage(be.getMessage());
        }
        catch(Exception e){
        	log.error("Error al consultar:",e);	
        	addErrorMessage("Ocurri\u00F3 un error al realizar la consulta.");	
        }
      
        this.setConsultaEjecutada(true);
        return null;
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
        if (StringUtils.isNotBlank(this.calendario.getEstadoEmision()) && this.pattern.matcher(this.calendario.getEstadoEmision()).matches()) {
        	this.calendario.setEstadoEmisionInt(Long.valueOf(this.calendario.getEstadoEmision()));
        }
        validaFolios();
    }

    /**
    * Se valida el Folio Indeval ingresado en los filtros
    * @return
    */    
    private void validaFolios() throws BusinessException{
    	if(StringUtils.isNotBlank(this.calendario.getFolioIndevalCadena())){
    		if(this.pattern.matcher(this.calendario.getFolioIndevalCadena()).matches()){
    			this.calendario.setFolioIndeval(Long.valueOf(this.calendario.getFolioIndevalCadena()));
    		}else{
    			this.calendario.setFolioIndevalCadena(null);
    			throw new BusinessException("Folio Indeval Inv\u00E1lido");
    		}
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
        if (this.resultados != null) {
            this.resultados.getRegistros().clear();
        }
        if (this.paginaVO.getRegistros() != null) {
            this.paginaVO.getRegistros().clear();
        }
        this.paginaVO.setRegistrosXPag(50);
        this.paginaVO.setOffset(0);

        this.setConsultaEjecutada(false);
    }
    
    private void limpiarFiltro(){
    	limpiaIdentificadores();
        getCaevsSelected().clear();
        this.calendario = new ConsultaCapitalesParamsTO();
       
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
    	this.calendario.setFolioIndeval(null);
    	this.calendario.setIdsCaevs(null);
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
    		log.error("Error al autorizar envio:",e);
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
		List<EnvioLegislacionSicDTO> listaEnvio = Collections.emptyList();;
		if(StringUtils.isNotEmpty(destinatarios)){
			listaEnvio = new ArrayList<EnvioLegislacionSicDTO>();
			String usuario = getNombreUsuarioSesion();
			String[] buff = destinatarios.split("\\|");
			for (String linea : buff){
				String[] datos = linea.split(":");
				if(datos.length == 2){
					String idevco= datos[0].substring(4);
					String idedo=datos[1];
					if(StringUtils.isNotEmpty(idedo) && !idedo.equals("-1")){
						Long ultimoHistorico = capitalesDistribucionService.consultaUltimoHistoricoCalendario(Long.valueOf(idevco));						
						if(ultimoHistorico != null){						
							EnvioLegislacionSicDTO envioLegislacionSicDTO = new EnvioLegislacionSicDTO();
						    envioLegislacionSicDTO.setIdHistorico(ultimoHistorico);	
							envioLegislacionSicDTO.setDestinatario(idedo);
							envioLegislacionSicDTO.setIdCalendario(Long.valueOf(idevco));
							envioLegislacionSicDTO.setUsuario(usuario);
							listaEnvio.add(envioLegislacionSicDTO);
						}					
					}
				}
			}			
		}
		return listaEnvio; 
	}
	
	public void validaFechas() throws BusinessException {
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
	 * Procesa fechas de envio inicial y final para evitar colapsar la aplicacion en la peticion del reporte excel con las narrativas
	 * 
	 */
	public void procesaFechas() {
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
	 * @return the admin
	 */
	public boolean isAdmin() {
		return admin;
	}

	/**
	 * @param admin the admin to set
	 */
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	/**
	 * @return the totalRegistrosReportes
	 */
	public int getTotalRegistrosReportes() {
		return totalRegistrosReportes;
	}

	/**
	 * @param totalRegistrosReportes the totalRegistrosReportes to set
	 */
	public void setTotalRegistrosReportes(int totalRegistrosReportes) {
		this.totalRegistrosReportes = totalRegistrosReportes;
	}
    
	 /**
     * @return the consultaEjecutada
     */
    public boolean isConsultaEjecutada() {
        return this.consultaEjecutada;
    }

    /**
     * @param consultaEjecutada the consultaEjecutada to set
     */
    public void setConsultaEjecutada(final boolean consultaEjecutada) {
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
    public void setTotalPaginas(final int totalPaginas) {
        this.totalPaginas = totalPaginas;
    }


    /**
     * @return the resultados
     */
    public PaginaVO getResultados() {
        return this.resultados;
    }

    /**
     * @param resultados the resultados to set
     */
    public void setResultados(final PaginaVO resultados) {
        this.resultados = resultados;
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
    public void setTotalRegistros(final int totalRegistros) {
        this.totalRegistros = totalRegistros;
    }

    public boolean isBanderaBitacoraConsulta() {
        return this.banderaBitacoraConsulta;
    }

    public void setBanderaBitacoraConsulta(final boolean banderaBitacoraConsulta) {
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
    public void setCalendario(final ConsultaCapitalesParamsTO calendario) {
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
    public void setPaginaReportes(final PaginaVO paginaReportes) {
        this.paginaReportes = paginaReportes;
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
	

	

}

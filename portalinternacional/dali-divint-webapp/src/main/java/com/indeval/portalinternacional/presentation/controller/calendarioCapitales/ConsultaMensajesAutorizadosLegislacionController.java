package com.indeval.portalinternacional.presentation.controller.calendarioCapitales;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.CapitalesDistribucionService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService;
import com.indeval.portalinternacional.middleware.servicios.modelo.Custodio;
import com.indeval.portalinternacional.middleware.servicios.modelo.DestinatarioIntSic;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoPagoInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.EnvioLegislacionSicDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.EstadoEnvioMoi;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * Controller para la pantalla de Consulta Mensajes Legislacion SIc enviados al sistema MOI (consultaMensajesAutorizadosLegislacion.xhtml)
 * @author javier.perez
 *
 */
public class ConsultaMensajesAutorizadosLegislacionController extends ControllerBase{
	
	private static final Logger LOG = LoggerFactory.getLogger(ConsultaMensajesAutorizadosLegislacionController.class);

    private boolean consultaEjecutada  = false;
    private boolean banderaBitacoraConsulta = false;
    
    /** control paginacion */
    private int totalPaginas = 1;   
    private int totalRegistros = 0;
    private int totalRegistrosReporte = 0;
    
    /** Pagina para los reportes */
    private PaginaVO paginaReportes;
    
    /** servicio de negocio */
    private CapitalesDistribucionService capitalesDistribucionService;
    private DivisionInternacionalService divisionInternacionalService;
    
    /** Filtro */
    private EnvioLegislacionSicDTO filtroConsulta = new EnvioLegislacionSicDTO();
    
    /** combos */
    private List<SelectItem> listaEstados;    
    private List<SelectItem> listaDestinatarios;    
    private List<SelectItem> listaCustodios;    
    private List<SelectItem> listaEstadoEmision;
    private List<SelectItem> listaTipoPagoCAEV;
    private List<SelectItem> listaTipoPagoCAMV;
    
    /** Patron numerico */
    private Pattern pattern = Pattern.compile("[0-9]*");
    
    
    public ConsultaMensajesAutorizadosLegislacionController(){}
    /**
     * Inicializa el bean
     * 
     * @return
     */
    public String getInit() {
    	if (this.paginaVO == null) {
    		this.paginaVO = new PaginaVO();
        }        
        this.paginaVO.setRegistrosXPag(50);
        this.paginaVO.setOffset(0);
        this.banderaBitacoraConsulta = false;
        limpiarFiltro();        
        return null;
    }
    
    /**
     * Ejecuta el evento de pantalla "Buscar"
     * @param evt
     */
    public void buscarEnvios(final ActionEvent evt){
    	LOG.info("Inica busqueda de informacion...");    	
    	this.getPaginaVO().setOffset(0);
    	this.getPaginaVO().setRegistrosXPag(50);
    	this.getPaginaVO().setRegistros(null);
    	this.ejecutarConsulta();    	    	
    }
    
    /**
     * Limpia los parametros del filtro
     */
    private void limpiarFiltro(){
    	this.limpiaIdentificadores();
    	this.filtroConsulta.setDestinatario("-1");
    	this.filtroConsulta.setEstadoCadena("-1");
    	this.filtroConsulta.setFuente("-1");
    	this.filtroConsulta.setEstadoEmision("-1");
    	this.filtroConsulta.setTipoDerechoCaev("-1");
    	this.filtroConsulta.setTipoDerechoCamv("-1");
    	    	
    	this.filtroConsulta.setFechaAutorizacionFin(null);
    	this.filtroConsulta.setFechaAutorizacionInicio(null);
    	this.filtroConsulta.setFechaSolicitudFin(null);
    	this.filtroConsulta.setFechaSolicitudInicio(null);
    	this.filtroConsulta.setFolioIndeval(null);
    	this.filtroConsulta.setFolioMensaje(null);
    	this.filtroConsulta.setUsuario(null);
    	this.filtroConsulta.setUsuarioAutoriza(null);
    	this.filtroConsulta.setTipoValor(null);
    	this.filtroConsulta.setEmisora(null);
    	this.filtroConsulta.setSerie(null);
    	this.filtroConsulta.setIsin(null);
    	this.filtroConsulta.setFechaExdateFin(null);
    	this.filtroConsulta.setFechaExdateInicio(null);
    	this.filtroConsulta.setFechaCorteFin(null);
    	this.filtroConsulta.setFechaCorteInicio(null);
    }
    
    /**
     * Asigna y convierte los valores num&eacute;ricos
     */
    private void setParams(){
    	if (StringUtils.isNotBlank(this.filtroConsulta.getEstadoCadena()) && this.pattern.matcher(this.filtroConsulta.getEstadoCadena()).matches()) {
            this.filtroConsulta.setEstado(Integer.valueOf(this.filtroConsulta.getEstadoCadena()));
         }
     	if (StringUtils.isNotBlank(this.filtroConsulta.getFolioIndeval())) {
     		if(this.pattern.matcher(this.filtroConsulta.getFolioIndeval()).matches()){
     			this.filtroConsulta.setIdCalendario(Long.valueOf(this.filtroConsulta.getFolioIndeval()));
     		}else{
     			this.filtroConsulta.setFolioIndeval(null);
     			throw new BusinessException("Folio Indeval Inv\u00E1lido");
     		}              
         }
    	if (StringUtils.isNotBlank(this.filtroConsulta.getFolioMensaje())) {
    		if(this.pattern.matcher(this.filtroConsulta.getFolioMensaje()).matches()){
    			this.filtroConsulta.setIdHistorico(Long.valueOf(this.filtroConsulta.getFolioMensaje()));
    		}else{
    			this.filtroConsulta.setFolioMensaje(null);
    			throw new BusinessException("Folio Mensaje Inv\u00E1lido");
    		}
            
       } 
    }
    
    /**
     * limpia los valores num&eacute;ricos del filtro
     */
    private void limpiaIdentificadores(){
    	this.filtroConsulta.setEstado(null);
       	this.filtroConsulta.setIdCalendario(null);
    	this.filtroConsulta.setIdHistorico(null);
    }
    
    /**
     * Ejecuta la consulta con los filtros seleccionados
     */
    public String ejecutarConsulta() {
    	try{
        	this.limpiaIdentificadores();
        	this.setParams();
        	paginaVO = capitalesDistribucionService.consultaAutorizacionesCalendarioCapitales(this.filtroConsulta, paginaVO);
            LOG.info(("Numero de Registros" + paginaVO.getRegistros().size()));
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
     * toma el evento de pantalla "Limpiar"
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
	    	paginaReportes = new PaginaVO();
	    	paginaReportes.setOffset(0);
	    	paginaReportes.setRegistrosXPag(PaginaVO.TODOS);				
			paginaReportes = capitalesDistribucionService.consultaAutorizacionesCalendarioCapitales(this.filtroConsulta, paginaReportes);
			this.totalRegistrosReporte = paginaVO.getTotalRegistros();
    	}catch(Exception e){
    		LOG.error("Error al generar reporte:",e);
    		addErrorMessage("Ocurri\u00F3 un error al generar el reporte.");
    	}
    }
    
    /**
     * Datos para cargar el combo de custodios
     * @return
     */
 	public List<SelectItem> getEstadosEnvio() {
 		if (this.listaEstados == null || this.listaEstados.isEmpty()) { 			
 			this.listaEstados = new ArrayList<SelectItem>();
 			this.listaEstados.add(new SelectItem(String.valueOf(EstadoEnvioMoi.POR_AUTORIZAR.ordinal()),EstadoEnvioMoi.POR_AUTORIZAR.getValor()));
 			this.listaEstados.add(new SelectItem(String.valueOf(EstadoEnvioMoi.AUTORIZADO.ordinal()),EstadoEnvioMoi.AUTORIZADO.getValor()));
 			this.listaEstados.add(new SelectItem(String.valueOf(EstadoEnvioMoi.CANCELADO.ordinal()),EstadoEnvioMoi.CANCELADO.getValor())); 			 
 		}		
 		return this.listaEstados;
 	}
 	
    /**
     * Datos para cargar el combo Destinatarios
     * @return
     */
    public List<SelectItem> getDestinatarios() {
        if (this.listaDestinatarios == null || this.listaDestinatarios.isEmpty()) {
        	List<DestinatarioIntSic> destinatarios = capitalesDistribucionService.obtieneDestinatariosIntSicByEstado(Long.valueOf(1));
        	this.listaDestinatarios = new ArrayList<SelectItem>();
            if (destinatarios != null) {
                for (DestinatarioIntSic destinatario : destinatarios) {
                	this.listaDestinatarios.add(new SelectItem(String.valueOf(destinatario.getBiccode()), destinatario.getNombre()));
                }
            } else {
            	this.listaDestinatarios.add(new SelectItem("-2", "VACIO"));
            }
        }                
        return this.listaDestinatarios;
    }
    
    /**
     * Datos para cargar el combo de custodios
     * @return
     */
 	public List<SelectItem> getCustodios() {
 		if (this.listaCustodios == null || this.listaCustodios.isEmpty()) {
 			List<Custodio> custodios = divisionInternacionalService.obtieneCatalogoCustodios();
 			this.listaCustodios = new ArrayList<SelectItem>();
 			if (custodios != null) {
 				for (Custodio custodio : custodios) {
 					this.listaCustodios.add(new SelectItem(custodio.getNombreCorto(), custodio.getDescripcion()));
 				}
 			} else {
 				this.listaCustodios.add(new SelectItem("-2", "VACIO"));
 			}
 		}		
 		return this.listaCustodios;
 	}
 	
    /**
     * Datos para cargar el combo Estado de la Emision
     * @return
     */
    public List<SelectItem> getEstadoEmisionCombo(){
    	if(listaEstadoEmision == null || listaEstadoEmision.isEmpty()){
    		listaEstadoEmision =  new ArrayList<SelectItem>();
    	
    		listaEstadoEmision.add(new SelectItem("LISTADA", "Listada"));
    		listaEstadoEmision.add(new SelectItem("DESLISTADA", "Deslistada"));
    		listaEstadoEmision.add(new SelectItem("MILA", "MILA"));
    		listaEstadoEmision.add(new SelectItem("FULL LISTED", "Full Listed"));
    	}
    	return listaEstadoEmision;
    }
    
    /**
     * Datos para cargar el combo tipo de Pago CAEV
     * @return
     */
     public List<SelectItem> getTiposPagoCAEV() {
         if (this.listaTipoPagoCAEV == null || this.listaTipoPagoCAEV.isEmpty()) {
         	this.listaTipoPagoCAEV = getTiposPagoMensaje(true);        	
         }
         return this.listaTipoPagoCAEV;
     }
     
     /**
      * Datos para cargar el combo tipo de Pago CAMV
      * @return
      */
     public List<SelectItem> getTiposPagoCAMV() {
         if (this.listaTipoPagoCAMV == null || this.listaTipoPagoCAMV.isEmpty()) {
         	this.listaTipoPagoCAMV = getTiposPagoMensaje(false);         	
         }
         return this.listaTipoPagoCAMV;
     }
 	
	/**
	 * Regresa el valor del Custodio seleccionado
	 * @return
	 */
	public String getSelectedCustodio() {
		String resultado = this.getSelected(this.filtroConsulta.getFuente(), this.listaCustodios);
		return resultado == null ? "TODOS" : resultado;
	}
    
	/**
	 * Regresa el valor seleccionado en Destinatario
	 */
	public String getSelectedDestinatario(){
		String resultado = getSelected(this.filtroConsulta.getDestinatario(), this.listaDestinatarios);
		return resultado == null ? "TODOS" : resultado;
	}
	
	/**
	 * Regresa el valor seleccionado en Destinatario
	 */
	public String getSelectedEstadoEnvio(){
		String resultado = getSelected(this.filtroConsulta.getEstadoCadena(), this.listaEstados);
		return resultado == null ? "TODOS" : resultado;
	}
	
	/**
	 * Regresa el valor seleccionado en Estado Emision
	 * @return
	 */
	public String getSelectedEstadoEmision(){
		String resultado = getSelected(this.filtroConsulta.getEstadoEmision(), this.listaEstadoEmision);
		return resultado == null ? "TODOS" : resultado;
	}
	
	/**
     * Regresa el valor de TipoPagoCAMV seleccionado
     * @return
     */
	public String getSelectedTipoPagoCAMV() {
		String resultado = this.getSelected(this.filtroConsulta.getTipoDerechoCamv(), this.listaTipoPagoCAMV);
		return resultado == null ? "TODOS" : resultado;
	}
	
	/**
	 * Regresa el valor de TipoPagoCAEV seleccionado
	 * @return
	 */
	public String getSelectedTipoPagoCAEV() {
		String resultado = this.getSelected(this.filtroConsulta.getTipoDerechoCaev(), this.listaTipoPagoCAEV);
		return resultado == null ? "TODOS" : resultado;
	}

	/**
	 * Regresa la descripci&oacute;n del valor en el combo seleccionado
	 * @param key
	 * @param lista
	 * @return
	 */
	private String getSelected(final String key, final List<SelectItem> lista) {
		String resultado = null;
		for (SelectItem item : lista) {
			if (key != null && item.getValue().equals(key)) {
				resultado = item.getLabel();
				break;
			}
		}
		return resultado;
	}
	
    /**
     * Auxiliar para determinar el tipo de pago solicitado
     * @return
     */
    private List<SelectItem> getTiposPagoMensaje(final Boolean isCAEV) {
        List<TipoPagoInt> tipos = divisionInternacionalService.obtieneTiposPagoInt(isCAEV);
        List<SelectItem> listaPagos = new ArrayList<SelectItem>();
        if (tipos != null) {
            for (TipoPagoInt tp : tipos) {
                listaPagos.add(new SelectItem(tp.getClavePago(), tp.getClavePago()));
            }
        } else {
            listaPagos.add(new SelectItem("-2", "VACIO"));
        }
        return listaPagos;
    }
	
	/**
	 * @return the consultaEjecutada
	 */
	public boolean isConsultaEjecutada() {
		return consultaEjecutada;
	}
	/**
	 * @param consultaEjecutada the consultaEjecutada to set
	 */
	public void setConsultaEjecutada(boolean consultaEjecutada) {
		this.consultaEjecutada = consultaEjecutada;
	}
	/**
	 * @return the banderaBitacoraConsulta
	 */
	public boolean isBanderaBitacoraConsulta() {
		return banderaBitacoraConsulta;
	}
	/**
	 * @param banderaBitacoraConsulta the banderaBitacoraConsulta to set
	 */
	public void setBanderaBitacoraConsulta(boolean banderaBitacoraConsulta) {
		this.banderaBitacoraConsulta = banderaBitacoraConsulta;
	}
	/**
	 * @return the totalPaginas
	 */
	public int getTotalPaginas() {
		return totalPaginas;
	}
	/**
	 * @param totalPaginas the totalPaginas to set
	 */
	public void setTotalPaginas(int totalPaginas) {
		this.totalPaginas = totalPaginas;
	}
	/**
	 * @return the totalRegistros
	 */
	public int getTotalRegistros() {
		return totalRegistros;
	}
	/**
	 * @param totalRegistros the totalRegistros to set
	 */
	public void setTotalRegistros(int totalRegistros) {
		this.totalRegistros = totalRegistros;
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
	 * @return the filtroConsulta
	 */
	public EnvioLegislacionSicDTO getFiltroConsulta() {
		return filtroConsulta;
	}
	/**
	 * @param filtroConsulta the filtroConsulta to set
	 */
	public void setFiltroConsulta(EnvioLegislacionSicDTO filtroConsulta) {
		this.filtroConsulta = filtroConsulta;
	}
	/**
	 * @return the listaCustodios
	 */
	public List<SelectItem> getListaCustodios() {
		return listaCustodios;
	}
	/**
	 * @param listaCustodios the listaCustodios to set
	 */
	public void setListaCustodios(List<SelectItem> listaCustodios) {
		this.listaCustodios = listaCustodios;
	}

	/**
	 * @return the listaEstadoEmision
	 */
	public List<SelectItem> getListaEstadoEmision() {
		return listaEstadoEmision;
	}
	/**
	 * @param listaEstadoEmision the listaEstadoEmision to set
	 */
	public void setListaEstadoEmision(List<SelectItem> listaEstadoEmision) {
		this.listaEstadoEmision = listaEstadoEmision;
	}
	/**
	 * @return the listaEstados
	 */
	public List<SelectItem> getListaEstados() {
		return listaEstados;
	}
	/**
	 * @param listaEstados the listaEstados to set
	 */
	public void setListaEstados(List<SelectItem> listaEstados) {
		this.listaEstados = listaEstados;
	}
	/**
	 * @return the listaDestinatarios
	 */
	public List<SelectItem> getListaDestinatarios() {
		return listaDestinatarios;
	}
	/**
	 * @param listaDestinatarios the listaDestinatarios to set
	 */
	public void setListaDestinatarios(List<SelectItem> listaDestinatarios) {
		this.listaDestinatarios = listaDestinatarios;
	}
	/**
	 * @return the listaTipoPagoCAEV
	 */
	public List<SelectItem> getListaTipoPagoCAEV() {
		return listaTipoPagoCAEV;
	}
	/**
	 * @param listaTipoPagoCAEV the listaTipoPagoCAEV to set
	 */
	public void setListaTipoPagoCAEV(List<SelectItem> listaTipoPagoCAEV) {
		this.listaTipoPagoCAEV = listaTipoPagoCAEV;
	}
	/**
	 * @return the listaTipoPagoCAMV
	 */
	public List<SelectItem> getListaTipoPagoCAMV() {
		return listaTipoPagoCAMV;
	}
	/**
	 * @param listaTipoPagoCAMV the listaTipoPagoCAMV to set
	 */
	public void setListaTipoPagoCAMV(List<SelectItem> listaTipoPagoCAMV) {
		this.listaTipoPagoCAMV = listaTipoPagoCAMV;
	}
	
	//se inyectan servicios
	/**
	 * @param capitalesDistribucionService the capitalesDistribucionService to set
	 */
	public void setCapitalesDistribucionService(CapitalesDistribucionService capitalesDistribucionService) {
		this.capitalesDistribucionService = capitalesDistribucionService;
	}
	/**
	 * @param divisionInternacionalService the divisionInternacionalService to set
	 */
	public void setDivisionInternacionalService(DivisionInternacionalService divisionInternacionalService) {
		this.divisionInternacionalService = divisionInternacionalService;
	}
}

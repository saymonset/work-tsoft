package com.indeval.portalinternacional.presentation.controller.conciliacionInternacional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService;
import com.indeval.portalinternacional.middleware.servicios.modelo.Custodio;
import com.indeval.portalinternacional.middleware.servicios.vo.DetalleConciliacionIntDTO;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * 
 * @author Luis R Munoz
 *
 */
public class ConsultaDetalleConciliacionInternacionalController extends ControllerBase{
	
	private static final Logger log = LoggerFactory.getLogger(ConsultaDetalleConciliacionInternacionalController.class);
	
	/** Listas */
	private List<SelectItem> listaCustodios;
	private List<SelectItem> listaBoveda;	
	
	private DivisionInternacionalService divisionInternacionalService;
	
	private boolean consultaEjecutada;
	
	private int totalPaginas = -1;	
	
	
	private PaginaVO resultados = null;

    private boolean banderaBitacoraConsulta = false;
	
	private int totalRegistros = 0;
	
	private DetalleConciliacionIntDTO detalleConciliacion;
	
	/** Parametros enviados por el Request */
	private Map<String, String> params;
	
	private String custodio;
	private String boveda;	
	private String tipoValor;
	private String emisora;
	private String serie;
	private String isin;
	private String folio;
	private Boolean conDiferencia;
	private Date fechaMensajeInicio;
	private Date fechaMensajeFin;	
	private Date fechaConciliacionInicio;
	private Date fechaConciliacionFin;
	/** Pagina para los reportes*/
    private PaginaVO paginaReportes;
    
    
	/**
	 * Inicializa el bean
	 * @return
	 */
	public String getInit()
	{
		if(paginaVO == null)
			paginaVO = new PaginaVO();
		
		
		this.custodio="-1";
		this.boveda="-1";		
		this.tipoValor="";
		this.emisora="";
		this.serie="";
		this.isin="";
		this.folio="";
		this.fechaMensajeInicio=null;
		this.fechaMensajeFin=null;
		this.fechaConciliacionInicio=null;
		this.fechaConciliacionFin=null;
		this.conDiferencia=false;
		
		inicializaCustodios();		
		inicializaBovedas();		
		
		paginaVO.setRegistrosXPag(50);
		paginaVO.setOffset(0);
		banderaBitacoraConsulta = false;
		
		return null;
	}
	
	/*
	 * =====================BOTONES========================
	 */

	/**
	 * Buscar las emisiones
	 * @param evt
	 */
	public void buscarDetalleConciliaciones(ActionEvent evt)
	{
        paginaVO.setRegistrosXPag(50);
		paginaVO.setOffset(0);
        getPaginaVO().setRegistros(null);
		ejecutarConsulta();

	}
	
	/**
	 * Genera los reportes de Consulta de Emisiones
	 * @param evt
	 */
	public void generarReportes(ActionEvent evt)
	{	

		reiniciarEstadoPeticion();
		paginaReportes = new PaginaVO();
		paginaReportes.setOffset(0);
		paginaReportes.setRegistrosXPag(PaginaVO.TODOS);		
		paginaReportes = divisionInternacionalService.consultaDetalleConciliacion(detalleConciliacion, paginaReportes);		
		this.totalRegistros=paginaReportes.getTotalRegistros();
		
	}
	
	/**
	 * Genera los reportes de Consulta de Emisiones
	 * @param evt
	 */
	public void generarReportesPopUp(ActionEvent evt)
	{	
		paginaReportes = new PaginaVO();
		paginaReportes.setOffset(0);
		paginaReportes.setRegistrosXPag(PaginaVO.TODOS);		
		paginaReportes = divisionInternacionalService.consultaDetalleConciliacion(detalleConciliacion, paginaReportes);		
		this.totalRegistros=paginaReportes.getTotalRegistros();
		
	}
	/**
	 * Ejecuta la consulta y calcula el numero de pagina para la paginacion.
	 *  Este metodo es un overide de la clase padre
	 */
	@Override
	public String ejecutarConsulta(){
		
		setParams();
		
		paginaVO = divisionInternacionalService.consultaDetalleConciliacion(detalleConciliacion, paginaVO);            
        
		totalPaginas = paginaVO.getTotalRegistros() / paginaVO.getRegistrosXPag();
		
		if(paginaVO.getTotalRegistros() % paginaVO.getRegistrosXPag() > 0){
			totalPaginas++;		
		}
		totalPaginas = (totalPaginas <= 0) ? 1 : totalPaginas;
		
		setConsultaEjecutada(true);
		return null;
	}
	/* =========================LAYOUT POPUP================================ */
	//para layoutpopup
	public String ejecutarConsultaPopUp() {
		Long id=null;
		if(params.get("folio")!=null ){
			id=Long.valueOf(params.get("folio"));
		}
		if(id != null){
			
			setFolio(id.toString());
			setParams();
			resultados = new PaginaVO();
			resultados.setOffset(0);
			resultados.setRegistrosXPag(PaginaVO.TODOS);
			resultados=divisionInternacionalService.consultaDetalleConciliacion(detalleConciliacion, paginaVO);
		}
		return null;
	}
	
	public String getInitPopUp() {
		params = new HashMap<String, String>();
        Set<String> keys = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().keySet();
        
        for (String key : keys) {
            params.put(key, FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key));
        }
        
        FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        ejecutarConsultaPopUp();
		return null;
	}
	/* =========================FIN LAYOUT POPUP================================ */
	private void setParams() {
		detalleConciliacion= new DetalleConciliacionIntDTO();
		detalleConciliacion.setEmisora(emisora);
		detalleConciliacion.setIsin(isin);
		detalleConciliacion.setSerie(serie);
		detalleConciliacion.setTipoValor(tipoValor);
		
		if(this.conDiferencia != null){
			detalleConciliacion.setConDiferencia(conDiferencia);
		}
		
		if (StringUtils.isNotEmpty(this.folio)  && this.folio.matches("[0-9]+")){
			detalleConciliacion.setFolio(Long.valueOf(folio));		
		}
		
		if(this.fechaMensajeInicio != null ) {
			detalleConciliacion.setFechaMensajeInicio(fechaMensajeInicio);
		}
		if(this.fechaMensajeFin != null ) {
			detalleConciliacion.setFechaMensajeFin(fechaMensajeFin);
		}
		if(this.fechaConciliacionInicio != null ) {
			detalleConciliacion.setFechaConciliacionInicio(fechaConciliacionInicio);
		}
		if(this.fechaConciliacionFin != null ) {
			detalleConciliacion.setFechaConciliacionFin(fechaConciliacionFin);
		}
		
		if (this.boveda != null && !this.boveda.equals("") && this.boveda.matches("-*[0-9]+")) {			
			detalleConciliacion.setBoveda(Long.valueOf(boveda));
		}		
		if (this.custodio != null && !this.custodio.equals("") && this.custodio.matches("-*[0-9]+")) {			
			detalleConciliacion.setCustodio(Long.valueOf(custodio));
		}
		
		
	}

	/**
	 * Limpia todos los campos
	 * @param evt
	 */
	public void limpiar(ActionEvent evt)
	{
        banderaBitacoraConsulta = false;       
 		
 		this.custodio="-1";
		this.boveda="-1";		
		this.tipoValor="";
		this.emisora="";
		this.serie="";
		this.isin="";
		this.folio="";
		this.fechaMensajeInicio=null;
		this.fechaMensajeFin=null;
		this.fechaConciliacionInicio=null;
		this.fechaConciliacionFin=null;
		this.conDiferencia=false;
 		
		if(resultados != null)
			resultados.getRegistros().clear();
		if(paginaVO.getRegistros() != null) {
			paginaVO.getRegistros().clear();
		}
		paginaVO.setRegistrosXPag(50);
		paginaVO.setOffset(0);		
		
		setConsultaEjecutada(false);
	}
	/*
	 * =====================FIN BOTONES========================
	 */

	/*
	 * =====================LISTAS==============================
	 */
	/**
	 * Obtiene la consulta de Bovedas
	 */
	@SuppressWarnings("unchecked")
	public List<SelectItem> getBovedas(){
		if(this.listaBoveda != null && this.listaBoveda.size() > 0) {
			return this.listaBoveda;
		}
		List<Boveda> bovedas = divisionInternacionalService.consultaBovedas(divisionInternacionalService.BOVEDA_VALORES_INTERNACIONAL);
    	List <SelectItem> listaBoveda = new ArrayList<SelectItem>();
    	if(bovedas != null){    		
	    	for (Boveda boveda : bovedas){
	    		listaBoveda.add(new SelectItem(boveda.getIdBoveda().toString(),boveda.getDescripcion()));	
	    	}
    	}else{
    		listaBoveda.add(new SelectItem("-2","VACIO"));
    	}
    	this.listaBoveda=listaBoveda;
    	return listaBoveda;
    }
	/**
	 * inicializa bovedas
	 */
	private void inicializaBovedas() {
		this.listaBoveda=getBovedas();		
	}	
    /**
	 * obtiene la lista de custodios
	 */
	public List<SelectItem> getCustodios(){	
		if(this.listaCustodios != null && this.listaCustodios.size() > 0){
			return this.listaCustodios;
		}
    	List<Custodio> custodios = divisionInternacionalService.obtieneCatalogoCustodios();
    	List <SelectItem> listaCustodios = new ArrayList<SelectItem>();
    	if( custodios != null){
	    	for (Custodio custodio : custodios){ 	    		
	    		listaCustodios.add(new SelectItem(custodio.getId().toString(), custodio.getDescripcion()));	    		    		
	    	}
    	}else{
    		listaCustodios.add(new SelectItem("-2","VACIO"));
    	}
    	this.listaCustodios=listaCustodios;
    	return listaCustodios;
    }
	
	
	/**
	 * Inicializa lista de custodios
	 */
	private void inicializaCustodios() {		
		listaCustodios =getCustodios();	
	}
	/*
	 * =====================FIN LISTAS==============================
	 */
	/*
	 * =====================SELECTED==============================
	 */
	/**
	 * 
	 */
	public String getSelectedBoveda(){
		String resultado = getSelected(getBoveda() ,this.listaBoveda);
		if(resultado != null){
			return resultado;
		}
		return "TODAS"; 
	}
	/**
	 * 
	 * @return
	 */
	public String getSelectedCustodio(){
		String resultado = getSelected(getCustodio() ,this.listaCustodios);
		if(resultado != null){
			return resultado;
		}
		return "TODOS"; 
	}
	
	private String getSelected(String key, List<SelectItem> lista){
		String resultado = null;
		for(SelectItem item : lista){
			if(key != null && item.getValue().equals(key))
				resultado=item.getLabel();
		}
		return resultado;		
	}
	/*
	 * =====================FIN SELECTED==============================
	 */
	
	/**
	 * @param divisionInternacionalService the divisionInternacionalService to set
	 */
	public void setDivisionInternacionalService(
			DivisionInternacionalService divisionInternacionalService) {
		this.divisionInternacionalService = divisionInternacionalService;
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
	 * @return the resultados
	 */
	public PaginaVO getResultados() {
		return resultados;
	}

	/**
	 * @param resultados the resultados to set
	 */
	public void setResultados(PaginaVO resultados) {
		this.resultados = resultados;
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

    public boolean isBanderaBitacoraConsulta() {
        return banderaBitacoraConsulta;
    }

    public void setBanderaBitacoraConsulta(boolean banderaBitacoraConsulta) {
        this.banderaBitacoraConsulta = banderaBitacoraConsulta;
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
	 * @param paginaReportes the paginaReportes to set
	 */
	public void setPaginaReportes(PaginaVO paginaReportes) {
		this.paginaReportes = paginaReportes;
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
	 * @return the listaBoveda
	 */
	public List<SelectItem> getListaBoveda() {
		return listaBoveda;
	}

	/**
	 * @param listaBoveda the listaBoveda to set
	 */
	public void setListaBoveda(List<SelectItem> listaBoveda) {
		this.listaBoveda = listaBoveda;
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
	 * @return the detalleConciliacion
	 */
	public DetalleConciliacionIntDTO getDetalleConciliacion() {
		return detalleConciliacion;
	}

	/**
	 * @param detalleConciliacion the detalleConciliacion to set
	 */
	public void setDetalleConciliacion(DetalleConciliacionIntDTO detalleConciliacion) {
		this.detalleConciliacion = detalleConciliacion;
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
	 * @return the boveda
	 */
	public String getBoveda() {
		return boveda;
	}

	/**
	 * @param boveda the boveda to set
	 */
	public void setBoveda(String boveda) {
		this.boveda = boveda;
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
	 * @return the conDiferencia
	 */
	public Boolean getConDiferencia() {
		return conDiferencia;
	}

	/**
	 * @param conDiferencia the conDiferencia to set
	 */
	public void setConDiferencia(Boolean conDiferencia) {
		this.conDiferencia = conDiferencia;
	}

	/**
	 * @return the fechaMensajeInicio
	 */
	public Date getFechaMensajeInicio() {
		return fechaMensajeInicio;
	}

	/**
	 * @param fechaMensajeInicio the fechaMensajeInicio to set
	 */
	public void setFechaMensajeInicio(Date fechaMensajeInicio) {
		this.fechaMensajeInicio = fechaMensajeInicio;
	}

	/**
	 * @return the fechaMensajeFin
	 */
	public Date getFechaMensajeFin() {
		return fechaMensajeFin;
	}

	/**
	 * @param fechaMensajeFin the fechaMensajeFin to set
	 */
	public void setFechaMensajeFin(Date fechaMensajeFin) {
		this.fechaMensajeFin = fechaMensajeFin;
	}

	/**
	 * @return the fechaConciliacionInicio
	 */
	public Date getFechaConciliacionInicio() {
		return fechaConciliacionInicio;
	}

	/**
	 * @param fechaConciliacionInicio the fechaConciliacionInicio to set
	 */
	public void setFechaConciliacionInicio(Date fechaConciliacionInicio) {
		this.fechaConciliacionInicio = fechaConciliacionInicio;
	}

	/**
	 * @return the fechaConciliacionFin
	 */
	public Date getFechaConciliacionFin() {
		return fechaConciliacionFin;
	}

	/**
	 * @param fechaConciliacionFin the fechaConciliacionFin to set
	 */
	public void setFechaConciliacionFin(Date fechaConciliacionFin) {
		this.fechaConciliacionFin = fechaConciliacionFin;
	}

	/**
	 * @return the paginaReportes
	 */
	public PaginaVO getPaginaReportes() {
		return paginaReportes;
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
	
}
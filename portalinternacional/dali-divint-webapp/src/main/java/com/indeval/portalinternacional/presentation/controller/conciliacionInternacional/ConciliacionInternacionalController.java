package com.indeval.portalinternacional.presentation.controller.conciliacionInternacional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService;
import com.indeval.portalinternacional.middleware.servicios.modelo.Custodio;
import com.indeval.portalinternacional.middleware.servicios.vo.ConciliacionIntDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.DetalleConciliacionIntDTO;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * 
 * @author Luis R Munoz
 *
 */
public class ConciliacionInternacionalController extends ControllerBase{
	
	private Pattern pattern; 
	private static final Logger log = LoggerFactory.getLogger(ConciliacionInternacionalController.class);
	
	/** Listas */
	private List<SelectItem> listaCustodios;
	private List<SelectItem> listaBoveda;
	
	private DivisionInternacionalService divisionInternacionalService;
	
	private boolean consultaEjecutada;
	
	private int totalPaginas = 1;	
	
	
	private PaginaVO resultados = null;

    private boolean banderaBitacoraConsulta = false;
	
	private int totalRegistros = 0;
	
	private ConciliacionIntDTO conciliacion;
	
	private String folio;
	private Date fechaMensajeInicio;
	private Date fechaMensajeFin;
	private String custodio;
	private String bovedaDali;
	private Date fechaConciliacionInicio;
	private Date fechaConciliacionFin;
	private boolean difDali;
	private boolean porConc;
	private boolean difPos;
	private boolean difCust;
	private boolean conciliacionNacional;
	
	/** Pagina para los reportes*/
    private PaginaVO paginaReportes;
    
    public ConciliacionInternacionalController(){
    	
    }
	/**
	 * Inicializa el bean
	 * @return
	 */
	public String getInit()
	{
		if(paginaVO == null)
			paginaVO = new PaginaVO();
		
		
		this.custodio="-1";
		this.bovedaDali="-1";		
		this.folio="";		
		this.fechaMensajeInicio=null;
		this.fechaMensajeFin=null;
		this.fechaConciliacionInicio=null;
		this.fechaConciliacionFin=null;
		this.difDali=false;
		this.porConc=false;
		this.difPos=false;
		this.difCust=false;
		
		inicializaCustodios();
		
		inicializaBovedas();
		
		
		paginaVO.setRegistrosXPag(50);
		paginaVO.setOffset(0);
		banderaBitacoraConsulta = false;
		
		return null;
	}
	
	

	/**
	 * Buscar las emisiones
	 * @param evt
	 */
	public void buscarConciliaciones(ActionEvent evt)
	{
        paginaVO.setRegistrosXPag(50);
		paginaVO.setOffset(0);
        getPaginaVO().setRegistros(null);
		ejecutarConsulta();

	}
	
	/**
	 * Genera los reportes de Consulta de Conciliaciones
	 * @param evt
	 */
	public void generarReportes(ActionEvent evt)
	{	
		paginaReportes = new PaginaVO();
		paginaReportes.setOffset(0);
		paginaReportes.setRegistrosXPag(PaginaVO.TODOS);		
		paginaReportes = divisionInternacionalService.consultaConciliacion(conciliacion, paginaReportes);		
		this.totalRegistros=paginaReportes.getTotalRegistros();
		
	}
	/**
	 * Ejecuta la consulta y calcula el numero de pagina para la paginacion.
	 *  Este metodo es un overide de la clase padre
	 */
	public String ejecutarConsulta(){
		
		setParams();		
		paginaVO = divisionInternacionalService.consultaConciliacion(conciliacion, paginaVO);            
        
		totalPaginas = paginaVO.getTotalRegistros() / paginaVO.getRegistrosXPag();
		
		if(paginaVO.getTotalRegistros() % paginaVO.getRegistrosXPag() > 0)
			totalPaginas++;		
		totalPaginas = (totalPaginas <= 0) ? 1 : totalPaginas;
		
		setConsultaEjecutada(true);
		return null;
	}
	
	private void setParams() {
		conciliacion= new ConciliacionIntDTO();
		conciliacion.setFolio(this.folio);
		
		
		if(this.fechaMensajeInicio != null ) {
			conciliacion.setFechaMensajeInicio(this.fechaMensajeInicio);
		}
		if(this.fechaMensajeFin != null ) {
			conciliacion.setFechaMensajeFin(this.fechaMensajeFin);
		}
		
		if(this.fechaConciliacionInicio != null ) {
			conciliacion.setFechaConciliacionInicio(this.fechaConciliacionInicio);
		}
		if(this.fechaConciliacionFin != null ) {
			conciliacion.setFechaConciliacionFin(this.fechaConciliacionFin);
		}
		
		
		if (this.custodio != null && !this.custodio.equals("") && this.custodio.matches("-*[0-9]+")) {			
			conciliacion.setCustodio(Integer.valueOf(custodio));
		}
		if (this.bovedaDali != null && !this.bovedaDali.equals("") && this.bovedaDali.matches("-*[0-9]+")) {			
			conciliacion.setBovedaDali(Integer.valueOf(bovedaDali));
		}
		//diferencia emisiones custodio 		
		conciliacion.setDifCust(difCust);
		//diferencia emisiones Dali
		conciliacion.setDifDali(difDali);
		//tienen diferencia en posicion positiva o negativa
		conciliacion.setDifPos(difPos);
		//aun no conciliados
		conciliacion.setPorConc(porConc);
		//en bovedas nacionales
		conciliacion.setConciliacionNacional(conciliacionNacional);
		
	}

	/**
	 * Limpia todos los campos
	 * @param evt
	 */
	public void limpiar(ActionEvent evt)
	{
        banderaBitacoraConsulta = false;
        
		this.custodio="-1";
		this.bovedaDali="-1";		
		this.folio="";		
		this.fechaMensajeInicio=null;
		this.fechaMensajeFin=null;
		this.fechaConciliacionInicio=null;
		this.fechaConciliacionFin=null;
		this.difDali=false;
		this.porConc=false;
		this.difPos=false;
		this.difCust=false;	
		this.conciliacionNacional=false;
		
		if(resultados != null)
			resultados.getRegistros().clear();
		if(paginaVO.getRegistros() != null) {
			paginaVO.getRegistros().clear();
		}
		paginaVO.setRegistrosXPag(50);
		paginaVO.setOffset(0);		
		
		setConsultaEjecutada(false);
	}
	/**
	 * Obtiene la consulta de Bovedas
	 * 
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
	 * @return the listaCustodios
	 */
	public List getListaCustodios() {
		return listaCustodios;
	}

	/**
	 * @param listaCustodios the listaCustodios to set
	 */
	public void setListaCustodios(List listaCustodios) {
		this.listaCustodios = listaCustodios;
	}

	/**
	 * @return the divisionInternacionalService
	 */
	public DivisionInternacionalService getDivisionInternacionalService() {
		return divisionInternacionalService;
	}

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
	
	
	public String getSelectedBoveda(){
		String resultado = getSelected(getBovedaDali() ,this.listaBoveda);
		if(resultado != null){
			return resultado;
		}
		return "TODAS"; 
	}
	
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
	/**
	 * Concilia
	 * @param evt
	 */
	public void concilia(ActionEvent evt){
		Long idConciliacion=Long.valueOf(getHiddenField("daliForm:idConciliacionHidden"));
		divisionInternacionalService.instruyeConciliacion(idConciliacion);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
                		"Se esta conciliando el folio: "+idConciliacion+" actualice en unos momentos",
                		"Se esta conciliando el folio: "+idConciliacion+" actualice en unos momentos"));
//		addMessage("Se esta conciliando el folio: "+idConciliacion+" actualice en unos momentos");
	}
	/**
	 * Obtiene un campo oculto de la forma
	 * @param hiddenField
	 * @return
	 */
	private String getHiddenField(String hiddenField){		
		Map<String , String> map=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();		
		String conciliacion=map.get(hiddenField);	
		
		return conciliacion;
	}
	/**
	 * Action para generar el reporte de auditoria
	 * 
	 * @return
	 */
	public String generarReporteAuditoria(){
		String idConciliacion=getHiddenField("daliForm:idConciliacionHidden");
		String guardarReporte = getHiddenField("daliForm:reporteAuditoria");
		if(guardarReporte.equals("true")){
			log.info("guardar el reporte "+idConciliacion);
			divisionInternacionalService.generaReporteAuditoriaConciliacion(Long.valueOf(idConciliacion));
		}
		DetalleConciliacionIntDTO conc= new DetalleConciliacionIntDTO();
		conc.setFolio(Long.valueOf(idConciliacion));
		paginaReportes = new PaginaVO();
		paginaReportes.setOffset(0);
		paginaReportes.setRegistrosXPag(PaginaVO.TODOS);		
		paginaReportes = divisionInternacionalService.consultaDetalleConciliacion(conc, paginaReportes);		
		this.totalRegistros=paginaReportes.getTotalRegistros();
		return "conciliacionInternacionalXLSAuditoria";
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
	 * @return the conciliacion
	 */
	public ConciliacionIntDTO getConciliacion() {
		return conciliacion;
	}
	/**
	 * @param conciliacion the conciliacion to set
	 */
	public void setConciliacion(ConciliacionIntDTO conciliacion) {
		this.conciliacion = conciliacion;
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
	 * @return the bovedaDali
	 */
	public String getBovedaDali() {
		return bovedaDali;
	}
	/**
	 * @param bovedaDali the bovedaDali to set
	 */
	public void setBovedaDali(String bovedaDali) {
		this.bovedaDali = bovedaDali;
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
	 * @return the difDali
	 */
	public boolean isDifDali() {
		return difDali;
	}
	/**
	 * @param difDali the difDali to set
	 */
	public void setDifDali(boolean difDali) {
		this.difDali = difDali;
	}
	/**
	 * @return the porConc
	 */
	public boolean isPorConc() {
		return porConc;
	}
	/**
	 * @param porConc the porConc to set
	 */
	public void setPorConc(boolean porConc) {
		this.porConc = porConc;
	}
	/**
	 * @return the difPos
	 */
	public boolean isDifPos() {
		return difPos;
	}
	/**
	 * @param difPos the difPos to set
	 */
	public void setDifPos(boolean difPos) {
		this.difPos = difPos;
	}
	/**
	 * @return the difCust
	 */
	public boolean isDifCust() {
		return difCust;
	}
	/**
	 * @param difCust the difCust to set
	 */
	public void setDifCust(boolean difCust) {
		this.difCust = difCust;
	}
	/**
	 * @return the conciliacionNacional
	 */
	public boolean isConciliacionNacional() {
		return conciliacionNacional;
	}
	/**
	 * @param conciliacionNacional the conciliacionNacional to set
	 */
	public void setConciliacionNacional(boolean conciliacionNacional) {
		this.conciliacionNacional = conciliacionNacional;
	}
	
		
}
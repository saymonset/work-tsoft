package com.indeval.portalinternacional.presentation.controller.ejercicioDerechosInt;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.dao.common.DivisaDao;
import com.indeval.portaldali.persistence.modelo.Divisa;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.Control;
import com.indeval.portalinternacional.middleware.servicios.modelo.Custodio;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstadoDerechoInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoPagoInt;
import com.indeval.portalinternacional.middleware.servicios.vo.CalendarioEmisionesDeudaExtDTO;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * 
 * @author Luis R Munoz
 *
 */
public class CalendarioEmisionesDeudaExtController extends ControllerBase{
	
	private Pattern pattern; 
	private static final Logger log = LoggerFactory.getLogger(CalendarioEmisionesDeudaExtController.class);
	
	/** Listas */
	private List<SelectItem> listaCustodios;
	private List<SelectItem> listaEstatus;
	private List<SelectItem> listaDivisa;
	private List<SelectItem> listaTipoPagoCAEV;
	private List<SelectItem> listaTipoPagoCAMV;
	private List<SelectItem> listaEstadoMensajeria;
	private Map<String,String> derechosAutomatizadosDeuda;
	
	private DivisaDao divisaDao;
	private DivisionInternacionalService divisionInternacionalService;
	
	private boolean consultaEjecutada;
	
	private int totalPaginas = 1;	
	
	
	private PaginaVO resultados = null;

    private boolean banderaBitacoraConsulta = false;
	
	private int totalRegistros = 0;
	
	private CalendarioEmisionesDeudaExtDTO calendario;
	
	private String custodio;
	private String estado;
	private String tipoPagoCAEV;
	private String tipoPagoCAMV;
	private String divisa;
	private String tipoValor;
	private String emisora;
	private String serie;
	private String isin;
	private String cupon;
	private String refCustodio;
	private Date fechaPago;
	private Date fechaCorte;	
	private Boolean hojaDeTrabajo;
	private Boolean iscalIndeval;
	private String estadoMensajeria;
	/** Pagina para los reportes*/
    private PaginaVO paginaReportes;
    
    public CalendarioEmisionesDeudaExtController(){
    	this.pattern = Pattern.compile("/calendarioEmisionesDeudaExt.faces$");    	
    	this.iscalIndeval =pattern.matcher(getRequestURL()).find();
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
		this.estado="-1";
		this.tipoPagoCAEV="-1";
		this.tipoPagoCAMV="-1";
		this.divisa="-1";
		this.tipoValor="";
		this.emisora="";
		this.serie="";
		this.isin="";
		this.cupon="";
		this.refCustodio="";
		this.fechaPago=null;
		this.fechaCorte=null;
		this.estadoMensajeria="";
		this.hojaDeTrabajo=false;
		
		inicializaCustodios();
		
		inicializaEstatus();
		inicializaDivisa();
		inicializaTipoPagoCAEV();
		inicializaTipoPagoCAMV();
		
		paginaVO.setRegistrosXPag(50);
		paginaVO.setOffset(0);
		banderaBitacoraConsulta = false;
		
		return null;
	}
	
	

	/**
	 * Buscar las emisiones
	 * @param evt
	 */
	public void buscarDerechos(ActionEvent evt)
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
		paginaReportes = divisionInternacionalService.consultaCalendarioDerechos(calendario, paginaReportes);		
		this.totalRegistros=paginaReportes.getTotalRegistros();
		
	}
	/**
	 * Ejecuta la consulta y calcula el numero de pagina para la paginacion.
	 *  Este metodo es un overide de la clase padre
	 */
	public String ejecutarConsulta(){
		
		setParams();
		
		paginaVO = divisionInternacionalService.consultaCalendarioDerechos(calendario, paginaVO);            
        
		totalPaginas = paginaVO.getTotalRegistros() / paginaVO.getRegistrosXPag();
		
		if(paginaVO.getTotalRegistros() % paginaVO.getRegistrosXPag() > 0)
			totalPaginas++;		
		totalPaginas = (totalPaginas <= 0) ? 1 : totalPaginas;
		
		setConsultaEjecutada(true);
		return null;
	}
	
	private void setParams() {
		calendario= new CalendarioEmisionesDeudaExtDTO();
		calendario.setRefCustodio(refCustodio);
		
		calendario.setTipoValor(tipoValor);
		calendario.setEmisora(emisora);
		calendario.setSerie(serie);
		calendario.setCupon(cupon);
		calendario.setIsin(isin);
		if(this.fechaCorte != null ) {
			calendario.setFechaCorte(fechaCorte);
		}
		if(this.fechaPago != null ) {
			calendario.setFechaPago(fechaPago);
		}
		
		
		if (this.custodio != null && !this.custodio.equals("") && this.custodio.matches("-*[0-9]+")) {			
			calendario.setCustodio(Integer.valueOf(custodio));
		}
		if (this.divisa != null && !this.divisa.equals("") && !this.divisa.matches("-*[0-9]+")) {			
			calendario.setDivisa(divisa);
		}
		if (this.estado != null && !this.estado.equals("") && this.estado.matches("-*[0-9]+")) {			
			calendario.setEstado(Integer.valueOf(estado));
		}
		if (this.tipoPagoCAEV != null && !this.tipoPagoCAEV.equals("") && this.tipoPagoCAEV.matches("-*[0-9]+")) {			
			calendario.setTipoPagoCAEV(Integer.valueOf(tipoPagoCAEV));
		}
		if (this.tipoPagoCAMV != null && !this.tipoPagoCAMV.equals("") && this.tipoPagoCAMV.matches("-*[0-9]+")) {			
			calendario.setTipoPagoCAMV(Integer.valueOf(tipoPagoCAMV));
		}	
		
			//es calendario indeval?			
		calendario.setCalendarioIndeval(this.iscalIndeval);
		
		calendario.setHojaDeTrabajo(this.hojaDeTrabajo);
		calendario.setEstadoMensajeria(this.estadoMensajeria);
	}

	/**
	 * Limpia todos los campos
	 * @param evt
	 */
	public void limpiar(ActionEvent evt)
	{
        banderaBitacoraConsulta = false;
         //calendario.init();		
        this.custodio="-1";
 		this.estado="-1";
 		this.tipoPagoCAEV="-1";
 		this.tipoPagoCAMV="-1";
 		this.divisa="-1";
 		this.tipoValor="";
 		this.emisora="";
 		this.serie="";
 		this.isin="";
 		this.cupon="";
 		this.refCustodio="";
 		this.fechaPago=null;
 		this.fechaCorte=null;
 		this.estadoMensajeria="";
 		this.hojaDeTrabajo=false;
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
	 * Obtiene la consulta de Estados
	 */
	@SuppressWarnings("unchecked")
	public List<SelectItem> getEstados(){
		if(this.listaEstatus != null && this.listaEstatus.size() > 0) {
			return this.listaEstatus;
		}
		List<EstadoDerechoInt> estados = divisionInternacionalService.obtieneEstadosDerechoInt();
    	List <SelectItem> listaEstados = new ArrayList<SelectItem>();
    	if(estados != null){
	    	for (EstadoDerechoInt estado:estados){
	    		if(!this.iscalIndeval){
	    			if(estado.getParticipante() == 1){
	    				if(estado.getDescripcion().equals("SUSPENDIDO")){
	    					listaEstados.add(new SelectItem(estado.getId().toString(),"EN PROCESO"));
	    				}else{
	    					listaEstados.add(new SelectItem(estado.getId().toString(),estado.getDescripcion()));
	    				}
	    			}
	    		}else{
	    			listaEstados.add(new SelectItem(estado.getId().toString(),estado.getDescripcion()));
	    		}
	    	}
    	}else{
    		listaEstados.add(new SelectItem("-2","VACIO"));
    	}
    	return listaEstados;
    }
	
	/**
	 * Obtiene la consulta de Estados Mensajeria
	 */
	@SuppressWarnings("unchecked")
	public List<SelectItem> getEstadosMensajeria(){
		if(listaEstadoMensajeria == null){
			List <SelectItem> listaEstados = new ArrayList<SelectItem>();
	    	listaEstados.add(new SelectItem("-1","Todos"));
	    	listaEstadoMensajeria=listaEstados;
		}
		return listaEstadoMensajeria;
		
    }
	public void updateEstadoMensajeria(ValueChangeEvent event){
		listaEstadoMensajeria.clear();
		String edo = (String) event.getNewValue();
		List<Control> estados = divisionInternacionalService.obtieneEstadosMensajeria(edo);
    	List <SelectItem> listaEstados = new ArrayList<SelectItem>();
    	listaEstados.add(new SelectItem("-1","Todos"));
    	if(estados != null){
	    	for (Control estado:estados){	    		
	    		listaEstados.add(new SelectItem(estado.getIdControl().toString(),estado.getDescripcion()));	    		
	    	}
    	}
    	listaEstadoMensajeria = listaEstados;
	}
	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<SelectItem> getTiposPagoCAEV(){
		if(this.listaTipoPagoCAEV != null && this.listaTipoPagoCAEV.size() > 0){
			return this.listaTipoPagoCAEV;
		}
		return getTiposPagoMensaje(true);
    }
	
	
	
	@SuppressWarnings("unchecked")
	public List<SelectItem> getTiposPagoCAMV(){
		if(this.listaTipoPagoCAMV != null && this.listaTipoPagoCAMV.size() > 0) {
			return this.listaTipoPagoCAMV;
		}
		return getTiposPagoMensaje(false);
    }
	
	
	/**
	 * getTiposPago
	 * @return
	 */
	public List<SelectItem> getTiposPago() {
		return getTiposPagoMensaje(null);
    }
	/**
	 * obtiene los tipos de mensaje
	 */
	private List<SelectItem> getTiposPagoMensaje(Boolean isCAEV) {		
		List<TipoPagoInt> tipos = divisionInternacionalService.obtieneTiposPagoInt(isCAEV);
    	List <SelectItem> listaPagos = new ArrayList<SelectItem>();
    	if(tipos != null){
	    	for (TipoPagoInt tp : tipos){
	    		if(!this.iscalIndeval){
	    			if(tp.getParticipante() == 1){
	    				listaPagos.add(new SelectItem(tp.getId().toString(),tp.getClavePago()));
	    			}
	    		}else{
	    			listaPagos.add(new SelectItem(tp.getId().toString(),tp.getClavePago()));
	    		}
	    		
	    	}
    	}else{
    		listaPagos.add(new SelectItem("-2","VACIO"));
    	}
    	return listaPagos;
    }
	
	private void inicializaTipoPagoCAMV() {
		this.listaTipoPagoCAMV=getTiposPagoCAMV();
		
	}

	private void inicializaTipoPagoCAEV() {
		this.listaTipoPagoCAEV=getTiposPagoCAEV();		
	}

	private void inicializaDivisa() {
		this.listaDivisa=getDivisas();		
	}
	
	/**
     * Obtiene una lista de <code>SelectItem</code> para popular el combo box de eleccion de divisa
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<SelectItem> getDivisas(){
    	if(this.listaDivisa != null && this.listaDivisa.size() > 0){
			return this.listaDivisa;
		}
    	Divisa[] listaDivisas = divisaDao.findDivisas();
    	List<SelectItem> listaSelectDivisas = new ArrayList<SelectItem>();
    	for (int i=0; i<listaDivisas.length; i++){
    		listaSelectDivisas.add(new SelectItem(listaDivisas[i].getClaveAlfabetica(), listaDivisas[i].getDescripcion()));
    	}
    	return listaSelectDivisas;
    }
	/**
	 * 
	 */
	public List<SelectItem> getCustodios(){	
		if(this.listaCustodios != null && this.listaCustodios.size() > 0){
			return this.listaCustodios;
		}
    	List<Custodio> custodios = divisionInternacionalService.obtieneCatalogoCustodios();
    	List <SelectItem> listaCustodios = new ArrayList<SelectItem>();
    	if( custodios != null){
	    	for (Custodio custodio : custodios){ 
	    		if(!this.iscalIndeval){
	    			if(custodio.getParticipante() == 1){
	    				listaCustodios.add(new SelectItem(custodio.getId().toString(), custodio.getDescripcion()));
	    			}
	    		}else{
	    			listaCustodios.add(new SelectItem(custodio.getId().toString(), custodio.getDescripcion()));
	    		}	    		
	    	}
    	}else{
    		listaCustodios.add(new SelectItem("-2","VACIO"));
    	}
    	return listaCustodios;
    }
	
	
	/**
	 * Inicializa lista de custodios
	 */
	private void inicializaCustodios() {		
		listaCustodios =getCustodios();		
	}
	
	/**
	 * Inicializa lista de estatus de emisiones
	 */
	private void inicializaEstatus() {		
		listaEstatus = getEstados();	
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
	 * @return the listaEstatus
	 */
	public List getListaEstatus() {
		return listaEstatus;
	}

	/**
	 * @param listaEstatus the listaEstatus to set
	 */
	public void setListaEstatus(List listaEstatus) {
		this.listaEstatus = listaEstatus;
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
	 * @return the calendario
	 */
	public CalendarioEmisionesDeudaExtDTO getCalendario() {
		return calendario;
	}

	/**
	 * @param calendario the calendario to set
	 */
	public void setCalendario(CalendarioEmisionesDeudaExtDTO calendario) {
		this.calendario = calendario;
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
	 * @return the tipoPagoCAEV
	 */
	public String getTipoPagoCAEV() {
		return tipoPagoCAEV;
	}

	/**
	 * @param tipoPagoCAEV the tipoPagoCAEV to set
	 */
	public void setTipoPagoCAEV(String tipoPagoCAEV) {
		this.tipoPagoCAEV = tipoPagoCAEV;
	}

	/**
	 * @return the tipoPagoCAMV
	 */
	public String getTipoPagoCAMV() {
		return tipoPagoCAMV;
	}

	/**
	 * @param tipoPagoCAMV the tipoPagoCAMV to set
	 */
	public void setTipoPagoCAMV(String tipoPagoCAMV) {
		this.tipoPagoCAMV = tipoPagoCAMV;
	}

	/**
	 * @return the divisa
	 */
	public String getDivisa() {
		return divisa;
	}

	/**
	 * @param divisa the divisa to set
	 */
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}

	/**
	 * @return the tipoValor
	 */
	public String getTipoValor() {
		return this.tipoValor != null ? this.tipoValor.toUpperCase():null;
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
	 * @return the refCustodio
	 */
	public String getRefCustodio() {
		return refCustodio;
	}

	/**
	 * @param refCustodio the refCustodio to set
	 */
	public void setRefCustodio(String refCustodio) {
		this.refCustodio = refCustodio;
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
	 * @return the fechaCorte
	 */
	public Date getFechaCorte() {
		return fechaCorte;
	}

	/**
	 * @param fechaCorte the fechaCorte to set
	 */
	public void setFechaCorte(Date fechaCorte) {
		this.fechaCorte = fechaCorte;
	}

	/**
	 * @return the cupon
	 */
	public String getCupon() {
		return cupon;
	}

	/**
	 * @param cupon the cupon to set
	 */
	public void setCupon(String cupon) {
		this.cupon = cupon;
	}

	/**
	 * @param divisaDao the divisaDao to set
	 */
	public void setDivisaDao(DivisaDao divisaDao) {
		this.divisaDao = divisaDao;
	}
	public String registrarDerechos(){
		Set<Long> ids = getIdsModificar("daliForm:calIdDerechoRegistrar");
		log.debug("ids "+ids);		
		Integer regactualizados=divisionInternacionalService.actualizarEstadosDerechoInt(ids, Constantes.CALENDARIO_DERECHOS_REGISTRADO, isUserInRoll("INT_CAL_INDEVAL_SU"));		
		return null;
	}
	public String autorizarDerechos(){
		Set<Long> ids = getIdsModificar("daliForm:calIdDerechoAutorizar");
		log.debug("ids "+ids);		
		Integer regactualizados=divisionInternacionalService.actualizarEstadosDerechoInt(ids, Constantes.CALENDARIO_DERECHOS_AUTORIZADO, isUserInRoll("INT_CAL_INDEVAL_SU"));		
		return null;
	}
	public String previoDerechos(){
		Set<Long> ids = getIdsModificar("daliForm:calIdDerechoPrevio");
		log.debug("ids "+ids);
		Integer regactualizados=divisionInternacionalService.actualizarEstadosDerechoInt(ids, Constantes.CALENDARIO_DERECHOS_PREVIO, isUserInRoll("INT_CAL_INDEVAL_SU"));
		return null;
	}
	public String confirmaDerechos(){
		Set<Long> ids = getIdsModificar("daliForm:calIdDerechoConfirmar");
		log.debug("ids "+ids);
		Integer regactualizados=divisionInternacionalService.actualizarEstadosDerechoInt(ids, Constantes.CALENDARIO_DERECHOS_CONFIRMADO, isUserInRoll("INT_CAL_INDEVAL_SU"));
		return null;
	}
	public String preliquidaDerechos(){
		Set<Long> ids = getIdsModificar("daliForm:calIdDerechoPreliquidar");
		log.debug("ids "+ids);
		Integer regactualizados=divisionInternacionalService.actualizarEstadosDerechoInt(ids, Constantes.CALENDARIO_DERECHOS_PRELIQUIDADO, isUserInRoll("INT_CAL_INDEVAL_SU"));
		return null;
	}
	public String liquidaDerechos(){
		Set<Long> ids = getIdsModificar("daliForm:calIdDerechoLiquidar");
		log.debug("ids "+ids);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar una boveda, por favor revisar.", "Debe seleccionar una boveda, por favor revisar."));
		//Integer regactualizados=divisionInternacionalService.actualizarEstadosDerechoInt(ids, Constantes.CALENDARIO_DERECHOS_LIQUIDADO, isUserInRoll("INT_CAL_INDEVAL_SU"));
		return null;
	}
	public String corregirReversal(){
		Set<Long> ids = getIdsModificar("daliForm:calIdDerechoCorregir");
		log.debug("ids "+ids);
		Integer regactualizados=divisionInternacionalService.actualizarEstadosDerechoInt(ids, Constantes.CALENDARIO_DERECHOS_CORREGIDO, isUserInRoll("INT_CAL_INDEVAL_SU"));
		return null;
	}
	public String cancelaDerechos(){
		Set<Long> ids = getIdsModificar("daliForm:calIdDerechoCancelar");
		log.debug("ids "+ids);
		Integer regactualizados=divisionInternacionalService.actualizarEstadosDerechoInt(ids, Constantes.CALENDARIO_DERECHOS_CANCELADO, isUserInRoll("INT_CAL_INDEVAL_SU"));
		return null;
	}
	public String suspendeDerechos(){
		Set<Long> ids = getIdsModificar("daliForm:calIdDerechoSuspender");
		log.debug("ids "+ids);
		Integer regactualizados=divisionInternacionalService.actualizarEstadosDerechoInt(ids, Constantes.CALENDARIO_DERECHOS_SUSPENDIDO, isUserInRoll("INT_CAL_INDEVAL_SU"));
		return null;
	}
	private Set<Long> getIdsModificar(String hiddenField){		
		Map<String , String> map=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();		
		String derechos=map.get(hiddenField);		
				
		StringTokenizer tokenizer = new StringTokenizer(derechos, ",");
		
		Set<Long> ids = new HashSet<Long>();
		while(tokenizer.hasMoreElements()){
			String element=tokenizer.nextToken();
			if(StringUtils.isNotBlank(element)){					
				Long id = Long.valueOf(element);
				log.debug("=====================> ids: "+id);
				ids.add(id);				
			}								
		}
		if(ids.size()>0){
			return ids;
		}
		return null;
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
	
	public String getSelectedDivisa(){
		String resultado = getSelected(getDivisa() ,this.listaDivisa);
		if(resultado != null){
			return resultado;
		}
		return "TODAS"; 
	}
	
	public String getSelectedTipoPagoCAMV(){
		String resultado = getSelected(getTipoPagoCAMV() ,this.listaTipoPagoCAMV);
		if(resultado != null){
			return resultado;
		}
		return "TODOS"; 
	}
	
	public String getSelectedTipoPagoCAEV(){
		String resultado = getSelected(getTipoPagoCAEV() ,this.listaTipoPagoCAEV);
		if(resultado != null){
			return resultado;
		}
		return "TODOS"; 
	}
	
	public String getSelectedEstado(){
		String resultado = getSelected(getEstado() ,this.listaEstatus);
		if(resultado != null){
			return resultado;
		}
		return "TODOS"; 
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

	

	private String getRequestURL(){
	    Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
	    if(request instanceof HttpServletRequest){
	            return ((HttpServletRequest) request).getRequestURL().toString();
	    }else{
	        return null;
	    }
	}
	/**
	 * @return the hojaDeTrabajo
	 */
	public Boolean getHojaDeTrabajo() {
		return hojaDeTrabajo;
	}
	/**
	 * @param hojaDeTrabajo the hojaDeTrabajo to set
	 */
	public void setHojaDeTrabajo(Boolean hojaDeTrabajo) {
		this.hojaDeTrabajo = hojaDeTrabajo;
	}
	/**
	 * @return the estadoMensajeria
	 */
	public String getEstadoMensajeria() {
		return estadoMensajeria;
	}
	/**
	 * @param estadoMensajeria the estadoMensajeria to set
	 */
	public void setEstadoMensajeria(String estadoMensajeria) {
		this.estadoMensajeria = estadoMensajeria;
	}
	/**
	 * @return the listaEstadoMensajeria
	 */
	public List<SelectItem> getListaEstadoMensajeria() {
		return listaEstadoMensajeria;
	}
	/**
	 * @param listaEstadoMensajeria the listaEstadoMensajeria to set
	 */
	public void setListaEstadoMensajeria(List<SelectItem> listaEstadoMensajeria) {
		this.listaEstadoMensajeria = listaEstadoMensajeria;
	}
	/**
	 * @return the derechosAutomatizadosDeuda
	 */
	public Map<String, String> getDerechosAutomatizadosDeuda() {
		if(derechosAutomatizadosDeuda == null || derechosAutomatizadosDeuda.size()>0){
			this.derechosAutomatizadosDeuda = divisionInternacionalService.getDerechosAutomatizadosDeudaMap();
		}
		return derechosAutomatizadosDeuda;
	}
		
}
package com.indeval.portalinternacional.presentation.controller.eventosCorporativos;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.seguridad.dto.UsuarioDTO;
import com.bursatec.seguridad.presentation.constants.SeguridadConstants;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ConsultaEventosCorporativosService;
import com.indeval.portalinternacional.middleware.servicios.modelo.Custodio;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.Estado;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.EventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.EventoCorporativoConsultaDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ReglaEstado;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.TipoDerechoEvCo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.TipoEvento;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.TipoMercado;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * 
 * @author Luis R Munoz
 *
 */
public class ConsultaEventosCorporativosController extends ControllerBase{
	
	private static final Long ESTADO_REGISTRADO = 1l;
	private Pattern pattern; 
	private static final Logger log = LoggerFactory.getLogger(ConsultaEventosCorporativosController.class);
	private ConsultaEventosCorporativosService consultaEventosCorporativosService;
	
	
	/** Listas */
	private List<SelectItem> listaCustodios;
	private List<SelectItem> listaEstatus;	
	private List<SelectItem> listaTipoMercado;
	private List<SelectItem> listaTipoDerechoMO;
	private List<SelectItem> listaTipoDerechoML;
	private List<SelectItem> listaTipoEvento;
	private List<ReglaEstado> reglasEstado;
		
	private boolean consultaEjecutada;
	
	private int totalPaginas = 1;	
	
	
	private PaginaVO resultados = null;

    private boolean banderaBitacoraConsulta = false;
	
	private int totalRegistros = 0;
	
	private EventoCorporativoConsultaDTO eventoCorporativoConsulta;
	
	private String idEventoCorporativo;
	private Date fechaCreacionInicio;
	private Date fechaCreacionFin;
	private Date fechaRegistroInicio;
	private Date fechaRegistroFin;
	private String tipoValor;
	private String emisora;
	private String serie;
	private String isin;
	private String tipoDerechoMO;
	private String tipoDerechoML;
	private String tipoMercado;
	private String estado;
	private Date fechaIndevalInicio;
	private Date fechaIndevalFin;
	private Date fechaClienteInicio;
	private Date fechaClienteFin;
	private Date fechaPagoInicio;
	private Date fechaPagoFin;
	private String custodio;	
	private String tipoEvento;
	
	private Boolean isConsultaIndeval;
	private String idsEstados;
	private boolean consultaExterna;//trae el folio del evento si se guardo bien o se modifico bien
	private Long consultaExternaFolio;
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	/** Pagina para los reportes*/
    private PaginaVO paginaReportes;
    private Pattern patternPagetype = Pattern.compile("/consultaEventosCorporativosIndeval.faces$");
    
    public ConsultaEventosCorporativosController(){
    	    	
    	
    }
	/**
	 * Inicializa el bean
	 * @return
	 */
	public String getInit()
	{
		if(paginaVO == null){
			paginaVO = new PaginaVO();
		}
		this.isConsultaIndeval =patternPagetype.matcher(getRequestURL()).find();
		this.idEventoCorporativo=null;
		this.fechaCreacionInicio=null;
		this.fechaCreacionFin=null;
		this.fechaRegistroInicio=null;
		this.fechaRegistroFin=null;
		this.tipoValor="";
		this.emisora="";
		this.serie="";
		this.isin="";
		this.tipoDerechoMO="-1";
		this.tipoDerechoML="-1";
		this.tipoMercado="-1";
		this.tipoEvento="-1";
		this.estado="-1";
		this.fechaIndevalInicio=null;
		this.fechaIndevalFin=null;
		this.fechaClienteInicio=null;
		this.fechaClienteFin=null;
		this.fechaPagoInicio=null;
		this.fechaPagoFin=null;
		this.custodio="-1";
		
		inicializaCustodios();
		
		//inicializaEstatus();
		inicializaTipoMercado();
		inicializaTiposDerecho();
		inicializaTiposEvento();
		reglasEstado = consultaEventosCorporativosService.getReglasEstado();
		
		paginaVO.setRegistrosXPag(50);
		paginaVO.setOffset(0);
		banderaBitacoraConsulta = false;
		
		
		 this.consultaExternaFolio=(Long)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idEventoGuardado");
		
	    //if("true".equals(facesContext.getExternalContext().getRequestParameterMap().get("return")))
		 if(this.consultaExternaFolio != null)
	    {
	    	this.consultaExternaFolio=(Long)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idEventoGuardado");
	    	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idEventoGuardado");	    	
	    	this.consultaExterna=true;
	    	ejecutarConsulta();
	    	
	    }
	    else
	    {
	    	this.consultaExterna=false;
	    }
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
		this.consultaExterna=false;

	}
	
	/**
	 * Ejecutar cambio de estado
	 * @param evt
	 */
	public void ejecutarCambioEstado(ActionEvent evt)
	{        
		String edos = getIdsEstados();
		Map cambios = formateaCambiosEstado(edos);
		if(cambios != null && cambios.size() > 0){
			setIdsEstados(null);
			getPaginaVO().setRegistros(null);
			Integer mods = consultaEventosCorporativosService.cambiarEstado(cambios,getNombreUsuarioSesion());
			ejecutarConsulta();
		}
		setIdsEstados(null);
	}
	
	private Map<String,String> formateaCambiosEstado(String edos){
		if(StringUtils.isNotEmpty(edos)){
			Map<String,String> estados = new HashMap<String,String>();
			String[] buff = edos.split("\\|");
			for (String linea : buff){
				String[] datos = linea.split(":");
				if(datos.length == 2){
					String idevco= datos[0].substring(4);
					String idedo=datos[1];
					if(StringUtils.isNotEmpty(idedo) && !idedo.equals("-1")){
						estados.put(idevco, idedo);
					}
				}
			}
			return estados; 
		}else{
			return null;
		}		
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
		paginaReportes.setRegistros(null);
		paginaReportes =consultaEventosCorporativosService.getEventosCorporativos(eventoCorporativoConsulta, paginaReportes,!isConsultaIndeval);		
		this.totalRegistros=paginaReportes.getTotalRegistros();
		
	}
	/**
	 * Ejecuta la consulta y calcula el numero de pagina para la paginacion.
	 *  Este metodo es un overide de la clase padre
	 */
	public String ejecutarConsulta(){
		try{
			setParams();
			
			paginaVO = consultaEventosCorporativosService.getEventosCorporativos(eventoCorporativoConsulta, paginaVO,!isConsultaIndeval);         
	        //setea los combos de cambio de estado en un decorador
			this.isConsultaIndeval =patternPagetype.matcher(getRequestURL()).find();
			if(isConsultaIndeval){
	        	setCombosCambioEstado(paginaVO);
	        }
			totalPaginas = paginaVO.getTotalRegistros() / paginaVO.getRegistrosXPag();
			
			if(paginaVO.getTotalRegistros() % paginaVO.getRegistrosXPag() > 0)
				totalPaginas++;		
			totalPaginas = (totalPaginas <= 0) ? 1 : totalPaginas;
			
			setConsultaEjecutada(true);
		}catch(BusinessException be){
			addErrorMessage(be.getMessage());
		}
		return null;
	}
	
	private void setCombosCambioEstado(PaginaVO paginaVO) {
		if(paginaVO.getRegistros() != null && paginaVO.getRegistros().size()>0){
			List<EventoCorporativoDeco> listaEvco = new ArrayList<EventoCorporativoDeco>();
			for(EventoCorporativo evco : (List<EventoCorporativo>)paginaVO.getRegistros()){
				//CreaDecorador
				EventoCorporativoDeco deco = new EventoCorporativoDeco(evco);
				//llena lista de estados
				List<SelectItem> listaedos = new ArrayList<SelectItem>();
				for(ReglaEstado rul: reglasEstado){
					if(deco.getEstado().getIdEstado().equals(rul.getEdoActual().getIdEstado())){
						listaedos.add(new SelectItem(rul.getEdoSiguiente().getIdEstado().toString(),rul.getEdoSiguiente().getEstado()));
					}
				}
				//llena el combo si es necesario
				if(listaedos!= null && listaedos.size()>0){
					deco.setEstados(listaedos);
				}else{
					listaedos=null;
				}
				//agregamos el decorador a la lista
				listaEvco.add(deco);
			}
			//cambiamos la lista de eventos por la de eventos decorados
			paginaVO.setRegistros(listaEvco);
		}
		
	}
	private void setParams() {
		eventoCorporativoConsulta= new EventoCorporativoConsultaDTO();		
		
		if(StringUtils.isNotEmpty(this.idEventoCorporativo)){
			if(StringUtils.isNumeric(this.idEventoCorporativo)){
				eventoCorporativoConsulta.setIdEventoCorporativo(this.idEventoCorporativo);
			}else{
				throw new BusinessException("El folio debe ser numérico");
			}
		}
		eventoCorporativoConsulta.setFechaCreacionInicio(this.fechaCreacionInicio);
		eventoCorporativoConsulta.setFechaCreacionFin(this.fechaCreacionFin);
		eventoCorporativoConsulta.setFechaRegistroInicio(this.fechaRegistroInicio);
		eventoCorporativoConsulta.setFechaRegistroFin(this.fechaRegistroFin);
		
		if(StringUtils.isNotEmpty(this.tipoValor) ){
			eventoCorporativoConsulta.setTipoValor(this.tipoValor.toUpperCase());
		}
		if(StringUtils.isNotEmpty(this.emisora )){
			eventoCorporativoConsulta.setEmisora(this.emisora.toUpperCase());
		}
		if(StringUtils.isNotEmpty(this.serie )){
			eventoCorporativoConsulta.setSerie(this.serie.toUpperCase());
		}
		if(StringUtils.isNotEmpty(this.isin )){
			eventoCorporativoConsulta.setIsin(this.isin.toUpperCase());
		}
		
		if (this.tipoDerechoMO != null && !this.tipoDerechoMO.equals("") && this.tipoDerechoMO.matches("-*[0-9]+")){
			eventoCorporativoConsulta.setTipoDerechoMO(this.tipoDerechoMO);
		}
		if (this.tipoDerechoML != null && !this.tipoDerechoML.equals("") && this.tipoDerechoML.matches("-*[0-9]+")){
			eventoCorporativoConsulta.setTipoDerechoML(this.tipoDerechoML);
		}
		if (this.tipoMercado != null && !this.tipoMercado.equals("") && this.tipoMercado.matches("-*[0-9]+")){
			eventoCorporativoConsulta.setTipoMercado(this.tipoMercado);
		}
		if (this.estado != null && !this.estado.equals("") && this.estado.matches("-*[0-9]+")){
			eventoCorporativoConsulta.setEstado(this.estado);
		}
		if (this.tipoEvento != null && !this.tipoEvento.equals("") && this.tipoEvento.matches("-*[0-9]+")){
			eventoCorporativoConsulta.setTipoEvento(this.tipoEvento);
		}
		eventoCorporativoConsulta.setFechaIndevalInicio(this.fechaIndevalInicio);
		eventoCorporativoConsulta.setFechaIndevalFin(this.fechaIndevalFin);
		eventoCorporativoConsulta.setFechaClienteInicio(this.fechaClienteInicio);
		eventoCorporativoConsulta.setFechaClienteFin(this.fechaClienteFin);
		eventoCorporativoConsulta.setFechaPagoInicio(this.fechaPagoInicio);
		eventoCorporativoConsulta.setFechaPagoFin(this.fechaPagoFin);
		if (this.custodio != null && !this.custodio.equals("") && this.custodio.matches("-*[0-9]+")){
			eventoCorporativoConsulta.setCustodio(this.custodio);
		}		
			//es consulta indeval?	
		this.isConsultaIndeval =patternPagetype.matcher(getRequestURL()).find();
		eventoCorporativoConsulta.setEdicion(this.isConsultaIndeval);		
	}

	/**
	 * Limpia todos los campos
	 * @param evt
	 */
	public void limpiar(ActionEvent evt)
	{
        banderaBitacoraConsulta = false;
         		
        this.idEventoCorporativo=null;
		this.fechaCreacionInicio=null;
		this.fechaCreacionFin=null;
		this.fechaRegistroInicio=null;
		this.fechaRegistroFin=null;
		this.tipoValor="";
		this.emisora="";
		this.serie="";
		this.isin="";
		this.tipoDerechoMO="-1";
		this.tipoDerechoML="-1";
		this.tipoMercado="-1";
		this.tipoEvento="-1";
		this.estado="-1";
		this.fechaIndevalInicio=null;
		this.fechaIndevalFin=null;
		this.fechaClienteInicio=null;
		this.fechaClienteFin=null;
		this.fechaPagoInicio=null;
		this.fechaPagoFin=null;
		this.custodio="-1";
		if(resultados != null)
			resultados.getRegistros().clear();
		if(paginaVO.getRegistros() != null) {
			paginaVO.getRegistros().clear();
		}
		paginaVO.setRegistrosXPag(50);
		paginaVO.setOffset(0);		
		
		setConsultaEjecutada(false);	
		
		this.consultaExterna=false;
	}
	/**
	 * Obtiene la consulta de Estados
	 */
	@SuppressWarnings("unchecked")
	public List<SelectItem> getEstados(){
		if(this.listaEstatus != null && this.listaEstatus.size() > 0) {
			return this.listaEstatus;
		}
		List<Estado> estados = consultaEventosCorporativosService.getEstadosEventoCorporativo();
		this.listaEstatus = new ArrayList<SelectItem>();
    	if(estados != null){
	    	for (Estado estado:estados){	
	    		log.debug("ESTADO EVCO: "+estado.getIdEstado());
	    		this.listaEstatus.add(new SelectItem(estado.getIdEstado().toString(),estado.getEstado()));	    		
	    	}
    	}else{
    		this.listaEstatus.add(new SelectItem("-2","VACIO"));
    	}
    	return this.listaEstatus;
    }
	
	/**
	 * Obtiene la consulta de Estados para el participante
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	public List<SelectItem> getEstadosParticipante(){
		if(this.listaEstatus != null && this.listaEstatus.size() > 0) {
			return this.listaEstatus;
		}
		List<Estado> estados = consultaEventosCorporativosService.getEstadosEventoCorporativo();
		this.listaEstatus = new ArrayList<SelectItem>();
    	if(estados != null){
	    	for (Estado estado:estados){	
	    		//log.debug("ESTADO EVCO: "+estado.getIdEstado());
	    		if(estado.getIdEstado() > this.ESTADO_REGISTRADO ){
	    			this.listaEstatus.add(new SelectItem(estado.getIdEstado().toString(),estado.getEstado()));
	    		}
	    	}
    	}else{
    		this.listaEstatus.add(new SelectItem("-2","VACIO"));
    	}
    	return this.listaEstatus;
    }
		
	/**
	 * 
	 */
	public List<SelectItem> getCustodios(){	
		if(this.listaCustodios != null && this.listaCustodios.size() > 0){
			return this.listaCustodios;
		}
    	List<Custodio> custodios = consultaEventosCorporativosService.getCatalogoCustodios();
    	this.listaCustodios = new ArrayList<SelectItem>();
    	if( custodios != null){
	    	for (Custodio custodio : custodios){ 	    			    			
	    			this.listaCustodios.add(new SelectItem(custodio.getId().toString(), custodio.getDescripcion()));	    		
	    	}	
    	}else{
    		this.listaCustodios.add(new SelectItem("-2","VACIO"));
    	}
    	return this.listaCustodios;
    }
	
	public List<SelectItem> getTiposMercado() {
		if(this.listaTipoMercado != null && this.listaTipoMercado.size() > 0) {
			return this.listaTipoMercado;
		}
		List<TipoMercado> mercados = consultaEventosCorporativosService.getTiposMercado();
    	this.listaTipoMercado = new ArrayList<SelectItem>();
    	if(mercados != null){
	    	for (TipoMercado mercado:mercados){	    		
	    		this.listaTipoMercado.add(new SelectItem(mercado.getIdTipoMercado().toString(),mercado.getDescripcion()));	    		
	    	}
    	}else{
    		this.listaTipoMercado.add(new SelectItem("-2","VACIO"));
    	}
    	
    	return this.listaTipoMercado;
	}
	
	

	public List<SelectItem> getTiposDerechoMO() {
		if(this.listaTipoDerechoMO != null && this.listaTipoDerechoMO.size() > 0) {
			return this.listaTipoDerechoMO;
		}
		List<TipoDerechoEvCo> derechos = consultaEventosCorporativosService.getTiposDerechoMO();
		this.listaTipoDerechoMO = new ArrayList<SelectItem>();
    	if(derechos != null){
	    	for (TipoDerechoEvCo derecho:derechos){	    		
	    		this.listaTipoDerechoMO.add(new SelectItem(derecho.getIdTipoDerecho().toString(),derecho.getTipoDerecho()));	    		
	    	}
    	}else{
    		this.listaTipoDerechoMO.add(new SelectItem("-2","VACIO"));
    	}
    	return this.listaTipoDerechoMO;
	}
	
	public List<SelectItem> getTiposDerechoML() {
		if(this.listaTipoDerechoML != null && this.listaTipoDerechoML.size() > 0) {
			return this.listaTipoDerechoML;
		}
		List<TipoDerechoEvCo> derechos = consultaEventosCorporativosService.getTiposDerechoML();
		this.listaTipoDerechoML = new ArrayList<SelectItem>();
    	if(derechos != null){
	    	for (TipoDerechoEvCo derecho:derechos){	    		
	    		this.listaTipoDerechoML.add(new SelectItem(derecho.getIdTipoDerecho().toString(),derecho.getTipoDerecho()));	    		
	    	}
    	}else{
    		this.listaTipoDerechoML.add(new SelectItem("-2","VACIO"));
    	}
    	return this.listaTipoDerechoML;
	}
	
	public List<SelectItem> getTiposEvento() {
		if(this.listaTipoEvento != null && this.listaTipoEvento.size() > 0) {
			return this.listaTipoEvento;
		}
		    		
		List<TipoEvento> eventos = consultaEventosCorporativosService.getTiposEvento();
		this.listaTipoEvento = new ArrayList<SelectItem>();
    	if(eventos != null){
	    	for (TipoEvento evento:eventos){	    		
	    		this.listaTipoEvento.add(new SelectItem(evento.getIdTipoEvento().toString(),evento.getDescripcion()));	    		
	    	}
    	}else{
    		this.listaTipoEvento.add(new SelectItem("-2","VACIO"));
    	}
    	return this.listaTipoEvento;
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
		getEstados();	
	}
	
	private void inicializaTiposDerecho() {
		getTiposDerechoMO();
		getTiposDerechoML();
		
	}
	
	private void inicializaTipoMercado() {
		listaTipoMercado=getTiposMercado();
		
	}
	
	private void inicializaTiposEvento() {
		listaTipoEvento=getTiposEvento();
		
	}
	
	@Override
	public String getNombreUsuarioSesion() {
   	 String nombreUsuario = null;
   	 FacesContext ctx = FacesContext.getCurrentInstance();
        Object obj = ((HttpSession) ctx.getExternalContext().getSession(false)).getAttribute(SeguridadConstants.USUARIO_SESION);
        if(obj instanceof UsuarioDTO){
       	 UsuarioDTO usuarioDTO = (UsuarioDTO)obj;
       	 if(usuarioDTO != null && usuarioDTO.getClaveUsuario() != null){
       		 nombreUsuario = usuarioDTO.getClaveUsuario();
       	 }
        }
   	return nombreUsuario;
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
	 * @return the listaEstatus
	 */
	public List getListaEstatus() {
		return listaEstatus;
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
	
	
	
	public String getSelectedCustodio(){
		String resultado = getSelected(getCustodio() ,this.listaCustodios);
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
	
	public String getSelectedTipoDerechoMO(){
		String resultado = getSelected(getTipoDerechoMO() ,this.listaTipoDerechoMO);
		if(resultado != null){
			return resultado;
		}
		return "TODOS"; 
	}
	
	public String getSelectedTipoDerechoML(){
		String resultado = getSelected(getTipoDerechoML() ,this.listaTipoDerechoML);
		if(resultado != null){
			return resultado;
		}
		return "TODOS"; 
	}
	
	public String getSelectedMercado(){
		String resultado = getSelected(getTipoMercado() ,this.listaTipoMercado);
		if(resultado != null){
			return resultado;
		}
		return "TODOS"; 
	}
	
	public String getSelectedTipoEvento(){
		String resultado = getSelected(getTipoEvento() ,this.listaTipoEvento);
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
	 * @param consultaEventosCorporativosService the consultaEventosCorporativosService to set
	 */
	public void setConsultaEventosCorporativosService(
			ConsultaEventosCorporativosService consultaEventosCorporativosService) {
		this.consultaEventosCorporativosService = consultaEventosCorporativosService;
	}
	/**
	 * @return the listaTipoMercado
	 */
	public List<SelectItem> getListaTipoMercado() {
		return listaTipoMercado;
	}
	/**
	 * @param listaTipoMercado the listaTipoMercado to set
	 */
	public void setListaTipoMercado(List<SelectItem> listaTipoMercado) {
		this.listaTipoMercado = listaTipoMercado;
	}
	/**
	 * @return the listaTipoDerechoMO
	 */
	public List<SelectItem> getListaTipoDerechoMO() {
		return listaTipoDerechoMO;
	}
	/**
	 * @param listaTipoDerechoMO the listaTipoDerechoMO to set
	 */
	public void setListaTipoDerechoMO(List<SelectItem> listaTipoDerechoMO) {
		this.listaTipoDerechoMO = listaTipoDerechoMO;
	}
	/**
	 * @return the listaTipoDerechoML
	 */
	public List<SelectItem> getListaTipoDerechoML() {
		return listaTipoDerechoML;
	}
	/**
	 * @param listaTipoDerechoML the listaTipoDerechoML to set
	 */
	public void setListaTipoDerechoML(List<SelectItem> listaTipoDerechoML) {
		this.listaTipoDerechoML = listaTipoDerechoML;
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
	 * @return the fechaCreacionInicio
	 */
	public Date getFechaCreacionInicio() {
		return fechaCreacionInicio;
	}
	/**
	 * @param fechaCreacionInicio the fechaCreacionInicio to set
	 */
	public void setFechaCreacionInicio(Date fechaCreacionInicio) {
		this.fechaCreacionInicio = fechaCreacionInicio;
	}
	/**
	 * @return the fechaCreacionFin
	 */
	public Date getFechaCreacionFin() {
		return fechaCreacionFin;
	}
	/**
	 * @param fechaCreacionFin the fechaCreacionFin to set
	 */
	public void setFechaCreacionFin(Date fechaCreacionFin) {
		this.fechaCreacionFin = fechaCreacionFin;
	}
	/**
	 * @return the fechaRegistroInicio
	 */
	public Date getFechaRegistroInicio() {
		return fechaRegistroInicio;
	}
	/**
	 * @param fechaRegistroInicio the fechaRegistroInicio to set
	 */
	public void setFechaRegistroInicio(Date fechaRegistroInicio) {
		this.fechaRegistroInicio = fechaRegistroInicio;
	}
	/**
	 * @return the fechaRegistroFin
	 */
	public Date getFechaRegistroFin() {
		return fechaRegistroFin;
	}
	/**
	 * @param fechaRegistroFin the fechaRegistroFin to set
	 */
	public void setFechaRegistroFin(Date fechaRegistroFin) {
		this.fechaRegistroFin = fechaRegistroFin;
	}
	/**
	 * @return the tipoValor
	 */
	public String getTipoValor() {
		if(tipoValor != null){
			return tipoValor.toUpperCase();
		}else{
			return tipoValor;
		}		
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
		if(emisora != null){
			return emisora.toUpperCase();
		}else{
			return emisora;
		}
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
		if(serie != null){
			return serie.toUpperCase();
		}else{
			return serie;
		}	
		
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
		if(isin != null){
			return isin.toUpperCase();
		}else{
			return isin;
		}			
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
	 * @return the fechaIndevalInicio
	 */
	public Date getFechaIndevalInicio() {
		return fechaIndevalInicio;
	}
	/**
	 * @param fechaIndevalInicio the fechaIndevalInicio to set
	 */
	public void setFechaIndevalInicio(Date fechaIndevalInicio) {
		this.fechaIndevalInicio = fechaIndevalInicio;
	}
	/**
	 * @return the fechaIndevalFin
	 */
	public Date getFechaIndevalFin() {
		return fechaIndevalFin;
	}
	/**
	 * @param fechaIndevalFin the fechaIndevalFin to set
	 */
	public void setFechaIndevalFin(Date fechaIndevalFin) {
		this.fechaIndevalFin = fechaIndevalFin;
	}
	/**
	 * @return the fechaClienteInicio
	 */
	public Date getFechaClienteInicio() {
		return fechaClienteInicio;
	}
	/**
	 * @param fechaClienteInicio the fechaClienteInicio to set
	 */
	public void setFechaClienteInicio(Date fechaClienteInicio) {
		this.fechaClienteInicio = fechaClienteInicio;
	}
	/**
	 * @return the fechaClienteFin
	 */
	public Date getFechaClienteFin() {
		return fechaClienteFin;
	}
	/**
	 * @param fechaClienteFin the fechaClienteFin to set
	 */
	public void setFechaClienteFin(Date fechaClienteFin) {
		this.fechaClienteFin = fechaClienteFin;
	}
	/**
	 * @return the fechaPagoInicio
	 */
	public Date getFechaPagoInicio() {
		return fechaPagoInicio;
	}
	/**
	 * @param fechaPagoInicio the fechaPagoInicio to set
	 */
	public void setFechaPagoInicio(Date fechaPagoInicio) {
		this.fechaPagoInicio = fechaPagoInicio;
	}
	/**
	 * @return the fechaPagoFin
	 */
	public Date getFechaPagoFin() {
		return fechaPagoFin;
	}
	/**
	 * @param fechaPagoFin the fechaPagoFin to set
	 */
	public void setFechaPagoFin(Date fechaPagoFin) {
		this.fechaPagoFin = fechaPagoFin;
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
	 * @return the isConsultaIndeval
	 */
	public Boolean getIsConsultaIndeval() {
		return isConsultaIndeval;
	}
	/**
	 * @param isConsultaIndeval the isConsultaIndeval to set
	 */
	public void setIsConsultaIndeval(Boolean isConsultaIndeval) {
		this.isConsultaIndeval = isConsultaIndeval;
	}
	/**
	 * @param listaCustodios the listaCustodios to set
	 */
	public void setListaCustodios(List<SelectItem> listaCustodios) {
		this.listaCustodios = listaCustodios;
	}
	/**
	 * @param listaEstatus the listaEstatus to set
	 */
	public void setListaEstatus(List<SelectItem> listaEstatus) {
		this.listaEstatus = listaEstatus;
	}
	/**
	 * @return the listaTipoEvento
	 */
	public List<SelectItem> getListaTipoEvento() {
		return listaTipoEvento;
	}
	/**
	 * @param listaTipoEvento the listaTipoEvento to set
	 */
	public void setListaTipoEvento(List<SelectItem> listaTipoEvento) {
		this.listaTipoEvento = listaTipoEvento;
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
	 * @return the idsEstados
	 */
	public String getIdsEstados() {
		return idsEstados;
	}
	/**
	 * @param idsEstados the idsEstados to set
	 */
	public void setIdsEstados(String idsEstados) {
		this.idsEstados = idsEstados;
	}
	
	/**
	 * @return the consultaExternaFolio
	 */
	public Long getConsultaExternaFolio() {
		return consultaExternaFolio;
	}
	/**
	 * @param consultaExternaFolio the consultaExternaFolio to set
	 */
	public void setConsultaExternaFolio(Long consultaExternaFolio) {
		this.consultaExternaFolio = consultaExternaFolio;
	}
	/**
	 * @return the consultaExterna
	 */
	public boolean isConsultaExterna() {
		return consultaExterna;
	}
	/**
	 * @param consultaExterna the consultaExterna to set
	 */
	public void setConsultaExterna(boolean consultaExterna) {
		this.consultaExterna = consultaExterna;
	}
	
	/**
	 * @return the fechaIndevalInicio
	 */
	public String getFechaIndevalInicioF() {
		if( fechaIndevalInicio != null){
			return df.format(fechaIndevalInicio);
		}else{
			return null;
		}
	}
	/**
	 * @return the fechaIndevalFin
	 */
	public String getFechaIndevalFinF() {
		if(fechaIndevalFin != null){
			return df.format(fechaIndevalFin);
		}else{
			return null;
		}
	}
	/**
	 * @return the fechaClienteInicio
	 */
	public String getFechaClienteInicioF() {
		if(fechaClienteInicio != null){
			return df.format(fechaClienteInicio);
		}else{
			return null;
		}
	}
	/**
	 * @return the fechaClienteFin
	 */
	public String getFechaClienteFinF() {
		if(fechaClienteFin != null){
			return df.format(fechaClienteFin);
		}else{
			return null;
		}
	}
	/**
	 * @return the fechaPagoInicio
	 */
	public String getFechaPagoInicioF() {		
		if( fechaPagoInicio != null){
			return df.format(fechaPagoInicio);
		}else{
			return null;
		}
	}
	/**
	 * @return the fechaPagoFin
	 */
	public String getFechaPagoFinF() {
		if(fechaPagoFin != null){
			return df.format(fechaPagoFin);
		}else{
			return null;
		}
	}
	/**
	 * @return the fechaCreacionInicio
	 */
	public String getFechaCreacionInicioF() {
		if( fechaCreacionInicio != null){
			return df.format(fechaCreacionInicio);
		}else{
			return null;
		}
	}
	/**
	 * @return the fechaCreacionFin
	 */
	public String getFechaCreacionFinF() {
		if( fechaCreacionFin != null){
			return df.format(fechaCreacionFin);
		}else{
			return null;
		}
	}
	/**
	 * @return the fechaRegistroInicio
	 */
	public String getFechaRegistroInicioF() {
		if(fechaRegistroInicio != null){
			return df.format(fechaRegistroInicio);
		}else{
			return null;
		}
	}
	/**
	 * @return the fechaRegistroFin
	 */
	public String getFechaRegistroFinF() {
		if( this.fechaRegistroFin != null){
			return df.format(this.fechaRegistroFin);
		}else{
			return null;
		}
	}
}
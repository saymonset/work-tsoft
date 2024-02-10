package com.indeval.portalinternacional.presentation.controller.conciliacionInternacional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Divisa;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ConciliacionEfectivoIntService;
import com.indeval.portalinternacional.middleware.servicios.modelo.CodigoIdentificacionEfectivoInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.ComentarioEfectivoInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.DetalleConciliacionEfectivoInt;
import com.indeval.portalinternacional.middleware.servicios.vo.DetalleConciliacionEfectivoIntDTO;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * @author Fernando Pineda
 *
 */
public class DetalleConciliacionEfectivoIntController extends ControllerBase {
	
	private ConciliacionEfectivoIntService conciliacionEfectivoIntService;
	
	private List<String> listBicCodes;
	private List<String> listDivisas;
	private List<String> listCuentas;
	private List<SelectItem> listCodigos;
	
	
	private Set<String> bicCodeSelected;
	private Set<String> divisaSelected;
	private Set<String> cuentaSelected;
	
	private boolean cuentaComercial;
	private boolean cuentaCustodia;
	private boolean comentarios;
	private boolean creditoDebito;
	
	private String codigoVerificacion;
	private String folioConciliacion;
	private String referenciaMensaje;
	private String tipo;
	
	private Date fechaBalanceInicio;
	private Date fechaBalanceFin;
	
	//entry date
	private Date fechaElaboracionInicio;
	private Date fechaElaboracionFin;
	
	private Date fechaCreditoDebitoInicio;
	private Date fechaCreditoDebitoFin;
	
	private Double saldoInicialNegocio;
	private Double saldoFinalNegocio;
	private Double saldoInicialMensaje;
	private Double saldoFinalMensaje;
	private Double saldoComprobacion;
	
	private boolean totales;
	private double totalCredito;
	private double totalDebito;
	
	private DetalleConciliacionEfectivoIntDTO detalleConciliacionEfectivo;
	private boolean consultaEjecutada;
	private boolean consultaDetalle;
	private int totalPaginas = 1;	
	private PaginaVO resultados = null;	
	private PaginaVO paginaReportes;
	private int totalRegistros = 0;
	private Map mapComentarios;
	
	/**
	 * Inicializa el bean
	 * @return String
	 */
	public String getInit(){
		FacesContext ctx = FacesContext.getCurrentInstance();
		String folioConciliacion = ctx.getExternalContext().getRequestParameterMap().get("folioConciliacion");
		
		if(folioConciliacion != null){
			this.folioConciliacion = folioConciliacion;
			this.consultaDetalle = true;
			buscarDetalleConciliacionesEfectivo(null);
		}
		
		List<String> bicCodes = conciliacionEfectivoIntService.consultaBicCodes();
		listBicCodes = new ArrayList<String>();
		listBicCodes.add("TODOS");
		listBicCodes.addAll(bicCodes);
		bicCodeSelected = new HashSet<String>();
		bicCodeSelected.add("TODOS");
		
		List<Divisa> divisas = conciliacionEfectivoIntService.consultaDivisas(bicCodeSelected);
		listDivisas = new ArrayList<String>();
		listDivisas.add("TODAS");
		
		for(Divisa d : divisas){
			listDivisas.add(d.getClaveAlfabetica());
		}
		
		divisaSelected = new HashSet<String>();
		divisaSelected.add("TODAS");
		
		List<String> cuentas = conciliacionEfectivoIntService.consultaCuentas(bicCodeSelected, divisaSelected);
		listCuentas = new ArrayList<String>();
		listCuentas.add("TODAS");
		listCuentas.addAll(cuentas);
		cuentaSelected = new HashSet<String>();
		cuentaSelected.add("TODAS");
		
		return null;
	}
	
	/**
	 * Busca las conciliaciones de efectivo
	 * @param evt
	 */
	public void buscarDetalleConciliacionesEfectivo(ActionEvent evt){
        paginaVO.setRegistrosXPag(50);
		paginaVO.setOffset(0);
        getPaginaVO().setRegistros(null);
		ejecutarConsulta();
	}
	
	/**
	 * Ejecuta la consulta y calcula el numero de pagina para la paginacion.
	 *  Este metodo es un overide de la clase padre
	 */
	public String ejecutarConsulta(){
		
		setParams();		
		paginaVO = conciliacionEfectivoIntService.consultaDetalleConciliacionEfectivoInt(detalleConciliacionEfectivo, paginaVO);            
        
		totalPaginas = paginaVO.getTotalRegistros() / paginaVO.getRegistrosXPag();
		
		if(paginaVO.getTotalRegistros() % paginaVO.getRegistrosXPag() > 0)
			totalPaginas++;
		
		totalPaginas = (totalPaginas <= 0) ? 1 : totalPaginas;
		
		saldoInicialNegocio = null;
		saldoFinalNegocio = null;
		saldoInicialMensaje = null;
		saldoFinalMensaje = null;
		saldoComprobacion = null;
		
		if(paginaVO.getTotalRegistros() != null && paginaVO.getTotalRegistros().doubleValue() > 0 && detalleConciliacionEfectivo.getDivisas() != null 
				&& detalleConciliacionEfectivo.getDivisas().size() == 1 && !detalleConciliacionEfectivo.getDivisas().contains("TODAS")
				&& detalleConciliacionEfectivo.getFechaBalanceInicio() != null && detalleConciliacionEfectivo.getFechaBalanceFin() != null){
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(detalleConciliacionEfectivo.getFechaBalanceInicio());
			calendar.add(Calendar.DATE, -1);
			
			String divisa = detalleConciliacionEfectivo.getDivisas().iterator().next();
			
			saldoInicialNegocio = calculaSaldoNegocio(detalleConciliacionEfectivo.getBicCodes(), divisa,
					detalleConciliacionEfectivo.getCuentas(), calendar.getTime());
			
			saldoFinalNegocio = calculaSaldoNegocio(detalleConciliacionEfectivo.getBicCodes(), divisa,
					detalleConciliacionEfectivo.getCuentas(), detalleConciliacionEfectivo.getFechaBalanceFin());
			
			BigDecimal saldoIniMensaje =conciliacionEfectivoIntService.consultaSaldoMensaje(detalleConciliacionEfectivo.getBicCodes(), divisa,
					detalleConciliacionEfectivo.getCuentas(), detalleConciliacionEfectivo.getFechaBalanceInicio(), "balanceInicio");
			
			if(saldoIniMensaje != null){
				saldoInicialMensaje = saldoIniMensaje.doubleValue();
			}
			
			BigDecimal saldoFinMensaje =conciliacionEfectivoIntService.consultaSaldoMensaje(detalleConciliacionEfectivo.getBicCodes(), divisa,
					detalleConciliacionEfectivo.getCuentas(), detalleConciliacionEfectivo.getFechaBalanceFin(), "balanceFinal");
			
			if(saldoFinMensaje != null){
				saldoFinalMensaje = saldoFinMensaje.doubleValue();
			}
			
			if(detalleConciliacionEfectivo.getCuentas() != null && detalleConciliacionEfectivo.getCuentas().size() == 1 
					&& !detalleConciliacionEfectivo.getCuentas().contains("TODAS") && detalleConciliacionEfectivo.getBicCodes() != null
					&& detalleConciliacionEfectivo.getBicCodes().size() == 1 && !detalleConciliacionEfectivo.getBicCodes().contains("TODOS")){
				
				if(saldoFinalMensaje != null && saldoFinalNegocio != null){
					saldoComprobacion = saldoFinalMensaje - saldoFinalNegocio;
				}
			}
		}
		
		if(divisaSelected != null && divisaSelected.size() == 1 && !divisaSelected.contains("TODAS")){
			totalCredito = 0;
			totalDebito = 0;
			
			BigDecimal credito = conciliacionEfectivoIntService.obtieneSumaCreditoDebito(detalleConciliacionEfectivo, "credito");
			
			if(credito != null){
				totalCredito = credito.doubleValue();
			}
			
			BigDecimal debito = conciliacionEfectivoIntService.obtieneSumaCreditoDebito(detalleConciliacionEfectivo, "debito");
			
			if(debito != null){
				totalDebito = debito.doubleValue();
			}
			
			totales = true;
		} else {
			totales = false;
		}
		
		if(creditoDebito){
			conciliacionEfectivoIntService.ordenarDetalleConciliacion((List<DetalleConciliacionEfectivoInt>)paginaVO.getRegistros());
		}
		
		setConsultaEjecutada(true);
		
		return null;
	}
	
	private double calculaSaldoNegocio(Set<String> bicCodes, String divisa, Set<String> cuentas, Date fecha){
		double saldoInicial = 0;
		double saldoAcumulado = 0;
		
		BigDecimal saldoIni = conciliacionEfectivoIntService.consultaSaldoInicialCuenta(bicCodes, divisa, cuentas);
		
		if(saldoIni != null){
			saldoInicial = saldoIni.doubleValue();
		}
		
		BigDecimal saldoAcu = conciliacionEfectivoIntService.consultaSaldoAcumuladoHistorico(bicCodes, divisa, cuentas, fecha);
		
		if(saldoAcu != null){
			saldoAcumulado = saldoAcu.doubleValue();
		}
		
		return saldoInicial + saldoAcumulado;
	}
	
	private void setParams() {
		detalleConciliacionEfectivo = new DetalleConciliacionEfectivoIntDTO();
		
		if(this.bicCodeSelected != null && this.bicCodeSelected.size() > 0){
			detalleConciliacionEfectivo.setBicCodes(bicCodeSelected);
		}
		
		if(this.divisaSelected != null && this.divisaSelected.size() > 0){
			detalleConciliacionEfectivo.setDivisas(divisaSelected);
		}
		
		if(this.cuentaSelected != null && this.cuentaSelected.size() > 0){
			detalleConciliacionEfectivo.setCuentas(cuentaSelected);
		}
		
		if (this.codigoVerificacion != null && !this.codigoVerificacion.equals("-1")) {
			detalleConciliacionEfectivo.setCodigoVerificacion(Integer.valueOf(this.codigoVerificacion));
		}
		
		if (this.folioConciliacion != null && !this.folioConciliacion.equals("") && this.folioConciliacion.matches("-*[0-9]+")) {
			detalleConciliacionEfectivo.setFolioConciliacion(Long.valueOf(this.folioConciliacion));
		}
		
		if (this.referenciaMensaje != null && !this.referenciaMensaje.equals("")) {
			detalleConciliacionEfectivo.setReferenciaMensaje(this.referenciaMensaje);
		}
		
		if (this.tipo != null && !this.tipo.equals("-1")) {
			detalleConciliacionEfectivo.setTipo(this.tipo);
		}
		
		detalleConciliacionEfectivo.setCuentaComercial(this.cuentaComercial);
		detalleConciliacionEfectivo.setCuentaCustodia(this.cuentaCustodia);
		detalleConciliacionEfectivo.setComentarios(this.comentarios);
		
		if(this.fechaBalanceInicio != null ) {
			detalleConciliacionEfectivo.setFechaBalanceInicio(this.fechaBalanceInicio);
		}
		
		if(this.fechaBalanceFin != null ) {
			detalleConciliacionEfectivo.setFechaBalanceFin(this.fechaBalanceFin);
		}
		
		if(this.fechaElaboracionInicio != null ) {
			detalleConciliacionEfectivo.setFechaEmisionInicio(this.fechaElaboracionInicio);
		}
		
		if(this.fechaElaboracionFin != null ) {
			detalleConciliacionEfectivo.setFechaEmisionFin(this.fechaElaboracionFin);
		}
		
		if(this.fechaCreditoDebitoInicio != null ) {
			detalleConciliacionEfectivo.setFechaCreditoDebitoInicio(this.fechaCreditoDebitoInicio);
		}
		
		if(this.fechaCreditoDebitoFin != null ) {
			detalleConciliacionEfectivo.setFechaCreditoDebitoFin(this.fechaCreditoDebitoFin);
		}
	}
	
	/**
	 * Limpia todos los campos
	 * @param ActionEvent
	 */
	public void limpiar(ActionEvent evt){
		this.listBicCodes = null;
		this.listDivisas = null;
		this.listCuentas = null;
		this.codigoVerificacion = "-1";
		this.folioConciliacion = "";
		this.referenciaMensaje = "";
		this.tipo = "-1";
		this.creditoDebito = false;
		this.cuentaComercial = false;
		this.cuentaCustodia = false;
		this.comentarios = false;
		this.fechaBalanceInicio = null;
		this.fechaBalanceFin = null;
		this.fechaElaboracionInicio = null;
		this.fechaElaboracionFin = null;
		this.fechaCreditoDebitoInicio = null;
		this.fechaCreditoDebitoFin = null;
		this.saldoInicialNegocio = null;
		this.saldoFinalNegocio = null;
		this.saldoInicialMensaje = null;
		this.saldoFinalMensaje = null;
		this.saldoComprobacion = null;
		
		if(resultados != null){
			resultados.getRegistros().clear();
		}
		
		if(paginaVO.getRegistros() != null) {
			paginaVO.getRegistros().clear();
		}
		paginaVO.setRegistrosXPag(50);
		paginaVO.setOffset(0);
		setConsultaEjecutada(false);
	}
	
	/**
	 * Genera los reportes de consulta de conciliaciones de efectuvo
	 * @param ActionEvent
	 */
	public void generarReportes(ActionEvent event){	
		

		reiniciarEstadoPeticion();

		paginaReportes = new PaginaVO();
		paginaReportes.setOffset(0);
		paginaReportes.setRegistrosXPag(PaginaVO.TODOS);
		paginaReportes = conciliacionEfectivoIntService.consultaDetalleConciliacionEfectivoInt(detalleConciliacionEfectivo, paginaReportes); 
		this.totalRegistros=paginaReportes.getTotalRegistros();
		
		totalCredito = 0;
		totalDebito = 0;
		mapComentarios = new HashMap();
		String comentarios;
		
		if(paginaReportes.getRegistros() != null){ 
			for(DetalleConciliacionEfectivoInt conciliacion : (List<DetalleConciliacionEfectivoInt>)paginaReportes.getRegistros()){
				if(conciliacion.getTipo().equals("C")){
					totalCredito += conciliacion.getMonto().doubleValue();
				} else {
					totalDebito += conciliacion.getMonto().doubleValue();
				}
				
				comentarios = "";
				
				for(ComentarioEfectivoInt comentario : conciliacion.getListaComentarioEfectivo()){
					comentarios += comentario.getComentario() + ", ";
				}
				
				if(!comentarios.equals("")){
					comentarios = comentarios.substring(0, comentarios.length()-2);
				}
				
				mapComentarios.put(conciliacion.getIdDetalleConciliacion(), comentarios);
			}
		}
		
		if(creditoDebito){
			conciliacionEfectivoIntService.ordenarDetalleConciliacion((List<DetalleConciliacionEfectivoInt>)paginaReportes.getRegistros());
		}
	}
	
	public void generarReporteAuditoria(ActionEvent evt){
		boolean reporteGenerado = conciliacionEfectivoIntService.generaReporteAuditoria(detalleConciliacionEfectivo);
		
		if(reporteGenerado){
			addMessage("El reporte de auditoría se guardó correctamente.");
		} else {
			addErrorMessage("Ocurrió un error al guardar el reporte de auditoría.");
		}
	}
	
	/**
	 * Consultas las divisas a partir de un BIC Code seleccionado
	 */
	public void obtieneDivisas(){
		listDivisas.clear();
		listCuentas.clear();
		
		List<Divisa> divisas = conciliacionEfectivoIntService.consultaDivisas(bicCodeSelected);
		
		listDivisas.add("TODAS");
		
		for(Divisa d : divisas){
			listDivisas.add(d.getClaveAlfabetica());
		}
		obtieneCuentas();
	}
	
	/**
	 * Obtiene las cuentas ligadas a una divisa seleccionada
	 */
	public void obtieneCuentas(){
		listCuentas.clear();
		
		List<String> cuentas = conciliacionEfectivoIntService.consultaCuentas(bicCodeSelected, divisaSelected);
		listCuentas.add("TODAS");
		listCuentas.addAll(cuentas);
	}
	
	public List<SelectItem> getCodigos(){	
		if(this.listCodigos != null && this.listCodigos.size() > 0){
			return this.listCodigos;
		}
    	List<CodigoIdentificacionEfectivoInt> codigos = conciliacionEfectivoIntService.consultaCodigosIdentificacion();
    	List <SelectItem> listaCodigos = new ArrayList<SelectItem>();
    	
    	if( codigos != null){
	    	for (CodigoIdentificacionEfectivoInt codigo : codigos){ 	    		
	    		listaCodigos.add(new SelectItem(codigo.getIdCodigoIdentificacion().toString(), codigo.getCodigoIdentificacion()));	    		    		
	    	}
    	}else{
    		listaCodigos.add(new SelectItem("-2","VACIO"));
    	}
    	this.listCodigos=listaCodigos;
    	return listaCodigos;
    }
	
	public String getSelectedCodigo(){
		String resultado = getSelected(getCodigoVerificacion() ,this.listCodigos);
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
	 * @return the conciliacionEfectivoIntService
	 */
	public ConciliacionEfectivoIntService getConciliacionEfectivoIntService() {
		return conciliacionEfectivoIntService;
	}

	/**
	 * @param conciliacionEfectivoIntService the conciliacionEfectivoIntService to set
	 */
	public void setConciliacionEfectivoIntService(
			ConciliacionEfectivoIntService conciliacionEfectivoIntService) {
		this.conciliacionEfectivoIntService = conciliacionEfectivoIntService;
	}

	/**
	 * @return the listBicCodes
	 */
	public List<String> getListBicCodes() {
		return listBicCodes;
	}

	/**
	 * @param listBicCodes the listBicCodes to set
	 */
	public void setListBicCodes(List<String> listBicCodes) {
		this.listBicCodes = listBicCodes;
	}

	/**
	 * @return the listDivisas
	 */
	public List<String> getListDivisas() {
		return listDivisas;
	}

	/**
	 * @param listDivisas the listDivisas to set
	 */
	public void setListDivisas(List<String> listDivisas) {
		this.listDivisas = listDivisas;
	}

	/**
	 * @return the listCuentas
	 */
	public List<String> getListCuentas() {
		return listCuentas;
	}

	/**
	 * @param listCuentas the listCuentas to set
	 */
	public void setListCuentas(List<String> listCuentas) {
		this.listCuentas = listCuentas;
	}

	/**
	 * @return the listCodigos
	 */
	public List<SelectItem> getListCodigos() {
		return listCodigos;
	}

	/**
	 * @param listCodigos the listCodigos to set
	 */
	public void setListCodigos(List<SelectItem> listCodigos) {
		this.listCodigos = listCodigos;
	}

	/**
	 * @return the bicCodeSelected
	 */
	public Set<String> getBicCodeSelected() {
		return bicCodeSelected;
	}

	/**
	 * @param bicSelected the bicSelected to set
	 */
	public void setBicCodeSelected(Set<String> bicCodeSelected) {
		this.bicCodeSelected = bicCodeSelected;
	}

	/**
	 * @return the divisaSelected
	 */
	public Set<String> getDivisaSelected() {
		return divisaSelected;
	}

	/**
	 * @param divisaSelected the divisaSelected to set
	 */
	public void setDivisaSelected(Set<String> divisaSelected) {
		this.divisaSelected = divisaSelected;
	}

	/**
	 * @return the cuentaSelected
	 */
	public Set<String> getCuentaSelected() {
		return cuentaSelected;
	}

	/**
	 * @param cuentaSelected the cuentaSelected to set
	 */
	public void setCuentaSelected(Set<String> cuentaSelected) {
		this.cuentaSelected = cuentaSelected;
	}

	/**
	 * @return the cuentaComercial
	 */
	public boolean isCuentaComercial() {
		return cuentaComercial;
	}

	/**
	 * @param cuentaComercial the cuentaComercial to set
	 */
	public void setCuentaComercial(boolean cuentaComercial) {
		this.cuentaComercial = cuentaComercial;
	}

	/**
	 * @return the cuentaCustodia
	 */
	public boolean isCuentaCustodia() {
		return cuentaCustodia;
	}

	/**
	 * @param cuentaCustodia the cuentaCustodia to set
	 */
	public void setCuentaCustodia(boolean cuentaCustodia) {
		this.cuentaCustodia = cuentaCustodia;
	}

	/**
	 * @return the comentarios
	 */
	public boolean isComentarios() {
		return comentarios;
	}

	/**
	 * @param comentarios the comentarios to set
	 */
	public void setComentarios(boolean comentarios) {
		this.comentarios = comentarios;
	}

	/**
	 * @return the creditoDebito
	 */
	public boolean isCreditoDebito() {
		return creditoDebito;
	}

	/**
	 * @param creditoDebito the creditoDebito to set
	 */
	public void setCreditoDebito(boolean creditoDebito) {
		this.creditoDebito = creditoDebito;
	}

	/**
	 * @return the codigoVerificacion
	 */
	public String getCodigoVerificacion() {
		return codigoVerificacion;
	}

	/**
	 * @param codigoVerificacion the codigoVerificacion to set
	 */
	public void setCodigoVerificacion(String codigoVerificacion) {
		this.codigoVerificacion = codigoVerificacion;
	}

	/**
	 * @return the folioConciliacion
	 */
	public String getFolioConciliacion() {
		return folioConciliacion;
	}

	/**
	 * @param folioConciliacion the folioConciliacion to set
	 */
	public void setFolioConciliacion(String folioConciliacion) {
		this.folioConciliacion = folioConciliacion;
	}

	/**
	 * @return the referenciaMensaje
	 */
	public String getReferenciaMensaje() {
		return referenciaMensaje;
	}

	/**
	 * @param referenciaMensaje the referenciaMensaje to set
	 */
	public void setReferenciaMensaje(String referenciaMensaje) {
		this.referenciaMensaje = referenciaMensaje;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the fechaBalanceInicio
	 */
	public Date getFechaBalanceInicio() {
		return fechaBalanceInicio;
	}

	/**
	 * @param fechaBalanceInicio the fechaBalanceInicio to set
	 */
	public void setFechaBalanceInicio(Date fechaBalanceInicio) {
		this.fechaBalanceInicio = fechaBalanceInicio;
	}

	/**
	 * @return the fechaBalanceFin
	 */
	public Date getFechaBalanceFin() {
		return fechaBalanceFin;
	}

	/**
	 * @param fechaBalanceFin the fechaBalanceFin to set
	 */
	public void setFechaBalanceFin(Date fechaBalanceFin) {
		this.fechaBalanceFin = fechaBalanceFin;
	}

	/**
	 * @return the fechaElaboracionInicio
	 */
	public Date getFechaElaboracionInicio() {
		return fechaElaboracionInicio;
	}

	/**
	 * @param fechaElaboracionInicio the fechaElaboracionInicio to set
	 */
	public void setFechaElaboracionInicio(Date fechaElaboracionInicio) {
		this.fechaElaboracionInicio = fechaElaboracionInicio;
	}

	/**
	 * @return the fechaElaboracionFin
	 */
	public Date getFechaElaboracionFin() {
		return fechaElaboracionFin;
	}

	/**
	 * @param fechaElaboracionFin the fechaElaboracionFin to set
	 */
	public void setFechaElaboracionFin(Date fechaElaboracionFin) {
		this.fechaElaboracionFin = fechaElaboracionFin;
	}

	/**
	 * @return the fechaCreditoDebitoInicio
	 */
	public Date getFechaCreditoDebitoInicio() {
		return fechaCreditoDebitoInicio;
	}

	/**
	 * @param fechaCreditoDebitoInicio the fechaCreditoDebitoInicio to set
	 */
	public void setFechaCreditoDebitoInicio(Date fechaCreditoDebitoInicio) {
		this.fechaCreditoDebitoInicio = fechaCreditoDebitoInicio;
	}

	/**
	 * @return the fechaCreditoDebitoFin
	 */
	public Date getFechaCreditoDebitoFin() {
		return fechaCreditoDebitoFin;
	}

	/**
	 * @param fechaCreditoDebitoFin the fechaCreditoDebitoFin to set
	 */
	public void setFechaCreditoDebitoFin(Date fechaCreditoDebitoFin) {
		this.fechaCreditoDebitoFin = fechaCreditoDebitoFin;
	}

	/**
	 * @return the saldoInicialNegocio
	 */
	public Double getSaldoInicialNegocio() {
		return saldoInicialNegocio;
	}

	/**
	 * @param saldoInicialNegocio the saldoInicialNegocio to set
	 */
	public void setSaldoInicialNegocio(Double saldoInicialNegocio) {
		this.saldoInicialNegocio = saldoInicialNegocio;
	}

	/**
	 * @return the saldoFinalNegocio
	 */
	public Double getSaldoFinalNegocio() {
		return saldoFinalNegocio;
	}

	/**
	 * @param saldoFinalNegocio the saldoFinalNegocio to set
	 */
	public void setSaldoFinalNegocio(Double saldoFinalNegocio) {
		this.saldoFinalNegocio = saldoFinalNegocio;
	}

	/**
	 * @return the saldoInicialMensaje
	 */
	public Double getSaldoInicialMensaje() {
		return saldoInicialMensaje;
	}

	/**
	 * @param saldoInicialMensaje the saldoInicialMensaje to set
	 */
	public void setSaldoInicialMensaje(Double saldoInicialMensaje) {
		this.saldoInicialMensaje = saldoInicialMensaje;
	}

	/**
	 * @return the saldoFinalMensaje
	 */
	public Double getSaldoFinalMensaje() {
		return saldoFinalMensaje;
	}

	/**
	 * @param saldoFinalMensaje the saldoFinalMensaje to set
	 */
	public void setSaldoFinalMensaje(Double saldoFinalMensaje) {
		this.saldoFinalMensaje = saldoFinalMensaje;
	}

	/**
	 * @return the saldoComprobacion
	 */
	public Double getSaldoComprobacion() {
		return saldoComprobacion;
	}

	/**
	 * @param saldoComprobacion the saldoComprobacion to set
	 */
	public void setSaldoComprobacion(Double saldoComprobacion) {
		this.saldoComprobacion = saldoComprobacion;
	}

	/**
	 * @return the totales
	 */
	public boolean isTotales() {
		return totales;
	}

	/**
	 * @param totales the totales to set
	 */
	public void setTotales(boolean totales) {
		this.totales = totales;
	}

	/**
	 * @return the totalCredito
	 */
	public double getTotalCredito() {
		return totalCredito;
	}

	/**
	 * @param totalCredito the totalCredito to set
	 */
	public void setTotalCredito(double totalCredito) {
		this.totalCredito = totalCredito;
	}

	/**
	 * @return the totalDebito
	 */
	public double getTotalDebito() {
		return totalDebito;
	}

	/**
	 * @param totalDebito the totalDebito to set
	 */
	public void setTotalDebito(double totalDebito) {
		this.totalDebito = totalDebito;
	}

	/**
	 * @return the detalleConciliacionEfectivo
	 */
	public DetalleConciliacionEfectivoIntDTO getDetalleConciliacionEfectivo() {
		return detalleConciliacionEfectivo;
	}

	/**
	 * @param detalleConciliacionEfectivo the detalleConciliacionEfectivo to set
	 */
	public void setDetalleConciliacionEfectivo(
			DetalleConciliacionEfectivoIntDTO detalleConciliacionEfectivo) {
		this.detalleConciliacionEfectivo = detalleConciliacionEfectivo;
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
	 * @return the consultaDetalle
	 */
	public boolean isConsultaDetalle() {
		return consultaDetalle;
	}

	/**
	 * @param consultaDetalle the consultaDetalle to set
	 */
	public void setConsultaDetalle(boolean consultaDetalle) {
		this.consultaDetalle = consultaDetalle;
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
	 * @return the mapComentarios
	 */
	public Map getMapComentarios() {
		return mapComentarios;
	}

	/**
	 * @param mapComentarios the mapComentarios to set
	 */
	public void setMapComentarios(Map mapComentarios) {
		this.mapComentarios = mapComentarios;
	}

}

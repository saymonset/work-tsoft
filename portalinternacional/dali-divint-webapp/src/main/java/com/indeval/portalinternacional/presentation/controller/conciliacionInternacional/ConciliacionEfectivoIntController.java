package com.indeval.portalinternacional.presentation.controller.conciliacionInternacional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.event.ActionEvent;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Divisa;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ConciliacionEfectivoIntService;
import com.indeval.portalinternacional.middleware.servicios.modelo.ConciliacionEfectivoInt;
import com.indeval.portalinternacional.middleware.servicios.vo.ConciliacionEfectivoIntDTO;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * @author Fernando Pineda
 *
 */
public class ConciliacionEfectivoIntController extends ControllerBase {
	
	private ConciliacionEfectivoIntService conciliacionEfectivoIntService;
	
	private List<String> listBicCodes;
	private List<String> listDivisas;
	private List<String> listCuentas;
	
	private Set<String> bicCodeSelected;
	private Set<String> divisaSelected;
	private Set<String> cuentaSelected;
	
	private boolean cuentaComercial;
	private boolean cuentaCustodia;
	private boolean diferencia;
	private String folioConciliacion;
	private String folioMensaje;
	private String referenciaMT;
	private Date fechaBalanceInicio;
	private Date fechaBalanceFin;
	private Date fechaElaboracionInicio;
	private Date fechaElaboracionFin;
	
	private ConciliacionEfectivoIntDTO conciliacionEfectivo;
	private boolean consultaEjecutada;
	private int totalPaginas = 1;	
	private PaginaVO resultados = null;	
	private PaginaVO paginaReportes;
	private int totalRegistros = 0;
	private boolean totales;
	private double totalSaldoInicial;
	private double totalSaldoFinal;
	private double totalNetoMovimientos;
	private double totalSaldoBoveda;
	private double totalDiferencia;
	private double totalSaldoComprobacion;
	
	/**
	 * Inicializa el bean
	 * @return String
	 */
	public String getInit(){
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
	public void buscarConciliacionesEfectivo(ActionEvent evt){
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
		paginaVO = conciliacionEfectivoIntService.consultaConciliacionEfectivoInt(conciliacionEfectivo, paginaVO);            
        
		totalPaginas = paginaVO.getTotalRegistros() / paginaVO.getRegistrosXPag();
		
		if(paginaVO.getTotalRegistros() % paginaVO.getRegistrosXPag() > 0)
			totalPaginas++;
		
		totalPaginas = (totalPaginas <= 0) ? 1 : totalPaginas;
		
		if(divisaSelected != null && divisaSelected.size() == 1 && !divisaSelected.contains("TODAS")){
			obtieneTotales();
			totales = true;
			
		} else {
			totales = false;
		}
		
		setConsultaEjecutada(true);
		return null;
	}
	
	private void setParams() {
		conciliacionEfectivo = new ConciliacionEfectivoIntDTO();
		
		if(bicCodeSelected != null && bicCodeSelected.size() > 0){
			conciliacionEfectivo.setBicCodes(bicCodeSelected);
		}
		
		if(divisaSelected != null && divisaSelected.size() > 0){
			conciliacionEfectivo.setDivisas(divisaSelected);
		}
		
		if(cuentaSelected != null && cuentaSelected.size() > 0){
			conciliacionEfectivo.setCuentas(cuentaSelected);
		}
		
		conciliacionEfectivo.setCuentaComercial(cuentaComercial);
		conciliacionEfectivo.setCuentaCustodia(cuentaCustodia);
		conciliacionEfectivo.setDiferencia(diferencia);
		
		if (this.folioConciliacion != null && !this.folioConciliacion.equals("") && this.folioConciliacion.matches("[0-9]+")) {
			conciliacionEfectivo.setFolioConciliacion(Long.valueOf(this.folioConciliacion));
		}
		
		if (this.folioMensaje != null && !this.folioMensaje.equals("") && this.folioMensaje.matches("[0-9]+")) {
			conciliacionEfectivo.setStatementNumber(Integer.valueOf(this.folioMensaje));
		}
		
		if (this.referenciaMT != null && !this.referenciaMT.equals("")) {
			conciliacionEfectivo.setReferenciaMT(this.referenciaMT);
		}
		
		if(this.fechaBalanceInicio != null ) {
			conciliacionEfectivo.setFechaBalanceInicio(this.fechaBalanceInicio);
		}
		
		if(this.fechaBalanceFin != null ) {
			conciliacionEfectivo.setFechaBalanceFin(this.fechaBalanceFin);
		}
		
		if(this.fechaElaboracionInicio != null ) {
			conciliacionEfectivo.setFechaEmisionInicio(this.fechaElaboracionInicio);
		}
		
		if(this.fechaElaboracionFin != null ) {
			conciliacionEfectivo.setFechaEmisionFin(this.fechaElaboracionFin);
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
		this.cuentaComercial = false;
		this.cuentaCustodia = false;
		this.diferencia = false;
		this.folioConciliacion = "";
		this.folioMensaje = "";
		this.referenciaMT = "";
		this.fechaBalanceInicio = null;
		this.fechaBalanceFin = null;
		this.fechaElaboracionInicio = null;
		this.fechaElaboracionFin = null;
		
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
		paginaReportes = conciliacionEfectivoIntService.consultaConciliacionEfectivoInt(conciliacionEfectivo, paginaReportes); 
		this.totalRegistros=paginaReportes.getTotalRegistros();
		
		if(divisaSelected != null && divisaSelected.size() == 1 && !divisaSelected.contains("TODAS")){
			obtieneTotales();
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
	
	private void obtieneTotales(){
		totalSaldoInicial = 0;
		totalSaldoFinal = 0;
		totalNetoMovimientos = 0;
		totalSaldoBoveda = 0;
		totalDiferencia = 0;
		totalSaldoComprobacion = 0;
		
		Object[] saldos = conciliacionEfectivoIntService.obtieneTotalesConciliacion(conciliacionEfectivo, "totales");
		
		if(saldos != null){
			if(saldos[0] != null){
				totalSaldoInicial = ((BigDecimal)saldos[0]).doubleValue();
			}
			if(saldos[1] != null){
				totalSaldoFinal = ((BigDecimal)saldos[1]).doubleValue();
			}
			if(saldos[2] != null){
				totalNetoMovimientos = ((BigDecimal)saldos[2]).doubleValue();
			}
			if(saldos[3] != null){
				totalSaldoBoveda = ((BigDecimal)saldos[3]).doubleValue();
			}
			
			if(saldos[4] != null){
				totalDiferencia = ((BigDecimal)saldos[4]).doubleValue();
			}
			
			if(saldos[5] != null){
				totalSaldoComprobacion = ((BigDecimal)saldos[5]).doubleValue();
			}
		}
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
	 * @return the diferencia
	 */
	public boolean isDiferencia() {
		return diferencia;
	}

	/**
	 * @param diferencia the diferencia to set
	 */
	public void setDiferencia(boolean diferencia) {
		this.diferencia = diferencia;
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
	 * @return the folioMensaje
	 */
	public String getFolioMensaje() {
		return folioMensaje;
	}

	/**
	 * @param folioMensaje the folioMensaje to set
	 */
	public void setFolioMensaje(String folioMensaje) {
		this.folioMensaje = folioMensaje;
	}

	/**
	 * @return the referenciaMT
	 */
	public String getReferenciaMT() {
		return referenciaMT;
	}

	/**
	 * @param referenciaMT the referenciaMT to set
	 */
	public void setReferenciaMT(String referenciaMT) {
		this.referenciaMT = referenciaMT;
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
	 * @return the conciliacionEfectivo
	 */
	public ConciliacionEfectivoIntDTO getConciliacionEfectivo() {
		return conciliacionEfectivo;
	}

	/**
	 * @param conciliacionEfectivo the conciliacionEfectivo to set
	 */
	public void setConciliacionEfectivo(
			ConciliacionEfectivoIntDTO conciliacionEfectivo) {
		this.conciliacionEfectivo = conciliacionEfectivo;
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
	 * @return the totalSaldoInicial
	 */
	public double getTotalSaldoInicial() {
		return totalSaldoInicial;
	}

	/**
	 * @param totalSaldoInicial the totalSaldoInicial to set
	 */
	public void setTotalSaldoInicial(double totalSaldoInicial) {
		this.totalSaldoInicial = totalSaldoInicial;
	}

	/**
	 * @return the totalSaldoFinal
	 */
	public double getTotalSaldoFinal() {
		return totalSaldoFinal;
	}

	/**
	 * @param totalSaldoFinal the totalSaldoFinal to set
	 */
	public void setTotalSaldoFinal(double totalSaldoFinal) {
		this.totalSaldoFinal = totalSaldoFinal;
	}

	/**
	 * @return the totalNetoMovimientos
	 */
	public double getTotalNetoMovimientos() {
		return totalNetoMovimientos;
	}

	/**
	 * @param totalNetoMovimientos the totalNetoMovimientos to set
	 */
	public void setTotalNetoMovimientos(double totalNetoMovimientos) {
		this.totalNetoMovimientos = totalNetoMovimientos;
	}

	/**
	 * @return the totalSaldoBoveda
	 */
	public double getTotalSaldoBoveda() {
		return totalSaldoBoveda;
	}

	/**
	 * @param totalSaldoBoveda the totalSaldoBoveda to set
	 */
	public void setTotalSaldoBoveda(double totalSaldoBoveda) {
		this.totalSaldoBoveda = totalSaldoBoveda;
	}

	/**
	 * @return the totalDiferencia
	 */
	public double getTotalDiferencia() {
		return totalDiferencia;
	}

	/**
	 * @param totalDiferencia the totalDiferencia to set
	 */
	public void setTotalDiferencia(double totalDiferencia) {
		this.totalDiferencia = totalDiferencia;
	}

	/**
	 * @return the totalSaldoComprobacion
	 */
	public double getTotalSaldoComprobacion() {
		return totalSaldoComprobacion;
	}

	/**
	 * @param totalSaldoComprobacion the totalSaldoComprobacion to set
	 */
	public void setTotalSaldoComprobacion(double totalSaldoComprobacion) {
		this.totalSaldoComprobacion = totalSaldoComprobacion;
	}

}

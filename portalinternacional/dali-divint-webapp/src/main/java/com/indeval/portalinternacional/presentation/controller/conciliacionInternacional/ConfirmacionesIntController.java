/**
 * 
 */
package com.indeval.portalinternacional.presentation.controller.conciliacionInternacional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Divisa;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ConfirmacionIntService;
import com.indeval.portalinternacional.middleware.servicios.modelo.ConfirmacionEfectivo;
import com.indeval.portalinternacional.middleware.servicios.vo.ConfirmacionIntDTO;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * @author César Hernández
 *
 */
public class ConfirmacionesIntController extends ControllerBase {

	private static final Logger logger = LoggerFactory.getLogger(ConfirmacionesIntController.class);
		
	/** Servicio de confirmaciones */
	private ConfirmacionIntService confirmacionIntService;
	
	/** Lista para llenar componentes de seleccion */
	private List<String> listBic;
	private List<Divisa> listDivisas;
	private List<String> divisas;
	private List<String> listCuentas;
	private List<String> listTipoMensaje;
	
	/** Set's donde se mantiene los items seleccionados */
	private Set<String> bicSelected;
	private Set<String> divisaSelected;
	private Set<String> cuentaSelected;
	private Set<String> tipoMensajeSelected;
	
	private List<String> listBicSelected;
	private List<String> listDivisaSelected;
	private List<String> listCuentaSelected;
	private List<String> listTipoMensajeSelected;
	
	private String referencia;
	private String mtEspecifico;
	private String relatedReference;
	private String folio;
	private String isin;
	private Date fechaRecepcionMensajeInicio;
	private Date fechaRecepcionMensajeFin;
	private Date fechaCreditoDebitoInicio;
	private Date fechaCreditoDebitoFin;
	private Date fechaRecepcionDaliInicio;
	private Date fechaRecepcionDaliFin;
	private boolean cuentasCustodia;
	private boolean cuentasComercial;
	private boolean creditoDebito;
	private boolean totales;
	private double totalCreditos;
	private double totalDebitos;
	private double totalCreditosPaginado;
	private double totalDebitosPaginado;
	private int totalPaginas;
	private int tipoNotificacion;
	
	private boolean consultaEjecutada;
	
	private PaginaVO paginaReportes;
	
	private ConfirmacionIntDTO confirmacionIntDTO;
	
	private int totalRegistros = 0;
	
	/** Pantalla Agrega Actualiza ComentarioDali */
	private String comentarioDali;
	
	private Long idConfirmacion;
	
	/** Pantalla de Agrega / Actualiza Id Folio */
	private String idFolio;
	
	private boolean initPopup = true;
	
	/**
	 * Contructor por default
	 */
	public ConfirmacionesIntController(){
		
	}
	
	/**
	 * Consultas las divisas a partir de un BIC Code seleccionado
	 */
	public void obtieneDivisas(){
		if(listCuentas == null)
			listCuentas = new ArrayList<String>();
		else
			listCuentas.clear();
		
		listBicSelected.clear();
		
		listCuentaSelected.clear();
		listCuentaSelected.add("TODAS");
		listCuentas.add("TODAS");
		
		Iterator<String> iter = bicSelected.iterator();
		while(iter.hasNext()) {
			listBicSelected.add(iter.next());
		}
		
		listDivisas = confirmacionIntService.consultaDivisas(bicSelected);
		divisas = new ArrayList<String>();
		divisas.add("TODAS");
		for(Divisa d : listDivisas){
			divisas.add(d.getClaveAlfabetica());
		}
		
		divisaSelected.clear();
		divisaSelected.add("TODAS");
		
		listCuentas.clear();
		listCuentas.add("TODAS");
		listCuentas.addAll(confirmacionIntService.consultaCuentas(bicSelected, divisaSelected));
	}
	
	/**
	 * Obtiene las cuentas ligadas a una divisa seleccionada
	 */
	public void obtieneCuentas(){
		listDivisaSelected.clear();
		
		Iterator<String> iter = divisaSelected.iterator();
		while(iter.hasNext()) {
			String claveDivisa = iter.next(); 
			for(Divisa d : listDivisas){
				if(claveDivisa.equals(d.getClaveAlfabetica())){
					listDivisaSelected.add(d.getClaveAlfabetica());
					break;
				}
			}
		}
		
		listCuentas.clear();
		listCuentas.add("TODAS");
		listCuentas.addAll(confirmacionIntService.consultaCuentas(bicSelected, divisaSelected));
	}
	
	/**
	 * Init
	 * @return
	 */
	public String getInit(){
		listBic = new ArrayList<String>();
		listBic.add("TODOS");
		listBic.addAll(confirmacionIntService.consultaBicCodes());
		
		bicSelected = new HashSet<String>();
		bicSelected.add("TODOS");
		
		listBicSelected = new ArrayList<String>();
		listBicSelected.add("TODOS");
		
		listDivisas = confirmacionIntService.consultaDivisas(bicSelected);
		divisas = new ArrayList<String>();
		divisas.add("TODAS");
		
		listDivisaSelected = new ArrayList<String>();
		listDivisaSelected.add("TODAS");
		
		for(Divisa d : listDivisas){
			divisas.add(d.getClaveAlfabetica());
		}
		
		divisaSelected = new HashSet<String>();
		divisaSelected.add("TODAS");
		
		List<String> cuentas = confirmacionIntService.consultaCuentas(bicSelected, divisaSelected);
		listCuentas = new ArrayList<String>();
		listCuentas.add("TODAS");
		listCuentas.addAll(cuentas);
		cuentaSelected = new HashSet<String>();
		cuentaSelected.add("TODAS");
		
		listCuentaSelected = new ArrayList<String>();
		listCuentaSelected.add("TODAS");
		
		llenaListaTiposMensaje();
		consultaEjecutada = false;
		
		listTipoMensajeSelected = new ArrayList<String>();
		listTipoMensajeSelected.add("TODOS");
		
		if(paginaVO == null)
			paginaVO = new PaginaVO();
		
		paginaVO.setRegistrosXPag(50);
		paginaVO.setOffset(0);
		
		totalCreditosPaginado = 0;
		totalDebitosPaginado = 0;
		
		cuentasCustodia = false;
		cuentasComercial = false;
		creditoDebito = false;
		
		initPopup = true;
		
		tipoMensajeSelected = new HashSet<String>();
		tipoMensajeSelected.add("TODOS");
		
		return null;
	}
	
	private void llenaListaTiposMensaje(){
		listTipoMensaje = new ArrayList<String>();
		listTipoMensaje.add("TODOS");
		listTipoMensaje.add("900");
		listTipoMensaje.add("910");
		listTipoMensaje.add("X90");
		listTipoMensaje.add("X99");
		/*listTipoMensaje.add("190");
		listTipoMensaje.add("290");
		listTipoMensaje.add("390");	
		listTipoMensaje.add("490");
		listTipoMensaje.add("590");
		listTipoMensaje.add("690");
		listTipoMensaje.add("790");
		listTipoMensaje.add("890");
		listTipoMensaje.add("990");
		listTipoMensaje.add("199");
		listTipoMensaje.add("299");
		listTipoMensaje.add("399");
		listTipoMensaje.add("499");
		listTipoMensaje.add("599");
		listTipoMensaje.add("699");
		listTipoMensaje.add("799");
		listTipoMensaje.add("899");
		listTipoMensaje.add("999");*/
	}

	/**
	 * Buscar las confirmaciones
	 * @param evt
	 */
	public void buscarConfirmaciones(ActionEvent evt){
		paginaVO.setOffset(0);
		
		ejecutarConsulta();
		
        consultaEjecutada = true;
	}
	
	public String ejecutarConsulta(){
		totalCreditos = 0.0f;
		totalDebitos = 0.0f;
		
		if(cuentaSelected != null){
			listCuentaSelected.clear();
			
			Iterator<String> iter = cuentaSelected.iterator();
			while(iter.hasNext()) {
				listCuentaSelected.add(iter.next());
			}
		}
		
		listTipoMensajeSelected.clear();
		
		Iterator<String> iter = tipoMensajeSelected.iterator();
		while(iter.hasNext()) {
			listTipoMensajeSelected.add(iter.next());
		}
		
		armaDTO();
		
		paginaVO = confirmacionIntService.consultaConfirmaciones(confirmacionIntDTO, paginaVO);
		
		totalPaginas = paginaVO.getTotalRegistros() / paginaVO.getRegistrosXPag();
		
		if(paginaVO.getTotalRegistros() % paginaVO.getRegistrosXPag() > 0)
			totalPaginas++;		
		totalPaginas = (totalPaginas <= 0) ? 1 : totalPaginas;
		
		if(paginaVO.getTotalRegistros() > 0){
			if(confirmacionIntDTO.getTipoMensajes().contains("910") || confirmacionIntDTO.getTipoMensajes().contains("TODOS") &&
			   confirmacionIntDTO.getTipoNotificacion() != ConfirmacionIntDTO.TIPO_NOTIFICACION_DEBITO){
				totalCreditos = confirmacionIntService.obtieneSumaTotalCreditos(confirmacionIntDTO);
			}
			
			if(calculaDebitos(confirmacionIntDTO) || confirmacionIntDTO.getTipoMensajes().contains("TODOS") && 
			   confirmacionIntDTO.getTipoNotificacion() != ConfirmacionIntDTO.TIPO_NOTIFICACION_CREDITO){
				totalDebitos = confirmacionIntService.obtieneSumaTotalDebitos(confirmacionIntDTO);
			}
		}
		
		if(divisaSelected != null && divisaSelected.size() == 1 && !divisaSelected.contains("TODAS")){
			totalCreditosPaginado = totalCreditos;
			totalDebitosPaginado = totalDebitos;
			totales = true;
		} else {
			totales = false;
		}
		
		if(creditoDebito){
			confirmacionIntService.ordernarConfirmaciones((List<ConfirmacionEfectivo>)paginaVO.getRegistros());
		}
		
		initPopup = true;
		
		return null;
	}
	
	private boolean calculaDebitos(ConfirmacionIntDTO dto){
		for(String tm : dto.getTipoMensajes()){
			if(tm.equals("900")){
				return true;
			}
			
			if(tm.length() == 3 && tm.substring(1,3).equals("90")){
				return true;
			}
		}
		
		return false;
	}
	
	private void armaDTO(){
		if(confirmacionIntDTO == null){
			confirmacionIntDTO = new ConfirmacionIntDTO();
		}
		
		if(mtEspecifico != null && !mtEspecifico.equals("")){
			listTipoMensajeSelected.clear();
			listTipoMensajeSelected.add(mtEspecifico);
		}
		
		confirmacionIntDTO.setBicCodes(listBicSelected);
		confirmacionIntDTO.setDivisas(listDivisaSelected);
		confirmacionIntDTO.setCuentas(listCuentaSelected);
		confirmacionIntDTO.setTipoMensajes(listTipoMensajeSelected);
		confirmacionIntDTO.setReferenciaMensaje(referencia);
		confirmacionIntDTO.setFechaRecepcionMensajeInicio(fechaRecepcionMensajeInicio);
		confirmacionIntDTO.setFechaRecepcionMensajeFin(fechaRecepcionMensajeFin);
		confirmacionIntDTO.setFechaCreditoDebitoInicio(fechaCreditoDebitoInicio);
		confirmacionIntDTO.setFechaCreditoDebitoFin(fechaCreditoDebitoFin);
		confirmacionIntDTO.setCuentasCustodia(cuentasCustodia);
		confirmacionIntDTO.setCuentasComercial(cuentasComercial);
		confirmacionIntDTO.setTipoNotificacion(tipoNotificacion);
		confirmacionIntDTO.setFechaRecepcionDaliInicio(fechaRecepcionDaliInicio);
		confirmacionIntDTO.setFechaRecepcionDaliFin(fechaRecepcionDaliFin);
		confirmacionIntDTO.setRelatedReference(relatedReference);
		confirmacionIntDTO.setIdFolio(folio);
		confirmacionIntDTO.setIsin(isin);
	}
	
	/**
	 * Genera los reportes de Consulta de Conciliaciones
	 * @param evt
	 */
	public void generarReportes(ActionEvent evt)
	{	
		armaDTO();
		
		paginaReportes = new PaginaVO();
		paginaReportes.setOffset(0);
		paginaReportes.setRegistrosXPag(PaginaVO.TODOS);		
		paginaReportes = confirmacionIntService.consultaConfirmaciones(confirmacionIntDTO, paginaReportes);		
		this.totalRegistros = paginaReportes.getTotalRegistros();
	}
	
	/**
	 * Limpia todos los campos
	 * @param evt
	 */
	public void limpiar(ActionEvent evt)
	{
		if(paginaVO.getRegistros() != null) {
			paginaVO.getRegistros().clear();
		}
		
		paginaVO.setRegistrosXPag(50);
		paginaVO.setOffset(0);		
		
		referencia = "";
		mtEspecifico = "";
		relatedReference = "";
		folio = "";
		isin = "";
		fechaRecepcionMensajeInicio = null;
		fechaRecepcionMensajeFin = null;
		fechaCreditoDebitoInicio = null;
		fechaCreditoDebitoFin = null;
		fechaRecepcionDaliInicio = null;
		fechaRecepcionDaliFin = null;
		cuentasCustodia = false;
		cuentasComercial = false;
		creditoDebito = false;
		totalCreditos = 0;
		totalDebitos = 0;
		totalPaginas = 0;
		tipoNotificacion = ConfirmacionIntDTO.TIPO_NOTIFICACION_TODAS;
		
		bicSelected.clear();
		divisaSelected.clear();
		cuentaSelected.clear();
		tipoMensajeSelected.clear();
		
		listDivisas.clear();
		divisas.clear();
		listCuentas.clear();
		
		listBicSelected.clear();
		listDivisaSelected.clear();
		listCuentaSelected.clear();
		listTipoMensajeSelected.clear();
		
		listBicSelected.add("TODOS");
		listDivisaSelected.add("TODAS");
		listCuentaSelected.add("TODAS");
		listTipoMensajeSelected.add("TODOS");
		
		totalCreditosPaginado = 0;
		totalDebitosPaginado = 0;
		
		setConsultaEjecutada(false);
	}
	
	/**
	 * Inicia el popup para actualiza comentarios Dali
	 * @return
	 */
	public String getInitComentariosDali() {
		if(initPopup)
		{
			initPopup = false;
			try{
				FacesContext facesContext = FacesContext.getCurrentInstance();
				idConfirmacion = Long.valueOf(facesContext.getExternalContext().getRequestParameterMap().get("idConfirmacion"));
				comentarioDali = facesContext.getExternalContext().getRequestParameterMap().get("comentariosDali");
			}catch (Exception ex){
				addErrorMessage("Error al recibir los parámetros");
			}
		}
		
		return null;
	}
	
	/**
	 * Guarda los comentario Dali
	 * @param evt
	 */
	public void guardarComentariosDali(ActionEvent evt)
	{
		if(idConfirmacion != null && comentarioDali != null){
			confirmacionIntService.agregaActualizaComentariosDali(idConfirmacion, comentarioDali);
			addMessage("Los comentarios Dali se guardaron satisfactoriamente");
		}
	}
	
	/**
	 * Inicia el popup para actualiza comentarios Dali
	 * @return
	 */
	public String getInitIdFolio() {
		if(initPopup)
		{
			initPopup = false;
			try{
				FacesContext facesContext = FacesContext.getCurrentInstance();
				idConfirmacion = Long.valueOf(facesContext.getExternalContext().getRequestParameterMap().get("idConfirmacion"));
				idFolio = facesContext.getExternalContext().getRequestParameterMap().get("idFolio");
			}catch (Exception ex){
				addErrorMessage("Error al recibir los parámetros");
			}
		}
		
		return null;
	}
	
	/**
	 * Guarda el ID Folio
	 * @param evt
	 */
	public void guardarIdFolio(ActionEvent evt)
	{
		if(idConfirmacion != null && idFolio != null){
			confirmacionIntService.agregaActualizaIdFolio(idConfirmacion, idFolio);
			addMessage("El Id Folio se guardo satisfactoriamente");
		}
	}
	
	/**
	 * @return the confirmacionIntService
	 */
	public ConfirmacionIntService getConfirmacionIntService() {
		return confirmacionIntService;
	}

	/**
	 * @param confirmacionIntService the confirmacionIntService to set
	 */
	public void setConfirmacionIntService(
			ConfirmacionIntService confirmacionIntService) {
		this.confirmacionIntService = confirmacionIntService;
	}

	/**
	 * @return the listBic
	 */
	public List<String> getListBic() {
		return listBic;
	}

	/**
	 * @param listBic the listBic to set
	 */
	public void setListBic(List<String> listBic) {
		this.listBic = listBic;
	}

	/**
	 * @return the listDivisas
	 */
	public List<Divisa> getListDivisas() {
		return listDivisas;
	}

	/**
	 * @param listDivisas the listDivisas to set
	 */
	public void setListDivisas(List<Divisa> listDivisas) {
		this.listDivisas = listDivisas;
	}

	/**
	 * @return the bicSelected
	 */
	public Set<String> getBicSelected() {
		return bicSelected;
	}

	/**
	 * @param bicSelected the bicSelected to set
	 */
	public void setBicSelected(Set<String> bicSelected) {
		this.bicSelected = bicSelected;
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
	 * @return the divisas
	 */
	public List<String> getDivisas() {
		return divisas;
	}

	/**
	 * @param divisas the divisas to set
	 */
	public void setDivisas(List<String> divisas) {
		this.divisas = divisas;
	}

	/**
	 * @return the referencia
	 */
	public String getReferencia() {
		return referencia;
	}

	/**
	 * @param referencia the referencia to set
	 */
	public void setReferencia(String referencia) {
		this.referencia = referencia;
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
	 * @return the listTipoMensaje
	 */
	public List<String> getListTipoMensaje() {
		return listTipoMensaje;
	}

	/**
	 * @param listTipoMensaje the listTipoMensaje to set
	 */
	public void setListTipoMensaje(List<String> listTipoMensaje) {
		this.listTipoMensaje = listTipoMensaje;
	}

	/**
	 * @return the tipoMensajeSelected
	 */
	public Set<String> getTipoMensajeSelected() {
		return tipoMensajeSelected;
	}

	/**
	 * @param tipoMensajeSelected the tipoMensajeSelected to set
	 */
	public void setTipoMensajeSelected(Set<String> tipoMensajeSelected) {
		this.tipoMensajeSelected = tipoMensajeSelected;
	}

	/**
	 * @return the cuentasCustodia
	 */
	public boolean isCuentasCustodia() {
		return cuentasCustodia;
	}

	/**
	 * @param cuentasCustodia the cuentasCustodia to set
	 */
	public void setCuentasCustodia(boolean cuentasCustodia) {
		this.cuentasCustodia = cuentasCustodia;
	}

	/**
	 * @return the cuentasComercial
	 */
	public boolean isCuentasComercial() {
		return cuentasComercial;
	}

	/**
	 * @param cuentasComercial the cuentasComercial to set
	 */
	public void setCuentasComercial(boolean cuentasComercial) {
		this.cuentasComercial = cuentasComercial;
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
	 * @return the fechaRecepcionMensajeInicio
	 */
	public Date getFechaRecepcionMensajeInicio() {
		return fechaRecepcionMensajeInicio;
	}

	/**
	 * @param fechaRecepcionMensajeInicio the fechaRecepcionMensajeInicio to set
	 */
	public void setFechaRecepcionMensajeInicio(Date fechaRecepcionMensajeInicio) {
		this.fechaRecepcionMensajeInicio = fechaRecepcionMensajeInicio;
	}

	/**
	 * @return the fechaRecepcionMensajeFin
	 */
	public Date getFechaRecepcionMensajeFin() {
		return fechaRecepcionMensajeFin;
	}

	/**
	 * @param fechaRecepcionMensajeFin the fechaRecepcionMensajeFin to set
	 */
	public void setFechaRecepcionMensajeFin(Date fechaRecepcionMensajeFin) {
		this.fechaRecepcionMensajeFin = fechaRecepcionMensajeFin;
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
	 * @return the comentarioDali
	 */
	public String getComentarioDali() {
		return comentarioDali;
	}

	/**
	 * @param comentarioDali the comentarioDali to set
	 */
	public void setComentarioDali(String comentarioDali) {
		this.comentarioDali = comentarioDali;
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
	 * @return the idConfirmacion
	 */
	public Long getIdConfirmacion() {
		return idConfirmacion;
	}

	/**
	 * @param idConfirmacion the idConfirmacion to set
	 */
	public void setIdConfirmacion(Long idConfirmacion) {
		this.idConfirmacion = idConfirmacion;
	}

	/**
	 * @return the idFolio
	 */
	public String getIdFolio() {
		return idFolio;
	}

	/**
	 * @param idFolio the idFolio to set
	 */
	public void setIdFolio(String idFolio) {
		this.idFolio = idFolio;
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
	 * @return the totalCreditos
	 */
	public double getTotalCreditos() {
		return totalCreditos;
	}

	/**
	 * @param totalCreditos the totalCreditos to set
	 */
	public void setTotalCreditos(double totalCreditos) {
		this.totalCreditos = totalCreditos;
	}

	/**
	 * @return the totalDebitos
	 */
	public double getTotalDebitos() {
		return totalDebitos;
	}

	/**
	 * @param totalDebitos the totalDebitos to set
	 */
	public void setTotalDebitos(double totalDebitos) {
		this.totalDebitos = totalDebitos;
	}

	/**
	 * @return the totalCreditosPaginado
	 */
	public double getTotalCreditosPaginado() {
		return totalCreditosPaginado;
	}

	/**
	 * @param totalCreditosPaginado the totalCreditosPaginado to set
	 */
	public void setTotalCreditosPaginado(double totalCreditosPaginado) {
		this.totalCreditosPaginado = totalCreditosPaginado;
	}

	/**
	 * @return the totalDebitosPaginado
	 */
	public double getTotalDebitosPaginado() {
		return totalDebitosPaginado;
	}

	/**
	 * @param totalDebitosPaginado the totalDebitosPaginado to set
	 */
	public void setTotalDebitosPaginado(double totalDebitosPaginado) {
		this.totalDebitosPaginado = totalDebitosPaginado;
	}

	/**
	 * @return the mtEspecifico
	 */
	public String getMtEspecifico() {
		return mtEspecifico;
	}

	/**
	 * @param mtEspecifico the mtEspecifico to set
	 */
	public void setMtEspecifico(String mtEspecifico) {
		this.mtEspecifico = mtEspecifico;
	}

	/**
	 * @return the tipoNotificacion
	 */
	public int getTipoNotificacion() {
		return tipoNotificacion;
	}

	/**
	 * @param tipoNotificacion the tipoNotificacion to set
	 */
	public void setTipoNotificacion(int tipoNotificacion) {
		this.tipoNotificacion = tipoNotificacion;
	}

	/**
	 * @return the relatedReference
	 */
	public String getRelatedReference() {
		return relatedReference;
	}

	/**
	 * @param relatedReference the relatedReference to set
	 */
	public void setRelatedReference(String relatedReference) {
		this.relatedReference = relatedReference;
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
	 * @return the fechaRecepcionDaliInicio
	 */
	public Date getFechaRecepcionDaliInicio() {
		return fechaRecepcionDaliInicio;
	}

	/**
	 * @param fechaRecepcionDaliInicio the fechaRecepcionDaliInicio to set
	 */
	public void setFechaRecepcionDaliInicio(Date fechaRecepcionDaliInicio) {
		this.fechaRecepcionDaliInicio = fechaRecepcionDaliInicio;
	}

	/**
	 * @return the fechaRecepcionDaliFin
	 */
	public Date getFechaRecepcionDaliFin() {
		return fechaRecepcionDaliFin;
	}

	/**
	 * @param fechaRecepcionDaliFin the fechaRecepcionDaliFin to set
	 */
	public void setFechaRecepcionDaliFin(Date fechaRecepcionDaliFin) {
		this.fechaRecepcionDaliFin = fechaRecepcionDaliFin;
	}
}

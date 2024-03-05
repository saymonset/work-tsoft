/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * CapturaOperacionesController.java
 * Apr 28, 2008
 */
package com.indeval.portaldali.presentation.controller.mercadodinero.capturaoperaciones;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;

import com.indeval.portaldali.presentation.controller.common.ControllerBase;
import com.indeval.portaldali.presentation.helper.IsoHelper;
import com.indeval.portaldali.presentation.util.CifradorDescifradorBlowfish;
import com.indeval.portaldali.presentation.util.ConsultaCatalogosFacade;

/**
 * Clase base para los controllers de captura de operaciones.
 * Este controller agrupa operaciones comunes al proceso de captura de operaciones.
 * @author Emigdio Hernández
 *
 */
public class CapturaOperacionesController extends ControllerBase {
	
	protected String pantallas[] = new String[]{"colocacionPrimaria","fondeoCtaPropia","reportoNominal","traspasoLibrePago","venta"};
	protected String nombresPantallas[] = new String[]{"Colocación Primaria o Recompra","Fondeo a Cuenta Propia","Reporto nominal","Traspaso libre de pago","Venta"};
	/**
	 * Pantalla actualmente seleccionada
	 */
	protected String pantallaActual = null;
	/**
	 * Acceco a catálogos
	 */
	protected ConsultaCatalogosFacade consultaCatalogos = null;
	/** Ayudante para la generación de las cadenas ISO que deberán ser firmadas */
    protected IsoHelper isoHelper = null;

    /** La cadena que contiene el iso sin firmar */
    protected String isoSinFirmar = "";

    /** La cadena que contiene el iso firmado */
    protected String isoFirmado = "";

    /** EL número de serie asociado al iso firmado */
    protected String numeroSerie = "";
    /**
     * Folio de la operación
     */
    protected String folioAsignado = null;
    /**
     * Cadena Hash del ISO a firmar
     */
    protected String hashIso = null;
    
    protected CifradorDescifradorBlowfish cdb = new CifradorDescifradorBlowfish();
    
	/**
	 * Inicialización y actualización del valor de la pantalla actual
	 * @return null
	 */
	public String getInit(){
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		
		if(params.get("pantalla") != null){
			pantallaActual = params.get("pantalla");
		}else{
			String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
			if(viewId != null){
				for (String pantalla : pantallas) {
					if(viewId.indexOf(pantalla)>0){
						pantallaActual = pantalla;
						break;
					}
				}
			}
		}
		return null;
	}
	

	/**
	 * Limpia los campos referentes a la firma electrúnica
	 */
	protected void limpiarCampos(){
			isoFirmado = null;
            isoSinFirmar = ""; 
            numeroSerie = null;
            hashIso = null;
	}
	/**
	 * Toma del request los valores de los campos referentes a la firma electrúnica
	 */
	protected void recuperarCamposFirma(){
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            isoFirmado = params.get("isoFirmado").replace("\r\n","\n");
            hashIso = params.get("hashIso").replace("\r\n","\n");
            isoSinFirmar = params.get("isoSinFirmar").replace("\r\n","\n");
            numeroSerie = params.get("numeroSerie").replace("\r\n","\n");
	}
	/***
	 * Obtiene las operaciones disponibles por fase
	 * @return
	 */
	public List<SelectItem> getOperacionesPorFase(){
		List<SelectItem> operaciones = new ArrayList<SelectItem>();
		SelectItem item = null;
		for(int i=0;i<pantallas.length;i++){
			item = new SelectItem(pantallas[i],nombresPantallas[i]);
			operaciones.add(item);
		}
		return operaciones;
	}
	
    /**
     * Indica si ya se firmó el ISO en pantalla.
     * 
     * @return <code>true</code> si se firmó el ISO en pantalla.
     */
    public boolean isIsoYaFirmado() {
    	
    	return !StringUtils.isEmpty(isoSinFirmar);
    }
	
	/**
	 * Obtiene el saldo neto efectivo del participante
	 *  @return Saldo de la cuenta de efectivo controlada del participante
	 */
	public Double getSaldoNetoEfectivo(){
		return consultaCatalogos.getSaldoNetoEfectivo(getInstitucionActual().getClaveTipoInstitucion()+getInstitucionActual().getFolioInstitucion());
	}
	public void setSaldoNetoEfectivo(Double saldo){
		
	}
	/**
	 * Cambia la ventana de la operación actual
	 * @return
	 */
	public String cambiarOperacion(){
		return pantallaActual;
	}
	
	/**
	 * Obtiene el campo pantallaActual
	 * @return  pantallaActual
	 */
	public String getPantallaActual() {
		return pantallaActual;
	}

	/**
	 * Asigna el campo pantallaActual
	 * @param pantallaActual el valor de pantallaActual a asignar
	 */
	public void setPantallaActual(String pantallaActual) {
		this.pantallaActual = pantallaActual;
	}
	public ConsultaCatalogosFacade getConsultaCatalogos() {
		return consultaCatalogos;
	}
	public void setConsultaCatalogos(ConsultaCatalogosFacade consultaCatalogos) {
		this.consultaCatalogos = consultaCatalogos;
	}
	public IsoHelper getIsoHelper() {
		return isoHelper;
	}
	public void setIsoHelper(IsoHelper isoHelper) {
		this.isoHelper = isoHelper;
	}
	public String getIsoSinFirmar() {
		return isoSinFirmar;
	}
	public void setIsoSinFirmar(String isoSinFirmar) {
		this.isoSinFirmar = isoSinFirmar;
	}
	public String getIsoFirmado() {
		return isoFirmado;
	}
	public void setIsoFirmado(String isoFirmado) {
		this.isoFirmado = isoFirmado;
	}
	public String getNumeroSerie() {
		return numeroSerie;
	}
	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}
	public String getFolioAsignado() {
		return folioAsignado;
	}
	public void setFolioAsignado(String folioAsignado) {
		this.folioAsignado = folioAsignado;
	}
	public String getHashIso() {
		return hashIso;
	}
	public void setHashIso(String hashIso) {
		this.hashIso = hashIso;
	}
	
	

	
}

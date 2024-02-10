/*
 *Copyright (c) 2005-2007 Bursatec. All Rights Reserved
 */
package com.indeval.portalinternacional.presentation.controller.common;

import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;

import com.indeval.portalinternacional.common.util.CifradorDescifradorBlowfish;
import com.indeval.portalinternacional.common.util.IsoHelper;

/**
 * Controller base para los controllers que capturan operaciones que requieren firma electrúnica.
 * @author Emigdio Hern&aacute;ndez
 *
 */
public class CapturaOperacionesController extends ControllerBase {

    
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
     * Toma del request los valores de los campos referentes a la firma electrúnica
     */
    protected void recuperarCamposFirma(){
    	Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        isoFirmado = params.get("isoFirmado").replace("\r\n","\n");
        hashIso = params.get("hashIso").replace("\r\n","\n");
        isoSinFirmar = params.get("isoSinFirmar").replace("\r\n","\n");
        numeroSerie = params.get("numeroSerie").replace("\r\n","\n");
    }
    /**
     * Limpia los campos referentes a la firma electrúnica
     */
    protected void limpiarCampos(){
        isoFirmado = null;
        isoSinFirmar = null;
        numeroSerie = null;
        hashIso = null;
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
     * @return the isoHelper
     */
    public IsoHelper getIsoHelper() {
        return isoHelper;
    }

    /**
     * @param isoHelper the isoHelper to set
     */
    public void setIsoHelper(IsoHelper isoHelper) {
        this.isoHelper = isoHelper;
    }

    /**
     * @return the isoSinFirmar
     */
    public String getIsoSinFirmar() {
        return isoSinFirmar;
    }

    /**
     * @param isoSinFirmar the isoSinFirmar to set
     */
    public void setIsoSinFirmar(String isoSinFirmar) {
        this.isoSinFirmar = isoSinFirmar;
    }

    /**
     * @return the isoFirmado
     */
    public String getIsoFirmado() {
        return isoFirmado;
    }

    /**
     * @param isoFirmado the isoFirmado to set
     */
    public void setIsoFirmado(String isoFirmado) {
        this.isoFirmado = isoFirmado;
    }

    /**
     * @return the numeroSerie
     */
    public String getNumeroSerie() {
        return numeroSerie;
    }

    /**
     * @param numeroSerie the numeroSerie to set
     */
    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    /**
     * @return the folioAsignado
     */
    public String getFolioAsignado() {
        return folioAsignado;
    }

    /**
     * @param folioAsignado the folioAsignado to set
     */
    public void setFolioAsignado(String folioAsignado) {
        this.folioAsignado = folioAsignado;
    }

    /**
     * @return the hashIso
     */
    public String getHashIso() {
        return hashIso;
    }

    /**
     * @param hashIso the hashIso to set
     */
    public void setHashIso(String hashIso) {
        this.hashIso = hashIso;
    }

    /**
     * @return the cdb
     */
    public CifradorDescifradorBlowfish getCdb() {
        return cdb;
    }

    /**
     * @param cdb the cdb to set
     */
    public void setCdb(CifradorDescifradorBlowfish cdb) {
        this.cdb = cdb;
    }
    
}

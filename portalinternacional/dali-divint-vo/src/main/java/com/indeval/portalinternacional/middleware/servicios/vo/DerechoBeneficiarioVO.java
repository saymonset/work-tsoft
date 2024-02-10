package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * VO para mapear los beneficiarios de un derecho de la vista V_CONS_BENEF_DER_CTA.
 * Al final se transformara en un BeneficiarioDerecho que se tienen en el 
 * AdminBeneficiariosDerechoController.
 */
public class DerechoBeneficiarioVO implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 25L;

    private String cuenta;
    private String folioInstitucion;
    private Long idDerechoBeneficiario;
    private Long idDerechoCapital;
    private String nombre;
    private Double porcentajeRetencion;
    private Long posicion;
    private String uoi;
    private String adpNumber;
    private String tipoFormato;
    private String descTipoBeneficiario;
    private Long idTipoInstitucion;
    private Long idCamposFormatoW8BEN;
    private String rfcFW8BEN;
    private Long idCamposFormatoW8BENE;
    private String rfcFW8BENE;
    private Long idDomicilioW8Normal;
    private String countryDomW8Normal;
    private String direccionDomW8Normal;
    private Long idCamposFormatoW9;
    private String rfcFW9;
    private String countryDomW9;
    private String direccionDomW9;
    private Long idCamposFormatoW8IMY;
    private String rfcFW8IMY;
    private String rfcFW8IMY2015;
    private String countryDomW8NormalDEW8IMY;
    private String direccionDomW8NormalDEW8IMY;
    private Long idCamposFormatoMila;
    private String rfcMila;
    private String rfcMilaNumeroDocumento;
    private Long idDomicilioMila;
    private String countryDomMila;
    private String direccionDomMila;
    private Double porcentajeRetencionCustodio;

    /**Constructor por omisi&oacute;n*/
    public DerechoBeneficiarioVO() {
        super();
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getFolioInstitucion() {
        return folioInstitucion;
    }

    public void setFolioInstitucion(String folioInstitucion) {
        this.folioInstitucion = folioInstitucion;
    }

    public Long getIdDerechoBeneficiario() {
        return idDerechoBeneficiario;
    }

    public void setIdDerechoBeneficiario(Long idDerechoBeneficiario) {
        this.idDerechoBeneficiario = idDerechoBeneficiario;
    }

    public Long getIdDerechoCapital() {
        return idDerechoCapital;
    }

    public void setIdDerechoCapital(Long idDerechoCapital) {
        this.idDerechoCapital = idDerechoCapital;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPorcentajeRetencion() {
        return porcentajeRetencion;
    }

    public void setPorcentajeRetencion(Double porcentajeRetencion) {
        this.porcentajeRetencion = porcentajeRetencion;
    }

    public Long getPosicion() {
        return posicion;
    }

    public void setPosicion(Long posicion) {
        this.posicion = posicion;
    }

    public String getUoi() {
        return uoi;
    }

    public void setUoi(String uoi) {
        this.uoi = uoi;
    }

    public String getAdpNumber() {
        return adpNumber;
    }

    public void setAdpNumber(String adpNumber) {
        this.adpNumber = adpNumber;
    }

    public String getTipoFormato() {
        return tipoFormato;
    }

    public void setTipoFormato(String tipoFormato) {
        this.tipoFormato = tipoFormato;
    }

    public String getDescTipoBeneficiario() {
        return descTipoBeneficiario;
    }

    public void setDescTipoBeneficiario(String descTipoBeneficiario) {
        this.descTipoBeneficiario = descTipoBeneficiario;
    }

    public Long getIdTipoInstitucion() {
        return idTipoInstitucion;
    }

    public void setIdTipoInstitucion(Long idTipoInstitucion) {
        this.idTipoInstitucion = idTipoInstitucion;
    }

    public Long getIdCamposFormatoW8BEN() {
        return idCamposFormatoW8BEN;
    }

    public void setIdCamposFormatoW8BEN(Long idCamposFormatoW8BEN) {
        this.idCamposFormatoW8BEN = idCamposFormatoW8BEN;
    }

    public String getRfcFW8BEN() {
        return rfcFW8BEN;
    }

    public void setRfcFW8BEN(String rfcFW8BEN) {
        this.rfcFW8BEN = rfcFW8BEN;
    }

    public Long getIdCamposFormatoW8BENE() {
        return idCamposFormatoW8BENE;
    }

    public void setIdCamposFormatoW8BENE(Long idCamposFormatoW8BENE) {
        this.idCamposFormatoW8BENE = idCamposFormatoW8BENE;
    }

    public String getRfcFW8BENE() {
        return rfcFW8BENE;
    }

    public void setRfcFW8BENE(String rfcFW8BENE) {
        this.rfcFW8BENE = rfcFW8BENE;
    }

    public Long getIdDomicilioW8Normal() {
        return idDomicilioW8Normal;
    }

    public void setIdDomicilioW8Normal(Long idDomicilioW8Normal) {
        this.idDomicilioW8Normal = idDomicilioW8Normal;
    }

    public String getCountryDomW8Normal() {
        return countryDomW8Normal;
    }

    public void setCountryDomW8Normal(String countryDomW8Normal) {
        this.countryDomW8Normal = countryDomW8Normal;
    }

    public String getDireccionDomW8Normal() {
        return direccionDomW8Normal;
    }

    public void setDireccionDomW8Normal(String direccionDomW8Normal) {
        this.direccionDomW8Normal = direccionDomW8Normal;
    }

    public Long getIdCamposFormatoW9() {
        return idCamposFormatoW9;
    }

    public void setIdCamposFormatoW9(Long idCamposFormatoW9) {
        this.idCamposFormatoW9 = idCamposFormatoW9;
    }

    public String getRfcFW9() {
        return rfcFW9;
    }

    public void setRfcFW9(String rfcFW9) {
        this.rfcFW9 = rfcFW9;
    }

    public String getCountryDomW9() {
        return countryDomW9;
    }

    public void setCountryDomW9(String countryDomW9) {
        this.countryDomW9 = countryDomW9;
    }

    public String getDireccionDomW9() {
        return direccionDomW9;
    }

    public void setDireccionDomW9(String direccionDomW9) {
        this.direccionDomW9 = direccionDomW9;
    }

    public Long getIdCamposFormatoW8IMY() {
        return idCamposFormatoW8IMY;
    }

    public void setIdCamposFormatoW8IMY(Long idCamposFormatoW8IMY) {
        this.idCamposFormatoW8IMY = idCamposFormatoW8IMY;
    }

    public String getRfcFW8IMY() {
        return rfcFW8IMY;
    }

    public void setRfcFW8IMY(String rfcFW8IMY) {
        this.rfcFW8IMY = rfcFW8IMY;
    }

    public String getRfcFW8IMY2015() {
        return rfcFW8IMY2015;
    }

    public void setRfcFW8IMY2015(String rfcFW8IMY2015) {
        this.rfcFW8IMY2015 = rfcFW8IMY2015;
    }

    public String getCountryDomW8NormalDEW8IMY() {
        return countryDomW8NormalDEW8IMY;
    }

    public void setCountryDomW8NormalDEW8IMY(String countryDomW8NormalDEW8IMY) {
        this.countryDomW8NormalDEW8IMY = countryDomW8NormalDEW8IMY;
    }

    public String getDireccionDomW8NormalDEW8IMY() {
        return direccionDomW8NormalDEW8IMY;
    }

    public void setDireccionDomW8NormalDEW8IMY(String direccionDomW8NormalDEW8IMY) {
        this.direccionDomW8NormalDEW8IMY = direccionDomW8NormalDEW8IMY;
    }

    public Long getIdCamposFormatoMila() {
        return idCamposFormatoMila;
    }

    public void setIdCamposFormatoMila(Long idCamposFormatoMila) {
        this.idCamposFormatoMila = idCamposFormatoMila;
    }

    public String getRfcMila() {
        return rfcMila;
    }

    public void setRfcMila(String rfcMila) {
        this.rfcMila = rfcMila;
    }

    public String getRfcMilaNumeroDocumento() {
        return rfcMilaNumeroDocumento;
    }

    public void setRfcMilaNumeroDocumento(String rfcMilaNumeroDocumento) {
        this.rfcMilaNumeroDocumento = rfcMilaNumeroDocumento;
    }

    public Long getIdDomicilioMila() {
        return idDomicilioMila;
    }

    public void setIdDomicilioMila(Long idDomicilioMila) {
        this.idDomicilioMila = idDomicilioMila;
    }

    public String getCountryDomMila() {
        return countryDomMila;
    }

    public void setCountryDomMila(String countryDomMila) {
        this.countryDomMila = countryDomMila;
    }

    public String getDireccionDomMila() {
        return direccionDomMila;
    }

    public void setDireccionDomMila(String direccionDomMila) {
        this.direccionDomMila = direccionDomMila;
    }

    public Double getPorcentajeRetencionCustodio() {
        return porcentajeRetencionCustodio;
    }

    public void setPorcentajeRetencionCustodio(Double porcentajeRetencionCustodio) {
        this.porcentajeRetencionCustodio = porcentajeRetencionCustodio;
    }

    /*@Override
    public String toString() {
        return "PosicionCuentaVO=[idCuentaNombrada=" + idCuentaNombrada + ", cuenta=" + cuenta + ", posicion=" + posicion
                + ", posicionAsignada=" + posicionAsignada + ", posicionNoAsignada=" + posicionNoAsignada
                + ", nombreInstitucion=" + nombreInstitucion + ", idEmision=" + idEmision + ", claveInstitucion="
                + claveInstitucion + "]";
    }*/

}

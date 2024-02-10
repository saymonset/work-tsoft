/**
 * Bursatec - Portal Internacional Copyrigth (c) 2016 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;
import java.util.Date;

import com.indeval.portalinternacional.middleware.servicios.constantes.BeneficiariosConstantes;

/**
 * Transfer Object que contiene los parámetros de consulta para el detalle de ejercicio de derechos
 * de capital.
 * 
 * @author Pablo Balderas
 */
public class ParamConsultaDetalleEjerDerCapTO implements Serializable {

    /** Id para la serialización */
    private static final long serialVersionUID = -2552750448862434897L;

    /** TV de la emisión */
    private String tv;

    /** Emisora de la emisión */
    private String emisora;

    /** Serie de la emisión */
    private String serie;

    /** ISIN de la emisión */
    private String isin;

    /** Id de la emisión */
    private Long idEmision;

    /** Id del custodio */
    private Long idCustodio;

    /** Custodio */
    private String custodio;

    /** Institución (id-folio) */
    private String institucion;

    /** Id institucion */
    private Long idInstitucion;

    /** Nombre de la institución */
    private String nombreInstitucion;

    /** Cuenta asociada */
    private String cuenta;

    /** Fecha de pago inicial */
    private Date fechaPagoInicial;

    /** Fecha de pago final */
    private Date fechaPagoFinal;

    /** Fecha de corte inicial */
    private Date fechaCorteInicial;

    /** Fecha de corte final */
    private Date fechaCorteFinal;

    /** Id de la divisa */
    private Long idDivisa;

    /** Divisa */
    private String divisa;

    /** Id del estado del derecho */
    private Long idEstadoDerecho;

    /** Estado derecho */
    private String estadoDerecho;

    /** Id derecho Capital */
    private Long idDerechoCapital;

    /** Porcentaje de Retencion */
    private String porcentajeRetencion;

    /** Nombre Beneficiario */
    private String nombreBeneficiario;

    /** RFC */
    private String rfc;

    /** Proporcion */
    private String proporcion;

    /** UOI */
    private String uoi;

    /** Pais */
    private String pais;

    /** Cantidad de Titulos */
    private String cantidadTitulos;

    /** Referencia Custodio */
    private String referenciaCustodio;

    /** Tipo Formato */
    private String tipoFormato;

    /** Id Tipo Beneficiario */
    private Long idTipoBeneficiario;

    /** Tipo Beneficiario */
    private String tipoBeneficiario;

    /** Indica con fee, sin fee o ambos */
    private Integer tipoFee;

    /** Fee */
    private String fee;

    /** Monto Bruto */
    private String montoBruto;

    /** Monto Fee */
    private String montoFee;

    /** Impuesto Retenido */
    private String impuestoRetenido;

    /** Monto Neto */
    private String montoNeto;

    /** Estado formato W */
    private String estadoFormatoW;

    /** GIIN */
    private String giin;

    /** Reference Number */
    private String referenceNumber;

    /** tipo derecho **/
    private Long idTipoDerecho;

    /**
     * Método para obtener el atributo tv
     * 
     * @return El atributo tv
     */
    public String getTv() {
        return this.tv;
    }

    /**
     * Método para establecer el atributo tv
     * 
     * @param tv El valor del atributo tv a establecer.
     */
    public void setTv(final String tv) {
        this.tv = tv != null ? tv.toUpperCase() : tv;
    }

    /**
     * Método para obtener el atributo emisora
     * 
     * @return El atributo emisora
     */
    public String getEmisora() {
        return this.emisora;
    }

    /**
     * Método para establecer el atributo emisora
     * 
     * @param emisora El valor del atributo emisora a establecer.
     */
    public void setEmisora(final String emisora) {
        this.emisora = emisora != null ? emisora.toUpperCase() : emisora;
    }

    /**
     * Método para obtener el atributo serie
     * 
     * @return El atributo serie
     */
    public String getSerie() {
        return this.serie;
    }

    /**
     * Método para establecer el atributo serie
     * 
     * @param serie El valor del atributo serie a establecer.
     */
    public void setSerie(final String serie) {
        this.serie = serie != null ? serie.toUpperCase() : serie;
    }

    /**
     * Método para obtener el atributo isin
     * 
     * @return El atributo isin
     */
    public String getIsin() {
        return this.isin;
    }

    /**
     * Método para establecer el atributo isin
     * 
     * @param isin El valor del atributo isin a establecer.
     */
    public void setIsin(final String isin) {
        this.isin = isin != null ? isin.toUpperCase() : isin;
    }

    /**
     * Método para obtener el atributo idEmision
     * 
     * @return El atributo idEmision
     */
    public Long getIdEmision() {
        return this.idEmision;
    }

    /**
     * Método para establecer el atributo idEmision
     * 
     * @param idEmision El valor del atributo idEmision a establecer.
     */
    public void setIdEmision(final Long idEmision) {
        this.idEmision = idEmision;
    }

    /**
     * Método para obtener el atributo idCustodio
     * 
     * @return El atributo idCustodio
     */
    public Long getIdCustodio() {
        return this.idCustodio;
    }

    /**
     * Método para establecer el atributo idCustodio
     * 
     * @param idCustodio El valor del atributo idCustodio a establecer.
     */
    public void setIdCustodio(final Long idCustodio) {
        this.idCustodio = idCustodio;
    }

    /**
     * Método para obtener el atributo institucion
     * 
     * @return El atributo institucion
     */
    public String getInstitucion() {
        return this.institucion;
    }

    /**
     * Método para establecer el atributo institucion
     * 
     * @param institucion El valor del atributo institucion a establecer.
     */
    public void setInstitucion(final String institucion) {
        this.institucion = institucion != null ? institucion.toUpperCase() : institucion;
    }

    /**
     * Método para obtener el atributo idInstitucion
     * 
     * @return El atributo idInstitucion
     */
    public Long getIdInstitucion() {
        return this.idInstitucion;
    }

    /**
     * Método para establecer el atributo idInstitucion
     * 
     * @param idInstitucion El valor del atributo idInstitucion a establecer.
     */
    public void setIdInstitucion(final Long idInstitucion) {
        this.idInstitucion = idInstitucion;
    }

    /**
     * Método para obtener el atributo nombreInstitucion
     * 
     * @return El atributo nombreInstitucion
     */
    public String getNombreInstitucion() {
        return this.nombreInstitucion;
    }

    /**
     * Método para establecer el atributo nombreInstitucion
     * 
     * @param nombreInstitucion El valor del atributo nombreInstitucion a establecer.
     */
    public void setNombreInstitucion(final String nombreInstitucion) {
        this.nombreInstitucion =
                nombreInstitucion != null ? nombreInstitucion.toUpperCase() : nombreInstitucion;
    }

    /**
     * Método para obtener el atributo cuenta
     * 
     * @return El atributo cuenta
     */
    public String getCuenta() {
        return this.cuenta;
    }

    /**
     * Método para establecer el atributo cuenta
     * 
     * @param cuenta El valor del atributo cuenta a establecer.
     */
    public void setCuenta(final String cuenta) {
        this.cuenta = cuenta != null ? cuenta.toUpperCase() : cuenta;
    }

    /**
     * Método para obtener el atributo fechaPagoInicial
     * 
     * @return El atributo fechaPagoInicial
     */
    public Date getFechaPagoInicial() {
        return this.fechaPagoInicial;
    }

    /**
     * Método para establecer el atributo fechaPagoInicial
     * 
     * @param fechaPagoInicial El valor del atributo fechaPagoInicial a establecer.
     */
    public void setFechaPagoInicial(final Date fechaPagoInicial) {
        this.fechaPagoInicial = fechaPagoInicial;
    }

    /**
     * Método para obtener el atributo fechaPagoFinal
     * 
     * @return El atributo fechaPagoFinal
     */
    public Date getFechaPagoFinal() {
        return this.fechaPagoFinal;
    }

    /**
     * Método para establecer el atributo fechaPagoFinal
     * 
     * @param fechaPagoFinal El valor del atributo fechaPagoFinal a establecer.
     */
    public void setFechaPagoFinal(final Date fechaPagoFinal) {
        this.fechaPagoFinal = fechaPagoFinal;
    }

    /**
     * Método para obtener el atributo fechaCorteInicial
     * 
     * @return El atributo fechaCorteInicial
     */
    public Date getFechaCorteInicial() {
        return this.fechaCorteInicial;
    }

    /**
     * Método para establecer el atributo fechaCorteInicial
     * 
     * @param fechaCorteInicial El valor del atributo fechaCorteInicial a establecer.
     */
    public void setFechaCorteInicial(final Date fechaCorteInicial) {
        this.fechaCorteInicial = fechaCorteInicial;
    }

    /**
     * Método para obtener el atributo fechaCorteFinal
     * 
     * @return El atributo fechaCorteFinal
     */
    public Date getFechaCorteFinal() {
        return this.fechaCorteFinal;
    }

    /**
     * Método para establecer el atributo fechaCorteFinal
     * 
     * @param fechaCorteFinal El valor del atributo fechaCorteFinal a establecer.
     */
    public void setFechaCorteFinal(final Date fechaCorteFinal) {
        this.fechaCorteFinal = fechaCorteFinal;
    }

    /**
     * Método para obtener el atributo idDivisa
     * 
     * @return El atributo idDivisa
     */
    public Long getIdDivisa() {
        return this.idDivisa;
    }

    /**
     * Método para establecer el atributo idDivisa
     * 
     * @param idDivisa El valor del atributo idDivisa a establecer.
     */
    public void setIdDivisa(final Long idDivisa) {
        this.idDivisa = idDivisa;
    }

    /**
     * Método para obtener el atributo idEstadoDerecho
     * 
     * @return El atributo idEstadoDerecho
     */
    public Long getIdEstadoDerecho() {
        return this.idEstadoDerecho;
    }

    /**
     * Método para establecer el atributo idEstadoDerecho
     * 
     * @param idEstadoDerecho El valor del atributo idEstadoDerecho a establecer.
     */
    public void setIdEstadoDerecho(final Long idEstadoDerecho) {
        this.idEstadoDerecho = idEstadoDerecho;
    }

    /**
     * Método para obtener el atributo divisa
     * 
     * @return El atributo divisa
     */
    public String getDivisa() {
        return this.divisa;
    }

    /**
     * Método para establecer el atributo divisa
     * 
     * @param divisa El valor del atributo divisa a establecer.
     */
    public void setDivisa(final String divisa) {
        this.divisa = divisa != null ? divisa.toUpperCase() : divisa;
    }

    /**
     * Método para obtener el atributo estadoDerecho
     * 
     * @return El atributo estadoDerecho
     */
    public String getEstadoDerecho() {
        return this.estadoDerecho;
    }

    /**
     * Método para establecer el atributo estadoDerecho
     * 
     * @param estadoDerecho El valor del atributo estadoDerecho a establecer.
     */
    public void setEstadoDerecho(final String estadoDerecho) {
        this.estadoDerecho = estadoDerecho != null ? estadoDerecho.toUpperCase() : estadoDerecho;
    }

    /**
     * Método para obtener el atributo custodio
     * 
     * @return El atributo custodio
     */
    public String getCustodio() {
        return this.custodio;
    }

    /**
     * Método para establecer el atributo custodio
     * 
     * @param custodio El valor del atributo custodio a establecer.
     */
    public void setCustodio(final String custodio) {
        this.custodio = custodio != null ? custodio.toUpperCase() : custodio;
    }

    /**
     * Método para obtener el atributo idDerechoCapital
     * 
     * @return El atributo idDerechoCapital
     */
    public Long getIdDerechoCapital() {
        return this.idDerechoCapital;
    }

    /**
     * Método para establecer el atributo idDerechoCapital
     * 
     * @param idDerechoCapital El valor del atributo idDerechoCapital a establecer.
     */
    public void setIdDerechoCapital(final Long idDerechoCapital) {
        this.idDerechoCapital = idDerechoCapital;
    }

    public String getPorcentajeRetencion() {
        return this.porcentajeRetencion;
    }

    public void setPorcentajeRetencion(final String porcentajeRetencion) {
        this.porcentajeRetencion = porcentajeRetencion;
    }

    public String getNombreBeneficiario() {
        return this.nombreBeneficiario;
    }

    public void setNombreBeneficiario(final String nombreBeneficiario) {
        this.nombreBeneficiario =
                nombreBeneficiario != null ? nombreBeneficiario.toUpperCase() : nombreBeneficiario;
    }

    public String getRfc() {
        return this.rfc;
    }

    public void setRfc(final String rfc) {
        this.rfc = rfc != null ? rfc.toUpperCase() : rfc;
    }

    public String getMontoBruto() {
        return this.montoBruto;
    }

    public void setMontoBruto(final String montoBruto) {
        this.montoBruto = montoBruto;
    }

    public String getMontoNeto() {
        return this.montoNeto;
    }

    public void setMontoNeto(final String montoNeto) {
        this.montoNeto = montoNeto;
    }

    public String getProporcion() {
        return this.proporcion;
    }

    public void setProporcion(final String proporcion) {
        this.proporcion = proporcion;
    }

    public String getUoi() {
        return this.uoi;
    }

    public void setUoi(final String uoi) {
        this.uoi = uoi != null ? uoi.toUpperCase() : uoi;
    }

    public String getPais() {
        return this.pais;
    }

    public void setPais(final String pais) {
        this.pais = pais != null ? pais.toUpperCase() : pais;
    }

    public String getCantidadTitulos() {
        return this.cantidadTitulos;
    }

    public void setCantidadTitulos(final String cantidadTitulos) {
        this.cantidadTitulos = cantidadTitulos;
    }

    public String getReferenciaCustodio() {
        return this.referenciaCustodio;
    }

    public void setReferenciaCustodio(final String referenciaCustodio) {
        this.referenciaCustodio =
                referenciaCustodio != null ? referenciaCustodio.toUpperCase() : referenciaCustodio;
    }

    /**
     * TODO Me siento sucio, borrameeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee!!!!!!!!!
     * @return
     */
    public String getTipoFormatoPantalla() {
        String tipoFormatoPantalla;
    	if (BeneficiariosConstantes.FORMATO_W8_IMY2017.equals(this.tipoFormato)) {
            tipoFormatoPantalla = BeneficiariosConstantes.FORMATO_W8_IMY2016;
        }
    	else {
            tipoFormatoPantalla = this.tipoFormato;
        }
        return tipoFormatoPantalla;
    }
    
    
    public String getTipoFormato() {
        return this.tipoFormato;
    }

    public void setTipoFormato(final String tipoFormato) {
        this.tipoFormato = tipoFormato != null ? tipoFormato.toUpperCase() : tipoFormato;
    }

    /**
     * Método para obtener el atributo idTipoBeneficiario
     * 
     * @return El atributo idTipoBeneficiario
     */
    public Long getIdTipoBeneficiario() {
        return this.idTipoBeneficiario;
    }

    /**
     * Método para establecer el atributo idTipoBeneficiario
     * 
     * @param idTipoBeneficiario El valor del atributo idTipoBeneficiario a establecer.
     */
    public void setIdTipoBeneficiario(final Long idTipoBeneficiario) {
        this.idTipoBeneficiario = idTipoBeneficiario;
    }

    /**
     * Método para obtener el atributo tipoBeneficiario
     * 
     * @return El atributo tipoBeneficiario
     */
    public String getTipoBeneficiario() {
        return this.tipoBeneficiario;
    }

    /**
     * Método para establecer el atributo tipoBeneficiario
     * 
     * @param tipoBeneficiario El valor del atributo tipoBeneficiario a establecer.
     */
    public void setTipoBeneficiario(final String tipoBeneficiario) {
        this.tipoBeneficiario = tipoBeneficiario;
    }

    public String getImpuestoRetenido() {
        return this.impuestoRetenido;
    }

    public void setImpuestoRetenido(final String impuestoRetenido) {
        this.impuestoRetenido = impuestoRetenido;
    }

    /**
     * Método para obtener el atributo montoFee
     * 
     * @return El atributo montoFee
     */
    public String getMontoFee() {
        return this.montoFee;
    }

    /**
     * Método para establecer el atributo montoFee
     * 
     * @param montoFee El valor del atributo montoFee a establecer.
     */
    public void setMontoFee(final String montoFee) {
        this.montoFee = montoFee;
    }

    /**
     * Método para obtener el atributo estadoFormatoW
     * 
     * @return El atributo estadoFormatoW
     */
    public String getEstadoFormatoW() {
        return this.estadoFormatoW;
    }

    /**
     * Método para establecer el atributo estadoFormatoW
     * 
     * @param estadoFormatoW El valor del atributo estadoFormatoW a establecer.
     */
    public void setEstadoFormatoW(final String estadoFormatoW) {
        this.estadoFormatoW =
                estadoFormatoW != null ? estadoFormatoW.toUpperCase() : estadoFormatoW;
    }

    /**
     * Método para obtener el atributo giin
     * 
     * @return El atributo giin
     */
    public String getGiin() {
        return this.giin;
    }

    /**
     * Método para establecer el atributo giin
     * 
     * @param giin El valor del atributo giin a establecer.
     */
    public void setGiin(final String giin) {
        this.giin = giin != null ? giin.toUpperCase() : giin;
    }

    /**
     * Método para obtener el atributo referenceNumber
     * 
     * @return El atributo referenceNumber
     */
    public String getReferenceNumber() {
        return this.referenceNumber;
    }

    /**
     * Método para establecer el atributo referenceNumber
     * 
     * @param referenceNumber El valor del atributo referenceNumber a establecer.
     */
    public void setReferenceNumber(final String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    /**
     * Método para obtener el atributo tipoFee
     * 
     * @return El atributo tipoFee
     */
    public Integer getTipoFee() {
        return this.tipoFee;
    }

    /**
     * Método para establecer el atributo tipoFee
     * 
     * @param tipoFee El valor del atributo tipoFee a establecer.
     */
    public void setTipoFee(final Integer tipoFee) {
        this.tipoFee = tipoFee;
    }

    /**
     * Método para obtener el atributo fee
     * 
     * @return El atributo fee
     */
    public String getFee() {
        return this.fee;
    }

    /**
     * Método para establecer el atributo fee
     * 
     * @param fee El valor del atributo fee a establecer.
     */
    public void setFee(final String fee) {
        this.fee = fee;
    }

    /** @return this.idTipoDerecho */
    public Long getIdTipoDerecho() {
        return this.idTipoDerecho;
    }

    /** @param idTipoDerecho to be set in this.idTipoDerecho */
    public void setIdTipoDerecho(final Long idTipoDerecho) {
        this.idTipoDerecho = idTipoDerecho;
    }

}

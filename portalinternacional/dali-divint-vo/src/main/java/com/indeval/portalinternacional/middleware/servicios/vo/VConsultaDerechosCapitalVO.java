package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;

import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;

/**
 * Entidad para la vista que se usa en la consulta de derechos de capital.
 * @author edeloera
 */
public class VConsultaDerechosCapitalVO implements Serializable {

    private static final long serialVersionUID = 131L;

    private Long idDerechoCapital;

    private Long idEmision;

    private String claveTipoValor;

	private String clavePizarra;

	private String serie;

	private String claveCupon;

	private String isin;

	private Boolean esExtranjera;

	private Date fechaCorte;
	
	private String fechaCorteString;

	private Date fechaPago;

	private Float proporcion;

	private Float fee;

	private Long idDivisa;

	private String divisa;

	private Long idBoveda;

	private String boveda;

    private String bovedaCustodio;

    private Long idEstatusDerecho;

    private Long idEstatusDerechoCortado;

    private String descEstatusDerecho;

    private Integer idTipoDerecho;

    private String descTipoDerecho;

    private Long idSubtipoDerecho;

    private String descSubtipoDerecho;

    private Long idCuentaNombrada;

    /*private String estatusRegistro;*/

    private Long idFolio;

    private EmisionVO emision;
    
    private String bovedaValoresDerecho;

    /**
     * Constructor default.
     */
    public VConsultaDerechosCapitalVO() {
    }

    /**
     * Constructor con varios parametros.
     * @param idDerechoCapital
     * @param idTipoDerecho
     * @param descTipoDerecho
     * @param idSubtipoDerecho
     * @param descSubtipoDerecho
     * @param claveTipoValor
     * @param clavePizarra
     * @param serie
     * @param claveCupon
     * @param isin
     * @param fechaCorte
     * @param fechaPagoDerechoCapital
     * @param proporcion
     * @param claveAlfabetica
     * @param bovedaCustodio
     * @param estatusRegistro
     */
    public VConsultaDerechosCapitalVO(Long idDerechoCapital, Integer idTipoDerecho, String descTipoDerecho, Long idSubtipoDerecho, 
                                      String descSubtipoDerecho, String claveTipoValor, String clavePizarra, String serie, 
                                      String claveCupon, String isin, Date fechaCorte, Date fechaPagoDerechoCapital, 
                                      Float proporcion, String claveAlfabetica, String bovedaCustodio/*, String estatusRegistro*/) {
        this.idDerechoCapital = idDerechoCapital;
        this.idTipoDerecho = idTipoDerecho;
        this.descTipoDerecho = descTipoDerecho;
        this.idSubtipoDerecho = idSubtipoDerecho;
        this.descSubtipoDerecho = descSubtipoDerecho;
        this.claveTipoValor = claveTipoValor;
        this.clavePizarra = clavePizarra;
        this.serie = serie;
        this.claveCupon = claveCupon;
        this.isin = isin;
        this.fechaCorte = fechaCorte;
        this.fechaPago = fechaPagoDerechoCapital;
        this.proporcion = proporcion;
        this.divisa = claveAlfabetica;
        this.bovedaCustodio = bovedaCustodio;
        //this.estatusRegistro = estatusRegistro;
    }

    public Long getIdDerechoCapital() {
        return idDerechoCapital;
    }

    public void setIdDerechoCapital(Long idDerechoCapital) {
        this.idDerechoCapital = idDerechoCapital;
    }

    public Long getIdEmision() {
        return idEmision;
    }

    public void setIdEmision(Long idEmision) {
        this.idEmision = idEmision;
    }

    public String getClaveTipoValor() {
        return claveTipoValor;
    }

    public void setClaveTipoValor(String claveTipoValor) {
        this.claveTipoValor = claveTipoValor;
    }

    public String getClavePizarra() {
        return clavePizarra;
    }

    public void setClavePizarra(String clavePizarra) {
        this.clavePizarra = clavePizarra;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getClaveCupon() {
        return claveCupon;
    }

    public void setClaveCupon(String claveCupon) {
        this.claveCupon = claveCupon;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public Boolean esExtranjera() {
        return esExtranjera;
    }

    public void setEsExtranjera(Boolean esExtranjera) {
        this.esExtranjera = esExtranjera;
    }

    public Date getFechaCorte() {
        return fechaCorte;
    }

    public void setFechaCorte(Date fechaCorte) {
        this.fechaCorte = fechaCorte;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Float getProporcion() {
        return this.proporcion;
    }

    public String getProporcionStr() {
        String val = "0.0";
        if (this.proporcion != null && this.proporcion != 0.0) {
            DecimalFormat df = new DecimalFormat("0.######");
            val = df.format(proporcion);
        }
        return val;
    }

    public void setProporcion(Float proporcion) {
        this.proporcion = proporcion;
    }

    public Float getFee() {
        return this.fee;
    }

    public String getFeeAsString() {
        String val = "0.0";
        if (this.fee != null && this.fee != 0.0) {
            DecimalFormat df = new DecimalFormat("0.######");
            val = df.format(fee);
        }
        return val;
    }

    public void setFee(Float fee) {
        this.fee = fee;
    }

    public Long getIdDivisa() {
        return idDivisa;
    }

    public void setIdDivisa(Long idDivisa) {
        this.idDivisa = idDivisa;
    }

    public String getDivisa() {
        return divisa;
    }

    public void setDivisa(String divisa) {
        this.divisa = divisa;
    }

    public Long getIdBoveda() {
        return idBoveda;
    }

    public void setIdBoveda(Long idBoveda) {
        this.idBoveda = idBoveda;
    }

    public String getBoveda() {
        return boveda;
    }

    public void setBoveda(String boveda) {
        this.boveda = boveda;
    }

    public String getBovedaCustodio() {
        return bovedaCustodio;
    }

    public void setBovedaCustodio(String bovedaCustodio) {
        this.bovedaCustodio = bovedaCustodio;
    }

    public Long getIdEstatusDerecho() {
        return idEstatusDerecho;
    }

    public void setIdEstatusDerecho(Long idEstatusDerecho) {
        this.idEstatusDerecho = idEstatusDerecho;
    }

    public Long getIdEstatusDerechoCortado() {
        return idEstatusDerechoCortado;
    }

    public void setIdEstatusDerechoCortado(Long idEstatusDerechoCortado) {
        this.idEstatusDerechoCortado = idEstatusDerechoCortado;
    }

    public String getDescEstatusDerecho() {
        return descEstatusDerecho;
    }

    public void setDescEstatusDerecho(String descEstatusDerecho) {
        this.descEstatusDerecho = descEstatusDerecho;
    }

    public Integer getIdTipoDerecho() {
        return idTipoDerecho;
    }

    public void setIdTipoDerecho(Integer idTipoDerecho) {
        this.idTipoDerecho = idTipoDerecho;
    }

    public String getDescTipoDerecho() {
        return descTipoDerecho;
    }

    public void setDescTipoDerecho(String descTipoDerecho) {
        this.descTipoDerecho = descTipoDerecho;
    }

    public Long getIdSubtipoDerecho() {
        return idSubtipoDerecho;
    }

    public void setIdSubtipoDerecho(Long idSubtipoDerecho) {
        this.idSubtipoDerecho = idSubtipoDerecho;
    }

    public String getDescSubtipoDerecho() {
        return descSubtipoDerecho;
    }

    public void setDescSubtipoDerecho(String descSubtipoDerecho) {
        this.descSubtipoDerecho = descSubtipoDerecho;
    }

    public Long getIdCuentaNombrada() {
        return idCuentaNombrada;
    }

    public void setIdCuentaNombrada(Long idCuentaNombrada) {
        this.idCuentaNombrada = idCuentaNombrada;
    }

    /*public String getEstatusRegistro() {
        return estatusRegistro;
    }

    public void setEstatusRegistro(String estatusRegistro) {
        this.estatusRegistro = estatusRegistro;
    }*/

    public Long getIdFolio() {
        return idFolio;
    }

    public void setIdFolio(Long idFolio) {
        this.idFolio = idFolio;
    }

    public String getFechaCorteString() {
		return fechaCorteString;
	}

	public void setFechaCorteString(String fechaCorteString) {
		this.fechaCorteString = fechaCorteString;
	}

	public EmisionVO getEmision() {
        if (this.emision == null) {
            EmisionVO emisionVO = new EmisionVO();
            emisionVO.setCupon(this.getClaveCupon());
            emisionVO.setEmisora(this.getClavePizarra());
            emisionVO.setIsin(this.getIsin());
            emisionVO.setSerie(this.getSerie());
            emisionVO.setTv(this.getClaveTipoValor());
            this.emision = emisionVO;
        }
        return emision;
    }

    public void setEmision(EmisionVO emision) {
        this.emision = emision;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        return sb.append("idDerechoCapital=[" + this.idDerechoCapital.toString() + "] | ").
        append("claveTipoValor=[" + this.claveTipoValor + "] | ").
        append("clavePizarra=[" + this.clavePizarra + "] | ").
        append("serie=[" + this.serie + "] | ").
        append("claveCupon=[" + this.claveCupon + "] | ").
        append("isin=[" + this.isin + "] | ").
        append("esExtranjera=[" + this.esExtranjera + "] | ").
        append("fechaCorte=[" + this.fechaCorte.toString() + "] | ").
        append("fechaPago=[" + this.fechaPago.toString() + "] | ").
        append("proporcion=[" + this.proporcion.toString() + "] | ").
        append("fee=[" + this.fee.toString() + "] | ").
        append("idDivisa=[" + this.idDivisa.toString() + "] | ").
        append("divisa=[" + this.divisa + "] | ").
        append("idBoveda=[" + this.idBoveda.toString() + "] | ").
        append("boveda=[" + this.boveda + "] | ").
        append("bovedaCustodio=[" + this.bovedaCustodio + "] | ").
        append("idEstatusDerecho=[" + this.idEstatusDerecho.toString() + "] | ").
        append("idEstatusDerechoCortado=[" + this.idEstatusDerechoCortado.toString() + "] | ").
        append("descEstatusDerecho=[" + this.descEstatusDerecho + "] | ").
        append("idTipoDerecho=[" + this.idTipoDerecho.toString() + "] | ").
        append("descTipoDerecho=[" + this.descTipoDerecho + "] | ").
        append("idSubtipoDerecho=[" + this.idSubtipoDerecho.toString() + "] | ").
        append("descSubtipoDerecho=[" + this.descSubtipoDerecho + "] | ").
        append("idCuentaNombrada=[" + this.idCuentaNombrada.toString() + "] | ").
        //append("estatusRegistro=[" + this.estatusRegistro + "] | ").
        append("idFolio=[" + this.idFolio.toString() + "] | ").toString()
        ;
    }

	/**
	 * @return the bovedaValoresDerecho
	 */
	public String getBovedaValoresDerecho() {
		return bovedaValoresDerecho;
	}

	/**
	 * @param bovedaValoresDerecho the bovedaValoresDerecho to set
	 */
	public void setBovedaValoresDerecho(String bovedaValoresDerecho) {
		this.bovedaValoresDerecho = bovedaValoresDerecho;
	}

}

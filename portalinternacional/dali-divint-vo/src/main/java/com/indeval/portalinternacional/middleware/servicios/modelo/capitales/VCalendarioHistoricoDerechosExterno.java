package com.indeval.portalinternacional.middleware.servicios.modelo.capitales;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Entidad de la vista V_HISTORICO_CONSULTA_EXTERNO
 * @author omar.gutierrez
 *
 */
@Entity
@Table(name = "V_HISTORICO_CONSULTA_EXTERNO")
public class VCalendarioHistoricoDerechosExterno implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;  
	private Date fechaPrimerEnvio;
	private Long idCalendario;
	private Long prioridad;
	private Long idTipoPagoCAEV;
	private Long idTipoPagoCAMV;
	private String tipoPagoCAEVDescripcion;
	private String tipoPagoCAMVDescripcion;
	private String tipoPagoCAEV;
	private String tipoPagoCAMV;
	private String cfi;
	private String tipoValor;
	private String emisora;
	private String serie;
	private String cupon;
	private String isin;
	private Date fechaCorte;
	private Date fechaPago;
	private Date fechaXDate;
	private Date fechaVencimiento;
	private Date fechaEfectiva;
	private BigDecimal importeGRSS;
	private String ratioAdicional;
	private BigDecimal importeNETT;
	private String divisa;
	private BigDecimal importeWITL;
    private BigDecimal importeFEE; 
    private String boveda;
    private String edoFunc;
    private String edoProc;
    private String subtipoDerecho;
    protected String valor1;
    protected String valor2;
    private String narrativa;
    protected List<String> ratioAdicionalLst;
	
    
    /**
	 * @return the id
	 */
    @Id	 
	@Column(name = "ID_HISTORICO")
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the fechaPrimerEnvio
	 */
    @Column(name = "FECHA_ENVIO")
	public Date getFechaPrimerEnvio() {
		return fechaPrimerEnvio;
	}
	/**
	 * @param fechaPrimerEnvio the fechaPrimerEnvio to set
	 */
	public void setFechaPrimerEnvio(Date fechaPrimerEnvio) {
		this.fechaPrimerEnvio = fechaPrimerEnvio;
	}
	/**
	 * @return the idCalendario
	 */ 
	@Column(name = "FOLIO_INDEVAL")
	public Long getIdCalendario() {
		return idCalendario;
	}
	/**
	 * @param idCalendario the idCalendario to set
	 */
	public void setIdCalendario(Long idCalendario) {
		this.idCalendario = idCalendario;
	}
	
	/**
	 * @return the prioridad
	 */
	@Column(name="PRIORIDAD")
	public Long getPrioridad() {
		return prioridad;
	}
	/**
	 * @param prioridad the prioridad to set
	 */
	public void setPrioridad(Long prioridad) {
		this.prioridad = prioridad;
	} 
	
	/**
	 * @return the idTipoPagoCAEV
	 */
	@Column(name = "ID_CAEV")
	public Long getIdTipoPagoCAEV() {
		return idTipoPagoCAEV;
	}
	/**
	 * @param idTipoPagoCAEV the idTipoPagoCAEV to set
	 */
	public void setIdTipoPagoCAEV(Long idTipoPagoCAEV) {
		this.idTipoPagoCAEV = idTipoPagoCAEV;
	}
	/**
	 * @return the idTipoPagoCAMV
	 */
	@Column(name = "ID_CAMV")
	public Long getIdTipoPagoCAMV() {
		return idTipoPagoCAMV;
	}
	/**
	 * @param idTipoPagoCAMV the idTipoPagoCAMV to set
	 */
	public void setIdTipoPagoCAMV(Long idTipoPagoCAMV) {
		this.idTipoPagoCAMV = idTipoPagoCAMV;
	}
	/**
	 * @return the cfi
	 */
	@Column(name = "CFI")
	public String getCfi() {
		return cfi;
	}
	
	/**
	 * @param cfi the cfi to set
	 */
	public void setCfi(String cfi) {
		this.cfi = cfi;
	}
	/**
	 * @return the tipoPagoCAEV
	 */
	@Column(name = "CAEV")
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
	@Column(name = "CAMV")
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
	 * @return the tipoValor
	 */
	@Column(name = "TV")
	public String getTipoValor() {
		return tipoValor;
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
	@Column(name = "EMISORA")
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
	@Column(name = "SERIE")
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
	 * @return the cupon
	 */
	@Column(name = "CUPON")
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
	 * @return the isin
	 */
	@Column(name = "ISIN")
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
	 * @return the fechaCorte
	 */
	@Column(name = "FECHA_CORTE")
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
	 * @return the fechaPago
	 */
	@Column(name = "FECHA_PAGO")
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
	 * @return the fechaXDate
	 */
	@Column(name = "FECHA_XDATE")
	public Date getFechaXDate() {
		return fechaXDate;
	}
	/**
	 * @param fechaXDate the fechaXDate to set
	 */
	public void setFechaXDate(Date fechaXDate) {
		this.fechaXDate = fechaXDate;
	}
	/**
	 * @return the fechaVencimiento
	 */
	@Column(name = "DUE_BILL")
	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}
	/**
	 * @param fechaVencimiento the fechaVencimiento to set
	 */
	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	/**
	 * @return the fechaEfectiva
	 */
	@Column(name = "FECHA_EFECTIVA")
	public Date getFechaEfectiva() {
		return fechaEfectiva;
	}
	/**
	 * @param fechaEfectiva the fechaEfectiva to set
	 */
	public void setFechaEfectiva(Date fechaEfectiva) {
		this.fechaEfectiva = fechaEfectiva;
	}
	/**
	 * @return the importeGRSS
	 */
	@Column(name = "RATIO_BRUTO")
	public BigDecimal getImporteGRSS() {
		return importeGRSS;
	}
	/**
	 * @param importeGRSS the importeGRSS to set
	 */
	public void setImporteGRSS(BigDecimal importeGRSS) {
		this.importeGRSS = importeGRSS;
	}
	/**
	 * @return the ratioAdicional
	 */
	@Column(name = "RATIO_ADICIONAL")
	public String getRatioAdicional() {
		return ratioAdicional;
	}
	/**
	 * @param ratioAdicional the ratioAdicional to set
	 */
	public void setRatioAdicional(String ratioAdicionales) {
		this.ratioAdicional = ratioAdicionales;
		if (ratioAdicional != null) {
			String[] arregloLink = ratioAdicional.split("</br>");
			if (arregloLink != null && arregloLink.length > 0) {
				this.ratioAdicionalLst = new ArrayList<String>();
				this.ratioAdicionalLst = Arrays.asList(arregloLink);
			}
		}
	}
	/**
	 * @return the importeNETT
	 */
	@Column(name = "RATIO_NETO")
	public BigDecimal getImporteNETT() {
		return importeNETT;
	}
	/**
	 * @param importeNETT the importeNETT to set
	 */
	public void setImporteNETT(BigDecimal importeNETT) {
		this.importeNETT = importeNETT;
	}
	/**
	 * @return the divisa
	 */
	@Column(name = "DIVISA")
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
	 * @return the importeWITL
	 */
	@Column(name = "RETENCION")
	public BigDecimal getImporteWITL() {
		return importeWITL;
	}
	/**
	 * @param importeWITL the importeWITL to set
	 */
	public void setImporteWITL(BigDecimal importeWITL) {
		this.importeWITL = importeWITL;
	}
	/**
	 * @return the importeFEE
	 */
	@Column(name = "COMISION")
	public BigDecimal getImporteFEE() {
		return importeFEE;
	}
	/**
	 * @param importeFEE the importeFEE to set
	 */
	public void setImporteFEE(BigDecimal importeFEE) {
		this.importeFEE = importeFEE;
	}
	/**
	 * @return the idBoveda
	 */
	
	/**
	 * @return the subtipoDerecho
	 */
	@Column(name = "SUBTIPO_DERECHO")
	public String getSubtipoDerecho() {
		return subtipoDerecho;
	}
	/**
	 * @param subtipoDerecho the subtipoDerecho to set
	 */
	public void setSubtipoDerecho(String subtipoDerecho) {
		this.subtipoDerecho = subtipoDerecho;
		if (subtipoDerecho != null) {
			String[] sub = subtipoDerecho.split("//");
			if (sub != null && sub.length > 1) {
				valor1 = sub[0];
				valor2 = sub[1];
			}
		}
	}
	/**
	 * @return the boveda
	 */
	@Column(name = "BOVEDA")
	public String getBoveda() {
		return boveda;
	}
	/**
	 * @param boveda the boveda to set
	 */
	public void setBoveda(String boveda) {
		this.boveda = boveda;
	}
	/**
	 * @return the edoFunc
	 */
	@Column(name = "FUNCTION_M")
	public String getEdoFunc() {
		return edoFunc;
	}
	/**
	 * @param edoFunc the edoFunc to set
	 */
	public void setEdoFunc(String edoFunc) {
		this.edoFunc = edoFunc;
	}
	/**
	 * @return the edoProc
	 */
	@Column(name = "ESTADO_PROC")
	public String getEdoProc() {
		return edoProc;
	}
	/**
	 * @param edoProc the edoProc to set
	 */
	public void setEdoProc(String edoProc) {
		this.edoProc = edoProc;
	}
	
	/** 
	 * @return the tipoPagoCAEVDescripcion
	 */
	@Column(name = "CAEV_DESCRIPCION")
	public String getTipoPagoCAEVDescripcion() {
		return tipoPagoCAEVDescripcion;
	}
	/**
	 * @param tipoPagoCAEVDescripcion the tipoPagoCAEVDescripcion to set
	 */
	public void setTipoPagoCAEVDescripcion(String tipoPagoCAEVDescripcion) {
		this.tipoPagoCAEVDescripcion = tipoPagoCAEVDescripcion;
	}
	/**
	 * @return the tipoPagoCAMVDescripcion
	 */
	@Column(name = "CAMV_DESCRIPCION")
	public String getTipoPagoCAMVDescripcion() {
		return tipoPagoCAMVDescripcion;
	}
	/**
	 * @param tipoPagoCAMVDescripcion the tipoPagoCAMVDescripcion to set
	 */
	public void setTipoPagoCAMVDescripcion(String tipoPagoCAMVDescripcion) {
		this.tipoPagoCAMVDescripcion = tipoPagoCAMVDescripcion;
	}
	/**
	 * @return the valor1
	 */
	@Transient
	public String getValor1() {
		return valor1;
	}
	/**
	 * @param valor1 the valor1 to set
	 */
	public void setValor1(String valor1) {
		this.valor1 = valor1;
	}
	/**
	 * @return the valor2
	 */
	@Transient
	public String getValor2() {
		return valor2;
	}
	/**
	 * @param valor2 the valor2 to set
	 */
	public void setValor2(String valor2) {
		this.valor2 = valor2;
	}
	/**
	 * @return the narrativa
	 */
	@Transient
	public String getNarrativa() {
		return narrativa;
	}
	/**
	 * @param narrativa the narrativa to set
	 */
	public void setNarrativa(String narrativa) {
		this.narrativa = narrativa;
	}
	
	/**
	 * @return the ratioAdicionalLst
	 */
	@Transient
	public List<String> getRatioAdicionalLst() {
		return ratioAdicionalLst;
	}
	/**
	 * @param ratioAdicionalLst the ratioAdicionalLst to set
	 */
	public void setRatioAdicionalLst(List<String> ratioAdicionalLst) {
		this.ratioAdicionalLst = ratioAdicionalLst;
	}
	
	@Transient
	public String getRatioAdicionalFormatoReporte(){		
		return this.ratioAdicional != null ? this.ratioAdicional.replaceAll("</br>", " ") : null;
	}
    
}

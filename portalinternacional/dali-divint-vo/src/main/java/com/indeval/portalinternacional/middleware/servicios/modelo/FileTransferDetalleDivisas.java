package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portaldali.persistence.modelo.Divisa;

@Entity
@Table(name = "T_FILETRANSFER_DETALLE_DIVISAS_INT")
@SequenceGenerator(name = "SEQ_FILETRANSFER_DETALLE_DIVISAS_INT", sequenceName = "SEQ_FILETRANSFER_DETALLE_DIVISAS_INT", allocationSize = 1, initialValue = 1)
public class FileTransferDetalleDivisas implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public Long idFileTransferDetalleDivisasInt;
	public FileTransferDivisas fileTransferDivisas;
	public String folioInstitucion;
	public Divisa divisa;
	public Boveda boveda;
	public String cuenta;
	public BigDecimal montoBruto;
	public BigDecimal retencion;
	public BigDecimal comision;
	public BigDecimal depositoAjuste;
	public BigDecimal retiroAjuste;
	public BigDecimal montoNeto;
	public String concepto;
	public String referencia;
	public Long estatusDivisas;
	public String comentarios;
    
    @Id
    @Column(name = "ID_FILETRANSFER_DETALLE_DIVISAS_INT", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FILETRANSFER_DETALLE_DIVISAS_INT")
	public Long getIdFileTransferDetalleDivisasInt() {
		return idFileTransferDetalleDivisasInt;
	}
	
	public void setIdFileTransferDetalleDivisasInt(Long idFileTransferDetalleDivisasInt) {
		this.idFileTransferDetalleDivisasInt = idFileTransferDetalleDivisasInt;
	}
	
	@OneToOne
	@JoinColumn(name = "ID_FILETRANSFER_DIVISAS_INT", unique = true, nullable = false)
	public FileTransferDivisas getFileTransferDivisas() {
		return fileTransferDivisas;
	}

	public void setFileTransferDivisas(FileTransferDivisas fileTransferDivisas) {
		this.fileTransferDivisas = fileTransferDivisas;
	}

	@Column(name = "FOLIO_INSTITUCION", unique = false, nullable = false)
	public String getFolioInstitucion() {
		return folioInstitucion;
	}
	
	public void setFolioInstitucion(String folioInstitucion) {
		this.folioInstitucion = folioInstitucion;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_DIVISA", nullable = false)
	public Divisa getDivisa() {
		return divisa;
	}
	
	public void setDivisa(Divisa divisa) {
		this.divisa = divisa;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_BOVEDA", nullable = false)
	public Boveda getBoveda() {
		return boveda;
	}
	
	public void setBoveda(Boveda boveda) {
		this.boveda = boveda;
	}
	
	@Column(name = "CUENTA", unique = false, nullable = false)
	public String getCuenta() {
		return cuenta;
	}
	
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}
	
	@Column(name = "MONTO_BRUTO", unique = false, nullable = true)
	public BigDecimal getMontoBruto() {
		return montoBruto;
	}
	
	public void setMontoBruto(BigDecimal montoBruto) {
		this.montoBruto = montoBruto;
	}
	
	@Column(name = "RETENCION", unique = false, nullable = true)
	public BigDecimal getRetencion() {
		return retencion;
	}
	
	public void setRetencion(BigDecimal retencion) {
		this.retencion = retencion;
	}
	
	@Column(name = "COMISION", unique = false, nullable = true)
	public BigDecimal getComision() {
		return comision;
	}
	
	public void setComision(BigDecimal comision) {
		this.comision = comision;
	}
	
	@Column(name = "DEPOSITO_AJUSTE", unique = false, nullable = true)
	public BigDecimal getDepositoAjuste() {
		return depositoAjuste;
	}
	
	public void setDepositoAjuste(BigDecimal depositoAjuste) {
		this.depositoAjuste = depositoAjuste;
	}
	
	@Column(name = "RETIRO_AJUSTE", unique = false, nullable = true)
	public BigDecimal getRetiroAjuste() {
		return retiroAjuste;
	}
	
	public void setRetiroAjuste(BigDecimal retiroAjuste) {
		this.retiroAjuste = retiroAjuste;
	}
	
	@Column(name = "MONTO_NETO", unique = false, nullable = false)
	public BigDecimal getMontoNeto() {
		return montoNeto;
	}
	
	public void setMontoNeto(BigDecimal montoNeto) {
		this.montoNeto = montoNeto;
	}
	
	@Column(name = "CONCEPTO", unique = false, nullable = false)
	public String getConcepto() {
		return concepto;
	}
	
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	
	@Column(name = "REFERENCIA", unique = false, nullable = false)
	public String getReferencia() {
		return referencia;
	}
	
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	
	@Column(name = "ID_ESTATUS", unique = false, nullable = true)
	public Long getEstatusDivisas() {
		return estatusDivisas;
	}
	
	public void setEstatusDivisas(Long estatusDivisas) {
		this.estatusDivisas = estatusDivisas;
	}
	
	@Column(name = "COMENTARIOS", unique = false, nullable = true)
	public String getComentarios() {
		return comentarios;
	}
	
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
	
}

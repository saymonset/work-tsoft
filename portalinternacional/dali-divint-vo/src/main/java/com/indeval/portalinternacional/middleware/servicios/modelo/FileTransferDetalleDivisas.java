package com.indeval.portalinternacional.middleware.servicios.modelo;

import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portaldali.persistence.modelo.Divisa;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Clob;
import java.util.Date;

@Entity
@Table(name = "T_FILETRANSFER_DETALLE_DIVISAS_INT")
@SequenceGenerator(name = "SEQ_FILETRANSFER_DETALLE_DIVISAS_INT", sequenceName = "SEQ_FILETRANSFER_DETALLE_DIVISAS_INT", allocationSize = 1, initialValue = 1)
public class FileTransferDetalleDivisas implements Serializable {

	private static final long serialVersionUID = 1L;

	public Long idFileTransferDetalleDivisasInt;
	public FileTransferDivisas fileTransferDivisas;
	public String claveTipoInstitucion;
	public String folioInstitucion;
	public Divisa divisa;
	public Boveda boveda;
	public BigDecimal retencion;
	public BigDecimal comision;
	public BigDecimal depositoAjuste;
	public BigDecimal retiroAjuste;
	public BigDecimal montoNeto;
	public String concepto;
	public String referencia;
	public EstatusDivisas estatusDivisas;
	public String comentarios;
	public String camposError;
	public Date fechaRegistro;
	public Long idInstitucion;
	public Long idFileTransferDivisas;
	private Clob error;
	public Long estatusDetalle;
    @Id
    @Column(name = "ID_FILETRANSFER_DETALLE_DIVISAS_INT", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FILETRANSFER_DETALLE_DIVISAS_INT")
	public Long getIdFileTransferDetalleDivisasInt() {
		return idFileTransferDetalleDivisasInt;
	}

	public void setIdFileTransferDetalleDivisasInt(Long idFileTransferDetalleDivisasInt) {
		this.idFileTransferDetalleDivisasInt = idFileTransferDetalleDivisasInt;
	}

	@ManyToOne
	@JoinColumn(name = "ID_FILETRANSFER_DIVISAS_INT", unique = true, nullable = false, insertable = false,updatable = false)
	public FileTransferDivisas getFileTransferDivisas() {
		return fileTransferDivisas;
	}

	public void setFileTransferDivisas(FileTransferDivisas fileTransferDivisas) {
		this.fileTransferDivisas = fileTransferDivisas;
	}

	@Column(name = "CLAVE_TIPO_INSTITUCION", unique = false, nullable = true)
	public String getClaveTipoInstitucion() {
		return claveTipoInstitucion;
	}

	public void setClaveTipoInstitucion(String claveTipoInstitucion) {
		this.claveTipoInstitucion = claveTipoInstitucion;
	}

	@Column(name = "FOLIO_INSTITUCION", unique = false, nullable = true)
	public String getFolioInstitucion() {
		return folioInstitucion;
	}

	public void setFolioInstitucion(String folioInstitucion) {
		this.folioInstitucion = folioInstitucion;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_DIVISA", nullable = true)
	public Divisa getDivisa() {
		return divisa;
	}

	public void setDivisa(Divisa divisa) {
		this.divisa = divisa;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_BOVEDA", nullable = true)
	public Boveda getBoveda() {
		return boveda;
	}

	public void setBoveda(Boveda boveda) {
		this.boveda = boveda;
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

	@Column(name = "MONTO_NETO", unique = false, nullable = true)
	public BigDecimal getMontoNeto() {
		return montoNeto;
	}

	public void setMontoNeto(BigDecimal montoNeto) {
		this.montoNeto = montoNeto;
	}

	@Column(name = "CONCEPTO", unique = false, nullable = true)
	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	@Column(name = "REFERENCIA", unique = false, nullable = true)
	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_ESTATUS", nullable = true)
	public EstatusDivisas getEstatusDivisas() {
		return estatusDivisas;
	}

	public void setEstatusDivisas(EstatusDivisas estatusDivisas) {
		this.estatusDivisas = estatusDivisas;
	}

	@Column(name = "COMENTARIOS", unique = false, nullable = true)
	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	@Column(name = "CAMPOS_ERROR", unique = false, nullable = true)
	public String getCamposError() {
		return camposError;
	}

	public void setCamposError(String camposError) {
		this.camposError = camposError;
	}

	@Column(name = "FECHA_REGIS", unique = false, nullable = true)
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	@Column(name = "ID_INSTITUCION", unique = false, nullable = true)
	public Long getIdInstitucion() {
		return idInstitucion;
	}

	public void setIdInstitucion(Long idInstitucion) {
		this.idInstitucion = idInstitucion;
	}



	@Column(name = "ID_FILETRANSFER_DIVISAS_INT")
	public Long getIdFileTransferDivisas() {
		return idFileTransferDivisas;
	}

	public void setIdFileTransferDivisas(Long idFileTransferDivisas) {
		this.idFileTransferDivisas = idFileTransferDivisas;
	}



	@Column(name = "ERROR")
	public Clob getError() {
		return error;
	}
	/**
	 * @param error
	 */
	public void setError(Clob error) {
		this.error = error;
	}

	public Long getEstatusDetalle() {
		return estatusDetalle;
	}

	public void setEstatusDetalle(Long estatusDetalle) {
		this.estatusDetalle = estatusDetalle;
	}
}
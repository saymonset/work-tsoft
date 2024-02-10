package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Modelo para la configuracion de: CSDR
 * @author smoreno
 *
 */
@Entity
@Table(name = "T_CONFIG_CSDR")
@SequenceGenerator(name = "foliador", sequenceName = "C_CONFIG_CSDR_ID", allocationSize = 1, initialValue = 1)
public class SettlementDisciplineRegime implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idConfigCsdr;
	private Long idCuentaBoveda;
	private Long cuenta;
	private String detalleCustodio;
	private Boolean holdAndRelease;
	private Boolean partialSettlement;
	private Boolean bilateralCancellation;
	private Date fechaAlta;
	private Date fechaUltimaModificacion;
	private Date fechaAutorizacion;
	private Long idInstitucion;
	
	private String folioInstitucion;
	private String nombreCortoinstitucion;
	
	/**
	 * @return the idConfigCsdr
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "ID_CONFIG_CSDR", unique = true, nullable = false)
	public Long getIdConfigCsdr() {
		return idConfigCsdr;
	}
	/**
	 * @param idConfigCsdr the idConfigCsdr to set
	 */
	public void setIdConfigCsdr(Long idConfigCsdr) {
		this.idConfigCsdr = idConfigCsdr;
	}
	/**
	 * @return the idCuentaBoveda
	 */
	@Column(name = "ID_CUENTA_BOVEDA", nullable = false)
	public Long getIdCuentaBoveda() {
		return idCuentaBoveda;
	}
	/**
	 * @param idCuentaBoveda the idCuentaBoveda to set
	 */
	public void setIdCuentaBoveda(Long idCuentaBoveda) {
		this.idCuentaBoveda = idCuentaBoveda;
	}
	/**
	 * @return the cuenta
	 */
	 @Column(name = "CUENTA", nullable = false)
	public Long getCuenta() {
		return cuenta;
	}
	/**
	 * @param cuenta the cuenta to set
	 */
	public void setCuenta(Long cuenta) {
		this.cuenta = cuenta;
	}
	/**
	 * @return the detalleCustodio
	 */
	@Column(name = "DETALLE_CUSTODIO")
	public String getDetalleCustodio() {
		return detalleCustodio;
	}
	/**
	 * @param detalleCustodio the detalleCustodio to set
	 */
	public void setDetalleCustodio(String detalleCustodio) {
		this.detalleCustodio = detalleCustodio;
	}
	/**
	 * @return the holdAndRelease
	 */
	@Column(name = "HOLD_RELEASE", nullable = false)
	public Boolean getHoldAndRelease() {
		return holdAndRelease;
	}
	/**
	 * @param holdAndRelease the holdAndRelease to set
	 */
	public void setHoldAndRelease(Boolean holdAndRelease) {
		this.holdAndRelease = holdAndRelease;
	}
	/**
	 * @return the partialSettlement
	 */
	@Column(name = "PARTIAL_SETTLEMENT", nullable = false)
	public Boolean getPartialSettlement() {
		return partialSettlement;
	}
	/**
	 * @param partialSettlement the partialSettlement to set
	 */
	public void setPartialSettlement(Boolean partialSettlement) {
		this.partialSettlement = partialSettlement;
	}
	/**
	 * @return the bilateralCancellation
	 */
	@Column(name = "BILATERAL_CANCELLATION", nullable = false)
	public Boolean getBilateralCancellation() {
		return bilateralCancellation;
	}
	/**
	 * @param bilateralCancellation the bilateralCancellation to set
	 */
	public void setBilateralCancellation(Boolean bilateralCancellation) {
		this.bilateralCancellation = bilateralCancellation;
	}
	/**
	 * @return the fechaAlta
	 */
	@Column(name = "FECHA_ALTA", nullable = false)
	public Date getFechaAlta() {
		return fechaAlta;
	}
	/**
	 * @param fechaAlta the fechaAlta to set
	 */
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	/**
	 * @return the fechaUltimaModificacion
	 */
	@Column(name = "FECHA_ULTIMA_MODIFICACION")
	public Date getFechaUltimaModificacion() {
		return fechaUltimaModificacion;
	}
	/**
	 * @param fechaUltimaModificacion the fechaUltimaModificacion to set
	 */
	public void setFechaUltimaModificacion(Date fechaUltimaModificacion) {
		this.fechaUltimaModificacion = fechaUltimaModificacion;
	}
	/**
	 * @return the fechaAutorizacion
	 */
	@Column(name = "FECHA_AUTORIZACION")
	public Date getFechaAutorizacion() {
		return fechaAutorizacion;
	}
	/**
	 * @param fechaAutorizacion the fechaAutorizacion to set
	 */
	public void setFechaAutorizacion(Date fechaAutorizacion) {
		this.fechaAutorizacion = fechaAutorizacion;
	}
	/**
	 * @return the idInstitucion
	 */
	public Long getIdInstitucion() {
		return idInstitucion;
	}
	/**
	 * @param idInstitucion the idInstitucion to set
	 */
	public void setIdInstitucion(Long idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	
	/**
	 * @return the folioInstitucion
	 */
	@Transient
	public String getFolioInstitucion() {
		return folioInstitucion;
	}
	/**
	 * @param folioInstitucion the folioInstitucion to set
	 */
	public void setFolioInstitucion(String folioInstitucion) {
		this.folioInstitucion = folioInstitucion;
	}
	/**
	 * @return the nombreCortoinstitucion
	 */
	@Transient
	public String getNombreCortoinstitucion() {
		return nombreCortoinstitucion;
	}
	/**
	 * @param nombreCortoinstitucion the nombreCortoinstitucion to set
	 */
	public void setNombreCortoinstitucion(String nombreCortoinstitucion) {
		this.nombreCortoinstitucion = nombreCortoinstitucion;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SettlementDisciplineRegime [idConfigCsdr=" + idConfigCsdr + ", idCuentaBoveda=" + idCuentaBoveda
				+ ", cuenta=" + cuenta + ", detalleCustodio=" + detalleCustodio + ", holdAndRelease=" + holdAndRelease
				+ ", partialSettlement=" + partialSettlement + ", bilateralCancellation=" + bilateralCancellation
				+ ", fechaAlta=" + fechaAlta + ", fechaUltimaModificacion=" + fechaUltimaModificacion + "]";
	}

}

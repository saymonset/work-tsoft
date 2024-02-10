package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Modelo para la configuracion de: CSDR
 * @author smoreno
 *
 */
@Entity
@Table(name = "T_LIQUIDACION_PARCIAL_MOI")
@SequenceGenerator(name = "foliador", sequenceName = "T_LIQ_PARCIAL_MOI_SEQ", allocationSize = 1, initialValue = 1)
public class LiquidacionParcialMoi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idLiquidacionParcial;
	private Long folioControl;
	private Long idInstruccionLiquidacion;
	private Long folioControlLiquidacion;
	private BigInteger parcialLiquidado;
	private BigInteger remanente;
	private BigDecimal parcialLiquidadoEfectivo;
	private BigDecimal remanenteEfectivo;
	private BigDecimal parcialEfectivoPrevio;
	private BigInteger parcialLiquidadoPrevio;
	private Date fechaConfirmacion;
	private Date fechaLiquidacion;
	private Long numeroLiquidacion;
	private EstatusOperacion estatusOperacion;
	private boolean libera;
	private boolean cancela;
    /* Indica la peticion del usuario para cancelar la operacion */
    private boolean cancelo = true;
    private boolean autorizada = false;
    private boolean actualizaCambio;
    private String estadoNuevo;
    private BitacoraOperacionesSic bitacoraOperacionesSic;
    private boolean autorizo = false;
	
	/**
	 * 
	 */
	public LiquidacionParcialMoi() {
		super();
	}
	/**
	 * @param idLiquidacionParcial
	 * @param folioControl
	 * @param idInstruccionLiquidacion
	 * @param parcialLiquidado
	 * @param remanente
	 * @param parcialLiquidadoEfectivo
	 * @param remanenteEfectivo
	 * @param parcialEfectivoPrevio
	 * @param parcialLiquidadoPrevio
	 * @param fechaConfirmacion
	 * @param fechaLiquidacion
	 * @param numeroLiquidacion
	 * @param estatusOperacion
	 * @param libera
	 * @param cancela
	 */
	public LiquidacionParcialMoi(Long idLiquidacionParcial, Long folioControl, Long idInstruccionLiquidacion,
			BigInteger parcialLiquidado, BigInteger remanente, BigDecimal parcialLiquidadoEfectivo,
			BigDecimal remanenteEfectivo, BigDecimal parcialEfectivoPrevio, BigInteger parcialLiquidadoPrevio,
			Date fechaConfirmacion, Date fechaLiquidacion, Long numeroLiquidacion, EstatusOperacion estatusOperacion) {
		super();
		this.idLiquidacionParcial = idLiquidacionParcial;
		this.folioControl = folioControl;
		this.idInstruccionLiquidacion = idInstruccionLiquidacion;
		this.parcialLiquidado = parcialLiquidado;
		this.remanente = remanente;
		this.parcialLiquidadoEfectivo = parcialLiquidadoEfectivo;
		this.remanenteEfectivo = remanenteEfectivo;
		this.parcialEfectivoPrevio = parcialEfectivoPrevio;
		this.parcialLiquidadoPrevio = parcialLiquidadoPrevio;
		this.fechaConfirmacion = fechaConfirmacion;
		this.fechaLiquidacion = fechaLiquidacion;
		this.numeroLiquidacion = numeroLiquidacion;
		this.estatusOperacion = estatusOperacion;
		this.libera = Boolean.FALSE;
		this.cancela = Boolean.FALSE;
	}
	/**
	 * @return the idLiquidacionParcial
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "ID_LIQUIDACION_PARCIAL_MOI", unique = true, nullable = false)
	public Long getIdLiquidacionParcial() {
		return idLiquidacionParcial;
	}
	/**
	 * @param idLiquidacionParcial the idLiquidacionParcial to set
	 */
	public void setIdLiquidacionParcial(Long idLiquidacionParcial) {
		this.idLiquidacionParcial = idLiquidacionParcial;
	}
	/**
	 * @return the folioControl
	 */
	@Column(name = "FOLIO_CONTROL")
	public Long getFolioControl() {
		return folioControl;
	}
	/**
	 * @param folioControl the folioControl to set
	 */
	public void setFolioControl(Long folioControl) {
		this.folioControl = folioControl;
	}
	/**
	 * @return the idInstruccionLiquidacion
	 */
	@Column(name = "ID_INSTRUCCION_LIQUIDACION")
	public Long getIdInstruccionLiquidacion() {
		return idInstruccionLiquidacion;
	}
	/**
	 * @param idInstruccionLiquidacion the idInstruccionLiquidacion to set
	 */
	public void setIdInstruccionLiquidacion(Long idInstruccionLiquidacion) {
		this.idInstruccionLiquidacion = idInstruccionLiquidacion;
	}
	/**
	 * @return the folioControlLiquidacion
	 */
	@Column(name = "FOLIO_CONTROL_LIQ")
	public Long getFolioControlLiquidacion() {
		return folioControlLiquidacion;
	}
	/**
	 * @param folioControlLiquidacion the folioControlLiquidacion to set
	 */
	public void setFolioControlLiquidacion(Long folioControlLiquidacion) {
		this.folioControlLiquidacion = folioControlLiquidacion;
	}
	/**
	 * @return the parcialLiquidado
	 */
	@Column(name = "PARCIAL_LIQUIDADO")
	public BigInteger getParcialLiquidado() {
		return parcialLiquidado;
	}
	/**
	 * @param parcialLiquidado the parcialLiquidado to set
	 */
	public void setParcialLiquidado(BigInteger parcialLiquidado) {
		this.parcialLiquidado = parcialLiquidado;
	}
	
	/**
	 * @return the remanente
	 */
	@Column(name = "REMANENTE")
	public BigInteger getRemanente() {
		return remanente;
	}
	/**
	 * @param remanente the remanente to set
	 */
	public void setRemanente(BigInteger remanente) {
		this.remanente = remanente;
	}
	/**
	 * @return the parcialLiquidadoEfectivo
	 */
	@Column(name = "PARCIAL_LIQUIDADO_EFECTIVO")
	public BigDecimal getParcialLiquidadoEfectivo() {
		return parcialLiquidadoEfectivo;
	}
	/**
	 * @param parcialLiquidadoEfectivo the parcialLiquidadoEfectivo to set
	 */
	public void setParcialLiquidadoEfectivo(BigDecimal parcialLiquidadoEfectivo) {
		this.parcialLiquidadoEfectivo = parcialLiquidadoEfectivo;
	}
	/**
	 * @return the remanenteEfectivo
	 */
	@Column(name = "REMANENTE_EFECTIVO")
	public BigDecimal getRemanenteEfectivo() {
		return remanenteEfectivo;
	}
	/**
	 * @param remanenteEfectivo the remanenteEfectivo to set
	 */
	public void setRemanenteEfectivo(BigDecimal remanenteEfectivo) {
		this.remanenteEfectivo = remanenteEfectivo;
	}
	
	/**
	 * @return the parcialEfectivoPrevio
	 */
	@Column(name = "PARCIAL_EFECTIVO_PREVIO")
	public BigDecimal getParcialEfectivoPrevio() {
		return parcialEfectivoPrevio;
	}
	/**
	 * @param parcialEfectivoPrevio the parcialEfectivoPrevio to set
	 */
	public void setParcialEfectivoPrevio(BigDecimal parcialEfectivoPrevio) {
		this.parcialEfectivoPrevio = parcialEfectivoPrevio;
	}
	/**
	 * @return the parcialLiquidadoPrevio
	 */
	@Column(name = "PARCIAL_LIQUIDADO_PREVIO")
	public BigInteger getParcialLiquidadoPrevio() {
		return parcialLiquidadoPrevio;
	}
	/**
	 * @param parcialLiquidadoPrevio the parcialLiquidadoPrevio to set
	 */
	public void setParcialLiquidadoPrevio(BigInteger parcialLiquidadoPrevio) {
		this.parcialLiquidadoPrevio = parcialLiquidadoPrevio;
	}
	/**
	 * @return the fechaConfirmacion
	 */
	@Column(name = "FECHA_HORA_CF")
	public Date getFechaConfirmacion() {
		return fechaConfirmacion;
	}
	/**
	 * @param fechaConfirmacion the fechaConfirmacion to set
	 */
	public void setFechaConfirmacion(Date fechaConfirmacion) {
		this.fechaConfirmacion = fechaConfirmacion;
	}
	/**
	 * @return the fechaLiquidacion
	 */
	@Column(name = "FECHA_HORA_LQ")
	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}
	/**
	 * @param fechaLiquidacion the fechaLiquidacion to set
	 */
	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}
	/**
	 * @return the numeroLiquidacion
	 */
	@Column(name = "NUMERO_LIQUIDACION")
	public Long getNumeroLiquidacion() {
		return numeroLiquidacion;
	}

	/**
	 * @param numeroLiquidacion the numeroLiquidacion to set
	 */
	public void setNumeroLiquidacion(Long numeroLiquidacion) {
		this.numeroLiquidacion = numeroLiquidacion;
	}
	/**
	 * @return the estatusOperacion
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_ESTATUS_OPERACION", nullable = false)
	public EstatusOperacion getEstatusOperacion() {
		return estatusOperacion;
	}
	/**
	 * @param estatusOperacion the estatusOperacion to set
	 */
	public void setEstatusOperacion(EstatusOperacion estatusOperacion) {
		this.estatusOperacion = estatusOperacion;
	}
	/**
	 * @return the libera
	 */
	@Transient
	public boolean isLibera() {
		return libera;
	}
	/**
	 * @param libera the libera to set
	 */
	public void setLibera(boolean libera) {
		this.libera = libera;
	}
	/**
	 * @return the cancela
	 */
	@Transient
	public boolean isCancela() {
		return cancela;
	}
	/**
	 * @param cancela the cancela to set
	 */
	public void setCancela(boolean cancela) {
		this.cancela = cancela;
	}
	
	/**
	 * @return the cancelo
	 */
	@Transient
	public boolean isCancelo() {
		return cancelo;
	}
	/**
	 * @param cancelo the cancelo to set
	 */
	public void setCancelo(boolean cancelo) {
		this.cancelo = cancelo;
	}
	/**
	 * @return the actualizaCambio
	 */
	@Transient
	public boolean isActualizaCambio() {
		return actualizaCambio;
	}
	/**
	 * @param actualizaCambio the actualizaCambio to set
	 */
	public void setActualizaCambio(boolean actualizaCambio) {
		this.actualizaCambio = actualizaCambio;
	}
	/**
	 * @return the estadoNuevo
	 */
	@Transient
	public String getEstadoNuevo() {
		return estadoNuevo;
	}
	/**
	 * @param estadoNuevo the estadoNuevo to set
	 */
	public void setEstadoNuevo(String estadoNuevo) {
		this.estadoNuevo = estadoNuevo;
	}
	/**
	 * @return the autorizada
	 */
	@Transient
	public boolean isAutorizada() {
		return autorizada;
	}
	/**
	 * @param autorizada the autorizada to set
	 */
	public void setAutorizada(boolean autorizada) {
		this.autorizada = autorizada;
	}
    @Transient
    public BitacoraOperacionesSic getBitacoraOperacionesSic() {
        return this.bitacoraOperacionesSic;
    }

    public void setBitacoraOperacionesSic(final BitacoraOperacionesSic bitacoraOperacionesSic) {
        this.bitacoraOperacionesSic = bitacoraOperacionesSic;
    }
	/**
	 * @return the autorizo
	 */
    @Transient
	public boolean isAutorizo() {
		return autorizo;
	}
	/**
	 * @param autorizo the autorizo to set
	 */
	public void setAutorizo(boolean autorizo) {
		this.autorizo = autorizo;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LiquidacionParcialMoi [idLiquidacionParcial=" + idLiquidacionParcial + ", folioControl=" + folioControl
				+ ", idInstruccionLiquidacion=" + idInstruccionLiquidacion + ", folioControlLiquidacion="
				+ folioControlLiquidacion + ", parcialLiquidado=" + parcialLiquidado + ", remanente=" + remanente
				+ ", parcialLiquidadoEfectivo=" + parcialLiquidadoEfectivo + ", remanenteEfectivo=" + remanenteEfectivo
				+ ", parcialEfectivoPrevio=" + parcialEfectivoPrevio + ", parcialLiquidadoPrevio="
				+ parcialLiquidadoPrevio + ", fechaConfirmacion=" + fechaConfirmacion + ", fechaLiquidacion="
				+ fechaLiquidacion + ", numeroLiquidacion=" + numeroLiquidacion + ", estatusOperacion="
				+ estatusOperacion + ", libera=" + libera + ", cancela=" + cancela + ", cancelo=" + cancelo
				+ ", autorizada=" + autorizada + ", actualizaCambio=" + actualizaCambio + ", estadoNuevo=" + estadoNuevo
				+ ", bitacoraOperacionesSic=" + bitacoraOperacionesSic + ", autorizo=" + autorizo + "]";
	}
	

}

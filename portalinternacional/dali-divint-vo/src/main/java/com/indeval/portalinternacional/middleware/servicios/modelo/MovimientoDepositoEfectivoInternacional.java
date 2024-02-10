// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.servicios.modelo;

import com.indeval.portaldali.persistence.modelo.CuentaNombrada;
import com.indeval.portalinternacional.middleware.servicios.vo.MovimientoEfectivoInternacionalVO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Enrique Guzman
 *
 */
@Entity
@Table(name = "T_MOVIMIENTO_DEPOSITO_EFECTIVO_INT")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_MOV_DEPOSITO_EFECT_INT", allocationSize = 1)
public class MovimientoDepositoEfectivoInternacional implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "ID_MOVIMIENTO_DEPOSITO_EFECTIVO_INT")
	private Long idMovimientoEfectivoInt;

	@Column(name = "ID_CUENTA")
	private Long idCuenta;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_CUENTA", insertable = false, updatable = false)
	private CuentaNombrada cuenta;

	@Column(name = "ID_DIVISA")
	private Long idDivisa;

	@Column(name = "ID_BOVEDA")
	private Long idBoveda;

	@Column(name = "SALDO_DISPONIBLE")
	private Double saldoDisponible;

	@Column(name = "IMPORTE_TRASPASAR")
	private Double importeTraspasar;

	@Column(name = "SALDO_EFECTIVO")
	private Double saldoEfectivo;

	@Column(name = "FECHA_ALTA")
	private Date fechaAlta;

	@Column(name = "FECHA_ULT_MODIFICACION")
	private Date fechaUltimaModificacion;
	
	@Column(name = "FECHA_LIQUIDACION")
	private Date fechaLiquidacion;

	@Column(name = "ESTADO_MOV_EFECTIVO_INT")
	private Long estadoMovEfectivoInt;

	@Column(name = "ESTADO_LIQ_INDEVAL")
	private Long estadoLiqIndeval;
	
	@Column(name = "FOLIO_CONTROL")
	private Long folioControl;
		
	@Column(name = "ID_CATBIC")
	private Long idCatbic;

	@Column(name = "REFERENCIA_RELACIONADA")
	private String referenciaRelacionada;
	
	public Long getIdMovimientoEfectivoInt() {
		return idMovimientoEfectivoInt;
	}

	public void setIdMovimientoEfectivoInt(Long idMovimientoEfectivoInt) {
		this.idMovimientoEfectivoInt = idMovimientoEfectivoInt;
	}

	public Long getIdCuenta() {
		return idCuenta;
	}

	public void setIdCuenta(Long idCuenta) {
		this.idCuenta = idCuenta;
	}

	public CuentaNombrada getCuenta() {
		return cuenta;
	}

	public void setCuenta(CuentaNombrada cuenta) {
		this.cuenta = cuenta;
	}

	public Long getIdDivisa() {
		return idDivisa;
	}

	public void setIdDivisa(Long idDivisa) {
		this.idDivisa = idDivisa;
	}

	public Long getIdBoveda() {
		return idBoveda;
	}

	public void setIdBoveda(Long idBoveda) {
		this.idBoveda = idBoveda;
	}

	public Double getSaldoDisponible() {
		return saldoDisponible;
	}

	public void setSaldoDisponible(Double saldoDisponible) {
		this.saldoDisponible = saldoDisponible;
	}

	public Double getImporteTraspasar() {
		return importeTraspasar;
	}

	public void setImporteTraspasar(Double importeTraspasar) {
		this.importeTraspasar = importeTraspasar;
	}

	public Double getSaldoEfectivo() {
		return saldoEfectivo;
	}

	public void setSaldoEfectivo(Double saldoEfectivo) {
		this.saldoEfectivo = saldoEfectivo;
	}

	public Long getEstadoMovEfectivoInt() {
		return estadoMovEfectivoInt;
	}

	public void setEstadoMovEfectivoInt(Long estadoMovEfectivoInt) {
		this.estadoMovEfectivoInt = estadoMovEfectivoInt;
	}

	public Long getEstadoLiqIndeval() {
		return estadoLiqIndeval;
	}

	public void setEstadoLiqIndeval(Long estadoLiqIndeval) {
		this.estadoLiqIndeval = estadoLiqIndeval;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaUltimaModificacion(Date fechaUltimaModificacion) {
		this.fechaUltimaModificacion = fechaUltimaModificacion;
	}

	public Date getFechaUltimaModificacion() {
		return fechaUltimaModificacion;
	}
	
	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}
	
	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	public Long getFolioControl() {
		return folioControl;
	}

	public void setFolioControl(Long folioControl) {
		this.folioControl = folioControl;
	}
	
	public void setIdCatbic(Long idCatbic) {
		this.idCatbic = idCatbic;
	}
	
	public Long getIdCatbic() {
		return idCatbic;
	}

	public void actualizaMovimientoEfectivo(MovimientoEfectivoInternacionalVO vo) {
		this.estadoMovEfectivoInt = vo.getEstadoMovimiento();
		this.estadoLiqIndeval = vo.getEstadoLiqIndeval();		
	}

	public String getReferenciaRelacionada() {
		return referenciaRelacionada;
	}

	public void setReferenciaRelacionada(String referenciaRelacionada) {
		this.referenciaRelacionada = referenciaRelacionada;
	}

}

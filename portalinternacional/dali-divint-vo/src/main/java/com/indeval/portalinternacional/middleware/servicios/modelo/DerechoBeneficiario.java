package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;
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

import com.indeval.portaldali.persistence.modelo.CuentaNombrada;

@Entity
@Table(name="T_DERECHO_BENEFICIARIO")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_DERECHO_BENEFICIARIO", allocationSize = 1, initialValue = 1)
public class DerechoBeneficiario implements Serializable, Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3237419388803718923L;

	private Long idDerechoBeneficiario;
	
	private Long idDerechoCapital;
	
	private CuentaNombrada cuentaNombrada;
	
	private Long asignacion;
	
	private EstatusDerecho estatusDerecho;		
	
	private Boolean eliminado;

	private Date fechaEliminacion;
	
	private Beneficiario beneficiario;
	
	private Boolean procesado;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "ID_DERECHO_BENEFICIARIO", unique = true, nullable = false)
	public Long getIdDerechoBeneficiario() {
		return idDerechoBeneficiario;
	}
	
	@Column(name = "ID_DERECHO_CAPITAL", unique = false, nullable = false)
	public Long getIdDerechoCapital() {
		return idDerechoCapital;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_CUENTA_NOMBRADA", nullable = true)
	public CuentaNombrada getCuentaNombrada() {
		return cuentaNombrada;
	}

	@Column(name = "ASIGNACION", unique = false, nullable = true)
	public Long getAsignacion() {
		return asignacion;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_ESTATUS_DERECHO",nullable = false)
	public EstatusDerecho getEstatusDerecho() {
		return estatusDerecho;
	}
	
	@Column(name = "ELIMINADO", unique = false, nullable = false)
	public Boolean getEliminado() {
		return eliminado;
	}

	@Column(name = "FECHA_ELIMINACION", unique = false, nullable = true)
	public Date getFechaEliminacion() {
		return fechaEliminacion;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_BENEFICIARIO", nullable = true)
	public Beneficiario getBeneficiario() {
		return beneficiario;
	}
	
	@Column(name = "PROCESADA", unique = false, nullable = false)
	public Boolean getProcesado() {
		return procesado;
	}
	
	public void setIdDerechoCapital(Long idDerechoCapital) {
		this.idDerechoCapital = idDerechoCapital;
	}

	public void setAsignacion(Long asignacion) {
		this.asignacion = asignacion;
	}

	public void setEstatusDerecho(EstatusDerecho estatusDerecho) {
		this.estatusDerecho = estatusDerecho;
	}

	public void setIdDerechoBeneficiario(Long idDerechoBeneficiario) {
		this.idDerechoBeneficiario = idDerechoBeneficiario;
	}

	public void setEliminado(Boolean eliminado) {
		this.eliminado = eliminado;
	}

	public void setFechaEliminacion(Date fechaEliminacion) {
		this.fechaEliminacion = fechaEliminacion;
	}

	public void setBeneficiario(Beneficiario beneficiario) {
		this.beneficiario = beneficiario;
	}

	public void setCuentaNombrada(CuentaNombrada cuentaNombrada) {
		this.cuentaNombrada = cuentaNombrada;
	}

	public void setProcesado(Boolean procesado) {
		this.procesado = procesado;
	}

	@Override
	public DerechoBeneficiario clone() throws CloneNotSupportedException{
		Beneficiario beneficiario = null;
		CuentaNombrada cuentaNombrada = null;
		EstatusDerecho estatusDerecho = new EstatusDerecho();
		DerechoBeneficiario derechoBeneficiario = new DerechoBeneficiario();
		derechoBeneficiario.setAsignacion(this.asignacion);
		derechoBeneficiario.setEliminado(this.eliminado);
		derechoBeneficiario.setFechaEliminacion(this.fechaEliminacion);		
		derechoBeneficiario.setProcesado(this.procesado);
		
		if(this.beneficiario != null){
			beneficiario = new Beneficiario();
			beneficiario.setIdBeneficiario(this.beneficiario.getIdBeneficiario());
		}
		
		derechoBeneficiario.setBeneficiario(beneficiario);
		
		if(this.cuentaNombrada != null){
			cuentaNombrada = new CuentaNombrada();
			cuentaNombrada.setIdCuentaNombrada(this.cuentaNombrada.getIdCuentaNombrada());
		}
		
		derechoBeneficiario.setCuentaNombrada(cuentaNombrada);
		
		estatusDerecho.setIdEstatusDerecho(this.getEstatusDerecho().getIdEstatusDerecho());
		derechoBeneficiario.setEstatusDerecho(estatusDerecho);
		derechoBeneficiario.setIdDerechoCapital(this.idDerechoCapital);
		return derechoBeneficiario;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DerechoBeneficiario [idDerechoBeneficiario=" + idDerechoBeneficiario + ", idDerechoCapital="
				+ idDerechoCapital + ", cuentaNombrada=" + cuentaNombrada + ", asignacion=" + asignacion
				+ ", estatusDerecho=" + estatusDerecho + ", eliminado=" + eliminado + ", fechaEliminacion="
				+ fechaEliminacion + ", beneficiario=" + beneficiario + ", procesado=" + procesado + "]";
	}
}

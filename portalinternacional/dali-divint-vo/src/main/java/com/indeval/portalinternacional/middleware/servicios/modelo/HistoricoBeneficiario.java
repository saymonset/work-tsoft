/*
 * Copyrigth (c) 2008 Bursatec. All Rights Reserved.
 */
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "T_HISTORICO_BENEF")
@SequenceGenerator(name = "foliador", sequenceName = "ID_HISTORICO_BENEF_SEQ", allocationSize = 1, initialValue = 1)
public class HistoricoBeneficiario implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long idHistoricoBeneficiario;
	private Beneficiario beneficiario;
	private Long idBeneficiario;
	private Date fechaAlta;
	private StatusBeneficiario statusAnterior;
	private StatusBeneficiario statusNuevo;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "id_historico_benef", unique = true, nullable = false)
	public Long getIdHistoricoBeneficiario() {
		return idHistoricoBeneficiario;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_beneficiario", nullable = false, updatable = false, insertable = false)
	public Beneficiario getBeneficiario() {
		return beneficiario;
	}

	@Column(name = "id_beneficiario", nullable = false)
	public Long getIdBeneficiario() {
		return idBeneficiario;
	}

	@Column(name = "fecha_alta", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getFechaAlta() {
		return fechaAlta;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_status_anterior", nullable = false)
	public StatusBeneficiario getStatusAnterior() {
		return statusAnterior;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_status_nuevo", nullable = false)
	public StatusBeneficiario getStatusNuevo() {
		return statusNuevo;
	}

	public void setIdHistoricoBeneficiario(Long idHistoricoBeneficiario) {
		this.idHistoricoBeneficiario = idHistoricoBeneficiario;
	}

	public void setBeneficiario(Beneficiario beneficiario) {
		this.beneficiario = beneficiario;
	}

	public void setIdBeneficiario(Long idBeneficiario) {
		this.idBeneficiario = idBeneficiario;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public void setStatusAnterior(StatusBeneficiario statusAnterior) {
		this.statusAnterior = statusAnterior;
	}

	public void setStatusNuevo(StatusBeneficiario statusNuevo) {
		this.statusNuevo = statusNuevo;
	}
}

/*
 * Copyrigth (c) 2008 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;

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

@Entity
@Table(name = "C_CUSTODIO_TIPO_BENEF")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_ID_CUST_TIPO_BENEF", allocationSize = 1, initialValue = 1)
public class CustodioTipoBenef implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idCustodioTipoBenef;
	private TipoBeneficiario tipoBeneficiario;
	private String formato;
	private Long idCuentaNombrada;
	private Double porcentajeRetencion;

	/**
	 * Default constructor.
	 */
	public CustodioTipoBenef() {}

	/**
	 * Constructor.
	 * @param tipoBeneficiario
	 * @param idCuentaNombrada
	 * @param formato
	 * @param porcentajeRetencion
	 */
	public CustodioTipoBenef(TipoBeneficiario tipoBeneficiario, Long idCuentaNombrada, String formato, Double porcentajeRetencion) {
	    this.tipoBeneficiario = tipoBeneficiario;
	    this.idCuentaNombrada = idCuentaNombrada;
	    this.formato = formato;
	    this.porcentajeRetencion = porcentajeRetencion;
	}

	/**
	 * 
	 * @return idCustodioTipoBenef
	 */
	@Id
	@Column(name = "id_custodio_tipo_benef")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	public Long getIdCustodioTipoBenef() {
		return idCustodioTipoBenef;
	}

	/**
	 * 
	 * @return tipoBeneficiario
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_tipo_beneficiario", unique = false, nullable = false)
	public TipoBeneficiario getTipoBeneficiario() {
		return tipoBeneficiario;
	}

	/**
	 * @return the formato
	 */
	@Column(name = "formato", unique = false, nullable = false)
	public String getFormato() {
		return formato;
	}
	

	/**
	 * @return the cuentaNombrada
	 */
	@Column(name = "ID_CUENTA_NOMBRADA", nullable = false, unique = false)
	public Long getIdCuentaNombrada() {
		return idCuentaNombrada;
	}

	/**
	 * @return the porcentajeRetencion
	 */
	@Column(name = "PORCENTAJE_RETENCION", nullable = true, unique = false)
	public Double getPorcentajeRetencion() {
		return porcentajeRetencion;
	}


	public void setIdCustodioTipoBenef(Long idCustodioTipoBenef) {
		this.idCustodioTipoBenef = idCustodioTipoBenef;
	}

	public void setTipoBeneficiario(TipoBeneficiario tipoBeneficiario) {
		this.tipoBeneficiario = tipoBeneficiario;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}
	
	public void setIdCuentaNombrada(Long idCuentaNombrada) {
		this.idCuentaNombrada = idCuentaNombrada;
	}

	public void setPorcentajeRetencion(Double porcentajeRetencion) {
		this.porcentajeRetencion = porcentajeRetencion;
	}

}

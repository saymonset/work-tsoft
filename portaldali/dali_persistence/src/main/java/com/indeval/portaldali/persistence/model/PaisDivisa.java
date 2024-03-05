/*
 * Copyrigth (c) 2009 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigInteger;

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

/**
 * Cata&acute;logo con la relacion de las divisas por pais
 * 
 * C_PAIS_DIVISA
 *
 * @author  Maria C. Buendia
 * @version 1.0
 */
@Entity
@Table(name="C_PAIS_DIVISA")
@SequenceGenerator(name = "SEQ_PaisDivisa", sequenceName = "C_PAIS_DIVISA_SEQ",allocationSize = 1,initialValue = 1)
public class PaisDivisa implements Serializable {
	
	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Id asignado para cada estado */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PaisDivisa")
	@Column(name = "ID_PAIS_DIVISA", nullable = false)
	private BigInteger idPaisDivisa;

	/** pais */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PAIS", nullable = false)
	private Pais pais;
	
	/** divisa */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DIVISA", nullable = false)
	private Divisa divisa;

	/** de union europea */
	@Column(name = "UNION_EUROPEA")
	private Integer deUnionEuropea;
	
	public BigInteger getIdPaisDivisa() {
		return idPaisDivisa;
	}

	public void setIdPaisDivisa(BigInteger idPaisDivisa) {
		this.idPaisDivisa = idPaisDivisa;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public Divisa getDivisa() {
		return divisa;
	}

	public void setDivisa(Divisa divisa) {
		this.divisa = divisa;
	}

	public Integer getDeUnionEuropea() {
		return deUnionEuropea;
	}

	public void setDeUnionEuropea(Integer deUnionEuropea) {
		this.deUnionEuropea = deUnionEuropea;
	}
	
}

package com.indeval.portalinternacional.middleware.servicios.modelo;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "C_CUSTODIO_BOVEDA_INT")
@SequenceGenerator(name = "custodiaBovedaSeq", sequenceName = "SEQ_CUSTODIO_BOVEDA_INT", allocationSize = 1, initialValue = 1)
public class CustodioBoveda implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Long idCustodio;
	private Integer idBoveda;	

	public CustodioBoveda() {
		
	}
	
	public CustodioBoveda(Long idCustodio, Integer idBoveda) {
		this.idCustodio = idCustodio;
		this.idBoveda = idBoveda;
	}
	
	
	@Id		
	@Column(name = "ID_CUSTODIO_BOVEDA_INT")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "custodiaBovedaSeq")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "ID_CUSTODIO")
	public Long getCustodio() {
		return idCustodio;
	}
	
	public void setCustodio(Long idCustodio) {
		this.idCustodio = idCustodio;
	}
	
	@Column(name = "ID_BOVEDA")
	public Integer getBoveda() {
		return idBoveda;
	}
	
	public void setBoveda(Integer idBoveda) {
		this.idBoveda = idBoveda;
	}

}

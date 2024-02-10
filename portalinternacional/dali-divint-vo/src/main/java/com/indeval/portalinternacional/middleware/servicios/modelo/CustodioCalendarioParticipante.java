package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "R_CUSTODIO_CAL_PART_INT")
public class CustodioCalendarioParticipante implements Serializable{
	
	/**
	 * Constante de serializacion por default
	 */
	private static final long serialVersionUID = 1L;

	private Long idCustodioCalPart;
	
	private Long idCustodio;

	/**
	 * @return Long
	 */
	@Id
	@Column(name = "ID_CUSTODIO_CAL_PART_INT", unique = true, nullable = false)
	public Long getIdCustodioCalPart() {
		return idCustodioCalPart;
	}

	public void setIdCustodioCalPart(Long idCustodioCalPart) {
		this.idCustodioCalPart = idCustodioCalPart;
	}
	
	/**
	 * @return Long
	 */
	@Column(name = "ID_CUSTODIO", unique = true, nullable = false)
	public Long getIdCustodio() {
		return idCustodio;
	}

	public void setIdCustodio(Long idCustodio) {
		this.idCustodio = idCustodio;
	}
	
	
	

}

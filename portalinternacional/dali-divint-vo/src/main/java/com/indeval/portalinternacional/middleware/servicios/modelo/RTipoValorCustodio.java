/**
 * Copyright (c) 2020 Bursatec. All Rights Reserved
 */
package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Clase que representa un registro de la tabla 'R_TIPO_VALOR_CUSTODIO'.
 */
@Entity
@Table(name = "R_TIPO_VALOR_CUSTODIO")
public class RTipoValorCustodio implements Serializable {

	/**
	 * Version de la clase para serializar
	 */
	private static final long serialVersionUID = 131L;

	@Id
	@Column(name = "TIPO_VALOR", unique = false, nullable = false)
	private String tipoValor;

	@Column(name = "ID_CUSTODIO", unique = false, nullable = false)
	private Long idCustodio;

	public RTipoValorCustodio() { }

	public RTipoValorCustodio(String tipoValor, Long idCustodio) {
	    this.tipoValor = tipoValor;
	    this.idCustodio = idCustodio;
	}

    public String getTipoValor() {
        return tipoValor;
    }

    public void setTipoValor(String tipoValor) {
        this.tipoValor = tipoValor;
    }

    public Long getIdCustodio() {
        return idCustodio;
    }

    public void setIdCustodio(Long idCustodio) {
        this.idCustodio = idCustodio;
    }

}

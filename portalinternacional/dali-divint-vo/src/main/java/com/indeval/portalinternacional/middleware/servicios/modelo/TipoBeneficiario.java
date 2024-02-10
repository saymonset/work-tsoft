/*
 * Copyrigth (c) 2008 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
@Entity
@Table(name = "C_TIPOS_BENEFICIARIOS")
public class TipoBeneficiario implements Serializable {
        
    /**
    * Constante de serializacion por default 
    */
    private static final long serialVersionUID = 1L;

	private Long idTipoBeneficiario;

	private String descTipoBeneficiario;

	private String statusTipoBenef;

    private String formato;
		
	/**
	 * @return Long
	 */
	@Id
	@Column(name = "id_tipo_beneficiario", unique = true, nullable = false)
	public Long getIdTipoBeneficiario() {
		return idTipoBeneficiario;
	}

	/**
	 * @return String
	 */
	@Column(name = "desc_tipo_beneficiario", unique = false, nullable = false)
	public String getDescTipoBeneficiario() {
		return descTipoBeneficiario;
	}
		
	/**
	 * @return String
	 */
	@Column(name = "status_tipo_benef", unique = false, nullable = true)
	public String getStatusTipoBenef() {
		return statusTipoBenef;
	}

    /**
     * @return String
     */
    @Column(name = "formato", unique = false, nullable = true)
	public String getFormato() {
        return formato;
    }

    /**
	 * @param idTipoBeneficiario
	 */
	public void setIdTipoBeneficiario(Long idTipoBeneficiario) {
		this.idTipoBeneficiario = idTipoBeneficiario;
	}

	/**
	 * @param descTipoBeneficiario
	 */
	public void setDescTipoBeneficiario(String descTipoBeneficiario) {
		this.descTipoBeneficiario = descTipoBeneficiario;
	}

	/**
	 * @param statusTipoBenef
	 */
	public void setStatusTipoBenef(String statusTipoBenef) {
		this.statusTipoBenef = statusTipoBenef;
	}

    public void setFormato(String formato) {
        this.formato = formato;
    }

}

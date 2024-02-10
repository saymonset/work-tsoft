package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;
import java.util.List;

public class ConsultaNombreBeneficiarioParam implements Serializable {

	private static final long serialVersionUID = -4281364609401465169L;
	
	private String nombres;
	/** Apellido Paterno */
	private String apellidoPaterno;
	/** Apellido Materno */
	private String apellidoMaterno;
	/** Razon Social */
	private String razonSocial;
	/** Persono Fisica o Moral */
	private boolean personaFisica;
	/** Persono RFC */
	private String RFC;
	/** Tipo Beneficiario */
	private Long idTipoBeneficiario;
	/** Custodio */
	private Long custodio;
    /** Lista con id de beneficiario */
    private List<Long> listaBeneficiarios;

	/**
	 * @return the nombres
	 */
	public String getNombres() {
		return nombres;
	}

	/**
	 * @param nombres
	 *            the nombres to set
	 */
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	/**
	 * @return the apellidoPaterno
	 */
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	/**
	 * @param apellidoPaterno
	 *            the apellidoPaterno to set
	 */
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	/**
	 * @return the apellidoMaterno
	 */
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	/**
	 * @param apellidoMaterno
	 *            the apellidoMaterno to set
	 */
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	/**
	 * @return the razonSocial
	 */
	public String getRazonSocial() {
		return razonSocial;
	}

	/**
	 * @param razonSocial
	 *            the razonSocial to set
	 */
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	/**
	 * @return the personaFisica
	 */
	public boolean isPersonaFisica() {
		return personaFisica;
	}

	/**
	 * @param personaFisica
	 *            the personaFisica to set
	 */
	public void setPersonaFisica(boolean personaFisica) {
		this.personaFisica = personaFisica;
	}

	/**
	 * @return the rFC
	 */
	public String getRFC() {
		return RFC;
	}

	/**
	 * @param rfc
	 *            the rFC to set
	 */
	public void setRFC(String rfc) {
		RFC = rfc;
	}

	/**
	 * @return the idTipoBeneficiario
	 */
	public Long getIdTipoBeneficiario() {
		return idTipoBeneficiario;
	}

	/**
	 * @param idTipoBeneficiario
	 *            the idTipoBeneficiario to set
	 */
	public void setIdTipoBeneficiario(Long idTipoBeneficiario) {
		this.idTipoBeneficiario = idTipoBeneficiario;
	}

	/**
	 * @return the custodio
	 */
	public Long getCustodio() {
		return custodio;
	}

	/**
	 * @param custodio the custodio to set
	 */
	public void setCustodio(Long custodio) {
		this.custodio = custodio;
	}

    /**
     * Lista con id de beneficiario
     * @return the listaBeneficiarios
     */
    public List<Long> getListaBeneficiarios() {
        return listaBeneficiarios;
    }

    /**
     * Lista con id de beneficiario
     * @param listaBeneficiarios the listaBeneficiarios to set
     */
    public void setListaBeneficiarios(List<Long> listaBeneficiarios) {
        this.listaBeneficiarios = listaBeneficiarios;
    }

}

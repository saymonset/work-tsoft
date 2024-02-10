package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;

public class ConsultaNombreBeneficiarioVO implements Serializable {

	private static final long serialVersionUID = -3239278497030861637L;
	
	private Long idBeneficiario;
	private String nombres;
	private String razonSocial;
	private boolean personaFisica;
	private String formato;
	private String descStatus;
	private String rfcW8BEN;
	private String rfcW8IMY;
	private Long idCuentaNombrada;
	private String descTipoBeneficiario;

	/**
	 * Constructor por default
	 */
	public ConsultaNombreBeneficiarioVO() {
		
	}
	
	/**
	 * Constructor para la consulta
	 * 
	 * @param idBeneficiario
	 * @param nombres
	 * @param razonSocial
	 * @param personaFisica
	 * @param formato
	 * @param descStatus
	 * @param rfcW8BEN
	 * @param rfcW8IMY
	 */
	public ConsultaNombreBeneficiarioVO(Long idBeneficiario, String nombres,
            String apellidosPaterno, String apellidosMaterno,
			String razonSocial, boolean personaFisica, String formato,
			String descStatus, String rfcW8BEN, String rfcW8IMY,
			Long idCuentaNombrada, String descTipoBeneficiario) {
		super();
		this.idBeneficiario = idBeneficiario;
		this.nombres = nombres + " " + apellidosPaterno + " " + apellidosMaterno;
		this.razonSocial = razonSocial;
		this.personaFisica = personaFisica;
		this.formato = formato;
		this.descStatus = descStatus;
		this.rfcW8BEN = rfcW8BEN;
		this.rfcW8IMY = rfcW8IMY;
		this.idCuentaNombrada = idCuentaNombrada;
		this.descTipoBeneficiario = descTipoBeneficiario;
	}



	/**
	 * @return the idBeneficiario
	 */
	public Long getIdBeneficiario() {
		return idBeneficiario;
	}

	/**
	 * @param idBeneficiario the idBeneficiario to set
	 */
	public void setIdBeneficiario(Long idBeneficiario) {
		this.idBeneficiario = idBeneficiario;
	}

	/**
	 * @return the nombres
	 */
	public String getNombres() {
		return nombres;
	}

	/**
	 * @param nombres the nombres to set
	 */
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	/**
	 * @return the razonSocial
	 */
	public String getRazonSocial() {
		return razonSocial;
	}

	/**
	 * @param razonSocial the razonSocial to set
	 */
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	/**
	 * @return the isPersonaFisica
	 */
	public boolean isPersonaFisica() {
		return personaFisica;
	}

	/**
	 * @param isPersonaFisica the isPersonaFisica to set
	 */
	public void setPersonaFisica(boolean personaFisica) {
		this.personaFisica = personaFisica;
	}

	/**
	 * @param formato the formato to set
	 */
	public void setFormato(String formato) {
		this.formato = formato;
	}

	/**
	 * @return the formato
	 */
	public String getFormato() {
		return formato;
	}

	/**
	 * @param descStatus the descStatus to set
	 */
	public void setDescStatus(String descStatus) {
		this.descStatus = descStatus;
	}

	/**
	 * @return the descStatus
	 */
	public String getDescStatus() {
		return descStatus;
	}

	/**
	 * @return the rfcW8BEN
	 */
	public String getRfcW8BEN() {
		return rfcW8BEN;
	}

	/**
	 * @param rfcW8BEN the rfcW8BEN to set
	 */
	public void setRfcW8BEN(String rfcW8BEN) {
		this.rfcW8BEN = rfcW8BEN;
	}

	/**
	 * @return the rfcW8IMY
	 */
	public String getRfcW8IMY() {
		return rfcW8IMY;
	}

	/**
	 * @param rfcW8IMY the rfcW8IMY to set
	 */
	public void setRfcW8IMY(String rfcW8IMY) {
		this.rfcW8IMY = rfcW8IMY;
	}
	
	public String getRfc() {
		final Logger log = LoggerFactory.getLogger(this.getClass());
		log.info("FORMATO:[" + formato + "]");
		if(formato.equals("W8BEN")) {
			return rfcW8BEN;
		} else if(formato.equals("W8IMY")) {
			return rfcW8IMY;
		} else {
			return "";
		}
	}

	/**
	 * @param idCuentaNombrada the idCuentaNombrada to set
	 */
	public void setIdCuentaNombrada(Long idCuentaNombrada) {
		this.idCuentaNombrada = idCuentaNombrada;
	}

	/**
	 * @return the idCuentaNombrada
	 */
	public Long getIdCuentaNombrada() {
		return idCuentaNombrada;
	}

	/**
	 * @return the descTipoBeneficiario
	 */
	public String getDescTipoBeneficiario() {
		return descTipoBeneficiario;
	}

	/**
	 * @param descTipoBeneficiario the descTipoBeneficiario to set
	 */
	public void setDescTipoBeneficiario(String descTipoBeneficiario) {
		this.descTipoBeneficiario = descTipoBeneficiario;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(13, 23).append(idBeneficiario).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		boolean isEqual = false;
		if (obj instanceof Beneficiario) {
			Beneficiario beneficiario = (Beneficiario) obj;
			isEqual = new EqualsBuilder().append(idBeneficiario,
					beneficiario.getIdBeneficiario()).isEquals();
		}
		return isEqual;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("Id Beneficiario", idBeneficiario)
				.append("Nombre", nombres)
				.append("Razon Social", razonSocial)
				.append("Es persona fisica", personaFisica)
				.append("Formato", formato)
				.append("Status", descStatus)
				.append("RFC", getRfc())
				.append("RFC W8BEN", rfcW8BEN)
				.append("RFC W8IMY", rfcW8IMY)
				.append("Custodio", idCuentaNombrada)
				.append("Tipo Beneficiario", descTipoBeneficiario).toString();
	}

}

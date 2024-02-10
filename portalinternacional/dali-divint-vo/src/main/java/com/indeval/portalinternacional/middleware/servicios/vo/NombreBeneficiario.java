/*
 * Copyrigth (c) 2009 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Clase para representar el nombre del beenficiario con su ID
 * @author Rafael Ibarra Zendejas
 * @version 1.0
 */
public class NombreBeneficiario implements Serializable {
    private static final long serialVersionUID = 1L;
    /** Id del Beneficiario */
    private Long idBeneficiario;
    /** Nombre del Beneficiario */
    private String nombre;
    /** Apellido Paterno del Beneficiario */
    private String apellidoPaterno;
    /** Apellido Materno del Beneficiario */
	private String apellidoMaterno;
    /** Razon Social del Beneficiario */
	private String razonSocial;
    /** Indica si es persona fisica */
	private Boolean personaFisica;

    /** Constructor por default */
    public NombreBeneficiario() {
        super();
    }

    /**
     * Constructor con los parametros
     * @param idBeneficiario Id del Beneficiario
     * @param nombre Nombre del Beneficiario
     * @param apellidoPaterno Apellido Paterno del Beneficiario
     * @param razonSocial Razon Social del Beneficiario
     * @param apellidoMaterno Apellido Materno del Beneficiario
     * @param personaFisica Persona Fisica
     */
    public NombreBeneficiario(Long idBeneficiario, String nombre, String apellidoPaterno,
            String apellidoMaterno, String razonSocial, boolean personaFisica ) {
        this();
        this.idBeneficiario = idBeneficiario;
        this.personaFisica = personaFisica;
        if(personaFisica) {
            this.nombre = nombre;
            this.apellidoPaterno = apellidoPaterno;
            this.apellidoMaterno = apellidoMaterno;
        } else {
            this.razonSocial = razonSocial;
        }
    }



    /**
     * Id del Beneficiario
     * @return the idBeneficiario
     */
    public Long getIdBeneficiario() {
        return idBeneficiario;
    }

    /**
     * Id del Beneficiario
     * @param idBeneficiario the idBeneficiario to set
     */
    public void setIdBeneficiario(Long idBeneficiario) {
        this.idBeneficiario = idBeneficiario;
    }

    /**
     * Nombre del Beneficiario
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Nombre del Beneficiario
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Apellido Paterno del Beneficiario
     * @return the apellidoPaterno
     */
    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    /**
     * Apellido Paterno del Beneficiario
     * @param apellidoPaterno the apellidoPaterno to set
     */
    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    /**
     * Apellido Materno del Beneficiario
     * @return the apellidoMaterno
     */
    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    /**
     * Apellido Materno del Beneficiario
     * @param apellidoMaterno the apellidoMaterno to set
     */
    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    /**
     * Razon Social del Beneficiario
     * @return the razonSocial
     */
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     * Razon Social del Beneficiario
     * @param razonSocial the razonSocial to set
     */
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    /**
     * Indica si es persona fisica
     * @return the personaFisica
     */
    public Boolean getPersonaFisica() {
        return personaFisica;
    }

    /**
     * Indica si es persona fisica
     * @param personaFisica the personaFisica to set
     */
    public void setPersonaFisica(Boolean personaFisica) {
        this.personaFisica = personaFisica;
    }

    /**
     * Compara una persona fisisca
     * @param nombreB Nombre Beneficiario
     * @param apellidoPaternoB Apellido Paterno del Beneficiario
     * @param apellidoMaternoB Apellido Materno del Beneficiario
     * @return Si es igual o no
     */
    public boolean comparaPersonaFisica(String nombreB, String apellidoPaternoB, String apellidoMaternoB) {
        boolean retornoNombre = true, retornoApellidoP = true,
                retornoApellidoM = true;
        if (StringUtils.isNotBlank(nombreB)) {
            retornoNombre = StringUtils.contains(StringUtils.upperCase(nombre), StringUtils.upperCase(nombreB));
        }
        if (StringUtils.isNotBlank(apellidoPaternoB)) {
            retornoApellidoP = StringUtils.contains(StringUtils.upperCase(apellidoPaterno), StringUtils.upperCase(apellidoPaternoB));
        }
        if (StringUtils.isNotBlank(apellidoMaternoB)) {
            retornoApellidoM = StringUtils.contains(StringUtils.upperCase(apellidoMaterno), StringUtils.upperCase(apellidoMaternoB));
        }
        return retornoNombre && retornoApellidoP && retornoApellidoM;
    }

    /**
     * Compara una persona moral
     * @param razonSocialB Razon Social
     * @return Si es igual o no
     */
    public boolean comparaPersonaMoral(String razonSocialB) {
        boolean retorno = true;
        if (StringUtils.isNotBlank(razonSocialB)) {
            retorno = StringUtils.contains(StringUtils.upperCase(razonSocial), StringUtils.upperCase(razonSocialB));
        }
        return retorno;
    }

    public boolean comparaNombre(String nombreRazonSocial) {
        boolean retorno = true;
		nombreRazonSocial = StringUtils.deleteWhitespace(nombreRazonSocial);
        if (personaFisica) {
            String nombreCompleto = nombre + apellidoPaterno + apellidoMaterno;
			nombreCompleto = StringUtils.deleteWhitespace(nombreCompleto);
            retorno = StringUtils.contains(StringUtils.upperCase(nombreCompleto), StringUtils.upperCase(nombreRazonSocial));
        } else {
			String razonTemp = StringUtils.deleteWhitespace(razonSocial);
            retorno = StringUtils.contains(StringUtils.upperCase(razonTemp), StringUtils.upperCase(nombreRazonSocial));
        }
        return retorno;
    }

    @Override
    public boolean equals(Object obj) {
        boolean isEqual = false;
		if (obj != null &&
                obj instanceof NombreBeneficiario) {
			NombreBeneficiario nombreBeneficiario = (NombreBeneficiario) obj;
			isEqual = new EqualsBuilder()
				.append(idBeneficiario, nombreBeneficiario.getIdBeneficiario())
				.isEquals();
		}
		return isEqual;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(13, 23)
			.append(idBeneficiario)
			.toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("idBeneficiario",idBeneficiario).
                 append("nombre",nombre).
                append("apellidoPaterno",apellidoPaterno).
                append("apellidoMaterno",apellidoMaterno).
                append("razonSocial",razonSocial).
                append("personaFisica",personaFisica).
                toString();
    }
}

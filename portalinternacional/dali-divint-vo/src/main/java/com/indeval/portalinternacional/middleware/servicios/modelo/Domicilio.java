/*
 * Copyrigth (c) 2008 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.jasypt.hibernate.type.EncryptedStringType;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 * 
 */
@Entity
@Table(name = "T_DOMICILIOS_W")
@SequenceGenerator(name = "foliador", sequenceName = "ID_DOMICILIOS_W_SEQ", allocationSize = 1, initialValue = 1)
@TypeDefs({
	@TypeDef(name = "encryptedString5",
		typeClass = EncryptedStringType.class,
			parameters = {
				@Parameter(name = "encryptorRegisteredName", value = "strongHibernateStringEncryptor5")
			}
	),
	@TypeDef(name = "encryptedString6",
		typeClass = EncryptedStringType.class,
			parameters = {
				@Parameter(name = "encryptorRegisteredName", value = "strongHibernateStringEncryptor6")
			}
	),
	@TypeDef(name = "encryptedString7",
		typeClass = EncryptedStringType.class,
			parameters = {
				@Parameter(name = "encryptorRegisteredName", value = "strongHibernateStringEncryptor7")
			}
	),
	@TypeDef(name = "encryptedString8",
		typeClass = EncryptedStringType.class,
			parameters = {
				@Parameter(name = "encryptorRegisteredName", value = "strongHibernateStringEncryptor8")
			}
	),
	@TypeDef(name = "encryptedString9",
		typeClass = EncryptedStringType.class,
			parameters = {
				@Parameter(name = "encryptorRegisteredName", value = "strongHibernateStringEncryptor9")
			}
	),
	@TypeDef(name = "encryptedString10",
		typeClass = EncryptedStringType.class,
			parameters = {
				@Parameter(name = "encryptorRegisteredName", value = "strongHibernateStringEncryptor10")
			}
	),
	@TypeDef(name = "encryptedString11",
		typeClass = EncryptedStringType.class,
			parameters = {
				@Parameter(name = "encryptorRegisteredName", value = "strongHibernateStringEncryptor11")
			}
	)
})
public class Domicilio implements Serializable {

	/**
	 * Constante de serializacion por default
	 */
	private static final long serialVersionUID = 1L;

	private Long idDomicilio;

	private String street;

	private String outerNumber;

	private String interiorNumber;

	private String postalCode;

	private String cityTown;

	private String stateProvince;

	private String country;

	/**
	 * Constructor de la clase
	 */
	public Domicilio() {}

	/**
	 * Constructor de la clase
	 * @param idDomicilio
	 * @param street
	 * @param outerNumber
	 * @param interiorNumber
	 * @param postalCode
	 * @param cityTown
	 * @param stateProvince
	 * @param country
	 */
	public Domicilio(Long idDomicilio, String street, String outerNumber,
			String interiorNumber, String postalCode, String cityTown,
			String stateProvince, String country) {
		super();
		this.idDomicilio = idDomicilio;
		this.street = street;
		this.outerNumber = outerNumber;
		this.interiorNumber = interiorNumber;
		this.postalCode = postalCode;
		this.cityTown = cityTown;
		this.stateProvince = stateProvince;
		this.country = country;
	}

	/**
	 * @return Long
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "id_domicilio_w", unique = true, nullable = false)
	public Long getIdDomicilio() {
		return idDomicilio;
	}

	/**
	 * @return String
	 */
	@Column(name = "street", unique = false, nullable = true)
    @Type(type="encryptedString5")
	public String getStreet() {
		return street;
	}

	/**
	 * @return Long
	 */
	@Column(name = "outer_number", unique = false, nullable = true)
    @Type(type="encryptedString6")
	public String getOuterNumber() {
		return outerNumber;
	}

	/**
	 * @return Long
	 */
	@Column(name = "interior_number", unique = false, nullable = true)
    @Type(type="encryptedString7")
	public String getInteriorNumber() {
		return interiorNumber;
	}

	/**
	 * @return String
	 */
	@Column(name = "postal_code", unique = false, nullable = true)
    @Type(type="encryptedString8")
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * @return String
	 */
	@Column(name = "city_town", unique = false, nullable = true)
    @Type(type="encryptedString9")
	public String getCityTown() {
		return cityTown;
	}

	/**
	 * @return String
	 */
	@Column(name = "state_province", unique = false, nullable = true)
    @Type(type="encryptedString10")
	public String getStateProvince() {
		return stateProvince;
	}

	/**
	 * @return String
	 */
	@Column(name = "country", unique = false, nullable = true)
    @Type(type="encryptedString11")
	public String getCountry() {
		return country;
	}
	
	@Transient
	public String getCalleNumeros() {
		String retorno = "";
		if(StringUtils.isNotBlank(street)) {
			retorno += (street+" ");
		}
		if(StringUtils.isNotBlank(outerNumber)) {
			retorno += (outerNumber+" ");
		}
		if(StringUtils.isNotBlank(interiorNumber)) {
			retorno += interiorNumber;
		}
		return retorno;
	}
	
	@Transient
	public String getCiudadEstadoCodigo() {
		String retorno = "";
		if(StringUtils.isNotBlank(cityTown)) {
			retorno += (cityTown+" ");
		}
		if(StringUtils.isNotBlank(stateProvince)) {
			retorno += (stateProvince+" ");
		}
		if(StringUtils.isNotBlank(postalCode)) {
			retorno += postalCode;
		}
		return retorno;
	}

	@Transient
	public String getDireccionCompletaSinPais() {
		String retorno = "";
		retorno = retorno + getCalleNumeros() + " ";
		retorno = retorno + getCiudadEstadoCodigo();
		return retorno;
	}

	/**
	 * @param idDomicilio
	 */
	public void setIdDomicilio(Long idDomicilio) {
		this.idDomicilio = idDomicilio;
	}

	/**
	 * @param street
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @param outerNumber
	 */
	public void setOuterNumber(String outerNumber) {
		this.outerNumber = outerNumber;
	}

	/**
	 * @param interiorNumber
	 */
	public void setInteriorNumber(String interiorNumber) {
		this.interiorNumber = interiorNumber;
	}

	/**
	 * @param postalCode
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * @param cityTown
	 */
	public void setCityTown(String cityTown) {
		this.cityTown = cityTown;
	}

	/**
	 * @param stateProvince
	 */
	public void setStateProvince(String stateProvince) {
		this.stateProvince = stateProvince;
	}

	/**
	 * @param country
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	
	
	/**
	 * Método que pasa a mayusculas todos los campos del objeto
	 */
	public void domicilioAMayusculas() {
		this.cityTown = StringUtils.upperCase(this.cityTown);
		this.country = StringUtils.upperCase(this.country);
		this.interiorNumber = StringUtils.upperCase(this.interiorNumber);
		this.outerNumber = StringUtils.upperCase(this.outerNumber);
		this.postalCode = StringUtils.upperCase(this.postalCode);
		this.stateProvince = StringUtils.upperCase(this.stateProvince);
		this.street = StringUtils.upperCase(this.street);
	}
	
	
}

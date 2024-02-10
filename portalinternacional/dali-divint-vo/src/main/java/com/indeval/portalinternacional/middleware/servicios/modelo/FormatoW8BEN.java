/*
 * Copyrigth (c) 2008 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

 @Entity
 @Table(name="t_campos_formato_w8ben")
 @SequenceGenerator(name = "foliador", sequenceName = "ID_CAMPOS_FORMATO_W8BEN_SEQ", allocationSize = 1, initialValue = 1)
public class FormatoW8BEN implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idCamposFormatoW8ben;
    private Field3W8BEN field3;
    private Boolean field9OptionA;
    private String field9OptionACountry;
    private Boolean field9OptionB;
    private Boolean field9OptionC;
    private Boolean field9OptionD;
    private Boolean field9OptionE;
    private String field10Article;
    private String field10Rate;
    private String field10Type;
    private String field10Reasons;
    private Boolean field11;
    private String capacityActing;
    private String referenceNumbers;
	private String taxpayerIdNumb;
	private Long tipoTaxIdNumb;
	private String foreignTaxIdNumb;
    /** Indica si la direccion Postal esta deshabilitada para no validarla*/
	private boolean disabledDireccionPostal;
	/** Indica si US id number esta deshabilitado para no validarlo*/
	private boolean disabledUsIdNumber;
	/** Indica si reference number esta deshabilitado para no validarlo*/
	private boolean disabledreferenceNumber;
	
	private boolean disabledSection10;
	
	private String signer;
	
	private Date fechaNacimiento;

     @Id
     @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
     @Column(name ="id_campos_formato_w8ben", unique = false, nullable = false)
    public Long getIdCamposFormatoW8ben() {
        return idCamposFormatoW8ben;
    }

     @ManyToOne(fetch = FetchType.EAGER)
     @JoinColumn(name ="FIELD3", nullable = false)
    public Field3W8BEN getField3() {
        return field3;
    }

     @Column(name ="field9_option_a", unique = false, nullable = true)
    public Boolean getField9OptionA() {
        return field9OptionA;
    }

     @Column(name ="field9_option_a_country", unique = false, nullable = true)
    public String getField9OptionACountry() {
        return field9OptionACountry;
    }

     @Column(name ="field9_option_b", unique = false, nullable = true)
    public Boolean getField9OptionB() {
        return field9OptionB;
    }

     @Column(name ="field9_option_c", unique = false, nullable = true)
    public Boolean getField9OptionC() {
        return field9OptionC;
    }

     @Column(name ="field9_option_d", unique = false, nullable = true)
    public Boolean getField9OptionD() {
        return field9OptionD;
    }

     @Column(name ="field9_option_e", unique = false, nullable = true)
    public Boolean getField9OptionE() {
        return field9OptionE;
    }

     @Column(name ="field10_article", unique = false, nullable = true)
    public String getField10Article() {
        return field10Article;
    }

     @Column(name ="field10_rate", unique = false, nullable = true)
    public String getField10Rate() {
        return field10Rate;
    }

     @Column(name ="field10_type", unique = false, nullable = true)
    public String getField10Type() {
        return field10Type;
    }

     @Column(name ="field10_reasons", unique = false, nullable = true)
    public String getField10Reasons() {
        return field10Reasons;
    }

     @Column(name ="field11", unique = false, nullable = true)
    public Boolean getField11() {
        return field11;
    }

     @Column(name ="capacity_acting", unique = false, nullable = true)
    public String getCapacityActing() {
        return capacityActing;
    }
     
 	@Column(name = "reference_numbers", unique = false, nullable = true)
 	public String getReferenceNumbers() {
 		return referenceNumbers;
 	}

 	@Column(name = "taxpayer_id_numb", unique = false, nullable = true)
 	public String getTaxpayerIdNumb() {
 		return taxpayerIdNumb;
 	}

 	@Column(name = "tipo_tax_id_numb", unique = false, nullable = true)
 	public Long getTipoTaxIdNumb() {
 		return tipoTaxIdNumb;
 	}

 	@Column(name = "foreign_tax_id_numb", unique = false, nullable = true)
 	public String getForeignTaxIdNumb() {
 		return foreignTaxIdNumb;
 	}
     
 	/**
 	 * @return the disabledDireccionPostal
 	 */
 	@Transient
 	public boolean isDisabledDireccionPostal() {
 		return disabledDireccionPostal;
 	}
 	
 	/**
 	 * @return the disabledUsIdNumber
 	 */
 	@Transient
 	public boolean isDisabledUsIdNumber() {
 		return disabledUsIdNumber;
 	}

 	/**
 	 * @return the disabledreferenceNumber
 	 */
 	@Transient
 	public boolean isDisabledreferenceNumber() {
 		return disabledreferenceNumber;
 	}


    public void setIdCamposFormatoW8ben(Long idCamposFormatoW8ben) {
        this.idCamposFormatoW8ben = idCamposFormatoW8ben;
    }

    public void setField3(Field3W8BEN field3) {
        this.field3 = field3;
    }

    public void setField9OptionA(Boolean field9OptionA) {
        this.field9OptionA = field9OptionA;
    }

    public void setField9OptionACountry(String field9OptionACountry) {
        this.field9OptionACountry = field9OptionACountry;
    }

    public void setField9OptionB(Boolean field9OptionB) {
        this.field9OptionB = field9OptionB;
    }

    public void setField9OptionC(Boolean field9OptionC) {
        this.field9OptionC = field9OptionC;
    }

    public void setField9OptionD(Boolean field9OptionD) {
        this.field9OptionD = field9OptionD;
    }

    public void setField9OptionE(Boolean field9OptionE) {
        this.field9OptionE = field9OptionE;
    }

    public void setField10Article(String field10Article) {
        this.field10Article = field10Article;
    }

    public void setField10Rate(String field10Rate) {
        this.field10Rate = field10Rate;
    }

    public void setField10Type(String field10Type) {
        this.field10Type = field10Type;
    }

    public void setField10Reasons(String field10Reasons) {
        this.field10Reasons = field10Reasons;
    }

    public void setField11(Boolean field11) {
        this.field11 = field11;
    }

    public void setCapacityActing(String capacityActing) {
        this.capacityActing = capacityActing;
    }

	/**
	 * @param disabledDireccionPostal the disabledDireccionPostal to set
	 */
	public void setDisabledDireccionPostal(boolean disabledDireccionPostal) {
		this.disabledDireccionPostal = disabledDireccionPostal;
	}

	/**
	 * @param disabledUsIdNumber the disabledUsIdNumber to set
	 */
	public void setDisabledUsIdNumber(boolean disabledUsIdNumber) {
		this.disabledUsIdNumber = disabledUsIdNumber;
	}

	/**
	 * @param disabledreferenceNumber the disabledreferenceNumber to set
	 */
	
	public void setDisabledreferenceNumber(boolean disabledreferenceNumber) {
		this.disabledreferenceNumber = disabledreferenceNumber;
	}
	
	/**
	 * @param referenceNumbers
	 */
	public void setReferenceNumbers(String referenceNumbers) {
		this.referenceNumbers = referenceNumbers;
	}

	/**
	 * @param taxpayerIdNumb
	 */
	public void setTaxpayerIdNumb(String taxpayerIdNumb) {
		this.taxpayerIdNumb = taxpayerIdNumb;
	}

	/**
	 * @param tipoTaxIdNumb
	 */
	public void setTipoTaxIdNumb(Long tipoTaxIdNumb) {
		this.tipoTaxIdNumb = tipoTaxIdNumb;
	}

	/**
	 * @param foreignTaxIdNumb
	 */
	public void setForeignTaxIdNumb(String foreignTaxIdNumb) {
		this.foreignTaxIdNumb = foreignTaxIdNumb;
	}

	/**
	 * @return the disabledSection10
	 */
	@Transient
	public boolean isDisabledSection10() {
		return disabledSection10;
	}

	/**
	 * @param disabledSection10 the disabledSection10 to set
	 */
	public void setDisabledSection10(boolean disabledSection10) {
		this.disabledSection10 = disabledSection10;
	}

	/**
	 * @return the signer
	 */
	@Column(name = "signer", unique = false, nullable = true)
	public String getSigner() {
		return signer;
	}

	/**
	 * @param signer the signer to set
	 */
	public void setSigner(String signer) {
		this.signer = signer;
	}

	/**
	 * @return the fechaNacimiento
	 */
	@Column(name = "fecha_nacimiento", unique = false, nullable = true)
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	/**
	 * @param fechaNacimiento the fechaNacimiento to set
	 */
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
}

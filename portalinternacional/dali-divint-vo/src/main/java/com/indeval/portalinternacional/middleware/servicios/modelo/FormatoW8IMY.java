/*
 * Copyrigth (c) 2008 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;

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
import javax.persistence.Transient;

 @Entity
 @Table(name="t_campos_formato_w8imy")
 @SequenceGenerator(name = "foliador", sequenceName = "ID_CAMPOS_FORMATO_W8IMY_SEQ", allocationSize = 1, initialValue = 1)
public class FormatoW8IMY implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idCamposFormatoW8imy;
    private Field3W8IMY field3;
    private Boolean field9OptionA;
    private Boolean field9OptionB;
    private String field9OptionBLine;
    private Boolean field9OptionC;
    private String field9OptionCLine;
    private Boolean field10OptionA;
    private Boolean field10OptionB;
    private Boolean field11;
    private Boolean field12;
    private Boolean field13;
    private Boolean field14;
    private Boolean field15;
    private String referenceNumbers;
	private String taxpayerIdNumb;
	private Long tipoTaxIdNumb;
	private String foreignTaxIdNumb;
	/** Indica si la direccion Postal esta deshabilitada para no validarla*/
	private boolean disabledDireccionPostal;


     @Id
     @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
     @Column(name ="id_campos_formato_w8imy", unique = false, nullable = false)
    public Long getIdCamposFormatoW8imy() {
        return idCamposFormatoW8imy;
    }

     @ManyToOne(fetch = FetchType.EAGER)
     @JoinColumn(name ="field3", unique = false, nullable = true)
    public Field3W8IMY getField3() {
        return field3;
    }

     @Column(name ="field9_option_a", unique = false, nullable = true)
    public Boolean getField9OptionA() {
        return field9OptionA;
    }

     @Column(name ="field9_option_b", unique = false, nullable = true)
    public Boolean getField9OptionB() {
        return field9OptionB;
    }

     @Column(name ="field9_option_b_line", unique = false, nullable = true)
    public String getField9OptionBLine() {
        return field9OptionBLine;
    }

     @Column(name ="field9_option_c", unique = false, nullable = true)
    public Boolean getField9OptionC() {
        return field9OptionC;
    }

     @Column(name ="field9_option_c_line", unique = false, nullable = true)
    public String getField9OptionCLine() {
        return field9OptionCLine;
    }

     @Column(name ="field10_option_a", unique = false, nullable = true)
    public Boolean getField10OptionA() {
        return field10OptionA;
    }

     @Column(name ="field10_option_b", unique = false, nullable = true)
    public Boolean getField10OptionB() {
        return field10OptionB;
    }

     @Column(name ="field11", unique = false, nullable = true)
    public Boolean getField11() {
        return field11;
    }

     @Column(name ="field12", unique = false, nullable = true)
    public Boolean getField12() {
        return field12;
    }

     @Column(name ="field13", unique = false, nullable = true)
    public Boolean getField13() {
        return field13;
    }

     @Column(name ="field14", unique = false, nullable = true)
    public Boolean getField14() {
        return field14;
    }

     @Column(name ="field15", unique = false, nullable = true)
    public Boolean getField15() {
        return field15;
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


    public void setIdCamposFormatoW8imy(Long idCamposFormatoW8imy) {
        this.idCamposFormatoW8imy = idCamposFormatoW8imy;
    }

    public void setField3(Field3W8IMY field3) {
        this.field3 = field3;
    }

    public void setField9OptionA(Boolean field9OptionA) {
        this.field9OptionA = field9OptionA;
    }

    public void setField9OptionB(Boolean field9OptionB) {
        this.field9OptionB = field9OptionB;
    }

    public void setField9OptionBLine(String field9OptionBLine) {
        this.field9OptionBLine = field9OptionBLine;
    }

    public void setField9OptionC(Boolean field9OptionC) {
        this.field9OptionC = field9OptionC;
    }

    public void setField9OptionCLine(String field9OptionCLine) {
        this.field9OptionCLine = field9OptionCLine;
    }

    public void setField10OptionA(Boolean field10OptionA) {
        this.field10OptionA = field10OptionA;
    }

    public void setField10OptionB(Boolean field10OptionB) {
        this.field10OptionB = field10OptionB;
    }

    public void setField11(Boolean field11) {
        this.field11 = field11;
    }

    public void setField12(Boolean field12) {
        this.field12 = field12;
    }

    public void setField13(Boolean field13) {
        this.field13 = field13;
    }

    public void setField14(Boolean field14) {
        this.field14 = field14;
    }

    public void setField15(Boolean field15) {
        this.field15 = field15;
    }
    
	public void setReferenceNumbers(String referenceNumbers) {
		this.referenceNumbers = referenceNumbers;
	}

	public void setTaxpayerIdNumb(String taxpayerIdNumb) {
		this.taxpayerIdNumb = taxpayerIdNumb;
	}

	public void setTipoTaxIdNumb(Long tipoTaxIdNumb) {
		this.tipoTaxIdNumb = tipoTaxIdNumb;
	}

	public void setForeignTaxIdNumb(String foreignTaxIdNumb) {
		this.foreignTaxIdNumb = foreignTaxIdNumb;
	}
	
	/**
	 * @param disabledDireccionPostal the disabledDireccionPostal to set
	 */
	public void setDisabledDireccionPostal(boolean disabledDireccionPostal) {
		this.disabledDireccionPostal = disabledDireccionPostal;
	}
}

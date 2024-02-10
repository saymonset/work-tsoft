/*
 * Copyrigth (c) 2008 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

 @Entity
 @Table(name="C_CUSTODIO_TIPO_BENEF")
public class CustodioTipoBenef implements Serializable {

    private static final long serialVersionUID = 1L;

    private BigDecimal idCustodioTipoBenef;
    private BigDecimal idCatbic;
    private BigDecimal idTipoBeneficiario;


     @Id
     @Column(name ="id_custodio_tipo_benef", unique = false, nullable = false)
    public BigDecimal getIdCustodioTipoBenef() {
        return idCustodioTipoBenef;
    }

     @Column(name ="id_catbic", unique = false, nullable = false)
    public BigDecimal getIdCatbic() {
        return idCatbic;
    }

     @Column(name ="id_tipo_beneficiario", unique = false, nullable = false)
    public BigDecimal getIdTipoBeneficiario() {
        return idTipoBeneficiario;
    }


    public void setIdCustodioTipoBenef(BigDecimal idCustodioTipoBenef) {
        this.idCustodioTipoBenef = idCustodioTipoBenef;
    }

    public void setIdCatbic(BigDecimal idCatbic) {
        this.idCatbic = idCatbic;
    }

    public void setIdTipoBeneficiario(BigDecimal idTipoBeneficiario) {
        this.idTipoBeneficiario = idTipoBeneficiario;
    }
}

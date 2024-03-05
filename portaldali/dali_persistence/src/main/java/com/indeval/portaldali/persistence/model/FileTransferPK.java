/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
@Embeddable
public class FileTransferPK implements Serializable {

    /**
     * Constante de serializacion
     */
    private static final long serialVersionUID = 1L;
    
	
	@Column(name ="id_inst")
	private String idInst;

	@Column(name ="folio_inst")
    private String folioInst;

	@Column(name ="tipo_reg")
    private String tipoReg;
	
	@Column(name ="consec")
    private BigDecimal consec;
    
    /**
     * @hibernate.key-property
     *   column="id_inst"
     */
    public String getIdInst() {
        return idInst;
    }

    /**
     * @hibernate.key-property
     *   column="folio_inst"
     */
    public String getFolioInst() {
        return folioInst;
    }

    /**
     * @hibernate.key-property
     *   column="tipo_reg"
     */
    public String getTipoReg() {
        return tipoReg;
    }
    
    /**
     * @hibernate.key-property
     *   column="consec"
     */
    public BigDecimal getConsec() {
        return consec;
    }
    
    /**
     * @param idInst
     */
    public void setIdInst(String idInst) {
        this.idInst = idInst;
    }

    /**
     * @param folioInst
     */
    public void setFolioInst(String folioInst) {
        this.folioInst = folioInst;
    }

    /**
     * @param tipoReg
     */
    public void setTipoReg(String tipoReg) {
        this.tipoReg = tipoReg;
    }
    
    /**
     * @param consec
     */
    public void setConsec(BigDecimal consec) {
        this.consec = consec;
    }

}

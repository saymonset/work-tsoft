/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadocapitales;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ErroresFileTransferVO extends AbstractBaseDTO {

    /** Constante de serializacion */
    private static final long serialVersionUID = 1L;    

    private Date fechaRegistro;

    private String cadenaInformacion;

    private String descripcionError;

    private BigDecimal consec;

    private List datos;

    /**
     * @return Returns the consec.
     */
    public BigDecimal getConsec() {
        return consec;
    }

    /**
     * @param consec
     *            The consec to set.
     */
    public void setConsec(BigDecimal consec) {
        this.consec = consec;
    }

    /**
     * @return Returns the cadenaInformacion.
     */
    public String getCadenaInformacion() {
        return cadenaInformacion;
    }

    /**
     * @param cadenaInformacion
     *            The cadenaInformacion to set.
     */
    public void setCadenaInformacion(String cadenaInformacion) {
        this.cadenaInformacion = cadenaInformacion;
    }

    /**
     * @return Returns the descripcionError.
     */
    public String getDescripcionError() {
        return descripcionError;
    }

    /**
     * @param descripcionError
     *            The descripcionError to set.
     */
    public void setDescripcionError(String descripcionError) {
        this.descripcionError = descripcionError;
    }

    /**
     * @return Returns the fechaRegistro.
     */
    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * @param fechaRegistro
     *            The fechaRegistro to set.
     */
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = clona(fechaRegistro);
    }

    /**
     * @return List
     */
    public List getDatos() {
        return datos;
    }

    /**
     * @param datos
     */
    public void setDatos(List datos) {
        this.datos = datos;
    }

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void validate(Object target, Errors errors) {
    }
    
}

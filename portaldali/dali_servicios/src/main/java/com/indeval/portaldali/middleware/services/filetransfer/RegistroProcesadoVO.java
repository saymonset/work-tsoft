/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.filetransfer;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import org.springframework.validation.Errors;

/**
 *
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class RegistroProcesadoVO extends AbstractBaseDTO {

    /** Constante de serializacion */
    private static final long serialVersionUID = 1L;

    /** Fecha registro. */
    private Date fechaRegistro;

    /** Cadena */
    private String cadena;

    /** Estado */
    private String estado;

    /** Mensaje de error */
    private String[] mensajesError;

    /** Nombre usuario */
    private String nombreUsuario;

    /** Consecutivo */
    private BigInteger consec;

    /** Datos */
    private List datos;

    /** Campos calculados en la validacion */
    private CamposCalculadosVO camposCalculadosVO;

    /** Indica si el registro es venta o compra */
    private boolean isCompra;

    /**
     * 
     * @see org.springframework.validation.Validator#validate(java.lang.Object,
     *      org.springframework.validation.Errors)
     */
    public void validate(Object target, Errors errors) {}

    /**
     *
     * @return the fechaRegistro
     */
    public Date getFechaRegistro() {

        return fechaRegistro;

    }

    /**
     *
     * @param fechaRegistro the fechaRegistro to set
     */
    public void setFechaRegistro(Date fechaRegistro) {

        this.fechaRegistro = clona(fechaRegistro);

    }

    /**
     *
     * @return the cadena
     */
    public String getCadena() {

        return cadena;

    }

    /**
     *
     * @param cadena the cadena to set
     */
    public void setCadena(String cadena) {

        this.cadena = cadena;

    }

    /**
     *
     * @return the estado
     */
    public String getEstado() {

        return estado;

    }

    /**
     *
     * @param estado the estado to set
     */
    public void setEstado(String estado) {

        this.estado = estado;

    }

    /**
     *
     * @return the mensajesError
     */
    public String[] getMensajesError() {

        return mensajesError;

    }

    /**
     *
     * @param mensajesError the mensajesError to set
     */
    public void setMensajesError(String[] mensajesError) {

        this.mensajesError = mensajesError;

    }

    /**
     *
     * @return the nombreUsuario
     */
    public String getNombreUsuario() {

        return nombreUsuario;

    }

    /**
     *
     * @param nombreUsuario the nombreUsuario to set
     */
    public void setNombreUsuario(String nombreUsuario) {

        this.nombreUsuario = nombreUsuario;

    }

    /**
     *
     * @return the consec
     */
    public BigInteger getConsec() {

        return consec;

    }

    /**
     *
     * @param consec the consec to set
     */
    public void setConsec(BigInteger consec) {

        this.consec = clonaBigInteger(consec);

    }

    /**
     *
     * @return the datos
     */
    public List getDatos() {

        return datos;

    }

    /**
     *
     * @param datos the datos to set
     */
    public void setDatos(List datos) {

        this.datos = datos;

    }

    /**
     *
     * @return CamposCalculadosVO
     */
    public CamposCalculadosVO getCamposCalculadosVO() {

        return camposCalculadosVO;

    }

    /**
     *
     * @param camposCalculadosVO
     */
    public void setCamposCalculadosVO(CamposCalculadosVO camposCalculadosVO) {

        this.camposCalculadosVO = camposCalculadosVO;

    }

    /**
     *
     * @return boolean
     */
    public boolean isCompra() {

        return isCompra;

    }

    /**
     *
     * @param isCompra
     */
    public void setCompra(boolean isCompra) {

        this.isCompra = isCompra;

    }

}

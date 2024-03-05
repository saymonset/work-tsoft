/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.divisioninternacional;

import java.math.BigDecimal;
import java.util.Date;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class TraspazosDivIntVO extends AbstractBaseDTO {

    /** Constante de serializacion */
    private static final long serialVersionUID = 1L;

    private String tr;

    private String cuentaPropia;

    private AgenteVO contraparte;

    private EmisionVO claveValor;

    private BigDecimal tituloTraspasante;

    private BigDecimal precioTitulo;

    private Integer folioControl;

    private Date fechaHora;

    private String origen;

    /**
     * @return EmisionVO
     */
    public EmisionVO getClaveValor() {
        return claveValor;
    }

    /**
     * @param claveValor
     */
    public void setClaveValor(EmisionVO claveValor) {
        this.claveValor = claveValor;
    }

    /**
     * @return AgenteVO
     */
    public AgenteVO getContraparte() {
        return contraparte;
    }

    /**
     * @param contraparte
     */
    public void setContraparte(AgenteVO contraparte) {
        this.contraparte = contraparte;
    }

    /**
     * @return String
     */
    public String getCuentaPropia() {
        return cuentaPropia;
    }

    /**
     * @param cuentaPropia
     */
    public void setCuentaPropia(String cuentaPropia) {
        this.cuentaPropia = cuentaPropia;
    }

    /**
     * @return Date
     */
    public Date getFechaHora() {
        return fechaHora;
    }

    /**
     * @param fechaHora
     */
    public void setFechaHora(Date fechaHora) {
        this.fechaHora = clona(fechaHora);
    }

    /**
     * @return Integer
     */
    public Integer getFolioControl() {
        return folioControl;
    }

    /**
     * @param folioControl
     */
    public void setFolioControl(Integer folioControl) {
        this.folioControl = folioControl;
    }

    /**
     * @return String
     */
    public String getOrigen() {
        return origen;
    }

    /**
     * @param origen
     */
    public void setOrigen(String origen) {
        this.origen = origen;
    }

    /**
     * @return BigDecimal
     */
    public BigDecimal getPrecioTitulo() {
        return precioTitulo;
    }

    /**
     * @param precioTitulo
     */
    public void setPrecioTitulo(BigDecimal precioTitulo) {
        this.precioTitulo = precioTitulo;
    }

    /**
     * @return BigDecimal
     */
    public BigDecimal getTituloTraspasante() {
        return tituloTraspasante;
    }

    /**
     * @param tituloTraspasante
     */
    public void setTituloTraspasante(BigDecimal tituloTraspasante) {
        this.tituloTraspasante = tituloTraspasante;
    }

    /**
     * @return String
     */
    public String getTr() {
        return tr;
    }

    /**
     * @param tr
     */
    public void setTr(String tr) {
        this.tr = tr;
    }

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object,
     *      org.springframework.validation.Errors)
     */
    public void validate(Object arg0, Errors arg1) {
    }

}
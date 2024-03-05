/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadocapitales;

import java.util.Map;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class EstadoCuentaTotalVO extends AbstractBaseDTO {

    /** Constante de Serializacion */
    private static final long serialVersionUID = 1L;

    /**
     * Arreglo con todas las cuentas del agente firmado para las cuales se
     * encontraron registros en bdcaptal..traspasos o en
     * bdcaptal..depositos_retiros
     */
    private String[] cuenta;

    /**
     * Mapa donde se parea cada una de las cuentas con el arreglo de las
     * emisiones con que cuenta TODO ya no debe ser necesario
     */
    private Map mapaCuentaArregloEmisiones;

    /**
     * Arreglo con todas las emisiones vinculadas a la cuenta del agente firmado
     * para la cual se estan recuperando los registros de bdcaptal..traspasos y
     * de bdcaptal..depositos_retiros
     */
    private EmisionVO[] emisiones;

    /**
     * Pagina que contiene los registros recuperados de bdcaptal..traspasos y de
     * bdcaptal..depositos_retiros para un par cuenta-emision
     */
    private PaginaVO paginaVO;

    /**
     * Objeto donde se devuelven los totales
     */
    private TotalesEstadoCuentaVO totales;

    /**
     * Arreglo de string utilizado para identificar el par cuenta-emision al que
     * se corresponden los registros de la pagina. Indispensable para la
     * exportacion. posicion[0] = cuenta posicion[1] = tv posicion[2] = emisora
     * posicion[3] = serie posicion[4] = cupon posicion[5] = descripcion
     */
    private String[] posicion;

    /**
     * @return Map
     */
    public Map getMapaCuentaArregloEmisiones() {
        return mapaCuentaArregloEmisiones;
    }

    /**
     * @param mapaCuentaArregloEmisiones
     */
    public void setMapaCuentaArregloEmisiones(Map mapaCuentaArregloEmisiones) {
        this.mapaCuentaArregloEmisiones = mapaCuentaArregloEmisiones;
    }

    /**
     * @return PaginaVO
     */
    public PaginaVO getPaginaVO() {
        return paginaVO;
    }

    /**
     * @param paginaVO
     */
    public void setPaginaVO(PaginaVO paginaVO) {
        this.paginaVO = paginaVO;
    }

    /**
     * @return TotalesEstadoCuentaVO
     */
    public TotalesEstadoCuentaVO getTotales() {
        return totales;
    }

    /**
     * @param totales
     */
    public void setTotales(TotalesEstadoCuentaVO totales) {
        this.totales = totales;
    }

    /**
     * @return the emisiones
     */
    public EmisionVO[] getEmisiones() {
        return emisiones;
    }

    /**
     * @param emisiones
     *            the emisiones to set
     */
    public void setEmisiones(EmisionVO[] emisiones) {
        this.emisiones = emisiones;
    }

    /**
     * @return String[] 
     */
    public String[] getCuenta() {
        return cuenta;
    }

    /**
     * @param cuenta
     */
    public void setCuenta(String[] cuenta) {
        this.cuenta = cuenta;
    }

    /**
     * @return String[]
     */
    public String[] getPosicion() {
        return posicion;
    }

    /**
     * @param posicion
     */
    public void setPosicion(String[] posicion) {
        this.posicion = posicion;
    }

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object,
     *      org.springframework.validation.Errors)
     */
    public void validate(Object arg0, Errors arg1) {
    }

}

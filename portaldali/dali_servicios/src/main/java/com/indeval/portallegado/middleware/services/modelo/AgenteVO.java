/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portallegado.middleware.services.modelo;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

import com.indeval.portallegado.middleware.services.AbstractBaseDTO;

/**
 * Clase Agente
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class AgenteVO extends AbstractBaseDTO {

    /** Constante de Serializacion */
    private static final long serialVersionUID = 1L;

    /** Define Cuenta 54 */
    private static final String CUENTA_54 = "54";

    /** Define Cuenta 94 */
    private static final String CUENTA_94 = "94";

    /** Define Cuenta de Activos de Sociedades de Inversi&oacute;n */
    private static final String CUENTA_97 = "97";

    /** Define Cuenta de Activos de Sociedades de Inversi&oacute;n */
    private static final String CUENTA_98 = "98";

    /** Define Cuenta 99 */
    private static final String CUENTA_99 = "99";

    /** id */
    private String id;

    /** folio */
    private String folio;

    /** cuenta */
    private String cuenta;

    /** nombre de la cuenta */
    private String nombreCuenta;

    /** nombre corto del agente */
    private String nombreCorto;

    /** tipo del agente */
    private String tipo;

    /** razon social del agente */
    private String razon;

    /** tenencia */
    private String tenencia;

    /** Identifica si el agente es el firmado */
    private boolean firmado;

    /* Constructores */

    /**
     * Constructor por defecto
     */
    public AgenteVO() {

    }

    /**
     * Construye un objeto AgenteVO a partir de la clave (id||folio) recibida
     * 
     * @param clave
     */
    public AgenteVO(String clave) {
        if (StringUtils.isNotBlank(clave) && clave.length()==5) {
            this.setId(clave.substring(0,2));
            this.setFolio(clave.substring(2));    
        }
    }
    
    /**
     * Construye un objeto AgenteVO con el id y folio recibidos
     * 
     * @param id
     * @param folio
     */
    public AgenteVO(String id, String folio) {
        this.setId(id);
        this.setFolio(folio);
    }

    /**
     * Construye un objeto AgenteVO con el id, folio y cuenta recibidos
     * 
     * @param id
     * @param folio
     * @param cuenta
     */
    public AgenteVO(String id, String folio, String cuenta) {
        this.setId(id);
        this.setFolio(folio);
        this.setCuenta(cuenta);
    }

    /**
     * @return String
     */
    public String getCuenta() {
        return cuenta;
    }

    /**
     * @param cuenta
     */
    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    /** 
     * @return String
     */
    public String getFolio() {
        return folio;
    }

    /**
     * @param folio
     */
    public void setFolio(String folio) {
        this.folio = folio;
    }

    /**
     * @return String
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return String
     */
    public String getNombreCuenta() {
        return nombreCuenta;
    }

    /**
     * @param nombreCuenta
     */
    public void setNombreCuenta(String nombreCuenta) {
        this.nombreCuenta = nombreCuenta;
    }

    /**
     * @return String
     */
    public String getNombreCorto() {
        return nombreCorto;
    }

    /**
     * @param nombreCorto
     */
    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    /**
     * @return String
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the razon
     */
    public String getRazon() {
        return razon;
    }

    /**
     * @param razon
     *            the razon to set
     */
    public void setRazon(String razon) {
        this.razon = razon;
    }

    /**
     * @return String
     */
    public String getTenencia() {
        return tenencia;
    }

    /**
     * @param tenencia
     */
    public void setTenencia(String tenencia) {
        this.tenencia = tenencia;
    }

    /**
     * Compara usando solo la llave (id, folio)
     * 
     * @param obj
     *            contra el cual se compara.
     * @return boolean
     */
    public boolean equals(Object obj) {
        boolean valido = true;
        if (obj instanceof AgenteVO) {
            AgenteVO agente = (AgenteVO) obj;

            if ((this.id == null && agente.getId() != null)
                    || (this.folio == null && agente.getFolio() != null)) {
                valido = false;
            }
            if (this.id != null && !this.id.equals(agente.getId().trim())
                    || (this.folio != null && !this.folio.equals(agente.getFolio().trim()))) {
                valido = false;
            }
        }
        else {
            valido = false;
        }
        return valido;
    }

    /**
     * Compara usando la llave (id, folio, cuenta)
     * 
     * @param obj
     *            contra el cual se compara.
     * @param nada solo sirve para sobrecargar el metodo, pero no da funcionalidad
     * @return boolean
     * 
     */
    public boolean equals(Object obj, boolean nada) {

        boolean valido = true;

        if (this.equals(obj)) {
            AgenteVO agente = (AgenteVO) obj;
            if ((this.cuenta == null && agente.getCuenta() != null) || this.cuenta != null
                    && !this.cuenta.equals(agente.getCuenta().trim())) {
                valido = false;
            }
        }
        else {
            valido = false;
        }
        return valido;
    }

    /**
     * Valida si el Object recibido es un AgenteVO
     * 
     * @param inObject
     * @return boolean
     */
    public static boolean isAn(Object inObject) {

        if ((inObject == null) || (!(inObject instanceof AgenteVO)))
            return false;

        return true;
    }

    /**
     * Valida que la clave (ID+FOLIO) del agente sea valida
     * 
     * @throws BusinessException
     */
    public void tieneClaveValida() throws BusinessException {
        if (StringUtils.isBlank(this.id)) {
            throw new BusinessException("Error: El agente tiene NULL o VACIO el atributo ID");
        }
        if (StringUtils.isBlank(this.folio)) {
            throw new BusinessException("Error: El agente tiene NULL o VACIO el atributo FOLIO");
        }
    }

    /**
     * Obtiene la clave del Agente concatenando su id y folio.
     * 
     * @return La clave del agente.
     */
    public String getClave() {
        StringBuffer clave = new StringBuffer();
        if (this.id != null) {
            clave.append(this.id.trim());
        }
        if (this.folio != null) {
            clave.append(this.folio.trim());
        }
        return clave.toString();
    }

    /**
     * Valida que la cuenta de este agente comience en 54
     * 
     * @return true solo si la cuenta comienza en 54. False lo contrario
     */
    public boolean esComienzoCuenta54() {
        return esComienzoTerminacionCuenta(false, CUENTA_54);
    }

    /**
     * Valida que la cuenta de este agente termina en 94
     * 
     * @return true solo si la cuenta termina en 94. False lo contrario
     */
    public boolean esTerminacionCuenta94() {
        return esComienzoTerminacionCuenta(true, CUENTA_94);
    }

    /**
     * Valida que la cuenta de este agente termina en 97
     * 
     * @return true solo si la cuenta termina en 97. False lo contrario
     */
    public boolean esTerminacionCuenta97() {
        return esComienzoTerminacionCuenta(true, CUENTA_97);
    }

    /**
     * Valida que la cuenta de este agente termina en 98
     * 
     * @return true solo si la cuenta termina en 98. False lo contrario
     */
    public boolean esTerminacionCuenta98() {
        return esComienzoTerminacionCuenta(true, CUENTA_98);
    }

    /**
     * Valida que la cuenta de este agente termina en 99
     * 
     * @return true solo si la cuenta termina en 99. False lo contrario
     */
    public boolean esTerminacionCuenta99() {
        return esComienzoTerminacionCuenta(true, CUENTA_99);
    }

    /**
     * Valida si la cuenta de este agente termina o comienza con el n&uacute;mero de
     * cuenta propuesto en el parametro
     * 
     * @param esTerminacion
     *            True verifica que la cuenta termine con el numero deseado.
     *            False verifica que comience con el numero propuesto.
     * @param numeroCuenta
     *            el n&uacute;mero de cuenta que con el que se desea validar
     * @return True si la cuenta termina o comienza con el n&uacute;mero deseado. False
     *         lo contrario.
     */
    private boolean esComienzoTerminacionCuenta(boolean esTerminacion, String numeroCuenta) {

        boolean flag = false;
        if (StringUtils.isNotBlank(cuenta)) {
            if (esTerminacion) {
                if (cuenta.endsWith(numeroCuenta))
                    flag = true;
            }
            else {
                if (cuenta.startsWith(numeroCuenta))
                    flag = true;
            }
        }
        return flag;
    }

    /**
     * @return the firmado
     */
    public boolean isFirmado() {
        return firmado;
    }

    /**
     * @param firmado
     *            the firmado to set
     */
    public void setFirmado(boolean firmado) {
        this.firmado = firmado;
    }

    /**
     * Retorna una cadena String con los valores de los atributos del objeto
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {

        StringBuffer sbAgenteVO = new StringBuffer(
                StringUtils.isNotBlank(this.toResumeString()) ? this.toResumeString() : "");
        if (StringUtils.isNotBlank(this.getNombreCuenta()))
            sbAgenteVO.append(" : " + this.getNombreCuenta());
        if (StringUtils.isNotBlank(this.getNombreCorto()))
            sbAgenteVO.append(" : " + this.getNombreCorto());
        if (StringUtils.isNotBlank(this.getTipo()))
            sbAgenteVO.append(" : " + this.getTipo());
        if (StringUtils.isNotBlank(this.getTenencia()))
            sbAgenteVO.append(" : " + this.getTenencia());

        return sbAgenteVO.toString();

    }

    /**
     * @return String
     */
    public String toResumeString() {
        StringBuffer sbAgenteVO = new StringBuffer(StringUtils.isNotBlank(this.getId()) ? this
                .getId().trim() : "");
        if (StringUtils.isNotBlank(this.getFolio()))
            sbAgenteVO.append(" : " + this.getFolio().trim());
        if (StringUtils.isNotBlank(this.getCuenta()))
            sbAgenteVO.append(" : " + this.getCuenta().trim());

        return sbAgenteVO.toString();
    }

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object,
     *      org.springframework.validation.Errors)
     */
    public void validate(Object obj, Errors errors) {

    }

}

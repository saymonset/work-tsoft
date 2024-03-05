/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Clase base para los DTOs de los servicios. Los DTOs implementan
 * un Validator por lo que son autovalidables.
 * Es importate que implemente Serializable para que los DTOs
 * puedan mandarse como parametros a EJBs remotos.
 */
public abstract class AbstractBaseDTO implements Validator, Serializable {
    /**
     * Constante de serializacion
     */
    private static final long serialVersionUID = 1L;

    /** Guardan los errores resultado de la validacion. */
    private Errors errors;
    
    /** Constante BigDecimal ZERO */
    public static final BigDecimal BIG_DECIMAL_ZERO = new BigDecimal(0);
    
    /** Constante BigInteger ZERO */
    public static final BigInteger BIG_INTEGER_ZERO = new BigInteger("0");

    /** Constante Integer ZERO */
    public static final Integer INTEGER_ZERO = new Integer("0");
    
    /** Define BLANK ( ) */
    public static final String BLANK = " ";

    /** Error de parametros */
    public static final String ERROR_DE_PARAMETROS = "Error: Parametros incompletos o incorrectos";

    /**
     * Por default auto-valida. Podria ser redefinido para validar otras clases.
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    public boolean supports(Class clase) {
        return this.getClass().equals(clase);
    }

    /**
     * Ejecuta la validacion. Desde aqui se manda llamar indirectamente
     * el metodo validate(Object, Errors) de cada clase.
     */
    public void validate() {
        ValidationUtils.invokeValidator(this, this, getErrors());
    }
    
    /**
     * Ejecuta la validacion de todos los atributos del objeto que recibe.
     * @param obj
     * @param errors
     */
    public void validateAll(Object obj, Errors errors) {
        Class objeto = obj.getClass();
        Field[] fields = objeto.getDeclaredFields();
        int count = 0;
        while (count<fields.length) {
            ValidationUtils.rejectIfEmpty(errors, fields[count].getName(), "");
            count++;
        }
    }

    /**
     * Obtiene la referencia a los errores de la clase.
     * @return la referencia a los errores de la clase.
     */
    public Errors getErrors() {
        if (errors == null) {
            errors = createErrors(this, getName());
        }
        return errors;
    }

    /**
     * Obtiene el nombre de la entidad que se esta validando.
     * Este nombre es usado para formar la llave de los mensajes de error, por
     * lo que es posible redefinir este metodo en las subclases para cambiar este
     * valor.
     * @return El nombre de esta clase.
     */
    protected String getName() {
        return this.getClass().getName();
    }

    /**
     * Crea una instancia nueva de tipo Errors. Este metodo puede ser
     * redefinido en las subclases para utilizar otra implementacion
     * de la interfaz Errors.
     * @return una nueva instancia de tipo Errors.
     */
    protected Errors createErrors(Object target, String objectName) {
//        return new BindException(target, objectName);
        return null;
    }
    
    /**
     * @param fecha
     * @return Date
     */
    protected static Date clona(Date fecha) {
        if (fecha != null)
            return new Date(fecha.getTime());
        return null;
    }
    
    /**
     * @param valor
     * @return BigInteger
     */
    protected static BigInteger clonaBigInteger(BigInteger valor){
        if (valor != null)
            return new BigInteger(valor.toString());
        return null;
    }

    /**
     * @param valor
     * @return BigDecimal
     */
    protected static BigDecimal clonaBigDecimal(BigDecimal valor){
        if (valor != null)
            return new BigDecimal(valor.toString());
        return null;
    }
    
    /**
     * @see com.indeval.portaldali.middleware.services.AbstractBaseDTO#clonaString(String, boolean)
     * @param str
     * @return String
     */
    protected static String clonaString(String str) {
        return clonaString(str, false);
    }
    
    /**
     * Clona String tomando en cuenta si la cadena es vacia. Si es vacia, entonces la cadena clonada
     * sera nula.  La clonacion considera si deseamos que se efectua un trim sobre la cadena original.
     * 
     * @param str Cadena a clonar
     * @param trim Indica si la clonacion va a incluir un trim a la cadena original.
     * @return Un String clonado.
     */
    protected static String clonaString(String str, boolean trim) {
        String result = null;
        if(StringUtils.isNotBlank(str)) {
            result = trim ? str.trim() : str;
        }
        return result;
    }
    
}

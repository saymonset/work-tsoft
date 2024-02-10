/**
 * Copyright (c) 2016 Bursatec. All Rights Reserved
 */
package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.indeval.portaldali.persistence.modelo.BitacoraOperaciones;
import com.indeval.portalinternacional.middleware.servicios.modelo.OperacionSic;

/**
 * Objeto que representa el resultado de registrar una operacion de Entrega y/o Recepci&oacute;n.
 *
 */
public class ResultadoFTVO implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = -4789L;

    /**Propiedad de nueva salto de linea en el sistema */ 
    private static final String NEWLINE = System.getProperty("line.separator");

	/**
	 * Id de la operacion Deposito o Retiro.
	 */
	private Integer idOperacion;
	
	/**
     * El tipo de movimiento (Entrega o Recepcion)
     */
    private String tipoMovimiento;

    /**
     * La operacion generada de la tabla T_BITACORA_OPERACIONES.
     */
    private BitacoraOperaciones bitacoraOperaciones;

	public ResultadoFTVO() {
		
	}

    public Integer getIdOperacion() {
        return idOperacion;
    }

    public void setIdOperacion(Integer idOperacion) {
        this.idOperacion = idOperacion;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public BitacoraOperaciones getBitacoraOperaciones() {
        return bitacoraOperaciones;
    }

    public void setBitacoraOperaciones(BitacoraOperaciones bitacoraOperaciones) {
        this.bitacoraOperaciones = bitacoraOperaciones;
    }

    /**
     * Metodo sobrecargado
     * @return java.lang.Object#toString()
     */
    public String toString() {
        final StringBuffer buffer = new StringBuffer();
        final Method[] metodos = this.getClass().getMethods();
        String nombreMetodo;
        Object valor;
        
        for (int i = 0; i < metodos.length; i++) {
            nombreMetodo = metodos[i].getName();
            if (metodos[i].getParameterTypes( ).length==0
                    && (nombreMetodo.startsWith("get") || nombreMetodo.startsWith("is"))
                    && !"getClass".equals(nombreMetodo)) {
                try
                {
                    valor = metodos[i].invoke(this, null);
                }
                catch( IllegalAccessException exception )
                {
                    valor="";
                }
                catch( InvocationTargetException exception )
                {
                    valor="";
                }
                buffer.append(NEWLINE);
                buffer.append(' ');
                if(nombreMetodo.startsWith("get")){
                    buffer.append(Character.toLowerCase(nombreMetodo.charAt(3)));
                    buffer.append(nombreMetodo.substring(4));
                }
                else{
                    buffer.append(Character.toLowerCase(nombreMetodo.charAt(2)));
                    buffer.append(nombreMetodo.substring(3));
                }               
                buffer.append( ": " );
                buffer.append(valor);
            }
        }
        return buffer.toString();
    }

}

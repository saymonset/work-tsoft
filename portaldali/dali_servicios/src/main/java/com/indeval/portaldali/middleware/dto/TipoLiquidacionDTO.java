/**
 * 
 * Portal DALI
 *
 * 
 * 13/10/2009
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Este cata&acute;logo contiene los tipos de liquidacion DTO
 * 
 *  
 * @author Fernando vazquez Ulloa
 * @version 1.0
 */

public class TipoLiquidacionDTO implements Serializable {
    
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador del tipo de instruccio&acute;n.
     */
    
    private BigInteger idTipoLiq;

    /**
     * Nombre corto del tipo de instruccio&acute;n.
     */

    private String nombreCorto;

    /**
     * Descripcion
     */

    private String descripcion;

    /**
     * Descripcio&acute;n del tipo de instruccio&acute;n.
     */

    /**
     * Indica TIPO_LIQUIDACION.
     */

    private String tipoLiquidacion;


     /**
     * Nombre corto de la instruccio&acute;n.
     * 
     * @return String
     */
    public String getNombreCorto() {
        return nombreCorto;
    }

    /**
     * Nombre corto de la instruccio&acute;n.
     * 
     * @param nombreCorto
     */
    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    /**
     * Descripcio&acute;n del tipo de instruccio&acute;n.
     * 
     * @return String
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Descripcio&acute;n del tipo de instruccio&acute;n.
     * 
     * @param descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

	public BigInteger getIdTipoLiq() {
		return idTipoLiq;
	}

	public void setIdTipoLiq(BigInteger idTipoLiq) {
		this.idTipoLiq = idTipoLiq;
	}

	public String getTipoLiquidacion() {
		return tipoLiquidacion;
	}

	public void setTipoLiquidacion(String tipoLiquidacion) {
		this.tipoLiquidacion = tipoLiquidacion;
	}

    
}

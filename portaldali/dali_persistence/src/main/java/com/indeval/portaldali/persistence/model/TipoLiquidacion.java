/*
 * Copyrigth (c) 2005-2009 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Este cata&acute;logo contiene los tipos de liquidacion
 * 
 * C_TIPO_LIQUIDACION
 * 
 * @author fernando vazquez ulloa
 * @version 1.0
 */
@Entity
@Table(name = "C_TIPO_LIQUIDACION")
public class TipoLiquidacion implements Serializable {
    
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador del tipo de instruccio&acute;n.
     */
    @Id
    @Column(name = "ID_TIPO_LIQ")
    private BigInteger idTipoLiq;

    /**
     * Nombre corto del tipo de instruccio&acute;n.
     */
    @Column(name = "NOMBRE_CORTO")
    private String nombreCorto;

    /**
     * Descripcion
     */
    @Column(name = "DESCRIPCION")
    private String descripcion;

    /**
     * Descripcio&acute;n del tipo de instruccio&acute;n.
     */

    /**
     * Indica TIPO_LIQUIDACION.
     */
    @Column(name = "TIPO_LIQUIDACION")
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

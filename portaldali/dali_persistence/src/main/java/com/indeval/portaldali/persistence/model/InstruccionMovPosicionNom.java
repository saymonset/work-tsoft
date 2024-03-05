/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * InstruccionMovPosicionNom.java
 * Mapeo de la tabla T_INSTRUCCION_MOV_POSICION_NOM
 * que contiene la información de las posiciones (para conocer
 * emisión, cuenta y bóveda)
 *
 * @author 2H Software - Bursatec - INDEVAL
 * @version Portal DALI - Feb 29, 2008
 */ 
@Entity
@Table (name = "T_INSTRUCCION_MOV_POSICION_NOM")
@SequenceGenerator(
    name="SEQ_InstruccionMovPosicion",
    sequenceName="T_INSTRUC_MOV_POSICION_NOM_SEQ"
)
public class InstruccionMovPosicionNom {
	@Id
	@Column (name = "id_instruc_mov_posicion_nom")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_InstruccionMovPosicion")
	private BigInteger idInstrucMovPosicionNom;

	@Column (name = "id_instruccion_operacion_val")
	private BigInteger idInstruccionOperacionVal;

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn (name = "id_posicion_nombrada")
	private PosicionNombrada posicionNombrada;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn (name = "id_instruccion_operacion_val",insertable=false,updatable=false)
	private InstruccionOperacionVal instruccionOperacionVal;
   
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "id_emision")
    @NotFound(action=NotFoundAction.IGNORE)
    private Emision emision;

	@Column (name = "numero_titulos")
	private BigInteger numeroTitulos;

	@Column (name = "precio_titulo")
	private BigDecimal precioTitulo;



    public BigInteger getIdInstrucMovPosicionNom() {
        return idInstrucMovPosicionNom;
    }

    public BigInteger getIdInstruccionOperacionVal() {
        return idInstruccionOperacionVal;
    }

    public BigInteger getNumeroTitulos() {
        return numeroTitulos;
    }

    public BigDecimal getPrecioTitulo() {
        return precioTitulo;
    }


    public void setIdInstrucMovPosicionNom(BigInteger idInstrucMovPosicionNom) {
        this.idInstrucMovPosicionNom = idInstrucMovPosicionNom;
    }

    public void setIdInstruccionOperacionVal(BigInteger idInstruccionOperacionVal) {
        this.idInstruccionOperacionVal = idInstruccionOperacionVal;
    }

    public void setNumeroTitulos(BigInteger numeroTitulos) {
        this.numeroTitulos = numeroTitulos;
    }

    public void setPrecioTitulo(BigDecimal precioTitulo) {
        this.precioTitulo = precioTitulo;
    }

	/**
	 * @return Returns the posicionNombrada.
	 */
	public PosicionNombrada getPosicionNombrada() {
		return posicionNombrada;
	}

	/**
	 * @param posicionNombrada The posicionNombrada to set.
	 */
	public void setPosicionNombrada(PosicionNombrada posicionNombrada) {
		this.posicionNombrada = posicionNombrada;
	}

    /**
     * @return the emision
     */
    public Emision getEmision() {
        return emision;
    }

    /**
     * @param emision the emision to set
     */
    public void setEmision(Emision emision) {
        this.emision = emision;
    }

	/**
	 * Obtiene el campo instruccionOperaciconVal
	 * @return  instruccionOperaciconVal
	 */
	public InstruccionOperacionVal getInstruccionOperacionVal() {
		return instruccionOperacionVal;
	}

	/**
	 * Asigna el campo instruccionOperaciconVal
	 * @param instruccionOperaciconVal el valor de instruccionOperaciconVal a asignar
	 */
	public void setInstruccionOperacionVal(
			InstruccionOperacionVal instruccionOperacionVal) {
		this.instruccionOperacionVal = instruccionOperacionVal;
	}
}


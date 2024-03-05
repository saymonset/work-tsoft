/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadocapitales;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import org.springframework.validation.Errors;
/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ArchivoConciliacionVO extends AbstractBaseDTO {

    /** Constante de serializacion */
    private static final long serialVersionUID = 1L;

    /** Agente Firmado */
    private AgenteVO agenteFirmado;
    
    /** Agente Receptor */
    private AgenteVO agenteReceptor;
    
    /** Agente Traspasante */
    private AgenteVO agenteTraspasante;
    
    /** Emision */
    private EmisionVO emision;

    /** Valor Nominal */
    private BigDecimal valorNominal;

    /** Ultimo Hecho */
    private BigDecimal ultimoHecho;

    /** Saldo Inicial */
    private BigDecimal saldoInicial;

    /** Saldo Disponible */
    private BigDecimal saldoDisponible;

    /** Saldo Tesoreria */
    private BigDecimal saldoTesoreria;

    /** Total */
    private BigDecimal total;

    /** Fecha Hora */
    private Date fechaHora;

    /** Identificara si tiene detalle, si es:  true = tiene detalle, false = no tiene detalle*/
    private boolean tieneDetalle;
    
    /** Cantidad de titulos operada */
    private BigInteger cantidadOperada;
    
    /**
	 * Clave del tipo de operaci&oacute;n.
	 */
	private String claveTipoOperacion;
	
	
	/** Nombre corto de la boveda */
	private String nombreCortoBoveda;
	
	/** Identificador de la boveda */
	private BigInteger idBoveda;
	
	/** Identificador de la posicion */
	private BigInteger idPosicion;
	
	/** Folio Control de la operacion */
	private BigInteger folioControl;

    /**
     * @return Returns the agenteFirmado.
     */
    public AgenteVO getAgenteFirmado() {
        return agenteFirmado;
    }

    /**
     * @param agenteFirmado
     *            The agenteFirmado to set.
     */
    public void setAgenteFirmado(AgenteVO agenteFirmado) {
        this.agenteFirmado = agenteFirmado;
    }

    /**
     * @return Returns the emision.
     */
    public EmisionVO getEmision() {
        return emision;
    }

    /**
     * @param emision
     *            The emision to set.
     */
    public void setEmision(EmisionVO emision) {
        this.emision = emision;
    }

    /**
     * @return Returns the valorNominal.
     */
    public BigDecimal getValorNominal() {
        return valorNominal;
    }

    /**
     * @param valorNominal
     *            The valorNominal to set.
     */
    public void setValorNominal(BigDecimal valorNominal) {
        this.valorNominal = clonaBigDecimal(valorNominal);
    }

    /**
     * @return Returns the ultimoHecho.
     */
    public BigDecimal getUltimoHecho() {
        return ultimoHecho;
    }

    /**
     * @param ultimoHecho
     *            The ultimoHecho to set.
     */
    public void setUltimoHecho(BigDecimal ultimoHecho) {
        this.ultimoHecho = clonaBigDecimal(ultimoHecho);
    }

    /**
     * @return Returns the fechaHora.
     */
    public Date getFechaHora() {
        return fechaHora;
    }

    /**
     * @param fechaHora
     *            The fechaHora to set.
     */
    public void setFechaHora(Date fechaHora) {
        this.fechaHora = clona(fechaHora);
    }

    /**
     * @return Returns the saldoDisponible.
     */
    public BigDecimal getSaldoDisponible() {
        return saldoDisponible;
    }

    /**
     * @param saldoDisponible
     *            The saldoDisponible to set.
     */
    public void setSaldoDisponible(BigDecimal saldoDisponible) {
        this.saldoDisponible = clonaBigDecimal(saldoDisponible);
    }

    /**
     * @return Returns the saldoInicial.
     */
    public BigDecimal getSaldoInicial() {
        return saldoInicial;
    }

    /**
     * @param saldoInicial
     *            The saldoInicial to set.
     */
    public void setSaldoInicial(BigDecimal saldoInicial) {
        this.saldoInicial = clonaBigDecimal(saldoInicial);
    }

    /**
     * @return Returns the saldoTesoreria.
     */
    public BigDecimal getSaldoTesoreria() {
        return saldoTesoreria;
    }

    /**
     * @param saldoTesoreria
     *            The saldoTesoreria to set.
     */
    public void setSaldoTesoreria(BigDecimal saldoTesoreria) {
        this.saldoTesoreria = clonaBigDecimal(saldoTesoreria);
    }

    /**
     * @return Returns the total.
     */
    public BigDecimal getTotal() {
        return total;
    }

    /**
     * @param total
     *            The total to set.
     */
    public void setTotal(BigDecimal total) {
        this.total = clonaBigDecimal(total);
    }

    /**
     * @see org.springframework.validation.Validator#validate(java.lang.Object,
     *      org.springframework.validation.Errors)
     */
    public void validate(Object target, Errors errors) {
    }

	/**
	 * @return the tieneDetalle
	 */
	public boolean isTieneDetalle() {
		return tieneDetalle;
	}

	/**
	 * @param tieneDetalle the tieneDetalle to set
	 */
	public void setTieneDetalle(boolean tieneDetalle) {
		this.tieneDetalle = tieneDetalle;
	}

    /**
     * @return the agenteReceptor
     */
    public AgenteVO getAgenteReceptor() {
        return agenteReceptor;
    }

    /**
     * @param agenteReceptor the agenteReceptor to set
     */
    public void setAgenteReceptor(AgenteVO agenteReceptor) {
        this.agenteReceptor = agenteReceptor;
    }

    /**
     * @return the agenteTraspasante
     */
    public AgenteVO getAgenteTraspasante() {
        return agenteTraspasante;
    }

    /**
     * @param agenteTraspasante the agenteTraspasante to set
     */
    public void setAgenteTraspasante(AgenteVO agenteTraspasante) {
        this.agenteTraspasante = agenteTraspasante;
    }

	/**
	 * @param cantidadOperada the cantidadOperada to set
	 */
	public void setCantidadOperada(BigInteger cantidadOperada) {
		this.cantidadOperada = cantidadOperada;
	}

	/**
	 * @return the cantidadOperada
	 */
	public BigInteger getCantidadOperada() {
		return cantidadOperada;
	}

	/**
	 * @return the claveTipoOperacion
	 */
	public String getClaveTipoOperacion() {
		return claveTipoOperacion;
	}

	/**
	 * @param claveTipoOperacion the claveTipoOperacion to set
	 */
	public void setClaveTipoOperacion(String claveTipoOperacion) {
		this.claveTipoOperacion = claveTipoOperacion;
	}

	/**
	 * @param nombreCortoBoveda the nombreCortoBoveda to set
	 */
	public void setNombreCortoBoveda(String nombreCortoBoveda) {
		this.nombreCortoBoveda = nombreCortoBoveda;
	}

	/**
	 * @return the nombreCortoBoveda
	 */
	public String getNombreCortoBoveda() {
		return nombreCortoBoveda;
	}

	/**
	 * @param folioControl the folioControl to set
	 */
	public void setFolioControl(BigInteger folioControl) {
		this.folioControl = folioControl;
	}

	/**
	 * @return the folioControl
	 */
	public BigInteger getFolioControl() {
		return folioControl;
	}

	/**
	 * @return the idBoveda
	 */
	public BigInteger getIdBoveda() {
		return idBoveda;
	}

	/**
	 * @param idBoveda the idBoveda to set
	 */
	public void setIdBoveda(BigInteger idBoveda) {
		this.idBoveda = idBoveda;
	}

	/**
	 * @return the idPosicion
	 */
	public BigInteger getIdPosicion() {
		return idPosicion;
	}

	/**
	 * @param idPosicion the idPosicion to set
	 */
	public void setIdPosicion(BigInteger idPosicion) {
		this.idPosicion = idPosicion;
	}
	
}

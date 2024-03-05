/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

/**
 * Contiene el cata&acute;logo de las emisiones a las que se asocian los títulos
 * contenidos en las b&oacute;vedas. Esta tabla se replica desde la base de
 * datos del sistema de Altas, Dep&oacute;sitos y Retiros.
 * 
 * C_EMISION
 * 
 * @author rchavez
 * @version 1.0
 */
@Entity
@Table(name = "C_EMISION")
@FilterDef (name="cuponActivo", parameters=@ParamDef(name="estadoCupon", type="integer"))
public class Emision implements Serializable {
    
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Identificador de la emisi&oacute;n.
	 */
	@Id
	@Column(name = "ID_EMISION")
	private BigInteger idEmision;
	
	/**
	 * Instrumento de la emisi&oacute;n.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_INSTRUMENTO")
	private Instrumento instrumento;
	
	/**
	 * Identificador del instrumento.
	 */
	@Column(name = "ID_INSTRUMENTO", updatable = false, insertable = false)
	private BigInteger idInstrumento;
	
	/**
	 * Identificador de la emisora.
	 */
	@Column(name = "ID_EMISORA", updatable = false, insertable = false)
	private BigInteger idEmisora;

	/** El conjunto de cupones relacionados con la emisión */
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_emision")
	@Filter(name="cuponActivo", condition=":estadoCupon = idEstadoCupon")
	private Set<Cupon> cupon;

	/**
	 * Instrumento de la emisi&oacute;n.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_EMISORA")
	private Emisora emisora;

	/** El identificador de la cuenta de la tesoreria */
	@Column(name = "id_cuenta_tesoreria", unique = true, nullable = false)
	private BigInteger idCuentaTesoreria;

	/** El identificador del representante comn */
	@Column(name = "id_representante_comun", unique = false, nullable = true)
	private BigInteger idRepresentanteComun;

	/** El identificador de la emisión convertible */
	@Column(name = "id_emision_convertible", unique = false, nullable = true)
	private BigInteger idEmisionConvertible;

	/** El identificador del tipo de alta */
	@Column(name = "id_tipo_alta", unique = true, nullable = false)
	private BigInteger idTipoAlta;

	/**
	 * Identificador de la divisa para el valor nominal
	 */
	@Column(name = "ID_DIVISA_VALOR_NOMINAL", updatable = false, insertable = false)
	private BigInteger idDivisaValorNominal;

	/**
	 * Divisa asociada
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DIVISA_VALOR_NOMINAL")
	private Divisa divisa;

	/** El número de títulos de la emisión */
	@Column(name = "numero_titulos", unique = true, nullable = false)
	private BigDecimal numeroTitulos;

	/**
	 * Serie de la emisi&oacute;n.
	 */
	@Column(name = "SERIE")
	private String serie;
	
	/**
	 * Isin de la emisi&oacute;n.
	 */
	@Column(name = "ISIN")
	private String isin;
	
	/**
	 * Valor nominal de la emisi&oacute;n.
	 */
	@Column(name = "VALOR_NOMINAL")
	private BigDecimal valorNominal;
	
	/**
	 * Fecha de inicio de la emisi&oacute;n.
	 */
	@Column(name = "FECHA_EMISION")
	private Date fechaEmision;
	
	

	/** El identificador del tipo de emisión */
	@Column(name = "id_tipo_emision", unique = true, nullable = false)
	private BigInteger idTipoEmision;

	/** El saldo inicial de la emisión */
	@Column(name = "saldo_inicial", unique = true, nullable = false)
	private BigDecimal saldoInicial;

 	@Column(name = "fecha_vencimiento", unique = false, nullable = true)
 	private Date fechaVencimiento;
	 @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_boveda")
    private Boveda boveda;
	@Column(name = "id_cruce_bmv", unique = true, nullable = false)
    private BigInteger idCruceBmv;
	@Column(name = "emision_extranjera", unique = true, nullable = false)
    private String emisionExtranjera;
	@Column(name = "es_deposito_generado", unique = true, nullable = false)
    private BigDecimal esDepositoGenerado;
	@Column(name = "id_estatus_emision", unique = true, nullable = false)
    private BigInteger idEstatusEmision;
	@Column(name = "id_frecuencia_pago", unique = false, nullable = true)
    private BigInteger idFrecuenciaPago;
	@Column(name = "es_generar_isin", unique = true, nullable = false)
    private BigDecimal esGenerarIsin;
	@Column(name = "fecha_limite_movimientos", unique = false, nullable = true)
    private Date fechaLimiteMovimientos;
	@Column(name = "primer_fecha_pago", unique = false, nullable = true)
    private Date primerFechaPago;
	@Column(name = "cantidad_minima_operacion", unique = false, nullable = true)
    private BigDecimal cantidadMinimaOperacion;
	@Column(name = "id_codigo_circulacion", unique = false, nullable = true)
    private BigInteger idCodigoCirculacion;
	@Column(name = "id_codigo_amortizacion", unique = false, nullable = true)
    private BigInteger idCodigoAmortizacion;
	@Column(name = "id_certificacion_emision", unique = false, nullable = true)
    private BigInteger idCertificacionEmision;
	@Column(name = "colocacion", unique = false, nullable = true)
    private String colocacion;
	@Column(name = "fecha_pago_anio_cal", unique = false, nullable = true)
    private Date fechaPagoAnioCal;
 	@Column(name = "fecha_baja", unique = false, nullable = true)
    private Date fechaBaja;
	 @Column(name = "fecha_creacion", unique = false, nullable = true)
    private Date fechaCreacion;
	@Column(name = "fecha_ult_modificacion", unique = true, nullable = false)
    private Date fechaUltModificacion;
	@Column(name = "id_origen_transaccion", unique = false, nullable = true)
    private BigInteger idOrigenTransaccion;
	@Column(name = "acuse", unique = false, nullable = true)
    private BigDecimal acuse;
	@Column(name = "id_tipo_valor_bmv", unique = false, nullable = true)
    private BigInteger idTipoValorBmv;

    
    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

   
    public Boveda getBoveda() {
        return boveda;
    }

    
    public BigInteger getIdCruceBmv() {
        return idCruceBmv;
    }

   
    public String getEmisionExtranjera() {
        return emisionExtranjera;
    }

    
    public BigDecimal getEsDepositoGenerado() {
        return esDepositoGenerado;
    }

    
    public BigInteger getIdEstatusEmision() {
        return idEstatusEmision;
    }

    
    public BigInteger getIdFrecuenciaPago() {
        return idFrecuenciaPago;
    }

    
    public BigDecimal getEsGenerarIsin() {
        return esGenerarIsin;
    }

    
    public Date getFechaLimiteMovimientos() {
        return fechaLimiteMovimientos;
    }

    
    public Date getPrimerFechaPago() {
        return primerFechaPago;
    }

    
    public BigDecimal getCantidadMinimaOperacion() {
        return cantidadMinimaOperacion;
    }

    
    public BigInteger getIdCodigoCirculacion() {
        return idCodigoCirculacion;
    }

    
    public BigInteger getIdCodigoAmortizacion() {
        return idCodigoAmortizacion;
    }

    
    public BigInteger getIdCertificacionEmision() {
        return idCertificacionEmision;
    }

    
    public String getColocacion() {
        return colocacion;
    }

    
    public Date getFechaPagoAnioCal() {
        return fechaPagoAnioCal;
    }

   
    public Date getFechaBaja() {
        return fechaBaja;
    }

    
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    
    public Date getFechaUltModificacion() {
        return fechaUltModificacion;
    }
    
    
    public BigInteger getIdOrigenTransaccion() {
        return idOrigenTransaccion;
    }

    
    public BigDecimal getAcuse() {
        return acuse;
    }

    
    public BigInteger getIdTipoValorBmv() {
        return idTipoValorBmv;
    }    
    
	/**
	 * Fecha en que se hace la emisi&oacute;n.
	 * 
	 * @return Date
	 */
	public Date getFechaEmision() {
		return fechaEmision;
	}

	/**
	 * Fecha en que se hace la emisi&oacute;n.
	 * 
	 * @param fechaEmision
	 */
	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(67, 97).append(idEmision).toHashCode();
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Emision)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Emision rhs = (Emision) obj;
		return new EqualsBuilder().append(idEmision, rhs.getIdEmision()).isEquals();
	}

	/**
	 * Obtiene el campo emisora
	 * 
	 * @return emisora
	 */
	public Emisora getEmisora() {
		return emisora;
	}

	/**
	 * Asigna el valor del campo emisora
	 * 
	 * @param emisora
	 *            el valor de emisora a asignar
	 */
	public void setEmisora(Emisora emisora) {
		this.emisora = emisora;
	}

	/**
	 * Obtiene el atributo idDivisaValorNominal
	 * 
	 * @return El atrubuto idDivisaValorNominal
	 */
	public BigInteger getIdDivisaValorNominal() {
		return idDivisaValorNominal;
	}

	/**
	 * Establece la propiedad idDivisaValorNominal
	 * 
	 * @param idDivisaValorNominal
	 *            el campo idDivisaValorNominal a establecer
	 */
	public void setIdDivisaValorNominal(BigInteger idDivisaValorNominal) {
		this.idDivisaValorNominal = idDivisaValorNominal;
	}

	/**
	 * Obtiene el atributo divisa
	 * 
	 * @return El atrubuto divisa
	 */
	public Divisa getDivisa() {
		return divisa;
	}

	/**
	 * Establece la propiedad divisa
	 * 
	 * @param divisa
	 *            el campo divisa a establecer
	 */
	public void setDivisa(Divisa divisa) {
		this.divisa = divisa;
	}

    /**
     * Obtiene el campo saldoInicial
     * 
     * @return saldoInicial
     */
    public BigDecimal getSaldoInicial() {
        return saldoInicial;
    }

    /**
     * Asigna el campo saldoInicial
     * 
     * @param saldoInicial
     *            el valor de saldoInicial a asignar
     */
    public void setSaldoInicial(BigDecimal saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    /**
     * Obtiene el campo idCuentaTesoreria
     * 
     * @return idCuentaTesoreria
     */
    public BigInteger getIdCuentaTesoreria() {
        return idCuentaTesoreria;
    }

    /**
     * Asigna el campo idCuentaTesoreria
     * 
     * @param idCuentaTesoreria
     *            el valor de idCuentaTesoreria a asignar
     */
    public void setIdCuentaTesoreria(BigInteger idCuentaTesoreria) {
        this.idCuentaTesoreria = idCuentaTesoreria;
    }

    /**
     * Obtiene el campo idRepresentanteComun
     * 
     * @return idRepresentanteComun
     */
    public BigInteger getIdRepresentanteComun() {
        return idRepresentanteComun;
    }

    /**
     * Asigna el campo idRepresentanteComun
     * 
     * @param idRepresentanteComun
     *            el valor de idRepresentanteComun a asignar
     */
    public void setIdRepresentanteComun(BigInteger idRepresentanteComun) {
        this.idRepresentanteComun = idRepresentanteComun;
    }

    /**
     * Obtiene el campo idEmisionConvertible
     * 
     * @return idEmisionConvertible
     */
    public BigInteger getIdEmisionConvertible() {
        return idEmisionConvertible;
    }

    /**
     * Asigna el campo idEmisionConvertible
     * 
     * @param idEmisionConvertible
     *            el valor de idEmisionConvertible a asignar
     */
    public void setIdEmisionConvertible(BigInteger idEmisionConvertible) {
        this.idEmisionConvertible = idEmisionConvertible;
    }

    /**
     * Obtiene el campo idTipoAlta
     * 
     * @return idTipoAlta
     */
    public BigInteger getIdTipoAlta() {
        return idTipoAlta;
    }

    /**
     * Asigna el campo idTipoAlta
     * 
     * @param idTipoAlta
     *            el valor de idTipoAlta a asignar
     */
    public void setIdTipoAlta(BigInteger idTipoAlta) {
        this.idTipoAlta = idTipoAlta;
    }

    /**
     * Obtiene el campo numeroTitulos
     * 
     * @return numeroTitulos
     */
    public BigDecimal getNumeroTitulos() {
        return numeroTitulos;
    }

    /**
     * Asigna el campo numeroTitulos
     * 
     * @param numeroTitulos
     *            el valor de numeroTitulos a asignar
     */
    public void setNumeroTitulos(BigDecimal numeroTitulos) {
        this.numeroTitulos = numeroTitulos;
    }

    /**
     * Obtiene el campo idTipoEmision
     * 
     * @return BigInteger
     */
    public BigInteger getIdTipoEmision() {
        return idTipoEmision;
    }

    /**
     * Asigna el campo idTipoEmision
     * 
     * @param idTipoEmision
     *            el valor de idTipoEmision a asignar
     */
    public void setIdTipoEmision(BigInteger idTipoEmision) {
        this.idTipoEmision = idTipoEmision;
    }

    /**
     * Identificador secuencial para la EmisionPersistence.
     * 
     * @return BigInteger
     */
    public BigInteger getIdEmision() {
        return idEmision;
    }

    /**
     * Identificador secuencial para la EmisionPersistence.
     * 
     * @param idEmision
     */
    public void setIdEmision(BigInteger idEmision) {
        this.idEmision = idEmision;
    }

    /**
     * Referencia al Instrumento de la Emisi&oacute;n (tipo valor).
     * 
     * @return Instrumento
     */
    public Instrumento getInstrumento() {
        return instrumento;
    }

    /**
     * Referencia al Instrumento de la Emisi&oacute;n (tipo valor).
     * 
     * @param instrumento
     */
    public void setInstrumento(Instrumento instrumento) {
        this.instrumento = instrumento;
    }

    /**
     * Obtiene el campo cupon
     * 
     * @return cupon
     */
    public Set<Cupon> getCupon() {
        return cupon;
    }

    /**
     * Asigna el campo cupon
     * 
     * @param cupon
     *            el valor de cupon a asignar
     */
    public void setCupon(Set<Cupon> cupon) {
        this.cupon = cupon;
    }

    /**
     * Referencia al identificador del instrumento de la Emisi&oacute;n (tipo
     * valor).
     * 
     * @return BigInteger
     */
    public BigInteger getIdInstrumento() {
        return idInstrumento;
    }

    /**
     * Referencia al identificador del instrumento de la Emisi&oacute;n (tipo
     * valor).
     * 
     * @param idInstrumento
     */
    public void setIdInstrumento(BigInteger idInstrumento) {
        this.idInstrumento = idInstrumento;
    }

    /**
     * Referencia a la Emisora de la Emisi&oacute;n.
     * 
     * @return BigInteger
     */
    public BigInteger getIdEmisora() {
        return idEmisora;
    }

    /**
     * Referencia a la Emisora de la Emisi&oacute;n.
     * 
     * @param idEmisora
     */
    public void setIdEmisora(BigInteger idEmisora) {
        this.idEmisora = idEmisora;
    }

    /**
     * Serie de la Emisi&oacute;n. Indica la fecha en la que vence el papel.
     * 
     * @return String
     */
    public String getSerie() {
        return serie;
    }

    /**
     * Serie de la Emisi&oacute;n. Indica la fecha en la que vence el papel.
     * 
     * @param serie
     */
    public void setSerie(String serie) {
        this.serie = serie;
    }

    /**
     * C&oacute;digo de Identificaci&oacute;n del instrumento financiero
     * (Homologaci&oacute;n con ISIN ISO 6166).
     * 
     * @return String
     */
    public String getIsin() {
        return isin;
    }

    /**
     * C&oacute;digo de Identificaci&oacute;n del instrumento financiero
     * (Homologaci&oacute;n con ISIN ISO 6166).
     * 
     * @param isin
     */
    public void setIsin(String isin) {
        this.isin = isin;
    }

    /**
     * Es el precio de referencia, expresado en unidades correspondientes a la
     * divisa asociada, que aparece en los títulos en el momento de su
     * emisi&oacute;n, como expresi&oacute;n de parte del capital contable que
     * represente y como antecedente para definir el precio de su
     * suscripci&oacute;n. En los títulos de deuda, el valor nominal es el valor
     * del título a vencimiento.
     * 
     * @return BigDecimal
     */
    public BigDecimal getValorNominal() {
        return valorNominal;
    }

    /**
     * Es el precio de referencia, expresado en unidades correspondientes a la
     * divisa asociada, que aparece en los títulos en el momento de su
     * emisi&oacute;n, como expresi&oacute;n de parte del capital contable que
     * represente y como antecedente para definir el precio de su
     * suscripci&oacute;n. En los títulos de deuda, el valor nominal es el valor
     * del título a vencimiento.
     * 
     * @param valorNominal
     */
    public void setValorNominal(BigDecimal valorNominal) {
        this.valorNominal = valorNominal;
    }

    /**
     * @param fechaVencimiento the fechaVencimiento to set
     */
    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    /**
     * @param boveda the boveda to set
     */
    public void setBoveda(Boveda boveda) {
        this.boveda = boveda;
    }

    /**
     * @param idCruceBmv the idCruceBmv to set
     */
    public void setIdCruceBmv(BigInteger idCruceBmv) {
        this.idCruceBmv = idCruceBmv;
    }

    /**
     * @param emisionExtranjera the emisionExtranjera to set
     */
    public void setEmisionExtranjera(String emisionExtranjera) {
        this.emisionExtranjera = emisionExtranjera;
    }

    /**
     * @param esDepositoGenerado the esDepositoGenerado to set
     */
    public void setEsDepositoGenerado(BigDecimal esDepositoGenerado) {
        this.esDepositoGenerado = esDepositoGenerado;
    }

    /**
     * @param idEstatusEmision the idEstatusEmision to set
     */
    public void setIdEstatusEmision(BigInteger idEstatusEmision) {
        this.idEstatusEmision = idEstatusEmision;
    }

    /**
     * @param idFrecuenciaPago the idFrecuenciaPago to set
     */
    public void setIdFrecuenciaPago(BigInteger idFrecuenciaPago) {
        this.idFrecuenciaPago = idFrecuenciaPago;
    }

    /**
     * @param esGenerarIsin the esGenerarIsin to set
     */
    public void setEsGenerarIsin(BigDecimal esGenerarIsin) {
        this.esGenerarIsin = esGenerarIsin;
    }

    /**
     * @param fechaLimiteMovimientos the fechaLimiteMovimientos to set
     */
    public void setFechaLimiteMovimientos(Date fechaLimiteMovimientos) {
        this.fechaLimiteMovimientos = fechaLimiteMovimientos;
    }

    /**
     * @param primerFechaPago the primerFechaPago to set
     */
    public void setPrimerFechaPago(Date primerFechaPago) {
        this.primerFechaPago = primerFechaPago;
    }

    /**
     * @param cantidadMinimaOperacion the cantidadMinimaOperacion to set
     */
    public void setCantidadMinimaOperacion(BigDecimal cantidadMinimaOperacion) {
        this.cantidadMinimaOperacion = cantidadMinimaOperacion;
    }

    /**
     * @param idCodigoCirculacion the idCodigoCirculacion to set
     */
    public void setIdCodigoCirculacion(BigInteger idCodigoCirculacion) {
        this.idCodigoCirculacion = idCodigoCirculacion;
    }

    /**
     * @param idCodigoAmortizacion the idCodigoAmortizacion to set
     */
    public void setIdCodigoAmortizacion(BigInteger idCodigoAmortizacion) {
        this.idCodigoAmortizacion = idCodigoAmortizacion;
    }

    /**
     * @param idCertificacionEmision the idCertificacionEmision to set
     */
    public void setIdCertificacionEmision(BigInteger idCertificacionEmision) {
        this.idCertificacionEmision = idCertificacionEmision;
    }

    /**
     * @param colocacion the colocacion to set
     */
    public void setColocacion(String colocacion) {
        this.colocacion = colocacion;
    }

    /**
     * @param fechaPagoAnioCal the fechaPagoAnioCal to set
     */
    public void setFechaPagoAnioCal(Date fechaPagoAnioCal) {
        this.fechaPagoAnioCal = fechaPagoAnioCal;
    }

    /**
     * @param fechaBaja the fechaBaja to set
     */
    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    /**
     * @param fechaCreacion the fechaCreacion to set
     */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * @param fechaUltModificacion the fechaUltModificacion to set
     */
    public void setFechaUltModificacion(Date fechaUltModificacion) {
        this.fechaUltModificacion = fechaUltModificacion;
    }

    /**
     * @param idOrigenTransaccion the idOrigenTransaccion to set
     */
    public void setIdOrigenTransaccion(BigInteger idOrigenTransaccion) {
        this.idOrigenTransaccion = idOrigenTransaccion;
    }

    /**
     * @param acuse the acuse to set
     */
    public void setAcuse(BigDecimal acuse) {
        this.acuse = acuse;
    }

    /**
     * @param idTipoValorBmv the idTipoValorBmv to set
     */
    public void setIdTipoValorBmv(BigInteger idTipoValorBmv) {
        this.idTipoValorBmv = idTipoValorBmv;
    }

}

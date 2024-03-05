/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.StringUtils;


/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class EmisionPersistence implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Emision_extr = ADCP */
	public static final String ADCP = "ADCP";

	/** Emision_extr = ADR */
	public static final String ADR = "ADR";

	/** Emision_extr = BADC */
	public static final String BADC = "BADC";

	/** Emision_extr = CPOS */
	public static final String CPOS = "CPOS";

	/** Emision_extr = GADC */
	public static final String GADC = "GADC";

	/** Emision_extr = IADC */
	public static final String IADC = "IADC";

	/** Emision_extr = ICPO */
	public static final String ICPO = "ICPO";

	/** Emision_extr = LIBR */
	public static final String LIBR = "LIBR";

	/** Emision_extr = VIVI */
	public static final String VIVI = "VIVI";

	/** Emision_extr = VI */
	public static final String VI = "VI";

	/** mercado = PG */
	public static final String PG = "PG";

	/** mercado = PB */
	public static final String PB = "PB";

	/** mercado = MC */
	public static final String MC = "MC";

	private EmisionPK emisionPk;

	private Date fechaEmision;

	private String cuponCortado;

	private String emisionExtra;

	private Date fechaVencimiento;

	private String isin;

	private BigDecimal ultimoHecho;

	private BigDecimal valorNominal;

	private Double topeExtr;

	private BigDecimal precioVector;

	private String divisa;

	private String cuenta;

	private BigDecimal tasaInteres;

	private Integer diasNat;

	private BigDecimal tasaDescuento;

	private BigDecimal tasaRendimiento;

	private AgentePersistence agente;

	private InstrumentoVO instrumento;

	private Set prestamos;

	private Set knetos;

	private Set valores;

	/**
     * @return the emisionPk
     */
    public EmisionPK getEmisionPk() {
        return emisionPk;
    }

    /**
     * @return the fechaEmision
     */
    public Date getFechaEmision() {
        return fechaEmision;
    }

    /**
     * @return the cuponCortado
     */
    public String getCuponCortado() {
        return cuponCortado;
    }

    /**
     * @return the emisionExtra
     */
    public String getEmisionExtra() {
        return emisionExtra;
    }

    /**
     * @return the fechaVencimiento
     */
    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    /**
     * @return the isin
     */
    public String getIsin() {
        return isin;
    }

    /**
     * @return the ultimoHecho
     */
    public BigDecimal getUltimoHecho() {
        return ultimoHecho;
    }

    /**
     * @return the valorNominal
     */
    public BigDecimal getValorNominal() {
        return valorNominal;
    }

    /**
     * @return the topeExtr
     */
    public Double getTopeExtr() {
        return topeExtr;
    }

    /**
     * @return the precioVector
     */
    public BigDecimal getPrecioVector() {
        return precioVector;
    }

    /**
     * @return the divisa
     */
    public String getDivisa() {
        return divisa;
    }

    /**
     * @return the cuenta
     */
    public String getCuenta() {
        return cuenta;
    }

    /**
     * @return the tasaInteres
     */
    public BigDecimal getTasaInteres() {
        return tasaInteres;
    }

    /**
     * @return the diasNat
     */
    public Integer getDiasNat() {
        return diasNat;
    }

    /**
     * @return the tasaDescuento
     */
    public BigDecimal getTasaDescuento() {
        return tasaDescuento;
    }

    /**
     * @return the tasaRendimiento
     */
    public BigDecimal getTasaRendimiento() {
        return tasaRendimiento;
    }

    /**
     * @return the agente
     */
    public AgentePersistence getAgente() {
        return agente;
    }

    /**
     * @return the prestamos
     */
    public Set getPrestamos() {
        return prestamos;
    }

    /**
     * @return the knetos
     */
    public Set getKnetos() {
        return knetos;
    }

    /**
     * @return the valores
     */
    public Set getValores() {
        return valores;
    }

    /**
	 * @param emisionPk
	 */
	public void setEmisionPk(EmisionPK emisionPk) {
		this.emisionPk = emisionPk;
	}

	/**
	 * @param precioVector
	 */
	public void setPrecioVector(BigDecimal precioVector) {
		this.precioVector = precioVector;
	}

	/**
	 * @param cuponCortado
	 */
	public void setCuponCortado(String cuponCortado) {
		this.cuponCortado = cuponCortado;
	}

	/**
	 * @param divisa
	 */
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}

	/**
	 * @param fechaEmision
	 */
	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	/**
	 * @param fechaVencimiento
	 */
	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	/**
	 * @param isin
	 */
	public void setIsin(String isin) {
		this.isin = isin;
	}

	/**
	 * @param valorNominal
	 */
	public void setValorNominal(BigDecimal valorNominal) {
		this.valorNominal = valorNominal;
	}

	/**
	 * @param ultimoHecho
	 */
	public void setUltimoHecho(BigDecimal ultimoHecho) {
		this.ultimoHecho = ultimoHecho;
	}

	/**
	 * @param topeExtr
	 */
	public void setTopeExtr(Double topeExtr) {
		this.topeExtr = topeExtr;
	}

	/**
	 * @param emisionExtra
	 */
	public void setEmisionExtra(String emisionExtra) {
		this.emisionExtra = emisionExtra;
	}

	/**
	 * @param cuenta
	 */
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	/**
	 * @param agente
	 */
	public void setAgente(AgentePersistence agente) {
		this.agente = agente;
	}

	/**
	 * @param prestamos
	 */
	public void setPrestamos(Set prestamos) {
		this.prestamos = prestamos;
	}

	/**
	 * @param knetos
	 */
	public void setKnetos(Set knetos) {
		this.knetos = knetos;
	}

	/**
	 * @param valores
	 */
	public void setValores(Set valores) {
		this.valores = valores;
	}

	/**
	 * @param tasaInteres
	 */
	public void setTasaInteres(BigDecimal tasaInteres) {
		this.tasaInteres = tasaInteres;
	}

	/**
	 * @param diasNat
	 */
	public void setDiasNat(Integer diasNat) {
		this.diasNat = diasNat;
	}

	/**
	 * @param tasaDescuento
	 */
	public void setTasaDescuento(BigDecimal tasaDescuento) {
		this.tasaDescuento = tasaDescuento;
	}

	/**
	 * @param tasaRendimiento
	 */
	public void setTasaRendimiento(BigDecimal tasaRendimiento) {
		this.tasaRendimiento = tasaRendimiento;
	}

	/**
	 * Verifica que este pojo sea cup&oacute;n cortado. Es cup&oacute;n cortado
	 * si el campo cupon_cortado = F
	 * 
	 * @return True si es cupon cortado. False lo contrario.
	 */
	public boolean esCuponCortado() {
		if (getCuponCortado() != null && getCuponCortado().equals("F")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Verifica que este pojo sea negociable. Es negociable si
	 * 
	 * @return True si es negociable. False lo contrario.
	 */
	public boolean esNegociable() {
		boolean esNegociable = true;
		return esNegociable;
	}

	/**
	 * Verifica que este pojo sea reportable. Es negociable si
	 * 
	 * @return True si es reportable. False lo contrario.
	 */
	public boolean esReportable() {
		boolean esReportable = true;
		return esReportable;
	}

	/**
	 * Valida si el campo emision_extr = ADCP
	 * 
	 * @return True si el valor de este campo es ADCP. False lo contrario.
	 */
	public boolean esEmisionExtrADCP() {
		if (StringUtils.isNotBlank(emisionExtra) && emisionExtra.equalsIgnoreCase(ADCP))
			return true;
		return false;
	}

	/**
	 * Valida si el campo emision_extr = CPOS
	 * 
	 * @return True si el valor de este campo es CPOS. False lo contrario.
	 */
	public boolean esEmisionExtrCPOS() {
		if (StringUtils.isNotBlank(emisionExtra) && emisionExtra.equalsIgnoreCase(CPOS))
			return true;
		return false;
	}

	/**
	 * Obtiene el campo instrumento
	 * 
	 * @return instrumento
	 */
	public InstrumentoVO getInstrumento() {
		return instrumento;
	}

	/**
	 * Asigna el campo instrumento
	 * 
	 * @param instrumento
	 *            el valor de instrumento a asignar
	 */
	public void setInstrumento(InstrumentoVO instrumento) {
		this.instrumento = instrumento;
	}

	/**
	 * Valida si el campo emision_extr comienza con VI
	 * 
	 * @return True si el valor de este campo es VI*. False lo contrario.
	 */
	public boolean emisionExtrStartsVI() {
		if (StringUtils.isNotBlank(emisionExtra) && emisionExtra.startsWith(VI))
			return true;
		return false;
	}

	/**
	 * Valida que el tipo valor de la emision no sea NULL ni VACIO
	 * 
	 * @return boolean
	 */
	public boolean tieneTvValida() {
		boolean tieneTvValida = false;
		if (this.getInstrumento() != null && StringUtils.isNotBlank(this.getInstrumento().getTv())) {
			tieneTvValida = true;
		}
		return tieneTvValida;
	}

	/**
	 * Verifica que el campo emisionExtra sea igual a ADCP
	 * 
	 * @return True si y solo si emisonExtra es igual a ADCP. False lo contrario
	 */
	public boolean esADCP() {
		return esClaveAlta(ADCP);
	}

	/**
	 * Verifica que el campo emisionExtra sea igual a ADR
	 * 
	 * @return True si y solo si emisonExtra es igual a ADR. False lo contrario
	 */
	public boolean esADR() {
		return esClaveAlta(ADR);
	}

	/**
	 * Verifica que el campo emisionExtra sea igual a BADC
	 * 
	 * @return True si y solo si emisonExtra es igual a BADC. False lo contrario
	 */
	public boolean esBADC() {
		return esClaveAlta(BADC);
	}

	/**
	 * Verifica que el campo emisionExtra sea igual a CPOS
	 * 
	 * @return True si y solo si emisonExtra es igual a CPOS. False lo contrario
	 */
	public boolean esCPOS() {
		return esClaveAlta(CPOS);
	}

	/**
	 * Verifica que el campo emisionExtra sea igual a GADC
	 * 
	 * @return True si y solo si emisonExtra es igual a GADC. False lo contrario
	 */
	public boolean esGADC() {
		return esClaveAlta(GADC);
	}

	/**
	 * Verifica que el campo emisionExtra sea igual a ICPO
	 * 
	 * @return True si y solo si emisonExtra es igual a ICPO. False lo contrario
	 */
	public boolean esICPO() {
		return esClaveAlta(ICPO);
	}

	/**
	 * Verifica que el campo emisionExtra sea igual a ICDC
	 * 
	 * @return True si y solo si emisonExtra es igual a ICDC. False lo contrario
	 */
	public boolean esIADC() {
		return esClaveAlta(IADC);
	}

	/**
	 * Verifica que el campo emisionExtra sea igual a LIBR
	 * 
	 * @return True si y solo si emisonExtra es igual a LIBR. False lo contrario
	 */
	public boolean esLIBR() {
		return esClaveAlta(LIBR);
	}

	/**
	 * Verifica que el campo emisionExtra sea igual a LIBR
	 * 
	 * @return True si y solo si emisonExtra es igual a LIBR. False lo contrario
	 */
	public boolean esVIVI() {
		return esClaveAlta(VIVI);
	}

	/**
	 * Valida que la clave de alta (emisionExtra) sea igual al parametro.-
	 * 
	 * @param emisionExtra
	 *            la clave de alta a comparar
	 * @return true si la clave pasada como parametro es igual al campo
	 *         emisionExtra. False lo contrario.
	 */
	private boolean esClaveAlta(String emisionExtra) {
		if (StringUtils.isNotBlank(getEmisionExtra()) && getEmisionExtra().trim().equalsIgnoreCase(emisionExtra)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Valida si este tipo de emision es de mercado de dinero. Atraves del
	 * atributo mercado del instrumento relacionado a esta emision.
	 * 
	 * @return boolean - true si y solo si instrumento.mercado = PB &oacute; PG.
	 *         false lo contrario.
	 */
	public boolean esEmisionMercadoDinero() {

		return esEmisionTipoUso(PB) || esEmisionTipoUso(PG);
	}

	/**
	 * Valida si este tipo de emision es de mercado de capitales. Atraves del
	 * atributo mercado del instrumento relacionado a esta emision.
	 * 
	 * @return True si y solo si instrumento.mercado es = MC. False lo
	 *         contrario.
	 */
	public boolean esEmisionMercadoCapitales() {
		return esEmisionTipoUso(MC);
	}

	/**
	 * Verifica que el tipo de mercado sea del mismo tipo que el mercado del
	 * instrumento
	 * 
	 * @param tipoMercado
	 *            el tipo de mercado a comparar con el uso en el instrumento.
	 * @return true si y solo si el tipo de mercado empata con el del
	 *         instrumento. False lo contrario
	 */
	private boolean esEmisionTipoUso(String tipoMercado) {
		boolean flag = false;
		if (getInstrumento() != null) {
			String mercado = getInstrumento().getMercado();
			if (StringUtils.isNotEmpty(mercado) && mercado.trim().equalsIgnoreCase(tipoMercado)) {
				flag = true;
			}
		}
		return flag;
	}

}

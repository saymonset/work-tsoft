/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadodinero;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class RegistraOperacionParams extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	private AgenteVO traspasante;

	private AgenteVO receptor;

	private EmisionVO emision;

	private String rol;

	private Integer diasVigentes;

	private String isin;

	private BigDecimal precioVector;

	private Integer saldoDisponible;

	private Integer posicionActual;

	private BigInteger simulado;

	private BigDecimal netoEfectivo;

	private String idTasaReferencia;

	private Integer plazoReporto;

	private Integer plazoLiquidacion;

	private Date fechaRegreso;

	private BigDecimal tasaPremio;

	private Date fechaConcertacion;

	private BigDecimal cantidadOperada;

	private String idValorEn;

	private BigDecimal precioTitulo;

	private BigDecimal importe;

	private BigDecimal premio;

	private String descripcion;

	private String claveReporto;

	private Integer diasPlazo;

	private Date fechaReporto;

	private String sociedad;

	private String bajaLogica;

	private Date fechaLiq;
	
	private Date fechaHoraCierreOper;

	private Integer liq;

	private String divisa;
	
	private String boveda;
	
	private BigInteger idBoveda;

	/** Constructor */
	public RegistraOperacionParams() {
		this.diasVigentes = INTEGER_ZERO;
		this.precioVector = BIG_DECIMAL_ZERO;
		this.saldoDisponible = INTEGER_ZERO;
		this.posicionActual = INTEGER_ZERO;
		this.simulado = BIG_INTEGER_ZERO;
		this.netoEfectivo = BIG_DECIMAL_ZERO;
		this.plazoReporto = INTEGER_ZERO;
		this.plazoLiquidacion = INTEGER_ZERO;
		this.tasaPremio = BIG_DECIMAL_ZERO;
		this.cantidadOperada = BIG_DECIMAL_ZERO;
		this.precioTitulo = BIG_DECIMAL_ZERO;
		this.importe = BIG_DECIMAL_ZERO;
		this.premio = BIG_DECIMAL_ZERO;
		this.diasPlazo = INTEGER_ZERO;
		this.liq = INTEGER_ZERO;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getCantidadOperada() {
		return cantidadOperada;
	}

	/**
	 * @param cantidadOperada
	 */
	public void setCantidadOperada(BigDecimal cantidadOperada) {
		this.cantidadOperada = cantidadOperada;
	}

	/**
	 * @return String
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return Integer
	 */
	public Integer getDiasVigentes() {
		return diasVigentes;
	}

	/**
	 * @param diasVigentes
	 */
	public void setDiasVigentes(Integer diasVigentes) {
		this.diasVigentes = diasVigentes;
	}

	/**
	 * @return EmisionVO
	 */
	public EmisionVO getEmision() {
		return emision;
	}

	/**
	 * @param emision
	 */
	public void setEmision(EmisionVO emision) {
		this.emision = emision;
	}

	/**
	 * @return Date
	 */
	public Date getFechaConcertacion() {
		return fechaConcertacion;
	}

	/**
	 * @param fechaConcertacion
	 */
	public void setFechaConcertacion(Date fechaConcertacion) {
		this.fechaConcertacion = clona(fechaConcertacion);
	}

	/**
	 * @return Date
	 */
	public Date getFechaRegreso() {
		return fechaRegreso;
	}

	/**
	 * @param fechaRegreso
	 */
	public void setFechaRegreso(Date fechaRegreso) {
		this.fechaRegreso = clona(fechaRegreso);
	}

	/**
	 * @return String
	 */
	public String getIdTasaReferencia() {
		return idTasaReferencia;
	}

	/**
	 * @param idTasaReferencia
	 */
	public void setIdTasaReferencia(String idTasaReferencia) {
		this.idTasaReferencia = idTasaReferencia;
	}

	/**
	 * @return String
	 */
	public String getIdValorEn() {
		return idValorEn;
	}

	/**
	 * @param idValorEn
	 */
	public void setIdValorEn(String idValorEn) {
		this.idValorEn = idValorEn;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getImporte() {
		return importe;
	}

	/**
	 * @param importe
	 */
	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	/**
	 * @return String
	 */
	public String getIsin() {
		return isin;
	}

	/**
	 * @param isin
	 */
	public void setIsin(String isin) {
		this.isin = isin;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getNetoEfectivo() {
		return netoEfectivo;
	}

	/**
	 * @param netoEfectivo
	 */
	public void setNetoEfectivo(BigDecimal netoEfectivo) {
		this.netoEfectivo = netoEfectivo;
	}

	/**
	 * @return Integer
	 */
	public Integer getPlazoLiquidacion() {
		return plazoLiquidacion;
	}

	/**
	 * @param plazoLiquidacion
	 */
	public void setPlazoLiquidacion(Integer plazoLiquidacion) {
		this.plazoLiquidacion = plazoLiquidacion;
	}

	/**
	 * @return Integer
	 */
	public Integer getPlazoReporto() {
		return plazoReporto;
	}

	/**
	 * @param plazoReporto
	 */
	public void setPlazoReporto(Integer plazoReporto) {
		this.plazoReporto = plazoReporto;
	}

	/**
	 * @return Integer
	 */
	public Integer getPosicionActual() {
		return posicionActual;
	}

	/**
	 * @param posicionActual
	 */
	public void setPosicionActual(Integer posicionActual) {
		this.posicionActual = posicionActual;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getPrecioTitulo() {
		return precioTitulo;
	}

	/**
	 * @param precioTitulo
	 */
	public void setPrecioTitulo(BigDecimal precioTitulo) {
		this.precioTitulo = precioTitulo;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getPrecioVector() {
		return precioVector;
	}

	/**
	 * @param precioVector
	 */
	public void setPrecioVector(BigDecimal precioVector) {
		this.precioVector = precioVector;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getPremio() {
		return premio;
	}

	/**
	 * @param premio
	 */
	public void setPremio(BigDecimal premio) {
		this.premio = premio;
	}

	/**
	 * @return AgenteVO
	 */
	public AgenteVO getReceptor() {
		return receptor;
	}

	/**
	 * @param receptor
	 */
	public void setReceptor(AgenteVO receptor) {
		this.receptor = receptor;
	}

	/**
	 * @return Integer
	 */
	public Integer getSaldoDisponible() {
		return saldoDisponible;
	}

	/**
	 * @param saldoDisponible
	 */
	public void setSaldoDisponible(Integer saldoDisponible) {
		this.saldoDisponible = saldoDisponible;
	}

	/**
	 * @return BigInteger
	 */
	public BigInteger getSimulado() {
		return simulado;
	}

	/**
	 * @param simulado
	 */
	public void setSimulado(BigInteger simulado) {
		this.simulado = simulado;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getTasaPremio() {
		return tasaPremio;
	}

	/**
	 * @param tasaPremio
	 */
	public void setTasaPremio(BigDecimal tasaPremio) {
		this.tasaPremio = tasaPremio;
	}

	/**
	 * @return AgenteVO
	 */
	public AgenteVO getTraspasante() {
		return traspasante;
	}

	/**
	 * @param traspasante
	 */
	public void setTraspasante(AgenteVO traspasante) {
		this.traspasante = traspasante;
	}

	/**
	 * @return String
	 */
	public String getBajaLogica() {
		return bajaLogica;
	}

	/**
	 * @return String
	 */
	public String getClaveReporto() {
		return claveReporto;
	}

	/**
	 * @return Integer
	 */
	public Integer getDiasPlazo() {
		return diasPlazo;
	}

	/**
	 * @return String
	 */
	public String getDivisa() {
		return divisa;
	}

	/**
	 * @return Date
	 */
	public Date getFechaLiq() {
		return fechaLiq;
	}

	public Date getFechaHoraCierreOper() {
		return fechaHoraCierreOper;
	}

	/**
	 * @return Date
	 */
	public Date getFechaReporto() {
		return fechaReporto;
	}

	/**
	 * @return Integer
	 */
	public Integer getLiq() {
		return liq;
	}

	/**
	 * @return String
	 */
	public String getSociedad() {
		return sociedad;
	}

	/**
	 * @param bajaLogica
	 */
	public void setBajaLogica(String bajaLogica) {
		this.bajaLogica = bajaLogica;
	}

	/**
	 * @param claveReporto
	 */
	public void setClaveReporto(String claveReporto) {
		this.claveReporto = claveReporto;
	}

	/**
	 * @param diasPlazo
	 */
	public void setDiasPlazo(Integer diasPlazo) {
		this.diasPlazo = diasPlazo;
	}

	/**
	 * @param divisa
	 */
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}

	/**
	 * @param fechaLiq
	 */
	public void setFechaLiq(Date fechaLiq) {
		this.fechaLiq = clona(fechaLiq);
	}
	
	public void setFechaHoraCierreOper(Date fechaHoraCierreOper) {
		this.fechaHoraCierreOper = fechaHoraCierreOper;
	}

	/**
	 * @param fechaReporto
	 */
	public void setFechaReporto(Date fechaReporto) {
		this.fechaReporto = clona(fechaReporto);
	}

	/**
	 * @param liq
	 */
	public void setLiq(Integer liq) {
		this.liq = liq;
	}

	/**
	 * @param sociedad
	 */
	public void setSociedad(String sociedad) {
		this.sociedad = sociedad;
	}

	/**
	 * @return String
	 */
	public String getRol() {
		return rol;
	}

	/**
	 * @param rol
	 */
	public void setRol(String rol) {
		this.rol = rol;
	}

	/**
	 * Valida que el objeto tenga todos los atributos requeridos y coloca en
	 * cero precioTitulo y tasaPremio si estos traen NULL
	 * 
	 * @throws BusinessException
	 */
	public void esValido() throws BusinessException {

		if (StringUtils.isBlank(this.getClaveReporto())) {
			throw new BusinessException("La Clave Reporto es NULL");
		}
		if (this.getPlazoReporto() == null) {
			throw new BusinessException("Los Dias Plazo es NULL");
		}
		if (this.getFechaRegreso() == null) {
			throw new BusinessException("La Fecha de Regreso es NULL");
		}
		if (StringUtils.isBlank(this.getRol())) {
			throw new BusinessException("El rol es NULL");
		}
		if (this.getPlazoLiquidacion() == null) {
			throw new BusinessException("El plazo de liquidacion es NULL");
		}
		if (this.getTraspasante() == null) {
			throw new BusinessException("El traspasante es NULL");
		}
		if (this.getEmision() == null) {
			throw new BusinessException("La emision es NULL");
		}
		this.getEmision().tienePKValida();

		if (this.getCantidadOperada() == null) {
			throw new BusinessException("La Cantidad Operada es requerida");
		} else if (!(this.getCantidadOperada().compareTo(BIG_DECIMAL_ZERO) > 0)) {
			throw new BusinessException("La Cantidad Operada debe ser mayor a cero");

		}

		// si no es un traspaso libre de pago T se valida el campo en las demas
		// operaciones
		// fase 3,5,7
		if (!this.getClaveReporto().equalsIgnoreCase("T") && !this.getClaveReporto().equalsIgnoreCase("F")) {

			if (this.getPrecioTitulo().equals(BIG_DECIMAL_ZERO)) {

				throw new BusinessException("El precio por t\u00edtulo debe ser mayor a cero ");

			}

		}
		// Se validan los campos segun la operacion.
		if (this.getClaveReporto().equalsIgnoreCase("C") || this.getClaveReporto().equalsIgnoreCase("D") || this.getClaveReporto().equalsIgnoreCase("E")
				|| this.getClaveReporto().equalsIgnoreCase("R") || this.getClaveReporto().equalsIgnoreCase("Y")) {

			if (this.getPlazoReporto().equals(INTEGER_ZERO) || this.getPlazoReporto().compareTo(INTEGER_ZERO) < INTEGER_ZERO.intValue()) {
				throw new BusinessException("EL Plazo del reporto debe ser minimo uno ");
			}

			if (this.getTasaPremio().equals(BIG_DECIMAL_ZERO)) {
				throw new BusinessException("La Tasa Premio debe ser mayor a cero ");
			}

		}

	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object obj, Errors errors) {

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
     * @return the boveda
     */
	public String getBoveda() {
		return boveda;
	}

	/**
     * @param boveda the boveda to set
     */
	public void setBoveda(String boveda) {
		this.boveda = boveda;
	}

}

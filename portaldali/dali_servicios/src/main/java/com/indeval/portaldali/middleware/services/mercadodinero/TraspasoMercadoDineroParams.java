/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadodinero;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class TraspasoMercadoDineroParams extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	/** Indica que el tipo de operacion es TRASPASO */
	public static final String TRASPASO = "TRASPASO";

	/** Indica que el tipo de operacion es APERTURA */
	public static final String APERTURA = "APERTURA";
	
	public static final String MERCADO_DINERO = "Dinero";
	
	public static final String MERCADO_CAPITAL = "Capital";

	private AgenteVO traspasante;

	private AgenteVO receptor;

	private String idTipoOperacion; // [aperturaSistema|traspasoMiscelaneaFiscal]

	private boolean mercadoDinero; // true= es mercado de dinero, false= es
	// mercado de capitales

	private String tipoMovimiento;

	private EmisionVO emision;

	private BigDecimal cantidad;

	private Date fechaAdquisicion;
	
	private Date fechaHoraCierreOper;

	private BigDecimal precioAdquisicion;

	private String cliente;

	private String rfcCURP;

	/** Costo promedio actualizado */
    private BigDecimal costoPromedio;
    
	/**
	 * Constructor
	 */
	public TraspasoMercadoDineroParams() {
		mercadoDinero = true;
	}

	/**
	 * Define si el cargo de la apertura se le aplica al traspasante (true) o al
	 * receptor (false)
	 */
	private boolean aceptaCargo;

	/**
	 * @return String
	 */
	public String getTipoMovimiento() {
		return tipoMovimiento;
	}

	/**
	 * @param tipoMovimiento
	 */
	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getCantidad() {
		return cantidad;
	}

	/**
	 * @param cantidad
	 */
	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * @return String
	 */
	public String getCliente() {
		return cliente;
	}

	/**
	 * @param cliente
	 */
	public void setCliente(String cliente) {
		this.cliente = cliente;
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
	public Date getFechaAdquisicion() {
		return fechaAdquisicion;
	}

	/**
	 * @param fechaAdquisicion
	 */
	public void setFechaAdquisicion(Date fechaAdquisicion) {
		this.fechaAdquisicion = clona(fechaAdquisicion);
	}

	/**
	 * @return String
	 */
	public String getIdTipoOperacion() {
		return idTipoOperacion;
	}

	/**
	 * @param idTipoOperacion
	 */
	public void setIdTipoOperacion(String idTipoOperacion) {
		this.idTipoOperacion = idTipoOperacion;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getPrecioAdquisicion() {
		return precioAdquisicion;
	}

	/**
	 * @param precioAdquisicion
	 */
	public void setPrecioAdquisicion(BigDecimal precioAdquisicion) {
		this.precioAdquisicion = precioAdquisicion;
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
	 * @return String
	 */
	public String getRfcCURP() {
		return rfcCURP;
	}

	/**
	 * @param rfcCURP
	 */
	public void setRfcCURP(String rfcCURP) {
		this.rfcCURP = rfcCURP;
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
	 * Valida el tipo de movimiento
	 * 
	 * @throws BusinessException
	 */
	public void validaTipoMovimiento() throws BusinessException {

		try {
			Assert.isTrue(StringUtils.isNotBlank(this.getTipoMovimiento()), "Falta el tipo de movimiento");

			Assert.isTrue(this.getTipoMovimiento().trim().equalsIgnoreCase(APERTURA) || this.getTipoMovimiento().trim().equalsIgnoreCase(TRASPASO), "Error: ["
					+ this.getTipoMovimiento() + "] es un valor invalido para el parametro tipo movimiento.");
		} catch (IllegalArgumentException e) {
			throw new BusinessException(e.getMessage());
		}

	}

	/**
	 * Valida que el objeto contenga todos los atributos requeridos
	 * 
	 * @throws BusinessException
	 */
	public void esValido() throws BusinessException {
		if (this.getEmision() == null) {
			throw new BusinessException("ERROR: El objeto params tiene la EMISION NULL");
		}
		this.getEmision().tienePKValida();
		if (this.getTraspasante() == null) {
			throw new BusinessException("ERROR: El objeto params tiene el TRASPASANTE NULL");
		}
		this.getTraspasante().tieneClaveValida();
		if (this.getReceptor() == null) {
			throw new BusinessException("ERROR: El objeto params tiene el RECEPTOR NULL");
		}
		this.getReceptor().tieneClaveValida();
		if (StringUtils.isBlank(this.getTipoMovimiento())) {
			throw new BusinessException("Error: El parametro tipo movimiento es NULL o VACIO");
		}
		this.validaTipoMovimiento();

		if (this.getPrecioAdquisicion() == null || !(this.getPrecioAdquisicion().compareTo(BIG_DECIMAL_ZERO) > 0)) {
			throw new BusinessException("Error: El precio de adquisicion no puedes ser nulo, ni menor o igual a 0");
		}
	}

	/**
	 * @return boolean
	 */
	public boolean isAceptaCargo() {
		return aceptaCargo;
	}

	/**
	 * @param aceptaCargo
	 */
	public void setAceptaCargo(boolean aceptaCargo) {
		this.aceptaCargo = aceptaCargo;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object obj, Errors errors) {
	}

	/**
	 * @return the mercadoDinero
	 */
	public boolean isMercadoDinero() {
		return mercadoDinero;
	}

	/**
	 * @param mercadoDinero
	 *            the mercadoDinero to set
	 */
	public void setMercadoDinero(boolean mercadoDinero) {
		this.mercadoDinero = mercadoDinero;
	}

	/**
	 * @return the costoPromedio
	 */
	public BigDecimal getCostoPromedio() {
		return costoPromedio;
	}

	/**
	 * @param costoPromedio the costoPromedio to set
	 */
	public void setCostoPromedio(BigDecimal costoPromedio) {
		this.costoPromedio = costoPromedio;
	}

	public Date getFechaHoraCierreOper() {
		return fechaHoraCierreOper;
	}

	public void setFechaHoraCierreOper(Date fechaHoraCierreOper) {
		this.fechaHoraCierreOper = fechaHoraCierreOper;
	}

}

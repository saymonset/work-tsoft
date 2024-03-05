package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @version 1.0
 * @created 06-dic-2007 10:15:43 a.m.
 */
public class PosicionDTO implements Serializable {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * Formato de numrico para las cantidades enteras de posición
	 */
	public final static DecimalFormat FORMATO_ENTERO = new DecimalFormat("###,##0");

	private static final long serialVersionUID = 1L;
	/**
	 * Emisión asociada a la posición
	 */

	private EmisionDTO emision = new EmisionDTO();
	/**
	 * Cuenta asociada a la posición
	 */
	private CuentaDTO cuenta = new CuentaDTO();
	/**
	 * bóveda asociada a la posición
	 */
	private BovedaDTO boveda = new BovedaDTO();

	/** El cupón asociado a la posición */
	private CuponDTO cupon = new CuponDTO();

	/**
	 * Identificador de la posición
	 */
	private long idPosicion;
	/**
	 * Valor de la posición disponible
	 */
	private String posicionDisponible = null;
	/**
	 * posicion disponible en formato numerico
	 */
	private BigDecimal posicionDisponibleNumerica = new BigDecimal("0.0");
	/**
	 * Valor de la posición no disponible
	 */
	private String posicionNoDisponible = null;
	/**
	 * posicion no disponible en formato numerico
	 */
	private BigDecimal posicionNoDisponibleNumerica = new BigDecimal("0.0");
	/**
	 * Indica si la posciones No Disponible es mayor a cero
	 */
	private boolean noDisponible;
	/**
	 * Valor de la posición
	 */
	private String posicion = null;
	/**
	 * posicion disponible en formato numerico
	 */
	private BigDecimal posicionNumerica = new BigDecimal("0.0");
	/**
	 * Criterio de fecha de inicio
	 */
	private Date fechaInicio = null;
	/**
	 * Criterio de fecha final
	 */
	private Date fechaFinal = null;
	/**
	 * valuacion nominal como criterios
	 */
	private BigDecimal valuacionNominal = new BigDecimal("0.0");
	/**
	 * dato para presentar en los reportes,
	 * 
	 * @return
	 */
	private String datosPipes;

	/**
	 * Valor que indica la posicion necesaria a fondear
	 */
	private BigInteger posicionFondeo = BigInteger.valueOf(0);

	/**
	 * obtiene el valor de Datos pipes: mergea
	 * //#{etq.tv}|#{etq.emisora}|#{etq.serieserie}|#{etq.cuponclave}
	 * 
	 * @return
	 */
	public String getDatosPipes() {

		if (this.emision != null && this.emision.getTipoValor() != null && !StringUtils.isEmpty(this.emision.getTipoValor().getClaveTipoValor())) {
			datosPipes = this.emision.getTipoValor().getClaveTipoValor();

			if (this.emision.getEmisora() != null) {
				datosPipes += "|" + this.emision.getEmisora().getDescripcion();

				if (this.emision.getSerie() != null) {
					datosPipes += "|" + this.emision.getSerie().getSerie();
					if (this.cupon != null)
						datosPipes += "|" + this.cupon.getClaveCupon();
				}
			}
		}
		return datosPipes;
	}

	/**
	 * método para obtener el atributo emision
	 * 
	 * @return the emision
	 */
	public EmisionDTO getEmision() {
		return emision;
	}

	/**
	 * método para establecer el atributo emision
	 * 
	 * @param emision
	 *            the emision to set
	 */
	public void setEmision(EmisionDTO emision) {
		this.emision = emision;
	}

	/**
	 * Obtiene el valor del atributo noDisponible
	 * 
	 * @return el valor del atributo noDisponible
	 */
	public boolean isNoDisponible() {
		return noDisponible;
	}

	/**
	 * Establece el valor del atributo noDisponible
	 * 
	 * @param noDisponible
	 *            el valor del atributo noDisponible a establecer.
	 */
	public void setNoDisponible(boolean noDisponible) {
		this.noDisponible = noDisponible;
	}

	/**
	 * método para obtener el atributo cuenta
	 * 
	 * @return the cuenta
	 */
	public CuentaDTO getCuenta() {
		return cuenta;
	}

	/**
	 * método para establecer el atributo cuenta
	 * 
	 * @param cuenta
	 *            the cuenta to set
	 */
	public void setCuenta(CuentaDTO cuenta) {
		this.cuenta = cuenta;
	}

	/**
	 * Obtiene el valor del atributo cupon
	 * 
	 * @return el valor del atributo cupon
	 */
	public CuponDTO getCupon() {
		return cupon;
	}

	/**
	 * Establece el valor del atributo cupon
	 * 
	 * @param cupon
	 *            el valor del atributo cupon a establecer.
	 */
	public void setCupon(CuponDTO cupon) {
		this.cupon = cupon;
	}

	/**
	 * método para obtener el atributo boveda
	 * 
	 * @return the boveda
	 */
	public BovedaDTO getBoveda() {
		return boveda;
	}

	/**
	 * método para establecer el atributo boveda
	 * 
	 * @param boveda
	 *            the boveda to set
	 */
	public void setBoveda(BovedaDTO boveda) {
		this.boveda = boveda;
	}

	/**
	 * método para obtener el atributo idPosicion
	 * 
	 * @return the idPosicion
	 */
	public long getIdPosicion() {
		return idPosicion;
	}

	/**
	 * método para establecer el atributo idPosicion
	 * 
	 * @param idPosicion
	 *            the idPosicion to set
	 */
	public void setIdPosicion(long idPosicion) {
		this.idPosicion = idPosicion;
	}

	/**
	 * Obtiene el campo fechaInicio
	 * 
	 * @return fechaInicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * Asigna el valor del campo fechaInicio
	 * 
	 * @param fechaInicio
	 *            el valor de fechaInicio a asignar
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * Obtiene el campo fechaFinal
	 * 
	 * @return fechaFinal
	 */
	public Date getFechaFinal() {
		return fechaFinal;
	}

	/**
	 * Asigna el valor del campo fechaFinal
	 * 
	 * @param fechaFinal
	 *            el valor de fechaFinal a asignar
	 */
	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	/**
	 * Obtiene el campo posicionDisponible
	 * 
	 * @return posicionDisponible
	 */
	public String getPosicionDisponible() {
		return posicionDisponible;
	}

	/**
	 * Asigna el valor del campo posicionDisponible
	 * 
	 * @param posicionDisponible
	 *            el valor de posicionDisponible a asignar
	 */
	public void setPosicionDisponible(String posicionDisponible) {
		this.posicionDisponible = posicionDisponible;
	}

	/**
	 * Obtiene el campo posicionNoDisponible
	 * 
	 * @return posicionNoDisponible
	 */
	public String getPosicionNoDisponible() {
		return posicionNoDisponible;
	}

	/**
	 * Asigna el valor del campo posicionNoDisponible
	 * 
	 * @param posicionNoDisponible
	 *            el valor de posicionNoDisponible a asignar
	 */
	public void setPosicionNoDisponible(String posicionNoDisponible) {
		this.posicionNoDisponible = posicionNoDisponible;
	}

	/**
	 * Obtiene el campo posicion
	 * 
	 * @return posicion
	 */
	public String getPosicion() {
		return posicion;
	}

	/**
	 * Asigna el valor del campo posicion
	 * 
	 * @param posicion
	 *            el valor de posicion a asignar
	 */
	public void setPosicion(String posicion) {
		this.posicion = posicion;
	}

	/**
	 * Obtiene el valor del atributo valuacionNominal
	 * 
	 * @return el valor del atributo valuacionNominal
	 */
	public BigDecimal getValuacionNominal() {
		return valuacionNominal;
	}

	/**
	 * Establece el valor del atributo valuacionNominal
	 * 
	 * @param valuacionNominal
	 *            el valor del atributo valuacionNominal a establecer
	 */
	public void setValuacionNominal(BigDecimal valuacionNominal) {
		this.valuacionNominal = valuacionNominal;
	}

	/**
	 * Obtiene la representación en cadena de la posición disponible
	 * 
	 * @return Cadena #,##0 de la posición disponible
	 */
	public String getPosicionDisponibleConFormato() {
		String res = "";

		if (StringUtils.isNotBlank(posicionDisponible)) {
			try {
				res = FORMATO_ENTERO.format(Double.valueOf(posicionDisponible).longValue());
			} catch (Exception e) {
				logger.error("Ocurrió un error al dar formato a la posición disponible.", e);
			}
		}

		return res;
	}

	/**
	 * Obtiene el valor del atributo posicionDisponibleNumerica
	 * 
	 * @return el valor del atributo posicionDisponibleNumerica
	 */
	public BigDecimal getPosicionDisponibleNumerica() {
		return posicionDisponibleNumerica;
	}

	/**
	 * Establece el valor del atributo posicionDisponibleNumerica
	 * 
	 * @param posicionDisponibleNumerica
	 *            el valor del atributo posicionDisponibleNumerica a establecer
	 */
	public void setPosicionDisponibleNumerica(BigDecimal posicionDisponibleNumerica) {
		this.posicionDisponibleNumerica = posicionDisponibleNumerica;
	}

	/**
	 * Obtiene el valor del atributo posicionNoDisponibleNumerica
	 * 
	 * @return el valor del atributo posicionNoDisponibleNumerica
	 */
	public BigDecimal getPosicionNoDisponibleNumerica() {
		return posicionNoDisponibleNumerica;
	}

	/**
	 * Establece el valor del atributo posicionNoDisponibleNumerica
	 * 
	 * @param posicionNoDisponibleNumerica
	 *            el valor del atributo posicionNoDisponibleNumerica a
	 *            establecer
	 */
	public void setPosicionNoDisponibleNumerica(BigDecimal posicionNoDisponibleNumerica) {
		this.posicionNoDisponibleNumerica = posicionNoDisponibleNumerica;
	}

	/**
	 * Obtiene el valor del atributo posicionNumerica
	 * 
	 * @return el valor del atributo posicionNumerica
	 */
	public BigDecimal getPosicionNumerica() {
		return posicionNumerica;
	}

	/**
	 * Establece el valor del atributo posicionNumerica
	 * 
	 * @param posicionNumerica
	 *            el valor del atributo posicionNumerica a establecer
	 */
	public void setPosicionNumerica(BigDecimal posicionNumerica) {
		this.posicionNumerica = posicionNumerica;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {

		boolean resultado = false;

		if (obj != null) {
			if (obj instanceof PosicionDTO) {
				PosicionDTO posicion = (PosicionDTO) obj;
				resultado = posicion.getEmision() != null && this.getEmision() != null && posicion.getEmision().getId() == this.getEmision().getId();
				resultado = resultado && posicion.getBoveda() != null && this.getBoveda() != null && posicion.getBoveda().getId() == this.getBoveda().getId();
				resultado = resultado && posicion.getCuenta() != null && this.getCuenta() != null
						&& posicion.getCuenta().getIdCuenta() == this.getCuenta().getIdCuenta();
			}
		}

		return resultado;
	}

	/**
	 * @return the posicionFondeo
	 */
	public BigInteger getPosicionFondeo() {
		return posicionFondeo;
	}

	/**
	 * @param posicionFondeo the posicionFondeo to set
	 */
	public void setPosicionFondeo(BigInteger posicionFondeo) {
		this.posicionFondeo = posicionFondeo;
	}
	
	public boolean isPosicionFondeoMayor() {
		if( posicionFondeo.compareTo(posicionDisponibleNumerica.toBigInteger()) > 0 ) {
			return true;
		} else {
			return false;
		}
	}

}
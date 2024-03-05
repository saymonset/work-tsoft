/**
 * Bursatec - Portal Dali
 * Copyrigth (c) 2015 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.aperturaSistema;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import com.indeval.portaldali.middleware.services.aperturaSistema.constantes.AperturaSistemasConstantes;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.util.util.Constantes;

/**
 * 
 * 
 * @author Pablo Balderas
 */
public class AperturaSistemasParametros implements Serializable {

	/** Id para la serializacion */
	private static final long serialVersionUID = 3979100988885012137L;

	private AgenteVO traspasante;

	private AgenteVO receptor;

	private String idTipoOperacion;

	/** Indica el mercado que va a operar */
	private String mercado;
	
	private String tipoMovimiento;

	private EmisionVO emision;

	private BigDecimal cantidad;

	private Date fechaAdquisicion;

	private BigDecimal precioAdquisicion;

	private String cliente;

	private String rfcCURP;

	/** Costo promedio actualizado */
    private BigDecimal costoPromedio;
    
	/**
	 * Constructor
	 */
	public AperturaSistemasParametros() {
		
	}


	/**
	 * Valida el tipo de movimiento
	 * @throws BusinessException En caso de ocurrir un error en la validación.
	 */
	public void validaTipoMovimiento() throws BusinessException {
		try {
			Assert.isTrue(StringUtils.isNotBlank(this.getTipoMovimiento()), "Falta el tipo de movimiento");
			Assert.isTrue(
				AperturaSistemasConstantes.APERTURA.equalsIgnoreCase(this.getTipoMovimiento().trim()),
				"Error: [" + this.getTipoMovimiento() + "] es un valor invalido para el parametro tipo movimiento.");
		}
		catch (IllegalArgumentException e) {
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

		if (this.getPrecioAdquisicion() == null || !(this.getPrecioAdquisicion().compareTo(Constantes.BIG_DECIMAL_ZERO) > 0)) {
			throw new BusinessException("Error: El precio de adquisicion no puedes ser nulo, ni menor o igual a 0");
		}
	}


	/**
	 * Método para obtener el atributo traspasante
	 * @return El atributo traspasante
	 */
	public AgenteVO getTraspasante() {
		return traspasante;
	}


	/**
	 * Método para establecer el atributo traspasante
	 * @param traspasante El valor del atributo traspasante a establecer.
	 */
	public void setTraspasante(AgenteVO traspasante) {
		this.traspasante = traspasante;
	}


	/**
	 * Método para obtener el atributo receptor
	 * @return El atributo receptor
	 */
	public AgenteVO getReceptor() {
		return receptor;
	}


	/**
	 * Método para establecer el atributo receptor
	 * @param receptor El valor del atributo receptor a establecer.
	 */
	public void setReceptor(AgenteVO receptor) {
		this.receptor = receptor;
	}


	/**
	 * Método para obtener el atributo idTipoOperacion
	 * @return El atributo idTipoOperacion
	 */
	public String getIdTipoOperacion() {
		return idTipoOperacion;
	}


	/**
	 * Método para establecer el atributo idTipoOperacion
	 * @param idTipoOperacion El valor del atributo idTipoOperacion a establecer.
	 */
	public void setIdTipoOperacion(String idTipoOperacion) {
		this.idTipoOperacion = idTipoOperacion;
	}


	/**
	 * Método para obtener el atributo mercado
	 * @return El atributo mercado
	 */
	public String getMercado() {
		return mercado;
	}


	/**
	 * Método para establecer el atributo mercado
	 * @param mercado El valor del atributo mercado a establecer.
	 */
	public void setMercado(String mercado) {
		this.mercado = mercado;
	}


	/**
	 * Método para obtener el atributo tipoMovimiento
	 * @return El atributo tipoMovimiento
	 */
	public String getTipoMovimiento() {
		return tipoMovimiento;
	}


	/**
	 * Método para establecer el atributo tipoMovimiento
	 * @param tipoMovimiento El valor del atributo tipoMovimiento a establecer.
	 */
	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}


	/**
	 * Método para obtener el atributo emision
	 * @return El atributo emision
	 */
	public EmisionVO getEmision() {
		return emision;
	}


	/**
	 * Método para establecer el atributo emision
	 * @param emision El valor del atributo emision a establecer.
	 */
	public void setEmision(EmisionVO emision) {
		this.emision = emision;
	}


	/**
	 * Método para obtener el atributo cantidad
	 * @return El atributo cantidad
	 */
	public BigDecimal getCantidad() {
		return cantidad;
	}


	/**
	 * Método para establecer el atributo cantidad
	 * @param cantidad El valor del atributo cantidad a establecer.
	 */
	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}


	/**
	 * Método para obtener el atributo fechaAdquisicion
	 * @return El atributo fechaAdquisicion
	 */
	public Date getFechaAdquisicion() {
		return fechaAdquisicion;
	}


	/**
	 * Método para establecer el atributo fechaAdquisicion
	 * @param fechaAdquisicion El valor del atributo fechaAdquisicion a establecer.
	 */
	public void setFechaAdquisicion(Date fechaAdquisicion) {
		this.fechaAdquisicion = fechaAdquisicion;
	}


	/**
	 * Método para obtener el atributo precioAdquisicion
	 * @return El atributo precioAdquisicion
	 */
	public BigDecimal getPrecioAdquisicion() {
		return precioAdquisicion;
	}


	/**
	 * Método para establecer el atributo precioAdquisicion
	 * @param precioAdquisicion El valor del atributo precioAdquisicion a establecer.
	 */
	public void setPrecioAdquisicion(BigDecimal precioAdquisicion) {
		this.precioAdquisicion = precioAdquisicion;
	}


	/**
	 * Método para obtener el atributo cliente
	 * @return El atributo cliente
	 */
	public String getCliente() {
		return cliente;
	}


	/**
	 * Método para establecer el atributo cliente
	 * @param cliente El valor del atributo cliente a establecer.
	 */
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}


	/**
	 * Método para obtener el atributo rfcCURP
	 * @return El atributo rfcCURP
	 */
	public String getRfcCURP() {
		return rfcCURP;
	}


	/**
	 * Método para establecer el atributo rfcCURP
	 * @param rfcCURP El valor del atributo rfcCURP a establecer.
	 */
	public void setRfcCURP(String rfcCURP) {
		this.rfcCURP = rfcCURP;
	}


	/**
	 * Método para obtener el atributo costoPromedio
	 * @return El atributo costoPromedio
	 */
	public BigDecimal getCostoPromedio() {
		return costoPromedio;
	}


	/**
	 * Método para establecer el atributo costoPromedio
	 * @param costoPromedio El valor del atributo costoPromedio a establecer.
	 */
	public void setCostoPromedio(BigDecimal costoPromedio) {
		this.costoPromedio = costoPromedio;
	}

}

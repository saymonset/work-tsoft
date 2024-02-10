// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.servicios.dto;

import java.io.Serializable;

public class TipoTenenciaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private TipoNaturalezaDTO tipoNaturaleza;
	private TipoCuentaDTO tipoCuenta;
	private String tipoCustodia = null;
	private long idTipoCuenta = 0;
	private String descripcion = null;
	private String claveTipoCuenta = null;

	/**
	 * Obtiene el campo tipoNaturaleza
	 * 
	 * @return tipoNaturaleza
	 */
	public TipoNaturalezaDTO getTipoNaturaleza() {
		return tipoNaturaleza;
	}

	/**
	 * Asigna el valor del campo tipoNaturaleza
	 * 
	 * @param tipoNaturaleza el valor de tipoNaturaleza a asignar
	 */
	public void setTipoNaturaleza(TipoNaturalezaDTO tipoNaturaleza) {
		this.tipoNaturaleza = tipoNaturaleza;
	}

	/**
	 * Obtiene el campo tipoCuenta
	 * 
	 * @return tipoCuenta
	 */
	public TipoCuentaDTO getTipoCuenta() {
		return tipoCuenta;
	}

	/**
	 * Asigna el valor del campo tipoCuenta
	 * 
	 * @param tipoCuenta el valor de tipoCuenta a asignar
	 */
	public void setTipoCuenta(TipoCuentaDTO tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	/**
	 * Obtiene el campo idTipoCuenta
	 * 
	 * @return idTipoCuenta
	 */
	public long getIdTipoCuenta() {
		return idTipoCuenta;
	}

	/**
	 * Asigna el valor del campo idTipoCuenta
	 * 
	 * @param idTipoCuenta el valor de idTipoCuenta a asignar
	 */
	public void setIdTipoCuenta(long idTipoCuenta) {
		this.idTipoCuenta = idTipoCuenta;
	}

	/**
	 * Obtiene el campo descripcion
	 * 
	 * @return descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Asigna el valor del campo descripcion
	 * 
	 * @param descripcion el valor de descripcion a asignar
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Obtiene el campo tipoCustodia
	 * 
	 * @return tipoCustodia
	 */
	public String getTipoCustodia() {
		return tipoCustodia;
	}

	/**
	 * Asigna el valor del campo tipoCustodia
	 * 
	 * @param tipoCustodia el valor de tipoCustodia a asignar
	 */
	public void setTipoCustodia(String tipoCustodia) {
		this.tipoCustodia = tipoCustodia;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		boolean result = false;
		if (o != null && o instanceof TipoTenenciaDTO) {
			result = ((TipoTenenciaDTO) o).getIdTipoCuenta() == this.getIdTipoCuenta();
		} else {
			result = super.equals(o);
		}
		return result;
	}

	public String getClaveTipoCuenta() {
		return claveTipoCuenta;
	}

	public void setClaveTipoCuenta(String claveTipoCuenta) {
		this.claveTipoCuenta = claveTipoCuenta;
	}
}

/**
 * 
 */
package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;
import java.util.List;

import com.indeval.portaldali.persistence.modelo.Divisa;

/**
 * @author César Hernández
 *
 */
public class CuentaEfectivoIntDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	List<String> bicCodes;
	List<String> cuentas;
	List<Long> divisas;
	
	int tipoCuenta;
	
	String cuentaNegocio;

	/**
	 * @return the bicCodes
	 */
	public List<String> getBicCodes() {
		return bicCodes;
	}

	/**
	 * @param bicCodes the bicCodes to set
	 */
	public void setBicCodes(List<String> bicCodes) {
		this.bicCodes = bicCodes;
	}

	/**
	 * @return the cuentas
	 */
	public List<String> getCuentas() {
		return cuentas;
	}

	/**
	 * @param cuentas the cuentas to set
	 */
	public void setCuentas(List<String> cuentas) {
		this.cuentas = cuentas;
	}

	/**
	 * @return the divisas
	 */
	public List<Long> getDivisas() {
		return divisas;
	}

	/**
	 * @param divisas the divisas to set
	 */
	public void setDivisas(List<Long> divisas) {
		this.divisas = divisas;
	}

	/**
	 * @return the tipoCuenta
	 */
	public int getTipoCuenta() {
		return tipoCuenta;
	}

	/**
	 * @param tipoCuenta the tipoCuenta to set
	 */
	public void setTipoCuenta(int tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	/**
	 * @return the cuentaNegocio
	 */
	public String getCuentaNegocio() {
		return cuentaNegocio;
	}

	/**
	 * @param cuentaNegocio the cuentaNegocio to set
	 */
	public void setCuentaNegocio(String cuentaNegocio) {
		this.cuentaNegocio = cuentaNegocio;
	}
}

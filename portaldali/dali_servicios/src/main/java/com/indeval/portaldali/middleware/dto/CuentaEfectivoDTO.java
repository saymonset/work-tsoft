/**
 * 2H Software - Bursatec
 * 
 * Sistema de Consulta de Estados de Cuenta
 *
 * Dec 10, 2007
 *
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;

/**
 * Data Transfer Object que representa una cuenta de efectivo.
 * 
 * @author Pablo Julián Balderas Méndez
 * @version 1.0
 */
public class CuentaEfectivoDTO implements Serializable{

	private static final long serialVersionUID = 1L; 
	
	/** El tipo de la cuenta de efectivo */
	private TipoCuentaDTO tipoCuenta;
	
	/** El identificador de la cuenta */
	private long idCuenta = 0;	
	
	/** El número de la cuenta */
	private String numeroCuenta = null;
	
	/** El nombre relacionado con la cuenta */
	private String nombreCuenta = null;
	/**
	 * número de cuenta de 4 dgitos
	 */
	private String cuenta = null;
	
	/** La institución asociada con la cuenta */
	private InstitucionDTO institucion = new InstitucionDTO();
	
	/** El tipo de custoria de la cuenta */
	private String tipoCustodia=null;
	
	/** La descripción de la cuenta */
	private String descripcion = null;
	
	/** El tipo de naturaleza de la cuenta */
	private TipoNaturalezaDTO tipoNaturaleza;
	
	/**
	 * Constructor de la clase
	 */
	public CuentaEfectivoDTO() {
		super();
	}

	/**
	 * Constructor de la clase
	 * @param idCuenta Id de la cuenta de efectivo
	 */
	public CuentaEfectivoDTO(long idCuenta) {
		super();
		this.idCuenta = idCuenta;
	}

	/**
	 * Obtiene el valor del atributo tipoNaturaleza
	 * 
	 * @return el valor del atributo tipoNaturaleza
	 */
	public TipoNaturalezaDTO getTipoNaturaleza() {
		return tipoNaturaleza;
	}

	/**
	 * Establece el valor del atributo tipoNaturaleza
	 *
	 * @param tipoNaturaleza el valor del atributo tipoNaturaleza a establecer
	 */
	public void setTipoNaturaleza(TipoNaturalezaDTO tipoNaturaleza) {
		this.tipoNaturaleza = tipoNaturaleza;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o){
		boolean result = false;
		if(o != null && o instanceof CuentaEfectivoDTO){
			result = ((CuentaEfectivoDTO)o).getNumeroCuenta().equals(this.getNumeroCuenta());
		}else{
			result = super.equals(o);
		}
		return result;
	}
	
	/**
	 * Obtiene el atributo tipoCuenta
	 *
	 * @return El atrubuto tipoCuenta
	 */
	public TipoCuentaDTO getTipoCuenta() {
		return tipoCuenta;
	}
	/**
	 * Establece la propiedad tipoCuenta
	 *
	 * @param tipoCuenta el campo tipoCuenta a establecer
	 */
	public void setTipoCuenta(TipoCuentaDTO tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}
	/**
	 * Obtiene el atributo idCuenta
	 *
	 * @return El atrubuto idCuenta
	 */
	public long getIdCuenta() {
		return idCuenta;
	}
	/**
	 * Establece la propiedad idCuenta
	 *
	 * @param idCuenta el campo idCuenta a establecer
	 */
	public void setIdCuenta(long idCuenta) {
		this.idCuenta = idCuenta;
	}
	/**
	 * Obtiene el atributo numeroCuenta
	 *
	 * @return El atrubuto numeroCuenta
	 */
	public String getNumeroCuenta() {
		return numeroCuenta;
	}
	/**
	 * Establece la propiedad numeroCuenta
	 *
	 * @param numeroCuenta el campo numeroCuenta a establecer
	 */
	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}
	/**
	 * Obtiene el atributo nombreCuenta
	 *
	 * @return El atrubuto nombreCuenta
	 */
	public String getNombreCuenta() {
		return nombreCuenta;
	}
	/**
	 * Establece la propiedad nombreCuenta
	 *
	 * @param nombreCuenta el campo nombreCuenta a establecer
	 */
	public void setNombreCuenta(String nombreCuenta) {
		this.nombreCuenta = nombreCuenta;
	}
	/**
	 * Obtiene el atributo institucion
	 *
	 * @return El atrubuto institucion
	 */
	public InstitucionDTO getInstitucion() {
		return institucion;
	}
	/**
	 * Establece la propiedad institucion
	 *
	 * @param institucion el campo institucion a establecer
	 */
	public void setInstitucion(InstitucionDTO institucion) {
		this.institucion = institucion;
	}
	/**
	 * @return the tipoCustodia
	 */
	public String getTipoCustodia() {
		return tipoCustodia;
	}
	/**
	 * @param tipoCustodia the tipoCustodia to set
	 */
	public void setTipoCustodia(String tipoCustodia) {
		this.tipoCustodia = tipoCustodia;
	}
	/**
	 * Obtiene el atributo descripcion
	 *
	 * @return El atrubuto descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * Establece la propiedad descripcion
	 *
	 * @param descripcion el campo descripcion a establecer
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the cuenta
	 */
	public String getCuenta() {
		return cuenta;
	}

	/**
	 * @param cuenta the cuenta to set
	 */
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}	
	
}

/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadodinero;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ParcialidadVO extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	private String idTipoOperacion;

	private AgenteVO traspasante;

	private AgenteVO receptor;

	private EmisionVO claveValor;

	private String origen;

	private String folioOrigen;

	private BigInteger cantidadOriginal;

	private BigDecimal importeOriginal;

	private DetalleParcialidadVO[] detalle;

	/**
	 * @return
	 */
	public BigInteger getCantidadOriginal() {
		return cantidadOriginal;
	}

	/**
	 * @param cantidadOriginal
	 */
	public void setCantidadOriginal(BigInteger cantidadOriginal) {
		this.cantidadOriginal = cantidadOriginal;
	}

	/**
	 * @return
	 */
	public EmisionVO getClaveValor() {
		return claveValor;
	}

	/**
	 * @param claveValor
	 */
	public void setClaveValor(EmisionVO claveValor) {
		this.claveValor = claveValor;
	}

	/**
	 * @return
	 */
	public DetalleParcialidadVO[] getDetalle() {
		return detalle;
	}

	/**
	 * @param detalle
	 */
	public void setDetalle(DetalleParcialidadVO[] detalle) {
		this.detalle = detalle;
	}

	/**
	 * @return
	 */
	public String getFolioOrigen() {
		return folioOrigen;
	}

	/**
	 * @param folioOrigen
	 */
	public void setFolioOrigen(String folioOrigen) {
		this.folioOrigen = folioOrigen;
	}

	/**
	 * @return
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
	 * @return
	 */
	public BigDecimal getImporteOriginal() {
		return importeOriginal;
	}

	/**
	 * @param importeOriginal
	 */
	public void setImporteOriginal(BigDecimal importeOriginal) {
		this.importeOriginal = importeOriginal;
	}

	/**
	 * @return
	 */
	public String getOrigen() {
		return origen;
	}

	/**
	 * @param origen
	 */
	public void setOrigen(String origen) {
		this.origen = origen;
	}

	/**
	 * @return
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
	 * @return
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
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object obj, Errors errors) {
	}

}

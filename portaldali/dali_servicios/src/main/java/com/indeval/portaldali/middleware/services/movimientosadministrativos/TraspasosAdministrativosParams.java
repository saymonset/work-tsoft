package com.indeval.portaldali.middleware.services.movimientosadministrativos;

import java.math.BigInteger;
import java.util.Date;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;

public class TraspasosAdministrativosParams extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	private AgenteVO traspasante;

	private AgenteVO receptor;

	private EmisionVO emision;

	private BigInteger cantidadTitulos;

	private boolean isMercadoDinero;
	
	private Date fechaHoraCierreOper;

	/**
	 * @return BigInteger
	 */
	public BigInteger getCantidadTitulos() {
		return cantidadTitulos;
	}

	/**
	 * @param cantidadTitulos
	 */
	public void setCantidadTitulos(BigInteger cantidadTitulos) {
		this.cantidadTitulos = cantidadTitulos;
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
	 * @return boolean
	 */
	public boolean isMercadoDinero() {
		return isMercadoDinero;
	}

	/**
	 * @param isMercadoDinero
	 */
	public void setMercadoDinero(boolean isMercadoDinero) {
		this.isMercadoDinero = isMercadoDinero;
	}

	public Date getFechaHoraCierreOper() {
		return fechaHoraCierreOper;
	}

	public void setFechaHoraCierreOper(Date fechaHoraCierreOper) {
		this.fechaHoraCierreOper = fechaHoraCierreOper;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {

	}

}

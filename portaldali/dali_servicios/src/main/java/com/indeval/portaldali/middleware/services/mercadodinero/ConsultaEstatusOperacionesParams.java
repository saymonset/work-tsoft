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
public class ConsultaEstatusOperacionesParams extends AbstractBaseDTO {

	/** Constante de Serializacion */
	private static final long serialVersionUID = 1L;

	/** */
	private String idTipoPapel;

	/** */
	private String status;

	/** */
	private String[] idTipoOperacion;

	/** */
	private Date fechaConcertacion;

	/** */
	private Date fechaLiquidacion;

	/** */
	private String rol;

	/** */
	private String cuentaPropia;

	/** */
	private AgenteVO agenteFirmado;

	/** */
	private AgenteVO contraparte;

	/** */
	private String error;

	/** */
	private String bajaLogica;

	/** */
	private EmisionVO emision;

	/** */
	private BigInteger cantidad;

	/** */
	private BigDecimal monto;

	/** */
	private BigInteger folioControl;

	/** */
	private String folioDescripcion;

	/** */
	private String idTipoMoneda;

	/** */
	private String origen;

	/**
	 * @return Integer
	 */
	public BigInteger getCantidad() {
		return cantidad;
	}

	/**
	 * @param cantidad
	 */
	public void setCantidad(BigInteger cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * @return AgenteVO
	 */
	public AgenteVO getContraparte() {
		return contraparte;
	}

	/**
	 * @param contraparte
	 */
	public void setContraparte(AgenteVO contraparte) {
		this.contraparte = contraparte;
	}

	/**
	 * @return String
	 */
	public String getCuentaPropia() {
		return cuentaPropia;
	}

	/**
	 * @param cuentaPropia
	 */
	public void setCuentaPropia(String cuentaPropia) {
		this.cuentaPropia = cuentaPropia;
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
	 * @return String
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param error
	 */
	public void setError(String error) {
		this.error = error;
	}

	/**
	 * @return Date
	 */
	public Date getFechaConcertacion() {
		return fechaConcertacion;
	}

	/**
	 * @param fecConcertacion
	 */
	public void setFechaConcertacion(Date fecConcertacion) {
		this.fechaConcertacion = clona(fecConcertacion);
	}

	/**
	 * @return Date
	 */
	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	/**
	 * @param fecLiquidacion
	 */
	public void setFechaLiquidacion(Date fecLiquidacion) {
		this.fechaLiquidacion = clona(fecLiquidacion);
	}

	/**
	 * @return BigInteger
	 */
	public BigInteger getFolioControl() {
		return folioControl;
	}

	/**
	 * @param folioControl
	 */
	public void setFolioControl(BigInteger folioControl) {
		this.folioControl = folioControl;
	}

	/**
	 * @return String
	 */
	public String getFolioDescripcion() {
		return folioDescripcion;
	}

	/**
	 * @param folioDescripcion
	 */
	public void setFolioDescripcion(String folioDescripcion) {
		this.folioDescripcion = folioDescripcion;
	}

	/**
	 * @return String
	 */
	public String getRol() {
		return rol;
	}

	/**
	 * @param idRol
	 */
	public void setRol(String idRol) {
		this.rol = idRol;
	}

	/**
	 * @return String
	 */
	public String getIdTipoMoneda() {
		return idTipoMoneda;
	}

	/**
	 * @param idTipoMoneda
	 */
	public void setIdTipoMoneda(String idTipoMoneda) {
		this.idTipoMoneda = idTipoMoneda;
	}

	/**
	 * @return String[]
	 */
	public String[] getIdTipoOperacion() {
		return idTipoOperacion;
	}

	/**
	 * @param idTipoOperacion
	 */
	public void setIdTipoOperacion(String[] idTipoOperacion) {
		this.idTipoOperacion = idTipoOperacion;
	}

	/**
	 * @return String
	 */
	public String getIdTipoPapel() {
		return idTipoPapel;
	}

	/**
	 * @param idTipoPapel
	 */
	public void setIdTipoPapel(String idTipoPapel) {
		this.idTipoPapel = idTipoPapel;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getMonto() {
		return monto;
	}

	/**
	 * @param monto
	 */
	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	/**
	 * @return String
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return AgenteVO
	 */
	public AgenteVO getAgenteFirmado() {
		return agenteFirmado;
	}

	/**
	 * @param agenteFirmado
	 */
	public void setAgenteFirmado(AgenteVO agenteFirmado) {
		this.agenteFirmado = agenteFirmado;
	}

	/**
	 * @return String
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
	 * @return String
	 */
	public String getBajaLogica() {
		return bajaLogica;
	}

	/**
	 * @param bajaLogica
	 */
	public void setBajaLogica(String bajaLogica) {
		this.bajaLogica = bajaLogica;
	}

	/**
	 * Valida el Tipo de Papel
	 * 
	 * @throws BusinessException
	 */
	public void validaTipoPapel() throws BusinessException {
		if (StringUtils.isBlank(this.idTipoPapel)) {
			throw new BusinessException("El objeto de parametros tiene el atributo requerido " + "idTipoPapel NULL o VACIO");
		}
		if (!this.idTipoPapel.equalsIgnoreCase(MercadoDineroService.PAPEL_BANCARIO)
				&& !this.idTipoPapel.equalsIgnoreCase(MercadoDineroService.PAPEL_GUBERNAMENTAL)
				&& !this.idTipoPapel.equalsIgnoreCase(MercadoDineroService.AMBOS_INIC)) {
			throw new BusinessException("[" + this.idTipoPapel + "] es un valor invalido para " + "el atributo requerido idTipoPapel");
		}
	}

	/**
	 * Valida que el objeto tenga todos los parametros necesarios
	 * 
	 * @throws BusinessException
	 */
	public void esValido() throws BusinessException {
		if (this.getAgenteFirmado() == null) {
			throw new BusinessException("El objeto de parametros tiene el atributo requerido " + "agenteFirmado NULL");
		}
		this.getAgenteFirmado().tieneClaveValida();
		if (StringUtils.isNotBlank(this.getCuentaPropia())) { // Ya se valido
			// el
			// agenteFirmado
			this.getAgenteFirmado().setCuenta(this.getCuentaPropia());
		}
		this.validaTipoPapel();
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object obj, Errors errors) {
	}

}

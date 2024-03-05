/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.util.vo;

import java.math.BigInteger;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class BitacoraMatchParams extends AbstractBaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** TRASPASANTE = T */
	static final String TRASPASANTE = "T";

	/** RECEPTOR = R */
	static final String RECEPTOR = "R";

	/** Define clave AMBOS (AMBOS) */
	static final String AMBOS = "A";

	private BigInteger idBitacora;

	private String codigoOperacion;

	private AgenteVO agenteFirmado;

	private AgenteVO contraparte;

	private String rol;

	private String folioInstruccionTraspasante; // XXX FK --> folioInstruccion

	private String folioInstruccionReceptor;

	private Date fecha;

	private String instancia;

	private BigInteger idBitacoraMatch;

	private String folioMatch;

	private String tipoOperacion;

	private String tipoMensaje;

	private Date fechaHoraRecepcion;

	private Date fechaLiquidacion;

	private BigInteger matchKey;

	private String expira;

	private EmisionVO emisionVO;

	private String mercado;

	private BigInteger cantidadTitulos;

	private String folioUsuario;

	private Boolean remitidos;

	/**
	 * 
	 * @return Boolean
	 */
	public Boolean getRemitidos() {
		return remitidos;
	}

	/**
	 * 
	 * @param remitidos
	 */
	public void setRemitidos(Boolean remitidos) {
		this.remitidos = remitidos;
	}

	/**
	 * 
	 * @return String
	 */
	public String getFolioUsuario() {
		return folioUsuario;
	}

	/**
	 * 
	 * @param folioUsuario
	 */
	public void setFolioUsuario(String folioUsuario) {
		this.folioUsuario = folioUsuario;
	}

	/**
	 * 
	 * @return BigInteger
	 */
	public BigInteger getCantidadTitulos() {
		return cantidadTitulos;
	}

	/**
	 * 
	 * @param cantidadTitulos
	 */
	public void setCantidadTitulos(BigInteger cantidadTitulos) {
		this.cantidadTitulos = cantidadTitulos;
	}

	/**
	 * @return String
	 */
	public String getMercado() {
		return mercado;
	}

	/**
	 * @param mercado
	 */
	public void setMercado(String mercado) {
		this.mercado = mercado;
	}

	/**
	 * @return the codigoOperacion
	 */
	public String getCodigoOperacion() {
		return codigoOperacion;
	}

	/**
	 * @param codigoOperacion
	 *            the codigoOperacion to set
	 */
	public void setCodigoOperacion(String codigoOperacion) {
		this.codigoOperacion = codigoOperacion;
	}

	/**
	 * @return the expira
	 */
	public String getExpira() {
		return expira;
	}

	/**
	 * @param expira
	 *            the expira to set
	 */
	public void setExpira(String expira) {
		this.expira = expira;
	}

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha
	 *            the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = clona(fecha);
	}

	/**
	 * @return the fechaHoraRecepcion
	 */
	public Date getFechaHoraRecepcion() {
		return fechaHoraRecepcion;
	}

	/**
	 * @param fechaHoraRecepcion
	 *            the fechaHoraRecepcion to set
	 */
	public void setFechaHoraRecepcion(Date fechaHoraRecepcion) {
		this.fechaHoraRecepcion = clona(fechaHoraRecepcion);
	}

	/**
	 * @return the fechaLiquidacion
	 */
	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	/**
	 * @param fechaLiquidacion
	 *            the fechaLiquidacion to set
	 */
	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = clona(fechaLiquidacion);
	}

	/**
	 * @return the folioInstruccionReceptor
	 */
	public String getFolioInstruccionReceptor() {
		return folioInstruccionReceptor;
	}

	/**
	 * @param folioInstruccionReceptor
	 *            the folioInstruccionReceptor to set
	 */
	public void setFolioInstruccionReceptor(String folioInstruccionReceptor) {
		this.folioInstruccionReceptor = folioInstruccionReceptor;
	}

	/**
	 * @return the folioInstruccionTraspasante
	 */
	public String getFolioInstruccionTraspasante() {
		return folioInstruccionTraspasante;
	}

	/**
	 * @param folioInstruccionTraspasante
	 *            the folioInstruccionTraspasante to set
	 */
	public void setFolioInstruccionTraspasante(String folioInstruccionTraspasante) {
		this.folioInstruccionTraspasante = folioInstruccionTraspasante;
	}

	/**
	 * @return the folioMatch
	 */
	public String getFolioMatch() {
		return folioMatch;
	}

	/**
	 * @param folioMatch
	 *            the folioMatch to set
	 */
	public void setFolioMatch(String folioMatch) {
		this.folioMatch = folioMatch;
	}

	/**
	 * @return the idBitacora
	 */
	public BigInteger getIdBitacora() {
		return idBitacora;
	}

	/**
	 * @param idBitacora
	 *            the idBitacora to set
	 */
	public void setIdBitacora(BigInteger idBitacora) {
		this.idBitacora = clonaBigInteger(idBitacora);
	}

	/**
	 * @return the idBitacoraMatch
	 */
	public BigInteger getIdBitacoraMatch() {
		return idBitacoraMatch;
	}

	/**
	 * @param idBitacoraMatch
	 *            the idBitacoraMatch to set
	 */
	public void setIdBitacoraMatch(BigInteger idBitacoraMatch) {
		this.idBitacoraMatch = clonaBigInteger(idBitacoraMatch);
	}

	/**
	 * @return the instancia
	 */
	public String getInstancia() {
		return instancia;
	}

	/**
	 * @param instancia
	 *            the instancia to set
	 */
	public void setInstancia(String instancia) {
		this.instancia = instancia;
	}

	/**
	 * @return the matchKey
	 */
	public BigInteger getMatchKey() {
		return matchKey;
	}

	/**
	 * @param matchKey
	 *            the matchKey to set
	 */
	public void setMatchKey(BigInteger matchKey) {
		this.matchKey = clonaBigInteger(matchKey);
	}

	/**
	 * @return the tipoMensaje
	 */
	public String getTipoMensaje() {
		return tipoMensaje;
	}

	/**
	 * @param tipoMensaje
	 *            the tipoMensaje to set
	 */
	public void setTipoMensaje(String tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
	}

	/**
	 * @return the tipoOperacion
	 */
	public String getTipoOperacion() {
		return tipoOperacion;
	}

	/**
	 * @param tipoOperacion
	 *            the tipoOperacion to set
	 */
	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	/**
	 * Garantiza que el rol no se encuentre nulo o vacio
	 */
	public void validaRol() {

		// Si el rol viene nulo o vacio, se settea como AMBOS
		if (StringUtils.isBlank(this.getRol()) || (!this.getRol().equalsIgnoreCase(RECEPTOR) && !this.getRol().equalsIgnoreCase(TRASPASANTE))) {
			this.setRol(AMBOS);
		}

	}

	/**
	 * @return the agenteFirmado
	 */
	public AgenteVO getAgenteFirmado() {
		return agenteFirmado;
	}

	/**
	 * @param agenteFirmado
	 *            the agenteFirmado to set
	 */
	public void setAgenteFirmado(AgenteVO agenteFirmado) {
		this.agenteFirmado = agenteFirmado;
	}

	/**
	 * @return the contraparte
	 */
	public AgenteVO getContraparte() {
		return contraparte;
	}

	/**
	 * @param contraparte
	 *            the contraparte to set
	 */
	public void setContraparte(AgenteVO contraparte) {
		this.contraparte = contraparte;
	}

	/**
	 * @return the rol
	 */
	public String getRol() {
		return rol;
	}

	/**
	 * @param rol
	 *            the rol to set
	 */
	public void setRol(String rol) {
		this.rol = rol;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {

	}

	/**
	 * @return EmisionVO
	 */
	public EmisionVO getEmisionVO() {
		return emisionVO;
	}

	/**
	 * 
	 * @param emisionVO
	 */
	public void setEmisionVO(EmisionVO emisionVO) {
		this.emisionVO = emisionVO;
	}

}

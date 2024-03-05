/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.util.vo;

import java.math.BigInteger;
import java.util.Date;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import org.springframework.validation.Errors;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class BitacoraMatchVO extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	private BigInteger idBitacora;

	private String codigoOperacion;

	private String idTraspasante;

	private String idReceptor;

	private String folioTraspasante;

	private String folioReceptor;

	private String folioInstruccionTraspasante; // XXX FK --> folioInstruccion

	private String folioInstruccionReceptor;

	private Date fecha;

	private String instancia;

	private BigInteger idBitacoraMatch;

	private String folioMatch;

	private String estadoInstruccion;

	private String tipoOperacion;

	private String tipoMensaje;

	private Date fechaHoraRecepcion;

	private Date fechaLiquidacion;

	private BigInteger matchKey;

	private String descripcionError;

	private Object mensaje;

	private String expira;

	/** Indica true, si el agente puede cancelar la operacion */
	private Boolean puedeCancelar;

	/** Indica true, si el agente puede confirmar la operacion */
	private Boolean puedeConfirmar;

	/** Indica true, si el agente puede expirar la operacion */
	private Boolean puedeExpirar;

	/** Indica true, si la operacion esta confirmada */
	private Boolean estaConfirmada;

	/** Indica true, si la operacion esta cancelada */
	private Boolean estaCancelada;

	/** Indica el numero de veces que la operacion ha sido enviada a espejear */
	private Integer enviada;

	/**
	 * Constructor por defecto
	 */
	public BitacoraMatchVO() {
		this.setPuedeCancelar(Boolean.FALSE);
		this.setPuedeConfirmar(Boolean.FALSE);
		this.setPuedeExpirar(Boolean.FALSE);
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
	 * @return the estadoInstruccion
	 */
	public String getEstadoInstruccion() {
		return estadoInstruccion;
	}

	/**
	 * @param estadoInstruccion
	 *            the estadoInstruccion to set
	 */
	public void setEstadoInstruccion(String estadoInstruccion) {
		this.estadoInstruccion = estadoInstruccion;
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
		this.fecha = fecha;
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
		this.fechaHoraRecepcion = fechaHoraRecepcion;
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
		this.fechaLiquidacion = fechaLiquidacion;
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
	 * @return the folioReceptor
	 */
	public String getFolioReceptor() {
		return folioReceptor;
	}

	/**
	 * @param folioReceptor
	 *            the folioReceptor to set
	 */
	public void setFolioReceptor(String folioReceptor) {
		this.folioReceptor = folioReceptor;
	}

	/**
	 * @return the folioTraspasante
	 */
	public String getFolioTraspasante() {
		return folioTraspasante;
	}

	/**
	 * @param folioTraspasante
	 *            the folioTraspasante to set
	 */
	public void setFolioTraspasante(String folioTraspasante) {
		this.folioTraspasante = folioTraspasante;
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
		this.idBitacora = idBitacora;
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
		this.idBitacoraMatch = idBitacoraMatch;
	}

	/**
	 * @return the idReceptor
	 */
	public String getIdReceptor() {
		return idReceptor;
	}

	/**
	 * @param idReceptor
	 *            the idReceptor to set
	 */
	public void setIdReceptor(String idReceptor) {
		this.idReceptor = idReceptor;
	}

	/**
	 * @return the idTraspasante
	 */
	public String getIdTraspasante() {
		return idTraspasante;
	}

	/**
	 * @param idTraspasante
	 *            the idTraspasante to set
	 */
	public void setIdTraspasante(String idTraspasante) {
		this.idTraspasante = idTraspasante;
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
		this.matchKey = matchKey;
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
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {

	}

	/**
	 * @return the mensaje
	 */
	public Object getMensaje() {
		return mensaje;
	}

	/**
	 * @param mensaje
	 *            the mensaje to set
	 */
	public void setMensaje(Object mensaje) {
		this.mensaje = mensaje;
	}

	/**
	 * @return the descripcionError
	 */
	public String getDescripcionError() {
		return descripcionError;
	}

	/**
	 * @param descripcionError
	 *            the descripcionError to set
	 */
	public void setDescripcionError(String descripcionError) {
		this.descripcionError = descripcionError;
	}

	/**
	 * @return the puedeCancelar
	 */
	public Boolean getPuedeCancelar() {
		return puedeCancelar;
	}

	/**
	 * @param puedeCancelar
	 *            the puedeCancelar to set
	 */
	public void setPuedeCancelar(Boolean puedeCancelar) {
		this.puedeCancelar = puedeCancelar;
	}

	/**
	 * Indica que el objeto puede ser confirmado
	 * 
	 * @return the puedeConfirmar
	 */
	public Boolean getPuedeConfirmar() {
		return puedeConfirmar;
	}

	/**
	 * @param puedeConfirmar
	 *            the puedeConfirmar to set
	 */
	public void setPuedeConfirmar(Boolean puedeConfirmar) {
		this.puedeConfirmar = puedeConfirmar;
	}

	/**
	 * @return the puedeExpirar
	 */
	public Boolean getPuedeExpirar() {
		return puedeExpirar;
	}

	/**
	 * @param puedeExpirar
	 *            the puedeExpirar to set
	 */
	public void setPuedeExpirar(Boolean puedeExpirar) {
		this.puedeExpirar = puedeExpirar;
	}

	/**
	 * 
	 * @return Boolean
	 */
	public Boolean getEstaConfirmada() {
		return estaConfirmada;
	}

	/**
	 * 
	 * @param estaConfirmada
	 */
	public void setEstaConfirmada(Boolean estaConfirmada) {
		this.estaConfirmada = estaConfirmada;
	}

	/**
	 * @return the estaCancelada
	 */
	public Boolean getEstaCancelada() {
		return estaCancelada;
	}

	/**
	 * @param estaCancelada
	 *            the estaCancelada to set
	 */
	public void setEstaCancelada(Boolean estaCancelada) {
		this.estaCancelada = estaCancelada;
	}

	/**
	 * @return the enviada
	 */
	public Integer getEnviada() {
		return enviada;
	}

	/**
	 * @param enviada
	 *            the enviada to set
	 */
	public void setEnviada(Integer enviada) {
		this.enviada = enviada;
	}

}

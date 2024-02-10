package com.indeval.portalinternacional.middleware.servicios.modelo.filetransfer.beneficiario.tipoformato;

import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;


public abstract class FileTransferForma implements Comparable<FileTransferForma>{
	
	/** Indica el estado del registro */
	private String estadoRegistro;
	
	/** Indica el número del registro */
	private Integer numeroRegistro;
	
	/** Descripción del error si fallan las validaciones */
	private String descripcionError;
	
	/** Indica el tipo de formato a cargar */
	@XStreamAsAttribute
	private String tipoFormato;
	
	/** Indica el tipo de beneficio a cargar */
	@XStreamAsAttribute
	private String tipoBeneficiario;
	
	/** Indica el id del tipo de beneficio */
	@XStreamOmitField
	private Long idTipoBeneficiario;
	
	/** Indica el cutodio asociado al beneficio */
	@XStreamAsAttribute
	private String claveCustodio;
	
	/** Indica el id de la cuenta nombrada asociada al custodia */
	@XStreamOmitField
	private Long idCuentaNombrada;
	
	/** Indica la clave de la institución asociada al registro */
	@XStreamAsAttribute
	private String claveInstitucion;
	
	/** Id de la institución asociada al registro */
	@XStreamOmitField
	private Long idInstitucion;
	
	/** Indica la fecha del formato */
	@XStreamAsAttribute
	private String fechaFormato;
	
	/** Fecha del formato como objeto java */
	@XStreamOmitField
	private Date dateFechaFormato;
	
	/** Indica el estado del registro */
	@XStreamAsAttribute
	private String estado;

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17,31).
				append(numeroRegistro!=null?numeroRegistro:0).hashCode();
	}
	
	/**
	 * Método para la comparación del objeto
	 */
	public int compareTo(FileTransferForma obj) {
		FileTransferForma fileTransferForma = obj;		
		return this.getNumeroRegistro().compareTo(fileTransferForma.getNumeroRegistro());
	}

	/**
	 * Método para obtener el atributo estadoRegistro
	 * @return El atributo estadoRegistro
	 */
	public String getEstadoRegistro() {
		return estadoRegistro;
	}

	/**
	 * Método para establecer el atributo estadoRegistro
	 * @param estadoRegistro El valor del atributo estadoRegistro a establecer.
	 */
	public void setEstadoRegistro(String estadoRegistro) {
		this.estadoRegistro = estadoRegistro;
	}

	/**
	 * Método para obtener el atributo numeroRegistro
	 * @return El atributo numeroRegistro
	 */
	public Integer getNumeroRegistro() {
		return numeroRegistro;
	}

	/**
	 * Método para establecer el atributo numeroRegistro
	 * @param numeroRegistro El valor del atributo numeroRegistro a establecer.
	 */
	public void setNumeroRegistro(Integer numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

	/**
	 * Método para obtener el atributo descripcionError
	 * @return El atributo descripcionError
	 */
	public String getDescripcionError() {
		return descripcionError;
	}

	/**
	 * Método para establecer el atributo descripcionError
	 * @param descripcionError El valor del atributo descripcionError a establecer.
	 */
	public void setDescripcionError(String descripcionError) {
		this.descripcionError = descripcionError;
	}

	/**
	 * Método para obtener el atributo tipoFormato
	 * @return El atributo tipoFormato
	 */
	public String getTipoFormato() {
		return tipoFormato;
	}

	/**
	 * Método para establecer el atributo tipoFormato
	 * @param tipoFormato El valor del atributo tipoFormato a establecer.
	 */
	public void setTipoFormato(String tipoFormato) {
		this.tipoFormato = tipoFormato;
	}

	/**
	 * Método para obtener el atributo tipoBeneficiario
	 * @return El atributo tipoBeneficiario
	 */
	public String getTipoBeneficiario() {
		return tipoBeneficiario;
	}

	/**
	 * Método para establecer el atributo tipoBeneficiario
	 * @param tipoBeneficiario El valor del atributo tipoBeneficiario a establecer.
	 */
	public void setTipoBeneficiario(String tipoBeneficiario) {
		this.tipoBeneficiario = tipoBeneficiario;
	}

	/**
	 * Método para obtener el atributo claveCustodio
	 * @return El atributo claveCustodio
	 */
	public String getClaveCustodio() {
		return claveCustodio;
	}

	/**
	 * Método para establecer el atributo claveCustodio
	 * @param claveCustodio El valor del atributo claveCustodio a establecer.
	 */
	public void setClaveCustodio(String claveCustodio) {
		this.claveCustodio = claveCustodio;
	}

	/**
	 * Método para obtener el atributo idCuentaNombrada
	 * @return El atributo idCuentaNombrada
	 */
	public Long getIdCuentaNombrada() {
		return idCuentaNombrada;
	}

	/**
	 * Método para establecer el atributo idCuentaNombrada
	 * @param idCuentaNombrada El valor del atributo idCuentaNombrada a establecer.
	 */
	public void setIdCuentaNombrada(Long idCuentaNombrada) {
		this.idCuentaNombrada = idCuentaNombrada;
	}

	/**
	 * Método para obtener el atributo claveInstitucion
	 * @return El atributo claveInstitucion
	 */
	public String getClaveInstitucion() {
		return claveInstitucion;
	}

	/**
	 * Método para establecer el atributo claveInstitucion
	 * @param claveInstitucion El valor del atributo claveInstitucion a establecer.
	 */
	public void setClaveInstitucion(String claveInstitucion) {
		this.claveInstitucion = claveInstitucion;
	}

	/**
	 * Método para obtener el atributo idInstitucion
	 * @return El atributo idInstitucion
	 */
	public Long getIdInstitucion() {
		return idInstitucion;
	}

	/**
	 * Método para establecer el atributo idInstitucion
	 * @param idInstitucion El valor del atributo idInstitucion a establecer.
	 */
	public void setIdInstitucion(Long idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	/**
	 * Método para obtener el atributo fechaFormato
	 * @return El atributo fechaFormato
	 */
	public String getFechaFormato() {
		return fechaFormato;
	}

	/**
	 * Método para establecer el atributo fechaFormato
	 * @param fechaFormato El valor del atributo fechaFormato a establecer.
	 */
	public void setFechaFormato(String fechaFormato) {
		this.fechaFormato = fechaFormato;
	}

	/**
	 * Método para obtener el atributo dateFechaFormato
	 * @return El atributo dateFechaFormato
	 */
	public Date getDateFechaFormato() {
		return dateFechaFormato;
	}

	/**
	 * Método para establecer el atributo dateFechaFormato
	 * @param dateFechaFormato El valor del atributo dateFechaFormato a establecer.
	 */
	public void setDateFechaFormato(Date dateFechaFormato) {
		this.dateFechaFormato = dateFechaFormato;
	}

	/**
	 * Método para obtener el atributo estado
	 * @return El atributo estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * Método para establecer el atributo estado
	 * @param estado El valor del atributo estado a establecer.
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * Método para obtener el atributo idTipoBeneficiario
	 * @return El atributo idTipoBeneficiario
	 */
	public Long getIdTipoBeneficiario() {
		return idTipoBeneficiario;
	}

	/**
	 * Método para establecer el atributo idTipoBeneficiario
	 * @param idTipoBeneficiario El valor del atributo idTipoBeneficiario a establecer.
	 */
	public void setIdTipoBeneficiario(Long idTipoBeneficiario) {
		this.idTipoBeneficiario = idTipoBeneficiario;
	}

}

package com.indeval.portaldali.middleware.dto.criterio;

import java.io.Serializable;
import java.util.Date;

public class CriterioVencimientoReportosDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private long idInstitucionRestringeResultados;

	private String institucionParticipante;
	/**
	 * El identificador de la institucion participante
	 */
	private long idInstitucionParticipante = 0;
	/**
	 * La clave del tipo de institucion participante
	 */
	private String claveTipoInstitucionParticipante;
	/**
	 * El folio de la institucion participante
	 */
	private String folioInstitucionParticipante;

	private String institucionContraparte;
	/**
	 * El identificador de la institucion contraparte
	 */
	private long idInstitucionContraparte = 0;
	/**
	 * La clave del tipo de institucion contraparte
	 */
	private String claveTipoInstitucionContraparte;
	/**
	 * Numero de cuenta de 4 posiciones
	 */
	private String cuentaParticipante;
	/**
	 * Numero de cuenta de 4 posiciones
	 */
	private String cuentaContraparte;

	/**
	 * fecha de reporto inicial
	 */
	private Date fechaReportoIni;

	/**
	 * fecha de reporto final
	 */
	private Date fechaReportoFin;

	private String emisora;

	private String tipoValor;
	/**
	 * El identificador de la emisora
	 */
	private long idTipoValor = 0;

	/**
	 * La descripcion de la emisora
	 */
	private String claveTipoValor = null;

	/**
	 * El identificador de la emisora
	 */
	private long idEmisora = 0;

	/**
	 * La descripcion de la emisora
	 */
	private String descripcionEmisora;

	/**
	 * Serie seleccionada
	 */
	private String serie;

	public CriterioVencimientoReportosDTO() {
		super();
	}

	@Override
	public String toString() {
		return "CriterioVencimientoReportosDTO [institucionParticipante=" + institucionParticipante
				+ ", idInstitucionParticipante=" + idInstitucionParticipante + ", claveTipoInstitucionParticipante="
				+ claveTipoInstitucionParticipante + ", folioInstitucionParticipante=" + folioInstitucionParticipante
				+ ", institucionContraparte=" + institucionContraparte + ", idInstitucionContraparte="
				+ idInstitucionContraparte + ", claveTipoInstitucionContraparte=" + claveTipoInstitucionContraparte
				+ ", cuentaParticipante=" + cuentaParticipante + ", cuentaContraparte=" + cuentaContraparte
				+ ", fechaReportoIni=" + fechaReportoIni + ", fechaReportoFin=" + fechaReportoFin + ", emisora="
				+ emisora + ", tipoValor=" + tipoValor + ", idTipoValor=" + idTipoValor + ", claveTipoValor="
				+ claveTipoValor + ", idEmisora=" + idEmisora + ", descripcionEmisora=" + descripcionEmisora
				+ ", serie=" + serie + "]";
	}

	public long getIdInstitucionParticipante() {
		return idInstitucionParticipante;
	}

	public void setIdInstitucionParticipante(long idInstitucionParticipante) {
		this.idInstitucionParticipante = idInstitucionParticipante;
	}

	public String getClaveTipoInstitucionParticipante() {
		return claveTipoInstitucionParticipante;
	}

	public void setClaveTipoInstitucionParticipante(String claveTipoInstitucionParticipante) {
		this.claveTipoInstitucionParticipante = claveTipoInstitucionParticipante;
	}

	public String getFolioInstitucionParticipante() {
		return folioInstitucionParticipante;
	}

	public void setFolioInstitucionParticipante(String folioInstitucionParticipante) {
		this.folioInstitucionParticipante = folioInstitucionParticipante;
	}

	public long getIdInstitucionContraparte() {
		return idInstitucionContraparte;
	}

	public void setIdInstitucionContraparte(long idInstitucionContraparte) {
		this.idInstitucionContraparte = idInstitucionContraparte;
	}

	public String getClaveTipoInstitucionContraparte() {
		return claveTipoInstitucionContraparte;
	}

	public void setClaveTipoInstitucionContraparte(String claveTipoInstitucionContraparte) {
		this.claveTipoInstitucionContraparte = claveTipoInstitucionContraparte;
	}

	public String getCuentaParticipante() {
		return cuentaParticipante;
	}

	public void setCuentaParticipante(String cuentaParticipante) {
		this.cuentaParticipante = cuentaParticipante;
	}

	public String getCuentaContraparte() {
		return cuentaContraparte;
	}

	public void setCuentaContraparte(String cuentaContraparte) {
		this.cuentaContraparte = cuentaContraparte;
	}

	public long getIdTipoValor() {
		return idTipoValor;
	}

	public void setIdTipoValor(long idTipoValor) {
		this.idTipoValor = idTipoValor;
	}

	public String getClaveTipoValor() {
		return claveTipoValor;
	}

	public void setClaveTipoValor(String claveTipoValor) {
		this.claveTipoValor = claveTipoValor;
	}

	public long getIdEmisora() {
		return idEmisora;
	}

	public void setIdEmisora(long idEmisora) {
		this.idEmisora = idEmisora;
	}

	public String getDescripcionEmisora() {
		return descripcionEmisora;
	}

	public void setDescripcionEmisora(String descripcionEmisora) {
		this.descripcionEmisora = descripcionEmisora;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getEmisora() {
		return emisora;
	}

	public void setEmisora(String emisora) {
		this.emisora = emisora;
	}

	public String getTipoValor() {
		return tipoValor;
	}

	public void setTipoValor(String tipoValor) {
		this.tipoValor = tipoValor;
	}

	public String getInstitucionParticipante() {
		return institucionParticipante;
	}

	public void setInstitucionParticipante(String institucionParticipante) {
		this.institucionParticipante = institucionParticipante;
	}

	public String getInstitucionContraparte() {
		return institucionContraparte;
	}

	public void setInstitucionContraparte(String institucionContraparte) {
		this.institucionContraparte = institucionContraparte;
	}

	public Date getFechaReportoIni() {
		return fechaReportoIni;
	}

	public void setFechaReportoIni(Date fechaReportoIni) {
		this.fechaReportoIni = fechaReportoIni;
	}

	public Date getFechaReportoFin() {
		return fechaReportoFin;
	}

	public void setFechaReportoFin(Date fechaReportoFin) {
		this.fechaReportoFin = fechaReportoFin;
	}

	public long getIdInstitucionRestringeResultados() {
		return idInstitucionRestringeResultados;
	}

	public void setIdInstitucionRestringeResultados(long idInstitucionRestringeResultados) {
		this.idInstitucionRestringeResultados = idInstitucionRestringeResultados;
	}

}

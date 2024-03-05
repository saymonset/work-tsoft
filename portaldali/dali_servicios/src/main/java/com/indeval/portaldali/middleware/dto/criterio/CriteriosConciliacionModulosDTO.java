package com.indeval.portaldali.middleware.dto.criterio;

import org.apache.commons.lang.StringUtils;

import com.indeval.portaldali.middleware.dto.InstitucionDTO;

public class CriteriosConciliacionModulosDTO {

	private String idFolioParticipante;
	private String idFolioContraparte;
	private String cuentaParticipante;
	private String cuentaContraparte;
	private String tipoValor;
	private String emisora;
	private String serie;
	private Long tipoInstruccion;
	private String descTipoInstruccion = "TODOS";
	private Long tipoOperacion;
	private String descTipoOperacion = "TODOS";
	private int idTab;
	private InstitucionDTO institucionActual;

	public String getIdFolioParticipante() {
		return idFolioParticipante;
	}

	public void setIdFolioParticipante(String idFolioParticipante) {
		this.idFolioParticipante = idFolioParticipante;
	}

	public String getIdFolioContraparte() {
		return idFolioContraparte;
	}

	public void setIdFolioContraparte(String idFolioContraparte) {
		this.idFolioContraparte = idFolioContraparte;
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

	public String getTipoValor() {
		return tipoValor;
	}

	public void setTipoValor(String tipoValor) {
		this.tipoValor = StringUtils.upperCase(tipoValor);
	}

	public String getEmisora() {
		return emisora;
	}

	public void setEmisora(String emisora) {
		this.emisora = StringUtils.upperCase(emisora);
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = StringUtils.upperCase(serie);
	}

	public Long getTipoInstruccion() {
		return tipoInstruccion;
	}

	public void setTipoInstruccion(Long tipoInstruccion) {
		this.tipoInstruccion = tipoInstruccion;
	}

	public Long getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(Long tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public String getDescTipoInstruccion() {
		return descTipoInstruccion;
	}

	public void setDescTipoInstruccion(String descTipoInstruccion) {
		this.descTipoInstruccion = descTipoInstruccion;
	}

	public String getDescTipoOperacion() {
		return descTipoOperacion;
	}

	public void setDescTipoOperacion(String descTipoOperacion) {
		this.descTipoOperacion = descTipoOperacion;
	}

	public int getIdTab() {
		return idTab;
	}

	public void setIdTab(int idTab) {
		this.idTab = idTab;
	}

	public InstitucionDTO getInstitucionActual() {
		return institucionActual;
	}

	public void setInstitucionActual(InstitucionDTO institucionActual) {
		this.institucionActual = institucionActual;
	}

}

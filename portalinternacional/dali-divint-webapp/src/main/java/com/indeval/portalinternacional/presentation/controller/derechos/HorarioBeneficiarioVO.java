package com.indeval.portalinternacional.presentation.controller.derechos;

import org.apache.commons.lang.StringUtils;

import com.indeval.portalinternacional.middleware.servicios.modelo.HorarioBeneficiario;

public class HorarioBeneficiarioVO {

	private Long idHorario;
	private Long idCuentaNombrada;
	private Float porcentajeRet;
	private Integer dias;
	private Integer hora;
	private Integer minuto;
	private String tv;
	private String emisora;
	private String serie;
	private Integer idInstitucion;
	private String folioInstitucion;
	private Boolean esDespuesFechaCorte;
	private Boolean eliminado;
	
	public Long getIdHorario() {
		return idHorario;
	}
	public Long getIdCuentaNombrada() {
		return idCuentaNombrada;
	}
	public Float getPorcentajeRet() {
		return porcentajeRet;
	}
	public Integer getDias() {
		return dias;
	}
	public Integer getHora() {
		return hora;
	}
	public Integer getMinuto() {
		return minuto;
	}
	public String getTv() {
		return tv;
	}
	public String getEmisora() {
		return emisora;
	}
	public String getSerie() {
		return serie;
	}
	public Integer getIdInstitucion() {
		return idInstitucion;
	}
	public String getFolioInstitucion() {
		return folioInstitucion;
	}
	public Boolean getEsDespuesFechaCorte() {
		return esDespuesFechaCorte;
	}
	public void setIdHorario(Long idHorario) {
		this.idHorario = idHorario;
	}
	public void setIdCuentaNombrada(Long idCuentaNombrada) {
		this.idCuentaNombrada = idCuentaNombrada;
	}
	public void setPorcentajeRet(Float porcentajeRet) {
		this.porcentajeRet = porcentajeRet;
	}
	public void setDias(Integer dias) {
		this.dias = dias;
	}
	public void setHora(Integer hora) {
		this.hora = hora;
	}
	public void setMinuto(Integer minuto) {
		this.minuto = minuto;
	}
	public void setTv(String tv) {
		this.tv = tv;
	}
	public void setEmisora(String emisora) {
		this.emisora = emisora;
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}
	public void setIdInstitucion(Integer idInstitucion) {
		this.idInstitucion = idInstitucion;
	}
	public void setFolioInstitucion(String folioInstitucion) {
		this.folioInstitucion = folioInstitucion;
	}
	public void setEsDespuesFechaCorte(Boolean esDespuesFechaCorte) {
		this.esDespuesFechaCorte = esDespuesFechaCorte;
	}
	public Boolean getEliminado() {
		return eliminado;
	}
	public void setEliminado(Boolean eliminado) {
		this.eliminado = eliminado;
	}
	public String getHoraCompuesta(){
		StringBuffer horaCompuesta = new StringBuffer();
		
		if(hora != null && minuto != null){
			if(hora.toString().length() == 1){
				horaCompuesta.append("0").append(hora.toString()).append(":");
			}else{
				horaCompuesta.append(hora.toString()).append(":");
			}
			if(minuto.toString().length() == 1){
				horaCompuesta.append("0").append(minuto.toString());
			}else{
				horaCompuesta.append(minuto.toString());
			}
		}
		
		
		return horaCompuesta.toString();
	}
	
	public String getIdFolio(){
		StringBuffer idFolio = new StringBuffer();
		
		if(idInstitucion != null && StringUtils.isNotBlank(folioInstitucion)){
			if(idInstitucion.toString().length() == 1){
				idFolio.append("0").append(idInstitucion.toString()).append(folioInstitucion);
			}else{
				idFolio.append(idInstitucion.toString()).append(folioInstitucion);
			}
		}
		
		return idFolio.toString();
	}
	
	public void transform(HorarioBeneficiario horarioBeneficiario){
		setDias(horarioBeneficiario.getDias());
		setEliminado(Boolean.FALSE);
		setEmisora(horarioBeneficiario.getEmisora());
		setEsDespuesFechaCorte(horarioBeneficiario.getEsDespuesFechaCorte());
		setFolioInstitucion(horarioBeneficiario.getFolioInstitucion());
		setHora(horarioBeneficiario.getHora());
		setIdCuentaNombrada(horarioBeneficiario.getIdCuentaNombrada());
		setIdHorario(horarioBeneficiario.getIdHorario());
		setIdInstitucion(horarioBeneficiario.getIdInstitucion());
		setMinuto(horarioBeneficiario.getMinuto());
		setPorcentajeRet(horarioBeneficiario.getPorcentajeRet());
		setSerie(horarioBeneficiario.getSerie());
		setTv(horarioBeneficiario.getTv());
	}
}

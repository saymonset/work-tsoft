package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="T_HORARIO_BENEFICIARIO")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_HORARIO_BENEFICIARIO", allocationSize = 1, initialValue = 1)
public class HorarioBeneficiario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8936648256275645965L;
	
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
	private Date fechaEliminacion;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "ID_HORARIO", unique = true, nullable = false)
	public Long getIdHorario() {
		return idHorario;
	}
	
	@Column(name = "ID_CUENTA_NOMBRADA", unique = false, nullable = false)
	public Long getIdCuentaNombrada() {
		return idCuentaNombrada;
	}
	
	@Column(name = "PORCENTAJE_RETENCION", unique = false, nullable = true)
	public Float getPorcentajeRet() {
		return porcentajeRet;
	}
	
	@Column(name = "DIAS", unique = false, nullable = true)
	public Integer getDias() {
		return dias;
	}
	
	@Column(name = "HORA", unique = false, nullable = true)
	public Integer getHora() {
		return hora;
	}
	
	@Column(name = "MINUTO", unique = false, nullable = true)
	public Integer getMinuto() {
		return minuto;
	}
	
	@Column(name = "TV", unique = false, nullable = true)
	public String getTv() {
		return tv;
	}
	
	@Column(name = "EMISORA", unique = false, nullable = true)
	public String getEmisora() {
		return emisora;
	}
	
	@Column(name = "SERIE", unique = false, nullable = true)
	public String getSerie() {
		return serie;
	}
	
	@Column(name = "ID_INSTITUCION", unique = false, nullable = true)	
	public Integer getIdInstitucion() {
		return idInstitucion;
	}

	@Column(name = "FOLIO_INSTITUCION", unique = false, nullable = true)
	public String getFolioInstitucion() {
		return folioInstitucion;
	}
	
	@Column(name = "DIAS_DESPUES_FECHA_CORTE", unique = false, nullable = false)
	public Boolean getEsDespuesFechaCorte() {
		return esDespuesFechaCorte;
	}
	
	@Column(name = "ELIMINADO", unique = false, nullable = false)
	public Boolean getEliminado() {
		return eliminado;
	}

	@Column(name = "FECHA_ELIMINACION", unique = false, nullable = true)
	public Date getFechaEliminacion() {
		return fechaEliminacion;
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
	public void setEliminado(Boolean eliminado) {
		this.eliminado = eliminado;
	}
	public void setFechaEliminacion(Date fechaEliminacion) {
		this.fechaEliminacion = fechaEliminacion;
	}
}

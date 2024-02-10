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
@Table(name="T_EXCEPCION_EMISION_BENEF")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_EXCEPCION_EMISION_BENEF", allocationSize = 1, initialValue = 1)
public class ExcepcionEmisionBenef implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7453006843930948371L;
	
	private Long idExcepcionEmision;
	private Long idCuentaNombrada;
	private String tv;
	private String emisora;
	private String serie;
	private String isin;
	private Boolean eliminado;
	private Date fechaEliminacion;
	private Boolean esPorcentajeCero;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "ID_EXCEPCION_EMISION", unique = true, nullable = false)
	public Long getIdExcepcionEmision() {
		return idExcepcionEmision;
	}
	
	@Column(name = "ID_CUENTA_NOMBRADA", unique = false, nullable = false)
	public Long getIdCuentaNombrada() {
		return idCuentaNombrada;
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
	
	@Column(name = "ISIN", unique = false, nullable = true)
	public String getIsin() {
		return isin;
	}
	
	@Column(name = "ELIMINADO", unique = false, nullable = false)
	public Boolean getEliminado() {
		return eliminado;
	}
	
	@Column(name = "FECHA_ELIMINACION", unique = false, nullable = true)
	public Date getFechaEliminacion() {
		return fechaEliminacion;
	}
	
	@Column(name = "ES_PORCENTAJE_CERO", unique = false, nullable = true)
	public Boolean getEsPorcentajeCero() {
		return esPorcentajeCero;
	}
	
	public void setIdExcepcionEmision(Long idExcepcionEmision) {
		this.idExcepcionEmision = idExcepcionEmision;
	}
	public void setIdCuentaNombrada(Long idCuentaNombrada) {
		this.idCuentaNombrada = idCuentaNombrada;
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
	public void setIsin(String isin) {
		this.isin = isin;
	}
	public void setEliminado(Boolean eliminado) {
		this.eliminado = eliminado;
	}
	public void setFechaEliminacion(Date fechaEliminacion) {
		this.fechaEliminacion = fechaEliminacion;
	}

	public void setEsPorcentajeCero(Boolean esPorcentajeCero) {
		this.esPorcentajeCero = esPorcentajeCero;
	}	

}

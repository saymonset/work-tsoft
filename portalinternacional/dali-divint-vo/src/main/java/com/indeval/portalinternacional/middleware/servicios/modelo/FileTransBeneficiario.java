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
@Table(name="T_FILETRANS_BENEFICIARIO")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_FILETRANS_BENEFICIARIO", allocationSize = 1, initialValue = 1)
public class FileTransBeneficiario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -225538735123002631L;
	
	private Long idFileTansfeBenef;
	private String folioInstitucion;
	private Integer idInstitucion;
	private String tv;
	private String emisora;
	private String serie;
	private String cupon;
	private Integer tipoDerecho;
	private Integer subtipoDerecho;
	private Long cantidadTitulos;
	private String uoi;
	private Date fechaCorte;
	private String cuenta;
	private Date fechaRegistro;
	private String error;
	private String linea;
	private Integer consecCampoError;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "ID_FILETRANS_BENEF", unique = true, nullable = false)
	public Long getIdFileTansfeBenef() {
		return idFileTansfeBenef;
	}
	
	@Column(name = "FOLIO_INSTITUCION", unique = false, nullable = false)
	public String getFolioInstitucion() {
		return folioInstitucion;
	}
	@Column(name = "ID_INSTITUCION", unique = false, nullable = false)
	public Integer getIdInstitucion() {
		return idInstitucion;
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
	@Column(name = "CUPON", unique = false, nullable = true)
	public String getCupon() {
		return cupon;
	}
	@Column(name = "TIPO_DERECHO", unique = false, nullable = true)
	public Integer getTipoDerecho() {
		return tipoDerecho;
	}
	@Column(name = "SUBTIPO_DERECHO", unique = false, nullable = true)
	public Integer getSubtipoDerecho() {
		return subtipoDerecho;
	}
	@Column(name = "CANTIDAD_TITULOS", unique = false, nullable = true)
	public Long getCantidadTitulos() {
		return cantidadTitulos;
	}
	@Column(name = "UOI", unique = false, nullable = true)
	public String getUoi() {
		return uoi;
	}
	@Column(name = "FECHA_CORTE", unique = false, nullable = true)
	public Date getFechaCorte() {
		return fechaCorte;
	}
	@Column(name = "CUENTA", unique = false, nullable = true)
	public String getCuenta() {
		return cuenta;
	}
	@Column(name = "FECHA_REGISTRO", unique = false, nullable = false)
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	@Column(name = "ERROR", unique = false, nullable = true)
	public String getError() {
		return error;
	}
	@Column(name = "LINEA", unique = false, nullable = true)
	public String getLinea() {
		return linea;
	}
	@Column(name = "CONSEC_CAMPO_ERROR", unique = false, nullable = true)
	public Integer getConsecCampoError() {
		return consecCampoError;
	}
	public void setFolioInstitucion(String folioInstitucion) {
		this.folioInstitucion = folioInstitucion;
	}
	public void setIdInstitucion(Integer idInstitucion) {
		this.idInstitucion = idInstitucion;
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
	public void setCupon(String cupon) {
		this.cupon = cupon;
	}
	public void setTipoDerecho(Integer tipoDerecho) {
		this.tipoDerecho = tipoDerecho;
	}
	public void setSubtipoDerecho(Integer subtipoDerecho) {
		this.subtipoDerecho = subtipoDerecho;
	}
	public void setCantidadTitulos(Long cantidadTitulos) {
		this.cantidadTitulos = cantidadTitulos;
	}
	public void setUoi(String uoi) {
		this.uoi = uoi;
	}
	public void setFechaCorte(Date fechaCorte) {
		this.fechaCorte = fechaCorte;
	}
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public void setError(String error) {
		this.error = error;
	}
	public void setLinea(String linea) {
		this.linea = linea;
	}
	public void setConsecCampoError(Integer consecCampoError) {
		this.consecCampoError = consecCampoError;
	}
	public void setIdFileTansfeBenef(Long idFileTansfeBenef) {
		this.idFileTansfeBenef = idFileTansfeBenef;
	}
	
	@Override
	public String toString() {
		return "FileTransBeneficiario [idFileTansfeBenef=" + idFileTansfeBenef + ", folioInstitucion="
				+ folioInstitucion + ", idInstitucion=" + idInstitucion + ", tv=" + tv + ", emisora=" + emisora
				+ ", serie=" + serie + ", cupon=" + cupon + ", tipoDerecho=" + tipoDerecho + ", subtipoDerecho="
				+ subtipoDerecho + ", cantidadTitulos=" + cantidadTitulos + ", uoi=" + uoi + ", fechaCorte="
				+ fechaCorte + ", cuenta=" + cuenta + ", fechaRegistro=" + fechaRegistro + ", error=" + error
				+ ", linea=" + linea + ", consecCampoError=" + consecCampoError + "]";
	}
	
}

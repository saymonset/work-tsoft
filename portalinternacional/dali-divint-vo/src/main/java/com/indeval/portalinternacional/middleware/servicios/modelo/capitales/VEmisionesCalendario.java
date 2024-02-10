package com.indeval.portalinternacional.middleware.servicios.modelo.capitales;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * The persistent class for the T_EMISION_INT_SIC database table.
 * 
 */
@Entity
@Table(name="V_EMISIONES_INT")
public class VEmisionesCalendario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4858581646477894518L;
	
	//valor consecutivo generado en la consulta
	@Id
	@Column(name="ID_EMISION_UNICA")
	private long idEmisionUnico;
	
	@Column(name="ID_EMISION")
	private long idEmision;
	
	@Column(name = "CLAVE_TIPO_VALOR")
	private String tipoValor;
	
	@Column(name = "ISIN")
	private String isin;
	
	@Column(name = "EMISORA")
	private String emisora;
	
	@Column(name = "SERIE")
	private String serie;
	
	@Column(name = "RAZON_SOCIAL")
	private String razonSocial;
	
	@Column(name = "ESTATUS_REGISTRO")
	private String estatusRegistro;
	
	@Column(name = "DETALLE_CUSTODIO")
	private String detalleCustodio;
	 
	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_ALTA")
	private Date fechaAlta;
	
	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_BAJA")
	private Date fechaBaja;
	
	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_ULT_MODIFICACION")
	private Date fechaModificacion;
	
	
	@JoinColumn(name="DESCRIPCION")
	private String descripcion;
	
	@Column(name = "NOMBRE_PAIS")
	private String paisOrigen;
	
	@Column(name = "CUSTODIO")
	private String custodio;
	
	@Column(name = "DESC_ESTATUS_EMISION")
	private String estatusEmision;
	
	@Column(name = "LISTADA")
	private String listada;
	
	@Transient
	private boolean permiteEnvio;

	/**
	 * @return the idEmisionUnico
	 */
	public long getIdEmisionUnico() {
		return idEmisionUnico;
	}

	/**
	 * @param idEmisionUnico the idEmisionUnico to set
	 */
	public void setIdEmisionUnico(long idEmisionUnico) {
		this.idEmisionUnico = idEmisionUnico;
	}

	/**
	 * @return the idEmision
	 */
	public long getIdEmision() {
		return idEmision;
	}

	/**
	 * @param idEmision the idEmision to set
	 */
	public void setIdEmision(long idEmision) {
		this.idEmision = idEmision;
	}

	/**
	 * @return the tipoValor
	 */
	public String getTipoValor() {
		return tipoValor;
	}

	/**
	 * @param tipoValor the tipoValor to set
	 */
	public void setTipoValor(String tipoValor) {
		this.tipoValor = tipoValor;
	}

	
	/**
	 * @return the isin
	 */
	public String getIsin() {
		return isin;
	}

	/**
	 * @param isin the isinto set
	 */
	public void setIsin(String isin) {
		this.isin = isin;
	}

	/**
	 * @return the emisora
	 */
	public String getEmisora() {
		return emisora;
	}

	/**
	 * @param emisora the emisora to set
	 */
	public void setEmisora(String emisora) {
		this.emisora = emisora;
	}
	
	/**
	 * @return the serie
	 */
	public String getSerie() {
		return serie;
	}

	/**
	 * @param serie the serie to set
	 */
	public void setSerie(String serie) {
		this.serie = serie;
	}

	/**
	 * @return the razonSocial
	 */
	public String getRazonSocial() {
		return razonSocial;
	}

	/**
	 * @param razonSocial the razonSocial to set
	 */
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	/**
	 * @return the estatusRegistro
	 */
	public String getEstatusRegistro() {
		return estatusRegistro;
	}

	/**
	 * @param estatusRegistro the estatusRegistro to set
	 */
	public void setEstatusRegistro(String estatusRegistro) {
		this.estatusRegistro = estatusRegistro;
	}

	/**
	 * @return the detalleCustodio
	 */
	public String getDetalleCustodio() {
		return detalleCustodio;
	}

	/**
	 * @param detalleCustodio the detalleCustodio to set
	 */
	public void setDetalleCustodio(String detalleCustodio) {
		this.detalleCustodio = detalleCustodio;
	}

	/**
	 * @return the fechaAlta
	 */
	public Date getFechaAlta() {
		return fechaAlta;
	}

	/**
	 * @param fechaAlta the fechaAlta to set
	 */
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	/**
	 * @return the fechaBaja
	 */
	public Date getFechaBaja() {
		return fechaBaja;
	}

	/**
	 * @param fechaBaja the fechaBaja to set
	 */
	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	/**
	 * @return the fechaModificacion
	 */
	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	/**
	 * @param fechaModificacion the fechaModificacion to set
	 */
	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	/**
	 * @return the paisOrigen
	 */
	public String getPaisOrigen() {
		return paisOrigen;
	}

	/**
	 * @param paisOrigen the paisOrigen to set
	 */
	public void setPaisOrigen(String paisOrigen) {
		this.paisOrigen = paisOrigen;
	}

	/**
	 * @return the custodio
	 */
	public String getCustodio() {
		return custodio;
	}

	/**
	 * @param custodio the custodio to set
	 */
	public void setCustodio(String custodio) {
		this.custodio = custodio;
	}

	/**
	 * @return the estatusEmision
	 */
	public String getEstatusEmision() {
		return estatusEmision;
	}

	/**
	 * @param estatusEmision the estatusEmision to set
	 */
	public void setEstatusEmision(String estatusEmision) {
		this.estatusEmision = estatusEmision;
	}

	/**
	 * @return the listada
	 */
	public String getListada() {
		return listada;
	}

	/**
	 * @param listada the listada to set
	 */
	public void setListada(String listada) {
		this.listada = listada;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the permiteEnvio
	 */
	public boolean isPermiteEnvio() {
		boolean regreso = false;
		if(this.listada!=null){
			if("LISTADA".equals(this.listada) || "FULL LISTED".equals(this.listada)){
				regreso = true;
			}
		}
		return regreso;
	}

	/**
	 * @param permiteEnvio the permiteEnvio to set
	 */
	public void setPermiteEnvio(boolean permiteEnvio) {
		this.permiteEnvio = permiteEnvio;
	}

	
    
	
    
	
}

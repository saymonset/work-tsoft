package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the T_BITACORA_OPERACIONES_SIC database table.
 * 
 */
@Entity
@Table(name="T_BITACORA_OPERACIONES_SIC")
@NamedQuery(name="TBitacoraOperacionesSic.findAll", query="SELECT t FROM BitacoraOperacionesSic t")
public class BitacoraOperacionesSic implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="foliadorTOSIC", sequenceName="ID_BIT_OPERACIONES_SIC_SEQ",allocationSize=1,initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="foliadorTOSIC")
	@Column(name="ID_BITACORA_OPERACION_SIC",unique = true, nullable = false)
	private long idBitacoraOperacionSic;

	@Column(name="CVE_USUARIO_AUTORIZO")
	private String cveUsuarioAutorizo;

	@Column(name="CVE_USUARIO_OPERADOR")
	private String cveUsuarioOperador;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FECHA_SOLICITUD_AUTORIZADOR")
	private Date fechaSolicitudAutorizador;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FECHA_SOLICITUD_OPERADOR")
	private Date fechaSolicitudOperador;

	@Column(name="FOLIO_CONTROL")
	private BigInteger folioControl;

	@Column(name="ID_ESTATUS_OPERACION_ANTERIOR")
	private Long idEstatusOperacionAnterior;

	@Column(name="ID_ESTATUS_OPERACION_NUEVO")
	private Long idEstatusOperacionNuevo;
	
	@Column(name="ID_ESTATUS_BITACORA")
	private Long idEstatusBitacora;
	
	@Column(name="NUMERO_LIQUIDACION")
	private Long numeroLiquidacion;
	
	@Lob
	@Column(name="MOTIVO_CAMBIO")
	private String motivoCambio;	

	//bi-directional many-to-one association to CatEstatusBitacoraSic
	@ManyToOne
	@JoinColumn(name="ID_ESTATUS_BITACORA",insertable=false,updatable=false)
	private CatEstatusBitacoraSic CEstatusBitacoraSic;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_ESTATUS_OPERACION_NUEVO", referencedColumnName="id_estatus_operacion", nullable = false,insertable=false,updatable=false)
    private EstatusOperacion estatusOperacion;
	
	public BitacoraOperacionesSic() {
	}
	
	
	public EstatusOperacion getEstatusOperacion() {
	    return estatusOperacion;
	}
	
	public void setEstatusOperacion(EstatusOperacion estatusOperacion) {
	    this.estatusOperacion = estatusOperacion;
	}

	public long getIdBitacoraOperacionSic() {
		return this.idBitacoraOperacionSic;
	}

	public void setIdBitacoraOperacionSic(long idBitacoraOperacionSic) {
		this.idBitacoraOperacionSic = idBitacoraOperacionSic;
	}

	public String getCveUsuarioAutorizo() {
		return this.cveUsuarioAutorizo;
	}

	public void setCveUsuarioAutorizo(String cveUsuarioAutorizo) {
		this.cveUsuarioAutorizo = cveUsuarioAutorizo;
	}

	public String getCveUsuarioOperador() {
		return this.cveUsuarioOperador;
	}

	public void setCveUsuarioOperador(String cveUsuarioOperador) {
		this.cveUsuarioOperador = cveUsuarioOperador;
	}

	public Date getFechaSolicitudAutorizador() {
		return this.fechaSolicitudAutorizador;
	}

	public void setFechaSolicitudAutorizador(Date fechaSolicitudAutorizador) {
		this.fechaSolicitudAutorizador = fechaSolicitudAutorizador;
	}

	public Date getFechaSolicitudOperador() {
		return this.fechaSolicitudOperador;
	}

	public void setFechaSolicitudOperador(Date fechaSolicitudOperador) {
		this.fechaSolicitudOperador = fechaSolicitudOperador;
	}

	public BigInteger getFolioControl() {
		return this.folioControl;
	}

	public void setFolioControl(BigInteger folioControl) {
		this.folioControl = folioControl;
	}

	public Long getIdEstatusOperacionAnterior() {
		return this.idEstatusOperacionAnterior;
	}

	public void setIdEstatusOperacionAnterior(Long idEstatusOperacionAnterior) {
		this.idEstatusOperacionAnterior = idEstatusOperacionAnterior;
	}

	public Long getIdEstatusOperacionNuevo() {
		return this.idEstatusOperacionNuevo;
	}

	public void setIdEstatusOperacionNuevo(Long idEstatusOperacionNuevo) {
		this.idEstatusOperacionNuevo = idEstatusOperacionNuevo;
	}

	public CatEstatusBitacoraSic getCEstatusBitacoraSic() {
		return this.CEstatusBitacoraSic;
	}

	public void setCEstatusBitacoraSic(CatEstatusBitacoraSic CEstatusBitacoraSic) {
		this.CEstatusBitacoraSic = CEstatusBitacoraSic;
	}

	public Long getIdEstatusBitacora() {
		return idEstatusBitacora;
	}

	public void setIdEstatusBitacora(Long idEstatusBitacora) {
		this.idEstatusBitacora = idEstatusBitacora;
	}


	public String getMotivoCambio() {
		return motivoCambio;
	}


	public void setMotivoCambio(String motivoCambio) {
		this.motivoCambio = motivoCambio;
	}
	/**
	 * @return the numeroLiquidacion
	 */
	public Long getNumeroLiquidacion() {
		return numeroLiquidacion;
	}
	/**
	 * @param numeroLiquidacion the numeroLiquidacion to set
	 */
	public void setNumeroLiquidacion(Long numeroLiquidacion) {
		this.numeroLiquidacion = numeroLiquidacion;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BitacoraOperacionesSic [idBitacoraOperacionSic=" + idBitacoraOperacionSic + ", cveUsuarioAutorizo="
				+ cveUsuarioAutorizo + ", cveUsuarioOperador=" + cveUsuarioOperador + ", fechaSolicitudAutorizador="
				+ fechaSolicitudAutorizador + ", fechaSolicitudOperador=" + fechaSolicitudOperador + ", folioControl="
				+ folioControl + ", idEstatusOperacionAnterior=" + idEstatusOperacionAnterior
				+ ", idEstatusOperacionNuevo=" + idEstatusOperacionNuevo + ", idEstatusBitacora=" + idEstatusBitacora
				+ ", numeroLiquidacion=" + numeroLiquidacion + ", motivoCambio=" + motivoCambio
				+ ", CEstatusBitacoraSic=" + CEstatusBitacoraSic + ", estatusOperacion=" + estatusOperacion + "]";
	}

}
package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the V_BITACORA_OPERACIONES_SIC database table.
 * 
 */
@Entity
@Table(name="V_BITACORA_OPERACIONES_SIC")
@NamedQuery(name="VBitacoraOperacionesSic.findAll", query="SELECT v FROM VBitacoraOperacionesSic v")
public class VBitacoraOperacionesSic implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="ID_BITACORA_OPERACION_SIC", insertable=false,updatable=false)
	private BigDecimal idBitacoraOperacionSic;
	
	@Column(name="CANTIDAD_TITULOS", insertable=false,updatable=false)
	private BigDecimal cantidadTitulos;

	@Column(name="CLAVE_PIZARRA", insertable=false,updatable=false)
	private String clavePizarra;

	@Column(name="CLAVE_TIPO_INSTITUCION", insertable=false,updatable=false)
	private String claveTipoInstitucion;

	@Column(name="CLAVE_TIPO_VALOR", insertable=false,updatable=false)
	private String claveTipoValor;

	@Column(name="CVE_USUARIO_AUTORIZO", insertable=false,updatable=false)
	private String cveUsuarioAutorizo;

	@Column(name="CVE_USUARIO_OPERADOR", insertable=false,updatable=false)
	private String cveUsuarioOperador;

	@Column(name="DESC_ESTATUS_BITACORA", insertable=false,updatable=false)
	private String descEstatusBitacora;

	@Column(name="DETALLE_CUSTODIO", insertable=false,updatable=false)
	private String detalleCustodio;

	@Column(name="ESTATUS_OPERACION_ANTERIOR", insertable=false,updatable=false)
	private String estatusOperacionAnterior;

	@Column(name="ESTATUS_OPERACION_NUEVA", insertable=false,updatable=false)
	private String estatusOperacionNueva;

	@Column(name="FECHA_SOLICITUD_AUTORIZADOR", insertable=false,updatable=false)
	private Timestamp fechaSolicitudAutorizador;

	@Column(name="FECHA_SOLICITUD_OPERADOR", insertable=false,updatable=false)
	private Timestamp fechaSolicitudOperador;

	@Column(name="FOLIO_CONTROL", insertable=false,updatable=false)
	private BigDecimal folioControl;

	@Column(name="FOLIO_INSTITUCION", insertable=false,updatable=false)
	private String folioInstitucion;	

	@Column(name="ID_EMISION", insertable=false,updatable=false)
	private BigDecimal idEmision;

	@Column(name="ID_ESTATUS_OPERACION_ANTERIOR", insertable=false,updatable=false)
	private BigDecimal idEstatusOperacionAnterior;

	@Column(name="ID_ESTATUS_OPERACION_NUEVO", insertable=false,updatable=false)
	private BigDecimal idEstatusOperacionNuevo;

	@Column(name="ID_OPERACIONES_SIC", insertable=false,updatable=false)
	private BigDecimal idOperacionesSic;

    @Column(name="CAMBIO_BOVEDA", insertable=false,updatable=false)
    private Integer cambioBoveda;

	private String isin;

	@Lob
	@Column(name="MOTIVO_CAMBIO", insertable=false,updatable=false)
	private String motivoCambio;

	private String operacion;

	private String serie;

	public VBitacoraOperacionesSic() {
	}

	public BigDecimal getCantidadTitulos() {
		return this.cantidadTitulos;
	}

	public void setCantidadTitulos(BigDecimal cantidadTitulos) {
		this.cantidadTitulos = cantidadTitulos;
	}

	public String getClavePizarra() {
		return this.clavePizarra;
	}

	public void setClavePizarra(String clavePizarra) {
		this.clavePizarra = clavePizarra;
	}

	public String getClaveTipoInstitucion() {
		return this.claveTipoInstitucion;
	}

	public void setClaveTipoInstitucion(String claveTipoInstitucion) {
		this.claveTipoInstitucion = claveTipoInstitucion;
	}

	public String getClaveTipoValor() {
		return this.claveTipoValor;
	}

	public void setClaveTipoValor(String claveTipoValor) {
		this.claveTipoValor = claveTipoValor;
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

	public String getDescEstatusBitacora() {
		return this.descEstatusBitacora;
	}

	public void setDescEstatusBitacora(String descEstatusBitacora) {
		this.descEstatusBitacora = descEstatusBitacora;
	}

	public String getDetalleCustodio() {
		return this.detalleCustodio;
	}

	public void setDetalleCustodio(String detalleCustodio) {
		this.detalleCustodio = detalleCustodio;
	}

	public String getEstatusOperacionAnterior() {
		return this.estatusOperacionAnterior;
	}

	public void setEstatusOperacionAnterior(String estatusOperacionAnterior) {
		this.estatusOperacionAnterior = estatusOperacionAnterior;
	}

	public String getEstatusOperacionNueva() {
		return this.estatusOperacionNueva;
	}

	public void setEstatusOperacionNueva(String estatusOperacionNueva) {
		this.estatusOperacionNueva = estatusOperacionNueva;
	}

	public Timestamp getFechaSolicitudAutorizador() {
		return this.fechaSolicitudAutorizador;
	}

	public void setFechaSolicitudAutorizador(Timestamp fechaSolicitudAutorizador) {
		this.fechaSolicitudAutorizador = fechaSolicitudAutorizador;
	}

	public Timestamp getFechaSolicitudOperador() {
		return this.fechaSolicitudOperador;
	}

	public void setFechaSolicitudOperador(Timestamp fechaSolicitudOperador) {
		this.fechaSolicitudOperador = fechaSolicitudOperador;
	}

	public BigDecimal getFolioControl() {
		return this.folioControl;
	}

	public void setFolioControl(BigDecimal folioControl) {
		this.folioControl = folioControl;
	}

	public String getFolioInstitucion() {
		return this.folioInstitucion;
	}

	public void setFolioInstitucion(String folioInstitucion) {
		this.folioInstitucion = folioInstitucion;
	}

	public BigDecimal getIdBitacoraOperacionSic() {
		return this.idBitacoraOperacionSic;
	}

	public void setIdBitacoraOperacionSic(BigDecimal idBitacoraOperacionSic) {
		this.idBitacoraOperacionSic = idBitacoraOperacionSic;
	}

	public BigDecimal getIdEmision() {
		return this.idEmision;
	}

	public void setIdEmision(BigDecimal idEmision) {
		this.idEmision = idEmision;
	}

	public BigDecimal getIdEstatusOperacionAnterior() {
		return this.idEstatusOperacionAnterior;
	}

	public void setIdEstatusOperacionAnterior(BigDecimal idEstatusOperacionAnterior) {
		this.idEstatusOperacionAnterior = idEstatusOperacionAnterior;
	}

	public BigDecimal getIdEstatusOperacionNuevo() {
		return this.idEstatusOperacionNuevo;
	}

	public void setIdEstatusOperacionNuevo(BigDecimal idEstatusOperacionNuevo) {
		this.idEstatusOperacionNuevo = idEstatusOperacionNuevo;
	}

	public BigDecimal getIdOperacionesSic() {
		return this.idOperacionesSic;
	}

	public void setIdOperacionesSic(BigDecimal idOperacionesSic) {
		this.idOperacionesSic = idOperacionesSic;
	}

	public String getIsin() {
		return this.isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

	public String getMotivoCambio() {
		return this.motivoCambio;
	}

	public void setMotivoCambio(String motivoCambio) {
		this.motivoCambio = motivoCambio;
	}

	public String getOperacion() {
		return this.operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	public String getSerie() {
		return this.serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

    public Integer getCambioBoveda() {
        return cambioBoveda;
    }

    public void setCambioBoveda(Integer cambioBoveda) {
        this.cambioBoveda = cambioBoveda;
    }

}
package com.indeval.portalinternacional.middleware.servicios.modelo;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the T_CUENTA_TRANSITORIA database table.
 * 
 */
@Entity
@Table(name="T_CUENTA_TRANSITORIA")
@SequenceGenerator(name="SEQ_CUENTA_TRANSITORIA", sequenceName = "T_CUENTA_TRANSITORIA_SEQ")
public class CuentaTransitoria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_CUENTA_TRANSITORIA")
	@Column(name="ID_CUENTA_TRANSITORIA")
	private long idCuentaTransitoria;

	private String aplicado;

	@Column(name="CODIGO_RELACION")
	private String codigoRelacion;

	@Lob
	@Column(name="DETALLE_MOVIMIENTOS")
	private String detalleMovimientos;

	@Column(name="ESTADO_OPERACION")
	private Integer estadoOperacion;

	@Column(name="ESTADO_OPERACION_INTERNO")
	private Integer estadoOperacionInterno;

	@Column(name="FECHA_MODIFICACION")
	private Date fechaModificacion;

	@Column(name="FECHA_RECEPCION")
	private Date fechaRecepcion;

	@Column(name="FOLIO_RELACIONADO")
	private String folioRelacionado;

	@Column(name="ID_CALENDARIO_INT")
	private Long idCalendarioInt;

	private BigDecimal monto;

	@Column(name="REFERENCIA_MENSAJE")
	private String referenciaMensaje;

	@Column(name="REFERENCIA_OPERACION")
	private String referenciaOperacion;

	private BigDecimal remanente;

	@Column(name="TIPO_MENSAJE")
	private String tipoMensaje;

	@Lob
	private String xml;

	//bi-directional many-to-one association to CCustodio
	@ManyToOne
	@JoinColumn(name="ID_CUSTODIO")
	private Custodio IdCustodio;

	//bi-directional many-to-one association to TConfirmacionEfectivo
	@ManyToOne
	@JoinColumn(name="ID_CONFIRMACION")
	private ConfirmacionEfectivo idConfirmacionEfectivo;

	//bi-directional many-to-one association to CDivisa
	@ManyToOne
	@JoinColumn(name="ID_DIVISA")
	private DivisaInt idDivisa;

	public long getIdCuentaTransitoria() {
		return this.idCuentaTransitoria;
	}

	public void setIdCuentaTransitoria(long idCuentaTransitoria) {
		this.idCuentaTransitoria = idCuentaTransitoria;
	}

	public String getAplicado() {
		return this.aplicado;
	}

	public void setAplicado(String aplicado) {
		this.aplicado = aplicado;
	}

	public String getCodigoRelacion() {
		return this.codigoRelacion;
	}

	public void setCodigoRelacion(String codigoRelacion) {
		this.codigoRelacion = codigoRelacion;
	}

	public String getDetalleMovimientos() {
		return this.detalleMovimientos;
	}

	public void setDetalleMovimientos(String detalleMovimientos) {
		this.detalleMovimientos = detalleMovimientos;
	}

	

	public Integer getEstadoOperacion() {
		return estadoOperacion;
	}

	public void setEstadoOperacion(Integer estadoOperacion) {
		this.estadoOperacion = estadoOperacion;
	}

	public Integer getEstadoOperacionInterno() {
		return estadoOperacionInterno;
	}

	public void setEstadoOperacionInterno(Integer estadoOperacionInterno) {
		this.estadoOperacionInterno = estadoOperacionInterno;
	}

	public Object getFechaModificacion() {
		return this.fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public Object getFechaRecepcion() {
		return this.fechaRecepcion;
	}

	public void setFechaRecepcion(Date fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}

	public String getFolioRelacionado() {
		return this.folioRelacionado;
	}

	public void setFolioRelacionado(String folioRelacionado) {
		this.folioRelacionado = folioRelacionado;
	}

	public BigDecimal getMonto() {
		return this.monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public String getReferenciaMensaje() {
		return this.referenciaMensaje;
	}

	public void setReferenciaMensaje(String referenciaMensaje) {
		this.referenciaMensaje = referenciaMensaje;
	}

	public String getReferenciaOperacion() {
		return this.referenciaOperacion;
	}

	public void setReferenciaOperacion(String referenciaOperacion) {
		this.referenciaOperacion = referenciaOperacion;
	}

	public BigDecimal getRemanente() {
		return this.remanente;
	}

	public void setRemanente(BigDecimal remanente) {
		this.remanente = remanente;
	}

	public String getTipoMensaje() {
		return this.tipoMensaje;
	}

	public void setTipoMensaje(String tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
	}

	public String getXml() {
		return this.xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public Long getIdCalendarioInt() {
		return idCalendarioInt;
	}

	public void setIdCalendarioInt(Long idCalendarioInt) {
		this.idCalendarioInt = idCalendarioInt;
	}

	public Custodio getIdCustodio() {
		return IdCustodio;
	}

	public void setIdCustodio(Custodio idCustodio) {
		IdCustodio = idCustodio;
	}

	public ConfirmacionEfectivo getIdConfirmacionEfectivo() {
		return idConfirmacionEfectivo;
	}

	public void setIdConfirmacionEfectivo(ConfirmacionEfectivo idConfirmacionEfectivo) {
		this.idConfirmacionEfectivo = idConfirmacionEfectivo;
	}

	public DivisaInt getIdDivisa() {
		return idDivisa;
	}

	public void setIdDivisa(DivisaInt idDivisa) {
		this.idDivisa = idDivisa;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((IdCustodio == null) ? 0 : IdCustodio.hashCode());
		result = prime * result + ((aplicado == null) ? 0 : aplicado.hashCode());
		result = prime * result + ((codigoRelacion == null) ? 0 : codigoRelacion.hashCode());
		result = prime * result + ((detalleMovimientos == null) ? 0 : detalleMovimientos.hashCode());
		result = prime * result + ((estadoOperacion == null) ? 0 : estadoOperacion.hashCode());
		result = prime * result + ((estadoOperacionInterno == null) ? 0 : estadoOperacionInterno.hashCode());
		result = prime * result + ((fechaModificacion == null) ? 0 : fechaModificacion.hashCode());
		result = prime * result + ((fechaRecepcion == null) ? 0 : fechaRecepcion.hashCode());
		result = prime * result + ((folioRelacionado == null) ? 0 : folioRelacionado.hashCode());
		result = prime * result + ((idCalendarioInt == null) ? 0 : idCalendarioInt.hashCode());
		result = prime * result + ((idConfirmacionEfectivo == null) ? 0 : idConfirmacionEfectivo.hashCode());
		result = prime * result + (int) (idCuentaTransitoria ^ (idCuentaTransitoria >>> 32));
		result = prime * result + ((idDivisa == null) ? 0 : idDivisa.hashCode());
		result = prime * result + ((monto == null) ? 0 : monto.hashCode());
		result = prime * result + ((referenciaMensaje == null) ? 0 : referenciaMensaje.hashCode());
		result = prime * result + ((referenciaOperacion == null) ? 0 : referenciaOperacion.hashCode());
		result = prime * result + ((remanente == null) ? 0 : remanente.hashCode());
		result = prime * result + ((tipoMensaje == null) ? 0 : tipoMensaje.hashCode());
		result = prime * result + ((xml == null) ? 0 : xml.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CuentaTransitoria other = (CuentaTransitoria) obj;
		if (IdCustodio == null) {
			if (other.IdCustodio != null)
				return false;
		} else if (!IdCustodio.equals(other.IdCustodio))
			return false;
		if (aplicado == null) {
			if (other.aplicado != null)
				return false;
		} else if (!aplicado.equals(other.aplicado))
			return false;
		if (codigoRelacion == null) {
			if (other.codigoRelacion != null)
				return false;
		} else if (!codigoRelacion.equals(other.codigoRelacion))
			return false;
		if (detalleMovimientos == null) {
			if (other.detalleMovimientos != null)
				return false;
		} else if (!detalleMovimientos.equals(other.detalleMovimientos))
			return false;
		if (estadoOperacion == null) {
			if (other.estadoOperacion != null)
				return false;
		} else if (!estadoOperacion.equals(other.estadoOperacion))
			return false;
		if (estadoOperacionInterno == null) {
			if (other.estadoOperacionInterno != null)
				return false;
		} else if (!estadoOperacionInterno.equals(other.estadoOperacionInterno))
			return false;
		if (fechaModificacion == null) {
			if (other.fechaModificacion != null)
				return false;
		} else if (!fechaModificacion.equals(other.fechaModificacion))
			return false;
		if (fechaRecepcion == null) {
			if (other.fechaRecepcion != null)
				return false;
		} else if (!fechaRecepcion.equals(other.fechaRecepcion))
			return false;
		if (folioRelacionado == null) {
			if (other.folioRelacionado != null)
				return false;
		} else if (!folioRelacionado.equals(other.folioRelacionado))
			return false;
		if (idCalendarioInt == null) {
			if (other.idCalendarioInt != null)
				return false;
		} else if (!idCalendarioInt.equals(other.idCalendarioInt))
			return false;
		if (idConfirmacionEfectivo == null) {
			if (other.idConfirmacionEfectivo != null)
				return false;
		} else if (!idConfirmacionEfectivo.equals(other.idConfirmacionEfectivo))
			return false;
		if (idCuentaTransitoria != other.idCuentaTransitoria)
			return false;
		if (idDivisa == null) {
			if (other.idDivisa != null)
				return false;
		} else if (!idDivisa.equals(other.idDivisa))
			return false;
		if (monto == null) {
			if (other.monto != null)
				return false;
		} else if (!monto.equals(other.monto))
			return false;
		if (referenciaMensaje == null) {
			if (other.referenciaMensaje != null)
				return false;
		} else if (!referenciaMensaje.equals(other.referenciaMensaje))
			return false;
		if (referenciaOperacion == null) {
			if (other.referenciaOperacion != null)
				return false;
		} else if (!referenciaOperacion.equals(other.referenciaOperacion))
			return false;
		if (remanente == null) {
			if (other.remanente != null)
				return false;
		} else if (!remanente.equals(other.remanente))
			return false;
		if (tipoMensaje == null) {
			if (other.tipoMensaje != null)
				return false;
		} else if (!tipoMensaje.equals(other.tipoMensaje))
			return false;
		if (xml == null) {
			if (other.xml != null)
				return false;
		} else if (!xml.equals(other.xml))
			return false;
		return true;
	}
	
	

}
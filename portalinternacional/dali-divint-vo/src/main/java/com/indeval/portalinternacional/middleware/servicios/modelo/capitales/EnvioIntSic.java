package com.indeval.portalinternacional.middleware.servicios.modelo.capitales;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.indeval.portalinternacional.middleware.servicios.modelo.DestinatarioIntSic;


/**
 * The persistent class for the T_ENVIO_INT_SIC database table.
 * 
 */
@Entity
@Table(name="T_ENVIO_INT_SIC")
@NamedQuery(name="EnvioIntSic.findAll", query="SELECT e FROM EnvioIntSic e")
@SequenceGenerator(name = "foliador", sequenceName = "T_ENVIO_INT_SEQ", allocationSize = 1, initialValue = 1)
public class EnvioIntSic implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name="ID_ENVIO_INT_SIC")
	private long idEnvioIntSic;

	@Column(name="ACK_AMH")
	private Integer ackAmh;

	@Column(name="ACK_SWIFT")
	private Integer ackSwift;

	@Column(name="FECHA_ACTUALIZACION")
	private Date fechaActualizacion;
	
	@Column(name="FECHA_ENVIO")
	private Date fechaEnvio;

	@ManyToOne
	@JoinColumn(name="ID_DESTINATARIO_INT")
	private DestinatarioIntSic destinatarioIntSic;

	//bi-directional many-to-one association to CalendarioInt
	@ManyToOne
	@JoinColumn(name="ID_CALENDARIO_INT")
	private CalendarioDerechosCapitales calendarioDerechosCapitales;

	//bi-directional many-to-one association to HistoricoDerechoInt
	@ManyToOne
	@JoinColumn(name="ID_HIST_DERECHO_INT")
	private CalendarioHistoricoDerechosCapitales calendarioHistoricoDerechosCapitales;

	@ManyToOne
	@JoinColumn(name="ID_T_ENVIO_BITACORA_INT_SIC")
	private EnvioBitacoraIntSic envioBitacoraIntSic;
	
	public EnvioIntSic() {
	}

	public long getIdEnvioIntSic() {
		return this.idEnvioIntSic;
	}

	public void setIdEnvioIntSic(long idEnvioIntSic) {
		this.idEnvioIntSic = idEnvioIntSic;
	}

	public Integer getAckAmh() {
		return this.ackAmh;
	}

	public void setAckAmh(Integer ackAmh) {
		this.ackAmh = ackAmh;
	}

	public Integer getAckSwift() {
		return this.ackSwift;
	}

	public void setAckSwift(Integer ackSwift) {
		this.ackSwift = ackSwift;
	}

	public Date getFechaActualizacion() {
		  return this.fechaActualizacion != null ? new Date(this.fechaActualizacion.getTime()) : null;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion != null ? new Date(fechaActualizacion.getTime()) : null;
	}
   
    /**
	 * @return the destinatarioIntSic
	 */
	public DestinatarioIntSic getDestinatarioIntSic() {
		return destinatarioIntSic;
	}

	/**
	 * @param destinatarioIntSic the destinatarioIntSic to set
	 */
	public void setDestinatarioIntSic(DestinatarioIntSic destinatarioIntSic) {
		this.destinatarioIntSic = destinatarioIntSic;
	}

	/**
	 * @return the calendarioDerechosCapitales
	 */
	public CalendarioDerechosCapitales getCalendarioDerechosCapitales() {
		return calendarioDerechosCapitales;
	}

	/**
	 * @param calendarioDerechosCapitales the calendarioDerechosCapitales to set
	 */
	public void setCalendarioDerechosCapitales(CalendarioDerechosCapitales calendarioDerechosCapitales) {
		this.calendarioDerechosCapitales = calendarioDerechosCapitales;
	}

	/**
	 * @return the calendarioHistoricoDerechosCapitales
	 */
	public CalendarioHistoricoDerechosCapitales getCalendarioHistoricoDerechosCapitales() {
		return calendarioHistoricoDerechosCapitales;
	}

	/**
	 * @param calendarioHistoricoDerechosCapitales the calendarioHistoricoDerechosCapitales to set
	 */
	public void setCalendarioHistoricoDerechosCapitales(
			CalendarioHistoricoDerechosCapitales calendarioHistoricoDerechosCapitales) {
		this.calendarioHistoricoDerechosCapitales = calendarioHistoricoDerechosCapitales;
	}

	/**
	 * @return the envioBitacoraIntSic
	 */
	public EnvioBitacoraIntSic getEnvioBitacoraIntSic() {
		return envioBitacoraIntSic;
	}

	/**
	 * @param envioBitacoraIntSic the envioBitacoraIntSic to set
	 */
	public void setEnvioBitacoraIntSic(EnvioBitacoraIntSic envioBitacoraIntSic) {
		this.envioBitacoraIntSic = envioBitacoraIntSic;
	}

	/**
	 * @return the fechaEnvio
	 */
	public Date getFechaEnvio() {
	 return this.fechaEnvio != null ? new Date(this.fechaEnvio.getTime()) : null;
	}

	/**
	 * @param fechaEnvio the fechaEnvio to set
	 */
	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio != null ? new Date(fechaEnvio.getTime()) : null;
	}
      

}
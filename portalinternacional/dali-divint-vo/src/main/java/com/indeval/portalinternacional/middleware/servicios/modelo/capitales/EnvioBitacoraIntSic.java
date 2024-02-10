package com.indeval.portalinternacional.middleware.servicios.modelo.capitales;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
* The persistent class for the T_ENVIO_BITACORA_INT_SIC database table.
*
*/
@Entity
@Table(name="T_ENVIO_BITACORA_INT_SIC")
@SequenceGenerator(name = "foliador", sequenceName = "T_ENVIO_BITACORA_INT_SIC_SEQ", allocationSize = 1, initialValue = 1)
@NamedQuery(name="EnvioBitacoraIntSic.findAll", query="SELECT e FROM EnvioBitacoraIntSic e")
public class EnvioBitacoraIntSic implements Serializable{

	 private static final long serialVersionUID = 1L;

	   @Temporal(TemporalType.DATE)
		@Column(name="FECHA_RECEPCION")
		private Date fechaRecepcion;
		
		@Id
		@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
		@Column(name="ID_T_ENVIO_BITACORA_INT_SIC")
		private long id;

		@Lob
		@Column(name="MENSAJE_XML")
		private String mensajeXml;

		@Column(name="USUARIO")
		private String usuario;
		
		@Column(name="ID_HISTORICO")
		private Long idHistorico;
		
		@Column(name="ID_CALENDARIO")
		private Long idCalendario;
		
		@Column(name="DESTINATARIO")
		private String destinatario;

		public EnvioBitacoraIntSic(Date fechaRecepcion, long id, String mensajeXml, String usuario, Long idHistorico,
				Long idCalendario, String destinatario) {
			super();
			this.fechaRecepcion = fechaRecepcion;
			this.id = id;
			this.mensajeXml = mensajeXml;
			this.usuario = usuario;
			this.idHistorico = idHistorico;
			this.idCalendario = idCalendario;
			this.destinatario = destinatario;
		}

		public EnvioBitacoraIntSic() {
		}

		public Date getFechaRecepcion() {
			return this.fechaRecepcion;
		}

		public void setFechaRecepcion(Date fechaRecepcion) {
			this.fechaRecepcion = fechaRecepcion;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getMensajeXml() {
			return this.mensajeXml;
		}

		public void setMensajeXml(String mensajeXml) {
			this.mensajeXml = mensajeXml;
		}

		public String getUsuario() {
			return this.usuario;
		}

		public void setUsuario(String usuario) {
			this.usuario = usuario;
		}

		public Long getIdHistorico() {
			return idHistorico;
		}

		public void setIdHistorico(Long idHistorico) {
			this.idHistorico = idHistorico;
		}

		public Long getIdCalendario() {
			return idCalendario;
		}

		public void setIdCalendario(Long idCalendario) {
			this.idCalendario = idCalendario;
		}

		public String getDestinatario() {
			return destinatario;
		}

		public void setDestinatario(String destinatario) {
			this.destinatario = destinatario;
		}
}

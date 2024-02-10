package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "C_ESTADO_MSJ_INT_SIC")
public class EstadoMensajeIntSic implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id		
	@Column(name = "ID_ESTADO_MSJ_INT")
	private Long idEstadoMensajeIntSic;
	
	@Column(name = "NOMBRE")
	private String nombreEstado;
			
	@Column(name = "FECHA_CREACION")
	private Date fechaCreacion;
	
	public EstadoMensajeIntSic() {
	
	}

	public EstadoMensajeIntSic(Long idEstadoMensajeIntSic, String nombreEstado, Date fechaCreacion) {
		super();
		this.idEstadoMensajeIntSic = idEstadoMensajeIntSic;
		this.nombreEstado = nombreEstado;
		this.fechaCreacion = fechaCreacion;
	}


	public Long getIdEstadoMensajeIntSic() {
		return idEstadoMensajeIntSic;
	}

	public void setIdEstadoMensajeIntSic(Long idEstadoMensajeIntSic) {
		this.idEstadoMensajeIntSic = idEstadoMensajeIntSic;
	}

	public String getNombreEstado() {
		return nombreEstado;
	}

	public void setNombreEstado(String nombreEstado) {
		this.nombreEstado = nombreEstado;
	}


	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

}

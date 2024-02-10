package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "C_DESTINATARIO_INT_SIC")
public class DestinatarioIntSic implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id		
	@Column(name = "ID_DESTINATARIO_INT")
	private Long idDestinatario;
	
	@Column(name = "NOMBRE")
	private String nombre;
	
	@Column(name = "FECHA_CREACION")
	private Date fechaCreacion;
	
	@Column(name = "ESTADO")
	private Long estado;
	
	@Column(name = "BICCODE")
	private String  biccode;
	
	@Column(name = "REQUERIDO_TODOS")
	private Boolean  requeridoTodos;
	
	
	public DestinatarioIntSic(Long idDestinatario, String nombre, Date fechaCreacion, Long estado, String biccode,
			Boolean requeridoTodos) {
		super();
		this.idDestinatario = idDestinatario;
		this.nombre = nombre;
		this.fechaCreacion = fechaCreacion;
		this.estado = estado;
		this.biccode = biccode;
		this.requeridoTodos = requeridoTodos;
	}

	public DestinatarioIntSic() {
		super();
	}

	public Long getIdDestinatario() {
		return idDestinatario;
	}

	public void setIdDestinatario(Long idDestinatario) {
		this.idDestinatario = idDestinatario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Long getEstado() {
		return estado;
	}

	public void setEstado(Long estado) {
		this.estado = estado;
	}

	/**
	 * @return the biccode
	 */
	public String getBiccode() {
		return biccode;
	}

	/**
	 * @param biccode the biccode to set
	 */
	public void setBiccode(String biccode) {
		this.biccode = biccode;
	}

	/**
	 * @return the requeridoTodos
	 */
	public Boolean getRequeridoTodos() {
		return requeridoTodos;
	}

	/**
	 * @param requeridoTodos the requeridoTodos to set
	 */
	public void setRequeridoTodos(Boolean requeridoTodos) {
		this.requeridoTodos = requeridoTodos;
	}
	
	
}

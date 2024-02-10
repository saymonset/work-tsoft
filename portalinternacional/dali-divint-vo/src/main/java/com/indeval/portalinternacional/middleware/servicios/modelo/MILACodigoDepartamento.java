package com.indeval.portalinternacional.middleware.servicios.modelo;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author arodriguez
 * 
 */
@Entity
@Table(name = "C_ENTIDADES_FEDERATIVAS")
public class MILACodigoDepartamento implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 5463912108742919060L;

	@Id
	@Column(name = "ID_ENTIDAD_FEDERATIVA")
	private Long idCodigoEstado;
	
	@Column(name = "NOMBRE_ENTIDAD_FED")
	private String nombre;

	public MILACodigoDepartamento() {
		super();
	}

	public MILACodigoDepartamento(Long idCodigoEstado) {
		super();
		this.idCodigoEstado = idCodigoEstado;
	}

	/**
	 * @return the idCodigoEstado
	 */
	public Long getIdCodigoEstado() {
		return idCodigoEstado;
	}

	/**
	 * @param idCodigoEstado the idCodigoEstado to set
	 */
	public void setIdCodigoEstado(Long idCodigoEstado) {
		this.idCodigoEstado = idCodigoEstado;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
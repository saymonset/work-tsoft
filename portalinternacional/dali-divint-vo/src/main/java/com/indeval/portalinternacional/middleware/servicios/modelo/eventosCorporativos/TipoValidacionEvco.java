/**
 * 
 */
package com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
 *
 */
@Entity
@Table(name="C_TIPO_VALIDACION_EVCO")
@SequenceGenerator(name="foliador",sequenceName="SEQ_T_VALIDACION_EVCO",allocationSize=1,initialValue=1)
public class TipoValidacionEvco implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idtipoValidacion;
	private String nombre;
	private String descripcion;
	/**
	 * @return the idtipoValidacion
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator = "foliador")
	@Column(name="ID_TIPO_VALIDACION", unique=true, nullable=false)
	public Long getIdtipoValidacion() {
		return idtipoValidacion;
	}
	/**
	 * @param idtipoValidacion the idtipoValidacion to set
	 */
	public void setIdtipoValidacion(Long idtipoValidacion) {
		this.idtipoValidacion = idtipoValidacion;
	}
	/**
	 * @return the nombre
	 */
	@Column(name="NOMBRE", unique=false, nullable=false )
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return the descripcion
	 */
	@Column(name="DESCRIPCION",unique=false, nullable=false)
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
}

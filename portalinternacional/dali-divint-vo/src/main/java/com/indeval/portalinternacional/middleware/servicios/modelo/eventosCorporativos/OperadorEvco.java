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
 * @author kode-
 *
 */
@Entity
@Table(name="C_OPERADOR_EVCO")
@SequenceGenerator(name="foliador",sequenceName="",allocationSize=1, initialValue=1)
public class OperadorEvco implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idoperador;
	private String operador;
	private String descripcion;
	/**
	 * @return the idoperador
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name="ID_OPERADOR", unique=true ,nullable=false)
	public Long getIdoperador() {
		return idoperador;
	}
	/**
	 * @param idoperador the idoperador to set
	 */
	public void setIdoperador(Long idoperador) {
		this.idoperador = idoperador;
	}
	/**
	 * @return the operador
	 */
	@Column(name="OPERADOR", unique=false, nullable=false)
	public String getOperador() {
		return operador;
	}
	/**
	 * @param operador the operador to set
	 */
	public void setOperador(String operador) {
		this.operador = operador;
	}
	/**
	 * @return the descripcion
	 */
	@Column(name="DESCRIPCION", unique=false, nullable=false)
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

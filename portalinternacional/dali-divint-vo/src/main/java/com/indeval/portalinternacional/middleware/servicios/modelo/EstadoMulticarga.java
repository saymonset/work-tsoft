package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "C_ESTADO_MULTICARGA")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "com.indeval.portaldali.persistence.modelo.EstadoMulticarga")
public class EstadoMulticarga implements Serializable{

	/**
	 * Constante de serializacion por default 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * identificador del estado multicarga
	 * */
	private Long idEstadoMulticarga;
	
	/**
	 * descriocion del estado multicarga
	 * */
	private String estadoMulticarga;
	
	/**
	 * geters and seters
	 * */

	@Id
	@Column(name = "ID_ESTADO_MULTICARGA", unique = true, nullable = false)
	public Long getIdEstadoMulticarga() {
		return idEstadoMulticarga;
	}

	@Column(name ="ESTADO_MULTICARGA", unique = false, nullable = false)
	public String getEstadoMulticarga() {
		return estadoMulticarga;
	}
	
	
	public void setIdEstadoMulticarga(Long idEstadoMulticarga) {
		this.idEstadoMulticarga = idEstadoMulticarga;
	}
	

	public void setEstadoMulticarga(String estadoMulticarga) {
		this.estadoMulticarga = estadoMulticarga;
	}
	
	

}

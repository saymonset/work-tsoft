package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Table(name = "C_TIPO_OPERACION_MULTICARGA")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "com.indeval.portaldali.persistence.modelo.TipoOperacionMulticarga")
public class TipoOperacionMulticarga implements Serializable{
	
	/**
	 * Constante de serializacion por default 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * identificador del tipo de operaci&oacute;n multicarga
	 * */
	private Long idTipoOperacionMulticarga;
	
	/**
	 * descriocion del tipo de operaci&oacute;n multicarga
	 * */
	private String tipoOperacionMulticarga;
	
	/**
	 * geters and seters
	 * */
	
	@Id
	@Column(name = "ID_TIPO_OPERACION_MULTICARGA", unique = true, nullable = false)
	public Long getIdTipoOperacionMulticarga() {
		return idTipoOperacionMulticarga;
	}

	@Column(name ="TIPO_OPERACION_MULTICARGA", unique = false, nullable = false)
	public String getTipoOperacionMulticarga() {
		return tipoOperacionMulticarga;
	}
	
	
	public void setIdTipoOperacionMulticarga(Long idTipoOperacionMulticarga) {
		this.idTipoOperacionMulticarga = idTipoOperacionMulticarga;
	}

	public void setTipoOperacionMulticarga(String tipoOperacionMulticarga) {
		this.tipoOperacionMulticarga = tipoOperacionMulticarga;
	}
		
}

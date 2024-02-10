/**
 * 
 */
package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author lmunoz
 *
 */
@Entity
@Table(name = "C_MOI_CONTROL_DERECHOS")
public class Control implements Serializable{	
	private Long idMoiControlDerechos;	
	private String descripcion;
	private String estado;
	private Integer idControl;
	private Integer idEstado;
	
	
		/**
		 * @return the idControl
		 */
	  	@Column(name = "ID_ESTADO_CONTROL")
		public Integer getIdControl() {
			return idControl;
		}
		/**
		 * @param idControl the idControl to set
		 */
		public void setIdControl(Integer idControl) {
			this.idControl = idControl;
		}
		/**
		 * @return the idEstado
		 */
		@Column(name = "ID_ESTADO_DERECHO", insertable=false, updatable=false)
		public Integer getIdEstado() {
			return idEstado;
		}
		/**
		 * @param idEstado the idEstado to set
		 */
		public void setIdEstado(Integer idEstado) {
			this.idEstado = idEstado;
		}
	
	/**
	 * @return the idMoiControlDerechos
	 */
	@Id
	@Column(name = "ID_CONTROL")
	public Long getIdMoiControlDerechos() {
		return idMoiControlDerechos;
	}
	/**
	 * @param idMoiControlDerechos the idMoiControlDerechos to set
	 */
	public void setIdMoiControlDerechos(Long idMoiControlDerechos) {
		this.idMoiControlDerechos = idMoiControlDerechos;
	}
	/**
	 * @return the descripcion
	 */
	@Column(name = "DESCRIPCION")
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	/**
	 * @return the estado
	 */
	@Column(name = "ETIQUETA")
	public String getEstado() {
		return estado;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
}


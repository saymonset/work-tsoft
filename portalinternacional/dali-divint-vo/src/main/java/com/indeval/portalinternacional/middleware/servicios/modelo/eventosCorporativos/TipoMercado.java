/**
 * 
 */
package com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author lmunoz
 *
 */
@Entity
@Table(name = "C_Tipo_Mercado_EVCO")
public class TipoMercado { 
	private Long idTipoMercado;
	private String codigo;
	private String descripcion;
	/**
	 * @return the idTipoMercado
	 */
	@Id
	@Column(name = "ID_TIPO_MERCADO", unique = true, nullable = false)
	public Long getIdTipoMercado() {
		return idTipoMercado;
	}
	/**
	 * @param idTipoMercado the idTipoMercado to set
	 */
	public void setIdTipoMercado(Long idTipoMercado) {
		this.idTipoMercado = idTipoMercado;
	}
	/**
	 * @return the codigo
	 */
	@Column(name = "CODIGO", unique = true, nullable = false)
	public String getCodigo() {
		return codigo;
	}
	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	/**
	 * @return the descripcion
	 */
	@Column(name = "DESCRIPCION", unique = true, nullable = false)
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

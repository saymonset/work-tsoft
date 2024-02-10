package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Fernando Pineda
 *
 */
@Entity
@Table(name = "T_COMENTARIO_EFECTIVO")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_T_COMENTARIO_EFECTIVO", allocationSize = 1)
public class ComentarioEfectivoInt implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "ID_COMENTARIO_EFECTIVO")
	private Long idComentario;
	
	@Column(name = "ID_DETALLE_CONCILIACION")
	private Long idDetalle;
	
	@Column(name = "USUARIO")
	private String usuario;
	
	@Column(name = "COMENTARIO")
	private String comentario;
	
	@Column(name = "FECHA")
	private Date fecha;
	
	/**
	 * @return the idComentario
	 */
	public Long getIdComentario() {
		return idComentario;
	}
	/**
	 * @param idComentario the idComentario to set
	 */
	public void setIdComentario(Long idComentario) {
		this.idComentario = idComentario;
	}
	/**
	 * @return the idDetalle
	 */
	public Long getIdDetalle() {
		return idDetalle;
	}
	/**
	 * @param idDetalle the idDetalle to set
	 */
	public void setIdDetalle(Long idDetalle) {
		this.idDetalle = idDetalle;
	}
	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}
	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	/**
	 * @return the usuario
	 */
	public String getComentario() {
		return comentario;
	}
	/**
	 * @param usuario the usuario to set
	 */
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}
	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	

}
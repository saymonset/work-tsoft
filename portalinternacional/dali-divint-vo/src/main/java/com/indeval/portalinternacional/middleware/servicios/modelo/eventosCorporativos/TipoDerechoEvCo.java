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
 * @author lmunoz
 *
 */
@Entity
@Table(name = "C_TIPO_DERECHO_EVCO")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_C_TIPO_DERECHO_EVCO", allocationSize = 1, initialValue = 1)
public class TipoDerechoEvCo  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idTipoDerecho;
	private String tipoDerecho;
	private String	tipo;
	private Long inactivo;
	/**
	 * @return the idTipoDerecho
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "ID_TIPO_DERECHO", unique = true, nullable = false)
	public Long getIdTipoDerecho() {
		return idTipoDerecho;
	}
	/**
	 * @param idTipoDerecho the idTipoDerecho to set
	 */
	public void setIdTipoDerecho(Long idTipoDerecho) {
		this.idTipoDerecho = idTipoDerecho;
	}
	/**
	 * @return the tipoDerecho
	 */
	@Column(name = "TIPO_DERECHO", unique = false, nullable = false)
	public String getTipoDerecho() {
		return tipoDerecho;
	}
	/**
	 * @param tipoDerecho the tipoDerecho to set
	 */
	public void setTipoDerecho(String tipoDerecho) {
		this.tipoDerecho = tipoDerecho;
	}
	/**
	 * @return the tipo
	 */
	@Column(name = "TIPO", unique = false, nullable = false)
	public String getTipo() {
		return tipo;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}	
	/**
	 * @return the inactivo
	 */
	@Column(name="INACTIVO", unique=false, nullable=false)
	public Long getInactivo() {
		return inactivo;
	}
	/**
	 * @param inactivo the inactivo to set
	 */
	public void setInactivo(Long inactivo) {
		this.inactivo = inactivo;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TipoDerechoEvCo [idTipoDerecho=" + idTipoDerecho
				+ ", tipoDerecho=" + tipoDerecho + ", tipo=" + tipo
				+ ", inactivo=" + inactivo + "]";
	}

}

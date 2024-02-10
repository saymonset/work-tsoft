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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author kode-
 *
 */
@Entity
@Table(name="T_VALIDACION_EVCO")
@SequenceGenerator(name="foliador", sequenceName="SEQ_T_VALIDACION_EVCO", allocationSize=1, initialValue=1)
public class ValidacionesEvco implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idValidacion;
	private Long valor;
	private OperadorEvco operador;
	private Long idEventoCorporativo;
	private TipoValidacionEvco tipoValidacion;
	/**
	 * @return the idValidacion
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="foliador")
	@Column(name="ID_VALIDACION", unique=true, nullable=false)
	public Long getIdValidacion() {
		return idValidacion;
	}
	/**
	 * @param idValidacion the idValidacion to set
	 */
	public void setIdValidacion(Long idValidacion) {
		this.idValidacion = idValidacion;
	}
	/**
	 * @return the valor
	 */
	@Column(name="VALOR",unique=false, nullable=false)
	public Long getValor() {
		return valor;
	}
	/**
	 * @param valor the valor to set
	 */
	public void setValor(Long valor) {
		this.valor = valor;
	}
	
	/**
	 * @return the operador
	 */
	@OneToOne
	@JoinColumn(name="ID_OPERADOR",referencedColumnName="ID_OPERADOR",unique=false,nullable=true,insertable=true, updatable=true)
	public OperadorEvco getOperador() {
		return operador;
	}
	/**
	 * @param operador the operador to set
	 */
	public void setOperador(OperadorEvco operador) {
		this.operador = operador;
	}
	/**
	 * @return the idEventoCorporativo
	 */
	@Column(name="ID_EVENTO_CORPORATIVO", unique=false, nullable=false)
	public Long getIdEventoCorporativo() {
		return idEventoCorporativo;
	}
	/**
	 * @param idEventoCorporativo the idEventoCorporativo to set
	 */
	public void setIdEventoCorporativo(Long idEventoCorporativo) {
		this.idEventoCorporativo = idEventoCorporativo;
	}
	/**
	 * @return the tipoValidacion
	 */
	@OneToOne
	@JoinColumn(name="ID_TIPO_VALIDACION",referencedColumnName="ID_TIPO_VALIDACION", unique=false,nullable=false,insertable=true,updatable=true)
	public TipoValidacionEvco getTipoValidacion() {
		return tipoValidacion;
	}
	/**
	 * @param tipoValidacion the tipoValidacion to set
	 */
	public void setTipoValidacion(TipoValidacionEvco tipoValidacion) {
		this.tipoValidacion = tipoValidacion;
	}	
}

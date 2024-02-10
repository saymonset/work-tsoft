package com.indeval.portalinternacional.middleware.servicios.modelo.adp;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "R_ADP_CUSTODIO_PORCENTAJE")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_ADP_CUSTODIO_PORCENTAJE", allocationSize = 1, initialValue = 1)
@Proxy(lazy = false)
public class AdpCustodioPorcentaje implements Serializable{

	/**
	 * Constante de serialización por default
	 */
	private static final long serialVersionUID = 1L;

	private Long idAdpCustodioPor;
	private ClaveAdp claveAdp;
	private Long idCuentaNombrada;
	private Integer porcentaje;
	
	/**
	 * @return
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "ID_ADP_CUST_POR", unique = true, nullable = false)
	public Long getIdAdpCustodioPor() {
		return idAdpCustodioPor;
	}
	
	/**
	 * @param idAdpCustodioPor
	 */
	public void setIdAdpCustodioPor(Long idAdpCustodioPor) {
		this.idAdpCustodioPor = idAdpCustodioPor;
	}
	
	/**
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "ID_CLAVE_ADP", nullable = true)
	public ClaveAdp getClaveAdp() {
		return claveAdp;
	}
	
	/**
	 * @param claveAdp
	 */
	public void setClaveAdp(ClaveAdp claveAdp) {
		this.claveAdp = claveAdp;
	}
	
	/**
	 * @return
	 */
	@Column(name = "ID_CUENTA_NOMBRADA", unique = false, nullable = false)
	public Long getIdCuentaNombrada() {
		return idCuentaNombrada;
	}
	
	/**
	 * @param idCuentaNombrada
	 */
	public void setIdCuentaNombrada(Long idCuentaNombrada) {
		this.idCuentaNombrada = idCuentaNombrada;
	}
	
	/**
	 * @return
	 */
	@Column(name = "PORCENTAJE", unique = false, nullable = false)
	public Integer getPorcentaje() {
		return porcentaje;
	}
	
	/**
	 * @param porcentaje
	 */
	public void setPorcentaje(Integer porcentaje) {
		this.porcentaje = porcentaje;
	}

	@Override
	public String toString() {
		return "AdpCustodioPorcentaje [idAdpCustodioPor=" + idAdpCustodioPor + ", claveAdp=" + claveAdp
				+ ", idCuentaNombrada=" + idCuentaNombrada + ", porcentaje=" + porcentaje + "]";
	}
}
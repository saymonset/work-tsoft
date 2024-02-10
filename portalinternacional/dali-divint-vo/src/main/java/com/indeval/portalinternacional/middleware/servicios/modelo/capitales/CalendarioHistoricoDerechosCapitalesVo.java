package com.indeval.portalinternacional.middleware.servicios.modelo.capitales;

import java.io.Serializable;

public class CalendarioHistoricoDerechosCapitalesVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long idCalendario;
	
	/**
	 * 
	 */
	public CalendarioHistoricoDerechosCapitalesVo() {
		super();
	}
	/**
	 * @param id
	 * @param idCalendario
	 */
	public CalendarioHistoricoDerechosCapitalesVo(Long id, Long idCalendario) {
		super();
		this.id = id;
		this.idCalendario = idCalendario;
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the idCalendario
	 */
	public Long getIdCalendario() {
		return idCalendario;
	}
	/**
	 * @param idCalendario the idCalendario to set
	 */
	public void setIdCalendario(Long idCalendario) {
		this.idCalendario = idCalendario;
	}

}

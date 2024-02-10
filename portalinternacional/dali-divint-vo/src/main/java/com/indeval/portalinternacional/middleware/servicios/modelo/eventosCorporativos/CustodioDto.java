package com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos;


public class CustodioDto  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;//ID_CUSTODIO
	private String nombreCorto;//NOMBRE_CORTO
	private String descripcion;//DESCRIPCION
	private String codigoBanco;//CODIGO_BANCO
	private Integer participante;//PARTICIPANTE

	/**
	 * @return the id
	 */

	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the nombreCorto
	 */

	public String getNombreCorto() {
		return nombreCorto;
	}
	/**
	 * @param nombreCorto the nombreCorto to set
	 */
	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}
	/**
	 * @return the descripcion
	 */

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
	 * @return the codigoBanco
	 */

	public String getCodigoBanco() {
		return codigoBanco;
	}


	/**
	 * @param codigoBanco the codigoBanco to set
	 */
	public void setCodigoBanco(String codigoBanco) {
		this.codigoBanco = codigoBanco;
	}


	/**
	 * @return the participante
	 */

	public Integer getParticipante() {
		return participante;
	}


	/**
	 * @param participante the participante to set
	 */
	public void setParticipante(Integer participante) {
		this.participante = participante;
	}

}

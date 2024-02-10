package com.indeval.portalinternacional.presentation.advice;

import java.io.Serializable;

/**
 * Clase que guarda los datos a enviar en el mensaje para 
 * la bitacora de ingresos por consulta
 * 
 * @author Rafael Ibarra
 *
 */

public class BitacoraIngresosConsulta implements Serializable {

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Nombre de la institucion seleccionada por el usuario
	 */
	private String institucion;
	
	/**
	 * Ticket del usuario que realiza la consulta
	 */
	private String ticket;
	
	/**
	 * Nombre especifico de la consulta
	 */
	private String nombreConsulta;
	
	/**
	 * Numero de registros obtenidos de la consulta
	 */
	private long numeroRegistros;
	
	/**
	 * Indica si el registro es por consulta Total o solo de paginacion
	 */
	private boolean representaTotal;
	
	/**
	 * Indica la institucion primaria
	 */
	private String institucionPrimaria;
	
	/**
	 * Constructor por omision
	 */
	public BitacoraIngresosConsulta() {
		super();
		institucion = "";
		ticket = "";
		nombreConsulta = "";
		numeroRegistros = 0;
		representaTotal = false;
		institucionPrimaria = "";
	}

	/**
	 * @return the institucion
	 */
	public String getInstitucion() {
		return institucion;
	}

	/**
	 * @param institucion the institucion to set
	 */
	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

	/**
	 * @return the ticket
	 */
	public String getTicket() {
		return ticket;
	}

	/**
	 * @param ticket the ticket to set
	 */
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	/**
	 * @return the nombreConsulta
	 */
	public String getNombreConsulta() {
		return nombreConsulta;
	}

	/**
	 * @param nombreConsulta the nombreConsulta to set
	 */
	public void setNombreConsulta(String nombreConsulta) {
		this.nombreConsulta = nombreConsulta;
	}

	/**
	 * @return the numeroRegistros
	 */
	public long getNumeroRegistros() {
		return numeroRegistros;
	}

	/**
	 * @param numeroRegistros the numeroRegistros to set
	 */
	public void setNumeroRegistros(long numeroRegistros) {
		this.numeroRegistros = numeroRegistros;
	}

	/**
	 * @return the representaTotal
	 */
	public boolean isRepresentaTotal() {
		return representaTotal;
	}

	/**
	 * @param representaTotal the representaTotal to set
	 */
	public void setRepresentaTotal(boolean representaTotal) {
		this.representaTotal = representaTotal;
	}

	/**
	 * @return the institucionPrimaria
	 */
	public String getInstitucionPrimaria() {
		return institucionPrimaria;
	}

	/**
	 * @param institucionPrimaria the institucionPrimaria to set
	 */
	public void setInstitucionPrimaria(String institucionPrimaria) {
		this.institucionPrimaria = institucionPrimaria;
	}

	

}

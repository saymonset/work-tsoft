/**
 * 
 */
package com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos;

/**
 *    
 *
 */
public class ValidacionesEvcoDTO {

   
	private Long idValidacion;
	private Long valor;
	private String operador;
	private Long idEventoCorporativo;
	private String tipoValidacion;
	private String strValidacion;
	private String strOperador;
	private String strCantidadCapturada;
	private String strNumValidacion;
	private String idOperador;
	private boolean borradoLogico;
	private int posicionLista;
	
	public String getStrValidacion() {
		return strValidacion;
	}
	
	public void setStrValidacion(String strValidacion) {
		this.strValidacion = strValidacion;
	}
	
	public String getStrOperador() {
		return strOperador;
	}
	
	public void setStrOperador(String strOperador) {
		this.strOperador = strOperador;
	}
	
	public String getStrCantidadCapturada() {
		return strCantidadCapturada;
	}
	
	public void setStrCantidadCapturada(String strCantidadCapturada) {
		this.strCantidadCapturada = strCantidadCapturada;
	}
	
	public String getStrNumValidacion() {
		return strNumValidacion;
	}
	
	public void setStrNumValidacion(String strNumValidacion) {
		this.strNumValidacion = strNumValidacion;
	}

	public String getIdOperador() {
		return idOperador;
	}

	public void setIdOperador(String idOperador) {
		this.idOperador = idOperador;
	}
	
	public Long getValor() {
		return valor;
	}
	
	public void setValor(Long valor) {
		this.valor = valor;
	}
	
	public String getOperador() {
		return operador;
	}
	
	public void setOperador(String operador) {
		this.operador = operador;
	}
	
	public Long getIdEventoCorporativo() {
		return idEventoCorporativo;
	}
	
	public void setIdEventoCorporativo(Long idEventoCorporativo) {
		this.idEventoCorporativo = idEventoCorporativo;
	}
	
	public String getTipoValidacion() {
		return tipoValidacion;
	}
	
	public void setTipoValidacion(String tipoValidacion) {
		this.tipoValidacion = tipoValidacion;
	}
	
	public void setIdValidacion(Long idValidacion) {
		this.idValidacion = idValidacion;
	}

	public Long getIdValidacion() {
		return idValidacion;
	}

	public boolean isBorradoLogico() {
		return borradoLogico;
	}

	public void setBorradoLogico(boolean borradoLogico) {
		this.borradoLogico = borradoLogico;
	}

	public int getPosicionLista() {
		return posicionLista;
	}

	public void setPosicionLista(int posicionLista) {
		this.posicionLista = posicionLista;
	}
}

/**
 * 
 */
package com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos;

/**
 * @author kode-edhy
 *
 */
public class ValidacionesEvcoTDO {


	private Long idValidacion;
	private Long valor;
	private String operador;
	private Long idEventoCorporativo;
	private String tipoValidacion;
	private String strNumValidacion;
	private String strValidacion;
	private String strOperador;
	private boolean borradoLogico;
	private int posicionLista;
	/**
	 * @return the idValidacion
	 */
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
	public String getOperador() {
		return operador;
	}
	/**
	 * @param operador the operador to set
	 */
	public void setOperador(String operador) {
		this.operador = operador;
	}
	/**
	 * @return the idEventoCorporativo
	 */
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
	public String getTipoValidacion() {
		return tipoValidacion;
	}
	/**
	 * @param tipoValidacion the tipoValidacion to set
	 */
	public void setTipoValidacion(String tipoValidacion) {
		this.tipoValidacion = tipoValidacion;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ValidacionesEvcoTDO [idValidacion=" + idValidacion + ", valor="
				+ valor + ", operador=" + operador + ", idEventoCorporativo="
				+ idEventoCorporativo + ", tipoValidacion=" + tipoValidacion
				+ "]";
	}
	public String getStrNumValidacion() {
		return strNumValidacion;
	}
	public void setStrNumValidacion(String strNumValidacion) {
		this.strNumValidacion = strNumValidacion;
	}
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

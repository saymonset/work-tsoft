package com.indeval.portalinternacional.presentation.controller.eventosCorporativos;

import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.indeval.portalinternacional.middleware.servicios.modelo.Custodio;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.Estado;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.EventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.TipoDerechoEvCo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.TipoEvento;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.TipoMercado;

public class EventoCorporativoDeco {
	private List<SelectItem> estados;
	private EventoCorporativo evco;
	
	public EventoCorporativoDeco(EventoCorporativo evco){
		this.evco = evco;
	}

	/**
	 * @return the estados
	 */
	public List<SelectItem> getEstados() {
		return estados;
	}

	/**
	 * @param estados the estados to set
	 */
	public void setEstados(List<SelectItem> estados) {
		this.estados = estados;
	}
	
	/**
	 * @return the idEventoCorporativo
	 */
	
	public Long getIdEventoCorporativo() {
		return this.evco.getIdEventoCorporativo();
	}
	/**
	 * @param idEventoCorporativo the idEventoCorporativo to set
	 */
	public void setIdEventoCorporativo(Long idEventoCorporativo) {
		this.evco.setIdEventoCorporativo(idEventoCorporativo);
	}
	/**
	 * @return the fechaCreacion
	 */
	
	public Date getFechaCreacion() {
		return this.evco.getFechaCreacion();
	}
	/**
	 * @param fechaCreacion the fechaCreacion to set
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.evco.setFechaCreacion(fechaCreacion);
	}
	/**
	 * @return the fechaRegistro
	 */	
	public Date getFechaRegistro() {
		return this.evco.getFechaRegistro();
	}
	/**
	 * @param fechaRegistro the fechaRegistro to set
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.evco.setFechaRegistro(fechaRegistro);
	}
	/**
	 * @return the tipoValor
	 */	
	public String getTipoValor() {
		return this.evco.getTipoValor();
	}
	/**
	 * @param tipoValor the tipoValor to set
	 */
	public void setTipoValor(String tipoValor) {
		this.evco.setTipoValor(tipoValor);
	}
	/**
	 * @return the emisora
	 */
	
	public String getEmisora() {
		return this.evco.getEmisora();
	}
	/**
	 * @param emisora the emisora to set
	 */
	public void setEmisora(String emisora) {
		this.evco.setEmisora(emisora);
	}
	/**
	 * @return the serie
	 */
	
	public String getSerie() {
		return this.evco.getSerie();
	}
	/**
	 * @param serie the serie to set
	 */
	public void setSerie(String serie) {
		this.evco.setSerie(serie);
	}
	/**
	 * @return the isin
	 */
	
	public String getIsin() {
		return this.evco.getIsin();
	}
	/**
	 * @param isin the isin to set
	 */
	public void setIsin(String isin) {
		this.evco.setIsin(isin);
	}
	/**
	 * @return the tipoDerechoMO
	 */
	
	public TipoDerechoEvCo getTipoDerechoMO() {
		return this.evco.getTipoDerechoMO();
	}
	/**
	 * @param tipoDerechoMO the tipoDerechoMO to set
	 */
	public void setTipoDerechoMO(TipoDerechoEvCo tipoDerechoMO) {
		this.evco.setTipoDerechoMO(tipoDerechoMO);
	}
	/**
	 * @return the tipoDerechoML
	 */
	
	public TipoDerechoEvCo getTipoDerechoML() {
		return this.evco.getTipoDerechoML();
	}
	/**
	 * @param tipoDerechoML the tipoDerechoML to set
	 */
	public void setTipoDerechoML(TipoDerechoEvCo tipoDerechoML) {
		this.evco.setTipoDerechoML(tipoDerechoML);
	}
	/**
	 * @return the tipoMercado
	 */
	
	public TipoMercado getTipoMercado() {
		return this.evco.getTipoMercado();
	}
	/**
	 * @param tipoMercado the tipoMercado to set
	 */
	public void setTipoMercado(TipoMercado tipoMercado) {
		this.evco.setTipoMercado(tipoMercado);
	}
	/**
	 * @return the estado
	 */
	
	public Estado getEstado() {
		return this.evco.getEstado();
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(Estado estado) {
		this.evco.setEstado(estado); 
	}
	/**
	 * @return the fechaIndeval
	 */
	
	public Date getFechaIndeval() {
		return this.evco.getFechaIndeval();
	}
	/**
	 * @param fechaIndeval the fechaIndeval to set
	 */
	public void setFechaIndeval(Date fechaIndeval) {
		this.evco.setFechaIndeval(fechaIndeval);
	}
	/**
	 * @return the fechaCliente
	 */
	
	public Date getFechaCliente() {
		return this.evco.getFechaCliente();
	}
	/**
	 * @param fechaCliente the fechaCliente to set
	 */
	public void setFechaCliente(Date fechaCliente) {
		this.evco.setFechaCliente(fechaCliente);
	}
	/**
	 * @return the fechaPago
	 */
	
	public Date getFechaPago() {
		return this.evco.getFechaPago();
	}
	/**
	 * @param fechaPago the fechaPago to set
	 */
	public void setFechaPago(Date fechaPago) {
		this.evco.setFechaPago(fechaPago);
	}
	/**
	 * @return the prioridad
	 */
	
	public Integer getPrioridad() {
		return this.evco.getPrioridad();
	}
	/**
	 * @param prioridad the prioridad to set
	 */
	public void setPrioridad(Integer prioridad) {
		this.evco.setPrioridad(prioridad);
	}
	/**
	 * @return the tipoEvento
	 */	
	
	public TipoEvento getTipoEvento() {
		return this.evco.getTipoEvento();
	}
	/**
	 * @param tipoEvento the tipoEvento to set
	 */
	public void setTipoEvento(TipoEvento tipoEvento) {
		this.evco.setTipoEvento(tipoEvento);
	}
	/**
	 * @return the custodio
	 */
	
	public Custodio getCustodio() {
		return this.evco.getCustodio();
	}
	/**
	 * @param custodio the custodio to set
	 */
	public void setCustodio(Custodio custodio) {
		this.evco.setCustodio(custodio);
	}
	
	/**
	 * @return the fechaActualizacion
	 */
	
	public Date getFechaActualizacion() {
		return this.evco.getFechaActualizacion();
	}
	/**
	 * @param fechaActualizacion the fechaActualizacion to set
	 */
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.evco.setFechaActualizacion(fechaActualizacion);
	}
	/**
	 * @return the actualizado
	 */
	
	
	public Boolean getActualizado() {
		return this.evco.getActualizado();
	}
	/**
	 * @param actualizado the actualizado to set
	 */
	public void setActualizado(Boolean actualizado) {
		this.evco.setActualizado(actualizado);
	}
}

package com.indeval.portaldali.presentation.controller.consultaEmisiones;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;

import com.indeval.portaldali.presentation.controller.common.ControllerBase;
import com.indeval.portaldali.middleware.dto.EmisionDTO;
import com.indeval.portaldali.middleware.dto.EmisoraDTO;
import com.indeval.portaldali.middleware.dto.TipoValorDTO;
import com.indeval.portaldali.middleware.dto.SerieDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.middleware.services.common.ConsultaEmisionService;
import com.indeval.portaldali.persistence.dao.common.EmisionDaliDAO;

public class ConsultaEmisionesBean extends ControllerBase{
	private EmisionDTO emision = null;
	
	private List<EmisionDTO> listaEmisiones = null;
	
	private EmisionDaliDAO emisionDAO;
	
	private boolean consultaEjecutada;
	
	private Date fechaIni;
	private Date fechaFin;
	
	private ConsultaEmisionService consultaEmisionService;
	
	private EstadoPaginacionDTO estadoPaginacion = null;
	
	private int totalRegistros = 0;
	
	public String getInit()
	{
		if(emision == null)
			emision = new EmisionDTO();
		
		if(emision.getTipoValor() == null)
			emision.setTipoValor(new TipoValorDTO());
		
		if(emision.getEmisora() == null)
			emision.setEmisora(new EmisoraDTO());
		
		if(emision.getSerie() == null)
			emision.setSerie(new SerieDTO());
		
		if(!isConsultaEjecutada()){
			fechaIni = new Date();
			fechaFin = new Date();
		}
		
		return null;
	}
	
	public List<EmisionDTO> getResultadosEmisiones()
	{
		if(listaEmisiones == null)
			listaEmisiones = new ArrayList<EmisionDTO>();
		
		return listaEmisiones;
	}
	
	/**
	 * TODO Se restringe a solo emisiones de internacional
	 * @param evt
	 */
	public void buscarEmisiones(ActionEvent evt)
	{		
		if(estadoPaginacion == null)
			estadoPaginacion = new EstadoPaginacionDTO();
		
		estadoPaginacion.setNumeroPagina(1);
		
		ejecutarConsulta();
		
		setConsultaEjecutada(true);
	}
	
	public void limpiar(ActionEvent evt)
	{
		setConsultaEjecutada(false);
		
		emision.getTipoValor().setClaveTipoValor(StringUtils.EMPTY);
		emision.getEmisora().setDescripcion(StringUtils.EMPTY);
		emision.getSerie().setSerie(StringUtils.EMPTY);
		emision.setIsin(StringUtils.EMPTY);
		
		estadoPaginacion = new EstadoPaginacionDTO();
		
		fechaIni = new Date();
		fechaFin = new Date();
	}
	
	private void ejecutarConsulta()
	{
		if(estadoPaginacion.getRegistrosPorPagina() <= 0)
			estadoPaginacion.setRegistrosPorPagina(50);

		if(estadoPaginacion.getNumeroPagina() <= 0)
			estadoPaginacion.setNumeroPagina(1);
		
		listaEmisiones = consultaEmisionService.consultarEmisionesPorDescripciones(emision, estadoPaginacion);
	}
	
	public void generarReportes(ActionEvent evt)
	{		
		listaEmisiones = consultaEmisionService.consultarEmisionesPorDescripciones(emision, null);
		
		totalRegistros = listaEmisiones.size();
	}
	
	public String avanzarPagina()
	{
		estadoPaginacion.avanzar(1);
		
		ejecutarConsulta();
		
		return null;
	}
	
	public String irPrimeraPagina()
	{
		estadoPaginacion.irAlPrincipio();
		
		ejecutarConsulta();
		
		return null;
	}
	
	public String retrocederPagina()
	{
		estadoPaginacion.retrocederPagina();
		
		ejecutarConsulta();
	
		return null;
	}
	
	public String irUltimaPagina()
	{
		estadoPaginacion.irAlUltimo();
		
		ejecutarConsulta();
		
		return null;
	}
	
	public String avanzarPaginasRapido()
	{
		estadoPaginacion.avanzarPaginasRapido();
		
		ejecutarConsulta();
		
		return null;
	}
	
	public String retrocederPaginasRapido()
	{
		estadoPaginacion.retrocederPaginasRapido();
		
		ejecutarConsulta();
		
		return null;
	}
	
	public EmisionDTO getEmision() {
		return emision;
	}

	public void setEmision(EmisionDTO emision) {
		this.emision = emision;
	}

	public EmisionDaliDAO getEmisionDAO() {
		return emisionDAO;
	}

	public void setEmisionDAO(EmisionDaliDAO emisionDAO) {
		this.emisionDAO = emisionDAO;
	}

	public boolean isConsultaEjecutada() {
		return consultaEjecutada;
	}

	public void setConsultaEjecutada(boolean consultaEjecutada) {
		this.consultaEjecutada = consultaEjecutada;
	}

	/**
	 * @return the listaEmisiones
	 */
	public List<EmisionDTO> getListaEmisiones() {
		return listaEmisiones;
	}

	/**
	 * @param listaEmisiones the listaEmisiones to set
	 */
	public void setListaEmisiones(List<EmisionDTO> listaEmisiones) {
		this.listaEmisiones = listaEmisiones;
	}

	/**
	 * @return the consultaEmisionService
	 */
	public ConsultaEmisionService getConsultaEmisionService() {
		return consultaEmisionService;
	}

	/**
	 * @param consultaEmisionService the consultaEmisionService to set
	 */
	public void setConsultaEmisionService(
			ConsultaEmisionService consultaEmisionService) {
		this.consultaEmisionService = consultaEmisionService;
	}

	/**
	 * @return the estadoPaginacion
	 */
	public EstadoPaginacionDTO getEstadoPaginacion() {
		return estadoPaginacion;
	}

	/**
	 * @param estadoPaginacion the estadoPaginacion to set
	 */
	public void setEstadoPaginacion(EstadoPaginacionDTO estadoPaginacion) {
		this.estadoPaginacion = estadoPaginacion;
	}

	/**
	 * @return the fechaIni
	 */
	public Date getFechaIni() {
		return fechaIni;
	}

	/**
	 * @param fechaIni the fechaIni to set
	 */
	public void setFechaIni(Date fechaIni) {
		this.fechaIni = fechaIni;
	}

	/**
	 * @return the fechaFin
	 */
	public Date getFechaFin() {
		return fechaFin;
	}

	/**
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * @return the totalRegistros
	 */
	public int getTotalRegistros() {
		return totalRegistros;
	}

	/**
	 * @param totalRegistros the totalRegistros to set
	 */
	public void setTotalRegistros(int totalRegistros) {
		this.totalRegistros = totalRegistros;
	}
}
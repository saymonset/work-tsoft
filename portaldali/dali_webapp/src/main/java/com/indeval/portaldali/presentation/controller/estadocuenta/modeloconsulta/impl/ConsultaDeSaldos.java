/**
 * 2H Software SA de CV
 * 
 * Sistema de Consulta de Estado de Cuenta - Indeval
 * 
 */
package com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.impl;

import java.util.Date;
import java.util.List;


import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.CuentaEfectivoDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.SaldoEfectivoDTO;
import com.indeval.portaldali.middleware.services.estadocuenta.ConsultaSaldosEfectivoService;
import com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.ConsultaAbstract;

/**
 * Representa la implementación de la funcionalidad para obtener la consulta a
 * los saldos de efectivo a cierta fecha, esta clase sirve para consultar
 * saldos nombradas y controladas.
 * 
 * @author Sandra Flores Arrieta
 * @version 1.0
 *
 */
public class ConsultaDeSaldos extends ConsultaAbstract<SaldoEfectivoDTO> {
	
	/**
	 * Servicio para las consultas de saldos de efectivos de cuentas
	 */
	private ConsultaSaldosEfectivoService consultaSaldosEfectivoService;
	
	/**
	 * Criterio de bóveda seleccionada utilizada para consulta saldos
	 */
	private ConsultaBoveda criterioBoveda = null;

	/**
	 * Criterio de cuenta seleccionada utilizada para consulta saldos
	 */
	private ConsultaCuentaEfectivo criterioCuenta = null;

	/**
	 * El criterio de divisa seleccionada utilizada para la consulta de
	 * saldos
	 */
	private ConsultaDivisa criterioDivisa = null;
	
	/** El criterio de cuenta seleccionada utilizada para la consulta de saldos */
	private CuentaEfectivoDTO cuentaSeleccionada = null;
	
	/** El criterio de la fecha para la consulta de saldos */
	private Date criterioFecha = null;

	/**
	 * Contiene el valor de los criterios de bóveda utilizados para la consulta
	 * en pantalla
	 */
	private BovedaDTO bovedaSeleccionada = null;	
	
	/**
	 * Contiene el valor de los criterios de divisa utilizados para la consulta
	 * en pantalla
	 */
	private DivisaDTO divisaSeleccionada = null;	
	
	
	
	/**
	 * Nombre de la  columna que est actualmente ordenada 
	 */
	private String columnaOrdenada = null;
	
	/**
	 * Indica si la conslta se ordena de forma ascendente
	 */
	private boolean ordenAscendente = true;
	
	/**
	 * Indica si se debe logear la consulta de proyeccion para sacar el total de
	 * registros de la consulta. 
	 */
	private boolean debeLogearProyeccion = false;
	/**
	 * Inicializa el criterio de consulta de cuenta que tomará el criterio de divisa para
	 * realizar un filtrado únicamente por las divisas asociadas a las cuentas elegidas
	 */
	public void init(){
		criterioDivisa.setCriterioCuenta(getCriterioCuenta());
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.ConsultaAbstract#getPaginaDeResultados()
	 */
	@Override
	public List<SaldoEfectivoDTO> getPaginaDeResultados() {
		List<SaldoEfectivoDTO> res = this.consultaSaldosEfectivoService.consultarSaldosEfectivoBitacora(getOpcionSeleccionada(),getEstadoPaginacion(),getCriterioOrden()); 
		
		return res;
	}
	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.ConsultaAbstract#getResultados()
	 */
	@Override
	public List<SaldoEfectivoDTO> getResultados() {
		
		return this.consultaSaldosEfectivoService.consultarSaldosEfectivoBitacora(getOpcionSeleccionada(),null,getCriterioOrden());
		
		 
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#getOpcionSeleccionada()
	 */
	public SaldoEfectivoDTO getOpcionSeleccionada() {
		//Se declara el DTO que contendra los parametros de búsqueda
		SaldoEfectivoDTO criterio = new SaldoEfectivoDTO();		
		//Colocamos la fecha en la que se buscan los saldos
		criterio.setFecha(this.getCriterioFecha());		
		//Colocamos el criterios de boveda
		criterio.setBoveda(getBovedaSeleccionada());
		//Colocamos el criterio de cuenta
		criterio.setCuenta(getCuentaSeleccionada());
		//Colocamos el criterio de divisa
		criterio.setDivisa(getDivisaSeleccionada());		
		//Obtenemos los resultados de la consulta
		opcionSeleccionada = criterio;
		return opcionSeleccionada;
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#setOpcionSeleccionada(java.lang.Object)
	 */
	public void setOpcionSeleccionada(SaldoEfectivoDTO opcionSeleccionada) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Método para obtener el atributo bovedaSeleccionada
	 * 
	 * @return el atributo bovedaSeleccionada
	 */
	public BovedaDTO getBovedaSeleccionada() {
		if(bovedaSeleccionada==null){
			if(criterioBoveda.getOpcionSeleccionada().getId() > 0) {
				bovedaSeleccionada = criterioBoveda.getOpcionSeleccionada();
				criterioBoveda.setDebePaginar(false);
			} else {
				if(criterioBoveda.isTodos()) {
					bovedaSeleccionada = criterioBoveda.getOpcionSeleccionada();
					criterioBoveda.setDebePaginar(true);
				} else {
					List<BovedaDTO> bovedas = criterioBoveda.getPaginaDeResultados();
					if(bovedas != null && bovedas.size() > 0) {
						bovedaSeleccionada = bovedas.get(0);
						criterioBoveda.setDebePaginar(true);
					} else {
						bovedaSeleccionada = criterioBoveda.getOpcionSeleccionada();
					}
				}
			}
		}
		
		
		return bovedaSeleccionada;
	}
	
	/**
	 * Método para obtener el atributo divisaSeleccionada
	 * 
	 * @return el atributo bovedaSeleccionada
	 */
	public DivisaDTO getDivisaSeleccionada() {
		if(divisaSeleccionada==null){
			if(criterioDivisa.getOpcionSeleccionada().getId() > 0) {
				divisaSeleccionada = criterioDivisa.getOpcionSeleccionada();
				criterioDivisa.setDebePaginar(false);
			} else {
				if(criterioDivisa.isTodos()) {
					divisaSeleccionada = criterioDivisa.getOpcionSeleccionada();
					criterioDivisa.setDebePaginar(true);
				} else {
					List<DivisaDTO> divisas = criterioDivisa.getPaginaDeResultados();
					if(divisas != null && divisas.size() > 0) {
						divisaSeleccionada = divisas.get(0);
						criterioDivisa.setDebePaginar(true);
					} else {
						divisaSeleccionada = criterioDivisa.getOpcionSeleccionada();
					}
				}
			}
		}
		
		
		return divisaSeleccionada;
	}
	
	/**
	 * Método para obtener el atributo criterioBoveda
	 * 
	 * @return the criterioBoveda
	 */
	public ConsultaBoveda getCriterioBoveda() {
		return criterioBoveda;
	}

	/**
	 * Método para establecer el atributo criterioBoveda
	 * 
	 * @param criterioBoveda the criterioBoveda to set
	 */
	public void setCriterioBoveda(ConsultaBoveda criterioBoveda) {
		this.criterioBoveda = criterioBoveda;
	}

	/**
	 * Método para obtener el atributo criterioCuenta
	 * 
	 * @return the criterioCuenta
	 */
	public ConsultaCuentaEfectivo getCriterioCuenta() {
		return criterioCuenta;
	}

	/**
	 * Método para establecer el atributo criterioCuenta
	 * 
	 * @param criterioCuenta the criterioCuenta to set
	 */
	public void setCriterioCuenta(ConsultaCuentaEfectivo criterioCuenta) {
		this.criterioCuenta = criterioCuenta;
	}

	/**
	 * Método para obtener el atributo criterioDivisa
	 * 
	 * @return the criterioDivisa
	 */
	public ConsultaDivisa getCriterioDivisa() {
		return criterioDivisa;
	}

	/**
	 * Método para establecer el atributo criterioDivisa
	 * 
	 * @param criterioDivisa the criterioDivisa to set
	 */
	public void setCriterioDivisa(ConsultaDivisa criterioDivisa) {
		this.criterioDivisa = criterioDivisa;
	}

	/**
	 * Método para obtener el atributo criterioFecha
	 * 
	 * @return the criterioFecha
	 */
	public Date getCriterioFecha() {
		return criterioFecha;
	}

	/**
	 * Método para establecer el atributo criterioFecha
	 * 
	 * @param criterioFecha the criterioFecha to set
	 */
	public void setCriterioFecha(Date criterioFecha) {
		this.criterioFecha = criterioFecha;
	}

	/**
	 * @return the consultaSaldosEfectivoService
	 */
	public ConsultaSaldosEfectivoService getConsultaSaldosEfectivoService() {
		return consultaSaldosEfectivoService;
	}

	/**
	 * @param consultaSaldosEfectivoService the consultaSaldosEfectivoService to set
	 */
	public void setConsultaSaldosEfectivoService(
			ConsultaSaldosEfectivoService consultaSaldosEfectivoService) {
		this.consultaSaldosEfectivoService = consultaSaldosEfectivoService;
	}

	/**
	 * @return the columnaOrdenada
	 */
	public String getColumnaOrdenada() {
		return columnaOrdenada;
	}

	/**
	 * @param columnaOrdenada the columnaOrdenada to set
	 */
	public void setColumnaOrdenada(String columnaOrdenada) {
		this.columnaOrdenada = columnaOrdenada;
	}

	/**
	 * @return the ordenAscendente
	 */
	public boolean isOrdenAscendente() {
		return ordenAscendente;
	}

	/**
	 * @param ordenAscendente the ordenAscendente to set
	 */
	public void setOrdenAscendente(boolean ordenAscendente) {
		this.ordenAscendente = ordenAscendente;
	}

	/**
	 * @return the cuentaSeleccionada
	 */
	public CuentaEfectivoDTO getCuentaSeleccionada() {
		if(cuentaSeleccionada == null){
			if(!criterioCuenta.getOpcionSeleccionada().getNumeroCuenta().equals("-1")) {
				cuentaSeleccionada = criterioCuenta.getOpcionSeleccionada();
				criterioCuenta.setDebePaginar(false);
			} else {
				if(criterioCuenta.isTodos()) {
					cuentaSeleccionada = criterioCuenta.getOpcionSeleccionada();
					criterioCuenta.setDebePaginar(true);
				} else {
					List<CuentaEfectivoDTO> cuentas = criterioCuenta.getPaginaDeResultados();
					if(cuentas != null && cuentas.size() > 0) {
						cuentaSeleccionada = cuentas.get(0);
						criterioCuenta.setDebePaginar(true);
					} else {
						cuentaSeleccionada = criterioCuenta.getOpcionSeleccionada();
					}
				}
			}
		}
		
		
		return cuentaSeleccionada;
	}
	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.ConsultaAbstract#obtenerProyeccionConsulta()
	 */
	@Override
	public long obtenerProyeccionConsulta() {
		long resultado = 0;
		if(getCriterioFecha()!=null){
			resultado = consultaSaldosEfectivoService.
			obtenerProyeccionConsultaSaldosEfectivo(this.getOpcionSeleccionada(),debeLogearProyeccion);
		}
		return resultado; 
	}

	/**
	 * Asigna el valor del campo cuentaSeleccionada
	 * @param cuentaSeleccionada el valor de cuentaSeleccionada a asignar
	 */
	public void setCuentaSeleccionada(CuentaEfectivoDTO cuentaSeleccionada) {
		this.cuentaSeleccionada = cuentaSeleccionada;
	}

	/**
	 * Asigna el valor del campo bovedaSeleccionada
	 * @param bovedaSeleccionada el valor de bovedaSeleccionada a asignar
	 */
	public void setBovedaSeleccionada(BovedaDTO bovedaSeleccionada) {
		this.bovedaSeleccionada = bovedaSeleccionada;
	}

	/**
	 * Asigna el valor del campo divisaSeleccionada
	 * @param divisaSeleccionada el valor de divisaSeleccionada a asignar
	 */
	public void setDivisaSeleccionada(DivisaDTO divisaSeleccionada) {
		this.divisaSeleccionada = divisaSeleccionada;
	}
	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#recibirNotificacionResultados(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void recibirNotificacionResultados(List resultados){
		getCriterioBoveda().recibirNotificacionResultados
		(consultaSaldosEfectivoService.
				obtenerIdentificadoresValidosParaBoveda(getOpcionSeleccionada()));
		getCriterioDivisa().recibirNotificacionResultados(consultaSaldosEfectivoService.
				obtenerIdentificadoresValidosParaDivisa(getOpcionSeleccionada()));
		
		
		SaldoEfectivoDTO criterioParaCuenta = getOpcionSeleccionada();
		criterioParaCuenta.setCuenta(getCriterioCuenta().getOpcionSeleccionada()); 
		
		
		getCriterioCuenta().recibirNotificacionResultados(consultaSaldosEfectivoService.obtenerIdentificadoresValidosParaCuenta(criterioParaCuenta));
	}

	/**
	 * @return the debeLogearProyeccion
	 */
	public boolean isDebeLogearProyeccion() {
		return debeLogearProyeccion;
	}

	/**
	 * @param debeLogearProyeccion the debeLogearProyeccion to set
	 */
	public void setDebeLogearProyeccion(boolean debeLogearProyeccion) {
		this.debeLogearProyeccion = debeLogearProyeccion;
	}
}

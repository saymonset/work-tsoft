/**
 * 2H Software SA de CV
 * 
 * Sistema de Consulta de Estado de Cuenta - Indeval
 * 
 */
package com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.CuentaEfectivoDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.EstadoCuentaEfectivoPorDivisaDTO;
import com.indeval.portaldali.middleware.dto.SaldoEfectivoDTO;
import com.indeval.portaldali.middleware.services.common.constants.TipoCuentaConstants;
import com.indeval.portaldali.middleware.services.estadocuenta.ConsultaEstadoCuentaSaldoEfectivoService;
import com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.ConsultaAbstract;

/**
 * Representa la implementación de la funcionalidad para obtener la consulta al estado
 * de cuenta de saldos de efectivo en cierto rango de fechas, esta clase sirve para consultar
 * saldos nombrados y controlados.
 * 
 * @author Emigdio Hernández
 * @version 1.0
 *
 */
public class ConsultaEstadoCuentaDeSaldos extends ConsultaAbstract<EstadoCuentaEfectivoPorDivisaDTO> {
	
	/**
	 * Servicio de negocio para el acceso a la consulta del estado de cuenta de efectivo
	 */
	private ConsultaEstadoCuentaSaldoEfectivoService consultaEstadoCuentaEfectivoService = null;
	
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
	
	/** El criterio de la fecha inicial para la consulta de estado de cuenta */
	private Date criterioFechaInicial = null;
	
	/** El criterio de la fecha final para la consulta de estado de cuenta */
	private Date criterioFechaFinal = null;

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
	 * Indica si la consulta de emisiones debe o no dejar bitacora
	 */
	private boolean debeDejarBitacora = false;
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.ConsultaAbstract#getPaginaDeResultados()
	 */
	@Override
	public List<EstadoCuentaEfectivoPorDivisaDTO> getPaginaDeResultados() {
		SaldoEfectivoDTO criterio = new SaldoEfectivoDTO();
		criterio.setFechaFinal(this.getCriterioFechaFinal());
		criterio.setFechaInicial(this.getCriterioFechaInicial());
		criterio.setCuenta(getCuentaSeleccionada());
		criterio.setDivisa(getCriterioDivisa().getOpcionSeleccionada());
		criterio.setBoveda(getCriterioBoveda().getOpcionSeleccionada());
		List<Long> divisas = new ArrayList<Long>();
		for(DivisaDTO divisa : getCriterioDivisa().getPaginaDeResultados()){
			divisas.add(divisa.getId());
		}
		List<EstadoCuentaEfectivoPorDivisaDTO> resultados = null;
		
		
		if( getCriterioCuenta().getCriterioTipoCuenta().
				getOpcionSeleccionada().getId().equals(TipoCuentaConstants.TIPO_CONTROLADA) ){
			resultados	= consultaEstadoCuentaEfectivoService.consultarEstadoDeCuentaSaldosControlados(criterio, divisas);
		}else{
			resultados	= consultaEstadoCuentaEfectivoService.consultarEstadoDeCuentaSaldosNombrada(criterio, divisas);
		}
		
		
		return resultados;
		
		
	}
	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.ConsultaAbstract#getResultados()
	 */
	@Override
	public List<EstadoCuentaEfectivoPorDivisaDTO> getResultados() {
		
		SaldoEfectivoDTO criterio = new SaldoEfectivoDTO();
		criterio.setFechaFinal(this.getCriterioFechaFinal());
		criterio.setFechaInicial(this.getCriterioFechaInicial());
		criterio.setCuenta(getCuentaSeleccionada());
		criterio.setDivisa(getCriterioDivisa().getOpcionSeleccionada());
		criterio.setBoveda(getCriterioBoveda().getOpcionSeleccionada());
		List<Long> divisas = new ArrayList<Long>();
		List<EstadoCuentaEfectivoPorDivisaDTO> resultados = null;
		
		if( getCriterioCuenta().getCriterioTipoCuenta().
				getOpcionSeleccionada().getId().equals(TipoCuentaConstants.TIPO_CONTROLADA) ){
			divisas = consultaEstadoCuentaEfectivoService.buscarDivisasDeSaldosControlados(criterio,debeDejarBitacora);
			resultados	= consultaEstadoCuentaEfectivoService.consultarEstadoDeCuentaSaldosControlados(criterio, divisas);
		}else{
			divisas = consultaEstadoCuentaEfectivoService.buscarDivisasDeSaldosNombrados(criterio,debeDejarBitacora);
			resultados	= consultaEstadoCuentaEfectivoService.consultarEstadoDeCuentaSaldosNombrada(criterio, divisas);
		}
		
		return resultados;
	}
	/**
	 * Obtiene el criterio de consulta actualmente usado para los resultados
	 * @return Criterio utilizado
	 */
	public SaldoEfectivoDTO getCriterioConsulta(){
		SaldoEfectivoDTO criterio = new SaldoEfectivoDTO();
		criterio.setFechaFinal(this.getCriterioFechaFinal());
		criterio.setFechaInicial(this.getCriterioFechaInicial());
		criterio.setCuenta(getCuentaSeleccionada());
		criterio.setDivisa(getCriterioDivisa().getOpcionSeleccionada());
		criterio.setBoveda(getCriterioBoveda().getOpcionSeleccionada());
		return criterio;
	}
	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#getOpcionSeleccionada()
	 */
	public EstadoCuentaEfectivoPorDivisaDTO getOpcionSeleccionada() {
		
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#setOpcionSeleccionada(java.lang.Object)
	 */
	public void setOpcionSeleccionada(EstadoCuentaEfectivoPorDivisaDTO opcionSeleccionada) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * método para obtener el atributo bovedaSeleccionada
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
	 * método para obtener el atributo divisaSeleccionada
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
	 * método para obtener el atributo criterioBoveda
	 * 
	 * @return the criterioBoveda
	 */
	public ConsultaBoveda getCriterioBoveda() {
		return criterioBoveda;
	}

	/**
	 * método para establecer el atributo criterioBoveda
	 * 
	 * @param criterioBoveda the criterioBoveda to set
	 */
	public void setCriterioBoveda(ConsultaBoveda criterioBoveda) {
		this.criterioBoveda = criterioBoveda;
	}

	/**
	 * método para obtener el atributo criterioCuenta
	 * 
	 * @return the criterioCuenta
	 */
	public ConsultaCuentaEfectivo getCriterioCuenta() {
		return criterioCuenta;
	}

	/**
	 * método para establecer el atributo criterioCuenta
	 * 
	 * @param criterioCuenta the criterioCuenta to set
	 */
	public void setCriterioCuenta(ConsultaCuentaEfectivo criterioCuenta) {
		this.criterioCuenta = criterioCuenta;
	}

	/**
	 * método para obtener el atributo criterioDivisa
	 * 
	 * @return the criterioDivisa
	 */
	public ConsultaDivisa getCriterioDivisa() {
		return criterioDivisa;
	}

	/**
	 * método para establecer el atributo criterioDivisa
	 * 
	 * @param criterioDivisa the criterioDivisa to set
	 */
	public void setCriterioDivisa(ConsultaDivisa criterioDivisa) {
		this.criterioDivisa = criterioDivisa;
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
		/*if(getCriterioFecha()!=null && getCriterioFecha().length()>0){
			resultado = consultaSaldosEfectivoService.
			obtenerProyeccionConsultaSaldosEfectivo(this.getOpcionSeleccionada());
			
		}*/
		return resultado; 
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#recibirNotificacionResultados(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void recibirNotificacionResultados(List resultados){
		SaldoEfectivoDTO criterio = new SaldoEfectivoDTO();
		criterio.setFechaFinal(this.getCriterioFechaFinal());
		criterio.setFechaInicial(this.getCriterioFechaInicial());
		criterio.setCuenta(getCuentaSeleccionada());
		criterio.setDivisa(getCriterioDivisa().getOpcionSeleccionada());
		criterio.setBoveda(getCriterioBoveda().getOpcionSeleccionada());
		
		if( getCriterioCuenta().getCriterioTipoCuenta().
				getOpcionSeleccionada().getId().equals(TipoCuentaConstants.TIPO_CONTROLADA) ){
			getCriterioDivisa().recibirNotificacionResultados(
					consultaEstadoCuentaEfectivoService.buscarDivisasDeSaldosControlados(criterio,debeDejarBitacora));
			
		}else{
			getCriterioDivisa().recibirNotificacionResultados(
					consultaEstadoCuentaEfectivoService.buscarDivisasDeSaldosNombrados(criterio,debeDejarBitacora));
			
		}
		
		criterio.setCuenta(getCriterioCuenta().getOpcionSeleccionada());
		getCriterioCuenta().recibirNotificacionResultados(consultaEstadoCuentaEfectivoService.buscarCuentasDeEfectivo(criterio));
				
		
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

	/**
	 * Obtiene el campo criterioFechaInicial
	 * @return  criterioFechaInicial
	 */
	public Date getCriterioFechaInicial() {
		return criterioFechaInicial;
	}

	/**
	 * Asigna el valor del campo criterioFechaInicial
	 * @param criterioFechaInicial el valor de criterioFechaInicial a asignar
	 */
	public void setCriterioFechaInicial(Date criterioFechaInicial) {
		this.criterioFechaInicial = criterioFechaInicial;
	}

	/**
	 * Obtiene el campo criterioFechaFinal
	 * @return  criterioFechaFinal
	 */
	public Date getCriterioFechaFinal() {
		return criterioFechaFinal;
	}

	/**
	 * Asigna el valor del campo criterioFechaFinal
	 * @param criterioFechaFinal el valor de criterioFechaFinal a asignar
	 */
	public void setCriterioFechaFinal(Date criterioFechaFinal) {
		this.criterioFechaFinal = criterioFechaFinal;
	}
	/**
	 * Obtiene el campo consultaEstadoCuentaEfectivoService
	 * @return  consultaEstadoCuentaEfectivoService
	 */
	public ConsultaEstadoCuentaSaldoEfectivoService getConsultaEstadoCuentaEfectivoService() {
		return consultaEstadoCuentaEfectivoService;
	}
	/**
	 * Asigna el valor del campo consultaEstadoCuentaEfectivoService
	 * @param consultaEstadoCuentaEfectivoService el valor de consultaEstadoCuentaEfectivoService a asignar
	 */
	public void setConsultaEstadoCuentaEfectivoService(
			ConsultaEstadoCuentaSaldoEfectivoService consultaEstadoCuentaEfectivoService) {
		this.consultaEstadoCuentaEfectivoService = consultaEstadoCuentaEfectivoService;
	}
	/**
	 * @return the debeDejarBitacora
	 */
	public boolean isDebeDejarBitacora() {
		return debeDejarBitacora;
	}
	/**
	 * @param debeDejarBitacora the debeDejarBitacora to set
	 */
	public void setDebeDejarBitacora(boolean debeDejarBitacora) {
		this.debeDejarBitacora = debeDejarBitacora;
	}
}

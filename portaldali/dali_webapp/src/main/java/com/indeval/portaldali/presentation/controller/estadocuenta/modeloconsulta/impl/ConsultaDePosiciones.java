/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 */
package com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.impl;

import java.util.Date;
import java.util.List;

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.EmisionDTO;
import com.indeval.portaldali.middleware.dto.PosicionDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.middleware.services.estadocuenta.ConsultaPosicionService;
import com.indeval.portaldali.persistencia.posicion.Posicion;
import com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.ConsultaAbstract;

/**
 * Representa la implementación de la funcionalidad para obtener la consulta a
 * los valores de posiciones a cierta fecha, esta clase sirve para consultar
 * posiciones nombradas y controladas.
 * 
 * @author Emigdio Hernández
 * @version 1.0
 */
public class ConsultaDePosiciones extends ConsultaAbstract<PosicionDTO> {
	
	/**
	 * Criterio de bóveda seleccionada utilizada para consulta posiciones
	 */
	private ConsultaBoveda criterioBoveda = null;

	/**
	 * Criterio de bóveda seleccionada utilizada para consulta posiciones
	 */
	private ConsultaCuenta criterioCuenta = null;

	/**
	 * El criterio de emisión seleccionada utilizada para la consulta de
	 * posiciones
	 */
	private ConsultaEmision criterioEmision = null;

	/** El criterio de la fecha para la consulta de posiciones */
	private Date criterioFecha = null;
	
	/**
	 * Contiene el valor de los criterios de bóveda utilizados para la consulta
	 * en pantalla
	 */
	private BovedaDTO bovedaSeleccionada = null;

	/**
	 * Contiene el valor de los criterios de cuenta utilizados para la consulta
	 * en pantalla
	 */
	private CuentaDTO cuentaSeleccionada = null;

	/**
	 * Contiene el valor de los criterios de emisión utilizados para la consulta
	 * en pantalla
	 */
	private EmisionDTO emisionSeleccionada = null;
	
	/**
	 * consultar cuentas con no disponible mayor a 0
	 */
	private boolean noDisponible = false;
	
	/**
	 * Indica si hará un traspaso
	 */
	private boolean traspasarValores = false;
	
	/**
	 * Indica si se debe logear la consulta de proyeccion para sacar el total de
	 * registros de la consulta. 
	 */
	private boolean debeLogearProyeccion = false;
		
	/**
	 * Servicio de negocio para la consulta de posición de valores
	 */
	private ConsultaPosicionService consultaPosicionService = null;

	/**
	 * Obtiene el campo criterioBoveda
	 * 
	 * @return criterioBoveda
	 */
	public ConsultaBoveda getCriterioBoveda() {
		return criterioBoveda;
	}

	/**
	 * Asigna el valor del campo criterioBoveda
	 * 
	 * @param criterioBoveda
	 *            el valor de criterioBoveda a asignar
	 */
	public void setCriterioBoveda(ConsultaBoveda criterioBoveda) {
		this.criterioBoveda = criterioBoveda;
	}

	/**
	 * Obtiene el campo criterioCuenta
	 * 
	 * @return criterioCuenta
	 */
	public ConsultaCuenta getCriterioCuenta() {
		return criterioCuenta;
	}

	/**
	 * Asigna el valor del campo criterioCuenta
	 * 
	 * @param criterioCuenta
	 *            el valor de criterioCuenta a asignar
	 */
	public void setCriterioCuenta(ConsultaCuenta criterioCuenta) {
		this.criterioCuenta = criterioCuenta;
	}

	public PosicionDTO getOpcionSeleccionada() {
		PosicionDTO criterio = new PosicionDTO();
		criterio.setFechaFinal(this.getCriterioFecha());
		criterio.setPosicion("");
		criterio.setBoveda(getBovedaSeleccionada());
		criterio.setCuenta(getCuentaSeleccionada());
		criterio.setEmision(getEmisionSeleccionada());
		criterio.setNoDisponible(isNoDisponible());
		return criterio;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.ConsultaAbstract#getPaginaDeResultados()
	 */
	@Override
	public List<PosicionDTO> getPaginaDeResultados() {
//		PosicionDTO criterio = getOpcionSeleccionada();
//		return  consultaPosicionService.consultarPosiciones(criterio,this.getEstadoPaginacion(), getCriterioOrden());
		return null;
	}

	/**
	 * Realiza la consulta de posicion.
	 * @param paginar Indica si debe paginar.
	 * @return Lista con los resultados de la consulta.
	 */
	public List<Posicion> consultarPosicion(boolean paginar) {
		EstadoPaginacionDTO paginacion = paginar ? getEstadoPaginacion() : null;
		return consultaPosicionService.consultarPosicion(getOpcionSeleccionada(), paginacion, getCriterioOrden());
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.ConsultaAbstract#getResultados()
	 */
	@Override
	public List<PosicionDTO> getResultados() {
//		PosicionDTO criterio = this.getOpcionSeleccionada();
//		return  consultaPosicionService.consultarPosiciones(criterio,null, getCriterioOrden());
		return null;
	}
	
	/**
	 * Obtiene el campo criterioFecha
	 * 
	 * @return criterioFecha
	 */
	public Date getCriterioFecha() {
		return criterioFecha;
	}

	/**
	 * Asigna el valor del campo criterioFecha
	 * 
	 * @param criterioFecha
	 *            el valor de criterioFecha a asignar
	 */
	public void setCriterioFecha(Date criterioFecha) {
		this.criterioFecha = criterioFecha;
	}

	public void setOpcionSeleccionada(PosicionDTO opcionSeleccionada) {
		

	}

	/**
	 * Obtiene el campo criterioEmision
	 * 
	 * @return criterioEmision
	 */
	public ConsultaEmision getCriterioEmision() {
		return criterioEmision;
	}

	/**
	 * Asigna el valor del campo criterioEmision
	 * 
	 * @param crierioEmision
	 *            el valor de criterioEmision a asignar
	 */
	public void setCriterioEmision(ConsultaEmision criterioEmision) {
		this.criterioEmision = criterioEmision;
	}

	/**
	 * Obtiene el campo consultaPosicionService
	 * 
	 * @return consultaPosicionService
	 */
	public ConsultaPosicionService getConsultaPosicionService() {
		return consultaPosicionService;
	}

	/**
	 * Asigna el valor del campo consultaPosicionService
	 * 
	 * @param consultaPosicionService
	 *            el valor de consultaPosicionService a asignar
	 */
	public void setConsultaPosicionService(
			ConsultaPosicionService consultaPosicionService) {
		this.consultaPosicionService = consultaPosicionService;
	}
	
	/**
	 * método para obtener el atributo bovedaSeleccionada
	 * 
	 * @return el atributo bovedaSeleccionada
	 */
	public BovedaDTO getBovedaSeleccionada() {
		if(bovedaSeleccionada == null){
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
	 * Establece el valor para el atributo bovedaSeleccionada
	 * 
	 * @param bovedaSeleccionada
	 *            el valor del atributo bovedaSeleccionada a establecer
	 */
	public void setBovedaSeleccionada(BovedaDTO bovedaSeleccionada) {
		this.bovedaSeleccionada = bovedaSeleccionada;
	}

	/**
	 * método para obtener el atributo cuentaSeleccionada
	 * 
	 * @return el atributo cuentaSeleccionada
	 */
	public CuentaDTO getCuentaSeleccionada() {
		if(cuentaSeleccionada == null){
			if(!criterioCuenta.getOpcionSeleccionada().getNumeroCuenta().equals("-1")) {
				cuentaSeleccionada = criterioCuenta.getOpcionSeleccionada();
				criterioCuenta.setDebePaginar(false);
			} else {
				if(criterioCuenta.isTodos()) {
					cuentaSeleccionada = criterioCuenta.getOpcionSeleccionada();
					criterioCuenta.setDebePaginar(true);
				} else {
					List<CuentaDTO> cuentas = criterioCuenta.getPaginaDeResultados();
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

	/**
	 * Establece el valor para el atributo cuentaSeleccionada
	 * 
	 * @param cuentaSeleccionada
	 *            el valor del atributo cuentaSeleccionada a establecer
	 */
	public void setCuentaSeleccionada(CuentaDTO cuentaSeleccionada) {
		this.cuentaSeleccionada = cuentaSeleccionada;
	}

	/**
	 * método para obtener el atributo emisionSeleccionada
	 * 
	 * @return el atributo emisionSeleccionada
	 */
	public EmisionDTO getEmisionSeleccionada() {
		if(emisionSeleccionada == null){
			if(criterioEmision.getOpcionSeleccionada().getId() > 0) {
				emisionSeleccionada = criterioEmision.getOpcionSeleccionada();
				criterioEmision.setDebePaginar(false);
			} else {
				if(criterioEmision.isTodos()) {
					emisionSeleccionada = criterioEmision.getOpcionSeleccionada();
					criterioEmision.setDebePaginar(true);
				} else {
					List<EmisionDTO> criterios = criterioEmision.getPaginaDeResultados();
					if(criterios != null && criterios.size() > 0) {
						emisionSeleccionada = criterios.get(0);
						criterioEmision.setDebePaginar(true);
					} else {
						emisionSeleccionada = criterioEmision.getOpcionSeleccionada();
					}
				}
			}
		}
		return emisionSeleccionada;
	}

	/**
	 * Establece el valor para el atributo emisionSeleccionada
	 * 
	 * @param emisionSeleccionada
	 *            el valor del atributo emisionSeleccionada a establecer
	 */
	public void setEmisionSeleccionada(EmisionDTO emisionSeleccionada) {
		this.emisionSeleccionada = emisionSeleccionada;
	}
	
	public boolean isNoDisponible() {
		return noDisponible;
	}

	public void setNoDisponible(boolean noDisponible) {
		this.noDisponible = noDisponible;
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.ConsultaAbstract#obtenerProyeccionConsulta()
	 */
	@Override
	public long obtenerProyeccionConsulta() {
		long resultado = 0;
		if(getCriterioFecha() !=null){
			resultado = consultaPosicionService.obtenerProyeccionDeConsultaDePosiciones(this.getOpcionSeleccionada(),debeLogearProyeccion);
		}
		return resultado; 
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#recibirNotificacionResultados(java.util.List)
	 */
	@Override
	public void recibirNotificacionResultados(List resultados) {
		getCriterioBoveda().recibirNotificacionResultados
		(consultaPosicionService.
				obtenerIdentificadoresValidosParaBoveda(getOpcionSeleccionada()));
		getCriterioEmision().recibirNotificacionResultados(consultaPosicionService.
				obtenerIdentificadoresValidosParaEmision(getOpcionSeleccionada()));
		
		PosicionDTO criterioParaCuentas = getOpcionSeleccionada();
		criterioParaCuentas.setCuenta(getCriterioCuenta().getOpcionSeleccionada());
		
		getCriterioCuenta().recibirNotificacionResultados(consultaPosicionService.obtenerIdentificadoresValidosParaCuenta(criterioParaCuentas));
	}

	public void inicializaPaginacion() {
		estadoPaginacion = new EstadoPaginacionDTO();
		estadoPaginacion.setNumeroPagina(0);
		estadoPaginacion.setRegistrosPorPagina(resultadosPorPagina);
		estadoPaginacion.setTotalPaginas(0);
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

	public boolean isTraspasarValores() {
		return traspasarValores;
	}

	public void setTraspasarValores(boolean traspasarValores) {
		this.traspasarValores = traspasarValores;
	}
}

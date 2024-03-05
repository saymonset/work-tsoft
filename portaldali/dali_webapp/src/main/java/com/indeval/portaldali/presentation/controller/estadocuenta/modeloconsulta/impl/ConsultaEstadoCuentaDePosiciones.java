/**
 * 2H Software SA de CV
 * 
 * Sistema de Consulta de Estado de Cuenta - Indeval
 *
 * Fecha de creación: Dec 19, 2007
 */
package com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.EmisionDTO;
import com.indeval.portaldali.middleware.dto.EstadoCuentaPosicionPorEmisionDTO;
import com.indeval.portaldali.middleware.dto.PosicionDTO;
import com.indeval.portaldali.middleware.services.common.constants.TipoCuentaConstants;
import com.indeval.portaldali.middleware.services.estadocuenta.ConsultaEstadoCuentaPosicionService;
import com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.ConsultaAbstract;

/**
 * Representa la implementación de la funcionalidad para obtener la consulta los
 * movimientos realizados en un lapso de tiempo en una o varias posiciones de
 * valores nombradas y controladas.
 * 
 * @author Sandra Flores Arrieta
 * @version 1.0
 * 
 */
public class ConsultaEstadoCuentaDePosiciones extends ConsultaAbstract<EstadoCuentaPosicionPorEmisionDTO> {

	/** El criterio de la fecha final para la consulta de estado de cuenta */
	private Date criterioFechaFinal = null;
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
	 * Criterio de bóveda seleccionada utilizada para la consulta de movimientos
	 */
	private ConsultaBoveda criterioBoveda = null;

	/**
	 * Criterio de bóveda seleccionada utilizada para la consulta de movimientos
	 */
	private ConsultaCuenta criterioCuenta = null;

	/**
	 * El criterio de emisión seleccionada utilizada para la consulta de
	 * movimientos
	 */
	private ConsultaEmision criterioEmision = null;

	/** El criterio de la fecha inicial para la consulta de movimientos */
	private Date criterioFechaInicial = null;

	/**
	 * Servicio de negocio para la consulta del estado de cuenta de movimeintos
	 * de posiones de valores
	 */
	private ConsultaEstadoCuentaPosicionService consultaEstadoCuentaPosicionService = null;

	/**
	 * Indica si la consulta de emisiones debe o no dejar bitacora
	 */
	private boolean debeDejarBitacora = false;

	/**
	 * Obtiene el valor del atributo criterioBoveda
	 * 
	 * @return el valor del atributo criterioBoveda
	 */
	public ConsultaBoveda getCriterioBoveda() {
		return criterioBoveda;
	}

	/**
	 * Establece el valor del atributo criterioBoveda
	 * 
	 * @param criterioBoveda
	 *            el valor del atributo criterioBoveda a establecer.
	 */
	public void setCriterioBoveda(ConsultaBoveda criterioBoveda) {
		this.criterioBoveda = criterioBoveda;
	}

	/**
	 * Obtiene el valor del atributo criterioCuenta
	 * 
	 * @return el valor del atributo criterioCuenta
	 */
	public ConsultaCuenta getCriterioCuenta() {
		return criterioCuenta;
	}

	/**
	 * Establece el valor del atributo criterioCuenta
	 * 
	 * @param criterioCuenta
	 *            el valor del atributo criterioCuenta a establecer.
	 */
	public void setCriterioCuenta(ConsultaCuenta criterioCuenta) {
		this.criterioCuenta = criterioCuenta;
	}

	/**
	 * Obtiene el valor del atributo criterioEmision
	 * 
	 * @return el valor del atributo criterioEmision
	 */
	public ConsultaEmision getCriterioEmision() {
		return criterioEmision;
	}

	/**
	 * Establece el valor del atributo criterioEmision
	 * 
	 * @param criterioEmision
	 *            el valor del atributo criterioEmision a establecer.
	 */
	public void setCriterioEmision(ConsultaEmision criterioEmision) {
		this.criterioEmision = criterioEmision;
	}

	/**
	 * Obtiene el valor del atributo criterioFechaInicial
	 * 
	 * @return el valor del atributo criterioFechaInicial
	 */
	public Date getCriterioFechaInicial() {
		return criterioFechaInicial;
	}

	/**
	 * Establece el valor del atributo criterioFechaInicial
	 * 
	 * @param criterioFechaInicial
	 *            el valor del atributo criterioFechaInicial a establecer.
	 */
	public void setCriterioFechaInicial(Date criterioFechaInicial) {
		this.criterioFechaInicial = criterioFechaInicial;
	}

	/**
	 * Obtiene el valor del atributo consultaEstadoCuentaPosicionService
	 * 
	 * @return el valor del atributo consultaEstadoCuentaPosicionService
	 */
	public ConsultaEstadoCuentaPosicionService getConsultaEstadoCuentaPosicionService() {
		return consultaEstadoCuentaPosicionService;
	}

	/**
	 * Establece el valor del atributo consultaEstadoCuentaPosicionService
	 * 
	 * @param consultaEstadoCuentaPosicionService
	 *            el valor del atributo consultaEstadoCuentaPosicionService a
	 *            establecer.
	 */
	public void setConsultaEstadoCuentaPosicionService(ConsultaEstadoCuentaPosicionService consultaEstadoCuentaPosicionService) {
		this.consultaEstadoCuentaPosicionService = consultaEstadoCuentaPosicionService;
	}

	public EstadoCuentaPosicionPorEmisionDTO getOpcionSeleccionada() {
		getBovedaSeleccionada();
		getCuentaSeleccionada();
		getEmisionSeleccionada();
		return null;
	}

	/**
	 * Obtiene los criterios actualmente usados para la consulta
	 */

	public PosicionDTO getCriterioConsulta() {
		PosicionDTO posicion = new PosicionDTO();
		posicion.setFechaFinal(this.getCriterioFechaFinal());
		posicion.setFechaInicio(this.getCriterioFechaInicial());
		posicion.setCuenta(getCuentaSeleccionada());
		posicion.setEmision(getCriterioEmision().getOpcionSeleccionada());
		posicion.setBoveda(getCriterioBoveda().getOpcionSeleccionada());
		return posicion;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.ConsultaAbstract#getResultados()
	 */
	@Override
	public List<EstadoCuentaPosicionPorEmisionDTO> getResultados() {

		PosicionDTO posicion = new PosicionDTO();
		posicion.setFechaFinal(this.getCriterioFechaFinal());
		posicion.setFechaInicio(this.getCriterioFechaInicial());
		posicion.setCuenta(getCriterioCuenta().getOpcionSeleccionada());
		posicion.setEmision(getCriterioEmision().getOpcionSeleccionada());
		posicion.setBoveda(getCriterioBoveda().getOpcionSeleccionada());

		List<EstadoCuentaPosicionPorEmisionDTO> resultados = null;
		if (getCriterioCuenta().getCriterioTipoTenencia().getCriterioTipoCuenta().getOpcionSeleccionada().getId().equals(TipoCuentaConstants.TIPO_CONTROLADA)) {
			resultados = consultaEstadoCuentaPosicionService.consultarEstadoDeCuentaPosicionesControladas(posicion, null);
		} else {
			resultados = consultaEstadoCuentaPosicionService.consultarEstadoDeCuentaPosicionesNombrada(posicion, null);
		}

		return resultados;
	}

	/**
	 * método para obtener el atributo bovedaSeleccionada
	 * 
	 * @return el atributo bovedaSeleccionada
	 */
	public BovedaDTO getBovedaSeleccionada() {
		if (bovedaSeleccionada == null) {
			if (criterioBoveda.getOpcionSeleccionada().getId() > 0) {
				bovedaSeleccionada = criterioBoveda.getOpcionSeleccionada();
				criterioBoveda.setDebePaginar(false);
			} else {
				if (criterioBoveda.isTodos()) {
					bovedaSeleccionada = criterioBoveda.getOpcionSeleccionada();
					criterioBoveda.setDebePaginar(true);
				} else {
					List<BovedaDTO> bovedas = criterioBoveda.getPaginaDeResultados();
					if (bovedas != null && bovedas.size() > 0) {
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
		if (cuentaSeleccionada == null) {
			if (!criterioCuenta.getOpcionSeleccionada().getNumeroCuenta().equals("-1")) {
				cuentaSeleccionada = criterioCuenta.getOpcionSeleccionada();
				criterioCuenta.setDebePaginar(false);
			} else {
				if (criterioCuenta.isTodos()) {
					cuentaSeleccionada = criterioCuenta.getOpcionSeleccionada();
					criterioCuenta.setDebePaginar(true);
				} else {
					List<CuentaDTO> cuentas = criterioCuenta.getPaginaDeResultados();
					if (cuentas != null && cuentas.size() > 0) {
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
		if (emisionSeleccionada == null) {
			if (criterioEmision.getOpcionSeleccionada().getId() > 0) {
				emisionSeleccionada = criterioEmision.getOpcionSeleccionada();
				criterioEmision.setDebePaginar(false);
			} else {
				if (criterioEmision.isTodos()) {
					emisionSeleccionada = criterioEmision.getOpcionSeleccionada();
					criterioEmision.setDebePaginar(true);
				} else {
					List<EmisionDTO> criterios = criterioEmision.getPaginaDeResultados();
					if (criterios != null && criterios.size() > 0) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.ConsultaAbstract#obtenerProyeccionConsulta()
	 */
	@Override
	public long obtenerProyeccionConsulta() {
		long resultado = 0;
		// if(getCriterioFechaFinal()!=null &&
		// getCriterioFechaFinal().length()>0){
		// resultado = consultaEstadoCuentaPosicionService.
		// .obtenerProyeccionDeConsultaDePosiciones(this.getOpcionSeleccionada());
		// }
		return resultado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#recibirNotificacionResultados(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void recibirNotificacionResultados(List resultados) {
		PosicionDTO posicion = new PosicionDTO();
		posicion.setFechaFinal(this.getCriterioFechaFinal());
		posicion.setFechaInicio(this.getCriterioFechaInicial());

		posicion.setEmision(getCriterioEmision().getOpcionSeleccionada());
		posicion.setBoveda(getCriterioBoveda().getOpcionSeleccionada());

		posicion.setCuenta(getCriterioCuenta().getOpcionSeleccionada());
		getCriterioCuenta().recibirNotificacionResultados(consultaEstadoCuentaPosicionService.buscarCuentasDePosiciones(posicion));

		getCriterioCuenta().getEstadoPaginacion().setRegistrosPorPagina(1);
		if (getCriterioCuenta().getEstadoPaginacion().getNumeroPagina() < 1) {
			getCriterioCuenta().getEstadoPaginacion().setNumeroPagina(1);
		}
		getCriterioCuenta().setResultadosPorPagina(1);
		posicion.setCuenta(getCuentaSeleccionada());

		if (getCriterioCuenta().getCriterioTipoTenencia().getCriterioTipoCuenta().getOpcionSeleccionada().getId().equals(TipoCuentaConstants.TIPO_CONTROLADA)) {
			getCriterioEmision().recibirNotificacionResultados(consultaEstadoCuentaPosicionService.buscarEmisionesDePosicionesControladas(posicion, debeDejarBitacora));

		} else {
			getCriterioEmision().recibirNotificacionResultados(consultaEstadoCuentaPosicionService.buscarEmisionesDePosicionesNombradas(posicion, debeDejarBitacora));

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.ConsultaAbstract#getPaginaDeResultados()
	 */
	@Override
	public List<EstadoCuentaPosicionPorEmisionDTO> getPaginaDeResultados() {
		PosicionDTO posicion = new PosicionDTO();
		posicion.setFechaFinal(this.getCriterioFechaFinal());
		posicion.setFechaInicio(this.getCriterioFechaInicial());
		posicion.setCuenta(getCuentaSeleccionada());
		posicion.setEmision(getCriterioEmision().getOpcionSeleccionada());
		posicion.setBoveda(getCriterioBoveda().getOpcionSeleccionada());

		List<Long> emisiones = new ArrayList<Long>();
		for (EmisionDTO emision : getCriterioEmision().getPaginaDeResultados()) {
			emisiones.add(new Long(String.valueOf(emision.getId())));
		}

		List<EstadoCuentaPosicionPorEmisionDTO> resultados = null;
		if (getCriterioCuenta().getCriterioTipoTenencia().getCriterioTipoCuenta().getOpcionSeleccionada().getId().equals(TipoCuentaConstants.TIPO_CONTROLADA)) {
			resultados = consultaEstadoCuentaPosicionService.consultarEstadoDeCuentaPosicionesControladas(posicion, emisiones);
		} else {

			resultados = consultaEstadoCuentaPosicionService.consultarEstadoDeCuentaPosicionesNombrada(posicion, emisiones);
		}
		return resultados;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#setOpcionSeleccionada(java.lang.Object)
	 */
	public void setOpcionSeleccionada(EstadoCuentaPosicionPorEmisionDTO opcionSeleccionada) {
		// TODO Auto-generated method stub

	}

	/**
	 * Obtiene el campo criterioFechaFinal
	 * 
	 * @return criterioFechaFinal
	 */
	public Date getCriterioFechaFinal() {
		return criterioFechaFinal;
	}

	/**
	 * Asigna el valor del campo criterioFechaFinal
	 * 
	 * @param criterioFechaFinal
	 *            el valor de criterioFechaFinal a asignar
	 */
	public void setCriterioFechaFinal(Date criterioFechaFinal) {
		this.criterioFechaFinal = criterioFechaFinal;
	}

	/**
	 * @param debeDejarBitacora the debeDejarBitacora to set
	 */
	public void setDebeDejarBitacora(boolean debeDejarBitacora) {
		this.debeDejarBitacora = debeDejarBitacora;
	}

	/**
	 * @return the debeDejarBitacora
	 */
	public boolean isDebeDejarBitacora() {
		return debeDejarBitacora;
	}
}

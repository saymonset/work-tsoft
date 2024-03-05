/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 */
package com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.CriterioConsultaMovimientosValorDTO;
import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.EmisionDTO;
import com.indeval.portaldali.middleware.dto.EstadoCuentaPosicionPorEmisionDTO;
import com.indeval.portaldali.middleware.dto.RegistroContablePosicionControladaDTO;
import com.indeval.portaldali.middleware.dto.RegistroContablePosicionNombradaDTO;
import com.indeval.portaldali.middleware.services.common.constants.RolCuentaConstants;
import com.indeval.portaldali.middleware.services.estadocuenta.ConsultaMovimientosValorService;
import com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.ConsultaAbstract;

/**
 * Representa la implementación de la funcionalidad para obtener la consulta
 * los movimientos realizados en un lapso de tiempo en una o varias posiciones
 * de valores nombradas y controladas.
 * 
 * @author Emigdio Hernández Rodríguez
 * @version 1.0
 */
public class ConsultaMovimientosValor extends ConsultaAbstract<EstadoCuentaPosicionPorEmisionDTO> {
	/**
	 * Criterio de tipos de instrucción
	 */
	private ConsultaTipoInstruccion criterioTipoInstruccion = null;

	/** El criterio de la fecha final para la consulta de estado de cuenta */
	private Date criterioFechaFinal = null;
	/**
	 * Contiene el valor de los criterios de bóveda utilizados para la
	 * consulta en pantalla
	 */
	private BovedaDTO bovedaSeleccionada = null;

	/**
	 * Contiene el valor de los criterios de cuenta utilizados para la consulta
	 * en pantalla
	 */
	private CuentaDTO cuentaSeleccionada = null;

	/**
	 * Contiene el valor de los criterios de emisión utilizados para la
	 * consulta en pantalla
	 */
	private EmisionDTO emisionSeleccionada = null;
	/**
	 * Criterio de bóveda seleccionada utilizada para la consulta de
	 * movimientos
	 */
	private ConsultaBoveda criterioBoveda = null;

	/**
	 * Criterio de bóveda seleccionada utilizada para la consulta de
	 * movimientos
	 */
	private ConsultaCuenta criterioCuenta = null;
	/**
	 * Criterio de la cuenta del contraparte
	 */
	private ConsultaCuenta criterioCuentaContraparte = null;

	private ConsultaTipoOperacion criterioTipoOperacion = null;
	/**
	 * El criterio de emisión seleccionada utilizada para la consulta de
	 * movimientos
	 */
	private ConsultaEmision criterioEmision = null;

	/** El criterio de la fecha inicial para la consulta de movimientos */
	private Date criterioFechaInicial = null;
	/**
	 * Indica si el criterio de fecha inicial y final opera sobre la fecha de
	 * concertación
	 */
	private boolean busquedaFechaConcertacion = false;
	/**
	 * Indica si el criterio de fecha inicial y final opera sobre la fecha de
	 * aplicación
	 */
	private boolean busquedaFechaAplicacion = false;

	/**
	 * valor para el criterio de busqueda
	 */
	private boolean busquedaTipoOperacion = false;

	/** Indica si deben ordenarse lo resultados por tipo de instrucción */
	private boolean ordenarPorTipoDeInstruccion = false;

	/**
	 * Rol que ocupa el participante en la operación
	 */

	private String criterioRolParticipante = "-1";
	/**
	 * Rol que ocupa la contraparte en la operación
	 */
	private String criterioRolContraparte = "-1";
	/**
	 * Folio de la instrucción
	 */
	private String criterioFolioInstruccion = null;
	
	/**
	 * Indica si la consulta de proyecciones debe dejar bitacora o no
	 */
	private boolean debeDejarBitacora = false;
	/**
	 * Servicio de negocio para la consulta del estado de cuenta de movimeintos
	 * de posiones de valores
	 */
	private ConsultaMovimientosValorService consultaMovimientosValorService = null;

	/**
	 * método de inicializacn, se utiliza para ligar el criterio de cuenta
	 * de contraparte con los criterios de tipos de cuenta del participante
	 */
	public void init() {
		criterioCuentaContraparte.getCriterioTipoTenencia().setCriterioTipoCuenta(criterioCuenta.getCriterioTipoTenencia().getCriterioTipoCuenta());
	}

	/**
	 * Obtiene el valor del atributo ordenarPorTipoDeInstruccion
	 * 
	 * @return el valor del atributo ordenarPorTipoDeInstruccion
	 */
	public boolean isOrdenarPorTipoDeInstruccion() {
		return ordenarPorTipoDeInstruccion;
	}

	/**
	 * Establece el valor del atributo ordenarPorTipoDeOperacion
	 * 
	 * @param ordenarPorTipoDeOperacion
	 *            el valor del atributo ordenarPorTipoDeOperacion a establecer
	 */
	public void setOrdenarPorTipoDeInstruccion(boolean ordenarPorTipoDeInstruccion) {
		this.ordenarPorTipoDeInstruccion = ordenarPorTipoDeInstruccion;
	}

	/**
	 * Obtiene la descrcipcion del rol del participante
	 * 
	 * @return Descripcion del rol
	 */
	public String getDescripcionRolParticipante() {
		int rol = Integer.parseInt(criterioRolParticipante);
		String descRol = null;
		if (rol == RolCuentaConstants.AMBOS) {
			descRol = RolCuentaConstants.DESCRIPCION_ROL[0];
		} else {
			descRol = RolCuentaConstants.DESCRIPCION_ROL[rol];
		}
		return descRol;
	}

	/**
	 * Obtiene la descrcipcion del rol del contraparte
	 * 
	 * @return Descripcion del rol
	 */
	public String getDescripcionRolContraparte() {
		int rol = Integer.parseInt(criterioRolContraparte);
		String descRol = null;
		if (rol == RolCuentaConstants.AMBOS) {
			descRol = RolCuentaConstants.DESCRIPCION_ROL[0];
		} else {
			descRol = RolCuentaConstants.DESCRIPCION_ROL[rol];
		}
		return descRol;
	}

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

	public EstadoCuentaPosicionPorEmisionDTO getOpcionSeleccionada() {
		getBovedaSeleccionada();
		getCuentaSeleccionada();
		getEmisionSeleccionada();
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.ConsultaAbstract#getResultados()
	 */
	@Override
	public List<EstadoCuentaPosicionPorEmisionDTO> getResultados() {
		CriterioConsultaMovimientosValorDTO criterio = new CriterioConsultaMovimientosValorDTO();

		criterio.setCuentaContraparte(getCriterioCuentaContraparte().getOpcionSeleccionada());
		criterio.setCuenta(criterioCuenta.getOpcionSeleccionada());
		criterio.setEmision(getCriterioEmision().getOpcionSeleccionada());
		criterio.setBoveda(getCriterioBoveda().getOpcionSeleccionada());
		criterio.setFechaInicial(getCriterioFechaInicial());
		criterio.setFechaFinal(getCriterioFechaFinal());
		criterio.setBusquedaFechaAplicacion(isBusquedaFechaAplicacion());
		criterio.setBusquedaFechaConcertacion(isBusquedaFechaConcertacion());
		criterio.setTipoInstruccion(getCriterioTipoInstruccion().getOpcionSeleccionada());
		criterio.setTipoOperacion(getCriterioTipoOperacion().getOpcionSeleccionada());
		criterio.setFolioInstruccion(getCriterioFolioInstruccion());
		criterio.setOrdenarPorTipoDeInstruccion(isOrdenarPorTipoDeInstruccion());
		criterio.setRolContraparte(Integer.parseInt(getCriterioRolContraparte()));
		criterio.setRolParticipante(Integer.parseInt(getCriterioRolParticipante()));

		return consultaMovimientosValorService.consultarMovimientosDeValor(criterio, null);
	}

	/**
	 * Obtiene los resultados de la consulta de movimientos de valores sin
	 * agruparlos por emisión ni por bóveda para el reporte en formato excel.
	 * 
	 * @return una lista con los registros contables nombrados o controlados
	 *         según sea el caso.
	 */
	public List<RegistroContablePosicionNombradaDTO> getResultadosNombradasSinAgrupar() {
		CriterioConsultaMovimientosValorDTO criterio = new CriterioConsultaMovimientosValorDTO();

		criterio.setCuentaContraparte(getCriterioCuentaContraparte().getOpcionSeleccionada());
		criterio.setCuenta(criterioCuenta.getOpcionSeleccionada());
		criterio.setEmision(getCriterioEmision().getOpcionSeleccionada());
		criterio.setBoveda(getCriterioBoveda().getOpcionSeleccionada());
		criterio.setFechaInicial(getCriterioFechaInicial());
		criterio.setFechaFinal(getCriterioFechaFinal());
		criterio.setBusquedaFechaAplicacion(isBusquedaFechaAplicacion());
		criterio.setBusquedaFechaConcertacion(isBusquedaFechaConcertacion());
		criterio.setTipoInstruccion(getCriterioTipoInstruccion().getOpcionSeleccionada());
		criterio.setTipoOperacion(getCriterioTipoOperacion().getOpcionSeleccionada());
		criterio.setFolioInstruccion(getCriterioFolioInstruccion());
		criterio.setOrdenarPorTipoDeInstruccion(isOrdenarPorTipoDeInstruccion());
		criterio.setRolContraparte(Integer.parseInt(getCriterioRolContraparte()));
		criterio.setRolParticipante(Integer.parseInt(getCriterioRolParticipante()));

		return consultaMovimientosValorService.consultarMovimientosDeValorNombradasSinAgrupar(criterio, null);
	}

	/**
	 * Obtiene los resultados de la consulta de movimientos de valores sin
	 * agruparlos por emisión ni por bóveda para el reporte en formato excel.
	 * 
	 * @return una lista con los registros contables nombrados o controlados
	 *         según sea el caso.
	 */
	public List<RegistroContablePosicionControladaDTO> getResultadosControladasSinAgrupar() {
		CriterioConsultaMovimientosValorDTO criterio = new CriterioConsultaMovimientosValorDTO();

		criterio.setCuentaContraparte(getCriterioCuentaContraparte().getOpcionSeleccionada());
		criterio.setCuenta(criterioCuenta.getOpcionSeleccionada());
		criterio.setEmision(getCriterioEmision().getOpcionSeleccionada());
		criterio.setBoveda(getCriterioBoveda().getOpcionSeleccionada());
		criterio.setFechaInicial(getCriterioFechaInicial());
		criterio.setFechaFinal(getCriterioFechaFinal());
		criterio.setBusquedaFechaAplicacion(isBusquedaFechaAplicacion());
		criterio.setBusquedaFechaConcertacion(isBusquedaFechaConcertacion());
		criterio.setTipoInstruccion(getCriterioTipoInstruccion().getOpcionSeleccionada());
		criterio.setTipoOperacion(getCriterioTipoOperacion().getOpcionSeleccionada());
		criterio.setFolioInstruccion(getCriterioFolioInstruccion());
		criterio.setOrdenarPorTipoDeInstruccion(isOrdenarPorTipoDeInstruccion());
		// criterio.setRolContraparte(Integer.parseInt(getCriterioRolContraparte()));
		// criterio.setRolParticipante(Integer.parseInt(getCriterioRolParticipante()));

		return consultaMovimientosValorService.consultarMovimientosDeValorControladasSinAgrupar(criterio, null);
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
	 * Obtiene el criterio actual de consulta
	 * 
	 * @return Criterio
	 */
	public CriterioConsultaMovimientosValorDTO getCriterioConsulta() {
		CriterioConsultaMovimientosValorDTO criterio = new CriterioConsultaMovimientosValorDTO();
		criterio.setCuentaContraparte(getCriterioCuentaContraparte().getOpcionSeleccionada());
		criterio.setCuenta(getCuentaSeleccionada());
		criterio.setEmision(getCriterioEmision().getOpcionSeleccionada());
		criterio.setBoveda(getCriterioBoveda().getOpcionSeleccionada());
		criterio.setFechaInicial(getCriterioFechaInicial());
		criterio.setFechaFinal(getCriterioFechaFinal());
		criterio.setBusquedaFechaAplicacion(isBusquedaFechaAplicacion());
		criterio.setBusquedaFechaConcertacion(isBusquedaFechaConcertacion());
		criterio.setTipoInstruccion(getCriterioTipoInstruccion().getOpcionSeleccionada());
		criterio.setTipoOperacion(getCriterioTipoOperacion().getOpcionSeleccionada());
		criterio.setFolioInstruccion(getCriterioFolioInstruccion());
		criterio.setOrdenarPorTipoDeInstruccion(isOrdenarPorTipoDeInstruccion());
		criterio.setRolContraparte(Integer.parseInt(getCriterioRolContraparte()));
		criterio.setRolParticipante(Integer.parseInt(getCriterioRolParticipante()));
		return criterio;
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

		return resultado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#recibirNotificacionResultados(java.util.List)
	 */
	public void recibirNotificacionResultados(List resultados) {

		CriterioConsultaMovimientosValorDTO criterio = new CriterioConsultaMovimientosValorDTO();

		criterio.setCuentaContraparte(getCriterioCuentaContraparte().getOpcionSeleccionada());
		
		criterio.setEmision(getCriterioEmision().getOpcionSeleccionada());
		criterio.setBoveda(getCriterioBoveda().getOpcionSeleccionada());
		criterio.setFechaInicial(getCriterioFechaInicial());
		criterio.setFechaFinal(getCriterioFechaFinal());
		criterio.setBusquedaFechaAplicacion(isBusquedaFechaAplicacion());
		criterio.setBusquedaFechaConcertacion(isBusquedaFechaConcertacion());
		criterio.setTipoInstruccion(getCriterioTipoInstruccion().getOpcionSeleccionada());
		criterio.setTipoOperacion(getCriterioTipoOperacion().getOpcionSeleccionada());
		criterio.setFolioInstruccion(getCriterioFolioInstruccion());
		criterio.setOrdenarPorTipoDeInstruccion(isOrdenarPorTipoDeInstruccion());
		criterio.setRolContraparte(Integer.parseInt(getCriterioRolContraparte()));
		criterio.setRolParticipante(Integer.parseInt(getCriterioRolParticipante()));
		
		criterio.setCuenta(getCriterioCuenta().getOpcionSeleccionada());
		getCriterioCuenta().recibirNotificacionResultados(consultaMovimientosValorService.buscarCuentasDeMovimientosDeValor(criterio));
		
		getCriterioCuenta().getEstadoPaginacion().setRegistrosPorPagina(1);
		if(getCriterioCuenta().getEstadoPaginacion().getNumeroPagina() < 1) {
			getCriterioCuenta().getEstadoPaginacion().setNumeroPagina(1);
		}
		getCriterioCuenta().setResultadosPorPagina(1);
		criterio.setCuenta(getCuentaSeleccionada());
		getCriterioEmision().recibirNotificacionResultados(consultaMovimientosValorService.buscarEmisionesDeMovimientosDeValor(criterio));
				
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.ConsultaAbstract#getPaginaDeResultados()
	 */
	@Override
	public List<EstadoCuentaPosicionPorEmisionDTO> getPaginaDeResultados() {
		CriterioConsultaMovimientosValorDTO criterio = new CriterioConsultaMovimientosValorDTO();

		criterio.setCuentaContraparte(getCriterioCuentaContraparte().getOpcionSeleccionada());
		criterio.setCuenta(getCuentaSeleccionada());
		criterio.setEmision(getCriterioEmision().getOpcionSeleccionada());
		criterio.setBoveda(getCriterioBoveda().getOpcionSeleccionada());
		criterio.setFechaInicial(getCriterioFechaInicial());
		criterio.setFechaFinal(getCriterioFechaFinal());
		criterio.setBusquedaFechaAplicacion(isBusquedaFechaAplicacion());
		criterio.setBusquedaFechaConcertacion(isBusquedaFechaConcertacion());
		criterio.setTipoInstruccion(getCriterioTipoInstruccion().getOpcionSeleccionada());
		criterio.setTipoOperacion(getCriterioTipoOperacion().getOpcionSeleccionada());
		criterio.setFolioInstruccion(getCriterioFolioInstruccion());
		criterio.setOrdenarPorTipoDeInstruccion(isOrdenarPorTipoDeInstruccion());
		criterio.setRolContraparte(Integer.parseInt(getCriterioRolContraparte()));
		criterio.setRolParticipante(Integer.parseInt(getCriterioRolParticipante()));

		List<Long> emisiones = new ArrayList<Long>();
		for (EmisionDTO emision : getCriterioEmision().getPaginaDeResultados()) {
			emisiones.add(emision.getId());
		}

		List<EstadoCuentaPosicionPorEmisionDTO> resultados = null;

		resultados = consultaMovimientosValorService.consultarMovimientosDeValor(criterio, new HashSet<Long>(emisiones));

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
	 * Obtiene el campo criterioTipoInstruccion
	 * 
	 * @return criterioTipoInstruccion
	 */
	public ConsultaTipoInstruccion getCriterioTipoInstruccion() {
		return criterioTipoInstruccion;
	}

	/**
	 * Asigna el valor del campo criterioTipoInstruccion
	 * 
	 * @param criterioTipoInstruccion
	 *            el valor de criterioTipoInstruccion a asignar
	 */
	public void setCriterioTipoInstruccion(ConsultaTipoInstruccion criterioTipoInstruccion) {
		this.criterioTipoInstruccion = criterioTipoInstruccion;
	}

	/**
	 * Obtiene el campo busquedaFechaConcertacion
	 * 
	 * @return busquedaFechaConcertacion
	 */
	public boolean isBusquedaFechaConcertacion() {
		return busquedaFechaConcertacion;
	}

	/**
	 * Asigna el valor del campo busquedaFechaConcertacion
	 * 
	 * @param busquedaFechaConcertacion
	 *            el valor de busquedaFechaConcertacion a asignar
	 */
	public void setBusquedaFechaConcertacion(boolean busquedaFechaConcertacion) {
		this.busquedaFechaConcertacion = busquedaFechaConcertacion;
	}

	/**
	 * Obtiene el campo busquedaFechaAplicacion
	 * 
	 * @return busquedaFechaAplicacion
	 */
	public boolean isBusquedaFechaAplicacion() {
		return busquedaFechaAplicacion;
	}

	/**
	 * Asigna el valor del campo busquedaFechaAplicacion
	 * 
	 * @param busquedaFechaAplicacion
	 *            el valor de busquedaFechaAplicacion a asignar
	 */
	public void setBusquedaFechaAplicacion(boolean busquedaFechaAplicacion) {
		this.busquedaFechaAplicacion = busquedaFechaAplicacion;
	}

	/**
	 * Obtiene el campo criterioRolParticipante
	 * 
	 * @return criterioRolParticipante
	 */
	public String getCriterioRolParticipante() {
		return criterioRolParticipante;
	}

	/**
	 * Asigna el valor del campo criterioRolParticipante
	 * 
	 * @param criterioRolParticipante
	 *            el valor de criterioRolParticipante a asignar
	 */
	public void setCriterioRolParticipante(String criterioRolParticipante) {
		this.criterioRolParticipante = criterioRolParticipante;
	}

	/**
	 * Obtiene el campo criterioCuentaContraparte
	 * 
	 * @return criterioCuentaContraparte
	 */
	public ConsultaCuenta getCriterioCuentaContraparte() {
		return criterioCuentaContraparte;
	}

	/**
	 * Asigna el valor del campo criterioCuentaContraparte
	 * 
	 * @param criterioCuentaContraparte
	 *            el valor de criterioCuentaContraparte a asignar
	 */
	public void setCriterioCuentaContraparte(ConsultaCuenta criterioCuentaContraparte) {
		this.criterioCuentaContraparte = criterioCuentaContraparte;
	}

	/**
	 * Obtiene el campo criterioRolContraparte
	 * 
	 * @return criterioRolContraparte
	 */
	public String getCriterioRolContraparte() {
		return criterioRolContraparte;
	}

	/**
	 * Asigna el valor del campo criterioRolContraparte
	 * 
	 * @param criterioRolContraparte
	 *            el valor de criterioRolContraparte a asignar
	 */
	public void setCriterioRolContraparte(String criterioRolContraparte) {
		this.criterioRolContraparte = criterioRolContraparte;
	}

	/**
	 * Obtiene el campo criterioTipoOperacion
	 * 
	 * @return criterioTipoOperacion
	 */
	public ConsultaTipoOperacion getCriterioTipoOperacion() {
		return criterioTipoOperacion;
	}

	/**
	 * Asigna el valor del campo criterioTipoOperacion
	 * 
	 * @param criterioTipoOperacion
	 *            el valor de criterioTipoOperacion a asignar
	 */
	public void setCriterioTipoOperacion(ConsultaTipoOperacion criterioTipoOperacion) {
		this.criterioTipoOperacion = criterioTipoOperacion;
	}

	/**
	 * Obtiene el campo consultaMovimientosValorService
	 * 
	 * @return consultaMovimientosValorService
	 */
	public ConsultaMovimientosValorService getConsultaMovimientosValorService() {
		return consultaMovimientosValorService;
	}

	/**
	 * Asigna el valor del campo consultaMovimientosValorService
	 * 
	 * @param consultaMovimientosValorService
	 *            el valor de consultaMovimientosValorService a asignar
	 */
	public void setConsultaMovimientosValorService(ConsultaMovimientosValorService consultaMovimientosValorService) {
		this.consultaMovimientosValorService = consultaMovimientosValorService;
	}

	/**
	 * Obtiene el campo criterioFolioInstruccion
	 * 
	 * @return criterioFolioInstruccion
	 */
	public String getCriterioFolioInstruccion() {
		return criterioFolioInstruccion;
	}

	/**
	 * Asigna el valor del campo criterioFolioInstruccion
	 * 
	 * @param criterioFolioInstruccion
	 *            el valor de criterioFolioInstruccion a asignar
	 */
	public void setCriterioFolioInstruccion(String criterioFolioInstruccion) {
		this.criterioFolioInstruccion = criterioFolioInstruccion;
	}

	public boolean isBusquedaTipoOperacion() {
		return busquedaTipoOperacion;
	}

	public void setBusquedaTipoOperacion(boolean busquedaTipoOperacion) {
		this.busquedaTipoOperacion = busquedaTipoOperacion;
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

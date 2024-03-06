/**
 * 2H Software SA de CV
 * 
 * Sistema de Consulta de Estado de Cuenta - Indeval
 *
 * Fecha de creación: Jan 3, 2008
 */
package com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.CriterioConsultaMovimientosEfectivoDTO;
import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.CuentaEfectivoDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.EmisionDTO;
import com.indeval.portaldali.middleware.dto.EstadoCuentaEfectivoPorDivisaDTO;
import com.indeval.portaldali.middleware.dto.MercadoDTO;
import com.indeval.portaldali.middleware.dto.RegistroContableSaldoControladaDTO;
import com.indeval.portaldali.middleware.dto.RegistroContableSaldoNombradaDTO;
import com.indeval.portaldali.middleware.services.common.constants.RolCuentaConstants;
import com.indeval.portaldali.middleware.services.estadocuenta.ConsultaMovimientosEfectivoService;
import com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.ConsultaAbstract;

/**
 * Representa la implementación de la funcionalidad para obtener la consulta del
 * estado de cuenta de movimientos de de saldos de efectivo, esta clase sirve
 * para consultar saldos nombrados y controlados.
 * 
 * @author Sandra Flores Arrieta
 * @version 1.0
 * 
 */
/**
 * @author ribarra
 *
 */
public class ConsultaMovimientosEfectivo extends ConsultaAbstract<EstadoCuentaEfectivoPorDivisaDTO> {

	/**
	 * Criterio de cuenta del participante seleccionada utilizada para consulta
	 * de movimientos
	 */
	private ConsultaCuentaEfectivo criterioCuenta = null;

	/**
	 * Criterio de cuenta de la contraparte seleccionada utilizada para consulta
	 * de movimientos
	 */
	private CuentaEfectivoDTO criterioCuentaContraparte = null;

	/**
	 * Criterio de bóveda seleccionada utilizada para consulta del estado de
	 * cuenta de movimientos
	 */
	private ConsultaBoveda criterioBoveda = null;
	
	
	/** El mercado seleccionado para la consulta */
	private MercadoDTO mercadoSeleccionado;
	
	/**
	 * Criterio que contiene la cuenta de valores de la contraparte
	 */
	private CuentaDTO criterioCuentaValoresContraparte = null;

	/**
	 * Criterio que contiene los datos de la emisión
	 */
	private EmisionDTO criterioEmision = new EmisionDTO();

	/**
	 * El criterio de divisa seleccionada utilizada para la consulta del estado
	 * de cuenta de movimientos
	 */
	private ConsultaDivisa criterioDivisa = null;

	/**
	 * El criterio de la fecha inicial para la consulta del estado de cuenta de
	 * movimientos
	 */
	private Date criterioFechaInicial = null;

	/** El criterio de la fecha final para la consulta de estado de cuenta */
	private Date criterioFechaFinal = null;

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
	 *  flag para verificar si el criterio es  tipo operacion
	 */
	private ConsultaTipoOperacion criterioTipoOperacion = null;
	/**
	 * Criterio de tipos de instrucción
	 */
	private ConsultaTipoInstruccion criterioTipoInstruccion = null;

	/**
	 * El criterio de cuenta del participante seleccionada utilizada para la
	 * consulta
	 */
	private CuentaEfectivoDTO cuentaSeleccionada = null;

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

	/** Indica si deben ordenarse lo resultados por tipo de instrucción */
	private boolean ordenarPorTipoDeInstruccion = false;

	private ConsultaMovimientosEfectivoService consultaMovimientosService = null;

	/**
	 * El importe de la busqueda
	 */
	private BigDecimal importe = BigDecimal.valueOf(0);
	
	/**
	 * Indica si se debe dejar bitacora de la consulta
	 */
	private boolean debeDejarBitacora;
	
	
	/**
	 * Tipo de Retiro
	 */
	private String tipoRetiro;

	/**
	 * método de inicializacn, se utiliza para ligar el criterio de cuenta de
	 * contraparte con los criterios de tipos de cuenta del participante
	 */
	public void init() {

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
	 * Obtiene el valor del atributo mercadoSeleccionado
	 *
	 * @return el valor del atributo mercadoSeleccionado
	 */
	public MercadoDTO getMercadoSeleccionado() {
		return mercadoSeleccionado;
	}

	/**
	 * Establece el valor del atributo mercadoSeleccionado
	 *
	 * @param mercadoSeleccionado el valor del atributo mercadoSeleccionado a establecer
	 */
	public void setMercadoSeleccionado(MercadoDTO mercadoSeleccionado) {
		this.mercadoSeleccionado = mercadoSeleccionado;
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
	 * Obtiene el valor del atributo criterioEmision
	 * 
	 * @return el valor del atributo criterioEmision
	 */
	public EmisionDTO getCriterioEmision() {
		return criterioEmision;
	}

	/**
	 * Establece el valor del atributo criterioEmision
	 * 
	 * @param criterioEmision
	 *            el valor del atributo criterioEmision a establecer
	 */
	public void setCriterioEmision(EmisionDTO criterioEmision) {
		this.criterioEmision = criterioEmision;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.ConsultaAbstract#getPaginaDeResultados()
	 */
	@Override
	public List<EstadoCuentaEfectivoPorDivisaDTO> getPaginaDeResultados() {
		CriterioConsultaMovimientosEfectivoDTO criterio = new CriterioConsultaMovimientosEfectivoDTO();
		
		criterio.setEmision(criterioEmision);
		criterio.getEmision().getTipoValor().setMercado(mercadoSeleccionado);
		criterio.setCuentaValoresContraparte(criterioCuentaValoresContraparte);
		criterio.setCuentaContraparte(criterioCuentaContraparte);
		criterio.setCuenta(getCuentaSeleccionada());
		criterio.setDivisa(getCriterioDivisa().getOpcionSeleccionada());
		criterio.setBoveda(getCriterioBoveda().getOpcionSeleccionada());

		criterio.setFechaInicial(getCriterioFechaInicial());
		criterio.setFechaFinal(getCriterioFechaFinal());

		criterio.setBusquedaFechaAplicacion(isBusquedaFechaAplicacion());
		criterio.setBusquedaFechaConcertacion(isBusquedaFechaConcertacion());

		criterio.setTipoInstruccion(getCriterioTipoInstruccion().getOpcionSeleccionada());
		criterio.setTipoOperacion(getCriterioTipoOperacion().getOpcionSeleccionada());
		criterio.setFolioInstruccion(getCriterioFolioInstruccion());

		criterio.setRolContraparte(Integer.parseInt(getCriterioRolContraparte()));
		criterio.setRolParticipante(Integer.parseInt(getCriterioRolParticipante()));

		criterio.setImporte(importe);
		criterio.setTipoRetiro(tipoRetiro);

		/** Se setea la opción de buscar por ordenamiento por tipo de instrucción */
		criterio.setOrdenarPorTipoDeInstruccion(isOrdenarPorTipoDeInstruccion());

		List<Long> divisas = new ArrayList<Long>();
		for (DivisaDTO divisa : getCriterioDivisa().getPaginaDeResultados()) {
			divisas.add(divisa.getId());
		}

		return consultaMovimientosService.consultarMovimientosDeEfectivo(criterio, divisas,debeDejarBitacora);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.ConsultaAbstract#getResultados()
	 */
	@Override
	public List<EstadoCuentaEfectivoPorDivisaDTO> getResultados() {
		CriterioConsultaMovimientosEfectivoDTO criterio = new CriterioConsultaMovimientosEfectivoDTO();
		
		criterio.setEmision(criterioEmision);
		criterio.getEmision().getTipoValor().setMercado(mercadoSeleccionado);
		criterio.setCuentaValoresContraparte(criterioCuentaValoresContraparte);
		criterio.setCuentaContraparte(criterioCuentaContraparte);
		criterio.setCuenta(getCuentaSeleccionada());
		criterio.setDivisa(getCriterioDivisa().getOpcionSeleccionada());
		criterio.setBoveda(getCriterioBoveda().getOpcionSeleccionada());

		criterio.setFechaInicial(getCriterioFechaInicial());
		criterio.setFechaFinal(getCriterioFechaFinal());

		criterio.setBusquedaFechaAplicacion(isBusquedaFechaAplicacion());
		criterio.setBusquedaFechaConcertacion(isBusquedaFechaConcertacion());

		criterio.setTipoInstruccion(getCriterioTipoInstruccion().getOpcionSeleccionada());
		criterio.setTipoOperacion(getCriterioTipoOperacion().getOpcionSeleccionada());
		criterio.setFolioInstruccion(getCriterioFolioInstruccion());

		criterio.setRolContraparte(Integer.parseInt(getCriterioRolContraparte()));
		criterio.setRolParticipante(Integer.parseInt(getCriterioRolParticipante()));

		criterio.setImporte(importe);
		criterio.setTipoRetiro(tipoRetiro);

		return consultaMovimientosService.consultarMovimientosDeEfectivo(criterio, consultaMovimientosService
				.buscarDivisasDeRegistrosContablesDeEfectivo(criterio),debeDejarBitacora);
	}

	/**
	 * Obtiene la lista de registros contables de cuentas nombradas que
	 * conforman la consulta de movimientos para el reporte en formato XLS.
	 * 
	 * @return la lista de registros contables que conforman la consulta de
	 *         movimientos.
	 */
	public List<RegistroContableSaldoNombradaDTO> getResultadosNombradasSinAgrupar() {
		CriterioConsultaMovimientosEfectivoDTO criterio = new CriterioConsultaMovimientosEfectivoDTO();
		
		criterio.setEmision(criterioEmision);
		criterio.getEmision().getTipoValor().setMercado(mercadoSeleccionado);
		criterio.setCuentaValoresContraparte(criterioCuentaValoresContraparte);
		criterio.setCuentaContraparte(criterioCuentaContraparte);
		criterio.setCuenta(criterioCuenta.getOpcionSeleccionada());
		criterio.setDivisa(getCriterioDivisa().getOpcionSeleccionada());
		criterio.setBoveda(getCriterioBoveda().getOpcionSeleccionada());

		criterio.setFechaInicial(getCriterioFechaInicial());
		criterio.setFechaFinal(getCriterioFechaFinal());

		criterio.setBusquedaFechaAplicacion(isBusquedaFechaAplicacion());
		criterio.setBusquedaFechaConcertacion(isBusquedaFechaConcertacion());

		criterio.setTipoInstruccion(getCriterioTipoInstruccion().getOpcionSeleccionada());
		criterio.setTipoOperacion(getCriterioTipoOperacion().getOpcionSeleccionada());
		criterio.setFolioInstruccion(getCriterioFolioInstruccion());

		criterio.setRolContraparte(Integer.parseInt(getCriterioRolContraparte()));
		criterio.setRolParticipante(Integer.parseInt(getCriterioRolParticipante()));

		criterio.setImporte(importe);
		criterio.setTipoRetiro(tipoRetiro);

		return consultaMovimientosService.consultarMovimientosDeEfectivoNombradasSinAgrupar(criterio, consultaMovimientosService
				.buscarDivisasDeRegistrosContablesDeEfectivo(criterio));
	}

	/**
	 * Obtiene la lista de registros contables de cuentas controladas que
	 * conforman la consulta de momvimientos para el reporte en formato XLS.
	 * 
	 * @return la lista de registros contables que conforman la consulta de
	 *         movimientos.
	 */
	public List<RegistroContableSaldoControladaDTO> getResultadosControladasSinAgrupar() {
		CriterioConsultaMovimientosEfectivoDTO criterio = new CriterioConsultaMovimientosEfectivoDTO();
		
		criterio.setEmision(criterioEmision);
		criterio.getEmision().getTipoValor().setMercado(mercadoSeleccionado);
		criterio.setCuentaValoresContraparte(criterioCuentaValoresContraparte);
		criterio.setCuentaContraparte(criterioCuentaContraparte);
		criterio.setCuenta(criterioCuenta.getOpcionSeleccionada());
		criterio.setDivisa(getCriterioDivisa().getOpcionSeleccionada());
		criterio.setBoveda(getCriterioBoveda().getOpcionSeleccionada());

		criterio.setFechaInicial(getCriterioFechaInicial());
		criterio.setFechaFinal(getCriterioFechaFinal());

		criterio.setBusquedaFechaAplicacion(isBusquedaFechaAplicacion());
		criterio.setBusquedaFechaConcertacion(isBusquedaFechaConcertacion());

		criterio.setTipoInstruccion(getCriterioTipoInstruccion().getOpcionSeleccionada());
		criterio.setTipoOperacion(getCriterioTipoOperacion().getOpcionSeleccionada());
		criterio.setFolioInstruccion(getCriterioFolioInstruccion());

		// criterio.setRolContraparte(Integer.parseInt(getCriterioRolContraparte()));
		// criterio.setRolParticipante(Integer.parseInt(getCriterioRolParticipante()));

		return consultaMovimientosService.consultarMovimientosDeEfectivoControladasSinAgrupar(criterio, consultaMovimientosService
				.buscarDivisasDeRegistrosContablesDeEfectivo(criterio));
	}

	/**
	 * Obtiene el criterio de consulta actualmente utilizado
	 * 
	 * @return
	 */
	public CriterioConsultaMovimientosEfectivoDTO getCriterioConsulta() {
		CriterioConsultaMovimientosEfectivoDTO criterio = new CriterioConsultaMovimientosEfectivoDTO();
		
		criterio.setEmision(criterioEmision);
		criterio.getEmision().getTipoValor().setMercado(mercadoSeleccionado);
		criterio.setCuentaValoresContraparte(criterioCuentaValoresContraparte);
		criterio.setCuentaContraparte(criterioCuentaContraparte);
		criterio.setCuenta(getCuentaSeleccionada());
		criterio.setDivisa(getCriterioDivisa().getOpcionSeleccionada());
		criterio.setBoveda(getCriterioBoveda().getOpcionSeleccionada());

		criterio.setFechaInicial(getCriterioFechaInicial());
		criterio.setFechaFinal(getCriterioFechaFinal());

		criterio.setBusquedaFechaAplicacion(isBusquedaFechaAplicacion());
		criterio.setBusquedaFechaConcertacion(isBusquedaFechaConcertacion());

		criterio.setTipoInstruccion(getCriterioTipoInstruccion().getOpcionSeleccionada());
		criterio.setTipoOperacion(getCriterioTipoOperacion().getOpcionSeleccionada());
		criterio.setFolioInstruccion(getCriterioFolioInstruccion());

		criterio.setRolContraparte(Integer.parseInt(getCriterioRolContraparte()));
		criterio.setRolParticipante(Integer.parseInt(getCriterioRolParticipante()));
		criterio.setOrdenarPorTipoDeInstruccion(isOrdenarPorTipoDeInstruccion());

		return criterio;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#getOpcionSeleccionada()
	 */
	public EstadoCuentaEfectivoPorDivisaDTO getOpcionSeleccionada() {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#setOpcionSeleccionada(java.lang.Object)
	 */
	public void setOpcionSeleccionada(EstadoCuentaEfectivoPorDivisaDTO opcionSeleccionada) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#recibirNotificacionResultados(java.util.List)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void recibirNotificacionResultados(List resultados) {
		CriterioConsultaMovimientosEfectivoDTO criterio = new CriterioConsultaMovimientosEfectivoDTO();
		criterio.setEmision(criterioEmision);
		criterio.getEmision().getTipoValor().setMercado(mercadoSeleccionado);
		criterio.setCuentaValoresContraparte(criterioCuentaValoresContraparte);
		criterio.setCuentaContraparte(criterioCuentaContraparte);
		criterio.setCuenta(getCuentaSeleccionada());
		criterio.setDivisa(getCriterioDivisa().getOpcionSeleccionada());
		criterio.setBoveda(getCriterioBoveda().getOpcionSeleccionada());

		criterio.setFechaInicial(getCriterioFechaInicial());
		criterio.setFechaFinal(getCriterioFechaFinal());

		criterio.setBusquedaFechaAplicacion(isBusquedaFechaAplicacion());
		criterio.setBusquedaFechaConcertacion(isBusquedaFechaConcertacion());

		criterio.setTipoInstruccion(getCriterioTipoInstruccion().getOpcionSeleccionada());
		criterio.setTipoOperacion(getCriterioTipoOperacion().getOpcionSeleccionada());
		criterio.setFolioInstruccion(getCriterioFolioInstruccion());

		criterio.setRolContraparte(Integer.parseInt(getCriterioRolContraparte()));
		criterio.setRolParticipante(Integer.parseInt(getCriterioRolParticipante()));
		getCriterioDivisa().recibirNotificacionResultados(consultaMovimientosService.buscarDivisasDeRegistrosContablesDeEfectivo(criterio));
		criterio.setCuenta(getCriterioCuenta().getOpcionSeleccionada());
		getCriterioCuenta().recibirNotificacionResultados(consultaMovimientosService.buscarCuentasDeRegistrosContablesDeEfectivo(criterio));
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
	 * Obtiene el valor del atributo criterioDivisa
	 * 
	 * @return el valor del atributo criterioDivisa
	 */
	public ConsultaDivisa getCriterioDivisa() {
		return criterioDivisa;
	}

	/**
	 * Establece el valor del atributo criterioDivisa
	 * 
	 * @param criterioDivisa
	 *            el valor del atributo criterioDivisa a establecer.
	 */
	public void setCriterioDivisa(ConsultaDivisa criterioDivisa) {
		this.criterioDivisa = criterioDivisa;
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
	 * Obtiene el valor del atributo criterioFechaFinal
	 * 
	 * @return el valor del atributo criterioFechaFinal
	 */
	public Date getCriterioFechaFinal() {
		return criterioFechaFinal;
	}

	/**
	 * Establece el valor del atributo criterioFechaFinal
	 * 
	 * @param criterioFechaFinal
	 *            el valor del atributo criterioFechaFinal a establecer.
	 */
	public void setCriterioFechaFinal(Date criterioFechaFinal) {
		this.criterioFechaFinal = criterioFechaFinal;
	}

	/**
	 * Obtiene el valor del atributo bovedaSeleccionada
	 * 
	 * @return el valor del atributo bovedaSeleccionada
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
	 * Establece el valor del atributo bovedaSeleccionada
	 * 
	 * @param bovedaSeleccionada
	 *            el valor del atributo bovedaSeleccionada a establecer.
	 */
	public void setBovedaSeleccionada(BovedaDTO bovedaSeleccionada) {
		this.bovedaSeleccionada = bovedaSeleccionada;
	}

	/**
	 * Obtiene el valor del atributo divisaSeleccionada
	 * 
	 * @return el valor del atributo divisaSeleccionada
	 */
	public DivisaDTO getDivisaSeleccionada() {
		if (divisaSeleccionada == null) {
			if (criterioDivisa.getOpcionSeleccionada().getId() > 0) {
				divisaSeleccionada = criterioDivisa.getOpcionSeleccionada();
				criterioDivisa.setDebePaginar(false);
			} else {
				if (criterioDivisa.isTodos()) {
					divisaSeleccionada = criterioDivisa.getOpcionSeleccionada();
					criterioDivisa.setDebePaginar(true);
				} else {
					List<DivisaDTO> divisas = criterioDivisa.getPaginaDeResultados();
					if (divisas != null && divisas.size() > 0) {
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
	 * Establece el valor del atributo divisaSeleccionada
	 * 
	 * @param divisaSeleccionada
	 *            el valor del atributo divisaSeleccionada a establecer.
	 */
	public void setDivisaSeleccionada(DivisaDTO divisaSeleccionada) {
		this.divisaSeleccionada = divisaSeleccionada;
	}

	/**
	 * Obtiene el valor del atributo criterioCuentaParticipante
	 * 
	 * @return el valor del atributo criterioCuentaParticipante
	 */
	public ConsultaCuentaEfectivo getCriterioCuenta() {
		return criterioCuenta;
	}

	/**
	 * Establece el valor del atributo criterioCuentaParticipante
	 * 
	 * @param criterioCuentaParticipante
	 *            el valor del atributo criterioCuentaParticipante a establecer.
	 */
	public void setCriterioCuenta(ConsultaCuentaEfectivo criterioCuentaParticipante) {
		this.criterioCuenta = criterioCuentaParticipante;
	}

	/**
	 * Obtiene el valor del atributo criterioCuentaContraparte
	 * 
	 * @return el valor del atributo criterioCuentaContraparte
	 */
	public CuentaEfectivoDTO getCriterioCuentaContraparte() {
		return criterioCuentaContraparte;
	}

	/**
	 * Establece el valor del atributo criterioCuentaContraparte
	 * 
	 * @param criterioCuentaContraparte
	 *            el valor del atributo criterioCuentaContraparte a establecer.
	 */
	public void setCriterioCuentaContraparte(CuentaEfectivoDTO criterioCuentaContraparte) {
		this.criterioCuentaContraparte = criterioCuentaContraparte;
	}

	/**
	 * Obtiene el valor del atributo cuentaParticipanteSeleccionada
	 * 
	 * @return el valor del atributo cuentaParticipanteSeleccionada
	 */
	public CuentaEfectivoDTO getCuentaSeleccionada() {
		if (cuentaSeleccionada == null) {
			if (!criterioCuenta.getOpcionSeleccionada().getNumeroCuenta().equals("-1")) {
				cuentaSeleccionada = criterioCuenta.getOpcionSeleccionada();
				criterioCuenta.setDebePaginar(true);
			} else {
				cuentaSeleccionada = criterioCuenta.getOpcionSeleccionada();
				criterioCuenta.setDebePaginar(true);
			}
		}

		return cuentaSeleccionada;
	}

	/**
	 * Asigna el valor del campo cuentaSeleccionada
	 * 
	 * @param cuentaSeleccionada
	 *            el valor de cuentaSeleccionada a asignar
	 */
	public void setCuentaSeleccionada(CuentaEfectivoDTO cuentaSeleccionada) {
		this.cuentaSeleccionada = cuentaSeleccionada;
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
	 * Obtiene el campo consultaMovimientosService
	 * 
	 * @return consultaMovimientosService
	 */
	public ConsultaMovimientosEfectivoService getConsultaMovimientosService() {
		return consultaMovimientosService;
	}

	/**
	 * Asigna el valor del campo consultaMovimientosService
	 * 
	 * @param consultaMovimientosService
	 *            el valor de consultaMovimientosService a asignar
	 */
	public void setConsultaMovimientosService(ConsultaMovimientosEfectivoService consultaMovimientosService) {
		this.consultaMovimientosService = consultaMovimientosService;
	}

	/**
	 * Obtiene el valor del atributo criterioCuentaValoresContraparte
	 * 
	 * @return el valor del atributo criterioCuentaValoresContraparte
	 */
	public CuentaDTO getCriterioCuentaValoresContraparte() {
		return criterioCuentaValoresContraparte;
	}

	/**
	 * Establece el valor del atributo criterioCuentaValoresContraparte
	 * 
	 * @param criterioCuentaValoresContraparte
	 *            el valor del atributo criterioCuentaValoresContraparte a
	 *            establecer
	 */
	public void setCriterioCuentaValoresContraparte(CuentaDTO criterioCuentaValoresContraparte) {
		this.criterioCuentaValoresContraparte = criterioCuentaValoresContraparte;
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
	 * Establece el valor del atributo ordenarPorTipoDeInstruccion
	 * 
	 * @param ordenarPorTipoDeInstruccion
	 *            el valor del atributo ordenarPorTipoDeInstruccion a establecer
	 */
	public void setOrdenarPorTipoDeInstruccion(boolean ordenarPorTipoDeInstruccion) {
		this.ordenarPorTipoDeInstruccion = ordenarPorTipoDeInstruccion;
	}

	/**
	 * @param importe the importe to set
	 */
	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	/**
	 * @return the importe
	 */
	public BigDecimal getImporte() {
		return importe;
	}

	/**
	 * @param tipoRetiro the tipoRetiro to set
	 */
	public void setTipoRetiro(String tipoRetiro) {
		this.tipoRetiro = tipoRetiro;
	}

	/**
	 * @return the tipoRetiro
	 */
	public String getTipoRetiro() {
		return tipoRetiro;
	}

	/**
	 * @return the tipoRetiro
	 */
	public String getTipoRetiroDescripcion() {
		if(StringUtils.isNotBlank(tipoRetiro)) {
			if( tipoRetiro.equalsIgnoreCase("-1") ) {
				return "TODOS";
			} else {
				return tipoRetiro;
			}
		}
		return "";
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

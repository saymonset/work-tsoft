/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 */
package com.indeval.portaldali.middleware.services.estadocuenta;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.CriterioConsultaMovimientosEfectivoDTO;
import com.indeval.portaldali.middleware.dto.DetalleEstadoCuentaSaldoPorBovedaDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.EstadoCuentaEfectivoPorDivisaDTO;
import com.indeval.portaldali.middleware.dto.RegistroContableSaldoControladaDTO;
import com.indeval.portaldali.middleware.dto.RegistroContableSaldoNombradaDTO;
import com.indeval.portaldali.middleware.dto.SaldoEfectivoDTO;
import com.indeval.portaldali.middleware.services.common.constants.CommonConstants;
import com.indeval.portaldali.middleware.services.common.constants.TipoAccionConstants;
import com.indeval.portaldali.middleware.services.common.constants.TipoCargoAValores;
import com.indeval.portaldali.middleware.services.common.constants.TipoCuentaConstants;
import com.indeval.portaldali.persistence.dao.estadocuenta.MovimientosEfectivoDAOV2;
import com.indeval.portaldali.persistence.model.TipoAccion;
import com.indeval.portaldali.persistence.util.DateUtil;

/**
 * Implementa la funcionalidad necesaria para realizar la consulta de
 * movimientos de efetivo ya sea controlado o nombrado.
 * 
 * @author Emigdio Hernández Rodríguez
 * @version 1.0
 */
public class ConsultaMovimientosEfectivoV2ServiceImpl implements ConsultaMovimientosEfectivoService {
	/**
	 * Objeto de acceso a los datos de los registros contables de efectivo.
	 */
	private MovimientosEfectivoDAOV2 movimientosEfectivoDAO = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaMovimientosEfectivoService#buscarCuentasDeRegistrosContablesDeEfectivo(com.indeval.estadocuenta.core.application.dto.CriterioConsultaMovimientosEfectivoDTO)
	 */
	public List<Long> buscarCuentasDeRegistrosContablesDeEfectivo(CriterioConsultaMovimientosEfectivoDTO criterio) {
		List<Long> resultados = null;

		if (DateUtils.isSameDay(criterio.getFechaFinal(), new Date())) {
			if (TipoCuentaConstants.TIPO_CONTROLADA.equals(criterio.getCuenta().getTipoCuenta().getId())) {
				resultados = movimientosEfectivoDAO.buscarCuentasDeRegistrosContablesDeEfectivoControlada(criterio);
			} else {
				resultados = movimientosEfectivoDAO.buscarCuentasDeRegistrosContablesDeEfectivoNombrada(criterio);
			}
		} else {
			if (TipoCuentaConstants.TIPO_CONTROLADA.equals(criterio.getCuenta().getTipoCuenta().getId())) {
				resultados = movimientosEfectivoDAO.buscarCuentasDeRegistrosContablesDeEfectivoControladaHistorico(criterio);
			} else {
				resultados = movimientosEfectivoDAO.buscarCuentasDeRegistrosContablesDeEfectivoNombradaHistorico(criterio);
			}
		}

		return resultados;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaMovimientosEfectivoService#buscarDivisasDeRegistrosContablesDeEfectivo(com.indeval.estadocuenta.core.application.dto.CriterioConsultaMovimientosEfectivoDTO)
	 */
	public List<Long> buscarDivisasDeRegistrosContablesDeEfectivo(CriterioConsultaMovimientosEfectivoDTO criterio) {
		List<Long> resultados =  new ArrayList<Long>();

		if (DateUtils.isSameDay(criterio.getFechaFinal(), new Date())) {
			if (TipoCuentaConstants.TIPO_CONTROLADA.equals(criterio.getCuenta().getTipoCuenta().getId())) {
				resultados = movimientosEfectivoDAO.buscarDivisasDeRegistrosContablesDeEfectivoControlada(criterio);
			}
			else {
				resultados = movimientosEfectivoDAO.buscarDivisasDeRegistrosContablesDeEfectivoNombrada(criterio);
			}
		}
		else {
			if (TipoCuentaConstants.TIPO_CONTROLADA.equals(criterio.getCuenta().getTipoCuenta().getId())) {
				resultados = movimientosEfectivoDAO.buscarDivisasDeRegistrosContablesDeEfectivoControladaHistorico(criterio);
			}
			else {
				resultados = movimientosEfectivoDAO.buscarDivisasDeRegistrosContablesDeEfectivoNombradaHistorico(criterio);
			}
		}

//		if( criterio.getDivisa() != null && criterio.getDivisa().getId() > 0) {
//			resultados.add(criterio.getDivisa().getId());
//			} else {
//			List<BigInteger> ids = movimientosEfectivoDAO.buscarDivisas();
//			
//			if( ids != null && ids.size() > 0 ) {
//				for( BigInteger id : ids ) {
//					resultados.add(id.longValue());
//			}
//			}
//		}
		
		return resultados;
	}

	public long obtenerProyeccionDeRegistrosContablesDeMovimientosDeEfectivo(CriterioConsultaMovimientosEfectivoDTO criterio) {
		long resultado = 0;

		if (DateUtils.isSameDay(criterio.getFechaFinal(), new Date())) {
			if (TipoCuentaConstants.TIPO_CONTROLADA.equals(criterio.getCuenta().getTipoCuenta().getId())) {
				resultado += movimientosEfectivoDAO.obtenerProyeccionDeRegistrosContablesDeSaldosControlada(criterio);
			}
			else {
				resultado += movimientosEfectivoDAO.obtenerProyeccionDeRegistrosContablesDeSaldosNombrada(criterio);
			}
		}

		if (DateUtils.truncate(criterio.getFechaInicial(), Calendar.DATE).before(DateUtils.truncate(new Date(), Calendar.DATE))) {
			if (TipoCuentaConstants.TIPO_CONTROLADA.equals(criterio.getCuenta().getTipoCuenta().getId())) {
				resultado += movimientosEfectivoDAO.obtenerProyeccionDeRegistrosContablesDeSaldosControladaHistorico(criterio);
			}
			else {
				resultado += movimientosEfectivoDAO.obtenerProyeccionDeRegistrosContablesDeSaldosNombradaHistorico(criterio);
			}
		}
		
		return resultado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaMovimientosEfectivoService#consultarMovimientosDeEfectivo(com.indeval.estadocuenta.core.application.dto.SaldoEfectivoDTO,
	 *      java.util.Set)
	 */
	public List<EstadoCuentaEfectivoPorDivisaDTO> consultarMovimientosDeEfectivo(CriterioConsultaMovimientosEfectivoDTO criterio, List<Long> idsDivisas, Boolean debeDejarLog) {
		List<EstadoCuentaEfectivoPorDivisaDTO> resultados = null;
		if (TipoCuentaConstants.TIPO_CONTROLADA.equals(criterio.getCuenta().getTipoCuenta().getId())) {
			resultados = consultarMovimientosDeEfectivoDeCuentasContrloladas(criterio, idsDivisas);
		}
		else {
			resultados = consultarMovimientosDeEfectivoDeCuentasNombradas(criterio, idsDivisas);
		}
		return resultados;
	}

	/**
	 * Crea una estructura de objetos de transferencia de datos que representan
	 * los movimientos de efectivo organizados por divisa y por bóveda.
	 * 
	 * @param criterio
	 *            Criterio para ejecutar la consulta de registro contables.
	 * @param idsDivisas
	 *            Conjunto de divisas para las que se realizar la consulta.
	 * @return Lista de registros contables de movimientos de efectivo
	 *         organizados por divisa y bóveda.
	 */
	private List<EstadoCuentaEfectivoPorDivisaDTO> consultarMovimientosDeEfectivoDeCuentasContrloladas(CriterioConsultaMovimientosEfectivoDTO criterio,
			List<Long> idsDivisas) {

		List<EstadoCuentaEfectivoPorDivisaDTO> detalleEstadoCuenta = new ArrayList<EstadoCuentaEfectivoPorDivisaDTO>();
		if (idsDivisas != null && idsDivisas.size() > 0) {
			
			List<RegistroContableSaldoControladaDTO> registrosContables = new ArrayList<RegistroContableSaldoControladaDTO>();

			if (DateUtils.isSameDay(criterio.getFechaFinal(), new Date())) {
				registrosContables.addAll(movimientosEfectivoDAO.buscarRegistrosContablesDeEfectivoControlada(criterio, idsDivisas));
			}

			if (DateUtil.comparaFechasDias(criterio.getFechaInicial(), new Date()) < 0) {
				registrosContables.addAll(movimientosEfectivoDAO.buscarRegistrosContablesDeEfectivoControladaHistorico(criterio, idsDivisas));

				Collections.sort(registrosContables, new Comparator<RegistroContableSaldoControladaDTO>() {

					public int compare(RegistroContableSaldoControladaDTO o1, RegistroContableSaldoControladaDTO o2) {

						return (String.valueOf(o1.getSaldoEfectivo().getDivisa().getId()) + String.valueOf(o1.getSaldoEfectivo().getBoveda().getId()) + String
								.valueOf(String.valueOf(o1.getSaldoEfectivo().getCuenta().getIdCuenta())))
								.compareTo(String.valueOf(o2.getSaldoEfectivo().getDivisa().getId()) + String.valueOf(o2.getSaldoEfectivo().getBoveda().getId())
										+ String.valueOf(String.valueOf(o2.getSaldoEfectivo().getCuenta().getIdCuenta())));
					}

				});
			}

			DivisaDTO divisa = null;
			BovedaDTO boveda = null;
			EstadoCuentaEfectivoPorDivisaDTO detalleDivisa = null;
			DetalleEstadoCuentaSaldoPorBovedaDTO detalleBoveda = null;

			BigDecimal saldoTotal = new BigDecimal(0.0);

			RegistroContableSaldoControladaDTO registro = null;
			Iterator<RegistroContableSaldoControladaDTO> itRegistros = registrosContables.iterator();

			if (itRegistros.hasNext()) {
				registro = itRegistros.next();
			}

			while (registro != null) {
				SaldoEfectivoDTO saldo = registro.getSaldoEfectivo();

				DivisaDTO divisaActual = saldo.getDivisa();
				BovedaDTO bovedaActual = saldo.getBoveda();

				if (divisa == null || divisa.getId() != divisaActual.getId()) {

					if (divisa != null) {
						detalleDivisa.getRegistrosContablesPorBoveda().add(detalleBoveda);
						detalleEstadoCuenta.add(detalleDivisa);
						detalleBoveda = null;
						boveda = null;
					}

					divisa = divisaActual;
					detalleDivisa = new EstadoCuentaEfectivoPorDivisaDTO();
					detalleDivisa.setDivisa(divisa);
				}

				if (boveda == null || boveda.getId() != bovedaActual.getId()) {

					if (boveda != null) {
						detalleDivisa.getRegistrosContablesPorBoveda().add(detalleBoveda);
					}

					boveda = bovedaActual;
					detalleBoveda = new DetalleEstadoCuentaSaldoPorBovedaDTO();
					detalleBoveda.setBoveda(boveda);

				}

				if (detalleDivisa != null && detalleBoveda != null) {

					String descripcionRegistro = null;

					while (registro != null && registro.getSaldoEfectivo().getDivisa().getId() == divisa.getId()
							&& registro.getSaldoEfectivo().getBoveda().getId() == boveda.getId()) {

						RegistroContableSaldoControladaDTO registroContable = registro;

						if (registroContable.getTipoAccion().getId() == TipoAccion.ABONO_PASIVO_VALUE
								|| registroContable.getTipoAccion().getId() == TipoAccion.CARGO_ACTIVO_VALUE) {
							descripcionRegistro = TipoAccionConstants.TIPO_ACCION_RECEPCION_SALDO + CommonConstants.SUFIJO_DESCRIPCION_OPERACIONES_CONTROLADA;
							;

							registroContable.setCantidadRecepcion(registroContable.getCantidad());
						} else {
							descripcionRegistro = TipoAccionConstants.TIPO_ACCION_ENTREGA_SALDO + CommonConstants.SUFIJO_DESCRIPCION_OPERACIONES_CONTROLADA;
							;

							registroContable.setCantidadEntrega(registroContable.getCantidad());
						}

						registroContable.setSaldoTotal(saldoTotal);
						registroContable.setDescripcion(descripcionRegistro);

						detalleBoveda.getRegistrosContablesControladas().add(registroContable);

						if (itRegistros.hasNext()) {
							registro = itRegistros.next();
						} else {
							registro = null;
						}
					}
				}
			}

			if (detalleDivisa != null && detalleBoveda != null) {
				if (divisa != null && boveda != null) {
					detalleDivisa.getRegistrosContablesPorBoveda().add(detalleBoveda);
					detalleEstadoCuenta.add(detalleDivisa);
				}
			}


			//Se colocan a null las listas
			registrosContables = null;
		}
		return detalleEstadoCuenta;

	}

	/**
	 * Crea una estructura de objetos de transferencia de datos que representan
	 * los movimientos de efectivo organizados por divisa y por bóveda.
	 * 
	 * @param criterio
	 *            Criterio para ejecutar la consulta de registro contables.
	 * @param idsDivisas
	 *            Conjunto de divisas para las que se realizar la consulta.
	 * @return Lista de registros contables de movimientos de efectivo
	 *         organizados por divisa y bóveda.
	 */
	private List<EstadoCuentaEfectivoPorDivisaDTO> consultarMovimientosDeEfectivoDeCuentasNombradas(CriterioConsultaMovimientosEfectivoDTO criterio,
			List<Long> idsDivisas) {

		List<EstadoCuentaEfectivoPorDivisaDTO> detalleEstadoCuenta = new ArrayList<EstadoCuentaEfectivoPorDivisaDTO>();
		if (idsDivisas != null && idsDivisas.size() > 0) {
			
			List<RegistroContableSaldoNombradaDTO> registrosContables = new ArrayList<RegistroContableSaldoNombradaDTO>();

			if (DateUtils.isSameDay(criterio.getFechaFinal(), new Date())) {

				registrosContables.addAll(movimientosEfectivoDAO.buscarRegistrosContablesDeEfectivoNombrada(criterio, idsDivisas));
			}

			if (DateUtil.comparaFechasDias(criterio.getFechaInicial(), new Date()) < 0) {
				registrosContables.addAll(movimientosEfectivoDAO.buscarRegistrosContablesDeEfectivoNombradaHistorico(criterio, idsDivisas));

				Collections.sort(registrosContables, new Comparator<RegistroContableSaldoNombradaDTO>() {
					public int compare(RegistroContableSaldoNombradaDTO o1, RegistroContableSaldoNombradaDTO o2) {
						return (
							String.valueOf(o1.getSaldoEfectivo().getDivisa().getId()) + 
							String.valueOf(o1.getSaldoEfectivo().getBoveda().getId()) + 
							String.valueOf(String.valueOf(o1.getSaldoEfectivo().getCuenta().getIdCuenta()))).compareTo(
								String.valueOf(o2.getSaldoEfectivo().getDivisa().getId()) + 
								String.valueOf(o2.getSaldoEfectivo().getBoveda().getId()) + 
								String.valueOf(String.valueOf(o2.getSaldoEfectivo().getCuenta().getIdCuenta())));
					}
				});
			}

			DivisaDTO divisa = null;
			BovedaDTO boveda = null;
			EstadoCuentaEfectivoPorDivisaDTO detalleDivisa = null;
			DetalleEstadoCuentaSaldoPorBovedaDTO detalleBoveda = null;
			
			BigDecimal saldoDisponible = new BigDecimal(0.0);
			BigDecimal saldoNoDisponible = new BigDecimal(0.0);
			RegistroContableSaldoNombradaDTO registro = null;
			Iterator<RegistroContableSaldoNombradaDTO> itRegistros = registrosContables.iterator();

			if (itRegistros.hasNext()) {
				registro = itRegistros.next();
			}

			while (registro != null) {

				SaldoEfectivoDTO saldo = registro.getSaldoEfectivo();

				DivisaDTO divisaActual = saldo.getDivisa();
				BovedaDTO bovedaActual = saldo.getBoveda();

				if (divisa == null || divisa.getId() != divisaActual.getId()) {

					if (divisa != null) {
						detalleDivisa.getRegistrosContablesPorBoveda().add(detalleBoveda);
						detalleEstadoCuenta.add(detalleDivisa);
						detalleBoveda = null;
						boveda = null;
					}

					divisa = divisaActual;
					detalleDivisa = new EstadoCuentaEfectivoPorDivisaDTO();
					detalleDivisa.setDivisa(divisa);
				}

				if (boveda == null || boveda.getId() != bovedaActual.getId()) {

					if (boveda != null) {
						detalleDivisa.getRegistrosContablesPorBoveda().add(detalleBoveda);
					}

					boveda = bovedaActual;
					detalleBoveda = new DetalleEstadoCuentaSaldoPorBovedaDTO();
					detalleBoveda.setBoveda(boveda);

				}

				if (detalleDivisa != null && detalleBoveda != null) {

					String descripcionRegistro = "";
					String descripcionContraparte = "";
					String cuentaContraparte = "";

					while (registro != null && registro.getSaldoEfectivo().getDivisa().getId() == divisa.getId()
							&& registro.getSaldoEfectivo().getBoveda().getId() == boveda.getId()) {

						RegistroContableSaldoNombradaDTO registroContable = registro;
												
						if (registro != null && 
								registro.getCargoA() == TipoCargoAValores.CARGO_AFECTA_DISPONIBLE.longValue()) {
							if (registroContable.getTipoAccion().getId() == TipoAccion.ABONO_PASIVO_VALUE
									|| registroContable.getTipoAccion().getId() == TipoAccion.CARGO_ACTIVO_VALUE) {
								descripcionRegistro = TipoAccionConstants.TIPO_ACCION_RECEPCION_SALDO;
								saldoDisponible = saldoDisponible.add(registroContable.getCantidad());

								registroContable.setCantidadRecepcion(registroContable.getCantidad());
							} else {
								descripcionRegistro = TipoAccionConstants.TIPO_ACCION_ENTREGA_SALDO;
								saldoDisponible = saldoDisponible.subtract(registroContable.getCantidad());

								registroContable.setCantidadEntrega(registroContable.getCantidad());
							}
						} else {
							if (registroContable.getTipoAccion().getId() == TipoAccion.ABONO_PASIVO_VALUE
									|| registroContable.getTipoAccion().getId() == TipoAccion.CARGO_ACTIVO_VALUE) {
								descripcionRegistro = TipoAccionConstants.TIPO_ACCION_RECEPCION_SALDO;
								saldoNoDisponible = saldoNoDisponible.add(registroContable.getCantidad());

								registroContable.setCantidadRecepcion(registroContable.getCantidad());
							} else {
								descripcionRegistro = TipoAccionConstants.TIPO_ACCION_ENTREGA_SALDO;
								saldoNoDisponible = saldoNoDisponible.subtract(registroContable.getCantidad());

								registroContable.setCantidadEntrega(registroContable.getCantidad());
							}
						}

						if (registro.getOperacion().getCuentaEfectivoReceptor() == null
								|| registro.getOperacion().getCuentaEfectivoTraspasante() == null) {
							if(registro.getOperacion().getCuentaEfectivoReceptor() != null) {
								descripcionContraparte = registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion().getNombreCorto();
								cuentaContraparte = 
									"(" + registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion().getClaveTipoInstitucion() + 
									"-" + 
									registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion().getFolioInstitucion() + 
									"-" + 
									registro.getOperacion().getCuentaEfectivoReceptor().getCuenta() + 
									")";
								registroContable.setInstitucionContraparte(registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion());
								registroContable.setFolioInstitucionContraparte(registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion().getFolioInstitucion());
								registroContable.setClaveTipoInstitucionContraparte(registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion().getClaveTipoInstitucion());
								registroContable.setNumeroCuentaContraparte(registro.getOperacion().getCuentaEfectivoReceptor().getCuenta());
								registroContable.setFolioInstitucionParticipante(registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion().getFolioInstitucion());
								registroContable.setClaveTipoInstitucionParticipante(registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion().getClaveTipoInstitucion());
								registroContable.setNumeroCuentaParticipante(registro.getOperacion().getCuentaEfectivoReceptor().getCuenta());
							} else {
								descripcionContraparte = registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion().getNombreCorto();
								cuentaContraparte = "(" + registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion().getClaveTipoInstitucion() + "-"
									+ registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion().getFolioInstitucion() + "-"
									+ registro.getOperacion().getCuentaEfectivoTraspasante().getCuenta() + ")";
								registroContable.setInstitucionContraparte(registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion());
								registroContable.setFolioInstitucionContraparte(registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion().getFolioInstitucion());
								registroContable.setClaveTipoInstitucionContraparte(registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion()
										.getClaveTipoInstitucion());
								registroContable.setNumeroCuentaContraparte(registro.getOperacion().getCuentaEfectivoTraspasante().getCuenta());
								registroContable.setFolioInstitucionParticipante(registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion().getFolioInstitucion());
								registroContable.setClaveTipoInstitucionParticipante(registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion()
										.getClaveTipoInstitucion());
								registroContable.setNumeroCuentaParticipante(registro.getOperacion().getCuentaEfectivoTraspasante().getCuenta());
							}
							
						} else {
							if (registro.getSaldoEfectivo().getCuenta().getIdCuenta() !=
									registro.getOperacion().getCuentaEfectivoReceptor().getIdCuenta()) {
								descripcionContraparte = registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion().getNombreCorto();
								cuentaContraparte = "(" + registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion().getClaveTipoInstitucion() + "-"
									+ registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion().getFolioInstitucion() + "-"
									+ registro.getOperacion().getCuentaEfectivoReceptor().getCuenta() + ")";
								registroContable.setInstitucionContraparte(registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion());
								registroContable.setFolioInstitucionContraparte(registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion().getFolioInstitucion());
								registroContable.setClaveTipoInstitucionContraparte(registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion().getClaveTipoInstitucion());
								registroContable.setNumeroCuentaContraparte(registro.getOperacion().getCuentaEfectivoReceptor().getCuenta());
								registroContable.setFolioInstitucionParticipante(registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion().getFolioInstitucion());
								registroContable.setClaveTipoInstitucionParticipante(registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion().getClaveTipoInstitucion());
								registroContable.setNumeroCuentaParticipante(registro.getOperacion().getCuentaEfectivoTraspasante().getCuenta());
							} else {
								descripcionContraparte = registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion().getNombreCorto();
								cuentaContraparte = "(" + registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion().getClaveTipoInstitucion() + "-"
									+ registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion().getFolioInstitucion() + "-"
									+ registro.getOperacion().getCuentaEfectivoTraspasante().getCuenta() + ")";
								registroContable.setInstitucionContraparte(registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion());
								registroContable.setFolioInstitucionContraparte(registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion().getFolioInstitucion());
								registroContable.setClaveTipoInstitucionContraparte(registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion().getClaveTipoInstitucion());
								registroContable.setNumeroCuentaContraparte(registro.getOperacion().getCuentaEfectivoTraspasante().getCuenta());
								registroContable.setFolioInstitucionParticipante(registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion().getFolioInstitucion());
								registroContable.setClaveTipoInstitucionParticipante(registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion().getClaveTipoInstitucion());
								registroContable.setNumeroCuentaParticipante(registro.getOperacion().getCuentaEfectivoReceptor().getCuenta());
							}
						}

						descripcionRegistro += CommonConstants.PREPOSICION_POR + registro.getOperacion().getTipoOperacion().getDescripcion();
						registroContable.setDescripcion(descripcionRegistro);
						registroContable.setDescripcionLarga(descripcionRegistro + CommonConstants.PREPOSICION_POR
								+ registro.getOperacion().getInstruccionLiquidacion().getTipoInstruccion().getInstruccion());

						registroContable.setContraparte(descripcionContraparte);
						registroContable.setCuentaContraparte(cuentaContraparte);
						registroContable.setSaldoDisponible(saldoDisponible);
						registroContable.setSaldoNoDisponible(saldoNoDisponible);

						detalleBoveda.getRegistrosContablesNombradas().add(registroContable);

						if (itRegistros.hasNext()) {
							registro = itRegistros.next();
						} else {
							registro = null;
						}
					}
				}
			}

			if (detalleDivisa != null && detalleBoveda != null) {
				if (divisa != null && boveda != null) {
					detalleDivisa.getRegistrosContablesPorBoveda().add(detalleBoveda);
					detalleEstadoCuenta.add(detalleDivisa);
				}
			}


			//Se colocan a null las listas
			registrosContables = null;
		}
		return detalleEstadoCuenta;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.estadocuenta.ConsultaMovimientosEfectivoService#consultarMovimientosDeEfectivoControladasSinAgrupar(com.indeval.portaldali.middleware.dto.CriterioConsultaMovimientosEfectivoDTO,
	 *      java.util.List)
	 */
	public List<RegistroContableSaldoControladaDTO> consultarMovimientosDeEfectivoControladasSinAgrupar(CriterioConsultaMovimientosEfectivoDTO criterio,
			List<Long> idsDivisas) {

		List<RegistroContableSaldoControladaDTO> resultados = new ArrayList<RegistroContableSaldoControladaDTO>();

		if (idsDivisas != null && idsDivisas.size() > 0) {

			List<RegistroContableSaldoControladaDTO> registrosContables = new ArrayList<RegistroContableSaldoControladaDTO>();

			if (DateUtils.isSameDay(criterio.getFechaFinal(), new Date())) {
				registrosContables.addAll(movimientosEfectivoDAO.buscarRegistrosContablesDeEfectivoControlada(criterio, idsDivisas));
			}

			if (DateUtil.comparaFechasDias(criterio.getFechaInicial(), new Date()) < 0) {
				registrosContables.addAll(movimientosEfectivoDAO.buscarRegistrosContablesDeEfectivoControladaHistorico(criterio, idsDivisas));

				Collections.sort(registrosContables, new Comparator<RegistroContableSaldoControladaDTO>() {

					public int compare(RegistroContableSaldoControladaDTO o1, RegistroContableSaldoControladaDTO o2) {

						return (String.valueOf(o1.getSaldoEfectivo().getDivisa().getId()) + String.valueOf(o1.getSaldoEfectivo().getBoveda().getId()) + String
								.valueOf(String.valueOf(o1.getSaldoEfectivo().getCuenta().getIdCuenta())))
								.compareTo(String.valueOf(o2.getSaldoEfectivo().getDivisa().getId()) + String.valueOf(o2.getSaldoEfectivo().getBoveda().getId())
										+ String.valueOf(String.valueOf(o2.getSaldoEfectivo().getCuenta().getIdCuenta())));
					}

				});
			}

			BigDecimal saldoTotal = new BigDecimal(0.0);

			RegistroContableSaldoControladaDTO registro = null;
			Iterator<RegistroContableSaldoControladaDTO> itRegistros = registrosContables.iterator();

			if (itRegistros.hasNext()) {
				registro = itRegistros.next();
			}

			while (registro != null) {

				String descripcionRegistro = null;

				RegistroContableSaldoControladaDTO registroContable = registro;

				if (registroContable.getTipoAccion().getId() == TipoAccion.ABONO_PASIVO_VALUE
						|| registroContable.getTipoAccion().getId() == TipoAccion.CARGO_ACTIVO_VALUE) {
					descripcionRegistro = TipoAccionConstants.TIPO_ACCION_RECEPCION_SALDO + CommonConstants.SUFIJO_DESCRIPCION_OPERACIONES_CONTROLADA;
					registroContable.setCantidadRecepcion(registroContable.getCantidad());
				} else {
					descripcionRegistro = TipoAccionConstants.TIPO_ACCION_ENTREGA_SALDO + CommonConstants.SUFIJO_DESCRIPCION_OPERACIONES_CONTROLADA;
					registroContable.setCantidadEntrega(registroContable.getCantidad());
				}

				registroContable.setSaldoTotal(saldoTotal);
				registroContable.setDescripcion(descripcionRegistro);

				resultados.add(registroContable);

				if (itRegistros.hasNext()) {
					registro = itRegistros.next();
				} else {
					registro = null;
				}

			}

			//Coloca a null las listas
			registrosContables = null;
		}

		return resultados;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.estadocuenta.ConsultaMovimientosEfectivoService#consultarMovimientosDeEfectivoNombradasSinAgrupar(com.indeval.portaldali.middleware.dto.CriterioConsultaMovimientosEfectivoDTO,
	 *      java.util.List)
	 */
	public List<RegistroContableSaldoNombradaDTO> consultarMovimientosDeEfectivoNombradasSinAgrupar(CriterioConsultaMovimientosEfectivoDTO criterio,
			List<Long> idsDivisas) {

		List<RegistroContableSaldoNombradaDTO> resultados = new ArrayList<RegistroContableSaldoNombradaDTO>();

		if (idsDivisas != null && idsDivisas.size() > 0) {

			List<RegistroContableSaldoNombradaDTO> registrosContables = new ArrayList<RegistroContableSaldoNombradaDTO>();

			if (DateUtils.isSameDay(criterio.getFechaFinal(), new Date())) {

				registrosContables.addAll(movimientosEfectivoDAO.buscarRegistrosContablesDeEfectivoNombrada(criterio, idsDivisas));
			}

			if (DateUtil.comparaFechasDias(criterio.getFechaInicial(), new Date()) < 0) {
				registrosContables.addAll(movimientosEfectivoDAO.buscarRegistrosContablesDeEfectivoNombradaHistorico(criterio, idsDivisas));

				Collections.sort(registrosContables, new Comparator<RegistroContableSaldoNombradaDTO>() {

					public int compare(RegistroContableSaldoNombradaDTO o1, RegistroContableSaldoNombradaDTO o2) {

						return (String.valueOf(o1.getSaldoEfectivo().getDivisa().getId()) + String.valueOf(o1.getSaldoEfectivo().getBoveda().getId()) + String
								.valueOf(String.valueOf(o1.getSaldoEfectivo().getCuenta().getIdCuenta())))
								.compareTo(String.valueOf(o2.getSaldoEfectivo().getDivisa().getId()) + String.valueOf(o2.getSaldoEfectivo().getBoveda().getId())
										+ String.valueOf(String.valueOf(o2.getSaldoEfectivo().getCuenta().getIdCuenta())));
					}

				});
			}
			
			BigDecimal saldoDisponible = new BigDecimal("0");
			BigDecimal saldoNoDisponible = new BigDecimal("0");
			
			RegistroContableSaldoNombradaDTO registro = null;
			Iterator<RegistroContableSaldoNombradaDTO> itRegistros = registrosContables.iterator();

			if (itRegistros.hasNext()) {
				registro = itRegistros.next();
			}
			
			while (registro != null) {

				String descripcionRegistro = "";
				String descripcionContraparte = "";
				String cuentaContraparte = "";

				RegistroContableSaldoNombradaDTO registroContable = registro;
								
				if (registro != null && 
						registro.getCargoA() == TipoCargoAValores.CARGO_AFECTA_DISPONIBLE.longValue()) {
					if (registroContable.getTipoAccion().getId() == TipoAccion.ABONO_PASIVO_VALUE
							|| registroContable.getTipoAccion().getId() == TipoAccion.CARGO_ACTIVO_VALUE) {
						descripcionRegistro = TipoAccionConstants.TIPO_ACCION_RECEPCION_SALDO;
						saldoDisponible = saldoDisponible.add(registroContable.getCantidad());

						registroContable.setCantidadRecepcion(registroContable.getCantidad());
					} else {
						descripcionRegistro = TipoAccionConstants.TIPO_ACCION_ENTREGA_SALDO;
						saldoDisponible = saldoDisponible.subtract(registroContable.getCantidad());

						registroContable.setCantidadEntrega(registroContable.getCantidad());
					}
				} else {
					if (registroContable.getTipoAccion().getId() == TipoAccion.ABONO_PASIVO_VALUE
							|| registroContable.getTipoAccion().getId() == TipoAccion.CARGO_ACTIVO_VALUE) {
						descripcionRegistro = TipoAccionConstants.TIPO_ACCION_RECEPCION_SALDO;
						saldoNoDisponible = saldoNoDisponible.add(registroContable.getCantidad());

						registroContable.setCantidadRecepcion(registroContable.getCantidad());
					} else {
						descripcionRegistro = TipoAccionConstants.TIPO_ACCION_ENTREGA_SALDO;
						saldoNoDisponible = saldoNoDisponible.subtract(registroContable.getCantidad());

						registroContable.setCantidadEntrega(registroContable.getCantidad());
					}
				}

				if (registro.getOperacion().getCuentaEfectivoReceptor() == null
						|| registro.getOperacion().getCuentaEfectivoTraspasante() == null) {
					if(registro.getOperacion().getCuentaEfectivoReceptor() != null) {
						descripcionContraparte = registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion().getNombreCorto();
						cuentaContraparte = "(" + registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion().getClaveTipoInstitucion() + "-"
							+ registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion().getFolioInstitucion() + "-"
							+ registro.getOperacion().getCuentaEfectivoReceptor().getCuenta() + ")";
						registroContable.setInstitucionContraparte(registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion());
						registroContable.setFolioInstitucionContraparte(registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion().getFolioInstitucion());
						registroContable.setClaveTipoInstitucionContraparte(registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion().getClaveTipoInstitucion());
						registroContable.setNumeroCuentaContraparte(registro.getOperacion().getCuentaEfectivoReceptor().getCuenta());
						registroContable.setFolioInstitucionParticipante(registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion().getFolioInstitucion());
						registroContable.setClaveTipoInstitucionParticipante(registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion().getClaveTipoInstitucion());
						registroContable.setNumeroCuentaParticipante(registro.getOperacion().getCuentaEfectivoReceptor().getCuenta());
					} else {
						descripcionContraparte = registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion().getNombreCorto();
						cuentaContraparte = "(" + registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion().getClaveTipoInstitucion() + "-"
							+ registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion().getFolioInstitucion() + "-"
							+ registro.getOperacion().getCuentaEfectivoTraspasante().getCuenta() + ")";
						registroContable.setInstitucionContraparte(registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion());
						registroContable.setFolioInstitucionContraparte(registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion().getFolioInstitucion());
						registroContable.setClaveTipoInstitucionContraparte(registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion()
								.getClaveTipoInstitucion());
						registroContable.setNumeroCuentaContraparte(registro.getOperacion().getCuentaEfectivoTraspasante().getCuenta());
						registroContable.setFolioInstitucionParticipante(registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion().getFolioInstitucion());
						registroContable.setClaveTipoInstitucionParticipante(registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion()
								.getClaveTipoInstitucion());
						registroContable.setNumeroCuentaParticipante(registro.getOperacion().getCuentaEfectivoTraspasante().getCuenta());
					}
					
				} else {
					if (registro.getSaldoEfectivo().getCuenta().getIdCuenta() !=
							registro.getOperacion().getCuentaEfectivoReceptor().getIdCuenta()) {
						descripcionContraparte = registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion().getNombreCorto();
						cuentaContraparte = "(" + registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion().getClaveTipoInstitucion() + "-"
							+ registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion().getFolioInstitucion() + "-"
							+ registro.getOperacion().getCuentaEfectivoReceptor().getCuenta() + ")";
						registroContable.setInstitucionContraparte(registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion());
						registroContable.setFolioInstitucionContraparte(registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion().getFolioInstitucion());
						registroContable.setClaveTipoInstitucionContraparte(registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion().getClaveTipoInstitucion());
						registroContable.setNumeroCuentaContraparte(registro.getOperacion().getCuentaEfectivoReceptor().getCuenta());
						registroContable.setFolioInstitucionParticipante(registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion().getFolioInstitucion());
						registroContable.setClaveTipoInstitucionParticipante(registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion().getClaveTipoInstitucion());
						registroContable.setNumeroCuentaParticipante(registro.getOperacion().getCuentaEfectivoTraspasante().getCuenta());
					} else {
						descripcionContraparte = registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion().getNombreCorto();
						cuentaContraparte = "(" + registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion().getClaveTipoInstitucion() + "-"
							+ registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion().getFolioInstitucion() + "-"
							+ registro.getOperacion().getCuentaEfectivoTraspasante().getCuenta() + ")";
						registroContable.setInstitucionContraparte(registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion());
						registroContable.setFolioInstitucionContraparte(registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion().getFolioInstitucion());
						registroContable.setClaveTipoInstitucionContraparte(registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion().getClaveTipoInstitucion());
						registroContable.setNumeroCuentaContraparte(registro.getOperacion().getCuentaEfectivoTraspasante().getCuenta());
						registroContable.setFolioInstitucionParticipante(registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion().getFolioInstitucion());
						registroContable.setClaveTipoInstitucionParticipante(registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion().getClaveTipoInstitucion());
						registroContable.setNumeroCuentaParticipante(registro.getOperacion().getCuentaEfectivoReceptor().getCuenta());
					}
				}

				descripcionRegistro += CommonConstants.PREPOSICION_POR + registro.getOperacion().getTipoOperacion().getDescripcion();
				registroContable.setDescripcion(descripcionRegistro);
				registroContable.setDescripcionLarga(descripcionRegistro + CommonConstants.PREPOSICION_POR
						+ registro.getOperacion().getInstruccionLiquidacion().getTipoInstruccion().getInstruccion());

				registroContable.setContraparte(descripcionContraparte);
				registroContable.setCuentaContraparte(cuentaContraparte);
				registroContable.setSaldoDisponible(saldoDisponible);
				registroContable.setSaldoNoDisponible(saldoNoDisponible);

				resultados.add(registroContable);

				if (itRegistros.hasNext()) {
					registro = itRegistros.next();
				} else {
					registro = null;
				}

			}

			//Coloca a null las listas
			registrosContables = null;
		}
		return resultados;
	}

	/**
	 * Obtiene el campo movimientosEfectivoDAO
	 * 
	 * @return movimientosEfectivoDAO
	 */
	public MovimientosEfectivoDAOV2 getMovimientosEfectivoDAO() {
		return movimientosEfectivoDAO;
	}

	/**
	 * Asigna el valor del campo movimientosEfectivoDAO
	 * 
	 * @param movimientosEfectivoDAO
	 *            el valor de movimientosEfectivoDAO a asignar
	 */
	public void setMovimientosEfectivoDAO(MovimientosEfectivoDAOV2 movimientosEfectivoDAO) {
		this.movimientosEfectivoDAO = movimientosEfectivoDAO;
	}
}

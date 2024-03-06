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
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.time.DateUtils;

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.DetalleEstadoCuentaSaldoPorBovedaDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.EstadoCuentaEfectivoPorDivisaDTO;
import com.indeval.portaldali.middleware.dto.EstadoSaldoDTO;
import com.indeval.portaldali.middleware.dto.RegistroContableSaldoControladaDTO;
import com.indeval.portaldali.middleware.dto.RegistroContableSaldoNombradaDTO;
import com.indeval.portaldali.middleware.dto.SaldoEfectivoDTO;
import com.indeval.portaldali.middleware.services.common.constants.CommonConstants;
import com.indeval.portaldali.middleware.services.common.constants.TipoAccionConstants;
import com.indeval.portaldali.middleware.services.common.constants.TipoCargoAValores;
import com.indeval.portaldali.middleware.services.common.constants.TipoCuentaConstants;
import com.indeval.portaldali.persistence.dao.common.DiaInhabilDaliDao;
import com.indeval.portaldali.persistence.dao.estadocuenta.EstadoCuentaSaldosEfectivoDAOV2;
import com.indeval.portaldali.persistence.dao.estadocuenta.SaldoDAO;
import com.indeval.portaldali.persistence.model.TipoAccion;
import com.indeval.portaldali.persistence.util.DateUtil;

/**
 * Implementación de la interfaz del servicio que permite realizar la consulta
 * del estado de cuenta de los saldos de efectivo, controlados y nombrados.
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 */
public class ConsultaEstadoCuentaSaldoEfectivoV2ServiceImpl implements ConsultaEstadoCuentaSaldoEfectivoService {

	/**
	 * DAO para realizar las consultas a la base de datos de estados de cuenta
	 */
	private EstadoCuentaSaldosEfectivoDAOV2 estadoCuentaSaldosEfectivoDAO = null;

	/**
	 * DAO para realizar operaciones de consulta de saldos
	 */
	private SaldoDAO saldoDAO = null;

	/** DAO para obtener dias (in)habiles */
	private DiaInhabilDaliDao diaInhabilDao = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaEstadoCuentaSaldoEfectivoService#consultarEstadoDeCuentaSaldosControlados(com.indeval.estadocuenta.core.application.dto.SaldoEfectivoDTO,
	 *      java.util.List)
	 */
	public List<EstadoCuentaEfectivoPorDivisaDTO> consultarEstadoDeCuentaSaldosControlados(SaldoEfectivoDTO saldoEfectivo, List<Long> idsDivisas) {
		List<EstadoCuentaEfectivoPorDivisaDTO> detalleEstadoCuenta = new ArrayList<EstadoCuentaEfectivoPorDivisaDTO>();
		if (idsDivisas != null && idsDivisas.size() > 0) {
			List<SaldoEfectivoDTO> saldosIniciales = null;
			List<SaldoEfectivoDTO> saldosFinales = null;
			List<RegistroContableSaldoControladaDTO> registrosContables = new ArrayList<RegistroContableSaldoControladaDTO>();

			if (DateUtil.comparaFechasDias(saldoEfectivo.getFechaFinal(), new Date()) == 0) {
				saldosFinales = estadoCuentaSaldosEfectivoDAO.buscarSaldoControlado(saldoEfectivo, idsDivisas);
				registrosContables.addAll(estadoCuentaSaldosEfectivoDAO.buscarRegistrosContablesDeSaldosControladas(saldoEfectivo));
			} else {
				saldosFinales = estadoCuentaSaldosEfectivoDAO.buscarSaldoControladoHistorico(saldoEfectivo, idsDivisas, DateUtils.truncate(saldoEfectivo
						.getFechaFinal(), Calendar.DATE));
			}

			saldosIniciales = estadoCuentaSaldosEfectivoDAO.buscarSaldoControladoHistorico(saldoEfectivo, idsDivisas, DateUtils.truncate(diaInhabilDao
					.agregaDiasHabiles(saldoEfectivo.getFechaInicial(), -1), Calendar.DATE));

			if (DateUtil.comparaFechasDias(saldoEfectivo.getFechaInicial(), new Date()) < 0) {
				registrosContables.addAll(estadoCuentaSaldosEfectivoDAO.buscarRegistrosContablesDeSaldosControladasHistorico(saldoEfectivo));

				Collections.sort(registrosContables, new Comparator<RegistroContableSaldoControladaDTO>() {

					public int compare(RegistroContableSaldoControladaDTO o1, RegistroContableSaldoControladaDTO o2) {

						return (String.valueOf(o1.getSaldoEfectivo().getDivisa().getId()) + String.valueOf(o1.getSaldoEfectivo().getBoveda().getId()) + String
								.valueOf(String.valueOf(o1.getSaldoEfectivo().getCuenta().getIdCuenta()))).compareTo(String.valueOf(o2.getSaldoEfectivo()
								.getDivisa().getId())
								+ String.valueOf(o2.getSaldoEfectivo().getBoveda().getId())
								+ String.valueOf(String.valueOf(o2.getSaldoEfectivo().getCuenta().getIdCuenta())));
					}

				});
			}

			DivisaDTO divisa = null;
			BovedaDTO boveda = null;
			EstadoCuentaEfectivoPorDivisaDTO detalleDivisa = null;
			DetalleEstadoCuentaSaldoPorBovedaDTO detalleBoveda = null;
			RegistroContableSaldoControladaDTO registro = null;

			BigDecimal saldoTotal = new BigDecimal(0.0);

			Set<SaldoEfectivoDTO> saldos = new TreeSet<SaldoEfectivoDTO>(new Comparator<SaldoEfectivoDTO>() {
				public int compare(SaldoEfectivoDTO o1, SaldoEfectivoDTO o2) {
					return (String.valueOf(o1.getDivisa().getId()) + String.valueOf(o1.getBoveda().getId()) + String.valueOf(String.valueOf(o1.getCuenta()
							.getIdCuenta()))).compareTo(String.valueOf(o2.getDivisa().getId()) + String.valueOf(o2.getBoveda().getId())
							+ String.valueOf(String.valueOf(o2.getCuenta().getIdCuenta())));
				}
			});

			saldos.addAll(saldosIniciales);
			saldos.addAll(saldosFinales);

			Iterator<RegistroContableSaldoControladaDTO> itRegistros = registrosContables.iterator();

			for (Iterator<SaldoEfectivoDTO> itSaldos = saldos.iterator(); itSaldos.hasNext();) {

				SaldoEfectivoDTO saldo = itSaldos.next();

				DivisaDTO divisaActual = saldo.getDivisa();
				BovedaDTO bovedaActual = saldo.getBoveda();

				if (divisa == null || divisa.getId() != divisaActual.getId()) {

					if (divisa != null) {
						detalleBoveda = null;
						boveda = null;
					}

					divisa = divisaActual;
					detalleDivisa = new EstadoCuentaEfectivoPorDivisaDTO();
					detalleDivisa.setDivisa(divisa);
					detalleEstadoCuenta.add(detalleDivisa);
				}

				if (boveda == null || boveda.getId() != bovedaActual.getId()) {
					boveda = bovedaActual;
					detalleBoveda = new DetalleEstadoCuentaSaldoPorBovedaDTO();
					detalleBoveda.setBoveda(boveda);

					EstadoSaldoDTO estadoSaldoFinal = new EstadoSaldoDTO();
					EstadoSaldoDTO estadoSaldoInicial = new EstadoSaldoDTO();
					SaldoEfectivoDTO saldoFinal = null;
					SaldoEfectivoDTO saldoInicial = null;

					int indicePosFin = saldosFinales.indexOf(saldo);
					int indicePosIni = saldosIniciales.indexOf(saldo);
					if (indicePosFin != -1) {
						saldoFinal = saldosFinales.get(indicePosFin);
					}
					if (indicePosIni != -1) {
						saldoInicial = saldosIniciales.get(indicePosIni);
					}

					if (saldoInicial != null) {
						estadoSaldoInicial.setSaldoInicial(new BigDecimal(saldoInicial.getSaldo()));
					} else {
						estadoSaldoInicial.setSaldoInicial(new BigDecimal(0.0));
					}

					if (saldoFinal != null) {
						estadoSaldoFinal.setSaldoFinal(new BigDecimal(saldoFinal.getSaldo()));
					} else {
						estadoSaldoFinal.setSaldoFinal(new BigDecimal(0.0));
					}

					detalleBoveda.setEstadoSaldoFinal(estadoSaldoFinal);
					detalleBoveda.setEstadoSaldoInicial(estadoSaldoInicial);

					saldoTotal = detalleBoveda.getEstadoSaldoInicial().getSaldoInicial();
				}

				if (detalleDivisa != null && detalleBoveda != null) {

					String descripcionRegistro = null;

					if (registro == null && itRegistros.hasNext()) {
						registro = itRegistros.next();
					}

					while (registro != null && registro.getSaldoEfectivo().getDivisa().getId() == divisa.getId()
							&& registro.getSaldoEfectivo().getBoveda().getId() == boveda.getId()) {

						RegistroContableSaldoControladaDTO registroContable = registro;

						if (registroContable.getTipoAccion().getId() == TipoAccion.ABONO_PASIVO_VALUE
								|| registroContable.getTipoAccion().getId() == TipoAccion.CARGO_ACTIVO_VALUE) {
							descripcionRegistro = TipoAccionConstants.TIPO_ACCION_RECEPCION_SALDO + CommonConstants.SUFIJO_DESCRIPCION_OPERACIONES_CONTROLADA;
							saldoTotal = saldoTotal.add(registroContable.getCantidad());
							registroContable.setCantidadRecepcion(registroContable.getCantidad());
						} else {
							descripcionRegistro = TipoAccionConstants.TIPO_ACCION_ENTREGA_SALDO + CommonConstants.SUFIJO_DESCRIPCION_OPERACIONES_CONTROLADA;
							saldoTotal = saldoTotal.subtract(registroContable.getCantidad());
							registroContable.setCantidadEntrega(registroContable.getCantidad());
						}

						registroContable.setSaldoTotal(saldoTotal);
						registroContable.setDescripcion(descripcionRegistro);

						detalleBoveda.getRegistrosContablesControladas().add(registroContable);

						if (itRegistros.hasNext()) {
							registro = itRegistros.next();
						} 
						else {
							registro = null;
						}
					}
				}

				if (detalleDivisa != null && detalleBoveda != null) {
					if (divisa != null && boveda != null) {
						detalleDivisa.getRegistrosContablesPorBoveda().add(detalleBoveda);
					}
				}
			}

			//Se colocan a null las listas.
			saldosIniciales = null;
			saldosFinales = null;
			registrosContables = null;
		}
		return detalleEstadoCuenta;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaEstadoCuentaSaldoEfectivoService#consultarEstadoDeCuentaSaldosNombrada(com.indeval.estadocuenta.core.application.dto.SaldoEfectivoDTO,
	 *      java.util.List)
	 */
	public List<EstadoCuentaEfectivoPorDivisaDTO> consultarEstadoDeCuentaSaldosNombrada(SaldoEfectivoDTO saldoEfectivo, List<Long> idsDivisas) {
		List<EstadoCuentaEfectivoPorDivisaDTO> detalleEstadoCuenta = new ArrayList<EstadoCuentaEfectivoPorDivisaDTO>();
		if (idsDivisas != null && idsDivisas.size() > 0) {
			List<SaldoEfectivoDTO> saldosIniciales = null;
			List<SaldoEfectivoDTO> saldosFinales = null;
			List<RegistroContableSaldoNombradaDTO> registrosContables = new ArrayList<RegistroContableSaldoNombradaDTO>();

			if (DateUtil.comparaFechasDias(saldoEfectivo.getFechaFinal(), new Date()) == 0) {
				saldosFinales = estadoCuentaSaldosEfectivoDAO.buscarSaldoNombrado(saldoEfectivo, idsDivisas);
				registrosContables.addAll(estadoCuentaSaldosEfectivoDAO.buscarRegistrosContablesDeSaldosNombradas(saldoEfectivo));
			} else {
				saldosFinales = estadoCuentaSaldosEfectivoDAO.buscarSaldoNombradoHistorico(saldoEfectivo, idsDivisas, DateUtils.truncate(saldoEfectivo
						.getFechaFinal(), Calendar.DATE));
			}

			saldosIniciales = estadoCuentaSaldosEfectivoDAO.buscarSaldoNombradoHistorico(saldoEfectivo, idsDivisas, DateUtils.truncate(diaInhabilDao
					.agregaDiasHabiles(saldoEfectivo.getFechaInicial(), -1), Calendar.DATE));

			if (DateUtil.comparaFechasDias(saldoEfectivo.getFechaInicial(), new Date()) < 0) {
				registrosContables.addAll(estadoCuentaSaldosEfectivoDAO.buscarRegistrosContablesDeSaldosNombradasHistorico(saldoEfectivo));

				Collections.sort(registrosContables, new Comparator<RegistroContableSaldoNombradaDTO>() {

					public int compare(RegistroContableSaldoNombradaDTO o1, RegistroContableSaldoNombradaDTO o2) {

						return (String.valueOf(o1.getSaldoEfectivo().getDivisa().getId()) + String.valueOf(o1.getSaldoEfectivo().getBoveda().getId()) + String
								.valueOf(String.valueOf(o1.getSaldoEfectivo().getCuenta().getIdCuenta()))).compareTo(String.valueOf(o2.getSaldoEfectivo()
								.getDivisa().getId())
								+ String.valueOf(o2.getSaldoEfectivo().getBoveda().getId())
								+ String.valueOf(String.valueOf(o2.getSaldoEfectivo().getCuenta().getIdCuenta())));
					}

				});
			}

			DivisaDTO divisa = null;
			BovedaDTO boveda = null;
			EstadoCuentaEfectivoPorDivisaDTO detalleDivisa = null;
			DetalleEstadoCuentaSaldoPorBovedaDTO detalleBoveda = null;
			RegistroContableSaldoNombradaDTO registro = null;

			BigDecimal saldoTotal = new BigDecimal(0.0);
			BigDecimal saldoDisponible = new BigDecimal("0");
			BigDecimal saldoNoDisponible = new BigDecimal("0");

			Set<SaldoEfectivoDTO> saldos = new TreeSet<SaldoEfectivoDTO>(new Comparator<SaldoEfectivoDTO>() {
				public int compare(SaldoEfectivoDTO o1, SaldoEfectivoDTO o2) {
					return (String.valueOf(o1.getDivisa().getId()) + String.valueOf(o1.getBoveda().getId()) + String.valueOf(String.valueOf(o1.getCuenta()
							.getIdCuenta()))).compareTo(String.valueOf(o2.getDivisa().getId()) + String.valueOf(o2.getBoveda().getId())
							+ String.valueOf(String.valueOf(o2.getCuenta().getIdCuenta())));
				}
			});

			saldos.addAll(saldosFinales);
			saldos.addAll(saldosIniciales);
			
			Iterator<RegistroContableSaldoNombradaDTO> itRegistros = registrosContables.iterator();

			for (Iterator<SaldoEfectivoDTO> itSaldos = saldos.iterator(); itSaldos.hasNext();) {

				SaldoEfectivoDTO saldo = itSaldos.next();

				DivisaDTO divisaActual = saldo.getDivisa();
				BovedaDTO bovedaActual = saldo.getBoveda();

				if (divisa == null || divisa.getId() != divisaActual.getId()) {

					if (divisa != null) {
						detalleBoveda = null;
						boveda = null;
					}

					divisa = divisaActual;
					detalleDivisa = new EstadoCuentaEfectivoPorDivisaDTO();
					detalleDivisa.setDivisa(divisa);
					detalleEstadoCuenta.add(detalleDivisa);
				}

				if (boveda == null || boveda.getId() != bovedaActual.getId()) {

					boveda = bovedaActual;
					detalleBoveda = new DetalleEstadoCuentaSaldoPorBovedaDTO();
					detalleBoveda.setBoveda(boveda);

					EstadoSaldoDTO estadoSaldoFinal = new EstadoSaldoDTO();
					EstadoSaldoDTO estadoSaldoInicial = new EstadoSaldoDTO();
					SaldoEfectivoDTO saldoFinal = null;
					SaldoEfectivoDTO saldoInicial = null;

					int indicePosFin = saldosFinales.indexOf(saldo);
					int indicePosIni = saldosIniciales.indexOf(saldo);
					if (indicePosFin != -1) {
						saldoFinal = saldosFinales.get(indicePosFin);
					}
					if (indicePosIni != -1) {
						saldoInicial = saldosIniciales.get(indicePosIni);
					}

					if (saldoInicial != null) {
						estadoSaldoInicial.setSaldoInicial(new BigDecimal(saldoInicial.getSaldo()));
						estadoSaldoInicial.setSaldoInicialDisponible(new BigDecimal(saldoInicial.getSaldoDisponible().doubleValue()));
						estadoSaldoInicial.setSaldoInicialNoDisponible(new BigDecimal(saldoInicial.getSaldoNoDisponible()));
					} else {
						estadoSaldoInicial.setSaldoInicial(new BigDecimal(0.0));
						estadoSaldoInicial.setSaldoInicialDisponible(new BigDecimal(0.0));
						estadoSaldoInicial.setSaldoInicialNoDisponible(new BigDecimal(0.0));
					}

					if (saldoFinal != null) {
						estadoSaldoFinal.setSaldoFinal(new BigDecimal(saldoFinal.getSaldo()));
						estadoSaldoFinal.setSaldoFinalDisponible(new BigDecimal(saldoFinal.getSaldoDisponible().doubleValue()));
						estadoSaldoFinal.setSaldoFinalNoDisponible(new BigDecimal(saldoFinal.getSaldoNoDisponible()));
					} else {
						estadoSaldoFinal.setSaldoFinal(new BigDecimal(0.0));
						estadoSaldoFinal.setSaldoFinalDisponible(new BigDecimal(0.0));
						estadoSaldoFinal.setSaldoFinalNoDisponible(new BigDecimal(0.0));
					}

					detalleBoveda.setEstadoSaldoFinal(estadoSaldoFinal);
					detalleBoveda.setEstadoSaldoInicial(estadoSaldoInicial);

					saldoDisponible = detalleBoveda.getEstadoSaldoInicial().getSaldoInicialDisponible();
					saldoNoDisponible = detalleBoveda.getEstadoSaldoInicial().getSaldoInicialNoDisponible();
					saldoTotal = saldoDisponible.add(saldoNoDisponible);
				}

				if (detalleDivisa != null && detalleBoveda != null) {

					String descripcionRegistro = null;
					String descripcionContraparte = null;
					String descripcionCuentaContraparte = null;

					if (registro == null && itRegistros.hasNext()) {
						registro = itRegistros.next();
					}

					while (registro != null && registro.getSaldoEfectivo().getDivisa().getId() == divisa.getId()
							&& registro.getSaldoEfectivo().getBoveda().getId() == boveda.getId()) {

						RegistroContableSaldoNombradaDTO registroContable = registro;

						if (registro != null && registro.getCargoA() == TipoCargoAValores.CARGO_AFECTA_DISPONIBLE.longValue()) {
							if (registroContable.getTipoAccion().getId() == TipoAccion.ABONO_PASIVO_VALUE
									|| registroContable.getTipoAccion().getId() == TipoAccion.CARGO_ACTIVO_VALUE) {
								descripcionRegistro = TipoAccionConstants.TIPO_ACCION_RECEPCION_SALDO;
								saldoDisponible = saldoDisponible.add(registroContable.getCantidad());
								saldoTotal = saldoTotal.add(registroContable.getCantidad());
								registroContable.setCantidadRecepcion(registroContable.getCantidad());
							} else {
								descripcionRegistro = TipoAccionConstants.TIPO_ACCION_ENTREGA_SALDO;
								saldoDisponible = saldoDisponible.subtract(registroContable.getCantidad());
								saldoTotal = saldoTotal.subtract(registroContable.getCantidad());
								registroContable.setCantidadEntrega(registroContable.getCantidad());
							}
						} else {
							if (registroContable.getTipoAccion().getId() == TipoAccion.ABONO_PASIVO_VALUE
									|| registroContable.getTipoAccion().getId() == TipoAccion.CARGO_ACTIVO_VALUE) {
								descripcionRegistro = TipoAccionConstants.TIPO_ACCION_RECEPCION_SALDO;
								saldoNoDisponible = saldoNoDisponible.add(registroContable.getCantidad());
								saldoTotal = saldoTotal.add(registroContable.getCantidad());
								registroContable.setCantidadRecepcion(registroContable.getCantidad());
							} else {
								descripcionRegistro = TipoAccionConstants.TIPO_ACCION_ENTREGA_SALDO;
								saldoNoDisponible = saldoNoDisponible.subtract(registroContable.getCantidad());
								saldoTotal = saldoTotal.subtract(registroContable.getCantidad());
								registroContable.setCantidadEntrega(registroContable.getCantidad());
							}
						}

						if (registro.getOperacion().getCuentaEfectivoReceptor() == null || registro.getOperacion().getCuentaEfectivoTraspasante() == null) {
							if (registro.getOperacion().getCuentaEfectivoReceptor() != null) {
								descripcionContraparte = registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion().getNombreCorto();
								descripcionCuentaContraparte = "(" + registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion().getClaveTipoInstitucion()
										+ "-" + registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion().getFolioInstitucion() + "-"
										+ registro.getOperacion().getCuentaEfectivoReceptor().getCuenta() + ")";
								registroContable.setInstitucionContraparte(registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion());
								registroContable.setFolioInstitucionContraparte(registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion()
										.getFolioInstitucion());
								registroContable.setClaveTipoInstitucionContraparte(registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion()
										.getClaveTipoInstitucion());
								registroContable.setNumeroCuentaContraparte(registro.getOperacion().getCuentaEfectivoReceptor().getCuenta());
								registroContable.setFolioInstitucionParticipante(registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion()
										.getFolioInstitucion());
								registroContable.setClaveTipoInstitucionParticipante(registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion()
										.getClaveTipoInstitucion());
								registroContable.setNumeroCuentaParticipante(registro.getOperacion().getCuentaEfectivoReceptor().getCuenta());
							} else {
								if(registro.getOperacion().getCuentaEfectivoTraspasante() != null)
								{
									descripcionContraparte = registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion().getNombreCorto();
									descripcionCuentaContraparte = "(" + registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion().getClaveTipoInstitucion()
											+ "-" + registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion().getFolioInstitucion() + "-"
											+ registro.getOperacion().getCuentaEfectivoTraspasante().getCuenta() + ")";
									registroContable.setInstitucionContraparte(registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion());
									registroContable.setFolioInstitucionContraparte(registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion()
											.getFolioInstitucion());
									registroContable.setClaveTipoInstitucionContraparte(registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion()
											.getClaveTipoInstitucion());
									registroContable.setNumeroCuentaContraparte(registro.getOperacion().getCuentaEfectivoTraspasante().getCuenta());
									registroContable.setFolioInstitucionParticipante(registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion()
											.getFolioInstitucion());
									registroContable.setClaveTipoInstitucionParticipante(registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion()
											.getClaveTipoInstitucion());
									registroContable.setNumeroCuentaParticipante(registro.getOperacion().getCuentaEfectivoTraspasante().getCuenta());
								}
							}

						} else {
							if (registro.getSaldoEfectivo().getCuenta().getIdCuenta() != registro.getOperacion().getCuentaEfectivoReceptor().getIdCuenta()) {
								descripcionContraparte = registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion().getNombreCorto();
								descripcionCuentaContraparte = "(" + registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion().getClaveTipoInstitucion()
										+ "-" + registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion().getFolioInstitucion() + "-"
										+ registro.getOperacion().getCuentaEfectivoReceptor().getCuenta() + ")";
								registroContable.setInstitucionContraparte(registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion());
								registroContable.setFolioInstitucionContraparte(registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion()
										.getFolioInstitucion());
								registroContable.setClaveTipoInstitucionContraparte(registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion()
										.getClaveTipoInstitucion());
								registroContable.setNumeroCuentaContraparte(registro.getOperacion().getCuentaEfectivoReceptor().getCuenta());
								registroContable.setFolioInstitucionParticipante(registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion()
										.getFolioInstitucion());
								registroContable.setClaveTipoInstitucionParticipante(registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion()
										.getClaveTipoInstitucion());
								registroContable.setNumeroCuentaParticipante(registro.getOperacion().getCuentaEfectivoTraspasante().getCuenta());
							} else {
								descripcionContraparte = registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion().getNombreCorto();
								descripcionCuentaContraparte = "(" + registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion().getClaveTipoInstitucion()
										+ "-" + registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion().getFolioInstitucion() + "-"
										+ registro.getOperacion().getCuentaEfectivoTraspasante().getCuenta() + ")";
								registroContable.setInstitucionContraparte(registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion());
								registroContable.setFolioInstitucionContraparte(registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion()
										.getFolioInstitucion());
								registroContable.setClaveTipoInstitucionContraparte(registro.getOperacion().getCuentaEfectivoTraspasante().getInstitucion()
										.getClaveTipoInstitucion());
								registroContable.setNumeroCuentaContraparte(registro.getOperacion().getCuentaEfectivoTraspasante().getCuenta());
								registroContable.setFolioInstitucionParticipante(registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion()
										.getFolioInstitucion());
								registroContable.setClaveTipoInstitucionParticipante(registro.getOperacion().getCuentaEfectivoReceptor().getInstitucion()
										.getClaveTipoInstitucion());
								registroContable.setNumeroCuentaParticipante(registro.getOperacion().getCuentaEfectivoReceptor().getCuenta());
							}
						}

						descripcionRegistro += CommonConstants.PREPOSICION_POR + registro.getOperacion().getTipoOperacion().getDescripcion();
						registroContable.setDescripcion(descripcionRegistro);
						registroContable.setDescripcionLarga(descripcionRegistro + CommonConstants.PREPOSICION_POR
								+ registro.getOperacion().getInstruccionLiquidacion().getTipoInstruccion().getInstruccion());

						registroContable.setContraparte(descripcionContraparte);
						registroContable.setCuentaContraparte(descripcionCuentaContraparte);
						registroContable.setSaldoDisponible(saldoDisponible);
						registroContable.setSaldoNoDisponible(saldoNoDisponible);
						registroContable.setSaldoTotal(saldoTotal);

						detalleBoveda.getRegistrosContablesNombradas().add(registroContable);

						if (itRegistros.hasNext()) {
							registro = itRegistros.next();
						} else {
							registro = null;
						}
					}
				}

				if (detalleDivisa != null && detalleBoveda != null) {
					if (divisa != null && boveda != null) {
						detalleDivisa.getRegistrosContablesPorBoveda().add(detalleBoveda);
					}
				}
			}

			saldosIniciales = null;
			saldosFinales = null;
			registrosContables = null;
		}
		return detalleEstadoCuenta;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaEstadoCuentaSaldoEfectivoService#buscarCeutnasDeEfectivo(com.indeval.estadocuenta.core.application.dto.SaldoEfectivoDTO)
	 */
	public List<Long> buscarCuentasDeEfectivo(SaldoEfectivoDTO saldoEfectivo) {
		List<Long> resultados = null;

		if (saldoEfectivo.getCuenta().getTipoCuenta().getId().equals(TipoCuentaConstants.TIPO_CONTROLADA)) {
			if (DateUtil.comparaFechasDias(saldoEfectivo.getFechaFinal(), new Date()) == 0) {
				resultados = estadoCuentaSaldosEfectivoDAO.buscarCuentasDeEfectivoControladas(saldoEfectivo);
			} else {
				resultados = estadoCuentaSaldosEfectivoDAO.buscarCuentasDeEfectivoControladasHistorico(saldoEfectivo, saldoEfectivo.getFechaFinal());
			}
		} else {
			if (DateUtil.comparaFechasDias(saldoEfectivo.getFechaFinal(), new Date()) == 0) {
				resultados = estadoCuentaSaldosEfectivoDAO.buscarCuentasDeEfectivoNombradas(saldoEfectivo);
			} else {
				resultados = estadoCuentaSaldosEfectivoDAO.buscarCuentasDeEfectivoNombradasHistorico(saldoEfectivo, saldoEfectivo.getFechaFinal());
			}
		}

		return resultados;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaEstadoCuentaSaldoEfectivoService#buscarDivisasDeSaldosNombrados(com.indeval.estadocuenta.core.application.dto.SaldoEfectivoDTO)
	 */
	public List<Long> buscarDivisasDeSaldosNombrados(SaldoEfectivoDTO saldoEfectivo, Boolean debeDejarLog) {
		if (DateUtil.comparaFechasDias(saldoEfectivo.getFechaFinal(), new Date()) == 0) {
			return estadoCuentaSaldosEfectivoDAO.buscarDivisasDeSaldosNombrados(saldoEfectivo);
		} else {
			return estadoCuentaSaldosEfectivoDAO.buscarDivisasDeSaldosNombradosHistorico(saldoEfectivo, saldoEfectivo.getFechaFinal());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaEstadoCuentaSaldoEfectivoService#buscarDivisasDeSaldossControlados(com.indeval.estadocuenta.core.application.dto.SaldoEfectivoDTO)
	 */
	public List<Long> buscarDivisasDeSaldosControlados(SaldoEfectivoDTO saldoEfectivo, Boolean debeDejarLog) {
		if (DateUtil.comparaFechasDias(saldoEfectivo.getFechaFinal(), new Date()) == 0) {
			return estadoCuentaSaldosEfectivoDAO.buscarDivisasDeSaldosControlados(saldoEfectivo);
		} else {
			return estadoCuentaSaldosEfectivoDAO.buscarDivisasDeSaldosControladosHistorico(saldoEfectivo, saldoEfectivo.getFechaFinal());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.estadocuenta.ConsultaEstadoCuentaSaldoEfectivoService#buscarSaldoEfectivoPorId(java.util.Date,
	 *      long, java.lang.String)
	 */
	public SaldoEfectivoDTO buscarSaldoEfectivoPorId(Date fecha, long idSaldo, String tipoCuenta) {
		SaldoEfectivoDTO resultado = null;
		if (TipoCuentaConstants.TIPO_CONTROLADA.equals(tipoCuenta)) {
			if(DateUtils.isSameDay(fecha, new Date())) {
				resultado = saldoDAO.obtenerSaldoEfectivoControladaPorId(idSaldo);
			} else {
				resultado = saldoDAO.obtenerSaldoEfectivoControladaHistoricoPorId(idSaldo);
			}
		} else {
			if(DateUtils.isSameDay(fecha, new Date())) {
				resultado = saldoDAO.obtenerSaldoEfectivoNombradaPorId(idSaldo);
			} else {
				resultado = saldoDAO.obtenerSaldoEfectivoNombradaHistoricoPorId(idSaldo);
			}
		}
		return resultado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.estadocuenta.ConsultaEstadoCuentaSaldoEfectivoService#obtenerProyeccionDeRegistrosContablesDeSaldos(com.indeval.portaldali.middleware.dto.SaldoEfectivoDTO)
	 */
	public long obtenerProyeccionDeRegistrosContablesDeSaldos(SaldoEfectivoDTO criterio) {
		long resultado = 0;

		if (DateUtils.isSameDay(criterio.getFechaFinal(), new Date())) {
			if (criterio.getCuenta().getTipoCuenta().getId().equals(TipoCuentaConstants.TIPO_CONTROLADA)) {
				resultado += estadoCuentaSaldosEfectivoDAO.obtenerProyeccionDeRegistrosContablesDeSaldosControlada(criterio);
			} else {
				resultado += estadoCuentaSaldosEfectivoDAO.obtenerProyeccionDeRegistrosContablesDeSaldosNombrada(criterio);
			}
		}

		if (DateUtils.truncate(criterio.getFechaInicial(), Calendar.DATE).before(DateUtils.truncate(new Date(), Calendar.DATE))) {
			if (criterio.getCuenta().getTipoCuenta().getId().equals(TipoCuentaConstants.TIPO_CONTROLADA)) {
				resultado += estadoCuentaSaldosEfectivoDAO.obtenerProyeccionDeRegistrosContablesDeSaldosControladaHistorico(criterio);
			} else {
				resultado += estadoCuentaSaldosEfectivoDAO.obtenerProyeccionDeRegistrosContablesDeSaldosNombradaHistorico(criterio);
			}
		}

		return resultado;
	}

	/**
	 * Obtiene el campo saldoDAO
	 * 
	 * @return saldoDAO
	 */
	public SaldoDAO getSaldoDAO() {
		return saldoDAO;
	}

	/**
	 * Asigna el valor del campo saldoDAO
	 * 
	 * @param saldoDAO
	 *            el valor de saldoDAO a asignar
	 */
	public void setSaldoDAO(SaldoDAO saldoDAO) {
		this.saldoDAO = saldoDAO;
	}

	/**
	 * @return the diaInhabilDao
	 */
	public DiaInhabilDaliDao getDiaInhabilDao() {
		return diaInhabilDao;
	}

	/**
	 * @param diaInhabilDao
	 *            the diaInhabilDao to set
	 */
	public void setDiaInhabilDao(DiaInhabilDaliDao diaInhabilDao) {
		this.diaInhabilDao = diaInhabilDao;
	}

	/**
	 * Obtiene el campo estadoCuentaSaldosEfectivoDAO
	 * 
	 * @return estadoCuentaSaldosEfectivoDAO
	 */
	public EstadoCuentaSaldosEfectivoDAOV2 getEstadoCuentaSaldosEfectivoDAO() {
		return estadoCuentaSaldosEfectivoDAO;
	}

	/**
	 * Asigna el valor del campo estadoCuentaSaldosEfectivoDAO
	 * 
	 * @param estadoCuentaSaldosEfectivoDAO
	 *            el valor de estadoCuentaSaldosEfectivoDAO a asignar
	 */
	public void setEstadoCuentaSaldosEfectivoDAO(EstadoCuentaSaldosEfectivoDAOV2 estadoCuentaSaldosEfectivoDAO) {
		this.estadoCuentaSaldosEfectivoDAO = estadoCuentaSaldosEfectivoDAO;
	}
}

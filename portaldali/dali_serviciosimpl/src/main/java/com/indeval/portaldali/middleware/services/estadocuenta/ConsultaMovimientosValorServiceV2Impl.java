/**
 * Sistema Portal DALI
 * 
 * Bursatec - 2H Software SA de CV
 *
 * Creado: Nov 25, 2008
 */
package com.indeval.portaldali.middleware.services.estadocuenta;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.CriterioConsultaMovimientosValorDTO;
import com.indeval.portaldali.middleware.dto.DetalleEstadoCuentaPosicionPorBovedaDTO;
import com.indeval.portaldali.middleware.dto.EmisionDTO;
import com.indeval.portaldali.middleware.dto.EstadoCuentaPosicionPorEmisionDTO;
import com.indeval.portaldali.middleware.dto.PosicionDTO;
import com.indeval.portaldali.middleware.dto.RegistroContablePosicionControladaDTO;
import com.indeval.portaldali.middleware.dto.RegistroContablePosicionNombradaDTO;
import com.indeval.portaldali.middleware.dto.TipoNaturalezaDTO;
import com.indeval.portaldali.middleware.services.common.constants.CommonConstants;
import com.indeval.portaldali.middleware.services.common.constants.TipoAccionConstants;
import com.indeval.portaldali.middleware.services.common.constants.TipoCargoAValores;
import com.indeval.portaldali.middleware.services.common.constants.TipoCuentaConstants;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import com.indeval.portaldali.middleware.services.util.UtilServices;
import com.indeval.portaldali.persistence.dao.estadocuenta.MovimientosValorDAOV2;
import com.indeval.portaldali.persistence.model.TipoAccion;

/**
 * Implementación del servicio de negocio para la consulta de movimientos de
 * valores del estado de cuenta del DALI.
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 * 
 */
public class ConsultaMovimientosValorServiceV2Impl implements ConsultaMovimientosValorService {

	/** DAO para la consulta de los registros contables y posiciones */
	private MovimientosValorDAOV2 movimientosValorDAOV2 = null;

	/** Servicio de negocio con métodos utilitarios para el sistema DALI */
	private UtilServices utilServices = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.estadocuenta.ConsultaMovimientosValorService#buscarCuentasDeMovimientosDeValor(com.indeval.portaldali.middleware.dto.CriterioConsultaMovimientosValorDTO)
	 */
	public List<Long> buscarCuentasDeMovimientosDeValor(CriterioConsultaMovimientosValorDTO criterio) {
		List<Long> idsCuentas = new ArrayList<Long>();

		if (DateUtils.isSameDay(criterio.getFechaFinal(), utilServices.getCurrentDate())) {
			if (criterio.getCuenta().getTipoTenencia().getTipoCuenta().getId().equals(TipoCuentaConstants.TIPO_CONTROLADA)) {
				idsCuentas = movimientosValorDAOV2.buscarCuentasDePosicionesControladas(criterio);
			} else {
				idsCuentas = movimientosValorDAOV2.buscarCuentasDePosicionesNombradas(criterio);
			}
		} else {
			if (criterio.getCuenta().getTipoTenencia().getTipoCuenta().getId().equals(TipoCuentaConstants.TIPO_CONTROLADA)) {
				idsCuentas = movimientosValorDAOV2.buscarCuentasDePosicionesControladasHistoricas(criterio, criterio.getFechaFinal());
			} else {
				idsCuentas = movimientosValorDAOV2.buscarCuentasDePosicionesNombradasHistoricas(criterio, criterio.getFechaFinal());
			}
		}

		return idsCuentas;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.estadocuenta.ConsultaMovimientosValorService#buscarEmisionesDeMovimientosDeValor(com.indeval.portaldali.middleware.dto.CriterioConsultaMovimientosValorDTO)
	 */
	public List<Long> buscarEmisionesDeMovimientosDeValor(CriterioConsultaMovimientosValorDTO criterio) {
		List<Long> idsEmisiones = new ArrayList<Long>();

		if (DateUtils.isSameDay(criterio.getFechaFinal(), utilServices.getCurrentDate())) {
			if (criterio.getCuenta().getTipoTenencia().getTipoCuenta().getId().equals(TipoCuentaConstants.TIPO_CONTROLADA)) {
				idsEmisiones = movimientosValorDAOV2.buscarEmisionesDePosicionesControladas(criterio);
			} else {
				idsEmisiones = movimientosValorDAOV2.buscarEmisionesDePosicionesNombradas(criterio);
			}
		} else {
			if (criterio.getCuenta().getTipoTenencia().getTipoCuenta().getId().equals(TipoCuentaConstants.TIPO_CONTROLADA)) {
				idsEmisiones = movimientosValorDAOV2.buscarEmisionesDePosicionesControladasHistoricas(criterio, criterio.getFechaFinal());
			} else {
				idsEmisiones = movimientosValorDAOV2.buscarEmisionesDePosicionesNombradasHistoricas(criterio, criterio.getFechaFinal());
			}
		}

		return idsEmisiones;
	}

	/**
	 * Invoca la búsqueda correspondiente de registros contables que reflejan
	 * las operaciones relacionadas a posiciones controladas según los criterios
	 * de búsqueda recibidos como parámetros. Consulta la base de datos para
	 * obtener los valores de las operaciones que se registran en un lapso de
	 * tiempo
	 * 
	 * @param criterio
	 *            el DTO con los criterios para realizar la consulta
	 * @param idsEmisiones
	 *            la lista de identificadores de emisiones a considerar para la
	 *            consulta del estado de movimientos.
	 * @return Lista de objetos {@link EstadoCuentaPosicionPorEmisionDTO} los
	 *         cuales contienen el detalle de los movimientos por emisión y
	 *         bóveda.
	 */
	private List<EstadoCuentaPosicionPorEmisionDTO> consultarMovimientosDeValorControladas(CriterioConsultaMovimientosValorDTO criterio, Set<Long> idsEmisiones) {
		List<RegistroContablePosicionControladaDTO> registrosContables = new ArrayList<RegistroContablePosicionControladaDTO>();
		List<EstadoCuentaPosicionPorEmisionDTO> detalleEstadoCuenta = new ArrayList<EstadoCuentaPosicionPorEmisionDTO>();

		if (DateUtils.isSameDay(criterio.getFechaFinal(), utilServices.getCurrentDate())) {
			registrosContables.addAll(movimientosValorDAOV2.buscarRegistrosContablesControladas(criterio, idsEmisiones));
		}

		if (DateUtils.truncate(criterio.getFechaInicial(), Calendar.DATE).before(DateUtils.truncate(utilServices.getCurrentDate(), Calendar.DATE))) {
			registrosContables.addAll(movimientosValorDAOV2.buscarRegistrosContablesControladasHistoricos(criterio, idsEmisiones));

			Collections.sort(registrosContables, new Comparator<RegistroContablePosicionControladaDTO>() {

				public int compare(RegistroContablePosicionControladaDTO o1, RegistroContablePosicionControladaDTO o2) {

					return (String.valueOf(o1.getPosicion().getEmision().getId()) + String.valueOf(o1.getPosicion().getBoveda().getId())
							+ String.valueOf(String.valueOf(o1.getPosicion().getCuenta().getIdCuenta())) + String.valueOf(o1.getFecha().getTime())
							+ o1.getPosicion().getCuenta().getNumeroCuenta() + o1.getPosicion().getEmision().getTipoValor().getClaveTipoValor()
							+ o1.getPosicion().getEmision().getEmisora().getDescripcion() + o1.getPosicion().getEmision().getSerie().getSerie() + o1
							.getPosicion().getCupon().getClaveCupon()).compareTo(String.valueOf(o2.getPosicion().getEmision().getId())
							+ String.valueOf(o2.getPosicion().getBoveda().getId()) + String.valueOf(String.valueOf(o2.getPosicion().getCuenta().getIdCuenta()))
							+ String.valueOf(o2.getFecha().getTime()) + o2.getPosicion().getCuenta().getNumeroCuenta()
							+ o2.getPosicion().getEmision().getTipoValor().getClaveTipoValor() + o2.getPosicion().getEmision().getEmisora().getDescripcion()
							+ o2.getPosicion().getEmision().getSerie().getSerie() + o2.getPosicion().getCupon().getClaveCupon());
				}

			});
		}

		EmisionDTO emision = null;
		BovedaDTO boveda = null;
		EstadoCuentaPosicionPorEmisionDTO detalleEmision = null;
		DetalleEstadoCuentaPosicionPorBovedaDTO detalleBoveda = null;

		BigInteger posicionTotal = new BigInteger("0");
		RegistroContablePosicionControladaDTO registro = null;
		Iterator<RegistroContablePosicionControladaDTO> itRegistros = registrosContables.iterator();

		if (itRegistros.hasNext()) {
			registro = itRegistros.next();
		}

		while (registro != null) {

			PosicionDTO posicion = registro.getPosicion();

			EmisionDTO emisionActual = posicion.getEmision();
			BovedaDTO bovedaActual = posicion.getBoveda();

			if (emision == null || emision.getId() != emisionActual.getId()) {

				if (emision != null) {
					detalleEmision.getDetallesBoveda().add(detalleBoveda);
					detalleEstadoCuenta.add(detalleEmision);
					detalleBoveda = null;
					boveda = null;
				}

				emision = emisionActual;
				detalleEmision = new EstadoCuentaPosicionPorEmisionDTO();
				detalleEmision.setEmision(emision);
			}

			if (boveda == null || boveda.getId() != bovedaActual.getId()) {

				if (boveda != null) {
					detalleEmision.getDetallesBoveda().add(detalleBoveda);
				}

				boveda = bovedaActual;
				detalleBoveda = new DetalleEstadoCuentaPosicionPorBovedaDTO();
				detalleBoveda.setBoveda(boveda);

			}

			if (detalleEmision != null && detalleBoveda != null) {

				String descripcionRegistro = null;

				while (registro != null && registro.getPosicion().getEmision().getId() == emision.getId()
						&& registro.getPosicion().getBoveda().getId() == boveda.getId()) {

					if (registro.getTipoAccion().getId() == TipoAccion.ABONO_PASIVO_VALUE || registro.getTipoAccion().getId() == TipoAccion.CARGO_ACTIVO_VALUE) {
						descripcionRegistro = TipoAccionConstants.TIPO_ACCION_RECEPCION_POSICION + CommonConstants.SUFIJO_DESCRIPCION_OPERACIONES_CONTROLADA;
						posicionTotal = posicionTotal.add(registro.getCantidad());
						registro.setCantidadRecepcion(registro.getCantidad());
					} else {
						descripcionRegistro = TipoAccionConstants.TIPO_ACCION_ENTREGA_POSICION + CommonConstants.SUFIJO_DESCRIPCION_OPERACIONES_CONTROLADA;
						posicionTotal = posicionTotal.subtract(registro.getCantidad());
						registro.setCantidadEntrega(registro.getCantidad());
					}

					registro.setIdCiclo(registro.getIdCiclo());
					registro.setPosicionTotal(posicionTotal);
					registro.setDescripcion(descripcionRegistro);

					registro.setCupon(registro.getPosicion().getCupon());
					detalleBoveda.getRegistrosContablesControladas().add(registro);

					if (itRegistros.hasNext()) {
						registro = itRegistros.next();
					} else {
						registro = null;
					}
				}
			}

		}

		if (detalleEmision != null && detalleBoveda != null) {
			if (emision != null && boveda != null) {
				detalleEmision.getDetallesBoveda().add(detalleBoveda);
				detalleEstadoCuenta.add(detalleEmision);
			}
		}


		//Se colocan las listas a null
		registrosContables = null;
		
		return detalleEstadoCuenta;
	}

	/**
	 * Invoca la búsqueda correspondiente de registros contables que reflejan
	 * las operaciones relacionadas a posiciones nombradas según los criterios
	 * de búsqueda recibidos como parámetros. Consulta la base de datos para
	 * obtener los valores de las operaciones que se registran en un lapso de
	 * tiempo
	 * 
	 * @param criterio
	 *            el DTO con los criterios para realizar la consulta
	 * @param idsEmisiones
	 *            la lista de identificadores de emisiones a considerar para la
	 *            consulta del estado de movimientos.
	 * @return Lista de objetos {@link EstadoCuentaPosicionPorEmisionDTO} los
	 *         cuales contienen el detalle de los movimientos por emisión y
	 *         bóveda.
	 */
	private List<EstadoCuentaPosicionPorEmisionDTO> consultarMovimientosDeValorNombradas(CriterioConsultaMovimientosValorDTO criterio, Set<Long> idsEmisiones) {
		List<RegistroContablePosicionNombradaDTO> registrosContables = new ArrayList<RegistroContablePosicionNombradaDTO>();
		List<EstadoCuentaPosicionPorEmisionDTO> detalleEstadoCuenta = new ArrayList<EstadoCuentaPosicionPorEmisionDTO>();

		if (DateUtils.isSameDay(criterio.getFechaFinal(), utilServices.getCurrentDate())) {

			registrosContables.addAll(movimientosValorDAOV2.buscarRegistrosContablesNombradas(criterio, idsEmisiones));
		}

		if (DateUtils.truncate(criterio.getFechaInicial(), Calendar.DATE).before(DateUtils.truncate(utilServices.getCurrentDate(), Calendar.DATE))) {
			registrosContables.addAll(movimientosValorDAOV2.buscarRegistrosContablesNombradasHistoricos(criterio, idsEmisiones));

			Collections.sort(registrosContables, new Comparator<RegistroContablePosicionNombradaDTO>() {

				public int compare(RegistroContablePosicionNombradaDTO o1, RegistroContablePosicionNombradaDTO o2) {

					return (String.valueOf(o1.getPosicion().getEmision().getId()) + String.valueOf(o1.getPosicion().getBoveda().getId())
							+ String.valueOf(String.valueOf(o1.getPosicion().getCuenta().getIdCuenta())) + String.valueOf(o1.getFecha().getTime())
							+ o1.getPosicion().getCuenta().getNumeroCuenta() + o1.getPosicion().getEmision().getTipoValor().getClaveTipoValor()
							+ o1.getPosicion().getEmision().getEmisora().getDescripcion() + o1.getPosicion().getEmision().getSerie().getSerie() + o1
							.getPosicion().getCupon().getClaveCupon()).compareTo(String.valueOf(o2.getPosicion().getEmision().getId())
							+ String.valueOf(o2.getPosicion().getBoveda().getId()) + String.valueOf(String.valueOf(o2.getPosicion().getCuenta().getIdCuenta()))
							+ String.valueOf(o2.getFecha().getTime()) + o2.getPosicion().getCuenta().getNumeroCuenta()
							+ o2.getPosicion().getEmision().getTipoValor().getClaveTipoValor() + o2.getPosicion().getEmision().getEmisora().getDescripcion()
							+ o2.getPosicion().getEmision().getSerie().getSerie() + o2.getPosicion().getCupon().getClaveCupon());
				}

			});
		}

		EmisionDTO emision = null;
		BovedaDTO boveda = null;
		EstadoCuentaPosicionPorEmisionDTO detalleEmision = null;
		DetalleEstadoCuentaPosicionPorBovedaDTO detalleBoveda = null;

		BigInteger posicionDisponible = new BigInteger("0");
		BigInteger posicionNoDisponible = new BigInteger("0");
		BigInteger posicionTotal = new BigInteger("0");
		RegistroContablePosicionNombradaDTO registro = null;
		Iterator<RegistroContablePosicionNombradaDTO> itRegistros = registrosContables.iterator();

		if (itRegistros.hasNext()) {
			registro = itRegistros.next();
		}

		while (registro != null) {

			PosicionDTO posicion = registro.getPosicion();

			EmisionDTO emisionActual = posicion.getEmision();
			BovedaDTO bovedaActual = posicion.getBoveda();

			if (emision == null || emision.getId() != emisionActual.getId()) {

				if (emision != null) {
					detalleEmision.getDetallesBoveda().add(detalleBoveda);
					detalleEstadoCuenta.add(detalleEmision);
					detalleBoveda = null;
					boveda = null;
				}

				emision = emisionActual;
				detalleEmision = new EstadoCuentaPosicionPorEmisionDTO();
				detalleEmision.setEmision(emision);
			}

			if (boveda == null || boveda.getId() != bovedaActual.getId()) {

				if (boveda != null) {
					detalleEmision.getDetallesBoveda().add(detalleBoveda);
				}

				boveda = bovedaActual;
				detalleBoveda = new DetalleEstadoCuentaPosicionPorBovedaDTO();
				detalleBoveda.setBoveda(boveda);

			}

			if (detalleEmision != null && detalleBoveda != null) {

				String descripcionRegistro = null;
				String descripcionContraparte = null;
				String cuentaContraparte = null;

				while (registro != null && registro.getPosicion().getCupon().getEmision().getId() == emision.getId()
						&& registro.getPosicion().getBoveda().getId() == boveda.getId()) {

					if (TipoNaturalezaDTO.PASIVO.equals(registro.getPosicion().getCuenta().getTipoTenencia().getTipoNaturaleza().getId())) {
						if (registro.getOperacion().getCuentaReceptora() != null
								&& registro.getOperacion().getCuentaReceptora().getInstitucion() != null
								&& registro.getOperacion().getCuentaReceptora().getInstitucion().getId() == registro.getPosicion().getCuenta().getInstitucion()
										.getId()) {
							if (registro.getTipoAccion().getId() == TipoAccion.ABONO_PASIVO_VALUE
									|| registro.getTipoAccion().getId() == TipoAccion.CARGO_ACTIVO_VALUE) {
								descripcionRegistro = TipoAccionConstants.TIPO_ACCION_RECEPCION_POSICION;
								posicionDisponible = posicionDisponible.add(registro.getCantidad());
								posicionTotal = posicionTotal.add(registro.getCantidad());
								registro.setCantidadRecepcion(registro.getCantidad());
							} else {
								descripcionRegistro = TipoAccionConstants.TIPO_ACCION_ENTREGA_POSICION;
								posicionDisponible = posicionDisponible.subtract(registro.getCantidad());
								posicionTotal = posicionTotal.subtract(registro.getCantidad());
								registro.setCantidadEntrega(registro.getCantidad());
							}

						} else {

							if (registro.getOperacion().getCuentaTraspasante() != null
									&& registro.getOperacion().getCuentaTraspasante().getInstitucion() != null
									&& registro.getOperacion().getCuentaTraspasante().getInstitucion().getId() == registro.getPosicion().getCuenta()
											.getInstitucion().getId()) {

								if (registro.getCargoA() != null && registro.getCargoA().longValue() == TipoCargoAValores.CARGO_AFECTA_DISPONIBLE.longValue()) {

									if (registro.getTipoAccion().getId() == TipoAccion.ABONO_PASIVO_VALUE
											|| registro.getTipoAccion().getId() == TipoAccion.CARGO_ACTIVO_VALUE) {
										descripcionRegistro = TipoAccionConstants.TIPO_ACCION_RECEPCION_POSICION;
										posicionDisponible = posicionDisponible.add(registro.getCantidad());
										posicionTotal = posicionTotal.add(registro.getCantidad());
										registro.setCantidadRecepcion(registro.getCantidad());
									} else {
										descripcionRegistro = TipoAccionConstants.TIPO_ACCION_ENTREGA_POSICION;
										posicionDisponible = posicionDisponible.subtract(registro.getCantidad());
										posicionTotal = posicionTotal.subtract(registro.getCantidad());
										registro.setCantidadEntrega(registro.getCantidad());
									}
								} else {
									if (registro.getTipoAccion().getId() == TipoAccion.ABONO_PASIVO_VALUE
											|| registro.getTipoAccion().getId() == TipoAccion.CARGO_ACTIVO_VALUE) {
										descripcionRegistro = TipoAccionConstants.TIPO_ACCION_RECEPCION_POSICION;
										posicionNoDisponible = posicionNoDisponible.add(registro.getCantidad());
										posicionTotal = posicionTotal.add(registro.getCantidad());
										registro.setCantidadRecepcion(registro.getCantidad());
									} else {
										descripcionRegistro = TipoAccionConstants.TIPO_ACCION_ENTREGA_POSICION;
										posicionNoDisponible = posicionNoDisponible.subtract(registro.getCantidad());
										posicionTotal = posicionTotal.subtract(registro.getCantidad());
										registro.setCantidadEntrega(registro.getCantidad());
									}
								}

							}
						}
					} else {
						if (registro.getCargoA() != null && registro.getCargoA().longValue() == TipoCargoAValores.CARGO_AFECTA_DISPONIBLE.longValue()) {
							if (registro.getTipoAccion().getId() == TipoAccion.ABONO_PASIVO_VALUE
									|| registro.getTipoAccion().getId() == TipoAccion.CARGO_ACTIVO_VALUE) {
								descripcionRegistro = TipoAccionConstants.TIPO_ACCION_RECEPCION_POSICION;
								posicionDisponible = posicionDisponible.add(registro.getCantidad());
								posicionTotal = posicionTotal.add(registro.getCantidad());
								registro.setCantidadRecepcion(registro.getCantidad());
							} else {
								descripcionRegistro = TipoAccionConstants.TIPO_ACCION_ENTREGA_POSICION;
								posicionDisponible = posicionDisponible.subtract(registro.getCantidad());
								posicionTotal = posicionTotal.subtract(registro.getCantidad());
								registro.setCantidadEntrega(registro.getCantidad());
							}
						} else {
							if (registro.getTipoAccion().getId() == TipoAccion.ABONO_PASIVO_VALUE
									|| registro.getTipoAccion().getId() == TipoAccion.CARGO_ACTIVO_VALUE) {
								descripcionRegistro = TipoAccionConstants.TIPO_ACCION_RECEPCION_POSICION;
								posicionNoDisponible = posicionNoDisponible.add(registro.getCantidad());
								posicionTotal = posicionTotal.add(registro.getCantidad());
								registro.setCantidadRecepcion(registro.getCantidad());
							} else {
								descripcionRegistro = TipoAccionConstants.TIPO_ACCION_ENTREGA_POSICION;
								posicionNoDisponible = posicionNoDisponible.subtract(registro.getCantidad());
								posicionTotal = posicionTotal.subtract(registro.getCantidad());
								registro.setCantidadEntrega(registro.getCantidad());
							}
						}
					}
					if (registro.getOperacion().getCuentaReceptora() == null || registro.getOperacion().getCuentaTraspasante() == null) {
						descripcionContraparte = StringUtils.EMPTY;
						cuentaContraparte = StringUtils.EMPTY;
						if (registro.getOperacion().getCuentaReceptora() != null) {
							registro.setClaveTipoInstitucionParticipante(registro.getOperacion().getCuentaReceptora().getInstitucion()
									.getClaveTipoInstitucion());
							registro.setFolioInstitucionParticipante(registro.getOperacion().getCuentaReceptora().getInstitucion().getFolioInstitucion());
							registro.setNumeroCuentaParticipante(registro.getOperacion().getCuentaReceptora().getCuenta());
						}

						if (registro.getOperacion().getCuentaTraspasante() != null) {
							registro.setClaveTipoInstitucionParticipante(registro.getOperacion().getCuentaTraspasante().getInstitucion()
									.getClaveTipoInstitucion());
							registro.setFolioInstitucionParticipante(registro.getOperacion().getCuentaTraspasante().getInstitucion().getFolioInstitucion());
							registro.setNumeroCuentaParticipante(registro.getOperacion().getCuentaTraspasante().getCuenta());
						}
					} else {
						if (!(registro.getPosicion().getCuenta().getIdCuenta() == registro.getOperacion().getCuentaReceptora().getIdCuenta())) {
							descripcionContraparte = registro.getOperacion().getCuentaReceptora().getInstitucion().getNombreCorto();
							cuentaContraparte = "(" + registro.getOperacion().getCuentaReceptora().getInstitucion().getClaveTipoInstitucion() + "-"
									+ registro.getOperacion().getCuentaReceptora().getInstitucion().getFolioInstitucion() + "-"
									+ registro.getOperacion().getCuentaReceptora().getCuenta() + ")";
							registro
									.setClaveTipoInstitucionContraparte(registro.getOperacion().getCuentaReceptora().getInstitucion().getClaveTipoInstitucion());
							registro.setFolioInstitucionContraparte(registro.getOperacion().getCuentaReceptora().getInstitucion().getFolioInstitucion());
							registro.setNumeroCuentaContraparte(registro.getOperacion().getCuentaReceptora().getCuenta());
							registro.setClaveTipoInstitucionParticipante(registro.getOperacion().getCuentaTraspasante().getInstitucion()
									.getClaveTipoInstitucion());
							registro.setFolioInstitucionParticipante(registro.getOperacion().getCuentaTraspasante().getInstitucion().getFolioInstitucion());
							registro.setNumeroCuentaParticipante(registro.getOperacion().getCuentaTraspasante().getCuenta());
						} else {
							descripcionContraparte = registro.getOperacion().getCuentaTraspasante().getInstitucion().getNombreCorto();
							cuentaContraparte = "(" + registro.getOperacion().getCuentaTraspasante().getInstitucion().getClaveTipoInstitucion() + "-"
									+ registro.getOperacion().getCuentaTraspasante().getInstitucion().getFolioInstitucion() + "-"
									+ registro.getOperacion().getCuentaTraspasante().getCuenta() + ")";
							registro.setClaveTipoInstitucionContraparte(registro.getOperacion().getCuentaTraspasante().getInstitucion()
									.getClaveTipoInstitucion());
							registro.setFolioInstitucionContraparte(registro.getOperacion().getCuentaTraspasante().getInstitucion().getFolioInstitucion());
							registro.setNumeroCuentaContraparte(registro.getOperacion().getCuentaTraspasante().getCuenta());
							registro.setClaveTipoInstitucionParticipante(registro.getOperacion().getCuentaReceptora().getInstitucion()
									.getClaveTipoInstitucion());
							registro.setFolioInstitucionParticipante(registro.getOperacion().getCuentaReceptora().getInstitucion().getFolioInstitucion());
							registro.setNumeroCuentaParticipante(registro.getOperacion().getCuentaReceptora().getCuenta());
						}
					}

					descripcionRegistro += CommonConstants.PREPOSICION_POR + registro.getOperacion().getTipoOperacion().getDescripcion();
					registro.setDescripcion(descripcionRegistro);

					registro.setDescripcionLarga(descripcionRegistro + CommonConstants.PREPOSICION_POR
							+ registro.getOperacion().getInstruccionLiquidacion().getTipoInstruccion().getInstruccion());

					registro.setContraparte(descripcionContraparte);
					registro.setCuentaContraparte(cuentaContraparte);
					registro.setFolioOperacion(registro.getOperacion().getFolio());
					registro.setPosicionDisponible(posicionDisponible);
					registro.setPosicionNoDisponible(posicionNoDisponible);
					registro.setPosicionTotal(posicionTotal);
					registro.setCupon(registro.getOperacion().getCupon());

					detalleBoveda.getRegistrosContablesNombradas().add(registro);

					if (itRegistros.hasNext()) {
						registro = itRegistros.next();
					} else {
						registro = null;
					}
				}
			}
		}

		if (detalleEmision != null && detalleBoveda != null) {
			if (emision != null && boveda != null) {
				detalleEmision.getDetallesBoveda().add(detalleBoveda);
				detalleEstadoCuenta.add(detalleEmision);
			}
		}
		

		//Se colocan las listas a null
		registrosContables = null;
		
		return detalleEstadoCuenta;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.estadocuenta.ConsultaMovimientosValorService#consultarMovimientosDeValor(com.indeval.portaldali.middleware.dto.CriterioConsultaMovimientosValorDTO,
	 *      java.util.Set)
	 */
	public List<EstadoCuentaPosicionPorEmisionDTO> consultarMovimientosDeValor(CriterioConsultaMovimientosValorDTO criterio, Set<Long> idsEmisiones) {

		List<EstadoCuentaPosicionPorEmisionDTO> resultados = null;
		if (TipoCuentaConstants.TIPO_CONTROLADA.equals(criterio.getCuenta().getTipoTenencia().getTipoCuenta().getId())) {
			resultados = consultarMovimientosDeValorControladas(criterio, idsEmisiones);
		} else {
			resultados = consultarMovimientosDeValorNombradas(criterio, idsEmisiones);
		}
		return resultados;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.estadocuenta.ConsultaMovimientosValorService#consultarMovimientosDeValorControladasSinAgrupar(com.indeval.portaldali.middleware.dto.CriterioConsultaMovimientosValorDTO,
	 *      java.util.Set)
	 */
	public List<RegistroContablePosicionControladaDTO> consultarMovimientosDeValorControladasSinAgrupar(CriterioConsultaMovimientosValorDTO criterio,
			Set<Long> idsEmisiones) {
		List<RegistroContablePosicionControladaDTO> registrosContables = new ArrayList<RegistroContablePosicionControladaDTO>();

		if (DateUtils.isSameDay(criterio.getFechaFinal(), utilServices.getCurrentDate())) {
			registrosContables.addAll(movimientosValorDAOV2.buscarRegistrosContablesControladas(criterio, idsEmisiones));
		}

		if (DateUtils.truncate(criterio.getFechaInicial(), Calendar.DATE).before(DateUtils.truncate(utilServices.getCurrentDate(), Calendar.DATE))) {
			registrosContables.addAll(movimientosValorDAOV2.buscarRegistrosContablesControladasHistoricos(criterio, idsEmisiones));

			Collections.sort(registrosContables, new Comparator<RegistroContablePosicionControladaDTO>() {

				public int compare(RegistroContablePosicionControladaDTO o1, RegistroContablePosicionControladaDTO o2) {

					return (o1.getPosicion().getCuenta().getNumeroCuenta() + o1.getPosicion().getEmision().getTipoValor().getClaveTipoValor()
							+ o1.getPosicion().getEmision().getEmisora().getDescripcion() + o1.getPosicion().getEmision().getSerie().getSerie()
							+ o1.getPosicion().getCupon().getClaveCupon() + String.valueOf(o1.getFecha().getTime())).compareTo(o2.getPosicion().getCuenta()
							.getNumeroCuenta()
							+ o2.getPosicion().getEmision().getTipoValor().getClaveTipoValor()
							+ o2.getPosicion().getEmision().getEmisora().getDescripcion()
							+ o2.getPosicion().getEmision().getSerie().getSerie()
							+ o2.getPosicion().getCupon().getClaveCupon()
							+ String.valueOf(o2.getFecha().getTime()));
				}

			});
		}

		BigInteger posicionTotal = new BigInteger("0");

		for (Iterator<RegistroContablePosicionControladaDTO> itRegistros = registrosContables.iterator(); itRegistros.hasNext();) {
			RegistroContablePosicionControladaDTO registro = itRegistros.next();

			String descripcionRegistro = null;

			if (registro.getTipoAccion().getId() == TipoAccion.ABONO_PASIVO_VALUE || registro.getTipoAccion().getId() == TipoAccion.CARGO_ACTIVO_VALUE) {
				descripcionRegistro = TipoAccionConstants.TIPO_ACCION_RECEPCION_POSICION + CommonConstants.SUFIJO_DESCRIPCION_OPERACIONES_CONTROLADA;
				posicionTotal = posicionTotal.add(registro.getCantidad());
				registro.setCantidadRecepcion(registro.getCantidad());
			} else {
				descripcionRegistro = TipoAccionConstants.TIPO_ACCION_ENTREGA_POSICION + CommonConstants.SUFIJO_DESCRIPCION_OPERACIONES_CONTROLADA;
				posicionTotal = posicionTotal.subtract(registro.getCantidad());
				registro.setCantidadEntrega(registro.getCantidad());
			}

			registro.setIdCiclo(registro.getIdCiclo());
			registro.setPosicionTotal(posicionTotal);
			registro.setDescripcion(descripcionRegistro);

			registro.setCupon(registro.getPosicion().getCupon());
		}
		return registrosContables;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.estadocuenta.ConsultaMovimientosValorService#consultarMovimientosDeValorNombradasSinAgrupar(com.indeval.portaldali.middleware.dto.CriterioConsultaMovimientosValorDTO,
	 *      java.util.Set)
	 */
	public List<RegistroContablePosicionNombradaDTO> consultarMovimientosDeValorNombradasSinAgrupar(CriterioConsultaMovimientosValorDTO criterio,
			Set<Long> idsEmisiones) {
		List<RegistroContablePosicionNombradaDTO> registrosContables = new ArrayList<RegistroContablePosicionNombradaDTO>();

		if (DateUtils.isSameDay(criterio.getFechaFinal(), utilServices.getCurrentDate())) {

			registrosContables.addAll(movimientosValorDAOV2.buscarRegistrosContablesNombradas(criterio, idsEmisiones));
		}

		if (DateUtils.truncate(criterio.getFechaInicial(), Calendar.DATE).before(DateUtils.truncate(utilServices.getCurrentDate(), Calendar.DATE))) {
			registrosContables.addAll(movimientosValorDAOV2.buscarRegistrosContablesNombradasHistoricos(criterio, idsEmisiones));

			Collections.sort(registrosContables, new Comparator<RegistroContablePosicionNombradaDTO>() {

				public int compare(RegistroContablePosicionNombradaDTO o1, RegistroContablePosicionNombradaDTO o2) {

					return (o1.getPosicion().getCuenta().getNumeroCuenta() + o1.getPosicion().getEmision().getTipoValor().getClaveTipoValor()
							+ o1.getPosicion().getEmision().getEmisora().getDescripcion() + o1.getPosicion().getEmision().getSerie().getSerie()
							+ o1.getPosicion().getCupon().getClaveCupon() + String.valueOf(o1.getFecha().getTime())).compareTo(o2.getPosicion().getCuenta()
							.getNumeroCuenta()
							+ o2.getPosicion().getEmision().getTipoValor().getClaveTipoValor()
							+ o2.getPosicion().getEmision().getEmisora().getDescripcion()
							+ o2.getPosicion().getEmision().getSerie().getSerie()
							+ o2.getPosicion().getCupon().getClaveCupon()
							+ String.valueOf(o2.getFecha().getTime()));
				}

			});
		}

		BigInteger posicionDisponible = new BigInteger("0");
		BigInteger posicionNoDisponible = new BigInteger("0");
		BigInteger posicionTotal = new BigInteger("0");

		for (Iterator<RegistroContablePosicionNombradaDTO> itRegistros = registrosContables.iterator(); itRegistros.hasNext();) {
			RegistroContablePosicionNombradaDTO registro = itRegistros.next();
			String descripcionRegistro = null;
			String descripcionContraparte = null;
			String cuentaContraparte = null;

			if (TipoNaturalezaDTO.PASIVO.equals(registro.getPosicion().getCuenta().getTipoTenencia().getTipoNaturaleza().getId())) {
				if (registro.getOperacion().getCuentaReceptora() != null && registro.getOperacion().getCuentaReceptora().getInstitucion() != null
						&& registro.getOperacion().getCuentaReceptora().getInstitucion().getId() == registro.getPosicion().getCuenta().getInstitucion().getId()) {
					if (registro.getTipoAccion().getId() == TipoAccion.ABONO_PASIVO_VALUE || registro.getTipoAccion().getId() == TipoAccion.CARGO_ACTIVO_VALUE) {
						descripcionRegistro = TipoAccionConstants.TIPO_ACCION_RECEPCION_POSICION;
						posicionDisponible = posicionDisponible.add(registro.getCantidad());
						posicionTotal = posicionTotal.add(registro.getCantidad());
						registro.setCantidadRecepcion(registro.getCantidad());
					} else {
						descripcionRegistro = TipoAccionConstants.TIPO_ACCION_ENTREGA_POSICION;
						posicionDisponible = posicionDisponible.subtract(registro.getCantidad());
						posicionTotal = posicionTotal.subtract(registro.getCantidad());
						registro.setCantidadEntrega(registro.getCantidad());
					}

				} else {

					if (registro.getOperacion().getCuentaTraspasante() != null
							&& registro.getOperacion().getCuentaTraspasante().getInstitucion() != null
							&& registro.getOperacion().getCuentaTraspasante().getInstitucion().getId() == registro.getPosicion().getCuenta().getInstitucion()
									.getId()) {

						if (registro.getCargoA() != null && registro.getCargoA().longValue() == TipoCargoAValores.CARGO_AFECTA_DISPONIBLE.longValue()) {

							if (registro.getTipoAccion().getId() == TipoAccion.ABONO_PASIVO_VALUE
									|| registro.getTipoAccion().getId() == TipoAccion.CARGO_ACTIVO_VALUE) {
								descripcionRegistro = TipoAccionConstants.TIPO_ACCION_RECEPCION_POSICION;
								posicionDisponible = posicionDisponible.add(registro.getCantidad());
								posicionTotal = posicionTotal.add(registro.getCantidad());
								registro.setCantidadRecepcion(registro.getCantidad());
							} else {
								descripcionRegistro = TipoAccionConstants.TIPO_ACCION_ENTREGA_POSICION;
								posicionDisponible = posicionDisponible.subtract(registro.getCantidad());
								posicionTotal = posicionTotal.subtract(registro.getCantidad());
								registro.setCantidadEntrega(registro.getCantidad());
							}
						} else {
							if (registro.getTipoAccion().getId() == TipoAccion.ABONO_PASIVO_VALUE
									|| registro.getTipoAccion().getId() == TipoAccion.CARGO_ACTIVO_VALUE) {
								descripcionRegistro = TipoAccionConstants.TIPO_ACCION_RECEPCION_POSICION;
								posicionNoDisponible = posicionNoDisponible.add(registro.getCantidad());
								posicionTotal = posicionTotal.add(registro.getCantidad());
								registro.setCantidadRecepcion(registro.getCantidad());
							} else {
								descripcionRegistro = TipoAccionConstants.TIPO_ACCION_ENTREGA_POSICION;
								posicionNoDisponible = posicionNoDisponible.subtract(registro.getCantidad());
								posicionTotal = posicionTotal.subtract(registro.getCantidad());
								registro.setCantidadEntrega(registro.getCantidad());
							}
						}

					}
				}
			} else {
				if (registro.getCargoA() != null && registro.getCargoA().longValue() == TipoCargoAValores.CARGO_AFECTA_DISPONIBLE.longValue()) {
					if (registro.getTipoAccion().getId() == TipoAccion.ABONO_PASIVO_VALUE || registro.getTipoAccion().getId() == TipoAccion.CARGO_ACTIVO_VALUE) {
						descripcionRegistro = TipoAccionConstants.TIPO_ACCION_RECEPCION_POSICION;
						posicionDisponible = posicionDisponible.add(registro.getCantidad());
						posicionTotal = posicionTotal.add(registro.getCantidad());
						registro.setCantidadRecepcion(registro.getCantidad());
					} else {
						descripcionRegistro = TipoAccionConstants.TIPO_ACCION_ENTREGA_POSICION;
						posicionDisponible = posicionDisponible.subtract(registro.getCantidad());
						posicionTotal = posicionTotal.subtract(registro.getCantidad());
						registro.setCantidadEntrega(registro.getCantidad());
					}
				} else {
					if (registro.getTipoAccion().getId() == TipoAccion.ABONO_PASIVO_VALUE || registro.getTipoAccion().getId() == TipoAccion.CARGO_ACTIVO_VALUE) {
						descripcionRegistro = TipoAccionConstants.TIPO_ACCION_RECEPCION_POSICION;
						posicionNoDisponible = posicionNoDisponible.add(registro.getCantidad());
						posicionTotal = posicionTotal.add(registro.getCantidad());
						registro.setCantidadRecepcion(registro.getCantidad());
					} else {
						descripcionRegistro = TipoAccionConstants.TIPO_ACCION_ENTREGA_POSICION;
						posicionNoDisponible = posicionNoDisponible.subtract(registro.getCantidad());
						posicionTotal = posicionTotal.subtract(registro.getCantidad());
						registro.setCantidadEntrega(registro.getCantidad());
					}
				}
			}
			if (registro.getOperacion().getCuentaReceptora() == null || registro.getOperacion().getCuentaTraspasante() == null) {
				descripcionContraparte = StringUtils.EMPTY;
				cuentaContraparte = StringUtils.EMPTY;
				if (registro.getOperacion().getCuentaReceptora() != null) {
					registro.setClaveTipoInstitucionParticipante(registro.getOperacion().getCuentaReceptora().getInstitucion().getClaveTipoInstitucion());
					registro.setFolioInstitucionParticipante(registro.getOperacion().getCuentaReceptora().getInstitucion().getFolioInstitucion());
					registro.setNumeroCuentaParticipante(registro.getOperacion().getCuentaReceptora().getCuenta());
				}

				if (registro.getOperacion().getCuentaTraspasante() != null) {
					registro.setClaveTipoInstitucionParticipante(registro.getOperacion().getCuentaTraspasante().getInstitucion().getClaveTipoInstitucion());
					registro.setFolioInstitucionParticipante(registro.getOperacion().getCuentaTraspasante().getInstitucion().getFolioInstitucion());
					registro.setNumeroCuentaParticipante(registro.getOperacion().getCuentaTraspasante().getCuenta());
				}
			} else {
				if (!(registro.getPosicion().getCuenta().getIdCuenta() == registro.getOperacion().getCuentaReceptora().getIdCuenta())) {
					descripcionContraparte = registro.getOperacion().getCuentaReceptora().getInstitucion().getNombreCorto();
					cuentaContraparte = "(" + registro.getOperacion().getCuentaReceptora().getInstitucion().getClaveTipoInstitucion() + "-"
							+ registro.getOperacion().getCuentaReceptora().getInstitucion().getFolioInstitucion() + "-"
							+ registro.getOperacion().getCuentaReceptora().getCuenta() + ")";
					registro.setClaveTipoInstitucionContraparte(registro.getOperacion().getCuentaReceptora().getInstitucion().getClaveTipoInstitucion());
					registro.setFolioInstitucionContraparte(registro.getOperacion().getCuentaReceptora().getInstitucion().getFolioInstitucion());
					registro.setNumeroCuentaContraparte(registro.getOperacion().getCuentaReceptora().getCuenta());
					registro.setClaveTipoInstitucionParticipante(registro.getOperacion().getCuentaTraspasante().getInstitucion().getClaveTipoInstitucion());
					registro.setFolioInstitucionParticipante(registro.getOperacion().getCuentaTraspasante().getInstitucion().getFolioInstitucion());
					registro.setNumeroCuentaParticipante(registro.getOperacion().getCuentaTraspasante().getCuenta());
				} else {
					descripcionContraparte = registro.getOperacion().getCuentaTraspasante().getInstitucion().getNombreCorto();
					cuentaContraparte = "(" + registro.getOperacion().getCuentaTraspasante().getInstitucion().getClaveTipoInstitucion() + "-"
							+ registro.getOperacion().getCuentaTraspasante().getInstitucion().getFolioInstitucion() + "-"
							+ registro.getOperacion().getCuentaTraspasante().getCuenta() + ")";
					registro.setClaveTipoInstitucionContraparte(registro.getOperacion().getCuentaTraspasante().getInstitucion().getClaveTipoInstitucion());
					registro.setFolioInstitucionContraparte(registro.getOperacion().getCuentaTraspasante().getInstitucion().getFolioInstitucion());
					registro.setNumeroCuentaContraparte(registro.getOperacion().getCuentaTraspasante().getCuenta());
					registro.setClaveTipoInstitucionParticipante(registro.getOperacion().getCuentaReceptora().getInstitucion().getClaveTipoInstitucion());
					registro.setFolioInstitucionParticipante(registro.getOperacion().getCuentaReceptora().getInstitucion().getFolioInstitucion());
					registro.setNumeroCuentaParticipante(registro.getOperacion().getCuentaReceptora().getCuenta());
				}
			}

			descripcionRegistro += CommonConstants.PREPOSICION_POR + registro.getOperacion().getTipoOperacion().getDescripcion();
			registro.setDescripcion(descripcionRegistro);

			registro.setDescripcionLarga(descripcionRegistro + CommonConstants.PREPOSICION_POR
					+ registro.getOperacion().getInstruccionLiquidacion().getTipoInstruccion().getInstruccion());

			registro.setContraparte(descripcionContraparte);
			registro.setCuentaContraparte(cuentaContraparte);
			registro.setFolioOperacion(registro.getOperacion().getFolio());
			registro.setPosicionDisponible(posicionDisponible);
			registro.setPosicionNoDisponible(posicionNoDisponible);
			registro.setPosicionTotal(posicionTotal);
			registro.setCupon(registro.getOperacion().getCupon());
		}

		return registrosContables;
	}

	
	/**
	 * @see com.indeval.portaldali.middleware.services.estadocuenta.ConsultaMovimientosValorService#consultarMovimientosArchivoConciliacion(com.indeval.portaldali.middleware.dto.CriterioConsultaMovimientosValorDTO, java.util.Set)
	 */
	public PaginaVO consultarMovimientosArchivoConciliacion(CriterioConsultaMovimientosValorDTO criterio,
			Set<Long> idsEmisiones, PaginaVO pagina, Boolean debeDejarLog) {
		List<RegistroContablePosicionNombradaDTO> registrosContables = new ArrayList<RegistroContablePosicionNombradaDTO>();

		if (DateUtils.isSameDay(criterio.getFechaFinal(), utilServices.getCurrentDate())) {

			registrosContables.addAll(movimientosValorDAOV2.buscarRegistrosContablesNombradas(criterio, idsEmisiones));
		}

		if (DateUtils.truncate(criterio.getFechaInicial(), Calendar.DATE).before(DateUtils.truncate(utilServices.getCurrentDate(), Calendar.DATE))) {
			registrosContables.addAll(movimientosValorDAOV2.buscarRegistrosContablesNombradasHistoricos(criterio, idsEmisiones));

			Collections.sort(registrosContables, new Comparator<RegistroContablePosicionNombradaDTO>() {

				public int compare(RegistroContablePosicionNombradaDTO o1, RegistroContablePosicionNombradaDTO o2) {

					return (o1.getPosicion().getCuenta().getNumeroCuenta() + o1.getPosicion().getEmision().getTipoValor().getClaveTipoValor()
							+ o1.getPosicion().getEmision().getEmisora().getDescripcion() + o1.getPosicion().getEmision().getSerie().getSerie()
							+ o1.getPosicion().getCupon().getClaveCupon() + String.valueOf(o1.getFecha().getTime())).compareTo(o2.getPosicion().getCuenta()
							.getNumeroCuenta()
							+ o2.getPosicion().getEmision().getTipoValor().getClaveTipoValor()
							+ o2.getPosicion().getEmision().getEmisora().getDescripcion()
							+ o2.getPosicion().getEmision().getSerie().getSerie()
							+ o2.getPosicion().getCupon().getClaveCupon()
							+ String.valueOf(o2.getFecha().getTime()));
				}

			});
		}

		BigInteger posicionDisponible = new BigInteger("0");
		BigInteger posicionNoDisponible = new BigInteger("0");
		BigInteger posicionTotal = new BigInteger("0");

		for (Iterator<RegistroContablePosicionNombradaDTO> itRegistros = registrosContables.iterator(); itRegistros.hasNext();) {
			RegistroContablePosicionNombradaDTO registro = itRegistros.next();
			String descripcionRegistro = null;
			String descripcionContraparte = null;
			String cuentaContraparte = null;

			if (TipoNaturalezaDTO.PASIVO.equals(registro.getPosicion().getCuenta().getTipoTenencia().getTipoNaturaleza().getId())) {
				if (registro.getOperacion().getCuentaReceptora() != null && registro.getOperacion().getCuentaReceptora().getInstitucion() != null
						&& registro.getOperacion().getCuentaReceptora().getInstitucion().getId() == registro.getPosicion().getCuenta().getInstitucion().getId()) {
					if (registro.getTipoAccion().getId() == TipoAccion.ABONO_PASIVO_VALUE || registro.getTipoAccion().getId() == TipoAccion.CARGO_ACTIVO_VALUE) {
						descripcionRegistro = TipoAccionConstants.TIPO_ACCION_RECEPCION_POSICION;
						posicionDisponible = posicionDisponible.add(registro.getCantidad());
						posicionTotal = posicionTotal.add(registro.getCantidad());
						registro.setCantidadRecepcion(registro.getCantidad());
					} else {
						descripcionRegistro = TipoAccionConstants.TIPO_ACCION_ENTREGA_POSICION;
						posicionDisponible = posicionDisponible.subtract(registro.getCantidad());
						posicionTotal = posicionTotal.subtract(registro.getCantidad());
						registro.setCantidadEntrega(registro.getCantidad());
					}

				} else {

					if (registro.getOperacion().getCuentaTraspasante() != null
							&& registro.getOperacion().getCuentaTraspasante().getInstitucion() != null
							&& registro.getOperacion().getCuentaTraspasante().getInstitucion().getId() == registro.getPosicion().getCuenta().getInstitucion()
									.getId()) {

						if (registro.getCargoA() != null && registro.getCargoA().longValue() == TipoCargoAValores.CARGO_AFECTA_DISPONIBLE.longValue()) {

							if (registro.getTipoAccion().getId() == TipoAccion.ABONO_PASIVO_VALUE
									|| registro.getTipoAccion().getId() == TipoAccion.CARGO_ACTIVO_VALUE) {
								descripcionRegistro = TipoAccionConstants.TIPO_ACCION_RECEPCION_POSICION;
								posicionDisponible = posicionDisponible.add(registro.getCantidad());
								posicionTotal = posicionTotal.add(registro.getCantidad());
								registro.setCantidadRecepcion(registro.getCantidad());
							} else {
								descripcionRegistro = TipoAccionConstants.TIPO_ACCION_ENTREGA_POSICION;
								posicionDisponible = posicionDisponible.subtract(registro.getCantidad());
								posicionTotal = posicionTotal.subtract(registro.getCantidad());
								registro.setCantidadEntrega(registro.getCantidad());
							}
						} else {
							if (registro.getTipoAccion().getId() == TipoAccion.ABONO_PASIVO_VALUE
									|| registro.getTipoAccion().getId() == TipoAccion.CARGO_ACTIVO_VALUE) {
								descripcionRegistro = TipoAccionConstants.TIPO_ACCION_RECEPCION_POSICION;
								posicionNoDisponible = posicionNoDisponible.add(registro.getCantidad());
								posicionTotal = posicionTotal.add(registro.getCantidad());
								registro.setCantidadRecepcion(registro.getCantidad());
							} else {
								descripcionRegistro = TipoAccionConstants.TIPO_ACCION_ENTREGA_POSICION;
								posicionNoDisponible = posicionNoDisponible.subtract(registro.getCantidad());
								posicionTotal = posicionTotal.subtract(registro.getCantidad());
								registro.setCantidadEntrega(registro.getCantidad());
							}
						}

					}
				}
			} else {
				if (registro.getCargoA() != null && registro.getCargoA().longValue() == TipoCargoAValores.CARGO_AFECTA_DISPONIBLE.longValue()) {
					if (registro.getTipoAccion().getId() == TipoAccion.ABONO_PASIVO_VALUE || registro.getTipoAccion().getId() == TipoAccion.CARGO_ACTIVO_VALUE) {
						descripcionRegistro = TipoAccionConstants.TIPO_ACCION_RECEPCION_POSICION;
						posicionDisponible = posicionDisponible.add(registro.getCantidad());
						posicionTotal = posicionTotal.add(registro.getCantidad());
						registro.setCantidadRecepcion(registro.getCantidad());
					} else {
						descripcionRegistro = TipoAccionConstants.TIPO_ACCION_ENTREGA_POSICION;
						posicionDisponible = posicionDisponible.subtract(registro.getCantidad());
						posicionTotal = posicionTotal.subtract(registro.getCantidad());
						registro.setCantidadEntrega(registro.getCantidad());
					}
				} else {
					if (registro.getTipoAccion().getId() == TipoAccion.ABONO_PASIVO_VALUE || registro.getTipoAccion().getId() == TipoAccion.CARGO_ACTIVO_VALUE) {
						descripcionRegistro = TipoAccionConstants.TIPO_ACCION_RECEPCION_POSICION;
						posicionNoDisponible = posicionNoDisponible.add(registro.getCantidad());
						posicionTotal = posicionTotal.add(registro.getCantidad());
						registro.setCantidadRecepcion(registro.getCantidad());
					} else {
						descripcionRegistro = TipoAccionConstants.TIPO_ACCION_ENTREGA_POSICION;
						posicionNoDisponible = posicionNoDisponible.subtract(registro.getCantidad());
						posicionTotal = posicionTotal.subtract(registro.getCantidad());
						registro.setCantidadEntrega(registro.getCantidad());
					}
				}
			}
			if (registro.getOperacion().getCuentaReceptora() == null || registro.getOperacion().getCuentaTraspasante() == null) {
				descripcionContraparte = StringUtils.EMPTY;
				cuentaContraparte = StringUtils.EMPTY;
				if (registro.getOperacion().getCuentaReceptora() != null) {
					registro.setClaveTipoInstitucionParticipante(registro.getOperacion().getCuentaReceptora().getInstitucion().getClaveTipoInstitucion());
					registro.setFolioInstitucionParticipante(registro.getOperacion().getCuentaReceptora().getInstitucion().getFolioInstitucion());
					registro.setNumeroCuentaParticipante(registro.getOperacion().getCuentaReceptora().getCuenta());
				}

				if (registro.getOperacion().getCuentaTraspasante() != null) {
					registro.setClaveTipoInstitucionParticipante(registro.getOperacion().getCuentaTraspasante().getInstitucion().getClaveTipoInstitucion());
					registro.setFolioInstitucionParticipante(registro.getOperacion().getCuentaTraspasante().getInstitucion().getFolioInstitucion());
					registro.setNumeroCuentaParticipante(registro.getOperacion().getCuentaTraspasante().getCuenta());
				}
			} else {
				if (!(registro.getPosicion().getCuenta().getIdCuenta() == registro.getOperacion().getCuentaReceptora().getIdCuenta())) {
					descripcionContraparte = registro.getOperacion().getCuentaReceptora().getInstitucion().getNombreCorto();
					cuentaContraparte = "(" + registro.getOperacion().getCuentaReceptora().getInstitucion().getClaveTipoInstitucion() + "-"
							+ registro.getOperacion().getCuentaReceptora().getInstitucion().getFolioInstitucion() + "-"
							+ registro.getOperacion().getCuentaReceptora().getCuenta() + ")";
					registro.setClaveTipoInstitucionContraparte(registro.getOperacion().getCuentaReceptora().getInstitucion().getClaveTipoInstitucion());
					registro.setFolioInstitucionContraparte(registro.getOperacion().getCuentaReceptora().getInstitucion().getFolioInstitucion());
					registro.setNumeroCuentaContraparte(registro.getOperacion().getCuentaReceptora().getCuenta());
					registro.setClaveTipoInstitucionParticipante(registro.getOperacion().getCuentaTraspasante().getInstitucion().getClaveTipoInstitucion());
					registro.setFolioInstitucionParticipante(registro.getOperacion().getCuentaTraspasante().getInstitucion().getFolioInstitucion());
					registro.setNumeroCuentaParticipante(registro.getOperacion().getCuentaTraspasante().getCuenta());
				} else {
					descripcionContraparte = registro.getOperacion().getCuentaTraspasante().getInstitucion().getNombreCorto();
					cuentaContraparte = "(" + registro.getOperacion().getCuentaTraspasante().getInstitucion().getClaveTipoInstitucion() + "-"
							+ registro.getOperacion().getCuentaTraspasante().getInstitucion().getFolioInstitucion() + "-"
							+ registro.getOperacion().getCuentaTraspasante().getCuenta() + ")";
					registro.setClaveTipoInstitucionContraparte(registro.getOperacion().getCuentaTraspasante().getInstitucion().getClaveTipoInstitucion());
					registro.setFolioInstitucionContraparte(registro.getOperacion().getCuentaTraspasante().getInstitucion().getFolioInstitucion());
					registro.setNumeroCuentaContraparte(registro.getOperacion().getCuentaTraspasante().getCuenta());
					registro.setClaveTipoInstitucionParticipante(registro.getOperacion().getCuentaReceptora().getInstitucion().getClaveTipoInstitucion());
					registro.setFolioInstitucionParticipante(registro.getOperacion().getCuentaReceptora().getInstitucion().getFolioInstitucion());
					registro.setNumeroCuentaParticipante(registro.getOperacion().getCuentaReceptora().getCuenta());
				}
			}

			descripcionRegistro += CommonConstants.PREPOSICION_POR + registro.getOperacion().getTipoOperacion().getDescripcion();
			registro.setDescripcion(descripcionRegistro);

			registro.setDescripcionLarga(descripcionRegistro + CommonConstants.PREPOSICION_POR
					+ registro.getOperacion().getInstruccionLiquidacion().getTipoInstruccion().getInstruccion());

			registro.setContraparte(descripcionContraparte);
			registro.setCuentaContraparte(cuentaContraparte);
			registro.setFolioOperacion(registro.getOperacion().getFolio());
			registro.setPosicionDisponible(posicionDisponible);
			registro.setPosicionNoDisponible(posicionNoDisponible);
			registro.setPosicionTotal(posicionTotal);
			registro.setCupon(registro.getOperacion().getCupon());
		}
		
		return pagina.extraerSublist(registrosContables);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.estadocuenta.ConsultaMovimientosValorService#obtenerProyeccionDeMovimientosDeValor(com.indeval.portaldali.middleware.dto.CriterioConsultaMovimientosValorDTO)
	 */
	public long obtenerProyeccionDeMovimientosDeValor(CriterioConsultaMovimientosValorDTO criterio, Boolean debeDejarLog) {
		long totalRegistros = 0;

		if (DateUtils.isSameDay(criterio.getFechaFinal(), utilServices.getCurrentDate())) {
			if (criterio.getCuenta().getTipoTenencia().getTipoCuenta().getId().equals(TipoCuentaConstants.TIPO_CONTROLADA)) {
				totalRegistros += movimientosValorDAOV2.obtenerProyeccionRegistrosContablesControladas(criterio);
			} else {
				totalRegistros += movimientosValorDAOV2.obtenerProyeccionRegistrosContablesNombradas(criterio);
			}
		}

		if (DateUtils.truncate(criterio.getFechaInicial(), Calendar.DATE).before(utilServices.getCurrentDate())) {
			if (criterio.getCuenta().getTipoTenencia().getTipoCuenta().getId().equals(TipoCuentaConstants.TIPO_CONTROLADA)) {
				totalRegistros += movimientosValorDAOV2.obtenerProyeccionRegistrosContablesControladasHistorico(criterio);
			} else {
				totalRegistros += movimientosValorDAOV2.obtenerProyeccionRegistrosContablesNombradasHistorico(criterio);
			}
		}

		return totalRegistros;
	}

	/**
	 * Obtiene el valor del atributo movimientosValorDAOV2
	 * 
	 * @return el valor del atributo movimientosValorDAOV2
	 */
	public MovimientosValorDAOV2 getMovimientosValorDAOV2() {
		return movimientosValorDAOV2;
	}

	/**
	 * Establece el valor del atributo movimientosValorDAOV2
	 * 
	 * @param movimientosValorDAOV2
	 *            el valor del atributo movimientosValorDAOV2 a establecer
	 */
	public void setMovimientosValorDAOV2(MovimientosValorDAOV2 movimientosValorDAOV2) {
		this.movimientosValorDAOV2 = movimientosValorDAOV2;
	}

	/**
	 * Obtiene el valor del atributo utilServices
	 * 
	 * @return el valor del atributo utilServices
	 */
	public UtilServices getUtilServices() {
		return utilServices;
	}

	/**
	 * Establece el valor del atributo utilServices
	 * 
	 * @param utilServices
	 *            el valor del atributo utilServices a establecer
	 */
	public void setUtilServices(UtilServices utilServices) {
		this.utilServices = utilServices;
	}

}

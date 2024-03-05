/**
 * Sistema Portal DALI
 * 
 * Bursatec - 2H Software SA de CV
 *
 * Creado: Nov 24, 2008
 */
package com.indeval.portaldali.middleware.services.estadocuenta;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.DetalleEstadoCuentaPosicionPorBovedaDTO;
import com.indeval.portaldali.middleware.dto.EmisionDTO;
import com.indeval.portaldali.middleware.dto.EstadoCuentaPosicionPorEmisionDTO;
import com.indeval.portaldali.middleware.dto.EstadoPosicionDTO;
import com.indeval.portaldali.middleware.dto.PosicionDTO;
import com.indeval.portaldali.middleware.dto.RegistroContablePosicionControladaDTO;
import com.indeval.portaldali.middleware.dto.RegistroContablePosicionNombradaDTO;
import com.indeval.portaldali.middleware.dto.TipoNaturalezaDTO;
import com.indeval.portaldali.middleware.services.common.constants.CommonConstants;
import com.indeval.portaldali.middleware.services.common.constants.TipoAccionConstants;
import com.indeval.portaldali.middleware.services.common.constants.TipoCargoAValores;
import com.indeval.portaldali.middleware.services.common.constants.TipoCuentaConstants;
import com.indeval.portaldali.middleware.services.util.UtilServices;
import com.indeval.portaldali.persistence.dao.estadocuenta.EstadoCuentaPosicionDAOV2;
import com.indeval.portaldali.persistence.dao.estadocuenta.PosicionDAO;
import com.indeval.portaldali.persistence.model.TipoAccion;

/**
 * Implementación del servicio para la consulta del estado de cuenta de
 * posiciones así como el histórico de posiciones.
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 * 
 */
public class ConsultaEstadoCuentaPosicionServiceV2Impl implements ConsultaEstadoCuentaPosicionService {

	/** Servicio de negocio con método utilitarios comunes */
	private UtilServices utilServices = null;

	/** DAO para realizar las consultas del estado de cuenta */
	private EstadoCuentaPosicionDAOV2 estadoCuentaPosicionDAOV2 = null;

	/** DAO para la consulta de posiciones en el DALI */
	private PosicionDAO posicionDAO = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.estadocuenta.ConsultaEstadoCuentaPosicionService#buscarCuentasDePosiciones(com.indeval.portaldali.middleware.dto.PosicionDTO)
	 */
	public List<Long> buscarCuentasDePosiciones(PosicionDTO criterio) {

		List<Long> idsCuentas = new ArrayList<Long>();

		if (DateUtils.isSameDay(criterio.getFechaFinal(), utilServices.getCurrentDate())) {
			if (criterio.getCuenta().getTipoTenencia().getTipoCuenta().getId().equals(TipoCuentaConstants.TIPO_CONTROLADA)) {
				idsCuentas = estadoCuentaPosicionDAOV2.buscarCuentasDePosicionesControladas(criterio);
			}
			else {
				idsCuentas = estadoCuentaPosicionDAOV2.buscarCuentasDePosicionesNombradas(criterio);
			}
		}
		else {
			if (criterio.getCuenta().getTipoTenencia().getTipoCuenta().getId().equals(TipoCuentaConstants.TIPO_CONTROLADA)) {
				idsCuentas = estadoCuentaPosicionDAOV2.buscarCuentasDePosicionesControladasHistoricas(criterio, criterio.getFechaFinal());
			}
			else {
				idsCuentas = estadoCuentaPosicionDAOV2.buscarCuentasDePosicionesNombradasHistoricas(criterio, criterio.getFechaFinal());
			}
		}

		return idsCuentas;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.estadocuenta.ConsultaEstadoCuentaPosicionService#buscarEmisionesDePosicionesControladas(com.indeval.portaldali.middleware.dto.PosicionDTO)
	 */
	public List<Long> buscarEmisionesDePosicionesControladas(PosicionDTO criterio, Boolean debeDejarLog) {

		List<Long> idsEmisiones = new ArrayList<Long>();

		if (DateUtils.isSameDay(criterio.getFechaFinal(), utilServices.getCurrentDate())) {
			idsEmisiones = estadoCuentaPosicionDAOV2.buscarEmisionesDePosicionesControladas(criterio);
		}
		else {
			idsEmisiones = estadoCuentaPosicionDAOV2.buscarEmisionesDePosicionesControladasHistoricas(criterio, criterio.getFechaFinal());
		}

		return idsEmisiones;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.estadocuenta.ConsultaEstadoCuentaPosicionService#buscarEmisionesDePosicionesNombradas(com.indeval.portaldali.middleware.dto.PosicionDTO)
	 */
	public List<Long> buscarEmisionesDePosicionesNombradas(PosicionDTO criterio, Boolean debeDejarLog) {
		List<Long> idsEmisiones = new ArrayList<Long>();

		if (DateUtils.isSameDay(criterio.getFechaFinal(), utilServices.getCurrentDate())) {
			idsEmisiones = estadoCuentaPosicionDAOV2.buscarEmisionesDePosicionesNombradas(criterio);
		}
		else {
			idsEmisiones = estadoCuentaPosicionDAOV2.buscarEmisionesDePosicionesNombradasHistoricas(criterio, criterio.getFechaFinal());
		}

		return idsEmisiones;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.estadocuenta.ConsultaEstadoCuentaPosicionService#buscarPosicionPorId(long,
	 *      java.lang.String)
	 */
	public PosicionDTO buscarPosicionPorId(Date fecha, long idPosicion, String tipoCuenta) {
		PosicionDTO resultado = null;
		if (TipoCuentaConstants.TIPO_CONTROLADA.equals(tipoCuenta)) {
			if(DateUtils.isSameDay(fecha, utilServices.getCurrentDate())) {
				resultado = posicionDAO.buscarPosicionControladaPorId(idPosicion);
			}
			else {
				resultado = posicionDAO.buscarPosicionControladaHistoricoPorId(fecha, idPosicion);
			}
		}
		else {
			if(DateUtils.isSameDay(fecha, utilServices.getCurrentDate())) {
				resultado = posicionDAO.buscarPosicionNombradaPorId(idPosicion);
			}
			else {
				resultado = posicionDAO.buscarPosicionNombradaHistoricoPorId(fecha,idPosicion);
			}
		}
		return resultado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.estadocuenta.ConsultaEstadoCuentaPosicionService#consultarEstadoDeCuentaPosicionesControladas(com.indeval.portaldali.middleware.dto.PosicionDTO,
	 *      java.util.List)
	 */
	public List<EstadoCuentaPosicionPorEmisionDTO> consultarEstadoDeCuentaPosicionesControladas(PosicionDTO criterio, List<Long> idsEmisiones) {
		List<PosicionDTO> posicionesIniciales = null;
		List<PosicionDTO> posicionesFinales = null;
		List<RegistroContablePosicionControladaDTO> registrosContables = new ArrayList<RegistroContablePosicionControladaDTO>();
		List<EstadoCuentaPosicionPorEmisionDTO> detalleEstadoCuenta = new ArrayList<EstadoCuentaPosicionPorEmisionDTO>();

		if (DateUtils.isSameDay(criterio.getFechaFinal(), utilServices.getCurrentDate())) {
			posicionesFinales = estadoCuentaPosicionDAOV2.buscarPosicionesControladas(criterio, idsEmisiones);
			registrosContables.addAll(estadoCuentaPosicionDAOV2.buscarRegistrosContablesControladas(criterio, idsEmisiones));
		} else {
			posicionesFinales = estadoCuentaPosicionDAOV2.buscarPosicionesControladasHistoricas(criterio, DateUtils.truncate(criterio.getFechaFinal(),
					Calendar.DATE), idsEmisiones);
		}

		posicionesIniciales = estadoCuentaPosicionDAOV2.buscarPosicionesControladasHistoricas(criterio, utilServices.agregarDiasHabiles(criterio
				.getFechaInicio(), -1), idsEmisiones);

		if (DateUtils.truncate(criterio.getFechaInicio(), Calendar.DATE).before(DateUtils.truncate(utilServices.getCurrentDate(), Calendar.DATE))) {
			registrosContables.addAll(estadoCuentaPosicionDAOV2.buscarRegistrosContablesControladasHistoricos(criterio, idsEmisiones));

			Collections.sort(registrosContables, new Comparator<RegistroContablePosicionControladaDTO>() {

				public int compare(RegistroContablePosicionControladaDTO o1, RegistroContablePosicionControladaDTO o2) {

					return (String.valueOf(o1.getPosicion().getEmision().getId()) + String.valueOf(o1.getPosicion().getBoveda().getId()) + String
							.valueOf(String.valueOf(o1.getPosicion().getCuenta().getIdCuenta())))
							.compareTo(String.valueOf(o2.getPosicion().getEmision().getId()) + String.valueOf(o2.getPosicion().getBoveda().getId())
									+ String.valueOf(String.valueOf(o2.getPosicion().getCuenta().getIdCuenta())));
				}

			});
		}

		EmisionDTO emision = null;
		BovedaDTO boveda = null;
		EstadoCuentaPosicionPorEmisionDTO detalleEmision = null;
		DetalleEstadoCuentaPosicionPorBovedaDTO detalleBoveda = null;

		BigInteger posicionTotal = new BigInteger("0");
		RegistroContablePosicionControladaDTO registro = null;

		Set<PosicionDTO> posiciones = new TreeSet<PosicionDTO>(new Comparator<PosicionDTO>() {

			public int compare(PosicionDTO o1, PosicionDTO o2) {
				return (String.valueOf(o1.getEmision().getId()) + String.valueOf(o1.getBoveda().getId()) + String.valueOf(String.valueOf(o1.getCuenta()
						.getIdCuenta()))).compareTo(String.valueOf(o2.getEmision().getId()) + String.valueOf(o2.getBoveda().getId())
						+ String.valueOf(String.valueOf(o2.getCuenta().getIdCuenta())));
			}
		});
		
		posiciones.addAll(posicionesFinales);
		posiciones.addAll(posicionesIniciales);
		
		Iterator<RegistroContablePosicionControladaDTO> itRegistros = registrosContables.iterator();

		for (Iterator<PosicionDTO> itPosiciones = posiciones.iterator(); itPosiciones.hasNext();) {

			PosicionDTO posicion = itPosiciones.next();

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

				EstadoPosicionDTO estadoPosicionFinal = new EstadoPosicionDTO();
				EstadoPosicionDTO estadoPosicionInicial = new EstadoPosicionDTO();
				PosicionDTO posicionFinal = null;
				PosicionDTO posicionInicial = null;

				int indicePosFin = posicionesFinales.indexOf(posicion);
				int indicePosIni = posicionesIniciales.indexOf(posicion);
				if (indicePosFin != -1) {
					posicionFinal = posicionesFinales.get(indicePosFin);
				}
				if (indicePosIni != -1) {
					posicionInicial = posicionesIniciales.get(indicePosIni);
				}

				if (posicionInicial != null) {
					estadoPosicionInicial.setPosicionInicial(posicionInicial.getPosicionNumerica().toBigInteger());
				} else {
					estadoPosicionInicial.setPosicionInicial(new BigInteger("0"));
				}

				if (posicionFinal != null) {
					estadoPosicionFinal.setPosicionFinal(posicionFinal.getPosicionNumerica().toBigInteger());
				} else {
					estadoPosicionFinal.setPosicionFinal(new BigInteger("0"));
				}

				detalleBoveda.setEstadoPosicionFinal(estadoPosicionFinal);
				detalleBoveda.setEstadoPosicionInicial(estadoPosicionInicial);

				posicionTotal = estadoPosicionInicial.getPosicionInicial();
			}

			if (detalleEmision != null && detalleBoveda != null) {

				String descripcionRegistro = null;

				if (registro == null && itRegistros.hasNext()) {
					registro = itRegistros.next();
				}

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

		//Coloca a null las listas
		posicionesIniciales = null;
		posicionesFinales = null;
		registrosContables = null;
		
		return detalleEstadoCuenta;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.estadocuenta.ConsultaEstadoCuentaPosicionService#consultarEstadoDeCuentaPosicionesNombrada(com.indeval.portaldali.middleware.dto.PosicionDTO,
	 *      java.util.List)
	 */
	public List<EstadoCuentaPosicionPorEmisionDTO> consultarEstadoDeCuentaPosicionesNombrada(PosicionDTO criterio, List<Long> idsEmisiones) {

		List<PosicionDTO> posicionesIniciales = null;
		List<PosicionDTO> posicionesFinales = null;
		List<RegistroContablePosicionNombradaDTO> registrosContables = new ArrayList<RegistroContablePosicionNombradaDTO>();
		List<EstadoCuentaPosicionPorEmisionDTO> detalleEstadoCuenta = new ArrayList<EstadoCuentaPosicionPorEmisionDTO>();

		if (DateUtils.isSameDay(criterio.getFechaFinal(), utilServices.getCurrentDate())) {
			posicionesFinales = estadoCuentaPosicionDAOV2.buscarPosicionesNombradas(criterio, idsEmisiones);
			registrosContables.addAll(estadoCuentaPosicionDAOV2.buscarRegistrosContablesNombradas(criterio, idsEmisiones));
		} else {
			posicionesFinales = estadoCuentaPosicionDAOV2.buscarPosicionesNombradasHistoricas(criterio, DateUtils.truncate(criterio.getFechaFinal(),
					Calendar.DATE), idsEmisiones);
		}

		posicionesIniciales = estadoCuentaPosicionDAOV2.buscarPosicionesNombradasHistoricas(criterio, utilServices.agregarDiasHabiles(
				criterio.getFechaInicio(), -1), idsEmisiones);

		if (DateUtils.truncate(criterio.getFechaInicio(), Calendar.DATE).before(DateUtils.truncate(utilServices.getCurrentDate(), Calendar.DATE))) {
			registrosContables.addAll(estadoCuentaPosicionDAOV2.buscarRegistrosContablesNombradasHistoricos(criterio, idsEmisiones));

			Collections.sort(registrosContables, new Comparator<RegistroContablePosicionNombradaDTO>() {

				public int compare(RegistroContablePosicionNombradaDTO o1, RegistroContablePosicionNombradaDTO o2) {

					return (String.valueOf(o1.getPosicion().getEmision().getId()) + String.valueOf(o1.getPosicion().getBoveda().getId()) + String
							.valueOf(String.valueOf(o1.getPosicion().getCuenta().getIdCuenta())))
							.compareTo(String.valueOf(o2.getPosicion().getEmision().getId()) + String.valueOf(o2.getPosicion().getBoveda().getId())
									+ String.valueOf(String.valueOf(o2.getPosicion().getCuenta().getIdCuenta())));
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
		Set<PosicionDTO> posiciones = new TreeSet<PosicionDTO>(new Comparator<PosicionDTO>() {

			public int compare(PosicionDTO o1, PosicionDTO o2) {
				return (String.valueOf(o1.getEmision().getId()) + String.valueOf(o1.getBoveda().getId()) + String.valueOf(String.valueOf(o1.getCuenta()
						.getIdCuenta()))).compareTo(String.valueOf(o2.getEmision().getId()) + String.valueOf(o2.getBoveda().getId())
						+ String.valueOf(String.valueOf(o2.getCuenta().getIdCuenta())));
			}
		});

		posiciones.addAll(posicionesIniciales);
		posiciones.addAll(posicionesFinales);

		Iterator<RegistroContablePosicionNombradaDTO> itRegistros = registrosContables.iterator();

		for (Iterator<PosicionDTO> itPosiciones = posiciones.iterator(); itPosiciones.hasNext();) {

			PosicionDTO posicion = itPosiciones.next();

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

				EstadoPosicionDTO estadoPosicionFinal = new EstadoPosicionDTO();
				EstadoPosicionDTO estadoPosicionInicial = new EstadoPosicionDTO();
				PosicionDTO posicionFinal = null;
				PosicionDTO posicionInicial = null;

				int indicePosFin = posicionesFinales.indexOf(posicion);
				int indicePosIni = posicionesIniciales.indexOf(posicion);
				if (indicePosFin != -1) {
					posicionFinal = posicionesFinales.get(indicePosFin);
				}
				if (indicePosIni != -1) {
					posicionInicial = posicionesIniciales.get(indicePosIni);
				}

				if (posicionInicial != null) {
					estadoPosicionInicial.setPosicionInicial(posicionInicial.getPosicionNumerica().toBigInteger());
					estadoPosicionInicial.setPosicionInicialDisponible(posicionInicial.getPosicionDisponibleNumerica().toBigInteger());
					estadoPosicionInicial.setPosicionInicialNoDisponible(posicionInicial.getPosicionNoDisponibleNumerica().toBigInteger());
				} else {
					estadoPosicionInicial.setPosicionInicial(new BigInteger("0"));
					estadoPosicionInicial.setPosicionInicialDisponible(new BigInteger("0"));
					estadoPosicionInicial.setPosicionInicialNoDisponible(new BigInteger("0"));
				}

				if (posicionFinal != null) {
					estadoPosicionFinal.setPosicionFinal(posicionFinal.getPosicionNumerica().toBigInteger());
					estadoPosicionFinal.setPosicionFinalDisponible(posicionFinal.getPosicionDisponibleNumerica().toBigInteger());
					estadoPosicionFinal.setPosicionFinalNoDisponible(posicionFinal.getPosicionNoDisponibleNumerica().toBigInteger());
				} else {
					estadoPosicionFinal.setPosicionFinal(new BigInteger("0"));
					estadoPosicionFinal.setPosicionFinalDisponible(new BigInteger("0"));
					estadoPosicionFinal.setPosicionFinalNoDisponible(new BigInteger("0"));
				}

				detalleBoveda.setEstadoPosicionFinal(estadoPosicionFinal);
				detalleBoveda.setEstadoPosicionInicial(estadoPosicionInicial);

				posicionDisponible = detalleBoveda.getEstadoPosicionInicial().getPosicionInicialDisponible();
				posicionNoDisponible = detalleBoveda.getEstadoPosicionInicial().getPosicionInicialNoDisponible();
				posicionTotal = posicionDisponible.add(posicionNoDisponible);
			}

			if (detalleEmision != null && detalleBoveda != null) {

				String descripcionRegistro = null;
				String descripcionContraparte = null;
				String cuentaContraparte = null;

				if (registro == null && itRegistros.hasNext()) {
					registro = itRegistros.next();
				}

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

		//Coloca a null las listas
		posicionesIniciales = null;
		posicionesFinales = null;
		registrosContables = null;

		return detalleEstadoCuenta;
	}

	/**
	 * @see com.indeval.portaldali.middleware.services.estadocuenta.ConsultaEstadoCuentaPosicionService#obtenerProyeccionDeRegistrosContablesDePosiciones(com.indeval.portaldali.middleware.dto.PosicionDTO)
	 */
	public long obtenerProyeccionDeRegistrosContablesDePosiciones(PosicionDTO criterio) {

		long totalRegistros = 0;

		if (DateUtils.isSameDay(criterio.getFechaFinal(), utilServices.getCurrentDate())) {
			if (criterio.getCuenta().getTipoTenencia().getTipoCuenta().getId().equals(TipoCuentaConstants.TIPO_CONTROLADA)) {
				totalRegistros += estadoCuentaPosicionDAOV2.obtenerProyeccionRegistrosContablesControladas(criterio);
			}
			else {
				totalRegistros += estadoCuentaPosicionDAOV2.obtenerProyeccionRegistrosContablesNombradas(criterio);
			}
		}
		if (DateUtils.truncate(criterio.getFechaInicio(), Calendar.DATE).before(DateUtils.truncate(utilServices.getCurrentDate(), Calendar.DATE))) {
			if (criterio.getCuenta().getTipoTenencia().getTipoCuenta().getId().equals(TipoCuentaConstants.TIPO_CONTROLADA)) {
				totalRegistros += estadoCuentaPosicionDAOV2.obtenerProyeccionRegistrosContablesControladasHistorico(criterio);
			}
			else {
				totalRegistros += estadoCuentaPosicionDAOV2.obtenerProyeccionRegistrosContablesNombradasHistorico(criterio);
			}
		}

		return totalRegistros;
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
	 * Obtiene el valor del atributo estadoCuentaPosicionDAOV2
	 * 
	 * @return el valor del atributo estadoCuentaPosicionDAOV2
	 */
	public EstadoCuentaPosicionDAOV2 getEstadoCuentaPosicionDAOV2() {
		return estadoCuentaPosicionDAOV2;
	}

	/**
	 * Establece el valor del atributo estadoCuentaPosicionDAOV2
	 * 
	 * @param estadoCuentaPosicionDAOV2
	 *            el valor del atributo estadoCuentaPosicionDAOV2 a establecer
	 */
	public void setEstadoCuentaPosicionDAOV2(EstadoCuentaPosicionDAOV2 estadoCuentaPosicionDAOV2) {
		this.estadoCuentaPosicionDAOV2 = estadoCuentaPosicionDAOV2;
	}

	/**
	 * Obtiene el valor del atributo posicionDAO
	 * 
	 * @return el valor del atributo posicionDAO
	 */
	public PosicionDAO getPosicionDAO() {
		return posicionDAO;
	}

	/**
	 * Establece el valor del atributo posicionDAO
	 * 
	 * @param posicionDAO
	 *            el valor del atributo posicionDAO a establecer
	 */
	public void setPosicionDAO(PosicionDAO posicionDAO) {
		this.posicionDAO = posicionDAO;
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

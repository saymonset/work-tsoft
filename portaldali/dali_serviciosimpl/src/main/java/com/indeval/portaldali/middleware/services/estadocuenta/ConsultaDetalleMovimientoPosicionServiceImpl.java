/**
 * 2H Software SA de CV
 * 
 * Sistema de Consulta de Estado de Cuenta - Indeval
 *
 * Fecha de creación: Dec 24, 2007
 */
package com.indeval.portaldali.middleware.services.estadocuenta;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.indeval.portaldali.middleware.dto.DefinicionDetalleMovimientoDTO;
import com.indeval.portaldali.middleware.dto.DetalleMovimientoValorDTO;
import com.indeval.portaldali.middleware.dto.DetalleParcialidadDTO;
import com.indeval.portaldali.middleware.dto.OperacionPosicionDTO;
import com.indeval.portaldali.middleware.dto.RegistroContablePosicionNombradaDTO;
import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.middleware.dto.util.DefinicionDetallesMovimientosHelper;
import com.indeval.portaldali.middleware.services.common.constants.TipoCargoAValores;
import com.indeval.portaldali.middleware.services.common.constants.TipoOperacionConstants;
import com.indeval.portaldali.middleware.services.common.constants.TipoTenenciaConstants;
import com.indeval.portaldali.persistence.dao.estadocuenta.DetalleMovimientoPosicionDAO;
import com.indeval.portaldali.persistence.model.RegContValNombrada;
import com.indeval.portaldali.persistence.model.RegContValNombradaHistorico;

/**
 * Implementación del servicio de negocio que contiene la lógica para la
 * consulta del detalle de movimientos de valores.
 * 
 * @author Sandra Flores Arrieta
 * @version 1.0
 * 
 */
public class ConsultaDetalleMovimientoPosicionServiceImpl implements ConsultaDetalleMovimientoPosicionService {

	/** DAO para ejecutar las consultas */
	private DetalleMovimientoPosicionDAO detalleMovimientoPosicionDAO = null;

	/**
	 * Helper para obtener la relación de los tipos de instrucción con los tipos
	 * de operación principal de una instrucción
	 */
	private DefinicionDetallesMovimientosHelper definicionDetallesMovimientosHelper = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaDetalleMovimientosService#consultarDetalleMovimientoValor(long,
	 *      java.lang.String, boolean)
	 */
	public DetalleMovimientoValorDTO consultarDetalleMovimientoValor(long idRegistroContable, long idInstitucion, boolean isHistorico) {

		// DTO con los datos del detalle
		DetalleMovimientoValorDTO detalleMovimientoValorDTO = null;

		if(isHistorico) {
			detalleMovimientoValorDTO = procesarDetalleHistorico(idInstitucion, detalleMovimientoPosicionDAO.consultarDetalleMovimientoHistorico(idRegistroContable));
		} else {
			detalleMovimientoValorDTO = procesarDetalle(idInstitucion, detalleMovimientoPosicionDAO.consultarDetalleMovimiento(idRegistroContable));
		}
		 
		return detalleMovimientoValorDTO;
	}
	
	
	/**
	 * Metodo con las reglas para sacar los detalles de los registros contables historicos
	 * 
	 * @param idInstitucion
	 * @param regContValNombrada Registro con los datos
	 * @return DTO con los detalles del movimiento
	 */
	private DetalleMovimientoValorDTO procesarDetalleHistorico(long idInstitucion,
			RegContValNombradaHistorico regContValNombrada) {
		DetalleMovimientoValorDTO detalleMovimientoValorDTO = null;
		
		if(idInstitucion == -1) {
			idInstitucion = regContValNombrada.getPosicion().getCuentaNombrada().getIdInstitucion().longValue();
		}
		
		String tipoOperacionPrincipal = null;
		DefinicionDetalleMovimientoDTO definicionDetalleMovimientoDTO = definicionDetallesMovimientosHelper
				.buscarDefinicionDetalleMovimiento(regContValNombrada.getOperacion().getInstruccion().getIdTipoInstruccion().longValue());

		if (definicionDetalleMovimientoDTO != null) {
			tipoOperacionPrincipal = definicionDetalleMovimientoDTO.getTipoOperacionPrincipal();
			if (TipoOperacionConstants.TIPO_OPERACION_DVP.equals(tipoOperacionPrincipal)) {
				detalleMovimientoValorDTO = DTOAssembler.crearDetalleMovimientoDVPDTO(regContValNombrada);
				detalleMovimientoValorDTO.setDetallesParcialidad(new ArrayList<DetalleParcialidadDTO>());
				RegistroContablePosicionNombradaDTO registroContable = DTOAssembler.crearRegistroContablePosicionNombradaConDatosOperacion(regContValNombrada);
				if (registroContable.getOperacion().getCuentaReceptora() != null && registroContable.getOperacion().getCuentaReceptora().getInstitucion().getId() == idInstitucion) {

					List<OperacionPosicionDTO> registros = detalleMovimientoPosicionDAO.buscarOperacionesDeInstruccionHistorico(registroContable);
					double totalCantidad = 0;
					double totalImporte = 0;
					for (OperacionPosicionDTO registro : registros) {
						DetalleParcialidadDTO detalleParcialidad = new DetalleParcialidadDTO();
						if (regContValNombrada.getOperacion().getCargoValoresA().equals(TipoCargoAValores.CARGO_AFECTA_DISPONIBLE)) {
							detalleParcialidad.setAfectaA(TipoCargoAValores.DESCRIPCION_DISPONIBLE);
						} else {
							detalleParcialidad.setAfectaA(TipoCargoAValores.DESCRIPCION_NO_DISPONIBLE);
						}
						detalleParcialidad.setEstado(registro.getInstruccionLiquidacion().getDescripcionEstadoInstruccion());
						detalleParcialidad.setFecha(registro.getInstruccionLiquidacion().getFechaLiquidacion());
						detalleParcialidad.setFolio(registro.getInstruccionLiquidacion().getFolioInstruccion().toString());
						detalleParcialidad.setImporteOperado(registro.getMonto());
						detalleParcialidad.setTotalOperacion(registro.getNumeroTitulos().doubleValue());
						totalCantidad += registro.getNumeroTitulos().doubleValue();
						totalImporte += registro.getMonto();

						detalleMovimientoValorDTO.getDetallesParcialidad().add(detalleParcialidad);
					}
					detalleMovimientoValorDTO.setCantidad(totalCantidad);
					detalleMovimientoValorDTO.setImporte(totalImporte);

				} else {

					List<Object[]> registros = detalleMovimientoPosicionDAO.buscarContrapartesDeInstruccionHistorico(registroContable);

					List<Long> idsCuentas = new ArrayList<Long>();
					for (Object[] registro : registros) {
						idsCuentas.add(((BigInteger)registro[0]).longValue());
					}

					List<Object[]> elementos = detalleMovimientoPosicionDAO.buscarCuentasYTotalPagadoDeInstruccionHistorico(idsCuentas, registroContable);

					double cantidadTotal = 0;
					double cantidadTotalEntregada = 0;
					double cantidadTotalPendiente = 0;
					double totalImporte = 0;
					double importePendiente = 0;
					double importePagado = 0;
					Iterator<Object[]> itElementos = elementos.iterator();
					for (Object[] registro : registros) {
						Object[] elemento = itElementos.next();
						DetalleParcialidadDTO detalleParcialidad = new DetalleParcialidadDTO();
						detalleParcialidad.setContraparte((String) elemento[2]);
						if (regContValNombrada.getOperacion().getInstruccion().getIdInstruccionLiquidacionOrigen() != null
								&& regContValNombrada.getOperacion().getInstruccion().getIdInstruccionLiquidacionOrigen().longValue() > 0) {
							detalleParcialidad.setFolio(regContValNombrada.getOperacion().getInstruccion().getFolioInstruccionLiquidacion().toString());
						} else {
							detalleParcialidad.setFolio(regContValNombrada.getOperacion().getInstruccion().getFolioInstruccionLiquidacion().toString());
						}
						detalleParcialidad.setFecha((Date) registro[1]);
						detalleParcialidad.setTotalOperacion(((BigInteger) registro[2]).doubleValue());
						detalleParcialidad.setTotalEntregado(((BigInteger) elemento[1]).doubleValue());
						detalleParcialidad.setPendientePorEntregar(detalleParcialidad.getTotalOperacion() - detalleParcialidad.getTotalEntregado());
						detalleParcialidad.setImporteOperado(((BigDecimal) registro[3]).doubleValue());
						detalleParcialidad.setImportePagado(((BigDecimal)elemento[3]).doubleValue());
						detalleParcialidad.setImportePendiente(detalleParcialidad.getImporteOperado().doubleValue() - detalleParcialidad.getImportePagado().doubleValue());
						detalleParcialidad.setDescripcionBovedaEfectivo((String)registro[4]);
						detalleParcialidad.setDescripcionDivisa((String)registro[5]);
						detalleMovimientoValorDTO.getDetallesParcialidad().add(detalleParcialidad);
						cantidadTotal += detalleParcialidad.getTotalOperacion();
						cantidadTotalEntregada += detalleParcialidad.getTotalEntregado();
						cantidadTotalPendiente += detalleParcialidad.getPendientePorEntregar();
						importePendiente += detalleParcialidad.getImportePendiente();
						importePagado += detalleParcialidad.getImportePagado();
						totalImporte += detalleParcialidad.getImporteOperado();
					}
					detalleMovimientoValorDTO.setCantidad(cantidadTotal);
					detalleMovimientoValorDTO.setTotalEntrega(cantidadTotalEntregada);
					detalleMovimientoValorDTO.setPendiente(cantidadTotalPendiente);
					detalleMovimientoValorDTO.setImporte(totalImporte);
					detalleMovimientoValorDTO.setImporteTotalPagado(importePagado);
					detalleMovimientoValorDTO.setImporteTotalPendiente(importePendiente);
				}

				if (regContValNombrada.getOperacion().getIdInstitucionReceptor() != null
						&& regContValNombrada.getOperacion().getIdInstitucionReceptor().longValue() == idInstitucion) {
					detalleMovimientoValorDTO.setParticipante(detalleMovimientoValorDTO.getReceptor());
					detalleMovimientoValorDTO.setContraparte(detalleMovimientoValorDTO.getTraspasante());
					detalleMovimientoValorDTO.setParticipanteReceptor(true);
				} else {
					detalleMovimientoValorDTO.setParticipante(detalleMovimientoValorDTO.getTraspasante());
					detalleMovimientoValorDTO.setContraparte(detalleMovimientoValorDTO.getReceptor());
					detalleMovimientoValorDTO.setParticipanteReceptor(false);
				}
			} else if (TipoOperacionConstants.TIPO_OPERACION_TLP.equals(tipoOperacionPrincipal)) {
				detalleMovimientoValorDTO = DTOAssembler.crearDetalleMovimientoTLPDTO(regContValNombrada);
				if(regContValNombrada.getOperacion().getIdInstitucionReceptor() == null || 
						regContValNombrada.getOperacion().getIdInstitucionTraspasante() == null) {
					detalleMovimientoValorDTO.setSinContraparte(true);
				}
				if (regContValNombrada.getOperacion().getIdInstitucionReceptor() != null
						&& regContValNombrada.getOperacion().getIdInstitucionReceptor().longValue() == idInstitucion) {
					detalleMovimientoValorDTO.setParticipante(detalleMovimientoValorDTO.getReceptor());
					detalleMovimientoValorDTO.setContraparte(detalleMovimientoValorDTO.getTraspasante());
					detalleMovimientoValorDTO.setParticipanteReceptor(true);
				} else {
					detalleMovimientoValorDTO.setParticipante(detalleMovimientoValorDTO.getTraspasante());
					detalleMovimientoValorDTO.setContraparte(detalleMovimientoValorDTO.getReceptor());
					detalleMovimientoValorDTO.setParticipanteReceptor(false);
				}
			} else if (TipoOperacionConstants.TIPO_OPERACION_DVD.equals(tipoOperacionPrincipal)) {
				detalleMovimientoValorDTO = DTOAssembler.crearDetalleMovimientoDVDDTO(regContValNombrada);

				if (TipoTenenciaConstants.CLAVE_SUBGRUPO_GARANTIAS.equals(regContValNombrada.getOperacion().getCuentaNombradaReceptor().getTipoCuenta()
						.getClaveSubgrupo())) {

					detalleMovimientoValorDTO.setDetallePrestamo(detalleMovimientoPosicionDAO.buscarDetalleDePrestamoHistorico(regContValNombrada.getOperacion()
							.getInstruccion().getIdInstruccionLiquidacion().longValue()));
					detalleMovimientoValorDTO.setParticipante(detalleMovimientoValorDTO.getTraspasante());
					detalleMovimientoValorDTO.setContraparte(detalleMovimientoValorDTO.getReceptor());
					detalleMovimientoValorDTO.setParticipanteReceptor(true);
					detalleMovimientoValorDTO.setDetalleGarantias(false);
				} else {

					detalleMovimientoValorDTO.setDetallesGarantias(detalleMovimientoPosicionDAO.buscarGarantiasDePrestamosHistorico(regContValNombrada.getOperacion()
							.getInstruccion().getIdInstruccionLiquidacion().longValue()));
					if (regContValNombrada.getOperacion().getIdInstitucionReceptor() != null
							&& regContValNombrada.getOperacion().getIdInstitucionReceptor().longValue() == idInstitucion) {
						detalleMovimientoValorDTO.setParticipante(detalleMovimientoValorDTO.getReceptor());
						detalleMovimientoValorDTO.setContraparte(detalleMovimientoValorDTO.getTraspasante());
						detalleMovimientoValorDTO.setParticipanteReceptor(true);
						
					} else {
						detalleMovimientoValorDTO.setParticipante(detalleMovimientoValorDTO.getTraspasante());
						detalleMovimientoValorDTO.setContraparte(detalleMovimientoValorDTO.getReceptor());
						detalleMovimientoValorDTO.setParticipanteReceptor(false);
					}
					detalleMovimientoValorDTO.setDetalleGarantias(true);
				}
			}

			detalleMovimientoValorDTO.getPosicion().getEmision().setCupon(
					regContValNombrada.getOperacion().getCupon() != null ? regContValNombrada.getOperacion().getCupon().getClaveCupon() : "");
		}

		return detalleMovimientoValorDTO;
	}

	/**
	 * Metodo con las reglas para sacar los detalles de los registros contables al dia de hoy
	 * 
	 * @param idInstitucion
	 * @param regContValNombrada Registro con los datos
	 * @return DTO con los detalles del movimiento
	 */
	private DetalleMovimientoValorDTO procesarDetalle(long idInstitucion,
			RegContValNombrada regContValNombrada) {
		DetalleMovimientoValorDTO detalleMovimientoValorDTO = null;
		
		if(idInstitucion == -1) {
			idInstitucion = regContValNombrada.getPosicion().getCuentaNombrada().getIdInstitucion().longValue();
		}
		
		String tipoOperacionPrincipal = null;
		DefinicionDetalleMovimientoDTO definicionDetalleMovimientoDTO = definicionDetallesMovimientosHelper
				.buscarDefinicionDetalleMovimiento(regContValNombrada.getOperacion().getInstruccion().getIdTipoInstruccion().longValue());

		if (definicionDetalleMovimientoDTO != null) {
			tipoOperacionPrincipal = definicionDetalleMovimientoDTO.getTipoOperacionPrincipal();
			if (TipoOperacionConstants.TIPO_OPERACION_DVP.equals(tipoOperacionPrincipal)) {
				detalleMovimientoValorDTO = DTOAssembler.crearDetalleMovimientoDVPDTO(regContValNombrada);
				detalleMovimientoValorDTO.setDetallesParcialidad(new ArrayList<DetalleParcialidadDTO>());
				RegistroContablePosicionNombradaDTO registroContable = DTOAssembler.crearRegistroContablePosicionNombradaConDatosOperacion(regContValNombrada);
				if (registroContable.getOperacion().getCuentaReceptora() != null && registroContable.getOperacion().getCuentaReceptora().getInstitucion().getId() == idInstitucion) {

					List<OperacionPosicionDTO> registros = detalleMovimientoPosicionDAO.buscarOperacionesDeInstruccion(registroContable);
					double totalCantidad = 0;
					double totalImporte = 0;
					for (OperacionPosicionDTO registro : registros) {
						DetalleParcialidadDTO detalleParcialidad = new DetalleParcialidadDTO();
						if(regContValNombrada.getOperacion().getCargoValoresA() != null)
						{
							if (regContValNombrada.getOperacion().getCargoValoresA().intValue() == TipoCargoAValores.CARGO_AFECTA_DISPONIBLE.intValue() ) {
								detalleParcialidad.setAfectaA(TipoCargoAValores.DESCRIPCION_DISPONIBLE);
							} else {
								detalleParcialidad.setAfectaA(TipoCargoAValores.DESCRIPCION_NO_DISPONIBLE);
							}
						}
						detalleParcialidad.setEstado(registro.getInstruccionLiquidacion().getDescripcionEstadoInstruccion());
						detalleParcialidad.setFecha(registro.getInstruccionLiquidacion().getFechaLiquidacion());
						detalleParcialidad.setFolio(registro.getInstruccionLiquidacion().getFolioInstruccion().toString());
						detalleParcialidad.setImporteOperado(registro.getMonto());
						detalleParcialidad.setTotalOperacion(registro.getNumeroTitulos().doubleValue());
						totalCantidad += registro.getNumeroTitulos().doubleValue();
						totalImporte += registro.getMonto();

						detalleMovimientoValorDTO.getDetallesParcialidad().add(detalleParcialidad);
					}
					detalleMovimientoValorDTO.setCantidad(totalCantidad);
					detalleMovimientoValorDTO.setImporte(totalImporte);

				} else {

					List<Object[]> registros = detalleMovimientoPosicionDAO.buscarContrapartesDeInstruccion(registroContable);

					List<Long> idsCuentas = new ArrayList<Long>();
					for (Object[] registro : registros) {
						idsCuentas.add(((BigInteger)registro[0]).longValue());
					}

					List<Object[]> elementos = detalleMovimientoPosicionDAO.buscarCuentasYTotalPagadoDeInstruccion(idsCuentas, registroContable);

					double cantidadTotal = 0;
					double cantidadTotalEntregada = 0;
					double cantidadTotalPendiente = 0;
					double totalImporte = 0;
					double importePendiente = 0;
					double importePagado = 0;
					Iterator<Object[]> itElementos = elementos.iterator();
					for (Object[] registro : registros) {
						Object[] elemento = itElementos.next();
						DetalleParcialidadDTO detalleParcialidad = new DetalleParcialidadDTO();
						detalleParcialidad.setContraparte((String) elemento[2]);
						if (regContValNombrada.getOperacion().getInstruccion().getIdInstruccionLiquidacionOrigen() != null
								&& regContValNombrada.getOperacion().getInstruccion().getIdInstruccionLiquidacionOrigen().longValue() > 0) {
							detalleParcialidad.setFolio(regContValNombrada.getOperacion().getInstruccion().getFolioInstruccionLiquidacion().toString());
						} else {
							detalleParcialidad.setFolio(regContValNombrada.getOperacion().getInstruccion().getFolioInstruccionLiquidacion().toString());
						}
						detalleParcialidad.setFecha((Date) registro[1]);
						detalleParcialidad.setTotalOperacion(((BigInteger) registro[2]).doubleValue());
						detalleParcialidad.setTotalEntregado(((BigInteger) elemento[1]).doubleValue());
						detalleParcialidad.setPendientePorEntregar(detalleParcialidad.getTotalOperacion() - detalleParcialidad.getTotalEntregado());
						detalleParcialidad.setImporteOperado(((BigDecimal) registro[3]).doubleValue());
						detalleParcialidad.setImportePagado(((BigDecimal)elemento[3]).doubleValue());
						detalleParcialidad.setImportePendiente(detalleParcialidad.getImporteOperado().doubleValue() - detalleParcialidad.getImportePagado().doubleValue());
						detalleParcialidad.setDescripcionBovedaEfectivo((String)registro[4]);
						detalleParcialidad.setDescripcionDivisa((String)registro[5]);
						detalleMovimientoValorDTO.getDetallesParcialidad().add(detalleParcialidad);
						cantidadTotal += detalleParcialidad.getTotalOperacion();
						cantidadTotalEntregada += detalleParcialidad.getTotalEntregado();
						cantidadTotalPendiente += detalleParcialidad.getPendientePorEntregar();
						importePendiente += detalleParcialidad.getImportePendiente();
						importePagado += detalleParcialidad.getImportePagado();
						totalImporte += detalleParcialidad.getImporteOperado();
					}
					detalleMovimientoValorDTO.setCantidad(cantidadTotal);
					detalleMovimientoValorDTO.setTotalEntrega(cantidadTotalEntregada);
					detalleMovimientoValorDTO.setPendiente(cantidadTotalPendiente);
					detalleMovimientoValorDTO.setImporte(totalImporte);
					detalleMovimientoValorDTO.setImporteTotalPagado(importePagado);
					detalleMovimientoValorDTO.setImporteTotalPendiente(importePendiente);
				}

				if (regContValNombrada.getOperacion().getIdInstitucionReceptor() != null
						&& regContValNombrada.getOperacion().getIdInstitucionReceptor().longValue() == idInstitucion) {
					detalleMovimientoValorDTO.setParticipante(detalleMovimientoValorDTO.getReceptor());
					detalleMovimientoValorDTO.setContraparte(detalleMovimientoValorDTO.getTraspasante());
					detalleMovimientoValorDTO.setParticipanteReceptor(true);
				} else {
					detalleMovimientoValorDTO.setParticipante(detalleMovimientoValorDTO.getTraspasante());
					detalleMovimientoValorDTO.setContraparte(detalleMovimientoValorDTO.getReceptor());
					detalleMovimientoValorDTO.setParticipanteReceptor(false);
				}
			} else if (TipoOperacionConstants.TIPO_OPERACION_TLP.equals(tipoOperacionPrincipal)) {
				detalleMovimientoValorDTO = DTOAssembler.crearDetalleMovimientoTLPDTO(regContValNombrada);
				if(regContValNombrada.getOperacion().getIdInstitucionReceptor() == null || 
						regContValNombrada.getOperacion().getIdInstitucionTraspasante() == null) {
					detalleMovimientoValorDTO.setSinContraparte(true);
				}
				if (regContValNombrada.getOperacion().getIdInstitucionReceptor() != null
						&& regContValNombrada.getOperacion().getIdInstitucionReceptor().longValue() == idInstitucion) {
					detalleMovimientoValorDTO.setParticipante(detalleMovimientoValorDTO.getReceptor());
					detalleMovimientoValorDTO.setContraparte(detalleMovimientoValorDTO.getTraspasante());
					detalleMovimientoValorDTO.setParticipanteReceptor(true);
				} else {
					detalleMovimientoValorDTO.setParticipante(detalleMovimientoValorDTO.getTraspasante());
					detalleMovimientoValorDTO.setContraparte(detalleMovimientoValorDTO.getReceptor());
					detalleMovimientoValorDTO.setParticipanteReceptor(false);
				}
			} else if (TipoOperacionConstants.TIPO_OPERACION_DVD.equals(tipoOperacionPrincipal)) {
				detalleMovimientoValorDTO = DTOAssembler.crearDetalleMovimientoDVDDTO(regContValNombrada);

				if (TipoTenenciaConstants.CLAVE_SUBGRUPO_GARANTIAS.equals(regContValNombrada.getOperacion().getCuentaNombradaReceptor().getTipoCuenta()
						.getClaveSubgrupo())) {

					detalleMovimientoValorDTO.setDetallePrestamo(detalleMovimientoPosicionDAO.buscarDetalleDePrestamo(regContValNombrada.getOperacion()
							.getInstruccion().getIdInstruccionLiquidacion().longValue()));
					detalleMovimientoValorDTO.setParticipante(detalleMovimientoValorDTO.getTraspasante());
					detalleMovimientoValorDTO.setContraparte(detalleMovimientoValorDTO.getReceptor());
					detalleMovimientoValorDTO.setParticipanteReceptor(true);
					detalleMovimientoValorDTO.setDetalleGarantias(false);
				} else {

					detalleMovimientoValorDTO.setDetallesGarantias(detalleMovimientoPosicionDAO.buscarGarantiasDePrestamos(regContValNombrada.getOperacion()
							.getInstruccion().getIdInstruccionLiquidacion().longValue()));
					if (regContValNombrada.getOperacion().getIdInstitucionReceptor() != null
							&& regContValNombrada.getOperacion().getIdInstitucionReceptor().longValue() == idInstitucion) {
						detalleMovimientoValorDTO.setParticipante(detalleMovimientoValorDTO.getReceptor());
						detalleMovimientoValorDTO.setContraparte(detalleMovimientoValorDTO.getTraspasante());
						detalleMovimientoValorDTO.setParticipanteReceptor(true);
						
					} else {
						detalleMovimientoValorDTO.setParticipante(detalleMovimientoValorDTO.getTraspasante());
						detalleMovimientoValorDTO.setContraparte(detalleMovimientoValorDTO.getReceptor());
						detalleMovimientoValorDTO.setParticipanteReceptor(false);
					}
					detalleMovimientoValorDTO.setDetalleGarantias(true);
				}
			}

			detalleMovimientoValorDTO.getPosicion().getEmision().setCupon(
					regContValNombrada.getOperacion().getCupon() != null ? regContValNombrada.getOperacion().getCupon().getClaveCupon() : "");
		}

		return detalleMovimientoValorDTO;
	}

	/**
	 * Obtiene el valor del atributo detalleMovimientoPosicionDAO
	 * 
	 * @return el valor del atributo detalleMovimientoPosicionDAO
	 */
	public DetalleMovimientoPosicionDAO getDetalleMovimientoPosicionDAO() {
		return detalleMovimientoPosicionDAO;
	}

	/**
	 * Establece el valor del atributo detalleMovimientoPosicionDAO
	 * 
	 * @param detalleMovimientoPosicionDAO
	 *            el valor del atributo detalleMovimientoPosicionDAO a
	 *            establecer.
	 */
	public void setDetalleMovimientoPosicionDAO(DetalleMovimientoPosicionDAO detalleMovimientoPosicionDAO) {
		this.detalleMovimientoPosicionDAO = detalleMovimientoPosicionDAO;
	}

	/**
	 * Obtiene el campo definicionDetallesMovimientosHelper
	 * @return  definicionDetallesMovimientosHelper
	 */
	public DefinicionDetallesMovimientosHelper getDefinicionDetallesMovimientosHelper() {
		return definicionDetallesMovimientosHelper;
	}

	/**
	 * Asigna el valor del campo definicionDetallesMovimientosHelper
	 * @param definicionDetallesMovimientosHelper el valor de definicionDetallesMovimientosHelper a asignar
	 */
	public void setDefinicionDetallesMovimientosHelper(DefinicionDetallesMovimientosHelper definicionDetallesMovimientosHelper) {
		this.definicionDetallesMovimientosHelper = definicionDetallesMovimientosHelper;
	}

}

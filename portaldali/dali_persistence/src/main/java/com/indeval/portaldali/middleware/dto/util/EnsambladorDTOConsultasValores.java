/**
 * Bursatec - Portal DALI
 * Copyrigth (c) 2014 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.dto.util;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.CuponDTO;
import com.indeval.portaldali.middleware.dto.EmisionDTO;
import com.indeval.portaldali.middleware.dto.EmisoraDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.InstruccionLiquidacionDTO;
import com.indeval.portaldali.middleware.dto.OperacionPosicionDTO;
import com.indeval.portaldali.middleware.dto.PosicionDTO;
import com.indeval.portaldali.middleware.dto.RegistroContablePosicionControladaDTO;
import com.indeval.portaldali.middleware.dto.RegistroContablePosicionNombradaDTO;
import com.indeval.portaldali.middleware.dto.SerieDTO;
import com.indeval.portaldali.middleware.dto.TipoAccionDTO;
import com.indeval.portaldali.middleware.dto.TipoCuentaDTO;
import com.indeval.portaldali.middleware.dto.TipoInstruccionDTO;
import com.indeval.portaldali.middleware.dto.TipoNaturalezaDTO;
import com.indeval.portaldali.middleware.dto.TipoOperacionDTO;
import com.indeval.portaldali.middleware.dto.TipoTenenciaDTO;
import com.indeval.portaldali.middleware.dto.TipoValorDTO;
import com.indeval.portaldali.persistence.model.CuentaControlada;
import com.indeval.portaldali.persistence.model.CuentaNombrada;
import com.indeval.portaldali.persistence.model.Emision;
import com.indeval.portaldali.persistence.model.OperacionNombrada;
import com.indeval.portaldali.persistence.model.OperacionNombradaHistorico;
import com.indeval.portaldali.persistence.model.PosicionControlada;
import com.indeval.portaldali.persistence.model.PosicionControladaHistorico;
import com.indeval.portaldali.persistence.model.PosicionNombrada;
import com.indeval.portaldali.persistence.model.PosicionNombradaHistorico;
import com.indeval.portaldali.persistence.model.RegContValControlada;
import com.indeval.portaldali.persistence.model.RegContValControladaHistorico;
import com.indeval.portaldali.persistence.model.RegContValNombrada;
import com.indeval.portaldali.persistence.model.RegContValNombradaHistorico;
import com.indeval.portaldali.persistence.model.TipoCuenta;
import com.indeval.portaldali.persistence.model.TipoInstruccion;
import com.indeval.portaldali.persistence.vo.EmisionPersistence;


/**
 * Ensamblador de DTOs para las consultas de efectivo.
 * 
 * @author Pablo Balderas
 */
public class EnsambladorDTOConsultasValores {

	/**
	 * Crea un objeto del tipo {@link RegistroContablePosicionControladaDTO} a partir de un objeto de hibernate del tipo {@link RegContValControlada}
	 * 
	 * @param regContable Objeto de hibernate para construir el DTO
	 * @return DTO creado a partir del parámetro de entrada
	 */
	public static RegistroContablePosicionControladaDTO crearRegContablePosicionControlada(RegContValControlada regContValControlada) {
		RegistroContablePosicionControladaDTO dto = null;
		if (regContValControlada != null) {
			dto = new RegistroContablePosicionControladaDTO();
			dto.setCantidad(regContValControlada.getCantidad());
			dto.setFecha(regContValControlada.getFecha());
			dto.setIdCiclo(regContValControlada.getIdCiclo().longValue());
			dto.setIdRegistroContable(regContValControlada.getIdRegistroContable().longValue());
			dto.setPosicion(crearPosicionControladaDTO(regContValControlada.getPosicionControlada()));
			if(regContValControlada.getTipoAccion() != null) {				
				dto.setTipoAccion(new TipoAccionDTO(regContValControlada.getTipoAccion().getValue()));
			}
		}
		return dto;
	}
	
	/**
	 * Crea un objeto {@link RegistroContablePosicionControladaDTO} a partir de un objeto {@link RegContValControladaHistorico}
	 * 
	 * @param posicionHistorica el BO del cual se toman los datos.
	 * @return el DTO creado a partir del BO.
	 */
	public static RegistroContablePosicionControladaDTO crearRegContablePosicionControladaHistorica(RegContValControladaHistorico registro) {
		RegistroContablePosicionControladaDTO dto = null;
		if (registro != null) {
			dto = new RegistroContablePosicionControladaDTO();
			dto.setHistorico(true);
			dto.setCantidad(registro.getCantidad());
			dto.setFecha(registro.getFecha());
			dto.setIdCiclo(registro.getIdCiclo().longValue());
			dto.setIdRegistroContable(registro.getIdRegistroContable().longValue());
			dto.setPosicion(crearPosicionControladaHistoricaDTO(registro.getPosicionControlada()));
			if(registro.getTipoAccion() != null) {				
				dto.setTipoAccion(new TipoAccionDTO(registro.getTipoAccion().getValue()));
			}
		}

		return dto;
	}
	
	/**
	 * Crea un objeto del tipo {@link RegistroContablePosicionNombradaDTO} a partir de un objeto de hibernate del tipo {@link RegContValNombrada}
	 * 
	 * @param operacion Objeto de hibernate que sirve de base para crear el DTO de salida.
	 * @return DTO creado a partir del parámetro de entrada, null si el parámetro de entrada es también nulo.
	 */
	public static RegistroContablePosicionNombradaDTO crearRegContablePosicionNombradaConDatosOperacion(RegContValNombrada registro) {
		RegistroContablePosicionNombradaDTO dto = null;
		if (registro != null) {
			dto = new RegistroContablePosicionNombradaDTO();
			dto.setIdRegistroContable(registro.getIdRegistroContable().longValue());
			dto.setPosicion(crearPosicionNombradaDTO(registro.getPosicion()));
			if(registro.getTipoAccion() != null) {				
				dto.setTipoAccion(new TipoAccionDTO(registro.getTipoAccion().getValue()));
			}
			dto.setCantidad(registro.getCantidad());
			dto.setFecha(registro.getFecha());
			dto.setCargoA(registro.getCargoA() != null ? registro.getCargoA().longValue() : null);
			dto.setOperacion(crearOperacionNombrada(registro.getOperacion()));
			dto.setFolio(registro.getOperacion().getInstruccion().getFolioInstruccion().longValue());
		}
		return dto;
	}
	
	/**
	 * Construye un objeto {@link RegistroContablePosicionNombradaDTO} a partir
	 * de un objeto {@link RegContValNombradaHistorico}
	 * 
	 * @param registro
	 *            el BO con los datos para construir el DTO.
	 * @return el DTO construido.
	 */
	public static RegistroContablePosicionNombradaDTO crearRegContablePosicionNombradaConDatosOperacion(RegContValNombradaHistorico registro) {
		RegistroContablePosicionNombradaDTO dto = null;
		if (registro != null) {
			dto = new RegistroContablePosicionNombradaDTO();
			dto.setHistorico(true);
			dto.setIdRegistroContable(registro.getIdRegistroContableHistorico().longValue());
			dto.setPosicion(crearPosicionNombradaHistoricaDTO(registro.getPosicion()));
			if(registro.getTipoAccion() != null) {				
				dto.setTipoAccion(new TipoAccionDTO(registro.getTipoAccion().getValue()));
			}
			dto.setCantidad(registro.getCantidad());
			dto.setFecha(registro.getFecha());
			dto.setCargoA(registro.getCargoA().longValue());
			dto.setOperacion(crearOperacionNombradaHistorica(registro.getOperacion()));
			dto.setFolio(registro.getOperacion().getInstruccion().getFolioInstruccion().longValue());
		}
		return dto;
	}
	
	/**
	 * Crea un objeto del tipo {@link PosicionDTO} a partir de un objeto de hibernate del tipo {@link PosicionControlada}
	 * 
	 * @param posicion Objeto de hibernate fuete para construir el DTO
	 * @return DTO creao a partir del parámetro de entrada
	 */
	public static PosicionDTO crearPosicionControladaDTO(PosicionControlada posicion) {
		PosicionDTO dto = null;
		if (posicion != null) {
			dto = new PosicionDTO();
			dto.setIdPosicion(posicion.getIdPosicion().longValue());
			dto.setBoveda(DTOAssembler.crearBovedaDTO(posicion.getBoveda()));
			dto.setCuenta(crearCuentaControladaDTO(posicion.getCuentaControlada()));
			dto.setEmision(crearEmisionDTO(posicion.getEmision()));
			dto.getEmision().setCupon(posicion.getCupon().getClaveCupon());
			if(posicion.getCupon() != null) {
				dto.setCupon(new CuponDTO(null, posicion.getCupon().getClaveCupon()));
				dto.getCupon().setEmision(new EmisionDTO(posicion.getCupon().getEmision().getIdEmision().longValue()));
			}
			dto.setPosicion(posicion.getPosicion().toString());
			dto.setPosicionNumerica(new BigDecimal(posicion.getPosicion()));
			dto.setPosicionDisponible(posicion.getPosicion().toString());
			dto.setPosicionDisponibleNumerica(new BigDecimal(posicion.getPosicion()));
			dto.setPosicionNoDisponible("0");
			dto.setPosicionNoDisponibleNumerica(new BigDecimal("0"));
		}
		return dto;
	}
	
	/**
	 * Crea un objeto {@link PosicionDTO} a partir de un objeto {@link PosicionControladaHistorico}
	 * 
	 * @param posicionHistorica el BO del cual se toman los datos.
	 * @return el DTO creado a partir del BO.
	 */
	public static PosicionDTO crearPosicionControladaHistoricaDTO(PosicionControladaHistorico posicionHistorica) {
		PosicionDTO dto = null;
		if (posicionHistorica != null) {
			dto = new PosicionDTO();
			dto.setIdPosicion(posicionHistorica.getIdPosicion().longValue());
			dto.setBoveda(DTOAssembler.crearBovedaDTO(posicionHistorica.getBoveda()));
			dto.setCuenta(crearCuentaControladaDTO(posicionHistorica.getCuentaControlada()));
			dto.setEmision(crearEmisionDTO(posicionHistorica.getEmision()));
			dto.getEmision().setCupon(posicionHistorica.getCupon().getClaveCupon());
			if(posicionHistorica.getCupon() != null) {
				dto.setCupon(new CuponDTO(null, posicionHistorica.getCupon().getClaveCupon()));
				dto.getCupon().setEmision(new EmisionDTO(posicionHistorica.getCupon().getEmision().getIdEmision().longValue()));
			}
			dto.setPosicion(posicionHistorica.getPosicion().toString());
			dto.setPosicionNumerica(new BigDecimal(posicionHistorica.getPosicion()));
			dto.setPosicionNoDisponible("0");
			dto.setPosicionDisponible(posicionHistorica.getPosicion().toString());
			dto.setPosicionDisponibleNumerica(new BigDecimal(posicionHistorica.getPosicion()));
		}
		return dto;
	}
	
	/**
	 * Crea un objeto del tipo {@link PosicionDTO} a partir de un objeto de hibernate del tipo {@link PosicionNombrada}
	 * 
	 * @param posicion Objeto de hibernate fuente para construir el DTO
	 * @return DTO creao a partir del parámetro de entrada
	 */
	public static PosicionDTO crearPosicionNombradaDTO(PosicionNombrada posicion) {
		PosicionDTO dto = null;
		if (posicion != null) {
			dto = new PosicionDTO();
			dto.setIdPosicion(posicion.getIdPosicion().longValue());
			if(posicion.getBoveda() != null) {
				dto.setBoveda(new BovedaDTO());
				dto.getBoveda().setId(posicion.getBoveda().getIdBoveda().longValue());
				dto.getBoveda().setDescripcion(posicion.getBoveda().getDescripcion());
			}
			dto.setEmision(crearEmisionDTO(posicion.getCupon().getEmision()));
			dto.getEmision().setCupon(posicion.getCupon().getClaveCupon());
			dto.setCuenta(crearCuentaNombradaDTO(posicion.getCuentaNombrada()));
			if (posicion.getCupon() != null) {
				dto.setCupon(new CuponDTO(
					posicion.getCupon().getIdCupon().longValue(), posicion.getCupon().getClaveCupon()));
				dto.getCupon().setEmision(new EmisionDTO(posicion.getEmision().getIdEmision().longValue()));		
			}
			dto.setPosicionDisponible(posicion.getPosicionDisponible().toString());
			dto.setPosicionDisponibleNumerica(new BigDecimal(posicion.getPosicionDisponible()));
			dto.setPosicionNoDisponible(posicion.getPosicionNoDisponible().toString());
			dto.setPosicionNoDisponibleNumerica(new BigDecimal(posicion.getPosicionNoDisponible()));
			dto.setPosicion(posicion.getPosicionDisponible().add(posicion.getPosicionNoDisponible()).toString());
			dto.setPosicionNumerica(new BigDecimal(posicion.getPosicionDisponible().add(posicion.getPosicionNoDisponible())));
			dto.setValuacionNominal(new BigDecimal(String.valueOf(dto.getEmision().getValorNominal())).multiply(new BigDecimal(posicion.getPosicionDisponible().add(posicion.getPosicionNoDisponible()))));
		}
		return dto;
	}
	
	/**
	 * Crea un objeto {@link PosicionDTO} a partir de un objeto {@link PosicionNombradaHistorico}
	 * 
	 * @param posicionHistorica el BO del cual se toman los datos.
	 * @return el DTO creado a partir del BO
	 */
	public static PosicionDTO crearPosicionNombradaHistoricaDTO(PosicionNombradaHistorico posicionHistorica) {
		PosicionDTO dto = null;
		if (posicionHistorica != null) {
			dto = new PosicionDTO();
			dto.setIdPosicion(posicionHistorica.getIdPosicion().longValue());
			if(posicionHistorica.getBoveda() != null) {
				dto.setBoveda(new BovedaDTO());
				dto.getBoveda().setId(posicionHistorica.getBoveda().getIdBoveda().longValue());
				dto.getBoveda().setDescripcion(posicionHistorica.getBoveda().getDescripcion());
			}
			dto.setCuenta(crearCuentaNombradaDTO(posicionHistorica.getCuentaNombrada()));
			dto.setEmision(crearEmisionDTO(posicionHistorica.getCupon().getEmision()));
			dto.getEmision().setCupon(posicionHistorica.getCupon().getClaveCupon());
			if (posicionHistorica.getCupon() != null) {
				dto.setCupon(new CuponDTO(
						posicionHistorica.getCupon().getIdCupon().longValue(), posicionHistorica.getCupon().getClaveCupon()));
				dto.getCupon().setEmision(new EmisionDTO(posicionHistorica.getEmision().getIdEmision().longValue()));		
			}
			dto.setPosicionDisponible(posicionHistorica.getPosicionDisponible().toString());
			dto.setPosicionDisponibleNumerica(new BigDecimal(posicionHistorica.getPosicionDisponible()));
			dto.setPosicionNoDisponible(posicionHistorica.getPosicionNoDisponible().toString());
			dto.setPosicionNoDisponibleNumerica(new BigDecimal(posicionHistorica.getPosicionNoDisponible()));
			dto.setPosicion(posicionHistorica.getPosicionDisponible().add(posicionHistorica.getPosicionNoDisponible()).toString());
			dto.setPosicionNumerica(new BigDecimal(posicionHistorica.getPosicionDisponible().add(posicionHistorica.getPosicionNoDisponible())));
			dto.setValuacionNominal(new BigDecimal(String.valueOf(dto.getEmision().getValorNominal())).multiply(new BigDecimal(posicionHistorica.getPosicionDisponible().add(posicionHistorica.getPosicionNoDisponible()))));
		}
		return dto;
	}
	
	/**
	 * Crea un objeto de tipo {@link OperacionPosicionDTO} a partir de un objeto de hibernate del tipo {@link OperacionNombrada}.
	 * 
	 * @param operacion Objeto de hibernate fuente para construir el DTO
	 * @return DTO creao a partir del parámetro de entrada.
	 */
	public static OperacionPosicionDTO crearOperacionNombrada(OperacionNombrada operacion) {
		OperacionPosicionDTO dto = null;
		if (operacion != null) {
			dto = new OperacionPosicionDTO();
			dto.setIdOperacion(operacion.getIdOperacion().longValue());
			dto.setFolio(operacion.getFolioOperacion().longValue());
			if(operacion.getCupon() != null) {
				dto.setCupon(new CuponDTO(
					operacion.getCupon().getIdCupon().longValue(),
					operacion.getCupon().getClaveCupon()));
			}
			if(operacion.getTipoOperacion() != null) {
				dto.setTipoOperacion(new TipoOperacionDTO());
				dto.getTipoOperacion().setDescripcion(operacion.getTipoOperacion().getDescripcion());
			}
			dto.setCuentaReceptora(crearCuentaNombradaDTO(operacion.getCuentaNombradaReceptor()));
			dto.setCuentaTraspasante(crearCuentaNombradaDTO(operacion.getCuentaNombradaTraspasante()));
			if(operacion.getInstruccion() != null) {
				dto.setInstruccionLiquidacion(new InstruccionLiquidacionDTO());
				dto.getInstruccionLiquidacion().setFolioInstruccion(operacion.getInstruccion().getFolioInstruccion().longValue());
				dto.getInstruccionLiquidacion().setTipoInstruccion(crearTipoInstruccion(operacion.getInstruccion().getTipoInstruccion()));
			}
//			operacionPosicionDTO.setMonto(operacion.getMonto() != null ? operacion.getMonto().doubleValue() : 0);
//			operacionPosicionDTO.setCargoValoresA(operacion.getCargoValoresA() != null ? operacion.getCargoValoresA().longValue() : 0);
//			operacionPosicionDTO.setNumeroTitulos(operacion.getNumeroTitulos() != null ? operacion.getNumeroTitulos().longValue() : 0);
//			operacionPosicionDTO.setPosicion(crearPosicionNombradaDTO(operacion.getPosicionNombrada()));
//			operacionPosicionDTO.setPrecio(operacion.getPrecio() != null ? operacion.getPrecio().doubleValue() : 0);
		}
		return dto;
	}
	
	/**
	 * Crea un objeto de tipo {@link OperacionPosicionDTO} a partir de un objeto
	 * de hibernate del tipo {@link OperacionNombrada}.
	 * 
	 * @param operacion
	 *            Objeto de hibernate fuente para construir el DTO
	 * @return DTO creao a partir del parámetro de entrada.
	 */
	public static OperacionPosicionDTO crearOperacionNombradaHistorica(OperacionNombradaHistorico operacion) {
		OperacionPosicionDTO dto = null;
		if (operacion != null) {
			dto = new OperacionPosicionDTO();
			dto.setIdOperacion(operacion.getIdOperacion().longValue());
			dto.setFolio(operacion.getFolioOperacion().longValue());
			if(operacion.getCupon() != null) {
				dto.setCupon(new CuponDTO(
					operacion.getCupon().getIdCupon().longValue(),
					operacion.getCupon().getClaveCupon()));
			}
			if(operacion.getTipoOperacion() != null) {
				dto.setTipoOperacion(new TipoOperacionDTO());
				dto.getTipoOperacion().setDescripcion(operacion.getTipoOperacion().getDescripcion());
			}
			dto.setCuentaReceptora(crearCuentaNombradaDTO(operacion.getCuentaNombradaReceptor()));
			dto.setCuentaTraspasante(crearCuentaNombradaDTO(operacion.getCuentaNombradaTraspasante()));
			if(operacion.getInstruccion() != null) {
				dto.setInstruccionLiquidacion(new InstruccionLiquidacionDTO());
				dto.getInstruccionLiquidacion().setFolioInstruccion(operacion.getInstruccion().getFolioInstruccion().longValue());
				dto.getInstruccionLiquidacion().setTipoInstruccion(crearTipoInstruccion(operacion.getInstruccion().getTipoInstruccion()));
			}
//			dto.setCargoEfectivoA(operacion.getCargoEfectivoA() != null ? operacion.getCargoEfectivoA().longValue() : 0);
//			dto.setMonto(operacion.getMonto() != null ? operacion.getMonto().doubleValue() : 0);
//			dto.setCargoValoresA(operacion.getCargoValoresA() != null ? operacion.getCargoValoresA().longValue() : 0);
//			dto.setNumeroTitulos(operacion.getNumeroTitulos() != null ? operacion.getNumeroTitulos().longValue() : 0);
//			dto.setPosicion(crearPosicionNombradaDTO(operacion.getPosicionNombrada()));
//			dto.setPrecio(operacion.getPrecio() != null ? operacion.getPrecio().doubleValue() : 0);
		}
		return dto;
	}
	
	/**
	 * Crea un objeto del tipo {@link CuentaDTO} a partir de un objeto de hibernate del tipo {@link CuentaControlada}
	 * 
	 * @param cuenta Objeto de donde se tomarón valores para crear el DTO de salida
	 * @return DTO creado a partir del parámetro de entrada
	 */
	public static CuentaDTO crearCuentaControladaDTO(CuentaControlada cuenta) {
		CuentaDTO dto = null;
		if (cuenta != null) {
			dto = new CuentaDTO();
			dto.setIdCuenta(cuenta.getIdCuentaControlada().longValue());
			dto.setNumeroCuenta(
					cuenta.getInstitucion().getTipoInstitucion().getClaveTipoInstitucion() +
					cuenta.getInstitucion().getFolioInstitucion() +
					cuenta.getCuenta());
			if(cuenta.getTipoCuenta() != null) {
				dto.setTipoTenencia(new TipoTenenciaDTO());
				dto.getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO(cuenta.getTipoCuenta().getNaturalezaContable(), null));
			}
			if(cuenta.getInstitucion() != null) {
				dto.setInstitucion(new InstitucionDTO(cuenta.getInstitucion().getIdInstitucion().longValue()));
				dto.getInstitucion().setClaveTipoInstitucion(cuenta.getInstitucion().getTipoInstitucion().getClaveTipoInstitucion());
				dto.getInstitucion().setFolioInstitucion(cuenta.getInstitucion().getFolioInstitucion());
			}
			dto.setCuenta(cuenta.getCuenta());
			dto.setDescripcion(dto.getNumeroCuenta());
			dto.setNombreCuenta(cuenta.getNombreCuenta());
		}
		return dto;
	}

	/**
	 * Crea un objeto del tipo {@link CuentaDTO} a partir de un objeto de
	 * hibernate del tipo {@link CuentaNombrada}
	 * 
	 * @param cuenta
	 *            Objeto de donde se tomarón valores para crear el DTO de salida
	 * @return DTO creado a partir del parámetro de entrada
	 */
	public static CuentaDTO crearCuentaNombradaDTO(CuentaNombrada cuentaNombrada) {
		CuentaDTO dto = null;
		if (cuentaNombrada != null) {
			dto = new CuentaDTO();
			dto.setIdCuenta(cuentaNombrada.getIdCuentaNombrada().longValue());
			dto.setCuenta(cuentaNombrada.getCuenta());
			dto.setNombreCuenta(cuentaNombrada.getNombreCuenta());
			if(cuentaNombrada.getInstitucion() != null) {
				InstitucionDTO instDTO = new InstitucionDTO();
				instDTO.setId(cuentaNombrada.getInstitucion().getIdInstitucion().longValue());
				instDTO.setFolioInstitucion(cuentaNombrada.getInstitucion().getFolioInstitucion());
				if (cuentaNombrada.getInstitucion().getTipoInstitucion() != null){
					instDTO.setClaveTipoInstitucion(cuentaNombrada.getInstitucion().getTipoInstitucion().getClaveTipoInstitucion());
				}
				else{
					instDTO.setClaveTipoInstitucion("");				
				}
				instDTO.setNombreCorto(cuentaNombrada.getInstitucion().getNombreCorto());
				dto.setInstitucion(instDTO);
			}
			dto.setTipoTenencia(crearTipoTeneciaDTO(cuentaNombrada.getTipoCuenta()));
		}
		return dto;
	}
	
	/**
	 * Crea un objeto del tipo {@link TipoTenenciaDTO} a partir de un objeto de hibernate del tipo {@link TipoCuenta}
	 * 
	 * @param tipoCuenta Objeto de donde se tomarón valores para crear el DTO de salida
	 * @return DTO creado a partir del parámetro de entrada
	 */
	public static TipoTenenciaDTO crearTipoTeneciaDTO(TipoCuenta tipoCuenta) {
		TipoTenenciaDTO dto = null;
		if (tipoCuenta != null) {
			dto = new TipoTenenciaDTO();
			TipoCuentaDTO tipoCuentaDTO = new TipoCuentaDTO();
			TipoNaturalezaDTO tipoNaturalezaDTO = new TipoNaturalezaDTO();
//			dto.setClaveTipoCuenta(tipoCuenta.getClaveTipoCuenta());
//			dto.setDescripcion(tipoCuenta.getDescripcion());
//			dto.setIdTipoCuenta(tipoCuenta.getIdTipoCuenta().longValue());
//			dto.setTipoCustodia(tipoCuenta.getTipoCustodia());
			
			tipoCuentaDTO.setId(tipoCuenta.getNaturalezaProcesoLiquidacion());
//			tipoCuentaDTO.setDescripcion(tipoCuenta.getNaturalezaProcesoLiquidacion().equals("C") ? "CONTROLADA" : "NOMBRADA");
			
			tipoNaturalezaDTO.setId(tipoCuenta.getNaturalezaContable());
//			tipoNaturalezaDTO.setDescripcion(tipoCuenta.getNaturalezaContable().equals("P") ? "PASIVO" : "ACTIVO");

			dto.setTipoCuenta(tipoCuentaDTO);
			dto.setTipoNaturaleza(tipoNaturalezaDTO);
		}
		return dto;
	}
	
	
	/**
	 * Crea un objeto del tipo {@link EmisionDTO} a partir de un objeto de hibernate del tipo {@link EmisionPersistence}
	 * 
	 * @param posicion Objeto de hibernate fuete para construir el DTO
	 * @return DTO creao a partir del parámetro de entrada
	 */
	public static EmisionDTO crearEmisionDTO(Emision emision) {
		EmisionDTO dto = null;
		if (emision != null) {
			dto = new EmisionDTO();
			dto.setId(emision.getIdEmision().longValue());
			dto.setIsin(emision.getIsin());
			if(emision.getEmisora() != null) {
				dto.setEmisora(new EmisoraDTO());
				dto.getEmisora().setDescripcion(emision.getEmisora().getDescripcion());
			}
			if(emision.getSerie() != null) {
				dto.setSerie(new SerieDTO());
				dto.getSerie().setSerie(emision.getSerie());
				dto.getSerie().setDescripcion(emision.getSerie());
			}			
			if(emision.getInstrumento() != null) {
				dto.setTipoValor(new TipoValorDTO(
					emision.getInstrumento().getIdInstrumento().longValue(), 
					emision.getInstrumento().getClaveTipoValor()));
				dto.getTipoValor().setDescripcion(emision.getInstrumento().getDescripcion());
			}
			dto.setValorNominal(emision.getValorNominal().doubleValue());
		}
		return dto;
	}
	
	
	/**
	 * Crea un objeto de tipo {@link TipoInstruccionDTO} a partir de un objeto de hibernate del tipo {@link TipoInstruccion}
	 * 
	 * @param tipoInstruccion Objeto de hibernate fuente para construir el DTO
	 * @return DTO creao a partir del parámetro de entrada
	 */
	public static TipoInstruccionDTO crearTipoInstruccion(TipoInstruccion tipoInstruccion) {
		TipoInstruccionDTO dto = null;
		if (tipoInstruccion != null) {
			dto = new TipoInstruccionDTO();
			dto.setIdTipoInstruccion(tipoInstruccion.getIdTipoInstruccion().longValue());
			dto.setDescripcion(tipoInstruccion.getDescripcion() != null ? tipoInstruccion.getDescripcion().toUpperCase() : StringUtils.EMPTY);
			dto.setNombreCorto(tipoInstruccion.getNombreCorto() != null ? tipoInstruccion.getNombreCorto().toUpperCase() : StringUtils.EMPTY);
			dto.setInstruccion(tipoInstruccion.getInstruccion() != null ? tipoInstruccion.getInstruccion().toUpperCase() : StringUtils.EMPTY);
		}
		return dto;
	}
}

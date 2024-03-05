/**
 * Bursatec - Portal DALI
 * Copyrigth (c) 2014 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.dto.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.CuentaEfectivoDTO;
import com.indeval.portaldali.middleware.dto.CuponDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.EmisionDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.InstruccionEfectivoDTO;
import com.indeval.portaldali.middleware.dto.InstruccionLiquidacionDTO;
import com.indeval.portaldali.middleware.dto.MercadoDTO;
import com.indeval.portaldali.middleware.dto.OperacionPosicionDTO;
import com.indeval.portaldali.middleware.dto.PosicionDTO;
import com.indeval.portaldali.middleware.dto.RegistroContableSaldoControladaDTO;
import com.indeval.portaldali.middleware.dto.RegistroContableSaldoNombradaDTO;
import com.indeval.portaldali.middleware.dto.SaldoEfectivoDTO;
import com.indeval.portaldali.middleware.dto.SerieDTO;
import com.indeval.portaldali.middleware.dto.TipoAccionDTO;
import com.indeval.portaldali.middleware.dto.TipoCuentaDTO;
import com.indeval.portaldali.middleware.dto.TipoInstitucionDTO;
import com.indeval.portaldali.middleware.dto.TipoInstruccionDTO;
import com.indeval.portaldali.middleware.dto.TipoNaturalezaDTO;
import com.indeval.portaldali.middleware.dto.TipoOperacionDTO;
import com.indeval.portaldali.middleware.dto.TipoValorDTO;
import com.indeval.portaldali.persistence.model.CuentaControlada;
import com.indeval.portaldali.persistence.model.CuentaNombrada;
import com.indeval.portaldali.persistence.model.Cupon;
import com.indeval.portaldali.persistence.model.Emision;
import com.indeval.portaldali.persistence.model.Institucion;
import com.indeval.portaldali.persistence.model.InstruccionLiquidacion;
import com.indeval.portaldali.persistence.model.InstruccionLiquidacionHistorico;
import com.indeval.portaldali.persistence.model.OperacionNombrada;
import com.indeval.portaldali.persistence.model.OperacionNombradaHistorico;
import com.indeval.portaldali.persistence.model.PosicionNombrada;
import com.indeval.portaldali.persistence.model.PosicionNombradaHistorico;
import com.indeval.portaldali.persistence.model.RegContEfecControlada;
import com.indeval.portaldali.persistence.model.RegContEfecControladaHistorico;
import com.indeval.portaldali.persistence.model.RegContEfecNombrada;
import com.indeval.portaldali.persistence.model.RegContEfecNombradaHistorico;
import com.indeval.portaldali.persistence.model.SaldoControlada;
import com.indeval.portaldali.persistence.model.SaldoControladaHistorico;
import com.indeval.portaldali.persistence.model.SaldoNombrada;
import com.indeval.portaldali.persistence.model.SaldoNombradaHistorico;
import com.indeval.portaldali.persistence.model.TipoInstruccion;
import com.indeval.portaldali.persistence.model.TipoOperacion;
import com.indeval.portaldali.persistence.model.TipoRetiro;
import com.indeval.portaldali.persistence.util.TipoPagoConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ensamblador de DTOs para las consultas de efectivo.
 *
 * @author Pablo Balderas
 */
public class EnsambladorDTOConsultasEfectivo {

    private static Logger logger = LoggerFactory.getLogger(EnsambladorDTOConsultasEfectivo.class);

    /**
     * Crea un objeto de tipo {@link RegistroContableSaldoControladaDTO} a partir de un objeto {@link RegContEfecControlada}
     *
     * @param registroContable Pojo base
     * @return Dto creado
     */
    public static RegistroContableSaldoControladaDTO crearRegContableSaldoControladaDTO(RegContEfecControlada registroContable) {
        logger.debug("EnsambladorDTOConsultasEfectivo :: crearRegContableSaldoControladaDTO");
        RegistroContableSaldoControladaDTO dto = null;
        if (registroContable != null) {
            dto = new RegistroContableSaldoControladaDTO();
            dto.setIdRegistroContable(registroContable.getIdRegistroContable().longValue());
            dto.setFechaLiquidacion(registroContable.getFecha());
            dto.setSaldoEfectivo(crearSaldoEfectivoControladaDTO(registroContable.getSaldo()));
            if (registroContable.getTipoAccion() != null) {
                dto.setTipoAccion(new TipoAccionDTO(registroContable.getTipoAccion().getValue()));
            }
            dto.setCantidad(registroContable.getImporte());
        }
        logger.debug(dto.toString());
        return dto;
    }

    /**
     * Crea un objeto de tipo {@link RegistroContableSaldoControladaDTO} a partir de un objeto {@link RegContEfecControladaHistorico}
     *
     * @param registroContable Pojo base
     * @return Dto creado
     */
    public static RegistroContableSaldoControladaDTO crearRegContableSaldoControladaHistoricoDTO(RegContEfecControladaHistorico registroContable) {
        RegistroContableSaldoControladaDTO dto = null;
        if (registroContable != null) {
            dto = new RegistroContableSaldoControladaDTO();
            dto.setHistorico(true);
            dto.setIdRegistroContable(registroContable.getIdRegistroContable().longValue());
            dto.setFechaLiquidacion(registroContable.getFecha());
            dto.setSaldoEfectivo(crearSaldoEfectivoControladaDTO(registroContable.getSaldo()));
            if (registroContable.getTipoAccion() != null) {
                dto.setTipoAccion(new TipoAccionDTO(registroContable.getTipoAccion().getValue()));
            }
            dto.setCantidad(registroContable.getImporte());
        }
        return dto;
    }


    public static RegistroContableSaldoNombradaDTO crearRegistroContableSaldoNombradaConDatosOperacionEInstruccion(
            RegContEfecNombrada registro, String instruccion,
            TipoRetiro tipoRetiro, Integer idTipoPago, BigInteger folioInstLiquidacion) {
        RegistroContableSaldoNombradaDTO dtoRegistro = null;

        if (registro != null) {

            dtoRegistro = new RegistroContableSaldoNombradaDTO();

            dtoRegistro.setIdRegistroContable(registro.getIdRegistroContable().longValue());
            dtoRegistro.setSaldoEfectivo(crearSaldoEfectivoNombradaDTO(registro.getSaldoNombrada()));

//			dtoRegistro.setTipoAccion(crearTipoAccion(registro.getTipoAccion()));
            if (registro.getTipoAccion() != null) {
                dtoRegistro.setTipoAccion(new TipoAccionDTO(registro.getTipoAccion().getValue()));
            }

            dtoRegistro.setCantidad(registro.getImporte());
            dtoRegistro.setFechaLiquidacion(registro.getFecha());
            dtoRegistro.setCargoA(registro.getCargoA() != null ? registro.getCargoA().longValue() : null);

            dtoRegistro.setOperacion(crearOperacionEfectivo(registro.getOperacion()));

            dtoRegistro.setIdTipoTipoInstruccion(registro.getOperacion().getInstruccion().getIdTipoInstruccion().longValue());
            dtoRegistro.setFolioInstruccion(registro.getOperacion().getInstruccion().getFolioInstruccion().longValue());

            if (dtoRegistro.getOperacion() != null && dtoRegistro.getOperacion().getInstruccionLiquidacion() != null) {
                if (StringUtils.isNotBlank(instruccion) && instruccion.indexOf(",") != -1 && instruccion.length() > 1) {
                    String[] datos = instruccion.split(",");
                    Long plazo = null;
                    Double tasa = null;

                    try {
                        plazo = Long.valueOf(datos[0]);
                    } catch (Exception e) {
                    }
                    try {
                        tasa = new Double(datos[1]);
                    } catch (Exception e) {
                    }

                    dtoRegistro.getOperacion().getInstruccionLiquidacion().setPlazoReporto(plazo != null ? plazo : 0L);
                    dtoRegistro.getOperacion().getInstruccionLiquidacion().setTasaNegociada(tasa != null ? tasa : 0.0);
                }
            }

            if (tipoRetiro != null && StringUtils.isNotBlank(tipoRetiro.getDescripcion()) && dtoRegistro.getOperacion() != null && dtoRegistro.getOperacion().getInstruccionLiquidacion() != null) {
                dtoRegistro.getOperacion().getInstruccionLiquidacion().setInstruccionEfectivo(new InstruccionEfectivoDTO());
                dtoRegistro.getOperacion().getInstruccionLiquidacion().getInstruccionEfectivo().setTipoRetiro(tipoRetiro.getDescripcion());
                dtoRegistro.getOperacion().getInstruccionLiquidacion().getInstruccionEfectivo().setDescripcionTipoRetiro(tipoRetiro.getDescripcion());
            }

            if (idTipoPago != null && dtoRegistro.getOperacion() != null && dtoRegistro.getOperacion().getInstruccionLiquidacion() != null) {
                dtoRegistro.getOperacion().getInstruccionLiquidacion().setInstruccionEfectivo(new InstruccionEfectivoDTO());
                String retiro = null;
                switch (idTipoPago) {
                    case TipoPagoConstants.TERCERO_A_TERCERO_SPEI:
                    case TipoPagoConstants.PARTICIPANTE_A_TERCERO_SPEI:
                    case TipoPagoConstants.PARTICIPANTE_A_PARTICIPANTE_SPEI:
                        retiro = TipoPagoConstants.DESCRIPCION_SPEI;
                        break;
                    case TipoPagoConstants.TRASPASO_FONDOS_SIAC:
                        retiro = TipoPagoConstants.DESCRIPCION_SIAC;
                        break;
                    case TipoPagoConstants.DEPOSITO_DEVOLUCION:
                        retiro = TipoPagoConstants.DESCRIPCION_DEVO;
                        break;
                    case TipoPagoConstants.DEPOSITO_OTRAS_DIVISAS:
                        retiro = TipoPagoConstants.DESCRIPCION_DIVI;
                        break;
                }
                dtoRegistro.getOperacion().getInstruccionLiquidacion().getInstruccionEfectivo().setTipoRetiro(retiro);
                dtoRegistro.getOperacion().getInstruccionLiquidacion().getInstruccionEfectivo().setDescripcionTipoRetiro(retiro);
            }
            if(folioInstLiquidacion!=null){
                dtoRegistro.setIdFolioLiquidacion(folioInstLiquidacion);
            }
        }
        return dtoRegistro;
    }

    public static RegistroContableSaldoNombradaDTO crearRegistroContableSaldoNombradaConDatosOperacionEInstruccionHistorico(
            RegContEfecNombradaHistorico registro, String instruccion,
            TipoRetiro tipoRetiro, Integer idTipoPago, Date fechaActual) {
        RegistroContableSaldoNombradaDTO dtoRegistro = null;
        if (registro != null) {
            dtoRegistro = new RegistroContableSaldoNombradaDTO();
            dtoRegistro.setHistorico(true);
            dtoRegistro.setIdRegistroContable(registro.getIdRegistroContable().longValue());
            dtoRegistro.setSaldoEfectivo(crearSaldoEfectivoNombradaHistoricaDTO(registro.getSaldoNombradaHistorico()));
//			dtoRegistro.setTipoAccion(crearTipoAccion(registro.getTipoAccion()));
            if (registro.getTipoAccion() != null) {
                dtoRegistro.setTipoAccion(new TipoAccionDTO(registro.getTipoAccion().getValue()));
            }

            dtoRegistro.setCantidad(registro.getImporte());
            dtoRegistro.setFechaLiquidacion(registro.getFecha());
            dtoRegistro.setCargoA(registro.getCargoA() != null ? registro.getCargoA().longValue() : null);
            dtoRegistro.setOperacion(crearOperacionEfectivoHistorico(registro.getOperacion(), null));
            dtoRegistro.setIdTipoTipoInstruccion(registro.getOperacion().getInstruccion().getIdTipoInstruccion().longValue());
            dtoRegistro.setFolioInstruccion(registro.getOperacion().getInstruccion().getFolioInstruccion().longValue());

            if (dtoRegistro.getOperacion() != null && dtoRegistro.getOperacion().getInstruccionLiquidacion() != null) {
                if (StringUtils.isNotBlank(instruccion)
                        && instruccion.indexOf(",") != -1
                        && instruccion.length() > 1) {
                    String[] datos = instruccion.split(",");
                    Long plazo = null;
                    Double tasa = null;

                    try {
                        plazo = Long.valueOf(datos[0]);
                    } catch (Exception e) {
                    }
                    try {
                        tasa = new Double(datos[1]);
                    } catch (Exception e) {
                    }
                    dtoRegistro.getOperacion().getInstruccionLiquidacion().setPlazoReporto(plazo != null ? plazo : 0L);
                    dtoRegistro.getOperacion().getInstruccionLiquidacion().setTasaNegociada(tasa != null ? tasa : 0.0);
                }
            }
            if (tipoRetiro != null && StringUtils.isNotBlank(tipoRetiro.getDescripcion())
                    && dtoRegistro.getOperacion() != null
                    && dtoRegistro.getOperacion().getInstruccionLiquidacion() != null) {
                dtoRegistro.getOperacion().getInstruccionLiquidacion().setInstruccionEfectivo(new InstruccionEfectivoDTO());
                dtoRegistro.getOperacion().getInstruccionLiquidacion().getInstruccionEfectivo().setTipoRetiro(tipoRetiro.getDescripcion());
                dtoRegistro.getOperacion().getInstruccionLiquidacion().getInstruccionEfectivo().setDescripcionTipoRetiro(tipoRetiro.getDescripcion());
            }

            if (idTipoPago != null
                    && dtoRegistro.getOperacion() != null
                    && dtoRegistro.getOperacion().getInstruccionLiquidacion() != null) {
                dtoRegistro.getOperacion().getInstruccionLiquidacion().setInstruccionEfectivo(new InstruccionEfectivoDTO());
                String retiro = null;
                switch (idTipoPago) {
                    case TipoPagoConstants.TERCERO_A_TERCERO_SPEI:
                    case TipoPagoConstants.PARTICIPANTE_A_TERCERO_SPEI:
                    case TipoPagoConstants.PARTICIPANTE_A_PARTICIPANTE_SPEI:
                        retiro = TipoPagoConstants.DESCRIPCION_SPEI;
                        break;
                    case TipoPagoConstants.TRASPASO_FONDOS_SIAC:
                        retiro = TipoPagoConstants.DESCRIPCION_SIAC;
                        break;
                    case TipoPagoConstants.DEPOSITO_DEVOLUCION:
                        retiro = TipoPagoConstants.DESCRIPCION_DEVO;
                        break;
                    case TipoPagoConstants.DEPOSITO_OTRAS_DIVISAS:
                        retiro = TipoPagoConstants.DESCRIPCION_DIVI;
                        break;
                }
                dtoRegistro.getOperacion().getInstruccionLiquidacion().getInstruccionEfectivo().setTipoRetiro(retiro);
                dtoRegistro.getOperacion().getInstruccionLiquidacion().getInstruccionEfectivo().setDescripcionTipoRetiro(retiro);
            }
            //TODO por paquetes y cuando no existe mov en efectivo se valida
            if (registro.getOperacion().getInstruccion().getInstruccionEfectivo() != null) {
                dtoRegistro.getOperacion().setReferenciaPaquete(registro.getOperacion().getInstruccion().getInstruccionEfectivo().getReferenciaPaquete());
                dtoRegistro.getOperacion().setTotalOperacionesPaquete(registro.getOperacion().getInstruccion().getInstruccionEfectivo().getTotalOperacionesPaquete());
                dtoRegistro.getOperacion().setNumeroOperacionPaquete(registro.getOperacion().getInstruccion().getInstruccionEfectivo().getNumeroOperacionPaquete());
                dtoRegistro.getOperacion().setTotalTitulosPaquete(registro.getOperacion().getInstruccion().getInstruccionEfectivo().getTotalTitulosPaquete());
                dtoRegistro.getOperacion().setTotalImportePaquete(registro.getOperacion().getInstruccion().getInstruccionEfectivo().getTotalImportePaquete());
            }
            if (dtoRegistro.getOperacion().getReferenciaPaquete() != null && dtoRegistro.getOperacion().getReferenciaPaquete().length() > 0) {
                dtoRegistro.getOperacion().setPorPaquete(true);
            } else {
                dtoRegistro.getOperacion().setPorPaquete(false);
            }
        }
        return dtoRegistro;
    }


    public static RegistroContableSaldoNombradaDTO crearRegistroContableSaldoNombradaConDatosOperacion(RegContEfecNombrada registro) {
        RegistroContableSaldoNombradaDTO dtoRegistro = null;
        if (registro != null) {
            dtoRegistro = new RegistroContableSaldoNombradaDTO();
            dtoRegistro.setIdRegistroContable(registro.getIdRegistroContable().longValue());
            dtoRegistro.setSaldoEfectivo(crearSaldoEfectivoNombradaDTO(registro.getSaldoNombrada()));
//			dtoRegistro.setTipoAccion(crearTipoAccion(registro.getTipoAccion()));
            if (registro.getTipoAccion() != null) {
                dtoRegistro.setTipoAccion(new TipoAccionDTO(registro.getTipoAccion().getValue()));
            }

            dtoRegistro.setCantidad(registro.getImporte());
            dtoRegistro.setFechaLiquidacion(registro.getFecha());
            dtoRegistro.setCargoA(registro.getCargoA() != null ? registro.getCargoA().longValue() : null);
            dtoRegistro.setOperacion(crearOperacionEfectivo(registro.getOperacion()));
            dtoRegistro.setIdTipoTipoInstruccion(registro.getOperacion().getInstruccion().getIdTipoInstruccion().longValue());
            dtoRegistro.setFolioInstruccion(registro.getOperacion().getInstruccion().getFolioInstruccion().longValue());
        }
        return dtoRegistro;
    }

    public static RegistroContableSaldoNombradaDTO crearRegistroContableSaldoNombradaConDatosOperacion(RegContEfecNombradaHistorico registro) {
        RegistroContableSaldoNombradaDTO dtoRegistro = null;
        if (registro != null) {
            dtoRegistro = new RegistroContableSaldoNombradaDTO();
            dtoRegistro.setHistorico(true);
            dtoRegistro.setIdRegistroContable(registro.getIdRegistroContable().longValue());
            dtoRegistro.setSaldoEfectivo(crearSaldoEfectivoNombradaHistoricaDTO(registro.getSaldoNombradaHistorico()));
//			dtoRegistro.setTipoAccion(crearTipoAccion(registro.getTipoAccion()));
            if (registro.getTipoAccion() != null) {
                dtoRegistro.setTipoAccion(new TipoAccionDTO(registro.getTipoAccion().getValue()));
            }

            dtoRegistro.setCantidad(registro.getImporte());
            dtoRegistro.setFechaLiquidacion(registro.getFecha());
            dtoRegistro.setCargoA(registro.getCargoA() != null ? registro.getCargoA().longValue() : null);
            dtoRegistro.setOperacion(crearOperacionEfectivoHistorico(registro.getOperacion()));
            dtoRegistro.setIdTipoTipoInstruccion(registro.getOperacion().getInstruccion().getIdTipoInstruccion().longValue());
            dtoRegistro.setFolioInstruccion(registro.getOperacion().getInstruccion().getFolioInstruccion().longValue());
        }
        return dtoRegistro;
    }

    /**
     * Crea un objeto {@link SaldoEfectivoDTO} a partir de un objeto {@link SaldoControlada}
     *
     * @param saldoControlada Pojo base
     * @return Dto creado
     */
    public static SaldoEfectivoDTO crearSaldoEfectivoControladaDTO(SaldoControlada saldoControlada) {
        SaldoEfectivoDTO saldoEfectivoDTO = new SaldoEfectivoDTO();
        saldoEfectivoDTO.setIdSaldo(saldoControlada.getIdSaldo().longValue());
        if (saldoControlada.getDivisa() != null) {
            saldoEfectivoDTO.setDivisa(new DivisaDTO(saldoControlada.getDivisa().getIdDivisa().longValue()));
            saldoEfectivoDTO.getDivisa().setDescripcion(saldoControlada.getDivisa().getDescripcion());
        }
        if (saldoControlada.getBoveda() != null) {
            saldoEfectivoDTO.setBoveda(new BovedaDTO(saldoControlada.getBoveda().getIdBoveda().longValue()));
            saldoEfectivoDTO.getBoveda().setDescripcion(saldoControlada.getBoveda().getDescripcion());
        }
        saldoEfectivoDTO.setCuenta(crearCuentaEfectivoDTO(saldoControlada.getCuentaControlada()));
        saldoEfectivoDTO.setSaldo(saldoControlada.getSaldo().doubleValue());
        return saldoEfectivoDTO;
    }

    /**
     * Crea un objeto {@link SaldoEfectivoDTO} a partir de objeto {@link SaldoControladaHistorico}
     *
     * @param saldoControlada Pojo base
     * @return Dto creado
     */
    public static SaldoEfectivoDTO crearSaldoEfectivoControladaHistoricaDTO(SaldoControladaHistorico saldoControlada) {
        SaldoEfectivoDTO saldoEfectivoDTO = new SaldoEfectivoDTO();
        saldoEfectivoDTO.setIdSaldo(saldoControlada.getIdSaldoHistorico().longValue());
        if (saldoControlada.getDivisa() != null) {
            saldoEfectivoDTO.setDivisa(new DivisaDTO(saldoControlada.getDivisa().getIdDivisa().longValue()));
            saldoEfectivoDTO.getDivisa().setDescripcion(saldoControlada.getDivisa().getDescripcion());
        }
        if (saldoControlada.getBoveda() != null) {
            saldoEfectivoDTO.setBoveda(new BovedaDTO(saldoControlada.getBoveda().getIdBoveda().longValue()));
            saldoEfectivoDTO.getBoveda().setDescripcion(saldoControlada.getBoveda().getDescripcion());
        }
        saldoEfectivoDTO.setCuenta(crearCuentaEfectivoDTO(saldoControlada.getCuentaControlada()));
        saldoEfectivoDTO.setSaldo(saldoControlada.getSaldo().doubleValue());
        saldoEfectivoDTO.setFecha(saldoControlada.getFechaCreacion());
        return saldoEfectivoDTO;
    }

    /**
     * Crea un objeto {@link OperacionPosicionDTO} a partir de un objeto {@link OperacionNombrada}
     *
     * @param operacion Pojo base
     * @return Dto creado
     */
    public static OperacionPosicionDTO crearOperacionEfectivo(OperacionNombrada operacion) {
        OperacionPosicionDTO operacionPosicionDTO = null;
        if (operacion != null) {
            operacionPosicionDTO = new OperacionPosicionDTO();
            operacionPosicionDTO.setCargoEfectivoA(operacion.getCargoEfectivoA() != null ? operacion.getCargoEfectivoA().longValue() : 0);
            operacionPosicionDTO.setCuentaEfectivoTraspasante(crearCuentaDTO(operacion.getCuentaEfectivoTraspasante()));
            operacionPosicionDTO.setCuentaEfectivoReceptor(crearCuentaDTO(operacion.getCuentaEfectivoReceptor()));
            operacionPosicionDTO.setCuentaReceptora(crearCuentaDTO(operacion.getCuentaNombradaReceptor()));
            operacionPosicionDTO.setCuentaTraspasante(crearCuentaDTO(operacion.getCuentaNombradaTraspasante()));
            operacionPosicionDTO.setFolio(operacion.getFolioOperacion().longValue());
            operacionPosicionDTO.setIdOperacion(operacion.getIdOperacion().longValue());
            operacionPosicionDTO.setInstruccionLiquidacion(crearInstruccionLiquidacion(operacion.getInstruccion()));
            operacionPosicionDTO.setTipoOperacion(crearTipoOperacion(operacion.getTipoOperacion()));
            operacionPosicionDTO.setMonto(operacion.getMonto() != null ? operacion.getMonto().doubleValue() : 0);
            operacionPosicionDTO.setNumeroTitulos(operacion.getNumeroTitulos() != null ? operacion.getNumeroTitulos().longValue() : 0);
            operacionPosicionDTO.setPosicion(crearPosicionNombradaDTO(operacion.getPosicionNombrada()));
            operacionPosicionDTO.setPrecio(operacion.getPrecio() != null ? operacion.getPrecio().doubleValue() : 0);
        }
        return operacionPosicionDTO;
    }

    /**
     * Crea un objeto {@link OperacionPosicionDTO} a partir de un objeto {@link OperacionNombradaHistorico}
     *
     * @param operacion  Pojo base
     * @param idTipoPago Dto creado
     * @return
     */
    public static OperacionPosicionDTO crearOperacionEfectivoHistorico(OperacionNombradaHistorico operacion, Integer idTipoPago) {
        OperacionPosicionDTO operacionPosicionDTO = null;
        if (operacion != null) {
            operacionPosicionDTO = new OperacionPosicionDTO();
            operacionPosicionDTO.setCargoEfectivoA(operacion.getCargoEfectivoA() != null ? operacion.getCargoEfectivoA().longValue() : 0);
            operacionPosicionDTO.setCuentaEfectivoTraspasante(crearCuentaDTO(operacion.getCuentaEfectivoTraspasante()));
            operacionPosicionDTO.setCuentaEfectivoReceptor(crearCuentaDTO(operacion.getCuentaEfectivoReceptor()));
            operacionPosicionDTO.setCuentaReceptora(crearCuentaDTO(operacion.getCuentaNombradaReceptor()));
            operacionPosicionDTO.setCuentaTraspasante(crearCuentaDTO(operacion.getCuentaNombradaTraspasante()));
            operacionPosicionDTO.setFolio(operacion.getFolioOperacion().longValue());
            operacionPosicionDTO.setIdOperacion(operacion.getIdOperacion().longValue());
            operacionPosicionDTO.setInstruccionLiquidacion(crearInstruccionLiquidacionHistorico(operacion.getInstruccion(), idTipoPago));
            operacionPosicionDTO.setTipoOperacion(crearTipoOperacion(operacion.getTipoOperacion()));
            operacionPosicionDTO.setMonto(operacion.getMonto() != null ? operacion.getMonto().doubleValue() : 0);
            operacionPosicionDTO.setNumeroTitulos(operacion.getNumeroTitulos() != null ? operacion.getNumeroTitulos().longValue() : 0);
            operacionPosicionDTO.setPosicion(crearPosicionNombradaHistoricoDTO(operacion.getPosicionNombrada()));
            operacionPosicionDTO.setPrecio(operacion.getPrecio() != null ? operacion.getPrecio().doubleValue() : 0);
        }
        return operacionPosicionDTO;
    }

    public static OperacionPosicionDTO crearOperacionEfectivoHistorico(OperacionNombradaHistorico operacion) {

        OperacionPosicionDTO operacionPosicionDTO = null;
        if (operacion != null) {
            operacionPosicionDTO = new OperacionPosicionDTO();
            operacionPosicionDTO.setCargoEfectivoA(operacion.getCargoEfectivoA() != null ? operacion.getCargoEfectivoA().longValue() : 0);
            operacionPosicionDTO.setCuentaReceptora(crearCuentaDTO(operacion.getCuentaNombradaReceptor()));
            operacionPosicionDTO.setCuentaTraspasante(crearCuentaDTO(operacion.getCuentaNombradaTraspasante()));
            operacionPosicionDTO.setCuentaEfectivoTraspasante(crearCuentaDTO(operacion.getCuentaEfectivoTraspasante()));
            operacionPosicionDTO.setCuentaEfectivoReceptor(crearCuentaDTO(operacion.getCuentaEfectivoReceptor()));
//			operacionPosicionDTO.setDivisa(crearDivisaDTO(operacion.getDivisa()));
            operacionPosicionDTO.setFolio(operacion.getFolioOperacion().longValue());
            operacionPosicionDTO.setIdOperacion(operacion.getIdOperacion().longValue());
            operacionPosicionDTO.setInstruccionLiquidacion(crearInstruccionLiquidacionHistorico(operacion.getInstruccion()));
            operacionPosicionDTO.setTipoOperacion(crearTipoOperacion(operacion.getTipoOperacion()));
            operacionPosicionDTO.setMonto(operacion.getMonto() != null ? operacion.getMonto().doubleValue() : 0);
            // operacionPosicionDTO.setCargoValoresA(operacion.getCargoValoresA()
            // != null ? operacion.getCargoValoresA().longValue() : 0);
            operacionPosicionDTO.setCupon(crearCuponDTO(operacion.getCupon()));
            operacionPosicionDTO.setNumeroTitulos(operacion.getNumeroTitulos() != null ? operacion.getNumeroTitulos().longValue() : 0);
            operacionPosicionDTO.setPosicion(crearPosicionNombradaHistoricoDTO(operacion.getPosicionNombrada()));
            operacionPosicionDTO.setPrecio(operacion.getPrecio() != null ? operacion.getPrecio().doubleValue() : 0);
        }
        return operacionPosicionDTO;
    }

    public static CuentaDTO crearCuentaDTO(CuentaNombrada cuenta) {
        CuentaDTO dto = null;
        if (cuenta != null) {
            dto = new CuentaDTO();
//			dto.setTipoTenencia(crearTipoTeneciaDTO(cuenta.getTipoCuenta()));
            dto.setIdCuenta(cuenta.getIdCuentaNombrada().longValue());
            dto.setInstitucion(crearInstitucionDTO(cuenta.getInstitucion()));
            dto.setNumeroCuenta(
                    cuenta.getInstitucion().getTipoInstitucion().getClaveTipoInstitucion() +
                            cuenta.getInstitucion().getFolioInstitucion() +
                            cuenta.getCuenta());
//			dto.setDescripcion(dto.getNumeroCuenta());
            dto.setNombreCuenta(cuenta.getNombreCuenta());
            dto.setCuenta(cuenta.getCuenta());
        }
        return dto;
    }

    public static TipoOperacionDTO crearTipoOperacion(TipoOperacion tipoOperacion) {
        TipoOperacionDTO tipoOperacionDTO = null;
        if (tipoOperacion != null) {
            tipoOperacionDTO = new TipoOperacionDTO();
            tipoOperacionDTO.setDescripcion(tipoOperacion.getDescripcion());
//			tipoOperacionDTO.setId(tipoOperacion.getIdTipoOperacion().longValue());
//			tipoOperacionDTO.setClaveTipoOperacion(tipoOperacion.getClaveTipoOperacion());
        }
        return tipoOperacionDTO;
    }

    public static InstruccionLiquidacionDTO crearInstruccionLiquidacion(InstruccionLiquidacion instruccion) {
        InstruccionLiquidacionDTO instruccionLiquidacionDTO = null;
        if (instruccion != null) {
            instruccionLiquidacionDTO = new InstruccionLiquidacionDTO();
            instruccionLiquidacionDTO.setFolioInstruccion(instruccion.getFolioInstruccion().longValue());
            instruccionLiquidacionDTO.setTipoInstruccion(crearTipoInstruccion(instruccion.getTipoInstruccion()));
            instruccionLiquidacionDTO.setFechaLiquidacion(instruccion.getFechaLiquidacion());
        }
        return instruccionLiquidacionDTO;
    }

    public static InstruccionLiquidacionDTO crearInstruccionLiquidacionHistorico(InstruccionLiquidacionHistorico instruccion, Integer idTipoPago) {
        InstruccionLiquidacionDTO instruccionLiquidacionDTO = null;
        if (instruccion != null) {
            instruccionLiquidacionDTO = new InstruccionLiquidacionDTO();
            instruccionLiquidacionDTO.setFolioInstruccion(instruccion.getFolioInstruccion().longValue());
            instruccionLiquidacionDTO.setTipoInstruccion(crearTipoInstruccion(instruccion.getTipoInstruccion()));
            instruccionLiquidacionDTO.setFechaLiquidacion(instruccion.getFechaLiquidacion());
            instruccionLiquidacionDTO.setInstruccionEfectivo(DTOAssembler.crearInstruccionEfectivoV2DTO(idTipoPago));
        }
        return instruccionLiquidacionDTO;
    }

    public static InstruccionLiquidacionDTO crearInstruccionLiquidacionHistorico(InstruccionLiquidacionHistorico instruccion) {
        InstruccionLiquidacionDTO instruccionLiquidacionDTO = null;
        if (instruccion != null) {
            instruccionLiquidacionDTO = new InstruccionLiquidacionDTO();
            instruccionLiquidacionDTO.setFolioInstruccion(instruccion.getFolioInstruccion().longValue());
            instruccionLiquidacionDTO.setIdInstruccionLiquidacion(instruccion.getIdInstruccionLiquidacion().longValue());
            instruccionLiquidacionDTO.setIdInstruccionLiquidacionOrigen(instruccion.getIdInstruccionLiquidacionOrigen() != null ? instruccion.getIdInstruccionLiquidacionOrigen().longValue() : 0);
            instruccionLiquidacionDTO.setTipoInstruccion(crearTipoInstruccion(instruccion.getTipoInstruccion()));
            instruccionLiquidacionDTO.setFechaLiquidacion(instruccion.getFechaLiquidacion());
            instruccionLiquidacionDTO.setDescripcionEstadoInstruccion(instruccion.getEstadoInstruccion().getName());
            instruccionLiquidacionDTO.setIdEstadoInstruccion(instruccion.getEstadoInstruccion().getValue());
            instruccionLiquidacionDTO.setEsBancoTrabajo(instruccion.isBancoTrabajo());
            // instruccionLiquidacionDTO.setInstruccionEfectivo(crearInstruccionEfectivoDTO(instruccion.getInstruccionEfectivo()));
        }
        return instruccionLiquidacionDTO;
    }


    public static TipoInstruccionDTO crearTipoInstruccion(TipoInstruccion tipoInstruccion) {
        TipoInstruccionDTO tipoInstruccionDTO = null;
        if (tipoInstruccion != null) {
            tipoInstruccionDTO = new TipoInstruccionDTO();
            tipoInstruccionDTO.setIdTipoInstruccion(tipoInstruccion.getIdTipoInstruccion().longValue());
            tipoInstruccionDTO.setDescripcion(
                    tipoInstruccion.getDescripcion() != null ? tipoInstruccion.getDescripcion().toUpperCase() : StringUtils.EMPTY);
            tipoInstruccionDTO.setNombreCorto(
                    tipoInstruccion.getNombreCorto() != null ? tipoInstruccion.getNombreCorto().toUpperCase() : StringUtils.EMPTY);
            tipoInstruccionDTO.setInstruccion(
                    tipoInstruccion.getInstruccion() != null ? tipoInstruccion.getInstruccion().toUpperCase() : StringUtils.EMPTY);
        }
        return tipoInstruccionDTO;
    }

    /**
     * Crea un objeto {@link PosicionDTO} a partir de un objeto {@link PosicionNombrada}
     *
     * @param posicion Pojo base
     * @return Dto creado
     */
    public static PosicionDTO crearPosicionNombradaDTO(PosicionNombrada posicion) {
        PosicionDTO dto = null;
        if (posicion != null) {
            dto = new PosicionDTO();
            dto.setIdPosicion(posicion.getIdPosicion().longValue());
            if (posicion.getBoveda() != null) {
                dto.setBoveda(new BovedaDTO(posicion.getBoveda().getIdBoveda().longValue()));
                dto.getBoveda().setDescripcion(posicion.getBoveda().getDescripcion());
            }
            dto.setEmision(crearEmisionDTO(posicion.getCupon().getEmision()));
            dto.getEmision().setCupon(posicion.getCupon().getClaveCupon());
            dto.setCupon(crearCuponDTO(posicion.getCupon()));
            dto.setPosicionDisponible(posicion.getPosicionDisponible().toString());
            dto.setPosicionDisponibleNumerica(new BigDecimal(posicion.getPosicionDisponible()));
            dto.setPosicionNoDisponible(posicion.getPosicionNoDisponible().toString());
            dto.setPosicionNoDisponibleNumerica(new BigDecimal(posicion.getPosicionNoDisponible()));
            dto.setPosicion(posicion.getPosicionDisponible().add(posicion.getPosicionNoDisponible()).toString());
            dto.setPosicionNumerica(new BigDecimal(posicion.getPosicionDisponible().add(posicion.getPosicionNoDisponible())));
            BigDecimal valorNominalEmision = new BigDecimal(String.valueOf(dto.getEmision().getValorNominal()));
            BigDecimal posicionTotal = new BigDecimal(posicion.getPosicionDisponible().add(posicion.getPosicionNoDisponible()));
            dto.setValuacionNominal(valorNominalEmision.multiply(posicionTotal));
        }
        return dto;
    }

    /**
     * Crea un objeto {@link PosicionDTO} a partir de un objeto {@link PosicionNombradaHistorico}
     *
     * @param posicion Pojo base
     * @return Dto creado
     */
    public static PosicionDTO crearPosicionNombradaHistoricoDTO(PosicionNombradaHistorico posicion) {
        PosicionDTO dto = null;
        if (posicion != null) {
            dto = new PosicionDTO();
            dto.setIdPosicion(posicion.getIdPosicion().longValue());
            if (posicion.getBoveda() != null) {
                dto.setBoveda(new BovedaDTO(posicion.getBoveda().getIdBoveda().longValue()));
                dto.getBoveda().setDescripcion(posicion.getBoveda().getDescripcion());
            }
            dto.setEmision(crearEmisionDTO(posicion.getCupon().getEmision()));
            dto.getEmision().setCupon(posicion.getCupon().getClaveCupon());
            dto.setCupon(crearCuponDTO(posicion.getCupon()));
            dto.setPosicionDisponible(posicion.getPosicionDisponible().toString());
            dto.setPosicionDisponibleNumerica(new BigDecimal(posicion.getPosicionDisponible()));
            dto.setPosicionNoDisponible(posicion.getPosicionNoDisponible().toString());
            dto.setPosicionNoDisponibleNumerica(new BigDecimal(posicion.getPosicionNoDisponible()));
            dto.setPosicion(posicion.getPosicionDisponible().add(posicion.getPosicionNoDisponible()).toString());
            dto.setPosicionNumerica(new BigDecimal(posicion.getPosicionDisponible().add(posicion.getPosicionNoDisponible())));
            BigDecimal valorNominalEmision = new BigDecimal(String.valueOf(dto.getEmision().getValorNominal()));
            BigDecimal posicionTotal = new BigDecimal(posicion.getPosicionDisponible().add(posicion.getPosicionNoDisponible()));
            dto.setValuacionNominal(valorNominalEmision.multiply(posicionTotal));
        }
        return dto;
    }

    /**
     * Crea un objeto {@link SaldoEfectivoDTO} a partir de un objeto {@link SaldoNombrada}
     *
     * @param saldoNombrada Pojo base
     * @return Dto creado
     */
    public static SaldoEfectivoDTO crearSaldoEfectivoNombradaDTO(SaldoNombrada saldoNombrada) {
        SaldoEfectivoDTO saldoEfectivoDTO = new SaldoEfectivoDTO();
        saldoEfectivoDTO.setIdSaldo(saldoNombrada.getIdSaldo().longValue());
        if (saldoNombrada.getDivisa() != null) {
            saldoEfectivoDTO.setDivisa(new DivisaDTO(saldoNombrada.getDivisa().getIdDivisa().longValue()));
            saldoEfectivoDTO.getDivisa().setDescripcion(saldoNombrada.getDivisa().getDescripcion());
        }
        if (saldoNombrada.getBoveda() != null) {
            saldoEfectivoDTO.setBoveda(new BovedaDTO(saldoNombrada.getBoveda().getIdBoveda().longValue()));
            saldoEfectivoDTO.getBoveda().setDescripcion(saldoNombrada.getBoveda().getDescripcion());
        }
        saldoEfectivoDTO.setCuenta(new CuentaEfectivoDTO(saldoNombrada.getCuentaNombrada().getIdCuentaNombrada().longValue()));
        saldoEfectivoDTO.setSaldo(saldoNombrada.getSaldoDisponible().doubleValue() + saldoNombrada.getSaldoNoDisponible().doubleValue());
        saldoEfectivoDTO.setSaldoDisponible(saldoNombrada.getSaldoDisponible().doubleValue());
        saldoEfectivoDTO.setSaldoNoDisponible(saldoNombrada.getSaldoNoDisponible().doubleValue());
        return saldoEfectivoDTO;
    }

    /**
     * Crea un objeto {@link SaldoEfectivoDTO} a partir de un objeto {@link SaldoNombradaHistorico}
     *
     * @param saldoNombrada Pojo base
     * @return Dto creado
     */
    public static SaldoEfectivoDTO crearSaldoEfectivoNombradaHistoricaDTO(SaldoNombradaHistorico saldoNombrada) {
        SaldoEfectivoDTO saldoEfectivoDTO = new SaldoEfectivoDTO();
        saldoEfectivoDTO.setIdSaldo(saldoNombrada.getIdSaldoHistorico().longValue());
        if (saldoNombrada.getDivisa() != null) {
            saldoEfectivoDTO.setDivisa(new DivisaDTO(saldoNombrada.getDivisa().getIdDivisa().longValue()));
            saldoEfectivoDTO.getDivisa().setDescripcion(saldoNombrada.getDivisa().getDescripcion());
        }
        if (saldoNombrada.getBoveda() != null) {
            saldoEfectivoDTO.setBoveda(new BovedaDTO(saldoNombrada.getBoveda().getIdBoveda().longValue()));
            saldoEfectivoDTO.getBoveda().setDescripcion(saldoNombrada.getBoveda().getDescripcion());
        }
        saldoEfectivoDTO.setCuenta(new CuentaEfectivoDTO(saldoNombrada.getCuentaNombrada().getIdCuentaNombrada().longValue()));
        saldoEfectivoDTO.setSaldo(saldoNombrada.getSaldoDisponible().doubleValue() + saldoNombrada.getSaldoNoDisponible().doubleValue());
        saldoEfectivoDTO.setSaldoDisponible(saldoNombrada.getSaldoDisponible().doubleValue());
        saldoEfectivoDTO.setSaldoNoDisponible(saldoNombrada.getSaldoNoDisponible().doubleValue());
        saldoEfectivoDTO.setFecha(saldoNombrada.getFechaCreacion());
        return saldoEfectivoDTO;
    }

    public static CuentaEfectivoDTO crearCuentaEfectivoDTO(CuentaControlada cuenta) {
        CuentaEfectivoDTO dto = null;
        if (cuenta != null) {
            dto = new CuentaEfectivoDTO();
            dto.setTipoCuenta(new TipoCuentaDTO(cuenta.getTipoCuenta().getIdTipoCuenta().toString(), cuenta.getTipoCuenta().getDescripcion()));
            dto.setIdCuenta(cuenta.getIdCuentaControlada().longValue());
            dto.setTipoNaturaleza(new TipoNaturalezaDTO(cuenta.getTipoCuenta().getIdTipoCuenta().toString(), cuenta.getTipoCuenta().getDescripcion()));
            dto.setInstitucion(crearInstitucionDTO(cuenta.getInstitucion()));
            dto.setNumeroCuenta(
                    cuenta.getInstitucion().getTipoInstitucion().getClaveTipoInstitucion() +
                            cuenta.getInstitucion().getFolioInstitucion() +
                            cuenta.getCuenta());
            dto.setDescripcion(dto.getNumeroCuenta());
            dto.setNombreCuenta(cuenta.getNombreCuenta());
            dto.setTipoCustodia(cuenta.getTipoCuenta().getTipoCustodia());
        }
        return dto;
    }


    public static InstitucionDTO crearInstitucionDTO(Institucion institucion) {
        InstitucionDTO dto = new InstitucionDTO();
        try {
            if (institucion != null) {
                dto.setId(institucion.getIdInstitucion().longValue());
                dto.setNombreCorto(institucion.getNombreCorto());
                if (institucion.getTipoInstitucion() != null) {
                    dto.setClaveTipoInstitucion(institucion.getTipoInstitucion().getClaveTipoInstitucion());
                    dto.setTipoInstitucionDTO(new TipoInstitucionDTO(
                            institucion.getTipoInstitucion().getIdTipoInstitucion().longValue()));
                } else {
                    dto.setClaveTipoInstitucion("");
                }
                dto.setFolioInstitucion(institucion.getFolioInstitucion());
            }
        } catch (Exception ex) {

        }
        return dto;
    }

    /**
     * Crea una clase {@link CuponDTO} a partir de un {@link Cupon}
     *
     * @param cupon Pojo base
     * @return Dto creado
     */
    public static CuponDTO crearCuponDTO(Cupon cupon) {
        CuponDTO cuponDTO = null;
        if (cupon != null) {
            cuponDTO = new CuponDTO();
            cuponDTO.setClaveCupon(cupon.getClaveCupon());
            cuponDTO.setIdCupon(cupon.getIdCupon().longValue());
            cuponDTO.setEmision(crearEmisionDTO(cupon.getEmision()));
        }
        return cuponDTO;
    }

    /**
     * Crea una clase {@link EmisionDTO} a partir de un {@link Emision}
     *
     * @param emision Pojo base
     * @return Dto creado
     */
    public static EmisionDTO crearEmisionDTO(Emision emision) {
        EmisionDTO dto = null;
        if (emision != null) {
            dto = new EmisionDTO();
            dto.setId(emision.getIdEmision().longValue());
            dto.setIsin(emision.getIsin());
            if (emision.getEmisora() != null) {
                dto.getEmisora().setId(emision.getEmisora().getIdEmisora().longValue());
                dto.getEmisora().setDescripcion(emision.getEmisora().getDescripcion());
            }
            dto.setSerie(new SerieDTO(emision.getSerie(), emision.getSerie()));
            dto.setTipoValor(
                    new TipoValorDTO(emision.getInstrumento().getIdInstrumento().longValue(), emision.getInstrumento().getClaveTipoValor()));
            dto.getTipoValor().setDescripcion(emision.getInstrumento().getDescripcion());
            dto.getTipoValor().setMercado(
                    new MercadoDTO(emision.getInstrumento().getMercado().getIdMercado().longValue(), emision.getInstrumento().getMercado().getClave()));
            dto.setValorNominal(emision.getValorNominal().doubleValue());
            dto.getDivisa().setId(emision.getDivisa().getIdDivisa().longValue());
            dto.getDivisa().setDescripcion(emision.getDivisa().getDescripcion());
            dto.getBoveda().setId(emision.getBoveda().getIdBoveda().longValue());
            dto.getBoveda().setNombreCorto(emision.getBoveda().getNombreCorto());
        }
        return dto;
    }

}

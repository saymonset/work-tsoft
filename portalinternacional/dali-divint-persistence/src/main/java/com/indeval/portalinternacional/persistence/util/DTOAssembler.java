// Cambio Multidivisas
package com.indeval.portalinternacional.persistence.util;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portaldali.persistence.modelo.CuentaControlada;
import com.indeval.portaldali.persistence.modelo.CuentaNombrada;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portaldali.persistence.modelo.TipoBoveda;
import com.indeval.portaldali.persistence.modelo.TipoCuenta;
import com.indeval.portaldali.persistence.modelo.TipoInstitucion;
import com.indeval.portalinternacional.middleware.servicios.constantes.EstatusDB;
import com.indeval.portalinternacional.middleware.servicios.dto.*;
import com.indeval.portalinternacional.middleware.servicios.dto.diasIhabilesDivisas.HistoricoDiasInhabilesDivisasDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.*;

import javax.persistence.Column;
import javax.persistence.Id;

import static com.indeval.portalinternacional.middleware.servicios.constantes.EstatusDB.AUTORIZADO;
import static com.indeval.portalinternacional.middleware.servicios.constantes.EstatusDB.REGISTRADO;

public class DTOAssembler {

    /**
     * Transforma una lista de BigInteger en una lista de objetos Long.
     *
     * @param listaBigInt una lista con elementos BigInteger
     * @return una lista con objetos Long
     */
    public static List<Long> transformarListaBigIntegerEnLong(
            List<BigInteger> listaBigInt) {
        List<Long> listaLong = new ArrayList<Long>();

        for (BigInteger big : listaBigInt) {
            listaLong.add(big.longValue());
        }

        return listaLong;
    }

    /**
     * Crea un objeto del tipo {@link SaldoEfectivoDTO} a partir de un objeto
     * {@link SaldoNombradaHistorico}
     *
     * @param saldoNombrada Objeto de Hibernate para construir el DTO.
     * @return DTO creado a partir del parámetro de entrada.
     */
    public static SaldoEfectivoDTO crearSaldoEfectivoDTO(
            SaldoControladaHistorico saldoControlada) {
        SaldoEfectivoDTO saldoEfectivoDTO = new SaldoEfectivoDTO();

        saldoEfectivoDTO.setIdSaldo(saldoControlada.getIdSaldoHistorico()
                .longValue());

        // Divisa en que esta dado el saldo
        saldoEfectivoDTO.setDivisa(crearDivisaDTO(saldoControlada.getDivisa()));
        // Boveda en donde se resguarda el saldo */
        saldoEfectivoDTO.setBoveda(crearBovedaDTO(saldoControlada.getBoveda()));
        // Cuenta del saldo */
        saldoEfectivoDTO.setCuenta(crearCuentaEfectivoDTO(saldoControlada
                .getCuentaControlada()));

        saldoEfectivoDTO.setSaldo(saldoControlada.getSaldo().doubleValue());
        saldoEfectivoDTO.setFecha(saldoControlada.getFechaCreacion());
        return saldoEfectivoDTO;
    }

    /**
     * Crea un objeto del tipo {@link SaldoEfectivoDTO} a partir de un objeto
     * {@link SaldoNombradaInt}
     *
     * @param saldoNombrada Objeto de Hibernate para construir el DTO.
     * @return DTO creado a partir del parámetro de entrada.
     */
    public static SaldoEfectivoDTO crearSaldoEfectivoDTO(SaldoControladaInt saldoControlada) {
        SaldoEfectivoDTO saldoEfectivoDTO = new SaldoEfectivoDTO();
        saldoEfectivoDTO.setIdSaldo(saldoControlada.getIdSaldo().longValue());
        // Divisa en que esta dado el saldo
        saldoEfectivoDTO.setDivisa(crearDivisaDTO(saldoControlada.getDivisa()));
        // Boveda en donde se resguarda el saldo */
        saldoEfectivoDTO.setBoveda(crearBovedaDTO(saldoControlada.getBoveda()));
        // Cuenta del saldo */
        saldoEfectivoDTO.setCuenta(crearCuentaEfectivoDTO(saldoControlada.getCuentaControlada()));
        saldoEfectivoDTO.setSaldo(saldoControlada.getSaldo().doubleValue());
        saldoEfectivoDTO.setIdSaldo(saldoControlada.getIdSaldo().longValue());
        return saldoEfectivoDTO;
    }

    /**
     * Crea un objeto del tipo {@link SaldoEfectivoDTO} a partir de un objeto
     * {@link SaldoNombradaHistorico}
     *
     * @param saldoNombrada Objeto de Hibernate para construir el DTO.
     * @return DTO creado a partir del parámetro de entrada.
     */
    public static SaldoEfectivoDTO crearSaldoEfectivoDTO(SaldoNombradaHistorico saldoNombrada) {
        SaldoEfectivoDTO saldoEfectivoDTO = new SaldoEfectivoDTO();
        //Id del saldo
        saldoEfectivoDTO.setIdSaldo(saldoNombrada.getIdSaldoHistorico().longValue());
        // Divisa en que esta dado el saldo
        saldoEfectivoDTO.setDivisa(crearDivisaDTO(saldoNombrada.getDivisa()));
        // Boveda en donde se resguarda el saldo
        saldoEfectivoDTO.setBoveda(crearBovedaDTO(saldoNombrada.getBoveda()));
        // Cuenta del saldo
        saldoEfectivoDTO.setCuenta(crearCuentaEfectivoDTO(saldoNombrada.getCuentaNombrada()));
        // Saldos de la cuenta
        saldoEfectivoDTO.setSaldo(saldoNombrada.getSaldoDisponible().doubleValue() + saldoNombrada.getSaldoNoDisponible().doubleValue());
        saldoEfectivoDTO.setSaldoDisponible(saldoNombrada.getSaldoDisponible().doubleValue());
        saldoEfectivoDTO.setSaldoNoDisponible(saldoNombrada.getSaldoNoDisponible().doubleValue());
        //Fecha de creación
        saldoEfectivoDTO.setFecha(saldoNombrada.getFechaCreacion());
        return saldoEfectivoDTO;
    }

    /**
     * Crea un objeto del tipo {@link SaldoEfectivoDTO} a partir de un objeto
     * {@link SaldoNombradaInt}
     *
     * @param saldoNombrada Objeto de Hibernate para construir el DTO.
     * @return DTO creado a partir del parámetro de entrada.
     */
    public static SaldoEfectivoDTO crearSaldoEfectivoDTO(
            SaldoNombradaInt saldoNombrada) {
        SaldoEfectivoDTO saldoEfectivoDTO = new SaldoEfectivoDTO();
        saldoEfectivoDTO.setIdSaldo(saldoNombrada.getIdSaldo().longValue());

        // Divisa en que esta dado el saldo
        saldoEfectivoDTO.setDivisa(crearDivisaDTO(saldoNombrada.getDivisa()));
        // Boveda en donde se resguarda el saldo */
        saldoEfectivoDTO.setBoveda(crearBovedaDTO(saldoNombrada.getBoveda()));
        // Cuenta del saldo */
        saldoEfectivoDTO.setCuenta(crearCuentaEfectivoDTO(saldoNombrada
                .getCuentaNombrada()));

        // Saldo de la cuenta */
        saldoEfectivoDTO.setSaldo(saldoNombrada.getSaldoDisponible()
                .doubleValue()
                + saldoNombrada.getSaldoNoDisponible().doubleValue());

        saldoEfectivoDTO.setSaldoDisponible(saldoNombrada.getSaldoDisponible()
                .doubleValue());

        saldoEfectivoDTO.setSaldoNoDisponible(saldoNombrada
                .getSaldoNoDisponible().doubleValue());
        saldoEfectivoDTO.setIdSaldo(saldoNombrada.getIdSaldo().longValue());
        return saldoEfectivoDTO;
    }

    /**
     * Crea un objeto del tipo {@link DivisaDTO} a partir de un objeto de hibernate
     * del tipo {@link DivisaInt}
     *
     * @param divisa Objeto de hibernate con los valores a setear
     * @return DTO con los datos del objeto hibernate
     */
    public static DivisaDTO crearDivisaDTO(DivisaInt divisa) {
        DivisaDTO divisaDTO = null;
        if (divisa != null) {
            divisaDTO = new DivisaDTO();
            divisaDTO.setId(divisa.getIdDivisa().longValue());
            divisaDTO.setClaveAlfabetica(divisa.getClaveAlfabetica());
            divisaDTO.setClaveNumerica(divisa.getClaveNumerica());
            divisaDTO.setDescripcion(divisa.getDescripcion());
        }
        return divisaDTO;
    }

    public static ConciliacionDivisasIntDTO crearConciliacionDivisasIntDTO(ConciliacionDivisasInt conciliacionDivisasInt) {
        ConciliacionDivisasIntDTO conciliacionDivisasIntDTO = null;
        if (conciliacionDivisasInt != null) {
            conciliacionDivisasIntDTO = new ConciliacionDivisasIntDTO();
            conciliacionDivisasIntDTO.setFecha(conciliacionDivisasInt.getFecha());
            conciliacionDivisasIntDTO.setIdConciliacionEfectivo(conciliacionDivisasInt.getIdConciliacionEfectivo());
            conciliacionDivisasIntDTO.setIdBoveda(conciliacionDivisasInt.getIdBoveda());
            conciliacionDivisasIntDTO.setIdDivisa(conciliacionDivisasInt.getIdDivisa());
            conciliacionDivisasIntDTO.setMontoCustodio(conciliacionDivisasInt.getMontoCustodio());
            conciliacionDivisasIntDTO.setMontoIndeval(conciliacionDivisasInt.getMontoIndeval());
            conciliacionDivisasIntDTO.setMontoDiferencia(conciliacionDivisasInt.getMontoDiferencia());
        }
        return conciliacionDivisasIntDTO;
    }

    public static BovedaDto crearBovedaDTO(Boveda boveda) {
        BovedaDto dto = null;
        if (boveda != null) {
            dto = new BovedaDto();
            dto.setIdBoveda(boveda.getIdBoveda().intValue());
            dto.setDescripcion(boveda.getDescripcion());
            dto.setNombreCorto(boveda.getNombreCorto());

//			TipoBovedaDto dtoB = null;
//			if (boveda.getTipoBoveda() != null) {
//				dtoB = new TipoBovedaDto();
//				dtoB.setIdTipoBoveda(boveda.getTipoBoveda().getIdTipoBoveda());
//				dtoB.setDescripcion(boveda.getTipoBoveda().getDescripcion());
//				dtoB.setClave(boveda.getTipoBoveda().getClaveTipoBoveda());
//				dtoB.setUbicacion(boveda.getTipoBoveda().getUbicacion());
//			}
//			dto.setTipoBoveda(dtoB);
        }
        return dto;
    }

    public static BovedaDto crearBovedasDTO(Bovedas boveda) {
        BovedaDto dto = null;
        if (boveda != null) {
            dto = new BovedaDto();
            dto.setIdBoveda(boveda.getIdBoveda().intValue());
            dto.setDescripcion(boveda.getDescripcion());
            dto.setNombreCorto(boveda.getNombreCorto());
        }
        return dto;
    }

    public static HorariosCustodiosDto crearHorariosCustodiosDTO(HorariosCustodios horariosCustodios) {
        HorariosCustodiosDto dto = null;
        if (horariosCustodios != null) {
            dto = new HorariosCustodiosDto();
            dto.setIdHorariosCustodios(horariosCustodios.getIdHorariosCustodios());
            dto.setIdCustodio(horariosCustodios.getIdCustodio());
            dto.setIdDivisa(horariosCustodios.getIdDivisa());
            dto.setIdCustodio(horariosCustodios.getIdCustodio());
            dto.setHorarioInicial(horariosCustodios.getHorarioInicial());
            dto.setHorarioFinal(horariosCustodios.getHorarioFinal());
            dto.setCreador(horariosCustodios.getCreador());
            dto.setFechaCreacion(horariosCustodios.getFechaCreacion());
            dto.setFechaUltModificacion(horariosCustodios.getFechaUltModificacion());
            dto.setEstatus(horariosCustodios.getEstatus());
            dto.setUsuarioChecker(horariosCustodios.getUsuarioChecker());
        }
        return dto;
    }

    /**
     * Multidivisas
     * <p>
     * Crea un objeto del tipo {@link HistoricoDiasInhabilesDivisasDTO } a partir de un objeto de hibernate
     * del tipo {@link HistoricoDiasInhabilesDivisas}
     *
     * @param entity Objeto de hibernate con los valores a setear
     * @return DTO con los datos del objeto hibernate
     */
    public static HistoricoDiasInhabilesDivisasDTO crearHistoricoDiasInhabilesDivisasDTO(
            HistoricoDiasInhabilesDivisas entity) {
        if (entity != null) {
            HistoricoDiasInhabilesDivisasDTO dto = new HistoricoDiasInhabilesDivisasDTO();
            dto.setIdHistorico(entity.getIdHistorico());
            dto.setCreador(entity.getCreador());
            dto.setFechaCreacion(entity.getFechaCreacion());
            dto.setFechaUltModificacion(entity.getFechaUltModificacion());
            dto.setRegistrosCorrectos(entity.getRegistrosCorrectos());
            dto.setRegistrosError(entity.getRegistrosError());
            dto.setRegistrosTotal(entity.getRegistrosTotal());
            dto.setUsuarioChecker(entity.getUsuarioChecker());
            dto.setNombreArchivo(entity.getNombreArchivo());
            dto.setEstatus(entity.getEstatus());
            dto.setEstadoDB(EstatusDB.obtenerEstado(dto.getEstatus()));
            dto.setPuedeAutorizar(dto.getEstadoDB() == REGISTRADO);
            dto.setPuedeCancelar(dto.getEstadoDB() == REGISTRADO || dto.getEstadoDB() == AUTORIZADO);
            return dto;
        }
        return null;
    }

    public static TipoBovedaDto crearTipoBovedaDTO(TipoBoveda tipoBoveda) {
        TipoBovedaDto dto = null;
        if (tipoBoveda != null) {
            dto = new TipoBovedaDto();
            dto.setIdTipoBoveda(tipoBoveda.getIdTipoBoveda().intValue());
            dto.setDescripcion(tipoBoveda.getDescripcion());
            dto.setClave(tipoBoveda.getClaveTipoBoveda());
            dto.setUbicacion(tipoBoveda.getUbicacion());
        }
        return dto;
    }

    /**
     * Crea un objeto del tipo {@link InstitucionWebDTO} a partir de un objeto de
     * hibernate del tipo {@link institucion}
     *
     * @param cuenta Objeto de donde se tomarón valores para crear el DTO de salida
     * @return DTO creado a partir del parámetro de entrada
     */
    public static InstitucionWebDTO crearInstitucionWebDTO(Instituciones institucion) {
        InstitucionWebDTO dto = new InstitucionWebDTO();
        try {
            if (institucion != null) {
                dto.setIdInstitucion(institucion.getIdInstitucion().intValue());
                dto.setNombre(institucion.getNombre());
                dto.setRazonSocial(institucion.getRazonSocial());
                if (institucion.getTipoInstitucion() != null) {
                    dto.setClaveTipoInstitucion(institucion.getTipoInstitucion().getClaveTipoInstitucion());
                    dto.setIdTipoInstitucion(institucion.getTipoInstitucion().getIdTipoInstitucion().intValue());
                } else {
                    dto.setClaveTipoInstitucion("");
                }
                dto.setFolio(institucion.getFolio());
                dto.setClaveCasfim(institucion.getClaveCasfim());
                dto.setClaveSpei(institucion.getClaveSpei());
                dto.setOperaSiac(institucion.getOperaSiac());

                /**
                 * Se agregan campos para obtener la cuenta clabe y spei del beneficiario de la
                 * institucion
                 */

                dto.setClabeBeneficiario(institucion.getClabeBeneficiario());
                dto.setClaveSpeiBeneficiario(institucion.getClaveSpeiBeneficiario());
            }
        } catch (Exception ex) {

        }
        return dto;
    }

    public static Instituciones crearInstitucion(InstitucionWebDTO InstitucionWebDTO) {
        Instituciones institucion = null;
        if (InstitucionWebDTO != null) {
            institucion = new Instituciones();
            institucion.setIdInstitucion(InstitucionWebDTO.getIdInstitucion());
            institucion.setNombre(InstitucionWebDTO.getNombre());
            institucion.setRazonSocial(InstitucionWebDTO.getRazonSocial());
            TipoInstitucion tipoInstitucion = new TipoInstitucion();
            tipoInstitucion.setIdTipoInstitucion(InstitucionWebDTO.getIdTipoInstitucion().longValue());
            tipoInstitucion.setClaveTipoInstitucion(InstitucionWebDTO.getClaveTipoInstitucion());
            institucion.setFolio(InstitucionWebDTO.getFolio());
            institucion.setClaveSpei(InstitucionWebDTO.getClaveSpei());
            institucion.setClaveCasfim(InstitucionWebDTO.getClaveCasfim());
        }
        return institucion;
    }

    /**
     * Crea un objeto del tipo {@link InstitucionDTO} a partir de un objeto de
     * hibernate del tipo {@link institucion}
     *
     * @param cuenta Objeto de donde se tomarón valores para crear el DTO de salida
     * @return DTO creado a partir del parámetro de entrada
     */
    public static InstitucionWebDTO crearInstitucionDTO(Institucion institucion) {
        InstitucionWebDTO dto = new InstitucionWebDTO();
        try {
            if (institucion != null) {
                dto.setIdInstitucion(institucion.getIdInstitucion().intValue());
                dto.setNombre(institucion.getNombreCorto());
                dto.setRazonSocial(institucion.getRazonSocial());
                if (institucion.getTipoInstitucion() != null) {
                    dto.setClaveTipoInstitucion(institucion.getTipoInstitucion().getClaveTipoInstitucion());
                    dto.setIdTipoInstitucion(institucion.getTipoInstitucion().getIdTipoInstitucion().intValue());
                } else {
                    dto.setClaveTipoInstitucion("");
                }
                dto.setFolio(institucion.getFolioInstitucion());
                dto.setClaveCasfim(institucion.getClaveCasfim());

            }
        } catch (Exception ex) {

        }
        return dto;
    }

    public static TipoInstitucionDTO crearTipoInstitucionDTO(TipoInstitucion tipoInstitucion) {
        TipoInstitucionDTO dto = new TipoInstitucionDTO();
        try {
            if (tipoInstitucion != null) {
                dto.setDescripcion(tipoInstitucion.getDescripcion());
                dto.setId(tipoInstitucion.getIdTipoInstitucion().longValue());
                dto.setClaveTipoInstitucion(tipoInstitucion.getClaveTipoInstitucion());
            }
        } catch (Exception ex) {

        }
        return dto;
    }

    /**
     * Crea un objeto del tipo {@link CuentaEfectivoDTO} a partir de un objeto de
     * hibernate del tipo {@link CuentaControlada}
     *
     * @param cuenta Objeto de donde se tomarón valores para crear el DTO de salida
     * @return DTO creado a partir del parámetro de entrada
     */
    public static CuentaEfectivoDTO crearCuentaEfectivoDTO(CuentaControlada cuenta) {
        CuentaEfectivoDTO dto = null;
        if (cuenta != null) {
            dto = new CuentaEfectivoDTO();
            dto.setTipoCuenta(crearTipoCuentaDTO(cuenta.getTipoCuenta()));
            dto.setIdCuenta(cuenta.getIdCuentaControlada().longValue());
            dto.setTipoNaturaleza(crearTipoNaturalezaDTO(cuenta.getTipoCuenta()));
            dto.setInstitucion(crearInstitucionDTO(cuenta.getInstitucion()));
            dto.setNumeroCuenta(cuenta.getInstitucion().getTipoInstitucion().getClaveTipoInstitucion()
                    + cuenta.getInstitucion().getFolioInstitucion() + cuenta.getCuenta());
            dto.setDescripcion(dto.getNumeroCuenta());
            dto.setNombreCuenta(cuenta.getNombreCuenta());
            dto.setTipoCustodia(cuenta.getTipoCuenta().getTipoCustodia());
        }
        return dto;
    }

    /**
     * Crea un objeto del tipo {@link TipoCuentaDTO} a partir de un objeto de
     * hibernate del tipo {@link TipoCuenta}
     *
     * @param tipoCuenta Objeto de donde se tomarón valores para crear el DTO de
     *                   salida
     * @return DTO creado a partir del parámetro de entrada
     */
    public static TipoCuentaDTO crearTipoCuentaDTO(TipoCuenta tipoCuenta) {
        TipoCuentaDTO dto = null;
        if (tipoCuenta != null) {
            dto = new TipoCuentaDTO();
            dto.setDescripcion(
                    tipoCuenta.getNaturalezaProcesoLiquidacion().equals(TipoCuentaDTO.CUENTA_CONTROLADA) ? "CONTROLADA"
                            : "NOMBRADA");
            dto.setId(tipoCuenta.getNaturalezaProcesoLiquidacion());
        }
        return dto;
    }

    /**
     * Crea un DTO del tipo {@link TipoNaturalezaDTO} a partir de un objeto
     * {@link TipoCuenta}
     *
     * @param tipoCuenta Tipo de cuenta origen para crear el tipo de naturaleza
     * @return DTO creado a partir del parámetro de entrada
     */
    private static TipoNaturalezaDTO crearTipoNaturalezaDTO(TipoCuenta tipoCuenta) {
        TipoNaturalezaDTO dto = null;
        if (tipoCuenta != null) {
            dto = new TipoNaturalezaDTO();
            dto.setId(tipoCuenta.getNaturalezaContable());
            dto.setDescripcion(
                    tipoCuenta.getNaturalezaContable().equals(TipoNaturalezaDTO.ACTIVO) ? "ACTIVO" : "PASIVO");
        }
        return dto;
    }

    /**
     * Crea un objeto del tipo {@link CuentaEfectivoDTO} a partir de un objeto
     * de hibernate del tipo {@link CuentaNombrada}
     *
     * @param cuenta Objeto de donde se tomarón valores para crear el DTO de salida
     * @return DTO creado a partir del parámetro de entrada
     */
    public static CuentaEfectivoDTO crearCuentaEfectivoDTO(CuentaNombrada cuenta) {

        CuentaEfectivoDTO dto = null;
        if (cuenta != null) {

            dto = new CuentaEfectivoDTO();
            dto.setTipoCuenta(crearTipoCuentaDTO(cuenta.getTipoCuenta()));
            dto
                    .setTipoNaturaleza(crearTipoNaturalezaDTO(cuenta
                            .getTipoCuenta()));
            dto.setIdCuenta(cuenta.getIdCuentaNombrada().longValue());
            dto.setInstitucion(crearInstitucionDTO(cuenta.getInstitucion()));
            dto.setNumeroCuenta(cuenta.getInstitucion().getTipoInstitucion()
                    .getClaveTipoInstitucion()
                    + cuenta.getInstitucion().getFolioInstitucion()
                    + cuenta.getCuenta());
            dto.setDescripcion(dto.getNumeroCuenta());
            dto.setNombreCuenta(cuenta.getNombreCuenta());
            dto.setCuenta(cuenta.getCuenta());
        }
        return dto;
    }

    /**
     * Crea un objeto del tipo {@link CuentaDTO} a partir de un objeto de
     * hibernate del tipo {@link CuentaNombrada}
     *
     * @param cuenta Objeto de donde se tomarón valores para crear el DTO de salida
     * @return DTO creado a partir del parámetro de entrada
     */
    public static CuentaDTO crearCuentaDTO(CuentaNombrada cuenta) {
        CuentaDTO dto = null;
        if (cuenta != null) {

            dto = new CuentaDTO();
            dto.setTipoTenencia(crearTipoTeneciaDTO(cuenta.getTipoCuenta()));
            dto.setIdCuenta(cuenta.getIdCuentaNombrada().longValue());
            dto.setInstitucion(crearInstitucionDTO(cuenta.getInstitucion()));
            dto.setNumeroCuenta(cuenta.getInstitucion().getTipoInstitucion()
                    .getClaveTipoInstitucion()
                    + cuenta.getInstitucion().getFolioInstitucion()
                    + cuenta.getCuenta());
            dto.setDescripcion(dto.getNumeroCuenta());
            dto.setNombreCuenta(cuenta.getNombreCuenta());
            dto.setCuenta(cuenta.getCuenta());
        }
        return dto;
    }

    /**
     * Crea un objeto del tipo {@link CuentaDTO} a partir de un objeto de
     * hibernate del tipo {@link CuentaControlada}
     *
     * @param cuenta Objeto de donde se tomarón valores para crear el DTO de salida
     * @return DTO creado a partir del parámetro de entrada
     */
    public static CuentaDTO crearCuentaDTO(CuentaControlada cuenta) {
        CuentaDTO dto = null;
        if (cuenta != null) {

            dto = new CuentaDTO();
            dto.setTipoTenencia(crearTipoTeneciaDTO(cuenta.getTipoCuenta()));
            dto.setIdCuenta(cuenta.getIdCuentaControlada().longValue());
            dto.setInstitucion(crearInstitucionDTO(cuenta.getInstitucion()));
            dto.setCuenta(cuenta.getCuenta());
            dto.setNumeroCuenta(cuenta.getInstitucion().getTipoInstitucion()
                    .getClaveTipoInstitucion()
                    + cuenta.getInstitucion().getFolioInstitucion()
                    + cuenta.getCuenta());
            dto.setDescripcion(dto.getNumeroCuenta());
            dto.setNombreCuenta(cuenta.getNombreCuenta());
        }
        return dto;
    }

    /**
     * Crea un objeto del tipo {@link TipoTenenciaDTO} a partir de un objeto de
     * hibernate del tipo {@link TipoCuenta}
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
            dto.setClaveTipoCuenta(tipoCuenta.getClaveTipoCuenta());
            dto.setDescripcion(tipoCuenta.getDescripcion());
            dto.setIdTipoCuenta(tipoCuenta.getIdTipoCuenta().longValue());
            dto.setTipoCustodia(tipoCuenta.getTipoCustodia());
            tipoCuentaDTO.setId(tipoCuenta.getNaturalezaProcesoLiquidacion());
            tipoCuentaDTO
                    .setDescripcion(tipoCuenta
                            .getNaturalezaProcesoLiquidacion().equals("C") ? "CONTROLADA"
                            : "NOMBRADA");
            tipoNaturalezaDTO.setId(tipoCuenta.getNaturalezaContable());
            tipoNaturalezaDTO.setDescripcion(tipoCuenta.getNaturalezaContable()
                    .equals("P") ? "PASIVO" : "ACTIVO");
            dto.setTipoCuenta(tipoCuentaDTO);
            dto.setTipoNaturaleza(tipoNaturalezaDTO);
        }
        return dto;
    }
}

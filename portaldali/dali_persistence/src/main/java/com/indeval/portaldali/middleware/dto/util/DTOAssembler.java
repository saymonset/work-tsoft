/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.dto.util;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.dto.BitacoraEdosCuentaRetiroDTO;
import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.CuentaEfectivoDTO;
import com.indeval.portaldali.middleware.dto.CuentaRetiroDTO;
import com.indeval.portaldali.middleware.dto.CuentaRetiroEfectivoDTO;
import com.indeval.portaldali.middleware.dto.CuentaRetiroInternacionalDTO;
import com.indeval.portaldali.middleware.dto.CuponDTO;
import com.indeval.portaldali.middleware.dto.DetalleMovimientoEfectivoDTO;
import com.indeval.portaldali.middleware.dto.DetalleMovimientoValorDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.EmisionDTO;
import com.indeval.portaldali.middleware.dto.EmisoraDTO;
import com.indeval.portaldali.middleware.dto.ErrorDaliDTO;
import com.indeval.portaldali.middleware.dto.EstadoInstruccionCatDTO;
import com.indeval.portaldali.middleware.dto.EstadoInstruccionDTO;
import com.indeval.portaldali.middleware.dto.EstadoPosicionDTO;
import com.indeval.portaldali.middleware.dto.EstadoSaldoDTO;
import com.indeval.portaldali.middleware.dto.EstadosCuentaDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.InstruccionEfectivoDTO;
import com.indeval.portaldali.middleware.dto.InstruccionLiquidacionDTO;
import com.indeval.portaldali.middleware.dto.MercadoDTO;
import com.indeval.portaldali.middleware.dto.MiscelaneaFiscalDTO;
import com.indeval.portaldali.middleware.dto.OperacionPosicionDTO;
import com.indeval.portaldali.middleware.dto.OperacionSaldoDTO;
import com.indeval.portaldali.middleware.dto.OperacionValorMatchDTO;
import com.indeval.portaldali.middleware.dto.ParametrosDaliDTO;
import com.indeval.portaldali.middleware.dto.ParametrosLiquidacionDTO;
import com.indeval.portaldali.middleware.dto.PosicionDTO;
import com.indeval.portaldali.middleware.dto.PropiedadesDaliDTO;
import com.indeval.portaldali.middleware.dto.RegistroContablePosicionControladaDTO;
import com.indeval.portaldali.middleware.dto.RegistroContablePosicionNombradaDTO;
import com.indeval.portaldali.middleware.dto.RegistroContableSaldoControladaDTO;
import com.indeval.portaldali.middleware.dto.RegistroContableSaldoNombradaDTO;
import com.indeval.portaldali.middleware.dto.RetiroEfectivoDTO;
import com.indeval.portaldali.middleware.dto.RetiroEfectivoInternacionalDTO;
import com.indeval.portaldali.middleware.dto.SaldoEfectivoDTO;
import com.indeval.portaldali.middleware.dto.SerieDTO;
import com.indeval.portaldali.middleware.dto.TipoAccionDTO;
import com.indeval.portaldali.middleware.dto.TipoCuentaDTO;
import com.indeval.portaldali.middleware.dto.TipoInstitucionDTO;
import com.indeval.portaldali.middleware.dto.TipoInstruccionDTO;
import com.indeval.portaldali.middleware.dto.TipoLiquidacionDTO;
import com.indeval.portaldali.middleware.dto.TipoMensajeDTO;
import com.indeval.portaldali.middleware.dto.TipoNaturalezaDTO;
import com.indeval.portaldali.middleware.dto.TipoOperacionDTO;
import com.indeval.portaldali.middleware.dto.TipoTenenciaDTO;
import com.indeval.portaldali.middleware.dto.TipoValorDTO;
import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;
import com.indeval.portaldali.middleware.services.common.constants.EstadoInstruccionConstants;
import com.indeval.portaldali.middleware.services.common.constants.RolConstants;
import com.indeval.portaldali.middleware.services.common.constants.TipoAccionConstants;
import com.indeval.portaldali.middleware.services.common.constants.TipoCargoAValores;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.InfrastructureException;
import com.indeval.portaldali.middleware.services.util.util.Constantes;
import com.indeval.portaldali.middleware.services.util.vo.BitacoraMatchVO;
import com.indeval.portaldali.persistence.model.BitacoraEstadosCuentaRetiro;
import com.indeval.portaldali.persistence.model.Boveda;
import com.indeval.portaldali.persistence.model.CuentaControlada;
import com.indeval.portaldali.persistence.model.CuentaNombrada;
import com.indeval.portaldali.persistence.model.CuentaRetiro;
import com.indeval.portaldali.persistence.model.CuentaRetiroInternacional;
import com.indeval.portaldali.persistence.model.CuentaRetiroNacional;
import com.indeval.portaldali.persistence.model.Cupon;
import com.indeval.portaldali.persistence.model.Divisa;
import com.indeval.portaldali.persistence.model.Emision;
import com.indeval.portaldali.persistence.model.EmisionPendiente;
import com.indeval.portaldali.persistence.model.Emisora;
import com.indeval.portaldali.persistence.model.ErrorDali;
import com.indeval.portaldali.persistence.model.EstadoInstruccionCat;
import com.indeval.portaldali.persistence.model.EstadoPosicionControlada;
import com.indeval.portaldali.persistence.model.EstadoPosicionNombrada;
import com.indeval.portaldali.persistence.model.EstadoSaldoControlada;
import com.indeval.portaldali.persistence.model.EstadoSaldoNombrada;
import com.indeval.portaldali.persistence.model.Institucion;
import com.indeval.portaldali.persistence.model.InstruccionEfectivo;
import com.indeval.portaldali.persistence.model.InstruccionLiquidacion;
import com.indeval.portaldali.persistence.model.InstruccionLiquidacionHistorico;
import com.indeval.portaldali.persistence.model.InstruccionMovPosicionNom;
import com.indeval.portaldali.persistence.model.InstruccionOperacionVal;
import com.indeval.portaldali.persistence.model.Instrumento;
import com.indeval.portaldali.persistence.model.InternacionalEfectivo;
import com.indeval.portaldali.persistence.model.LiquidacionParcialInstruccionMov;
import com.indeval.portaldali.persistence.model.MensajeBean;
import com.indeval.portaldali.persistence.model.MensajeBean.EstadoInstruccion;
import com.indeval.portaldali.persistence.model.Mercado;
import com.indeval.portaldali.persistence.model.OperacionMiscelaneaFiscal;
import com.indeval.portaldali.persistence.model.OperacionNombrada;
import com.indeval.portaldali.persistence.model.OperacionNombradaHistorico;
import com.indeval.portaldali.persistence.model.ParametrosDali;
import com.indeval.portaldali.persistence.model.ParametrosLiquidacion;
import com.indeval.portaldali.persistence.model.PosicionControlada;
import com.indeval.portaldali.persistence.model.PosicionControladaHistorico;
import com.indeval.portaldali.persistence.model.PosicionNombrada;
import com.indeval.portaldali.persistence.model.PosicionNombradaHistorico;
import com.indeval.portaldali.persistence.model.PropiedadesDali;
import com.indeval.portaldali.persistence.model.RegContEfecControlada;
import com.indeval.portaldali.persistence.model.RegContEfecControladaHistorico;
import com.indeval.portaldali.persistence.model.RegContEfecNombrada;
import com.indeval.portaldali.persistence.model.RegContEfecNombradaHistorico;
import com.indeval.portaldali.persistence.model.RegContValControlada;
import com.indeval.portaldali.persistence.model.RegContValControladaHistorico;
import com.indeval.portaldali.persistence.model.RegContValNombrada;
import com.indeval.portaldali.persistence.model.RegContValNombradaHistorico;
import com.indeval.portaldali.persistence.model.RetiroEfectivo;
import com.indeval.portaldali.persistence.model.RetiroEfectivoInternacional;
import com.indeval.portaldali.persistence.model.SaldoControlada;
import com.indeval.portaldali.persistence.model.SaldoControladaHistorico;
import com.indeval.portaldali.persistence.model.SaldoNombrada;
import com.indeval.portaldali.persistence.model.SaldoNombradaHistorico;
import com.indeval.portaldali.persistence.model.TipoAccion;
import com.indeval.portaldali.persistence.model.TipoCuenta;
import com.indeval.portaldali.persistence.model.TipoInstitucion;
import com.indeval.portaldali.persistence.model.TipoInstruccion;
import com.indeval.portaldali.persistence.model.TipoLiquidacion;
import com.indeval.portaldali.persistence.model.TipoMensajeCat;
import com.indeval.portaldali.persistence.model.TipoOperacion;
import com.indeval.portaldali.persistence.model.TipoRetiro;
import com.indeval.portaldali.persistence.util.TipoPagoConstants;
import com.indeval.portaldali.persistence.vo.EmisionPersistence;
import com.indeval.protocolofinanciero.api.vo.RetiroEfectivoVO;
import com.indeval.protocolofinanciero.api.vo.TraspasoEfectivoVO;
import com.thoughtworks.xstream.XStream;


/**
 * Clase de utilera para la creación de DTO a partir de objetos de la base de
 * datos entregados por Hibernate.
 *
 * @author Emigdio Hernández
 * @version 1.0
 */
public class DTOAssembler {

    private static Logger logger = LoggerFactory.getLogger(DTOAssembler.class);

    private static XStream stream = new XStream();

    static {
        stream.processAnnotations(MiscelaneaFiscalDTO.class);
        stream.omitField(MiscelaneaFiscalDTO.class, "idInstitucionCompradora");
        stream.omitField(MiscelaneaFiscalDTO.class, "idInstitucionVendedora");
        stream.omitField(MiscelaneaFiscalDTO.class, "folioInstitucionCompradora");
        stream.omitField(MiscelaneaFiscalDTO.class, "matchRequeridoId");
        stream.omitField(MiscelaneaFiscalDTO.class, "folioInstitucionVendedora");
        stream.omitField(MiscelaneaFiscalDTO.class, "diasPlazo");
        stream.omitField(MiscelaneaFiscalDTO.class, "tipoOperacion");
        stream.omitField(MiscelaneaFiscalDTO.class, "tipoValor");
        stream.omitField(MiscelaneaFiscalDTO.class, "emisora");
        stream.omitField(MiscelaneaFiscalDTO.class, "serie");
        stream.omitField(MiscelaneaFiscalDTO.class, "cupon");
        stream.omitField(MiscelaneaFiscalDTO.class, "fechaLiquidacion");
        stream.omitField(MiscelaneaFiscalDTO.class, "folioUsuario");
        stream.omitField(MiscelaneaFiscalDTO.class, "tasaReferencia");
        stream.omitField(MiscelaneaFiscalDTO.class, "cantidadTitulosOp");
        stream.omitField(MiscelaneaFiscalDTO.class, "cuentaComprador");
        stream.omitField(MiscelaneaFiscalDTO.class, "cuentaVendedora");
        stream.omitField(MiscelaneaFiscalDTO.class, "origenTransac");
        stream.omitField(MiscelaneaFiscalDTO.class, "importe");
        stream.omitField(MiscelaneaFiscalDTO.class, "tipoMensaje");
        stream.omitField(MiscelaneaFiscalDTO.class, "fechaConcertacion");
        stream.omitField(MiscelaneaFiscalDTO.class, "fechaHoraRegistro");
        stream.omitField(MiscelaneaFiscalDTO.class, "participanteVO");
        stream.omitField(MiscelaneaFiscalDTO.class, "FolioInstitucion");
        stream.omitField(MiscelaneaFiscalDTO.class, "IdInstitucion");
        stream.omitField(MiscelaneaFiscalDTO.class, "participanteVO");
        stream.omitField(MiscelaneaFiscalDTO.class, "funcionDelMensaje");
        stream.omitField(MiscelaneaFiscalDTO.class, "mensaje");
        stream.omitField(MiscelaneaFiscalDTO.class, "folioControl");
        stream.omitField(MiscelaneaFiscalDTO.class, "precioTitulo");
        stream.omitField(MiscelaneaFiscalDTO.class, "fechaHoraCierreOper");
    }


    private static final BigInteger ESTADO_REGISTRADO = new BigInteger("19");
    private static final BigInteger ESTADO_AUTORIZADO = new BigInteger("23");
//	private static final BigInteger ESTADO_LIBERADO =  new BigInteger("24");
//	private static final BigInteger ESTADO_ENVIADO =  new BigInteger("25");

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

    public static TraspasoEfectivoVO crearTraspasoEfectivoVOByRetiroEfectivoDTO(RetiroEfectivoDTO retiroDTO) {

        TraspasoEfectivoVO vo = new TraspasoEfectivoVO();

        vo.setBeneficiario(retiroDTO.getIdInstReceptor().getNombreCorto());
        vo.setCuentaBeneficiaria(retiroDTO.getCuentaBeneficiario());
        vo.setOrdenante(retiroDTO.getInstitucion().getNombreCorto());
        vo.setCuentaOrdenante(retiroDTO.getCuentaEmisor());

        vo.setFechaLiquidacion(retiroDTO.getFechaLiberacion());
        vo.setFechaRegistro(retiroDTO.getFechaCreacion());
        vo.setMonto(retiroDTO.getImporteTraspaso());
        vo.setTipoInstruccion(retiroDTO.getTipoInstruccion().getNombreCorto());
        vo.setReferenciaMensaje(retiroDTO.getReferenciaMensaje());
        vo.setReferenciaOperacion(retiroDTO.getReferenciaOperacion());
        vo.setDivisa(retiroDTO.getDivisa().getClaveAlfabetica());
        vo.setBoveda(retiroDTO.getBoveda().getNombreCorto());
        vo.setBovedaTraspasante(retiroDTO.getBoveda().getNombreCorto());

        return vo;
    }

    public static RetiroEfectivoVO creaRetiroEfectivoVOByRetiroEfectivoDTO(RetiroEfectivoDTO retiroDTO) {
        RetiroEfectivoVO reteVO = new RetiroEfectivoVO();

        reteVO.setBeneficiario(StringUtils.trimToNull(retiroDTO.getIdInstReceptor().getNombreCorto()));
        reteVO.setCuentaBeneficiaria(StringUtils.trimToNull(retiroDTO.getCuentaBeneficiario()));
        reteVO.setBoveda(null);
        reteVO.setBovedaTraspasante(retiroDTO.getBoveda().getNombreCorto());//MODIF
        reteVO.setCodigoCargos("OUR");
        reteVO.setConceptoPago(retiroDTO.getConceptoPago());


        reteVO.setCuentaIntermediaria(null);

        reteVO.setDivisa(retiroDTO.getDivisa().getClaveAlfabetica());
        reteVO.setFechaLiquidacion(retiroDTO.getFechaLiberacion());//MODIF
        reteVO.setFechaRegistro(null);
        reteVO.setFolioPaqueteMovimientos(null);
        reteVO.setIdFolioCtaReceptora(null);
        reteVO.setInstitucionReceptora(retiroDTO.getIdInstReceptor().getClaveTipoInstitucion() + retiroDTO.getIdInstReceptor().getFolioInstitucion());//MODIF
        reteVO.setIntermediario(null);
        reteVO.setMonto(retiroDTO.getImporteTraspaso());
        reteVO.setOrdenante(retiroDTO.getInstitucion().getNombreCorto());
        reteVO.setCuentaOrdenante(retiroDTO.getCuentaEmisor());
        reteVO.setReferenciaEmisorError(null);

//		reteVO.setReferenciaMensaje(retiroDTO.getReferenciaOperacion());
        reteVO.setReferenciaMensaje(retiroDTO.getReferenciaMensaje());
        reteVO.setReferenciaNumericaSPEI(retiroDTO.getReferencia());//MODIF
        reteVO.setReferenciaOperacion(retiroDTO.getReferenciaOperacion());

        reteVO.setReferenciaRelacionada(null);
        reteVO.setTipoInstruccion(null);
        reteVO.setTipoMensaje(null);
        reteVO.setTipoRetiro(null);
        return reteVO;
    }


    public static RetiroEfectivoVO creaRetiroEfectivoVOByRetiroEfectivoInternacionalDTO(RetiroEfectivoInternacionalDTO retiroInternacionalDTO) {
        RetiroEfectivoVO reteVO = new RetiroEfectivoVO();
        reteVO.setBeneficiario("RETE");
        reteVO.setBoveda(null);
        reteVO.setBovedaTraspasante(retiroInternacionalDTO.getBoveda().getNombreCorto());
        //----------------------------------
        reteVO.setCodigoCargos("OUR");
        reteVO.setConceptoPago(retiroInternacionalDTO.getConceptoPago());
        //FVU ESTE valor 2000 debe salir del catalogo correspondiente.
        //select * from c_cuenta_nombrada cn where id_institucion = X and id_tipo_cuenta = 16
        // x = retiroInternacionalDTO.getInstitucion().getId()
        // catalogosFacade
        reteVO.setCuentaBeneficiaria(retiroInternacionalDTO.getIdCuentaBeneficiario().toString());

        //-----------------------------------
        reteVO.setCuentaIntermediaria(null);
        reteVO.setCuentaOrdenante("2000");
        reteVO.setDivisa(retiroInternacionalDTO.getDivisa().getClaveAlfabetica());
        reteVO.setFechaLiquidacion(retiroInternacionalDTO.getFechaValor());
        reteVO.setFechaRegistro(null);
        reteVO.setFolioPaqueteMovimientos(null);
        reteVO.setIdFolioCtaReceptora(null);
        reteVO.setInstitucionReceptora("96005");
        reteVO.setIntermediario(null);
        reteVO.setMonto(retiroInternacionalDTO.getImporteTraspaso());
        reteVO.setOrdenante(retiroInternacionalDTO.getInstitucion().getNombreCorto());
//		pago203[] paago203 = new pago203[0];
//		reteVO.setPago203(paago203);
        reteVO.setReferenciaEmisorError(null);
        reteVO.setReferenciaMensaje(retiroInternacionalDTO.getReferenciaOperacion());
        reteVO.setReferenciaNumericaSPEI(retiroInternacionalDTO.getReferenciaOperacion());
        reteVO.setReferenciaOperacion(retiroInternacionalDTO.getReferenciaOperacion());
        reteVO.setReferenciaRelacionada(null);
        reteVO.setTipoInstruccion(null);
        reteVO.setTipoMensaje(null);
        reteVO.setTipoRetiro(null);
        return reteVO;
    }

    /**
     * Crea un objeto del tipo {@link TipoCuentaDTO} a partir de un objeto de
     * hibernate del tipo {@link TipoCuenta}
     *
     * @param tipoCuenta Objeto de donde se tomarón valores para crear el DTO de salida
     * @return DTO creado a partir del parámetro de entrada
     */
    public static TipoCuentaDTO crearTipoCuentaDTO(TipoCuenta tipoCuenta) {
        TipoCuentaDTO dto = null;
        if (tipoCuenta != null) {
            dto = new TipoCuentaDTO();
            dto.setDescripcion(
                    tipoCuenta.getNaturalezaProcesoLiquidacion().equals(TipoCuentaDTO.CUENTA_CONTROLADA) ? "CONTROLADA" : "NOMBRADA");
            dto.setId(tipoCuenta.getNaturalezaProcesoLiquidacion());
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

    public static TipoInstitucion crearTipoInstitucion(TipoInstitucionDTO tipoInstitucionDTO) {
        TipoInstitucion tipoInstitucion = null;
        if (tipoInstitucionDTO != null) {
            tipoInstitucion = new TipoInstitucion();
            tipoInstitucion.setDescripcion(tipoInstitucionDTO.getDescripcion());
            tipoInstitucion.setIdTipoInstitucion(new BigInteger(String.valueOf(tipoInstitucionDTO.getId())));
            tipoInstitucion.setClaveTipoInstitucion(tipoInstitucionDTO.getClaveTipoInstitucion());
        }
        return tipoInstitucion;
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
     * hibernate del tipo {@link CuentaNombrada}
     *
     * @param institucion Objeto de donde se tomarón valores para crear el DTO de salida
     * @return DTO creado a partir del parámetro de entrada
     */
    public static CuentaDTO crearCuentaDTO(Institucion institucion) {
        CuentaDTO dto = null;
        if (institucion != null) {

            dto = new CuentaDTO();
            // dto.setTipoTenencia(crearTipoTeneciaDTO(cuenta.getTipoCuenta()));
            // dto.setIdCuenta(cuenta.getIdCuentaNombrada().longValue());
            dto.setInstitucion(crearInstitucionDTO(institucion));
            dto.setNumeroCuenta(institucion.getTipoInstitucion()
                    .getClaveTipoInstitucion()
                    + institucion.getFolioInstitucion());
            dto.setDescripcion(dto.getNumeroCuenta());
            // dto.setNombreCuenta(cuenta.getNombreCuenta());
            // dto.setCuenta(cuenta.getCuenta());
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
     * Crea un objeto del tipo {@link CuentaEfectivoDTO} a partir de un objeto
     * de hibernate del tipo {@link CuentaNombrada}
     *
     * @param cuenta Objeto de donde se tomarón valores para crear el DTO de salida
     * @return DTO creado a partir del parámetro de entrada
     */
    public static CuentaEfectivoDTO crearCuentaEfectivoV2DTO(
            CuentaNombrada cuenta) {

        CuentaEfectivoDTO dto = null;
        if (cuenta != null) {
            dto = new CuentaEfectivoDTO();
            dto.setIdCuenta(cuenta.getIdCuentaNombrada().longValue());
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
            dto.setDescripcion(tipoCuenta.getNaturalezaContable().equals(TipoNaturalezaDTO.ACTIVO) ? "ACTIVO" : "PASIVO");
        }
        return dto;
    }

    /**
     * Crea un objeto del tipo {@link CuentaEfectivoDTO} a partir de un objeto
     * de hibernate del tipo {@link CuentaControlada}
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

    /**
     * Crea un objeto del tipo {@link InstitucionDTO} a partir de un objeto de
     * hibernate del tipo {@link Institucion}
     *
     * @param institucion Objeto de donde se tomarón valores para crear el DTO de salida
     * @return DTO creado a partir del parámetro de entrada
     */
    public static InstitucionDTO crearInstitucionDTO(Institucion institucion) {
        InstitucionDTO dto = new InstitucionDTO();
        try {
            if (institucion != null) {
                dto.setId(institucion.getIdInstitucion().longValue());
                dto.setNombreCorto(institucion.getNombreCorto());
                dto.setRazonSocial(institucion.getRazonSocial());
                if (institucion.getTipoInstitucion() != null) {
                    dto.setClaveTipoInstitucion(institucion.getTipoInstitucion().getClaveTipoInstitucion());
                    dto.setTipoInstitucionDTO(crearTipoInstitucionDTO(institucion.getTipoInstitucion()));
                } else {
                    dto.setClaveTipoInstitucion("");
                }
                dto.setFolioInstitucion(institucion.getFolioInstitucion());
                dto.setCuentaClabe(institucion.getCuentaClabe());
                dto.setClaveCasfim(institucion.getClaveCasfim());

                /*
                 * Se agregan campos para la resolución del requerimiento R-IND-2014-010
                 * 02/10/2014 Pablo Balderas.
                 */
                dto.setClaveSpei(institucion.getClaveSpei());
                dto.setOperaSiac(institucion.isOperaSiac());

                /**
                 * Se agregan campos para obtener la cuenta clabe y spei del beneficiario de la institucion
                 */

                dto.setCuentaClabeBeneficiario(institucion.getCuentaClabeBeneficiario());
                dto.setClaveSpeiBeneficiario(institucion.getClaveSpeiBeneficiario());
                dto.setOperaSiac(institucion.isOperaSiac());
            }
        } catch (Exception ex) {

        }
        return dto;
    }


    public static Institucion crearInstitucion(InstitucionDTO institucionDTO) {
        Institucion institucion = null;
        if (institucionDTO != null) {
            institucion = new Institucion();
            institucion.setIdInstitucion(new BigInteger(String.valueOf(institucionDTO.getId())));
            institucion.setNombreCorto(institucionDTO.getNombreCorto());
            institucion.setRazonSocial(institucionDTO.getRazonSocial());
            institucion.setTipoInstitucion(DTOAssembler.crearTipoInstitucion(institucionDTO.getTipoInstitucionDTO()));
            institucion.setFolioInstitucion(institucionDTO.getFolioInstitucion());
            institucion.setCuentaClabe(institucionDTO.getCuentaClabe());
            institucion.setClaveCasfim(institucionDTO.getClaveCasfim());
        }
        return institucion;
    }


    /**
     * Crea un objeto del tipo {@link BovedaDTO} a partir de un objeto de
     * hibernate del tipo {@link Boveda}
     *
     * @param bovedaDTO Objeto de donde se tomarón valores para crear el DTO de salida
     * @return DTO creado a partir del parámetro de entrada
     */
    public static Boveda crearBoveda(BovedaDTO bovedaDTO) {
        Boveda boveda = null;
        if (bovedaDTO != null) {
            boveda = new Boveda();
            //boveda.setCuentaNombrada(bovedaDTO.get)
            boveda.setIdBoveda(new BigInteger(String.valueOf(bovedaDTO.getId())));
            boveda.setDescripcion(bovedaDTO.getDescripcion());
            boveda.setNombreCorto(bovedaDTO.getNombreCorto());
            //boveda.setTipoBoveda(bovedaDTO.get)

        }
        return boveda;
    }

    public static BovedaDTO crearBovedaDTO(Boveda boveda) {
        BovedaDTO dto = null;
        if (boveda != null) {
            dto = new BovedaDTO();
            dto.setId(boveda.getIdBoveda().longValue());
            dto.setDescripcion(boveda.getDescripcion());
            dto.setNombreCorto(boveda.getNombreCorto());
        }
        return dto;
    }

    /**
     * Crea un objeto del tipo {@link DivisaDTO} a partir de un objeto de
     * hibernate del tipo {@link Divisa}
     *
     * @param divisa Objeto de hibernate con los valores a setear
     * @return DTO con los datos del objeto hibernate
     */
    public static DivisaDTO crearDivisaDTO(Divisa divisa) {
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

    public static EstadoInstruccionCatDTO crearEstadoInstruccionCatDTO(EstadoInstruccionCat estadoInstruccionCat) {
        EstadoInstruccionCatDTO estadoInstruccionCatDTO = null;
        if (estadoInstruccionCat != null) {
            estadoInstruccionCatDTO = new EstadoInstruccionCatDTO();
            estadoInstruccionCatDTO.setClaveEstadoInstruccion(estadoInstruccionCat.getClaveEstadoInstruccion());
            estadoInstruccionCatDTO.setDescripcion(estadoInstruccionCat.getDescripcion());
            estadoInstruccionCatDTO.setIdEstadoInstruccion(estadoInstruccionCat.getIdEstadoInstruccion());
        }
        return estadoInstruccionCatDTO;
    }


    public static Divisa crearDivisa(DivisaDTO divisaDTO) {
        Divisa divisa = null;
        if (divisaDTO != null) {
            divisa = new Divisa();

            divisa.setIdDivisa(new BigInteger(String.valueOf(divisaDTO.getId())));
            divisa.setClaveAlfabetica(divisaDTO.getClaveAlfabetica());
            divisa.setClaveNumerica(divisaDTO.getClaveNumerica());
            divisa.setDescripcion(divisaDTO.getDescripcion());
        }
        return divisa;
    }


    /**
     * Crea un objeto del tipo {@link PosicionDTO} a partir de un objeto de
     * hibernate del tipo {@link EstadoPosicionNombrada}
     *
     * @param posicion Objeto de hibernate fuete para construir el DTO
     * @return DTO creao a partir del parámetro de entrada
     */
    public static PosicionDTO crearPosicionNombradaDTO(
            EstadoPosicionNombrada posicion) {
        PosicionDTO dto = null;
        if (posicion != null) {
            dto = new PosicionDTO();
            dto.setIdPosicion(posicion.getPosicionNombrada().getIdPosicion()
                    .longValue());
            dto.setBoveda(crearBovedaDTO(posicion.getPosicionNombrada()
                    .getBoveda()));
            dto.setCuenta(crearCuentaDTO(posicion.getPosicionNombrada()
                    .getCuentaNombrada()));
            dto.setEmision(crearEmisionDTO(posicion.getPosicionNombrada()
                    .getCupon().getEmision()));
            dto.setPosicionDisponible(posicion.getPosicionFinalDisponible()
                    .toString());
            dto.setPosicionNoDisponible(posicion.getPosicionFinalNoDisponible()
                    .toString());
            dto.setPosicion(posicion.getPosicionFinalDisponible().add(
                    posicion.getPosicionFinalNoDisponible()).toString());
            dto.setValuacionNominal(new BigDecimal(String.valueOf(dto
                    .getEmision().getValorNominal())).multiply(new BigDecimal(
                    posicion.getPosicionFinalDisponible().add(
                            posicion.getPosicionFinalNoDisponible()))));
        }
        return dto;
    }

    /**
     * Crea un objeto del tipo {@link EmisionDTO} a partir de un objeto de
     * hibernate del tipo {@link EmisionPersistence}
     *
     * @param emision Objeto de hibernate fuete para construir el DTO
     * @return DTO creao a partir del parámetro de entrada
     */
    public static EmisionDTO crearEmisionDTO(Emision emision) {
        EmisionDTO dto = null;
        if (emision != null) {
            dto = new EmisionDTO();
            dto.setId(emision.getIdEmision().longValue());
            dto.setIsin(emision.getIsin());
            dto.setEmisora(crearEmisoraDTO(emision.getEmisora()));
            dto.setSerie(crearSerieDTO(emision.getSerie()));
            dto.setTipoValor(crearTipoValorDTO(emision.getInstrumento()));
            dto.setValorNominal(emision.getValorNominal().doubleValue());
            dto.setFechaVencimiento(emision.getFechaVencimiento());
            dto.setDivisa(crearDivisaDTO(emision.getDivisa()));
            dto.getBoveda().setId(emision.getBoveda().getIdBoveda().longValue());
            dto.getBoveda().setNombreCorto(emision.getBoveda().getNombreCorto());
        }
        return dto;
    }

    /**
     * Crea un objeto del tipo {@link SerieDTO} a partir de un objeto de
     * hibernate del tipo {@link String}
     *
     * @param serie Objeto de hibernate fuete para construir el DTO
     * @return DTO creao a partir del parámetro de entrada
     */
    public static SerieDTO crearSerieDTO(String serie) {
        SerieDTO dto = null;
        if (serie != null) {
            dto = new SerieDTO();
            dto.setDescripcion(serie);
            dto.setSerie(serie);
        }
        return dto;
    }

    /**
     * Crea un objeto del tipo {@link MercadoDTO} a partir de un objeto de
     * hibernate del tipo {@link Mercado}.
     *
     * @param mercado Objeto de hibernate que sirve de base para crear el DTO de
     *                salida
     * @return DTO creado a partir del parámetro de entrada, null si el
     * parámetro de entrada es también nulo.
     */
    public static MercadoDTO crearMercadoDTO(Mercado mercado) {
        MercadoDTO dto = null;
        if (mercado != null) {
            dto = new MercadoDTO();
            dto.setId(mercado.getIdMercado().longValue());
            dto.setDescripcion(mercado.getDescripcion());
            dto.setClaveMercado(mercado.getClave());
        }

        return dto;
    }

    /**
     * Crea un objeto del tipo {@link TipoValorDTO} a partir de un objeto de
     * Hibernate del tipo {@link Instrumento}.
     *
     * @param tipoValorBO Objeto de hibernate que sirve de base para crear el DTO de
     *                    salida.
     * @return DTO creado a partir del parámetro de entrada, null si el
     * parámetro de entrada es también nulo.
     */
    public static TipoValorDTO crearTipoValorDTO(Instrumento tipoValorBO) {
        TipoValorDTO tipoValorDTO = null;
        if (tipoValorBO != null) {
            tipoValorDTO = new TipoValorDTO();
            tipoValorDTO.setId(tipoValorBO.getIdInstrumento().longValue());
            tipoValorDTO.setDescripcion(tipoValorBO.getDescripcion());
            tipoValorDTO.setClaveTipoValor(tipoValorBO.getClaveTipoValor());
            tipoValorDTO.setMercado(crearMercadoDTO(tipoValorBO.getMercado()));
        }
        return tipoValorDTO;
    }

    /**
     * Crea un objeto del tipo {@link EmisoraDTO} a partir de un objeto de
     * Hibernate del tipo {@link Emisora}.
     *
     * @param emisora Objeto de hibernate que sirve de base para crear el DTO de
     *                salida.
     * @return DTO creado a partir del parámetro de entrada, null si el
     * parámetro de entrada es también nulo.
     */
    public static EmisoraDTO crearEmisoraDTO(Emisora emisora) {
        EmisoraDTO dto = null;

        if (emisora != null) {
            dto = new EmisoraDTO();
            dto.setId(emisora.getIdEmisora().longValue());
            dto.setDescripcion(emisora.getDescripcion());
        }

        return dto;
    }

    /**
     * Crea un objeto del tipo {@link PosicionDTO} a partir de un objeto de
     * hibernate del tipo {@link EstadoPosicionControlada}
     *
     * @param posicion Objeto de hibernate fuete para construir el DTO
     * @return DTO creao a partir del parámetro de entrada
     */
    public static PosicionDTO crearPosicionControladaDTO(
            EstadoPosicionControlada posicion) {
        PosicionDTO dto = null;
        if (posicion != null) {
            dto = new PosicionDTO();
            dto.setIdPosicion(posicion.getPosicionControlada().getIdPosicion()
                    .longValue());
            dto.setBoveda(crearBovedaDTO(posicion.getPosicionControlada()
                    .getBoveda()));
            dto.setCuenta(crearCuentaDTO(posicion.getPosicionControlada()
                    .getCuentaControlada()));
            dto.setEmision(crearEmisionDTO(posicion.getPosicionControlada()
                    .getEmision()));
            dto.setPosicionDisponible(posicion.getPosicionFinal().toString());
            dto.setPosicionNoDisponible(String.valueOf(0));
            dto.setPosicion(posicion.getPosicionFinal().toString());
            dto.setValuacionNominal(posicion.getPosicionControlada()
                    .getEmision().getValorNominal().multiply(
                            new BigDecimal(posicion.getPosicionFinal())));
        }
        return dto;
    }

    /**
     * Crea un objeto del tipo {@link PosicionDTO} a partir de un objeto de
     * hibernate del tipo {@link PosicionControlada}
     *
     * @param posicion Objeto de hibernate fuete para construir el DTO
     * @return DTO creao a partir del parámetro de entrada
     */
    public static PosicionDTO crearPosicionControladaDTO(
            PosicionControlada posicion) {
        PosicionDTO dto = null;
        if (posicion != null) {
            dto = new PosicionDTO();
            dto.setIdPosicion(posicion.getIdPosicion().longValue());
            dto.setBoveda(crearBovedaDTO(posicion.getBoveda()));
            dto.setCuenta(crearCuentaDTO(posicion.getCuentaControlada()));
            dto.setEmision(crearEmisionDTO(posicion.getEmision()));
            dto.getEmision().setCupon(posicion.getCupon().getClaveCupon());
            dto.setCupon(crearCuponDTO(posicion.getCupon()));
            dto.setPosicion(posicion.getPosicion().toString());
            dto.setPosicionNumerica(new BigDecimal(posicion.getPosicion()));
            dto.setPosicionDisponible(posicion.getPosicion().toString());
            dto.setPosicionDisponibleNumerica(new BigDecimal(posicion
                    .getPosicion()));
            dto.setPosicionNoDisponible("0");
            dto.setPosicionNoDisponibleNumerica(new BigDecimal("0"));
        }
        return dto;
    }

    /**
     * Crea un objeto del tipo {@link SaldoEfectivoDTO} a partir de un objeto de
     * hibernate del tipo {@link EstadoSaldoNombrada}
     *
     * @param estadoSaldoNombrada Objeto de hibernate fuete para construir el DTO
     * @return DTO creao a partir del parámetro de entrada
     */
    public static SaldoEfectivoDTO crearSaldoEfectivoDTO(
            EstadoSaldoNombrada estadoSaldoNombrada) {
        SaldoEfectivoDTO saldoEfectivoDTO = new SaldoEfectivoDTO();
        // Divisa en que esta dado el saldo
        saldoEfectivoDTO.setDivisa(crearDivisaDTO(estadoSaldoNombrada
                .getSaldoNombrada().getDivisa()));
        // Boveda en donde se resguarda el saldo */
        saldoEfectivoDTO.setBoveda(crearBovedaDTO(estadoSaldoNombrada
                .getSaldoNombrada().getBoveda()));
        // Cuenta del saldo */
        saldoEfectivoDTO.setCuenta(crearCuentaEfectivoDTO(estadoSaldoNombrada
                .getSaldoNombrada().getCuentaNombrada()));

        // Id del estado del saldo */
        saldoEfectivoDTO.setIdEstadoSaldo(estadoSaldoNombrada
                .getIdEstadoSaldoNombrada().longValue());
        // Saldo de la cuenta */
        saldoEfectivoDTO
                .setSaldo(estadoSaldoNombrada.getSaldoFinalDisponible()
                        .doubleValue()
                        + estadoSaldoNombrada.getSaldoFinalNoDisponible()
                        .doubleValue());
        // Saldo disponible. Aplica para cuentas nombradas */
        saldoEfectivoDTO.setSaldoDisponible(estadoSaldoNombrada
                .getSaldoFinalDisponible().doubleValue());
        // Saldo no disponible. Aplica para cuentas nombradas */
        saldoEfectivoDTO.setSaldoNoDisponible(estadoSaldoNombrada
                .getSaldoFinalNoDisponible().doubleValue());
        saldoEfectivoDTO.setIdSaldo(estadoSaldoNombrada.getSaldoNombrada()
                .getIdSaldo().longValue());
        return saldoEfectivoDTO;
    }

    /**
     * Crea un objeto del tipo {@link SaldoEfectivoDTO} a partir de un objeto de
     * hibernate del tipo {@link EstadoSaldoControlada}
     *
     * @param estadoSaldoControlada Objeto de hibernate fuete para construir el DTO
     * @return DTO creao a partir del parámetro de entrada
     */
    public static SaldoEfectivoDTO crearSaldoEfectivoDTO(
            EstadoSaldoControlada estadoSaldoControlada) {
        SaldoEfectivoDTO saldoEfectivoDTO = new SaldoEfectivoDTO();
        // Divisa en que esta dado el saldo
        saldoEfectivoDTO.setDivisa(crearDivisaDTO(estadoSaldoControlada
                .getSaldoControlada().getDivisa()));
        // Boveda en donde se resguarda el saldo */
        saldoEfectivoDTO.setBoveda(crearBovedaDTO(estadoSaldoControlada
                .getSaldoControlada().getBoveda()));
        // Cuenta del saldo */
        saldoEfectivoDTO.setCuenta(crearCuentaEfectivoDTO(estadoSaldoControlada
                .getSaldoControlada().getCuentaControlada()));

        // Id del estado del saldo */
        saldoEfectivoDTO.setIdEstadoSaldo(estadoSaldoControlada
                .getIdEstadoSaldoControlada().longValue());
        // Saldo de la cuenta */
        saldoEfectivoDTO.setSaldo(estadoSaldoControlada.getSaldoFinal()
                .doubleValue());
        saldoEfectivoDTO.setIdSaldo(estadoSaldoControlada.getSaldoControlada()
                .getIdSaldo().longValue());
        return saldoEfectivoDTO;
    }

    /**
     * Crea un objeto del tipo {@link RegistroContablePosicionNombradaDTO} a
     * partir de un objeto de hibernate del tipo {@link RegContValNombrada}
     *
     * @param registro Objeto de hibernate que sirve de base para crear el DTO de
     *                 salida.
     * @return DTO creado a partir del parámetro de entrada, null si el
     * parámetro de entrada es también nulo.
     */
    public static RegistroContablePosicionNombradaDTO crearRegistroContablePosicionNombrada(
            RegContValNombrada registro) {
        RegistroContablePosicionNombradaDTO dtoRegistro = null;

        if (registro != null) {

            dtoRegistro = new RegistroContablePosicionNombradaDTO();

            dtoRegistro.setIdRegistroContable(registro.getIdRegistroContable()
                    .longValue());
            dtoRegistro.setPosicion(crearPosicionNombradaDTO(registro
                    .getPosicion()));
            dtoRegistro
                    .setTipoAccion(crearTipoAccion(registro.getTipoAccion()));
            dtoRegistro.setCantidad(registro.getCantidad());
            dtoRegistro.setFecha(registro.getFecha());
            dtoRegistro.setCargoA(registro.getCargoA() != null ? registro.getCargoA().longValue() : null);
            dtoRegistro.setTipoOperacion(crearTipoOperacion(registro
                    .getOperacion().getTipoOperacion()));
            dtoRegistro.setFolio(registro.getOperacion().getInstruccion()
                    .getFolioInstruccion().longValue());
        }
        return dtoRegistro;
    }

    /**
     * Crea un objeto del tipo {@link RegistroContablePosicionNombradaDTO} a
     * partir de un objeto de hibernate del tipo {@link RegContValNombrada}
     *
     * @param registro Objeto de hibernate que sirve de base para crear el DTO de
     *                 salida.
     * @return DTO creado a partir del parámetro de entrada, null si el
     * parámetro de entrada es también nulo.
     */
    public static RegistroContablePosicionNombradaDTO crearRegistroContablePosicionNombradaConDatosOperacion(
            RegContValNombrada registro) {
        RegistroContablePosicionNombradaDTO dtoRegistro = null;

        if (registro != null) {

            dtoRegistro = new RegistroContablePosicionNombradaDTO();

            dtoRegistro.setIdRegistroContable(registro.getIdRegistroContable()
                    .longValue());
            dtoRegistro.setPosicion(crearPosicionNombradaDTO(registro
                    .getPosicion()));
            dtoRegistro
                    .setTipoAccion(crearTipoAccion(registro.getTipoAccion()));
            dtoRegistro.setCantidad(registro.getCantidad());
            dtoRegistro.setFecha(registro.getFecha());
            dtoRegistro.setCargoA(registro.getCargoA() != null ? registro.getCargoA().longValue() : null);
            dtoRegistro.setOperacion(crearOperacion(registro.getOperacion()));
            dtoRegistro.setFolio(registro.getOperacion().getInstruccion()
                    .getFolioInstruccion().longValue());
        }
        return dtoRegistro;
    }

    /**
     * Crea un objeto de tipo {@link OperacionSaldoDTO} a partir de un objeto de
     * hibernate del tipo {@link OperacionNombrada}.
     *
     * @param operacion Objeto de hibernate fuente para construir el DTO
     * @return DTO creao a partir del parámetro de entrada.
     */
    public static OperacionSaldoDTO crearOperacionSaldoDTO(
            OperacionNombrada operacion) {
        OperacionSaldoDTO operacionDTO = null;
        if (operacion != null) {
            operacionDTO = new OperacionSaldoDTO();
            // La cuenta del traspasante */
            operacionDTO.setCuentaTraspasante(crearCuentaEfectivoDTO(operacion
                    .getCuentaNombradaTraspasante()));
            // Representa la cuenta del receptor */
            operacionDTO.setCuentaReceptora(crearCuentaEfectivoDTO(operacion
                    .getCuentaNombradaReceptor()));
            // Representa la divisa en que se efectu la operación
            operacionDTO.setDivisa(crearDivisaDTO(operacion.getDivisa()));
            // Indicador de a que secci&oacute;n del saldo se cargan y abonan
            // los movimientos de efectivo. Si afecta al disponible es 1. Si
            // afecta al no disponible es 2
            if (operacion.getCargoEfectivoA() != null) {
                operacionDTO.setCargoEfectivoA(operacion.getCargoEfectivoA().longValue());
            }
            // El identificador único de la operación
            operacionDTO.setIdOperacion(operacion.getIdOperacion().longValue());
            // El folio de la operación dentro de la instrucci&oacute;n
            // liquidaci&oacute;n
            operacionDTO.setFolio(operacion.getFolioOperacion().longValue());
            // El tipo de operación de la operación
            operacionDTO.setTipoOperacion(crearTipoOperacion(operacion
                    .getTipoOperacion()));
            // Instrucci&oacute;n de liquidaci&oacute;n a la que pertenece la
            // operaci&oacute;n.
            operacionDTO
                    .setInstruccionLiquidacion(crearInstruccionLiquidacion(operacion
                            .getInstruccion()));
            // Cuenta de efectivo del traspasante
            operacionDTO
                    .setCuentaEfectivoTraspasante(crearCuentaEfectivoDTO(operacion
                            .getCuentaEfectivoTraspasante()));
            // Cuenta de efectivo del receptor
            operacionDTO
                    .setCuentaEfectivoReceptor(crearCuentaEfectivoDTO(operacion
                            .getCuentaEfectivoReceptor()));
        }
        return operacionDTO;
    }

    /**
     * Crea un objeto de tipo {@link OperacionSaldoDTO} a partir de un objeto de
     * hibernate del tipo {@link OperacionNombrada}.
     *
     * @param operacion Objeto de hibernate fuente para construir el DTO
     * @return DTO creao a partir del parámetro de entrada.
     */
    public static OperacionSaldoDTO crearOperacionSaldoDTO(
            OperacionNombradaHistorico operacion) {
        OperacionSaldoDTO operacionDTO = null;
        if (operacion != null) {
            operacionDTO = new OperacionSaldoDTO();
            // La cuenta del traspasante */
            operacionDTO.setCuentaTraspasante(crearCuentaEfectivoDTO(operacion
                    .getCuentaNombradaTraspasante()));
            // Representa la cuenta del receptor */
            operacionDTO.setCuentaReceptora(crearCuentaEfectivoDTO(operacion
                    .getCuentaNombradaReceptor()));
            // Representa la divisa en que se efectu la operación
            operacionDTO.setDivisa(crearDivisaDTO(operacion.getDivisa()));
            // Indicador de a que secci&oacute;n del saldo se cargan y abonan
            // los movimientos de efectivo. Si afecta al disponible es 1. Si
            // afecta
            // al no disponible es 2
            operacionDTO.setCargoEfectivoA(operacion.getCargoEfectivoA()
                    .longValue());
            // El identificador único de la operación
            operacionDTO.setIdOperacion(operacion.getIdOperacion().longValue());
            // El folio de la operación dentro de la instrucci&oacute;n
            // liquidaci&oacute;n
            operacionDTO.setFolio(operacion.getFolioOperacion().longValue());
            // El tipo de operación de la operación
            operacionDTO.setTipoOperacion(crearTipoOperacion(operacion
                    .getTipoOperacion()));
            // Instrucci&oacute;n de liquidaci&oacute;n a la que pertenece la
            // operaci&oacute;n.
            operacionDTO
                    .setInstruccionLiquidacion(crearInstruccionLiquidacionHistorico(operacion
                            .getInstruccion()));
            // Cuenta de efectivo del traspasante
            operacionDTO
                    .setCuentaEfectivoTraspasante(crearCuentaEfectivoDTO(operacion
                            .getCuentaEfectivoTraspasante()));
            // Cuenta de efectivo del receptor
            operacionDTO
                    .setCuentaEfectivoReceptor(crearCuentaEfectivoDTO(operacion
                            .getCuentaEfectivoReceptor()));
        }
        return operacionDTO;
    }

    /**
     * Crea un objeto del tipo {@link TipoOperacionDTO} a partir de un objeto de
     * hibernate del tipo {@link TipoOperacion}
     *
     * @param tipoOperacion Objeto de hibernate fuente para construir el DTO
     * @return DTO creao a partir del parámetro de entrada
     */
    public static TipoOperacionDTO crearTipoOperacion(
            TipoOperacion tipoOperacion) {

        TipoOperacionDTO tipoOperacionDTO = null;

        if (tipoOperacion != null) {
            tipoOperacionDTO = new TipoOperacionDTO();
            tipoOperacionDTO.setDescripcion(tipoOperacion.getDescripcion());
            tipoOperacionDTO.setId(tipoOperacion.getIdTipoOperacion()
                    .longValue());
            tipoOperacionDTO.setClaveTipoOperacion(tipoOperacion
                    .getClaveTipoOperacion());
        }
        return tipoOperacionDTO;
    }

    /**
     * Crea un objeto de tipo {@link TipoAccionDTO} a partir de un objeto de
     * hibernate del tipo {@link TipoAccion}
     *
     * @param tipoAccion Objeto de hibernate fuente para construir el DTO
     * @return DTO creao a partir del parámetro de entrada
     */
    public static TipoAccionDTO crearTipoAccion(TipoAccion tipoAccion) {

        TipoAccionDTO tipoAccionDTO = null;

        if (tipoAccion != null) {
            tipoAccionDTO = new TipoAccionDTO();
            tipoAccionDTO.setDescripcion(tipoAccion.getName());
            tipoAccionDTO.setId(tipoAccion.getValue());
        }
        return tipoAccionDTO;
    }

    /**
     * Crea un objeto del tipo {@link PosicionDTO} a partir de un objeto de
     * hibernate del tipo {@link PosicionNombrada}
     *
     * @param posicion Objeto de hibernate fuente para construir el DTO
     * @return DTO creao a partir del parámetro de entrada
     */
    public static PosicionDTO crearPosicionNombradaDTO(PosicionNombrada posicion) {
        PosicionDTO dto = null;
        if (posicion != null) {
            dto = new PosicionDTO();
            dto.setIdPosicion(posicion.getIdPosicion().longValue());
            dto.setBoveda(crearBovedaDTO(posicion.getBoveda()));
            dto.setCuenta(crearCuentaDTO(posicion.getCuentaNombrada()));
            dto.setEmision(crearEmisionDTO(posicion.getCupon().getEmision()));
            dto.getEmision().setCupon(posicion.getCupon().getClaveCupon());
            dto.setCupon(crearCuponDTO(posicion.getCupon()));
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
     * Crea un objeto del tipo {@link PosicionDTO} a partir de un objeto de
     * hibernate del tipo {@link PosicionNombrada}
     *
     * @param posicion Objeto de hibernate fuente para construir el DTO
     * @return DTO creao a partir del parámetro de entrada
     */
    public static PosicionDTO crearPosicionNombradaV2DTO(
            PosicionNombrada posicion) {
        PosicionDTO dto = null;
        if (posicion != null) {
            dto = new PosicionDTO();
            dto.setIdPosicion(posicion.getIdPosicion().longValue());
            dto.setBoveda(crearBovedaDTO(posicion.getBoveda()));
            dto.setEmision(crearEmisionDTO(posicion.getCupon().getEmision()));
            dto.getEmision().setCupon(posicion.getCupon().getClaveCupon());
            dto.setCupon(crearCuponDTO(posicion.getCupon()));

            dto.setPosicionDisponible(posicion.getPosicionDisponible()
                    .toString());
            dto.setPosicionDisponibleNumerica(new BigDecimal(posicion
                    .getPosicionDisponible()));
            dto.setPosicionNoDisponible(posicion.getPosicionNoDisponible()
                    .toString());
            dto.setPosicionNoDisponibleNumerica(new BigDecimal(posicion
                    .getPosicionNoDisponible()));
            dto.setPosicion(posicion.getPosicionDisponible().add(
                    posicion.getPosicionNoDisponible()).toString());
            dto.setPosicionNumerica(new BigDecimal(posicion
                    .getPosicionDisponible().add(
                            posicion.getPosicionNoDisponible())));

            dto.setValuacionNominal(new BigDecimal(String.valueOf(dto
                    .getEmision().getValorNominal())).multiply(new BigDecimal(
                    posicion.getPosicionDisponible().add(
                            posicion.getPosicionNoDisponible()))));
        }
        return dto;
    }

    /**
     * Crea un objeto del tipo {@link PosicionDTO} a partir de un objeto de
     * hibernate del tipo {@link PosicionNombrada}
     *
     * @param posicion Objeto de hibernate fuente para construir el DTO
     * @return DTO creao a partir del parámetro de entrada
     */
    public static PosicionDTO crearPosicionNombradaHistoricoV2DTO(
            PosicionNombradaHistorico posicion) {
        PosicionDTO dto = null;
        if (posicion != null) {
            dto = new PosicionDTO();
            dto.setIdPosicion(posicion.getIdPosicion().longValue());
            dto.setBoveda(crearBovedaDTO(posicion.getBoveda()));
            dto.setEmision(crearEmisionDTO(posicion.getCupon().getEmision()));
            dto.getEmision().setCupon(posicion.getCupon().getClaveCupon());
            dto.setCupon(crearCuponDTO(posicion.getCupon()));

            dto.setPosicionDisponible(posicion.getPosicionDisponible()
                    .toString());
            dto.setPosicionDisponibleNumerica(new BigDecimal(posicion
                    .getPosicionDisponible()));
            dto.setPosicionNoDisponible(posicion.getPosicionNoDisponible()
                    .toString());
            dto.setPosicionNoDisponibleNumerica(new BigDecimal(posicion
                    .getPosicionNoDisponible()));
            dto.setPosicion(posicion.getPosicionDisponible().add(
                    posicion.getPosicionNoDisponible()).toString());
            dto.setPosicionNumerica(new BigDecimal(posicion
                    .getPosicionDisponible().add(
                            posicion.getPosicionNoDisponible())));

            dto.setValuacionNominal(new BigDecimal(String.valueOf(dto
                    .getEmision().getValorNominal())).multiply(new BigDecimal(
                    posicion.getPosicionDisponible().add(
                            posicion.getPosicionNoDisponible()))));
        }
        return dto;
    }

    /**
     * Crea un objeto del tipo {@link RegistroContableSaldoNombradaDTO} a partir
     * de un objeto de hibernate del tipo {@link RegContEfecNombrada} e
     * {@link String}
     *
     * @param registro    Objeto de hibernate que sirve de base para crear el DTO de
     *                    salida.
     * @param instruccion Objeto de hibernate que sirve de base para crear el DTO de
     *                    salida.
     * @return DTO creado a partir del parámetro de entrada, null si el
     * parámetro de entrada es también nulo.
     */
    public static RegistroContableSaldoNombradaDTO crearRegistroContableSaldoNombradaConDatosOperacionEInstruccion(
            RegContEfecNombrada registro, String instruccion, Integer idTipoPago) {
        RegistroContableSaldoNombradaDTO dtoRegistro = null;

        if (registro != null) {

            dtoRegistro = new RegistroContableSaldoNombradaDTO();

            dtoRegistro.setIdRegistroContable(registro.getIdRegistroContable()
                    .longValue());
            dtoRegistro.setSaldoEfectivo(crearSaldoEfectivoV2DTO(registro
                    .getSaldoNombrada()));
            dtoRegistro
                    .setTipoAccion(crearTipoAccion(registro.getTipoAccion()));
            dtoRegistro.setCantidad(registro.getImporte());
            dtoRegistro.setFechaLiquidacion(registro.getFecha());

            dtoRegistro.setCargoA(registro.getCargoA() != null ? registro.getCargoA().longValue() : null);
            dtoRegistro.setOperacion(crearOperacionEfectivo(registro
                    .getOperacion(), idTipoPago));
            dtoRegistro.setIdTipoTipoInstruccion(registro.getOperacion()
                    .getInstruccion().getIdTipoInstruccion().longValue());
            dtoRegistro.setFolioInstruccion(registro.getOperacion()
                    .getInstruccion().getFolioInstruccion().longValue());

            if (dtoRegistro.getOperacion() != null
                    && dtoRegistro.getOperacion().getInstruccionLiquidacion() != null) {
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

                    dtoRegistro.getOperacion().getInstruccionLiquidacion()
                            .setPlazoReporto(plazo != null ? plazo : 0L);
                    dtoRegistro.getOperacion().getInstruccionLiquidacion()
                            .setTasaNegociada(tasa != null ? tasa : 0.0);
                }
            }
        }
        return dtoRegistro;
    }

    /**
     * Crea un objeto del tipo {@link RegistroContableSaldoNombradaDTO} a partir
     * de un objeto de hibernate del tipo {@link RegContEfecNombrada} e
     * {@link String}
     *
     * @param registro    Objeto de hibernate que sirve de base para crear el DTO de
     *                    salida.
     * @param instruccion Objeto de hibernate que sirve de base para crear el DTO de
     *                    salida.
     * @return DTO creado a partir del parámetro de entrada, null si el
     * parámetro de entrada es también nulo.
     */
    public static RegistroContableSaldoNombradaDTO crearRegistroContableSaldoNombradaConDatosOperacionEInstruccion(
            RegContEfecNombrada registro, String instruccion,
            TipoRetiro tipoRetiro, Integer idTipoPago) {
        RegistroContableSaldoNombradaDTO dtoRegistro = null;

        if (registro != null) {

            dtoRegistro = new RegistroContableSaldoNombradaDTO();

            dtoRegistro.setIdRegistroContable(registro.getIdRegistroContable()
                    .longValue());
            dtoRegistro.setSaldoEfectivo(crearSaldoEfectivoV2DTO(registro
                    .getSaldoNombrada()));
            dtoRegistro
                    .setTipoAccion(crearTipoAccion(registro.getTipoAccion()));
            dtoRegistro.setCantidad(registro.getImporte());
            dtoRegistro.setFechaLiquidacion(registro.getFecha());
            dtoRegistro.setCargoA(registro.getCargoA() != null ? registro.getCargoA().longValue() : null);
            dtoRegistro.setOperacion(crearOperacionEfectivo(registro
                    .getOperacion(), 0));
            dtoRegistro.setIdTipoTipoInstruccion(registro.getOperacion()
                    .getInstruccion().getIdTipoInstruccion().longValue());
            dtoRegistro.setFolioInstruccion(registro.getOperacion()
                    .getInstruccion().getFolioInstruccion().longValue());

            if (dtoRegistro.getOperacion() != null
                    && dtoRegistro.getOperacion().getInstruccionLiquidacion() != null) {
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

                    dtoRegistro.getOperacion().getInstruccionLiquidacion()
                            .setPlazoReporto(plazo != null ? plazo : 0L);
                    dtoRegistro.getOperacion().getInstruccionLiquidacion()
                            .setTasaNegociada(tasa != null ? tasa : 0.0);
                }
            }

            if (tipoRetiro != null && StringUtils.isNotBlank(tipoRetiro.getDescripcion())
                    && dtoRegistro.getOperacion() != null
                    && dtoRegistro.getOperacion().getInstruccionLiquidacion() != null) {
                dtoRegistro.getOperacion().getInstruccionLiquidacion()
                        .setInstruccionEfectivo(new InstruccionEfectivoDTO());
                dtoRegistro.getOperacion().getInstruccionLiquidacion()
                        .getInstruccionEfectivo().setTipoRetiro(tipoRetiro.getDescripcion());
                dtoRegistro.getOperacion().getInstruccionLiquidacion()
                        .getInstruccionEfectivo().setDescripcionTipoRetiro(
                                tipoRetiro.getDescripcion());
            }

            if (idTipoPago != null
                    && dtoRegistro.getOperacion() != null
                    && dtoRegistro.getOperacion().getInstruccionLiquidacion() != null) {
                dtoRegistro.getOperacion().getInstruccionLiquidacion()
                        .setInstruccionEfectivo(new InstruccionEfectivoDTO());
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
                dtoRegistro.getOperacion().getInstruccionLiquidacion()
                        .getInstruccionEfectivo().setTipoRetiro(retiro);
                dtoRegistro.getOperacion().getInstruccionLiquidacion()
                        .getInstruccionEfectivo().setDescripcionTipoRetiro(
                                retiro);
            }
        }
        return dtoRegistro;
    }

    /**
     * Crea un objeto del tipo {@link RegistroContableSaldoNombradaDTO} a partir
     * de un objeto de hibernate del tipo {@link RegContEfecNombradaHistorico} e
     * {@link String}
     *
     * @param registro    Objeto de hibernate que sirve de base para crear el DTO de
     *                    salida.
     * @param instruccion Objeto de hibernate que sirve de base para crear el DTO de
     *                    salida.
     * @return DTO creado a partir del parámetro de entrada, null si el
     * parámetro de entrada es también nulo.
     */
    public static RegistroContableSaldoNombradaDTO crearRegistroContableSaldoNombradaConDatosOperacionEInstruccionHistorico(
            RegContEfecNombradaHistorico registro, String instruccion,
            Integer idTipoPago) {
        RegistroContableSaldoNombradaDTO dtoRegistro = null;
        if (registro != null) {
            dtoRegistro = new RegistroContableSaldoNombradaDTO();

            dtoRegistro.setHistorico(true);
            dtoRegistro.setIdRegistroContable(registro.getIdRegistroContable().longValue());
            dtoRegistro.setSaldoEfectivo(crearSaldoEfectivoDTO(registro.getSaldoNombradaHistorico()));
            dtoRegistro.setTipoAccion(crearTipoAccion(registro.getTipoAccion()));
            dtoRegistro.setCantidad(registro.getImporte());
            dtoRegistro.setFechaLiquidacion(registro.getFecha());
            dtoRegistro.setCargoA(registro.getCargoA() != null ? registro.getCargoA().longValue() : null);
            dtoRegistro.setOperacion(crearOperacionEfectivoHistorico(registro.getOperacion(), idTipoPago));
            dtoRegistro.setIdTipoTipoInstruccion(registro.getOperacion().getInstruccion().getIdTipoInstruccion().longValue());
            dtoRegistro.setFolioInstruccion(registro.getOperacion().getInstruccion().getFolioInstruccion().longValue());

            if (dtoRegistro.getOperacion() != null
                    && dtoRegistro.getOperacion().getInstruccionLiquidacion() != null) {
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

                    dtoRegistro.getOperacion().getInstruccionLiquidacion()
                            .setPlazoReporto(plazo != null ? plazo : 0L);
                    dtoRegistro.getOperacion().getInstruccionLiquidacion()
                            .setTasaNegociada(tasa != null ? tasa : 0.0);
                }
            }
        }
        return dtoRegistro;
    }

    /**
     * Crea un objeto del tipo {@link RegistroContableSaldoNombradaDTO} a partir
     * de un objeto de hibernate del tipo {@link RegContEfecNombradaHistorico} e
     * {@link String}
     *
     * @param registro    Objeto de hibernate que sirve de base para crear el DTO de
     *                    salida.
     * @param instruccion Objeto de hibernate que sirve de base para crear el DTO de
     *                    salida.
     * @return DTO creado a partir del parámetro de entrada, null si el
     * parámetro de entrada es también nulo.
     */
    public static RegistroContableSaldoNombradaDTO crearRegistroContableSaldoNombradaConDatosOperacionEInstruccionHistorico(
            RegContEfecNombradaHistorico registro, String instruccion,
            TipoRetiro tipoRetiro, Integer idTipoPago, Date fechaActual) {
        RegistroContableSaldoNombradaDTO dtoRegistro = null;

        if (registro != null) {
            dtoRegistro = new RegistroContableSaldoNombradaDTO();
            dtoRegistro.setHistorico(true);
            dtoRegistro.setIdRegistroContable(registro.getIdRegistroContable().longValue());
            dtoRegistro.setSaldoEfectivo(crearSaldoEfectivoDTO(registro.getSaldoNombradaHistorico()));
            dtoRegistro.setTipoAccion(crearTipoAccion(registro.getTipoAccion()));
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

    /**
     * Crea un objeto del tipo {@link RegistroContableSaldoNombradaDTO} a partir
     * de un objeto de hibernate del tipo {@link RegContEfecNombrada}
     *
     * @param registro Objeto de hibernate que sirve de base para crear el DTO de
     *                 salida.
     * @return DTO creado a partir del parámetro de entrada, null si el
     * parámetro de entrada es también nulo.
     */
    public static RegistroContableSaldoNombradaDTO crearRegistroContableSaldoNombradaConDatosOperacion(
            RegContEfecNombrada registro) {
        RegistroContableSaldoNombradaDTO dtoRegistro = null;

        if (registro != null) {

            dtoRegistro = new RegistroContableSaldoNombradaDTO();

            dtoRegistro.setIdRegistroContable(registro.getIdRegistroContable()
                    .longValue());
            dtoRegistro.setSaldoEfectivo(crearSaldoEfectivoDTO(registro
                    .getSaldoNombrada()));
            dtoRegistro
                    .setTipoAccion(crearTipoAccion(registro.getTipoAccion()));
            dtoRegistro.setCantidad(registro.getImporte());
            dtoRegistro.setFechaLiquidacion(registro.getFecha());
            dtoRegistro.setCargoA(registro.getCargoA() != null ? registro.getCargoA().longValue() : null);
            dtoRegistro.setOperacion(crearOperacionEfectivo(registro
                    .getOperacion()));
            dtoRegistro.setIdTipoTipoInstruccion(registro.getOperacion()
                    .getInstruccion().getIdTipoInstruccion().longValue());
            dtoRegistro.setFolioInstruccion(registro.getOperacion()
                    .getInstruccion().getFolioInstruccion().longValue());
        }
        return dtoRegistro;
    }

    /**
     * Crea un objeto del tipo {@link RegistroContableSaldoNombradaDTO} a partir
     * de un objeto de hibernate del tipo {@link RegContEfecNombradaHistorico}
     *
     * @param registro Objeto de hibernate que sirve de base para crear el DTO de
     *                 salida.
     * @return DTO creado a partir del parámetro de entrada, null si el
     * parámetro de entrada es también nulo.
     */
    public static RegistroContableSaldoNombradaDTO
    crearRegistroContableSaldoNombradaConDatosOperacion(RegContEfecNombradaHistorico registro) {
        RegistroContableSaldoNombradaDTO dtoRegistro = null;
        if (registro != null) {
            dtoRegistro = new RegistroContableSaldoNombradaDTO();
            dtoRegistro.setHistorico(true);
            dtoRegistro.setIdRegistroContable(registro.getIdRegistroContable().longValue());
            dtoRegistro.setSaldoEfectivo(crearSaldoEfectivoDTO(registro.getSaldoNombradaHistorico()));
            dtoRegistro.setTipoAccion(crearTipoAccion(registro.getTipoAccion()));
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
     * Crea un objeto del tipo
     * {@link RegistroContableSaldoNombradaDTO} a partir de un objeto
     * de hibernate del tipo {@link RegContEfecNombrada}
     *
     * @param registroContable Objeto de hibernate para construir el DTO
     * @return DTO creado a partir del parámetro de entrada
     */
    public static RegistroContableSaldoNombradaDTO crearRegistroContableSaldoNombradaDTO(
            RegContEfecNombrada registroContable) {
        RegistroContableSaldoNombradaDTO dto = null;
        if (registroContable != null) {
            dto = new RegistroContableSaldoNombradaDTO();
            // Id del registro contable
            dto.setIdRegistroContable(registroContable.getIdRegistroContable()
                    .longValue());
            // Fecha en la que se afecto el saldo
            dto.setFechaLiquidacion(registroContable.getFecha());
            // Saldo que se ve afectado por el registro contable
            dto.setSaldoEfectivo(crearSaldoEfectivoDTO(registroContable
                    .getSaldoNombrada()));
            // Tipo de acción aplicada al saldo
            dto
                    .setTipoAccion(crearTipoAccion(registroContable
                            .getTipoAccion()));
            dto.setIdTipoTipoInstruccion(registroContable.getOperacion()
                    .getInstruccion().getIdTipoInstruccion().longValue());
            dto.setCantidad(registroContable.getImporte());
            dto.setOperacion(DTOAssembler.crearOperacion(registroContable
                    .getOperacion()));
            dto.setFolioInstruccion(registroContable.getOperacion()
                    .getInstruccion().getFolioInstruccion().longValue());
        }
        return dto;
    }

    /**
     * Crea un objeto del tipo
     * {@link RegistroContableSaldoNombradaDTO} a partir de un objeto
     * de hibernate del tipo {@link RegContEfecNombradaHistorico}
     *
     * @param registroContable Objeto de hibernate para construir el DTO
     * @return DTO creado a partir del parámetro de entrada
     */
    public static RegistroContableSaldoNombradaDTO crearRegistroContableSaldoNombradaDTO(RegContEfecNombradaHistorico registroContable) {
        RegistroContableSaldoNombradaDTO dto = null;
        if (registroContable != null) {
            dto = new RegistroContableSaldoNombradaDTO();
            dto.setHistorico(true);
            // Id del registro contable
            dto.setIdRegistroContable(registroContable.getIdRegistroContable().longValue());
            // Fecha en la que se afecto el saldo
            dto.setFechaLiquidacion(registroContable.getFecha());
            // Saldo que se ve afectado por el registro contable
            dto.setSaldoEfectivo(crearSaldoEfectivoDTO(registroContable.getSaldoNombradaHistorico()));
            // Tipo de acción aplicada al saldo
            dto.setTipoAccion(crearTipoAccion(registroContable.getTipoAccion()));
            dto.setIdTipoTipoInstruccion(registroContable.getOperacion().getInstruccion().getIdTipoInstruccion().longValue());
            dto.setCantidad(registroContable.getImporte());
            dto.setOperacion(DTOAssembler.crearOperacionHistorico(registroContable.getOperacion()));
            dto.setFolioInstruccion(registroContable.getOperacion().getInstruccion().getFolioInstruccion().longValue());
        }
        return dto;
    }

    /**
     * Crea un objeto del tipo {@link CuponDTO} a partir de un objeto de
     * hibernate del tipo {@link Cupon}
     *
     * @param cupon Objeto de hibernate para construir el DTO
     * @return DTO creado a partir del parámetro de entrada
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
     * Crea un objeto del tipo {@link EstadoPosicionDTO} a partir de un objeto
     * de hibernate del tipo {@link EstadoPosicionNombrada}
     *
     * @param estadoPosicionNombrada Objeto de hibernate para construir el DTO
     * @return DTO creado a partir del parámetro de entrada
     */
    public static EstadoPosicionDTO crearEstadoPosicionDTO(
            EstadoPosicionNombrada estadoPosicionNombrada) {
        EstadoPosicionDTO estadoPosicionDTO = null;

        if (estadoPosicionNombrada != null) {

            estadoPosicionDTO = new EstadoPosicionDTO();
            estadoPosicionDTO.setIdEstadoPosicion(estadoPosicionNombrada
                    .getIdEstadoPosicionNombrada().longValue());
            estadoPosicionDTO.setPosicionFinalDisponible(estadoPosicionNombrada
                    .getPosicionFinalDisponible());
            estadoPosicionDTO
                    .setPosicionFinalNoDisponible(estadoPosicionNombrada
                            .getPosicionFinalNoDisponible());
            estadoPosicionDTO
                    .setPosicionInicialDisponible(estadoPosicionNombrada
                            .getPosicionInicialDisponible());
            estadoPosicionDTO
                    .setPosicionInicialNoDisponible(estadoPosicionNombrada
                            .getPosicionInicialNoDisponible());
            estadoPosicionDTO.setPosicionFinal(new BigInteger(
                    String
                            .valueOf(estadoPosicionNombrada
                                    .getPosicionFinalDisponible().longValue()
                                    + estadoPosicionNombrada
                                    .getPosicionFinalNoDisponible()
                                    .longValue()), 10));
            estadoPosicionDTO.setPosicionInicial(new BigInteger(String
                    .valueOf(estadoPosicionNombrada
                            .getPosicionInicialDisponible().longValue()
                            + estadoPosicionNombrada
                            .getPosicionInicialNoDisponible()
                            .longValue()), 10));
        }

        return estadoPosicionDTO;
    }

    /**
     * Crea un objeto del tipo {@link EstadoPosicionDTO} a partir de un objeto
     * de hibernate del tipo {@link EstadoPosicionControlada}
     *
     * @param estadoPosicionControlada Objeto de hibernate para construir el DTO
     * @return DTO creado a partir del parámetro de entrada
     */
    public static EstadoPosicionDTO crearEstadoPosicionDTO(
            EstadoPosicionControlada estadoPosicionControlada) {
        EstadoPosicionDTO estadoPosicionDTO = null;

        if (estadoPosicionControlada != null) {

            estadoPosicionDTO = new EstadoPosicionDTO();
            estadoPosicionDTO.setIdEstadoPosicion(estadoPosicionControlada
                    .getIdEstadoPosicionControlada().longValue());
            estadoPosicionDTO.setPosicionFinal(estadoPosicionControlada
                    .getPosicionFinal());
            estadoPosicionDTO.setPosicionInicial(estadoPosicionControlada
                    .getPosicionInicial());
        }

        return estadoPosicionDTO;
    }

    /**
     * Crea un objeto del tipo
     * {@link RegistroContableSaldoControladaDTO} a partir de un objeto
     * de hibernate del tipo {@link RegContEfecControlada}
     *
     * @param registroContable Objeto de hibernate para construir el DTO
     * @return DTO creado a partir del parámetro de entrada
     */
    public static RegistroContableSaldoControladaDTO crearRegistroContableSaldoControladaDTO(RegContEfecControlada registroContable) {
        RegistroContableSaldoControladaDTO dto = null;
        if (registroContable != null) {
            dto = new RegistroContableSaldoControladaDTO();
            // Id del registro contable
            dto.setIdRegistroContable(registroContable.getIdRegistroContable().longValue());
            // Fecha en la que se afecto el saldo
            dto.setFechaLiquidacion(registroContable.getFecha());
            // Saldo que se ve afectado por el registro contable
            dto.setSaldoEfectivo(crearSaldoEfectivoDTO(registroContable.getSaldo()));
            // Tipo de acción aplicada al saldo
            dto.setTipoAccion(crearTipoAccion(registroContable.getTipoAccion()));
            // Monto del registro contable
            dto.setCantidad(registroContable.getImporte());
        }
        return dto;
    }

    /**
     * Crea un objeto del tipo
     * {@link RegistroContableSaldoControladaDTO} a partir de un objeto
     * de hibernate del tipo {@link RegContEfecControladaHistorico}
     *
     * @param registroContable Objeto de hibernate para construir el DTO
     * @return DTO creado a partir del parámetro de entrada
     */
    public static RegistroContableSaldoControladaDTO crearRegistroContableSaldoControladaDTO(
            RegContEfecControladaHistorico registroContable) {
        RegistroContableSaldoControladaDTO dto = null;
        if (registroContable != null) {
            dto = new RegistroContableSaldoControladaDTO();

            dto.setHistorico(true);
            // Id del registro contable
            dto.setIdRegistroContable(registroContable.getIdRegistroContable()
                    .longValue());
            // Fecha en la que se afecto el saldo
            dto.setFechaLiquidacion(registroContable.getFecha());
            // Saldo que se ve afectado por el registro contable
            dto.setSaldoEfectivo(crearSaldoEfectivoDTO(registroContable
                    .getSaldo()));
            // Tipo de acción aplicada al saldo
            dto
                    .setTipoAccion(crearTipoAccion(registroContable
                            .getTipoAccion()));
            // Monto del registro contable
            dto.setCantidad(registroContable.getImporte());
        }
        return dto;
    }

    /**
     * Crea un objeto del tipo {@link DetalleMovimientoValorDTO} a partir de un
     * objeto de hibernate del tipo {@link RegContValNombrada}
     *
     * @param regContable Objeto de hibernate para construir el DTO
     * @return DTO creado a partir del parámetro de entrada
     */
    public static DetalleMovimientoValorDTO crearDetalleMovimientoTLPDTO(
            RegContValNombrada regContable) {
        DetalleMovimientoValorDTO dto = null;
        if (regContable != null) {
            dto = crearDetalleMovimientoDTO(regContable);
            // Posición correspondiente a la operación que se lleva a cabo */
            dto
                    .setPosicion(crearPosicionNombradaDTO(regContable
                            .getPosicion()));
            // Fecha de concertación de la operación */
            dto.setFechaConcertacion(regContable.getOperacion()
                    .getInstruccion().getFechaConcertacion());
            // Fecha de liquidación de la operación */
            dto.setFechaLiquidacion(regContable.getOperacion().getInstruccion()
                    .getFechaLiquidacion());
            // Fecha de aplicación de la instrucción
            dto.setFechaAplicacion(regContable.getOperacion().getInstruccion()
                    .getFechaAplicacion());
            // Cantidad de posiciones que se registró en la operación
            dto.setCantidad(regContable.getCantidad().doubleValue());
        }
        return dto;
    }

    /**
     * Crea un objeto del tipo {@link DetalleMovimientoValorDTO} a partir de un
     * objeto de hibernate del tipo {@link RegContValNombradaHistorico}
     *
     * @param regContable Objeto de hibernate para construir el DTO
     * @return DTO creado a partir del parámetro de entrada
     */
    public static DetalleMovimientoValorDTO crearDetalleMovimientoTLPDTO(
            RegContValNombradaHistorico regContable) {
        DetalleMovimientoValorDTO dto = null;
        if (regContable != null) {
            dto = crearDetalleMovimientoDTO(regContable);
            // Posición correspondiente a la operación que se lleva a cabo */
            dto
                    .setPosicion(crearPosicionNombradaDTO(regContable
                            .getPosicion()));
            // Fecha de concertación de la operación */
            dto.setFechaConcertacion(regContable.getOperacion()
                    .getInstruccion().getFechaConcertacion());
            // Fecha de liquidación de la operación */
            dto.setFechaLiquidacion(regContable.getOperacion().getInstruccion()
                    .getFechaLiquidacion());
            // Fecha de aplicación de la instrucción
            dto.setFechaAplicacion(regContable.getOperacion().getInstruccion()
                    .getFechaAplicacion());
            // Cantidad de posiciones que se registró en la operación
            dto.setCantidad(regContable.getCantidad().doubleValue());
        }
        return dto;
    }

    /**
     * Crea un objeto del tipo {@link DetalleMovimientoValorDTO} a partir de un
     * objeto de hibernate del tipo {@link RegContValNombrada}
     *
     * @param regContable Objeto de hibernate para construir el DTO
     * @return DTO creado a partir del parámetro de entrada
     */
    public static DetalleMovimientoValorDTO crearDetalleMovimientoDVPDTO(
            RegContValNombrada regContable) {
        DetalleMovimientoValorDTO dto = null;
        if (regContable != null) {
            dto = crearDetalleMovimientoDTO(regContable);
            // Posición correspondiente a la operación que se lleva a cabo */
            dto
                    .setPosicion(crearPosicionNombradaDTO(regContable
                            .getPosicion()));
            // Fecha de concertación de la operación */
            dto.setFechaConcertacion(regContable.getOperacion()
                    .getInstruccion().getFechaConcertacion());
            // Fecha de liquidación de la operación */
            dto.setFechaLiquidacion(regContable.getOperacion().getInstruccion()
                    .getFechaLiquidacion());

            dto.getPosicion().getEmision().setCupon(
                    regContable.getOperacion().getCupon() != null ? regContable
                            .getOperacion().getCupon().getClaveCupon() : "");
            // Nombre y cuenta del transpasante
            if (regContable.getOperacion().getCuentaNombradaTraspasante() != null) {
                dto.setTraspasante(regContable.getOperacion()
                        .getCuentaNombradaTraspasante().getInstitucion()
                        .getTipoInstitucion().getClaveTipoInstitucion()
                        + " "
                        + regContable.getOperacion()
                        .getCuentaNombradaTraspasante()
                        .getInstitucion().getFolioInstitucion()
                        + " "
                        + regContable.getOperacion()
                        .getCuentaNombradaTraspasante().getCuenta()
                        + " "
                        + regContable.getOperacion()
                        .getCuentaNombradaTraspasante()
                        .getInstitucion().getNombreCorto());
            }
            // Nombre y cuenta del receptor
            if (regContable.getOperacion().getCuentaNombradaReceptor() != null) {
                dto.setReceptor(regContable.getOperacion()
                        .getCuentaNombradaReceptor().getInstitucion()
                        .getTipoInstitucion().getClaveTipoInstitucion()
                        + " "
                        + regContable.getOperacion()
                        .getCuentaNombradaReceptor().getInstitucion()
                        .getFolioInstitucion()
                        + " "
                        + regContable.getOperacion()
                        .getCuentaNombradaReceptor().getCuenta()
                        + " "
                        + regContable.getOperacion()
                        .getCuentaNombradaReceptor().getInstitucion()
                        .getNombreCorto());
            }
            dto.setFechaAplicacion(regContable.getOperacion().getInstruccion()
                    .getFechaAplicacion());
            // Cantidad de posiciones que se registró en la operación
            dto.setCantidad(regContable.getCantidad().doubleValue());
            // Precio nominal del valor
            dto.setPrecio(regContable.getOperacion().getPrecio().doubleValue());
            // Cantidad de posiciones que se registró en la operación
            dto.setCantidad(regContable.getCantidad().doubleValue());
            // Importe del valor. Se calcula multiplicando el precio nominal por
            // la cantidad operada
            dto.setImporte(dto.getPrecio() * dto.getCantidad());
            dto.setDescripcionBovedaEfectivo(regContable.getOperacion()
                    .getBovedaEfectivo() != null ? regContable.getOperacion()
                    .getBovedaEfectivo().getDescripcion() : "");
            dto
                    .setDescripcionDivisa(regContable.getOperacion()
                            .getDivisa() != null ? regContable.getOperacion()
                            .getDivisa().getDescripcion() : StringUtils.EMPTY);
        }
        return dto;
    }

    /**
     * Crea un objeto del tipo {@link DetalleMovimientoValorDTO} a partir de un
     * objeto de hibernate del tipo {@link RegContValNombradaHistorico}
     *
     * @param regContable Objeto de hibernate para construir el DTO
     * @return DTO creado a partir del parámetro de entrada
     */
    public static DetalleMovimientoValorDTO crearDetalleMovimientoDVPDTO(
            RegContValNombradaHistorico regContable) {
        DetalleMovimientoValorDTO dto = null;
        if (regContable != null) {
            dto = crearDetalleMovimientoDTO(regContable);
            // Posición correspondiente a la operación que se lleva a cabo */
            dto
                    .setPosicion(crearPosicionNombradaDTO(regContable
                            .getPosicion()));
            // Fecha de concertación de la operación */
            dto.setFechaConcertacion(regContable.getOperacion()
                    .getInstruccion().getFechaConcertacion());
            // Fecha de liquidación de la operación */
            dto.setFechaLiquidacion(regContable.getOperacion().getInstruccion()
                    .getFechaLiquidacion());

            dto.getPosicion().getEmision().setCupon(
                    regContable.getOperacion().getCupon() != null ? regContable
                            .getOperacion().getCupon().getClaveCupon() : "");
            // Nombre y cuenta del transpasante
            if (regContable.getOperacion().getCuentaNombradaTraspasante() != null) {
                dto.setTraspasante(regContable.getOperacion()
                        .getCuentaNombradaTraspasante().getInstitucion()
                        .getTipoInstitucion().getClaveTipoInstitucion()
                        + " "
                        + regContable.getOperacion()
                        .getCuentaNombradaTraspasante()
                        .getInstitucion().getFolioInstitucion()
                        + " "
                        + regContable.getOperacion()
                        .getCuentaNombradaTraspasante().getCuenta()
                        + " "
                        + regContable.getOperacion()
                        .getCuentaNombradaTraspasante()
                        .getInstitucion().getNombreCorto());
            }
            // Nombre y cuenta del receptor
            if (regContable.getOperacion().getCuentaNombradaReceptor() != null) {
                dto.setReceptor(regContable.getOperacion()
                        .getCuentaNombradaReceptor().getInstitucion()
                        .getTipoInstitucion().getClaveTipoInstitucion()
                        + " "
                        + regContable.getOperacion()
                        .getCuentaNombradaReceptor().getInstitucion()
                        .getFolioInstitucion()
                        + " "
                        + regContable.getOperacion()
                        .getCuentaNombradaReceptor().getCuenta()
                        + " "
                        + regContable.getOperacion()
                        .getCuentaNombradaReceptor().getInstitucion()
                        .getNombreCorto());
            }
            dto.setFechaAplicacion(regContable.getOperacion().getInstruccion()
                    .getFechaAplicacion());
            // Cantidad de posiciones que se registró en la operación
            dto.setCantidad(regContable.getCantidad().doubleValue());
            // Precio nominal del valor
            dto.setPrecio(regContable.getOperacion().getPrecio().doubleValue());
            // Cantidad de posiciones que se registró en la operación
            dto.setCantidad(regContable.getCantidad().doubleValue());
            // Importe del valor. Se calcula multiplicando el precio nominal por
            // la cantidad operada
            dto.setImporte(dto.getPrecio() * dto.getCantidad());
            dto.setDescripcionBovedaEfectivo(regContable.getOperacion()
                    .getBovedaEfectivo() != null ? regContable.getOperacion()
                    .getBovedaEfectivo().getDescripcion() : "");
            dto
                    .setDescripcionDivisa(regContable.getOperacion()
                            .getDivisa() != null ? regContable.getOperacion()
                            .getDivisa().getDescripcion() : StringUtils.EMPTY);
        }
        return dto;
    }

    /**
     * Crea un objeto del tipo {@link DetalleMovimientoValorDTO} a partir de un
     * objeto de hibernate del tipo {@link RegContValNombrada}
     *
     * @param regContable Objeto de hibernate para construir el DTO
     * @return DTO creado a partir del parámetro de entrada
     */
    public static DetalleMovimientoValorDTO crearDetalleMovimientoDVDDTO(
            RegContValNombrada regContable) {
        DetalleMovimientoValorDTO dto = null;
        if (regContable != null) {
            dto = crearDetalleMovimientoDTO(regContable);
            // Posición correspondiente a la operación que se lleva a cabo */
            dto
                    .setPosicion(crearPosicionNombradaDTO(regContable
                            .getPosicion()));
            // Fecha de concertación de la operación */
            dto.setFechaConcertacion(regContable.getOperacion()
                    .getInstruccion().getFechaConcertacion());
            // Fecha de liquidación de la operación */
            dto.setFechaLiquidacion(regContable.getOperacion().getInstruccion()
                    .getFechaLiquidacion());
            // Cantidad de posiciones que se registró en la operación
            dto.setCantidad(regContable.getCantidad().doubleValue());
            // Precio nominal del valor
            dto.setPrecio(regContable.getOperacion().getPrecio().doubleValue());
            // Cantidad de posiciones que se registró en la operación
            dto.setCantidad(regContable.getCantidad().doubleValue());
            // Importe del valor. Se calcula multiplicando el precio nominal por
            // la cantidad operada
            dto.setImporte(dto.getPrecio() * dto.getCantidad());
        }
        return dto;
    }

    /**
     * Crea un objeto del tipo {@link DetalleMovimientoValorDTO} a partir de un
     * objeto de hibernate del tipo {@link RegContValNombradaHistorico}
     *
     * @param regContable Objeto de hibernate para construir el DTO
     * @return DTO creado a partir del parámetro de entrada
     */
    public static DetalleMovimientoValorDTO crearDetalleMovimientoDVDDTO(
            RegContValNombradaHistorico regContable) {
        DetalleMovimientoValorDTO dto = null;
        if (regContable != null) {
            dto = crearDetalleMovimientoDTO(regContable);
            // Posición correspondiente a la operación que se lleva a cabo */
            dto
                    .setPosicion(crearPosicionNombradaDTO(regContable
                            .getPosicion()));
            // Fecha de concertación de la operación */
            dto.setFechaConcertacion(regContable.getOperacion()
                    .getInstruccion().getFechaConcertacion());
            // Fecha de liquidación de la operación */
            dto.setFechaLiquidacion(regContable.getOperacion().getInstruccion()
                    .getFechaLiquidacion());
            // Cantidad de posiciones que se registró en la operación
            dto.setCantidad(regContable.getCantidad().doubleValue());
            // Precio nominal del valor
            dto.setPrecio(regContable.getOperacion().getPrecio().doubleValue());
            // Cantidad de posiciones que se registró en la operación
            dto.setCantidad(regContable.getCantidad().doubleValue());
            // Importe del valor. Se calcula multiplicando el precio nominal por
            // la cantidad operada
            dto.setImporte(dto.getPrecio() * dto.getCantidad());
        }
        return dto;
    }

    /**
     * Crea un objeto del tipo {@link DetalleMovimientoValorDTO} a partir de un
     * objeto de hibernate del tipo {@link RegContValNombrada} con los datos en
     * comun para los diferentes tipos de operaciones (DVP, TLP, DVD)
     *
     * @param regContable Objeto de hibernate para construir el DTO
     * @return DTO creado a partir del parámetro de entrada
     */
    private static DetalleMovimientoValorDTO crearDetalleMovimientoDTO(
            RegContValNombrada regContable) {
        DetalleMovimientoValorDTO dto = new DetalleMovimientoValorDTO();
        // Identificador del registro contable que se registró en una operación
        // de una posición */
        dto.setIdRegistroContable(regContable.getIdRegistroContable()
                .longValue());
        // Tipo de operación */
        dto.setTipoOperacion(crearTipoOperacion(regContable.getOperacion()
                .getTipoOperacion()));
        // Fecha de registro de la operación */
        dto.setFecha(regContable.getFecha());
        // Nombre y cuenta del transpasante
        if (regContable.getOperacion().getCuentaNombradaTraspasante() != null) {
            dto.setTraspasante(regContable.getOperacion()
                    .getCuentaNombradaTraspasante().getInstitucion()
                    .getTipoInstitucion().getClaveTipoInstitucion()
                    + " "
                    + regContable.getOperacion().getCuentaNombradaTraspasante()
                    .getInstitucion().getFolioInstitucion()
                    + " "
                    + regContable.getOperacion().getCuentaNombradaTraspasante()
                    .getCuenta()
                    + " "
                    + regContable.getOperacion().getCuentaNombradaTraspasante()
                    .getInstitucion().getNombreCorto());
        }
        // Nombre y cuenta del receptor
        if (regContable.getOperacion().getCuentaNombradaReceptor() != null) {
            dto.setReceptor(regContable.getOperacion()
                    .getCuentaNombradaReceptor().getInstitucion()
                    .getTipoInstitucion().getClaveTipoInstitucion()
                    + " "
                    + regContable.getOperacion().getCuentaNombradaReceptor()
                    .getInstitucion().getFolioInstitucion()
                    + " "
                    + regContable.getOperacion().getCuentaNombradaReceptor()
                    .getCuenta()
                    + " "
                    + regContable.getOperacion().getCuentaNombradaReceptor()
                    .getInstitucion().getNombreCorto());
        }
        // Folio de la operación a la que pertenece esta operación */
        dto.setFolioOperacion(regContable.getOperacion().getInstruccion()
                .getFolioInstruccion().longValue());
        // Cantidad entregada en la operación
        dto.setEntrega(regContable.getCantidad());
        // La descripción del registro contable */
        String descripcion = "";
        if (regContable.getCargoA().equals(
                TipoCargoAValores.CARGO_AFECTA_DISPONIBLE)) {
            if (regContable.getTipoAccion().getValue() == TipoAccion.ABONO_PASIVO_VALUE
                    || regContable.getTipoAccion().getValue() == TipoAccion.CARGO_ACTIVO_VALUE) {
                descripcion = TipoAccionConstants.TIPO_ACCION_RECEPCION_POSICION;
            } else {
                descripcion = TipoAccionConstants.TIPO_ACCION_ENTREGA_POSICION;
            }
        } else {
            if (regContable.getTipoAccion().getValue() == TipoAccion.ABONO_PASIVO_VALUE
                    || regContable.getTipoAccion().getValue() == TipoAccion.CARGO_ACTIVO_VALUE) {
                descripcion = TipoAccionConstants.TIPO_ACCION_RECEPCION_POSICION;
            } else {
                descripcion = TipoAccionConstants.TIPO_ACCION_ENTREGA_POSICION;
            }
        }
        descripcion += " POR "
                + regContable.getOperacion().getTipoOperacion()
                .getDescripcion();
        descripcion += " POR "
                + regContable.getOperacion().getInstruccion()
                .getTipoInstruccion().getInstruccion();
        dto.setDescripcion(descripcion);
        return dto;
    }

    /**
     * Crea un objeto del tipo {@link DetalleMovimientoValorDTO} a partir de un
     * objeto de hibernate del tipo {@link RegContValNombradaHistorico} con los
     * datos en comun para los diferentes tipos de operaciones (DVP, TLP, DVD)
     *
     * @param regContable Objeto de hibernate para construir el DTO
     * @return DTO creado a partir del parámetro de entrada
     */
    private static DetalleMovimientoValorDTO crearDetalleMovimientoDTO(
            RegContValNombradaHistorico regContable) {
        DetalleMovimientoValorDTO dto = new DetalleMovimientoValorDTO();
        // Identificador del registro contable que se registró en una operación
        // de una posición */
        dto.setIdRegistroContable(regContable.getIdRegistroContableHistorico()
                .longValue());
        // Tipo de operación */
        dto.setTipoOperacion(crearTipoOperacion(regContable.getOperacion()
                .getTipoOperacion()));
        // Fecha de registro de la operación */
        dto.setFecha(regContable.getFecha());
        // Nombre y cuenta del transpasante
        if (regContable.getOperacion().getCuentaNombradaTraspasante() != null) {
            dto.setTraspasante(regContable.getOperacion()
                    .getCuentaNombradaTraspasante().getInstitucion()
                    .getTipoInstitucion().getClaveTipoInstitucion()
                    + " "
                    + regContable.getOperacion().getCuentaNombradaTraspasante()
                    .getInstitucion().getFolioInstitucion()
                    + " "
                    + regContable.getOperacion().getCuentaNombradaTraspasante()
                    .getCuenta()
                    + " "
                    + regContable.getOperacion().getCuentaNombradaTraspasante()
                    .getInstitucion().getNombreCorto());
        }
        // Nombre y cuenta del receptor
        if (regContable.getOperacion().getCuentaNombradaReceptor() != null) {
            dto.setReceptor(regContable.getOperacion()
                    .getCuentaNombradaReceptor().getInstitucion()
                    .getTipoInstitucion().getClaveTipoInstitucion()
                    + " "
                    + regContable.getOperacion().getCuentaNombradaReceptor()
                    .getInstitucion().getFolioInstitucion()
                    + " "
                    + regContable.getOperacion().getCuentaNombradaReceptor()
                    .getCuenta()
                    + " "
                    + regContable.getOperacion().getCuentaNombradaReceptor()
                    .getInstitucion().getNombreCorto());
        }
        // Folio de la operación a la que pertenece esta operación */
        dto.setFolioOperacion(regContable.getOperacion().getInstruccion()
                .getFolioInstruccion().longValue());
        // Cantidad entregada en la operación
        dto.setEntrega(regContable.getCantidad());
        // La descripción del registro contable */
        String descripcion = "";
        if (regContable.getCargoA().equals(
                TipoCargoAValores.CARGO_AFECTA_DISPONIBLE)) {
            if (regContable.getTipoAccion().getValue() == TipoAccion.ABONO_PASIVO_VALUE
                    || regContable.getTipoAccion().getValue() == TipoAccion.CARGO_ACTIVO_VALUE) {
                descripcion = TipoAccionConstants.TIPO_ACCION_RECEPCION_POSICION;
            } else {
                descripcion = TipoAccionConstants.TIPO_ACCION_ENTREGA_POSICION;
            }
        } else {
            if (regContable.getTipoAccion().getValue() == TipoAccion.ABONO_PASIVO_VALUE
                    || regContable.getTipoAccion().getValue() == TipoAccion.CARGO_ACTIVO_VALUE) {
                descripcion = TipoAccionConstants.TIPO_ACCION_RECEPCION_POSICION;
            } else {
                descripcion = TipoAccionConstants.TIPO_ACCION_ENTREGA_POSICION;
            }
        }
        descripcion += " POR "
                + regContable.getOperacion().getTipoOperacion()
                .getDescripcion();
        descripcion += " POR "
                + regContable.getOperacion().getInstruccion()
                .getTipoInstruccion().getInstruccion();
        dto.setDescripcion(descripcion);
        return dto;
    }

    /**
     * Crea un objeto del tipo {@link RegistroContablePosicionControladaDTO} a
     * partir de un objeto de hibernate del tipo {@link RegContValControlada}
     *
     * @param regContValControlada Objeto de hibernate para construir el DTO
     * @return DTO creado a partir del parámetro de entrada
     */
    public static RegistroContablePosicionControladaDTO crearRegistroContablePosicionControlada(
            RegContValControlada regContValControlada) {

        RegistroContablePosicionControladaDTO registroContablePosicionControladaDTO = null;

        if (regContValControlada != null) {
            registroContablePosicionControladaDTO = new RegistroContablePosicionControladaDTO();

            registroContablePosicionControladaDTO
                    .setCantidad(regContValControlada.getCantidad());
            registroContablePosicionControladaDTO.setFecha(regContValControlada
                    .getFecha());
            registroContablePosicionControladaDTO
                    .setIdCiclo(regContValControlada.getIdCiclo().longValue());
            registroContablePosicionControladaDTO
                    .setIdRegistroContable(regContValControlada
                            .getIdRegistroContable().longValue());
            registroContablePosicionControladaDTO
                    .setPosicion(crearPosicionControladaDTO(regContValControlada
                            .getPosicionControlada()));
            registroContablePosicionControladaDTO
                    .setTipoAccion(crearTipoAccion(regContValControlada
                            .getTipoAccion()));

        }

        return registroContablePosicionControladaDTO;
    }

    /**
     * Crea un objeto del tipo {@link DetalleMovimientoEfectivoDTO} a partir de
     * un objeto de hibernate del tipo {@link RegContEfecNombrada}
     *
     * @param regContEfecNombrada Objeto de hibernate para construir el DTO
     * @param tipoRetiro          Objeto que ayuda a determinar el tipo de retiro del objeto contruido DTO
     * @param idTipoPago          Objeto que ayuda a determinar el tipo de pago del objeto contruido DTO
     * @return DTO creado a partir del parámetro de entrada
     */
    public static DetalleMovimientoEfectivoDTO crearDetalleMovimientoEfectivoDTO(
            RegContEfecNombrada regContEfecNombrada, String tipoRetiro,
            Integer idTipoPago) {
        DetalleMovimientoEfectivoDTO dto = null;
        if (regContEfecNombrada != null) {
            dto = new DetalleMovimientoEfectivoDTO();
            // Fecha del movimiento
            dto.setFechaMovimiento(regContEfecNombrada.getFecha());

            // Descripción del movimiento
            dto.setDescripcionMovimiento(null);
            // Tipo de movimiento
            String movimiento = "";
            if (regContEfecNombrada.getCargoA().equals(
                    TipoCargoAValores.CARGO_AFECTA_DISPONIBLE)) {
                if (regContEfecNombrada.getTipoAccion().getValue() == TipoAccion.ABONO_PASIVO_VALUE
                        || regContEfecNombrada.getTipoAccion().getValue() == TipoAccion.CARGO_ACTIVO_VALUE) {
                    movimiento = TipoAccionConstants.TIPO_ACCION_RECEPCION_SALDO;
                } else {
                    movimiento = TipoAccionConstants.TIPO_ACCION_ENTREGA_SALDO;
                }
            } else {
                if (regContEfecNombrada.getTipoAccion().getValue() == TipoAccion.ABONO_PASIVO_VALUE
                        || regContEfecNombrada.getTipoAccion().getValue() == TipoAccion.CARGO_ACTIVO_VALUE) {
                    movimiento = TipoAccionConstants.TIPO_ACCION_RECEPCION_SALDO;
                } else {
                    movimiento = TipoAccionConstants.TIPO_ACCION_ENTREGA_SALDO;
                }
            }
            dto.setMovimiento(movimiento);
            // Folio de la operación
            dto.setFolioOperacion(regContEfecNombrada.getOperacion()
                    .getInstruccion().getFolioInstruccion().longValue());
            // Cargo del movimiento (es el mismo que monto operado)
            dto.setCargoMovimiento(regContEfecNombrada.getImporte()
                    .doubleValue());
            // Transpasante
            if (regContEfecNombrada.getOperacion()
                    .getCuentaEfectivoTraspasante() != null) {
                dto.setTraspasante(regContEfecNombrada.getOperacion()
                        .getCuentaEfectivoTraspasante().getInstitucion()
                        .getTipoInstitucion().getClaveTipoInstitucion()
                        + " "
                        + regContEfecNombrada.getOperacion()
                        .getCuentaEfectivoTraspasante()
                        .getInstitucion().getFolioInstitucion()
                        + " "
                        + regContEfecNombrada.getOperacion()
                        .getCuentaEfectivoTraspasante().getCuenta()
                        + " "
                        + regContEfecNombrada.getOperacion()
                        .getCuentaEfectivoTraspasante()
                        .getInstitucion().getNombreCorto());
            }
            logger.info("40");
            // Receptor
            if (regContEfecNombrada.getOperacion().getCuentaEfectivoReceptor() != null) {
                dto.setReceptor(regContEfecNombrada.getOperacion()
                        .getCuentaEfectivoReceptor().getInstitucion()
                        .getTipoInstitucion().getClaveTipoInstitucion()
                        + " "
                        + regContEfecNombrada.getOperacion()
                        .getCuentaEfectivoReceptor().getInstitucion()
                        .getFolioInstitucion()
                        + " "
                        + regContEfecNombrada.getOperacion()
                        .getCuentaEfectivoReceptor().getCuenta()
                        + " "
                        + regContEfecNombrada.getOperacion()
                        .getCuentaEfectivoReceptor().getInstitucion()
                        .getNombreCorto());
            }
            logger.info("50");
            // Participante del movimiento
            if (regContEfecNombrada.getOperacion().getIdInstitucionReceptor() != null
                    && regContEfecNombrada.getOperacion()
                    .getIdInstitucionReceptor().equals(
                            regContEfecNombrada.getSaldoNombrada()
                                    .getCuentaNombrada()
                                    .getInstitucion()
                                    .getIdInstitucion())) {
                dto.setParticipante(dto.getReceptor());
                dto.setContraparte(dto.getTraspasante());
            } else {
                if (regContEfecNombrada.getOperacion()
                        .getIdInstitucionTraspasante() != null
                        && regContEfecNombrada.getOperacion()
                        .getIdInstitucionTraspasante().equals(
                                regContEfecNombrada.getSaldoNombrada()
                                        .getCuentaNombrada()
                                        .getInstitucion()
                                        .getIdInstitucion())) {
                    dto.setParticipante(dto.getTraspasante());
                    dto.setContraparte(dto.getReceptor());
                }
            }
            logger.info("60");
            // Saldo
            dto.setSaldoEfectivoDTO(crearSaldoEfectivoDTO(regContEfecNombrada
                    .getSaldoNombrada()));
            // Instrucción de liquidación
            logger.info("70");
            dto.setInstruccion(crearInstruccionLiquidacion(regContEfecNombrada
                    .getOperacion().getInstruccion()));
            // Operación realizada
            logger.info("80");
            dto.setOperacion(crearOperacionSaldoDTO(regContEfecNombrada
                    .getOperacion()));
            logger.info("90");
            // Tipo de Deposito / Retiro
            String tipoDepositoRetiro = "";
            if (StringUtils.isNotBlank(tipoRetiro)) {
                tipoDepositoRetiro = tipoRetiro;
            } else if (idTipoPago != null) {
                switch (idTipoPago) {
                    case TipoPagoConstants.TERCERO_A_TERCERO_SPEI:
                    case TipoPagoConstants.PARTICIPANTE_A_TERCERO_SPEI:
                    case TipoPagoConstants.PARTICIPANTE_A_PARTICIPANTE_SPEI:
                        tipoDepositoRetiro = TipoPagoConstants.DESCRIPCION_SPEI;
                        break;
                    case TipoPagoConstants.TRASPASO_FONDOS_SIAC:
                        tipoDepositoRetiro = TipoPagoConstants.DESCRIPCION_SIAC;
                        break;
                    case TipoPagoConstants.DEPOSITO_DEVOLUCION:
                        tipoDepositoRetiro = TipoPagoConstants.DESCRIPCION_DEVO;
                        break;
                    case TipoPagoConstants.DEPOSITO_OTRAS_DIVISAS:
                        tipoDepositoRetiro = TipoPagoConstants.DESCRIPCION_DIVI;
                        break;
                }
            }
            if (StringUtils.isNotBlank(tipoDepositoRetiro)
                    && dto.getOperacion() != null
                    && dto.getOperacion().getInstruccionLiquidacion() != null) {
                dto.getOperacion().getInstruccionLiquidacion()
                        .setInstruccionEfectivo(new InstruccionEfectivoDTO());
                dto.getOperacion().getInstruccionLiquidacion()
                        .getInstruccionEfectivo().setTipoRetiro(
                                tipoDepositoRetiro);

                /*
                 * TODO
                 * Se comenta este bloque debido a que esta seteando el tipo de instrucción
                 * en el traspasante o receptor.
                 * Si se requiere mostrar este campo, se sugiere agregar una propiedad de
                 * tipoDepositoRetiro como en la pantalla principal.
                 *
                 * Pablo Balderas 06/03/2014
                 */
                String tipoInstruccion = dto.getOperacion()
                        .getInstruccionLiquidacion().getTipoInstruccion()
                        .getNombreCorto();
                if (StringUtils.isNotBlank(tipoInstruccion)) {
                    if (tipoInstruccion.trim().equalsIgnoreCase("DEPE")) {
                        dto.setTraspasante(tipoDepositoRetiro);
                    } else if (tipoInstruccion.trim().equalsIgnoreCase("RETE")) {
                        dto.setReceptor(tipoDepositoRetiro);
                    }
                }
            }
        }
        return dto;
    }


    /**
     * Crea un objeto del tipo {@link DetalleMovimientoEfectivoDTO} a partir de
     * un objeto de hibernate del tipo {@link RegContEfecNombrada}
     *
     * @param regContEfecNombrada Objeto de hibernate para construir el DTO
     * @return DTO creado a partir del parámetro de entrada
     */
    public static DetalleMovimientoEfectivoDTO crearDetalleMovimientoEfectivoDTO(
            RegContEfecNombrada regContEfecNombrada) {
        DetalleMovimientoEfectivoDTO dto = null;
        if (regContEfecNombrada != null) {
            dto = new DetalleMovimientoEfectivoDTO();
            // Fecha del movimiento
            dto.setFechaMovimiento(regContEfecNombrada.getFecha());

            // Descripción del movimiento
            dto.setDescripcionMovimiento(null);
            // Tipo de movimiento
            String movimiento = "";
            if (regContEfecNombrada.getCargoA().equals(
                    TipoCargoAValores.CARGO_AFECTA_DISPONIBLE)) {
                if (regContEfecNombrada.getTipoAccion().getValue() == TipoAccion.ABONO_PASIVO_VALUE
                        || regContEfecNombrada.getTipoAccion().getValue() == TipoAccion.CARGO_ACTIVO_VALUE) {
                    movimiento = TipoAccionConstants.TIPO_ACCION_RECEPCION_SALDO;
                } else {
                    movimiento = TipoAccionConstants.TIPO_ACCION_ENTREGA_SALDO;
                }
            } else {
                if (regContEfecNombrada.getTipoAccion().getValue() == TipoAccion.ABONO_PASIVO_VALUE
                        || regContEfecNombrada.getTipoAccion().getValue() == TipoAccion.CARGO_ACTIVO_VALUE) {
                    movimiento = TipoAccionConstants.TIPO_ACCION_RECEPCION_SALDO;
                } else {
                    movimiento = TipoAccionConstants.TIPO_ACCION_ENTREGA_SALDO;
                }
            }
            dto.setMovimiento(movimiento);
            // Folio de la operación
            dto.setFolioOperacion(regContEfecNombrada.getOperacion()
                    .getInstruccion().getFolioInstruccion().longValue());
            // Cargo del movimiento (es el mismo que monto operado)
            dto.setCargoMovimiento(regContEfecNombrada.getImporte()
                    .doubleValue());
            // Transpasante
            if (regContEfecNombrada.getOperacion()
                    .getCuentaEfectivoTraspasante() != null) {
                dto.setTraspasante(regContEfecNombrada.getOperacion()
                        .getCuentaEfectivoTraspasante().getInstitucion()
                        .getTipoInstitucion().getClaveTipoInstitucion()
                        + " "
                        + regContEfecNombrada.getOperacion()
                        .getCuentaEfectivoTraspasante()
                        .getInstitucion().getFolioInstitucion()
                        + " "
                        + regContEfecNombrada.getOperacion()
                        .getCuentaEfectivoTraspasante().getCuenta()
                        + " "
                        + regContEfecNombrada.getOperacion()
                        .getCuentaEfectivoTraspasante()
                        .getInstitucion().getNombreCorto());
            }
            logger.info("40");
            // Receptor
            if (regContEfecNombrada.getOperacion().getCuentaEfectivoReceptor() != null) {
                dto.setReceptor(regContEfecNombrada.getOperacion()
                        .getCuentaEfectivoReceptor().getInstitucion()
                        .getTipoInstitucion().getClaveTipoInstitucion()
                        + " "
                        + regContEfecNombrada.getOperacion()
                        .getCuentaEfectivoReceptor().getInstitucion()
                        .getFolioInstitucion()
                        + " "
                        + regContEfecNombrada.getOperacion()
                        .getCuentaEfectivoReceptor().getCuenta()
                        + " "
                        + regContEfecNombrada.getOperacion()
                        .getCuentaEfectivoReceptor().getInstitucion()
                        .getNombreCorto());
            }
            logger.info("50");
            // Participante del movimiento
            if (regContEfecNombrada.getOperacion().getIdInstitucionReceptor() != null
                    && regContEfecNombrada.getOperacion()
                    .getIdInstitucionReceptor().equals(
                            regContEfecNombrada.getSaldoNombrada()
                                    .getCuentaNombrada()
                                    .getInstitucion()
                                    .getIdInstitucion())) {
                dto.setParticipante(dto.getReceptor());
                dto.setContraparte(dto.getTraspasante());
            } else {
                if (regContEfecNombrada.getOperacion()
                        .getIdInstitucionTraspasante() != null
                        && regContEfecNombrada.getOperacion()
                        .getIdInstitucionTraspasante().equals(
                                regContEfecNombrada.getSaldoNombrada()
                                        .getCuentaNombrada()
                                        .getInstitucion()
                                        .getIdInstitucion())) {
                    dto.setParticipante(dto.getTraspasante());
                    dto.setContraparte(dto.getReceptor());
                }
            }
            logger.info("60");
            // Saldo
            dto.setSaldoEfectivoDTO(crearSaldoEfectivoDTO(regContEfecNombrada
                    .getSaldoNombrada()));
            // Instrucción de liquidación
            logger.info("70");
            dto.setInstruccion(crearInstruccionLiquidacion(regContEfecNombrada
                    .getOperacion().getInstruccion()));
            // Operación realizada
            logger.info("80");
            dto.setOperacion(crearOperacionSaldoDTO(regContEfecNombrada
                    .getOperacion()));
            logger.info("90");
        }
        return dto;
    }


    /**
     * Crea un objeto del tipo {@link DetalleMovimientoEfectivoDTO} a partir de
     * un objeto de hibernate del tipo {@link RegContEfecNombradaHistorico}
     *
     * @param regContEfecNombrada Objeto de hibernate para construir el DTO
     * @param tipoRetiro
     * @return DTO creado a partir del parámetro de entrada
     */
    public static DetalleMovimientoEfectivoDTO crearDetalleMovimientoEfectivoDTO(
            RegContEfecNombradaHistorico regContEfecNombrada,
            String tipoRetiro, Integer idTipoPago) {
        DetalleMovimientoEfectivoDTO dto = null;
        if (regContEfecNombrada != null) {
            dto = new DetalleMovimientoEfectivoDTO();
            // Fecha del movimiento
            dto.setFechaMovimiento(regContEfecNombrada.getFecha());
            // Descripción del movimiento
            dto.setDescripcionMovimiento(null);
            // Tipo de movimiento
            String movimiento = "";
            if (regContEfecNombrada.getCargoA().equals(TipoCargoAValores.CARGO_AFECTA_DISPONIBLE)) {
                if (regContEfecNombrada.getTipoAccion().getValue() == TipoAccion.ABONO_PASIVO_VALUE
                        || regContEfecNombrada.getTipoAccion().getValue() == TipoAccion.CARGO_ACTIVO_VALUE) {
                    movimiento = TipoAccionConstants.TIPO_ACCION_RECEPCION_SALDO;
                } else {
                    movimiento = TipoAccionConstants.TIPO_ACCION_ENTREGA_SALDO;
                }
            } else {
                if (regContEfecNombrada.getTipoAccion().getValue() == TipoAccion.ABONO_PASIVO_VALUE
                        || regContEfecNombrada.getTipoAccion().getValue() == TipoAccion.CARGO_ACTIVO_VALUE) {
                    movimiento = TipoAccionConstants.TIPO_ACCION_RECEPCION_SALDO;
                } else {
                    movimiento = TipoAccionConstants.TIPO_ACCION_ENTREGA_SALDO;
                }
            }
            dto.setMovimiento(movimiento);
            // Folio de la operación
            dto.setFolioOperacion(regContEfecNombrada.getOperacion().getInstruccion().getFolioInstruccion().longValue());
            // Cargo del movimiento (es el mismo que monto operado)
            dto.setCargoMovimiento(regContEfecNombrada.getImporte().doubleValue());
            // Transpasante
            if (regContEfecNombrada.getOperacion().getCuentaEfectivoTraspasante() != null) {
                dto.setTraspasante(regContEfecNombrada.getOperacion()
                        .getCuentaEfectivoTraspasante().getInstitucion()
                        .getTipoInstitucion().getClaveTipoInstitucion()
                        + " "
                        + regContEfecNombrada.getOperacion()
                        .getCuentaEfectivoTraspasante()
                        .getInstitucion().getFolioInstitucion()
                        + " "
                        + regContEfecNombrada.getOperacion()
                        .getIdCuentaEfectivoTraspasante()
                        + " "
                        + regContEfecNombrada.getOperacion()
                        .getCuentaEfectivoTraspasante()
                        .getInstitucion().getNombreCorto());
            }
            // Receptor
            if (regContEfecNombrada.getOperacion().getCuentaEfectivoReceptor() != null) {
                dto.setReceptor(regContEfecNombrada.getOperacion()
                        .getCuentaEfectivoReceptor().getInstitucion()
                        .getTipoInstitucion().getClaveTipoInstitucion()
                        + " "
                        + regContEfecNombrada.getOperacion()
                        .getCuentaEfectivoReceptor().getInstitucion()
                        .getFolioInstitucion()
                        + " "
                        + regContEfecNombrada.getOperacion()
                        .getIdCuentaEfectivoReceptor()
                        + " "
                        + regContEfecNombrada.getOperacion()
                        .getCuentaEfectivoReceptor().getInstitucion()
                        .getNombreCorto());
            }
            // Participante del movimiento
            if (regContEfecNombrada.getOperacion().getIdInstitucionReceptor() != null
                    && regContEfecNombrada.getOperacion()
                    .getIdInstitucionReceptor().equals(
                            regContEfecNombrada.getSaldoNombradaHistorico()
                                    .getCuentaNombrada()
                                    .getInstitucion()
                                    .getIdInstitucion())) {
                dto.setParticipante(dto.getReceptor());
                dto.setContraparte(dto.getTraspasante());
            } else {
                if (regContEfecNombrada.getOperacion()
                        .getIdInstitucionTraspasante() != null
                        && regContEfecNombrada.getOperacion()
                        .getIdInstitucionTraspasante().equals(
                                regContEfecNombrada.getSaldoNombradaHistorico()
                                        .getCuentaNombrada()
                                        .getInstitucion()
                                        .getIdInstitucion())) {
                    dto.setParticipante(dto.getTraspasante());
                    dto.setContraparte(dto.getReceptor());
                }
            }
            // Saldo
            dto.setSaldoEfectivoDTO(crearSaldoEfectivoDTO(regContEfecNombrada.getSaldoNombradaHistorico()));
            // Instrucción de liquidación
            dto.setInstruccion(crearInstruccionLiquidacionHistorico(regContEfecNombrada.getOperacion().getInstruccion()));
            // Operación realizada
            dto.setOperacion(crearOperacionSaldoDTO(regContEfecNombrada.getOperacion()));
            // Tipo de Deposito / Retiro
            String tipoDepositoRetiro = "";
            if (StringUtils.isNotBlank(tipoRetiro)) {
                tipoDepositoRetiro = tipoRetiro;
            } else if (idTipoPago != null) {
                switch (idTipoPago) {
                    case TipoPagoConstants.TERCERO_A_TERCERO_SPEI:
                    case TipoPagoConstants.PARTICIPANTE_A_TERCERO_SPEI:
                    case TipoPagoConstants.PARTICIPANTE_A_PARTICIPANTE_SPEI:
                        tipoDepositoRetiro = TipoPagoConstants.DESCRIPCION_SPEI;
                        break;
                    case TipoPagoConstants.TRASPASO_FONDOS_SIAC:
                        tipoDepositoRetiro = TipoPagoConstants.DESCRIPCION_SIAC;
                        break;
                    case TipoPagoConstants.DEPOSITO_DEVOLUCION:
                        tipoDepositoRetiro = TipoPagoConstants.DESCRIPCION_DEVO;
                        break;
                    case TipoPagoConstants.DEPOSITO_OTRAS_DIVISAS:
                        tipoDepositoRetiro = TipoPagoConstants.DESCRIPCION_DIVI;
                        break;
                }
            }
            if (StringUtils.isNotBlank(tipoDepositoRetiro)
                    && dto.getOperacion() != null
                    && dto.getOperacion().getInstruccionLiquidacion() != null) {
                logger.info("SI hay concidencias: [" + tipoDepositoRetiro + "]");
                dto.getOperacion().getInstruccionLiquidacion().setInstruccionEfectivo(new InstruccionEfectivoDTO());
                dto.getOperacion().getInstruccionLiquidacion().getInstruccionEfectivo().setTipoRetiro(tipoDepositoRetiro);
                /*
                 * TODO
                 * Se comenta este bloque debido a que esta seteando el tipo de instrucción
                 * en el traspasante o receptor.
                 * Si se requiere mostrar este campo, se sugiere agregar una propiedad de
                 * tipoDepositoRetiro como en la pantalla principal.
                 *
                 * Pablo Balderas 06/03/2014
                 */
                String tipoInstruccion = dto.getOperacion().getInstruccionLiquidacion().getTipoInstruccion().getNombreCorto();
                if (StringUtils.isNotBlank(tipoInstruccion)) {
                    if (tipoInstruccion.trim().equalsIgnoreCase("DEPE")) {
                        dto.setTraspasante(tipoDepositoRetiro);
                    } else if (tipoInstruccion.trim().equalsIgnoreCase("RETE")) {
                        dto.setReceptor(tipoDepositoRetiro);
                    }
                }
            } else {
                logger.info("No hay concidencias");
            }
        }
        return dto;
    }

    /**
     * Crea un objeto del tipo {@link DetalleMovimientoEfectivoDTO} a partir de
     * un objeto de hibernate del tipo {@link RegContEfecNombradaHistorico}
     *
     * @param regContEfecNombrada Objeto de hibernate para construir el DTO
     * @param regContEfecNombrada
     * @return DTO creado a partir del parámetro de entrada
     */
    public static DetalleMovimientoEfectivoDTO crearDetalleMovimientoEfectivoDTO(
            RegContEfecNombradaHistorico regContEfecNombrada) {
        DetalleMovimientoEfectivoDTO dto = null;
        if (regContEfecNombrada != null) {
            dto = new DetalleMovimientoEfectivoDTO();
            // Fecha del movimiento
            dto.setFechaMovimiento(regContEfecNombrada.getFecha());
            // Descripción del movimiento
            dto.setDescripcionMovimiento(null);
            // Tipo de movimiento
            String movimiento = "";
            if (regContEfecNombrada.getCargoA().equals(TipoCargoAValores.CARGO_AFECTA_DISPONIBLE)) {
                if (regContEfecNombrada.getTipoAccion().getValue() == TipoAccion.ABONO_PASIVO_VALUE
                        || regContEfecNombrada.getTipoAccion().getValue() == TipoAccion.CARGO_ACTIVO_VALUE) {
                    movimiento = TipoAccionConstants.TIPO_ACCION_RECEPCION_SALDO;
                } else {
                    movimiento = TipoAccionConstants.TIPO_ACCION_ENTREGA_SALDO;
                }
            } else {
                if (regContEfecNombrada.getTipoAccion().getValue() == TipoAccion.ABONO_PASIVO_VALUE
                        || regContEfecNombrada.getTipoAccion().getValue() == TipoAccion.CARGO_ACTIVO_VALUE) {
                    movimiento = TipoAccionConstants.TIPO_ACCION_RECEPCION_SALDO;
                } else {
                    movimiento = TipoAccionConstants.TIPO_ACCION_ENTREGA_SALDO;
                }
            }
            dto.setMovimiento(movimiento);
            // Folio de la operación
            dto.setFolioOperacion(regContEfecNombrada.getOperacion().getInstruccion().getFolioInstruccion().longValue());
            // Cargo del movimiento (es el mismo que monto operado)
            dto.setCargoMovimiento(regContEfecNombrada.getImporte().doubleValue());
            // Transpasante
            if (regContEfecNombrada.getOperacion().getCuentaEfectivoTraspasante() != null) {
                dto.setTraspasante(regContEfecNombrada.getOperacion()
                        .getCuentaEfectivoTraspasante().getInstitucion()
                        .getTipoInstitucion().getClaveTipoInstitucion()
                        + " "
                        + regContEfecNombrada.getOperacion()
                        .getCuentaEfectivoTraspasante()
                        .getInstitucion().getFolioInstitucion()
                        + " "
                        + regContEfecNombrada.getOperacion()
                        .getIdCuentaEfectivoTraspasante()
                        + " "
                        + regContEfecNombrada.getOperacion()
                        .getCuentaEfectivoTraspasante()
                        .getInstitucion().getNombreCorto());
            }
            // Receptor
            if (regContEfecNombrada.getOperacion().getCuentaEfectivoReceptor() != null) {
                dto.setReceptor(regContEfecNombrada.getOperacion()
                        .getCuentaEfectivoReceptor().getInstitucion()
                        .getTipoInstitucion().getClaveTipoInstitucion()
                        + " "
                        + regContEfecNombrada.getOperacion()
                        .getCuentaEfectivoReceptor().getInstitucion()
                        .getFolioInstitucion()
                        + " "
                        + regContEfecNombrada.getOperacion()
                        .getIdCuentaEfectivoReceptor()
                        + " "
                        + regContEfecNombrada.getOperacion()
                        .getCuentaEfectivoReceptor().getInstitucion()
                        .getNombreCorto());
            }
            // Participante del movimiento
            if (regContEfecNombrada.getOperacion().getIdInstitucionReceptor() != null
                    && regContEfecNombrada.getOperacion()
                    .getIdInstitucionReceptor().equals(
                            regContEfecNombrada.getSaldoNombradaHistorico()
                                    .getCuentaNombrada()
                                    .getInstitucion()
                                    .getIdInstitucion())) {
                dto.setParticipante(dto.getReceptor());
                dto.setContraparte(dto.getTraspasante());
            } else {
                if (regContEfecNombrada.getOperacion()
                        .getIdInstitucionTraspasante() != null
                        && regContEfecNombrada.getOperacion()
                        .getIdInstitucionTraspasante().equals(
                                regContEfecNombrada.getSaldoNombradaHistorico()
                                        .getCuentaNombrada()
                                        .getInstitucion()
                                        .getIdInstitucion())) {
                    dto.setParticipante(dto.getTraspasante());
                    dto.setContraparte(dto.getReceptor());
                }
            }
            // Saldo
            dto.setSaldoEfectivoDTO(crearSaldoEfectivoDTO(regContEfecNombrada.getSaldoNombradaHistorico()));
            // Instrucción de liquidación
            dto.setInstruccion(crearInstruccionLiquidacionHistorico(regContEfecNombrada.getOperacion().getInstruccion()));
            // Operación realizada
            dto.setOperacion(crearOperacionSaldoDTO(regContEfecNombrada.getOperacion()));
        }
        return dto;
    }

    /**
     * Crea un objeto del tipo {@link SaldoEfectivoDTO} a partir de un objeto
     * {@link SaldoNombrada}
     *
     * @param saldoNombrada Objeto de Hibernate para construir el DTO.
     * @return DTO creado a partir del parámetro de entrada.
     */
    public static SaldoEfectivoDTO crearSaldoEfectivoDTO(
            SaldoNombrada saldoNombrada) {
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
     * Crea un objeto del tipo {@link SaldoEfectivoDTO} a partir de un objeto
     * {@link SaldoNombrada}
     *
     * @param saldoNombrada Objeto de Hibernate para construir el DTO.
     * @return DTO creado a partir del parámetro de entrada.
     */
    public static SaldoEfectivoDTO crearSaldoEfectivoV2DTO(
            SaldoNombrada saldoNombrada) {
        SaldoEfectivoDTO saldoEfectivoDTO = new SaldoEfectivoDTO();
        saldoEfectivoDTO.setIdSaldo(saldoNombrada.getIdSaldo().longValue());

        // Divisa en que esta dado el saldo
        saldoEfectivoDTO.setDivisa(crearDivisaDTO(saldoNombrada.getDivisa()));
        // Boveda en donde se resguarda el saldo */
        saldoEfectivoDTO.setBoveda(crearBovedaDTO(saldoNombrada.getBoveda()));
        // Cuenta del saldo */
        saldoEfectivoDTO.setCuenta(crearCuentaEfectivoV2DTO(saldoNombrada
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
     * {@link SaldoNombrada}
     *
     * @param saldoControlada Objeto de Hibernate para construir el DTO.
     * @return DTO creado a partir del parámetro de entrada.
     */
    public static SaldoEfectivoDTO crearSaldoEfectivoDTO(SaldoControlada saldoControlada) {
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
     * @param saldoControlada Objeto de Hibernate para construir el DTO.
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
     * Crea un objeto de tipo {@link EstadoSaldoDTO} a partir de un objeto
     * {@link EstadoSaldoNombrada}
     *
     * @param estadoSaldoNombrada Objeto de Hibernate para construir el DTO.
     * @return DTO creado a partir del parámetro de entrada.
     */
    public static EstadoSaldoDTO crearEstadoSaldoDTO(
            EstadoSaldoNombrada estadoSaldoNombrada) {

        EstadoSaldoDTO estadoSaldoDTO = null;
        if (estadoSaldoNombrada != null) {
            estadoSaldoDTO = new EstadoSaldoDTO();
            estadoSaldoDTO.setIdEstadoSaldo(estadoSaldoNombrada
                    .getIdEstadoSaldoNombrada());
            estadoSaldoDTO.setSaldoFinalDisponible(estadoSaldoNombrada
                    .getSaldoFinalDisponible());
            estadoSaldoDTO.setSaldoFinalNoDisponible(estadoSaldoNombrada
                    .getSaldoFinalNoDisponible());
            estadoSaldoDTO.setSaldoInicialDisponible(estadoSaldoNombrada
                    .getSaldoInicialDisponible());
            estadoSaldoDTO.setSaldoInicialNoDisponible(estadoSaldoNombrada
                    .getSaldoInicialNoDisponible());
            estadoSaldoDTO.setSaldoFinal(new BigDecimal(estadoSaldoNombrada
                    .getSaldoFinalDisponible().doubleValue()
                    + estadoSaldoNombrada.getSaldoFinalNoDisponible()
                    .doubleValue()));
            estadoSaldoDTO.setSaldoInicial(new BigDecimal(estadoSaldoNombrada
                    .getSaldoInicialDisponible().doubleValue()
                    + estadoSaldoNombrada.getSaldoInicialNoDisponible()
                    .doubleValue()));
        }

        return estadoSaldoDTO;
    }

    /**
     * Crea un objeto de tipo {@link EstadoSaldoDTO} a partir de un objeto
     * {@link EstadoSaldoControlada}
     *
     * @param estadoSaldoControlada Objeto de Hibernate para construir el DTO.
     * @return DTO creado a partir del parámetro de entrada.
     */
    public static EstadoSaldoDTO crearEstadoSaldoDTO(
            EstadoSaldoControlada estadoSaldoControlada) {

        EstadoSaldoDTO estadoSaldoDTO = new EstadoSaldoDTO();

        estadoSaldoDTO.setIdEstadoSaldo(estadoSaldoControlada
                .getIdEstadoSaldoControlada());
        estadoSaldoDTO.setSaldoFinal(estadoSaldoControlada.getSaldoFinal());
        estadoSaldoDTO.setSaldoInicial(estadoSaldoControlada.getSaldoInicial());

        return estadoSaldoDTO;
    }

    /**
     * Crea un objeto del tipo {@link TipoOperacionDTO} a partir de un objeto de
     * hibernate del tipo {@link TipoOperacion}
     *
     * @param tipoOperacion
     *            Objeto de hibernate fuente para construir el DTO
     * @return DTO creao a partir del parámetro de entrada
     */
    // public static TipoOperacionDTO crearTipoOperacion(TipoOperacion
    // tipoOperacion) {
    //
    // TipoOperacionDTO tipoOperacionDTO = null;
    //
    // if (tipoOperacion != null) {
    // tipoOperacionDTO = new TipoOperacionDTO();
    // tipoOperacionDTO.setDescripcion(tipoOperacion.getDescripcion());
    // tipoOperacionDTO.setId(tipoOperacion.getIdTipoOperacion());
    // tipoOperacionDTO.setClaveTipoOperacion(tipoOperacion.getClaveTipoOperacion());
    // }
    // return tipoOperacionDTO;
    // }

    /**
     * Crea un objeto de tipo {@link TipoInstruccionDTO} a partir de un objeto de hibernate del tipo {@link TipoInstruccion}
     *
     * @param tipoInstruccion Objeto de hibernate fuente para construir el DTO
     * @return DTO creao a partir del parámetro de entrada
     */
    public static TipoInstruccionDTO crearTipoInstruccionDTO(TipoInstruccion tipoInstruccion) {
        TipoInstruccionDTO tipoInstruccionDTO = null;
        if (tipoInstruccion != null) {
            tipoInstruccionDTO = new TipoInstruccionDTO();
            tipoInstruccionDTO.setIdTipoInstruccion(tipoInstruccion.getIdTipoInstruccion().longValue());
            tipoInstruccionDTO.setDescripcion(tipoInstruccion.getInstruccion());
            tipoInstruccionDTO.setNombreCorto(tipoInstruccion.getNombreCorto());
            tipoInstruccionDTO.setInstruccion(tipoInstruccion.getDescripcion()); // Para no afectar otros modulos
        }
        return tipoInstruccionDTO;
    }

    /**
     * Crea un objeto de tipo {@link TipoLiquidacionDTO} a partir de un objeto
     * de hibernate del tipo {@link TipoLiquidacion}
     *
     * @param tipoLiquidacion Objeto de hibernate fuente para construir el DTO
     * @return DTO creao a partir del parámetro de entrada
     */
    public static TipoLiquidacionDTO crearTipoLiquidacionDTO(
            TipoLiquidacion tipoLiquidacion) {
        TipoLiquidacionDTO tipoLiquidacionDTO = null;
        if (tipoLiquidacion != null) {
            tipoLiquidacionDTO = new TipoLiquidacionDTO();
            tipoLiquidacionDTO.setTipoLiquidacion(tipoLiquidacion
                    .getTipoLiquidacion().toString());
            tipoLiquidacionDTO.setDescripcion(tipoLiquidacion.getDescripcion());
            tipoLiquidacionDTO.setNombreCorto(tipoLiquidacion.getNombreCorto()
                    .toString());
            tipoLiquidacionDTO.setIdTipoLiq(tipoLiquidacion.getIdTipoLiq());
        }
        return tipoLiquidacionDTO;
    }

    /**
     * Crea un objeto del tipo {@link CuponDTO} a partir de un objeto de
     * hibernate del tipo {@link Cupon}
     *
     * @param cupon
     *            Objeto de hibernate para construir el DTO
     * @return DTO creado a partir del parámetro de entrada
     */
    // public static CuponDTO crearCuponDTO(Cupon cupon) {
    //
    // CuponDTO cuponDTO = null;
    //
    // if (cupon != null) {
    //
    // cuponDTO = new CuponDTO();
    // cuponDTO.setClaveCupon(cupon.getClaveCupon());
    // cuponDTO.setIdCupon(cupon.getIdCupon());
    // }
    //
    // return cuponDTO;
    // }

    /**
     * Crea un objeto del tipo {@link EstadoInstruccionDTO} a partir de un
     * objeto de hibernate del tipo {@link EstadoInstruccionCat}
     *
     * @param bo Objeto de hibernate para construir el DTO
     * @return DTO creado a partir del parámetro de entrada
     */
    public static EstadoInstruccionDTO crearEstadoInstruccionDTO(
            EstadoInstruccionCat bo) {
        EstadoInstruccionDTO dto = null;
        if (bo != null) {
            dto = new EstadoInstruccionDTO();

            dto.setIdEstadoInstruccion(bo.getIdEstadoInstruccion().intValue());
            dto.setClaveEstadoInstruccion(bo.getClaveEstadoInstruccion());
            dto.setDescripcion(bo.getDescripcion());

        }
        return dto;
    }

    /**
     * Crea un objeto del tipo {@link TipoMensajeDTO} a partir de un objeto de
     * hibernate del tipo {@link TipoMensajeCat}
     *
     * @param bo Objeto de hibernate para construir el DTO
     * @return DTO creado a partir del parámetro de entrada
     */
    public static TipoMensajeDTO crearTipoMensajeDTO(TipoMensajeCat bo) {
        TipoMensajeDTO dto = null;
        if (bo != null) {
            dto = new TipoMensajeDTO();

            dto.setIdTipoMensaje(bo.getIdTipoMensaje().intValue());
            dto.setClaveTipoMensaje(bo.getClaveTipoMensaje());
            dto.setDescripcion(bo.getDescripcion());

        }
        return dto;
    }

    /**
     * Crea un objeto {@link ErrorDaliDTO} a partir de un objeto
     * {@link ErrorDali}
     *
     * @param bo el BO con los datos fuente.
     * @return el DTO generado.
     */
    public static ErrorDaliDTO crearErrorDaliDTO(ErrorDali bo) {
        ErrorDaliDTO errorDali = null;

        if (bo != null) {
            errorDali = new ErrorDaliDTO();
            errorDali.setClaveError(bo.getClaveError());
            errorDali.setDescripcion(bo.getDescripcion());
            errorDali.setIdErrorDali(bo.getIdErrorDali().intValue());
        }

        return errorDali;
    }

    /**
     * Crea un objeto del tipo {@link OperacionValorMatchDTO} a partir de un
     * objeto de hibernate del tipo {@link InstruccionMovPosicionNom}
     *
     * @param bo                   Objeto con los datos fuente
     * @param mensajeBean
     * @param participante         Datos de la institución participante para tomar decisiones al
     *                             momento de crear el DTO
     * @param incluirParcialidades Indica si debe crear un objeto por cada parcialidad asociada
     *                             con el registro.
     * @param idEstadoInstruccion  el identificador el estado de la instrucción solicitada. Si el
     *                             registro no tiene este estado de la instrucción no será
     *                             incluido.
     * @return DTO generado
     */
    public static Collection<OperacionValorMatchDTO> crearOperacionValorMatchDTO(
            InstruccionOperacionVal bo, MensajeBean mensajeBean,
            InstitucionDTO participante, boolean incluirParcialidades,
            int idEstadoInstruccion, int rol, boolean ignorarEstatus, Date fechaActual) {
        Collection<OperacionValorMatchDTO> operaciones = new ArrayList<OperacionValorMatchDTO>();
        List<OperacionValorMatchDTO> operacionesOriginales = new ArrayList<OperacionValorMatchDTO>();
        OperacionValorMatchDTO dto = null;
        if (bo != null) {
            dto = new OperacionValorMatchDTO();

            dto.setCantidadTitulos(bo.getCantidadTitulos().longValue());
            dto.setCuentaNombradaBancoTrabajo(DTOAssembler.crearCuentaDTO(bo
                    .getCuentaNombradaBancoTrabajo()));
            dto.setCuentaNombradaReceptora(DTOAssembler.crearCuentaDTO(bo
                    .getCuentaNombradaReceptora()));
            dto.setCuentaNombradaTraspasante(DTOAssembler.crearCuentaDTO(bo
                    .getCuentaNombradaTraspasante()));
            if (bo.getCupon() != null) {
                dto.setCupon(DTOAssembler.crearCuponDTO(bo.getCupon()));
            } else {
                dto.setCupon(DTOAssembler.crearCuponDTO(bo
                        .getEmisionPendiente()));
            }

            DivisaDTO divisaDTO = DTOAssembler.crearDivisaDTO(bo.getDivisa());
            dto.setDivisa(divisaDTO != null ? divisaDTO.getClaveAlfabetica() : StringUtils.EMPTY);

            BovedaDTO bovedaDTO = DTOAssembler.crearBovedaDTO(bo.getBoveda());
            dto.setBoveda(bovedaDTO != null ? bovedaDTO.getNombreCorto() : StringUtils.EMPTY);

            bovedaDTO = DTOAssembler.crearBovedaDTO(bo.getBovedaEfectivo());
            dto.setBovedaEfectivo(bovedaDTO != null ? bovedaDTO.getNombreCorto() : StringUtils.EMPTY);

            dto.setEstadoInstruccion(DTOAssembler.crearEstadoInstruccionDTO(bo.getEstadoInstruccionCat()));
            dto.setEstadoInstruccionEnvio(DTOAssembler.crearEstadoInstruccionDTO(bo.getEstadoInstruccionCat()));
            dto.setFechaConcertacion(bo.getFechaConcertacion());
            dto.setFechaIntereses(bo.getFechaIntereses());
            dto.setFechaLiquidacion(bo.getFechaLiquidacion());
            dto.setFolioControl(bo.getFolioControl().toString());
            dto.setFolioInstruccionReceptora(bo.getFolioInstruccionReceptora());
            dto.setFolioInstruccionTraspasante(bo.getFolioInstruccionTraspasante());
            // dto.setIdEmisionPendiente();
            dto.setIdInstruccionAnterior(bo.getIdInstruccionAnterior() != null ? bo
                    .getIdInstruccionAnterior().longValue()
                    : 0);
            dto.setIdInstruccionOperacionVal(bo
                    .getIdInstruccionOperacionVal() != null ? bo
                    .getIdInstruccionOperacionVal().longValue() : 0);
            dto.setIdInstruccionSiguiente(bo.getIdInstruccionSiguiente() != null ? bo
                    .getIdInstruccionSiguiente().longValue()
                    : 0);
            dto.setImporte(bo.getImporte().doubleValue());
            dto.setInteresesGenerados(bo.getInteresesGenerados() != null ? bo.getInteresesGenerados().doubleValue() : 0);
            if (bo.getInstitucionTraspasante() != null || bo.getInstitucionReceptora() != null) {
                dto.setOrigen((StringUtils.isNotBlank(bo
                        .getInstitucionTraspasante().getOrigen()) ? bo
                        .getInstitucionTraspasante().getOrigen() : "03 ")
                        + "|"
                        + (StringUtils.isNotBlank(bo.getInstitucionReceptora()
                        .getOrigen()) ? bo.getInstitucionReceptora()
                        .getOrigen() : " 03"));
            }
            if (bo.getOperacionMiscelaneaFiscal() != null) {
                dto.setMiscelaneaFiscal(DTOAssembler
                        .crearMiscelaneaFiscalDTO(bo
                                .getOperacionMiscelaneaFiscal()));
                dto.setConMiscelaneaFiscal(true);
            }

            dto.setErrorDali(crearErrorDaliDTO(bo.getErrorDali()));
            // dto.setOrigenReceptor();
            if (mensajeBean != null) {
                dto.setFechaReporto(mensajeBean.getFechaReporto());
            }
            dto.setMensajeXmlMatch(bo.getMensajeRecibido());
            if (bo.getPlazoReporto() != null) {
                dto.setPlazoReporto(bo.getPlazoReporto().longValue());

                if (dto.getFechaReporto() == null) {
                    Calendar cal = new GregorianCalendar();
                    cal.setTime(dto.getFechaLiquidacion());
                    cal.add(Calendar.DATE, dto.getPlazoReporto().intValue());
                    dto.setFechaReporto(cal.getTime());
                }
            }

            if (bo.getPrecioTitulo() != null) {
                dto.setPrecioTitulo(bo.getPrecioTitulo().doubleValue());
            }
            dto.setRequiereMatch(bo.getRequiereMatch());
            dto.setTasaNegociada(bo.getTasaNegociada() != null ? bo.getTasaNegociada().doubleValue() : 0);
            dto.setTasaReferencia(null);
            dto.setTipoInstruccion(DTOAssembler.crearTipoInstruccionDTO(bo.getTipoInstruccion()));
            if (bo.getCupon() != null) {
                dto.setEmision(DTOAssembler.crearEmisionDTO(bo.getCupon()
                        .getEmision()));
            } else {
                dto.setEmision(DTOAssembler.crearEmisionDTO(bo
                        .getEmisionPendiente()));
            }
            dto.setTipoMensaje(DTOAssembler.crearTipoMensajeDTO(bo
                    .getTipoMensajeCat()));
            dto.setInstitucionReceptora(DTOAssembler.crearInstitucionDTO(bo
                    .getInstitucionReceptora()));
            dto.setInstitucionTraspasante(DTOAssembler.crearInstitucionDTO(bo
                    .getInstitucionTraspasante()));
            //TODO por paquetes
            dto.setReferenciaPaquete(bo.getReferenciaPaquete());
            dto.setTotalOperacionesPaquete(bo.getTotalOperacionesPaquete());
            dto.setNumeroOperacionPaquete(bo.getNumeroOperacionPaquete());
            dto.setTotalTitulosPaquete(bo.getTotalTitulosPaquete());
            dto.setTotalImportePaquete(bo.getTotalImportePaquete());
            if ((dto.getReferenciaPaquete() != null && dto.getReferenciaPaquete().length() > 0 &&
                    dto.getFolioControl() != null && dto.getFolioControl().length() > 0)
                    && !(dto.getEstadoInstruccion().getClaveEstadoInstruccion().equals("SM")
                    || dto.getEstadoInstruccion().getClaveEstadoInstruccion().equals("EP")
                    || dto.getEstadoInstruccion().getClaveEstadoInstruccion().equals("FV"))
                    && !(dto.getEstadoInstruccion().getClaveEstadoInstruccion().equals("PE")
                    && dto.getFechaLiquidacion().after(fechaActual))) {
                dto.setPorPaquete(true);
            } else {
                dto.setPorPaquete(false);
            }
            if (bo.getInstitucionBancoTrabajo() != null) {
                dto.setInstitucionBancoTrabajo(DTOAssembler
                        .crearInstitucionDTO(bo.getInstitucionBancoTrabajo()));
                if (rol == RolConstants.ROL_AMBOS
                        || rol == RolConstants.ROL_RECEPTOR) {

                    try {
                        OperacionValorMatchDTO aux = (OperacionValorMatchDTO) BeanUtils
                                .cloneBean(dto);
                        if (participante != null
                                && participante.getId() > DaliConstants.VALOR_COMBO_TODOS
                                && participante.getId() == bo
                                .getIdInstitucionBancoTrabajo()
                                .longValue()) {
                            aux.setInstitucionReceptora(dto.getInstitucionBancoTrabajo());
                            aux.setCuentaNombradaReceptora(dto.getCuentaNombradaBancoTrabajo());
                            dto.setCuentaNombradaTraspasante(dto.getCuentaNombradaBancoTrabajo());

                            operacionesOriginales.add(aux);
                        } else {
                            aux.setInstitucionTraspasante(dto.getInstitucionBancoTrabajo());
                            aux.setCuentaNombradaTraspasante(dto.getCuentaNombradaBancoTrabajo());
                            dto.setCuentaNombradaReceptora(dto.getCuentaNombradaBancoTrabajo());
                            if (participante != null
                                    && participante.getId() > DaliConstants.VALOR_COMBO_TODOS
                                    && participante.getId() == bo
                                    .getIdInstitucionReceptora()
                                    .longValue()) {
                                operacionesOriginales.add(aux);
                            } else {
                                if (participante == null
                                        || participante.getId() == DaliConstants.VALOR_COMBO_TODOS) {
                                    operacionesOriginales.add(aux);
                                }
                            }
                        }
                    } catch (IllegalAccessException e) {
                        logger.error(e.getMessage(), e);
                        throw new InfrastructureException(e);
                    } catch (InstantiationException e) {
                        logger.error(e.getMessage(), e);
                        throw new InfrastructureException(e);
                    } catch (InvocationTargetException e) {
                        logger.error(e.getMessage(), e);
                        throw new InfrastructureException(e);
                    } catch (NoSuchMethodException e) {
                        logger.error(e.getMessage(), e);
                        throw new InfrastructureException(e);
                    }
                }
                if (rol == RolConstants.ROL_AMBOS
                        || rol == RolConstants.ROL_TRASPASANTE) {

                    try {
                        OperacionValorMatchDTO aux = (OperacionValorMatchDTO) BeanUtils
                                .cloneBean(dto);
                        if (participante != null
                                && participante.getId() > DaliConstants.VALOR_COMBO_TODOS
                                && participante.getId() == bo
                                .getIdInstitucionBancoTrabajo()
                                .longValue()) {
                            aux.setInstitucionTraspasante(dto
                                    .getInstitucionBancoTrabajo());
                            operacionesOriginales.add(aux);
                        } else {
                            aux.setInstitucionReceptora(dto
                                    .getInstitucionBancoTrabajo());
                            if (participante != null
                                    && participante.getId() > DaliConstants.VALOR_COMBO_TODOS
                                    && participante.getId() == bo
                                    .getIdInstitucionTraspasante()
                                    .longValue()) {
                                operacionesOriginales.add(aux);
                            } else {
                                if (participante == null
                                        || participante.getId() == DaliConstants.VALOR_COMBO_TODOS) {
                                    operacionesOriginales.add(aux);
                                }
                            }
                        }

                    } catch (IllegalAccessException e) {
                        logger.error(e.getMessage(), e);
                        throw new InfrastructureException(e);
                    } catch (InstantiationException e) {
                        logger.error(e.getMessage(), e);
                        throw new InfrastructureException(e);
                    } catch (InvocationTargetException e) {
                        logger.error(e.getMessage(), e);
                        throw new InfrastructureException(e);
                    } catch (NoSuchMethodException e) {
                        logger.error(e.getMessage(), e);
                        throw new InfrastructureException(e);
                    }
                }
            } else {
                operacionesOriginales.add(dto);
            }

            if (participante != null) {
                for (OperacionValorMatchDTO operacionOriginal : operacionesOriginales) {
                    operacionOriginal.setFolioUsuario(operacionOriginal
                            .getInstitucionReceptora().getId() == participante
                            .getId() ? bo.getFolioInstruccionReceptora() : bo
                            .getFolioInstruccionTraspasante());
                }
            }

            if (bo.getParcialidadesLiquidacion() != null
                    && !bo.getParcialidadesLiquidacion().isEmpty()) {
                for (OperacionValorMatchDTO operacionOriginal : operacionesOriginales) {
                    operacionOriginal.setTieneParcialidades(true);
                }

                if (incluirParcialidades) {
                    for (LiquidacionParcialInstruccionMov parcialidad : bo
                            .getParcialidadesLiquidacion()) {
                        try {
                            for (OperacionValorMatchDTO operacionOriginal : operacionesOriginales) {
                                OperacionValorMatchDTO aux = (OperacionValorMatchDTO) BeanUtils
                                        .cloneBean(operacionOriginal);
                                aux.setCantidadTitulos(parcialidad
                                        .getNumeroTitulos().longValue());
                                aux.setImporte(parcialidad.getImporte()
                                        .doubleValue());
                                aux
                                        .setFolioControl(parcialidad
                                                .getIdLiquidacionParcialInstruccionMov()
                                                .toString());
                                aux
                                        .setEstadoInstruccion(new EstadoInstruccionDTO());
                                aux.setFechaLiquidacion(parcialidad
                                        .getFechaHora());
                                aux
                                        .getEstadoInstruccion()
                                        .setClaveEstadoInstruccion(
                                                EstadoInstruccionConstants.CLAVE_EDO_INSTRUCCION_LIQUIDADA);
                                aux
                                        .getEstadoInstruccion()
                                        .setIdEstadoInstruccion(
                                                EstadoInstruccionConstants.INSTRUCCION_LIQUIDADA);
                                aux.setErrorDali(new ErrorDaliDTO());
                                aux.setFolioOrigen(operacionOriginal
                                        .getFolioControl());
                                aux.setTieneParcialidades(true);

                                operaciones.add(aux);
                            }
                        } catch (IllegalAccessException e) {
                            logger.error(e.getMessage(), e);
                            throw new InfrastructureException(e);
                        } catch (InstantiationException e) {
                            logger.error(e.getMessage(), e);
                            throw new InfrastructureException(e);
                        } catch (InvocationTargetException e) {
                            logger.error(e.getMessage(), e);
                            throw new InfrastructureException(e);
                        } catch (NoSuchMethodException e) {
                            logger.error(e.getMessage(), e);
                            throw new InfrastructureException(e);
                        }
                    }
                }
            }

        }
        for (OperacionValorMatchDTO operacionOriginal : operacionesOriginales) {
            if (ignorarEstatus
                    || idEstadoInstruccion == DaliConstants.VALOR_COMBO_TODOS
                    || operacionOriginal.getEstadoInstruccion()
                    .getIdEstadoInstruccion() == idEstadoInstruccion) {
                operaciones.add(operacionOriginal);
            }
        }
        return operaciones;

    }

    /**
     * Crea un objeto del tipo {@link OperacionValorMatchDTO} a partir de un
     * objeto de hibernate del tipo {@link InstruccionMovPosicionNom}
     *
     * @param bo                   Objeto con los datos fuente
     * @param mensajeBean
     * @param participante         Datos de la institución participante para tomar decisiones al
     *                             momento de crear el DTO
     * @param incluirParcialidades Indica si debe crear un objeto por cada parcialidad asociada
     *                             con el registro.
     * @param idEstadoInstruccion  el identificador el estado de la instrucción solicitada. Si el
     *                             registro no tiene este estado de la instrucción no será
     *                             incluido.
     * @return DTO generado
     */
    public static Collection<OperacionValorMatchDTO> crearOperacionValorMatchDTOParcialidades(
            InstruccionOperacionVal bo, MensajeBean mensajeBean,
            InstitucionDTO participante, boolean incluirParcialidades,
            int idEstadoInstruccion, int rol, boolean ignorarEstatus, Date fechaActual) {
        Collection<OperacionValorMatchDTO> operaciones = new ArrayList<OperacionValorMatchDTO>();
        List<OperacionValorMatchDTO> operacionesOriginales = new ArrayList<OperacionValorMatchDTO>();
        OperacionValorMatchDTO dto = null;
        if (bo != null) {
            dto = new OperacionValorMatchDTO();

            // dto.setBoveda()
            dto.setCantidadTitulos(bo.getCantidadTitulos().longValue());
            dto.setCuentaNombradaBancoTrabajo(DTOAssembler.crearCuentaDTO(bo
                    .getCuentaNombradaBancoTrabajo()));
            dto.setCuentaNombradaReceptora(DTOAssembler.crearCuentaDTO(bo
                    .getCuentaNombradaReceptora()));
            dto.setCuentaNombradaTraspasante(DTOAssembler.crearCuentaDTO(bo
                    .getCuentaNombradaTraspasante()));
            if (bo.getCupon() != null) {
                dto.setCupon(DTOAssembler.crearCuponDTO(bo.getCupon()));
            } else {
                dto.setCupon(DTOAssembler.crearCuponDTO(bo
                        .getEmisionPendiente()));
            }

            dto.setDivisaDTO(DTOAssembler.crearDivisaDTO(bo.getDivisa()));
            dto.setDivisa(dto.getDivisaDTO() != null ? dto.getDivisaDTO().getClaveAlfabetica() : StringUtils.EMPTY);

            dto.setBovedaDTO(DTOAssembler.crearBovedaDTO(bo.getBoveda()));
            dto.setBoveda(dto.getBovedaDTO() != null ? dto.getBovedaDTO().getNombreCorto() : StringUtils.EMPTY);

            dto.setBovedaEfectivoDTO(DTOAssembler.crearBovedaDTO(bo.getBovedaEfectivo()));
            dto.setBovedaEfectivo(dto.getBovedaEfectivoDTO() != null ? dto.getBovedaEfectivoDTO().getNombreCorto() : StringUtils.EMPTY);

            dto.setEstadoInstruccion(DTOAssembler.crearEstadoInstruccionDTO(bo
                    .getEstadoInstruccionCat()));
            dto.setEstadoInstruccionEnvio(DTOAssembler
                    .crearEstadoInstruccionDTO(bo.getEstadoInstruccionCat()));
            dto.setFechaConcertacion(bo.getFechaConcertacion());
            dto.setFechaIntereses(bo.getFechaIntereses());
            dto.setFechaLiquidacion(bo.getFechaLiquidacion());
            dto.setFolioControl(bo.getFolioControl().toString());
            dto.setFolioInstruccionReceptora(bo.getFolioInstruccionReceptora());
            dto.setFolioInstruccionTraspasante(bo
                    .getFolioInstruccionTraspasante());
            // dto.setIdEmisionPendiente();
            dto.setIdInstruccionAnterior(bo.getIdInstruccionAnterior() != null ? bo
                    .getIdInstruccionAnterior().longValue()
                    : 0);
            dto.setIdInstruccionOperacionVal(bo
                    .getIdInstruccionOperacionVal() != null ? bo
                    .getIdInstruccionOperacionVal().longValue() : 0);
            dto.setIdInstruccionSiguiente(bo.getIdInstruccionSiguiente() != null ? bo
                    .getIdInstruccionSiguiente().longValue()
                    : 0);
            dto.setImporte(bo.getImporte().doubleValue());
            dto.setInteresesGenerados(bo.getInteresesGenerados() != null ? bo
                    .getInteresesGenerados().doubleValue() : 0);
            if (bo.getInstitucionTraspasante() != null
                    || bo.getInstitucionReceptora() != null) {
                dto.setOrigen((StringUtils.isNotBlank(bo
                        .getInstitucionTraspasante().getOrigen()) ? bo
                        .getInstitucionTraspasante().getOrigen() : "03 ")
                        + "|"
                        + (StringUtils.isNotBlank(bo.getInstitucionReceptora()
                        .getOrigen()) ? bo.getInstitucionReceptora()
                        .getOrigen() : " 03"));
            }
            if (bo.getOperacionMiscelaneaFiscal() != null) {
                dto.setMiscelaneaFiscal(DTOAssembler
                        .crearMiscelaneaFiscalDTO(bo
                                .getOperacionMiscelaneaFiscal()));
                dto.setConMiscelaneaFiscal(true);
            }

            dto.setErrorDali(crearErrorDaliDTO(bo.getErrorDali()));
            // dto.setOrigenReceptor();
            if (mensajeBean != null) {
                dto.setFechaReporto(mensajeBean.getFechaReporto());
            }
            dto.setMensajeXmlMatch(bo.getMensajeRecibido());
            if (bo.getPlazoReporto() != null) {
                dto.setPlazoReporto(bo.getPlazoReporto().longValue());

                if (dto.getFechaReporto() == null) {
                    Calendar cal = new GregorianCalendar();
                    cal.setTime(dto.getFechaLiquidacion());
                    cal.add(Calendar.DATE, dto.getPlazoReporto().intValue());
                    dto.setFechaReporto(cal.getTime());
                }
            }

            if (bo.getPrecioTitulo() != null) {
                dto.setPrecioTitulo(bo.getPrecioTitulo().doubleValue());
            }
            dto.setRequiereMatch(bo.getRequiereMatch());
            dto.setTasaNegociada(bo.getTasaNegociada() != null ? bo
                    .getTasaNegociada().doubleValue() : 0);
            dto.setTasaReferencia(null);
            dto.setTipoInstruccion(DTOAssembler.crearTipoInstruccionDTO(bo
                    .getTipoInstruccion()));
            if (bo.getCupon() != null) {
                dto.setEmision(DTOAssembler.crearEmisionDTO(bo.getCupon()
                        .getEmision()));
            } else {
                dto.setEmision(DTOAssembler.crearEmisionDTO(bo
                        .getEmisionPendiente()));
            }
            dto.setTipoMensaje(DTOAssembler.crearTipoMensajeDTO(bo
                    .getTipoMensajeCat()));
            dto.setInstitucionReceptora(DTOAssembler.crearInstitucionDTO(bo
                    .getInstitucionReceptora()));
            dto.setInstitucionTraspasante(DTOAssembler.crearInstitucionDTO(bo
                    .getInstitucionTraspasante()));
            //TODO por paquetes
            dto.setReferenciaPaquete(bo.getReferenciaPaquete());
            dto.setTotalOperacionesPaquete(bo.getTotalOperacionesPaquete());
            dto.setNumeroOperacionPaquete(bo.getNumeroOperacionPaquete());
            dto.setTotalTitulosPaquete(bo.getTotalTitulosPaquete());
            dto.setTotalImportePaquete(bo.getTotalImportePaquete());
            if ((dto.getReferenciaPaquete() != null && dto.getReferenciaPaquete().length() > 0 &&
                    dto.getFolioControl() != null && dto.getFolioControl().length() > 0)
                    && !(dto.getEstadoInstruccion().getClaveEstadoInstruccion().equals("SM")
                    || dto.getEstadoInstruccion().getClaveEstadoInstruccion().equals("EP")
                    || dto.getEstadoInstruccion().getClaveEstadoInstruccion().equals("FV"))
                    && !(dto.getEstadoInstruccion().getClaveEstadoInstruccion().equals("PE")
                    && dto.getFechaLiquidacion().after(fechaActual))) {
                dto.setPorPaquete(true);
            } else {
                dto.setPorPaquete(false);
            }
            if (bo.getInstitucionBancoTrabajo() != null) {
                dto.setInstitucionBancoTrabajo(DTOAssembler
                        .crearInstitucionDTO(bo.getInstitucionBancoTrabajo()));
                if (rol == RolConstants.ROL_AMBOS
                        || rol == RolConstants.ROL_RECEPTOR) {

                    try {
                        OperacionValorMatchDTO aux = (OperacionValorMatchDTO) BeanUtils
                                .cloneBean(dto);
                        if (participante != null
                                && participante.getId() > DaliConstants.VALOR_COMBO_TODOS
                                && participante.getId() == bo
                                .getIdInstitucionBancoTrabajo()
                                .longValue()) {
                            aux.setInstitucionReceptora(dto
                                    .getInstitucionBancoTrabajo());
                            operacionesOriginales.add(aux);
                        } else {
                            aux.setInstitucionTraspasante(dto
                                    .getInstitucionBancoTrabajo());
                            if (participante != null
                                    && participante.getId() > DaliConstants.VALOR_COMBO_TODOS
                                    && participante.getId() == bo
                                    .getIdInstitucionReceptora()
                                    .longValue()) {
                                operacionesOriginales.add(aux);
                            } else {
                                if (participante == null
                                        || participante.getId() == DaliConstants.VALOR_COMBO_TODOS) {
                                    operacionesOriginales.add(aux);
                                }
                            }
                        }

                    } catch (IllegalAccessException e) {
                        logger.error(e.getMessage(), e);
                        throw new InfrastructureException(e);
                    } catch (InstantiationException e) {
                        logger.error(e.getMessage(), e);
                        throw new InfrastructureException(e);
                    } catch (InvocationTargetException e) {
                        logger.error(e.getMessage(), e);
                        throw new InfrastructureException(e);
                    } catch (NoSuchMethodException e) {
                        logger.error(e.getMessage(), e);
                        throw new InfrastructureException(e);
                    }
                }
                if (rol == RolConstants.ROL_AMBOS
                        || rol == RolConstants.ROL_TRASPASANTE) {

                    try {
                        OperacionValorMatchDTO aux = (OperacionValorMatchDTO) BeanUtils
                                .cloneBean(dto);
                        boolean agregar = Boolean.FALSE;
                        if (participante != null
                                && participante.getId() > DaliConstants.VALOR_COMBO_TODOS
                                && participante.getId() == bo
                                .getIdInstitucionBancoTrabajo()
                                .longValue()) {
                            aux.setInstitucionTraspasante(dto
                                    .getInstitucionBancoTrabajo());
                            operacionesOriginales.add(aux);
                        } else {
                            aux.setInstitucionReceptora(dto
                                    .getInstitucionBancoTrabajo());
                            if (participante != null
                                    && participante.getId() > DaliConstants.VALOR_COMBO_TODOS
                                    && participante.getId() == bo
                                    .getIdInstitucionTraspasante()
                                    .longValue()) {
                                operacionesOriginales.add(aux);
                            } else {
                                if (participante == null
                                        || participante.getId() == DaliConstants.VALOR_COMBO_TODOS) {
                                    for (OperacionValorMatchDTO operacionOriginal : operacionesOriginales) {
                                        if (!operacionOriginal
                                                .getFolioControl().equals(
                                                        aux.getFolioControl())) {
                                            agregar = Boolean.TRUE;
                                            break;
                                        }
                                    }
                                    if (agregar) {
                                        operacionesOriginales.add(aux);
                                    }
                                }
                            }
                        }

                    } catch (IllegalAccessException e) {
                        logger.error(e.getMessage(), e);
                        throw new InfrastructureException(e);
                    } catch (InstantiationException e) {
                        logger.error(e.getMessage(), e);
                        throw new InfrastructureException(e);
                    } catch (InvocationTargetException e) {
                        logger.error(e.getMessage(), e);
                        throw new InfrastructureException(e);
                    } catch (NoSuchMethodException e) {
                        logger.error(e.getMessage(), e);
                        throw new InfrastructureException(e);
                    }
                }
            } else {
                operacionesOriginales.add(dto);
            }

            if (participante != null) {
                for (OperacionValorMatchDTO operacionOriginal : operacionesOriginales) {
                    operacionOriginal.setFolioUsuario(operacionOriginal
                            .getInstitucionReceptora().getId() == participante
                            .getId() ? bo.getFolioInstruccionReceptora() : bo
                            .getFolioInstruccionTraspasante());
                }
            }

            if (bo.getParcialidadesLiquidacion() != null
                    && !bo.getParcialidadesLiquidacion().isEmpty()) {
                for (OperacionValorMatchDTO operacionOriginal : operacionesOriginales) {
                    operacionOriginal.setTieneParcialidades(true);
                }

                if (incluirParcialidades) {
                    for (LiquidacionParcialInstruccionMov parcialidad : bo
                            .getParcialidadesLiquidacion()) {
                        try {
                            for (OperacionValorMatchDTO operacionOriginal : operacionesOriginales) {
                                OperacionValorMatchDTO aux = (OperacionValorMatchDTO) BeanUtils
                                        .cloneBean(operacionOriginal);
                                aux.setCantidadTitulos(parcialidad
                                        .getNumeroTitulos().longValue());
                                aux.setImporte(parcialidad.getImporte()
                                        .doubleValue());
                                aux
                                        .setFolioControl(parcialidad
                                                .getIdLiquidacionParcialInstruccionMov()
                                                .toString());
                                aux
                                        .setEstadoInstruccion(new EstadoInstruccionDTO());
                                aux.setFechaLiquidacion(parcialidad
                                        .getFechaHora());
                                aux
                                        .getEstadoInstruccion()
                                        .setClaveEstadoInstruccion(
                                                EstadoInstruccionConstants.CLAVE_EDO_INSTRUCCION_LIQUIDADA);
                                aux
                                        .getEstadoInstruccion()
                                        .setIdEstadoInstruccion(
                                                EstadoInstruccionConstants.INSTRUCCION_LIQUIDADA);
                                aux.setErrorDali(new ErrorDaliDTO());
                                aux.setFolioOrigen(operacionOriginal
                                        .getFolioControl());
                                aux.setTieneParcialidades(true);

                                operaciones.add(aux);
                            }
                        } catch (IllegalAccessException e) {
                            logger.error(e.getMessage(), e);
                            throw new InfrastructureException(e);
                        } catch (InstantiationException e) {
                            logger.error(e.getMessage(), e);
                            throw new InfrastructureException(e);
                        } catch (InvocationTargetException e) {
                            logger.error(e.getMessage(), e);
                            throw new InfrastructureException(e);
                        } catch (NoSuchMethodException e) {
                            logger.error(e.getMessage(), e);
                            throw new InfrastructureException(e);
                        }
                    }
                }
            }

        }
        for (OperacionValorMatchDTO operacionOriginal : operacionesOriginales) {
            if (ignorarEstatus
                    || idEstadoInstruccion == DaliConstants.VALOR_COMBO_TODOS
                    || operacionOriginal.getEstadoInstruccion()
                    .getIdEstadoInstruccion() == idEstadoInstruccion) {
                operaciones.add(operacionOriginal);
            }
        }
        return operaciones;

    }

    /**
     * Crea un objeto {@link MiscelaneaFiscalDTO} a partir de un objeto
     * {@link OperacionMiscelaneaFiscal}.
     *
     * @param operacionMiscelaneaFiscal objeto de hibernate para construir el DTO
     * @return DTO creado a partir del parámetro del entrada.
     */
    private static MiscelaneaFiscalDTO crearMiscelaneaFiscalDTO(
            OperacionMiscelaneaFiscal operacionMiscelaneaFiscal) {

        MiscelaneaFiscalDTO dto = null;

        if (operacionMiscelaneaFiscal != null) {
            dto = new MiscelaneaFiscalDTO();
            dto
                    .setIdMiscelaneaFiscal(operacionMiscelaneaFiscal
                            .getIdMiscelaneaFiscal() != null ? operacionMiscelaneaFiscal
                            .getIdMiscelaneaFiscal().longValue()
                            : 0);
            dto.setCliente(operacionMiscelaneaFiscal.getCliente());
            dto.setCurpRfc(operacionMiscelaneaFiscal.getCurpRfc());
            dto.setFechaAdquisicion(operacionMiscelaneaFiscal
                    .getFechaAdquisicion());
            dto.setFechaLiquidacion(operacionMiscelaneaFiscal
                    .getFechaLiquidacion());
            dto
                    .setIdInstruccionOperacionVal(operacionMiscelaneaFiscal
                            .getIdInstruccionOperacionVal() != null ? operacionMiscelaneaFiscal
                            .getIdInstruccionOperacionVal().longValue()
                            : 0);
            dto.setPrecioAdquisicion(operacionMiscelaneaFiscal
                    .getPrecioAdquisicion() != null ? operacionMiscelaneaFiscal
                    .getPrecioAdquisicion().doubleValue() : 0.0);
            dto.setCostoPromedio(operacionMiscelaneaFiscal
                    .getCostoPromedio() != null ? operacionMiscelaneaFiscal
                    .getCostoPromedio().doubleValue() : 0.0);
        }

        return dto;
    }

    /**
     * Crea un objeto {@link CuponDTO} a partir de un objeto
     * {@link EmisionPendiente}
     *
     * @param emisionPendiente objeto de hibernate para construir el DTO
     * @return DTO creado a partir del parámetro del entrada.
     */
    private static CuponDTO crearCuponDTO(EmisionPendiente emisionPendiente) {

        CuponDTO cupon = null;

        if (emisionPendiente != null) {
            cupon = new CuponDTO();
            cupon.setClaveCupon(emisionPendiente.getCupon());
            cupon.setEmision(DTOAssembler.crearEmisionDTO(emisionPendiente));
        }

        return cupon;
    }

    /**
     * Crea un objeto {@link EmisionDTO} a partir de un objeto
     * {@link EmisionPendiente}
     *
     * @param emisionPendiente objeto de hibernate para construir el DTO
     * @return DTO creado a partir del parámetro del entrada.
     */
    private static EmisionDTO crearEmisionDTO(EmisionPendiente emisionPendiente) {

        EmisionDTO emision = null;

        if (emisionPendiente != null) {
            emision = new EmisionDTO();
            emision.setCupon(emisionPendiente.getCupon());
            emision.setSerie(new SerieDTO());
            emision.getSerie().setSerie(emisionPendiente.getSerie());
            emision.setEmisora(new EmisoraDTO());
            emision.getEmisora().setDescripcion(emisionPendiente.getEmisora());
            emision.setTipoValor(new TipoValorDTO());
            emision.getTipoValor().setClaveTipoValor(emisionPendiente.getTv());
            emision.setIsin(StringUtils.EMPTY);
        }

        return emision;
    }

    /**
     * Crea un objeto {@link InstruccionEfectivoDTO} en base a un objeto
     * {@link InstruccionEfectivo}.
     *
     * @param instruccionEfectivo Objeto de hibernate para construir el DTO
     * @return DTO creado a partir del parámetro de entrada
     */
    public static InstruccionEfectivoDTO crearInstruccionEfectivoDTO(
            InstruccionEfectivo instruccionEfectivo, Date fechaActual) {

        InstruccionEfectivoDTO dto = null;

        if (instruccionEfectivo != null) {

            dto = new InstruccionEfectivoDTO();
            // DETERMINA EL MODULO QUE LO CARGA. false = Efectivo True= Match
            dto.setModuloOrigenMatch(false);

            dto.setClabe(instruccionEfectivo.getClabe());
            dto.setNombreBeneficiario(instruccionEfectivo.getNombreBeneficiario());

            dto.setDivisa(instruccionEfectivo.getDivisa() == null ? null : instruccionEfectivo.getDivisa().getClaveAlfabetica());
            dto.setBoveda(instruccionEfectivo.getBoveda() == null ? null : instruccionEfectivo.getBoveda().getNombreCorto());

            if (instruccionEfectivo.getCuentaReceptora() != null) {
                dto.setCuentaReceptora(crearCuentaDTO(instruccionEfectivo
                        .getCuentaReceptora()));
            } else {
                dto.setCuentaReceptora(crearCuentaDTO(instruccionEfectivo
                        .getInstitucionReceptora()));
            }

            if (instruccionEfectivo.getCuentaTraspasante() != null) {
                dto.setCuentaTraspasante(crearCuentaDTO(instruccionEfectivo
                        .getCuentaTraspasante()));
            } else {
                dto.setCuentaTraspasante(crearCuentaDTO(instruccionEfectivo
                        .getInstitucionTraspasante()));
            }

            dto.setFechaLiquidacion(instruccionEfectivo.getFechaLiquidacion());
            dto.setFechaVencimiento(instruccionEfectivo.getFechaVencimiento());
            dto.setInstitucionReceptora(crearInstitucionDTO(instruccionEfectivo
                    .getInstitucionReceptora()));
            dto.setInstitucionTraspasante(crearInstitucionDTO(instruccionEfectivo
                    .getInstitucionTraspasante()));

            if (instruccionEfectivo.getMonto() != null) {
                dto.setMonto(instruccionEfectivo.getMonto());
            } else {
                dto.setMonto(new BigDecimal("0"));
            }

            if (instruccionEfectivo.getMontoVencimiento() != null) {
                dto.setMontoVencimiento(instruccionEfectivo
                        .getMontoVencimiento());
            } else {
                dto.setMontoVencimiento(new BigDecimal("0"));
            }

            if (instruccionEfectivo.getTasa() != null) {
                dto.setTasa(instruccionEfectivo.getTasa());
            } else {
                dto.setTasa(0.0);
            }

            if (StringUtils.isNotBlank(instruccionEfectivo
                    .getReferenciaOperacion())
                    && instruccionEfectivo.getReferenciaOperacion() != null) {
                dto.setReferenciaOperacion(instruccionEfectivo
                        .getReferenciaOperacion());
            } else {
                dto.setReferenciaOperacion("N/A");
            }

            if (instruccionEfectivo.getPlazo() != null) {
                dto.setPlazo(Double.valueOf(instruccionEfectivo.getPlazo())
                        .longValue());
            } else {
                dto.setPlazo(0L);
            }

            dto.setTipoInstruccion(crearTipoInstruccionDTO(instruccionEfectivo
                    .getTipoInstruccion()));

            dto.setTipoLiquidacion(crearTipoLiquidacionDTO(instruccionEfectivo
                    .getTipoLiquidacion()));
            dto.setLiquidacionVencimiento(crearTipoLiquidacionDTO(instruccionEfectivo
                    .getLiquidacionVencimiento()));

            dto.setTipoMensaje(new TipoMensajeDTO());
            dto.setTipoMensaje(crearTipoMensajeDTO(instruccionEfectivo
                    .getTipoMensajeCat()));
            // dto.getTipoMensaje().setClaveTipoMensaje(instruccionEfectivo.getIdTipoMensaje().toString());
            dto.setEstadoInstruccion(crearEstadoInstruccionDTO(instruccionEfectivo
                    .getEstadoInstruccion()));
            dto.setOrigen(StringUtils.isNotEmpty(instruccionEfectivo.getFolioOrigen()) ? instruccionEfectivo.getFolioOrigen() : "PORTAL");

            if (instruccionEfectivo.getTipoRetiro() != null) {
                dto.setTipoRetiro(instruccionEfectivo.getTipoRetiro().getNombreCorto());
                dto.setDescripcionTipoRetiro(instruccionEfectivo.getTipoRetiro().getDescripcion());
            }
            dto.setConcepto(instruccionEfectivo.getConcepto());
            dto.setClaveRastreo(instruccionEfectivo.getClaveRastreo());

            dto.setFechaAplicacion(instruccionEfectivo.getFechaAplicacion());

            dto.setReferenciaNumerica(instruccionEfectivo
                    .getReferenciaNumerica());
            dto.setFolioInstruccion(instruccionEfectivo.getFolioInstruccion());
            dto.setFolioInstLiquidacion(instruccionEfectivo.getFolioInstLiquidacion());
            dto.setDescripcionEstado(instruccionEfectivo.getDescripcionEstado());
            dto.setFolioOrigen(instruccionEfectivo.getFolioOrigen());
            dto.setLiqSpei(instruccionEfectivo.getLiqSpei());
            //TODO por paquetes
            dto.setReferenciaPaquete(instruccionEfectivo.getReferenciaPaquete());
            dto.setTotalOperacionesPaquete(instruccionEfectivo.getTotalOperacionesPaquete());
            dto.setNumeroOperacionPaquete(instruccionEfectivo.getNumeroOperacionPaquete());
            dto.setTotalTitulosPaquete(instruccionEfectivo.getTotalTitulosPaquete());
            dto.setTotalImportePaquete(instruccionEfectivo.getTotalImportePaquete());
            if ((dto.getReferenciaPaquete() != null && dto.getReferenciaPaquete().length() > 0)
                    && !(dto.getEstadoInstruccion().getClaveEstadoInstruccion().equals("SM")
                    || dto.getEstadoInstruccion().getClaveEstadoInstruccion().equals("EP")
                    || dto.getEstadoInstruccion().getClaveEstadoInstruccion().equals("FV"))
                    && !(dto.getEstadoInstruccion().getClaveEstadoInstruccion().equals("PE")
                    && dto.getFechaLiquidacion().after(fechaActual))) {
                dto.setPorPaquete(true);
            } else {
                dto.setPorPaquete(false);
            }

            if (instruccionEfectivo.getTipoPago() != null) {
                logger.info(
                        "*****Tipo de Retito: ["
                                + instruccionEfectivo.getTipoPago()
                                .getIdTipoPago() + "]");
                switch (instruccionEfectivo.getTipoPago().getIdTipoPago()) {
                    case TipoPagoConstants.TERCERO_A_TERCERO_SPEI:
                    case TipoPagoConstants.PARTICIPANTE_A_TERCERO_SPEI:
                    case TipoPagoConstants.PARTICIPANTE_A_PARTICIPANTE_SPEI:
                        dto.setTipoRetiro(TipoPagoConstants.DESCRIPCION_SPEI);
                        break;
                    case TipoPagoConstants.TRASPASO_FONDOS_SIAC:
                        dto.setTipoRetiro(TipoPagoConstants.DESCRIPCION_SIAC);
                        break;
                    case TipoPagoConstants.DEPOSITO_DEVOLUCION:
                        dto.setTipoRetiro(TipoPagoConstants.DESCRIPCION_DEVO);
                        break;
                    case TipoPagoConstants.DEPOSITO_OTRAS_DIVISAS:
                        dto.setTipoRetiro(TipoPagoConstants.DESCRIPCION_DIVI);
                        break;
                }
                dto.setDescripcionTipoRetiro(instruccionEfectivo.getTipoPago()
                        .getDescripcion());
            } else {
                logger.info(
                        "*****No hay tipo Retiro");
            }
        }
        return dto;
    }

    /**
     * Crea un objeto {@link InstruccionEfectivoDTO} en base a un objeto
     * {@link InstruccionEfectivo}.
     *
     * @param idTipoPago Objeto de hibernate para construir el DTO
     * @return DTO creado a partir del parámetro de entrada
     */
    public static InstruccionEfectivoDTO crearInstruccionEfectivoV2DTO(
            Integer idTipoPago) {

        InstruccionEfectivoDTO dto = new InstruccionEfectivoDTO();

        if (idTipoPago != null) {
            logger.info(
                    "*****Tipo de Retito: [" + idTipoPago + "]");
            switch (idTipoPago) {
                case TipoPagoConstants.TERCERO_A_TERCERO_SPEI:
                case TipoPagoConstants.PARTICIPANTE_A_TERCERO_SPEI:
                case TipoPagoConstants.PARTICIPANTE_A_PARTICIPANTE_SPEI:
                    dto.setTipoRetiro(TipoPagoConstants.DESCRIPCION_SPEI);
                    break;
                case TipoPagoConstants.TRASPASO_FONDOS_SIAC:
                    dto.setTipoRetiro(TipoPagoConstants.DESCRIPCION_SIAC);
                    break;
                case TipoPagoConstants.DEPOSITO_DEVOLUCION:
                    dto.setTipoRetiro(TipoPagoConstants.DESCRIPCION_DEVO);
                    break;
                case TipoPagoConstants.DEPOSITO_OTRAS_DIVISAS:
                    dto.setTipoRetiro(TipoPagoConstants.DESCRIPCION_DIVI);
                    break;
            }
        }

        return dto;
    }

    /**
     * Crea un objeto del tipo {@link OperacionValorMatchDTO} a partir de un
     * objeto de hibernate del tipo {@link MensajeBean}
     *
     * @param bo                     Objeto con los datos fuente
     * @param institucionReceptora   Institucion Traspasante
     * @param institucionTraspasante Institucion Receptora
     * @param cuentaTraspasante      Cuenta del trasapasante
     * @param cuentaReceptora        Cuenta del receptor
     * @param participante           Datos de la institución participante para tomar decisiones al
     *                               momento de crear el DTO
     * @return DTO generado
     */
    public static OperacionValorMatchDTO crearOperacionValorMatchDTO(
            MensajeBean bo, Institucion institucionTraspasante,
            Institucion institucionReceptora, CuentaNombrada cuentaReceptora,
            CuentaNombrada cuentaTraspasante, InstitucionDTO participante, Date fechaActual) {
        OperacionValorMatchDTO dto = null;
        if (bo != null) {
            dto = new OperacionValorMatchDTO();

            dto.setDivisa(bo.getDivisa());
            dto.setBoveda(bo.getBoveda());
            dto.setBovedaEfectivo(bo.getBovedaEfectivo());

            dto.setCantidadTitulos(NumberUtils.toLong(bo.getCantidadTitulos()));
            // dto.setCuentaNombradaBancoTrabajo(DTOAssembler.crearCuentaDTO(bo.getInstruccionOperacionVal().getCuentaNombradaBancoTrabajo()));

            dto.setCuentaNombradaReceptora(DTOAssembler
                    .crearCuentaDTO(cuentaReceptora));

            dto.setCuentaNombradaTraspasante(DTOAssembler
                    .crearCuentaDTO(cuentaTraspasante));

            dto.setCupon(new CuponDTO());
            dto.getCupon().setClaveCupon(bo.getCupon());

            dto.setEstadoInstruccion(new EstadoInstruccionDTO());
            dto.getEstadoInstruccion().setClaveEstadoInstruccion(bo.getEstadoMensajeString());

            if (StringUtils.isNotBlank(bo.getEstadoMensajeString()) &&
                    (bo.getEstadoMensajeString().equals(EstadoInstruccion.PRE_MATCH.toString()) ||
                            bo.getEstadoMensajeString().equals(EstadoInstruccion.POSIBLE_MATCH.toString()) ||
                            bo.getEstadoMensajeString().equals(EstadoInstruccion.SIN_MATCH.toString()))) {
                dto.getEstadoInstruccion().setClaveEstadoInstruccion(DaliConstants.ABREV_ESTATUS_SIN_MATCH);
            }

            if (StringUtils.isNotBlank(bo.getEstadoMensajeString())
                    && (bo.getEstadoMensajeString().equals(
                    EstadoInstruccion.CANCELADA_NO_REQUIERE_MATCH
                            .toString())
                    || bo.getEstadoMensajeString().equals(
                    EstadoInstruccion.CANCELAR_APLICADO
                            .toString())
                    || bo.getEstadoMensajeString().equals(
                    EstadoInstruccion.CANCELAR_NO_APLICADO
                            .toString()) || bo
                    .getEstadoMensajeString().equals(
                            EstadoInstruccion.CANCELADA.toString()))) {
                dto.getEstadoInstruccion().setClaveEstadoInstruccion(
                        DaliConstants.ABREV_ESTATUS_CANCELADA);
            }

            if (StringUtils.isNotBlank(bo.getTipoOperacionInstruccion()) && "M".equals(bo.getTipoOperacionInstruccion()) && StringUtils.isNotBlank(bo.getXml())) {
                dto.setMiscelaneaFiscal((MiscelaneaFiscalDTO) stream.fromXML(bo.getXml()));
                dto.setConMiscelaneaFiscal(true);
            }

            // dto.setEstadoInstruccion(DTOAssembler.crearEstadoInstruccionDTO(bo.getInstruccionOperacionVal().getEstadoInstruccionCat()));

            // dto.setEstadoInstruccionEnvio(DTOAssembler.crearEstadoInstruccionDTO(bo.getInstruccionOperacionVal().getEstadoInstruccionCat()));
            dto.setFechaConcertacion(bo.getFechaHoraRegistro());
            // dto.setFechaConcertacion(bo.getInstruccionOperacionVal().getFechaConcertacion());

            // dto.setFechaIntereses(bo.getInstruccionOperacionVal().getFechaIntereses());

            dto.setFechaLiquidacion(bo.getFechaLiquidacion());

            dto.setFolioControl(bo.getFolioControl());

            dto.setFolioInstruccionReceptora(bo.getFolioInstruccion());

            dto.setFolioInstruccionTraspasante(bo.getFolioInstruccion());
            // dto.setIdEmisionPendiente();
            // dto.setIdInstruccionAnterior(bo.getInstruccionOperacionVal().getIdInstruccionAnterior()!=null?bo.getInstruccionOperacionVal().getIdInstruccionAnterior():0);
            // dto.setIdInstruccionOperacionVal(bo.getInstruccionOperacionVal().getIdInstruccionOperacionVal()!=null?bo.getInstruccionOperacionVal().getIdInstruccionOperacionVal():0);
            // dto.setIdInstruccionSiguiente(bo.getInstruccionOperacionVal().getIdInstruccionSiguiente()!=null?bo.getInstruccionOperacionVal().getIdInstruccionSiguiente():0);
            dto.setIdBitacoraMatch(bo.getIdInstruccion());

            dto.setImporte(NumberUtils.toDouble(bo.getImporte()));

            // dto.setInstitucionBancoTrabajo(DTOAssembler.crearInstitucionDTO(bo.getInstruccionOperacionVal().getInstitucionBancoTrabajo()));
            dto.setInstitucionReceptora(DTOAssembler
                    .crearInstitucionDTO(institucionReceptora));

            dto.setInstitucionTraspasante(DTOAssembler
                    .crearInstitucionDTO(institucionTraspasante));

            // dto.setInstitucionReceptora(DTOAssembler.crearInstitucionDTO(bo.getInstruccionOperacionVal().getInstitucionReceptora()));
            // dto.setInstitucionTraspasante(DTOAssembler.crearInstitucionDTO(bo.getInstruccionOperacionVal().getInstitucionTraspasante()));

            // dto.setInteresesGenerados(bo.getInstruccionOperacionVal().getInteresesGenerados()!=null?bo.getInstruccionOperacionVal().getInteresesGenerados().doubleValue():0);
            dto.setOrigen(bo.getOrigen());
            // dto.setOrigenReceptor();

            if (bo.getPlazo() != null) {
                dto.setPlazoReporto(bo.getPlazo().longValue());
            }
            dto.setPrecioTitulo(NumberUtils.toDouble(bo.getPrecioTitulo()));

            // dto.setRequiereMatch(bo.getInstruccionOperacionVal().getRequiereMatch());

            dto.setTasaNegociada(NumberUtils.toDouble(bo.getTasaNegociada()));

            dto.setTasaReferencia(bo.getTasaReferencia());

            dto.setTipoInstruccion(new TipoInstruccionDTO());
            dto.getTipoInstruccion().setNombreCorto(bo.getTipoOperacionInstruccion());

            dto.setEmision(new EmisionDTO());

            dto.getEmision().setTipoValor(new TipoValorDTO());
            dto.getEmision().getTipoValor()
                    .setClaveTipoValor(bo.getTipoValor());
            dto.getEmision().getTipoValor().setMercado(new MercadoDTO());
            if (bo.getInstrumento() != null
                    && bo.getInstrumento().getIdMercado() != null) {
                dto.getEmision().getTipoValor().getMercado().setId(
                        bo.getInstrumento().getIdMercado().longValue());
            }
            dto.getEmision().setEmisora(new EmisoraDTO());
            dto.getEmision().getEmisora().setDescripcion(bo.getEmisora());
            dto.getEmision().setSerie(crearSerieDTO(bo.getSerie()));
            dto.setFechaReporto(bo.getFechaReporto());

            dto.setTipoMensaje(new TipoMensajeDTO());
            dto.getTipoMensaje().setClaveTipoMensaje(bo.getTipoMensaje());

            dto.setFolioUsuario(bo.getFolioInstruccion());
            dto.setMensajeXmlMatch(bo.getXml());
            //TODO por paquetes
            dto.setReferenciaPaquete(bo.getReferenciaPaquete());
            dto.setTotalOperacionesPaquete(bo.getTotalOperacionesPaquete());
            dto.setNumeroOperacionPaquete(bo.getNumeroOperacionPaquete());
            dto.setTotalTitulosPaquete(bo.getTotalTitulosPaquete());
            dto.setTotalImportePaquete(bo.getTotalImportePaquete());
            if ((dto.getReferenciaPaquete() != null && dto.getReferenciaPaquete().length() > 0 &&
                    dto.getFolioControl() != null && dto.getFolioControl().length() > 0)
                    && !(dto.getEstadoInstruccion().getClaveEstadoInstruccion().equals("SM")
                    || dto.getEstadoInstruccion().getClaveEstadoInstruccion().equals("EP")
                    || dto.getEstadoInstruccion().getClaveEstadoInstruccion().equals("FV"))
                    && !(dto.getEstadoInstruccion().getClaveEstadoInstruccion().equals("PE")
                    && dto.getFechaLiquidacion().after(fechaActual))) {
                dto.setPorPaquete(true);
            } else {
                dto.setPorPaquete(false);
            }
        }

        return dto;

    }

    /**
     * @param bo
     * @param fechaActual
     * @return
     * @author fernando vazquez ulloa- metodo para llever las operacion con
     * match del modulo de match.
     */
    public static InstruccionEfectivoDTO crearOperacionEfectivoByMensajeBean(
            MensajeBean bo, Date fechaActual) {
        InstruccionEfectivoDTO dto = null;
        if (bo != null) {
            dto = new InstruccionEfectivoDTO();
            // true = MATCH. Determina el modulo que lo carga False=efectivo.
            dto.setModuloOrigenMatch(true);

            dto.setConcepto("N/A");// No se usa
//			dto.setDivisa(bo.getDivisa());

            // ---Cargando la cuenta receptora
            CuentaDTO cuentaDTO = new CuentaDTO();
            cuentaDTO.setCuenta(bo.getCuentaReceptor());
            cuentaDTO.setDescripcion(bo.getIdFolioReceptor());// Se carga en la
            // descripcion
            // el folio del
            // receptor
            dto.setCuentaReceptora(cuentaDTO);// No se usa
            // ---------------

            // ---Cargando la cuenta traspasante
            cuentaDTO = new CuentaDTO();

            cuentaDTO.setCuenta(bo.getCuentaTraspasante());
            cuentaDTO.setDescripcion(bo.getIdFolioTraspasante());// Se carga en
            // la
            // descricion
            // el folio
            // del
            // traspasante
            dto.setCuentaTraspasante(cuentaDTO);// No se usa

            // -----------------

            dto.setDescripcionTipoRetiro(bo.getEstadoMensajeString());// ???
            dto.setIdInstruccion(bo.getIdInstruccion());// Establece el valor
            // del dato leido desde
            // el match

            // ------------------
            // Cargando el DTO de EstadoInstruccionDTO
            EstadoInstruccionDTO estadoInstruccionDTO = new EstadoInstruccionDTO();
            estadoInstruccionDTO.setClaveEstadoInstruccion(bo
                    .getEstadoMensajeString());
            estadoInstruccionDTO.setDescripcion(bo.getEstadoMensajeString());
            estadoInstruccionDTO.setIdEstadoInstruccion(0);
            dto.setEstadoInstruccion(estadoInstruccionDTO);

            // -----------------
            dto.setFechaLiquidacion(bo.getFechaLiquidacion());

            dto.setFechaVencimiento(bo.getFechaReporto());

            dto.setFechaConcertacion(bo.getFechaHoraRegistro());

            if (bo.getFolioControl() != null) {

                dto.setFolioInstruccion(new BigInteger("0"
                        + bo.getFolioControl()));
            } else {

                dto.setFolioInstruccion(BigInteger.ZERO);
            }

            // ---------------------------
            // Cargando el dto de InstitucionDTO
            InstitucionDTO institucionDTO = new InstitucionDTO();
            institucionDTO.setId(Long.valueOf(bo.getIdFolioReceptor()));
            institucionDTO.setNombreCorto("");
            institucionDTO.setRazonSocial("");
            if (bo.getIdFolioReceptor().length() >= 5) {
                institucionDTO.setFolioInstitucion(bo.getIdFolioReceptor()
                        .substring(2, 5));
                institucionDTO.setClaveTipoInstitucion(bo.getIdFolioReceptor()
                        .substring(0, 2));
            } else {
                institucionDTO.setFolioInstitucion("");
                institucionDTO.setClaveTipoInstitucion("");
            }
            institucionDTO.setClaveCasfim("");
            institucionDTO.setCuentaClabe("");
            dto.setInstitucionReceptora(institucionDTO);

            // ---------------------------

            // ---------------------------
            // Cargando el dto de InstitucionDTO
            institucionDTO = new InstitucionDTO();
            institucionDTO.setId(Long.valueOf(bo.getIdFolioTraspasante()));
            institucionDTO.setNombreCorto("");
            institucionDTO.setRazonSocial("");

            if (bo.getIdFolioTraspasante().length() >= 5) {
                institucionDTO.setFolioInstitucion(bo.getIdFolioTraspasante()
                        .substring(2, 5));
                institucionDTO.setClaveTipoInstitucion(bo
                        .getIdFolioTraspasante().substring(0, 2));
            } else {
                institucionDTO.setFolioInstitucion("");
                institucionDTO.setClaveTipoInstitucion("");
            }

            institucionDTO.setClaveCasfim("");
            institucionDTO.setCuentaClabe("");
            dto.setInstitucionTraspasante(institucionDTO);

            // ---------------------------

            if (bo.getImporte() != null) {
                dto.setMonto(new BigDecimal("0" + bo.getImporte()));
            } else {
                dto.setMonto(BigDecimal.ZERO);
            }

//comentando TEFV
//			if (bo.getImporteIntereses() != null) {
//				dto.setMontoVencimiento(new BigDecimal("0"
//						+ bo.getImporteIntereses()));
//			} else {
//				dto.setMontoVencimiento(new BigDecimal("0"));
//			}

            dto.setOrigen("N/A");

            if (bo.getPlazo() != null) {

                dto.setPlazo(bo.getPlazo().longValue());
            } else {

                dto.setPlazo(0L);
            }
            // --------------------------------------------------------------
            if (bo.getFolioInstruccion() != null
                    && NumberUtils.isNumber(bo.getFolioInstruccion())) {
                dto.setReferenciaOperacion(bo.getFolioInstruccion());// Referencia
                // operacion.
            } else {

                dto.setReferenciaOperacion(""); // Referencia Numerica N/A
            }
            // -----------------------------------------
//			dto.setReferenciaNumerica(Integer.valueOf(0));
            dto.setReferenciaNumerica("0");
            // ----------------------------------------
            if (bo.getTasaNegociada() != null) {
                dto.setTasa(Double.valueOf("0" + bo.getTasaNegociada()));
            } else {

                dto.setTasa(Double.valueOf("0"));
            }
            // -------------------------------------------

            dto.setReferenciaParticipante("" + bo.getReferenciaParticipante());

            // -------------------------------------
            // cargando el tipo TipoInstruccionDTO()
            TipoInstruccionDTO tipoInstruccionDTO = new TipoInstruccionDTO();
            tipoInstruccionDTO.setDescripcion(bo.getTipoOperacionInstruccion());
            tipoInstruccionDTO.setIdTipoInstruccion(0L);
            tipoInstruccionDTO.setInstruccion(bo.getTipoOperacionInstruccion());
            tipoInstruccionDTO.setNombreCorto(bo.getTipoOperacionInstruccion());
            dto.setTipoInstruccion(tipoInstruccionDTO);
            // -----------------------------------------

            // -------------------------------------
            // cargando el tipo TipoLiquidacionDTO() inicio
            TipoLiquidacionDTO tipoLiquidacionDTO = new TipoLiquidacionDTO();
            tipoLiquidacionDTO.setIdTipoLiq(new BigInteger("0"));
            StringBuffer aplicar = new StringBuffer("");

//		    comentando TEFV
//			if (bo.getTipoLiquidacionInicio()!= null){
//				if (bo.getTipoLiquidacionInicio().trim().equals("R")) {
//					aplicar = new StringBuffer("REGISTRO");
//				} else if (bo.getTipoLiquidacionInicio().trim().equals("D")) {
//					aplicar = new StringBuffer("DALI");
//				} else if (bo.getTipoLiquidacionInicio().trim().equals("S")) {
//					aplicar = new StringBuffer("SPEI");
//				} else {
//					aplicar = new StringBuffer("N/A");
//				}
//			}else {
//				aplicar = new StringBuffer("N/A");
//			}

            tipoLiquidacionDTO.setDescripcion(aplicar.toString());
            tipoLiquidacionDTO.setNombreCorto(aplicar.toString());

//		    comentando TEFV
//			tipoLiquidacionDTO
//					.setTipoLiquidacion(bo.getTipoLiquidacionInicio());
            dto.setTipoLiquidacion(tipoLiquidacionDTO);
            // -----------------------------------------

            // --------------------------------
            // Cargando el tipo TipoLiquidacionDTO vencimiento
            tipoLiquidacionDTO = new TipoLiquidacionDTO();
            tipoLiquidacionDTO.setIdTipoLiq(new BigInteger("0"));

//		    comentando TEFV
//			if (bo.getTipoLiquidacionVencimiento() != null){
//				if (bo.getTipoLiquidacionVencimiento().trim().equals("R")) {
//					aplicar = new StringBuffer("REGISTRO");
//				} else if (bo.getTipoLiquidacionVencimiento().trim().equals("D")) {
//					aplicar = new StringBuffer("DALI");
//				} else if (bo.getTipoLiquidacionVencimiento().trim().equals("S")) {
//					aplicar = new StringBuffer("SPEI");
//				} else {
//					aplicar = new StringBuffer("N/A");
//				}
//			} else {
//				aplicar = new StringBuffer("N/A");
//			}

            tipoLiquidacionDTO.setDescripcion(aplicar.toString());
            tipoLiquidacionDTO.setNombreCorto(aplicar.toString());
//		    comentando TEFV
//			tipoLiquidacionDTO.setTipoLiquidacion(bo
//					.getTipoLiquidacionVencimiento());
            dto.setLiquidacionVencimiento(tipoLiquidacionDTO);
            // ------------------------------


            // --------------------------------
            // Cargando el tipo TipoLiquidacionDTO vencimiento
            TipoMensajeDTO tipoMensajeDTO = new TipoMensajeDTO();
            tipoMensajeDTO.setClaveTipoMensaje(bo.getTipoMensaje());
            tipoMensajeDTO.setDescripcion(bo.getTipoMensaje());
            tipoMensajeDTO.setIdTipoMensaje(0);
            dto.setTipoMensaje(tipoMensajeDTO);
            // ------------------------------
            //TODO por paquetes
            dto.setReferenciaPaquete(bo.getReferenciaPaquete());
            dto.setTotalOperacionesPaquete(bo.getTotalOperacionesPaquete());
            dto.setNumeroOperacionPaquete(bo.getNumeroOperacionPaquete());
            dto.setTotalTitulosPaquete(bo.getTotalTitulosPaquete());
            dto.setTotalImportePaquete(bo.getTotalImportePaquete());
            if ((dto.getReferenciaPaquete() != null && dto.getReferenciaPaquete().length() > 0)
                    && !(dto.getEstadoInstruccion().getClaveEstadoInstruccion().equals("SM")
                    || dto.getEstadoInstruccion().getClaveEstadoInstruccion().equals("EP")
                    || dto.getEstadoInstruccion().getClaveEstadoInstruccion().equals("FV"))
                    && !(dto.getEstadoInstruccion().getClaveEstadoInstruccion().equals("PE")
                    && dto.getFechaLiquidacion().after(fechaActual))) {
                dto.setPorPaquete(true);
            } else {
                dto.setPorPaquete(false);
            }
            dto.setTipoRetiro("N/A");
        }

        return dto;

    }


    /**
     * @param bo
     * @return
     * @author fernando vazquez ulloa- metodo para llevar las operacion de retiros de efectivo
     * sin liberacion del modulo de internacional.
     * 2009 - 11 - 09
     */

    public static RetiroEfectivoInternacionalDTO creaRetiroEfectivoInternacionalDTOByRetiroEfectivoInternacional(
            RetiroEfectivoInternacional bo) {
        RetiroEfectivoInternacionalDTO dto = null;
        if (bo != null) {
            dto = new RetiroEfectivoInternacionalDTO();
            // Origen -------------------

            if (bo.getEstado().getIdEstadoInstruccion().equals(ESTADO_REGISTRADO)) {
                dto.setModuloOrigen("aprobacion");
            } else if (bo.getEstado().getIdEstadoInstruccion().equals(ESTADO_AUTORIZADO)) {
                dto.setModuloOrigen("liberacion");
            } else {
                dto.setModuloOrigen("N/A");
            }

            dto.setIdRetiroEfectivoInt(BigInteger.valueOf(bo.getIdRetiroEfectivoInt()));
            dto.setIdCuentaBeneficiario(bo.getIdCuentaBeneficiario());
            //----------------------------
            InstitucionDTO institucionDTO = new InstitucionDTO();
            if (bo.getInstitucion() != null) {
                institucionDTO.setId(bo.getInstitucion().getIdInstitucion().longValue());
                institucionDTO.setNombreCorto(bo.getInstitucion().getNombreCorto());
                institucionDTO.setRazonSocial(bo.getInstitucion().getRazonSocial());
                institucionDTO.setFolioInstitucion(bo.getInstitucion().getFolioInstitucion());

                institucionDTO.setCuentaClabe(bo.getInstitucion().getCuentaClabe());
                institucionDTO.setClaveCasfim(bo.getInstitucion().getClaveCasfim());

                institucionDTO.setClaveTipoInstitucion(bo.getInstitucion().getTipoInstitucion().getClaveTipoInstitucion());
            } else {
                institucionDTO.setId(0);
                institucionDTO.setNombreCorto(null);
                institucionDTO.setRazonSocial(null);
                institucionDTO.setFolioInstitucion(null);
                institucionDTO.setClaveTipoInstitucion(null);
            }
            dto.setInstitucion(institucionDTO);

            //-------------------------------------------------
            dto.setReferenciaOperacion(null);
            dto.setImporteTraspaso(bo.getImporteTraspaso());
            //agregando la divisa --------
            DivisaDTO divisaDTO = new DivisaDTO();
            if (bo.getDivisa() != null) {
                divisaDTO.setId(bo.getDivisa().getIdDivisa().longValue());
                divisaDTO.setDescripcion(bo.getDivisa().getDescripcion());
                divisaDTO.setClaveAlfabetica(bo.getDivisa().getClaveAlfabetica());
                divisaDTO.setClaveNumerica(bo.getDivisa().getClaveNumerica());
            } else {
                divisaDTO.setId(0);
                divisaDTO.setDescripcion("");
                divisaDTO.setClaveAlfabetica("");
                divisaDTO.setClaveNumerica("0");
            }
            dto.setDivisa(divisaDTO);
            //agregando la boveda --------
            BovedaDTO bovedaDTO = new BovedaDTO();
            if (bo.getBoveda() != null) {
                bovedaDTO.setId(bo.getBoveda().getIdBoveda().longValue());
                bovedaDTO.setDescripcion(bo.getBoveda().getDescripcion());
                bovedaDTO.setClaveTipoBoveda(bo.getBoveda().getTipoBoveda().getClaveTipoBoveda());
                bovedaDTO.setNombreCorto(bo.getBoveda().getNombreCorto());
            } else {
                bovedaDTO.setId(0);
                bovedaDTO.setDescripcion("");
                bovedaDTO.setNombreCorto("");
                bovedaDTO.setClaveTipoBoveda("");
            }
            dto.setBoveda(bovedaDTO);
            //---------------------------
            //aparentemente nadie esta ocupando el valor de este dato cargado desde aqui
            //dto.setCuentaBeneficiarioDTO( DTOAssembler.setCuentaRetiroInternacionalDTOByCRI(bo.getCuentaBeneficiario()));

            //---------------------------


            dto.setFechaValor(bo.getFechaValor());
            dto.setFechaLiquidacion(null);
            dto.setFechaLiberacion(bo.getFechaLiberacion());
            dto.setFechaEnvio(bo.getFechaEnvio());
            dto.setFechaAutorizacion(bo.getFechaAutorizacion());
            dto.setConceptoPago(bo.getConceptoPago());
            //agregando el estado --------
            EstadoInstruccionCatDTO estadoInstruccionCatDTO = new EstadoInstruccionCatDTO();
            if (bo.getEstado() != null) {
                estadoInstruccionCatDTO.setClaveEstadoInstruccion(bo.getEstado().getClaveEstadoInstruccion());
                estadoInstruccionCatDTO.setDescripcion(bo.getEstado().getDescripcion());
                estadoInstruccionCatDTO.setIdEstadoInstruccion(bo.getEstado().getIdEstadoInstruccion());
            } else {
                estadoInstruccionCatDTO.setClaveEstadoInstruccion("");
                estadoInstruccionCatDTO.setDescripcion("");
                estadoInstruccionCatDTO.setIdEstadoInstruccion(BigInteger.ZERO);
            }
            dto.setEstado(estadoInstruccionCatDTO);

        }

        return dto;


    }


    /**
     * @param bo
     * @return
     * @author fernando vazquez ulloa- metodo para llevar las operacion de retiros de efectivo
     * sin liberacion del modulo de internacional.
     * 2009 - 11 - 09
     */
    public static InstruccionEfectivoDTO setOperacionEfectivoByOperacionEfectivoIntl(
            RetiroEfectivoInternacional bo) {
        InstruccionEfectivoDTO dto = null;
        if (bo != null) {
            dto = new InstruccionEfectivoDTO();
            // true = MATCH. Determina el modulo que lo carga False=efectivo.
            dto.setModuloOrigenMatch(false);

            if (bo.getEstado().getIdEstadoInstruccion().equals(ESTADO_REGISTRADO)) {
                dto.setModuloOrigen("aprobacion");
            } else if (bo.getEstado().getIdEstadoInstruccion().equals(ESTADO_AUTORIZADO)) {
                dto.setModuloOrigen("liberacion");
            } else {
                dto.setModuloOrigen("N/A");
            }
            dto.setIdInstruccion(BigInteger.valueOf(bo.getIdRetiroEfectivoInt()));

            dto.setConcepto(bo.getConceptoPago());
            // ---Cargando la cuenta receptora
            CuentaDTO cuentaDTO = new CuentaDTO();
            //no importa que valor se ponga, lineas adelante dice "no se usa" :s
            cuentaDTO.setCuenta("" + bo.getIdCuentaBeneficiario());
            cuentaDTO.setDescripcion("");// Se carga en la
            // descripcion
            // el folio del
            // receptor
            dto.setCuentaReceptora(cuentaDTO);// No se usa
            cuentaDTO = new CuentaDTO();
            dto.setCuentaTraspasante(cuentaDTO);// No se usa
            dto.setDescripcionTipoRetiro(null);
            dto.setIdInstruccion(BigInteger.valueOf(bo.getIdRetiroEfectivoInt()));// ID de la tabla leido
            // ------------------
            // Cargando el DTO de EstadoInstruccionDTO
            EstadoInstruccionDTO estadoInstruccionDTO = new EstadoInstruccionDTO();
            estadoInstruccionDTO.setClaveEstadoInstruccion(null);
            estadoInstruccionDTO.setDescripcion(null);
            estadoInstruccionDTO.setIdEstadoInstruccion(0);
            dto.setEstadoInstruccion(estadoInstruccionDTO);

            // -----------------
            dto.setFechaLiquidacion(null);
            dto.setFechaVencimiento(null);
            dto.setFechaConcertacion(null);
            dto.setFolioInstruccion(BigInteger.ZERO);
            // ---------------------------
            // Cargando el dto de InstitucionDTO
            InstitucionDTO institucionDTO = new InstitucionDTO();
            dto.setInstitucionReceptora(institucionDTO);
            // ---------------------------
            // Cargando el dto de InstitucionDTO
            institucionDTO = new InstitucionDTO();
            institucionDTO.setId(bo.getInstitucion().getIdInstitucion().longValue());
            institucionDTO.setNombreCorto("");
            institucionDTO.setRazonSocial("");
            institucionDTO.setFolioInstitucion(bo.getInstitucion().getFolioInstitucion());
            institucionDTO.setClaveTipoInstitucion(bo.getInstitucion().getTipoInstitucion().getClaveTipoInstitucion());
            institucionDTO.setClaveCasfim("");
            institucionDTO.setCuentaClabe("");
            dto.setInstitucionTraspasante(institucionDTO);
            // ---------------------------
            if (bo.getImporteTraspaso() != null) {
                dto.setMonto(bo.getImporteTraspaso());
            } else {
                dto.setMonto(BigDecimal.ZERO);
            }

            dto.setMontoVencimiento(BigDecimal.ZERO);
            dto.setOrigen("N/A");
            dto.setPlazo(0L);
            // --------------------------------------------------------------

            dto.setReferenciaOperacion("N/A"); // Referencia Numerica N/A
            // -----------------------------------------
//			dto.setReferenciaNumerica(Integer.valueOf(0));
            dto.setReferenciaNumerica("0");
            // ----------------------------------------
            dto.setTasa(Double.valueOf("0"));
            // -------------------------------------
            // cargando el tipo TipoInstruccionDTO()
            TipoInstruccionDTO tipoInstruccionDTO = new TipoInstruccionDTO();
            dto.setTipoInstruccion(tipoInstruccionDTO);
            // -------------------------------------
            // cargando el tipo TipoLiquidacionDTO() inicio
            TipoLiquidacionDTO tipoLiquidacionDTO = new TipoLiquidacionDTO();
            dto.setTipoLiquidacion(tipoLiquidacionDTO);
            // --------------------------------
            // Cargando el tipo TipoLiquidacionDTO vencimiento
            tipoLiquidacionDTO = new TipoLiquidacionDTO();
            dto.setLiquidacionVencimiento(tipoLiquidacionDTO);
            // --------------------------------
            // Cargando el tipo TipoLiquidacionDTO vencimiento
            TipoMensajeDTO tipoMensajeDTO = new TipoMensajeDTO();
            dto.setTipoMensaje(tipoMensajeDTO);
            // ------------------------------
            dto.setTipoRetiro("N/A");
        }
        return dto;

    }


    /**
     * @param bo
     * @return
     * @author fernando vazquez ulloa- metodo para llevar las operacion de retiros de efectivo
     * sin liberacion del modulo de internacional.
     * 2009 - 11 - 09
     */
    public static InstruccionEfectivoDTO setOperacionEfectivoByOperacionEfectivoNal(
            RetiroEfectivo bo) {
        InstruccionEfectivoDTO dto = null;
        if (bo != null) {
            dto = new InstruccionEfectivoDTO();
            // true = MATCH. Determina el modulo que lo carga False=efectivo.
            dto.setModuloOrigenMatch(false);

            dto.setDivisa(bo.getDivisa() == null ? null : bo.getDivisa().getClaveAlfabetica());
            dto.setBoveda(bo.getBoveda() == null ? null : bo.getBoveda().getNombreCorto());

            if (bo.getEstado().getIdEstadoInstruccion().equals(ESTADO_REGISTRADO)) {
                dto.setModuloOrigen("c_retiro_efectivo_nal_aprobacion");
            } else if (bo.getEstado().getIdEstadoInstruccion().equals(ESTADO_AUTORIZADO)) {
                dto.setModuloOrigen("c_retiro_efectivo_nal_liberacion");
            } else {
                dto.setModuloOrigen("c_retiro_efectivo_nal_NA");
            }

            // ---Cargando la cuenta RECEPTORA
            CuentaDTO cuentaDTO = new CuentaDTO();
            cuentaDTO.setCuenta(bo.getCuentaBeneficiario());
            cuentaDTO.setNombreCuenta(bo.getIdInstReceptor().getNombreCorto());
            cuentaDTO.setDescripcion("");// Se carga en la descripcion el folio del receptor
            dto.setCuentaReceptora(cuentaDTO);// No se usa
            // ---Cargando la cuenta TRASPASANTE
            dto.setCuentaTraspasante(new CuentaDTO());// No se usa

            dto.setDescripcionTipoRetiro(null);
            dto.setIdInstruccion(BigInteger.valueOf(bo.getIdRetiroEfectivoNal()));// ID de la tabla leido

            // dto.setIdCuentaBeneficiario(bo.getIdCuentaBeneficiario());

            // ------------------
            // Cargando el DTO de EstadoInstruccionDTO
            dto.setEstadoInstruccion(DTOAssembler.crearEstadoInstruccionDTO(bo.getEstado()));

            // -----------------
            dto.setFechaAutorizacion(bo.getFechaAutorizacion());
            dto.setFechaLiberacion(bo.getFechaLiberacion());
            dto.setFechaLiquidacion(null);
            dto.setFechaVencimiento(null);
            dto.setFechaConcertacion(null);
            dto.setFolioInstruccion(null);
            // ---------------------------
            // Cargando el dto de InstitucionDTO
            InstitucionDTO institucionDTO = new InstitucionDTO();
            institucionDTO.setId(bo.getIdInstReceptor().getIdInstitucion().longValue());
            institucionDTO.setNombreCorto("");
            institucionDTO.setRazonSocial("");
            institucionDTO.setFolioInstitucion(bo.getIdInstReceptor().getFolioInstitucion());
            institucionDTO.setClaveTipoInstitucion(bo.getIdInstReceptor().getTipoInstitucion().getClaveTipoInstitucion());
            institucionDTO.setClaveCasfim("");
            institucionDTO.setCuentaClabe("");
            dto.setInstitucionReceptora(institucionDTO);
            // ---------------------------
            // Cargando el dto de InstitucionDTO
            institucionDTO = new InstitucionDTO();
            institucionDTO.setId(bo.getInstitucion().getIdInstitucion().longValue());
            institucionDTO.setNombreCorto("");
            institucionDTO.setRazonSocial("");
            institucionDTO.setFolioInstitucion(bo.getInstitucion().getFolioInstitucion());
            institucionDTO.setClaveTipoInstitucion(bo.getInstitucion().getTipoInstitucion().getClaveTipoInstitucion());
            institucionDTO.setClaveCasfim("");
            institucionDTO.setCuentaClabe("");
            dto.setInstitucionTraspasante(institucionDTO);

            // ---------------------------
            if (bo.getImporteTraspaso() != null) {
                dto.setMonto(bo.getImporteTraspaso());
            } else {
                dto.setMonto(BigDecimal.ZERO);
            }

            dto.setMontoVencimiento(BigDecimal.ZERO);
            dto.setOrigen(bo.getOrigen());
            dto.setPlazo(0L);
            // --------------------------------------------------------------
            dto.setConcepto(bo.getConceptoPago());
            // --------------------------------------------------------------
            dto.setReferenciaOperacion(bo.getReferenciaOperacion());
            // -----------------------------------------
            dto.setReferenciaNumerica(bo.getReferencia());
            // ----------------------------------------
            dto.setTasa(Double.valueOf("0"));
            // -------------------------------------
            // cargando el tipo TipoInstruccionDTO()
            // TipoInstruccionDTO tipoInstruccionDTO = new TipoInstruccionDTO();
            dto.setTipoInstruccion(DTOAssembler.crearTipoInstruccionDTO(bo.getTipoInstruccion()));
            // -------------------------------------
            // cargando el tipo TipoLiquidacionDTO() inicio
            TipoLiquidacionDTO tipoLiquidacionDTO = new TipoLiquidacionDTO();
            dto.setTipoLiquidacion(tipoLiquidacionDTO);
            // --------------------------------
            // Cargando el tipo TipoLiquidacionDTO vencimiento
            tipoLiquidacionDTO = new TipoLiquidacionDTO();
            dto.setLiquidacionVencimiento(tipoLiquidacionDTO);
            // --------------------------------
            // Cargando el tipo TipoLiquidacionDTO vencimiento
            TipoMensajeDTO tipoMensajeDTO = new TipoMensajeDTO();
            dto.setTipoMensaje(tipoMensajeDTO);

            // ------------------------------
            dto.setTipoRetiro(bo.getTipoOperacion());
        }
        return dto;

    }


    /**
     * Fernando Vázquez Ulloa 2009-11-19
     *
     * @param bo
     * @return
     */
    public static CuentaRetiroInternacionalDTO setCuentaRetiroInternacionalDTOByCRI(CuentaRetiroInternacional bo) {
        CuentaRetiroInternacionalDTO dto = null;
        if (bo != null) {
            dto = new CuentaRetiroInternacionalDTO();

            dto.setBancoBeneficiario(bo.getBancoBeneficiario());
            dto.setBancoIntermediario(bo.getBancoIntermediario());
            dto.setCuentaBeneficiario(bo.getCuentaBeneficiario());
            dto.setCuentaIntermediario(bo.getCuentaIntermediario());
            dto.setDivisa(DTOAssembler.crearDivisaDTO(bo.getDivisa()));
            dto.setEstado(DTOAssembler.crearEstadosCuentaDTO(bo.getEstado()));
            dto.setIdCuentaRetiro(bo.getIdCuentaRetiro());
            dto.setInstitucion(DTOAssembler.crearInstitucionDTO(bo.getInstitucion()));

            dto.setDetallesPago(bo.getDetallesPago());
            dto.setIdCuentaRetiroInt(bo.getIdCuentaRetiroInt());
            dto.setNombreBancoBeneficiario(bo.getNombreBancoBeneficiario());
            dto.setNombreCorto(bo.getNombreCorto());
            dto.setNombreIntermediario(bo.getNombreIntermediario());
        }
        return dto;
    }

    public static RetiroEfectivoDTO setRetiroEfectivoDTOByRetiroEfectivo(RetiroEfectivo bo) {
        RetiroEfectivoDTO dto = null;
        if (bo != null) {

            dto = new RetiroEfectivoDTO();
            dto.setBoveda(DTOAssembler.crearBovedaDTO(bo.getBoveda()));
            dto.setConceptoPago(bo.getConceptoPago());

            Map<String, Object> datosFirmas = new HashMap<String, Object>(0);
            datosFirmas.put("autorizacion_isoFirmado", bo.getAutorizacionFirmada());
            datosFirmas.put("autorizacion_usuario", bo.getUsuarioAutorizacion());
            datosFirmas.put("autorizacion_serie", bo.getUsuarioAutorizacion());
            datosFirmas.put("creacion_isoFirmado", bo.getCreacionFirmada());
            datosFirmas.put("creacion_usuario", bo.getUsuarioCreacion());
            datosFirmas.put("creacion_serie", bo.getSerieCreacion());
            datosFirmas.put("liberacion_isoFirmado", bo.getLiberacionFirmada());
            datosFirmas.put("liberacion_usuario", bo.getUsuarioLiberacion());
            datosFirmas.put("liberacion_serie", bo.getSerieLiberacion());
            dto.setDatosFirmas(datosFirmas);

            dto.setCuentaRetiroEfectivo(null);

            //se pega esto mientras ya que en realidad no esta ultizandose el campo.
            //Este metodo va a dar a bitacora operaciones
            //CuentaRetiroDTO cuenta = new CuentaRetiroDTO();
            //cuenta.setIdCuentaRetiro(bo.getIdCuentaBeneficiario()==null?null: BigInteger.valueOf(bo.getIdCuentaBeneficiario()));

            dto.setCuentaBeneficiarioFinal(null);

            //dto.setIdCuentaBeneficiario(bo.getIdCuentaBeneficiario());

            dto.setIdInstReceptor(DTOAssembler.crearInstitucionDTO(bo.getIdInstReceptor()));

            dto.setDivisa(DTOAssembler.crearDivisaDTO(bo.getDivisa()));
            dto.setEstado(DTOAssembler.crearEstadoInstruccionCatDTO(bo.getEstado()));
            dto.setFechaAutorizacion(bo.getFechaAutorizacion());
            dto.setFechaCreacion(bo.getFechaCreacion());
            dto.setFechaLiberacion(bo.getFechaLiberacion());
            dto.setIdRetiroEfectivoNal(bo.getIdRetiroEfectivoNal());
            dto.setImporteTraspaso(bo.getImporteTraspaso());
            dto.setInstitucion(DTOAssembler.crearInstitucionDTO(bo.getInstitucion()));
            dto.setIdRetiroEfectivoNal(bo.getIdRetiroEfectivoNal());

            dto.setReferencia(bo.getReferencia());
            dto.setReferenciaMensaje(bo.getReferenciaMensaje());
            dto.setReferenciaOperacion(bo.getReferenciaOperacion());

            dto.setCuentaBeneficiario(bo.getCuentaBeneficiario());
            //dto.setNombreBeneficiario(bo.getNombreBeneficiario());
        }
        return dto;
    }


    /**
     * @param bo
     * @return
     * @author fernando vazquez ulloa- metodo para obtener las operaciones
     * registradas de retiro de efectivo internacion en el MOI
     */
    public static RetiroEfectivoInternacionalDTO setOperacionEfecIntlByOperacionEfecIntlEnMoi(
            InternacionalEfectivo bo) {
        RetiroEfectivoInternacionalDTO dto = null;
        if (bo != null) {
            dto = new RetiroEfectivoInternacionalDTO();
            // Origen -------------------
            dto.setModuloOrigen("moi");
            //----------------------------
            InstitucionDTO institucionDTO = new InstitucionDTO();
            dto.setIdRetiroEfectivoInt(new BigInteger(bo.getIdInternacionalEfectivo().toString()));


            if (bo.getIdInstitucionTraspasante() != null) {
                institucionDTO.setId(bo.getIdInstitucionTraspasante().getIdInstitucion().longValue());
                institucionDTO.setNombreCorto("");
                institucionDTO.setRazonSocial("");

                institucionDTO.setFolioInstitucion(bo.getIdInstitucionTraspasante().getFolioInstitucion());
                institucionDTO.setClaveTipoInstitucion(bo.getIdInstitucionTraspasante().getTipoInstitucion().getClaveTipoInstitucion());
            } else {
                institucionDTO.setId(0);
                institucionDTO.setNombreCorto("");
                institucionDTO.setRazonSocial("");
                institucionDTO.setFolioInstitucion("");
                institucionDTO.setClaveTipoInstitucion("");
            }

            institucionDTO.setClaveCasfim("");
            institucionDTO.setCuentaClabe("");
            dto.setInstitucion(institucionDTO);

            //-------------------------------------------------
            dto.setReferenciaOperacion(bo.getReferenciaOperacion());
            dto.setImporteTraspaso(bo.getMonto());
            //agregando la divisa --------
            DivisaDTO divisaDTO = new DivisaDTO();
            if (bo.getIdDivisa() != null) {
                divisaDTO.setId(bo.getIdDivisa().getIdDivisa().longValue());
                divisaDTO.setDescripcion(bo.getIdDivisa().getDescripcion());
                divisaDTO.setClaveAlfabetica(bo.getIdDivisa().getClaveAlfabetica());
                divisaDTO.setClaveNumerica(bo.getIdDivisa().getClaveNumerica());
            } else {
                divisaDTO.setId(0);
                divisaDTO.setDescripcion("N/A");
                divisaDTO.setClaveAlfabetica("N/A");
                divisaDTO.setClaveNumerica("0");
            }
            dto.setDivisa(divisaDTO);
            //agregando la boveda --------


            BovedaDTO bovedaDTO = new BovedaDTO();
            if (bo.getIdBoveda() != null) {
                bovedaDTO.setId(bo.getIdBoveda().getIdBoveda().longValue());
                bovedaDTO.setDescripcion(bo.getIdBoveda().getDescripcion());
                bovedaDTO.setNombreCorto(bo.getIdBoveda().getNombreCorto());
                bovedaDTO.setClaveTipoBoveda(bo.getIdBoveda().getTipoBoveda().getClaveTipoBoveda());

            } else {
                bovedaDTO.setId(0);
                bovedaDTO.setDescripcion("");
                bovedaDTO.setNombreCorto("");
                bovedaDTO.setClaveTipoBoveda("");
            }
            dto.setBoveda(bovedaDTO);
            //---------------------------
            //agregando la cuenta del beneficiario --------
            //String cuentaBeneficiario = "";
            //if (bo.getIdCuentaReceptora()!= null){
            //	cuentaBeneficiario = bo.getIdCuentaReceptora().getCuenta();
            //}else{
            //	cuentaBeneficiario = "";
            //}
            //dto.setCuentaBeneficiario(cuentaBeneficiario);
            //---------------------------
            dto.setFechaValor(bo.getFechaValor());
            dto.setFechaLiquidacion(bo.getFechaLiquidacion());
            dto.setFechaLiberacion(null);
            dto.setConceptoPago(bo.getConcepto());


            //agregando el estado --------
            EstadoInstruccionCatDTO estadoInstruccionCatDTO = new EstadoInstruccionCatDTO();
            if (bo.getIdEstadoInstruccion() != null) {
                estadoInstruccionCatDTO.setClaveEstadoInstruccion(bo.getIdEstadoInstruccion().getClaveEstadoInstruccion());
                estadoInstruccionCatDTO.setDescripcion(bo.getIdEstadoInstruccion().getDescripcion());
                estadoInstruccionCatDTO.setIdEstadoInstruccion(bo.getIdEstadoInstruccion().getIdEstadoInstruccion());
            } else {
                estadoInstruccionCatDTO.setClaveEstadoInstruccion("");
                estadoInstruccionCatDTO.setDescripcion("");
                estadoInstruccionCatDTO.setIdEstadoInstruccion(BigInteger.ZERO);
            }
            dto.setEstado(estadoInstruccionCatDTO);

        }

        return dto;

    }

    /**
     * Crea un objeto de tipo {@link OperacionPosicionDTO} a partir de un objeto
     * de hibernate del tipo {@link OperacionNombrada}.
     *
     * @param operacion Objeto de hibernate fuente para construir el DTO
     * @return DTO creao a partir del parámetro de entrada.
     */
    public static OperacionPosicionDTO crearOperacionEfectivo(
            OperacionNombrada operacion) {

        OperacionPosicionDTO operacionPosicionDTO = null;
        if (operacion != null) {
            operacionPosicionDTO = new OperacionPosicionDTO();
            operacionPosicionDTO.setCargoEfectivoA(operacion
                    .getCargoEfectivoA() != null ? operacion
                    .getCargoEfectivoA().longValue() : 0);
            operacionPosicionDTO
                    .setCuentaEfectivoTraspasante(crearCuentaDTO(operacion
                            .getCuentaEfectivoTraspasante()));
            operacionPosicionDTO
                    .setCuentaEfectivoReceptor(crearCuentaDTO(operacion
                            .getCuentaEfectivoReceptor()));
            operacionPosicionDTO.setCuentaReceptora(crearCuentaDTO(operacion
                    .getCuentaNombradaReceptor()));
            operacionPosicionDTO.setCuentaTraspasante(crearCuentaDTO(operacion
                    .getCuentaNombradaTraspasante()));
            operacionPosicionDTO
                    .setDivisa(crearDivisaDTO(operacion.getDivisa()));
            operacionPosicionDTO.setFolio(operacion.getFolioOperacion()
                    .longValue());
            operacionPosicionDTO.setIdOperacion(operacion.getIdOperacion()
                    .longValue());
            operacionPosicionDTO
                    .setInstruccionLiquidacion(crearInstruccionLiquidacion(operacion
                            .getInstruccion()));
            operacionPosicionDTO.setTipoOperacion(crearTipoOperacion(operacion
                    .getTipoOperacion()));

            operacionPosicionDTO
                    .setMonto(operacion.getMonto() != null ? operacion
                            .getMonto().doubleValue() : 0);

            // operacionPosicionDTO.setCargoValoresA(operacion.getCargoValoresA()
            // != null ? operacion.getCargoValoresA().longValue() : 0);
            operacionPosicionDTO.setCupon(crearCuponDTO(operacion.getCupon()));
            operacionPosicionDTO
                    .setNumeroTitulos(operacion.getNumeroTitulos() != null ? operacion
                            .getNumeroTitulos().longValue()
                            : 0);
            operacionPosicionDTO.setPosicion(crearPosicionNombradaDTO(operacion
                    .getPosicionNombrada()));
            operacionPosicionDTO
                    .setPrecio(operacion.getPrecio() != null ? operacion
                            .getPrecio().doubleValue() : 0);
        }
        return operacionPosicionDTO;
    }

    /**
     * Crea un objeto de tipo {@link OperacionPosicionDTO} a partir de un objeto
     * de hibernate del tipo {@link OperacionNombrada}.
     *
     * @param operacion Objeto de hibernate fuente para construir el DTO
     * @return DTO creao a partir del parámetro de entrada.
     */
    public static OperacionPosicionDTO crearOperacionEfectivo(
            OperacionNombrada operacion, Integer idTipoPago) {

        OperacionPosicionDTO operacionPosicionDTO = null;
        if (operacion != null) {
            operacionPosicionDTO = new OperacionPosicionDTO();
            operacionPosicionDTO.setCargoEfectivoA(operacion
                    .getCargoEfectivoA() != null ? operacion
                    .getCargoEfectivoA().longValue() : 0);
            operacionPosicionDTO
                    .setCuentaEfectivoTraspasante(crearCuentaDTO(operacion
                            .getCuentaEfectivoTraspasante()));
            operacionPosicionDTO
                    .setCuentaEfectivoReceptor(crearCuentaDTO(operacion
                            .getCuentaEfectivoReceptor()));
            operacionPosicionDTO.setCuentaReceptora(crearCuentaDTO(operacion
                    .getCuentaNombradaReceptor()));
            operacionPosicionDTO.setCuentaTraspasante(crearCuentaDTO(operacion
                    .getCuentaNombradaTraspasante()));
            operacionPosicionDTO
                    .setDivisa(crearDivisaDTO(operacion.getDivisa()));
            operacionPosicionDTO.setFolio(operacion.getFolioOperacion()
                    .longValue());
            operacionPosicionDTO.setIdOperacion(operacion.getIdOperacion()
                    .longValue());
            operacionPosicionDTO
                    .setInstruccionLiquidacion(crearInstruccionLiquidacion(operacion
                            .getInstruccion()));
            operacionPosicionDTO.setTipoOperacion(crearTipoOperacion(operacion
                    .getTipoOperacion()));

            operacionPosicionDTO
                    .setMonto(operacion.getMonto() != null ? operacion
                            .getMonto().doubleValue() : 0);

            operacionPosicionDTO.setCupon(crearCuponDTO(operacion.getCupon()));
            operacionPosicionDTO
                    .setNumeroTitulos(operacion.getNumeroTitulos() != null ? operacion
                            .getNumeroTitulos().longValue()
                            : 0);
            operacionPosicionDTO
                    .setPosicion(crearPosicionNombradaV2DTO(operacion
                            .getPosicionNombrada()));
            operacionPosicionDTO
                    .setPrecio(operacion.getPrecio() != null ? operacion
                            .getPrecio().doubleValue() : 0);
        }
        return operacionPosicionDTO;
    }

    /**
     * Crea un objeto de tipo {@link OperacionPosicionDTO} a partir de un objeto
     * de hibernate del tipo {@link OperacionNombradaHistorico}.
     *
     * @param operacion Objeto de hibernate fuente para construir el DTO
     * @return DTO creao a partir del parámetro de entrada.
     */
    public static OperacionPosicionDTO crearOperacionEfectivoHistorico(
            OperacionNombradaHistorico operacion) {

        OperacionPosicionDTO operacionPosicionDTO = null;
        if (operacion != null) {
            operacionPosicionDTO = new OperacionPosicionDTO();
            operacionPosicionDTO.setCargoEfectivoA(operacion
                    .getCargoEfectivoA() != null ? operacion
                    .getCargoEfectivoA().longValue() : 0);
            operacionPosicionDTO.setCuentaReceptora(crearCuentaDTO(operacion
                    .getCuentaNombradaReceptor()));
            operacionPosicionDTO.setCuentaTraspasante(crearCuentaDTO(operacion
                    .getCuentaNombradaTraspasante()));
            operacionPosicionDTO
                    .setCuentaEfectivoTraspasante(crearCuentaDTO(operacion
                            .getCuentaEfectivoTraspasante()));
            operacionPosicionDTO
                    .setCuentaEfectivoReceptor(crearCuentaDTO(operacion
                            .getCuentaEfectivoReceptor()));
            operacionPosicionDTO
                    .setDivisa(crearDivisaDTO(operacion.getDivisa()));
            operacionPosicionDTO.setFolio(operacion.getFolioOperacion()
                    .longValue());
            operacionPosicionDTO.setIdOperacion(operacion.getIdOperacion()
                    .longValue());
            operacionPosicionDTO
                    .setInstruccionLiquidacion(crearInstruccionLiquidacionHistorico(operacion
                            .getInstruccion()));
            operacionPosicionDTO.setTipoOperacion(crearTipoOperacion(operacion
                    .getTipoOperacion()));
            operacionPosicionDTO
                    .setMonto(operacion.getMonto() != null ? operacion
                            .getMonto().doubleValue() : 0);
            // operacionPosicionDTO.setCargoValoresA(operacion.getCargoValoresA()
            // != null ? operacion.getCargoValoresA().longValue() : 0);
            operacionPosicionDTO.setCupon(crearCuponDTO(operacion.getCupon()));
            operacionPosicionDTO
                    .setNumeroTitulos(operacion.getNumeroTitulos() != null ? operacion
                            .getNumeroTitulos().longValue()
                            : 0);
            operacionPosicionDTO.setPosicion(crearPosicionNombradaDTO(operacion
                    .getPosicionNombrada()));
            operacionPosicionDTO
                    .setPrecio(operacion.getPrecio() != null ? operacion
                            .getPrecio().doubleValue() : 0);
        }
        return operacionPosicionDTO;
    }

    /**
     * Crea un objeto de tipo {@link OperacionPosicionDTO} a partir de un objeto
     * de hibernate del tipo {@link OperacionNombradaHistorico}.
     *
     * @param operacion Objeto de hibernate fuente para construir el DTO
     * @return DTO creao a partir del parámetro de entrada.
     */
    public static OperacionPosicionDTO crearOperacionEfectivoHistorico(
            OperacionNombradaHistorico operacion, Integer idTipoPago) {

        OperacionPosicionDTO operacionPosicionDTO = null;
        if (operacion != null) {
            operacionPosicionDTO = new OperacionPosicionDTO();
            operacionPosicionDTO.setCargoEfectivoA(operacion
                    .getCargoEfectivoA() != null ? operacion
                    .getCargoEfectivoA().longValue() : 0);
            operacionPosicionDTO
                    .setCuentaEfectivoTraspasante(crearCuentaDTO(operacion
                            .getCuentaEfectivoTraspasante()));
            operacionPosicionDTO
                    .setCuentaEfectivoReceptor(crearCuentaDTO(operacion
                            .getCuentaEfectivoReceptor()));
            operacionPosicionDTO.setCuentaReceptora(crearCuentaDTO(operacion
                    .getCuentaNombradaReceptor()));
            operacionPosicionDTO.setCuentaTraspasante(crearCuentaDTO(operacion
                    .getCuentaNombradaTraspasante()));
            operacionPosicionDTO
                    .setDivisa(crearDivisaDTO(operacion.getDivisa()));
            operacionPosicionDTO.setFolio(operacion.getFolioOperacion()
                    .longValue());
            operacionPosicionDTO.setIdOperacion(operacion.getIdOperacion()
                    .longValue());
            operacionPosicionDTO
                    .setInstruccionLiquidacion(crearInstruccionLiquidacionHistorico(
                            operacion.getInstruccion(), idTipoPago));
            operacionPosicionDTO.setTipoOperacion(crearTipoOperacion(operacion
                    .getTipoOperacion()));
            operacionPosicionDTO
                    .setMonto(operacion.getMonto() != null ? operacion
                            .getMonto().doubleValue() : 0);
            operacionPosicionDTO.setCupon(crearCuponDTO(operacion.getCupon()));
            operacionPosicionDTO
                    .setNumeroTitulos(operacion.getNumeroTitulos() != null ? operacion
                            .getNumeroTitulos().longValue()
                            : 0);
            operacionPosicionDTO
                    .setPosicion(crearPosicionNombradaHistoricoV2DTO(operacion
                            .getPosicionNombrada()));
            operacionPosicionDTO
                    .setPrecio(operacion.getPrecio() != null ? operacion
                            .getPrecio().doubleValue() : 0);
        }
        return operacionPosicionDTO;
    }

    /**
     * Crea un objeto de tipo {@link OperacionPosicionDTO} a partir de un objeto
     * de hibernate del tipo {@link OperacionNombrada}.
     *
     * @param operacion Objeto de hibernate fuente para construir el DTO
     * @return DTO creao a partir del parámetro de entrada.
     */
    public static OperacionPosicionDTO crearOperacion(
            OperacionNombrada operacion) {

        OperacionPosicionDTO operacionPosicionDTO = null;
        if (operacion != null) {
            operacionPosicionDTO = new OperacionPosicionDTO();
            operacionPosicionDTO.setCargoEfectivoA(operacion
                    .getCargoEfectivoA() != null ? operacion
                    .getCargoEfectivoA().longValue() : 0);
            operacionPosicionDTO.setCuentaReceptora(crearCuentaDTO(operacion
                    .getCuentaNombradaReceptor()));
            operacionPosicionDTO.setCuentaTraspasante(crearCuentaDTO(operacion
                    .getCuentaNombradaTraspasante()));
            operacionPosicionDTO
                    .setDivisa(crearDivisaDTO(operacion.getDivisa()));
            operacionPosicionDTO.setFolio(operacion.getFolioOperacion()
                    .longValue());
            operacionPosicionDTO.setIdOperacion(operacion.getIdOperacion()
                    .longValue());
            operacionPosicionDTO
                    .setInstruccionLiquidacion(crearInstruccionLiquidacion(operacion
                            .getInstruccion()));
            operacionPosicionDTO.setTipoOperacion(crearTipoOperacion(operacion
                    .getTipoOperacion()));
            operacionPosicionDTO
                    .setMonto(operacion.getMonto() != null ? operacion
                            .getMonto().doubleValue() : 0);
            operacionPosicionDTO
                    .setCargoValoresA(operacion.getCargoValoresA() != null ? operacion
                            .getCargoValoresA().longValue()
                            : 0);
            operacionPosicionDTO.setCupon(crearCuponDTO(operacion.getCupon()));
            operacionPosicionDTO
                    .setNumeroTitulos(operacion.getNumeroTitulos() != null ? operacion
                            .getNumeroTitulos().longValue()
                            : 0);
            operacionPosicionDTO.setPosicion(crearPosicionNombradaDTO(operacion
                    .getPosicionNombrada()));
            operacionPosicionDTO
                    .setPrecio(operacion.getPrecio() != null ? operacion
                            .getPrecio().doubleValue() : 0);
        }
        return operacionPosicionDTO;
    }

    /**
     * Crea un objeto de tipo {@link OperacionPosicionDTO} a partir de un objeto
     * de hibernate del tipo {@link OperacionNombrada}.
     *
     * @param operacion Objeto de hibernate fuente para construir el DTO
     * @return DTO creao a partir del parámetro de entrada.
     */
    public static OperacionPosicionDTO crearOperacionHistorico(
            OperacionNombradaHistorico operacion) {

        OperacionPosicionDTO operacionPosicionDTO = null;
        if (operacion != null) {
            operacionPosicionDTO = new OperacionPosicionDTO();
            operacionPosicionDTO.setCargoEfectivoA(operacion
                    .getCargoEfectivoA() != null ? operacion
                    .getCargoEfectivoA().longValue() : 0);
            operacionPosicionDTO.setCuentaReceptora(crearCuentaDTO(operacion
                    .getCuentaNombradaReceptor()));
            operacionPosicionDTO.setCuentaTraspasante(crearCuentaDTO(operacion
                    .getCuentaNombradaTraspasante()));
            operacionPosicionDTO
                    .setDivisa(crearDivisaDTO(operacion.getDivisa()));
            operacionPosicionDTO.setFolio(operacion.getFolioOperacion()
                    .longValue());
            operacionPosicionDTO.setIdOperacion(operacion.getIdOperacion()
                    .longValue());
            operacionPosicionDTO
                    .setInstruccionLiquidacion(crearInstruccionLiquidacionHistorico(operacion
                            .getInstruccion()));
            operacionPosicionDTO.setTipoOperacion(crearTipoOperacion(operacion
                    .getTipoOperacion()));
            operacionPosicionDTO
                    .setMonto(operacion.getMonto() != null ? operacion
                            .getMonto().doubleValue() : 0);
            operacionPosicionDTO
                    .setCargoValoresA(operacion.getCargoValoresA() != null ? operacion
                            .getCargoValoresA().longValue()
                            : 0);
            operacionPosicionDTO.setCupon(crearCuponDTO(operacion.getCupon()));
            operacionPosicionDTO
                    .setNumeroTitulos(operacion.getNumeroTitulos() != null ? operacion
                            .getNumeroTitulos().longValue()
                            : 0);
            operacionPosicionDTO.setPosicion(crearPosicionNombradaDTO(operacion
                    .getPosicionNombrada()));
            operacionPosicionDTO
                    .setPrecio(operacion.getPrecio() != null ? operacion
                            .getPrecio().doubleValue() : 0);
        }
        return operacionPosicionDTO;
    }

    /**
     * Crea un objeto de tipo {@link InstruccionLiquidacionDTO} a partir de un
     * objeto de hibernate del tipo {@link InstruccionLiquidacion}.
     *
     * @param instruccion Objeto de hibernate fuente para construir el DTO
     * @return DTO creao a partir del parámetro de entrada
     */
    public static InstruccionLiquidacionDTO crearInstruccionLiquidacion(
            InstruccionLiquidacion instruccion) {
        InstruccionLiquidacionDTO instruccionLiquidacionDTO = null;
        if (instruccion != null) {
            instruccionLiquidacionDTO = new InstruccionLiquidacionDTO();
            instruccionLiquidacionDTO.setFolioInstruccion(instruccion
                    .getFolioInstruccion().longValue());
            instruccionLiquidacionDTO.setIdInstruccionLiquidacion(instruccion
                    .getIdInstruccionLiquidacion().longValue());
            instruccionLiquidacionDTO
                    .setIdInstruccionLiquidacionOrigen(instruccion
                            .getIdInstruccionLiquidacionOrigen() != null ? instruccion
                            .getIdInstruccionLiquidacionOrigen().longValue()
                            : 0);
            instruccionLiquidacionDTO
                    .setTipoInstruccion(crearTipoInstruccion(instruccion
                            .getTipoInstruccion()));
            instruccionLiquidacionDTO.setFechaLiquidacion(instruccion
                    .getFechaLiquidacion());
            instruccionLiquidacionDTO
                    .setDescripcionEstadoInstruccion(instruccion
                            .getEstadoInstruccion().getName());
            instruccionLiquidacionDTO.setIdEstadoInstruccion(instruccion
                    .getEstadoInstruccion().getValue());
            instruccionLiquidacionDTO.setEsBancoTrabajo(instruccion
                    .isBancoTrabajo());

        }
        return instruccionLiquidacionDTO;
    }

    /**
     * Crea un objeto de tipo {@link InstruccionLiquidacionDTO} a partir de un
     * objeto de hibernate del tipo {@link InstruccionLiquidacion}.
     *
     * @param instruccion Objeto de hibernate fuente para construir el DTO
     * @return DTO creao a partir del parámetro de entrada
     */
    public static InstruccionLiquidacionDTO crearInstruccionLiquidacionHistorico(
            InstruccionLiquidacionHistorico instruccion) {
        InstruccionLiquidacionDTO instruccionLiquidacionDTO = null;
        if (instruccion != null) {
            instruccionLiquidacionDTO = new InstruccionLiquidacionDTO();
            instruccionLiquidacionDTO.setFolioInstruccion(instruccion
                    .getFolioInstruccion().longValue());
            instruccionLiquidacionDTO.setIdInstruccionLiquidacion(instruccion
                    .getIdInstruccionLiquidacion().longValue());
            instruccionLiquidacionDTO
                    .setIdInstruccionLiquidacionOrigen(instruccion
                            .getIdInstruccionLiquidacionOrigen() != null ? instruccion
                            .getIdInstruccionLiquidacionOrigen().longValue()
                            : 0);
            instruccionLiquidacionDTO
                    .setTipoInstruccion(crearTipoInstruccion(instruccion
                            .getTipoInstruccion()));
            instruccionLiquidacionDTO.setFechaLiquidacion(instruccion
                    .getFechaLiquidacion());

            instruccionLiquidacionDTO
                    .setDescripcionEstadoInstruccion(instruccion
                            .getEstadoInstruccion().getName());
            instruccionLiquidacionDTO.setIdEstadoInstruccion(instruccion
                    .getEstadoInstruccion().getValue());
            instruccionLiquidacionDTO.setEsBancoTrabajo(instruccion
                    .isBancoTrabajo());
            // instruccionLiquidacionDTO.setInstruccionEfectivo(crearInstruccionEfectivoDTO(instruccion.getInstruccionEfectivo()));
        }
        return instruccionLiquidacionDTO;
    }

    /**
     * Crea un objeto de tipo {@link InstruccionLiquidacionDTO} a partir de un
     * objeto de hibernate del tipo {@link InstruccionLiquidacion}.
     *
     * @param instruccion Objeto de hibernate fuente para construir el DTO
     * @return DTO creao a partir del parámetro de entrada
     */
    public static InstruccionLiquidacionDTO crearInstruccionLiquidacionHistorico(
            InstruccionLiquidacionHistorico instruccion, Integer idTipoPago) {
        InstruccionLiquidacionDTO instruccionLiquidacionDTO = null;
        if (instruccion != null) {
            instruccionLiquidacionDTO = new InstruccionLiquidacionDTO();
            instruccionLiquidacionDTO.setFolioInstruccion(instruccion
                    .getFolioInstruccion().longValue());
            instruccionLiquidacionDTO.setIdInstruccionLiquidacion(instruccion
                    .getIdInstruccionLiquidacion().longValue());
            instruccionLiquidacionDTO
                    .setIdInstruccionLiquidacionOrigen(instruccion
                            .getIdInstruccionLiquidacionOrigen() != null ? instruccion
                            .getIdInstruccionLiquidacionOrigen().longValue()
                            : 0);
            instruccionLiquidacionDTO
                    .setTipoInstruccion(crearTipoInstruccion(instruccion
                            .getTipoInstruccion()));
            instruccionLiquidacionDTO.setFechaLiquidacion(instruccion
                    .getFechaLiquidacion());
            instruccionLiquidacionDTO
                    .setDescripcionEstadoInstruccion(instruccion
                            .getEstadoInstruccion().getName());
            instruccionLiquidacionDTO.setIdEstadoInstruccion(instruccion
                    .getEstadoInstruccion().getValue());
            instruccionLiquidacionDTO.setEsBancoTrabajo(instruccion
                    .isBancoTrabajo());
            instruccionLiquidacionDTO
                    .setInstruccionEfectivo(crearInstruccionEfectivoV2DTO(idTipoPago));

        }
        return instruccionLiquidacionDTO;
    }

    /**
     * Crea un objeto de tipo {@link TipoInstruccionDTO} a partir de un objeto
     * de hibernate del tipo {@link TipoInstruccion}
     *
     * @param tipoInstruccion Objeto de hibernate fuente para construir el DTO
     * @return DTO creao a partir del parámetro de entrada
     */
    public static TipoInstruccionDTO crearTipoInstruccion(
            TipoInstruccion tipoInstruccion) {
        TipoInstruccionDTO tipoInstruccionDTO = null;
        if (tipoInstruccion != null) {
            tipoInstruccionDTO = new TipoInstruccionDTO();
            tipoInstruccionDTO.setIdTipoInstruccion(tipoInstruccion
                    .getIdTipoInstruccion().longValue());
            tipoInstruccionDTO
                    .setDescripcion(tipoInstruccion.getDescripcion() != null ? tipoInstruccion
                            .getDescripcion().toUpperCase()
                            : StringUtils.EMPTY);
            tipoInstruccionDTO
                    .setNombreCorto(tipoInstruccion.getNombreCorto() != null ? tipoInstruccion
                            .getNombreCorto().toUpperCase()
                            : StringUtils.EMPTY);
            tipoInstruccionDTO
                    .setInstruccion(tipoInstruccion.getInstruccion() != null ? tipoInstruccion
                            .getInstruccion().toUpperCase()
                            : StringUtils.EMPTY);
        }
        return tipoInstruccionDTO;
    }

    public static TipoInstruccion crearTipoInstruccion(TipoInstruccionDTO dto) {
        TipoInstruccion tipoInstruccion = null;
        if (dto != null) {
            tipoInstruccion = new TipoInstruccion();
            if (dto.getIdTipoInstruccion() != null)
                tipoInstruccion.setIdTipoInstruccion(new BigInteger(dto.getIdTipoInstruccion().toString()));
            tipoInstruccion.setDescripcion(dto.getDescripcion());
            tipoInstruccion.setNombreCorto(dto.getNombreCorto());
            tipoInstruccion.setInstruccion(dto.getInstruccion());

        }
        return tipoInstruccion;
    }

    /**
     * Crea un objeto de tipo {@link TipoLiquidacionDTO} a partir de un objeto
     * de hibernate del tipo {@link TipoLiquidacion}
     *
     * @param tipoLiquidacion Objeto de hibernate fuente para construir el DTO
     * @return DTO creao a partir del parámetro de entrada
     */
    public static TipoLiquidacionDTO crearTipoLiquidacion(
            TipoLiquidacion tipoLiquidacion) {
        TipoLiquidacionDTO tipoLiquidacionDTO = null;
        if (tipoLiquidacion != null) {
            tipoLiquidacionDTO = new TipoLiquidacionDTO();
            tipoLiquidacionDTO.setIdTipoLiq(tipoLiquidacion.getIdTipoLiq());
            tipoLiquidacionDTO
                    .setDescripcion(tipoLiquidacion.getDescripcion() != null ? tipoLiquidacion
                            .getDescripcion().toUpperCase()
                            : StringUtils.EMPTY);
            tipoLiquidacionDTO
                    .setNombreCorto(tipoLiquidacion.getNombreCorto() != null ? tipoLiquidacion
                            .getNombreCorto().toUpperCase()
                            : StringUtils.EMPTY);
            tipoLiquidacionDTO.setTipoLiquidacion(tipoLiquidacion
                    .getTipoLiquidacion() != null ? tipoLiquidacion
                    .getTipoLiquidacion().toUpperCase() : StringUtils.EMPTY);
        }
        return tipoLiquidacionDTO;
    }

    /**
     * Crea un objeto BitacoraMatchVO a partir de los datos de un DTO
     * OperacionValorMatchDTO
     *
     * @param dto Datos de origen
     * @return DTO con los datos destino
     */
    public static BitacoraMatchVO crearBitacoraMatchVO(
            OperacionValorMatchDTO dto) {
        BitacoraMatchVO vo = null;
        if (dto != null) {
            // vo = new BitacoraMatchVO();
            // vo.setCodigoOperacion(codigoOperacion)
            // vo.setDescripcionError(descripcionError)
            // vo.setEnviada(enviada)
            // vo.setEstadoInstruccion(estadoInstruccion)
            // vo.setExpira(expira)
            // vo.setFecha(fecha)
            // vo.setFechaHoraRecepcion(fechaHoraRecepcion)
            // vo.setFechaLiquidacion(fechaLiquidacion)
            // vo.setFolioInstruccionReceptor(folioInstruccionReceptor)
            // vo.setFolioInstruccionTraspasante(folioInstruccionTraspasante)
            // vo.setFolioMatch(folioMatch)
            // vo.setFolioReceptor(folioReceptor)
            // vo.setFolioTraspasante(folioTraspasante)
            // vo.setIdBitacora(idBitacora)
            // vo.setIdBitacoraMatch(idBitacoraMatch)
            // vo.setIdReceptor(idReceptor)
            // vo.setIdTraspasante(idTraspasante)
            // vo.setMensaje(mensaje)
            // vo.setTipoMensaje(tipoMensaje)
            // vo.setTipoOperacion(tipoOperacion)

        }

        return vo;
    }

    /**
     * Crea un objeto del tipo AgenteVO a partir de una objeto del tipo Cuenta
     *
     * @param cuenta Cuenta con los datos origen
     */
    public static AgenteVO crearAgenteVO(CuentaDTO cuenta) {
        AgenteVO agente = null;

        if (cuenta != null) {
            agente = new AgenteVO();
            agente.setId(cuenta.getInstitucion().getClaveTipoInstitucion());
            agente.setFolio(cuenta.getInstitucion().getFolioInstitucion());
            agente.setCuenta(cuenta.getCuenta());
            agente.setNombreCorto(cuenta.getInstitucion().getNombreCorto());
            agente.setNombreCuenta(cuenta.getNombreCuenta());
            agente.setTenencia(cuenta.getTipoTenencia().getDescripcion());
        }

        return agente;
    }

    /**
     * Crea un objeto del tipo AgenteVO a partir de una objeto del tipo Cuenta
     *
     * @param cuenta Cuenta con los datos origen
     */
    public static AgenteVO crearAgenteVO(CuentaEfectivoDTO cuenta) {
        AgenteVO agente = null;

        if (cuenta != null) {
            agente = new AgenteVO();
            agente.setId(cuenta.getInstitucion().getClaveTipoInstitucion());
            agente.setFolio(cuenta.getInstitucion().getFolioInstitucion());
            agente.setCuenta(cuenta.getDescripcion());
            agente.setNombreCorto(cuenta.getInstitucion().getNombreCorto());
            agente.setNombreCuenta(cuenta.getNombreCuenta());
        }

        return agente;
    }

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
     * Transforma una lista de Long en una lista de objetos BigInteger.
     *
     * @param listaLong una lista con elementos Long
     * @return una lista con objetos BigInteger
     */
    public static List<BigInteger> transformarListaLongEnBigInteger(
            List<Long> listaLong) {
        List<BigInteger> listaBigInt = new ArrayList<BigInteger>();

        for (Long value : listaLong) {
            listaBigInt.add(new BigInteger(String.valueOf(value)));
        }

        return listaBigInt;
    }

    /**
     * Crea un objeto {@link PosicionDTO} a partir de un objeto
     * {@link PosicionNombradaHistorico}
     *
     * @param posicionHistorica el BO del cual se toman los datos.
     * @return el DTO creado a partir del BO
     */
    public static PosicionDTO crearPosicionNombradaDTO(
            PosicionNombradaHistorico posicionHistorica) {
        PosicionDTO dto = null;
        if (posicionHistorica != null) {
            dto = new PosicionDTO();
            dto.setIdPosicion(posicionHistorica.getIdPosicion().longValue());
            dto.setBoveda(crearBovedaDTO(posicionHistorica.getBoveda()));
            dto
                    .setCuenta(crearCuentaDTO(posicionHistorica
                            .getCuentaNombrada()));
            dto.setEmision(crearEmisionDTO(posicionHistorica.getCupon()
                    .getEmision()));
            dto.getEmision().setCupon(
                    posicionHistorica.getCupon().getClaveCupon());
            dto.setCupon(crearCuponDTO(posicionHistorica.getCupon()));
            dto.setPosicionDisponible(posicionHistorica.getPosicionDisponible()
                    .toString());
            dto.setPosicionDisponibleNumerica(new BigDecimal(posicionHistorica
                    .getPosicionDisponible()));
            dto.setPosicionNoDisponible(posicionHistorica
                    .getPosicionNoDisponible().toString());
            dto.setPosicionNoDisponibleNumerica(new BigDecimal(
                    posicionHistorica.getPosicionNoDisponible()));
            dto.setPosicion(posicionHistorica.getPosicionDisponible().add(
                    posicionHistorica.getPosicionNoDisponible()).toString());
            dto.setPosicionNumerica(new BigDecimal(posicionHistorica
                    .getPosicionDisponible().add(
                            posicionHistorica.getPosicionNoDisponible())));

            dto.setValuacionNominal(new BigDecimal(String.valueOf(dto
                    .getEmision().getValorNominal())).multiply(new BigDecimal(
                    posicionHistorica.getPosicionDisponible().add(
                            posicionHistorica.getPosicionNoDisponible()))));

        }
        return dto;
    }

    /**
     * Crea un objeto {@link PosicionDTO} a partir de un objeto
     * {@link PosicionControladaHistorico}
     *
     * @param posicionHistorica el BO del cual se toman los datos.
     * @return el DTO creado a partir del BO.
     */
    public static PosicionDTO crearPosicionControladaDTO(
            PosicionControladaHistorico posicionHistorica) {

        PosicionDTO dto = null;
        if (posicionHistorica != null) {
            dto = new PosicionDTO();
            dto.setIdPosicion(posicionHistorica.getIdPosicion().longValue());
            dto.setBoveda(crearBovedaDTO(posicionHistorica.getBoveda()));
            dto.setCuenta(crearCuentaDTO(posicionHistorica
                    .getCuentaControlada()));
            dto.setCupon(crearCuponDTO(posicionHistorica.getCupon()));
            dto.setEmision(crearEmisionDTO(posicionHistorica.getEmision()));
            dto.getEmision().setCupon(
                    posicionHistorica.getCupon().getClaveCupon());
            dto.setPosicion(posicionHistorica.getPosicion().toString());
            dto.setPosicionNumerica(new BigDecimal(posicionHistorica
                    .getPosicion()));
            dto.setPosicionNoDisponible("0");
            dto.setPosicionDisponible(posicionHistorica.getPosicion()
                    .toString());
            dto.setPosicionDisponibleNumerica(new BigDecimal(posicionHistorica
                    .getPosicion()));
        }
        return dto;
    }

    /**
     * Construye un objeto {@link RegistroContablePosicionNombradaDTO} a partir
     * de un objeto {@link RegContValNombradaHistorico}
     *
     * @param registro el BO con los datos para construir el DTO.
     * @return el DTO construido.
     */
    public static RegistroContablePosicionNombradaDTO crearRegistroContablePosicionNombradaConDatosOperacion(
            RegContValNombradaHistorico registro) {
        RegistroContablePosicionNombradaDTO dtoRegistro = null;

        if (registro != null) {

            dtoRegistro = new RegistroContablePosicionNombradaDTO();
            dtoRegistro.setHistorico(true);
            dtoRegistro.setIdRegistroContable(registro
                    .getIdRegistroContableHistorico().longValue());
            dtoRegistro.setPosicion(crearPosicionNombradaDTO(registro
                    .getPosicion()));
            dtoRegistro
                    .setTipoAccion(crearTipoAccion(registro.getTipoAccion()));
            dtoRegistro.setCantidad(registro.getCantidad());
            dtoRegistro.setFecha(registro.getFecha());
            dtoRegistro.setCargoA(registro.getCargoA().longValue());
            dtoRegistro.setOperacion(crearOperacionHistorico(registro
                    .getOperacion()));
            dtoRegistro.setFolio(registro.getOperacion().getInstruccion()
                    .getFolioInstruccion().longValue());
        }
        return dtoRegistro;
    }

    /**
     * Crea un objeto {@link RegistroContablePosicionControladaDTO} a partir de
     * un objeto {@link RegContValControladaHistorico}
     *
     * @param registro el BO del cual se toman los datos.
     * @return el DTO creado a partir del BO.
     */
    public static RegistroContablePosicionControladaDTO crearRegistroContablePosicionControlada(
            RegContValControladaHistorico registro) {
        RegistroContablePosicionControladaDTO registroContablePosicionControladaDTO = null;

        if (registro != null) {
            registroContablePosicionControladaDTO = new RegistroContablePosicionControladaDTO();
            registroContablePosicionControladaDTO.setHistorico(true);
            registroContablePosicionControladaDTO.setCantidad(registro
                    .getCantidad());
            registroContablePosicionControladaDTO.setFecha(registro.getFecha());
            registroContablePosicionControladaDTO.setIdCiclo(registro
                    .getIdCiclo().longValue());
            registroContablePosicionControladaDTO
                    .setIdRegistroContable(registro.getIdRegistroContable()
                            .longValue());
            registroContablePosicionControladaDTO
                    .setPosicion(crearPosicionControladaDTO(registro
                            .getPosicionControlada()));
            registroContablePosicionControladaDTO
                    .setTipoAccion(crearTipoAccion(registro.getTipoAccion()));

        }

        return registroContablePosicionControladaDTO;
    }

    /**
     * Crea un objeto {@link EstadosCuentaDTO} a partir de
     * un objeto {@link EstadoInstruccionCat}
     *
     * @param estado el EstadoInstruccionCat del cual se toman los datos.
     * @return el DTO creado.
     */
    public static EstadosCuentaDTO crearEstadosCuentaDTO(EstadoInstruccionCat estado) {
        EstadosCuentaDTO estadoDTO = new EstadosCuentaDTO();
        try {
            if (estado != null) {
                estadoDTO.setId(estado.getIdEstadoInstruccion().longValue());
                estadoDTO.setDescripcion(estado.getDescripcion());
            }
        } catch (Exception ex) {

        }

        return estadoDTO;
    }


    public static EstadoInstruccionCat crearEstadoInstruccionCat(EstadosCuentaDTO estado) {
        EstadoInstruccionCat estadoInstruccionCat = null;

        if (estado != null) {
            estadoInstruccionCat = new EstadoInstruccionCat();
            estadoInstruccionCat.setIdEstadoInstruccion(new BigInteger(String.valueOf(estado.getId())));
            estadoInstruccionCat.setDescripcion(estado.getDescripcion());
        }

        return estadoInstruccionCat;
    }

    public static EstadoInstruccionCat crearEstadoInstruccionCat(EstadoInstruccionCatDTO estado) {
        EstadoInstruccionCat estadoInstruccionCat = null;

        if (estado != null) {
            estadoInstruccionCat = new EstadoInstruccionCat();
            estadoInstruccionCat.setIdEstadoInstruccion(estado.getIdEstadoInstruccion());
            estadoInstruccionCat.setDescripcion(estado.getDescripcion());
            estadoInstruccionCat.setClaveEstadoInstruccion(estado.getClaveEstadoInstruccion());
        }

        return estadoInstruccionCat;
    }

    /**
     * Crea un objeto {@link CuentaRetiroInternacionalDTO} a partir de
     * un objeto {@link CuentaRetiroInternacional}
     *
     * @param cuenta CuentaRetiroInternacional del cual se toman los datos.
     * @return el DTO creado.
     */
    public static CuentaRetiroInternacionalDTO crearCuentaRetiroInternacionalDTO(
            CuentaRetiroInternacional cuenta) {
        CuentaRetiroInternacionalDTO cuentaDTO = new CuentaRetiroInternacionalDTO();
        try {
            if (cuenta != null) {
                cuentaDTO.setIdCuentaRetiroInt(cuenta.getIdCuentaRetiroInt().longValue());
                cuentaDTO.setNombreCorto(cuenta.getNombreCorto());
                cuentaDTO.setDivisa(DTOAssembler.crearDivisaDTO(cuenta.getDivisa()));
                cuentaDTO.setEstado(DTOAssembler.crearEstadosCuentaDTO(cuenta.getEstado()));
                cuentaDTO.setIdCuentaRetiro(cuenta.getIdCuentaRetiro());
                cuentaDTO.setInstitucion(DTOAssembler.crearInstitucionDTO(cuenta.getInstitucion()));
                cuentaDTO.setBancoBeneficiario(cuenta.getBancoBeneficiario());
                cuentaDTO.setBancoIntermediario(cuenta.getBancoIntermediario());
                cuentaDTO.setCuentaBeneficiario(cuenta.getCuentaBeneficiario());
                cuentaDTO.setCuentaBeneficiarioFinal(cuenta.getCuentaBeneficiarioFinal());
                cuentaDTO.setCuentaIntermediario(cuenta.getCuentaIntermediario());
                cuentaDTO.setDetallesPago(cuenta.getDetallesPago());
                cuentaDTO.setNombreBancoBeneficiario(cuenta.getNombreBancoBeneficiario());
                cuentaDTO.setNombreBeneficiarioFinal(cuenta.getNombreBeneficiarioFinal());
                cuentaDTO.setNombreIntermediario(cuenta.getNombreIntermediario());
            }
        } catch (Exception ex) {

        }
        return cuentaDTO;
    }

    /**
     * Crea un objeto {@link CuentaRetiroInternacional} a partir de
     * un objeto {@link CuentaRetiroInternacionalDTO}
     *
     * @param cuentaDTO CuentaRetiroInternacional del cual se toman los datos.
     * @return el model creado.
     */
    public static CuentaRetiroInternacional crearCuentaRetiroInternacional(
            CuentaRetiroInternacionalDTO cuentaDTO) {
        CuentaRetiroInternacional cuenta = null;

        if (cuentaDTO != null) {
            cuenta = new CuentaRetiroInternacional();
            cuenta.setBancoBeneficiario(cuentaDTO.getBancoBeneficiario());
            cuenta.setBancoIntermediario(cuentaDTO.getBancoIntermediario());
            cuenta.setCuentaBeneficiario(cuentaDTO.getCuentaBeneficiario());
            cuenta.setCuentaBeneficiarioFinal(cuentaDTO.getCuentaBeneficiarioFinal());
            cuenta.setCuentaIntermediario(cuentaDTO.getCuentaIntermediario());
            cuenta.setDetallesPago(cuentaDTO.getDetallesPago());
            cuenta.setNombreBancoBeneficiario(cuentaDTO.getNombreBancoBeneficiario());
            cuenta.setNombreBeneficiarioFinal(cuentaDTO.getNombreBeneficiarioFinal());
            cuenta.setNombreCorto(cuentaDTO.getNombreCorto());
            cuenta.setNombreIntermediario(cuentaDTO.getNombreIntermediario());
            cuenta.setDivisa(DTOAssembler.crearDivisa(cuentaDTO.getDivisa()));
            cuenta.setEstado(DTOAssembler.crearEstadoInstruccionCat(cuentaDTO.getEstado()));
            cuenta.setIdCuentaRetiro(cuentaDTO.getIdCuentaRetiro());
            cuenta.setInstitucion(DTOAssembler.crearInstitucion(cuentaDTO.getInstitucion()));

            cuenta.setIdCuentaRetiroInt(cuentaDTO.getIdCuentaRetiroInt());

        }

        return cuenta;
    }

    /**
     * Crea un objeto {@link CuentaRetiroDTO} a partir de
     * un objeto {@link CuentaRetiro}
     *
     * @param cr CuentaRetiro del cual se toman los datos.
     * @return el model creado.
     */

    public static CuentaRetiroDTO crearCuentaRetiroDTO(CuentaRetiro cr) {
        CuentaRetiroDTO dto = new CuentaRetiroDTO();
        try {
            if (cr != null) {
                if (cr.getDivisa() != null) {
                    dto.setDivisa(crearDivisaDTO(cr.getDivisa()));
                }
                if (cr.getEstado() != null) {
                    dto.setEstado(crearEstadosCuentaDTO(cr.getEstado()));
                }
                if (cr.getIdCuentaRetiro() != null) {
                    dto.setIdCuentaRetiro(BigInteger.valueOf(cr.getIdCuentaRetiro()));
                } else {
                    dto.setIdCuentaRetiro(BigInteger.ZERO);
                }

                if (cr.getInstitucion() != null) {
                    dto.setInstitucion(crearInstitucionDTO(cr.getInstitucion()));
                }
                dto.setIdCuentaRetiroPorInstitucion(cr.getIdCuentaPorInstitucion());
                dto.setMaxMovsMensual(cr.getMaxMovsMensual());
                dto.setMontoMaxDiario(cr.getMontoMaxDiario());
                dto.setMontoMaxMensual(cr.getMontoMaxMensual());
                dto.setMontoMaxPorTran(cr.getMontoMaxPorTran());
            }
        } catch (Exception ex) {

        }

        return dto;
    }


    public static CuentaRetiro crearCuentaRetiro(CuentaRetiroDTO crDTO) {
        CuentaRetiro cuentaRetiro = null;

        if (crDTO != null) {
            cuentaRetiro = new CuentaRetiro();
            cuentaRetiro.setDivisa(DTOAssembler.crearDivisa(crDTO.getDivisa()));
            cuentaRetiro.setEstado(DTOAssembler.crearEstadoInstruccionCat(crDTO.getEstado()));
            cuentaRetiro.setIdCuentaRetiro(crDTO.getIdCuentaRetiro().longValue());
            cuentaRetiro.setInstitucion(DTOAssembler.crearInstitucion(crDTO.getInstitucion()));
            cuentaRetiro.setIdCuentaPorInstitucion(crDTO.getIdCuentaRetiroPorInstitucion());
            cuentaRetiro.setMaxMovsMensual(crDTO.getMaxMovsMensual());
            cuentaRetiro.setMontoMaxDiario(crDTO.getMontoMaxDiario());
            cuentaRetiro.setMontoMaxMensual(crDTO.getMontoMaxMensual());
            cuentaRetiro.setMontoMaxPorTran(crDTO.getMontoMaxPorTran());
        }

        return cuentaRetiro;
    }


    /**
     * Crea un objeto {@link BitacoraEdosCuentaRetiroDTO} a partir de
     * un objeto {@link BitacoraEstadosCuentaRetiro}
     *
     * @param bec BitacoraEstadosCuentaRetiro del cual se toman los datos.
     * @return el model creado.
     */
    public static BitacoraEdosCuentaRetiroDTO crearBitacoraEdosCuentaRetiroDTO(
            BitacoraEstadosCuentaRetiro bec) {
        BitacoraEdosCuentaRetiroDTO dto = null;

        if (bec != null) {
            dto = new BitacoraEdosCuentaRetiroDTO();
            dto.setCuentaRetiro(DTOAssembler.crearCuentaRetiroDTO(bec.getCuentaRetiro()));
            dto.setFechaAprobacion(bec.getFechaAprobacion());
            dto.setFechaAutorizacion(bec.getFechaAutorizacion());
            dto.setFechaCancelacion(bec.getFechaCancelacion());
            dto.setFechaCreacion(bec.getFechaCreacion());
            dto.setFechaLiberacion(bec.getFechaLiberacion());
            dto.setFechaModificacion(bec.getFechaModificacion());
            dto.setIdBitacoraEdosCuenta(bec.getIdBitacoraEdosCuenta());

            dto.setUsuarioAprobacion(bec.getAprobacionUsuario());
            dto.setUsuarioAutorizacion(bec.getAutorizacionUsuario());
            dto.setUsuarioCancelacion(bec.getCancelacionUsuario());
            dto.setUsuarioCreacion(bec.getCreacionUsuario());
            dto.setUsuarioLiberacion(bec.getLiberacionUsuario());
            dto.setUsuarioModificacion(bec.getModificacionUsuario());

            dto.setSerieAprobacion(bec.getAprobacionSerie());
            dto.setSerieAutorizacion(bec.getAutorizacionSerie());
            dto.setSerieCancelacion(bec.getCancelacionSerie());
            dto.setSerieCreacion(bec.getCreacionSerie());
            dto.setSerieLiberacion(bec.getLiberacionSerie());
            dto.setSerieModificacion(bec.getModificacionSerie());
        }

        return dto;
    }

    /**
     * Crea un objeto {@link CuentaRetiroNacional} a partir de
     * un objeto {@link CuentaRetiroEfectivoDTO}
     *
     * @param cuentaDTO CuentaRetiroNacional del cual se toman los datos.
     * @return el model creado.
     */
    public static CuentaRetiroNacional crearCuentaRetiroNacional(
            CuentaRetiroEfectivoDTO cuentaDTO) {
        CuentaRetiroNacional cuenta = null;

        if (cuentaDTO != null) {
            cuenta = new CuentaRetiroNacional();
            cuenta.setIdCuentaRetiroNal(cuentaDTO.getIdCuentaRetiroNal());
            cuenta.setDivisa(DTOAssembler.crearDivisa(cuentaDTO.getDivisa()));
            cuenta.setEstado(DTOAssembler.crearEstadoInstruccionCat(cuentaDTO.getEstado()));
            cuenta.setIdCuentaRetiro(cuentaDTO.getIdCuentaRetiro());
            cuenta.setInstitucion(DTOAssembler.crearInstitucion(cuentaDTO.getInstitucion()));
            cuenta.setMontoMaxDiario(cuentaDTO.getMontoMaximoDiario() == null ? null : cuentaDTO.getMontoMaximoDiario());
            cuenta.setMontoMaxMensual(cuentaDTO.getMontoMaximoMensual() == null ? null : BigDecimal.valueOf(cuentaDTO.getMontoMaximoMensual().doubleValue()));
            cuenta.setMontoMaxPorTran(cuentaDTO.getMontoMaximoXTran() == null ? null : BigDecimal.valueOf(cuentaDTO.getMontoMaximoXTran().doubleValue()));
            cuenta.setMaxMovsMensual(cuentaDTO.getNumMaximoMovsXMes() == null ? null : cuentaDTO.getNumMaximoMovsXMes().longValue());
            cuenta.setCuentaBeneficiario(cuentaDTO.getCuentaBeneficiario());
            cuenta.setNombreBeneficiario(cuentaDTO.getNombreBeneficiario());
        }

        return cuenta;
    }

    /**
     * Crea un objeto {@link CuentaRetiroInternacional} a partir de
     * un objeto {@link CuentaRetiroEfectivoDTO}
     *
     * @param cuentaDTO CuentaRetiroInternacional del cual se toman los datos.
     * @return el model creado.
     */
    public static CuentaRetiroInternacional crearCuentaRetiroInternacional(
            CuentaRetiroEfectivoDTO cuentaDTO) {
        CuentaRetiroInternacional cuenta = null;

        if (cuentaDTO != null) {
            cuenta = new CuentaRetiroInternacional();
            cuenta.setBancoBeneficiario(cuentaDTO.getBancoBeneficiario());
            cuenta.setBancoIntermediario(cuentaDTO.getBancoIntermediario());
            cuenta.setCuentaBeneficiario(cuentaDTO.getCuentaBeneficiario());
            cuenta.setCuentaBeneficiarioFinal(cuentaDTO.getCuentaBeneficiarioFinal());
            cuenta.setCuentaIntermediario(cuentaDTO.getCuentaIntermediario());
            cuenta.setDetallesPago(cuentaDTO.getDetallesPago());
            cuenta.setNombreBancoBeneficiario(cuentaDTO.getNombreBancoBeneficiario());
            cuenta.setNombreBeneficiarioFinal(cuentaDTO.getNombreBeneficiarioFinal());
            cuenta.setNombreCorto(cuentaDTO.getNombreCorto());
            cuenta.setNombreIntermediario(cuentaDTO.getNombreIntermediario());
            cuenta.setDivisa(DTOAssembler.crearDivisa(cuentaDTO.getDivisa()));
            cuenta.setEstado(DTOAssembler.crearEstadoInstruccionCat(cuentaDTO.getEstado()));
            cuenta.setIdCuentaRetiro(cuentaDTO.getIdCuentaRetiro());
            cuenta.setInstitucion(DTOAssembler.crearInstitucion(cuentaDTO.getInstitucion()));
            cuenta.setIdCuentaRetiroInt(cuentaDTO.getIdCuentaRetiroInt());
        }

        return cuenta;
    }

    /**
     * Crea un objeto {@link ParametrosDaliDTO} a partir de
     * un objeto {@link ParametrosDali}
     *
     * @param parametros ParametrosDali del cual se toman los datos.
     * @return el model creado.
     */
    public static ParametrosDaliDTO crearParametrosDaliDTO(ParametrosDali parametros) {
        ParametrosDaliDTO paramDTO = null;

        if (parametros != null) {
            paramDTO = new ParametrosDaliDTO();
            paramDTO.setIdParametro(parametros.getIdParametro());
            paramDTO.setHoraInicioModificacionCuentas(parametros.getHoraInicioModificacionCuentas());
            paramDTO.setHoraFinModificacionCuentas(parametros.getHoraFinModificacionCuentas());
            paramDTO.setCtasBComTipoInstituciones(parametros.getCtasBComTipoInstituciones());
        }

        return paramDTO;
    }

    /**
     * Crea un objeto {@link RetiroEfectivo} a partir de
     * un objeto {@link RetiroEfectivoDTO}
     *
     * @param dto RetiroEfectivoDTO del cual se toman los datos.
     * @return el model creado.
     */
    public static RetiroEfectivo crearRetiroEfectivo(RetiroEfectivoDTO dto) {
        RetiroEfectivo retiro = null;
        if (dto != null) {
            retiro = new RetiroEfectivo();

            retiro.setFechaAutorizacion(dto.getFechaAutorizacion());
            retiro.setFechaCreacion(dto.getFechaCreacion());
            retiro.setFechaLiberacion(dto.getFechaLiberacion());
            retiro.setFechaEnvio(dto.getFechaEnvio());
            retiro.setSerieCreacion(dto.getSerieCreacion());
            retiro.setSerieAutorizacion(dto.getSerieAutorizacion());
            retiro.setSerieLiberacion(dto.getSerieLiberacion());
            retiro.setUsuarioCreacion(dto.getUsuarioCreacion());
            retiro.setUsuarioAutorizacion(dto.getUsuarioAutorizacion());
            retiro.setUsuarioLiberacion(dto.getUsuarioLiberacion());
            retiro.setCreacionFirmada(dto.getCreacionFirmada());
            // retiro.setAutorizacionFirmada(dto.getAutorizacionFirmada());
            // retiro.setLiberacionFirmada(dto.getLiberacionFirmada());
            //retiroDTO.setIdCuentaBeneficiario(retiro.getIdCuentaBeneficiario());
            retiro.setIdRetiroEfectivoNal(dto.getIdRetiroEfectivoNal());
            retiro.setConceptoPago(dto.getConceptoPago());
            retiro.setImporteTraspaso(dto.getImporteTraspaso());
            retiro.setReferencia(dto.getReferencia());
            retiro.setReferenciaMensaje(dto.getReferenciaMensaje());
            retiro.setReferenciaOperacion(dto.getReferenciaOperacion());
            retiro.setCuentaBeneficiario(dto.getCuentaBeneficiario());
            retiro.setBoveda(crearBoveda(dto.getBoveda()));
            retiro.setDivisa(crearDivisa(dto.getDivisa()));
            retiro.setInstitucion(crearInstitucion(dto.getInstitucion()));
            retiro.setIdInstReceptor(crearInstitucion(dto.getIdInstReceptor()));
            retiro.setEstado(crearEstadoInstruccionCat(dto.getEstado()));
            retiro.setTipoInstruccion(crearTipoInstruccion(dto.getTipoInstruccion()));
            retiro.setOrigen(dto.getOrigen());
            retiro.setTipoOperacion(dto.getTipoOperacion());
            retiro.setCuentaEmisor(dto.getCuentaEmisor());

            //retiro.setNombreBeneficiario(dto.getNombreBeneficiario());
        }
        return retiro;
    }

    /**
     * Crea un objeto {@link RetiroEfectivo} a partir de
     * un objeto {@link RetiroEfectivo}
     *
     * @param retiro RetiroEfectivo del cual se toman los datos.
     * @return el model creado.
     */
    public static RetiroEfectivoDTO crearRetiroEfectivoDTO(RetiroEfectivo retiro) {
        RetiroEfectivoDTO retiroDTO = null;
        if (retiro != null) {
            retiroDTO = new RetiroEfectivoDTO();

            retiroDTO.setFechaAutorizacion(retiro.getFechaAutorizacion());
            retiroDTO.setFechaCreacion(retiro.getFechaCreacion());
            retiroDTO.setFechaLiberacion(retiro.getFechaLiberacion());
            retiroDTO.setFechaEnvio(retiro.getFechaEnvio());
            retiroDTO.setSerieCreacion(retiro.getSerieCreacion());
            retiroDTO.setSerieAutorizacion(retiro.getSerieAutorizacion());
            retiroDTO.setSerieLiberacion(retiro.getSerieLiberacion());
            retiroDTO.setUsuarioCreacion(retiro.getUsuarioCreacion());
            retiroDTO.setUsuarioAutorizacion(retiro.getUsuarioAutorizacion());
            retiroDTO.setUsuarioLiberacion(retiro.getUsuarioLiberacion());

            //retiroDTO.setIdCuentaBeneficiario(retiro.getIdCuentaBeneficiario());
            retiroDTO.setIdRetiroEfectivoNal(retiro.getIdRetiroEfectivoNal());
            retiroDTO.setConceptoPago(retiro.getConceptoPago());
            retiroDTO.setImporteTraspaso(retiro.getImporteTraspaso());
            retiroDTO.setReferencia(retiro.getReferencia());
            retiroDTO.setReferenciaMensaje(retiro.getReferenciaMensaje());
            retiroDTO.setReferenciaOperacion(retiro.getReferenciaOperacion());
            retiroDTO.setCuentaBeneficiario(retiro.getCuentaBeneficiario());
            retiroDTO.setBoveda(crearBovedaDTO(retiro.getBoveda()));
            retiroDTO.setDivisa(crearDivisaDTO(retiro.getDivisa()));
            retiroDTO.setInstitucion(crearInstitucionDTO(retiro.getInstitucion()));
            retiroDTO.setIdInstReceptor(crearInstitucionDTO(retiro.getIdInstReceptor()));
            retiroDTO.setEstado(crearEstadoInstruccionCatDTO(retiro.getEstado()));
            retiroDTO.setTipoInstruccion(crearTipoInstruccionDTO(retiro.getTipoInstruccion()));
            retiroDTO.setOrigen(retiro.getOrigen());
            retiroDTO.setTipoOperacion(retiro.getTipoOperacion());
            retiroDTO.setCuentaEmisor(retiro.getCuentaEmisor());

            //retiroDTO.setNombreBeneficiario(retiro.getNombreBeneficiario());
        }
        return retiroDTO;
    }

    /**
     * Crea un objeto {@link RetiroEfectivoInternacionalDTO} a partir de
     * un objeto {@link RetiroEfectivoInternacional}
     *
     * @param retiro RetiroEfectivoInternacional del cual se toman los datos.
     * @return el model creado.
     */
    public static RetiroEfectivoInternacionalDTO crearRetiroEfectivoInternacionalDTO(
            RetiroEfectivoInternacional retiro) {
        RetiroEfectivoInternacionalDTO retiroDTO = null;
        if (retiro != null) {
            retiroDTO = new RetiroEfectivoInternacionalDTO();
            retiroDTO.setConceptoPago(retiro.getConceptoPago());
            retiroDTO.setFechaAutorizacion(retiro.getFechaAutorizacion());
            retiroDTO.setFechaCreacion(retiro.getFechaCreacion());
            retiroDTO.setFechaLiberacion(retiro.getFechaLiberacion());
            retiroDTO.setIdCuentaBeneficiario(retiro.getIdCuentaBeneficiario());
            retiroDTO.setIdRetiroEfectivoInt(BigInteger.valueOf(retiro.getIdRetiroEfectivoInt()));
            retiroDTO.setImporteTraspaso(retiro.getImporteTraspaso());
            retiroDTO.setReferenciaMensaje(retiro.getReferenciaMensaje());
            retiroDTO.setReferenciaOperacion(retiro.getReferenciaOperacion());
        }
        return retiroDTO;
    }

    /**
     * @param uniqueResult
     * @return
     */
    public static ParametrosLiquidacionDTO crearParametrosLiquidacion(
            ParametrosLiquidacion uniqueResult) {

        ParametrosLiquidacionDTO liquidacionDTO = null;

        if (uniqueResult != null) {
            liquidacionDTO = new ParametrosLiquidacionDTO();
            liquidacionDTO.setIdConfiguracion(uniqueResult.getIdConfiguracion());
            liquidacionDTO.setProcesoFin(uniqueResult.getProcesoFin());
        }
        return liquidacionDTO;
    }

    public static PropiedadesDaliDTO crearPropiedadesDaliDTO(PropiedadesDali propiedadesDali) {

        PropiedadesDaliDTO dto = null;

        if (propiedadesDali != null) {
            dto = new PropiedadesDaliDTO();
            dto.setEnvironment(propiedadesDali.getPropiedadesDaliPK().getEnvironment());
            dto.setKey(propiedadesDali.getPropiedadesDaliPK().getKey());
            dto.setValue(propiedadesDali.getValue());
        }
        return dto;
    }


}

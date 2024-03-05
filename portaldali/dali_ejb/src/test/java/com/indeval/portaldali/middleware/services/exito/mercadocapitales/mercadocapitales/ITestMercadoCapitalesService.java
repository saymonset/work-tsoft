/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exito.mercadocapitales.mercadocapitales;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.mercadocapitales.ArchivoConciliacionMovimientosVO;
import com.indeval.portaldali.middleware.services.mercadocapitales.ArchivoConciliacionVO;
import com.indeval.portaldali.middleware.services.mercadocapitales.ConfirmaTraspasoContraPagoParams;
import com.indeval.portaldali.middleware.services.mercadocapitales.EstadoCuentaSocInvDetalleVO;
import com.indeval.portaldali.middleware.services.mercadocapitales.EstadoCuentaSocInvVO;
import com.indeval.portaldali.middleware.services.mercadocapitales.EstadoCuentaTotalVO;
import com.indeval.portaldali.middleware.services.mercadocapitales.EstadoCuentaUnicoParams;
import com.indeval.portaldali.middleware.services.mercadocapitales.EstadoCuentaUnicoVO;
import com.indeval.portaldali.middleware.services.mercadocapitales.GetCapturaTraspasoContraPagoParams;
import com.indeval.portaldali.middleware.services.mercadocapitales.GetCapturaTraspasoParams;
import com.indeval.portaldali.middleware.services.mercadocapitales.GetEstadoCuentaSociedadesInvParams;
import com.indeval.portaldali.middleware.services.mercadocapitales.GetEstadocuentaMercadoParams;
import com.indeval.portaldali.middleware.services.mercadocapitales.GetOperacionPendienteIncumplidaParams;
import com.indeval.portaldali.middleware.services.mercadocapitales.GetPosicionValorMerCapParams;
import com.indeval.portaldali.middleware.services.mercadocapitales.HistoricoEstadoCuentaTraspasoVO;
import com.indeval.portaldali.middleware.services.mercadocapitales.MercadoCapitalesService;
import com.indeval.portaldali.middleware.services.mercadocapitales.OperacionDiaCapitalesParams;
import com.indeval.portaldali.middleware.services.mercadocapitales.OperacionDiaCapitalesVO;
import com.indeval.portaldali.middleware.services.mercadocapitales.OperacionDiaDetalleCapitalesVO;
import com.indeval.portaldali.middleware.services.mercadocapitales.OperacionLiqFuturoTotalVO;
import com.indeval.portaldali.middleware.services.mercadocapitales.OperacionLiqFuturoVO;
import com.indeval.portaldali.middleware.services.mercadocapitales.OperacionPendienteIncumplidaVO;
import com.indeval.portaldali.middleware.services.mercadocapitales.PosicionValorTotalVO;
import com.indeval.portaldali.middleware.services.mercadocapitales.PosicionValorVO;
import com.indeval.portaldali.middleware.services.mercadocapitales.TraspasoCapitalesVO;
import com.indeval.portaldali.middleware.services.mercadocapitales.TraspasosContraPagosVO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import com.indeval.portaldali.persistence.util.DateUtil;
import com.indeval.portaldali.persistence.util.UtilsLog;

/**
 * Clase de prueba para el servicio de Garantias.
 *
 * @author Igor Mejia
 */
public class ITestMercadoCapitalesService extends BaseITestService {

    /**
     * Servicio a probar.
     */
    private MercadoCapitalesService mercadoCapitales;

    /** Log de bitacora */
    Logger logger = LoggerFactory.getLogger(ITestMercadoCapitalesService.class);

    /**
     * Inicializa lo que la prueba necesita para su ejecucion.
     */
    protected void onSetUp() throws Exception {
        super.onSetUp();
        if (mercadoCapitales == null) {
            mercadoCapitales = (MercadoCapitalesService) applicationContext
                    .getBean("mercadoCapitalesService");
        }
    }

    /**
     *
     * @throws BusinessException
     */
    public void ttestEJBCapturaTraspaso() throws BusinessException {
        log.debug(":::::::::Entro al test de captura traspasos:::::::::::::::");
        GetCapturaTraspasoParams params = new GetCapturaTraspasoParams();
        GregorianCalendar fecha = new GregorianCalendar(1, 1, 1);
        fecha.set(2007, 3, 14);
        log.debug("fecha fecha " + fecha.getTime());
        BigInteger folio;
        EmisionVO emision = new EmisionVO();
        AgenteVO traspasante = new AgenteVO();
        AgenteVO receptor = new AgenteVO();

        // Datos traspasante
        traspasante.setId("01");
        traspasante.setFolio("003");
        traspasante.setCuenta("0307");
        traspasante.setTipo("TERC");

        // traspasante.setId("01");
        // traspasante.setFolio("001");
        // traspasante.setCuenta("0109");
        // traspasante.setTipo("MERCAP");

        // Datos receptor

        receptor.setId("02");
        receptor.setFolio("014");
        receptor.setCuenta("6620");
        receptor.setTipo("PROP");

        // emision
        emision.setCupon("0041");
        emision.setEmisora("TELMEX");
        emision.setSerie("A");
        emision.setIdTipoValor("1");

        // parametros

        params.setTraspasante(traspasante);
        params.setReceptor(receptor);
        params.setEmision(emision);
        params.setCantidad(new BigDecimal("1"));
        params.setCveReporto("T");
        params.setDivisa(" ");
        params.setFecha(fecha.getTime());
        params.setFechaLiquidacion(fecha.getTime());
        // log.debug("fecha log "+params.getFecha());
        params.setFolioControl(new Integer("0"));
        params.setFolioDescripcion("0");
        params.setLlaveFolioMd(" ");
        // params.setTipoOperacion("activo"); //Para Traspasos de Ctas. Activos
        // S. I.

        try {
            log.debug("Entro en el try de la prueba");
            if (params.getTraspasante() == null) {
                log.debug("esta null el traspasante");
            }
            log.debug("traspasante id" + params.getTraspasante().getId());
            log.debug("traspasante folio" + params.getTraspasante().getFolio());
            log.debug("traspasante cuneta"
                    + params.getTraspasante().getCuenta());
            log.debug("traspasante tipo" + params.getTraspasante().getTipo());
            log.debug("Receptor id" + params.getReceptor().getId());
            log.debug("Receptor folio" + params.getReceptor().getFolio());
            log.debug("Receptor cuenta" + params.getReceptor().getCuenta());
            log.debug("Receptor tipo" + params.getReceptor().getTipo());
            log.debug("Emision cupon" + params.getEmision().getCupon());
            log.debug("Emision emisora" + params.getEmision().getEmisora());
            log.debug("Emision serie" + params.getEmision().getSerie());
            log.debug("Emision tv" + params.getEmision().getIdTipoValor());
            log.debug("cantidad" + params.getCantidad());
            log.debug("Cve reporto" + params.getCveReporto());
            log.debug("Divisa" + params.getDivisa());
            log.debug("Fecha" + params.getFecha());
            log.debug("FechaLiquidacion" + params.getFechaLiquidacion());
            log.debug("FolioControl" + params.getFolioControl());
            log.debug("FolioDesc" + params.getFolioDescripcion());
            log.debug("LlaveFolio" + params.getLlaveFolioMd());

            // Se invoca al servicio
            folio = mercadoCapitales.getCapturaTraspaso(params);
            assertNotNull(folio);

            log.debug("testGetCapturaTraspaso() " + folio);
            log
                    .debug(":::::: FIN DE LA PRUEBA DE METODO testGetCapturaTraspaso() :::::::");
            log.debug("Folio: " + folio);
        } catch (BusinessException e) {
            e.getMessage();
            e.printStackTrace();
        }
    }

    /**
     *
     * @throws BusinessException
     */
    public void testEJBGetPosicionValor() throws BusinessException {

        log
                .debug("::::::Entrando al metodo testEJBGetPosicionValor():::::::::");

        GetPosicionValorMerCapParams params = new GetPosicionValorMerCapParams();

        // seteo de agente
        AgenteVO agente = new AgenteVO("01", "038");
        // agente.setCuenta("0030");
        params.setAgenteFirmado(agente);

        // Setteo de la pagina
        PaginaVO paginaVO = new PaginaVO();
        paginaVO.setRegistrosXPag(new Integer(20));
        params.setPaginaVO(paginaVO);

        // seteo de emision
        EmisionVO emision = new EmisionVO();
        // emision.setIdTipoValor("00");
        // emision.setEmisora("AMX");
        // emision.setSerie("B");
        // emision.setCupon("0005");
        // params.setClaveValor(emision);

        // Ejecucion del servicio
        PosicionValorTotalVO posicionValorTotal[] = mercadoCapitales
                .getPosicionValorPlain(params);
        assertNotNull(posicionValorTotal);
        assertTrue(posicionValorTotal.length > 0);

        for (int z = 0; z < posicionValorTotal.length; z++) {
            // log.debug("VO# " + (z + 1));
            // //Se muestra la informacion del arreglo de cuentas y tv's
            // for (int i = 0; posicionValorTotal[z].getCuentas() != null
            // && i < posicionValorTotal[z].getCuentas().length; i++) {
            // log.debug("Cuenta: " + posicionValorTotal[z].getCuentas()[i]);
            // String[] tvs = (String[])
            // posicionValorTotal[z].getTipoValor().get(posicionValorTotal[z].getCuentas()[i]);
            // for (int j = 0; tvs != null && j < tvs.length; j++) {
            // log.debug("tv: " + tvs[j]);
            // }
            // }

            // Se muestra la informacion que devuelve el servicio

            assertNotNull(posicionValorTotal[z].getPagina());
            assertNotNull(posicionValorTotal[z].getPagina().getRegistros());
            assertFalse(posicionValorTotal[z].getPagina().getRegistros()
                    .isEmpty());
            for (int j = 0; j < posicionValorTotal[z].getPagina()
                    .getRegistros().size(); j++) {
                PosicionValorVO posValor = (PosicionValorVO) posicionValorTotal[z]
                        .getPagina().getRegistros().get(j);
                log.debug("----------------------");
                log.debug("Tipo Valor: "
                        + posValor.getClaveValor().getIdTipoValor());
                log.debug("Emisora: " + posValor.getClaveValor().getEmisora());
                log.debug("Serie: " + posValor.getClaveValor().getSerie());
                log.debug("Cupon: " + posValor.getClaveValor().getCupon());
                log.debug("Cuenta:" + posValor.getAgente().getCuenta());
                log.debug("Saldo disponible: " + posValor.getSaldoDisponible());
                log.debug("Ultimo hecho: " + posValor.getUltimoHecho());
                log.debug("Valor nominal: " + posValor.getValorNominal());
                log.debug("Valuacion mercado: "
                        + posValor.getValuacionMercado());
                log.debug("Valuacion nominal: "
                        + posValor.getValuacionNominal());
                log.debug("Cupon cortado: " + posValor.getCuponCortado());
            }
            log.debug("---------------------- Totales de la cuenta");
            log.debug("Total Saldo Disponible: "
                    + posicionValorTotal[z].getTotalSaldoDisponible());
            log.debug("Total Valuacion Mercado: "
                    + posicionValorTotal[z].getTotalValuacionMercado());
            log.debug("Total Valuacion Nominal: "
                    + posicionValorTotal[z].getTotalValuacionNominal());
            log.debug("Total de registros: "
                    + posicionValorTotal[z].getPagina().getTotalRegistros());
            log.debug("Total de registros (Cupon cortado F): "
                    + posicionValorTotal[z].getTotalRegCuponCortadoF());
            log.debug("Gran Total Saldo Disponible: "
                    + posicionValorTotal[z].getGranTotalSaldoDisponible());
            log.debug("Gran Total Saldo Disponible: "
                    + posicionValorTotal[z].getGranTotalValuacionMercado());
            log.debug("Gran Total Saldo Disponible: "
                    + posicionValorTotal[z].getGranTotalValuacionNominal());
        }

        log.debug(":::::::::Fin de el testEJBGetPosicionValor():::::::::::");
    }

    /**
     *
     * @throws BusinessException
     */
    public void ttestEJBGetOperacionPendienteIncumplida()
            throws BusinessException {

        log
                .debug("::::Entro en la prueba testEJBGetOperacionPendienteIncumplida()::::");
        PaginaVO paginaVO = new PaginaVO();
        GetOperacionPendienteIncumplidaParams params = new GetOperacionPendienteIncumplidaParams();
        AgenteVO agenteFirmado = new AgenteVO();
        String origen = null;
        String aplicacion = null;
        Boolean tipoOperacion = null;

        paginaVO.setOffset(new Integer(0));
        paginaVO.setRegistrosXPag(new Integer(8));

        // seteando agente
        // agenteFirmado.setCuenta("0307");
        agenteFirmado.setFolio("001");
        agenteFirmado.setId("01");

        // setando el origen
        origen = "03";

        // seteando el tipo de operacion
        tipoOperacion = new Boolean(true);
        log.debug("El valor de tipo operacion " + tipoOperacion);

        params.setAgenteFirmado(agenteFirmado);
        params.setAplicacion(aplicacion);
        params.setCompradorVendedor(tipoOperacion);
        params.setOrigen(origen);
        params.setPaginaVO(paginaVO);

        paginaVO = mercadoCapitales.getOperacionPendienteIncumplida(params);
        log.debug("PaginaVO " + ToStringBuilder.reflectionToString(paginaVO));
        assertNotNull(paginaVO);
        if (paginaVO.getRegistros().size() > 0) {
            log.debug("ENTRO EN EL IF ES MAYOR A CERO");
            log
                    .debug("VALOR DE TOTAL REGISTROS testEJBGetOperacionPendienteIncumplida()"
                            + paginaVO.getTotalRegistros());
            // assertTrue(paginaVO.getTotalRegistros().equals(new Integer(0)));
        }

        assertNotNull(paginaVO.getRegistros());
        assertFalse(paginaVO.getRegistros().isEmpty());

        log.debug(":::::::::antes del for::::::::::::::....");
        log.debug("Total de Totalregistros "
                + paginaVO.getTotalRegistros().intValue());
        log.debug("Registros " + paginaVO.getRegistros().size());
        log.debug("Total de registrosxpag " + paginaVO.getRegistrosXPag());
        OperacionPendienteIncumplidaVO operacionesPendIncumplidasVO = null;
        for (int i = 0; i < paginaVO.getRegistros().size(); i++) {
            log.debug(":::::::::antrando en el  for::::::::::::::....");
            operacionesPendIncumplidasVO = (OperacionPendienteIncumplidaVO) paginaVO
                    .getRegistros().get(i);
            if (paginaVO == null) {
                log.debug("esta null la pagina");
            }
            if (paginaVO != null) {
                log.debug("el tamaño del arreglo opePenIncum es: "
                        + paginaVO.getTotalRegistros());
                log.debug("getCuentaVendedor" + paginaVO.getRegistrosXPag());
                log.debug("Folio BMVtest "
                        + operacionesPendIncumplidasVO.getFolioBMV());
                log.debug("FolioIndtest "
                        + operacionesPendIncumplidasVO.getFolioInd());
                log.debug("Estado " + operacionesPendIncumplidasVO.getEstado());
                log.debug("idVendedor  "
                        + operacionesPendIncumplidasVO.getIdVendedor());
                log.debug("idComprador "
                        + operacionesPendIncumplidasVO.getIdComprador());
                log.debug("claveValor  "
                        + operacionesPendIncumplidasVO.getClaveValor());
                log.debug("cantidadOperada "
                        + operacionesPendIncumplidasVO.getCantidadOperada());
                log.debug("Liquidacion "
                        + operacionesPendIncumplidasVO.getLiquidacion());
                log.debug("Precio " + operacionesPendIncumplidasVO.getPrecio());
                log.debug("Fecha " + operacionesPendIncumplidasVO.getFecha());
                log.debug("Fecha concertacion "
                        + operacionesPendIncumplidasVO.getFechaConcertacion());
                log.debug("Fecha liquidacion "
                        + operacionesPendIncumplidasVO.getFechaLiqOrig());
                log.debug("OR " + operacionesPendIncumplidasVO.getOrigen());
                log.debug("TO " + operacionesPendIncumplidasVO.getTo());
                log.debug("ST " + operacionesPendIncumplidasVO.getSt());
                log
                        .debug("*********************************************************************");
            }
        }
        log
                .debug(":::::::Fin de la prueba testEJBGetOperacionPendienteIncumplida::::::");
    }

    /**
     *
     * @throws ParseException
     * @throws BusinessException
     */
    public void ttestEJBGetOperacionLiqFuturo() throws ParseException,
            BusinessException {

        log
                .debug("::::::::::::TEST Entro en la prueba testEJBGetOperacionLiqFuturo()::::::::::");

        OperacionLiqFuturoTotalVO opeLiqFutTotVO = new OperacionLiqFuturoTotalVO();
        AgenteVO agente = new AgenteVO();
        agente.setId("01");
        agente.setFolio("001");

        PaginaVO paginaVO = new PaginaVO();
        paginaVO.setOffset(new Integer(0));
        paginaVO.setRegistrosXPag(new Integer(10));

        OperacionLiqFuturoVO opLiqFut = new OperacionLiqFuturoVO();

        // String tipoValor = "1";
        String tipoFecha = "liquidacion";
        Calendar calendar = Calendar.getInstance();
        calendar.set(2007, Calendar.FEBRUARY, 27, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        // Date fechaLiquidacion = calendar.getTime();
        String fechaLiquidacion = "Tue Feb 27 00:00:00 GMT 2007";
        Date fechaliquidacion2 = DateUtil.convierteStringToDate("27/02/2007");
        log.debug("fecha " + fechaLiquidacion);
        /* Ejecuta el DAO */
        opeLiqFutTotVO = mercadoCapitales.getOperacionLiqFuturo(agente,
                tipoFecha, fechaliquidacion2, paginaVO);

        assertNotNull(opeLiqFutTotVO);
        assertNotNull(opeLiqFutTotVO.getPaginaVO());
        log.debug("Tamanio del arreglo de paginas "
                + opeLiqFutTotVO.getPaginaVO().length);
        log.debug("TipoValor " + opeLiqFutTotVO.getTipoValor());
        log.debug("Totalmovimientos " + opeLiqFutTotVO.getTotalMovimiento());

        for (int i = 0; i < opeLiqFutTotVO.getPaginaVO().length; i++) {
            log.debug("Agrupado por tivo de valor["
                    + opeLiqFutTotVO.getTipoValor()[i].toString() + "]");

            assertNotNull(opeLiqFutTotVO.getPaginaVO()[i]);
            assertNotNull(opeLiqFutTotVO.getPaginaVO()[i].getRegistros());
            assertFalse(opeLiqFutTotVO.getPaginaVO()[i].getRegistros().isEmpty());
            log.debug("Tamanio del arreglo de paginas "
                    + opeLiqFutTotVO.getPaginaVO()[i].getRegistros().size());
            for (int j = 0; j < opeLiqFutTotVO.getPaginaVO()[i].getRegistros()
                    .size(); j++) {
                opLiqFut = (OperacionLiqFuturoVO) opeLiqFutTotVO.getPaginaVO()[i]
                        .getRegistros().get(j);

                assertNotNull(opLiqFut);

                log.debug("FolioTest " + opLiqFut.getFolio());
                log.debug("FolioBMVTest " + opLiqFut.getFolioBMV());
                log.debug("IdVendedorTest " + opLiqFut.getIdVendedor());
                log.debug("FolioVendedorTest " + opLiqFut.getFolioVendedor());
                log.debug("CuentaVendedorTest " + opLiqFut.getCuentaVendedor());
                log.debug("NombreInstVendedorTest "
                        + opLiqFut.getNombreInstitucionVendedor());
                log.debug("IdCompradorTest " + opLiqFut.getIdComprador());
                log.debug("FolioComprador " + opLiqFut.getFolioComprador());
                log.debug("CuentaCompradorTest "
                        + opLiqFut.getCuentaComprador());
                log.debug("NombreCompradorTest "
                        + opLiqFut.getNombreInstitucionComprador());
                log.debug("EmisoraTest " + opLiqFut.getEmisora());
                log.debug("SerieTest " + opLiqFut.getSerie());
                log.debug("TipoOperacionTest " + opLiqFut.getTipoOperacion());
                log.debug("CantidadTitulosTest "
                        + opLiqFut.getCantidadTitulos());
                log.debug("PrecioTest " + opLiqFut.getPrecio());
                log.debug("IporteTest " + opLiqFut.getImporte());
                log.debug("tipoValorTest "
                        + opeLiqFutTotVO.getTipoValor()[i].toString());
                log.debug("tipoValorTest "
                        + opeLiqFutTotVO.getTotalMovimiento());
                log
                        .debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

            }
        }
        log.debug("tipoValorTest " + opeLiqFutTotVO.getTipoValor());
        log
                .debug("totalMovimientosTest "
                        + opeLiqFutTotVO.getTotalMovimiento());
        log
                .debug("::::::::::::TEST Fin de la prueba testEJBGetOperacionLiqFuturo()::::::::::");
    }

    // public void ttestEJBGetEstadoCuentaMercadoCapital() throws Exception{
    //
    // log.debug("Entrando a testEJBGetEstadoCuentaMercadoCapital()");
    //
    // //Setteo de agenteVO
    // //AgenteVO agenteVO = new AgenteVO("02","013","5001");
    // AgenteVO agenteVOSinCuenta = new AgenteVO("01","003");
    //
    // //Setteo del Origen
    // String origen = "01";
    //
    // //Setteo de aplicacion
    // String aplicacion = "MERSECMC";
    //
    // //Setteo de la paginaVO
    // PaginaVO paginaVO = new PaginaVO();
    //
    // //Setteo de la emisionVO
    // EmisionVO emisionVO = new EmisionVO();
    //
    // //Setteo de la Fecha de Operacion
    // Date fechaOperacion = null;
    // Calendar calendar = Calendar.getInstance();
    // calendar.set(2007,Calendar.MARCH,22,0,0,0);
    // calendar.set(Calendar.MILLISECOND, 0);
    // fechaOperacion = calendar.getTime();
    //
    // //seteo de tipoOperacion
    // String [] tipoOperacion = new String[4];
    // tipoOperacion[0] = "V";
    // tipoOperacion[1] = "T";
    // tipoOperacion[2] = "D";
    // tipoOperacion[3] = "R";
    //
    // //Seteo del objeto de parametros
    // GetEstadocuentaMercadoParams params = new GetEstadocuentaMercadoParams();
    // // params.setAgenteFirmado(agenteVO);
    // // params.setAplicacion(aplicacion);
    // params.setClaveValor(emisionVO);
    // params.setFechaOperacion(fechaOperacion);
    // params.setOrigen(origen);
    // params.setPaginaVO(paginaVO);
    // //params.setTipoOperacion(tipoOperacion);
    //
    // // log.debug("Imprimiendo el params enviado [" +
    // ToStringBuilder.reflectionToString(params) + "]");
    //
    // // log.debug("Llamando al servicio con export=false");
    // // long startTime = System.currentTimeMillis();
    // // EstadoCuentaTotalVO[] estadoCuenta =
    // mercadoCapitales.getEstadoCuentaMercadoCapital(params);
    // // long endTime = System.currentTimeMillis();
    // // double milisegundos = (endTime - startTime);
    // // log.debug("Milisegundos en responder el servicio
    // DetalleParcialidades():" +
    // // milisegundos);
    //
    // // assertNotNull(estadoCuenta);
    // // assertTrue(estadoCuenta.length >0);
    // // log.debug("El arreglo de elementos devuelto por el servicio tiene [" +
    // estadoCuenta.length + "] elementos");
    // // UtilsLog.logElementosArreglo(estadoCuenta, true);
    //
    // // log.debug("Llamando al servicio con export=true");
    // // params.setExport(true);
    // // startTime = System.currentTimeMillis();
    // // estadoCuenta = mercadoCapitales.getEstadoCuentaMercadoCapital(params);
    // // endTime = System.currentTimeMillis();
    // // milisegundos = (endTime - startTime);
    // // log.debug("Milisegundos en responder el servicio
    // DetalleParcialidades():" +
    // // milisegundos);
    //
    // // assertNotNull(estadoCuenta);
    // // assertTrue(estadoCuenta.length >0);
    // // log.debug("El arreglo de elementos devuelto por el servicio tiene [" +
    // estadoCuenta.length + "] elementos");
    // // UtilsLog.logElementosArreglo(estadoCuenta, false);
    //
    // // log.debug("Ejecucion exitosa de
    // testEJBGetEstadoCuentaMercadoCapital()");
    //
    // log.debug("Llamando al servicio con export=true sin pasarle la cuenta");
    // params.setAgenteFirmado(agenteVOSinCuenta);
    // long startTime = System.currentTimeMillis();
    // EstadoCuentaTotalVO[] estadoCuenta =
    // mercadoCapitales.getEstadoCuentaMercadoCapital(params);
    // long endTime = System.currentTimeMillis();
    // double milisegundos = (endTime - startTime);
    // log.debug("Milisegundos en responder el servicio DetalleParcialidades():"
    // +
    // milisegundos);
    //
    // assertNotNull(estadoCuenta);
    // assertTrue(estadoCuenta.length >0);
    // log.debug("El arreglo de elementos devuelto por el servicio tiene [" +
    // estadoCuenta.length + "] elementos");
    // UtilsLog.logElementosArreglo(estadoCuenta, true);
    // UtilsLog.logElementosArreglo(estadoCuenta, false);
    // for (int i = 0; i < estadoCuenta.length; i++) {
    // if(i<5 && estadoCuenta[i].getPaginaVO()!= null){
    // log.debug("TOTALES: " +
    // ToStringBuilder.reflectionToString(estadoCuenta[i].getTotales()));
    // UtilsLog.logElementosLista(estadoCuenta[i].getPaginaVO().getRegistros());
    // }
    // }
    //
    // log.debug("Ejecucion exitosa de testEJBGetEstadoCuentaMercadoCapital()");
    //
    // }

    /**
     *
     */
    public void ttestEJBGetNuevoEstadoCuentaMercadoCapital() throws Exception {

        log.debug("Entrando a testEJBGetEstadoCuentaMercadoCapital()");

        // Setteo de agenteVO
        AgenteVO agenteVO = new AgenteVO("01", "012", "0315");
        AgenteVO agenteVOSinCuenta = new AgenteVO("01", "003");

        // Setteo del Origen
        // String origen = "01";

        // Setteo de aplicacion
        // String aplicacion = "MERSECMC";

        // Setteo de la paginaVO
        PaginaVO paginaVO = new PaginaVO();

        // Setteo de la emisionVO
        EmisionVO emisionVO = new EmisionVO();

        // Setteo de la Fecha de Operacion
        Date fechaOperacion = null;
        Calendar calendar = Calendar.getInstance();
        calendar.set(2007, Calendar.JUNE, 29, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        fechaOperacion = calendar.getTime();

        // seteo de tipoOperacion
        String[] tipoOperacion = new String[4];
        tipoOperacion[0] = "V";
        tipoOperacion[1] = "T";
        tipoOperacion[2] = "D";
        tipoOperacion[3] = "R";

        // Seteo del objeto de parametros
        GetEstadocuentaMercadoParams params = new GetEstadocuentaMercadoParams();
        params.setAgenteFirmado(agenteVO);
        // params.setAplicacion(aplicacion);
        params.setClaveValor(emisionVO);
        params.setFechaOperacion(fechaOperacion);
        // params.setOrigen(origen);
        params.setPaginaVO(paginaVO);
        // params.setTipoOperacion(tipoOperacion);
        params.setExport(false);

        // log.debug("Imprimiendo el params enviado [" +
        // ToStringBuilder.reflectionToString(params) + "]");

        // log.debug("Llamando al servicio con export=false");
        // long startTime = System.currentTimeMillis();
        // EstadoCuentaTotalVO[] estadoCuenta =
        // mercadoCapitales.getEstadoCuentaMercadoCapital(params);
        // long endTime = System.currentTimeMillis();
        // double milisegundos = (endTime - startTime);
        // log.debug("Milisegundos en responder el servicio
        // DetalleParcialidades():" +
        // milisegundos);

        // assertNotNull(estadoCuenta);
        // assertTrue(estadoCuenta.length >0);
        // log.debug("El arreglo de elementos devuelto por el servicio tiene ["
        // + estadoCuenta.length + "] elementos");
        // UtilsLog.logElementosArreglo(estadoCuenta, true);

        // log.debug("Llamando al servicio con export=true");
        // params.setExport(true);
        // startTime = System.currentTimeMillis();
        // estadoCuenta =
        // mercadoCapitales.getEstadoCuentaMercadoCapital(params);
        // endTime = System.currentTimeMillis();
        // milisegundos = (endTime - startTime);
        // log.debug("Milisegundos en responder el servicio
        // DetalleParcialidades():" +
        // milisegundos);

        // assertNotNull(estadoCuenta);
        // assertTrue(estadoCuenta.length >0);
        // log.debug("El arreglo de elementos devuelto por el servicio tiene ["
        // + estadoCuenta.length + "] elementos");
        // UtilsLog.logElementosArreglo(estadoCuenta, false);

        // log.debug("Ejecucion exitosa de
        // testEJBGetEstadoCuentaMercadoCapital()");

        log.debug("Llamando al servicio con export=true sin pasarle la cuenta");
        params.setAgenteFirmado(agenteVOSinCuenta);
        long startTime = System.currentTimeMillis();
        EstadoCuentaTotalVO[] estadoCuenta = mercadoCapitales
                .getNuevoEstadoCuentaMercadoCapital(params);
        long endTime = System.currentTimeMillis();
        double milisegundos = (endTime - startTime);
        log
                .debug("Milisegundos en responder el servicio DetalleParcialidades():"
                        + milisegundos);

        assertNotNull(estadoCuenta);
        assertTrue(estadoCuenta.length > 0);
        log.debug("El arreglo de elementos devuelto por el servicio tiene ["
                + estadoCuenta.length + "] elementos");
        UtilsLog.logElementosArreglo(estadoCuenta, true);
        UtilsLog.logElementosArreglo(estadoCuenta, false);
        for (int i = 0; i < estadoCuenta.length; i++) {
            if (i < 5 && estadoCuenta[i].getPaginaVO() != null) {
                log.debug("TOTALES: "
                        + ToStringBuilder.reflectionToString(estadoCuenta[i]
                                .getTotales()));
                UtilsLog.logElementosLista(estadoCuenta[i].getPaginaVO()
                        .getRegistros());
            }
        }

        log
                .debug("Ejecucion exitosa de testEJBGetEstadoCuentaMercadoCapital()");

    }

    /**
     *
     * @throws BusinessException
     */
    public void ttestEJBCapturaTraspasoContraPago() throws BusinessException {

        log.debug(":::::::::captura traspaso contra pago:::::::::::::::");

        GetCapturaTraspasoContraPagoParams params = new GetCapturaTraspasoContraPagoParams();
        GregorianCalendar fecha = new GregorianCalendar(1, 1, 1);
        fecha.set(2007, 3, 14);

        log.debug("fecha " + fecha.getTime());
        Integer folio;
        EmisionVO emision = new EmisionVO();
        AgenteVO traspasante = new AgenteVO();
        AgenteVO receptor = new AgenteVO();

        // Datos traspasante
        traspasante.setId("01");
        traspasante.setFolio("003");
        traspasante.setCuenta("0307");
        traspasante.setTipo("TERC");

        // Datos receptor
        receptor.setId("02");
        receptor.setFolio("014");
        receptor.setCuenta("6620");
        receptor.setTipo("PROP");

        // emision
        emision.setIdTipoValor("1");
        emision.setEmisora("TELMEX");
        emision.setSerie("L");
        emision.setCupon("0041");

        // parametros
        params.setTraspasante(traspasante);
        params.setReceptor(receptor);
        params.setEmision(emision);
        params.setCantidad(new BigDecimal("101"));
        params.setPrecioTitulo(new BigDecimal("11"));
        params.setCveReporto("A");
        params.setFechaLiquidacion(fecha.getTime());
        params.setLiquidacion(new Integer("0"));
        params.setUsuario("PORTAL LEGADO");
        params.setNombreUsuario("PORTAL");

        try {
            log.debug("Entro en el try de la prueba");
            if (params.getTraspasante() == null) {
                log.debug("esta null el traspasante");
            }
            log.debug("traspasante id" + params.getTraspasante().getId());
            log.debug("traspasante folio" + params.getTraspasante().getFolio());
            log.debug("traspasante cuneta"
                    + params.getTraspasante().getCuenta());
            log.debug("traspasante tipo" + params.getTraspasante().getTipo());
            log.debug("Receptor id" + params.getReceptor().getId());
            log.debug("Receptor folio" + params.getReceptor().getFolio());
            log.debug("Receptor cuenta" + params.getReceptor().getCuenta());
            log.debug("Receptor tipo" + params.getReceptor().getTipo());
            log.debug("Emision cupon" + params.getEmision().getCupon());
            log.debug("Emision emisora" + params.getEmision().getEmisora());
            log.debug("Emision serie" + params.getEmision().getSerie());
            log.debug("Emision tv" + params.getEmision().getIdTipoValor());
            log.debug("cantidad" + params.getCantidad());
            log.debug("Cve reporto" + params.getCveReporto());
            log.debug("Precio Titulo" + params.getPrecioTitulo());
            log.debug("FechaLiquidacion" + params.getFechaLiquidacion());
            log.debug("Liquidacion" + params.getLiquidacion());
            log.debug("Usuario" + params.getUsuario());
            log.debug("NombreUsuario" + params.getNombreUsuario());

            // Se invoca al servicio
            folio = mercadoCapitales.capturaTraspasoContraPago(params);
            assertNotNull(folio);

            log.debug("testGetCapturaTraspasoContraPago() " + folio);
            log
                    .debug(":::::: FIN DE LA PRUEBA DE METODO testGetCapturaTraspasoContraPago() :::::::");
            log.debug("Folio: " + folio);
        } catch (BusinessException e) {
            e.getMessage();
            e.printStackTrace();
        }
    }

    /**
     * Prueba la lista de confirmacion de traspaso contra pago
     *
     * @throws BusinessException
     */
    public void ttestEJBGetListaConfirmacionTraspasoContraPago()
            throws BusinessException {

        log.debug("Entrando al metodo testEJBGetListaConfirmacionApertura()");

        assertNotNull(mercadoCapitales);

        log.debug("Probando con un agente valido");

        try {
            AgenteVO agente = new AgenteVO();
            agente.setId("01");
            agente.setFolio("001");
            TraspasosContraPagosVO[] traspVsPagoVO = mercadoCapitales
                    .getListaConfirmacionTraspasoContraPago(agente, null);
            assertNotNull(traspVsPagoVO);
            for (int i = 0; i < traspVsPagoVO.length; i++) {
                log.debug("Traspasante==> "
                        + traspVsPagoVO[i].getTraspasante().getId() + ", "
                        + traspVsPagoVO[i].getTraspasante().getFolio() + ", "
                        + traspVsPagoVO[i].getTraspasante().getCuenta()
                        + "Receptor==> "
                        + traspVsPagoVO[i].getReceptor().getId() + ", "
                        + traspVsPagoVO[i].getReceptor().getFolio() + ", "
                        + traspVsPagoVO[i].getReceptor().getCuenta()
                        + " Emision==> "
                        + traspVsPagoVO[i].getEmision().getIdTipoValor() + ", "
                        + traspVsPagoVO[i].getEmision().getEmisora() + ", "
                        + traspVsPagoVO[i].getEmision().getSerie() + ", "
                        + traspVsPagoVO[i].getEmision().getCupon()
                        + " cantidad operada==> "
                        + traspVsPagoVO[i].getCantidadOperada()
                        + " fechaLiquidacion==> "
                        + traspVsPagoVO[i].getFechaLiquidacion()
                        + " folioControl==> " + traspVsPagoVO[i].getFolio()
                        + " precioAdquisicion==> "
                        + traspVsPagoVO[i].getPrecioAdquisicion()
                        + " fechaAdquisicion==> "
                        + traspVsPagoVO[i].getFechaAquisicion());
            }
            log.debug("Total de registros==> " + traspVsPagoVO.length);
        } catch (BusinessException be) {
            log
                    .debug(be.getMessage()
                            + " - deberia mostrar datos con los criterios de seleccion");
        }

    }

    /**
     * Prueba la confirmacion de traspasos contra pago
     *
     * @throws BusinessException
     */
    public void ttestEJBConfirmaTraspasoContraPago() throws BusinessException {

        log.debug("Entrando al metodo testEJBConfirmaTraspasoContraPago()");

        assertNotNull(mercadoCapitales);

        log.debug("Probando con un params valido");

        try {
            ConfirmaTraspasoContraPagoParams[] arregloParams = new ConfirmaTraspasoContraPagoParams[1];
            ConfirmaTraspasoContraPagoParams params = new ConfirmaTraspasoContraPagoParams();
            AgenteVO agente = new AgenteVO();
            agente.setId("01");
            agente.setFolio("001");
            agente.setCuenta("0109");
            params.setTraspasante(agente);
            params.setFolio(new Integer("6"));
            Calendar fechaLiquidacion = Calendar.getInstance();
            fechaLiquidacion.set(2007, Calendar.MARCH, 30);
            params.setFechaLiquidacion(fechaLiquidacion.getTime());
            arregloParams[0] = params;
            // params = new ConfirmaTraspasoContraPagoParams();
            // agente = new AgenteVO();
            // agente.setId("01");
            // agente.setFolio("003");
            // agente.setCuenta("0307");
            // params.setTraspasante(agente);
            // params.setFolio(new Integer("6"));
            // fechaLiquidacion = Calendar.getInstance();
            // fechaLiquidacion.set(2007, Calendar.MARCH, 30);
            // params.setFechaLiquidacion(fechaLiquidacion.getTime());
            // arregloParams[1] = params;
            boolean confTraspVsPago = mercadoCapitales
                    .confirmaTraspasoContraPago(arregloParams);
            if (confTraspVsPago) {
                log.debug("La actualizacion se ha realizado con exito");
            } else {
                log
                        .debug("No se ha realizado la actualizcion de los registros");
            }
        } catch (BusinessException be) {
            log
                    .debug(be.getMessage()
                            + " - deberia mostrar datos con los criterios de seleccion");
        }

        log.debug("Saliendo del metodo testEJBConfirmaTraspasoContraPago()");

    }

    /**
     * Prueba el archivo de conciliacion para un agente firmado (id, folio,
     * cuenta)
     *
     * @throws BusinessException
     */
    public void testEJBArchivoConciliacion() throws BusinessException {

        log.debug("Entrando al metodo testEJBArchivoConciliacion()");

        assertNotNull(mercadoCapitales);

        log.debug("Probando con agente - id, folio, cuenta");

        AgenteVO agenteVO = new AgenteVO();
        agenteVO.setId("01");
        agenteVO.setFolio("001");
        agenteVO.setCuenta("0109");
        PaginaVO paginaVO = new PaginaVO();
        paginaVO.setOffset(Integer.valueOf("11"));
        paginaVO.setRegistrosXPag(Integer.valueOf("10"));
        EmisionVO emisionVO = new EmisionVO();
        // emisionVO.setIdTipoValor("WI");
        // emisionVO.setEmisora("IPC609R");
        // emisionVO.setSerie("DC187");
        PaginaVO pagina = mercadoCapitales.archivoConciliacion(agenteVO,
        		emisionVO, paginaVO);
        assertNotNull(pagina);
        assertNotNull(pagina.getRegistros());
        assertFalse(pagina.getRegistros().isEmpty());
        for (Iterator iter = pagina.getRegistros().iterator(); iter
        .hasNext();) {
        	ArchivoConciliacionVO element = (ArchivoConciliacionVO) iter
        	.next();
        	log
        	.debug("Registro: "
        			+ ReflectionToStringBuilder
        			.reflectionToString(element));
        }
        log.debug("Total de registros: " + pagina.getTotalRegistros());

        log.debug("Saliendo del metodo testEJBArchivoConciliacion()");

    }

    /**
     *
     * @throws BusinessException
     */
    public void ttestEJBArchivoConciliacionMovimientos()
            throws BusinessException {

        log.debug("Entrando al metodo testEJBArchivoConciliacionMovimientos()");

        assertNotNull(mercadoCapitales);

        log.debug("Probando el servicio");

        try {
            AgenteVO agenteVO = new AgenteVO();
            agenteVO.setId("01");
            agenteVO.setFolio("003");

            agenteVO.setCuenta("0307");
            PaginaVO pagina = mercadoCapitales.archivoConciliacionMovimientos(
                    agenteVO, null);
            assertNotNull(pagina);
            assertNotNull(pagina.getRegistros());
            assertFalse(pagina.getRegistros().isEmpty());
            for (Iterator iter = pagina.getRegistros().iterator(); iter
                    .hasNext();) {
                ArchivoConciliacionMovimientosVO element = (ArchivoConciliacionMovimientosVO) iter
                        .next();
                log
                        .debug("Registro: "
                                + ReflectionToStringBuilder
                                        .reflectionToString(element));
            }
            log.debug("Total de registros: " + pagina.getTotalRegistros());
        } catch (BusinessException be) {
            log.debug(be.getMessage());

        }

        log
                .debug("Saliendo del metodo testEJBArchivoConciliacionMovimientos()");

    }

    /**
     *
     * @throws BusinessException
     */
    public void ttestEJBArchivoConciliacionDetalle() throws BusinessException {

        log.debug("Entrando al metodo testEJBArchivoConciliacionDetalle()");

        assertNotNull(mercadoCapitales);

        log.debug("Probando el servicio");

        try {
            AgenteVO agenteVO = new AgenteVO();
            agenteVO.setId("01");
            agenteVO.setFolio("003");
            agenteVO.setCuenta("0307");
            //agenteVO.setCuenta("");
            EmisionVO emisionVO = new EmisionVO();
            emisionVO.setIdTipoValor("1A");
            emisionVO.setEmisora("BP");
            emisionVO.setSerie("N");
            emisionVO.setCupon("0000");
            PaginaVO pagina = mercadoCapitales.archivoConciliacionDetalle(
                    agenteVO, emisionVO, null);
            assertNotNull(pagina);
            assertNotNull(pagina.getRegistros());
            assertFalse(pagina.getRegistros().isEmpty());
            for (Iterator iter = pagina.getRegistros().iterator(); iter
                    .hasNext();) {
                ArchivoConciliacionMovimientosVO element = (ArchivoConciliacionMovimientosVO) iter
                        .next();
                log
                        .debug("Registro: "
                                + ReflectionToStringBuilder
                                        .reflectionToString(element));
            }
            log.debug("Total de registros: " + pagina.getTotalRegistros());
        } catch (BusinessException be) {
            log.debug(be.getMessage());

        }

        log.debug("Saliendo del metodo testEJBArchivoConciliacionDetalle()");

    }

    /**
     *
     * @throws BusinessException
     */
    public void testtEJBCapturaTraspasoTLPFechaValor() throws BusinessException {

        log.debug(":::::::::captura traspaso TLP fecha valor:::::::::::::::");

        GetCapturaTraspasoContraPagoParams params = new GetCapturaTraspasoContraPagoParams();
        GregorianCalendar fecha = new GregorianCalendar(1, 1, 1);
        fecha.set(2007, 3, 2);

        log.debug("fecha " + fecha.getTime());
        Integer folio;
        EmisionVO emision = new EmisionVO();
        AgenteVO traspasante = new AgenteVO();
        AgenteVO receptor = new AgenteVO();

        // Datos traspasante
        traspasante.setId("01");
        traspasante.setFolio("001");
        traspasante.setCuenta("0109");
        traspasante.setTipo("TERC");

        // Datos receptor
        receptor.setId("01");
        receptor.setFolio("003");
        receptor.setCuenta("0307");
        receptor.setTipo("PROP");

        // emision
        emision.setIdTipoValor("1");
        emision.setEmisora("TELMEX");
        emision.setSerie("L");
        emision.setCupon("0041");

        // parametros
        params.setTraspasante(traspasante);
        params.setReceptor(receptor);
        params.setEmision(emision);
        params.setCveReporto("T");
        params.setFechaLiquidacion(fecha.getTime());
        params.setLiquidacion(new Integer("0"));
        params.setUsuario("PORTAL LEGADO");
        params.setNombreUsuario("PORTAL");

        try {
            log.debug("Entro en el try de la prueba");
            if (params.getTraspasante() == null) {
                log.debug("esta null el traspasante");
            }
            log.debug("traspasante id" + params.getTraspasante().getId());
            log.debug("traspasante folio" + params.getTraspasante().getFolio());
            log.debug("traspasante cuneta"
                    + params.getTraspasante().getCuenta());
            log.debug("traspasante tipo" + params.getTraspasante().getTipo());
            log.debug("Receptor id" + params.getReceptor().getId());
            log.debug("Receptor folio" + params.getReceptor().getFolio());
            log.debug("Receptor cuenta" + params.getReceptor().getCuenta());
            log.debug("Receptor tipo" + params.getReceptor().getTipo());
            log.debug("Emision cupon" + params.getEmision().getCupon());
            log.debug("Emision emisora" + params.getEmision().getEmisora());
            log.debug("Emision serie" + params.getEmision().getSerie());
            log.debug("Emision tv" + params.getEmision().getIdTipoValor());
            log.debug("cantidad" + params.getCantidad());
            log.debug("Cve reporto" + params.getCveReporto());
            log.debug("Precio Titulo" + params.getPrecioTitulo());
            log.debug("FechaLiquidacion" + params.getFechaLiquidacion());
            log.debug("Liquidacion" + params.getLiquidacion());
            log.debug("Usuario" + params.getUsuario());
            log.debug("NombreUsuario" + params.getNombreUsuario());

            // Se invoca al servicio
            folio = mercadoCapitales.capturaTraspasoTLPFechaValor(params);
            assertNotNull(folio);

            log.debug("testGetCapturaTLPFechaValor() " + folio);
            log
                    .debug(":::::: FIN DE LA PRUEBA DE METODO testGetCapturaCapturaTLPFechaValor() :::::::");
            log.debug("Folio: " + folio);
        } catch (BusinessException e) {
            e.getMessage();
            e.printStackTrace();
        }
    }

    /**
     *
     * @throws BusinessException
     */
    public void ttestEJBEstadoCuentaUnicoConCuenta() throws BusinessException {
        log.debug("Entrando a testEJBEstadoCuentaUnico...");
        EstadoCuentaUnicoParams params = new EstadoCuentaUnicoParams();
        AgenteVO agenteFirmado = new AgenteVO();
        // agenteFirmado.setId("01");
        // agenteFirmado.setFolio("003");
        // agenteFirmado.setCuenta("0307");

        agenteFirmado.setId("01");
        agenteFirmado.setFolio("001");
        agenteFirmado.setCuenta("0117");

        // 2007-03-15 00:00:00.0
        Calendar fecha = Calendar.getInstance();
        fecha.set(2007, Calendar.APRIL, 3, 0, 0, 0);
        fecha.set(Calendar.MILLISECOND, 0);
        Date fechaMovimiento = fecha.getTime();
        EmisionVO emisionVO = new EmisionVO();
        // emisionVO.setIdTipoValor("91");
        // emisionVO.setEmisora("BNORTCB");

        PaginaVO paginaVO = new PaginaVO();

        params.setAgenteFirmado(agenteFirmado);
        params.setEmisionVO(null);
        params.setExport(false);
        params.setFechaMovimiento(fechaMovimiento);
        params.setPaginaVO(paginaVO);

        EstadoCuentaUnicoVO[] cuentaUnicoVO = null;
        cuentaUnicoVO = mercadoCapitales.getEstadoCuentaUnico(params);

        assertNotNull(cuentaUnicoVO);
        assertTrue(cuentaUnicoVO.length > 0);
        PaginaVO resultados = cuentaUnicoVO[0].getPaginaHistoricos();
        // log.debug("El total de registros es: " +
        // resultados.getTotalRegistros().intValue());

        if (resultados != null && resultados.getRegistros() != null) {
            for (int i = 0; i < resultados.getRegistros().size(); i++) {
                log.debug("Los resultados son: "
                                + ToStringBuilder
                                        .reflectionToString(((HistoricoEstadoCuentaTraspasoVO) resultados
                                                .getRegistros().get(i))));
            }
        }
        log.debug("El saldo disponible es: "
                + cuentaUnicoVO[0].getSaldoDisponible());

        EmisionVO[] emisionVOs = cuentaUnicoVO[0].getEmisionesByCuenta();
        log.debug("El numero de emisiones es: " + emisionVOs.length);

        for (int i = 0; i < emisionVOs.length; i++) {
            log.debug("Emision " + i + " es: " + emisionVOs[i]);
        }

        // for(int i=0;i<cuentaUnicoVO.length;i++){
        // log.debug("Elemento " + i + " es: " +
        // ToStringBuilder.reflectionToString(cuentaUnicoVO[i]));
        // }

    }

    /**
     *
     * @throws BusinessException
     */
    public void ttestEJBEstadoCuentaUnicoSinCuenta() throws BusinessException {
        log.debug("Entrando a testEJBEstadoCuentaUnico...");
        EstadoCuentaUnicoParams params = new EstadoCuentaUnicoParams();
        AgenteVO agenteFirmado = new AgenteVO();
        agenteFirmado.setId("01");
        agenteFirmado.setFolio("001");

        // 2007-02-06 00:00:00.0
        Calendar fecha = Calendar.getInstance();
        fecha.set(2007, Calendar.APRIL, 3, 0, 0, 0);
        fecha.set(Calendar.MILLISECOND, 0);
        Date fechaMovimiento = fecha.getTime();
        EmisionVO emisionVO = new EmisionVO();

        PaginaVO paginaVO = new PaginaVO();

        params.setAgenteFirmado(agenteFirmado);
        params.setEmisionVO(emisionVO);
        params.setExport(false);
        params.setFechaMovimiento(fechaMovimiento);
        params.setPaginaVO(paginaVO);

        EstadoCuentaUnicoVO[] cuentaUnicoVO = mercadoCapitales
                .getEstadoCuentaUnico(params);
        assertNotNull(cuentaUnicoVO);
        String[] cuentas = cuentaUnicoVO[0].getCuentasAgente();
        for (int i = 0; i < cuentas.length; i++) {
            log.debug("Las cuentas son: " + cuentas[i]);
        }

        PaginaVO resultados = cuentaUnicoVO[0].getPaginaHistoricos();
        log.debug("El total de registros es: "
                + resultados.getTotalRegistros().intValue());
        if (resultados != null && resultados.getRegistros() != null) {
            for (int i = 0; i < resultados.getRegistros().size(); i++) {
                log
                        .debug("Los resultados son: "
                                + ToStringBuilder
                                        .reflectionToString(((HistoricoEstadoCuentaTraspasoVO) resultados
                                                .getRegistros().get(i))));
            }
        }
        log.debug("El saldo disponible es: "
                + cuentaUnicoVO[0].getSaldoDisponible());
        log.debug("El saldo calculado es: "
                + cuentaUnicoVO[0].getSaldoCalculado());
    }

    /**
     *
     * @throws BusinessException
     */
    public void ttestEJBEstadoCuentaUnicoConSaldo() throws BusinessException {
        log.debug("Entrando a testEJBEstadoCuentaUnico...");
        EstadoCuentaUnicoParams params = new EstadoCuentaUnicoParams();
        AgenteVO agenteFirmado = new AgenteVO();
        agenteFirmado.setId("01");
        agenteFirmado.setFolio("001");
        agenteFirmado.setCuenta("0109");

        // 2007-02-28 00:00:00.0
        Calendar fecha = Calendar.getInstance();
        fecha.set(2007, Calendar.FEBRUARY, 28, 0, 0, 0);
        fecha.set(Calendar.MILLISECOND, 0);
        Date fechaMovimiento = fecha.getTime();
        EmisionVO emisionVO = new EmisionVO();
        emisionVO.setIdTipoValor("1");
        emisionVO.setEmisora("ALFA");
        emisionVO.setSerie("A");
        emisionVO.setCupon("0021");

        PaginaVO paginaVO = new PaginaVO();

        params.setAgenteFirmado(agenteFirmado);
        params.setEmisionVO(emisionVO);
        params.setExport(true);
        params.setFechaMovimiento(fechaMovimiento);
        params.setPaginaVO(paginaVO);

        EstadoCuentaUnicoVO[] cuentaUnicoVO = mercadoCapitales
                .getEstadoCuentaUnico(params);

        assertNotNull(cuentaUnicoVO);
        assertTrue(cuentaUnicoVO.length > 0);
        log
                .debug("El tamaño del arreglo principal es: "
                        + cuentaUnicoVO.length);
        PaginaVO resultados = cuentaUnicoVO[0].getPaginaHistoricos();
        log.debug("El total de registros en la pagina es: "
                + resultados.getTotalRegistros().intValue());

        if (resultados != null && resultados.getRegistros() != null) {
            for (int i = 0; i < resultados.getRegistros().size(); i++) {
                log
                        .debug("Los resultados de la pagina son: "
                                + ToStringBuilder
                                        .reflectionToString(((HistoricoEstadoCuentaTraspasoVO) resultados
                                                .getRegistros().get(i))));
            }
            log.debug("Saldo disponible "
                    + cuentaUnicoVO[0].getSaldoDisponible().intValue());
            log.debug("Saldo calculado "
                    + cuentaUnicoVO[0].getSaldoCalculado().intValue());
        }
    }

    /**
     *
     * @throws BusinessException
     */
    public void ttestEJBEstadoCuentaUnicoSoloDatosObligatorios()
            throws BusinessException {
        log.debug("Entrando a testEJBEstadoCuentaUnico...");
        EstadoCuentaUnicoParams params = new EstadoCuentaUnicoParams();
        AgenteVO agenteFirmado = new AgenteVO();
        agenteFirmado.setId("01");
        agenteFirmado.setFolio("001");

        // 2007-02-06 00:00:00.0
        Calendar fecha = Calendar.getInstance();
        fecha.set(2007, Calendar.APRIL, 3, 0, 0, 0);
        fecha.set(Calendar.MILLISECOND, 0);
        Date fechaMovimiento = fecha.getTime();

        PaginaVO paginaVO = new PaginaVO();
        paginaVO.setOffset(new Integer(0));
        paginaVO.setRegistrosXPag(new Integer(30));

        EmisionVO emisionVO = new EmisionVO();
        emisionVO.setIdTipoValor("1");
        emisionVO.setEmisora("DIXON");
        emisionVO.setSerie("*");
        emisionVO.setCupon("0001");

        params.setAgenteFirmado(agenteFirmado);
        params.setFechaMovimiento(fechaMovimiento);
        params.setPaginaVO(paginaVO);
        params.setExport(true);
        params.setEmisionVO(emisionVO);

        EstadoCuentaUnicoVO[] cuentaUnicoVO = mercadoCapitales
                .getEstadoCuentaUnico(params);
        assertNotNull(cuentaUnicoVO);
        assertTrue(cuentaUnicoVO.length > 0);
        log.debug("El numero de elementos del arreglo es: "
                + cuentaUnicoVO.length);
        log.debug("De tipo: " + cuentaUnicoVO[0].getClass().getName());

        String[] cuentas = cuentaUnicoVO[0].getCuentasAgente();
        assertNotNull(cuentas);
        assertTrue(cuentas.length>0);
        log.debug("El numero de cuentas para el agente es: "
                + cuentas.length);
        for (int i = 0; i < cuentas.length; i++) {
            log.debug("Cuenta " + i + " = " + cuentas[i]);
        }

        EmisionVO[] emisionVOs = cuentaUnicoVO[0].getEmisionesByCuenta();
        assertNotNull(emisionVOs);
        log.debug("El tamaño del arreglo de emisiones para el primer elemento es: "
                        + emisionVOs.length);

        for (int i = 0; i < cuentaUnicoVO[0].getEmisionesByCuenta().length; i++) {
            log.debug("Emision " + i + " >> "
                    + cuentaUnicoVO[0].getEmisionesByCuenta()[i]);
        }

        for (int i = 0; i < cuentaUnicoVO.length; i++) {
            log.debug("EstadoCuentaUnicoVO.registros "
                    + i
                    + " es: "
                    + ToStringBuilder.reflectionToString(cuentaUnicoVO[i]
                            .getPaginaHistoricos().getRegistros()));
            List registros = cuentaUnicoVO[i].getPaginaHistoricos()
                    .getRegistros();
            if (cuentaUnicoVO[i].getPaginaHistoricos().getRegistros() != null) {
                for (int j = 0; j < registros.size(); j++) {
                    HistoricoEstadoCuentaTraspasoVO nada = (HistoricoEstadoCuentaTraspasoVO) registros
                            .get(j);
                    log.debug("NADA es: "
                            + ToStringBuilder.reflectionToString(nada));
                }
            }
        }
    }

    /**
     *
     * @throws BusinessException
     */
    public void ttestEJBEstadoCuentaUnicoDatosNoExistentesEnBD()
            throws BusinessException {
        log.debug("Entrando a testEJBEstadoCuentaUnico...");
        EstadoCuentaUnicoParams params = new EstadoCuentaUnicoParams();
        AgenteVO agenteFirmado = new AgenteVO();
        agenteFirmado.setId("99");
        agenteFirmado.setFolio("999");

        // 2007-02-06 00:00:00.0
        Calendar fecha = Calendar.getInstance();
        fecha.set(2007, Calendar.FEBRUARY, 6, 0, 0, 0);
        fecha.set(Calendar.MILLISECOND, 0);
        Date fechaMovimiento = fecha.getTime();

        PaginaVO paginaVO = new PaginaVO();
        paginaVO.setOffset(new Integer(0));
        paginaVO.setRegistrosXPag(new Integer(30));

        params.setAgenteFirmado(agenteFirmado);
        params.setFechaMovimiento(fechaMovimiento);
        params.setPaginaVO(paginaVO);
        params.setExport(true);

        EstadoCuentaUnicoVO[] cuentaUnicoVO = mercadoCapitales
                .getEstadoCuentaUnico(params);

        assertNotNull(cuentaUnicoVO);
        assertTrue(cuentaUnicoVO.length > 0);
        log.debug("El numero de elementos del arreglo es: "
                + cuentaUnicoVO.length);
        log.debug("De tipo: " + cuentaUnicoVO[0].getClass().getName());

        String[] cuentas = cuentaUnicoVO[0].getCuentasAgente();
        assertNotNull(cuentas);
        log.debug("El numero de cuentas para el agente es: " + cuentas.length);
        for (int i = 0; i < cuentas.length; i++) {
            log.debug("Cuenta " + i + " " + cuentas[i]);
        }
        for (int i = 0; i < cuentaUnicoVO.length; i++) {
            log.debug("El numero de emisiones para la cuenta " + i + " es: "
                    + cuentaUnicoVO[i].getEmisionesByCuenta().length);
        }
        for (int i = 0; i < cuentaUnicoVO[0].getEmisionesByCuenta().length; i++) {
            log.debug("Emision " + i + " >> "
                    + cuentaUnicoVO[0].getEmisionesByCuenta()[i]);
        }
        for (int i = 0; i < cuentaUnicoVO.length; i++) {
            log.debug("EstadoCuentaUnicoVO " + i + " es: "
                    + ToStringBuilder.reflectionToString(cuentaUnicoVO[i]));
        }
        for (int i = 0; i < cuentaUnicoVO.length; i++) {
            log.debug("EstadoCuentaUnicoVO.registros "
                    + i
                    + " es: "
                    + ToStringBuilder.reflectionToString(cuentaUnicoVO[i]
                            .getPaginaHistoricos().getRegistros()));
            List registros = cuentaUnicoVO[i].getPaginaHistoricos()
                    .getRegistros();
            if (cuentaUnicoVO[i].getPaginaHistoricos().getRegistros() != null) {
                for (int j = 0; j < registros.size(); j++) {
                    HistoricoEstadoCuentaTraspasoVO nada = (HistoricoEstadoCuentaTraspasoVO) registros
                            .get(j);
                    log.debug("NADA es: "
                            + ToStringBuilder.reflectionToString(nada));
                }
            }
        }
    }

    /**
     *
     * @throws BusinessException
     */
    public void ttestEJBEstadoCuentaUnicoAmortizacion()
            throws BusinessException {
        log.debug("Entrando a testEJBEstadoCuentaUnico...");
        EstadoCuentaUnicoParams params = new EstadoCuentaUnicoParams();
        AgenteVO agenteFirmado = new AgenteVO();
        agenteFirmado.setId("01");
        agenteFirmado.setFolio("001");
        // agenteFirmado.setCuenta("0107");

        // 2007-02-06 00:00:00.0
        Calendar fecha = Calendar.getInstance();
        fecha.set(2007, Calendar.APRIL, 24, 0, 0, 0);
        fecha.set(Calendar.MILLISECOND, 0);
        Date fechaMovimiento = fecha.getTime();

        PaginaVO paginaVO = new PaginaVO();
        paginaVO.setOffset(new Integer(0));
        paginaVO.setRegistrosXPag(new Integer(30));

        EmisionVO emisionVO = new EmisionVO();
        // emisionVO.setIdTipoValor("1");
        // emisionVO.setEmisora("GEO");
        // emisionVO.setSerie("B");
        // emisionVO.setCupon("0000");

        params.setAgenteFirmado(agenteFirmado);
        params.setEmisionVO(emisionVO);
        params.setFechaMovimiento(fechaMovimiento);
        params.setPaginaVO(paginaVO);
        params.setExport(true);

        EstadoCuentaUnicoVO[] cuentaUnicoVO = mercadoCapitales
                .getEstadoCuentaUnico(params);

        assertNotNull(cuentaUnicoVO);
        assertTrue(cuentaUnicoVO.length > 0);

        log.debug("Tenemos " + cuentaUnicoVO.length + " objetos de retorno");

        String[] cuentasAgente = cuentaUnicoVO[0].getCuentasAgente();
        log.debug("El numero de cuentas para el caso 24/04/2007 >> 01001 es: "
                + cuentasAgente.length);
        for (int i = 0; i < cuentaUnicoVO.length; i++) {

            EstadoCuentaUnicoVO cuenta = cuentaUnicoVO[i];

            log.debug("Objeto " + i + "completo >> "
                    + ToStringBuilder.reflectionToString(cuentaUnicoVO[i]));

            PaginaVO paginaHistoricosVO = cuentaUnicoVO[i]
                    .getPaginaHistoricos();
            assertNotNull(paginaHistoricosVO);
            List registros = paginaHistoricosVO.getRegistros();
            for (int j = 0; j < registros.size(); j++) {
                String tipoOperacion = ((HistoricoEstadoCuentaTraspasoVO) registros
                        .get(j)).getTipoOperacion();
                log.debug("El tipo de operacion es: " + tipoOperacion);
                HistoricoEstadoCuentaTraspasoVO vo = (HistoricoEstadoCuentaTraspasoVO) registros
                        .get(j);
                log.debug("Detalle ==> "
                        + ToStringBuilder.reflectionToString(vo));
            }

        }
    }

//    /**
//     *
//     * @throws BusinessException
//     */
//    public void ttestEJBFileTransferCargaArchivo() throws BusinessException {
//
//        log.debug("Entrando a testEJBFileTransferCargaArchivo()");
//
//        assertNotNull(mercadoCapitales);
//
//        AgenteVO agenteVO = new AgenteVO();
//        agenteVO.setId("01");
//        agenteVO.setFolio("003");
//
//        List informacion = new ArrayList();
//        informacion
//                .add("23-apr-200725001000102061017 1   ALFA   A     0022A 00                  20                   1");
//        informacion
//                .add("23-apr-200725001000102061017 1   AMX    L     0022A 00                  20                   1");
//        informacion
//                .add("23-apr-200725001000102061017 1   ARA    *     0000A 000000000000000000002000000006289860000000");
//
//        String usuario = "AAAYYYMM";
//
//        String tipoArchivo = "CT";
//
//        Boolean resultado = mercadoCapitales.fileTransferCargaArchivo(agenteVO,
//                tipoArchivo, informacion, usuario);
//
//        log.debug("Carga exitosa: " + resultado);
//
//        log.debug("Saliendo de testEJBFileTransferCargaArchivo()");
//
//    }

//    /**
//     * Test para fileTransferObtieneResumen
//     *
//     * @throws BusinessException
//     */
//    public void ttestEJBFileTransferObtieneResumen() throws BusinessException {
//
//        log.debug("Entrando a testEJBFileTransferObtieneResumen()");
//
//        assertNotNull(mercadoCapitales);
//
//        AgenteVO agenteVO = new AgenteVO();
//        agenteVO.setId("01");
//        agenteVO.setFolio("003");
//
//        String tipoArchivo = "CT";
//
//        ResumenFileTransferVO resumenFileTransferVO = mercadoCapitales
//                .fileTransferObtieneResumen(agenteVO, tipoArchivo);
//
//        assertNotNull(resumenFileTransferVO);
//
//        log.debug("Resumen: "
//                + ReflectionToStringBuilder
//                        .reflectionToString(resumenFileTransferVO));
//
//        log.debug("Saliendo de testEJBFileTransferObtieneResumen()");
//
//    }

//    /**
//     * Prueba la salida de informacion del proceso - FileTransfer
//     *
//     * @throws BusinessException
//     */
//    public void ttestEJBFileTransferObtieneInformacionProcesada()
//            throws BusinessException {
//
//        log
//                .debug("Entrando a testEJBFileTransferObtieneInformacionProcesada()");
//
//        assertNotNull(mercadoCapitales);
//
//        AgenteVO agenteVO = new AgenteVO();
//        agenteVO.setId("02");
//        agenteVO.setFolio("061");
//
//        String tipoArchivo = "CL";
//
//        PaginaVO paginaVO = new PaginaVO();
//
//        TotalesProcesoFileTrasnferVO totalesProceso = mercadoCapitales
//                .fileTransferObtieneInformacionProcesada(agenteVO, tipoArchivo,
//                        paginaVO);
//        assertNotNull(totalesProceso);
//        assertNotNull(totalesProceso.getPagina());
//        assertNotNull(totalesProceso.getPagina().getRegistros());
//
//        log.debug("Totales Proceso: "
//                + ReflectionToStringBuilder.reflectionToString(totalesProceso));
//        for (Iterator iter = totalesProceso.getPagina().getRegistros()
//                .iterator(); iter.hasNext();) {
//            RegistroProcesoFileTransferVO registro = (RegistroProcesoFileTransferVO) iter
//                    .next();
//            log.debug("Registro: "
//                    + ReflectionToStringBuilder.reflectionToString(registro));
//        }
//
//        log
//                .debug("Saliendo de testEJBFileTransferObtieneInformacionProcesada()");
//
//    }

//    /**
//     * Test para fileTransferProcesaInformacion - Traspasos
//     *
//     * @throws BusinessException
//     */
//    public void ttestEJBFileTransferProcesaInformacionTraspasos()
//            throws BusinessException {
//
//        log
//                .debug("Entrando a testEJBFileTransferProcesaInformacionTraspasos()");
//
//        assertNotNull(mercadoCapitales);
//
//        AgenteVO agenteVO = new AgenteVO();
//        agenteVO.setId("02");
//        agenteVO.setFolio("061");
//
//        String tipoArchivo = "CT";
//
//        Boolean resultado = mercadoCapitales.fileTransferProcesaInformacion(
//                agenteVO, tipoArchivo);
//
//        log.debug("resultado: " + resultado);
//
//        log
//                .debug("Saliendo de testEJBFileTransferProcesaInformacionTraspasos()");
//
//    }

//    /**
//     * Test para fileTransferGuardaInformacion - traspasos
//     *
//     * @throws BusinessException
//     */
//    public void ttestEJBFileTransferGuardaInformacionTraspasos()
//            throws BusinessException {
//
//        log.debug("Entrando a testEJBFileTransferGuardaInformacionTraspasos()");
//
//        assertNotNull(mercadoCapitales);
//
//        AgenteVO agenteVO = new AgenteVO();
//        agenteVO.setId("02");
//        agenteVO.setFolio("061");
//
//        String tipoArchivo = "CT";
//
//        Boolean resultado = mercadoCapitales.fileTransferGuardaInformacion(
//                agenteVO, tipoArchivo);
//
//        log.debug("resultado: " + resultado);
//
//        log
//                .debug("Saliendo de testEJBFileTransferGuardaInformacionTraspasos()");
//
//    }

//    /**
//     * Test para fileTransferProcesaInformacion - Liberacion
//     *
//     * @throws BusinessException
//     */
//    public void ttestEJBFileTransferProcesaInformacionLiberacion()
//            throws BusinessException {
//
//        log
//                .debug("Entrando a testEJBFileTransferProcesaInformacionLiberacion()");
//
//        assertNotNull(mercadoCapitales);
//
//        AgenteVO agenteVO = new AgenteVO();
//        agenteVO.setId("02");
//        agenteVO.setFolio("061");
//
//        String tipoArchivo = "CL";
//
//        Boolean resultado = mercadoCapitales.fileTransferProcesaInformacion(
//                agenteVO, tipoArchivo);
//
//        log.debug("resultado: " + resultado);
//
//        log
//                .debug("Saliendo de testEJBFileTransferProcesaInformacionLiberacion()");
//
//    }

//    /**
//     * Test para fileTransferGuardaInformacion - liberacion
//     *
//     * @throws BusinessException
//     */
//    public void ttestEJBFileTransferGuardaInformacionLiberacion()
//            throws BusinessException {
//
//        log
//                .debug("Entrando a testEJBFileTransferGuardaInformacionLiberacion()");
//
//        assertNotNull(mercadoCapitales);
//
//        AgenteVO agenteVO = new AgenteVO();
//        agenteVO.setId("02");
//        agenteVO.setFolio("061");
//
//        String tipoArchivo = "CL";
//
//        Boolean resultado = mercadoCapitales.fileTransferGuardaInformacion(
//                agenteVO, tipoArchivo);
//
//        log.debug("resultado: " + resultado);
//
//        log
//                .debug("Saliendo de testEJBFileTransferGuardaInformacionLiberacion()");
//
//    }

//    /**
//     * Test para fileTransferProcesaInformacion - Confirmacion
//     *
//     * @throws BusinessException
//     */
//    public void ttestEJBFileTransferProcesaInformacionConfirmacion()
//            throws BusinessException {
//
//        log
//                .debug("Entrando a testEJBFileTransferProcesaInformacionConfirmacion()");
//
//        assertNotNull(mercadoCapitales);
//
//        AgenteVO agenteVO = new AgenteVO();
//        agenteVO.setId("01");
//        agenteVO.setFolio("001");
//
//        String tipoArchivo = "CC";
//
//        Boolean resultado = mercadoCapitales.fileTransferProcesaInformacion(
//                agenteVO, tipoArchivo);
//
//        log.debug("resultado: " + resultado);
//
//        log
//                .debug("Saliendo de testEJBFileTransferProcesaInformacionConfirmacion()");
//
//    }

//    /**
//     * Test para fileTransferGuardaInformacion - confirmacion
//     *
//     * @throws BusinessException
//     */
//    public void ttestEJBFileTransferGuardaInformacionConfirmacion()
//            throws BusinessException {
//
//        log
//                .debug("Entrando a testEJBFileTransferGuardaInformacionConfirmacion()");
//
//        assertNotNull(mercadoCapitales);
//
//        AgenteVO agenteVO = new AgenteVO();
//        agenteVO.setId("02");
//        agenteVO.setFolio("061");
//
//        String tipoArchivo = "CC";
//
//        Boolean resultado = mercadoCapitales.fileTransferGuardaInformacion(
//                agenteVO, tipoArchivo);
//
//        log.debug("resultado: " + resultado);
//
//        log
//                .debug("Saliendo de testEJBFileTransferGuardaInformacionConfirmacion()");
//
//    }

//    /**
//     * Test para fileTransferObtieneErrores
//     *
//     * @throws BusinessException
//     */
//    public void ttestEJBFileTransferObtieneErrores() throws BusinessException {
//
//        log.debug("Entrando a testEJBFileTransferObtieneErrores()");
//
//        assertNotNull(mercadoCapitales);
//
//        AgenteVO agenteVO = new AgenteVO();
//        agenteVO.setId("01");
//        agenteVO.setFolio("003");
//
//        String tipoArchivo = "CT";
//
//        PaginaVO paginaVO = mercadoCapitales.fileTransferObtieneErrores(
//                agenteVO, tipoArchivo, null);
//        assertNotNull(paginaVO);
//        assertNotNull(paginaVO.getRegistros());
//
//
//        for (Iterator iter = paginaVO.getRegistros().iterator(); iter.hasNext();) {
//            ErroresFileTransferVO erroresFileTransferVO = (ErroresFileTransferVO) iter
//                    .next();
//            log.debug("Registro: "
//                    + ReflectionToStringBuilder
//                            .reflectionToString(erroresFileTransferVO));
//        }
//        log.debug("Total de registros: " + paginaVO.getTotalRegistros());
//
//        log.debug("Saliendo de testEJBFileTransferObtieneErrores()");
//
//    }

    /**
     *
     * @throws BusinessException
     */
    public void testEJBEstadoCuentaSociedadesDeInversion()
            throws BusinessException {

        assertNotNull(mercadoCapitales);

        AgenteVO agenteVO = new AgenteVO();
        agenteVO.setId("01");
        agenteVO.setFolio("001");

        GetEstadoCuentaSociedadesInvParams params = new GetEstadoCuentaSociedadesInvParams();
        params.setAgenteFirmado(agenteVO);
        params.setTv("51");
        params.setEmisora("NTE-FD1");
        params.setExportacion(false);

        EstadoCuentaSocInvVO[] socInvVOs = mercadoCapitales
                .getEstadoCuentaSociedadesInversion(params);

        assertNotNull(socInvVOs);

        for (int i = 0; i < socInvVOs.length; i++) {
            EstadoCuentaSocInvVO invVO = socInvVOs[i];
            log.debug("Registro: "
                    + ReflectionToStringBuilder.reflectionToString(invVO));
            assertNotNull(invVO.getPaginaVO());
            assertNotNull(invVO.getPaginaVO().getRegistros());
            for (Iterator iter = invVO.getPaginaVO().getRegistros().iterator(); iter
                    .hasNext();) {
                EstadoCuentaSocInvDetalleVO element = (EstadoCuentaSocInvDetalleVO) iter
                        .next();
                log
                        .debug("Detalle: "
                                + ReflectionToStringBuilder
                                        .reflectionToString(element));
            }
        }
        log.debug("Saliendo de testEJBEstadoCuentaSociedadesDeInversion()");

    }

    /**
     *
     * @throws Exception
     */
    public void ttestEJBGetOperacionDiaCapitalesVO() throws Exception {
        OperacionDiaCapitalesParams operacionDiaCapitalesParams = new OperacionDiaCapitalesParams();
        // OperacionDiaCapitalesParams operacionDiaCapitalesParams = null;
        AgenteVO agenteFirmado = new AgenteVO();
        agenteFirmado.setId("01");
        agenteFirmado.setFolio("001");
        agenteFirmado.setCuenta("0117");
        Calendar c = Calendar.getInstance();
        c.set(2007, Calendar.JUNE, 26, 0, 0, 0);
        c.set(Calendar.MILLISECOND, 0);

        operacionDiaCapitalesParams.setFechaInicial(c.getTime());
        operacionDiaCapitalesParams.setFechaFinal(c.getTime());

        operacionDiaCapitalesParams.setManeraExtraccion("AMBOS");
        operacionDiaCapitalesParams.setTipoOperacion("AMBOS");
        operacionDiaCapitalesParams.setAgenteVO(agenteFirmado);

        // operacionDiaCapitalesParams.setTipoOperacion(tipoOperacion);

        EmisionVO emisionVO = new EmisionVO();
        // emisionVO.setIdTipoValor("1");
        // emisionVO.setEmisora("WALMEX");
        operacionDiaCapitalesParams.setEmisionVO(emisionVO);
        OperacionDiaCapitalesVO result = mercadoCapitales
                .getOperacionDiaCapitalesVO(operacionDiaCapitalesParams);

        assertNotNull(result);
        assertNotNull(result.getPaginaVO());
        assertNotNull(result.getPaginaVO().getRegistros());
        PaginaVO paginaVO = result.getPaginaVO();

        Iterator it = paginaVO.getRegistros().iterator();

        log.debug("El numero total de registros es: "
                + result.getTotalMovimientos());
        log.debug("La suma de las cantidades operadas es: "
                + result.getTotalTitulos());

        while (it.hasNext()) {
            OperacionDiaDetalleCapitalesVO det = (OperacionDiaDetalleCapitalesVO) it
                    .next();
            log
                    .debug("Elemento >>> "
                            + ToStringBuilder.reflectionToString(det));
        }

    }

    /**
     *
     * @throws Exception
     */
    public void ttestGetDetalleOperacionDiaCapitales() throws Exception {
        log.debug("Entrando al metodo testGetDetalleOperacionDiaCapitales");
        TraspasoCapitalesVO traspasoCapitalesVO = new TraspasoCapitalesVO();
        traspasoCapitalesVO.setIdInst("02");
        traspasoCapitalesVO.setFolioInst("061");
        traspasoCapitalesVO.setCuenta("8409");
        traspasoCapitalesVO.setLlaveFolio("001112070620");

        OperacionDiaDetalleCapitalesVO result = mercadoCapitales
                .getDetalleOperacionDiaCapitales(traspasoCapitalesVO);

        assertNotNull(result);

        log.debug("El detalle de la consulta es: "
                + ToStringBuilder.reflectionToString(result));
    }

    /**
     *
     * @throws BusinessException
     */
    public void testEJBEstadoCuentaUnicoExportacion() throws BusinessException {
        log.debug("Entrando a testEJBEstadoCuentaUnico...");
        EstadoCuentaUnicoParams params = new EstadoCuentaUnicoParams();
        AgenteVO agenteFirmado = new AgenteVO();
        agenteFirmado.setId("02");
        agenteFirmado.setFolio("061");
        agenteFirmado.setCuenta("6920");


        System.out.println("Aqui --> ");

        // 2007-02-06 00:00:00.0
        Calendar fecha = Calendar.getInstance();
        fecha.set(2007, Calendar.AUGUST, 8, 0, 0, 0);
        fecha.set(Calendar.MILLISECOND, 0);
        Date fechaMovimiento = fecha.getTime();

        PaginaVO paginaVO = new PaginaVO();
        paginaVO.setOffset(new Integer(0));
        paginaVO.setRegistrosXPag(new Integer(100));

        EmisionVO emisionVO = new EmisionVO();
        emisionVO.setIdTipoValor("94");
        emisionVO.setEmisora("BACMEXT");
        emisionVO.setSerie("04");
        emisionVO.setCupon("0035");

        params.setAgenteFirmado(agenteFirmado);
        params.setEmisionVO(emisionVO);
        params.setFechaMovimiento(fechaMovimiento);
        params.setPaginaVO(paginaVO);
        params.setExport(true);

        EstadoCuentaUnicoVO[] cuentaUnicoVO = mercadoCapitales
                .getEstadoCuentaUnico(params);

        assertNotNull(cuentaUnicoVO);
        assertTrue(cuentaUnicoVO.length > 0);

        System.out.println("Tenemos " + cuentaUnicoVO.length + " objetos de retorno");

        String[] cuentasAgente = cuentaUnicoVO[0].getCuentasAgente();

        log.debug("Cuentas >> "
                + ToStringBuilder.reflectionToString(cuentasAgente));

        for (int i = 0; i < cuentaUnicoVO.length; i++) {

            System.out.println("Objeto " + i + " completo >> "
                    + ToStringBuilder.reflectionToString(cuentaUnicoVO[i]));

            PaginaVO paginaHistoricosVO = cuentaUnicoVO[i]
                    .getPaginaHistoricos();
            List registros = paginaHistoricosVO.getRegistros();
            assertNotNull(registros);
            for (int j = 0; j < registros.size(); j++) {
                HistoricoEstadoCuentaTraspasoVO vo = (HistoricoEstadoCuentaTraspasoVO) registros
                        .get(j);
                log.debug("Detalle ==> "
                        + ToStringBuilder.reflectionToString(vo));
            }

        }
    }

    /**
     * Probando servicio cuando el objeto de par&aacute;metros es nulo.
     * @throws BusinessException
     */
    public void testBusinessRulesCapturaTraspasoParamsNulo() throws BusinessException {

        log.info("Entrando al metodo testBusinessRulesCapturaTraspasoParamsNulo");

        try {
            mercadoCapitales.businessRulesCapturaTraspaso(null);
        }
        catch (BusinessException e) {
            log.info("La excepcion es: " + e.getMessage());
        }

    }

    /**
     * Probando cuando falta alguno de los par&aacute;metros requeridos.
     * @throws BusinessException
     */
    public void testBusinessRulesCapturaTraspasoParametrosIncompletos() throws BusinessException {

        log.info("Entrando al metodo testBusinessRulesCapturaTraspasoParametrosIncompletos");

        GetCapturaTraspasoParams params = new GetCapturaTraspasoParams();

        params.setAgenteFirmado(null);
        params.setCantidad(null);
        params.setCliente(null);
        params.setCveReporto(null);
        params.setDivisa(null);

        params.setFecha(null);
        params.setFechaAdquisicion(null);
        params.setFechaLiquidacion(null);
        params.setFolioControl(null);
        params.setFolioDescripcion(null);
        params.setLlaveFolioMd(null);
        params.setPrecioAdquisicion(null);
        params.setPrecioVector(null);

        params.setRfcCurp(null);
        params.setSaldoActual(null);
        params.setSaldoDisponible(null);
        params.setSerie(null);
        params.setTipoOperacion(null);

        //el traspasante no puede ser nulo
        AgenteVO traspasante = new AgenteVO("02","061","0003");
        params.setTraspasante(traspasante);
        params.setTv(null);

        //el receptor no puede ser nulo
        AgenteVO receptor = new AgenteVO("01","003","0307");
        params.setReceptor(receptor);


        //la emision no puede ser nula
        EmisionVO emisionVO = new EmisionVO();
        emisionVO.setIdTipoValor("1");
        emisionVO.setEmisora("ALFA");
        emisionVO.setSerie("A");
        emisionVO.setCupon("0022");
        params.setEmision(emisionVO);


        try {
            mercadoCapitales.businessRulesCapturaTraspaso(params);
        }
        catch (BusinessException e) {
            log.info("La excepcion es: " + e.getMessage());
        }

    }

    /**
     * Probando el caso en que la emisi&oacute;n no es nula, pero sus par&aacute;metros son
     * vac&iacute;os.
     * @throws BusinessException
     */
    public void testBusinessRulesCapturaTraspasoEmisionVacia() throws BusinessException {

        log.info("Entrando al metodo testBusinessRulesCapturaTraspasoEmisionVacia");

        GetCapturaTraspasoParams params = new GetCapturaTraspasoParams();

        params.setAgenteFirmado(new AgenteVO("02","061"));
        params.setCantidad(new BigDecimal("1"));
        params.setCliente("Uno");
        params.setCveReporto("T");
        params.setDivisa("PE");

        params.setFecha(new Date());
        params.setFechaAdquisicion(new Date());
        params.setFechaLiquidacion(new Date());
        params.setFolioControl(new Integer("12"));
        params.setFolioDescripcion("Desc.");
        params.setLlaveFolioMd("Llave folio");
        params.setPrecioAdquisicion(new BigDecimal("21"));
        params.setPrecioVector(new BigDecimal("12"));

        params.setRfcCurp("bursa-indeval");
        params.setSaldoActual(new BigInteger("1"));
        params.setSaldoDisponible(new BigInteger("52"));
        params.setSerie("serie");
        params.setTipoOperacion("T");

        //el traspasante no puede ser nulo
        AgenteVO traspasante = new AgenteVO("02","061","0003");
        params.setTraspasante(traspasante);
        params.setTv("1");

        //el receptor no puede ser nulo
        AgenteVO receptor = new AgenteVO("01","003","0307");
        params.setReceptor(receptor);


        //la emision no puede ser nula
        EmisionVO emisionVO = new EmisionVO();
        params.setEmision(emisionVO);


        try {
            mercadoCapitales.businessRulesCapturaTraspaso(params);
        }
        catch (BusinessException e) {
            log.info("La excepcion es: " + e.getMessage());
        }

    }

    /**
     *
     * @throws Exception
     */
    public void testBusinessRulesCapturaTraspaso() throws Exception {

        log.info("Entrando al metodo testBusinessRulesCapturaTraspaso");

        GetCapturaTraspasoParams params = new GetCapturaTraspasoParams();

        params.setAgenteFirmado(new AgenteVO("02","061"));
        params.setCantidad(new BigDecimal("1"));
        params.setCliente("Uno");
        params.setCveReporto("T");
        params.setDivisa("PE");

        params.setFecha(new Date());
        params.setFechaAdquisicion(new Date());
        params.setFechaLiquidacion(new Date());
        params.setFolioControl(new Integer("12"));
        params.setFolioDescripcion("Desc.");
        params.setLlaveFolioMd("Llave folio");
        params.setPrecioAdquisicion(new BigDecimal("21"));
        params.setPrecioVector(new BigDecimal("12"));

        params.setRfcCurp("bursa-indeval");
        params.setSaldoActual(new BigInteger("1"));
        params.setSaldoDisponible(new BigInteger("52"));
        params.setSerie("serie");
        params.setTipoOperacion("T");

        //el traspasante no puede ser nulo
        AgenteVO traspasante = new AgenteVO("02","061","0003");
        params.setTraspasante(traspasante);
        params.setTv("1");

        //el receptor no puede ser nulo
        AgenteVO receptor = new AgenteVO("01","003","0307");
        params.setReceptor(receptor);


        //la emision no puede ser nula
        EmisionVO emisionVO = new EmisionVO();
        emisionVO.setIdTipoValor("1");
        emisionVO.setEmisora("ALFA");
        emisionVO.setSerie("A");
        emisionVO.setCupon("0022");
        params.setEmision(emisionVO);


        try {
            mercadoCapitales.businessRulesCapturaTraspaso(params);
        }
        catch (BusinessException e) {
            log.info("La excepcion es: " + e.getMessage());
        }

    }

}

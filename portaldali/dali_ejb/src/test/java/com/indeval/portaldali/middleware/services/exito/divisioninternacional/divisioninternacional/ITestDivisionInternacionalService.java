/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exito.divisioninternacional.divisioninternacional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.remoting.RemoteAccessException;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.divisioninternacional.AgenteArqueoVO;
import com.indeval.portaldali.middleware.services.divisioninternacional.ArqueoVO;
import com.indeval.portaldali.middleware.services.divisioninternacional.CapturaOperacionParams;
import com.indeval.portaldali.middleware.services.divisioninternacional.ConsultaEmisionesVO;
import com.indeval.portaldali.middleware.services.divisioninternacional.ConsultaOperacionesVO;
import com.indeval.portaldali.middleware.services.divisioninternacional.DetalleEmisionRentaFijaVariableTotalVO;
import com.indeval.portaldali.middleware.services.divisioninternacional.DetalleEmisionRentaFijaVariableVO;
import com.indeval.portaldali.middleware.services.divisioninternacional.DivisionInternacionalService;
import com.indeval.portaldali.middleware.services.divisioninternacional.EjercicioDerechoRentaFijaVariableVO;
import com.indeval.portaldali.middleware.services.divisioninternacional.EmisionArqueadaVO;
import com.indeval.portaldali.middleware.services.divisioninternacional.EstadoCuentaTotalVO;
import com.indeval.portaldali.middleware.services.divisioninternacional.EstadoCuentaVO;
import com.indeval.portaldali.middleware.services.divisioninternacional.GetEjercicioDerechoRentaFijaVariableParams;
import com.indeval.portaldali.middleware.services.divisioninternacional.GetPosicionValorParams;
import com.indeval.portaldali.middleware.services.divisioninternacional.GetTraspasosDivIntVO;
import com.indeval.portaldali.middleware.services.divisioninternacional.GetTraspazosDivIntParams;
import com.indeval.portaldali.middleware.services.divisioninternacional.ListaArqueoVO;
import com.indeval.portaldali.middleware.services.divisioninternacional.PorcentajeAgenteVO;
import com.indeval.portaldali.middleware.services.divisioninternacional.PorcentajeArqueoVO;
import com.indeval.portaldali.middleware.services.divisioninternacional.PorcentajeCuentaVO;
import com.indeval.portaldali.middleware.services.divisioninternacional.PosicionValorTotalVO;
import com.indeval.portaldali.middleware.services.divisioninternacional.PosicionValorVO;
import com.indeval.portaldali.middleware.services.divisioninternacional.SetCapturaTraspazoParams;
import com.indeval.portaldali.middleware.services.divisioninternacional.TopeCirculanteFidecomisoVO;
import com.indeval.portaldali.middleware.services.divisioninternacional.TotalArqueoVO;
import com.indeval.portaldali.middleware.services.divisioninternacional.TotalListaArqueoVO;
import com.indeval.portaldali.middleware.services.divisioninternacional.TraspazosDivIntVO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;

/**
 * Clase para probar el servicio DivisionInternacionalService
 * 
 * @author <a href="mailto:julio@itbrain.com.mx">Julio G&oacute;mez Resendiz.</a> 2006
 * 
 */
public class ITestDivisionInternacionalService extends BaseITestService {

    /** Servicio de log */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /** Servicio que sera probado en esta prueba */
    private DivisionInternacionalService divisionInternacionalService;

    /**
     * @see com.indeval.portaldali.middleware.services.BaseITestService#onSetUp()
     */
    protected void onSetUp() throws Exception {
        super.onSetUp();
        if (divisionInternacionalService == null) {
            divisionInternacionalService = (DivisionInternacionalService) applicationContext
                    .getBean("divisionInternacionalService");
        }
    }

    /**
     * Prueba el servicio getTopeCirculanteFideicomiso.
     */
    public void testGetTopeCirculanteFidecomiso() {

        log.debug("Entra a " + getName());

        assertNotNull(divisionInternacionalService);

        EmisionVO emisionVO = new EmisionVO();
        emisionVO.setAlta("todos");
        PaginaVO paginaVO = new PaginaVO();
        paginaVO.setOffset(new Integer(0));
        paginaVO.setRegistrosXPag(new Integer(5));
        try {
            paginaVO = divisionInternacionalService
                    .getTopeCirculanteFidecomiso(emisionVO, paginaVO);

            assertNotNull(paginaVO);
            assertNotNull(paginaVO.getRegistros());
            log.debug("PaginaVO: "
                    + ReflectionToStringBuilder.reflectionToString(paginaVO));
            log.debug("Total de registros: " + paginaVO.getTotalRegistros());
            for (Iterator it = paginaVO.getRegistros().iterator(); it.hasNext();) {
                TopeCirculanteFidecomisoVO fidecomisoVO = (TopeCirculanteFidecomisoVO) it
                        .next();
                assertNotNull(fidecomisoVO);
                log.debug("registro: "
                        + ReflectionToStringBuilder
                                .reflectionToString(fidecomisoVO));
                log.debug("clave alta: ["
                        + fidecomisoVO.getEmisionVO().getAlta() + "]");
            }
        } catch (BusinessException ex) {
            log.debug("Mensaje be: " + ex.getMessage());
        } catch (RemoteAccessException e) { // Bloque para cachar la excepcion
            // con las pruebas del EJB
            log.debug("Mensaje remote: " + e.getMessage());
        }
        log.debug("Termina " + getName());
    }

    /**
     * Prueba el servicio getConsultaEmisiones
     */
    public void testGetConsultaEmisiones() {

        log.debug("Entra a " + getName());

        assertNotNull(divisionInternacionalService);

        EmisionVO emisionVO = new EmisionVO();
        emisionVO.setIdTipoValor("93");
        emisionVO.setEmisora("CINMOBI");
        emisionVO.setSerie("00106");
        // emisionVO.setCupon("0010");
        emisionVO.setIsin("MX93CI040054");
        emisionVO.setAlta("Todos");
        emisionVO.setCuponCortado("B"); // puede ser C o F
        Calendar cal = Calendar.getInstance();
        cal.set(2007, Calendar.JANUARY, 18);
        Date fechaInicio = cal.getTime();
        cal.set(2007, Calendar.FEBRUARY, 15);
        Date fechaFin = cal.getTime();
        PaginaVO paginaVO = new PaginaVO();
        paginaVO.setOffset(new Integer(0));
        paginaVO.setRegistrosXPag(new Integer(10));

        try {
            paginaVO = divisionInternacionalService.getConsultaEmisiones(
                    emisionVO, fechaInicio, fechaFin, paginaVO);
            assertNotNull(paginaVO);
        } catch (BusinessException e) {
            log.debug("Lanzo " + e.getMessage());
        } catch (RemoteAccessException e) {
            log.debug("Lanzo " + e.getMessage());
        }

        assertNotNull(paginaVO.getRegistros());
        assertTrue(paginaVO.getRegistros().size() > 0);
        log.debug("Elementos consultaEmisionesVOs "
                + paginaVO.getRegistros().size());

        for (Iterator it = paginaVO.getRegistros().iterator(); it.hasNext();) {

            ConsultaEmisionesVO consultaEmisionesVO = (ConsultaEmisionesVO) it
                    .next();
            assertNotNull(consultaEmisionesVO);
            log.debug("Emision: "
                    + ToStringBuilder.reflectionToString(consultaEmisionesVO
                            .getEmisionVO()));
            log.debug(" Dias: " + consultaEmisionesVO.getDias());
            log.debug(" FechaAlta: " + consultaEmisionesVO.getFechaAlta());
            log.debug(" Fecha Asignacion: "
                    + consultaEmisionesVO.getFechaAsignacionlsin());
            log.debug(" Fecha Vencimiento: "
                    + consultaEmisionesVO.getFechaVencimiento());
        }
        log.debug("Termina " + getName());
    }

    /**
     * Prueba el servicio getTraspazosDivInt()
     * 
     * @throws BusinessException
     */
    public void testGetTraspazosDivInt() throws BusinessException {

        log.debug("Entra a " + getName());

        assertNotNull(divisionInternacionalService);

        GetTraspazosDivIntParams params = new GetTraspazosDivIntParams();
        GetTraspasosDivIntVO divIntVO;

        PaginaVO paginaVO = new PaginaVO();
        paginaVO.setOffset(new Integer(0));
        paginaVO.setRegistrosXPag(new Integer(10));
        params.setPaginaVO(paginaVO);

        AgenteVO agenteFirmado = new AgenteVO("02", "012", "6304");
        params.setAgenteFirmado(agenteFirmado);

        EmisionVO emisionVO = new EmisionVO();
        emisionVO.setIdTipoValor("52");
        emisionVO.setEmisora("HSBC-V3");
        emisionVO.setSerie("B-5");
        emisionVO.setCupon("0000");
        emisionVO.setIsin("MX52HS030065");
        params.setClaveValor(emisionVO);

        Calendar fecha = Calendar.getInstance();
        fecha.set(2007, Calendar.FEBRUARY, 12);
        params.setTraspazosDia(fecha.getTime());

        params.setFolioControl(new Integer(6189));
        params.setTituloTraspansante(new BigDecimal(12125));
        params.setRol("T");

        divIntVO = divisionInternacionalService.getTraspasosDivInt(params);
        assertNotNull(divIntVO);
        muestraResultadosConsultaTraspaso(divIntVO);

        agenteFirmado.setCuenta("6399");
        emisionVO.setIdTipoValor("51");
        emisionVO.setEmisora("HSBC-D2");
        emisionVO.setSerie("B-1");
        emisionVO.setCupon("0001");
        emisionVO.setIsin("MX51HS070021");
        params.setFolioControl(new Integer(6045));
        params.setTituloTraspansante(new BigDecimal(5757));
        params.setRol("R");

        divIntVO = divisionInternacionalService.getTraspasosDivInt(params);
        assertNotNull(divIntVO);
        muestraResultadosConsultaTraspaso(divIntVO);

        agenteFirmado.setCuenta("6304");
        params.setClaveValor(null);
        params.setFolioControl(null);
        params.setTituloTraspansante(null);
        params.setRol("A");

        divIntVO = divisionInternacionalService.getTraspasosDivInt(params);
        assertNotNull(divIntVO);
        muestraResultadosConsultaTraspaso(divIntVO);

        agenteFirmado.setId("01");
        agenteFirmado.setFolio("027");
        agenteFirmado.setCuenta("3202");

        AgenteVO contraparte = new AgenteVO("01", "027", "3299");
        params.setContraparte(contraparte);
        divIntVO = divisionInternacionalService.getTraspasosDivInt(params);
        assertNotNull(divIntVO);
        muestraResultadosConsultaTraspaso(divIntVO);

        log.debug("Termina " + getName());
    }

    /**
     * Muestra los resultados de la consulta de traspasos
     * 
     * @param divIntVO
     *            contiene los registros y subtotales
     */
    private void muestraResultadosConsultaTraspaso(GetTraspasosDivIntVO divIntVO) {

        assertNotNull(divIntVO.getPaginaVO());
        PaginaVO paginaVO = divIntVO.getPaginaVO();

        log.debug("el VO "
                + ReflectionToStringBuilder.reflectionToString(divIntVO));
        log.debug("el PaginaVO "
                + ReflectionToStringBuilder.reflectionToString(divIntVO
                        .getPaginaVO()));

        assertNotNull(divIntVO.getPaginaVO().getRegistros());
        assertFalse(divIntVO.getPaginaVO().getRegistros().isEmpty());
        int i = 0;
        for (Iterator it = paginaVO.getRegistros().iterator(); it.hasNext(); i++) {

            TraspazosDivIntVO traspazosDivIntVO = (TraspazosDivIntVO) it.next();
            log.debug("el registro "
                    + i
                    + " "
                    + ReflectionToStringBuilder
                            .reflectionToString(traspazosDivIntVO));
            log.debug("el agente "
                    + ReflectionToStringBuilder
                            .reflectionToString(traspazosDivIntVO
                                    .getContraparte()));
            log.debug("la emision "
                    + ReflectionToStringBuilder
                            .reflectionToString(traspazosDivIntVO
                                    .getClaveValor()));
        }
        log
                .debug("************************************************************");
    }

    /**
     * Prueba el servicio getPosicionValor(GetPosicionValorParams params)
     */
    public void testGetPosicionValor() {

        log.debug("Entra a " + getName());

        assertNotNull(divisionInternacionalService);

        GetPosicionValorParams params = new GetPosicionValorParams();
        params.setAgenteFirmado(new AgenteVO());
        // params.getAgenteFirmado().setCuenta("0099");//9729
        params.getAgenteFirmado().setId("02");// 02
        params.getAgenteFirmado().setFolio("015");// 061,013

        params.setClaveValor(new EmisionVO());
        /*
         * params.getClaveValor().setIdTipoValor("54");
         * params.getClaveValor().setEmisora("ABSB1");
         * params.getClaveValor().setSerie("A2");
         * params.getClaveValor().setCupon("0000");
         * params.getClaveValor().setIsin("MX54AB270012");
         */
        // 97 es la que tiene
        // problemas--------------------------------------------------------------------------------------
        params.setPosicion("97");
        params.setAltaCatalogo("Todos");
        // params.setPosicion("97");

        Calendar calendar = Calendar.getInstance();
        calendar.set(2007, Calendar.JUNE, 8);
        Date fechaSaldo = calendar.getTime();
        params.setFecha(fechaSaldo);

        params.setPaginaVO(new PaginaVO());
        params.getPaginaVO().setOffset(new Integer(0));
        params.getPaginaVO().setRegistrosXPag(new Integer(30));
        try {
            PosicionValorTotalVO vo = divisionInternacionalService
                    .getPosicionValor(params);
            assertNotNull(vo);
            muestraResultadosPosicionValor(vo);

            // params.getAgenteFirmado().setCuenta("0009");
            // params.getClaveValor().setCupon("0000");
            // params.setFecha(null);

            vo = divisionInternacionalService.getPosicionValor(params);
            assertNotNull(vo);
            muestraResultadosPosicionValor(vo);
        } catch (BusinessException e) {
            log.debug("Mensaje: ", e);
        } catch (RemoteAccessException e) {
            log.debug("Mensaje: ", e);
        }
        log.debug("Termina " + getName());
    }

    /**
     * 
     * @param posicionValorTotalVO
     */
    private void muestraResultadosPosicionValor(
            PosicionValorTotalVO posicionValorTotalVO) {

        assertNotNull(posicionValorTotalVO);
        assertNotNull(posicionValorTotalVO.getPaginaVO());
        assertNotNull(posicionValorTotalVO.getPaginaVO().getRegistros());
        assertFalse(posicionValorTotalVO.getPaginaVO().getRegistros().isEmpty());
        log.debug("Tamaño de registros "
                + posicionValorTotalVO.getPaginaVO().getRegistros().size());
        StringBuffer buffer = null;
        int i = 0;
        for (Iterator it = posicionValorTotalVO.getPaginaVO().getRegistros()
                .iterator(); it.hasNext(); i++) {
            PosicionValorVO posicionValorVO = (PosicionValorVO) it.next();
            buffer = new StringBuffer();
            buffer.append("Emision: " + i);
            assertNotNull(posicionValorVO.getEmision());
            buffer.append(posicionValorVO.getEmision().getIdTipoValor());
            buffer.append(" Emisora: "
                    + posicionValorVO.getEmision().getEmisora());
            buffer.append(" Serie: " + posicionValorVO.getEmision().getSerie());
            buffer.append(" Cupon: " + posicionValorVO.getEmision().getCupon());
            buffer.append(" Isin: " + posicionValorVO.getEmision().getIsin());
            buffer.append(" Alta: " + posicionValorVO.getEmision().getAlta());
            buffer.append(" Pos. actual: "
                    + posicionValorVO.getPosicionActual());
            buffer.append(" Valor mercado: "
                    + posicionValorVO.getValorMercado());
            buffer.append(" Valor nominal: "
                    + posicionValorVO.getValorNominal());
            buffer.append(" Valuacion mercado: "
                    + posicionValorVO.getValuacionMercado());
            buffer.append(" Valuacion nominal: "
                    + posicionValorVO.getValuacionNominal());
            log.debug(buffer);
        }
        log.debug("El vo "
                + ToStringBuilder.reflectionToString(posicionValorTotalVO));
    }

    /**
     * Prueba el servicio getEjercicioDerechoRentaFijaVariable()
     * 
     */
    public void testGetEjercicioDerechoRentaFijaVariable() {

        log.debug("Entra a " + getName());

        try {

            GetEjercicioDerechoRentaFijaVariableParams params = new GetEjercicioDerechoRentaFijaVariableParams();

            params.setPaginaVO(new PaginaVO());
            params.getPaginaVO().setOffset(new Integer(0));
            params.getPaginaVO().setRegistrosXPag(new Integer(5));

            params.setEmisionVO(new EmisionVO());
            params.getEmisionVO().setIdTipoValor("91");
            params.getEmisionVO().setEmisora("ALMACO");
            params.getEmisionVO().setSerie("05");
            params.getEmisionVO().setCupon("0016");
            // params.setEstatus("");
            params.setTe("INT");

            Calendar fechaFija = Calendar.getInstance();

            log.debug("Probando Renta Fija");

            fechaFija.set(2007, Calendar.MARCH, 1);
            params.setFechaPrevioInicio(fechaFija.getTime());
            fechaFija.set(2007, Calendar.MARCH, 23);
            params.setFechaPrevioFin(fechaFija.getTime());

            // fechaFija.set(2007, Calendar.FEBRUARY, 1);
            // params.setFechaFirmeInicio(fechaFija.getTime());
            // fechaFija.set(2007, Calendar.MARCH, 16);
            // params.setFechaFirmeFin(fechaFija.getTime());

            params.isFija(new Boolean(true));
            // params.setTasaInteres(new BigDecimal(7.04));

            PaginaVO paginaVO = divisionInternacionalService
                    .getEjercicioDerechoRentaFijaVariable(params);
            assertNotNull(paginaVO);
            assertNotNull(paginaVO.getRegistros());
            muestraResultadosEjercicioDerechoRentaFijaVariable(paginaVO);

            log.debug("Probando Renta Variable");

            params.isFija(new Boolean(false));
            fechaFija.set(2007, Calendar.MARCH, 1);
            params.setFechaFirmeInicio(fechaFija.getTime());
            fechaFija.set(2007, Calendar.MARCH, 23);
            params.setFechaFirmeFin(fechaFija.getTime());
            params.getEmisionVO().setIdTipoValor("91");
            params.getEmisionVO().setEmisora("ALMACO");
            params.getEmisionVO().setSerie("05");
            params.getEmisionVO().setCupon("0016");
            params.setTe("INT");
            // params.setTasaInteres(null);

            // PaginaVO paginaVO = divisionInternacionalService
            // .getEjercicioDerechoRentaFijaVariable(params);
            // assertNotNull(paginaVO);
            // assertNotNull(paginaVO.getRegistros());
            // muestraResultadosEjercicioDerechoRentaFijaVariable(paginaVO);
        } catch (BusinessException e) {
            log.debug("Lanzo " + e.getMessage());
        }
        log.debug("termina " + getName());
    }

    /**
     * Imprime los datos obtenidos de testGetEjercicioDerechoRentaFijaVariable()
     * 
     * @param paginaVO
     *            contiene los registros obtenidos
     */
    private void muestraResultadosEjercicioDerechoRentaFijaVariable(
            PaginaVO paginaVO) {

        assertNotNull(paginaVO);
        assertNotNull(paginaVO.getRegistros());
        assertFalse(paginaVO.getRegistros().isEmpty());
        log.debug("Total de registros " + paginaVO.getTotalRegistros());
        for (Iterator it = paginaVO.getRegistros().iterator(); it.hasNext();) {
            EjercicioDerechoRentaFijaVariableVO ejercicio = (EjercicioDerechoRentaFijaVariableVO) it
                    .next();
            log.debug("registro "
                    + ToStringBuilder.reflectionToString(ejercicio));
            log.debug("emisionOrginial "
                    + ToStringBuilder.reflectionToString(ejercicio
                            .getClaveValorOriginal()));
            log.debug("emisionDestino "
                    + ToStringBuilder.reflectionToString(ejercicio
                            .getClaveValorDestino()));

        }

    }

    /**
     * Prueba el servicio getDetalleEmisionRentaFijaVariable
     */
    public void testGetDetalleEmisionRentaFijaVariable() {
        log.debug("Entra a " + getName());

        DetalleEmisionRentaFijaVariableTotalVO detalle = null;
        EjercicioDerechoRentaFijaVariableVO renta = new EjercicioDerechoRentaFijaVariableVO();
        EmisionVO emisionVODestino = new EmisionVO();
        EmisionVO emisionVOOriginal = new EmisionVO();
        Calendar fecha = Calendar.getInstance();

        emisionVODestino.setIdTipoValor("91");
        emisionVODestino.setEmisora("ALMACO");
        emisionVODestino.setSerie("05");
        emisionVODestino.setCupon("0017");

        emisionVOOriginal.setIdTipoValor("91");
        emisionVOOriginal.setEmisora("ALMACO");
        emisionVOOriginal.setSerie("05");
        emisionVOOriginal.setCupon("0016");
        emisionVOOriginal.setIsin("MX91AL2B0012");

        renta.setClaveValorDestino(emisionVODestino);
        renta.setClaveValorOriginal(emisionVOOriginal);
        fecha.set(2007, Calendar.MARCH, 9);
        renta.setFechaPago(fecha.getTime());
        fecha.set(2007, Calendar.MARCH, 8);
        renta.setFechaCorte(fecha.getTime());
        renta.setProporcionCupon(new BigDecimal(
                0.6618888888888889709960494656115770339965820312));
        renta.setTe("INT");
        renta.setValorNominal(new BigDecimal(100));
        renta.setFolioFija(new BigInteger("169017"));
        renta.setFija(true);

        try {
            log.debug("Probando detalle de una renta fija");
            detalle = divisionInternacionalService
                    .getDetalleEmisionRentaFijaVariable(renta, new AgenteVO(
                            "01", "003"));
            assertNotNull(detalle);
            muestraGetDetalleEmisionRentaFijaVariable(detalle);

            emisionVODestino.setIdTipoValor("51");
            emisionVODestino.setEmisora("APOLO10");
            emisionVODestino.setSerie("B-1");
            emisionVODestino.setCupon("0000");

            emisionVOOriginal.setIdTipoValor("51");
            emisionVOOriginal.setEmisora("APOLO10");
            emisionVOOriginal.setSerie("B-1");
            emisionVOOriginal.setCupon("0000");
            emisionVOOriginal.setIsin("MX51AP000028");

            renta.setClaveValorDestino(emisionVODestino);
            renta.setClaveValorOriginal(emisionVOOriginal);
            fecha.set(2007, Calendar.FEBRUARY, 13);
            renta.setFechaPago(fecha.getTime());
            renta.setFechaCorte(fecha.getTime());
            renta.setProporcionCupon(new BigDecimal(10));
            renta.setTe("SPL");
            renta.setValorNominal(new BigDecimal(1));
            renta.setFolioVariable(new Integer("0"));
            renta.setFija(false);

            log.debug("Probando detalle de una renta variable");
            // detalle =
            // divisionInternacionalService.getDetalleEmisionRentaFijaVariable(
            // renta, new AgenteVO("02","061"));
            // muestraGetDetalleEmisionRentaFijaVariable(detalle);

        } catch (BusinessException e) {
            log.debug("Lanzo " + e.getMessage());
        }
        log.debug("Termina " + getName());
    }

    /**
     * Muestra el detale de emision renta fija o variable
     * 
     * @param detalle
     *            el detalle
     */
    private void muestraGetDetalleEmisionRentaFijaVariable(
            DetalleEmisionRentaFijaVariableTotalVO detalle) {

        assertNotNull(detalle);
        assertNotNull(detalle.getRegistros());
        assertFalse(detalle.getRegistros().isEmpty());
        if (detalle != null) {
            log.debug("El detalle "
                    + ReflectionToStringBuilder.reflectionToString(detalle));
            log.debug("La renta "
                    + ReflectionToStringBuilder.reflectionToString(detalle
                            .getRenta()));
            for (Iterator it = detalle.getRegistros().iterator(); it.hasNext();) {
                DetalleEmisionRentaFijaVariableVO registro = (DetalleEmisionRentaFijaVariableVO) it
                        .next();
                log.debug("El registro "
                        + ReflectionToStringBuilder
                                .reflectionToString(registro));
                log.debug("El agente "
                        + ReflectionToStringBuilder.reflectionToString(registro
                                .getAgente()));
            }
        }
    }

    /**
     * Prueba el servicio getCapturaTraspaso
     */
    public void testGetCapturaTraspaso() {

        log.debug("Comienza " + getName());

        assertNotNull(divisionInternacionalService);

        SetCapturaTraspazoParams params = new SetCapturaTraspazoParams();
        params.setAgenteVOtraspasante(new AgenteVO());
        params.setAgenteVOReceptor(new AgenteVO());
        params.setEmisionVO(new EmisionVO());

        // caso 54
        params.getAgenteVOtraspasante().setId("01");
        params.getAgenteVOtraspasante().setFolio("003");
        params.getAgenteVOtraspasante().setCuenta("0307");
        params.getAgenteVOtraspasante().setNombreCorto("ANGUSTIN");

        params.getAgenteVOReceptor().setId("03");
        params.getAgenteVOReceptor().setFolio("003");
        params.getAgenteVOReceptor().setCuenta("5431");

        params.getEmisionVO().setIdTipoValor("1");
        params.getEmisionVO().setEmisora("ALFA");
        params.getEmisionVO().setSerie("A");
        params.getEmisionVO().setCupon("0021");

        params.setCantidadOperada(new BigDecimal(1500));
        params.setPosicionActual(new BigDecimal(35000));
        params.setSaldoDisponible(new BigDecimal(50000));

        BigInteger folio = null;
        try {
            folio = divisionInternacionalService.getCapturaTraspazo(params);
            assertNotNull(folio);
            log.debug("Valor de folio 54:" + folio.intValue());
        } catch (BusinessException e) {
            log.debug("Caso 54 Lanzo: " + e.getMessage());
        } catch (RemoteAccessException e) {
            log.debug("Caso 54 Lanzo: " + e.getMessage());
        }

        // caso 94
        params.getAgenteVOtraspasante().setId("02");
        params.getAgenteVOtraspasante().setFolio("061");
        params.getAgenteVOtraspasante().setCuenta("5317");
        params.getAgenteVOtraspasante().setNombreCorto("JULIUS");

        params.getAgenteVOReceptor().setId("02");
        params.getAgenteVOReceptor().setFolio("061");
        params.getAgenteVOReceptor().setCuenta("8494");

        params.getEmisionVO().setIdTipoValor("1");
        params.getEmisionVO().setEmisora("VITRO");
        params.getEmisionVO().setSerie("A");
        params.getEmisionVO().setCupon("0066");

        try {
            folio = divisionInternacionalService.getCapturaTraspazo(params);
            assertNotNull(folio);
            log.debug("Valor de folio 94:" + folio.intValue());
        } catch (BusinessException e) {
            log.debug("Caso 94 Lanzo: " + e.getMessage());
        } catch (RemoteAccessException e) {
            log.debug("Caso 94 Lanzo: " + e.getMessage());
        }

        // caso 97
        params.getAgenteVOtraspasante().setId("01");
        params.getAgenteVOtraspasante().setFolio("003");
        params.getAgenteVOtraspasante().setCuenta("0304");
        params.getAgenteVOtraspasante().setNombreCorto("ANGUSTIN");

        params.getAgenteVOReceptor().setId("02");
        params.getAgenteVOReceptor().setFolio("013");
        params.getAgenteVOReceptor().setCuenta("6597");

        params.getEmisionVO().setIdTipoValor("00");
        params.getEmisionVO().setEmisora("ABACOGF");
        params.getEmisionVO().setSerie("B");
        params.getEmisionVO().setCupon("0000");

        try {
            folio = divisionInternacionalService.getCapturaTraspazo(params);
            assertNotNull(folio);
            log.debug("Valor de folio 97:" + folio.intValue());
        } catch (BusinessException e) {
            log.debug("Caso 97 Lanzo: " + e.getMessage());
        } catch (RemoteAccessException e) {
            log.debug("Caso 97 Lanzo: " + e.getMessage());
        }

        // caso 99
        params.getAgenteVOtraspasante().setId("01");
        params.getAgenteVOtraspasante().setFolio("003");
        params.getAgenteVOtraspasante().setCuenta("0357");
        params.getAgenteVOtraspasante().setNombreCorto("JULIUS");

        params.getAgenteVOReceptor().setId("01");
        params.getAgenteVOReceptor().setFolio("003");
        params.getAgenteVOReceptor().setCuenta("3399");

        params.getEmisionVO().setIdTipoValor("0");
        params.getEmisionVO().setEmisora("GNP");
        params.getEmisionVO().setSerie("*");
        params.getEmisionVO().setCupon("0000");

        try {
            folio = divisionInternacionalService.getCapturaTraspazo(params);
            assertNotNull(folio);
            log.debug("Valor de folio 99:" + folio.intValue());
        } catch (BusinessException e) {
            log.debug("Caso 99 Lanzo: " + e.getMessage());
        } catch (RemoteAccessException e) {
            log.debug("Caso 99 Lanzo: " + e.getMessage());
        }

        log.debug("Termina " + getName());
    }

    /**
     * prueba el servicio getEstadoCuenta()
     */
    public void testGetEstadoCuenta() {

        log.debug("Comienza " + getName());

        assertNotNull(divisionInternacionalService);

        Calendar fecha = Calendar.getInstance();
        fecha.set(2007, Calendar.FEBRUARY, 22);
        String tipoOperacion = "D";
        AgenteVO agenteVO = new AgenteVO("01", "009", "1206");
        EmisionVO emisionVO = null;
        String folioControl = null;
        PaginaVO paginaVO = new PaginaVO();
        paginaVO.setOffset(new Integer(0));
        paginaVO.setRegistrosXPag(new Integer(9));
        EstadoCuentaTotalVO[] estadoCuentaTotalVO = null;
        try {

            /*
             * estadoCuentaTotalVO =
             * divisionInternacionalService.getEstadoCuenta(fecha.getTime(),
             * tipoOperacion, agenteVO, emisionVO, folioControl, paginaVO,
             * false); muestraResultadosEstadoCuenta(estadoCuentaTotalVO);
             * 
             * agenteVO = new AgenteVO("02","012","6304"); fecha.set(2007,
             * Calendar.FEBRUARY, 20); tipoOperacion = "T";
             */

            /*
             * estadoCuentaTotalVO =
             * divisionInternacionalService.getEstadoCuenta(fecha.getTime(),
             * tipoOperacion, agenteVO, emisionVO, folioControl, paginaVO,
             * true); muestraResultadosEstadoCuenta(estadoCuentaTotalVO);
             */

            /*
             * tipoOperacion = "*"; estadoCuentaTotalVO =
             * divisionInternacionalService.getEstadoCuenta(fecha.getTime(),
             * tipoOperacion, agenteVO, emisionVO, folioControl, paginaVO,
             * true); muestraResultadosEstadoCuenta(estadoCuentaTotalVO);
             */

            /*
             * agenteVO = new AgenteVO("02","012","0031"); fecha.set(2006,
             * Calendar.OCTOBER, 18); tipoOperacion = "R";
             * 
             * estadoCuentaTotalVO =
             * divisionInternacionalService.getEstadoCuenta(fecha.getTime(),
             * tipoOperacion, agenteVO, emisionVO, folioControl, paginaVO,
             * false); muestraResultadosEstadoCuenta(estadoCuentaTotalVO);
             */

            agenteVO = new AgenteVO("01", "002", "0208");
            fecha.set(1996, Calendar.JUNE, 26);
            tipoOperacion = "T";

            estadoCuentaTotalVO = divisionInternacionalService.getEstadoCuenta(
                    fecha.getTime(), tipoOperacion, agenteVO, emisionVO,
                    folioControl, paginaVO, false);
            assertNotNull(estadoCuentaTotalVO);
            muestraResultadosEstadoCuenta(estadoCuentaTotalVO);
        } catch (BusinessException e) {
            log.debug("Lanzo " + e.getMessage());
        } catch (RemoteAccessException e) {
            log.debug("Lanzo " + e.getMessage());
        }
        log.debug("Termina " + getName());
    }

    /**
     * Muestra el resultado de testGetEstadoCuenta()
     * 
     * @param estadoCuentaTotalVO
     */
    private void muestraResultadosEstadoCuenta(
            EstadoCuentaTotalVO[] estadosCuenta) {
        log.debug("Entra al muestraResultadosEstadoCuenta");
        assertNotNull(estadosCuenta);
        for (int i = 0; i < estadosCuenta.length; i++) {
            EstadoCuentaTotalVO estadoCuentaTotalVO = estadosCuenta[i];
            assertNotNull(estadoCuentaTotalVO);
            assertNotNull(estadoCuentaTotalVO.getPaginaVO());
            assertNotNull(estadoCuentaTotalVO.getPaginaVO().getRegistros());
            assertFalse(estadoCuentaTotalVO.getPaginaVO().getRegistros()
                    .isEmpty());

            log.debug("Registros "
                    + estadoCuentaTotalVO.getPaginaVO().getRegistros().size());
            log.debug("Registros "
                    + estadoCuentaTotalVO.getPaginaVO().getTotalRegistros());
            for (Iterator it = estadoCuentaTotalVO.getPaginaVO().getRegistros()
                    .iterator(); it.hasNext();) {

                EstadoCuentaVO estadoCuentaVO = (EstadoCuentaVO) it.next();
                log.debug("Registro "
                        + ToStringBuilder.reflectionToString(estadoCuentaVO));
            }
            log.debug("Cuentas "
                    + ToStringBuilder.reflectionToString(estadoCuentaTotalVO
                            .getCuentas()));
            log
                    .debug("total entradas "
                            + estadoCuentaTotalVO.getTotalEntrada());
            log.debug("total salidas " + estadoCuentaTotalVO.getTotalSalida());
            log.debug("Termina mostrar datos!!!");
        }

    }

    /**
     * Prueba el servicio getConsultaOperaciones
     */
    public void testGetConsultaOperaciones() {

        log.debug("Comienza " + getName());

        assertNotNull(divisionInternacionalService);

        EmisionVO emisionVO = new EmisionVO();
        AgenteVO agenteVO = new AgenteVO();
        agenteVO.setId("01");
        agenteVO.setFolio("001");

        Calendar cal = Calendar.getInstance();
        cal.set(2007, Calendar.MAY, 15, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date fecha = cal.getTime();

        String estado = "Todos";
        String llaveFolio = null;

        PaginaVO paginaVO = new PaginaVO();
        paginaVO.setOffset(new Integer(0));
        paginaVO.setRegistrosXPag(new Integer(30));

        try {
            // primer caso
            paginaVO = divisionInternacionalService.getConsultaOperaciones(
                    emisionVO, agenteVO, fecha, estado, llaveFolio, paginaVO,
                    "540");
            assertNotNull(paginaVO);
            muestraResultadosConsultaOperaciones(paginaVO);
            // segundo caso
            // cal.set(2007,Calendar.JUNE,8,0,0,0);
            // cal.set(Calendar.MILLISECOND,0);
            // fecha = cal.getTime();
            // estado = "L";
            // paginaVO =
            // divisionInternacionalService.getConsultaOperaciones(emisionVO,
            // agenteVO,
            // fecha, estado, llaveFolio, paginaVO);
            // muestraResultadosConsultaOperaciones(paginaVO);
            // //tercer caso
            // cal.set(2002,Calendar.NOVEMBER,19);
            // fecha = cal.getTime();
            // estado = "B";
            // paginaVO =
            // divisionInternacionalService.getConsultaOperaciones(emisionVO,
            // agenteVO,
            // fecha, estado, llaveFolio, paginaVO);
            // muestraResultadosConsultaOperaciones(paginaVO);
        } catch (BusinessException ex) {
            log.debug("Lanzo: " + ex.getMessage());
        } catch (RemoteAccessException e) {
            log.debug("Lanzo: " + e.getMessage());
        }

        log.debug("Termina " + getName());
    }

    /**
     * 
     * @param paginaVO
     */
    private void muestraResultadosConsultaOperaciones(PaginaVO paginaVO) {

        assertNotNull(paginaVO);
        assertNotNull(paginaVO.getRegistros());
        assertFalse(paginaVO.getRegistros().isEmpty());
        log.debug("PaginaVO "
                + ReflectionToStringBuilder.reflectionToString(paginaVO));

        assertNotNull(divisionInternacionalService);

        int i = 1;
        for (Iterator it = paginaVO.getRegistros().iterator(); it.hasNext(); i++) {
            ConsultaOperacionesVO operacionesVO = (ConsultaOperacionesVO) it
                    .next();
            log.debug("Registro "
                    + i
                    + " "
                    + ReflectionToStringBuilder
                            .reflectionToString(operacionesVO));
        }
    }

    /**
     * Prueba el servicio CapturaTOperacion
     */
    public void testCapturaOperacion() {

        log.debug("Comienza " + getName());

        assertNotNull(divisionInternacionalService);

        CapturaOperacionParams params = new CapturaOperacionParams();

        AgenteVO agenteVO = new AgenteVO();
        agenteVO.setId("01");
        agenteVO.setFolio("24");
        agenteVO.setCuenta("2914");
        params.setAgente(agenteVO);

        EmisionVO emisionVO = new EmisionVO();
        emisionVO.setIdTipoValor("1");
        emisionVO.setEmisora("POSADAS");
        emisionVO.setSerie("A");
        emisionVO.setCupon("0006");
        params.setEmision(emisionVO);

        Calendar c = Calendar.getInstance();
        c.set(2007, Calendar.JULY, 20, 0, 0, 0);
        c.set(Calendar.MILLISECOND, 0);

        params.setTipoOperacion(new Integer(1));
        params.setFechaNotificacion(c.getTime());
        params.setFechaLiquidacion(c.getTime());
        params.setFechaOperacion(c.getTime());
        params.setCantidad(new BigDecimal(1500));
        // params.setCustodio("'CLEARSTREAM BANKING");

        // params.setCuentaContraparte("54");
        // params.setDescContraparte("DESCRIPCION CONTRAPARTE");
        // params.setNombreDepositoLiq("THE DEPOSITORY TRUST COMPANY");
        // params.setInstruccEspeciales("INSTRUCCIONES ESPECIALES");

        // params.setNombreCuenta("CUENTA NOMBRE");
        // params.setNumeroCuenta("987654321");

        // params.setUsuario("CBACCVAL");
        // params.setNombreUsuario("CBACCVAL");

        try {
            String folio = divisionInternacionalService
                    .capturaOperacion(params);
            assertNotNull(folio);
            log.debug("Valor de folio :" + folio);
        } catch (BusinessException e) {
            log.debug("Error: " + e);
        }

        log.debug("Termina " + getName());
    }

    /**
     * Caso Exito Linea
     */
    public void testGetArqueoValoresLinea() {
        log
                .debug("Entrando a ITestDivisionInFternacionalService.testGetArqueoValoresLinea()");
        assertNotNull(divisionInternacionalService);

        AgenteVO agenteVO = new AgenteVO("02", "022");
        EmisionVO emisionVO = null;
        Date fechaConsulta = new Date();

        try {
            ArqueoVO arqueoVO = divisionInternacionalService.getArqueoValores(
                    agenteVO, emisionVO, null, fechaConsulta, Boolean.FALSE);

            assertNotNull(arqueoVO);

            log.debug("Imprimiendo el objeto completo: "
                    + ToStringBuilder.reflectionToString(arqueoVO));

            EmisionArqueadaVO[] emisionArqueadaVOs = arqueoVO
                    .getDatosEmisiones();

            assertNotNull(emisionArqueadaVOs);

            log
                    .debug("Imprimiendo Emision Arqueada: "
                            + ToStringBuilder
                                    .reflectionToString(emisionArqueadaVOs[0]));

            ListaArqueoVO listaArqueoVO = emisionArqueadaVOs[0]
                    .getListaArqueos();

            assertNotNull(listaArqueoVO);

            log.debug("Imprimiendo Registros de la Emision Arqueada: ");

            AgenteArqueoVO[] agenteArqueoVOs = listaArqueoVO.getAgentesArqueo();

            assertNotNull(agenteArqueoVOs);

            log.debug("Imprimiendo Agentes de la Emision Arqueada: "
                    + ToStringBuilder.reflectionToString(agenteArqueoVOs));

            for (int i = 0; i < agenteArqueoVOs.length; i++) {
                log
                        .debug("Imprimiendo Datos de los Agentes de la Emision Arqueada: "
                                + ToStringBuilder
                                        .reflectionToString(agenteArqueoVOs[i]));
            }

            TotalListaArqueoVO totalListaArqueoVO = listaArqueoVO
                    .getTotalListaArqueo();

            assertNotNull(totalListaArqueoVO);

            log.debug("Imprimiendo Totales de la Emision Arqueada: "
                    + ToStringBuilder.reflectionToString(totalListaArqueoVO));

            PorcentajeArqueoVO porcentajeArqueoVO = emisionArqueadaVOs[0]
                    .getPorcentajesArqueo();

            assertNotNull(porcentajeArqueoVO);

            log.debug("Imprimiendo Estadisticas de la Emision Arqueada: "
                    + ToStringBuilder.reflectionToString(porcentajeArqueoVO));

            PorcentajeAgenteVO[] porcentajeAgenteVOs = porcentajeArqueoVO
                    .getPorcentajesAgentes();

            assertNotNull(porcentajeAgenteVOs);

            for (int i = 0; i < porcentajeAgenteVOs.length; i++) {
                log
                        .debug("Imprimiendo Datos de las Estadisticas de los Agentes de la Emision Arqueada: "
                                + ToStringBuilder
                                        .reflectionToString(porcentajeAgenteVOs[i]));
            }

            PorcentajeCuentaVO[] porcentajeCuentaVOs = porcentajeArqueoVO
                    .getPorcentajesCuentas();

            assertNotNull(porcentajeCuentaVOs);

            for (int i = 0; i < porcentajeCuentaVOs.length; i++) {
                log
                        .debug("Imprimiendo Datos de las Estadisticas de las Cuentas de la Emision Arqueada: "
                                + ToStringBuilder
                                        .reflectionToString(porcentajeCuentaVOs[i]));
            }

            TotalArqueoVO totalArqueoVO = arqueoVO.getTotalArqueoVO();

            assertNotNull(totalArqueoVO);

            log.debug("Imprimiendo Totales del Arqueo: "
                    + ToStringBuilder.reflectionToString(totalArqueoVO));

        } catch (BusinessException businessException) {
            log.debug("Error : " + businessException);
        }

        log
                .debug("Saliendo de ITestDivisionInFternacionalService.testGetArqueoValoresLinea()");
    }

    /**
     * Caso Exito Linea2
     */
    public void testGetArqueoValoresLinea2() {
        log
                .debug("Entrando a ITestDivisionInFternacionalService.testGetArqueoValoresLinea2()");
        assertNotNull(divisionInternacionalService);

        AgenteVO agenteVO = new AgenteVO("02", "022");
        EmisionVO emisionVO = new EmisionVO();
        emisionVO.setIdTipoValor("1");
        emisionVO.setEmisora("SANLUIS");
        emisionVO.setSerie("A");
        emisionVO.setCupon("0011");
        emisionVO.setUltimoHecho(BigDecimal.valueOf(0.59));
        emisionVO.setValorNominal(BigDecimal.valueOf(1));
        emisionVO.setAlta("ADCP");

        Date fechaConsulta = new Date();

        try {
            ArqueoVO arqueoVO = divisionInternacionalService.getArqueoValores(
                    agenteVO, emisionVO, null, fechaConsulta, Boolean.FALSE);

            assertNotNull(arqueoVO);

            log.debug("Imprimiendo el objeto completo: "
                    + ToStringBuilder.reflectionToString(arqueoVO));

            EmisionArqueadaVO[] emisionArqueadaVOs = arqueoVO
                    .getDatosEmisiones();

            assertNotNull(emisionArqueadaVOs);

            log
                    .debug("Imprimiendo Emision Arqueada: "
                            + ToStringBuilder
                                    .reflectionToString(emisionArqueadaVOs[0]));

            ListaArqueoVO listaArqueoVO = emisionArqueadaVOs[0]
                    .getListaArqueos();

            assertNotNull(listaArqueoVO);

            log.debug("Imprimiendo Registros de la Emision Arqueada: ");

            AgenteArqueoVO[] agenteArqueoVOs = listaArqueoVO.getAgentesArqueo();

            assertNotNull(agenteArqueoVOs);

            log.debug("Imprimiendo Agentes de la Emision Arqueada: "
                    + ToStringBuilder.reflectionToString(agenteArqueoVOs));

            for (int i = 0; i < agenteArqueoVOs.length; i++) {
                log
                        .debug("Imprimiendo Datos de los Agentes de la Emision Arqueada: "
                                + ToStringBuilder
                                        .reflectionToString(agenteArqueoVOs[i]));
            }

            TotalListaArqueoVO totalListaArqueoVO = listaArqueoVO
                    .getTotalListaArqueo();

            assertNotNull(totalListaArqueoVO);

            log.debug("Imprimiendo Totales de la Emision Arqueada: "
                    + ToStringBuilder.reflectionToString(totalListaArqueoVO));

            PorcentajeArqueoVO porcentajeArqueoVO = emisionArqueadaVOs[0]
                    .getPorcentajesArqueo();

            assertNotNull(porcentajeArqueoVO);

            log.debug("Imprimiendo Estadisticas de la Emision Arqueada: "
                    + ToStringBuilder.reflectionToString(porcentajeArqueoVO));

            PorcentajeAgenteVO[] porcentajeAgenteVOs = porcentajeArqueoVO
                    .getPorcentajesAgentes();

            assertNotNull(porcentajeAgenteVOs);

            for (int i = 0; i < porcentajeAgenteVOs.length; i++) {
                log
                        .debug("Imprimiendo Datos de las Estadisticas de los Agentes de la Emision Arqueada: "
                                + ToStringBuilder
                                        .reflectionToString(porcentajeAgenteVOs[i]));
            }

            PorcentajeCuentaVO[] porcentajeCuentaVOs = porcentajeArqueoVO
                    .getPorcentajesCuentas();

            assertNotNull(porcentajeCuentaVOs);

            for (int i = 0; i < porcentajeCuentaVOs.length; i++) {
                log.debug("Imprimiendo Datos de las Estadisticas de las Cuentas de la Emision Arqueada: "
                                + ToStringBuilder.reflectionToString(porcentajeCuentaVOs[i]));
            }

            TotalArqueoVO totalArqueoVO = arqueoVO.getTotalArqueoVO();

            assertNotNull(totalArqueoVO);

            log.debug("Imprimiendo Totales del Arqueo: "
                    + ToStringBuilder.reflectionToString(totalArqueoVO));

        } catch (BusinessException businessException) {
            log.debug("Error : " + businessException);
        }

        log.debug("Saliendo de ITestDivisionInFternacionalService.testGetArqueoValoresLinea2()");
    }

    /**
     * Caso Exito Historico
     */
    public void testGetArqueoValoresHistorico() {
        log.debug("Entrando a ITestDivisionInFternacionalService.testGetArqueoValoresHistorico()");
        assertNotNull(divisionInternacionalService);

        AgenteVO agenteVO = new AgenteVO("02", "022");
        EmisionVO emisionVO = null;
        Calendar calendar = Calendar.getInstance();
        calendar.set(2007, Calendar.AUGUST, 15, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        try {
            ArqueoVO arqueoVO = divisionInternacionalService.getArqueoValores(
                    agenteVO, emisionVO, null, calendar.getTime(),
                    Boolean.FALSE);

            assertNotNull(arqueoVO);

            log.debug("Imprimiendo el objeto completo: "
                    + ToStringBuilder.reflectionToString(arqueoVO));

            EmisionArqueadaVO[] emisionArqueadaVOs = arqueoVO
                    .getDatosEmisiones();

            assertNotNull(emisionArqueadaVOs);

            log.debug("Imprimiendo Emision Arqueada: "
                            + ToStringBuilder
                                    .reflectionToString(emisionArqueadaVOs[0]));

            ListaArqueoVO listaArqueoVO = emisionArqueadaVOs[0]
                    .getListaArqueos();

            assertNotNull(listaArqueoVO);

            log.debug("Imprimiendo Registros de la Emision Arqueada: ");

            AgenteArqueoVO[] agenteArqueoVOs = listaArqueoVO.getAgentesArqueo();

            assertNotNull(agenteArqueoVOs);

            log.debug("Imprimiendo Agentes de la Emision Arqueada: "
                    + ToStringBuilder.reflectionToString(agenteArqueoVOs));

            for (int i = 0; i < agenteArqueoVOs.length; i++) {
                log.debug("Imprimiendo Datos de los Agentes de la Emision Arqueada: "
                                + ToStringBuilder
                                        .reflectionToString(agenteArqueoVOs[i]));
            }

            TotalListaArqueoVO totalListaArqueoVO = listaArqueoVO
                    .getTotalListaArqueo();

            assertNotNull(totalListaArqueoVO);

            log.debug("Imprimiendo Totales de la Emision Arqueada: "
                    + ToStringBuilder.reflectionToString(totalListaArqueoVO));

            TotalArqueoVO totalArqueoVO = arqueoVO.getTotalArqueoVO();

            assertNotNull(totalArqueoVO);

            log.debug("Imprimiendo Totales del Arqueo: "
                    + ToStringBuilder.reflectionToString(totalArqueoVO));

        } catch (BusinessException businessException) {
            log.debug("Error : " + businessException);
        }

        log.debug("Saliendo de ITestDivisionInFternacionalService.testGetArqueoValoresHistorico()");
    }

    /**
     * Caso Exito Historico2
     */
    public void testGetArqueoValoresHistotico2() {
        log.debug("Entrando a ITestDivisionInFternacionalService.testGetArqueoValoresHistotico2()");
        assertNotNull(divisionInternacionalService);

        AgenteVO agenteVO = new AgenteVO("02", "022");
        EmisionVO emisionVO = new EmisionVO();
        emisionVO.setIdTipoValor("00");
        emisionVO.setEmisora("AEROMEX");
        emisionVO.setSerie("A");
        emisionVO.setCupon("0001");
        emisionVO.setUltimoHecho(BigDecimal.valueOf(0.59));
        emisionVO.setValorNominal(BigDecimal.valueOf(1));
        emisionVO.setAlta("ADCP");

        Calendar calendar = Calendar.getInstance();
        calendar.set(2007, Calendar.AUGUST, 15, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        try {
            ArqueoVO arqueoVO = divisionInternacionalService.getArqueoValores(
                    agenteVO, emisionVO, null, calendar.getTime(),
                    Boolean.FALSE);

            assertNotNull(arqueoVO);

            log.debug("Imprimiendo el objeto completo: "
                    + ToStringBuilder.reflectionToString(arqueoVO));

            EmisionArqueadaVO[] emisionArqueadaVOs = arqueoVO
                    .getDatosEmisiones();

            assertNotNull(emisionArqueadaVOs);

            log.debug("Imprimiendo Emision Arqueada: "
                            + ToStringBuilder
                                    .reflectionToString(emisionArqueadaVOs[0]));

            ListaArqueoVO listaArqueoVO = emisionArqueadaVOs[0].getListaArqueos();

            assertNotNull(listaArqueoVO);

            log.debug("Imprimiendo Registros de la Emision Arqueada: ");

            AgenteArqueoVO[] agenteArqueoVOs = listaArqueoVO.getAgentesArqueo();

            assertNotNull(agenteArqueoVOs);

            log.debug("Imprimiendo Agentes de la Emision Arqueada: "
                    + ToStringBuilder.reflectionToString(agenteArqueoVOs));

            for (int i = 0; i < agenteArqueoVOs.length; i++) {
                log.debug("Imprimiendo Datos de los Agentes de la Emision Arqueada: "
                                + ToStringBuilder
                                        .reflectionToString(agenteArqueoVOs[i]));
            }

            TotalListaArqueoVO totalListaArqueoVO = listaArqueoVO
                    .getTotalListaArqueo();

            assertNotNull(totalListaArqueoVO);

            log.debug("Imprimiendo Totales de la Emision Arqueada: "
                    + ToStringBuilder.reflectionToString(totalListaArqueoVO));

            TotalArqueoVO totalArqueoVO = arqueoVO.getTotalArqueoVO();

            assertNotNull(totalArqueoVO);

            log.debug("Imprimiendo Totales del Arqueo: "
                    + ToStringBuilder.reflectionToString(totalArqueoVO));

        } catch (BusinessException businessException) {
            log.debug("Error : " + businessException);
        }

        log.debug("Saliendo de ITestDivisionInFternacionalService.testGetArqueoValoresHistotico2()");
    }

    /**
     * Caso Exito Exportacion Linea
     */
    public void testGetArqueoValoresExportacionLinea() {
        log.debug("Entrando a ITestDivisionInFternacionalService.testGetArqueoValoresExportacionLinea()");
        assertNotNull(divisionInternacionalService);

        AgenteVO agenteVO = new AgenteVO("02", "022");
        EmisionVO emisionVO = null;
        Date fechaConsulta = new Date();

        try {
            ArqueoVO arqueoVO = divisionInternacionalService.getArqueoValores(
                    agenteVO, emisionVO, null, fechaConsulta, Boolean.TRUE);

            assertNotNull(arqueoVO);

            log.debug("Imprimiendo el objeto completo: "
                    + ToStringBuilder.reflectionToString(arqueoVO));

            EmisionArqueadaVO[] emisionArqueadaVOs = arqueoVO
                    .getDatosEmisiones();

            assertNotNull(emisionArqueadaVOs);

            log.debug("Imprimiendo Emision Arqueada: "
                            + ToStringBuilder.reflectionToString(emisionArqueadaVOs[0]));

            for (int i = 0; i < emisionArqueadaVOs.length; i++) {

                ListaArqueoVO listaArqueoVO = emisionArqueadaVOs[i]
                        .getListaArqueos();

                assertNotNull(listaArqueoVO);

                log.debug("Imprimiendo Registros de la Emision Arqueada: ");

                AgenteArqueoVO[] agenteArqueoVOs = listaArqueoVO
                        .getAgentesArqueo();

                assertNotNull(agenteArqueoVOs);

                log.debug("Imprimiendo Agentes de la Emision Arqueada: "
                        + ToStringBuilder.reflectionToString(agenteArqueoVOs));

                for (int j = 0; j < agenteArqueoVOs.length; j++) {
                    log.debug("Imprimiendo Datos de los Agentes de la Emision Arqueada: "
                                    + ToStringBuilder
                                            .reflectionToString(agenteArqueoVOs[j]));
                }

                TotalListaArqueoVO totalListaArqueoVO = listaArqueoVO.getTotalListaArqueo();

                assertNotNull(totalListaArqueoVO);

                log.debug("Imprimiendo Totales de la Emision Arqueada: "
                        + ToStringBuilder
                                .reflectionToString(totalListaArqueoVO));

                PorcentajeArqueoVO porcentajeArqueoVO = emisionArqueadaVOs[i]
                        .getPorcentajesArqueo();

                assertNotNull(porcentajeArqueoVO);

                log.debug("Imprimiendo Estadisticas de la Emision Arqueada: "
                        + ToStringBuilder
                                .reflectionToString(porcentajeArqueoVO));

                PorcentajeAgenteVO[] porcentajeAgenteVOs = porcentajeArqueoVO
                        .getPorcentajesAgentes();

                assertNotNull(porcentajeAgenteVOs);

                for (int x = 0; x < porcentajeAgenteVOs.length; x++) {
                    log
                            .debug("Imprimiendo Datos de las Estadisticas de los Agentes de la Emision Arqueada: "
                                    + ToStringBuilder
                                            .reflectionToString(porcentajeAgenteVOs[x]));
                }

                PorcentajeCuentaVO[] porcentajeCuentaVOs = porcentajeArqueoVO
                        .getPorcentajesCuentas();

                assertNotNull(porcentajeCuentaVOs);

                for (int z = 0; z < porcentajeCuentaVOs.length; z++) {
                    log
                            .debug("Imprimiendo Datos de las Estadisticas de las Cuentas de la Emision Arqueada: "
                                    + ToStringBuilder
                                            .reflectionToString(porcentajeCuentaVOs[z]));
                }

                TotalArqueoVO totalArqueoVO = arqueoVO.getTotalArqueoVO();

                assertNotNull(totalArqueoVO);

                log.debug("Imprimiendo Totales del Arqueo: "
                        + ToStringBuilder.reflectionToString(totalArqueoVO));
            }

        } catch (BusinessException businessException) {
            log.debug("Error : " + businessException);
        }

        log
                .debug("Saliendo de ITestDivisionInFternacionalService.testGetArqueoValoresExportacionLinea()");
    }

}

/*
 *Copyright (c) 2016 Bursatec. All Rights Reserved 
 */
package com.indeval.portaldali.presentation.controller.tesoreria;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;

import com.indeval.portaldali.middleware.services.decretos.LiquidacionDecretosDaliService;
import com.indeval.portaldali.presentation.controller.common.ControllerBase;
import com.indeval.portaldali.presentation.dto.tesoreria.LiquidacionDecretosDTO;
import com.indeval.sidv.decretos.persistence.model.vo.AgenteVO;
import com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosParams;
import com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosVO;

/**
 * Controller que da soporte a los reportes de -> Estado de Cuenta de Liq. por Decretos. 
 * Este controller consume servicios del modulo Ejercicio de Derechos.
 * 
 */
public class ConsultaEstadoCuentaLiquidacionDecretosExportacionController extends ControllerBase {

    /** Nombre del bean de consulta */
    private final String BEAN_CONSULTA = "consultaEstadoCuentaLiqDecretosController";

    /**
     * Parametros de consulta
     */
    private LiquidacionDecretosParams edoCtaLiqParams = new LiquidacionDecretosParams();

    /**
     * Id y folio de la institucion
     */
    private String idFolioParticipante = null;

    private int totalResultados = 0;
    
    private List <LiquidacionDecretosDTO> resultados = null;

    private BigDecimal totalImporteACobrar = null;

    private BigDecimal totalImporteCobrado = null;

    private BigDecimal totalRemanente = null;
    
    private List<LiquidacionDecretosVO> datosLiquidacion = null;

    /**
     * servicio para consultar la lista de tipos de ejercicio
     */
    private LiquidacionDecretosDaliService liquidacionDecretosDaliService = null;


	

    /**
     * Ejecuta la consulta para los reportes de exportacion
     * 
     * @param e
     */
    @SuppressWarnings("unchecked")
    public void generarReportes(ActionEvent ev) {
    	reiniciarEstadoPeticion();
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        ConsultaEstadoCuentaLiquidacionDecretosController consultaEdoCtaLiqDecController = 
            (ConsultaEstadoCuentaLiquidacionDecretosController) FacesContext.getCurrentInstance().getApplication().
                getELResolver().getValue(elContext, null, BEAN_CONSULTA);
        this.edoCtaLiqParams = consultaEdoCtaLiqDecController.getParametros();
        this.idFolioParticipante = consultaEdoCtaLiqDecController.getIdFolioParticipante();

        LiquidacionDecretosParams parametros = crearCriterio();
        try {
            this.datosLiquidacion = liquidacionDecretosDaliService.getLiquidacionDecretos(parametros, false);
            this.totalResultados = this.datosLiquidacion.size();
            this.obtenerTotales(this.datosLiquidacion);
            this.armarResultados();
            //ordena la lista para exportar
            if (resultados != null || !resultados.isEmpty()) 
                Collections.sort(resultados, new Comparator<LiquidacionDecretosDTO>() {
                    public int compare(LiquidacionDecretosDTO vo1, LiquidacionDecretosDTO vo2) {
                        StringBuilder buffer1 = new StringBuilder();
                        StringBuilder buffer2 = new StringBuilder();
                        buffer1.append(vo1.getIdInst() + vo1.getFolioInst())
                            .append(vo1.getTipoDerecho());

                        buffer2.append(vo2.getIdInst() + vo2.getFolioInst())
                            .append(vo2.getTipoDerecho());

                        return buffer1.toString().compareTo(buffer2.toString());
                    }
                });
        } catch (com.indeval.sidv.decretos.common.exception.BusinessException e) {
            e.printStackTrace();
        }
        //obtiene el subtotal de cada bloque encontrado
        BigDecimal subtotalImporteCobrar = new BigDecimal(0);
        BigDecimal subtotalImporteCobrado = new BigDecimal(0);
        BigDecimal subtotalRemanente = new BigDecimal(0);

        int index = 0;
        int indexInicial = 0;
        List<List<LiquidacionDecretosDTO>> listaSubtotales = new ArrayList<List<LiquidacionDecretosDTO>>();
        List<LiquidacionDecretosDTO> vector= new ArrayList<LiquidacionDecretosDTO>();

        String tipoEjercicioActual = resultados.get(0).getTipoDerecho();
        String institucion = resultados.get(0).getIdInst() + resultados.get(0).getFolioInst();
        for (LiquidacionDecretosDTO liquidacionDecretosVO : resultados) {
            index += 1;
            if (liquidacionDecretosVO.getImporteACobrar() == null) {
                liquidacionDecretosVO.setImporteACobrar(new BigDecimal(0));
            }
            if (liquidacionDecretosVO.getImporteCobrado() == null) {
                liquidacionDecretosVO.setImporteCobrado(new BigDecimal(0));
            }
            if (liquidacionDecretosVO.getRemanente() == null) {
                liquidacionDecretosVO.setRemanente(new BigDecimal(0));
            }
            if ((liquidacionDecretosVO.getIdInst() + liquidacionDecretosVO.getFolioInst()).equals(institucion) &&
                 liquidacionDecretosVO.getTipoDerecho().equals(tipoEjercicioActual)) {
                subtotalImporteCobrar = subtotalImporteCobrar.add(liquidacionDecretosVO.getImporteACobrar());
                subtotalImporteCobrado = subtotalImporteCobrado.add(liquidacionDecretosVO.getImporteCobrado());
                subtotalRemanente = subtotalRemanente.add(liquidacionDecretosVO.getRemanente());
                    
            } 
            else {
                LiquidacionDecretosVO decretos= new LiquidacionDecretosVO();
                decretos.setImporteACobrar(subtotalImporteCobrar);
                decretos.setImporteCobrado(subtotalImporteCobrado);
                decretos.setRemanente(subtotalRemanente);
                decretos.setTipoDerecho(tipoEjercicioActual);
                //solo en el reporte
                decretos.setIdDerecho(index);
                tipoEjercicioActual = liquidacionDecretosVO.getTipoDerecho();
                institucion = liquidacionDecretosVO.getIdInst() + liquidacionDecretosVO.getFolioInst();
                LiquidacionDecretosDTO subtotalEjercicio = new LiquidacionDecretosDTO(decretos);
                vector.add(subtotalEjercicio);
                listaSubtotales.add(resultados.subList(indexInicial, index));
                indexInicial = index;

                subtotalImporteCobrar = new BigDecimal(0);
                subtotalImporteCobrado = new BigDecimal(0);
                subtotalRemanente = new BigDecimal(0);

                subtotalImporteCobrar = subtotalImporteCobrar.add(liquidacionDecretosVO.getImporteACobrar());
                subtotalImporteCobrado = subtotalImporteCobrado.add(liquidacionDecretosVO.getImporteCobrado());
                subtotalRemanente = subtotalRemanente.add(liquidacionDecretosVO.getRemanente());
            }

            if (index == resultados.size()) {
                LiquidacionDecretosVO decretos= new LiquidacionDecretosVO();
                decretos.setImporteACobrar(subtotalImporteCobrar);
                decretos.setImporteCobrado(subtotalImporteCobrado);
                decretos.setRemanente(subtotalRemanente);
                decretos.setTipoDerecho(tipoEjercicioActual);
                //solo en el reporte
                decretos.setIdDerecho(index + 1);
                tipoEjercicioActual = liquidacionDecretosVO.getTipoDerecho();
                institucion = liquidacionDecretosVO.getIdInst() + liquidacionDecretosVO.getFolioInst();
                LiquidacionDecretosDTO subtotalEjercicio = new LiquidacionDecretosDTO(decretos);
                vector.add(subtotalEjercicio);
                listaSubtotales.add(resultados.subList(indexInicial, index));
                indexInicial = index;
            }
        }

        for (int i = vector.size() - 1; i >= 0; i--) {
            int n = (int) vector.get(i).getIdDerecho()-1;
            vector.get(i).setTipoDerecho("SUBTOTAL " + vector.get(i).getTipoDerecho());
            resultados.add(n, vector.get(i));
        }
    }

    /**
     * Calcula el gran total de importes.
     * 
     * @param datosLiquidacion Los resultados de los decretos filtrados.
     */
    private void obtenerTotales(List<LiquidacionDecretosVO>  datosLiquidacion) {
        double cobrado = 0;
        double porCobrar = 0;
        double remanente = 0;

        if (datosLiquidacion != null && !datosLiquidacion.isEmpty()) {
            for (LiquidacionDecretosVO vo : datosLiquidacion) {
                cobrado += vo.getImporteCobrado() != null ? vo.getImporteCobrado().doubleValue() : 0;
                porCobrar += vo.getImporteACobrar() != null ? vo.getImporteACobrar().doubleValue() : 0;
                remanente += vo.getRemanente() != null ? vo.getRemanente().doubleValue() : 0;
            }
        }

        this.totalImporteACobrar = new BigDecimal(porCobrar);
        this.totalImporteCobrado = new BigDecimal(cobrado);
        this.totalRemanente = new BigDecimal(remanente);
    }

    /**
     * Crea un objeto de parametros copia del objeto original del controller
     * 
     * @return Objeto de parametros
     */
    private LiquidacionDecretosParams crearCriterio() {
        LiquidacionDecretosParams params = new LiquidacionDecretosParams();

        params.setAgente(new AgenteVO(this.obtenerIdInstitucion(idFolioParticipante), this.obtenerFolioInstitucion(idFolioParticipante), 
                                      edoCtaLiqParams.getAgente().getCuenta()));

        params.setEmision(edoCtaLiqParams.getEmision());
        params.getEmision().setIdTipoValor(StringUtils.trimToNull(params.getEmision().getIdTipoValor()));
        params.getEmision().setEmisora(StringUtils.trimToNull(params.getEmision().getEmisora()));
        params.getEmision().setSerie(StringUtils.trimToNull(params.getEmision().getSerie()));
        params.getEmision().setCupon(StringUtils.trimToNull(params.getEmision().getCupon()));
        if (params.getEmision().getIdTipoValor() == null && params.getEmision().getEmisora() == null && 
            params.getEmision().getSerie() == null && params.getEmision().getCupon() == null) {
            params.setEmision(null);            
        }

        params.setFechaFin(edoCtaLiqParams.getFechaFin());
        params.setFechaIni(edoCtaLiqParams.getFechaIni());
        params.setTipoConsulta(edoCtaLiqParams.getTipoConsulta());
        if (StringUtils.isEmpty(edoCtaLiqParams.getTipoEjercicio()) || "-1".equals(edoCtaLiqParams.getTipoEjercicio())) {
            params.setTipoEjercicio(null);
        } 
        else {
            params.setTipoEjercicio(edoCtaLiqParams.getTipoEjercicio());
        }
        params.setTipoMoneda(edoCtaLiqParams.getTipoMoneda());

        return params;
    }

    public void armarResultados() {
        resultados = new ArrayList<LiquidacionDecretosDTO>();
        for (LiquidacionDecretosVO datos: this.datosLiquidacion) {
            LiquidacionDecretosDTO liquidacion= new LiquidacionDecretosDTO(datos);
            resultados.add(liquidacion);
        }
        this.datosLiquidacion = null;
    }

    /**
     * @return the edoCtaLiqParams
     */
    public LiquidacionDecretosParams getEdoCtaLiqParams() {
        return edoCtaLiqParams;
    }

    /**
     * @return the idFolioParticipante
     */
    public String getIdFolioParticipante() {
        return idFolioParticipante;
    }

    /**
     * @return the totalResultados
     */
    public int getTotalResultados() {
        return totalResultados;
    }

    /**
     * @return the totalImporteACobrar
     */
    public BigDecimal getTotalImporteACobrar() {
        return totalImporteACobrar;
    }

    /**
     * @return the totalImporteCobrado
     */
    public BigDecimal getTotalImporteCobrado() {
        return totalImporteCobrado;
    }

    /**
     * @return the totalRemanente
     */
    public BigDecimal getTotalRemanente() {
        return totalRemanente;
    }

    public List<LiquidacionDecretosDTO> getResultados() {
        return resultados;
    }

    /**
     * @param liquidacionDecretosDaliService the liquidacionDecretosDaliService to set
     */
    public void setLiquidacionDecretosDaliService(LiquidacionDecretosDaliService liquidacionDecretosDaliService) {
        this.liquidacionDecretosDaliService = liquidacionDecretosDaliService;
    }

}

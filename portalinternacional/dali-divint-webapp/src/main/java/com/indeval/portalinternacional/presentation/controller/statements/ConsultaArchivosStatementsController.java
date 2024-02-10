/*
 * Copyright (c) 2010 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.presentation.controller.statements;

import java.util.ArrayList;

import javax.faces.event.ActionEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.StatementsDivintService;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * Controller de la consulta de los Archivos de Statements
 * 
 * @author Rafael Ibarra
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class ConsultaArchivosStatementsController extends ControllerBase {

    /** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(ConsultaArchivosStatementsController.class);
    /** Servicio de Statements */
    private StatementsDivintService statementsDivintService;
    /** Indica si la consulta ya se ejecuto */
    private boolean consultaEjecutada;
    /** Pagina para los reportes*/
    private PaginaVO paginaReportes;
    /** Parametro del nombre del archivo */
    private String nombreArchivo;

    /**
     * Constructor de consulta Operaciones
     */
    public ConsultaArchivosStatementsController() {
        super();
    }

    /**
     * Asigna las opciones predeterminadas para cuando se carga la página por
     * primerva vez.
     *
     * @return nulo, este método no requiere retornar un valor
     */
    public String getInit() {
        log.debug("Entrnado a ConsultaArchivosStatementsController.getInit");
        paginaVO.setRegistrosXPag(50);
        nombreArchivo = null;
        return null;
    }

    /** Busca los registros
     * @param ActionEvent event
     */
    public void buscar(ActionEvent event) {
        paginaVO.setOffset(0);
        ejecutarConsulta();
    }

    @Override
    public String ejecutarConsulta() {
        if (paginaVO != null) {
            paginaVO.limpiaResultados();
        }
        setPaginaVO(statementsDivintService.consultaArchivosStatement(nombreArchivo, paginaVO));
        log.debug("Cantidad de registros: [" + paginaVO.getRegistros().size() + "]");
        if (paginaVO == null ||
                paginaVO.getRegistros() == null ||
                paginaVO.getRegistros().isEmpty()) {
            paginaVO.setTotalRegistros(0);
            paginaVO.setRegistros(new ArrayList());
        }
        consultaEjecutada = true;
        return null;
    }

    public void obtenerRegistros(ActionEvent event) {
        paginaVO.setOffset(0);
        ejecutarConsulta();
    }

    public void eliminarArchivo(ActionEvent event) {
        String archivoEliminar = (String)getActionAttribute(event, "archivoEliminar");
        log.info("Eliminando archivo: [" + archivoEliminar + "]");
        statementsDivintService.borraArchivo(archivoEliminar);
        obtenerRegistros(event);
        addErrorMessage("Archivo " + archivoEliminar + " eliminado.");
    }

    public void generarReportes(ActionEvent event) {
        paginaReportes = new PaginaVO();
        paginaReportes.setRegistrosXPag(PaginaVO.TODOS);
        paginaReportes.setOffset(0);
        paginaReportes = statementsDivintService.consultaArchivosStatement(nombreArchivo, paginaVO);
    }

    public void limpiar(ActionEvent event) {
        if (paginaVO != null) {
            if (paginaVO.getRegistros() != null) {
                paginaVO.getRegistros().clear();
                paginaVO.setRegistros(null);
            }
            paginaVO.setOffset(0);
            paginaVO.setTotalRegistros(0);
            paginaVO.setRegistrosXPag(20);
        }
        nombreArchivo = null;
        consultaEjecutada = false;
    }

    /**
     * Servicio de Statements
     * @param statementsDivintService the statementsDivintService to set
     */
    public void setStatementsDivintService(StatementsDivintService statementsDivintService) {
        this.statementsDivintService = statementsDivintService;
    }

    /**
     * Indica si la consulta ya se ejecuto
     * @return the consultaEjecutada
     */
    public boolean isConsultaEjecutada() {
        return consultaEjecutada;
    }

    /**
     * Indica si la consulta ya se ejecuto
     * @param consultaEjecutada the consultaEjecutada to set
     */
    public void setConsultaEjecutada(boolean consultaEjecutada) {
        this.consultaEjecutada = consultaEjecutada;
    }

    /**
     * Pagina para los reportes
     * @return the paginaReportes
     */
    public PaginaVO getPaginaReportes() {
        return paginaReportes;
    }

    /**
     * Pagina para los reportes
     * @param paginaReportes the paginaReportes to set
     */
    public void setPaginaReportes(PaginaVO paginaReportes) {
        this.paginaReportes = paginaReportes;
    }

    /**
     * Parametro del nombre del archivo
     * @return the nombreArchivo
     */
    public String getNombreArchivo() {
        return nombreArchivo;
    }

    /**
     * Parametro del nombre del archivo
     * @param nombreArchivo the nombreArchivo to set
     */
    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

}

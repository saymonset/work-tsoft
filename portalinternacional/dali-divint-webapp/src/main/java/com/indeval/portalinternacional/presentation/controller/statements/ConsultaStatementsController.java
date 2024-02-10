/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.presentation.controller.statements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.util.ConsultaCatalogoService;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.DomiciliosInstituciones;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.StatementsDivintService;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaStatementsParam;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * Controller de la consulta de statements
 * 
 * @author Rafael Ibarra
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class ConsultaStatementsController extends ControllerBase {

    /** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(ConsultaStatementsController.class);
    /** Servicio de Statements */
    private StatementsDivintService statementsDivintService;
    /** Servicio de Beneficiarios */
    private ControlBeneficiariosService controlBeneficiariosService;
    /** Lista de Tipos de Beneficiarios de acuerdo al custodio */
    private List tiposBeneficiarios;
    /** Indica si la consulta ya se ejecuto */
    private boolean consultaEjecutada;
    /** Parametros para la consulta */
    private ConsultaStatementsParam param = new ConsultaStatementsParam();
    /** Institucion seleccionada */
    private String idFolioInstitucion;
    /** Institucion seleccionada */
    private String nombreInstitucion;
    /** Servicio para obtener la institucion */
    private ConsultaCatalogoService consultaCatService;
    /** Pagina para los reportes*/
    private PaginaVO paginaReportes;
    /** Mapa que contiene la lista de custodios para la tabla */
    private Map mapaTipoBeneficiario;
    /** Mapa de los domicilios fiscales */
    private Map<Long, DomiciliosInstituciones> domiciliosFiscales;
    
    private List listaCustodios;
    

    /**
     * Constructor de consulta Operaciones
     */
    public ConsultaStatementsController() {
        super();
    }

    /**
     * Asigna las opciones predeterminadas para cuando se carga la página por
     * primerva vez.
     *
     * @return nulo, este método no requiere retornar un valor
     */
    public String getInit() {
        log.debug("Entrnado a ConsultaStatementsController.getInit");
        param = new ConsultaStatementsParam();
        inicializaTiposBeneficiarios();
        inicializaCustodios();
        paginaVO.setRegistrosXPag(20);
        if (!isInstitucionIndeval()) {
            idFolioInstitucion = getAgenteFirmado().getId() + getAgenteFirmado().getFolio();
            nombreInstitucion = getAgenteFirmado().getNombreCorto();
        } else {
            idFolioInstitucion = null;
            nombreInstitucion = null;
        }
        domiciliosFiscales = null;
        return null;
    }

    /** Busca los registros
     * @param ActionEvent event
     */
    public void buscar(ActionEvent event) {
        paginaVO.setOffset(0);
        if (param.getFechaRegistroInicio() != null && param.getFechaRegistroFin() != null) {
            if (param.getFechaRegistroInicio().compareTo(param.getFechaRegistroFin()) > 0) {
                addErrorMessage("Fechas de Registro Incorrectas");
                return;
            }
        }

        if (param.getFechaPagoInicio() != null && param.getFechaPagoFin() != null) {
            if (param.getFechaPagoInicio().compareTo(param.getFechaPagoFin()) > 0) {
                addErrorMessage("Fechas de Pago Incorrectas");
                return;
            }
        }

        ejecutarConsulta();
    }

    @Override
    public String ejecutarConsulta() {
        if (paginaVO != null) {
            paginaVO.limpiaResultados();
        }
        if (StringUtils.isNotBlank(idFolioInstitucion)) {
            Institucion ins = consultaCatService.findInstitucionByClaveFolio(idFolioInstitucion);
            if (ins != null) {
                param.setIdInstitucion(ins.getIdInstitucion());
            } else {
                addErrorMessage("Institucion invalida");
                return null;
            }
        } else {
            param.setIdInstitucion(null);
            if (!isInstitucionIndeval()) {
                addErrorMessage("Institucion requerida");
                return null;
            }
        }

        setPaginaVO(statementsDivintService.consultaStatements(param, paginaVO));
        domiciliosFiscales = statementsDivintService.consultaDomiciliosFiscales();
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

    public void generarReportes(ActionEvent event) {
    	
    	reiniciarEstadoPeticion();
        paginaReportes = new PaginaVO();
        paginaReportes.setRegistrosXPag(PaginaVO.TODOS);
        paginaReportes.setOffset(0);
        paginaReportes = statementsDivintService.consultaStatements(param, paginaReportes);
        domiciliosFiscales = statementsDivintService.consultaDomiciliosFiscales();
        inicializaMapaTiposBeneficiarios();
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
        consultaEjecutada = false;
        idFolioInstitucion = null;
        nombreInstitucion = null;
 
    }

    /**
     * Inicializa lista de custodios
     */
    private void inicializaTiposBeneficiarios() {
        tiposBeneficiarios = new ArrayList();

        List<Object[]> lista = controlBeneficiariosService.obtieneTiposBeneficiario();

        for (Object[] bene : lista) {
            tiposBeneficiarios.add(new SelectItem((Long) bene[0], (String) bene[1]));
        }
    }
    
    private void inicializaCustodios(){
    	listaCustodios = new ArrayList();
    	List<String> custodios = statementsDivintService.listaCustodios();
    	if(custodios != null  && !custodios.isEmpty()){
    		for (String c: custodios){
    			
    			 if (!(c == null  )){
					
					 listaCustodios.add(new SelectItem(c, c));
				 }
        	}	
    	}
    	
    }

    /**
     * Obtiene los datos de la institucion relacionada
     *
     * @param ActionEvent event
     */
    public void obtenerDatosParticipante(ActionEvent event) {
        Institucion institucion = null;
        if (StringUtils.isNotBlank(idFolioInstitucion)) {
            institucion = consultaCatService.findInstitucionByClaveFolio(idFolioInstitucion);
            if (institucion != null) {
                nombreInstitucion = institucion.getNombreCorto();
                param.setIdInstitucion(institucion.getIdInstitucion());
            } else {
                nombreInstitucion = null;
            }
        } else {
            nombreInstitucion = null;
        }
    }

    /**
     * Inicializa lista de custodios
     */
    private void inicializaMapaTiposBeneficiarios() {
        mapaTipoBeneficiario = new HashMap();
        List<Object[]> lista = controlBeneficiariosService.obtieneTiposBeneficiario();
        for (Object[] bene : lista) {
            mapaTipoBeneficiario.put((Long) bene[0], ((String) bene[1]).toUpperCase());
        }

    }

    /**
     * Servicio de Statements
     * @param statementsDivintService the statementsDivintService to set
     */
    public void setStatementsDivintService(StatementsDivintService statementsDivintService) {
        this.statementsDivintService = statementsDivintService;
    }

    /**
     * Servicio de Beneficiarios
     * @param controlBeneficiariosService the controlBeneficiariosService to set
     */
    public void setControlBeneficiariosService(ControlBeneficiariosService controlBeneficiariosService) {
        this.controlBeneficiariosService = controlBeneficiariosService;
    }

    /**
     * Lista de Tipos de Beneficiarios de acuerdo al custodio
     * @return the tiposBeneficiarios
     */
    public List getTiposBeneficiarios() {
        return tiposBeneficiarios;
    }

    /**
     * Lista de Tipos de Beneficiarios de acuerdo al custodio
     * @param tiposBeneficiarios the tiposBeneficiarios to set
     */
    public void setTiposBeneficiarios(List tiposBeneficiarios) {
        this.tiposBeneficiarios = tiposBeneficiarios;
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
     * Parametros para la consulta
     * @return the param
     */
    public ConsultaStatementsParam getParam() {
        return param;
    }

    /**
     * Parametros para la consulta
     * @param param the param to set
     */
    public void setParam(ConsultaStatementsParam param) {
        this.param = param;
    }

    /**
     * Institucion seleccionada
     * @return the idFolioInstitucion
     */
    public String getIdFolioInstitucion() {
        return idFolioInstitucion;
    }

    /**
     * Institucion seleccionada
     * @param idFolioInstitucion the idFolioInstitucion to set
     */
    public void setIdFolioInstitucion(String idFolioInstitucion) {
        this.idFolioInstitucion = idFolioInstitucion;
    }

    /**
     * Institucion seleccionada
     * @return the nombreInstitucion
     */
    public String getNombreInstitucion() {
        return nombreInstitucion;
    }

    /**
     * Institucion seleccionada
     * @param nombreInstitucion the nombreInstitucion to set
     */
    public void setNombreInstitucion(String nombreInstitucion) {
        this.nombreInstitucion = nombreInstitucion;
    }

    /**
     * Servicio para obtener la institucion
     * @param consultaCatService the consultaCatService to set
     */
    public void setConsultaCatService(ConsultaCatalogoService consultaCatService) {
        this.consultaCatService = consultaCatService;
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
     * Mapa que contiene la lista de custodios para la tabla
     * @return the mapaTipoBeneficiario
     */
    public Map getMapaTipoBeneficiario() {
        return mapaTipoBeneficiario;
    }

    /**
     * Mapa que contiene la lista de custodios para la tabla
     * @param mapaTipoBeneficiario the mapaTipoBeneficiario to set
     */
    public void setMapaTipoBeneficiario(Map mapaTipoBeneficiario) {
        this.mapaTipoBeneficiario = mapaTipoBeneficiario;
    }

    public Map<Long, DomiciliosInstituciones> getDomiciliosFiscales() {
        return domiciliosFiscales;
    }

    public void setDomiciliosFiscales(Map<Long, DomiciliosInstituciones> domiciliosFiscales) {
        this.domiciliosFiscales = domiciliosFiscales;
    }

	public List getListaCustodios() {
		return listaCustodios;
	}

	public void setListaCustodios(List listaCustodios) {
		this.listaCustodios = listaCustodios;
	}
    
    
}

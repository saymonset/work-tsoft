/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.presentation.controller.beneficiarios;

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
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService;
import com.indeval.portalinternacional.middleware.servicios.constantes.BeneficiariosConstantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.StatusBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaHistoricoBeneficiariosParam;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * Controller de la consulta historica de beneficiarios
 * @author Rafael Ibarra
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class ConsultaHistoricaBeneficiariosController extends ControllerBase {

    /** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(ConsultaHistoricaBeneficiariosController.class);
    /** Servicio de Beneficiarios */
    private ControlBeneficiariosService controlBeneficiariosService;
    /** Lista de Custodios */
    private List listaCustodios;
    /** Lista de Tipos de Beneficiarios de acuerdo al custodio */
    private List tiposBeneficiarios;
    /** Lista de Tipos de Beneficiarios de acuerdo al custodio */
    private List listaEstados;
    /** Indica si la consulta ya se ejecuto */
    private boolean consultaEjecutada;
    /** Parametros para la consulta */
    private ConsultaHistoricoBeneficiariosParam param = new ConsultaHistoricoBeneficiariosParam();
    /** Institucion seleccionada */
    private String idFolioInstitucion;
    /** Institucion seleccionada */
    private String nombreInstitucion;
    /** Servicio para obtener la institucion */
    private ConsultaCatalogoService consultaCatService;
    /** Mapa que contiene la lista de custodios para la tabla */
    private Map mapaCustodios;
    /** Pagina para los reportes*/
    private PaginaVO paginaReportes;
    /** Mapa con los datos del Tipo de USTaxPayer */
    private Map mapaTaxPayerIdNumer;
    /** Mapa que contiene la lista de custodios para la tabla */
    private Map mapaTipoBeneficiario;
    /** Mapa que contiene los estatus de los beneficiarios */
    private Map mapaStatusBenef;

	/** Lista con los diferentes formatos para los diferentes */
	private List<SelectItem> formatosBeneficiarios;
    
    /**
     * Constructor de consulta historica de beneficiarios
     */
    public ConsultaHistoricaBeneficiariosController() {
        super();

    }

    /**
     * Asigna las opciones predeterminadas para cuando se carga la página por
     * primerva vez.
     *
     * @return nulo, este método no requiere retornar un valor
     */
    public String getInit() {
        log.debug("Entrnado a ConsultaHistoricaBeneficiariosController.getInit");
        param = new ConsultaHistoricoBeneficiariosParam();
        param.setCustodio(null);
        param.setTipoBeneficiario(null);
        inicializaCustodios();
        inicializaTiposBeneficiarios();
        inicializaListaEstados();
        initListaFormatos();
        consultaEjecutada = false;
        paginaVO.setRegistrosXPag(20);
        if (!isInstitucionIndeval()) {
            idFolioInstitucion = getAgenteFirmado().getId() + getAgenteFirmado().getFolio();
            nombreInstitucion = getAgenteFirmado().getNombreCorto();
        } else {
            idFolioInstitucion = null;
            nombreInstitucion = null;
        }
        return null;
    }

    /** Busca los registros
     * @param ActionEvent event
     */
    public void buscar(ActionEvent event) {
    	try{
	    	if(param.getFechaRegistroInicio() != null && param.getFechaRegistroFin() != null) {
	            if(param.getFechaRegistroInicio().compareTo(param.getFechaRegistroFin()) > 0) {
	                log.debug("Es mayor inicio que fin.");
	                addErrorMessage("Fechas de Registro Incorrectas");
	                return;
	            } else {
	                 log.debug("Es correcto inicio que fin.");
	            }
	        }
	        ejecutarConsulta();
    	}catch(Exception e){
    		this.addErrorMessage("Un error no esperado ha ocurrido mientras se procesaba su solicitud. Si el error persiste, favor de comunicarse con el personal de soporte técnico.");
            return;
    	}
    }

    @Override
    public String ejecutarConsulta() {
        if (paginaVO != null && paginaVO.getRegistros() != null) {
            paginaVO.getRegistros().clear();
            paginaVO.setRegistros(null);
        }

        if(StringUtils.isNotBlank(idFolioInstitucion)) {
            Institucion ins = consultaCatService.findInstitucionByClaveFolio(idFolioInstitucion);
            if(ins != null) {
                param.setInstitucion(ins);
            } else {
                 addErrorMessage("Institucion invalida");
                return null;
            }
        } else {
            param.setInstitucion(null);
            if(!isInstitucionIndeval()) {
                addErrorMessage("Institucion requerida");
                return null;
            }
        }

        setPaginaVO(controlBeneficiariosService.consultaBeneficiariosHistorico(
                param, paginaVO));
        inicializaMapaCustodios();
        inicializaMapaTiposBeneficiarios();
        consultaEjecutada = true;
        return null;
    }

    public void generarReportes(ActionEvent event) {
        paginaReportes = new PaginaVO();
        paginaReportes.setRegistrosXPag(PaginaVO.TODOS);
        paginaReportes.setOffset(0);
        paginaReportes = controlBeneficiariosService.consultaBeneficiariosHistorico(param, paginaReportes);
        inicializaMapaCustodios();
        inicializaMapaTiposBeneficiarios();
        inicializaMapaStatusBenef();
        log.info("Tamaño de la lista: [" + paginaReportes.getRegistros().size() + "]");
    }

    public void limpiar(ActionEvent event) {
        param = new ConsultaHistoricoBeneficiariosParam();
        if (paginaVO != null) {
            if (paginaVO.getRegistros() != null) {
                paginaVO.getRegistros().clear();
                paginaVO.setRegistros(null);
            }
            paginaVO.setOffset(0);
            paginaVO.setTotalRegistros(0);
            paginaVO.setRegistrosXPag(20);
        }
        consultaEjecutada  = false;
        idFolioInstitucion = null;
        nombreInstitucion = null;
    }

    /**
     * Inicializa lista de custodios
     */
    private void inicializaCustodios() {
        List<Object[]> lista = controlBeneficiariosService.obtieneCatBic();
        listaCustodios = new ArrayList<SelectItem>();

        for (Object[] custodioActual : lista) {
            listaCustodios.add(new SelectItem((Long) custodioActual[0], (String) custodioActual[1]));
        }
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

    /**
     * Inicializa mapa de custodios
     */
    private void inicializaMapaCustodios() {
        mapaCustodios = new HashMap();

        List<Object[]> lista = controlBeneficiariosService.obtieneCatBic();

        for (Object[] bene : lista) {
            mapaCustodios.put((Long) bene[0], (String) bene[1]);
        }
    }

    /**
     * Inicializa lista de estados
     */
    private void inicializaListaEstados() {
        listaEstados = new ArrayList();

        StatusBeneficiario[] lista = controlBeneficiariosService.obtieneStatusBeneficiario();

        for (StatusBeneficiario bene : lista) {
            listaEstados.add(new SelectItem(bene.getIdStatusBenef(), bene.getDescStatusBenef()));
        }
    }

    /**
     * Inicializa lista de custodios
     */
    private void inicializaMapaTiposBeneficiarios() {
        mapaTipoBeneficiario = new HashMap();
        Long custodio = param.getCustodio();
        if (custodio != null && custodio > 0) {
            List<Object[]> lista = controlBeneficiariosService.obtieneTiposBeneficiario(custodio);
            for (Object[] bene : lista) {
                mapaTipoBeneficiario.put((Long) bene[0], ((String) bene[1]).toUpperCase());
            }
        }

    }

    /**
     * Inicializa lista de custodios
     */
    private void inicializaMapaStatusBenef() {
        mapaStatusBenef = new HashMap();
        StatusBeneficiario lista[] = controlBeneficiariosService.obtieneStatusBeneficiario();
        for (StatusBeneficiario status : lista) {
            mapaStatusBenef.put(status.getIdStatusBenef(), status.getDescStatusBenef().toUpperCase());
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
                param.setInstitucion(institucion);
            } else {
                nombreInstitucion = null;
            }
        } else {
            nombreInstitucion = null;
        }
    }

	/**
	 * Inicializa la lista de formatos
	 */
	private void initListaFormatos() {
		formatosBeneficiarios = new ArrayList<SelectItem>();
		formatosBeneficiarios.add(new SelectItem(BeneficiariosConstantes.FORMATO_W8_BEN, BeneficiariosConstantes.FORMATO_W8_BEN));
		formatosBeneficiarios.add(new SelectItem(BeneficiariosConstantes.FORMATO_W8_BEN_E, BeneficiariosConstantes.FORMATO_W8_BEN_E));
		formatosBeneficiarios.add(new SelectItem(BeneficiariosConstantes.FORMATO_W8_IMY, BeneficiariosConstantes.FORMATO_W8_IMY));
		formatosBeneficiarios.add(new SelectItem(BeneficiariosConstantes.FORMATO_W9, BeneficiariosConstantes.FORMATO_W9));
		formatosBeneficiarios.add(new SelectItem(BeneficiariosConstantes.FORMATO_MILA, BeneficiariosConstantes.FORMATO_MILA));
	}
    
    /**
     * Servicio de Beneficiarios
     * @return the controlBeneficiariosService
     */
    public ControlBeneficiariosService getControlBeneficiariosService() {
        return controlBeneficiariosService;
    }

    /**
     * Servicio de Beneficiarios
     * @param controlBeneficiariosService the controlBeneficiariosService to set
     */
    public void setControlBeneficiariosService(ControlBeneficiariosService controlBeneficiariosService) {
        this.controlBeneficiariosService = controlBeneficiariosService;
    }

    /**
     * Lista de Custodios
     * @return the listaCustodios
     */
    public List getListaCustodios() {
        return listaCustodios;
    }

    /**
     * Lista de Custodios
     * @param listaCustodios the listaCustodios to set
     */
    public void setListaCustodios(List listaCustodios) {
        this.listaCustodios = listaCustodios;
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
     * Lista de Tipos de Beneficiarios de acuerdo al custodio
     * @return the listaEstados
     */
    public List getListaEstados() {
        return listaEstados;
    }

    /**
     * Lista de Tipos de Beneficiarios de acuerdo al custodio
     * @param listaEstados the listaEstados to set
     */
    public void setListaEstados(List listaEstados) {
        this.listaEstados = listaEstados;
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
    public ConsultaHistoricoBeneficiariosParam getParam() {
        return param;
    }

    /**
     * Parametros para la consulta
     * @param param the param to set
     */
    public void setParam(ConsultaHistoricoBeneficiariosParam param) {
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
     * @return the consultaCatService
     */
    public ConsultaCatalogoService getConsultaCatService() {
        return consultaCatService;
    }

    /**
     * Servicio para obtener la institucion
     * @param consultaCatService the consultaCatService to set
     */
    public void setConsultaCatService(ConsultaCatalogoService consultaCatService) {
        this.consultaCatService = consultaCatService;
    }

    /**
     * Mapa que contiene la lista de custodios para la tabla
     * @return the mapaCustodios
     */
    public Map getMapaCustodios() {
        return mapaCustodios;
    }

    /**
     * Mapa que contiene la lista de custodios para la tabla
     * @param mapaCustodios the mapaCustodios to set
     */
    public void setMapaCustodios(Map mapaCustodios) {
        this.mapaCustodios = mapaCustodios;
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
     * Mapa con los datos del Tipo de USTaxPayer
     * @return the mapaTaxPayerIdNumer
     */
    public Map getMapaTaxPayerIdNumer() {
        return mapaTaxPayerIdNumer;
    }

    /**
     * Mapa con los datos del Tipo de USTaxPayer
     * @param mapaTaxPayerIdNumer the mapaTaxPayerIdNumer to set
     */
    public void setMapaTaxPayerIdNumer(Map mapaTaxPayerIdNumer) {
        this.mapaTaxPayerIdNumer = mapaTaxPayerIdNumer;
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

    /**
     * Mapa que contiene los estatus de los beneficiarios
     * @return the mapaStatusBenef
     */
    public Map getMapaStatusBenef() {
        return mapaStatusBenef;
    }

    /**
     * Mapa que contiene los estatus de los beneficiarios
     * @param mapaStatusBenef the mapaStatusBenef to set
     */
    public void setMapaStatusBenef(Map mapaStatusBenef) {
        this.mapaStatusBenef = mapaStatusBenef;
    }

	/**
	 * Método para obtener el atributo formatosBeneficiarios
	 * @return El atributo formatosBeneficiarios
	 */
	public List<SelectItem> getFormatosBeneficiarios() {
		return formatosBeneficiarios;
	}

	/**
	 * Método para establecer el atributo formatosBeneficiarios
	 * @param formatosBeneficiarios El valor del atributo formatosBeneficiarios a establecer.
	 */
	public void setFormatosBeneficiarios(List<SelectItem> formatosBeneficiarios) {
		this.formatosBeneficiarios = formatosBeneficiarios;
	}
}

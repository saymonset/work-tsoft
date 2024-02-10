/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.presentation.controller.beneficiarios;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.formatosw.FormaGeneral;
import com.indeval.portaldali.middleware.formatosw.FormaMILA;
import com.indeval.portaldali.middleware.formatosw.FormaW8BEN;
import com.indeval.portaldali.middleware.formatosw.FormaW8IMY;
import com.indeval.portaldali.middleware.formatosw.FormaW9;
import com.indeval.portaldali.middleware.services.util.ConsultaCatalogoService;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.FormatoW8Service;
import com.indeval.portalinternacional.middleware.servicios.constantes.BeneficiariosConstantes;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.vo.BeneficiariosPaginacionVO;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaBeneficiariosParam;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * Controller de la consulta de beneficiarios
 * 
 * @author Rafael Ibarra
 * @version 1.0
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class ConsultaBeneficiariosRFCController extends ControllerBase {

    /** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(ConsultaBeneficiariosRFCController.class);

    /** Tipos de tax payer id number */
    public static String tipoSSNITN = "SSN or ITIN";
    public static String tipoEIN = "EIN";
    public static String tipoQIEIN = "QI-EIN";
    public static int RANGO = 500;
    public static String ESTATUS_BENEFICIARIO_ELIMINADO = "ELIMINADO";
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
    private ConsultaBeneficiariosParam param = new ConsultaBeneficiariosParam();
    /** Institucion seleccionada */
    private String idFolioInstitucion;
    /** Institucion seleccionada */
    private String nombreInstitucion;
    /** Servicio para obtener la institucion */
    private ConsultaCatalogoService consultaCatService;
    /** Mapa que contiene la lista de custodios para la tabla */
    private Map mapaCustodios;
    /** Mapa con los porcentajes de retencion */
    private Map mapaPorcentajes;
    /** Pagina para los reportes */
    private PaginaVO paginaReportes;
    /** Mapa con los datos del Tipo de USTaxPayer */
    private Map mapaTaxPayerIdNumer;
    /** Mapa que contiene la lista de custodios para la tabla */
    private Map mapaTipoBeneficiario;
    /** Mapa que contiene los estatus de los beneficiarios */
    private Map mapaStatusBenef;
    /** Bandera que indica el inicio */
    private boolean banderaInicio = true;

    private boolean reportesDireccion = false;

    private String tipoReporte = "consultaBeneficiariosPDF";

    private int inicioRegistros = 0;

    private int finRegistros = 0;

    private List<BeneficiariosPaginacionVO> listaPaginasBenef =
            new ArrayList<BeneficiariosPaginacionVO>();

    private boolean ejecutarLetras = false;

    private String activo = Constantes.MENOS_UNO_STRING;

    private boolean exportaMila;

    /** Mapa que contiene la lista de documentos para la tabla */
    private Map<Long, String> mapaTipoDocumentos;

    /** Lista con los diferentes formatos para los diferentes */
    private List<SelectItem> formatosBeneficiarios;

    /** Servicio para parsear el formato a xml y viceversa */
    private FormatoW8Service formatoW8Service;
    
	/** Parametros enviados por el Request */
	private Map<String, String> params = null;


    /**
     * Constructor de consulta Operaciones
     */
    public ConsultaBeneficiariosRFCController() {
        super();
        this.mapaTaxPayerIdNumer = new HashMap();
        this.mapaTaxPayerIdNumer.put(1l, tipoSSNITN);
        this.mapaTaxPayerIdNumer.put(2l, tipoEIN);
        this.mapaTaxPayerIdNumer.put(3l, tipoQIEIN);
    }
    
    public String getInitModal() {
        log.info("Entrando a getInitModal");
		params = new HashMap<String, String>();
        Set<String> keys = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().keySet();
        
        for (String key : keys) {
            params.put(key, FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key));
        }
        
        FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        
        this.setConsultaEjecutada(false);
        this.paginaVO.setRegistrosXPag(20);
        this.banderaInicio = false;
        this.reportesDireccion = false;
        if (!this.isInstitucionIndeval()) {
            this.idFolioInstitucion =
                    this.getAgenteFirmado().getId() + this.getAgenteFirmado().getFolio();
            this.nombreInstitucion = this.getAgenteFirmado().getNombreCorto();
        } else {
            this.idFolioInstitucion = null;
            this.nombreInstitucion = null;
        }
        
        this.idFolioInstitucion = params.get("idFolioInstitucion");
        
        this.param = new ConsultaBeneficiariosParam();
        this.param.setCustodio(Long.parseLong(params.get("custodio")));
        this.param.setTipoBeneficiario(Long.parseLong(params.get("tipoBeneficiario")));
        this.param.setFormato(params.get("formato"));
        this.param.setStatusBenef(Long.parseLong(params.get("estatusBenef")));
        this.param.setUoiNumber(params.get("uoiNumber"));
        
        /** Se parsean fechas **/
	    String sDateRegistroInicio = params.get("fechaRegistroInicio");  
	    String sDateRegistroFin = params.get("fechaRegistroFin");  
	    
	    Date datesDateRegistroInicio = null;
	    Date datesDateRegistroFin = null;
	    if(StringUtils.isNotBlank(sDateRegistroInicio) && sDateRegistroInicio != null){
	    	try {
				datesDateRegistroInicio = new SimpleDateFormat("dd/MM/yyyy").parse(sDateRegistroInicio);
			} catch (ParseException e) {
				e.printStackTrace();
			}	    	
	    }
	    if(StringUtils.isNotBlank(sDateRegistroFin) && sDateRegistroFin != null){
	    	try {
				datesDateRegistroFin = new SimpleDateFormat("dd/MM/yyyy").parse(sDateRegistroFin);
			} catch (ParseException e) {
				e.printStackTrace();
			}	    	
	    }

        this.param.setFechaRegistroInicio(datesDateRegistroInicio);
        this.param.setFechaRegistroFin(datesDateRegistroFin);
        
	    String sDateFormatoInicio = params.get("fechaFormatoInicio");  
	    String sDateFormatoFin = params.get("fechaFormatoFin");
	    
	    Date datesDateFormatoInicio = null;
	    Date datesDateFormatoFin = null;
	    if(StringUtils.isNotBlank(sDateFormatoInicio) && sDateFormatoInicio != null){
	    	try {
				datesDateFormatoInicio = new SimpleDateFormat("dd/MM/yyyy").parse(sDateFormatoInicio);
			} catch (ParseException e) {
				e.printStackTrace();
			}	    	
	    }
	    if(StringUtils.isNotBlank(sDateFormatoFin) && sDateFormatoFin != null){
	    	try {
				datesDateFormatoFin = new SimpleDateFormat("dd/MM/yyyy").parse(sDateFormatoFin);
			} catch (ParseException e) {
				e.printStackTrace();
			}	    	
	    }
        
        this.param.setFechaFormatoInicio(datesDateFormatoInicio);
        this.param.setFechaFormatoFin(datesDateFormatoFin);
        
	    String sDateAutorizacionInicio = params.get("fechaAutorizacionInicio");  
	    String sDateAutorizacionFin = params.get("fechaAutorizacionFin");
	    
	    Date datesDateAutorizacionInicio = null;
	    Date datesDateAutorizacionFin = null;
	    if(StringUtils.isNotBlank(sDateAutorizacionInicio) && sDateAutorizacionInicio != null){
	    	try {
				datesDateAutorizacionInicio = new SimpleDateFormat("dd/MM/yyyy").parse(sDateAutorizacionInicio);
			} catch (ParseException e) {
				e.printStackTrace();
			}	    	
	    }
	    if(StringUtils.isNotBlank(sDateAutorizacionFin) && sDateAutorizacionFin != null){
	    	try {
				datesDateAutorizacionFin = new SimpleDateFormat("dd/MM/yyyy").parse(sDateAutorizacionFin);
			} catch (ParseException e) {
				e.printStackTrace();
			}	    	
	    }
        
        this.param.setFechaAutorizacionInicio(datesDateAutorizacionInicio);
        this.param.setFechaAutorizacionFin(datesDateAutorizacionFin);
        
        this.paginaVO.setOffset(0);
        log.debug("LLego a comparar las fechas......");
        if (this.param.getFechaRegistroInicio() != null && this.param.getFechaRegistroFin() != null) {
            if (this.param.getFechaRegistroInicio().compareTo(this.param.getFechaRegistroFin()) > 0) {
                log.debug("Es mayor inicio que fin.");
                this.addErrorMessage("Fechas de Registro Incorrectas");
                return null;
            } else {
                log.debug("Es correcto inicio que fin.");
            }
        }

        if (this.param.getFechaFormatoInicio() != null && this.param.getFechaFormatoFin() != null) {
            if (this.param.getFechaFormatoInicio().compareTo(this.param.getFechaFormatoFin()) > 0) {
                this.addErrorMessage("Fechas de Formato Incorrectas");
                return null;
            }
        }

        if (this.param.getFechaAutorizacionInicio() != null
                && this.param.getFechaAutorizacionFin() != null) {
            if (this.param.getFechaAutorizacionInicio().compareTo(
                    this.param.getFechaAutorizacionFin()) > 0) {
                this.addErrorMessage("Fechas de Autorizacion Incorrectas");
                return null;
            }
        }
        
        
        this.param.setNombreRazonSocial(params.get("nombreBenef"));
        this.param.setReferenceNumber(params.get("referenceNumber"));
        this.param.setAdp(params.get("adp"));
        

        if(params.get("activo") != null){
            if (Constantes.UNO_STRING.equals(params.get("activo"))) {
                this.param.setActivo(true);
            } else if (Constantes.CERO_STRING.equals(params.get("activo"))) {
                this.param.setActivo(false);
            }	
        } else {
            this.param.setActivo(null);
        }

        this.param.setRfc(params.get("rfc"));
        
        ejecutarConsulta();
		return null;
    }    
    
    /** Metodo para enlazar el evento de la vista **/
    public void buscarBeneficiarioRFC(final ActionEvent event) {

    }

    @Override
    public String ejecutarConsulta() {
        if (this.paginaVO != null && this.paginaVO.getRegistros() != null) {
            this.paginaVO.getRegistros().clear();
            this.paginaVO.setRegistros(null);
        }
        if (StringUtils.isNotBlank(this.idFolioInstitucion)) {
            Institucion ins =
                    this.consultaCatService.findInstitucionByClaveFolio(this.idFolioInstitucion);
            if (ins != null) {
                this.param.setInstitucion(ins);
            } else {
                this.addErrorMessage("Institucion invalida");
                return null;
            }
        } else {
            this.param.setInstitucion(null);
            if (!this.isInstitucionIndeval()) {
                this.addErrorMessage("Institucion requerida");
                return null;
            }
        }

        // Aqui siempre debe ir en verdadero la direccion de la consulta
        this.param.setTraeDireccion(false);
        this.param.setLetra(null);
        this.param.setPagina(null);
        
        log.debug("## param :: " + this.param.toString());
        
        this.setPaginaVO(this.controlBeneficiariosService.consultaBeneficiarios(this.param, this.paginaVO, Boolean.TRUE));
        log.debug("Cantidad de registros: [" + this.paginaVO.getRegistros().size() + "]");

        // Banderas para saber si se tiene que exportar la consulta al INT 100
        boolean contieneMila = false;
        this.exportaMila = false;
        if (this.paginaVO != null && this.paginaVO.getRegistros() != null
                && !this.paginaVO.getRegistros().isEmpty()) {

            List<FormaGeneral> list = new ArrayList<FormaGeneral>();
            FormaGeneral forma = null;
            for (Beneficiario ben : (List<Beneficiario>) this.paginaVO.getRegistros()) {
                if (BeneficiariosConstantes.FORMATO_W8_BEN.equals(ben.getTipoFormato())
                        || BeneficiariosConstantes.FORMATO_W8_BEN2014.equals(ben.getTipoFormato())
                        || BeneficiariosConstantes.FORMATO_W8_BEN2017.equals(ben.getTipoFormato())) {
                    forma = new FormaW8BEN();
                } else if (BeneficiariosConstantes.FORMATO_W8_IMY.equals(ben.getTipoFormato())) {
                    forma = new FormaW8IMY();
                } else if (BeneficiariosConstantes.FORMATO_W9.equals(ben.getTipoFormato())) {
                    forma = new FormaW9();
                } else if (BeneficiariosConstantes.FORMATO_MILA.equals(ben.getTipoFormato())) {
                    forma = new FormaMILA();
                    contieneMila = true;
                } else if (BeneficiariosConstantes.FORMATO_W8_BEN_E.equals(ben.getTipoFormato())
                        || BeneficiariosConstantes.FORMATO_W8_BEN_E_2016.equals(ben
                                .getTipoFormato())) {
                    forma =
                            this.formatoW8Service.obtenerCamposFormatoW8BENE(ben.getFormatoW8BENE()
                                    .getIdCamposFormatoW8bene());
                } else if (BeneficiariosConstantes.FORMATO_W8_IMY2015.equals(ben.getTipoFormato())) {
                    forma =
                            this.formatoW8Service.obtenerCamposFormatoW8IMY2015(ben
                                    .getFormatoW8IMY2015().getIdCamposFormato());
                } else if (BeneficiariosConstantes.FORMATO_W8_IMY2017.equals(ben.getTipoFormato())) {
                    forma =
                            this.formatoW8Service.obtenerCamposFormatoW8IMY2017(ben
                                    .getFormatoW8IMY2015().getIdCamposFormato());
                }
                
                // Construye la forma particular
                forma.construyeBean(ben, true);
                /** Agrega la vigencia de 3 anios al formato **/
    		    Calendar calendario = Calendar.getInstance();
    		    calendario.setTimeInMillis(ben.getFechaFormato().getTime());
    		    calendario.add(Calendar.YEAR, 3);
                forma.setFechaVigenciaFormato(calendario.getTime());
                
                list.add(forma);
                
                // Filtro solo los estatus: 
/*                if(forma.getDescStatusBeneficiario().equals(BeneficiariosConstantes.STATUS_BENEFICIARIO_AUTORIZADO)
                		|| forma.getDescStatusBeneficiario().equals(BeneficiariosConstantes.STATUS_BENEFICIARIO_VENCIDO)
                		|| forma.getDescStatusBeneficiario().equals(BeneficiariosConstantes.STATUS_BENEFICIARIO_CANCELADO)){
                    list.add(forma);
                } */
            }
            
            /* Consultar por paginacion */
            try {
                if (this.ejecutarLetras || this.listaPaginasBenef == null
                        || this.listaPaginasBenef.isEmpty()) {
                    this.listaPaginasBenef =
                            this.controlBeneficiariosService
                                    .consultaPaginasBeneficiarios(this.param, Boolean.TRUE);
                }
            } catch (Exception e) {
                log.debug("Ocurrio un error:",e.getCause());
            }
            
            this.paginaVO.getRegistros().clear();
            this.paginaVO.setRegistros(list);
        } else {
            this.paginaVO.setTotalRegistros(0);
            this.paginaVO.setRegistros(new ArrayList());
            this.listaPaginasBenef = new ArrayList<BeneficiariosPaginacionVO>();
        }
        this.inicializaMapaCustodios();
        this.inicializaMapaTiposBeneficiarios();
        this.setConsultaEjecutada(true);


        // Indicamos si la exportacion al INT se debe mostrar, solo aplica cuando se tiene
        // resultados de tipo mila y cuando se selecciona un solo custodio ya que el formato mila
        // es solo para un custodio
        if (this.param.getCustodio() != null && this.param.getCustodio() != -1 && contieneMila) {
            this.exportaMila = true;
        }
        return null;
    }
    
    public void obtenerOperaciones(final ActionEvent event) {
        this.ejecutarConsulta();
    }

    /**
     * Inicializa mapa de custodios
     */
    private void inicializaMapaCustodios() {
        this.mapaCustodios = new HashMap();

        List<Object[]> lista = this.controlBeneficiariosService.obtieneCatBic();

        for (Object[] bene : lista) {
            this.mapaCustodios.put(bene[0], bene[1]);
        }
    }

    /**
     * Inicializa lista de custodios
     */
    private void inicializaMapaTiposBeneficiarios() {
        this.mapaTipoBeneficiario = new HashMap();
        Long custodio = this.param.getCustodio();
        if (custodio != null && custodio > 0) {
            List<Object[]> lista =
                    this.controlBeneficiariosService.obtieneTiposBeneficiario(custodio);
            for (Object[] bene : lista) {
                this.mapaTipoBeneficiario.put(bene[0], ((String) bene[1]).toUpperCase());
            }
        }

    }

    /**
     * Obtiene los datos de la institucion relacionada
     * 
     * @param ActionEvent event
     */
    public void obtenerDatosParticipante(final ActionEvent event) {
        Institucion institucion = null;
        if (StringUtils.isNotBlank(this.idFolioInstitucion)) {
            institucion =
                    this.consultaCatService.findInstitucionByClaveFolio(this.idFolioInstitucion);
            if (institucion != null) {
                this.nombreInstitucion = institucion.getNombreCorto();
                this.param.setInstitucion(institucion);
            } else {
                this.nombreInstitucion = null;
            }
        } else {
            this.nombreInstitucion = null;
        }
    }
    
    public void generarReportes(final ActionEvent event) {
        this.reiniciarEstadoPeticion();
        this.paginaReportes = new PaginaVO();
        this.paginaReportes.setRegistrosXPag(PaginaVO.TODOS);
        this.paginaReportes.setOffset(0);
        
        this.param.setTraeDireccion(false);
        this.param.setLetra(null);
        this.param.setPagina(null);

        this.paginaReportes = this.controlBeneficiariosService.consultaBeneficiarios(this.param, this.paginaReportes, Boolean.TRUE);
        
        List<Beneficiario> lstBeneficiarios = new ArrayList<Beneficiario>();
        if (this.paginaReportes != null && this.paginaReportes.getRegistros() != null
                && !this.paginaReportes.getRegistros().isEmpty()) {
            for (Beneficiario ben : (List<Beneficiario>) this.paginaReportes.getRegistros()) {
                /** Agrega la vigencia de 3 anios al formato **/
    		    Calendar calendario = Calendar.getInstance();
    		    calendario.setTimeInMillis(ben.getFechaFormato().getTime());
    		    calendario.add(Calendar.YEAR, 3);
                ben.setFechaVigenciaFormato(calendario.getTime());
                lstBeneficiarios.add(ben);
            }
            this.paginaReportes.setRegistros(lstBeneficiarios);
        }
    }

    /**
     * @return the listaCustodios
     */
    public List getListaCustodios() {
        return this.listaCustodios;
    }

    /**
     * @param listaCustodios the listaCustodios to set
     */
    public void setListaCustodios(final List listaCustodios) {
        this.listaCustodios = listaCustodios;
    }

    /**
     * @param controlBeneficiariosService the controlBeneficiariosService to set
     */
    public void setControlBeneficiariosService(
            final ControlBeneficiariosService controlBeneficiariosService) {
        this.controlBeneficiariosService = controlBeneficiariosService;
    }

    /**
     * @return the tiposBeneficiarios
     */
    public List getTiposBeneficiarios() {
        return this.tiposBeneficiarios;
    }

    /**
     * @param tiposBeneficiarios the tiposBeneficiarios to set
     */
    public void setTiposBeneficiarios(final List tiposBeneficiarios) {
        this.tiposBeneficiarios = tiposBeneficiarios;
    }

    /**
     * @param consultaEjecutada the consultaEjecutada to set
     */
    public void setConsultaEjecutada(final boolean consultaEjecutada) {
        this.consultaEjecutada = consultaEjecutada;
    }

    /**
     * @return the consultaEjecutada
     */
    public boolean isConsultaEjecutada() {
        return this.consultaEjecutada;
    }

    /**
     * @param param the param to set
     */
    public void setParam(final ConsultaBeneficiariosParam param) {
        this.param = param;
    }

    /**
     * @return the param
     */
    public ConsultaBeneficiariosParam getParam() {
        return this.param;
    }

    /**
     * @return the idFolioInstitucion
     */
    public String getIdFolioInstitucion() {
        return this.idFolioInstitucion;
    }

    /**
     * @param idFolioInstitucion the idFolioInstitucion to set
     */
    public void setIdFolioInstitucion(final String idFolioInstitucion) {
        this.idFolioInstitucion = idFolioInstitucion;
    }

    /**
     * @return the nombreInstitucion
     */
    public String getNombreInstitucion() {
        return this.nombreInstitucion;
    }

    /**
     * @param nombreInstitucion the nombreInstitucion to set
     */
    public void setNombreInstitucion(final String nombreInstitucion) {
        this.nombreInstitucion = nombreInstitucion;
    }

    /**
     * @param consultaCatService the consultaCatService to set
     */
    public void setConsultaCatService(final ConsultaCatalogoService consultaCatService) {
        this.consultaCatService = consultaCatService;
    }

    /**
     * @return the listaEstados
     */
    public List getListaEstados() {
        return this.listaEstados;
    }

    /**
     * @param listaEstados the listaEstados to set
     */
    public void setListaEstados(final List listaEstados) {
        this.listaEstados = listaEstados;
    }

    /**
     * @return the mapaCustodios
     */
    public Map getMapaCustodios() {
        return this.mapaCustodios;
    }

    /**
     * @param mapaCustodios the mapaCustodios to set
     */
    public void setMapaCustodios(final Map mapaCustodios) {
        this.mapaCustodios = mapaCustodios;
    }

    /**
     * Pagina para los reportes
     * 
     * @return the paginaReportes
     */
    public PaginaVO getPaginaReportes() {
        return this.paginaReportes;
    }

    /**
     * Pagina para los reportes
     * 
     * @param paginaReportes the paginaReportes to set
     */
    public void setPaginaReportes(final PaginaVO paginaReportes) {
        this.paginaReportes = paginaReportes;
    }

    /**
     * Mapa con los datos del Tipo de USTaxPayer
     * 
     * @return the mapaTaxPayerIdNumer
     */
    public Map getMapaTaxPayerIdNumer() {
        return this.mapaTaxPayerIdNumer;
    }

    /**
     * Mapa con los datos del Tipo de USTaxPayer
     * 
     * @param mapaTaxPayerIdNumer the mapaTaxPayerIdNumer to set
     */
    public void setMapaTaxPayerIdNumer(final Map mapaTaxPayerIdNumer) {
        this.mapaTaxPayerIdNumer = mapaTaxPayerIdNumer;
    }

    /**
     * Mapa que contiene la lista de custodios para la tabla
     * 
     * @return the mapaTipoBeneficiario
     */
    public Map getMapaTipoBeneficiario() {
        return this.mapaTipoBeneficiario;
    }

    /**
     * Mapa que contiene la lista de custodios para la tabla
     * 
     * @param mapaTipoBeneficiario the mapaTipoBeneficiario to set
     */
    public void setMapaTipoBeneficiario(final Map mapaTipoBeneficiario) {
        this.mapaTipoBeneficiario = mapaTipoBeneficiario;
    }

    /**
     * Mapa que contiene los estatus de los beneficiarios
     * 
     * @return the mapaStatusBenef
     */
    public Map getMapaStatusBenef() {
        return this.mapaStatusBenef;
    }

    /**
     * Mapa que contiene los estatus de los beneficiarios
     * 
     * @param mapaStatusBenef the mapaStatusBenef to set
     */
    public void setMapaStatusBenef(final Map mapaStatusBenef) {
        this.mapaStatusBenef = mapaStatusBenef;
    }

    /**
     * Bandera que indica el inicio
     * 
     * @return the banderaInicio
     */
    public boolean isBanderaInicio() {
        return this.banderaInicio;
    }

    /**
     * Bandera que indica el inicio
     * 
     * @param banderaInicio the banderaInicio to set
     */
    public void setBanderaInicio(final boolean banderaInicio) {
        this.banderaInicio = banderaInicio;
    }

    /**
     * Mapa con los porcentajes de retencion
     * 
     * @return the mapaPorcentajes
     */
    public Map getMapaPorcentajes() {
        return this.mapaPorcentajes;
    }

    /**
     * Mapa con los porcentajes de retencion
     * 
     * @param mapaPorcentajes the mapaPorcentajes to set
     */
    public void setMapaPorcentajes(final Map mapaPorcentajes) {
        this.mapaPorcentajes = mapaPorcentajes;
    }

    public boolean isReportesDireccion() {
        return this.reportesDireccion;
    }

    public void setReportesDireccion(final boolean reportesDireccion) {
        this.reportesDireccion = reportesDireccion;
    }

    public List<BeneficiariosPaginacionVO> getListaPaginasBenef() {
        return this.listaPaginasBenef;
    }

    public void setListaPaginasBenef(final List<BeneficiariosPaginacionVO> listaPaginasBenef) {
        this.listaPaginasBenef = listaPaginasBenef;
    }

    public String getTipoReporte() {
        return this.tipoReporte;
    }

    public void setTipoReporte(final String tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    public int getInicioRegistros() {
        return this.inicioRegistros;
    }

    public void setInicioRegistros(final int inicioRegistros) {
        this.inicioRegistros = inicioRegistros;
    }

    public int getFinRegistros() {
        return this.finRegistros;
    }

    public void setFinRegistros(final int finRegistros) {
        this.finRegistros = finRegistros;
    }

    /**
     * @return the activo
     */
    public String getActivo() {
        return this.activo;
    }

    /**
     * @param activo the activo to set
     */
    public void setActivo(final String activo) {
        this.activo = activo;
    }

    /**
     * @return the exportaMila
     */
    public boolean isExportaMila() {
        return this.exportaMila;
    }

    /**
     * @return the mapaTipoDocumentos
     */
    public Map<Long, String> getMapaTipoDocumentos() {
        return this.mapaTipoDocumentos;
    }

    /**
     * Método para obtener el atributo formatosBeneficiarios
     * 
     * @return El atributo formatosBeneficiarios
     */
    public List<SelectItem> getFormatosBeneficiarios() {
        return this.formatosBeneficiarios;
    }

    /**
     * Método para establecer el atributo formatosBeneficiarios
     * 
     * @param formatosBeneficiarios El valor del atributo formatosBeneficiarios a establecer.
     */
    public void setFormatosBeneficiarios(final List<SelectItem> formatosBeneficiarios) {
        this.formatosBeneficiarios = formatosBeneficiarios;
    }

    /**
     * Método para establecer el atributo formatoW8Service
     * 
     * @param formatoW8Service El valor del atributo formatoW8Service a establecer.
     */
    public void setFormatoW8Service(final FormatoW8Service formatoW8Service) {
        this.formatoW8Service = formatoW8Service;
    }

	/**
	 * @return the params
	 */
	public Map<String, String> getParams() {
		return params;
	}

	/**
	 * @param params the params to set
	 */
	public void setParams(Map<String, String> params) {
		this.params = params;
	}
    
}

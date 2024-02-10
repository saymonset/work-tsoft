/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.presentation.controller.beneficiarios;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.indeval.portalinternacional.middleware.servicios.modelo.CustodioTipoBenef;
import com.indeval.portalinternacional.middleware.servicios.modelo.MILATipoDocumento;
import com.indeval.portalinternacional.middleware.servicios.modelo.StatusBeneficiario;
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
public class ConsultaBeneficiariosController extends ControllerBase {

    /** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(ConsultaBeneficiariosController.class);

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
    
    /** Bandera para mostrar botton **/
    private Boolean showButtonFormatoBene;


    /**
     * Constructor de consulta Operaciones
     */
    public ConsultaBeneficiariosController() {
        super();
        this.mapaTaxPayerIdNumer = new HashMap();
        this.mapaTaxPayerIdNumer.put(1l, tipoSSNITN);
        this.mapaTaxPayerIdNumer.put(2l, tipoEIN);
        this.mapaTaxPayerIdNumer.put(3l, tipoQIEIN);
    }

    /**
     * Asigna las opciones predeterminadas para cuando se carga la p�gina por primerva vez.
     * 
     * @return nulo, este m�todo no requiere retornar un valor
     */
    public String getInit() {
        log.debug("Entrnado a ConsultaBeneficiariosController.getInit");
        log.debug("Bandera Inicio: [" + this.banderaInicio + "]");
        if (this.banderaInicio) {
            this.param = new ConsultaBeneficiariosParam();
            this.param.setCustodio(null);
            this.param.setTipoBeneficiario(null);
            this.inicializaCustodios();
            this.inicializaTiposBeneficiarios();
            this.inicializaListaEstados();
            this.inicializaMapaPorcentajes();
            this.inicializaMapaTipoDocumentos();
            this.initListaFormatos();
            this.setConsultaEjecutada(false);
            this.paginaVO.setRegistrosXPag(20);
            this.banderaInicio = false;
            this.showButtonFormatoBene = true;
            this.reportesDireccion = false;
            if (!this.isInstitucionIndeval()) {
                this.idFolioInstitucion =
                        this.getAgenteFirmado().getId() + this.getAgenteFirmado().getFolio();
                this.nombreInstitucion = this.getAgenteFirmado().getNombreCorto();
            } else {
                this.idFolioInstitucion = null;
                this.nombreInstitucion = null;
            }
        }
        return null;
    }
    
    /**
     * Busca los registros
     * 
     * @param ActionEvent event
     */
    public void buscar(final ActionEvent event) {
        this.ejecutarLetras = true;
        this.paginaVO.setOffset(0);
        log.debug("LLego a comparar las fechas......");
        if (this.param.getFechaRegistroInicio() != null && this.param.getFechaRegistroFin() != null) {
            if (this.param.getFechaRegistroInicio().compareTo(this.param.getFechaRegistroFin()) > 0) {
                log.debug("Es mayor inicio que fin.");
                this.addErrorMessage("Fechas de Registro Incorrectas");
                return;
            } else {
                log.debug("Es correcto inicio que fin.");
            }
        }

        if (this.param.getFechaFormatoInicio() != null && this.param.getFechaFormatoFin() != null) {
            if (this.param.getFechaFormatoInicio().compareTo(this.param.getFechaFormatoFin()) > 0) {
                this.addErrorMessage("Fechas de Formato Incorrectas");
                return;
            }
        }

        if (this.param.getFechaAutorizacionInicio() != null
                && this.param.getFechaAutorizacionFin() != null) {
            if (this.param.getFechaAutorizacionInicio().compareTo(
                    this.param.getFechaAutorizacionFin()) > 0) {
                this.addErrorMessage("Fechas de Autorizacion Incorrectas");
                return;
            }
        }
        this.ejecutarConsulta();
        this.reportesDireccion = false;
        this.tipoReporte = "consultaBeneficiariosPDF";
        this.ejecutarLetras = false;
    }
    
        
    /**
     * Activa los beneficiarios selecionados
     * 
     * @param event
     */
    public void preAutorizaBeneficiario(final ActionEvent event) {
        int contador = 0;
        if (this.paginaVO != null && !this.paginaVO.getRegistros().isEmpty()) {
            List<FormaGeneral> lista = this.paginaVO.getRegistros();
            for (FormaGeneral elemento : lista) {
                if (elemento.isPreAutoriza()) {
                    this.controlBeneficiariosService.preAutorizaBeneficiario(elemento
                            .getIdBeneficiario());
                    contador++;
                }
            }
        }
        if (contador > 0) {
            this.addMessage("Beneficiario(s) Pre Autorizado(s) con \u00C9xito");
            this.buscar(event);
        } else {
            this.addErrorMessage("Ningun beneficiario seleccionado");
        }
    }

    /**
     * Activa los beneficiarios selecionados
     * 
     * @param event
     */
    public void activar(final ActionEvent event) {
        int contador = 0;
        if (this.paginaVO != null && !this.paginaVO.getRegistros().isEmpty()) {
            List<FormaGeneral> lista = this.paginaVO.getRegistros();
            for (FormaGeneral elemento : lista) {
                if (elemento.isActivar()) {
                    this.controlBeneficiariosService.activarBeneficiario(elemento
                            .getIdBeneficiario());
                    contador++;
                }
            }
        }
        if (contador > 0) {
            this.addMessage("Beneficiario(s) Autorizado(s) con \u00C9xito");
            this.buscar(event);
        } else {
            this.addErrorMessage("Ningun beneficiario seleccionado");
        }
    }

    /**
     * Elimina los beneficiarios selecionados
     * 
     * @param event
     */
    public void eliminar(final ActionEvent event) {
        int contador = 0;
        if (this.paginaVO != null && !this.paginaVO.getRegistros().isEmpty()) {
            List<FormaGeneral> lista = this.paginaVO.getRegistros();
            for (FormaGeneral elemento : lista) {
                if (elemento.isEliminar()) {
                    this.controlBeneficiariosService.eliminaBeneficiario(elemento
                            .getIdBeneficiario());
                    contador++;
                }
            }
        }
        if (contador > 0) {
            this.addMessage("Beneficiario(s) Eliminado(s) con \u00C9xito");
            this.buscar(event);
        } else {
            this.addErrorMessage("Ningun beneficiario seleccionado");
        }
    }

    public void eliminaInstitucion(final ActionEvent event) {
        log.debug("Entro a elimina");
        String idFolio = (String) this.getActionAttribute(event, "idFolio");
        long idBeneficiario = (Long) this.getActionAttribute(event, "idBeneficiario");
        long idInstitucion = 0l;
        log.debug("IdFolio: [" + idFolio + "]");


        if (StringUtils.isNotBlank(idFolio)) {
            idFolio = StringUtils.substring(idFolio, 0, 5);
            Institucion ins = this.consultaCatService.findInstitucionByClaveFolio(idFolio);
            if (ins != null) {
                idInstitucion = ins.getIdInstitucion();
            }
        }
        log.debug("Parametros: [" + idInstitucion + " - " + idBeneficiario + "]");
        if (idBeneficiario > 0 && idInstitucion > 0) {
            this.controlBeneficiariosService.eliminaInstitucionBeneficiario(idBeneficiario,
                    idInstitucion);
            this.addMessage("Institucion " + idFolio + " desasignada con exito");
            this.buscar(event);
        } else {
            this.addErrorMessage("La institucion y el beneficiario son campos requeridos");
        }
    }

    /**
     * Cancela los beneficiarios selecionados
     * 
     * @param event
     */
    public void cancelar(final ActionEvent event) {
        int contador = 0;
        if (this.paginaVO != null && !this.paginaVO.getRegistros().isEmpty()) {
            List<FormaGeneral> lista = this.paginaVO.getRegistros();
            for (FormaGeneral elemento : lista) {
                if (elemento.isCancelar()) {
                    this.controlBeneficiariosService.cancelaBeneficiario(elemento
                            .getIdBeneficiario());
                    contador++;
                }
            }
        }
        if (contador > 0) {
            this.addMessage("Beneficiario(s) Cancelado(s) con \u00C9xito");
            this.buscar(event);
        } else {
            this.addMessage("Ningun beneficiario seleccionado");
        }
    }

    public void obtenerOperaciones(final ActionEvent event) {
        this.ejecutarConsulta();
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

        if (Constantes.UNO_STRING.equals(this.activo)) {
            this.param.setActivo(true);
        } else if (Constantes.CERO_STRING.equals(this.activo)) {
            this.param.setActivo(false);
        } else {
            this.param.setActivo(null);
        }

        // Aqui siempre debe ir en verdadero la direccion de la consulta
        this.param.setTraeDireccion(true);
        this.param.setLetra(null);
        this.param.setPagina(null);
        this.setPaginaVO(this.controlBeneficiariosService.consultaBeneficiarios(this.param,
                this.paginaVO, Boolean.FALSE));
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
/*
            	if (ben.getInstitucion() != null && !ben.getInstitucion().isEmpty() && ben.getStatusBenef().getDescStatusBenef().equals("AUTORIZADO")) {
                	List<String> listInstitucion = new ArrayList<String>();
	                for (Institucion inst : ben.getInstitucion()) {
	                	Boolean isCobro = Boolean.FALSE;
	                	BeneficiarioInstitucion benefInstitucion = this.controlBeneficiariosService.getBeneficiarioInstitucion(ben.getIdBeneficiario(), inst.getIdInstitucion());
	                	 if (benefInstitucion.getGeneraCobro() != null){
	                		 isCobro = benefInstitucion.getGeneraCobro().booleanValue();
	                	 }
	                	 if(isCobro)
	                		 listInstitucion.add(inst.getClaveInstitucion() + " - " + inst.getNombreCorto() + " / Si");
	                	 else
	                		 listInstitucion.add(inst.getClaveInstitucion() + " - " + inst.getNombreCorto() + " / No");
	                }
	                forma.setInstitucion(listInstitucion);
            	}
*/
                list.add(forma);
            }
            /* Consultar por paginacion */
            try {
                if (this.ejecutarLetras || this.listaPaginasBenef == null
                        || this.listaPaginasBenef.isEmpty()) {
                    this.listaPaginasBenef =
                            this.controlBeneficiariosService
                                    .consultaPaginasBeneficiarios(this.param, Boolean.FALSE);
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

    public void generarReportes(final ActionEvent event) {
        this.reiniciarEstadoPeticion();
        this.paginaReportes = new PaginaVO();
        this.paginaReportes.setRegistrosXPag(PaginaVO.TODOS);
        this.paginaReportes.setOffset(0);

        this.param.setTraeDireccion(false);
        this.param.setLetra(null);
        this.param.setPagina(null);

        this.paginaReportes =
                this.controlBeneficiariosService.consultaBeneficiarios(this.param,
                        this.paginaReportes, Boolean.FALSE);

        this.inicializaMapaCustodios();
        this.inicializaMapaTiposBeneficiarios();
        this.inicializaMapaStatusBenef();
    }

    public void generarReporteMila() {
        this.paginaReportes = new PaginaVO();
        this.paginaReportes.setRegistrosXPag(PaginaVO.TODOS);
        this.paginaReportes.setOffset(0);

        this.param.setTraeDireccion(true);
        this.param.setLetra(null);
        this.param.setPagina(0);

        this.paginaReportes =
                this.controlBeneficiariosService.consultaBeneficiarios(this.param,
                        this.paginaReportes, Boolean.FALSE);
        try {
            FacesContext ctx = FacesContext.getCurrentInstance();
            ExternalContext ectx = ctx.getExternalContext();
            HttpServletRequest request = (HttpServletRequest) ectx.getRequest();
            HttpServletResponse response = (HttpServletResponse) ectx.getResponse();
            request.setAttribute("beneficiarios", this.paginaReportes.getRegistros());
            RequestDispatcher dispatcher =
                    request.getRequestDispatcher("ObtieneFormatoMILA?funcion=generarFormatoMilaCustodio");
            dispatcher.forward(request, response);
            ctx.responseComplete();
        } catch (ServletException ex) {
            log.equals(ex);
        } catch (IOException ex) {
            log.equals(ex);
        }
    }

    public String generarReportesBloque() {
        Map<String, String> parameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        String letra = parameters.get("daliForm:letraHiden");
        Integer pagina = Integer.valueOf(parameters.get("daliForm:paginaHiden"));

        this.paginaReportes = new PaginaVO();
        this.paginaReportes.setRegistrosXPag(PaginaVO.TODOS);
        this.paginaReportes.setOffset(0);

        this.param.setTraeDireccion(true);
        this.param.setLetra(letra);
        this.param.setPagina(pagina);

        // se ejecuta la consulta
        this.paginaReportes =
                this.controlBeneficiariosService.consultaBeneficiarios(this.param,
                        this.paginaReportes,Boolean.FALSE);

        // obtiene el numero de archivo que se esta obteniendo
        this.inicioRegistros = 0;
        this.finRegistros = 0;
        for (BeneficiariosPaginacionVO benefElement : this.listaPaginasBenef) {
            if (benefElement.getLetra().equalsIgnoreCase(this.param.getLetra())) {
                // se sacan los resultados a mostrar
                this.inicioRegistros = 1;
                this.finRegistros = this.paginaReportes.getRegistros().size();

                if (pagina != null && pagina > 0) {// offset
                    this.inicioRegistros = pagina * RANGO + 1;
                }
                if (this.finRegistros < RANGO && pagina > 0) {
                    this.finRegistros = this.finRegistros + this.inicioRegistros - 1;// se quita el
                                                                                     // num inicial
                                                                                     // que se sumo
                } else if (this.finRegistros == RANGO) {
                    this.finRegistros = RANGO * (pagina + 1);
                }

                break;
            }
        }// end for

        this.inicializaMapaCustodios();
        this.inicializaMapaTiposBeneficiarios();
        this.inicializaMapaStatusBenef();
        return this.tipoReporte;
    }

    public String navegaReportes() {
        String navegacion = "consultaBeneficiariosXLS";
        if (StringUtils.isNotBlank(this.param.getFormato())) {
            if (BeneficiariosConstantes.FORMATO_W8_BEN.equalsIgnoreCase(this.param.getFormato())
                    || BeneficiariosConstantes.FORMATO_W8_BEN2014.equalsIgnoreCase(this.param
                            .getFormato())
                    || BeneficiariosConstantes.FORMATO_W8_BEN2017.equalsIgnoreCase(this.param
                            .getFormato())) {
                navegacion = "consultaBeneficiariosXLSW8BEN";
            } else if (BeneficiariosConstantes.FORMATO_W8_IMY.equalsIgnoreCase(this.param
                    .getFormato())) {
                navegacion = "consultaBeneficiariosXLSW8IMY";
            } else if (BeneficiariosConstantes.FORMATO_W9.equalsIgnoreCase(this.param.getFormato())) {
                navegacion = "consultaBeneficiariosXLSW9";
            }
        }
        return navegacion;
    }

    public String limpiar() {
        this.param = new ConsultaBeneficiariosParam();
        if (this.paginaVO != null) {
            if (this.paginaVO.getRegistros() != null) {
                this.paginaVO.getRegistros().clear();
                this.paginaVO.setRegistros(null);
            }
            this.paginaVO.setOffset(0);
            this.paginaVO.setTotalRegistros(0);
            this.paginaVO.setRegistrosXPag(20);
        }
        this.setConsultaEjecutada(false);
        this.idFolioInstitucion = null;
        this.nombreInstitucion = null;
        this.banderaInicio = true;
        this.tipoReporte = "consultaBeneficiariosPDF";
        this.activo = "-1";
        return "consultaBeneficiarios";
    }

    /**
     * Inicializa lista de custodios
     */
    private void inicializaCustodios() {
        List<Object[]> lista = this.controlBeneficiariosService.obtieneCatBic();
        this.listaCustodios = new ArrayList<SelectItem>();

        for (Object[] custodioActual : lista) {
            this.listaCustodios.add(new SelectItem((Long) custodioActual[0],
                    (String) custodioActual[1]));
        }
    }

    /**
     * Inicializa lista de custodios
     */
    private void inicializaTiposBeneficiarios() {
        this.tiposBeneficiarios = new ArrayList();

        List<Object[]> lista = this.controlBeneficiariosService.obtieneTiposBeneficiario();

        for (Object[] bene : lista) {
            this.tiposBeneficiarios.add(new SelectItem((Long) bene[0], (String) bene[1]));
        }
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
     * Inicializa lista de estados
     */
    private void inicializaListaEstados() {
        this.listaEstados = new ArrayList();

        StatusBeneficiario[] lista = this.controlBeneficiariosService.obtieneStatusBeneficiario();

        for (StatusBeneficiario bene : lista) {
            if (!ESTATUS_BENEFICIARIO_ELIMINADO.equals(bene.getDescStatusBenef())) {
                this.listaEstados.add(new SelectItem(bene.getIdStatusBenef(), bene
                        .getDescStatusBenef()));
            }
        }
    }

    /**
     * Inicializa la lista de formatos
     */
    private void initListaFormatos() {
        this.formatosBeneficiarios = new ArrayList<SelectItem>();
        this.formatosBeneficiarios.add(new SelectItem(BeneficiariosConstantes.FORMATO_W8_BEN,
                BeneficiariosConstantes.FORMATO_W8_BEN));
        this.formatosBeneficiarios.add(new SelectItem(BeneficiariosConstantes.FORMATO_W8_BEN_E,
                BeneficiariosConstantes.FORMATO_W8_BEN_E));
        this.formatosBeneficiarios.add(new SelectItem(BeneficiariosConstantes.FORMATO_W8_IMY,
                BeneficiariosConstantes.FORMATO_W8_IMY));
        this.formatosBeneficiarios.add(new SelectItem(BeneficiariosConstantes.FORMATO_W9,
                BeneficiariosConstantes.FORMATO_W9));
        this.formatosBeneficiarios.add(new SelectItem(BeneficiariosConstantes.FORMATO_MILA,
                BeneficiariosConstantes.FORMATO_MILA));
    }

    /**
     * Inicializa el mapa de Porcentajes
     */
    private void inicializaMapaPorcentajes() {
        this.mapaPorcentajes = new HashMap();

        List<CustodioTipoBenef> listado =
                this.controlBeneficiariosService.getListaCustodioTipoBenef();

        Map subMapa = null;
        for (CustodioTipoBenef ctb : listado) {
            if (!this.mapaPorcentajes.containsKey(ctb.getIdCuentaNombrada())) {
                subMapa = new HashMap();
                this.mapaPorcentajes.put(ctb.getIdCuentaNombrada(), subMapa);
            }

            Map subMapaActual = (Map) this.mapaPorcentajes.get(ctb.getIdCuentaNombrada());
            subMapaActual.put(ctb.getTipoBeneficiario().getIdTipoBeneficiario(),
                    ctb.getPorcentajeRetencion());
        }

        // Set<Long> llaves = mapaPorcentajes.keySet();
        // for(Long llaveP : llaves) {
        // log.info("Llave Principal: [" + llaveP + "]");
        // Map mapaSec = (Map)mapaPorcentajes.get(llaveP);
        // Set<Long> llavesSec = mapaSec.keySet();
        // for(Long llaveS : llavesSec) {
        // log.info("LLave Secundaria: [" + llaveS + "]");
        // log.info("Valor: [" + mapaSec.get(llaveS) + "]");
        // }
        // }

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
     * Inicializa lista de custodios
     */
    private void inicializaMapaStatusBenef() {
        this.mapaStatusBenef = new HashMap();
        StatusBeneficiario lista[] = this.controlBeneficiariosService.obtieneStatusBeneficiario();
        for (StatusBeneficiario status : lista) {
            this.mapaStatusBenef.put(status.getIdStatusBenef(), status.getDescStatusBenef()
                    .toUpperCase());
        }

    }

    /**
     * Inicializa mapa de tipo de documentos
     */
    private void inicializaMapaTipoDocumentos() {
        List<MILATipoDocumento> tiposDocumentos =
                this.controlBeneficiariosService.consultaCatMilaTipoDocumento();
        this.mapaTipoDocumentos = new HashMap<Long, String>(tiposDocumentos.size());
        for (MILATipoDocumento tipoDocumento : tiposDocumentos) {
            this.mapaTipoDocumentos.put(tipoDocumento.getIdTipoDocumento().longValue(),
                    tipoDocumento.getDescripcionIndeval().toUpperCase());

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
    
	public void showButton(ActionEvent event) {
		if(this.param.getFormato().equals("W8BEN") || this.param.getFormato().equals("W8BENE")) {
			this.showButtonFormatoBene = Boolean.FALSE;
		} else {
			this.showButtonFormatoBene = Boolean.TRUE;
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
	 * @return the showButtonFormatoBene
	 */
	public Boolean getShowButtonFormatoBene() {
		return showButtonFormatoBene;
	}

	/**
	 * @param showButtonFormatoBene the showButtonFormatoBene to set
	 */
	public void setShowButtonFormatoBene(Boolean showButtonFormatoBene) {
		this.showButtonFormatoBene = showButtonFormatoBene;
	}
    
}

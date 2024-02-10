/**
 * 2H Software - Bursatec - INDEVAL Portal DALI
 * 
 * Jul 2, 2008
 */
package com.indeval.portalinternacional.presentation.controller.beneficiarios;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.formatosw.FormaGeneral;
import com.indeval.portaldali.middleware.formatosw.FormaW8BEN;
import com.indeval.portaldali.middleware.formatosw.FormaW8BENE;
import com.indeval.portaldali.middleware.formatosw.FormaW8IMY;
import com.indeval.portaldali.middleware.formatosw.FormaW8IMY2015;
import com.indeval.portaldali.middleware.formatosw.FormaW8IMY2017;
import com.indeval.portaldali.middleware.formatosw.FormaW9;
import com.indeval.portaldali.middleware.services.util.ConsultaCatalogoService;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.FormatoW8Service;
import com.indeval.portalinternacional.middleware.services.util.W8BENEUtil;
import com.indeval.portalinternacional.middleware.services.util.W8IMY2015Util;
import com.indeval.portalinternacional.middleware.services.util.W8IMY2017Util;
import com.indeval.portalinternacional.middleware.servicios.constantes.BeneficiariosConstantes;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.Domicilio;
import com.indeval.portalinternacional.middleware.servicios.modelo.ExemptPayeeW9;
import com.indeval.portalinternacional.middleware.servicios.modelo.ExemptionFatcaW9;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoW8BENE;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoW8IMY2015;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;
import com.indeval.portalinternacional.presentation.controller.seguridad.MensajeInternacionalBean;

/**
 * Controller para la Captura de Beneficiarios de Division Internacional
 * 
 * @author Rafael Ibarra
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class CapturaBeneficiariosController extends ControllerBase {
    private final DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    /** Log de clase */
    private static final Logger log = LoggerFactory.getLogger(CapturaBeneficiariosController.class);

    /** Servicio de Beneficiarios */
    private ControlBeneficiariosService controlBeneficiariosService;

    /** Servicio para parsear el formato a xml y viceversa */
    private FormatoW8Service formatoW8Service;

    /** Lista de Custodios */
    private List<SelectItem> listaCustodios;
    /** Custodio Seleccionado */
    private Long custodio;
    /** Lista de Tipos de Beneficiarios de acuerdo al custodio */
    private List<SelectItem> tiposBeneficiarios;
    /** TipoBeneficiario Seleccionado */
    private Long tipoBeneficiario;
    /** Formarto de acuerdo a custodio y a tipo beneficiario */
    private String formato;
    /** Forma W8BEN para la pantalla */
    private FormaGeneral forma;
    /** Institucion seleccionada */
    private String idFolioInstitucion;
    /** Institucion seleccionada */
    private String nombreInstitucion;
    /** Servicio para obtener la institucion */
    private ConsultaCatalogoService consultaCatService;
    /** Dice si hay beneficiarios existentes al momento de guardar */
    private boolean beneficiariosExistentes = false;
    /** Indica si debe bsucar beneficiarios existentes */
    private boolean flagBuscarBeneficiariosExistentes = true;
    /** Indica la navegacio despues de guardar */
    private String navegacion;
    /** Mapa que contiene la lista de custodios para la tabla */
    private Map<Long, String> mapaCustodios;
    /** Mapa que contiene la lista de custodios para la tabla */
    private Map<Long, String> mapaTipoBeneficiario;
    /** Bandera que indica si se guardo con exito el registro */
    private boolean exitoGuardar;

    private boolean guardaJPMorgan = false;
    private boolean guardaDeutsche = false;
    private boolean guardaBony = false;
    private boolean guardaBofa = false;
    private boolean bloqueoGuardaJPMorgan = false;
    private boolean bloqueoGuardaDeutsche = false;
    private boolean bloqueoGuardaBony = false;
    private boolean bloqueoGuardaBofa = false;

    /** Listas para el manejo de los combos */
    private List<SelectItem> selectExemptPayeeW9;
    private List<SelectItem> selectExemptionFatcaW9;

    /** Indica si esta deshabilitado el domicilio de correo */
    private boolean deshabilitaDomicilioCorreo;

    /** Objeto para capturar el domicilio del formato */
    private Domicilio domicilio;

    /** Objeto para capturar el domicilio de correo */
    private Domicilio domicilioCorreo;

    /** Apendice parte 4 W8IMY 2015 */
    private String apendiceParte4W8IMY2015;
    /** Apendice parte 4 W8IMY 2017 */
    private String apendiceParte4W8IMY2017;

    /**
     * Constructor de Captura Operaciones
     */
    public CapturaBeneficiariosController() {

    }

    /**
     * Asigna las opciones predeterminadas para cuando se carga la pï¿½gina por primerva vez.
     * 
     * @return nulo, este mï¿½todo no requiere retornar un valor
     */
    public String getInit() {
        this.beneficiariosExistentes = false;
        this.guardaJPMorgan = false;
        this.guardaDeutsche = false;
        this.guardaBony = false;
        this.guardaBofa = false;
        this.bloqueoGuardaJPMorgan = false;
        this.bloqueoGuardaDeutsche = false;
        this.bloqueoGuardaBony = false;
        this.bloqueoGuardaBofa = false;
        this.flagBuscarBeneficiariosExistentes = true;
        this.custodio = null;
        this.tipoBeneficiario = null;
        this.idFolioInstitucion = null;
        this.inicializaCustodios();
        this.inicializaTiposBeneficiarios();
        if (!this.isInstitucionIndeval()) {
            AgenteVO agente = this.getAgenteFirmado();
            this.idFolioInstitucion = agente.getId() + agente.getFolio();
            this.nombreInstitucion = agente.getNombreCorto();
        }
        this.dateFormat.setLenient(false);
        // Inicializa los objetos de domicilio
        this.domicilio = new Domicilio();
        this.domicilioCorreo = new Domicilio();
        return null;
    }

    public void cambiaCustodio(final ActionEvent event) {
        this.inicializaTiposBeneficiarios();
        if (this.custodio != null && this.custodio > 0) {
            if (this.custodio.equals(Constantes.CN_JP_MORGAN)) {
                this.guardaJPMorgan = true;
                this.guardaDeutsche = false;
                this.guardaBony = false;
                this.guardaBofa = false;
                this.bloqueoGuardaJPMorgan = true;
                this.bloqueoGuardaDeutsche = false;
                this.bloqueoGuardaBony = false;
                this.bloqueoGuardaBofa = false;
            } else if (this.custodio.equals(Constantes.CN_DEUSTCHE_BANK)) {
                this.guardaJPMorgan = false;
                this.guardaDeutsche = true;
                this.guardaBony = false;
                this.guardaBofa = false;
                this.bloqueoGuardaJPMorgan = false;
                this.bloqueoGuardaDeutsche = true;
                this.bloqueoGuardaBony = false;
                this.bloqueoGuardaBofa = false;
            } else if (this.custodio.equals(Constantes.CN_THE_BANK_OF_NEW_YORK)) {
                this.guardaJPMorgan = false;
                this.guardaDeutsche = false;
                this.guardaBony = true;
                this.guardaBofa = false;
                this.bloqueoGuardaJPMorgan = false;
                this.bloqueoGuardaDeutsche = false;
                this.bloqueoGuardaBony = true;
                this.bloqueoGuardaBofa = false;
            } else if (this.custodio.equals(Constantes.CN_BANK_OF_AMERICA)) {
                this.guardaJPMorgan = false;
                this.guardaDeutsche = false;
                this.guardaBony = false;
                this.guardaBofa = true;
                this.bloqueoGuardaJPMorgan = false;
                this.bloqueoGuardaDeutsche = false;
                this.bloqueoGuardaBony = false;
                this.bloqueoGuardaBofa = true;
            }
        }
    }

    public void limpiar(final ActionEvent event) {
        this.custodio = -1l;
        this.tipoBeneficiario = -1l;
        this.formato = null;
        this.forma = null;
        this.idFolioInstitucion = null;
        this.nombreInstitucion = null;
        this.beneficiariosExistentes = false;
        this.flagBuscarBeneficiariosExistentes = true;
    }

    public String guardar() {
        this.navegacion = null;
        try {
            Institucion institucion = null;
            this.exitoGuardar = false;
            if (StringUtils.isNotBlank(this.idFolioInstitucion)) {
                institucion =
                        this.consultaCatService
                                .findInstitucionByClaveFolio(this.idFolioInstitucion);
                if (institucion != null) {
                    this.beneficiariosExistentes = false;
                    if (this.flagBuscarBeneficiariosExistentes) {
                        // beneficiariosExistentes = buscarBeneficiariosExistentes();
                        this.beneficiariosExistentes = false;
                    }
                    if (!this.beneficiariosExistentes) {
                        this.flagBuscarBeneficiariosExistentes = true;
                        if (!this.isDebeMostrarGaurdar3Formatos()) {
                            // Genera el beneficiario
                            Beneficiario beneficiario = this.forma.construyeBO();
                            this.prepararFormatoW8(beneficiario);
                            // Guarda el registro en la base de datos
                            try {
                                this.controlBeneficiariosService.insertaBeneficiario(beneficiario,
                                        institucion.getIdInstitucion());	
                            } catch(Exception ex){
                                FacesContext.getCurrentInstance().addMessage(null,
                                		new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                                				ex.getMessage() + " - " + ex.getCause(), ex.getMessage() + " - " + ex.getCause()));
                                return null;
                            }
                        } else {
                            if (this.guardaJPMorgan) {
                                this.forma.setCustodio(Constantes.CN_JP_MORGAN);
                                // Genera el beneficiario
                                Beneficiario beneficiario = this.forma.construyeBO();
                                this.prepararFormatoW8(beneficiario);
                                try {
                                    this.controlBeneficiariosService.insertaBeneficiario(beneficiario,
                                            institucion.getIdInstitucion());	
                                } catch(Exception ex){
                                    FacesContext.getCurrentInstance().addMessage(null,
                                    		new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                                    				ex.getMessage() + " - " + ex.getCause(), ex.getMessage() + " - " + ex.getCause()));
                                    return null;
                                }
                            }
                            if (this.guardaDeutsche) {
                                this.forma.setCustodio(Constantes.CN_DEUSTCHE_BANK);
                                // Genera el beneficiario
                                Beneficiario beneficiario = this.forma.construyeBO();
                                this.prepararFormatoW8(beneficiario);
                                try {
                                    this.controlBeneficiariosService.insertaBeneficiario(beneficiario,
                                            institucion.getIdInstitucion());	
                                } catch(Exception ex){
                                    FacesContext.getCurrentInstance().addMessage(null,
                                    		new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                                    				ex.getMessage() + " - " + ex.getCause(), ex.getMessage() + " - " + ex.getCause()));
                                    return null;
                                }
                            }
                            if (this.guardaBony) {
                                this.forma.setCustodio(Constantes.CN_THE_BANK_OF_NEW_YORK);
                                // Genera el beneficiario
                                Beneficiario beneficiario = this.forma.construyeBO();
                                this.prepararFormatoW8(beneficiario);
                                try {
                                    this.controlBeneficiariosService.insertaBeneficiario(beneficiario,
                                            institucion.getIdInstitucion());	
                                } catch(Exception ex){
                                    FacesContext.getCurrentInstance().addMessage(null,
                                    		new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                                    				ex.getMessage() + " - " + ex.getCause(), ex.getMessage() + " - " + ex.getCause()));
                                    return null;
                                }
                            }
                            if (this.guardaBofa) {
                                this.forma.setCustodio(Constantes.CN_BANK_OF_AMERICA);
                                // Genera el beneficiario
                                Beneficiario beneficiario = this.forma.construyeBO();
                                this.prepararFormatoW8(beneficiario);
                                try {
                                    this.controlBeneficiariosService.insertaBeneficiario(beneficiario,
                                            institucion.getIdInstitucion());	
                                } catch(Exception ex){
                                	log.debug("### ex.getCause() " + ex.getCause());
                                	log.debug("### ex.getMessage() " + ex.getMessage());
                                    FacesContext.getCurrentInstance().addMessage(null,
                                    		new FacesMessage(ex.getMessage()));
                                    return null;
                                }
                            }
                        }
                        this.navegacion = "regresaCapturaBeneficiarios";
                    } else {
                        this.flagBuscarBeneficiariosExistentes = true;
                        log.info("Se encontraron beneficiarios existentes");
                        return null;
                    }
                } else {
                    String message = "La Institucion no existe";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, message, message));
                    return null;
                }
            } else {
                String message = "Institucion invalida";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, message, message));
                return null;
            }
            String message = "Beneficiario guardado con exito";
            MensajeInternacionalBean mensajeInternacionalBean =
                    (MensajeInternacionalBean) this.getBean("mensajeInternacionalBean");
            if (mensajeInternacionalBean != null) {
                mensajeInternacionalBean.setMensajeUsuario(message);
            } else {
                throw new Exception("No se pudo obtener el bean elegirInstitucionBean");
            }
            this.limpiar(null);
            this.exitoGuardar = true;
            return "capturaBeneficiariosRedirect";
        } catch (EJBException ejbException) {
            log.error("ERROR", ejbException);
            this.trataExcepcion(ejbException);
        } catch (Throwable ex) {
            log.error("ERROR", ex);
        }
        return null;
    }

    /**
     * Prepara el formato W8 asociado al beneficiario.
     * 
     * @param beneficiario Beneficiario al que se asocia el formato W8.
     */
    private void prepararFormatoW8(final Beneficiario beneficiario) {
        if (BeneficiariosConstantes.FORMATO_W8_BEN_E.equals(this.formato)
                || BeneficiariosConstantes.FORMATO_W8_BEN_E_2016.equals(this.formato)) {
            // Valida los apendices de la forma
            W8BENEUtil.verificarPartesFormato((FormaW8BENE) this.forma);
            // Obtiene el xml del formato y lo coloca en el beneficiario
            String formaXML =
                    this.formatoW8Service.generarXmlCamposFormato((FormaW8BENE) this.forma);
            FormatoW8BENE formatoW8BENE =
                    new FormatoW8BENE(null, formaXML, ((FormaW8BENE) this.forma).getPartIcmp8(),
                            ((FormaW8BENE) this.forma).getPartIcmp9b2(),
                            ((FormaW8BENE) this.forma).getPartIcmp10(),
                            ((FormaW8BENE) this.forma).getPartIcmp9a2(),
                            ((FormaW8BENE) this.forma).getPrintName());
            beneficiario.setFormatoW8BENE(formatoW8BENE);
            // Coloca los domicilios y valida
            beneficiario.setDomicilioW8Normal(this.domicilio);
            if (!this.deshabilitaDomicilioCorreo) {
                beneficiario.setDomicilioW8Correo(this.domicilioCorreo);
            } else {
                beneficiario.setDomicilioW8Correo(null);
            }
            W8BENEUtil.validarDomiciliosBeneficiario(beneficiario);
        } else if (BeneficiariosConstantes.FORMATO_W8_IMY2015.equals(this.formato)) {
            // Valida las partes del formato
            W8IMY2015Util.validarPartesFormato((FormaW8IMY2015) this.forma);
            // Obtiene el xml del formato y lo coloca en el beneficiario
            String formaXML =
                    this.formatoW8Service.generarXmlCamposFormato((FormaW8IMY2015) this.forma);
            FormatoW8IMY2015 formatoW8IMY2015 =
                    new FormatoW8IMY2015(null, formaXML,
                            ((FormaW8IMY2015) this.forma).getPartIcmp8(),
                            ((FormaW8IMY2015) this.forma).getPartIcmp10(),
                            ((FormaW8IMY2015) this.forma).getPartIcmp9(),
                            ((FormaW8IMY2015) this.forma).getPrintName());
            beneficiario.setFormatoW8IMY2015(formatoW8IMY2015);
            // Coloca los domicilios y valida
            beneficiario.setDomicilioW8Normal(this.domicilio);
            if (!this.deshabilitaDomicilioCorreo) {
                beneficiario.setDomicilioW8Correo(this.domicilioCorreo);
            } else {
                beneficiario.setDomicilioW8Correo(null);
            }
            W8IMY2015Util.validarDomiciliosBeneficiario(beneficiario);
        } else if (BeneficiariosConstantes.FORMATO_W8_IMY2017.equals(this.formato)) { // W8imy2017
            // Valida las partes del formato
            W8IMY2017Util.validarPartesFormato((FormaW8IMY2017) this.forma);
            // Obtiene el xml del formato y lo coloca en el beneficiario
            String formaXML =
                    this.formatoW8Service.generaXmlFormaBeneficiarios((FormaW8IMY2017) this.forma);
            FormatoW8IMY2015 formatoW8IMY2017 =
                    new FormatoW8IMY2015(null, formaXML,
                            ((FormaW8IMY2017) this.forma).getPartIcmp8(),
                            ((FormaW8IMY2017) this.forma).getPartIcmp10(),
                            ((FormaW8IMY2017) this.forma).getPartIcmp9(),
                            ((FormaW8IMY2017) this.forma).getPrintName());
            beneficiario.setFormatoW8IMY2015(formatoW8IMY2017);
            // Coloca los domicilios y valida
            beneficiario.setDomicilioW8Normal(this.domicilio);
            if (!this.deshabilitaDomicilioCorreo) {
                beneficiario.setDomicilioW8Correo(this.domicilioCorreo);
            } else {
                beneficiario.setDomicilioW8Correo(null);
            }
            W8IMY2017Util.validarDomiciliosBeneficiario(beneficiario);
        }
    }


    public boolean isDebeMostrarGaurdar3Formatos() {
        boolean retorno = Boolean.FALSE;
        if ((this.tipoBeneficiario.equals(Constantes.PERSONA_FISICA_NO_EUA)
                || this.tipoBeneficiario.equals(Constantes.PERSONA_MORAL_NO_EUA) || this.tipoBeneficiario
                    .equals(Constantes.SIEFORE_AFORE))
                && (this.custodio.equals(Constantes.CN_JP_MORGAN)
                        || this.custodio.equals(Constantes.CN_DEUSTCHE_BANK)
                        || this.custodio.equals(Constantes.CN_THE_BANK_OF_NEW_YORK) || this.custodio
                            .equals(Constantes.CN_BANK_OF_AMERICA))) {
            retorno = Boolean.TRUE;
        }
        return retorno;
    }

    public String navegacionGuardar() {
        return this.navegacion;
    }

    public void copiaPaisW8BEN(final ActionEvent event) {
        if (this.forma instanceof FormaW8BEN) {
            FormaW8BEN tempForma = (FormaW8BEN) this.forma;
            if (StringUtils.isNotBlank(tempForma.getPais())) {
                tempForma.setLine9a2(tempForma.getPais().toUpperCase());
            }
        }
    }

    public void copiaNombreSigner(final ActionEvent event) {
        if (this.forma instanceof FormaW8BEN) {
            FormaW8BEN tempForma = (FormaW8BEN) this.forma;
            if (StringUtils.isNotBlank(tempForma.getNombreCompleto())) {
                tempForma.setSigner(tempForma.getNombreCompleto().toUpperCase());
            }
        }
    }

    public void calculaFechaNacimiento(final ActionEvent event) {
        if (this.forma instanceof FormaW8BEN) {
            FormaW8BEN tempForma = (FormaW8BEN) this.forma;
            if (tempForma != null) {
                tempForma.validaFechaNacimiento();
            }
        }
    }

    /**
     * Soporte para el cambio de tipo Beneficiario
     * 
     * @param ActionEvent event
     */
    public void cambiaTipoBeneficiario(final ActionEvent event) {
        this.formato =
                this.controlBeneficiariosService.obtieneFormato(this.custodio,
                        this.tipoBeneficiario);
        String mensajeError = "No se encontro ningun formato con esta combinacion";
        if (StringUtils.isNotBlank(this.formato)) {
            if (BeneficiariosConstantes.FORMATO_W8_BEN.equals(this.formato)) {
                this.forma =
                        new FormaW8BEN(this.custodio, this.tipoBeneficiario, null,
                                this.controlBeneficiariosService.getField3W8BEN());
            } else if (BeneficiariosConstantes.FORMATO_W8_BEN2014.equals(this.formato)
                    || BeneficiariosConstantes.FORMATO_W8_BEN2017.equals(this.formato)) {
                this.forma =
                        new FormaW8BEN(this.custodio, this.tipoBeneficiario, null,
                                this.controlBeneficiariosService.getField3W8BEN());
                ((FormaW8BEN) this.forma).setDisabledSection10(false);
                ((FormaW8BEN) this.forma).setLine9a1(true);
                ((FormaW8BEN) this.forma).setLine9b(false);
                ((FormaW8BEN) this.forma).setLine9c(false);
                ((FormaW8BEN) this.forma).setLine9d(false);
                ((FormaW8BEN) this.forma).setLine9e(false);
                ((FormaW8BEN) this.forma).setDisabledreferenceNumber(false);
            } else if (BeneficiariosConstantes.FORMATO_W8_BEN_E.equals(this.formato)
                    || BeneficiariosConstantes.FORMATO_W8_BEN_E_2016.equals(this.formato)) {
                this.forma = new FormaW8BENE(this.custodio, this.tipoBeneficiario, null);
            } else if (BeneficiariosConstantes.FORMATO_W8_IMY.equals(this.formato)) {
                this.forma =
                        new FormaW8IMY(this.custodio, this.tipoBeneficiario, null,
                                this.controlBeneficiariosService.getField3W8IMY());
            } else if (BeneficiariosConstantes.FORMATO_W8_IMY2015.equals(this.formato)) {
                // Crea la forma
                this.forma = new FormaW8IMY2015(this.custodio, this.tipoBeneficiario, null);
                // Obtiene el apendice del campo 4
                this.apendiceParte4W8IMY2015 =
                        W8IMY2015Util.obtenerApendiceCampo4((FormaW8IMY2015) this.forma);
            } else if (BeneficiariosConstantes.FORMATO_W8_IMY2017.equals(this.formato)) { // W8imy2017
                // Crea la forma
                this.forma = new FormaW8IMY2017(this.custodio, this.tipoBeneficiario, null);
                // Obtiene el apendice del campo 4
                this.apendiceParte4W8IMY2017 =
                        W8IMY2017Util.obtenerApendiceCampo4((FormaW8IMY2017) this.forma);
            } else if (BeneficiariosConstantes.FORMATO_W9.equals(this.formato)) {
                this.forma =
                        new FormaW9(this.custodio, this.tipoBeneficiario, null,
                                this.controlBeneficiariosService.getField3W9());
                // **** Combo Exempt Payee W9 *****
                List<ExemptPayeeW9> exempt =
                        this.controlBeneficiariosService.consultaCatExemptPayeeW9();
                if (this.selectExemptPayeeW9 == null) {
                    this.selectExemptPayeeW9 = new ArrayList<SelectItem>();
                }
                this.selectExemptPayeeW9.clear();
                for (ExemptPayeeW9 e : exempt) {
                    this.selectExemptPayeeW9.add(new SelectItem(String.valueOf(e
                            .getIdExemptPayeeW9().intValue()), e.getFatcaCode() + " - "
                            + e.getDescription()));
                }
                // ***** Combo Exemption Fatca W9 *****
                List<ExemptionFatcaW9> ex =
                        this.controlBeneficiariosService.consultaCatExemptionFatcaW9();
                if (this.selectExemptionFatcaW9 == null) {
                    this.selectExemptionFatcaW9 = new ArrayList<SelectItem>();
                }
                this.selectExemptionFatcaW9.clear();
                for (ExemptionFatcaW9 e : ex) {
                    this.selectExemptionFatcaW9.add(new SelectItem(String.valueOf(e
                            .getIdExemptionFatcaW9().intValue()), e.getFatcaCode() + " - "
                            + e.getDescription()));
                }
            } else {
                // Para los formatos mila no se crea su forma en esta pagina sino que se crea en
                // CapturaBaneficiariosMILAConstroller
                // Se deja esto para la pantalla de selecciÃ³n de formato sea la misma para todos
                if (!this.formato.equals("MILA")) {
                    FacesContext.getCurrentInstance().addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, mensajeError,
                                    mensajeError));
                }
            }
            // De igual manera si es el Formato MILA no se crea la forma por lo tanto es nula en
            // este momento
            // Cualquier otro formato sigue el flujo normal
            if (!this.formato.equals("MILA")) {
                this.forma.setFormato(this.formato);
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, mensajeError, mensajeError));
        }
    }

    /**
     * Obtiene los datos de la institucion relacionada
     * 
     * @param ActionEvent event
     */
    public void obtenerDatosParticipante(final ActionEvent event) {
        Institucion institucion = null;
        if (this.idFolioInstitucion != null) {
            institucion =
                    this.consultaCatService.findInstitucionByClaveFolio(this.idFolioInstitucion);
            if (institucion != null) {
                this.nombreInstitucion = institucion.getNombreCorto();
            }
        }
    }

    /**
     * Navega al formato correspodiente
     * 
     * @return String
     */
    public String navegaFormato() {
        if (StringUtils.isNotBlank(this.formato)) {
            // Si el formato es MILA ponemos en el request estos 3 valores para que lo pueda
            // tomar el otro controller CapturaBeneficiarioMILAController ya que para esto formato
            // se maneja en otra clase
            if (this.formato.equals("MILA")) {
                Map<String, Object> request =
                        FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
                request.put("custodio", this.custodio);
                request.put("tipoBeneficiario", this.tipoBeneficiario);
                request.put("formato", this.formato);
            }
            if (BeneficiariosConstantes.FORMATO_W8_BEN_E_2016.equals(this.formato)) {
                return "capturaBeneficiariosW8BENE";
            }

            return "capturaBeneficiarios" + this.formato;
        }
        return null;
    }

    /**
     * Inicializa lista de custodios
     */
    private void inicializaCustodios() {
        List<Object[]> lista = this.controlBeneficiariosService.obtieneCatBicActivo();
        this.listaCustodios = new ArrayList<SelectItem>();
        this.mapaCustodios = new HashMap<Long, String>();
        for (Object[] custodioActual : lista) {
            this.listaCustodios.add(new SelectItem((Long) custodioActual[0],
                    (String) custodioActual[1]));
            this.mapaCustodios.put((Long) custodioActual[0], (String) custodioActual[1]);
        }
    }

    /**
     * Inicializa lista de custodios
     */
    private void inicializaTiposBeneficiarios() {
        this.tiposBeneficiarios = new ArrayList<SelectItem>();
        this.mapaTipoBeneficiario = new HashMap<Long, String>();

        if (this.custodio != null && this.custodio > 0) {
            log.info("Custodio: [" + this.custodio + "]");
            List<Object[]> lista =
                    this.controlBeneficiariosService.obtieneTiposBeneficiario(this.custodio);
            /*
             * se elimina la opcion FideicomisoSimple para el custodio CN_DEUSTCHE_BANK SE pasa la
             * validacion al siguiente if(custodio == Constantes.CN_DEUSTCHE_BANK){ for(Object[]
             * beneficiario : lista){ if( ((Long)beneficiario[0]) == Constantes.FIDEICOMISO_SIMPLE){
             * lista.remove(beneficiario); break; } } }
             */
            /*
             * Se elimina las opciones INTERMEDIARIO_NO_CALIFICADO y SOCIEDAD_PARTNERSHIPpara el
             * nuevo W8IMY2015
             */
            Iterator<Object[]> iter = lista.iterator();
            while (iter.hasNext()) {
                Object[] beneficiario = iter.next();
                if ((Long) beneficiario[0] == Constantes.INTERMEDIARIO_NO_CALIFICADO
                        || (Long) beneficiario[0] == Constantes.SOCIEDAD_PARTNERSHIP
                        || this.custodio == Constantes.CN_DEUSTCHE_BANK
                        && (Long) beneficiario[0] == Constantes.FIDEICOMISO_SIMPLE) {
                    iter.remove();
                    // break;
                }
            }

            for (Object[] bene : lista) {
                this.tiposBeneficiarios.add(new SelectItem((Long) bene[0], (String) bene[1]));
                this.mapaTipoBeneficiario.put((Long) bene[0], ((String) bene[1]).toUpperCase());
            }
        }

    }

    /**
     * Selecciona los apendices del formato W8BEN-E
     * 
     * @param actionEvent Accion generada por faces
     */
    public void seleccionarApendicesW8BENE(final ActionEvent actionEvent) {
        W8BENEUtil.habilitarDeshabilitarPartesFormato((FormaW8BENE) this.forma);
    }


    /**
     * Copia el campo 2 de la parte I al campo 14b de la parte III
     * 
     * @param actionEvent Accion generada por faces
     */
    public void copiarCampo2ParteICampo14bParteIII(final ActionEvent actionEvent) {
        W8BENEUtil.copiarPais((FormaW8BENE) this.forma);
    }

    /**
     * Completa el campo 15 de la forma W8BEN-E
     * 
     * @param event Accion generada por faces
     */
    public void completarCampo15W8BENE(final ActionEvent event) {
        W8BENEUtil.completarCampo15((FormaW8BENE) this.forma);
    }

    /**
     * Inhabilita el domicilio del correo.
     * 
     * @param actionEvent Accion generada por faces.
     */
    public void deshabilitarDomicilioCorreoAction(final ActionEvent actionEvent) {
        if (this.forma instanceof FormaW8BENE) {
            W8BENEUtil.deshabilitarDomicilioCorreo(this.deshabilitaDomicilioCorreo,
                    (FormaW8BENE) this.forma);
        } else if (this.forma instanceof FormaW8IMY2015) {
            W8IMY2015Util.deshabilitarDomicilioCorreo(this.deshabilitaDomicilioCorreo,
                    (FormaW8IMY2015) this.forma);
        } else if (this.forma instanceof FormaW8IMY2017) {
            W8IMY2017Util.deshabilitarDomicilioCorreo(this.deshabilitaDomicilioCorreo,
                    (FormaW8IMY2017) this.forma);
        }
        this.domicilioCorreo = new Domicilio();
    }

    /**
     * Limpia el contenido del GIIN según el valor del checkbox correspondiente
     * 
     * @param actionEvent
     */
    public void limpiarGiinAction(final ActionEvent actionEvent) {
        if (this.forma instanceof FormaW8BENE) {
            boolean isLimpiarGiin = !((FormaW8BENE) this.forma).isPartIcmp9a1();
            if (isLimpiarGiin) {
                ((FormaW8BENE) this.forma).setPartIcmp9a2(BeneficiariosConstantes.CADENA_VACIA);
            }
        }
    }

    /**
     * Limpia el contenido del foreign TIN según el valor del checkbox correspondiente
     * 
     * @param actionEvent
     */
    public void limpiarForeignTinAction(final ActionEvent actionEvent) {
        if (this.forma instanceof FormaW8BENE) {
            boolean isLimpiarFTin = !((FormaW8BENE) this.forma).isPartIcmp9b1();
            if (isLimpiarFTin) {
                ((FormaW8BENE) this.forma).setPartIcmp9b2(BeneficiariosConstantes.CADENA_VACIA);
            }
        }
    }

    /**
     * Prende o apaga los booleanos correspondientes a los apendices de la forma W8IMY.
     * 
     * @param actionEvent Accion generada por faces
     */
    public void checarApendicesCampo4(final ActionEvent actionEvent) {
        W8IMY2015Util.checarApendicesCampo4((FormaW8IMY2015) this.forma);
    }

    /**
     * Prende o apaga los booleanos correspondientes a los apendices de la forma W8IMY.
     * 
     * @param actionEvent Accion generada por faces
     */
    public void checarApendicesCampo5(final ActionEvent actionEvent) {
        W8IMY2015Util.checarApendicesCampo5((FormaW8IMY2015) this.forma);
    }

    /**
     * @param controlBeneficiariosService the controlBeneficiariosService to set
     */
    public void setControlBeneficiariosService(
            final ControlBeneficiariosService controlBeneficiariosService) {
        this.controlBeneficiariosService = controlBeneficiariosService;
    }

    /**
     * @return the listaCustodios
     */
    public List<SelectItem> getListaCustodios() {
        return this.listaCustodios;
    }

    /**
     * @param listaCustodios the listaCustodios to set
     */
    public void setListaCustodios(final List<SelectItem> listaCustodios) {
        this.listaCustodios = listaCustodios;
    }

    /**
     * @return the custodio
     */
    public Long getCustodio() {
        return this.custodio;
    }

    /**
     * @param custodio the custodio to set
     */
    public void setCustodio(final Long custodio) {
        this.custodio = custodio;
    }

    /**
     * @return the tiposBeneficiarios
     */
    public List<SelectItem> getTiposBeneficiarios() {
        return this.tiposBeneficiarios;
    }

    /**
     * @param tiposBeneficiarios the tiposBeneficiarios to set
     */
    public void setTiposBeneficiarios(final List<SelectItem> tiposBeneficiarios) {
        this.tiposBeneficiarios = tiposBeneficiarios;
    }

    /**
     * @return the tipoBeneficiario
     */
    public Long getTipoBeneficiario() {
        return this.tipoBeneficiario;
    }

    /**
     * @param tipoBeneficiario the tipoBeneficiario to set
     */
    public void setTipoBeneficiario(final Long tipoBeneficiario) {
        this.tipoBeneficiario = tipoBeneficiario;
    }

    /**
     * @param formato the formato to set
     */
    public void setFormato(final String formato) {
        this.formato = formato;
    }

    /**
     * @return the formato
     */
    public String getFormato() {
        return this.formato;
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
     * @param nombreInstitucion the nombreInstitucion to set
     */
    public void setNombreInstitucion(final String nombreInstitucion) {
        this.nombreInstitucion = nombreInstitucion;
    }

    /**
     * @return the nombreInstitucion
     */
    public String getNombreInstitucion() {
        return this.nombreInstitucion;
    }

    /**
     * @return the formaW8BEN
     */
    public FormaGeneral getForma() {
        return this.forma;
    }

    /**
     * @param formaW8BEN the formaW8BEN to set
     */
    public void setForma(final FormaGeneral forma) {
        this.forma = forma;
    }

    /**
     * @param consultaCatService the consultaCatService to set
     */
    public void setConsultaCatService(final ConsultaCatalogoService consultaCatService) {
        this.consultaCatService = consultaCatService;
    }

    /**
     * @param beneficiariosExistentes the beneficiariosExistentes to set
     */
    public void setBeneficiariosExistentes(final boolean beneficiariosExistentes) {
        this.beneficiariosExistentes = beneficiariosExistentes;
    }

    /**
     * @return the beneficiariosExistentes
     */
    public boolean isBeneficiariosExistentes() {
        return this.beneficiariosExistentes;
    }

    /**
     * @return the flagBuscarBeneficiariosExistentes
     */
    public boolean isFlagBuscarBeneficiariosExistentes() {
        return this.flagBuscarBeneficiariosExistentes;
    }

    /**
     * @param flagBuscarBeneficiariosExistentes the flagBuscarBeneficiariosExistentes to set
     */
    public void setFlagBuscarBeneficiariosExistentes(final boolean flagBuscarBeneficiariosExistentes) {
        this.flagBuscarBeneficiariosExistentes = flagBuscarBeneficiariosExistentes;
    }

    /**
     * @return the mapaCustodios
     */
    public Map<Long, String> getMapaCustodios() {
        return this.mapaCustodios;
    }

    /**
     * @param mapaCustodios the mapaCustodios to set
     */
    public void setMapaCustodios(final Map<Long, String> mapaCustodios) {
        this.mapaCustodios = mapaCustodios;
    }

    /**
     * @return the mapaTipoBeneficiario
     */
    public Map<Long, String> getMapaTipoBeneficiario() {
        return this.mapaTipoBeneficiario;
    }

    /**
     * @param mapaTipoBeneficiario the mapaTipoBeneficiario to set
     */
    public void setMapaTipoBeneficiario(final Map<Long, String> mapaTipoBeneficiario) {
        this.mapaTipoBeneficiario = mapaTipoBeneficiario;
    }

    /**
     * Bandera que indica si se guardo con exito el registro
     * 
     * @return the exitoGuardar
     */
    public boolean isExitoGuardar() {
        return this.exitoGuardar;
    }

    /**
     * Bandera que indica si se guardo con exito el registro
     * 
     * @param exitoGuardar the exitoGuardar to set
     */
    public void setExitoGuardar(final boolean exitoGuardar) {
        this.exitoGuardar = exitoGuardar;
    }

    public boolean isGuardaJPMorgan() {
        return this.guardaJPMorgan;
    }

    public void setGuardaJPMorgan(final boolean guardaJPMorgan) {
        this.guardaJPMorgan = guardaJPMorgan;
    }

    public boolean isGuardaDeutsche() {
        return this.guardaDeutsche;
    }

    public void setGuardaDeutsche(final boolean guardaDeutsche) {
        this.guardaDeutsche = guardaDeutsche;
    }

    public boolean isGuardaBony() {
        return this.guardaBony;
    }

    public void setGuardaBony(final boolean guardaBony) {
        this.guardaBony = guardaBony;
    }

    public boolean isBloqueoGuardaJPMorgan() {
        return this.bloqueoGuardaJPMorgan;
    }

    public void setBloqueoGuardaJPMorgan(final boolean bloqueoGuardaJPMorgan) {
        this.bloqueoGuardaJPMorgan = bloqueoGuardaJPMorgan;
    }

    public boolean isBloqueoGuardaDeutsche() {
        return this.bloqueoGuardaDeutsche;
    }

    public void setBloqueoGuardaDeutsche(final boolean bloqueoGuardaDeutsche) {
        this.bloqueoGuardaDeutsche = bloqueoGuardaDeutsche;
    }

    public boolean isBloqueoGuardaBony() {
        return this.bloqueoGuardaBony;
    }

    public void setBloqueoGuardaBony(final boolean bloqueoGuardaBony) {
        this.bloqueoGuardaBony = bloqueoGuardaBony;
    }

    /**
     * @return the selectExemptPayeeW9
     */
    public List<SelectItem> getSelectExemptPayeeW9() {
        return this.selectExemptPayeeW9;
    }

    /**
     * @param selectExemptPayeeW9 the selectExemptPayeeW9 to set
     */
    public void setSelectExemptPayeeW9(final List<SelectItem> selectExemptPayeeW9) {
        this.selectExemptPayeeW9 = selectExemptPayeeW9;
    }

    /**
     * @return the selectExemptionFatcaW9
     */
    public List<SelectItem> getSelectExemptionFatcaW9() {
        return this.selectExemptionFatcaW9;
    }

    /**
     * @param selectExemptionFatcaW9 the selectExemptionFatcaW9 to set
     */
    public void setSelectExemptionFatcaW9(final List<SelectItem> selectExemptionFatcaW9) {
        this.selectExemptionFatcaW9 = selectExemptionFatcaW9;
    }

    /**
     * @return the guardaBofa
     */
    public boolean isGuardaBofa() {
        return this.guardaBofa;
    }

    /**
     * @param guardaBofa the guardaBofa to set
     */
    public void setGuardaBofa(final boolean guardaBofa) {
        this.guardaBofa = guardaBofa;
    }

    /**
     * @return the bloqueoGuardaBofa
     */
    public boolean isBloqueoGuardaBofa() {
        return this.bloqueoGuardaBofa;
    }

    /**
     * @param bloqueoGuardaBofa the bloqueoGuardaBofa to set
     */
    public void setBloqueoGuardaBofa(final boolean bloqueoGuardaBofa) {
        this.bloqueoGuardaBofa = bloqueoGuardaBofa;
    }

    /**
     * MÃ©todo para obtener el atributo formatoW8Service
     * 
     * @return El atributo formatoW8Service
     */
    public FormatoW8Service getFormatoW8Service() {
        return this.formatoW8Service;
    }

    /**
     * MÃ©todo para establecer el atributo formatoW8Service
     * 
     * @param formatoW8Service El valor del atributo formatoW8Service a establecer.
     */
    public void setFormatoW8Service(final FormatoW8Service formatoW8Service) {
        this.formatoW8Service = formatoW8Service;
    }

    /**
     * MÃ©todo para obtener el atributo deshabilitaDomicilioCorreo
     * 
     * @return El atributo deshabilitaDomicilioCorreo
     */
    public boolean isDeshabilitaDomicilioCorreo() {
        return this.deshabilitaDomicilioCorreo;
    }

    /**
     * MÃ©todo para establecer el atributo deshabilitaDomicilioCorreo
     * 
     * @param deshabilitaDomicilioCorreo El valor del atributo deshabilitaDomicilioCorreo a
     *        establecer.
     */
    public void setDeshabilitaDomicilioCorreo(final boolean deshabilitaDomicilioCorreo) {
        this.deshabilitaDomicilioCorreo = deshabilitaDomicilioCorreo;
    }

    /**
     * MÃ©todo para obtener el atributo domicilio
     * 
     * @return El atributo domicilio
     */
    public Domicilio getDomicilio() {
        return this.domicilio;
    }

    /**
     * MÃ©todo para establecer el atributo domicilio
     * 
     * @param domicilio El valor del atributo domicilio a establecer.
     */
    public void setDomicilio(final Domicilio domicilio) {
        this.domicilio = domicilio;
    }

    /**
     * MÃ©todo para obtener el atributo domicilioCorreo
     * 
     * @return El atributo domicilioCorreo
     */
    public Domicilio getDomicilioCorreo() {
        return this.domicilioCorreo;
    }

    /**
     * MÃ©todo para establecer el atributo domicilioCorreo
     * 
     * @param domicilioCorreo El valor del atributo domicilioCorreo a establecer.
     */
    public void setDomicilioCorreo(final Domicilio domicilioCorreo) {
        this.domicilioCorreo = domicilioCorreo;
    }

    /**
     * MÃ©todo para obtener el atributo apendiceParte4W8IMY2015
     * 
     * @return El atributo apendiceParte4W8IMY2015
     */
    public String getApendiceParte4W8IMY2015() {
        return this.apendiceParte4W8IMY2015;
    }

    /**
     * MÃ©todo para establecer el atributo apendiceParte4W8IMY2015
     * 
     * @param apendiceParte4W8IMY2015 El valor del atributo apendiceParte4W8IMY2015 a establecer.
     */
    public void setApendiceParte4W8IMY2015(final String apendiceParte4W8IMY2015) {
        this.apendiceParte4W8IMY2015 = apendiceParte4W8IMY2015;
    }

    /** @return this.apendiceParte4W8IMY2017 */
    public String getApendiceParte4W8IMY2017() {
        return this.apendiceParte4W8IMY2017;
    }

    /** @param apendiceParte4W8IMY2017 to be set in this.apendiceParte4W8IMY2017 */
    public void setApendiceParte4W8IMY2017(final String apendiceParte4W8IMY2017) {
        this.apendiceParte4W8IMY2017 = apendiceParte4W8IMY2017;
    }

}

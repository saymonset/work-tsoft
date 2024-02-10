/**
 * 2H Software - Bursatec - INDEVAL Portal DALI
 * 
 * Jul 2, 2008
 */
package com.indeval.portalinternacional.presentation.controller.beneficiarios;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.Domicilio;
import com.indeval.portalinternacional.middleware.servicios.modelo.ExemptPayeeW9;
import com.indeval.portalinternacional.middleware.servicios.modelo.ExemptionFatcaW9;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * Controller para la Captura de Beneficiarios de Division Internacional
 * 
 * @author Rafael Ibarra
 * @version 1.0
 */
// @SuppressWarnings("unchecked")
public class ModificaBeneficiarioController extends ControllerBase {

    /** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(ModificaBeneficiarioController.class);

    /** Servicio de Beneficiarios */
    private ControlBeneficiariosService controlBeneficiariosService;
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
    /** Mapa que contiene la lista de custodios para la tabla */
    private Map<Long, String> mapaCustodios;
    /** Mapa que contiene la lista de custodios para la tabla */
    private Map<Long, String> mapaTipoBeneficiario;
    /** Id del Beneficiario Seleccionado */
    private Long idBeneficiarioSelected;

    private List<SelectItem> selectExemptPayeeW9;
    private List<SelectItem> selectExemptionFatcaW9;

    /** Servicio para parsear el formato a xml y viceversa */
    private FormatoW8Service formatoW8Service;

    /** Indica que apendice de la forma W8 debe activar */
    private String apendiceW8;

    /** Apendice parte 4 W8IMY 2015 */
    private String apendiceParte4W8IMY2015;

    /** Apendice parte 4 W8IMY 2015 */
    private String apendiceParte4W8IMY2017;

    /** Indica si esta deshabilitado el domicilio de correo */
    private boolean deshabilitaDomicilioCorreo;

    /** Objeto para capturar el domicilio del formato */
    private Domicilio domicilio;

    /** Objeto para capturar el domicilio de correo */
    private Domicilio domicilioCorreo;

    /**
     * Constructor de Captura Operaciones
     */
    public ModificaBeneficiarioController() {

    }

    /**
     * Asigna las opciones predeterminadas para cuando se carga la página por primerva vez.
     * 
     * @return nulo, este método no requiere retornar un valor
     */
    public String getIniciaModificacion() {
        if (this.idBeneficiarioSelected != null && this.idBeneficiarioSelected > 0) {
            log.debug("Beneficiario Seleccionado: [" + this.idBeneficiarioSelected + "-"
                    + this.formato + "]");
            Beneficiario beneficiario =
                    this.controlBeneficiariosService
                            .consultaBeneficiarioById(this.idBeneficiarioSelected);
            if (beneficiario != null) {
                Long custodio = beneficiario.getIdCuentaNombrada();
                Long tipoBeneficiario = beneficiario.getTipoBeneficiario().getIdTipoBeneficiario();
                this.formato = beneficiario.getTipoFormato();
                if (BeneficiariosConstantes.FORMATO_W8_BEN.equals(this.formato)
                        || BeneficiariosConstantes.FORMATO_W8_BEN2014.equals(this.formato)
                        || BeneficiariosConstantes.FORMATO_W8_BEN2017.equals(this.formato)) {
                    this.forma =
                            new FormaW8BEN(custodio, tipoBeneficiario, null,
                                    this.controlBeneficiariosService.getField3W8BEN());
                } else if (BeneficiariosConstantes.FORMATO_W8_BEN_E.equals(this.formato)
                        || BeneficiariosConstantes.FORMATO_W8_BEN_E_2016.equals(this.formato)) {
                    this.forma =
                            this.formatoW8Service.obtenerCamposFormatoW8BENE(beneficiario
                                    .getFormatoW8BENE().getIdCamposFormatoW8bene());
                    this.forma.construyeBean(beneficiario, false);
                    this.apendiceW8 = W8BENEUtil.obtenerApendice((FormaW8BENE) this.forma);
                    this.domicilio = beneficiario.getDomicilioW8Normal();
                    this.domicilioCorreo = beneficiario.getDomicilioW8Correo();
                    this.deshabilitaDomicilioCorreo =
                            ((FormaW8BENE) this.forma).isInhabilitarPartIcmp7();
                } else if (BeneficiariosConstantes.FORMATO_W8_IMY2015.equals(this.formato)) {
                    // Obtiene la forma a modificar
                    this.forma =
                            this.formatoW8Service.obtenerCamposFormatoW8IMY2015(beneficiario
                                    .getFormatoW8IMY2015().getIdCamposFormato());
                    this.forma.construyeBean(beneficiario, false);
                    // Obtiene los apendices asociados a los campos 4 y 5
                    this.apendiceParte4W8IMY2015 =
                            W8IMY2015Util.obtenerApendiceCampo4((FormaW8IMY2015) this.forma);
                    this.apendiceW8 =
                            W8IMY2015Util.obtenerApendiceCampo5((FormaW8IMY2015) this.forma);
                    // Obtiene los domicilios
                    this.domicilio = beneficiario.getDomicilioW8Normal();
                    this.domicilioCorreo = beneficiario.getDomicilioW8Correo();
                    this.deshabilitaDomicilioCorreo =
                            ((FormaW8IMY2015) this.forma).isReadOnlyCmp7();
                } else if (BeneficiariosConstantes.FORMATO_W8_IMY2017.equals(this.formato)) {// W8imy2017
                    // Obtiene la forma a modificar
                    this.forma =
                            this.formatoW8Service.obtenerCamposFormatoW8IMY2017(beneficiario
                                    .getFormatoW8IMY2015().getIdCamposFormato());
                    this.forma.construyeBean(beneficiario, false);
                    // Obtiene los apendices asociados a los campos 4 y 5
                    this.apendiceParte4W8IMY2017 =
                            W8IMY2017Util.obtenerApendiceCampo4((FormaW8IMY2017) this.forma);
                    this.apendiceW8 =
                            W8IMY2017Util.obtenerApendiceCampo5((FormaW8IMY2017) this.forma);
                    // Obtiene los domicilios
                    this.domicilio = beneficiario.getDomicilioW8Normal();
                    this.domicilioCorreo = beneficiario.getDomicilioW8Correo();
                    this.deshabilitaDomicilioCorreo =
                            ((FormaW8IMY2017) this.forma).isReadOnlyCmp7();
                } else if (this.formato.equals("W8IMY")) {
                    this.forma =
                            new FormaW8IMY(custodio, tipoBeneficiario, null,
                                    this.controlBeneficiariosService.getField3W8IMY());
                } else if (this.formato.equals("W9")) {
                    this.forma =
                            new FormaW9(custodio, tipoBeneficiario, null,
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
                }
                this.forma.construyeBean(beneficiario, false);
            } else {
                log.error("Beneficiario no encontrado");
            }
        } else {
            log.error("No se tiene el parametro del Id del beneficiairo");
            throw new IllegalArgumentException("No se pudo recibir el parametro del beneficiario");
        }
        this.idFolioInstitucion = null;
        this.inicializaCustodios();
        this.inicializaTiposBeneficiarios();
        if (!this.isInstitucionIndeval()) {
            AgenteVO agente = this.getAgenteFirmado();
            this.idFolioInstitucion = agente.getId() + agente.getFolio();
            this.nombreInstitucion = agente.getNombreCorto();
        }
        return "modificaBeneficiario" + this.formato;
    }

    public void limpiar(final ActionEvent event) {
        log.debug("Entrando a ModificaBeneficiarioController.limpiar");
        this.formato = null;
        this.forma = null;
        this.idFolioInstitucion = null;
        this.nombreInstitucion = null;
    }

    public String guardar() {
        log.debug("Entrando a ModificaBeneficiarioController.guardar");
        try {
            Beneficiario beneficiarioOriginal =
                    this.controlBeneficiariosService
                            .consultaBeneficiarioById(this.idBeneficiarioSelected);
            Beneficiario beneficiario = this.forma.construyeBO(beneficiarioOriginal);
            this.prepararFormatoW8(beneficiario);
            this.controlBeneficiariosService.actualizaBeneficiario(beneficiario);
            String message = "Beneficiario guardado con exito";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, message, message));
            this.limpiar(null);
            ConsultaBeneficiariosController consultaBeneficiariosBean =
                    (ConsultaBeneficiariosController) this.getBean("consultaBeneficiariosBean");
            consultaBeneficiariosBean.buscar(null);
            log.debug("Bandera Inicio Consulta: [" + consultaBeneficiariosBean.isBanderaInicio()
                    + "]");
            return "consultaBeneficiarios";
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
            beneficiario.getFormatoW8BENE().setCamposFormato(formaXML);
            // Coloca los domicilios y valida
            beneficiario.setDomicilioW8Normal(this.domicilio);
            if (!this.deshabilitaDomicilioCorreo) {
                beneficiario.setDomicilioW8Correo(this.domicilioCorreo);
            } else {
                beneficiario.setDomicilioW8Correo(null);
            }
            W8BENEUtil.validarDomiciliosBeneficiario(beneficiario);
            // Setea los campos US TIN, Foreing TIN y Reference Number
            beneficiario.getFormatoW8BENE().setUsTIN(((FormaW8BENE) this.forma).getPartIcmp8());
            beneficiario.getFormatoW8BENE().setForeingTIN(
                    ((FormaW8BENE) this.forma).getPartIcmp9b2());
            beneficiario.getFormatoW8BENE().setReferenceNumber(
                    ((FormaW8BENE) this.forma).getPartIcmp10());
            beneficiario.getFormatoW8BENE().setGiin(((FormaW8BENE) this.forma).getPartIcmp9a2());
            beneficiario.getFormatoW8BENE().setPrintName(((FormaW8BENE) this.forma).getPrintName());
        } else if (BeneficiariosConstantes.FORMATO_W8_IMY2015.equals(this.formato)) {
            // Valida las partes del formato
            W8IMY2015Util.validarPartesFormato((FormaW8IMY2015) this.forma);
            // Obtiene el xml del formato y lo coloca en el beneficiario
            String formaXML =
                    this.formatoW8Service.generarXmlCamposFormato((FormaW8IMY2015) this.forma);
            beneficiario.getFormatoW8IMY2015().setCamposFormato(formaXML);
            // Coloca los domicilios y valida
            beneficiario.setDomicilioW8Normal(this.domicilio);
            if (!this.deshabilitaDomicilioCorreo) {
                beneficiario.setDomicilioW8Correo(this.domicilioCorreo);
            } else {
                beneficiario.setDomicilioW8Correo(null);
            }
            W8IMY2015Util.validarDomiciliosBeneficiario(beneficiario);
            // Setea los campos US TIN y reference number
            beneficiario.getFormatoW8IMY2015().setUsTIN(
                    ((FormaW8IMY2015) this.forma).getPartIcmp8());
            beneficiario.getFormatoW8IMY2015().setReferenceNumber(
                    ((FormaW8IMY2015) this.forma).getPartIcmp10());
            beneficiario.getFormatoW8IMY2015()
                    .setGiin(((FormaW8IMY2015) this.forma).getPartIcmp9());
            beneficiario.getFormatoW8IMY2015().setPrintName(
                    ((FormaW8IMY2015) this.forma).getPrintName());
        } else if (BeneficiariosConstantes.FORMATO_W8_IMY2017.equals(this.formato)) { // W8imy2017
            // Valida las partes del formato
            W8IMY2017Util.validarPartesFormato((FormaW8IMY2017) this.forma);
            // Obtiene el xml del formato y lo coloca en el beneficiario
            String formaXML =
                    this.formatoW8Service.generarXmlCamposFormato((FormaW8IMY2017) this.forma);
            beneficiario.getFormatoW8IMY2015().setCamposFormato(formaXML);
            // Coloca los domicilios y valida
            beneficiario.setDomicilioW8Normal(this.domicilio);
            if (!this.deshabilitaDomicilioCorreo) {
                beneficiario.setDomicilioW8Correo(this.domicilioCorreo);
            } else {
                beneficiario.setDomicilioW8Correo(null);
            }
            W8IMY2017Util.validarDomiciliosBeneficiario(beneficiario);
            // Setea los campos US TIN y reference number
            beneficiario.getFormatoW8IMY2015().setUsTIN(
                    ((FormaW8IMY2017) this.forma).getPartIcmp8());
            beneficiario.getFormatoW8IMY2015().setReferenceNumber(
                    ((FormaW8IMY2017) this.forma).getPartIcmp10());
            beneficiario.getFormatoW8IMY2015()
                    .setGiin(((FormaW8IMY2017) this.forma).getPartIcmp9());
            beneficiario.getFormatoW8IMY2015().setPrintName(
                    ((FormaW8IMY2017) this.forma).getPrintName());
        }
    }

    public void copiaPaisW8BEN(final ActionEvent event) {
        if (this.forma instanceof FormaW8BEN) {
            FormaW8BEN tempForma = (FormaW8BEN) this.forma;
            if (StringUtils.isNotBlank(tempForma.getPais())) {
                tempForma.setLine9a2(tempForma.getPais().toUpperCase());
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
        if (this.idFolioInstitucion != null) {
            institucion =
                    this.consultaCatService.findInstitucionByClaveFolio(this.idFolioInstitucion);
            if (institucion != null) {
                this.nombreInstitucion = institucion.getNombreCorto();
            }
        }
    }

    /**
     * Inicializa lista de custodios
     */
    private void inicializaCustodios() {
        List<Object[]> lista = this.controlBeneficiariosService.obtieneCatBic();
        this.mapaCustodios = new HashMap<Long, String>();
        for (Object[] custodioActual : lista) {
            this.mapaCustodios.put((Long) custodioActual[0], (String) custodioActual[1]);
        }
    }

    /**
     * Inicializa lista de custodios
     */
    @SuppressWarnings("unchecked")
    private void inicializaTiposBeneficiarios() {
        this.mapaTipoBeneficiario = new HashMap<Long, String>();
        Long custodio = this.forma.getCustodio();
        if (custodio != null && custodio > 0) {
            List<Object[]> lista =
                    this.controlBeneficiariosService.obtieneTiposBeneficiario(custodio);
            for (Object[] bene : lista) {
                this.mapaTipoBeneficiario.put((Long) bene[0], ((String) bene[1]).toUpperCase());
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
     * Selecciona los apendices del formato W8BEN-E
     * 
     * @param actionEvent Accion generada por faces
     */
    public void seleccionarApendicesW8BENE(final ActionEvent actionEvent) {
        W8BENEUtil.habilitarDeshabilitarPartesFormato((FormaW8BENE) this.forma);
        this.apendiceW8 = W8BENEUtil.obtenerApendice((FormaW8BENE) this.forma);
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
        if (this.forma != null && this.forma instanceof FormaW8IMY2015) {
            W8IMY2015Util.checarApendicesCampo4((FormaW8IMY2015) this.forma);
            this.apendiceParte4W8IMY2015 =
                    W8IMY2015Util.obtenerApendiceCampo4((FormaW8IMY2015) this.forma);
        } else if (this.forma != null && this.forma instanceof FormaW8IMY2017) {
            // Se desactiva la incursion de partes
            // W8IMY2017Util.checarApendicesCampo4((FormaW8IMY2017) this.forma);
            // this.apendiceParte4W8IMY2017 =
            // W8IMY2017Util.obtenerApendiceCampo4((FormaW8IMY2017) this.forma);
        }
    }

    /**
     * Prende o apaga los booleanos correspondientes a los apendices de la forma W8IMY.
     * 
     * @param actionEvent Accion generada por faces
     */
    public void checarApendicesCampo5(final ActionEvent actionEvent) {
        if (this.forma != null && this.forma instanceof FormaW8IMY2015) {
            W8IMY2015Util.checarApendicesCampo5((FormaW8IMY2015) this.forma);
            this.apendiceW8 = W8IMY2015Util.obtenerApendiceCampo5((FormaW8IMY2015) this.forma);
        } else if (this.forma != null && this.forma instanceof FormaW8IMY2017) {
            // Se desactiva la incursion de partes
            // W8IMY2017Util.checarApendicesCampo5((FormaW8IMY2017) this.forma);
            // this.apendiceW8 = W8IMY2017Util.obtenerApendiceCampo5((FormaW8IMY2017) this.forma);
        }
    }

    /**
     * @param controlBeneficiariosService the controlBeneficiariosService to set
     */
    public void setControlBeneficiariosService(
            final ControlBeneficiariosService controlBeneficiariosService) {
        this.controlBeneficiariosService = controlBeneficiariosService;
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
     * Id del Beneficiario Seleccionado
     * 
     * @return the idBeneficiarioSelected
     */
    public Long getIdBeneficiarioSelected() {
        return this.idBeneficiarioSelected;
    }

    /**
     * Id del Beneficiario Seleccionado
     * 
     * @param idBeneficiarioSelected the idBeneficiarioSelected to set
     */
    public void setIdBeneficiarioSelected(final Long idBeneficiarioSelected) {
        this.idBeneficiarioSelected = idBeneficiarioSelected;
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
     * Método para obtener el atributo formatoW8Service
     * 
     * @return El atributo formatoW8Service
     */
    public FormatoW8Service getFormatoW8Service() {
        return this.formatoW8Service;
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
     * Método para obtener el atributo apendiceW8
     * 
     * @return El atributo apendiceW8
     */
    public String getApendiceW8() {
        return this.apendiceW8;
    }

    /**
     * Método para establecer el atributo apendiceW8
     * 
     * @param apendiceW8 El valor del atributo apendiceW8 a establecer.
     */
    public void setApendiceW8(final String apendiceW8) {
        this.apendiceW8 = apendiceW8;
    }

    /**
     * Método para obtener el atributo deshabilitaDomicilioCorreo
     * 
     * @return El atributo deshabilitaDomicilioCorreo
     */
    public boolean isDeshabilitaDomicilioCorreo() {
        return this.deshabilitaDomicilioCorreo;
    }

    /**
     * Método para establecer el atributo deshabilitaDomicilioCorreo
     * 
     * @param deshabilitaDomicilioCorreo El valor del atributo deshabilitaDomicilioCorreo a
     *        establecer.
     */
    public void setDeshabilitaDomicilioCorreo(final boolean deshabilitaDomicilioCorreo) {
        this.deshabilitaDomicilioCorreo = deshabilitaDomicilioCorreo;
    }

    /**
     * Método para obtener el atributo domicilio
     * 
     * @return El atributo domicilio
     */
    public Domicilio getDomicilio() {
        return this.domicilio;
    }

    /**
     * Método para establecer el atributo domicilio
     * 
     * @param domicilio El valor del atributo domicilio a establecer.
     */
    public void setDomicilio(final Domicilio domicilio) {
        this.domicilio = domicilio;
    }

    /**
     * Método para obtener el atributo domicilioCorreo
     * 
     * @return El atributo domicilioCorreo
     */
    public Domicilio getDomicilioCorreo() {
        return this.domicilioCorreo;
    }

    /**
     * Método para establecer el atributo domicilioCorreo
     * 
     * @param domicilioCorreo El valor del atributo domicilioCorreo a establecer.
     */
    public void setDomicilioCorreo(final Domicilio domicilioCorreo) {
        this.domicilioCorreo = domicilioCorreo;
    }

    /**
     * Método para obtener el atributo apendiceParte4W8IMY2015
     * 
     * @return El atributo apendiceParte4W8IMY2015
     */
    public String getApendiceParte4W8IMY2015() {
        return this.apendiceParte4W8IMY2015;
    }

    /**
     * Método para establecer el atributo apendiceParte4W8IMY2015
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

/**
 * 2H Software - Bursatec - INDEVAL Portal DALI
 * 
 * Jul 2, 2008
 */
package com.indeval.portalinternacional.presentation.controller.beneficiarios;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.formatosw.FormaGeneral;
import com.indeval.portaldali.middleware.formatosw.FormaMILA;
import com.indeval.portaldali.middleware.formatosw.FormaW8BEN;
import com.indeval.portaldali.middleware.formatosw.FormaW8IMY;
import com.indeval.portaldali.middleware.formatosw.FormaW9;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.FormatoW8Service;
import com.indeval.portalinternacional.middleware.servicios.constantes.BeneficiariosConstantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * Controller para la Captura de Beneficiarios de Division Internacional
 * 
 * @author Rafael Ibarra
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class AsignarAdpBeneficiarioController extends ControllerBase {

    /** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(AsignarAdpBeneficiarioController.class);
    /** Servicio de Beneficiarios */
    private ControlBeneficiariosService controlBeneficiariosService;
    /** Mapa que contiene la lista de custodios para la tabla */
    private Map<Long, String> mapaCustodios;
    /** Mapa que contiene la lista de custodios para la tabla */
    private Map<Long, String> mapaTipoBeneficiario;
    /** Forma W8BEN para la pantalla */
    private FormaGeneral forma = new FormaGeneral();
    /** ID Beneficiacrio */
    private Long idBeneficiarioSelected;
    /** Adp a guardar */
    private String adp;

    /** Servicio para parsear el formato a xml y viceversa */
    private FormatoW8Service formatoW8Service;

    /**
     * Constructor de Captura Operaciones
     */
    public AsignarAdpBeneficiarioController() {}

    /**
     * Asigna las opciones predeterminadas para cuando se carga la página por primerva vez.
     * 
     * @return nulo, este método no requiere retornar un valor
     */
    public String getInit() {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        String idBenefCadena =
                facesContext.getExternalContext().getRequestParameterMap().get("idBeneficiario");
        String messageError = "Hubo un error al recibir los parametros";
        try {
            this.idBeneficiarioSelected = Long.valueOf(idBenefCadena);
            this.limpiar(null);
            this.inicializaMapaCustodios();
            this.inicializaTiposBeneficiarios();
        } catch (Exception e) {
            log.error(messageError, e);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, messageError, messageError));
            return null;
        }

        return null;
    }

    private void limpiar(final ActionEvent event) throws Exception {
        this.adp = null;
        this.forma = null;
        if (this.idBeneficiarioSelected == null || this.idBeneficiarioSelected <= 0) {
            throw new Exception("Id de Beneficiario incorrecto");
        }

        Beneficiario beneficiario =
                this.controlBeneficiariosService
                        .consultaBeneficiarioById(this.idBeneficiarioSelected);
        if (beneficiario != null) {
            String formato = beneficiario.getTipoFormato();
            Long custodio = beneficiario.getIdCuentaNombrada();
            Long tipoBeneficiario = beneficiario.getTipoBeneficiario().getIdTipoBeneficiario();
            String nombreTipoBeneficiario =
                    beneficiario.getTipoBeneficiario().getDescTipoBeneficiario();

            if (BeneficiariosConstantes.FORMATO_W8_BEN.equals(formato)
                    || BeneficiariosConstantes.FORMATO_W8_BEN2014.equals(formato)
                    || BeneficiariosConstantes.FORMATO_W8_BEN2017.equals(formato)) {
                this.forma =
                        new FormaW8BEN(custodio, tipoBeneficiario, null,
                                this.controlBeneficiariosService.getField3W8BEN());
            } else if (formato.equals("W8IMY")) {
                this.forma =
                        new FormaW8IMY(custodio, tipoBeneficiario, null,
                                this.controlBeneficiariosService.getField3W8IMY());
            } else if (formato.equals("W9")) {
                this.forma =
                        new FormaW9(custodio, tipoBeneficiario, null,
                                this.controlBeneficiariosService.getField3W9());
            } else if (formato.equals("MILA")) {
                this.forma =
                        new FormaMILA(custodio, tipoBeneficiario, nombreTipoBeneficiario, null,
                                true);
            } else if (formato.equals(BeneficiariosConstantes.FORMATO_W8_BEN_E)
                    || formato.equals(BeneficiariosConstantes.FORMATO_W8_BEN_E_2016)) {
                this.forma =
                        this.formatoW8Service.obtenerCamposFormatoW8BENE(beneficiario
                                .getFormatoW8BENE().getIdCamposFormatoW8bene());
            } else if (formato.equals(BeneficiariosConstantes.FORMATO_W8_IMY2015)) {
                this.forma =
                        this.formatoW8Service.obtenerCamposFormatoW8IMY2015(beneficiario
                                .getFormatoW8IMY2015().getIdCamposFormato());
            } else if (formato.equals(BeneficiariosConstantes.FORMATO_W8_IMY2017)) {
                this.forma =
                        this.formatoW8Service.obtenerCamposFormatoW8IMY2017(beneficiario
                                .getFormatoW8IMY2015().getIdCamposFormato());
            }

            this.forma.construyeBean(beneficiario, false);
            this.adp = this.forma.getAdp();
        }
    }

    public String guardar() {
        try {
            this.controlBeneficiariosService.asignaAdpBeneficiario(this.idBeneficiarioSelected,
                    this.adp);
            String message = "ADP asignado con exito";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, message, message));
            this.limpiar(null);
        } catch (EJBException ejbException) {
            log.error("ERROR", ejbException);
            this.trataExcepcion(ejbException);
        } catch (Throwable ex) {
            log.error("ERROR", ex);
        }
        return null;
    }

    /**
     * Inicializa mapa de custodios
     */
    private void inicializaMapaCustodios() {
        this.mapaCustodios = new HashMap<Long, String>();

        List<Object[]> lista = this.controlBeneficiariosService.obtieneCatBic();

        for (Object[] bene : lista) {
            this.mapaCustodios.put((Long) bene[0], (String) bene[1]);
        }
    }

    /**
     * Inicializa lista de custodios
     */
    private void inicializaTiposBeneficiarios() {
        this.mapaTipoBeneficiario = new HashMap<Long, String>();

        if (this.forma.getCustodio() != null && this.forma.getCustodio() > 0) {
            log.info("Custodio: [" + this.forma.getCustodio() + "]");
            List<Object[]> lista =
                    this.controlBeneficiariosService.obtieneTiposBeneficiario(this.forma
                            .getCustodio());

            for (Object[] bene : lista) {
                this.mapaTipoBeneficiario.put((Long) bene[0], ((String) bene[1]).toUpperCase());
            }
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
     * @return the idBeneficiarioSelected
     */
    public Long getIdBeneficiarioSelected() {
        return this.idBeneficiarioSelected;
    }

    /**
     * @param idBeneficiarioSelected the idBeneficiarioSelected to set
     */
    public void setIdBeneficiarioSelected(final Long idBeneficiarioSelected) {
        this.idBeneficiarioSelected = idBeneficiarioSelected;
    }

    public String getAdp() {
        return this.adp;
    }

    public void setAdp(final String adp) {
        this.adp = adp;
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
}

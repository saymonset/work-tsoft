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

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.formatosw.FormaGeneral;
import com.indeval.portaldali.middleware.formatosw.FormaW8BEN;
import com.indeval.portaldali.middleware.formatosw.FormaW8IMY;
import com.indeval.portaldali.middleware.formatosw.FormaW9;
import com.indeval.portaldali.middleware.services.util.ConsultaCatalogoService;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.FormatoFiscalService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.FormatoW8Service;
import com.indeval.portalinternacional.middleware.servicios.constantes.BeneficiariosConstantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.Domicilio;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * Controller para la Captura de Beneficiarios de Division Internacional
 * 
 * @author Rafael Ibarra
 * @version 1.0
 */
public class MostrarBeneficiarioActivoController extends ControllerBase {

    /** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(MostrarBeneficiarioActivoController.class);

    /** Servicio de Beneficiarios */
    private ControlBeneficiariosService controlBeneficiariosService;
    
    /** Servicio Formatos Fiscales */
    private FormatoFiscalService serviceFormatosFiscales;

    /** Mapa que contiene la lista de custodios para la tabla */
    private Map<Long, String> mapaCustodios;

    /** Mapa que contiene la lista de custodios para la tabla */
    private Map<Long, String> mapaTipoBeneficiario;

    /** Forma W8BEN para la pantalla */
    private FormaGeneral forma = new FormaGeneral();

    /** Servicio para obtener la institucion */
    private ConsultaCatalogoService consultaCatService;

    /** Formato */
    private String formato;

    /** ID Beneficiacrio */
    private Long idBeneficiarioSelected;

    /** Institucion seleccionada */
    private String idFolioInstitucion;

    /** Institucion seleccionada */
    private String nombreInstitucion;

    /** Mostar Forma de Guardar */
    private boolean mostrarFormaGuardar = false;

    /** Servicio para parsear el formato a xml y viceversa */
    private FormatoW8Service formatoW8Service;

    /** Objeto para capturar el domicilio del formato */
    private Domicilio domicilio;

    /** Objeto para capturar el domicilio de correo */
    private Domicilio domicilioCorreo;
    
    /** Check para saber si se genera TIN de cobro **/
    private Boolean isCobro;

    /**
     * Constructor de Captura Operaciones
     */
    public MostrarBeneficiarioActivoController() {

    }

    /**
     * Asigna las opciones predeterminadas para cuando se carga la pï¿½gina por primerva vez.
     * 
     * @return nulo, este mï¿½todo no requiere retornar un valor
     */
    public String getInit() {
    	
    	this.isCobro = Boolean.FALSE;

        if (!this.isInstitucionIndeval()) {
            AgenteVO agente = this.getAgenteFirmado();
            this.idFolioInstitucion = agente.getId() + agente.getFolio();
            this.nombreInstitucion = agente.getNombreCorto();
        }

        FacesContext facesContext = FacesContext.getCurrentInstance();
        this.formato = facesContext.getExternalContext().getRequestParameterMap().get("formato");
        String idBenefCadena =
                facesContext.getExternalContext().getRequestParameterMap().get("idBeneficiario");
        String messageError = "Hubo un error al recibir los parametros";
        try {
            this.idBeneficiarioSelected = Long.valueOf(idBenefCadena);
        } catch (Exception e) {
            log.error(messageError, e);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, messageError, messageError));
            return null;
        }

        if (StringUtils.isBlank(this.formato) || this.idBeneficiarioSelected == null
                || this.idBeneficiarioSelected.compareTo(1l) < 0) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, messageError, messageError));
            return null;
        }

        String mostrarGuardarCadena =
                facesContext.getExternalContext().getRequestParameterMap().get("idMuestraConsulta");
        try {
            if (StringUtils.isNotBlank(mostrarGuardarCadena)) {
                this.mostrarFormaGuardar = Boolean.valueOf(mostrarGuardarCadena);
            } else {
                this.mostrarFormaGuardar = false;
            }
        } catch (Exception e) {
            log.error("Error al convertir el Boolean: [" + this.mostrarFormaGuardar + "]", e);
        }

        Beneficiario beneficiario =
                this.controlBeneficiariosService
                        .consultaBeneficiarioByIdEliminados(this.idBeneficiarioSelected);
        if (beneficiario != null) {
            if (StringUtils.isNotBlank(beneficiario.getTipoFormato())
                    && this.formato.equals(beneficiario.getTipoFormato())) {
                Long custodio = beneficiario.getIdCuentaNombrada();
                Long tipoBeneficiario = beneficiario.getTipoBeneficiario().getIdTipoBeneficiario();

                if (this.formato.equals("W8BEN") || this.formato.equals("W8BEN2014")
                        || this.formato.equals("W8BEN2017")) {
                    this.forma =
                            new FormaW8BEN(custodio, tipoBeneficiario, null,
                                    this.controlBeneficiariosService.getField3W8BEN());
                } else if (BeneficiariosConstantes.FORMATO_W8_BEN_E.equals(this.formato)
                        || BeneficiariosConstantes.FORMATO_W8_BEN_E_2016.equals(this.formato)) {
                    this.forma =
                            this.formatoW8Service.obtenerCamposFormatoW8BENE(beneficiario
                                    .getFormatoW8BENE().getIdCamposFormatoW8bene());
                    this.forma.construyeBean(beneficiario, true);
                    // Obtiene los domicilios
                    this.domicilio = beneficiario.getDomicilioW8Normal();
                    this.domicilioCorreo = beneficiario.getDomicilioW8Correo();
                } else if (BeneficiariosConstantes.FORMATO_W8_IMY2015.equals(this.formato)) {
                    this.forma =
                            this.formatoW8Service.obtenerCamposFormatoW8IMY2015(beneficiario
                                    .getFormatoW8IMY2015().getIdCamposFormato());
                    this.forma.construyeBean(beneficiario, true);
                    // Obtiene los domicilios
                    this.domicilio = beneficiario.getDomicilioW8Normal();
                    this.domicilioCorreo = beneficiario.getDomicilioW8Correo();
                } else if (BeneficiariosConstantes.FORMATO_W8_IMY2017.equals(this.formato)) {
                    this.forma =
                            this.formatoW8Service.obtenerCamposFormatoW8IMY2017(beneficiario
                                    .getFormatoW8IMY2015().getIdCamposFormato());
                    this.forma.construyeBean(beneficiario, true);
                    // Obtiene los domicilios
                    this.domicilio = beneficiario.getDomicilioW8Normal();
                    this.domicilioCorreo = beneficiario.getDomicilioW8Correo();
                } else if (this.formato.equals("W8IMY")) {
                    this.forma =
                            new FormaW8IMY(custodio, tipoBeneficiario, null,
                                    this.controlBeneficiariosService.getField3W8IMY());
                } else if (this.formato.equals("W9")) {
                    this.forma =
                            new FormaW9(custodio, tipoBeneficiario, null,
                                    this.controlBeneficiariosService.getField3W9());
                }
                this.forma.construyeBean(beneficiario, true);
                this.inicializaMapaCustodios();
                this.inicializaTiposBeneficiarios();
            }
        }
        return null;
    }

    public void limpiar(final ActionEvent event) {
        this.idBeneficiarioSelected = null;
        this.forma = null;
        this.formato = null;
        this.idFolioInstitucion = null;
        this.nombreInstitucion = null;
        this.mostrarFormaGuardar = false;
    }

    public String guardar() {
        if (!this.isInstitucionIndeval()) {
            AgenteVO agente = this.getAgenteFirmado();
            this.idFolioInstitucion = agente.getId() + agente.getFolio();
            this.nombreInstitucion = agente.getNombreCorto();
        }
        try {
            Institucion institucion = null;
            if (StringUtils.isNotBlank(this.idFolioInstitucion)) {
                institucion =
                        this.consultaCatService
                                .findInstitucionByClaveFolio(this.idFolioInstitucion);
                if (institucion != null) {
                    this.forma.setIdInstitucion(institucion.getIdInstitucion());
/*                   if(this.isCobro != null){
                        this.controlBeneficiariosService.agregaBeneficiarioInstitucion(this.idBeneficiarioSelected, institucion.getIdInstitucion(), this.isCobro.booleanValue());	
                    }else{
                        this.controlBeneficiariosService.agregaBeneficiarioInstitucion(this.idBeneficiarioSelected, institucion.getIdInstitucion(), Boolean.FALSE);
                    }
*/
                    this.controlBeneficiariosService.agregaBeneficiarioInstitucion(this.idBeneficiarioSelected, institucion.getIdInstitucion());
                } else {
                    String message = "La institucion no se ha encontrado";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
                    return null;
                }

                // Aqui controlo el almacenamiento del TIN
/*                Beneficiario beneficiario = this.controlBeneficiariosService.consultaBeneficiarioById(this.idBeneficiarioSelected);
				List<FormatoFiscal> listFormatoFiscal = new ArrayList<FormatoFiscal>();
                FormatoFiscal formatoFiscal = serviceFormatosFiscales.prepareTinCobroInstitucion(beneficiario, institucion);
                listFormatoFiscal.add(formatoFiscal);                
                listFormatoFiscal = serviceFormatosFiscales.validateFormatosFiscales(listFormatoFiscal);
                if(listFormatoFiscal != null && listFormatoFiscal.size()>0){
                	if(this.isCobro != null && this.isCobro.booleanValue()){
                		serviceFormatosFiscales.generarTinCobro(listFormatoFiscal.get(0), Boolean.TRUE);
                	}else{
                		serviceFormatosFiscales.generarTinCobro(listFormatoFiscal.get(0), Boolean.FALSE);
                	}
                }
*/                
            } else {
                String message = "La institucion es invalida";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
                return null;
            }
            String message = "Beneficiario agregado con exito";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, message, message));
            this.limpiar(null);
            return "regresaMostrarBenefExito";
        } catch (EJBException ejbException) {
            log.error("ERROR", ejbException);
            this.trataExcepcion(ejbException);
        } catch (Throwable ex) {
            log.error("ERROR", ex);
        }
        return null;
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
     * Asigna valor del check para cobro de Tin
     * @param e
     */
/*	public void isEventoCorporativo(ActionEvent e) {
		if(this.isCobro.booleanValue()){
			this.isCobro = Boolean.TRUE;
		}else{
			this.isCobro = Boolean.FALSE;
		}
	}
*/
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
    @SuppressWarnings("unchecked")
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
	 * @param serviceFormatosFiscales the serviceFormatosFiscales to set
	 */
	public void setServiceFormatosFiscales(FormatoFiscalService serviceFormatosFiscales) {
		this.serviceFormatosFiscales = serviceFormatosFiscales;
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
     * @return the formato
     */
    public String getFormato() {
        return this.formato;
    }

    /**
     * @return the idBeneficiarioSelected
     */
    public Long getIdBeneficiarioSelected() {
        return this.idBeneficiarioSelected;
    }

    /**
     * @param formato the formato to set
     */
    public void setFormato(final String formato) {
        this.formato = formato;
    }

    /**
     * @param idBeneficiarioSelected the idBeneficiarioSelected to set
     */
    public void setIdBeneficiarioSelected(final Long idBeneficiarioSelected) {
        this.idBeneficiarioSelected = idBeneficiarioSelected;
    }

    /**
     * @return the idFolioInstitucion
     */
    public String getIdFolioInstitucion() {
        return this.idFolioInstitucion;
    }

    /**
     * @return the nombreInstitucion
     */
    public String getNombreInstitucion() {
        return this.nombreInstitucion;
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
     * @return the mostrarFormaGuardar
     */
    public boolean isMostrarFormaGuardar() {
        return this.mostrarFormaGuardar;
    }

    /**
     * @param mostrarFormaGuardar the mostrarFormaGuardar to set
     */
    public void setMostrarFormaGuardar(final boolean mostrarFormaGuardar) {
        this.mostrarFormaGuardar = mostrarFormaGuardar;
    }

    /**
     * Método para obtener el atributo mapaCustodios
     * 
     * @return El atributo mapaCustodios
     */
    public Map<Long, String> getMapaCustodios() {
        return this.mapaCustodios;
    }

    /**
     * Método para establecer el atributo mapaCustodios
     * 
     * @param mapaCustodios El valor del atributo mapaCustodios a establecer.
     */
    public void setMapaCustodios(final Map<Long, String> mapaCustodios) {
        this.mapaCustodios = mapaCustodios;
    }

    /**
     * Método para obtener el atributo mapaTipoBeneficiario
     * 
     * @return El atributo mapaTipoBeneficiario
     */
    public Map<Long, String> getMapaTipoBeneficiario() {
        return this.mapaTipoBeneficiario;
    }

    /**
     * Método para establecer el atributo mapaTipoBeneficiario
     * 
     * @param mapaTipoBeneficiario El valor del atributo mapaTipoBeneficiario a establecer.
     */
    public void setMapaTipoBeneficiario(final Map<Long, String> mapaTipoBeneficiario) {
        this.mapaTipoBeneficiario = mapaTipoBeneficiario;
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
	 * @return the isCobro
	 */
	public Boolean getIsCobro() {
		return isCobro;
	}

	/**
	 * @param isCobro the isCobro to set
	 */
	public void setIsCobro(Boolean isCobro) {
		this.isCobro = isCobro;
	}

}

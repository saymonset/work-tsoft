/**
 * 
 */
package com.indeval.portaldali.presentation.controller.efectivo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;

import com.bursatec.seguridad.presentation.constants.SeguridadConstants;
import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.DepositoDivisaDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.InfrastructureException;
import com.indeval.portaldali.presentation.common.constants.UtilConstantes;
import com.indeval.portaldali.presentation.controller.common.ControllerBase;
import com.indeval.portaldali.presentation.helper.IsoHelper;
import com.indeval.portaldali.presentation.util.CifradorDescifradorBlowfish;
import com.indeval.portaldali.presentation.util.ConsultaCatalogosFacade;

/**
 * @author enavarrete
 * 
 */
public class DepositoDivisaController extends ControllerBase {

	private String claveInstitucion;

	private String nombreCortoInstitucion;

	private ConsultaCatalogosFacade consultaCatalogosFacade = null;

	private BovedaDTO boveda;

	private BigDecimal importe;

	private DivisaDTO divisa;

	private BigDecimal saldo;

	private BigDecimal total;

	private EnvioOperacionesService envioOperacionesService;

	/** La cadena que contiene el iso firmado */
	protected String isoFirmado = "";

	/** Cadena Hash del ISO a firmar */
	protected String hashIso = null;
	
	/** EL número de serie asociado al iso firmado */
    protected String numeroSerie = "";
    
    /** La cadena que contiene el iso sin firmar */
    protected String isoSinFirmar = "";
    
    protected CifradorDescifradorBlowfish cdb = new CifradorDescifradorBlowfish();
    
	/** Ayudante para la generación de las cadenas ISO que deberán ser firmadas */
    protected IsoHelper isoHelper = null;

	public String getInit() {
		logger.info("Entrando a DepositoDivisaController");
		boveda = new BovedaDTO();
		boveda.setId(BovedaDTO.BOVEDA_BANXICO);
		divisa = new DivisaDTO(DivisaDTO.DIVISA_PESO_MX);
		return null;
	}

	public void validaInstitucion(ActionEvent event) {
		logger.info("Entrando a DepositoDivisaController.validaInstitucion");
		if (validaClaveInstitucion()) {
			InstitucionDTO inst = consultaCatalogosFacade
					.buscarInstitucionPorIdFolio(claveInstitucion);
			if (inst != null) {
				nombreCortoInstitucion = inst.getNombreCorto();
			} else {
				nombreCortoInstitucion = null;
				agregaMensajeInstitucionInvalida();
			}
		} else {
			nombreCortoInstitucion = null;
		}
		boveda = new BovedaDTO();
		boveda.setId(BovedaDTO.BOVEDA_BANXICO);
		saldo = null;
		importe = null;
		total = null;
		obtieneSaldo(event);
	}

	public void obtieneSaldo(ActionEvent event) {
		logger.info("Entrando a DepositoDivisaController.obtieneSaldo");
		importe = null;
		total = null;
		if (validaClaveInstitucion() && validaBovedaDTO() && validaDivisaDTO()) {
			BigDecimal saldoInt = consultaCatalogosFacade
					.getSaldoEfectivoBovedaDivisa(claveInstitucion.substring(0,
							2), claveInstitucion.substring(2), new BigInteger(
							String.valueOf(boveda.getId())), new BigInteger(
							String.valueOf(divisa.getId())));
			if (saldoInt != null) {
				saldo = saldoInt;
				return;
			} else {
				saldo = null;
			}
		}
	}

	public void pintaTotal(ActionEvent event) {
		if (saldo != null && importe != null) {
			total = saldo.add(importe, MathContext.UNLIMITED);
		} else {
			total = null;
		}
	}

	public void limpiar(ActionEvent event) {
		logger.info("Entrando a DepositoDivisaController.limpiar");
		claveInstitucion = "";
		nombreCortoInstitucion = "";
		boveda = new BovedaDTO();
		boveda.setId(-1);
		saldo = null;
		importe = null;
		total = null;
		divisa = null;
		
		isoFirmado = null;
        isoSinFirmar = ""; 
        numeroSerie = null;
        hashIso = null;
	}

	public void guardar(ActionEvent event) {
		try {				
			logger.info("Entrando a DepositoDivisaController.guardar");
			if (!validaClaveInstitucion()) {
				agregaMensajeInstitucionInvalida();
				return;
			}
			if (!validaBovedaDTO()) {
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Debes seleccionar una boveda",
						"Debes seleccionar una boveda");
				FacesContext.getCurrentInstance().addMessage(null, message);
				return;
			}

			if (!validaDivisaDTO()) {
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Divisa incorrecta",
						"Divisa incorrecta");
				FacesContext.getCurrentInstance().addMessage(null, message);
				return;
			}

			if (saldo == null) {
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Saldo incorrecto",
						"Saldo incorrecto");
				FacesContext.getCurrentInstance().addMessage(null, message);
				return;
			}

			if (!validaImporte()) {
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Importe incorrecto",
						"Importe incorrecto");
				FacesContext.getCurrentInstance().addMessage(null, message);
				return;
			}

			logger.info("Va a guardar.");	
			
			DepositoDivisaDTO depositoDivisa = new DepositoDivisaDTO();
			boolean procesado = false;
			
			InstitucionDTO institucionDTO= consultaCatalogosFacade.buscarInstitucionPorIdFolio(claveInstitucion);

			depositoDivisa.setClaveInstitucion(claveInstitucion);
			depositoDivisa.setNombreCorto(nombreCortoInstitucion);
			depositoDivisa.setCasfim(institucionDTO.getClaveCasfim());
					
			BovedaDTO bovedaDTO=consultaCatalogosFacade.buscarBovedaPorId(boveda.getId());
			DivisaDTO divisaDTO=consultaCatalogosFacade.buscarDivisaPorId(divisa.getId());
				
			depositoDivisa.setIdBoveda(new BigInteger(String.valueOf(boveda.getId())));
			depositoDivisa.setBoveda(bovedaDTO.getNombreCorto());		
			depositoDivisa.setIdDivisa(new BigInteger(String.valueOf(divisa.getId())));
			depositoDivisa.setDivisa(divisaDTO.getClaveAlfabetica());
			depositoDivisa.setImporte(importe);
			depositoDivisa.setSaldo(saldo);
					

			final Map<String, Object> datosAdicionales = new HashMap<String, Object>();
			datosAdicionales.put(SeguridadConstants.USR_CREDENCIAL, (String) FacesContext.getCurrentInstance().getExternalContext()
							.getSessionMap().get(SeguridadConstants.TICKET_SESION));
	 
			if (isUsuarioConFacultadFirmar()) {
				//Valida la vigencia del certificado - 18-Agosto-2014 Pablo Balderas
				validarVigenciaCertificado();
				// Si el usuario debe firmar la operacion, se recuperar la firma.
				recuperarCamposFirma();
				 
				if (!StringUtils.isEmpty(isoFirmado)) {				
					
					if (!cdb.validation(hashIso, isoSinFirmar)) {
						throw new InfrastructureException(UtilConstantes.ERROR_ISO_DIFERENTE);
					}
					isoFirmado = (new StringBuilder()).append(isoSinFirmar)
							.append(numeroSerie).append("\n")
							.append("{SHA1withRSA}").append(isoFirmado).toString();
					
					envioOperacionesService.grabaOperacion(depositoDivisa, datosAdicionales, isoFirmado);
					procesado = true;
				} else {

					isoSinFirmar = isoHelper.creaISO(depositoDivisa, false );
					hashIso = cdb.cipherHash(isoSinFirmar);
				}

			} else {			
				envioOperacionesService.grabaOperacion(depositoDivisa, datosAdicionales, null);
				procesado = true;
			}

			
			if (!existeErrorEnInvocacion() && procesado ) {
				limpiar(event);

				String exito = "Operaci\u00F3n guardada con \u00E9xito";
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
						exito, exito);
				FacesContext.getCurrentInstance().addMessage(null, message);
			}
		}
		catch(BusinessException businessException) {
    		addMessage(businessException.getMessage(), FacesMessage.SEVERITY_ERROR);
    		return;
    	}
	}

	 

	/**
	 * Recupera la informacion de los campos ocultos de firmas electronicas
	 */
	private void recuperarCamposFirma() {
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		isoFirmado = params.get("isoFirmado").replace("\r\n", "\n");
		hashIso = params.get("hashIso").replace("\r\n", "\n");
		isoSinFirmar = params.get("isoSinFirmar").replace("\r\n", "\n");
		numeroSerie = params.get("numeroSerie").replace("\r\n", "\n");

	}
	
	 /**
     * Indica si ya se firmó el ISO en pantalla.
     * 
     * @return <code>true</code> si se firmó el ISO en pantalla.
     */
    public boolean isIsoYaFirmado() {
    	
    	return !StringUtils.isEmpty(isoSinFirmar);
    }

    /**
     * 
     * @return
     */
	private boolean validaClaveInstitucion() {
		if (StringUtils.isNotBlank(claveInstitucion)
				&& claveInstitucion.length() == 5
				&& StringUtils.isNumeric(claveInstitucion)) {
			return true;
		}
		return false;
	}

	private boolean validaBovedaDTO() {
		if (boveda != null && boveda.getId() > 0) {
			return true;
		}
		return false;
	}

	private boolean validaDivisaDTO() {
		if (divisa != null && divisa.getId() > 0) {
			return true;
		}
		return false;
	}

	private boolean validaImporte() {
		if (importe != null && importe.compareTo(BigDecimal.ZERO) > 0) {
			return true;
		}
		return false;
	}

	private void agregaMensajeInstitucionInvalida() {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"Institucion invalida", "Institucion invalida");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	/**
	 * @return the claveInstitucion
	 */
	public String getClaveInstitucion() {
		return claveInstitucion;
	}

	/**
	 * @param claveInstitucion
	 *            the claveInstitucion to set
	 */
	public void setClaveInstitucion(String claveInstitucion) {
		this.claveInstitucion = claveInstitucion;
	}

	/**
	 * @return the nombreCortoInstitucion
	 */
	public String getNombreCortoInstitucion() {
		return nombreCortoInstitucion;
	}

	/**
	 * @param nombreCortoInstitucion
	 *            the nombreCortoInstitucion to set
	 */
	public void setNombreCortoInstitucion(String nombreCortoInstitucion) {
		this.nombreCortoInstitucion = nombreCortoInstitucion;
	}

	/**
	 * @param consultaCatalogosFacade
	 *            the consultaCatalogosFacade to set
	 */
	public void setConsultaCatalogosFacade(
			ConsultaCatalogosFacade consultaCatalogosFacade) {
		this.consultaCatalogosFacade = consultaCatalogosFacade;
	}

	/**
	 * @param boveda
	 *            the boveda to set
	 */
	public void setBoveda(BovedaDTO boveda) {
		this.boveda = boveda;
	}

	/**
	 * @return the boveda
	 */
	public BovedaDTO getBoveda() {
		return boveda;
	}

	/**
	 * @param importe
	 *            the importe to set
	 */
	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	/**
	 * @return the importe
	 */
	public BigDecimal getImporte() {
		return importe;
	}

	/**
	 * @param divisa
	 *            the divisa to set
	 */
	public void setDivisa(DivisaDTO divisa) {
		this.divisa = divisa;
	}

	/**
	 * @return the divisa
	 */
	public DivisaDTO getDivisa() {
		return divisa;
	}

	/**
	 * @param saldo
	 *            the saldo to set
	 */
	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	/**
	 * @return the saldo
	 */
	public BigDecimal getSaldo() {
		return saldo;
	}

	/**
	 * @param envioOperacionesService
	 *            the envioOperacionesService to set
	 */
	public void setEnvioOperacionesService(
			EnvioOperacionesService envioOperacionesService) {
		this.envioOperacionesService = envioOperacionesService;
	}

	/**
	 * @param total
	 *            the total to set
	 */
	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	/**
	 * @return the total
	 */
	public BigDecimal getTotal() {
		return total;
	}

	/**
	 * @return the isoFirmado
	 */
	public String getIsoFirmado() {
		return isoFirmado;
	}

	/**
	 * @param isoFirmado
	 *            the isoFirmado to set
	 */
	public void setIsoFirmado(String isoFirmado) {
		this.isoFirmado = isoFirmado;
	}

	/**
	 * @return the hashIso
	 */
	public String getHashIso() {
		return hashIso;
	}

	/**
	 * @param hashIso the hashIso to set
	 */
	public void setHashIso(String hashIso) {
		this.hashIso = hashIso;
	}

	/**
	 * @return the numeroSerie
	 */
	public String getNumeroSerie() {
		return numeroSerie;
	}

	/**
	 * @param numeroSerie the numeroSerie to set
	 */
	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}

	/**
	 * @return the isoSinFirmar
	 */
	public String getIsoSinFirmar() {
		return isoSinFirmar;
	}

	/**
	 * @param isoSinFirmar the isoSinFirmar to set
	 */
	public void setIsoSinFirmar(String isoSinFirmar) {
		this.isoSinFirmar = isoSinFirmar;
	}

	/**
	 * @return the isoHelper
	 */
	public IsoHelper getIsoHelper() {
		return isoHelper;
	}

	/**
	 * @param isoHelper the isoHelper to set
	 */
	public void setIsoHelper(IsoHelper isoHelper) {
		this.isoHelper = isoHelper;
	}

}

/*
 *Copyright (c) 2005-2007 Bursatec. All Rights Reserved
 */
package com.indeval.portalinternacional.presentation.controller;

import java.math.BigInteger;
import java.util.HashMap;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.seguridad.presentation.constants.SeguridadConstants;
import com.indeval.portaldali.middleware.services.util.ConsultaCatalogoService;
import com.indeval.portaldali.middleware.services.util.DateUtilService;
import com.indeval.portaldali.middleware.services.util.UtilService;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.InfrastructureException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.dao.common.CuentaNombradaDao;
import com.indeval.portaldali.persistence.dao.common.EmisionDao;
import com.indeval.portaldali.persistence.modelo.CuentaNombrada;
import com.indeval.portaldali.persistence.modelo.Emision;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portaldali.persistence.modelo.PosicionNombrada;
import com.indeval.portalinternacional.common.util.AgenteViewHelper;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.vo.GrabaOperacionParams;
import com.indeval.portalinternacional.presentation.controller.common.CapturaOperacionesController;
import com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO;

/**
 * Controller de la Captura de Operaciones para la opción de Captura de
 * Traspasos.
 * 
 * @author Marcos Rivas
 * 
 * @version 1.0
 */
public class CapturaTraspasosController extends CapturaOperacionesController {

	private String cuentaTraspasante;
	private String nombreTraspasante;
	private String idReceptor;
	private String folioReceptor;
	private String idTraspasante;
	private String tenenciaTraspasante;
	private String tenenciaReceptor;
	private String folioTraspasante;
	private String cuentaReceptor;
	private String nombreReceptor;
	private String alta;
	private String nombreCuenta;
	private Long saldoDisponible;
	private Long saldoActual;
	private String descripcion;
	/** Dao que obtiene la Cuenta Nombrada */
	private CuentaNombradaDao cuentaNombradaDao;

	/** Servicio para obtener la institucion */
	private ConsultaCatalogoService consultaCatalogoService;

	private DivisionInternacionalService divisionInternacionalService;
	private TraspasoLibrePagoVO tlp = new TraspasoLibrePagoVO();
	// private CapturaTraspasoParams params;

	private String mensaje;
	private String mensajeErr;

	private DateUtilService dateUtilService;
	private UtilService utilService;
	/** Dao para obtener las emisiones */
	private EmisionDao emisionDao;

	boolean confirmar = false;

	/** Descripcion de la boveda */
	private String descripcionBoveda;
	
	private String nombreCuentaRec;

	/** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(CapturaTraspasosController.class);

	/**
	 * Cadena estatica para obtener la referencia del mensaje
	 */
	static final String SEQ_REFERENCIA_MENSAJE = "SEQ_REFERENCIA_MENSAJE";

	

	public String[] consultaNombre(String id, String folio, String cuenta) {
		String[] resultado = new String[2];
		resultado[0] = "";
		resultado[1] = "";

		// if( id != null && folio != null && cuenta != null &&
		// !id.equals("") && !folio.equals("") && !cuenta.equals("")) {
		// try {
		// resultado = tenenciaCapOperDao.getTenenciaInstitucion(id, folio,
		// cuenta);
		// return resultado;
		// }catch (Exception e){
		// e.printStackTrace();
		// return resultado;
		// }
		// }
		return resultado;
	}

	/**
	 * Cancela la operación
	 * 
	 * @return
	 */
	public String cancelar() {
		limpiaForma();

		confirmar = false;
		return "limpiaCaptura";
	}

	public void realizarCalculoSimulado(ActionEvent e) {
		if (saldoDisponible != null && tlp.getCantidadTitulos() != null) {
			saldoActual = saldoDisponible.longValue()
					- tlp.getCantidadTitulos().longValue();
			if (tlp.getCantidadTitulos().longValue() <= 0) {
				addErrorMessage("La Cantidad Operada no puede ser menor o igual que 0");
			}
			if (tlp.getCantidadTitulos().longValue() > saldoDisponible) {
				addErrorMessage("La Cantidad Operada es Mayor al Saldo Actual");
			}
		}
	}

	/**
	 * Incializa los datos de cuentas e instituciones
	 * 
	 * @return
	 */
	public String getInicializar() {
		try {
			
			//idTraspasante = getAgenteFirmado().getId()
			//		+ getAgenteFirmado().getFolio();
			
			tenenciaReceptor = "";
			nombreCuentaRec = "";
			nombreCuenta = "";
			tenenciaTraspasante = "";
			// Se obtiene el nombre de la institución
			if (idTraspasante == null || StringUtils.isEmpty(idTraspasante)) {
				if (getAgenteFirmado() != null
						&& getAgenteFirmado().getId() != null
						&& getAgenteFirmado().getFolio() != null) {
					idTraspasante = AgenteViewHelper
							.obtenerClaveTipoInstitucionYFolio(
									getAgenteFirmado().getId(),
									getAgenteFirmado().getFolio());
				}
			}
			// Se obtiene el nombre de la institucion
			if (StringUtils.isNotBlank(idTraspasante)) {
				Institucion ins = consultaCatalogoService
						.findInstitucionByClaveFolio(idTraspasante);

				if (ins != null) {
					nombreTraspasante = consultaCatalogoService
							.findInstitucionByClaveFolio(idTraspasante)
							.getNombreCorto();
				}
			}
			// Se obtiene la tenencia del Traspasante y el nombre de la cuenta
			if (StringUtils.isNotBlank(idTraspasante)
					&& StringUtils.isNotBlank(cuentaTraspasante)) {

				Institucion ins = consultaCatalogoService
						.findInstitucionByClaveFolio(idTraspasante);

				if (ins != null) {
					CuentaNombrada cuenta = cuentaNombradaDao
							.findCuenta(AgenteViewHelper.crearAgenteVO(
									idTraspasante, cuentaTraspasante));
					
					if (cuenta != null) {
						tenenciaTraspasante = cuenta.getTipoCuenta()
								.getClaveTipoCuenta();
						
						nombreCuenta = cuenta.getNombreCuenta();
					}
				}else{
					addErrorMessage("Error en la Clave del Agente Traspasante");
				}

			}
			// se obtiene los datos del receptor
			if (StringUtils.isNotBlank(idReceptor)) {
				Institucion receptor = consultaCatalogoService
						.findInstitucionByClaveFolio(idReceptor);
				if (receptor != null) {
					nombreReceptor = receptor.getNombreCorto();
				}
			}
			//se obtiene la tenencia del receptor
			if (StringUtils.isNotBlank(idReceptor)
					&& StringUtils.isNotEmpty(cuentaReceptor)) {
				Institucion ins = consultaCatalogoService
						.findInstitucionByClaveFolio(idReceptor);
				// validar si la institucion es nula para poder 
				//obtener custodios y depositantes
				if (ins != null) {					
					CuentaNombrada cuenta = cuentaNombradaDao
							.findCuenta(AgenteViewHelper.crearAgenteVO(
									idReceptor, cuentaReceptor));
					if (cuenta != null) {
						tenenciaReceptor = cuenta.getTipoCuenta()
								.getClaveTipoCuenta();
						
						nombreCuentaRec = cuenta.getNombreCuenta();
					}
				}else{
					addErrorMessage("Error en la Clave del Agente Receptor");
				}

			}
			
			// obtener los datos de la posicion
			getBuscarEmisionPorIsinCuenta();
		} catch (BusinessException e) {
			log.error("Ocurrio un error",e);
			agregarMensaje(e);
			super.limpiarCampos();
		} catch (Exception e) {
			log.error("Ocurrio un error",e);
			agregarMensaje(new BusinessException(e.getCause().getMessage()));
			super.limpiarCampos();
		} catch (Throwable e) {
			log.error("Ocurrio un error",e);
			agregarMensaje(e);
			super.limpiarCampos();
		}

		return null;
	}

	public void errorCantidad(ActionEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage("cantidad", new FacesMessage(
				"Cantidad no puede ser menor que 0, ni mayor al saldo Actual"));
		nombreTraspasante = "nuevo valor";
	}

	public void errorSaldoDisponible(ActionEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage("saldoDisponible", new FacesMessage(
				"El Saldo Disponible no puede ser menor que el saldo Actual"));
	}

	
	/**
	 * 
	 * @param event
	 */
	public void guardar(ActionEvent event) {
		//Valida si puede realizar la operación
		if (!validarCuentaReceptora(cuentaReceptor, tlp.getCantidadTitulos() != null ? tlp.getCantidadTitulos().toString() : "")) {
			addMessage("La cantidad operada no puede superar el tope del fideicomiso");
		}
		else {
			confirmar = true;
			confirmarTraspaso(event);
		}
	}

	/**
	 * Una vez que el usuario confirma los datos se procede a validar la
	 * información y a grabar la operación o firmarla según corresponda
	 * 
	 * @param event
	 */
	public void confirmarTraspaso(ActionEvent event) {
        tlp.setTipoValor(StringUtils.upperCase(tlp.getTipoValor()));
        tlp.setEmisora(StringUtils.upperCase(tlp.getEmisora()));
        tlp.setSerie(StringUtils.upperCase(tlp.getSerie()));
        tlp.setCupon(StringUtils.upperCase(tlp.getCupon()));

		try {
			if (conFirma()) {
				//Valida la vigencia del certificado digital - 20/01/2015 Pablo Balderas
				validarVigenciaCertificado();
				recuperarCamposFirma();
				if (StringUtils.isEmpty(isoFirmado)) {
					validar();
					isoSinFirmar = isoHelper.creaISO(tlp, false);
					hashIso = cdb.cipherHash(isoSinFirmar);
				}
				else {
					if (!cdb.validation(hashIso, isoSinFirmar)) {
						throw new InfrastructureException(Constantes.ERROR_ISO_DIFERENTE);
					}
					grabarOperacion();
					limpiar(event);
                    getInicializar();
				}
			}
			//El usuario no firma operación
			else {
				validar();
				grabarOperacion();
				limpiar(event);
                getInicializar();
			}
		}
		catch (BusinessException e) {
			log.error("Ocurrio un error",e);
			agregarMensaje(e);
			super.limpiarCampos();
		}
		catch (Exception e) {
			log.error("Ocurrio un error",e);
			agregarMensaje(e);
			super.limpiarCampos();
		}
		catch (Throwable e) {
			log.error("Ocurrio un error",e);
			agregarMensaje(e);
			super.limpiarCampos();
		}

	}

	/**
	 * ActionListener para los datos de la emision a partir de la cuenta y el
	 * isin.
	 * 
	 * @param event
	 *            el evento que dispar este ActionListener
	 */
	public String getBuscarEmisionPorIsinCuenta() {
		if ((StringUtils.isNotEmpty(cuentaTraspasante) && StringUtils
				.isNotEmpty(tlp.getISIN()))
				|| (StringUtils.isNotEmpty(cuentaTraspasante)
						&& StringUtils.isNotEmpty(tlp.getTipoValor())
						&& StringUtils.isNotEmpty(tlp.getEmisora()) && StringUtils
						.isNotEmpty(tlp.getSerie()))) {
			AgenteVO agente = new AgenteVO();
			EmisionVO emision = new EmisionVO();
			Institucion institucion = null;
			if (idTraspasante != null) {
				institucion = consultaCatalogoService
						.findInstitucionByClaveFolio(idTraspasante);
			}
			if (institucion != null) {
				agente.setId(institucion.getTipoInstitucion()
						.getClaveTipoInstitucion());
				agente.setFolio(institucion.getFolioInstitucion());

				emision.setTv(StringUtils.isEmpty(tlp.getTipoValor()) ? null : tlp
						.getTipoValor());

				emision.setEmisora(StringUtils.isEmpty(tlp.getEmisora()) ? null
						: tlp.getEmisora());

				emision.setSerie(StringUtils.isEmpty(tlp.getSerie()) ? null : tlp
						.getSerie());

				emision.setIsin(StringUtils.isEmpty(tlp.getISIN()) ? null : tlp
						.getISIN());

				emision.setCupon(StringUtils.isEmpty(tlp.getCupon()) ? null : tlp
						.getCupon());

				// se llena el criterio de cuenta para la busqueda
				agente.setCuenta(StringUtils.isEmpty(cuentaTraspasante) ? null
						: cuentaTraspasante);

				PaginaVO pgTmp = consultaCatalogoService
						.buscarListaPosicionesNombradasDeParticipante(agente,
								emision, paginaVO);
				if (pgTmp != null) {
					paginaVO = pgTmp;
					if (!paginaVO.getRegistros().isEmpty()
							) {
						PosicionNombrada posicion = new PosicionNombrada();
						posicion = (PosicionNombrada) paginaVO.getRegistros()
								.get(0);
						cuentaTraspasante = posicion.getCuentaNombrada()
								.getCuenta() != null ? posicion.getCuentaNombrada()
								.getCuenta() : "";
						tenenciaTraspasante = posicion.getCuentaNombrada()
								.getTipoCuenta().getClaveTipoCuenta() != null ? posicion
								.getCuentaNombrada().getTipoCuenta()
								.getClaveTipoCuenta()
								: "";
						tlp
								.setTipoValor(posicion.getCupon().getEmision()
										.getInstrumento().getClaveTipoValor() != null ? posicion
										.getCupon().getEmision().getInstrumento()
										.getClaveTipoValor()
										: "");
						tlp.setEmisora(posicion.getCupon().getEmision()
								.getEmisora().getClavePizarra() != null ? posicion
								.getCupon().getEmision().getEmisora()
								.getClavePizarra() : "");
						tlp
								.setSerie(posicion.getCupon().getEmision()
										.getSerie() != null ? posicion.getCupon()
										.getEmision().getSerie() : "");
						tlp
								.setCupon(posicion.getCupon().getClaveCupon() != null ? posicion
										.getCupon().getClaveCupon()
										: "");
						tlp
								.setISIN(posicion.getCupon().getEmision().getIsin() != null ? posicion
										.getCupon().getEmision().getIsin()
										: "");
						descripcionBoveda = (posicion.getBoveda().getDescripcion() != null ? posicion
								.getBoveda().getDescripcion()
								: "");

//						tlp
//								.setBoveda(posicion.getBoveda().getIdBoveda() != null ? posicion
//										.getBoveda().getIdBoveda().toString()
//										: "");

						alta = (posicion.getCupon().getEmision()
								.getEmisionExtranjera() != null ? posicion
								.getCupon().getEmision().getEmisionExtranjera()
								: "");

						saldoDisponible = (posicion.getPosicionDisponible()
								.longValue() > 0 ? posicion.getPosicionDisponible()
								.longValue() : 0);

					}

				}
			}
		} else {
			if (StringUtils.isNotEmpty(tlp.getISIN())) {
				EmisionVO criterio = new EmisionVO();
				criterio.setIsin(tlp.getISIN());
				Emision emisionModel = emisionDao.findEmision(criterio);
				if (emisionModel != null) {
					tlp.setTipoValor(emisionModel.getInstrumento()
							.getClaveTipoValor() != null ? emisionModel
							.getInstrumento().getClaveTipoValor() : "");
					tlp
							.setEmisora(emisionModel.getEmisora()
									.getClavePizarra() != null ? emisionModel
									.getEmisora().getClavePizarra() : "");
					tlp.setSerie(emisionModel.getSerie() != null ? emisionModel
							.getSerie() : "");
					tlp.setISIN(emisionModel.getIsin() != null ? emisionModel
							.getIsin() : "");
//					tlp
//							.setBoveda(emisionModel.getDivisa().getIdDivisa() != null ? emisionModel
//									.getDivisa().getIdDivisa().toString()
//									: "");
				}
			}
		}
		return null;
	}

	/**
	 * Almacena la información de la operación
	 */
	private void grabarOperacion() {
		GrabaOperacionParams gop = new GrabaOperacionParams();
		gop.setTraspasoLibrePagoVO(tlp);
		gop.setRecepcion(false);
		gop.setOrigenRegistro("PORTAL");
			
		
		if (conFirma()) {
			
			isoSinFirmar = isoSinFirmar.replace("\r\n", "\n");
			
			isoFirmado = (new StringBuilder()).append(isoSinFirmar)
					.append(numeroSerie).append("\n").append(
							"{SHA1withRSA}").append(isoFirmado).toString();
			gop.setIsoFirmado(isoFirmado);
		} else {
			gop.setIsoFirmado(null);
		}
		
		HashMap<String, String> datosAdicionales = new HashMap<String, String>();
		
		datosAdicionales.put(SeguridadConstants.USR_CREDENCIAL, 
				(String)FacesContext.getCurrentInstance().getExternalContext().
				getSessionMap().get(SeguridadConstants.TICKET_SESION));	

		gop.setDatosAdicionales(datosAdicionales);
				
		divisionInternacionalService.grabaOperacion(gop);

		agregarInfoMensaje("La operaci\u00f3n se llev\u00f3 a cabo exitosamente. Folio de la operaci\u00f3n : "
				+ folioAsignado);

	}

	/**
	 * Realiza validaciones de negocio y la firma del ISO del TLP respectivo
	 */
	private void validar(){

		folioAsignado = null;

		if (idTraspasante == null) {
			idTraspasante = "";
		}
		if (folioTraspasante == null) {
			folioTraspasante = "";
		}
		if (cuentaTraspasante == null) {
			cuentaTraspasante = "";
		}
		if (idReceptor == null) {
			idReceptor = "";
		}
		if (folioReceptor == null) {
			folioReceptor = "";
		}

		if (alta == null) {
			alta = "";
		}

		AgenteVO traspasante = AgenteViewHelper.crearAgenteVO(idTraspasante,
				cuentaTraspasante);
		AgenteVO receptor = AgenteViewHelper.crearAgenteVO(idReceptor,
				cuentaReceptor);

		creaTLPVO(traspasante, receptor);

		// EmisionVO emision = new EmisionVO();
		// emision.setAlta( alta );
		// emision.setCupon( tlp.getCupon());
		// emision.setDiasVigentes( null );
		// emision.setEmisora( tlp.getEmisora() );
		// emision.setSerie(tlp.getSerie());
		// emision.setFechaVencimiento( null );
		// emision.setTv( tlp.getTipoValor() );
		// emision.setIsin(tlp.getISIN());
		// emision.setMercado( null );
		// emision.setPrecioVector( null );
		// emision.setSaldoDisponible(new BigDecimal(saldoDisponible));
		
			BigInteger folioInt = divisionInternacionalService
					.businessRulesCapturaTraspaso(tlp);

			if (folioInt != null) {

				folioAsignado = folioInt.toString();
				BigInteger referenciaMensaje = utilService
						.getFolio(SEQ_REFERENCIA_MENSAJE);
				tlp.setReferenciaMensaje(referenciaMensaje.toString());
				tlp.setReferenciaOperacion(folioAsignado);
				tlp.setCliente("");
			}
	}

	/**
	 * Crea un TraspasoContraPagoVO con base a los elementos de la pantalla para
	 * invocar al metodo de graba operacion
	 * 
	 * @param traspasante
	 *            Datos del traspasante
	 * @param receptor
	 *            Datos del receptor
	 * @return objeto inicializado con los valores de la pantalla
	 */
	private void creaTLPVO(AgenteVO traspasante, AgenteVO receptor) {

		tlp.setIdFolioCtaReceptora(receptor.getId() + receptor.getFolio()
				+ receptor.getCuenta());
		tlp.setIdFolioCtaTraspasante(traspasante.getId()
				+ traspasante.getFolio() + traspasante.getCuenta());
		tlp.setTipoInstruccion("T");
		tlp.setFechaRegistro(dateUtilService.getCurrentDate());
		tlp.setFechaLiquidacion(dateUtilService.getCurrentDate());

	}

	// private CapturaTraspasoParams creaTraspasoParams() {
	// CapturaTraspasoParams retValue = new CapturaTraspasoParams();
	//		
	// EmisionVO emision = new EmisionVO();
	// emision.setIdTipoValor(tipoValor);
	// emision.setEmisora(emisora);
	// emision.setSerie(serie);
	// emision.setCupon(cupon);
	// emision.setIsin(isin);
	//		
	// AgenteVO parTraspasante = new AgenteVO(traspasante.getId(),
	// traspasante.getFolio(), cuentaTraspasante);
	// parTraspasante.setNombreCorto(nombreTraspasante);
	//		
	// AgenteVO parReceptor = new AgenteVO(idReceptor, folioReceptor,
	// cuentaReceptor);
	// parReceptor.setNombreCorto(nombreReceptor);
	//		
	// retValue.setAgenteTraspasante(parTraspasante);
	// retValue.setAgenteReceptor(parReceptor);
	// retValue.setCantidadTitulos(new BigInteger(cantidad.replaceAll(",",
	// "")));
	// retValue.setEmision(emision);
	// retValue.setIdBoveda(boveda);
	//		
	// return retValue;
	// }

	public void validateCuentaTraspasante(FacesContext context,
			UIComponent toValidate, Object value) {

	}

	public void aceptar(ActionEvent event) {

	}

	public void aceptarX(ActionEvent event) {

	}

	/**
	 * Borra la forma
	 * 
	 * @param event
	 */
	public void limpiar(ActionEvent e) {

		tlp = new TraspasoLibrePagoVO();
		// params = new CapturaTraspasoParams( );
		limpiaForma();
		confirmar = false;
		FacesContext context = FacesContext.getCurrentInstance();
		context.renderResponse();
		super.limpiarCampos();
		
	}

	public void limpiaForma() {
		idTraspasante="";
		cuentaTraspasante = "";
		nombreTraspasante = "";
		idReceptor = "";
		folioReceptor = "";
		cuentaReceptor = "";
		nombreReceptor = "";
		tenenciaReceptor = "";
		tenenciaTraspasante = "";
		tlp = new TraspasoLibrePagoVO();
		tlp.setCantidadTitulos(new Long("0"));
		saldoDisponible = null;
		saldoActual = null;
		descripcion = "";
		descripcionBoveda = "";
		alta = "";
		nombreCuenta = "";
		nombreCuentaRec = "";
		super.limpiarCampos();
	}
		
	private boolean validarCuentaReceptora(String cuenta, String cantidad) {
		// boolean x = false;
		return true;
	}

	/**
	 * @return the cuentaTraspasante
	 */
	public String getCuentaTraspasante() {
		return cuentaTraspasante;
	}

	/**
	 * @param cuentaTraspasante
	 *            the cuentaTraspasante to set
	 */
	public void setCuentaTraspasante(String cuentaTraspasante) {
		this.cuentaTraspasante = cuentaTraspasante;
	}

	/**
	 * @return the nombreTraspasante
	 */
	public String getNombreTraspasante() {
		return nombreTraspasante;
	}

	/**
	 * @param nombreTraspasante
	 *            the nombreTraspasante to set
	 */
	public void setNombreTraspasante(String nombreTraspasante) {
		this.nombreTraspasante = nombreTraspasante;
	}

	/**
	 * @return the idReceptor
	 */
	public String getIdReceptor() {
		return idReceptor;
	}

	/**
	 * @param idReceptor
	 *            the idReceptor to set
	 */
	public void setIdReceptor(String idReceptor) {
		this.idReceptor = idReceptor;
	}

	/**
	 * @return the folioReceptor
	 */
	public String getFolioReceptor() {
		return folioReceptor;
	}

	/**
	 * @param folioReceptor
	 *            the folioReceptor to set
	 */
	public void setFolioReceptor(String folioReceptor) {
		this.folioReceptor = folioReceptor;
	}

	/**
	 * @return the cuentaReceptor
	 */
	public String getCuentaReceptor() {
		return cuentaReceptor;
	}

	/**
	 * @param cuentaReceptor
	 *            the cuentaReceptor to set
	 */
	public void setCuentaReceptor(String cuentaReceptor) {
		this.cuentaReceptor = cuentaReceptor;
	}

	/**
	 * @return the nombreReceptor
	 */
	public String getNombreReceptor() {
		return nombreReceptor;
	}

	/**
	 * @param nombreReceptor
	 *            the nombreReceptor to set
	 */
	public void setNombreReceptor(String nombreReceptor) {
		this.nombreReceptor = nombreReceptor;
	}

	/**
	 * @return the alta
	 */
	public String getAlta() {
		return alta;
	}

	/**
	 * @param alta
	 *            the alta to set
	 */
	public void setAlta(String alta) {
		this.alta = alta;
	}

	/**
	 * @return the saldoDisponible
	 */
	public Long getSaldoDisponible() {
		return saldoDisponible;
	}

	/**
	 * @param saldoDisponible
	 *            the saldoDisponible to set
	 */
	public void setSaldoDisponible(Long saldoDisponible) {
		this.saldoDisponible = saldoDisponible;
	}

	/**
	 * @return the saldoActual
	 */
	public Long getSaldoActual() {
		return saldoActual;
	}

	/**
	 * @param saldoActual
	 *            the saldoActual to set
	 */
	public void setSaldoActual(Long saldoActual) {
		this.saldoActual = saldoActual;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the usuario
	 */

	/**
	 * @return the divisionInternacionalService
	 */
	public DivisionInternacionalService getDivisionInternacionalService() {
		return divisionInternacionalService;
	}

	/**
	 * @param divisionInternacionalService
	 *            the divisionInternacionalService to set
	 */
	public void setDivisionInternacionalService(
			DivisionInternacionalService divisionInternacionalService) {
		this.divisionInternacionalService = divisionInternacionalService;
	}

	public TraspasoLibrePagoVO getTlp() {
		return tlp;
	}

	public void setTlp(TraspasoLibrePagoVO tlp) {
		this.tlp = tlp;
	}

	public DateUtilService getDateUtilService() {
		return dateUtilService;
	}

	public void setDateUtilService(DateUtilService dateUtilService) {
		this.dateUtilService = dateUtilService;
	}

	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * @param mensaje
	 *            the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	/**
	 * @return the mensajeErr
	 */
	public String getMensajeErr() {
		return mensajeErr;
	}

	/**
	 * @param mensajeErr
	 *            the mensajeErr to set
	 */
	public void setMensajeErr(String mensajeErr) {
		this.mensajeErr = mensajeErr;
	}

	/**
	 * @return the utilService
	 */
	public UtilService getUtilService() {
		return utilService;
	}

	/**
	 * @param utilService
	 *            the utilService to set
	 */
	public void setUtilService(UtilService utilService) {
		this.utilService = utilService;
	}

	/**
	 * @return the idTraspasante
	 */
	public String getIdTraspasante() {
		return idTraspasante;
	}

	/**
	 * @param idTraspasante
	 *            the idTraspasante to set
	 */
	public void setIdTraspasante(String idTraspasante) {
		this.idTraspasante = idTraspasante;
	}

	/**
	 * @return the folioTraspasante
	 */
	public String getFolioTraspasante() {
		return folioTraspasante;
	}

	/**
	 * @param folioTraspasante
	 *            the folioTraspasante to set
	 */
	public void setFolioTraspasante(String folioTraspasante) {
		this.folioTraspasante = folioTraspasante;
	}

	/**
	 * @return the confirmar
	 */
	public boolean isConfirmar() {
		return confirmar;
	}

	/**
	 * @param confirmar
	 *            the confirmar to set
	 */
	public void setConfirmar(boolean confirmar) {
		this.confirmar = confirmar;
	}

	/**
	 * @return the cuentaNombradaDao
	 */
	public CuentaNombradaDao getCuentaNombradaDao() {
		return cuentaNombradaDao;
	}

	/**
	 * @param cuentaNombradaDao
	 *            the cuentaNombradaDao to set
	 */
	public void setCuentaNombradaDao(CuentaNombradaDao cuentaNombradaDao) {
		this.cuentaNombradaDao = cuentaNombradaDao;
	}

	/**
	 * @return the consultaCatalogoService
	 */
	public ConsultaCatalogoService getConsultaCatalogoService() {
		return consultaCatalogoService;
	}

	/**
	 * @param consultaCatalogoService
	 *            the consultaCatalogoService to set
	 */
	public void setConsultaCatalogoService(
			ConsultaCatalogoService consultaCatalogoService) {
		this.consultaCatalogoService = consultaCatalogoService;
	}

	/**
	 * @return the tenenciaTraspasante
	 */
	public String getTenenciaTraspasante() {
		return tenenciaTraspasante;
	}

	/**
	 * @param tenenciaTraspasante
	 *            the tenenciaTraspasante to set
	 */
	public void setTenenciaTraspasante(String tenenciaTraspasante) {
		this.tenenciaTraspasante = tenenciaTraspasante;
	}

	/**
	 * @return the tenenciaReceptor
	 */
	public String getTenenciaReceptor() {
		return tenenciaReceptor;
	}

	/**
	 * @param tenenciaReceptor
	 *            the tenenciaReceptor to set
	 */
	public void setTenenciaReceptor(String tenenciaReceptor) {
		this.tenenciaReceptor = tenenciaReceptor;
	}

	public EmisionDao getEmisionDao() {
		return emisionDao;
	}

	public void setEmisionDao(EmisionDao emisionDao) {
		this.emisionDao = emisionDao;
	}

	/**
	 * @return the descripcionBoveda
	 */
	public String getDescripcionBoveda() {
		return descripcionBoveda;
	}

	/**
	 * @param descripcionBoveda
	 *            the descripcionBoveda to set
	 */
	public void setDescripcionBoveda(String descripcionBoveda) {
		this.descripcionBoveda = descripcionBoveda;
	}
	
	public void setNombreCuenta(String nombreCuenta){
		this.nombreCuenta = nombreCuenta;
	}
	
	public String getNombreCuenta(){
		return nombreCuenta;
	}

	public String getNombreCuentaRec() {
		return nombreCuentaRec;
	}

	public void setNombreCuentaRec(String nombreCuentaRec) {
		this.nombreCuentaRec = nombreCuentaRec;
	}
}

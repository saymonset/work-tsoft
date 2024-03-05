/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 * 
 * Apr 30, 2008
 */
package com.indeval.portaldali.presentation.controller.mercadodinero.traspasosadministrativos;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.bursatec.seguridad.presentation.constants.SeguridadConstants;
import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.CriterioOrdenamientoDTO;
import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.EmisionDTO;
import com.indeval.portaldali.middleware.dto.EmisoraDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.PosicionDTO;
import com.indeval.portaldali.middleware.dto.SerieDTO;
import com.indeval.portaldali.middleware.dto.TipoCuentaDTO;
import com.indeval.portaldali.middleware.dto.TipoNaturalezaDTO;
import com.indeval.portaldali.middleware.dto.TipoTenenciaDTO;
import com.indeval.portaldali.middleware.dto.TipoValorDTO;
import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.middleware.dto.util.ResultadoValidacionDTO;
import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;
import com.indeval.portaldali.middleware.services.common.constants.TipoCuentaConstants;
import com.indeval.portaldali.middleware.services.common.constants.TipoCustodiaConstants;
import com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService;
import com.indeval.portaldali.middleware.services.estadocuenta.ConsultaPosicionService;
import com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.InfrastructureException;
import com.indeval.portaldali.middleware.services.movimientosadministrativos.TraspasosAdministrativosParams;
import com.indeval.portaldali.middleware.services.movimientosadministrativos.TraspasosAdministrativosService;
import com.indeval.portaldali.middleware.services.util.FechasUtilService;
import com.indeval.portaldali.middleware.services.util.UtilServices; 
import com.indeval.portaldali.persistence.dao.common.EmisionDaliDAO;
import com.indeval.portaldali.presentation.common.constants.UtilConstantes;
import com.indeval.portaldali.presentation.controller.mercadodinero.capturaoperaciones.CapturaOperacionesController;
import com.indeval.portaldali.presentation.dto.traspasosadministrativos.TraspasosAdministrativosDTO;
import com.indeval.portaldali.presentation.helper.CalculoCapturaOperacionViewHelper;
import com.indeval.portaldali.presentation.helper.ServicesCapturaOperacionViewHelper;
import com.indeval.portaldali.presentation.util.ConsultaCatalogosFacade;
import com.indeval.portaldali.presentation.validation.DTOValidator;
import com.indeval.portaldali.vo.CaptOpsConfig;
import com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO;

/**
 * @author Erik Vera Montoya
 * 
 * @version 1.0
 */
public class TraspasosAdministrativosController extends CapturaOperacionesController {
	/**
	 * Validador Traspasos Administrativos
	 */
	private DTOValidator validadorTA;
	/**
	 * determina si debe mostrarse el componente de firma
	 */
	private boolean componenteFirmar = false;
	/**
	 * El DTO que representa a los elementos de la pantalla de traspasos
	 * administrativos, con el detalle de los datos del Traspasante 
	 * participante y del Receptor  contraparte, as como los campos necesarios
	 * con la información necesaria para realizar la captura de la operación.
	 */
	private TraspasosAdministrativosDTO traspasos = new TraspasosAdministrativosDTO();

	/**
	 * Servicio de negocio relacionado con el envo de operaciones
	 */
	private EnvioOperacionesService envioOperacionesService = null;

	/**
	 * Servicio helper para la captura de operaciones
	 */
	private ServicesCapturaOperacionViewHelper servicesCapturaOperacionViewHelper = null;

	/**
	 * Configuracicn de operaciones
	 */
	private CaptOpsConfig configuracion = null;

	/**
	 * 
	 * /** El DTO que representa a los elementos Calculados.
	 */
	private CalculoCapturaOperacionViewHelper calculo = new CalculoCapturaOperacionViewHelper();

	/**
	 * Servicio de operaciones de mercado de dinero
	 */
	private MercadoDineroService mercadoDineroService = null;

	/** Servicio de Traspasos Administrativos */
	private TraspasosAdministrativosService traspasosAdministrativosService;

	/** La clase que permite el acceso a la consulta de los catálogos utilizados */
	private ConsultaCatalogosFacade consultaCatalogosFacade = null;

	/**
	 * Contiene los cálculos de Simulado, Fecha Regreso, Importe y Premio
	 * necesarios para las pantallas de Captura de Operaciones
	 */
	private CalculoCapturaOperacionViewHelper calculos = new CalculoCapturaOperacionViewHelper();

	/**
	 * Saldo Actual, es solo informativo
	 */
	private Long saldoActual;

	/** Valid Object de Traspaso Libre de Pago */
	private TraspasoLibrePagoVO tlpvo = null;

	/** Bandera para indicar que la operacion es del mercado de capitales */
	private Boolean mercadoCapitales = false;

	/** Identificadores del mercado a filtrar */
	private List<Long> idMercadoDinero = null;
	/** Dao para la obtención de la emisión */
	private EmisionDaliDAO emisionDaliDAO = null;
	/** Servicio para la obtencion de la posicion */
	private ConsultaPosicionService consultaPosicionService;
	/** Servicio para el acceso al cálculo de fechas */
	private FechasUtilService fechasUtilService = null;

	
	private UtilServices utilService;

	/** Fecha Dao */
	// private FechaDao fechaDao;
	/**
	 * Constructor
	 */
	public String getInitForm() {
		traspasos.setIdFolioTraspasante(getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion());
		traspasos.getPosicionTraspasante().getCuenta().setInstitucion(getInstitucionActual());
		return null;
	}

	/**
	 * Ejecuta las acciones necesarias para inicializar la pantalla de captura
	 * de traspasos administrativos.
	 * 
	 * @return <code>null</code>. No es necesario un valor de retorno.
	 */
	public String getInit() {
		if (traspasos.getIdFolioReceptor() != null) {
			InstitucionDTO inst = consultaCatalogosFacade.buscarInstitucionPorIdFolio(traspasos.getIdFolioReceptor());
			if (inst != null) {
				traspasos.getCuentaReceptor().setInstitucion(inst);
			} else {
				traspasos.setIdFolioReceptor(StringUtils.EMPTY);
				traspasos.setCuentaReceptor(new CuentaDTO());
			}
		}
		if (traspasos.getIdFolioTraspasante() != null) {
			InstitucionDTO inst = consultaCatalogosFacade.buscarInstitucionPorIdFolio(traspasos.getIdFolioTraspasante());
			if (inst != null) {
				traspasos.getPosicionTraspasante().getCuenta().setInstitucion(inst);
			} else {
				traspasos.setNetoEfectivo(null);
				traspasos.setIdFolioTraspasante(StringUtils.EMPTY);
			}
		}
		if (traspasos.getCuentaReceptor().getCuenta() != null) {
			CuentaDTO cnt = consultaCatalogosFacade.buscarCuentaPorNumeroCuentaNullSiNoExiste(traspasos.getIdFolioReceptor()
					+ traspasos.getCuentaReceptor().getCuenta());

			if (cnt != null) {
				traspasos.setCuentaReceptor(cnt);
			} else {
				traspasos.getCuentaReceptor().setTipoTenencia(new TipoTenenciaDTO());
				traspasos.getCuentaReceptor().setNumeroCuenta(StringUtils.EMPTY);
				traspasos.getCuentaReceptor().setCuenta(StringUtils.EMPTY);
				traspasos.getCuentaReceptor().setNombreCuenta(StringUtils.EMPTY);
			}
		}
		if (traspasos.getPosicionTraspasante().getCuenta().getCuenta() != null) {
			CuentaDTO cnt = consultaCatalogosFacade.buscarCuentaPorNumeroCuentaNullSiNoExiste(traspasos.getIdFolioTraspasante()
					+ traspasos.getPosicionTraspasante().getCuenta().getCuenta());
			if (cnt != null) {
				traspasos.getPosicionTraspasante().setCuenta(cnt);
			} else {
				
				traspasos.getPosicionTraspasante().getCuenta().setTipoTenencia(new TipoTenenciaDTO());
				traspasos.getPosicionTraspasante().getCuenta().setNumeroCuenta(StringUtils.EMPTY);
				traspasos.getPosicionTraspasante().getCuenta().setCuenta(StringUtils.EMPTY);
				traspasos.getPosicionTraspasante().getCuenta().setNombreCuenta(StringUtils.EMPTY);
				
			}
		}
		// obtener los datos de la posicion
		obtenerDatosPosicion();
		return null;
	}

	/**
	 * Valida que el Id Folio del Transpasante o Receptor no este vacio
	 * 
	 * @param e
	 */
	private String validarIdFolioParticipantes() {
		mensajeUsuario = "";
		
		if(StringUtils.isBlank(traspasos.getIdFolioTraspasante())) {
			mensajeUsuario = "La clave Tipo Instituci\u00f3n Trasapasante es requerida";
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensajeUsuario, mensajeUsuario));
		}
		else if(StringUtils.isBlank(traspasos.getIdFolioReceptor())) {
			mensajeUsuario = "La clave Tipo Instituci\u00f3n Receptor es requerida";
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensajeUsuario, mensajeUsuario));
		}
		
		return mensajeUsuario;

	}

	/**
	 * Valida que la cuenta del Transpasante no sea igual a la cuenta del
	 * Receptor
	 * 
	 * @param e
	 */

	public String validarCuenta(ActionEvent e) {
		mensajeUsuario = "";
		if (traspasos.getPosicionTraspasante().getCuenta().getCuenta() != null && !traspasos.getPosicionTraspasante().getCuenta().getCuenta().equals("")) {
			if ( traspasos.getPosicionTraspasante().getCuenta().getInstitucion().getId() == traspasos.getCuentaReceptor().getInstitucion().getId() &&
					traspasos.getPosicionTraspasante().getCuenta().getCuenta().equals(traspasos.getCuentaReceptor().getCuenta())) {
				mensajeUsuario = "La cuenta de Transpasante no puede ser igual a la cuenta del Receptor";
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensajeUsuario, mensajeUsuario));
				traspasos.getCuentaReceptor().setCuenta("");
			}
		}
		return mensajeUsuario;

	}

	/**
	 * llevar a cabo la inserci&oacute;n de la Operaci&oacute;n en la BD
	 * 
	 * @return String Siguiente P&aacute;gina a redireccionar
	 */
	public void enviarOperacion(ActionEvent e) {
		super.limpiarCampos();
		
		try {				
			if (StringUtils.isEmpty(validarIdFolioParticipantes()) && StringUtils.isEmpty(validarCuenta(e))) {
				InstitucionDTO traspasante = consultaCatalogosFacade.buscarInstitucionPorIdNoNulaFolio(traspasos.getIdFolioTraspasante());
				InstitucionDTO receptor = consultaCatalogosFacade.buscarInstitucionPorIdNoNulaFolio(traspasos.getIdFolioReceptor());
				if(traspasante != null && StringUtils.isNotBlank(traspasante.getClaveTipoInstitucion())) {
					traspasos.getPosicionTraspasante().getCuenta().setInstitucion(traspasante);
					if(receptor != null && StringUtils.isNotBlank(receptor.getClaveTipoInstitucion())) {
						traspasos.getCuentaReceptor().setInstitucion(receptor);
						// Si el usuario debe firmar la operacion, se recuperar la firma.
						// Si no se ha firmado, se procesan los datos y regresa el control a la pantalla para firmar
						if (isUsuarioConFacultadFirmar()) {
							//Valida la vigencia del certificado - 18-Agosto-2014 Pablo Balderas
							validarVigenciaCertificado();
							componenteFirmar = true;
						}
						
						procesarDatos();
					}
					else {
						mensajeUsuario = "La clave Tipo Instituci\u00f3n Receptor no existe";
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensajeUsuario, mensajeUsuario));
					}
				}
				else {
					mensajeUsuario = "La clave Tipo Instituci\u00f3n Trasapasante no existe";
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensajeUsuario, mensajeUsuario));
				}
			}
		}
		catch(BusinessException businessException) {
    		addMessage(businessException.getMessage(), FacesMessage.SEVERITY_ERROR);
    	}
	}

	public void enviarOperacion2(ActionEvent event) {
		if (isUsuarioConFacultadFirmar()) {
			validarVigenciaCertificado();
			recuperarCamposFirma();
			if (!StringUtils.isEmpty(isoFirmado)) {
				try {
					grabarOperacion(event);
				}catch(BusinessException e) {
					addMessage(e.getMessage(), FacesMessage.SEVERITY_ERROR);
				}
			}
		}
		
		super.limpiarCampos();
	}
	/**
	 * Valida, Calcula y registra la operacion antes de firmar
	 */
	private void procesarDatos() {
		boolean valido = validarDTO();
		if (valido) {
			calcularYRegistrarOperacicon();
		}
	}

	/**
	 * Guarda la operacion firmada
	 * 
	 * @param e
	 */
	public void grabarOperacion(ActionEvent e) {
		if (!cdb.validation(hashIso, isoSinFirmar)) {
			throw new InfrastructureException(UtilConstantes.ERROR_ISO_DIFERENTE);
		}
		isoFirmado = (new StringBuilder()).append(isoSinFirmar).append(numeroSerie).append("\n").append("{SHA1withRSA}").append(isoFirmado)
				.toString();

		enviarOperacionABitacora();
	}

	/**
	 * Envia la operacion a bitacora
	 */
	private void enviarOperacionABitacora() {
		boolean isCompra = false;
		
		HashMap<String, String> datosAdicionales = new HashMap<String, String>();     
		datosAdicionales.put(SeguridadConstants.USR_CREDENCIAL, 
				(String)FacesContext.getCurrentInstance().getExternalContext().
				getSessionMap().get(SeguridadConstants.TICKET_SESION));	

		envioOperacionesService.grabaOperacion(tlpvo, folioAsignado, isCompra, datosAdicionales, null, isoFirmado);

		if (!existeErrorEnInvocacion()) {
			mensajeUsuario = "La informaci\u00f3n ha sido guardada y se ha generado el folio: " + folioAsignado;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensajeUsuario, mensajeUsuario));
			traspasos = new TraspasosAdministrativosDTO();
			limpiarCampos(null);
		}
	}

	/**
	 * Guarda la operacion firmada
	 * 
	 * @param e
	 */
	public void enviarOperacionCapital(ActionEvent e) {
		// pruebas solicitadas jira
		// if(!StringUtils.isEmpty(this.isoSinFirmar))
		mercadoCapitales = true;
		enviarOperacion(e);
	}

	/**
	 * Calcula y registra la operacion en detalle
	 */
	private void calcularYRegistrarOperacicon() {
		// try {
		EmisionVO emision = new EmisionVO();

		if (traspasos.getSaldoDisponible() == null)
			traspasos.setSaldoDisponible(new Long(0));
		emision.setSaldoDisponible(new BigDecimal(traspasos.getSaldoDisponible()));
		emision.setIdTipoValor(traspasos.getPosicionTraspasante().getEmision().getTipoValor().getClaveTipoValor());
		emision.setEmisora(traspasos.getPosicionTraspasante().getEmision().getEmisora().getDescripcion());
		emision.setCupon(traspasos.getPosicionTraspasante().getEmision().getCupon());
		emision.setSerie(traspasos.getPosicionTraspasante().getEmision().getSerie().getSerie());
		emision.setIsin(traspasos.getPosicionTraspasante().getEmision().getIsin());
		if (traspasos.getPrecioVector() == null)
			traspasos.setPrecioVector(new Double(0));

		emision.setPrecioVector(new BigDecimal(traspasos.getPrecioVector()));
		// emision.setSaldoDisponible(new
		// BigDecimal(traspasos.getSaldoDisponible()));

		TraspasosAdministrativosParams traspasosAdministrativosParams = new TraspasosAdministrativosParams();
		AgenteVO traspasante = DTOAssembler.crearAgenteVO(traspasos.getPosicionTraspasante().getCuenta());
		AgenteVO receptor = DTOAssembler.crearAgenteVO(traspasos.getCuentaReceptor());

		traspasosAdministrativosParams.setTraspasante(traspasante);
		traspasosAdministrativosParams.setReceptor(receptor);
		traspasosAdministrativosParams.setEmision(emision);
		traspasosAdministrativosParams.setCantidadTitulos(new BigInteger(Long.toString(traspasos.getCantidadOperada())));

		if (mercadoCapitales) {
			traspasosAdministrativosParams.setMercadoDinero(false);
		} else {
			traspasosAdministrativosParams.setMercadoDinero(true);
		}
		traspasosAdministrativosParams.setFechaHoraCierreOper(traspasos.getFechaHoraCierreOper());

		BigInteger folioAsignadoBI;
		folioAsignadoBI = traspasosAdministrativosService.businessRulesTraspasosAdministrativos(traspasosAdministrativosParams);

		if (folioAsignadoBI != null) {
			folioAsignado = folioAsignadoBI.toString();
		}
		if (!existeErrorEnInvocacion()) {
			tlpvo = llenaVOTraspasoMercadoDinero(traspasosAdministrativosParams);
			tlpvo.setReferenciaMensaje(folioAsignado);
			
			BovedaDTO boveda = null;
			if(traspasos.getPosicionTraspasante().getBoveda() != null) {				
				boveda =  consultaCatalogosFacade.buscarBovedaPorId(traspasos.getPosicionTraspasante().getBoveda().getId());			
			}
			if(boveda!=null && boveda.getId()>-1){			
				tlpvo.setBoveda(boveda != null ? boveda.getNombreCorto() : null);
			}

			// Si el usuario debe firmar la operacion, se crea el iso
			if (isUsuarioConFacultadFirmar()) {
				isoSinFirmar = isoHelper.creaISO(tlpvo, false);
				hashIso = cdb.cipherHash(isoSinFirmar);
			} else {
				enviarOperacionABitacora();
			}
		}

	}

	/**
	 * Metodo auxiliar para llenar el VO de TLP
	 * 
	 * @param params
	 * @return
	 */
	private TraspasoLibrePagoVO llenaVOTraspasoMercadoDinero(TraspasosAdministrativosParams params) {
		TraspasoLibrePagoVO vo = new TraspasoLibrePagoVO();

		vo.setTipoValor(params.getEmision().getIdTipoValor().trim());
		vo.setEmisora(params.getEmision().getEmisora().trim());
		vo.setSerie(params.getEmision().getSerie().trim());
		vo.setCupon(params.getEmision().getCupon().trim());
		vo.setCantidadTitulos(params.getCantidadTitulos().longValue());
		vo.setIdFolioCtaReceptora(params.getReceptor().getId() + params.getReceptor().getFolio() + params.getReceptor().getCuenta());
		vo.setIdFolioCtaTraspasante(params.getTraspasante().getId() + params.getTraspasante().getFolio() + params.getTraspasante().getCuenta());		
		vo.setFechaLiquidacion(servicesCapturaOperacionViewHelper.getFechasUtilService().getCurrentDate());
		vo.setFechaRegistro(getHoy()); 
		vo.setFechaHoraCierreOper(params.getFechaHoraCierreOper());
		vo.setTipoInstruccion("K");
		vo.setReferenciaOperacion(folioAsignado);
		return vo;
	}

	/**
	 * @return Returns the currentDate
	 */
	public Date getHoy() {
		// return fechaDao.getCurrentDate();
		return new Date();
	}

	/**
	 * Ejecuta las validaciones de los DTOs de captura de acuerdo al tipo de
	 * operación a realizar
	 * 
	 * @return
	 */
	private boolean validarDTO() {
		ResultadoValidacionDTO resultadao = null;

		resultadao = validadorTA.validarDTO(traspasos);
		if (!resultadao.isValido()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, resultadao.getMensaje(), resultadao.getMensaje()));
		}
		return resultadao.isValido();
	}

	/**
	 * Limpiar los campos de la página respetando las opciones de la operacion.
	 * 
	 * 
	 */
	public void limpiarCampos(ActionEvent e) {
		componenteFirmar = false;
		traspasos = new TraspasosAdministrativosDTO();

		traspasos.setIdFolioTraspasante(getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion());
		traspasos.getPosicionTraspasante().getCuenta().setInstitucion(getInstitucionActual());
		super.limpiarCampos();
		this.setSaldoActual(null);

	}

	public void limpiarCampos() {
		limpiarCampos(null);

	}

	/**
	 * Metodo para calcular el saldo actual
	 * 
	 * @param event
	 */
	public void realizaCalculoSaldoActual(ActionEvent event) {
		if (traspasos.getCantidadOperada() != null && traspasos.getCantidadOperada() > 0) {
			if (traspasos.getSaldoDisponible() != null) {
				this.saldoActual = (traspasos.getSaldoDisponible() - traspasos.getCantidadOperada());
			}
		}

	}

	/**
	 * Metodo para la obtencion de la Posicion a partir de los campos
	 * idFolioParticiante, TV, Emisora, Serie.
	 * 
	 * return <code>null</code>. No es necesario un valor de retorno.
	 */
	public String obtenerDatosPosicion() {
		// primero se obtiene la Posicion
		PosicionDTO posicion = null;
		if (StringUtils.isNotEmpty(traspasos.getPosicionTraspasante().getCuenta().getCuenta())
				&& StringUtils.isNotEmpty(traspasos.getPosicionTraspasante().getEmision().getTipoValor().getClaveTipoValor())
				&& StringUtils.isNotEmpty(traspasos.getPosicionTraspasante().getEmision().getEmisora().getDescripcion())
				&& StringUtils.isNotEmpty(traspasos.getPosicionTraspasante().getEmision().getSerie().getSerie())) {
			PosicionDTO criterio = new PosicionDTO();

			criterio.setCuenta(new CuentaDTO());
			criterio.getCuenta().setTipoCustodia(TipoCustodiaConstants.VALORES);
			criterio.getCuenta().setTipoTenencia(new TipoTenenciaDTO());
			criterio.getCuenta().getTipoTenencia().setTipoCuenta(new TipoCuentaDTO(TipoCuentaConstants.TIPO_NOMBRADA, ""));
			criterio.getCuenta().getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO, ""));
			criterio.getCuenta().setNumeroCuenta(
					getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion()
							+ traspasos.getPosicionTraspasante().getCuenta().getCuenta());
			criterio.setEmision(new EmisionDTO());
			InstitucionDTO institucion = null;
			institucion = consultaCatalogosFacade.buscarInstitucionPorIdFolio(traspasos.getIdFolioTraspasante());

			if (institucion != null) {
				// cuenta
				if (StringUtils.isNotEmpty(traspasos.getPosicionTraspasante().getCuenta().getCuenta())) {
					criterio.getCuenta().setNumeroCuenta(
							institucion.getClaveTipoInstitucion() + institucion.getFolioInstitucion()
									+ traspasos.getPosicionTraspasante().getCuenta().getCuenta());
				} else {
					criterio.getCuenta().setCuenta("-1");
				}

				// TV
				criterio.getEmision().setTipoValor(
						consultaCatalogosFacade.buscarTipoValorPorClave(traspasos.getPosicionTraspasante().getEmision().getTipoValor().getClaveTipoValor()));
				// Emisora
				criterio.getEmision().setEmisora(
						consultaCatalogosFacade.buscarEmisoraPorNombreCorto(traspasos.getPosicionTraspasante().getEmision().getEmisora().getDescripcion()));
				criterio.getEmision().setSerie(new SerieDTO());
				// Serie
				criterio.getEmision().getSerie().setSerie(traspasos.getPosicionTraspasante().getEmision().getSerie().getSerie().trim());
							

				if (traspasos.getPosicionTraspasante().getBoveda() != null && traspasos.getPosicionTraspasante().getBoveda().getId() > 0) {
					criterio.setBoveda(new BovedaDTO());
					criterio.getBoveda().setId(traspasos.getPosicionTraspasante().getBoveda().getId());
				}
				criterio.getBoveda().setId(NumberUtils.toLong("-1", DaliConstants.VALOR_COMBO_TODOS));
				criterio.getCuenta().setInstitucion(institucion);
				criterio.setFechaFinal(new Date());

				CriterioOrdenamientoDTO orden = new CriterioOrdenamientoDTO();
				orden.setColumna("sortCuenta");

				List<PosicionDTO> listaPosiciones = consultaPosicionService.consultarPosicionesPorMercado(criterio, null, orden, idMercadoDinero
						.toArray(new Long[] {}));

				if (listaPosiciones != null && !listaPosiciones.isEmpty()) {
					posicion = listaPosiciones.get(0);
					// obtencion de los datos para ser desplegado en pantalla
					traspasos.getPosicionTraspasante().getCuenta().setCuenta(posicion.getCuenta().getCuenta());
					traspasos.getPosicionTraspasante().getEmision().getEmisora().setDescripcion(posicion.getEmision().getEmisora().getDescripcion());
					traspasos.getPosicionTraspasante().getEmision().setCupon(posicion.getEmision().getCupon());
					traspasos.getPosicionTraspasante().getEmision().setIsin(posicion.getEmision().getIsin());
					traspasos.getPosicionTraspasante().setBoveda(new BovedaDTO());
					traspasos.getPosicionTraspasante().getBoveda().setNombreCorto(posicion.getBoveda().getNombreCorto());
					traspasos.getPosicionTraspasante().getBoveda().setId(posicion.getBoveda().getId());
					traspasos.setSaldoDisponible(new BigDecimal(posicion.getPosicionDisponible()).longValue());

					if (posicion.getEmision().getFechaVencimiento() != null) {
						traspasos.setDiasVigentes((int)utilService.dateDiff(fechasUtilService.getCurrentDate(),posicion.getEmision().getFechaVencimiento()));
					}
				} else {

//					traspasos.getPosicionTraspasante().getEmision().setCupon(null);
					traspasos.getPosicionTraspasante().getEmision().setIsin(null);
					traspasos.getPosicionTraspasante().getEmision().getTipoValor().getMercado().setClaveMercado(null);
					traspasos.getPosicionTraspasante().setBoveda(null);
					traspasos.setSaldoDisponible(null);
					traspasos.setDiasVigentes(null);
				}
			}

		} 
		
		if (posicion == null && StringUtils.isNotEmpty(traspasos.getPosicionTraspasante().getEmision().getTipoValor().getClaveTipoValor())
				&& StringUtils.isNotEmpty(traspasos.getPosicionTraspasante().getEmision().getEmisora().getDescripcion())
				&& StringUtils.isNotEmpty(traspasos.getPosicionTraspasante().getEmision().getSerie().getSerie())) {

			EmisionDTO criterio = new EmisionDTO();
			criterio.setTipoValor(new TipoValorDTO());
			criterio.getTipoValor().setClaveTipoValor(traspasos.getPosicionTraspasante().getEmision().getTipoValor().getClaveTipoValor());
			criterio.setEmisora(new EmisoraDTO());
			criterio.getEmisora().setDescripcion(traspasos.getPosicionTraspasante().getEmision().getEmisora().getDescripcion());
			criterio.setSerie(new SerieDTO());
			criterio.getSerie().setSerie(traspasos.getPosicionTraspasante().getEmision().getSerie().getSerie());
			List<EmisionDTO> listaEmisiones = emisionDaliDAO.consultarEmisionesPorDescripciones(criterio, null);

			if (listaEmisiones != null && !listaEmisiones.isEmpty() && listaEmisiones.size() == 1) {
				// pasar los datos al objeto de la pantalla
				traspasos.getPosicionTraspasante().getEmision().getEmisora().setDescripcion(listaEmisiones.get(0).getEmisora().getDescripcion());
				traspasos.getPosicionTraspasante().getEmision().getTipoValor().setClaveTipoValor(listaEmisiones.get(0).getTipoValor().getClaveTipoValor());
				traspasos.getPosicionTraspasante().getEmision().getSerie().setSerie(listaEmisiones.get(0).getSerie().getSerie());
				traspasos.getPosicionTraspasante().getEmision().setIsin(listaEmisiones.get(0).getIsin());
				traspasos.setDiasVigentes(listaEmisiones.get(0).getDiasVigentes());
				traspasos.getPosicionTraspasante().getEmision().setCupon(listaEmisiones.get(0).getCupon());

				traspasos.getPosicionTraspasante().setBoveda(new BovedaDTO());
				traspasos.getPosicionTraspasante().getBoveda().setNombreCorto(listaEmisiones.get(0).getBoveda().getNombreCorto());
				traspasos.getPosicionTraspasante().getBoveda().setId(listaEmisiones.get(0).getBoveda().getId());
			} else {
//				traspasos.getPosicionTraspasante().getEmision().setCupon(null);
				traspasos.getPosicionTraspasante().getEmision().setIsin(null);
				traspasos.getPosicionTraspasante().getEmision().getTipoValor().getMercado().setClaveMercado(null);
				traspasos.getPosicionTraspasante().setBoveda(null);
				traspasos.setSaldoDisponible(null);
				traspasos.setDiasVigentes(null);
			}

		}
		return null;
	}

	/**
	 * Obtiene el valor del campo validadorTA
	 * 
	 * @return el valor de validadorTA
	 */
	public DTOValidator getValidadorTA() {
		return validadorTA;
	}

	/**
	 * Asigna el campo validadorTA
	 * 
	 * @param validadorTA
	 *            el valor de validadorTA a asignar
	 */
	public void setValidadorTA(DTOValidator validadorTA) {
		this.validadorTA = validadorTA;
	}

	/**
	 * Obtiene el valor del campo traspasos
	 * 
	 * @return el valor de traspasos
	 */
	public TraspasosAdministrativosDTO getTraspasos() {
		return traspasos;
	}

	/**
	 * Asigna el campo traspasos
	 * 
	 * @param traspasos
	 *            el valor de traspasos a asignar
	 */
	public void setTraspasos(TraspasosAdministrativosDTO traspasos) {
		this.traspasos = traspasos;
	}

	/**
	 * Obtiene el valor del campo envioOperacionesService
	 * 
	 * @return el valor de envioOperacionesService
	 */
	public EnvioOperacionesService getEnvioOperacionesService() {
		return envioOperacionesService;
	}

	/**
	 * Asigna el campo envioOperacionesService
	 * 
	 * @param envioOperacionesService
	 *            el valor de envioOperacionesService a asignar
	 */
	public void setEnvioOperacionesService(EnvioOperacionesService envioOperacionesService) {
		this.envioOperacionesService = envioOperacionesService;
	}

	/**
	 * Obtiene el valor del campo servicesCapturaOperacionViewHelper
	 * 
	 * @return el valor de servicesCapturaOperacionViewHelper
	 */
	public ServicesCapturaOperacionViewHelper getServicesCapturaOperacionViewHelper() {
		return servicesCapturaOperacionViewHelper;
	}

	/**
	 * Asigna el campo servicesCapturaOperacionViewHelper
	 * 
	 * @param servicesCapturaOperacionViewHelper
	 *            el valor de servicesCapturaOperacionViewHelper a asignar
	 */
	public void setServicesCapturaOperacionViewHelper(ServicesCapturaOperacionViewHelper servicesCapturaOperacionViewHelper) {
		this.servicesCapturaOperacionViewHelper = servicesCapturaOperacionViewHelper;
	}

	/**
	 * Obtiene el valor del campo configuracion
	 * 
	 * @return el valor de configuracion
	 */
	public CaptOpsConfig getConfiguracion() {
		return configuracion;
	}

	/**
	 * Asigna el campo configuracion
	 * 
	 * @param configuracion
	 *            el valor de configuracion a asignar
	 */
	public void setConfiguracion(CaptOpsConfig configuracion) {
		this.configuracion = configuracion;
	}

	/**
	 * Obtiene el valor del campo calculo
	 * 
	 * @return el valor de calculo
	 */
	public CalculoCapturaOperacionViewHelper getCalculo() {
		return calculo;
	}

	/**
	 * Asigna el campo calculo
	 * 
	 * @param calculo
	 *            el valor de calculo a asignar
	 */
	public void setCalculo(CalculoCapturaOperacionViewHelper calculo) {
		this.calculo = calculo;
	}

	/**
	 * Obtiene el valor del campo mercadoDineroService
	 * 
	 * @return el valor de mercadoDineroService
	 */
	public MercadoDineroService getMercadoDineroService() {
		return mercadoDineroService;
	}

	/**
	 * Asigna el campo mercadoDineroService
	 * 
	 * @param mercadoDineroService
	 *            el valor de mercadoDineroService a asignar
	 */
	public void setMercadoDineroService(MercadoDineroService mercadoDineroService) {
		this.mercadoDineroService = mercadoDineroService;
	}

	/**
	 * Obtiene el valor del campo consultaCatalogosFacade
	 * 
	 * @return el valor de consultaCatalogosFacade
	 */
	public ConsultaCatalogosFacade getConsultaCatalogosFacade() {
		return consultaCatalogosFacade;
	}

	/**
	 * Asigna el campo consultaCatalogosFacade
	 * 
	 * @param consultaCatalogosFacade
	 *            el valor de consultaCatalogosFacade a asignar
	 */
	public void setConsultaCatalogosFacade(ConsultaCatalogosFacade consultaCatalogosFacade) {
		this.consultaCatalogosFacade = consultaCatalogosFacade;
	}

	/**
	 * Obtiene el valor del campo calculos
	 * 
	 * @return el valor de calculos
	 */
	public CalculoCapturaOperacionViewHelper getCalculos() {
		return calculos;
	}

	/**
	 * Asigna el campo calculos
	 * 
	 * @param calculos
	 *            el valor de calculos a asignar
	 */
	public void setCalculos(CalculoCapturaOperacionViewHelper calculos) {
		this.calculos = calculos;
	}

	/**
	 * Obtiene el valor del campo saldoActual
	 * 
	 * @return el valor de saldoActual
	 */
	public Long getSaldoActual() {
		return saldoActual;
	}

	/**
	 * Asigna el campo saldoActual
	 * 
	 * @param saldoActual
	 *            el valor de saldoActual a asignar
	 */
	public void setSaldoActual(Long saldoActual) {
		this.saldoActual = saldoActual;
	}

	/**
	 * Obtiene el valor del campo traspasosAdministrativosService
	 * 
	 * @return el valor de traspasosAdministrativosService
	 */
	public TraspasosAdministrativosService getTraspasosAdministrativosService() {
		return traspasosAdministrativosService;
	}

	/**
	 * Asigna el campo traspasosAdministrativosService
	 * 
	 * @param traspasosAdministrativosService
	 *            el valor de traspasosAdministrativosService a asignar
	 */
	public void setTraspasosAdministrativosService(TraspasosAdministrativosService traspasosAdministrativosService) {
		this.traspasosAdministrativosService = traspasosAdministrativosService;
	}

	/**
	 * @param mercadoCapitales
	 *            the mercadoCapitales to set
	 */
	public void setMercadoCapitales(Boolean mercadoCapitales) {
		this.mercadoCapitales = mercadoCapitales;
	}

	/**
	 * @return the mercadoCapitales
	 */
	public Boolean getMercadoCapitales() {
		return mercadoCapitales;
	}

	public boolean isComponenteFirmar() {
		return componenteFirmar;
	}

	public void setComponenteFirmar(boolean componenteFirmar) {
		this.componenteFirmar = componenteFirmar;
	}

	/**
	 * Obtiene el valor del atributo idMercadoDinero
	 * 
	 * @return el valor del atributo idMercadoDinero
	 */
	public List<Long> getIdMercadoDinero() {
		return idMercadoDinero;
	}

	/**
	 * Establece el valor del atributo idMercadoDinero
	 * 
	 * @param idMercadoDinero
	 *            el valor del atributo idMercadoDinero a establecer
	 */
	public void setIdMercadoDinero(List<Long> idMercadoDinero) {
		this.idMercadoDinero = idMercadoDinero;
	}

	/**
	 * Obtiene el valor del atributo emisionDaliDAO
	 * 
	 * @return el valor del atributo emisionDaliDAO
	 */
	public EmisionDaliDAO getEmisionDAO() {
		return emisionDaliDAO;
	}

	/**
	 * Establece el valor del atributo emisionDaliDAO
	 * 
	 * @param emisionDaliDAO
	 *            el valor del atributo emisionDaliDAO a establecer
	 */
	public void setEmisionDAO(EmisionDaliDAO emisionDaliDAO) {
		this.emisionDaliDAO = emisionDaliDAO;
	}

	/**
	 * Obtiene el valor del atributo consultaPosicionService
	 * 
	 * @return el valor del atributo consultaPosicionService
	 */
	public ConsultaPosicionService getConsultaPosicionService() {
		return consultaPosicionService;
	}

	/**
	 * Establece el valor del atributo consultaPosicionService
	 * 
	 * @param consultaPosicionService
	 *            el valor del atributo consultaPosicionService a establecer
	 */
	public void setConsultaPosicionService(ConsultaPosicionService consultaPosicionService) {
		this.consultaPosicionService = consultaPosicionService;
	}

	/**
	 * Obtiene el valor del atributo fechasUtilService
	 * 
	 * @return el valor del atributo fechasUtilService
	 */
	public FechasUtilService getFechasUtilService() {
		return fechasUtilService;
	}

	/**
	 * Establece el valor del atributo fechasUtilService
	 * 
	 * @param fechasUtilService
	 *            el valor del atributo fechasUtilService a establecer
	 */
	public void setFechasUtilService(FechasUtilService fechasUtilService) {
		this.fechasUtilService = fechasUtilService;
	}

	public void setUtilService(UtilServices utilService) {
		this.utilService = utilService;
	}


}

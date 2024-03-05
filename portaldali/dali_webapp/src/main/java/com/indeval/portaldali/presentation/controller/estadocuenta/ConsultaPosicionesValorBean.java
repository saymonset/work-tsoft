/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 */
package com.indeval.portaldali.presentation.controller.estadocuenta;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.bursatec.seguridad.presentation.constants.SeguridadConstants;
import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.EmisionDTO;
import com.indeval.portaldali.middleware.dto.EmisoraDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.MercadoDTO;
import com.indeval.portaldali.middleware.dto.PosicionDTO;
import com.indeval.portaldali.middleware.dto.TipoCuentaDTO;
import com.indeval.portaldali.middleware.dto.TipoNaturalezaDTO;
import com.indeval.portaldali.middleware.dto.TipoTenenciaDTO;
import com.indeval.portaldali.middleware.dto.TipoValorDTO;
import com.indeval.portaldali.middleware.dto.util.ResultadoValidacionDTO;
import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;
import com.indeval.portaldali.middleware.services.common.util.FormatUtil;
import com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService;
import com.indeval.portaldali.middleware.services.mercadocapitales.GetCapturaTraspasoParams;
import com.indeval.portaldali.middleware.services.mercadocapitales.MercadoCapitalesService;
import com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService;
import com.indeval.portaldali.middleware.services.mercadodinero.RegistraOperacionParams;
import com.indeval.portaldali.middleware.services.modelo.BusinessDataException;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.util.UtilServices;
import com.indeval.portaldali.middleware.services.util.util.Constantes;
import com.indeval.portaldali.modelo.to.estadocuenta.TotalesPosicionTO;
import com.indeval.portaldali.persistencia.posicion.Posicion;
import com.indeval.portaldali.presentation.common.constants.CamposPantallaConstantes;
import com.indeval.portaldali.presentation.common.constants.ReportesConstants;
import com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.BackingBeanBase;
import com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.impl.ConsultaDePosiciones;
import com.indeval.portaldali.presentation.dto.mercadodinero.TraspasoLibrePagoDTO;
import com.indeval.portaldali.presentation.helper.IsoHelper;
import com.indeval.portaldali.presentation.helper.ServicesCapturaOperacionViewHelper;
import com.indeval.portaldali.presentation.util.CifradorDescifradorBlowfish;
import com.indeval.portaldali.presentation.util.ConsultaCatalogosFacade;
import com.indeval.portaldali.presentation.validation.DTOValidator;
import com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO;

/**
 * Backing bean asociado a la pantalla de consulta de posicion de valores. Esta
 * clase se encarga de la invocación a los servicios de negocio relacionados con
 * la consulta de posiciones de valor
 * 
 * @author Emigdio Hernández
 * @version 1.0
 * 
 */
public class ConsultaPosicionesValorBean extends BackingBeanBase {
	
	private boolean mensaje = false;
	
	/** Objeto que representa un esquema de consulta */
	private ConsultaDePosiciones consultaPosiciones = null;
	
	/** Indica si la consulta principal ya fue ejecutada */
	private boolean consultaEjecutada = false;
	
	/** Resumen de totales por pagina */
	private PosicionDTO resumenPosicion = null;

	/** Resumen en consultas */
	private TotalesPosicionTO resumenPosicionConsulta = null;

	/** Contiene el valor seleccionado de la emisora en el criterio de emision */
	private String emisoraSeleccionada = null;

	private TipoValorDTO instrumentoSeleccionado = new TipoValorDTO();
	
	/** Indica si se debe de restaurar la pagicacion de los resultados principales de la pagina */
	private boolean restaurarPaginacionResultados = true;

	/** Lista con los resultados de la consulta */
	private List<Posicion> resultados = null; 
	
	/** Almacena la clave y folio de la institucion */
	private String folioClaveInstitucion;

	/** El nombre de la institucion seleccionada */
	private String nombreInstitucion;

	/** Facade para accesar a los catálogos del DALI */
	private ConsultaCatalogosFacade consultaCatalogos;

	/** Institucion a buscar cuando el usuario es INDEVAL */
	private InstitucionDTO institucion = null;

	/** Contiene el papel mercado por el cual se filtra el mercado */
	private String papelMercado = DaliConstants.PAPEL_MERCADO_TODOS;

	/** Validador de la Captura Operaciones Reporto Nominal Opcion Mismo Dia */
    private DTOValidator validadorTraspasoLibrePago;

    /** Servicio de cosultas generales */
    private ConsultaCatalogosFacade consultaCatalogosFacade; 
    
    /** Servicio helper para la captura de operaciones */
    private ServicesCapturaOperacionViewHelper servicesCapturaOperacionViewHelper;
    
    /** Servicio para Mercado de Capitales */
    private MercadoCapitalesService mercadoCapitalesService;
    
    /** Servicio para Mercado de Capitales */
    private MercadoDineroService mercadoDineroService;

    /** Servicio para sacar folio de una secuencia */
    private UtilServices utilService;
    
    /** Servicio de negocio relacionado con el envo de operaciones */
    private EnvioOperacionesService envioOperacionesService;
    
    /** Corresponde al campo de la 2da pantalla de Consulta de Posiciones*/
    private String folioClaveInstitucion2;
    
    private String cuenta;
    
    private Date fechaHoraCierreOper;
    
    private String nombreCuenta;
    
    private String tenencia;
    
    private String foliosRegistrados;
    
    private boolean editarTraspasante = Boolean.FALSE;

    private List<String> isosSinFirmar = null;

    private List<String> isosFirmados = null;
    
    private List<String> hashIso = null;

	private int totalOperaciones = 0;

    /** EL numero de serie asociado al iso firmado */
    protected String numeroSerie = "";

    /** Ayudante para la generacion de las cadenas ISO que deberin ser firmadas */
    protected IsoHelper isoHelper = null;
	
    protected CifradorDescifradorBlowfish cdb = new CifradorDescifradorBlowfish();
    
    private List<TraspasoLibrePagoVO> tlpVo = new ArrayList<TraspasoLibrePagoVO>();
    
    /** Valor cero */
    private Double cero = 0.0;
    
	/**
	 * Establece el valor del atributo cuentaSeleccionada
	 * 
	 * @param cuentaSeleccionada el valor del atributo cuentaSeleccionada a establecer
	 */
	public void setCuentaSeleccionada(String cuentaSeleccionada) {
		consultaPosiciones.getCriterioCuenta().setOpcionSeleccionada(null);
		if (cuentaSeleccionada != null && cuentaSeleccionada.length() > 0) {
			if (!"TODAS".equals(cuentaSeleccionada)) {
				String institucionCuenta = null;
				if (!isUsuarioIndeval()) {
					institucionCuenta = getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion() + cuentaSeleccionada;
				}
				else {
					institucionCuenta = getFolioClaveInstitucion() + cuentaSeleccionada;
				}
				CuentaDTO cuenta = new CuentaDTO();
				cuenta.setNumeroCuenta(cuentaSeleccionada);
				consultaPosiciones.getCriterioCuenta().getOpcionSeleccionada().setNumeroCuenta(institucionCuenta);
				consultaPosiciones.getCriterioCuenta().getOpcionSeleccionada().setDescripcion(institucionCuenta);
				consultaPosiciones.getCriterioCuenta().getOpcionSeleccionada().setCuenta(cuentaSeleccionada);
			}
		}
	}

	/**
	 * Obtiene el valor del atributo consultaCatalogos
	 * 
	 * @return el valor del atributo consultaCatalogos
	 */
	public ConsultaCatalogosFacade getConsultaCatalogos() {
		return consultaCatalogos;
	}

	/**
	 * Establece el valor del atributo consultaCatalogos
	 * 
	 * @param consultaCatalogos
	 *            el valor del atributo consultaCatalogos a establecer
	 */
	public void setConsultaCatalogos(ConsultaCatalogosFacade consultaCatalogos) {
		this.consultaCatalogos = consultaCatalogos;
	}

	/**
	 * Busca cuentas en el catálogo cuyo número de cuenta comiencen con el
	 * prefijo proporcionado.
	 * 
	 * @param prefijo
	 *            el prefijo a consultar en la BD.
	 * @return una lista con objetos de tipo {@link CuentaDTO} con todas las
	 *         coincidencias encontradas.
	 */
	public List<CuentaDTO> buscarCuentasPorPrefijo(Object value) {
		List<CuentaDTO> cuentas = new ArrayList<CuentaDTO>();
		String prefijoAjustado = "";
		if (value != null) {
			prefijoAjustado = String.valueOf(value).trim().replace("*", "%");
		}
		if (value != null && consultaPosiciones.getCriterioCuenta().getIdInstitucion() != -1) {

			CuentaDTO criterio = new CuentaDTO();

			if (!isUsuarioIndeval()) {
				criterio.setNumeroCuenta(getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion() + prefijoAjustado);
				criterio.setInstitucion(getInstitucionActual());
			} else {
				criterio.setNumeroCuenta(getFolioClaveInstitucion() + prefijoAjustado);
				criterio.setInstitucion(institucion);
			}

			criterio.setTipoTenencia(consultaPosiciones.getCriterioCuenta().getCriterioTipoTenencia().getOpcionSeleccionada());

			cuentas = consultaPosiciones.getCriterioCuenta().getConsultaCuentaService().buscarCuentasPorFragmentoNumeroCuenta(criterio);

			for (CuentaDTO cuentaDTO : cuentas) {
				cuentaDTO.setDescripcion(cuentaDTO.getDescripcion().substring(5));
			}
		}

		return cuentas;
	}

	private void limpiaFirma() {
		isosFirmados = new ArrayList<String>();
		isosSinFirmar = new ArrayList<String>();
		hashIso = new ArrayList<String>();
		totalOperaciones = 0;
		tlpVo = new ArrayList<TraspasoLibrePagoVO>();
	}
	
	/**
	 * Busca cuentas en el catálogo cuyo número de cuenta comiencen con el
	 * prefijo proporcionado.
	 * 
	 * @param prefijo
	 *            el prefijo a consultar en la BD.
	 * @return una lista con objetos de tipo {@link CuentaDTO} con todas las
	 *         coincidencias encontradas.
	 */
	public List<CuentaDTO> buscarCuentasPorPrefijo2(Object value) {
		List<CuentaDTO> cuentas = new ArrayList<CuentaDTO>();
		String prefijoAjustado = "";
		if (value != null) {
			prefijoAjustado = String.valueOf(value).trim().replace("*", "%");
		}
		if (value != null && consultaPosiciones.getCriterioCuenta().getIdInstitucion() != -1) {

			CuentaDTO criterio = new CuentaDTO();

			if (!isUsuarioIndeval()) {
				criterio.setNumeroCuenta(getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion() + prefijoAjustado);
				criterio.setInstitucion(getInstitucionActual());
			} else {
				criterio.setNumeroCuenta(getFolioClaveInstitucion2() + prefijoAjustado);
				criterio.setInstitucion(institucion);
			}

			criterio.setTipoTenencia(consultaPosiciones.getCriterioCuenta().getCriterioTipoTenencia().getOpcionSeleccionada());

			cuentas = consultaPosiciones.getCriterioCuenta().getConsultaCuentaService().buscarCuentasPorFragmentoNumeroCuenta(criterio);

			for (CuentaDTO cuentaDTO : cuentas) {
				cuentaDTO.setDescripcion(cuentaDTO.getDescripcion().substring(5));
			}
		}

		return cuentas;
	}

	public void guardaOperaciones(ActionEvent event) {		
		try {				
			logger.info("Entrando al metodo FondeoOperacionesBean.guardaOperaciones");
			boolean existeTraspaso = false;
			boolean traspasoValido = true;
			foliosRegistrados = "";
			//Se consulta la cuenta receptor elegida
			CuentaDTO cuentaDTO = consultaCatalogos.buscarCuentaPorNumeroCuentaNullSiNoExiste(this.folioClaveInstitucion2 + this.cuenta);
			
			List<TraspasoLibrePagoDTO> traspasos = new ArrayList<TraspasoLibrePagoDTO>();
			if(cuentaDTO != null){
				//Buscamos los traspasos solicitados
				for(Posicion posicion : resultados) {
					//Si el traspaso es diferente a Cero efectuar el traspaso
					if(posicion != null && posicion.getPosicionFondeo() != null && posicion.getPosicionFondeo() > 0 ) {
						existeTraspaso = true;
						PosicionDTO posicionDTO = consultaPosiciones.getConsultaPosicionService().buscarPosicionPorId(posicion);
						if(posicionDTO == null) {
							throw new BusinessException(obtenerMensajePropiedades("msgErrorInconsistenciaTablasVistasPosicion"));
						}
						// Si el traspaso no excede la posicion disponible
						if(posicionDTO.getPosicionDisponibleNumerica().longValue() >= posicionDTO.getPosicionFondeo().longValue()) {
							TraspasoLibrePagoDTO traspasoLibrePago = new TraspasoLibrePagoDTO();
							traspasoLibrePago.setCantidadOperada(posicion.getPosicionFondeo());
							traspasoLibrePago.setCuentaReceptor(cuentaDTO);
							traspasoLibrePago.setDiasVigentes(0);
							traspasoLibrePago.setDivisa(new DivisaDTO());
							traspasoLibrePago.getDivisa().setId(0);
							traspasoLibrePago.setFechaConcertacion(utilService.getCurrentDate());
							traspasoLibrePago.setFechaHoraCierreOper(this.fechaHoraCierreOper);
							traspasoLibrePago.setIdFolioReceptor(cuentaDTO.getInstitucion().getClaveTipoInstitucion() + cuentaDTO.getInstitucion().getFolioInstitucion());
							traspasoLibrePago.setIdFolioTraspasante(
								posicionDTO.getCuenta().getInstitucion().getClaveTipoInstitucion() +
								posicionDTO.getCuenta().getInstitucion().getFolioInstitucion());
							traspasoLibrePago.setIsin(posicionDTO.getEmision().getIsin());
							traspasoLibrePago.setNetoEfectivo(0.0);
							traspasoLibrePago.setPosicionTraspasante(posicionDTO);
							traspasos.add(traspasoLibrePago);
						}
						else{
							traspasoValido = false;
							break;
						}
					}
				}
				if(!existeTraspaso){
					mensajeUsuario = "No se introdujo ning\u00FAn traspaso en la lista. No se realiz\u00F3 ninguna operaci\u00F3n";
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensajeUsuario, mensajeUsuario));
				}
				else{
					if (!traspasoValido) {
						mensajeUsuario = "El traspaso no debe exceder la posicion disponible. No se realiz\u00F3 ninguna operaci\u00F3n";
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensajeUsuario, mensajeUsuario));
					}
					else {
						int i = 0;
						foliosRegistrados = "";
						recuperarCamposFirma();
						boolean debeMostrarMensaje = false;
						boolean debeLimpiarFirma = false;
						for(TraspasoLibrePagoDTO traspasoProcesable : traspasos) {
							// Si el usuario debe firmar la operacion, se recuperar la firma.
							// Si no se ha firmado, se procesan los datos y regresa el control a la pantalla para firmar
							if (isUsuarioConFacultadFirmar()) {
								//Valida la vigencia del certificado - 18-Agosto-2014 Pablo Balderas
								validarVigenciaCertificado();
								if (getIsoYaFirmado()) {
									logger.info("Los isos ya estan firmados");
									grabarOperacion(event, i);
									debeLimpiarFirma = true;
									debeMostrarMensaje = true;
								}
								else {
									procesarDatos(traspasoProcesable);
								}
							}
							else {
								procesarDatos(traspasoProcesable);
								debeMostrarMensaje = true;
								for(Posicion posicion : resultados) {
									posicion.setPosicionFondeo(0L);
								}
								cuenta = null;
								nombreCuenta = null;
								fechaHoraCierreOper = null;
								if (!isUsuarioIndeval()) {
									folioClaveInstitucion2 = nombreInstitucion = getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion();
									editarTraspasante = Boolean.FALSE;
								}
								else {
									editarTraspasante = Boolean.TRUE;
								}
							}
							i++;
						}
						totalOperaciones = isosSinFirmar.size();
						if (debeLimpiarFirma) {
							limpiaFirma();
							for(Posicion posicion : resultados){
								posicion.setPosicionFondeo(0L);
							}
							cuenta = null;
							nombreCuenta = null;
							fechaHoraCierreOper = null;
							if (!isUsuarioIndeval()) {
								folioClaveInstitucion2 = nombreInstitucion = getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion();
								editarTraspasante = Boolean.FALSE;
							}
							else {
								editarTraspasante = Boolean.TRUE;
							}
						}

						if(debeMostrarMensaje) {
							mensajeUsuario = "La operaci\u00f3n se llev\u00f3 a cabo exitosamente. Folio de la(s) operaci\u00f3n(es) : " + foliosRegistrados;
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensajeUsuario, mensajeUsuario));
						}
					}
				}
			// Si la cuenta receptor no es válida...
			}
			else {
				mensajeUsuario = "Instituci\u00F3n o Cuenta del Receptor no v\u00E1lidas";
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensajeUsuario, mensajeUsuario));
			}
		}
		catch(BusinessException businessException) {
    		addMessage(businessException.getMessage(), FacesMessage.SEVERITY_ERROR);
    	}
	}

	public boolean getIsoYaFirmado() {
		if(isosFirmados != null && isosFirmados.size() > 0) {
			return true;
		}
		return false;
	}

	public boolean getIsoListoParaFirma() {
		if(isosSinFirmar != null && isosSinFirmar.size() > 0) {
			return true;
		}
		return false;
	}
	
//	/**
//	 * Crea un dto de posicion con los datos necesarios para realizar el TLP.
//	 * @param posicion Clase de donde se toman los datos.
//	 * @return Dto
//	 */
//	private PosicionDTO crearPosicionDtoTLP(Posicion posicion) {
//		PosicionDTO posicionDTO = new PosicionDTO();
//		MercadoDTO mercadoDTO = new MercadoDTO();
//		mercadoDTO.setClaveMercado(posicion.getClaveMercado());
//		TipoValorDTO tipoValorDTO = new TipoValorDTO();
//		tipoValorDTO.setMercado(mercadoDTO);
//		EmisionDTO emisionDTO = new EmisionDTO();
//		emisionDTO.setTipoValor(tipoValorDTO);
//		posicionDTO.setEmision(emisionDTO);
//		
//		//Cuenta
//		CuentaDTO cuentaDto = new CuentaDTO();
//		 agente.setId(cuenta.getInstitucion().getClaveTipoInstitucion());
//		agente.setFolio(cuenta.getInstitucion().getFolioInstitucion());
//		agente.setCuenta(cuenta.getCuenta());
//		agente.setNombreCorto(cuenta.getInstitucion().getNombreCorto());
//		agente.setNombreCuenta(cuenta.getNombreCuenta());
//		agente.setTenencia(cuenta.getTipoTenencia().getDescripcion());
//		
//		return posicionDTO;
//	}
	
	private void procesarDatos(TraspasoLibrePagoDTO traspasoLibrePago) {
		logger.info("Entrando al metodo FondeoOperacionesBean.procesarDatos");
		boolean valido = validarDTO(traspasoLibrePago);
		if (valido) {
			String mercado = traspasoLibrePago.getPosicionTraspasante().getEmision().getTipoValor().getMercado().getClaveMercado();
			if( mercado != null ) {
				if(mercado.trim().equalsIgnoreCase("MC")) {
					calcularYRegistrarOperacionCapitales(traspasoLibrePago);
				} else if(mercado.trim().equalsIgnoreCase("MD") ||
        				mercado.trim().equalsIgnoreCase("PG") ||
        				mercado.trim().equalsIgnoreCase("PB")) {
					calcularYRegistrarOperacionDinero(traspasoLibrePago);
				}
			}
		}
	}
	
	/**
	 * Guarda la operación firmada
	 * 
	 * @param e
	 */
	public void grabarOperacion(ActionEvent e, int pos) {
		enviarOperacionABitacora(tlpVo.get(pos), isosFirmados.get(pos));
	}

	private boolean validarDTO(TraspasoLibrePagoDTO traspasoLibrePago) {
        ResultadoValidacionDTO resultadao = null;
        resultadao = validadorTraspasoLibrePago.validarDTO(traspasoLibrePago);
        if (!resultadao.isValido()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, resultadao.getMensaje(), resultadao.getMensaje()));
        }
        return resultadao.isValido();
    }

	private void calcularYRegistrarOperacionCapitales(TraspasoLibrePagoDTO traspasoLibrePago) {
		logger.info("Entrando al metodo FondeoOperacionesBean.calcularYRegistrarOperacionCapitales");

		// buscar la divisa por id
		traspasoLibrePago.setDivisa(consultaCatalogosFacade.buscarDivisaPorId(traspasoLibrePago.getDivisa().getId()));

		GetCapturaTraspasoParams registraOperacionParams = servicesCapturaOperacionViewHelper.construyeYCalculaOperacionCapitales(traspasoLibrePago);

		if (traspasoLibrePago != null) {
			String mercado = traspasoLibrePago.getPosicionTraspasante().getEmision().getTipoValor().getMercado().getClaveMercado();
			if (mercado != null) {
				String folioAsignado = null;

				folioAsignado = mercadoCapitalesService.businessRulesCapturaTraspaso(registraOperacionParams);

				if (!existeErrorEnInvocacion()) {
					TraspasoLibrePagoVO vo = new TraspasoLibrePagoVO();

					vo = servicesCapturaOperacionViewHelper.inicializaTraspasoContrapagoVO(registraOperacionParams);
					vo.setReferenciaOperacion(folioAsignado);
					vo.setTipoInstruccion(registraOperacionParams.getCveReporto());
					vo.setReferenciaMensaje(utilService.getFolio(Constantes.SEQ_REFERENCIA_MENSAJE).toString());
					vo.setBoveda(traspasoLibrePago.getPosicionTraspasante().getBoveda() != null ? traspasoLibrePago.getPosicionTraspasante().getBoveda().getNombreCorto() : null);

					logger.info("Traspaso Capitales: [" + ToStringBuilder.reflectionToString(vo) + "]");

					tlpVo.add(vo);

					if (isUsuarioConFacultadFirmar()) {
						String iso = isoHelper.creaISO(vo, false);
						isosSinFirmar.add(iso);
						hashIso.add(cdb.cipherHash(iso));
					} else {
						enviarOperacionABitacora(vo, null);
					}
				}
			}
		}
	}

	private void calcularYRegistrarOperacionDinero(TraspasoLibrePagoDTO traspasoLibrePago) {
		logger.info("Entrando al metodo FondeoOperacionesBean.calcularYRegistrarOperacionDinero");

		// buscar la divisa por id
		traspasoLibrePago.setDivisa(consultaCatalogosFacade.buscarDivisaPorId(traspasoLibrePago.getDivisa().getId()));

		RegistraOperacionParams registraOperacionParams = servicesCapturaOperacionViewHelper.construyeYCalculaOperacionDinero(traspasoLibrePago);
		String folioAsignado = mercadoDineroService.validaRegistraOperacionBusinessRules(registraOperacionParams);
		if (!existeErrorEnInvocacion()) {
			TraspasoLibrePagoVO vo = new TraspasoLibrePagoVO();

			vo = servicesCapturaOperacionViewHelper.inicializaTraspasoLibrePagoVO(registraOperacionParams);
			vo.setReferenciaOperacion(folioAsignado);
			vo.setTipoInstruccion(registraOperacionParams.getClaveReporto());
			vo.setReferenciaMensaje(utilService.getFolio(Constantes.SEQ_REFERENCIA_MENSAJE).toString());

			logger.info("Traspaso Dinero: [" + ToStringBuilder.reflectionToString(vo) + "]");

			tlpVo.add(vo);

			if (isUsuarioConFacultadFirmar()) {
				String iso = isoHelper.creaISO(vo, false);
				isosSinFirmar.add(iso);
				hashIso.add(cdb.cipherHash(iso));
			} else {
				enviarOperacionABitacora(vo, null);
			}
		}
	}
	
    private void enviarOperacionABitacora(TraspasoLibrePagoVO vo,
            String isoFirmado) {
        String folioControl = vo.getReferenciaOperacion();
        final HashMap<String, String> datosAdicionales = new HashMap<String, String>();
        datosAdicionales.put(SeguridadConstants.USR_CREDENCIAL,
                (String) FacesContext.getCurrentInstance().getExternalContext()
                        .getSessionMap().get(SeguridadConstants.TICKET_SESION));
        envioOperacionesService.grabaOperacion(vo, folioControl, false,
                datosAdicionales, null, isoFirmado);
        if (!existeErrorEnInvocacion()) {
            if (StringUtils.isNotBlank(foliosRegistrados)) {
                foliosRegistrados = foliosRegistrados + ", ";
            }
            foliosRegistrados = foliosRegistrados + folioControl;
            logger.error("********Folios Registrados: " + foliosRegistrados
                    + "\n]");

        }
    }
	
	/**
	 * Busca emisoras en el catálogo cuya descripción comiencen con el prefijo
	 * proporcionado.
	 * 
	 * @param prefijo
	 *            el prefijo a consultar en la BD.
	 * @return una lista con objetos de tipo {@link EmisoraDTO} con todas las
	 *         coincidencias encontradas.
	 */
	public List<EmisoraDTO> buscarEmisorasPorPrefijo(Object value) {
		List<EmisoraDTO> emisoras = new ArrayList<EmisoraDTO>();
		String prefijoAjustado = "";
		if (value != null) {
			prefijoAjustado = String.valueOf(value).trim().replace("*", "%");
		}
		if (value != null) {
			emisoras = consultaPosiciones.getCriterioEmision().getCriterioEmisora().buscarEmisorasPorPrefijo(prefijoAjustado);
		}
		return emisoras;
	}

	

	/**
	 * Obtiene el atributo emisoraSeleccionada
	 * 
	 * @return el emisoraSeleccionada
	 */
	public String getEmisoraSeleccionada() {
		return emisoraSeleccionada;
	}

	/**
	 * Establece el atributo emisoraSeleccionada
	 * 
	 * @param emisoraSeleccionada
	 *            el emisoraSeleccionada a establecer
	 */
	public void setEmisoraSeleccionada(String emisoraSeleccionada) {
		this.emisoraSeleccionada = emisoraSeleccionada;
		EmisoraDTO emisora = null;
		if(StringUtils.isNotBlank(emisoraSeleccionada)) {
			emisora = consultaPosiciones.getCriterioEmision().getCriterioEmisora().getConsultaEmisoraService().buscarEmisoraPorDescripcion(
				emisoraSeleccionada.toUpperCase());
			if (emisora == null) {
				emisora = new EmisoraDTO();
				// Valor para indicar que va a ir a buscar una emisora inexistente
				emisora.setId(DaliConstants.VALOR_COMBO_NINGUNO);
				emisora.setDescripcion(emisoraSeleccionada);
			}
		}
		consultaPosiciones.getCriterioEmision().getCriterioEmisora().setOpcionSeleccionada(emisora);
	}

	public TipoValorDTO getInstrumentoSeleccionado() {
		return instrumentoSeleccionado;
	}

	public void setInstrumentoSeleccionado(TipoValorDTO instrumentoSeleccionado) {
		this.instrumentoSeleccionado = instrumentoSeleccionado;
		MercadoDTO mercadoSeleccionado = consultaPosiciones.getCriterioEmision().getCriterioTipoValor().getCriterioMercado().getOpcionSeleccionada();
		if(instrumentoSeleccionado != null) {
			consultaPosiciones.getCriterioEmision().getCriterioTipoValor().setOpcionSeleccionada(instrumentoSeleccionado);
		} 
		consultaPosiciones.getCriterioEmision().getCriterioTipoValor().getCriterioMercado().setOpcionSeleccionada(mercadoSeleccionado);
		consultaPosiciones.getCriterioEmision().getCriterioTipoValor().getOpcionSeleccionada().setMercado(mercadoSeleccionado);
	}

	/**
	 * Configura el criterio de ordenamiento dela consulta principal de
	 * posiciones
	 * 
	 * @param e
	 *            ActionEvent generado durante la solicitud
	 */
	public void ordenar(ActionEvent e) {
		if (e.getComponent().getId().equals("sortCuenta") || e.getComponent().getId().equals("sortPosicion") || e.getComponent().getId().equals("sortBoveda")
				|| e.getComponent().getId().equals("sortEmisora") || e.getComponent().getId().equals("sortTv") ||  e.getComponent().getId().equals("sortSerie")) {
			if (e.getComponent().getId().equals(consultaPosiciones.getColumnaOrdenada())) {
				consultaPosiciones.setOrdenAscendente(!consultaPosiciones.isOrdenAscendente());
			} else {
				consultaPosiciones.setOrdenAscendente(true);
			}
			consultaPosiciones.setColumnaOrdenada(e.getComponent().getId());

		}
	}

	/**
	 * Inicializa los criterios de consulta
	 * 
	 * @param e
	 *            ActionEvent generado durante la solicitud
	 */
	public void limpiar(ActionEvent e) {
		inicializarCriterios();

	}

	/**
	 * Asigna las opciones predeterminadas para cuando se carga la página por
	 * primerva vez.
	 * 
	 * @return nulo, este método no requiere retornar un valor
	 */
	public String getInit() {
		nombreCuenta = "";
		tenencia = "";
		
		FacesContext ctx = FacesContext.getCurrentInstance();

		if (getInstitucionActual() != null) {
			consultaPosiciones.getCriterioCuenta().setIdInstitucion(getInstitucionActual().getId());
			nombreInstitucion = folioClaveInstitucion;
			// Reiniciar el criterio de naturaleza para el usuario participante
			// para dejar por default la naturaleza : Pasivo
			if (!isUsuarioIndeval()) {
				TipoNaturalezaDTO pasivo = new TipoNaturalezaDTO();
				pasivo.setId(TipoNaturalezaDTO.PASIVO);
				consultaPosiciones.getCriterioCuenta().getCriterioTipoTenencia().getCriterioNaturaleza().setOpcionSeleccionada(pasivo);
				institucion = getConsultaInstitucionService().buscarInstitucionPorClaveYFolio(
						this.getInstitucionActual().getClaveTipoInstitucion() + this.getInstitucionActual().getFolioInstitucion());
				folioClaveInstitucion = nombreInstitucion = getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion();
				consultaPosiciones.getCriterioCuenta().setIdInstitucion(institucion.getId());
				
				folioClaveInstitucion2 = nombreInstitucion = getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion();
				editarTraspasante = Boolean.FALSE;
				
			} else {
				if (StringUtils.isBlank(nombreInstitucion) && StringUtils.isBlank(folioClaveInstitucion)) {
					nombreInstitucion = DaliConstants.DESCRIPCION_TODOS;
				}

				editarTraspasante = Boolean.TRUE;
			}
		}

		if (ctx.getRenderResponse()) {
			if (isConsultaEjecutada()) {

				consultaPosiciones.setEmisionSeleccionada(null);
				consultaPosiciones.setBovedaSeleccionada(null);
				consultaPosiciones.setCuentaSeleccionada(null);
			}
			consultaPosiciones.getOpcionSeleccionada();
		}

		if (!ctx.getRenderKit().getResponseStateManager().isPostback(ctx)) {

			inicializarCriterios();

		}
		
		if (getCuenta() != null && getFolioClaveInstitucion2() != null) {
			if (!getCuenta().equals("") && !getFolioClaveInstitucion2().equals("")) {
				CuentaDTO cnt = consultaCatalogos.buscarCuentaPorNumeroCuentaNullSiNoExiste(getFolioClaveInstitucion2()
						+ getCuenta());
				if (cnt != null) {
					//colocacionPrimaria.getPosicionTraspasante().setCuenta(cnt);
					nombreCuenta = cnt.getNombreCuenta();
					tenencia = cnt.getTipoTenencia().getClaveTipoCuenta();
				} else {
					//nombreCuenta = cnt.getNombreCuenta();
					nombreCuenta = "";
					tenencia = "";
				}
			}
		}

		return null;
	}

	/**
	 * método que atiende las solicitudes de autocomplete del catálogo de tipos
	 * de valor.
	 * 
	 * @param prefijo
	 *            Criterio de búsqueda
	 * @return Lista con los tipos de valor encontrados
	 */
	public List<TipoValorDTO> buscarTiposValorPorPrefijo(Object prefijo) {
		return consultaCatalogos.getConsultaTipoValorService().buscarTiposDeValoresPorMercadoYPrefijo(
				consultaPosiciones.getCriterioEmision().getCriterioTipoValor().getCriterioMercado().getOpcionSeleccionada(), String.valueOf(prefijo));
	}

	/**
	 * Inicializa los criterios de consulta a las opciones predeterminadas
	 */
	private void inicializarCriterios() {
		// si es la primera vez que se visita la pagina
		setConsultaEjecutada(false);
		consultaPosiciones.setCriterioFecha(new Date());
		consultaPosiciones.getCriterioCuenta().getCriterioTipoTenencia().getCriterioNaturaleza().setOpcionSeleccionada(null);
		consultaPosiciones.getCriterioCuenta().getCriterioTipoTenencia().getCriterioTipoCuenta().setOpcionSeleccionada(null);
		consultaPosiciones.getCriterioCuenta().getCriterioTipoTenencia().setOpcionSeleccionada(null);

		consultaPosiciones.getCriterioCuenta().setOpcionSeleccionada(null);

		consultaPosiciones.getCriterioBoveda().setOpcionSeleccionada(null);
		consultaPosiciones.getCriterioEmision().getCriterioTipoValor().getCriterioMercado().setOpcionSeleccionada(null);
		consultaPosiciones.getCriterioEmision().getCriterioTipoValor().setOpcionSeleccionada(null);
		consultaPosiciones.getCriterioEmision().getCriterioEmisora().setOpcionSeleccionada(null);
		consultaPosiciones.getCriterioEmision().getCriterioSerie().setOpcionSeleccionada(null);
		consultaPosiciones.getCriterioEmision().setIsin(null);

		consultaPosiciones.getCriterioEmision().setOpcionSeleccionada(null);

		consultaPosiciones.getCriterioBoveda().setOpcionSeleccionada(null);

		emisoraSeleccionada = null;
		instrumentoSeleccionado = new TipoValorDTO();

		consultaPosiciones.setBovedaSeleccionada(null);
		consultaPosiciones.setEmisionSeleccionada(null);
		consultaPosiciones.setCuentaSeleccionada(null);

		consultaPosiciones.getCriterioBoveda().reestablecerEstadoPaginacion();
		consultaPosiciones.getCriterioEmision().reestablecerEstadoPaginacion();
		consultaPosiciones.getCriterioCuenta().reestablecerEstadoPaginacion();
		consultaPosiciones.inicializaPaginacion();
		if (consultaPosiciones.getCriterioCuenta().isTodos()) {
			consultaPosiciones.getCriterioCuenta().toggleTodos();
		}
		if (consultaPosiciones.getCriterioEmision().isTodos()) {
			consultaPosiciones.getCriterioEmision().toggleTodos();
		}
		if (consultaPosiciones.getCriterioBoveda().isTodos()) {
			consultaPosiciones.getCriterioBoveda().toggleTodos();
		}
		folioClaveInstitucion = StringUtils.EMPTY;
		nombreInstitucion = StringUtils.EMPTY;
		
		tenencia = StringUtils.EMPTY;		
		consultaPosiciones.setTraspasarValores(Boolean.FALSE);

		isosSinFirmar = new ArrayList<String>();
		totalOperaciones = 0;
		hashIso = new ArrayList<String>();
		tlpVo = new ArrayList<TraspasoLibrePagoVO>();
		
		this.limpiaCamposTraspaso();
	}
	
	private void limpiaCamposTraspaso() {
		folioClaveInstitucion2 = StringUtils.EMPTY;
		cuenta = StringUtils.EMPTY;
		nombreCuenta = StringUtils.EMPTY;
		fechaHoraCierreOper = null;
	}

	/**
	 * Determina si se debe de restaurar la paginación de los resultados
	 * principaless. Se debe restaurar una paginación despus de realizar
	 * cualquier cambio que afecte la cantidad de resultados.
	 * 
	 * @return <code>null</code>
	 */
	public String getRestaurPaginacion() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		
		if (restaurarPaginacionResultados && ctx.getRenderResponse()) {
			consultaPosiciones.setDebeLogearProyeccion(false);
			consultaPosiciones.reestablecerEstadoPaginacion();
		}
		
		restaurarPaginacionResultados = true;
		return null;
	}

	/**
	 * Obtiene las opciones en forma de una lista de {@link SelectItem} para
	 * llenar el combo de criterio de bóveda
	 * 
	 * @return Lista con las opciones válidas del criterio de bóveda
	 */
	public List<SelectItem> getOpcionesComboBoveda() {
		List<SelectItem> opcionesCombo = new ArrayList<SelectItem>();

		BovedaDTO bovedaTodos = new BovedaDTO();
		bovedaTodos.setId(-1);
		bovedaTodos.setDescripcion("TODAS");

		opcionesCombo.add(new SelectItem(bovedaTodos, bovedaTodos.getDescripcion()));

		for (BovedaDTO boveda : consultaPosiciones.getCriterioBoveda().getResultados()) {
			opcionesCombo.add(new SelectItem(boveda, boveda.getDescripcion()));
		}

		return opcionesCombo;
	}

	/**
	 * Obtiene las opciones en forma de una lista de {@link SelectItem} para
	 * llenar el combo de criterio de Naturaleza
	 * 
	 * @return Lista con las opciones validas del criterio de Naturaleza
	 */
	public List<SelectItem> getOpcionesComboNaturaleza() {
		List<SelectItem> opcionesCombo = new ArrayList<SelectItem>();

		for (TipoNaturalezaDTO nat : consultaPosiciones.getCriterioCuenta().getCriterioTipoTenencia().getCriterioNaturaleza().getResultados()) {
			opcionesCombo.add(new SelectItem(nat, nat.getDescripcion()));
		}

		return opcionesCombo;
	}

	/**
	 * Obtiene las opciones en forma de una lista de {@link SelectItem} para
	 * llenar el combo de criterio de Tipo de Cuenta
	 * 
	 * @return Lista con las opciones válidas del criterio de Tipo de Cuenta
	 */
	public List<SelectItem> getOpcionesComboTipoCuenta() {
		List<SelectItem> opcionesCombo = new ArrayList<SelectItem>();

		for (TipoCuentaDTO nat : consultaPosiciones.getCriterioCuenta().getCriterioTipoTenencia().getCriterioTipoCuenta().getResultados()) {
			opcionesCombo.add(new SelectItem(nat, nat.getDescripcion()));
		}

		return opcionesCombo;
	}

	/**
	 * Obtiene las opciones en forma de una lista de {@link SelectItem} para
	 * llenar el combo de criterio de Tipo Tenencia
	 * 
	 * @return Lista con las opciones válidas del criterio de tipo Tenencia
	 */
	public List<SelectItem> getOpcionesComboTipoTenencia() {
		List<SelectItem> opcionesCombo = new ArrayList<SelectItem>();

		TipoTenenciaDTO tenenciatodos = new TipoTenenciaDTO();
		tenenciatodos.setIdTipoCuenta(-1);
		tenenciatodos.setDescripcion("TODAS");

		opcionesCombo.add(new SelectItem(tenenciatodos, tenenciatodos.getDescripcion()));

		for (TipoTenenciaDTO nat : consultaPosiciones.getCriterioCuenta().getCriterioTipoTenencia().getResultados()) {
			opcionesCombo.add(new SelectItem(nat, nat.getDescripcion()));
		}

		return opcionesCombo;
	}

	/**
	 * Obtiene las opciones en forma de una lista de {@link SelectItem} para
	 * llenar el combo de criterio de Cuenta
	 * 
	 * @return Lista con las opciones válidas del criterio de Cuenta
	 */
	public List<SelectItem> getOpcionesComboCuenta() {
		List<SelectItem> opcionesCombo = new ArrayList<SelectItem>();

		CuentaDTO cuentaTodos = new CuentaDTO();
		cuentaTodos.setNumeroCuenta("-1");
		cuentaTodos.setDescripcion("TODAS");

		opcionesCombo.add(new SelectItem(cuentaTodos, cuentaTodos.getDescripcion()));

		for (CuentaDTO nat : consultaPosiciones.getCriterioCuenta().getResultados()) {
			opcionesCombo.add(new SelectItem(nat, nat.getDescripcion()));
		}

		return opcionesCombo;
	}

	/**
	 * Obtiene las opciones en forma de una lista de {@link SelectItem} para
	 * llenar el combo de criterio de Mercado
	 * 
	 * @return Lista con las opciones válidas del criterio de Mercado
	 */
	public List<SelectItem> getOpcionesComboMercado() {
		List<SelectItem> resultado = null;
		if (StringUtils.isBlank(papelMercado) || DaliConstants.PAPEL_MERCADO_TODOS.equals(papelMercado)) {
			resultado = consultaCatalogos.getSelectItemMercadoTodos();
		} else {
			if (DaliConstants.PAPEL_MERCADO_DINERO.equals(papelMercado)) {
				resultado = consultaCatalogos.getSelectItemsDeMercadoDinero();
			} else {
				resultado = consultaCatalogos.getSelectItemsDeMercadoCapitales();
			}
		}
		return resultado;
	}

	/**
	 * Obtiene la descripción del atributo papelMercado.
	 * 
	 * @return la descripción del atributo papelMercado.
	 */
	public String getDescripcionPapelMercado() {
		String resultado = "";
		if (DaliConstants.PAPEL_MERCADO_TODOS.equals(papelMercado)) {
			resultado = DaliConstants.DESCRIPCION_TODOS;
		} else {
			if (DaliConstants.PAPEL_MERCADO_DINERO.equals(papelMercado)) {
				resultado = DaliConstants.DESCRIPCION_MERCADO_DINERO;
			} else {
				resultado = DaliConstants.DESCRIPCION_MERCADO_CAPITALES;
			}
		}

		return resultado;
	}

	/**
	 * Prepara los valores para la ejecución de la consulta de posiciones
	 * 
	 * @param e
	 *            ActionEvent generado durante la petición
	 */
	public void buscarPosiciones(ActionEvent e) {
		if(validarFechaObligatoria(consultaPosiciones.getCriterioFecha(), false, CamposPantallaConstantes.campoFecha)) {
			mensaje=false;
			consultaPosiciones.setCuentaSeleccionada(null);
			consultaPosiciones.setEmisionSeleccionada(null);
			consultaPosiciones.setBovedaSeleccionada(null);

			if (!consultaPosiciones.getCriterioCuenta().isTodos()) {
				consultaPosiciones.getCriterioCuenta().toggleTodos();
			}
			if (!consultaPosiciones.getCriterioEmision().isTodos()) {
				consultaPosiciones.getCriterioEmision().toggleTodos();
			}
			if (!consultaPosiciones.getCriterioBoveda().isTodos()) {
				consultaPosiciones.getCriterioBoveda().toggleTodos();
			}

			if(resultados != null) {
				resultados.clear();
			}
			consultaPosiciones.recibirNotificacionResultados(null);

			if (folioClaveInstitucion != null && folioClaveInstitucion.length() == 2) {
				consultaPosiciones.getOpcionSeleccionada().getCuenta().getInstitucion().setClaveTipoInstitucion(folioClaveInstitucion);
			}

			consultaPosiciones.getCriterioEmision().reestablecerEstadoPaginacion();
			consultaPosiciones.getCriterioCuenta().reestablecerEstadoPaginacion();
			consultaPosiciones.getCriterioBoveda().reestablecerEstadoPaginacion();

			consultaPosiciones.setDebeLogearProyeccion(true);
			consultaPosiciones.reestablecerEstadoPaginacion();
			consultaPosiciones.setDebeLogearProyeccion(false);
			restaurarPaginacionResultados = false;
			consultaPosiciones.setColumnaOrdenada("sortCuenta");
			consultaPosiciones.setOrdenAscendente(true);
			resumenPosicionConsulta = null;
			this.limpiaCamposTraspaso();
			setConsultaEjecutada(true);
		}
		else {
			consultaEjecutada = false;
		}
	}

	/**
	 * Atiende el evento de cambio de elemento seleccionado del criterio de
	 * naturaleza de la cuenta. Reinicia la opción seleccionada del tipo de
	 * tenencia y de la cuenta.
	 * 
	 * @param e
	 *            Evento generado durante la solicitud
	 */
	public void cambioSelectNaturaleza(ActionEvent e) {
		consultaPosiciones.getCriterioCuenta().getCriterioTipoTenencia().setOpcionSeleccionada(null);
		consultaPosiciones.getCriterioCuenta().setOpcionSeleccionada(null);
	}

	/**
	 * Atiene el evento de cambio de elemento seleccionado del criterio de papel
	 * mercado.
	 * 
	 * @param e
	 *            Evento generado durante la solicitud
	 */
	public void cambioSelectPapelMercado(ActionEvent e) {
		MercadoDTO mercadosTodos = new MercadoDTO();
		mercadosTodos.setDescripcion("TODOS");
		if (papelMercado.equals(DaliConstants.PAPEL_MERCADO_TODOS)) {
			mercadosTodos.setId(DaliConstants.VALOR_COMBO_TODOS);
		} else {
			if (papelMercado.equals(DaliConstants.PAPEL_MERCADO_DINERO)) {
				mercadosTodos.setId(DaliConstants.ID_MERCADO_DINERO);
			} else {
				mercadosTodos.setId(DaliConstants.ID_MERCADO_CAPITALES);
			}
		}
		consultaPosiciones.getCriterioEmision().getCriterioTipoValor().getCriterioMercado().setOpcionSeleccionada(mercadosTodos);
		consultaPosiciones.getCriterioEmision().getCriterioTipoValor().setOpcionSeleccionada(null);

	}

	/**
	 * Atiende el evento de cambio de elemento seleccionado del tipo de cuenta
	 * de la cuenta. Reinicia la opción seleccionada del tipo de tenencia y de
	 * la cuenta.
	 * 
	 * @param e
	 *            Evento generado durante la solicitud
	 */
	public void cambioSelectTipoCuenta(ActionEvent e) {
		consultaPosiciones.getCriterioCuenta().getCriterioTipoTenencia().setOpcionSeleccionada(null);
		consultaPosiciones.getCriterioCuenta().setOpcionSeleccionada(null);
	}

	/**
	 * Atiende el evento de cambio de elemento seleccionado del criterio de
	 * tenencia de la cuenta. Reinicia la opción seleccionada de la cuenta.
	 * 
	 * @param e
	 *            Evento generado durante la solicitud
	 */
	public void cambioSelectTipoTenencia(ActionEvent e) {

		consultaPosiciones.getCriterioCuenta().setOpcionSeleccionada(null);
	}

	/**
	 * Atiende el evento de cambio de elemento seleccionado del criterio de
	 * mercado . Reinicia la opción seleccionada del tipo de valor y serie.
	 * 
	 * @param e
	 *            Evento generado durante la solicitud
	 */
	public void cambioSelectMercado(ActionEvent e) {

		consultaPosiciones.getCriterioEmision().getCriterioTipoValor().setOpcionSeleccionada(null);
		consultaPosiciones.getCriterioEmision().getCriterioSerie().setOpcionSeleccionada(null);
	}

	/**
	 * Atiende el evento de cambio de elemento seleccionado del tipo de valor.
	 * Reinicia la opción seleccionada del criterio serie
	 * 
	 * @param e
	 *            Evento generado durante la solicitud
	 */
	public void cambioSelectTipoValor(ActionEvent e) {

		consultaPosiciones.getCriterioEmision().getCriterioSerie().setOpcionSeleccionada(null);
	}

	public void cambioCuenta(ActionEvent e) {

	}

	/**
	 * Realiza la consulta al controlador de consulta de posicion. La consulta
	 * es realiza siempre y cuando el ciclo de vida de la solicitud este en la
	 * fase de respuesta al cliente.
	 * 
	 * @return Lista con los resultados de la consulta de posiciones
	 */
	public List<Posicion> getResultadosConsultaPosicion() {
		if (resultados == null) {
			getInitConsulta();
		}
		return resultados;

	}

	/**
	 * Inicializa la variable de resultados de consulta
	 * 
	 * @return null
	 */
	public String getInitConsulta() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		if (isUsuarioIndeval()||(!StringUtils.isEmpty(folioClaveInstitucion))){
			if (ctx.getRenderResponse()) {
				resultados = consultaPosiciones.consultarPosicion(true);
				if(resultados != null && !resultados.isEmpty()) {
					resumenPosicion = new PosicionDTO();
					long posicionTotal = 0;
					long disponibleTotal = 0;
					long noDisponibleTotal = 0;
					double valuacionTotal = 0;
					double vlorNominalTotal = 0;
					for (Posicion posicion : resultados) {
						posicionTotal += posicion.getPosicion() != null ? posicion.getPosicion() : 0;
						disponibleTotal += posicion.getPosicionDisponible() != null ? posicion.getPosicionDisponible() : 0;
						noDisponibleTotal += posicion.getPosicionNoDisponible() != null ? posicion.getPosicionNoDisponible() : 0;
						valuacionTotal += posicion.getValuacion() != null ? posicion.getValuacion() : 0;
						vlorNominalTotal += posicion.getValorNominal() != null ? posicion.getValorNominal() : 0;
					}
					resumenPosicion.setPosicion(String.valueOf(posicionTotal));
					resumenPosicion.setPosicionDisponible(String.valueOf(disponibleTotal));
					resumenPosicion.setPosicionNoDisponible(String.valueOf(noDisponibleTotal));
					resumenPosicion.setValuacionNominal(new BigDecimal(valuacionTotal));
					resumenPosicion.setEmision(new EmisionDTO());
					resumenPosicion.getEmision().setValorNominal(vlorNominalTotal);
					resumenPosicionConsulta = consultaPosiciones.getConsultaPosicionService().obtenerTotalesConsultaPosicion(consultaPosiciones.getOpcionSeleccionada());
				}
			}
			else {
				resultados = new ArrayList<Posicion>();
			}
		}
		else{
			throw new BusinessDataException("Ocurri\u00F3 un error en los permisos de consulta", "Error con la institucion participante");
		}
		return null;
	}

	/**
	 * Limpia la variable de resultados de consulta
	 * 
	 * @return null
	 */
	public String getLimpiarConsulta() {
		resultados = null;
		return null;
	}

//	/**
//	 * Prepara los datos para generar reportes
//	 * 
//	 * @return Cadena para la regla de bavegacion para selecccion de tipo de
//	 *         Reporte
//	 */
//	public void generarReportes(ActionEvent e) {
//		reiniciarEstadoPeticion();
//		this.setResultados(consultaPosiciones.getResultados());
//		PosicionDTO resumenTipoValor = null;
//		TipoValorDTO tipoValorEnTurno = null;
//		BigDecimal subtotalDisponible = new BigDecimal("0.0");
//		BigDecimal totalDisponible = new BigDecimal("0.0");
//		BigDecimal totalNoDisponible = new BigDecimal("0.0");
//		BigDecimal totalValuacionNominal = new BigDecimal("0.0");
//		BigDecimal subtotalNoDisponible = new BigDecimal("0.0");
//		BigDecimal subtotalValuacion = new BigDecimal("0.0");
//		for(int i=0; i<resultados.size(); i++) {
//			
//			PosicionDTO posicion = resultados.get(i);
//			if(tipoValorEnTurno == null) {
//				tipoValorEnTurno = posicion.getEmision().getTipoValor();
//			}
//			
//			if(!posicion.getEmision().getTipoValor().equals(tipoValorEnTurno)) {
//				
//				resumenTipoValor = new PosicionDTO();
//				resumenTipoValor.getCuenta().setNumeroCuenta("SUBTOTAL");
//				resumenTipoValor.getEmision().setTipoValor(tipoValorEnTurno);
//				resumenTipoValor.getEmision().getEmisora().setDescripcion(StringUtils.EMPTY);
//				resumenTipoValor.setPosicionDisponibleNumerica(subtotalDisponible);
//				resumenTipoValor.setPosicionNoDisponibleNumerica(subtotalNoDisponible);
//				resumenTipoValor.setPosicionNumerica(subtotalDisponible.add(subtotalNoDisponible));
//				resumenTipoValor.setValuacionNominal(subtotalValuacion);
//				
//				totalDisponible = totalDisponible.add(subtotalDisponible);
//				totalNoDisponible = totalNoDisponible.add(subtotalNoDisponible);
//				totalValuacionNominal = totalValuacionNominal.add(subtotalValuacion);
//				
//				subtotalNoDisponible = new BigDecimal("0.0");
//				subtotalDisponible = new BigDecimal("0.0");
//				subtotalValuacion = new BigDecimal("0.0");
//				
//				resultados.add(i++, resumenTipoValor);
//				
//				tipoValorEnTurno = posicion.getEmision().getTipoValor();
//			}
//			
//			posicion.setPosicionDisponibleNumerica(new BigDecimal(posicion.getPosicionDisponible()));
//			subtotalDisponible = subtotalDisponible.add(posicion.getPosicionDisponibleNumerica());
//			posicion.setPosicionNoDisponibleNumerica(new BigDecimal(posicion.getPosicionNoDisponible()));
//			subtotalNoDisponible = subtotalNoDisponible.add(posicion.getPosicionNoDisponibleNumerica());
//			posicion.setPosicionNumerica(new BigDecimal(posicion.getPosicion()));
//			subtotalValuacion = subtotalValuacion.add(posicion.getValuacionNominal());
//		}
//		resumenTipoValor = new PosicionDTO();
//		resumenTipoValor.getCuenta().setNumeroCuenta("SUBTOTAL");
//		resumenTipoValor.getEmision().setTipoValor(tipoValorEnTurno);
//		resumenTipoValor.getEmision().getEmisora().setDescripcion(StringUtils.EMPTY);
//		resumenTipoValor.setPosicionDisponibleNumerica(subtotalDisponible);
//		resumenTipoValor.setPosicionNoDisponibleNumerica(subtotalNoDisponible);
//		resumenTipoValor.setPosicionNumerica(subtotalDisponible.add(subtotalNoDisponible));
//		resumenTipoValor.setValuacionNominal(subtotalValuacion);
//		
//		totalDisponible = totalDisponible.add(subtotalDisponible);
//		totalNoDisponible = totalNoDisponible.add(subtotalNoDisponible);
//		totalValuacionNominal = totalValuacionNominal.add(subtotalValuacion);
//		
//		resultados.add(resumenTipoValor);
//		
//		resumenPosicion = new PosicionDTO();
//		resumenPosicion.getCuenta().setNumeroCuenta("TOTAL");
//		resumenPosicion.setPosicionDisponibleNumerica(totalDisponible);
//		resumenPosicion.setPosicionNoDisponibleNumerica(totalNoDisponible);
//		resumenPosicion.setPosicionNumerica(totalDisponible.add(totalNoDisponible));
//		resumenPosicion.setValuacionNominal(totalValuacionNominal);
//	}

	/**
	 * Invoca las funciones de navegación de página de la consulta principal de
	 * la pantalla
	 * 
	 * @param e
	 *            Evento generado durante la solicitud
	 */
	public void navegarPorResultados(ActionEvent e) {
		String navegacion = (String) e.getComponent().getAttributes().get("navegacion");

		try {
			consultaPosiciones.getClass().getMethod(navegacion).invoke(consultaPosiciones);
		} catch (Exception ex) {
			logger.error("Error de invocación de método al navega por los resultados principales", ex);
		}

		restaurarPaginacionResultados = false;
	}

	/**
	 * Genera un mapa con los parámetros de la consulta de posiciones.
	 * 
	 * @return Map mapa con los parámetros de la consulta
	 */
	@Override
	protected Map<String, Object> llenarParametros() {
		Map<String, Object> parametros = new HashMap<String, Object>();

		parametros.put(ReportesConstants.CUENTA_PARAMETER, consultaPosiciones.getCuentaSeleccionada().getDescripcion());
		parametros.put(ReportesConstants.NATURALEZA_PARAMETER, consultaPosiciones.getCuentaSeleccionada().getTipoTenencia().getTipoNaturaleza()
				.getDescripcion());
		parametros.put(ReportesConstants.TIPO_CUENTA_PARAMETER, consultaPosiciones.getCuentaSeleccionada().getTipoTenencia().getTipoCuenta().getDescripcion());
		parametros.put(ReportesConstants.TIPO_TENENCIA_PARAMETER, consultaPosiciones.getCuentaSeleccionada().getTipoTenencia().getDescripcion());
		parametros.put(ReportesConstants.TIPO_MERCADO_PARAMETER, consultaPosiciones.getEmisionSeleccionada().getTipoValor().getMercado().getDescripcion());
		parametros.put(ReportesConstants.ISIN_PARAMETER, consultaPosiciones.getEmisionSeleccionada().getIsin());
		parametros.put(ReportesConstants.TV_PARAMETER, consultaPosiciones.getEmisionSeleccionada().getTipoValor().getClaveTipoValor());
		parametros.put(ReportesConstants.EMISORA_PARAMETER, consultaPosiciones.getEmisionSeleccionada().getEmisora().getDescripcion());
		parametros.put(ReportesConstants.SERIE_PARAMETER, consultaPosiciones.getEmisionSeleccionada().getSerie().getDescripcion());
		parametros.put(ReportesConstants.CUPON_PARAMETER, consultaPosiciones.getEmisionSeleccionada().getCupon());
		parametros.put(ReportesConstants.BOVEDA_PARAMETER, consultaPosiciones.getBovedaSeleccionada().getDescripcion());
		parametros.put(ReportesConstants.FECHA_PARAMETER, consultaPosiciones.getCriterioFecha());
		parametros.put(ReportesConstants.RESULTADOS_PARAMETER, consultaPosiciones.getResultados());
		return parametros;
	}
	
	/**
	 * Toma del request los valores de los campos referentes a la firma electrúnica
	 */
	@SuppressWarnings("unchecked")
	protected void recuperarCamposFirma(){
		Map<String,String[]> params = ((HttpServletRequest)FacesContext.
				getCurrentInstance().getExternalContext().getRequest()).getParameterMap();
		logger.info("*******Total de operaciones 1: [" + totalOperaciones + "]");
		if(isosSinFirmar != null && totalOperaciones > 0){
    		isosFirmados = new ArrayList<String>();

    		String numeroDeSerie=(String)params.get("numeroSerie")[0];
    		String localIsoSinfirma=null;
    		String localFirmaIso=null;
    		StringBuilder isoCompleto=null;

    		for(int i=1;i<=totalOperaciones;i++){
    			if(params.get("isoFirmado"+i) != null && params.get("isoFirmado"+i).length >= 1
    					&& params.get("isoFirmado"+i)[0] != null && params.get("isoFirmado"+i)[0].trim().length() > 0) {
    				isoCompleto = new StringBuilder();
    				localIsoSinfirma=params.get("isoSinFirmar"+i)[0].replace("\r\n","\n");
    				localFirmaIso=params.get("isoFirmado"+i)[0].replace("\r\n","\n");
    				isoCompleto.append(localIsoSinfirma)
    					.append(numeroDeSerie).append("\n").append("{SHA1withRSA}").append(localFirmaIso);
    				isosFirmados.add(isoCompleto.toString());
    			} else if(params.get("isoSinFirmar"+i) != null && params.get("isoSinFirmar"+i).length >= 1) {
    				isosSinFirmar.add(params.get("isoSinFirmar"+i)[0].replace("\r\n","\n"));
    			}
    		}
    		
    	}
	}
	
	/**
	 * Obtiene el campo consultaPosiciones
	 * 
	 * @return consultaPosiciones
	 */
	public ConsultaDePosiciones getConsultaPosiciones() {
		return consultaPosiciones;
	}

	/**
	 * Asigna el valor del campo consultaPosiciones
	 * 
	 * @param consultaPosiciones
	 *            el valor de consultaPosiciones a asignar
	 */
	public void setConsultaPosiciones(ConsultaDePosiciones consultaPosiciones) {
		this.consultaPosiciones = consultaPosiciones;
	}

	/**
	 * Obtiene el campo consultaEjecutada
	 * 
	 * @return consultaEjecutada
	 */
	public boolean isConsultaEjecutada() {
		return consultaEjecutada;
	}

	/**
	 * Asigna el valor del campo consultaEjecutada
	 * 
	 * @param consultaEjecutada
	 *            el valor de consultaEjecutada a asignar
	 */
	public void setConsultaEjecutada(boolean consultaEjecutada) {
		this.consultaEjecutada = consultaEjecutada;
	}

	/**
	 * método para obtener el atributo cuentaSeleccionada
	 * 
	 * @return el atributo cuentaSeleccionada
	 */
	public String getCuentaSeleccionada() {
		String cuenta = null;
		List<CuentaDTO> resultados = null;
		if (consultaPosiciones.getCriterioCuenta().getCriterioTipoTenencia().getOpcionSeleccionada().getIdTipoCuenta() > 0 && !isUsuarioIndeval()) {
			resultados = consultaPosiciones.getCriterioCuenta().getResultados();
		}

		// Si existe solo una cuenta, colocarla como predeterminada
		if (resultados != null && resultados.size() == 1) {
			consultaPosiciones.getCriterioCuenta().setOpcionSeleccionada(resultados.get(0));
		}
		cuenta = consultaPosiciones.getCriterioCuenta().getOpcionSeleccionada().getNumeroCuenta().equals("-1") ? "TODAS" : consultaPosiciones
				.getCriterioCuenta().getOpcionSeleccionada().getNumeroCuenta();

		if (cuenta.length() == 9) {
			cuenta = cuenta.substring(5);
		}

		return cuenta;
	}

	/**
	 * Obtiene el campo resumenPosicion
	 * 
	 * @return resumenPosicion
	 */
	public PosicionDTO getResumenPosicion() {
		return resumenPosicion;
	}

	/**
	 * Asigna el valor del campo resumenPosicion
	 * 
	 * @param resumenPosicion
	 *            el valor de resumenPosicion a asignar
	 */
	public void setResumenPosicion(PosicionDTO resumenPosicion) {
		this.resumenPosicion = resumenPosicion;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.presentation.controller.common.ControllerBase#ejecutarConsultaReporte()
	 */
	@Override
	protected Collection<? extends Object> ejecutarConsultaReporte() {
		List<Object> datosReporte = new ArrayList<Object>();
		datosReporte.add(StringUtils.EMPTY);
		return datosReporte;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.presentation.controller.common.ControllerBase#getNombreReporte()
	 */
	@Override
	protected String getNombreReporte() {
		return ReportesConstants.REPORTE_CONSULTA_POSICIONES;
	}

	/**
	 * Obtiene el valor del atributo nombreInstitucion
	 * 
	 * @return el valor del atributo nombreInstitucion
	 */
	public String getNombreInstitucion() {
		return nombreInstitucion;
	}

	/**
	 * Establece el valor del atributo nombreInstitucion
	 * 
	 * @param nombreInstitucion
	 *            el valor del atributo nombreInstitucion a establecer.
	 */
	public void setNombreInstitucion(String nombreInstitucion) {
		this.nombreInstitucion = nombreInstitucion;
	}

	/**
	 * Establece el folio y la clave de la institución.
	 * 
	 * @param folioClaveInstitucion
	 *            el folio y la clave de la institución a establecer.
	 */
	public void setFolioClaveInstitucion(String folioClaveInstitucion) {
		this.folioClaveInstitucion = folioClaveInstitucion;

		if (folioClaveInstitucion.length() >= 5) {
			this.folioClaveInstitucion = folioClaveInstitucion.substring(0, 5);

			institucion = getConsultaInstitucionService().buscarInstitucionPorClaveYFolio(this.folioClaveInstitucion);
			if (institucion != null) {
				consultaPosiciones.getCriterioCuenta().setIdInstitucion(institucion.getId());
				nombreInstitucion = getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion();
			} else {
				nombreInstitucion = "TODAS";
			}
		} else {
			consultaPosiciones.getCriterioCuenta().setIdInstitucion(-1);
			if (folioClaveInstitucion.length() == 2) {
				consultaPosiciones.getCriterioCuenta().getOpcionSeleccionada().getInstitucion().setClaveTipoInstitucion(folioClaveInstitucion);
				nombreInstitucion = folioClaveInstitucion;
			}
		}

	}

	/**
	 * Obtiene el valor del atributo resumenPosicionConsulta
	 * 
	 * @return el valor del atributo resumenPosicionConsulta
	 */
	public TotalesPosicionTO getResumenPosicionConsulta() {
		return resumenPosicionConsulta;
	}

	/**
	 * Establece el valor del atributo resumenPosicionConsulta
	 * 
	 * @param resumenPosicionConsulta
	 *            el valor del atributo resumenPosicionConsulta a establecer
	 */
	public void setResumenPosicionConsulta(TotalesPosicionTO resumenPosicionConsulta) {
		this.resumenPosicionConsulta = resumenPosicionConsulta;
	}

	/**
	 * Obtiene el valor del atributo restaurarPaginacionResultados
	 * 
	 * @return el valor del atributo restaurarPaginacionResultados
	 */
	public boolean isRestaurarPaginacionResultados() {
		return restaurarPaginacionResultados;
	}

	/**
	 * Establece el valor del atributo restaurarPaginacionResultados
	 * 
	 * @param restaurarPaginacionResultados
	 *            el valor del atributo restaurarPaginacionResultados a
	 *            establecer
	 */
	public void setRestaurarPaginacionResultados(boolean restaurarPaginacionResultados) {
		this.restaurarPaginacionResultados = restaurarPaginacionResultados;
	}

	/**
	 * Obtiene el valor del atributo resultados
	 * 
	 * @return el valor del atributo resultados
	 */
	public List<Posicion> getResultados() {
		return resultados;
	}

	/**
	 * Establece el valor del atributo resultados
	 * 
	 * @param resultados
	 *            el valor del atributo resultados a establecer
	 */
	public void setResultados(List<Posicion> resultados) {
		this.resultados = resultados;
	}

	/**
	 * Obtiene el valor del atributo institucion
	 * 
	 * @return el valor del atributo institucion
	 */
	public InstitucionDTO getInstitucion() {
		return institucion;
	}

	/**
	 * Establece el valor del atributo institucion
	 * 
	 * @param institucion
	 *            el valor del atributo institucion a establecer
	 */
	public void setInstitucion(InstitucionDTO institucion) {
		this.institucion = institucion;
	}

	/**
	 * Obtiene el valor del atributo papelMercado
	 * 
	 * @return el valor del atributo papelMercado
	 */
	public String getPapelMercado() {
		return papelMercado;
	}

	/**
	 * Establece el valor del atributo papelMercado
	 * 
	 * @param papelMercado
	 *            el valor del atributo papelMercado a establecer
	 */
	public void setPapelMercado(String papelMercado) {
		this.papelMercado = papelMercado;
	}

	/**
	 * Obtiene el valor del atributo folioClaveInstitucion
	 * 
	 * @return el valor del atributo folioClaveInstitucion
	 */
	public String getFolioClaveInstitucion() {
		return folioClaveInstitucion;
	}

	public boolean isMensaje() {
		return mensaje;
	}

	public void setMensaje(boolean mensaje) {
		this.mensaje = mensaje;
	}

	public DTOValidator getValidadorTraspasoLibrePago() {
		return validadorTraspasoLibrePago;
	}

	public void setValidadorTraspasoLibrePago(
			DTOValidator validadorTraspasoLibrePago) {
		this.validadorTraspasoLibrePago = validadorTraspasoLibrePago;
	}

	public ConsultaCatalogosFacade getConsultaCatalogosFacade() {
		return consultaCatalogosFacade;
	}

	public void setConsultaCatalogosFacade(
			ConsultaCatalogosFacade consultaCatalogosFacade) {
		this.consultaCatalogosFacade = consultaCatalogosFacade;
	}

	public ServicesCapturaOperacionViewHelper getServicesCapturaOperacionViewHelper() {
		return servicesCapturaOperacionViewHelper;
	}

	public void setServicesCapturaOperacionViewHelper(
			ServicesCapturaOperacionViewHelper servicesCapturaOperacionViewHelper) {
		this.servicesCapturaOperacionViewHelper = servicesCapturaOperacionViewHelper;
	}

	public MercadoCapitalesService getMercadoCapitalesService() {
		return mercadoCapitalesService;
	}

	public void setMercadoCapitalesService(
			MercadoCapitalesService mercadoCapitalesService) {
		this.mercadoCapitalesService = mercadoCapitalesService;
	}

	public MercadoDineroService getMercadoDineroService() {
		return mercadoDineroService;
	}

	public void setMercadoDineroService(MercadoDineroService mercadoDineroService) {
		this.mercadoDineroService = mercadoDineroService;
	}

	public UtilServices getUtilService() {
		return utilService;
	}

	public void setUtilService(UtilServices utilService) {
		this.utilService = utilService;
	}

	public EnvioOperacionesService getEnvioOperacionesService() {
		return envioOperacionesService;
	}

	public void setEnvioOperacionesService(
			EnvioOperacionesService envioOperacionesService) {
		this.envioOperacionesService = envioOperacionesService;
	}

	public String getFolioClaveInstitucion2() {
		return folioClaveInstitucion2;
	}

	public void setFolioClaveInstitucion2(String folioClaveInstitucion) {
		this.folioClaveInstitucion2 = folioClaveInstitucion;

		if (folioClaveInstitucion.length() >= 5) {
			this.folioClaveInstitucion2 = folioClaveInstitucion.substring(0, 5);

			institucion = getConsultaInstitucionService().buscarInstitucionPorClaveYFolio(this.folioClaveInstitucion2);
			if (institucion != null) {
				consultaPosiciones.getCriterioCuenta().setIdInstitucion(institucion.getId());
				nombreInstitucion = getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion();
			} else {
				nombreInstitucion = "TODAS";
			}
		} else {
			consultaPosiciones.getCriterioCuenta().setIdInstitucion(-1);
			if (folioClaveInstitucion.length() == 2) {
				consultaPosiciones.getCriterioCuenta().getOpcionSeleccionada().getInstitucion().setClaveTipoInstitucion(folioClaveInstitucion);
				nombreInstitucion = folioClaveInstitucion;
			}
		}

	}
	
	public String seleccionaNombreCuentayTenencia(ActionEvent evt)
	{
		if (getCuenta() != null && getFolioClaveInstitucion2() != null) {
			if (!getCuenta().equals("") && !getFolioClaveInstitucion2().equals("")) {
				CuentaDTO cnt = consultaCatalogos.buscarCuentaPorNumeroCuentaNullSiNoExiste(getFolioClaveInstitucion2()
						+ getCuenta());
				if (cnt != null) {
					//colocacionPrimaria.getPosicionTraspasante().setCuenta(cnt);
					nombreCuenta = cnt.getNombreCuenta();
					tenencia = cnt.getTipoTenencia().getClaveTipoCuenta();
				} else {
					//nombreCuenta = cnt.getNombreCuenta();
					nombreCuenta = "";
					tenencia = "";
				}
			}
		}
		
		return null;
	}

	public String getCuenta() {
		return cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	public Date getFechaHoraCierreOper() {
		return fechaHoraCierreOper;
	}

	public void setFechaHoraCierreOper(Date fechaHoraCierreOper) {
		this.fechaHoraCierreOper = fechaHoraCierreOper;
	}

	public String getNombreCuenta() {
		return nombreCuenta;
	}

	public void setNombreCuenta(String nombreCuenta) {
		this.nombreCuenta = nombreCuenta;
	}

	public String getTenencia() {
		return tenencia;
	}

	public void setTenencia(String tenencia) {
		this.tenencia = tenencia;
	}

	public boolean isEditarTraspasante() {
		return editarTraspasante;
	}

	public void setEditarTraspasante(boolean editarTraspasante) {
		this.editarTraspasante = editarTraspasante;
	}

	/**
	 * @return the foliosRegistrados
	 */
	public String getFoliosRegistrados() {
		return foliosRegistrados;
	}

	/**
	 * @param foliosRegistrados the foliosRegistrados to set
	 */
	public void setFoliosRegistrados(String foliosRegistrados) {
		this.foliosRegistrados = foliosRegistrados;
	}

	public List<String> getIsosSinFirmar() {
		return isosSinFirmar;
	}

	public void setIsosSinFirmar(List<String> isosSinFirmar) {
		this.isosSinFirmar = isosSinFirmar;
	}

	public List<String> getHashIso() {
		return hashIso;
	}

	public void setHashIso(List<String> hashIso) {
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

	/**
	 * @return the cdb
	 */
	public CifradorDescifradorBlowfish getCdb() {
		return cdb;
	}

	/**
	 * @param cdb the cdb to set
	 */
	public void setCdb(CifradorDescifradorBlowfish cdb) {
		this.cdb = cdb;
	}

	public int getTotalOperaciones() {
		return totalOperaciones;
	}

	public void setTotalOperaciones(int totalOperaciones) {
		this.totalOperaciones = totalOperaciones;
	}

	public List<String> getIsosFirmados() {
		return isosFirmados;
	}

	public void setIsosFirmados(List<String> isosFirmados) {
		this.isosFirmados = isosFirmados;
	}

	public List<TraspasoLibrePagoVO> getTlpVo() {
		return tlpVo;
	}

	public void setTlpVo(List<TraspasoLibrePagoVO> tlpVo) {
		this.tlpVo = tlpVo;
	}

	/**
	 * Obtiene el criterio fecha como cadena.
	 * @return Criterio fecha como cadena.
	 */
	public String getCriterioFechaStr() {
		return FormatUtil.DateToShortString(consultaPosiciones.getCriterioFecha());
	}

	/**
	 * Método para obtener el atributo cero
	 * @return El atributo cero
	 */
	public Double getCero() {
		return cero;
	}

	/**
	 * Método para establecer el atributo cero
	 * @param cero El valor del atributo cero a establecer.
	 */
	public void setCero(Double cero) {
		this.cero = cero;
	}
}

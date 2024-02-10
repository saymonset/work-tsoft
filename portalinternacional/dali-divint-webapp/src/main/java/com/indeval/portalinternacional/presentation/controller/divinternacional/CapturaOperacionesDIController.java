/**
 * 2H Software - Bursatec - INDEVAL Portal DALI
 *
 * Jul 2, 2008
 */
package com.indeval.portalinternacional.presentation.controller.divinternacional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

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
import com.indeval.portaldali.persistence.dao.common.CuponDao;
import com.indeval.portaldali.persistence.dao.common.EmisionDao;
import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portaldali.persistence.modelo.CuentaNombrada;
import com.indeval.portaldali.persistence.modelo.Cupon;
import com.indeval.portaldali.persistence.modelo.Emision;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portaldali.persistence.modelo.PosicionNombrada;
import com.indeval.portaldali.persistence.modelo.TipoInstitucion;
import com.indeval.portalinternacional.common.util.AgenteViewHelper;
import com.indeval.portalinternacional.common.util.CifradorDescifradorBlowfish;
import com.indeval.portalinternacional.common.util.IsoHelper;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.EmisionesConsultasService;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.CatBic;
import com.indeval.portalinternacional.middleware.servicios.modelo.OperacionSic;
import com.indeval.portalinternacional.middleware.servicios.modelo.SicDetalle;
import com.indeval.portalinternacional.persistence.dao.OperacionSicDao;
import com.indeval.portalinternacional.persistence.dao.SettlementDisciplineRegimeDao;
import com.indeval.portalinternacional.presentation.controller.common.CapturaOperacionesController;
import com.indeval.protocolofinanciero.api.vo.TraspasoContraPagoVO;
import com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO;

/**
 * Controller para la Captura de Operaciones de Divisi?n Internacional
 * 
 * @author 
 * @version 1.0
 */
public class CapturaOperacionesDIController extends CapturaOperacionesController {

	/**
	 * Objeto que representa al captura de operaciones en Divisi?n Internacional
	 */
	private OperacionSic operacionSic = new OperacionSic();

	/** bandera para saber si es contrapago */
	private boolean contrapago = false;
	
	/** bandera para saber si es contrapago */
	private boolean recepcion = false;
	
	private String liquidacionParcial; 

	/** El identificador y el folio del traspasante */
	private String idFolioTraspasante;

	/** Cuenta del Traspasante */
	private String cuentaTraspasante;

	/** Institucion */
	private String nombreInstitucion;

	/** Tenencia */
	private String tenencia;

	/** Nombre de la cuenta */
	private String nombreCuenta;

	/** Objeto que representa la emision en la pantalla de captura */
	private EmisionVO emisionVO = new EmisionVO();

	/** Servicio utilitario para obtener las fechas */
	private DateUtilService dateUtilService;

	/** Servicio utilitario para la secuencia */
	private UtilService utilService;

	/** Dao que obtiene la Cuenta Nombrada */
	private CuentaNombradaDao cuentaNombradaDao;

	/** Dao de division internacional */
	private OperacionSicDao operacionSicDao;

	/** Servicio para obtener la institucion */
	private ConsultaCatalogoService consultaCatService;

	/** Servicion para grabar la operacion de division internacional */
	private DivisionInternacionalService divisionInternacionalService;

	/** Lista que representa los custodios */
	private List<SelectItem> listaCustodios = new ArrayList<SelectItem>();

	/** Lista que representa los depositantes */
	private List<SelectItem> listaDepositantes = new ArrayList<SelectItem>();

	/** Saldo Actual */
	private Long saldoActual;

	/** Dao para obtener el cupon */
	private CuponDao cuponDao;
	
	/** Dao para Settlement Partial **/
	SettlementDisciplineRegimeDao settlementDisciplineRegimeDao;

	/** Saldo Disponible */
	private Long saldoDisponible;

	protected CifradorDescifradorBlowfish cdb = new CifradorDescifradorBlowfish();

	/**
	 * traspaso libre de pago para grabar Operacion
	 */
	private TraspasoLibrePagoVO tlpvo = new TraspasoLibrePagoVO();
	
	/**
	 * traspaso contra pago para grabrar Operacion
	 */
	private TraspasoContraPagoVO dvpvo= new TraspasoContraPagoVO();

	

	/**
	 * Objeto para insertar cuando el tipo de operacion sea Entrega
	 */
//	private GrabaOperacionParams params = null;

	/**
	 * Cadena estatica para obtener la referencia del mensaje
	 */
	static final String SEQ_REFERENCIA_MENSAJE = "SEQ_REFERENCIA_MENSAJE";

	/**
	 * Dao para el acceso a la consulta de emisiones
	 */
	private EmisionDao emisionDao = null;

	/** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(CapturaOperacionesDIController.class);

    /**
     * Servicio para la consulta de emisiones
     */
	private EmisionesConsultasService emisionesConsultasService;

	/**
	 * Constructor de Captura Operaciones
	 */
	public CapturaOperacionesDIController() {
		inicializar(null);
	}

	/**
	 * Asigna las opciones predeterminadas para cuando se carga la p?gina por
	 * primerva vez.
	 * 
	 * @return nulo, este m?todo no requiere retornar un valor
	 */
	public String getInit() {
        boolean revisoCB = false;

		try {

			if (idFolioTraspasante == null
					|| StringUtils.isEmpty(idFolioTraspasante)) {
				if (getAgenteFirmado() != null
						&& getAgenteFirmado().getId() != null
						&& getAgenteFirmado().getFolio() != null) {
					idFolioTraspasante = AgenteViewHelper
							.obtenerClaveTipoInstitucionYFolio(
									getAgenteFirmado().getId(),
									getAgenteFirmado().getFolio());

				}
			}

			// Se obtiene el nombre de la instituci?n
			if (StringUtils.isNotBlank(idFolioTraspasante)) {
				Institucion ins = consultaCatService
						.findInstitucionByClaveFolio(idFolioTraspasante);
				if (ins != null) {
					nombreInstitucion = consultaCatService
							.findInstitucionByClaveFolio(idFolioTraspasante)
							.getNombreCorto();
				}
			}

			// Se obtiene la tenencia
			if (StringUtils.isNotBlank(idFolioTraspasante)
					&& StringUtils.isNotEmpty(cuentaTraspasante)) {

				// validar la institucion por falta del autocompleter
				Institucion institucion = consultaCatService
						.findInstitucionByClaveFolio(idFolioTraspasante);
				if (institucion != null) {
					CuentaNombrada cta = cuentaNombradaDao
							.findCuenta(AgenteViewHelper.crearAgenteVO(
									idFolioTraspasante, cuentaTraspasante));
					if (cta != null) {
						operacionSic.setCuentaNombrada(cta);
						tenencia = operacionSic.getCuentaNombrada()
								.getTipoCuenta().getClaveTipoCuenta();

						nombreCuenta = cta.getNombreCuenta();
					}
				} else {
					addErrorMessage("Error en la Clave del Agente");
				}

			}
			operacionSic.setBoveda(new Boveda());
			operacionSic.setFechaOperacion(dateUtilService.getCurrentDate());

			/*
			 * Modificacion 15/03/2012 se comentan estas fechas para que se tome
			 * la informacion capturada
			 */
			if (!(operacionSic.getFechaLiquidacion() == null)) {
				operacionSic.setFechaLiquidacion(operacionSic
						.getFechaLiquidacion());
				//la fecha notificacion se queda fija con el valor de la fecha actual
				//operacionSic.setFechaNotificacion(operacionSic.getFechaNotificacion());
				operacionSic.setFechaNotificacion(dateUtilService.getFechaHoraCero(dateUtilService.getCurrentDate()));
			} else {
				operacionSic.setFechaLiquidacion(dateUtilService
						.getCurrentDate());
				//la fecha notificacion se queda fija con el valor de la fecha actual
				//operacionSic.setFechaNotificacion(dateUtilService.getCurrentDate());
				operacionSic.setFechaNotificacion(dateUtilService.getFechaHoraCero(dateUtilService.getCurrentDate()));
			}

			/*
			 * Modificacion 19/04/2012 se quita condicion para reiniciar el
			 * catalogo de divisas con un pre llenado
			 */
			// settear al catogo de divisas la llave USD (US DOLLAR)
			operacionSic.setDivisa("USD");
			operacionSic.setImporte(BigDecimal.ZERO);

			// obtener datos de la posicion
			getBuscarEmisionPorIsinCuenta();

			// obtenerCustodios
			getObtenerCustodios();

			// se revisa si la emision no se encuentra involucrada en un cambio de boveda.
			if (this.listaDepositantes != null && this.listaDepositantes.size() > 0) {
                revisoCB = true;
			    this.revisaEmisionEnCambioBoveda();
			}

		} catch (BusinessException e) {
			log.error("Ocurrio un error:",e);
			agregarMensaje(e);
			if (revisoCB) {
			    this.limpiar(null);
                this.emisionVO = new EmisionVO();
                this.emisionVO.setSerie(null);
                this.emisionVO.setIsin(null);
                revisoCB = false;
			}
			super.limpiarCampos();
		} catch (Exception e) {
			log.error("Ocurrio un error:",e);
			agregarMensaje(e);
			super.limpiarCampos();
		} catch (Throwable e) {
			log.error("Ocurrio un error:",e);
			agregarMensaje(e);
			super.limpiarCampos();
		}
		return null;
	}

	/**
	 * Asigna las opciones predeterminadas para cuando se carga la p?gina por
	 * primerva vez.
	 * 
	 * @return nulo, este m?todo no requiere retornar un valor
	 */
	public void inicializar(ActionEvent ev) {
		this.setOperacionSic(new OperacionSic());
		this.getOperacionSic().setBoveda(new Boveda());
		this.getOperacionSic().setCatBic(new CatBic());
		this.getOperacionSic().setSicDetalle(new SicDetalle());
		this.getOperacionSic().getCatBic()
				.setCuentaNombrada(new CuentaNombrada());
		this.getOperacionSic().getCatBic().getCuentaNombrada()
				.setInstitucion(new Institucion());
		this.getOperacionSic().getCatBic().getCuentaNombrada().getInstitucion()
				.setTipoInstitucion(new TipoInstitucion());
		this.getOperacionSic().setTipoTraspaso(Constantes.TRASPASO_CONTRA);

		cuentaTraspasante = new String();
		contrapago = true;
		tenencia = new String();
		emisionVO = new EmisionVO();
		saldoDisponible = new Long(0);
		saldoActual = new Long(0);
		recepcion = false;
		liquidacionParcial = "NO";
		
		// settear al catogo de divisas la llave USD (US DOLLAR)
		operacionSic.setDivisa("USD");
		// limpiar la lista de depositantes
		listaDepositantes = new ArrayList<SelectItem>();
		// limpiar la lista de custodios
		listaCustodios = new ArrayList<SelectItem>();

	}

	/*
	 * Metodo que inicializa solo emisionVO y OperacionSic
	 */
	public void inicializarParcial(ActionEvent ev) {
		this.getOperacionSic().setBoveda(new Boveda());
		this.getOperacionSic().setCatBic(new CatBic());

		this.getOperacionSic().getCatBic()
				.setCuentaNombrada(new CuentaNombrada());
		this.getOperacionSic().getCatBic().getCuentaNombrada()
				.setInstitucion(new Institucion());
		this.getOperacionSic().getCatBic().getCuentaNombrada().getInstitucion()
				.setTipoInstitucion(new TipoInstitucion());

		this.getOperacionSic().setCantidadTitulos(new BigInteger("0"));
		this.getOperacionSic().setEmision(new Emision());
		operacionSic.setDivisa("USD");
		operacionSic.setImporte(BigDecimal.ZERO);

		emisionVO = new EmisionVO();
		saldoActual = new Long(0);
		saldoDisponible = new Long(0);
		listaCustodios = new ArrayList<SelectItem>();
		super.limpiarCampos();

	}

	/**
	 * Metodo que obiene un custodio a partir de su id.
	 * 
	 * 
	 * 
	 */
	public String obtenerDepositantes() {
		try {
			if (!(operacionSic.getCatBic().getIdCatbic() == null)) {
				if (operacionSic.getCatBic().getIdCatbic() > 0) {
					CatBic catBic = (CatBic) operacionSicDao.getByPk(
							CatBic.class, operacionSic.getCatBic()
									.getIdCatbic());

					if (catBic != null) {
						if (catBic.getCuentaNombrada() != null) {
							operacionSic.setCatBic(catBic);
							SicDetalle[] depositantes = divisionInternacionalService
									.obtieneDepositantes(catBic);
							if (depositantes != null) {
								listaDepositantes = new ArrayList<SelectItem>();
								if (depositantes.length > 0) {
									for (SicDetalle detalle : depositantes) {
										listaDepositantes.add(new SelectItem(
												detalle.getIdSicDetalle(),
												detalle.getDepLiq()));
									}
									/*
									 * Modificacion 17/04/2012 se agrega calculo
									 * de saldo para que tome en automatico
									 */
									realizarCalculos();
								}
							} else {
								addErrorMessage("No hay Depositantes para el custodio"
										+ operacionSic.getCatBic()
												.getDetalleCustodio());
							}

						}

					}

				}

			}
		} catch (BusinessException e) {
			log.error("Ocurrio un error:",e);
			agregarMensaje(e);
			super.limpiarCampos();
		} catch (Exception e) {
			log.error("Ocurrio un error:",e);
			agregarMensaje(e);
			super.limpiarCampos();
		} catch (Throwable e) {
			log.error("Ocurrio un error:",e);
			agregarMensaje(e);
			super.limpiarCampos();
		}
		return null;
	}

	/**
	 * Metodo que obiene un custodio a partir de su id.
	 * 
	 * @param event
	 *            el evento que dispar&oacute; este ActionListener
	 */
	public void obtenerDepositantes(ActionEvent event) {
		try {
			if (operacionSic.getCatBic().getIdCatbic() > 0) {
				CatBic catBic = (CatBic) operacionSicDao.getByPk(CatBic.class,
						operacionSic.getCatBic().getIdCatbic());

				if (catBic != null) {
					if (catBic.getCuentaNombrada() != null) {
						operacionSic.setCatBic(catBic);
						SicDetalle[] depositantes = divisionInternacionalService
								.obtieneDepositantes(catBic);
						if (depositantes != null) {
							listaDepositantes = new ArrayList<SelectItem>();
							if (depositantes.length > 0) {
								for (SicDetalle detalle : depositantes) {
									listaDepositantes.add(new SelectItem(
											detalle.getIdSicDetalle(), detalle
													.getDepLiq()));
								}
							}
						} else {
							addErrorMessage("No hay Depositantes para el custodio"
									+ operacionSic.getCatBic()
											.getDetalleCustodio());
						}

					}

				}

			}

		} catch (BusinessException e) {
			log.error("Ocurrio un error:",e);
			agregarMensaje(e);
			super.limpiarCampos();
		} catch (Exception e) {
			log.error("Ocurrio un error:",e);
			agregarMensaje(e);
			super.limpiarCampos();
		} catch (Throwable e) {
			log.error("Ocurrio un error:",e);
			agregarMensaje(e);
			super.limpiarCampos();
		}

	}

	/**
	 * ActionListener la opcion de tipo de operacion.
	 * 
	 * @param event
	 *            el evento que dispar? este ActionListener
	 */
	public void cambiaTipoOperacion(ActionEvent e) {

		if (operacionSic != null && operacionSic.getTipoOperacion() != null) {
			if (Constantes.TRASPASO_LIBRE
					.equals(operacionSic.getTipoTraspaso())) {
				contrapago = false;
			} else {
				contrapago = true;
			}

		}
	}

	/**
	 * Metodo que obiene los custodios a partir de la emision.
	 * 
	 * 
	 * 
	 */

	public String getObtenerCustodios() {
		try {
			if (emisionVO != null
					&& ((StringUtils.isNotEmpty(emisionVO.getTv())
							&& StringUtils.isNotEmpty(emisionVO.getEmisora()) && StringUtils
								.isNotEmpty(emisionVO.getSerie())) || StringUtils
							.isNotEmpty(emisionVO.getIsin()))) {
				CatBic[] custodios;
				EmisionVO criterio = new EmisionVO();
				criterio.setTv(emisionVO.getTv());
				criterio.setEmisora(emisionVO.getEmisora());
				criterio.setSerie(emisionVO.getSerie());
				criterio.setIsin(emisionVO.getIsin());
				criterio.setCupon("0000");
				// emisionVO.setCupon(StringUtils.isNotEmpty(emisionVO.getCupon())
				// ? emisionVO.getCupon() : "0000");

				custodios = this.divisionInternacionalService.obtieneCustodioEnBaseAEmision(criterio);

				if (custodios.length > 0) {
					listaCustodios = new ArrayList<SelectItem>();
					if (custodios.length > 1) {
						listaCustodios.add(new SelectItem(-1,
								"Selecciones Custodio"));
					}
					for (CatBic custodio : custodios) {

						listaCustodios.add(new SelectItem(custodio
								.getIdCatbic().toString(), custodio
								.getDetalleCustodio()));
						/*
						 * Modificacion Se asigna valor catBic para llenar los
						 * datos en pantalla en automatico, ya que no existen
						 * listas con mas de 1 valor y se invoca el
						 * mentodoObtenerDepositante
						 */
						operacionSic.setCatBic(custodio);
						/* Se agrega llamado a metodo que completa los campos */
						obtenerDepositantes();
					}
				} else {
					// limpiar la lista y datos del custodio
					listaCustodios = new ArrayList<SelectItem>();
					operacionSic.getCatBic().getCuentaNombrada()
							.setNombreCuenta("");
					operacionSic.getCatBic().getCuentaNombrada()
							.getInstitucion().getTipoInstitucion()
							.setClaveTipoInstitucion("");
					operacionSic.getCatBic().getCuentaNombrada()
							.getInstitucion().setFolioInstitucion("");
					operacionSic.getCatBic().getCuentaNombrada().setCuenta("");
					operacionSic.getCatBic().setPais("");
					operacionSic.getCatBic().setMoneda("");
					operacionSic.getCatBic().getCuentaNombrada()
							.getInstitucion().setNombreCorto("");

					// limpiar los datos de los depositantes
					addErrorMessage("No se encontraron custodios con los datos proporcionados");
				}
			}
		} catch (BusinessException exc) {
			log.error("Ocurrio un error:",exc);
			agregarMensaje(exc);
			super.limpiarCampos();
		} catch (Exception exc) {
			log.error("Ocurrio un error:",exc);
			agregarMensaje(exc);
			super.limpiarCampos();
		} catch (Throwable exc) {
			log.error("Ocurrio un error:",exc);
			agregarMensaje(exc);
			super.limpiarCampos();
		}

		return null;
	}

	/**
	 * Metodo para validar al momento de guardar la operacion que cuente con
	 * Custodios
	 */
	private void validaCustodio() throws BusinessException {
		log.debug("CapturaOperacionesDIController :: validaCustodio");
		if (emisionVO != null
				&& ((StringUtils.isNotEmpty(emisionVO.getTv())
						&& StringUtils.isNotEmpty(emisionVO.getEmisora()) && StringUtils
							.isNotEmpty(emisionVO.getSerie())) || StringUtils
						.isNotEmpty(emisionVO.getIsin()))) {
			CatBic[] custodios;
			EmisionVO criterio = new EmisionVO();
			criterio.setTv(emisionVO.getTv());
			criterio.setEmisora(emisionVO.getEmisora());
			criterio.setSerie(emisionVO.getSerie());
			criterio.setIsin(emisionVO.getIsin());
			criterio.setCupon("0000");
			custodios = divisionInternacionalService.obtieneCustodios(criterio);

			if (custodios.length == 0) {

				listaCustodios = new ArrayList<SelectItem>();
				operacionSic.getCatBic().getCuentaNombrada()
						.setNombreCuenta("");
				operacionSic.getCatBic().getCuentaNombrada().getInstitucion()
						.getTipoInstitucion().setClaveTipoInstitucion("");
				operacionSic.getCatBic().getCuentaNombrada().getInstitucion()
						.setFolioInstitucion("");
				operacionSic.getCatBic().getCuentaNombrada().setCuenta("");
				operacionSic.getCatBic().setPais("");
				operacionSic.getCatBic().setMoneda("");
				operacionSic.getCatBic().getCuentaNombrada().getInstitucion()
						.setNombreCorto("");

				// limpiar los datos de los depositantes
				throw new BusinessException(
						"No se encontraron custodios con los datos proporcionados");
			}
		}
	}

	/**
	 * ActionListener para los datos de la emision a partir de la cuenta y el
	 * isin.
	 * 
	 * @param event
	 *            el evento que dispar? este ActionListener
	 */
	public String getBuscarEmisionPorIsinCuenta() {
		if ((StringUtils.isNotEmpty(cuentaTraspasante) && StringUtils
				.isNotEmpty(emisionVO.getIsin()))
				|| (StringUtils.isNotEmpty(cuentaTraspasante)
						&& StringUtils.isNotEmpty(emisionVO.getTv())
						&& StringUtils.isNotEmpty(emisionVO.getEmisora()) && StringUtils
							.isNotEmpty(emisionVO.getSerie()))) {
			AgenteVO agente = new AgenteVO();
			Institucion institucion = null;
			if (idFolioTraspasante != null) {
				institucion = consultaCatService
						.findInstitucionByClaveFolio(idFolioTraspasante);
			}
			// validar la institucion por falta del autocompleter
			if (institucion != null) {
				agente.setId(institucion.getTipoInstitucion()
						.getClaveTipoInstitucion());
				agente.setFolio(institucion.getFolioInstitucion());
				agente.setCuenta(StringUtils.isEmpty(cuentaTraspasante) ? null
						: cuentaTraspasante);
				PaginaVO pgTmp = consultaCatService
						.buscarListaPosicionesNombradasDeParticipante(agente,
								emisionVO, paginaVO);
				if (pgTmp != null) {
					paginaVO = pgTmp;
					if (paginaVO.getRegistros() != null
							&& !paginaVO.getRegistros().isEmpty()) {
						// && paginaVO.getRegistros().size() == 1) {
						PosicionNombrada posicion = new PosicionNombrada();
						posicion = (PosicionNombrada) paginaVO.getRegistros()
								.get(0);
						cuentaTraspasante = posicion.getCuentaNombrada()
								.getCuenta() != null ? posicion
								.getCuentaNombrada().getCuenta() : "";
						tenencia = posicion.getCuentaNombrada().getTipoCuenta()
								.getClaveTipoCuenta() != null ? posicion
								.getCuentaNombrada().getTipoCuenta()
								.getClaveTipoCuenta() : "";
						emisionVO
								.setTv(posicion.getCupon().getEmision()
										.getInstrumento().getClaveTipoValor() != null ? posicion
										.getCupon().getEmision()
										.getInstrumento().getClaveTipoValor()
										: "");
						emisionVO
								.setEmisora(posicion.getCupon().getEmision()
										.getEmisora().getClavePizarra() != null ? posicion
										.getCupon().getEmision().getEmisora()
										.getClavePizarra()
										: "");
						emisionVO.setSerie(posicion.getCupon().getEmision()
								.getSerie() != null ? posicion.getCupon()
								.getEmision().getSerie() : "");
						emisionVO
								.setCupon(posicion.getCupon().getClaveCupon() != null ? posicion
										.getCupon().getClaveCupon() : "");
						emisionVO.setIsin(posicion.getCupon().getEmision()
								.getIsin() != null ? posicion.getCupon()
								.getEmision().getIsin() : "");
						emisionVO.setSaldoDisponible(new BigDecimal(posicion
								.getPosicionDisponible()));
						operacionSic
								.getBoveda()
								.setIdBoveda(
										posicion.getBoveda().getIdBoveda() != null ? posicion
												.getBoveda().getIdBoveda()
												: new Long(-1));
						operacionSic.getBoveda().setDescripcion(
								StringUtils.isNotEmpty(posicion.getBoveda()
										.getDescripcion()) ? posicion
										.getBoveda().getDescripcion() : "");

					}

				} else {
					if ((StringUtils.isNotEmpty(emisionVO.getTv())
							&& StringUtils.isNotEmpty(emisionVO.getEmisora())
							&& StringUtils.isNotEmpty(emisionVO.getSerie()) || StringUtils
								.isNotEmpty(emisionVO.getIsin()))) {
						EmisionVO criterio = new EmisionVO();
						criterio.setTv(emisionVO.getTv());
						criterio.setEmisora(emisionVO.getEmisora());
						criterio.setSerie(emisionVO.getSerie());
						criterio.setIsin(emisionVO.getIsin());
						criterio.setCupon("0000");
						List<Long> listaIds = new ArrayList<Long>();
						listaIds.add(new Long(1));
						listaIds.add(new Long(2));
						listaIds.add(new Long(3));
						criterio.setListaIdEstatusEmision(listaIds);
						emisionVO.setCupon(null);
						emisionVO.setSaldoDisponible(new BigDecimal(0));

						Emision emisionModel = emisionDao.findEmisionLiberada(criterio);

						if (emisionModel != null) {
							emisionVO.setTv(emisionModel.getInstrumento()
									.getClaveTipoValor() != null ? emisionModel
									.getInstrumento().getClaveTipoValor() : "");
							emisionVO.setEmisora(emisionModel.getEmisora()
									.getClavePizarra() != null ? emisionModel
									.getEmisora().getClavePizarra() : "");
							emisionVO
									.setSerie(emisionModel.getSerie() != null ? emisionModel
											.getSerie() : "");
							emisionVO
									.setIsin(emisionModel.getIsin() != null ? emisionModel
											.getIsin() : "");

							// obtener el cupon
							Cupon cuponTmp = cuponDao
									.findCuponByIdEmision(emisionModel
											.getIdEmision());
							emisionVO.setCupon(cuponTmp != null ? cuponTmp
									.getClaveCupon() : null);
							operacionSic.getBoveda().setIdBoveda(
									emisionModel.getIdBoveda());
							operacionSic.getBoveda().setDescripcion(" ");
						}
					}
				}
			}

		}
		return null;

	}
	/**
	 * M?todo que limpia la pantalla de Captura Operaciones Divisi?n
	 * Internacional
	 * 
	 * @param event
	 *            el evento que dispar? este ActionListener
	 */
	public void limpiar(ActionEvent event) {
		inicializar(event);
		idFolioTraspasante = new String();
		getInit();
		nombreCuenta = "";
		super.limpiarCampos();
	}

	/*
	 * Modificacion 12/04/2012 Se inicializa obj operacionSic, para completar la
	 * funcionalidad en pantalla de limpiar posicion
	 */
	public void limpiarDepositantesCustodios(ActionEvent e) {

		operacionSic.setCatBic(new CatBic());
		operacionSic.getCatBic().setCuentaNombrada(new CuentaNombrada());
		operacionSic.getCatBic().getCuentaNombrada()
				.setInstitucion(new Institucion());
		operacionSic.getCatBic().getCuentaNombrada().getInstitucion()
				.setTipoInstitucion(new TipoInstitucion());
		operacionSic.setCantidadTitulos(new BigInteger("0"));

		saldoActual = new Long("0");
		// settear al catogo de divisas la llave USD (US DOLLAR)
		operacionSic.setDivisa("USD");
		// limpiar la lista de depositantes
		/*
		 * Modificacion 25/04/2012 se comenta limpiar depositantes para dejar
		 * precargado el listado de depositantes al hacer clic en la opcion
		 * limpiar posicion
		 */
		// listaDepositantes = new ArrayList<SelectItem>();
		// limpiar la lista de custodios
		listaCustodios = new ArrayList<SelectItem>();

	}

	/**
	 * Realiza una revision de si la emision se encuentra participando en un cambio de boveda.
	 * @throws BusinessException
	 */
	private void revisaEmisionEnCambioBoveda() throws BusinessException {
        //if (this.usuarioContieneRolCambioBoveda()) {
            if (this.emisionActualEnCambioBoveda()) {
                this.listaDepositantes = new ArrayList<SelectItem>();
                final String MENSAJE = "Error: La Emisi\u00F3n no se puede operar en este momento debido a que se encuentra en Cambio de B\u00F3veda";
                throw new BusinessException(MENSAJE);
            }
        //}
	}

    /**
     * Checa si la emision actual esta participando en un cambio de boveda.
     * @return true si la emision participa en un cambio de boveda, false en caso contrario.
     */
    private boolean emisionActualEnCambioBoveda() throws BusinessException {
        boolean emisionEnCambioBoveda = false;
        if (emisionVO != null && 
            ((StringUtils.isNotEmpty(emisionVO.getTv()) && StringUtils.isNotEmpty(emisionVO.getEmisora()) && 
              StringUtils.isNotEmpty(emisionVO.getSerie())) && StringUtils.isNotEmpty(emisionVO.getIsin()))) {
            emisionVO.setTv(StringUtils.upperCase(emisionVO.getTv()));
            emisionVO.setEmisora(StringUtils.upperCase(emisionVO.getEmisora()));
            emisionVO.setSerie(StringUtils.upperCase(emisionVO.getSerie()));
            emisionVO.setCupon(StringUtils.upperCase(emisionVO.getCupon()));
            emisionVO.setIsin(StringUtils.upperCase(emisionVO.getIsin()));

            emisionEnCambioBoveda = this.emisionesConsultasService.consultarEmisionActualEnCambioBoveda(emisionVO);
        }
        else {
            if (emisionVO != null && StringUtils.isNotEmpty(emisionVO.getIsin())) {
                emisionEnCambioBoveda = this.emisionesConsultasService.consultarEmisionActualEnCambioBovedaByIsin(emisionVO.getIsin());
            }
        }

        return emisionEnCambioBoveda;
    }

    /**
     * Verifica si en los roles del usuario se encuentra el de Cambio de Boveda.
     * @return true si contiene el rol, false en caso contrario.
     */
    private boolean usuarioContieneRolCambioBoveda() {
        final String ROL_CAMBIO_BOVEDA = "INT_CAMBIO_BOVEDA";
        return this.isUserInRoll(ROL_CAMBIO_BOVEDA);
    }

	/**
	 * Una vez que el usuario confirma los datos se procede a validar la
	 * informaci?n y a grabar la operaci?n o firmarla seg?n corresponda
	 * 
	 * @param event
	 */
	public void confirmarTraspaso(ActionEvent event) {
		log.debug("CapturaOperacionesDIController :: confirmarTraspaso");
	    try {
	        /* se agrega la validacion y obtencion de los custodios */
	        validaCustodio();
	        emisionVO.setTv(StringUtils.upperCase(emisionVO.getTv()));
	        emisionVO.setEmisora(StringUtils.upperCase(emisionVO.getEmisora()));
	        emisionVO.setSerie(StringUtils.upperCase(emisionVO.getSerie()));
	        emisionVO.setCupon(StringUtils.upperCase(emisionVO.getCupon()));
	        toUpper();
	        Boolean isEntrega = Constantes.TIPO_MOVTO_E.equals(operacionSic.getTipoOperacion());
	        
	        //Entrega
	        if (isEntrega) {
	            if (emisionVO.getSaldoDisponible() != null) {
	                saldoActual = 
	                        (emisionVO.getSaldoDisponible().longValue()) - 
	                        (operacionSic.getCantidadTitulos() != null ? operacionSic.getCantidadTitulos().longValue() : 0);
	            }
	        }
	        //Recepcion
	        else {
	            if (emisionVO.getSaldoDisponible() != null && operacionSic.getCantidadTitulos() != null) {
	                saldoActual = 
	                        emisionVO.getSaldoDisponible().longValue() + operacionSic.getCantidadTitulos().longValue();
	            }
	        }

	        //Si el usuario firma operaci��n
	        if (conFirma()) {
	            //Valida la vigencia del certificado - 18-Agosto-2014 Pablo Balderas
	            validarVigenciaCertificado();

	            recuperarCamposFirma();
	            if (StringUtils.isEmpty(isoFirmado)) {
	                validar(); //se llama a este metodo dentro de la condicion para que no se ejecute 2 veces
	                //18/09/12 Adicion de uso de TPL o DVP para generar la firma
	                if (isContrapago()) {
	                    isoSinFirmar = isoHelper.creaIsoSicDvp(dvpvo, !isEntrega);
	                    hashIso = cdb.cipherHash(isoSinFirmar);
	                }
	                else {
	                    isoSinFirmar = isoHelper.creaIsoSicTlp(tlpvo, !isEntrega);
	                    hashIso = cdb.cipherHash(isoSinFirmar);
	                }
	            }
	            else {
	                if (!cdb.validation(hashIso, isoSinFirmar)) {
	                    throw new InfrastructureException(Constantes.ERROR_ISO_DIFERENTE);
	                }
	                if (conFirma()) {
	                    isoSinFirmar = isoSinFirmar.replace("\r\n", "\n");						
	                    isoFirmado = (new StringBuilder()).append(isoSinFirmar)
	                            .append(numeroSerie).append("\n")
	                            .append("{SHA1withRSA}").append(isoFirmado)
	                            .toString();
	                }
	                grabarOperacion();
	                //Se invoca a una nueva funcion para que se realize el limpiado parcial de la pantalla
	                inicializarParcial(event);
	            }
	        }
	        else {
	            validar();
	            grabarOperacion();
	            //Se invoca a una nueva funcion para que se realize el limpiado parcial de la pantalla
	            inicializarParcial(event);
	        }
	    }
	    catch (BusinessException e) {
	        log.error("Ocurrio un error:",e);
	        agregarMensaje(e);
	        super.limpiarCampos();
	    }
	    catch (Exception e) {
	        log.error("Ocurrio un error:",e);
	        agregarMensaje(e);
	        super.limpiarCampos();
	    }
	    catch (Throwable e) {
	        log.error("Ocurrio un error:",e);
	        agregarMensaje(e);
	        super.limpiarCampos();
	    }
	}

	/**
	 * 
	 */
	private void toUpper() {		
		operacionSic.setCuentaContraparte(StringUtils.upperCase(operacionSic.getCuentaContraparte()));					
		operacionSic.setDescContraparte(StringUtils.upperCase(operacionSic.getDescContraparte()));
		operacionSic.setInstruccEspeciales(StringUtils.upperCase(operacionSic.getInstruccEspeciales()));
		operacionSic.setNomCtaBenef(StringUtils.upperCase(operacionSic.getNomCtaBenef()));
		operacionSic.setNumCtaBenef(StringUtils.upperCase(operacionSic.getNumCtaBenef()));
	}

	/**
	 *
	 */
	private void grabarOperacion() {
		log.debug("CapturaOperacionesDIController :: grabarOperacion");
		/*
		 * Modificacion 30/03/2012 Se agrega condicion para que las operaciones
		 * de entrega con fecha posterior ala fecha actual se guarden en
		 * OperacionesSic con estatus retenida, se comenta condicion para
		 * pruebas primer entrega dateUtilService.getCurrentDate() Modificacion
		 * 21/06/2012 Se quita condicion, para que todas las entregas se guarden
		 * en operacionesSic
		 */	

		operacionSic.setFechaLiquidacion(new Date(operacionSic.getFechaLiquidacion().getTime()));
		operacionSic.setFechaOperacion(new Date(operacionSic.getFechaOperacion().getTime()));
	
		operacionSic.setDatosAdicionales(creaMapaDatosAdicionales());
		
		if (StringUtils.isNotBlank(isoFirmado)) {
			operacionSic.setIsoFirmado(isoFirmado);
		}		

		log.info("\n\n####### Folio control a insertar = [" + folioAsignado + "]");

		this.divisionInternacionalService.insertaOperacionSIC(operacionSic);
		agregarInfoMensaje("La operaci\u00f3n se llev\u00f3 a cabo exitosamente. Folio de la operaci\u00f3n : " + folioAsignado);
	}

	/**
	 * Realiza validaciones de negocio y la firma del ISO del TLP respectivo
	 */
	private void validar() {
		log.debug("CapturaOperacionesDIController :: validar");
		if(recepcion && StringUtils.isNotEmpty(liquidacionParcial) && liquidacionParcial.equals("SI") 
			&& operacionSic != null && operacionSic.getCatBic() != null && operacionSic.getCatBic().getCuentaNombrada() != null){
			Boolean isLiquidacionParcial = settlementDisciplineRegimeDao.getSettlementDisciplineRegimeByIdCuentaBoveda(operacionSic.getCatBic().getCuentaNombrada().getIdCuentaNombrada(), Constantes.PARTIAL_SETTLEMENT);
			if(!isLiquidacionParcial){
				String detalleCustodio = (StringUtils.isNotEmpty(operacionSic.getCatBic().getDetalleCustodio())) ? operacionSic.getCatBic().getDetalleCustodio() : "";
				throw new BusinessException("Error: El Custodio: " + detalleCustodio + " no permite Liquidaciones Parciales.");	
			}
		}
		
		folioAsignado = null;
		
		
		if(StringUtils.isBlank(emisionVO.getTv()) || StringUtils.isBlank(emisionVO.getEmisora()) 
				|| StringUtils.isBlank(emisionVO.getCupon())||StringUtils.isBlank(emisionVO.getSerie())){
			throw new BusinessException("Error:Faltan parametros para obtener la emision");			
		}
		
        /* Valida la cantidad de titulos */
        // Para TV: D4, D5, D6 la cantidad maxima de titulos es de 500,000
		if(StringUtils.isNotBlank(emisionVO.getTv()) && 
				(emisionVO.getTv().equals(Constantes.TIPO_VALOR_D4) 
						|| emisionVO.getTv().equals(Constantes.TIPO_VALOR_D5) 
						|| emisionVO.getTv().equals(Constantes.TIPO_VALOR_D6))){
			if(operacionSic.getCantidadTitulos().doubleValue() > Constantes.VALOR_MAXIMO_TITULOS){
				throw new BusinessException("Error: La cantidad maxima de titulos es de: " + Constantes.VALOR_MAXIMO_TITULOS.intValue() + " para TipoValor D4, D5, D6");
			}			
		}
		
		
		/** Valida que campos Nombre Cuenta y Numero Cuenta en Beneficiario Final no contengan punto (.)
		 * operacionSic
		 */
/*		Pattern p = Pattern.compile(Constantes.REGEXP);
		Matcher m = p.matcher(operacionSic.getNomCtaBenef());
		if (operacionSic.getNomCtaBenef() != null && m.matches()){
			throw new BusinessException("Error: Nombre Cuenta no debe contener caracteres: .");
		}
	    
		m = p.matcher(operacionSic.getNumCtaBenef());
		if (operacionSic.getNumCtaBenef() != null && m.matches()){
			throw new BusinessException("Error: Numero Cuenta no debe contener caracteres: .");
		}
*/
		operacionSic.setEmision(emisionDao.findEmisionLiberada(emisionVO));
		operacionSic = divisionInternacionalService.businessRulesCapturaOperacion(operacionSic);
		folioAsignado = operacionSic.getFolioControl().toString();
		BigInteger referenciaMensaje = utilService
				.getFolio(SEQ_REFERENCIA_MENSAJE);
		/**seteo la referencia del mensaje en la operacionSic*/
		operacionSic.setReferenciaMensaje(referenciaMensaje.toString().trim());
		
		// Liquidacion Parcial
		String auxLiqParcial = "LP:NO";
		if(recepcion && StringUtils.isNotEmpty(liquidacionParcial) && liquidacionParcial.equals("SI")){
			auxLiqParcial = "LP:SI";
		}
		String auxInstruccionesEspeciales = auxLiqParcial;
		
		/*
		 * 14/09/12 adicion de validacion para la utilizacion de TPLVO o DVPVO
		 */
		if (isContrapago()) {
			dvpvo = new TraspasoContraPagoVO();
			dvpvo.setReferenciaOperacion(folioAsignado);
			dvpvo.setReferenciaMensaje(referenciaMensaje.toString().trim());
			dvpvo.setCantidadTitulos(operacionSic.getCantidadTitulos()
					.longValue());
			dvpvo.setCupon(emisionVO.getCupon());
			dvpvo.setEmisora(emisionVO.getEmisora());
			dvpvo.setIdFolioCtaTraspasante(idFolioTraspasante.trim()
					+ cuentaTraspasante.trim());
			dvpvo.setIdFolioCtaReceptora(idFolioTraspasante.trim()
					+ cuentaTraspasante.trim());
			
			dvpvo.setFechaRegistro(operacionSic.getFechaNotificacion());
			dvpvo.setFechaConcertacion(operacionSic.getFechaOperacion());
			dvpvo.setFechaLiquidacion(dateUtilService
					.getFechaHoraCero(operacionSic.getFechaLiquidacion()));
			dvpvo.setISIN(StringUtils.stripToNull(emisionVO.getIsin()));
			dvpvo.setTipoValor(emisionVO.getTv());
			dvpvo.setSerie(emisionVO.getSerie());
			dvpvo.setTipoInstruccion(Constantes.TIPO_OPER_V);
			
			dvpvo.setCustodio(operacionSic.getCatBic().getBicProd());
			dvpvo.setBoveda(operacionSic.getCatBic().getBicProd());
			dvpvo.setBovedaEfectivo(operacionSic.getCatBic().getBicProd());
			
			dvpvo.setCuentaContraparte(operacionSic.getCuentaContraparte());
			dvpvo.setDescripcionCuentaContraparte(operacionSic.getDescContraparte());
			
			SicDetalle detalle = (SicDetalle) operacionSicDao.getByPk(SicDetalle.class, operacionSic.getSicDetalle().getIdSicDetalle());			
			dvpvo.setLugarDeLiquidacion(detalle.getBicDepLiq());			
			// TOMAR SOLO 200 caracteres de instrucciones especiales
			// para el depositante
			if (StringUtils.isNotBlank(operacionSic.getInstruccEspeciales())) {
				if (operacionSic.getInstruccEspeciales().length() > 200) {
					operacionSic.setInstruccEspeciales(StringUtils.substring(
							operacionSic.getInstruccEspeciales(), 0, 201));
				}
				auxInstruccionesEspeciales = auxInstruccionesEspeciales + operacionSic.getInstruccEspeciales();
			}
			dvpvo.setInstruccionesEspeciales(auxInstruccionesEspeciales);
			dvpvo.setCuentaBeneficiarioFinal(operacionSic.getNumCtaBenef());
			dvpvo.setDescripcionBeneficiarioFinal(operacionSic.getNomCtaBenef());
			
			dvpvo.setMonto(operacionSic.getImporte());
			dvpvo.setDivisa(operacionSic.getDivisa());			
		} 
		else {
			tlpvo = new TraspasoLibrePagoVO();
			tlpvo.setReferenciaOperacion(folioAsignado);
			tlpvo.setReferenciaMensaje(referenciaMensaje.toString().trim());
			tlpvo.setCantidadTitulos(operacionSic.getCantidadTitulos()
					.longValue());
			tlpvo.setCupon(emisionVO.getCupon());
			tlpvo.setEmisora(emisionVO.getEmisora());
			// tlpvo.setBoveda(operacionSic.getBoveda().getIdBoveda()
			// .toString());
			tlpvo.setIdFolioCtaTraspasante(idFolioTraspasante.trim()
					+ cuentaTraspasante.trim());
			tlpvo.setIdFolioCtaReceptora(idFolioTraspasante.trim()
					+ cuentaTraspasante.trim());
			tlpvo.setFechaRegistro(operacionSic.getFechaNotificacion());
			tlpvo.setFechaConcertacion(operacionSic.getFechaOperacion());
			tlpvo.setFechaLiquidacion(dateUtilService.getFechaHoraCero(operacionSic.getFechaLiquidacion()));			
			tlpvo.setISIN(StringUtils.stripToNull(emisionVO.getIsin()));
			tlpvo.setTipoValor(emisionVO.getTv());
			tlpvo.setSerie(emisionVO.getSerie());
			tlpvo.setTipoInstruccion(Constantes.TIPO_OPER_T);
			// TOMAR SOLO 200 caracteres de instrucciones especiales
			// para el depositante
			if (StringUtils.isNotBlank(operacionSic.getInstruccEspeciales())) {
				if (operacionSic.getInstruccEspeciales().length() > 200) {
					operacionSic.setInstruccEspeciales(StringUtils.substring(
							operacionSic.getInstruccEspeciales(), 0, 201));
				}
				auxInstruccionesEspeciales = auxInstruccionesEspeciales + operacionSic.getInstruccEspeciales();
			}
			tlpvo.setInstruccionesEspeciales(auxInstruccionesEspeciales);
			
			tlpvo.setDescripcionBeneficiarioFinal(operacionSic.getNomCtaBenef());
			tlpvo.setCuentaContraparte(operacionSic.getCuentaContraparte());
			tlpvo.setDescripcionCuentaContraparte(operacionSic
					.getDescContraparte());
			
			SicDetalle detalle = (SicDetalle) operacionSicDao.getByPk(SicDetalle.class, operacionSic.getSicDetalle().getIdSicDetalle());			
			tlpvo.setLugarDeLiquidacion(detalle.getBicDepLiq());		
			
			tlpvo.setCustodio(operacionSic.getCatBic().getBicProd());
			tlpvo.setBoveda(operacionSic.getCatBic().getBicProd());

			tlpvo.setCuentaBeneficiarioFinal(operacionSic.getNumCtaBenef());
			
		}	
	}


	@SuppressWarnings("unchecked")
	private HashMap creaMapaDatosAdicionales() {
		log.debug("CapturaOperacionesDIController :: creaMapaDatosAdicionales");
		HashMap datosAdicionales = new HashMap();
		
		if (isContrapago()){
			datosAdicionales.put("lugarDeLiquidacion",      dvpvo.getLugarDeLiquidacion());
			datosAdicionales.put("cuentaBeneficiarioFinal", dvpvo.getCuentaBeneficiarioFinal());			
			datosAdicionales.put("descripcionCuentaContraparte", dvpvo.getDescripcionCuentaContraparte());
			datosAdicionales.put("descripcionBeneficiarioFinal", dvpvo.getDescripcionBeneficiarioFinal());
			String liqParcial = "LP:NO";
			if(recepcion && StringUtils.isNotEmpty(liquidacionParcial) && liquidacionParcial.equals("SI")){
				liqParcial = "LP:SI";
			}
			if(StringUtils.isNotBlank(dvpvo.getInstruccionesEspeciales())){
				String auxInstruccionesEspeciales = dvpvo.getInstruccionesEspeciales();
				if(dvpvo.getInstruccionesEspeciales().length() >= 5){
					String auxCadena = dvpvo.getInstruccionesEspeciales().substring(0,5);
					if(auxCadena.contains("LP:SI") || auxCadena.contains("LP:NO")){
						auxInstruccionesEspeciales = dvpvo.getInstruccionesEspeciales();
					} else {
						auxInstruccionesEspeciales = auxInstruccionesEspeciales + dvpvo.getInstruccionesEspeciales();		
					}
				} else {
					auxInstruccionesEspeciales = liqParcial + dvpvo.getInstruccionesEspeciales();
				}
				
				datosAdicionales.put("instruccionesEspeciales", auxInstruccionesEspeciales);
			} else {
				datosAdicionales.put("instruccionesEspeciales", liqParcial);
			}

			datosAdicionales.put("custodio", dvpvo.getCustodio());
			if(StringUtils.isNotBlank(dvpvo.getISIN())){
				datosAdicionales.put("ISIN", dvpvo.getISIN());
			}			
		}else{		
			datosAdicionales.put("lugarDeLiquidacion",      tlpvo.getLugarDeLiquidacion());
			datosAdicionales.put("cuentaBeneficiarioFinal", tlpvo.getCuentaBeneficiarioFinal());			
			datosAdicionales.put("descripcionCuentaContraparte", tlpvo.getDescripcionCuentaContraparte());			
			datosAdicionales.put("descripcionBeneficiarioFinal", tlpvo.getDescripcionBeneficiarioFinal());
			String liqParcial = "LP:NO";
			if(recepcion && StringUtils.isNotEmpty(liquidacionParcial) && liquidacionParcial.equals("SI")){
				liqParcial = "LP:SI";
			}
			if(StringUtils.isNotBlank(tlpvo.getInstruccionesEspeciales())){
				datosAdicionales.put("instruccionesEspeciales", liqParcial + tlpvo.getInstruccionesEspeciales());
				String auxInstruccionesEspeciales = tlpvo.getInstruccionesEspeciales();
				if(tlpvo.getInstruccionesEspeciales().length() >= 5){
					String auxCadena = tlpvo.getInstruccionesEspeciales().substring(0,5);
					if(auxCadena.contains("LP:SI") || auxCadena.contains("LP:NO")){
						auxInstruccionesEspeciales = tlpvo.getInstruccionesEspeciales();
					} else {
						auxInstruccionesEspeciales = auxInstruccionesEspeciales + tlpvo.getInstruccionesEspeciales();		
					}
				} else {
					auxInstruccionesEspeciales = liqParcial + tlpvo.getInstruccionesEspeciales();
				}
				
				datosAdicionales.put("instruccionesEspeciales", auxInstruccionesEspeciales);
				
			} else {
				datosAdicionales.put("instruccionesEspeciales", liqParcial);
			}
		
			datosAdicionales.put("custodio", tlpvo.getCustodio());			
			if(StringUtils.isNotBlank(tlpvo.getISIN())){
				datosAdicionales.put("ISIN", tlpvo.getISIN());
			}			
			
		}			
		
		datosAdicionales.put(Constantes.CUENTA_CONTRAPARTE_DA,
				operacionSic.getCuentaContraparte());
		datosAdicionales.put(Constantes.DESC_CTA_CONTRAPARTE_DA,
				operacionSic.getDescContraparte());
		datosAdicionales.put(Constantes.FECHA_OP_DA,
				operacionSic.getFechaOperacion());
		datosAdicionales.put(Constantes.FECHA_LIQ_DA,
				operacionSic.getFechaLiquidacion());
		datosAdicionales.put(Constantes.FECHA_NOT_DA,
				operacionSic.getFechaNotificacion());
		datosAdicionales.put(Constantes.NUM_CUNETA_BENEF_DA,
				operacionSic.getNumCtaBenef());
		datosAdicionales.put(Constantes.NOM_CUENTA_BENEF_DA,
				operacionSic.getNomCtaBenef());
//		datosAdicionales.put(Constantes.CUSTODIO_DA, operacionSic.getCatBic()
//				.getIdCatbic());
		datosAdicionales.put(Constantes.DEPOSITANTE_DA, operacionSic
				.getSicDetalle().getIdSicDetalle());
		datosAdicionales.put(Constantes.INSTRUCCIONES_ESP_DA, operacionSic.getInstruccEspeciales());
		datosAdicionales.put(Constantes.TIPO_MENSAJE_DA,
				operacionSic.getTipoMensaje());
		datosAdicionales.put(Constantes.ESTATUS_DA, operacionSic
				.getEstatusOperacion().getIdEstatusOperacion());
		datosAdicionales.put(Constantes.PRECIO_DA,
				operacionSic.getImporte() != null ? operacionSic.getImporte()
						: new BigDecimal(0));
		datosAdicionales.put(Constantes.DIVISA_DA, operacionSic.getDivisa());

		datosAdicionales.put(SeguridadConstants.USR_CREDENCIAL,
				(String) FacesContext.getCurrentInstance().getExternalContext()
						.getSessionMap().get(SeguridadConstants.TICKET_SESION));

		return datosAdicionales;
	}
	
	
	/**
	 * Metodo para validar campos: Nombre Cuenta y Numero Cuenta de Beneficiario Final
	 */
	public void validaBeneficiarioFinal(ActionEvent event) {

        // No pueden contener caracteres: .
		if(StringUtils.isNotBlank(operacionSic.getNomCtaBenef())){
			Pattern p = Pattern.compile(Constantes.REGEXP);
			Matcher m = p.matcher(operacionSic.getNomCtaBenef());
			if (operacionSic.getNomCtaBenef() != null && m.matches()){
				addErrorMessage("Nombre Cuenta no debe contener caracteres: .");
			}
		}
		
		if(StringUtils.isNotBlank(operacionSic.getNumCtaBenef())){
			Pattern p = Pattern.compile(Constantes.REGEXP);
			Matcher m = p.matcher(operacionSic.getNumCtaBenef());
			if (operacionSic.getNumCtaBenef() != null && m.matches()){
				addErrorMessage("Numero Cuenta no debe contener caracteres: .");
			}
		}
	}

	/**
	 * Metodo que calcula el saldo actual
	 * 
	 * @param event
	 *            el evento que dispar? este ActionListener
	 */

	/*
	 * Modificacion 16/03/2012 Se quita condicion de saldo menor a 0 ya que se
	 * puede capturar una cantidad opera sin haber seleccionado la emision
	 * Modificiacion 03/05/2012 se agrega condicion a >=0 para cuando saldo
	 * disponible es cero muestre el msg del Cantidad Operada mauor a Cero
	 */
	public void realizarCalculos(ActionEvent event) {
		log.debug("CapturaOperacionesDIController :: realizarCalculos(ActionEvent event)");
		if (emisionVO.getSaldoDisponible() != null
				&& operacionSic.getCantidadTitulos() != null) {
			// cuando es ENTREGA
			if (Constantes.TIPO_MOVTO_E.equals(operacionSic.getTipoOperacion())) {
				saldoActual = emisionVO.getSaldoDisponible().longValue()
						- operacionSic.getCantidadTitulos().longValue();
				if (operacionSic.getCantidadTitulos().longValue() > emisionVO
						.getSaldoDisponible().longValue()
						&& emisionVO.getSaldoDisponible().longValue() >= 0l
						&& emisionVO.getCupon().length() > 0) {
					addErrorMessage("La Cantidad Operada es Mayor al Saldo Actual");
				}

			} else {
				// CUANDO ES RECEPCION
				saldoActual = emisionVO.getSaldoDisponible().longValue()
						+ operacionSic.getCantidadTitulos().longValue();
			}
			
	        /* Valida la cantidad de titulos */
	        // Para TV: D4, D5, D6 la cantidad maxima de titulos es de 50,000
			if(StringUtils.isNotBlank(emisionVO.getTv()) && 
				(emisionVO.getTv().equals(Constantes.TIPO_VALOR_D4) 
						|| emisionVO.getTv().equals(Constantes.TIPO_VALOR_D5) 
						|| emisionVO.getTv().equals(Constantes.TIPO_VALOR_D6))){
				if(operacionSic.getCantidadTitulos().doubleValue() > Constantes.VALOR_MAXIMO_TITULOS){
					addErrorMessage("La cantidad maxima de titulos es de: " + Constantes.VALOR_MAXIMO_TITULOS.intValue() + " para TipoValor D4, D5, D6");
				}
			}
		}

		if (Constantes.TIPO_MOVTO_E.equals(operacionSic.getTipoOperacion())) {
			recepcion = false;
			liquidacionParcial = "NO";
		} else {
			recepcion = true;
			liquidacionParcial = "NO";
		}
	}

	public void realizarCalculos() {
		log.debug("CapturaOperacionesDIController :: realizarCalculos()");
		if (emisionVO.getSaldoDisponible() != null
				&& operacionSic.getCantidadTitulos() != null) {
			// cuando es ENTREGA
			if (Constantes.TIPO_MOVTO_E.equals(operacionSic.getTipoOperacion())) {
				saldoActual = emisionVO.getSaldoDisponible().longValue()
						- operacionSic.getCantidadTitulos().longValue();
				if (operacionSic.getCantidadTitulos().longValue() > emisionVO
						.getSaldoDisponible().longValue()
						&& emisionVO.getSaldoDisponible().longValue() >= 0l
						&& emisionVO.getCupon().length() > 0) {
					addErrorMessage("La Cantidad Operada es Mayor al Saldo Actual");
				}
				
			} else {
				// CUANDO ES RECEPCION
				saldoActual = emisionVO.getSaldoDisponible().longValue()
						+ operacionSic.getCantidadTitulos().longValue();
			}
		}

		if (Constantes.TIPO_MOVTO_E.equals(operacionSic.getTipoOperacion())) {
			this.recepcion = false;
			liquidacionParcial = "NO";
		} else {
			this.recepcion = true;
			liquidacionParcial = "NO";
		}
	}

	/**
	 * Metodo que obtiene los datos de la intitucion a partir de su idFolio
	 * 
	 * @param event
	 *            el evento que dispar? este ActionListener
	 */
	public void obtenerDatosParticipante(ActionEvent e) {
		Institucion institucion = null;
		if (idFolioTraspasante != null) {
			institucion = consultaCatService
					.findInstitucionByClaveFolio(idFolioTraspasante);
			if (institucion != null) {
				nombreInstitucion = institucion.getNombreCorto();
			}
		}
	}

	/**
	 * Indica si la operaci?n se realiza con certificado digital o no
	 * 
	 * @return
	 */
	public boolean conFirma() {
		return isUsuarioConFacultadFirmar();
	}

	/**
	 * @return the contrapago
	 */
	public boolean isContrapago() {
		
		return contrapago;
	}

	/**
	 * @param contrapago
	 *            the contrapago to set
	 */
	public void setContrapago(boolean contrapago) {
		this.contrapago = contrapago;
	}

	/**
	 * @param dateUtilService
	 *            the dateUtilService to set
	 */
	public void setDateUtilService(DateUtilService dateUtilService) {
		this.dateUtilService = dateUtilService;
	}

	/**
	 * @return the idFolioTraspasante
	 */
	public String getIdFolioTraspasante() {
		return idFolioTraspasante;
	}

	/**
	 * @param idFolioTraspasante
	 *            the idFolioTraspasante to set
	 */
	public void setIdFolioTraspasante(String idFolioTraspasante) {
		this.idFolioTraspasante = idFolioTraspasante;
	}

	/**
	 * @return the operacionSic
	 */
	public OperacionSic getOperacionSic() {
		return operacionSic;
	}

	/**
	 * @param operacionSic
	 *            the operacionSic to set
	 */
	public void setOperacionSic(OperacionSic operacionSic) {
		this.operacionSic = operacionSic;
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
	 * @return the emisionVO
	 */
	public EmisionVO getEmisionVO() {
		return emisionVO;
	}

	/**
	 * @param emisionVO
	 *            the emisionVO to set
	 */
	public void setEmisionVO(EmisionVO emisionVO) {
		this.emisionVO = emisionVO;
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
	 * @return the consultaCatService
	 */
	public ConsultaCatalogoService getConsultaCatService() {
		return consultaCatService;
	}

	/**
	 * @param consultaCatService
	 *            the consultaCatService to set
	 */
	public void setConsultaCatService(ConsultaCatalogoService consultaCatService) {
		this.consultaCatService = consultaCatService;
	}

	/**
	 * @return the institucion
	 */
	public String getNombreInstitucion() {
		return nombreInstitucion;
	}

	/**
	 * @param institucion
	 *            the institucion to set
	 */
	public void setNombreInstitucion(String institucion) {
		this.nombreInstitucion = institucion;
	}

	/**
	 * @return the tenencia
	 */
	public String getTenencia() {
		return tenencia;
	}

	/**
	 * @param tenencia
	 *            the tenencia to set
	 */
	public void setTenencia(String tenencia) {
		this.tenencia = tenencia;
	}

	/**
	 * @param divisionInternacionalService
	 *            the divisionInternacionalService to set
	 */
	public void setDivisionInternacionalService(
			DivisionInternacionalService divisionInternacionalService) {
		this.divisionInternacionalService = divisionInternacionalService;
	}

	/**
	 * @return the listaCustodios
	 */
	public List<SelectItem> getListaCustodios() {
		return listaCustodios;
	}

	/**
	 * @param listaCustodios
	 *            the listaCustodios to set
	 */
	public void setListaCustodios(List<SelectItem> listaCustodios) {
		this.listaCustodios = listaCustodios;
	}

	/**
	 * @return the listaDepositantes
	 */
	public List<SelectItem> getListaDepositantes() {
		return listaDepositantes;
	}

	/**
	 * @param listaDepositantes
	 *            the listaDepositantes to set
	 */
	public void setListaDepositantes(List<SelectItem> listaDepositantes) {
		this.listaDepositantes = listaDepositantes;
	}

	/**
	 * @param operacionSicDao
	 *            the operacionSicDao to set
	 */
	public void setOperacionSicDao(OperacionSicDao operacionSicDao) {
		this.operacionSicDao = operacionSicDao;
	}

	/**
	 * @return the isoHelper
	 */
	public IsoHelper getIsoHelper() {
		return isoHelper;
	}

	/**
	 * @param isoHelper
	 *            the isoHelper to set
	 */
	public void setIsoHelper(IsoHelper isoHelper) {
		this.isoHelper = isoHelper;
	}

	/**
	 * @return the isoSinFirmar
	 */
	public String getIsoSinFirmar() {
		return isoSinFirmar;
	}

	/**
	 * @param isoSinFirmar
	 *            the isoSinFirmar to set
	 */
	public void setIsoSinFirmar(String isoSinFirmar) {
		this.isoSinFirmar = isoSinFirmar;
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
	 * @return the numeroSerie
	 */
	public String getNumeroSerie() {
		return numeroSerie;
	}

	/**
	 * @param numeroSerie
	 *            the numeroSerie to set
	 */
	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
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
	 * @return the cdb
	 */
	public CifradorDescifradorBlowfish getCdb() {
		return cdb;
	}

	/**
	 * @param cdb
	 *            the cdb to set
	 */
	public void setCdb(CifradorDescifradorBlowfish cdb) {
		this.cdb = cdb;
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
	 * @return the tlpvo
	 */
	public TraspasoLibrePagoVO getTlpvo() {
		return tlpvo;
	}

	/**
	 * @param tlpvo
	 *            the tlpvo to set
	 */
	public void setTlpvo(TraspasoLibrePagoVO tlpvo) {
		this.tlpvo = tlpvo;
	}
	


	public TraspasoContraPagoVO getDvpvo() {
		return dvpvo;
	}

	public void setDvpvo(TraspasoContraPagoVO dvpvo) {
		this.dvpvo = dvpvo;
	}

	public EmisionDao getEmisionDao() {
		return emisionDao;
	}

	public void setEmisionDao(EmisionDao emisionDao) {
		this.emisionDao = emisionDao;
	}

	/**
	 * Obtiene el valor del atributo cuponDao
	 * 
	 * @return el valor del atributo cuponDao
	 */
	public CuponDao getCuponDao() {
		return cuponDao;
	}

	/**
	 * Establece el valor del atributo cuponDao
	 * 
	 * @param cuponDao
	 *            el valor del atributo cuponDao a establecer
	 */
	public void setCuponDao(CuponDao cuponDao) {
		this.cuponDao = cuponDao;
	}

	public String getNombreCuenta() {
		return nombreCuenta;
	}

	public void setNombreCuenta(String nombreCuenta) {
		this.nombreCuenta = nombreCuenta;
	}

    public void setEmisionesConsultasService(EmisionesConsultasService emisionesConsultasService) {
        this.emisionesConsultasService = emisionesConsultasService;
    }

	/**
	 * @return the recepcion
	 */
	public boolean isRecepcion() {
		return recepcion;
	}

	/**
	 * @param recepcion the recepcion to set
	 */
	public void setRecepcion(boolean recepcion) {
		this.recepcion = recepcion;
	}

	/**
	 * @return the liquidacionParcial
	 */
	public String getLiquidacionParcial() {
		return liquidacionParcial;
	}

	/**
	 * @param liquidacionParcial the liquidacionParcial to set
	 */
	public void setLiquidacionParcial(String liquidacionParcial) {
		this.liquidacionParcial = liquidacionParcial;
	}

	/**
	 * @return the settlementDisciplineRegimeDao
	 */
	public SettlementDisciplineRegimeDao getSettlementDisciplineRegimeDao() {
		return settlementDisciplineRegimeDao;
	}

	/**
	 * @param settlementDisciplineRegimeDao the settlementDisciplineRegimeDao to set
	 */
	public void setSettlementDisciplineRegimeDao(SettlementDisciplineRegimeDao settlementDisciplineRegimeDao) {
		this.settlementDisciplineRegimeDao = settlementDisciplineRegimeDao;
	}

}
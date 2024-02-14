// Cambio Multidivisas
package com.indeval.portalinternacional.presentation.controller;

import com.bursatec.seguridad.vo.InstitucionVO;
import com.indeval.portaldali.middleware.services.util.ConsultaCatalogoService;
import com.indeval.portaldali.middleware.services.util.DateUtilService;
import com.indeval.portaldali.middleware.services.util.UtilService;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.persistence.util.constants.DaliConstants;
import com.indeval.portaldali.persistence.util.constants.TipoCuentaConstants;
import com.indeval.portalinternacional.common.util.CatalogosUtil;
import com.indeval.portalinternacional.common.util.ConsultaCatalogosFacade;
import com.indeval.portalinternacional.middleware.services.bitacora.BitacoraSupport;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.*;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.constantes.TipoCustodiaConstants;
import com.indeval.portalinternacional.middleware.servicios.dto.*;
import com.indeval.portalinternacional.middleware.servicios.modelo.Multidivisa;
import com.indeval.portalinternacional.middleware.servicios.modelo.Multidivisa.EstadoMovimiento;
import com.indeval.portalinternacional.middleware.servicios.modelo.Multidivisa.TipoMovimiento;
import com.indeval.portalinternacional.middleware.servicios.modelo.RetiroEfectivoIntPendientes;
import com.indeval.portalinternacional.middleware.servicios.vo.MovimientoEfectivoInternacionalVO;
import com.indeval.portalinternacional.presentation.controller.common.CapturaOperacionesController;
import com.indeval.sidv.bitacoraauditoria.middleware.service.BitacoraService;
import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Controller para captura de depositos o retiros de efectivo
 *
 * @author genner.cardenas
 * @since 14/06/2023
 */
public class MovimientosEfectivoController extends CapturaOperacionesController {

	/**
	 * Trasa de log
	 */
	private static final Logger LOG = LoggerFactory.getLogger(MovimientosEfectivoController.class);

	private Long idInstitucion;
	private String folioInstitucion;
	private String claveInstitucion;
	private String nombreInstitucion;
	private String descripcionBoveda;
	private String descripcionDivisa;
	private String idDivisa;
	private Long divisa;
	private Long boveda;
	private Integer idTipoMovimiento;
	private Long saldoDisponible;
	private Long importe;
	private Long saldoActual;
	private Integer referenciaNumerica;
	private String referenciaRelacionada;
	private String informacionRemesas;
	private String notasComentarios;

	/**
	 * Servicio para obtener la institucion
	 */
	private ConsultaCatalogoService consultaCatalogoService;

	private DivisionInternacionalService divisionInternacionalService;

	private DateUtilService dateUtilService;
	private UtilService utilService;

	private List<SelectItem> divisas;
	private List<SelectItem> bovedas;
	private CatalogosUtil catalogosUtil;
	private ConsultaCatalogosFacade catalogosFacade;
	private HorariosCustodiosService horariosCustodiosService;
	private CustodioService custodioService;
	private DiasInhabilesDivisasService diasInhabilesDivisasService;

	private static final Logger log = LoggerFactory.getLogger(MovimientosEfectivoController.class);

	/**
	 * agregado
	 ***/
	private MovimientoEfectivoInternacionalVO efectivoInternacionalVO;
	private boolean disableImporte;

	/**
	 * ID del tipo de intruccion
	 */
	private static final String TIPO_INSTR_TRASPASO_EFECTVIO = "TREF";

	private static final String DEPOSITO = "0";
	private static final String RETIRO = "1";

	private static final String ORIGEN = "PORTAL";
	private static final Integer STATUS = 0;
	private static final double PRECIO_INICIAL = 0.0;

	private SicService sicService;

	private BitacoraService bitacoraService;
	/**
	 * fin agregado
	 **/
	ConsultaDivisaServiceImpl divisaService;

	/**
	 * Servicio para asignar folio apartir de una secuencia
	 */
	private boolean renderReferencias;
	private boolean renderRemesasInfo;

	public String getInicializar() {
		divisas = new ArrayList<>();
		bovedas = new ArrayList<>();
		log.info("Iniciando modulo de movimientos de efectivo internacional");

		InstitucionVO institucion = new InstitucionVO();

		log.info("obteniendo la instituci�n activa");

		institucion = super.getInstitucionVigente();
		log.info(String.format("El id de la instituci�n es %d ", institucion.getId()));

		this.setIdInstitucion(institucion.getId());
		this.setFolioInstitucion(institucion.getFolio());
		this.setNombreInstitucion(institucion.getNombre() + " " + institucion.getId());
		this.setClaveInstitucion(institucion.getClave() + "" + institucion.getFolio());

		log.info("Institucion seteada");
		log.info("Inicializando Controlador");
		disableImporte = false;
		inicializaIso();
		divisas = new ArrayList<SelectItem>();
		divisas.add(new SelectItem("0", "Seleccione una Divisa"));
		bovedas = new ArrayList<SelectItem>();
		bovedas.add(new SelectItem("0", "Ingrese un folio"));
		efectivoInternacionalVO = new MovimientoEfectivoInternacionalVO();
		efectivoInternacionalVO.setTipoMovimiento(DEPOSITO);
		efectivoInternacionalVO.setFechaLiquidacion(new Date());

		final AgenteVO agente = getAgenteFirmado();
		efectivoInternacionalVO.setParticipante(agente.getId() + agente.getFolio());
		efectivoInternacionalVO.setNombreInstitucion(agente.getNombreCorto());
		final InstitucionWebDTO institucionTemp = catalogosFacade
				.buscarInstitucionPorIdFolio(efectivoInternacionalVO.getParticipante());
		divisas.addAll(catalogosFacade.getSelectItemsTipoDivisa());
		validarParticipante(null);
		if (!isUsuarioConFacultadFirmar()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Constantes.MENSAJE_FACULTAD_FIRMA_DIGITAL_REQUERIDA,
					Constantes.MENSAJE_FACULTAD_FIRMA_DIGITAL_REQUERIDA));
		}

		return "";
	}

	private void inicializaIso() {
		// Inicializa los ISO
		// Inicializa los ISO
		setIsoSinFirmar("");
		setIsoFirmado("");
		setHashIso("");
	}

	public void seleccionarDivisa(ActionEvent event) {
		try {
			LOG.info("divisaDTO.getId():" + efectivoInternacionalVO.getDivisa().getId());
			if (efectivoInternacionalVO.getDivisa().getIdString().equals("-1")) {
				efectivoInternacionalVO.setSaldoDisponible(null);
				efectivoInternacionalVO.setSaldoEfectivo(null);
				return;
			}
			bovedas = catalogosFacade.getSelectItemsBovedasEfectivoPorDivisa(efectivoInternacionalVO.getDivisa());
			for (SelectItem item : divisas) {
				if (item.getValue().equals(efectivoInternacionalVO.getDivisa().getIdString())) {
					efectivoInternacionalVO.getDivisa().setClaveAlfabetica(item.getLabel());
				}
			}
			LOG.info("efectivoInternacionalVO.getIdInstitucion():" + efectivoInternacionalVO.getIdInstitucion());
			final CuentaCorresponsalDTO corresponsalDTO = catalogosFacade
					.getCuentasCorresponsalesByDivisaAndInstitucion(efectivoInternacionalVO.getDivisa().getId(),
							efectivoInternacionalVO.getIdInstitucion().longValue());
			LOG.info("Cuenta Corresponsa:" + corresponsalDTO);
			if (corresponsalDTO != null) {
				efectivoInternacionalVO.setTipoMensaje(corresponsalDTO.getMensaje());

				setOperacionRetiro(corresponsalDTO,
						Constantes.MT_103.equals(efectivoInternacionalVO.getTipoMensaje()) && RETIRO.equals(efectivoInternacionalVO.getTipoMovimiento()),
						Constantes.MT_202.equals(efectivoInternacionalVO.getTipoMensaje()) && RETIRO.equals(efectivoInternacionalVO.getTipoMovimiento())
				);

			}
			if (efectivoInternacionalVO.getBoveda().getIdBoveda() != null && !efectivoInternacionalVO.getBoveda().getIdBoveda().equals(-1)) {
				efectivoInternacionalVO.setSaldoDisponible(catalogosFacade.getSaldoNetoEfectivoPorBovedaDivisa(efectivoInternacionalVO.getParticipante(),
						efectivoInternacionalVO.getBoveda().getIdBoveda().longValue(), efectivoInternacionalVO.getDivisa().getId()));
				if (efectivoInternacionalVO.getImporteTraspasar() != null && efectivoInternacionalVO.getImporteTraspasar() > 0) {
					if (DEPOSITO.equals(efectivoInternacionalVO.getTipoMovimiento())) {
						if (efectivoInternacionalVO.getSaldoDisponible() != null) {
							final Double operacion = efectivoInternacionalVO.getSaldoDisponible() + efectivoInternacionalVO.getImporteTraspasar();
							efectivoInternacionalVO.setSaldoEfectivo(operacion);
						} else {
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe de existir un saldo disponible, por favor revisar.", "Debe de existir un saldo disponible, por favor revisar."));
						}
					} else if (RETIRO.equals(efectivoInternacionalVO.getTipoMovimiento())) {
						if (efectivoInternacionalVO.getSaldoDisponible() != null) {
							final Double operacion = efectivoInternacionalVO.getSaldoDisponible() - efectivoInternacionalVO.getImporteTraspasar();
							efectivoInternacionalVO.setSaldoEfectivo(operacion);
						} else {
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe de existir un saldo disponible, por favor revisar.", "Debe de existir un saldo disponible, por favor revisar."));
						}
					}
				}
			} else {
				efectivoInternacionalVO.setSaldoDisponible((double) 0);
				efectivoInternacionalVO.setImporteTraspasar((double) 0);
				efectivoInternacionalVO.setSaldoEfectivo((double) 0);
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
		}
	}

	private void setOperacionRetiro(CuentaCorresponsalDTO corresponsalDTO, boolean primeraComprobacion, boolean segundaComprobacion) {
		if (primeraComprobacion) {
			renderRemesasInfo = true;
			if (corresponsalDTO.getActivoCorresponsalPrincipal()) {
				efectivoInternacionalVO.setIntermediaryOption(corresponsalDTO.getIntermediaryOptionP());
				efectivoInternacionalVO.setIntermediaryValue(corresponsalDTO.getIntermediaryValueP());
				efectivoInternacionalVO.setIntermediaryNameAddress(corresponsalDTO.getIntermediaryNameAddressP());
				efectivoInternacionalVO.setAccountOption(corresponsalDTO.getAccountOptionP());
				efectivoInternacionalVO.setAccountLocation(corresponsalDTO.getAccountLocationP());
				efectivoInternacionalVO.setAccountNameAddress(corresponsalDTO.getAccountNameAddressP());
				efectivoInternacionalVO.setAccountValue(corresponsalDTO.getAccountValueP());
				efectivoInternacionalVO.setBeneficiaryOption(corresponsalDTO.getBeneficiaryOptionP());
				efectivoInternacionalVO.setBeneficiaryNameAddress(corresponsalDTO.getBeneficiaryNameAddressP());
				efectivoInternacionalVO.setBeneficiaryNumberName(corresponsalDTO.getBeneficiaryNumberNameP());
				efectivoInternacionalVO.setBeneficiaryValue(corresponsalDTO.getBeneficiaryValueP());
				efectivoInternacionalVO.setIntermediaryBic(corresponsalDTO.getIntermediaryBicP());
				efectivoInternacionalVO.setAccountBic(corresponsalDTO.getAccountBicP());
				efectivoInternacionalVO.setBeneficiaryBic(corresponsalDTO.getBeneficiaryBicP());
			} else if (corresponsalDTO.getActivoCorresponsalBackup()) {
				efectivoInternacionalVO.setIntermediaryOption(corresponsalDTO.getIntermediaryOptionB());
				efectivoInternacionalVO.setIntermediaryValue(corresponsalDTO.getIntermediaryValueB());
				efectivoInternacionalVO.setIntermediaryNameAddress(corresponsalDTO.getIntermediaryNameAddressB());
				efectivoInternacionalVO.setAccountOption(corresponsalDTO.getAccountOptionB());
				efectivoInternacionalVO.setAccountLocation(corresponsalDTO.getAccountLocationB());
				efectivoInternacionalVO.setAccountNameAddress(corresponsalDTO.getAccountNameAddressB());
				efectivoInternacionalVO.setBeneficiaryOption(corresponsalDTO.getBeneficiaryOptionB());
				efectivoInternacionalVO.setBeneficiaryNumberName(corresponsalDTO.getBeneficiaryNumberNameB());
				efectivoInternacionalVO.setAccountValue(corresponsalDTO.getAccountValueB());
				efectivoInternacionalVO.setBeneficiaryNameAddress(corresponsalDTO.getBeneficiaryNameAddressB());
				efectivoInternacionalVO.setBeneficiaryValue(corresponsalDTO.getBeneficiaryValueB());
				efectivoInternacionalVO.setIntermediaryBic(corresponsalDTO.getIntermediaryBicB());
				efectivoInternacionalVO.setAccountBic(corresponsalDTO.getAccountBicB());
				efectivoInternacionalVO.setBeneficiaryBic(corresponsalDTO.getBeneficiaryBicB());
			}
		} else if (segundaComprobacion) {
			renderRemesasInfo = true;
			if (corresponsalDTO.getActivoCorresponsalPrincipal()) {
				efectivoInternacionalVO.setIntermediaryOption(corresponsalDTO.getIntermediaryOptionP());
				efectivoInternacionalVO.setIntermediaryValue(corresponsalDTO.getIntermediaryValueP());
				efectivoInternacionalVO.setIntermediaryNameAddress(corresponsalDTO.getIntermediaryNameAddressP());
				efectivoInternacionalVO.setAccountOption(corresponsalDTO.getAccountOptionP());
				efectivoInternacionalVO.setAccountLocation(corresponsalDTO.getAccountLocationP());
				efectivoInternacionalVO.setAccountNameAddress(corresponsalDTO.getAccountNameAddressP());
				efectivoInternacionalVO.setAccountValue(corresponsalDTO.getAccountValueP());
				efectivoInternacionalVO.setBeneficiaryOption(corresponsalDTO.getBeneficiaryOptionP());
				efectivoInternacionalVO.setBeneficiaryNameAddress(corresponsalDTO.getBeneficiaryNameAddressP());
				efectivoInternacionalVO.setBeneficiaryValue(corresponsalDTO.getBeneficiaryValueP());
				efectivoInternacionalVO.setIntermediaryBic(corresponsalDTO.getIntermediaryBicP());
				efectivoInternacionalVO.setAccountBic(corresponsalDTO.getAccountBicP());
				efectivoInternacionalVO.setBeneficiaryBic(corresponsalDTO.getBeneficiaryBicP());
			} else if (corresponsalDTO.getActivoCorresponsalBackup()) {
				efectivoInternacionalVO.setIntermediaryOption(corresponsalDTO.getIntermediaryOptionB());
				efectivoInternacionalVO.setIntermediaryValue(corresponsalDTO.getIntermediaryValueB());
				efectivoInternacionalVO.setIntermediaryNameAddress(corresponsalDTO.getIntermediaryNameAddressB());
				efectivoInternacionalVO.setAccountOption(corresponsalDTO.getAccountOptionB());
				efectivoInternacionalVO.setAccountLocation(corresponsalDTO.getAccountLocationB());
				efectivoInternacionalVO.setAccountNameAddress(corresponsalDTO.getAccountNameAddressB());
				efectivoInternacionalVO.setBeneficiaryOption(corresponsalDTO.getBeneficiaryOptionB());
				efectivoInternacionalVO.setAccountValue(corresponsalDTO.getAccountValueB());
				efectivoInternacionalVO.setBeneficiaryNameAddress(corresponsalDTO.getBeneficiaryNameAddressB());
				efectivoInternacionalVO.setBeneficiaryValue(corresponsalDTO.getBeneficiaryValueB());
				efectivoInternacionalVO.setIntermediaryBic(corresponsalDTO.getIntermediaryBicB());
				efectivoInternacionalVO.setAccountBic(corresponsalDTO.getAccountBicB());
				efectivoInternacionalVO.setBeneficiaryBic(corresponsalDTO.getBeneficiaryBicB());
			}
		}
	}

	public void validarParticipante(ActionEvent event) {
		final InstitucionWebDTO institucionTemp = catalogosFacade
				.buscarInstitucionPorIdFolio(efectivoInternacionalVO.getParticipante());
		efectivoInternacionalVO.setSaldoDisponible(null);
		efectivoInternacionalVO.setSaldoEfectivo(null);
		if (institucionTemp != null) {
			disableImporte = true;
			efectivoInternacionalVO.setFolioControl(catalogosFacade.getInternacionalService().getFolioControl());
			final CuentaEfectivoDTO cuentaEfectivoDTO = buscarParticipanteEfectivo(
					efectivoInternacionalVO.getParticipante());
			if (cuentaEfectivoDTO != null) {
				efectivoInternacionalVO.setCuenta(cuentaEfectivoDTO.getCuenta());
				efectivoInternacionalVO.setIdCuenta(cuentaEfectivoDTO.getIdCuenta());
			}
			efectivoInternacionalVO.setNombreInstitucion(institucionTemp.getNombre());
			efectivoInternacionalVO.setIdInstitucion(institucionTemp.getIdInstitucion());
			efectivoInternacionalVO
					.setSaldoDisponible(PRECIO_INICIAL);
			divisas = new ArrayList<SelectItem>();
			divisas.add(new SelectItem("-1", "Seleccione una Divisa"));

			if (efectivoInternacionalVO.getTipoMovimiento().equals(DEPOSITO)) {
				divisas.addAll(catalogosFacade.getSelectItemsTipoDivisa());
			} else {
				divisas.addAll(catalogosFacade.obtenerDivisasPorInstitucion(institucionTemp.getIdInstitucion()));
			}
			if (divisas != null && !divisas.isEmpty()) {
				final DivisaDTO divisaDTO = new DivisaDTO();
				divisaDTO.setId(Long.parseLong((String) divisas.get(0).getValue()));
				divisaDTO.setClaveAlfabetica(divisas.get(0).getLabel());
				efectivoInternacionalVO.setDivisa(divisaDTO);
				bovedas = catalogosFacade.getSelectItemsBovedasEfectivoPorDivisa(divisaDTO);
				LOG.info("divisaDTO.getId():" + divisaDTO.getId());
				LOG.info("efectivoInternacionalVO.getIdInstitucion():" + efectivoInternacionalVO.getIdInstitucion());
				final CuentaCorresponsalDTO corresponsalDTO = catalogosFacade
						.getCuentasCorresponsalesByDivisaAndInstitucion(divisaDTO.getId(),
								efectivoInternacionalVO.getIdInstitucion().longValue());
				LOG.info("Cuenta Corresponsa:" + corresponsalDTO);
				if (corresponsalDTO != null) {
					efectivoInternacionalVO.setTipoMensaje(corresponsalDTO.getMensaje());

					setOperacionRetiro(corresponsalDTO,
							Constantes.MT_103.equals(efectivoInternacionalVO.getTipoMensaje()) && RETIRO.equals(efectivoInternacionalVO.getTipoMovimiento()),
							Constantes.MT_202.equals(efectivoInternacionalVO.getTipoMensaje()) && RETIRO.equals(efectivoInternacionalVO.getTipoMovimiento())
					);
				}
			} else {
				divisas = new ArrayList<>();
				divisas.add(new SelectItem("-1", "No se encontraron divisas"));
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El participante ingresado no cuenta con divisas registradas, por favor revisar.", "El participante ingresado no cuenta con divisas registradas, por favor revisar."));
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se encontro el participante ingresado, por favor revisar.", "No se encontro el participante ingresado, por favor revisar."));
		}
	}

	public CuentaEfectivoDTO buscarParticipanteEfectivo(String idFolioPariticipante) {
		CuentaEfectivoDTO criterio = new CuentaEfectivoDTO();
		CuentaEfectivoDTO resultado = null;
		List<CuentaEfectivoDTO> resultados = new ArrayList<CuentaEfectivoDTO>();

		InstitucionWebDTO institucion = new InstitucionWebDTO();
		criterio.setTipoCustodia(TipoCustodiaConstants.EFECTIVO);
		criterio.setTipoCuenta(new TipoCuentaDTO(TipoCuentaConstants.TIPO_NOMBRADA, ""));
		criterio.setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO, ""));

		if (!StringUtils.isEmpty(idFolioPariticipante)) {
			institucion = catalogosFacade.buscarInstitucionPorIdFolio(idFolioPariticipante);
		}
		if (institucion != null) {
			criterio.setInstitucion(institucion);
			criterio.setNumeroCuenta(String.valueOf(DaliConstants.VALOR_COMBO_TODOS));
			resultados = catalogosFacade.getConsultaCuentaService().buscarCuentasNombradasEfectivo(criterio, null);
			if (resultados != null && !resultados.isEmpty()) {
				resultado = resultados.get(0);
			}
		}
		BovedaDto boveda = new BovedaDto();
		boveda.setIdBoveda(11);
		efectivoInternacionalVO.setBoveda(boveda);
		return resultado;
	}

	public void seleccionarBoveda(ActionEvent event) {
		Integer idBoveda = getIdBoveda();
		if (idBoveda == null) return;

		obtenerSaldoDisponible(idBoveda);

		if (efectivoInternacionalVO.getImporteTraspasar() != null && efectivoInternacionalVO.getImporteTraspasar() > 0) {
			comprobarSaldoDisponible();
		} else {
			efectivoInternacionalVO.setSaldoEfectivo(null);
		}
	}

	private void obtenerSaldoDisponible(Integer idBoveda) {
		if (RETIRO.equals(efectivoInternacionalVO.getTipoMovimiento())) {
			efectivoInternacionalVO.setSaldoDisponible(catalogosFacade.getSaldoDisponibleEfectivoPorBovedaDivisa(efectivoInternacionalVO.getParticipante(),
					idBoveda.longValue(), efectivoInternacionalVO.getDivisa().getId()));
		} else {
			efectivoInternacionalVO.setSaldoDisponible(catalogosFacade.getSaldoNetoEfectivoPorBovedaDivisa(efectivoInternacionalVO.getParticipante(),
					idBoveda.longValue(), efectivoInternacionalVO.getDivisa().getId()));
		}
	}

	private Integer getIdBoveda() {
		final String idBovedaStr = efectivoInternacionalVO.getBoveda().getIdBovedaStr();
		Integer idBoveda = efectivoInternacionalVO.getBoveda().getIdBoveda();
		for (SelectItem boveda : bovedas) {
			if (boveda.getValue().equals(efectivoInternacionalVO.getBoveda().getIdBovedaStr())) {
				efectivoInternacionalVO.getBoveda().setNombreCorto(boveda.getLabel());
			}
		}
		if (idBovedaStr != null) {
			idBoveda = Integer.parseInt(idBovedaStr);
			if (idBovedaStr.equals("-1")) {
				efectivoInternacionalVO.setSaldoDisponible(null);
				efectivoInternacionalVO.setSaldoEfectivo(null);
				return null;
			}
		}
		return idBoveda;
	}

	public void seleccionarTipoMovimiento(ActionEvent event) {
		Integer idBoveda = getIdBoveda();

		final InstitucionWebDTO institucionTemp = catalogosFacade
				.buscarInstitucionPorIdFolio(efectivoInternacionalVO.getParticipante());

		renderReferencias = false;
		renderRemesasInfo = false;
		if (institucionTemp != null) {
			divisas = new ArrayList<SelectItem>();
			divisas.add(new SelectItem("-1", "Seleccione una Divisa"));
			if (RETIRO.equals(efectivoInternacionalVO.getTipoMovimiento())) {
				renderReferencias = true;
				renderRemesasInfo = Constantes.MT_103.equals(efectivoInternacionalVO.getTipoMensaje());
				divisas.addAll(catalogosFacade.obtenerDivisasPorInstitucion(institucionTemp.getIdInstitucion()));
				if (idBoveda != null) {
					efectivoInternacionalVO.setSaldoDisponible(catalogosFacade.getSaldoDisponibleEfectivoPorBovedaDivisa(efectivoInternacionalVO.getParticipante(),
							idBoveda.longValue(), efectivoInternacionalVO.getDivisa().getId()));
				}

				///////////////
				if (efectivoInternacionalVO.getDivisa() != null) {
					final CuentaCorresponsalDTO corresponsalDTO = catalogosFacade
							.getCuentasCorresponsalesByDivisaAndInstitucion(efectivoInternacionalVO.getDivisa().getId(),
									efectivoInternacionalVO.getIdInstitucion().longValue());
					LOG.info("Cuenta Corresponsa:" + corresponsalDTO);
					if (corresponsalDTO != null) {
						efectivoInternacionalVO.setTipoMensaje(corresponsalDTO.getMensaje());

						setOperacionRetiro(corresponsalDTO,
								Constantes.MT_103.equals(efectivoInternacionalVO.getTipoMensaje()) && RETIRO.equals(efectivoInternacionalVO.getTipoMovimiento()),
								Constantes.MT_202.equals(efectivoInternacionalVO.getTipoMensaje()) && RETIRO.equals(efectivoInternacionalVO.getTipoMovimiento())
						);

					}
				}

				//////////////


			} else {
				efectivoInternacionalVO.setFechaLiquidacion(new Date());
				divisas.addAll(catalogosFacade.getSelectItemsTipoDivisa());
				if (idBoveda != null) {
					efectivoInternacionalVO.setSaldoDisponible(catalogosFacade.getSaldoNetoEfectivoPorBovedaDivisa(efectivoInternacionalVO.getParticipante(),
							idBoveda.longValue(), efectivoInternacionalVO.getDivisa().getId()));
				}
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se encontro el participante ingresado, por favor revisar.", "No se encontro el participante ingresado, por favor revisar."));
		}
		if (efectivoInternacionalVO.getImporteTraspasar() != null && efectivoInternacionalVO.getImporteTraspasar() > 0) {
			comprobarSaldoDisponible();
		} else {
			efectivoInternacionalVO.setSaldoEfectivo(null);
		}
	}

	public void onChangeImporte(ActionEvent event) {
		LOG.info("Realizando Operacion de Suma/Resta importe:" + efectivoInternacionalVO.getTipoMovimiento());
		comprobarSaldoDisponible();
	}

	public void limpiar(ActionEvent event) {
		System.out.println("Dentro de limpiar");
		getInicializar();
	}

	public void notIsZero(ActionEvent event) {

		if (efectivoInternacionalVO.getReferenciaNumerica() != null && efectivoInternacionalVO.getReferenciaNumerica().trim().equals("0")) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El valor de la referencia numérica no puede ser cero., por favor revisar.", "El valor de la referencia numérica no puede ser cero., por favor revisar."));
		}
	}


	public void enviarOperacion(ActionEvent event) {
		boolean hayError = false;
		try {
			if (!isUsuarioConFacultadFirmar()) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Constantes.MENSAJE_FACULTAD_FIRMA_DIGITAL_REQUERIDA,
						Constantes.MENSAJE_FACULTAD_FIRMA_DIGITAL_REQUERIDA));
				return;
			}

			if (efectivoInternacionalVO.getTipoMovimiento().equals(DEPOSITO) || efectivoInternacionalVO.getTipoMovimiento().equals(RETIRO)) {

				if (efectivoInternacionalVO.getDivisa() == null ||
						efectivoInternacionalVO.getDivisa().getIdString() == null ||
						efectivoInternacionalVO.getDivisa().getIdString().isEmpty() ||
						efectivoInternacionalVO.getDivisa().getIdString().equals("-1")) {
					addErrorMessage("Seleccione divisa por favor ");
					hayError = true;
				} else if (efectivoInternacionalVO.getBoveda() == null || efectivoInternacionalVO.getBoveda().getIdBovedaStr() == null
						|| efectivoInternacionalVO.getBoveda().getIdBovedaStr().isEmpty() || efectivoInternacionalVO.getBoveda().getIdBovedaStr().equals("-1")) {
					addErrorMessage("Seleccione una b&oacute;veda por favor");
					hayError = true;
				}  else if (efectivoInternacionalVO.getImporteTraspasar() == null || efectivoInternacionalVO.getImporteTraspasar().isNaN()) {
					addErrorMessage("El importe es obligatorio");
					hayError = true;
				}else if (efectivoInternacionalVO.getReferenciaRelacionada() == null || efectivoInternacionalVO.getReferenciaRelacionada().isEmpty()) {
					addErrorMessage("La referencia relacionada es obligatoria");
					hayError = true;
				}

				if (efectivoInternacionalVO.getTipoMovimiento().equals(RETIRO)) {

					if (efectivoInternacionalVO.getReferenciaNumerica() == null
							|| efectivoInternacionalVO.getReferenciaNumerica().isEmpty()) {
						addErrorMessage("La referencia relacionada es obligatoria");
						hayError = true;
					}

					final CuentaCorresponsalDTO corresponsalDTO = catalogosFacade
							.getCuentasCorresponsalesByDivisaAndInstitucion(efectivoInternacionalVO.getDivisa().getId(),
									efectivoInternacionalVO.getIdInstitucion().longValue());

					if (corresponsalDTO == null) {
						addErrorMessage("No se encontró una cuenta corresponsal asociada");
						hayError = true;
					}
				}

			}

			if (hayError) {
				return;
			}

			obtenerSaldoDisponible(getIdBoveda());
			comprobarSaldoDisponible();

			if (validaUsuarioPermitidoRegistrar(efectivoInternacionalVO)) {
				if (validarDTO()) {
					Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
					String numeroSerie = params.get("numeroSerie");
					inicializaIso();
					if (StringUtils.isNotBlank(numeroSerie)) {
						LOG.info("Movimiento a Guardar:" + efectivoInternacionalVO);
						final String folioControl = new String(efectivoInternacionalVO.getFolioControl().toString());
						final Long idBoveda = efectivoInternacionalVO.getBoveda().getIdBoveda().longValue();
						final Long idInstitucion = efectivoInternacionalVO.getIdInstitucion().longValue();
						final Long idCatbic = catalogosFacade.findCatBicEnBaseABovedaEfectivoParticipante(idBoveda, idInstitucion);
						efectivoInternacionalVO.setIdCatbic(idCatbic);
						LOG.info("idCatbic:" + idCatbic);
						LOG.info("getIdCatbic():" + efectivoInternacionalVO.getIdCatbic());
						efectivoInternacionalVO.setFechaAlta(new Date());

						catalogosFacade.getInternacionalService().saveMovimientoEfectivoInternacional(efectivoInternacionalVO);

						final Multidivisa notificacionMoi = new Multidivisa();
						notificacionMoi.setId(efectivoInternacionalVO.getIdMovimiento());
						notificacionMoi.setFolioConstrol(Long.parseLong(folioControl));

						if (efectivoInternacionalVO.getTipoMovimiento().equals(DEPOSITO)) {
							notificacionMoi.setTipoMovimiento(TipoMovimiento.DEPOSITO);
							notificacionMoi.setEstado(EstadoMovimiento.RETENIDO);
						} else {
							notificacionMoi.setTipoMovimiento(TipoMovimiento.RETIRO);
							notificacionMoi.setEstado(EstadoMovimiento.REGISTRADO);
						}

						String mensajeXML = this.sicService.crearXML(notificacionMoi);


						if (efectivoInternacionalVO.getTipoMovimiento().equals(RETIRO)) {
							if (!(esHorarioCustodioValido(idCatbic) && esDiaHabilDivisa(efectivoInternacionalVO.getDivisa().getId()))) {
								Long id = efectivoInternacionalVO.getIdMovimiento();

								RetiroEfectivoIntPendientes retiroEfectivoIntPendientes = new RetiroEfectivoIntPendientes();
								retiroEfectivoIntPendientes.setIdMovimientoRetiroEfectivoInt(id.intValue());
								retiroEfectivoIntPendientes.setMensaje(mensajeXML);
								retiroEfectivoIntPendientes.setOrigen(ORIGEN);
								retiroEfectivoIntPendientes.setIdEstatus(STATUS);
								retiroEfectivoIntPendientes.setFechaRegistro(efectivoInternacionalVO.getFechaAlta());
								retiroEfectivoIntPendientes.setFechaProceso(efectivoInternacionalVO.getFechaAlta());
								retiroEfectivoIntPendientes.setComentario("Registro de Retiro fuera de Horario o Dia Habil");

								catalogosFacade.getRetiroEfectivoIntPendientesService().saveRetiroEfectivoIntPendientes(retiroEfectivoIntPendientes);
								FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
										FacesMessage.SEVERITY_ERROR, "Retiro pendiente por estar fuera de Horario o Dia Habil.", "Retiro pendiente por estar fuera de Horario o Dia Habil."));
							}
						}

						final MovimientoEfectivoInternacionalVO movDespues = (MovimientoEfectivoInternacionalVO) SerializationUtils.clone(efectivoInternacionalVO);
						movDespues.setFolioControl(Long.parseLong(folioControl));
						LOG.info("Folio Control:" + movDespues.getFolioControl());

						if (efectivoInternacionalVO.getTipoMovimiento().equals(DEPOSITO)) {
							movDespues.setEstadoMovimiento(Constantes.ID_ESTADO_MOVIMIENTO_EFECTIVO_RETENIDO);
							movDespues.setDescEstadoMovimiento(Constantes.DESC_ESTADO_MOVIMIENTO_EFECTIVO_RETENIDO);
						} else {
							movDespues.setEstadoMovimiento(Constantes.ID_ESTADO_MOVIMIENTO_EFECTIVO_REGISTRADO);
							movDespues.setDescEstadoMovimiento(Constantes.DESC_ESTADO_MOVIMIENTO_EFECTIVO_REGISTRADO);
						}

						this.sicService.notificaCambioEstadoMovEfeDivInt(mensajeXML);
						final ObjetoFirmadoDTO objDespues = new ObjetoFirmadoDTO(numeroSerie, params.get("isoFirmado"), movDespues);
						boolean registraBitacora = BitacoraSupport.doRegistrarBitacora(getCveUsuarioSesion(), objDespues, objDespues, this.bitacoraService,
								Constantes.ID_MODULO_MOV_EFE_DIV_EXT, Constantes.ID_OP_TR_REGISTRO_MOV_EFE_DIV_EXT);
						if (registraBitacora) {
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
									FacesMessage.SEVERITY_INFO, "Movimiento registrado exitosamente con folio control " + folioControl
									+ ".", "Movimiento registrado exitosamente con folio control " + folioControl + "."));
						} else {
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
									FacesMessage.SEVERITY_ERROR, "Ha ocurrido un error al insertar el registro.", "Ha ocurrido un error al insertar el registro."));
						}
						getInicializar();
						renderReferencias = false;
						renderRemesasInfo = false;
					} else {
						String isoData = procesarDatos();
						isoData += '\n';
						LOG.info("isoData:" + isoData);
						if (StringUtils.isNotBlank(isoData)) {
							isoSinFirmar = isoData;
							hashIso = cdb.cipherHash(isoSinFirmar);
						}
					}
				}
			}
		} catch (Exception e) {
			LOG.info("Error:" + e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocurrio un error al enviar la operacion:" + e.getMessage(), e.getMessage()));
		}
	}

	private void comprobarSaldoDisponible() {
		if (DEPOSITO.equals(efectivoInternacionalVO.getTipoMovimiento())) {
			if (efectivoInternacionalVO.getSaldoDisponible() != null) {
				final Double operacion = efectivoInternacionalVO.getSaldoDisponible() + efectivoInternacionalVO.getImporteTraspasar();
				efectivoInternacionalVO.setSaldoEfectivo(operacion);
			} else {

				if (efectivoInternacionalVO.getBoveda().getIdBovedaStr().equals("-1")) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar una boveda, por favor revisar.", "Debe seleccionar una boveda, por favor revisar."));
				}

				if (efectivoInternacionalVO.getDivisa().getIdString().equals("-1")) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar una divisa, por favor revisar.", "Debe seleccionar una divisa, por favor revisar."));
				} else {
					efectivoInternacionalVO.setSaldoEfectivo(efectivoInternacionalVO.getSaldoDisponible());
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe de existir un saldo disponible, por favor revisar.", "Debe de existir un saldo disponible, por favor revisar."));
				}

			}
		} else if (RETIRO.equals(efectivoInternacionalVO.getTipoMovimiento())) {
			if (efectivoInternacionalVO.getSaldoDisponible() != null) {
				if (efectivoInternacionalVO.getImporteTraspasar() > efectivoInternacionalVO.getSaldoDisponible()) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El importe disponible debe ser menor al saldo actual, por favor revisar.", "El importe disponible debe ser menor al saldo actual, por favor revisar."));
					efectivoInternacionalVO.setSaldoEfectivo(null);
				} else if (efectivoInternacionalVO.getImporteTraspasar() == null) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe de existir un importe a traspasar, por favor revisar.", "Debe de existir un importe a traspasar, por favor revisar."));
					efectivoInternacionalVO.setSaldoEfectivo(null);
				} else {
					final Double operacion = efectivoInternacionalVO.getSaldoDisponible() - efectivoInternacionalVO.getImporteTraspasar();
					efectivoInternacionalVO.setSaldoEfectivo(operacion);
				}
			} else {
				if (efectivoInternacionalVO.getBoveda().getIdBovedaStr().equals("-1")) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar una boveda, por favor revisar.", "Debe seleccionar una boveda, por favor revisar."));
				}
				if (efectivoInternacionalVO.getDivisa().getIdString().equals("-1")) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar una divisa, por favor revisar.", "Debe seleccionar una divisa, por favor revisar."));
				} else {
					efectivoInternacionalVO.setSaldoEfectivo(efectivoInternacionalVO.getSaldoDisponible());
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe de existir un saldo disponible, por favor revisar.", "Debe de existir un saldo disponible, por favor revisar."));
				}

			}
		}
	}

	private boolean validaUsuarioPermitidoRegistrar(MovimientoEfectivoInternacionalVO movimiento) {
		if (!catalogosFacade.getInternacionalService().esUsuarioPermitidoAutorizar(getCveUsuarioSesion(), movimiento.getFolioControl())) {
			String mensaje = Constantes.MENSAJE_USUARIO_INVALIDO_AUTORIZACION + movimiento.getFolioControl();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, mensaje, mensaje));
			return false;
		}
		return true;
	}

	private boolean validarDTO() {
		Boolean resultado = Boolean.TRUE;

		if (efectivoInternacionalVO.getFechaLiquidacion() != null) {
			final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			final String actual = dateFormat.format(new Date());
			final String fechaIngresada = dateFormat.format(efectivoInternacionalVO.getFechaLiquidacion());

			Date date1;
			Date date2;
			try {
				date1 = dateFormat.parse(actual);
				date2 = dateFormat.parse(fechaIngresada);
				if (date2.before(date1)) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "La fecha de liquidacion debe ser mayor o igual a hoy, por favor revisar.", "La fecha de liquidacion debe ser mayor o igual a hoy, por favor revisar."));
					resultado = Boolean.FALSE;
				}
			} catch (ParseException e) {
				LOG.info("ERROR:" + e.getMessage());
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Por favor introduzca una Fecha de Liquidaci�n.", "Por favor introduzca una Fecha de Liquidaci�n."));
			resultado = Boolean.FALSE;
		}

		if (efectivoInternacionalVO.getImporteTraspasar() != null && efectivoInternacionalVO.getImporteTraspasar().doubleValue() <= 0) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El importe a traspasar debe ser mayor a 0, por favor revisar.", "El importe a traspasar debe ser mayor a 0"));
			resultado = Boolean.FALSE;
		} else if (efectivoInternacionalVO.getImporteTraspasar() == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El importe a traspasar debe ser mayor a 0, por favor revisar.", "El importe a traspasar debe ser mayor a 0"));
			resultado = Boolean.FALSE;
		}

		if (StringUtils.isBlank(efectivoInternacionalVO.getParticipante())) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El participante es requerido, por favor revisar.", "El participante es requerido, por favor revisar."));
			resultado = Boolean.FALSE;
		}

		if (StringUtils.isBlank(efectivoInternacionalVO.getBoveda().getIdBovedaStr())) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "La boveda es requerida, por favor revisar.", "La boveda es requerida, por favor revisar."));
			resultado = Boolean.FALSE;
		} else if (efectivoInternacionalVO.getBoveda().getIdBovedaStr().equals("-1")) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "La boveda es requerida, por favor revisar.", "La boveda es requerida, por favor revisar."));
			resultado = Boolean.FALSE;
		} else if (efectivoInternacionalVO.getBoveda().getIdBoveda().equals(-1)) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "La boveda es requerida, por favor revisar.", "La boveda es requerida, por favor revisar."));
			resultado = Boolean.FALSE;
		}

		if (StringUtils.isBlank(efectivoInternacionalVO.getDivisa().getIdString())) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "La divisa es requerida, por favor revisar.", "La divisa es requerida, por favor revisar."));
			resultado = Boolean.FALSE;
		} else if (efectivoInternacionalVO.getDivisa().getIdString().equals("-1")) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "La divisa es requerida, por favor revisar.", "La divisa es requerida, por favor revisar."));
			resultado = Boolean.FALSE;
		}

		if (RETIRO.equals(efectivoInternacionalVO.getTipoMovimiento())) {

			String regex = "^[1-9][0-9]*$";
			// return !numero.equals("0") && Pattern.matches(regex, numero);

			if (efectivoInternacionalVO.getReferenciaNumerica() == null) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "La referencia numerica es requerida, por favor revisar.", "La referencia numerica es requerida, por favor revisar."));
				resultado = Boolean.FALSE;
			}

			if (StringUtils.isBlank(efectivoInternacionalVO.getReferenciaNumerica())) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "La referencia numerica es requerida, por favor revisar.", "La referencia numerica es requerida, por favor revisar."));
				resultado = Boolean.FALSE;
			}

			if (efectivoInternacionalVO.getReferenciaNumerica().equals("0")) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "La referencia numerica no puede ser cero, por favor revisar.", "La referencia numerica no puede ser cero, por favor revisar."));
				resultado = Boolean.FALSE;
			}

			if (efectivoInternacionalVO.getReferenciaNumerica().isEmpty() && efectivoInternacionalVO.getReferenciaNumerica().length() > 16) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "La referencia numerica debe tener entre 1 y 16 caracteres, por favor revisar.", "La referencia numerica debe tener entre 1 y 16 caracteres, por favor revisar."));
				resultado = Boolean.FALSE;
			}

			if (!Pattern.matches(regex, efectivoInternacionalVO.getReferenciaNumerica())) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "La referencia numerica debe contener solo números, por favor revisar.", "La referencia numerica debe contener solo números, por favor revisar."));
				resultado = Boolean.FALSE;
			}

			if (StringUtils.isBlank(efectivoInternacionalVO.getReferenciaRelacionada())) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "La referencia relacionada es requerida, por favor revisar.", "La referencia relacionada es requerida, por favor revisar."));
				resultado = Boolean.FALSE;
			}

			if (efectivoInternacionalVO.getReferenciaRelacionada().isEmpty() && efectivoInternacionalVO.getReferenciaRelacionada().length() > 16) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "La referencia relacionada debe tener entre 1 y 16 caracteres, por favor revisar.", "La referencia relacionada debe tener entre 1 y 16 caracteres, por favor revisar."));
				resultado = Boolean.FALSE;
			}

			if (efectivoInternacionalVO.getNotasComentarios().length() > 140) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Las Notas/Comentarios no pueden superar los 140 caracteres, por favor revisar.", "Las Notas/Comentarios no pueden superar los 140 caracteres, por favor revisar."));
			}
		}
		if (!resultado) {
			inicializaIso();
		}
		return resultado;
	}

	private boolean esDiaHabilDivisa(Long idDivisa) {
		List<Date> diasInhabiles = diasInhabilesDivisasService.getDiasInhabilesByIdDivisa(idDivisa);
		Date fechaAlta = efectivoInternacionalVO.getFechaAlta();

		Calendar diaAlta = Calendar.getInstance();
		diaAlta.setTime(fechaAlta);

		for (Date diaDate : diasInhabiles) {
			Calendar dia = Calendar.getInstance();
			dia.setTime(diaDate);

			if (sonFechasIguales(dia, diaAlta)) {
				return false;
			}
		}
		return true;
	}

	private boolean sonFechasIguales(Calendar cal1, Calendar cal2) {
		return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
				cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
				cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
	}

	private boolean esHorarioCustodioValido(Long idCatbic) throws ParseException {
		Date fechaAlta = efectivoInternacionalVO.getFechaAlta();

		SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
		String horaAltaFormateada = formatoHora.format(fechaAlta);

		Date horaAlta = formatoHora.parse(horaAltaFormateada);


		Integer idCustodio = custodioService.getIdCustodioByIdCatbic(idCatbic);
		Map<String, String> rangoHorarios = horariosCustodiosService.getRangoHorariosPorCustodio(idCustodio);
		String strHorarioInicial = rangoHorarios.get("horarioInicial");
		String strHorarioFinal = rangoHorarios.get("horarioFinal");

		Date horarioInicial = formatoHora.parse(strHorarioInicial);
		Date horarioFinal = formatoHora.parse(strHorarioFinal);

		return horaAlta.after(horarioInicial) && horaAlta.before(horarioFinal);
	}

	private String procesarDatos() {
		final DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		final StringBuilder data = new StringBuilder();
		data.append("Folio Control:" + efectivoInternacionalVO.getFolioControl() + '\n');
		data.append("Participante:" + efectivoInternacionalVO.getParticipante() + '\n');
		data.append("Institucion:" + efectivoInternacionalVO.getNombreInstitucion() + '\n');
		data.append("Divisa:" + efectivoInternacionalVO.getDivisa().getClaveAlfabetica() + '\n');
		data.append("Boveda:" + efectivoInternacionalVO.getBoveda().getNombreCorto() + '\n');
		data.append("Fecha Liquidacion:" + df.format(efectivoInternacionalVO.getFechaLiquidacion()) + '\n');
		data.append("Saldo Disponible:" + BigDecimal.valueOf(efectivoInternacionalVO.getSaldoDisponible()) + '\n');
		data.append("Importe:" + BigDecimal.valueOf(efectivoInternacionalVO.getImporteTraspasar()) + '\n');
		data.append("Saldo Actual:" + BigDecimal.valueOf(efectivoInternacionalVO.getSaldoEfectivo()) + '\n');
		data.append("Referencia Relacionada:" + efectivoInternacionalVO.getReferenciaRelacionada() + '\n');
		if (RETIRO.equals(efectivoInternacionalVO.getTipoMovimiento())) {
			data.append("Referencia Numerica:" + efectivoInternacionalVO.getReferenciaNumerica() + '\n');
			data.append("Notas/Comentarios:" + efectivoInternacionalVO.getNotasComentarios() + '\n');
		}
		return data.toString();
	}

	public Long getIdInstitucion() {
		return idInstitucion;
	}

	public void setIdInstitucion(Long idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	public String getNombreInstitucion() {
		return nombreInstitucion;
	}

	public void setNombreInstitucion(String nombreInstitucion) {
		this.nombreInstitucion = nombreInstitucion;
	}

	public ConsultaCatalogoService getConsultaCatalogoService() {
		return consultaCatalogoService;
	}

	public void setConsultaCatalogoService(ConsultaCatalogoService consultaCatalogoService) {
		this.consultaCatalogoService = consultaCatalogoService;
	}

	public DivisionInternacionalService getDivisionInternacionalService() {
		return divisionInternacionalService;
	}

	public void setDivisionInternacionalService(DivisionInternacionalService divisionInternacionalService) {
		this.divisionInternacionalService = divisionInternacionalService;
	}

	public DateUtilService getDateUtilService() {
		return dateUtilService;
	}

	public void setDateUtilService(DateUtilService dateUtilService) {
		this.dateUtilService = dateUtilService;
	}

	public UtilService getUtilService() {
		return utilService;
	}

	public void setUtilService(UtilService utilService) {
		this.utilService = utilService;
	}

	public String getDescripcionBoveda() {
		return descripcionBoveda;
	}

	public void setDescripcionBoveda(String descripcionBoveda) {
		this.descripcionBoveda = descripcionBoveda;
	}

	public String getDescripcionDivisa() {
		return descripcionDivisa;
	}

	public void setDescripcionDivisa(String descripcionDivisa) {
		this.descripcionDivisa = descripcionDivisa;
	}

	public String getIdDivisa() {
		return idDivisa;
	}

	public void setIdDivisa(String idDivisa) {
		this.idDivisa = idDivisa;
	}

	public Long getDivisa() {
		return divisa;
	}

	public void setDivisa(Long divisa) {
		this.divisa = divisa;
	}

	public Long getBoveda() {
		return boveda;
	}

	public void setBoveda(Long boveda) {
		this.boveda = boveda;
	}

	public Integer getIdTipoMovimiento() {
		return idTipoMovimiento;
	}

	public void setIdTipoMovimiento(Integer idTipoMovimiento) {
		this.idTipoMovimiento = idTipoMovimiento;
	}

	public Long getSaldoDisponible() {
		return saldoDisponible;
	}

	public void setSaldoDisponible(Long saldoDisponible) {
		this.saldoDisponible = saldoDisponible;
	}

	public Long getImporte() {
		return importe;
	}

	public void setImporte(Long importe) {
		this.importe = importe;
	}

	public Long getSaldoActual() {
		return saldoActual;
	}

	public void setSaldoActual(Long saldoActual) {
		this.saldoActual = saldoActual;
	}

	public Integer getReferenciaNumerica() {
		return referenciaNumerica;
	}

	public void setReferenciaNumerica(Integer referenciaNumerica) {
		this.referenciaNumerica = referenciaNumerica;
	}

	public String getReferenciaRelacionada() {
		return referenciaRelacionada;
	}

	public void setReferenciaRelacionada(String referenciaRelacionada) {
		this.referenciaRelacionada = referenciaRelacionada;
	}

	public String getInformacionRemesas() {
		return informacionRemesas;
	}

	public void setInformacionRemesas(String informacionRemesas) {
		this.informacionRemesas = informacionRemesas;
	}

	public String getNotasComentarios() {
		return notasComentarios;
	}

	public void setNotasComentarios(String notasComentarios) {
		this.notasComentarios = notasComentarios;
	}


	public List<SelectItem> getDivisas() {
		return divisas;
	}

	public void setDivisas(List<SelectItem> divisas) {
		this.divisas = divisas;
	}

	public List<SelectItem> getBovedas() {
		return bovedas;
	}

	public void setBovedas(List<SelectItem> bovedas) {
		this.bovedas = bovedas;
	}

	public String getFolioInstitucion() {
		return folioInstitucion;
	}

	public void setFolioInstitucion(String folioInstitucion) {
		this.folioInstitucion = folioInstitucion;
	}

	public String getClaveInstitucion() {
		return claveInstitucion;
	}

	public void setClaveInstitucion(String claveInstitucion) {
		this.claveInstitucion = claveInstitucion;
	}

	public MovimientoEfectivoInternacionalVO getEfectivoInternacionalVO() {
		return efectivoInternacionalVO;
	}

	public void setEfectivoInternacionalVO(MovimientoEfectivoInternacionalVO efectivoInternacionalVO) {
		this.efectivoInternacionalVO = efectivoInternacionalVO;
	}

	public boolean isDisableImporte() {
		return disableImporte;
	}

	public void setDisableImporte(boolean disableImporte) {
		this.disableImporte = disableImporte;
	}

	public CatalogosUtil getCatalogosUtil() {
		return catalogosUtil;
	}

	public void setCatalogosUtil(CatalogosUtil catalogosUtil) {
		this.catalogosUtil = catalogosUtil;
	}

	public static String getTipoInstrTraspasoEfectvio() {
		return TIPO_INSTR_TRASPASO_EFECTVIO;
	}

	public static String getDeposito() {
		return DEPOSITO;
	}

	public static String getRetiro() {
		return RETIRO;
	}

	public void setCatalogosFacade(ConsultaCatalogosFacade catalogosFacade) {
		this.catalogosFacade = catalogosFacade;
	}

	public boolean isRenderReferencias() {
		return renderReferencias;
	}

	public boolean isRenderRemesasInfo() {
		return renderRemesasInfo;
	}

	public SicService getSicService() {
		return sicService;
	}

	public void setSicService(SicService sicService) {
		this.sicService = sicService;
	}

	public BitacoraService getBitacoraService() {
		return bitacoraService;
	}

	public void setBitacoraService(BitacoraService bitacoraService) {
		this.bitacoraService = bitacoraService;
	}

	public HorariosCustodiosService getHorariosCustodiosService() {
		return horariosCustodiosService;
	}

	public void setHorariosCustodiosService(HorariosCustodiosService horariosCustodiosService) {
		this.horariosCustodiosService = horariosCustodiosService;
	}

	public CustodioService getCustodioService() {
		return custodioService;
	}

	public void setCustodioService(CustodioService custodioService) {
		this.custodioService = custodioService;
	}

	public DiasInhabilesDivisasService getDiasInhabilesDivisasService() {
		return diasInhabilesDivisasService;
	}

	public void setDiasInhabilesDivisasService(DiasInhabilesDivisasService diasInhabilesDivisasService) {
		this.diasInhabilesDivisasService = diasInhabilesDivisasService;
	}
}

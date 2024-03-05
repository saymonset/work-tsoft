/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * EstatusOpEfectivoBean.java
 * 04/03/2008
 */
package com.indeval.portaldali.presentation.controller.bitacoraefectivo;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.seguridad.presentation.constants.SeguridadConstants;
import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.CuentaEfectivoDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.EstadoInstruccionDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.InstruccionEfectivoDTO;
import com.indeval.portaldali.middleware.dto.RetiroEfectivoDTO;
import com.indeval.portaldali.middleware.dto.RetiroEfectivoInternacionalDTO;
import com.indeval.portaldali.middleware.dto.TipoCuentaDTO;
import com.indeval.portaldali.middleware.dto.TipoInstitucionDTO;
import com.indeval.portaldali.middleware.dto.TipoInstruccionDTO;
import com.indeval.portaldali.middleware.dto.TipoLiquidacionDTO;
import com.indeval.portaldali.middleware.dto.TipoNaturalezaDTO;
import com.indeval.portaldali.middleware.dto.TipoTenenciaDTO;
import com.indeval.portaldali.middleware.dto.criterio.CriterioEstatusOpEfectivoDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.middleware.dto.util.ResultadoValidacionDTO;
import com.indeval.portaldali.middleware.services.common.PropiedadesDaliService;
import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;
import com.indeval.portaldali.middleware.services.common.constants.TipoCuentaConstants;
import com.indeval.portaldali.middleware.services.common.constants.TipoCustodiaConstants;
import com.indeval.portaldali.middleware.services.efectivo.RetiroEfectivoService;
import com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService;
import com.indeval.portaldali.middleware.services.estatus.ConsultaInstruccionesEfectivoService;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.tesoreria.util.Constantes;
import com.indeval.portaldali.middleware.services.util.UtilServices;
import com.indeval.portaldali.persistence.dao.common.BovedaDaliDAO;
import com.indeval.portaldali.persistence.dao.common.DivisaDaliDAO;
import com.indeval.portaldali.persistence.dao.common.EstadoInstruccionDaliDAO;
import com.indeval.portaldali.persistence.dao.common.TipoInstruccionDaliDAO;
import com.indeval.portaldali.persistence.dao.common.TipoLiquidacionDaliDAO;
import com.indeval.portaldali.persistence.model.EstadoInstruccion;
import com.indeval.portaldali.presentation.controller.common.ControllerBase;
import com.indeval.portaldali.presentation.helper.IsoHelper;
import com.indeval.portaldali.presentation.util.CifradorDescifradorBlowfish;
import com.indeval.portaldali.presentation.util.ConsultaCatalogosFacade;

/**
 * Backing bean para el servicio a la clase Estatus de las operaciones de
 * efectivo.
 * 
 * @author 
 * 
 * 
 */
public class EstatusOpEfectivoBean extends ControllerBase {
	
	/** Servicio para el envio de operaciones. */
	private EnvioOperacionesService envioOperacionService = null;

	private Boolean confirmacion;
	private String esTipo;

	/** La cadena que contiene el iso sin firmar */
	// protected String isoSinFirmar = "";

	/** Ayudante para la generación de las cadenas ISO que deberán ser firmadas */
	protected IsoHelper isoHelper = null;

	/**
	 * Conjunto de isos Firmados
	 */
	private List<String> isosFirmados = null;
	
	private List<String> referenciaOperaciones = null;
	
	private List<String> referenciaMensajes = null;
	/**
	 * Conjunto de isos sin firmar
	 */
	private List<String> isosSinFirmar = null;
	/**
	 * Conjunto de hash de isos
	 */
	private List<String> hashIso = null;

	/**
	 * Total de operaciones a firmar
	 */
	private int totalOperaciones = 0;
	/**
	 * Lista de los Items del Tipo de Abono
	 */
	private List<SelectItem> listaTipoAbono = new ArrayList<SelectItem>();
	/**
	 * Mapa con los tipo de Abono
	 */
	private Map<String, String> mapaTipoAbono = new HashMap<String, String>();

	/**
	 * Cadena Hash del ISO a firmar
	 */
	// protected String hashIso = null;

	protected CifradorDescifradorBlowfish cdb = new CifradorDescifradorBlowfish();

	/** Servicio para asignar folio apartir de una secuencia */
	private UtilServices utilService;

	private Logger log = LoggerFactory.getLogger(EstatusOpEfectivoBean.class);

	/**
	 * Criterio de búsqueda
	 */
	private CriterioEstatusOpEfectivoDTO criterio = new CriterioEstatusOpEfectivoDTO();

	/**
	 * Acceso a los elementos de los catálogos
	 */
	private ConsultaCatalogosFacade consultaCatalogos = null;

	/** Consulta ejecutada */
	private boolean consultaEjecutada = false;

	/** Lista con los resultados de la consulta */
	private List<InstruccionEfectivoDTO> resultados = null;
	/** Lista con los resultados de la consulta */
	private List<InstruccionEfectivoDTO> operacionesEfectivo = null;
	
	/** Lista con los resultados de la consulta para interacnaion */
	private List<RetiroEfectivoInternacionalDTO> resultadosIntl = null;

	/**
	 * Total del la consulta
	 */
	private BigDecimal totalImporte = BigDecimal.ZERO;
	/** Total de registros encontrados en la consulta */
	private int totalRegistros = 0;

	/** Servicio de negocio */
	private PropiedadesDaliService propiedadesDaliService;
	private ConsultaInstruccionesEfectivoService consultaInstruccionesEfectivoService = null;
	private RetiroEfectivoService retiroEfectivoService;
	
	private TipoInstruccionDaliDAO tipoInstruccionDaliDAO;
	private TipoLiquidacionDaliDAO tipoLiquidacionDaliDAO;
	private EstadoInstruccionDaliDAO estadoInstruccionDaliDAO;
	private DivisaDaliDAO divisaDaliDAO;
	private BovedaDaliDAO bovedaDaliDAO;

	private List<SelectItem> estadosInstruccionItems;
	private List<SelectItem> divisasItems;
	private List<SelectItem> bovedasItems;
	private List<SelectItem> estadosTipoAbonoItems;

	private List<SelectItem> tiposInstruccionesItems;

	private String idFolioParticipante = null;

	private String idFolioContraparte = null;

	private String tipoInstruccion = null;
	private String tipoLiquidacion = null;
	private String liquidacionVencimiento = null;

	/**
	 * Indica si se debe dejar bitacora o no
	 */
	private boolean debeDejarBitacora;

	/** booleano para controlar si se muestra el filtro de Tipo de Retiro */
	private boolean muestraTipoRetiro = false;

	private boolean checkAllAutoriza;
	private boolean checkAllLibera;
	private boolean validationErrors;
	private Long idInstruccion;
	private boolean checkAllAutorizaEnabled;
	private boolean checkAllLiberaEnabled;
	
	private int limMaxAutorizaLibera;
	private String idsAutorizar;
	private String idsLiberar;
	
	public String getInit() {
		limMaxAutorizaLibera = propiedadesDaliService.obtenerLimMaxAutorizaLibera();
		paginacion = new EstadoPaginacionDTO();
		paginacion.setRegistrosPorPagina(20);
		criterio.setFechaLiquidacion(new Date());

		CuentaDTO cuenta = new CuentaDTO();
		cuenta.setIdCuenta(getInstitucionActual().getId());
		cuenta.setCuenta(getInstitucionActual().getFolioInstitucion());
		criterio.setCuentaParticipante(cuenta);
		// ----Inicializacióm del estado instruccion
		EstadoInstruccionDTO estadoInstruccionDTO = new EstadoInstruccionDTO();
		estadoInstruccionDTO.setIdEstadoInstruccion(-1);
		estadoInstruccionDTO.setDescripcion("TODOS");
		estadoInstruccionDTO.setClaveEstadoInstruccion("TD");
		criterio.setEstadoInstruccion(estadoInstruccionDTO);
		// ---------------------------------------------
		DivisaDTO divisaDTO = new DivisaDTO();
		divisaDTO.setId(-1);
		divisaDTO.setDescripcion("TODAS");
		divisaDTO.setClaveAlfabetica("TODAS");
		divisaDTO.setClaveNumerica("TODAS");
		criterio.setDivisa(divisaDTO);
		// -------------------------------------------
		BovedaDTO bovedaDTO = new BovedaDTO();
		bovedaDTO.setNombreCorto("TODOS");
		bovedaDTO.setDescripcion("TODOS");
		bovedaDTO.setId(Long.valueOf(-1));
		criterio.setBoveda(bovedaDTO);
		// -------------------------------------------

		// ---------------------------------------------
		if (criterio.getTipoInstruccion() == null) {
			criterio.setTipoInstruccion(new TipoInstruccionDTO());
		}

		if (criterio.getTipoLiquidacion() == null) {
			criterio.setTipoLiquidacion(new TipoLiquidacionDTO());
		}
		if (criterio.getLiquidacionVencimiento() == null) {
			criterio.setLiquidacionVencimiento(new TipoLiquidacionDTO());
		}

		if (criterio.getCuentaContraparte() == null) {
			criterio.setCuentaContraparte(new CuentaDTO());
		}
		if (criterio.getCuentaParticipante() == null) {
			criterio.setCuentaParticipante(new CuentaDTO());
		}
		if (criterio.getEstadoInstruccion() == null) {
			criterio.setEstadoInstruccion(new EstadoInstruccionDTO());
		}
		if (criterio.getDivisa() == null) {
			criterio.setDivisa(new DivisaDTO());
		}
		if (criterio.getBoveda() == null) {
			criterio.setBoveda(new BovedaDTO());
		}
		
		esTipo ="";
		
		
		referenciaOperaciones = new ArrayList<String>();

		
		
		if (estadosInstruccionItems == null
				|| estadosInstruccionItems.isEmpty()) {
			List<EstadoInstruccionDTO> listaResultados = estadoInstruccionDaliDAO
					.consultarEstadosInstruccionPorIds();

			estadosInstruccionItems = new ArrayList<SelectItem>();
			if (listaResultados != null) {
				Iterator<EstadoInstruccionDTO> iterator = listaResultados
						.iterator();
				while (iterator.hasNext()) {
					EstadoInstruccionDTO obj = iterator.next();
					SelectItem item = new SelectItem(obj
							.getIdEstadoInstruccion(), obj.getDescripcion()
							.toUpperCase());
					estadosInstruccionItems.add(item);
				}
			}
		}
		// Divisas
		if (divisasItems == null || divisasItems.isEmpty()) {
			List<DivisaDTO> listaResultados = divisaDaliDAO.buscarDivisas(null);
			divisasItems = new ArrayList<SelectItem>();
			if (listaResultados != null) {
				Iterator<DivisaDTO> iterator = listaResultados.iterator();
				while (iterator.hasNext()) {
					DivisaDTO obj = iterator.next();
					if (obj != null) {
						SelectItem item = new SelectItem(obj.getId(), obj
								.getDescripcion().toUpperCase());
						divisasItems.add(item);
					}

				}
			}
		}

		// Boveda
		if (bovedasItems == null || bovedasItems.isEmpty()) {
			final BovedaDTO boveda = new BovedaDTO();
			boveda.setClaveTipoBoveda(TipoCustodiaConstants.EFECTIVO);
			List<BovedaDTO> listaResultados = bovedaDaliDAO.buscarBovedasPorTipoCustodia(boveda, null);
			bovedasItems = new ArrayList<SelectItem>();
			if (listaResultados != null) {
				Iterator<BovedaDTO> iterator = listaResultados.iterator();
				while (iterator.hasNext()) {
					BovedaDTO obj = iterator.next();
					SelectItem item = new SelectItem(obj.getId(), obj
							.getDescripcion().toUpperCase());
					bovedasItems.add(item);
				}
			}
		}

		if (estadosTipoAbonoItems == null || estadosTipoAbonoItems.isEmpty()) {
			List<TipoLiquidacionDTO> listaResultados = tipoLiquidacionDaliDAO
					.buscarTipoDeLiquidacionPorIds();
			estadosTipoAbonoItems = new ArrayList<SelectItem>();
			if (listaResultados != null) {
				Iterator<TipoLiquidacionDTO> iterator = listaResultados
						.iterator();
				while (iterator.hasNext()) {
					TipoLiquidacionDTO obj = iterator.next();
					SelectItem item = new SelectItem(obj.getIdTipoLiq(), obj
							.getNombreCorto().toUpperCase());
					estadosTipoAbonoItems.add(item);
				}
			}
		}

		if (!isUsuarioIndeval()) {
			List<CuentaDTO> listaCuentas = buscarCuentasParticipantePorPrefijo("*");
			if (listaCuentas != null && listaCuentas.size() == 1) {
				criterio.setCuentaParticipante(listaCuentas.get(0));
			}
		}

		idFolioParticipante = getInstitucionActual().getClaveTipoInstitucion()
				+ getInstitucionActual().getFolioInstitucion();

		listaTipoAbono = new ArrayList<SelectItem>();
		listaTipoAbono.add(new SelectItem("-1", "TODOS"));
		listaTipoAbono.add(new SelectItem("1", "REGISTRO"));
		listaTipoAbono.add(new SelectItem("2", "DALI"));
		listaTipoAbono.add(new SelectItem("3", "SPEI"));

		mapaTipoAbono = new HashMap<String, String>();
		for (SelectItem item : listaTipoAbono) {
			mapaTipoAbono.put((String) item.getValue(), item.getLabel());
		}

		Map<String, Object> mapBCOM = new HashMap<String, Object>(0);
		mapBCOM.put("tipoMensaje", consultaCatalogos.buscarTipoMensajePorId(5));
		mapBCOM.put("tipoInstruccion", consultaCatalogos.buscarTipoInstruccionPorClave("RETE"));
		criterio.setMapBCOM(mapBCOM);
		
		
		this.checkAllAutoriza = false;
		this.checkAllLibera = false;
		this.idInstruccion = null;
		
		this.checkAllAutorizaEnabled = false;
		this.checkAllLiberaEnabled = false;
		
		this.idsAutorizar = "";
		this.idsLiberar = "";
		
		inicializaIso();
		
		return "";
	}

	
	

	/**
	 * Envia la operacion a bitacora
	 * 
	 * @author FERNANDO VAZQUEZ ULLOA -
	 */
	private void enviarOperacionABitacora(RetiroEfectivoDTO objRete,  String isoFirmado) {
		
		String tipoOperacion = objRete.getTipoOperacion();
		
		HashMap<String, String> datosAdicionales = new HashMap<String, String>();
		datosAdicionales.put(SeguridadConstants.USR_CREDENCIAL, 
				(String)FacesContext.getCurrentInstance().getExternalContext().
				getSessionMap().get(SeguridadConstants.TICKET_SESION));
		datosAdicionales.put("ordenante", objRete.getInstitucion().getNombreCorto());
		datosAdicionales.put("beneficiario", objRete.getIdInstReceptor().getNombreCorto());
        
        if( !"TREF".equals(objRete.getTipoOperacion()) ){ // TRANSFERENCIA
        	
        	if("CCS".equals(tipoOperacion)) {
        		datosAdicionales.put("bovedaTraspasante", objRete.getBoveda().getNombreCorto());
    			datosAdicionales.put("divisaTraspasante", objRete.getDivisa().getClaveNumerica());
    			datosAdicionales.put("conceptoPago", objRete.getConceptoPago());
    			datosAdicionales.put("referenciaNumerica",objRete.getReferencia());
    			datosAdicionales.put("tipoBCom", "NAL");
        	}else { // SPEI SIAC
        		datosAdicionales.put("tipoRetiro", objRete.getConceptoPago());
            	datosAdicionales.put("referenciaNumericaSPEI", objRete.getReferencia());
        	}
        }
		envioOperacionService.grabaOperacion(objRete, datosAdicionales, null, isoFirmado);
		
		if (!existeErrorEnInvocacion()) {		
			limpiarCampos(null);
		}

	}
	
	/**
	 * Limpiar los campos de la página respetando las opciones de la operacion.
	 */
	public void limpiarCampos(ActionEvent e) {

		hashIso = null;
		getInit();
	}

	public void limpiar(ActionEvent actionEvent) {
		criterio = new CriterioEstatusOpEfectivoDTO();

		// ----Inicializacióm del estado instruccion
		EstadoInstruccionDTO estadoInstruccionDTO = new EstadoInstruccionDTO();
		estadoInstruccionDTO.setIdEstadoInstruccion(-1);
		estadoInstruccionDTO.setDescripcion("TODOS");
		estadoInstruccionDTO.setClaveEstadoInstruccion("TD");
		criterio.setEstadoInstruccion(estadoInstruccionDTO);
		// -----------------
		DivisaDTO divisaDTO = new DivisaDTO();
		divisaDTO.setId(-1);
		divisaDTO.setDescripcion("TODAS");
		divisaDTO.setClaveAlfabetica("TODAS");
		divisaDTO.setClaveNumerica("TODAS");
		criterio.setDivisa(divisaDTO);
		// ---------------
		BovedaDTO bovedaDTO = new BovedaDTO();
		bovedaDTO.setId(-1);
		bovedaDTO.setDescripcion("TODOS");
		bovedaDTO.setNombreCorto("TODOS");
		criterio.setBoveda(bovedaDTO);
		// ----------------------
		criterio.setTipoInstruccion(new TipoInstruccionDTO());
		criterio.setTipoLiquidacion(new TipoLiquidacionDTO());
		criterio.setLiquidacionVencimiento(new TipoLiquidacionDTO());

		criterio.setCuentaContraparte(new CuentaDTO());
		criterio.setCuentaParticipante(new CuentaDTO());
		// criterio.setEstadoInstruccion(new EstadoInstruccionDTO());
		idFolioParticipante = getInstitucionActual().getClaveTipoInstitucion()
				+ getInstitucionActual().getFolioInstitucion();
		idFolioContraparte = StringUtils.EMPTY;
		paginacion = new EstadoPaginacionDTO();
		paginacion.setRegistrosPorPagina(20);
		// TODO: consultar servicio
		criterio.setFechaLiquidacion(new Date());
		criterio.setMonto(null);
		consultaEjecutada = false;
		tipoInstruccion = StringUtils.EMPTY;
		tipoLiquidacion = StringUtils.EMPTY;
		liquidacionVencimiento = StringUtils.EMPTY;
		muestraTipoRetiro = false;
		
		this.validationErrors = false;
		this.checkAllAutoriza = false;
		this.checkAllLibera = false;
		
		this.esTipo = null;
		this.idInstruccion = null;
		
		this.checkAllAutorizaEnabled = false;
		this.checkAllLiberaEnabled = false;
		
		this.idsAutorizar = "";
		this.idsLiberar = "";
		
		inicializaIso();
	}

	/**
	 * método que atiende las solicitudes de autocomplete del catálogo de tipos
	 * de instrucción.
	 * 
	 * @param prefijo
	 *            Criterio de búsqueda
	 * @return Lista con los tipos de instrucciones encontradas
	 */
	public List<TipoInstruccionDTO> buscarTipoInstruccionPorPrefijo(
			Object prefijo) {
		log.info("Entro a buscar tipo Ins: [" + prefijo.toString() + "]");
		// return
		// consultaCatalogos.getConsultaTipoInstruccionService().buscarTiposDeInstruccionPorPrefijo(prefijo.toString());
		return tipoInstruccionDaliDAO
				.buscarTiposDeInstruccionPorIdsEfectivo(prefijo.toString());
	}

	/**
	 * método que atiende las solicitudes de autocomplete del catálogo de tipos
	 * de instrucción.
	 * 
	 * @param prefijo
	 *            Criterio de búsqueda
	 * @return Lista con los tipos de instrucciones encontradas
	 */
	public List<TipoLiquidacionDTO> buscarTipoLiquidacionPorPrefijo(
			Object prefijo) {
		log.info("Entro a buscar tipo Ins: [" + prefijo.toString() + "]");
		// return
		// consultaCatalogos.getConsultaTipoInstruccionService().buscarTiposDeInstruccionPorPrefijo(prefijo.toString());
		return tipoLiquidacionDaliDAO
				.buscarTiposDeLiquidacionPorIdsEfectivo(prefijo.toString());
	}

	/**
	 * método que atiende las solicitudes de autocomplete del catálogo de cuenta
	 * propia.
	 * 
	 * @param prefijo
	 *            Criterio de búsqueda
	 * @return Lista con las cuentas encontradas
	 */
	public List<CuentaDTO> buscarCuentasParticipantePorPrefijo(Object prefijo) {
		InstitucionDTO institucion = getInstitucionActual();
		List<CuentaDTO> resultado = null;
		List<TipoInstitucionDTO> tipoInst = new ArrayList<TipoInstitucionDTO>();
		List<CuentaDTO> listaCuentas = new ArrayList<CuentaDTO>();
		CuentaDTO criterio = new CuentaDTO();
		if (!isUsuarioIndeval()) {
			criterio.setInstitucion(institucion);
		}
		criterio.setTipoTenencia(new TipoTenenciaDTO());

		criterio.getTipoTenencia().setTipoCuenta(new TipoCuentaDTO());
		criterio.getTipoTenencia().getTipoCuenta().setId(
				TipoCuentaDTO.CUENTA_NOMBRADA);
		criterio.getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO());
		criterio.getTipoTenencia().getTipoNaturaleza().setId(
				String.valueOf(DaliConstants.VALOR_COMBO_TODOS));
		criterio.getTipoTenencia().setTipoCustodia(
				TipoCustodiaConstants.EFECTIVO);
		String prefijoAjustado = "";
		if (prefijo != null) {
			prefijoAjustado = String.valueOf(prefijo).replace('*', '%');
		}
		if (isUsuarioIndeval()) {
			criterio.setNumeroCuenta(prefijoAjustado);

		} else {
			criterio.setNumeroCuenta(institucion.getClaveTipoInstitucion()
					+ institucion.getFolioInstitucion() + prefijoAjustado);
		}
		tipoInst = consultaCatalogos.getConsultaTipoInstitucionService()
				.consultaTipoInstitucionPorPrefijo(prefijoAjustado);
		resultado = consultaCatalogos.getConsultaCuentaService()
				.buscarCuentasPorFragmentoNumeroCuenta(criterio);
		if (!tipoInst.isEmpty()) {
			CuentaDTO cuen = new CuentaDTO();
			Long desc = Long.valueOf(tipoInst.get(0).getId());
			if (desc < 10) {
				cuen.getInstitucion().setClaveTipoInstitucion(
						"0" + desc.toString());
			} else {
				cuen.getInstitucion().setClaveTipoInstitucion(desc.toString());
			}
			cuen.getInstitucion().setNombreCorto(
					tipoInst.get(0).getDescripcion());

			listaCuentas.add(cuen);
		}
		for (CuentaDTO cuentaDto : resultado) {
			listaCuentas.add(cuentaDto);
		}
		return listaCuentas;
	}

	/**
	 * Genera las consultas para llenar los reportes de la pantalla
	 * 
	 * @param e
	 */
	public void generarReportes(ActionEvent actionEvent) {
		reiniciarEstadoPeticion();
		crearCriterio();
		resultados = consultaInstruccionesEfectivoService
				.consultarInstruccionesEfectivo(criterio, null);
		totalRegistros = resultados.size();
		if(resultados != null )
		{
			System.out.println("EstatusOpEfectivoBean resultados : " + resultados.size());
		}
		obtenerTotales();
	}

	/**
	 * Genera las consultas para llenar los reportes de la pantalla
	 * 
	 * @param e
	 */
	public void generarReportesIntl(ActionEvent actionEvent) {
		crearCriterio();
		resultadosIntl = consultaInstruccionesEfectivoService.getInstEfecIntl(
				criterio, null);
		totalRegistros = resultadosIntl.size();
		obtenerTotalesIntl();
	}

	/**
	 * método que atiende las solicitudes de autocomplete del catálogo de
	 * cuentas de contraparte.
	 * 
	 * @param prefijo
	 *            Criterio de búsqueda
	 * @return Lista con las cuentas encontradas
	 */
	public List<CuentaDTO> buscarCuentasContrapartePorPrefijo(Object prefijo) {
		InstitucionDTO institucion = new InstitucionDTO();
		List<CuentaDTO> resultado = null;
		List<TipoInstitucionDTO> tipoInst = new ArrayList<TipoInstitucionDTO>();
		List<CuentaDTO> listaCuentas = new ArrayList<CuentaDTO>();
		institucion.setId(DaliConstants.VALOR_COMBO_TODOS);
		CuentaDTO criterio = new CuentaDTO();
		criterio.setInstitucion(institucion);
		criterio.setTipoTenencia(new TipoTenenciaDTO());

		criterio.getTipoTenencia().setTipoCuenta(new TipoCuentaDTO());
		criterio.getTipoTenencia().getTipoCuenta().setId(
				TipoCuentaDTO.CUENTA_NOMBRADA);
		criterio.getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO());
		criterio.getTipoTenencia().getTipoNaturaleza().setId(
				String.valueOf(DaliConstants.VALOR_COMBO_TODOS));
		criterio.getTipoTenencia().setTipoCustodia(
				TipoCustodiaConstants.EFECTIVO);
		String prefijoAjustado = "";
		if (prefijo != null) {
			prefijoAjustado = String.valueOf(prefijo).replace('*', '%');
		}
		criterio.setNumeroCuenta(prefijoAjustado);
		tipoInst = consultaCatalogos.getConsultaTipoInstitucionService()
				.consultaTipoInstitucionPorPrefijo(prefijoAjustado);
		resultado = consultaCatalogos.getConsultaCuentaService()
				.buscarCuentasPorFragmentoNumeroCuenta(criterio);
		if (!tipoInst.isEmpty()) {
			CuentaDTO cuen = new CuentaDTO();
			Long desc = Long.valueOf(tipoInst.get(0).getId());
			if (desc < 10) {
				cuen.getInstitucion().setClaveTipoInstitucion(
						"0" + desc.toString());
			} else {
				cuen.getInstitucion().setClaveTipoInstitucion(desc.toString());
			}
			cuen.getInstitucion().setNombreCorto(
					tipoInst.get(0).getDescripcion());

			listaCuentas.add(cuen);
		}
		for (CuentaDTO cuentaDto : resultado) {
			listaCuentas.add(cuentaDto);
		}
		return listaCuentas;
	}

	/**
	 * Completa el objeto de criterios con los valores capturados por el usuario
	 */
	private ResultadoValidacionDTO crearCriterio() {

		ResultadoValidacionDTO resultado = new ResultadoValidacionDTO();
		resultado.setValido(true);

		CuentaEfectivoDTO ctaEfec = new CuentaEfectivoDTO();
		CuentaEfectivoDTO resEfec = null;
		List<CuentaEfectivoDTO> lstEfec = new ArrayList<CuentaEfectivoDTO>();

		CuentaEfectivoDTO ctaEfecR = new CuentaEfectivoDTO();
		CuentaEfectivoDTO resEfecR = null;
		List<CuentaEfectivoDTO> lstEfecR = new ArrayList<CuentaEfectivoDTO>();

		InstitucionDTO inst = null;
		InstitucionDTO instR = null;

		ctaEfec.setTipoCustodia(TipoCustodiaConstants.EFECTIVO);
		ctaEfec.setTipoCuenta(new TipoCuentaDTO(
				TipoCuentaConstants.TIPO_NOMBRADA, ""));
		ctaEfec.setTipoNaturaleza(new TipoNaturalezaDTO(
				TipoNaturalezaDTO.PASIVO, ""));

		ctaEfecR.setTipoCustodia(TipoCustodiaConstants.EFECTIVO);
		ctaEfecR.setTipoCuenta(new TipoCuentaDTO(
				TipoCuentaConstants.TIPO_NOMBRADA, ""));
		ctaEfecR.setTipoNaturaleza(new TipoNaturalezaDTO(
				TipoNaturalezaDTO.PASIVO, ""));

		if (criterio.getFechaLiquidacion() == null) {
			resultado.setValido(false);
			resultado.setMensaje("La fecha de liquidaci\u00F3n no debe ir vac\u00EDa");
		}

		if (!StringUtils.isEmpty(idFolioParticipante)) {
			inst = consultaCatalogos
					.buscarInstitucionPorIdFolio(idFolioParticipante);
			if (inst == null) {
				resultado.setValido(false);
				resultado
						.setMensaje("La instituci\u00F3n especificada para el participante no existe.");
			}
		}
		else
		{
			inst = null;//TODAS
		}
		if (inst == null) {
			CuentaDTO dto = new CuentaDTO();
			dto.setIdCuenta(DaliConstants.VALOR_COMBO_TODOS);
			dto.setDescripcion("");
			dto.setCuenta("");
			dto.setNumeroCuenta("");
			criterio.setCuentaParticipante(dto);
		} else {
			ctaEfec.setInstitucion(inst);
			ctaEfec.setNumeroCuenta(String
					.valueOf(DaliConstants.VALOR_COMBO_TODOS));
			lstEfec = consultaCatalogos.getConsultaCuentaService()
					.buscarCuentasNombradasEfectivo(ctaEfec, null);
			if (lstEfec == null || lstEfec.isEmpty()) {
				resultado.setValido(false);
				resultado
						.setMensaje("La instituci\u00F3n capturada para el participante no tiene cuentas de efectivo");

			} else {
				resEfec = lstEfec.get(0);
			}

		}

		if (resEfec != null && !StringUtils.isEmpty(resEfec.getNumeroCuenta())) {
			criterio.getCuentaParticipante().setNumeroCuenta(
					resEfec.getNumeroCuenta());
			criterio.getCuentaParticipante().setIdCuenta(resEfec.getIdCuenta());
			criterio.getCuentaParticipante().setInstitucion(inst);
		}

		if (!StringUtils.isEmpty(idFolioContraparte)) {
			instR = consultaCatalogos
					.buscarInstitucionPorIdFolio(idFolioContraparte);
			if (instR == null) {
				resultado.setValido(false);
				resultado
						.setMensaje("La instituci\u00F3n especificada para la contraparte no existe.");
			}
		}
		else
		{
			instR = null;//TODAS
		}

		if (instR == null) {
			CuentaDTO dto = new CuentaDTO();
			dto.setIdCuenta(DaliConstants.VALOR_COMBO_TODOS);
			dto.setDescripcion("");
			dto.setCuenta("");
			dto.setNumeroCuenta("");
			criterio.setCuentaContraparte(dto);
		} else {
			ctaEfecR.setInstitucion(instR);
			ctaEfecR.setNumeroCuenta(String
					.valueOf(DaliConstants.VALOR_COMBO_TODOS));
			lstEfecR = consultaCatalogos.getConsultaCuentaService()
					.buscarCuentasNombradasEfectivo(ctaEfecR, null);
			if (lstEfecR == null || lstEfecR.isEmpty()) {
				resultado.setValido(false);
				resultado
						.setMensaje("La instituci\u00F3n capturada para la contraparte no tiene cuentas de efectivo");
			} else {
				resEfecR = lstEfecR.get(0);
			}
		}

		if (resEfecR != null) {
			criterio.getCuentaContraparte().setNumeroCuenta(
					resEfecR.getNumeroCuenta());
			criterio.getCuentaContraparte().setIdCuenta(resEfecR.getIdCuenta());
			criterio.getCuentaContraparte().setInstitucion(instR);
		}
		criterio.setEstadoInstruccion(consultaCatalogos
				.buscarEstadoInstruccionPorId(criterio.getEstadoInstruccion()
						.getIdEstadoInstruccion()));

		criterio.setDivisa(consultaCatalogos.buscarDivisaPorId(criterio
				.getDivisa().getId()));
		criterio.setBoveda(consultaCatalogos.buscarBovedaPorId(criterio
				.getBoveda().getId()));

		if (StringUtils.isNotBlank(tipoInstruccion)) {
			criterio.setTipoInstruccion(consultaCatalogos
					.buscarTipoInstruccionPorClave(tipoInstruccion));
			if (criterio.getTipoInstruccion().getIdTipoInstruccion() == DaliConstants.VALOR_COMBO_TODOS) {
				resultado.setValido(false);
				resultado
						.setMensaje("El tipo de instrucci\u00F3n capturado no existe.");
			}
		} else {
			criterio.setTipoInstruccion(consultaCatalogos
					.buscarTipoInstruccionPorClave(tipoInstruccion));
		}

		if (StringUtils.isNotBlank(tipoLiquidacion)) {
			criterio.setTipoLiquidacion(consultaCatalogos
					.buscarTipoLiquidacionPorClave(tipoLiquidacion));
			if (criterio.getTipoLiquidacion().getIdTipoLiq().intValue() == DaliConstants.VALOR_COMBO_TODOS) {
				criterio.setTipoLiquidacion(null);
				// resultado.setValido(false);
				// resultado.setMensaje("El tipo de liquidaci\u00F3n capturado no existe.");
			}
		} else {
			criterio.setTipoLiquidacion(new TipoLiquidacionDTO());
		}

		if (StringUtils.isNotBlank(liquidacionVencimiento)) {
			criterio.setLiquidacionVencimiento(consultaCatalogos
					.buscarTipoLiquidacionPorClave(liquidacionVencimiento));
			if (criterio.getLiquidacionVencimiento().getIdTipoLiq().intValue() == DaliConstants.VALOR_COMBO_TODOS) {
				criterio.setLiquidacionVencimiento(null);
				// resultado.setValido(false);
				// resultado.setMensaje("El tipo de liquidaci\u00F3n al vencimiento capturado no existe.");
			}
		} else {
			criterio.setLiquidacionVencimiento(new TipoLiquidacionDTO());
		}

		if (isUsuarioIndeval()) {
			criterio.setInstitucionParticipante(inst);
			criterio.setInstitucionContraparte(instR);
		} else {
			criterio.setInstitucionParticipante(getInstitucionActual());
			criterio.setInstitucionContraparte(instR);
		}
		
		if(StringUtils.isNotBlank(criterio.getReferenciaPaquete()))
		{
			criterio.setReferenciaPaquete(criterio.getReferenciaPaquete().toUpperCase());
		}
		
		String monto = StringUtils.trimToNull(criterio.getMonto());
		criterio.setMonto(monto);
		
		if(StringUtils.isNotBlank(monto)){
			try {
				criterio.setMontoBd( new BigDecimal(monto) );
			}catch(NumberFormatException e) {
				criterio.setMonto(null);
			}
		}
		
		return resultado;

	}

	public long getIdInstitucionActual() {
		return getInstitucionActual().getId();
	}
	/**
	 * Obtiene el valor del atributo tipoInstruccion
	 * 
	 * @return el valor del atributo tipoInstruccion
	 */
	public String getTipoInstruccion() {
		return tipoInstruccion;
	}

	/**
	 * Establece el valor del atributo tipoInstruccion
	 * 
	 * @param tipoInstruccion
	 *            el valor del atributo tipoInstruccion a establecer
	 */
	public void setTipoInstruccion(String tipoInstruccion) {
		this.tipoInstruccion = tipoInstruccion;
	}

	public String getTipoLiquidacion() {
		return tipoLiquidacion;
	}

	public void setTipoLiquidacion(String tipoLiquidacion) {
		this.tipoLiquidacion = tipoLiquidacion;
	}

	public String getLiquidacionVencimiento() {
		return liquidacionVencimiento;
	}

	public void setLiquidacionVencimiento(String liquidacionVencimiento) {
		this.liquidacionVencimiento = liquidacionVencimiento;
	}

	/**
	 * Invoca las funciones de navegación de página de la consulta principal de
	 * la pantalla
	 * 
	 * @param e
	 *            Evento generado durante la solicitud
	 */
	public void navegarPorResultados(ActionEvent actionEvent) {
		String navegacion = (String) actionEvent.getComponent().getAttributes().get("navegacion");
		
		try {
			if(navegacion != null)
				EstadoPaginacionDTO.class.getMethod(navegacion).invoke(paginacion);
		} catch (Exception ex) {
			log.error("Error de invocación de método al navega por los resultados principales", ex);
		}
		consultaEjecutada = true;
			
		paginar();
		
	}

	public void cambiarTamanioPagina(ActionEvent actionEvent) {
		paginacion.setNumeroPagina(1);
		
		navegarPorResultados(actionEvent);
	}
	
	private void paginar() {
		consultaEjecutada = true;
		
		int size = operacionesEfectivo.size();
		
		if (paginacion != null) {
            paginacion.setTotalResultados(size);
            if (paginacion.getTotalResultados() > 0) {
                paginacion.setTotalPaginas((int) Math.ceil((double) paginacion.getTotalResultados() / (double) paginacion.getRegistrosPorPagina()));
                if (paginacion.getNumeroPagina() < 1) {
                    paginacion.setNumeroPagina(1);
                }
            }else {
                paginacion.setNumeroPagina(0);
                paginacion.setTotalPaginas(0);
                
                resultados = new ArrayList<>();
            }
            
            int pagina = paginacion.getNumeroPagina();
            int registrosPorPagina = paginacion.getRegistrosPorPagina();

            if (pagina > 0 && registrosPorPagina > 0) {
                int primerRes = (paginacion.getNumeroPagina() - 1) * paginacion.getRegistrosPorPagina();
                int ultimoRes = (paginacion.getNumeroPagina() - 1) * paginacion.getRegistrosPorPagina() + paginacion.getRegistrosPorPagina();
                if (ultimoRes > (size - 1)) {
                    ultimoRes = size;
                }

                resultados = operacionesEfectivo.subList(primerRes, ultimoRes);
            }
        }
	}
	
	private void buscar() {
		log.trace("buscar");
		
		long totalRegistros = consultaInstruccionesEfectivoService.obtenerProyeccionConsultaInstruccionesEfectivo(criterio,debeDejarBitacora);
		incializarEstadoPaginacion(totalRegistros);
		debeDejarBitacora = false;
		
		operacionesEfectivo = consultaInstruccionesEfectivoService.consultarInstruccionesEfectivo(criterio, paginacion);
		
		habilitaChecks();
		
		obtenerTotales();
		
		paginar();
	}
	
	private void habilitaChecks() {
		boolean checkAllAutorizaEnabled = false;
		boolean checkAllLiberaEnabled = false;
		
		this.idsAutorizar = "";
		this.idsLiberar = "";
		
		String idFolio = getInstitucionActual().getIdFolio();
		
		if(this.operacionesEfectivo != null && !this.operacionesEfectivo.isEmpty()) {
			
			for(InstruccionEfectivoDTO dto: this.operacionesEfectivo) {
				int idEstadoInstruccion = dto.getEstadoInstruccion().getIdEstadoInstruccion(); 
				
				if( idFolio.equals(dto.getInstitucionTraspasante().getIdFolio()) ||
						(isUsuarioIndeval() &&  dto.getTipoInstruccion().getIdTipoInstruccion() == Constantes.ID_TIPO_INSTRUCCION__TREF)) {
					if( idEstadoInstruccion== EstadoInstruccion.REGISTRADA_VALUE) {
						checkAllAutorizaEnabled = true;
					}
					
					if( idEstadoInstruccion== EstadoInstruccion.AUTORIZADA_VALUE) {
						checkAllLiberaEnabled = true;
					}
				}
			}
		}
		
		this.checkAllAutorizaEnabled = checkAllAutorizaEnabled;
		this.checkAllLiberaEnabled = checkAllLiberaEnabled;
	}
	
	public void checkOperAutoriza(ActionEvent event) {
		validationErrors = false;
		
		int count = 0;
		if(this.operacionesEfectivo != null && !this.operacionesEfectivo.isEmpty()) {
			
			for(InstruccionEfectivoDTO dto: this.operacionesEfectivo) {
				if( dto.isSeleccionadoAutoriza()) {
					count++;
				}
			}
			
			log.debug("count: " + count);
			
			if(count > limMaxAutorizaLibera) {
				String mensaje = String.format("Solo puede seleccionar hasta %d registros.", limMaxAutorizaLibera);
				validationErrors = true;
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, mensaje));
			}
		}
	}
	
	public void checkOperLibera(ActionEvent event) {
		
		int count = 0;
		if(this.operacionesEfectivo != null && !this.operacionesEfectivo.isEmpty()) {
			
			for(InstruccionEfectivoDTO dto: this.operacionesEfectivo) {
				if( dto.isSeleccionadoLibera()) {
					count++;
				}
			}
			
			log.debug("count: " + count);
			
			if(count > limMaxAutorizaLibera) {
				validationErrors = true;
				String mensaje = String.format("Solo puede seleccionar hasta %d registros.", limMaxAutorizaLibera);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, mensaje));
			}
		}
	}
	
	public void checkAllOperAutoriza(ActionEvent event) {
		this.idsAutorizar = "";
		
		if(this.operacionesEfectivo != null && !this.operacionesEfectivo.isEmpty()) {
			
			List<Long> ids = new ArrayList<>();
			for(InstruccionEfectivoDTO dto: this.operacionesEfectivo) {
				
				if( (getInstitucionActual().getIdFolio().equals( dto.getInstitucionTraspasante().getIdFolio()) || 
						(isUsuarioIndeval() && dto.getTipoInstruccion().getIdTipoInstruccion() == Constantes.ID_TIPO_INSTRUCCION__TREF) ) 
						&& dto.getEstadoInstruccion().getIdEstadoInstruccion() == EstadoInstruccion.REGISTRADA_VALUE 
						&& this.checkAllAutoriza && ids.size() < limMaxAutorizaLibera) {
					ids.add(dto.getIdInstruccion().longValue());
					dto.setSeleccionadoAutoriza(this.checkAllAutoriza);
				}else {
					dto.setSeleccionadoAutoriza(false);
				}
			}
			
			this.idsAutorizar = StringUtils.join(ids.iterator(), ",");
			
			log.debug("count: " + ids.size());
			
			if(this.checkAllAutoriza && ids.size() == limMaxAutorizaLibera) {
				String mensaje = String.format("Se seleccionaron %d registros.", limMaxAutorizaLibera);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, mensaje));
			}
		}
	}
	
	public void checkAllOperLibera(ActionEvent event) {
		this.idsLiberar = "";
		if(this.operacionesEfectivo != null && !this.operacionesEfectivo.isEmpty()) {
			
			List<Long> ids = new ArrayList<>();
			for(InstruccionEfectivoDTO dto: this.operacionesEfectivo) {
				
				if( (getInstitucionActual().getIdFolio().equals( dto.getInstitucionTraspasante().getIdFolio()) || 
						(isUsuarioIndeval() && dto.getTipoInstruccion().getIdTipoInstruccion() == Constantes.ID_TIPO_INSTRUCCION__TREF) ) 
						&& dto.getEstadoInstruccion().getIdEstadoInstruccion() == EstadoInstruccion.AUTORIZADA_VALUE 
						&& this.checkAllLibera && ids.size() < limMaxAutorizaLibera) {
					ids.add(dto.getIdInstruccion().longValue());
					dto.setSeleccionadoLibera(this.checkAllLibera);
				}else {
					dto.setSeleccionadoLibera(false);
				}
			}
			
			this.idsLiberar = StringUtils.join(ids.iterator(), ",");
			
			log.debug("count: " + ids.size());
			
			if(this.checkAllLibera && ids.size() == limMaxAutorizaLibera) {
				String mensaje = String.format("Se seleccionaron %d registros.", limMaxAutorizaLibera);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, mensaje));
			}
		}
	}
	
	private static final String MENSAJE_FIRMA_DIGITAL = "Usted No tiene habilitada la facultad de firma digital.";
	
	public String autorizaSeleccion() {
		log.debug("autorizaSeleccion");
		
		this.validationErrors = false;
		if(!isUsuarioConFacultadFirmar()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, MENSAJE_FIRMA_DIGITAL, MENSAJE_FIRMA_DIGITAL));
			return null;
		}
		
		this.esTipo = "autorizaSeleccion";
		
		try {
			List<Long> seleccion = getSeleccion(EstadoInstruccion.REGISTRADA_VALUE);
			
			log.debug("autorizar registros " + seleccion.size());
			
			autorizaOperaciones(seleccion);
			
			log.debug("isosSinFirmar " + isosSinFirmar.size());
			
		}catch(BusinessException e) {
			log.warn(e.getMessage());
			inicializaIso();
			
			this.validationErrors = true;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
		}
		
		return null;
	}

	public String liberaSeleccion() {
		log.debug("liberaSeleccion");
		
		this.validationErrors = false;
		
		if(!isUsuarioConFacultadFirmar()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, MENSAJE_FIRMA_DIGITAL, MENSAJE_FIRMA_DIGITAL));
			return null;
		}
		
		this.esTipo = "liberaSeleccion";
		
		try {
			List<Long> seleccion = getSeleccion(EstadoInstruccion.AUTORIZADA_VALUE);
			
			log.debug("autorizar registros " + seleccion.size());
			
			liberaOperaciones(seleccion);
			
		}catch(BusinessException e) {
			log.warn(e.getMessage());
			inicializaIso();
			
			this.validationErrors = true;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
		}
		
		return null;
	}
	
	public String autoriza() {
		log.debug("autoriza");
		this.validationErrors = false;
		
		if(!isUsuarioConFacultadFirmar()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, MENSAJE_FIRMA_DIGITAL, MENSAJE_FIRMA_DIGITAL));
			return null;
		}
		
		this.esTipo = "autoriza";
		
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		
		String idInstruccionStr = params.get("idInstruccion");
		log.debug("idInstruccionStr: " + idInstruccionStr);
		
		try {
			// !important
			this.idInstruccion = Long.valueOf(idInstruccionStr);
			autorizaOperaciones(Arrays.asList(this.idInstruccion));
		}catch(NumberFormatException e) {
			log.error(e.getMessage());
			inicializaIso();
			
			this.validationErrors = true;
		}catch(BusinessException e) {
			log.warn(e.getMessage());
			inicializaIso();
			
			this.validationErrors = true;
			FacesContext.getCurrentInstance().addMessage(null,
			new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
		}
		return null;
	}

	public String libera() {
		log.debug("libera");
		
		this.validationErrors = false;
		if(!isUsuarioConFacultadFirmar()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, MENSAJE_FIRMA_DIGITAL, MENSAJE_FIRMA_DIGITAL));
			return null;
		}
		
		this.esTipo = "libera";
		
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		
		String idInstruccionStr = params.get("idInstruccion");
		log.debug("idInstruccionStr: " + idInstruccionStr);
		
		try {
			// !important 
			this.idInstruccion = Long.valueOf(idInstruccionStr);
			liberaOperaciones(Arrays.asList(this.idInstruccion));
		}catch(NumberFormatException e) {
			log.error(e.getMessage());
			inicializaIso();
			
			this.validationErrors = true;
		}catch(BusinessException e) {
			log.warn(e.getMessage());
			inicializaIso();
			
			this.validationErrors = true;
			FacesContext.getCurrentInstance().addMessage(null,
			new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
		}
		return null;
	}
	
	private void autorizaOperaciones(List<Long> operaciones) {
		log.debug("autorizaOperaciones");
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String numeroSerie = params.get("numeroSerie");
		
		inicializaIso();
		
		int index=0;
		for(Long idOperacionEfectivo: operaciones) {
			index++;
			
			RetiroEfectivoDTO retiroEfectivoDTO = retiroEfectivoService.findById(idOperacionEfectivo );
			
			if(retiroEfectivoDTO == null)
				throw new BusinessException(String.format("No se encontr\u00f3 la operaci\u00f3n con id [%d]", idOperacionEfectivo));
			
			if(retiroEfectivoDTO.getEstado()== null || retiroEfectivoDTO.getEstado().getIdEstadoInstruccion() == null || 
					retiroEfectivoDTO.getEstado().getIdEstadoInstruccion().intValue() != EstadoInstruccion.REGISTRADA_VALUE)
				throw new BusinessException(String.format("La operaci\u00f3n con id [%s] tiene un estado inv\u00e1lido.", retiroEfectivoDTO.getReferenciaOperacion()));
			
			
			// aqui si no se usa firma se guarda la operacion
			// o se guarda la operacion si ya viene con firma
			if (!isUsuarioConFacultadFirmar() || StringUtils.isNotBlank(numeroSerie)) {
				String isoSinFirmar = params.get("isoSinFirmar" + index);
				String isoFirmado = params.get("isoFirmado" + index);
				
				//En IE esta cadena llega con esta modificacion se elimina para que coincida la firma
				isoSinFirmar = isoSinFirmar.replace("\r\n", "\n");
				
				/// String referenciaOperacionActual = map.get("referenciaMensajes" + var);
				StringBuilder isoCompleto = new StringBuilder();
				if (StringUtils.isNotBlank(isoSinFirmar) && StringUtils.isNotBlank(numeroSerie) && StringUtils.isNotBlank(isoFirmado)) {
					//no se coloca \n antes del numero de serie ya que el isoSinFirmar ya lo trae
					isoCompleto.append(isoSinFirmar).append(numeroSerie).append("\n").append("{SHA1withRSA}")
							.append(isoFirmado);
				}
				
				Map<String, String> datosFirma = new HashMap<String, String>(0);
				datosFirma.put(SeguridadConstants.ISO_FIRMADO, isoCompleto.toString());
				datosFirma.put(SeguridadConstants.USUARIO_SESION, obtenerUsuarioSesion().getClaveUsuario());
				datosFirma.put(SeguridadConstants.SERIE, numeroSerie);
				
				retiroEfectivoService.autoriza(idOperacionEfectivo, datosFirma);
				
				if (!existeErrorEnInvocacion() && index == operaciones.size()) {
					mensajeUsuario = "Autorizaci\u00f3n realizada con exito.";
					addMessage(mensajeUsuario, FacesMessage.SEVERITY_INFO);
				}
			}else {
				
				retiroEfectivoDTO.setFechaLiberacion(new Date());
				String isoElem = envioOperacionService.generaIso(retiroEfectivoDTO);
				//se coloca el salto de linea antes de firmar, es necesario para que no las tome como firma invalida.
				isoElem +='\n';
				
				logger.debug("1.-ISOS_SIN_FIRMAR ---->"+isoElem+"<------");
				
				if (isoElem != null){
					referenciaOperaciones.add(retiroEfectivoDTO.getReferenciaOperacion());
					referenciaMensajes.add(String.valueOf(idOperacionEfectivo));
					isosSinFirmar.add(isoElem);
					hashIso.add(cdb.cipherHash(isoElem));
				}
			}
		}
		
		totalOperaciones = isosSinFirmar.size();
		
		log.info("totalOperaciones: " + totalOperaciones);
		log.info("isosSinFirmar: " + isosSinFirmar.size());
	}
	
	private void liberaOperaciones(List<Long> operaciones) {
		log.debug("liberaOperaciones");
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String numeroSerie = params.get("numeroSerie");
		
		inicializaIso();
		
		// aqui si no se usa firma se guarda la operacion
		// o se guarda la operacion si ya viene con firma
		
		int index=0;
		for(Long idOperacionEfectivo: operaciones) {
			index++;
			
			RetiroEfectivoDTO retiroEfectivoDTO = retiroEfectivoService.findById(idOperacionEfectivo);
			
			if(retiroEfectivoDTO == null)
				throw new BusinessException(String.format("No se encontr\u00f3 la operaci\u00f3n con id [%d]", idOperacionEfectivo));
			
			if(retiroEfectivoDTO.getEstado()== null || retiroEfectivoDTO.getEstado().getIdEstadoInstruccion() == null || 
					retiroEfectivoDTO.getEstado().getIdEstadoInstruccion().intValue() != EstadoInstruccion.AUTORIZADA_VALUE)
				throw new BusinessException(String.format("La operaci\u00f3n [%s] tiene un estado inv\u00e1lido.", retiroEfectivoDTO.getReferenciaOperacion()));
			
			if (!isUsuarioConFacultadFirmar()  || StringUtils.isNotBlank(numeroSerie) ) {
				String isoSinFirmar = params.get("isoSinFirmar" + index);
				String isoFirmado = params.get("isoFirmado" + index);
				
				//En IE esta cadena llega con esta modificacion se elimina para que coincida la firma
				isoSinFirmar = isoSinFirmar.replace("\r\n", "\n");
						
				StringBuilder isoCompleto = new StringBuilder();
				if (StringUtils.isNotBlank(isoSinFirmar) && StringUtils.isNotBlank(numeroSerie) && StringUtils.isNotBlank(isoFirmado)) {
					//no se coloca \n antes del numero de serie ya que el isoSinFirmar ya lo trae
					isoCompleto.append(isoSinFirmar).append(numeroSerie).append("\n").append("{SHA1withRSA}").append(isoFirmado);
				} 
				
				enviarOperacionABitacora(retiroEfectivoDTO, isoCompleto.toString());
				
				Map<String, String> datosFirma = new HashMap<String, String>();
				datosFirma.put(SeguridadConstants.ISO_FIRMADO, isoCompleto.toString());
				datosFirma.put(SeguridadConstants.USUARIO_SESION, obtenerUsuarioSesion().getClaveUsuario());
				datosFirma.put(SeguridadConstants.SERIE, numeroSerie);
				
				retiroEfectivoService.libera(idOperacionEfectivo, datosFirma);
				
				if (!existeErrorEnInvocacion() && index == operaciones.size()) {
					mensajeUsuario = "Liberaci\u00f3n realizada con exito.";
					addMessage(mensajeUsuario, FacesMessage.SEVERITY_INFO);
				}
			}else {
				if(!consultaInstruccionesEfectivoService.validaLiberacion()) {
					String mensaje = "No se Puede Liberar Fuera de Horario";
					log.warn(mensaje);
					throw new BusinessException(mensaje);
				}
				
				retiroEfectivoDTO.setFechaLiberacion(new Date());
				String isoElem = envioOperacionService.generaIso( retiroEfectivoDTO);
				
				//se coloca el salto de linea antes de firmar, es necesario para que no las tome como firma invalida.
				isoElem +='\n';

				logger.debug("2.-ISOS_SIN_FIRMAR ---->"+isoElem+"<------");
				
				if (isoElem != null){
					referenciaOperaciones.add(retiroEfectivoDTO.getReferenciaOperacion());
					referenciaMensajes.add(String.valueOf(idOperacionEfectivo));
					isosSinFirmar.add(isoElem);
					hashIso.add(cdb.cipherHash(isoElem));
				}
			}
		}
		
		totalOperaciones = isosSinFirmar.size();
		
		log.info("totalOperaciones: " + totalOperaciones);
		log.info("isosSinFirmar: " + isosSinFirmar.size());
	}
	
	private void inicializaIso() {
		// Inicializa los ISO
		isosSinFirmar = new ArrayList<String>();
		isosFirmados = new ArrayList<String>();
		hashIso = new ArrayList<String>();
		referenciaOperaciones = new ArrayList<String>();
		referenciaMensajes= new ArrayList<String>();
	}
	private List<Long> getSeleccion(int tipoSeleccion) throws BusinessException{
			
		List<InstruccionEfectivoDTO> seleccion = new ArrayList<>();
		
		for(InstruccionEfectivoDTO dto: this.operacionesEfectivo) {
			if(tipoSeleccion == EstadoInstruccion.REGISTRADA_VALUE && dto.isSeleccionadoAutoriza() ) {
				seleccion.add(dto);
			} else if(tipoSeleccion == EstadoInstruccion.AUTORIZADA_VALUE && dto.isSeleccionadoLibera() ) {
				seleccion.add(dto);
			}
		}
		
		if(seleccion.isEmpty()) {
			throw new BusinessException("Debe seleccionar al menos un registro.");
		}
		
		if(seleccion.size() > this.limMaxAutorizaLibera ) {
			throw new BusinessException(String.format("Solo puede seleccionar hasta %d registros.", this.limMaxAutorizaLibera));
		}
		
		List<Long> result = new ArrayList<>();
		for(InstruccionEfectivoDTO dto: seleccion) {
			
			// Agregar de la seleccion, los registros que ya se encuentran inactivos
			if( EstadoInstruccion.REGISTRADA_VALUE == tipoSeleccion &&  
					dto.getEstadoInstruccion().getIdEstadoInstruccion() == EstadoInstruccion.REGISTRADA_VALUE ) {
				result.add(dto.getIdInstruccion().longValue());
			}else if( EstadoInstruccion.AUTORIZADA_VALUE == tipoSeleccion && 
					dto.getEstadoInstruccion().getIdEstadoInstruccion() == EstadoInstruccion.AUTORIZADA_VALUE) {
				result.add(dto.getIdInstruccion().longValue());
			}
		}
		
		if(result.isEmpty()) {
			throw new BusinessException("Su solicitud contiene una seleccion invalida.");
		}
		
		return result;
	}

	public void navegarPorResultadosIntl(ActionEvent actionEvent) {
		String navegacion = (String) actionEvent.getComponent().getAttributes()
				.get("navegacion");
		ResultadoValidacionDTO resultado = crearCriterio();
		if (resultado.isValido()) {
			try {
				EstadoPaginacionDTO.class.getMethod(navegacion).invoke(
						paginacion);

			} catch (Exception ex) {
				log
						.error(
								"Error de invocación de método al navega por los resultados principales",
								ex);
			}
			consultaEjecutada = true;
			resultadosIntl = consultaInstruccionesEfectivoService.getInstEfecIntl(criterio, paginacion);
			obtenerTotalesIntl();
			
		} else {
			consultaEjecutada = false;
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, resultado
							.getMensaje(), resultado.getMensaje()));
		}

	}
	
	/**
	 * fecha para reportes
	 */
	public String getFechaReportes() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
		return formatter.format(new Date());

	}

	/**
	 * Realiza la consulta de los totales de la consulta principal
	 * 
	 */
	private void obtenerTotales() {
		Map<Object, Object> resultados = consultaInstruccionesEfectivoService.obtenerTotalesDeConsultaInstruccionesEfectivo(criterio);
		totalImporte = BigDecimal.ZERO;
		if (resultados != null
				&& resultados
						.get(ConsultaInstruccionesEfectivoService.TOTAL_IMPORTE) != null) {
			totalImporte = new BigDecimal(((Number) resultados
					.get(ConsultaInstruccionesEfectivoService.TOTAL_IMPORTE))
					.doubleValue());

		}
		/*
		if (resultados != null && resultados.get(ConsultaInstruccionesEfectivoService.TOTAL_IMPORTE_MATCH) != null) {
			totalImporte=totalImporte.add(new BigDecimal(((Number) resultados.get(ConsultaInstruccionesEfectivoService.TOTAL_IMPORTE_MATCH)).doubleValue()));
		}/*     */
		
		if (resultados != null && resultados.get(ConsultaInstruccionesEfectivoService.TOTAL_IMPORTE_NAL) != null ) {
			
			totalImporte=totalImporte.add(new BigDecimal(((Number) resultados.get(ConsultaInstruccionesEfectivoService.TOTAL_IMPORTE_NAL)).doubleValue()));
		}
		
		totalImporte=totalImporte.setScale(2, BigDecimal.ROUND_DOWN);		
	}

	/**
	 * Realiza la consulta de los totales de la consulta principal
	 * 
	 */
	private void obtenerTotalesIntl() {

		// Map<Object, Object> resultados =
		// consultaInstruccionesEfectivoService.obtenerTotalesDeConsultaInstruccionesEfectivo(criterio);
		Map<Object, Object> resultados = consultaInstruccionesEfectivoService
				.sumTotDeConsInstEfec(criterio);
		totalImporte = BigDecimal.ZERO;
		if (resultados != null
				&& resultados
						.get(ConsultaInstruccionesEfectivoService.TOTAL_IMPORTE_INTL) != null) {
			totalImporte = totalImporte.add(new BigDecimal(((Number) resultados
					.get(ConsultaInstruccionesEfectivoService.TOTAL_IMPORTE_INTL)).doubleValue()));

		}
		if (resultados != null
				&& resultados
						.get(ConsultaInstruccionesEfectivoService.TOTAL_IMPORTE_MOI) != null) {
			totalImporte =totalImporte.add(new BigDecimal(((Number) resultados
					.get(ConsultaInstruccionesEfectivoService.TOTAL_IMPORTE_MOI))
					.doubleValue()));

		}

	}

	/**
	 * Ejecuta la consulta de búsqueda de operaciones y match para un conjunto
	 * de criterios solicitados
	 * 
	 */
	public void buscarOperacionesEfectivo() {
		log.debug("buscarOperacionesEfectivo");
		isosSinFirmar = new ArrayList<String>();
		isosFirmados = new ArrayList<String>();
		hashIso = new ArrayList<String>();
		this.checkAllAutoriza = false;
		this.checkAllLibera = false;
		this.esTipo = null;
		this.idInstruccion=null;
		this.validationErrors = false;
        //List<InstruccionEfectivoDTO> resultadoslistReteNal = null;
		ResultadoValidacionDTO resultado = crearCriterio();
		if (resultado.isValido()) {
			buscar();
		} else {
			consultaEjecutada = false;
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, resultado
							.getMensaje(), resultado.getMensaje()));
		}
	}

	public void buscarOperacionesEfectivoIntl() {
		ResultadoValidacionDTO resultado = crearCriterio();
		isosSinFirmar = new ArrayList<String>();
		isosFirmados = new ArrayList<String>();
		hashIso = new ArrayList<String>();
		if (resultado.isValido()) {

			incializarEstadoPaginacion(consultaInstruccionesEfectivoService.getProyeccionConsultaInstEfecIntl(criterio,	debeDejarBitacora));
			debeDejarBitacora = false;
			// ee
			resultadosIntl = consultaInstruccionesEfectivoService.getInstEfecIntl(criterio, paginacion);
			// obtenerTotales();
			obtenerTotalesIntl();
			consultaEjecutada = true;
		} else {
			consultaEjecutada = false;
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, resultado
							.getMensaje(), resultado.getMensaje()));
		}
	}

	public void verificaInstruccion(ActionEvent actionEvent) {
		if (StringUtils.isNotEmpty(tipoInstruccion)
				&& (tipoInstruccion.equalsIgnoreCase("DEPE") || tipoInstruccion
						.equalsIgnoreCase("RETE"))) {
			muestraTipoRetiro = true;
		} else {
			muestraTipoRetiro = false;
			criterio.setTipoRetiro("TODOS");
		}
	}

	/** métodos getters & settters. */

	/**
	 * Obtiene el campo criterio
	 * 
	 * @return criterio
	 */
	public CriterioEstatusOpEfectivoDTO getCriterio() {
		return criterio;
	}

	/**
	 * Asigna el campo criterio
	 * 
	 * @param criterio
	 *            el valor de criterio a asignar
	 */
	public void setCriterio(CriterioEstatusOpEfectivoDTO criterio) {
		this.criterio = criterio;
	}

	/**
	 * Obtiene el campo consultaCatalogos
	 * 
	 * @return consultaCatalogos
	 */
	public ConsultaCatalogosFacade getConsultaCatalogos() {
		return consultaCatalogos;
	}

	/**
	 * Asigna el campo consultaCatalogos
	 * 
	 * @param consultaCatalogos
	 *            el valor de consultaCatalogos a asignar
	 */
	public void setConsultaCatalogos(ConsultaCatalogosFacade consultaCatalogos) {
		this.consultaCatalogos = consultaCatalogos;
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
	 * Asigna el campo consultaEjecutada
	 * 
	 * @param consultaEjecutada
	 *            el valor de consultaEjecutada a asignar
	 */
	public void setConsultaEjecutada(boolean consultaEjecutada) {
		this.consultaEjecutada = consultaEjecutada;
	}

	/**
	 * Obtiene el campo resultados
	 * 
	 * @return resultados
	 */
	public List<InstruccionEfectivoDTO> getResultados() {
		return resultados;
	}

	/**
	 * Asigna el campo resultados
	 * 
	 * @param resultados
	 *            el valor de resultados a asignar
	 */
	public void setResultados(List<InstruccionEfectivoDTO> resultados) {
		this.resultados = resultados;
	}

	/**
	 * Obtiene el campo consultaInstruccionesEfectivoService
	 * 
	 * @return consultaInstruccionesEfectivoService
	 */
	public ConsultaInstruccionesEfectivoService getConsultaInstruccionesEfectivoService() {
		return consultaInstruccionesEfectivoService;
	}

	/**
	 * Asigna el campo consultaInstruccionesEfectivoService
	 * 
	 * @param consultaInstruccionesEfectivoService
	 *            el valor de consultaInstruccionesEfectivoService a asignar
	 */
	public void setConsultaInstruccionesEfectivoService(
			ConsultaInstruccionesEfectivoService consultaInstruccionesEfectivoService) {
		this.consultaInstruccionesEfectivoService = consultaInstruccionesEfectivoService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indeval.portaldali.presentation.controller.common.ControllerBase#
	 * ejecutarConsultaReporte()
	 */
	@Override
	protected Collection<? extends Object> ejecutarConsultaReporte() {
		crearCriterio();
		return consultaInstruccionesEfectivoService
				.consultarInstruccionesEfectivo(criterio, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indeval.portaldali.presentation.controller.common.ControllerBase#
	 * getNombreReporte()
	 */
	@Override
	protected String getNombreReporte() {
		return "operacionEfectivo";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indeval.portaldali.presentation.controller.common.ControllerBase#
	 * llenarParametros()
	 */
	@Override
	protected Map<String, Object> llenarParametros() {
		Map<String, Object> params = new HashMap<String, Object>();
		crearCriterio();
		params.put("criterio", criterio);
		return params;
	}

	/**
	 * Obtiene el campo totalImporte
	 * 
	 * @return totalImporte
	 */
	public BigDecimal getTotalImporte() {
		return totalImporte;
	}

	/**
	 * Asigna el campo totalImporte
	 * 
	 * @param totalImporte
	 *            el valor de totalImporte a asignar
	 */
	public void setTotalImporte(BigDecimal totalImporte) {
		this.totalImporte = totalImporte;
	}

	/**
	 * Obtiene el valor del campo totalRegistros
	 * 
	 * @return el valor de totalRegistros
	 */
	public int getTotalRegistros() {
		return totalRegistros;
	}

	/**
	 * Asigna el campo totalRegistros
	 * 
	 * @param totalRegistros
	 *            el valor de totalRegistros a asignar
	 */
	public void setTotalRegistros(int totalRegistros) {
		this.totalRegistros = totalRegistros;
	}

	/**
	 * @return the tipoInstruccionDaliDAO
	 */
	public TipoInstruccionDaliDAO getTipoInstruccionDAO() {
		return tipoInstruccionDaliDAO;
	}

	/**
	 * @param tipoInstruccionDaliDAO
	 *            the tipoInstruccionDaliDAO to set
	 */
	public void setTipoInstruccionDAO(
			TipoInstruccionDaliDAO tipoInstruccionDaliDAO) {
		this.tipoInstruccionDaliDAO = tipoInstruccionDaliDAO;
	}

	public TipoLiquidacionDaliDAO getTipoLiquidacionDaliDAO() {
		return tipoLiquidacionDaliDAO;
	}

	public void setTipoLiquidacionDaliDAO(
			TipoLiquidacionDaliDAO tipoLiquidacionDaliDAO) {
		this.tipoLiquidacionDaliDAO = tipoLiquidacionDaliDAO;
	}

	/**
	 * @return the estadoInstruccionDaliDAO
	 */
	public EstadoInstruccionDaliDAO getEstadoInstruccionDAO() {
		return estadoInstruccionDaliDAO;
	}

	/**
	 * @param estadoInstruccionDaliDAO
	 *            the estadoInstruccionDaliDAO to set
	 */
	public void setEstadoInstruccionDAO(
			EstadoInstruccionDaliDAO estadoInstruccionDaliDAO) {
		this.estadoInstruccionDaliDAO = estadoInstruccionDaliDAO;
	}

	public DivisaDaliDAO getDivisaDAO() {
		return divisaDaliDAO;
	}

	public void setDivisaDAO(DivisaDaliDAO divisaDaliDAO) {
		this.divisaDaliDAO = divisaDaliDAO;
	}

	public BovedaDaliDAO getBovedaDAO() {
		return bovedaDaliDAO;
	}

	public void setBovedaDAO(BovedaDaliDAO bovedaDaliDAO) {
		this.bovedaDaliDAO = bovedaDaliDAO;
	}

	public TipoLiquidacionDaliDAO getTipoLiquidacionDAO() {
		return tipoLiquidacionDaliDAO;
	}

	/**
	 * @param estadoInstruccionDaliDAO
	 *            the estadoInstruccionDaliDAO to set
	 */
	public void setTipoLiquidacionDAO(
			TipoLiquidacionDaliDAO tipoLiquidacionDaliDAO) {
		this.tipoLiquidacionDaliDAO = tipoLiquidacionDaliDAO;
	}

	/**
	 * @param estadosInstruccionItems
	 *            the estadosInstruccionItems to set
	 */
	public void setEstadosInstruccionItems(List<SelectItem> estadosInstruccionItems) {
		this.estadosInstruccionItems = estadosInstruccionItems;
	}

	/**
	 * @return the estadosInstruccionItems
	 */
	public List<SelectItem> getEstadosInstruccionItems() {
		return estadosInstruccionItems;
	}

	public List<SelectItem> getDivisasItems() {
		return divisasItems;
	}

	public void setDivisasItems(List<SelectItem> divisasItems) {
		this.divisasItems = divisasItems;
	}

	public List<SelectItem> getBovedasItems() {
		return bovedasItems;
	}

	public void setBovedasItems(List<SelectItem> bovedasItems) {
		this.bovedasItems = bovedasItems;
	}

	public List<SelectItem> getEstadosTipoAbonoItems() {
		return estadosTipoAbonoItems;
	}

	public void setEstadosTipoAbonoItems(List<SelectItem> estadosTipoAbonoItems) {
		this.estadosTipoAbonoItems = estadosTipoAbonoItems;
	}

	/**
	 * @param tiposInstruccionesItems
	 *            the tiposInstruccionesItems to set
	 */
	public void setTiposInstruccionesItems(
			List<SelectItem> tiposInstruccionesItems) {
		this.tiposInstruccionesItems = tiposInstruccionesItems;
	}

	/**
	 * @return the tiposInstruccionesItems
	 */
	public List<SelectItem> getTiposInstruccionesItems() {
		return tiposInstruccionesItems;
	}

	public boolean isMuestraTipoRetiro() {
		return muestraTipoRetiro;
	}

	public void setMuestraTipoRetiro(boolean muestraTipoRetiro) {
		this.muestraTipoRetiro = muestraTipoRetiro;
	}

	/**
	 * @return the debeDejarBitacora
	 */
	public boolean isDebeDejarBitacora() {
		return debeDejarBitacora;
	}

	/**
	 * @param debeDejarBitacora
	 *            the debeDejarBitacora to set
	 */
	public void setDebeDejarBitacora(boolean debeDejarBitacora) {
		this.debeDejarBitacora = debeDejarBitacora;
	}

	/**
	 * Obtiene el valor del atributo idFolioParticipante
	 * 
	 * @return el valor del atributo idFolioParticipante
	 */
	public String getIdFolioParticipante() {
		return idFolioParticipante;

	}

	/**
	 * Establece el valor del atributo idFolioParticipante
	 * 
	 * @param idFolioParticipante
	 *            el valor del atributo idFolioParticipante a establecer
	 */
	public void setIdFolioParticipante(String idFolioParticipante) {
		this.idFolioParticipante = idFolioParticipante;
	}

	/**
	 * Obtiene el valor del atributo idFolioContraparte
	 * 
	 * @return el valor del atributo idFolioContraparte
	 */
	public String getIdFolioContraparte() {
		return idFolioContraparte;
	}

	/**
	 * Establece el valor del atributo idFolioContraparte
	 * 
	 * @param idFolioContraparte
	 *            el valor del atributo idFolioContraparte a establecer
	 */
	public void setIdFolioContraparte(String idFolioContraparte) {
		this.idFolioContraparte = idFolioContraparte;
	}

	public EnvioOperacionesService getEnvioOperacionService() {
		return envioOperacionService;
	}

	public void setEnvioOperacionService(
			EnvioOperacionesService envioOperacionService) {
		this.envioOperacionService = envioOperacionService;
	}

	public IsoHelper getIsoHelper() {
		return isoHelper;
	}

	public void setIsoHelper(IsoHelper isoHelper) {
		this.isoHelper = isoHelper;
	}

	public UtilServices getUtilService() {
		return utilService;
	}

	public void setUtilService(UtilServices utilService) {
		this.utilService = utilService;
	}

	public List<String> getIsosFirmados() {
		return isosFirmados;
	}

	public void setIsosFirmados(List<String> isosFirmados) {
		this.isosFirmados = isosFirmados;
	}

	public List<String> getIsosSinFirmar() {
		return isosSinFirmar;
	}

	public void setIsosSinFirmar(List<String> isosSinFirmar) {
		this.isosSinFirmar = isosSinFirmar;
	}	

	public List<String> getReferenciaOperaciones() {
		return referenciaOperaciones;
	}

	public void setReferenciaOperaciones(List<String> referenciaOperacion) {
		this.referenciaOperaciones = referenciaOperacion;
	}
	
	public List<String> getHashIso() {
		return hashIso;
	}

	public void setHashIso(List<String> hashIso) {
		this.hashIso = hashIso;
	}


	public List<RetiroEfectivoInternacionalDTO> getResultadosIntl() {
		return resultadosIntl;
	}

	public void setResultadosIntl(
			List<RetiroEfectivoInternacionalDTO> resultadosIntl) {
		this.resultadosIntl = resultadosIntl;
	}

	public Boolean getConfirmacion() {
		return confirmacion;
	}

	public void setConfirmacion(Boolean confirmacion) {
		this.confirmacion = confirmacion;
	}

	
	
	public String getEsTipo() {
		return esTipo;
	}

	public void setEsTipo(String esTipo) {
		this.esTipo = esTipo;
	}

	public int getTotalOperaciones() {
		return totalOperaciones;
	}

	public void setTotalOperaciones(int totalOperaciones) {
		this.totalOperaciones = totalOperaciones;
	}

	public boolean isCheckAllAutoriza() {
		return checkAllAutoriza;
	}

	public void setCheckAllAutoriza(boolean checkAllAutoriza) {
		this.checkAllAutoriza = checkAllAutoriza;
	}

	public boolean isCheckAllLibera() {
		return checkAllLibera;
	}

	public void setCheckAllLibera(boolean checkAllLibera) {
		this.checkAllLibera = checkAllLibera;
	}

	/**
	 * @return the listaTipoAbono
	 */
	public List<SelectItem> getListaTipoAbono() {
		return listaTipoAbono;
	}

	/**
	 * @param listaTipoAbono
	 *            the listaTipoAbono to set
	 */
	public void setListaTipoAbono(List<SelectItem> listaTipoAbono) {
		this.listaTipoAbono = listaTipoAbono;
	}

	public Map<String, String> getMapaTipoAbono() {
		return mapaTipoAbono;
	}

	public void setMapaTipoAbono(Map<String, String> mapaTipoAbono) {
		this.mapaTipoAbono = mapaTipoAbono;
	}

	public List<String> getReferenciaMensajes() {
		return referenciaMensajes;
	}

	public void setReferenciaMensajes(List<String> referenciaMensajes) {
		this.referenciaMensajes = referenciaMensajes;
	}

	public void setRetiroEfectivoService(RetiroEfectivoService retiroEfectivoService) {
		this.retiroEfectivoService = retiroEfectivoService;
	}


	public boolean isValidationErrors() {
		return validationErrors;
	}


	public void setValidationErrors(boolean validationErrors) {
		this.validationErrors = validationErrors;
	}


	public Long getIdInstruccion() {
		return idInstruccion;
	}


	public void setIdInstruccion(Long idInstruccion) {
		this.idInstruccion = idInstruccion;
	}
	public boolean isCheckAllAutorizaEnabled() {
		return checkAllAutorizaEnabled;
	}
	public void setCheckAllAutorizaEnabled(boolean checkAllAutorizaEnabled) {
		this.checkAllAutorizaEnabled = checkAllAutorizaEnabled;
	}
	public boolean isCheckAllLiberaEnabled() {
		return checkAllLiberaEnabled;
	}
	public void setCheckAllLiberaEnabled(boolean checkAllLiberaEnabled) {
		this.checkAllLiberaEnabled = checkAllLiberaEnabled;
	}
	public void setPropiedadesDaliService(PropiedadesDaliService propiedadesDaliService) {
		this.propiedadesDaliService = propiedadesDaliService;
	}
	public String getIdsAutorizar() {
		return idsAutorizar;
	}
	public void setIdsAutorizar(String idsAutorizar) {
		this.idsAutorizar = idsAutorizar;
	}
	public String getIdsLiberar() {
		return idsLiberar;
	}
	public void setIdsLiberar(String idsLiberar) {
		this.idsLiberar = idsLiberar;
	}
}

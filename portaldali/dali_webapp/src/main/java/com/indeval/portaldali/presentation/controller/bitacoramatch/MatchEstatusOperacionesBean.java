/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 *
 * 24/02/2008
 */
package com.indeval.portaldali.presentation.controller.bitacoramatch;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;

import com.bursatec.seguridad.presentation.constants.SeguridadConstants;
import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.EmisoraDTO;
import com.indeval.portaldali.middleware.dto.ErrorDaliDTO;
import com.indeval.portaldali.middleware.dto.EstadoInstruccionDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.MercadoDTO;
import com.indeval.portaldali.middleware.dto.OperacionValorMatchDTO;
import com.indeval.portaldali.middleware.dto.TipoCuentaDTO;
import com.indeval.portaldali.middleware.dto.TipoInstitucionDTO;
import com.indeval.portaldali.middleware.dto.TipoInstruccionDTO;
import com.indeval.portaldali.middleware.dto.TipoNaturalezaDTO;
import com.indeval.portaldali.middleware.dto.TipoTenenciaDTO;
import com.indeval.portaldali.middleware.dto.TipoValorDTO;
import com.indeval.portaldali.middleware.dto.criterio.CriterioMatchOperacionesDTO;
import com.indeval.portaldali.middleware.dto.criterio.CriterioMatchOperacionesExportacionDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.middleware.dto.util.ResultadoValidacionDTO;
import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;
import com.indeval.portaldali.middleware.services.common.constants.TipoCustodiaConstants;
import com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService;
import com.indeval.portaldali.middleware.services.estatus.ConsultaEstatusOperacionesMatchService;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.persistence.dao.common.DivisaDaliDAO;
import com.indeval.portaldali.persistence.dao.common.EstadoInstruccionDaliDAO;
import com.indeval.portaldali.persistence.dao.common.TipoInstruccionDaliDAO;
import com.indeval.portaldali.presentation.controller.common.ControllerBase;
import com.indeval.portaldali.presentation.util.CifradorDescifradorBlowfish;
import com.indeval.portaldali.presentation.util.ConsultaCatalogosFacade;

/**
 * Backing bean para el servicio a la clase de match de operaciones y estatus.
 *
 * @author Emigdio Hernández
 *
 */
public class MatchEstatusOperacionesBean extends ControllerBase {

	/**
	 * Criterio de búsqueda
	 */
	private CriterioMatchOperacionesDTO criterio = new CriterioMatchOperacionesDTO();
	/**
	 * Acceso a los elementos de los catálogos
	 */
	private ConsultaCatalogosFacade consultaCatalogos = null;
	/**
	 * Servicio de consulta de las operaciones
	 */
	private ConsultaEstatusOperacionesMatchService consultaEstatusOperacionesMatchService = null;
	/**
	 * Indica si existen resultados disponibles
	 */
	private boolean consultaEjecutada = false;

	/**
	 * Lista que contiene los datos resultado de la consulta de estatus de
	 * operaciones y match
	 */
	private List<OperacionValorMatchDTO> resultados = null;

	/** Contiene la representación en cadena del id y el folio de la contraparte */
	private String institucionContraparte = null;

	/**
	 * Contiene la representación en cadena del número de cuenta de la
	 * contraparte
	 */
	private String cuentaContraparte = null;

	/** Contiene la representación en cadena del id y el folio del participante */
	private String institucionParticipante = null;

	/**
	 * Contiene la representación en cadena del numero de cuenta del
	 * participante
	 */
	private String cuentaParticipante = null;

	/**
	 * Contiene la representación en cadena del tipo Valor
	 */
	private String tipoValor = null;

	/**
	 * Contiene la representación en cadena del nombre corto de Emisora
	 */
	private String emisora = null;

	/**
	 * Contiene la representación en cadena de la serie
	 */
	private String serie = null;

	/**
	 * Contiene la representación en cadena del Estado de la Instuccion
	 */
	private String estadoInstruccion = null;

	/**
	 * Contiene la representación en cadena del nombre corto del tipo de
	 * instruccion
	 */
	private String tipoInstruccion = null;

	/** Contiene el mercado por el cual se realizará la consulta */
	private String papelMercado = DaliConstants.PAPEL_MERCADO_TODOS;

	/** DAO para la consulta de tipos de instrucciones */
	private TipoInstruccionDaliDAO tipoInstruccionDaliDAO;

	/** DAO para la consulta de estados de instrucciones */
	private EstadoInstruccionDaliDAO estadoInstruccionDAO;

	/** DAO para la consulta de las divisas */
	private DivisaDaliDAO divisaDAO;


	/** El número de registros encontrados para los reportes */
	private Integer registrosEncontrados = 0;
	/**
	 * Servicio para la confirmación de la operación.
	 */
	EnvioOperacionesService envioOperacionService = null;
	/**
	 * Indica si al consultar la proyeccion debe dejar bitacora o no
	 */
	private boolean debeDejarBitacora = false;
	/**
	 * Campos para los totales de la consulta
	 */
	long titulosTraspasante = 0;
	long titulosReceptor = 0;
	long netoTitulos = 0;
	double montoTraspasante = 0;
	double montoReceptor = 0;
	double netoMonto = 0;

	private Map<String, List<SelectItem>>  bovedasPorDivisaTipoInstruccion;

	/**
	 * Conjunto de isos Firmados
	 */
	private List<String> isosFirmados = null;
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

	/** Utileria para la firma digital */
	private CifradorDescifradorBlowfish cdb = new CifradorDescifradorBlowfish();

	private Boolean confirmacion;

	private List<String> tipoInstruccionesParaBoveda;
	
	private Date fechaHoraCierreOper;


	

	/**
	 * Cancela una operación.
	 * @return Nulo para que el control quede en la misma pantalla.
	 */
	public String cancelarOperacionMatch() {
		try {
			//Limpia los mensajes de usuario y la severidad
			mensajeUsuario = null;
			severidadMensaje = null;
			
			Map<String , String> map=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			String operacionesCancelar=map.get("daliForm:matchIdOperacionesCancelar");

			logger.debug(" request Map ["+map+"]");
			logger.debug(" operaciones a Cancelar ["+operacionesCancelar+"] ");

			StringTokenizer tokenizer = new StringTokenizer(operacionesCancelar, ",");

			Boolean usaFirma= Boolean.valueOf(map.get("firma"));
			String numeroSerie=map.get("numeroSerie");

			isosSinFirmar=new ArrayList<String>();
			isosFirmados = new ArrayList<String>();
			hashIso = new ArrayList<String>();

			AgenteVO agenteFirmado = new AgenteVO();
			agenteFirmado.setId(getInstitucionActual().getClaveTipoInstitucion());
			agenteFirmado.setFolio(getInstitucionActual().getFolioInstitucion());

			confirmacion=Boolean.FALSE;
			int var=1;
			while(tokenizer.hasMoreElements()) {
				String element=tokenizer.nextToken();
				if(StringUtils.isNotBlank(element)){
					BigInteger idBitacoraMatch = new BigInteger(element);
					logger.debug("actualizando idBitacoraMatch ["+idBitacoraMatch+"]");
					if (!usaFirma || StringUtils.isNotEmpty(numeroSerie)){
						String isoFirmado=null;
						String isoSinFirmar=map.get("isoSinFirmar"+var);
						String firma =map.get("isoFirmado"+var);

						if(StringUtils.isNotBlank(isoSinFirmar)
								&& StringUtils.isNotBlank(numeroSerie)
								&& StringUtils.isNotBlank(firma)){
							StringBuilder isoCompleto= new StringBuilder();
							isoCompleto.append(isoSinFirmar.replace("\r\n", "\n"))
							.append(numeroSerie).append("\n").append("{SHA1withRSA}").append(firma);
							isoFirmado=isoCompleto.toString();
						}
						var++;

						HashMap<String, String> datosAdicionales = new HashMap<String, String>();
						datosAdicionales.put(SeguridadConstants.USR_CREDENCIAL,
								(String)FacesContext.getCurrentInstance().getExternalContext().
								getSessionMap().get(SeguridadConstants.TICKET_SESION));

						envioOperacionService.actualizaEstadoInstruccionExpira(idBitacoraMatch, agenteFirmado, datosAdicionales, isoFirmado);
						
//						mensajeUsuario = "Se cancel\u00F3 la operaci\u00F3n con \u00E9xito.";
//						severidadMensaje = FacesMessage.SEVERITY_INFO;
					}
					else {
						//Valida la vigencia del certificado - 18-Agosto-2014 Pablo Balderas
						validarVigenciaCertificado();
						//Se genera el iso de la operacion
						String isoElem=	envioOperacionService.generaIso(idBitacoraMatch, agenteFirmado, null, null, Boolean.FALSE);
						isosSinFirmar.add(isoElem);
						hashIso.add(cdb.cipherHash(isoElem));
					}
				}
			}
			totalOperaciones =isosSinFirmar.size();

			if (!existeErrorEnInvocacion()) {
				if(StringUtils.isNotEmpty(mensajeUsuario) && null != severidadMensaje) { 
					addMessage(mensajeUsuario, severidadMensaje);
				}
			}
		}
		catch(BusinessException businessException) {
    		addMessage(businessException.getMessage(), FacesMessage.SEVERITY_ERROR);
    	}
		return null;
	}


	/**
	 * Confirma las operaciones para que hagan match.
	 * @return nulo para devolver el control a la misma pantalla.
	 */
	public String confirmaOperacionMatch() {
		try {
			//Limpia los mensajes de usuario y la severidad
			mensajeUsuario = null;
			severidadMensaje = null;
			
			Map<String , String> map=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

			String operacionesConfirmar=map.get("daliForm:matchIdOperacionesConfirmar");
			Boolean usaFirma= Boolean.valueOf(map.get("firma"));
			String numeroSerie=map.get("numeroSerie");
			String strFechaHoraCierreOper = map.get("daliForm:fechaHoraCierreOperInputDate");
			Date fechaHoraCierreOper = null;
			
			if(!StringUtils.isEmpty(strFechaHoraCierreOper)) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				try {
					fechaHoraCierreOper = sdf.parse(strFechaHoraCierreOper);
				} catch(Exception e) {
				}
			}

			isosSinFirmar=new ArrayList<String>();
			isosFirmados = new ArrayList<String>();
			hashIso = new ArrayList<String>();

			StringTokenizer tokenizer = new StringTokenizer(operacionesConfirmar, ",");

			AgenteVO agenteFirmado = new AgenteVO();
			agenteFirmado.setId(getInstitucionActual().getClaveTipoInstitucion());
			agenteFirmado.setFolio(getInstitucionActual().getFolioInstitucion());

			confirmacion=Boolean.TRUE;

			int var=1;
			while(tokenizer.hasMoreElements()){
				String elementBase=tokenizer.nextToken();
				if(StringUtils.isNotBlank(elementBase)){
					String elementArray [] = elementBase.split("-");
					String element = elementArray[0];
					BovedaDTO bovedaEfectivoDTO =null;
									
					if(elementArray.length>1){
					Long idBovedaEfec = Long.valueOf(elementArray[1]);
						bovedaEfectivoDTO = consultaCatalogos.buscarBovedaPorId(idBovedaEfec);	
					}				

					BigInteger idBitacoraMatch = new BigInteger(element);
					logger.debug("Confirmando idBitacoraMatch ["+idBitacoraMatch+"]");
					//aqui si no se usa firma se guarda la operacion
					// o se guarda la operacion si ya viene con firma
					if (!usaFirma || StringUtils.isNotEmpty(numeroSerie)){

						String isoFirmado=null;
						String isoSinFirmar=map.get("isoSinFirmar"+var);
						String firma =map.get("isoFirmado"+var);

						if(StringUtils.isNotBlank(isoSinFirmar)
								&& StringUtils.isNotBlank(numeroSerie)
								&& StringUtils.isNotBlank(firma)){
							StringBuilder isoCompleto= new StringBuilder();
							isoCompleto.append(isoSinFirmar.replace("\r\n", "\n"))
							.append(numeroSerie).append("\n").append("{SHA1withRSA}").append(firma);
							isoFirmado=isoCompleto.toString();
						}
						var++;

						HashMap<String, String> datosAdicionales = new HashMap<String, String>();
						datosAdicionales.put(SeguridadConstants.USR_CREDENCIAL,
								(String)FacesContext.getCurrentInstance().getExternalContext().
								getSessionMap().get(SeguridadConstants.TICKET_SESION));

						envioOperacionService.confirmaOperacionMatch(idBitacoraMatch,agenteFirmado, bovedaEfectivoDTO, datosAdicionales, isoFirmado, fechaHoraCierreOper);
//						mensajeUsuario = "Confirmacion realizada con exito.";
//						severidadMensaje = FacesMessage.SEVERITY_INFO;
					}
					else {
						//Valida la vigencia del certificado - 18-Agosto-2014 Pablo Balderas
						validarVigenciaCertificado();
						//Se genera el iso de la operacion
						String isoElem=	envioOperacionService.generaIso(idBitacoraMatch, agenteFirmado, bovedaEfectivoDTO, fechaHoraCierreOper, Boolean.TRUE);
						isosSinFirmar.add(isoElem);
						hashIso.add(cdb.cipherHash(isoElem));
					}
				}
			}
			totalOperaciones =isosSinFirmar.size();
			if (!existeErrorEnInvocacion()) {
				if(StringUtils.isNotEmpty(mensajeUsuario) && null != severidadMensaje) { 
					addMessage(mensajeUsuario, severidadMensaje);
				}
			}
		}
		catch(BusinessException businessException) {
    		addMessage(businessException.getMessage(), FacesMessage.SEVERITY_ERROR);
    	}
		return null;			
	}
	/**
	 * Método conveniente para la inicialización de la pantalla de Consulta de
	 * Estatus de Operaciones y Match
	 *
	 * @return este método siempre retorna <code>null</code>
	 */
	@SuppressWarnings("unused")
	public String getInit() {
		
		
		paginacion = new EstadoPaginacionDTO();
		paginacion.setRegistrosPorPagina(20);
		criterio.setFechaConcertacion(new Date());
		criterio.setFechaLiquidacion(new Date());
		
		institucionParticipante = getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion();
		bovedasPorDivisaTipoInstruccion = new HashMap<String,List<SelectItem>>();
		tipoInstruccionesParaBoveda = new ArrayList<String>();
		tipoInstruccionesParaBoveda.add("CORE");
		tipoInstruccionesParaBoveda.add("COVE");
		tipoInstruccionesParaBoveda.add("REPO");
		tipoInstruccionesParaBoveda.add("DVPC");
		for (String claveTipoInstruccion : tipoInstruccionesParaBoveda) {
			TipoInstruccionDTO tipoInstruccionDTO = consultaCatalogos.buscarTipoInstruccionPorClave(claveTipoInstruccion);
			for (SelectItem selectItem : consultaCatalogos.getOpcionesComboDivisaPorTipoInstruccion(tipoInstruccionDTO)) {
				DivisaDTO divisaDTO = (DivisaDTO) selectItem.getValue();
				Map<DivisaDTO, List<SelectItem>> mapa = new HashMap<DivisaDTO, List<SelectItem>>();
				bovedasPorDivisaTipoInstruccion.put(tipoInstruccionDTO.getNombreCorto()+"-"+divisaDTO.getClaveAlfabetica(),
						consultaCatalogos.getSelectItemsBovedasEfectivoPorDivisaTipoInstruccionCorto(divisaDTO, tipoInstruccionDTO));
			}
		}
		return null;
	}

	/**
	 * Implementación de un action listener para el cambio del combo estado de
	 * instrucción.
	 *
	 * @param e
	 *            el evento que disparó este action listener.
	 */
	public void cambioEstadoInstruccion(ActionEvent e) {
		criterio.setEstadoInstruccion(consultaCatalogos.buscarEstadoInstruccionPorId(criterio.getEstadoInstruccion().getIdEstadoInstruccion()));
	}

	/**
	 * Implementación de un action listener para el cambio del combo estado del
	 * select error
	 *
	 * @param e
	 *            el evento que disparó este action listener.
	 */
	public void cambioSelectError(ActionEvent e) {
		if (criterio.getError() != null) {
			ErrorDaliDTO error = consultaCatalogos.buscarErrorLiquidacionPorClaveError(criterio.getError().getClaveError());
			if (error == null) {
				error = new ErrorDaliDTO();
				error.setIdErrorDali(-1);
				error.setDescripcion(DaliConstants.DESCRIPCION_TODOS);
				error.setClaveError(StringUtils.EMPTY);
			}
			criterio.setError(error);
		}
	}



	/**
	 * Implementación de un action listener para el cambio del combo  boveda de efectivo
	 *
	 * @param e
	 *            el evento que disparó este action listener.
	 */
	public void cambioSelects() {

		if (criterio.getBovedaValores() != null) {
			BovedaDTO bovedaDTO = consultaCatalogos.buscarBovedaPorId(criterio.getBovedaValores().getId());
			if (bovedaDTO == null) {
				bovedaDTO = new BovedaDTO();
				bovedaDTO.setId(-1);
				bovedaDTO.setDescripcion(DaliConstants.DESCRIPCION_TODOS);
			}
			criterio.setBovedaValores(bovedaDTO);
		}

		if (criterio.getBovedaEfectivo() != null) {
			BovedaDTO bovedaDTO = consultaCatalogos.buscarBovedaPorId(criterio.getBovedaEfectivo().getId());
			if (bovedaDTO == null) {
				bovedaDTO = new BovedaDTO();
				bovedaDTO.setId(-1);
				bovedaDTO.setDescripcion(DaliConstants.DESCRIPCION_TODOS);
			}
			criterio.setBovedaEfectivo(bovedaDTO);
		}

		if (criterio.getDivisa() != null) {
			DivisaDTO divisaDTO = consultaCatalogos.buscarDivisaPorId(criterio.getDivisa().getId());
			if (divisaDTO == null) {
				divisaDTO = new DivisaDTO();
				divisaDTO.setId(-1);
				divisaDTO.setDescripcion(DaliConstants.DESCRIPCION_TODOS);
			}
			criterio.setDivisa(divisaDTO);
		}
		
		if(StringUtils.isNotBlank(criterio.getReferenciaPaquete()))
		{
			criterio.setReferenciaPaquete(criterio.getReferenciaPaquete().toUpperCase());
		}
	}

	/**
	 * Obtiene el valor del atributo registrosEncontrados
	 *
	 * @return el valor del atributo registrosEncontrados
	 */
	public Integer getRegistrosEncontrados() {
		return registrosEncontrados;
	}

	/**
	 * Establece el valor del atributo registrosEncontrados
	 *
	 * @param registrosEncontrados
	 *            el valor del atributo registrosEncontrados a establecer
	 */
	public void setRegistrosEncontrados(Integer registrosEncontrados) {
		this.registrosEncontrados = registrosEncontrados;
	}

	/**
	 * Implementación de action listener para el cambio del combo de tipos de
	 * mensaje.
	 *
	 * @param e
	 *            el evento que disparó este action listener.
	 */
	public void cambioTipoMensaje(ActionEvent e) {
		criterio.setTipoMensaje(consultaCatalogos.buscarTipoMensajePorId(criterio.getTipoMensaje().getIdTipoMensaje()));
	}

	/**
	 * Limpia los resultados de la consulta
	 *
	 * @param e
	 */
	public void limpiar(ActionEvent e) {
		paginacion = new EstadoPaginacionDTO();

		paginacion.setRegistrosPorPagina(20);
		criterio = new CriterioMatchOperacionesDTO();
		criterio.setFechaConcertacion(new Date());
		criterio.setFechaLiquidacion(new Date());
		criterio.setMercado(new MercadoDTO());
		this.papelMercado = null;
		criterio.getMercado().setClaveMercado("TODOS");
		criterio.getMercado().setDescripcion("TODOS");
		institucionContraparte = null;
		cuentaContraparte = null;
		consultaEjecutada = false;
		resultados = null;
		tipoValor = StringUtils.EMPTY;
		emisora = StringUtils.EMPTY;
		serie = StringUtils.EMPTY;
		tipoInstruccion = StringUtils.EMPTY;
		estadoInstruccion = StringUtils.EMPTY;

		isosSinFirmar=new ArrayList<String>();
		isosFirmados = new ArrayList<String>();
		hashIso = new ArrayList<String>();
	}

	/**
	 * método que atiende las solicitudes de autocomplete del catálogo de tipos
	 * de instrucción.
	 *
	 * @param prefijo
	 *            Criterio de búsqueda
	 * @return Lista con los tipos de instrucciones encontradas
	 */
	public List<TipoInstruccionDTO> buscarTipoInstruccionPorPrefijo(Object prefijo) {
		return tipoInstruccionDaliDAO.buscarTiposDeInstruccionPorIdsValores(prefijo.toString());
	}

	/**
	 * método que atiende las solicitudes de autocomplete del catálogo de tipos
	 * de instrucción.
	 *
	 * @param prefijo
	 *            Criterio de búsqueda
	 * @return Lista con los tipos de instrucciones encontradas
	 */
	public List<EstadoInstruccionDTO> buscarEstadoInstruccionPorPrefijo(Object prefijo) {
		return estadoInstruccionDAO.buscarEstadosInstruccionPorIds(prefijo.toString());
	}

	/**
	 * Busca en el catálogo de cuentas aquellas que pertenezcan a una
	 * institución dada y comiencen con un prefijo dado.
	 *
	 * @param idFolioInstitucion
	 *            el identificador y el folio concatenados de la institución a
	 *            consultar.
	 * @param prefijoCuenta
	 *            el prefijo de la cuenta a consultar.
	 * @return una lista con objetos tipo {@link CuentaDTO} los cuales cumplen
	 *         con el criterio de consulta. Una lista vacía en caso de no
	 *         existir coincidencias.
	 */
	private List<CuentaDTO> buscarCuentasDeInstitucionPorPrefijo(String idFolioInstitucion, String prefijoCuenta) {
		List<CuentaDTO> cuentas = new ArrayList<CuentaDTO>();

		if (StringUtils.isNotEmpty(idFolioInstitucion) && StringUtils.isNotEmpty(prefijoCuenta)) {
			InstitucionDTO institucion = consultaCatalogos.buscarInstitucionPorIdFolio(idFolioInstitucion);
			if (institucion != null) {
				CuentaDTO criterio = new CuentaDTO();
				criterio.setInstitucion(institucion);
				criterio.setTipoTenencia(new TipoTenenciaDTO());

				criterio.getTipoTenencia().setTipoCuenta(new TipoCuentaDTO());
				criterio.getTipoTenencia().getTipoCuenta().setId(TipoCuentaDTO.CUENTA_NOMBRADA);
				criterio.getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO());
				criterio.getTipoTenencia().getTipoNaturaleza().setId(String.valueOf(DaliConstants.VALOR_COMBO_TODOS));
				criterio.getTipoTenencia().setTipoCustodia(TipoCustodiaConstants.VALORES);
				String prefijoAjustado = "";
				if (prefijoCuenta != null) {
					prefijoAjustado = String.valueOf(prefijoCuenta).replace('*', '%');
				}
				criterio.setNumeroCuenta(institucion.getClaveTipoInstitucion() + institucion.getFolioInstitucion() + prefijoAjustado);

				cuentas = consultaCatalogos.getConsultaCuentaService().buscarCuentasPorFragmentoNumeroCuenta(criterio);
			}
		}

		return cuentas;
	}

	/**
	 * método que atiende las solicitudes de autocomplete del catálogo de
	 * cuentas de participante.
	 *
	 * @param prefijo
	 *            Criterio de búsqueda
	 * @return Lista con las cuentas encontradas
	 */
	public List<CuentaDTO> buscarCuentasParticipantePorPrefijo(Object prefijo) {

		return buscarCuentasDeInstitucionPorPrefijo(institucionParticipante, (String) prefijo);
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

		return buscarCuentasDeInstitucionPorPrefijo(institucionContraparte, (String) prefijo);
	}

	/**
	 * Busca instituciones en el catálogo cuyo nombre corto comience con el
	 * prefijo proporcionado.
	 *
	 * @param prefijo
	 *            el prefijo a consultar en la BD.
	 * @return una lista con objetos de tipo {@link InstitucionDTO} con todas
	 *         las coincidencias encontradas.
	 */
	public List<InstitucionDTO> buscarParticipante(Object value) {
		List<InstitucionDTO> resultado = null;
		List<TipoInstitucionDTO> institucion = new ArrayList<TipoInstitucionDTO>();
		List<InstitucionDTO> listaInstituciones = null;
		String prefijoAjustado = "";

		if (value != null) {
			prefijoAjustado = String.valueOf(value).trim().replace("*", "%");
			listaInstituciones = new ArrayList<InstitucionDTO>();

			institucion = consultaCatalogos.getConsultaTipoInstitucionService().consultaTipoInstitucionPorPrefijo(prefijoAjustado);
			resultado = consultaCatalogos.getConsultaInstitucionService().buscarInstitucionesPorPrefijo(prefijoAjustado);

			if (!institucion.isEmpty()) {

				InstitucionDTO ins = new InstitucionDTO();
				ins.setId(new Long(100) + institucion.get(0).getId());
				ins.setClaveTipoInstitucion(institucion.get(0).getClaveTipoInstitucion());
				ins.setNombreCorto(institucion.get(0).getDescripcion());

				listaInstituciones.add(ins);
			}

			for (InstitucionDTO institucionDTO : resultado) {
				listaInstituciones.add(institucionDTO);
			}
		}

		return listaInstituciones;
	}

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
			paginacion.getClass().getMethod(navegacion).invoke(paginacion);
		} catch (Exception ex) {
			logger.error("Error de invocación de método al navega por los resultados principales", ex);
		}
		ResultadoValidacionDTO resultado = crearCriterio();
		if (resultado.isValido()) {
			buscarOperaciones(resultado);
		} else {
			consultaEjecutada = false;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, resultado.getMensaje(), resultado.getMensaje()));
		}
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
		return consultaCatalogos.getConsultaTipoValorService().buscarTiposDeValoresPorMercadoYPrefijo(criterio.getMercado(), String.valueOf(prefijo));

	}

	/**
	 * método que atiende las solicitudes de autocomplete del catálogo de
	 * emisoras.
	 *
	 * @param prefijo
	 *            Criterio de búsqueda
	 * @return Lista con las emisoras encontradas
	 */
	public List<EmisoraDTO> buscarEmisorasPorPrefijo(Object prefijo) {
		String prefijoAjustado = "";
		if (prefijo != null) {
			prefijoAjustado = String.valueOf(prefijo).replace('*', '%');
		}
		return consultaCatalogos.getConsultaEmisoraService().buscarEmisorasPorPrefijo(prefijoAjustado);

	}

	/**
	 * método que atiende las solicitudes de autocomplete del campo
	 * origen.
	 *
	 * @param prefijo
	 *            Criterio de búsqueda
	 * @return Lista con los origenes encontrados
	 */
	public List<String> buscarOrigenPorPrefijo(Object prefijo) {
		String prefijoAjustado = "";
		if (prefijo != null) {
			prefijoAjustado = String.valueOf(prefijo).replace('*', '%');
		}
		return consultaCatalogos.getConsultaInstitucionService().buscarOrigenPorPrefijo(prefijoAjustado);

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
	 * Obtiene la descripción del atributo papelMercado.
	 *
	 * @return la descripción del atributo papelMercado.
	 */
	public String getDescripcionPapelMercado2() {
		String resultado = "";
		if (DaliConstants.PAPEL_MERCADO_TODOS.equals(papelMercado)) {
			resultado = DaliConstants.DESCRIPCION_TODOS;
		} else {
			if (DaliConstants.PAPEL_MERCADO_DINERO.equals(papelMercado)) {
				if(criterio.getMercado()  != null && criterio.getMercado().getClaveMercado() != null
						&& DaliConstants.ID_MERCADO_DINERO  == criterio.getMercado().getId()) {
					resultado = DaliConstants.DESCRIPCION_TODOS;
				} else {
					resultado = criterio.getMercado().getClaveMercado();
				}
			} else {
				resultado = DaliConstants.DESCRIPCION_TODOS;
			}
		}

		return resultado;
	}

	/**
	 * Obtiene la descripción del atributo papelMercado.
	 *
	 * @return la descripción del atributo papelMercado.
	 */
	public String getDescripcionPapelMercadoTitle2() {
		String resultado = "";
		if (DaliConstants.PAPEL_MERCADO_TODOS.equals(papelMercado)) {
			resultado = DaliConstants.DESCRIPCION_TODOS;
		} else {
			if (DaliConstants.PAPEL_MERCADO_DINERO.equals(papelMercado)) {
				if(criterio.getMercado()  != null && criterio.getMercado().getClaveMercado() != null
						&& DaliConstants.ID_MERCADO_DINERO  == criterio.getMercado().getId()) {
					resultado = DaliConstants.DESCRIPCION_TODOS;
				} else {
					resultado = criterio.getMercado().getDescripcion();
				}
			} else {
				resultado = DaliConstants.DESCRIPCION_TODOS;
			}
		}

		return resultado;
	}

	/**
	 * Completa el objeto de criterios con los valores capturados por el usuario
	 */
	public ResultadoValidacionDTO crearCriterio() {

		ResultadoValidacionDTO resultado = new ResultadoValidacionDTO();
		resultado.setValido(true);

		cambioSelects();

		if (DaliConstants.ID_MERCADO_DINERO != criterio.getMercado().getId()) {
			criterio.setMercado(consultaCatalogos.buscarMercadoPorId(criterio.getMercado().getId()));
		} else {
			criterio.getMercado().setClaveMercado(DaliConstants.DESCRIPCION_MERCADO_DINERO);
		}

		if (StringUtils.isNotBlank(estadoInstruccion)) {
			criterio.setEstadoInstruccion(consultaCatalogos.buscarEstadoInstruccionPorClave(estadoInstruccion));
			if (criterio.getEstadoInstruccion().getIdEstadoInstruccion() == DaliConstants.VALOR_COMBO_TODOS) {
				resultado.setValido(false);
				resultado.setMensaje("El estado de la instrucci\u00F3n especificado no existe.");
			}
		}
		else {
			criterio.setEstadoInstruccion(consultaCatalogos.buscarEstadoInstruccionPorClave(estadoInstruccion));
		}


		if (StringUtils.isNotBlank(criterio.getDivisa().getDescripcion())) {
			criterio.setDivisa(consultaCatalogos.buscarDivisaPorId(Long.valueOf((criterio.getDivisa().getId()))));
//			 if (criterio.getDivisa().getId() == DaliConstants.VALOR_COMBO_TODOS) {
//				resultado.setValido(false);
//				resultado.setMensaje("La divisa especificada no existe.");
//			}
		}

		//Se valida que al menos se capture una de las fechas.
		if(criterio.getFechaConcertacion() == null && criterio.getFechaLiquidacion() == null) {
			resultado.setValido(false);
			resultado.setMensaje("Al menos una de las fechas es requerida");
		}

		if (StringUtils.isNotBlank(tipoValor)) {
			criterio.getEmision().setTipoValor(consultaCatalogos.buscarTipoValorPorClave(tipoValor));
			if (criterio.getEmision().getTipoValor().getId() == DaliConstants.VALOR_COMBO_TODOS) {
				resultado.setValido(false);
				resultado.setMensaje("El tipo de valor especificado no existe.");
			}
		} else {
			criterio.getEmision().setTipoValor(consultaCatalogos.buscarTipoValorPorClave(tipoValor));
		}

		if (StringUtils.isNotBlank(tipoInstruccion)) {
			criterio.setTipoInstruccion(consultaCatalogos.buscarTipoInstruccionPorClave(tipoInstruccion));
			if (criterio.getTipoInstruccion().getIdTipoInstruccion() == DaliConstants.VALOR_COMBO_TODOS) {
				resultado.setValido(false);
				resultado.setMensaje("El tipo de instrucci\u00F3n especificado no existe.");
			}
		} else {
			criterio.setTipoInstruccion(consultaCatalogos.buscarTipoInstruccionPorClave(tipoInstruccion));
		}
		criterio.setTipoMensaje(consultaCatalogos.buscarTipoMensajePorId(criterio.getTipoMensaje().getIdTipoMensaje()));
		if (StringUtils.isNotBlank(emisora)) {
			criterio.getEmision().setEmisora(consultaCatalogos.buscarEmisoraPorNombreCorto(emisora));
			if (criterio.getEmision().getEmisora().getId() == DaliConstants.VALOR_COMBO_TODOS) {
				resultado.setValido(false);
				resultado.setMensaje("La emisora especificada no existe.");
			}
		} else {
			criterio.getEmision().setEmisora(consultaCatalogos.buscarEmisoraPorNombreCorto(emisora));
		}
		if (StringUtils.isNotBlank(serie)) {
			criterio.getEmision().getSerie().setSerie(serie);
		}
		criterio.setCuentaContraparte(new CuentaDTO());
		criterio.getCuentaContraparte().setCuenta(cuentaContraparte);
		// Se eliminan las comas cuando se pega la cantidad o el monto.
		if (criterio.getMonto() != null) {
			criterio.setMonto(criterio.getMonto().replaceAll(",", ""));
		}
		if (criterio.getCantidad() != null) {
			criterio.setCantidad(criterio.getCantidad().replaceAll(",", ""));
		}

		if (StringUtils.isNotEmpty(institucionContraparte)) {

			if (institucionContraparte.length() >= 5) {
				this.institucionContraparte = institucionContraparte.substring(0, 5);
				criterio.setInstitucionContraparte(consultaCatalogos.buscarInstitucionPorIdFolio(this.institucionContraparte));
				if (criterio.getInstitucionContraparte() == null) {
					resultado.setValido(false);
					resultado.setMensaje("La instituci\u00F3n especificada para la contraparte no existe.");
				}
			} else {
				criterio.setInstitucionContraparte(new InstitucionDTO());
				criterio.getInstitucionContraparte().setId(-1);
				criterio.getInstitucionContraparte().setClaveTipoInstitucion(institucionContraparte);
				List<TipoInstitucionDTO> tiposInstitucion = consultaCatalogos.getConsultaTipoInstitucionService().consultaTipoInstitucionPorPrefijo(
						institucionContraparte);
				if (tiposInstitucion == null || tiposInstitucion.isEmpty() || !tiposInstitucion.get(0).getClaveTipoInstitucion().equals(institucionContraparte)) {
					resultado.setValido(false);
					resultado.setMensaje("El tipo de instituci\u00F3n especificada para la contraparte no existe.");
				}
			}
		} else {
			criterio.setInstitucionContraparte(new InstitucionDTO());
			criterio.getInstitucionContraparte().setId(DaliConstants.VALOR_COMBO_TODOS);
		}

		if (StringUtils.isNotEmpty(institucionParticipante)) {

			if (institucionParticipante.length() >= 5) {
				this.institucionParticipante = institucionParticipante.substring(0, 5);
				criterio.setInstitucionParticipante(consultaCatalogos.buscarInstitucionPorIdFolio(this.institucionParticipante));
				if (criterio.getInstitucionParticipante() == null) {
					resultado.setValido(false);
					resultado.setMensaje("La instituci\u00F3n especificada para el participante no existe.");
				}
			} else {
				criterio.setInstitucionParticipante(new InstitucionDTO());
				criterio.getInstitucionParticipante().setId(-1);
				criterio.getInstitucionParticipante().setClaveTipoInstitucion(institucionParticipante);
				List<TipoInstitucionDTO> tiposInstitucion = consultaCatalogos.getConsultaTipoInstitucionService().consultaTipoInstitucionPorPrefijo(
						institucionParticipante);
				if (tiposInstitucion == null || tiposInstitucion.isEmpty()
						|| !tiposInstitucion.get(0).getClaveTipoInstitucion().equals(institucionParticipante)) {
					resultado.setValido(false);
					resultado.setMensaje("El tipo de instituci\u00F3n especificada para el participante no existe.");
				}
			}
		} else {
			criterio.setInstitucionParticipante(new InstitucionDTO());
			criterio.getInstitucionParticipante().setId(DaliConstants.VALOR_COMBO_TODOS);
		}

		return resultado;
	}

    /**
     * Arma un objeto de criterios de exportacion en plano.
     * @return Un objeto de tipo CriterioMatchOperacionesExportacionDTO.
     */
    public CriterioMatchOperacionesExportacionDTO obtenerCriterioExportacion() {
        //this.crearCriterio();
        CriterioMatchOperacionesExportacionDTO newcrit = new CriterioMatchOperacionesExportacionDTO();

        if (this.criterio.getBovedaValores() != null) {
            newcrit.setIdBovedaValores(this.criterio.getBovedaValores().getId());
            newcrit.setNombreCortoBovedaValores(this.criterio.getBovedaValores().getNombreCorto());
        }
        if (this.criterio.getBovedaEfectivo() != null) {
            newcrit.setIdBovedaEfectivo(this.criterio.getBovedaEfectivo().getId());
            newcrit.setNombreCortoBovedaEfectivo(this.criterio.getBovedaEfectivo().getNombreCorto());
        }
        if (this.criterio.getDivisa() != null) {
            newcrit.setIdDivisa(this.criterio.getDivisa().getId());
            newcrit.setClaveAlfabetica(this.criterio.getDivisa().getClaveAlfabetica());
        }
        newcrit.setReferenciaPaquete(this.criterio.getReferenciaPaquete());
        if (this.criterio.getMercado() != null) {
            newcrit.setIdMercado(this.criterio.getMercado().getId());
            newcrit.setClaveMercado(this.criterio.getMercado().getClaveMercado());
        }
        if (this.criterio.getEstadoInstruccion() != null) {
            newcrit.setIdEstadoInstruccion(this.criterio.getEstadoInstruccion().getIdEstadoInstruccion());
        }
        newcrit.setFechaConcertacion(this.criterio.getFechaConcertacion());
        newcrit.setFechaLiquidacion(this.criterio.getFechaLiquidacion());
        if (this.criterio.getEmision() != null) {
            if (this.criterio.getEmision().getTipoValor() != null) {
                newcrit.setIdTipoValor(this.criterio.getEmision().getTipoValor().getId());
                newcrit.setClaveTipoValor(this.criterio.getEmision().getTipoValor().getClaveTipoValor());
            }
            if (this.criterio.getEmision().getEmisora() != null) {
                newcrit.setIdEmisora(this.criterio.getEmision().getEmisora().getId());
                newcrit.setDescripcionEmisora(this.criterio.getEmision().getEmisora().getDescripcion());
            }
            if (this.criterio.getEmision().getSerie() != null) {
                newcrit.setSerie(StringUtils.upperCase(this.criterio.getEmision().getSerie().getSerie()));
            }
        }
        if (this.criterio.getTipoInstruccion() != null) {
            newcrit.setIdTipoInstruccion(this.criterio.getTipoInstruccion().getIdTipoInstruccion());
            newcrit.setNombreCortoTipoInstruccion(this.criterio.getTipoInstruccion().getNombreCorto());
        }
        if (this.criterio.getTipoMensaje() != null) {
            newcrit.setIdTipoMensaje(this.criterio.getTipoMensaje().getIdTipoMensaje());
            newcrit.setClaveTipoMensaje(this.criterio.getTipoMensaje().getClaveTipoMensaje());
        }
        if (this.criterio.getCuentaContraparte() != null) {
            newcrit.setCuentaContraparte(this.criterio.getCuentaContraparte().getCuenta());
        }
        newcrit.setMonto(this.criterio.getMonto());
        newcrit.setCantidad(this.criterio.getCantidad());
        if (this.criterio.getInstitucionContraparte() != null) {
            newcrit.setIdInstitucionContraparte(this.criterio.getInstitucionContraparte().getId());
            newcrit.setClaveTipoInstitucionContraparte(this.criterio.getInstitucionContraparte().getClaveTipoInstitucion());
            newcrit.setFolioInstitucionContraparte(this.criterio.getInstitucionContraparte().getFolioInstitucion());
        }
        if (this.criterio.getInstitucionParticipante() != null) {
            newcrit.setIdInstitucionParticipante(this.criterio.getInstitucionParticipante().getId());
            newcrit.setClaveTipoInstitucionParticipante(this.criterio.getInstitucionParticipante().getClaveTipoInstitucion());
            newcrit.setFolioInstitucionParticipante(this.criterio.getInstitucionParticipante().getFolioInstitucion());
        }
        newcrit.setRol(this.criterio.getRol());
        if (criterio.getCuentaParticipante() != null) {
            newcrit.setCuentaParticipante(criterio.getCuentaParticipante().getCuenta());
        }
        newcrit.setFolioUsuario(criterio.getFolioUsuario());
        newcrit.setRemitente(criterio.isRemitente());
        newcrit.setOrigen(criterio.getOrigen());
        if (criterio.getError() != null) {
            newcrit.setClaveError(criterio.getError().getClaveError());
        }
        newcrit.setFolioControl(criterio.getFolioControl());

        return newcrit;
    }

	/**
	 * Ejecuta la consulta de búsqueda de operaciones y match para un conjunto
	 * de criterios solicitados
	 * @param e Action Event generado
	 */
	public void buscarMatchYOperaciones() {
		isosSinFirmar=new ArrayList<String>();
		isosFirmados = new ArrayList<String>();
		hashIso = new ArrayList<String>();		
		ResultadoValidacionDTO resultado = crearCriterio();
		if (resultado.isValido()) {
			criterio.getEmision().getSerie().setSerie(StringUtils.upperCase(criterio.getEmision().getSerie().getSerie()));
			incializarEstadoPaginacion(consultaEstatusOperacionesMatchService.obtenerProyeccionConsultaOperacionesValor(criterio, debeDejarBitacora));
			debeDejarBitacora = false;
			buscarOperaciones(resultado);
		}
		else {
			consultaEjecutada = false;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, resultado.getMensaje(), resultado.getMensaje()));
		}
	}

	/**
	 * Busca las operaciones de match e instrucciones.
	 */
	private void buscarOperaciones(ResultadoValidacionDTO resultado) {

		consultaEjecutada = true;
		Map<Object, Object> resultadosExtras = new HashMap<Object, Object>();
		CriterioMatchOperacionesExportacionDTO newcriterio = this.obtenerCriterioExportacion();

		//resultados = consultaEstatusOperacionesMatchService.consultarOperacionesValor(criterio, paginacion, resultadosExtras);
		resultados = consultaEstatusOperacionesMatchService.getOperacionesValorConVistas(newcriterio, paginacion, resultadosExtras);

		titulosTraspasante = ((Number) resultadosExtras.get(ConsultaEstatusOperacionesMatchService.TOTAL_TITULOS_TRASPASANTE)).longValue();
		titulosReceptor = ((Number) resultadosExtras.get(ConsultaEstatusOperacionesMatchService.TOTAL_TITULOS_RECEPTOR)).longValue();
		netoTitulos = titulosTraspasante - titulosReceptor;

		montoTraspasante = ((Number) resultadosExtras.get(ConsultaEstatusOperacionesMatchService.TOTAL_MONTO_TRASPASANTE)).doubleValue();
		montoReceptor = ((Number) resultadosExtras.get(ConsultaEstatusOperacionesMatchService.TOTAL_MONTO_RECEPTOR)).doubleValue();

		netoMonto = montoTraspasante - montoReceptor;

	}

	/**
	 * Obtiene los elementos SelectItem para el combo de mercado
	 *
	 * @return los elementos SelectItem para el combo de mercado
	 */
	public List<SelectItem> getSelectItemsMercado() {
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
	 * método para generar un reporte con formato XLS o PDF
	 *
	 * @return cadena de para ejecutar el action para genera el reporte
	 */
	public void generarReportes(ActionEvent e) {
		
		reiniciarEstadoPeticion();
		
		crearCriterio();

		incializarEstadoPaginacion(consultaEstatusOperacionesMatchService.obtenerProyeccionConsultaOperacionesValor(criterio, false));
		Map<Object, Object> resultadosExtras = new HashMap<Object, Object>();
		resultados = consultaEstatusOperacionesMatchService.consultarOperacionesValor(criterio, null, resultadosExtras);

		titulosTraspasante = ((Number) resultadosExtras.get(ConsultaEstatusOperacionesMatchService.TOTAL_TITULOS_TRASPASANTE)).longValue();
		titulosReceptor = ((Number) resultadosExtras.get(ConsultaEstatusOperacionesMatchService.TOTAL_TITULOS_RECEPTOR)).longValue();
		netoTitulos = titulosTraspasante - titulosReceptor;

		montoTraspasante = ((Number) resultadosExtras.get(ConsultaEstatusOperacionesMatchService.TOTAL_MONTO_TRASPASANTE)).doubleValue();
		montoReceptor = ((Number) resultadosExtras.get(ConsultaEstatusOperacionesMatchService.TOTAL_MONTO_RECEPTOR)).doubleValue();

		netoMonto = montoTraspasante - montoReceptor;
		registrosEncontrados = resultados.size();
	}

	/**
	 * Obtiene el campo criterio
	 *
	 * @return criterio
	 */
	public CriterioMatchOperacionesDTO getCriterio() {
		return criterio;
	}

	/**
	 * Asigna el campo criterio
	 *
	 * @param criterio
	 *            el valor de criterio a asignar
	 */
	public void setCriterio(CriterioMatchOperacionesDTO criterio) {
		this.criterio = criterio;
	}

	/**
	 * Obtiene el campo consultaCatalogosFacade
	 *
	 * @return consultaCatalogosFacade
	 */
	public ConsultaCatalogosFacade getConsultaCatalogos() {
		return consultaCatalogos;
	}

	/**
	 * Asigna el campo consultaCatalogosFacade
	 *
	 * @param consultaCatalogosFacade
	 *            el valor de consultaCatalogosFacade a asignar
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
	 * Obtiene el campo consultaEstatusOperacionesMatchService
	 *
	 * @return consultaEstatusOperacionesMatchService
	 */
	public ConsultaEstatusOperacionesMatchService getConsultaEstatusOperacionesMatchService() {
		return consultaEstatusOperacionesMatchService;
	}

	/**
	 * Asigna el campo consultaEstatusOperacionesMatchService
	 *
	 * @param consultaEstatusOperacionesMatchService
	 *            el valor de consultaEstatusOperacionesMatchService a asignar
	 */
	public void setConsultaEstatusOperacionesMatchService(ConsultaEstatusOperacionesMatchService consultaEstatusOperacionesMatchService) {
		this.consultaEstatusOperacionesMatchService = consultaEstatusOperacionesMatchService;
	}

	/**
	 * Obtiene el campo resultados
	 *
	 * @return resultados
	 */
	public List<OperacionValorMatchDTO> getResultados() {

		return resultados;
	}

	/**
	 * Asigna el campo resultados
	 *
	 * @param resultados
	 *            el valor de resultados a asignar
	 */
	public void setResultados(List<OperacionValorMatchDTO> resultados) {
		this.resultados = resultados;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.indeval.portaldali.presentation.controller.common.ControllerBase#ejecutarConsultaReporte()
	 */
	@Override
	protected Collection<? extends Object> ejecutarConsultaReporte() {
		crearCriterio();
		return consultaEstatusOperacionesMatchService.consultarOperacionesValor(criterio, null, null);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.indeval.portaldali.presentation.controller.common.ControllerBase#getNombreReporte()
	 */
	@Override
	protected String getNombreReporte() {

		return "operacionMatch";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.indeval.portaldali.presentation.controller.common.ControllerBase#llenarParametros()
	 */
	@Override
	protected Map<String, Object> llenarParametros() {
		Map<String, Object> params = new HashMap<String, Object>();
		crearCriterio();
		params.put("criterio", criterio);

		return params;
	}

	/**
	 * Obtiene el campo titulosTraspasante
	 *
	 * @return titulosTraspasante
	 */
	public long getTitulosTraspasante() {
		return titulosTraspasante;
	}

	/**
	 * Asigna el campo titulosTraspasante
	 *
	 * @param titulosTraspasante
	 *            el valor de titulosTraspasante a asignar
	 */
	public void setTitulosTraspasante(long titulosTraspasante) {
		this.titulosTraspasante = titulosTraspasante;
	}

	/**
	 * Obtiene el campo titulosReceptor
	 *
	 * @return titulosReceptor
	 */
	public long getTitulosReceptor() {
		return titulosReceptor;
	}

	/**
	 * Asigna el campo titulosReceptor
	 *
	 * @param titulosReceptor
	 *            el valor de titulosReceptor a asignar
	 */
	public void setTitulosReceptor(long titulosReceptor) {
		this.titulosReceptor = titulosReceptor;
	}

	/**
	 * Obtiene el campo netoTitulos
	 *
	 * @return netoTitulos
	 */
	public long getNetoTitulos() {
		return netoTitulos;
	}

	/**
	 * Asigna el campo netoTitulos
	 *
	 * @param netoTitulos
	 *            el valor de netoTitulos a asignar
	 */
	public void setNetoTitulos(long netoTitulos) {
		this.netoTitulos = netoTitulos;
	}

	/**
	 * Obtiene el campo montoTraspasante
	 *
	 * @return montoTraspasante
	 */
	public double getMontoTraspasante() {
		return montoTraspasante;
	}

	/**
	 * Asigna el campo montoTraspasante
	 *
	 * @param montoTraspasante
	 *            el valor de montoTraspasante a asignar
	 */
	public void setMontoTraspasante(double montoTraspasante) {
		this.montoTraspasante = montoTraspasante;
	}

	/**
	 * Obtiene el campo montoReceptor
	 *
	 * @return montoReceptor
	 */
	public double getMontoReceptor() {
		return montoReceptor;
	}

	/**
	 * Asigna el campo montoReceptor
	 *
	 * @param montoReceptor
	 *            el valor de montoReceptor a asignar
	 */
	public void setMontoReceptor(double montoReceptor) {
		this.montoReceptor = montoReceptor;
	}

	/**
	 * Obtiene el campo netoMonto
	 *
	 * @return netoMonto
	 */
	public double getNetoMonto() {
		return netoMonto;
	}

	/**
	 * Asigna el campo netoMonto
	 *
	 * @param netoMonto
	 *            el valor de netoMonto a asignar
	 */
	public void setNetoMonto(double netoMonto) {
		this.netoMonto = netoMonto;
	}

	/**
	 * @return the institucionContraparte
	 */
	public String getInstitucionContraparte() {
		return institucionContraparte;
	}

	/**
	 * @param institucionContraparte
	 *            the institucionContraparte to set
	 */
	public void setInstitucionContraparte(String institucionContraparte) {
		this.institucionContraparte = institucionContraparte;
	}

	/**
	 * @return the cuentaContraparte
	 */
	public String getCuentaContraparte() {
		return cuentaContraparte;
	}

	/**
	 * @param cuentaContraparte
	 *            the cuentaContraparte to set
	 */
	public void setCuentaContraparte(String cuentaContraparte) {
		this.cuentaContraparte = cuentaContraparte;
	}

	/**
	 * @return the institucionParticipante
	 */
	public String getInstitucionParticipante() {
		return institucionParticipante;
	}

	/**
	 * @param institucionParticipante
	 *            the institucionParticipante to set
	 */
	public void setInstitucionParticipante(String institucionParticipante) {
		this.institucionParticipante = institucionParticipante;
	}

	/**
	 * @return the cuentaParticipante
	 */
	public String getCuentaParticipante() {
		return cuentaParticipante;
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
	 * @param cuentaParticipante
	 *            serie the cuentaParticipante to set
	 */
	public void setCuentaParticipante(String cuentaParticipante) {
		this.cuentaParticipante = cuentaParticipante;
	}

	/**
	 * Obtiene el valor del atributo tipoInstruccionDaliDAO
	 *
	 * @return el valor del atributo tipoInstruccionDaliDAO
	 */
	public TipoInstruccionDaliDAO getTipoInstruccionDAO() {
		return tipoInstruccionDaliDAO;
	}

	/**
	 * Establece el valor del atributo tipoInstruccionDaliDAO
	 *
	 * @param tipoInstruccionDaliDAO
	 *            el valor del atributo tipoInstruccionDaliDAO a establecer
	 */
	public void setTipoInstruccionDAO(TipoInstruccionDaliDAO tipoInstruccionDaliDAO) {
		this.tipoInstruccionDaliDAO = tipoInstruccionDaliDAO;
	}

	/**
	 * Obtiene el valor del atributo tipoInstruccionDaliDAO
	 *
	 * @return el valor del atributo tipoInstruccionDaliDAO
	 */
	public TipoInstruccionDaliDAO getTipoInstruccionDaliDAO() {
		return tipoInstruccionDaliDAO;
	}

	/**
	 * Establece el valor del atributo tipoInstruccionDaliDAO
	 *
	 * @param tipoInstruccionDaliDAO
	 *            el valor del atributo tipoInstruccionDaliDAO a establecer
	 */
	public void setTipoInstruccionDaliDAO(TipoInstruccionDaliDAO tipoInstruccionDaliDAO) {
		this.tipoInstruccionDaliDAO = tipoInstruccionDaliDAO;
	}

	/**
	 * Obtiene el valor del atributo estadoInstruccionDAO
	 *
	 * @return el valor del atributo estadoInstruccionDAO
	 */
	public EstadoInstruccionDaliDAO getEstadoInstruccionDAO() {
		return estadoInstruccionDAO;
	}

	/**
	 * Establece el valor del atributo estadoInstruccionDAO
	 *
	 * @param estadoInstruccionDAO
	 *            el valor del atributo estadoInstruccionDAO a establecer
	 */
	public void setEstadoInstruccionDAO(EstadoInstruccionDaliDAO estadoInstruccionDAO) {
		this.estadoInstruccionDAO = estadoInstruccionDAO;
	}
	public DivisaDaliDAO getDivisaDAO() {
		return divisaDAO;
	}
	public void setDivisaDAO(DivisaDaliDAO divisaDAO) {
		this.divisaDAO = divisaDAO;
	}

	/**
	 * Obtiene el valor del atributo envioOperacionService
	 *
	 * @return el valor del atributo envioOperacionService
	 */
	public EnvioOperacionesService getEnvioOperacionService() {
		return envioOperacionService;
	}

	/**
	 * Establece el valor del atributo envioOperacionService
	 *
	 * @param envioOperacionService
	 *            el valor del atributo envioOperacionService a establecer
	 */
	public void setEnvioOperacionService(EnvioOperacionesService envioOperacionService) {
		this.envioOperacionService = envioOperacionService;
	}

	/**
	 * Obtiene el valor del atributo tipoValor
	 *
	 * @return el valor del atributo tipoValor
	 */
	public String getTipoValor() {
		return tipoValor;
	}

	/**
	 * Establece el valor del atributo tipoValor
	 *
	 * @param tipoValor
	 *            el valor del atributo tipoValor a establecer
	 */
	public void setTipoValor(String tipoValor) {
		this.tipoValor = tipoValor;
	}

	/**
	 * Obtiene el valor del atributo emisora
	 *
	 * @return el valor del atributo emisora
	 */
	public String getEmisora() {
		return emisora;
	}

	/**
	 * Establece el valor del atributo emisora
	 *
	 * @param emisora
	 *            el valor del atributo emisora a establecer
	 */
	public void setEmisora(String emisora) {
		this.emisora = emisora;
	}

	/**
	 * Obtiene el valor del atributo serie
	 *
	 * @return el valor del atributo serie
	 */
	public String getSerie() {
		return serie;
	}

	/**
	 * Establece el valor del atributo serie
	 *
	 * @param serie
	 *            el valor del atributo serie a establecer
	 */
	public void setSerie(String serie) {
		this.serie = serie;
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

	/**
	 * Obtiene el valor del atributo estadoInstruccion
	 *
	 * @return el valor del atributo estadoInstruccion
	 */
	public String getEstadoInstruccion() {
		return estadoInstruccion;
	}

	/**
	 * Establece el valor del atributo estadoInstruccion
	 *
	 * @param estadoInstruccion
	 *            el valor del atributo estadoInstruccion a establecer
	 */
	public void setEstadoInstruccion(String estadoInstruccion) {
		this.estadoInstruccion = estadoInstruccion;
	}

	/**
	 * @return the debeDejarBitacora
	 */
	public boolean isDebeDejarBitacora() {
		return debeDejarBitacora;
	}

	/**
	 * @param debeDejarBitacora the debeDejarBitacora to set
	 */
	public void setDebeDejarBitacora(boolean debeDejarBitacora) {
		this.debeDejarBitacora = debeDejarBitacora;
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

	public List<String> getHashIso() {
		return hashIso;
	}

	public void setHashIso(List<String> hashIso) {
		this.hashIso = hashIso;
	}

	public int getTotalOperaciones() {
		return totalOperaciones;
	}

	public void setTotalOperaciones(int totalOperaciones) {
		this.totalOperaciones = totalOperaciones;
	}

	public Boolean getConfirmacion() {
		return confirmacion;
	}

	public void setConfirmacion(Boolean confirmacion) {
		this.confirmacion = confirmacion;
	}

	public Date getFechaHoraCierreOper() {
		return fechaHoraCierreOper;
	}

	public void setFechaHoraCierreOper(Date fechaHoraCierreOper) {
		this.fechaHoraCierreOper = fechaHoraCierreOper;
	}

	/**
	 * Obtiene el valor del atributo tipoInstruccionesParaBoveda.
	 *
	 * @return el valor del atributo tipoInstruccionesParaBoveda.
	 */
	public List<String> getTipoInstruccionesParaBoveda() {
		return tipoInstruccionesParaBoveda;
	}

	/**
	 * Establece el valor del atributo tipoInstruccionesParaBoveda.
	 *
	 * @param tipoInstruccionesParaBoveda el valor del atributo tipoInstruccionesParaBoveda a establecer.
	 */
	public void setTipoInstruccionesParaBoveda(
			List<String> tipoInstruccionesParaBoveda) {
		this.tipoInstruccionesParaBoveda = tipoInstruccionesParaBoveda;
	}

	/**
	 * Obtiene el valor del atributo bovedasPorDivisaTipoInstruccion.
	 *
	 * @return el valor del atributo bovedasPorDivisaTipoInstruccion.
	 */
	public Map<String, List<SelectItem>> getBovedasPorDivisaTipoInstruccion() {
		return bovedasPorDivisaTipoInstruccion;
	}

	/**
	 * Establece el valor del atributo bovedasPorDivisaTipoInstruccion.
	 *
	 * @param bovedasPorDivisaTipoInstruccion el valor del atributo bovedasPorDivisaTipoInstruccion a establecer.
	 */
	public void setBovedasPorDivisaTipoInstruccion(
			Map<String, List<SelectItem>> bovedasPorDivisaTipoInstruccion) {
		this.bovedasPorDivisaTipoInstruccion = bovedasPorDivisaTipoInstruccion;
	}



}

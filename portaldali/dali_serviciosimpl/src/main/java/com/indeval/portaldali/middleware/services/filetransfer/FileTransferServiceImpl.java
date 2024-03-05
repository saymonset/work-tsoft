/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.filetransfer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.util.message.MessageResolver;
import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.CuentaEfectivoDTO;
import com.indeval.portaldali.middleware.dto.CuentaNombradaDTO;
import com.indeval.portaldali.middleware.dto.CuentaRetiroEfectivoDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.EmisionDTO;
import com.indeval.portaldali.middleware.dto.EstadoInstruccionDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.TipoCuentaDTO;
import com.indeval.portaldali.middleware.dto.TipoInstruccionDTO;
import com.indeval.portaldali.middleware.dto.TipoNaturalezaDTO;
import com.indeval.portaldali.middleware.dto.criterio.CriterioCuentaEfectivoDTO;
import com.indeval.portaldali.middleware.services.common.ValidacionService;
import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;
import com.indeval.portaldali.middleware.services.common.constants.TipoCuentaConstants;
import com.indeval.portaldali.middleware.services.common.constants.TipoCustodiaConstants;
import com.indeval.portaldali.middleware.services.filetransfer.util.Constantes;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessDataException;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import com.indeval.portaldali.middleware.services.modelo.util.UtilsVOCatalogo;
import com.indeval.portaldali.middleware.services.tesoreria.AdmonRetirosEfectivoService;
import com.indeval.portaldali.middleware.services.tesoreria.cuentas.AdministracionCuentasRetiroService;
import com.indeval.portaldali.middleware.services.util.UtilServices;
import com.indeval.portaldali.middleware.services.util.UtilsDaliVO;
import com.indeval.portaldali.persistence.dao.common.BovedaDaliDAO;
import com.indeval.portaldali.persistence.dao.common.CuentaDaliDAO;
import com.indeval.portaldali.persistence.dao.common.DateUtilsDao;
import com.indeval.portaldali.persistence.dao.common.DepositanteValidoBanxicoDao;
import com.indeval.portaldali.persistence.dao.common.DiaInhabilDaliDao;
import com.indeval.portaldali.persistence.dao.common.DivisaDaliDAO;
import com.indeval.portaldali.persistence.dao.common.EmisionDaliDAO;
import com.indeval.portaldali.persistence.dao.common.EstadoInstruccionDaliDAO;
import com.indeval.portaldali.persistence.dao.common.InstitucionDaliDAO;
import com.indeval.portaldali.persistence.dao.common.InstrumentoDaliDao;
import com.indeval.portaldali.persistence.dao.conciliacion.OperacionNombradaDao;
import com.indeval.portaldali.persistence.dao.filetransfer.FileTransferDao;
import com.indeval.portaldali.persistence.dao.filetransfer.FileTransferOperacionesDao;
import com.indeval.portaldali.persistence.model.DiaInhabil;
import com.indeval.portaldali.persistence.model.FileTransfer;
import com.indeval.portaldali.persistence.model.FileTransferOperaciones;
import com.indeval.portaldali.persistence.model.FileTransferPK;
import com.indeval.portaldali.persistence.model.Instrumento;
import com.indeval.portaldali.persistence.model.TipoInstruccion;
import com.indeval.portaldali.persistence.util.DateUtil;
import com.indeval.portaldali.persistence.vo.InstrumentoVO;
import com.indeval.portaldali.persistencia.fileTransfer.ArchivosFileTransfer;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class FileTransferServiceImpl implements FileTransferService, Constantes {

	/** Servicio de log */
	private Logger log = LoggerFactory.getLogger(FileTransferServiceImpl.class);

	/** Bean para acceso a las utilerias de los servicios */
	private UtilServices utilService;

	/** Bean de acceso a datos de DiaInhabil */
	private DiaInhabilDaliDao diaInhabilDaliDao;

	/** Bean para acceso al properties de mensaje de errores */
	private MessageResolver errorResolver;

	/** Bean de acceso a fileTransferDao */
	private FileTransferDao fileTransferDao;

	/** Bean de acceso a fileTransferOperacionesDao */
	private FileTransferOperacionesDao fileTransferOperacionesDao;

	/** Bean de acceso a DateUtilsDao */
	private DateUtilsDao dateUtilsDao;

	/** Bean de acceso a datos de Instrumento */
	private InstrumentoDaliDao instrumentoDaliDao;

	/** Bean de acceso a datos de emision */
	private EmisionDaliDAO emisionDao;

	/** Bean de acceso a operacion nombrada */
	private OperacionNombradaDao operacionNombradaDao;

	private List<String> cuentasInvalidas;
	/** DAO para consulta de Instituciones */
	private InstitucionDaliDAO institucionDaliDAO;
	/** DAO para consulta de Divisas */ 
	private DivisaDaliDAO divisaDAO;
	/** DAO para consulta de Bovedas */
	private BovedaDaliDAO bovedaDAO;
    /** dao depositante valido banxico */
    private DepositanteValidoBanxicoDao depositanteValidoBanxicoDao;
    /** service de administacion de cuentas de bcom*/
    private AdministracionCuentasRetiroService admonCuentasService;
    /** Objeto de acceso al catalogo de estados de instruccion */
	private EstadoInstruccionDaliDAO estadoInstruccionDaliDAO = null;
	/** Service de retiros*/
	private AdmonRetirosEfectivoService admonRetiroEfectivo;
	
	private ValidacionService validacionService;
	private CuentaDaliDAO cuentaDaliDao;
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.filetransfer.FileTransferService#guardarArchivoFileTransfer(com.indeval.portaldali.persistencia.posicion.ArchivosFileTransfer)
	 */
	public void guardarArchivoFileTransfer(ArchivosFileTransfer archivosFileTransfer) throws BusinessException {
		fileTransferDao.guardarArchivoFileTransfer(archivosFileTransfer);		
	}
	
	/**
	 * @see com.indeval.portaldali.middleware.services.filetransfer.FileTransferService#almacenaInformacion(com.indeval.portaldali.middleware.services.modelo.AgenteVO,
	 *      java.lang.String, java.util.ArrayList, int, String)
	 */
	@SuppressWarnings("unchecked")
	public int almacenaInformacion(AgenteVO agenteFirmado, String tipoProceso,
			ArrayList informacionArchivo, int offset, String nombreUsuario)
			throws BusinessException {

		log.trace("Entrando a FileTransferServiceImpl.almacenaInformacion()");

		/* Valida parametros */
		utilService.validaDTONoNulo(agenteFirmado, " agente firmado ");
		agenteFirmado.tieneClaveValida();

		if (StringUtils.isBlank(tipoProceso)) {
			throw new BusinessException(errorResolver.getMessage("J0026",
					" el tipo de proceso "), "J0026");
		}
		if (informacionArchivo == null || informacionArchivo.isEmpty()) {
			throw new BusinessDataException(errorResolver.getMessage("J0046",
					new Object[] { "informacionArchivo" }), "J0046");
		}
		if (offset >= informacionArchivo.size()) {
			throw new BusinessDataException(errorResolver
					.getMessage("J0045", new Object[] { Integer
							.toString(informacionArchivo.size()) }), "J0045");
		}

		/* Borra la informacion antes de almacenarla */
		if (offset == 0) {
			cancelaProceso(agenteFirmado, tipoProceso);
		}

		/* Realiza la lectura y almacenamiento de registros */
		long inicioProceso = Calendar.getInstance().getTimeInMillis();
		int ultimoRegistro = 0;
		for (int i = offset; i < informacionArchivo.size(); i++) {
			FileTransfer fileTransfer = new FileTransfer();
			fileTransfer.setFileTransferPK(new FileTransferPK());
			fileTransfer.getFileTransferPK().setIdInst(agenteFirmado.getId());
			fileTransfer.getFileTransferPK().setFolioInst(
					agenteFirmado.getFolio());
			fileTransfer.getFileTransferPK().setTipoReg(tipoProceso);
			fileTransfer.getFileTransferPK().setConsec(
					new BigDecimal(Integer.toString(i + 1)));
			fileTransfer.setCadena(informacionArchivo.get(i).toString());
			fileTransfer.setEstado(ESTADO_NUEVO);
			fileTransfer.setFechaReg(dateUtilsDao.getDateFechaDB());
			fileTransfer.setUsuario(nombreUsuario);
			Serializable nuevaEntidad = fileTransferDao.save(fileTransfer);
			if (nuevaEntidad == null) {
				throw new BusinessDataException(errorResolver.getMessage(
						"J0048", (Object) ReflectionToStringBuilder
								.reflectionToString(fileTransfer
										.getFileTransferPK())), "J0048");
			}

			/*
			 * System.out.println("CARGANDO FILETRANSFER [" +
			 * ReflectionToStringBuilder
			 * .reflectionToString(fileTransfer.getFileTransferPK()) + "]");
			 */

			ultimoRegistro = i;

			long finalProceso = Calendar.getInstance().getTimeInMillis();
			if ((finalProceso - inicioProceso) > 10000) {
				break;
			}
		}

		if ((ultimoRegistro + 1) == informacionArchivo.size()) {
			return -1;
		}

		return ultimoRegistro + 1;
	}

	/**
	 * @see com.indeval.portaldali.middleware.services.filetransfer.FileTransferService#cancelaProceso(com.indeval.portaldali.middleware.services.modelo.AgenteVO,
	 *      java.lang.String)
	 */
	public void cancelaProceso(AgenteVO agenteFirmado, String tipoProceso)
			throws BusinessException {

		log.trace("Entrando a FileTransferServiceImpl.cancelaProceso()");

		/* Valida parametros */
		utilService.validaAgente(agenteFirmado, "firmado", false);

		if (StringUtils.isBlank(tipoProceso)) {
			throw new BusinessException(errorResolver.getMessage("J0026",
					" el tipo de proceso "), "J0026");
		}

		/* Obtiene la lista de instancias FileTransfer y FileTransferOperaciones */
		List<FileTransfer> listaFileTransfer = obtieneListaFileTransfer(
				agenteFirmado.getId(), agenteFirmado.getFolio(), tipoProceso);
		List<FileTransferOperaciones> listaFileTransferOperaciones = obtieneListaFileTransferOperaciones(
				agenteFirmado.getId(), agenteFirmado.getFolio(), tipoProceso);

		// Borra los objetos recuperados de la bd
		for (FileTransfer fileTransfer : listaFileTransfer) {
			utilService.validaDTONoNulo(fileTransfer, " fileTransfer ");

			try {
				fileTransferDao.delete(fileTransfer);
				fileTransferDao.flush();
			} catch (Exception e) {
				throw new BusinessException(e.getMessage());
			}
		}
		// Borra los objetos recuperados de la bd
		for (FileTransferOperaciones fileTransfer : listaFileTransferOperaciones) {
			utilService.validaDTONoNulo(fileTransfer,
					" fileTransfer operaciones ");

			try {
				fileTransferOperacionesDao.delete(fileTransfer);
				fileTransferOperacionesDao.flush();
			} catch (Exception e) {
				throw new BusinessException(e.getMessage());
			}
		}
	}

	/**
	 * @see com.indeval.portaldali.middleware.services.filetransfer.FileTransferService#muestraInformacion(com.indeval.portaldali.middleware.services.modelo.AgenteVO,
	 *      java.lang.String, boolean, PaginaVO)
	 */
	@SuppressWarnings("unchecked")
	public TotalesProcesoVO muestraInformacion(AgenteVO agenteFirmado,
			String tipoProceso, boolean soloErrores, PaginaVO paginaVO)
			throws BusinessException {

		log.trace("Entrando a FileTransferServiceImpl.muestraInformacion()");

		/* Valida parametros */
		TotalesProcesoVO totalesProcesoVO = null;

		try {
			utilService.validaDTONoNulo(agenteFirmado, " agente firmado ");
			agenteFirmado.tieneClaveValida();
			if (StringUtils.isBlank(tipoProceso)) {
				throw new BusinessException(errorResolver.getMessage("J0026",
						" el tipo de proceso "));
			}

			List listaFileTransfer = this.obtieneListaFileTransfer(
					agenteFirmado.getId(), agenteFirmado.getFolio(),
					tipoProceso);

			ArrayList listaRegistros = new ArrayList();
			int registrosError = 0;
			int totalregistros = 0;
			StringBuilder contenidoArchivo = new StringBuilder();
			for (Iterator iter = listaFileTransfer.iterator(); iter.hasNext();) {

				FileTransfer fileTransfer = (FileTransfer) iter.next();
				utilService.validaDTONoNulo(fileTransfer, " fileTransfer ");

				/*
				 * System.out.println("OBTIENE DATOS PARA MOSTRAR EN PANTALLA ["
				 * + ReflectionToStringBuilder.reflectionToString(fileTransfer
				 * .getFileTransferPK()) + "]");
				 */

				if (!soloErrores
						|| (soloErrores && fileTransfer.getEstado().compareTo(
								ESTADO_ERROR) == 0)) {
					RegistroProcesadoVO registroProcesadoVO = new RegistroProcesadoVO();
					CamposCalculadosVO camposCalculadosVO = new CamposCalculadosVO();
					contenidoArchivo.append(fileTransfer.getCadena()).append("\n");
					registroProcesadoVO.setCadena(fileTransfer.getCadena());
					registroProcesadoVO.setConsec(fileTransfer
							.getFileTransferPK().getConsec().toBigInteger());
					registroProcesadoVO.setDatos(generaDatos(fileTransfer,
							tipoProceso));
					if (registroProcesadoVO.getDatos() == null) {
						registroProcesadoVO.setDatos((List) new ArrayList());
					}
					registroProcesadoVO.setMensajesError(fileTransfer
							.getError() != null ? fileTransfer.getError()
							.split("-") : null);
					registroProcesadoVO.setEstado(fileTransfer.getEstado());
					registroProcesadoVO.setFechaRegistro(fileTransfer
							.getFechaReg());
					registroProcesadoVO.setNombreUsuario(fileTransfer
							.getUsuario());

					FileTransferOperaciones fileTransferOperaciones = obtieneFileTransferOperacionesByPK(fileTransfer2FileTransferOperacionesPK(fileTransfer));

					if (fileTransferOperaciones != null) {
						camposCalculadosVO.setDivisa(fileTransferOperaciones
								.getDivisa());
						camposCalculadosVO
								.setFechaConcertacion(fileTransferOperaciones
										.getFechaConcertacion());
						camposCalculadosVO
								.setFechaLiquidacion(fileTransferOperaciones
										.getFechaLiquidacion());
						camposCalculadosVO
								.setFechaReporto(fileTransferOperaciones
										.getFechaReporto());
						camposCalculadosVO.setImporte(fileTransferOperaciones
								.getImporte());
						camposCalculadosVO.setMercado(fileTransferOperaciones
								.getMercado());
						camposCalculadosVO.setOrigen(fileTransferOperaciones
								.getOrigen());
						camposCalculadosVO
								.setOrigenAplicacion(fileTransferOperaciones
										.getOrigenAplicacion());
						camposCalculadosVO
								.setPrecioTitulo(fileTransferOperaciones
										.getPrecioTitulo());
						camposCalculadosVO
								.setSociedadSerie(fileTransferOperaciones
										.getSociedadSerie());
						camposCalculadosVO
								.setTasaPremio(fileTransferOperaciones
										.getTasaPremio());
						registroProcesadoVO.setCompra(fileTransferOperaciones
								.getMarcaCompra() != null
								&& fileTransferOperaciones.getMarcaCompra()
										.intValue() == 1 ? true : false);
					}

					registroProcesadoVO
							.setCamposCalculadosVO(camposCalculadosVO);

					if (fileTransfer.getEstado().compareTo(ESTADO_ERROR) == 0) {
						registrosError++;
					}

					totalregistros++;
					listaRegistros.add(registroProcesadoVO);
				}
			}

			/* Se construye el objeto de retorno */
			if (listaRegistros != null && !listaRegistros.isEmpty()) {
				totalesProcesoVO = new TotalesProcesoVO();
				totalesProcesoVO.setPaginaVO(UtilsDaliVO
						.getPaginaNotBlank(paginaVO).extraerSublist(
								listaRegistros));
				totalesProcesoVO.setRegistrosACargar(Integer.valueOf (totalregistros
						- registrosError));
				totalesProcesoVO.setRegistrosConError(Integer.valueOf(
						registrosError));
				
				totalesProcesoVO.setContenidoArchivo(contenidoArchivo.toString());
			}

		} catch (Exception e) {
			// Se captura cualquier excepcion para evitar el bloqueo del
			// candado.
			e.printStackTrace();
		}

		return totalesProcesoVO;
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.filetransfer.FileTransferService#obtieneResumen(com.indeval.portaldali.middleware.services.modelo.AgenteVO, java.lang.String)
	 */
	public ResumenVO obtieneResumen(AgenteVO agenteFirmado, String tipoProceso) throws BusinessException {
		int registrosCargados = 0;
		int registrosError = 0;
		int registrosNuevos = 0;
		int totalRegistros = 0;
		int registrosProtocolo = 0;
		String nombreUsuario = null;
		Date fechaCarga = null;

		log.trace("Entrando a FileTransferServiceImpl.obtieneResumen()");
		// Valida parametros
		utilService.validaAgente(agenteFirmado, "firmado", false);
		if (StringUtils.isBlank(tipoProceso)) {
			throw new BusinessException(errorResolver.getMessage("J0026", new Object[] { " el tipo de proceso " }));
		}
		//Se construye el objeto de retorno y se settean los parametros recibidos
		ResumenVO resumenVO = new ResumenVO();
		resumenVO.setAgenteFirmado(agenteFirmado);
		resumenVO.setTipoProceso(tipoProceso);
		//Obtiene la lista de instancias FileTransfer
		List<FileTransfer> listaFileTransfer = 
			obtieneListaFileTransfer(agenteFirmado.getId(), agenteFirmado.getFolio(), tipoProceso);
		// Se cuentan los registros por estado
		for (FileTransfer fileTransfer : listaFileTransfer) {
			if (fileTransfer.getEstado().compareTo(ESTADO_ERROR) == 0) {
				registrosError++;
			}
			else if (fileTransfer.getEstado().compareTo(ESTADO_NUEVO) == 0) {
				registrosNuevos++;
			}
			else if (fileTransfer.getEstado().compareTo(ESTADO_CARGADO) == 0) {
				registrosCargados++;
			}
			else if (fileTransfer.getEstado().compareTo(ESTADO_PROTOCOLO) == 0) {
				registrosProtocolo++;
			}
			nombreUsuario = fileTransfer.getUsuario();
			fechaCarga = fileTransfer.getFechaReg();
			totalRegistros++;
		}
		if (totalRegistros != registrosCargados + registrosError + registrosNuevos + registrosProtocolo) {
			throw new BusinessException(errorResolver.getMessage("J0049", new Object[] { " " }));
		}
		// Se settean en el objeto de retorno el resumen de registros
		resumenVO.setFechaCarga(fechaCarga);
		resumenVO.setHoraCarga(convertFechaToHora(fechaCarga));
		resumenVO.setTotalCargados(new Integer(registrosCargados));
		resumenVO.setTotalError(new Integer(registrosError));
		resumenVO.setTotalNuevos(new Integer(registrosNuevos));
		resumenVO.setTotalRegistros(new Integer(totalRegistros));
		resumenVO.setTotalProtocolo(new Integer(registrosProtocolo));
		resumenVO.setNombreUsuario(nombreUsuario);
		return resumenVO;
	}

	/**
	 * Obtiene la hora de una fecha
	 * 
	 * @param fechaCarga
	 *            Fecha de la cual se obtendra la hora
	 * @return hora en formato HH:mm:ss
	 */
	private String convertFechaToHora(Date fechaCarga) {
		String hora = "";

		if (fechaCarga != null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				hora = sdf.format(fechaCarga);
			} catch (Exception e) {
				e.printStackTrace();
				hora = "";
			}
		}

		return hora;
	}

	/**
	 * @see com.indeval.portaldali.middleware.services.filetransfer.FileTransferService#validaInformacion(com.indeval.portaldali.middleware.services.modelo.AgenteVO,
	 *      java.lang.String, int)
	 */
	@SuppressWarnings("unchecked")
	public int validaInformacion(AgenteVO agenteFirmado, String tipoProceso,
			BigInteger idBoveda, int offset) throws BusinessException {

		log.trace("Entrando a FileTransferServiceImpl.validaInformacion()");

		/* Valida parametros */
		utilService.validaAgente(agenteFirmado, "firmado", false);
		if (StringUtils.isBlank(tipoProceso)) {
			throw new BusinessException(errorResolver.getMessage("J0026",
					" el tipo de proceso "));
		}

		/* Obtiene la lista de instancias FileTransfer  */
		/* esta es la lista de registros que se cargaron*/
		List listaFileTransfer = obtieneListaFileTransfer(
				agenteFirmado.getId(), agenteFirmado.getFolio(), tipoProceso);

		if (listaFileTransfer == null) {
			throw new BusinessDataException(errorResolver.getMessage("J0046",
					new Object[] { " listaFileTransfer " }), "J0046");
		}

		if (offset >= listaFileTransfer.size()) {
			throw new BusinessDataException(errorResolver
					.getMessage("J0045", new Object[] { Integer
							.toString(listaFileTransfer.size()) }), "J0045");
		}

		int result = -1;

		if (tipoProceso.equalsIgnoreCase(TRASPASO_MD)) {
			result = validaInformacionTraspasosMD(agenteFirmado, idBoveda,
					listaFileTransfer, offset);
		} else if (tipoProceso.equalsIgnoreCase(TRASPASO_MC)) {
			result = validaInformacionTraspasosMC(agenteFirmado, idBoveda,
					listaFileTransfer, offset);
		}else if(tipoProceso.equalsIgnoreCase(DEPOSITO_EFECTIVO)){					
			result= validaInformacionDepositosEfectivo(agenteFirmado, idBoveda,
					listaFileTransfer, offset);
		}else if(tipoProceso.equalsIgnoreCase(CUENTAS_BCOM)){
			result = validaInformacionCuentasBCom(agenteFirmado, idBoveda,
					listaFileTransfer, offset); 
		}else if(tipoProceso.equalsIgnoreCase(RETIROS_BCOM)){
			result = validaInformacionRetirosBCom(agenteFirmado, idBoveda,
					listaFileTransfer, offset); 
		}else if(tipoProceso.equalsIgnoreCase(TRASPASOS_EFECTIVO) ) {
			result = validaInformacionTraspasosEfectivo(agenteFirmado, idBoveda, listaFileTransfer, offset); 
		}
		
		
		return result;
	}


	/**
	 * @see com.indeval.portaldali.middleware.services.filetransfer.FileTransferService#grabaInformacion(AgenteVO,
	 *      String, HashMap)
	 */
	@SuppressWarnings("unchecked")
	public void grabaInformacion(AgenteVO agenteFirmado, String tipoProceso,
			HashMap consecProtocolo) throws BusinessException {

		log.trace("Entrando a FileTransferServiceImpl.grabaInformacion()");

		utilService.validaAgente(agenteFirmado, "firmado", false);

		utilService.validarClaveTipo(tipoProceso, " tipo de proceso ");

		// Obtiene la lista de instancias FileTransfer
		List<FileTransfer> listaFileTransfer = obtieneListaFileTransfer(
				agenteFirmado.getId(), agenteFirmado.getFolio(), tipoProceso);

		for (FileTransfer fileTransfer : listaFileTransfer) {
			Boolean isProtocolo = isProcesadaXProtocolo(fileTransfer,
					consecProtocolo);

			if (isProtocolo != null) {
				if (isProtocolo) {
					fileTransfer.setEstado(ESTADO_PROTOCOLO);
					fileTransferDao.update(fileTransfer);
				}
			} else {
				fileTransfer.setEstado(ESTADO_ERROR);
				fileTransferDao.update(fileTransfer);
			}

			// Si no fue enviado a protocolo, este tipo de operacion no es
			// soportado
			if (fileTransfer.getEstado().equalsIgnoreCase(ESTADO_NUEVO)) {
				fileTransfer.setEstado(ESTADO_ERROR);
				fileTransfer
						.setError("El tipo de operacion no es soportado por este FileTransfer");
				fileTransferDao.update(fileTransfer);
			}
		}
	}

	/**
	 * Indica si la operacion se proceso por protocolo y en forma correcta
	 * 
	 * @param fileTransfer
	 * @param consecProtocolo
	 * @return Boolean true - Si se proceso por protocolo, false - si no se
	 *         proceso por protocolo, null si se proceso por protocolo con error
	 */
	private Boolean isProcesadaXProtocolo(FileTransfer fileTransfer,
			Map<BigInteger, Integer> consecProtocolo) {

		log.trace("Entrando a FileTransferServiceImpl.isProcesadaXProtocolo()");

		if (consecProtocolo != null
				&& !consecProtocolo.isEmpty()
				&& consecProtocolo.containsKey(fileTransfer.getFileTransferPK()
						.getConsec().toBigInteger())) {
			if (((Integer) consecProtocolo.get(fileTransfer.getFileTransferPK()
					.getConsec().toBigInteger())).intValue() == 1) {
				return Boolean.TRUE;
			}
			return null;
		}

		return Boolean.FALSE;

	}
	
	/**
	 * Valida la informacion del filetrasnfer de depositos de efectivo
	 * 
	 * @param agenteFirmado
	 * @param idBoveda
	 * @param listaFileTransfer
	 * @param offset
	 * @return
	 */
	
	private int validaInformacionDepositosEfectivo(AgenteVO agenteFirmado,
			BigInteger idBoveda, List listaFileTransfer, int offset) {
		
		
		long inicioProceso = Calendar.getInstance().getTimeInMillis();
		int ultimoRegistro = 0;

		
		for (int i = offset; i < listaFileTransfer.size(); i++) {
			
			FileTransfer fileTransfer = (FileTransfer) listaFileTransfer.get(i);
			boolean error = false;
			StringBuffer msgError = new StringBuffer(UtilsDaliVO.BLANK);
			StringBuffer numCampoError = new StringBuffer(UtilsDaliVO.BLANK);
			String cadenaErrorFormato = errorResolver.getMessage("J0051");
			
		
			/* Obtiene los campos correspondientes al layout */
			String[] campos = camposDepositoEfectivo(fileTransfer.getCadena());
			
			
			if (campos == null) {
				msgError.append(cadenaErrorFormato);
				msgError.append("en la estructura del registro");
				msgError.append(GUION);
				error = true;
			} else {

				/* Obtiene el mapa de indices (true) correspondiente al layout */
				Map indice = indicesNombresDepositosEfectivo(true);

				// CASFIM
				String casfim=campos[((Integer) indice.get(CASFIM)).intValue()];
				if (StringUtils.isBlank(casfim) || casfim.length()!=5
							||!casfim.matches(PATRON_NUMERICO)){
					msgError.append(cadenaErrorFormato);
					msgError.append(" del casfim de beneficiario ");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(CASFIM))
							.toString());
					numCampoError.append(GUION);
					error = true;
				} else {
					InstitucionDTO institucion = institucionDaliDAO.buscarInstitucionPorCasfim(casfim.trim());
					if (institucion == null) {
						msgError.append("La institucion con este CASFIM no existe");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice.get(CASFIM)).toString());
						numCampoError.append(GUION);
						error = true;
					}
				}				
				// IMPORTE
				String importe=campos[((Integer) indice.get(IMPORTE)).intValue()];
				String PATRON="\\d+(.\\d{2})?";				
				if (StringUtils.isBlank(importe) || !importe.trim().matches(PATRON) || importe.length() != 15){
					msgError.append(cadenaErrorFormato);
					msgError.append(" el importe debe ser una cantidad ###.##");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(IMPORTE))
							.toString());
					numCampoError.append(GUION);
					error = true;
				} else {
					BigDecimal importeNumerico = null;
					try {
						importeNumerico = new BigDecimal(importe.trim());
						if(importeNumerico.compareTo(BigDecimal.ZERO) == 0) {
							msgError.append("El importe debe ser mayor a CERO");
							msgError.append(GUION);
							numCampoError.append(((Integer) indice.get(IMPORTE))
									.toString());
							numCampoError.append(GUION);
							error = true;
						}
					} catch (NumberFormatException e) {
						msgError.append(cadenaErrorFormato);
						msgError.append(" el importe debe ser una cantidad ###.##");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice.get(IMPORTE))
								.toString());
						numCampoError.append(GUION);
						error = true;
						log.error("el importe debe ser una cantidad ###.##", e);
					}
				}	
				
				//DIVISA(Validacion Nueva)Comprueba k venga bien estructurado la columna divisa  				
				  String divisa=campos[((Integer) indice.get(DIVISA)).intValue()];				  				  
					if (StringUtils.isBlank(divisa) || divisa.length()!=3
								||!divisa.matches(PATRON_ALFABETICO)){
						msgError.append(cadenaErrorFormato);
						msgError.append(" la es divisa incorrecta ");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice.get(DIVISA))
								.toString());
						numCampoError.append(GUION);
						error = true;
					} else {
						  DivisaDTO divisaDTO = divisaDAO.obtenerDivisaPorClaveAlfavetica(divisa);
						if (divisaDTO == null) {
							msgError.append(" la Divisa no existe");
							msgError.append(GUION);
							numCampoError.append(((Integer) indice.get(DIVISA)).toString());
							numCampoError.append(GUION);
							error = true;
						}
					}	
				// BOVEDA_EFECTIVO (validacion nueva)compueba que venga bien estructurada la columna 30082012
					String bovedaEfectivo =campos[((Integer) indice.get(BOVEDA_EFECTIVO)).intValue()];					
					if (StringUtils.isBlank(bovedaEfectivo) || bovedaEfectivo.length()!=18
							|| !bovedaEfectivo.trim().matches(PATRON_NOMBRE_CORTO_BOVEDA)){
					msgError.append(cadenaErrorFormato);
					msgError.append(" la boveda efectivo es incorrecta ");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(BOVEDA_EFECTIVO))
							.toString());
					numCampoError.append(GUION);
					error = true;
				} else {
						BovedaDTO bovedaDTO= new BovedaDTO();
						bovedaDTO.setNombreCorto(bovedaEfectivo.trim());
						BovedaDTO bovedaEfectivoDTO = bovedaDAO.buscarBovedaPorTipoCustodia(bovedaDTO);
					if (bovedaEfectivoDTO == null) {
						msgError.append("La Boveda Efectivo no existe");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice.get(BOVEDA_EFECTIVO)).toString());
						numCampoError.append(GUION);
						error = true;
					}
				}
					
				
			}	 
						

			if (error) {
				fileTransfer.setEstado(ESTADO_ERROR);
				if (numCampoError.length() > 0) {
					fileTransfer.setCamposError(numCampoError.toString()
							.substring(0, numCampoError.length() - 1));
				}
				fileTransfer.setError(msgError.toString().substring(0,
						msgError.length() - 1));
				fileTransferDao.update(fileTransfer);
			}

			ultimoRegistro = i;
			long finalProceso = Calendar.getInstance().getTimeInMillis();
			if ((finalProceso - inicioProceso) > 10000) {
				break;
			}		
		}
		
		if ((ultimoRegistro + 1) == listaFileTransfer.size()) {
			return -1;
		}

		return ultimoRegistro + 1;	
	}

	/**
	 * Valida la informacion del filetrasnfer de depositos de efectivo
	 * 
	 * @param agenteFirmado
	 * @param idBoveda
	 * @param listaFileTransfer
	 * @param offset
	 * @return
	 */
	
	private int validaInformacionTraspasosEfectivo(AgenteVO agenteFirmado, BigInteger idBoveda, List listaFileTransfer, int offset) {
		
		
		long inicioProceso = Calendar.getInstance().getTimeInMillis();
		int ultimoRegistro = 0;

		
		for (int i = offset; i < listaFileTransfer.size(); i++) {
			
			FileTransfer fileTransfer = (FileTransfer) listaFileTransfer.get(i);
			boolean error = false;
			StringBuilder msgError = new StringBuilder(UtilsDaliVO.BLANK);
			StringBuilder numCampoError = new StringBuilder(UtilsDaliVO.BLANK);
			String cadenaErrorFormato = errorResolver.getMessage("J0051");
			String datoRequerido = "Error: El dato es requerido. ";
		
			/* Obtiene los campos correspondientes al layout */
			String[] campos = camposTraspasoEfectivo(fileTransfer.getCadena());
			
			
			if (campos == null) {
				msgError.append(cadenaErrorFormato);
				msgError.append(" En la estructura del registro");
				msgError.append(GUION);
				error = true;
			} else {

				/* Obtiene el mapa de indices (true) correspondiente al layout */
				Map<Object, Object> indice = indicesNombresTraspasosEfectivo(true);

				// TRASPASANTE
				String traspasante=campos[((Integer) indice.get(CLAVE_TRASP)).intValue()];
				
				
				if (StringUtils.isBlank(traspasante) ) {
					msgError.append(datoRequerido);
					msgError.append(" Clave del TRASPASANTE ");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(CLAVE_TRASP)).toString());
					numCampoError.append(GUION);
				}else if(traspasante.length()!=5 || !traspasante.matches(PATRON_NUMERICO)){
					msgError.append(cadenaErrorFormato);
					msgError.append(" Clave del TRASPASANTE ");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(CLAVE_TRASP)).toString());
					numCampoError.append(GUION);
					error = true;
				} 
				
				// RECEPTOR
				String receptor = campos[((Integer) indice.get(CLAVE_RECEP)).intValue()];
				
				if (StringUtils.isBlank(receptor)) { 
					msgError.append(datoRequerido);
					msgError.append(" Clave del RECEPTOR ");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(CLAVE_RECEP)).toString());
					numCampoError.append(GUION);
					error = true;
				}else if (receptor.length()!=5 || !receptor.matches(PATRON_NUMERICO)){
					msgError.append(cadenaErrorFormato);
					msgError.append(" Clave del RECEPTOR ");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(CLAVE_RECEP)).toString());
					numCampoError.append(GUION);
					error = true;
				}
				
				// IMPORTE
				String importe=campos[((Integer) indice.get(IMPORTE)).intValue()];
				String PATRON="\\d+(.\\d{2})?";	
				
				if (StringUtils.isBlank(importe)) {
					msgError.append(datoRequerido);
					msgError.append(" Importe");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(IMPORTE)).toString());
					numCampoError.append(GUION);
					error = true;
				}else if( !importe.trim().matches(PATRON) || importe.length() != 15){
					msgError.append(cadenaErrorFormato);
					msgError.append(" El importe debe ser una cantidad ###.##");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(IMPORTE))
							.toString());
					numCampoError.append(GUION);
					error = true;
				} else {
					BigDecimal importeNumerico = null;
					try {
						importeNumerico = new BigDecimal(importe.trim());
						if(importeNumerico.compareTo(BigDecimal.ZERO) == 0) {
							msgError.append("El importe debe ser mayor a CERO");
							msgError.append(GUION);
							numCampoError.append(((Integer) indice.get(IMPORTE))
									.toString());
							numCampoError.append(GUION);
							error = true;
						}
					} catch (NumberFormatException e) {
						msgError.append(cadenaErrorFormato);
						msgError.append(" El importe debe ser una cantidad ###.##");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice.get(IMPORTE))
								.toString());
						numCampoError.append(GUION);
						error = true;
						log.error(" El importe debe ser una cantidad ###.##", e);
					}
				}	
				
				// DIVISA			
				String divisa=campos[((Integer) indice.get(DIVISA)).intValue()];
				if (StringUtils.isBlank(divisa)) {
					msgError.append(datoRequerido);
					msgError.append(" Divisa ");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(DIVISA)).toString());
					numCampoError.append(GUION);
					error = true;
				}else if(divisa.length()!=3 || !divisa.matches(PATRON_ALFABETICO)){
					msgError.append(cadenaErrorFormato);
					msgError.append(" Divisa incorrecta ");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(DIVISA))
							.toString());
					numCampoError.append(GUION);
					error = true;
				} 	
				
				// BOVEDA_EFECTIVO 
				String bovedaEfectivo =campos[((Integer) indice.get(BOVEDA_EFECTIVO)).intValue()];
				if (StringUtils.isBlank(bovedaEfectivo)) {
					msgError.append(datoRequerido);
					msgError.append(" Boveda de efectivo ");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(BOVEDA_EFECTIVO)).toString());
					numCampoError.append(GUION);
					error = true;
				}else if(bovedaEfectivo.length()!=18 || !bovedaEfectivo.trim().matches(PATRON_NOMBRE_CORTO_BOVEDA)){
					msgError.append(cadenaErrorFormato);
					msgError.append(" Boveda de efectivo incorrecta ");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(BOVEDA_EFECTIVO))
							.toString());
					numCampoError.append(GUION);
					error = true;
				}
				
				
				if(!error) {
					
					//Validar que traspasante y receptor sean distintos
					if (traspasante.trim().equals(receptor.trim()) ) {
						msgError.append("La institucion TRASPASANTE y RECEPTOR deben ser distintos.");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice.get(CLAVE_TRASP)).toString());
						numCampoError.append(GUION);
						error = true;
					}else { // Validar si el agente es distinto a Indeval, entonces el agente debe fungir como Traspasante
						if( !Constantes.ID_FOLIO_INDEVAL.equals(agenteFirmado.getClave()) && 
								!traspasante.trim().equals(agenteFirmado.getClave()) ) {
							msgError.append("La agente firmado solo puede fungir como TRASPASANTE.");
							msgError.append(GUION);
							numCampoError.append(((Integer) indice.get(CLAVE_TRASP)).toString());
							numCampoError.append(GUION);
							error = true;
						}else {
							// Validar que la institucion traspasante exista
							InstitucionDTO institucionTraspasante = institucionDaliDAO.buscarInstitucionPorClaveYFolio(traspasante.trim());
							if (institucionTraspasante == null) {
								msgError.append("La institucion traspasante con este ID FOLIO no existe");
								msgError.append(GUION);
								numCampoError.append(((Integer) indice.get(CLAVE_TRASP)).toString());
								numCampoError.append(GUION);
								error = true;
							}else{
								// Validar que la institucion traspasante tenga una cuenta clabe.
								String cuentaClabe = StringUtils.trimToNull(institucionTraspasante.getCuentaClabe());
								// Validar que la institucion traspasante tenga una cuenta clabe.
								if(StringUtils.isBlank(cuentaClabe)) {
									msgError.append("La institucion traspasante no tiene con una cuenta CLABE.");
									msgError.append(GUION);
									numCampoError.append(((Integer) indice.get(CLAVE_TRASP)).toString());
									numCampoError.append(GUION);
									error = true;
								}else {// Validar que se pueda obtener la clave del participante a partir de la cuenta CLABE 
									if(cuentaClabe.length() != 18  || !traspasante.equals(StringUtils.substring(cuentaClabe, 8, 13)) ) {
										msgError.append("La institucion traspasante no tiene una cuenta CLABE valida.");
										msgError.append(GUION);
										numCampoError.append(((Integer) indice.get(CLAVE_TRASP)).toString());
										numCampoError.append(GUION);
										error = true;
									}else {// Validar que el Traspasante tenga asignada una cuenta nombrada de efectivo
										CuentaEfectivoDTO criterio = new CuentaEfectivoDTO();
										criterio.setTipoCustodia(TipoCustodiaConstants.EFECTIVO);
										criterio.setTipoCuenta(new TipoCuentaDTO(TipoCuentaConstants.TIPO_NOMBRADA, ""));
										criterio.setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO, ""));
										criterio.setInstitucion(institucionTraspasante);
										criterio.setNumeroCuenta(String.valueOf(DaliConstants.VALOR_COMBO_TODOS));
										
										List<CuentaEfectivoDTO> cuentas = cuentaDaliDao.buscarCuentasNombradasEfectivo(criterio, null);
										if(cuentas == null || cuentas.isEmpty()) {
											msgError.append("La institucion traspasante no tiene cuenta de efectivo asignada.");
											msgError.append(GUION);
											numCampoError.append(((Integer) indice.get(CLAVE_TRASP)).toString());
											numCampoError.append(GUION);
											error = true;
										}
									}
								}
							}
							
						
							// Validar que la institucion receptora exista
							InstitucionDTO institucionReceptora = institucionDaliDAO.buscarInstitucionPorClaveYFolio(receptor);
							if (institucionReceptora == null) {
								msgError.append("La institucion receptora con este ID FOLIO no existe");
								msgError.append(GUION);
								numCampoError.append(((Integer) indice.get(CLAVE_RECEP)).toString());
								numCampoError.append(GUION);
								error = true;
							}else {
								String cuentaClabe = StringUtils.trimToNull(institucionReceptora.getCuentaClabe());
								// Validar que la institucion receptora tenga una cuenta clabe.
								if(StringUtils.isBlank(cuentaClabe)) {
									msgError.append("La institucion receptora no tiene una cuenta CLABE.");
									msgError.append(GUION);
									numCampoError.append(((Integer) indice.get(CLAVE_RECEP)).toString());
									numCampoError.append(GUION);
									error = true;
								}else {// Validar que se pueda obtener la clave del participante a partir de la cuenta CLABE 
									if(cuentaClabe.length() != 18  || !receptor.equals(StringUtils.substring(cuentaClabe, 8, 13)) ) {
										msgError.append("La institucion receptora no tiene una cuenta CLABE valida.");
										msgError.append(GUION);
										numCampoError.append(((Integer) indice.get(CLAVE_RECEP)).toString());
										numCampoError.append(GUION);
										error = true;
									}else {// Validar que el Receptor tenga asignada una cuenta nombrada de efectivo
										CuentaEfectivoDTO criterio = new CuentaEfectivoDTO();
										criterio.setTipoCustodia(TipoCustodiaConstants.EFECTIVO);
										criterio.setTipoCuenta(new TipoCuentaDTO(TipoCuentaConstants.TIPO_NOMBRADA, ""));
										criterio.setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO, ""));
										criterio.setInstitucion(institucionReceptora);
										criterio.setNumeroCuenta(String.valueOf(DaliConstants.VALOR_COMBO_TODOS));
										
										List<CuentaEfectivoDTO> cuentas = cuentaDaliDao.buscarCuentasNombradasEfectivo(criterio, null);
										if(cuentas == null || cuentas.isEmpty()) {
											msgError.append("La institucion receptora no tiene cuenta de efectivo asignada.");
											msgError.append(GUION);
											numCampoError.append(((Integer) indice.get(CLAVE_RECEP)).toString());
											numCampoError.append(GUION);
											error = true;
										}
									}
								}
							}
						
						}
					}
					
					// Validar que la divisa exista en C_DIVISA
					DivisaDTO divisaDTO = divisaDAO.obtenerDivisaPorClaveAlfavetica(divisa);
					if (divisaDTO == null) {
						msgError.append(" la Divisa no existe");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice.get(DIVISA)).toString());
						numCampoError.append(GUION);
						error = true;
					}else { //validar que la divisa exista en R_TIPO_INSTRUCCION_DIVISA
						List<DivisaDTO> divisas  = divisaDAO.buscarDivisasPorTipoInstruccion(null, new BigInteger( String.valueOf(
								com.indeval.portaldali.middleware.services.tesoreria.util.Constantes.ID_TIPO_INSTRUCCION__TREF)) );
						
						if ( !divisas.contains(divisaDTO)) {
							msgError.append(" la Divisa no puede utilizarse para este tipo de instruccion.");
							msgError.append(GUION);
							numCampoError.append(((Integer) indice.get(DIVISA)).toString());
							numCampoError.append(GUION);
							error = true;
						}
					}
					
					// validar que la divisa exista en C_BOVEDA
					BovedaDTO bovedaDTO= new BovedaDTO();
					bovedaDTO.setNombreCorto(bovedaEfectivo.trim());
					BovedaDTO bovedaEfectivoDTO = bovedaDAO.buscarBovedaPorTipoCustodia(bovedaDTO);
					if (bovedaEfectivoDTO == null) {
						msgError.append("La Boveda Efectivo no existe");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice.get(BOVEDA_EFECTIVO)).toString());
						numCampoError.append(GUION);
						error = true;
					}else {
						TipoInstruccionDTO tipoInstruccion = new TipoInstruccionDTO();
						tipoInstruccion.setIdTipoInstruccion((long)com.indeval.portaldali.middleware.services.tesoreria.util.Constantes.ID_TIPO_INSTRUCCION__TREF);
						List<BigInteger> idsBovedas = bovedaDAO.obtenerBovedasPorTipoInstruccion(tipoInstruccion);
						
						if ( !idsBovedas.contains(new BigInteger(String.valueOf(bovedaEfectivoDTO.getId() )) )) {
							msgError.append(" la Boveda no puede utilizarse para este tipo de instruccion.");
							msgError.append(GUION);
							numCampoError.append(((Integer) indice.get(BOVEDA_EFECTIVO)).toString());
							numCampoError.append(GUION);
							error = true;
						} 
					}
					
					// Validar 
					if(!error) {
						List<BigInteger> idsBovedas = bovedaDAO.obtenerBovedasPorDivisa(divisaDTO);
						
						if ( !idsBovedas.contains(new BigInteger(String.valueOf(bovedaEfectivoDTO.getId() )) )) {
							msgError.append(" la Boveda no puede utilizarse con la divisa seleccionada.");
							msgError.append(GUION);
							numCampoError.append(((Integer) indice.get(BOVEDA_EFECTIVO)).toString());
							numCampoError.append(GUION);
							error = true;
						} 
					}
				}
			}	 
						

			if (error) {
				fileTransfer.setEstado(ESTADO_ERROR);
				if (numCampoError.length() > 0) {
					fileTransfer.setCamposError(numCampoError.toString()
							.substring(0, numCampoError.length() - 1));
				}
				fileTransfer.setError(msgError.toString().substring(0,
						msgError.length() - 1));
				fileTransferDao.update(fileTransfer);
			}

			ultimoRegistro = i;
			long finalProceso = Calendar.getInstance().getTimeInMillis();
			if ((finalProceso - inicioProceso) > 10000) {
				break;
			}		
		}
		
		if ((ultimoRegistro + 1) == listaFileTransfer.size()) {
			return -1;
		}

		return ultimoRegistro + 1;	
	}
	
	private String[] camposTraspasoEfectivo(String cadena) {
		log.trace("Entrando a FileTransferServiceImpl.camposTraspasosMD()");

		if (StringUtils.isBlank(cadena)) {
			throw new BusinessDataException(errorResolver.getMessage("J0026",
					new Object[] { " la cadena de informacion " }), "J0026");
		}// validara si la cadena tiene 41 caracteres
		if(cadena.length() == 46){
			List<Integer> posiciones = new ArrayList<Integer>();
			// TRASPASANTE
			posiciones.add(Integer.valueOf(5));
			// BENEFICIARIO
			posiciones.add(Integer.valueOf(5));
			// IMPORTE
			posiciones.add(Integer.valueOf(15));
			// DIVISA
			posiciones.add(Integer.valueOf(3));
			// BOVEDA
			posiciones.add(Integer.valueOf(18));
			
			return obtieneCampos(cadena, posiciones);
		}
		return null;
	}
	
	private String[] camposDepositoEfectivo(String cadena) {
		log.trace("Entrando a FileTransferServiceImpl.camposTraspasosMD()");

		if (StringUtils.isBlank(cadena)) {
			throw new BusinessDataException(errorResolver.getMessage("J0026",
					new Object[] { " la cadena de informacion " }), "J0026");
		}//validara si la cadena tiene 41 caracteres
		if(cadena.length() == 41){
			List<Integer> posiciones = new ArrayList<Integer>();
			posiciones.add(new Integer(5));
			posiciones.add(new Integer(15));
			//se leeran otras columnas
			posiciones.add(new Integer(3));//campos agregados para divisa
			posiciones.add(new Integer(18));//campos agregados para bovedaEfectivo(nombreCorto)
			
			return obtieneCampos(cadena, posiciones);
		}
		return null;
		
		/*if(cadena==null || cadena.length() < 5 ){
			return null;
		}*/

		
	}

	/**
	 * Valida la informacion del archivo plano que corresponde a Traspasos de
	 * Mercado de Dinero
	 * 
	 * @param agenteFirmado
	 * @param listaFileTransfer
	 * @param offset
	 * @return int Numero de registros validados
	 */
	@SuppressWarnings("unchecked")
	private int validaInformacionTraspasosMD(AgenteVO agenteFirmado,
			BigInteger idBoveda, List listaFileTransfer, int offset)
			throws BusinessException {

		log.trace("Entrando a FileTransferServiceImpl."
				+ "validaInformacionTraspasosMD()");

		validaListaFileTransfer(listaFileTransfer);

		long inicioProceso = Calendar.getInstance().getTimeInMillis();
		int ultimoRegistro = 0;

		for (int i = offset; i < listaFileTransfer.size(); i++) {
			FileTransfer fileTransfer = (FileTransfer) listaFileTransfer.get(i);

			log.debug("VALIDA TRASPASOS FILETRANSFER MD ["
					+ ReflectionToStringBuilder.reflectionToString(fileTransfer
							.getFileTransferPK()) + "]");

			boolean error = false;
			StringBuffer msgError = new StringBuffer(UtilsDaliVO.BLANK);
			StringBuffer numCampoError = new StringBuffer(UtilsDaliVO.BLANK);
			String cadenaErrorFormato = errorResolver.getMessage("J0051");

			/* Obtiene los campos correspondientes al layout */
			String[] campos = camposTraspasosMD(fileTransfer.getCadena());

			if (campos == null) {
				msgError.append(cadenaErrorFormato);
				msgError.append("en la estructura del registro");
				msgError.append(GUION);
				error = true;
			} else {

				/* Obtiene el mapa de indices (true) correspondiente al layout */
				Map indice = indicesNombresTraspasosMD(true);

				// ///////////////////////////////////// VALIDACIONES DE FORMATO
				// ///////////////////////////////////////
				// FOLIO DESCRIPCION
				// No se valida el folio descripcion

				// ID VENDEDOR
				
				/**variable para determinar si el archivo contiene Traspasos y Recepciones */
				boolean archivoConTraspasosRecepciones = false;
				
				if (StringUtils.isBlank(campos[((Integer) indice
						.get(ID_INST_VEND)).intValue()])
						|| campos[((Integer) indice.get(ID_INST_VEND))
								.intValue()].trim().length() != 2
						|| !campos[((Integer) indice.get(ID_INST_VEND))
								.intValue()].matches(PATRON_NUMERICO)) {
					msgError.append(cadenaErrorFormato);
					msgError.append("del id del vendedor");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(ID_INST_VEND))
							.toString());
					numCampoError.append(GUION);
					error = true;
				}

				// FOLIO VENDEDOR
				if (StringUtils.isBlank(campos[((Integer) indice
						.get(FOLIO_INST_VEND)).intValue()])
						|| campos[((Integer) indice.get(FOLIO_INST_VEND))
								.intValue()].trim().length() != 3
						|| !campos[((Integer) indice.get(FOLIO_INST_VEND))
								.intValue()].matches(PATRON_NUMERICO)) {
					msgError.append(cadenaErrorFormato);
					msgError.append("del folio del vendedor");
					msgError.append(GUION);
					numCampoError
							.append(((Integer) indice.get(FOLIO_INST_VEND))
									.toString());
					numCampoError.append(GUION);
					error = true;
				}

				// CUENTA VENDEDOR
				if (StringUtils.isBlank(campos[((Integer) indice
						.get(CUENTA_VEND)).intValue()])
						|| campos[((Integer) indice.get(CUENTA_VEND))
								.intValue()].trim().length() > 4
						|| !campos[((Integer) indice.get(CUENTA_VEND))
								.intValue()].matches(PATRON_NUMERICO)) {
					msgError.append(cadenaErrorFormato);
					msgError.append("de la cuenta del vendedor");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(CUENTA_VEND))
							.toString());
					numCampoError.append(GUION);
					error = true;
				}

				// ID RECEPTOR
				if (StringUtils.isBlank(campos[((Integer) indice.get(ID_RECEP))
						.intValue()])
						|| campos[((Integer) indice.get(ID_RECEP)).intValue()]
								.trim().length() != 2
						|| !campos[((Integer) indice.get(ID_RECEP)).intValue()]
								.matches(PATRON_NUMERICO)) {
					msgError.append(cadenaErrorFormato);
					msgError.append("del id del receptor");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(ID_RECEP))
							.toString());
					numCampoError.append(GUION);
					error = true;
				}

				// FOLIO RECEPTOR
				if (StringUtils.isBlank(campos[((Integer) indice
						.get(FOLIO_RECEP)).intValue()])
						|| campos[((Integer) indice.get(FOLIO_RECEP))
								.intValue()].trim().length() != 3
						|| !campos[((Integer) indice.get(FOLIO_RECEP))
								.intValue()].matches(PATRON_NUMERICO)) {
					msgError.append(cadenaErrorFormato);
					msgError.append("del folio del receptor");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(FOLIO_RECEP))
							.toString());
					numCampoError.append(GUION);
					error = true;
				}

				// CUENTA RECEPTOR
				if (StringUtils.isBlank(campos[((Integer) indice
						.get(CUENTA_RECEP)).intValue()])
						|| campos[((Integer) indice.get(CUENTA_RECEP))
								.intValue()].trim().length() > 4
						|| !campos[((Integer) indice.get(CUENTA_RECEP))
								.intValue()].matches(PATRON_NUMERICO)) {
					msgError.append(cadenaErrorFormato);
					msgError.append("de la cuenta del receptor");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(CUENTA_RECEP))
							.toString());
					numCampoError.append(GUION);
					error = true;
				}
				//*************************************************************
				// se realizan validaciones para saber si se valida la divisa y la boveda
				boolean isVenta = false;
				if(!error){
					if ( (agenteFirmado.getId().equalsIgnoreCase(campos[((Integer) indice.get(ID_INST_VEND)).intValue()]) &&  
			        		agenteFirmado.getFolio().equalsIgnoreCase(campos[((Integer) indice.get(FOLIO_INST_VEND)).intValue()])) &&
			        		!(agenteFirmado.getId() + agenteFirmado.getFolio()).equalsIgnoreCase((campos[((Integer) indice.get(ID_RECEP))
			        		     .intValue()] + campos[((Integer) indice.get(FOLIO_RECEP)).intValue()]))){
			        				
							isVenta = true;
							
			        }else if((agenteFirmado.getId().equalsIgnoreCase(campos[((Integer) indice.get(ID_INST_VEND)).intValue()]) &&  
			        		agenteFirmado.getFolio().equalsIgnoreCase(campos[((Integer) indice.get(FOLIO_INST_VEND)).intValue()])) &&
			        		(agenteFirmado.getId() + agenteFirmado.getFolio()).equalsIgnoreCase((campos[((Integer) indice.get(ID_RECEP))
			        		     .intValue()] + campos[((Integer) indice.get(FOLIO_RECEP)).intValue()]))){
			        	
			        	archivoConTraspasosRecepciones = true;
			        }
				}
				// TV
				if (StringUtils.isBlank(campos[((Integer) indice.get(TV))
						.intValue()])
						|| campos[((Integer) indice.get(TV)).intValue()].trim()
								.length() > 4
						|| !campos[((Integer) indice.get(TV)).intValue()]
								.matches(PATRON_ALFANUMERICO)) {
					msgError.append(cadenaErrorFormato);
					msgError.append("del tv");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(TV)).toString());
					numCampoError.append(GUION);
					error = true;
				}

				// EMISORA
				if (StringUtils.isBlank(campos[((Integer) indice.get(EMISORA))
						.intValue()])
						|| campos[((Integer) indice.get(EMISORA)).intValue()]
								.trim().length() > 7) {
					msgError.append(cadenaErrorFormato);
					msgError.append("de la emisora");
					log.debug("ERROR: " + msgError.toString());
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(EMISORA))
							.toString());
					numCampoError.append(GUION);
					error = true;
				}

				// SERIE
				if (StringUtils.isBlank(campos[((Integer) indice.get(SERIE))
						.intValue()])
						|| campos[((Integer) indice.get(SERIE)).intValue()]
								.trim().length() > 6) {
					msgError.append(cadenaErrorFormato);
					msgError.append("de la serie");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(SERIE))
							.toString());
					numCampoError.append(GUION);
					error = true;
				}

				// CUPON
				if (StringUtils.isBlank(campos[((Integer) indice.get(CUPON))
						.intValue()])
						|| campos[((Integer) indice.get(CUPON)).intValue()]
								.trim().length() > 4
						|| !campos[((Integer) indice.get(CUPON)).intValue()]
								.matches(PATRON_ALFANUMERICO)) {
					msgError.append(cadenaErrorFormato);
					msgError.append("del cupon");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(CUPON))
							.toString());
					numCampoError.append(GUION);
					error = true;
				}

				// CANTIDAD
				if (StringUtils.isBlank(campos[((Integer) indice.get(CANTIDAD))
						.intValue()])
						|| campos[((Integer) indice.get(CANTIDAD)).intValue()]
								.trim().length() > 20
						|| !campos[((Integer) indice.get(CANTIDAD)).intValue()]
						        .trim().matches(PATRON_CANTIDADES)
						|| (campos[((Integer) indice.get(CANTIDAD)).intValue()]
								.indexOf(".") >= 0 && campos[((Integer) indice
								.get(CANTIDAD)).intValue()].indexOf(".") < 10)
						|| !NumberUtils.isNumber(campos[((Integer) indice.get(CANTIDAD)).intValue()].trim())) {
					msgError.append(cadenaErrorFormato);
					msgError.append("de la cantidad");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(CANTIDAD))
							.toString());
					numCampoError.append(GUION);
					error = true;
				}

				// TIPO OPERACION
				
				if (StringUtils
						.isBlank(campos[((Integer) indice.get(TIPO_OPER))
								.intValue()])
						|| campos[((Integer) indice.get(TIPO_OPER)).intValue()]
								.trim().length() != 1
						|| !campos[((Integer) indice.get(TIPO_OPER)).intValue()]
								.matches(PATRON_TIPO_OPER)) {
					msgError.append(cadenaErrorFormato);
					msgError.append("del tipo de operacion");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(TIPO_OPER))
							.toString());
					numCampoError.append(GUION);
					error = true;
				}
				// DIAS PLAZO
				if (StringUtils.isBlank(campos[((Integer) indice
						.get(DIAS_PLAZO)).intValue()])
						|| campos[((Integer) indice.get(DIAS_PLAZO)).intValue()]
								.trim().length() > 3
						|| !campos[((Integer) indice.get(DIAS_PLAZO))
								.intValue()].matches(PATRON_NUMERICO)) {
					msgError.append(cadenaErrorFormato);
					msgError.append("de los dias de plazo");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(DIAS_PLAZO))
							.toString());
					numCampoError.append(GUION);
					error = true;
				}

				// FECHA
				if (StringUtils.isBlank(campos[((Integer) indice.get(FECHA))
						.intValue()])
						|| campos[((Integer) indice.get(FECHA)).intValue()]
								.trim().length() != 11) {
					msgError.append(cadenaErrorFormato);
					msgError.append("de la fecha");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(FECHA))
							.toString());
					numCampoError.append(GUION);
					error = true;
				} else {
					String[] fechaSeparada = campos[((Integer) indice
							.get(FECHA)).intValue()].split("-");
					if (fechaSeparada.length != 3
							|| fechaSeparada[0].length() != 2
							|| !fechaSeparada[0].matches(PATRON_NUMERICO)
							|| fechaSeparada[1].length() != 3
							|| !fechaSeparada[1].matches(PATRON_ALFABETICO)
							|| fechaSeparada[2].length() != 4
							|| !fechaSeparada[2].matches(PATRON_NUMERICO)) {
						msgError.append(cadenaErrorFormato);
						msgError.append("de la fecha");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice.get(FECHA))
								.toString());
						numCampoError.append(GUION);
						error = true;
					}
				}

				// FECHA REP
				// No se valida la fecha rep

				// PRECIO
				if (StringUtils.isBlank(campos[((Integer) indice.get(PRECIO))
						.intValue()])
						|| campos[((Integer) indice.get(PRECIO)).intValue()]
								.trim().length() > 20
						|| !campos[((Integer) indice.get(PRECIO)).intValue()]
								.trim().matches(PATRON_CANTIDADES)
						|| (campos[((Integer) indice.get(PRECIO)).intValue()]
								.indexOf(".") >= 0 && campos[((Integer) indice
								.get(PRECIO)).intValue()].indexOf(".") < 10)
						|| !NumberUtils.isNumber(campos[((Integer) indice.get(PRECIO)).intValue()].trim())) {
					msgError.append(cadenaErrorFormato);
					msgError.append("del precio");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(PRECIO))
							.toString());
					numCampoError.append(GUION);
					error = true;
				}

				// TASA
				if (StringUtils.isBlank(campos[((Integer) indice.get(TASA))
						.intValue()])
						|| campos[((Integer) indice.get(TASA)).intValue()]
								.trim().length() > 20
						|| !campos[((Integer) indice.get(TASA)).intValue()]
								.trim().matches(PATRON_CANTIDADES)
						|| (campos[((Integer) indice.get(TASA)).intValue()]
								.indexOf(".") >= 0 && campos[((Integer) indice
								.get(TASA)).intValue()].indexOf(".") < 10)) {
					msgError.append(cadenaErrorFormato);
					msgError.append("de la tasa");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(TASA))
							.toString());
					numCampoError.append(GUION);
					error = true;
				}

				// PLAZO
				if (StringUtils.isBlank(campos[((Integer) indice.get(PLAZO))
						.intValue()])
						|| campos[((Integer) indice.get(PLAZO)).intValue()]
								.trim().length() > 3
						|| !campos[((Integer) indice.get(PLAZO)).intValue()]
								.matches(PATRON_NUMERICO)
						|| !validaMultiplos(
								campos[((Integer) indice.get(PLAZO)).intValue()],
								24, 8)) {
					msgError.append(cadenaErrorFormato);
					msgError.append("del plazo");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(PLAZO))
							.toString());
					numCampoError.append(GUION);
					error = true;
				}
				
				// FECHA CONCERTACION
				if (StringUtils.isNotBlank(campos[((Integer) indice.get(FECHA_HORA_CIERRE_OPER))
						.intValue()]) && campos[((Integer) indice.get(FECHA_HORA_CIERRE_OPER)).intValue()]
								.trim().length() > 0) {
					String[] fechaHora = campos[((Integer) indice
							.get(FECHA_HORA_CIERRE_OPER)).intValue()].split(" ");
					
					if(fechaHora.length != 2 
							|| !validacionService.validarFechaValida(fechaArchivo2fechaFileTransfer(fechaHora[0]) + " " + fechaHora[1], 
									FORMATO_FECHA_HORA_FILE_TRANSFER_TRAS)) {
						msgError.append("El campo Fecha y Hora de Concertaci\u00f3n no es v\u00e1lido");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice.get(FECHA_HORA_CIERRE_OPER))
								.toString());
						numCampoError.append(GUION);
						error = true;
					} 
				}
				
				// INDEXACION (DIVISA) para operaciones de tipo J,R y V DIVISA OBLIGATORIA
				if( campos[((Integer) indice.get(TIPO_OPER)).intValue()].toString().equalsIgnoreCase(TIPO_OPER_J) || 
						campos[((Integer) indice.get(TIPO_OPER)).intValue()].toString().equalsIgnoreCase(TIPO_OPER_R)||
						campos[((Integer) indice.get(TIPO_OPER)).intValue()].toString().equalsIgnoreCase(TIPO_OPER_V) ){
					if (fileTransfer.getCadena().length() == LONGITUD_MINIMA_TRASP_MD){
						msgError.append("La divisa es requerida para el tipo de operaci\u00f3n ingresada");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice.get(TIPO_OPER)).toString());	
						numCampoError.append(GUION);
						error = true;
					}
					else if(StringUtils.isBlank(campos[((Integer) indice.get(DIVISA)).intValue()]) || 
							campos[((Integer) indice.get(DIVISA)).intValue()].trim().length() != 3 || 
							!campos[((Integer) indice.get(DIVISA)).intValue()].matches(PATRON_ALFABETICO)){
						msgError.append(cadenaErrorFormato);
						msgError.append("de la divisa");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice.get(DIVISA)).toString());	
						numCampoError.append(GUION);
						error = true;
					}
				}	
				/*Modificaci\u00f3n se comenta esta condicion para que pase el campo de divisa aunque no tenga informacion*/
				else if( campos[((Integer) indice.get(TIPO_OPER)).intValue()].toString().trim().equalsIgnoreCase(TIPO_OPER_T) &&
						StringUtils.trim(fileTransfer.getCadena()).length() > LONGITUD_MINIMA_TRASP_MD){ 
					msgError.append(" La Divisa y Boveda de Efectivo no son requeridos ");					
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(DIVISA)).toString());				
					numCampoError.append(GUION);
					numCampoError.append(((Integer) indice.get(BOVEDA_EFECTIVO)).toString());
					numCampoError.append(GUION);
					error = true;
				}
				
				// valida que venga la BOVEDA para los tipos de operacion V,R, y J (COMPRA)
				if( (campos[((Integer) indice.get(TIPO_OPER)).intValue()].toString().equalsIgnoreCase(TIPO_OPER_J) || 
						campos[((Integer) indice.get(TIPO_OPER)).intValue()].toString().equalsIgnoreCase(TIPO_OPER_R)||
						campos[((Integer) indice.get(TIPO_OPER)).intValue()].toString().equalsIgnoreCase(TIPO_OPER_V)) && 
						!isVenta && !archivoConTraspasosRecepciones){ 
						//se valida que sea del tamaño especificado
					 /*Modificación  2012/11/05
					  * se comenta esta condicion para que pase el campo de divisa aunque no tenga informacion*/
						if(fileTransfer.getCadena().length() != LONGITUD_MAXIMA_TRASP_MD){
							msgError.append("La boveda de efectivo es requerida para el tipo de operaci\u00f3n ingresada");
							msgError.append(GUION);
							numCampoError.append(((Integer) indice.get(TIPO_OPER)).toString());	
							numCampoError.append(GUION);
							error = true;
						}						
						else if(StringUtils.isBlank(campos[((Integer) indice.get(BOVEDA_EFECTIVO)).intValue()])||
								campos[((Integer) indice.get(BOVEDA_EFECTIVO)).intValue()].length() != 18 ||
								!campos[((Integer) indice.get(BOVEDA_EFECTIVO)).intValue()].trim().matches(PATRON_NOMBRE_CORTO_BOVEDA)){	
							msgError.append(cadenaErrorFormato);
							msgError.append("de la boveda de efectivo");
							msgError.append(GUION);
							numCampoError.append(((Integer) indice.get(BOVEDA_EFECTIVO)).toString());
							numCampoError.append(GUION);
							error = true;
						}//solo entra al flujo si el receptor y traspasante es la misma cuenta y el archivo contiene boveda
				}else if(archivoConTraspasosRecepciones && fileTransfer.getCadena().length() == LONGITUD_MAXIMA_TRASP_MD &&
						(campos[((Integer) indice.get(TIPO_OPER)).intValue()].toString().equalsIgnoreCase(TIPO_OPER_J) || 
						campos[((Integer) indice.get(TIPO_OPER)).intValue()].toString().equalsIgnoreCase(TIPO_OPER_R)||
						campos[((Integer) indice.get(TIPO_OPER)).intValue()].toString().equalsIgnoreCase(TIPO_OPER_V))){
						if( !StringUtils.isBlank(campos[((Integer) indice.get(BOVEDA_EFECTIVO)).intValue()]) &&
						    (campos[((Integer) indice.get(BOVEDA_EFECTIVO)).intValue()].length() != 18 ||
						    !campos[((Integer) indice.get(BOVEDA_EFECTIVO)).intValue()].trim().matches(PATRON_NOMBRE_CORTO_BOVEDA))){
							
							msgError.append(cadenaErrorFormato);
							msgError.append("de la boveda de efectivo");
							msgError.append(GUION);
							numCampoError.append(((Integer) indice.get(BOVEDA_EFECTIVO)).toString());
							numCampoError.append(GUION);
							error = true;
						}
					
				}
								
				if ((campos[((Integer) indice.get(TIPO_OPER)).intValue()].toString().equalsIgnoreCase(TIPO_OPER_J) || 
						campos[((Integer) indice.get(TIPO_OPER)).intValue()].toString().equalsIgnoreCase(TIPO_OPER_R)||
						campos[((Integer) indice.get(TIPO_OPER)).intValue()].toString().equalsIgnoreCase(TIPO_OPER_V)) && isVenta){
					
					if(StringUtils.trim(fileTransfer.getCadena()).length() == LONGITUD_MAXIMA_TRASP_MD ||
							StringUtils.trim(fileTransfer.getCadena()).length() > LONGITUD_MEDIA_TRASP_MD){					
						msgError.append("La boveda de efectivo no es requerida para el tipo de operaci\u00f3n ingresada");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice.get(TIPO_OPER)).toString());	
						numCampoError.append(GUION);
						error = true;					
					
					}
				}
				
				// Longitud del registro
				//se comenta esta parte ya que sera eliminada del nuevo formato del fileTransfer
			/*	if (fileTransfer.getCadena().length() == LONGITUD_TRASP_MD_MAX) {
					// ID TASA REF
					if (StringUtils.isNotBlank(campos[((Integer) indice
							.get(ID_TASA_REF)).intValue()])
							&& (campos[((Integer) indice.get(ID_TASA_REF))
									.intValue()].trim().length() != 1 || !campos[((Integer) indice
									.get(ID_TASA_REF)).intValue()]
									.matches(PATRON_NUMERICO))) {
						msgError.append(cadenaErrorFormato);
						msgError.append("del id tasa ref");
						msgError.append(GUION);
						numCampoError
								.append(((Integer) indice.get(ID_TASA_REF))
										.toString());
						numCampoError.append(GUION);
						error = true;
					}

					// BL (BANCO LIQUIDADOR)
					if (StringUtils
							.isNotBlank(campos[((Integer) indice.get(BL))
									.intValue()])
							&& (campos[((Integer) indice.get(BL)).intValue()]
									.trim().length() != 2 || !campos[((Integer) indice
									.get(BL)).intValue()]
									.matches(PATRON_ALFABETICO))) {
						msgError.append(cadenaErrorFormato);
						msgError.append("del bl (banco liquidador)");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice.get(BL))
								.toString());
						numCampoError.append(GUION);
						error = true;
					}
				} else if (fileTransfer.getCadena().length() == LONGITUD_TRASP_MD_MIN + 1) {
					// ID TASA REF
					if (StringUtils.isNotBlank(campos[((Integer) indice
							.get(ID_TASA_REF)).intValue()])
							&& (campos[((Integer) indice.get(ID_TASA_REF))
									.intValue()].trim().length() != 1 || !campos[((Integer) indice
									.get(ID_TASA_REF)).intValue()]
									.matches(PATRON_NUMERICO))) {
						msgError.append(cadenaErrorFormato);
						msgError.append("del id tasa ref");
						msgError.append(GUION);
						numCampoError
								.append(((Integer) indice.get(ID_TASA_REF))
										.toString());
						numCampoError.append(GUION);
						error = true;
					}
				}*/

				// ///////////////////////////////////// VALIDACIONES DE NEGOCIO
				// ///////////////////////////////////////
				// Si no hay errores de formato, se validan las reglas de
				// negocio
				if (!error) {
					Date fechaActual = dateUtilsDao.getDateFechaDB();
					Date fechaActualHrCero = dateUtilsDao
							.getFechaHoraCero(fechaActual);
					boolean valida = true;
					int marcaCompra = 0;

					// Valida que el traspasante o el receptor realice la carga
					// Para el ID
					if (!agenteFirmado.getId().equals(
							campos[((Integer) indice.get(ID_INST_VEND))
									.intValue()])) {
						if ((valida)
								&& ((campos[((Integer) indice.get(TIPO_OPER))
										.intValue()]
										.equalsIgnoreCase(TIPO_OPER_V)) || (campos[((Integer) indice
										.get(TIPO_OPER)).intValue()]
										.equalsIgnoreCase(TIPO_OPER_R))
								|| (campos[((Integer) indice.get(TIPO_OPER))
										.intValue()]
										.equalsIgnoreCase(TIPO_OPER_J))
								|| (campos[((Integer) indice.get(TIPO_OPER))
										.intValue()]
										.trim().equalsIgnoreCase(TIPO_OPER_T)))) {
							if (!agenteFirmado.getId().equals(
									campos[((Integer) indice.get(ID_RECEP))
											.intValue()])) {
								msgError
										.append("El id del agente firmado no existe en el archivo");
								msgError.append(GUION);
								numCampoError.append(((Integer) indice
										.get(ID_RECEP)).toString());
								numCampoError.append(GUION);
								error = true;
							} else {
								marcaCompra = 1;
							}
						} else {
							msgError
									.append("El id del agente firmado no corresponde al traspasante");
							msgError.append(GUION);
							numCampoError.append(((Integer) indice
									.get(ID_INST_VEND)).toString());
							numCampoError.append(GUION);
							error = true;
						}
					}
					// Para el Folio
					if (!agenteFirmado.getFolio().equals(
							campos[((Integer) indice.get(FOLIO_INST_VEND))
									.intValue()])) {
						if ((valida)
								&& ((campos[((Integer) indice.get(TIPO_OPER))
											.intValue()]
										.equalsIgnoreCase(TIPO_OPER_V)) || (campos[((Integer) indice
											.get(TIPO_OPER)).intValue()]
											.equalsIgnoreCase(TIPO_OPER_R))
									|| (campos[((Integer) indice.get(TIPO_OPER))
											.intValue()]
											.equalsIgnoreCase(TIPO_OPER_J))
									|| (campos[((Integer) indice.get(TIPO_OPER))
											.intValue()]
											.trim().equalsIgnoreCase(TIPO_OPER_T)))) {
							if (!agenteFirmado.getFolio().equals(
									campos[((Integer) indice.get(FOLIO_RECEP))
											.intValue()])) {
								msgError
										.append("El folio del agente firmado no existe en el archivo");
								msgError.append(GUION);
								numCampoError.append(((Integer) indice
										.get(FOLIO_RECEP)).toString());
								numCampoError.append(GUION);
								error = true;
							} else {
								marcaCompra = 1;
							}
						} else {
							msgError
									.append("El folio del agente firmado no corresponde al traspasante");
							msgError.append(GUION);
							numCampoError.append(((Integer) indice
									.get(FOLIO_INST_VEND)).toString());
							numCampoError.append(GUION);
							error = true;
						}
					}
					

					// Valida que el traspasante exista
					AgenteVO traspasante = new AgenteVO(
							campos[((Integer) indice.get(ID_INST_VEND))
									.intValue()], campos[((Integer) indice
									.get(FOLIO_INST_VEND)).intValue()],
							campos[((Integer) indice.get(CUENTA_VEND))
									.intValue()]);
					try {
						utilService.validaAgente(traspasante, " traspasante ",
								true);
					} catch (BusinessException e) {

						log.debug("WARN : [" + e.getMessage() + "]");

						msgError.append("No existe el traspasante");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice
								.get(ID_INST_VEND)).toString());
						numCampoError.append(GUION);
						numCampoError.append(((Integer) indice
								.get(FOLIO_INST_VEND)).toString());
						numCampoError.append(GUION);
						numCampoError
								.append(((Integer) indice.get(CUENTA_VEND))
										.toString());
						numCampoError.append(GUION);
						error = true;
					}
					// Valida que el receptor exista
					AgenteVO receptor = new AgenteVO(campos[((Integer) indice
							.get(ID_RECEP)).intValue()],
							campos[((Integer) indice.get(FOLIO_RECEP))
									.intValue()], campos[((Integer) indice
									.get(CUENTA_RECEP)).intValue()]);
					try {
						utilService.validaAgente(receptor, " receptor ", true);
					} catch (BusinessException e) {

						log.debug("WARN : [" + e.getMessage() + "]");

						msgError.append("No existe el receptor");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice.get(ID_RECEP))
								.toString());
						numCampoError.append(GUION);
						numCampoError
								.append(((Integer) indice.get(FOLIO_RECEP))
										.toString());
						numCampoError.append(GUION);
						numCampoError.append(((Integer) indice
								.get(CUENTA_RECEP)).toString());
						numCampoError.append(GUION);
						error = true;
					}

					// Valida que la emision exista
					EmisionVO emisionVO = new EmisionVO();
					emisionVO.setIdTipoValor(campos[((Integer) indice.get(TV))
							.intValue()].trim());
					emisionVO.setEmisora(campos[((Integer) indice.get(EMISORA))
							.intValue()].trim());
					emisionVO.setSerie(campos[((Integer) indice.get(SERIE))
							.intValue()].trim());
					emisionVO.setCupon(campos[((Integer) indice.get(CUPON))
							.intValue()].trim());
					EmisionDTO emision = null;
					try {
						emision = emisionDao
								.consultarEmisionPorCupon(emisionVO);
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (emision == null) {
						log.info(" WARNING : La Emision no esta registrada ");
//						msgError
//								.append("La emision no fue encontrada con los datos proporcionados");
//						msgError.append(GUION);
//						numCampoError.append(((Integer) indice.get(TV))
//								.toString());
//						numCampoError.append(GUION);
//						numCampoError.append(((Integer) indice.get(EMISORA))
//								.toString());
//						numCampoError.append(GUION);
//						numCampoError.append(((Integer) indice.get(SERIE))
//								.toString());
//						numCampoError.append(GUION);
//						numCampoError.append(((Integer) indice.get(CUPON))
//								.toString());
//						numCampoError.append(GUION);
//						error = true;
					}

					// Valida cuentas de emision para colocacion primaria
					// (Recompra)
					if (campos[((Integer) indice.get(TIPO_OPER)).intValue()]
							.equalsIgnoreCase(TIPO_OPER_J)) {
						if (campos[((Integer) indice.get(CUENTA_VEND))
								.intValue()]
								.equalsIgnoreCase(CUENTA_EMISION_5000)
								|| campos[((Integer) indice.get(CUENTA_VEND))
										.intValue()]
										.equalsIgnoreCase(CUENTA_EMISION_5001)) {
							if (campos[((Integer) indice.get(CUENTA_RECEP))
									.intValue()]
									.equalsIgnoreCase(CUENTA_EMISION_5000)
									|| campos[((Integer) indice
											.get(CUENTA_RECEP)).intValue()]
											.equalsIgnoreCase(CUENTA_EMISION_5001)) {
								msgError
										.append("Para recompra solo una de las cuentas puede ser de emision");
								msgError.append(GUION);
								numCampoError.append(((Integer) indice
										.get(CUENTA_VEND)).toString());
								numCampoError.append(GUION);
								numCampoError.append(((Integer) indice
										.get(CUENTA_RECEP)).toString());
								numCampoError.append(GUION);
								error = true;
							}
						} else if (!campos[((Integer) indice.get(CUENTA_RECEP))
								.intValue()]
								.equalsIgnoreCase(CUENTA_EMISION_5000)
								&& !campos[((Integer) indice.get(CUENTA_RECEP))
										.intValue()]
										.equalsIgnoreCase(CUENTA_EMISION_5001)) {
							msgError
									.append("Para recompra al menos una de las cuentas debe ser de emision");
							msgError.append(GUION);
							numCampoError.append(((Integer) indice
									.get(CUENTA_VEND)).toString());
							numCampoError.append(GUION);
							numCampoError.append(((Integer) indice
									.get(CUENTA_RECEP)).toString());
							numCampoError.append(GUION);
							error = true;
						}
					}
					/*
					 * se valida para operaciones J, R y V, que la divisa y la Bobeda de efectivo sea la que les corresponde
					 * 
					 */
					String tipoOperacion = campos[((Integer) indice.get(TIPO_OPER)).intValue()].toString();;
					String bovedaEfectivo = null;
					String claveDivisa = null;
					String nombreCortoInstruccion = null;
					///validamos la divisa 
					if(tipoOperacion.equalsIgnoreCase(TIPO_OPER_J) || tipoOperacion.equalsIgnoreCase(TIPO_OPER_R) ||
							tipoOperacion.equalsIgnoreCase(TIPO_OPER_V)){
						claveDivisa = campos[((Integer) indice.get(DIVISA)).intValue()].toString();
						nombreCortoInstruccion = regresaNombreCortoInstruccion(tipoOperacion, MERCADO_DINERO);
						if(!utilService.isTipoInstruccionDivisaValido(nombreCortoInstruccion,claveDivisa)){
							msgError.append("No es v\u00e1lida la combinaci\u00f3n operaci\u00f3n - divisa.");
							msgError.append(GUION);
							numCampoError.append(((Integer) indice.get(TIPO_OPER)).toString());
							numCampoError.append(GUION);
							numCampoError.append(((Integer) indice.get(DIVISA)).toString());
							numCampoError.append(GUION);
							error = true;
						}	
					}
					// se valida el tipoOperacion, divisa y boveda de Efectivo para las compras (J,V y R)
					if( (tipoOperacion.equalsIgnoreCase(TIPO_OPER_J) || tipoOperacion.equalsIgnoreCase(TIPO_OPER_R) ||
							tipoOperacion.equalsIgnoreCase(TIPO_OPER_V)) && !isVenta && !archivoConTraspasosRecepciones){
						marcaCompra = 1;
						bovedaEfectivo = campos[((Integer) indice.get(BOVEDA_EFECTIVO)).intValue()].toString().trim();
						claveDivisa = campos[((Integer) indice.get(DIVISA)).intValue()].toString();	
						//se valida que Exista la Divisa para el tipo de operacion ingresada
						nombreCortoInstruccion = regresaNombreCortoInstruccion(tipoOperacion,MERCADO_DINERO);
							if(!utilService.isTipoInstruccionDivisaBovedaValido(nombreCortoInstruccion,claveDivisa,bovedaEfectivo)){
								msgError.append("No es v\u00e1lida la combinaci\u00f3n operaci\u00f3n, divisa y boveda ");
								msgError.append(GUION);
								numCampoError.append(((Integer) indice.get(TIPO_OPER)).toString());
								numCampoError.append(GUION);
								numCampoError.append(((Integer) indice.get(DIVISA)).toString());
								numCampoError.append(GUION);
								numCampoError.append(((Integer) indice.get(BOVEDA_EFECTIVO)).toString());
								numCampoError.append(GUION);
								error = true;
							}//solo entra al flujo si el receptor y traspasante es la misma cuenta y el archivo contiene boveda				
					}else if( (tipoOperacion.equalsIgnoreCase(TIPO_OPER_J) || tipoOperacion.equalsIgnoreCase(TIPO_OPER_R) ||
							tipoOperacion.equalsIgnoreCase(TIPO_OPER_V))&& fileTransfer.getCadena().length() == LONGITUD_MAXIMA_TRASP_MD && 
							!StringUtils.isBlank(campos[((Integer) indice.get(BOVEDA_EFECTIVO)).intValue()])
							&& archivoConTraspasosRecepciones){
						marcaCompra = 1;
						bovedaEfectivo = campos[((Integer) indice.get(BOVEDA_EFECTIVO)).intValue()].toString().trim();
						claveDivisa = campos[((Integer) indice.get(DIVISA)).intValue()].toString();	
						//se valida que Exista la Divisa para el tipo de operacion ingresada
						nombreCortoInstruccion = regresaNombreCortoInstruccion(tipoOperacion,MERCADO_DINERO);
							if(!utilService.isTipoInstruccionDivisaBovedaValido(nombreCortoInstruccion,claveDivisa,bovedaEfectivo)){
								msgError.append("No es v\u00e1lida la combinaci\u00f3n operaci\u00f3n, divisa y boveda ");
								msgError.append(GUION);
								numCampoError.append(((Integer) indice.get(TIPO_OPER)).toString());
								numCampoError.append(GUION);
								numCampoError.append(((Integer) indice.get(DIVISA)).toString());
								numCampoError.append(GUION);
								numCampoError.append(((Integer) indice.get(BOVEDA_EFECTIVO)).toString());
								numCampoError.append(GUION);
								error = true;
							}						
					}
					else // Determina la divisa, si no trae estas opciones, pone pesos
					if(tipoOperacion.equalsIgnoreCase(TIPO_OPER_T)){
						claveDivisa = "MXN";
						/*claveDivisa = campos[((Integer) indice.get(INDEXACION)).intValue()];
						if (!claveDivisa.equalsIgnoreCase("MXN")
								&& !claveDivisa.equalsIgnoreCase("UDI")
								&& !claveDivisa.equalsIgnoreCase("USD")) {
							
						}*/
					}
					
					// Se valida la fecha de carga que trae el archivo plano
					String fechaFT = fechaArchivo2fechaFileTransfer(campos[((Integer) indice
							.get(FECHA)).intValue()]);
					try {
						if (DateUtil.convierteStringToDate2(fechaFT).compareTo(
								fechaActualHrCero) != 0) {
							msgError.append("La fecha debe ser la actual");
							msgError.append(GUION);
							numCampoError.append(((Integer) indice.get(FECHA)).toString());
							numCampoError.append(GUION);
							error = true;
						}
					} catch (ParseException e) {
						msgError.append("Error en la conversion de la fecha");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice.get(FECHA))
								.toString());
						numCampoError.append(GUION);
						error = true;
					}
					
					// Se valida si hay fecha de concertacion para la operacion y se almacena en un objeto Date
					Date fechaHoraCierreOper = null;
					if (StringUtils.isNotBlank(campos[((Integer) indice.get(FECHA_HORA_CIERRE_OPER))
      						.intValue()]) && campos[((Integer) indice.get(FECHA_HORA_CIERRE_OPER)).intValue()]
      								.trim().length() == 17) {
						String[] fechaHora = campos[((Integer) indice.get(FECHA_HORA_CIERRE_OPER)).intValue()].split(" ");
						String fechaFormatoNum = fechaArchivo2fechaFileTransfer(fechaHora[0]);
						fechaHoraCierreOper = DateUtil.stringToDate(fechaFormatoNum + " " + fechaHora[1], FORMATO_FECHA_HORA_FILE_TRANSFER_TRAS);
					}

					// Valida el valor de la cantidad
					if ((new BigDecimal(campos[((Integer) indice.get(CANTIDAD))
							.intValue()].trim())).compareTo(new BigDecimal(
							"999999999999999")) > 0) {
						msgError.append("Cantidad operada demasiado grande");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice.get(CANTIDAD))
								.toString());
						numCampoError.append(GUION);
						error = true;
					}

					// Valida el tv y determina el tipo de papel de la operacion
					String mercado = "";
					Instrumento cinstrumento = instrumentoDaliDao
							.getInstrumento(campos[((Integer) indice.get(TV))
									.intValue()].trim());
					if (cinstrumento == null) {
						msgError.append("No existe el instrumento");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice.get(TV))
								.toString());
						numCampoError.append(GUION);
						error = true;
					} else {
						InstrumentoVO instrumento = UtilsVOCatalogo
								.getInstanceInstrumento(cinstrumento);
						if (StringUtils.isNotBlank(instrumento.getMercado())) {
							mercado = instrumento.getMercado().trim();
						}

						// Se calcula la fecha de liquidacion y de reporto en
						// base al tipo de papel y tipo de operacion
						Date fechaLiquidacion = fechaActualHrCero;
						String origen = "";
						if (Integer.parseInt(campos[((Integer) indice
								.get(PLAZO)).intValue()].trim()) > 0) {
							fechaLiquidacion = utilService.agregarDiasHabiles(
									fechaActualHrCero, Integer
											.parseInt(campos[((Integer) indice
													.get(PLAZO)).intValue()]
													.trim()) / 24);
						}
						Date fechaReporto = fechaLiquidacion;
						if (mercado.equalsIgnoreCase(PAPEL_BANCARIO)) {
							origen = "MERSECPB";
							if (campos[((Integer) indice.get(TIPO_OPER))
									.intValue()].equalsIgnoreCase(TIPO_OPER_C)
									|| campos[((Integer) indice.get(TIPO_OPER))
											.intValue()]
											.equalsIgnoreCase(TIPO_OPER_D)) {
								msgError
										.append("El tipo de operacion es incorrecto para emisiones bancarias");
								msgError.append(GUION);
								numCampoError.append(((Integer) indice
										.get(TIPO_OPER)).toString());
								numCampoError.append(GUION);
								error = true;
							}
							if (campos[((Integer) indice.get(TIPO_OPER))
									.intValue()].equalsIgnoreCase(TIPO_OPER_R)
									|| campos[((Integer) indice.get(TIPO_OPER))
											.intValue()]
											.equalsIgnoreCase(TIPO_OPER_E)) {
								if (Integer.parseInt(campos[((Integer) indice
										.get(DIAS_PLAZO)).intValue()].trim()) > 0) {
									fechaReporto = DateUtil
											.addDays(
													fechaReporto,
													Integer
															.parseInt(campos[((Integer) indice
																	.get(DIAS_PLAZO))
																	.intValue()]
																	.trim()));
									Calendar cal = Calendar.getInstance();
									List<DiaInhabil> listaDiasInhabiles = diaInhabilDaliDao
											.findDiasInhabilesByMonthYear(cal
													.get(Calendar.MONTH), cal
													.get(Calendar.YEAR));
									for (DiaInhabil d : listaDiasInhabiles) {
										Calendar diaInhabil = Calendar
												.getInstance();
										diaInhabil.setTime(d.getDiaInhabil());
										if (diaInhabil.getTime().compareTo(
												fechaReporto) == 0) {
											msgError
													.append("La fecha reporto es un dia inhabil");
											msgError.append(GUION);
											error = true;
											break;
										}
									}

									if (emision != null) {
										if (emision.getFechaVencimiento() != null
												&& fechaReporto
														.compareTo(emision
																.getFechaVencimiento()) >= 0) {
											msgError
													.append("La emision vence antes de la fecha reporto");
											msgError.append(GUION);
											error = true;
										}
									}
								}
							} else {
								if (Integer.parseInt(campos[((Integer) indice
										.get(DIAS_PLAZO)).intValue()].trim()) != 0) {
									msgError
											.append("Los dias de plazo "
													+ "deben ser cero para operaciones "
													+ "que no son reportos");
									msgError.append(GUION);
									numCampoError.append(((Integer) indice
											.get(DIAS_PLAZO)).toString());
									numCampoError.append(GUION);
									error = true;
								} else {
									Calendar cal = Calendar.getInstance();
									cal.set(1900, 0, 1, 0, 0, 0);
									fechaReporto = dateUtilsDao
											.getFechaHoraCero(cal.getTime());
								}
							}
						} else if (mercado
								.equalsIgnoreCase(PAPEL_GUBERNAMENTAL)) {
							origen = "MERSECPG";
							if (campos[((Integer) indice.get(TIPO_OPER))
									.intValue()].equalsIgnoreCase(TIPO_OPER_R)
									|| campos[((Integer) indice.get(TIPO_OPER))
											.intValue()]
											.equalsIgnoreCase(TIPO_OPER_D)
									|| campos[((Integer) indice.get(TIPO_OPER))
											.intValue()]
											.equalsIgnoreCase(TIPO_OPER_E)
									|| campos[((Integer) indice.get(TIPO_OPER))
											.intValue()]
											.equalsIgnoreCase(TIPO_OPER_C)
									|| campos[((Integer) indice.get(TIPO_OPER))
											.intValue()]
											.equalsIgnoreCase(TIPO_OPER_Y)) {
								if (Integer.parseInt(campos[((Integer) indice
										.get(DIAS_PLAZO)).intValue()].trim()) > 0) {
									fechaReporto = DateUtil
											.addDays(
													fechaReporto,
													Integer
															.parseInt(campos[((Integer) indice
																	.get(DIAS_PLAZO))
																	.intValue()]
																	.trim()));
									Calendar cal = Calendar.getInstance();
									List<DiaInhabil> listaDiasInhabiles = diaInhabilDaliDao
											.findDiasInhabilesByMonthYear(cal
													.get(Calendar.MONTH), cal
													.get(Calendar.YEAR));
									for (DiaInhabil d : listaDiasInhabiles) {
										Calendar diaInhabil = Calendar
												.getInstance();
										diaInhabil.setTime(d.getDiaInhabil());
										if (diaInhabil.getTime().compareTo(
												fechaReporto) == 0) {
											msgError
													.append("La fecha reporto es un dia inhabil");
											msgError.append(GUION);
											error = true;
											break;
										}
									}
								}

								if (emision != null) {
									if (emision.getFechaVencimiento() != null
											&& fechaReporto.compareTo(emision
													.getFechaVencimiento()) >= 0) {
										msgError
												.append("La emision vence antes de la fecha reporto");
										msgError.append(GUION);
										error = true;
									}
								}
							} else {
								if (Integer.parseInt(campos[((Integer) indice
										.get(DIAS_PLAZO)).intValue()].trim()) != 0) {
									msgError
											.append("Los dias de plazo deben ser "
													+ "cero para operaciones que no son reportos");
									msgError.append(GUION);
									numCampoError.append(((Integer) indice
											.get(DIAS_PLAZO)).toString());
									numCampoError.append(GUION);
									error = true;
								} else {
									Calendar cal = Calendar.getInstance();
									cal.set(1900, 0, 1, 0, 0, 0);
									fechaReporto = dateUtilsDao
											.getFechaHoraCero(cal.getTime());
								}
							}
						} else {
							msgError.append("No se pudo obtener un mercado");
							msgError.append(GUION);
							numCampoError.append(((Integer) indice.get(TV))
									.toString());
							numCampoError.append(GUION);
							error = true;
						}

						// Calcula el valor de la sociedad_serie (Estatus del
						// movimiento)
						
						// SE ELIMINA, PENDIENTE REVISAR
						/*String sociedad = "";
						if (campos[((Integer) indice.get(TIPO_OPER)).intValue()]
								.equalsIgnoreCase(TIPO_OPER_T)
								|| (fileTransfer.getCadena().trim().length() == LONGITUD_TRASP_MD_MAX
										&& campos[((Integer) indice.get(BL))
												.intValue()]
												.equalsIgnoreCase(BL) && traspasante
										.equals(receptor))) {

							sociedad = SOCIEDAD_SERIE_P;
						} else {
							sociedad = SOCIEDAD_SERIE_C;
						}*/

						// Determina el origen de la aplicacion
						
						//SE ELIMINA, PENDIENTE REVISAR
						/*String origenAplicacion = "";
						if (fileTransfer.getCadena().length() == LONGITUD_TRASP_MD_MAX
								&& campos[((Integer) indice.get(BL)).intValue()]
										.equalsIgnoreCase(BL)) {
							origenAplicacion = "09";
						} else {
							origenAplicacion = "01";
						}*/

						// Graba el registro en la tabla temporal de operaciones
						// catalogo..filetransfer_operaciones
						if (!error) {
							FileTransferPK fileTransferPK = fileTransfer2FileTransferOperacionesPK(fileTransfer);

							Map<String, Object> valores = new HashMap<String, Object>();
							valores.put(DIVISA, claveDivisa);
							valores.put(FECHA_LIQ, fechaLiquidacion);
							valores.put(FECHA_CONCER, dateUtilsDao
									.getFechaHoraCero(dateUtilsDao
											.getDateFechaDB()));
							//se elimina esta fecha del nuevo fromato del transfer
							if(tipoOperacion.equalsIgnoreCase(TIPO_OPER_R)){
								valores.put(FECHA_REP, fechaReporto);
							}
							
							valores.put(MERCADO, mercado);
							valores.put(ORIGEN, origen);
							valores.put(FECHA_HORA_CIERRE_OPER_KEY, fechaHoraCierreOper);
							//SE MODIFICA, PENDIENDE DE VERIFICAR
							//valores.put(ORIGEN_APLICACION, origenAplicacion);
							valores.put(PRECIO, (new BigDecimal(
									campos[((Integer) indice.get(PRECIO))
											.intValue()].trim())).divide(
									new BigDecimal("100000000"), 8,
									BigDecimal.ROUND_HALF_UP));
							//SE MODIFICA, PENDIENDE DE VERIFICAR
							//valores.put(SOCIEDAD_SERIE, sociedad);
							valores.put(TASA, (new BigDecimal(
									campos[((Integer) indice.get(TASA))
											.intValue()].trim())).divide(
									new BigDecimal("100000000"), 6,
									BigDecimal.ROUND_HALF_UP));
							valores.put(IMPORTE, new BigDecimal(
									campos[((Integer) indice.get(CANTIDAD))
											.intValue()].trim()).multiply(
									(BigDecimal) valores.get(PRECIO)).setScale(
									2, BigDecimal.ROUND_HALF_UP));
							valores.put(MARCA_COMPRA, marcaCompra);
							log.debug("Valores.." + valores);

							Object o = null;
							try {
								o = grabaFileTransferOperaciones(
										fileTransferPK, valores);
							} catch (Exception e) {
								e.printStackTrace();
							}

							if (o == null) {
								msgError
										.append("No se puede almacenar la informacion "
												+ "en FileTransferOperaciones");
								msgError.append(GUION);
								error = true;
							}
						}
						System.out.println("msgError[" + msgError.toString()
								+ "]");
					}

				}

			}

			if (error) {
				fileTransfer.setEstado(ESTADO_ERROR);
				if (numCampoError.length() > 0) {
					fileTransfer.setCamposError(numCampoError.toString()
							.substring(0, numCampoError.length() - 1));
				}
				fileTransfer.setError(msgError.toString().substring(0,
						msgError.length() - 1));
				fileTransferDao.update(fileTransfer);
			}

			ultimoRegistro = i;
			long finalProceso = Calendar.getInstance().getTimeInMillis();
			if ((finalProceso - inicioProceso) > 10000) {
				break;
			}
		}

		if ((ultimoRegistro + 1) == listaFileTransfer.size()) {
			return -1;
		}

		return ultimoRegistro + 1;
	}
	
	
	/**
	 * Metodo para convertir el tipoOperacion(J,R y V) ingresado en el file de MD en el nombre corto de la instruccion
	 * @param tipo de Operacion(J,R y V)
	 * return nombre corto de la instruccion
	 */
	private String regresaNombreCortoInstruccion(String tipoInstruccion,String tipoOperacion){
		String nombreCortoInstruccion = null;
		if(TIPO_OPER_R.equalsIgnoreCase(tipoInstruccion)){
			nombreCortoInstruccion = TIPO_OPER_REPO;
		}
		else if(TIPO_OPER_V.equalsIgnoreCase(tipoInstruccion) && tipoOperacion.equals(MERCADO_DINERO)){
			nombreCortoInstruccion = TIPO_OPER_COVE;
		}
		else if(TIPO_OPER_V.equalsIgnoreCase(tipoInstruccion) && tipoOperacion.equals(MERCADO_CAPITALES)){
			nombreCortoInstruccion = TIPO_OPER_DVPC;
		}	
		else if(TIPO_OPER_J.equalsIgnoreCase(tipoInstruccion)){
			nombreCortoInstruccion = TIPO_OPER_CORE;
		}
		return nombreCortoInstruccion;
	}
	
	
	/**
	 * Arma el VO con los datos necesrios de cada campo del archivo plano para
	 * poder presentarlos en pantalla
	 * 
	 * @param fileTransfer
	 * @param tipoProceso
	 * @return Una lista de instancias de la clase CampoArchivoVO
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	private List generaDatos(FileTransfer fileTransfer, String tipoProceso)
			throws BusinessException {

		log.trace("Entrando a FileTransferServiceImpl.generaDatos()");

		/* Valida parametros */
		if (fileTransfer == null) {
			throw new BusinessDataException(errorResolver.getMessage("J0026",
					"el objeto fileTransfer"), "J0026");
		}
		if (StringUtils.isBlank(tipoProceso)) {
			throw new BusinessException(errorResolver.getMessage("J0026",
					" el tipo de proceso "), "J0026");
		}

		/* Obtiene lista de campos con error */
		String[] numeroCampoError = null;
		if (fileTransfer.getCamposError() != null) {
			numeroCampoError = fileTransfer.getCamposError().split("-");
			utilService.validaDTONoNulo(numeroCampoError,
					" numero de campo con error ");
		}
		List listaCamposError = new ArrayList();
		for (int i = 0; numeroCampoError != null && i < numeroCampoError.length; i++) {
			if (StringUtils.isNotBlank(numeroCampoError[i])
					&& !listaCamposError.contains(numeroCampoError[i])) {
				listaCamposError.add(numeroCampoError[i]);
			}
		}

		/* Define variables comunes */
		String[] campos = null;
		Map nombreCampo = new TreeMap();
		Map tipoDatoCampos = new HashMap();
		tipoDatoCampos.put(TIPO_DATO_BIG_DECIMAL, new ArrayList());
		tipoDatoCampos.put(TIPO_DATO_DATE, new ArrayList());
		tipoDatoCampos.put(TIPO_DATO_DATE_TIME, new ArrayList());
		tipoDatoCampos.put(TIPO_DATO_INTEGER, new ArrayList());
		tipoDatoCampos.put(TIPO_DATO_STRING, new ArrayList());

		/*
		 * Determina tipo de operacion y obtiene informacion correspondiente a
		 * cada layout
		 */
		if (tipoProceso.equalsIgnoreCase(TRASPASO_MD)) {
			/* Obtiene los campos correspondientes al layout */
			campos = camposTraspasosMD(fileTransfer.getCadena());
			/* Obtiene el mapa de nombres de campos */
			nombreCampo = indicesNombresTraspasosMD(false);
			if (campos != null) {
				if(fileTransfer.getCadena().length() == LONGITUD_MINIMA_TRASP_MD){
					nombreCampo.remove(new Integer(19));
				}
				//SE MODIFICA ESTA PARTE
				/*if (fileTransfer.getCadena().length() == LONGITUD_TRASP_MD_MIN) {
					nombreCampo.remove(new Integer(20)); //ID_TASA_REF
					nombreCampo.remove(new Integer(21)); //BL
				} else if (fileTransfer.getCadena().length() == LONGITUD_TRASP_MD_MIN + 1) {
					nombreCampo.remove(new Integer(22)); //BL
				}*/
				/* Coloca los campos por tipos de datos */
				tipoDatoCampos = tiposDatosTraspasoMD(nombreCampo,
						tipoDatoCampos);
			}
		} else if (tipoProceso.equalsIgnoreCase(TRASPASO_MC)) {
			/* Obtiene los campos correspondientes al layout */
			campos = camposTraspasosMC(fileTransfer.getCadena());
			/* Obtiene el mapa de nombres de campos */
			nombreCampo = indicesNombresTraspasosMC(false);
			/* Coloca los campos por tipos de datos */
			tipoDatoCampos = tiposDatosTraspasoMC(nombreCampo, tipoDatoCampos);
		}else if (tipoProceso.equalsIgnoreCase(DEPOSITO_EFECTIVO)){
			/* Obtiene los campos correspondientes al layout */
			campos = camposDepositoEfectivo(fileTransfer.getCadena());
			/* Obtiene el mapa de nombres de campos */
			nombreCampo = indicesNombresDepositosEfectivo(false);
			/* Coloca los campos por tipos de datos */
			tipoDatoCampos = tiposDatosDepositos(nombreCampo, tipoDatoCampos);
		}else if (tipoProceso.equalsIgnoreCase(CUENTAS_BCOM)){
			/* Obtiene los campos correspondientes al layout */
			campos = camposCuentasBancaComercial(fileTransfer.getCadena());
			/* Obtiene el mapa de nombres de campos */
			nombreCampo = indicesNombresCuentasBCom(false);
			/* Coloca los campos por tipos de datos */
			tipoDatoCampos = tiposDatosCuentasBCOM(nombreCampo, tipoDatoCampos);
		}else if (tipoProceso.equalsIgnoreCase(RETIROS_BCOM)){
			/* Obtiene los campos correspondientes al layout */
			campos = camposRetirosBancaComercial(fileTransfer.getCadena());
			/* Obtiene el mapa de nombres de campos */
			nombreCampo = indicesNombresRetirosBCom(false);
			/* Coloca los campos por tipos de datos */
			tipoDatoCampos = tiposDatosRetirosBCOM(nombreCampo, tipoDatoCampos);
		}else if(tipoProceso.equalsIgnoreCase(TRASPASOS_EFECTIVO)) {
			campos = camposTraspasosEfectivo(fileTransfer.getCadena());
			nombreCampo = indicesNombresTraspasosEfectivo(false);
			
			tipoDatoCampos = tiposDatosRetirosEfectivo(nombreCampo, tipoDatoCampos);
		}

		/* Genera el objeto de salida del servicio */
		List listaCamposArchivo = new ArrayList();
		for (int i = 0; campos != null && i < campos.length; i++) {
			CampoArchivoVO campoArchivoVO = new CampoArchivoVO();
			campoArchivoVO.setLongitud(campos[i].toString().length());
			if (listaCamposError != null
					&& listaCamposError.contains(String.valueOf(i))) {
				campoArchivoVO.setTieneError(true);
			}
			campoArchivoVO.setNombre((String) nombreCampo.get(new Integer(i)));
			if (((List) tipoDatoCampos.get(TIPO_DATO_BIG_DECIMAL))
					.contains(new Integer(i))) {
				if (StringUtils.isNotBlank(campos[i])) {
					if (campos[i].matches(PATRON_NUMERICO)) {
						campoArchivoVO
								.setValor(new BigDecimal(campos[i].trim()));
						campoArchivoVO.setFormato("###############0.0##");
					} else {
						campoArchivoVO.setValor(new String(campos[i].trim()));
					}
				}
			} else if (((List) tipoDatoCampos.get(TIPO_DATO_INTEGER))
					.contains(new Integer(i))) {
				if (StringUtils.isNotBlank(campos[i])) {
					if (campos[i].matches(PATRON_NUMERICO)) {
						campoArchivoVO.setValor(new Integer(campos[i].trim()));
						campoArchivoVO.setFormato("###################0");
					} else {
						campoArchivoVO.setValor(new String(campos[i].trim()));
					}
				}
			} else if (((List) tipoDatoCampos.get(TIPO_DATO_DATE))
					.contains(new Integer(i))) {
				Date fecha = null;
				try {
					if (StringUtils.isNotBlank(campos[i])) {
						String fechaFormateada = fechaArchivo2fechaFileTransfer(campos[i]);
						if (StringUtils.isNotBlank(fechaFormateada)) {
							fecha = DateUtil
									.convierteStringToDate2(fechaFormateada);
							campoArchivoVO.setFormato("dd/MM/yyyy");
							campoArchivoVO.setValor(fecha);
						}
					}
				} catch (Exception e) {
					campoArchivoVO.setTieneError(true);
				}
			} else if (((List) tipoDatoCampos.get(TIPO_DATO_DATE_TIME))
					.contains(new Integer(i))) {
				Date fecha = null;
				try {
					if (StringUtils.isNotBlank(campos[i].trim())) {
						String[] fechaHora = campos[i].split(" ");
						String fechaFormateada = fechaArchivo2fechaFileTransfer(fechaHora[0]) + " " + fechaHora[1];
						if (StringUtils.isNotBlank(fechaFormateada)) {
							fecha = DateUtil.stringToDate(fechaFormateada, FORMATO_FECHA_HORA_FILE_TRANSFER_TRAS);
							campoArchivoVO.setFormato("dd/MM/yyyy HH:mm");
						}
						campoArchivoVO.setValor(fecha);
					} 
				} catch (Exception e) {
					campoArchivoVO.setTieneError(true);
				}
			} else if (((List) tipoDatoCampos.get(TIPO_DATO_STRING))
					.contains(new Integer(i))) {
				campoArchivoVO.setValor(new String(campos[i]));
			}

			listaCamposArchivo.add(campoArchivoVO);
		}

		return listaCamposArchivo;
	}

	/**
	 * Obtiene el arreglo de campos correspondiente al layout del filetransfer
	 * porporcionado por la lista y las logitudes
	 * 
	 * @param cadena
	 * @param posiciones
	 * @return Arreglo de String
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	private String[] obtieneCampos(String cadena, List posiciones)
			throws BusinessException {

		log.trace("Entrando a FileTransferServiceImpl.obtieneCampos()");

		if (StringUtils.isBlank(cadena)) {
			throw new BusinessDataException(errorResolver.getMessage("J0026",
					new Object[] { " la cadena de informacion " }), "J0026");
		}
		if (posiciones == null || posiciones.isEmpty()) {
			throw new BusinessDataException(errorResolver.getMessage("J0046",
					new Object[] { " de posiciones " }), "J0046");
		}

		String campo = null;
		int posIni = 0;
		int posFin = 0;
		String[] campos = new String[posiciones.size()];
		for (int i = 0; i < posiciones.size(); i++) {
			posFin = posIni + ((Integer) posiciones.get(i)).intValue();
			
			if(posFin >= cadena.length())
				posFin = cadena.length();
			
			campo = cadena.substring(posIni, posFin);
			if (campo != null) {
				campo = campo.toUpperCase();
			}
			campos[i] = campo;
			posIni = posFin;
		}

		return campos;
	}

	/**
	 * Obtiene los campos correspondientes al layout de traspasos de mercado
	 * dinero
	 * 
	 * @param cadena
	 * @return Arreglo de String
	 * @throws BusinessException
	 */
	private String[] camposTraspasosMD(String cadena) throws BusinessException {

		log.trace("Entrando a FileTransferServiceImpl.camposTraspasosMD()");

		if (StringUtils.isBlank(cadena)) {
			throw new BusinessDataException(errorResolver.getMessage("J0026",
					new Object[] { " la cadena de informacion " }), "J0026");
		}
		if(cadena.length()== LONGITUD_MAXIMA_TRASP_MD || cadena.length() == LONGITUD_MINIMA_TRASP_MD || 
				cadena.length() == LONGITUD_MEDIA_TRASP_MD){
		List<Integer> posiciones = new ArrayList<Integer>();
		//posiciones.add(new Integer(12));
		posiciones.add(new Integer(2));
		posiciones.add(new Integer(3));
		posiciones.add(new Integer(4));
		posiciones.add(new Integer(2));
		posiciones.add(new Integer(3));
		posiciones.add(new Integer(4));
		posiciones.add(new Integer(4));
		posiciones.add(new Integer(7));
		posiciones.add(new Integer(6));
		posiciones.add(new Integer(4));
		posiciones.add(new Integer(20));
		posiciones.add(new Integer(1));
		posiciones.add(new Integer(3));
		posiciones.add(new Integer(11));
		//se elimina la fecha Rep.
		//posiciones.add(new Integer(11));
		posiciones.add(new Integer(20));
		posiciones.add(new Integer(20));
		posiciones.add(new Integer(3));
		posiciones.add(new Integer(17));
		if(cadena.length() == LONGITUD_MEDIA_TRASP_MD){
			posiciones.add(new Integer(3));//divisa
		}else if(cadena.length() == LONGITUD_MAXIMA_TRASP_MD){
				posiciones.add(new Integer(3));//divisa
				posiciones.add(new Integer(18));
		}
		
		//se eliminan los ultimos 2 campos
		/*if (cadena.length() == LONGITUD_TRASP_MD_MIN + 1) {
			posiciones.add(new Integer(1));
		} else if (cadena.length() == LONGITUD_TRASP_MD_MAX) {
			posiciones.add(new Integer(1));
			posiciones.add(new Integer(2));
		}*/
		
			return obtieneCampos(cadena, posiciones);
		}
		/*if (cadena.length() != LONGITUD_TRASP_MD_MAX
				&& cadena.length() != LONGITUD_TRASP_MD_MIN
				&& cadena.length() != LONGITUD_TRASP_MD_MIN + 1) {
			return null;
		}*/
			return null;
		
	}

	/**
	 * Cambiar cadenas por el uso de constantes Convierte el formato de
	 * fecha del archivo plano DE "dd-MMM-yyyy" A "MM/dd/yyyy"
	 * 
	 * @param fechaArchivo
	 * @return String
	 */
	private String fechaArchivo2fechaFileTransfer(String fechaArchivo) {

		log
				.trace("Entrando a FileTransferServiceImpl.fechaArchivo2fechaFileTransfer()");

		if (StringUtils.isNotBlank(fechaArchivo)) {
			Map<String, String> meses = new HashMap<String, String>();
			meses.put("JAN", String.valueOf("01"));
			meses.put("FEB", String.valueOf("02"));
			meses.put("MAR", String.valueOf("03"));
			meses.put("APR", String.valueOf("04"));
			meses.put("MAY", String.valueOf("05"));
			meses.put("JUN", String.valueOf("06"));
			meses.put("JUL", String.valueOf("07"));
			meses.put("AUG", String.valueOf("08"));
			meses.put("SEP", String.valueOf("09"));
			meses.put("OCT", String.valueOf("10"));
			meses.put("NOV", String.valueOf("11"));
			meses.put("DEC", String.valueOf("12"));

			Map<String, String> mesesEsp = new HashMap<String, String>();
			meses.put("ENE", String.valueOf("01"));
			meses.put("ABR", String.valueOf("04"));
			meses.put("AGO", String.valueOf("08"));
			meses.put("DIC", String.valueOf("12"));
			List<String> elementosFecha = new ArrayList<String>();
			elementosFecha.add(fechaArchivo.substring(0, 2));
			elementosFecha.add(meses.get(fechaArchivo.substring(3, 6)
					.toUpperCase()) != null ? meses.get(fechaArchivo.substring(
					3, 6).toUpperCase()) : mesesEsp.get(fechaArchivo.substring(
					3, 6).toUpperCase()));
			elementosFecha.add(fechaArchivo.substring(7));

			return elementosFecha.get(1) + "/" + elementosFecha.get(0) + "/"
					+ elementosFecha.get(2);
		}

		return "";
	}

	/**
	 * Valida que los multiplos sean correctos de acuerdo a la cantidad
	 * proporcionada
	 * 
	 * @param cadena
	 * @param base
	 * @param numElementos
	 * @return boolean
	 */
	private boolean validaMultiplos(String cadena, int base, int numElementos) {

		log.trace("Entrando a FileTransferServiceImpl.validaMultiplos()");

		if (StringUtils.isNotBlank(cadena) && cadena.matches(PATRON_NUMERICO)
				&& base > 0 && numElementos > 0) {
			int iCadena = Integer.parseInt(cadena.trim());
			if (iCadena == 0) {
				return true;
			}
			List<String> multiplos = new ArrayList<String>();
			for (int i = 1; i <= numElementos; i++) {
				multiplos.add(Integer.toString(base * i));
			}
			if (multiplos.contains(Integer.toString(iCadena))) {
				return true;
			}
		}

		return false;

	}

	/**
	 * Obtiene la informacion de la tabla temporal catalogo..filetransfer
	 * 
	 * @param id
	 * @param folio
	 * @param tipoProceso
	 * @return Lista de instancias del POJO FileTransfer
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	private List<FileTransfer> obtieneListaFileTransfer(String id, String folio, String tipoProceso) {
		log.trace("Entrando a FileTransferServiceImpl.obtieneListaFileTransfer()");
		return fileTransferDao.getByIdFolioTipo(id, folio, tipoProceso);
	}

	/**
	 * Obtiene la informacion de la tabla temporal catalogo..filetransfer
	 * operaciones
	 * 
	 * @param id
	 * @param folio
	 * @param tipoProceso
	 * @return Lista de instancias del POJO FileTransfer
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	private List<FileTransferOperaciones> obtieneListaFileTransferOperaciones(
			String id, String folio, String tipoProceso)
			throws BusinessException {

		log
				.trace("Entrando a FileTransferServiceImpl.obtieneListaFileTransfer()");

		List listaFileTransfer = fileTransferOperacionesDao.getByIdFolioTipo(
				id, folio, tipoProceso);

		return listaFileTransfer;
	}

	/**
	 * Valida que la listaFileTransfer no este nula o vacia
	 * 
	 * @param listaFileTransfer
	 * @return
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	private List validaListaFileTransfer(List listaFileTransfer)
			throws BusinessException {

		log
				.trace("Entrando a FileTransferServiceImpl.validaListaFileTransfer()");

		if (listaFileTransfer == null || listaFileTransfer.isEmpty()) {
			// throw new BusinessException(errorResolver.getMessage("J0046",
			// new Object[] { "listaFileTransfer" }));
		}

		return listaFileTransfer;
	}

	/**
	 * Obtiene la informacion de la tabla temporal
	 * catalogo..filetransfer_operaciones correspondiente a la llave
	 * proporcionada (PK)
	 * 
	 * @param fileTransferPK
	 * @return Una instancia de la clase FileTransferOperaciones
	 * @throws BusinessException
	 */
	private FileTransferOperaciones obtieneFileTransferOperacionesByPK(
			FileTransferPK fileTransferPK) throws BusinessException {

		log
				.trace("Entrando a FileTransferServiceImpl.obtieneFileTransferOperacionesByPK()");

		return (FileTransferOperaciones) fileTransferOperacionesDao.getByPk(
				FileTransferOperaciones.class, fileTransferPK);
	}

	/**
	 * Obtiene el objeto PK de operaciones desde el objeto FileTransfer
	 * 
	 * @param fileTransfer
	 * @return Una instancia de la clase FileTransferOperacionesPK
	 * @throws BusinessException
	 */
	private FileTransferPK fileTransfer2FileTransferOperacionesPK(
			FileTransfer fileTransfer) throws BusinessException {

		log
				.trace("Entrando a FileTransferServiceImpl.fileTransfer2FileTransferOperacionesPK()");

		utilService.validaDTONoNulo(fileTransfer, " fileTransfer ");

		FileTransferPK fileTransferPK = new FileTransferPK();
		fileTransferPK.setIdInst(fileTransfer.getFileTransferPK().getIdInst());
		fileTransferPK.setFolioInst(fileTransfer.getFileTransferPK()
				.getFolioInst());
		fileTransferPK
				.setTipoReg(fileTransfer.getFileTransferPK().getTipoReg());
		fileTransferPK.setConsec(fileTransfer.getFileTransferPK().getConsec());

		return fileTransferPK;
	}

	/**
	 * Graba el registro correspondiente a la llave proporcionada (PK), los
	 * valores grabados estan indicados por valores
	 * 
	 * @param fileTransferPK
	 * @param valores
	 * @return Serializable Una nueva instancia de FileTransferOperaciones que
	 *         ha sido almacenada en la BD
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	private Serializable grabaFileTransferOperaciones(
			FileTransferPK fileTransferPK, Map valores)
			throws BusinessException {

		log
				.trace("Entrando a FileTransferServiceImpl.grabaFileTransferOperaciones()");

		if (fileTransferPK == null
				|| StringUtils.isBlank(fileTransferPK.getIdInst())
				|| StringUtils.isBlank(fileTransferPK.getFolioInst())
				|| StringUtils.isBlank(fileTransferPK.getTipoReg())
				|| fileTransferPK.getConsec() == null) {
			throw new BusinessException(
					"No existe el objeto PK para operaciones ["
							+ ReflectionToStringBuilder
									.reflectionToString(fileTransferPK) + "]");
		}
		if (valores == null) {
			throw new BusinessException("No existe el mapa de valores");
		}

		FileTransferOperaciones fileTransferOperaciones = new FileTransferOperaciones();
		fileTransferOperaciones.setFileTransferPK(fileTransferPK);
		fileTransferOperaciones.setFechaLiquidacion((Date) valores
				.get(FECHA_LIQ));
		//SE ELIMINO ESTE DATO
		if(valores.get(FECHA_REP)!= null){
			fileTransferOperaciones.setFechaReporto((Date) valores.get(FECHA_REP));
		}
		
		fileTransferOperaciones.setFechaConcertacion((Date) valores
				.get(FECHA_CONCER));
		fileTransferOperaciones.setFechaHoraCierreOper((Date) valores.get(FECHA_HORA_CIERRE_OPER_KEY));
		fileTransferOperaciones.setPrecioTitulo((BigDecimal) valores
				.get(PRECIO));
		fileTransferOperaciones.setTasaPremio((BigDecimal) valores.get(TASA));
		fileTransferOperaciones.setImporte((BigDecimal) valores.get(IMPORTE));
		fileTransferOperaciones.setMercado((String) valores.get(MERCADO));
		//pendiente de revisar
		//fileTransferOperaciones.setOrigenAplicacion((String) valores
			//	.get(ORIGEN_APLICACION));
		fileTransferOperaciones.setOrigen((String) valores.get(ORIGEN));
		fileTransferOperaciones.setDivisa((String) valores.get(DIVISA));
		//pendiente de revisar
		//fileTransferOperaciones.setSociedadSerie((String) valores
			//	.get(SOCIEDAD_SERIE));
		fileTransferOperaciones.setMarcaCompra((Integer) valores
				.get(MARCA_COMPRA));

		Serializable s = fileTransferOperacionesDao
				.save(fileTransferOperaciones);
		fileTransferOperacionesDao.flush();

		return s;
	}

	/**
	 * Genera el mapa de indices (true) o de nombres (false) correspondientes a
	 * traspasos de mercado dinero
	 * 
	 * @param obtieneIndices
	 * @return Map
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	private Map indicesNombresTraspasosMD(boolean obtieneIndices)
			throws BusinessException {

		log
				.trace("Entrando a FileTransferServiceImpl.indicesNombresTraspasosMD()");

		Map mapa = new TreeMap();
		if (obtieneIndices) {
			//se elimina este dato del file
			//mapa.put(FOLIO_DESC, new Integer(0));
			mapa.put(ID_INST_VEND, new Integer(0));
			mapa.put(FOLIO_INST_VEND, new Integer(1));
			mapa.put(CUENTA_VEND, new Integer(2));
			mapa.put(ID_RECEP, new Integer(3));
			mapa.put(FOLIO_RECEP, new Integer(4));
			mapa.put(CUENTA_RECEP, new Integer(5));
			mapa.put(TV, new Integer(6));
			mapa.put(EMISORA, new Integer(7));
			mapa.put(SERIE, new Integer(8));
			mapa.put(CUPON, new Integer(9));
			mapa.put(CANTIDAD, new Integer(10));
			mapa.put(TIPO_OPER, new Integer(11));
			mapa.put(DIAS_PLAZO, new Integer(12));
			mapa.put(FECHA, new Integer(13));
			//se elimina en el nuevo file
			//mapa.put(FECHA_REP, new Integer(15));
			mapa.put(PRECIO, new Integer(14));
			mapa.put(TASA, new Integer(15));
			mapa.put(PLAZO, new Integer(16));
			mapa.put(FECHA_HORA_CIERRE_OPER, new Integer(17));
			mapa.put(DIVISA, new Integer(18));
			mapa.put(BOVEDA_EFECTIVO, new Integer(19));			
			//se eliminan estos datos en el nuevoFile
			//mapa.put(ID_TASA_REF, new Integer(20));
			//mapa.put(BL, new Integer(21));
		} else {
			//se elimina este dato del file
			//mapa.put(new Integer(0), FOLIO_DESC);
			mapa.put(new Integer(0), ID_INST_VEND);
			mapa.put(new Integer(1), FOLIO_INST_VEND);
			mapa.put(new Integer(2), CUENTA_VEND);
			mapa.put(new Integer(3), ID_RECEP);
			mapa.put(new Integer(4), FOLIO_RECEP);
			mapa.put(new Integer(5), CUENTA_RECEP);
			mapa.put(new Integer(6), TV);
			mapa.put(new Integer(7), EMISORA);
			mapa.put(new Integer(8), SERIE);
			mapa.put(new Integer(9), CUPON);
			mapa.put(new Integer(10), CANTIDAD);
			mapa.put(new Integer(11), TIPO_OPER);
			mapa.put(new Integer(12), DIAS_PLAZO);
			mapa.put(new Integer(13), FECHA);
			//se elimina en el nuevo file
			//mapa.put(new Integer(15), FECHA_REP);
			mapa.put(new Integer(14), PRECIO);
			mapa.put(new Integer(15), TASA);
			mapa.put(new Integer(16), PLAZO);
			mapa.put(new Integer(17), FECHA_HORA_CIERRE_OPER);
			mapa.put(new Integer(18), DIVISA);
			mapa.put(new Integer(19), BOVEDA_EFECTIVO);
			//se eliminan estos datos en el nuevoFile
			//mapa.put(new Integer(20), ID_TASA_REF);
			//mapa.put(new Integer(21), BL);
		}
		return mapa;
	}
	
	/**
	 * Genera la lista de indices por tipo de dato correspondientes a traspasos
	 * de mercado dinero
	 * 
	 * @param nombreCampo
	 * @param tipoDatoCampos
	 * @return Map
	 */
	@SuppressWarnings("unchecked")
	private Map tiposDatosDepositos(Map nombreCampo, Map tipoDatoCampos) {

		log.trace("Entrando a FileTransferServiceImpl.tiposDatosTraspasoMD()");

		for (Iterator iter = nombreCampo.keySet().iterator(); iter.hasNext();) {
			Integer posicion = (Integer) iter.next();
			switch (posicion.intValue()) {			
			case 2:
				((List) tipoDatoCampos.get(TIPO_DATO_BIG_DECIMAL))
						.add(posicion);
				break;			
			default:
				((List) tipoDatoCampos.get(TIPO_DATO_STRING)).add(posicion);
				break;
			}
		}
		return tipoDatoCampos;
	}
	
	
	/**
     * Genera la lista de indices por tipo de dato correspondientes a traspasos
     * de SCO
     * 
     * @param nombreCampo
     * @param tipoDatoCampos
     * @return Map
     */
    @SuppressWarnings("unchecked")
	private Map tiposDatosTraspasoMC(Map nombreCampo, Map tipoDatoCampos) {
        for (Iterator iter = nombreCampo.keySet().iterator(); iter.hasNext();) {
            Integer posicion = (Integer) iter.next();
            switch (posicion.intValue()) {
                case 11:
                case 12:
                    ((List) tipoDatoCampos.get(TIPO_DATO_BIG_DECIMAL)).add(posicion);
                    break;
                case 10:
                    ((List) tipoDatoCampos.get(TIPO_DATO_INTEGER)).add(posicion);
                    break;
                case 0:
                    ((List) tipoDatoCampos.get(TIPO_DATO_DATE)).add(posicion);
                    break;
                case 13:
                    ((List) tipoDatoCampos.get(TIPO_DATO_DATE_TIME)).add(posicion);
                    break;
                default:
                    ((List) tipoDatoCampos.get(TIPO_DATO_STRING)).add(posicion);
                    break;
            }
        }
        return tipoDatoCampos;
    }

	/**
	 * Genera la lista de indices por tipo de dato correspondientes a traspasos
	 * de mercado dinero
	 * 
	 * @param nombreCampo
	 * @param tipoDatoCampos
	 * @return Map
	 */
	@SuppressWarnings("unchecked")
	private Map tiposDatosTraspasoMD(Map nombreCampo, Map tipoDatoCampos) {

		log.trace("Entrando a FileTransferServiceImpl.tiposDatosTraspasoMD()");

		for (Iterator iter = nombreCampo.keySet().iterator(); iter.hasNext();) {
			Integer posicion = (Integer) iter.next();
			switch (posicion.intValue()) {
			case 10: //cantidad
			case 14: //precio
			case 15: //tasa
			/*case 11: 
			case 16:
			case 17:*/
				((List) tipoDatoCampos.get(TIPO_DATO_BIG_DECIMAL))
						.add(posicion);
				break;
			
			case 12: //dias de plazo
			case 16: //plazo
			/*case 13:
			case 18:*/
				((List) tipoDatoCampos.get(TIPO_DATO_INTEGER)).add(posicion);
				break;
			case 13: //fecha
			/*case 14:
			case 15:*/
				((List) tipoDatoCampos.get(TIPO_DATO_DATE)).add(posicion);
				break;
			case 17: //fecha
				/*case 14:
				case 15:*/
					((List) tipoDatoCampos.get(TIPO_DATO_DATE_TIME)).add(posicion);
					break;
			default:
				((List) tipoDatoCampos.get(TIPO_DATO_STRING)).add(posicion);
				break;
			}
		}
		return tipoDatoCampos;
	}

	
	/**
     * Genera la lista de indices por tipo de dato correspondientes a cuentas de
     * Banca Comercial
     * @param nombreCampo
     * @param tipoDatoCampos
     * @return Map
     */
    @SuppressWarnings("unchecked")
	private Map tiposDatosCuentasBCOM(Map nombreCampo, Map tipoDatoCampos) {
        for (Iterator iter = nombreCampo.keySet().iterator(); iter.hasNext();) {
            Integer posicion = (Integer) iter.next();
            switch (posicion.intValue()) {
                case 4:
                case 5:
                case 6:
                    ((List) tipoDatoCampos.get(TIPO_DATO_BIG_DECIMAL)).add(posicion);
                    break;
                case 7:
                    ((List) tipoDatoCampos.get(TIPO_DATO_INTEGER)).add(posicion);
                    break;
                default:
                    ((List) tipoDatoCampos.get(TIPO_DATO_STRING)).add(posicion);
                    break;
            }
        }
        return tipoDatoCampos;
    }
    
	/**
     * Genera la lista de indices por tipo de dato correspondientes a cuentas de
     * Banca Comercial
     * @param nombreCampo
     * @param tipoDatoCampos
     * @return Map
     */
    @SuppressWarnings("unchecked")
	private Map tiposDatosRetirosBCOM(Map nombreCampo, Map tipoDatoCampos) { 
        for (Iterator iter = nombreCampo.keySet().iterator(); iter.hasNext();) {
            Integer posicion = (Integer) iter.next();
            switch (posicion.intValue()) {
                case 2:
                    ((List) tipoDatoCampos.get(TIPO_DATO_BIG_DECIMAL)).add(posicion);
                    break;
                default:
                    ((List) tipoDatoCampos.get(TIPO_DATO_STRING)).add(posicion);
                    break;
            }
        }
        return tipoDatoCampos;
    }
    
    /**
     * Genera la lista de indices por tipo de dato correspondientes a traspasos de efectivo
     * @param nombreCampo
     * @param tipoDatoCampos
     * @return Map
     */
    @SuppressWarnings("unchecked")
	private Map tiposDatosRetirosEfectivo(Map nombreCampo, Map tipoDatoCampos) { 
        for (Iterator iter = nombreCampo.keySet().iterator(); iter.hasNext();) {
            Integer posicion = (Integer) iter.next();
            switch (posicion.intValue()) {
                case 2:
                    ((List) tipoDatoCampos.get(TIPO_DATO_BIG_DECIMAL)).add(posicion);
                    break;
                default:
                    ((List) tipoDatoCampos.get(TIPO_DATO_STRING)).add(posicion);
                    break;
            }
        }
        return tipoDatoCampos;
    }
	/**
	 * Valida la informacion del archivo plano que corresponde a los traspasos
	 * de mercado capitales
	 * 
	 * @param agenteFirmado
	 * @param listaFileTransfer
	 * @param offset
	 * @return Numero de registros validados
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	private int validaInformacionTraspasosMC(AgenteVO agenteFirmado,
			BigInteger idBoveda, List listaFileTransfer, int offset)
			throws BusinessException {

		log
				.trace("Entrando a FileTransferServiceImpl.validaInformacionTraspasosMC()");

		if (listaFileTransfer == null || listaFileTransfer.isEmpty()) {
			throw new BusinessDataException(errorResolver.getMessage("J0046",
					"listaFileTransfer"), "J0046");
		}

		int ultimoRegistro = 0;

		long inicioProceso = Calendar.getInstance().getTimeInMillis();
		for (int i = offset; i < listaFileTransfer.size(); i++) {
			FileTransfer fileTransfer = (FileTransfer) listaFileTransfer.get(i);

			boolean error = false;
			StringBuffer msgError = new StringBuffer();
			StringBuffer numCampoError = new StringBuffer();
			String cadenaErrorFormato = errorResolver.getMessage("J0051");
			int marcaCompra = 0;

			/* Obtiene los campos correspondientes al layout */
			String[] campos = camposTraspasosMC(fileTransfer.getCadena());

			if (campos == null) {
				msgError.append(cadenaErrorFormato);
				msgError.append("en la estructura del registro");
				msgError.append(GUION);
				error = true;
			} else {

				/* Obtiene el mapa de indices (true) correspondiente al layout */
				Map indice = indicesNombresTraspasosMC(true);

				/* VALIDACIONES DE FORMATO */
				
				/**variable para determinar si el archivo contiene Traspasos y Recepciones */
				boolean archivoConTraspasosRecepciones = false;
				/** FEHCA LIQUIDACION */
				if (StringUtils
						.isBlank(campos[((Integer) indice.get(FECHA_LIQ))
								.intValue()])
						|| campos[((Integer) indice.get(FECHA_LIQ)).intValue()]
								.trim().length() != 11) {
					msgError.append("de la fecha");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(FECHA_LIQ))
							.toString());
					numCampoError.append(GUION);
					error = true;
				} else {
					String[] fechaSeparada = campos[((Integer) indice
							.get(FECHA_LIQ)).intValue()].split("-");
					if (fechaSeparada.length != 3
							|| fechaSeparada[0].length() != 2
							|| !fechaSeparada[0].matches(PATRON_NUMERICO)
							|| fechaSeparada[1].length() != 3
							|| !fechaSeparada[1].matches(PATRON_ALFABETICO)
							|| fechaSeparada[2].length() != 4
							|| !fechaSeparada[2].matches(PATRON_NUMERICO)) {
						msgError.append(cadenaErrorFormato);
						msgError.append("de la fecha");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice.get(FECHA_LIQ))
								.toString());
						numCampoError.append(GUION);
						error = true;
					}
				}

				/** CLAVE TRASPASANTE */
				if (StringUtils.isBlank(campos[((Integer) indice
						.get(CLAVE_TRASP)).intValue()])
						|| campos[((Integer) indice.get(CLAVE_TRASP))
								.intValue()].trim().length() != 5
						|| !campos[((Integer) indice.get(CLAVE_TRASP))
								.intValue()].matches(PATRON_NUMERICO)) {
					msgError.append(cadenaErrorFormato);
					msgError.append("de la clave del traspasante");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(CLAVE_TRASP))
							.toString());
					numCampoError.append(GUION);
					error = true;
				}
				/** CUENTA TRASPASANTE */
				if (StringUtils.isBlank(campos[((Integer) indice
						.get(CUENTA_VEND)).intValue()])
						|| campos[((Integer) indice.get(CUENTA_VEND))
								.intValue()].trim().length() > 4
						|| !campos[((Integer) indice.get(CUENTA_VEND))
								.intValue()].matches(PATRON_NUMERICO)) {
					msgError.append(cadenaErrorFormato);
					msgError.append("de la cuenta del traspasante");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(CUENTA_VEND))
							.toString());
					numCampoError.append(GUION);
					error = true;
				}
				/** CLAVE RECEPTOR */
				if (StringUtils.isBlank(campos[((Integer) indice
						.get(CLAVE_RECEP)).intValue()])
						|| campos[((Integer) indice.get(CLAVE_RECEP))
								.intValue()].trim().length() != 5
						|| !campos[((Integer) indice.get(CLAVE_RECEP))
								.intValue()].matches(PATRON_NUMERICO)) {
					msgError.append(cadenaErrorFormato);
					msgError.append("de la clave del receptor");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(CLAVE_RECEP))
							.toString());
					numCampoError.append(GUION);
					error = true;
				}
				/** CUENTA RECEPTOR */
				if (StringUtils.isBlank(campos[((Integer) indice
						.get(CUENTA_RECEP)).intValue()])
						|| campos[((Integer) indice.get(CUENTA_RECEP))
								.intValue()].trim().length() > 4
						|| !campos[((Integer) indice.get(CUENTA_RECEP))
								.intValue()].matches(PATRON_NUMERICO)) {
					msgError.append(cadenaErrorFormato);
					msgError.append("de la cuenta del receptor");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(CUENTA_RECEP))
							.toString());
					numCampoError.append(GUION);
					error = true;
				}
				//*************************************************************
				// se realizan validaciones para saber si se valida la boveda
				boolean isVenta = false;
				if(!error){
					if (agenteFirmado.getClave().equalsIgnoreCase(campos[((Integer) indice.get(CLAVE_TRASP)).intValue()]) && 
			        		!agenteFirmado.getClave().equalsIgnoreCase(campos[((Integer) indice.get(CLAVE_RECEP)).intValue()]) ){
							isVenta = true;
			        }else if(agenteFirmado.getClave().equalsIgnoreCase(campos[((Integer) indice.get(CLAVE_TRASP)).intValue()]) && 
			        		agenteFirmado.getClave().equalsIgnoreCase(campos[((Integer) indice.get(CLAVE_RECEP)).intValue()])){
			        	
			        	archivoConTraspasosRecepciones = true;
			        }
				}
				//////////////////////////////////////////////////////////////////////
				/** TV */
				if (StringUtils.isBlank(campos[((Integer) indice.get(TV))
						.intValue()])
						|| campos[((Integer) indice.get(TV)).intValue()].trim()
								.length() > 4
						|| !campos[((Integer) indice.get(TV)).intValue()]
								.matches(PATRON_ALFANUMERICO)) {
					msgError.append(cadenaErrorFormato);
					msgError.append("del tv");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(TV)).toString());
					numCampoError.append(GUION);
					error = true;
				}
				/** EMISORA */
				if (StringUtils.isBlank(campos[((Integer) indice.get(EMISORA))
						.intValue()])
						|| campos[((Integer) indice.get(EMISORA)).intValue()]
								.trim().length() > 7) {
					msgError.append(cadenaErrorFormato);
					msgError.append("de la emisora");
					log.debug("ERROR: " + msgError.toString());
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(EMISORA))
							.toString());
					numCampoError.append(GUION);
					error = true;
				}
				/** SERIE */
				if (StringUtils.isBlank(campos[((Integer) indice.get(SERIE))
						.intValue()])
						|| campos[((Integer) indice.get(SERIE)).intValue()]
								.trim().length() > 6) {
					msgError.append(cadenaErrorFormato);
					msgError.append("de la serie");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(SERIE))
							.toString());
					numCampoError.append(GUION);
					error = true;
				}

				/** CUPON */
				if (StringUtils.isBlank(campos[((Integer) indice.get(CUPON))
						.intValue()])
						|| campos[((Integer) indice.get(CUPON)).intValue()]
								.trim().length() > 4
						|| !campos[((Integer) indice.get(CUPON)).intValue()]
								.matches(PATRON_ALFANUMERICO)) {
					msgError.append(cadenaErrorFormato);
					msgError.append("del cupon");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(CUPON))
							.toString());
					numCampoError.append(GUION);
					error = true;
				}
				/** TIPO OPERACION */
				if (StringUtils
						.isBlank(campos[((Integer) indice.get(TIPO_OPER))
								.intValue()])
						|| campos[((Integer) indice.get(TIPO_OPER)).intValue()]
								.trim().length() > 2) {
					msgError.append(cadenaErrorFormato);
					msgError.append("del tipo de operacion");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(TIPO_OPER))
							.toString());
					numCampoError.append(GUION);
					error = true;
				}
				/** PLAZO */
				if (StringUtils.isBlank(campos[((Integer) indice.get(PLAZO))
						.intValue()])
						|| campos[((Integer) indice.get(PLAZO)).intValue()]
								.trim().length() > 3
						|| !campos[((Integer) indice.get(PLAZO)).intValue()]
								.matches(PATRON_NUMERICO)
						|| !validaMultiplos(
								campos[((Integer) indice.get(PLAZO)).intValue()],
								24, 8)) {
					msgError.append(cadenaErrorFormato);
					msgError.append("del plazo");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(PLAZO))
							.toString());
					numCampoError.append(GUION);
					error = true;
				}
				/** CANTIDAD */
				if (StringUtils.isBlank(campos[((Integer) indice.get(CANTIDAD))
						.intValue()])
						|| campos[((Integer) indice.get(CANTIDAD)).intValue()]
								.trim().length() > 20
						|| !campos[((Integer) indice.get(CANTIDAD)).intValue()]
								.trim().matches(PATRON_CANTIDADES)
						|| (campos[((Integer) indice.get(CANTIDAD)).intValue()]
								.indexOf(".") >= 0 && campos[((Integer) indice
								.get(CANTIDAD)).intValue()].indexOf(".") < 10)
						|| !NumberUtils.isNumber(campos[((Integer) indice.get(CANTIDAD)).intValue()].trim())) {
					msgError.append(cadenaErrorFormato);
					msgError.append("de la cantidad");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(CANTIDAD))
							.toString());
					numCampoError.append(GUION);
					error = true;
				}
				/** IMPORTE */
				if (StringUtils.isBlank(campos[((Integer) indice.get(IMPORTE))
						.intValue()])
						|| campos[((Integer) indice.get(IMPORTE)).intValue()]
								.trim().length() > 20
						|| !campos[((Integer) indice.get(IMPORTE)).intValue()]
								.trim().matches(PATRON_CANTIDADES)
						|| (campos[((Integer) indice.get(IMPORTE)).intValue()]
								.trim().indexOf(".") >= 0 && campos[((Integer) indice
								.get(IMPORTE)).intValue()].trim().indexOf(".") < 10)
						|| !NumberUtils.isNumber(campos[((Integer) indice.get(IMPORTE)).intValue()].trim())) {
					msgError.append(cadenaErrorFormato);
					msgError.append("del importe");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(IMPORTE))
							.toString());
					numCampoError.append(GUION);
					error = true;
				}
				
				// FECHA CONCERTACION
				// FECHA CONCERTACION
				if (StringUtils.isNotBlank(campos[((Integer) indice.get(FECHA_HORA_CIERRE_OPER))
						.intValue()]) && campos[((Integer) indice.get(FECHA_HORA_CIERRE_OPER)).intValue()]
								.trim().length() > 0) {
					String[] fechaHora = campos[((Integer) indice
							.get(FECHA_HORA_CIERRE_OPER)).intValue()].split(" ");
					
					if(fechaHora.length != 2 
							|| !validacionService.validarFechaValida(fechaArchivo2fechaFileTransfer(fechaHora[0]) + " " + fechaHora[1], 
									FORMATO_FECHA_HORA_FILE_TRANSFER_TRAS)) {
						msgError.append("El campo Fecha y Hora de Concertaci\u00f3n no es v\u00e1lido o no es un d\u00eda h\u00e1bil");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice.get(FECHA_HORA_CIERRE_OPER))
								.toString());
						numCampoError.append(GUION);
						error = true;
					} 
				}
				
				/** valida que venga la divisa para los tipos de operacion V y TV */
				if( campos[((Integer) indice.get(TIPO_OPER)).intValue()].toString().trim().equalsIgnoreCase(TIPO_OPER_V) || 
						campos[((Integer) indice.get(TIPO_OPER)).intValue()].toString().trim().equalsIgnoreCase(TIPO_OPER_TV)){
					if (fileTransfer.getCadena().length() == LONGITUD_MINIMA_TRASP_MC){
						msgError.append("La divisa es requerida para el tipo de operaci\u00f3n ingresada");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice.get(TIPO_OPER)).toString());	
						numCampoError.append(GUION);
						error = true;
					}
					else if(StringUtils.isBlank(campos[((Integer) indice.get(DIVISA)).intValue()]) || 
							campos[((Integer) indice.get(DIVISA)).intValue()].trim().length() != 3 || 
							!campos[((Integer) indice.get(DIVISA)).intValue()].matches(PATRON_ALFABETICO)){
						msgError.append(cadenaErrorFormato);
						msgError.append("de la divisa");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice.get(DIVISA)).toString());	
						numCampoError.append(GUION);
						error = true;
					}
				}	
				/*Modificación  2012/11/06
				  * se comenta esta condicion para que pase el campo de divisa aunque no tenga informacion*/				
				else if(StringUtils.trim(fileTransfer.getCadena()).length() > LONGITUD_MINIMA_TRASP_MC &&
						(campos[((Integer) indice.get(TIPO_OPER)).intValue()].toString().equalsIgnoreCase(TIPO_OPER_TL) ||
						  campos[((Integer) indice.get(TIPO_OPER)).intValue()].toString().trim().equalsIgnoreCase(TIPO_OPER_T))){										
					msgError.append(" La Divisa y Boveda de Efectivo no son requeridos ");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(DIVISA)).toString());	
					numCampoError.append(GUION);
					numCampoError.append(((Integer) indice.get(BOVEDA_EFECTIVO)).toString());
					numCampoError.append(GUION);
					error = true;
					
				}				
				// Boveda de Efectivo
				// valida que venga la boveda para los tipos de operacion V,R, y J (COMPRA)
				if(!isVenta && !archivoConTraspasosRecepciones &&(campos[((Integer) indice.get(TIPO_OPER)).intValue()].toString().trim().equalsIgnoreCase(TIPO_OPER_V) || 
						campos[((Integer) indice.get(TIPO_OPER)).intValue()].toString().trim().equalsIgnoreCase(TIPO_OPER_TV))){ 
						//se valida que sea del tamaño especificado  
						if(fileTransfer.getCadena().length() != LONGITUD_MAXIMA_TRASP_MC){
							msgError.append("La boveda de efectivo es requerida para el tipo de operaci\u00f3n ingresada");
							msgError.append(GUION);
							numCampoError.append(((Integer) indice.get(TIPO_OPER)).toString());	
							numCampoError.append(GUION);
							error = true;
						}
						else if(StringUtils.isBlank(campos[((Integer) indice.get(BOVEDA_EFECTIVO)).intValue()])||
								campos[((Integer) indice.get(BOVEDA_EFECTIVO)).intValue()].length() != 18 ||
								!campos[((Integer) indice.get(BOVEDA_EFECTIVO)).intValue()].trim().matches(PATRON_NOMBRE_CORTO_BOVEDA)){	
							msgError.append(cadenaErrorFormato);
							msgError.append("de la boveda de efectivo");
							msgError.append(GUION);
							numCampoError.append(((Integer) indice.get(BOVEDA_EFECTIVO)).toString());
							numCampoError.append(GUION);
							error = true;
						}
				}else if(archivoConTraspasosRecepciones && fileTransfer.getCadena().length() == LONGITUD_MAXIMA_TRASP_MC &&
						(campos[((Integer) indice.get(TIPO_OPER)).intValue()].toString().trim().equalsIgnoreCase(TIPO_OPER_V) || 
								campos[((Integer) indice.get(TIPO_OPER)).intValue()].toString().equalsIgnoreCase(TIPO_OPER_TV))){
								if( !StringUtils.isBlank(campos[((Integer) indice.get(BOVEDA_EFECTIVO)).intValue()]) &&
								    (campos[((Integer) indice.get(BOVEDA_EFECTIVO)).intValue()].length() != 18 ||
								    !campos[((Integer) indice.get(BOVEDA_EFECTIVO)).intValue()].trim().matches(PATRON_NOMBRE_CORTO_BOVEDA))){
									
									msgError.append(cadenaErrorFormato);
									msgError.append("de la boveda de efectivo");
									msgError.append(GUION);
									numCampoError.append(((Integer) indice.get(BOVEDA_EFECTIVO)).toString());
									numCampoError.append(GUION);
									error = true;
								}							
						}				
				if ((campos[((Integer) indice.get(TIPO_OPER)).intValue()].toString().trim().equalsIgnoreCase(TIPO_OPER_V) ||
						campos[((Integer) indice.get(TIPO_OPER)).intValue()].toString().equalsIgnoreCase(TIPO_OPER_TV)) && isVenta){
					
					if(StringUtils.trim(fileTransfer.getCadena()).length() == LONGITUD_MAXIMA_TRASP_MC || 
							StringUtils.trim(fileTransfer.getCadena()).length() > LONGITUD_MEDIA_TRASP_MC){					
						msgError.append("La boveda de efectivo no es requerida para el tipo de operaci\u00f3n ingresada");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice.get(TIPO_OPER)).toString());	
						numCampoError.append(GUION);
						error = true;					
					
					}
				}
				
				if (!error) {

					/* REGLAS DE NEGOCIO */

					/*
					 * Valida que el traspasante o receptor sea el agente
					 * firmado y que exista
					 */
					if (!campos[((Integer) indice.get(CLAVE_TRASP)).intValue()]
							.equals(agenteFirmado.getClave())) {
						if ((campos[((Integer) indice.get(TIPO_OPER))
								.intValue()].trim()
								.equalsIgnoreCase(TIPO_OPER_TV)) 
							|| (campos[((Integer) indice
								.get(TIPO_OPER)).intValue()].trim()
								.equalsIgnoreCase(TIPO_OPER_V))
							|| (campos[((Integer) indice
								.get(TIPO_OPER)).intValue()].trim()
								.equalsIgnoreCase(TIPO_OPER_T))
							|| (campos[((Integer) indice
								.get(TIPO_OPER)).intValue()].trim()
								.equalsIgnoreCase(TIPO_OPER_TL))) {

							if (!campos[((Integer) indice.get(CLAVE_RECEP))
									.intValue()].equals(agenteFirmado
									.getClave())) {
								msgError
										.append("La institucion firmada no existe en el archivo");
								msgError.append(GUION);
								numCampoError.append(((Integer) indice
										.get(CLAVE_RECEP)).toString());
								numCampoError.append(GUION);
								error = true;
							} else {
								marcaCompra = 1;
							}
						} else {
							msgError
									.append("No se pueden traspasar titulos de otra institucion");
							msgError.append(GUION);
							numCampoError.append(((Integer) indice
									.get(CLAVE_TRASP)).toString());
							numCampoError.append(GUION);
							error = true;
						}
					}

					/* Valida que el traspasante exista */
					String cvTraspasante = campos[((Integer) indice
							.get(CLAVE_TRASP))];
					AgenteVO traspasante = new AgenteVO(cvTraspasante
							.substring(0, 2), cvTraspasante.substring(2),
							campos[((Integer) indice.get(CUENTA_VEND))]);
					try {
						utilService.validaAgente(traspasante, " traspasante ",
								true);
					} catch (BusinessException e) {

						log.debug("WARN : [" + e.getMessage() + "]");

						msgError.append("El traspasante no esta registrado");
						msgError.append(GUION);
						numCampoError
								.append(((Integer) indice.get(CLAVE_TRASP))
										.toString());
						numCampoError.append(GUION);
						numCampoError
								.append(((Integer) indice.get(CUENTA_VEND))
										.toString());
						numCampoError.append(GUION);
						error = true;
					}

					/* Valida que exista el receptor */
					String cvReceptor = campos[((Integer) indice
							.get(CLAVE_RECEP))];
					AgenteVO receptor = new AgenteVO(
							cvReceptor.substring(0, 2),
							cvReceptor.substring(2), campos[((Integer) indice
									.get(CUENTA_RECEP))]);
					try {
						utilService.validaAgente(receptor, " receptor ", true);
					} catch (BusinessException e) {

						log.debug("WARN : [" + e.getMessage() + "]");

						msgError.append("El receptor no esta registrado");
						msgError.append(GUION);
						numCampoError
								.append(((Integer) indice.get(CLAVE_RECEP))
										.toString());
						numCampoError.append(GUION);
						numCampoError.append(((Integer) indice
								.get(CUENTA_RECEP)).toString());
						numCampoError.append(GUION);
						error = true;
					}

					/*
					 * Valida que la cuenta traspasante no este en la lista
					 * de cuentas invalidas */
					if (!error) {
						String cuentaTraspasante = campos[((Integer) indice.get(CUENTA_VEND))];
						String mensaje = validaCuenta(cuentaTraspasante, " traspasante ", cuentasInvalidas);
						if (StringUtils.isNotBlank(mensaje)) {
							msgError.append("Cuenta invalida para operar en este modulo");
							msgError.append(GUION);
							numCampoError.append(((Integer) indice.get(CUENTA_VEND)).toString());
							numCampoError.append(GUION);
							error = true;
						}
					}
					/*
					 * Valida que la cuenta receptora no este en la lista
					 * de cuentas invalidas */
					if (!error) {
						String cuentaReceptora = campos[((Integer) indice.get(CUENTA_RECEP))];
						String mensaje = validaCuenta(cuentaReceptora, " receptora ", cuentasInvalidas);
						if (StringUtils.isNotBlank(mensaje)) {
							msgError.append("Cuenta invalida para operar en este modulo");
							msgError.append(GUION);
							numCampoError.append(((Integer) indice.get(CUENTA_RECEP)).toString());
							numCampoError.append(GUION);
							error = true;
						}
					}

					/*
					 * Valida que la emision exista en el catalogo de emisiones
					 * y que la emision este en firme
					 */

					EmisionVO emisionVO = new EmisionVO();
					emisionVO.setIdTipoValor(campos[((Integer) indice.get(TV))
							.intValue()].trim());
					emisionVO.setEmisora(campos[((Integer) indice.get(EMISORA))
							.intValue()].trim());
					emisionVO.setSerie(campos[((Integer) indice.get(SERIE))
							.intValue()].trim());
					emisionVO.setCupon(campos[((Integer) indice.get(CUPON))
							.intValue()].trim());
					EmisionDTO emision = null;
					try {
						emision = emisionDao
								.consultarEmisionPorCupon(emisionVO);
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (emision == null) {
						msgError.append("Emision no registrada");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice.get(TV))
								.toString());
						numCampoError.append(GUION);
						numCampoError.append(((Integer) indice.get(EMISORA))
								.toString());
						numCampoError.append(GUION);
						numCampoError.append(((Integer) indice.get(SERIE))
								.toString());
						numCampoError.append(GUION);
						numCampoError.append(((Integer) indice.get(CUPON))
								.toString());
						numCampoError.append(GUION);
						error = true;
					}

					/*
					 * Valida el tipo de operacion y realiza la conversion si es
					 * necesario
					 */
					String tipoOperacion = campos[((Integer) indice
							.get(TIPO_OPER)).intValue()].trim();
					if (!campos[((Integer) indice.get(TIPO_OPER)).intValue()]
							.trim().equalsIgnoreCase(TIPO_OPER_V)
							&& !campos[((Integer) indice.get(TIPO_OPER))
									.intValue()].trim().equalsIgnoreCase(
									TIPO_OPER_T)
							&& !campos[((Integer) indice.get(TIPO_OPER))
									.intValue()].trim().equalsIgnoreCase(
									TIPO_OPER_TL)
							&& !campos[((Integer) indice.get(TIPO_OPER))
									.intValue()].trim().equalsIgnoreCase(
									TIPO_OPER_TV)) {
						msgError.append("Tipo de operacion indefinido");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice.get(TIPO_OPER))
								.toString());
						numCampoError.append(GUION);
						error = true;
					} else if (campos[((Integer) indice.get(TIPO_OPER))
							.intValue()].equalsIgnoreCase(TIPO_OPER_TL)) {
						tipoOperacion = TIPO_OPER_T;
					} else if (campos[((Integer) indice.get(TIPO_OPER))
							.intValue()].equalsIgnoreCase(TIPO_OPER_TV)) {
						tipoOperacion = TIPO_OPER_V;
					}

					/* Valida el importe de acuerdo al tipo de operacion */
					if (tipoOperacion.trim().equalsIgnoreCase(TIPO_OPER_T)
							&& (new BigDecimal(campos[((Integer) indice
									.get(IMPORTE)).intValue()].trim()))
									.compareTo(UtilsDaliVO.BIG_DECIMAL_ZERO) != 0) {
						msgError.append("Para TLP's el importe debe ser cero");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice.get(IMPORTE))
								.toString());
						numCampoError.append(GUION);
						error = true;
					} else if (tipoOperacion.trim().equalsIgnoreCase(
							TIPO_OPER_V)
							&& (new BigDecimal(campos[((Integer) indice
									.get(IMPORTE)).intValue()].trim()))
									.compareTo(UtilsDaliVO.BIG_DECIMAL_ZERO) <= 0) {
						msgError
								.append("Para TLVE's el importe debe ser mayor a cero");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice.get(IMPORTE))
								.toString());
						numCampoError.append(GUION);
						error = true;
					}
					/*
					 * se valida para operaciones V y TV, que la divisa y la Bobeda de efectivo sea la que les corresponde
					 * 
					 */
					String nombreCortoInstruccion = null;
					String bovedaEfectivo = null;
					String claveDivisa = null;
					//se valida la divisa
					if(TIPO_OPER_V.equalsIgnoreCase(tipoOperacion) ){
						claveDivisa = campos[((Integer) indice.get(DIVISA)).intValue()].toString();
						nombreCortoInstruccion = regresaNombreCortoInstruccion(tipoOperacion,MERCADO_CAPITALES);
						if(!utilService.isTipoInstruccionDivisaValido(nombreCortoInstruccion,claveDivisa)){
							msgError.append("No es v\u00e1lida la combinaci\u00f3n operacion - divisa.");
							msgError.append(GUION);
							numCampoError.append(((Integer) indice.get(TIPO_OPER)).toString());
							numCampoError.append(GUION);
							numCampoError.append(((Integer) indice.get(DIVISA)).toString());
							numCampoError.append(GUION);
							error = true;
						}	
					}
					//se valida la boveda
					if(TIPO_OPER_V.equalsIgnoreCase(tipoOperacion) && !isVenta && !archivoConTraspasosRecepciones){
						bovedaEfectivo = campos[((Integer) indice.get(BOVEDA_EFECTIVO)).intValue()].toString().trim();
						claveDivisa = campos[((Integer) indice.get(DIVISA)).intValue()].toString();
						marcaCompra = 1;
						//se valida que Exista la Divisa para el tipo de operacion ingresada
						nombreCortoInstruccion = regresaNombreCortoInstruccion(tipoOperacion,MERCADO_CAPITALES);
							if(!utilService.isTipoInstruccionDivisaBovedaValido(nombreCortoInstruccion,claveDivisa,bovedaEfectivo)){
								msgError.append("No es v\u00e1lida la combinaci\u00f3n operaci\u00f3n, divisa y boveda ");
								msgError.append(GUION);
								numCampoError.append(((Integer) indice.get(TIPO_OPER)).toString());
								numCampoError.append(GUION);
								numCampoError.append(((Integer) indice.get(DIVISA)).toString());
								numCampoError.append(GUION);
								numCampoError.append(((Integer) indice.get(BOVEDA_EFECTIVO)).toString());
								numCampoError.append(GUION);
								error = true;
							}
					}else if( TIPO_OPER_V.equalsIgnoreCase(tipoOperacion) && fileTransfer.getCadena().length() == LONGITUD_MAXIMA_TRASP_MC && 
							!StringUtils.isBlank(campos[((Integer) indice.get(BOVEDA_EFECTIVO)).intValue()])
							&& archivoConTraspasosRecepciones){
						marcaCompra = 1;
						bovedaEfectivo = campos[((Integer) indice.get(BOVEDA_EFECTIVO)).intValue()].toString().trim();
						claveDivisa = campos[((Integer) indice.get(DIVISA)).intValue()].toString();	
						//se valida que Exista la Divisa para el tipo de operacion ingresada
						nombreCortoInstruccion = regresaNombreCortoInstruccion(tipoOperacion,MERCADO_CAPITALES);
							if(!utilService.isTipoInstruccionDivisaBovedaValido(nombreCortoInstruccion,claveDivisa,bovedaEfectivo)){
								msgError.append("No es valida la combinaci\u00f3n operaci\u00f3n, divisa y boveda ");
								msgError.append(GUION);
								numCampoError.append(((Integer) indice.get(TIPO_OPER)).toString());
								numCampoError.append(GUION);
								numCampoError.append(((Integer) indice.get(DIVISA)).toString());
								numCampoError.append(GUION);
								numCampoError.append(((Integer) indice.get(BOVEDA_EFECTIVO)).toString());
								numCampoError.append(GUION);
								error = true;
							}						
					}
					
					/* Valida que el plazo no sea cero */
					if (Integer.parseInt(campos[((Integer) indice.get(PLAZO))
							.intValue()].trim()) < 0
							|| Integer.parseInt(campos[((Integer) indice
									.get(PLAZO)).intValue()].trim()) > 192) {
						msgError.append("El plazo debe ser 0, 24, 48, 72, 96, 120, 144, 168, 192");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice.get(PLAZO))
								.toString());
						numCampoError.append(GUION);
						error = true;
					}
					
					// Se valida si hay fecha de concertacion para la operacion y se almacena en un objeto Date
					Date fechaHoraCierreOper = null;
					if (StringUtils.isNotBlank(campos[((Integer) indice.get(FECHA_HORA_CIERRE_OPER))
      						.intValue()]) && campos[((Integer) indice.get(FECHA_HORA_CIERRE_OPER)).intValue()]
      								.trim().length() == 17) {
						String[] fechaHora = campos[((Integer) indice.get(FECHA_HORA_CIERRE_OPER)).intValue()].split(" ");
						String fechaFormatoNum = fechaArchivo2fechaFileTransfer(fechaHora[0]);
						fechaHoraCierreOper = DateUtil.stringToDate(fechaFormatoNum + " " + fechaHora[1], FORMATO_FECHA_HORA_FILE_TRANSFER_TRAS);
					}

					/*
					 * Obtiene la fecha de liquidacion y valida contra el ultimo
					 * ciclo
					 */
					String nuevaFechaLiquidacion = fechaArchivo2fechaFileTransfer(campos[((Integer) indice
							.get(FECHA_LIQ)).intValue()]);
					Date fechaLiquidacion = null;
					try {
						fechaLiquidacion = DateUtil
								.convierteStringToDate2(nuevaFechaLiquidacion);
						if (DateUtil.comparaFechasDias(dateUtilsDao.getDateFechaDB(), fechaLiquidacion) != 0) {
							msgError
									.append("La fecha de liquidacion debe ser la fecha actual");
							msgError.append(GUION);
							numCampoError.append(((Integer) indice
									.get(FECHA_LIQ)).toString());
							numCampoError.append(GUION);
							error = true;
						} else {
							if(!error) {
								fechaLiquidacion = utilService.agregarDiasHabiles(
										fechaLiquidacion, Integer
												.parseInt(campos[((Integer) indice
														.get(PLAZO)).intValue()]
														.trim()) / 24);
							}
						}

					} catch (ParseException e) {
						msgError
								.append("Error en la conversion de la fecha de liquidacion");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice.get(FECHA_LIQ))
								.toString());
						numCampoError.append(GUION);
						error = true;
					}

					/* Valida el valor de la cantidad */
					if ((new BigDecimal(campos[((Integer) indice.get(CANTIDAD))
							.intValue()].trim()))
							.compareTo(UtilsDaliVO.BIG_DECIMAL_ZERO) <= 0) {
						msgError
								.append("La cantidad del traspaso debe ser mayor que cero");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice.get(CANTIDAD))
								.toString());
						numCampoError.append(GUION);
						error = true;
					} else if ((new BigDecimal(campos[((Integer) indice
							.get(CANTIDAD)).intValue()].trim()))
							.compareTo(new BigDecimal("99999999999")) > 0) {
						msgError
								.append("La cantidad del traspaso no debe ser mayor a 99999999999");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice.get(CANTIDAD))
								.toString());
						numCampoError.append(GUION);
						error = true;
					}

					if (!error) {
						FileTransferPK fileTransferPK = fileTransfer2FileTransferOperacionesPK(fileTransfer);
						Map valores = new HashMap();
						valores.put(DIVISA, claveDivisa);
						valores.put(FECHA_LIQ, fechaLiquidacion);
						valores.put(FECHA_CONCER,
								dateUtilsDao.getFechaHoraCero(dateUtilsDao
										.getDateFechaDB()));
						valores.put(FECHA_HORA_CIERRE_OPER_KEY, fechaHoraCierreOper);
						valores.put(IMPORTE, new BigDecimal(
								campos[((Integer) indice.get(IMPORTE))
										.intValue()].trim()).divide(
								new BigDecimal("100000000"), 2,
								BigDecimal.ROUND_HALF_UP));
						valores.put(MERCADO, MERCADO_MC);
						valores.put(MARCA_COMPRA, marcaCompra);
						System.out.println("Valores.." + valores);
						if (grabaFileTransferOperaciones(fileTransferPK,
								valores) == null) {
							msgError
									.append("No se puede almacenar la informacion en FileTransferOperaciones");
							msgError.append(GUION);
							error = true;
						}
					}

				}

			}

			if (error) {
				fileTransfer.setEstado(ESTADO_ERROR);
				if (numCampoError.length() > 0) {
					fileTransfer.setCamposError(numCampoError.toString()
							.substring(0, numCampoError.length() - 1));
				}
				fileTransfer.setError(msgError.toString().substring(0,
						msgError.length() - 1));
				fileTransferDao.update(fileTransfer);
			}

			ultimoRegistro = i;

			long finalProceso = Calendar.getInstance().getTimeInMillis();
			 if ((finalProceso - inicioProceso) > 10000) {
				break;
			}
		}

		if ((ultimoRegistro + 1) == listaFileTransfer.size()) {
			return -1;
		}

		return ultimoRegistro + 1;
	}

	private String validaCuenta(String cuenta, String tipoCuenta, List<String> listaCuentasInvalidas) {
		for( String cuentaInvalida : listaCuentasInvalidas ) {
			if(cuenta.matches(cuentaInvalida)) {
				return ("Cuenta " + tipoCuenta + " Invalida");
			}
		}
		return null;
	}

	/**
	 * Obtiene los campos correspondientes al layout de traspasos de mercado
	 * capitales
	 * 
	 * @param cadena
	 * @return Arreglo de String
	 * @throws BusinessException
	 */
	private String[] camposTraspasosMC(String cadena) throws BusinessException {

		log.trace("Entrando a FileTransferServiceImpl.camposTraspasosMC()");

		if (StringUtils.isBlank(cadena)) {
			throw new BusinessDataException(errorResolver.getMessage("J0026",
					new Object[] { " la cadena de informacion " }), "J0026");
		}
		if (cadena.length() == LONGITUD_MINIMA_TRASP_MC || cadena.length() == LONGITUD_MAXIMA_TRASP_MC
				|| cadena.length() == LONGITUD_MEDIA_TRASP_MC) {
			List<Integer> posiciones = new ArrayList<Integer>();
			posiciones.add(new Integer(11));
			posiciones.add(new Integer(5));
			posiciones.add(new Integer(4));
			posiciones.add(new Integer(5));
			posiciones.add(new Integer(4));
			posiciones.add(new Integer(4));
			posiciones.add(new Integer(7));
			posiciones.add(new Integer(6));
			posiciones.add(new Integer(4));
			posiciones.add(new Integer(2));
			////se modifico de 2 por 3 (plazo)
			posiciones.add(new Integer(3));
			posiciones.add(new Integer(20));
			posiciones.add(new Integer(20));
			posiciones.add(new Integer(17));
			// SE AGREGAN 2 CAMPOS 
			if(cadena.length() == LONGITUD_MEDIA_TRASP_MC){
				posiciones.add(new Integer(3));
			}
			else if(cadena.length() == LONGITUD_MAXIMA_TRASP_MC){
				posiciones.add(new Integer(3));
				posiciones.add(new Integer(18));
			}
			return obtieneCampos(cadena, posiciones);	
		}
		return null;
		
	}

	
	private Map indicesNombresDepositosEfectivo(boolean obtieneIndices){
		Map mapa = new TreeMap();
		if (obtieneIndices) {
			mapa.put(CASFIM, new Integer(0));
			mapa.put(IMPORTE, new Integer(1));
			//se agregaron 2 nuevas columnas para leer del archivo 
			mapa.put(DIVISA, new Integer(2));
			mapa.put(BOVEDA_EFECTIVO, new Integer(3));
		
		} else {
			mapa.put(new Integer(0), CASFIM);
			mapa.put(new Integer(1), IMPORTE);
			//se agregaron 2 nuevas columnas para leer del archivo 
			mapa.put(new Integer(2), DIVISA);
			mapa.put(new Integer(3),BOVEDA_EFECTIVO);
		}
		return mapa;
	}
	
	private Map<Object, Object> indicesNombresTraspasosEfectivo(boolean obtieneIndices){
		Map<Object, Object> mapa = new HashMap<>();
		if (obtieneIndices) {
			mapa.put(CLAVE_TRASP, Integer.valueOf(0));
			mapa.put(CLAVE_RECEP, Integer.valueOf(1));
			mapa.put(IMPORTE, Integer.valueOf(2)); 
			mapa.put(DIVISA, Integer.valueOf(3));
			mapa.put(BOVEDA_EFECTIVO, Integer.valueOf(4));
		
		} else {
			mapa.put(Integer.valueOf(0), CLAVE_TRASP);
			mapa.put(Integer.valueOf(1), CLAVE_RECEP);
			mapa.put(Integer.valueOf(2), IMPORTE); 
			mapa.put(Integer.valueOf(3), DIVISA);
			mapa.put(Integer.valueOf(4), BOVEDA_EFECTIVO);
		}
		return mapa;
	}
	
	/**
	 * Genera el mapa de indices (true) o de nombres (false) correspondientes a
	 * traspasos de mercado capitales
	 * 
	 * @param obtieneIndices
	 * @return Map
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	private Map indicesNombresTraspasosMC(boolean obtieneIndices)
			throws BusinessException {

		log
				.trace("Entrando a FileTransferServiceImpl.indicesNombresTraspasosMC()");

		Map mapa = new TreeMap();
		if (obtieneIndices) {
			mapa.put(FECHA_LIQ, new Integer(0));
			mapa.put(CLAVE_TRASP, new Integer(1));
			mapa.put(CUENTA_VEND, new Integer(2));
			mapa.put(CLAVE_RECEP, new Integer(3));
			mapa.put(CUENTA_RECEP, new Integer(4));
			mapa.put(TV, new Integer(5));
			mapa.put(EMISORA, new Integer(6));
			mapa.put(SERIE, new Integer(7));
			mapa.put(CUPON, new Integer(8));
			mapa.put(TIPO_OPER, new Integer(9));
			mapa.put(PLAZO, new Integer(10));
			mapa.put(CANTIDAD, new Integer(11));
			mapa.put(IMPORTE, new Integer(12));
			mapa.put(FECHA_HORA_CIERRE_OPER, new Integer(13));
			//SE AGREGAN DOS CAMPOS, DIVISA Y BOVEDA
			mapa.put(DIVISA, new Integer(14));
			mapa.put(BOVEDA_EFECTIVO, new Integer(15));
		} else {
			mapa.put(new Integer(0), FECHA_LIQ);
			mapa.put(new Integer(1), CLAVE_TRASP);
			mapa.put(new Integer(2), CUENTA_VEND);
			mapa.put(new Integer(3), CLAVE_RECEP);
			mapa.put(new Integer(4), CUENTA_RECEP);
			mapa.put(new Integer(5), TV);
			mapa.put(new Integer(6), EMISORA);
			mapa.put(new Integer(7), SERIE);
			mapa.put(new Integer(8), CUPON);
			mapa.put(new Integer(9), TIPO_OPER);
			mapa.put(new Integer(10), PLAZO);
			mapa.put(new Integer(11), CANTIDAD);
			mapa.put(new Integer(12), IMPORTE);
			mapa.put(new Integer(13), FECHA_HORA_CIERRE_OPER);
			//SE AGREGAN DOS CAMPOS, DIVISA Y BOVEDA
			mapa.put(new Integer(14), DIVISA);
			mapa.put(new Integer(15), BOVEDA_EFECTIVO);
		}
		return mapa;
	}
	
	/**
	 * Valida la informacion del filetrasnfer de cuentas de banca comercial
	 * 
	 * @param agenteFirmado
	 * @param idBoveda
	 * @param listaFileTransfer
	 * @param offset
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private int validaInformacionCuentasBCom(AgenteVO agenteFirmado,
			BigInteger idBoveda, List listaFileTransfer, int offset) {
		
		long inicioProceso = Calendar.getInstance().getTimeInMillis();
		int ultimoRegistro = 0;
		
		for (int i = offset; i < listaFileTransfer.size(); i++) {
			
			FileTransfer fileTransfer = (FileTransfer) listaFileTransfer.get(i);
			boolean error = false;
			StringBuffer msgError = new StringBuffer(UtilsDaliVO.BLANK);
			StringBuffer numCampoError = new StringBuffer(UtilsDaliVO.BLANK);
			String cadenaErrorFormato = errorResolver.getMessage("J0051");
		
			/* Obtiene los campos correspondientes al layout */
			String[] campos = camposCuentasBancaComercial(fileTransfer.getCadena());
			
			if (campos == null) {
				msgError.append(cadenaErrorFormato);
				msgError.append("en la estructura del registro el tama\u00f1o minimo es ["+LONGITUD_MIN_CUENTAS_BCOM+"]");
				msgError.append(GUION);
				error = true;
			} else {

				/* Obtiene el mapa de indices (true) correspondiente al layout */
				Map indice = indicesNombresCuentasBCom(true);

				/** ***** VALIDACIONES DE FORMATO ***** */
				
				//CLAVE TRASPASANTE
				if (StringUtils.isBlank(campos[((Integer) indice
						.get(CLAVE_TRASP)).intValue()])
						|| campos[((Integer) indice.get(CLAVE_TRASP))
								.intValue()].trim().length() != 5
						|| !campos[((Integer) indice.get(CLAVE_TRASP))
								.intValue()].matches(PATRON_NUMERICO)) {
					msgError.append(cadenaErrorFormato);
					msgError.append("de la clave del traspasante");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(CLAVE_TRASP))
							.toString());
					numCampoError.append(GUION);
					error = true;
				}
				//CLAVE BENEFICIARIO
				if (StringUtils.isBlank(campos[((Integer) indice
						.get(CLAVE_RECEP)).intValue()])
						|| campos[((Integer) indice.get(CLAVE_RECEP))
								.intValue()].trim().length() != 5
						|| !campos[((Integer) indice.get(CLAVE_RECEP))
								.intValue()].matches(PATRON_NUMERICO)) {
					msgError.append(cadenaErrorFormato);
					msgError.append("de la clave del beneficiario");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(CLAVE_RECEP))
							.toString());
					numCampoError.append(GUION);
					error = true;
				}
				//CUENTA BENEFICIARIO
				if (StringUtils.isBlank(campos[((Integer) indice
						.get(CUENTA_BENEF)).intValue()])
						|| campos[((Integer) indice.get(CUENTA_BENEF))
								.intValue()].trim().length() > 18
						|| !campos[((Integer) indice.get(CUENTA_BENEF))
								.intValue()].matches(PATRON_NUMERICO)) {
					msgError.append(cadenaErrorFormato);
					msgError.append("de la cuenta del beneficiario");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(CUENTA_BENEF))
							.toString());
					numCampoError.append(GUION);
					error = true;
				}
				//NOMBRE BENEFICIARIO
				String nombreBenef=campos[((Integer) indice.get(NOMBRE_BENEF)).intValue()];
				nombreBenef = nombreBenef.toUpperCase();
				String patronAlfaNumEspacio="[A-Z0-9\\s]+";
				if (StringUtils.isBlank(nombreBenef)
						|| nombreBenef.trim().length() > 30
						|| !nombreBenef.trim().matches(patronAlfaNumEspacio)) {
					msgError.append(cadenaErrorFormato);
					msgError.append("del nombre del beneficiario");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(NOMBRE_BENEF))
							.toString());
					numCampoError.append(GUION);
					error = true;
				}
				// MONTO MAX MENSUAL - si esta en blanco es correcto
				String limiteMensual=campos[((Integer) indice.get(MAX_MENSUAL)).intValue()];
				String patronNumerico="\\d+(.\\d{2})?";
				if (!StringUtils.isBlank(limiteMensual) && !limiteMensual.trim().matches(patronNumerico)){ //si lo capturaron y el formato esta mal
					msgError.append(cadenaErrorFormato);
					msgError.append(" el monto maximo mensual debe ser una cantidad ###.##");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(MAX_MENSUAL))
							.toString());
					numCampoError.append(GUION);
					error = true;
				}
				// MONTO MAX DIARIO - si esta en blanco es correcto
				String limiteDiario=campos[((Integer) indice.get(MAX_DIARIO)).intValue()];
				if (!StringUtils.isBlank(limiteDiario) && !limiteDiario.trim().matches(patronNumerico)){ //si lo capturaron y el formato esta mal
					msgError.append(cadenaErrorFormato);
					msgError.append(" el monto maximo diario debe ser una cantidad ###.##");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(MAX_DIARIO))
							.toString());
					numCampoError.append(GUION);
					error = true;
				}
				// MONTO MAX X TRANSACCION - si esta en blanco es correcto
				String limitexTran=campos[((Integer) indice.get(MAX_XTRANSC)).intValue()];
				if (!StringUtils.isBlank(limitexTran) && !limitexTran.trim().matches(patronNumerico)){ //si lo capturaron y el formato esta mal
					msgError.append(cadenaErrorFormato);
					msgError.append(" el monto maximo por transaccion debe ser una cantidad ###.##");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(MAX_XTRANSC))
							.toString());
					numCampoError.append(GUION);
					error = true;
				}
				// MOVS MAX X MES - si esta en blanco es correcto
				String limiteMovs=campos[((Integer) indice.get(MOVS_MENSUAL)).intValue()];
				if (!StringUtils.isBlank(limiteMovs) 
						&& (limiteMovs.trim().length() > 4
						|| !limiteMovs.matches(PATRON_NUMERICO))){ //si lo capturaron y el formato esta mal
					msgError.append(cadenaErrorFormato);
					msgError.append(" el maximo de movimientos debe ser un numero entero");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(MOVS_MENSUAL))
							.toString());
					numCampoError.append(GUION);
					error = true;
				}
				
				if(!error){ 
					/** ***** VALIDACIONES ENTRE REGISTRO ACTUAL CONTRA EL RESTO DE LOS REGISTROS A CARGAR SIN ACCESO A BD ***** */
					/* Valida que el registro no este duplicado*/
					int linea = esDuplicado(i, listaFileTransfer);
					if(linea != -1){
						msgError.append("El registro es duplicado de la linea ").append(linea).append(" del archivo");
						msgError.append(GUION);
						error = true;
					}

					/* Validacion nombreBeneficiario corresponda con la combinacion cuentaBeneficiario-bancoBeneficiario, depende del traspasante */
					String[] nombreLinea = nombreParaCuentaBancoBeneficiarioValido(i, listaFileTransfer); 
					if(nombreLinea != null){
						msgError.append(
							"La combinaci\u00f3n del beneficiario ( Banco_Cuenta ) ya existe para el nombre ").append(nombreLinea[0])
							.append(" en la linea ").append(nombreLinea[1]).append(" del archivo");
						msgError.append(GUION);						
						error = true;
					}
					
					/* Validacion combinacion nombreBeneficiario-cuentaBeneficiario-bancoBeneficiario no se repita para el traspasante */
					linea = existeBancoNombreCuentaBeneficiario(i, listaFileTransfer);
					if(linea != -1){
						msgError.append(
							"La combinaci\u00f3n del beneficiario ( Banco_Cuenta_Nombre ) ya existe para el traspasante ")
							.append(" en la linea ").append(linea).append(" del archivo");
						msgError.append(GUION);		
						error = true;
					}
					
					
					/** ***** VALIDACIONES DE NEGOCIO CON ACCESO A BD ***** */
					
					/* Valida que el traspasante sea el agente firmado */
					if (!campos[((Integer) indice.get(CLAVE_TRASP)).intValue()]
							.equals(agenteFirmado.getClave())) {
						msgError.append("No se pueden crear cuentas de otra institucion");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice
								.get(CLAVE_TRASP)).toString());
						numCampoError.append(GUION);
						error = true;
					}

					/* Valida que el traspasante exista */
					String cvTraspasante = campos[((Integer) indice.get(CLAVE_TRASP))];
					AgenteVO traspasante = new AgenteVO(cvTraspasante
							.substring(0, 2), cvTraspasante.substring(2));
					try {
						utilService.validaAgente(traspasante, " traspasante ",false);
					} catch (BusinessException e) {
						log.debug("WARN : [" + e.getMessage() + "]");
						msgError.append("El traspasante no esta registrado");
						msgError.append(GUION);
						numCampoError
								.append(((Integer) indice.get(CLAVE_TRASP))
										.toString());
						numCampoError.append(GUION);
						error = true;
					}
					
					//Validacion de traspasante existe en catalogo de instituciones
					InstitucionDTO instTrasp = institucionDaliDAO.buscarInstitucionPorClaveYFolio(
																campos[((Integer) indice.get(CLAVE_TRASP)).intValue()]);
					if (instTrasp == null) {
						msgError.append("La institucion traspasante no existe en el catalogo");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice.get(CLAVE_TRASP)).toString());
						numCampoError.append(GUION);
						error = true;
					}
					
					//Validacion de traspasante existente para Banxico.
//					if(instTrasp!=null && !depositanteValidoBanxicoDao.isDepositanteValidoBanxico(instTrasp.getId())) {
//						msgError.append("No es un depositante valido para Banxico");
//						msgError.append(GUION);
//						numCampoError.append(((Integer) indice
//								.get(CLAVE_TRASP)).toString());
//						numCampoError.append(GUION);
//						error = true;
//			        }
					
					//Validacion traspasante con cuenta clabe.
					if(instTrasp!=null && StringUtils.isEmpty(instTrasp.getCuentaClabe())) {
						msgError.append("La institucion traspasante debe tener una cuenta CLABE");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice
								.get(CLAVE_TRASP)).toString());
						numCampoError.append(GUION);
						error = true;
			        }
					
					//Validacion de receptor existe en catalogo de instituciones
					InstitucionDTO instRecep = 
						institucionDaliDAO.buscarInstitucionPorClaveYFolio(campos[((Integer) indice.get(CLAVE_RECEP)).intValue()]);
					if (instRecep == null) {
						msgError.append("La institucion receptora no existe en el catalogo");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice.get(CLAVE_RECEP)).toString());
						numCampoError.append(GUION);
						error = true;
					}
					
			        /*
			         * Se agregan validaciones para el requerimiento R-IND-2014-010
			         * 02/10/2014 Pablo Balderas.
			         */
					// 1) Valida que el banco beneficiario tenga clave SPEI
					if(StringUtils.isEmpty(instRecep.getClaveSpei())) {
						msgError.append(errorResolver.getMessage("JBR-012"));
						msgError.append(GUION);
						numCampoError.append(((Integer) indice.get(CLAVE_RECEP)).toString());
						numCampoError.append(GUION);
						error = true;
					}
					
					//Validacion receptor con cuenta clabe.
					if(instRecep!=null && StringUtils.isEmpty(instRecep.getCuentaClabe())) {
						msgError.append("La institucion receptora debe tener una cuenta CLABE");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice.get(CLAVE_RECEP)).toString());
						numCampoError.append(GUION);
						error = true;
			        }
					
					//Validacion receptor con cuenta clabe de 18 digitos exactos
					if(instRecep!=null && !StringUtils.isEmpty(instRecep.getCuentaClabe()) && !instRecep.getCuentaClabe().matches("([0-9]{18})*")){
						msgError.append("La cuenta CLABE de la institucion beneficiaria debe ser numerica y de longitud 18.");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice.get(CLAVE_RECEP)).toString());
						numCampoError.append(GUION);
						error = true;
					}
					
					//validacion nombreBeneficiario corresponda con la combinacion cuentaBeneficiario-bancoBeneficiario, depende del traspasante
					String[] nombreBeneficiarioEnBD = new String[1];
					nombreBeneficiarioEnBD[0] = "TBD";
					if(instRecep!=null 
							&& !admonCuentasService.nombreParaCuentaBancoBeneficiarioValido(
									instTrasp, instRecep, 
									campos[((Integer) indice.get(CUENTA_BENEF)).intValue()], 
									campos[((Integer) indice.get(NOMBRE_BENEF)).intValue()], 
									nombreBeneficiarioEnBD, null)){
						msgError.append("La combinaci\u00f3n del beneficiario ( Banco_Cuenta ) ya existe para el nombre ").append(nombreBeneficiarioEnBD[0]);
						msgError.append(GUION);
						numCampoError.append(((Integer) indice.get(CLAVE_RECEP)).toString());
						numCampoError.append(GUION);
						numCampoError.append(((Integer) indice.get(CUENTA_BENEF)).toString());
						numCampoError.append(GUION);
						numCampoError.append(((Integer) indice.get(NOMBRE_BENEF)).toString());
						numCampoError.append(GUION);
						error = true;
					}
					
					//validacion combinacion nombreBeneficiario-cuentaBeneficiario-bancoBeneficiario no se repita para el traspasante
					if(instTrasp!=null && instRecep!=null 
							&& admonCuentasService.existeBancoNombreCuentaBeneficiario(
									instTrasp, instRecep, 
									campos[((Integer) indice.get(CUENTA_BENEF)).intValue()], 
									campos[((Integer) indice.get(NOMBRE_BENEF)).intValue()], 
									null)){
						msgError.append("La combinaci\u00f3n del beneficiario ( Banco_Cuenta_Nombre ) ya existe para el traspasante ");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice.get(CLAVE_RECEP)).toString());
						numCampoError.append(GUION);
						numCampoError.append(((Integer) indice.get(CUENTA_BENEF)).toString());
						numCampoError.append(GUION);
						numCampoError.append(((Integer) indice.get(NOMBRE_BENEF)).toString());
						numCampoError.append(GUION);
						error = true;						
					}
					
					//validacion de monto mensual decimal y mayor a 0 (en caso de aplicar)
					if(!StringUtils.isBlank(limiteMensual)){
						if(Constantes.CERO_BIG_DECIMAL.compareTo((new BigDecimal(limiteMensual.trim()))) >= Constantes.CERO_INT){
							msgError.append("El monto maximo mensual debe ser mayor a cero");
							msgError.append(GUION);
							numCampoError.append(((Integer) indice.get(MAX_MENSUAL)).toString());
							numCampoError.append(GUION);
							error = true;
						}
					}
					
					//validacion de monto diario decimal y mayor a 0 (en caso de aplicar)
					if(!StringUtils.isBlank(limiteDiario) 
						&& (Constantes.CERO_BIG_DECIMAL.compareTo((new BigDecimal(limiteDiario.trim()))) >= Constantes.CERO_INT)){
						msgError.append("El monto maximo diario debe ser mayor a cero");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice.get(MAX_DIARIO)).toString());
						numCampoError.append(GUION);
						error = true;
					}

					//validacion de monto diario no mayor a monto mensual
					if(!StringUtils.isBlank(limiteMensual) && !StringUtils.isBlank(limiteDiario.trim()) 
						&& Double.valueOf(limiteDiario.trim()) > Double.valueOf(limiteMensual.trim())){
						msgError.append("El monto maximo diario no debe ser mayor al monto maximo mensual");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice.get(MAX_DIARIO)).toString());
						numCampoError.append(GUION);
						error = true;
					}
					
					//validacion de monto por transaccion decimal y mayor a 0 (en caso de aplicar)
					if(!StringUtils.isBlank(limitexTran)){
						if(Constantes.CERO_BIG_DECIMAL.compareTo((new BigDecimal(limitexTran.trim()))) >= Constantes.CERO_INT){
							msgError.append("El monto maximo por transaccion debe ser mayor a cero");
							msgError.append(GUION);
							numCampoError.append(((Integer) indice.get(MAX_XTRANSC)).toString());
							numCampoError.append(GUION);
							error = true;
						}
					}
					
					//validacion de monto x transaccion no mayor a monto mensual
					if(!StringUtils.isBlank(limiteMensual) && !StringUtils.isBlank(limitexTran) 
							&& Double.valueOf(limitexTran.trim()) > Double.valueOf(limiteMensual.trim())){
						msgError.append("El monto maximo por transaccion no debe ser mayor al monto maximo mensual");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice.get(MAX_XTRANSC)).toString());
						numCampoError.append(GUION);
						error = true;
					}

					//validacion de monto x transaccion no mayor a monto diario
					if(!StringUtils.isBlank(limiteDiario) && !StringUtils.isBlank(limitexTran) 
							&& Double.valueOf(limitexTran.trim()) > Double.valueOf(limiteDiario.trim())){
						msgError.append("El monto maximo por transaccion no debe ser mayor al monto maximo diario");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice.get(MAX_XTRANSC)).toString());
						numCampoError.append(GUION);
						error = true;
					}
					
					//validacion de numero de movimientos mensual entero y mayor a 0 (en caso de aplicar)
					if(!StringUtils.isBlank(limiteMovs)){
						if(Long.valueOf(limiteMovs.trim()) <= Constantes.CERO_INT){
							msgError.append("El numero de movimientos mensuales debe ser numero entero y mayor a cero");
							msgError.append(GUION);
							numCampoError.append(((Integer) indice.get(MOVS_MENSUAL)).toString());
							numCampoError.append(GUION);
							error = true;
						}
					}
				}
			}	
						
			if (error) {
				fileTransfer.setEstado(ESTADO_ERROR);
				if (numCampoError.length() > 0) {
					fileTransfer.setCamposError(numCampoError.toString()
							.substring(0, numCampoError.length() - 1));
				}
				fileTransfer.setError(msgError.toString().substring(0,
						msgError.length() - 1));
				fileTransferDao.update(fileTransfer);
			}

			ultimoRegistro = i;
			long finalProceso = Calendar.getInstance().getTimeInMillis();
			if ((finalProceso - inicioProceso) > 10000) {
				break;
			}		
		}
		
		if ((ultimoRegistro + 1) == listaFileTransfer.size()) {
			return -1;
		}

		return ultimoRegistro + 1;	
	}

	/**
	 * Definicion de layout del archivo de cuentas de banca comercial
	 * @param cadena linea en el archivo
	 * 
	 */
	private String[] camposCuentasBancaComercial(String cadena){
		log.trace("Entrando a FileTransferServiceImpl.camposCuentasBancaComercial()");

		if (StringUtils.isBlank(cadena)) {
			throw new BusinessDataException(errorResolver.getMessage("J0026",
					new Object[] { " la cadena de informacion " }), "J0026");
		}

		List<Integer> posiciones = new ArrayList<Integer>();
		posiciones.add(new Integer(5));  //id folio traspasante
		posiciones.add(new Integer(5));  //id folio beneficiario
		posiciones.add(new Integer(18)); //cuenta beneficiario
		posiciones.add(new Integer(30)); //nombre beneficiario
		posiciones.add(new Integer(16)); //monto max mensual
		posiciones.add(new Integer(16)); //monto max diario
		posiciones.add(new Integer(16)); //monto max x transaccion
		posiciones.add(new Integer(4));  //mov max mensual
		
		if(cadena==null || cadena.length() !=LONGITUD_MIN_CUENTAS_BCOM ){
			return null;
		}

		return obtieneCampos(cadena, posiciones);
	}
	
	/**
	 * Genera el mapa de indices (true) o de nombres (false) correspondientes a
	 * traspasos de mercado capitales
	 * 
	 * @param obtieneIndices
	 * @return Map
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	private Map indicesNombresCuentasBCom(boolean obtieneIndices){
		Map mapa = new TreeMap();
		if (obtieneIndices) {
			mapa.put(CLAVE_TRASP,  new Integer(0));
			mapa.put(CLAVE_RECEP,  new Integer(1));
			mapa.put(CUENTA_BENEF, new Integer(2));
			mapa.put(NOMBRE_BENEF, new Integer(3));
			mapa.put(MAX_MENSUAL,  new Integer(4));
			mapa.put(MAX_DIARIO,   new Integer(5));
			mapa.put(MAX_XTRANSC,  new Integer(6));
			mapa.put(MOVS_MENSUAL, new Integer(7));
		} else {
			mapa.put(new Integer(0), CLAVE_TRASP);
			mapa.put(new Integer(1), CLAVE_RECEP);
			mapa.put(new Integer(2), CUENTA_BENEF);
			mapa.put(new Integer(3), NOMBRE_BENEF);
			mapa.put(new Integer(4), MAX_MENSUAL);
			mapa.put(new Integer(5), MAX_DIARIO);
			mapa.put(new Integer(6), MAX_XTRANSC);
			mapa.put(new Integer(7), MOVS_MENSUAL);
		}
		return mapa;
	}
	
	/**
	 * Revisa si la linea actual que se esta revisando es o no duplicado de alguno 
	 * de los registros del archivo a cargar
	 * @param lineaActual numero de linea actual
	 * @param listaFileTransfer lineas del archivo
	 * @return numero de linea que se encontro duplicado 
	 */
	@SuppressWarnings("unchecked")
	private int esDuplicado(int lineaActual, List listaFileTransfer){
		String strLineaActual = ((FileTransfer) listaFileTransfer.get(lineaActual)).getCadena();
		//revisa solo las lineas anteriores
		for (int i = 0; i < lineaActual; i++) {
			FileTransfer lineaArchivo = (FileTransfer) listaFileTransfer.get(i);
			if(strLineaActual.equals(lineaArchivo.getCadena())){
				return i+1; //retorna la linea que se encontro duplicada
			}
		}
		return -1;//no hay duplicados
	}

	/**
	 * Valida que el beneficiario corresponda con la combinacion cuenta - banco de la linea actual
	 * contra el resto de los registros del archivo a cargar
	 * @param lineaActual numero de linea actual
	 * @param listaFileTransfer lineas del archivo
	 * @return String[] [0]=nombre encontrado, [1]=linea localizada
	 */
	@SuppressWarnings("unchecked")
	private String[] nombreParaCuentaBancoBeneficiarioValido(int lineaActual, List listaFileTransfer){
		String strLineaActual = ((FileTransfer) listaFileTransfer.get(lineaActual)).getCadena();
		String[] camposLineaActual = camposCuentasBancaComercial(strLineaActual);
		Map indice = indicesNombresCuentasBCom(true);
		String[] nombreLinea = null;
		//revisa solo las lineas anteriores
		for (int i = 0; i < lineaActual; i++) {
			FileTransfer lineaArchivo = (FileTransfer) listaFileTransfer.get(i);
			String[] camposLineaArchivo = camposCuentasBancaComercial(lineaArchivo.getCadena());
			if(camposLineaArchivo==null)return null;

			log.debug("!#$%&/ camposLineaArchivo-CLAVE_TRASP"+camposLineaArchivo[((Integer) indice.get(CLAVE_TRASP)).intValue()]);
			log.debug("!#$%&/ camposLineaActual-CLAVE_TRASP"+camposLineaActual[((Integer) indice.get(CLAVE_TRASP)).intValue()]);
			log.debug("!#$%&/ camposLineaArchivo-CLAVE_RECEP"+camposLineaArchivo[((Integer) indice.get(CLAVE_RECEP)).intValue()]);
			log.debug("!#$%&/ camposLineaActual-CLAVE_RECEP"+camposLineaActual[((Integer) indice.get(CLAVE_RECEP)).intValue()]);
			log.debug("!#$%&/ camposLineaArchivo-CUENTA_BENEF"+camposLineaArchivo[((Integer) indice.get(CUENTA_BENEF)).intValue()]);
			log.debug("!#$%&/ camposLineaActual-CUENTA_BENEF"+camposLineaActual[((Integer) indice.get(CUENTA_BENEF)).intValue()]);
			log.debug("!#$%&/ camposLineaArchivo-NOMBRE_BENEF"+camposLineaArchivo[((Integer) indice.get(NOMBRE_BENEF)).intValue()]);
			log.debug("!#$%&/ camposLineaActual-NOMBRE_BENEF"+camposLineaActual[((Integer) indice.get(NOMBRE_BENEF)).intValue()]);
			
			//si el traspasante o receptor o cuenta no coinciden que lea el siguiente
			if(!camposLineaArchivo[((Integer) indice.get(CLAVE_TRASP)).intValue()].trim().equals(camposLineaActual[((Integer) indice.get(CLAVE_TRASP)).intValue()].trim())
					||!camposLineaArchivo[((Integer) indice.get(CLAVE_RECEP)).intValue()].trim().equals(camposLineaActual[((Integer) indice.get(CLAVE_RECEP)).intValue()].trim())
					||!camposLineaArchivo[((Integer) indice.get(CUENTA_BENEF)).intValue()].trim().equals(camposLineaActual[((Integer) indice.get(CUENTA_BENEF)).intValue()].trim())){
				log.debug("!#$%&/ alguno no cuadro... continue");
				continue;
			}

			log.debug("!#$%&/ cuadraron... revisa nombre... ");
			//si coinciden y el nombre es diferente
			if(!camposLineaArchivo[((Integer) indice.get(NOMBRE_BENEF)).intValue()].trim().equals(camposLineaActual[((Integer) indice.get(NOMBRE_BENEF)).intValue()].trim())){
				log.debug("!#$%&/ no cuadra el nombre... error");
				//retorna los datos localizados
				nombreLinea = new String [2];
				nombreLinea[0] = camposLineaArchivo[((Integer) indice.get(NOMBRE_BENEF)).intValue()].trim();
				nombreLinea[1] = ""+(i+1);	
				return nombreLinea; 
			}
			log.debug("!#$%&/ nombre igual... ");
		}
		return nombreLinea;
	}

	/**
	 * Valida los datos de banco - cuenta - beneficiario de la linea actual  
	 * contra el resto de los registros del archivo a cargar
	 * @param lineaActual numero de linea actual
	 * @param listaFileTransfer lineas del archivo
	 * @return numero de linea que se encontro duplicado 
	 */
	@SuppressWarnings("unchecked")
	private int existeBancoNombreCuentaBeneficiario(int lineaActual, List listaFileTransfer){
		String strLineaActual = ((FileTransfer) listaFileTransfer.get(lineaActual)).getCadena();
		String[] camposLineaActual = camposCuentasBancaComercial(strLineaActual);
		Map indice = indicesNombresCuentasBCom(true);
		//revisa solo las lineas anteriores
		for (int i = 0; i < lineaActual; i++) {
			FileTransfer lineaArchivo = (FileTransfer) listaFileTransfer.get(i);
			String[] camposLineaArchivo = camposCuentasBancaComercial(lineaArchivo.getCadena());
			if(camposLineaArchivo==null)continue;

			//existe una coincidencia completa (trasp-banco-cuenta-nombre) anterior en el archivo
			if(camposLineaArchivo[((Integer) indice.get(CLAVE_TRASP)).intValue()].trim().equals(camposLineaActual[((Integer) indice.get(CLAVE_TRASP)).intValue()].trim())
					&&camposLineaArchivo[((Integer) indice.get(CLAVE_RECEP)).intValue()].trim().equals(camposLineaActual[((Integer) indice.get(CLAVE_RECEP)).intValue()].trim())
					&&camposLineaArchivo[((Integer) indice.get(CUENTA_BENEF)).intValue()].trim().equals(camposLineaActual[((Integer) indice.get(CUENTA_BENEF)).intValue()].trim())
					&&camposLineaArchivo[((Integer) indice.get(NOMBRE_BENEF)).intValue()].trim().equals(camposLineaActual[((Integer) indice.get(NOMBRE_BENEF)).intValue()].trim())){
				return i+1; 
			}
		}
		return -1; //no se encontro nada
	}
	
	/**
	 * Valida la informacion del filetrasnfer de retiros de banca comercial
	 * 
	 * @param agenteFirmado
	 * @param idBoveda
	 * @param listaFileTransfer
	 * @param offset
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private int validaInformacionRetirosBCom(AgenteVO agenteFirmado,
			BigInteger idBoveda, List listaFileTransfer, int offset){
		
		long inicioProceso = Calendar.getInstance().getTimeInMillis();
		int ultimoRegistro = 0;
		
		for (int i = offset; i < listaFileTransfer.size(); i++) {
			
			FileTransfer fileTransfer = (FileTransfer) listaFileTransfer.get(i);
			boolean error = false;
			StringBuffer msgError = new StringBuffer(UtilsDaliVO.BLANK);
			StringBuffer numCampoError = new StringBuffer(UtilsDaliVO.BLANK);
			String cadenaErrorFormato = errorResolver.getMessage("J0051");
		
			/* Obtiene los campos correspondientes al layout */
			String[] campos = camposRetirosBancaComercial(fileTransfer.getCadena());
			
			if (campos == null) {
				msgError.append(cadenaErrorFormato);
				msgError.append("en la estructura del registro");
				msgError.append(GUION);
				error = true;
			} else {

				/* Obtiene el mapa de indices (true) correspondiente al layout */
				Map indice = indicesNombresRetirosBCom(true);

				/** ***** VALIDACIONES DE FORMATO ***** */
				
				//CLAVE TRASPASANTE
				if (StringUtils.isBlank(campos[((Integer) indice
						.get(CLAVE_TRASP)).intValue()])
						|| campos[((Integer) indice.get(CLAVE_TRASP))
								.intValue()].trim().length() != 5
						|| !campos[((Integer) indice.get(CLAVE_TRASP))
								.intValue()].matches(PATRON_NUMERICO)) {
					msgError.append(cadenaErrorFormato);
					msgError.append("de la clave del traspasante");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(CLAVE_TRASP))
							.toString());
					numCampoError.append(GUION);
					error = true;
				}
				
				// ID CUENTA BENEFICIARIO
				if (StringUtils.isBlank(campos[((Integer) indice
						.get(ID_CTA_BENEF)).intValue()])
						|| campos[((Integer) indice.get(ID_CTA_BENEF))
								.intValue()].trim().length() < 1
						|| !campos[((Integer) indice.get(ID_CTA_BENEF))
								.intValue()].matches(PATRON_NUMERICO)) {
					msgError.append(cadenaErrorFormato);
					msgError.append("del id de la cuenta beneficiario");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(ID_CTA_BENEF))
							.toString());
					numCampoError.append(GUION);
					error = true;
				}
				
				// IMPORTE
				String importe=campos[((Integer) indice.get(IMPORTE)).intValue()];
				String PATRON="\\d+(.\\d{2})?";
				if (StringUtils.isBlank(importe) || !importe.trim().matches(PATRON)){
					msgError.append(cadenaErrorFormato);
					msgError.append(" el importe debe ser una cantidad ###.##");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(IMPORTE))
							.toString());
					numCampoError.append(GUION);
					error = true;
				}				
				
				// REFERENCIA NUMERICA
				if (StringUtils.isBlank(campos[((Integer) indice
						.get(REFERENCIA)).intValue()])
						|| campos[((Integer) indice.get(REFERENCIA))
								.intValue()].trim().length() > 7
						|| !campos[((Integer) indice.get(REFERENCIA))
								.intValue()].matches(PATRON_NUMERICO)) {
					msgError.append(cadenaErrorFormato);
					msgError.append("de la referencia");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(REFERENCIA))
							.toString());
					numCampoError.append(GUION);
					error = true;
				}
				
				// CONCEPTO DE PAGO
				String concepto=campos[((Integer) indice.get(CONCEPTO)).intValue()];
				concepto = concepto.toUpperCase();
				String patronAlfaNumEspacio="[A-Z0-9\\s]+";
				if (StringUtils.isBlank(concepto)
						|| concepto.trim().length() > 40
						|| !concepto.trim().matches(patronAlfaNumEspacio)) {
					msgError.append(cadenaErrorFormato);
					msgError.append("del concepto");
					msgError.append(GUION);
					numCampoError.append(((Integer) indice.get(CONCEPTO))
							.toString());
					numCampoError.append(GUION);
					error = true;
				}
				
				if(!error){ 
					
					/** ***** VALIDACIONES DE NEGOCIO ***** */
					
					/* Valida que el traspasante sea el agente firmado */
					if (!campos[((Integer) indice.get(CLAVE_TRASP)).intValue()]
							.equals(agenteFirmado.getClave())) {
						msgError.append("No se pueden crear cuentas de otra institucion");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice
								.get(CLAVE_TRASP)).toString());
						numCampoError.append(GUION);
						error = true;
					}

					/* Valida que el traspasante exista */
					String cvTraspasante = campos[((Integer) indice.get(CLAVE_TRASP))];
					AgenteVO traspasante = new AgenteVO(cvTraspasante
							.substring(0, 2), cvTraspasante.substring(2));
					try {
						utilService.validaAgente(traspasante, " traspasante ",false);
					} catch (BusinessException e) {
						log.debug("WARN : [" + e.getMessage() + "]");
						msgError.append("El traspasante no esta registrado");
						msgError.append(GUION);
						numCampoError
								.append(((Integer) indice.get(CLAVE_TRASP))
										.toString());
						numCampoError.append(GUION);
						error = true;
					}
					
					//Validacion de traspasante existe en catalogo de instituciones
					InstitucionDTO instTrasp = institucionDaliDAO.buscarInstitucionPorClaveYFolio(
																campos[((Integer) indice.get(CLAVE_TRASP)).intValue()]);
					if (instTrasp == null) {
						msgError.append("La institucion con traspasante no existe en el catalogo");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice.get(CLAVE_TRASP)).toString());
						numCampoError.append(GUION);
						error = true;
					}
					
					//que el importe sea mayor a cero 
			        BigDecimal importeNumerico = null;
					try {
						importeNumerico = new BigDecimal(importe.trim());
						if(importeNumerico.compareTo(BigDecimal.ZERO) == 0) {
							msgError.append("El importe debe ser mayor a CERO");
							msgError.append(GUION);
							numCampoError.append(((Integer) indice.get(IMPORTE))
									.toString());
							numCampoError.append(GUION);
							error = true;
						}
					} catch (NumberFormatException e) {
						msgError.append(cadenaErrorFormato);
						msgError.append(" el importe debe ser una cantidad ###.##");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice.get(IMPORTE))
								.toString());
						numCampoError.append(GUION);
						error = true;
						log.error("el importe debe ser una cantidad ###.##", e);
					}

					//que el importe sea 13.2
					String[] cantidades = importe.trim().split("\\.");
					String enteros = null;
					String decimales = null;
					if(cantidades.length>2){
						msgError.append(cadenaErrorFormato);
						msgError.append(" el importe debe ser una cantidad ###.##");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice.get(IMPORTE))
								.toString());
						numCampoError.append(GUION);
						error = true;
					}else if(cantidades.length==1){ //vienen solo enteros
						enteros = cantidades[0];
					}else if(cantidades.length==2){ //vienen decimales
						enteros = cantidades[0];
						decimales = cantidades[1];
					}
					
					if(!StringUtils.isBlank(enteros) && enteros.length() > 13){
						msgError.append("La cantidad entera del importe no debe exceder de 13 digitos");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice.get(IMPORTE))
								.toString());
						numCampoError.append(GUION);
						error = true;
					}
					
					if(!StringUtils.isBlank(decimales) && decimales.length() > 2){
						msgError.append("La cantidad decimal del importe no debe exceder de 2 digitos");
						msgError.append(GUION);
						numCampoError.append(((Integer) indice.get(IMPORTE))
								.toString());
						numCampoError.append(GUION);
						error = true;
					}					
					
					if (instTrasp != null) {

					//que exista una cuenta liberada con el id para el traspasante
						CriterioCuentaEfectivoDTO criterio = new CriterioCuentaEfectivoDTO();
						criterio.setValorEn(new DivisaDTO(1));
						criterio.setInstitucion(instTrasp);
						criterio.setFolioCuentaPorTraspasante(campos[((Integer) indice.get(ID_CTA_BENEF)).intValue()].trim());
						EstadoInstruccionDTO estado = estadoInstruccionDaliDAO.consultarEstadoInstruccionPorClave("LA");
						criterio.setEstadoCuenta(estado==null||estado.getIdEstadoInstruccion()==0? null : (""+estado.getIdEstadoInstruccion()) );
						CuentaRetiroEfectivoDTO cuenta = admonRetiroEfectivo.buscarCuentaPorCriterio(criterio,true);
						
						if(cuenta==null){
							msgError.append("El id de cuenta no existe para el traspasante capturado");
							msgError.append(GUION);
							numCampoError.append(((Integer) indice.get(ID_CTA_BENEF)).toString());
							numCampoError.append(GUION);
							error = true;							
						}
						
					//que el importe no sea mayor al limite por transaccion  (si aplica)
						else if(importeNumerico!=null
								&& cuenta.getMontoMaximoXTran()!=null  
				        		&& importeNumerico.doubleValue() > cuenta.getMontoMaximoXTran().doubleValue()){
							msgError.append("El importe no debe ser mayor al monto maximo por transaccion");
							msgError.append(GUION);
							numCampoError.append(((Integer) indice.get(IMPORTE)).toString());
							numCampoError.append(GUION);
							error = true;
						}					
				        
					}
				}
			}	
						
			if (error) {
				fileTransfer.setEstado(ESTADO_ERROR);
				if (numCampoError.length() > 0) {
					fileTransfer.setCamposError(numCampoError.toString()
							.substring(0, numCampoError.length() - 1));
				}
				fileTransfer.setError(msgError.toString().substring(0,
						msgError.length() - 1));
				fileTransferDao.update(fileTransfer);
			}

			ultimoRegistro = i;
			long finalProceso = Calendar.getInstance().getTimeInMillis();
			if ((finalProceso - inicioProceso) > 10000) {
				break;
			}		
		}
		
		if ((ultimoRegistro + 1) == listaFileTransfer.size()) {
			return -1;
		}

		return ultimoRegistro + 1;	
	}

	/**
	 * Definicion de layout del archivo de cuentas de banca comercial
	 * @param cadena linea en el archivo
	 * 
	 */
	private String[] camposRetirosBancaComercial(String cadena){
		log.trace("Entrando a FileTransferServiceImpl.camposCuentasBancaComercial()");

		if (StringUtils.isBlank(cadena)) {
			throw new BusinessDataException(errorResolver.getMessage("J0026",
					new Object[] { " la cadena de informacion " }), "J0026");
		}

		List<Integer> posiciones = new ArrayList<Integer>();
		posiciones.add(new Integer(5));   //id folio traspasante			
		posiciones.add(new Integer(6));   //id cuenta por traspasante
		posiciones.add(new Integer(16));  //importe
		posiciones.add(new Integer(7));   //referencia
		posiciones.add(new Integer(40));  //concepto pago
		
		if(cadena==null || cadena.length() < LONGITUD_RETIROS_BCOM ){
			return null;
		}

		return obtieneCampos(cadena, posiciones);
	}
	
	/**
	 * Definicion de layout del archivo de cuentas de banca comercial
	 * @param cadena linea en el archivo
	 * 
	 */
	private String[] camposTraspasosEfectivo(String cadena){
		log.trace("Entrando a FileTransferServiceImpl.camposTraspasosEfectivo");

		if (StringUtils.isBlank(cadena)) {
			throw new BusinessDataException(errorResolver.getMessage("J0026",
					new Object[] { " la cadena de informacion " }), "J0026");
		}

		List<Integer> posiciones = new ArrayList<Integer>();
		posiciones.add(new Integer(5));   // Traspasante			
		posiciones.add(new Integer(5));   // Receptor
		posiciones.add(new Integer(15));  // importe
		posiciones.add(new Integer(3));   // divisa
		posiciones.add(new Integer(18));  // boveda
		
		if(cadena==null || cadena.length() < LONGITUD_TRASPASOS_EFECTIVO ){
			return null;
		}

		return obtieneCampos(cadena, posiciones);
	}
	/**
	 * Genera el mapa de indices (true) o de nombres (false) correspondientes a
	 * traspasos de mercado capitales
	 * 
	 * @param obtieneIndices
	 * @return Map
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	private Map indicesNombresRetirosBCom(boolean obtieneIndices){
		Map mapa = new TreeMap();
		if (obtieneIndices) {
			mapa.put(CLAVE_TRASP,  new Integer(0));
			mapa.put(ID_CTA_BENEF, new Integer(1));
			mapa.put(IMPORTE,      new Integer(2));
			mapa.put(REFERENCIA,   new Integer(3));
			mapa.put(CONCEPTO,     new Integer(4));
		} else {
			mapa.put(new Integer(0), CLAVE_TRASP );
			mapa.put(new Integer(1), ID_CTA_BENEF);
			mapa.put(new Integer(2), IMPORTE     );
			mapa.put(new Integer(3), REFERENCIA  );
			mapa.put(new Integer(4), CONCEPTO    );
		}
		return mapa;
	}
	
	
	
	/**
	 * @param utilService
	 */
	public void setUtilService(UtilServices utilService) {
		this.utilService = utilService;
	}

	/**
	 * @param errorResolver
	 */
	public void setErrorResolver(MessageResolver errorResolver) {
		this.errorResolver = errorResolver;
	}

	/**
	 * @param fileTransferDao
	 */
	public void setFileTransferDao(FileTransferDao fileTransferDao) {
		this.fileTransferDao = fileTransferDao;
	}

	/**
	 * @param instrumentoDaliDao
	 *            the instrumentoDaliDao to set
	 */
	public void setInstrumentoDao(InstrumentoDaliDao instrumentoDaliDao) {
		this.instrumentoDaliDao = instrumentoDaliDao;
	}

	/**
	 * @param fileTransferOperacionesDao
	 *            the fileTransferOperacionesDao to set
	 */
	public void setFileTransferOperacionesDao(
			FileTransferOperacionesDao fileTransferOperacionesDao) {
		this.fileTransferOperacionesDao = fileTransferOperacionesDao;
	}

	/**
	 * @param dateUtilsDao
	 *            the dateUtilsDao to set
	 */
	public void setDateUtilsDao(DateUtilsDao dateUtilsDao) {
		this.dateUtilsDao = dateUtilsDao;
	}

	/**
	 * @param diaInhabilDaliDao
	 *            the diaInhabilDaliDao to set
	 */
	public void setDiaInhabilDao(DiaInhabilDaliDao diaInhabilDaliDao) {
		this.diaInhabilDaliDao = diaInhabilDaliDao;
	}

	/**
	 * @param emisionDao
	 *            the emisionDao to set
	 */
	public void setEmisionDao(EmisionDaliDAO emisionDao) {
		this.emisionDao = emisionDao;
	}

	/**
	 * @param operacionNombradaDao
	 *            the operacionNombradaDao to set
	 */
	public void setOperacionNombradaDao(
			OperacionNombradaDao operacionNombradaDao) {
		this.operacionNombradaDao = operacionNombradaDao;
	}

	/**
	 * @param cuentasInvalidas the cuentasInvalidas to set
	 */
	public void setCuentasInvalidas(List<String> cuentasInvalidas) {
		this.cuentasInvalidas = cuentasInvalidas;
	}

	/**
	 * DAO para consulta de Instituciones
	 * @return the institucionDaliDAO
	 */
	public InstitucionDaliDAO getInstitucionDaliDAO() {
		return institucionDaliDAO;
	}

	/**
	 * DAO para consulta de Instituciones
	 * @param institucionDaliDAO the institucionDaliDAO to set
	 */
	public void setInstitucionDaliDAO(InstitucionDaliDAO institucionDaliDAO) {
		this.institucionDaliDAO = institucionDaliDAO;
	}

	public DepositanteValidoBanxicoDao getDepositanteValidoBanxicoDao() {
		return depositanteValidoBanxicoDao;
	}

	public void setDepositanteValidoBanxicoDao(
			DepositanteValidoBanxicoDao depositanteValidoBanxicoDao) {
		this.depositanteValidoBanxicoDao = depositanteValidoBanxicoDao;
	}

	public AdministracionCuentasRetiroService getAdmonCuentasService() {
		return admonCuentasService;
	}

	public void setAdmonCuentasService(
			AdministracionCuentasRetiroService admonCuentasService) {
		this.admonCuentasService = admonCuentasService;
	}

	public EstadoInstruccionDaliDAO getEstadoInstruccionDaliDAO() {
		return estadoInstruccionDaliDAO;
	}

	public void setEstadoInstruccionDaliDAO(
			EstadoInstruccionDaliDAO estadoInstruccionDaliDAO) {
		this.estadoInstruccionDaliDAO = estadoInstruccionDaliDAO;
	}

	public AdmonRetirosEfectivoService getAdmonRetiroEfectivo() {
		return admonRetiroEfectivo;
	}

	public void setAdmonRetiroEfectivo(
			AdmonRetirosEfectivoService admonRetiroEfectivo) {
		this.admonRetiroEfectivo = admonRetiroEfectivo;
	}

	public ValidacionService getValidacionService() {
		return validacionService;
	}

	public void setValidacionService(ValidacionService validacionService) {
		this.validacionService = validacionService;
	}

	public DivisaDaliDAO getDivisaDAO() {
		return divisaDAO;
	}

	public void setDivisaDAO(DivisaDaliDAO divisaDAO) {
		this.divisaDAO = divisaDAO;
	}

	public BovedaDaliDAO getBovedaDAO() {
		return bovedaDAO;
	}

	public void setBovedaDAO(BovedaDaliDAO bovedaDAO) {
		this.bovedaDAO = bovedaDAO;
	}

	public void setCuentaDaliDao(CuentaDaliDAO cuentaDaliDao) {
		this.cuentaDaliDao = cuentaDaliDao;
	}

	
}

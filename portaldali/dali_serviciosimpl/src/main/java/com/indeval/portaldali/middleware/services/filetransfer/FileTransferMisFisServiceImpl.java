/**
 * Bursatec - Portal DALI
 * Copyrigth (c) 2014 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.filetransfer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.bursatec.util.message.MessageResolver;
import com.indeval.portaldali.middleware.constantes.FileTransferConstantes;
import com.indeval.portaldali.middleware.dto.filetransfer.CampoRegFileTransDto;
import com.indeval.portaldali.middleware.dto.filetransfer.RegistroFileTransDto;
import com.indeval.portaldali.middleware.services.filetransfer.formatos.FormatoMiscelaneaFiscal;
import com.indeval.portaldali.middleware.services.filetransfer.util.Constantes;
import com.indeval.portaldali.middleware.services.filetransfer.util.ParserFileTransfer;
import com.indeval.portaldali.middleware.services.filetransfer.validacion.ValidadorMiscelaneaFiscal;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessDataException;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import com.indeval.portaldali.middleware.services.util.FileUploadService;
import com.indeval.portaldali.middleware.services.util.UtilServices;
import com.indeval.portaldali.middleware.services.util.vo.ProcessInfoVO;
import com.indeval.portaldali.persistence.dao.common.DateUtilsDao;
import com.indeval.portaldali.persistence.dao.filetransfer.FileTransferDao;
import com.indeval.portaldali.persistence.dao.filetransfer.FileTransferMiscelaneaDao;
import com.indeval.portaldali.persistence.model.FileTransfer;
import com.indeval.portaldali.persistence.model.FileTransferMiscelanea;
import com.indeval.portaldali.persistence.model.FileTransferPK;
import com.indeval.portaldali.persistence.util.DateUtil;

/**
 * Implementación de la interfaz de negocio FileTransferMisFisService.
 * 
 * @author Pablo Balderas
 */
public class FileTransferMisFisServiceImpl implements FileTransferMisFisService {

	/** Dao de utileria de fechas */
	private DateUtilsDao dateUtilsDao;
	
	/** Dao de file transfer */
	private FileTransferDao fileTransferDao;

	/** Dao para el file transfer de miscelanea fiscal */
	private FileTransferMiscelaneaDao fileTransferMiscelaneaDao;
	
	/** Servicio para el bloqueo y cordinación del servicio */
	private FileUploadService fileUploadService = null;
	
	/** Parser para los procesos de miscelanea fiscal */
	private ParserFileTransfer parserFileTransfer;
	
	/** Objeto para realizar las validaciones del registro */
	private ValidadorMiscelaneaFiscal validadorMiscelaneaFiscal;

	/** Bean para acceso al properties de mensaje de errores */
	private MessageResolver errorResolver;
	
	/** Servicio para sacar folios de secuencia */
	private UtilServices utilService;
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.filetransfer.FileTransferMisFisService#registrarFileTransfer(java.util.List, com.indeval.portaldali.middleware.services.modelo.AgenteVO, java.lang.String, java.lang.String)
	 */
	public TotalesProcesoVO registrarFileTransfer(List<String> registros,
			AgenteVO agenteFirmado, String tipoFileTransfer, String usuario) throws BusinessDataException {
		try{
			//Objeto con el resultado de la operación
			TotalesProcesoVO totalesProceso = new TotalesProcesoVO();
			//Indica el total de registros procesados.
			int totalProcesados = 0;
			//Indica el total de registros con error.
			int totalError = 0;
			//Lista con la información de los registros procesados
			List<RegistroFileTransDto> registrosProcesados = new ArrayList<RegistroFileTransDto>();
			//Bloquea el file upload
			ProcessInfoVO processInfoVO = bloquearFileUpload(agenteFirmado, tipoFileTransfer);
			//Limpia las tablas de file transfer
			limpiarRegistrosFileTransfer(agenteFirmado, tipoFileTransfer);
			//Inicia el consecutivo
	 		int consecutivo = 1;
	 		//Se recorre la lista con los registros y se arman los objetos a ser persistidos.
			for (String registro : registros) {
				Date fechaProcesamiento = dateUtilsDao.getDateFechaDB();
				RegistroFileTransDto registroFileTransDto = new RegistroFileTransDto();
				registroFileTransDto.setConsecutivo(consecutivo);
				registroFileTransDto.setUsuario(usuario);
				registroFileTransDto.setFechaProcesamiento(fechaProcesamiento);
				FileTransfer fileTransfer = 
					fileTransferBuilder(agenteFirmado, tipoFileTransfer, usuario, consecutivo, registro);
				//Valida si el registro cumple con la longitud requerida.
				if(validadorMiscelaneaFiscal.validarLongitudRegistro(tipoFileTransfer, registro)) {
					//Obtiene los campos del registro
					List<CampoRegFileTransDto> camposRegistro = 
						parserFileTransfer.parsearRegistroFileTransfer(registro, tipoFileTransfer);
					//Si la validación es correcta, guarda el registro en la tabla de
					//file transfer de miscelanea fiscal.
					if(validadorMiscelaneaFiscal.validarRegistro(fileTransfer, camposRegistro)) {
						FormatoMiscelaneaFiscal formatoMiscelaneaFiscal = 
							(FormatoMiscelaneaFiscal) parserFileTransfer.obtenerObjectoDeCampos(tipoFileTransfer, camposRegistro);
						FileTransferMiscelanea fileTransferMiscelanea = 
							fileTransferMiscelaneaBuilder(agenteFirmado, tipoFileTransfer, consecutivo, formatoMiscelaneaFiscal);
						determinarCompraVenta(agenteFirmado, fileTransferMiscelanea);
						try {
							fileTransferMiscelaneaDao.save(fileTransferMiscelanea);
							totalProcesados++;
						}
						catch(Exception e) {
							cancelarFileTransfer(agenteFirmado, tipoFileTransfer);
							throw new BusinessDataException(errorResolver.getMessage("J0105"), "J0105");
						}
						registroFileTransDto.setEdoRegistro(Constantes.ESTADO_NUEVO);
						registroFileTransDto.setCamposRegistro(camposRegistro);
					}
					else {
						totalError++;
						registroFileTransDto.setEdoRegistro(Constantes.ESTADO_ERROR);
						registroFileTransDto.setMensajesRegistro(fileTransfer.getError().split(Constantes.GUION));
						registroFileTransDto.setCamposRegistro(camposRegistro);
					}
				}
				else {
					//Valida si la cadena es mayor a 255 caracteres
					if(FileTransferConstantes.LONGITUD_MAXIMA_CADENA < registro.length()) {
						fileTransfer.setCadena(registro.substring(0, FileTransferConstantes.LONGITUD_MAXIMA_CADENA));
					}
					//Obtiene y coloca el mensaje de error
					String mensajeError = errorResolver.getMessage("J0400");
					fileTransfer.setEstado(Constantes.ESTADO_ERROR);
					fileTransfer.setError(mensajeError);
					registroFileTransDto.setEdoRegistro(Constantes.ESTADO_ERROR);
					registroFileTransDto.setMensajesRegistro(fileTransfer.getError().split(Constantes.GUION));
					registroFileTransDto.setCadenaRegistro(registro);
					totalError++;
				}
				//Registra en la tabla de file transfer
				Serializable nuevaEntidad = fileTransferDao.save(fileTransfer);
				if (nuevaEntidad == null) {
					cancelarFileTransfer(agenteFirmado, tipoFileTransfer);
					throw new BusinessDataException(
						errorResolver.getMessage(
							"J0048", 
							(Object) ReflectionToStringBuilder.reflectionToString(
								fileTransfer.getFileTransferPK())), 
							"J0048");
				}
				//Aumenta el indice
				consecutivo++;
				//Agrega el registro procesado a la lista
				registrosProcesados.add(registroFileTransDto);
			}
			//Actualiza el file upload a confirmar
			processInfoVO.setStatus(ProcessInfoVO.CONFIRMACION);
			fileUploadService.updateProcessInfo(processInfoVO);
			//Coloca los datos de la operacion
			totalesProceso.setRegistrosACargar(totalProcesados);
			totalesProceso.setRegistrosConError(totalError);
			totalesProceso.setPaginaVO(new PaginaVO());
			totalesProceso.getPaginaVO().setRegistros(registrosProcesados);
			//Retorna el resultado de la operacion
			return totalesProceso;
		}
		catch(BusinessException exception) {
			throw new BusinessDataException(exception.getMessage(), "J0214");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.filetransfer.FileTransferMisFisService#obtenerInformacionFileTransfer(com.indeval.portaldali.middleware.services.modelo.AgenteVO, java.lang.String, com.indeval.portaldali.middleware.services.modelo.PaginaVO)
	 */
	@SuppressWarnings("all")
	public TotalesProcesoVO obtenerInformacionFileTransfer(AgenteVO agenteFirmado, String tipoFileTransfer) 
			throws BusinessDataException {
		//Lista de campos por registro
		List<CampoRegFileTransDto> camposRegistro = null;
		//Total de registros
		int totalregistros = 0;
		//Registros con error
		int registrosError = 0;
		//Lista con la información de los registros procesados
		List<RegistroFileTransDto> registrosProcesados = new ArrayList<RegistroFileTransDto>();
		//Objeto con la información del file transfer
		TotalesProcesoVO totalesProcesoVO = new TotalesProcesoVO();
		//Obtiene los regisitros del file transfer
		List<FileTransfer> listFileTransfer = 
			consultarFileTransfer(agenteFirmado.getId(), agenteFirmado.getFolio(), tipoFileTransfer);
		//Colocamos el total del registros
		totalregistros = listFileTransfer.size();
		//Iteramos la lista de registros
		for (FileTransfer fileTransfer : listFileTransfer) {
			RegistroFileTransDto registroFileTransDto = new RegistroFileTransDto();
			registroFileTransDto.setConsecutivo(fileTransfer.getFileTransferPK().getConsec().intValue());
			registroFileTransDto.setUsuario(fileTransfer.getUsuario());
			registroFileTransDto.setFechaProcesamiento(fileTransfer.getFechaReg());
			registroFileTransDto.setEdoRegistro(fileTransfer.getEstado());
			//Validamos si es estado es de error
			if (Constantes.ESTADO_ERROR.equals(fileTransfer.getEstado())) {
				if(StringUtils.isNotBlank(fileTransfer.getCamposError())) {
					//Obtenemos los campos
					camposRegistro = 
							parserFileTransfer.parsearRegistroFileTransfer(fileTransfer.getCadena(), tipoFileTransfer);	
					String[] camposConError = fileTransfer.getCamposError().split(Constantes.GUION);
					for (String campoError : camposConError) {
						int numCampo = Integer.valueOf(campoError);
						camposRegistro.get(numCampo - 1).setCampoCorrecto(Boolean.FALSE);
					}
					registroFileTransDto.setCamposRegistro(camposRegistro);
				}
				else {
					registroFileTransDto.setCadenaRegistro(fileTransfer.getCadena());
				}
				registroFileTransDto.setMensajesRegistro(fileTransfer.getError().split(Constantes.GUION));
				registrosError++;
			}
			else {
				registroFileTransDto.setCamposRegistro(
					parserFileTransfer.parsearRegistroFileTransfer(fileTransfer.getCadena(), tipoFileTransfer));
			}
			registrosProcesados.add(registroFileTransDto);
		}
		//Si existen resultados, arma el objeto que se regresa al controller
		if (registrosProcesados != null && !registrosProcesados.isEmpty()) {
			PaginaVO paginaVO = new PaginaVO();
			paginaVO.setRegistros(registrosProcesados);
			totalesProcesoVO.setPaginaVO(paginaVO);
			totalesProcesoVO.setRegistrosACargar(totalregistros - registrosError);
			totalesProcesoVO.setRegistrosConError(registrosError);
		}
		//Regresa el objeto
		return totalesProcesoVO;
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.filetransfer.FileTransferMisFisService#obtenerErroresFileTransfer(com.indeval.portaldali.middleware.services.modelo.AgenteVO, java.lang.String)
	 */
	public List<RegistroFileTransDto> obtenerErroresFileTransfer(AgenteVO agenteFirmado, String tipoFileTransfer) throws BusinessDataException {
		List<CampoRegFileTransDto> camposRegistro = null;
		//Lista con la información de los registros procesados
		List<RegistroFileTransDto> registrosProcesados = new ArrayList<RegistroFileTransDto>();
		//Obtiene los regisitros del file transfer
		List<FileTransfer> listFileTransfer = 
			consultarFileTransfer(agenteFirmado.getId(), agenteFirmado.getFolio(), tipoFileTransfer);
		//Iteramos la lista de registros
		for (FileTransfer fileTransfer : listFileTransfer) {
			if(Constantes.ESTADO_ERROR.equals(fileTransfer.getEstado())) {
				RegistroFileTransDto registroFileTransDto = new RegistroFileTransDto();
				registroFileTransDto.setConsecutivo(fileTransfer.getFileTransferPK().getConsec().intValue());
				registroFileTransDto.setUsuario(fileTransfer.getUsuario());
				registroFileTransDto.setFechaProcesamiento(fileTransfer.getFechaReg());
				registroFileTransDto.setEdoRegistro(fileTransfer.getEstado());
				if(StringUtils.isNotBlank(fileTransfer.getCamposError())) {
					camposRegistro = 
						parserFileTransfer.parsearRegistroFileTransfer(fileTransfer.getCadena(), tipoFileTransfer);			
					registroFileTransDto.setCamposRegistro(camposRegistro);
					String[] camposConError = fileTransfer.getCamposError().split(Constantes.GUION);
					for (String campoError : camposConError) {
						int numCampo = Integer.valueOf(campoError);
						camposRegistro.get(numCampo - 1).setCampoCorrecto(Boolean.FALSE);
					}
				}
				else {
					registroFileTransDto.setCadenaRegistro(fileTransfer.getCadena());
				}
				registroFileTransDto.setMensajesRegistro(fileTransfer.getError().split(Constantes.GUION));
				registrosProcesados.add(registroFileTransDto);
			}
		}
		listFileTransfer = null;
		camposRegistro = null;
		return registrosProcesados;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.filetransfer.FileTransferMisFisService#obtenerResumenFileTransfer(com.indeval.portaldali.middleware.services.modelo.AgenteVO, java.lang.String)
	 */
	public ResumenVO obtenerResumenFileTransfer(AgenteVO agenteFirmado, String tipoFileTransfer) throws BusinessException {
		int registrosCargados = 0;
		int registrosError = 0;
		int registrosNuevos = 0;
		int totalRegistros = 0;
		int registrosProtocolo = 0;
		String nombreUsuario = null;
		Date fechaCarga = null;

		//Se construye el objeto de retorno y se settean los parametros recibidos
		ResumenVO resumenVO = new ResumenVO();
		resumenVO.setAgenteFirmado(agenteFirmado);
		resumenVO.setTipoProceso(tipoFileTransfer);
		//Obtiene la lista de instancias FileTransfer
		List<FileTransfer> listaFileTransfer = 
			consultarFileTransfer(agenteFirmado.getId(), agenteFirmado.getFolio(), tipoFileTransfer);
		// Se cuentan los registros por estado
		for(FileTransfer fileTransfer : listaFileTransfer) {
			if (Constantes.ESTADO_ERROR.equals(fileTransfer.getEstado())) {
				registrosError++;
			}
			else if (Constantes.ESTADO_NUEVO.equals(fileTransfer.getEstado())) {
				registrosNuevos++;
			}
			else if (Constantes.ESTADO_CARGADO.equals(fileTransfer.getEstado())) {
				registrosCargados++;
			}
			else if (Constantes.ESTADO_PROTOCOLO.equals(fileTransfer.getEstado())) {
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
		resumenVO.setHoraCarga(DateUtil.obetnerHoraDeFecha(fechaCarga));
		resumenVO.setTotalCargados(registrosCargados);
		resumenVO.setTotalError(registrosError);
		resumenVO.setTotalNuevos(registrosNuevos);
		resumenVO.setTotalRegistros(totalRegistros);
		resumenVO.setTotalProtocolo(registrosProtocolo);
		resumenVO.setNombreUsuario(nombreUsuario);
		return resumenVO;
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.filetransfer.FileTransferMisFisService#obtenerOperacionesMiscelaneaFiscal(java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<FileTransferMiscelaneaVo> obtenerOperacionesMiscelaneaFiscal(String idInstitucion, String folioInstitucion, String tipoFileTransfer)
			throws BusinessException {
		//Lista con los TLP que corresponden a los registros que serán enviados al PFI
		List<FileTransferMiscelaneaVo> operaciones = null;
		//Obtenemos los registros guardados del file transfer de miscelanea fiscal
		List<FileTransferMiscelanea> registros =
			fileTransferMiscelaneaDao.getByIdFolioTipo(idInstitucion, folioInstitucion, tipoFileTransfer);
		if(registros != null && !registros.isEmpty()) {
			operaciones = new ArrayList<FileTransferMiscelaneaVo>();
			//Iteramos la lista
			for (FileTransferMiscelanea fileTransferMiscelanea : registros) {
				FileTransferMiscelaneaVo vo = new FileTransferMiscelaneaVo();
				vo.setIdInst(fileTransferMiscelanea.getFileTransferPK().getIdInst());
			    vo.setFolioInst(fileTransferMiscelanea.getFileTransferPK().getFolioInst());
			    vo.setTipoReg(fileTransferMiscelanea.getFileTransferPK().getTipoReg());
			    vo.setConsec(fileTransferMiscelanea.getFileTransferPK().getConsec().intValue());
			    vo.setFechaRegistro(fileTransferMiscelanea.getFechaRegistro());
			    vo.setFechaHoraCierreOper(fileTransferMiscelanea.getFechaHoraCierreOper());
			    vo.setIdTraspasante(fileTransferMiscelanea.getIdTraspasante());
			    vo.setFolioTraspasante(fileTransferMiscelanea.getFolioTraspasante());
			    vo.setCuentaTraspasante(fileTransferMiscelanea.getCuentaTraspasante());
			    vo.setIdReceptor(fileTransferMiscelanea.getIdReceptor());
			    vo.setFolioReceptor(fileTransferMiscelanea.getFolioReceptor());
			    vo.setCuentaReceptor(fileTransferMiscelanea.getCuentaReceptor());
			    vo.setTipoValor(fileTransferMiscelanea.getTipoValor());
			    vo.setEmisora(fileTransferMiscelanea.getEmisora());
			    vo.setSerie(fileTransferMiscelanea.getSerie());
			    vo.setCupon(fileTransferMiscelanea.getCupon());
			    vo.setIsin(fileTransferMiscelanea.getIsin());
			    vo.setBoveda(fileTransferMiscelanea.getBoveda());
			    vo.setCantidadOperada(fileTransferMiscelanea.getCantidadOperada());
			    vo.setDivisa(fileTransferMiscelanea.getDivisa());
			    vo.setFechaAdquisicion(fileTransferMiscelanea.getFechaAdquisicion());
			    vo.setPrecioAdquisicion(fileTransferMiscelanea.getPrecioAdquisicion());
			    vo.setCliente(fileTransferMiscelanea.getCliente());
			    vo.setRfcCurp(fileTransferMiscelanea.getRfcCurp());
			    vo.setExtranjero(fileTransferMiscelanea.isExtranjero());
			    vo.setCostoFiscalActualizado(fileTransferMiscelanea.getCostoFiscalActualizado());
			    vo.setCompra(fileTransferMiscelanea.isCompra());
				operaciones.add(vo);
			}
		}
		return operaciones;
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.filetransfer.FileTransferMisFisService#cancelarFileTransfer(com.indeval.portaldali.middleware.services.modelo.AgenteVO, java.lang.String)
	 */
	public void cancelarFileTransfer(AgenteVO agenteFirmado, String tipoProceso) throws BusinessException {
		try {
			limpiarRegistrosFileTransfer(agenteFirmado, tipoProceso);
			//Desbloquea el candado de file upload
			ProcessInfoVO processInfo = new ProcessInfoVO();
			processInfo.setUsuario(agenteFirmado.getId() + agenteFirmado.getFolio());
	        processInfo.setIdProceso(agenteFirmado.getId() + agenteFirmado.getFolio() + tipoProceso);
	    	fileUploadService.releaseLock(processInfo);
		}
		catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.filetransfer.FileTransferMisFisService#validarRegistrosAProcesar(com.indeval.portaldali.middleware.services.modelo.AgenteVO, java.lang.String)
	 */
	public boolean validarRegistrosAProcesar(AgenteVO agenteFirmado, String tipoFileTransfer) {

		ProcessInfoVO processInfo = new ProcessInfoVO();
		processInfo.setUsuario(agenteFirmado.getId() + agenteFirmado.getFolio());
        processInfo.setIdProceso(agenteFirmado.getId() + agenteFirmado.getFolio() + tipoFileTransfer);
		processInfo = fileUploadService.getProcessInfo(processInfo);
		
		List<FileTransferMiscelanea> registros =
			fileTransferMiscelaneaDao.getByIdFolioTipo(
				agenteFirmado.getId(), 
				agenteFirmado.getFolio(), 
				tipoFileTransfer);
		return processInfo != null && registros != null && !registros.isEmpty();
	}
	
	/**
	 * Método que limpia las tablas de file transfer.
	 * @param agenteFirmado Agente firmado.
	 * @param tipoFileTransfer Tipo de file transfer.
	 */
	@SuppressWarnings("all")
	private void limpiarRegistrosFileTransfer(AgenteVO agenteFirmado, String tipoFileTransfer) {
		//Obtiene los registros del file transfer
		List<FileTransfer> registrosFileTransfer = 
			fileTransferDao.getByIdFolioTipo(agenteFirmado.getId(), agenteFirmado.getFolio(), tipoFileTransfer);
		//Borra los objetos recuperados de la bd
		for (FileTransfer fileTransfer : registrosFileTransfer) {
			fileTransferDao.delete(fileTransfer);
		}
		//Realiza un flush a la tabla
		fileTransferDao.flush();
		//Obtiene los registros de file transfer de miscelanea fiscal
		List<FileTransferMiscelanea> registrosFileTransferMiscelanea = 
			fileTransferMiscelaneaDao.getByIdFolioTipo(agenteFirmado.getId(), agenteFirmado.getFolio(), tipoFileTransfer);
		//Borra los objetos recuperados de la bd
		for (FileTransferMiscelanea fileTransferMiscelanea : registrosFileTransferMiscelanea) {
			fileTransferMiscelaneaDao.delete(fileTransferMiscelanea);
		}
		//Realiza un flush a la tabla
		fileTransferMiscelaneaDao.flush();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.filetransfer.FileTransferMisFisService#actualizarRegistrosProcesadosPFI(com.indeval.portaldali.middleware.services.modelo.AgenteVO, java.lang.String, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public void actualizarRegistrosProcesadosPFI(AgenteVO agenteFirmado, String tipoProceso, Map<Integer, Boolean> resultadoOper)
			throws BusinessException {
		// Obtiene la lista de instancias FileTransfer
		List<FileTransfer> listaFileTransfer = 
			fileTransferDao.getByIdFolioTipo(agenteFirmado.getId(), agenteFirmado.getFolio(), tipoProceso);	
		//Itera la lista y actualiza los registros que fueron enviados correctamente al PFI
		for (FileTransfer fileTransfer : listaFileTransfer) {
			boolean procesado = false;
			if (resultadoOper != null && !resultadoOper.isEmpty()
					&& resultadoOper.containsKey(fileTransfer.getFileTransferPK().getConsec().intValue())) {
				procesado = resultadoOper.get(fileTransfer.getFileTransferPK().getConsec().intValue());
			}
			if(procesado) {
				fileTransfer.setEstado(Constantes.ESTADO_PROTOCOLO);
				fileTransferDao.update(fileTransfer);
			}
			else {
				fileTransfer.setEstado(Constantes.ESTADO_ERROR);
				fileTransferDao.update(fileTransfer);
			}
			// Si no fue enviado a protocolo, este tipo de operacion no es soportado
			if (fileTransfer.getEstado().equalsIgnoreCase(Constantes.ESTADO_NUEVO)) {
				fileTransfer.setEstado(Constantes.ESTADO_ERROR);
				fileTransfer.setError("El tipo de operacion no es soportado por este FileTransfer");
				fileTransferDao.update(fileTransfer);
			}
		}
		
	}
	
	/**
	 * Bloquea la tabla de file upload para el agente firmado y el tipo de file transfer.
	 * @param agenteFirmado Agente firmado que ejecuta el file transfer.
	 * @param tipoFileTransfer Tipo de file transfer.
	 * @return Vo con la información del candado.
	 * @throws BusinessException En caso de ocurrir un error o que la tabla ya este bloqueada.
	 */
	private ProcessInfoVO bloquearFileUpload(AgenteVO agenteFirmado, String tipoFileTransfer) throws BusinessException {
        ProcessInfoVO processInfo = new ProcessInfoVO();
		processInfo.setUsuario(agenteFirmado.getId() + agenteFirmado.getFolio());
        processInfo.setIdProceso(agenteFirmado.getId() + agenteFirmado.getFolio() + tipoFileTransfer);
        processInfo.setAbort(FileTransferConstantes.ABORT_FALSE);
        processInfo.setPorcentajeTerminado(new BigDecimal(0));
        processInfo.setStatus(ProcessInfoVO.CARGANDO);
        Boolean candado = fileUploadService.getLock(processInfo);
        if(!candado) {
        	String mensajeError = 
        		errorResolver.getMessage("J0214");
        	throw new BusinessException(mensajeError);
        }
        return processInfo;
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.filetransfer.FileTransferMisFisService#consultarFileTransfer(java.lang.String, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("all")
	private List<FileTransfer> consultarFileTransfer(String id, String folio, String tipoProceso) throws BusinessException {
		return fileTransferDao.getByIdFolioTipo(id, folio, tipoProceso);
	}

	/**
	 * Contruye un objeto de tipo FileTransfer.
	 * @param agenteFirmado Agente firmado en el sistema.
	 * @param tipoFileTransfer Tipo de file transfer.
	 * @param usuario Usuario que ejecuta el proceso.
	 * @param consecutivo Consecutivo del registro.
	 * @param registro Registro recibido.
	 * @return Objeto FileTransfer.
	 */
	private FileTransfer fileTransferBuilder(AgenteVO agenteFirmado,
			String tipoFileTransfer, String usuario, int consecutivo, String registro) {
		FileTransfer fileTransfer = new FileTransfer();
		fileTransfer.setFileTransferPK(new FileTransferPK());
		fileTransfer.getFileTransferPK().setIdInst(agenteFirmado.getId());
		fileTransfer.getFileTransferPK().setFolioInst(agenteFirmado.getFolio());
		fileTransfer.getFileTransferPK().setTipoReg(tipoFileTransfer);
		fileTransfer.getFileTransferPK().setConsec(new BigDecimal(consecutivo));
		fileTransfer.setCadena(registro);
		fileTransfer.setEstado(Constantes.ESTADO_NUEVO);
		fileTransfer.setFechaReg(dateUtilsDao.getDateFechaDB());
		fileTransfer.setUsuario(usuario);
		return fileTransfer;
	}
	
	
	/**
	 * Construye un pojo de file transfer de miscelanea fiscal a partir de un formato.
	 * @param agenteFirmado Agente firmado.
	 * @param tipoFileTransfer Tipo de file transfer.
	 * @param consecutivo Consecutivo.
	 * @param formatoMiscelaneaFiscal Formato a partir del cual se construye el POJO
	 * @return POJO.
	 */
	private FileTransferMiscelanea fileTransferMiscelaneaBuilder(
			AgenteVO agenteFirmado, String tipoFileTransfer, int consecutivo, FormatoMiscelaneaFiscal formatoMiscelaneaFiscal)  {
		FileTransferMiscelanea fileTransferMiscelanea = null;
		if(formatoMiscelaneaFiscal != null) {
			fileTransferMiscelanea = new FileTransferMiscelanea();
			//Se crea la llave primaria
			FileTransferPK fileTransferPK = new FileTransferPK();
			fileTransferPK.setIdInst(agenteFirmado.getId());
			fileTransferPK.setFolioInst(agenteFirmado.getFolio());
			fileTransferPK.setTipoReg(tipoFileTransfer);
			fileTransferPK.setConsec(new BigDecimal(consecutivo));
			fileTransferMiscelanea.setFileTransferPK(fileTransferPK);
			//Se llena el pojo
			if(StringUtils.isNotBlank(formatoMiscelaneaFiscal.getFechaRegistro())) {				
				fileTransferMiscelanea.setFechaRegistro(
					DateUtil.stringToDate(
						formatoMiscelaneaFiscal.getFechaRegistro(), FileTransferConstantes.FORMATO_FECHA_MISCELANEA_FISCAL));
			}
			if(StringUtils.isNotBlank(formatoMiscelaneaFiscal.getFechaHoraCierreOper())) {				
				fileTransferMiscelanea.setFechaHoraCierreOper(
					DateUtil.stringToDate(
						formatoMiscelaneaFiscal.getFechaHoraCierreOper(), FileTransferConstantes.FORMATO_FECHA_HORA_MISCELANEA_FISCAL));
			}
			fileTransferMiscelanea.setIdTraspasante(formatoMiscelaneaFiscal.getTraspasante().substring(0, 2));
		    fileTransferMiscelanea.setFolioTraspasante(formatoMiscelaneaFiscal.getTraspasante().substring(2, 5));
		    fileTransferMiscelanea.setCuentaTraspasante(formatoMiscelaneaFiscal.getCuentaTraspasante());
		    fileTransferMiscelanea.setIdReceptor(formatoMiscelaneaFiscal.getReceptor().substring(0, 2));
		    fileTransferMiscelanea.setFolioReceptor(formatoMiscelaneaFiscal.getReceptor().substring(2, 5));
		    fileTransferMiscelanea.setCuentaReceptor(formatoMiscelaneaFiscal.getCuentaReceptor());
		    fileTransferMiscelanea.setTv(formatoMiscelaneaFiscal.getTipoValor());
		    fileTransferMiscelanea.setEmisora(formatoMiscelaneaFiscal.getEmisora());
		    fileTransferMiscelanea.setSerie(formatoMiscelaneaFiscal.getSerie());
		    fileTransferMiscelanea.setCupon(formatoMiscelaneaFiscal.getCupon());
		    fileTransferMiscelanea.setIsin(formatoMiscelaneaFiscal.getIsin());
		    fileTransferMiscelanea.setBoveda(formatoMiscelaneaFiscal.getBoveda());
		    fileTransferMiscelanea.setCantidadOperada(formatoMiscelaneaFiscal.getCantidadOperada());
		    fileTransferMiscelanea.setDivisa(formatoMiscelaneaFiscal.getDivisa());
		    if(StringUtils.isNotBlank(formatoMiscelaneaFiscal.getFechaAdquisicion())) {
				fileTransferMiscelanea.setFechaAdquisicion(
					DateUtil.stringToDate(
						formatoMiscelaneaFiscal.getFechaAdquisicion(), FileTransferConstantes.FORMATO_FECHA_MISCELANEA_FISCAL));
		    }
		    fileTransferMiscelanea.setPrecioAdquisicion(formatoMiscelaneaFiscal.getPrecioAdquisicion());
		    fileTransferMiscelanea.setCliente(formatoMiscelaneaFiscal.getCliente());
		    fileTransferMiscelanea.setRfcCurp(formatoMiscelaneaFiscal.getRfcCurp());
		    fileTransferMiscelanea.setExtranjero(FileTransferConstantes.MISCELANEA_FISCAL_EXTRANJERO.equals(formatoMiscelaneaFiscal.getExtranjero()));
		    if(fileTransferMiscelanea.isExtranjero()) {
		    	fileTransferMiscelanea.setRfcCurp(FileTransferConstantes.RFC_EXTRANJERO);
		    }
		    fileTransferMiscelanea.setCostoFiscalActualizado(formatoMiscelaneaFiscal.getCostoFiscalActualizado());
		}
		return fileTransferMiscelanea;
	}
	
	/**
	 * Determina si la operación es compra o venta.
	 * @param fileTransferMiscelanea Objeto para determinar la operación.
	 */
	private void determinarCompraVenta(AgenteVO agenteFirmado, FileTransferMiscelanea fileTransferMiscelanea) {
		String idFolioFirmado = 
			agenteFirmado.getId() + agenteFirmado.getFolio();
		String traspasante = 
			fileTransferMiscelanea.getIdTraspasante() + fileTransferMiscelanea.getFolioTraspasante();
		String receptor = 
			fileTransferMiscelanea.getIdReceptor() + fileTransferMiscelanea.getFolioReceptor();
		//Caso 1: Si el traspasante es igual al agente firmado y distinto al receptor, es una venta.
		if(idFolioFirmado.equals(traspasante) && !idFolioFirmado.equals(receptor)) {
			fileTransferMiscelanea.setCompra(false);
		}
		//Caso 1: Si el receptor es igual al agente firmado y distinto al traspasante, es una compra.
		else if(!idFolioFirmado.equals(traspasante) && idFolioFirmado.equals(receptor)) {
			fileTransferMiscelanea.setCompra(true);
		}
		//Caso 3: Si el traspasante y el receptor es igual al agente firmado, es una venta.
		else if(idFolioFirmado.equals(traspasante) && idFolioFirmado.equals(receptor)) {			
			fileTransferMiscelanea.setCompra(false);
		}
	}
	
	
	/**
	 * @return the dateUtilsDao
	 */
	public DateUtilsDao getDateUtilsDao() {
		return dateUtilsDao;
	}

	/**
	 * @param dateUtilsDao the dateUtilsDao to set
	 */
	public void setDateUtilsDao(DateUtilsDao dateUtilsDao) {
		this.dateUtilsDao = dateUtilsDao;
	}

	/**
	 * @return the fileTransferDao
	 */
	public FileTransferDao getFileTransferDao() {
		return fileTransferDao;
	}

	/**
	 * @param fileTransferDao the fileTransferDao to set
	 */
	public void setFileTransferDao(FileTransferDao fileTransferDao) {
		this.fileTransferDao = fileTransferDao;
	}

	/**
	 * @return the errorResolver
	 */
	public MessageResolver getErrorResolver() {
		return errorResolver;
	}

	/**
	 * @param errorResolver the errorResolver to set
	 */
	public void setErrorResolver(MessageResolver errorResolver) {
		this.errorResolver = errorResolver;
	}

	/**
	 * @return the fileTransferMiscelaneaDao
	 */
	public FileTransferMiscelaneaDao getFileTransferMiscelaneaDao() {
		return fileTransferMiscelaneaDao;
	}

	/**
	 * @param fileTransferMiscelaneaDao the fileTransferMiscelaneaDao to set
	 */
	public void setFileTransferMiscelaneaDao(
			FileTransferMiscelaneaDao fileTransferMiscelaneaDao) {
		this.fileTransferMiscelaneaDao = fileTransferMiscelaneaDao;
	}

	/**
	 * @return the fileUploadService
	 */
	public FileUploadService getFileUploadService() {
		return fileUploadService;
	}

	/**
	 * @param fileUploadService the fileUploadService to set
	 */
	public void setFileUploadService(FileUploadService fileUploadService) {
		this.fileUploadService = fileUploadService;
	}

	/**
	 * @return the validadorMiscelaneaFiscal
	 */
	public ValidadorMiscelaneaFiscal getValidadorMiscelaneaFiscal() {
		return validadorMiscelaneaFiscal;
	}

	/**
	 * @param validadorMiscelaneaFiscal the validadorMiscelaneaFiscal to set
	 */
	public void setValidadorMiscelaneaFiscal(
			ValidadorMiscelaneaFiscal validadorMiscelaneaFiscal) {
		this.validadorMiscelaneaFiscal = validadorMiscelaneaFiscal;
	}

	/**
	 * @return the parserFileTransfer
	 */
	public ParserFileTransfer getParserFileTransfer() {
		return parserFileTransfer;
	}

	/**
	 * @param parserFileTransfer the parserFileTransfer to set
	 */
	public void setParserFileTransfer(ParserFileTransfer parserFileTransfer) {
		this.parserFileTransfer = parserFileTransfer;
	}

	/**
	 * @return the utilService
	 */
	public UtilServices getUtilService() {
		return utilService;
	}

	/**
	 * @param utilService the utilService to set
	 */
	public void setUtilService(UtilServices utilService) {
		this.utilService = utilService;
	}
}

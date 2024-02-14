/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 * <p>
 * Agosto 28, 2008
 */
package com.indeval.portalinternacional.middleware.services.util;

import com.indeval.portalinternacional.middleware.services.util.vo.ProcessInfoVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstatusDivisas;
import com.indeval.portalinternacional.middleware.servicios.modelo.FileTransferDivisas;
import com.indeval.portalinternacional.persistence.dao.FileUploadDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;

/**
 * Implementacion del servicio que opera el mecanismo de lock para la pantalla de
 * trasferencia de archivos
 *
 * @author Esteban Herrera
 */
public class FileUploadMultiDivServiceImpl implements FileUploadMultiDivService {

	/**
	 * Objeto de loggeo
	 */
	private final static Logger log = LoggerFactory.getLogger(FileUploadMultiDivServiceImpl.class);

	/**
	 * Dao FileUploadDao
	 */
	private FileUploadDao fileUploadDao;


	public FileTransferDivisas obtainLock(ProcessInfoVO processInfoVO) {
		log.info("Entrando a FileUploadMultiDivServiceImpl.getLock()");
		FileTransferDivisas ft = parseProcessInfoVO2FileUpload(processInfoVO);
		System.out.println("Identificador de proceso " + ft.getIdFileTransferDivisasInt());
		return fileUploadDao.getLockMulti(ft);
	}


	public ProcessInfoVO getProcessInfo(ProcessInfoVO processInfoVO) {
		log.info("Entrando a FileUploadServiceImpl.getProcessInfo()");
		FileTransferDivisas fileUpload = parseProcessInfoVO2FileUpload(processInfoVO);
		//FileTransferDivisas fileUpload2 = fileUploadDao.getProcessInfoMulti(fileUpload);
		ProcessInfoVO processInfoVO2 = parseFileUpload2ProcessInfoVO(fileUpload);
		return processInfoVO2;
	}


	public Boolean isProcessRunning(ProcessInfoVO processInfoVO) {
		log.info("Entrando a FileUploadServiceImpl.isProcessRunning()");
		FileTransferDivisas fileUpload = parseProcessInfoVO2FileUpload(processInfoVO);
		return fileUploadDao.isProcessRunning(fileUpload);
	}


	public void releaseLock(ProcessInfoVO processInfoVO) {
		log.info("Entrando a FileUploadMultiDivServiceImpl.releaseLock()");
		FileTransferDivisas fileUpload = parseProcessInfoVO2FileUpload(processInfoVO);
		//fileUploadDao.releaseLock(fileUpload);
	}


	public void updateProcessInfo(ProcessInfoVO processInfoVO) {
		log.info("Entrando a FileUploadMultiDivServiceImpl.updateProcessInfo()");
		FileTransferDivisas fileUpload = parseProcessInfoVO2FileUpload(processInfoVO);
		fileUploadDao.updateProcessInfoMulti(fileUpload);
	}

	/**
	 * @param processInfoVO
	 * @return
	 */
	private FileTransferDivisas parseProcessInfoVO2FileUpload(ProcessInfoVO processInfoVO) {
		Calendar cal = Calendar.getInstance();
		log.info("Entrando a FileUploadMultiDivServiceImpl.parseProcessInfoVO2FileUpload()");
		FileTransferDivisas fileUpload = null;

		if (processInfoVO.getIdProceso().equals("-1")) {
			fileUpload = new FileTransferDivisas();
			fileUpload.setIdFileTransferDivisasInt(-1L);
			fileUpload.setEstatusDivisa(0L);
			fileUpload.setFechaRegistro(cal.getTime());
			fileUpload.setFechaAutoriza(cal.getTime());
			fileUpload.setRegistrosTotal(new BigInteger("0"));
			fileUpload.setRegistrosError(new BigInteger("0"));
			fileUpload.setRegistrosExitosos(new BigInteger("0"));
			fileUpload.setUsuarioAutoriza(processInfoVO.getUsuario());
			fileUpload.setUsuarioRegistro(processInfoVO.getUsuario());
			fileUpload.setEstatusDivisas(new EstatusDivisas());

			fileUpload.setPorcentaje(0L);

		} else {

			fileUpload = fileUploadDao.getFileTransferMDById(Long.valueOf(processInfoVO.getIdProceso()));
			fileUpload.setUsuarioRegistro(processInfoVO.getUsuario());
			fileUpload.setEstatusDivisa(Long.valueOf(processInfoVO.getStatus()));

			if (processInfoVO.getPorcentajeTerminado() != null) {
				fileUpload.setPorcentaje(processInfoVO.getPorcentajeTerminado().longValue());
			}

			if (processInfoVO.getTotalRegistros() != null) {
				fileUpload.setRegistrosTotal(new BigInteger(processInfoVO.getTotalRegistros().toString()));
			}
		}

		return fileUpload;
	}

	/**
	 * @param fileUpload
	 * @return
	 */
	private ProcessInfoVO parseFileUpload2ProcessInfoVO(FileTransferDivisas fileUpload) {
		log
				.info("Entrando a FileUploadServiceImpl.parseFileUpload2ProcessInfoVO()");
		if (fileUpload != null) {
			ProcessInfoVO processInfoVO = new ProcessInfoVO();
			processInfoVO.setIdProceso(fileUpload.getIdFileTransferDivisasInt().toString());
			processInfoVO.setAbort('F');
			processInfoVO.setPorcentajeTerminado(new BigDecimal(fileUpload.getPorcentaje().toString()));
			processInfoVO.setStatus(fileUpload.getEstatusDivisa().toString()); //valorar utilizar el id
			processInfoVO.setUsuario(fileUpload.getUsuarioRegistro());
			processInfoVO.setTotalRegistros(fileUpload.getRegistrosTotal().intValue());

			return processInfoVO;
		}
		return null;
	}

	/**
	 * @param fileUploadDao the fileUploadDao to set
	 */
	public void setFileUploadDao(FileUploadDao fileUploadDao) {
		this.fileUploadDao = fileUploadDao;
	}


}

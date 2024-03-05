/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.util;

import com.indeval.portaldali.middleware.services.util.vo.ProcessInfoVO;

/**
 * 
 * @author csanchez
 * 
 */
public interface FileUploadService {

	/**
	 * M&eacute;todo encargado de realizar un candado
	 * 
	 * @param processInfoVO
	 * @return
	 */
	public Boolean getLock(ProcessInfoVO processInfoVO);

	/**
	 * M&eacute;todo encargado de liberar el candado
	 * 
	 * @param processInfoVO
	 */
	public void releaseLock(ProcessInfoVO processInfoVO);

	/**
	 * M&eacute;todo encargado de obtener la informaci&oacute;n del candado
	 * 
	 * @param processInfoVO
	 * @return
	 */
	public ProcessInfoVO getProcessInfo(ProcessInfoVO processInfoVO);

	/**
	 * M&eacute;todo encargado de verificar si existen procesos ejecutandose
	 * 
	 * @param processInfoVO
	 * @return
	 */
	public Boolean isProcessRunning(ProcessInfoVO processInfoVO);

	/**
	 * M&eacute;todo encargado de actulizar el estado del proceso
	 * 
	 * @param processInfoVO
	 */
	public void updateProcessInfo(ProcessInfoVO processInfoVO);
}

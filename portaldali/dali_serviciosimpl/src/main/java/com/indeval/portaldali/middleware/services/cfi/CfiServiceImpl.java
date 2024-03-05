/**
 * CMMV - Portal Dali
 * Copyrigth (c) 2017 CMMV. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.cfi;

import org.apache.commons.lang.StringUtils;

import com.indeval.portaldali.modelo.to.cfi.DetalleCfiTO;
import com.indeval.portaldali.persistence.dao.cfi.CfiDao;

/**
 * Implementacion de la interfaz CfiService
 * 
 * @author Pablo Balderas
 */
public class CfiServiceImpl implements CfiService {

	private final int LONGITUD_CFI = 6;
	private final int POSICION_CATEGORIA = 0;
	private final int POSICION_GRUPO = 1;
	private final int POSICION_ATRIBUTO_UNO = 2;
	private final int POSICION_ATRIBUTO_DOS = 3;
	private final int POSICION_ATRIBUTO_TRES = 4;
	private final int POSICION_ATRIBUTO_CUATRO = 5;
	
	/** Dao para consultas de CFI */
	private CfiDao cfiDao;
	
	/* (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.cfi.CfiService#findDetalleCfi(java.lang.String)
	 */
	public DetalleCfiTO findDetalleCfi(String cfi) {
		DetalleCfiTO detalleCfiTO = null;
		if(StringUtils.isNotBlank(cfi) && LONGITUD_CFI == cfi.length()) {
			detalleCfiTO = 
				cfiDao.findDetalleCfi(
					String.valueOf(cfi.charAt(POSICION_CATEGORIA)), 
					String.valueOf(cfi.charAt(POSICION_GRUPO)), 
					String.valueOf(cfi.charAt(POSICION_ATRIBUTO_UNO)), 
					String.valueOf(cfi.charAt(POSICION_ATRIBUTO_DOS)), 
					String.valueOf(cfi.charAt(POSICION_ATRIBUTO_TRES)), 
					String.valueOf(cfi.charAt(POSICION_ATRIBUTO_CUATRO))
				);
		}
		return detalleCfiTO;
	}

	/**
	 * Método para establecer el atributo cfiDao
	 * @param cfiDao El valor del atributo cfiDao a establecer.
	 */
	public void setCfiDao(CfiDao cfiDao) {
		this.cfiDao = cfiDao;
	}

}

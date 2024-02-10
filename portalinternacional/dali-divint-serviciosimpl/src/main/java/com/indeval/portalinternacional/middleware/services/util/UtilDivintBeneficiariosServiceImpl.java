package com.indeval.portalinternacional.middleware.services.util;

import java.util.Date;
import java.util.List;

import com.indeval.portalinternacional.persistence.dao.FileTransferCapturaBenefDao;
import com.indeval.portaldali.middleware.services.util.DateUtilService;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoFormatoBeneficiario;

public class UtilDivintBeneficiariosServiceImpl implements UtilDivintBeneficiariosService{
	
	/** servicio para el manejo de los datos correspondientes al fileTransfer beneficiarios*/
	private FileTransferCapturaBenefDao fileTransferCapturaBenefDao;
	

	/** servicio para obtener la fecha actual*/
	private DateUtilService dateUtilService;
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.util.UtilDivintBeneficiariosService#obtieneCatalogoTipoOperacionMulticarga()
	 */
	public List<TipoFormatoBeneficiario> obtieneCatalogoTipoOperacionMulticarga() throws BusinessException{
		return fileTransferCapturaBenefDao.getFormatoBeneficiario();
	}
	
	/*
	 *  (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.util.UtilDivintBeneficiariosService#obtieneFechaActual()
	 */
	public Date obtieneFechaActual()throws BusinessException{
		return dateUtilService.getCurrentDate();
	}
	
	// se inyecta la dependencia
	public void setFileTransferCapturaBenefDao(
			FileTransferCapturaBenefDao fileTransferCapturaBenefDao) {
		this.fileTransferCapturaBenefDao = fileTransferCapturaBenefDao;
	}

	public void setDateUtilService(DateUtilService dateUtilService) {
		this.dateUtilService = dateUtilService;
	}
	
}

package com.indeval.portalinternacional.middleware.services.util;

import java.util.Date;
import java.util.List;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoFormatoBeneficiario;

public interface UtilDivintBeneficiariosService {
	
	/**
	 * Servicio que obtiene una lista con los tipos de formatos de los beneficiarios que se pueden cargar por fileTransfer
	 * @return List<TipoFormatoBeneficiario>
	 * @throws BusinessException 
	 * */
	public List<TipoFormatoBeneficiario> obtieneCatalogoTipoOperacionMulticarga() throws BusinessException;
	
	/**
	 * Servicio para obtener la fecha Actual
	 * @return Date
	 * @throws BusinessException
	 * */
	Date obtieneFechaActual()throws BusinessException;

}

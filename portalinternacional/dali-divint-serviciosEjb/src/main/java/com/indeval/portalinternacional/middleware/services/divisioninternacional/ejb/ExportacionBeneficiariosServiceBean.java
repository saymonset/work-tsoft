/**
 * Portal Internacional
 * Copyrigth (c) 2022 Indeval. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional.ejb;

import java.sql.Date;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;

import com.indeval.portalinternacional.middleware.services.SpringBeanAutowiringInterceptorDivInt;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.beneficiarios.ExportacionBeneficiariosService;

/**
 * EJB que funciona como proxy para acceder al servicio de exportacion de beneficiarios
 * 
 * @author Pablo Balderas
 */
@Stateless(name = "ejb.exportacionBeneficiariosService", mappedName = "ejb.exportacionBeneficiariosService")
@Interceptors(SpringBeanAutowiringInterceptorDivInt.class)
@Remote(ExportacionBeneficiariosService.class)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ExportacionBeneficiariosServiceBean implements ExportacionBeneficiariosService {

	/** Servicio de negocio para realizar las exportaciones de beneficiarios */
	@Autowired
	private ExportacionBeneficiariosService exportacionBeneficiariosService;
	
	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.beneficiarios.ExportacionBeneficiariosService#obtenerRegistrosLayout(java.lang.String, java.sql.Date, java.sql.Date)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<String> obtenerRegistrosLayauts(String formato, Date fechaInicio, Date fechaFin) {
		return exportacionBeneficiariosService.obtenerRegistrosLayauts(formato, fechaInicio, fechaFin);
	}

}

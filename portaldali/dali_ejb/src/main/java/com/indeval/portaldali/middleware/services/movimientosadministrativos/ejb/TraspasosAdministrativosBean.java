/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 * 
 * May 14, 2008
 */
package com.indeval.portaldali.middleware.services.movimientosadministrativos.ejb;

import java.math.BigInteger;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import com.indeval.portaldali.middleware.services.SpringBeanAutowiringInterceptorDali;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.movimientosadministrativos.TraspasosAdministrativosParams;
import com.indeval.portaldali.middleware.services.movimientosadministrativos.TraspasosAdministrativosService;

/**
 * Enterprise Java Bean para exponer el servicio de negocio de Traspasos Administrativos
 * 
 * @author Erik Vera Montoya
 * 
 * @version 1.0
 */
@Stateless(name = "ejb.traspasosAdministrativos", mappedName = "ejb.traspasosAdministrativos")
@Interceptors(SpringBeanAutowiringInterceptorDali.class)
@Remote(TraspasosAdministrativosService.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class TraspasosAdministrativosBean implements TraspasosAdministrativosService {
    @Autowired
    private TraspasosAdministrativosService traspasosAdministrativosService;

    /*
     * (non-Javadoc)
     * 
     * @see com.indeval.portaldali.middleware.services.movimientosadministrativos.TraspasosAdministrativosService#businessRulesTraspasosAdministrativos(com.indeval.portaldali.middleware.services.movimientosadministrativos.TraspasosAdministrativosParams)
     */
    public BigInteger businessRulesTraspasosAdministrativos(TraspasosAdministrativosParams params) throws BusinessException {
        return traspasosAdministrativosService.businessRulesTraspasosAdministrativos(params);
    }

    /**
     * Obtiene el valor del campo traspasosAdministrativosService
     * 
     * @return el valor de traspasosAdministrativosService
     */
    public TraspasosAdministrativosService getTraspasosAdministrativosService() {
        return traspasosAdministrativosService;
    }

    /**
     * Asigna el campo traspasosAdministrativosService
     * 
     * @param traspasosAdministrativosService el valor de traspasosAdministrativosService a asignar
     */
    public void setTraspasosAdministrativosService(TraspasosAdministrativosService traspasosAdministrativosService) {
        this.traspasosAdministrativosService = traspasosAdministrativosService;
    }

}

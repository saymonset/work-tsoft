/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.util.ConvertBO2VO;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;
import com.indeval.portaldali.persistence.dao.common.CuentaNombradaDao;
import com.indeval.portaldali.persistence.modelo.CuentaNombrada;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.SicEmision;
import com.indeval.portalinternacional.persistence.dao.SicEmisionDao;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 * @author javiles
 */
public class UtilDivIntServiceImpl implements UtilDivIntService, Constantes {

    /** Para el registro de log de esta clase */
	private final Logger log = LoggerFactory.getLogger(this.getClass());
    
//    /** Bean de acceso a ValidatorDivInt */
//    private ValidatorService validatorService = null;
//    
//    /** Bean de acceso a CuentaNombradaDao */
    private CuentaNombradaDao cuentaNombradaDao;
    
    /** Bean de acceso a SicEmisionDao */
    private SicEmisionDao sicEmisionDao = null;
    
    /**
     *
     * @see com.indeval.portalinternacional.middleware.services.util.UtilDivIntService#obtieneCuentaReceptora(CuentaNombrada)
     */
    public String obtieneCuentaReceptora(CuentaNombrada cuentaNombrada) throws BusinessException {
        
        log.info("Entrando a obtieneCuentaReceptora()");
        
        String cuentaRecep = "";
        
        if (cuentaNombrada != null 
                && cuentaNombrada.getInstitucion() != null 
                && cuentaNombrada.getInstitucion().getTipoInstitucion() != null) {
            cuentaRecep = this.obtieneCuentaReceptora(ConvertBO2VO.crearAgenteVO(cuentaNombrada));
        }
        
        return cuentaRecep;
        
    }
    
    /**
     * @see com.indeval.portalinternacional.middleware.services.util.UtilDivIntService#obtieneCuentaReceptora(com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO)
     */
    public String obtieneCuentaReceptora(AgenteVO agenteVO) throws BusinessException {
        
        log.info("Entrando a obtieneCuentaReceptora()");
        
        String cuentaRecep = "";
        
        if (agenteVO != null 
                && StringUtils.isNotBlank(agenteVO.getId()) 
                && StringUtils.isNotBlank(agenteVO.getFolio())
                && StringUtils.isNotBlank(agenteVO.getCuenta())) {
            CuentaNombrada cuentaNombrada2 = cuentaNombradaDao.findCuenta(agenteVO);
            if (cuentaNombrada2 != null && cuentaNombrada2.getTipoCuenta() != null) {
                if (StringUtils.isNotBlank(cuentaNombrada2.getTipoCuenta().getClaveTipoCuenta())) {
                    if (cuentaNombrada2.getTipoCuenta().getClaveTipoCuenta().indexOf("PROP") >= 0) {
                        cuentaRecep = "0031";
                    }
                    if (cuentaNombrada2.getTipoCuenta().getClaveTipoCuenta().indexOf("TERC") >= 0) {
                        cuentaRecep = "0030";
                    }
                }
            }
        }
        
        return cuentaRecep;
        
    }
    
    /**
     * @see com.indeval.portalinternacional.middleware.services.util.UtilDivIntService#obtieneAgentesSIC(com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO)
     */
    public AgenteVO[] obtieneAgentesSIC(EmisionVO emisionVO){
    	
    	log.info("Entrando a UtilDivIntServiceImpl.obtieneAgentesSIC()");
    	
    	AgenteVO[] arregloAgenteSic = null;
    	if(emisionVO != null){
    		
    		/* Recupera el objeto SICEmision  que corresponde a la emision recibida */
    		List<SicEmision> listaSicEmision =  sicEmisionDao.findSicEmisionesByEmision(emisionVO);
        	
        	/* Se verifica que el objeto SICEmision no sea NULL */
        	if ((listaSicEmision != null) && (!listaSicEmision.isEmpty())) {
        		
        		/* Recupera las CuentaNombrada de la lista de SICEmision y se construye una lista de AgenteVO */
        		List<AgenteVO> listaAgenteVO = new ArrayList<AgenteVO>();
    			for (Iterator<SicEmision> iterator = listaSicEmision.iterator(); iterator.hasNext();) {
    				SicEmision sicEmision = iterator.next();
    				if(sicEmision != null) {
    					listaAgenteVO.add(ConvertBO2VO.crearAgenteVO(sicEmision.getCuentaNombrada()));
    				}
    			}
    			arregloAgenteSic = new AgenteVO[listaAgenteVO.size()];
    			arregloAgenteSic = (AgenteVO[]) listaAgenteVO.toArray(arregloAgenteSic);
        	} 
    	}
    	return (arregloAgenteSic);
    }
    
    
    /**
     * @see com.indeval.portalinternacional.middleware.services.util.UtilDivIntService#obtieneAgentesSIC(com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO)
     */
    public Long[] obtieneCatBics(EmisionVO emisionVO){
    	
    	log.info("Entrando a UtilDivIntServiceImpl.obtieneAgentesSIC()");
    	
    	Long[] arregloAgenteSic = null;
    	if(emisionVO != null){
    		
    		/* Recupera el objeto SICEmision  que corresponde a la emision recibida */
    		List<SicEmision> listaSicEmision =  sicEmisionDao.findSicEmisionesByEmision(emisionVO);
        	
        	/* Se verifica que el objeto SICEmision no sea NULL */
        	if ((listaSicEmision != null) && (!listaSicEmision.isEmpty())) {
        		
        		/* Recupera las CuentaNombrada de la lista de SICEmision y se construye una lista de AgenteVO */
        		List<Long> listaAgenteVO = new ArrayList<Long>();
    			for (Iterator<SicEmision> iterator = listaSicEmision.iterator(); iterator.hasNext();) {
    				SicEmision sicEmision = iterator.next();
    				if(sicEmision != null && sicEmision.getCatBic() != null && sicEmision.getCatBic().getIdCatbic() != null) {
    					listaAgenteVO.add(sicEmision.getCatBic().getIdCatbic());
    				}
    			}
    			arregloAgenteSic = new Long[listaAgenteVO.size()];
    			arregloAgenteSic = (Long[]) listaAgenteVO.toArray(arregloAgenteSic);
        	} 
    	}
    	return (arregloAgenteSic);
    }

    /* Setters */

    /**
     * @param cuentaNombradaDao the cuentaNombradaDao to set
     */
//    public void setCuentaNombradaDao(CuentaNombradaDao cuentaNombradaDao) {
//        this.cuentaNombradaDao = cuentaNombradaDao;
//    }
    
    /**
	 * @param sicEmisionDao the sicEmisionDao to set
	 */
	public void setSicEmisionDao(SicEmisionDao sicEmisionDao) {
		this.sicEmisionDao = sicEmisionDao;
	}

    /**
     * @param cuentaNombradaDao the cuentaNombradaDao to set
     */
    public void setCuentaNombradaDao(CuentaNombradaDao cuentaNombradaDao) {
        this.cuentaNombradaDao = cuentaNombradaDao;
    }

	public Long getCuentaNombradaOfCustodio(Long idEmision) {
		Long idCuentaNombrada = null;
		SicEmision sicEmision = sicEmisionDao.findSicEmisionByIdEmision(idEmision);
		
		if(sicEmision != null && sicEmision.getCuentaNombrada() != null){
			idCuentaNombrada = sicEmision.getCuentaNombrada().getIdCuentaNombrada();
		}
		
		return idCuentaNombrada;
	}
	
    /**
     * @param validatorService the validatorService to set
     */
//    public void setValidatorService(ValidatorService validatorService) {
//        this.validatorService = validatorService;
//    }
    
}

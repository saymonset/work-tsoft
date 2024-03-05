/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exception.enviooperaciones.enviooperaciones;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.persistence.dao.common.DateUtilsDao;
import com.indeval.protocolofinanciero.api.vo.RetiroEfectivoVO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class ITestGrabaOperacionRetiroEfectivo extends BaseITestService {

	/**
	 * Objeto de loggeo
	 */
	private static final Log log =	LogFactory.getLog(ITestGrabaOperacionRetiroEfectivo.class);
	
	 /** Bean del servicio a ser probado */
    private EnvioOperacionesService envioOperacionesService;
    
    /** Bean de acceso a DateUtilsDao */
    private DateUtilsDao dateUtilsDao;
    
    
    /**
     * En este método se inicializan las propiedades que serán utilizadas
     * durante la clase de prueba.
     * 
     * @throws Exception
     */
    protected void onSetUp() throws Exception {

        super.onSetUp();

        if (envioOperacionesService == null) {

            envioOperacionesService = (EnvioOperacionesService) applicationContext
                    .getBean("envioOperacionesService");
            
            dateUtilsDao = (DateUtilsDao) applicationContext.getBean("dateUtilsDao");

        }
    }

    
    /**
     * @throws Exception
     */
    public void testGrabaOperacion() throws Exception {
    	
    	log.debug("Entrando a ITestGrabaOperacionRetiroEfectivo.testGrabaOperacion() ");
    	
    	RetiroEfectivoVO retiroVO = new RetiroEfectivoVO();
    	retiroVO.setReferenciaMensaje("1");
    	retiroVO.setReferenciaOperacion("2");
    	retiroVO.setFechaLiquidacion(new Date());
    	retiroVO.setMonto(new BigDecimal(1000));
    	retiroVO.setBeneficiario("01003");
    	retiroVO.setCuentaBeneficiaria("SPEI"); //SPEI o SIAC
    	retiroVO.setFechaRegistro(new Date());
    	
    	String folioControl = "123";
    	boolean isCompra = false;
    	
    	try{
    		//TODO
//    		this.envioOperacionesService.grabaOperacion(retiroVO, folioControl, isCompra);	
    	}
		catch(BusinessException e){
			e.printStackTrace();
		}
	
    }
    
    
	
}

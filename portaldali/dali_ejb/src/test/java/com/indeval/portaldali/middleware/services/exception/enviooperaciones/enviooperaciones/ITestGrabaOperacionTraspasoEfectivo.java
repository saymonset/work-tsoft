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
import com.indeval.portaldali.persistence.dao.common.DateUtilsDao;
import com.indeval.protocolofinanciero.api.vo.TraspasoEfectivoVO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class ITestGrabaOperacionTraspasoEfectivo extends BaseITestService {

	/**
	 * Objeto de loggeo
	 */
	private static final Logger logger = LoggerFactory.getLogger(ITestGrabaOperacionTraspasoEfectivo.class);

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
            envioOperacionesService = 
                (EnvioOperacionesService) applicationContext.getBean("envioOperacionesService");
        }
        if (dateUtilsDao == null) {
            dateUtilsDao = (DateUtilsDao) applicationContext.getBean("dateUtilsDao");
        }
        
	}

	/**
	 * Metodo de prueba para grabaOperacionTraspasoEfectivo
	 * @throws Exception
	 */
	public void testGrabaOperacionTraspasoEfectivo() throws Exception {
		
		log.debug("Entrando a ITestGrabaOperacionTraspasoEfectivo" +
				".testGrabaOperacionTraspasoEfectivo() ");
		
		TraspasoEfectivoVO traspasoVO = new TraspasoEfectivoVO();

		traspasoVO.setFechaLiquidacion(new Date());
		traspasoVO.setReferenciaMensaje("1");
		traspasoVO.setReferenciaOperacion("2");
		traspasoVO.setMonto(new BigDecimal(123456));
		traspasoVO.setOrdenante("01001");
		traspasoVO.setCuentaOrdenante("1234");
		traspasoVO.setBeneficiario("02002");
		traspasoVO.setCuentaBeneficiaria("4321");
		traspasoVO.setFechaRegistro(new Date());
//		traspasoVO.set
		
		String folioControl = "12345";
		boolean isCompra = true;
		
		//TODO
//		 envioOperacionesService.grabaOperacion(traspasoVO, folioControl, isCompra);

		
	}

}
/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.dali.ingresos;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.persistence.dao.ingresos.IngresosDao;
import com.indeval.portaldali.persistence.model.ConsultaIngresos;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class ITestIngresos_3 extends BaseDaoTestCase {
	
	/** Objeto de loggeo */
    private static final Logger logger = LoggerFactory.getLogger(ITestIngresos_3.class);

    
    private IngresosDao ingresosDao;
	
    /**
     * @see com.indeval.persistence.portallegado.unittest.BaseDaoTestCase#onSetUp()
     */
    protected void onSetUp() {
        super.onSetUp();
        ingresosDao = (IngresosDao) getBean("ingresosDao");
    }
	
	/**
	 * TestCase para probar exitosamente esInhabil()
	 * @throws Exception
	 */
	public void testGetConsulta() throws Exception {
		ConsultaIngresos consultaIngresos = ingresosDao.getConsultaIngresosPorNombre("CONSULTA_ESTADO_CUENTA_VALORES");
		
		assertNotNull(consultaIngresos);
		
		log.info("Objeto encontrado: [" + ToStringBuilder.reflectionToString(consultaIngresos,ToStringStyle.MULTI_LINE_STYLE) + "]");
	}

}

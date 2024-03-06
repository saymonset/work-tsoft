/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.dali.cuentanombradadao;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.persistence.dao.common.CuentaNombradaDaliDao;
import com.indeval.portaldali.persistence.model.CuentaNombrada;
import com.indeval.portaldali.persistence.vo.AgentePK;
import com.indeval.portaldali.persistence.vo.PageVO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class ITestGetCCuentaNombrada_1 extends BaseDaoTestCase {
	
	/** Objeto de loggeo */
    private static final Logger logger = LoggerFactory.getLogger(ITestGetCCuentaNombrada_1.class);

    /** Bean para cuentaNombradaDao */
    private CuentaNombradaDaliDao cCuentaNombradaDao;
	
    /**
     * @see com.indeval.persistence.portallegado.unittest.BaseDaoTestCase#onSetUp()
     */
    protected void onSetUp() {
        super.onSetUp();
        cCuentaNombradaDao = (CuentaNombradaDaliDao) getBean("cCuentaNombradaDao");
    }
	
	/**
	 * TestCase para probar exitosamente esInhabil()
	 * @throws Exception
	 */
	public void testGetCCuentaNombrada_1() throws Exception {
		
		log.info("Entrando a ITestEsInhabil_1.testEsInhabil()");
		
		assertNotNull(cCuentaNombradaDao);
        
        AgentePK agentePK = new AgentePK();
        agentePK.setIdInst("02");
        agentePK.setFolioInst("013");
        
        PageVO pageVO = cCuentaNombradaDao.getCuentaNombrada(agentePK, new PageVO());
        assertNotNull(pageVO);
        assertNotNull(pageVO.getRegistros());
        assertTrue(!pageVO.getRegistros().isEmpty());
        
        log.debug("Registros en la pagina : [" + pageVO.getRegistros().size() + "]");
        
        /* Se imprime el primer elemento de la lista obtenida */
        CuentaNombrada cCuentaNombrada = (CuentaNombrada) pageVO.getRegistros().get(0);
        
        log.debug("CuentaNombrada ["+ ReflectionToStringBuilder.reflectionToString(
                cCuentaNombrada) + "]");
        log.debug("Institucion ["+ ReflectionToStringBuilder.reflectionToString(
                cCuentaNombrada.getInstitucion()) + "]");
		
	}

}

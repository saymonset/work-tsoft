/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.dali.common;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.persistence.dao.common.MensajePortalDaliDAO;
import com.indeval.portaldali.persistence.model.MensajePortal;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class ITestMensajePortal_2 extends BaseDaoTestCase {
	
	/** Objeto de loggeo  */
    private static final Logger log = LoggerFactory.getLogger(ITestMensajePortal_2.class);

    /**
     * bean de mensajePortalDao
     */
	private MensajePortalDaliDAO mensajePortalDao;
	
    /**
     * @see com.indeval.persistence.unittest.BaseDaoTestCase#onSetUp()
     */
    protected void onSetUp() {
        super.onSetUp();
        mensajePortalDao = (MensajePortalDaliDAO) getBean("mensajePortalDaliDao");
    }
    
    public void testAcutalizaMensaje() {
    	assertNotNull(mensajePortalDao);
    	
    	MensajePortal mensaje = mensajePortalDao.getMensajePortal();
    	
    	log.info("Mensaje obtenido: [" + ToStringBuilder.reflectionToString(mensaje,ToStringStyle.MULTI_LINE_STYLE) + "]");
    	
    	mensaje.setMensaje("Hola Mundo");
    	mensaje.setHabilitado(true);
    	mensaje.setIdMensajePortal(3);
    	
    	mensajePortalDao.actualizaMensaje(mensaje);
    	
    }
}

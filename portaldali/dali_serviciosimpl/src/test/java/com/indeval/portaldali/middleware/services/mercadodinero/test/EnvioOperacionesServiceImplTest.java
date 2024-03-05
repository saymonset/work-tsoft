/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * EnvioOperacionesServiceImplTest.java
 * 31/03/2008
 */
package com.indeval.portaldali.middleware.services.mercadodinero.test;

import java.math.BigInteger;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import com.indeval.portaldali.middleware.services.BaseTestCase;
import com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;

/**
 * Clase de prueba unitaria para verificar la integracicn del servicio
 * de envo de operaciones
 * @author Emigdio Hernández
 *
 */
public class EnvioOperacionesServiceImplTest extends
		BaseTestCase {

	public void testConfirmaMatch(){
		EnvioOperacionesService service = (EnvioOperacionesService)applicationContext.getBean("envioOperacionesService");
		AgenteVO agenteFirmado = new AgenteVO();
		agenteFirmado.setId("01");
		agenteFirmado.setFolio("003");
		
		try {
			//service.confirmaOperacionMatch( new BigInteger("70209"), agenteFirmado,null,null,null);
		} catch (BusinessException e) {
			
			e.printStackTrace();
			assertNull(e);
		}
		
	}
	
	public void testEjb(){
		
//		java.naming.factory.initial = weblogic.jndi.WLInitialContextFactory
//		java.naming.provider.url = t3://10.100.192.63:6003, t3://10.100.192.63:6005
//		jndiName = CaptchaServiceHome#com.bursatec.seguridad.middleware.ejb.CaptchaService
    			
		Properties p = new Properties();
		
		p.setProperty("java.naming.factory.initial", "weblogic.jndi.WLInitialContextFactory");
		//p.setProperty("java.naming.provider.url", "t3://10.100.192.63:6005");
		p.setProperty("java.naming.provider.url", "t3://IND7D563.BMVCORP.COM.MX:7001");
		
		try{
			Context ctx = new InitialContext(p);

			Object EBJObject = ctx.lookup("ejb.envioOperaciones#com.indeval.portallegado.middleware.services.enviooperaciones.EnvioOperacionesService");

			EnvioOperacionesService service = (EnvioOperacionesService) EBJObject;
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	protected String[] getConfigLocations() {
        return new String[] { "classpath:com/indeval/portaldali/conf/applicationContext-test.xml",
                "classpath:com/indeval/portaldali/conf/applicationContext.xml",
                "classpath:com/indeval/portaldali/conf/applicationContext-portallegado.xml",
                "persistence-dali-dao-context.xml",
                "iTestApplicationContext.xml"};
    }
}

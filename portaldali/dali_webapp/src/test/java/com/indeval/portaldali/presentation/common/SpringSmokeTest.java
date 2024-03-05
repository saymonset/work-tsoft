/**
 * 2H Software Bursatec Indeval
 */
package com.indeval.portaldali.presentation.common;

/**
 * @author emigdio
 *
 */
public class SpringSmokeTest extends BaseWebTestCase {

	@Override
	public void testDummy(){
		
		System.out.println("OK");
	}
	
	protected String[] getConfigLocations() {
        return new String[] { 
        		"classpath:com/indeval/portaldali/conf/applicationContext-test.xml",
        		"classpath:com/indeval/portaldali/conf/estatus/applicationContext-operaciones.xml",
        		"classpath:com/indeval/portaldali/conf/applicationContext-portallegado.xml",
        		"classpath:com/indeval/portaldali/conf/common/applicationContext-catalogos.xml",
        		"classpath:com/indeval/portaldali/conf/applicationContext-portallegado-dao-dali.xml",
        		"classpath:com/indeval/portaldali/conf/estadocuenta/applicationContext-movimientos-efectivo.xml",
        		"classpath:com/indeval/portaldali/conf/estadocuenta/applicationContext-posicion.xml",
        		"classpath:com/indeval/portaldali/conf/estadocuenta/applicationContext-saldos.xml",
        		"classpath:com/indeval/portaldali/conf/estadocuenta/applicationContext-estadocuenta-posicion.xml",
        		"classpath:com/indeval/portaldali/conf/estadocuenta/applicationContext-estadocuenta-saldo.xml"
        		 
        		 
                };

    }
	
}

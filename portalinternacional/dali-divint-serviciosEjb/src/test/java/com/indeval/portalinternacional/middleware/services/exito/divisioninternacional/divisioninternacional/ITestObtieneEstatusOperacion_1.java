/**
 * 
 */
package com.indeval.portalinternacional.middleware.services.exito.divisioninternacional.divisioninternacional;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portalinternacional.middleware.services.BaseITestService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstatusOperacion;

/**
 * @author javiles
 *
 */
public class ITestObtieneEstatusOperacion_1 extends BaseITestService {

    /** Objeto de loggeo */
	private final Logger log = LoggerFactory.getLogger(ITestObtieneEstatusOperacion_1.class);
    
    /** Servicio que sera probado en esta prueba */
    private DivisionInternacionalService divisionInternacionalService;

    /**
     * @see com.indeval.portalinternacional.portalinternacional.services.BaseITestService#onSetUp()
     */
    protected void onSetUp() throws Exception {
        super.onSetUp();
        if (divisionInternacionalService == null) {
            divisionInternacionalService = (DivisionInternacionalService) applicationContext
                    .getBean("divisionInternacionalService");
        }
    }
    
    public void testObtieneEstatusOperacion() {
        
        log.info("Ejecutando prueba testObtieneEstatusOperacion()");
        
        assertNotNull(divisionInternacionalService);
        
        EstatusOperacion[] estatusOperaciones = divisionInternacionalService.obtieneEstatusOperacion();
        assertNotNull("No existe el arreglo de registros", estatusOperaciones);
        assertTrue("No existen registros en el arreglo", estatusOperaciones.length > 0);
        
        for (int i = 0; i < estatusOperaciones.length; i++) {
            log.debug("Registro [" + ReflectionToStringBuilder.toString(estatusOperaciones[i]) + "]");
        }
        
    }
    
}

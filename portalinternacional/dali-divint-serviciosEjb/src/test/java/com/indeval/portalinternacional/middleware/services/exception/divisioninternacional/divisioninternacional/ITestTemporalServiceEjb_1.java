/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.exception.divisioninternacional.divisioninternacional;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.CuentaNombrada;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portaldali.persistence.modelo.TipoInstitucion;
import com.indeval.portalinternacional.middleware.services.BaseITestService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.TemporalBeneficiariosService;
import com.indeval.portalinternacional.middleware.servicios.modelo.OperacionSic;

/**
 * @author javiles
 *
 */
@SuppressWarnings({"unchecked"})
public class ITestTemporalServiceEjb_1 extends BaseITestService {

    /** Servicio de log */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

    /** Servicio que sera probado en esta prueba */
    private TemporalBeneficiariosService temporalBeneficiariosService;
    

    /**
     * @see com.indeval.portalinternacional.portalinternacional.services.BaseITestService#onSetUp()
     */
    protected void onSetUp() throws Exception {
    	System.setProperty("ejb", "true");
    	
        super.onSetUp();
        if (temporalBeneficiariosService == null) {
        	temporalBeneficiariosService = (TemporalBeneficiariosService) applicationContext
                    .getBean("temporalService");
        }
    }
    
    
    @Test
    public void testMigrarInfo(){    	
        log.info("Ejecutando copiaNombreRazonSocial()");
        
        temporalBeneficiariosService.copiaNombreRazonSocial();

        
    }
    
}

/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.exception.divisioninternacional.divisioninternacional;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.CuentaNombrada;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portaldali.persistence.modelo.TipoInstitucion;
import com.indeval.portalinternacional.middleware.services.BaseITestService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService;
import com.indeval.portalinternacional.middleware.servicios.modelo.OperacionSic;

/**
 * @author javiles
 *
 */
@SuppressWarnings({"unchecked"})
public class ITestConsultaOperaciones_1 extends BaseITestService {

    /** Servicio de log */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

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
    
    public void testConsultaOperaciones(){
        
        log.info("Ejecutando testConsultaOperaciones()");

        assertNotNull(divisionInternacionalService);
        
        OperacionSic operacionSic = new OperacionSic();
        operacionSic.setCuentaNombrada(new CuentaNombrada());
        operacionSic.getCuentaNombrada().setInstitucion(new Institucion());
        operacionSic.getCuentaNombrada().getInstitucion().setTipoInstitucion(new TipoInstitucion());
        operacionSic.getCuentaNombrada().getInstitucion().getTipoInstitucion().setClaveTipoInstitucion("01");
        operacionSic.getCuentaNombrada().getInstitucion().setFolioInstitucion("003");
        Calendar cal = Calendar.getInstance();
        cal.set(2008, Calendar.NOVEMBER, 04, 0, 0, 0);
        Date[] fechaConsulta = new Date[2];
        fechaConsulta[0] = cal.getTime();
        cal.set(2008, Calendar.NOVEMBER, 05, 23, 59, 59);
        fechaConsulta[1] = cal.getTime();
        operacionSic.setFechaConsulta(fechaConsulta);
        operacionSic.setFolioControl(BigInteger.valueOf(72049));
//        operacionSic.setImporte(new BigDecimal("654.32"));
//        operacionSic.setTipoOperacion("E");
//        operacionSic.setTipoTraspaso("TCP");
        PaginaVO paginaVO = divisionInternacionalService.consultaOperaciones(operacionSic, null, false, true, false);
        
        assertNotNull("PaginaVO null", paginaVO);
        assertNotNull("TotalRegistros null", paginaVO.getTotalRegistros());
        assertNotNull("Registros null", paginaVO.getRegistros());
        assertTrue("No hay registros", !paginaVO.getRegistros().isEmpty());
        
        for (Iterator iterator = paginaVO.getRegistros().iterator(); iterator.hasNext();) {
            OperacionSic operSic = (OperacionSic) iterator.next();
            log.debug("OPERACIONES["+ReflectionToStringBuilder.toString(operSic)+"]");
        }
        
    }
    
}

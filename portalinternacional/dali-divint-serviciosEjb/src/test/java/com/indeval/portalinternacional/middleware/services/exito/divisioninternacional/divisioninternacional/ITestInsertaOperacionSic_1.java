/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.exito.divisioninternacional.divisioninternacional;

import java.math.BigInteger;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;
import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portaldali.persistence.modelo.CuentaNombrada;
import com.indeval.portaldali.persistence.modelo.Emision;
import com.indeval.portalinternacional.middleware.services.BaseITestService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService;
import com.indeval.portalinternacional.middleware.servicios.modelo.CatBic;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstatusOperacion;
import com.indeval.portalinternacional.middleware.servicios.modelo.OperacionSic;
import com.indeval.portalinternacional.middleware.servicios.modelo.SicDetalle;

/**
 * @author javiles
 *
 */
public class ITestInsertaOperacionSic_1  extends BaseITestService  {

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
    
    /**
     * TestCase para DivisionInternacionalService.insertaOperacionSIC()
     */
    public void testInsertaOperacionSic() {
        
        log.info("Entrando a ITestInsertaOperacionSic_1.testInsertaOperacionSic()");
        
        assertNotNull(divisionInternacionalService);
        
        OperacionSic operacionSic = new OperacionSic();
        
        operacionSic.setCuentaNombrada(new CuentaNombrada(new AgenteVO("01", "003", "0307")));
        operacionSic.getCuentaNombrada().setIdCuentaNombrada(83l);
        
        operacionSic.setEmision(new Emision(new EmisionVO("1", "ALFA", "A", "0022")));
        operacionSic.getEmision().setIdEmision(1130l); 
        
        operacionSic.setBoveda(new Boveda());
        operacionSic.getBoveda().setIdBoveda(1l);
        
        operacionSic.setCantidadTitulos(BigInteger.valueOf(123000));
        
        operacionSic.setCatBic(new CatBic());
        operacionSic.getCatBic().setIdCatbic(1l);
        
        operacionSic.setCuentaContraparte("123465");
        operacionSic.setCuentaRecep("0030");
        operacionSic.setDescContraparte("contraparte");
        operacionSic.setDivisa("USD"); 
        
        operacionSic.setEstatusOperacion(new EstatusOperacion());
        operacionSic.getEstatusOperacion().setIdEstatusOperacion(2l);
        
        operacionSic.setFechaNotificacion(new Date());
        operacionSic.setFechaLiquidacion(new Date());
        operacionSic.setFechaOperacion(new Date());
        operacionSic.setNomCtaBenef("cta01");
        operacionSic.setNumCtaBenef("852741");
        
        operacionSic.setSicDetalle(new SicDetalle());
        operacionSic.getSicDetalle().setIdSicDetalle(1l);
        
        operacionSic.setTipoOperacion("TLP");
        operacionSic.setTipoTraspaso("R");
        divisionInternacionalService.insertaOperacionSIC(operacionSic);
        
        log.debug("Se grabo el registro exitosamente");
        
    }
    
}

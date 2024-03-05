/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exito.tesoreria.tesoreria;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService;

/**
 * Clase de prueba para el servicio que abstrae las reglas de negocio para el
 * servicio de retiro de fondos.
 * 
 * @author salvador
 * 
 */
public class ITestBusinessRulesRetirarFondos extends BaseITestService {

    /**
     * Objeto de loggeo
     */
    private static final Logger logger = LoggerFactory.getLogger(ITestBusinessRulesRetirarFondos.class);

    /** Bean tesoreriaService */
    private TesoreriaService tesoreriaService;

    /**
     * @see com.indeval.portaldali.middleware.services.BaseITestService#onSetUp()
     */
    protected void onSetUp() throws Exception {
        super.onSetUp();
        if (tesoreriaService == null) {
            tesoreriaService = (TesoreriaService) applicationContext.getBean("tesoreriaService");
        }
    }

    /**
     * 
     * @throws Exception
     */
    public void testBusinessRulesRetirarFondosOK() throws Exception {
        log.info("Entrando al metodo testBusinessRulesRetirarFondos");

        log.debug("Probando reglas de negocio de retiro de fondos con agente nulo");
        AgenteVO agente = new AgenteVO("01", "003");
        String idTipoRetiro = "21";
        BigDecimal importe = new BigDecimal("1000");
        BigInteger idBoveda = new BigInteger("0");
        
        Integer result = null;

        try {
            tesoreriaService.businessRulesRetirarFondos(agente, idTipoRetiro, idBoveda, importe);
            assertNotNull(result);
        } catch (BusinessException e) {
            e.printStackTrace();
        } finally {
            log.debug("La prueba se realizo exitosamente");
            log.debug("El resultado es: " + result);    
        }
    }

}

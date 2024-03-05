/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exception.tesoreria.tesoreria;

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
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
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
    public void testBusinessRulesRetirarFondosAgenteNulo() throws Exception {
        log.info("Entrando al metodo testBusinessRulesRetirarFondos");

        log.debug("Probando reglas de negocio de retiro de fondos con agente nulo");
        AgenteVO agente = new AgenteVO(null, null);
        String idTipoRetiro = "21";
        BigDecimal importe = new BigDecimal("1000");
        BigInteger idBoveda = new BigInteger("0");
        
        BigInteger result = null;
        
        try{
            result = tesoreriaService.businessRulesRetirarFondos(agente, idTipoRetiro, idBoveda, importe);    
        }
        catch(BusinessException e){
            e.printStackTrace();
        }
        finally{
            log.debug("Dentro de la sentencia finally de testBusinessRulesRetirarFondosAgenteNulo");
        }
    }

    
    /**
     * 
     * @throws Exception
     */
    public void testBusinessRulesRetirarFondosTipoRetiroNulo() throws Exception {
        log.info("Entrando al metodo testBusinessRulesRetirarFondosTipoRetiroNulo");
        
        log.debug("Probando reglas de negocio de retiro de fondos con agente nulo");
        AgenteVO agente = new AgenteVO("01", "003");
        String idTipoRetiro = null;
        BigDecimal importe = new BigDecimal("1000");
        BigInteger idBoveda = new BigInteger("0");

        BigInteger result = null;
        
        try{
            result = tesoreriaService.businessRulesRetirarFondos(agente, idTipoRetiro, idBoveda, importe);    
        }
        catch(BusinessException e){
            e.printStackTrace();
        }
        finally{
            log.debug("Dentro de la sentencia finally de testBusinessRulesRetirarFondosTipoRetiroNulo");
        }
    }

    /**
     * 
     * @throws Exception
     */
    public void testBusinessRulesRetirarFondosImporteCero() throws Exception {
        log.info("Entrando al metodo testBusinessRulesRetirarFondosImporteCero");
        
        AgenteVO agente = new AgenteVO("01", "003");
        String idTipoRetiro = "21";
        BigDecimal importe = new BigDecimal("0");
        BigInteger idBoveda = new BigInteger("0");
        
        BigInteger result = null;
        
        try{
            result = tesoreriaService.businessRulesRetirarFondos(agente, idTipoRetiro, idBoveda, importe);   
        }
        catch(BusinessException e){
            e.printStackTrace();
        }
        finally{
            log.debug("Dentro de la sentencia finally de testBusinessRulesRetirarFondosImporteCero");
        }
    }    
     
}

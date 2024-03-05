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
 * Clase de prueba para el servicio que abstrae las reglas de negocio para el servicio de 
 * traspaso entre cuentas de control.
 * @author salvador
 *
 */
public class ITestBusinessRulesTraspasarEntreCuentas extends BaseITestService {

    /**
     * Objeto de loggeo
     */
    private static final Logger logger = LoggerFactory.getLogger(ITestBusinessRulesTraspasarEntreCuentas.class);

    /** Inyecci&oacute;n de bean de tesoreria */
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
    public void testBusinessRulesTraspasarEntreCuentasTraspasanteNulo() throws Exception {
        log.info("Entrando al metodo testBusinessRulesTraspasarEntreCuentasTraspasanteNulo");
        
        AgenteVO traspasante = null;
        AgenteVO receptor = new AgenteVO("01", "003");
        BigDecimal importe = new BigDecimal("1000");
        BigInteger idBoveda = new BigInteger("0");

        Integer result = null;

        try {
            tesoreriaService.businessRulesTraspasarEntreCuentas(traspasante, receptor, idBoveda, importe);
        } catch (BusinessException e) {
            e.printStackTrace();
        } finally {
            log.debug("Dentro de la sentencia finally de testBusinessRulesTraspasarEntreCuentasTraspasanteNulo");
        }
    }

    /**
     * 
     * @throws Exception
     */
    public void testBusinessRulesTraspasarEntreCuentasReceptorNulo() throws Exception {
        log.info("Entrando al metodo testBusinessRulesTraspasarEntreCuentasReceptorNulo");
        
        AgenteVO traspasante = new AgenteVO("01", "003");
        AgenteVO receptor = null;
        BigDecimal importe = new BigDecimal("1000");
        BigInteger idBoveda = new BigInteger("0");

        Integer result = null;

        try {
            tesoreriaService.businessRulesTraspasarEntreCuentas(traspasante, receptor, idBoveda, importe);
        } catch (BusinessException e) {
            e.printStackTrace();
        } finally {
            log.debug("Dentro de la sentencia finally de testBusinessRulesTraspasarEntreCuentasReceptorNulo");
        }
    }    
    

    /**
     * 
     * @throws Exception
     */
    public void testBusinessRulesTraspasarEntreCuentasImporteCero() throws Exception {
        log.info("Entrando al metodo testBusinessRulesTraspasarEntreCuentasImporteCero");
        
        AgenteVO traspasante = new AgenteVO("01", "003","0001");
        AgenteVO receptor = new AgenteVO("01","003","0307");
        BigDecimal importe = new BigDecimal("0");
        BigInteger idBoveda = new BigInteger("0");
        
        Integer result = null;

        try {
            tesoreriaService.businessRulesTraspasarEntreCuentas(traspasante, receptor, idBoveda, importe);
        } catch (BusinessException e) {
            e.printStackTrace();
        } finally {
            log.debug("Dentro de la sentencia finally de testBusinessRulesTraspasarEntreCuentasReceptorNulo");
        }
    }
    
}

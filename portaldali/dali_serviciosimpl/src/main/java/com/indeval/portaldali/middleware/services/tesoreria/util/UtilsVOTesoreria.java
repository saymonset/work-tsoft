/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.tesoreria.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.tesoreria.Saldo3CuentasVO;
import com.indeval.portaldali.persistence.model.SaldoControlada;
import com.indeval.portaldali.persistence.model.SaldoNombrada;

/**
 * Clase utilitaria que contiene metodos para el manejo o manipulacion de
 * los VOs utilizados por MercadoDineroService
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class UtilsVOTesoreria {

    
    /** Log de Clase. */
    private static final Logger log = LoggerFactory.getLogger(UtilsVOTesoreria.class);
    
    /**
     * @param saldoNombrada
     * @return Saldo3CuentasVO
     */
    public static final Saldo3CuentasVO getInstanceSaldo3CuentasVO(SaldoNombrada saldoNombrada){
    	
    	log.info("Entrando a UtilsVOTesoreria.getInstanceSaldo3CuentasVO");
    	
    	Saldo3CuentasVO saldo3Cuentas = new Saldo3CuentasVO();
    	
    	if(saldoNombrada != null) {
        	saldo3Cuentas.setSaldoCtaConcentradora(saldoNombrada.getSaldoDisponible());
        	saldo3Cuentas.setSaldoTot(saldoNombrada.getSaldoDisponible().add(
        			saldoNombrada.getSaldoNoDisponible()));    		
    	}
    	
    	return saldo3Cuentas;
    	
    }
    
    /**
     * @param saldoControlada
     * @return Saldo3CuentasVO
     */
    public static final Saldo3CuentasVO getInstanceSaldo3CuentasVO(SaldoControlada saldoControlada){
    	
    	log.info("Entrando a UtilsVOTesoreria.getInstanceSaldo3CuentasVO");
    	
    	Saldo3CuentasVO saldo3Cuentas = new Saldo3CuentasVO();
    	
    	if(saldoControlada != null) {
        	saldo3Cuentas.setSaldoCtaConcentradora(saldoControlada.getSaldo());
        	saldo3Cuentas.setSaldoTot(saldoControlada.getSaldo());    		
    	}
    	
    	return saldo3Cuentas;
    	
    }
    
}

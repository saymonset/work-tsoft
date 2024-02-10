/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.portalinternacional.posicionnombrada;

import java.math.BigInteger;
import java.util.List;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portalinternacional.persistence.dao.PosicionNombradaDivIntDao;

/**
 * @author <a href="mailto:ivan.kazakov@2hsoftware.com.mx">Ivan Kazakov</a>
 *
 */
public class ITestPosicionNombradaDivIntDao extends BaseDaoTestCase {

    /** Dao a probar */
    private PosicionNombradaDivIntDao posicionNombradaDivIntDao;
    
    /**
     * @see com.indeval.persistence.unittest.BaseDaoTestCase#onSetUp()
     */
    protected void onSetUp() throws Exception {
        super.onSetUp();
        posicionNombradaDivIntDao = (PosicionNombradaDivIntDao) getBean("posicionNombradaDivIntDao");
    }
    
//    /**
//     * @throws Exception
//     */
//    public void testCountNumeroEmisionesPosicionPorCuentaNombrada_1() throws Exception {
//
//    	assertNotNull(posicionNombradaDivIntDao);
//    	Long result = posicionNombradaDivIntDao.countNumeroEmisionesPosicionPorCuentaNombrada(new Long(1386),false);
//    	assertNotNull(result);
//    	System.out.println("------------> total de registros encontrados ("+result+")");
//    }
//
//    /**
//     * @throws Exception
//     */
//    public void testCountNumeroEmisionesPosicionPorCuentaNombrada_2() throws Exception {
//
//    	assertNotNull(posicionNombradaDivIntDao);
//    	Long result = posicionNombradaDivIntDao.countNumeroEmisionesPosicionPorCuentaNombrada(new Long(1386),true);
//    	assertNotNull(result);
//    	System.out.println("------------> total de registros encontrados ("+result+")");
//    }
//
//    /**
//     * @throws Exception
//     */
//    public void testSumPosicionDisponiblePorCuentaNombrada_1() throws Exception {
//
//    	assertNotNull(posicionNombradaDivIntDao);
//    	BigInteger result = posicionNombradaDivIntDao.sumPosicionDisponiblePorCuentaNombrada(new Long(1386),false);
//    	assertNotNull(result);
//    	System.out.println("------------> suma ("+result+")");
//    }
//
//    /**
//     * @throws Exception
//     */
//    public void testSumPosicionDisponiblePorCuentaNombrada_2() throws Exception {
//
//    	assertNotNull(posicionNombradaDivIntDao);
//    	BigInteger result = posicionNombradaDivIntDao.sumPosicionDisponiblePorCuentaNombrada(new Long(1386),true);
//    	assertNotNull(result);
//    	System.out.println("------------> suma ("+result+")");
//    }

    /**
     * @throws Exception
     */
    public void testCountNumeroEmisionesInbur_1() throws Exception {
    	
    	assertNotNull(posicionNombradaDivIntDao);
    	Long result = posicionNombradaDivIntDao.countNumeroEmisionesInbur();
    	assertNotNull(result);
    	System.out.println("------------> total de registros encontrados ("+result+")");
    }
//
//    /**
//     * @throws Exception
//     */
//    public void testCountNumeroEmisionesNafin_1() throws Exception {
//
//    	assertNotNull(posicionNombradaDivIntDao);
//    	Long result = posicionNombradaDivIntDao.countNumeroEmisionesNafin();
//    	assertNotNull(result);
//    	System.out.println("------------> total de registros encontrados ("+result+")");
//    }
//
//    /**
//     * @throws Exception
//     */
//    public void testCountNumeroEmisionesVitro_1() throws Exception {
//
//    	assertNotNull(posicionNombradaDivIntDao);
//    	Long result = posicionNombradaDivIntDao.countNumeroEmisionesVitro();
//    	assertNotNull(result);
//    	System.out.println("------------> total de registros encontrados ("+result+")");
//    }
//
//    /**
//     * @throws Exception
//     */
//    public void testCountNumeroEmisionesBanamex_1() throws Exception {
//
//    	assertNotNull(posicionNombradaDivIntDao);
//    	Long result = posicionNombradaDivIntDao.countNumeroEmisionesBanamex();
//    	assertNotNull(result);
//    	System.out.println("------------> total de registros encontrados ("+result+")");
//    }
//
//    /**
//     * @throws Exception
//     */
//    public void testSumPosicionDisponibleBanamex_1() throws Exception {
//
//    	assertNotNull(posicionNombradaDivIntDao);
//    	BigInteger result = posicionNombradaDivIntDao.sumPosicionDisponibleBanamex();
//    	assertNotNull(result);
//    	System.out.println("------------> sum ("+result+")");
//    }
//
//    /**
//     * @throws Exception
//     */
//    public void testSumPosicionDisponibleInbur_1() throws Exception {
//
//    	assertNotNull(posicionNombradaDivIntDao);
//    	BigInteger result = posicionNombradaDivIntDao.sumPosicionDisponibleInbur();
//    	assertNotNull(result);
//    	System.out.println("------------> sum ("+result+")");
//    }
//
//    /**
//     * @throws Exception
//     */
//    public void testSumPosicionDisponibleNafin_1() throws Exception {
//
//    	assertNotNull(posicionNombradaDivIntDao);
//    	BigInteger result = posicionNombradaDivIntDao.sumPosicionDisponibleNafin();
//    	assertNotNull(result);
//    	System.out.println("------------> sum ("+result+")");
//    }
//
//    /**
//     * @throws Exception
//     */
//    public void testSumPosicionDisponibleVitro_1() throws Exception {
//
//    	assertNotNull(posicionNombradaDivIntDao);
//    	BigInteger result = posicionNombradaDivIntDao.sumPosicionDisponibleVitro();
//    	assertNotNull(result);
//    	System.out.println("------------> sum ("+result+")");
//    }
//
//    /**
//     * @throws Exception
//     */
//    public void testConsultaPosicionesFideicomiso_1() throws Exception {
//    	assertNotNull(posicionNombradaDivIntDao);
//    	/* BANAMEX */
//    	AgenteVO agenteVO = new AgenteVO("02","061");
//    	List<Object> result = posicionNombradaDivIntDao.consultaPosicionesFideicomiso(agenteVO);
//    	assertNotNull(result);
//    	assertFalse(result.isEmpty());
//    	System.out.println("---------------> registros encontrados ("+result.size()+")");
//    }
//
//    /**
//     * @throws Exception
//     */
//    public void testConsultaPosicionesFideicomiso_2() throws Exception {
//    	assertNotNull(posicionNombradaDivIntDao);
//    	/* NAFINSA */
//    	AgenteVO agenteVO = new AgenteVO("02","022");
//    	List<Object> result = posicionNombradaDivIntDao.consultaPosicionesFideicomiso(agenteVO);
//    	assertNotNull(result);
//    	assertFalse(result.isEmpty());
//    	System.out.println("---------------> registros encontrados ("+result.size()+")");
//    }
//
//    /**
//     * @throws Exception
//     */
//    public void testConsultaPosicionesFideicomiso_3() throws Exception {
//    	assertNotNull(posicionNombradaDivIntDao);
//    	/* INBURSA */
//    	AgenteVO agenteVO = new AgenteVO("02","021");
//    	List<Object> result = posicionNombradaDivIntDao.consultaPosicionesFideicomiso(agenteVO);
//    	assertNotNull(result);
//    	assertFalse(result.isEmpty());
//    	System.out.println("---------------> registros encontrados ("+result.size()+")");
//    }

}

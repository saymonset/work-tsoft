/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.portalinternacional.posicionnombrada;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portalinternacional.persistence.dao.PosicionNombradaHDivIntDao;

/**
 * @author <a href="mailto:ivan.kazakov@2hsoftware.com.mx">Ivan Kazakov</a>
 *
 */
public class ITestPosicionNombradaHDivIntDao extends BaseDaoTestCase {

    /** Dao a probar */
    private PosicionNombradaHDivIntDao posicionNombradaHDivIntDao;
    
    /**
     * @see com.indeval.persistence.unittest.BaseDaoTestCase#onSetUp()
     */
    protected void onSetUp() throws Exception {
        super.onSetUp();
        posicionNombradaHDivIntDao = (PosicionNombradaHDivIntDao) getBean("posicionNombradaHDivIntDao");
    }
    
    /**
     * @throws Exception
     */
    public void testCountNumeroEmisionesPosicionPorCuentaNombrada_1() throws Exception {
    	
    	assertNotNull(posicionNombradaHDivIntDao);
    	Long result = posicionNombradaHDivIntDao.countNumeroEmisionesPosicionPorCuentaNombrada(new Long(2395),false);
    	assertNotNull(result);
    	System.out.println("------------> total de registros encontrados ("+result+")");
    }

    /**
     * @throws Exception
     */
    public void testCountNumeroEmisionesPosicionPorCuentaNombrada_2() throws Exception {
    	
    	assertNotNull(posicionNombradaHDivIntDao);
    	Long result = posicionNombradaHDivIntDao.countNumeroEmisionesPosicionPorCuentaNombrada(new Long(2395),true);
    	assertNotNull(result);
    	System.out.println("------------> total de registros encontrados ("+result+")");
    }
    
    /**
     * @throws Exception
     */
    public void testSumPosicionDisponiblePorCuentaNombrada_1() throws Exception {
    	
    	assertNotNull(posicionNombradaHDivIntDao);
    	BigInteger result = posicionNombradaHDivIntDao.sumPosicionDisponiblePorCuentaNombrada(new Long(2395),false);
    	assertNotNull(result);
    	System.out.println("------------> suma ("+result+")");
    }

    /**
     * @throws Exception
     */
    public void testSumPosicionDisponiblePorCuentaNombrada_2() throws Exception {
    	
    	assertNotNull(posicionNombradaHDivIntDao);
    	BigInteger result = posicionNombradaHDivIntDao.sumPosicionDisponiblePorCuentaNombrada(new Long(2395),true);
    	assertNotNull(result);
    	System.out.println("------------> suma ("+result+")");
    }
    /**
     * @throws Exception
     */
    public void testCountNumeroEmisionesInbur_1() throws Exception {
    	
    	assertNotNull(posicionNombradaHDivIntDao);
    	Long result = posicionNombradaHDivIntDao.countNumeroEmisionesInbur();
    	assertNotNull(result);
    	System.out.println("------------> total de registros encontrados ("+result+")");
    }

    /**
     * @throws Exception
     */
    public void testCountNumeroEmisionesNafin_1() throws Exception {
    	
    	assertNotNull(posicionNombradaHDivIntDao);
    	Long result = posicionNombradaHDivIntDao.countNumeroEmisionesNafin();
    	assertNotNull(result);
    	System.out.println("------------> total de registros encontrados ("+result+")");
    }
    
    /**
     * @throws Exception
     */
    public void testCountNumeroEmisionesVitro_1() throws Exception {
    	
    	assertNotNull(posicionNombradaHDivIntDao);
    	Long result = posicionNombradaHDivIntDao.countNumeroEmisionesVitro();
    	assertNotNull(result);
    	System.out.println("------------> total de registros encontrados ("+result+")");
    }

    /**
     * @throws Exception
     */
    public void testCountNumeroEmisionesBanamex_1() throws Exception {
    	
    	assertNotNull(posicionNombradaHDivIntDao);
    	Long result = posicionNombradaHDivIntDao.countNumeroEmisionesBanamex();
    	assertNotNull(result);
    	System.out.println("------------> total de registros encontrados ("+result+")");
    }
    
    /**
     * @throws Exception
     */
    public void testSumPosicionDisponibleBanamex_1() throws Exception {
    	
    	assertNotNull(posicionNombradaHDivIntDao);
    	BigInteger result = posicionNombradaHDivIntDao.sumPosicionDisponibleBanamex();
    	assertNotNull(result);
    	System.out.println("------------> sum ("+result+")");
    }

    /**
     * @throws Exception
     */
    public void testSumPosicionDisponibleInbur_1() throws Exception {
    	
    	assertNotNull(posicionNombradaHDivIntDao);
    	BigInteger result = posicionNombradaHDivIntDao.sumPosicionDisponibleInbur();
    	assertNotNull(result);
    	System.out.println("------------> sum ("+result+")");
    }

    /**
     * @throws Exception
     */
    public void testSumPosicionDisponibleNafin_1() throws Exception {
    	
    	assertNotNull(posicionNombradaHDivIntDao);
    	BigInteger result = posicionNombradaHDivIntDao.sumPosicionDisponibleNafin();
    	assertNotNull(result);
    	System.out.println("------------> sum ("+result+")");
    }

    /**
     * @throws Exception
     */
    public void testSumPosicionDisponibleVitro_1() throws Exception {
    	
    	assertNotNull(posicionNombradaHDivIntDao);
    	BigInteger result = posicionNombradaHDivIntDao.sumPosicionDisponibleVitro();
    	assertNotNull(result);
    	System.out.println("------------> sum ("+result+")");
    }

    /**
     * @throws Exception
     */
    public void testConsultaPosicionesFideicomiso_1() throws Exception {
    	assertNotNull(posicionNombradaHDivIntDao);

    	/* BANAMEX */
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(2008, 10, 14);
    	AgenteVO agenteVO = new AgenteVO("02","061");
    	List<Object> result = posicionNombradaHDivIntDao.consultaPosicionesFideicomiso(agenteVO,calendar.getTime());
    	assertNotNull(result);
    	assertFalse(result.isEmpty());
    	System.out.println("---------------> registros encontrados ("+result.size()+")");
    }

    /**
     * @throws Exception
     */
    public void testConsultaPosicionesFideicomiso_2() throws Exception {
    	assertNotNull(posicionNombradaHDivIntDao);

    	/* NAFINSA */
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(2008, 10, 14);
    	AgenteVO agenteVO = new AgenteVO("02","022");
    	List<Object> result = posicionNombradaHDivIntDao.consultaPosicionesFideicomiso(agenteVO, calendar.getTime());
    	assertNotNull(result);
    	assertFalse(result.isEmpty());
    	System.out.println("---------------> registros encontrados ("+result.size()+")");
    }

    /**
     * @throws Exception
     */
    public void testConsultaPosicionesFideicomiso_3() throws Exception {
    	assertNotNull(posicionNombradaHDivIntDao);

    	/* INBURSA */
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(2008, 10, 14);
    	AgenteVO agenteVO = new AgenteVO("02","021");
    	List<Object> result = posicionNombradaHDivIntDao.consultaPosicionesFideicomiso(agenteVO,calendar.getTime());
    	assertNotNull(result);
    	assertFalse(result.isEmpty());
    	System.out.println("---------------> registros encontrados ("+result.size()+")");
    }
    
}

/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.dali.posicionnombradadao;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.middleware.services.util.UtilServices;
import com.indeval.portaldali.persistence.dao.common.PosicionNombradaDaliDao;
import com.indeval.portaldali.persistence.model.PosicionNombrada;
import com.indeval.portaldali.persistence.model.PosicionNombradaHistorico;
import com.indeval.portaldali.persistence.util.DateUtil;
import com.indeval.portaldali.persistence.vo.PageVO;
import com.indeval.portaldali.persistence.vo.TPosicionNombradaParamsPersistence;

/**
 * @author Rafael Ibarra
 *
 */
public class ITestGetTPosicionNombradaSaldoInicial_1 extends BaseDaoTestCase {
	
	/** Objeto de loggeo  */
    private static final Logger log = LoggerFactory.getLogger(ITestGetTPosicionNombradaSaldoInicial_1.class);

    /**
     * bean de cInstrumentoDao
     */
	private PosicionNombradaDaliDao tPosicionNombradaDao;
	
	
    /**
     * @see com.indeval.persistence.unittest.BaseDaoTestCase#onSetUp()
     */
    protected void onSetUp() {
        super.onSetUp();
        tPosicionNombradaDao = (PosicionNombradaDaliDao) getBean("tPosicionNombradaDao");
    }
    
    /**
     * TestCase para testGetTPosicionNombrada()
     * @throws ParseException 
     */
    public void testGetTPosicionNombradaSaldoInicial_1() throws Exception {
    	
    	log.info("Entrando a ITestGetTPosicionNombrada_1.testGetTPosicionNombradaSaldoInicial_1()");
    	
    	assertNotNull(tPosicionNombradaDao);
    	
    	TPosicionNombradaParamsPersistence params = 
    		UtilsTPosicionNombrada.getInstanceTPosicionNombradaParamsPersistence();
    	params.setIdInstitucion("01");
    	params.setFolioInstitucion("003");
    	params.setCuentas(new String[]{"0315"});
//    	params.setTiposDeValor(new String[]{"1"});
//    	params.setEmisora("ALFA");
//    	params.setSerie("A");
//    	params.setCupon("0023");
//    	params.setIdBoveda(new BigInteger("1"));
//    	params.setIsin("MXP000511016");
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		Date fecha = sdf.parse("19/11/2008");
		Date fechas[] = DateUtil.preparaIntervaloFechas(fecha, fecha);
		params.setFechaInicio(fechas[0]);
		params.setFechaFin(fechas[1]);
    	PageVO pagina = new PageVO();
    	pagina.setOffset(0);
    	pagina.setRegistrosXPag(0);
		params.setPageVO(pagina);
		
    	PageVO pageVO = tPosicionNombradaDao.getTPosicionNombradaSaldoInicial(params);
    	
    	assertNotNull(pageVO);
    	assertNotNull(pageVO.getRegistros());
    	
        log.info("Total Registros : [" + pageVO.getTotalRegistros() + "]");
        log.info("Registros en la pagina : [" + pageVO.getRegistros().size() + "]");
        Iterator it = pageVO.getRegistros().iterator();
        while( it.hasNext() ) {
        	PosicionNombrada pn = (PosicionNombrada)it.next();
	        BigInteger saldoInicial = BigInteger.ZERO;
        	
	        Set<PosicionNombradaHistorico> pnh = pn.getPosicionNombradaHistorico();
	        if( pnh != null ) {
	        	if( pnh.size() <= 1 ) {
		        	Iterator<PosicionNombradaHistorico> ith = pnh.iterator();
		        	if( ith.hasNext() ) {
		        		PosicionNombradaHistorico temp = (PosicionNombradaHistorico)ith.next();
		        		if( temp != null && temp.getPosicionDisponible() != null ) {
		        			saldoInicial = temp.getPosicionDisponible();
		        		}
		        	} 
	        	} else {
		        	throw new Exception("Mas de una posicion en Historico con la misma fecha: [" + pnh.size() + "]");
		        }
	        } 
	        
        	if( pn != null ) {
	        	log.info("Detalles: [" + pn.getCupon().getEmision().getInstrumento().getClaveTipoValor() + "-" +
	        			pn.getCupon().getEmision().getEmisora().getClavePizarra() + "-" + 
	        			pn.getCupon().getEmision().getSerie() + "-" +
	        			pn.getCupon().getClaveCupon() + "-" +
	        			saldoInicial + "-" + 
	        			pn.getCuentaNombrada().getCuenta() + "-" +
	        			pn.getPosicionDisponible() + "-" +
	        			pn.getIdPosicion() + "----------------]");
        	}
        	
        }  	
    }
    
    
	
}

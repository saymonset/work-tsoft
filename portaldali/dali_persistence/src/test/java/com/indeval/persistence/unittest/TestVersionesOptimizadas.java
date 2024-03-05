/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.unittest;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.EmisionDTO;
import com.indeval.portaldali.middleware.dto.EmisoraDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.MercadoDTO;
import com.indeval.portaldali.middleware.dto.SerieDTO;
import com.indeval.portaldali.middleware.dto.TipoCuentaDTO;
import com.indeval.portaldali.middleware.dto.TipoNaturalezaDTO;
import com.indeval.portaldali.middleware.dto.TipoTenenciaDTO;
import com.indeval.portaldali.middleware.dto.TipoValorDTO;

/**
 * Clase para probar nuevas versiones de metodos
 * IMPORTANTE: En el classpath deben de estar los proyectos:
 * 				dali_persistence
 * 				dali_servicios
 * 				dalis_servicios_impl
 * La prueba apunta a la BD: jdbc:oracle:thin:@10.100.144.4:1521:DWBUILD con el usuario liqval_user3
 * (los datos estan en testApplicationContext.xml)
 * 
 * @author 2h
 *
 */
public class TestVersionesOptimizadas extends BaseDaoTestCase {
    
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
    //protected static final String CONTEXT = "testApplicationContext.xml";
    protected boolean logging = true;
    //private static ApplicationContext context;
    
    static {
		// context = new ClassPathXmlApplicationContext(
		// new String[] { CONTEXT });
        // log.debug(context.toString());
    }
    
    /**
     *  Metodo para obtener el bean del application context
     * @param nombreBean
     * @return
     */
    public Object getBean(String nombreBean) {
        try {
            return applicationContext.getBean(nombreBean);
        } catch (Exception e) {
            logger.debug(e.getMessage(), e);
            return null;
        }
    }
    
    /**
     * Test Dummy
     */
    public void testDummy(){
        //Manten feliz a JUnit
    }
    
    /**
     * Para obtener los datos de prueba de la  boveda
     * @return
     */
    private BovedaDTO obtenerBoveda() {
    	BovedaDTO boveda = new BovedaDTO();
    	boveda.setClaveTipoBoveda("V");
    	boveda.setId(-1);
    	
    	return boveda;
    }
    
    /**
     * Para  obtener las posiciones de prueba
     * @return
     */
    private List<Long> obtenerPosiciones() {
    	List<Long> idsPosiciones = new ArrayList<Long>();
    	idsPosiciones.add(5964L);
    	idsPosiciones.add(5980L);
    	idsPosiciones.add(5983L);
    	idsPosiciones.add(6011L);
    	idsPosiciones.add(6047L);
    	idsPosiciones.add(6085L);
    	idsPosiciones.add(6169L);
    	idsPosiciones.add(6170L);
    	idsPosiciones.add(6171L);
    	idsPosiciones.add(6201L);
    	idsPosiciones.add(6203L);
    	idsPosiciones.add(6219L);
    	idsPosiciones.add(6301L);
    	idsPosiciones.add(56942L);
    	idsPosiciones.add(6305L);
    	idsPosiciones.add(57772L);
    	idsPosiciones.add(6630L);
    	idsPosiciones.add(6387L);
    	idsPosiciones.add(6490L);
    	idsPosiciones.add(6653L);
    	
    	return idsPosiciones;
    }

    /**
     * Para obtener los IDs de las emisiones de prueba
     * @return
     */
	private List<Long> obtenerEmisiones() {
		List<Long> idsEmisiones = new ArrayList<Long>();
		idsEmisiones.add(17992L);
    	idsEmisiones.add(1162L);
    	idsEmisiones.add(4417L);
    	idsEmisiones.add(4419L);
    	idsEmisiones.add(4423L);
    	idsEmisiones.add(12434L);
    	idsEmisiones.add(5659L);
    	idsEmisiones.add(23167L);
    	idsEmisiones.add(3535L);
    	idsEmisiones.add(1235L);
    	idsEmisiones.add(4004L);
    	idsEmisiones.add(1188L);
    	idsEmisiones.add(1191L);
    	idsEmisiones.add(2738L);
    	idsEmisiones.add(3586L);
    	idsEmisiones.add(2740L);
    	idsEmisiones.add(2742L);
    	idsEmisiones.add(1534L);
    	idsEmisiones.add(4851L);
    	idsEmisiones.add(1297L);
    	
    	return idsEmisiones;
	}

	/**
	 * Para obtener los datos de la emision de prueba
	 * @return
	 */
	private EmisionDTO obtenerEmision() {
		EmisionDTO emision = new EmisionDTO();
    	DivisaDTO divisa = new DivisaDTO();
    	emision.setDivisa(divisa);
    	EmisoraDTO emisora = new EmisoraDTO();
    	emisora.setDescripcion("TODAS");
    	emisora.setId(-1);
    	emision.setEmisora(emisora);
    	emision.setId(-1);
    	emision.setIsin("");
    	emision.setValorNominal(0);
    	SerieDTO serie = new SerieDTO();
    	serie.setDescripcion("TODAS");
    	serie.setEmisora(emisora);
    	serie.setId(0);
    	serie.setSerie("-1");
    	TipoValorDTO tvalor = new TipoValorDTO();
    	tvalor.setClaveTipoValor("TODOS");
    	tvalor.setDescripcion("TODOS");
    	tvalor.setId(-1);
    	MercadoDTO mercado = new MercadoDTO();
    	mercado.setDescripcion("TODOS");
    	mercado.setId(-1);
    	tvalor.setMercado(mercado);
    	serie.setTipoValor(tvalor);
    	emision.setSerie(serie);
    	emision.setTipoValor(tvalor);
    	emision.setValorNominal(0);
    	
    	return emision;
	}

	/**
	 * Para obtener los datos de prueba de la cuenta
	 * @return
	 */
	private CuentaDTO obtenerCuenta() {
		CuentaDTO cuenta = new CuentaDTO();
    	cuenta.setCuenta("0505");
    	cuenta.setDescripcion("010050505");
    	cuenta.setIdCuenta(1065);
    	InstitucionDTO inst = new InstitucionDTO();
    	inst.setClaveTipoInstitucion("01");
    	inst.setFolioInstitucion("005");
    	inst.setId(5);
    	inst.setNombreCorto("CBBURMEX");
    	cuenta.setInstitucion(inst);
    	cuenta.setNumeroCuenta("010050505");
    	TipoTenenciaDTO ten = new TipoTenenciaDTO();
    	ten.setClaveTipoCuenta("TERC_NOM");
    	ten.setIdTipoCuenta(2);
    	ten.setTipoCustodia("V");
    	TipoCuentaDTO tipoc = new TipoCuentaDTO();
    	tipoc.setDescripcion("NOMBRADA");
    	tipoc.setId("N");
    	ten.setTipoCuenta(tipoc);
    	TipoNaturalezaDTO tipon = new TipoNaturalezaDTO();
    	tipon.setDescripcion("PASIVO");
    	tipon.setId("P");
    	ten.setTipoNaturaleza(tipon);
    	cuenta.setTipoTenencia(ten);
    	
    	return cuenta;
	}    
}

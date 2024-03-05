/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exception.mercadocapitales.mercadocapitales;

import java.math.BigDecimal;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.mercadocapitales.GetCapturaTraspasoContraPagoParams;
import com.indeval.portaldali.middleware.services.mercadocapitales.MercadoCapitalesService;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;

/**
 * Clase de prueba para el servicio de mercado de capitales
 *
 * @author Sergio Mena
 */
public class ITestMercadoCapitalesService_e2 extends BaseITestService {

	 /**
     * Servicio a probar.
     */
    private MercadoCapitalesService mercadoCapitales;

    /** Log de bitacora */
    Logger logger = LoggerFactory.getLogger(ITestMercadoCapitalesService_e2.class);

    /**
     * Inicializa lo que la prueba necesita para su ejecucion.
     */
    protected void onSetUp() throws Exception {
        super.onSetUp();
        if (mercadoCapitales == null) {
            mercadoCapitales = (MercadoCapitalesService) applicationContext
                    .getBean("mercadoCapitalesService");
        }
    }
    
    /**
     * Metodo que prueba el valor maximo de  la cantidad definido en el patron establecido.
     * @throws Exception
     */
    public void testBusinessRulesCapturaTraspasoContraPago() throws Exception {
    	log.info("Entrando a ITestMercadoCapitalesService_e2" +
    			".testBusinessRulesCapturaTraspasoContraPago()");
    	
    	GetCapturaTraspasoContraPagoParams params = new GetCapturaTraspasoContraPagoParams();
        GregorianCalendar fecha = new GregorianCalendar(1, 1, 1);
        fecha.set(2007, 3, 14);

        log.debug("fecha " + fecha.getTime());
        
        EmisionVO emision = new EmisionVO();
        AgenteVO traspasante = new AgenteVO();
        AgenteVO receptor = new AgenteVO();

        // Datos traspasante
        traspasante.setId("01");
        traspasante.setFolio("003");
        traspasante.setCuenta("0307");
        traspasante.setTipo("TERC");

        // Datos receptor
        receptor.setId("02");
        receptor.setFolio("014");
        receptor.setCuenta("6620");
        receptor.setTipo("PROP");

        // emision
        emision.setIdTipoValor("1");
        emision.setEmisora("TELMEX");
        emision.setSerie("L");
        emision.setCupon("0041");

        // parametros
        params.setTraspasante(traspasante);
        params.setReceptor(receptor);
        params.setEmision(emision);
        params.setCantidad(new BigDecimal("9999999999999999.99999999"));
        params.setPrecioTitulo(new BigDecimal("11"));
        params.setCveReporto("A");
        params.setFechaLiquidacion(fecha.getTime());
        params.setLiquidacion(new Integer("0"));
        params.setUsuario("PORTAL LEGADO");
        params.setNombreUsuario("PORTAL");
        
        String folioControl = mercadoCapitales.businessRulesCapturaTraspasoContraPago(params);
        
        assertNotNull(folioControl);
        
        
        
	}

}

/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exception.mercadodinero.mercadodinero;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.mercadodinero.AdministracionGarantiaVO;
import com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService;
import com.indeval.portaldali.middleware.services.mercadodinero.TraspasoMercadoDineroParams;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.persistence.dao.common.DateUtilsDao;
import com.indeval.portaldali.persistence.model.Emision;
import com.indeval.portaldali.persistence.util.UtilsLog;

/** 
 *
 */
public class ITestTraspasosBusinessRules extends BaseITestService {

    /** Objeto de loggeo de clase */
    private static final Logger logger = LoggerFactory.getLogger(ITestTraspasosBusinessRules.class);

    /** Inyecci&oacute;n del bean mercadoDineroService */
    private MercadoDineroService mercadoDineroService;


    /**
     * En el m&eacute;todo onSetUp inicializamos los beans que utilizaremos en
     * la clase de prueba.
     *
     * @throws Exception
     */
    protected void onSetUp() throws Exception {        
        if (mercadoDineroService == null) {
            mercadoDineroService = (MercadoDineroService) applicationContext
                    .getBean("mercadoDineroService");
        }
      
    }

    /**
     * TestCase para el servicio de administracionGarantias()
     *
     * @throws Exception
     */
    public void testTraspasosMiscFisc() throws Exception {

        log.info("Entrando a ["+this.getClass().getName()+"]");
        
        TraspasoMercadoDineroParams traspasoMercadoDineroParams= new TraspasoMercadoDineroParams();
        AgenteVO agenteTraspasante= new AgenteVO();
        agenteTraspasante.setId("01");
        agenteTraspasante.setFolio("003");;
        agenteTraspasante.setCuenta("0307");
        
        AgenteVO agenteReceptor= new AgenteVO();
        
        agenteReceptor.setId("01");
        agenteReceptor.setFolio("001");;
        agenteReceptor.setCuenta("0109");
                
        traspasoMercadoDineroParams.setTraspasante(agenteTraspasante);
        traspasoMercadoDineroParams.setReceptor(agenteReceptor);
        traspasoMercadoDineroParams.setMercadoDinero(true);
        traspasoMercadoDineroParams.setTipoMovimiento("TRASPASO");
        
        EmisionVO emision = new EmisionVO();
        emision.setIdTipoValor("BI");
        emision.setEmisora("GOBFED");
        emision.setSerie("090108");
        emision.setCupon("0000");
        emision.setMercado("PG");
        
        traspasoMercadoDineroParams.setEmision(emision);
        
        traspasoMercadoDineroParams.setCantidad(new BigDecimal(1));        

        traspasoMercadoDineroParams.setPrecioAdquisicion(new BigDecimal(100000));
        
        Calendar fechaAdquisicion= Calendar.getInstance();
               
        fechaAdquisicion.set(Calendar.DAY_OF_MONTH, 19);
        fechaAdquisicion.set(Calendar.MONTH, Calendar.JANUARY);
        fechaAdquisicion.set(Calendar.YEAR, 2009);
        
        traspasoMercadoDineroParams.setFechaAdquisicion(fechaAdquisicion.getTime());
        
        
        traspasoMercadoDineroParams.setCliente("ERIK NAVARRETE SEVILLA");
        traspasoMercadoDineroParams.setRfcCURP("NASE800105PV4");
        
        traspasoMercadoDineroParams.setAceptaCargo(false);
        
                
        mercadoDineroService.traspasoMercadoDineroBusinessRules(traspasoMercadoDineroParams);
        
        log.debug("Ejecución exitosa ["+this.getClass().getName()+"]");

    }

}
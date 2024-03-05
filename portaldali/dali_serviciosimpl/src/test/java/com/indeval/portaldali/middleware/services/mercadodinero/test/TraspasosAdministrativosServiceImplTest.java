package com.indeval.portaldali.middleware.services.mercadodinero.test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import com.indeval.portaldali.middleware.services.BaseTestCase;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.movimientosadministrativos.TraspasosAdministrativosParams;
import com.indeval.portaldali.middleware.services.movimientosadministrativos.TraspasosAdministrativosService;

/**
 * Clase de prueba unitaria para verificar la integracicn del servicio de traspasos adminsitrativos
 * 
 * @author Emigdio Hernández
 * 
 */
public class TraspasosAdministrativosServiceImplTest extends BaseTestCase {

    public void testValidarBusinessRules() {
        /** Representa el detalle del usuario logueado */

        TraspasosAdministrativosService service = (TraspasosAdministrativosService) applicationContext
                .getBean("traspasosAdministrativosService");
        AgenteVO traspasante = new AgenteVO();
        traspasante.setId("01");
        traspasante.setFolio("003");
       
        traspasante.setCuenta("0053");

        AgenteVO receptor = new AgenteVO();
        receptor.setId("13");
        receptor.setFolio("003");
        receptor.setCuenta("8823");
        receptor.setTenencia("PROP");
        TraspasosAdministrativosParams params = new TraspasosAdministrativosParams();
        params.setMercadoDinero(false);

        params.setReceptor(receptor);
        params.setTraspasante(traspasante);

        params.setCantidadTitulos(new BigInteger("1500"));

        params.setEmision(new EmisionVO());
        params.getEmision().setCupon("0000");
        params.getEmision().setEmisora("ARCA");
        params.getEmision().setFechaVencimiento(new Date());
        params.getEmision().setIdTipoValor("1");
        params.getEmision().setSerie("*");
        params.getEmision().setCupon("1111");
        params.getEmision().setSaldoDisponible(new BigDecimal("400"));
        params.getEmision().setIsin("MX01AR2E0007");

        service.businessRulesTraspasosAdministrativos(params);

    }

}

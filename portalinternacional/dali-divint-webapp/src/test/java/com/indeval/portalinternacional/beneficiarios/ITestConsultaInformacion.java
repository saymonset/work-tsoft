/*
 * Copyrigth (c) 2009 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.beneficiarios;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaBeneficiariosParam;
import com.indeval.portalinternacional.presentation.test.BaseTestCase;

/**
 * Clase para
 * @author Rafael Ibarra Zendejas
 */
public class ITestConsultaInformacion extends BaseTestCase {

    private SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private ControlBeneficiariosService controlBeneficiariosService;
    

    @Override
    protected void onSetUp() throws Exception {
        super.onSetUp();
        if (controlBeneficiariosService == null) {
            controlBeneficiariosService = (ControlBeneficiariosService) applicationContext.getBean("controlBeneficiariosServiceEJB");
        }
    }

    public void testCargaInformacion() throws Exception {
        for(int i = 0; i < 100; i++) {
            log.info("---------");
            log.info("INICIANDO la consulta");
            Date fechaInicio = new Date();
            ConsultaBeneficiariosParam param = new ConsultaBeneficiariosParam();
            /* Campos de la Institucion */
            Institucion institucion = new Institucion();
            institucion.setIdInstitucion(61l);
            param.setInstitucion(institucion);
            PaginaVO paginaVO = new PaginaVO();
            paginaVO.setRegistrosXPag(0);
            paginaVO.setOffset(0);
        //    paginaVO = controlBeneficiariosService.consultaBeneficiarios(param, paginaVO);

            assertNotNull(paginaVO);
            assertNotNull(paginaVO.getRegistros());
            assertTrue("No existe la lista de Beneficiario", !paginaVO.getRegistros().isEmpty());
    //        log.debug("Total de registros reales = " + paginaVO.getTotalRegistros());
    //		log.debug("Total de registros = " + paginaVO.getRegistros().size());
    //		for (Iterator iter = paginaVO.getRegistros().iterator(); iter.hasNext();) {
    //			Beneficiario element = (Beneficiario) iter.next();
    //			assertNotNull(element);
    //			log.debug("Beneficiario [" + element + "]");
    //		}
            Date fechaFin = new Date();
            Long diff = fechaFin.getTime() - fechaInicio.getTime();

            int minutos = (int) (diff / 60000);
            int segundos = (int) (diff / 1000 % 60);

            log.info("FIN de la carga");
            log.info("Tiempo de ejecuacion: [" + minutos + " m " + segundos + " s] - [" + diff + "ms]");
            log.info("FINALIZANDO la consulta");
        }
    }
}


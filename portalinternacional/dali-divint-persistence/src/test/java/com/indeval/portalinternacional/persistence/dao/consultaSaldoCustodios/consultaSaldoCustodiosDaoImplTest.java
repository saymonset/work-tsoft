package com.indeval.portalinternacional.persistence.dao.consultaSaldoCustodios;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portaldali.persistence.modelo.Divisa;
import com.indeval.portalinternacional.middleware.servicios.dto.DivisaDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.SaldoCustodio;
import com.indeval.portalinternacional.persistence.dao.BovedaDao;
import com.indeval.portalinternacional.persistence.dao.ConsultaSaldoCustodiosDao;
import com.indeval.portalinternacional.persistence.dao.impl.consultaSaldoCustodiosDaoImpl;
import junit.framework.TestCase;

import java.math.BigInteger;
import java.util.List;

import static com.indeval.portalinternacional.persistence.dao.Boveda.Util.getDivisa;

public class consultaSaldoCustodiosDaoImplTest extends BaseDaoTestCase {

    ConsultaSaldoCustodiosDao dao;

    protected void onSetUp() throws Exception {
        super.onSetUp();
        dao = (ConsultaSaldoCustodiosDao) getBean("ConsultaSaldoCustodiosDao");
    }

    public void testConsultaGeneral() {
        System.out.println("testObtenerSaldos()");
        int expectedSize = 15;
        Divisa divisa = new Divisa();
        Boveda bov =new Boveda();
        divisa.setDescripcion("Argentine Peso");
        bov.setDescripcion("TODAS");

        assertNotNull("BovedaDao Nulo", dao);
        List<SaldoCustodio> consulta = dao.consultaGeneral(divisa,bov);


        assertNotNull(consulta);
    }

    public void testConsultaPorCuenta() {
    }
}
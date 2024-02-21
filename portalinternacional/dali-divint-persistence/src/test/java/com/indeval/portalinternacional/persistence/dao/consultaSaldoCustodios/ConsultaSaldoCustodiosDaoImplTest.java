package com.indeval.portalinternacional.persistence.dao.consultaSaldoCustodios;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portaldali.persistence.modelo.Divisa;
import com.indeval.portalinternacional.middleware.servicios.modelo.SaldoNombradaInt;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaSaldoCustodiosInDTO;
import com.indeval.portalinternacional.persistence.dao.ConsultaSaldoCustodiosDao;

import java.text.ParseException;
import java.util.List;

public class ConsultaSaldoCustodiosDaoImplTest extends BaseDaoTestCase {

    ConsultaSaldoCustodiosDao dao;

    protected void onSetUp() throws Exception {
        super.onSetUp();
        dao = (ConsultaSaldoCustodiosDao) getBean("consultaSaldoCustodiosDao");
    }

    public void testConsultaGeneral() {
        System.out.println("testObtenerSaldos()");
        int expectedSize = 15;
        Divisa divisa = new Divisa();
        Boveda bov =new Boveda();
        divisa.setDescripcion("Argentine Peso");
        bov.setDescripcion("TODAS");

        assertNotNull("BovedaDao Nulo", dao);
       // List<SaldoCustodio> consulta = dao.consultaGeneral(divisa,bov);


        //assertNotNull(consulta);
    }

    public void testConsultaPorCuenta() {
    }

    public void testConsultaSaldoCustodio() throws ParseException {


        // Objeto de prueba
        ConsultaSaldoCustodiosInDTO params = new ConsultaSaldoCustodiosInDTO();
        params.setBovedaDali("13");
        params.setDivisaDali("3");
        params.setIdCuenta("4040");



        // Prueba de consulta
        PaginaVO pvo=new PaginaVO();
        pvo.setRegistrosXPag(PaginaVO.TODOS);
        pvo.setOffset(0);
        PaginaVO cons=dao.consultaSaldoCustodio( params,pvo);

        for(SaldoNombradaInt reg : (List<SaldoNombradaInt>)cons.getRegistros()){
            System.out.println(reg.getCuentaNombrada().getInstitucion().getRazonSocial());
        }
        System.out.println("TOTAL REGISTROS "+cons.getTotalRegistros());

        assertTrue(true);

    }
}
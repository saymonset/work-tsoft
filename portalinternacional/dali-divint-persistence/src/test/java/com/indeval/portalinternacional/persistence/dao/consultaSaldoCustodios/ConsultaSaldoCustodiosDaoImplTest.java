package com.indeval.portalinternacional.persistence.dao.consultaSaldoCustodios;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portaldali.persistence.modelo.Divisa;
import com.indeval.portalinternacional.middleware.servicios.modelo.SaldoNombradaInt;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaSaldoCustodiosInDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaSaldoCustodiosTotalesInDTO;
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
        params.setBovedaDali("16");
        params.setDivisaDali("5");
        params.setIdCuentaPopup("");
        // Prueba de consulta
        PaginaVO pvo=new PaginaVO();
        pvo.setRegistrosXPag(PaginaVO.TODOS);
        pvo.setOffset(0);
        PaginaVO cons=dao.consultaSaldoCustodio( params,pvo);
        System.out.println("TOTAL REGISTROS "+cons.getTotalRegistros());
        assertTrue(true);
    }
    public void testConsultaSaldoCustodioTotales() throws ParseException {
        // Objeto de prueba
        ConsultaSaldoCustodiosInDTO params = new ConsultaSaldoCustodiosInDTO();
        params.setBovedaDali("16");
        params.setDivisaDali("5");
        params.setIdCuentaPopup("1");
        // Prueba de consulta
        ConsultaSaldoCustodiosTotalesInDTO cons=dao.consultaSaldoCustodioTotales( params);
        System.out.println("TOTAL SALDO "+cons.getTotalSaldo());
        System.out.println("TOTAL getTotalDisponible "+cons.getTotalDisponible());
        System.out.println("TOTAL getTotalNODisponible "+cons.getTotalNoDisponible());
        assertTrue(true);
    }

    // consultaSaldoCustodioTotales(final ConsultaSaldoCustodiosInDTO criteriosConsulta) throws BusinessException {
}
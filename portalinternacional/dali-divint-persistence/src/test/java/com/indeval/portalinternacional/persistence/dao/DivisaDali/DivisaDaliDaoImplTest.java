// Cambio Multidivisas
package com.indeval.portalinternacional.persistence.dao.DivisaDali;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.dto.DivisaDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.ConciliacionInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.SaldoNombradaInt;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaSaldoCustodiosInDTO;
import com.indeval.portalinternacional.persistence.dao.DivisaDaliDao;

import java.text.ParseException;
import java.util.List;

public class DivisaDaliDaoImplTest extends BaseDaoTestCase {

    DivisaDaliDao dao;

    protected void onSetUp() throws Exception {
        super.onSetUp();
        dao = (DivisaDaliDao) getBean("divisaDaliDao");
    }

    public void testBuscarDivisas() {
        System.out.println("testBuscarDivisas()");

        int expectedSize = 185;

        assertNotNull("DivisaDaliDao Nulo", dao);
        List<DivisaDTO> divisas = dao.buscarDivisas(null);

        for (DivisaDTO divisa : divisas) {
            System.out.println(divisa.getDescripcion());
        }

        assertFalse(divisas.isEmpty());
        assertNotNull(divisas);
        assertEquals(expectedSize, divisas.size());
    }

    public void testConsultarDivisaPorId() {
        System.out.println("testConsultarDivisaPorId()");

        int idDivisa = 3;
        String expectedClaveAlfabetica = "USD";
        String expectedClaveNumerica = "840";
        String expectedDescripcion = "USA DOLLAR";

        assertNotNull("DivisaDaliDao Nulo", dao);
        DivisaDTO divisa = dao.consultarDivisaPorId(idDivisa);

        assertNotNull(divisa);
        assertEquals(expectedClaveAlfabetica, divisa.getClaveAlfabetica());
        assertEquals(expectedClaveNumerica, divisa.getClaveNumerica());
        assertEquals(expectedDescripcion, divisa.getDescripcion());
    }

    public void testFindDivisasByBovedad() {
        System.out.println("testFindDivisaByBovedad()");

        long idBoveda = 12;

        assertNotNull("DivisaDaliDao Nulo", dao);
        List<DivisaDTO> list = dao.findDivisaByBovedad(idBoveda);


        assertNotNull(list);

    }
    public void testConsultaSaldoCustodio() throws ParseException {


        // Objeto de prueba
        //ConciliacionIntDTO params = new ConciliacionIntDTO();
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
         //   log.debug("reg =\n"+ObjectToString(reg));
            //System.out.println(ObjectToString(reg));
            System.out.println(reg.getCuentaNombrada().getInstitucion().getRazonSocial());
        }
        System.out.println("TOTAL REGISTROS "+cons.getTotalRegistros());

        assertTrue(true);

    }

}
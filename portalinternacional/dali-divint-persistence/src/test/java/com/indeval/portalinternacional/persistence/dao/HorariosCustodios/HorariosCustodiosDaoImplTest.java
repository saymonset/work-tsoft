// Cambio Multidivisas
package com.indeval.portalinternacional.persistence.dao.HorariosCustodios;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Divisa;
import com.indeval.portalinternacional.middleware.servicios.dto.HorariosCustodiosDto;
import com.indeval.portalinternacional.middleware.servicios.modelo.Custodio;
import com.indeval.portalinternacional.middleware.servicios.vo.CriteriosConsultaHorariosCustodiosVO;
import com.indeval.portalinternacional.persistence.dao.HorariosCustodiosDao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HorariosCustodiosDaoImplTest extends BaseDaoTestCase {
    HorariosCustodiosDao dao;

    protected void onSetUp() throws Exception {
        super.onSetUp();
        dao = (HorariosCustodiosDao) getBean("horariosCustodiosDao");
    }

    public void testFindAllByIdDivisa() {
        System.out.println("testFindAllByIdDivisa()");
        int expectedSize = 1;
        Integer idDivisa = 3;

        assertNotNull("HorariosCustodiosDao Nulo", dao);
        List<HorariosCustodiosDto> horariosCustodiosDtos = dao.findAllByIdDivisa(idDivisa);

        for (HorariosCustodiosDto horario : horariosCustodiosDtos) {
            System.out.println(horario.getHorarioInicial());
        }

        assertNotNull(horariosCustodiosDtos);
        assertEquals(expectedSize, horariosCustodiosDtos.size());
    }

    public void testGetHorarioInicialYHorarioFinalPorIdCustodio() {
        System.out.println("testGetHorarioInicialYHorarioFinalPorIdCustodio()");

        Integer idCustodio = 2;
        int expectedSize = 1;

        assertNotNull("HorariosCustodiosDao Nulo", dao);
        List<Object[]> resultados = dao.getHorarioInicialYHorarioFinalPorIdCustodio(idCustodio);

        assertNotNull(resultados);
        assertEquals(expectedSize, resultados.size());
    }

    public void testProbando() throws ParseException {
        Date fechaAlta = new Date();

        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        String horaAltaFormateada = formatoHora.format(fechaAlta);

        Date horaAlta = formatoHora.parse(horaAltaFormateada);

        // Cual es el custodio?
        Integer idCustodio = 9;
        List<Object[]> resultados = dao.getHorarioInicialYHorarioFinalPorIdCustodio(idCustodio);
        Map<String, String> rangoHorarios = new HashMap<>();
        rangoHorarios.put("horarioInicial", resultados.get(0)[0].toString());
        rangoHorarios.put("horarioFinal", resultados.get(0)[1].toString());

        String strHorarioInicial = rangoHorarios.get("horarioInicial");
        String strHorarioFinal = rangoHorarios.get("horarioFinal");

        Date horarioInicial = formatoHora.parse(strHorarioInicial);
        Date horarioFinal = formatoHora.parse(strHorarioFinal);

        System.out.println("Hora Alta: " + horaAltaFormateada);
        System.out.println("Horario Inicial: " + strHorarioInicial);
        System.out.println("Horario Final: " + strHorarioFinal);

        boolean esValido = horaAlta.after(horarioInicial) && horaAlta.before(horarioFinal);

        assertTrue("OOOOAAAA", esValido);
    }

    public void testUltimoID() {
        Integer id = dao.ultimoID();
        System.out.println(id);
        assertNotNull(id);
    }

    public void testSalvarHorarioCustodio() {
        HorariosCustodiosDto horariosCustodios = new HorariosCustodiosDto();
        horariosCustodios.setIdDivisa(3);
        horariosCustodios.setIdCustodio(13);
        horariosCustodios.setHorarioInicial("00:00:00");
        horariosCustodios.setHorarioFinal("00:59:59");
        horariosCustodios.setFechaCreacion(new Date());
        horariosCustodios.setFechaUltModificacion(horariosCustodios.getFechaCreacion());
        horariosCustodios.setEstatus(1);
        horariosCustodios.setCreador("indevaldrp");
        HorariosCustodiosDto nuevoHorariosCustodios = dao.salvarHorarioCustodio(horariosCustodios);
        System.out.println(nuevoHorariosCustodios);
        assertNotNull(horariosCustodios);
    }

    public void testObtenerDivisa() {
        Divisa divisa = dao.obtenerDivisa(3);
        System.out.println(divisa.getIdDivisa() + ".- " + divisa.getClaveAlfabetica() + " :: " + divisa.getDescripcion());
        assertNotNull(divisa);
    }

    public void testObtenerCustodio() {
        Custodio custodio = dao.obtenerCustodio(13);
        System.out.println(custodio.getId() + ".- " + custodio.getNombreCorto() + " :: " + custodio.getDescripcion());

    }

    public void testGetHorariosCustodios() {
        CriteriosConsultaHorariosCustodiosVO criteriosConsulta = new CriteriosConsultaHorariosCustodiosVO();
        PaginaVO paginaVO = new PaginaVO();
        paginaVO = dao.getHorariosCustodios(criteriosConsulta, paginaVO);
        System.out.println(paginaVO.getTotalRegistros());
        assertNotNull(paginaVO.getRegistros());
    }

    public void testUpdateHorariosCustodios() {
        Integer idHorarioCustodio = 1;
        Integer cambioEstado = 1;
        String usuarioChecker = "Jacito";
        HorariosCustodiosDto horariosCustodios = dao.updateHorariosCustodios(idHorarioCustodio, cambioEstado, usuarioChecker);
        assertNotNull(horariosCustodios);
    }
}

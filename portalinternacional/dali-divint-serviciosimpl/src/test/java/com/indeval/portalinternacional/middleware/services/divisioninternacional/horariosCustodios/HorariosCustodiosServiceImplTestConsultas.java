package com.indeval.portalinternacional.middleware.services.divisioninternacional.horariosCustodios;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.HorariosCustodiosService;
import com.indeval.portalinternacional.middleware.servicios.vo.CriteriosConsultaHorariosCustodiosVO;
import com.indeval.portalinternacional.middleware.servicios.vo.HorariosCustodiosVO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(MockitoJUnitRunner.class)
public class HorariosCustodiosServiceImplTestConsultas {

    @Mock
    HorariosCustodiosService serviceConsultaMock;

    public HorariosCustodiosServiceImplTestConsultas(HorariosCustodiosService serviceConsultaMock) {
        this.serviceConsultaMock = serviceConsultaMock;
    }

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);

        System.out.println("Mocks inicializados: " + serviceConsultaMock);
    }

    @Test
    public void getHorariosCustodiosconsultas() {
        getHorariosCustodiosSinCriterios();

        getHorariosCustodiosPorDivisa();
        getHorariosCustodiosPorDivisaCustodio();
        getHorariosCustodiosPorDivisaEstado();
        getHorariosCustodiosPorDivisaFecha();
        getHorariosCustodiosPorDivisaCustodioEstado();
        getHorariosCustodiosPorDivisaCustodioFecha();
        getHorariosCustodiosPorDivisaEstadoFecha();
        getHorariosCustodiosPorDivisaCustodioEstadoFecha();


        getHorariosCustodiosPorCustodio();
        getHorariosCustodiosPorCustodioEstado();
        getHorariosCustodiosPorCustodioFecha();
        getHorariosCustodiosPorCustodioEstadoFecha();

        getHorariosCustodiosPorEstado();
        getHorariosCustodiosPorEstadoFecha();

        getHorariosCustodiosPorFecha();
    }

    @Test
    public void getHorariosCustodiosSinCriterios() {
        System.out.println("Consulta Horarios Custodios: TODOS");
        CriteriosConsultaHorariosCustodiosVO criteriosConsulta =
                new CriteriosConsultaHorariosCustodiosVO();
        PaginaVO paginaVO = new PaginaVO();
        Mockito.when(serviceConsultaMock.getHorariosCustodios(criteriosConsulta, paginaVO)).
                thenReturn(Util.paginaVOconsultaEsperada(criteriosConsulta, paginaVO));
        paginaVO = serviceConsultaMock.getHorariosCustodios(criteriosConsulta, paginaVO);

        List<HorariosCustodiosVO> resultadosBusqueda = paginaVO.getRegistros();
        for (Object horariosCustodiosVO : resultadosBusqueda) {
            System.out.println(horariosCustodiosVO.toString());
        }

        assertEquals(Util.allHorariosCustodiosEsperados.size(), resultadosBusqueda.size());

    }

    @Test
    public void getHorariosCustodiosPorDivisa() {
        System.out.println("Consulta Horarios Custodios: Por Divisa");
        CriteriosConsultaHorariosCustodiosVO criteriosConsulta = Util.cargaCriterios(
                true, false, false, false);

        PaginaVO paginaVO = new PaginaVO();
        Mockito.when(serviceConsultaMock.getHorariosCustodios(criteriosConsulta, paginaVO)).
                thenReturn(Util.paginaVOconsultaEsperada(criteriosConsulta, paginaVO));
        paginaVO = serviceConsultaMock.getHorariosCustodios(criteriosConsulta, paginaVO);

        List<HorariosCustodiosVO> resultadosBusqueda = paginaVO.getRegistros();
        for (Object horariosCustodiosVO : resultadosBusqueda) {
            System.out.println(horariosCustodiosVO.toString());
        }

        assertTrue(resultadosBusqueda.size() > 0);
        assertTrue(resultadosBusqueda.size() <= Util.allHorariosCustodiosEsperados.size());

    }

    @Test
    public void getHorariosCustodiosPorDivisaCustodio() {
        System.out.println("Consulta Horarios Custodios: Por Divisa-Custodio");
        CriteriosConsultaHorariosCustodiosVO criteriosConsulta = Util.cargaCriterios(
                true, true, false, false);

        PaginaVO paginaVO = new PaginaVO();
        Mockito.when(serviceConsultaMock.getHorariosCustodios(criteriosConsulta, paginaVO)).
                thenReturn(Util.paginaVOconsultaEsperada(criteriosConsulta, paginaVO));
        paginaVO = serviceConsultaMock.getHorariosCustodios(criteriosConsulta, paginaVO);

        List<HorariosCustodiosVO> resultadosBusqueda = paginaVO.getRegistros();
        for (Object horariosCustodiosVO : resultadosBusqueda) {
            System.out.println(horariosCustodiosVO.toString());
        }

        assertTrue(resultadosBusqueda.size() > 0);
        assertTrue(resultadosBusqueda.size() <= Util.allHorariosCustodiosEsperados.size());

    }

    @Test
    public void getHorariosCustodiosPorDivisaEstado() {
        System.out.println("Consulta Horarios Custodios: Por Divisa-Estado");
        CriteriosConsultaHorariosCustodiosVO criteriosConsulta = Util.cargaCriterios(
                true, false, true, false);

        PaginaVO paginaVO = new PaginaVO();
        Mockito.when(serviceConsultaMock.getHorariosCustodios(criteriosConsulta, paginaVO)).
                thenReturn(Util.paginaVOconsultaEsperada(criteriosConsulta, paginaVO));
        paginaVO = serviceConsultaMock.getHorariosCustodios(criteriosConsulta, paginaVO);

        List<HorariosCustodiosVO> resultadosBusqueda = paginaVO.getRegistros();
        for (Object horariosCustodiosVO : resultadosBusqueda) {
            System.out.println(horariosCustodiosVO.toString());
        }

        assertTrue(resultadosBusqueda.size() > 0);
        assertTrue(resultadosBusqueda.size() <= Util.allHorariosCustodiosEsperados.size());

    }

    @Test
    public void getHorariosCustodiosPorDivisaFecha() {
        System.out.println("Consulta Horarios Custodios: Por Divisa-Estado");
        CriteriosConsultaHorariosCustodiosVO criteriosConsulta = Util.cargaCriterios(
                true, false, false, true);

        PaginaVO paginaVO = new PaginaVO();
        Mockito.when(serviceConsultaMock.getHorariosCustodios(criteriosConsulta, paginaVO)).
                thenReturn(Util.paginaVOconsultaEsperada(criteriosConsulta, paginaVO));
        paginaVO = serviceConsultaMock.getHorariosCustodios(criteriosConsulta, paginaVO);

        List<HorariosCustodiosVO> resultadosBusqueda = paginaVO.getRegistros();
        for (Object horariosCustodiosVO : resultadosBusqueda) {
            System.out.println(horariosCustodiosVO.toString());
        }

        assertTrue(resultadosBusqueda.size() > 0);
        assertTrue(resultadosBusqueda.size() <= Util.allHorariosCustodiosEsperados.size());

    }

    @Test
    public void getHorariosCustodiosPorDivisaCustodioEstado() {
        System.out.println("Consulta Horarios Custodios: Por Divisa-Custodio-Estado");
        CriteriosConsultaHorariosCustodiosVO criteriosConsulta = Util.cargaCriterios(
                true, true, true, false);

        PaginaVO paginaVO = new PaginaVO();
        Mockito.when(serviceConsultaMock.getHorariosCustodios(criteriosConsulta, paginaVO)).
                thenReturn(Util.paginaVOconsultaEsperada(criteriosConsulta, paginaVO));
        paginaVO = serviceConsultaMock.getHorariosCustodios(criteriosConsulta, paginaVO);

        List<HorariosCustodiosVO> resultadosBusqueda = paginaVO.getRegistros();
        for (Object horariosCustodiosVO : resultadosBusqueda) {
            System.out.println(horariosCustodiosVO.toString());
        }

        assertTrue(resultadosBusqueda.size() > 0);
        assertTrue(resultadosBusqueda.size() <= Util.allHorariosCustodiosEsperados.size());

    }

    @Test
    public void getHorariosCustodiosPorDivisaCustodioFecha() {
        System.out.println("Consulta Horarios Custodios: Por Divisa-Custodio-Fecha");
        CriteriosConsultaHorariosCustodiosVO criteriosConsulta = Util.cargaCriterios(
                true, true, false, true);

        PaginaVO paginaVO = new PaginaVO();
        Mockito.when(serviceConsultaMock.getHorariosCustodios(criteriosConsulta, paginaVO)).
                thenReturn(Util.paginaVOconsultaEsperada(criteriosConsulta, paginaVO));
        paginaVO = serviceConsultaMock.getHorariosCustodios(criteriosConsulta, paginaVO);

        List<HorariosCustodiosVO> resultadosBusqueda = paginaVO.getRegistros();
        for (Object horariosCustodiosVO : resultadosBusqueda) {
            System.out.println(horariosCustodiosVO.toString());
        }

        assertTrue(resultadosBusqueda.size() > 0);
        assertTrue(resultadosBusqueda.size() <= Util.allHorariosCustodiosEsperados.size());

    }

    @Test
    public void getHorariosCustodiosPorDivisaCustodioEstadoFecha() {
        System.out.println("Consulta Horarios Custodios: Por Divisa-Custodio-Estado-Fecha");
        CriteriosConsultaHorariosCustodiosVO criteriosConsulta = Util.cargaCriterios(
                true, true, true, true);

        PaginaVO paginaVO = new PaginaVO();
        Mockito.when(serviceConsultaMock.getHorariosCustodios(criteriosConsulta, paginaVO)).
                thenReturn(Util.paginaVOconsultaEsperada(criteriosConsulta, paginaVO));
        paginaVO = serviceConsultaMock.getHorariosCustodios(criteriosConsulta, paginaVO);

        List<HorariosCustodiosVO> resultadosBusqueda = paginaVO.getRegistros();
        for (Object horariosCustodiosVO : resultadosBusqueda) {
            System.out.println(horariosCustodiosVO.toString());
        }

        assertTrue(resultadosBusqueda.size() > 0);
        assertTrue(resultadosBusqueda.size() <= Util.allHorariosCustodiosEsperados.size());

    }

    public void getHorariosCustodiosPorDivisaEstadoFecha() {
        System.out.println("Consulta Horarios Custodios: Por Divisa-Custodio");
        CriteriosConsultaHorariosCustodiosVO criteriosConsulta = Util.cargaCriterios(
                true, false, true, true);

        PaginaVO paginaVO = new PaginaVO();
        Mockito.when(serviceConsultaMock.getHorariosCustodios(criteriosConsulta, paginaVO)).
                thenReturn(Util.paginaVOconsultaEsperada(criteriosConsulta, paginaVO));
        paginaVO = serviceConsultaMock.getHorariosCustodios(criteriosConsulta, paginaVO);

        List<HorariosCustodiosVO> resultadosBusqueda = paginaVO.getRegistros();
        for (Object horariosCustodiosVO : resultadosBusqueda) {
            System.out.println(horariosCustodiosVO.toString());
        }

        assertTrue(resultadosBusqueda.size() > 0);
        assertTrue(resultadosBusqueda.size() <= Util.allHorariosCustodiosEsperados.size());

    }
    @Test
    public void getHorariosCustodiosPorCustodio() {
        System.out.println("Consulta Horarios Custodios: Por Custodio");
        CriteriosConsultaHorariosCustodiosVO criteriosConsulta = Util.cargaCriterios(
                false, true, false, false);

        PaginaVO paginaVO = new PaginaVO();
        Mockito.when(serviceConsultaMock.getHorariosCustodios(criteriosConsulta, paginaVO)).
                thenReturn(Util.paginaVOconsultaEsperada(criteriosConsulta, paginaVO));
        paginaVO = serviceConsultaMock.getHorariosCustodios(criteriosConsulta, paginaVO);

        List<HorariosCustodiosVO> resultadosBusqueda = paginaVO.getRegistros();
        for (Object horariosCustodiosVO : resultadosBusqueda) {
            System.out.println(horariosCustodiosVO.toString());
        }

        assertTrue(resultadosBusqueda.size() > 0);
        assertTrue(resultadosBusqueda.size() <= Util.allHorariosCustodiosEsperados.size());

    }

    @Test
    public void getHorariosCustodiosPorCustodioEstado() {
        System.out.println("Consulta Horarios Custodios: Por Custodio-Estado");
        CriteriosConsultaHorariosCustodiosVO criteriosConsulta = Util.cargaCriterios(
                false, true, true, false);

        PaginaVO paginaVO = new PaginaVO();
        Mockito.when(serviceConsultaMock.getHorariosCustodios(criteriosConsulta, paginaVO)).
                thenReturn(Util.paginaVOconsultaEsperada(criteriosConsulta, paginaVO));
        paginaVO = serviceConsultaMock.getHorariosCustodios(criteriosConsulta, paginaVO);

        List<HorariosCustodiosVO> resultadosBusqueda = paginaVO.getRegistros();
        for (Object horariosCustodiosVO : resultadosBusqueda) {
            System.out.println(horariosCustodiosVO.toString());
        }

        assertTrue(resultadosBusqueda.size() > 0);
        assertTrue(resultadosBusqueda.size() <= Util.allHorariosCustodiosEsperados.size());

    }

    @Test
    public void getHorariosCustodiosPorCustodioFecha() {
        System.out.println("Consulta Horarios Custodios: Por Custodio");
        CriteriosConsultaHorariosCustodiosVO criteriosConsulta = Util.cargaCriterios(
                false, true, false, true);

        PaginaVO paginaVO = new PaginaVO();
        Mockito.when(serviceConsultaMock.getHorariosCustodios(criteriosConsulta, paginaVO)).
                thenReturn(Util.paginaVOconsultaEsperada(criteriosConsulta, paginaVO));
        paginaVO = serviceConsultaMock.getHorariosCustodios(criteriosConsulta, paginaVO);

        List<HorariosCustodiosVO> resultadosBusqueda = paginaVO.getRegistros();
        for (Object horariosCustodiosVO : resultadosBusqueda) {
            System.out.println(horariosCustodiosVO.toString());
        }

        assertTrue(resultadosBusqueda.size() > 0);
        assertTrue(resultadosBusqueda.size() <= Util.allHorariosCustodiosEsperados.size());

    }

    @Test
    public void getHorariosCustodiosPorCustodioEstadoFecha() {
        System.out.println("Consulta Horarios Custodios: Por Custodio-Estado-Fecha");
        CriteriosConsultaHorariosCustodiosVO criteriosConsulta = Util.cargaCriterios(
                false, true, true, true);

        PaginaVO paginaVO = new PaginaVO();
        Mockito.when(serviceConsultaMock.getHorariosCustodios(criteriosConsulta, paginaVO)).
                thenReturn(Util.paginaVOconsultaEsperada(criteriosConsulta, paginaVO));
        paginaVO = serviceConsultaMock.getHorariosCustodios(criteriosConsulta, paginaVO);

        List<HorariosCustodiosVO> resultadosBusqueda = paginaVO.getRegistros();
        for (Object horariosCustodiosVO : resultadosBusqueda) {
            System.out.println(horariosCustodiosVO.toString());
        }

        assertTrue(resultadosBusqueda.size() > 0);
        assertTrue(resultadosBusqueda.size() <= Util.allHorariosCustodiosEsperados.size());

    }

    @Test
    public void getHorariosCustodiosPorEstado() {
        System.out.println("Consulta Horarios Custodios: Por Estado");
        CriteriosConsultaHorariosCustodiosVO criteriosConsulta = Util.cargaCriterios(
                false, false, true, false);

        PaginaVO paginaVO = new PaginaVO();
        Mockito.when(serviceConsultaMock.getHorariosCustodios(criteriosConsulta, paginaVO)).
                thenReturn(Util.paginaVOconsultaEsperada(criteriosConsulta, paginaVO));
        paginaVO = serviceConsultaMock.getHorariosCustodios(criteriosConsulta, paginaVO);

        List<HorariosCustodiosVO> resultadosBusqueda = paginaVO.getRegistros();
        for (Object horariosCustodiosVO : resultadosBusqueda) {
            System.out.println(horariosCustodiosVO.toString());
        }

        assertTrue(resultadosBusqueda.size() > 0);
        assertTrue(resultadosBusqueda.size() <= Util.allHorariosCustodiosEsperados.size());

    }

    @Test
    public void getHorariosCustodiosPorEstadoFecha() {
        System.out.println("Consulta Horarios Custodios: Por Estado-Fecha");
        CriteriosConsultaHorariosCustodiosVO criteriosConsulta = Util.cargaCriterios(
                false, false, true, true);

        PaginaVO paginaVO = new PaginaVO();
        Mockito.when(serviceConsultaMock.getHorariosCustodios(criteriosConsulta, paginaVO)).
                thenReturn(Util.paginaVOconsultaEsperada(criteriosConsulta, paginaVO));
        paginaVO = serviceConsultaMock.getHorariosCustodios(criteriosConsulta, paginaVO);

        List<HorariosCustodiosVO> resultadosBusqueda = paginaVO.getRegistros();
        for (Object horariosCustodiosVO : resultadosBusqueda) {
            System.out.println(horariosCustodiosVO.toString());
        }

        assertTrue(resultadosBusqueda.size() > 0);
        assertTrue(resultadosBusqueda.size() <= Util.allHorariosCustodiosEsperados.size());

    }
    @Test
    public void getHorariosCustodiosPorFecha() {
        System.out.println("Consulta Horarios Custodios: Por Fecha");
        CriteriosConsultaHorariosCustodiosVO criteriosConsulta = Util.cargaCriterios(
                false, false, false, true);

        PaginaVO paginaVO = new PaginaVO();
        Mockito.when(serviceConsultaMock.getHorariosCustodios(criteriosConsulta, paginaVO)).
                thenReturn(Util.paginaVOconsultaEsperada(criteriosConsulta, paginaVO));
        paginaVO = serviceConsultaMock.getHorariosCustodios(criteriosConsulta, paginaVO);

        List<HorariosCustodiosVO> resultadosBusqueda = paginaVO.getRegistros();
        for (Object horariosCustodiosVO : resultadosBusqueda) {
            System.out.println(horariosCustodiosVO.toString());
        }

        assertTrue(resultadosBusqueda.size() > 0);
        assertTrue(resultadosBusqueda.size() <= Util.allHorariosCustodiosEsperados.size());

    }

}






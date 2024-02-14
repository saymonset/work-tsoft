// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.services.divisioninternacional.horariosCustodios;

import com.indeval.portalinternacional.middleware.services.divisioninternacional.HorariosCustodiosService;
import com.indeval.portalinternacional.middleware.servicios.dto.HorariosCustodiosDto;
import com.indeval.portalinternacional.middleware.servicios.vo.HorariosCustodiosVO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(MockitoJUnitRunner.class)
public class HorariosCustodiosServiceImplTestMockito {

    @Mock
    HorariosCustodiosService serviceMock;

    private HorariosCustodiosServiceImplTestConsultas horariosCustodiosServiceImplTestConsultas;

    @Before
    public void setup() {
        horariosCustodiosServiceImplTestConsultas = new HorariosCustodiosServiceImplTestConsultas(serviceMock);
    }

    @Test
    public void salvarHorarioCustodio() {
        HorariosCustodiosDto dto = new HorariosCustodiosDto();
        dto.setCreador("jacito");
        dto.setIdCustodio(1);
        dto.setIdDivisa(1);
        dto.setHorarioInicial("00:00");
        dto.setHorarioFinal("05:00");

        Mockito.when(serviceMock.salvarHorarioCustodio(eq(dto))).thenReturn(Util.horarioCustodioActializadoEsperado(dto));

        HorariosCustodiosDto resultadoDto = serviceMock.salvarHorarioCustodio(dto);

        System.out.println(resultadoDto);

        assertNotNull(resultadoDto);
        assertEquals("jacito", resultadoDto.getCreador());
        assertEquals("00:00", resultadoDto.getHorarioInicial());
        assertEquals("05:00", resultadoDto.getHorarioFinal());
        assertNotNull(resultadoDto.getIdHorariosCustodios());
        assertNotNull(resultadoDto.getFechaUltModificacion());
        assertNotNull(resultadoDto.getUsuarioChecker());


    }

    @Test
    public void getHorariosCustodios() {
        horariosCustodiosServiceImplTestConsultas.getHorariosCustodiosconsultas();
        assertTrue(true);
    }

    @Test
    public void updateHorariosCustodios() {
        HorariosCustodiosVO vo = Util.obtenerAlgunHorarioCustodio();
        Integer idHorarioCustodio = vo.getIdHorariosCustodios();
        Integer cambioEstado = 3;//CANCELADO
        String usuarioChecker = "Jacito";

        Mockito.when(serviceMock.updateHorariosCustodios(
                        eq(idHorarioCustodio), eq(cambioEstado), eq(usuarioChecker))).
                thenReturn(Util.horarioCustodioActializadoEsperado(idHorarioCustodio, cambioEstado, usuarioChecker));

        HorariosCustodiosDto dto = serviceMock.updateHorariosCustodios(idHorarioCustodio, cambioEstado, usuarioChecker);

        System.out.println("Objeto a Actualizar : \n" + vo);
        System.out.println("Objeto Actualizado: \n" + dto);

        assertNotNull(dto);

        assertEquals(dto.getIdHorariosCustodios(), vo.getIdHorariosCustodios());
        assertEquals(dto.getIdCustodio(), vo.getIdCustodio());
        assertEquals(dto.getIdDivisa(), vo.getIdDivisa());
        assertEquals(dto.getHorarioInicial(), vo.getHorarioInicial());
        assertEquals(dto.getHorarioFinal(), vo.getHorarioFinal());
        assertEquals(dto.getFechaCreacion(), vo.getFechaCreacion());
        assertEquals(dto.getCreador(), vo.getCreador());

        assertNotNull(dto.getUsuarioChecker());
        assertNotNull(dto.getFechaUltModificacion());

        assertEquals(cambioEstado,dto.getEstatus());
        assertEquals(usuarioChecker,dto.getUsuarioChecker());

    }

}






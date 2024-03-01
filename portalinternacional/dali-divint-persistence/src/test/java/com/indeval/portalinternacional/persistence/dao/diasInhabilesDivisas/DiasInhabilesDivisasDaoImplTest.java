// Cambio Multidivisas
package com.indeval.portalinternacional.persistence.dao.diasInhabilesDivisas;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.dto.diasIhabilesDivisas.DiasInhabilesDivisasDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.DiasInhabilesDivisas;
import com.indeval.portalinternacional.persistence.dao.DiasInhabilesDivisasDao;
import junit.framework.TestCase;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.indeval.portalinternacional.middleware.servicios.constantes.EstatusDB.REGISTRADO;

public class DiasInhabilesDivisasDaoImplTest extends BaseDaoTestCase {
    DiasInhabilesDivisasDao dao;

    protected void onSetUp() throws Exception {
        super.onSetUp();
        dao = (DiasInhabilesDivisasDao) getBean("diasInhabilesDivisasDao");
    }

    public void testGetDiasInhabilesByIdDivisa() {
        System.out.println("testGetDiasInhabilesByIdDivisa()");
        Long idDivisa = 3L;

        assertNotNull(dao);
        List<Date> diasInhabiles = dao.getDiasInhabilesByIdDivisa(idDivisa);

        for (Date diaInhabil : diasInhabiles) {
            System.out.println("Dia inhabil> " + diaInhabil);
        }

        TestCase.assertNotNull(diasInhabiles);
    }

    public void testEsDiaHabilDivisa() {
        Long idDivisa = 3L;
        List<Date> diasInhabiles = dao.getDiasInhabilesByIdDivisa(idDivisa);
        boolean res = true;
        Date fechaAlta = new Date();

        Calendar diaAlta = Calendar.getInstance();
        diaAlta.setTime(fechaAlta);

        for (Date diaDate : diasInhabiles) {
            Calendar dia = Calendar.getInstance();
            dia.setTime(diaDate);

            if (sonFechasIguales(diaAlta, dia)) {
                res = false;
                break;
            }
        }

        assertTrue(res);
    }

    public void testGetAniosDisponibles() {
        List<Integer> aniosDisponibles = dao.getAniosDisponibles();
        System.out.println(aniosDisponibles);
        assertNotNull(aniosDisponibles);
    }

    private static boolean sonFechasIguales(Calendar cal1, Calendar cal2) {
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }

    public void testGetDiasInhabilesByIdHistorico() {
        Long idHistorico = 1L;
        PaginaVO paginaVO = new PaginaVO();
        paginaVO.setRegistrosXPag(100);;
        paginaVO = dao.getDiasInhabilesByIdHistorico(idHistorico, paginaVO);
        for (Object diaInhabilDivisa : paginaVO.getRegistros()) {
            System.out.println((DiasInhabilesDivisasDTO) diaInhabilDivisa);
        }
        System.out.println("**************************************************************************************");
        paginaVO.setRegistrosXPag(PaginaVO.TODOS);
        paginaVO = dao.getDiasInhabilesByIdHistorico(idHistorico, paginaVO);
        for (Object diaInhabilDivisa : paginaVO.getRegistros()) {
            System.out.println((DiasInhabilesDivisasDTO) diaInhabilDivisa);
        }
    }

    public void testSaveDiasInhabilesDivisas() {
        DiasInhabilesDivisas diaInhabilDivisa = new DiasInhabilesDivisas();
        diaInhabilDivisa.setDiaInhabil(new Date());
        diaInhabilDivisa.setIdDivisa(3L);
        diaInhabilDivisa.setCreador("jacito");
        diaInhabilDivisa.setFechaCreacion(new Date());
        diaInhabilDivisa.setFechaUltModificacion(new Date());
        diaInhabilDivisa.setEstatus(REGISTRADO.getCodigo());
        diaInhabilDivisa.setIdHistoricoDiasInhabilesDivisas(3L);
        dao.saveDiasInhabilesDivisas(diaInhabilDivisa);
        assertTrue(true);
    }

    public void testUpdateDiaInhabilDivisas() {
        Long id = 1L;
        Integer cambioEstado = 5;
        dao.updateDiaInhabilDivisas(id, cambioEstado);
        assertTrue(true);
    }

}
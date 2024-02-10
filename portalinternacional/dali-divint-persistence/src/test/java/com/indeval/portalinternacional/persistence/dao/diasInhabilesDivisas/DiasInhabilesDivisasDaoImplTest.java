// Cambio Multidivisas
package com.indeval.portalinternacional.persistence.dao.diasInhabilesDivisas;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.persistence.dao.DiasInhabilesDivisasDao;
import junit.framework.TestCase;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

        for (Date diaInhabil: diasInhabiles) {
            System.out.println("Dia inhabil> " + diaInhabil);
        }

        TestCase.assertNotNull(diasInhabiles);
    }

    public void testEsDiaHabilDivisa () {
        Long idDivisa = 3L;
        List<Date> diasInhabiles = dao.getDiasInhabilesByIdDivisa(idDivisa);
        boolean res = true;
        Date fechaAlta = new Date();

        Calendar diaAlta = Calendar.getInstance();
        diaAlta.setTime(fechaAlta);

        for(Date diaDate : diasInhabiles) {
            Calendar dia = Calendar.getInstance();
            dia.setTime(diaDate);

            if(sonFechasIguales(diaAlta, dia)) {
                res = false;
                break;
            }
        }

        assertTrue(res);
    }

    private static boolean sonFechasIguales(Calendar cal1, Calendar cal2) {
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }
}
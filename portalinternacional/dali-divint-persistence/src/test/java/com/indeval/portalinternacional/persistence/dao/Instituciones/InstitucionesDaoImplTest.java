// Cambio Multidivisas
package com.indeval.portalinternacional.persistence.dao.Instituciones;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.middleware.servicios.dto.InstitucionWebDTO;
import com.indeval.portalinternacional.persistence.dao.CuentaDaliDAO;
import com.indeval.portalinternacional.persistence.dao.InstitucionesDao;
import junit.framework.TestCase;

public class InstitucionesDaoImplTest extends BaseDaoTestCase {
    InstitucionesDao dao;

    protected void onSetUp() throws Exception {
        super.onSetUp();
        dao = (InstitucionesDao) getBean("institucionesDao");
    }

    public void testBuscarInstitucionPorClaveYFolio() {
        System.out.println("testBuscarInstitucionPorClaveYFolio()");

        String idFolio = "1009";
        String expectedRazonSocial = "VALORES MEXICANOS, CASA DE BOLSA, S. A. DE C.V.";
        String expectedNombre = "CBVALMEX";

        assertNotNull("InstitucionesDao Nulo", dao);
        InstitucionWebDTO institucion = dao.buscarInstitucionPorClaveYFolio(idFolio);

        assertNotNull(institucion);
        assertEquals(expectedRazonSocial, institucion.getRazonSocial());
        assertEquals(expectedNombre, institucion.getNombre());
    }
}
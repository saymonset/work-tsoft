package com.indeval.portalinternacional.persistence.dao.impl;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.middleware.servicios.dto.diasIhabilesDivisas.HistoricoDiasInhabilesDivisasDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.HistoricoDiasInhabilesDivisas;
import com.indeval.portalinternacional.middleware.servicios.vo.UsuarioVO;
import com.indeval.portalinternacional.persistence.dao.HistoricoDiasInhabilesDivisasDao;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.indeval.portalinternacional.middleware.servicios.constantes.EstatusDB.REGISTRADO;

public class HistoricoDiasInhabilesDivisasDaoImplTest extends BaseDaoTestCase {

    HistoricoDiasInhabilesDivisasDao dao;

    protected void onSetUp() throws Exception {
        super.onSetUp();
        dao = (HistoricoDiasInhabilesDivisasDao) getBean("historicoDiasInhabilesDivisasDao");
    }

    public void testObtenerTodoPorUsuario() {
        System.out.println("*************************************************************");
        List<HistoricoDiasInhabilesDivisas> todos = dao.obtenerTodo(null, null);
        for (HistoricoDiasInhabilesDivisas resultado : todos) {
            System.out.println(resultado);
        }
        assertNotNull(todos);
        System.out.println("*************************************************************");
        String usuario = "indevaldrp";
        List<HistoricoDiasInhabilesDivisas> porUsuario = dao.obtenerTodo(usuario, null);
        for (HistoricoDiasInhabilesDivisas resultado : porUsuario) {
            System.out.println(resultado);
        }
        assertNotNull(porUsuario);
        assertTrue(todos.size() >= porUsuario.size());
        System.out.println("*************************************************************");
        Integer anio = 2024;
        List<HistoricoDiasInhabilesDivisas> porAnio = dao.obtenerTodo(null, anio);
        for (HistoricoDiasInhabilesDivisas resultado : porAnio) {
            System.out.println(resultado);
        }
        assertNotNull(porAnio);
        assertTrue(todos.size() >= porAnio.size());
        System.out.println("*************************************************************");
        List<HistoricoDiasInhabilesDivisas> porUsuarioAnio = dao.obtenerTodo(usuario, anio);
        for (HistoricoDiasInhabilesDivisas resultado : porUsuarioAnio) {
            System.out.println(resultado);
        }
        assertNotNull(porUsuarioAnio);
        assertTrue(todos.size() >= porUsuarioAnio.size());

    }

    public void testUltimoID() {
        Long ultimoID = dao.ultimoID();
        System.out.println("Ultimo ID :: " + ultimoID);
        assertNotNull(ultimoID);
    }

    public void testAddHistorico() {
        Long ultimoID = dao.ultimoID();
        HistoricoDiasInhabilesDivisas historicoDiasInhabilesDivisas = new HistoricoDiasInhabilesDivisas();
        historicoDiasInhabilesDivisas.setIdHistorico(ultimoID + 1);
        historicoDiasInhabilesDivisas.setCreador("jacito");
        Date ahora = new Date();
        historicoDiasInhabilesDivisas.setFechaCreacion(ahora);
        historicoDiasInhabilesDivisas.setFechaUltModificacion(ahora);
        historicoDiasInhabilesDivisas.setEstatus(REGISTRADO.getCodigo());
        historicoDiasInhabilesDivisas.setNombreArchivo("PruebasJacito.csv");
        historicoDiasInhabilesDivisas.setRegistrosTotal(1);
        historicoDiasInhabilesDivisas.setRegistrosCorrectos(1);
        historicoDiasInhabilesDivisas.setRegistrosError(0);
        dao.save(historicoDiasInhabilesDivisas);
        dao.delete(historicoDiasInhabilesDivisas);
        assertTrue(true);
    }

    public void testUpdateHistorico() {
        Long idHorarioCustodio = 2L;
        Integer cambioEstado = 3;
        String usuarioChecker = "Jacito";
        dao.updateHistorico(idHorarioCustodio, cambioEstado, usuarioChecker);
        assertTrue(true);
    }
}
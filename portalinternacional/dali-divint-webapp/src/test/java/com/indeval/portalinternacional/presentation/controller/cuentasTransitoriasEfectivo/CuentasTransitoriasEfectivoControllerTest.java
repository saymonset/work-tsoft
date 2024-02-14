package com.indeval.portalinternacional.presentation.controller.cuentasTransitoriasEfectivo;

import com.indeval.portalinternacional.middleware.services.divisioninternacional.cuentasTransitoriasEfectivo.CuentasTransitoriasEfectivoService;
import com.indeval.portalinternacional.presentation.test.BaseTestCase;

import javax.faces.context.FacesContext;
import java.util.Random;

import static com.indeval.portalinternacional.presentation.controller.cuentasTransitoriasEfectivo.Util.*;

public class CuentasTransitoriasEfectivoControllerTest extends BaseTestCase {

    private CuentasTransitoriasEfectivoService service;
    private CuentasTransitoriasEfectivoController controller;


    @Override
    protected void onSetUp() throws Exception {
        super.onSetUp();
        service = (CuentasTransitoriasEfectivoService) applicationContext.getBean("cuentasTransitoriasEfectivoService");
        controller = new CuentasTransitoriasEfectivoController();
        controller.setCuentasTransitoriasEfectivoService(service);
    }

    public void testGetInit() {
        controller.getInit();
        assertTrue(true);
    }

    public void testGetInitDetalle() {
        FacesContext.getCurrentInstance().getExternalContext().
                getRequestParameterMap().put("referencia", FOLIO_RELACIONADO);
        controller.getInitDetalle();
        assertTrue(true);
    }

    public void testGetInitDetalleIso() {
        FacesContext.getCurrentInstance().getExternalContext().
                getRequestParameterMap().put("idRegistro", ID_REGISTRO);
        controller.getInitDetalleIso();
        assertTrue(true);
    }

    public void testConsultarAction() {
        int i = 0;
        for (DataFaces datos : COMBINACIONES) {
            i++;
            System.out.println("      Prueba combinacion [" + i + "] :: " + datos.filtro);
            controller.setIdDivisaConsulta(datos.idDivisaConsulta);
            controller.setIdCustodioConsulta(datos.idCustodioConsulta);
            controller.setFechaInicio(datos.fechaInicio);
            controller.setFechaFin(datos.fechaFin);
            controller.setReferenciaBuscar_1(datos.folioReferenciado);
            controller.consultarAction();
        }
        assertTrue(true);
    }

    public void testAsignarAction() {
        controller.ejecutarConsulta();
        controller.setReferenciaAsignar(FOLIO_RELACIONADO);

        Random r = new Random();
        for (int i = 0; i < controller.getRegistrosNoReferenciados().size(); i++) {
            if (r.nextBoolean()) {
                controller.getRegistrosNoReferenciados().get(i).setRelacionar(true);
            }
        }

        controller.asignarAction();
        assertTrue(true);
    }

    public void testEjecutarConsulta() {
        int i = 0;
        for (DataFaces datos : COMBINACIONES) {
            i++;
            System.out.println("      Prueba combinacion [" + i + "] :: " + datos.filtro);
            controller.setIdDivisaConsulta(datos.idDivisaConsulta);
            controller.setIdCustodioConsulta(datos.idCustodioConsulta);
            controller.setFechaInicio(datos.fechaInicio);
            controller.setFechaFin(datos.fechaFin);
            controller.setReferenciaBuscar_1(datos.folioReferenciado);
            controller.ejecutarConsulta();
        }
        assertTrue(true);
    }

}
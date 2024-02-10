// Cambio Multidivisas
package com.indeval.portalinternacional.presentation.controller;

import com.indeval.portalinternacional.common.util.CatalogosUtil;
import com.indeval.portalinternacional.common.util.ConsultaCatalogosFacade;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.SicService;
import com.indeval.portalinternacional.presentation.test.BaseTestCase;
//import com.indeval.sidv.bitacora.middleware.service.BitacoraService;
import com.indeval.sidv.bitacoraauditoria.middleware.service.BitacoraService;

public class MovimientosEfectivoControllerTest extends BaseTestCase {
    private MovimientosEfectivoController controller;
    private BitacoraService bitacoraService;
    private SicService sicService;
    private ConsultaCatalogosFacade catalogosFacade;
    private CatalogosUtil catalogosUtil;

    @Override
    protected void onSetUp() throws Exception {
        super.onSetUp();
        bitacoraService = (BitacoraService) applicationContext.getBean("bitacoraService");
        sicService = (SicService) applicationContext.getBean("sicService");
        catalogosFacade = (ConsultaCatalogosFacade) applicationContext.getBean("catalogosFacade");
        catalogosUtil = (CatalogosUtil) applicationContext.getBean("catalogosUtil");

        controller = new MovimientosEfectivoController();

        controller.setBitacoraService(bitacoraService);
        controller.setSicService(sicService);
        controller.setCatalogosFacade(catalogosFacade);
        controller.setCatalogosUtil(catalogosUtil);
    }

    public void testGetInicializar() {
        System.out.println("testGetInicializar()");

        assertNotNull("MovimientosEfectivoController Nulo", controller);
        controller.getInicializar();

        assertTrue(true);
    }

    public void testRealizarDeposito() {
        System.out.println("testEnviarDeposito()");

        try {
            assertNotNull("MovimientosEfectivoController Nulo", controller);

            // Creo que acá falta setear institucion, y tipo de movimiento

            controller.getInicializar();

            // Será con esto?
       /* FacesContext.getCurrentInstance().getExternalContext().
                getRequestParameterMap().put("idRegistro", ID_REGISTRO);*/

            controller.getEfectivoInternacionalVO().setParticipante("1009");
            controller.validarParticipante(null);

            controller.getEfectivoInternacionalVO().getDivisa().setIdString("USD");
            controller.seleccionarDivisa(null);

            controller.getEfectivoInternacionalVO().getBoveda().setIdBovedaStr("EUROCLE???");
            controller.seleccionarBoveda(null);

            controller.getEfectivoInternacionalVO().setImporteTraspasar(456D);
            controller.onChangeImporte(null);

            controller.getEfectivoInternacionalVO().setReferenciaRelacionada("Ref desde Test");

            controller.enviarOperacion(null);
            assertTrue(true);
        } catch (Exception e){
            System.out.println("ERROR: " + e.getMessage());
            fail();
        }
    }

    public void testRealizarRetiro(){
        System.out.println("testRealizarRetiro()");

        try {
            assertNotNull("MovimientosEfectivoController Nulo", controller);

            controller.getInicializar();

            controller.getEfectivoInternacionalVO().setParticipante("1009");
            controller.validarParticipante(null);

            controller.getEfectivoInternacionalVO().getDivisa().setIdString("USD");
            controller.seleccionarDivisa(null);

            controller.getEfectivoInternacionalVO().getBoveda().setIdBovedaStr("EUROCLE???");
            controller.seleccionarBoveda(null);

            controller.getEfectivoInternacionalVO().setImporteTraspasar(789D);
            controller.onChangeImporte(null);

            controller.getEfectivoInternacionalVO().setReferenciaNumerica("1234567");
            controller.getEfectivoInternacionalVO().setReferenciaRelacionada("Ref desde Test");
            controller.getEfectivoInternacionalVO().setNotasComentarios("Esto es una nota desde un Test del controller");

            controller.enviarOperacion(null);
            assertTrue(true);
        } catch (Exception e){
            System.out.println("ERROR: " + e.getMessage());
            fail();
        }
    }
}
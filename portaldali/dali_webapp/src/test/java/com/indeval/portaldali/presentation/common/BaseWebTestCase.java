package com.indeval.portaldali.presentation.common;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;


public class BaseWebTestCase extends AbstractDependencyInjectionSpringContextTests {

    public void onSetup() {

    }

    /**
     * Metodo para obtener el bean.
     * 
     * @param id
     * @return Object
     */
    protected Object getBean(String id) {
        System.out.println("BaseWebTestCase.getBean--->" + id);
        // System.out.println("BaseWebTestCase.getBean--->" + applicationContext.getBean(id));
        return applicationContext.getBean(id);
    }

    /**
     * 
     */
    public void testDummy() {
        // Manten feliz al JUnit.
    }

    /**
     * metodo para obtener el nombre de los applicationContext a utilizar.
     * 
     * @return String[]
     */
    protected String[] getConfigLocations() {
        return new String[] { 
        		 "classpath:com/indeval/dali/conf/applicationContext-validacionesComunesCO.xml",
                "classpath:com/indeval/dali/conf/applicationContext-reportoNominal.xml",
                "classpath:com/indeval/dali/conf/applicationContext-traspasoLibrePago.xml",
                "classpath:com/indeval/dali/conf/applicationContext-fondeoCtaPropia.xml",
                "classpath:com/indeval/dali/conf/applicationContext-validacion-capturaOperaciones-venta.xml",
                "classpath:com/indeval/dali/conf/applicationContext-colocacionPrimaria.xml"
//                "classpath:com/indeval/dali/conf/applicationContext-aperturaSistemas.xml",
//                "classpath:com/indeval/dali/conf/applicationContext-traspasoEfectivoCuentasControl.xml",
//                "classpath:com/indeval/dali/conf/applicationContext-retiroDeFondos.xml",
//                "classpath:com/indeval/dali/conf/applicationContext-traspasosAdministrativos.xml"
                };

    }
}

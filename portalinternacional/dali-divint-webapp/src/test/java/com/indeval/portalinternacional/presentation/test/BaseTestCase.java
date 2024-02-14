/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.presentation.test;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

/**
 * 
 * @author Marcos
 *
 */
public class BaseTestCase extends AbstractDependencyInjectionSpringContextTests {

    public void onSetup() {
    }

    /**
     * @see org.springframework.test.AbstractSingleSpringContextTests#getConfigLocations()
     */
    protected String[] getConfigLocations() {
        return archivosContext;
    }

    protected Object getBean(String id) {
        return applicationContext.getBean(id);
    }
    /**
     * TODO corregir
     */
    private static String[] archivosContext = new String[]{
        "iTestApplicationContext.xml",
           // "db-propertiesContext-Test.xml",
            "test-persistence-portalinternacional-context.xml",
            "test-middleware-portalinternacional-services-context.xml"

       // "persistence-core-dao-context.xml"
    };

    /**
     * Manten feliz al Junit.
     */
    public void testDummy() {
    }
}

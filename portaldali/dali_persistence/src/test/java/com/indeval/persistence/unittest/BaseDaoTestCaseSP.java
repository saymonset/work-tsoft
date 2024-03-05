/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.unittest;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;


/**
 * Clase base para las pruebas de unidad de los daos
 * @author Fco. Agustin Calderon Orduña
 */
public class BaseDaoTestCaseSP extends AbstractDependencyInjectionSpringContextTests {

    private SessionFactory sessionFactory = null;
    
    private HibernateTemplate hibernateTemplate = null;
    
    protected void onSetUp() throws Exception {
        super.onSetUp();
        
            sessionFactory = (SessionFactory)getBean("sessionFactory");            
        
            hibernateTemplate = new HibernateTemplate();
    }
    
    /**
     * Metodo sobrecargado de la superclase.
     * Regresa las localidades de configuracion para esta prueba.
     * @return String[] con los filenames y paths a los archivos de
     * configuracion para este application context.
     */
    protected String[] getConfigLocations() {
        String[] paths = new String[] {
                "iTestStoredProcedureApplicationContext.xml"
               
        };
        return paths;
    }

    protected Object getBean(String id) {
        return applicationContext.getBean(id);
    }

    /**
     * Mant&eacute;n feliz al Junit.
     */
    public void testDummy() {
    }
    
    /**
     * @return SessionFactory
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    /**
     * @return HibernateTemplate
     */
    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }
}

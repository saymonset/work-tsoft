/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;


/**
 * Clase base para las pruebas de unidad
 * de los servicios con/sin ejb
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public abstract class BaseITestService extends AbstractDependencyInjectionSpringContextTests {

    private SessionFactory sessionFactoryInternacional = null;
    
    private SessionFactory sessionFactoryDali = null;
    
    private HibernateTransactionManager transactionManager = null;
    
    private TransactionTemplate transactionTemplate = new TransactionTemplate();
    
    private HibernateTemplate hibernateTemplate = null;
    
    /**
     * Constructor que recibe el nombre del metodo a probar en el junit.framework.TestCase.
     * @param name
     */
    public BaseITestService(String name) {
    	super(name);

    }

    /**
     *  Constructor
     */
    public BaseITestService() {
        super();

    }
    
    /**
     * @see org.springframework.test.AbstractSingleSpringContextTests#onSetUp()
     * @throws Exception
     */
    @Override
    protected void onSetUp() throws Exception {

        super.onSetUp();
        /*
        if(sessionFactoryInternacional == null) {
        	sessionFactoryInternacional = (SessionFactory) getBean("sessionFactoryInternacional");
        }
        if(sessionFactoryDali == null) {
        	sessionFactoryDali = (SessionFactory) getBean("sessionFactoryDali");
        }
        
        if (transactionManager == null) {
            transactionManager = new HibernateTransactionManager();
            transactionManager.setSessionFactory(sessionFactoryInternacional);
            transactionManager.setSessionFactory(sessionFactoryDali);
            transactionTemplate.setTransactionManager(transactionManager);
        }*/
        
    }
    
    /**
     * Metodo sobrecargado de la superclase.
     * Regresa las localidades de configuracion para esta prueba.
     * @return String[] con los filenames y paths a los archivos de
     * configuracion para este application context.
     */
    @Override
    protected String[] getConfigLocations() {

        String ejb = System.getProperty("ejb");
        System.out.println("ejb=[" + ejb + "]");
        if (ejb == null || ejb.equals("false")) {
            System.out.println("Probando sin ejb");
            return new String[] {
            		"iTestApplicationContext.xml",
                    "db-propertiesContext.xml",
            		"persistence-portalinternacional-context.xml",
                    "middleware-portalinternacional-services-context.xml",
                    "persistence-core-dao-context.xml",
                    "middleware-core-services-context.xml",
                };
        }
        else {
            System.out.println("Probando con ejb");
            /* Requiere los daos en caso de que quiera manipular
             * la base de datos para preparar la prueba.
             */
            return new String[] {
                    "portalinternacional-ejbclient-context.xml"
            };
        }
    }
    
    /**
     * Obtiene el bean cuyo nombre recibe
     * @param id
     * @return Object
     */
    protected Object getBean(String id) {
        return applicationContext.getBean(id);
    }
    
    /**
     * @return TransactionTemplate
     */
    public TransactionTemplate getTransactionTemplate() {
        return transactionTemplate;
    }
    
    /**
     * @return PlatformTransactionManager
     */
    public PlatformTransactionManager getTransactionManager() {
        return transactionManager;
    }

    /**
     * @return HibernateTemplate
     */
    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

}


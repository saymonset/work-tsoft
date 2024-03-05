/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */

/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services;

import java.sql.SQLException;

import org.hibernate.SessionFactory;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.HibernateTransactionManager;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import org.springframework.transaction.support.TransactionTemplate;


/**
 * Anteriormente esta clase se obtenia el dataSource y/o el sessionFactory que pueden ser
 * usados para las pruebas de DAOs con la base de datos. Ahora se utiliza para probar la
 * implementacion de los servicios.
 *
 * @author Sergio Mena.
 */
public class BaseTestCase extends AbstractDependencyInjectionSpringContextTests {

    /**  */
    public static String[] archivosContext = new String[] {
                                                 "iTestApplicationContext.xml",
                                                 "iTestApplicationContext-dali-dummy-services.xml",
                                                 "persistence-dali-dao-context.xml",
                                                 "applicationContext-dali-services.xml"                                                 
                                             };
    private SessionFactory sessionFactoryAnnotationDali = null;

    private HibernateTransactionManager transactionManager = null;
    private TransactionTemplate transactionTemplate = new TransactionTemplate();
    private HibernateTemplate hibernateTemplate = null;

    /**
     * Constructor que recibe el nombre del metodo a probar en el junit.framework.TestCase.
     * @param name
     */
    public BaseTestCase(String name) {
        super(name);

    }

    /**
     *  Constructor
     */
    public BaseTestCase() {
        super();

    }

    /**
     * 
     *  
     * @throws Exception 
     * @see org.springframework.test.AbstractSingleSpringContextTests#onSetUp()
     */
    public void onSetUp() {

        if (sessionFactoryAnnotationDali == null) {

            sessionFactoryAnnotationDali = (SessionFactory) getBean("sessionFactory");

        }

        if (transactionManager == null) {

            transactionManager = new HibernateTransactionManager();
            //            transactionManager.setSessionFactory(sessionFactoryDali);
            transactionManager.setSessionFactory(sessionFactoryAnnotationDali);
            transactionTemplate.setTransactionManager(transactionManager);

        }

        if (hibernateTemplate == null) {

            hibernateTemplate = new HibernateTemplate();
            //            hibernateTemplate.setSessionFactory(sessionFactoryDali);
            hibernateTemplate.setSessionFactory(sessionFactoryAnnotationDali);

        }

    }

    /**
     * Metodo para obtener el bean.
     *
     * @param id
     *
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
     * @return the transactionManager
     */
    public HibernateTransactionManager getTransactionManager() {

        return transactionManager;

    }

    /**
     * @return the hibernateTemplate
     */
    public HibernateTemplate getHibernateTemplate() {

        return hibernateTemplate;

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

        return archivosContext;

    }

}

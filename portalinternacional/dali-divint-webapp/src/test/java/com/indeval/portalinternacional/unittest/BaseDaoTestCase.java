// Cambio Multidivisas
package com.indeval.portalinternacional.unittest;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;


/**
 * Clase base para las pruebas de unidad de los servicios de eventos corporativos
 * 
 * @author Abraham Morales
 */
public class BaseDaoTestCase extends AbstractDependencyInjectionSpringContextTests {

    private SessionFactory sessionFactoryInternacional = null;

    private HibernateTransactionManager transactionManager = null;

    private final TransactionTemplate transactionTemplate = new TransactionTemplate();

    private HibernateTemplate hibernateTemplate = null;

    /**
     * Constructor por default
     * 
     */
    public BaseDaoTestCase() {

    }

    /**
     * Constructor que recibe el nombre del metodo a probar en el junit.framework.TestCase.
     * 
     * @param name
     */
    public BaseDaoTestCase(final String name) {
        super(name);

    }


    /**
     * @see org.springframework.test.AbstractSingleSpringContextTests#onSetUp()
     * @throws Exception
     */
    @Override
    protected void onSetUp() throws Exception {
        super.onSetUp();

        if (this.sessionFactoryInternacional == null) {
            this.sessionFactoryInternacional =
                    (SessionFactory) this.getBean("sessionFactoryInternacional");
        }
        System.out.println("Sesion factoru "+this.sessionFactoryInternacional);

        if (this.transactionManager == null) {
            this.transactionManager = new HibernateTransactionManager();
            this.transactionManager.setSessionFactory(this.sessionFactoryInternacional);
            this.transactionTemplate.setTransactionManager(this.transactionManager);
        }

        if (this.hibernateTemplate == null) {
            this.hibernateTemplate = new HibernateTemplate();
            this.hibernateTemplate.setSessionFactory(this.sessionFactoryInternacional);
        }
    }

    /**
     * Metodo sobrecargado de la superclase. Regresa las localidades de configuracion para esta
     * prueba.
     * 
     * @return String[] con los filenames y paths a los archivos de configuracion para este
     *         application context.
     */
    @Override
    protected String[] getConfigLocations() {
//
        String[] paths =
                new String[] {"iTestApplicationContext.xml","db-propertiesContext-Test.xml",
                        "test-persistence-portalinternacional-context.xml",
                        "test-middleware-portalinternacional-services-context.xml"};
        return paths;
    }

    /**
     * Obtiene el bean cuyo nombre recibe
     * 
     * @param id
     * @return Object
     */
    protected Object getBean(final String id) {
        return this.applicationContext.getBean(id);
    }

    /**
     * Manten feliz al Junit.
     */
    public void testDummy() {}

    /**
     * @return TransactionTemplate
     */
    public TransactionTemplate getTransactionTemplate() {
        return this.transactionTemplate;
    }

    /**
     * @return PlatformTransactionManager
     */
    public PlatformTransactionManager getTransactionManager() {
        return this.transactionManager;
    }

    /**
     * @return HibernateTemplate
     */
    public HibernateTemplate getHibernateTemplate() {
        return this.hibernateTemplate;
    }

    public SessionFactory getSessionFactoryInternacional() {
        return sessionFactoryInternacional;
    }
}

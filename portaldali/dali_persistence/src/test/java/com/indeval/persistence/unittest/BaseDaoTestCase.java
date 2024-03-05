/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.unittest;

import org.hibernate.SessionFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.HibernateTransactionManager;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.indeval.portaldali.persistence.vo.PageVO;


/**
 * Clase base para las pruebas de unidad de los daos
 *
 * @author Fco. Agustin Calderon Ordu&ntilde;a
 */
public abstract class BaseDaoTestCase extends AbstractDependencyInjectionSpringContextTests {

    private Logger logger = LoggerFactory.getLogger(BaseDaoTestCase.class);

    private SessionFactory sessionFactoryAnotation = null;
    
    //    private SessionFactory sessionFactoryDali = null;
    private HibernateTransactionManager transactionManager = null;
    private TransactionTemplate transactionTemplate = new TransactionTemplate();
    private HibernateTemplate hibernateTemplate = null;
    
    /**
     * Constructor por default
     *
     */
    public BaseDaoTestCase() {

    }

    /**
     * Constructor que recibe el nombre del metodo a probar en el junit.framework.TestCase.
     * @param name
     */
    public BaseDaoTestCase(String name) {
    	super(name);

    }
    
    /**
     * @see org.springframework.test.AbstractSingleSpringContextTests#onSetUp()
     * @throws Exception
     */
    protected void onSetUp() {

		try {
			super.onSetUp();
		} catch (Exception e) {
			logger.error("Falla de config inicial del test case ", e);
		}
        
        if(sessionFactoryAnotation == null) {

        	sessionFactoryAnotation = (SessionFactory) getBean("sessionFactory");

        }
        
        if (transactionManager == null) {

            transactionManager = new HibernateTransactionManager();
            //            transactionManager.setSessionFactory(sessionFactoryDali);
            transactionManager.setSessionFactory(sessionFactoryAnotation);
            transactionTemplate.setTransactionManager(transactionManager);

        }
        
        if (hibernateTemplate == null) {

            hibernateTemplate = new HibernateTemplate();
            //            hibernateTemplate.setSessionFactory(sessionFactoryDali);
            hibernateTemplate.setSessionFactory(sessionFactoryAnotation);

        }

    }
    
    /**
     * Metodo sobrecargado de la superclase. Regresa las localidades de configuracion para
     * esta prueba.
     *
     * @return String[] con los filenames y paths a los archivos de configuracion para este
     *         application context.
     */
    protected String[] getConfigLocations() {

        String[] paths = new String[] {
                "iTestApplicationContext.xml",
                "db-propertiesContext-Test.xml",
                "persistence-dali-dao-context-Test.xml"
                         
        };

        return paths;

    }

    /**
     * Obtiene el bean cuyo nombre recibe
     *
     * @param id
     *
     * @return Object
     */
    protected Object getBean(String id) {

        return applicationContext.getBean(id);

    }

    /**
     * Manten feliz al Junit.
     */
//    public void testDummy() {
//    }
    
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

    /**
     * Valida que la pagina no sea nula y que la misma tenga registros
     * 
     * @param pageVO
     * @throws BusinessException
     *             Excepcio&oacute;n arrojada si no hay registros
     */
    public void validaPagina(PageVO pageVO) throws Exception {

        if (pageVO == null) {

            throw new RuntimeException("La p&aacute;gina esta null.");

        }
        if (pageVO.getRegistros() == null) {

            throw new RuntimeException("La p&aacute;gina no tiene la lista de registros.");

        }
        if (pageVO.getRegistros().isEmpty()) {

            throw new RuntimeException("La lista de registros esta vac&iacute;a.");

        }
        if(pageVO.getTotalRegistros() == null){
            
            throw new RuntimeException("La p&aacute;gina no tiene el total de registros");
            
        }

    }

    //	/**
    //	 * @return the sessionFactoryDali
    //	 */
    //	public SessionFactory getSessionFactoryDali() {
    //		return sessionFactoryDali;
    //	}
    
}

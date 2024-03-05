/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;


/**
 * Clase base para las pruebas de unidad de los servicios con/sin ejb
 *
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public abstract class BaseITestService extends AbstractDependencyInjectionSpringContextTests {
    
    private SessionFactory sessionFactoryAnotation = null;
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
    protected void onSetUp() throws Exception {

        super.onSetUp();

        if (sessionFactoryAnotation == null) {

            sessionFactoryAnotation = (SessionFactory) getBean("sessionFactoryAnotation");

        }
        
        if (transactionManager == null) {

            transactionManager = new HibernateTransactionManager();
            transactionManager.setSessionFactory(sessionFactoryAnotation);
            transactionTemplate.setTransactionManager(transactionManager);

        }
        
        if (hibernateTemplate == null) {

            hibernateTemplate = new HibernateTemplate();
            hibernateTemplate.setSessionFactory(getSessionFactoryAnotation());

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

        String ejb = System.getProperty("ejb");
        System.out.println("ejb=[" + ejb + "]");

        if ((ejb == null) || ejb.equals("${ejb}")) {

            System.out.println("Probando sin ejb");
         
            return new String[] {
                       "middleware-integrationtest-context.xml",
                       "persistence-dali-dao-context.xml",
                       "persistence-oracle-dao-annotation-context.xml",
                       "middleware-transactions-context.xml",
                       "classpath:com/indeval/portaldali/conf/applicationContext-test.xml",
                       "classpath:com/indeval/portaldali/conf/applicationContext-detalles.xml",
                       "classpath:com/indeval/portaldali/conf/estatus/applicationContext-operaciones.xml",
                       "classpath:com/indeval/portaldali/conf/applicationContext-portallegado.xml",
                       "classpath:com/indeval/portaldali/conf/common/applicationContext-catalogos.xml",
                       "classpath:com/indeval/portaldali/conf/applicationContext-portallegado-dao-dali.xml",
                       "classpath:com/indeval/portaldali/conf/estadocuenta/applicationContext-movimientos-efectivo.xml",
                       "classpath:com/indeval/portaldali/conf/estadocuenta/applicationContext-posicion.xml",
                       "classpath:com/indeval/portaldali/conf/estadocuenta/applicationContext-saldos.xml",
                       "classpath:com/indeval/portaldali/conf/estadocuenta/applicationContext-estadocuenta-posicion.xml",
                       "classpath:com/indeval/portaldali/conf/estadocuenta/applicationContext-estadocuenta-saldo.xml"
                   };

        } else {

            System.out.println("Probando con ejb");

            /* Requiere los daos en caso de que quiera manipular
             * la base de datos para preparar la prueba.
             */
            return new String[] {
                       "middleware-integrationtest-context.xml", 
                       "persistence-dali-dao-context.xml",
                       "persistence-oracle-dao-annotation-context.xml",
                       "middleware-transactions-context.xml",
                       "middleware-portallegado-ejbclient-context.xml",
                       "classpath:com/indeval/portaldali/conf/applicationContext-test.xml",
                       "classpath:com/indeval/portaldali/conf/applicationContext-detalles.xml",
                       "classpath:com/indeval/portaldali/conf/estatus/applicationContext-operaciones.xml",
                       "classpath:com/indeval/portaldali/conf/applicationContext-portallegado.xml",
                       "classpath:com/indeval/portaldali/conf/common/applicationContext-catalogos.xml",
                       "classpath:com/indeval/portaldali/conf/applicationContext-portallegado-dao-dali.xml",
                       "classpath:com/indeval/portaldali/conf/estadocuenta/applicationContext-movimientos-efectivo.xml",
                       "classpath:com/indeval/portaldali/conf/estadocuenta/applicationContext-posicion.xml",
                       "classpath:com/indeval/portaldali/conf/estadocuenta/applicationContext-saldos.xml",
                       "classpath:com/indeval/portaldali/conf/estadocuenta/applicationContext-estadocuenta-posicion.xml",
                       "classpath:com/indeval/portaldali/conf/estadocuenta/applicationContext-estadocuenta-saldo.xml"
                   };

        }

    }
    
    /**
     * Valida que la pagina no sea nula y que la misma tenga registros
     * 
     * @param paginaVO
     * @throws BusinessException
     *             Excepcio&oacute;n arrojada si no hay registros
     */
    public void validaPagina(PaginaVO paginaVO) throws Exception {

        if (paginaVO == null) {

            throw new BusinessException("La p&aacute;gina esta null.");

        }
        if (paginaVO.getRegistros() == null) {

            throw new BusinessException("La p&aacute;gina no tiene la lista de registros.");

        }
        if (paginaVO.getRegistros().isEmpty()) {

            throw new BusinessException("La lista de registros esta vac&iacute;a.");

        }
        if(paginaVO.getTotalRegistros() == null){
            
            throw new BusinessException("La p&aacute;gina no tiene el total de registros");
            
        }

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
	 * @return the sessionFactoryAnotation
	 */
	public SessionFactory getSessionFactoryAnotation() {

		return sessionFactoryAnotation;

	}

}
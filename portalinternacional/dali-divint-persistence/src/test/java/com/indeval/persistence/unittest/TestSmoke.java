package com.indeval.persistence.unittest;

import org.hibernate.SessionFactory;


/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class TestSmoke extends BaseDaoTestCase {
    
    /**
     * @throws Exception
     */
    public void testSessionFactory() throws Exception {
        SessionFactory sessionFactoryInternacional = (SessionFactory)getBean("sessionFactoryInternacional");
        assertNotNull(sessionFactoryInternacional);
    }

}

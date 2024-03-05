/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.unittest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 */
public class TestSmoke extends BaseDaoTestCase {
    
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @throws Exception
     */
	public void testBeans() throws Exception {
        
		String[] beanNames = applicationContext.getBeanDefinitionNames();
		String beanName = null;		
        
		
			for (String stringBeanName : beanNames) {
				try {
					beanName = stringBeanName;					
					logger.info("Loading ["+stringBeanName+"]");
					Object o = getBean(stringBeanName);
				} catch (Exception e) {
					logger.error(beanName, e);
				}
    }

		

	}
}

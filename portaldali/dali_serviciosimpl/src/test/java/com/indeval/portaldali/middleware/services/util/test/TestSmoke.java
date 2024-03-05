/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.util.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseTestCase;



/**
 * 
 */
public class TestSmoke extends BaseTestCase {

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

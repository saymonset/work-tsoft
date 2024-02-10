/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.indeval.portalinternacional.middleware.services;

import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

/**
 *
 * @author ribarra
 */
public class SpringBeanAutowiringInterceptorDivInt extends SpringBeanAutowiringInterceptor {

    @Override
    protected String getBeanFactoryLocatorKey(Object target) {
        return "indeval.divint.factory";
    }

}

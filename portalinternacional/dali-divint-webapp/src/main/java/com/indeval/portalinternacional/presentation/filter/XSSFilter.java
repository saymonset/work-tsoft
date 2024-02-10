/**
 * Bursatec - Portal Dali
 * Copyrigth (c) 2015 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.presentation.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Filtro utilizado para evitar los ataques por cross site scripting. Incluye además
 * funcionalidad para hacer las cookies de sesión seguras.
 * 
 * La implementación se tomo de las siguientes páginas:
 * https://www.owasp.org/index.php/HttpOnly
 * http://www.javacodegeeks.com/2012/07/anti-cross-site-scripting-xss-filter.html
 * 
 * @author Pablo Balderas
 *
 */
public class XSSFilter implements Filter {

    /*
     * (non-Javadoc)
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        chain.doFilter(new XSSRequestWrapper((HttpServletRequest) request), response);
    }
}
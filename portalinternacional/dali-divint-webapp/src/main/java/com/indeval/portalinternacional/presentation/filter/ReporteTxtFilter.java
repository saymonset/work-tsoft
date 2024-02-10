/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.indeval.portalinternacional.presentation.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ribarra
 */
public class ReporteTxtFilter implements Filter {

    private FilterConfig filterConfig = null;
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static List<String> contentTypes = new ArrayList<String>();

    static {
        contentTypes.add("text/plain");
        contentTypes.add("text/plain;charset=UTF-8");
    }

    public ReporteTxtFilter() {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        RequestWrapper wrappedRequest = new RequestWrapper((HttpServletRequest) request);
        ResponseWrapper wrappedResponse = new ResponseWrapper((HttpServletResponse) response);


        try {
            if (isReporteTxt(wrappedRequest)) {
                log.info("Es un probable reporte txt");
                TrimResponseWrapper trimResponseWrapper = new TrimResponseWrapper((HttpServletResponse) response);
                chain.doFilter(request, trimResponseWrapper);
                byte[] pageContent = trimResponseWrapper.getData();
                ServletOutputStream servletOut = response.getOutputStream();
                String contentType = trimResponseWrapper.getContentType();
                response.setContentType(contentType);
                if (pageContent != null && pageContent.length > 0 && contentTypes.contains(contentType)) {
                    log.info("Es un reporte txt");
                    String cadena = new String(pageContent);
                    String cadenasBusqueda[] = {"<html>", "</html>"};
                    for (int i = 0; i < cadenasBusqueda.length; i++) {
                        int indice = cadena.indexOf(cadenasBusqueda[i]);
                        if (indice >= 0) {
                            cadena = cadena.replaceAll(cadenasBusqueda[i], " ");
                        }
                    }
                    pageContent = cadena.getBytes();

                    int init = 0;
                    int end = pageContent.length;

                    while (isBlank(pageContent[init]) && init < end) {
                        init++;
                    }

                    while (isBlank(pageContent[end - 1]) && end > init) {
                        end--;
                    }

                    response.setContentLength(end - init);

                    if (end != init) {
                        servletOut.write(pageContent, init, end - init);
                    }
                } else {
                    servletOut.write(pageContent);
                }
                servletOut.flush();
                servletOut.close();
            } else {
                chain.doFilter(wrappedRequest, wrappedResponse);
            }
        } catch (Exception ex) {
            log.error("Error al prealizar la peticion", ex);
        }
    }

    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    public void destroy() {
    }

    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;

    }

    private boolean isReporteTxt(RequestWrapper wrappedRequest) {
        Map parametros = wrappedRequest.getParameterMap();
        boolean retorno = Boolean.FALSE;
        if (parametros != null && parametros.size() > 0) {
            for (String llave : (Set<String>) parametros.keySet()) {
                if (StringUtils.contains(llave, "generarTxt")) {
                    retorno = Boolean.TRUE;
                    break;
                }
            }
        }
        return retorno;
    }

    private boolean isBlank(byte caracter) {
		char c = (char) caracter;
		return (c == ' ' || c == '\t' || c == '\n' || c == '\r');
	}

    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("ReporteTxtFilter()");
        }
        StringBuffer sb = new StringBuffer("ReporteTxtFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());

    }

    class RequestWrapper extends HttpServletRequestWrapper {

        public RequestWrapper(HttpServletRequest request) {
            super(request);
        }
        protected Hashtable localParams = null;

        public void setParameter(String name, String[] values) {

            if (localParams == null) {
                localParams = new Hashtable();
                Map wrappedParams = getRequest().getParameterMap();
                Set keySet = wrappedParams.keySet();
                for (Iterator it = keySet.iterator(); it.hasNext();) {
                    Object key = it.next();
                    Object value = wrappedParams.get(key);
                    localParams.put(key, value);
                }
            }
            localParams.put(name, values);
        }

        @Override
        public String getParameter(String name) {

            if (localParams == null) {
                return getRequest().getParameter(name);
            }
            Object val = localParams.get(name);
            if (val instanceof String) {
                return (String) val;
            }
            if (val instanceof String[]) {
                String[] values = (String[]) val;
                return values[0];
            }
            return (val == null ? null : val.toString());
        }

        @Override
        public String[] getParameterValues(String name) {

            if (localParams == null) {
                return getRequest().getParameterValues(name);
            }
            return (String[]) localParams.get(name);
        }

        @Override
        public Enumeration getParameterNames() {

            if (localParams == null) {
                return getRequest().getParameterNames();
            }
            return localParams.keys();
        }

        @Override
        public Map getParameterMap() {

            if (localParams == null) {
                return getRequest().getParameterMap();
            }
            return localParams;
        }
    }

    class ResponseWrapper extends HttpServletResponseWrapper {

        public ResponseWrapper(HttpServletResponse response) {
            super(response);
        }
    }
}

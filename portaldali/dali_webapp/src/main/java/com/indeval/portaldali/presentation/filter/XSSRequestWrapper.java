/**
 * Bursatec - Portal Dali
 * Copyrigth (c) 2015 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.presentation.filter;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.owasp.esapi.ESAPI;

/**
 * Clase utilizada para el filtrado de peticiones para evitar los ataques por
 * cross site scripting.
 * 
 * La implementación se tomo de:
 * http://www.javacodegeeks.com/2012/07/anti-cross-site-scripting-xss-filter.html
 * 
 * @author Pablo Balderas
 *
 */
public class XSSRequestWrapper extends HttpServletRequestWrapper {

	
	private static final String PATTERN_1="<script>(.*?)</script>";
	private static final String PATTERN_2="src[\r\n]*=[\r\n]*\\\'(.*?)\\\'";
	private static final String PATTERN_3="src[\r\n]*=[\r\n]*\\\"(.*?)\\\"";
	private static final String PATTERN_4="</script>";
	private static final String PATTERN_5="<script(.*?)>";
	private static final String PATTERN_6="eval\\((.*?)\\)";
	private static final String PATTERN_7="expression\\((.*?)\\)";
	private static final String PATTERN_8="javascript:";
	private static final String PATTERN_9="vbscript:";
	private static final String PATTERN_10="onload(.*?)=";
	private static final String EMPTY="";
	private Pattern scriptPattern_1=null;
	private Pattern scriptPattern_2=null;
	private Pattern scriptPattern_3=null;
	private Pattern scriptPattern_4=null;
	private Pattern scriptPattern_5=null;
	private Pattern scriptPattern_6=null;
	private Pattern scriptPattern_7=null;
	private Pattern scriptPattern_8=null;
	private Pattern scriptPattern_9=null;
	private Pattern scriptPattern_10=null;
	
	
    public XSSRequestWrapper(HttpServletRequest servletRequest) {
        super(servletRequest);
        // Avoid anything between script tags
        scriptPattern_1 = Pattern.compile(PATTERN_1, Pattern.CASE_INSENSITIVE);
        // Avoid anything in a src='...' type of expression
        scriptPattern_2 = Pattern.compile(PATTERN_2, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        
        scriptPattern_3 = Pattern.compile(PATTERN_3, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        
        // Remove any lonesome </script> tag
        scriptPattern_4 = Pattern.compile(PATTERN_4, Pattern.CASE_INSENSITIVE);
        // Remove any lonesome <script ...> tag
        scriptPattern_5 = Pattern.compile(PATTERN_5, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);        
        // Avoid eval(...) expressions
        scriptPattern_6 = Pattern.compile(PATTERN_6, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        
        // Avoid expression(...) expressions
        scriptPattern_7 = Pattern.compile(PATTERN_7, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        
        // Avoid javascript:... expressions
        scriptPattern_8 = Pattern.compile(PATTERN_8, Pattern.CASE_INSENSITIVE);
        
        // Avoid vbscript:... expressions
        scriptPattern_9 = Pattern.compile(PATTERN_9, Pattern.CASE_INSENSITIVE);
        
        // Avoid onload= expressions
        scriptPattern_10 = Pattern.compile(PATTERN_10, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.ServletRequestWrapper#getParameterValues(java.lang.String)
     */
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);

        if (values == null) {
            return null;
        }

        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = stripXSS(values[i]);
        }

        return encodedValues;
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.ServletRequestWrapper#getParameter(java.lang.String)
     */
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);

        return stripXSS(value);
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpServletRequestWrapper#getHeader(java.lang.String)
     */
    public String getHeader(String name) {
        String value = super.getHeader(name);
        return stripXSS(value);
    }

    /**
     * Método que realiza el filtrado.
     * @param value Valor a filtrar.
     * @return Valor filtrado.
     */
    private String stripXSS(String value) {
        if (value != null) {
            // NOTE: It's highly recommended to use the ESAPI library and uncomment the following line to
            // avoid encoded attacks.
            value = ESAPI.encoder().canonicalize(value);

            // Avoid null characters
            value = value.replaceAll(EMPTY, EMPTY);
            
            value = scriptPattern_1.matcher(value).replaceAll(EMPTY);

           
            value = scriptPattern_2.matcher(value).replaceAll(EMPTY);

            
            value = scriptPattern_3.matcher(value).replaceAll(EMPTY);

            
            value = scriptPattern_4.matcher(value).replaceAll(EMPTY);

            
            value = scriptPattern_5.matcher(value).replaceAll(EMPTY);

           
            value = scriptPattern_6.matcher(value).replaceAll(EMPTY);

            
            value = scriptPattern_7.matcher(value).replaceAll(EMPTY);

           
            value = scriptPattern_8.matcher(value).replaceAll(EMPTY);

            
            value = scriptPattern_9.matcher(value).replaceAll(EMPTY);

           
            value = scriptPattern_10.matcher(value).replaceAll(EMPTY);
        }
        return value;
    }
}
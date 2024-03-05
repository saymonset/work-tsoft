/**
 * 
 */
package com.indeval.portaldali.presentation.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author enavarrete
 * 
 */
public class TrimFilter implements Filter {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private FilterConfig filterConfig;

	private Set<String> contentTypes;

	private Set<String> excluded;

	private static final String CONTENT_TYPES = "contentTypes";

	private static final String EXCLUDE = "exclude";

	/**
	 * @return filterConfig
	 */
	public FilterConfig getFilterConfig() {
		return filterConfig;
	}

	/**
	 * @param filterConfig
	 */
	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

	/**
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
		filterConfig = null;
	}

	/**
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 *      javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest servletrequest,
			ServletResponse servletresponse, FilterChain filterchain)
			throws IOException, ServletException {

		logger.debug("Entrando a TrimFilter.doFilter");

		TrimResponseWrapper trimResponseWrapper = new TrimResponseWrapper(
				(HttpServletResponse) servletresponse);
		filterchain.doFilter(servletrequest, trimResponseWrapper);

		byte[] pageContent = trimResponseWrapper.getData();
		if (pageContent == null || pageContent.length == 0) {
			return;
		}
		String contentType = trimResponseWrapper.getContentType();

		logger.debug("Es del tipo: [" + contentType + "]");

		ServletOutputStream servletOut = servletresponse.getOutputStream();
		if (servletOut == null) {
			return;
		}
		servletresponse.setContentType(contentType);

		if (contentTypes.contains(contentType)) {
			String cadena = new String(pageContent);
			String cadenasBusqueda[] = {
					"<\\?xml version=\"1.0\" encoding=\"UTF-8\"\\?>", "xml version=\"1.0\" encoding=\"UTF-8\"",
					"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\" >",
					"<html xmlns\\=\"http://www.w3.org/1999/xhtml\">", "<html>",
					"</html>" };
			for( int i = 0; i < cadenasBusqueda.length; i++ ) {
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

			servletresponse.setContentLength(end - init);

			if (end != init) {
				servletOut.write(pageContent, init, end - init);
			}
		} else {
			servletOut.write(pageContent);
		}
		servletOut.flush();
		servletOut.close();
	}

	/**
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		setFilterConfig(filterConfig);
		contentTypes = new HashSet<String>();
		String stContentTypes = filterConfig.getInitParameter(CONTENT_TYPES);
		if (stContentTypes != null) {
			String[] arrContentTypes = stContentTypes.split(",");
			for (int i = 0; i < arrContentTypes.length; i++) {
				contentTypes.add(arrContentTypes[i].trim());
			}
		}
		excluded = new HashSet<String>();
		String stExclude = filterConfig.getInitParameter(EXCLUDE);
		if (stExclude != null) {
			String[] arrExclude = stExclude.split(",");
			for (int i = 0; i < arrExclude.length; i++) {
				excluded.add(arrExclude[i].trim());
			}
		}
	}

	private boolean isBlank(byte caracter) {
		char c = (char) caracter;
		return (c == ' ' || c == '\t' || c == '\n' || c == '\r');
	}

}

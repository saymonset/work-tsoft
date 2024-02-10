package com.indeval.portalinternacional.middleware.services.makerchecker;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.services.util.JsonUtil;
import com.indeval.workflow.utils.JwtUtils;

public class WorkflowClientService {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	private String url;
	private String secret;
	
	public WorkflowClientService() {
		// TODO Auto-generated constructor stub
	}
	
	public long enviarSolicitud(String workflow, String businessId, String descripcion, String ticket)throws BusinessException{
		LOGGER.info("WorkflowClientService :: enviarSolicitud");
		
		if(LOGGER.isTraceEnabled())LOGGER.trace("httpPost");
		if(StringUtils.isBlank(workflow)) throw new IllegalArgumentException("workflow es un dato requerido.");
		
		Map<String, String> params = new HashMap<>();
		params.put("businesId", businessId);
		params.put("description", descripcion);
		
		CloseableHttpClient httpClient = null;
		try {
			
			httpClient = createCloseableHttpClient();
			String token = JwtUtils.buildJwtToken(ticket, secret.getBytes("UTF-8"));
			String urlPost = this.url + "/authorization/" + workflow;
			HttpPost httpPost = new HttpPost(urlPost);
			httpPost.setHeader("Content-type", "application/json");
			httpPost.setHeader("Authorization", token);
			httpPost.setEntity(new StringEntity(JsonUtil.serializeAsJson(params), "UTF-8"));
			HttpClientContext context = HttpClientContext.create();
			HttpResponse response = httpClient.execute(httpPost, context);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if(HttpStatus.SC_ACCEPTED != statusCode) {
				
				String message = String.format("No se pudo CREAR la solicitud de Autorizacion. [%d:%s]", statusCode, statusLine.getReasonPhrase());
				LOGGER.error("WorkflowClientService :: error: " + message);
				// throw new BusinessException(message);
				
				return -statusCode;
			}
			HttpEntity entity = response.getEntity();
			LOGGER.info("entity:",entity);
			
			String result = EntityUtils.toString(entity);
			LOGGER.info(result);
			
			long idProcesoAutorizacion = Long.parseLong(result);
			LOGGER.info("WorkflowClientService :: idProcesoAutorizacion: " + idProcesoAutorizacion);
			return idProcesoAutorizacion;
		}catch(IOException e) {
			LOGGER.error(e.getMessage(), e);
			throw new RuntimeException("Ocurrio un error inesperado.");
		}catch(NumberFormatException e) {
			LOGGER.error(e.getMessage(), e);
			throw new RuntimeException("No se pudo obtener la respuesta.");
		}catch(KeyManagementException | KeyStoreException | NoSuchAlgorithmException e) {
			throw new RuntimeException("Error al conectarse por SSL");
		}finally {
			closeHttpClient(httpClient);
		}
	}

	private CloseableHttpClient createCloseableHttpClient() throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException{
		TrustStrategy acceptingTrustStrategy = new TrustAllStrategy() {
			@Override
			public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				return true;
			}
		};
		
		SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
		
		Registry<ConnectionSocketFactory> socketFactoryregistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("https", sslsf)
				.register("http", new PlainConnectionSocketFactory())
				.build();
				
		BasicHttpClientConnectionManager connectionManager = new BasicHttpClientConnectionManager(socketFactoryregistry);
		
		//CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).setConnectionManager(connectionManager).build();
		
		return httpClient;
	}
	private void closeHttpClient(CloseableHttpClient httpClient) {
		try {
			if(httpClient != null)
				httpClient.close();
		}catch(IOException e) {
			LOGGER.error(e.getMessage());
		}
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the secret
	 */
	public String getSecret() {
		return secret;
	}

	/**
	 * @param secret the secret to set
	 */
	public void setSecret(String secret) {
		this.secret = secret;
	}

}

package com.indeval.portaldali.middleware.services.tarea;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.util.JsonUtil;
import com.indeval.workflow.vo.TaskVo;

public class WorkflowClientService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private String url;
	
	public TaskVo findTaskById(String taskId, String ticket) {
		if(logger.isDebugEnabled())logger.trace("findTaskById");
		
		CloseableHttpClient httpClient = null;
		
		try {
			
			httpClient = createCloseableHttpClient();
			
			URIBuilder builder = new URIBuilder(new StringBuilder(this.url).append("/tasks/").append(taskId).toString() );
			URI uri = builder.build();
			
			if(logger.isDebugEnabled())logger.debug("URI: " + uri.toString());
			
			HttpGet httpGet = new HttpGet(uri);
			httpGet.setHeader("Authorization", ticket);
			HttpResponse response = httpClient.execute(httpGet);
			
			StatusLine statusLine = response.getStatusLine();
			if(HttpStatus.SC_OK != statusLine.getStatusCode()) {
				String message = String.format("No se pudo consultar la  [%d]. %s", statusLine.getStatusCode(),
						statusLine.getReasonPhrase()); 
				logger.error(message);
				throw new RuntimeException(message);
			}
			
			String responseString = EntityUtils.toString(response.getEntity());
			
			logger.info(responseString);
			
			TaskVo task = JsonUtil.readObject(responseString,  TaskVo.class);
			
			return task;
		}catch(URISyntaxException e) {
			logger.error(e.getMessage());
			throw new RuntimeException("La URl es invalida.");
		}catch(IOException e) {
			logger.error(e.getMessage());
			throw new RuntimeException("Ocurrio un error inesperado.");
		}catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException("Ocurrio un error inesperado al obtener la respuesta.");
		}finally {
			closeHttpClient(httpClient);
		}
	}
	public List<TaskVo> findTasksByUsername(String claveUsuario, String ticket) {
		if(logger.isTraceEnabled())logger.trace("findTasksByUsername");
		
		CloseableHttpClient httpClient = null;
		
		try {
			httpClient = createCloseableHttpClient();
			
			URIBuilder builder = new  URIBuilder(this.url + "/tasks");
			builder.addParameter("username", claveUsuario);
			URI uri = builder.build();
			
			if(logger.isDebugEnabled())logger.debug("URI: " + uri.toString());
			
			HttpGet httpGet = new HttpGet(uri);
			httpGet.setHeader("Authorization", ticket);
			
			logger.info("Authorization: " + ticket);
			HttpResponse response = httpClient.execute(httpGet);
			
			StatusLine statusLine = response.getStatusLine();
			if(HttpStatus.SC_OK != statusLine.getStatusCode()) {
				String message = String.format("No se pudo consultar la  [%d]. %s", statusLine.getStatusCode(),
						statusLine.getReasonPhrase()); 
				logger.error(message);
				throw new RuntimeException(message);
			}
			
			String responseString = EntityUtils.toString(response.getEntity());
			
			logger.info(responseString);
			
			List<TaskVo> solicitudes = JsonUtil.readList(responseString, TaskVo.class);
			
			return solicitudes;
		}catch(URISyntaxException e) {
			logger.error(e.getMessage());
			throw new RuntimeException("La URl es invalida.");
		}catch(IOException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException("Ocurrio un error inesperado.");
		}catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException("Ocurrio un error inesperado al obtener la respuesta.");
		}finally {
			closeHttpClient(httpClient);
		}
	}
	
	public void claimTask(String taskId, String claveUsuario, String ticket) {
		if(logger.isTraceEnabled())logger.trace("claimTask");
		
		CloseableHttpClient httpClient = null;
		
		try {
			
			httpClient = createCloseableHttpClient();
			
			URIBuilder builder = new URIBuilder(new StringBuilder(this.url).append("/tasks/").append(taskId).append("/claim").toString() );
			URI uri = builder.build();
			
			if(logger.isDebugEnabled())logger.debug("URI: " + uri.toString());
			
			HttpPost httpPost = new HttpPost(uri);
			httpPost.setHeader("Authorization", ticket);
			HttpResponse response = httpClient.execute(httpPost);
			
			StatusLine statusLine = response.getStatusLine();
			if(HttpStatus.SC_ACCEPTED != statusLine.getStatusCode()) {
				logger.error(String.format("No se pudo ASIGNAR la tarea [%d]. %s", statusLine.getStatusCode(),
						statusLine.getReasonPhrase()));
				return;
			}
			
			String responseString = EntityUtils.toString(response.getEntity());
			
			logger.info(responseString);
		}catch(URISyntaxException e) {
			logger.error(e.getMessage());
			throw new RuntimeException("La URl es invalida.");
		}catch(IOException e){
			logger.error(e.getMessage(), e);
			throw new RuntimeException("No se pudo obtener la respuesta.");
		}catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException("Ocurrio un error inesperado al obtener la respuesta.");
		}finally {
			closeHttpClient(httpClient);
		}
	}
	
	public void completeTask(String taskId, boolean authorize, String claveUsuario, String ticket) {
		if(logger.isTraceEnabled())logger.trace("completeTask");
		
		CloseableHttpClient httpClient = null;
		
		try {
			httpClient = createCloseableHttpClient();
			
			URIBuilder builder = new URIBuilder(new StringBuilder(this.url).append("/tasks/").append(taskId).append("/complete").toString() );
			URI uri = builder.build();
			
			if(logger.isDebugEnabled())logger.debug("URI: " + uri.toString());
			
			Map<String, String> params = new HashMap<>();
			params.put("authorize", String.valueOf(authorize));
			params.put("username", claveUsuario);
			
			HttpPost httpPost = new HttpPost(uri);
			httpPost.setEntity(new StringEntity(JsonUtil.serializeAsJson(params), "UTF-8"));
			httpPost.setHeader("Content-type", "application/json");
			httpPost.setHeader("Authorization", ticket);
			
			HttpResponse response = httpClient.execute(httpPost);
			
			StatusLine statusLine = response.getStatusLine();
			if(HttpStatus.SC_ACCEPTED != statusLine.getStatusCode()) {
				logger.error(String.format("No se pudo COMPLETAR la tarea [%d]. %s", statusLine.getStatusCode(),
						statusLine.getReasonPhrase()));
				return;
			}
			
			String responseString = EntityUtils.toString(response.getEntity());
			
			logger.info(responseString);
		}catch(URISyntaxException e){
			logger.error(e.getMessage(), e);
			throw new RuntimeException("No se pudo obtener la respuesta.");
		}catch(IOException e){
			logger.error(e.getMessage(), e);
			throw new RuntimeException("No se pudo obtener la respuesta.");
		}catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException("Ocurrio un error inesperado al obtener la respuesta.");
		}finally {
			closeHttpClient(httpClient);
		}
	}

	private CloseableHttpClient createCloseableHttpClient() throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException{
		TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
			
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
			logger.error(e.getMessage());
		}
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}

package com.indeval.portalinternacional.middleware.services.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

public class MimeMessagePreparatorImpl implements MimeMessagePreparator {

	private final static Logger LOG = LoggerFactory.getLogger(MimeMessagePreparatorImpl.class);
	private String from;
	private String to;
	private String subject;
	private String plainTextContent;
	private String htmlContent;
	private String destinatarios;
	private String origen;
	
	public void prepare(MimeMessage message) {	
		LOG.debug("MimeMessagePreparatorImpl :: prepare");
		try {
			message.addFrom(InternetAddress.parse(getFrom()));
			message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(getSubject());

			MimeMultipart mimeMultipart = new MimeMultipart("alternative");
			message.setContent(mimeMultipart);

			BodyPart htmlBodyPart = new MimeBodyPart();
			htmlBodyPart.setContent(getHtmlContent(), "text/html");
			mimeMultipart.addBodyPart(htmlBodyPart);
		} catch (AddressException e) {
			LOG.error(e.getMessage());
		} catch (MessagingException e) {
			LOG.error(e.getMessage());
		}		
	}
	
	 public MimeMessagePreparator createMail(final String message, final String destino, final String subject, final String from, final String encoding) {
		 LOG.debug("MimeMessagePreparatorImpl :: createMail :: " + message);
		 final String[] destinos = separaDestinos(destino);
		 MimeMessagePreparator preparator = new MimeMessagePreparator() {

			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, encoding);				
				for(String dest : destinos) {
					helper.addTo(dest);
				}
				helper.setSubject(subject);
				helper.setFrom(from);
				LOG.debug("%%%%%%%%%%%%%%%%%%%%%% MENSAJE A MAIL:" + message);
				helper.setText(message);
			}
		 };
		 return preparator;
	 }
	 
	 public MimeMessage createMail(MimeMessage message, byte[] fichero, String mensaje, String subject, String encoding, 
			 Long idDerecho, String isin, Date fechaPago){
		 LOG.debug("MimeMessagePreparatorImpl :: createMail :: " + message);
			ByteArrayResource byteArrayResource = new ByteArrayResource(fichero);
			SimpleDateFormat dfNombreArchivo = new SimpleDateFormat("dd/MM/yyyy");
			Date fechaHoy = new Date();
			String strFechaArchivo = dfNombreArchivo.format(fechaHoy);
			final String[] destinos = separaDestinos(this.destinatarios);
			
			try {
		        MimeMessageHelper helper = new MimeMessageHelper(message,true);
				for(String dest : destinos) {
		        	helper.addTo(dest);
				}
		        
		        helper.setFrom(this.origen);
		        helper.setText(mensaje, true);
		        helper.setSubject(subject + " - " + isin + " " + dfNombreArchivo.format(fechaPago));
		        helper.addAttachment("RetencionDerecho_" + idDerecho + "_" + strFechaArchivo + ".xls", byteArrayResource, "application/vnd.ms-excel");
			} catch(Exception e) {
				LOG.error("{} Se presento un problema al enviar el reporte por correo.", e);
			}
	        return message;
	 }
	 
	 /**
	  * separa los destinatarios por coma o punto y coma
	  * @param destino
	  * @return
	  */
	private String[] separaDestinos(String destino) {
		String[] destinos = null;
		if(destino.contains(";")){
			destinos=destino.split(";");
		}else if(destino.contains(",")) {
			destinos=destino.split(",");
		} else {
			destinos = new String[1];
			destinos[0] = destino; 
		}
		return destinos;
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	public String getSubject() {
		return subject;
	}

	public String getPlainTextContent() {
		return plainTextContent;
	}

	public String getHtmlContent() {
		return htmlContent;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setPlainTextContent(String plainTextContent) {
		this.plainTextContent = plainTextContent;
	}

	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}

	public String getDestinatarios() {
		return destinatarios;
	}

	public void setDestinatarios(String destinatarios) {
		this.destinatarios = destinatarios;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

}

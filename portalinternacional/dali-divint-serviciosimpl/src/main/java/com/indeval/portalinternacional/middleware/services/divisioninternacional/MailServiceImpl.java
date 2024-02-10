package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.util.Date;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import com.indeval.portalinternacional.middleware.services.util.MimeMessagePreparatorImpl;

public class MailServiceImpl implements MailService{
	
	private JavaMailSender mailSender;
	private MimeMessagePreparatorImpl messageMimeMessagePreparator;
	private MimeMessagePreparator messagePreparator;
    

	public void sendMail(String message, String from,String to, String subject) {
		messageMimeMessagePreparator.setHtmlContent(String.format(messageMimeMessagePreparator.getHtmlContent(),message));
        mailSender.send(messageMimeMessagePreparator);
	}
	
	public void sendMail(String message) {
		messageMimeMessagePreparator.setHtmlContent(message);
        mailSender.send(messageMimeMessagePreparator);
	}
	
	public void sendMail(String message, String destino, String subject, String from, String encoding) {
		messagePreparator = messageMimeMessagePreparator.createMail(message, destino, subject, from, encoding);
        mailSender.send(messagePreparator);
	}
	
	public void sendMail(byte[] fichero, String mensaje, String subject, String encoding, Long idDerecho, String isin, Date fechaPago) {
		try {
			MimeMessage message = this.mailSender.createMimeMessage();
			message = messageMimeMessagePreparator.createMail(message, fichero, mensaje, subject, encoding, idDerecho, isin, fechaPago);
			this.mailSender.send(message);
		} catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setMessageMimeMessagePreparator(
			MimeMessagePreparatorImpl messageMimeMessagePreparator) {
		this.messageMimeMessagePreparator = messageMimeMessagePreparator;
	}

}
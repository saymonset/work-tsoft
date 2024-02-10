package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.util.Date;

import javax.ejb.Remote;

@Remote
public interface MailService {

	void sendMail(String message,String from,String to,String subject);
	
	public void sendMail(String message);
	
	public void sendMail(String message, String destino, String subject, String from, String encoding);
	
	public void sendMail(byte[] fichero, String mensaje, String subject, String encoding, Long idDerecho, String isin, Date fechaPago);
	
}

package com.indeval.portalinternacional.middleware.services.divisioninternacional.ejb;

import java.util.Date;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;

import com.indeval.portalinternacional.middleware.services.SpringBeanAutowiringInterceptorDivInt;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.MailService;

@Stateless(name = "ejb.mailService", mappedName = "ejb.mailService")
@Interceptors(SpringBeanAutowiringInterceptorDivInt.class)
@Remote(MailService.class)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class MailServiceBean implements MailService{

	@Autowired
	private MailService mailService;
	
	public void sendMail(String message, String from,String to, String subject) {
		mailService.sendMail(message, from,to, subject);		
	}
	
	public void sendMail(String message, String destino, String subject, String from, String encoding){
		mailService.sendMail(message, destino, subject, from, encoding);
	}

	@Override
	public void sendMail(byte[] fichero, String mensaje, String subject, String encoding, Long idDerecho, String isin, Date fechaPago) {
		mailService.sendMail(fichero, mensaje, subject, encoding, idDerecho, isin, fechaPago);
	}

	@Override
	public void sendMail(String message) {
		mailService.sendMail(message);
	}

}

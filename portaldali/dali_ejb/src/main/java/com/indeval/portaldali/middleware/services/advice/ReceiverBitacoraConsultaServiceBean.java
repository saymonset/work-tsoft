package com.indeval.portaldali.middleware.services.advice;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.indeval.portaldali.middleware.services.SpringBeanAutowiringInterceptorDali;


/**
 * EJB para recpecion de los mensajes de la bitacora de consultas
 * 
 * @version 1.0
 * 
 * @author Rafael Ibarra
 * 
 */
@MessageDriven(
		mappedName = "SampleQ1",
		name="ReceiverBitacoraConsultaServiceBean",
		activationConfig = {
				@ActivationConfigProperty(
						propertyName = "destinationType",
						propertyValue = "javax.jms.Queue"
				),
				@ActivationConfigProperty(
						propertyName = "destination", 
						propertyValue = "java:/jms/queue/PortalDali/Request/BitacoraIngresos"
				),
				@ActivationConfigProperty(
						propertyName = "connectionFactory", 
						propertyValue = "java:/jms/QCFXA"
				)
				
		}
)
@Interceptors(SpringBeanAutowiringInterceptorDali.class)
@TransactionManagement(value = TransactionManagementType.CONTAINER)
@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
public class ReceiverBitacoraConsultaServiceBean implements MessageListener {

	
	
	/** Logger de la clase */
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * Servicio para procesar los mensajes
	 */
	@Autowired
	private ReceiverBitacoraConsultaService receiverBitacoraConsultaService;
	

	/**
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	public void onMessage(Message message) {
		logger.debug("Entrando a ReceiverBitacoraConsultaServiceBean.onMessage");
		TextMessage textMessage = null;
        if (message instanceof TextMessage) {
        	textMessage = (TextMessage) message;
            this.recibeMensajes(textMessage);
        } else {
        	logger.error("El mensaje no es un mensaje de Texto");
        }
	}
	
	/**
	 * @see com.indeval.portaldali.middleware.services.advice.ReceiverBitacoraConsultaService#recibeMensajes(javax.jms.TextMessage)
	 */
	public void recibeMensajes(TextMessage textMessage) {
		logger.debug("Entrando a ReceiverBitacoraConsultaServiceBean.recibeMensajes");
		receiverBitacoraConsultaService.recibeMensajes(textMessage);
	}
	
}

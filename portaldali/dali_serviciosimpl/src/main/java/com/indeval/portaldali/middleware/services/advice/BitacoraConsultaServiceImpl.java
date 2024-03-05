/**
 * 
 */
package com.indeval.portaldali.middleware.services.advice;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.indeval.portaldali.middleware.dto.BitacoraIngresosConsulta;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Implementacion del servicio de utilidades para
 * la bitacora de consultas
 * 
 * @author Rafael Ibarra
 *
 */
public class BitacoraConsultaServiceImpl implements BitacoraConsultaService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/** JmsTemplate para el envio de mensajes de la 
	 * bitacora de Consultas */
	private JmsTemplate jmsTemplate;
	
	/** Nombre de la cola de envio */
	private String nameSendQueue;
	
	/**
	 * @see com.indeval.portaldali.middleware.services.advice.BitacoraConsultaService#enviaMensajes(com.indeval.portaldali.middleware.dto.BitacoraIngresosConsulta)
	 */
	public void enviaMensajes(BitacoraIngresosConsulta bitacora) throws Exception {
		logger.debug("Entrando a BitacoraConsultaServiceImpl.enviaMensajes");
		XStream parser = new XStream(new DomDriver());
		parser.alias("bitacoraIngresos", BitacoraIngresosConsulta.class);
		final String mensaje = parser.toXML(bitacora);
		
		jmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				TextMessage message = session.createTextMessage();
				message.setText(mensaje);
				return message;
			}
		});
		logger.debug("Mensaje enviado con exito");
//		log.debug("Mensaje enviado con exito: [" + mensaje + "]");
	}
	
	/**
	 * @param jmsTemplate the jmsTemplate to set
	 */
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
	
	/**
	 * @param nameSendQueue the nameSendQueue to set
	 */
	public void setNameSendQueue(String nameSendQueue) {
		this.nameSendQueue = nameSendQueue;
	}

}

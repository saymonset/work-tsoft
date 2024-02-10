package com.indeval.portalinternacional.middleware.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import com.indeval.workflow.helper.XmlHelper;
import com.indeval.workflow.xml.AuthorizationResponse;

public class AuthorizationMessageConverter implements MessageConverter {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private XmlHelper xmlHelper;

	@Override
	public Object fromMessage(Message message) throws JMSException, MessageConversionException {
		if (!(message instanceof TextMessage))
			throw new MessageConversionException("El mensaje recibido no es un TextMessage.");

		String xmlString = ((TextMessage) message).getText();

		if (!xmlHelper.isXmlString(xmlString))
			throw new MessageConversionException(
					String.format("El mensaje de texto [%s] no es un XML valido.", xmlString));

		try {
			AuthorizationResponse response = xmlHelper.transformXmlStringToObject(xmlString,
					AuthorizationResponse.class);

			if (logger.isDebugEnabled())
				logger.debug(response.toString());

			return response;
		} catch (JAXBException e) {
			logger.error(e.getMessage(), e);
			throw new MessageConversionException(String.format("No se pudo convertir la cadena [%s] a un Objecto [%s]",
					xmlString, AuthorizationResponse.class.getName()));
		}
	}

	@Override
	public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
		if (object == null)
			throw new MessageConversionException("El objeto no es nulo.");
		if (!(object instanceof AuthorizationResponse))
			throw new MessageConversionException("El objeto no es del tipo AuthorizationResponse.");

		try {
			String xmlString = xmlHelper.serialize(object);

			logger.info(String.format("MESSAGE [%s]", xmlString));

			Message message = session.createTextMessage(xmlString);

			return message;
		} catch (JAXBException e) {
			logger.error(e.getMessage(), e);
			throw new MessageConversionException(
					String.format("No se pudo serializar el objeto [%s].", String.valueOf(object)));
		}

	}

	public void setXmlHelper(XmlHelper xmlHelper) {
		this.xmlHelper = xmlHelper;
	}

}

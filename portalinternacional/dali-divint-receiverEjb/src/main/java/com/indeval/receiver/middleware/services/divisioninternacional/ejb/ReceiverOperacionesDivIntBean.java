/**
 * 
 */
package com.indeval.receiver.middleware.services.divisioninternacional.ejb;

import java.io.Serializable;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.access.ContextSingletonBeanFactoryLocator;

import com.bursatec.ejb.support.AbstractJmsMessageDrivenBean;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ReceiverOperacionesDivIntService;

@MessageDriven(name = "ReceiverOperacionesDivIntBean", 
			   messageListenerInterface = MessageListener.class, 
			   mappedName = "ReceiverOperacionesDivIntBean", 
			   activationConfig = {
				@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
				@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/queue/adaptador15022/out/DIVINT"),
				@ActivationConfigProperty(propertyName = "connectionFactory", propertyValue = "java:/jms/QCFXA"),
				@ActivationConfigProperty(propertyName = "minSessions"  , propertyValue="1"),
			    @ActivationConfigProperty(propertyName = "maxSessions", propertyValue = "1")})
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)



public class ReceiverOperacionesDivIntBean extends AbstractJmsMessageDrivenBean
	implements Serializable, ReceiverOperacionesDivIntService {

    /** Objeto de loggeo para ITestEnvioOperacionesService */
	private static final Logger log = LoggerFactory.getLogger(ReceiverOperacionesDivIntBean.class);
    
    /**
     * Constante de serializacion
     */
    private static final long serialVersionUID = 1L;
    
    private ReceiverOperacionesDivIntService receiverOperacionesDivIntService;

    public void setMessageDrivenContext(MessageDrivenContext context) {
        super.setMessageDrivenContext(context);
        setBeanFactoryLocator(ContextSingletonBeanFactoryLocator
                .getInstance("beanRefContextReceiver.xml"));
        setBeanFactoryLocatorKey("baseApplicationContext");
    }

    /**
     * @see org.springframework.ejb.support.AbstractMessageDrivenBean#onEjbCreate()
     */
    @Override
    protected void onEjbCreate() {
	receiverOperacionesDivIntService = (ReceiverOperacionesDivIntService) this
		.getBeanFactory().getBean("receiverOperacionesDivIntService");
    }

    /**
     * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
     */
    public void onMessage(Message mensaje15022) {
	log.debug("Entrando al metodo ReceiverOperacionesDivIntBean.onMessage()");
        TextMessage mensaje = null;
        if (mensaje15022 instanceof TextMessage) {
            mensaje = (TextMessage) mensaje15022;
            this.receiverMessages(mensaje);
        }
    }

    /**
     * @ejbgen:local-method transaction-attribute="NotSupported"
     * @ejbgen:remote-method transaction-attribute="NotSupported"
     * @see com.indeval.portallegado.middleware.services.divisioninternacional.ReceiverOperacionesDivIntService#receiverMessages(javax.jms.TextMessage)
     */
    public void receiverMessages(TextMessage textMessage) {
        receiverOperacionesDivIntService.receiverMessages(textMessage);
    }

    private static String versionNumber;
    static {
          biz.c24.VersionNumber verNumber = new biz.c24.VersionNumber();
          versionNumber = "" + verNumber.getMajorVersion()+ verNumber.getMinorVersion();
    }
}

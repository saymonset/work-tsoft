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
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ReceiverOperacionesMoiService;



@MessageDriven(name = "ReceiverOperacionesMoiBean", 
			   messageListenerInterface = MessageListener.class, 
			   mappedName = "ReceiverOperacionesMoiBean", 
			   activationConfig = {
				@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
				@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/queue/adaptador15022/out/MOI"),
				@ActivationConfigProperty(propertyName = "connectionFactory", propertyValue = "java:/jms/QCFXA"),
				@ActivationConfigProperty(propertyName = "minSessions"  , propertyValue="20"),
			    @ActivationConfigProperty(propertyName = "maxSessions", propertyValue = "20")})
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ReceiverOperacionesMoiBean extends AbstractJmsMessageDrivenBean
	implements Serializable, ReceiverOperacionesMoiService {

    /** Objeto de loggeo para ITestEnvioOperacionesService */
	private static final Logger log = LoggerFactory.getLogger(ReceiverOperacionesMoiBean.class);
    
    /**
     * Constante de serializacion
     */
    private static final long serialVersionUID = 1L;
    
    private ReceiverOperacionesMoiService receiverOperacionesMoiService;

    public void setMessageDrivenContext(MessageDrivenContext context) {
        super.setMessageDrivenContext(context);
        setBeanFactoryLocator(ContextSingletonBeanFactoryLocator
                .getInstance("beanRefContextMoiReceiver.xml"));
        setBeanFactoryLocatorKey("baseApplicationContext");
    }

    /**
     * @see org.springframework.ejb.support.AbstractMessageDrivenBean#onEjbCreate()
     */
    @Override
    protected void onEjbCreate() {
	receiverOperacionesMoiService = (ReceiverOperacionesMoiService) this
		.getBeanFactory().getBean("receiverOperacionesMoiService");
    }

    /**
     * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
     */
    public void onMessage(Message mensaje15022) {
	log.debug("Entrando al metodo ReceiverOperacionesMoiBean.onMessage()");
        TextMessage mensaje = null;
        if (mensaje15022 instanceof TextMessage) {
            mensaje = (TextMessage) mensaje15022;
            this.receiverMessages(mensaje);
        }
    }

    /**
     * @ejbgen:local-method transaction-attribute="NotSupported"
     * @ejbgen:remote-method transaction-attribute="NotSupported"
     * @see com.indeval.portallegado.middleware.services.divisioninternacional.ReceiverOperacionesMoiService#receiverMessages(javax.jms.TextMessage)
     */
    public void receiverMessages(TextMessage textMessage) {
        receiverOperacionesMoiService.receiverMessages(textMessage);
    }

    private static String versionNumber;
    static {
          biz.c24.VersionNumber verNumber = new biz.c24.VersionNumber();
          versionNumber = "" + verNumber.getMajorVersion()+ verNumber.getMinorVersion();
    }
}

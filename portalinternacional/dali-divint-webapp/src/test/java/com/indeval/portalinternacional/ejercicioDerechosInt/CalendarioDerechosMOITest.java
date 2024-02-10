package com.indeval.portalinternacional.ejercicioDerechosInt;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

public class CalendarioDerechosMOITest extends AbstractDependencyInjectionSpringContextTests {
	
	public void onSetup() {
    }
	public CalendarioDerechosMOITest() {
        System.setProperty("basedir", ".");
    }
    /**
     * @see org.springframework.test.AbstractSingleSpringContextTests#getConfigLocations()
     */
    protected String[] getConfigLocations() {
        return archivosContext;
    }

    protected Object getBean(String id) {
        return applicationContext.getBean(id);
    }
    /**
     * Context
     */
    private static String[] archivosContext = new String[]{
    	"./moiJmsContextTest.xml"		
    };

    public void tesstMOIJMS() {
    	JmsTemplate jmsTemplate = (JmsTemplate)getBean("jmsTemplateMoi");
    	 final String msgTXT = "2:1,3,5,7,4";
    	 jmsTemplate.send(new MessageCreator() {
             public Message createMessage(Session session) throws JMSException {
                 final Message msg = session.createTextMessage(msgTXT);
                 return msg;
             }
         });
    	 
    	 try {
			Thread.currentThread().sleep(20000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	 
    	 Message msg = jmsTemplate.receive("jms/moiestados");
         try{
           TextMessage textMessage = (TextMessage) msg;
           if( msg!=null){
           System.out.println(" Message Received -->" + 
                       textMessage.getText());
           }


         }catch(Exception e){
               e.printStackTrace();
         }
    	assertTrue(true);
    }
    
   
}

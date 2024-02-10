package com.indeval.portalinternacional.middleware.services.util;

import com.indeval.protocolofinanciero.api.JmsClient;

public class JmsFactory {
	
	private final String VALPREFV = "VALPREFV";
	private final String SCO = "SCO";
	private final String DIVINT = "DIVINT";
	
	private JmsClient jmsClientVALPREFV;
	
	private JmsClient jmsClientSCO;
	
	private JmsClient jmsClientDivInt;

	/**
	 * Obtiene el jms correspondiente
	 */
	public JmsClient getJmsClient(String origen) {
		if(VALPREFV.equalsIgnoreCase(origen)){
			return jmsClientVALPREFV;
		}
		else if(SCO.equalsIgnoreCase(origen)){
			return jmsClientSCO;
		}
		else if (DIVINT.equalsIgnoreCase(origen)) {
		    	return jmsClientDivInt;
		}
		return null;
	}
	
	public void setJmsClientVALPREFV(JmsClient jmsClientVALPREFV) {
		this.jmsClientVALPREFV = jmsClientVALPREFV;
	}

	public void setJmsClientSCO(JmsClient jmsClientSCO) {
		this.jmsClientSCO = jmsClientSCO;
	}
	
	public void setJmsClientDivInt(JmsClient jmsClientDivInt) {
	    this.jmsClientDivInt = jmsClientDivInt;
	}

}

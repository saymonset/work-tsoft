package com.indeval.portalinternacional.middleware.services.exception.divisioninternacional.divisioninternacional;

import java.util.Hashtable;

import javax.naming.InitialContext;

import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.TemporalBeneficiariosService;

public class TemporalServiceMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		        try {
		        	
		        	
		        	Hashtable<String, String> hashtable= new Hashtable<String, String>();
		        	
		        	hashtable.put("java.naming.factory.initial", "weblogic.jndi.WLInitialContextFactory");
		        	hashtable.put("java.naming.provider.url", "t3://127.0.0.1:9001");
		        	
		            InitialContext ic = new InitialContext(hashtable);		            
		            
		            ControlBeneficiariosService service= 
		            	(ControlBeneficiariosService) 
		            	ic.lookup("ejb.controlBeneficiariosService#com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService");
		            service.consultaBeneficiariosHistorico(null, null);            

		        } catch(Exception e) {
		            e.printStackTrace();
		        }

		    }
}

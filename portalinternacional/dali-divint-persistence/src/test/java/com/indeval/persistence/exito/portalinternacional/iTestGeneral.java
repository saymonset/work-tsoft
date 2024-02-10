package com.indeval.persistence.exito.portalinternacional;

import junit.framework.TestCase;

public class iTestGeneral extends TestCase {
	
	public void test() throws Exception {
		String cadena = "12345678";

        byte array[] = cadena.getBytes();

        for(byte by : array) {
            System.out.println("Byte: [" +  by + "]");
        }


	}
}

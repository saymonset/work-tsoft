/*
 * Copyrigth (c) 2009 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.encrypt;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.TestCase;

/**
 *
 * @author Rafael Ibarra Zendejas
 * @version 1.0
 */
public class TestGenerateKey extends TestCase {

    /** Log de clase */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void testGenerateKey() throws Exception {
        byte[] keyBytes = new byte[]{0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09,
            0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17};

        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");

        FileOutputStream fos = new FileOutputStream("c:\\llave.pk");

        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(key);

        oos.close();
        fos.close();

    }
}

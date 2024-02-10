/*
 * Copyrigth (c) 2009 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.encrypt;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.TestCase;

/**
 *
 * @author Rafael Ibarra Zendejas
 * @version 1.0
 */
public class TestEncrypt extends TestCase {

    /** Log de clase */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void testEncrypt() throws Exception {
        byte[] keyBytes = new byte[]{0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09,
            0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17};

        Key key = KeyGenerator.getInstance("AES").generateKey();
        byte[] input = " www.java2s.com ".getBytes();
        Cipher cipher = Cipher.getInstance("AES");

        log.info("Cadena a encryptar: [" + new String(input) + "]");

        byte[] cipherText = new byte[input.length];
        cipher.init(Cipher.ENCRYPT_MODE, key);
        int ctLength = cipher.update(input, 0, input.length, cipherText, 0);
        ctLength += cipher.doFinal(cipherText, ctLength);
        System.out.println("cipher text: " + new String(cipherText) + " bytes: " + ctLength);

    }

    private SecretKeySpec recuperaLlave() throws Exception {
        FileInputStream fis = new FileInputStream("c:\\llave.pk");
        ObjectInputStream ois = new ObjectInputStream(fis);
        SecretKeySpec key = (SecretKeySpec) ois.readObject();
        ois.close();
        fis.close();
        return key;
    }
}

/*
 *Copyright (c) 2005-2007 Bursatec. All Rights Reserved
 */
package com.indeval.portaldali.presentation.util;


import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import org.apache.axis.encoding.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Cifrador y descifrador de texto que utiliza el algoritmo de blowfish.
 *
 * @author Luis Avia
 * @version 1.0
 */
public class CifradorDescifradorBlowfish {

    /*
     * Log del Sistema
     */
    private Logger logger = LoggerFactory.getLogger(CifradorDescifradorBlowfish.class);

    /**
     * Objeto para generar el hash
     */
    private MessageDigest hashMessage;

    /**
     * Objeto utilizado para realizar el cifrado
     */
    private Cipher cipher;

    /**
     * Variable que contiene arreglo de byte's para el relleno
     */
    private byte[] paddingBytes;

    /**
     * Objeto para generar la llave
     */
    private SecretKeySpec key;

    /**
     * Objeto para el relleno de byte's
     */
    private IvParameterSpec ivSpec;

    /**
     * Crea una nueva instancia de la clase Inicializando cada uno de los
     * objetos Para realizar el hash,cifrado y decifrado de los datos
     *
     * @throws HashFirmaDigitalException Se lanza excepción en caso de que no pueda crearsela instancia
     */
    public CifradorDescifradorBlowfish() throws HashFirmaDigitalException {

        try {
            ResourceBundle rb = ResourceBundle.getBundle("util.Blowfish");
            String keyProp = rb.getString("key.settings");
            String ivProp = rb.getString("iv.settings");
            //Llave privada utilizada para cifrar.
            byte[] keyBytes = keyProp.getBytes();
            //Relleno agregar al cifrado.
            paddingBytes = ivProp.getBytes();
            key = new SecretKeySpec(keyBytes, "Blowfish");
            ivSpec = new IvParameterSpec(new byte[8]);
            hashMessage = MessageDigest.getInstance("SHA-1");
            cipher = Cipher.getInstance("Blowfish/CBC/PKCS5Padding");
        }
        catch (NoSuchAlgorithmException nsae) {

            nsae.printStackTrace();
            logger.error(nsae.getMessage(), nsae);
            throw new HashFirmaDigitalException(nsae);
        }

        catch (NoSuchPaddingException nspae) {

            nspae.printStackTrace();
            logger.error(nspae.getMessage(), nspae);
            throw new HashFirmaDigitalException(nspae);
        }
    }

    /**
     * Transformación de datos a hash Sha 1.
     *
     * @param message Datos a transformar a hash sha1.
     * @return Hash en sha1 convertido a hexadecimal
     */
    public String sha1Message(String message) {

        hashMessage.update(message.getBytes());
        String digestSha1;
        digestSha1 = new String(toHex(hashMessage.digest()));
        return digestSha1;

    }

    /**
     * Transformación de bytes a hexadecimal.
     *
     * @param data Arreglo de bytes a transformar a hexadecimal.
     * @return Arreglo de bytes en formato hexadecimal
     */
    private static byte[] toHex(byte[] data) {

        StringBuffer out = new StringBuffer();
        for (int i = 0, j = data.length; i < j; i++) {
            String aux = Integer.toString((((int) ((char) data[i])) & 0xFF), 16);
            if (aux.length() == 1) {
                aux = "0" + aux;
            }
            out.append(aux);
        }
        return out.toString().getBytes();
    }


    /**
     * Cifrado de datos.
     *
     * @param str Datos a encriptar.
     * @return Datos cifrados.
     * @throws HashFirmaDigitalException Se lanza excepción en caso ej.
     *                                   Llave invalida, algoritmo invalido, mal relleno, etc.
     */
    public String cipherHash(String str) throws HashFirmaDigitalException {

        try {

            String hash = sha1Message(str);
            cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
            byte[] cipher_array = new byte[cipher.getOutputSize(hash.getBytes().length + paddingBytes.length)];
            int ctLength = cipher.update(paddingBytes, 0, paddingBytes.length, cipher_array, 0);
            ctLength += cipher.update(hash.getBytes(), 0, hash.getBytes().length, cipher_array, ctLength);
            ctLength += cipher.doFinal(cipher_array, ctLength);
            String cipherText = new String(Base64.encode(cipher_array));
            return cipherText;
        }
        catch (InvalidKeyException e) {

            logger.error(e.getMessage(), e);
            throw new HashFirmaDigitalException(e);
        }
        catch (InvalidAlgorithmParameterException e) {

            logger.error(e.getMessage(), e);
            throw new HashFirmaDigitalException(e);
        }
        catch (ShortBufferException e) {

            logger.error(e.getMessage(), e);
            throw new HashFirmaDigitalException(e);
        }
        catch (IllegalBlockSizeException e) {

            logger.error(e.getMessage(), e);
            throw new HashFirmaDigitalException(e);
        }
        catch (BadPaddingException e) {

            logger.error(e.getMessage(), e);
            throw new HashFirmaDigitalException(e);
        }
    }

    /**
     * Descifrado de datos.
     *
     * @param str Datos a descifrar.
     * @return Datos descifrados.
     * @throws HashFirmaDigitalException Se lanza excepción en caso ej.
     *                                   Llave invalida, algoritmo invalido, mal relleno, etc.
     */
    private String decipherHash(String str) throws HashFirmaDigitalException {

        try {

            byte[] cipherData = Base64.decode(str);
            int cLength = cipherData.length;
            cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
            byte[] buf = new byte[cipher.getOutputSize(cLength)];
            int bufLength = cipher.update(cipherData, 0, cLength, buf, 0);
            bufLength += cipher.doFinal(buf, bufLength);
            byte[] plain = new byte[bufLength - paddingBytes.length];
            System.arraycopy(buf, paddingBytes.length, plain, 0, plain.length);
            String plainText = new String(plain);
            return plainText;
        }
        catch (InvalidKeyException e) {

            logger.error(e.getMessage(), e);
            throw new HashFirmaDigitalException(e);
        }
        catch (InvalidAlgorithmParameterException e) {

            logger.error(e.getMessage(), e);
            throw new HashFirmaDigitalException(e);
        }
        catch (ShortBufferException e) {

            logger.error(e.getMessage(), e);
            throw new HashFirmaDigitalException(e);
        }
        catch (IllegalBlockSizeException e) {

            logger.error(e.getMessage(), e);
            throw new HashFirmaDigitalException(e);
        }
        catch (BadPaddingException e) {

            logger.error(e.getMessage(), e);
            throw new HashFirmaDigitalException(e);
        }
    }

    /**
     * Descifrado de datos.
     *
     * @param hashOper Datos cifrados de la captura de operaciones.
     * @param dataFirm Datos a convertir hash sha1.
     * @return Regresa si el resultado de comparar los hash
     * @throws HashFirmaDigitalException Se lanza excepción en caso de que no se pueda descifrar el hash
     */
    public boolean validation(String hashOper, String dataFirm) throws HashFirmaDigitalException {
        String hashDecipher = decipherHash(hashOper);
        String hash = sha1Message(dataFirm);

        if (hashDecipher.equals(hash))
            return true;
        else
            return false;
    }

}


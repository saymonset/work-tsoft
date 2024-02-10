/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.util;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import org.bouncycastle.util.encoders.Base64;

import com.indeval.portaldali.middleware.services.util.ConvertBO2VO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PrivateKeyVO;
import com.indeval.protocolofinanciero.api.PrivateKeyProvider;
import com.indeval.protocolofinanciero.api.impl.PkiProviderException;

/**
 * Implementaci&oacute;n que obtiene una llave privada de una tabla de la base
 * de datos. La tabla es SEGU_LLAVE_PRIVADA (la tabla de llaves privadas de la
 * seguridad)
 * 
 * @author Alejandro Rodr&iacute;guez
 */
public class DatabasePrivateKeyProvider extends
		AbstractDatabaseSecurityProvider implements PrivateKeyProvider {
	
	/* Variable que guarda la informaci&oacute;n de la llave privada */
	private transient PrivateKey privateKey;

	/* Variable que guarda el nombre del sistema */
	private transient String nombreSistema;

	/* Variable que guarda el nombre del usuario asociado al sistema */
	private transient String user;

	/* Variable que guarda la informaci&oacute;n de seguridad obtenida de la bd */
	private transient PrivateKeyVO securityInfo;

	/**
	 * @see com.indeval.protocolofinanciero.api.PrivateKeyProvider#getCertificateSerialNumber()
	 */
	public String getCertificateSerialNumber() {
		return getSecurityInfo().getSerialNumber();
	}

	/**
	 * @see com.indeval.protocolofinanciero.api.PrivateKeyProvider#getPrivateKey()
	 */
	public PrivateKey getPrivateKey() throws PkiProviderException {
		if (privateKey == null) {
			final String privateKeyBase64 = getPrimaryKeyfromSystemName();
			if (privateKeyBase64 != null) {
				try {
					privateKey = getPrivateKey(privateKeyBase64);
				} catch (NoSuchAlgorithmException e) {
					for (StackTraceElement ste : e.getStackTrace()) {
						log.debug(ste.toString());
					}
				} catch (InvalidKeySpecException e) {
					for (StackTraceElement ste : e.getStackTrace()) {
						log.debug(ste.toString());
					}
				}
			}
		}
		return privateKey;
	}

	/**
	 * Obtiene la PrimaryKey dado el nombre del sistema.
	 * 
	 * @return String PrivateKey
	 */
	private String getPrimaryKeyfromSystemName() {
		return getSecurityInfo().getPrivateKey();
	}

	/**
	 * Obtiene la informaci&iacute; de seguridad dado un nombre de sistema 
	 * y usuario
	 * 
	 * @return PrivateKeyVO securityInfo
	 */
	private PrivateKeyVO getSecurityInfo() {
		if (securityInfo == null) {
			securityInfo = ConvertBO2VO.crearPrivateKeyVO(securityDao
					.getSecurityInfofromSystemName(nombreSistema, user));
		}
		return securityInfo;
	}

	/**
	 * Obtiene el valor de la llave privada de su representaci&oacute;n en
	 * base 64.
	 * 
	 * @param privateKeyBase64
	 *                La representaci&oacute;n en base 64 de una llave
	 *                privada
	 * @return La llave privada
	 * @throws java.security.NoSuchAlgorithmException
	 *                 en caso de no haber encontrado el proveedor de
	 *                 seguridad para RSA
	 * @throws java.security.spec.InvalidKeySpecException
	 *                 en caso de no haber podido decodidar la llave privada
	 */
	private PrivateKey getPrivateKey(final String privateKeyBase64)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		final byte[] keybuf = Base64.decode(privateKeyBase64);
		final PKCS8EncodedKeySpec prvSpec = new PKCS8EncodedKeySpec(keybuf);
		final KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePrivate(prvSpec);
	}

	/**
	 * Asigna el valor de la propiedad <code>nombreSistema</code>.
	 * 
	 * @param nombreSistema
	 *                El nombre del Sistema al que est&aacute; asociada la
	 *                llave privada
	 */
	public void setNombreSistema(String nombreSistema) {
		this.nombreSistema = nombreSistema;
	}

	/**
	 * Asigna el valor de la propiedad <code>user</code>.
	 * 
	 * @param user
	 *                El nombre del usuario al que est&aacute; asociada al
	 *                sistema
	 */
	public void setUser(String user) {
		this.user = user;
	}
}
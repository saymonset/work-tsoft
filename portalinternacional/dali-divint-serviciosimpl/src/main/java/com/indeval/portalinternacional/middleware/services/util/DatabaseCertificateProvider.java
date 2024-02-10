package com.indeval.portalinternacional.middleware.services.util;

import java.io.ByteArrayInputStream;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import org.bouncycastle.util.encoders.Base64;

import com.indeval.protocolofinanciero.api.CertificateProvider;
import com.indeval.protocolofinanciero.api.impl.PkiProviderException;

/**
 * Implementaci&oacute;n que obtiene un certificado de una tabla de la base de
 * datos. La tabla es SEGU_CERTIFICADOS (la tabla de certificados de la
 * seguridad)
 * 
 * @author Alejandro Rodr&iacute;guez
 */
public class DatabaseCertificateProvider extends AbstractDatabaseSecurityProvider implements CertificateProvider
{
    /* Variable que almacena el certificado */
    private transient X509Certificate certificate;

    /* Variable que guarda el n&uacute;mero de serie de certificado */
    private transient String serialNumber;

    /*
     * (non-Javadoc)
     * 
     * @see com.indeval.protocolofinanciero.api.CertificateProvider#getPublicKey()
     */
    public PublicKey getPublicKey() throws PkiProviderException
    {
	return getCertificate().getPublicKey();
    }

    /**
     * @see com.indeval.pki.CertificateProvider#getCertificate()
     */
    private X509Certificate getCertificate()
    {
	if (certificate == null)
	{
	    final String certificateBase64 = getCertificadoFromSerialNumber();
	    if (certificateBase64 != null)
	    {
		try
		{
		    certificate = getCertificate(certificateBase64);
		} catch (CertificateException e)
		{
		    for (StackTraceElement ste : e.getStackTrace())
		    {
			log.debug("Ocurrio un error:",ste.toString());
		    }
		}
	    }
	}
	return certificate;
    }

    /**
     * Obtiene un certificado a partir de su representaci&oacute;n en base
     * 64.
     * 
     * @param certificateBase64
     *                Representaci&oacute;n en base 64 de un certificado
     * @return Certificado X.509
     * @throws java.security.cert.CertificateException
     *                 Se lanza en caso de no haber podido obtener el
     *                 certificado
     */
    private X509Certificate getCertificate(final String certificateBase64) throws CertificateException
    {
	final byte[] bytes = Base64.decode(certificateBase64.getBytes());
	final CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
	return (X509Certificate) certificateFactory.generateCertificate(new ByteArrayInputStream(bytes));
    }

    /**
     * M&eacute;todo que obtiene el certificado dado un n&uacute;mero de
     * serie.
     * 
     * @return String Certificado
     */
    private String getCertificadoFromSerialNumber()
    {
	return securityDao.getCertificadoFromSerialNumber(serialNumber);
    }

    /**
     * Asigna el valor de la propiedad <code>serialNumber</code>.
     * 
     * @param serialNumber
     *                El n&uacute;mero de serie de certificado
     */
    public void setSerialNumber(String serialNumber)
    {
	this.serialNumber = serialNumber;
    }
}
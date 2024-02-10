/**
 * Bursatec - Portal Internacional Copyright (c) 2015 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.lang.reflect.Field;

import com.indeval.portaldali.middleware.formatosw.FormaGeneral;
import com.indeval.portaldali.middleware.formatosw.FormaW8BENE;
import com.indeval.portaldali.middleware.formatosw.FormaW8IMY2015;
import com.indeval.portaldali.middleware.formatosw.FormaW8IMY2017;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoW8BENE;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoW8IMY2015;
import com.indeval.portalinternacional.persistence.dao.FormatoW8Dao;
import com.thoughtworks.xstream.XStream;

/**
 * @author amoralesm
 * 
 */
public class FormatoW8ServiceImpl implements FormatoW8Service {

    private FormatoW8Dao formatoW8Dao;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.indeval.portalinternacional.middleware.services.divisioninternacional.FormatoW8Service
     * #generarXmlCamposFormato(com.indeval.portaldali.middleware.formatosw.FormaW8BENE)
     */
    public String generarXmlCamposFormato(final FormaW8BENE forma) {
        String result = null;
        XStream xstream = new XStream();

        xstream.alias("camposFormato", FormaW8BENE.class);

        // Omitir atributos de FormaGeneral
        Class<FormaGeneral> clazz = FormaGeneral.class;
        for (Field field : clazz.getDeclaredFields()) {
            xstream.omitField(FormaGeneral.class, field.getName());
        }

        // Transformación del objeto a XML
        result = xstream.toXML(forma);

        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.indeval.portalinternacional.middleware.services.divisioninternacional.FormatoW8Service
     * #generarXmlCamposFormato(com.indeval.portaldali.middleware.formatosw.FormaW8IMY2015)
     */
    public String generarXmlCamposFormato(final FormaW8IMY2015 forma) {
        String result = null;
        XStream xstream = new XStream();

        xstream.alias("camposFormato", FormaW8IMY2015.class);

        // Omitir atributos de FormaGeneral
        Class<FormaGeneral> clazz = FormaGeneral.class;
        for (Field field : clazz.getDeclaredFields()) {
            xstream.omitField(FormaGeneral.class, field.getName());
        }

        // Transformacion del objeto a XML
        result = xstream.toXML(forma);

        return result;
    }

    public String generarXmlCamposFormato(final FormaW8IMY2017 forma) {
        return this.generaXmlFormaBeneficiarios(forma);
    }

    // TODO: cambiar por esta implementacion los demas formatos
    public <T extends FormaGeneral> String generaXmlFormaBeneficiarios(final T forma) {
        String result = null;
        XStream xstream = new XStream();
        xstream.alias("camposFormato", forma.getClass());
        // Omitir atributos de FormaGeneral
        Class<FormaGeneral> clazz = FormaGeneral.class;
        for (Field field : clazz.getDeclaredFields()) {
            xstream.omitField(FormaGeneral.class, field.getName());
        }
        // Transformacion del objeto a XML
        result = xstream.toXML(forma);

        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.indeval.portalinternacional.middleware.services.divisioninternacional.FormatoW8Service
     * #obtenerCamposFormatoW8BENE(java.lang.Long)
     */
    public FormaW8BENE obtenerCamposFormatoW8BENE(final Long idCamposFormato) {
        FormatoW8BENE formato = this.formatoW8Dao.findCamposFormatoW8BeneById(idCamposFormato);
        FormaW8BENE forma =
                (FormaW8BENE) this.obtenerXmlCamposFormato(FormaW8BENE.class,
                        formato.getCamposFormato());

        return forma;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.indeval.portalinternacional.middleware.services.divisioninternacional.FormatoW8Service
     * #obtenerCamposFormatoW8IMY2015(java.lang.Long)
     */
    public FormaW8IMY2015 obtenerCamposFormatoW8IMY2015(final Long idCamposFormato) {
        FormatoW8IMY2015 formato =
                this.formatoW8Dao.findCamposFormatoW8Imy2015ById(idCamposFormato);
        FormaW8IMY2015 forma =
                (FormaW8IMY2015) this.obtenerXmlCamposFormato(FormaW8IMY2015.class,
                        formato.getCamposFormato());

        return forma;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.indeval.portalinternacional.middleware.services.divisioninternacional.FormatoW8Service
     * #obtenerCamposFormatoW8IMY2015(java.lang.Long)
     */
    public FormaW8IMY2017 obtenerCamposFormatoW8IMY2017(final Long idCamposFormato) {
        FormatoW8IMY2015 formato =
                this.formatoW8Dao.findCamposFormatoW8Imy2015ById(idCamposFormato);
        FormaW8IMY2017 forma =
                (FormaW8IMY2017) this.obtenerXmlCamposFormato(FormaW8IMY2017.class,
                        formato.getCamposFormato());

        return forma;
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * com.indeval.portalinternacional.middleware.services.divisioninternacional.FormatoW8Service
     * #obtenerCamposFormatoW8IMY2015(java.lang.String)
     */
    public FormaW8IMY2017 obtenerCamposFormatoW8IMY2017(final String camposFormato) {
        return (FormaW8IMY2017) this.obtenerXmlCamposFormato(FormaW8IMY2017.class, camposFormato);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.indeval.portalinternacional.middleware.services.divisioninternacional.FormatoW8Service
     * #obtenerCamposFormato(java.lang.String)
     */
    public FormaW8BENE obtenerCamposFormato(final String camposFormato) {
        return (FormaW8BENE) this.obtenerXmlCamposFormato(FormaW8BENE.class, camposFormato);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.indeval.portalinternacional.middleware.services.divisioninternacional.FormatoW8Service
     * #obtenerCamposFormatoW8IMY2015(java.lang.String)
     */
    public FormaW8IMY2015 obtenerCamposFormatoW8IMY2015(final String camposFormato) {
        return (FormaW8IMY2015) this.obtenerXmlCamposFormato(FormaW8IMY2015.class, camposFormato);
    }

    /**
     * Genera un objeto a partir de un XML para W8BENE
     * 
     * @param camposFormato
     * @return
     */
    private FormaGeneral obtenerXmlCamposFormato(final Class clazz, final String camposFormato) {

        XStream xstream = new XStream();
        xstream.alias("camposFormato", clazz);

        return (FormaGeneral) xstream.fromXML(camposFormato);
    }

    /**
     * @param formatoW8Dao the formatoW8Dao to set
     */
    public void setFormatoW8Dao(final FormatoW8Dao formatoW8Dao) {
        this.formatoW8Dao = formatoW8Dao;
    }

}

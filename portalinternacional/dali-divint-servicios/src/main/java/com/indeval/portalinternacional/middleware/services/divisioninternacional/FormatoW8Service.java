/**
 * Bursatec - Portal Internacional Copyright (c) 2015 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import com.indeval.portaldali.middleware.formatosw.FormaGeneral;
import com.indeval.portaldali.middleware.formatosw.FormaW8BENE;
import com.indeval.portaldali.middleware.formatosw.FormaW8IMY2015;
import com.indeval.portaldali.middleware.formatosw.FormaW8IMY2017;

/**
 * Interfaz de negocio para los servicios del formato W8BENE y W8 IMY 2015.
 * 
 * @author Abraham Morales
 * 
 */
public interface FormatoW8Service {

    /**
     * Obtiene un XML a partir de un POJO para W8 BENE
     * 
     * @param forma
     * @return
     */
    String generarXmlCamposFormato(FormaW8BENE forma);

    /**
     * Obtiene un XML a partir de un POJO para W8 IMY 2015
     * 
     * @param forma
     * @return
     */
    String generarXmlCamposFormato(FormaW8IMY2015 forma);

    /**
     * Recupera de la BD el contenido del W8 BENE y genera el POJO
     * 
     * @param idCamposFormato
     * @return
     */
    FormaW8BENE obtenerCamposFormatoW8BENE(Long idCamposFormato);

    /**
     * Recupera de la BD el contenido del W8 IMY 2015 y genera el POJO
     * 
     * @param idCamposFormato
     * @return
     */
    FormaW8IMY2015 obtenerCamposFormatoW8IMY2015(Long idCamposFormato);

    /**
     * Recupera de la BD el contenido del W8 IMY 2017 y genera el POJO
     * 
     * @param idCamposFormato
     * @return
     */
    FormaW8IMY2017 obtenerCamposFormatoW8IMY2017(Long idCamposFormato);

    /**
     * Genera un POJO W8 BENE a partir de un XML
     * 
     * @param camposFormato
     * @return
     */
    FormaW8BENE obtenerCamposFormato(String camposFormato);

    /**
     * Genera un POJO W8 IMY 2015 a partir de un XML
     * 
     * @param camposFormato
     * @return
     */
    FormaW8IMY2015 obtenerCamposFormatoW8IMY2015(String camposFormato);

    String generarXmlCamposFormato(FormaW8IMY2017 forma);

    <T extends FormaGeneral> String generaXmlFormaBeneficiarios(final T forma);

    public FormaW8IMY2017 obtenerCamposFormatoW8IMY2017(final String camposFormato);

}

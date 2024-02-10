/**
 * Copyright (c) 2015 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.dao;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoW8BENE;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoW8IMY2015;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoW8IMY2017;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.CamposW;

/**
 * DAO para T_CAMPOS_FORMATO_W8BENE y T_CAMPOS_FORMATO_W8IMY2015
 * 
 * @author Abraham Morales
 * 
 */
public interface FormatoW8Dao extends BaseDao {

    /**
     * Recupera el registro con el contenido del formato W8BENE de acuerdo a su ID
     * 
     * @param idCamposFormato
     * @return
     */
    FormatoW8BENE findCamposFormatoW8BeneById(Long idCamposFormato);

    /**
     * Recupera el registro con el contenido del formato W8IMY 2015 de acuerdo a su ID
     * 
     * @param idCamposFormato
     * @return
     */
    FormatoW8IMY2015 findCamposFormatoW8Imy2015ById(Long idCamposFormato);

    FormatoW8IMY2017 findCamposFormatoW8Imy2017ById(Long idCamposFormato);
    
    public CamposW findCamposFormatoW(final Long idBeneficiario, final String tipoFormato);

}

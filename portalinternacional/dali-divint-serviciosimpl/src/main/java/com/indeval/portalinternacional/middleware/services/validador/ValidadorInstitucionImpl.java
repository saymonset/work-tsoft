/**
 * Copyrigth (c) 2017 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.validador;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.InstitucionesService;
import com.indeval.portalinternacional.middleware.servicios.vo.InstitucionesVo;

/**
 * Implementación de la interfaz ValidadorInstitucion
 * 
 */
public class ValidadorInstitucionImpl implements ValidadorInstitucion {

    /** Logger */
	private final static Logger LOG = LoggerFactory.getLogger(ValidadorInstitucionImpl.class);

    private ValidadorLongitud validadorLongitud;

    private InstitucionesService institucionesService;

    private final static int LONGITUD_IDFOLIO = 5;

    /**
     * @see com.indeval.sidv.emisiones.middleware.service.validador.ValidadorInstitucion#validarExistencia(String)
     */
    public InstitucionesVo validarExistencia(String idFolio) throws BusinessException {
        LOG.info("####### Entrando a ValidadorInstitucionImpl.validarExistencia()...");
        this.validadorLongitud.validarLongitudCadena(idFolio, LONGITUD_IDFOLIO, "Id y Folio");
        String idInst = idFolio.substring(0, 2);
        String folioInst = idFolio.substring(2);
        InstitucionesVo ivo = this.institucionesService.getInstitucionByTipoYFolio(Integer.valueOf(idInst), folioInst);
        if (ivo == null) {
            throw new BusinessException("La instituci\u00F3n no existe!");
        }
        return ivo;
    }

    public void setValidadorLongitud(ValidadorLongitud validadorLongitud) {
        this.validadorLongitud = validadorLongitud;
    }

    public void setInstitucionesService(InstitucionesService institucionesService) {
        this.institucionesService = institucionesService;
    }

}

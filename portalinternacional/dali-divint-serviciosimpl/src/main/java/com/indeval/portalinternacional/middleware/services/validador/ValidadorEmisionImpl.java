/**
 * Copyrigth (c) 2017 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.validador;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.persistence.modelo.Cupon;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.EmisionesConsultasService;
import com.indeval.portalinternacional.middleware.servicios.modelo.Emisiones;

/**
 * Implementación de la interfaz ValidadorEmision
 * 
 */
public class ValidadorEmisionImpl implements ValidadorEmision {

    /** Logger */
	private final static Logger LOG = LoggerFactory.getLogger(ValidadorEmisionImpl.class);

    private EmisionesConsultasService emisionesConsultasService;

    private static final int ID_ESTATUS_CUPON_VIGENTE = 1;

    /**
     * @see com.indeval.sidv.emisiones.middleware.service.validador.ValidadorEmision#validarEmisionLiberada(String, String, String, String)
     */
    public Emisiones validarEmisionLiberada(String tv, String emisora, String serie, String isin) throws BusinessException {
        LOG.info("####### Entrando a ValidadorEmisionImpl.validarEmisionLiberada(tv,emisora,serie,isin)...");
        if (tv == null) {
            throw new BusinessException("El tipo valor no viene incluido!");
        }
        if (emisora == null) {
            throw new BusinessException("La emisora no viene incluida!");
        }
        if (serie == null) {
            throw new BusinessException("La serie no viene incluida!");
        }
        if (isin == null) {
            throw new BusinessException("El ISIN no viene incluido!");
        }
        Emisiones emision = this.emisionesConsultasService.obtenerEmisionLiberada(tv, emisora, serie, isin);
        if (emision == null) {
            throw new BusinessException("La Emisi\u00F3n no existe!");
        }

        return emision;
    }

    /**
     * @see com.indeval.sidv.emisiones.middleware.service.validador.ValidadorEmision#validarEmisionLiberada(String)
     */
    public Emisiones validarEmisionLiberada(String isin) throws BusinessException {
        LOG.info("####### Entrando a ValidadorEmisionImpl.validarEmisionLiberada(isin)...");
        if (isin == null) {
            throw new BusinessException("El ISIN no viene incluido!");
        }
        Emisiones emision = this.emisionesConsultasService.obtenerEmisionLiberada(isin);
        if (emision == null) {
            throw new BusinessException("La Emisi\u00F3n no existe!");
        }

        return emision;
    }

    /**
     * @see com.indeval.sidv.emisiones.middleware.service.validador.ValidadorEmision#validarEmisionLiberada(String, String, String)
     */
    public Emisiones validarEmisionLiberada(String tv, String emisora, String serie) throws BusinessException {
        LOG.info("####### Entrando a ValidadorEmisionImpl.validarEmisionLiberada(tv,emisora,serie)...");
        if (tv == null) {
            throw new BusinessException("El tipo valor no viene incluido!");
        }
        if (emisora == null) {
            throw new BusinessException("La emisora no viene incluida!");
        }
        if (serie == null) {
            throw new BusinessException("La serie no viene incluida!");
        }
        Emisiones emision = this.emisionesConsultasService.obtenerEmisionLiberada(tv, emisora, serie);
        if (emision == null) {
            throw new BusinessException("La Emisi\u00F3n no existe!");
        }

        return emision;
    }

    /**
     * @see com.indeval.sidv.emisiones.middleware.service.validador.ValidadorEmision#validarCuponVigente(Emisiones)
     */
    public Cupon validarCuponVigente(Emisiones emision) throws BusinessException {
        LOG.info("####### Entrando a ValidadorEmisionImpl.validarCuponVigente()...");
        if (emision == null) {
            throw new BusinessException("La Emisi\u00F3n no existe!");
        }
        Set<Cupon> cupones = emision.getCupones();
        if (cupones == null) {
            throw new BusinessException("La Emisi\u00F3n no contiene ning\u00FAn cup\u00F3n!");
        }
        List<Cupon> cuponesVigentes = new ArrayList<Cupon>();
        for (Cupon cupon : cupones) {
            if (cupon.getEstadoCupon().getIdEstatusCupon().intValue() == ID_ESTATUS_CUPON_VIGENTE) {
                cuponesVigentes.add(cupon);
            }
        }
        if (cuponesVigentes.isEmpty()) {
            throw new BusinessException("La Emisi\u00F3n no contiene ning\u00FAn cup\u00F3n vigente!");
        }

        return cuponesVigentes.get(0);
    }

    public void setEmisionesConsultasService(EmisionesConsultasService emisionesConsultasService) {
        this.emisionesConsultasService = emisionesConsultasService;
    }

}

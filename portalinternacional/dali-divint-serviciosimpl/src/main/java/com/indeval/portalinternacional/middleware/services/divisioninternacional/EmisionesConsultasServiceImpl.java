/**
 * Copyright (c) 2017 Bursatec. All Rights Reserved
 */

package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.math.BigDecimal;
import java.util.List;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;
import com.indeval.portaldali.persistence.dao.common.EmisionDao;
import com.indeval.portalinternacional.middleware.servicios.modelo.Emisiones;
import com.indeval.portalinternacional.middleware.servicios.modelo.OperacionSic;
import com.indeval.portalinternacional.persistence.dao.EmisionesConsultasDao;
import com.indeval.portalinternacional.persistence.dao.OperacionSicDao;

/**
 * Implementaci&oacute;n de la interfaz de servicio EmisionesConsultasService
 */
public class EmisionesConsultasServiceImpl implements EmisionesConsultasService {

    /** Invocaci&oacute;n al servicio <code>emisionesConsultasDao</code>. **/
    private EmisionesConsultasDao emisionesConsultasDao;

    /**
     * Dao para el acceso a la consulta de emisiones
     */
    private EmisionDao emisionDao;

    /**
     * Dao para operaciones SIC
     */
    private OperacionSicDao operacionSicDao;

    /**
     * @see com.indeval.sidv.emisiones.middleware.service.EmisionesConsultasService#obtenerEmisionLiberada(String, String, String, String)
     */
    public Emisiones obtenerEmisionLiberada(String tv, String emisora, String serie, String isin) {
        return this.emisionesConsultasDao.obtenerEmisionLiberada(tv, emisora, serie, isin);
    }

    /**
     * @see com.indeval.sidv.emisiones.middleware.service.EmisionesConsultasService#obtenerEmisionLiberada(String)
     */
    public Emisiones obtenerEmisionLiberada(String isin) {
        return this.emisionesConsultasDao.obtenerEmisionByIsin(isin);
    }

    /**
     * @see com.indeval.sidv.emisiones.middleware.service.EmisionesConsultasService#obtenerEmisionLiberada(String, String, String)
     */
    public Emisiones obtenerEmisionLiberada(String tv, String emisora, String serie) {
        return this.emisionesConsultasDao.obtenerEmisionLiberada(tv, emisora, serie);
    }

    public BigDecimal getSaldo(Integer idEmision, Integer idCuenta, Integer idBoveda, Integer idCuponVigente) throws BusinessException {
        return this.emisionesConsultasDao.getSaldo(idEmision, idCuenta, idBoveda, idCuponVigente);
    }

    public BigDecimal getSaldoTotal(Integer idEmision, Integer idBoveda, Integer idCuponVigente) throws BusinessException {
        return this.emisionesConsultasDao.getSaldoTotal(idEmision, idBoveda, idCuponVigente);
    }

    public BigDecimal getSaldoPosicionNoDisponible(Integer idEmision, Integer idBoveda, Integer idCuponVigente) throws BusinessException {
        return this.emisionesConsultasDao.getSaldoPosicionNoDisponible(idEmision, idBoveda, idCuponVigente);
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.EmisionesConsultasService#consultarEmisionActualEnCambioBoveda(EmisionVO)
     */
    public boolean consultarEmisionActualEnCambioBoveda(EmisionVO emisionVO) throws BusinessException {
        boolean resultado = false;

        try {
            Emisiones emision = this.emisionesConsultasDao.obtenerEmisionLiberada(emisionVO.getTv(), emisionVO.getEmisora(), 
                                                                                  emisionVO.getSerie(), emisionVO.getIsin());
            resultado = this.seEncuentraEnCambioBoveda(emision.getIdEmision());
        } catch(Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }

        return resultado;
    }

    /**
     * Realiza la busqueda de operaciones de entrega y recepcion para validar si la emision se encuentra participando
     * en un cambio de boveda.
     * @param idEmision
     * @return
     */
    private boolean seEncuentraEnCambioBoveda(Long idEmision) {
        boolean enCB = false;
        List<OperacionSic> operaciones =
        	operacionSicDao.findOperacionSicCambioBovedaEnTransito(idEmision);
        enCB = operaciones != null && !operaciones.isEmpty();
        return enCB;
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.EmisionesConsultasService#consultarEmisionActualEnCambioBovedaByIsin(String)
     */
    public boolean consultarEmisionActualEnCambioBovedaByIsin(String isin) throws BusinessException {
        boolean resultado = false;

        try {
            Emisiones emision = this.emisionesConsultasDao.obtenerEmisionByIsin(isin);
            resultado = this.seEncuentraEnCambioBoveda(emision.getIdEmision());
        } catch(Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }

        return resultado;
    }

    public void setEmisionesConsultasDao(EmisionesConsultasDao emisionesConsultasDao) {
        this.emisionesConsultasDao = emisionesConsultasDao;
    }

    public void setEmisionDao(EmisionDao emisionDao) {
        this.emisionDao = emisionDao;
    }

    public void setOperacionSicDao(OperacionSicDao operacionSicDao) {
        this.operacionSicDao = operacionSicDao;
    }

}

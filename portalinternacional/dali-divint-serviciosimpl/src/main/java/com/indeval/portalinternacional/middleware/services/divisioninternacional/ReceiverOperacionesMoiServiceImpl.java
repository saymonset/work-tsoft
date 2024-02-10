/**
 * 
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.util.Iterator;
import java.util.List;

import javax.jms.TextMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.util.DateUtilService;
import com.indeval.portaldali.persistence.dao.common.BitacoraOperacionesDao;
import com.indeval.portaldali.persistence.modelo.BitacoraOperaciones;
import com.indeval.portalinternacional.middleware.services.util.TransformaMensaje;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstatusOperacion;
import com.indeval.portalinternacional.middleware.servicios.modelo.OperacionSic;
import com.indeval.portalinternacional.persistence.dao.OperacionSicDao;
import com.indeval.protocolofinanciero.api.vo.AcuseVO;
import com.indeval.protocolofinanciero.api.vo.ConfirmacionLiquidacionVO;
import com.indeval.protocolofinanciero.api.vo.ErrorVO;
import com.indeval.protocolofinanciero.api.vo.StatusVO;
import com.indeval.protocolofinanciero.api.vo.TraspasoEfectivoVO;

/**
 * @author javiles
 *
 */
@SuppressWarnings({"unchecked"})
public class ReceiverOperacionesMoiServiceImpl implements
        ReceiverOperacionesMoiService, Constantes {


    /** Servicio de log */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

    private BitacoraOperacionesDao bitacoraOperacionesDao;
    
    /** Bean de acceso a los servicios de DateUtilService*/
    private DateUtilService dateUtilService;
    
    private OperacionSicDao operacionSicDao;
    
    /** Bean para acceso a la Transformacion del mensaje */
    private TransformaMensaje transformaMensaje;
    
    
    /**
     * @see com.indeval.portalinternacional.services.divisioninternacional.ReceiverOperacionesDivIntService#receiverMessages(javax.jms.TextMessage)
     */
    public void receiverMessages(TextMessage textMessage) {
        
        log.debug("Entrando al metodo recibeMensajes()");
        
        Object mensaje = transformaMensaje.generaVO(textMessage);
        
        if (mensaje instanceof AcuseVO) {
            log.debug("Es un AcuseVO");
            AcuseVO acuseVO = (AcuseVO) mensaje;
            procesaAcuse(acuseVO);
        }
        if (mensaje instanceof ErrorVO) {
            log.debug("Es un ErrorVO");
            ErrorVO errorVO = (ErrorVO) mensaje;
            procesaError(errorVO);
        }
        if (mensaje instanceof ConfirmacionLiquidacionVO) {
            log.debug("Es un ConfirmacionLiquidacionVO");
            ConfirmacionLiquidacionVO confirmacionLiquidacionVO = (ConfirmacionLiquidacionVO) mensaje;
            procesaLiquidacion(confirmacionLiquidacionVO);
        }
        if (mensaje instanceof StatusVO) {
            log.debug("Es un StatusVO");
            StatusVO statusVO = (StatusVO) mensaje;
			log.info("StatusVO: [" + ToStringBuilder.reflectionToString(statusVO, ToStringStyle.MULTI_LINE_STYLE) + "]");
//            procesaStatus(statusVO);
        }
        if (mensaje instanceof TraspasoEfectivoVO ) {
            log.debug("Es un TraspasoEfectivoVO");
            TraspasoEfectivoVO traspasoEfectivoVO = (TraspasoEfectivoVO) mensaje;
            procesaTraspasoEfectivoVO(traspasoEfectivoVO);
        }
        
    }
    
    /**
     * @param acuseVO
     */
    private void procesaAcuse(AcuseVO acuseVO) {
        log.debug("Entrando a procesaAcuse()");
        if (acuseVO != null) {
            if (StringUtils.isNotBlank(acuseVO.getReferencia())
                    && StringUtils.isNotBlank(acuseVO.getEstado())) {
                
                BitacoraOperaciones bitacoraOperaciones = new BitacoraOperaciones();
                bitacoraOperaciones.setReferenciaMensaje(acuseVO.getReferencia());
                List<BitacoraOperaciones> listaBO = bitacoraOperacionesDao.getBitacoras(bitacoraOperaciones);
                if (listaBO != null) {
                    for (Iterator iter = listaBO.iterator(); iter.hasNext();) {
                        BitacoraOperaciones bo = (BitacoraOperaciones) iter.next();
                        if (StringUtils.isNotBlank(bo.getEstatusRegistro())
                                && !ST_MENSAJE_LIQUIDADO.equalsIgnoreCase(bo.getEstatusRegistro().trim())) {
                            bo.setEstatusRegistro(UNO_STRING.equals(acuseVO.getEstado()) ? ACKNOWLEGDE : NOT_ACKNOWLEGDE);
                            bo.setFechaHoraCambio(dateUtilService.getCurrentDate());
                            bitacoraOperacionesDao.update(bo);
                        }
                    }
                }
                
            }
        }
    }
    
    /**
     * @param errorVO
     */
    private void procesaError(ErrorVO errorVO) {
        log.debug("Entrando a procesaError()");
        if (errorVO != null) {
            if (StringUtils.isNotBlank(errorVO.getFolioIndevalOperacionPadre())) {
                
                BitacoraOperaciones bitacoraOperaciones = new BitacoraOperaciones();
                bitacoraOperaciones.setReferenciaOperacion(errorVO.getFolioIndevalOperacionPadre());
                List<BitacoraOperaciones> listaBO = bitacoraOperacionesDao.getBitacoras(bitacoraOperaciones);
                if (listaBO != null) {
                    for (Iterator iter = listaBO.iterator(); iter.hasNext();) {
                        BitacoraOperaciones bo = (BitacoraOperaciones) iter.next();
                        if (StringUtils.isNotBlank(bo.getEstatusRegistro())
                                && !ST_MENSAJE_LIQUIDADO.equalsIgnoreCase(bo.getEstatusRegistro().trim())) {
                            bo.setEstatusRegistro(errorVO.getNumeroError());
                            bo.setFechaHoraCambio(dateUtilService.getCurrentDate());
                            bitacoraOperacionesDao.update(bo);
                            OperacionSic operacionSic = operacionSicDao.findOperacionByFolioControl(bo.getFolioControl());
                            if (operacionSic != null) {
                                operacionSic.setStPfi548(errorVO.getNumeroError());
                                operacionSic.setRefMsjPfi(errorVO.getDescripcionError());
                                EstatusOperacion estatusOperacion = operacionSic.getEstatusOperacion();
                                estatusOperacion.setIdEstatusOperacion(ST_OPER_CONFIRMADA);
                                operacionSic.setEstatusOperacion(estatusOperacion);
                                operacionSic.setFecha530(dateUtilService.getCurrentDate());
                                operacionSic.setFechaCambio(dateUtilService.getCurrentDate());
                                operacionSicDao.update(operacionSic);
                            }
                        }
                    }
                }
                
            }
        }
    }
    
    /**
     * @param confirmacionLiquidacionVO
     */
    private void procesaLiquidacion(ConfirmacionLiquidacionVO confirmacionLiquidacionVO) {
        log.debug("Entrando a procesaLiquidacion()");

        if (confirmacionLiquidacionVO != null) {
            if (StringUtils.isNotBlank(confirmacionLiquidacionVO.getFolioIndevalOperacionPadre())) {
                
                BitacoraOperaciones bitacoraOperaciones = new BitacoraOperaciones();
                bitacoraOperaciones.setReferenciaOperacion(confirmacionLiquidacionVO.getFolioIndevalOperacionPadre());
                List<BitacoraOperaciones> listaBO = bitacoraOperacionesDao.getBitacoras(bitacoraOperaciones);
                if (listaBO != null) {
                    for (Iterator iter = listaBO.iterator(); iter.hasNext();) {
                        BitacoraOperaciones bo = (BitacoraOperaciones) iter.next();
                        if (StringUtils.isNotBlank(bo.getEstatusRegistro())
                                && !ST_MENSAJE_LIQUIDADO.equalsIgnoreCase(bo.getEstatusRegistro().trim())) {
                            bo.setEstatusRegistro(ST_MENSAJE_LIQUIDADO);
                            bo.setFechaHoraCambio(dateUtilService.getCurrentDate());
                            bitacoraOperacionesDao.update(bo);
                            OperacionSic operacionSic = operacionSicDao.findOperacionByFolioControl(bo.getFolioControl());
                            if (operacionSic != null) {
                                if (operacionSic.getMensajeEfectivo() == null 
                                        || !operacionSic.getMensajeEfectivo().trim().equalsIgnoreCase("C103")) {
                                    EstatusOperacion estatusOperacion = new EstatusOperacion();
                                    estatusOperacion.setIdEstatusOperacion(ST_OPER_LIBERADA);
                                    operacionSic.setEstatusOperacion(estatusOperacion);
                                }
                                operacionSic.setStPfi548(ST_MENSAJE_LIQUIDADO);
                                operacionSic.setRefMsjPfi(DESC_ST_MENSAJE_LIQUIDADO);
                                operacionSic.setFechaHora(dateUtilService.getCurrentDate());
                                operacionSic.setFechaCambio(dateUtilService.getCurrentDate());
                                operacionSicDao.update(operacionSic);
                            }
                        }
                    }
                }
                
            }
        }
    }
    
    /**
     * @param statusVO
     */
    private void procesaStatus(StatusVO statusVO) {
        log.debug("Entrando a procesaStatus()");
        if (statusVO != null) {
            if (StringUtils.isNotBlank(statusVO.getFolioIndevalOperacionPadre())) {
                
                BitacoraOperaciones bitacoraOperaciones = new BitacoraOperaciones();
                bitacoraOperaciones.setReferenciaOperacion(statusVO.getFolioIndevalOperacionPadre());
                List<BitacoraOperaciones> listaBO = bitacoraOperacionesDao.getBitacoras(bitacoraOperaciones);
                if (listaBO != null) {
                    for (Iterator iter = listaBO.iterator(); iter.hasNext();) {
                        BitacoraOperaciones bo = (BitacoraOperaciones) iter.next();
                        if (StringUtils.isNotBlank(bo.getEstatusRegistro())
                                && !ST_MENSAJE_LIQUIDADO.equalsIgnoreCase(bo.getEstatusRegistro().trim())) {
                            try {
                                bo.setEstatusRegistro(statusVO.getNumeroStatus());
                                bo.setFechaHoraCambio(dateUtilService.getCurrentDate());
                                bitacoraOperacionesDao.update(bo);
                                OperacionSic operacionSic = operacionSicDao.findOperacionByFolioControl(bo.getFolioControl());
                                if (operacionSic != null) {
                                    operacionSic.setStPfi548(statusVO.getNumeroStatus());
                                    operacionSic.setRefMsjPfi(statusVO.getDescripcion());
                                    EstatusOperacion estatusOperacion = operacionSic.getEstatusOperacion();
                                    estatusOperacion.setIdEstatusOperacion(ST_OPER_CONFIRMADA);
                                    operacionSic.setEstatusOperacion(estatusOperacion);
                                    operacionSic.setFecha530(dateUtilService.getCurrentDate());
                                    operacionSic.setFechaCambio(dateUtilService.getCurrentDate());
                                    operacionSicDao.update(operacionSic);
                                }
                            } catch (Exception e) {
                                log.error("Error al procesar el StatusVO", e);
                            }
                        }
                    }
                }
            
            }
        }
    }
    
    /**
     * @param traspasoEfectivoVO
     */
    private void procesaTraspasoEfectivoVO(TraspasoEfectivoVO traspasoEfectivoVO) {
        log.debug("Entrando a procesaTraspasoEfectivoVO()");
    }
    

    /* Setters */
    
    /**
     * @param transformaMensaje the transformaMensaje to set
     */
    public void setTransformaMensaje(TransformaMensaje transformaMensaje) {
        this.transformaMensaje = transformaMensaje;
    }


        /**
     * @param bitacoraOperacionesDao the bitacoraOperacionesDao to set
     */
    public void setBitacoraOperacionesDao(BitacoraOperacionesDao bitacoraOperacionesDao) {
        this.bitacoraOperacionesDao = bitacoraOperacionesDao;
    }

    /**
     * @param dateUtilService the dateUtilService to set
     */
    public void setDateUtilService(DateUtilService dateUtilService) {
        this.dateUtilService = dateUtilService;
    }

    /**
     * @param operacionSicDao the operacionSicDao to set
     */
    public void setOperacionSicDao(OperacionSicDao operacionSicDao) {
        this.operacionSicDao = operacionSicDao;
    }

    
}

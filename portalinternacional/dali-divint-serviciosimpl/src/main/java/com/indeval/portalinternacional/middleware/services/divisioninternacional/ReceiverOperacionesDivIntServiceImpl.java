/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jms.TextMessage;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.util.DateUtilService;
import com.indeval.portaldali.middleware.services.util.UtilService;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;
import com.indeval.portaldali.persistence.dao.common.BitacoraOperacionesDao;
import com.indeval.portaldali.persistence.dao.common.CuentaNombradaDao;
import com.indeval.portaldali.persistence.dao.common.EmisionDao;
import com.indeval.portaldali.persistence.modelo.BitacoraOperaciones;
import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portaldali.persistence.modelo.CuentaNombrada;
import com.indeval.portaldali.persistence.modelo.Emision;
import com.indeval.portalinternacional.middleware.services.util.FactoryDivInt;
import com.indeval.portalinternacional.middleware.services.util.TransformaMensaje;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.CatBic;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstatusOperacion;
import com.indeval.portalinternacional.middleware.servicios.modelo.OperacionSic;
import com.indeval.portalinternacional.middleware.servicios.modelo.SicDetalle;
import com.indeval.portalinternacional.persistence.dao.CatBicDao;
import com.indeval.portalinternacional.persistence.dao.EstatusOperacionDao;
import com.indeval.portalinternacional.persistence.dao.OperacionSicDao;
import com.indeval.portalinternacional.persistence.dao.SicDetalleDao;
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
public class ReceiverOperacionesDivIntServiceImpl implements
	ReceiverOperacionesDivIntService, Constantes {

    /** Servicio de log */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

    private BitacoraOperacionesDao bitacoraOperacionesDao;
    
    private CatBicDao catBicDao;
    
    private CuentaNombradaDao cuentaNombradaDao;
    
    /** Bean de acceso a los servicios de DateUtilService*/
    private DateUtilService dateUtilService;
    
    private DivisionInternacionalService divisionInternacionalService;
    
    private EmisionDao emisionDao;
    
    private EstatusOperacionDao estatusOperacionDao;
    
    private OperacionSicDao operacionSicDao;
    
    private SicDetalleDao sicDetalleDao;
    
    /** Bean para acceso a la Transformacion del mensaje */
    private TransformaMensaje transformaMensaje;
    
   
	private UtilService utilService;
    
    
    
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
            procesaStatus(statusVO);
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
                            bo.setEstatusRegistro(ST_MENSAJE_ERROR);
                            bo.setFechaHoraCambio(dateUtilService.getCurrentDate());
                            bitacoraOperacionesDao.update(bo);
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
                            if (operacionSic == null && bo.getDatosAdicionales()!= null) {
                                divisionInternacionalService.insertaOperacionSIC(creaOperacionSic(bo));
                            }
                            /* Modificiación  31/05/2012 se agrega condicion para las operaciones sic, retenidas
                             * que fueron habilitadas y en este paso se actualizan a notificadas*/
                            else {
                            	operacionSic.setEstatusOperacion((EstatusOperacion) estatusOperacionDao.getByPk(EstatusOperacion.class, ST_OPER_NOTIFICADA));
                            	//ingreso el valor de fechaLiquidacionTlp
                            	operacionSic.setFechaLiquidacionTlp(dateUtilService.getCurrentDate());
                            	/*
                            	 * seteamos la fecha de notificacion para que el swift-dali la pueda Enviar
                            	 */
                            	operacionSic.setFechaNotificacion(dateUtilService.getCurrentDate());
                            	//OperacionSic[] operaciones = new OperacionSic[] {operacionSic};
                            	OperacionSic[] operaciones = {operacionSic};
                            	divisionInternacionalService.actualizaOperacionSIC(operaciones);
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
                                && !ST_MENSAJE_LIQUIDADO.equalsIgnoreCase(bo.getEstatusRegistro().trim())
                                && CLAVE_MENSAJE_FALTANTE_VALORES.equalsIgnoreCase(bo.getEstatusRegistro().trim())) {
                            try {
                                bo.setEstatusRegistro(ST_FALTANTE_VALORES);
                                bo.setFechaHoraCambio(dateUtilService.getCurrentDate());
                                bitacoraOperacionesDao.update(bo);
                            } catch (Exception e) {
                                e.printStackTrace();
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

    /**
     * Obtiene una instancia de OperacionSic a partir de la instancia BitacoraOperaciones 
     * proporcionada
     * 
     * @param bo
     * @return OperacionSICParams
     */
    private OperacionSic creaOperacionSic(BitacoraOperaciones bo) {
        OperacionSic opSic = new OperacionSic();
        
      
        Map datosAdicionales = null;
        try {     	
        	
            datosAdicionales = utilService.stringToMap(bo.getDatosAdicionales());
            opSic.setCantidadTitulos(bo.getCantidadTitulos());
            opSic.setCuentaRecep(bo.getCuentaRecep());
            opSic.setCuentaContraparte((String) datosAdicionales.get(CUENTA_CONTRAPARTE_DA));
            opSic.setEmision(new Emision(new EmisionVO(bo.getTv(), bo.getEmisora(), bo.getSerie(), bo.getCupon())));
            opSic.setCuentaNombrada(new CuentaNombrada(new AgenteVO(bo.getIdTrasp(), bo.getFolioTrasp(), bo.getCuentaTrasp())));
            opSic.setDescContraparte((String) datosAdicionales.get(DESC_CTA_CONTRAPARTE_DA));
            opSic.setFechaLiquidacion((Date) datosAdicionales.get(FECHA_LIQ_DA)); 
            opSic.setFechaHora(dateUtilService.getCurrentDate());
            opSic.setFechaNotificacion((Date) datosAdicionales.get(FECHA_NOT_DA)); 
            opSic.setFechaOperacion((Date) datosAdicionales.get(FECHA_OP_DA));  
            opSic.setTipoMensaje((String) datosAdicionales.get(TIPO_MENSAJE_DA));
            opSic.setNumCtaBenef((String) datosAdicionales.get(NUM_CUNETA_BENEF_DA));
            opSic.setNomCtaBenef((String) datosAdicionales.get(NOM_CUENTA_BENEF_DA));
            opSic.setSicDetalle(
                    (SicDetalle) (datosAdicionales.get(DEPOSITANTE_DA) != null 
                            ? sicDetalleDao.getByPk(SicDetalle.class, 
                                    (Long) datosAdicionales.get(DEPOSITANTE_DA)) : null));
            opSic.setCatBic(
                    (CatBic) (datosAdicionales.get(CUSTODIO_DA) != null 
                            ? catBicDao.getByPk(CatBic.class, 
                                    (Long) datosAdicionales.get(CUSTODIO_DA)) : null));
            opSic.setEstatusOperacion(
                    (EstatusOperacion) (datosAdicionales.get(ESTATUS_DA) != null 
                            ? estatusOperacionDao.getByPk(EstatusOperacion.class, 
                                    (Long) datosAdicionales.get(ESTATUS_DA)) : null)); 
            opSic.setUsuario(bo.getIdTrasp() + bo.getFolioTrasp());
            opSic.setImporte((BigDecimal) datosAdicionales.get(PRECIO_DA));
            opSic.setDivisa((String) datosAdicionales.get(DIVISA_DA));
            opSic.setInstruccEspeciales((String) datosAdicionales.get(INSTRUCCIONES_ESP_DA));
            String[] operTrasp = FactoryDivInt.obtieneTipoOperTipoTrasp(opSic.getTipoMensaje());
            if (operTrasp != null && operTrasp.length == 2) {
                opSic.setTipoOperacion(operTrasp[0]);
                opSic.setTipoTraspaso(operTrasp[1]);
            }
            opSic.setCuentaNombrada(cuentaNombradaDao.findCuenta(new AgenteVO(bo.getIdTrasp(), bo.getFolioTrasp(), bo.getCuentaTrasp())));
            opSic.setEmision(emisionDao.findEmision(new EmisionVO(bo.getTv(), bo.getEmisora(), bo.getSerie(), bo.getCupon())));
            opSic.setBoveda(new Boveda());
            opSic.getBoveda().setIdBoveda(1l);
            opSic.setFolioControl(bo.getFolioControl());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return opSic;
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
     * @param utilService the utilService to set
     */
//    public void setUtilService(UtilService utilService) {
//        this.utilService = utilService;
//    }

    /**
     * @param divisionInternacionalService the divisionInternacionalService to set
     */
    public void setDivisionInternacionalService(
            DivisionInternacionalService divisionInternacionalService) {
        this.divisionInternacionalService = divisionInternacionalService;
    }

    /**
     * @param dateUtilService the dateUtilService to set
     */
    public void setDateUtilService(DateUtilService dateUtilService) {
        this.dateUtilService = dateUtilService;
    }

    /**
     * @param sicDetalleDao the sicDetalleDao to set
     */
    public void setSicDetalleDao(SicDetalleDao sicDetalleDao) {
        this.sicDetalleDao = sicDetalleDao;
    }

    /**
     * @param catBicDao the catBicDao to set
     */
    public void setCatBicDao(CatBicDao catBicDao) {
        this.catBicDao = catBicDao;
    }

    /**
     * @param estatusOperacionDao the estatusOperacionDao to set
     */
    public void setEstatusOperacionDao(EstatusOperacionDao estatusOperacionDao) {
        this.estatusOperacionDao = estatusOperacionDao;
    }

    /**
     * @param cuentaNombradaDao the cuentaNombradaDao to set
     */
    public void setCuentaNombradaDao(CuentaNombradaDao cuentaNombradaDao) {
        this.cuentaNombradaDao = cuentaNombradaDao;
    }

    /**
     * @param emisionDao the emisionDao to set
     */
    public void setEmisionDao(EmisionDao emisionDao) {
        this.emisionDao = emisionDao;
    }

    /**
     * @param operacionSicDao the operacionSicDao to set
     */
    public void setOperacionSicDao(OperacionSicDao operacionSicDao) {
        this.operacionSicDao = operacionSicDao;
    }
    
    public UtilService getUtilService() {
		return utilService;
	}

	public void setUtilService(UtilService utilService) {
		this.utilService = utilService;
	}

    
}

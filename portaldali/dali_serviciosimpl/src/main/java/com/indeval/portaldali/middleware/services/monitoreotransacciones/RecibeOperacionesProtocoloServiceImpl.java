/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.monitoreotransacciones;

import javax.jms.TextMessage;

/**
 * Clase que monitorea los mensajes de protocolo
 * 
 * @author csanchez
 * 
 */
public class RecibeOperacionesProtocoloServiceImpl implements RecibeOperacionesProtocoloService {

	public void recibeMensajes(TextMessage textMessage) {
		// TODO Auto-generated method stub
		
	}
//
//    /** Servicio de log */
//    private Logger logger = LoggerFactory.getLogger(RecibeOperacionesProtocoloServiceImpl.class);
//
//    /** Bean para acceso a la Transformacion del mensaje */
//    private TransformaMensaje transformaMensaje;
//
//    /** Bean para el manejo de la transacciones */
//    private TransaccionDao transaccionDao;
//
//    /** Bean para el manejo de la bitacora */
//    private BitacoraProtocoloDao bitacoraProtocoloDao;
//    
//    /** Bean de acceso a DateUtilsDao */
//    private DateUtilsDao dateUtilsDao;
//
//    /**
//     * @see com.indeval.portaldali.middleware.services.monitoreotransacciones.RecibeOperacionesProtocoloService#recibeMensajes(javax.jms.TextMessage)
//     */
//    public void recibeMensajes(TextMessage textMessage) {
//        
//        log.info("Entrando al metodo recibeMensajes()");
//        Object mensaje = transformaMensaje.generaVO(textMessage);
//
//        if (mensaje instanceof AcuseVO) {
//            System.out.println("Es un AcuseVO");
//            AcuseVO acuseVO = (AcuseVO) mensaje;
//            procesaAcuse(acuseVO);
//        }
//        if (mensaje instanceof ErrorVO) {
//            System.out.println("Es un ErrorVO");
//            ErrorVO errorVO = (ErrorVO) mensaje;
//            procesaError(errorVO);
//        }
//        if (mensaje instanceof ConfirmacionLiquidacionVO) {
//            System.out.println("Es un ConfirmacionLiquidacionVO");
//            ConfirmacionLiquidacionVO confirmacionLiquidacionVO = (ConfirmacionLiquidacionVO) mensaje;
//            procesaLiquidacion(confirmacionLiquidacionVO);
//        }
//        if (mensaje instanceof StatusVO) {
//            System.out.println("Es un StatusVO");
//            StatusVO statusVO = (StatusVO) mensaje;
//            procesaStatus(statusVO);
//        }
//    }
//
//    /**
//     * @param statusVO
//     */
//    private void procesaStatus(StatusVO statusVO) {
//        
//        log.info("Entrando al metodo procesaStatus()");
//        System.out.println("Entrando al metodo procesaStatus()");
//        
//        Transaccion transaccion = null;
//        BitacoraProtocolo bitacoraProtocolo = null;
//        BitacoraProtocoloPK bitacoraProtocoloPK = null;
//        BigInteger fltransaccion = null;
//        StringBuffer mensaje = null;
//
//        if (statusVO != null) {
//            fltransaccion = new BigInteger(statusVO.getFolioIndevalOperacionPadre().trim());
//            transaccion = (Transaccion) transaccionDao.getByPk(Transaccion.class, fltransaccion);
//
//            log.debug("transaccion = " + transaccion);
//            System.out.println("transaccion = " + transaccion);
//            if (transaccion != null) {
//
//                bitacoraProtocolo = new BitacoraProtocolo();
//                bitacoraProtocoloPK = new BitacoraProtocoloPK();
//
//                bitacoraProtocoloPK.setFlTransaccion(new BigInteger(statusVO
//                        .getFolioIndevalOperacionPadre().trim()));
//
//                mensaje = new StringBuffer("");
//                mensaje.append(statusVO.getStatus());
//                mensaje.append(" ");
//                mensaje.append(statusVO.getDescripcion());
//                mensaje.append(" ");
//                mensaje.append(statusVO.getNumeroStatus());
//
//                bitacoraProtocoloPK.setMensaje(mensaje.toString());
//                bitacoraProtocoloPK.setFeOperacion(dateUtilsDao.getDateFechaDB());
//                bitacoraProtocolo.setBitacoraProtocoloPK(bitacoraProtocoloPK);
//                bitacoraProtocoloDao.save(bitacoraProtocolo);
//
//                log.debug("Numero Status = " + statusVO.getNumeroStatus());
//                log.debug("Status = " + statusVO.getStatus());
//                log.debug("Tipo Mensaje = " + statusVO.getTipoMensaje());
//                log.debug("Tipo Instruccion = " + statusVO.getTipoInstruccion());
//
//                if (statusVO.getNumeroStatus() != null) {
//                    if (statusVO.getNumeroStatus().equalsIgnoreCase(Constantes.CLAVE_FALTA_VALORES)) {
//                        transaccion.setCvEstado(Constantes.ESTATUS_FALTA_VALORES);
//                        transaccion.setFlSalida(statusVO.getDescripcion());
//                        transaccionDao.update(transaccion);
//                    }
//                }
//            }
//        }
//    }
//
//    /**
//     * @param confirmacionLiquidacionVO
//     */
//    private void procesaLiquidacion(ConfirmacionLiquidacionVO confirmacionLiquidacionVO) {
//        
//        log.info("Entrando al metodo procesaLiquidacion()");
//        System.out.println("Entrando al metodo procesaLiquidacion()");
//        
//        Transaccion transaccion = null;
//        BitacoraProtocolo bitacoraProtocolo = null;
//        BitacoraProtocoloPK bitacoraProtocoloPK = null;
//        BigInteger fltransaccion = null;
//        StringBuffer mensaje = null;
//
//        if (confirmacionLiquidacionVO != null) {
//            fltransaccion = new BigInteger(confirmacionLiquidacionVO
//                    .getFolioIndevalOperacionPadre().trim());
//            transaccion = (Transaccion) transaccionDao.getByPk(Transaccion.class, fltransaccion);
//
//            log.debug("transaccion = " + transaccion);
//            System.out.println("transaccion = " + transaccion);
//            if (transaccion != null) {
//
//                bitacoraProtocolo = new BitacoraProtocolo();
//                bitacoraProtocoloPK = new BitacoraProtocoloPK();
//
//                bitacoraProtocoloPK.setFlTransaccion(new BigInteger(confirmacionLiquidacionVO
//                        .getFolioIndevalOperacionPadre().trim()));
//
//                mensaje = new StringBuffer("");
//                mensaje.append(confirmacionLiquidacionVO.getStatus());
//                mensaje.append(" ");
//                mensaje.append(Constantes.ESTATUS_LIQUIDACION);
//
//                bitacoraProtocoloPK.setMensaje(mensaje.toString());
//                bitacoraProtocoloPK.setFeOperacion(dateUtilsDao.getDateFechaDB());
//                bitacoraProtocolo.setBitacoraProtocoloPK(bitacoraProtocoloPK);
//                bitacoraProtocoloDao.save(bitacoraProtocolo);
//
//                transaccion.setCvEstado(Constantes.ESTATUS_LIQUIDACION);
//                transaccion.setFlSalida(confirmacionLiquidacionVO.getFolioAsignadoIndeval().trim());
//                transaccionDao.update(transaccion);
//            }
//        }
//    }
//
//    /**
//     * @param errorVO
//     */
//    private void procesaError(ErrorVO errorVO) {
//        
//        log.info("Entrando al metodo procesaError()");
//        System.out.println("Entrando al metodo procesaError()");
//        
//        Transaccion transaccion = null;
//        BitacoraProtocolo bitacoraProtocolo = null;
//        BitacoraProtocoloPK bitacoraProtocoloPK = null;
//        BigInteger fltransaccion = null;
//        StringBuffer mensaje = null;
//
//        if (errorVO != null) {
//            fltransaccion = new BigInteger(errorVO.getFolioIndevalOperacionPadre().trim());
//            transaccion = (Transaccion) transaccionDao.getByPk(Transaccion.class, fltransaccion);
//
//            log.debug("transaccion = " + transaccion);
//            System.out.println("transaccion = " + transaccion);
//            if (transaccion != null) {
//                bitacoraProtocolo = new BitacoraProtocolo();
//                bitacoraProtocoloPK = new BitacoraProtocoloPK();
//                bitacoraProtocoloPK.setFlTransaccion(new BigInteger(errorVO
//                        .getFolioIndevalOperacionPadre().trim()));
//
//                mensaje = new StringBuffer("");
//                mensaje.append(errorVO.getStatus());
//                mensaje.append(" ");
//                mensaje.append(errorVO.getTipoError());
//                mensaje.append(" ");
//                mensaje.append(errorVO.getDescripcionError());
//
//                bitacoraProtocoloPK.setMensaje(mensaje.toString());
//                bitacoraProtocoloPK.setFeOperacion(dateUtilsDao.getDateFechaDB());
//                bitacoraProtocolo.setBitacoraProtocoloPK(bitacoraProtocoloPK);
//                bitacoraProtocoloDao.save(bitacoraProtocolo);
//
//                transaccion.setCvEstado(Constantes.ESTATUS_ERROR);
//                transaccion.setFlSalida(errorVO.getDescripcionError());
//                transaccionDao.update(transaccion);
//            }
//        }
//    }
//
//    /**
//     * @param acuseVO
//     */
//    private void procesaAcuse(AcuseVO acuseVO) {
//        
//        log.info("Entrando al metodo procesaAcuse()");
//        System.out.println("Entrando al metodo procesaAcuse()");
//        
//        Transaccion transaccion = null;
//        BitacoraProtocolo bitacoraProtocolo = null;
//        BitacoraProtocoloPK bitacoraProtocoloPK = null;
//        BigInteger fltransaccion = null;
//        StringBuffer mensaje = null;
//
//        if (acuseVO != null) {
//            fltransaccion = new BigInteger(acuseVO.getReferencia().trim());
//            transaccion = (Transaccion) transaccionDao.getByPk(Transaccion.class, fltransaccion);
//
//            log.debug("transaccion = " + transaccion);
//            System.out.println("transaccion = " + transaccion);
//            if (transaccion != null) {
//
//                bitacoraProtocolo = new BitacoraProtocolo();
//                bitacoraProtocoloPK = new BitacoraProtocoloPK();
//
//                bitacoraProtocoloPK
//                        .setFlTransaccion(new BigInteger(acuseVO.getReferencia().trim()));
//
//                mensaje = new StringBuffer("");
//                mensaje.append(acuseVO.getEstado());
//                mensaje.append(" ");
//                mensaje.append(acuseVO.getDescripcionError());
//                mensaje.append(" ");
//                if (Constantes.CERO_STRING.equalsIgnoreCase(acuseVO.getEstado())) {
//                    mensaje.append(Constantes.ESTATUS_NAK);
//                }
//                else {
//                    mensaje.append(Constantes.ESTATUS_AK);
//                }
//
//                bitacoraProtocoloPK.setMensaje(mensaje.toString());
//                bitacoraProtocoloPK.setFeOperacion(dateUtilsDao.getDateFechaDB());
//                bitacoraProtocolo.setBitacoraProtocoloPK(bitacoraProtocoloPK);
//                bitacoraProtocoloDao.save(bitacoraProtocolo);
//
//                // Estado NACK
//                if (Constantes.CERO_STRING.equalsIgnoreCase(acuseVO.getEstado())) {
//                    boolean actualizaNack = true;
//                    List listBitacoraProtocolo = bitacoraProtocoloDao.getBitacora(bitacoraProtocolo
//                            .getBitacoraProtocoloPK().getFlTransaccion());
//                    for (Iterator iter = listBitacoraProtocolo.iterator(); iter.hasNext();) {
//                        BitacoraProtocolo element = (BitacoraProtocolo) iter.next();
//                        if (element.getBitacoraProtocoloPK().getMensaje().charAt(0) == '1') {
//                            actualizaNack = false;
//                            break;
//                        }
//                    }
//                    if (actualizaNack) {
//                        transaccion.setCvEstado(Constantes.ESTATUS_NAK);
//                        transaccion.setFlSalida(acuseVO.getDescripcionError());
//                        transaccionDao.update(transaccion);
//                    }
//                }
//            }
//
//        }
//    }
//
//    /**
//     * @param bitacoraProtocoloDao
//     */
//    public void setBitacoraProtocoloDao(BitacoraProtocoloDao bitacoraProtocoloDao) {
//        this.bitacoraProtocoloDao = bitacoraProtocoloDao;
//    }
//
//    /**
//     * @param transaccionDao
//     */
//    public void setTransaccionDao(TransaccionDao transaccionDao) {
//        this.transaccionDao = transaccionDao;
//    }
//
//    /**
//     * @param transformaMensaje
//     */
//    public void setTransformaMensaje(TransformaMensaje transformaMensaje) {
//        this.transformaMensaje = transformaMensaje;
//    }
//
//    /**
//     * @param dateUtilsDao the dateUtilsDao to set
//     */
//    public void setDateUtilsDao(DateUtilsDao dateUtilsDao) {
//        this.dateUtilsDao = dateUtilsDao;
//    }

}

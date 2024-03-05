/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.enviooperaciones;

import com.indeval.portaldali.middleware.services.enviooperaciones.util.Constantes;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class DispatcherOperacionesServiceImpl //implements DispatcherOperacionesService, Constantes 
{
    
//    /** Log de clase. */
//    private static final Logger logger = LoggerFactory.getLogger(DispatcherOperacionesServiceImpl.class);
//
//    /**
//     * Bean para acceso a la bitacora de las operaciones
//     * bddinero..bitacora_operaciones con driver XA
//     */
//    private BitacoraOperacionesSybaseDao bitacoraOperacionesXADao;
//
//    /** Bean para acceso al envio de operaciones */
//    private EnvioOperacionesService envioOperacionesService;
//    
//    /** Bean para acceso utilService */
//    private UtilService utilService;
//
//    /**
//     * @see com.indeval.portaldali.middleware.services.enviooperaciones.DispatcherOperacionesService#enviaOperacionesH2H(java.util.HashMap)
//     */
//    public void enviaOperacionesH2H(HashMap instituciones) throws BusinessException {
//        
//        log.info("Entrando a DispatcherOperacionesServiceImpl.enviaOperacionesH2H()");
//        
//        // SOLO OBTIENE UN NUMERO PARA APARTARLO
//        BigInteger statusActual = utilService.getFolio(SEQ_OPERACION_ENVIABLE); 
//
//        // SE RESERVAN LOS REGISTROS CON EL APARTADO
//        bitacoraOperacionesXADao.updateEstatusBloque("NE", "H2H", statusActual.toString());
//
//        try {
//            System.out.println("ENVIA OPERACIONES enviaOperacionesH2H [" + instituciones
//                    + "]  NUM APARTADO [" + statusActual + "]");
//
//            List list = null;
//            // SE BUSCAN LOS REGISTROS APARTADOS
//            list = envioOperacionesService.getMensajeBitacoraXEstatusRegistro(statusActual.toString(), "H2H");
//
//            log.debug(" se obtienen [ " + (list != null ? list.size() : "NULL") + "]");
//
//            if (list != null) {
//                for (Iterator iter = list.iterator(); iter.hasNext();) {
//                    BitacoraOperacionesSybase element = (BitacoraOperacionesSybase) iter.next();
//                    TraspasoContraPagoVO vo = new TraspasoContraPagoVO();
//                    vo.setReferenciaMensaje(element.getBitacoraOperacionesPK()
//                            .getReferenciaMensaje());
//                    vo.setFechaConcertacion(element.getBitacoraOperacionesPK()
//                            .getFechaConcertacion());
//                    vo.setCantidadTitulos(element.getCantidadTitulos() != null ? Long
//                            .valueOf(element.getCantidadTitulos().toString()) : null);
//                    vo.setIdFolioCtaReceptora(element.getIdRecep().trim()
//                            + element.getFolioRecep().trim() + element.getCuentaRecep().trim());
//                    vo.setIdFolioCtaTraspasante(element.getIdTrasp().trim()
//                            + element.getFolioTrasp().trim() + element.getCuentaTrasp().trim());
//                    vo.setTipoValor(element.getTv().trim());
//                    vo.setEmisora(element.getEmisora().trim());
//                    vo.setSerie(element.getSerie().trim());
//                    vo.setCupon(element.getCupon().trim());
//                    vo.setDivisa(element.getDivisa());
//                    vo.setFechaLiquidacion(element.getFechaLiquidacion());
//                    vo.setFechaRegistro(element.getFechaRegistro());
//                    vo.setFechaVencimiento(element.getFechaVencimiento());
//                    vo.setIdFolioCtaPuente(element.getIdFolioCtaPuente());
//                    vo.setMonto(element.getMonto());
//                    vo.setPrecio(element.getPrecio());
//
//                    if ("MC".equalsIgnoreCase(element.getMercado().trim())) {
//                        // vo.setReferenciaOperacion(StringUtils.isNotBlank(element.getReferenciaOperacion())?element.getReferenciaOperacion().trim():"123");
//                        vo.setReferenciaOperacion(element.getFolioControl().toString());
//                    }
//                    else {
//                        // Se busca en la lista de instituciones a las que se
//                        // les asigna el folio
//                        String idFolio = element.getIdTrasp().trim()
//                                + element.getFolioTrasp().trim();
//                        if (instituciones.containsKey(idFolio)) {
//                            vo.setReferenciaOperacion(utilService.getFolio(SEQ_OPERACION_ENVIABLE).toString());
//                        }
//                        else {
//                            vo.setReferenciaOperacion(StringUtils.isNotBlank(element
//                                    .getReferenciaOperacion()) ? element.getReferenciaOperacion()
//                                    .trim() : element.getFolioControl().toString());
//                        }
//                    }
//                    vo.setReferenciaRelacionada(element.getReferenciaRelacionada());
//                    vo.setTasaFija(element.getTasaFija() != null ? (element.getTasaFija()
//                            .intValue() == 1 ? Boolean.TRUE : Boolean.FALSE) : null);
//                    vo.setTasaNegociada(element.getTasaNegociada() != null ? Double.valueOf(element
//                            .getTasaNegociada().toString()) : null);
//                    vo.setTasaReferencia(element.getTasaReferencia() != null ? Double
//                            .valueOf(element.getTasaReferencia().toString()) : null);
//                    vo.setTipoInstruccion(element.getTipoInstruccion());
//                    try {
//                        envioOperacionesService.reenviaOperacion(vo, element.getFolioControl().toString(), COLA_H2H, false);
//                    }
//                    catch (ProtocoloFinancieroException e) {
//                        e.printStackTrace();
//                        log.error(" ERROR AL ENVIAR LA OPERACION ", e);
//                        try {
//                            System.out.println("DATOS DE OPERACION  [" + BeanUtils.describe(vo)
//                                    + "]");
//                        }
//                        catch (Exception exe) {
//                        }
//                    }
//                    catch (BusinessException e) {
//                        e.printStackTrace();
//                        log.error(" ERROR AL ENVIAR LA OPERACION ", e);
//                        try {
//                            System.out.println("DATOS DE OPERACION  [" + BeanUtils.describe(vo)
//                                    + "]");
//                        }
//                        catch (Exception exe) {
//                        }
//                    }
//                }
//            }
//        }
//        finally {// SI ALGUNO DE LOS REGISTROS NO SE ENVIO SE REGRESA A
//                    // ESTATUS NE PARA QUE SE REEVIE
//            bitacoraOperacionesXADao.updateEstatusBloque(statusActual.toString(), "H2H", "NE");
//        }
//    }
//
//    /**
//     * @see com.indeval.portaldali.middleware.services.enviooperaciones.DispatcherOperacionesService#enviaOperacionesPortal()
//     */
//    public void enviaOperacionesPortal() throws BusinessException {
//        
//        log.info("Entrando a DispatcherOperacionesServiceImpl.enviaOperacionesPortal()");
//        
//        BigInteger statusActual = utilService.getFolio(SEQ_OPERACION_ENVIABLE);
//
//        System.out.println("ENVIA OPERACIONES enviaOperacionesPortal NUM APARTADO [" + statusActual.toString()
//                + "]");
//
//        // SE RESERVAN LOS REGISTROS CON EL APARTADO
//        bitacoraOperacionesXADao.updateEstatusBloque("NE", "PORTAL", statusActual.toString());
//        List list = null;
//        list = envioOperacionesService.getMensajeBitacoraXEstatusRegistro(statusActual.toString(), "PORTAL");
//
//        try {
//            if (list != null) {
//                for (Iterator iter = list.iterator(); iter.hasNext();) {
//                    BitacoraOperacionesSybase element = (BitacoraOperacionesSybase) iter.next();
//                    TraspasoContraPagoVO vo = new TraspasoContraPagoVO();
//                    vo.setReferenciaMensaje(element.getBitacoraOperacionesPK()
//                            .getReferenciaMensaje());
//                    vo.setFechaConcertacion(element.getBitacoraOperacionesPK()
//                            .getFechaConcertacion());
//                    vo.setCantidadTitulos(element.getCantidadTitulos() != null ? Long
//                            .valueOf(element.getCantidadTitulos().toString()) : null);
//                    vo.setIdFolioCtaReceptora(element.getIdRecep().trim()
//                            + element.getFolioRecep().trim() + element.getCuentaRecep().trim());
//                    vo.setIdFolioCtaTraspasante(element.getIdTrasp().trim()
//                            + element.getFolioTrasp().trim() + element.getCuentaTrasp().trim());
//                    vo.setTipoValor(element.getTv().trim());
//                    vo.setEmisora(element.getEmisora().trim());
//                    vo.setSerie(element.getSerie().trim());
//                    vo.setCupon(element.getCupon().trim());
//                    vo.setDivisa(element.getDivisa());
//                    vo.setFechaLiquidacion(element.getFechaLiquidacion());
//                    vo.setFechaRegistro(element.getFechaRegistro());
//                    vo.setFechaVencimiento(element.getFechaVencimiento());
//                    vo.setIdFolioCtaPuente(element.getIdFolioCtaPuente());
//                    vo.setMonto(element.getMonto());
//                    vo.setPrecio(element.getPrecio());
//                    vo.setReferenciaOperacion(element.getReferenciaOperacion());
//                    vo.setReferenciaRelacionada(element.getReferenciaRelacionada());
//                    vo.setTasaFija(element.getTasaFija() != null ? (element.getTasaFija()
//                            .intValue() == 1 ? Boolean.TRUE : Boolean.FALSE) : null);
//                    vo.setTasaNegociada(element.getTasaNegociada() != null ? Double.valueOf(element
//                            .getTasaNegociada().toString()) : null);
//                    vo.setTasaReferencia(element.getTasaReferencia() != null ? Double
//                            .valueOf(element.getTasaReferencia().toString()) : null);
//                    vo.setTipoInstruccion(element.getTipoInstruccion());
//                    try {
//                        envioOperacionesService.reenviaOperacion(vo, element.getFolioControl().toString(), COLA_PORTAL,
//                                element.getMarcaCompra() != null
//                                        && element.getMarcaCompra().intValue() == 1 ? true : false);
//                    }
//                    catch (ProtocoloFinancieroException e) {
//                        e.printStackTrace();
//                        log.error(" ERROR AL ENVIAR LA OPERACION ", e);
//                        try {
//                            System.out.println("DATOS DE OPERACION  [" + BeanUtils.describe(vo)
//                                    + "]");
//                        }
//                        catch (Exception exe) {
//                        }
//                    }
//                    catch (BusinessException e) {
//                        e.printStackTrace();
//                        log.error(" ERROR AL ENVIAR LA OPERACION ", e);
//                        try {
//                            System.out.println("DATOS DE OPERACION  [" + BeanUtils.describe(vo)
//                                    + "]");
//                        }
//                        catch (Exception exe) {
//                        }
//                    }
//                }
//            }
//        }
//        finally {// SI ALGUNO DE LOS REGISTROS NO SE ENVIO SE REGRESA A
//                    // ESTATUS NE PARA QUE SE REEVIE
//            bitacoraOperacionesXADao.updateEstatusBloque(statusActual.toString(), "PORTAL", "NE");
//        }
//    }
//
//    /**
//     * @param bitacoraOperacionesXADao
//     */
//    public void setBitacoraOperacionesXADao(BitacoraOperacionesSybaseDao bitacoraOperacionesXADao) {
//        this.bitacoraOperacionesXADao = bitacoraOperacionesXADao;
//    }
//
//    /**
//     * @param envioOperacionesService
//     */
//    public void setEnvioOperacionesService(EnvioOperacionesService envioOperacionesService) {
//        this.envioOperacionesService = envioOperacionesService;
//    }
//
//    /**
//     * @param utilService the utilService to set
//     */
//    public void setUtilService(UtilService utilService) {
//        this.utilService = utilService;
//    }

}

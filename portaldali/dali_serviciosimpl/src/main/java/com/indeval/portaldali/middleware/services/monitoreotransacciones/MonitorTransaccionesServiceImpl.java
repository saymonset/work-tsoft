/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.monitoreotransacciones;

import com.indeval.portaldali.middleware.services.modelo.BusinessException;

/**
 * Clase que monitorea las operaciones de ktransacciones y las envia a protocolo
 * 
 * @author csanchez
 * 
 */
public class MonitorTransaccionesServiceImpl implements MonitorTransaccionesService {

	public void monitoreaTransacciones() throws BusinessException {
		// TODO Auto-generated method stub
		
	}
//
//    /** Servicio de log */
//    private Logger logger = LoggerFactory.getLogger(MonitorTransaccionesServiceImpl.class);
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
//    /** Bean para acceso al cliente de jms */
//    private JmsClient jmsClient;
//
//    /** Bean para acceso al template de jms */
//    private JmsTemplate jmsTemplate;
//
//    /** Bean para acceso al servicio de traspasos libres de pago */
//    private TraspasoLibrePago tlpService;
//
//    /** Define la cola para el env&iacute;o del mensaje */
//    public String cola;
//
//    /** Bean para acceso al properties de mensaje de errores */
//    private MessageResolver errorResolver;
//
//    /**
//     * @see com.indeval.portaldali.middleware.services.monitoreotransacciones.MonitorTransaccionesService#monitoreaTransacciones()
//     */
//    public void monitoreaTransacciones() throws BusinessException {
//        
//        log.info("Entrando al metodo monitoreaTransacciones()");
//        System.out.println("Entrando al metodo monitoreaTransacciones()");
//
//        List transaccionesPendientes = transaccionDao.getTransacionesPendientes();
//
//        TraspasoLibrePagoVO traspasoLibrePagoVO = null;
//        BitacoraProtocolo bitacoraProtocolo = null;
//        BitacoraProtocoloPK bitacoraProtocoloPK = null;
//        for (Iterator iter = transaccionesPendientes.iterator(); iter.hasNext();) {
//            Transaccion element = (Transaccion) iter.next();
//
//            traspasoLibrePagoVO = new TraspasoLibrePagoVO();
//
//            if (StringUtils.isNotBlank(element.getCvTipoTransaccion())
//                    && (element.getCvTipoTransaccion().equalsIgnoreCase(Constantes.RETIRO_VALORES) || element
//                            .getCvTipoTransaccion().equalsIgnoreCase(Constantes.RETIRO_FIN_DIA))) {
//                traspasoLibrePagoVO.setIdFolioCtaTraspasante(Constantes.IDFOLIOCUENTA_FIJA);
//                traspasoLibrePagoVO.setIdFolioCtaReceptora(element.getCvReceptor().trim().concat(
//                        element.getCvCuentaReceptor().trim()));
//            }
//            else if (StringUtils.isNotBlank(element.getCvTipoTransaccion())
//                    && (element.getCvTipoTransaccion()
//                            .equalsIgnoreCase(Constantes.DEPOSITO_VALORES) || element
//                            .getCvTipoTransaccion()
//                            .equalsIgnoreCase(Constantes.TRASPASO_ANTICIPADO))) {
//                traspasoLibrePagoVO.setIdFolioCtaTraspasante(element.getCvTraspasante().trim()
//                        .concat(element.getCvCuentaTraspasante().trim()));
//                traspasoLibrePagoVO.setIdFolioCtaReceptora(Constantes.IDFOLIOCUENTA_FIJA);
//            }
//            else {
//
//                traspasoLibrePagoVO.setIdFolioCtaTraspasante(element.getCvTraspasante().trim()
//                        .concat(element.getCvCuentaTraspasante().trim()));
//                traspasoLibrePagoVO.setIdFolioCtaReceptora(element.getCvReceptor().trim().concat(
//                        element.getCvCuentaReceptor().trim()));
//            }
//
//            traspasoLibrePagoVO.setFechaRegistro(element.getFeTransaccion());
//            traspasoLibrePagoVO.setFechaLiquidacion(element.getFeTransaccion());
//            traspasoLibrePagoVO.setCantidadTitulos(element.getNoTitulos().longValue());
//
//            traspasoLibrePagoVO.setTipoValor(element.getCvTipoValor().trim());
//            traspasoLibrePagoVO.setEmisora(element.getCvEmisora().trim());
//            traspasoLibrePagoVO.setSerie(element.getCvSerie().trim());
//            traspasoLibrePagoVO.setCupon(element.getCvCupon().trim());
//
//            traspasoLibrePagoVO.setTipoInstruccion("T");
//            traspasoLibrePagoVO.setReferenciaOperacion(element.getFlTransaccion().toString());
//            traspasoLibrePagoVO.setReferenciaMensaje(element.getFlTransaccion().toString());
//
//            element.setCvEstado(Constantes.ESTATUS_ENVIADO);
//            element.setFultmod(dateUtilsDao.getDateFechaDB());
//
//            bitacoraProtocolo = new BitacoraProtocolo();
//            bitacoraProtocoloPK = new BitacoraProtocoloPK();
//
//            bitacoraProtocoloPK.setFlTransaccion(element.getFlTransaccion());
//            bitacoraProtocoloPK.setMensaje(Constantes.ESTATUS_ENVIADO);
//            bitacoraProtocoloPK.setFeOperacion(element.getFultmod());
//
//            bitacoraProtocolo.setBitacoraProtocoloPK(bitacoraProtocoloPK);
//            procesaOperacion(traspasoLibrePagoVO, element, bitacoraProtocolo);
//        }
//        log.debug("Saliendo del metodo monitoreaTransacciones()");
//        System.out.println("Saliendo del metodo monitoreaTransacciones()");
//    }
//
//    /**
//     * 
//     * @param traspasoLibrePagoVO
//     * @param element
//     * @param bitacoraProtocolo
//     * @throws BusinessException
//     */
//    private void procesaOperacion(TraspasoLibrePagoVO traspasoLibrePagoVO, Transaccion element,
//            BitacoraProtocolo bitacoraProtocolo) throws BusinessException {
//        
//        log.info("Entrando al metodo procesaOperacion()");
//        
//        try {
//            enviaOperacion(traspasoLibrePagoVO);
//        }
//        catch (ProtocoloFinancieroException protocoloFinancieroException) {
//            log.error("No se puedo enviar la operacion");
//            log.error("Exception de Protocolo : ");
//            protocoloFinancieroException.printStackTrace();
//            throw new BusinessException(errorResolver.getMessage("J0058"), "J0058");
//        }
//        transaccionDao.update(element);
//        bitacoraProtocoloDao.save(bitacoraProtocolo);
//    }
//
//    /**
//     * Envia al protocolo la operacion correspondiente al tipo de vo
//     * proporcionado
//     * 
//     * @param vo
//     * @param folioControl
//     * @throws ProtocoloFinancieroException
//     * @throws BusinessException
//     */
//    private void enviaOperacion(Object vo) throws ProtocoloFinancieroException, BusinessException {
//        
//        log.info("Entrando al metodo enviaOperacion()");
//        System.out.println("Entrando al metodo enviaOperacion()");
//
//        String isoSigned = getIsoSigned(vo);
//        String ticket = getTicket();
//        sendCola(ticket, isoSigned);
//
//    }
//
//    /**
//     * Obtiene el iso firmado
//     * 
//     * @param vo
//     * @return String
//     * @throws ProtocoloFinancieroException
//     * @throws BusinessException
//     */
//    private String getIsoSigned(Object vo) throws ProtocoloFinancieroException, BusinessException {
//        
//        log.info("Entrando al metodo getIsoSigned()");
//        System.out.println("Entrando al metodo getIsoSigned()");
//
//        String iso = null;
//        if (vo instanceof TraspasoLibrePagoVO) {
//            iso = tlpService.entrega((TraspasoLibrePagoVO) vo);
//        }
//        String certificateSerialNumber = jmsClient.getPrivateKeyProvider()
//                .getCertificateSerialNumber();
//        PrivateKey privateKey = jmsClient.getPrivateKeyProvider().getPrivateKey();
//        return jmsClient.signMessage(iso, privateKey, certificateSerialNumber);
//    }
//
//    /**
//     * Obtiene el ticket del mensaje
//     * 
//     * @return String
//     */
//    private String getTicket() {
//        log.info("Entrando al metodo getTicket()");
//        System.out.println("Entrando al metodo getTicket()");
//
//        return jmsClient.getSecurityDelegate().getTicket();
//    }
//
//    /**
//     * Env&iacute;a el mensaje a la cola
//     * 
//     * @param ticket
//     * @param isoSigned
//     */
//    private void sendCola(final String ticket, final String isoSigned) {
//        log.info("Entrando al metodo sendCola()");
//        System.out.println("Entrando al metodo sendCola()");
//
//        jmsTemplate.send(cola, new MessageCreator() {
//            public Message createMessage(Session session) throws JMSException {
//                TextMessage textMessage = session.createTextMessage();
//                textMessage.setStringProperty("credencial", ticket);
//                textMessage.setText(isoSigned);
//                return textMessage;
//            }
//        });
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
//     * @param jmsClient
//     */
//    public void setJmsClient(JmsClient jmsClient) {
//        this.jmsClient = jmsClient;
//    }
//
//    /**
//     * @param jmsTemplate
//     */
//    public void setJmsTemplate(JmsTemplate jmsTemplate) {
//        this.jmsTemplate = jmsTemplate;
//    }
//
//    /**
//     * @param tlpService
//     */
//    public void setTlpService(TraspasoLibrePago tlpService) {
//        this.tlpService = tlpService;
//    }
//
//    /**
//     * @param cola
//     */
//    public void setCola(String cola) {
//        this.cola = cola;
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
//     * @param errorResolver
//     */
//    public void setErrorResolver(MessageResolver errorResolver) {
//        this.errorResolver = errorResolver;
//    }
//
//    /**
//     * @param dateUtilsDao the dateUtilsDao to set
//     */
//    public void setDateUtilsDao(DateUtilsDao dateUtilsDao) {
//        this.dateUtilsDao = dateUtilsDao;
//    }
}

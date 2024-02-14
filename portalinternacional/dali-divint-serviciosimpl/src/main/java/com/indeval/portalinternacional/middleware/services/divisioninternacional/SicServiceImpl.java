/**
 * CMMV - Portal Internacional
 * Copyrigth (c) 2016 CMMV. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional;
// Cambio Multidivisas
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import com.indeval.portalinternacional.middleware.servicios.modelo.Multidivisa;
import org.springframework.dao.DataAccessException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.w3c.dom.Document;

import com.bursatec.util.message.MessageResolver;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.persistence.dao.common.DivisaDao;
import com.indeval.portaldali.persistence.modelo.CuentaNombrada;
import com.indeval.portaldali.persistence.modelo.Divisa;
import com.indeval.portaldali.persistence.modelo.SaldoNombrada;
import com.indeval.portaldali.persistence.util.DateUtils;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstadoMensajeSic;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstatusOperacion;
import com.indeval.portalinternacional.middleware.servicios.modelo.OperacionSic;
import com.indeval.portalinternacional.middleware.servicios.modelo.RBovedaValEf;
import com.indeval.portalinternacional.persistence.dao.MensajePortalDaliDAO;
import com.indeval.portalinternacional.persistence.dao.NombradaSaldoDao;
import com.indeval.portalinternacional.persistence.dao.OperacionSicDao;
import com.indeval.seguridadMensajeria.exception.DigitalSignException;
import com.indeval.seguridadMensajeria.sign.SendMessageService;
import com.indeval.seguridadMensajeria.sign.ValidateSignatureService;
import com.indeval.seguridadMensajeria.util.Constante;
import com.indeval.seguridadMensajeria.util.XMLUtils;
import com.indeval.seguridadMensajeria.vo.ErrorSeguridadMensajeriaVo;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.Annotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementacion de la interfaz de negocio SicService
 * 
 * @author Pablo Balderas
 */
public class SicServiceImpl implements SicService {
	
	private static final Logger log = LoggerFactory.getLogger(SicServiceImpl.class);

	/** DAO para las operaciones SIC */
    private OperacionSicDao operacionSicDao = null;
	
    /** Template para el envio de mensajes SIC */
    private JmsTemplate jmsTemplateSic;
    
    /** Custodios que no envian por Swift, como los custodios MILA. */
    private Set<String> custodiosSinEnvioMensaje;
    
    /** Resolvedor de Mensajes */
    private MessageResolver messageResolver = null;
    
    private final String TIPO_MENSAJE_ENTREGA = "542";
    
    private final String TIPO_MENSAJE_RECEPCION = "540";
    
    private SignMensaje signMensaje;
   
    private ValidateSignatureService validateSignatureService;
    
    private SendMessageService sendMessageServiceSign;

    private MensajePortalDaliDAO mensajePortalDaliDao = null;
    
    private NombradaSaldoDao nombradaSaldoDao = null;
    
    private DivisaDao divisaDao = null;
    
    /*
     * (non-Javadoc)
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.SicService#actualizarOperacionSic(int, com.indeval.portalinternacional.middleware.servicios.modelo.OperacionSic, com.indeval.portalinternacional.middleware.servicios.modelo.OperacionSic, java.util.Date)
     */
	public void actualizarOperacionSic(int indice, OperacionSic operacionSic, OperacionSic operacionSicActual, Date fechaActual) throws BusinessException {
		log.debug("SicServiceImpl :: actualizarOperacionSic");
		try {
			XStream xstream = new XStream();
			Annotations.configureAliases(xstream, EstadoMensajeSic.class);
			EstadoMensajeSic msg = new EstadoMensajeSic();
			msg.setAccion(EstadoMensajeSic.TipoAccion.CAMBIO_ESTADO);
			
			boolean isAutorizo = operacionSic.isAutorizo();
			boolean isLibero = operacionSic.isLibero();
			boolean isConMensaje = operacionSic.isConMensaje();
			boolean isCancelo = operacionSic.isCancelo();
			boolean isConfirmo = operacionSic.isConfirmo();
			boolean isHabilito = operacionSic.isHabilito();
			boolean isRegreso = operacionSic.isRegreso();
            boolean isRegresoPosicion = operacionSic.isRegresoPosicion();

			if (operacionSicActual.getEstatusOperacion().getIdEstatusOperacion() != Constantes.ST_OPER_LIBERADA &&
					operacionSicActual.getEstatusOperacion().getIdEstatusOperacion() != Constantes.ST_OPER_ENVIO_LIBERACION &&
					operacionSicActual.getEstatusOperacion().getIdEstatusOperacion() != Constantes.ST_OPER_MENSAJE_LIBERACION &&
					operacionSicActual.getEstatusOperacion().getIdEstatusOperacion() != Constantes.ST_OPER_PENDIENTE_LIBERAR &&
					// se agrega validacion de los estados
					operacionSicActual.getEstatusOperacion().getIdEstatusOperacion() != Constantes.ST_OPER_RETENIDA &&
					operacionSicActual.getEstatusOperacion().getIdEstatusOperacion() != Constantes.ST_OPER_HABILITADA &&
					isCancelo) {

				operacionSicActual.setEstatusOperacion(new EstatusOperacion(Constantes.ST_OPER_ENVIO_CANCELACION));
				operacionSicActual.setFecha592(fechaActual);
				if (isConMensaje) {
					operacionSicActual.setEstatusOperacion(new EstatusOperacion(Constantes.ST_OPER_CANCELADA));
					operacionSicActual.setMensajeEfectivo(null);
				}
				msg.setEstado(EstadoMensajeSic.TipoEstado.CANCELADO);
			}
			else {
				if (operacionSicActual.getEstatusOperacion().getIdEstatusOperacion() == Constantes.ST_OPER_PENDIENTE_AUTORIZAR && isAutorizo) {
					//operacionSicActual.setEstatusOperacion(new EstatusOperacion(Constantes.ST_OPER_NOTIFICADA));
					operacionSicActual.setFechaNotificacion(fechaActual);
					if (isConMensaje) {
						operacionSicActual.setMensajeEfectivo("C210");
					}
					//msg.setEstado(EstadoMensajeSic.TipoEstado.AUTORIZADO);
					
					Long idEstatus = operacionSicActual.getEstatusOperacion().getIdEstatusOperacion();
					
					if (validarSaldo(operacionSicActual, idEstatus)) {
							
						operacionSicActual.setEstatusOperacion(new EstatusOperacion(Constantes.ST_OPER_NOTIFICADA));
						msg.setEstado(EstadoMensajeSic.TipoEstado.AUTORIZADO);
							
					}else
						throw new BusinessException("El participante no tiene saldo disponible para la autorizacion de la SIC " + operacionSicActual.getIdOperacionesSic());
					
				}
				else if (operacionSicActual.getEstatusOperacion().getIdEstatusOperacion() == Constantes.ST_OPER_CONFIRMADA && isLibero) {
					//operacionSicActual.setEstatusOperacion(new EstatusOperacion(Constantes.ST_OPER_PENDIENTE_LIBERAR));
					operacionSicActual.setFechaHora(fechaActual);
					if (isConMensaje) {
						operacionSicActual.setMensajeEfectivo("C103");
					}
					//msg.setEstado(EstadoMensajeSic.TipoEstado.LIBERAR);
					
					Long idEstatus = operacionSicActual.getEstatusOperacion().getIdEstatusOperacion();
					
					if (validarSaldo(operacionSicActual, idEstatus)) {
							
						operacionSicActual.setEstatusOperacion(new EstatusOperacion(Constantes.ST_OPER_PENDIENTE_LIBERAR));
						msg.setEstado(EstadoMensajeSic.TipoEstado.LIBERAR);
							
					}else
						throw new BusinessException("El participante no tiene saldo no disponible para la liberacion de la SIC " + operacionSicActual.getIdOperacionesSic());
					
				}
				else if ((operacionSicActual.getEstatusOperacion().getIdEstatusOperacion() == Constantes.ST_OPER_CONFIRMADA_PARCIAL
						|| operacionSicActual.getEstatusOperacion().getIdEstatusOperacion() == Constantes.ST_OPER_LIBERADA_PARCIAL) && isLibero) {
//					operacionSicActual.setEstatusOperacion(new EstatusOperacion(Constantes.ST_OPER_LIBERADA_PARCIAL));
					operacionSicActual.setFechaHora(fechaActual);
					if (isConMensaje) {
						operacionSicActual.setMensajeEfectivo("C103");
					}
					//msg.setEstado(EstadoMensajeSic.TipoEstado.LIBERAR_PARCIAL);
					
					Long idEstatus = operacionSicActual.getEstatusOperacion().getIdEstatusOperacion();
					
					if (validarSaldo(operacionSicActual, idEstatus)) {
						
						operacionSicActual.setEstatusOperacion(new EstatusOperacion(Constantes.ST_OPER_LIBERADA_PARCIAL));
						msg.setEstado(EstadoMensajeSic.TipoEstado.LIBERAR_PARCIAL);
							
					}else
						throw new BusinessException("El participante no tiene saldo no disponible para la liberacion parcial de la SIC " + operacionSicActual.getIdOperacionesSic());
					
				}
				// Para las operaciones que esten como confirmadas y son de los custodios que no tienen
				// mensajeria Swift se puede habilitar la funcion de regresar el mensaje a enviado
				else if (operacionSicActual.getEstatusOperacion() .getIdEstatusOperacion() == Constantes.ST_OPER_CONFIRMADA &&
						operacionSicActual.getCatBic() != null &&
						this.custodiosSinEnvioMensaje != null && 
						this.custodiosSinEnvioMensaje.contains(operacionSicActual.getCatBic().getBicProd()) &&
						isRegreso) {
					// Cuando aprietan con mensaje esto no es correcto ya que no se envia ninguno se manda un error
					if (isConMensaje) {
						throw new BusinessException(messageResolver.getMessage(
								"J0111", "la operacion -- Id[" + operacionSicActual.getIdOperacionesSic().toString() + "], " + "indice[" + String.valueOf(indice + 1) + "]"));
					}
					else {
						operacionSicActual.setEstatusOperacion(new EstatusOperacion(Constantes.ST_OPER_ENVIADA));
						operacionSicActual.setFecha530(null);
						msg.setEstado(EstadoMensajeSic.TipoEstado.CONFIRMADO);
					}
				}
				// Para custodios MILA
				// Verificamos que solamente los operaciones que esten como enviadas y cuyo custodio no envie mensajes por la red Swift pueda cambiar el estado a confirmado
				else if (operacionSicActual.getEstatusOperacion() .getIdEstatusOperacion() == Constantes.ST_OPER_ENVIADA &&
						operacionSicActual.getCatBic() != null &&
						this.custodiosSinEnvioMensaje != null &&
						this.custodiosSinEnvioMensaje.contains(operacionSicActual.getCatBic().getBicProd()) && isConfirmo) {
					// Cuando aprietan con mensaje esto no es correcto ya que no se envia ninguno se manda un error
					if (isConMensaje) {
						throw new BusinessException(messageResolver.getMessage(
								"J0110", "la operacion -- Id[" + operacionSicActual.getIdOperacionesSic().toString() + "], " + "indice[" + String.valueOf(indice + 1) + "]"));
					}
					else {
						operacionSicActual.setEstatusOperacion(new EstatusOperacion(Constantes.ST_OPER_CONFIRMADA));
						operacionSicActual.setFecha530(fechaActual);
						msg.setEstado(EstadoMensajeSic.TipoEstado.CONFIRMADO);
					}
				}
				else if (operacionSicActual.getEstatusOperacion().getIdEstatusOperacion() == Constantes.ST_OPER_RETENIDA && isHabilito) {
					operacionSicActual.setEstatusOperacion(new EstatusOperacion(Constantes.ST_OPER_HABILITADA));
					operacionSicActual.setFechaHoraHabilitada(fechaActual);
					operacionSicActual.setFechaHora(fechaActual);
					msg.setEstado(EstadoMensajeSic.TipoEstado.HABILITAR);
				}
				else if ((operacionSicActual.getEstatusOperacion().getIdEstatusOperacion() == Constantes.ST_OPER_RETENIDA ||
						operacionSicActual.getEstatusOperacion().getIdEstatusOperacion() == Constantes.ST_OPER_HABILITADA) &&
						isCancelo //&& !isConMensaje
						) {
					
					operacionSicActual.setEstatusOperacion(new EstatusOperacion(Constantes.ST_OPER_CANCEL_SIST));
					operacionSicActual.setFecha592(fechaActual);
					if (isConMensaje) {
						operacionSicActual.setEstatusOperacion(new EstatusOperacion(Constantes.ST_OPER_CANCELADA));
						operacionSicActual.setMensajeEfectivo(null);
					}
					msg.setEstado(EstadoMensajeSic.TipoEstado.CANCEL_SIST);
				}
				// Se agrega el condicional en Mayo 2017 debido a que las cancelaciones del extranjero no le llegan 
				// ni a Portal ni a MOI; y con las operaciones de cambio de boveda es necesario enterar al MOI para 
				// que regrese la posicion movida.
				else if (operacionSicActual.getEstatusOperacion().getIdEstatusOperacion() == Constantes.ST_OPER_CANCELADA && 
				        isRegresoPosicion) {
				    operacionSicActual.setEstatusOperacion(new EstatusOperacion(Constantes.ST_OPER_ENVIO_CANCELACION));
				    operacionSicActual.setFecha592(fechaActual);
	                if (isConMensaje) {
	                    operacionSicActual.setEstatusOperacion(new EstatusOperacion(Constantes.ST_OPER_CANCELADA));
	                    operacionSicActual.setMensajeEfectivo(null);
	                }
	                msg.setEstado(EstadoMensajeSic.TipoEstado.REGRESO_POSICION);
				}
			}
			msg.setFechaLiquidacion(DateUtils.format(operacionSicActual.getFechaLiquidacion(), "yyyy-MM-dd HH:mm:ss"));
			msg.setFolioControl(operacionSicActual.getFolioControl().toString());
			msg.setId(String.valueOf(operacionSicActual.getIdOperacionesSic()));

            operacionSicDao.update(operacionSicActual);
            operacionSicDao.flush();

			// Evitamos mandar el mensaje para cuando solamente se regresa de confirmada a enviada crea El mensaje
			if (!operacionSicActual.isRegreso()) {
				final String msgTXT = xstream.toXML(msg);
			 	
			 	String msgFirmado = msgTXT;
			 	try {
					if(validateSignatureService.isSignature()) {
						msgFirmado = signMensaje.signMensaje(msgFirmado);
						log.info(Constante.loggerSignature(false, Constante.PORTAL_INTERNAIONAL, Constante.MOI, msgFirmado));
					}
				 	final String msgToSend = msgFirmado;
					jmsTemplateSic.send(new MessageCreator() {
						public Message createMessage(Session session) throws JMSException {
							final Message msgSession = session.createTextMessage(msgToSend);
							return msgSession;
						}
					});
				} catch (DigitalSignException e) {
					log.error(e.getMessage(), e.getCause());
		            ErrorSeguridadMensajeriaVo error = new ErrorSeguridadMensajeriaVo();
		            error.setError(e.getMessage().split("\\|")[0].trim());
		            error.setModulo(Constante.PORTAL_INTERNAIONAL);
		            error.setXml(msgFirmado);
		            
		            Document doc = XMLUtils.convertStringToDocument(msgFirmado);
		            error.setNumeroSerieCertificado(e.getMessage().split("\\|")[1].trim());
		            error.setTipoMensaje(XMLUtils.getStringToElement(doc, Constante.TIPO_MENSAJE));
		            sendMessageServiceSign.sendQueueAlert(error);
				}
			}
		}
        catch(Exception e) {
        	throw new BusinessException(e.getMessage(), e);
        }
	}
    
    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.SicService#esValidoEstadoAdecuadoDeContraoperacion(OperacionSic)
     */
	public boolean esValidoEstadoAdecuadoDeContraoperacion(OperacionSic operacionSic) throws BusinessException {
		log.debug("SicServiceImpl :: esValidoEstadoAdecuadoDeContraoperacion");
	    boolean estadoValido = false;

	    try {
	        OperacionSic opsic = null;
	        List<OperacionSic> operaciones = new ArrayList<OperacionSic>();
	        operacionSic = this.operacionSicDao.findOperacionByIdOperacion(operacionSic.getIdOperacionesSic());

	        if (operacionSic.getTipoMensaje().equals(TIPO_MENSAJE_ENTREGA)) {
	            operaciones = this.operacionSicDao.getContraoperacionRecepcionEstadoAdecuado(
	                    operacionSic.getEmision().getIdEmision());
	            if (operaciones != null && operaciones.size() > 0) {
	                opsic = operaciones.get(0);
	                estadoValido = true;
	                if (opsic.getEstatusOperacion().getIdEstatusOperacion().equals(Constantes.ST_OPER_CONFIRMADA)) {
	                    estadoValido = true;
	                }
	            }
	        }
	        else if (operacionSic.getTipoMensaje().equals(TIPO_MENSAJE_RECEPCION)) {
	            operaciones = this.operacionSicDao.getContraoperacionEntregaEstadoAdecuado(
	                    operacionSic.getEmision().getIdEmision());
	            if (operaciones != null && operaciones.size() > 0) {
	                opsic = operaciones.get(0);
	                estadoValido = true;
	                if (opsic.getEstatusOperacion().getIdEstatusOperacion().equals(Constantes.ST_OPER_LIBERADA)) {
	                    estadoValido = true;
	                }
	            }
	        }
	    } catch (Exception e) {
	        throw new BusinessException(e.getMessage());
	    }

	    return estadoValido;
	}

	/**
	 * Metodo para establecer el atributo operacionSicDao
	 * @param operacionSicDao El valor del atributo operacionSicDao a establecer.
	 */
	public void setOperacionSicDao(OperacionSicDao operacionSicDao) {
		this.operacionSicDao = operacionSicDao;
	}
	
	public void setNombradaSaldoDao(NombradaSaldoDao nombradaSaldoDao) {
		this.nombradaSaldoDao = nombradaSaldoDao;
	}
	
	public void setDivisaDao(DivisaDao divisaDao) {
		this.divisaDao = divisaDao;
	}

	/**
	 * Metodo para establecer el atributo custodiosSinEnvioMensaje
	 * @param custodiosSinEnvioMensaje El valor del atributo custodiosSinEnvioMensaje a establecer.
	 */
	public void setCustodiosSinEnvioMensaje(Set<String> custodiosSinEnvioMensaje) {
		this.custodiosSinEnvioMensaje = custodiosSinEnvioMensaje;
	}

	/**
	 * Metodo para establecer el atributo messageResolver
	 * @param messageResolver El valor del atributo messageResolver a establecer.
	 */
	public void setMessageResolver(MessageResolver messageResolver) {
		this.messageResolver = messageResolver;
	}

	/**
	 * Metodo para establecer el atributo jmsTemplateSic
	 * @param jmsTemplateSic El valor del atributo jmsTemplateSic a establecer.
	 */
	public void setJmsTemplateSic(JmsTemplate jmsTemplateSic) {
		this.jmsTemplateSic = jmsTemplateSic;
	}

	public void setSignMensaje(SignMensaje signMensaje) {
		this.signMensaje = signMensaje;
	}

	public void setValidateSignatureService(ValidateSignatureService validateSignatureService) {
		this.validateSignatureService = validateSignatureService;
	}
	
	public void setSendMessageServiceSign(SendMessageService sendMessageServiceSign) {
		this.sendMessageServiceSign = sendMessageServiceSign;
	}

    public void setMensajePortalDaliDao(MensajePortalDaliDAO mensajePortalDaliDao) {
        this.mensajePortalDaliDao = mensajePortalDaliDao;
    }

	@Override
	public OperacionSic getOperacionSicByFolioControl(BigInteger folioControl) {
		return operacionSicDao.findOperacionByFolioControl(folioControl);
	}

	@Override
	public void cancelaParcialidadOperacionSIC(OperacionSic operacionSic, Date fechaActual, Long parcialidad) throws BusinessException {
		log.debug("SicServiceImpl :: cancelaParcialidadOperacionSIC");
		
		XStream xstream = new XStream();
		Annotations.configureAliases(xstream, EstadoMensajeSic.class);
		EstadoMensajeSic msg = new EstadoMensajeSic();
		msg.setAccion(EstadoMensajeSic.TipoAccion.CAMBIO_ESTADO);
		operacionSic.setEstatusOperacion(new EstatusOperacion(Constantes.ST_OPER_CANCELADA));
		operacionSic.setFecha592(fechaActual);
        operacionSicDao.update(operacionSic);
        operacionSicDao.flush();    
        
		msg.setEstado(EstadoMensajeSic.TipoEstado.CANCELADO_REMANENTE);
		msg.setFechaLiquidacion(DateUtils.format(operacionSic.getFechaLiquidacion(), "yyyy-MM-dd HH:mm:ss"));
		msg.setFolioControl(operacionSic.getFolioControl().toString());
		msg.setId(String.valueOf(operacionSic.getIdOperacionesSic()));
		
		final String msgTXT = xstream.toXML(msg);
	 	final String msgToSend = msgTXT;

		jmsTemplateSic.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				final Message msgSession = session.createTextMessage(msgToSend);
				return msgSession;
			}
		});
	}

	@Override
	public void eliminarRegistroControlLiberacionParcialesRecepciones(long idOperacionSic, long folioControl) {
		log.debug("SicServiceImpl :: eliminarRegistroControlLiberacionRecepciones");
        try {
            this.operacionSicDao.eliminarRegistroControlLiberacionRecepciones(idOperacionSic, folioControl);
         } catch (DataAccessException daex) {
             daex.printStackTrace();
             if (daex.getMessage() != null) {
                 log.error(daex.getMessage());
             }
             if (daex.getCause() != null) {
                 log.error(daex.getCause().getMessage());
             }
             String mensajeError = 
                     "Error de aceso de datos al intentar eliminar un registro de la tabla T_CONTROL_LIBERACION_INT =[" 
                    		 + "idOperacionSic=" + idOperacionSic + "|" +  "folioControl=" + folioControl + "]";
             log.error("\n\n####### " + mensajeError + "\n\n");
             throw new BusinessException(mensajeError);
         } catch (Exception ex) {
             ex.printStackTrace();
             if (ex.getMessage() != null) {
                 log.error(ex.getMessage());
             }
             if (ex.getCause() != null) {
                 log.error(ex.getCause().getMessage());
             }
             String mensajeError = "Error al intentar eliminar un registro de la tabla T_CONTROL_LIBERACION_INT =[" + 
                     "idOperacionSic=" + idOperacionSic + "|" + "folioControl=" + folioControl + "]";
             log.error("\n\n####### " + mensajeError + "\n\n");
             throw new BusinessException(mensajeError);
         }
	}

	@Override
	public void notificaCambioEstadoMovEfeDivInt(final String xmlEnviar) {
		try {
			log.debug(String.format("Mensaje %s ",xmlEnviar));
			jmsTemplateSic.send(new MessageCreator() {
				public Message createMessage(Session session) throws JMSException {
					final Message msgSession = session.createTextMessage(xmlEnviar);
					return msgSession;
				}
			});
		} catch(Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public String crearXML(Multidivisa multidivisa){
		XStream xstream = new XStream();
		Annotations.configureAliases(xstream, Multidivisa.class);
		String xmlMultidivisa = null;

		try {
			xmlMultidivisa = xstream.toXML(multidivisa);

			if(validateSignatureService.isSignature()) {
				xmlMultidivisa = signMensaje.signMensaje(xmlMultidivisa);
			}
		} catch (DigitalSignException e) {
			ErrorSeguridadMensajeriaVo error = new ErrorSeguridadMensajeriaVo();
			error.setError(e.getMessage().split("\\|")[0].trim());
			error.setModulo(Constante.PORTAL_INTERNAIONAL);
			error.setXml(xmlMultidivisa);

			Document doc = XMLUtils.convertStringToDocument(xmlMultidivisa);
			error.setNumeroSerieCertificado(e.getMessage().split("\\|")[1].trim());
			error.setTipoMensaje(XMLUtils.getStringToElement(doc, Constante.TIPO_MENSAJE));

			sendMessageServiceSign.sendQueueAlert(error);
		} catch(Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
        return xmlMultidivisa;
    }
	
	public boolean validarSaldo (OperacionSic operacionSicActual, Long idEstatus) {
		
		RBovedaValEf bovedaEfectivo = nombradaSaldoDao.getEfectivoByIdValores(operacionSicActual.getBoveda().getIdBoveda());
		
		if (bovedaEfectivo == null)
			throw new BusinessException("No ha sido encontrada la boveda para la SIC ", 
					operacionSicActual.getIdOperacionesSic()+ " con id boveda "+ 
			operacionSicActual.getBoveda().getIdBoveda());
		
		CuentaNombrada cuentaNombrada = nombradaSaldoDao.getCuentaNombradaById(
				operacionSicActual.getCuentaNombrada().getIdCuentaNombrada());
			
		if (cuentaNombrada == null)
			throw new BusinessException("No ha sido encontrada la cuenta nombrada para la SIC ", 
					operacionSicActual.getIdOperacionesSic()+ " con id cuenta nombrada "
			+ operacionSicActual.getCuentaNombrada().getIdCuentaNombrada());
			
		CuentaNombrada cuentaNombradaPorInstitucion = nombradaSaldoDao.getCuentaNombradaByInstitucionAndCuenta(cuentaNombrada.getInstitucion().getIdInstitucion(), "2000");
		
		if (cuentaNombradaPorInstitucion == null) 
			throw new BusinessException("No ha sido encontrada la cuenta nombrada asociada a la institucion para la SIC ", 
					operacionSicActual.getIdOperacionesSic()+ " con id cuenta nombrada " 
			+ operacionSicActual.getCuentaNombrada().getIdCuentaNombrada());
		
		Divisa divisa = null;
		Divisa[] divisas = divisaDao.findDivisas();
		if (divisas != null && divisas.length > 0) {				
			int i = 0;				
			while (i <= divisas.length -1  ) {					
				if(divisas[i].getClaveAlfabetica().equals(operacionSicActual.getDivisa())){					
					divisa = divisas[i];
					break;
				}
				i++;
			}
		}
		
		List<SaldoNombrada> lstSaldoNombrada = nombradaSaldoDao.getSaldoNombradaByCuentaNombradaAndBovedaAndDivisa(cuentaNombradaPorInstitucion.getIdCuentaNombrada(), 
				bovedaEfectivo.getIdEfectivo(), divisa.getIdDivisa());
					
		if (lstSaldoNombrada != null && !lstSaldoNombrada.isEmpty()) {		
			SaldoNombrada saldoNombrada = lstSaldoNombrada.get(0);
			
			if (idEstatus.equals(Constantes.ST_OPER_PENDIENTE_AUTORIZAR)) {
				
				BigDecimal saldoDisponible = saldoNombrada.getSaldoDisponible();	
				return (saldoDisponible.compareTo(operacionSicActual.getImporte()) >= 0 || saldoDisponible.compareTo(operacionSicActual.getImporte()) == 0);
				
			}else if (idEstatus.equals(Constantes.ST_OPER_CONFIRMADA))  {

				BigDecimal saldoNoDisponible = saldoNombrada.getSaldoNoDisponible();		
				return (saldoNoDisponible.compareTo(operacionSicActual.getImporte()) >= 0 || saldoNoDisponible.compareTo(operacionSicActual.getImporte()) == 0);
				
			}else {
				
				BigDecimal saldoNoDisponible = saldoNombrada.getSaldoNoDisponible();		
				return (saldoNoDisponible.compareTo(operacionSicActual.getImporteParcial()) >= 0 || saldoNoDisponible.compareTo(operacionSicActual.getImporteParcial()) == 0);
				
			}
							
		}else 
			return false;
		
	}

    /**
     * Multidivisas
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.SicService#notificaCambioEstadoMovEfeDivInt(Multidivisa)
     */
    @Override
    public void notificaCambioEstadoMovEfeDivInt(Multidivisa multidivisa) {
        XStream xstream = new XStream();
        Annotations.configureAliases(xstream, Multidivisa.class);
        String xmlMultidivisa = null;

        try {
            xmlMultidivisa = xstream.toXML(multidivisa);

            if (validateSignatureService.isSignature()) {
                xmlMultidivisa = signMensaje.signMensaje(xmlMultidivisa);
            }

            final String xmlEnviar = xmlMultidivisa;
            jmsTemplateSic.send(new MessageCreator() {
                public Message createMessage(Session session) throws JMSException {
                    final Message msgSession = session.createTextMessage(xmlEnviar);
                    return msgSession;
                }
            });
        } catch (DigitalSignException e) {
            log.error(e.getMessage(), e.getCause());
            ErrorSeguridadMensajeriaVo error = new ErrorSeguridadMensajeriaVo();
            error.setError(e.getMessage().split("\\|")[0].trim());
            error.setModulo(Constante.PORTAL_INTERNAIONAL);
            error.setXml(xmlMultidivisa);

            Document doc = XMLUtils.convertStringToDocument(xmlMultidivisa);
            error.setNumeroSerieCertificado(e.getMessage().split("\\|")[1].trim());
            error.setTipoMensaje(XMLUtils.getStringToElement(doc, Constante.TIPO_MENSAJE));

            sendMessageServiceSign.sendQueueAlert(error);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }


}

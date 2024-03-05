package com.indeval.portaldali.middleware.services.conciliacionModulos;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.collections.map.MultiKeyMap;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.w3c.dom.Document;

import com.indeval.portaldali.middleware.constantes.CampoConciliacionModulosEnum;
import com.indeval.portaldali.middleware.constantes.EstiloDatosExcel;
import com.indeval.portaldali.middleware.constantes.FlujoConciliacionModulosEnum;
import com.indeval.portaldali.middleware.dto.ConciliacionModulosDTO;
import com.indeval.portaldali.middleware.dto.ConciliacionModulosExcelDTO;
import com.indeval.portaldali.middleware.dto.InstruccionEfectivoDTO;
import com.indeval.portaldali.middleware.dto.criterio.CriteriosConciliacionModulosDTO;
import com.indeval.portaldali.middleware.services.common.BitacoraReenvioConfLiqReteService;
import com.indeval.portaldali.middleware.services.common.SignMensaje;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.util.ReenvioOperacionesConverter;
import com.indeval.portaldali.modelo.to.commons.BitacoraReenvioConfLiqReteDTO;
import com.indeval.portaldali.persistence.dao.conciliacionModulos.BitacoraReenvioDAO;
import com.indeval.portaldali.persistence.dao.conciliacionModulos.ConciliacionModulosDAO;
import com.indeval.portaldali.persistence.dao.estatus.ConsultaInstruccionesEfectivoDAO;
import com.indeval.portaldali.persistence.model.BitacoraEstadoReenvio;
import com.indeval.portaldali.persistence.model.BitacoraReenvio;
import com.indeval.portaldali.persistence.model.InstruccionEfectivoSimple;
import com.indeval.seguridadMensajeria.exception.DigitalSignException;
import com.indeval.seguridadMensajeria.sign.SendMessageService;
import com.indeval.seguridadMensajeria.sign.ValidateSignatureService;
import com.indeval.seguridadMensajeria.util.Constante;
import com.indeval.seguridadMensajeria.util.XMLUtils;
import com.indeval.seguridadMensajeria.vo.ErrorSeguridadMensajeriaVo;
import com.indeval.spei.dto.ReenviaReteDTO;
import com.indeval.spei.dto.RespuestaReenviaReteDTO;
import com.indeval.spei.exception.SpeiFacadeException;
import com.indeval.spei.interfaces.Operaciones;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class ConciliacionModulosServiceImpl implements ConciliacionModulosService {

	private static final Logger logger = LoggerFactory.getLogger(ConciliacionModulosServiceImpl.class);
	private static final SimpleDateFormat DATE_FORMAT_LARGO = new SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy | HH:mm:ss", new Locale("es", "ES"));
	private static final int ANCHO_COLUMNAS_EXCEL = 5000;
	private static final String RESULTADO_FAIL = "FAIL";
	
	public static final String DESC_MODULO_ORIGEN_AE = "AE";
	public static final String DESC_MODULO_ORIGEN_MOV = "MOV";
	public static final String DESC_MODULO_ORIGEN_MAV = "MAV";
	public static final String DESC_MODULO_ORIGEN_MAV_PFI = "MAV_PFI";
	public static final String DESC_MODULO_ORIGEN_MOS = "MOS";
	public static final String DESC_MODULO_ORIGEN_SLV = "SLV";
	public static final String DESC_MODULO_ORIGEN_AS = "AS";
	public static final String DESC_MODULO_ORIGEN_MATCH = "MATCH";
	
	public static final String LLAVE_REENVIO_MODULO_DESTINO = "destino";
	
	private ConciliacionModulosDAO conciliacionModulosDAO;
	private BitacoraReenvioDAO bitacoraReenvioDAO;
	private ConsultaInstruccionesEfectivoDAO consultaInstruccionesEfectivoDAO;
	private JmsTemplate jmsTemplateReenvioMov;
	private ValidateSignatureService validateSignatureService;
	private SignMensaje signMensaje;
	private SendMessageService sendMessageServiceSign;
	private Operaciones ejbSpeiOperaciones;
	private BitacoraReenvioConfLiqReteService bitacoraReenvioConfLiqReteService;
	private JmsTemplate jmsTemplateReenvioMAV;
    private JmsTemplate jmsTemplateReenvioMOS;
    private JmsTemplate jmsTemplateReenvioSLV;
    private JmsTemplate jmsTemplateReenvioADAPTERMAVPFI;
    private JmsTemplate jmsTemplateReenvioAE;
    private JmsTemplate jmsTemplateReenvioMATCH;

    private List<BitacoraEstadoReenvio> lstBitacoraEstadoReenvio = new ArrayList<>();
    private String cadenaResultado;

	private static Comparator<FlujoConciliacionModulosEnum> comparatorConciliacion = new Comparator<FlujoConciliacionModulosEnum>() {
		@Override
		public int compare(FlujoConciliacionModulosEnum o1, FlujoConciliacionModulosEnum o2) {
			return o1.getId() - o2.getId();
		}
	};
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<ConciliacionModulosDTO> obtenConciliacionModulos(CriteriosConciliacionModulosDTO criterios) {	
		FlujoConciliacionModulosEnum config = FlujoConciliacionModulosEnum.obtenPorId(criterios.getIdTab());
		Method metodoDAO;
		Class[] argumentosMetodoDAO = new Class[] {CriteriosConciliacionModulosDTO.class};
		
		try {
			List<ConciliacionModulosDTO> resultados = new ArrayList<ConciliacionModulosDTO>();
			metodoDAO = ConciliacionModulosDAO.class.getDeclaredMethod(config.getMetodoDAO(), argumentosMetodoDAO);
			resultados = (List<ConciliacionModulosDTO>) metodoDAO.invoke(this.conciliacionModulosDAO, criterios);
			List<BitacoraEstadoReenvio> estadosReenvio = null;
			Map<String, Method> mapMetodos = this.getMetodosCampos(CampoConciliacionModulosEnum.getCamposXMLPorFlujo(
					FlujoConciliacionModulosEnum.obtenPorId(criterios.getIdTab())));
			List<String> lstFoliosOperaciones = new ArrayList<>();
			
			for(ConciliacionModulosDTO resultado : resultados) {
				
	            StringBuilder strBuilder = new StringBuilder();
	            
	            for(Entry<String, Method> entryMetodo : mapMetodos.entrySet()) {
	            	if(entryMetodo.getValue().invoke(resultado) != null) {
	            		strBuilder.append(entryMetodo.getValue().invoke(resultado).toString()+"|");
	            	}
	            }
	            
	            lstFoliosOperaciones.add(strBuilder.toString());
	            
			}
            
			//Busca si las operaciones existentes en conciliación ya se intentaron reenviar
            estadosReenvio = buscaOperacionesReenvio(lstFoliosOperaciones);
			
            //Cambia el estado de las operaciones encontradas a no enviadas
			actualizarEstadosreenvio(estadosReenvio, lstFoliosOperaciones);
			
			return resultados;
		} catch (InvocationTargetException | IllegalArgumentException | IllegalAccessException | NoSuchMethodException | SecurityException e) {
			logger.error("Error al obtener el metodo "+ config.getMetodoDAO() +" del DAO de Conciliacion de Modulos Indeval", e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} catch (Exception e) {
			logger.error("Error al obtener el metodo "+ config.getMetodoDAO() +" del DAO de Conciliacion de Modulos Indeval", e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
	}
	
	private List<BitacoraEstadoReenvio> buscaOperacionesReenvio(List<String> lstFoliosOperaciones) {
    	List<BitacoraEstadoReenvio> estadosReenvio = new ArrayList<>();
        if(lstFoliosOperaciones != null && !lstFoliosOperaciones.isEmpty()){
        	List<String> foliosBuscar = null;
        	int limiteFolios = 1000;
            int loops = lstFoliosOperaciones.size() / limiteFolios;
            int sobrante = lstFoliosOperaciones.size() - (limiteFolios * loops);
            
            foliosBuscar = lstFoliosOperaciones.subList(0, sobrante);
        	
            estadosReenvio.addAll(this.bitacoraReenvioDAO.getEstadosReenvioByPk(foliosBuscar));
            
            for(int i = 0; i < loops ; i++) {
            	
            	foliosBuscar = 
            			lstFoliosOperaciones.subList((i * limiteFolios) + sobrante, (i * limiteFolios) + limiteFolios);

            	
            	estadosReenvio.addAll(this.bitacoraReenvioDAO.getEstadosReenvioByPk(foliosBuscar));
            	
            }
        }
        
        return estadosReenvio;
		
	}
	
	private void actualizarEstadosreenvio(List<BitacoraEstadoReenvio> estadosReenvio, List<String> lstFoliosOperaciones) {
        if(estadosReenvio != null && !estadosReenvio.isEmpty()){
        	List<String> foliosActualizar = null;
        	int limiteFolios = 1000;
            int loops = lstFoliosOperaciones.size() / limiteFolios;
            int sobrante = lstFoliosOperaciones.size() - (limiteFolios * loops);
            
            foliosActualizar = lstFoliosOperaciones.subList(0, sobrante);
        	
        	this.bitacoraReenvioDAO.updateEstadosReenvio(foliosActualizar, false);
            
            for(int i = 0; i < loops ; i++) {
            	
            	foliosActualizar = 
            			lstFoliosOperaciones.subList((i * limiteFolios) + sobrante, (i * limiteFolios) + limiteFolios);

            	
            	this.bitacoraReenvioDAO.updateEstadosReenvio(foliosActualizar, false);
            	
            }
        }
        
	}
	
	@Override
	public ConciliacionModulosExcelDTO generaExcelConciliacionModulos(CriteriosConciliacionModulosDTO criterios) throws BusinessException {
		Workbook libro = new HSSFWorkbook();
		MultiKeyMap estilos = this.generaMapaEstilos(libro);
		Map<FlujoConciliacionModulosEnum, List<ConciliacionModulosDTO>> mapaDatos = this.obtenConciliacionesModulos(criterios);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ConciliacionModulosExcelDTO conciliacionModulosExcelDTO = new ConciliacionModulosExcelDTO();
		conciliacionModulosExcelDTO.setReporteVacio(true);
		
		try {
			for(Entry<FlujoConciliacionModulosEnum, List<ConciliacionModulosDTO>> entry : mapaDatos.entrySet()) {
				if(entry.getValue() != null && entry.getValue().size() > 0) {
					this.llenaHojaReporteConciliaciones(libro.createSheet(entry.getKey().getOrigen() + "-" + entry.getKey().getDestino()), 
							CampoConciliacionModulosEnum.getCamposPorFlujoExcel(entry.getKey()), entry.getValue(), estilos, criterios);
					
					conciliacionModulosExcelDTO.setReporteVacio(false);
				}
			}
	
			libro.write(os);
			conciliacionModulosExcelDTO.setBytesReporte(os.toByteArray());
			
			return conciliacionModulosExcelDTO;
		} catch(IOException e) {
			logger.error("Se presentó un error al escribir el reporte en el arreglo de bytes.", e);
			throw new BusinessException(e.getMessage());
		} catch(Exception e) {
			logger.error("Se presentó un error inesperado al generar el reporte de conciliaciones.", e);
			throw new BusinessException(e.getMessage());
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				logger.debug("No se logró cerrar el ByteArrayOutputStream con que se generó el arreglo de bytes del reporte");
			}
		}
	}

	/**
	 * Reenvio de mensajes particular de tab a MAV->AS.
	 * @param reenvios Los registros de pantalla
	 * @param flujo El flujo atado
	 * @param claveUsuario El usuario
	 * @param mapMetodos Un mapa de metodos
	 */
	private void reenviarMensajesMAV_AS(List<ConciliacionModulosDTO> reenvios, FlujoConciliacionModulosEnum flujo, String claveUsuario, Map<String, Method> mapMetodos) {
        BitacoraReenvio bitacoraReenvio = new BitacoraReenvio();
        Map<String, Object> mapReenvio;
        final String DERECHOS = "Registro";
        List<Map<String, Object>> lstReenviosMAV = new ArrayList<>();
        List<Map<String, Object>> lstReenviosADAPTERMAVPFI = new ArrayList<>();
        XStream xstream = new XStream(new DomDriver());
        xstream.alias("reenvios", List.class);
        xstream.registerConverter(new ReenvioOperacionesConverter());

	    bitacoraReenvio.setOrigen(flujo.getOrigen());
        bitacoraReenvio.setDestino(flujo.getDestino());
        bitacoraReenvio.setFechaReenvio(new Date());
        bitacoraReenvio.setUsuario(claveUsuario);

        for(ConciliacionModulosDTO reenvio : reenvios) {
            mapReenvio = new HashMap<>();
            StringBuilder strBuilder = new StringBuilder();
            mapReenvio.put(LLAVE_REENVIO_MODULO_DESTINO, flujo.getDestino());
            for(Entry<String, Method> entryMetodo : mapMetodos.entrySet()) {
                try {
                	mapReenvio.put(entryMetodo.getKey(), entryMetodo.getValue().invoke(reenvio));
	            	if(entryMetodo.getValue().invoke(reenvio) != null) {
	            		strBuilder.append(entryMetodo.getValue().invoke(reenvio).toString()+"|");
	            	}
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    logger.debug("Se presentó un problema al obtener el valor del método get de la propiedad " + entryMetodo.getKey(), e);
                }
            }
            
            if(administraBitacoraEstadoReenvio(strBuilder.toString())) {
                
                if (DERECHOS.equals(reenvio.getTipoOperacion().trim())) {
                    lstReenviosADAPTERMAVPFI.add(mapReenvio);
                }
                else {
                    lstReenviosMAV.add(mapReenvio);
                }
            	
            }
        }

        String mensajeXml_MAV;
        String mensajeXml_ADAPTERMAVPFI;
        StringBuffer mensajeBitacorar = new StringBuffer();
        if (lstReenviosMAV.size() > 0) {
            mensajeXml_MAV = xstream.toXML(lstReenviosMAV);
            logger.debug("####### MensajeXML_MAV=[" + mensajeXml_MAV + "]");
            mensajeBitacorar.append(mensajeXml_MAV);
            this.encolaMensajeReenvio(flujo.getOrigen(), mensajeXml_MAV);
        }
        if (lstReenviosADAPTERMAVPFI.size() > 0) {
            mensajeXml_ADAPTERMAVPFI = xstream.toXML(lstReenviosADAPTERMAVPFI);
            logger.debug("####### MensajeXML_ADAPTERMAVPFI=[" + mensajeXml_ADAPTERMAVPFI + "]");
            mensajeBitacorar.append(mensajeXml_ADAPTERMAVPFI);
            this.encolaMensajeReenvio(DESC_MODULO_ORIGEN_MAV_PFI, mensajeXml_ADAPTERMAVPFI);
        }
        
        if(mensajeBitacorar.length() > 0) {
	        logger.debug("####### Mensaje a Bitacorar=[" + mensajeBitacorar.toString() + "]");
	        bitacoraReenvio.setXml(mensajeBitacorar.toString());
	        this.bitacoraReenvioDAO.save(bitacoraReenvio);
        }
	}

	
	@Override
	public void reenviaMensajes(List<ConciliacionModulosDTO> reenvios, FlujoConciliacionModulosEnum flujo, String claveUsuario) {
		try {
	        String mensajeXml = "";
			Map<String, Method> mapMetodos = this.getMetodosCampos(CampoConciliacionModulosEnum.getCamposXMLPorFlujo(flujo));
			final String ORIGEN_MAV = "MAV";
			final String DESTINO_AS = "AS";
	        BitacoraReenvio bitacoraReenvio = new BitacoraReenvio();
	
	        bitacoraReenvio.setOrigen(flujo.getOrigen());
	        bitacoraReenvio.setDestino(flujo.getDestino());
	        bitacoraReenvio.setFechaReenvio(new Date());
	        bitacoraReenvio.setUsuario(claveUsuario);
	        
			/* Separacion del reenvio del tab MAV->AS, debido a que los registros con TO = Deposito y Retiro van directamente a EnvioBloques
			 * y  los registros con TO = Registro van directamente a Adaptador MAV PFI. */
	        if (ORIGEN_MAV.equals(flujo.getOrigen()) && DESTINO_AS.equals(flujo.getDestino())) {
	        	this.reenviarMensajesMAV_AS(reenvios, flujo, claveUsuario, mapMetodos);
	        }
	        else {
	            Map<String, Object> mapReenvio;
	            List<Map<String, Object>> lstReenvios = new ArrayList<>();
	            XStream xstream = new XStream(new DomDriver());
	            xstream.alias("reenvios", List.class);
	            xstream.registerConverter(new ReenvioOperacionesConverter());
	
	            for(ConciliacionModulosDTO reenvio : reenvios) {
	                mapReenvio = new HashMap<>();
	                StringBuilder strBuilder = new StringBuilder();
	                mapReenvio.put(LLAVE_REENVIO_MODULO_DESTINO, flujo.getDestino());
	
	                for(Entry<String, Method> entryMetodo : mapMetodos.entrySet()) {
	                    try {
	                        mapReenvio.put(entryMetodo.getKey(), entryMetodo.getValue().invoke(reenvio));
	    	            	if(entryMetodo.getValue().invoke(reenvio) != null) {
	    	            		strBuilder.append(entryMetodo.getValue().invoke(reenvio).toString()+"|");
	    	            	}
	                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
	                        logger.debug("Se presentó un problema al obtener el valor del método get de la propiedad " + entryMetodo.getKey(), e);
	                    }
	                }
	                
	
	                if(administraBitacoraEstadoReenvio(strBuilder.toString())) {
	
	                	lstReenvios.add(mapReenvio);
	                	
	                }
	            }
	
	            mensajeXml = xstream.toXML(lstReenvios);
	            logger.debug("####### MensajeXML=[" + mensajeXml + "]");
	
	            if (!lstReenvios.isEmpty()) {
	                this.encolaMensajeReenvio(flujo.getOrigen(), mensajeXml);
	                
	    	        logger.debug("####### Mensaje a Bitacorar=[" + mensajeXml + "]");
	    	        bitacoraReenvio.setXml(mensajeXml);
	    	        this.bitacoraReenvioDAO.save(bitacoraReenvio);
	            }
	        }
		}catch(Exception e) {
			throw new BusinessException(e.getMessage());
		}
	}
	
	private void encolaMensajeReenvio(String origen, final String mensajeXml) {
		String mensajeEnviar = mensajeXml.replace(" ", "").replace(System.lineSeparator(), "");
		
		try {

		    if(validateSignatureService.isSignature()) {
		        mensajeEnviar = signMensaje.signMensaje(mensajeEnviar);
		        logger.info(Constante.loggerSignature(false, Constante.PORTAL_DALI, origen.equals(DESC_MODULO_ORIGEN_MAV_PFI)?"MAV":origen, mensajeEnviar));
		    }

		    final String mensajeFirmado = mensajeEnviar;

		    logger.debug("####### flujo origen=[" + origen + "]");
		    switch(origen) {
		    case DESC_MODULO_ORIGEN_AE:
		        this.jmsTemplateReenvioAE.send(new MessageCreator() {
		            @Override
		            public Message createMessage(Session session) throws JMSException {
		                TextMessage message = session.createTextMessage();
		                message.setText(mensajeFirmado);
		                return message;
		            }
		        });
		        break;

		    case DESC_MODULO_ORIGEN_MOV:
		        this.jmsTemplateReenvioMov.send(new MessageCreator() {
		            @Override
		            public Message createMessage(Session session) throws JMSException {
		                TextMessage message = session.createTextMessage();
		                message.setText(mensajeFirmado);
		                return message;
		            }
		        });
		        break;

		    case DESC_MODULO_ORIGEN_MOS:
		        this.jmsTemplateReenvioMOS.send(new MessageCreator() {
		            @Override
		            public Message createMessage(Session session) throws JMSException {
		                TextMessage message = session.createTextMessage();
		                message.setText(mensajeFirmado);
		                return message;
		            }
		        });
		        break;

		    case DESC_MODULO_ORIGEN_MAV:
		        this.jmsTemplateReenvioMAV.send(new MessageCreator() {
		            @Override
		            public Message createMessage(Session session) throws JMSException {
		                TextMessage message = session.createTextMessage();
		                message.setText(mensajeFirmado);
		                return message;
		            }
		        });
		        break;

		    case DESC_MODULO_ORIGEN_MAV_PFI:
		        this.jmsTemplateReenvioADAPTERMAVPFI.send(new MessageCreator() {
		            @Override
		            public Message createMessage(Session session) throws JMSException {
		                TextMessage message = session.createTextMessage();
		                message.setText(mensajeFirmado);
		                return message;
		            }
		        });
		        break;

		    case DESC_MODULO_ORIGEN_SLV:
		        this.jmsTemplateReenvioSLV.send(new MessageCreator() {
		            @Override
		            public Message createMessage(Session session) throws JMSException {
		                TextMessage message = session.createTextMessage();
		                message.setText(mensajeFirmado);
		                return message;
		            }
		        });
		        break;

		    case DESC_MODULO_ORIGEN_MATCH:
		        this.jmsTemplateReenvioMATCH.send(new MessageCreator() {
		            @Override
		            public Message createMessage(Session session) throws JMSException {
		                TextMessage message = session.createTextMessage();
		                message.setText(mensajeFirmado);
		                return message;
		            }
		        });
		        break;
		    }
		    
			this.bitacoraReenvioDAO.save(lstBitacoraEstadoReenvio);
			lstBitacoraEstadoReenvio.clear();
		}catch(DigitalSignException e) {
		    e.getCause().printStackTrace();
		    logger.error(e.getCause().getMessage(), e.getCause());
        	String[] mensajeError = e.getMessage().split("\\|");
            ErrorSeguridadMensajeriaVo error = new ErrorSeguridadMensajeriaVo();
            error.setError(mensajeError[0]);
            error.setModulo(Constante.MOV);
            error.setXml(mensajeEnviar);
            Document doc = XMLUtils.convertStringToDocument(mensajeEnviar);
            error.setNumeroSerieCertificado(mensajeError.length == 2?mensajeError[1]:"");
            error.setTipoMensaje(XMLUtils.getStringToElement(doc, Constante.TIPO_MENSAJE));
            
            sendMessageServiceSign.sendQueueAlert(error);
		}
	}
	
	private boolean administraBitacoraEstadoReenvio(String folioOperacion) {
        BitacoraEstadoReenvio estadoReenvio = null;
        boolean enviar = true;
        
        estadoReenvio = this.bitacoraReenvioDAO.getEstadoReenvioByPk(folioOperacion);
        
        if(estadoReenvio != null) {
        	if(!estadoReenvio.getProcesado()) {
        		this.bitacoraReenvioDAO.updateEstadoReenvio(folioOperacion, true);
        		return enviar;
        	}else {
        		return !enviar;
        	}
        }else {
	        
	        estadoReenvio = new BitacoraEstadoReenvio();
	        estadoReenvio.setFolioOperacion(folioOperacion);
	        estadoReenvio.setFechaReenvio(new Date());
	        estadoReenvio.setProcesado(true);
	        lstBitacoraEstadoReenvio.add(estadoReenvio);
	        
	        return enviar;
        }
		
	}
	
	private Map<String, Method> getMetodosCampos(List<CampoConciliacionModulosEnum> camposXMLFlujo) {
		Map<String, Method> mapMetodos = new HashMap<>();
		
		for(CampoConciliacionModulosEnum campoXML : camposXMLFlujo) {
			try {
				mapMetodos.put(campoXML.getPropiedadDto(), ConciliacionModulosDTO.class.getDeclaredMethod(obtenMetodoGetDto(campoXML.getPropiedadDto())));
			} catch (NoSuchMethodException | SecurityException e) {
				logger.error("Se presentó un problema al obtener, de la clase ConciliacionModulosDTO, el método get de la propiedad " + campoXML.getPropiedadDto(), e);
			}
		}
		
		return mapMetodos;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map<FlujoConciliacionModulosEnum, List<ConciliacionModulosDTO>> obtenConciliacionesModulos(CriteriosConciliacionModulosDTO criterios) {
		Map<FlujoConciliacionModulosEnum, List<ConciliacionModulosDTO>> resultados = new TreeMap<>(comparatorConciliacion);
		Method metodoDAO;
		Class[] argumentosMetodoDAO = new Class[] {CriteriosConciliacionModulosDTO.class};
		
		for(FlujoConciliacionModulosEnum config : FlujoConciliacionModulosEnum.values()) {
			try {
				metodoDAO = ConciliacionModulosDAO.class.getDeclaredMethod(config.getMetodoDAO(), argumentosMetodoDAO);
				resultados.put(config, (List<ConciliacionModulosDTO>) metodoDAO.invoke(this.conciliacionModulosDAO, criterios));
			} catch (InvocationTargetException | IllegalArgumentException | IllegalAccessException | NoSuchMethodException | SecurityException e) {
				logger.error("Error al obtener el metodo "+ config.getMetodoDAO() +" del DAO de Conciliacion de Modulos Indeval", e);
			} 
		}
		
		return resultados;
	}
	
	private void llenaHojaReporteConciliaciones(Sheet hoja, List<CampoConciliacionModulosEnum> columnas, List<ConciliacionModulosDTO> registros, 
			MultiKeyMap estilos, CriteriosConciliacionModulosDTO criterios) {
		Row fila;
		Cell celda;
		Method metodoGet;
		Object valorCelda = null;
		int contadorColumna;
		int contadorFilas;
		
		contadorFilas = this.agregaCabeceraYCriterios(hoja, estilos, criterios, registros.size());		
		contadorFilas = this.configuraColumnasHoja(hoja, (CellStyle)estilos.get(EstiloDatosExcel.CABECERA_TABLA, null), contadorFilas, columnas);
		
		for(ConciliacionModulosDTO registro : registros) {
			fila = hoja.createRow(contadorFilas++);
			contadorColumna = 0;
			
			for(CampoConciliacionModulosEnum columna : columnas) {
				celda = fila.createCell(contadorColumna++);
				celda.setCellStyle((CellStyle)estilos.get(columna.getEstiloExcel(), (contadorFilas % 2) != 0));
				
				try {
					metodoGet = registro.getClass().getMethod(obtenMetodoGetDto(columna.getPropiedadDto()));
					valorCelda = metodoGet.invoke(registro);
					
					if(valorCelda instanceof String) {
						celda.setCellValue(valorCelda.toString());
					} else if(valorCelda instanceof Date) {
						celda.setCellValue((Date) valorCelda);
					} else if(valorCelda instanceof Number) {
						celda.setCellValue(((Number) valorCelda).doubleValue());
					}
					
				} catch(Exception e) {
					logger.debug("Ocurrió un problema al asignar en el reporte el valor en la columna "+ columna.getDescripcion() +", para la fila "+ (contadorFilas-1) +
							". Valor: " + valorCelda == null ? "null" : valorCelda.toString(), e);
				}
			}
		}
	}
	
	private int agregaCabeceraYCriterios(Sheet hoja, MultiKeyMap estilos, CriteriosConciliacionModulosDTO criterios, int totalRegistros) {
		int contFilas = 0;
		int contColumnasCriterios = 0;
		
		this.generaFilaTituloSubtitulo(hoja, (CellStyle) estilos.get(EstiloDatosExcel.TITULO, null), contFilas++, "Conciliaci\u00f3n de Mensajes entre M\u00f3dulos Indeval");
		contFilas++;
		
		this.generaFilaCabecera(hoja, estilos, contFilas++, "Instituci\u00f3n:",
				criterios.getInstitucionActual().getClaveTipoInstitucion() + " "
						+ criterios.getInstitucionActual().getFolioInstitucion() + " "
						+ criterios.getInstitucionActual().getRazonSocial());
		this.generaFilaCabecera(hoja, estilos, contFilas++, "Fecha:", DATE_FORMAT_LARGO.format(new Date()));
		contFilas++;
		
		this.generaFilaTituloSubtitulo(hoja, (CellStyle) estilos.get(EstiloDatosExcel.SUBTITULO, null), contFilas++, "Criterios de B\u00fasqueda");
		contFilas++;
		
		Row filaDescCriterios = hoja.createRow(contFilas++);
		Row filaValCriterios = hoja.createRow(contFilas++);
		this.generaColumnaCriterio(hoja, filaDescCriterios, filaValCriterios, estilos, contColumnasCriterios++,
				"Participante", StringUtils.defaultIfEmpty(criterios.getIdFolioParticipante(), "TODOS"));
		this.generaColumnaCriterio(hoja, filaDescCriterios, filaValCriterios, estilos, contColumnasCriterios++,
				"Cuenta Participante", StringUtils.defaultIfEmpty(criterios.getCuentaParticipante(), "TODAS"));
		this.generaColumnaCriterio(hoja, filaDescCriterios, filaValCriterios, estilos, contColumnasCriterios++, 
				"Contraparte", StringUtils.defaultIfEmpty(criterios.getIdFolioContraparte(), "TODOS"));
		this.generaColumnaCriterio(hoja, filaDescCriterios, filaValCriterios, estilos, contColumnasCriterios++,
				"Cuenta Contraparte", StringUtils.defaultIfEmpty(criterios.getCuentaContraparte(), "TODAS"));
		this.generaColumnaCriterio(hoja, filaDescCriterios, filaValCriterios, estilos, contColumnasCriterios++, 
				"T.V.", StringUtils.defaultIfEmpty(criterios.getTipoValor(), "TODOS"));
		this.generaColumnaCriterio(hoja, filaDescCriterios, filaValCriterios, estilos, contColumnasCriterios++, 
				"Emisora", StringUtils.defaultIfEmpty(criterios.getEmisora(), "TODAS"));
		this.generaColumnaCriterio(hoja, filaDescCriterios, filaValCriterios, estilos, contColumnasCriterios++, 
				"Serie", StringUtils.defaultIfEmpty(criterios.getSerie(), "TODAS"));
		this.generaColumnaCriterio(hoja, filaDescCriterios, filaValCriterios, estilos, contColumnasCriterios++,
				"Tipo Instrucci\u00f3n", criterios.getDescTipoInstruccion());
//		this.generaColumnaCriterio(hoja, filaDescCriterios, filaValCriterios, estilos, contColumnasCriterios++,
//				"Tipo Operaci\u00f3n", criterios.getDescTipoOperacion());
		contFilas++;
		
		Row filaDescTotalReg = hoja.createRow(contFilas++);
		Row filaValTotalReg = hoja.createRow(contFilas++);
		this.generaColumnaCriterio(hoja, filaDescTotalReg, filaValTotalReg, estilos, 0, "Registros Encontrados", totalRegistros);
		
		return ++contFilas;
	}
	
	private void generaFilaTituloSubtitulo(Sheet hoja, CellStyle estilo, int contFila, String texto) {
		Row fila = hoja.createRow(contFila);
		Cell celda;
		
		celda = fila.createCell(0);
		celda.setCellStyle(estilo);
		celda.setCellValue(texto);
	}
	
	private void generaFilaCabecera(Sheet hoja, MultiKeyMap estilos, int contFila, String descCabecera, String valorCabecera) {
		Row fila = hoja.createRow(contFila);
		Cell celda;
		
		celda = fila.createCell(0);
		celda.setCellStyle((CellStyle)estilos.get(EstiloDatosExcel.ETIQUETA_CAB, null));
		celda.setCellValue(descCabecera);
		
		celda = fila.createCell(1);
		celda.setCellStyle((CellStyle)estilos.get(EstiloDatosExcel.VALOR_CAB, null));
		celda.setCellValue(valorCabecera);		
	}
	
	private void generaColumnaCriterio(Sheet hoja, Row filaDescCriterios, Row filaValCriterios, MultiKeyMap estilos, int contCol, 
			String descCriterio, Object valorCriterio) {
		Cell celda;
		
		hoja.setColumnWidth(contCol, ANCHO_COLUMNAS_EXCEL);
		
		celda = filaDescCriterios.createCell(contCol);
		celda.setCellStyle((CellStyle)estilos.get(EstiloDatosExcel.CABECERA_TABLA, null));
		celda.setCellValue(descCriterio);
		
		celda = filaValCriterios.createCell(contCol);
		if(valorCriterio instanceof String) {
			celda.setCellStyle((CellStyle)estilos.get(EstiloDatosExcel.TEXTO, true));
			celda.setCellValue(valorCriterio.toString());
		} else if(valorCriterio instanceof Number) {
			celda.setCellStyle((CellStyle)estilos.get(EstiloDatosExcel.NUMERICO, true));
			celda.setCellValue(((Number) valorCriterio).doubleValue());
		}	
	}
	
	private String obtenMetodoGetDto(String nombrePropiedad) {
		return "get" + nombrePropiedad.substring(0, 1).toUpperCase() + nombrePropiedad.substring(1);
	}
	
	private int configuraColumnasHoja(Sheet hoja, CellStyle estilo, int contFila, List<CampoConciliacionModulosEnum> columnas) {
		Row cabecera = hoja.createRow(contFila++);
		Cell celda;
		int contador = 0;
		
		for(CampoConciliacionModulosEnum columna : columnas) {
			hoja.setColumnWidth(contador, ANCHO_COLUMNAS_EXCEL);
			celda = cabecera.createCell(contador++);
			celda.setCellValue(columna.getDescripcion());
			celda.setCellStyle(estilo);
		}
		
		return contFila;
	}

	private MultiKeyMap generaMapaEstilos(Workbook libro) {
		MultiKeyMap estilos = new MultiKeyMap();
		
		estilos.put(EstiloDatosExcel.TITULO, null, this.generaEstiloTitulo(libro));
		estilos.put(EstiloDatosExcel.SUBTITULO, null, this.generaEstiloSubTitulo(libro));
		estilos.put(EstiloDatosExcel.ETIQUETA_CAB, null, this.generaEstiloEtiquetaCab(libro));
		estilos.put(EstiloDatosExcel.VALOR_CAB, null, this.generaEstiloValorCab(libro));
		estilos.put(EstiloDatosExcel.TITULO, null, this.generaEstiloTitulo(libro));
		estilos.put(EstiloDatosExcel.CABECERA_TABLA, null, this.generaEstiloCabecera(libro));
		estilos.put(EstiloDatosExcel.TEXTO, true, this.generaEstiloTexto(libro, true));
		estilos.put(EstiloDatosExcel.NUMERICO, true, this.generaEstiloNumerico(libro, true));
		estilos.put(EstiloDatosExcel.MONEDA, true, this.generaEstiloMoneda(libro, true));
		estilos.put(EstiloDatosExcel.FECHA, true, this.generaEstiloFecha(libro, true));
		estilos.put(EstiloDatosExcel.FECHA_HORA, true, this.generaEstiloFechaHora(libro, true));
		estilos.put(EstiloDatosExcel.TEXTO, false, this.generaEstiloTexto(libro, false));
		estilos.put(EstiloDatosExcel.NUMERICO, false, this.generaEstiloNumerico(libro, false));
		estilos.put(EstiloDatosExcel.MONEDA, false, this.generaEstiloMoneda(libro, false));
		estilos.put(EstiloDatosExcel.FECHA, false, this.generaEstiloFecha(libro, false));
		estilos.put(EstiloDatosExcel.FECHA_HORA, false, this.generaEstiloFechaHora(libro, false));
		
		this.configuraFuenteEstilo(libro, (CellStyle)estilos.get(EstiloDatosExcel.TEXTO, true), false, 9, HSSFColor.BLACK.index, false, false);
		
		return estilos;
	}
	
	private CellStyle generaEstiloTitulo(Workbook libro) {
		CellStyle estilo = libro.createCellStyle();
		
		this.configuraFuenteEstilo(libro, estilo, true, 16, HSSFColor.BLACK.index, true, false);
		estilo.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		
		return estilo;
	}
	
	private CellStyle generaEstiloSubTitulo(Workbook libro) {
		CellStyle estilo = libro.createCellStyle();
		
		this.configuraFuenteEstilo(libro, estilo, true, 12, HSSFColor.BLACK.index, true, false);
		estilo.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		
		return estilo;
	}
	
	private CellStyle generaEstiloEtiquetaCab(Workbook libro) {
		CellStyle estilo = libro.createCellStyle();
		
		this.configuraFuenteEstilo(libro, estilo, true, 10, HSSFColor.BLACK.index, true, false);
		estilo.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		
		return estilo;
	}
	
	private CellStyle generaEstiloValorCab(Workbook libro) {
		CellStyle estilo = libro.createCellStyle();
		
		this.configuraFuenteEstilo(libro, estilo, true, 10, HSSFColor.BLACK.index, false, true);
		estilo.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		
		return estilo;
	}
	
	private CellStyle generaEstiloCabecera(Workbook libro) {
		CellStyle estilo = libro.createCellStyle();
		
		this.asignaColorFondo(estilo, HSSFColor.BLUE_GREY.index);
		this.configuraFuenteEstilo(libro, estilo, true, 9, HSSFColor.WHITE.index, true, false);
		estilo.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		return estilo;
	}
	
	private CellStyle generaEstiloTexto(Workbook libro, boolean filasPar) {
		CellStyle estiloTexto = libro.createCellStyle();
		
		this.asignaEstilosComunes(estiloTexto, filasPar);
		estiloTexto.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		
		return estiloTexto;
	}
	
	private CellStyle generaEstiloNumerico(Workbook libro, boolean filasPar) {
		CellStyle estiloNumerico = libro.createCellStyle();
		
		this.asignaEstilosComunes(estiloNumerico, filasPar);
		estiloNumerico.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		estiloNumerico.setDataFormat(libro.getCreationHelper().createDataFormat().getFormat("#,##0;-#,##0"));
		
		return estiloNumerico;
	}
	
	private CellStyle generaEstiloMoneda(Workbook libro, boolean filasPar) {
		CellStyle estiloNumerico = libro.createCellStyle();
		
		this.asignaEstilosComunes(estiloNumerico, filasPar);
		estiloNumerico.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		estiloNumerico.setDataFormat(libro.getCreationHelper().createDataFormat().getFormat("$#,##0.00;-$#,##0.00"));
		
		return estiloNumerico;
	}
	
	private CellStyle generaEstiloFecha(Workbook libro, boolean filasPar) {
		CellStyle estiloFecha = libro.createCellStyle();
		
		this.asignaEstilosComunes(estiloFecha, filasPar);
		estiloFecha.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		estiloFecha.setDataFormat(libro.getCreationHelper().createDataFormat().getFormat("dd/mm/yyyy"));
		
		return estiloFecha;
	}
	
	private CellStyle generaEstiloFechaHora(Workbook libro, boolean filasPar) {
		CellStyle estiloFechaHora = libro.createCellStyle();
		
		this.asignaEstilosComunes(estiloFechaHora, filasPar);
		estiloFechaHora.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		estiloFechaHora.setDataFormat(libro.getCreationHelper().createDataFormat().getFormat("dd/mm/yyyy hh:mm:ss"));
		
		return estiloFechaHora;
	}
	
	private void asignaEstilosComunes(CellStyle estilo, boolean filasPar) {
		this.asignaColorFondo(estilo, filasPar ? HSSFColor.WHITE.index : HSSFColor.GREY_25_PERCENT.index);
	}
	
	private void asignaColorFondo(CellStyle estilo, short indexColor) {
		estilo.setFillForegroundColor(indexColor);
		estilo.setFillBackgroundColor(indexColor);
		estilo.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	}
	
	private void configuraFuenteEstilo(Workbook libro, CellStyle estilo, boolean fuenteNueva, int tamanio, short indexColor, boolean bold, boolean italic) {
		if(fuenteNueva) {
			estilo.setFont(libro.createFont());
		}
		libro.getFontAt(estilo.getFontIndex()).setFontHeightInPoints((short) tamanio);
		libro.getFontAt(estilo.getFontIndex()).setColor(indexColor);
		libro.getFontAt(estilo.getFontIndex()).setBoldweight(bold ? HSSFFont.BOLDWEIGHT_BOLD : HSSFFont.BOLDWEIGHT_NORMAL);
		libro.getFontAt(estilo.getFontIndex()).setItalic(italic);
		libro.getFontAt(estilo.getFontIndex()).setFontName(HSSFFont.FONT_ARIAL);
	}
	
	public ConciliacionModulosDAO getConciliacionModulosDAO() {
		return conciliacionModulosDAO;
	}

	public void setConciliacionModulosDAO(ConciliacionModulosDAO conciliacionModulosDAO) {
		this.conciliacionModulosDAO = conciliacionModulosDAO;
	}

	public BitacoraReenvioDAO getBitacoraReenvioDAO() {
		return bitacoraReenvioDAO;
	}

	public void setBitacoraReenvioDAO(BitacoraReenvioDAO bitacoraReenvioDAO) {
		this.bitacoraReenvioDAO = bitacoraReenvioDAO;
	}

	public JmsTemplate getJmsTemplateReenvioMov() {
		return jmsTemplateReenvioMov;
	}

	public void setJmsTemplateReenvioMov(JmsTemplate jmsTemplateReenvioMov) {
		this.jmsTemplateReenvioMov = jmsTemplateReenvioMov;
	}

	public void setValidateSignatureService(ValidateSignatureService validateSignatureService) {
		this.validateSignatureService = validateSignatureService;
	}

	public void setSignMensaje(SignMensaje signMensaje) {
		this.signMensaje = signMensaje;
	}

	public void setSendMessageServiceSign(SendMessageService sendMessageServiceSign) {
		this.sendMessageServiceSign = sendMessageServiceSign;
	}

    public void setJmsTemplateReenvioMAV(JmsTemplate jmsTemplateReenvioMAV) {
        this.jmsTemplateReenvioMAV = jmsTemplateReenvioMAV;
    }

	public void setJmsTemplateReenvioMOS(JmsTemplate jmsTemplateReenvioMOS) {
		this.jmsTemplateReenvioMOS = jmsTemplateReenvioMOS;
	}
	
	public void setJmsTemplateReenvioSLV(JmsTemplate jmsTemplateReenvioSLV) {
		this.jmsTemplateReenvioSLV = jmsTemplateReenvioSLV;
	}

    public void setJmsTemplateReenvioADAPTERMAVPFI(JmsTemplate jmsTemplateReenvioADAPTERMAVPFI) {
        this.jmsTemplateReenvioADAPTERMAVPFI = jmsTemplateReenvioADAPTERMAVPFI;
    }

	public void setJmsTemplateReenvioAE(JmsTemplate jmsTemplateReenvioAE) {
		this.jmsTemplateReenvioAE = jmsTemplateReenvioAE;
	}

	public void setJmsTemplateReenvioMATCH(JmsTemplate jmsTemplateReenvioMATCH) {
		this.jmsTemplateReenvioMATCH = jmsTemplateReenvioMATCH;
	}

	@Override
	public String reenviaRete(List<InstruccionEfectivoDTO> retes, String idUsuario, String ip) {
		List<ReenviaReteDTO> reenvios = new ArrayList<ReenviaReteDTO>();
		for (InstruccionEfectivoDTO dto : retes) {
			ReenviaReteDTO reenvio = new ReenviaReteDTO();
			reenvio.setFechaLiquidacion(dto.getFechaLiquidacion());
			reenvio.setFolioInstruccion(dto.getFolioInstruccion().toString());
			reenvio.setTipoInstruccion(dto.getTipoInstruccion().getIdTipoInstruccion().intValue());
			reenvio.setTipoRetiro(dto.getTipoRetiro());			
			reenvios.add(reenvio);
		}

		StringBuilder textoRespuesta = new StringBuilder();
		StringBuilder cadenaErrores = new StringBuilder();
		List<BitacoraReenvioConfLiqReteDTO> bitacora = new ArrayList<BitacoraReenvioConfLiqReteDTO>();
		List<RespuestaReenviaReteDTO> resultados;
		try {
			int errorCont = 0;
			int okCont = 0;
			resultados = ejbSpeiOperaciones.reenviaRete(reenvios);

			for (RespuestaReenviaReteDTO respuestaDTO : resultados) {
				// Generar dto para bitacora
				BitacoraReenvioConfLiqReteDTO bitacoraDto = this.generaBitacoraReenvioConfLiqRete(respuestaDTO, idUsuario, ip);
				bitacora.add(bitacoraDto);
				// Generar resultado para pantalla
				if (respuestaDTO.getResultado().equalsIgnoreCase(RESULTADO_FAIL)) {
					errorCont ++;
					cadenaErrores.append(errorCont == 1 
							? respuestaDTO.getFolioInstruccion()
							: ", " + respuestaDTO.getFolioInstruccion());
				} else {
					okCont ++;
				}
			}

			// Texto de respuesta
			textoRespuesta.append(
					"Se solicitaron [" + reenvios.size() + "] reenvios de confirmaciones: "
					+ "(" + okCont +") correctas y "
					+ "(" + errorCont + ") con error"
					+ (cadenaErrores.length() > 0 ? ": " + cadenaErrores.toString() : "")
					);
			
			cadenaResultado = textoRespuesta.toString();
			// Guardar bitacora
			bitacoraReenvioConfLiqReteService.saveBitacora(bitacora);
		}
		catch (SpeiFacadeException e) {
			cadenaResultado = "Ocurrio un error en el proceso de reenvio, favor de verificar";
			logger.error("Ocurrio un error en el proceso de reenvio de confirmaciones de liquidacion RETES ... ");
			e.printStackTrace();
		}

		return cadenaResultado;
	}

	private BitacoraReenvioConfLiqReteDTO generaBitacoraReenvioConfLiqRete(RespuestaReenviaReteDTO respuestaDTO, String idUsuario, String ip) {
		BitacoraReenvioConfLiqReteDTO bitacoraDTO = new BitacoraReenvioConfLiqReteDTO();
		// Localiza instruccion asociada a la respuesta
		InstruccionEfectivoSimple instEfectivo = 
				consultaInstruccionesEfectivoDAO.getByFolioInstruccion(new BigInteger(respuestaDTO.getFolioInstruccion()));

		// Genera DTO de bitacora
		bitacoraDTO.setBoveda(instEfectivo.getBoveda());
		bitacoraDTO.setCasfimInstitucionReceptora(instEfectivo.getCasfimInstitucionReceptora());
		bitacoraDTO.setCasfimInstitucionTraspasante(instEfectivo.getCasfimInstitucionTraspasante());
		bitacoraDTO.setClabe(instEfectivo.getClabe());
		bitacoraDTO.setClaveRastreo(instEfectivo.getClaveRastreo());
		bitacoraDTO.setConcepto(instEfectivo.getConcepto());
		bitacoraDTO.setCuentaReceptora(instEfectivo.getCuentaReceptora());
		bitacoraDTO.setCuentaTraspasante(instEfectivo.getCuentaTraspasante());
		bitacoraDTO.setDescErrorReenvio(respuestaDTO.getDetalle());
		bitacoraDTO.setDescripcionEstado(instEfectivo.getDescripcionEstado());
		bitacoraDTO.setDivisa(instEfectivo.getDivisa());
		bitacoraDTO.setEstadoInstruccion(instEfectivo.getEstadoInstruccion());
		bitacoraDTO.setFechaLiquidacion(instEfectivo.getFechaLiquidacion());
		bitacoraDTO.setFechaReenvio(new Date());
		bitacoraDTO.setFechaRegistro(instEfectivo.getFechaRegistro());
		bitacoraDTO.setFolioInstLiquidacion(instEfectivo.getFolioInstLiquidacion());
		bitacoraDTO.setFolioInstruccion(instEfectivo.getFolioInstruccion());
		bitacoraDTO.setFolioOrigen(instEfectivo.getFolioOrigen());
		bitacoraDTO.setIdInstEfec(instEfectivo.getId());
		bitacoraDTO.setIdUsuario(idUsuario);
		bitacoraDTO.setInstitucionOrigen(instEfectivo.getInstitucionOrigen());
		bitacoraDTO.setInstitucionReceptora(
				instEfectivo.getInstitucionReceptora() == null ? null :
				instEfectivo.getInstitucionReceptora().intValue());
		bitacoraDTO.setInstitucionTraspasante(
				instEfectivo.getInstitucionTraspasante() == null ? null :
				instEfectivo.getInstitucionTraspasante().intValue());
		bitacoraDTO.setIp(ip);
		bitacoraDTO.setLiqSpei(instEfectivo.getLiqSpei());
		bitacoraDTO.setMonto(instEfectivo.getMonto());
		bitacoraDTO.setReferenciaNumerica(instEfectivo.getReferenciaNumerica());
		bitacoraDTO.setReferenciaOperacion(instEfectivo.getReferenciaOperacion());
		bitacoraDTO.setReferenciaParticipante(instEfectivo.getReferenciaParticipante());
		bitacoraDTO.setResultadoReenvio(respuestaDTO.getResultado());
		bitacoraDTO.setTipoInstruccion(instEfectivo.getTipoInstruccion());
		bitacoraDTO.setTipoMensaje(instEfectivo.getTipoMensaje());
		bitacoraDTO.setTipoRetiro(instEfectivo.getTipoRetiro());

		return bitacoraDTO;
	}

	public String getCadenaResultado() {
		return cadenaResultado;
	}

    public void setEjbSpeiOperaciones(Operaciones ejbSpeiOperaciones) {
		this.ejbSpeiOperaciones = ejbSpeiOperaciones;
	}

	public void setBitacoraReenvioConfLiqReteService(BitacoraReenvioConfLiqReteService bitacoraReenvioConfLiqReteService) {
		this.bitacoraReenvioConfLiqReteService = bitacoraReenvioConfLiqReteService;
	}

	public void setConsultaInstruccionesEfectivoDAO(ConsultaInstruccionesEfectivoDAO consultaInstruccionesEfectivoDAO) {
		this.consultaInstruccionesEfectivoDAO = consultaInstruccionesEfectivoDAO;
	}

}

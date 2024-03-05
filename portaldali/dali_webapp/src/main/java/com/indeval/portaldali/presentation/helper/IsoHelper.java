/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * IsoHelper.java
 * May 2, 2008
 */
package com.indeval.portaldali.presentation.helper;

import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.indeval.portaldali.middleware.dto.DepositoDivisaDTO;
import com.indeval.portaldali.middleware.dto.RetiroEfectivoDTO;
import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.middleware.services.enviooperaciones.util.Constantes;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.presentation.dto.tesoreria.DepositoDivisaVO;
import com.indeval.protocolofinanciero.api.ProtocoloFinancieroException;
import com.indeval.protocolofinanciero.api.service.Efectivo;
import com.indeval.protocolofinanciero.api.service.TraspasoContraPago;
import com.indeval.protocolofinanciero.api.service.TraspasoLibrePago;
import com.indeval.protocolofinanciero.api.vo.RetiroEfectivoVO;
import com.indeval.protocolofinanciero.api.vo.TraspasoContraPagoVO;
import com.indeval.protocolofinanciero.api.vo.TraspasoEfectivoVO;
import com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Ayudante para la generación de los mensajes con formato ISO que serán
 * firmados en la captura de operaciones.
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 * 
 */
public class IsoHelper {
	
	public static final String DEPOSITO_DIVISA_VO = "com.indeval.dispatcher.service.util.DepositoDivisaVO";	
	public static final String MENSAJE_DIVISA = "depositoEfectivo";
	private static final Integer TIPO_MSJ = Integer.valueOf(100);

	/** Servicio de negocio para la generación de las cadenas ISO a firmar */
	private TraspasoContraPago dvpService;

	/** Bean para acceso al servicio de traspasos libres de pago */
	private TraspasoLibrePago tlpService;

	/** Bean para acceso al servicio de operaciones de efectivo */
	private Efectivo efectivo;
	
	/**
	 * Bean para acceso al servicio de call money 
	 */
//	private TraspasoEfectivoFV callMoney;

	/** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(IsoHelper.class);

	/**
	 * Obtiene el campo tlpService
	 * 
	 * @return tlpService
	 */
	public TraspasoLibrePago getTlpService() {
		return tlpService;
	}

	/**
	 * Asigna el campo tlpService
	 * 
	 * @param tlpService
	 *            el valor de tlpService a asignar
	 */
	public void setTlpService(TraspasoLibrePago tlpService) {
		this.tlpService = tlpService;
	}

	/**
	 * Obtiene el campo efectivo
	 * 
	 * @return efectivo
	 */
	public Efectivo getEfectivo() {
		return efectivo;
	}

	/**
	 * Asigna el campo efectivo
	 * 
	 * @param efectivo
	 *            el valor de efectivo a asignar
	 */
	public void setEfectivo(Efectivo efectivo) {
		this.efectivo = efectivo;
	}

	/**
	 * Obtiene el campo dvpService
	 * 
	 * @return dvpService
	 */
	public TraspasoContraPago getDvpService() {
		return dvpService;
	}

	/**
	 * Asigna el campo dvpService
	 * 
	 * @param dvpService
	 *            el valor de dvpService a asignar
	 */
	public void setDvpService(TraspasoContraPago dvpService) {
		this.dvpService = dvpService;
	}

	/**
	 * @see com.indeval.portallegado.middleware.services.enviooperaciones.EnvioOperacionesService#creaISO(java.lang.Object,
	 *      boolean)
	 */
	public String creaISO(Object vo, boolean isCompra) throws BusinessException {
		log.info("IsoHelper :: creaISO");
		String iso = "";
		try {
			if (vo instanceof TraspasoContraPagoVO) {
				log.debug("IsoHelper :: creaISO :: TraspasoContraPagoVO");
				iso = creaIsoDvp(vo, isCompra);
			} else if (vo instanceof TraspasoLibrePagoVO) {
				log.debug("IsoHelper :: creaISO :: TraspasoLibrePagoVO");
				iso = creaIsoTlp(vo, isCompra);
			} else if (vo instanceof RetiroEfectivoVO) {
				log.debug("IsoHelper :: creaISO :: RetiroEfectivoVO");
				iso = creaIsoRetiroEfectivo(vo);
			} else if (vo instanceof TraspasoEfectivoVO) {
				log.debug("IsoHelper :: creaISO :: TraspasoEfectivoVO");
				iso = creaIsoTraspasoEfectivo(vo);
            } else if (vo instanceof RetiroEfectivoDTO ) {
            	log.debug("IsoHelper :: creaISO :: RetiroEfectivoDTO");
                iso = creaIsoRetiroEfectivo103(vo);
            } else if (vo instanceof DepositoDivisaDTO ) {
            	log.debug("IsoHelper :: creaISO :: DepositoDivisaDTO");
                iso = generaDepositoDivisasVo((DepositoDivisaDTO) vo);
			} 			
            if (iso != null) {
                iso = iso + "\n";
                iso = iso.replace("\r\n", "\n");
            }
			log.debug("isoSinFirmar: '" + iso + "'");
			
			return iso;
		} catch (ProtocoloFinancieroException e) {
			throw new BusinessException(e.getMessage());
		}
	}

	
//	private String creaIsoCallMoney(Object vo) throws ProtocoloFinancieroException {		
//		return callMoney.traspasoEfectivoFV((TraspasoEfectivoFvVO)vo);
//	}
	

	/**
	 * Crea el iso correspondiente a traspasos contrapago
	 * 
	 * @param vo
	 * @param isCompra
	 * @return String
	 * @throws ProtocoloFinancieroException
	 * @throws BusinessException
	 */
	private String creaIsoDvp(Object vo, boolean isCompra) throws ProtocoloFinancieroException, BusinessException {
		log.debug("IsoHelper :: Entrando a creaIsoDvp");
		String iso = null;
		TraspasoContraPagoVO dvpVo = (TraspasoContraPagoVO) vo;
		dvpVo.setCupon(dvpVo.getCupon().trim());
		dvpVo.setEmisora(dvpVo.getEmisora().trim());
		dvpVo.setIdFolioCtaReceptora(dvpVo.getIdFolioCtaReceptora().trim());
		dvpVo.setIdFolioCtaTraspasante(dvpVo.getIdFolioCtaTraspasante().trim());
		dvpVo.setReferenciaMensaje(dvpVo.getReferenciaMensaje().trim());
		dvpVo.setSerie(dvpVo.getSerie().trim());
		dvpVo.setTipoInstruccion(dvpVo.getTipoInstruccion().trim());
		dvpVo.setTipoValor(dvpVo.getTipoValor().trim());
		dvpVo.setReferenciaOperacion(dvpVo.getReferenciaOperacion().trim());
		dvpVo.setISIN(dvpVo.getISIN() != null ? dvpVo.getISIN().trim() : null); 
		dvpVo.setBoveda((null != dvpVo.getBoveda() ? dvpVo.getBoveda().trim():null));
		dvpVo.setBovedaEfectivo(null != dvpVo.getBovedaEfectivo() ? dvpVo.getBovedaEfectivo().trim() : null);
		dvpVo.setDivisa(null !=dvpVo.getDivisa()?  dvpVo.getDivisa().trim():null);
		
		if (dvpVo.getTipoInstruccion().equalsIgnoreCase("V")) {
			dvpVo.setFechaVencimiento(null);
			dvpVo.setTasaNegociada(null);
			dvpVo.setTasaFija(null);
		}
/*		
		try {
			log.debug("IsoHelper :: (TraspasoContraPagoVO)");
			getAttributesWithReflection(dvpVo);
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
		} catch (IllegalAccessException e) {
			log.error(e.getMessage());
		} catch (ParserConfigurationException e) {
			log.error(e.getMessage());
		} catch (TransformerFactoryConfigurationError e) {
			log.error(e.getMessage());
		} catch (TransformerException e) {
			log.error(e.getMessage());
		}
*/
		if (!isCompra) {
			log.debug("Enviando una venta... (TraspasoContraPagoVO)");
			iso = dvpService.entrega(dvpVo);
		} else {
			log.debug("Enviando una compra... (TraspasoContraPagoVO)");
			iso = dvpService.recepcion(dvpVo);
		}
		log.debug("Valor de iso..DVP.. " + iso);
		return iso;
	}

	/**
	 * Crea el iso correspondiente a traspasos libres de pago
	 * 
	 * @param vo
	 * @param isCompra
	 * @return String
	 * @throws ProtocoloFinancieroException
	 * @throws BusinessException
	 */
	private String creaIsoTlp(Object vo, boolean isCompra) throws ProtocoloFinancieroException, BusinessException {
		log.debug("Entrando a creaIsoTlp");
		String iso = null;
		TraspasoLibrePagoVO tlpVO = (TraspasoLibrePagoVO) vo;
		tlpVO.setCliente(tlpVO.getCliente() != null ? tlpVO.getCliente().trim() : null);
		tlpVO.setCupon(tlpVO.getCupon().trim());
		tlpVO.setEmisora(tlpVO.getEmisora().trim());
		tlpVO.setIdFolioCtaReceptora(tlpVO.getIdFolioCtaReceptora().trim());
		tlpVO.setIdFolioCtaTraspasante(tlpVO.getIdFolioCtaTraspasante().trim());
		tlpVO.setReferenciaMensaje(tlpVO.getReferenciaMensaje().trim());
		tlpVO.setRfcCurp(tlpVO.getRfcCurp() != null ? tlpVO.getRfcCurp().trim() : null);
		tlpVO.setSerie(tlpVO.getSerie().trim());
		tlpVO.setTipoInstruccion(tlpVO.getTipoInstruccion().trim());
		tlpVO.setTipoValor(tlpVO.getTipoValor().trim());
		tlpVO.setReferenciaOperacion(tlpVO.getReferenciaOperacion().trim());
		tlpVO.setISIN(tlpVO.getISIN() != null ? tlpVO.getISIN().trim() : null);
		tlpVO.setBoveda(tlpVO.getBoveda());
		
/*		try {
			log.debug("IsoHelper :: (TraspasoLibrePagoVO)");
			getAttributesWithReflection(tlpVO);			
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
		} catch (IllegalAccessException e) {
			log.error(e.getMessage());
		} catch (ParserConfigurationException e) {
			log.error(e.getMessage());
		} catch (TransformerFactoryConfigurationError e) {
			log.error(e.getMessage());
		} catch (TransformerException e) {
			log.error(e.getMessage());
		}
*/		
		if (!isCompra) {
			log.debug("Enviando una venta... (TraspasoLibrePagoVO)  | Objeto [" + ReflectionToStringBuilder.toString(tlpVO) + "]");
			iso = tlpService.entrega(tlpVO);
		} else {
			log.debug("Enviando una compra... (TraspasoLibrePagoVO)");
			iso = tlpService.recepcion(tlpVO);
		}
		log.debug("Valor de iso..TLP.. " + iso);
		return iso;
	}

	/**
	 * Crea el iso correspondiente a retiros de efectivo
	 * 
	 * @param vo
	 * @return String
	 * @throws ProtocoloFinancieroException
	 * @throws BusinessException
	 */
	private String creaIsoRetiroEfectivo(Object vo) throws ProtocoloFinancieroException, BusinessException {
		log.debug("Entrando a creaIsoRetiroEfectivo");
		String iso = null;
		RetiroEfectivoVO reteVO = (RetiroEfectivoVO) vo;
		reteVO.setBeneficiario(reteVO.getBeneficiario().trim());
		reteVO.setCuentaBeneficiaria(reteVO.getCuentaBeneficiaria().trim());
		reteVO.setReferenciaMensaje(reteVO.getReferenciaMensaje().trim());
		reteVO.setTipoInstruccion(reteVO.getTipoInstruccion().trim());
		reteVO.setReferenciaOperacion(reteVO.getReferenciaOperacion().trim());
		reteVO.setDivisa(Constantes.DIVISA_MXN);	
		reteVO.setBoveda(Constantes.E_BANXICO);	
		reteVO.setBovedaTraspasante(Constantes.E_BANXICO);
		
/*		try {
			log.debug("IsoHelper :: (RetiroEfectivoVO): " + reteVO);
			getAttributesWithReflection(reteVO);
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
		} catch (IllegalAccessException e) {
			log.error(e.getMessage());
		} catch (ParserConfigurationException e) {
			log.error(e.getMessage());
		} catch (TransformerFactoryConfigurationError e) {
			log.error(e.getMessage());
		} catch (TransformerException e) {
			log.error(e.getMessage());
		}
*/		
		log.debug("Enviando un retiro de efectivo");
		iso = efectivo.retiro(reteVO);
		log.debug("ISO RETE RetiroEfectivoVO:\n" + iso);
		return iso;
	}

	/**
	 * Crea el iso correspondiente a traspaos de efectivo
	 * 
	 * @param vo
	 * @return String
	 * @throws ProtocoloFinancieroException
	 * @throws BusinessException
	 */
	private String creaIsoTraspasoEfectivo(Object vo) throws ProtocoloFinancieroException, BusinessException {
		log.debug("Entrando a creaIsoTraspasoEfectivo");
		String iso = null;
		TraspasoEfectivoVO trefVO = (TraspasoEfectivoVO) vo;
		trefVO.setBeneficiario(trefVO.getBeneficiario().trim());
		trefVO.setCuentaBeneficiaria(trefVO.getCuentaBeneficiaria().trim());
		trefVO.setReferenciaMensaje(trefVO.getReferenciaMensaje().trim());
		trefVO.setTipoInstruccion(trefVO.getTipoInstruccion().trim());
		trefVO.setReferenciaOperacion(trefVO.getReferenciaOperacion().trim());
		trefVO.setOrdenante(trefVO.getOrdenante().trim());
		trefVO.setCuentaOrdenante(trefVO.getCuentaOrdenante().trim());
		if(trefVO.getReferenciaNumericaSPEI() == null){
			trefVO.setReferenciaNumericaSPEI("");
		}
		
/*		try {
			log.debug("IsoHelper :: (TraspasoEfectivoVO)");
			getAttributesWithReflection(trefVO);		
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
		} catch (IllegalAccessException e) {
			log.error(e.getMessage());
		} catch (ParserConfigurationException e) {
			log.error(e.getMessage());
		} catch (TransformerFactoryConfigurationError e) {
			log.error(e.getMessage());
		} catch (TransformerException e) {
			log.error(e.getMessage());
		}
*/
		log.debug("Enviando un traspaso de efectivo");
		iso = efectivo.traspaso(trefVO);
		log.debug("Valor de iso..TREF.. " + iso);
		return iso;
	}
	
	/**
	 * Crea el iso correspondiente a retiros de efectivo del mensaje 103 (Banca Comercial)
	 * 
	 * @param vo
	 * @return String
	 * @throws ProtocoloFinancieroException
	 * @throws BusinessException
	 */
	private String creaIsoRetiroEfectivo103(Object vo) throws ProtocoloFinancieroException, BusinessException {
		log.debug("Entrando a creaIsoRetiroEfectivo");
		String iso = null;
		RetiroEfectivoDTO reteDTO = (RetiroEfectivoDTO) vo;
		RetiroEfectivoVO reteVO = new RetiroEfectivoVO();
		reteVO = DTOAssembler.creaRetiroEfectivoVOByRetiroEfectivoDTO(reteDTO);
		reteVO.setCuentaOrdenante(reteDTO.getInstitucion().getCuentaClabe());	
		log.debug("IsoHelper :: reteVO :: " + reteVO);
		
/*		try {
			log.debug("IsoHelper :: (RetiroEfectivoDTO)");
			getAttributesWithReflection(reteVO);
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
		} catch (IllegalAccessException e) {
			log.error(e.getMessage());
		} catch (ParserConfigurationException e) {
			log.error(e.getMessage());
		} catch (TransformerFactoryConfigurationError e) {
			log.error(e.getMessage());
		} catch (TransformerException e) {
			log.error(e.getMessage());
		}
*/		
		log.debug("Generando iso 103");
		iso = efectivo.retiro103(reteVO);			
		log.debug("Valor de iso..103.. " + iso);
		
		return iso;
	}

    /**
     * 
     * @param depDivDto
     * @return
     * @throws DispatcherException
     */
    private String generaDepositoDivisasVo(DepositoDivisaDTO depDivDto)
            throws ProtocoloFinancieroException, BusinessException {
        DepositoDivisaVO depositoDivisaVO = new DepositoDivisaVO();
        String iso = null;
        
        SimpleDateFormat sdfFecha = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdfHora = new SimpleDateFormat("HHmmss");
        String fechaActual = sdfFecha.format(new Date());
        String horaActual = sdfHora.format(new Date());
        
        depositoDivisaVO.setTipoMensaje(TIPO_MSJ);
        depositoDivisaVO.setFechaOperacion(fechaActual);
        depositoDivisaVO.setClaveOrdenante(depDivDto.getCasfim());
        depositoDivisaVO.setNombreOrdenante(depDivDto.getNombreCorto());
        depositoDivisaVO.setClaveBeneficiario(depDivDto.getCasfim());
        depositoDivisaVO.setConcepto("DEPOSITO DIVISAS");
        depositoDivisaVO.setReferenciaNumerica(BigInteger.ONE); 
        depositoDivisaVO.setClaveRastreo(BigInteger.ONE);
        depositoDivisaVO.setTipoPago(TIPO_PAGO_DEP_DIVISA);
        depositoDivisaVO.setMonto(depDivDto.getImporte().setScale(2,BigDecimal.ROUND_DOWN));
        depositoDivisaVO.setBoveda(depDivDto.getBoveda());
        depositoDivisaVO.setDivisa(depDivDto.getDivisa());
        
        
        XStream xstream = new XStream(new DomDriver());
        xstream.autodetectAnnotations(true);		
        xstream.alias(MENSAJE_DIVISA, DepositoDivisaVO.class);
        
/*		try {
			log.debug("IsoHelper :: (DepositoDivisaVO)");
			getAttributesWithReflection(depositoDivisaVO);
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
		} catch (IllegalAccessException e) {
			log.error(e.getMessage());
		} catch (ParserConfigurationException e) {
			log.error(e.getMessage());
		} catch (TransformerFactoryConfigurationError e) {
			log.error(e.getMessage());
		} catch (TransformerException e) {
			log.error(e.getMessage());
		}
*/		
        iso = xstream.toXML(depositoDivisaVO); 

        return iso;
    }
    
    private static String getAttributesWithReflection(Object objectVO) throws IllegalArgumentException, IllegalAccessException, ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException{
    	log.debug("\nIsoHelper :: getAttributesWithReflection");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document XmlDocument = db.newDocument();
        Element XmlElementTag = XmlDocument.createElement(objectVO.getClass().getName());
        XmlDocument.appendChild(XmlElementTag);
        
	    StringBuffer buffer = new StringBuffer();
	    Field[] fields = objectVO.getClass().getDeclaredFields();
	    for (Field f : fields) {
	    	Element object = XmlDocument.createElement(f.getName().toString());	    	
	    	f.setAccessible(true);
	        if (!Modifier.isStatic(f.getModifiers())) {
	          Object value = f.get(objectVO);
	          buffer.append(f.getType().getName());
	          buffer.append(" ");
	          buffer.append(f.getName());
	          buffer.append("=");
	          buffer.append("" + value);
	          buffer.append("\n");
//	          object.setAttribute(f.getName().toString(), f.getName().toString());
	          object.setTextContent((value != null) ? value.toString() : "");
	          XmlElementTag.appendChild(object);
	        } else {
		          Object value = f.get(objectVO);
		          log.debug("Name: " + f.getName() + "=" + value);
		          buffer.append("\n");
	        }
	        XmlElementTag.appendChild(object);
	     }
	    log.debug("nIsoHelper :: buffer.toString() :: " + buffer.toString());
        Source source = new DOMSource(XmlDocument);
        StringWriter writer = new StringWriter();
        Result result = new StreamResult(writer);            
        Transformer xmlTransformer = TransformerFactory.newInstance().newTransformer();
        xmlTransformer.transform(source, result);
        String strResult = writer.toString();
    	log.debug("\nIsoHelper :: strResult: " + strResult);
	    return buffer.toString();
    }
	
    private static final Integer TIPO_PAGO_DEP_DIVISA = Integer.valueOf(5);

}

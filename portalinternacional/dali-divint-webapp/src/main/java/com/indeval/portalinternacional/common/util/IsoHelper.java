/*
 * Copyright (c) 2005-2007 Bursatec. All Rights Reserved
 */
package com.indeval.portalinternacional.common.util;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.util.constants.Constantes;
import com.indeval.protocolofinanciero.api.ProtocoloFinancieroException;
import com.indeval.protocolofinanciero.api.service.Efectivo;
import com.indeval.protocolofinanciero.api.service.TraspasoContraPago;
import com.indeval.protocolofinanciero.api.service.TraspasoContraPagoInternacional;
import com.indeval.protocolofinanciero.api.service.TraspasoLibrePago;
import com.indeval.protocolofinanciero.api.service.TraspasoLibrePagoInternacional;
import com.indeval.protocolofinanciero.api.vo.RetiroEfectivoVO;
import com.indeval.protocolofinanciero.api.vo.TraspasoContraPagoVO;
import com.indeval.protocolofinanciero.api.vo.TraspasoEfectivoVO;
import com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO;

/**
 * Ayudante para la generaci&oacute;n de los mensajes con formato ISO que ser&aacute;n
 * firmados en la captura de operaciones.
 * 
 * @author Jos&eacute; Antonio Huizar Moreno
 * @version 1.0
 * 
 */ 
public class IsoHelper {

	private TraspasoLibrePago tlpService;	

	private TraspasoContraPago dvpService;

	/** Servicio de negocio para la generación de las cadenas ISO a firmar */
	private TraspasoContraPagoInternacional dvpSicService;

	/** Bean para acceso al servicio de traspasos libres de pago */
	private TraspasoLibrePagoInternacional tlpSicService;

	/** Bean para acceso al servicio de operaciones de efectivo */
	private Efectivo efectivo;

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


	public TraspasoContraPagoInternacional getDvpSicService() {
		return dvpSicService;
	}

	public void setDvpSicService(TraspasoContraPagoInternacional dvpSicService) {
		this.dvpSicService = dvpSicService;
	}

	public TraspasoLibrePagoInternacional getTlpSicService() {
		return tlpSicService;
	}

	public void setTlpSicService(TraspasoLibrePagoInternacional tlpSicService) {
		this.tlpSicService = tlpSicService;
	}
	

	
	/**
	 * @see com.indeval.portallegado.middleware.services.enviooperaciones.EnvioOperacionesService#creaISO(java.lang.Object,
	 *      boolean)
	 */
	public String creaISO(Object vo, boolean isCompra) throws BusinessException {
		log.debug("Entrando a creaISO");
		try {
			if (vo instanceof TraspasoContraPagoVO) {
				return creaIsoDvp(vo, isCompra);
			} else if (vo instanceof TraspasoLibrePagoVO) {
				return creaIsoTlp(vo, isCompra);
			} else if (vo instanceof RetiroEfectivoVO) {
				return creaIsoRetiroEfectivo(vo);
			} else if (vo instanceof TraspasoEfectivoVO) {
				return creaIsoTraspasoEfectivo(vo);
			}
		} catch (ProtocoloFinancieroException e) {
			throw new BusinessException(e.getMessage());
		}
		return null;
	}
	
	
		
	public String creaIsoSicDvp(TraspasoContraPagoVO vo, boolean isCompra) throws BusinessException {
		String iso = null;
		try {
		if (!isCompra) {
			iso = dvpSicService.entrega(vo);
		} else {
			iso = dvpSicService.recepcion(vo);
		}	
		} catch (ProtocoloFinancieroException e) {
			throw new BusinessException(e.getMessage());
		}
		 if (iso != null) {
             iso = iso + "\n";
             iso = iso.replace("\r\n", "\n");
         }				
		return iso;
	}


	/**
	 * Crea el ISO correspondiente a traspasos contrapago
	 * 
	 * @param vo
	 * @param isCompra
	 * @return String
	 * @throws ProtocoloFinancieroException
	 * @throws BusinessException
	 */
	private String creaIsoDvp(Object vo, boolean isCompra) throws ProtocoloFinancieroException, BusinessException {
		log.debug("Entrando a creaIsoDvp");
		String iso = null;
		TraspasoContraPagoVO dvpVo = (TraspasoContraPagoVO) vo;

		dvpVo.setBoveda(null);//TODO POR EL MOMENTO SE VA DEJAR ASI SE VA A PONER LA BOVEDA ADECUADA EN
		dvpVo.setBovedaEfectivo(null);
		
		dvpVo.setCupon(dvpVo.getCupon().trim());
		dvpVo.setEmisora(dvpVo.getEmisora().trim());
		dvpVo.setIdFolioCtaReceptora(dvpVo.getIdFolioCtaReceptora().trim());
		dvpVo.setIdFolioCtaTraspasante(dvpVo.getIdFolioCtaTraspasante().trim());
		dvpVo.setReferenciaMensaje(dvpVo.getReferenciaMensaje().trim());
		dvpVo.setSerie(dvpVo.getSerie()!= null ? dvpVo.getSerie().trim() : null);
		dvpVo.setTipoInstruccion(dvpVo.getTipoInstruccion()!= null ? dvpVo.getTipoInstruccion().trim() : null );
		dvpVo.setTipoValor(dvpVo.getTipoValor().trim());
		dvpVo.setReferenciaOperacion(dvpVo.getReferenciaOperacion() != null ? dvpVo.getReferenciaOperacion().trim(): null);
		dvpVo.setISIN(dvpVo.getISIN() != null ? dvpVo.getISIN().trim() : null);
		if (dvpVo.getTipoInstruccion().equalsIgnoreCase(Constantes.TIPO_INSTRUCCION_VENTA)) {
			dvpVo.setFechaVencimiento(null);
			dvpVo.setTasaNegociada(null);
			dvpVo.setTasaFija(null);
		}
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
	 * Crea el ISO correspondiente a traspasos libres de pago
	 * 
	 * @param vo
	 * @param isCompra
	 * @return String
	 * @throws ProtocoloFinancieroException
	 * @throws BusinessException
	 */
	public String creaIsoSicTlp(TraspasoLibrePagoVO vo, boolean isCompra) throws  BusinessException {
		String iso = null;
	 try{
		if (!isCompra) {
			iso = tlpSicService.entrega(vo);
		} else {
			iso = tlpSicService.recepcion(vo);
		}
	 } catch (ProtocoloFinancieroException e) {
			throw new BusinessException(e.getMessage());
	}
		 if (iso != null) {
             iso = iso + "\n";
             iso = iso.replace("\r\n", "\n");
         }
		return iso;
	}
	
	/**
	 * Crea el ISO correspondiente a traspasos libres de pago
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

		tlpVO.setBoveda(null);//TODO POR EL MOMENTO SE VA DEJAR ASI SE VA A PONER LA BOVEDA ADECUADA EN 

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
		if (!isCompra) {
			log.debug("Enviando una venta... (TraspasoLibrePagoVO)  | Objeto [" + ReflectionToStringBuilder.toString(tlpVO) + "]");
			iso = tlpService.entrega(tlpVO);
		} else {
			log.debug("Enviando una compra... (TraspasoLibrePagoVO)");
			iso = tlpService.recepcion(tlpVO);
		}
		iso=iso.replaceAll("\n\n", "\n");
		iso=iso+"\n";
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
		log.debug("Enviando un retiro de efectivo");
		iso = efectivo.retiro(reteVO);
		log.debug("Valor de iso..RETE.. " + iso);
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
		trefVO.setOrdenante(trefVO.getOrdenante().trim());
		trefVO.setCuentaOrdenante(trefVO.getCuentaOrdenante().trim());
		trefVO.setReferenciaMensaje(trefVO.getReferenciaMensaje().trim());
		trefVO.setTipoInstruccion(trefVO.getTipoInstruccion().trim());
		trefVO.setReferenciaOperacion(trefVO.getReferenciaOperacion().trim());
		log.debug("Enviando un traspaso de efectivo");
		iso = efectivo.traspaso(trefVO);
		log.debug("Valor de iso..TREF.. " + iso);
		return iso;
	}
}

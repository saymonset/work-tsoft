/**
 * Bursatec - Portal DALI
 * Copyrigth (c) 2014 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.presentation.controller.fileTransfer.encolador;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.indeval.portaldali.middleware.constantes.FileTransferConstantes;
import com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService;
import com.indeval.portaldali.middleware.services.filetransfer.FileTransferMisFisService;
import com.indeval.portaldali.middleware.services.filetransfer.FileTransferMiscelaneaVo;
import com.indeval.portaldali.middleware.services.filetransfer.util.Constantes;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.util.UtilServices;
import com.indeval.portaldali.persistence.util.DateUtil;
import com.indeval.portaldali.presentation.helper.IsoHelper;
import com.indeval.protocolofinanciero.api.ProtocoloFinancieroException;
import com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO;

/**
 * Encolador para las operaciones de Miscelanea Fiscal.
 * 
 * @author Pablo Balderas
 *
 */
public class EncoladorMiscelaneaFiscal extends EncoladorPFI {

	/** Servicio para grabar las operaciones */
	private EnvioOperacionesService envioOperacionesService;
	
	/** Servicio para obtener constantes */
	private UtilServices utilService;

	/** Ayudante para la generacion de las cadenas ISO que deberan ser firmadas */
	private IsoHelper isoHelper;
	
	/** Servicio de file transfer de miscelanea fiscal */
	private FileTransferMisFisService fileTransferMisFisService;
	
	
	/* (non-Javadoc)
	 * @see com.indeval.portaldali.presentation.controller.fileTransfer.encolador.EncoladorPFI#obtenerISOs(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Map<String, Object> obtenerISOs(String idInstitucion, String folioInstitucion, String tipoFileTransfer) {
		Map<String, Object> resultados = new HashMap<String, Object>();
		List<String> listaIsos = new ArrayList<String>();
		List<Object> listaTlps = new ArrayList<Object>();
		List<Object> listaRegistrosFT = new ArrayList<Object>();
		Date fechaActual = utilService.getCurrentDate();
		//Obtiene los registros a procesar
		List<FileTransferMiscelaneaVo> registros = 
			fileTransferMisFisService.obtenerOperacionesMiscelaneaFiscal(idInstitucion, folioInstitucion, tipoFileTransfer);
		//Recorremos la lista para generar los isos
		if(registros != null && !registros.isEmpty()) {
			for (FileTransferMiscelaneaVo fileTransferMiscelanea : registros) {
				//Si la fecha de registro no corresponde, lanzamos una excpecion
				if(DateUtil.comparaFechasDias(fechaActual, fileTransferMiscelanea.getFechaRegistro()) != 0) {
					throw new BusinessException("Error: No se pueden procesar registros cuya Fecha de Registro sea diferente a la fecha actual.");
				}
				//Se arma el TLP
				TraspasoLibrePagoVO tlp = new TraspasoLibrePagoVO();
				tlp.setFechaRegistro(fileTransferMiscelanea.getFechaRegistro());
				tlp.setFechaHoraCierreOper(fileTransferMiscelanea.getFechaHoraCierreOper());
				tlp.setIdFolioCtaTraspasante(
					fileTransferMiscelanea.getIdTraspasante() + 
					fileTransferMiscelanea.getFolioTraspasante() + 
					fileTransferMiscelanea.getCuentaTraspasante());
				tlp.setIdFolioCtaReceptora(
					fileTransferMiscelanea.getIdReceptor() + 
					fileTransferMiscelanea.getFolioReceptor() + 
					fileTransferMiscelanea.getCuentaReceptor());
				tlp.setTipoValor(fileTransferMiscelanea.getTipoValor());
				tlp.setEmisora(fileTransferMiscelanea.getEmisora());
				tlp.setSerie(fileTransferMiscelanea.getSerie());
				tlp.setCupon(fileTransferMiscelanea.getCupon());
				//ISIN ?
				tlp.setBoveda(fileTransferMiscelanea.getBoveda());
				tlp.setCantidadTitulos(fileTransferMiscelanea.getCantidadOperada().longValue());
				//Divisa ?
				if(FileTransferConstantes.TIPO_OPERACION_FC.equals(tipoFileTransfer)) {					
					tlp.setFechaAdquisicion(fileTransferMiscelanea.getFechaAdquisicion());
				}
				tlp.setPrecioAdquisicion(fileTransferMiscelanea.getPrecioAdquisicion());
				tlp.setCliente(fileTransferMiscelanea.getCliente());
				tlp.setRfcCurp(fileTransferMiscelanea.getRfcCurp());
				if(FileTransferConstantes.TIPO_OPERACION_FD.equals(tipoFileTransfer)) {
					tlp.setFechaAdquisicion(fechaActual);
					tlp.setCostoFiscalActualizado(fileTransferMiscelanea.getCostoFiscalActualizado());
				}
				tlp.setFechaLiquidacion(fechaActual);
				tlp.setReferenciaOperacion(utilService.getFolio(Constantes.SECUENCIA_FOLIO_CONTROL).toString());
				tlp.setReferenciaMensaje(utilService.getFolio(Constantes.SEQ_REFERENCIA_MENSAJE).toString());
				tlp.setTipoInstruccion(FileTransferMisFisService.TIPO_OPERACION_MISC_FISCAL);
				//Se genera el ISO y se agrega a la lista
				listaIsos.add(isoHelper.creaISO(tlp, fileTransferMiscelanea.isCompra()).replace("\r\n", "\n"));
				//Se agrega el objeto a partir del cual se genero el ISO
				listaTlps.add(tlp);
				//Indica si es compra o venta
				listaRegistrosFT.add(fileTransferMiscelanea);
			}
		}
		//Se colocan ambas listas en el mapa y retornamos.
		resultados.put(ID_LISTA_ISOS, listaIsos);
		resultados.put(ID_LISTA_OBJ_PFI, listaTlps);
		resultados.put(ID_LISTA_REGISTROS, listaRegistrosFT);
		return resultados;
	}

	/* (non-Javadoc)
	 * @see com.indeval.portaldali.presentation.controller.fileTransfer.encolador.EncoladorPFI#firmayEncola(java.util.List, java.util.List)
	 */
	@Override
	public Map<Integer, Boolean> firmayEncola(List<Object> listaObjetos, List<String> isosFirmados, 
			List<Object> registrosFT, HashMap<String, Object> datosAdicionales) 
			throws BusinessException, ProtocoloFinancieroException {
		Map<Integer, Boolean> resultado = new HashMap<Integer, Boolean>();
		String folioAsignado = null;
		String isoFirmado = null;
		FileTransferMiscelaneaVo fileTransferMiscelanea = null;
		for(int i=0; i<listaObjetos.size(); i++) {
			//Obtiene el objeto
			TraspasoLibrePagoVO tlp = (TraspasoLibrePagoVO) listaObjetos.get(i);
			//Obtiene el iso firmado
			if (!isosFirmados.isEmpty() && i < isosFirmados.size()) {
				isoFirmado = isosFirmados.get(i);
			}
			//Obtiene la bandera de compra
			fileTransferMiscelanea = (FileTransferMiscelaneaVo) registrosFT.get(i);
			//Obtiene el folio asignado
			folioAsignado = tlp.getReferenciaOperacion();
			//Envía la operación
			envioOperacionesService.grabaOperacion(
				tlp, folioAsignado, fileTransferMiscelanea.isCompra(), datosAdicionales, null, isoFirmado);
			resultado.put(fileTransferMiscelanea.getConsec(), true);
		}
		return resultado;
	}

	/**
	 * @return the envioOperacionesService
	 */
	public EnvioOperacionesService getEnvioOperacionesService() {
		return envioOperacionesService;
	}

	/**
	 * @param envioOperacionesService the envioOperacionesService to set
	 */
	public void setEnvioOperacionesService(
			EnvioOperacionesService envioOperacionesService) {
		this.envioOperacionesService = envioOperacionesService;
	}

	/**
	 * @return the utilService
	 */
	public UtilServices getUtilService() {
		return utilService;
	}

	/**
	 * @param utilService the utilService to set
	 */
	public void setUtilService(UtilServices utilService) {
		this.utilService = utilService;
	}

	/**
	 * @return the isoHelper
	 */
	public IsoHelper getIsoHelper() {
		return isoHelper;
	}

	/**
	 * @param isoHelper the isoHelper to set
	 */
	public void setIsoHelper(IsoHelper isoHelper) {
		this.isoHelper = isoHelper;
	}

	/**
	 * @return the fileTransferMisFisService
	 */
	public FileTransferMisFisService getFileTransferMisFisService() {
		return fileTransferMisFisService;
	}

	/**
	 * @param fileTransferMisFisService the fileTransferMisFisService to set
	 */
	public void setFileTransferMisFisService(
			FileTransferMisFisService fileTransferMisFisService) {
		this.fileTransferMisFisService = fileTransferMisFisService;
	}

}

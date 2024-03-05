package com.indeval.portaldali.presentation.controller.mercadodinero.fileTransfer.encoladores;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService;
import com.indeval.portaldali.middleware.services.filetransfer.CampoArchivoVO;
import com.indeval.portaldali.middleware.services.filetransfer.RegistroProcesadoVO;
import com.indeval.portaldali.middleware.services.filetransfer.TotalesProcesoVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import com.indeval.portaldali.middleware.services.util.UtilServices;
import com.indeval.portaldali.middleware.services.util.util.Constantes;
import com.indeval.portaldali.presentation.controller.mercadodinero.fileTransfer.IEncoladorProtocoloFinanciero;
import com.indeval.portaldali.presentation.controller.mercadodinero.fileTransfer.layout.LayoutMD;
import com.indeval.portaldali.presentation.helper.IsoHelper;
import com.indeval.protocolofinanciero.api.ProtocoloFinancieroException;
import com.indeval.protocolofinanciero.api.vo.TraspasoContraPagoVO;
import com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO;

public class EncoladorMDTraspasos implements IEncoladorProtocoloFinanciero {

	/** Servicio para grabar las operaciones */
	private EnvioOperacionesService envioOperacionesService;
	
	/** Servicio para obtener constantes */
	private UtilServices utilService;

	/** Ayudante para la generacion de las cadenas ISO que deberan ser firmadas */
	protected IsoHelper isoHelper = null;

	/** Constante que indica la operacion de tipo reporto */
	private static String TIPO_OPER_REPORTO = "R";

	/** Constante que indica la operacion de tipo traspaso libre pago */
	private static String TIPO_OPER_TLP = "T";

    private Map<String, Object> datosAdicionales;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.presentation.controller.mercadodinero.fileTransfer.IEncoladorProtocoloFinanciero#obtenerOperacionesFirmadas(com.indeval.portaldali.middleware.services.filetransfer.TotalesProcesoVO)
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> obtenerISOs(TotalesProcesoVO totProcVO) {
		PaginaVO pag = totProcVO.getPaginaVO();
		List<RegistroProcesadoVO> l = pag.getRegistros();
		List<String> resultados = new ArrayList<String>();
		List<Object> listaObjetos = new ArrayList<Object>();
		Map<String, Object> map = new HashMap<String, Object>();

		for (int i = 0; i < l.size(); i++) {
			final RegistroProcesadoVO reg = l.get(i);
			// El registro no tiene errores
			if (!reg.getEstado().trim().equals("ER")) {
				// Esta lista contiene cada uno de los campos que conforman el
				// registro
				@SuppressWarnings("rawtypes")
                List campos = reg.getDatos();
				// Revisar en cada uno de los campos hasta encontrar el tipo de
				// operacion
				String to = getValor(campos, LayoutMD.TIPO_OPER).toString()
						.trim();
				TraspasoContraPagoVO vo = new TraspasoContraPagoVO();
				TraspasoLibrePagoVO voTLP = new TraspasoLibrePagoVO();

				if (TIPO_OPER_TLP.equalsIgnoreCase(to)) {

					voTLP.setTipoInstruccion(to);
					voTLP.setIdFolioCtaTraspasante(getValor(campos,
							LayoutMD.ID_TRAS).toString()
							+ getValor(campos, LayoutMD.FOLIO_TRAS).toString()
							+ getValor(campos, LayoutMD.CTA_TRASP).toString());
					voTLP.setIdFolioCtaReceptora(getValor(campos,
							LayoutMD.ID_RECEP).toString()
							+ getValor(campos, LayoutMD.FOLIO_RECEP).toString()
							+ getValor(campos, LayoutMD.CTA_RECEP).toString());
					voTLP.setTipoValor(getValor(campos, LayoutMD.TV).toString()
							.trim().toUpperCase());
					voTLP.setEmisora(getValor(campos, LayoutMD.EMISORA)
							.toString().trim().toUpperCase());
					voTLP.setSerie(getValor(campos, LayoutMD.SERIE).toString()
							.trim().toUpperCase());
					voTLP.setCupon(getValor(campos, LayoutMD.CUPON).toString()
							.trim().toUpperCase());
					voTLP.setCantidadTitulos(Long.parseLong(getValor(campos,
							LayoutMD.CANTIDAD).toString()));
					voTLP.setFechaLiquidacion(reg.getCamposCalculadosVO()
							.getFechaLiquidacion());
					voTLP.setFechaRegistro(reg.getCamposCalculadosVO()
							.getFechaConcertacion());
					voTLP.setFechaHoraCierreOper((Date) getValor(campos, LayoutMD.FECHA_HORA_CIERRE_OPER));
					voTLP.setReferenciaMensaje(utilService.getFolio(Constantes.SEQ_REFERENCIA_MENSAJE).toString());
					voTLP.setReferenciaOperacion(utilService.getFolio(Constantes.SECUENCIA_FOLIO_CONTROL).toString()); 
					
					        
					resultados.add(isoHelper.creaISO(voTLP, reg.isCompra()).replace("\r\n", "\n"));
					
					listaObjetos.add(voTLP);
					
				} else {

					vo.setTipoInstruccion(to);
					vo.setIdFolioCtaTraspasante(getValor(campos,
							LayoutMD.ID_TRAS).toString()
							+ getValor(campos, LayoutMD.FOLIO_TRAS).toString()
							+ getValor(campos, LayoutMD.CTA_TRASP).toString());
					vo.setIdFolioCtaReceptora(getValor(campos,
							LayoutMD.ID_RECEP).toString()
							+ getValor(campos, LayoutMD.FOLIO_RECEP).toString()
							+ getValor(campos, LayoutMD.CTA_RECEP).toString());
					vo.setTipoValor(getValor(campos, LayoutMD.TV).toString()
							.trim().toUpperCase());
					vo.setEmisora(getValor(campos, LayoutMD.EMISORA).toString()
							.trim().toUpperCase());
					vo.setSerie(getValor(campos, LayoutMD.SERIE).toString()
							.trim().toUpperCase());
					vo.setCupon(getValor(campos, LayoutMD.CUPON).toString()
							.trim().toUpperCase());
					vo.setCantidadTitulos(Long.parseLong(getValor(campos,
							LayoutMD.CANTIDAD).toString()));
					vo.setFechaLiquidacion(reg.getCamposCalculadosVO()
							.getFechaLiquidacion());
					vo.setFechaConcertacion(reg.getCamposCalculadosVO()
							.getFechaConcertacion());
					vo.setFechaHoraCierreOper((Date) getValor(campos, LayoutMD.FECHA_HORA_CIERRE_OPER));
					vo.setMonto(reg.getCamposCalculadosVO().getImporte());
					vo.setFechaRegistro(reg.getCamposCalculadosVO()
							.getFechaConcertacion());
					
					if (TIPO_OPER_REPORTO.equalsIgnoreCase(vo
							.getTipoInstruccion())) {
						vo.setFechaVencimiento(reg.getCamposCalculadosVO()
								.getFechaReporto());
						vo.setTasaFija(Boolean.TRUE);
						vo.setTasaNegociada(new Double(reg
								.getCamposCalculadosVO().getTasaPremio()
								.doubleValue()));
						vo.setTasaReferencia(new Double(reg
								.getCamposCalculadosVO().getTasaPremio()
								.doubleValue()));
					}
					vo.setReferenciaMensaje(utilService.getFolio(Constantes.SEQ_REFERENCIA_MENSAJE).toString());
					vo.setReferenciaOperacion(utilService.getFolio(Constantes.SECUENCIA_FOLIO_CONTROL).toString());
					/*
					 * se modifica el seteo de la BobedaEfectivo y la Divisa que contiene el archivo
					 */
					vo.setDivisa(getValor(campos, LayoutMD.DIVISA).toString()
							.trim().toUpperCase());
					//se valida si es una compra para agregar la boveda de Efectivo
					if(reg.isCompra()){
						vo.setBovedaEfectivo(getValor(campos, LayoutMD.BOVEDA_EFECTIVO).toString()
								.trim().toUpperCase());
					}
					///////
					//
//					vo.setBoveda(Constantes.E_BANXICO);
//					vo.setBovedaEfectivo(Constantes.E_BANXICO);
					//vo.setDivisa(Constantes.DIVISA_MXN);
					
					resultados.add(isoHelper.creaISO(vo, reg.isCompra()).replace("\r\n", "\n"));
					
					listaObjetos.add(vo);
					
				}
			}
		}

		map.put(ID_LISTA_ISOS, resultados);
		map.put(ID_LISTA_OBJETOS, listaObjetos);
		
		return map;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.presentation.controller.mercadodinero.fileTransfer.IEncoladorProtocoloFinanciero#firmayEncola(com.indeval.portaldali.middleware.services.filetransfer.TotalesProcesoVO,
	 *      java.util.List, java.util.List)
	 */
	public Map<BigInteger, Integer> firmayEncola(TotalesProcesoVO totProcVO, List<Object> listaObjetos,
			List<String> isosFirmados) throws BusinessException,
			ProtocoloFinancieroException {
		Map<BigInteger, Integer> mpRegistrosEncolados = new HashMap<BigInteger, Integer>();

		PaginaVO pag = totProcVO.getPaginaVO();
		@SuppressWarnings("rawtypes")
        List l = pag.getRegistros();
		RegistroProcesadoVO reg;
		int indiceObjetoAProcesar = 0;
		String isoFirmado = null;
		
		for (int i = 0; i < l.size(); i++) {
			reg = (RegistroProcesadoVO) l.get(i);
			isoFirmado = null;
			// El registro no tiene errores
			if (!reg.getEstado().trim().equals("ER")) {
				
				TraspasoContraPagoVO vo = null;
				TraspasoLibrePagoVO voTLP = null;
				String folioAsignado = null;
				
				if(listaObjetos != null && !listaObjetos.isEmpty() &&  indiceObjetoAProcesar < listaObjetos.size()) {
					// Revisa si existe el iso antes de tratar de extraerlo
					if (!isosFirmados.isEmpty()
							&& indiceObjetoAProcesar < isosFirmados.size()) {
						isoFirmado = isosFirmados.get(indiceObjetoAProcesar);
					}
	
					mpRegistrosEncolados.put(reg.getConsec(), new Integer(1));
	
					try {
						if (listaObjetos.get(indiceObjetoAProcesar) instanceof TraspasoLibrePagoVO) {
							voTLP = (TraspasoLibrePagoVO)listaObjetos.get(indiceObjetoAProcesar);
							folioAsignado = voTLP.getReferenciaOperacion();
							envioOperacionesService.grabaOperacion(voTLP, folioAsignado, reg
									.isCompra(), (HashMap<String, Object>) getDatosAdicionales(),
									null, isoFirmado);
						} else {
							vo = (TraspasoContraPagoVO)listaObjetos.get(indiceObjetoAProcesar);
							folioAsignado = vo.getReferenciaOperacion(); 	
							
							envioOperacionesService.grabaOperacion(vo, folioAsignado, reg
									.isCompra(), (HashMap<String, Object>) getDatosAdicionales(),
									null, isoFirmado);
						}
					} catch (Exception e) {
						mpRegistrosEncolados.put(reg.getConsec(), new Integer(0));
						e.printStackTrace();
					}
				
					indiceObjetoAProcesar++;
				}
			}
		}

		return mpRegistrosEncolados;
	}

	/**
	 * Obtiene el valor de un campo del layout
	 * 
	 * @param campos
	 *            Contiene todos los campos
	 * @param campo
	 *            Campo a obtener
	 * @return Valor del campo
	 */
	private Object getValor(List<CampoArchivoVO> campos, LayoutMD campo) {
		Object valor = campos.get(campo.getPosicion()).getValor();
		return valor;
	}

	/**
	 * @return the envioOperacionesService
	 */
	public EnvioOperacionesService getEnvioOperacionesService() {
		return envioOperacionesService;
	}

	/**
	 * @param envioOperacionesService
	 *            the envioOperacionesService to set
	 */
	public void setEnvioOperacionesService(
			EnvioOperacionesService envioOperacionesService) {
		this.envioOperacionesService = envioOperacionesService;
	}

	/**
	 * @return the isoHelper
	 */
	public IsoHelper getIsoHelper() {
		return isoHelper;
	}

	/**
	 * @param isoHelper
	 *            the isoHelper to set
	 */
	public void setIsoHelper(IsoHelper isoHelper) {
		this.isoHelper = isoHelper;
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

    public Map<String, Object> getDatosAdicionales() {
        return datosAdicionales;
    }

    public void setDatosAdicionales(Map<String, Object> datosAdicionales) {
        this.datosAdicionales = datosAdicionales;
    }

}

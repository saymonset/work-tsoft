package com.indeval.portaldali.presentation.controller.mercadodinero.fileTransfer.encoladores;

import java.math.BigDecimal;
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
import com.indeval.portaldali.presentation.controller.mercadodinero.fileTransfer.layout.LayoutMC;
import com.indeval.portaldali.presentation.controller.mercadodinero.fileTransfer.layout.LayoutMD;
import com.indeval.portaldali.presentation.helper.IsoHelper;
import com.indeval.protocolofinanciero.api.ProtocoloFinancieroException;
import com.indeval.protocolofinanciero.api.vo.TraspasoContraPagoVO;
import com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO;

public class EncoladorMCTraspasos implements IEncoladorProtocoloFinanciero {

	/** Servicio para grabar las operaciones */
	private EnvioOperacionesService envioOperacionesService;
	
	/** Servicio para obtener constantes */
	private UtilServices utilService;

	/** Ayudante para la generacion de las cadenas ISO que deberan ser firmadas */
	protected IsoHelper isoHelper = null;

    private Map<String, Object> datosAdicionales;
    
    public static final String TIPO_OP_V = "V";

    public static final String TIPO_OP_T = "T";

    public static final String TIPO_OP_TL = "TL";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.presentation.controller.mercadodinero.fileTransfer.IEncoladorProtocoloFinanciero#obtenerOperacionesFirmadas(com.indeval.portaldali.middleware.services.filetransfer.TotalesProcesoVO)
	 */
	public Map<String, Object> obtenerISOs(final TotalesProcesoVO totProcVO) {
		final List<String> resultados = new ArrayList<String>();
		PaginaVO pag = totProcVO.getPaginaVO();
		@SuppressWarnings("rawtypes")
		final List l = pag.getRegistros();
		RegistroProcesadoVO reg;
		List<Object> listaObjetos = new ArrayList<Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		
		for (int i = 0; i < l.size(); i++) {
            reg = (RegistroProcesadoVO) l.get(i);
            // El registro no tiene errores
            if (!reg.getEstado().trim().equals("ER")) {
                // Esta lista contiene cada uno de los campos que conforman el registro
                @SuppressWarnings("unchecked")
                List<CampoArchivoVO> campos = reg.getDatos();
                // Revisar en cada uno de los campos hasta encontrar el tipo de operacion
                String to = getValor(campos, LayoutMC.TIPO_OPER).toString().trim();
                TraspasoContraPagoVO vo = new TraspasoContraPagoVO();
                TraspasoLibrePagoVO voTLP = new TraspasoLibrePagoVO();
                
                if ((TIPO_OP_T.equalsIgnoreCase(to) || TIPO_OP_TL.equalsIgnoreCase(to))) {
					voTLP.setTipoInstruccion(TIPO_OP_T);
					voTLP.setIdFolioCtaTraspasante(getValor(campos, LayoutMC.CVE_TRAS)
					        .toString()
					         + getValor(campos, LayoutMC.CTA_TRAS).toString());
					voTLP.setIdFolioCtaReceptora(getValor(campos, LayoutMC.CVE_RECEP)
					         .toString()
					         + getValor(campos, LayoutMC.CTA_RECEP).toString());
					voTLP.setTipoValor(getValor(campos, LayoutMC.TV).toString().trim());
					voTLP.setEmisora(getValor(campos, LayoutMC.EMISORA).toString().trim());
					voTLP.setSerie(getValor(campos, LayoutMC.SERIE).toString().trim());
					voTLP.setCupon(getValor(campos, LayoutMC.CUPON).toString().trim());
					voTLP.setCantidadTitulos(Long.parseLong(getValor(campos, LayoutMC.CANTIDAD).toString()));
					voTLP.setFechaLiquidacion(reg.getCamposCalculadosVO()
					                 .getFechaLiquidacion());
					voTLP.setFechaRegistro(reg.getCamposCalculadosVO().getFechaConcertacion());
					voTLP.setFechaHoraCierreOper((Date) getValor(campos, LayoutMC.FECHA_HORA_CIERRE_OPER));
					voTLP.setReferenciaMensaje(utilService.getFolio(Constantes.SEQ_REFERENCIA_MENSAJE).toString());
					voTLP.setReferenciaOperacion(utilService.getFolio(Constantes.SECUENCIA_FOLIO_CONTROL).toString());					
					 
					resultados.add(isoHelper.creaISO(voTLP, reg.isCompra()).replace("\r\n", "\n"));
					listaObjetos.add(voTLP);
            	}
            	else {
                    vo.setTipoInstruccion(TIPO_OP_V);
                    vo.setIdFolioCtaTraspasante(getValor(campos, LayoutMC.CVE_TRAS).toString()
                            + getValor(campos, LayoutMC.CTA_TRAS).toString());
                    vo.setTipoValor(getValor(campos, LayoutMC.TV).toString().trim());
                    vo.setEmisora(getValor(campos, LayoutMC.EMISORA).toString().trim());
                    vo.setSerie(getValor(campos, LayoutMC.SERIE).toString().trim());
                    vo.setCupon(getValor(campos, LayoutMC.CUPON).toString().trim());
                    vo.setCantidadTitulos(Long.parseLong(getValor(campos, LayoutMC.CANTIDAD).toString()));
                    vo.setIdFolioCtaReceptora(getValor(campos, LayoutMC.CVE_RECEP).toString()
                            + getValor(campos, LayoutMC.CTA_RECEP).toString());
                    vo.setFechaLiquidacion(reg.getCamposCalculadosVO().getFechaLiquidacion());
                    vo.setFechaConcertacion(reg.getCamposCalculadosVO().getFechaConcertacion());
                    vo.setFechaHoraCierreOper((Date) getValor(campos, LayoutMC.FECHA_HORA_CIERRE_OPER));
                    vo.setMonto(reg.getCamposCalculadosVO().getImporte());
                    vo.setPrecio(reg.getCamposCalculadosVO().getImporte().divide(
                            new BigDecimal(vo.getCantidadTitulos()), 6,
                            BigDecimal.ROUND_HALF_UP));
                    //se modifica esta parte para que no ingrese siempre la divisa MXN y se setean los 2 nuevos campos, divisa y boveda
					//vo.setDivisa(Constantes.DIVISA_MXN);
					vo.setDivisa(getValor(campos, LayoutMC.DIVISA).toString().trim());
					// se valida que sea una compra para ingresar la boveda de Efectivo
					if(reg.isCompra()){
						vo.setBovedaEfectivo(getValor(campos, LayoutMC.BOVEDA_EFECTIVO).toString().trim());
					}
                    vo.setFechaRegistro(reg.getCamposCalculadosVO().getFechaConcertacion());
                    vo.setReferenciaMensaje(utilService.getFolio(Constantes.SEQ_REFERENCIA_MENSAJE).toString());
                    vo.setReferenciaOperacion(utilService.getFolio(Constantes.SECUENCIA_FOLIO_CONTROL).toString());
                    
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
	public Map<BigInteger, Integer> firmayEncola(final TotalesProcesoVO totProcVO, final List<Object> listaObjetos,
	        final List<String> isosFirmados) throws BusinessException,
			ProtocoloFinancieroException {
		final Map<BigInteger, Integer> mpRegistrosEncolados = new HashMap<BigInteger, Integer>();

		PaginaVO pag = totProcVO.getPaginaVO();
		@SuppressWarnings("rawtypes")
		final List l = pag.getRegistros();
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
	private Object getValor(List<CampoArchivoVO> campos, LayoutMC campo) {
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

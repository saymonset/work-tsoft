/*
 *Copyright (c) 2010 Bursatec. All Rights Reserved 
 */
package com.indeval.portaldali.presentation.controller.tesoreria.cuentas.fileTransfer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.CuentaRetiroEfectivoDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.services.filetransfer.CampoArchivoVO;
import com.indeval.portaldali.middleware.services.filetransfer.RegistroProcesadoVO;
import com.indeval.portaldali.middleware.services.filetransfer.TotalesProcesoVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import com.indeval.portaldali.middleware.services.tesoreria.cuentas.AdministracionCuentasRetiroService;
import com.indeval.portaldali.middleware.services.util.UtilServices;
import com.indeval.portaldali.presentation.controller.mercadodinero.fileTransfer.IEncoladorProtocoloFinanciero;
import com.indeval.portaldali.presentation.helper.IsoHelper;
import com.indeval.portaldali.presentation.util.ConsultaCatalogosFacade;
import com.indeval.protocolofinanciero.api.ProtocoloFinancieroException;

/**
 * Clase para la generacion de iso y registros de las cuentas de banca comercial
 * 
 * @author Maria C. Buendia
 * @version 1.0
 */
public class EncoladorCuentasBCOM implements IEncoladorProtocoloFinanciero {

	/** Servicio para obtener constantes */
	private UtilServices utilService;

	/** Ayudante para la generacion de las cadenas ISO que deberan ser firmadas */
	protected IsoHelper isoHelper = null;
	
	/** servicio de administracion de cuentas */
    private AdministracionCuentasRetiroService admonCuentasService;
    
	/** La clase que permite el acceso a la consulta de los catálogos utilizados */
	private ConsultaCatalogosFacade consultaCatalogosFacade;
    
    public static final String TIPO_OP_V = "V";

    public static final String TIPO_OP_T = "T";

    public static final String TIPO_OP_TL = "TL";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.presentation.controller.mercadodinero.fileTransfer.IEncoladorProtocoloFinanciero#obtenerOperacionesFirmadas(com.indeval.portaldali.middleware.services.filetransfer.TotalesProcesoVO)
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> obtenerISOs(TotalesProcesoVO totProcVO) {
		List resultados = new ArrayList();
		PaginaVO pag = totProcVO.getPaginaVO();
		List l = pag.getRegistros();
		RegistroProcesadoVO reg;
		List<Object> listaObjetos = new ArrayList<Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		
		for (int i = 0; i < l.size(); i++) {
            reg = (RegistroProcesadoVO) l.get(i);
            // El registro no tiene errores
            if (!reg.getEstado().trim().equals("ER")) {
                // Esta lista contiene cada uno de los campos que conforman el registro
                List campos = reg.getDatos();
                
				CuentaRetiroEfectivoDTO cuenta = new CuentaRetiroEfectivoDTO();
				//divisa
				DivisaDTO divisa = new DivisaDTO();
				divisa.setId(1);
				cuenta.setDivisa(divisa);
				BovedaDTO boveda = new BovedaDTO();
				boveda.setId(11);
				cuenta.setBoveda(boveda);
				cuenta.setTipoCuenta('N');

				InstitucionDTO inst =  
					consultaCatalogosFacade.buscarInstitucionPorIdFolio((String)getValor(campos, LayoutCB.CLAVE_TRASP));
				cuenta.setInstitucion(inst);
				inst =  
					consultaCatalogosFacade.buscarInstitucionPorIdFolio((String)getValor(campos, LayoutCB.CLAVE_RECEP));
				cuenta.setInstitucionBeneficiario(inst);
				cuenta.setCuentaBeneficiario((String)getValor(campos, LayoutCB.CUENTA_BENEF));
				cuenta.setNombreBeneficiario(((String)getValor(campos, LayoutCB.NOMBRE_BENEF)).trim().toUpperCase());
				Object maxMensual = getValor(campos, LayoutCB.MAX_MENSUAL);
				if(maxMensual != null){
					cuenta.setMontoMaximoMensual(
							maxMensual instanceof String?
									(StringUtils.isBlank((String)maxMensual)? null: new BigDecimal((String)maxMensual))
									:((BigDecimal)maxMensual));
				}
				Object maxDiario = getValor(campos, LayoutCB.MAX_DIARIO);
				if(maxDiario != null){
					cuenta.setMontoMaximoDiario(
							maxDiario instanceof String?
								(StringUtils.isBlank((String)maxDiario)? null: new BigDecimal((String)maxDiario))
								:((BigDecimal)maxDiario));
				}
				Object maxTransac = getValor(campos, LayoutCB.MAX_XTRANSC);
				if(maxTransac != null){
					cuenta.setMontoMaximoXTran(
							maxTransac instanceof String?
								(StringUtils.isBlank((String)maxTransac)? null: new BigDecimal((String)maxTransac))
								:((BigDecimal)maxTransac));
				}
				cuenta.setNumMaximoMovsXMes(
						((Integer)getValor(campos, LayoutCB.MOVS_MENSUAL))==null?
								null
								:Long.valueOf((Integer)getValor(campos, LayoutCB.MOVS_MENSUAL)));
				
				cuenta.setIdCuentaPorInstitucion(
						admonCuentasService.obtenerFolioCuenta(true, cuenta.getInstitucion().getId()).longValue());
				cuenta.setIdCuentaRetiroNal(admonCuentasService.obtenerFolioCuenta(true).longValue());
				
				listaObjetos.add(cuenta);
				

				/* genera iso de la firma */
        		StringBuffer datosFirma = new StringBuffer("\nCREACION DE CUENTA NACIONAL");
        		datosFirma.append("\nTRASPASANTE: ")
    			.append(cuenta.getInstitucion().getClaveTipoInstitucion() + cuenta.getInstitucion().getFolioInstitucion())
    			.append(" ")
    			.append(cuenta.getInstitucion().getNombreCorto());
	    		datosFirma.append("\nRECEPTOR: ")
	    			.append(cuenta.getInstitucionBeneficiario().getClaveTipoInstitucion()+ cuenta.getInstitucionBeneficiario().getFolioInstitucion())
	    			.append(" ")
	    			.append(cuenta.getInstitucionBeneficiario().getNombreCorto());;
	    		datosFirma.append("\nCUENTA BENEFICIARIO: ").append(cuenta.getCuentaBeneficiario());
	    		datosFirma.append("\nNOMBRE BENEFICIARIO: ").append(cuenta.getNombreBeneficiario());
	    		datosFirma.append("\nMONTO MAXIMO MENSUAL: ").append(cuenta.getMontoMaximoMensual()==null?"SIN LIMITE":cuenta.getMontoMaximoMensual());
	    		datosFirma.append("\nMONTO MAXIMO DIARIO: ").append(cuenta.getMontoMaximoDiario()==null?"SIN LIMITE":cuenta.getMontoMaximoDiario());
	    		datosFirma.append("\nMONTO MAXIMO TRANSACCION: ").append(cuenta.getMontoMaximoXTran()==null?"SIN LIMITE":cuenta.getMontoMaximoXTran());
	    		datosFirma.append("\nMOVIMIENTOS MAXIMOS POR MES: ").append(cuenta.getNumMaximoMovsXMes()==null?"SIN LIMITE":cuenta.getNumMaximoMovsXMes());
	    		datosFirma.append("\n");
                
				resultados.add(datosFirma.toString());
				
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
	@SuppressWarnings("unchecked")
	public Map<BigInteger, Integer> firmayEncola(TotalesProcesoVO totProcVO, List<Object> listaObjetos,
			List<String> isosFirmados) throws BusinessException,
			ProtocoloFinancieroException {
		Map<BigInteger, Integer> mpRegistrosEncolados = new HashMap<BigInteger, Integer>();

		PaginaVO pag = totProcVO.getPaginaVO();
		List l = pag.getRegistros();
		RegistroProcesadoVO reg;
		int indiceObjetoAProcesar = 0;
		String isoFirmado = null;
		
		for (int i = 0; i < l.size(); i++) {
			reg = (RegistroProcesadoVO) l.get(i);
			isoFirmado = null;
			// El registro no tiene errores
			if (!reg.getEstado().trim().equals("ER")) {
				
				if(listaObjetos != null && !listaObjetos.isEmpty() &&  indiceObjetoAProcesar < listaObjetos.size()) {
					// Revisa si existe el iso antes de tratar de extraerlo
					if (!isosFirmados.isEmpty()
							&& indiceObjetoAProcesar < isosFirmados.size()) {
						isoFirmado = isosFirmados.get(indiceObjetoAProcesar);
					}
	
					mpRegistrosEncolados.put(reg.getConsec(), new Integer(1));
	
					try {
					
						CuentaRetiroEfectivoDTO cuenta = (CuentaRetiroEfectivoDTO)listaObjetos.get(indiceObjetoAProcesar);

	     	    		Map<String, Object> datosFirma = new HashMap<String, Object>(0);
			    		datosFirma.put("creacion_usuario", isoFirmado.substring(0,isoFirmado.indexOf("_-*#")));
			    		isoFirmado = isoFirmado.substring(isoFirmado.indexOf("_-*#")+4);
			    		datosFirma.put("creacion_serie", isoFirmado.substring(0,isoFirmado.indexOf("_-*#")));
			    		datosFirma.put("creacion_isoFirmado", isoFirmado.substring(isoFirmado.indexOf("_-*#")+4));
						cuenta.setDatosFirmas(datosFirma);

						admonCuentasService.crearCuenta(cuenta);

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
	private Object getValor(List<CampoArchivoVO> campos, LayoutCB campo) {
		Object valor = campos.get(campo.getPosicion()).getValor();
		return valor;
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

	public AdministracionCuentasRetiroService getAdmonCuentasService() {
		return admonCuentasService;
	}

	public void setAdmonCuentasService(
			AdministracionCuentasRetiroService admonCuentasService) {
		this.admonCuentasService = admonCuentasService;
	}

	public ConsultaCatalogosFacade getConsultaCatalogosFacade() {
		return consultaCatalogosFacade;
	}

	public void setConsultaCatalogosFacade(
			ConsultaCatalogosFacade consultaCatalogosFacade) {
		this.consultaCatalogosFacade = consultaCatalogosFacade;
	}
}

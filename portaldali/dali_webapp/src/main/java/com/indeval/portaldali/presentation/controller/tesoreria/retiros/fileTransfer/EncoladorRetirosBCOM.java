/*
 *Copyright (c) 2010 Bursatec. All Rights Reserved 
 */
package com.indeval.portaldali.presentation.controller.tesoreria.retiros.fileTransfer;

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
import com.indeval.portaldali.middleware.dto.EstadoInstruccionDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.RetiroEfectivoDTO;
import com.indeval.portaldali.middleware.dto.criterio.CriterioCuentaEfectivoDTO;
import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;
import com.indeval.portaldali.middleware.services.filetransfer.CampoArchivoVO;
import com.indeval.portaldali.middleware.services.filetransfer.RegistroProcesadoVO;
import com.indeval.portaldali.middleware.services.filetransfer.TotalesProcesoVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import com.indeval.portaldali.middleware.services.tesoreria.AdmonRetirosEfectivoService;
import com.indeval.portaldali.middleware.services.tesoreria.cuentas.AdministracionCuentasRetiroService;
import com.indeval.portaldali.middleware.services.util.UtilServices;
import com.indeval.portaldali.middleware.services.util.util.Constantes;
import com.indeval.portaldali.persistence.dao.common.EstadoInstruccionDaliDAO;
import com.indeval.portaldali.presentation.controller.mercadodinero.fileTransfer.IEncoladorProtocoloFinanciero;
import com.indeval.portaldali.presentation.helper.IsoHelper;
import com.indeval.portaldali.presentation.util.ConsultaCatalogosFacade;
import com.indeval.protocolofinanciero.api.ProtocoloFinancieroException;

/**
 * Clase para la generacion de iso y registros de los retiros de banca comercial
 * 
 * @author Maria C. Buendia
 * @version 1.0
 */
public class EncoladorRetirosBCOM implements IEncoladorProtocoloFinanciero {

	/** Servicio para obtener constantes */
	private UtilServices utilService;

	/** Ayudante para la generacion de las cadenas ISO que deberan ser firmadas */
	protected IsoHelper isoHelper = null;
	
	/** servicio de administracion de cuentas */
    private AdministracionCuentasRetiroService admonCuentasService;
    
	/** La clase que permite el acceso a la consulta de los catálogos utilizados */
	private ConsultaCatalogosFacade consultaCatalogosFacade;

    /** Objeto de acceso al catalogo de estados de instruccion */
	private EstadoInstruccionDaliDAO estadoInstruccionDaliDAO = null;
	
	/** Service de retiros*/
	private AdmonRetirosEfectivoService admonRetiroEfectivo;
    
    public static final String TIPO_OP_V = "V";

    public static final String TIPO_OP_T = "T";

    public static final String TIPO_OP_TL = "TL";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.presentation.controller.mercadodinero.fileTransfer.IEncoladorProtocoloFinanciero#obtenerOperacionesFirmadas(com.indeval.portaldali.middleware.services.filetransfer.TotalesProcesoVO)
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> obtenerISOs(TotalesProcesoVO totProcVO){
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
				RetiroEfectivoDTO retiroDto = new RetiroEfectivoDTO();

				DivisaDTO divisa = new DivisaDTO();
				divisa.setId(1);
				retiroDto.setDivisa(divisa);
				BovedaDTO boveda = new BovedaDTO();
				boveda.setId(11);
				retiroDto.setBoveda(boveda);
				InstitucionDTO instTrasp =  
					consultaCatalogosFacade.buscarInstitucionPorIdFolio((String)getValor(campos, LayoutRB.CLAVE_TRASP));
				retiroDto.setInstitucion(instTrasp);
				retiroDto.setConceptoPago(((String)getValor(campos, LayoutRB.CONCEPTO)).trim().toUpperCase());
				retiroDto.setReferencia(((String)getValor(campos, LayoutRB.REFERENCIA)).trim());
				retiroDto.setImporteTraspaso(
						getValor(campos, LayoutRB.IMPORTE) instanceof String? 
								BigDecimal.valueOf(Double.valueOf(((String)getValor(campos, LayoutRB.IMPORTE)).trim()))
								:(BigDecimal)getValor(campos, LayoutRB.IMPORTE));
				retiroDto.setReferenciaMensaje(""+utilService.getFolio(Constantes.SEQ_REFERENCIA_MENSAJE));
				retiroDto.setReferenciaOperacion(""+utilService.getFolio(Constantes.SECUENCIA_FOLIO_CONTROL));
				
				CriterioCuentaEfectivoDTO criterio = new CriterioCuentaEfectivoDTO();
				criterio.setValorEn(new DivisaDTO(1));
				criterio.setInstitucion(instTrasp);
				criterio.setFolioCuentaPorTraspasante(((String)getValor(campos, LayoutRB.ID_CTA_BENEF)).trim());
				EstadoInstruccionDTO estado = estadoInstruccionDaliDAO.consultarEstadoInstruccionPorClave("LA");
				criterio.setEstadoCuenta(estado==null||estado.getIdEstadoInstruccion()==0? null : (""+estado.getIdEstadoInstruccion()) );
				CuentaRetiroEfectivoDTO cuenta = admonRetiroEfectivo.buscarCuentaPorCriterio(criterio,true);
				retiroDto.setCuentaRetiroEfectivo(cuenta);
				retiroDto.setIdInstReceptor(cuenta.getInstitucionBeneficiario());
				retiroDto.setFechaLiberacion(utilService.getCurrentDate());
				retiroDto.setDivisa(consultaCatalogosFacade.buscarDivisaPorId(DaliConstants.ID_DIVISA_MEXICAN_PESO));
				
				listaObjetos.add(retiroDto);
				
				/* genera iso de la firma */
				resultados.add(isoHelper.creaISO(retiroDto, false)+"\n");
				
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
						RetiroEfectivoDTO retiroDto = (RetiroEfectivoDTO)listaObjetos.get(indiceObjetoAProcesar);
						
	     	    		Map<String, Object> datosFirma = new HashMap<String, Object>(0);
			    		datosFirma.put("creacion_usuario", isoFirmado.substring(0,isoFirmado.indexOf("_-*#")));
			    		isoFirmado = isoFirmado.substring(isoFirmado.indexOf("_-*#")+4);
			    		datosFirma.put("creacion_serie", isoFirmado.substring(0,isoFirmado.indexOf("_-*#")));
			    		datosFirma.put("creacion_isoFirmado", isoFirmado.substring(isoFirmado.indexOf("_-*#")+4));
						retiroDto.setDatosFirmas(datosFirma);
						
						admonRetiroEfectivo.crearRetiro(retiroDto);

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
	private Object getValor(List<CampoArchivoVO> campos, LayoutRB campo) {
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

	public EstadoInstruccionDaliDAO getEstadoInstruccionDaliDAO() {
		return estadoInstruccionDaliDAO;
	}

	public void setEstadoInstruccionDaliDAO(
			EstadoInstruccionDaliDAO estadoInstruccionDaliDAO) {
		this.estadoInstruccionDaliDAO = estadoInstruccionDaliDAO;
	}

	public AdmonRetirosEfectivoService getAdmonRetiroEfectivo() {
		return admonRetiroEfectivo;
	}

	public void setAdmonRetiroEfectivo(
			AdmonRetirosEfectivoService admonRetiroEfectivo) {
		this.admonRetiroEfectivo = admonRetiroEfectivo;
	}
}

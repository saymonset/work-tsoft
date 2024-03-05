package com.indeval.portaldali.presentation.controller.mercadodinero.fileTransfer.encoladores;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.seguridad.presentation.constants.SeguridadConstants;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.services.common.ConsultaInstitucionService;
import com.indeval.portaldali.middleware.services.efectivo.MovimientosEfectivoService;
import com.indeval.portaldali.middleware.services.filetransfer.CampoArchivoVO;
import com.indeval.portaldali.middleware.services.filetransfer.RegistroProcesadoVO;
import com.indeval.portaldali.middleware.services.filetransfer.TotalesProcesoVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import com.indeval.portaldali.middleware.services.util.FechasUtilService;
import com.indeval.portaldali.middleware.services.util.UtilServices;
import com.indeval.portaldali.middleware.services.util.util.Constantes;
import com.indeval.portaldali.presentation.common.constants.TesoreriaConstantes;
import com.indeval.portaldali.presentation.controller.mercadodinero.fileTransfer.IEncoladorProtocoloFinanciero;
import com.indeval.portaldali.presentation.helper.IsoHelper;
import com.indeval.protocolofinanciero.api.ProtocoloFinancieroException;
import com.indeval.protocolofinanciero.api.vo.TraspasoEfectivoVO;

public class EncoladorTref implements IEncoladorProtocoloFinanciero {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
    /** Servicio para grabar las operaciones */
	private MovimientosEfectivoService movimientosEfectivoService;
	/** Servicio para grabar las operaciones */
	private ConsultaInstitucionService consultaInstitucionService;
	
	private FechasUtilService fechasUtilService;
	/** Servicio para obtener constantes */
	private UtilServices utilService;

	/** Ayudante para la generacion de las cadenas ISO que deberan ser firmadas */
	protected IsoHelper isoHelper = null;
    

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.presentation.controller.mercadodinero.fileTransfer.IEncoladorProtocoloFinanciero#obtenerOperacionesFirmadas(com.indeval.portaldali.middleware.services.filetransfer.TotalesProcesoVO)
	 */
	public Map<String, Object> obtenerISOs(TotalesProcesoVO totProcVO) {
		@SuppressWarnings("unchecked")
        final List<RegistroProcesadoVO> l = totProcVO.getPaginaVO().getRegistros();
		
		final List<TraspasoEfectivoVO> listaObjetos = new ArrayList<TraspasoEfectivoVO>();
		final List<String> resultados = new ArrayList<String>();
		
		final Map<String, Object> map = new HashMap<String, Object>();
		
		for (int i = 0; i < l.size(); i++) {
		    final RegistroProcesadoVO reg = (RegistroProcesadoVO) l.get(i);
		    
            // El registro no tiene errores
            if (!reg.getEstado().trim().equals("ER")) {
                // Esta lista contiene cada uno de los campos que conforman el registro
            	@SuppressWarnings("unchecked")
                List<CampoArchivoVO> campos = reg.getDatos();
                
            	// Traspasante
            	String ordenante=((String)campos.get(0).getValor()).trim();
            	// beneficiario
            	String beneficiario=((String)campos.get(1).getValor()).trim();
            	// Importe
            	Object valorImporte = campos.get(2).getValor();
            	BigDecimal importe = (valorImporte instanceof BigDecimal)? (BigDecimal)campos.get(2).getValor():
            		(valorImporte instanceof String) && StringUtils.isNotBlank((String)valorImporte)?new BigDecimal((String)valorImporte):null;
            	
            	// Divisa
            	String claveDivisa=((String)campos.get(3).getValor()).trim();
            	// Boveda Efectivo
            	String boveda=((String)campos.get(4).getValor()).trim();
            	
            	logger.debug(boveda);
            	InstitucionDTO institucionOrdenante = 
            			consultaInstitucionService.buscarInstitucionPorClaveYFolio(ordenante);
            	InstitucionDTO institucionBeneficiario = 
            			consultaInstitucionService.buscarInstitucionPorClaveYFolio(beneficiario);
            	Date currentDate = fechasUtilService.getCurrentDate();
            	
            	TraspasoEfectivoVO traspasoEfectivoVo = new TraspasoEfectivoVO();            	
            	traspasoEfectivoVo.setOrdenante(institucionOrdenante.getNombreCorto());
				traspasoEfectivoVo.setCuentaOrdenante(institucionOrdenante.getCuentaClabe());
            	traspasoEfectivoVo.setBeneficiario(institucionBeneficiario.getNombreCorto());
				traspasoEfectivoVo.setCuentaBeneficiaria(institucionBeneficiario.getCuentaClabe());
				traspasoEfectivoVo.setFechaRegistro(currentDate);
				traspasoEfectivoVo.setFechaLiquidacion(currentDate);
				traspasoEfectivoVo.setMonto(importe);
	            traspasoEfectivoVo.setTipoInstruccion(TesoreriaConstantes.TIPO_INSTRUCCION__TRASPASO_EFECTIVO );
	            traspasoEfectivoVo.setReferenciaMensaje(utilService.getFolio(Constantes.SEQ_REFERENCIA_MENSAJE).toString());
	            traspasoEfectivoVo.setReferenciaOperacion(utilService.getFolio(Constantes.SECUENCIA_FOLIO_CONTROL).toString());
	            traspasoEfectivoVo.setDivisa(claveDivisa);
	            traspasoEfectivoVo.setBovedaTraspasante(boveda);
	            traspasoEfectivoVo.setBoveda(boveda);

	            //
            	resultados.add(isoHelper.creaISO(traspasoEfectivoVo, false).replace("\r\n", "\n"));
                listaObjetos.add(traspasoEfectivoVo);
           	}
        }
		logger.info(ID_LISTA_ISOS + resultados.size());
		map.put(ID_LISTA_ISOS, resultados);
		map.put(ID_LISTA_OBJETOS, listaObjetos);
		
		return map;
	}
	
	
	public Map<BigInteger, Integer> firmayEncola(final TotalesProcesoVO totProcVO, final List<Object> listaObjetos, final List<String> isosFirmados) throws BusinessException,
	ProtocoloFinancieroException {
		return firmayEncola(totProcVO, listaObjetos, isosFirmados, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.presentation.controller.mercadodinero.fileTransfer.IEncoladorProtocoloFinanciero#firmayEncola(com.indeval.portaldali.middleware.services.filetransfer.TotalesProcesoVO,
	 *      java.util.List, java.util.List)
	 */
	public Map<BigInteger, Integer> firmayEncola(final TotalesProcesoVO totProcVO, final List<Object> listaObjetos, final List<String> isosFirmados, Map<String, ?> datosAdicionales) throws BusinessException,
			ProtocoloFinancieroException {
		Map<BigInteger, Integer> mpRegistrosEncolados = new HashMap<BigInteger, Integer>();

		PaginaVO pag = totProcVO.getPaginaVO();
		List<?> l = pag.getRegistros();
		RegistroProcesadoVO reg;
		int indiceObjetoAProcesar = 0;
		
		Map<String, String> datosFirma = new HashMap<>();
		datosFirma.put(SeguridadConstants.USR_CREDENCIAL, (String)datosAdicionales.get(SeguridadConstants.USR_CREDENCIAL));
		datosFirma.put(SeguridadConstants.USUARIO_SESION, (String)datosAdicionales.get(SeguridadConstants.USUARIO_SESION));
		datosFirma.put(SeguridadConstants.SERIE, (String)datosAdicionales.get(SeguridadConstants.SERIE));
		
		
		for (int i = 0; i < l.size();i++) {
			reg = (RegistroProcesadoVO) l.get(i);		
			// El registro no tiene errores
			if (!reg.getEstado().trim().equals("ER")) {			
				
				if(listaObjetos != null && !listaObjetos.isEmpty() &&  indiceObjetoAProcesar < listaObjetos.size()) {
				
					mpRegistrosEncolados.put(reg.getConsec(), Integer.valueOf(1));
					
                    try {
                    	if(isosFirmados!=null && indiceObjetoAProcesar < isosFirmados.size()) {
                    		datosFirma.put(SeguridadConstants.ISO_FIRMADO, isosFirmados.get(indiceObjetoAProcesar));
                    	}
                    	
                    	movimientosEfectivoService.guardarOperacion((TraspasoEfectivoVO)listaObjetos.get(indiceObjetoAProcesar), datosFirma);
                    	
                    } catch (Exception e) {
                    	e.printStackTrace();
						mpRegistrosEncolados.put(reg.getConsec(), Integer.valueOf(0));
						logger.error(e.getMessage(), e);
					}
				
					indiceObjetoAProcesar++;
				}
			}
		}

		return mpRegistrosEncolados;
	}

    

	
	public void setMovimientosEfectivoService(MovimientosEfectivoService movimientosEfectivoService) {
		this.movimientosEfectivoService = movimientosEfectivoService;
	}


	/**
	 * @param isoHelper the isoHelper to set
	 */
	public void setIsoHelper(IsoHelper isoHelper) {
		this.isoHelper = isoHelper;
	}

	/**
	 * @param utilService the utilService to set
	 */
	public void setUtilService(UtilServices utilService) {
		this.utilService = utilService;
	}


	public void setConsultaInstitucionService(ConsultaInstitucionService consultaInstitucionService) {
		this.consultaInstitucionService = consultaInstitucionService;
	}


	public void setFechasUtilService(FechasUtilService fechasUtilService) {
		this.fechasUtilService = fechasUtilService;
	}
}

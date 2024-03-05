package com.indeval.portaldali.presentation.controller.mercadodinero.fileTransfer.encoladores;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.DepositoDivisaDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService;
import com.indeval.portaldali.middleware.services.filetransfer.CampoArchivoVO;
import com.indeval.portaldali.middleware.services.filetransfer.RegistroProcesadoVO;
import com.indeval.portaldali.middleware.services.filetransfer.TotalesProcesoVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import com.indeval.portaldali.middleware.services.util.UtilServices;
import com.indeval.portaldali.persistence.dao.common.BovedaDaliDAO;
import com.indeval.portaldali.persistence.dao.common.DivisaDaliDAO;
import com.indeval.portaldali.presentation.controller.mercadodinero.fileTransfer.IEncoladorProtocoloFinanciero;
import com.indeval.portaldali.presentation.helper.IsoHelper;
import com.indeval.protocolofinanciero.api.ProtocoloFinancieroException;

public class EncoladorDepositosEfectivo implements IEncoladorProtocoloFinanciero {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
    /** Servicio para grabar las operaciones */
	private EnvioOperacionesService envioOperacionesService;
	
	/** Servicio para obtener constantes */
	private UtilServices utilService;

	/** Ayudante para la generacion de las cadenas ISO que deberan ser firmadas */
	protected IsoHelper isoHelper = null;

    private Map<String, Object> datosAdicionales;

	private boolean firmaRequerida;
    

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.presentation.controller.mercadodinero.fileTransfer.IEncoladorProtocoloFinanciero#obtenerOperacionesFirmadas(com.indeval.portaldali.middleware.services.filetransfer.TotalesProcesoVO)
	 */
	public Map<String, Object> obtenerISOs(TotalesProcesoVO totProcVO) {
		@SuppressWarnings("unchecked")
        final List<RegistroProcesadoVO> l = totProcVO.getPaginaVO().getRegistros();
		final List<DepositoDivisaDTO> listaObjetos = new ArrayList<DepositoDivisaDTO>();
		final List<String> resultados = new ArrayList<String>();
		final Map<String, Object> map = new HashMap<String, Object>();
		
		for (int i = 0; i < l.size(); i++) {
		    final RegistroProcesadoVO reg = (RegistroProcesadoVO) l.get(i);
            // El registro no tiene errores
            if (!reg.getEstado().trim().equals("ER")) {
                // Esta lista contiene cada uno de los campos que conforman el registro
            	@SuppressWarnings("unchecked")
                List<CampoArchivoVO> campos = reg.getDatos();
                
                // Revisar en cada uno de los campos hasta encontrar el tipo de operacion
                
            		String casfim=(String)campos.get(0).getValor();//idfolio
            		String importe=(String)campos.get(1).getValor();
            		
            		//revisa que en la tabla c_divisa Exista ese valor que se le pasa y devuelve el id si lo encuentra            		
            		long divisaNum=obtieneIdDivisa((String)campos.get(2).getValor());
            		
            		//revisa que en la tabla c_boveda tenga el dato que se le pasa y devuelve el id si lo encuentra
            		long bovedaNum=obtieneIdBoveda(((String)campos.get(3).getValor()).trim());
            		/*
            		 *  se agrego los 2 nuevos campos Divisa y Boveda_Efectivo(NombreCorto)
            		 */
            		                	
                	final DepositoDivisaDTO depositoDivisa = new DepositoDivisaDTO();            		
            		depositoDivisa.setCasfim(casfim);
            		depositoDivisa.setIdBoveda ( BigInteger.valueOf(bovedaNum));//( BigInteger.valueOf(11));//
            		depositoDivisa.setIdDivisa (BigInteger.valueOf(divisaNum));//( BigInteger.ONE); //
            		//campos agregados divisa y nombre-corto = bovedaEfectivo
            		depositoDivisa.setDivisa((String)campos.get(2).getValor() );
            		depositoDivisa.setNombreCorto(((String)campos.get(3).getValor()).trim());
            		depositoDivisa.setImporte( new BigDecimal(importe.trim())); //long u=( BigInteger.ONE);
            		depositoDivisa.setSaldo(BigDecimal.ZERO);
            		resultados.add(isoHelper.creaISO(depositoDivisa, false).replace("\r\n", "\n"));
                    listaObjetos.add(depositoDivisa);
           	}
        }
			
		map.put(ID_LISTA_ISOS, resultados);
		map.put(ID_LISTA_OBJETOS, listaObjetos);
		
		return map;
	}
	//se agrego este metodo para que vaya a la c_boveda y traiga el BovedaDTO y devuelva el id_Boveda
	private long obtieneIdBoveda(String bovedaEfectivo){	
		
		BovedaDTO bovedaDTO=utilService.buscarBovedaPorTipoCustodia(bovedaEfectivo);			
		return bovedaDTO.getId();
	}
	//se agrego este met para k vaya a la c_divisa y traiga el divisaDTO y devuelva el id_Divisa
	private long obtieneIdDivisa(String divisaDato){
		if(divisaDato!= null){
			DivisaDTO divisa=utilService.obtenerDivisaPorClaveAlfavetica(divisaDato);
		
		return divisa.getId();
		}
		return 0;
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
		Map<BigInteger, Integer> mpRegistrosEncolados = new HashMap<BigInteger, Integer>();

		PaginaVO pag = totProcVO.getPaginaVO();
		List<?> l = pag.getRegistros();
		RegistroProcesadoVO reg;
		int indiceObjetoAProcesar = 0;
//se agrego el contadorDatosFirmados 		
		int contadorDatosFirmados = 0;
		for (int i = 0; i < l.size();i++) {
			reg = (RegistroProcesadoVO) l.get(i);		
			// El registro no tiene errores
			if (!reg.getEstado().trim().equals("ER")) {			
				
				if(listaObjetos != null && !listaObjetos.isEmpty() &&  indiceObjetoAProcesar < listaObjetos.size()) {
				
					mpRegistrosEncolados.put(reg.getConsec(), new Integer(1));
					
                    try {
                    	if( this.firmaRequerida){
                        envioOperacionesService.grabaOperacion(
                                (DepositoDivisaDTO) listaObjetos
                                        .get(indiceObjetoAProcesar), getDatosAdicionales(),
                                isosFirmados.get(contadorDatosFirmados));
//se buscan los registros firmados y se incrementa el contador
                        contadorDatosFirmados++;
                    	}else{
                    		envioOperacionesService.grabaOperacion(
                                    (DepositoDivisaDTO) listaObjetos
                                            .get(indiceObjetoAProcesar), getDatosAdicionales(),
                                    null);
                    	}
                    } catch (Exception e) {
                    	e.printStackTrace();
						mpRegistrosEncolados.put(reg.getConsec(), new Integer(0));
						logger.error("", e);
					}
				
					indiceObjetoAProcesar++;
				}
			}
		}

		return mpRegistrosEncolados;
	}

    public Map<String, Object> getDatosAdicionales() {
        return datosAdicionales;
    }

    public void setDatosAdicionales(final Map<String, Object> datosAdicionales) {
        this.datosAdicionales = datosAdicionales;
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
//	private Object getValor(List<CampoArchivoVO> campos, LayoutMC campo) {
//		Object valor = campos.get(campo.getPosicion()).getValor();
//		return valor;
//	}

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

	public void setFirmaRequerida(boolean firmaRequerida) {
		this.firmaRequerida = firmaRequerida;
	}

}

/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * Agosto 28, 2008
 */
package com.indeval.portalinternacional.presentation.controller.fileTransfer.encoladores;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.servicios.vo.TotalesProcesoVO;
import com.indeval.protocolofinanciero.api.ProtocoloFinancieroException;
import com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO;

/**
 * Interfaz para encoladores
 * 
 * @author Esteban Herrera
 *
 */
public interface IEncoladorProtocoloFinanciero {
	
	/** Id del mapa del metodo de obtenerISOs con el que se guardan los ISOS */
	String ID_LISTA_ISOS = "isos";
	
	/** Id del mapa del metodo de obtenerISOs con el que se guardan los objetos */
	String ID_LISTA_OBJETOS = "objetos";
    	
    /**
     * 
     * @param totProcVO
     * @param listaTLPs
     * @param isosFirmados
     * @return
     * @throws BusinessException
     * @throws ProtocoloFinancieroException
     */
    public Map<BigInteger, Integer> firmayEncola( TotalesProcesoVO totProcVO, List lista, List<String> isosFirmados, String ticket) throws BusinessException, ProtocoloFinancieroException ;
    
    /**
     * 
     * @param totProcVO
     * @return
     */
    public Map<String, Object> obtenerISOs(TotalesProcesoVO totProcVO);
    
}

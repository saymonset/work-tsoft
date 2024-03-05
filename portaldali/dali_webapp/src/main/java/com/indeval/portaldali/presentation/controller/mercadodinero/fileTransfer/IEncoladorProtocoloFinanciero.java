package com.indeval.portaldali.presentation.controller.mercadodinero.fileTransfer;



import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.indeval.portaldali.middleware.services.filetransfer.TotalesProcesoVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.protocolofinanciero.api.ProtocoloFinancieroException;

public interface IEncoladorProtocoloFinanciero {
	
	/** Id del mapa del metodo de obtenerISOs con el que se guardan los ISOS */
	String ID_LISTA_ISOS = "isos";
	
	/** Id del mapa del metodo de obtenerISOs con el que se guardan los objetos */
	String ID_LISTA_OBJETOS = "objetos";
    
    /**
     * @param totProcVO 
     * @return contiene los consecutivos como String de los registros que se enviaron a protocolo
     */
    public Map<BigInteger, Integer> firmayEncola( TotalesProcesoVO totProcVO, List<Object> listaObjetos, List<String> isosFirmados ) throws BusinessException, ProtocoloFinancieroException ;
    
    public Map<String, Object> obtenerISOs(TotalesProcesoVO totProcVO);
    
}

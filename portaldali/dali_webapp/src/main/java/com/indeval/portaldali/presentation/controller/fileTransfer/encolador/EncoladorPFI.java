/**
 * Bursatec - Portal DALI
 * Copyrigth (c) 2014 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.presentation.controller.fileTransfer.encolador;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.protocolofinanciero.api.ProtocoloFinancieroException;

/**
 * Clase abstracta que define los métodos para realizar el encolamiento de las operaciones hacia
 * el PFI.
 * 
 * @author Pablo Balderas
 *
 */
public abstract class EncoladorPFI {

	/** Id del mapa del metodo de obtenerISOs con el que se guardan los ISOS */
	public static final String ID_LISTA_ISOS = "isos";
	
	/** Id del mapa del metodo de obtenerISOs con el que se guardan los objetos */
	public static final String ID_LISTA_OBJ_PFI = "tlps";
	
	/** Id para la lista con los registros de file transfer */
	public static final String ID_LISTA_REGISTROS = "registrosFT";

	/**
	 * Método que obtiene las cadenas ISO de los registros de file transfer a enviar al PFI.
	 * @param idInstitucion Id de la institución firmada.
	 * @param folioInstitucion Folio de la institución firmada.
	 * @param tipoFileTransfer Tipo de file transfer que se esta ejecutando.
	 * @return Mapa con los ISO's de las operaciones a enviar al PFI.
	 */
	public abstract Map<String, Object> obtenerISOs(String idInstitucion, String folioInstitucion, String tipoFileTransfer);
    
	/**
	 * Método que firma y encola las operaciones de file transfer a enviar al PFI.
	 * @param listaObjetos Objetos a partir de los cuales se crearon los isos.
	 * @param isosFirmados Isos firmados.
	 * @param registrosFT Registros del file transfer.
	 * @param datosAdicionales Datos adicionales para el encolamiento.
	 * @return Mapa que indica cuales registros fueron procesados por el PFI.
	 * @throws BusinessException En caso de ocurrir un error de negocio.
	 * @throws ProtocoloFinancieroException En caso de ocurrir un error en el PFI.
	 */
    public abstract Map<Integer, Boolean> firmayEncola(
    		List<Object> listaObjetos, 
    		List<String> isosFirmados,
    		List<Object> registrosFT,
    		HashMap<String, Object> datosAdicionales) 
    		throws BusinessException, ProtocoloFinancieroException ;

}

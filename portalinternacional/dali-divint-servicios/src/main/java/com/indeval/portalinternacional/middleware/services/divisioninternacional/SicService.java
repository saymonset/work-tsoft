/**
 * CMMV - Portal Internacional
 * Copyrigth (c) 2016 CMMV. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.math.BigInteger;
import java.util.Date;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.servicios.modelo.Multidivisa;
import com.indeval.portalinternacional.middleware.servicios.modelo.OperacionSic;

/**
 * Interfaz que define los metodos de negocio utilizados por el SIC.
 * 
 * @author Pablo Balderas
 */
public interface SicService {

	/**
	 * Actualiza la informacion de una operacion del SIC
	 * @param indice Indice del registro que se esta procesando
	 * @param operacionSic Objeto a ser actualizado
	 * @param operacionSicActual Objeto con los valores actuales
	 * @param fechaActual Fecha Actual del sistema
	 */
	void actualizarOperacionSic(int indice, OperacionSic operacionSic, OperacionSic operacionSicActual, Date fechaActual) throws BusinessException;

	/**
	 * Valida si la contraoperacion de una operacion SIC de Cambio de Boveda; es decir que si es una Entrega se tiene 
	 * que validar que su contra Recepcion se encuentre en CONFIRMADA y por el contraio si es una Recepcion se tiene 
	 * que validar que su contra Entrega se encuentre en LIBERADA. 
	 * @param operacionSic La operacion a validar su contra operacion.
	 * @return
	 * @throws BusinessException
	 */
	boolean esValidoEstadoAdecuadoDeContraoperacion(OperacionSic operacionSic) throws BusinessException;
	
	/**
	 * Metodo para obtener una operacion SIC por su FolioControl
	 * @param folioControl
	 * @return
	 */
	public OperacionSic getOperacionSicByFolioControl(BigInteger folioControl);
	
    /**
     * Metodo para cancelar remanente de una operacion sic
     * @param operacionSic
     * @param fechaActual
     * @return
     * @throws BusinessException
     */
    public void cancelaParcialidadOperacionSIC(OperacionSic operacionSic, Date fechaActual, Long parcialidad) throws BusinessException;
    
    /**
     * Elimina un registro en la tabla T_CONTROL_LIBERACION_INT.
     * @param idOperacionSic
     * @param folioControl
     */
    void eliminarRegistroControlLiberacionParcialesRecepciones(long idOperacionSic, long folioControl);

	// Cambio Multidivisas
	/**
	 * Envia a MOI mensaje de notificacion de cambio de estado de Movimiento de Efectivo en Divisas Extranjeras
	 * @param xmlEnviar Objeto con info del movimiento efectivo y su cambio de estado
	 */
	void notificaCambioEstadoMovEfeDivInt(String xmlEnviar);

    String crearXML(Multidivisa multidivisa);

    /**
     * Multidivisas
     * Envia a MOI mensaje de notificacion de cambio de estado de Movimiento de Efectivo en Divisas Extranjeras
     * @param multidivisa Objeto con info del movimiento efectivo y su cambio de estado
     */
    void notificaCambioEstadoMovEfeDivInt(Multidivisa multidivisa);

	// Fin Cambio Multidivisas
}

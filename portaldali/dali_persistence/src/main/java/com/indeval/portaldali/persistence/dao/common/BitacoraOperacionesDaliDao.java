/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.common;

import java.util.List;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import com.indeval.portaldali.persistence.model.BitacoraOperaciones;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public interface BitacoraOperacionesDaliDao extends BaseDao {
	
	/* Constantes para BitacoraOperaciones */
    
    /** Define el titulo del estatus NO ENVIADO de BitacoraOperaciones */
    static final String NO_ENVIADA = "NE";
    
    /** Define el titulo del estatus ENVIADO de BitacoraOperaciones */
    static final String ENVIADA = "EN";
    
    /** Define el titulo del estatus ACK de BitacoraOperaciones */
    static final String ACKNOWLEGDE = "ACK";
    
    /** Define el titulo del estatus LIQUIDADO (Portal) de BitacoraOperaciones */
    static final String LIQUIDADA_PORTAL = "LIQ";
    
    /** Define el titulo del estatus LIQUIDADO (Savar) de BitacoraOperaciones */
    static final String LIQUIDADA_SAVAR = "LQ";
    
    /** Define el titulo del estatus FALTANTE DE VALORES de BitacoraOperaciones */
    static final String FALTANTE_VALORES = "FV";
    
    /** Define el titulo Del estatus NAK de BitacoraOperaciones */
    static final String NOT_ACKNOWLEGDE = "NAK";
    
    /** Define el titulo del total del importe para operaciones de efectivo de BitacoraOperaciones */
    static final String TOTAL_IMPORTE = "totalImporte";
    
    /** Define el titulo del total del importe para operaciones de efectivo de BitacoraOperaciones */
    static final String TOTAL_TITULOS = "totalTitulos";


	/**
     * Realiza el merge de BitacoraOperaciones
     *  
	 * @param bitacoraOperaciones
	 * @return BitacoraOperaciones
	 */
	public BitacoraOperaciones merge(BitacoraOperaciones bitacoraOperaciones);
	
	/**
     * Obtiene las operaciones correspondientes a los criterios de indicados
     * en el parametro BitacoraOperaciones, regresa una lista paginada
     *
     * @param bitacoraOperaciones
     * @param paginaVO 
     * @return PaginaVO
     */
    PaginaVO getBitacoras(BitacoraOperaciones bitacoraOperaciones, PaginaVO paginaVO);
}

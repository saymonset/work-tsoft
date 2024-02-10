/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.dao;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.BitacoraOperacionesSic;
import com.indeval.portalinternacional.middleware.servicios.modelo.OperacionSic;
import com.indeval.portalinternacional.middleware.servicios.vo.HistorialBitacoraOperacionesSICDTO;

/**
 * @author javiles
 *
 */
public interface OperacionSicDao extends BaseDao {

    /**
     * Obtiene las operaciones del SIC
     * @param operacionSic 
     * @param paginaVO 
     * @param obtenerTotalReg
     * 
     * @return PaginaVO
     */
    public PaginaVO findOperaciones(OperacionSic operacionSic, PaginaVO paginaVO, Boolean obtenerTotalReg);
    
    /**
     * Obtiene una instancia de OperacionSic por folio control
     * 
     * @param folioControl
     * @return OperacionSic
     */
    public OperacionSic findOperacionByFolioControl(BigInteger folioControl);

    /**
     * Obtiene las operaciones del SIC con un listado de folios de control.
     * @param foliosControl 
     * @param paginaVO 
     * 
     * @return PaginaVO
     */
    public PaginaVO findOperacionesPorListaDeFolioControl(List<BigInteger> foliosControl, PaginaVO paginaVO);
    
    /**
     * Obtiene una instancia de OperacionSic por id de la operacion
     * 
     * @param idOperacion
     * @return OperacionSic
     */
    public OperacionSic findOperacionByIdOperacion(Long idOperacion);
    
    
    public List<String> findCustodiosinOperacionSic();
    
    public List<BitacoraOperacionesSic> obtieneBitacoraOperacionSic(List<BigInteger> foliosControl);
    
    public List<BitacoraOperacionesSic> obtieneBitacoraOperacionSic(Long folioControl, Long numeroLiquidacion);
    
    public PaginaVO obtenerHistorialBitacoraOperacionesSic(HistorialBitacoraOperacionesSICDTO hist, PaginaVO paginaVO, boolean esParaCambioDeBoveda);

    /**
     * Obtiene las operaciones de Entrega correspondiente en base a los parametros recibidos.
     * @param idEmision
     * @return
     */
    List<OperacionSic> obtenerOperacionDeEntrega(Long idEmision);

    /**
     * Obtiene el listado de las contraoperaciones de Recepcion de una operacion de Entrega especifica.
     * @param idEmision
     * @return
     */
    List<OperacionSic> getContraoperacionRecepcionEstadoAdecuado(Long idEmision);

    /**
     * Obtiene el listado de las contraoperaciones de Entrega de una operacion de Recepcion especifica.
     * @param idEmision
     * @return
     */
    List<OperacionSic> getContraoperacionEntregaEstadoAdecuado(Long idEmision);
    
    /**
     * Busca una operacion SIC de cambio de boveda en transito.
     * @param idEmision Id de la emision involucrada.
     * @return Operacion Sic en transito o nulo si no hay operaciones en transito.
     */
    List<OperacionSic> findOperacionSicCambioBovedaEnTransito(Long idEmision);

    /**
     * Busca si existe una operacion SIC de cambio de boveda en transito por el id de la emision y el tipo del mensaje
     * @param idEmision Id emision
     * @param tipoMensaje Tipo mensaje
     * @return Lista de operaciones en transito o nulo si no hay
     */
    List<OperacionSic> findOperacionSicCambioBovedaEnTransito(Long idEmision, String tipoMensaje);

    /**
     * Elimina un registro en la tabla T_CONTROL_LIBERACION_INT.
     * @param idOperacionSic
     * @param folioControl
     */
    void eliminarRegistroControlLiberacionRecepciones(long idOperacionSic, long folioControl);

    /**
     * Obtiene el estado actual y la version mas actual de una operacion sic
     * @param idOpSic El id de la operacion sic
     * @return Los datos en un objeto List
     */
    List<Object> getDataToCompareForUpdate(Long idOpSic);
    
    /**
     * Metodo para obtener operaciones parciales: Confirmada Parcial, Liberada Parcial
     * @param idCuentaNombrada
     * @return
     */
    public Long findOperacionesParcialesPendientes(Long idCuentaNombrada );

}

/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.common;

import java.math.BigInteger;
import java.util.List;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portaldali.middleware.dto.PosicionDTO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.persistence.model.PosicionNombrada;
import com.indeval.portaldali.persistence.vo.PageVO;
import com.indeval.portaldali.persistence.vo.TPosicionNombradaParamsPersistence;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public interface PosicionNombradaDaliDao extends BaseDao {

    /**
     * @param tPosicionNombradaParamsPersistence
     * @return PageVO
     */
    PageVO getDetalleEmisiones(
    		TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence);
    
    /**
     * @param tv
     * @return List
     */
    List getEmisoraByTV(final String tv);

    /**
     * @param tPosicionNombradaParamsPersistence
     * @return PageVO
     */
    PageVO getTPosicionNombrada(
    		TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence);
    
    /**
     * Metodo que regresa las posiciones nombradas haciendo un left outer join
     * con los historicos para obtener saldo inicial
     * 
     * @param tPosicionNombradaParamsPersistence
     * @return PageVO
     */
    PageVO getTPosicionNombradaSaldoInicial(
    		TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence);

    /**
     * @param tPosicionNombradaParamsPersistence
     * @return PageVO
     */ 
    PageVO getTPosicionNombradaByAgente(
    		TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence);

    /**
     * @param tPosicionNombradaParamsPersistence
     * @return List
     */
    List getTPosicionNombradaByCuenta(
    		TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence);
    
    /**
     * @param tPosicionNombradaParamsPersistence
     * @return List[]
     */
    List[] getTPosicionNombradaByCuentas(
    		TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence);

    /**
     * @param tPosicionNombradaParamsPersistence
     * @return PageVO
     */
    PageVO getTPosicionNombradaByExample(
    		TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence);

    /**
     * @param tPosicionNombradaParamsPersistence
     * @return PageVO
     */
    PageVO getTPosicionNombradaByIdTipoValor(
    		TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence);

    /**
     * @param tPosicionNombradaParamsPersistence
     * @return PageVO
     */
    PageVO getTPosicionNombradaByInstitucionEdoCtaUnico(
            TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence);

    /**
     * @param tPosicionNombradaParamsPersistence
     * @return PageVO
     */
    PageVO getTPosicionNombradaCapitales(
            TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence);

    /**
     * @param tPosicionNombradaParamsPersistence
     * @return PageVO
     */
    PageVO getTPosicionNombradaDivisionInternacional(
            TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence);

    /**
     * @param tPosicionNombradaParamsPersistence
     * @return PosicionNombrada
     */
    PosicionNombrada getTPosicionNombradaFileTransferMC(
    		TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence);

    /**
     * @param tPosicionNombradaParamsPersistence
     * @return PageVO
     */
    PageVO getTPosicionNombradaGarantias(
    		TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence);

    /**
     * @param tPosicionNombradaParamsPersistence
     * @return PageVO
     */
    PageVO getTPosicionNombradaPorEjercicioDivisionInternacional(
    		TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence);
    
    /**
     * @param tPosicionNombradaParamsPersistence
     * @return PageVO
     */
    PageVO getTPosicionNombradaValpreE(
    		TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence);

    /**
     * @param tPosicionNombradaParamsPersistence
     * @return PageVO
     */
    PageVO getTPosicionNombradaValpreEAdmonG(
    		TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence);
    
//    /**
//     * @param tPosicionNombradaParamsPersistence
//     * @return PageVO - contiene una lista de Object[]
//     */
//    PageVO getVencimientosPendientesByInstitucionFechaVencimiento(
//            TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence);

    /**
     * Regresa la posicion inicial de una posicion nombrada, es decir, la 
     * posicion del dia habil anterior
     * 
     * @param tPosicionNombradaParamsPersistence
     * @return el saldo inicial
     */
    public BigInteger getSaldoInicial(final TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence);
    
    /**
     * Obtiene el saldo actual de la posicion indicada
     * 
     * @param tPosicionNombradaParamsPersistence
     * @return El saldo actual de ls posicion indicada
     */
    public BigInteger getSaldoActual(TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence);
    
    /**
     * Obtiene la lista de posiciones con saldo mayor a cero para una institucion y
     * emision en especifico.
     * 
     * @return 	la lista de Posiciones Nombradas con saldo mayor a cero de esa 
     * 			institucion para esa emision
     */
    public List<PosicionDTO> getCuentasParaFondeoMatch(AgenteVO agenteFirmado, EmisionVO emision);

}

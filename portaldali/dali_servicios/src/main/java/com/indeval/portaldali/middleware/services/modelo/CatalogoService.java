/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.modelo;

import java.math.BigInteger;
import java.util.Date;

/**
 * Interface para los servicios de Catalogo
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public interface CatalogoService {

	/**
	 * @param agenteVO
	 * @param fechaVencimiento
	 * @param paginaVO
	 * @return PaginaVO
	 * @throws BusinessException
	 */
	PaginaVO getVencimientosPendientesByInstitucionFechaVencimientoAgurpadosPorEmision(AgenteVO agenteVO, 
	        Date fechaVencimiento, BigInteger idBoveda, PaginaVO paginaVO, Boolean debeDejarLog) throws BusinessException;

	/**
	 * Obtiene el nombre de la emisora que corresponde al tv proporcionado, de
	 * la tabla catalogo..emisiones
	 * 
	 * @param tv
	 * @return String
	 * @throws BusinessException
	 */
	String getEmisoraByTVDinero(String tv) throws BusinessException;

	/**
	 * CUMD43-09 : Lista de Emisiones Muestra todas las emisiones de un Agente
	 * de acuerdo a los filtros proporcionados sin importar si el saldo
	 * disponible es 0. Solo para Mercado de Dinero.
	 * 
	 * @param agenteVO
	 *            (Requerido)
	 * @param emisionVO
	 *            (Requerido)
	 * @return EmisionVO[]
	 * @throws BusinessException
	 */
	EmisionVO[] getListaEmisionesDinero(AgenteVO agenteVO, EmisionVO emisionVO, BigInteger idBoveda) 
	        throws BusinessException;

	/**
	 * CUMD43-09 : Lista de Emisiones Muestra todas las emisiones de un Agente
	 * de acuerdo a los filtros proporcionados sin importar si el saldo
	 * disponible es 0. Solo para Mercado de Dinero.
	 * 
	 * @param agenteVO
	 * @param emisionVO
	 * @param paginaVO
	 * @return PaginaVO
	 * @throws BusinessException
	 */
	PaginaVO getListaEmisionesDinero(AgenteVO agenteVO, EmisionVO emisionVO, 
	        BigInteger idBoveda, PaginaVO paginaVO) throws BusinessException;

	/**
	 * Recupera la lista de emisiones relacionadas a los servicios de valpre-e
	 * 
	 * @param emisionVO
	 * @param agenteVO
	 * @return EmisionVO[]
	 * @throws BusinessException
	 */
	EmisionVO[] getListaEmisionesValpreE(EmisionVO emisionVO, AgenteVO agenteVO, 
	        BigInteger idBoveda) throws BusinessException;

	/**
	 * Recupera la lista de emisiones relacionadas a los servicios de valpre-e
	 * 
	 * @param emisionVO
	 * @param agenteVO
	 * @param paginaVO
	 * @return PaginaVO
	 * @throws BusinessException
	 */
	PaginaVO getListaEmisionesValpreE(EmisionVO emisionVO, AgenteVO agenteVO, 
	        BigInteger idBoveda, PaginaVO paginaVO) throws BusinessException;

	/**
	 * CUMD43-09 : Lista de Emisiones (Valpre-E) Muestra todas las emisiones
	 * correspondientes a la cuenta propia de un Agente
	 * 
	 * @param agenteVO
	 *            (Requerido: id, folio)
	 * @param emisionVO
	 * @return EmisionVO[]
	 * @throws BusinessException
	 */
	EmisionVO[] getListaEmisionesValpreEAdmonG(AgenteVO agenteVO, EmisionVO emisionVO, 
	        BigInteger idBoveda) throws BusinessException;

	/**
	 * CUMD43-09 : Lista de Emisiones (Valpre-E) Muestra todas las emisiones
	 * correspondientes a la cuenta propia de un Agente
	 * 
	 * @param agenteVO
	 * @param emisionVO
	 * @param paginaVO
	 * @return PaginaVO
	 * @throws BusinessException
	 */
	PaginaVO getListaEmisionesValpreEAdmonG(AgenteVO agenteVO, EmisionVO emisionVO,
	        BigInteger idBoveda, PaginaVO paginaVO) throws BusinessException;

	/**
	 * CUMD43-09 : Lista de Emisiones Wrapper del metodo
	 * getListaEmisiones(EmisionVO emisionVO, Boolean mercado, boolean lazy) que
	 * hace una llamada interna a este, con el parametro lazy = false
	 * 
	 * @param emisionVO
	 *            (Requerido)
	 * @param mercado
	 *            (Requerido)
	 * @return EmisionVO[]
	 * @throws BusinessException
	 */
	EmisionVO[] getListaEmisiones(EmisionVO emisionVO, BigInteger idBoveda, Boolean mercado) throws BusinessException;

	/**
	 * CUMD43-09 : Lista de Emisiones Muestra todas las emisiones
	 * correspondientes a un tipo valor (tv), del mercado especificado { Dinero
	 * (true) o Capitales (false) }, las cuales pueden ser filtradas por
	 * cualquier otro de los atributos de la PK (emisora, serie, cupon).
	 * 
	 * @param emisionVO
	 *            (requerido) - Al menos debe llevar el Tipo de Valor
	 * @param mercado
	 *            (requerido) - true (Mercado de Dinero) / false (Mercado de
	 *            Capitales)
	 * @param isMercadoDinero
	 *            (requerido) - true (la consulta es de Mercado Dinero) / false
	 *            (la consulta es de Tesoreria)
	 * @return EmisionVO[]
	 * @throws BusinessException
	 */
	EmisionVO[] getListaEmisiones(EmisionVO emisionVO, BigInteger idBoveda, Boolean mercado, 
	        boolean isMercadoDinero) throws BusinessException;

	/**
	 * CUMD43-09 : Lista de Emisiones Muestra todas las emisiones
	 * correspondientes a un tipo valor (tv), del mercado especificado { Dinero
	 * (true) o Capitales (false) }, las cuales pueden ser filtradas por
	 * cualquier otro de los atributos de la PK (emisora, serie, cupon).
	 * 
	 * @param emisionVO
	 * @param mercado
	 * @param isMercadoDinero
	 * @param paginaVO
	 * @return PaginaVO
	 * @throws BusinessException
	 */
	PaginaVO getListaEmisiones(EmisionVO emisionVO, BigInteger idBoveda, Boolean mercado, 
	        boolean isMercadoDinero, PaginaVO paginaVO) throws BusinessException;

	/**
	 * CUMD43-09 : Lista de Emisiones Muestra todas las emisiones
	 * correspondientes a un tipo valor (tv), del mercado especificado { Dinero
	 * (true) o Capitales (false) }, las cuales pueden ser filtradas por
	 * cualquier otro de los atributos de la PK (emisora, serie, cupon).
	 * 
	 * @param emisionVO
	 *            (requerido) - Al menos debe llevar el Tipo de Valor
	 * @param mercado
	 *            (requerido) - true (Mercado de Dinero) / false (Mercado de
	 *            Capitales)
	 * @param isMercadoDinero
	 *            (requerido) - true (la consulta es de Mercado Dinero) / false
	 *            (la consulta es de Tesoreria)
	 * @return EmisionVO[]
	 * @throws BusinessException
	 */
	EmisionVO[] getListaEmisionesTesoreria(EmisionVO emisionVO, BigInteger idBoveda, Boolean mercado, 
	        boolean isMercadoDinero) throws BusinessException;

	/**
	 * CUMD43-09 : Lista de Emisiones Muestra todas las emisiones
	 * correspondientes a un tipo valor (tv), del mercado especificado { Dinero
	 * (true) o Capitales (false) }, las cuales pueden ser filtradas por
	 * cualquier otro de los atributos de la PK (emisora, serie, cupon).
	 * 
	 * @param emisionVO
	 * @param mercado
	 * @param isMercadoDinero
	 * @param paginaVO
	 * @return PaginaVO
	 * @throws BusinessException
	 */
	PaginaVO getListaEmisionesTesoreria(EmisionVO emisionVO, BigInteger idBoveda, Boolean mercado, 
	        boolean isMercadoDinero, PaginaVO paginaVO) throws BusinessException;

	/**
	 * Recupera el instrumento de una determinada emision
	 * 
	 * @param emisionVO
	 * @return
	 * @throws BusinessException
	 */
	EmisionVO buscaInstrumento(EmisionVO emisionVO) throws BusinessException;

	/**
	 * Muestra todas las emisiones correspondientes a los tipo de valor del
	 * arreglo BigInteger
	 * 
	 * @param idTipoValor
	 * @return EmisionVO[]
	 * @throws BusinessException
	 */
	EmisionVO[] getListaEmisiones(String[] idTipoValor, BigInteger idBoveda) throws BusinessException;

	/**
	 * Muestra todas las emisiones correspondientes a los tipo de valor del
	 * arreglo BigInteger
	 * 
	 * @param idTipoValor
	 * @param paginaVO
	 * @return PaginaVO
	 * @throws BusinessException
	 */
	PaginaVO getListaEmisiones(String[] idTipoValor, BigInteger idBoveda,
	        PaginaVO paginaVO) throws BusinessException;

	/**
	 * Recupera una EmisionVO a partir de su PK (tv, emisora, serie y cupon).
	 * 
	 * @param emisionVO -
	 *            Es obligatoria la PK (tv, emisora, serie, cupon) completa
	 * @return EmisionVO
	 * @throws BusinessException
	 */
	EmisionVO getEmision(EmisionVO emisionVO, BigInteger idBoveda) throws BusinessException;

	/**
	 * Recupera el arreglo de cuentas y las emisiones asociadas a una cuenta
	 * especifica de un agente (o a la primera cuenta, si no se envia esta) con
	 * las consideraciones de filtro utilizadas para el servicio de estado de
	 * cuenta del mercado de capitales.
	 * 
	 * @param params
	 * @return CuentasYEmisionesEstadoCuentaCapitalesVO
	 * @throws BusinessException
	 */
//	CuentasYEmisionesEstadoCuentaCapitalesVO getListaEmisionesByCuentaEstadoCuentaMercadoCapitales(GetEstadocuentaMercadoParams params)
//			throws BusinessException;

//	/**
//	 * Obtiene un arreglo con cadenas tipoValor correspondientes al agente
//	 * proporcionado
//	 * 
//	 * @param tabla
//	 * @param agente
//	 * @return String[]
//	 * @throws BusinessException
//	 */
//	String[] getTipoValorByAgente(AgenteVO agente, String tabla) throws BusinessException;

	// /**
	// * Obtiene una lista de emisiones para mercado capitales
	// *
	// * @deprecated Utilizar getListaEmisionesMercadoCapitales(AgenteVO
	// agenteVO,
	// * EmisionVO emisionVO) throws BusinessException;
	// * @param agenteVO
	// * obligatorio
	// * @param tipoValor
	// * opcional
	// * @return lista de emisiones
	// * @throws BusinessException
	// */
	// EmisionVO[] getListaEmisionesMercadoCapitales(AgenteVO agenteVO, String
	// tipoValor)
	// throws BusinessException;

	/**
     * Obtiene una lista de emisiones para mercado capitales
	 * @param agenteVO
	 * @param emisionVO
	 * @param idBoveda
	 * @return Arreglo de emisiones
	 * @throws BusinessException
	 */
	EmisionVO[] getListaEmisionesMercadoCapitales(AgenteVO agenteVO, 
	        EmisionVO emisionVO, BigInteger idBoveda) throws BusinessException;

	/**
     * Obtiene una lista de emisiones para mercado capitales
     *  
	 * @param agenteVO
	 * @param emisionVO
	 * @param idBoveda
	 * @param paginaVO
	 * @return PaginaVO
	 * @throws BusinessException
	 */
	PaginaVO getListaEmisionesMercadoCapitales(AgenteVO agenteVO, EmisionVO emisionVO, 
	        BigInteger idBoveda, PaginaVO paginaVO) throws BusinessException;

//	/**
//	 * Caso de uso <b>CUEC 01-01 Estado de Cuenta &uacute;nico</b>, consulta el
//	 * estado de cuenta &uacute;nico
//	 * 
//	 * @param params
//	 * @return EmisionVO[]
//	 * @throws BusinessException
//	 */
//	EmisionVO[] getEmisionesEstadoCuentaUnico(EstadoCuentaUnicoParams params) throws BusinessException;

//	/**
//	 * Recupera una lista de las emisiones vigentes para el estado de cuenta de
//	 * sociedades de inversi&oacute;n
//	 * 
//	 * @param agenteVO
//	 * @param emisionVO
//	 * @return PaginaVO
//	 * @throws BusinessException
//	 */
//	PaginaVO getListaEmisionesEstadoCuentaSociedadesInversion(AgenteVO agenteVO, EmisionVO emisionVO) throws BusinessException;

//	/**
//	 * Recupera una lista de emisiones de estado de cuenta unico.
//	 * 
//	 * @param agenteVO
//	 * @param emisionVO
//	 * @param fechaMovimiento
//	 * @return PaginaVO
//	 * @throws BusinessException
//	 */
//
//	PaginaVO getListaEmisionesEstadoCuentaUnico(AgenteVO agenteVO, EmisionVO emisionVO, Date fechaMovimiento) throws BusinessException;

	/**
     * Obtiene una lista de emisiones
	 * @param agenteVO
	 * @param emisionVO
	 * @param idBoveda
	 * @return Arreglo de emisiones
	 * @throws BusinessException
	 */
	EmisionVO[] getListaEmisionesCapitales(AgenteVO agenteVO, 
	        EmisionVO emisionVO, BigInteger idBoveda) throws BusinessException;

	/**
	 * Obtiene una lista de emisiones
	 * 
	 * @param agenteVO
	 * @param emisionVO
	 * @param paginaVO
	 * @return PaginaVO
	 * @throws BusinessException
	 */
	PaginaVO getListaEmisionesCapitales(AgenteVO agenteVO, EmisionVO emisionVO, BigInteger idBoveda,
	        PaginaVO paginaVO) throws BusinessException;

//	/**
//	 * Recupera la lista de emisiones para la pantalla de depositos, retiros y
//	 * requerimientos
//	 * 
//	 * @param claveAL
//	 * @param claveANL
//	 * @param tipoGarantia
//	 * @param pageVO
//	 * @return PaginaVO
//	 * @throws BusinessException
//	 */
//	PaginaVO getEmisionesInEstadoValores(String claveAL, String claveANL, String tipoGarantia, PaginaVO pageVO) throws BusinessException;

//	/**
//	 * Returna la lista de emisiones para garantias
//	 * 
//	 * @param claveAL
//	 * @param cuenta
//	 * @return List
//	 * @throws BusinessException
//	 */
//	List getEmisionesGarantias(String claveAL, String cuenta) throws BusinessException;

//	/**
//	 * Devuelve la lista de emisiones para Division Internacional incluyendo o
//	 * no las que tienen cupon_cortado = 'C' y/o las historicas
//	 * 
//	 * @param agente
//	 *            (obligatorio)
//	 * @param emision
//	 *            (opcional)
//	 * @param enCorte
//	 *            (true: cupon_cortado in ('F', 'C') / false: cupon_cortado =
//	 *            'F')
//	 * @param historico
//	 *            (false: saldo_disponible > 0)
//	 * @return EmisionVO[]
//	 * @throws BusinessException
//	 */
//	EmisionVO[] getListaEmisiones(AgenteVO agente, EmisionVO emision, boolean enCorte, boolean historico) throws BusinessException;

//	/**
//	 * Devuelve la lista de las emisiones por ejercicios presentes en la tabla
//	 * de decretos correspondiente
//	 * 
//	 * @param emisionVO
//	 * @param esFija
//	 * @return EmisionVO[]
//	 * @throws BusinessException
//	 */
//	EmisionVO[] getListaEmisionesPorEjercicio(EmisionVO emisionVO, boolean esFija) throws BusinessException;

//	/**
//	 * Este m&eacute;todo recupera la lista de emisiones para realizar los
//	 * c&aacute;lculos de arqueo de valores de divisi&oacute;n internacional.
//	 * 
//	 * @param agenteVO
//	 * @param fechaConsulta
//	 * @return EmisionVO[]
//	 * @throws BusinessException
//	 */
//	EmisionVO[] getListaEmisionesFideicomisoArqueo(AgenteVO agenteVO, Date fechaConsulta) throws BusinessException;

	/**
	 * Regresa el nombre de la emisora cuando es &uacute;nica para el tv
	 * proporcinado
	 * 
	 * @param tv
	 * @return String
	 * @throws BusinessException
	 */
	String getEmisoraByTVCustodia(String tv) throws BusinessException;

//	/**
//	 * Obtiene la lista de emisiones validas para Strips - Custodia
//	 * 
//	 * @param emisionVO
//	 * @return Una lista de EmisionesStripsVO
//	 * @throws BusinessException
//	 */
//	List getListaEmisionesStrips(EmisionVO emisionVO) throws BusinessException;

//	/**
//	 * Obtiene una lista de emisiones que tengan posici&oacute;n y que cupon
//	 * cortado sea en firme (F)
//	 * 
//	 * @param emisionVO
//	 *            tv, emisora, serie y cup&oacute;n
//	 * @param agenteVO
//	 *            idInst, folioInst y cuenta.
//	 * @param isCapitales
//	 *            si se trata de capitales o dinero y gubernamental
//	 * @return un arreglo de instancias de {@link EmisionVO}
//	 * @throws BusinessException
//	 *             en caso de que los parametos sean nulos o no se obtengan
//	 *             registros
//	 */
//	EmisionVO[] getListaEmisiones(EmisionVO emisionVO, AgenteVO agenteVO, boolean isCapitales) throws BusinessException;

	/**
	 * Obtiene una lista de emisiones para mercado capitales
	 * 
	 * @param agenteVO
	 *            obligatorio
	 * @param emisionVO
	 *            opcional
	 * @return lista de emisiones de bdcamara
	 * @throws BusinessException
	 */
	EmisionVO[] getListaEmisionesControlPosicionValores(AgenteVO agenteVO, 
	        EmisionVO emisionVO, BigInteger idBoveda) throws BusinessException;

	/**
	 * @param agenteVO
	 * @param emisionVO
	 * @param paginaVO
	 * @return PaginaVO
	 * @throws BusinessException
	 */
	PaginaVO getListaEmisionesControlPosicionValores(AgenteVO agenteVO, EmisionVO emisionVO, 
	        BigInteger idBoveda, PaginaVO paginaVO) throws BusinessException;

}

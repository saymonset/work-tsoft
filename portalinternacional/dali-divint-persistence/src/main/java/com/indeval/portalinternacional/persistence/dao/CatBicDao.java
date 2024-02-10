/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.dao;

import java.util.List;
import java.util.Map;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.persistence.modelo.Emision;
import com.indeval.portalinternacional.middleware.servicios.modelo.CatBic;
import com.indeval.portalinternacional.middleware.servicios.modelo.Custodio;
import com.indeval.portalinternacional.middleware.servicios.modelo.SicEmision;
import com.indeval.portalinternacional.middleware.servicios.vo.CatBicVO;

/**
 * @author javiles
 *
 */
public interface CatBicDao extends BaseDao {

    /**
     * Obtiene una lista de instancias de CatBic correspondiente 
     * a los {@link AgenteVO} proporcionados
     * 
     * @param agenteVO
     * @return List<CatBic>
     */
    public List<CatBic> findCatBic(AgenteVO[] agenteVO);
    
    /**
     * Obtiene una lista de instancias de CatBic correspondiente
     * a los {@link List<SicEmision>} proporcionados
     * 
     * @param sicEmisiones
     * @return List<CatBic>
     */
    public List<CatBic> findCatBic(List<SicEmision> sicEmisiones);

    /**
     * Obtiene una lista de instancias de CatBic que corresponde a la {@link Emision} proporcionada
     * @param emision La emision que se usa para obtener el listado de instancias de CatBic.
     * @return El listado de instancias de CatBic.
     */
    List<CatBic> findCatBicEnBaseAEmision(Emision emision);

    /**
     * Obtiene una lista de instancias de CatBic correspondiente 
     * a los {@link AgenteVO} proporcionads
     * @param agenteVO
     * @param custodio
     * @return Integer
     */
    public Integer findCustodios(AgenteVO[] agenteVO, String custodio);
    
    /**
     * Obtiene la instancia {@link CatBic} correspondiente a los {@link AgenteVO} 
     * proporcionados filtrada por custodio.
     * @param agenteVO
     * @param custodio
     * @return List<CatBic>
     */
    public List<CatBic> findCatBic(AgenteVO[] agenteVO, String custodio);
    
    /**
     * Obtiene una lista de todas las instancias de CatBic
     * @return List<CatBic>
     */
    public List<CatBic> findCatBic();
    
    /**
     * Obtiene una lista de los distintos nombres de CatBics sin repeticiones
     * @return List<String>
     */
    public List findCatBicByName();
    
    /**
     * Obtiene una lista de todos los distintos nombres de CatBics sin repeticiones
     * @return List<String>
     */
    public List findAllCatBicByName();
    
    /**
     * Obtiene los custodios por agente vo y nombre de custodio
     * @param agenteVO
     * @param custodio
     * @return List<CatBic>
     */
    public List<CatBic> findCatBic(Long[] agenteVO, String custodio);
    
    /**
     * Regresa el numero de catbics con el id de cuenta nombrada proporcionado
     * @param idCuentaNombrada
     * @return
     */
    public int findCatBicByCuentaNombrada(Long idCuentaNombrada);

    // Cambio Multidivisas
    /**
     * Obtiene el ID del CatBic que corresponde a la boveda de efectivo e institucion proporcionadas
     * @param idBovedaEfectivo
     * @param idInstitucion
     * @return El ID del CatBic correspondiente.
     */
    Long findCatBicEnBaseABovedaEfectivoParticipante(Long idBovedaEfectivo, Long idInstitucion);
    // Fin Cambio Multidivisas
    /* Metodo que busca los datos
     * 
     * */
    public List<CatBic> findCatBicByIds(Long[] idAgente);
    
    
    public List<CatBic> findCatBicByIds(Long[] idAgente, String cuentaIndeval);

    /**
     * Metodo para obtener el custodio por cuenta nombrada
     * @param cuentaNombrada
     * @return
     */
    public Map<Long, String> findCustodioCuentaNombrada(Long cuentaNombrada);

    /**
     * Obtiene un mapa de los distintos nombres de CatBics sin repeticiones
     * @return Map<Long,String>, la llave es la cuenta nombrada, el valor es el nombre
     * */
    public Map<Long,String> findCatBicByNameForMulticarga();
   
    /**
     * obtine un mapa con nombres banco de los custodios y el CatBic
     * @return Map<String,CarBic>, la llave es el valor del nombre banco
     */
    public Map<String,CatBic> getCatalogoCustodios();
    
    public Map<String,Long> getCustodiosIdCuentaNombrada();

    /**
     * Encuentra la descripcion bic_prod de CatBic en base a la cuenta nombrada.
     * @param idCuentaNombrada La cuenta nombrada.
     * @return La descripcion.
     */
    String findCatBicEntityByCuentaNombrada(Long idCuentaNombrada);

    /**
     * Obtiene una entidad CatBic en base a un id de cuenta nombrada.
     * @param idCuentaNombrada El id de la cuenta nombrada.
     * @return La entidad.
     */
    CatBic getCatBicEntityByIdCuentaNombrada(Long idCuentaNombrada);
    
    /**
     * Obtiene el Catbic por id cuenta nombrada y cuenta Indeval.
     * @param idCuentaNombrada Id cuenta nombrada.
     * @param cuentaIndeval Cuenta Indeval.
     * @return Catbic o nulo si no existe.
     */
    CatBic getCatBicByIdCtaNombradaCtaIndeval(Long idCuentaNombrada, String cuentaIndeval) throws BusinessException;
    
    /**
     * Obtiene una lista de los distintos nombres de CatBics sin repeticiones que este activo
     * @return List<String>
     */
    public List findCatBicByNameAndActive();
    
    
    public List<Object[]> findCatBicByNameEntity();

	/**
	 * Metodo para modificar atributos de un objeto CatBic
	 * @param custodiosBeneficiarios
	 */
	void modificarCustodios(CatBicVO custodiosBeneficiarios);
	
	/**
	 * 
	 * @param abreviacionCustodio
	 * @return
	 */
	public CatBic findAbreviacionByCustodio(String abreviacionCustodio);

	/**
	 * Encuentra los Custodios (tabla C_CUSTODIO) por idCatBic.
	 * @param idCatBic El idCatBic
	 * @return La lista de Custodios.
	 */
	List<Custodio> getCustodiosByIdCatBic(Long idCatBic);

	/**
	 * Encuentra los tipos valor de un custodio
	 * @param idCustodio El id del custodio a buscar
	 * @return El listado de tipos valor del custodio.
	 */
	List<String> findTiposValorCustodiosByIdCustodio(Long idCustodio);

	/**
	 * Elimina los tipos de valor del custodio que se pasa como parametro.
	 * @param idCustodio El id del custodio para eliminar sus relaciones de tipo valor.
	 * @return El numero de registros eliminados.
	 */
	int eliminarTiposValorCustodiosByIdCustodio(Long idCustodio);

	/**
	 * Elimina los tipos de valor que se pasan como parametro, del custodio que se pasa como parametro.
     * @param idCustodio El id del custodio para eliminar sus relaciones de tipo valor.
	 * @param tvsCustodio Los tipos valor del custodio a eliminar.
	 * @return El numero de registros eliminados.
	 */
	int eliminarTiposValorCustodio(Long idCustodio, List<String> tvsCustodio);

}

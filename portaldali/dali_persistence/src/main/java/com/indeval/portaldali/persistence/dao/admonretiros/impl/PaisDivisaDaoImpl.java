/*
 * Copyrigth (c) 2009 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.admonretiros.impl;

import java.math.BigInteger;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.persistence.model.PaisDivisa;
import com.indeval.portaldali.persistence.dao.admonretiros.PaisDivisaDao;

/**
 * Implementacion de la interface PaisDivisaDao, contiene los metodos 
 * para las acciones sobre el catalogo de pais-divisa
 * 
 * @author Maria C. Buendia
 * @version 1.0
 */
public class PaisDivisaDaoImpl extends BaseDaoHibernateImpl implements PaisDivisaDao {
	
    /** Log de clase. */
    private static final Logger logger = LoggerFactory.getLogger(PaisDivisaDaoImpl.class);
	
	/**
	 * Consulta los estados de cuenta por id
	 * @param id objeto Long que identifica un estado
	 * @return objeto tipo EstatusRetiro
	 */
	public PaisDivisa getPaisDivisa(BigInteger id){
		logger.debug("Encontrando... PaisDivisa con id : " + id);
		return (PaisDivisa)getHibernateTemplate().get(PaisDivisa.class, id);
	}
	
	/**
	 * Consulta todos los estados existentes
	 * @return lista de objetos PaisDivisa
	 */
	public List getAll(){
		logger.debug("Encontrando todos los objetos PaisDivisa");
		return getHibernateTemplate().find("from "+PaisDivisa.class.getName() + " pd order by pd.divisa.descripcion asc ");
	}
	
	/**
	 * Consulta los datos del registro buscando por el id de la divisa
	 * @param idDivisa id de la divisa
	 * @return String[]  
	 */
	public PaisDivisa getDatosPorDivisa(BigInteger idDivisa){
		logger.debug("Encontrando PaisDivisa para idDivisa="+idDivisa);
		StringBuffer query = new StringBuffer();
		query.append("from "+PaisDivisa.class.getName()).append(" pd");
		query.append("     join fetch pd.pais ");
		query.append("where pd.divisa.idDivisa = ").append(idDivisa);
		List encontrados = getHibernateTemplate().find(query.toString());
		return encontrados == null||encontrados.size()==0? null:(PaisDivisa)encontrados.get(0);
	}
	
	/**
	 * Consulta los datos del registro buscando por la clave del pais
	 * @param clavePais caracteres 5 y 6 del BIC
	 * @return String[]  
	 */
	public PaisDivisa getDatosPorPais(String clavePais){
		logger.debug("Encontrando PaisDivisa para clavePais="+clavePais);
		StringBuffer query = new StringBuffer();
		query.append("from "+PaisDivisa.class.getName()).append(" pd");
		query.append(" where pd.pais.clave = '").append(clavePais).append("'");
		List encontrados = getHibernateTemplate().find(query.toString());
		return encontrados == null||encontrados.size()==0? null:(PaisDivisa)encontrados.get(0);
	}


}

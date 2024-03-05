/*
 * Copyrigth (c) 2009 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.admoncuentas.impl;

import java.math.BigInteger;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.persistence.model.EstadoInstruccionCat;
import com.indeval.portaldali.persistence.dao.admoncuentas.EstatusCuentaDao;

/**
 * Implementacion de la interface EstatusCuentaDao, contiene los metodos 
 * para las acciones sobre el catalogo de estados para una cuenta de retiro 
 * internacional o nacional
 * 
 * @author Maria C. Buendia
 * @version 1.0
 */
public class EstatusCuentaDaoImpl extends BaseDaoHibernateImpl implements EstatusCuentaDao {
	
    /** Log de clase. */
    private static final Logger logger = LoggerFactory.getLogger(EstatusCuentaDaoImpl.class);
	
	/**
	 * Consulta los estados de cuenta por id
	 * @param id objeto Long que identifica un estado
	 * @return objeto tipo EstadoInstruccionCat
	 */
	public EstadoInstruccionCat getEstado(BigInteger id){
		logger.debug("Encontrando... EstadoInstruccionCat con id : " + id);
		return (EstadoInstruccionCat)getHibernateTemplate().get(EstadoInstruccionCat.class, id);
	}
	
	/**
	 * Consulta todos los estados existentes
	 * @return lista de objetos EstadoInstruccionCat
	 */
	public List getAll(){
		logger.debug("Encontrando todos los objetos EstadoInstruccionCat");
		return getHibernateTemplate().find("from "+EstadoInstruccionCat.class.getName() + 
				" ec where ec.claveEstadoInstruccion in('AU','LA','CA','RE','AP') order by ec.descripcion asc ");
	}

	/**
	 * Busca el estado por clave corta
	 * @param cveCorta clave corta del estado 
	 */
	public EstadoInstruccionCat getEstadoXClaveCorta(String cveCorta){
		logger.debug("Encontrando el objeto para EstadoInstruccionCat cveCorta="+cveCorta);
		List resultados = getHibernateTemplate().find("from "+EstadoInstruccionCat.class.getName() + " ec where claveEstadoInstruccion = '"+cveCorta+"'");
		return resultados==null || resultados.size()==0? null: (EstadoInstruccionCat)resultados.get(0); 
	}
}

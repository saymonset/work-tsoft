/*
 * Copyrigth (c) 2009 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.admoncuentas.impl;

import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.persistence.dao.admoncuentas.HistoricoAdmonCuentasRetiroDao;
import com.indeval.portaldali.persistence.model.HistoricoAdmonCuentasRetiroNacional;
import com.indeval.portaldali.persistence.model.HistoricoAdmonCuentasRetiroInternacional;

/**
 * Implementacion de la interface CuentaRetiroDao, contiene los metodos para las 
 * acciones sobre el historico las cuentas de retiro de efectivo internacionales. 
 * 
 * @author Maria C. Buendia
 * @version 1.0
 */
public class HistoricoAdmonCuentasRetiroDaoImpl  extends BaseDaoHibernateImpl implements HistoricoAdmonCuentasRetiroDao {
	
    /** Log de clase. */
    private static final Logger logger = LoggerFactory.getLogger(HistoricoAdmonCuentasRetiroDaoImpl.class);
	
	/** 
	 * Crea un registro en el historico 
	 * @param historico objeto HistoricoAdmonCuentaRetiroInt
	 */
	public void save(HistoricoAdmonCuentasRetiroInternacional historico){
		logger.info("salvando una CuentaRetiroInternacional");
		save(historico);
	}
	
	public void save(HistoricoAdmonCuentasRetiroNacional historico){
		logger.info("salvando una CuentaRetiroNacional");
		save(historico);
	}
	
	/**
	 * Consulta historico por id 
	 * @param id objeto Long que identifica un historico
	 */
	public Object getHistorico(BigInteger id, String tipoHistorico){
		if(tipoHistorico.equals("N")){
			logger.debug("Encontrando... HistoricoAdmonCuentasRetiroNacional con id : " + id);
			return (HistoricoAdmonCuentasRetiroNacional)getHibernateTemplate().get(HistoricoAdmonCuentasRetiroNacional.class, id);
		}
		logger.debug("Encontrando... HistoricoAdmonCuentasRetiroInternacional con id : " + id);
		return (HistoricoAdmonCuentasRetiroInternacional)getHibernateTemplate().get(HistoricoAdmonCuentasRetiroInternacional.class, id);
	} 
	
	/**
	 * Actualiza historico 
	 * @param historico objeto HistoricoAdmonCuentasRetiroInternacional
	 */
	public void saveOrUpdate(HistoricoAdmonCuentasRetiroInternacional historico){
		logger.debug("saveOrUpdate, HistoricoAdmonCuentaRetiroInt");
		getHibernateTemplate().saveOrUpdate(historico);
	}
	
	public void saveOrUpdate(HistoricoAdmonCuentasRetiroNacional historico){
		logger.debug("saveOrUpdate, HistoricoAdmonCuentaRetiroNal");
		getHibernateTemplate().saveOrUpdate(historico);
	}
	
	/**
	 * Elimina historico  
	 * @param historico objeto HistoricoAdmonCuentasRetiroInternacional
	 */
	public void delete(HistoricoAdmonCuentasRetiroInternacional historico){
		logger.debug("eliminando HistoricoAdmonCuentasRetiroInternacional cuentaId:"+historico.getIdHistCuentaRetiroInt());
		getHibernateTemplate().delete(historico);
	}
	
	public void delete(HistoricoAdmonCuentasRetiroNacional historico){
		logger.debug("eliminando HistoricoAdmonCuentasRetiroNacional cuentaId:"+historico.getIdHistCuentaRetiroNal());
		getHibernateTemplate().delete(historico);
	}
}

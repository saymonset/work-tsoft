/*
 * Copyrigth (c) 2009 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.admoncuentas;

import java.math.BigInteger;

import com.indeval.portaldali.persistence.model.HistoricoAdmonCuentasRetiroNacional;
import com.indeval.portaldali.persistence.model.HistoricoAdmonCuentasRetiroInternacional;

/**
 * Interface que expone los m&eacute;todos para las acciones sobre las cuentas de 
 * retiro de efectivo nacional e internacional. 
 * 
 * @author Maria C. Buendia
 * @version 1.0
 */
public interface HistoricoAdmonCuentasRetiroDao {

	/** 
	 * Crea una nueva cuenta 
	 * @param cuenta objeto CuentaRetiro
	 */
	void save(HistoricoAdmonCuentasRetiroNacional historico);
	void save(HistoricoAdmonCuentasRetiroInternacional historico);
	
	/**
	 * Consulta la cuenta por id 
	 * @param id objeto Long que identifica una cuenta
	 * @param tipoHistorico N - Nacional, I- Internacional 
	 */
	Object getHistorico(BigInteger id, String tipoHistorico); 
	
	/**
	 * Actualiza cuenta 
	 * @param cuenta objeto CuentaRetiro
	 */
	void saveOrUpdate(HistoricoAdmonCuentasRetiroNacional historico);
	void saveOrUpdate(HistoricoAdmonCuentasRetiroInternacional historico);
	
	/**
	 * Elimina cuenta  
	 * @param cuenta objeto CuentaRetiro
	 */
	void delete(Object cuenta);
}

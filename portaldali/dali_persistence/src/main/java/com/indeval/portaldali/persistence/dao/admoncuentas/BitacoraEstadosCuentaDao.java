/*
 * Copyrigth (c) 2009 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.admoncuentas;

import java.util.List;

import com.indeval.portaldali.persistence.model.BitacoraEstadosCuentaRetiro;

/**
 * Interface que expone los m&eacute;todos para las acciones sobre la bitacora
 * de cambios de estado.  
 * 
 * @author Maria C. Buendia
 * @version 1.0
 */
public interface BitacoraEstadosCuentaDao {

	/** 
	 * Crea un registro en la bitacora
	 * @param cuenta objeto BitacoraEstadosCuentaRetiro
	 */
	void save(BitacoraEstadosCuentaRetiro cuenta);
	
	/**
	 * Consulta la Bitacora de estados por id 
	 * @param id objeto Long que identifica un registro en la bitacora
	 */
	BitacoraEstadosCuentaRetiro getBitacora(Long id); 
	
	/**
	 * Actualiza bitacora 
	 * @param cuenta objeto BitacoraEstadosCuentaRetiro
	 */
	void saveOrUpdate(BitacoraEstadosCuentaRetiro cuenta);
	
	/**
	 * Elimina registro de bitacora  
	 * @param cuenta objeto BitacoraEstadosCuentaRetiro
	 */
	void delete(BitacoraEstadosCuentaRetiro cuenta);
	
	/**
	 * Busca la bitacora de los estados por un id de cuenta
	 * @param idcuenta id de la cuenta retiro 
	 */
	BitacoraEstadosCuentaRetiro getBitacoraPorCuenta(Long idCuenta);
	
	/**
	 * Busca las bitacoras correspondientes a los id de las cuentas (CuentaRetiro) 
	 * @param idCuentas id de las cuentas (CuentaRetiro) separadas por coma
	 */
	@SuppressWarnings("unchecked")
	List getBitacoraXIdsCuentaRetiro(String idCuentas);
}

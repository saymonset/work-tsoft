/*
 * Copyrigth (c) 2009 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.admoncuentas;

import java.math.BigInteger;
import java.util.List;

import com.indeval.portaldali.persistence.model.EstadoInstruccionCat;
/**
 * Interface que expone los m&eacute;todos para las acciones sobre el catalogo
 * de estados para una cuenta de retiro internacional o nacional
 * 
 * @author Maria C. Buendia
 * @version 1.0
 */
public interface EstatusCuentaDao {

	/**
	 * Consulta los estados de cuenta por id
	 * @param id objeto Long que identifica un estado
	 * @return objeto tipo EstadoInstruccionCat
	 */
	EstadoInstruccionCat getEstado(BigInteger id);
	
	/**
	 * Consulta todos los estados existentes
	 * @return lista de objetos EstadoInstruccionCat
	 */
	public List getAll();
	
	/**
	 * Busca el estado por clave corta
	 * @param cveCorta clave corta del estado 
	 */
	EstadoInstruccionCat getEstadoXClaveCorta(String cveCorta);

}

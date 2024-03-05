/*
 * Copyrigth (c) 2009 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.admonretiros;

import java.math.BigInteger;
import java.util.List;

import com.indeval.portaldali.persistence.model.PaisDivisa;

/**
 * Interface que expone los m&eacute;todos para las acciones sobre el catalogo
 * de pais-divisa
 * 
 * @author Maria C. Buendia
 * @version 1.0
 */
public interface PaisDivisaDao {

	/**
	 * Consulta los estados de cuenta por id
	 * @param id objeto Long que identifica un estado
	 * @return objeto tipo EstatusRetiro
	 */
	PaisDivisa getPaisDivisa(BigInteger id);
	
	/**
	 * Consulta todos los estados existentes
	 * @return lista de objetos EstatusRetiro
	 */
	List<PaisDivisa> getAll();
	
	/**
	 * Consulta los datos del registro buscando por el id de la divisa
	 * @param idDivisa id de la divisa
	 * @return String[]  
	 */
	PaisDivisa getDatosPorDivisa(BigInteger idDivisa);
	
	/**
	 * Consulta los datos del registro buscando por la clave del pais
	 * @param clavePais caracteres 5 y 6 del BIC
	 * @return String[]  
	 */
	PaisDivisa getDatosPorPais(String clavePais);

}



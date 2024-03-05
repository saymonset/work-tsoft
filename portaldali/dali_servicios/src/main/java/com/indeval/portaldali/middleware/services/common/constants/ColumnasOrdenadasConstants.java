/**
 * 2H Software - Bursatec
 * 
 * Sistema de Consulta de Estados de Cuenta
 *
 * Jan 7, 2008
 *
 */
package com.indeval.portaldali.middleware.services.common.constants;

import com.indeval.portaldali.middleware.dto.CriterioOrdenamientoDTO;

/**
 * Constantes que definen las columnas por las cuales se ordenan
 * las consultas que utilizan el DTO {@link CriterioOrdenamientoDTO}
 * @author Pablo Julián Balderas Méndez
 * 
 */
public interface ColumnasOrdenadasConstants {

	/** Indica que el criterio por el que se ordena es la cuenta */
	final String POR_CUENTA = "sortCuenta";
	
	/** Indica que el criterio por el que se ordena es la emisora */
	final String POR_EMISORA = "sortEmisora";
	
	/** Indica que el criterio por el que se ordena es la posicion */
	final String POR_POSICION = "sortPosicion";
	
	/** Indica que el criterio por el que se ordena es la boveda */
	final String POR_BOVEDA = "sortBoveda";
	
	/** Indica que el criterio por el que se ordena es la divisa */
	final String POR_DIVISA = "sortDivisa";
	
	/** Indica que el criterio por el que se ordena es el saldo */
	final String POR_SALDO = "sortSaldo";
	
}

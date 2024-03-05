/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * FolioUsuarioDTOComparator.java
 * 07/03/2008
 */
package com.indeval.portaldali.middleware.dto;

import java.util.Comparator;

/**
 * Comparator para el ordenamiento de resultados por Fecha de Registro
 * @author Emigdio Hernández
 *
 */
public class FechaRegistroDTOComparator implements Comparator<OperacionValorMatchDTO> {

	public int compare(OperacionValorMatchDTO o1, OperacionValorMatchDTO o2) {
		return o1.getFechaConcertacion().compareTo(o2.getFechaConcertacion());
	}

	

}

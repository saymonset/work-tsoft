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
 * Comparator para el ordenamiento de resultados por Folio de Usuario
 * @author Emigdio Hernández
 *
 */
public class FolioUsuarioDTOComparator implements Comparator<OperacionValorMatchDTO> {

	public int compare(OperacionValorMatchDTO o1, OperacionValorMatchDTO o2) {
		return o1.getFolioUsuario().compareToIgnoreCase(o2.getFolioUsuario());
	}

	

}

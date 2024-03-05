/**
 * CMMV - Portal Dali
 * Copyrigth (c) 2016 CMMV. All Rights Reserved.
 */
package com.indeval.portaldali.constantes;

/**
 * Enumeracion con los tipos de ordenamiento 
 * 
 * @author Pablo Balderas
 */
public enum OrdenamientoConsulta {

	SORT_CUENTA("sortCuenta"),
	SORT_EMISORA("sortEmisora"),
	SORT_BOVEDA("sortBoveda"),
	SORT_TV("sortTv"),
	SORT_POSICION("sortPosicion"),
	SORT_SERIE("sortSerie"),
	SORTE_DEFAULT("sortDefault");
	
	/** Ordenamiento */
	private String ordenamiento;
	
	/**
	 * Constructor de la clase.
	 * @param ordenamiento
	 */
	private OrdenamientoConsulta(String ordenamiento) {
		this.ordenamiento = ordenamiento;
	}

	/**
	 * Método para obtener el atributo ordenamiento
	 * @return El atributo ordenamiento
	 */
	public String getOrdenamiento() {
		return ordenamiento;
	}
}

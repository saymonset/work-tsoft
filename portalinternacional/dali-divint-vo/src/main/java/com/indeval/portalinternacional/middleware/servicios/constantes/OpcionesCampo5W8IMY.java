/**
 * Bursatec - Portal Internacional
 * Copyrigth (c) 2015 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.constantes;

/**
 * Enumeración con las opciones validas para el campo 5 del formato W8IMY
 * en el proceso de file transfer.
 * 
 * @author Pablo Balderas
 *
 */
public enum OpcionesCampo5W8IMY {
	A, B, C, D, E, F, G, H, I, J, K, L,
	M, N, O, P, Q, R, S, T, U, V, W, X,
	Y;
	
	/**
	 * Método que evalua si la cadena esta dentro de los valores del enum
	 * @param value Valor a evaluar
	 * @param enumClass Tipo de enum
	 * @return true si esta; false en caso contrario
	 */
	public static <E extends Enum<E>> boolean isInEnum(String value, Class<E> enumClass) {
		for (E e : enumClass.getEnumConstants()) {
			if (e.name().equals(value)) {
				return true;
			}
		}
		return false;
	}
}

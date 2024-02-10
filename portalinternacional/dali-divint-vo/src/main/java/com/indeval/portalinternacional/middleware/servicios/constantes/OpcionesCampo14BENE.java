/**
 * Bursatec - Portal Internacional Copyrigth (c) 2015 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.constantes;

/**
 * Enumeración con las opciones validas para el campo 5 del formato W8BEN-E en el proceso de file
 * transfer.
 * 
 * @author Pablo Balderas
 * 
 */
public enum OpcionesCampo14BENE {
    A, B, C, D, E, F, G, H, I, J;

    /**
     * Método que evalua si la cadena esta dentro de los valores del enum
     * 
     * @param value Valor a evaluar
     * @param enumClass Tipo de enum
     * @return true si esta; false en caso contrario
     */
    public static <E extends Enum<E>> boolean isInEnum(final String value, final Class<E> enumClass) {
        for (E e : enumClass.getEnumConstants()) {
            if (e.name().equals(value)) {
                return true;
            }
        }
        return false;
    }
}

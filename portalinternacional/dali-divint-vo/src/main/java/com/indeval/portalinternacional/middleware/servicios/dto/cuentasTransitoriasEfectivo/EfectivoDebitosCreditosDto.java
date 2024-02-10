/**
 * Portal Internacional
 * Copyrigth (c) 2022 Indeval. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.dto.cuentasTransitoriasEfectivo;

import java.io.Serializable;

/**
 * Dto con la informacion de los debitos y creditos de un bloque de efectivo de un ISO.
 *
 * @author Pablo Balderas
 * @author Jacito
 */
public class EfectivoDebitosCreditosDto implements Serializable {

    /**
     * Id para la serializacion
     */
    private static final long serialVersionUID = 1037078116334868277L;

    /**
     * Indica el tipo de movimiento, credito o debito
     */
    private String tipoMovimiento;

    /**
     * Indica el monto del movimiento
     */
    private String monto;


    /**
     * Constructor de la clase
     */
    public EfectivoDebitosCreditosDto() {
        super();
    }

    /**
     * Constructor de la clase
     *
     * @param tipoMovimiento
     * @param monto
     */
    public EfectivoDebitosCreditosDto(String tipoMovimiento, String monto) {
        super();
        this.tipoMovimiento = tipoMovimiento;
        this.monto = monto;
    }

    public EfectivoDebitosCreditosDto(String monto) {
        this.monto = monto;
    }

    /**
     * Metodo para obtener el atributo tipoMovimiento
     *
     * @return El atributo tipoMovimiento
     */
    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    /**
     * Metodo para establecer el atributo tipoMovimiento
     *
     * @param tipoMovimiento El valor del atributo tipoMovimiento a establecer.
     */
    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    /**
     * Metodo para obtener el atributo monto
     *
     * @return El atributo monto
     */
    public String getMonto() {
        return monto;
    }

    /**
     * Metodo para establecer el atributo monto
     *
     * @param monto El valor del atributo monto a establecer.
     */
    public void setMonto(String monto) {
        this.monto = monto;
    }

    @Override
    public String toString() {
        return "EfectivoDebitosCreditosDto{" +
                "tipoMovimiento='" + tipoMovimiento + '\'' +
                ", monto='" + monto + '\'' +
                '}';
    }
}

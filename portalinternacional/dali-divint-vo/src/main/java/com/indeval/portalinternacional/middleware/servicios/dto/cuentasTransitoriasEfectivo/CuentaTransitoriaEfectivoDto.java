/**
 * Portal Internacional
 * Copyrigth (c) 2022 Indeval. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.dto.cuentasTransitoriasEfectivo;

import java.io.Serializable;
import java.util.List;

/**
 * DTO que contiene la informacion de las cuentas transitorias de efectivo.
 *
 * @author Pablo Balderas
 * @author Jacito
 */
public class CuentaTransitoriaEfectivoDto extends DetalleReferenciaDto implements Serializable {

    /**
     * Id para la serializacion
     */
    private static final long serialVersionUID = -1110696129502671028L;

    /**
     * REFERENCIA_OPERACION : Referencia de operacion
     */
    private String referenciaOperacion;

    private boolean relacionar;

    /**
     * Constructor de la clase.
     */
    public CuentaTransitoriaEfectivoDto() {
        super();
    }


    /**
     * Metodo para obtener el atributo referenciaOperacion
     *
     * @return El atributo referenciaOperacion
     */
    public String getReferenciaOperacion() {
        return referenciaOperacion;
    }

    /**
     * Metodo para establecer el atributo referenciaOperacion
     *
     * @param referenciaOperacion El valor del atributo referenciaOperacion a establecer.
     */
    public void setReferenciaOperacion(String referenciaOperacion) {
        this.referenciaOperacion = referenciaOperacion;
    }

    /**
     * Metodo para obtener el atributo relacionar
     *
     * @return El atributo relacionar
     */
    public boolean isRelacionar() {
        return relacionar;
    }

    /**
     * Metodo para establecer el atributo relacionar
     *
     * @param relacionar El valor del atributo relacionar a establecer.
     */
    public void setRelacionar(boolean relacionar) {
        this.relacionar = relacionar;
    }

    @Override
    public String toString() {
        return "CuentaTransitoriaEfectivoDto{" +
                "referenciaOperacion='" + referenciaOperacion + '\'' +
                ", tipoMensaje='" + tipoMensaje + '\'' +
                ", divisa='" + divisa + '\'' +
                ", idDivisa='" + idDivisa + '\'' +
                ", custodio='" + custodio + '\'' +
                ", idCustodio='" + idCustodio + '\'' +
                ", total='" + total + '\'' +
                ", idRegistro='" + idRegistro + '\'' +
                ", relacionar=" + relacionar +
                ", mensajeISO='" + mensajeISO + '\'' +
                '}';
    }
}

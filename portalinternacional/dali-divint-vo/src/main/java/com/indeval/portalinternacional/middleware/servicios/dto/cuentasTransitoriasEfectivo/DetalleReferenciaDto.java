package com.indeval.portalinternacional.middleware.servicios.dto.cuentasTransitoriasEfectivo;

import java.io.Serializable;

/**
 * DTO que contiene la informacion detalle de la referencia: FOLIO_RELACIONADO agrupado en cuentas transitorias de efectivo.
 *
 * @author Jacito
 */
public class DetalleReferenciaDto extends FolioAgrupadoDto implements Serializable {
    /**
     * Id para la serializacion
     */
    private static final long serialVersionUID = -2709202303152671028L;

    /**
     * Id del registro
     */
    protected String idRegistro;

    /**
     * TIPO_MENSAJE
     */
    protected String tipoMensaje;

    /**
     * XML: Mensaje ISO
     */
    protected String mensajeISO;

    /**
     * XML: seme
     */
    private String seme;

    /**
     * Detalle de Movimientos
     */
    private String detalleMovimientos;

    /**
     * Constructor de la clase.
     */
    public DetalleReferenciaDto() {
        super();
    }

    /**
     * Metodo para obtener el atributo idRegistro
     *
     * @return El atributo idRegistro
     */
    public String getIdRegistro() {
        return idRegistro;
    }

    /**
     * Metodo para establecer el atributo idRegistro
     *
     * @param idRegistro El valor del atributo idRegistro a establecer.
     */
    public void setIdRegistro(String idRegistro) {
        this.idRegistro = idRegistro;
    }


    /**
     * Metodo para obtener el atributo tipoMensaje
     *
     * @return El atributo tipoMensaje
     */
    public String getTipoMensaje() {
        return tipoMensaje;
    }

    /**
     * Metodo para establecer el atributo tipoMensaje
     *
     * @param tipoMensaje El valor del atributo tipoMensaje a establecer.
     */
    public void setTipoMensaje(String tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }

    /**
     * Metodo para obtener el atributo mensajeISO
     *
     * @return El atributo mensajeISO
     */
    public String getMensajeISO() {
        return mensajeISO;
    }

    /**
     * Metodo para establecer el atributo mensajeISO
     *
     * @param mensajeISO El valor del atributo mensajeISO a establecer.
     */
    public void setMensajeISO(String mensajeISO) {
        this.mensajeISO = mensajeISO;
    }

    /**
     * Metodo para obtener el atributo seme
     *
     * @return El atributo seme
     */
    public String getSeme() {
        return seme;
    }

    /**
     * Metodo para establecer el atributo seme
     *
     * @param seme El valor del atributo seme a establecer.
     */
    public void setSeme(String seme) {
        this.seme = seme;
    }

    /**
     * Metodo para obtener el atributo detalleMovimientos
     *
     * @return El atributo detalleMovimientos
     */
    public String getDetalleMovimientos() {
        return detalleMovimientos;
    }

    /**
     * Metodo para establecer el atributo detalleMovimientos
     *
     * @param detalleMovimientos El valor del atributo detalleMovimientos a establecer.
     */
    public void setDetalleMovimientos(String detalleMovimientos) {
        this.detalleMovimientos = detalleMovimientos;
    }

    @Override
    public String toString() {
        return "DetalleReferenciaDto{" +
                "idRegistro='" + idRegistro + '\'' +
                ", folioRelacionado='" + folioRelacionado + '\'' +
                ", divisa='" + divisa + '\'' +
                ", custodio='" + custodio + '\'' +
                ", total='" + total + '\'' +
                ", tipoMensaje='" + tipoMensaje + '\'' +
                ", seme='" + seme + '\'' +
                ", detalleMovimientos='" + detalleMovimientos + '\'' +
                ", mensajeISO='" + mensajeISO + '\'' +
                '}';
    }
}

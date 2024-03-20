package com.indeval.portalinternacional.middleware.servicios.dto.cuentasTransitoriasEfectivo;

import java.io.Serializable;
import java.math.BigDecimal;

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
     * Número de Registro, ROWNUM
     */
    private BigDecimal numeroRegistro;

    /**
     * ID de transacción generado para identificación de los movimientos
     * <p>
     * NULL: Saldos Negativos
     * 1: TIPO_MENSAJE 566 son depositos y se suman
     * 1: TIPO_MENSAJE = 900 AND ID_CUSTODIO IN (2, 14) son depositos y se suman
     * -1: TIPO_MENSAJE = 910 AND ID_CUSTODIO IN (2, 14) son retiros y se restan, para los custodios\n" +
     * 0: TIPO_MENSAJE IN (900, 910) AND ID_CUSTODIO = 13 son informativos
     */
    private BigDecimal idTipoTransaccion;

    /**
     * Tipo de transacción generado para identificación de los movimientos
     * <p>
     * NULL: Saldos Negativos
     * Deposito: TIPO_MENSAJE 566 son depositos y se suman
     * Deposito: TIPO_MENSAJE = 900 AND ID_CUSTODIO IN (2, 14) son depositos y se suman
     * Retiro: TIPO_MENSAJE = 910 AND ID_CUSTODIO IN (2, 14) son retiros y se restan, para los custodios\n" +
     * Informativo: TIPO_MENSAJE IN (900, 910) AND ID_CUSTODIO = 13 son informativos
     */
    private String tipoTransaccion;

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

    /**
     * Metodo para obtener el atributo numeroRegistro
     *
     * @return El atributo numeroRegistro
     */
    public BigDecimal getNumeroRegistro() {
        return numeroRegistro;
    }

    /**
     * Metodo para establecer el atributo numeroRegistro
     *
     * @param numeroRegistro El valor del atributo numeroRegistro a establecer.
     */
    public void setNumeroRegistro(BigDecimal numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    /**
     * Metodo para obtener el atributo idTipoTransaccion
     *
     * @return El atributo idTipoTransaccion
     */
    public BigDecimal getIdTipoTransaccion() {
        return idTipoTransaccion;
    }

    /**
     * Metodo para establecer el atributo idTipoTransaccion
     *
     * @param idTipoTransaccion El valor del atributo idTipoTransaccion a establecer.
     */
    public void setIdTipoTransaccion(BigDecimal idTipoTransaccion) {
        this.idTipoTransaccion = idTipoTransaccion;
    }

    /**
     * Metodo para obtener el atributo tipoTransaccion
     *
     * @return El atributo tipoTransaccion
     */
    public String getTipoTransaccion() {
        return tipoTransaccion;
    }

    /**
     * Metodo para establecer el atributo tipoTransaccion
     *
     * @param tipoTransaccion El valor del atributo tipoTransaccion a establecer.
     */
    public void setTipoTransaccion(String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    @Override
    public String toString() {
        return "DetalleReferenciaDto{" +
                ", numeroRegistro=" + numeroRegistro +
                "idRegistro='" + idRegistro + '\'' +
                ", folioRelacionado='" + folioRelacionado + '\'' +
                ", idCustodio='" + idCustodio + '\'' +
                ", custodio='" + custodio + '\'' +
                ", idDivisa='" + idDivisa + '\'' +
                ", divisa='" + divisa + '\'' +
                ", tipoMensaje='" + tipoMensaje + '\'' +
                ", idTipoTransaccion=" + idTipoTransaccion +
                ", tipoTransaccion='" + tipoTransaccion + '\'' +
                ", detalleMovimientos='" + detalleMovimientos + '\'' +
                ", seme='" + seme + '\'' +
                ", mensajeISO='" + mensajeISO + '\'' +
                ", montoNegativo=" + montoNegativo +
                '}';
    }
}

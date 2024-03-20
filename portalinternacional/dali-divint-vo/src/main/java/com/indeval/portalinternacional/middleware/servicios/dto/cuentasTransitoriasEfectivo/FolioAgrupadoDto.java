package com.indeval.portalinternacional.middleware.servicios.dto.cuentasTransitoriasEfectivo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO que contiene la informacion de las cuentas transitorias de efectivo agrupadas por FOLIO_RELACIONADO.
 *
 * @author Jacito
 */
public class FolioAgrupadoDto implements Serializable {

    /**
     * Id para la serializacion
     */
    private static final long serialVersionUID = -2709202303152671028L;

    /**
     * FOLIO_RELACIONADO : Referencia
     */
    protected String folioRelacionado;

    /**
     * CUSTODIO : custodio
     */
    protected String idCustodio;
    protected String custodio;

    /**
     * DIVISA : Divisa
     */
    protected String idDivisa;
    protected String divisa;
    protected String divisaExtendida;


    /**
     * REGISTROS : Cantidad
     */
    private String registros;

    /**
     * Indica el monto TOTAL de los movimientos
     */
    protected BigDecimal total;

    /**
     * Indica si el monto TOTAL de los movimientos es NEGATIVO
     */
    protected boolean montoNegativo;

    /**
     * Constructor de la clase.
     */
    public FolioAgrupadoDto() {
        super();
    }

    /**
     * Metodo para obtener el atributo folioRelacionado
     *
     * @return El atributo folioRelacionado
     */
    public String getFolioRelacionado() {
        return folioRelacionado;
    }

    /**
     * Metodo para establecer el atributo folioRelacionado
     *
     * @param folioRelacionado El valor del atributo folioRelacionado a establecer.
     */
    public void setFolioRelacionado(String folioRelacionado) {
        this.folioRelacionado = folioRelacionado;
    }

    /**
     * Metodo para obtener el atributo idCustodio
     *
     * @return El atributo idCustodio
     */
    public String getIdCustodio() {
        return idCustodio;
    }

    /**
     * Metodo para establecer el atributo idCustodio
     *
     * @param idCustodio El valor del atributo idCustodio a establecer.
     */
    public void setIdCustodio(String idCustodio) {
        this.idCustodio = idCustodio;
    }

    /**
     * Metodo para obtener el atributo custodio
     *
     * @return El atributo custodio
     */
    public String getCustodio() {
        return custodio;
    }

    /**
     * Metodo para establecer el atributo custodio
     *
     * @param custodio El valor del atributo custodio a establecer.
     */
    public void setCustodio(String custodio) {
        this.custodio = custodio;
    }

    /**
     * Metodo para obtener el atributo idDivisa
     *
     * @return El atributo idDivisa
     */
    public String getIdDivisa() {
        return idDivisa;
    }

    /**
     * Metodo para establecer el atributo idDivisa
     *
     * @param idDivisa El valor del atributo idDivisa a establecer.
     */
    public void setIdDivisa(String idDivisa) {
        this.idDivisa = idDivisa;
    }

    /**
     * Metodo para obtener el atributo divisa
     *
     * @return El atributo divisa
     */
    public String getDivisa() {
        return divisa;
    }

    /**
     * Metodo para establecer el atributo divisa
     *
     * @param divisa El valor del atributo divisa a establecer.
     */
    public void setDivisa(String divisa) {
        this.divisa = divisa;
    }

    /**
     * Metodo para obtener el atributo divisaExtendida
     *
     * @return El atributo divisaExtendida
     */
    public String getDivisaExtendida() {
        return divisaExtendida;
    }

    /**
     * Metodo para establecer el atributo divisaExtendida
     *
     * @param divisaExtendida El valor del atributo divisaExtendida a establecer.
     */
    public void setDivisaExtendida(String divisaExtendida) {
        this.divisaExtendida = divisaExtendida;
    }


    /**
     * Metodo para obtener el atributo registros
     *
     * @return El atributo registros
     */
    public String getRegistros() {
        return registros;
    }

    /**
     * Metodo para establecer el atributo registros
     *
     * @param registros El valor del atributo registros establecer.
     */
    public void setRegistros(String registros) {
        this.registros = registros;
    }

    /**
     * Metodo para obtener el atributo total
     *
     * @return El atributo total
     */
    public BigDecimal getTotal() {
        return total;
    }

    /**
     * Metodo para establecer el atributo total
     *
     * @param total El valor del atributo total a establecer.
     */
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    /**
     * Metodo para obtener el atributo montoNegativo
     *
     * @return El atributo montoNegativo
     */
    public boolean isMontoNegativo() {
        return montoNegativo;
    }

    /**
     * Metodo para establecer el atributo montoNegativo
     *
     * @param montoNegativo El valor del atributo montoNegativo a establecer.
     */
    public void setMontoNegativo(boolean montoNegativo) {
        this.montoNegativo = montoNegativo;
    }


    @Override
    public String toString() {
        return "FolioAgrupadoDto{" +
                "folioRelacionado='" + folioRelacionado + '\'' +
                ", idCustodio='" + idCustodio + '\'' +
                ", custodio='" + custodio + '\'' +
                ", idDivisa='" + idDivisa + '\'' +
                ", divisa='" + divisa + '\'' +
                ", divisaExtendida='" + divisaExtendida + '\'' +
                ", registros='" + registros + '\'' +
                ", total=" + total +
                ", montoNegativo=" + montoNegativo +
                '}';
    }
}

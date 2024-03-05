/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model.protocolofinanciero;

import java.math.BigInteger;
import java.util.Date;

/**
 * @hibernate.mapping
 *
 * @hibernate.class
 *   table=T_BITACORA_MATCH
 */
public class BitacoraMatch {
    
    private BigInteger idBitacora;

    private String codigoOperacion;

    private String idTraspasante;

    private String idReceptor;

    private String folioTraspasante;

    private String folioReceptor;

    private String folioInstruccionTraspasante;

    private String folioInstruccionReceptor;

    private Date fecha;

    private String instancia;

    /**
     * @hibernate.id generator-class="native"
     *  column="id_bitacora"
     */
    public BigInteger getIdBitacora() {
        return idBitacora;
    }

    /**
     * @hibernate.property
     *   column="codigo_operacion"
     *   not-null="false"
     */
    public String getCodigoOperacion() {
        return codigoOperacion;
    }

    /**
     * @hibernate.property
     *   column="id_traspasante"
     *   not-null="false"
     */
    public String getIdTraspasante() {
        return idTraspasante;
    }

    /**
     * @hibernate.property
     *   column="id_receptor"
     *   not-null="false"
     */
    public String getIdReceptor() {
        return idReceptor;
    }

    /**
     * @hibernate.property
     *   column="folio_traspasante"
     *   not-null="false"
     */
    public String getFolioTraspasante() {
        return folioTraspasante;
    }

    /**
     * @hibernate.property
     *   column="folio_receptor"
     *   not-null="false"
     */
    public String getFolioReceptor() {
        return folioReceptor;
    }

    /**
     * @hibernate.property
     *   column="folio_instruccion_traspasante"
     *   not-null="false"
     */
    public String getFolioInstruccionTraspasante() {
        return folioInstruccionTraspasante;
    }

    /**
     * @hibernate.property
     *   column="folio_instruccion_receptor"
     *   not-null="false"
     */
    public String getFolioInstruccionReceptor() {
        return folioInstruccionReceptor;
    }

    /**
     * @hibernate.property
     *   column="fecha"
     *   not-null="false"
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @hibernate.property
     *   column="instancia"
     *   not-null="false"
     */
    public String getInstancia() {
        return instancia;
    }


    public void setIdBitacora(BigInteger idBitacora) {
        this.idBitacora = idBitacora;
    }

    public void setCodigoOperacion(String codigoOperacion) {
        this.codigoOperacion = codigoOperacion;
    }

    public void setIdTraspasante(String idTraspasante) {
        this.idTraspasante = idTraspasante;
    }

    public void setIdReceptor(String idReceptor) {
        this.idReceptor = idReceptor;
    }

    public void setFolioTraspasante(String folioTraspasante) {
        this.folioTraspasante = folioTraspasante;
    }

    public void setFolioReceptor(String folioReceptor) {
        this.folioReceptor = folioReceptor;
    }

    public void setFolioInstruccionTraspasante(String folioInstruccionTraspasante) {
        this.folioInstruccionTraspasante = folioInstruccionTraspasante;
    }

    public void setFolioInstruccionReceptor(String folioInstruccionReceptor) {
        this.folioInstruccionReceptor = folioInstruccionReceptor;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setInstancia(String instancia) {
        this.instancia = instancia;
    }
}

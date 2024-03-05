/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model.protocolofinanciero;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Clob;
import java.util.Date;

/**
 * @hibernate.mapping
 *
 * @hibernate.class
 *   table=T_REGISTRO_INSTRUCCIONES_MATCH
 *   lazy=true
 */
public class RegistroInstruccionesMatch implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private BigInteger idBitacoraMatch; // key?

    private String folioInstruccion; // key?

    private String folioMatch;

    private String estadoInstruccion; // key?

    private String tipoOperacion; // key?

    private String tipoMensaje; // key?

    private Date fechaHoraRecepcion; // key?

    private Date fechaLiquidacion;

    private BigInteger matchKey;

    private Clob mensaje;

    private String expira;

    private String confirmacion;

    private String idFolioReceptor;
    
    private String idFolioTraspasante;
    

    /**
     * @hibernate.property
     *   column="confirmacion"
     *   not-null="true"
     */
    public String getConfirmacion() {
        return confirmacion;
    }

    /**
     * @hibernate.id generator-class="native"
     *  column="id_bitacora_match"
     */
    public BigInteger getIdBitacoraMatch() {
        return idBitacoraMatch;
    }

    /**
     * @hibernate.property
     *   column="folio_instruccion"
     *   not-null="true"
     */
    public String getFolioInstruccion() {
        return folioInstruccion;
    }

    /**
     * @hibernate.property
     *   column="folio_match"
     *   not-null="false"
     */
    public String getFolioMatch() {
        return folioMatch;
    }

    /**
     * @hibernate.property
     *   column="estado_instruccion"
     *   not-null="true"
     */
    public String getEstadoInstruccion() {
        return estadoInstruccion;
    }

    /**
     * @hibernate.property
     *   column="tipo_operacion"
     *   not-null="true"
     */
    public String getTipoOperacion() {
        return tipoOperacion;
    }

    /**
     * @hibernate.property
     *   column="tipo_mensaje"
     *   not-null="true"
     */
    public String getTipoMensaje() {
        return tipoMensaje;
    }

    /**
     * @hibernate.property
     *   column="fecha_hora_recepcion"
     *   not-null="true"
     */
    public Date getFechaHoraRecepcion() {
        return fechaHoraRecepcion;
    }

    /**
     * @hibernate.property
     *   column="fecha_liquidacion"
     *   not-null="false"
     */
    public Date getFechaLiquidacion() {
        return fechaLiquidacion;
    }

    /**
     * @hibernate.property
     *   column="match_key"
     *   not-null="false"
     */
    public BigInteger getMatchKey() {
        return matchKey;
    }

    /**
     * @hibernate.property
     *   column="mensaje"
     *   not-null="false"
     */
    public Clob getMensaje() {
        return mensaje;
    }

    /**
     * @hibernate.property
     *   column="expira"
     *   not-null="false"
     */
    public String getExpira() {
        return expira;
    }

    /**
     * @hibernate.property
     *   column="id_folio_receptor"
     *   not-null="false"
     */
	public String getIdFolioReceptor() {
		return idFolioReceptor;
	}

	/**
     * @hibernate.property
     *   column="id_folio_traspasante"
     *   not-null="false"
     */
	public String getIdFolioTraspasante() {
		return idFolioTraspasante;
	}


    public void setIdBitacoraMatch(BigInteger idBitacoraMatch) {
        this.idBitacoraMatch = idBitacoraMatch;
    }

    public void setFolioInstruccion(String folioInstruccion) {
        this.folioInstruccion = folioInstruccion;
    }

    public void setFolioMatch(String folioMatch) {
        this.folioMatch = folioMatch;
    }

    public void setEstadoInstruccion(String estadoInstruccion) {
        this.estadoInstruccion = estadoInstruccion;
    }

    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public void setTipoMensaje(String tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }

    public void setFechaHoraRecepcion(Date fechaHoraRecepcion) {
        this.fechaHoraRecepcion = fechaHoraRecepcion;
    }

    public void setFechaLiquidacion(Date fechaLiquidacion) {
        this.fechaLiquidacion = fechaLiquidacion;
    }

    public void setMatchKey(BigInteger matchKey) {
        this.matchKey = matchKey;
    }

    public void setMensaje(Clob mensaje) {
        this.mensaje = mensaje;
    }

    public void setExpira(String expira) {
        this.expira = expira;
    }

    /**
     * @param confirmacion the confirmacion to set
     */
    public void setConfirmacion(String confirmacion) {
        this.confirmacion = confirmacion;
    }

	public void setIdFolioReceptor(String idFolioReceptor) {
		this.idFolioReceptor = idFolioReceptor;
	}

	public void setIdFolioTraspasante(String idFolioTraspasante) {
		this.idFolioTraspasante = idFolioTraspasante;
	}

}

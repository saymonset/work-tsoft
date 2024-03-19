package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;
import java.util.Date;

public class BitacoraMensajeSwiftVO implements Serializable {

    /**
     *Clase modificada para multidivisas
     * Se hace el cambio de entidad a Inmutable para poder consultar
     * al tiempo T_BITACORA_SWIFT y T_CUENTA_TRANSITORIA (donde viven los mensajes 9XX)
     */
    private static final long serialVersionUID = 1L;
    private Long id;//ID_BITACORA_MENSAJE
    private Long idCalendario;//ID_CALENDARIO_INT
    private Date fecha;//FECHA_NOTIFICACION
    private String mensaje;//MENSAJE
    private String origen;//ORIGEN
    private String tipoMensaje;//TIPO_MENSAJE

    private String mensajeISO;

    public BitacoraMensajeSwiftVO() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param id
     * @param idCalendario
     * @param fecha
     * @param mensaje
     * @param origen
     * @param tipoMensaje
     */
    public BitacoraMensajeSwiftVO(Long id, Long idCalendario, Date fecha,
                                String mensaje, String origen, String tipoMensaje) {
        this.id = id;
        this.idCalendario = idCalendario;
        this.fecha = fecha;
        this.mensaje = mensaje;
        this.origen = origen;
        this.tipoMensaje = tipoMensaje;
    }

    /**
     * @return the id
     */

    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the idCalendario
     */
    public Long getIdCalendario() {
        return idCalendario;
    }

    /**
     * @param idCalendario the idCalendario to set
     */
    public void setIdCalendario(Long idCalendario) {
        this.idCalendario = idCalendario;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje the mensaje to set
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * @return the origen
     */
    public String getOrigen() {
        return origen;
    }

    /**
     * @param origen the origen to set
     */
    public void setOrigen(String origen) {
        this.origen = origen;
    }

    /**
     * @return the tipoMensaje
     */
    public String getTipoMensaje() {
        return tipoMensaje;
    }

    /**
     * @param tipoMensaje the tipoMensaje to set
     */
    public void setTipoMensaje(String tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }

    public String getMensajeISO() {
        return mensajeISO;
    }

    public void setMensajeISO(String mensajeISO) {
        this.mensajeISO = mensajeISO;
    }
}

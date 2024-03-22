package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;
import java.util.Date;

public class TcuentaTransitoriaVO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long id;//ID_BITACORA_MENSAJE
    private Long idTransaccion;//ID_CALENDARIO_INT
    private Long idCalendario;//ID_CALENDARIO_INT

    private Long idDivisa;//ID_CALENDARIO_INT
    private Date fecha;//FECHA_NOTIFICACION
    private String mensaje;//MENSAJE
    private String origen;//ORIGEN
    private String tipoMensaje;//TIPO_MENSAJE
    //Dato usado para Multidivisas - Se usa para el calculo de Importe segun los mensajes
    private Double importe;//IMPORTE

    public TcuentaTransitoriaVO() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param id
     * @param idCalendario
     * @param fecha
     * @param mensaje
     * @param origen
     * @param tipoMensaje
     * @param importe
     */
    public TcuentaTransitoriaVO(Long id, Long idCalendario, Long idDivisa, Date fecha,
                                String mensaje, String origen, String tipoMensaje, Double importe) {
        this.id = id;
        this.idCalendario = idCalendario;
        this.idDivisa = idDivisa;
        this.fecha = fecha;
        this.mensaje = mensaje;
        this.origen = origen;
        this.tipoMensaje = tipoMensaje;
        this.importe = importe;
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

    //AÑADIDO PARA MULTIDIVISAS - CALCULO DE IMPORTE SEGUN EL TIPO DE MENSAJES

    public Double getImporte(){
        return importe;
    }
    //Añadido para multidivisas - para el calculo de importe
    public void setImporte(Double importe){
        this.importe=importe;
    }

    public Long getIdDivisa() {
        return idDivisa;
    }

    public void setIdDivisa(Long idDivisa) {
        this.idDivisa = idDivisa;
    }

    public Long getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(Long idTransaccion) {
        this.idTransaccion = idTransaccion;
    }
}

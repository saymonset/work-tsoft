// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.servicios.modelo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "T_RETIRO_EFECTIVO_INT_PENDIENTES")
public class RetiroEfectivoIntPendientes implements Serializable {

    @Id
    @Column(name = "ID_MOVIMIENTO_RETIRO_EFECTIVO_INT", unique = true, nullable = false)
    private Integer idMovimientoRetiroEfectivoInt;

    @Column(name = "MENSAJE")
    private String mensaje;

    @Column(name = "ORIGEN")
    private String origen;

    @Column(name = "ID_ESTATUS", unique = true, nullable = false)
    private Integer idEstatus;

    @Column(name = "FECHA_REGISTRO")
    private Date fechaRegistro;

    @Column(name = "FECHA_PROCESO")
    private Date fechaProceso;

    @Column(name = "COMENTARIO")
    private String comentario;

    public Integer getIdMovimientoRetiroEfectivoInt() {
        return idMovimientoRetiroEfectivoInt;
    }

    public void setIdMovimientoRetiroEfectivoInt(Integer idMovimientoRetiroEfectivoInt) {
        this.idMovimientoRetiroEfectivoInt = idMovimientoRetiroEfectivoInt;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public Integer getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(Integer idEstatus) {
        this.idEstatus = idEstatus;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaProceso() {
        return fechaProceso;
    }

    public void setFechaProceso(Date fechaProceso) {
        this.fechaProceso = fechaProceso;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}

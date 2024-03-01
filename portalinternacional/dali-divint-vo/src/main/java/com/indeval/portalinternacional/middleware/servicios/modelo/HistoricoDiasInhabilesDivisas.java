/**
 * Cambio Multidivisas
 */
package com.indeval.portalinternacional.middleware.servicios.modelo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Modelo que representa la tabla T_HISTORICO_DIAS_INHABILES_DIVISAS.
 */
@Entity
@Table(name = "T_HISTORICO_DIAS_INHABILES_DIVISAS")
public class HistoricoDiasInhabilesDivisas implements Serializable {

    private static final long serialVersionUID = -2198929088144500134L;

    @Id
    @Column(name = "ID_HISTORICO")
    private Long idHistorico;
    @Column(name = "CREADOR")
    private String creador;
    @Column(name = "FECHA_CREACION")
    private Date fechaCreacion;
    @Column(name = "FECHA_ULT_MODIFICACION")
    private Date fechaUltModificacion;
    @Column(name = "REGISTROS_CORRECTOS")
    Integer registrosCorrectos;
    @Column(name = "REGISTROS_ERROR")
    Integer registrosError;
    @Column(name = "REGISTROS_TOTAL")
    Integer registrosTotal;
    @Column(name = "USUARIO_CHEKER")
    private String usuarioChecker;
    @Column(name = "ESTATUS")
    private Integer estatus;
    @Column(name = "NOMBRE_ARCHIVO")
    private String nombreArchivo;

    public HistoricoDiasInhabilesDivisas() {
    }

    public HistoricoDiasInhabilesDivisas(Long idHistorico, String creador, Date fechaCreacion, Date fechaUltModificacion,
                                         Integer registrosCorrectos, Integer registrosError, Integer registrosTotal,
                                         String usuarioChecker, Integer estatus, String nombreArchivo) {
        this.idHistorico = idHistorico;
        this.creador = creador;
        this.fechaCreacion = fechaCreacion;
        this.fechaUltModificacion = fechaUltModificacion;
        this.registrosCorrectos = registrosCorrectos;
        this.registrosError = registrosError;
        this.registrosTotal = registrosTotal;
        this.usuarioChecker = usuarioChecker;
        this.estatus = estatus;
        this.nombreArchivo = nombreArchivo;
    }

    public Long getIdHistorico() {
        return idHistorico;
    }

    public void setIdHistorico(Long idHistorico) {
        this.idHistorico = idHistorico;
    }

    public String getCreador() {
        return creador;
    }

    public void setCreador(String creador) {
        this.creador = creador;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaUltModificacion() {
        return fechaUltModificacion;
    }

    public void setFechaUltModificacion(Date fechaUltModificacion) {
        this.fechaUltModificacion = fechaUltModificacion;
    }

    public Integer getRegistrosCorrectos() {
        return registrosCorrectos;
    }

    public void setRegistrosCorrectos(Integer registrosCorrectos) {
        this.registrosCorrectos = registrosCorrectos;
    }

    public Integer getRegistrosError() {
        return registrosError;
    }

    public void setRegistrosError(Integer registrosError) {
        this.registrosError = registrosError;
    }

    public Integer getRegistrosTotal() {
        return registrosTotal;
    }

    public void setRegistrosTotal(Integer registrosTotal) {
        this.registrosTotal = registrosTotal;
    }

    public String getUsuarioChecker() {
        return usuarioChecker;
    }

    public void setUsuarioChecker(String usuarioChecker) {
        this.usuarioChecker = usuarioChecker;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    @Override
    public String toString() {
        return "HistoricoDiasInhabilesDivisas{" +
                "idHistorico=" + idHistorico +
                ", creador='" + creador + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaUltModificacion=" + fechaUltModificacion +
                ", registrosCorrectos=" + registrosCorrectos +
                ", registrosError=" + registrosError +
                ", registrosTotal=" + registrosTotal +
                ", usuarioChecker='" + usuarioChecker + '\'' +
                ", estatus=" + estatus +
                ", nombreArchivo='" + nombreArchivo + '\'' +
                '}';
    }
}

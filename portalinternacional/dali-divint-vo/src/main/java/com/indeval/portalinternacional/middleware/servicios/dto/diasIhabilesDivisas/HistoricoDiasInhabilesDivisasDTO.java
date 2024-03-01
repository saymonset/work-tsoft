/**
 * Multidivisas: Días inhábiles por Divisa DTO
 */
package com.indeval.portalinternacional.middleware.servicios.dto.diasIhabilesDivisas;

import com.indeval.portaldali.persistence.util.DateUtils;
import com.indeval.portalinternacional.middleware.servicios.constantes.EstatusDB;

import javax.swing.*;
import java.util.Date;
import java.util.List;

public class HistoricoDiasInhabilesDivisasDTO {

    private Long idHistorico;
    private String divisaClave;
    private String divisaDescripcion;
    private String creador;
    private Date fechaCreacion;
    private Date fechaUltModificacion;
    Integer registrosCorrectos;
    Integer registrosError;
    Integer registrosTotal;
    private String usuarioChecker;
    private Integer estatus;
    private EstatusDB estadoDB;
    private String nombreArchivo;

    private boolean puedeAutorizar;
    private boolean puedeCancelar;

    public HistoricoDiasInhabilesDivisasDTO() {
    }

    public Long getIdHistorico() {
        return idHistorico;
    }

    public void setIdHistorico(Long idHistorico) {
        this.idHistorico = idHistorico;
    }

    public String getDivisaClave() {
        return divisaClave;
    }

    public void setDivisaClave(String divisaClave) {
        this.divisaClave = divisaClave;
    }

    public String getDivisaDescripcion() {
        return divisaDescripcion;
    }

    public void setDivisaDescripcion(String divisaDescripcion) {
        this.divisaDescripcion = divisaDescripcion;
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

    public EstatusDB getEstadoDB() {
        return estadoDB;
    }

    public void setEstadoDB(EstatusDB estadoDB) {
        this.estadoDB = estadoDB;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public boolean isPuedeAutorizar() {
        return puedeAutorizar;
    }

    public void setPuedeAutorizar(boolean puedeAutorizar) {
        this.puedeAutorizar = puedeAutorizar;
    }

    public boolean isPuedeCancelar() {
        return puedeCancelar;
    }

    public void setPuedeCancelar(boolean puedeCancelar) {
        this.puedeCancelar = puedeCancelar;
    }

    public String toDetalleString() {
        StringBuilder sb = new StringBuilder("[");
        sb.append("idHistorico=").append(this.idHistorico).append(", ");
        sb.append("creador=").append(this.creador).append(", ");
        sb.append("fechaCreacion=").append(DateUtils.format(this.fechaCreacion, "dd/MM/yyyy")).append(", ");
        sb.append("registrosTotal=").append(this.registrosTotal).append(", ");
        sb.append("registrosCorrectos=").append(this.registrosCorrectos).append(", ");
        sb.append("registrosError=").append(this.registrosError).append(", ");
        sb.append("estatus=").append(this.estatus).append(", ");
        sb.append("nombreArchivo =").append(this.nombreArchivo);
        sb.append("] ");
        return sb.toString();
    }

    @Override
    public String toString() {
        return "HistoricoDiasInhabilesDivisasDTO{" +
                "idHistorico=" + idHistorico +
                ", divisaClave='" + divisaClave + '\'' +
                ", divisaDescripcion='" + divisaDescripcion + '\'' +
                ", creador='" + creador + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaUltModificacion=" + fechaUltModificacion +
                ", registrosCorrectos=" + registrosCorrectos +
                ", registrosError=" + registrosError +
                ", registrosTotal=" + registrosTotal +
                ", usuarioChecker='" + usuarioChecker + '\'' +
                ", estatus=" + estatus +
                ", estadoDB=" + estadoDB +
                ", nombreArchivo='" + nombreArchivo + '\'' +
                ", puedeAutorizar=" + puedeAutorizar +
                ", puedeCancelar=" + puedeCancelar +
                '}';
    }
}

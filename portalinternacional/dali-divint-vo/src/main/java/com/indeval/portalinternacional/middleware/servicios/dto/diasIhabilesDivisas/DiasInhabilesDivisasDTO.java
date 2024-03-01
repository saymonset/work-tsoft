/*
 * Multidivisas: Días inhábiles por Divisa DTO
 */
package com.indeval.portalinternacional.middleware.servicios.dto.diasIhabilesDivisas;

import com.indeval.portaldali.persistence.util.DateUtils;

import java.util.Date;

public class DiasInhabilesDivisasDTO {

    private long idDiasInhabiles;
    private Date diaInhabil;
    private String anio;
    private String mes;
    private String dia;
    private Long divisaId;
    private String divisaClave;
    private String divisaDescripcion;
    private String creador;
    private Date fechaCreacion;
    private Date fechaUltModificacion;
    private Long idHistoricoDiasInhabilesDivisas;
    private Integer estatus;
    private boolean seleccionadoEliminar;

    public DiasInhabilesDivisasDTO() {
    }

    public long getIdDiasInhabiles() {
        return idDiasInhabiles;
    }

    public void setIdDiasInhabiles(long idDiasInhabiles) {
        this.idDiasInhabiles = idDiasInhabiles;
    }

    public Date getDiaInhabil() {
        return diaInhabil;
    }

    public void setDiaInhabil(Date diaInhabil) {
        this.diaInhabil = diaInhabil;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public Long getDivisaId() {
        return divisaId;
    }

    public void setDivisaId(Long divisaId) {
        this.divisaId = divisaId;
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

    public Long getIdHistoricoDiasInhabilesDivisas() {
        return idHistoricoDiasInhabilesDivisas;
    }

    public void setIdHistoricoDiasInhabilesDivisas(Long idHistoricoDiasInhabilesDivisas) {
        this.idHistoricoDiasInhabilesDivisas = idHistoricoDiasInhabilesDivisas;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public boolean isSeleccionadoEliminar() {
        return seleccionadoEliminar;
    }

    public void setSeleccionadoEliminar(boolean seleccionadoEliminar) {
        this.seleccionadoEliminar = seleccionadoEliminar;
    }


    public String toDetalleString() {
        StringBuilder sb = new StringBuilder("[");
        sb.append("idDiasInhabilesDivisas=").append(this.idDiasInhabiles).append(", ");
        sb.append("diaInhabil=").append(DateUtils.format(this.diaInhabil, "dd/MM/yyyy")).append(", ");
        sb.append("idDivisa=").append(this.divisaId).append(", ");
        sb.append("divisaClave=").append(this.divisaClave).append(", ");
        sb.append("divisaDescripcion=").append(this.divisaDescripcion).append(", ");
        sb.append("creador=").append(this.creador).append(", ");
        sb.append("fechaCreacion=").append(DateUtils.format(this.fechaCreacion, "dd/MM/yyyy")).append(", ");
        sb.append("estatus=").append(this.estatus).append(", ");
        sb.append("idHistoricoDiasInhabilesDivisas=").append(this.idHistoricoDiasInhabilesDivisas);
        sb.append("] ");
        return sb.toString();
    }



    @Override
    public String toString() {
        return "DiasInhabilesDivisasDTO{" +
                "idDiasInhabiles=" + idDiasInhabiles +
                ", diaInhabil=" + diaInhabil +
                ", anio='" + anio + '\'' +
                ", mes='" + mes + '\'' +
                ", dia='" + dia + '\'' +
                ", divisaId=" + divisaId +
                ", divisaClave='" + divisaClave + '\'' +
                ", divisaDescripcion='" + divisaDescripcion + '\'' +
                ", creador='" + creador + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaUltModificacion=" + fechaUltModificacion +
                ", idHistoricoDiasInhabilesDivisas=" + idHistoricoDiasInhabilesDivisas +
                ", estatus=" + estatus +
                ", seleccionadoEliminar=" + seleccionadoEliminar +
                '}';
    }
}

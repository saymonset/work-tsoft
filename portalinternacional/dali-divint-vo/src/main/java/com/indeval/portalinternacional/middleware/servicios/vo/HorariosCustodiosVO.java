package com.indeval.portalinternacional.middleware.servicios.vo;

import com.indeval.portaldali.persistence.util.DateUtils;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;

public class HorariosCustodiosVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idHorariosCustodios;
    private Integer idDivisa;
    private Integer idCustodio;
    private String horarioInicial;
    private String horarioFinal;
    private String creador;
    private Date fechaCreacion;
    private Integer estatus;
    private String usuarioChecker;
    private String custodioNombre;
    private String custodioDescripcion;
    private String divisaClave;
    private String divisaDescripcion;

    private boolean seleccionadoAutorizar;
    private boolean seleccionadoCancelar;


    public Integer getIdHorariosCustodios() {
        return idHorariosCustodios;
    }

    public void setIdHorariosCustodios(Integer idHorariosCustodios) {
        this.idHorariosCustodios = idHorariosCustodios;
    }

    public Integer getIdDivisa() {
        return idDivisa;
    }

    public void setIdDivisa(Integer idDivisa) {
        this.idDivisa = idDivisa;
    }

    public Integer getIdCustodio() {
        return idCustodio;
    }

    public void setIdCustodio(Integer idCustodio) {
        this.idCustodio = idCustodio;
    }

    public String getHorarioInicial() {
        return horarioInicial;
    }

    public void setHorarioInicial(String horarioInicial) {
        this.horarioInicial = horarioInicial;
    }

    public String getHorarioFinal() {
        return horarioFinal;
    }

    public void setHorarioFinal(String horarioFinal) {
        this.horarioFinal = horarioFinal;
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

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public String getUsuarioChecker() {
        if (this.usuarioChecker == null) {
            return "";
        } else {
            return usuarioChecker;
        }
    }

    public void setUsuarioChecker(String usuarioChecker) {
        this.usuarioChecker = usuarioChecker;
    }

    public String getCustodioNombre() {
        return custodioNombre;
    }

    public void setCustodioNombre(String custodioNombre) {
        this.custodioNombre = custodioNombre;
    }

    public String getCustodioDescripcion() {
        return custodioDescripcion;
    }

    public void setCustodioDescripcion(String custodioDescripcion) {
        this.custodioDescripcion = custodioDescripcion;
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

    public boolean isSeleccionadoAutorizar() {
        return seleccionadoAutorizar;
    }

    public void setSeleccionadoAutorizar(boolean seleccionadoAutorizar) {
        this.seleccionadoAutorizar = seleccionadoAutorizar;
    }

    public boolean isSeleccionadoCancelar() {
        return seleccionadoCancelar;
    }

    public void setSeleccionadoCancelar(boolean seleccionadoCancelar) {
        this.seleccionadoCancelar = seleccionadoCancelar;
    }

    public String toDetalleString() {
        StringBuilder sb = new StringBuilder("[");
        sb.append("idHorariosCustodios=").append(this.idHorariosCustodios).append(", ");
        sb.append("idDivisa=").append(this.idDivisa).append(", ");
        sb.append("divisaClave=").append(this.divisaClave).append(", ");
        sb.append("idCustodio=").append(this.idCustodio).append(", ");
        sb.append("custodioNombre=").append(this.custodioNombre).append(", ");
        sb.append("horarioInicial=").append(this.horarioInicial).append(", ");
        sb.append("horarioFinal=").append(this.horarioFinal).append(", ");
        sb.append("creador=").append(this.creador).append(", ");
        sb.append("fechaCreacion=").append(DateUtils.format(this.fechaCreacion, "dd/MM/yyyy")).append(", ");
        sb.append("estatus=").append(this.estatus);
        sb.append("] ");
        return sb.toString();
    }

    @Override
    public String toString() {
        return "HorariosCustodiosVO{" +
                "idHorariosCustodios=" + idHorariosCustodios +
                ", idDivisa=" + idDivisa +
                ", idCustodio=" + idCustodio +
                ", horarioInicial='" + horarioInicial + '\'' +
                ", horarioFinal='" + horarioFinal + '\'' +
                ", creador='" + creador + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", estatus=" + estatus +
                ", usuarioChecker='" + usuarioChecker + '\'' +
                ", custodioNombre='" + custodioNombre + '\'' +
                ", custodioDescripcion='" + custodioDescripcion + '\'' +
                ", divisaClave='" + divisaClave + '\'' +
                ", divisaDescripcion='" + divisaDescripcion + '\'' +
                ", seleccionadoAutorizar=" + seleccionadoAutorizar +
                ", seleccionadoCancelar=" + seleccionadoCancelar +
                '}';
    }
}

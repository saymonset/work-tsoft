// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.servicios.modelo;

import com.indeval.portaldali.persistence.modelo.Divisa;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "C_HORARIOS_CUSTODIOS")
//@SequenceGenerator(name = "foliador", sequenceName = "SEQ_HORARIOS_CUSTODIOS", allocationSize = 1, initialValue = 1)
public class HorariosCustodios implements Serializable {

    /*@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
    @Column(name = "ID_HORARIOS_CUSTODIO")
    private Long idHorariosCustodios;*/

    @Id
    @Column(name = "ID_HORARIOS_CUSTODIO", unique = true, nullable = false)
    private Integer idHorariosCustodios;

    /*@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_DIVISA")
    private Divisa idDivisa;*/

    @Column(name = "ID_DIVISA", unique = true, nullable = false)
    private Integer idDivisa;

    /*@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_CUSTODIO")
    private Custodio idCustodio;*/

    @Column(name = "ID_CUSTODIO", unique = true, nullable = false)
    private Integer idCustodio;

    @Column(name = "HORARIO_INICIAL")
    private String horarioInicial;

    @Column(name = "HORARIO_FINAL")
    private String horarioFinal;

    @Column(name = "CREADOR")
    private String creador;

    @Column(name = "FECHA_CREACION")
    private Date fechaCreacion;

    @Column(name = "FECHA_ULT_MODIFICACION")
    private Date fechaUltModificacion;

    @Column(name = "ESTATUS")
    private Integer estatus;

    @Column(name = "USUARIO_CHEKER")
    private String usuarioChecker;

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

    public Date getFechaUltModificacion() {
        return fechaUltModificacion;
    }

    public void setFechaUltModificacion(Date fechaUltModificacion) {
        this.fechaUltModificacion = fechaUltModificacion;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public String getUsuarioChecker() {
        return usuarioChecker;
    }

    public void setUsuarioChecker(String usuarioChecker) {
        this.usuarioChecker = usuarioChecker;
    }
}

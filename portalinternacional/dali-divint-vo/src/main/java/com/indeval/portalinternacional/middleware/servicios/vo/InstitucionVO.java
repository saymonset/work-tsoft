package com.indeval.portalinternacional.middleware.servicios.vo;

import com.indeval.portaldali.persistence.modelo.Institucion;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class InstitucionVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long idInstitucion;

    private TipoInstitucionVO tipoInstitucion;

    private String folioInstitucion;

    private String bic;

    private String claveCasfim;

    private String nombreCorto;

    private Date fechaAlta;

    private Date fechaBaja;

    private EstadoInstitucionVO estadoInstitucion;

    private BigDecimal claveCasfim2;

    private String origen;

    private BigDecimal interna;

    private String razonSocial;

    private String rfc;

    private String domicilioFiscal;

    private String codigoPostal;

    private Long idCiudadNacional;

    private Long idEstadoNacional;

    private Long idPais;

    private String descEstado;

    private String descCiudad;

    private Long procesaTituloGlobal;

    private String catfin;

    private Date fechaCreacion;

    private Date fechaUltModificacion;

    public InstitucionVO(Institucion o) {
        if (o!=null){
            this.idInstitucion = o.getIdInstitucion();
            this.tipoInstitucion = new TipoInstitucionVO(o.getTipoInstitucion());
            this.folioInstitucion = o.getFolioInstitucion();
            this.bic = o.getBic();
            this.claveCasfim = o.getClaveCasfim();
            this.nombreCorto = o.getNombreCorto();
            this.fechaAlta = o.getFechaAlta();
            this.fechaBaja = o.getFechaBaja();
            this.estadoInstitucion = new EstadoInstitucionVO(o.getEstadoInstitucion());
            this.claveCasfim2 = o.getClaveCasfim2();
            this.origen = o.getOrigen();
            this.interna = o.getInterna();
            this.razonSocial = o.getRazonSocial();
            this.rfc = o.getRfc();
            this.domicilioFiscal = o.getDomicilioFiscal();
            this.codigoPostal = o.getCodigoPostal();
            this.idCiudadNacional =o.getIdCiudadNacional();
            this.idEstadoNacional = o.getIdEstadoNacional();
            this.idPais = o.getIdPais();
            this.descEstado = o.getDescEstado();
            this.descCiudad = o.getDescCiudad();
            this.procesaTituloGlobal = o.getProcesaTituloGlobal();
            this.catfin = o.getCatfin();
            this.fechaCreacion = o.getFechaCreacion();
            this.fechaUltModificacion = o.getFechaUltModificacion();
        }

    }

    public InstitucionVO(Long idInstitucion, TipoInstitucionVO tipoInstitucion, String folioInstitucion, String bic, String claveCasfim, String nombreCorto, Date fechaAlta, Date fechaBaja, EstadoInstitucionVO estadoInstitucion, BigDecimal claveCasfim2, String origen, BigDecimal interna, String razonSocial, String rfc, String domicilioFiscal, String codigoPostal, Long idCiudadNacional, Long idEstadoNacional, Long idPais, String descEstado, String descCiudad, Long procesaTituloGlobal, String catfin, Date fechaCreacion, Date fechaUltModificacion) {
        this.idInstitucion = idInstitucion;
        this.tipoInstitucion = tipoInstitucion;
        this.folioInstitucion = folioInstitucion;
        this.bic = bic;
        this.claveCasfim = claveCasfim;
        this.nombreCorto = nombreCorto;
        this.fechaAlta = fechaAlta;
        this.fechaBaja = fechaBaja;
        this.estadoInstitucion = estadoInstitucion;
        this.claveCasfim2 = claveCasfim2;
        this.origen = origen;
        this.interna = interna;
        this.razonSocial = razonSocial;
        this.rfc = rfc;
        this.domicilioFiscal = domicilioFiscal;
        this.codigoPostal = codigoPostal;
        this.idCiudadNacional = idCiudadNacional;
        this.idEstadoNacional = idEstadoNacional;
        this.idPais = idPais;
        this.descEstado = descEstado;
        this.descCiudad = descCiudad;
        this.procesaTituloGlobal = procesaTituloGlobal;
        this.catfin = catfin;
        this.fechaCreacion = fechaCreacion;
        this.fechaUltModificacion = fechaUltModificacion;
    }

    public InstitucionVO() {
    }

    public Long getIdInstitucion() {
        return idInstitucion;
    }

    public void setIdInstitucion(Long idInstitucion) {
        this.idInstitucion = idInstitucion;
    }

    public TipoInstitucionVO getTipoInstitucion() {
        return tipoInstitucion;
    }

    public void setTipoInstitucion(TipoInstitucionVO tipoInstitucion) {
        this.tipoInstitucion = tipoInstitucion;
    }

    public String getFolioInstitucion() {
        return folioInstitucion;
    }

    public void setFolioInstitucion(String folioInstitucion) {
        this.folioInstitucion = folioInstitucion;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public String getClaveCasfim() {
        return claveCasfim;
    }

    public void setClaveCasfim(String claveCasfim) {
        this.claveCasfim = claveCasfim;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public EstadoInstitucionVO getEstadoInstitucion() {
        return estadoInstitucion;
    }

    public void setEstadoInstitucion(EstadoInstitucionVO estadoInstitucion) {
        this.estadoInstitucion = estadoInstitucion;
    }

    public BigDecimal getClaveCasfim2() {
        return claveCasfim2;
    }

    public void setClaveCasfim2(BigDecimal claveCasfim2) {
        this.claveCasfim2 = claveCasfim2;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public BigDecimal getInterna() {
        return interna;
    }

    public void setInterna(BigDecimal interna) {
        this.interna = interna;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getDomicilioFiscal() {
        return domicilioFiscal;
    }

    public void setDomicilioFiscal(String domicilioFiscal) {
        this.domicilioFiscal = domicilioFiscal;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public Long getIdCiudadNacional() {
        return idCiudadNacional;
    }

    public void setIdCiudadNacional(Long idCiudadNacional) {
        this.idCiudadNacional = idCiudadNacional;
    }

    public Long getIdEstadoNacional() {
        return idEstadoNacional;
    }

    public void setIdEstadoNacional(Long idEstadoNacional) {
        this.idEstadoNacional = idEstadoNacional;
    }

    public Long getIdPais() {
        return idPais;
    }

    public void setIdPais(Long idPais) {
        this.idPais = idPais;
    }

    public String getDescEstado() {
        return descEstado;
    }

    public void setDescEstado(String descEstado) {
        this.descEstado = descEstado;
    }

    public String getDescCiudad() {
        return descCiudad;
    }

    public void setDescCiudad(String descCiudad) {
        this.descCiudad = descCiudad;
    }

    public Long getProcesaTituloGlobal() {
        return procesaTituloGlobal;
    }

    public void setProcesaTituloGlobal(Long procesaTituloGlobal) {
        this.procesaTituloGlobal = procesaTituloGlobal;
    }

    public String getCatfin() {
        return catfin;
    }

    public void setCatfin(String catfin) {
        this.catfin = catfin;
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
}

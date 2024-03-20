package com.indeval.portalinternacional.middleware.servicios.vo;



import com.indeval.portaldali.persistence.modelo.CuentaControlada;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CuentaControladaVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idCuentaControlada;
    private String cuenta;
    private String iban;
    private InstitucionVO institucion;
    private TipoCuentaVO tipoCuenta;
    private String nombreCuenta;
    private Date fechaAlta;
    private EstadoCuentaVO estadoCuenta;
    private Date fechaBaja;
    private BigDecimal idSector;


    public CuentaControladaVO() {
    }

    public CuentaControladaVO(CuentaControlada o) {
        if (o!=null){
            this.idCuentaControlada = o.getIdCuentaControlada();
            this.cuenta = o.getCuenta();
            this.iban = o.getIban();
            this.institucion = new InstitucionVO(o.getInstitucion());
            this.tipoCuenta = new TipoCuentaVO(o.getTipoCuenta());
            this.nombreCuenta = o.getNombreCuenta();
            this.fechaAlta = o.getFechaAlta();
            this.estadoCuenta = new EstadoCuentaVO(o.getEstadoCuenta());
            this.fechaBaja = o.getFechaBaja();
            this.idSector = o.getIdSector();
        }

    }

    public CuentaControladaVO(Long idCuentaControlada, String cuenta, String iban, InstitucionVO institucion,
                              TipoCuentaVO tipoCuenta, String nombreCuenta, Date fechaAlta, EstadoCuentaVO estadoCuenta,
                              Date fechaBaja, BigDecimal idSector) {
        this.idCuentaControlada = idCuentaControlada;
        this.cuenta = cuenta;
        this.iban = iban;
        this.institucion = institucion;
        this.tipoCuenta = tipoCuenta;
        this.nombreCuenta = nombreCuenta;
        this.fechaAlta = fechaAlta;
        this.estadoCuenta = estadoCuenta;
        this.fechaBaja = fechaBaja;
        this.idSector = idSector;
    }

    public Long getIdCuentaControlada() {
        return idCuentaControlada;
    }

    public void setIdCuentaControlada(Long idCuentaControlada) {
        this.idCuentaControlada = idCuentaControlada;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public InstitucionVO getInstitucion() {
        return institucion;
    }

    public void setInstitucion(InstitucionVO institucion) {
        this.institucion = institucion;
    }

    public TipoCuentaVO getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(TipoCuentaVO tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public String getNombreCuenta() {
        return nombreCuenta;
    }

    public void setNombreCuenta(String nombreCuenta) {
        this.nombreCuenta = nombreCuenta;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public EstadoCuentaVO getEstadoCuenta() {
        return estadoCuenta;
    }

    public void setEstadoCuenta(EstadoCuentaVO estadoCuenta) {
        this.estadoCuenta = estadoCuenta;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public BigDecimal getIdSector() {
        return idSector;
    }

    public void setIdSector(BigDecimal idSector) {
        this.idSector = idSector;
    }
}

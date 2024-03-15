package com.indeval.portalinternacional.middleware.servicios.vo;


import com.indeval.portaldali.persistence.modelo.CuentaNombrada;

import java.io.Serializable;
import java.util.Date;

public class CuentaNombradaVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idCuentaNombrada;
    private String cuenta;
    private String iban;
    private InstitucionVO institucion;
    private TipoCuentaVO tipoCuenta;
    private String razonSocialCuenta;
    private String nombreCuenta;
    private SectorVO sector;
    private Date fechaAlta;
    private EstadoCuentaVO estadoCuenta;
    private Date fechaBaja;
    private CuentaControladaVO cuentaControlada;

    public CuentaNombradaVO() {
    }

    public CuentaNombradaVO(CuentaNombrada o) {
        if (o!=null){
            this.idCuentaNombrada = o.getIdCuentaNombrada();
            this.cuenta = o.getCuenta();
            this.iban = o.getIban();
            this.institucion = new InstitucionVO(o.getInstitucion());
            this.tipoCuenta = new TipoCuentaVO(o.getTipoCuenta());
            this.razonSocialCuenta = o.getRazonSocialCuenta();
            this.nombreCuenta = o.getNombreCuenta();
            this.sector = new SectorVO(o.getSector());
            this.fechaAlta = o.getFechaAlta();
            this.estadoCuenta = new EstadoCuentaVO(o.getEstadoCuenta());
            this.fechaBaja = o.getFechaBaja();
            this.cuentaControlada = new CuentaControladaVO( o.getCuentaControlada());
        }

    }

    public CuentaNombradaVO(Long idCuentaNombrada, String cuenta, String iban, InstitucionVO institucion, TipoCuentaVO tipoCuenta, String razonSocialCuenta,
                            String nombreCuenta, SectorVO sector, Date fechaAlta, EstadoCuentaVO estadoCuenta, Date fechaBaja, CuentaControladaVO cuentaControlada) {
        this.idCuentaNombrada = idCuentaNombrada;
        this.cuenta = cuenta;
        this.iban = iban;
        this.institucion = institucion;
        this.tipoCuenta = tipoCuenta;
        this.razonSocialCuenta = razonSocialCuenta;
        this.nombreCuenta = nombreCuenta;
        this.sector = sector;
        this.fechaAlta = fechaAlta;
        this.estadoCuenta = estadoCuenta;
        this.fechaBaja = fechaBaja;
        this.cuentaControlada = cuentaControlada;
    }

    public Long getIdCuentaNombrada() {
        return idCuentaNombrada;
    }

    public void setIdCuentaNombrada(Long idCuentaNombrada) {
        this.idCuentaNombrada = idCuentaNombrada;
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

    public String getRazonSocialCuenta() {
        return razonSocialCuenta;
    }

    public void setRazonSocialCuenta(String razonSocialCuenta) {
        this.razonSocialCuenta = razonSocialCuenta;
    }

    public String getNombreCuenta() {
        return nombreCuenta;
    }

    public void setNombreCuenta(String nombreCuenta) {
        this.nombreCuenta = nombreCuenta;
    }

    public SectorVO getSector() {
        return sector;
    }

    public void setSector(SectorVO sector) {
        this.sector = sector;
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

    public CuentaControladaVO getCuentaControlada() {
        return cuentaControlada;
    }

    public void setCuentaControlada(CuentaControladaVO cuentaControlada) {
        this.cuentaControlada = cuentaControlada;
    }
}

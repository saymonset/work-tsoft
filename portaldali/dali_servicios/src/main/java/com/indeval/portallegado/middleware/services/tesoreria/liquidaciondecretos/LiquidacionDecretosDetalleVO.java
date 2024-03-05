package com.indeval.portallegado.middleware.services.tesoreria.liquidaciondecretos;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.validation.Errors;

import com.indeval.portallegado.middleware.services.AbstractBaseDTO;
import com.indeval.portallegado.middleware.services.modelo.EmisionVO;

public class LiquidacionDecretosDetalleVO extends AbstractBaseDTO {

    /**
     * Constante de serializacion
     */
    private static final long serialVersionUID = 1L;
    
    private EmisionVO emision;

    private Date fechaLiquidacion;

    private BigDecimal valorNominal;

    private BigDecimal saldoTitulos;

    private BigDecimal importeDecreto;

    private BigDecimal intereses;
    
    private BigDecimal importeCobrar;
    
    private String divisa;
    
    private BigDecimal tasaInteres;
    
    private BigDecimal tasaRendimientoDescto;
    
    private Integer plazo;
    
    private String idInstitucion;

    private String folioInstitucion;

    private String cuenta;

    private Integer idDerecho;

    private String descTipoEjercicio;

    private BigDecimal tasaDescuento;

    private BigDecimal tasaRendimiento;
    
    
    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getDescTipoEjercicio() {
        return descTipoEjercicio;
    }

    public void setDescTipoEjercicio(String descTipoEjercicio) {
        this.descTipoEjercicio = descTipoEjercicio;
    }

    public String getFolioInstitucion() {
        return folioInstitucion;
    }

    public void setFolioInstitucion(String folioInstitucion) {
        this.folioInstitucion = folioInstitucion;
    }

    public Integer getIdDerecho() {
        return idDerecho;
    }

    public void setIdDerecho(Integer idDerecho) {
        this.idDerecho = idDerecho;
    }

    public String getIdInstitucion() {
        return idInstitucion;
    }

    public void setIdInstitucion(String idInstitucion) {
        this.idInstitucion = idInstitucion;
    }

    public BigDecimal getTasaDescuento() {
        return tasaDescuento;
    }

    public void setTasaDescuento(BigDecimal tasaDescuento) {
        this.tasaDescuento = tasaDescuento;
    }

    public BigDecimal getTasaRendimiento() {
        return tasaRendimiento;
    }

    public void setTasaRendimiento(BigDecimal tasaRendimiento) {
        this.tasaRendimiento = tasaRendimiento;
    }

    public EmisionVO getEmision() {
        return emision;
    }

    public void setEmision(EmisionVO emision) {
        this.emision = emision;
    }

    public Date getFechaLiquidacion() {
        return fechaLiquidacion;
    }

    public void setFechaLiquidacion(Date fechaLiquidacion) {
        this.fechaLiquidacion = fechaLiquidacion;
    }

    public BigDecimal getImporteCobrar() {
        return importeCobrar;
    }

    public void setImporteCobrar(BigDecimal importeCobrar) {
        this.importeCobrar = importeCobrar;
    }

    public BigDecimal getImporteDecreto() {
        return importeDecreto;
    }

    public void setImporteDecreto(BigDecimal importeDecreto) {
        this.importeDecreto = importeDecreto;
    }

    public BigDecimal getIntereses() {
        return intereses;
    }

    public void setIntereses(BigDecimal intereses) {
        this.intereses = intereses;
    }

    public BigDecimal getSaldoTitulos() {
        return saldoTitulos;
    }

    public void setSaldoTitulos(BigDecimal saldoTitulos) {
        this.saldoTitulos = saldoTitulos;
    }

    public BigDecimal getValorNominal() {
        return valorNominal;
    }

    public void setValorNominal(BigDecimal valorNominal) {
        this.valorNominal = valorNominal;
    }

    public String getDivisa() {
        return divisa;
    }

    public void setDivisa(String divisa) {
        this.divisa = divisa;
    }

    public Integer getPlazo() {
        return plazo;
    }

    public void setPlazo(Integer plazo) {
        this.plazo = plazo;
    }

    public BigDecimal getTasaInteres() {
        return tasaInteres;
    }

    public void setTasaInteres(BigDecimal tasaInteres) {
        this.tasaInteres = tasaInteres;
    }

    public BigDecimal getTasaRendimientoDescto() {
        return tasaRendimientoDescto;
    }

    public void setTasaRendimientoDescto(BigDecimal tasaRendimientoDescto) {
        this.tasaRendimientoDescto = tasaRendimientoDescto;
    }

    public void validate(Object target, Errors errors) {
        // TODO Auto-generated method stub

    }

}

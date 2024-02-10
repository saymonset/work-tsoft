/**
 * Copyright (c) 2017 Bursatec. All Rights Reserved
 */
package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * Detalle de un file transfer al usuario.
 *
 */
public class FileTransferDetalleVO implements Serializable {

	/**
	 * Constante de serializacion
	 */
	private static final long serialVersionUID = 10L;

    /** 
     * el valor del id folio de la institucion 
     */
    private String idFolio;

    /**
     * El Tipo Valor 
     */
    private String tipoValor;
    
    /**
     * La Emisora
     */
    private String emisora;
    
    /**
     * La Serie
     */
    private String serie;
    
    /**
     * El ISIN
     */
    private String isin;
    
    /**
     * La Fecha de Liquidaci&oacute;n
     */
    private String fechaLiquidacion;
    
    /**
     * La Fecha de Liquidaci&oacute;n
     */
    private Date fechaLiquidacionDate;
    
    /**
     * La Fecha de Operaci&oacute;n
     */
    private String fechaOperacion;
    
    /**
     * La Fecha de Operaci&oacute;n
     */
    private Date fechaOperacionDate;
    
    /**
     * El Custodio Destino
     */
    private String custodioDestino;
    
    /** Cuanta Indeval asociada al custodio */
    private String cuentaIndeval;
    
    /**
     * La Cuenta Contraparte
     */
    private String cuentaContraparte;
    
    /**
     * La Descripcion de la Cuenta Contraparte
     */
    private String descCuentaContraparte;
    
    /**
     * El Depositante o Liquidador
     */
    private String depositanteLiquidador;
    
    /**
     * El Nombre de la Cuenta Beneficiario
     */
    private String nomCuentaBeneficiario;
    
    /**
     * El Numero de la Cuenta Beneficiario
     */
    private String numCuentaBeneficiario;
    
    /**
     * El tipo de movimiento (Entrega o Recepci&oacute;n)
     */
    private String tipoMovimiento;
    
    /**
     * "Username" del usuario firmado en el sistema
     */
    private String usuario;

	/**
	 * El id de la boveda.
	 */
	private Integer idBoveda;

    /**
     * La descripcion de la boveda.
     */
    private String boveda;

    /**
     * La descripcion de la boveda de efectivo.
     */
    private String bovedaEfectivo;

    /**
     * Errores de validacion.
     */
    private String error;

    /**
     * El id de la cuenta.
     */
    private Integer idCuenta;

    /**
     * El numero de cuenta.
     */
    private String cuenta;

    /**
     * El id del cupon vigente.
     */
    private Integer idCuponVigente;

    /**
     * La clave del cupon vigente.
     */
    private String claveCuponVigente;

    /**
     * El id de la emision.
     */
    private Integer idEmision;

    /**
     * El id de la institucion.
     */
    private Integer idInstitucion;

    /**
     * El id del tipo de cuenta.
     */
    private Integer idTipoCuenta;

    /**
     * La descripcion del tipo de cuenta.
     */
    private String descTipoCuenta;

    /**
     * El id de la divisa.
     */
    private Integer idDivisa;

    /**
     * La clave de la divisa.
     */
    private String claveAlfabeticaDivisa;


    /** DATOS ADICIONALES*/

    private String lugarDeLiquidacion;
    private Long idSicDetalle;
    private String cuentaBeneficiarioFinal;
    private String descripcionCuentaContraparte;
    private String descripcionBeneficiarioFinal;
    private String instruccionesEspeciales;
    private String custodio;
    private String usrCredencial;
    private BigDecimal posicion;

    public String getIdFolio() {
        return idFolio;
    }

    public void setIdFolio(String idFolio) {
        this.idFolio = idFolio;
    }

    public String getTipoValor() {
        return tipoValor;
    }

    public void setTipoValor(String tipoValor) {
        this.tipoValor = tipoValor;
    }

    public String getEmisora() {
        return emisora;
    }

    public void setEmisora(String emisora) {
        this.emisora = emisora;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public String getFechaLiquidacion() {
        return fechaLiquidacion;
    }

    public void setFechaLiquidacion(String fechaLiquidacion) {
        this.fechaLiquidacion = fechaLiquidacion;
    }

    public Date getFechaLiquidacionDate() {
        return fechaLiquidacionDate;
    }

    public void setFechaLiquidacionDate(Date fechaLiquidacionDate) {
        this.fechaLiquidacionDate = fechaLiquidacionDate;
    }

    public String getFechaOperacion() {
        return fechaOperacion;
    }

    public void setFechaOperacion(String fechaOperacion) {
        this.fechaOperacion = fechaOperacion;
    }

    public Date getFechaOperacionDate() {
        return fechaOperacionDate;
    }

    public void setFechaOperacionDate(Date fechaOperacionDate) {
        this.fechaOperacionDate = fechaOperacionDate;
    }

    public String getCustodioDestino() {
        return custodioDestino;
    }

    public void setCustodioDestino(String custodioDestino) {
        this.custodioDestino = custodioDestino;
    }

    public String getCuentaContraparte() {
        return cuentaContraparte;
    }

    public void setCuentaContraparte(String cuentaContraparte) {
        this.cuentaContraparte = cuentaContraparte;
    }

    public String getDescCuentaContraparte() {
        return descCuentaContraparte;
    }

    public void setDescCuentaContraparte(String descCuentaContraparte) {
        this.descCuentaContraparte = descCuentaContraparte;
    }

    public String getDepositanteLiquidador() {
        return depositanteLiquidador;
    }

    public void setDepositanteLiquidador(String depositanteLiquidador) {
        this.depositanteLiquidador = depositanteLiquidador;
    }

    public String getNomCuentaBeneficiario() {
        return nomCuentaBeneficiario;
    }

    public void setNomCuentaBeneficiario(String nomCuentaBeneficiario) {
        this.nomCuentaBeneficiario = nomCuentaBeneficiario;
    }

    public String getNumCuentaBeneficiario() {
        return numCuentaBeneficiario;
    }

    public void setNumCuentaBeneficiario(String numCuentaBeneficiario) {
        this.numCuentaBeneficiario = numCuentaBeneficiario;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Integer getIdBoveda() {
        return idBoveda;
    }

    public void setIdBoveda(Integer idBoveda) {
        this.idBoveda = idBoveda;
    }

    public String getBoveda() {
        return boveda;
    }

    public void setBoveda(String boveda) {
        this.boveda = boveda;
    }

    public String getBovedaEfectivo() {
        return bovedaEfectivo;
    }

    public void setBovedaEfectivo(String bovedaEfectivo) {
        this.bovedaEfectivo = bovedaEfectivo;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void addError(String error) {
        if (StringUtils.isBlank(this.error)) {
            this.error = error;
        }
        else {
            this.error += "\n" + error;
        }
    }

    public Integer getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Integer idCuenta) {
        this.idCuenta = idCuenta;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public Integer getIdCuponVigente() {
        return idCuponVigente;
    }

    public void setIdCuponVigente(Integer idCuponVigente) {
        this.idCuponVigente = idCuponVigente;
    }

    public String getClaveCuponVigente() {
        return claveCuponVigente;
    }

    public void setClaveCuponVigente(String claveCuponVigente) {
        this.claveCuponVigente = claveCuponVigente;
    }

    public Integer getIdEmision() {
        return idEmision;
    }

    public void setIdEmision(Integer idEmision) {
        this.idEmision = idEmision;
    }

    public Integer getIdInstitucion() {
        return idInstitucion;
    }

    public void setIdInstitucion(Integer idInstitucion) {
        this.idInstitucion = idInstitucion;
    }

    public Integer getIdTipoCuenta() {
        return idTipoCuenta;
    }

    public void setIdTipoCuenta(Integer idTipoCuenta) {
        this.idTipoCuenta = idTipoCuenta;
    }

    public String getDescTipoCuenta() {
        return descTipoCuenta;
    }

    public void setDescTipoCuenta(String descTipoCuenta) {
        this.descTipoCuenta = descTipoCuenta;
    }

    public Integer getIdDivisa() {
        return idDivisa;
    }

    public void setIdDivisa(Integer idDivisa) {
        this.idDivisa = idDivisa;
    }

    public String getClaveAlfabeticaDivisa() {
        return claveAlfabeticaDivisa;
    }

    public void setClaveAlfabeticaDivisa(String claveAlfabeticaDivisa) {
        this.claveAlfabeticaDivisa = claveAlfabeticaDivisa;
    }

    public String getLugarDeLiquidacion() {
        return lugarDeLiquidacion;
    }

    public void setLugarDeLiquidacion(String lugarDeLiquidacion) {
        this.lugarDeLiquidacion = lugarDeLiquidacion;
    }

    public Long getIdSicDetalle() {
        return idSicDetalle;
    }

    public void setIdSicDetalle(Long idSicDetalle) {
        this.idSicDetalle = idSicDetalle;
    }

    public String getCuentaBeneficiarioFinal() {
        return cuentaBeneficiarioFinal;
    }

    public void setCuentaBeneficiarioFinal(String cuentaBeneficiarioFinal) {
        this.cuentaBeneficiarioFinal = cuentaBeneficiarioFinal;
    }

    public String getDescripcionCuentaContraparte() {
        return descripcionCuentaContraparte;
    }

    public void setDescripcionCuentaContraparte(String descripcionCuentaContraparte) {
        this.descripcionCuentaContraparte = descripcionCuentaContraparte;
    }

    public String getDescripcionBeneficiarioFinal() {
        return descripcionBeneficiarioFinal;
    }

    public void setDescripcionBeneficiarioFinal(String descripcionBeneficiarioFinal) {
        this.descripcionBeneficiarioFinal = descripcionBeneficiarioFinal;
    }

    public String getInstruccionesEspeciales() {
        return instruccionesEspeciales;
    }

    public void setInstruccionesEspeciales(String instruccionesEspeciales) {
        this.instruccionesEspeciales = instruccionesEspeciales;
    }

    public String getCustodio() {
        return custodio;
    }

    public void setCustodio(String custodio) {
        this.custodio = custodio;
    }

    public String getUsrCredencial() {
        return usrCredencial;
    }

    public void setUsrCredencial(String usrCredencial) {
        this.usrCredencial = usrCredencial;
    }

    public BigDecimal getPosicion() {
        return posicion;
    }

    public void setPosicion(BigDecimal posicion) {
        this.posicion = posicion;
    }

    /**
     * Revisa si la operacion del file transfer recibida como parametro es equivalente a la actual.
     * @param op
     * @return
     */
    public boolean esEquivalente(FileTransferDetalleVO op) {
        if (this.getTipoValor().equals(op.getTipoValor()) && 
            this.getEmisora().equals(op.getEmisora()) &&
            this.getSerie().equals(op.getSerie()) &&
            this.getIsin().equals(op.getIsin())
            ) {
            return true;
        }
        return false;
    }

	/**
	 * Método para obtener el atributo cuentaIndeval
	 * @return El atributo cuentaIndeval
	 */
	public String getCuentaIndeval() {
		return cuentaIndeval;
	}

	/**
	 * Método para establecer el atributo cuentaIndeval
	 * @param cuentaIndeval El valor del atributo cuentaIndeval a establecer.
	 */
	public void setCuentaIndeval(String cuentaIndeval) {
		this.cuentaIndeval = cuentaIndeval;
	}

}

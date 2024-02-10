/*
 * Copyrigth (c) 2009 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.beneficiarios;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Clase para
 * @author Rafael Ibarra Zendejas
 */
public class RegistroExcelVO {

	

	private String idFolio;
	private String custodio;
	private String referenciaCustodio;
	private String nombre;
	private String calle;
	private String colonia;
	private String ciudad;
	private String estado;
	private String codigoPostal;
	private String dia;
	private String mes;
	private String anio;
    private String usIdNumber;
	private String tipoFormato;
    private String tipoEntidad;
	private String tipoBeneficiario;
	private String rfc;

	

	public RegistroExcelVO( ) {
		super();
	}

    public String getIdFolio() {
        return idFolio;
    }

    public void setIdFolio(String idFolio) {
        this.idFolio = idFolio;
    }

    public String getCustodio() {
        return custodio;
    }

    public void setCustodio(String custodio) {
        this.custodio = custodio;
    }

    public String getReferenciaCustodio() {
        return referenciaCustodio;
    }

    public void setReferenciaCustodio(String referenciaCustodio) {
        this.referenciaCustodio = referenciaCustodio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getUsIdNumber() {
        return usIdNumber;
    }

    public void setUsIdNumber(String usIdNumber) {
        this.usIdNumber = usIdNumber;
    }

    public String getTipoFormato() {
        return tipoFormato;
    }

    public void setTipoFormato(String tipoFormato) {
        this.tipoFormato = tipoFormato;
    }

    public String getTipoEntidad() {
        return tipoEntidad;
    }

    public void setTipoEntidad(String tipoEntidad) {
        this.tipoEntidad = tipoEntidad;
    }

    public String getTipoBeneficiario() {
        return tipoBeneficiario;
    }

    public void setTipoBeneficiario(String tipoBeneficiario) {
        this.tipoBeneficiario = tipoBeneficiario;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }


	@Override
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE).
				append("idFolio",idFolio).
				append("custodio",custodio).
				append("nombre",nombre).
                append("dia",dia).
                append("mes",mes).
                append("anio",anio).
				toString();
	}


}

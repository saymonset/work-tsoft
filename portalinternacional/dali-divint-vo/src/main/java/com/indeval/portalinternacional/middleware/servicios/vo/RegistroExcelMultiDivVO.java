package com.indeval.portalinternacional.middleware.servicios.vo;

import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portaldali.persistence.modelo.Divisa;
import com.indeval.portaldali.persistence.modelo.Institucion;

import java.util.Date;

public class RegistroExcelMultiDivVO {

	private Long idFileTransferMultidivisas;
	private String idFolio;

	private String claveTipoInst;

//	private String cuenta;
//	private String mtoBruto;
	private String retencion;
	private String comision;
	private String divisa;
	private String boveda;
	private String depAjuste;
	private String retAjuste;
	private String mtoNeto;

	private String tipoFormato;

	private String concepto;
	private String referencia;

	private Boveda oboveda;
	private Divisa odivisa;

	private Institucion oinstitucion;

	private Long estatusDivisas;
	private String comentarios;

	private Date FechaReg;

	private Long idInstitucion;

	private String divisaClaveAlfabeticaAux;
	private String bovedaNombreCortoAux;
	public String getIdFolio() {
		return idFolio;
	}

	public void setIdFolio(String idFolio) {
		this.idFolio = idFolio;
	}

//	public String getCuenta() {
//		return cuenta;
//	}
//
//	public void setCuenta(String cuenta) {
//		this.cuenta = cuenta;
//	}
//
//	public String getMtoBruto() {
//		return mtoBruto;
//	}
//
//	public void setMtoBruto(String mtoBruto) {
//		this.mtoBruto = mtoBruto;
//	}

	public String getRetencion() {
		return retencion;
	}

	public void setRetencion(String retencion) {
		this.retencion = retencion;
	}

	public String getComision() {
		return comision;
	}

	public void setComision(String comision) {
		this.comision = comision;
	}

	public String getDivisa() {
		return divisa;
	}

	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}

	public String getBoveda() {
		return boveda;
	}

	public void setBoveda(String boveda) {
		this.boveda = boveda;
	}

	public String getDepAjuste() {
		return depAjuste;
	}

	public void setDepAjuste(String depAjuste) {
		this.depAjuste = depAjuste;
	}

	public String getRetAjuste() {
		return retAjuste;
	}

	public void setRetAjuste(String retAjuste) {
		this.retAjuste = retAjuste;
	}

	public String getMtoNeto() {
		return mtoNeto;
	}

	public void setMtoNeto(String mtoNeto) {
		this.mtoNeto = mtoNeto;
	}

	public String getTipoFormato() {
		return tipoFormato;
	}

	public void setTipoFormato(String tipoFormato) {
		this.tipoFormato = tipoFormato;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public Boveda getOboveda() {
		return oboveda;
	}

	public void setOboveda(Boveda oboveda) {
		this.oboveda = oboveda;
	}

	public Divisa getOdivisa() {
		return odivisa;
	}

	public void setOdivisa(Divisa odivisa) {
		this.odivisa = odivisa;
	}

	public Long getIdFileTransferMultidivisas() {
		return idFileTransferMultidivisas;
	}

	public void setIdFileTransferMultidivisas(Long idFileTransferMultidivisas) {
		this.idFileTransferMultidivisas = idFileTransferMultidivisas;
	}

	public Long getEstatusDivisas() {
		return estatusDivisas;
	}

	public void setEstatusDivisas(Long estatusDivisas) {
		this.estatusDivisas = estatusDivisas;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public Institucion getOinstitucion() {
		return oinstitucion;
	}

	public void setOinstitucion(Institucion oinstitucion) {
		this.oinstitucion = oinstitucion;
	}

	public String getClaveTipoInst() {
		return claveTipoInst;
	}

	public void setClaveTipoInst(String claveTipoInst) {
		this.claveTipoInst = claveTipoInst;
	}

	public Date getFechaReg() {
		return FechaReg;
	}

	public void setFechaReg(Date fechaReg) {
		FechaReg = fechaReg;
	}

	public Long getIdInstitucion() {
		return idInstitucion;
	}

	public void setIdInstitucion(Long idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	public String getDivisaClaveAlfabeticaAux() {
		return divisaClaveAlfabeticaAux;
	}

	public void setDivisaClaveAlfabeticaAux(String divisaClaveAlfabeticaAux) {
		this.divisaClaveAlfabeticaAux = divisaClaveAlfabeticaAux;
	}

	public String getBovedaNombreCortoAux() {
		return bovedaNombreCortoAux;
	}

	public void setBovedaNombreCortoAux(String bovedaNombreCortoAux) {
		this.bovedaNombreCortoAux = bovedaNombreCortoAux;
	}
}

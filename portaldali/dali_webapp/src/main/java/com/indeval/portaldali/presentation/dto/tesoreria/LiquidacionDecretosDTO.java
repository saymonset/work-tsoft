package com.indeval.portaldali.presentation.dto.tesoreria;

import java.math.BigDecimal;
import java.util.Date;

import com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosVO;

public class LiquidacionDecretosDTO {
	
	  /**
	 * se agrego esta clase para obtener los datos de otra mas pequea
	 */
	private static final long serialVersionUID = 1L;
	
    private String nombreCorto;	
	 
	  private LiquidacionDecretosVO decretos;
	  
	  public LiquidacionDecretosDTO(LiquidacionDecretosVO decretos){
		  this.decretos= decretos;
		  
	  }
	  public LiquidacionDecretosDTO(){
		 		  
	  }
	  
	public String getNombreCorto() {
		return nombreCorto;
	}

	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}

	public void setIdInst(String idInst) {
		decretos.setIdInst(idInst);
	}
	
	public String getIdInst() {
		return decretos.getIdInst();
	}
	
	public String getFolioInst() {
		return decretos.getFolioInst();
	}

	public void setFolioInst(String folioInst) {
		decretos.setFolioInst(folioInst);
	}

	public String getTipoDerecho() {
		return decretos.getTipoDerecho();
	}

	public void setTipoDerecho(String tipoDerecho) {
		decretos.setTipoDerecho(tipoDerecho);
	}

	public String getCuenta() {
		return decretos.getCuenta();
	}

	public void setCuenta(String cuenta) {
		decretos.setCuenta(cuenta);
	}

	public String getTv() {
		return decretos.getTv();
	}

	public void setTv(String tv) {
		decretos.setTv(tv);
	}

	public String getEmisora() {
		return decretos.getEmisora();
	}

	public void setEmisora(String emisora) {
		decretos.setEmisora(emisora);
	}

	public String getSerie() {
		return decretos.getSerie();
	}

	public void setSerie(String serie) {
		decretos.setSerie(serie);
	}

	public String getCupon() {
		return decretos.getCupon();
	}

	public void setCupon(String cupon) {
		decretos.setCupon(cupon);
	}

	public BigDecimal getImporteACobrar() {
		return decretos.getImporteACobrar();
	}

	public void setImporteACobrar(BigDecimal importeACobrar) {
		decretos.setImporteACobrar(importeACobrar);
	}

	public BigDecimal getImporteCobrado() {
		return decretos.getImporteCobrado();
	}

	public void setImporteCobrado(BigDecimal importeCobrado) {
		decretos.setImporteCobrado(importeCobrado);
	}

	public BigDecimal getRemanente() {
		return decretos.getRemanente();
	}

	public void setRemanente(BigDecimal remanente) {
		decretos.setRemanente(remanente);
	}

	public Date getFechaLiq() {
		return decretos.getFechaLiq();
	}

	public void setFechaLiq(Date fechaLiq) {
		decretos.setFechaLiq(fechaLiq);
	}

	public Integer getIdDerecho() {
		return decretos.getIdDerecho();
	}

	public void setIdDerecho(Integer idDerecho) {
		decretos.setIdDerecho(idDerecho);
	}

	public String getIsin() {
		return decretos.getIsin();
	}

	public void setIsin(String isin) {
		decretos.setIsin(isin);
	}

	public String getDivisaPago() {
		return decretos.getDivisaPago();
	}

	public void setDivisaPago(String divisaPago) {
		decretos.setDivisaPago(divisaPago);
	}

	public Date getFechaVencimientoEmision() {
		return decretos.getFechaVencimientoEmision();
	}

	public void setFechaVencimientoEmision(Date fechaVencimientoEmision) {
		decretos.setFechaVencimientoEmision(fechaVencimientoEmision);
	}

	public Integer getIdMercado() {
		return decretos.getIdMercado();
	}

	public void setIdMercado(Integer idMercado) {
		decretos.setIdMercado(idMercado);
	}

	public Integer getIdTipoDerecho() {
		return decretos.getIdTipoDerecho();
	}

	public void setIdTipoDerecho(Integer idTipoDerecho) {
		decretos.setIdTipoDerecho(idTipoDerecho);
	}

	public Integer getIdTipoEjercicio() {
		return decretos.getIdTipoEjercicio();
	}

	public void setIdTipoEjercicio(Integer idTipoEjercicio) {
		decretos.setIdTipoEjercicio(idTipoEjercicio);
	}

	public Integer getFolioFija() {
		return decretos.getFolioFija();
	}

	public void setFolioFija(Integer folioFija) {
		decretos.setFolioFija(folioFija);
	}

	public Integer getFolioVariable() {
		return decretos.getFolioVariable();
	}

	public void setFolioVariable(Integer folioVariable) {
		decretos.setFolioVariable(folioVariable);
	}

	public Integer getIdBovedaEfectivo() {
		return decretos.getIdBovedaEfectivo();
	}

	public void setIdBovedaEfectivo(Integer idBovedaEfectivo) {
		decretos.setIdBovedaEfectivo(idBovedaEfectivo);
	}

    public String getDescBoveda() {
        return decretos.getDescBoveda();
    }

    public void setDescBoveda(String descBoveda) {
        decretos.setDescBoveda(descBoveda);
    }

}

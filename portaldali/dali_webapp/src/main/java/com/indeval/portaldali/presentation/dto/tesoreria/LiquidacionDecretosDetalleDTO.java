package com.indeval.portaldali.presentation.dto.tesoreria;

import java.math.BigDecimal;
import java.util.Date;

import com.indeval.sidv.decretos.persistence.model.vo.EmisionVO;
import com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosDetalleVO;

public class LiquidacionDecretosDetalleDTO {
	
  /* se agrego esta clase para obtener los datos de otra mas lista mas pequea
   * */
	private static final long serialVersionUID = 1L;
	
	private String nombreCorto;
	
	private EmisionVO emision;
	
	private LiquidacionDecretosDetalleVO detalle;
	
	public LiquidacionDecretosDetalleDTO(LiquidacionDecretosDetalleVO decretosDetalle){
		detalle=decretosDetalle;
	}
	
	public LiquidacionDecretosDetalleDTO(){
		
	}

	public String getNombreCorto() {
		return nombreCorto;
	}

	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}
	
	public Date getFechaLiquidacion() {
		return detalle.getFechaLiquidacion();
	}

	public void setFechaLiquidacion(Date fechaLiq) {
		detalle.setFechaLiquidacion(fechaLiq);
	}
	
	public BigDecimal getValorNominal() {
		return detalle.getValorNominal();
	}

	public void setValorNominal(BigDecimal valorNominal) {
		detalle.setValorNominal(valorNominal);
	}
	
	public BigDecimal getSaldoTitulos() {
		return detalle.getSaldoTitulos();
	}

	public void setSaldoTitulos(BigDecimal saldoTitulos) {
		detalle.setSaldoTitulos(saldoTitulos);
	}
	
	public BigDecimal getImporteDecreto() {
		return detalle.getImporteDecreto();
	}

	public void setImporteDecreto(BigDecimal importeDecreto) {
		detalle.setImporteDecreto(importeDecreto);
	}
	
	public BigDecimal getIntereses() {
		return detalle.getIntereses();
	}

	public void setIntereses(BigDecimal interes) {
		detalle.setIntereses(interes);
	}
	
	public BigDecimal getImporteCobrar() {
		return detalle.getImporteCobrar();
	}

	public void setImporteCobrar(BigDecimal importeCobrar) {
		detalle.setImporteCobrar(importeCobrar);
	}
	
	public String getDivisa() {
		return detalle.getDivisa();
	}

	public void setDivisa(String divisa){
		detalle.setDivisa(divisa);
	}
	
	public BigDecimal getTasaInteres() {
		return detalle.getTasaInteres();
	}

	public void setTasaInteres(BigDecimal tasaInteres) {
		detalle.setTasaInteres(tasaInteres);
	}
	
	public BigDecimal getTasaRendimientoDescto() {
		return detalle.getTasaInteres();
	}

	public void setTasaRendimientoDescto(BigDecimal tasaRendimientoDescto) {
		detalle.setTasaRendimientoDescto(tasaRendimientoDescto);
	}
	
	public Integer getPlazo() {
		return detalle.getPlazo();
	}

	public void setPlazo(Integer plazo) {
		detalle.setPlazo(plazo);
	}
	
	public String getIdInstitucion() {
		return detalle.getIdInstitucion();
	}

	public void setIdInstitucion(String idInstitucion){
		detalle.setIdInstitucion(idInstitucion);
	}
	
	public String getFolioInstitucion() {
		return detalle.getFolioInstitucion();
	}

	public void setFolioIntitucion(String folioInstitucion){
		detalle.setFolioInstitucion(folioInstitucion);
	}
	
	public String getCuenta() {
		return detalle.getCuenta();
	}

	public void setCuenta(String cuenta) {
		detalle.setCuenta(cuenta);
	}
	
	public Integer getIdDerecho() {
		return detalle.getIdDerecho();
	}

	public void setIdDerecho(Integer idDerecho) {
		detalle.setIdDerecho(idDerecho);
	}
	
	public String getDescTipoEjercicio() {
		return detalle.getDescTipoEjercicio();
	}

	public void setDescTipoEjercicio(String descTipoEjercicio) {
		detalle.setDescTipoEjercicio(descTipoEjercicio);
	}
	
	public BigDecimal getTasaDescuento(){
		return detalle.getTasaDescuento();
	}
	
	public void setTasaDescuento(BigDecimal tasaDescuento){
		detalle.setTasaDescuento(tasaDescuento);
	}
	
	public BigDecimal getTasaRendimiento(){
		return detalle.getTasaRendimiento();
	}
	
	public void setTasaRendimiento(BigDecimal tasaRendimiento){
		detalle.setTasaRendimiento(tasaRendimiento);
	}
	
	public Integer getIdBovedaEfectivo(){
		return detalle.getIdBovedaEfectivo();
	}
	
	public void setIdBovedaEfectivo(Integer idBovedaEfectivo){
		detalle.setIdBovedaEfectivo(idBovedaEfectivo);
	}
	
	public EmisionVO getEmision(){
		return detalle.getEmision();
	}
}

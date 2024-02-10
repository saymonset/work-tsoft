package com.indeval.portalinternacional.presentation.controller.derechos;

import java.util.Date;

import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.DerechoBeneficiario;

public class Derecho {
	
	private Long idDerecho;
	private Long idEmision;
	private EmisionVO emision;
	private Date fechaCorte;
	private Date fechaPago;
	private String divisa;
	private String boveda;
	private String estado;
	private Float proporcion;
	private Integer idEstatusDerecho;
	private String tipoDerecho;
	private String subtipoDerecho;
	private Integer idTipoDerecho;
	private Integer idSubTipoDerecho;
	private Float fee;
	
	public EmisionVO getEmision() {
		return emision;
	}
	public void setEmision(EmisionVO emision) {
		this.emision = emision;
	}
	public Date getFechaCorte() {
		return fechaCorte;
	}
	public void setFechaCorte(Date fechaCorte) {
		this.fechaCorte = fechaCorte;
	}
	public Date getFechaPago() {
		return fechaPago;
	}
	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
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
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Float getProporcion() {
		return proporcion;
	}
	public void setProporcion(Float proporcion) {
		this.proporcion = proporcion;
	}
	
	public void transform(com.indeval.portalinternacional.middleware.servicios.modelo.Derecho derecho,String estatus){
		this.idDerecho = derecho.getIdDerechoCapital();
		EmisionVO emisionVO = new EmisionVO();
		fechaCorte = derecho.getFechaCorte();
		fechaPago = derecho.getFechaPago();
		divisa = derecho.getDivisaProducto().getClaveAlfabetica();
		boveda = derecho.getBoveda().getNombreCorto();
		proporcion = derecho.getPrecioProducto();
		tipoDerecho = derecho.getTipoDerecho().getDescTipoDerecho();
		idTipoDerecho = derecho.getTipoDerecho().getIdTipoDerecho();
		if(derecho.getSubTipoDerecho() != null){
			subtipoDerecho = derecho.getSubTipoDerecho().getDescSubtipoDerecho();
			idSubTipoDerecho = derecho.getSubTipoDerecho().getIdSubtipoDerecho();
		}
		idEmision = derecho.getCuponOrigen().getEmision().getIdEmision();
		
		emisionVO.setCupon(derecho.getCuponOrigen().getClaveCupon());
		emisionVO.setEmisora(derecho.getCuponOrigen().getEmision().getEmisora().getClavePizarra());
		emisionVO.setIsin(derecho.getCuponOrigen().getEmision().getIsin());
		emisionVO.setSerie(derecho.getCuponOrigen().getEmision().getSerie());
		emisionVO.setTv(derecho.getCuponOrigen().getEmision().getInstrumento().getClaveTipoValor());
		this.emision = emisionVO;				
		
		if(estatus == null && derecho.getDerechosBeneficiarios() != null && !derecho.getDerechosBeneficiarios().isEmpty()){
			for(DerechoBeneficiario beneficiarioTmp : derecho.getDerechosBeneficiarios()){
				if(!beneficiarioTmp.getEliminado() && beneficiarioTmp.getAsignacion() == null && beneficiarioTmp.getCuentaNombrada() == null){
					estado = beneficiarioTmp.getEstatusDerecho().getDescEstatusDerecho();
					break;
				}
			}									
			idEstatusDerecho = derecho.getDerechosBeneficiarios().get(0).getEstatusDerecho().getIdEstatusDerecho();
		}else{			
			estado = estatus;
			idEstatusDerecho = Integer.valueOf(1);
		}		
	}
	public Long getIdDerecho() {
		return idDerecho;
	}
	public void setIdDerecho(Long idDerecho) {
		this.idDerecho = idDerecho;
	}
	public Integer getIdEstatusDerecho() {
		return idEstatusDerecho;
	}
	public void setIdEstatusDerecho(Integer idEstatusDerecho) {
		this.idEstatusDerecho = idEstatusDerecho;
	}
	public String getTipoDerecho() {
		return tipoDerecho;
	}
	public void setTipoDerecho(String tipoDerecho) {
		this.tipoDerecho = tipoDerecho;
	}
	public Long getIdEmision() {
		return idEmision;
	}
	public void setIdEmision(Long idEmision) {
		this.idEmision = idEmision;
	}
	public String getSubtipoDerecho() {
		return subtipoDerecho;
	}
	public void setSubtipoDerecho(String subtipoDerecho) {
		this.subtipoDerecho = subtipoDerecho;
	}
	public Integer getIdTipoDerecho() {
		return idTipoDerecho;
	}
	public Integer getIdSubTipoDerecho() {
		return idSubTipoDerecho;
	}
	public void setIdTipoDerecho(Integer idTipoDerecho) {
		this.idTipoDerecho = idTipoDerecho;
	}
	public void setIdSubTipoDerecho(Integer idSubTipoDerecho) {
		this.idSubTipoDerecho = idSubTipoDerecho;
	}
	public String getProporcionAsString() {		
		return Constantes.DECIMAL_FORMAT_PROPORCION.format(proporcion);
	}
	public Float getFee() {
		return fee;
	}
	public void setFee(Float fee) {
		this.fee = fee;
	}
	public String getFeeAsString() {		
		return Constantes.DECIMAL_FORMAT_FEE.format(fee);
	}

}

package com.indeval.portalinternacional.middleware.services.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.DerechoCapitalHistorico;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.DerechoCapitalHistoricoVo;

public class DTOAssemblerCapitales {
	
	private final static Logger log = LoggerFactory.getLogger(DTOAssemblerCapitales.class);
	
	public static DerechoCapitalHistorico getDerechoCapitalEntity(DerechoCapitalHistoricoVo derecho){
		DerechoCapitalHistorico entity= new DerechoCapitalHistorico();
		entity.setAdp(derecho.getAdp());
		//entity.setApellidoMaterno(derecho.getApellidoMaterno());
		//entity.setApellidoPaterno(derecho.getApellidoPaterno());
		entity.setAsignacion(derecho.getAsignacion());
		entity.setCuenta(derecho.getCuenta());
		entity.setCustodio(derecho.getDetalleCustodio());
		entity.setDescEstatusDerecho(derecho.getDescEstatusDerecho());
		entity.setDivisa(derecho.getClaveAlfabetica());
		entity.setEmisora(derecho.getClavePizarra());
		entity.setFechaCorte(derecho.getFechaCorte());
		entity.setFechaPago(derecho.getFechaPagoDerechoCapital());
		entity.setFee(derecho.getFee());
		entity.setIdBeneficiario(derecho.getIdBeneficiario());
		entity.setIdCatBic(derecho.getIdCatBic());
		entity.setIdCuentaNombradaBeneficiario(derecho.getIdCuentaNombradaBeneficiario());
		entity.setIdCuentaNombradaCuenta(derecho.getIdCuentaNombradaCuenta());
		entity.setIdDerechoCapital(derecho.getIdDerechoCapital());
		entity.setIdDivisa(derecho.getIdDivisa());
		entity.setIdDomicilioW(derecho.getDomicilio());
		entity.setIdEstatusDerecho(derecho.getIdEstatusDerecho());
		entity.setIdFolioInstitucion((StringUtils.isNotBlank(derecho.getIdFolio())) ? derecho.getIdFolio().toString() : null);
		entity.setIdTipoBeneficiario(derecho.getIdTipoBeneficiario());
		entity.setIdTipoDerecho(derecho.getIdTipoDerecho());
		entity.setImpuestoRetenido(derecho.getImpuestoRetenido());
		entity.setIsin(derecho.getIsin());
		entity.setMontoBruto(derecho.getMontoBruto());
		entity.setMontoNeto(derecho.getMontoNeto());
		//entity.setNombre(derecho.getNombre());
		entity.setPaisIncorporacion(derecho.getPaisIncorporacion());
		entity.setPorcentajeBen(derecho.getPorcentajeBen());
		entity.setPorcentajeHBDefault(derecho.getPorcentajeHBDefault());
		entity.setPorcentajeMav(derecho.getPorcentajeMav());
		entity.setPorcentajeRetencionCTB(derecho.getPorcentajeRetencionCTB());
		entity.setPorcentajeRetencionReal(derecho.getPorcentajeRetencionReal());
		entity.setProporcion(derecho.getProporcion());
		//entity.setRazonSocial(derecho.getRazonSocial());
		entity.setRfc(derecho.getRfc());
		entity.setSaldo(derecho.getSaldo());
		entity.setSerie(derecho.getSerie());
		entity.setTipoFormato(derecho.getTipoFormato());
		entity.setTipoValor(derecho.getClaveTipoValor());
		entity.setUoi(derecho.getUoi());
		entity.setMontoFee(derecho.getMontoFee());
		if(StringUtils.isNotEmpty(derecho.getNombreBenef())){
			entity.setBeneficiario(derecho.getNombreBenef()+
					(StringUtils.isNotEmpty(derecho.getApellidoPaternoBenef()) ?  " "+derecho.getApellidoPaternoBenef() :"" )+
					(StringUtils.isNotEmpty(derecho.getApellidoMaternoBenef()) ?  " "+derecho.getApellidoMaternoBenef() :"" )
					);
		}else{
			entity.setBeneficiario(derecho.getRazonSocialBenef());
		}
		entity.setFechaFormatoW(derecho.getFechaFormato());
		entity.setEstadoW(derecho.getEstatusBeneficiario());
		entity.setExemptFatcaRepCode(derecho.getExemptionFromFatcaRepCode());
		entity.setTiin(derecho.getTiin());
		entity.setSsnEin(derecho.getSsn());
		entity.setForeigntiin(derecho.getForeigntiin());
		entity.setReferenceNumber(derecho.getReferenceNumber());
		entity.setGiin(derecho.getGiin());
		entity.setExempPayeeCode(derecho.getExemptPayeeCode());
		entity.setFechaNacimientoFW(derecho.getFechaNacimiento());
		entity.setTipoDerecho(derecho.getDescTipoDerecho());
		return entity;
	}

}

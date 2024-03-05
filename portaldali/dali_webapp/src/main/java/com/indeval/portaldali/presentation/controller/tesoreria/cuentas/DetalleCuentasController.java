/*
 * Copyright (c) 2009 Bursatec. All Rights Reserved 
 */
package com.indeval.portaldali.presentation.controller.tesoreria.cuentas;

import java.math.BigInteger;

import javax.faces.context.FacesContext;

import org.apache.commons.lang.math.NumberUtils;

import com.indeval.portaldali.middleware.dto.CuentaRetiroEfectivoDTO;
import com.indeval.portaldali.middleware.services.tesoreria.cuentas.AdministracionCuentasRetiroService;
import com.indeval.portaldali.presentation.controller.common.ControllerBase;

/**
 * Backing bean para dar servicio a la pantalla de detalle de cuentas
 * @author Maria C. Buendia
 */
public class DetalleCuentasController extends ControllerBase {
	
	/** servicio de administracion de cuentas */
    private AdministracionCuentasRetiroService admonCuentasService;
    
	/** DTO para pasar los datos de la pantalla */
    private CuentaRetiroEfectivoDTO cuenta = new CuentaRetiroEfectivoDTO();
    
	/** define si se trata de cuentas nacionales(false) o internacionales(true), default=internacionales */
    private boolean sonCuentasNacional;
	
	/**
	 * Metodo de inicializacion de propiedades
	 * @return null
	 */
	public String getInit(){
		logger.debug("en detalle de cuenta...");
		FacesContext ctx = FacesContext.getCurrentInstance();
		String idCuenta = ctx.getExternalContext().getRequestParameterMap().get("idCuenta");
		String tipoCuenta = ctx.getExternalContext().getRequestParameterMap().get("tipoCuenta");
		
		logger.debug("idCuenta="+idCuenta);
		logger.debug("tipoCuenta="+tipoCuenta);
		
		if(tipoCuenta != null && tipoCuenta.equals("1")){//internacional
			sonCuentasNacional = false;
			cuenta = admonCuentasService.buscarCuenta(BigInteger.valueOf(NumberUtils.toLong(idCuenta)), sonCuentasNacional);
		}else if(tipoCuenta != null && tipoCuenta.equals("2")){//nacional
			sonCuentasNacional = true;
			cuenta = admonCuentasService.buscarCuenta(BigInteger.valueOf(NumberUtils.toLong(idCuenta)), sonCuentasNacional);
		}
		
		cuenta = cuenta==null? new CuentaRetiroEfectivoDTO():cuenta;
		
		return null;
	}

	public AdministracionCuentasRetiroService getAdmonCuentasService() {
		return admonCuentasService;
	}

	public void setAdmonCuentasService(
			AdministracionCuentasRetiroService admonCuentasService) {
		this.admonCuentasService = admonCuentasService;
	}

	public CuentaRetiroEfectivoDTO getCuenta() {
		return cuenta;
	}

	public void setCuenta(CuentaRetiroEfectivoDTO cuenta) {
		this.cuenta = cuenta;
	}

	public boolean isSonCuentasNacional() {
		return sonCuentasNacional;
	}

	public void setSonCuentasNacional(boolean sonCuentasNacional) {
		this.sonCuentasNacional = sonCuentasNacional;
	}
	
	
}

/**
 * 2H Software - Bursatec
 * 
 * Sistema de Consulta de Estados de Cuenta
 *
 * Jan 7, 2008
 *
 */
package com.indeval.portaldali.middleware.services.util.test;

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.CuentaEfectivoDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.EmisionDTO;
import com.indeval.portaldali.middleware.dto.EmisoraDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.MercadoDTO;
import com.indeval.portaldali.middleware.dto.PosicionDTO;
import com.indeval.portaldali.middleware.dto.SaldoEfectivoDTO;
import com.indeval.portaldali.middleware.dto.SerieDTO;
import com.indeval.portaldali.middleware.dto.TipoCuentaDTO;
import com.indeval.portaldali.middleware.dto.TipoNaturalezaDTO;
import com.indeval.portaldali.middleware.dto.TipoTenenciaDTO;
import com.indeval.portaldali.middleware.dto.TipoValorDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;

/**
 * Utileria que crea DTO's para ser utilizados en los criterios de búsqueda de las
 * pruebas unitarias.
 * 
 * @author Pablo Julián Balderas Méndez
 * 
 */
public class DTOUtil {

	/**
	 * Crea un DTO de {@link PosicionDTO} con los valores por default
	 * @return DTO de posicion con valores por default
	 */
	public static PosicionDTO crearPosicionDTO(String tipoCuenta, String naturalezaContable) {
		PosicionDTO posicionDTO = new PosicionDTO();
		
		BovedaDTO bovedaDTO = new BovedaDTO();
		bovedaDTO.setId(-1);		
		posicionDTO.setBoveda(bovedaDTO);
				
		CuentaDTO cuentaDTO = new CuentaDTO();
		cuentaDTO.setIdCuenta(-1);
		cuentaDTO.setNumeroCuenta("-1");
		InstitucionDTO institucionDTO = new InstitucionDTO();
		institucionDTO.setId(-1);
		cuentaDTO.setInstitucion(institucionDTO);
		TipoTenenciaDTO tipoTenenciaDTO = new TipoTenenciaDTO();
		tipoTenenciaDTO.setIdTipoCuenta(-1);
		tipoTenenciaDTO.setTipoCustodia("V");
		tipoTenenciaDTO.setTipoNaturaleza(new TipoNaturalezaDTO(naturalezaContable,""));
		tipoTenenciaDTO.setTipoCuenta(new TipoCuentaDTO(tipoCuenta,""));		
		cuentaDTO.setTipoTenencia(tipoTenenciaDTO);
		posicionDTO.setCuenta(cuentaDTO);
		
		EmisionDTO emisionDTO = new EmisionDTO();
		emisionDTO.setId(-1);
		SerieDTO serieDTO = new SerieDTO();
		serieDTO.setSerie("-1");
		EmisoraDTO emisoraDTO = new EmisoraDTO();
		emisionDTO.setSerie(serieDTO);
		emisoraDTO.setId(-1);
		emisionDTO.setEmisora(emisoraDTO);
		emisionDTO.setIsin("");
		TipoValorDTO tipoValorDTO = new TipoValorDTO();
		tipoValorDTO.setMercado(new MercadoDTO());
		tipoValorDTO.setId(-1);
		emisionDTO.setTipoValor(tipoValorDTO);
		posicionDTO.setEmision(emisionDTO);		
		
		return posicionDTO;
	}
	
	/**
	 * Crea un DTO de paginación. Por omisión la página es la 1 y el número de registros por 
	 * página es de 10.
	 * @return {@link EstadoPaginacionDTO} con los valores por default
	 */
	public static EstadoPaginacionDTO crearEstadoPaginacionDTO() {
		EstadoPaginacionDTO estadoPaginacionDTO = new EstadoPaginacionDTO();
		estadoPaginacionDTO.setNumeroPagina(1);
		estadoPaginacionDTO.setRegistrosPorPagina(10);
		return estadoPaginacionDTO;
	}
	
	/**
	 * Crea un objeto de tipo {@link SaldoEfectivoDTO} con los valores necesarios para ejecutar 
	 * una busqueda sin criterios especificos. 
	 * @param tipoCuenta Tipo de cuenta asociado al saldo, nombrada o controlada
	 * @return DTO de saldo efectivo con valores para realizar una búsqueda
	 */
	public static SaldoEfectivoDTO crearSaldoEfectivoDTO(String tipoCuenta, String naturalezaContable) {
		SaldoEfectivoDTO criterio = new SaldoEfectivoDTO();
		
		TipoCuentaDTO tipoCuentaDTO = new TipoCuentaDTO(tipoCuenta,"");		
		CuentaEfectivoDTO cuenta = new CuentaEfectivoDTO();
		cuenta.setTipoCuenta(tipoCuentaDTO);
		cuenta.setTipoCustodia("E");
		cuenta.setNumeroCuenta("-1");
		cuenta.setTipoNaturaleza(new TipoNaturalezaDTO(naturalezaContable,""));
		
		InstitucionDTO institucionDTO = new InstitucionDTO();
		cuenta.setInstitucion(institucionDTO);
		
		BovedaDTO boveda = new BovedaDTO();
		DivisaDTO divisa = new DivisaDTO();		
		boveda.setId(-1);
		divisa.setId(-1);
		
		criterio.setCuenta(cuenta);
		criterio.setBoveda(boveda);
		criterio.setDivisa(divisa);	
		
		return criterio;
	}
	
	
}

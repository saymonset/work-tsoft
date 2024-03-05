/*
 * Copyrigth (c) 2009 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.tesoreria;

import java.util.List;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.indeval.portaldali.middleware.dto.CuentaRetiroEfectivoDTO;
import com.indeval.portaldali.middleware.dto.CuentaRetiroInternacionalDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.RetiroEfectivoDTO;
import com.indeval.portaldali.middleware.dto.RetiroEfectivoInternacionalDTO;
import com.indeval.portaldali.middleware.dto.criterio.CriterioCuentaEfectivoDTO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;


/**
 * Servicio para la administracion de los retiros de efectivo
 * internacionales
 * 
 * @author Maria C. Buendia
 */
public interface AdmonRetirosEfectivoService {
	
	public String generaIso(RetiroEfectivoInternacionalDTO retiroEfectivoInternacionalDTO);
	/**
	 * Guarda un nuevo retiro
	 * @param retiro objeto retiro
	 */
	public void crearRetiro(RetiroEfectivoInternacionalDTO retiro);
	
	/**
	 * Guarda un nuevo retiro nacional
	 * @param retiro
	 */
	public void crearRetiro(RetiroEfectivoDTO retiro);
	
	/**
	 * Cambia el estado de un retiro.  
	 * @param retiro objeto retiro
	 * @param edoNuevo id del nuevo estado al que cambiar el retiro
	 */
	public void cambiarEstadoRetiro(Object retiro, BigInteger edoNuevo);
	
	/**
	 * Cambia el estado de un conjunto de retiros. 
	 * @param retiros lista de objetos retiro
	 * @param edoNuevo id del nuevo estado al que cambiarn los retiros
	 */
	public void cambiarEstadoRetiro(List<Object> retiros, BigInteger edoNuevo);
	
	/**
	 * Validaciones y reglas de negocio para el servicio que realiza la creacion
	 * de retiro de efectivo internacional
	 * @return boolean
	 * @throws BusinessException
	 */
	public boolean businessRulesCrearRetiro( DivisaDTO divisa, BigDecimal importe) throws BusinessException;
	
	/**
	 * Validaciones y reglas de negocio para el servicio que realiza la creacion
	 * de retiro de efectivo banca comercial
	 * @return boolean
	 * @throws BusinessException
	 */
	public boolean businessRulesCrearRetiroBancaComercial(RetiroEfectivoDTO cre);
	
	/**
	 * Obtiene las cuentas beneficiario en base a la divisa
	 * @param idDivisa id de la divisa seleccionada 
	 */
	public List<CuentaRetiroInternacionalDTO> getCuentasInterXDivisa(BigInteger idDivisa);
	
	/**
	 * Busca las cuentas por prefijo
	 * @param criterio datos para realizar la consulta 
	 * @param esNacional define si es nacional o no
	 * @return lista de objetos CuentaRetiroEfectivoDTO
	 */
	public List<CuentaRetiroEfectivoDTO> buscarCuentasPorPrefijo(CriterioCuentaEfectivoDTO criterio, boolean esNacional);
	
	/**
	 * Busca una cuenta por numero de cuenta
	 * @param numeroCuenta numero de cuenta
	 * @param esNacional define si es nacional o no
	 * @return objeto de tipo CuentaRetiroEfectivoDTO
	 */
	public CuentaRetiroEfectivoDTO buscarCuentaPorCriterio(CriterioCuentaEfectivoDTO criterio, boolean esNacional);
	
	/**
	 * Busca una cuenta clabe de la institucion
	 * @param cuentaBeneficiarioInsitucion
	 * @param idInstitucion
	 * @return objeto de tipo CuentaRetiroEfectivoDTO
	 */
	public CuentaRetiroEfectivoDTO buscarCuentaBeneficiario(String cuentaBeneficiarioInsitucion, Long idInstitucion);
	
	/**
	 * Busca un retiro de efectivo por referencia operacion
	 * @param referenciaOperacion
	 * @param esNacional define si es nacional o no 
	 * @return RetiroEfectivoInternacionalDTO o RetiroEfectivoDTO
	 */
	public Object buscarRetiroConCuentaPorReferenciaOperacion(String referenciaOperacion, boolean esNacional);
}

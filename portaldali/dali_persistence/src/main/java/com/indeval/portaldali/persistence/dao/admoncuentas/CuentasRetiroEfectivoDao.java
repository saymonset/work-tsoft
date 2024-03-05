/*
 * Copyrigth (c) 2009 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.admoncuentas;

import java.math.BigInteger;
import java.util.List;

import com.indeval.portaldali.middleware.dto.CuentaRetiroEfectivoDTO;
import com.indeval.portaldali.middleware.dto.CuentaRetiroInternacionalDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.RetiroEfectivoDTO;
import com.indeval.portaldali.middleware.dto.criterio.CriterioCuentaEfectivoDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.persistence.model.CuentaRetiro;
import com.indeval.portaldali.persistence.model.CuentaRetiroNacional;
import com.indeval.portaldali.persistence.model.CuentaRetiroInternacional;

/**
 * Interface que expone los m&eacute;todos para las acciones sobre las cuentas de 
 * retiro de efectivo nacional e internacional. 
 * 
 * @author Maria C. Buendia
 * @version 1.0
 */
public interface CuentasRetiroEfectivoDao {

	/** 
	 * Crea una nueva cuenta 
	 * @param cuenta objeto CuentaRetiro
	 */
	void save(CuentaRetiro cuenta);
	void save(CuentaRetiroNacional cuenta);
	void save(CuentaRetiroInternacional cuenta);
	
	/**
	 * Consulta la cuenta por id 
	 * @param id objeto Long que identifica una cuenta
	 * @param tipo 0 - nacional, 1 -internacional, 2 - cuenta retiro general 
	 */
	public Object getCuenta(BigInteger id, int tipo);
	
	/**
	 * Consulta la cuenta por id 
	 * @param id objeto Long que identifica una cuenta
	 */
	public CuentaRetiroEfectivoDTO getCuentaCompletaDTO(BigInteger id, boolean esNacional);
	
	public CuentaRetiroInternacionalDTO getCuentaRetiroInternacional(BigInteger id);
	
	
	/**
	 * Obtiene el total de registros para la consulta de cuentas
	 * 
	 * @param criterio
	 * @param sonNacional
	 * @param buscarExacto
	 * @return
	 */	
	public Integer obtenerProyeccionCuentas(CriterioCuentaEfectivoDTO criterio, boolean sonNacional, boolean buscarExacto);

	
	/**
	 * Consulta las cuentas en base a un criterio de busqueda
	 * @param criterio filtros de busqueda
	 */
	public List<CuentaRetiroEfectivoDTO> getCuentas(CriterioCuentaEfectivoDTO criterio, boolean sonNacional, boolean buscarExacto,EstadoPaginacionDTO paginacion);
	
	/**
	 * Consulta las cuentas nacionales x id de cuenta retiro
	 * @param id cuenta retiro 
	 */
	public CuentaRetiroEfectivoDTO getCuentaNacionalByCuentaRetiroNoEnMOS(Long id);
	
	/**
	 * Actualiza cuenta 
	 * @param cuenta objeto CuentaRetiro
	 */
	void saveOrUpdate(CuentaRetiroNacional cuenta);
	void saveOrUpdate(CuentaRetiroInternacional cuenta);
	void saveOrUpdate(CuentaRetiro cuenta);
	
	/**
	 * Elimina cuenta  
	 * @param cuenta objeto CuentaRetiro
	 */
	void delete(CuentaRetiroNacional cuenta);
	void delete(CuentaRetiroInternacional cuenta);
	
	/**
	 * Consulta las cuentas internacionales por divisa
	 * @param idDivisa identificador de divisa 
	 */
	List<CuentaRetiroInternacional> getCuentasInterXDivisa(BigInteger idDivisa);
	
	/**
	 * Consulta las cuentas nacionales por combinaciones de banco-cuenta-nombre del beneficiario.
	 * @param criterio filtros de busqueda
	 */
	List<CuentaRetiroEfectivoDTO> buscarCuentasBancoNombreCuentaBeneficiario(CriterioCuentaEfectivoDTO criterio);
	
	/**
	 * Consulta las cuentas para encontrar la combinacion de banco-cuenta del beneficiario y poder comparar el nombre
	 * @param beneficiario institucion (banco) beneficiario
	 * @param cuentaBeneficiario cuenta del beneficiario
	 */
	CuentaRetiroEfectivoDTO buscarPorCombinacionBancoCuentaBeneficiario(InstitucionDTO traspasante, InstitucionDTO beneficiario, String cuentaBeneficiario, String folioCuenta);
	
	/** 
	 * Buscar movimientos pendientes de la cuenta (retiros PE)
	 * @param id id de la cuenta, o cadena de id cuentas separadas por coma
	 * @return boolean 
	 */
	public List<RetiroEfectivoDTO> buscarMovimientosPendientesNal(String id);
	
	/**
	 * Crea la secuencia y obtiene el primer valor para crear la cuenta
	 * @param nombre de la secuencia
	 * @return id por institucion para la cuenta
	 */
	
	public Long getConsecutivoCreandoParaInstitucion(String nombre);
	
	/**
	 * Busca una cuenta por numero de cuenta
	 * @param numeroCuenta numero de cuenta
	 * @param esNacional define si es nacional o no
	 * @return objeto de tipo CuentaRetiroEfectivoDTO
	 */
	public CuentaRetiroEfectivoDTO getCuentaBeneficiario(String cuentaBeneficiarioInsitucion, Long idInstitucion);
	
}

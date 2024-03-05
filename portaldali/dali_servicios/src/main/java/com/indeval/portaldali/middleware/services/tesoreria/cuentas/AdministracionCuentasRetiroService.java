/*
 * Copyrigth (c) 2009 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.tesoreria.cuentas;

import java.util.List;
import java.util.Map;

import java.math.BigInteger;

import com.indeval.portaldali.middleware.dto.CuentaRetiroEfectivoDTO;
import com.indeval.portaldali.middleware.dto.CuentaRetiroInternacionalDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.EstadosCuentaDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.criterio.CriterioCuentaEfectivoDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;

/**
 * Servicio para la administracion de cuentas de retiro de efectivo
 * nacionales e internacionales
 * 
 * @author Maria C. Buendia
 */
public interface AdministracionCuentasRetiroService {
	
	
	public CuentaRetiroEfectivoDTO getCuentaNacionalByCuentaRetiroNoEnMOS(Long idCuenta);
	
	public Integer obtenerProyeccionCuentas(final CriterioCuentaEfectivoDTO criterio, final boolean sonNacional, boolean buscarExacto);
	
/*
	public String generaIso(CuentaRetiroInternacionalDTO cuentaRetiroInternacionalDTO);
	
	
	
	*/
	/**
	 * Busca el conjunto de cuentas de acuerdo a los filtros
	 * @param 
	 * @return lista de objetos  
	 */
	public List<CuentaRetiroEfectivoDTO> buscarCuentas(CriterioCuentaEfectivoDTO criterio, EstadoPaginacionDTO paginacion, boolean sonNacional, boolean buscarExacto);
	
	/**
	 * Buscar cuenta retiro completo
	 * @param id cuenta retiro
	 */
	public CuentaRetiroEfectivoDTO buscarCuenta(BigInteger id, boolean esNacional);
	
	public CuentaRetiroInternacionalDTO buscarCuentaRetiroInternacional(BigInteger id);
	
	
	
	/**
	 * Guarda una nueva cuenta internacional
	 * @param cuenta objeto cuenta internacional 
	 */
	public void crearCuenta(CuentaRetiroEfectivoDTO cuentaDTO);
	
	/**
	 * Elimina una cuenta. 
	 * @cuenta id de la cuenta a eliminar
	 */
	public void eliminarCuenta(BigInteger cuenta);
	
	/**
	 * Realiza cambios sobre una cuenta existente
	 * @param cuenta objeto cuenta 
	 */
	public void actualizarCuenta(CuentaRetiroEfectivoDTO cuentaDTO);
	
	/**
	 * Cambia el estado de una cuenta. La firma viene en cada objeto de cuenta.
	 * @param id id de la cuenta
	 * @param cveCortaEdoNuevo clave corta del estado al que se cambiara
	 * @param datosFirma mapa con los datos que se insertaran como parte de la firma
	 */
	public boolean cambiarEstadoCuenta(long id, String cveCortaEdoNuevo, Map<String,Object> datosFirma);
	
	/**
	 * Validaciones y reglas de negocio para el servicio que realiza la creacion
	 * de cuentas de retiro de efectivo
	 * 
	 * @param agente traspasante
	 * @return boolean
	 * @throws BusinessException
	 */
	public boolean businessRulesCrearCuentaRetiro(
			InstitucionDTO agente, InstitucionDTO beneficiario, DivisaDTO divisa, String cuentaBeneficiario, String nombreBeneficiario,
			String montoMensual, String montoDiario, String montoTransaccion, String movsMensuales, long[] esModificacion) throws BusinessException;
	
	/**
	 * Validaciones y reglas de negocio para el servicio que realiza la creacion
	 * de cuentas de retiro de efectivo internacional
	 * 
	 * @param agente traspasante
	 * @return boolean
	 * @throws BusinessException
	 */
	public boolean businessRulesCrearCuentaRetiroInternacional(
			DivisaDTO divisa,
			String cuentaBeneficiarioFinal, String nombreBeneficiarioFinal,
			String bancoBeneficiario, String nombreBancoBeneficiario, String cuentaBancoBeneficiario,
			String bancoIntermediario, String nombreBancoIntermediario, long[] esModificacion);
	
	/**
	 * Llena el combo de estados para la consulta de las cuentas 
	 */
	public List<EstadosCuentaDTO> getEstadosCuenta();
	
	/**
	 * Busca el estado por id 
	 */
	public EstadosCuentaDTO getEstadoPorId(long id);
	
	/**
	 * Obtiene el folio para la tabla CuentaRetiro
	 * @param esNacional true-obtiene la secuencia para cuentas nacionales
	 * 					 false-obtiene la secuencia para cuentas internacionales
	 * @return valor de la secuencia 
	 */
	public BigInteger obtenerFolioCuenta(boolean esNacional);
	
	/**
	 * Obtiene el folio para la tabla CuentaRetiro por institucion
	 * @param esNacional true-obtiene la secuencia para cuentas nacionales
	 * 					 false-obtiene la secuencia para cuentas internacionales
	 * @param idInstitucion id de bd de la institucion para la que se requiere el consecutivo de sus cuentas
	 * @return valor de la secuencia 
	 */
	public BigInteger obtenerFolioCuenta(boolean esNacional, Long idInstitucion);
	
	/**
	 * Define si es posible o no realizar modificaciones
	 * @return Map {"existenParametros"-Boolean, "aplicaModificaciones"-Boolean, "horarioModificacion"-String}
	 */
	public Map<String, Object> revisarModificacionesHabilitadas();
	
	/** 
	 * Revisar movimientos pendientes de la cuenta
	 * @param id id de la cuenta
	 * @return esNacional true-nacional, false-internacional
	 * @return boolean 
	 */
	public boolean existenMovimientosPendientesNalXCuenta(long id);
	
	/**
	 * Obtiene todos los movimientos pendientes y los acomoda en listas de acuerdo a la cuenta 
	 * que implican. Se colocan las listas en un mapa cuya 'key' es el numero de cuenta.
	 * 
	 * @param esNacional true- es nacional, false-es internacional
	 * @param List<CuentaRetiroEfectivoDTO> lista de cuentas a revisar
	 * @return Map <Long, List<Object>>
	 */
	public Map <Long, List<Object>> obtenerRetirosPendientes(boolean esNacional, List<CuentaRetiroEfectivoDTO> cuentas);
	
	/**
	 * Busca la combinacion cuenta-banco beneficiario.
	 * Si existe en la tabla, lo compara con el nombre
	 * 		- (return true) el nombre corresponde - ok
	 * 		- (return false) el nombre no corresponde
	 * No existe en la tabla
	 * 		- (return true) puede darse de alta la combinacion cuenta-banco-nombre sin problema
	 */
	public boolean nombreParaCuentaBancoBeneficiarioValido(
			InstitucionDTO traspasante, InstitucionDTO beneficiario, 
			String cuentaBeneficiario, String nombreBeneficiarioPrueba, String[] nombreBeneficiarioEnBD,  
			long[] esModificacion);
			
			
	/**
	 * Busca entre las cuentas la combinacion nombre-cuenta-banco beneficiario para que no se repita
	 * para la misma institucion traspasante.
	 * 1. En caso de modificacion busca si alguna otra cuenta distinta a la que se esta modificando ya tiene la combinacion de pantalla 
	 *    si encuentra alguna seria un error.
     * 2. En caso de alta no se considera un folio de cuenta, solo se busca entre las existentes.
	 * @return boolean true - ya existe la combinacion para el traspasante
	 *   			   false -  no existe la combinacion para el traspasante
	 */
	public boolean existeBancoNombreCuentaBeneficiario(
			InstitucionDTO traspasante, InstitucionDTO beneficiario, 
			String cuentaBeneficiario, String nombreBeneficiario, long[] esModificacion);
	
	/**
	 * Metodo para almacenar en BitacoraEdoCuenta
	 * @param cuenta
	 */
	public void createBitacoraEdoCuenta(CuentaRetiroEfectivoDTO cuenta);
	
	/**
	 * Obtiene la institucion a la cual pertenece la clave spei
	 * @param claveSpei
	 * @return Institucion
	 */
	public InstitucionDTO getInstitucionForClaveSpei(String claveSpei);	

}

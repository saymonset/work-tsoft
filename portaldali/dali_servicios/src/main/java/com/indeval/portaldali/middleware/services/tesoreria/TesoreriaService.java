/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.tesoreria;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosService;

/**
 * Interfaz para los servicios de Tesoreria, con los metodos publicados
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public interface TesoreriaService extends LiquidacionDecretosService {

	/* Constantes */

	/** Indica el tipo de operacion RETIROS_SPEI en getTraspFondos() */
	public static final String RETIROS_SPEI = "28";

	/** Indica que la agrupacion es agrupada */
	public static final String AGRUPADA = "AGRUPADA";

	/** Indica que la agrupacion es cronologica */
	public static final String CRONOLOGICA = "CRONOLOGICA";

	/** Indica que es pagos */
	public static final String PAGOS = "PAGOS";

	/** Indica que el tipo de moneda es pesos mexicanos */
	public static final String PESOS = "PE";

	/** Indica que es cobros */
	public static final String COBROS = "COBROS";

	/** Indica el tipo de operacion DEPOSITOS_TODOS en getTraspFondos() */
	public static final String DEPOSITOS_TODOS = "23,25,27";

	/**
	 * Liquidaci&oacute;n Por Decretos Realiza la consulta para Edo. Cta. De
	 * liquidaci&oacute;n por Derechos, Pagos por Decreto de Ejercicios y
	 * Consulta de Vencimientos pendientes de acuerdo a los criterios
	 * seleccionados Se utiliza en conjunto con un servicio de IDS que lee papel
	 * gubernamental de Oracle
	 * 
	 * @param params
	 * @return Arreglo de objetos de la clase CuentaLiquidacionVO
	 * @throws BusinessException
	 */
	CuentaLiquidacionVO[] getEstadoCuentaLiquidacion(GetEdoCtaLiqParams params) throws BusinessException;

	/**
	 * CUTE49-01  Detalle Amortizacion Liquidaci&oacute;n Por Decretos Realiza
	 * la consulta del detalle de amortizaci&oacute;n parcial
	 * 
	 * @param params
	 *            GetDetalleAmortizacionesParams {AgenteVO agente(Requerido);
	 *            String folioVariable(Requerido); String folioFija(Requerido); }
	 * @return DetalleAmortizacionesVO[]
	 * @throws BusinessException
	 */
	DetalleAmortizacionesVO[] getDetalleAmortizaciones(GetDetalleAmortizacionesParams params) throws BusinessException;

	/**
	 * Obtiene el detalle de las amortizaciones de la liquidacion por decretos
	 * 
	 * @param params
	 * @return Un arreglo de objetos DetalleAmortizacionesVO
	 * @throws BusinessException
	 */
	DetalleAmortizacionesVO[] getDetalleAmort(GetDetalleAmortizacionesParams params) throws BusinessException;

	/**
	 * CUTE49-01  Detalle Liquidaci&oacute;n Por Decretos Realiza la consulta
	 * del detalle de cobros por decreto
	 * 
	 * @param params
	 *            GetDetalleCuentaLiqParams {AgenteVO agente(Requerido); String
	 *            folioVariable(Requerido); String folioFija(Requerido); }
	 * @return DetalleCuentaLiqVO
	 * @throws BusinessException
	 */
	DetalleCuentaLiqVO getDetalleCuentaLiqVO(GetDetalleCuentaLiqParams params) throws BusinessException;

	/**
	 * Obtiene el detalle de los cobros de la liquidacion por decretos
	 * 
	 * @param params
	 * @return Un objeto de la clase DetalleCuentaLiqVO
	 * @throws BusinessException
	 */
	DetalleCuentaLiqVO getDetalleCuentaLiquidacionVO(GetDetalleCuentaLiqParams params) throws BusinessException;

	/**
	 * CUTE49-04 : Retiro de Fondos Indeval - Banxico Realiza los retiros de
	 * fondos del SIDV al SIAC/SPEI de los usuarios
	 * 
	 * @param agente
	 *            AgenteVO (Requerido) - El agente firmado
	 * @param tipoRetiro
	 *            String (Requerido) - El tipo de la operacion de retiro
	 * @param importe
	 *            BigDecimal (Requerido) - El importe del retiro
	 * @return Integer - El folio de la operacion de retiro
	 * @throws BusinessException
	 *             e
	 */
	Integer retirarFondos(AgenteVO agente, String tipoRetiro, BigDecimal importe) throws BusinessException;

	/**
	 * CUTE49-05 : Traspasos de Efectivo entre Cuentas de Control Realiza los
	 * traspasos de fondos entre las cuentas de los usuarios indicados.
	 * 
	 * @param traspasante
	 *            AgenteVO (Requerido) - El agente firmado o traspasante
	 * @param receptor
	 *            AgenteVO (Requerido) - La contraparte o receptor de la
	 *            operacion
	 * @param importe
	 *            BigDecimal(Requerido) - El monto de la operacion de traspaso
	 * @return Integer - El folio de la operacion de traspaso
	 * @throws BusinessException
	 *             exception - La excepcion de negocio
	 */
	Integer traspasarEntreCuentas(AgenteVO traspasante, AgenteVO receptor, BigDecimal importe) throws BusinessException;

	// /**
	// * Valida que la aplicacion para traspasar los saldos de efectivo de LC a
	// LD este abierta
	// * @throws BusinessException
	// */
	// void validaAplicacionTraspasoLC2LD() throws BusinessException;
	//    
	// /**
	// * Valida que la aplicacion para traspasar los saldos de efectivo de CC a
	// LD este abierta
	// * @throws BusinessException
	// */
	// void validaAplicacionTraspasoCC2LD() throws BusinessException;
	//    
	// /**
	// * Consulta global de Saldos para el traspaso de los saldos de efectivo de
	// LC a LD
	// * @param agenteVO - El agente es opcional -
	// * @return SaldosATraspasarVO
	// * @throws BusinessException
	// */
	// SaldosATraspasarVO consultaSaldosATraspasarLC2LD(AgenteVO agenteVO)
	// throws BusinessException;
	//    
	// /**
	// * Consulta global de Saldos para el traspaso de los saldos de efectivo de
	// CC a LD
	// * @param agenteVO - El agente es opcional -
	// * @return SaldosATraspasarVO
	// * @throws BusinessException
	// */
	// SaldosATraspasarVO consultaSaldosATraspasarCC2LD(AgenteVO agenteVO)
	// throws BusinessException;

	/**
	 * CUTE49-06 : Traspasos de fondos entre Sistemas de Pago Realiza la
	 * consulta de la informaci&oacute;n con el tipo de movimiento, la fecha
	 * actual, el ID y el Folio del usuario que se encuentra en sesi&oacute;n
	 * actualmente. Retorna los datos paginados.
	 * 
	 * @param agente
	 *            AgenteVO (Requerido)
	 * @param idTipoOperacion
	 *            String (Requerido)
	 * @param pagina
	 *            PaginaVO (Requerido)
	 * @return PaginaVO - La pagina con los resultados del metodo
	 * @throws BusinessException
	 */
	PaginaVO getTraspFondos(AgenteVO agente, String idTipoOperacion, PaginaVO pagina) throws BusinessException;

	/**
	 * CUTE49-07 : Saldo 3 Cuentas Metodo que retorna un objeto con los saldos
	 * efectivos de las 3 cuentas de un agente (Liq.Capitales, Liq.Dinero y
	 * Concentradora), el saldo total y el valor colateral.
	 * 
	 * @param agente
	 *            AgenteVO (Requerido) - El agente firmado
	 * @return Saldo3CuentasVO objeto retorno
	 * @throws BusinessException
	 */
	Saldo3CuentasVO getSaldo3Cuentas(AgenteVO agente) throws BusinessException;

	/**
	 * CUTE49-08 : Estado de Cuenta de Saldo Neto de Efectivo Realiza la
	 * consulta de la informaci&oacute;n con el tipo de consulta (en
	 * l&iacute;nea o hist&oacute;rico), la fecha de operaci&oacute;n (en
	 * l&iacute;nea o hist&oacute;rico), el ID y el Folio del usuario que se
	 * encuentra firmado, Origen, Aplicaci&oacute;n, Mercado, Movimiento, el
	 * tipo de cuenta y el ordenamiento (cronol&oacute;gica o agrupada),
	 * Contraparte (id, folio y nombre corto).
	 * 
	 * @param params
	 *            Objeto {@link GetEdoCtaSNEParams} params {Date
	 *            fechaOperacion(Requerido); String agrupacion(Requerido);
	 *            AgenteVO agente(Requerido); String idOrigen; String
	 *            idAplicacion; String idMercado; String idMovimiento; String
	 *            idCuentasEfectivo; AgenteVO contraparte; PaginaVO
	 *            pagina(Requerido); }
	 * @return EstadoCuentaSNEVO objeto retorno
	 * @throws BusinessException
	 */
	EstadoCuentaSNEVO getEstadoCuentaSNE(GetEdoCtaSNEParams params) throws BusinessException;

	/**
	 * Valida que al agente firmado se le permita realizar retiros de fondos. La
	 * validacion consiste en que se tenga permiso para realizar el retiro de
	 * fondos en base a la siguiente instruccion SQL:
	 * 
	 * <pre>
	 *   select permiso from catalogo..areas_trabajo 
	 *     where id_inst = :idInstAgenteFirmado 
	 *     and folio_inst = :folioIntAgenteFirmado 
	 *     and nombre_corto = &quot;TESOR&quot; 
	 * </pre>
	 * 
	 * Si el permiso obtenido es "C", entonces no se permite el retiro de
	 * fondos. Si es blanco o nulo se le concede el permiso de retirar los
	 * fondos.
	 * 
	 * @param agente
	 *            El agente firmado, parametro obligatorio
	 * @throws BusinessException
	 *             Si no se tiene permiso de retirar fondos
	 */
	void permitirRetirosFondos(AgenteVO agente) throws BusinessException;

	/**
	 * Recupera la divisa default para mostrarse en la pantalla de
	 * liquidaci&oacute;n por decretos
	 * 
	 * @return String la clave de la divisa default
	 * @throws BusinessException
	 */
	String getDivisaDefault() throws BusinessException;

	/**
	 * Validaciones y reglas de negocio para el servicio que realiza el retiro
	 * de fondos Indeval-Banxico
	 * 
	 * @param agente
	 * @param idTipoRetiro
	 * @param importe
	 * @return Integer
	 * @throws BusinessException
	 */
	BigInteger businessRulesRetirarFondos(AgenteVO agente, String idTipoRetiro, 
	        BigInteger idBoveda, BigDecimal importe) throws BusinessException;

	/**
	 * Implementa las reglas de negocio para el traspaso de efectivo entre
	 * cuentas de control.
	 * 
	 * @param traspasante
	 * @param receptor
	 * @param idBoveda TODO
	 * @param importe
	 * @return Integer
	 * @throws BusinessException
	 */
	BigInteger businessRulesTraspasarEntreCuentas(AgenteVO traspasante, 
	        AgenteVO receptor, BigInteger idBoveda, BigDecimal importe) throws BusinessException;
	
	/**
	 * Obtiene la cuenta clabe de efectivo a traves de su cuenta nombrada
	 * 
	 * @param agente datos de id,folio y cuenta
	 * @return clabe de la cuenta efectivo
	 */
	String getCuentaClabeEfectivoPorCuentaNombrada(AgenteVO agente);

}

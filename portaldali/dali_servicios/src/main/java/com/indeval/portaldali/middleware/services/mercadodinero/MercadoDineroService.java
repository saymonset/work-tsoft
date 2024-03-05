/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadodinero;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;

/**
 * Interfaz para los servicios de Mercado de Dinero, con los metodos publicados
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public interface MercadoDineroService {

	/**
	 * Indica que el Rol del agente firmado es Traspasante se usa en el servicio
	 * consultaEstatusOperaciones() y en getEstadoCuenta()
	 */
	public static final String TRASPASANTE_INIC = "T";

	/**
	 * Indica que el Rol del agente firmado es Receptor se usa en el servicio
	 * consultaEstatusOperaciones() y en getEstadoCuenta()
	 */
	public static final String RECEPTOR_INIC = "R";

	/**
	 * Indica que el agente firmado toma ambos roles se usa en el servicio
	 * consultaEstatusOperaciones() y en getEstadoCuenta() Tambien se utiliza
	 * para indicar que el tipo de papel es ambos (PB y PG) en mercado de dinero
	 */
	public static final String AMBOS_INIC = "A";

	/** Indica el tipo de papel que corresponde en mercado de capitales */
	public static final String MERCADO_CAPITALES = "MC";

	/** Indica que el origen es 01 */
	public static final String ORIGEN = "01";

	/** Indica que el tipo de operacion es Deposito */
	public static final String DEPOSITO = "D";

	/** Indica que el tipo de operacion es Retiro */
	public static final String RETIRO = "R";

	/** Indica que el estado es por confirmar */
	public static final String POR_CONFIRMAR = "C";

	/** Indica que el estado es liquidado */
	public static final String LIQUIDADO = "L";

	/** Indica que el estado es pendiente */
	public static final String PENDIENTE = "P";

	/**
	 * Indica que el mercado del instrumento es Papel Bancario, usado en
	 * getListaEmisiones()
	 */
	public static final String PAPEL_BANCARIO = "PB";

	/**
	 * Indica que el mercado del instrumento es Papel de Dinero, usado en
	 * getListaEmisiones() Tambien se utiliza para indicar que el archivo a leer
	 * en el File Transfer es de "dinero"
	 */
	public static final String PAPELES_DINERO = "PD";

	/**
	 * Indica que el mercado del instrumento es Papel gubernamental, usado en
	 * getListaEmisiones() Tambien se utiliza para indicar que el archivo a leer
	 * en el File Transfer es de "guber"
	 */
	public static final String PAPEL_GUBERNAMENTAL = "PG";

	/**
	 * Indica que esta en firme, usado en consultaEstatusOperaciones() y en
	 * getListaEmisiones()
	 */
	public static final String EN_FIRME = "F";

	/** Indica que el tipo de operacion es TRASPASO */
	public static final String TRASPASO = "TRASPASO";

	/** Indica que el tipo de operacion es APERTURA */
	public static final String APERTURA = "APERTURA";

	/** Indica que se carga la informacion para confirmacion de papel bancario */
	public static final String CONFIRMACION_PAPEL_BANCARIO = "CB";

	/**
	 * Indica que se carga la informacion para confirmacion de papel
	 * gubernamental
	 */
	public static final String CONFIRMACION_PAPEL_GUBERNAMENTAL = "CG";

	/**
	 * CUMD43-01 : Estatus de Operaciones Retorna una lista de registros de
	 * operaciones (filtrada por distintos parametros) y su estatus, as&iacute;
	 * como los totales de titulos y montos, netos y segmentados por rol.
	 * 
	 * @param params
	 *            (requerido) ConsultaEstatusOperacionesParams - Objeto de
	 *            parametros
	 * @return EstatusOperacionesVO - Objeto de retorno
	 * @throws BusinessException
	 */
	EstatusOperacionesVO consultaEstatusOperaciones(ConsultaEstatusOperacionesParams params) throws BusinessException;

	/**
	 * CUMD43-02 : Captura de Operaciones Efectua las operaciones de: Fondeo
	 * entre cuentas propias. Traspasos Libres de Pago, Ventas, Reporto, Reporto
	 * Tasa Referencia, Reporto D&oacute;lares, Reporto Real, Reporto Indi
	 * 
	 * @param params
	 *            RegistraOperacionParams - Objeto de parametros
	 * @return String
	 * @throws BusinessException
	 */
	String registraOperacion(RegistraOperacionParams params) throws BusinessException;

	/**
	 * CUMD43-04 : Estado de Cuenta Retorna los estados de cuenta de un agente
	 * filtrados y ordenados por los parametros recibidos. Muestra todos los
	 * movimientos realizados por los intermediarios en d&iacute;as anteriores
	 * (hasta una semana) y en fecha corriente
	 * 
	 * @param params
	 *            GetEstadoCuentaParams - Objeto de parametros
	 * @return EstadoCuentaTotalMDVO - contiene totales y Arreglo de Objetos
	 *         ElementoEstadoCuentaVO
	 * @throws BusinessException
	 */
	EstadoCuentaTotalMDVO getEstadoCuenta(GetEstadoCuentaParams params) throws BusinessException;

	/**
	 * CUMD43-07 : Detalle de parcialidades Muestra el detalle del conjunto de
	 * parcialidades que forman parte de una operaci&oacute;n
	 * 
	 * @param folio -
	 *            folio origen (Requerido)
	 * @return ParcialidadVO - vo con las parcialidades encontradas
	 * @throws BusinessException
	 */
	ParcialidadVO detalleParcialidades(String folio) throws BusinessException;

	/**
	 * CUMD43-10 : Apertura de Sistemas de MD Realiza la apertura de sistemas de
	 * Mercado de Dinero Realiza dos tipos de movimientos: Traspasos de
	 * Miscelanea Fiscal de Mercado de Dinero
	 * 
	 * @param params -
	 *            TraspasoMercadoDineroParams
	 * @return BigInteger - folio_control
	 * @throws BusinessException
	 */
	BigInteger traspasoMercadoDinero(TraspasoMercadoDineroParams params) throws BusinessException;

	/**
	 * CUMD43-10 : Apertura de Sistemas de MD Realiza la apertura de sistemas de
	 * Mercado de Dinero Realiza dos tipos de movimientos: Apertura de Sistemas
	 * de Mercado de Dinero
	 * 
	 * @param params -
	 *            TraspasoMercadoDineroParams
	 * @return BigInteger - folio_control
	 * @throws BusinessException
	 */
	BigInteger traspasoMercadoDineroBusinessRules(TraspasoMercadoDineroParams params) throws BusinessException;

	/**
	 * CUMD43-11 : Confirmacion de Apertura de Sistemas de Dinero Presenta los
	 * datos para el proceso de confirmaci&oacute;n de apertura de sistemas de
	 * mercado de dinero a ejecutar por el agente receptor Ejecuta una consulta
	 * a la tabla bddinero..mdintran para determinada fecha y agente firmado.
	 * 
	 * @param agenteFirmado
	 * @param aperturaSistema
	 * @return Arreglo del tipo TraspasoMiscFiscalVO
	 * @throws BusinessException
	 */
	TraspasoMiscFiscalVO[] getListaConfirmacionApertura(AgenteVO agenteFirmado, boolean aperturaSistema) throws BusinessException;

	/**
	 * Muestra la lista de traspasos de misc. fiscal a confirmar
	 * 
	 * @param agenteFirmado
	 * @return Arreglo del tipo TraspasoMiscFiscalVO
	 * @throws BusinessException
	 */
	TraspasoMiscFiscalVO[] getListaConfirmacionMiscFiscal(AgenteVO agenteFirmado) throws BusinessException;

	/**
	 * CUMD43-11 : Confirmacion de Apertura de Sistemas de Dinero Ejecuta el
	 * proceso de confirmaci&oacute;n de apertura de sistemas de mercado de
	 * dinero por el agente receptor. Actualiza el estatus para la confirmacion
	 * de aperturas en la tabla bddinero..mdintran
	 * 
	 * @param agenteFirmado
	 * @param folioTraspaso
	 * @param aperturaSistema
	 * @return boolean - true = Actualizaci&oacute;n realizada correctamente
	 *         false = Actualizaci&oacute;n no fue realizada, no se encontraron
	 *         los folios solicitados
	 * @throws BusinessException
	 *             En caso de no recibir algun folio de confirmaci&oacute;n
	 */
	boolean confirmaAperturaSistemaDinero(AgenteVO agenteFirmado, BigInteger[] folioTraspaso, boolean aperturaSistema) throws BusinessException;

	/**
	 * Actualiza los registros de los traspasos de Misc. Fiscal correspondientes
	 * al arreglo de folios y el agente firmado, para que el estatus quede como
	 * pendiente de liquidar
	 * 
	 * @param agenteFirmado
	 * @param foliosTraspasos
	 * @return Boolean - true = Actualizaci&oacute;n realizada correctamente
	 *         false = Actualizaci&oacute;n no fue realizada, no se encontraron
	 *         los folios solicitados
	 * @throws BusinessException
	 */
	Integer[] setConfirmacionMiscFiscal(AgenteVO agenteFirmado, Integer[] foliosTraspasos) throws BusinessException;

	/**
	 * CUMD43-14 : Consulta de Movimientos de Miscelanea Fiscal de Mercado de
	 * Dinero Consulta los movimientos con informaci&oacute;n de
	 * miscel&aacute;nea fiscal, del traspasante y del receptor, tanto para el
	 * modulo de mercado de dinero (mercado=true), como para el de
	 * capitales(mercado=false)
	 * 
	 * @param consultaMovimientosMiscFiscalParams
	 * @return PaginaVO(Requerido)
	 * @throws BusinessException
	 */
	PaginaVO consultaMovimientosMiscFiscal(ConsultaMovimientosMiscFiscalParams consultaMovimientosMiscFiscalParams) throws BusinessException;

	/**
	 * CUGB53-01 : Captura de Garantias Captura traspasos entre cuentas 0060
	 * para construir garant&iacute;as en el SAGA Ejecuta el Stored Procedure
	 * bddinero..UP_trasmdin_c para captura de garantias
	 * 
	 * @param params
	 *            CapturaGarantiasParams
	 * @return String
	 * @throws BusinessException
	 */
	String capturaGarantias(CapturaGarantiasParams params) throws BusinessException;

	/**
	 * CUGB53-02 : Posicion de Valores en Garantias Muestra las posiciones
	 * (t&iacute;tulos) que tiene una instituci&oacute;n
	 * 
	 * @param emision
	 * @param idTipoPapel
	 * @param agente
	 * @return PosicionValorGarantiaVO[]
	 * @throws BusinessException
	 */
	PosicionValorGarantiaVO[] consultaPosicionValoresGarantias(EmisionVO emision, String idTipoPapel, AgenteVO agente) throws BusinessException;

	/**
	 * CUMD43-03 : Posicion de Valores Muestra todas las posiciones
	 * (t&iacute;tulos) de las cuentas de un usuario que est&aacute;n al cierre
	 * de ayer, en l&iacute;nea, en tr&aacute;nsito (MD), a 24, 48, 72 y 96
	 * d&iacute;as; y el Neto de posiciones de cada cuenta
	 * 
	 * @param params
	 * @return PaginaVO
	 * @throws BusinessException
	 */
	PaginaVO consultaPosicionValores(PosicionValoresParams params) throws BusinessException;

	/**
	 * CUVE69-02 : Captura de concertacion de prestamos Ejecuta el proceso de
	 * captura de concertaci&oacute;n de pr&eacute;stamos Metodo que realiza la
	 * Captura de los Prestamos StoreProcedure: bdvalpre..UP_pv_insprestamo
	 * '@id_prestamista', '@folio_prestamista' '@id_prestatario',
	 * '@folio_prestatario', '@tv', '@emisora', '@serie', '@cupon', '@cantidad',
	 * '@fecha_vencimiento'
	 * 
	 * @param params -
	 *            objeto de tipo CapturaConcertacionPrestamosParams
	 * @return Integer - folio prestamo
	 * @throws BusinessException
	 */
	Integer capturaConcertacionPrestamos(CapturaConcertacionPrestamosParams params) throws BusinessException;

	/**
	 * CUVE69-03 : Administracion de Garantias Realiza tanto el Dep&oacute;sito
	 * de Garantias y Retiro de Garant&iacute;as como la Administraci&oacute;n
	 * de pr&eacute;stamos vigentes. (disponible &uacute;nicamente para papel
	 * gubernamental) Consulta las tablas bdvalpre..prestamo y
	 * bdvalpre..garantias para obtener los datos del prestamo solicitado y sus
	 * garantias.<br>
	 * Solo muestra los prestamos en estado: 1=Concertado, 2=Cerrado, 5=Llamadas
	 * de margen, 13=Pendiente por valores, que correspondan al usuario
	 * firmado(idInstitucion + folioInstitucion) y solo las garant&iacute;as que
	 * tengan un valor mayor a cero.<br>
	 * Si no recibe el folio del prestamo regresa todos los prestamos que se
	 * encuentren en los estados (1,2,5,13) y sus garantias cuya cantidad sea
	 * mayor a 0, para el usuario firmado.
	 * 
	 * @param folio -
	 *            Folio del prestamo
	 * @param idInstitucion
	 *            (Requerido) - Id de la Institucion a la que pertenece el
	 *            prestamo
	 * @param folioInstitucion
	 *            (Requerido) - Folio de la Institucion a la que pertenece el
	 *            prestamo
	 * @return AdministracionGarantiaVO[] - Arreglo de tipo
	 *         AdministracionGarantiaVO con los resultados encontrados
	 * @throws BusinessException -
	 *             En caso de no recibir los parametros de idInstitucion y
	 *             folioInstitucion
	 */
	AdministracionGarantiaVO[] administracionGarantias(BigInteger folio, String idInstitucion, String folioInstitucion) throws BusinessException;

	/**
	 * CUVE69-03 : VALPREE : Administracion de Garantias Si el excedente es
	 * mayor que cero obtiene el precio_vector de la emision recibida y efectua
	 * el calculo de la cantidad sugerida.
	 * 
	 * @param emision
	 *            (Requerido)
	 * @param Excedente
	 *            (Requerido)
	 * @return OperacionGarantiaVO
	 * @throws BusinessException
	 */
	BigInteger consultaDatosOperacionGarantias(EmisionVO emision, BigDecimal Excedente) throws BusinessException;

	/**
	 * CUVE69-03 : VALPREE : Administracion de Garantias Realiza tanto el
	 * Dep&oacute;sito de Garantias y Retiro de Garant&iacute;as como la
	 * Administraci&oacute;n de pr&eacute;stamos vigentes. (disponible
	 * &uacute;nicamente para papel gubernamental) Metodo que cambia el estado
	 * de las operaciones Stored Procedure : exec bdvalpre..UP_pv_garantias
	 * '@cuenta', '@tv', '@emisora',
	 * '@serie','@cupon',@cantidad,@folio_prestamo,'@origen','@operacion'
	 * 
	 * @param agente
	 * @param emision
	 * @param cantidadLiberada
	 * @param folioPrestamo
	 * @param tipoOperacion
	 * @return integer (return=0, si es exitosa la operacion, menor que 0, si
	 *         arroja algun error)
	 * @throws BusinessException
	 */
	Integer ejecutaOperacionGarantias(AgenteVO agente, EmisionVO emision, BigDecimal cantidadLiberada, BigInteger folioPrestamo, String tipoOperacion)
			throws BusinessException;

	/**
	 * CUVE69-04 Consulta de Prestamos Despliega la informaci&oacute;n referente
	 * de los Pr&eacute;stamos. Realiza una consulta sobre la tabla
	 * bdvalpre..prestamo con los parametros que recibe y regresa un objeto de
	 * tipo PrestamoVO[] con los registros encontrados. Si no recibe parametros
	 * ejecuta la consulta solo con las condiciones de id_prestatario y
	 * fol_prestatario. El Boolean toma los valores true = Faltante / false =
	 * Sobrante,
	 * 
	 * @param params
	 * @return PrestamoVO[]
	 * @throws BusinessException
	 */
	PrestamoVO[] consultaPrestamos(ConsultaPrestamosParams params) throws BusinessException;

	/**
	 * CUVE69-04 Consulta de Prestamos Verifica la hora maxima para prorrogar y
	 * ejecuta el stored procedure bdvalpre..UP_pv_prorroga que permite realizar
	 * una prorroga sobre el folio del prestamo que se recibe como parametro.
	 * 1.Valida que la hora actual sea menor a la hora_maxima_para_prorrogar de
	 * la tabla de parametros. select valor from bdvalpre..parametros where
	 * parametro = 'hora maxima para prorrogar' 2.Si la hora en la que se quiere
	 * hacer la prorroga (hora_actual del sistema) es menor al valor
	 * (hora_maxima_permitida), entra en la hora permitida para prorrogar y se
	 * ejecuta el Store Procedure UP_pv_prorroga con los parametros de
	 * folioPrestamo y Origen= 01: bdvalpre..UP_pv_prorroga(folioPrestamo,
	 * Origen) exec bdvalpre..UP_pv_prorroga (?,'01')
	 * 
	 * @param folioPrestamo
	 *            BigDecimal - Folio del prestamo del que se quiere hacer la
	 *            prorroga
	 * @return BigDecimal = Numero negativo - en caso de error 0 - en caso de
	 *         exito y folioPrestamo - en caso de exito
	 * @throws BusinessException -
	 *             En caso de que la hora actual sea mayor a la hora maxima
	 *             permitida para prorrogar. En caso de que falle la ejecucion
	 *             del SP o no regrese ningun regegistro (codigo_error !=0 o
	 *             !=1).
	 */
	BigDecimal prorroga(BigDecimal folioPrestamo) throws BusinessException;

	/**
	 * CUVE69-04 Consulta de Prestamos Verifica que se tengan permisos para
	 * anticipar y la hora maxima para prorrogar y ejecuta el stored procedure
	 * bdvalpre..UP_pv_pendiente, que permite realizar un vencimiento anticipado
	 * sobre el folio del prestamo que se recibe como parametro. 1. Verifica en
	 * bdvalpre..parametros si el parametro de permiso para anticipar es = S
	 * select valor from bdvalpre..parametros where parametro='permiso para
	 * anticipar' Si valor = 'N' ---> No se permite vencer anticipadamente 2. Si
	 * se tienen permisos para anticipar se valida que la hora actual sea menor
	 * a la hora_maxima_para_prorrogar de la tabla de parametros. select valor
	 * from bdvalpre..parametros where parametro = 'hora maxima para prorrogar'
	 * 3. Si la hora en la que se quiere hacer el vencimiento anticipado
	 * (hora_actual del sistema) es menor al valor(hora_maxima_permitida), entra
	 * en la hora permitida para prorrogar y se obtiene el valor del mercado:
	 * select mercado from catalogo..instrumentos where tv ? = tv del prestamo
	 * --Eje: 'M0' , 'M5' , 'M7' 3. Se ejecuta el Store Procedure
	 * UP_pv_pendiente con los parametros de folioPrestamo y Origen= 01
	 * (constate): bdvalpre..UP_pv_pendiente(folio_prestamo, origen, mercado)
	 * exec bdvalpre..UP_pv_pendiente (?,'01','PG')
	 * 
	 * @param folioPrestamo
	 *            BigDecimal - Folio del prestamo del que se quiere hacer el
	 *            vencimiento anticipado
	 * @return boolean - True para indicar que el vencimiento anticipado ha sido
	 *         realizado correctamente.
	 * @throws BusinessException -
	 *             En caso de que falle alguna de las validaciones de permiso
	 *             para anticipar o la validacion de hora maxima para prorrogar.
	 *             Si falla la ejecucion del SP o no regrese ningun registro
	 *             (codigo_error !=0 o !=1).
	 */
	boolean vencimientoAnticipado(BigDecimal folioPrestamo) throws BusinessException;

	/**
	 * confirma Operaciones ya sea fecha valor o mismo dia
	 * 
	 * @param agente
	 *            agente del vendedor (id, folio, cuenta)
	 * @param llaveFolio
	 *            llave folio de la operacion
	 * @param isAgenteFirmado
	 *            indica si el agente vendedor es el agente firmado
	 * @return BigInteger[] arreglo de BigInteger con el status de cada una de
	 *         las operaciones enviadas
	 * @throws BusinessException
	 */
	BigInteger[] confirmaOperacion(AgenteVO agente, String llaveFolio, Boolean isAgenteFirmado) throws BusinessException;

	/**
	 * Cancela Operaciones de mercado de dinero
	 * 
	 * @param agente
	 *            agente del vendedor (id, folio, cuenta)
	 * @param llaveFolio
	 *            llave folio de la operacion
	 * @param isAgenteFirmado
	 *            indica si el agente vendedor es el agente firmado
	 * @return BigInteger[] arreglo de BigInteger con el status de cada una de
	 *         las operaciones enviadas
	 * @throws BusinessException
	 */
	BigInteger[] cancelaOperacion(AgenteVO agente, String llaveFolio, Boolean isAgenteFirmado) throws BusinessException;

	/**
	 * Regresa una fecha igual a la fecha enviada como parametro mas el numero
	 * de dias h&aacute;biles
	 * 
	 * @param fechaInicial -
	 *            Date
	 * @param offset -
	 *            int
	 * @return Date
	 * @throws BusinessException
	 */
	Date agregarDiasHabiles(Date fechaInicial, int offset) throws BusinessException;

	/**
	 * Regresa una lista de los d&iacute;as inh&aacute;biles del mes y
	 * a&ntilde;o indicados
	 * 
	 * @param month
	 * @param year
	 * @return List
	 * @throws BusinessException
	 */
	List consultarDiasInhabilesByMonth(int month, int year) throws BusinessException;

	/**
	 * Obtiene los saldos del prestamista
	 * 
	 * @param agenteFirmado
	 * @param emision
	 * @return PosturaPrestamistaVO
	 * @throws BusinessException
	 */
	PosturaPrestamistaVO getPosturaPrestamista(AgenteVO agenteFirmado, EmisionVO emision) throws BusinessException;

	/**
	 * Requiere minimo del id, folio y tv para funcionar
	 * 
	 * @param agenteVO
	 * @param emisionVO
	 * @param paginaVO
	 * @param export
	 * @return una instancia de {@link PosicionValoresSimpleTotalVO}
	 * @throws BusinessException
	 */
	PosicionValoresSimpleTotalVO[] getPosicionValorSimple(AgenteVO agenteVO, EmisionVO emisionVO, PaginaVO paginaVO, boolean export) throws BusinessException;

	/**
	 * Actualizaci&oacute;n de par&aacute;metros para VALPRE-E
	 * 
	 * @param mapaParametros
	 * @throws BusinessException
	 */
	void setActualizaParametrosValpreE(HashMap mapaParametros) throws BusinessException;

	/**
	 * Metodo que realiza la consulta de operaciones del dia de MD
	 * 
	 * @param operacionDiaDineroParams
	 * @return OperacionDiaDineroVO
	 * @throws BusinessException
	 */

	OperacionDiaDineroVO getOperacionesDiaDinero(OperacionDiaDineroParams operacionDiaDineroParams) throws BusinessException;

	/**
	 * Metodo que realiza la consulta del detalle de operaciones del dia MD
	 * 
	 * @param traspasoDineroVO
	 * @return OperacionDiaDetalleDineroVO
	 * @throws BusinessException
	 */

	OperacionDiaDetalleDineroVO getDetalleOperacionDiaDinero(TraspasoDineroParams traspasoDineroVO) throws BusinessException;

	/**
	 * Obtiene un arreglo de objetos con la informacion del precio vector para
	 * los tipos de valor requeridos - Valpre-E
	 * 
	 * @param tv
	 * @return Arreglo de objetos de la clase PrecioVectorValpreEVO
	 * @throws BusinessException
	 */
	List getPrecioVectorValpreE(String tv) throws BusinessException;

	/**
	 * Obtiene una lista de String con los tv's que intervienen en la consulta
	 * de precio vector de Valpre-E
	 * 
	 * @return List
	 * @throws BusinessException
	 */
	List getTvsPrecioVectorValpreE() throws BusinessException;

	/**
	 * Este m&eacute;todo realiza la captura de operaciones de tipo
	 * colocaci&oacute;n primaria o recompra.
	 * 
	 * @param params
	 * @return String el folio de la operaci&oacute;n
	 * @throws BusinessException
	 */
	String getColocacionPrimariaRecompra(RegistraOperacionParams params) throws BusinessException;

	/**
	 * Valida que el parametro recibido sea una tv v&aacute;lida para mercado de
	 * dinero.
	 * 
	 * @param tv
	 * @return String
	 * @throws BusinessException
	 */
	String validaTVMD(String tv) throws BusinessException;

	/**
	 * Valida los parametros de entrada para realizar un registro de la
	 * operacion correcto.
	 * 
	 * @param params
	 *            RegistraOperacionParams - Objeto de parametros
	 * @return String
	 * @throws BusinessException
	 */
	String validaRegistraOperacionBusinessRules(RegistraOperacionParams params) throws BusinessException;

	/**
	 * Valida los parametros de entrada para realizar que la captura de
	 * operaciones de tipo colocaci&oacute;n primaria o recompra sea correcta.
	 * 
	 * @param params
	 * @return String
	 * @throws BusinessException
	 */
	String validaGetColocacionPrimariaRecompraBusinessRules(RegistraOperacionParams params) throws BusinessException;

}

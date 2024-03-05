/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.divisioninternacional;

import java.math.BigInteger;
import java.util.Date;

import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;

/**
 * @author <a href="mailto:julio@itbrain.com.mx">Julio G&oacute;mez Resendiz.</a> 2006
 */
public interface DivisionInternacionalService {

    /**
     * Caso de Uso: CUDI51-01 Obtiene emisiones con los siguientes criterios:
     * -Que correspondan a los del agente en sesion. -Con la alta CPOS, ADCP,
     * IADC, BADC, VIVI y GADC. -Y Con las terminaciones de cuenta en 54, 94, 97
     * y 98. -Y que saldo disponible sea > 0. -Y que en catalogo.. emisiones
     * cupon_cortado = F
     *
     * @param agenteFirmado
     *            idInst y folioInst del agente en sesion
     * @param emisionVO
     *            tv, emisora, serie, cupon, emisionExtra e Isin.
     * @param paginaVO
     *            el offset, registros por pagina.
     * @return Una instacia de {@link PaginaVO} con los registros obtenidos,
     *         offset, registros por pagina y el total de registros encontrados.
     * @throws BusinessException
     *             en caso de que el agente sea nulo. En caso de que el paginaVO
     *             sea nulo.
     */
    PaginaVO getTopeCirculanteFidecomiso(EmisionVO emisionVO, PaginaVO paginaVO)
            throws BusinessException;

    /**
     * Caso de Uso: CUDI51-02 Obtiene emisiones de acuerdo a los siguientes
     * criterios:
     *
     * @param emisionVO
     *            Los datos de tv, emisora, serie, cupon ,isin, alta y cupon
     *            cortado.
     * @param fechaInicio
     *            Fecha de emisi&oacute;n
     * @param fechaFin
     *            fecha de vencimiento
     * @param paginaVO
     *            n&uacute;mero de registros por pagina y total de registros En caso de
     *            que fechaInicio y fechaFin sean nulos, el servicio asignara la
     *            fecha actual.
     * @return {@link PaginaVO} con los registros obtenidos, el total de
     *         registros y el offset.
     * @throws BusinessException
     *             en caso de que paginaVO sea nulo.
     */
    PaginaVO getConsultaEmisiones(EmisionVO emisionVO, Date fechaInicio,
            Date fechaFin, PaginaVO paginaVO) throws BusinessException;

    /**
     * Caso de uso: CUDI51-03 Devuelve una consulta de trasapasos de acuerdo a
     * los siguientes criterios de consulta: Que pertenescan al agente firmado y
     * que sus traspasos sean como traspasante &oacute; receptor.
     * (params.agenteFirmado) Por contraparte. Traspaso que pertenescan al
     * agente firma y que correspondan con un agente especificado como
     * contraparte (params.contraparte); Por tipo valor, emisora, serie, cupon e
     * isin (params.claveValor) Por fecha de liquidacion (params.traspazosAlDia)
     * Por folio de control (params.folioControl) Por cantidad operada
     * (params.tituloTraspasante)
     *
     * @param params
     *            Bean que puede contener todos los parametros de consulta y la
     *            pagina.
     * @return Una instancia de {@link PaginaVO} con los registros de traspasos
     *         obtenidos de acuerdo a la consulta.
     * @throws BusinessException
     */
    GetTraspasosDivIntVO getTraspasosDivInt(GetTraspazosDivIntParams params)
            throws BusinessException;

    /**
     * Caso de Uso: CUDI51-04 Devuelve una consulta de la tabla valores de
     * acuerdo a los siguiente criterios de consulta: Que correspondan al agente
     * firmado (params.agenteFirmado) Que correspondan al tipo valor, emisora,
     * serie, cupon e isin de su emisi&oacute;n (params.claveValor) Que correspondan
     * con la emision_extr de emision (params.altaCatalogo) Que correspondan con
     * la posicion de terminaci&oacute;n de cuenta: globales, extranjera,
     * 54,94,97,98,99 (params.posicion)
     *
     * @param params
     *            Bean que puede contener los criterios de consulta.
     * @return una instancia de {@link PosicionValorTotalVO} que contiene:
     *         -Total de TV por agente -Total de emisiones por agente -Posici&oacute;n
     *         total. La suma de todos los saldos disponibles de todos los
     *         valores de todas las cuentas. -Cuentas. las cuentas del agente.
     *         -Cuenta actual. la cuenta sobre la que esta hecha la consulta que
     *         se presenta en la pagina -PaginaVO. Contiene los registros
     *         consultados, el rango de consulta y el offset -Total de posici&oacute;n
     *         actual por cuenta. -Total de valuaci&oacute;n mercado por cuenta. -Total
     *         de valuaci&oacute;n nominal por cuenta.
     * @throws BusinessException
     *             en caso de que no exista agente firmado. En caso de que no se
     *             hayan obtenido registros. En caso de que el parametro que
     *             recibe este servicio sea nulo.
     */
    PosicionValorTotalVO getPosicionValor(GetPosicionValorParams params)
            throws BusinessException;

    /**
     * Caso de Uso: CUDI51-05
     *
     * @param params
     *            Bean que representa los parametros de este m&eacute;todo y que
     *            contiene: VO con los datos de la instituci&oacute;n. VO con los datos
     *            de la instituci&oacute;n receptora. Valor de tv Nombre de la emisora
     *            Nombre de la serie. Valor del cup&oacute;n. Valor del isin. Valor del
     *            alta. Valor de la cantidad operada. Valor del saldo
     *            disponible. Valor de la posici&oacute;n actual. Descripci&oacute;n de la
     *            captura.
     * @return Un n&uacute;mero positivo que representa el folio del traspaso capturado
     *         &oacute; un n&uacute;mero negativo en caso de que no haya guradado el traspaso.
     * @throws BusinessException
     *             En caso de que los parametros de entrada sean nulos (agenten
     *             traspasante, receptor, emision, cantidad operada, saldo
     *             disponible, posici&oacute;n actual. En caso de que no haya saldo
     *             disponible en el fidecomiso. En caso de que no se genere los
     *             folios de transmision y de control. En caso de que no sea
     *             posible capturar el traspaso.
     */
    BigInteger getCapturaTraspazo(SetCapturaTraspazoParams params)
            throws BusinessException;

    /**
     * Caso de Uso: CUDI51-06 Obtiene una consulta de los registros de las
     * tablas dbcaptal..depositos..retiros y bdcatal..traspasos que cumplan con
     * los siguintes criterios de selecci&oacute;n:
     *
     * @param fecha
     *            A una fecha de operaci&oacute;n. En el caso de los traspasos es
     *            fecha_liquidacion y para los depositos y retiros fecha_hora.
     *            En caso de que esta fecha se nula, el sistema asignar&aacute; la
     *            fecha actual.
     * @param tipoOperacion
     *            T = traspasos, D = deposistos, R = retiros y * = todos
     * @param agente
     *            Del cual se toma idInst, folioInst y cuenta. En caso de que la
     *            cuenta sea nula se asignara la primer cuenta con la que el
     *            agente en session tenga registros.
     * @param claveValor
     *            De donde se consideran tv, emisora, serie , cupon e isin.
     * @param folioControl
     *            El n&uacute;mero de folio control
     * @param paginaVO
     *            Determina el numero de registros por pagina y el offset
     * @param isExport
     *            Determina si es exportacion. (True)
     * @return Una instancia de {@link EstadoCuentaReturnVO} que contiene el
     *         total de salidas el total de entradas el conjunto de cuentas del
     *         agente y los registros paginados
     * @throws BusinessException
     *             Se arroja una {@link BusinessException} en caso de que el
     *             tipo de operaci&oacute;n sea nulo, en caso de que el agente sea
     *             nulo, en caso de que el paginaVo sea nulo.
     */
    EstadoCuentaTotalVO[] getEstadoCuenta(Date fecha, String tipoOperacion,
            AgenteVO agente, EmisionVO claveValor, String folioControl,
            PaginaVO paginaVO, boolean isExport) throws BusinessException;

    /**
     * Caso de Uso: CUDI51-07 y Caso de Uso: CUDI51-08
     *
     * @param params
     *            Bean que representa los parametros de este m&eacute;todo y que
     *            contiene: La fecha de inicio. La fecha de finalizacion. La
     *            clave de valor original. El valor te. La fecha de pago. La
     *            tasa de interes. El estatus.
     * @return Una objeto {@link PaginaVO} que contiene los registros
     *         consultados
     * @throws BusinessException
     */
    PaginaVO getEjercicioDerechoRentaFijaVariable(
            GetEjercicioDerechoRentaFijaVariableParams params)
            throws BusinessException;

    /**
     * Caso de Uso: CUDI51-11 Obtiene el detalle de las cuentas correspondientes
     * al ejercicio de renta fija o variable con base a la emision original, el
     * id y el folio del agente.
     *
     * @param renta
     *            Ejericio de derecho de renta fija o variable del que se desea
     *            obtener las cuentas, importes y sus posiciones actuales.
     * @param agenteVO
     *            Id y folio del agente
     * @return DetalleEmisionRentaFijaVariableTotalVO
     * @throws BusinessException
     *             en caso de que alguno de los siguientes datos del ejericicio
     *             falte: -La emision original -La emision destino -Proporcion
     *             -Id y folio del Agente
     */
    DetalleEmisionRentaFijaVariableTotalVO getDetalleEmisionRentaFijaVariable(
            EjercicioDerechoRentaFijaVariableVO renta, AgenteVO agenteVO)
            throws BusinessException;

    /**
     * Caso de Uso: CUSI57-02 Obtiene regsitros de la tabla catalogo..oper_sic
     * de acuerdo a los siguientes criterios de consulta:
     *
     * @param emisionVO
     *            tv, emisora, serie, cupon
     * @param agenteVO
     *            idInst, folioInst
     * @param fecha
     *            fecha sobre la cual se desea obtener los registros. si viene
     *            null, el serivicio usara la fecha actual.
     * @param estado
     *            El estatus de la operacion: 'N' = notificada, 'E' = enviada ,
     *            'C' = confirmada , 'P' = pendiente, 'B' = cancelada, 'L' =
     *            liberada &oacute; 'Todos' = incluye todos los status). Cualquier otro
     *            valor distinto ser&aacute; ignorado por el servicio.
     * @param llaveFolio
     *            La llave de folio
     * @param paginaVO
     *            Contiene el offset y el numero de registros a obtener.
     * @param operacion
     *            indica cuando la operaci&oacute;n entrega o recibe, los valores
     *            posibles son 540 y 542
     * @return una instancia de {@link PaginaVO} con los registros obtenidos, el
     *         n&uacute;mero total de registros hallados, el offset y el numero de
     *         registros por pagina.
     * @throws BusinessException
     *             en caso de que el agente sea nulo. En caso de que el PaginaVO
     *             sea nulo.
     */
    PaginaVO getConsultaOperaciones(EmisionVO emisionVO, AgenteVO agenteVO,
            Date fecha, String estado, String llaveFolio, PaginaVO paginaVO,
            String operacion) throws BusinessException;

    /**
     * CUSI57-01 Captura de Operaciones Captura las operaciones de valores de
     * los usuarios que reciben o entregan en el extranjero. Utiliza el SP
     * catalogo..UP_libera_city adem&aacute;s guarda informaci&oacute;n en la tabla
     * catalogo..mensaje_sic
     *
     * @param params -
     *            objeto que contiene los siguientes parametros: agente - Id,
     *            Folio y Cuenta de la instituci&oacute;n emision - Objeto de tipo
     *            emision que contiene el tipo de valor, emision, serie y cupon
     *            tipoOperacion - puede ser Entrega "1" o Recibe "0"
     *            fechaNotificacion - Fecha de captura/notificaci&oacute;n de la
     *            operaci&oacute;n. Asignada autom&aacute;ticamente por el sistema.
     *            fechaLiquidacion - Fecha de Liquidaci&oacute;n de la Operaci&oacute;n.
     *            fechaOperacion - Fecha de la Operaci&oacute;n. cantidad - Cantidad de
     *            titulos a Operar custodio - Descripci&oacute;n del Custodio
     *            descContraparte - Descripci&oacute;n de la cuenta de la contraparte.
     *            cuentaContraparte - Cuenta de la contraparte nombreDepositoLiq -
     *            Nombre del dep&oacute;sito por el que se liquidan los valores
     *            operados instruccEspeciales - Instrucciones especiales para la
     *            contraparte mercado - tipo de mercado nombreCuenta - Nombre de
     *            la cuenta del Beneficiario Final. numeroCuenta - N&uacute;mero de la
     *            cuenta del Beneficiario final. usuario - Usuario Firmado
     *            nombreUsuario - Nombre del Usuario Firmado
     * @return String - folio llave
     * @throws BusinessException
     */
    String capturaOperacion(CapturaOperacionParams params)
            throws BusinessException;

    /**
     * Este m&eacute;todo recupera el c&aacute; del arqueo de valores de
     * divisi&oacute; internaional para las emisiones pertenecientes a la
     * instituci&oacute;n
     *
     * @param agenteVO
     * @param emisionVO
     * @param totalArqueoVO
     * @param fechaConsulta
     * @param isExport
     * @return ArqueoVO
     * @throws BusinessException
     */
    ArqueoVO getArqueoValores(AgenteVO agenteVO, EmisionVO emisionVO,
            TotalArqueoVO totalArqueoVO, Date fechaConsulta, Boolean isExport)
            throws BusinessException;

    /**
     * Este m&eacute;todo implementa la l&oacute;gica de negocio requerida para realizar
     * un traspasaso en la divisi&oacute;n internacional.
     * @param params
     * @throws BusinessException
     * @return BigInteger
     */
    BigInteger businessRulesGetCapturaTraspaso(SetCapturaTraspazoParams params) throws BusinessException;
    
    /**
     * Validaciones de negocio para el servicio de captura de operaciones de divisi&oacute;n 
     * internacional, estas validaciones se realizan para que las operaciones sean enviadas al
     * protocolo financiero. 
     * @param params
     * @return String
     * @throws BusinessException
     */
    String businessRulesCapturaOperacion(CapturaOperacionParams params) throws BusinessException;
    
}
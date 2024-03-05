/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadocapitales;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
/**
 * Interfaz para el servicio de Capitales, con los metodos que estaran
 * publicados.
 *
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 * @author enavarrete
 */
public interface MercadoCapitalesService {

    /**
     * Definen el texto para obtener la lista de tipos de valor por agente y
     * saber a que tabla se hace referencia, estas cadenas se deben utilizar
     * como argumentos del metodo getTipoValorByAgente, en el parametro tabla
     */
    public static final String BDCAPTAL_MDINTRAN = "bdcaptal_mdintran";

    /**
     *
     */
    public static final String BDCAPTAL_VALORES = "bdcaptal_valores";

    /** Indica estatus de registro nuevo en la tabla temporal */
    public static final String REGISTRO_NUEVO = "NU";

    /** Indica estatus de registro cargado en la BD */
    public static final String REGISTRO_CARGADO = "CA";

    /** Indica estatus de registro con error en la tabla temporal */
    public static final String REGISTRO_ERROR = "ER";

    /**
     * Indica el tipo de registro a utilizar en la tabla temporal
     * catalogo..filetran - para la opcion de traspasos del menu FileTran en SCO
     */
    public static final String ARCHIVO_TRASPASOS = "CT";

    /**
     * Indica el tipo de registro a utilizar en la tabla temporal
     * catalogo..filetran - para la opcion de liberacion del menu FileTran en
     * SCO
     */
    public static final String ARCHIVO_LIBERACION = "CL";

    /**
     * Indica el tipo de registro a utilizar en la tabla temporal
     * catalogo..filetran - para la opcion de confirmacion del menu FileTran en
     * SCO
     */
    public static final String ARCHIVO_CONFIRMACION = "CC";

    /**
     * Indica el tipo de registro a utilizar en la tabla temporal
     * catalogo..filetran - para la opcion de traspasos del menu FileTran en
     * SIDV-MC
     */
    public static final String ARCHIVO_TRASPASOS_SIDV_MC = "ST";

    /**
     * Caso de uso CUMC47-02 Captura de traspaso
     *
     * @param params
     * @return BigInteger
     * @throws BusinessException
     */
    BigInteger getCapturaTraspaso(GetCapturaTraspasoParams params) throws BusinessException;

    /**
     * Caso de uso CUMC47-11 Consulta de Posicion Valores de Mercado de
     * Capitales
     *
     * @param params
     * @return GetPosicionValorMerCapParams
     * @throws BusinessException
     */
    PosicionValorTotalVO[] getPosicionValorPlain(GetPosicionValorMerCapParams params)
            throws BusinessException;

    /**
     * Caso de uso CUMC47-14 Estado de Cuenta de Mercado de Capitales
     *
     * @param params
     * @return Un arreglo con objetos de tipo EstadoCuentaMercadoCapitalTotalVO
     * @throws BusinessException
     */
    EstadoCuentaTotalVO[] getNuevoEstadoCuentaMercadoCapital(GetEstadocuentaMercadoParams params)
            throws BusinessException;

    /**
     * Caso de uso CUMC47-16 Operaciones Pendientes e Incumplidas
     *
     * @param params
     * @return PaginaVO
     * @throws BusinessException
     */
    PaginaVO getOperacionPendienteIncumplida(GetOperacionPendienteIncumplidaParams params)
            throws BusinessException;

    /**
     * Caso de uso CUMC47-21 Operaciones a Liquidar Plazo Futuro
     *
     * @param agente
     * @param tipoFecha
     * @param fecha
     * @param paginaVO
     * @return OperacionLiqFuturoTotalVO
     * @throws BusinessException
     */
    OperacionLiqFuturoTotalVO getOperacionLiqFuturo(AgenteVO agente, String tipoFecha, Date fecha,
            PaginaVO paginaVO) throws BusinessException;

    /**
     * Caso de uso CUMC47-26 ConfirmacionTraspasosMiscelaneaFiscal
     *
     * @param agenteFirmado
     * @return Un arreglo con objetos de tipo ConfirmaTraspasoVO
     */
    ConfirmaTraspasoVO[] getConfirmacionTraspaso(AgenteVO agenteFirmado);

    /**
     * Caso de uso CUMC47-02 Captura de traspasos de Mercado de Capitales Guarda
     * informaci&oacute;n en la tabla bdcaptal..mdintran
     *
     * @param params -
     *            objeto que contiene los siguientes parametros: traspasante -
     *            Id, Folio y Cuenta de la instituci&oacute;n. receptor - Id, Folio y
     *            Cuenta de la instituci&oacute;n. emision - Objeto de tipo emision que
     *            contiene el tipo de valor, emision, serie y cupon. cantidad -
     *            Cantidad por la que se realiza el traspaso. cveReporto - Clave
     *            Reporto. precioTitulo - Precio por Titulo. fechaLiquidacion -
     *            Fecha de Liquidaci&oacute;n de la Operaci&oacute;n. liquidacion - Es el
     *            plazo de liquidaci&oacute;n del traspaso usuario - Usuario Firmado.
     *            nombreUsuario - Nombre del Usuario Firmado.
     * @return Integer - folio control
     * @throws BusinessException
     */
    Integer capturaTraspasoContraPago(GetCapturaTraspasoContraPagoParams params)
            throws BusinessException;

    /**
     * Caso de uso <b>CUEC 01-01 Estado de Cuenta &uacute;nico</b>, consulta el
     * estado de cuenta &uacute;nico
     *
     * @param params
     * @return EstadoCuentaUnicoVO[]
     * @throws BusinessException
     */
    EstadoCuentaUnicoVO[] getEstadoCuentaUnico(EstadoCuentaUnicoParams params)
            throws BusinessException;


    /**
     * Caso de uso <b>CUEC 01-01 Estado de Cuenta &uacute;nico</b>, consulta el
     * estado de cuenta &uacute;nico
     *
     * @param params
     * @return String[]
     * @throws BusinessException
     */
    String[] getCuentasEstadoCuentaUnico(EstadoCuentaUnicoParams params) throws BusinessException;

    /**
     * Regresa la lista de registros de traspasos contrapago en donde la fecha
     * de liquidacion el mayor o igual a la fecha actual del sistema, en el
     * estado indicado <br>
     * <b>C</b> por confirmar<br>
     * <b>P</b> pendiente de liquida<br>
     * <b>L</b> liquidado<br>
     * si no se pasa el estado el servicio regresa los tres anteriores
     *
     * @param agenteFirmado
     *            id y folio del agente firmado
     * @param estado
     *            estado del traspaso
     * @return Arreglo del tipo TraspasosContraPagosVO
     * @throws BusinessException
     */
    TraspasosContraPagosVO[] getListaConfirmacionTraspasoContraPago(AgenteVO agenteFirmado,
            String estado) throws BusinessException;

    /**
     * Realiza la confirmacion de los registros de la captura de traspaso contra
     * pago
     *
     * @param params
     * @return True - si se actualizaron correctamente todos los registros
     *         solicitados
     * @throws BusinessException
     */
    boolean confirmaTraspasoContraPago(ConfirmaTraspasoContraPagoParams[] params)
            throws BusinessException;

    /**
     * CUEC 01-02 Archivo de Conciliacion Realiza la consulta de conciliacion de
     * valores (de dinero y capitales) y genera un archivo plano en caso de
     * indicarlo
     *
     * @param agenteFirmado
     * @param emision
     * @param pagina
     * @return PaginaVO
     * @throws BusinessException
     */
    PaginaVO archivoConciliacion(AgenteVO agenteFirmado, EmisionVO emision, PaginaVO pagina)
            throws BusinessException;

    /**
     * Captura de traspasos Libres de Pago Fecha Valor Guarda informaci&oacute;n en la
     * tabla bdcaptal..mdintran
     *
     * @param params -
     *            objeto que contiene los siguientes parametros: traspasante -
     *            Id, Folio y Cuenta de la instituci&oacute;n. receptor - Id, Folio y
     *            Cuenta de la instituci&oacute;n. emision - Objeto de tipo emision que
     *            contiene el tipo de valor, emision, serie y cupon. cantidad -
     *            Cantidad por la que se realiza el traspaso. cveReporto - Clave
     *            Reporto. precioTitulo - Precio por Titulo. fechaLiquidacion -
     *            Fecha de Liquidaci&oacute;n de la Operaci&oacute;n. liquidacion - Es el
     *            plazo de liquidaci&oacute;n del traspaso usuario - Usuario Firmado.
     *            nombreUsuario - Nombre del Usuario Firmado.
     * @return Integer - folio control
     * @throws BusinessException
     */
    Integer capturaTraspasoTLPFechaValor(GetCapturaTraspasoContraPagoParams params)
            throws BusinessException;

    /**
     * CUEC 01-02 Archivo de Conciliacion Realiza la consulta del detalle de la
     * conciliacion de valores (de dinero y capitales) y genera un archivo plano
     * en caso de indicarlo
     *
     * @param agente
     * @param pagina
     * @return PaginaVO
     * @throws BusinessException
     */
    PaginaVO archivoConciliacionMovimientos(AgenteVO agente, PaginaVO pagina)
            throws BusinessException;

    /**
     * CUEC 01-02 Archivo de Conciliacion Realiza la consulta de movimientos de
     * la conciliacion de valores (de dinero y capitales) y genera un archivo
     * plano en caso de indicarlo
     *
     * @param agente
     * @param emision
     * @param pagina
     * @return PaginaVO
     * @throws BusinessException
     */
    PaginaVO archivoConciliacionDetalle(AgenteVO agente, EmisionVO emision, PaginaVO pagina)
            throws BusinessException;


    /**
     * @param getEstadoCuentaSociedadesInvParams
     * @return EstadoCuentaSocInvVO[]
     * @throws BusinessException
     */
    EstadoCuentaSocInvVO[] getEstadoCuentaSociedadesInversion(
            GetEstadoCuentaSociedadesInvParams getEstadoCuentaSociedadesInvParams)
            throws BusinessException;

    /**
     * Servicio para recuperar las operaciones del d&iacute;a del m&oacute;dulo de capitales.
     *
     * @param operacionDiaCapitalesParams
     * @return OperacionDiaCapitalesVO
     * @throws BusinessException
     */
    OperacionDiaCapitalesVO getOperacionDiaCapitalesVO(
            OperacionDiaCapitalesParams operacionDiaCapitalesParams) throws BusinessException;

    /**
     * Recupera un objeto OperacionDiaDetalleCapitalesVO a partir de los objetos
     * que componen la llave primaria de bdcaptal..traspasos
     *
     * @param traspasoCapitalesVO
     * @return OperacionDiaDetalleCapitalesVO
     * @throws BusinessException
     */
    OperacionDiaDetalleCapitalesVO getDetalleOperacionDiaCapitales(
            TraspasoCapitalesVO traspasoCapitalesVO) throws BusinessException;

    /**
     * Reglas de negocio para efectuar la captura de un traspaso.
     *
     * @param capturaTraspasoParams
     * @return String
     * @throws BusinessException
     */
    String businessRulesCapturaTraspaso(GetCapturaTraspasoParams capturaTraspasoParams)
            throws BusinessException;


    /**
     * Reglas de negocio para efectuar la captura de traspasos contra pago.
     *
     * @param params
     * @return String
     * @throws BusinessException
     */
    String businessRulesCapturaTraspasoContraPago(GetCapturaTraspasoContraPagoParams params)
            throws BusinessException;
    
    /**
     * @param agente
     * @param emision
     * @return List
     */
    @SuppressWarnings("unchecked")
	public List getListaEmisionSociedadesInversion(AgenteVO agente,
			EmisionVO emision) throws BusinessException;
    
    /**
     * @param agente
     * @param emision
     * @return List
     */
    @SuppressWarnings("unchecked")
	public List getListaEmisionSociedadesInversionRazonSocial(AgenteVO agente,
			EmisionVO emision) throws BusinessException;
    
    /**
     * @param agente
     * @param emisora
     * @return List
     * @throws BusinessException
     */
    @SuppressWarnings("unchecked")
	public List getSaldoEmisionSociedadesInversionRazonSocial(AgenteVO agente,
			String emisora, Boolean debeDejarLog) throws BusinessException;
}

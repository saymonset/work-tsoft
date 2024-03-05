/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * 
 * Apr 16, 2008
 */
package com.indeval.portaldali.presentation.common.constants;

/**
 * Constantes para los componente de validacion en la capa WEB
 * 
 * @author Erik Vera Montoya
 * @version 1.0
 */
public interface ValidadoresConstantes {

    /** El identificador del bean para validar una cuenta */
    String VALIDADOR_CUENTA = "validadorCuenta";

    /**
     * Identificador del bean de spring para validar una venta en captura de operaciones en MD (mismo día)
     */
    String VALIDADOR_CAPTURA_OPERACIONES_VENTA_MD = "validadorCapturaOperacionesVentaMismoDia";

    /**
     * Identificador del bean de spring para validar una venta en captura de operaciones en FV (fecha valor)
     */
    String VALIDADOR_CAPTURA_OPERACIONES_VENTA_FV = "validadorCapturaOperacionesVentaFechaValor";

    /**
     * Identificador del bean de spring para validar una venta en captura de operaciones en MD (mismo día) y CD (compra directo)
     */
    String VALIDADOR_CAPTURA_OPERACIONES_VENTA_MD_CD = "validadorCapturaOperacionesVentaMismoDiaCompraDirecto";

    /**
     * Identificador del bean de spring para validar una venta en captura de operaciones en FV (fecha valor) y CD (compra en
     * directo)
     */
    String VALIDADOR_CAPTURA_OPERACIONES_VENTA_FV_CD = "validadorCapturaOperacionesVentaFechaValorCompraDirecto";

    /**
     * Identificador del bean de spring para validar una Captura de Operaciones mismo día
     */
    String VALIDADOR_CAPTURA_OPERACIONES_REPORTO_NOMINAL_MD = "validadorCapturaOperacionesReportoNominalOpcionMismoDia";
    /**
     * Identificador del bean de spring para validar una Captura de Operaciones para una fecha
     */
    String VALIDADOR_CAPTURA_OPERACIONES_REPORTO_NOMINAL_FV = "validadorCapturaOperacionesReportoNominalOpcionFechaValor";
    /**
     * Identificador del bean de spring para validar una Compra en reporto mismo día
     */
    String VALIDADOR_CAPTURA_OPERACIONES_REPORTO_NOMINAL_COMPRA_MD = "validadorCapturaOperacionesReportoNominalOpcionCompraEnMD";
    /**
     * Identificador del bean de spring para validar una Compra en reporto para una fecha.
     */
    String VALIDADOR_CAPTURA_OPERACIONES_REPORTO_NOMINAL_COMPRA_FV = "validadorCapturaOperacionesReportoNominalOpcionCompraEnFV";

    /**
     * Identificador del bean de spring para validar una captura de operaciones para la opción Traspaso libre de pago
     */
    String VALIDADOR_CAPTURA_TRASPASO_LIBRE_PAGO = "validadorCapturaOperacionesTraspasoLibrePago";

    /**
     * Identificador del bean de spring para validar una captura de operaciones para la opción Fondeo a Cuenta Propia
     */
    String VALIDADOR_CAPTURA_FONDEO_CTA_PROPIA = "validadorCapturaOperacionesFondeoCtaPropia";

    /**
     * Identificador del bean de spring para validar una captura de operaciones para las distintas opción de colocacion primaria:
     * Captura de Operaciones mismo día
     */
    String VALIDADOR_CAPTURA_OPERACIONES_COLOCACION_PRIMARIA_MD = "validadorCapturaOperacionesColocacionPrimariaOpcionMismoDia";
    /**
     * Identificador del bean de spring para validar una captura de operaciones para las distintas opción de colocacion primaria:
     * Captura de Operaciones para una fecha
     */
    String VALIDADOR_CAPTURA_OPERACIONES_COLOCACION_PRIMARIA_FV = "validadorCapturaOperacionesColocacionPrimariaOpcionFechaValor";
    /**
     * Identificador del bean de spring para validar una captura de operaciones para las distintas opción de colocacion primaria:
     * Compra en reporto mismo día
     */
    String VALIDADOR_CAPTURA_OPERACIONES_COLOCACION_PRIMARIA_COMPRA_MD = "validadorCapturaOperacionesColocacionPrimariaOpcionCompraEnMD";
    /**
     * Identificador del bean de spring para validar una captura de operaciones para las distintas opción de colocacion primaria:
     * Compra en reporto para una fecha.
     */
    String VALIDADOR_CAPTURA_OPERACIONES_COLOCACION_PRIMARIA_COMPRA_FV = "validadorCapturaOperacionesColocacionPrimariaOpcionCompraEnFV";

    /**
     * Identifiador del bean de spring para validad una captura de operaciones para las distintas opciones de apertura de sistema:
     * Apertura de Sistema
     */
    String VALIDADOR_CAPTUTA_OPERACION_APERTURA_SISTEMAS = "validadorAperturaSistemas";
    /**
     * Identifiador del bean de spring para validad una captura de operaciones para las distintas opciones de apertura de sistema:
     * Apertura de Sistema Recepción
     */
    String VALIDADOR_CAPTUTA_OPERACION_APERTURA_SISTEMAS_RECEPCION = "validadorAperturaSistemasRecepcion";

    /**
     * Identificador del bean de Spring para validar una captura de operaciones para las distintas opciones de traspaso efectivo
     * cuentas control misma institucion
     */
    String VALIDADOR_TRASPASO_EFECTIVO_CUENTA_CONTROL_MISMA_INSTITUCION = "validacionTraspasoEfectivo";

    /**
     * Identificador del bean de Spring para validar una captura de operaciones para las distintas opciones de traspaso efectivo
     * cuentas control terceros
     */
    String VALIDADOR_TRASPASO_EFECTIVO_CUENTA_CONTROL_TERCEROS = "validacionTraspasoEfectivoCuentasTerceros";

    /**
     * Identificador del bean de Spring para validar los Traspasos Administrativos
     */
    String VALIDADOR_TRASPASOS_ADMINISTRATIVOS = "validadorTraspasosAdministrativos";

    /**
     * Identificador del bean de Spring para validar los Retiros de Efectivo.
     */
    String VALIDADOR_RETIRO_EFECTIVO = "validadorRetiroDeFondos";

    /**
     * Identificador del bean de Spring para validar traspaso miscelanea fiscal
     */
    String VALIDADOR_TRASPASO_MF = "validadorTraspasoMiscelaneaFiscal";

    /**
     * Identificador del bean de Spring para validar traspaso miscelanea fiscal con Recepción
     */
    String VALIDADOR_TRASPASO_MF_RECEPCION = "validadorTraspasoMiscelaneaFiscalRecepcion";
    
    

}

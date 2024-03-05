package com.indeval.portaldali.presentation.common.constants;
 
public class TesoreriaConstantes {
	
	/** variable para obtener la clave para la cuenta concentradora*/
	public final static String CUENTA_CONCENTRADORA="CC";
    
    public final static String CUENTA_DINERO="MD";
    
    public final static String CUENTA_CAPITALES="MC";
    
    public final static String TIPO_RETIRO_MISMO="mismo";
    
    public final static String TIPO_RETIRO_TERCERO="tercero";
    
    
	/** variable para enviar la respuesta de exito*/
	public final static String CONFIRMAR_RETIRO="confirmarRetiro";
	
	/** variable para enviar la respuesta de exito al traspaso entre cuentas*/
	public final static String CONFIRMAR_TRASPASO="confirmarTraspasoEntreCuentas";
	
	/** variable para enviar la respuesta de exito*/
	public final static String EXITO="exito";
	
	/** variable para enviar la respuesta de error*/
	public final static String ERROR="error";
	
	/** variable para enviar la respuesta para la consulta de liquidacion por decretos */
	public final static String LIQUIDACION_POR_DECRETOS="liquidacionPorDecretos";
	
	/** variable para enviar la respuesta para la consulta a detalle de liquidacion por decretos */
	public final static String LIQUIDACION_POR_DECRETOS_DETALLE="liquidacionPorDecretosDetalle";	
	
	/** variable para enviar la respuesta para la consulta a detalle de liquidacion por decretos */
	public final static String LIQUIDACION_POR_DECRETOS_DETALLE_AMORTIZACION="liquidacionPorDecretosDetalleAmortizacion";	
		
	
	/** variable para enviar la respuesta para el retiro de fondos de indeval - banxico */
	public final static String RETIRO_DE_FONDOS_INDEVAL_BANXICO="retiroDeFondos";
	
	/** variable para enviar la respuesta del caso de uso 3 */
	public final static String TRASPASO_ENTRE_CUENTAS_CONTROL_MISMA="traspasoEntreCtaControlMisma";	
    
    public final static String TRASPASO_ENTRE_CUENTAS_CONTROL_TERCEROS="traspasoEntreCtaControlTerceros";

	/** variable para identificar la operacion PAGO */
	public final static String PAGOS="1";	

	/** variable para enviar la respuesta para la consulta de Saldos de 3 Cuentas */
	public final static String CONSULTA_TRES_CUENTAS="consultaTresCuentas";	
	
	/** variable para enviar la respuesta para la consulta de Saldos de 3 Cuentas */
	public final static String CONSULTA_TRES_CUENTAS_EXCEL="consultaTresCuentasExcel";	

	/** variable para enviar la respuesta para la consulta de Saldos de 3 Cuentas */
	public final static String CONSULTA_TRES_CUENTAS_PDF="consultaTresCuentasPdf";	

	/** variable para enviar la respuesta para la consulta de Traspasos entre Sistemas de Pago */
	public final static String CONSULTA_TRASPASOS_SISTEMAS_PAGO="consultaTraspasoSistemas";	

	/** variable para enviar la respuesta para la consulta de Traspasos entre Sistemas de Pago Excel*/
	public final static String CONSULTA_TRASPASOS_SISTEMAS_PAGO_EXCEL="consultaTraspasoSistemasExcel";	

	/** variable para enviar la respuesta para la consulta de Traspasos entre Sistemas de Pago Pdf*/
	public final static String CONSULTA_TRASPASOS_SISTEMAS_PAGO_PDF="consultaTraspasoSistemasPdf";	
	/**
	 * Clave de la divisa Mexican Peso
	 */
    public final static String CLAVE_MEXICAN_PESO = "MXN";
    
    /** Valor del segmento de la semilla para obtener el d&iacute;gito verificador correspondiente 
     * al identificador y folio de la instituci&oacute;n.
     */
    public static final String CTA_EFE_SEMILLA_ID_FOLIO = "13713";
    
    /** Valor del dígito sobre el cual se hace la resta para obtener el digito verificador */
    public static final String CTA_EFE_NUMERO_A_RESTAR = "50";
    
    /** Digitos de relleno para obtener el relleno de la cuenta efectivo */
    public static final String CTA_EFE_SUMANDO_CLAVE_INDEVAL_PLAZA_CEROS = "18";
    
    /** Valor de la cuenta de efectivo */
    public static final String CTA_EFE_SUMANDO_CUENTA_EFECTIVO = "4";

    /** Clave Indeval para las cuentas de efectivo */
    public static final String CTA_EFE_CLAVE_INDEVAL = "902";
    
    /** Plaza Indeval para el distrito federal para las cuentas de efectivo */
    public static final String CTA_EFE_PLAZA_INDEVAL_DF = "180";
    
    /** Caracteres de relleno para las cuentas de efectivo */
    public static final String CTA_EFE_00_RELLENO = "00";
    
    /** Caracteres de relleno para las cuentas de efectivo */
    public static final String CTA_EFE_DALI_CTA_EFECTIVO = "2000";
	
    /** ID del tipo de intruccion */
	public static final String TIPO_INSTRUCCION__TRASPASO_EFECTIVO = "TREF";
}

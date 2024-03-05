package com.indeval.portaldali.presentation.common.constants;

public interface UtilConstantes {
	
    /** variable para enviar la respuesta de exito en Posicion Valores*/
     String EXITOPOSICION="exitoposicion";
     String EXITOPOSICION_EXCEL="exitoposicionExcel";
     String EXITOPOSICION_PDF="exitoposicionPdf";
     String EXITOPOSICION_PRINT="exitoposicionPrint";
     String EXITO_EDO_CTA_EFEC="exitoEdoCtaEfectivo";
    /** variable para enviar la respuesta de exito en Concentrado Aportaciones*/
     String EXITOCONCENTRADO="exitoconcentrado";
     String EXITOMP="exitoMediosPagos";
    
    /** Variable para consulta de confirmaci&oacute;n de Contrapago*/
     String EXITOCONTRAPAGO="exitoContrapago";
    
     String DET_LD_AMORT="detLDAmort";
     String DET_LD="detLD";
   
    /** variable para enviar la respuesta de exito en Saldo Tres Cuentas*/
	 String SALDOTRESCUENTAS="SaldoTresCuentas";
	
	/** variable para inicializar en estado de cuenta de garantias */
	 String EXITOINIT ="exitoInicializar";

	/** variable para inicializar en Mercado Capitales captura traspasos */
	 String EXITOTRASPASOS ="exitoInitTraspasos";
	
	/** variable por inicializar en Confirmacion Contrapago de mercado de Capitales*/
	 String EXITOINITCONFCONTRA = "exitoInitContrapago";
	
	/** variable para inicializar en Mercado Capitales captura traspasos */
	 String EXITOCAPTURAT ="exitoCapturaTraspasos";
	
	
	/** variable para limpiar estado de cuenta de Valores */
	 String EXITOLIMPIAR = "limpiarEstadoCuentaValores";
	
	/** variable para inicializar operaciones concertadas */
	 String EXITOOPERACIONES ="exitoInitOperaciones";
    
    /** variable para inicializar operaciones concertadas */
     String EXITOPRESTAMONETOSINC ="exitoInitPrestamos";
	
	 String EXITO_EDO_CTA_CAP="exitoEdoCtaCapitales";
	
	/** variable para enviar la respuesta de exito*/
	 String EXITO="exito";
     String EXITOLA="listaAgentes";
     String EXITOLC="listaCuentas";
     String EXITOLC_SILOPP="listaCuentasSilopp";
     String EXITOLCBYN="listaCuentasByNombre";
     String EXITOLE="listaEmisiones";
	
	/** variable para enviar la respuesta a la pagina de login*/
	 String RELOGIN="relogin";
    
     String UNAUTORIZED="unautorized";
	
	/** variable para enviar la respuesta de error*/
	 String ERROR="error";
    
    /** variable para enviar la respuesta de error en las listas*/
     String ERROR_LISTA="errorLista";
	
	/** variable para enviar la respuesta del caso de uso 1 */
	 String CASO_DE_USO_1="caso1";
	
	/** variable para enviar la respuesta del caso de uso 2 */
	 String CASO_DE_USO_2="caso2";
	
	/** variable para enviar la respuesta del caso de uso 3 */
	 String CASO_DE_USO_3="caso3";	
	
	/** variable para enviar la respuesta del caso de uso 1 */
	 String MD4301="estatus_operaciones";
	
	/** variable para enviar la respuesta del caso de uso 2 */
	 String MD4302="captura_operaciones";
	
	/** variable para enviar la respuesta del caso de uso 3 */
	 String MD4303="posicion_valores";
	
	/** variable para enviar la respuesta del caso de uso 4 */
	 String MD4304="estado_cuenta";	
	
	/** variable para enviar la respuesta del caso de uso 1 de Garantias Banxico */
	 String GB5301="captura_garantias";

	/** variable para enviar la respuesta del caso de uso 2 de Garantias Banxico */
	 String GB5302="posicion_garantias";
	
	/** variable para enviar la respuesta del caso de uso 3 de Garantias Banxico */
	 String GB5303="estatus_garantias";

	/** variable para enviar la respuesta del caso de uso 4 de Garantias Banxico */
	 String GB5304="lista_emisiones";

	/** variable para enviar la respuesta de la carga de parametros de vuelta al caso de uso GB5303 */
	 String GB5305="carga_parametros";	
	
	/** variable para enviar la respuesta del caso de uso 2 de ValPre E */
	 String VA6902="concertacion_prestamo";	
	
	/** variable para enviar la respuesta del caso de uso 3 de ValPre E */
	 String VA6903="administracion_garantias";

	 String MF01 = "apertura_misc_fiscal";
	 String MF02 = "confimacion_aper_sistemas_dinero";
	 String MF03 = "traspaso_misc_fiscal";
	 String MF04 = "consulta_mov_misc_fiscal_merc_dinero";
	 String MF05 = "traspaso_pantalla_misc_fiscal";
	 String MF06 = "confirmacion_traspaso_misc_fiscal";
    
     String ADMIN_GARANTIAS = "administracionGarantias";
     String G6505 = "estado_cuenta_garantias";
	 String C1159 = "estado_cuenta_valores";
	/** variable para enviar la respuesta del caso de uso 12 de netos y operaciones */
	 String cuCO5904 = "Asignacion_de_hechos";
	 String NO6112 = "estado_cuenta_efectivo";
	 String NO6110 = "operaciones_forzosas";
	 String NO6113 = "detalle_operacion_forzosa";
	
	 String TRASPASO_TIPO_CONTRA_PAGO = "TV";
	
	 String TRASPASO_TIPO_LIBRE_PAGO = "TL";
	 String TRASPASO_TIPO_ANTICIPADO = "TA";
	 String TRASPASO_ESTADO_ENVIADO = "EN";
	 String TRASPASO_ESTADO_RETENIDO = "RE";
	 String TRASPASO_ESTADO_LQ = "LQ";
	
	 String DI5107 = "historicoRentaFija";
	 String DI5108 = "historicoRentaVariable";
	/**  ADD OCDR */
	 String MOSTRAR_CICLOS_LIQ = "mostrar_ciclos_liq";
	 String ASIGNA_HECHOS_EXITO = "asigna_hechos_exito";
	 String MOSTRAR_HECHOS_ERROR = "mostrar_hechos_error";
	 String MOSTRAR_HECHOS_PDF = "mostrar_hechos_pdf";
	 String MOSTRAR_HECHOS_XLS = "mostrar_hechos_xls";
	 String HECHOS_EXITO="exito_hecho";
	
	 String OPEINCUMPLIDAS_EXITO="exito_opeIncumplida";
	 String MOSTRAR_OPEINCUMPLIDA_ERROR = "mostrar_opeIncumplida_error";
	 String OPER_LIQ_FUTURO_EXITO="exito_operLiqFuturo";
	/** estado de cuenta unico */
	 String EDO_CTA_UNICO_EXITO="estadoCuentaUnico";
	 String EDO_CTA_SOC_INV="estadoCuentaSocInv";
	
	 String MC4714 = "estado_cuenta_capitales";
	 String MC4726 = "confirmacionTraspasoCapital";
	
	 String COPIA = "copia";
	
	 String OFF = "display:none;visibility:collapse;visibility:hidden;visibility:hide;";
	 String ON  = "display:block;visibility:visible;";
	
	 String POS_VAL_SIMPLE = "posicion_valores_simple";
	 String VENCIMIENTOS_PEND = "consulta_vencimientos_pendientes";
	
	 String TRASPASO_DINERO = "confimacion_traspasos_dinero";
	
	 String OPERACION_DIA_CAPITALES = "operaciones_dia_capitales";
	 String OPERACION_DIA_DINERO = "operaciones_dia_dinero";
	 String ARQUEO_VALORES = "arqueo_valores";
	 String POS_VALOR_CONTROL = "posicionValorControl";
	 
	 String ERROR_ISO_DIFERENTE = "EL código Hash del iso generado y del iso firmado en pantalla son diferentes.";
 
	 /* Opciones de login en la seguridad */
    /** Login y password */
    int LOGIN_PASSWORD = 0;
    
    /** Login, password, token */
    int LOGIN_OTP = 2;
    
    /** Valor de login con firma digital */
    int LOGIN_PASSWORD_CERTIFICADO = 4;
    
    /** Autentificacion por DEFAULT que es tomada del SISTEMA asociado, puede ser cualquiera de las anteriores */
    int  DEFAULT_VALUE = 3;
	
}

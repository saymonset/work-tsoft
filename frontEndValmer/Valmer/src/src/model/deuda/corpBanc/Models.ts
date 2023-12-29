import React from "react";

export type DataTypeKey = "FvDeudaCorpInstrumentos" | "FvDeudaGobInstrumentos" | "FvInterInstrumentos";

export type HTMLInputSelectNull = HTMLInputElement | HTMLSelectElement | null

export interface CarCalTasas {
    n_crv_descuento: string;
    n_familia: string;
    b_ajuste_fc: string;
    d_fecha_ini_cupon: string;
    n_tipo_tasa: string;
    n_tasa_cupon_48_vig: string;
    b_proteccion_infl: string;
    d_fecha_vencimiento: string;
    n_prima: string;
    n_frecuencia_cupon: string;
    b_intereses_hibrido: string;
    n_titulos_circ: string;
    n_tipo_promedio: string;
    n_tasa_cupon_cierre: string;
    n_dia_corte_cupon: string;
    n_dias_deter_tasa: string;
    n_crv_referencia: string;
    n_convencion: string;
    n_vn: string;
    n_periodo_cupon: string;
    n_rendondeo: string;
    n_tasa_cupon_md_vig: string;
    n_tipo_instrumento: string;
    n_monto_circ: string;
    n_num_cupon_vig: string;
    n_tasa_cupon_24_vig: string;
    b_pago_intereses: string;
    d_fecha_fin_cupon: string;
    n_calc_fc: string;
    n_vna: string;
    n_moneda: string;
    n_calc_dias: string;
    n_periodo_cupon_vig: string;
    n_tipo_calculo: string;
    d_fecha_emision: string;
}

export interface CalTasasIntereses {
    n_tc_48: string;
    n_intereses_mo_48: string;
    n_tc_24: string;
    n_tasa_cupon_vig: string;
    n_intereses_48: string;
    n_tasa_cupon_24_vig: string;
    n_intereses_md: string;
    n_dias_trans_cpn_48: string;
    n_intereses_mo_md: string;
    n_dias_trans_cpn_24: string;
    n_intereses_mo_24: string;
    n_tc_md: string;
    n_intereses_24: string;
    n_tasa_cupon_48_vig: string;
    n_dias_trans_cpn_md: string;
}

export interface BodyCalTasInt {
    property: string;
    data: CarCalTasas | CalTasasIntereses;
}

export interface CalTasasInt {
    status: number;
    body: BodyCalTasInt[];
}

interface CalPreciosFlujo {
    property: string;
    data: {
        FLUJO: string;
        INTERESES: string;
        VPF: string;
        VALOR_NOMINAL: string;
        PERIODO: string;
        DIAS: string;
        AMORTIZACIONES: string;
    };
}

export interface CalPrecios {
    status: number;
    body: {
        'INTERESES FLUJOS': string;
        'PRECIO SUCIO FLUJOS': string;
        FLUJOS: CalPreciosFlujo[];
        TASA_REND: string;
        CONVEXIDAD: string;
        'PRECIO LIMPIO': string;
        'DURACION MAUCULAY': string;
        'PRECIO SUCIO': string;
        'DURACION CUPON': string;
        'PRECIO LIMPIO FLUJOS': string;
        'DURACION MONETARIA': string;
        INTERESES: string;
        'DURACION MODIFICADA': string;
    };
}


export interface DataCorteCupon {
    FEC_FIN_CUPON: string;
    REF_MERC: string;
    FEC_VTO: string;
    VAL_NOM: string;
    FEC_INI_CUPON: string;
    DIAS_X_VENC: string;
}

export interface BodyCorteCupon {
    property: string;
    data: DataCorteCupon;
}

export interface ResponseCorteCupon {
    status: number;
    body: BodyCorteCupon[];
}

export interface DataBonosRef {
    "DUR_INSTR": string,
    "DUR_BONO_AYER": string,
    "FEHA": string,
    "BONO_HOY": string,
    "CAMBIO": string,
    "BONO_AYER": string,
    "DUR_BONO_HOY": string
    "SOBRETASA": string
}

export interface BodyBonosRef {
    property: string;
    data: DataBonosRef;
}

export interface ResponseBonosRef {
    status: number;
    body: BodyBonosRef[];
}

export interface FormValuesCorp {
    [key: string]: string | FlujosCorp[];
}

export interface FlujosCorp {
    property: string;
    data: FlujosData | FlujosValData | FlujosCuponData;
}

export interface IsFlujosCorp {
    [key: string]: boolean
}

export interface FlujosData {
    [key: string]: string
    d_fecha_ini_cupon: string;
    n_tasa_cupon: string;
    d_fecha_fin_cupon: string;
}

interface FlujosValData {
    amort_proc: string;
    vn: string;
}

interface FlujosCuponData {
    n_tasa_intercupon: string;
    n_vn_intercupon: string;
    d_fecha_intercupon: string;
}

export interface CatalogCalificadora {
    status: number;
    body: { [key: string]: string }
}

export type ActionTvEmiSerieCorpEdit = | { type: 'UPDATE', payload: Partial<StateTvEmiSerieDataCorpEdit> };

export type StateTvEmiSerieDataCorpEdit = {
    triggerEmisora: boolean;
    triggerSerie: boolean;
    triggerConsultaData: boolean;
    loadingEmisoras: boolean;
    loadingSerie: boolean;
    loadingConsultaData: boolean;
    triggerEmisoraRef1: boolean;
    triggerEmisoraRef2: boolean;
    triggerSerieRef1: boolean;
    triggerSerieRef2: boolean;
    loadingEmisorasRef1: boolean;
    loadingSerieRef1: boolean;
    loadingEmisorasRef2: boolean;
    loadingSerieRef2: boolean;
};

export interface FvDeudaCorpInstrumentos extends FormValuesCorp {
    n_busdayrule: string;
    n_last_reset_rate: string;
    n_rendimiento: string;
    s_bolsa_emi: string;
    n_isr: string;
    n_referencia_mercado: string;
    s_tv_ref_1: string;
    s_emisora_ref_1: string;
    s_serie_ref_1: string;
    s_tv_ref_2: string;
    s_emisora_ref_2: string;
    s_serie_ref_2: string;
    b_couponpro: string;
    s_pdf_verum: string;
    s_moneda: string;
    n_calif_dbrs_g: string;
    n_prima: string;
    n_tasa_mercado: string;
    d_fecha_primer_cupon: string;
    n_representante_comun: string;
    n_tipo_mercado: string;
    n_importe_amortizar: string;
    n_curve_index: string;
    d_fec_moody: string;
    s_evento_moody: string;
    n_calif_dbrs_n: string;
    d_fecha_subasta: string;
    n_valor_nominal_act: string;
    s_pdf_fitch: string;
    s_inscrito_bolsa: string;
    n_valor_nominal: string;
    n_re_anc: string;
    n_precio_mercado: string;
    s_emisor: string;
    n_calif_hr_n: string;
    n_tipo_instrumento: string;
    h_flujos_hist: FlujosCorp[];
    b_oferta_publica: string;
    d_fecha_amort_ant: string;
    n_calif_best_g: string;
    n_notches: string;
    n_convencion_dias: string;
    n_calc_fc: string;
    n_ac: string;
    n_precio_nuevo: string;
    n_calc_dias: string;
    n_sobretasa: string;
    b_mbs: string;
    b_esg: string;
    b_tipo_oferta: string;
    s_oddlastcoupon: string;
    n_calif_fitch_n: string;
    n_tipo_deuda: string;
    s_tipo_papel: string;
    s_evento_fitch: string;
    n_tipo_tasa: string;
    n_re_garantias: string;
    n_calificacion_n: string;
    b_intereses_hibrido: string;
    n_porc_vna_ant: string;
    n_familia_esg: string;
    b_rnv: string;
    b_fiduciario: string;
    n_dias_deter_tasa: string;
    b_canje: string;
    s_oddfirstcoupon: string;
    s_rep_comun: string;
    n_der_35_porcent: string;
    n_activo_no_circ: string;
    n_calificacion_a: string;
    n_titulos_iniciales: string;
    n_intereses_md: string;
    n_redondeo_tasa_cupon: string;
    s_agen_col: string;
    h_flujos_cupon: FlujosCorp[];
    n_intereses_24h: string;
    s_pdf_dbrs: string;
    h_flujos_val: FlujosCorp[];
    n_porc_vna: string;
    n_clasificacion_sectorial: string;
    n_familia_instrumento: string;
    n_pais: string;
    n_composicion_yield: string;
    s_evento_best: string;
    n_tasa_cupon: string;
    s_emisora: string;
    n_titulos_amortizar: string;
    n_importe_circulacion: string;
    n_moneda: string;
    n_calendario: string;
    s_pdf_moody: string;
    n_calif_hr_g: string;
    d_fec_ins: string;
    d_fecha_emision: string;
    n_tipo_calculo: string;
    s_pais: string;
    b_movimiento_tasa: string;
    n_calificadora_n: string;
    n_calif_verum_g: string;
    n_calif_moody_g: string;
    b_not_equal: string;
    s_com_val: string;
    d_fecha_vto: string;
    n_tipo_oferta: string;
    s_pdf_sp: string;
    n_factor: string;
    n_frecuencia_cupon: string;
    s_cusip: string;
    d_fec_venc: string;
    n_nomb_tasa: string;
    s_serie: string;
    d_fec_fitch: string;
    n_agente_colocador: string;
    n_periodo_cupon_v: string;
    d_fec_dbrs: string;
    n_theo_model: string;
    n_precio_mercado_24h: string;
    d_fec_verum: string;
    n_market_model: string;
    n_fixed_coupon_date: string;
    n_dia_corte_cupon: string;
    d_fecha_vto_estimada: string;
    n_titulos_circulacion: string;
    n_calif_sp_g: string;
    b_corto_plazo: string;
    s_pdf_best: string;
    n_calificadora_a: string;
    s_evento_sp: string;
    n_plazo: string;
    n_tasa_referencia: string;
    n_tasa_descuento: string;
    n_monto_emitido: string;
    b_callable: string;
    s_coupgenmthd: string;
    s_evento_dbrs: string;
    n_prima_amortizacion: string;
    s_evento_verum: string;
    b_pago_intereses: string;
    d_fecha_fin_cupon: string;
    d_fecha_canje: string;
    n_calif_sp_n: string;
    n_calif_best_n: string;
    d_lastregcou: string;
    n_crv_descuento: string;
    n_num_cupones: string;
    n_calif_moody_n: string;
    s_credit_spread_curve: string;
    n_calif_fitch_g: string;
    n_last_reset_rate_24h: string;
    d_fecha_ini_cupon: string;
    s_pdf_hr: string;
    n_der_cobro: string;
    s_evento_hr: string;
    n_emisor: string;
    n_reset_rule: string;
    n_monto_circulacion: string;
    n_tasa_cupon_24h: string;
    n_tipo_promedio: string;
    n_ac_rec: string;
    n_pais_riesgo: string;
    d_fec_sp: string;
    s_instrumento_canje: string;
    n_efectivo: string;
    s_isin: string;
    n_garantias: string;
    n_periodo_cupon: string;
    b_runscfphase: string;
    n_calif_verum_n: string;
    s_num_fideicomiso: string;
    s_tv: string;
    n_forma_cotizacion: string;
    n_precio_vector_dha: string;
    d_fec_best: string;
    d_fec_hr: string;
    n_status: string;
    n_fiduciario: string;
    b_quasi_sob: string;
    s_instrumento: string;
    n_sector: string;
    b_cambio_precio_mkt: string;
}

export const keysToKeepCorp: (keyof FvDeudaCorpInstrumentos)[] = [
    "s_tv",
    "s_emisora",
    "s_serie",
    "n_calificadora_a",
    "n_calificacion_a",
    "n_calificadora_n",
    "n_calificacion_n",
    "n_notches",
    "n_der_cobro",
    "n_der_35_porcent",
    "n_ac",
    "n_factor",
    "n_activo_no_circ",
    "n_garantias",
    "n_ac_rec",
    "n_precio_nuevo",
    "n_efectivo",
    "n_re_anc",
    "n_re_garantias",
    "n_porc_vna"
];

export const initialFormValuesCorp: FvDeudaCorpInstrumentos = {
    n_busdayrule: '',
    n_last_reset_rate: '',
    n_rendimiento: '',
    s_bolsa_emi: '',
    n_isr: '',
    s_tv_ref_1: '',
    s_emisora_ref_1: '',
    s_serie_ref_1: '',
    s_tv_ref_2: '',
    s_emisora_ref_2: '',
    s_serie_ref_2: '',
    n_referencia_mercado: '',
    b_couponpro: '',
    s_pdf_verum: '',
    s_moneda: '',
    n_calif_dbrs_g: '',
    n_prima: '',
    n_tasa_mercado: '',
    d_fecha_primer_cupon: '',
    n_representante_comun: '',
    n_tipo_mercado: '',
    n_importe_amortizar: '',
    n_curve_index: '',
    d_fec_moody: '',
    b_tipo_oferta: '',
    s_evento_moody: '',
    n_calif_dbrs_n: '',
    d_fecha_subasta: '',
    n_valor_nominal_act: '',
    s_pdf_fitch: '',
    s_inscrito_bolsa: '',
    n_valor_nominal: '',
    n_re_anc: '',
    n_precio_mercado: '',
    s_emisor: '',
    n_calif_hr_n: '',
    n_tipo_instrumento: '',
    h_flujos_hist: [],
    b_oferta_publica: '',
    d_fecha_amort_ant: '',
    n_calif_best_g: '',
    n_notches: '',
    n_convencion_dias: '',
    n_calc_fc: '',
    n_ac: '',
    n_precio_nuevo: '',
    n_calc_dias: '',
    n_sobretasa: '',
    b_mbs: '',
    b_esg: '',
    s_oddlastcoupon: '',
    n_calif_fitch_n: '',
    n_tipo_deuda: '',
    s_tipo_papel: '',
    s_evento_fitch: '',
    n_tipo_tasa: '',
    n_re_garantias: '',
    n_calificacion_n: '',
    b_intereses_hibrido: '',
    n_porc_vna_ant: '',
    n_familia_esg: '',
    b_rnv: '',
    b_fiduciario: '',
    n_dias_deter_tasa: '',
    b_canje: '',
    s_oddfirstcoupon: '',
    s_rep_comun: '',
    n_der_35_porcent: '',
    n_activo_no_circ: '',
    n_calificacion_a: '',
    n_titulos_iniciales: '',
    n_intereses_md: '',
    n_redondeo_tasa_cupon: '',
    s_agen_col: '',
    h_flujos_cupon: [],
    n_intereses_24h: '',
    s_pdf_dbrs: '',
    h_flujos_val: [],
    n_porc_vna: '',
    n_clasificacion_sectorial: '',
    n_familia_instrumento: '',
    n_pais: '',
    n_composicion_yield: '',
    s_evento_best: '',
    n_tasa_cupon: '',
    s_emisora: '',
    n_titulos_amortizar: '',
    n_importe_circulacion: '',
    n_moneda: '',
    n_calendario: '',
    s_pdf_moody: '',
    n_calif_hr_g: '',
    d_fec_ins: '',
    d_fecha_emision: '',
    n_tipo_calculo: '',
    s_pais: '',
    b_movimiento_tasa: '',
    n_calificadora_n: '',
    n_calif_verum_g: '',
    n_calif_moody_g: '',
    b_not_equal: '',
    s_com_val: '',
    d_fecha_vto: '',
    n_tipo_oferta: '',
    s_pdf_sp: '',
    n_factor: '',
    n_frecuencia_cupon: '',
    s_cusip: '',
    d_fec_venc: '',
    n_nomb_tasa: '',
    s_serie: '',
    d_fec_fitch: '',
    n_agente_colocador: '',
    n_periodo_cupon_v: '',
    d_fec_dbrs: '',
    n_theo_model: '',
    n_precio_mercado_24h: '',
    d_fec_verum: '',
    n_market_model: '',
    n_fixed_coupon_date: '',
    n_dia_corte_cupon: '',
    d_fecha_vto_estimada: '',
    n_titulos_circulacion: '',
    n_calif_sp_g: '',
    b_corto_plazo: '',
    s_pdf_best: '',
    n_calificadora_a: '',
    s_evento_sp: '',
    n_plazo: '',
    n_tasa_referencia: '',
    n_tasa_descuento: '',
    n_monto_emitido: '',
    b_callable: '',
    s_coupgenmthd: '',
    s_evento_dbrs: '',
    n_prima_amortizacion: '',
    s_evento_verum: '',
    b_pago_intereses: '',
    d_fecha_fin_cupon: '',
    d_fecha_canje: '',
    n_calif_sp_n: '',
    n_calif_best_n: '',
    d_lastregcou: '',
    n_crv_descuento: '',
    n_num_cupones: '',
    n_calif_moody_n: '',
    s_credit_spread_curve: '',
    n_calif_fitch_g: '',
    n_last_reset_rate_24h: '',
    d_fecha_ini_cupon: '',
    s_pdf_hr: '',
    n_der_cobro: '',
    s_evento_hr: '',
    n_emisor: '',
    n_reset_rule: '',
    n_monto_circulacion: '',
    n_tasa_cupon_24h: '',
    n_tipo_promedio: '',
    n_ac_rec: '',
    n_pais_riesgo: '',
    d_fec_sp: '',
    s_instrumento_canje: '',
    n_efectivo: '',
    s_isin: '',
    n_garantias: '',
    n_periodo_cupon: '',
    b_runscfphase: '',
    n_calif_verum_n: '',
    s_num_fideicomiso: '',
    s_tv: '',
    n_forma_cotizacion: '',
    n_precio_vector_dha: '',
    d_fec_best: '',
    d_fec_hr: '',
    n_status: '',
    n_fiduciario: '',
    b_quasi_sob: '',
    s_instrumento: '',
    n_sector: '',
    b_cambio_precio_mkt: ''
}

export interface IsFieldModifiedFvDdCorpIns {
    n_busdayrule: boolean;
    n_last_reset_rate: boolean;
    n_rendimiento: boolean;
    s_bolsa_emi: boolean;
    n_isr: boolean;
    n_referencia_mercado: boolean;
    b_couponpro: boolean;
    s_tv_ref_1: boolean;
    s_emisora_ref_1: boolean;
    s_serie_ref_1: boolean;
    s_tv_ref_2: boolean;
    s_emisora_ref_2: boolean;
    s_serie_ref_2: boolean;
    s_pdf_verum: boolean;
    s_moneda: boolean;
    n_calif_dbrs_g: boolean;
    n_prima: boolean;
    n_tasa_mercado: boolean;
    d_fecha_primer_cupon: boolean;
    n_representante_comun: boolean;
    n_tipo_mercado: boolean;
    n_importe_amortizar: boolean;
    n_curve_index: boolean;
    d_fec_moody: boolean;
    s_evento_moody: boolean;
    n_calif_dbrs_n: boolean;
    d_fecha_subasta: boolean;
    n_valor_nominal_act: boolean;
    s_pdf_fitch: boolean;
    s_inscrito_bolsa: boolean;
    n_valor_nominal: boolean;
    n_re_anc: boolean;
    n_precio_mercado: boolean;
    s_emisor: boolean;
    n_calif_hr_n: boolean;
    n_tipo_instrumento: boolean;
    h_flujos_hist: boolean;
    b_oferta_publica: boolean;
    d_fecha_amort_ant: boolean;
    n_notches: boolean;
    n_calif_best_g: boolean;
    n_convencion_dias: boolean;
    n_calc_fc: boolean;
    n_ac: boolean;
    n_precio_nuevo: boolean;
    n_calc_dias: boolean;
    n_sobretasa: boolean;
    b_mbs: boolean;
    b_esg: boolean;
    s_oddlastcoupon: boolean;
    n_calif_fitch_n: boolean;
    n_tipo_deuda: boolean;
    s_tipo_papel: boolean;
    s_evento_fitch: boolean;
    n_tipo_tasa: boolean;
    n_re_garantias: boolean;
    n_calificacion_n: boolean;
    b_intereses_hibrido: boolean;
    n_porc_vna_ant: boolean;
    n_familia_esg: boolean;
    b_rnv: boolean;
    b_fiduciario: boolean;
    n_dias_deter_tasa: boolean;
    b_canje: boolean;
    s_oddfirstcoupon: boolean;
    s_rep_comun: boolean;
    n_der_35_porcent: boolean;
    n_activo_no_circ: boolean;
    n_calificacion_a: boolean;
    n_titulos_iniciales: boolean;
    n_intereses_md: boolean;
    n_redondeo_tasa_cupon: boolean;
    s_agen_col: boolean;
    h_flujos_cupon: boolean;
    n_intereses_24h: boolean;
    s_pdf_dbrs: boolean;
    h_flujos_val: boolean;
    n_porc_vna: boolean;
    n_clasificacion_sectorial: boolean;
    n_familia_instrumento: boolean;
    n_pais: boolean;
    n_composicion_yield: boolean;
    s_evento_best: boolean;
    n_tasa_cupon: boolean;
    s_emisora: boolean;
    n_titulos_amortizar: boolean;
    n_importe_circulacion: boolean;
    n_moneda: boolean;
    n_calendario: boolean;
    s_pdf_moody: boolean;
    n_calif_hr_g: boolean;
    d_fec_ins: boolean;
    d_fecha_emision: boolean;
    n_tipo_calculo: boolean;
    s_pais: boolean;
    b_movimiento_tasa: boolean;
    n_calificadora_n: boolean;
    n_calif_verum_g: boolean;
    n_calif_moody_g: boolean;
    b_not_equal: boolean;
    s_com_val: boolean;
    d_fecha_vto: boolean;
    n_tipo_oferta: boolean;
    s_pdf_sp: boolean;
    n_factor: boolean;
    n_frecuencia_cupon: boolean;
    s_cusip: boolean;
    d_fec_venc: boolean;
    n_nomb_tasa: boolean;
    s_serie: boolean;
    d_fec_fitch: boolean;
    n_agente_colocador: boolean;
    n_periodo_cupon_v: boolean;
    d_fec_dbrs: boolean;
    n_theo_model: boolean;
    n_precio_mercado_24h: boolean;
    d_fec_verum: boolean;
    n_market_model: boolean;
    n_fixed_coupon_date: boolean;
    n_dia_corte_cupon: boolean;
    d_fecha_vto_estimada: boolean;
    n_titulos_circulacion: boolean;
    n_calif_sp_g: boolean;
    b_corto_plazo: boolean;
    s_pdf_best: boolean;
    n_calificadora_a: boolean;
    s_evento_sp: boolean;
    n_plazo: boolean;
    n_tasa_referencia: boolean;
    n_tasa_descuento: boolean;
    n_monto_emitido: boolean;
    b_callable: boolean;
    s_coupgenmthd: boolean;
    s_evento_dbrs: boolean;
    n_prima_amortizacion: boolean;
    s_evento_verum: boolean;
    b_pago_intereses: boolean;
    d_fecha_fin_cupon: boolean;
    d_fecha_canje: boolean;
    n_calif_sp_n: boolean;
    n_calif_best_n: boolean;
    d_lastregcou: boolean;
    n_crv_descuento: boolean;
    n_num_cupones: boolean;
    n_calif_moody_n: boolean;
    s_credit_spread_curve: boolean;
    n_calif_fitch_g: boolean;
    n_last_reset_rate_24h: boolean;
    d_fecha_ini_cupon: boolean;
    s_pdf_hr: boolean;
    n_der_cobro: boolean;
    s_evento_hr: boolean;
    n_emisor: boolean;
    n_reset_rule: boolean;
    n_monto_circulacion: boolean;
    n_tasa_cupon_24h: boolean;
    n_tipo_promedio: boolean;
    n_ac_rec: boolean;
    n_pais_riesgo: boolean;
    d_fec_sp: boolean;
    s_instrumento_canje: boolean;
    n_efectivo: boolean;
    s_isin: boolean;
    n_garantias: boolean;
    n_periodo_cupon: boolean;
    b_runscfphase: boolean;
    n_calif_verum_n: boolean;
    s_num_fideicomiso: boolean;
    s_tv: boolean;
    n_forma_cotizacion: boolean;
    n_precio_vector_dha: boolean;
    d_fec_best: boolean;
    d_fec_hr: boolean;
    n_status: boolean;
    n_fiduciario: boolean;
    b_quasi_sob: boolean;
    n_sector: boolean;
    s_instrumento: boolean;
    b_cambio_precio_mkt: boolean;
}

type InputOrSelect = HTMLInputElement | HTMLSelectElement | null;
type SelectOrNull = HTMLSelectElement | null;
type InputOrNull = HTMLInputElement | null;

export interface RequeridosCorp {
    [key: string]: React.MutableRefObject<InputOrSelect>;

    s_tv: React.MutableRefObject<InputOrSelect>;
    s_emisora: React.MutableRefObject<InputOrSelect>;
    s_serie: React.MutableRefObject<InputOrSelect>;
    n_tipo_instrumento: React.MutableRefObject<SelectOrNull>;
    n_familia_instrumento: React.MutableRefObject<SelectOrNull>;
    n_tipo_mercado: React.MutableRefObject<SelectOrNull>;
    n_pais: React.MutableRefObject<SelectOrNull>;
    n_moneda: React.MutableRefObject<SelectOrNull>;
    n_pais_riesgo: React.MutableRefObject<SelectOrNull>;
    n_emisor: React.MutableRefObject<SelectOrNull>;
    d_fecha_emision: React.MutableRefObject<InputOrNull>;
    n_plazo: React.MutableRefObject<InputOrNull>;
    d_fecha_vto: React.MutableRefObject<InputOrNull>;
    d_fecha_vto_estimada: React.MutableRefObject<InputOrNull>;
    n_valor_nominal: React.MutableRefObject<InputOrNull>;
    n_prima: React.MutableRefObject<InputOrNull>;
    b_oferta_publica: React.MutableRefObject<InputOrNull>;
    n_crv_descuento: React.MutableRefObject<SelectOrNull>;
    n_tasa_referencia: React.MutableRefObject<SelectOrNull>;
    n_tipo_tasa: React.MutableRefObject<SelectOrNull>;
    n_convencion_dias: React.MutableRefObject<SelectOrNull>;
    n_composicion_yield: React.MutableRefObject<SelectOrNull>;
    n_tipo_deuda: React.MutableRefObject<SelectOrNull>;
    n_calendario: React.MutableRefObject<SelectOrNull>;
    s_tv_ref_1: React.MutableRefObject<SelectOrNull>;
    s_emisora_ref_1: React.MutableRefObject<SelectOrNull>;
    s_serie_ref_1: React.MutableRefObject<SelectOrNull>;
    s_tv_ref_2: React.MutableRefObject<SelectOrNull>;
    s_emisora_ref_2: React.MutableRefObject<SelectOrNull>;
    s_serie_ref_2: React.MutableRefObject<SelectOrNull>;
    n_titulos_iniciales: React.MutableRefObject<InputOrNull>;
    n_titulos_circulacion: React.MutableRefObject<InputOrNull>;
    n_monto_emitido: React.MutableRefObject<InputOrNull>;
    n_monto_circulacion: React.MutableRefObject<InputOrNull>;
    n_frecuencia_cupon: React.MutableRefObject<SelectOrNull>;
    n_tipo_promedio: React.MutableRefObject<SelectOrNull>;
    n_calc_dias: React.MutableRefObject<SelectOrNull>;
    n_dias_deter_tasa: React.MutableRefObject<InputOrNull>;
    n_tasa_cupon: React.MutableRefObject<InputOrNull>;
    n_tasa_cupon_24h: React.MutableRefObject<InputOrNull>;
    n_valor_nominal_act: React.MutableRefObject<InputOrNull>;
    d_fecha_ini_cupon: React.MutableRefObject<InputOrNull>;
    d_fecha_fin_cupon: React.MutableRefObject<InputOrNull>;
    d_fecha_subasta: React.MutableRefObject<InputOrNull>;
    n_num_cupones: React.MutableRefObject<InputOrNull>;
    n_periodo_cupon: React.MutableRefObject<InputOrNull>;
    n_periodo_cupon_v: React.MutableRefObject<InputOrNull>;
    s_inscrito_bolsa: React.MutableRefObject<SelectOrNull>;
}
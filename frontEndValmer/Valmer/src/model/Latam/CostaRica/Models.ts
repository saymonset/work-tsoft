import {Catalogo} from "../../deuda";
import React from "react";

export type CostaRicaHeaderProps = {
    selectedEmisor: string
    selectedNemo: string
    selectedSerie: string
    emisor: string[]
    nemoInstrumento: string[]
    serie: string[]
    checkboxValue: number
    loadingSave: boolean
    loadingNemoInst: boolean
    loadingSerie: boolean
    setSelectedEmisor: (value: string) => void;
    setSelectedNemo: (value: string) => void;
    setSelectedSerie: (value: string) => void;
    activeNuevo: boolean;
    handleNuevo: () => void;
    handleCancel: () => void;
    handleErase: (e: React.MouseEvent<HTMLButtonElement>) => void;
    handleEmisor: (e: React.ChangeEvent<HTMLSelectElement>) => Promise<void>;
    handleNemo: (e: React.ChangeEvent<HTMLSelectElement>) => Promise<void>;
    handleSerie: (e: React.ChangeEvent<HTMLSelectElement>) => Promise<void>;
    handleCheckboxChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
    handleSave: (e: React.MouseEvent<HTMLButtonElement>) => void;
};


export type CostaRicaFormProps = {
    catalog: Catalogo[];
    consultaData: RespConsultaDataCR;
    mergeInstrumentos: string;
    loadingConsultaInfo: boolean;
    handleChange: (event: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => void;
};


export interface RespConsultaDataCR {
    status: number;
    body: BodyCr;
}

export interface BodyCr {
    info_bd: InfoBd;
    info_rw: InfoRw;
}

export interface InfoBd {
    n_last_reset_rate: string;
    n_tipo_diversificacion: string;
    n_liquidos: string;
    n_scr: string;
    d_fecha_vencimiento: string;
    n_tipo_emision: string;
    d_fecha_moodys_i: string;
    n_curve_index: string;
    s_req_premio: string;
    n_cve_custodia: string;
    s_serie: string;
    n_fitch_i: string;
    n_monto_colocado: string;
    n_market_model: string;
    s_nemotecnico: string;
    n_scr_riesgomerc: string;
    n_tasa_minima: string;
    n_fixed_coupon_date: string;
    n_tasa_cupon_techo: string;
    n_tipo_fondo: string;
    n_base_calculo: string;
    d_fec_ini_cupon: string;
    n_tasa_maxima: string;
    n_valor_nominal: string;
    n_plazo: string;
    s_emisor: string;
    n_periodicidad: string;
    n_sp_i: string;
    n_tipo_merc: string;
    n_odd_first_coupon: string;
    s_fitch_riesgomerc: string;
    n_tipo_titulo: string;
    n_tipo_privilegio: string;
    s_formula: string;
    d_fecha_sp_i: string;
    n_sobretasa: string;
    d_last_reg_coup_date: string;
    n_tipo_valor: string;
    n_tasa_cupon_piso: string;
    n_tipo_tasa: string;
    n_calendar_adjust: string;
    n_emisor: string;
    n_odd_last_coupon: string;
    n_reset_rule: string;
    n_monto_ventanilla: string;
    n_discount_curve: string;
    s_amortizable: string;
    n_pcr: string;
    s_entrega_previa: string;
    n_monto_aprobado: string;
    d_fecha_inclusion: string;
    n_curva_prima: string;
    d_fecha_pago_interes: string;
    n_tipo_accion: string;
    n_edo_instrumento: string;
    n_curva_valuacion: string;
    d_fec_pri_dia_val: string;
    s_colateral: string;
    s_isin: string;
    n_fitch: string;
    n_clasificacion_riesgo: string;
    n_modo_neg: string;
    n_moodys_i: string;
    s_coupon_prorated: string;
    n_tasa_bruta: string;
    n_fac_liqui: string;
    n_tasa_neta: string;
    n_factor_impuesto: string;
    n_tasa_fluctuante: string;
    d_fecha_fitch_i: string;
    n_forma_cotizacion: string;
    n_theoretical_model: string;
    n_clasif_general: string;
    n_pais: string;
    n_spot_price: string;
    n_business_day_rule: string;
    n_subfamilia: string;
    s_pcr_riesgomerc: string;
    n_premio: string;
    n_moneda: string;
    n_coupon_gm: string;
    s_nemo_instr: string;
    d_fecha_emision: string;
}

export interface InfoRw {
    [key: string]: {
        [key: string]: Action;
    };
}

export interface Action {
    "Common Stock": string;
    "Calendar Adjust": string;
    "Spot Price": string;
    "Currency": string;
    "*Theoretical Model": string;
    "ENC": string;
    "Name": string;
}
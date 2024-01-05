import { InputOrNull, InputOrSelect, SelectOrNull } from "../../Models";
import {Catalogo} from "../../deuda";
import React from "react";

export type PanamaHeaderProps = {
    activeNuevo: boolean;
    selectedNemoTecnico: string;
    loadingConsultaData: boolean;
    loadingSave: boolean;
    consultaData: RespConsultaDataPanam;
    catalog: Catalogo[];
    nemoTecnico: string[];
    checkboxValues: { inactivas: number; amortAnticipadas: number; };
    isFieldRequiredLatPanama: IsFieldRequiredLatPanama;
    refReqLatPanama: RefReqLatPanama;
    handleNewNemo: (e: React.ChangeEvent<HTMLInputElement>) => void;
    handleNuevo: () => void;
    handleCancel: () => void;
    handleSelectNemo: (e: React.ChangeEvent<HTMLSelectElement>) => void;
    handleCheckboxChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
    handleChange: (event: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => void;
    handleSave: (event: React.MouseEvent<HTMLButtonElement>) => void;
};

export type PanamaFormProps = {
    catalog: Catalogo[];
    consultaData: RespConsultaDataPanam;
    selectedNemoTecnico: string;
    isFieldRequiredLatPanama: IsFieldRequiredLatPanama;
    refReqLatPanama: RefReqLatPanama;
    handleChange: (event: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => void;
};

export interface RespConsultaDataPanam {
    status: number;
    body: {
        info_bd: {
            n_coupon_gen_met: string;
            d_last_reg_coup_date: string;
            n_moody: string;
            d_fitch: string;
            n_tasa: string;
            n_curva_desc: string;
            n_sector: string;
            d_fecha_vencimiento: string;
            n_emisor: string;
            n_frecuencia_cupon: string;
            n_odd_last_coupon: string;
            n_tipo_mercado: string;
            n_monto_colocado: string;
            n_theo_model: string;
            d_fecha_vto_cupon: string;
            s_nemotecnico: string;
            n_fixed_coupon_date: string;
            n_tipo_instrumento_edit: string;
            n_crv_index: string;
            n_soy: string;
            s_isin: string;
            n_fitch: string;
            d_fecha_liquidacion: string;
            n_base_calculo: string;
            n_clase: string;
            n_valor_nominal: string;
            n_plazo: string;
            d_sp: string;
            d_fecha_inicio_cupon: string;
            n_tipo_instrumento: string;
            n_business_day_rule: string;
            n_pais: string;
            n_odd_first_coupon: string;
            n_precio_colocacion: string;
            d_fecha_amort_ant: string;
            n_sp: string;
            d_moody: string;
            n_status: string;
            n_form_cotizacion: string;
            d_fecha_ingreso_titulo: string;
            n_moneda: string;
            n_precio: string;
            n_sobretasa: string;
            d_fecha_emision: string;
        };
        info_rw: {
            [key: string]: {
                [key: string]: {
                    "Coupon Generation Method"?: string;
                    "Spot Price"?: string;
                    "Coupon Prorated"?: string;
                    "Term"?: string;
                    "Spread Over Yield"?: string;
                    "Maturity Date"?: string;
                    "Coupon Rate"?: string;
                    "Currency"?: string;
                    "Issue Date"?: string;
                    "Business Day Rule"?: string;
                    "ENC"?: string;
                    "Theorical Model"?: string;
                    "Notional"?: string;
                    "State Procedure"?: string;
                    "Market Model"?: string;
                    "Discount Courve"?: string;
                };
            };
        };
    };
}

export interface ResponseCamposHistorico {
    status: number;
    body: CamposHistorico[];
}

export interface CamposHistorico {
    [key: string]: string;
}

export interface RespHistoricoDownloadCsv {
    status: number;
    message?: string;
    body: HistoricoDownloadCsv;
}

export interface HistoricoDownloadCsv {
    nombre: string;
    extension: string;
    contenido: string;
    contentType: string;
    nombreCompleto: string;
}

export interface RespHistoricoConsultaTabla {
    status: number;
    body: HistoricoConsultaTabla[];
}

export interface HistoricoConsultaTabla {
    s_nemotecnico: string;
    d_fecha: string;
    s_base: string;
    s_cierre_libro: string;
    s_clase: string;
    n_convexidad: number;
    s_curva: string;
    n_dias_x_venc: number;
    n_duracion: number;
    n_dur_mack: number;
    s_emisor_corto: string;
    d_fec_emision: string;
    d_fecha_ini_cupon: string;
    d_fecha_fin_cupon: string;
    s_fitch: string;
    d_fitch: string;
    s_frecuencia: string;
    n_interes: number;
    s_isin: string;
    s_moneda: string;
    n_monto: number;
    s_moody: string;
    d_moody: string;
    s_origen: string;
    s_pais: string;
    n_plazo: string;
    n_porc_precio: number;
    n_precio_limpio: number;
    n_precio_sucio: number;
    s_emisor: string;
    n_rendimiento: number;
    s_sp: string;
    s_sector: string;
    n_sobretasa: number;
    d_sp: string;
    n_spread: string;
    n_tasa_c: number;
    n_tasa_desc: string;
    s_tipo_instrumento: string;
    s_tipo_mercado: string;
    n_valor_nominal: number;
    d_fecha_vencimiento: string;
}

export interface formValuesLatPanama {
    [key: string]: string;
    n_coupon_gen_met: string;
    d_last_reg_coup_date: string;
    n_moody: string;
    d_fitch: string;
    n_tasa: string;
    n_curva_desc: string;
    n_sector: string;
    d_fecha_vencimiento: string;
    n_emisor: string;
    n_frecuencia_cupon: string;
    n_odd_last_coupon: string;
    n_tipo_mercado: string;
    n_monto_colocado: string;
    n_theo_model: string;
    d_fecha_vto_cupon: string;
    s_nemotecnico: string;
    n_fixed_coupon_date: string;
    n_tipo_instrumento_edit: string;
    n_crv_index: string;
    n_soy: string;
    s_isin: string;
    n_fitch: string;
    d_fecha_liquidacion: string;
    n_base_calculo: string;
    n_clase: string;
    n_valor_nominal: string;
    n_plazo: string;
    d_sp: string;
    d_fecha_inicio_cupon: string;
    n_tipo_instrumento: string;
    n_business_day_rule: string;
    n_pais: string;
    n_odd_first_coupon: string;
    n_precio_colocacion: string;
    d_fecha_amort_ant: string;
    n_sp: string;
    d_moody: string;
    n_status: string;
    n_form_cotizacion: string;
    d_fecha_ingreso_titulo: string;
    n_moneda: string;
    n_precio: string;
    n_sobretasa: string;
    d_fecha_emision: string;
}

export interface IsFieldRequiredLatPanama {
    [key: string]: boolean;
    s_nemotecnico: boolean;
    n_tipo_instrumento: boolean;
    n_tipo_instrumento_edit: boolean;
    d_fecha_emision: boolean;
    n_frecuencia_cupon: boolean;
    d_fecha_liquidacion: boolean;
    n_tipo_mercado: boolean;
    d_fecha_vencimiento: boolean;
    n_clase: boolean;
    d_fecha_inicio_cupon: boolean;
    n_sector: boolean;
    d_fecha_vto_cupon: boolean;
    n_curva_desc: boolean;
    n_plazo: boolean;
    n_moneda: boolean;
    n_monto_colocado: boolean;
    n_theo_model: boolean;
    n_valor_nominal: boolean;
    n_tasa: boolean;
    n_base_calculo: boolean;
    n_sobretasa: boolean;
    n_status: boolean;
    s_isin: boolean;
    n_precio: boolean;
    n_form_cotizacion: boolean;
    n_coupon_gen_met: boolean;
    n_odd_last_coupon: boolean;
    n_fixed_coupon_date: boolean;
    n_odd_first_coupon: boolean;
    n_pais: boolean;
    n_emisor: boolean;
    d_fecha_ingreso_titulo: boolean;
    n_crv_index: boolean;
}

export const fieldToValidateLatamPanama = [
    { name: "s_nemotecnico", defaultValue: "default" },
    { name: "n_tipo_instrumento", defaultValue: "default" },
    { name: "n_tipo_instrumento_edit", defaultValue: "default" },
    { name: "d_fecha_emision", defaultValue: "default" },
    { name: "n_frecuencia_cupon", defaultValue: "default" },
    { name: "d_fecha_liquidacion", defaultValue: "default" },
    { name: "n_tipo_mercado", defaultValue: "default" },
    { name: "d_fecha_vencimiento", defaultValue: "default" },
    { name: "n_clase", defaultValue: "default" },
    { name: "d_fecha_inicio_cupon", defaultValue: "default" },
    { name: "n_sector", defaultValue: "default" },
    { name: "d_fecha_vto_cupon", defaultValue: "default" },
    { name: "n_curva_desc", defaultValue: "default" },
    { name: "n_plazo", defaultValue: "default" },
    { name: "n_moneda", defaultValue: "default" },
    { name: "n_monto_colocado", defaultValue: "default" },
    { name: "n_theo_model", defaultValue: "default" },
    { name: "n_valor_nominal", defaultValue: "default" },
    { name: "n_tasa", defaultValue: "default" },
    { name: "n_base_calculo", defaultValue: "default" },
    { name: "n_sobretasa", defaultValue: "default" },
    { name: "n_status", defaultValue: "default" },
    { name: "s_isin", defaultValue: "default" },
    { name: "n_precio", defaultValue: "default" },
    { name: "n_form_cotizacion", defaultValue: "default" },
    { name: "n_coupon_gen_met", defaultValue: "default" },
    { name: "n_odd_last_coupon", defaultValue: "default" },
    { name: "n_fixed_coupon_date", defaultValue: "default" },
    { name: "n_odd_first_coupon", defaultValue: "default" },
    { name: "n_pais", defaultValue: "default" },
    { name: "n_emisor", defaultValue: "default" },
    { name: "d_fecha_ingreso_titulo", defaultValue: "default" },
    { name: "n_crv_index", defaultValue: "default" }
]

export interface RefReqLatPanama {
    [key: string]: React.MutableRefObject<InputOrSelect>;
    n_tipo_instrumento: React.MutableRefObject<SelectOrNull>;
    n_tipo_instrumento_edit: React.MutableRefObject<SelectOrNull>;
    d_fecha_emision: React.MutableRefObject<InputOrNull>;
    n_frecuencia_cupon: React.MutableRefObject<SelectOrNull>;
    d_fecha_liquidacion: React.MutableRefObject<InputOrNull>;
    n_tipo_mercado: React.MutableRefObject<SelectOrNull>;
    d_fecha_vencimiento: React.MutableRefObject<InputOrNull>;
    n_clase: React.MutableRefObject<SelectOrNull>;
    d_fecha_inicio_cupon: React.MutableRefObject<InputOrNull>;
    n_sector: React.MutableRefObject<SelectOrNull>;
    d_fecha_vto_cupon: React.MutableRefObject<InputOrNull>;
    n_curva_desc: React.MutableRefObject<SelectOrNull>;
    n_plazo: React.MutableRefObject<InputOrNull>;
    n_moneda: React.MutableRefObject<SelectOrNull>;
    n_monto_colocado: React.MutableRefObject<InputOrNull>;
    n_theo_model: React.MutableRefObject<SelectOrNull>;
    n_valor_nominal: React.MutableRefObject<InputOrNull>;
    n_tasa: React.MutableRefObject<InputOrNull>;
    n_base_calculo: React.MutableRefObject<SelectOrNull>;
    n_sobretasa: React.MutableRefObject<InputOrNull>;
    n_status: React.MutableRefObject<SelectOrNull>;
    s_isin: React.MutableRefObject<InputOrNull>;
    n_precio: React.MutableRefObject<InputOrNull>;
    n_form_cotizacion: React.MutableRefObject<SelectOrNull>;
    n_coupon_gen_met: React.MutableRefObject<SelectOrNull>;
    n_odd_last_coupon: React.MutableRefObject<SelectOrNull>;
    n_fixed_coupon_date: React.MutableRefObject<InputOrNull>;
    n_odd_first_coupon: React.MutableRefObject<SelectOrNull>;
    n_pais: React.MutableRefObject<SelectOrNull>;
    n_emisor: React.MutableRefObject<SelectOrNull>;
    d_fecha_ingreso_titulo: React.MutableRefObject<InputOrNull>;
    n_crv_index: React.MutableRefObject<SelectOrNull>;
}
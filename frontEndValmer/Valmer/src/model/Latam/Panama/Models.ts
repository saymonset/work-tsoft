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
    setSelectedNemoTecnico: (value: string) => void;
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
}
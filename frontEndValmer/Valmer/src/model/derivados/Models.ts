import React from "react";
import {Catalogo} from "../deuda";

export type InstrumentosDerivadosType = FvDerivadosListas | FvDefDerivados;

export interface ModalVencProps {
    isOpen: boolean
    tv: string
    emisora: string
    serieOp: string[]
    tipoTv: string
    triggerVenc: boolean
    setTriggerVenc: React.Dispatch<React.SetStateAction<boolean>>
    onClose: () => void
}

export interface ModalPegaVtosProps {
    isOpen: boolean,
    tipoTv: string,
    onClose: () => void
}

export interface RespDataUnderlying {
    status: number;
    body: {
        s_underlying: string[];
        s_underlying_sp: string[];
        s_underlying_cu: string[];
        s_underlying_sp_cu: string[];
    };
}

export interface RespDataDcsCurveUnd {
    status: number;
    body: string[];
}

export type ListItemType = {
    property: string;
    data: {
        PRECIO_AYER: string;
        MONTO_EXPOS_AYER: string;
        MONTO_EXPOS_HOY: string;
        PRECIO_HOY: string;
        FEC_VTO: string;
    };
};
export interface RespDerivados {
    status: number;
    body: {
        n_busdayrule?: string;
        n_crv_descuento: string;
        n_valor_nominal: string;
        d_fecha_vto: string;
        b_couponpro?: string;
        s_coupgenmthd?: string;
        s_tv: string;
        n_tipo_tasa: string;
        n_tipo_instrumento: string;
        n_composicion_yield: string;
        s_serie: string;
        n_convencion_dias: string;
        n_tasa_cupon?: string;
        n_theo_model: string;
        s_emisora: string;
        n_fam_instrumento: string;
        n_market_model: string;
        n_moneda: string;
        n_calendario: string;
        s_instrumento: string;
        s_dsc_curve_und?: string;
        s_foreign_curve?: string;
        n_tipo_mercado?: string;
        n_pais?: string;
        s_volatilidad?: string;
        s_consec?: string;
        consec_fecha_vto?: string;
        consec_precio_hoy?: string;
        consec_precio_ayer?: string;
        consec_monto_ex_h?: string;
        consec_monto_ex_a?: string;
        consec_under_fin_ent?: string;
        consec_compon_weights?: string;
        n_info?: string;
        s_user?: string;
        s_consecutivo?: string;
        LIST?: Array<{
            property: string;
            data: {
                PRECIO_AYER: string;
                MONTO_EXPOS_AYER: string;
                MONTO_EXPOS_HOY: string;
                PRECIO_HOY: string;
                FEC_VTO: string;
            };
        }>;
    };
}

export interface RespDerivadosDef {
    status: 200,
    body: {
      n_crv_descuento: string,
      b_tv_vd: string,
      n_crv_descuento_cu_sofr_e: string,
      s_tipo_subyacente: string,
      n_crv_index_cu_sofr_e: string,
      s_underlying_sp: string,
      s_underlying_sp_cu: string,
      n_crv_index_cu: string,
      s_fuente: string,
      n_tipo_tasa: string,
      s_ric: string,
      s_fb_underlyings: string,
      n_crv_foranea: string,
      b_tv_emi_vd: string,
      n_theo_model: string,
      b_rw: string,
      n_factor_filtro_strike: string,
      n_crv_foranea_cu: string,
      n_crv_index_cu_sofr: string,
      n_crv_descuento_cu_e: string,
      n_supervol: string,
      n_crv_index: string,
      n_crv_foranea_cu_sofr: string,
      n_crv_referencia: string,
      n_crv_foranea_cu_sofr_e: string,
      s_underlying: string,
      n_tipo_subyacente: string,
      s_future_quote_units: string,
      n_tipo_calculo_r: string,
      n_comp_yield: string,
      s_tv: string,
      n_crv_descuento_e: string,
      n_crv_foranea_e: string,
      n_crv_index_e: string,
      n_tipo_instrumento: string,
      n_component_weights: string,
      n_crv_descuento_cu: string,
      n_tipo_calculo_e: string,
      n_crv_foranea_cu_e: string,
      b_filtro_strike: string,
      n_crv_index_cu_e: string,
      n_swp_rate: string,
      n_mkt_model: string,
      s_emisora: string,
      n_tam_contrato: string,
      n_tipo_instrumento_e: string,
      n_moneda: string,
      n_moneda_e: string,
      s_dcs_curve_und: string,
      n_setdayrule: string,
      n_crv_descuento_cu_sofr: string,
      s_underlying_cu: string,
      s_instrumento: string,
      s_user?: string
    }
  }

  export interface TvEmiDefProps {
    isNew: boolean
    loadingConsultaData: boolean
    loadingEmisoras: boolean
    selectedTv: string
    selectedEmisora: string
    tv: string[]
    emisora: string[]
    consultaData: RespDerivadosDef
    requiredTv: boolean
    requiredEmisora: boolean
    requeridosDefDer: RequeridosDefDerivados
    handleTv: (e: any) => void
    handleEmisora: (e: any) => void
    handleChange: (e: any) => void
  }

  export interface RespDataTableDef {
    status: number;
    body: DataTableDef[];
  }

  export interface DataTableDef {
    s_clave: string,
    n_tam_contrato: string,
    n_moneda: string,
    n_tipo_instrumento: string,
    n_crv_descuento: string,
    n_crv_foranea: string,
    n_crv_referencia: string,
    n_theo_model: string,
    n_mkt_model: string,
    s_underlying: string,
    s_tipo_valor: string,
    s_emisora: string,
    n_tipo_tasa: string,
    n_comp_yield: string,
    s_dcs_curve_und: string,
    n_setdayrule: string,
    n_superficie_volatilidad: string,
    s_fb_underlyings: string,
    n_component_weights: string,
    n_factor_filtro_strike: string,
    n_swp_rate: string,
    b_tv_emi_vd: string,
    b_tv_vd: string,
    s_fuente: string,
    b_rw: string,
    s_future_quote_units: string
  }

  export interface FormValuesVenc {
    sTv: string
    sEmisora: string
    sSerie: string
    date_picker0: string
    date_picker1?: string
  }

  export interface TableDefVenc {
    s_instrumentoq: string,
    s_fechaq?: string,
    s_fechaq0?: string,
    s_fechaq1?: string
  }

export interface FormDefDerivadosProps {
    isNew: boolean
    isTabla: boolean
    isModalVenc: boolean
    triggerTablaVenc: boolean
    consultaDataDerDef: RespDerivadosDef
    selectedTv: string
    selectedEmisora: string
    requiredTv: boolean
    requiredEmisora: boolean
    fieldRequiredDefDerivados: IsFieldModifiedFvDerivados
    requeridosDefDer: RequeridosDefDerivados
    setSelectedTv: React.Dispatch<React.SetStateAction<string>>
    setSelectedEmisora: React.Dispatch<React.SetStateAction<string>>
    setConsultaDataDerDef: React.Dispatch<React.SetStateAction<RespDerivadosDef>>
    setIsVencimiento: React.Dispatch<React.SetStateAction<boolean>>
    setTriggerTablaVenc: React.Dispatch<React.SetStateAction<boolean>>
    closeModalVenc: () => void
}

export interface DerDefHandleDataProps {
    consultaDataDerDef: RespDerivadosDef
    selectedTv: string
    selectedEmisora: string
    fieldRequiredDefDerivados: IsFieldModifiedFvDerivados
    setSelectedTv: React.Dispatch<React.SetStateAction<string>>
    setSelectedEmisora: React.Dispatch<React.SetStateAction<string>>
    setConsultaDataDerDef: React.Dispatch<React.SetStateAction<RespDerivadosDef>>
    setIsVencimiento: React.Dispatch<React.SetStateAction<boolean>>
}

export interface TvEmiHookProps {
    selectedTv: string
    selectedEmisora: string
    setSelectedTv: React.Dispatch<React.SetStateAction<string>>
    setSelectedEmisora: React.Dispatch<React.SetStateAction<string>>
    setConsultaDataDerDef: React.Dispatch<React.SetStateAction<RespDerivadosDef>>;
    setIsVencimiento: React.Dispatch<React.SetStateAction<boolean>>
}

export interface FormColateralFFProps {
    consultaDataDerDef: RespDerivadosDef
    catalog: Catalogo[]
    handleChange: (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => void
}

export interface FormValuesDerivados {
    [key: string]: string | undefined | any;
}
export interface FvDerivadosListas extends FormValuesDerivados{
    n_busdayrule?: string;
    n_crv_descuento: string;
    n_valor_nominal: string;
    d_fecha_vto: string;
    b_couponpro?: string;
    s_coupgenmthd?: string;
    s_tv: string;
    n_tipo_tasa: string;
    n_tipo_instrumento: string;
    n_composicion_yield: string;
    s_serie: string;
    n_convencion_dias: string;
    n_tasa_cupon?: string;
    n_theo_model: string;
    s_emisora: string;
    n_fam_instrumento: string;
    n_market_model: string;
    n_moneda: string;
    n_calendario: string;
    s_instrumento: string;
    s_dsc_curve_und?: string;
    s_foreign_curve?: string;
    n_tipo_mercado?: string;
    n_pais?: string;
    s_volatilidad?: string;
    s_consec?: string;
    consec_fecha_vto?: string;
    consec_precio_hoy?: string;
    consec_precio_ayer?: string;
    consec_monto_ex_h?: string;
    consec_monto_ex_a?: string;
    consec_under_fin_ent?: string;
    consec_compon_weights?: string;
    n_info?: string;
    s_user?: string;
    s_consecutivo?: string;
    LIST?: Array<{
        property: string;
        data: {
            PRECIO_AYER: string;
            MONTO_EXPOS_AYER: string;
            MONTO_EXPOS_HOY: string;
            PRECIO_HOY: string;
            FEC_VTO: string;
        };
    }>;
};

export interface FvDefDerivados extends FormValuesDerivados {
    n_crv_descuento: string,
    b_tv_vd: string,
    n_crv_descuento_cu_sofr_e: string,
    s_tipo_subyacente: string,
    n_crv_index_cu_sofr_e: string,
    s_underlying_sp: string,
    s_underlying_sp_cu: string,
    n_crv_index_cu: string,
    s_fuente: string,
    n_tipo_tasa: string,
    s_ric: string,
    s_fb_underlyings: string,
    n_crv_foranea: string,
    b_tv_emi_vd: string,
    n_theo_model: string,
    b_rw: string,
    n_factor_filtro_strike: string,
    n_crv_foranea_cu: string,
    n_crv_index_cu_sofr: string,
    n_crv_descuento_cu_e: string,
    n_supervol: string,
    n_crv_index: string,
    n_crv_foranea_cu_sofr: string,
    n_crv_referencia: string,
    n_crv_foranea_cu_sofr_e: string,
    s_underlying: string,
    n_tipo_subyacente: string,
    s_future_quote_units: string,
    n_tipo_calculo_r: string,
    n_comp_yield: string,
    s_tv: string,
    n_crv_descuento_e: string,
    n_crv_foranea_e: string,
    n_crv_index_e: string,
    n_tipo_instrumento: string,
    n_component_weights: string,
    n_crv_descuento_cu: string,
    n_tipo_calculo_e: string,
    n_crv_foranea_cu_e: string,
    b_filtro_strike: string,
    n_crv_index_cu_e: string,
    n_swp_rate: string,
    n_mkt_model: string,
    s_emisora: string,
    n_tam_contrato: string,
    n_tipo_instrumento_e: string,
    n_moneda: string,
    n_moneda_e: string,
    s_dcs_curve_und: string,
    n_setdayrule: string,
    n_crv_descuento_cu_sofr: string,
    s_underlying_cu: string,
    s_instrumento: string,
    s_user?: string
}

export interface IsFieldModifiedFvDerivados {
    [key: string]: boolean;
    n_crv_descuento: boolean,
    b_tv_vd: boolean,
    n_crv_descuento_cu_sofr_e: boolean,
    s_tipo_subyacente: boolean,
    n_crv_index_cu_sofr_e: boolean,
    s_underlying_sp: boolean,
    s_underlying_sp_cu: boolean,
    n_crv_index_cu: boolean,
    s_fuente: boolean,
    n_tipo_tasa: boolean,
    n_tasa_cupon: boolean,
    s_ric: boolean,
    s_fb_underlyings: boolean,
    n_crv_foranea: boolean,
    b_tv_emi_vd: boolean,
    n_theo_model: boolean,
    b_rw: boolean,
    n_composicion_yield: boolean,
    n_factor_filtro_strike: boolean,
    n_crv_foranea_cu: boolean,
    n_crv_index_cu_sofr: boolean,
    n_crv_descuento_cu_e: boolean,
    n_supervol: boolean,
    n_crv_index: boolean,
    n_crv_foranea_cu_sofr: boolean,
    n_crv_referencia: boolean,
    n_crv_foranea_cu_sofr_e: boolean,
    s_underlying: boolean,
    n_tipo_subyacente: boolean,
    s_future_quote_units: boolean,
    n_tipo_calculo_r: boolean,
    n_convencion_dias: boolean,
    n_comp_yield: boolean,
    s_tv: boolean,
    s_serie: boolean;
    n_crv_descuento_e: boolean,
    n_crv_foranea_e: boolean,
    n_crv_index_e: boolean,
    n_tipo_instrumento: boolean,
    n_component_weights: boolean,
    n_crv_descuento_cu: boolean,
    n_tipo_calculo_e: boolean,
    n_crv_foranea_cu_e: boolean,
    b_filtro_strike: boolean,
    n_crv_index_cu_e: boolean,
    n_swp_rate: boolean,
    n_mkt_model: boolean,
    s_emisora: boolean,
    n_tam_contrato: boolean,
    n_calendario: boolean,
    n_tipo_instrumento_e: boolean,
    n_moneda: boolean,
    n_moneda_e: boolean,
    s_dcs_curve_und: boolean,
    n_setdayrule: boolean,
    n_crv_descuento_cu_sofr: boolean,
    s_underlying_cu: boolean,
    s_instrumento: boolean,
    s_user: boolean
}

type InputOrSelect = HTMLInputElement | HTMLSelectElement | null;
type SelectOrNull = HTMLSelectElement | null;
type InputOrNull = HTMLInputElement | null;

export interface RequeridosDerivados {
    [key: string]: React.MutableRefObject<InputOrSelect>;
    s_tv: React.MutableRefObject<SelectOrNull>;
    s_emisora: React.MutableRefObject<SelectOrNull>;
    s_serie: React.MutableRefObject<SelectOrNull>;
    n_tipo_instrumento: React.MutableRefObject<SelectOrNull>;
    n_fam_instrumento: React.MutableRefObject<SelectOrNull>;
    n_tipo_mercado: React.MutableRefObject<SelectOrNull>;
    n_pais: React.MutableRefObject<SelectOrNull>;
    n_moneda: React.MutableRefObject<SelectOrNull>;
    n_valor_nominal: React.MutableRefObject<InputOrNull>;
    n_crv_descuento: React.MutableRefObject<SelectOrNull>;
    n_tipo_tasa: React.MutableRefObject<SelectOrNull>;
    n_convencion_dias: React.MutableRefObject<SelectOrNull>;
    n_composicion_yield: React.MutableRefObject<SelectOrNull>;
    n_calendario: React.MutableRefObject<SelectOrNull>;
    n_tasa_cupon: React.MutableRefObject<InputOrNull>;
}

export interface RequeridosDefDerivados {
    [key: string]: React.MutableRefObject<InputOrSelect>;
    s_tv: React.MutableRefObject<InputOrSelect>;
    s_emisora: React.MutableRefObject<InputOrSelect>;
    n_moneda: React.MutableRefObject<SelectOrNull>;
}

export const fieldToValidateDerivados = [
    { name: 'n_tipo_instrumento', defaultValue: 'default'},
    { name: 'n_tipo_mercado', defaultValue: 'default'},
    { name: 'n_pais', defaultValue: 'default'},
    { name: 'n_fam_instrumento', defaultValue: 'default'},
    { name: 'n_moneda', defaultValue: 'default'},
    { name: 'n_crv_descuento', defaultValue: 'default'},
    { name: 'n_valor_nominal', defaultValue: 'default'},
    { name: 'n_composicion_yield', defaultValue: 'default'},
    { name: 'n_tipo_tasa', defaultValue: 'default'},
    { name: 'n_convencion_dias', defaultValue: 'default'},
    { name: 'n_calendario', defaultValue: 'default'}
]

export const fieldToValidateDerivadosTeo = [
    { name: 'n_tipo_instrumento', defaultValue: 'default'},
    { name: 'n_fam_instrumento', defaultValue: 'default'},
    { name: 'n_moneda', defaultValue: 'default'},
    { name: 'n_crv_descuento', defaultValue: 'default'},
    { name: 'n_valor_nominal', defaultValue: 'default'},
    { name: 'n_composicion_yield', defaultValue: 'default'},
    { name: 'n_tipo_tasa', defaultValue: 'default'},
    { name: 'n_convencion_dias', defaultValue: 'default'},
    { name: 'n_calendario', defaultValue: 'default'},
    { name: 'n_tasa_cupon', defaultValue: 'default'}
]

export const fieldToValidateDefDerivados = [
    { name: 'n_moneda', defaultValue: 'default'}
]
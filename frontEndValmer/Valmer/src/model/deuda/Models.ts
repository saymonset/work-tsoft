import {FvDeudaGobInstrumentos} from "./gubernamental";
import {CalPrecios, CalTasasInt, FvDeudaCorpInstrumentos, IsFieldModifiedFvDdCorpIns} from "./corpBanc";
import React, {ChangeEvent} from "react";
import { FvInterInstrumentos, IsFieldModifiedFvInterIns, ResponseConsultaDataInter } from "../internacional";
import {ActionCreatorWithPayload} from "@reduxjs/toolkit";
import {Dispatch} from "redux";

export type SectionType = 'fitch' | 'sp' | 'moody' | 'hr' | 'verum' | 'dbrs' | 'best';
export type InstrumentosType = FvDeudaGobInstrumentos | FvDeudaCorpInstrumentos | FvInterInstrumentos;
export interface ReglasAltaCorp {
    [key: string]: string | number | null;
}

export interface FrecuenciaCupon {
    [key: string]: string;
}

export interface BodyReglasAltaCorp {
    reglas_alta?: ReglasAltaCorp;
    FRECUENCIA_CUPON?: FrecuenciaCupon;
}

export interface ResponseReglasAltasCorp {
    status: number;
    body: BodyReglasAltaCorp[];
}

export interface ResponseReglasAlta {
    status: number;
    body: BodyReglasAlta;
}

export interface BodyReglasAlta {
    reglas: ReglasAlta[];
    cat_periodo_cupon: Catalogo;
}

export interface ReglasAlta {
    property: string;
    data: Record<string, string>;
}

export interface UpdateFlujosParams {
    triggerUpdate: boolean;
    setLoading: React.Dispatch<React.SetStateAction<boolean>>;
    txtAreaCargaFlujos: string;
    setIsModalOpen: React.Dispatch<React.SetStateAction<boolean>>;
    setTxtAreaCargaFlujos: React.Dispatch<React.SetStateAction<string>>;
    setTriggerUpdate: React.Dispatch<React.SetStateAction<boolean>>;
    url: string,
    emisora: string,
    serie: string,
    tv: string,
    method: ActionCreatorWithPayload<any>,
    dispatch: Dispatch
}

export interface HandleActualizaParams {
    tv: string;
    emisora: string;
    serie: string;
    isDiaSig: boolean;
    setTitleModal: React.Dispatch<React.SetStateAction<string>>;
    setIsModalOpen: React.Dispatch<React.SetStateAction<boolean>>;
}

export interface UseCustomHookProps {
    consultaData: ResponseConsultaData | ResponseConsultaDataCorp;
}
export interface ResponseConsultaData {
    body: FvDeudaGobInstrumentos;
}
export interface ResponseConsultaDataCorp {
    body: FvDeudaCorpInstrumentos;
}
export interface ResponseDataCatalog {
    status: number;
    body: Catalogo;
}
export interface ResponseDataCorp {
    status: number;
    body: {
        catalogos: Catalogo[];
    };
}

export interface Catalogo {
    catalogo: string;
    registros: Record<string, string>;
}

export interface CalifEmisoras {
    status: number;
    body: {
        fitch: Catalogo[];
        "Standard & Poors": Catalogo[];
        Moodys: Catalogo[];
        Hr: Catalogo[];
        Verum: Catalogo[];
        DBRS: Catalogo[];
        BEST: Catalogo[];
    };
}

export interface ConsultaData {
    body: {
        [key: string]: string | any[];
    };
}

export interface RespQueryValuation {
    status: number;
    body: BodyValuation[];
}
export interface BodyValuation {
    property: string;
    data: {
        PSMD: string;
        PLMD: string;
        PS24: string;
        PL24: string;
        DURACION: string;
        CONVEXIDAD: string;
        REND: string;
        SOBRETASA: string;
    };
}
export interface SelectFileProps {
    handleFileChange: (event: ChangeEvent<HTMLInputElement>, section: string) => void;
    fileName: string | null;
    section: string;
}
export interface CalifProps {
    loading: boolean
    consultaData: ResponseConsultaData | ResponseConsultaDataCorp
    loadingSubmitCalif: boolean
    catalog: Catalogo[]
    handleChange: <T extends HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>(e: React.ChangeEvent<T>) => void
    handleSubmit: (e: React.FormEvent<HTMLFormElement>) => Promise<void>
    handleFileChange: (event: ChangeEvent<HTMLInputElement>, section: string) => void
}
export interface ModalCalProps {
    instrument: string;
    isModalOpenCalTasas: boolean;
    handleModalCloseTasas: () => void;
    isModalOpenCalPrecio: boolean;
    handleModalClosePrecio: () => void;
    handleUpdateCalculator: (url: string, messageError: string) => void;
    loadingSaveCalculator: boolean;
    precios: CalPrecios
    tasasInt: CalTasasInt
}
export interface TvEmiSerieProps
{
    isNewFormInst: boolean,
    isNewSerie: boolean
    loadingConsultaData: boolean
    loadingEmisoras : boolean
    loadingSerie : boolean
    selectedTv : string
    selectedEmisora : string
    selectedSerie : string
    requiredTv: boolean
    requiredEmisora: boolean
    requiredSerie: boolean
    tv: string []
    emisora: string []
    serie: string[]
    isFieldModified: IsFieldModifiedFvDdGobIns | IsFieldModifiedFvDdCorpIns | IsFieldModifiedFvInterIns
    consultaData: ResponseConsultaData | ResponseConsultaDataCorp | ResponseConsultaDataInter
    requeridos: any
    inputValueEval: (fieldName: string, formValues: InstrumentosType, consultaData: ConsultaData
        | undefined, isFieldModified: boolean) => string;
    handleChange: <T extends HTMLInputElement | HTMLSelectElement>(e: React.ChangeEvent<T>, setFormValues? :
        React.Dispatch<React.SetStateAction<FvDeudaGobInstrumentos | FvDeudaCorpInstrumentos | FvInterInstrumentos>>) => void;
    handleTvNewForm: (value:string) => void;
    handleClickTv: (e: any, isNewForm?: boolean) => void;
    handleEmisora: (e: any) => void;
    handleSerie: (e: any) => void;
}

export const fieldsToValidateGub = [
    { name: 'n_status', defaultValue: 'default' },
    { name: 'n_composicion_yield', defaultValue: 'default' },
    { name: 'n_tipo_tasa', defaultValue: 'default' },
    { name: 'n_monto_circ', defaultValue: '' },
    { name: 'n_monto_emi', defaultValue: '' },
    { name: 'n_titulos_circulacion', defaultValue: '' },
    { name: 'd_fecha_vto', defaultValue: '' },
    { name: 'n_plazo', defaultValue: '' },
    { name: 'd_fecha_emision', defaultValue: '' },
    { name: 'n_convencion_dias', defaultValue: 'default' },
    { name: 'n_valor_nominal', defaultValue: '' },
    { name: 'n_agente_colocador', defaultValue: 'default' },
    { name: 'n_representante_comun', defaultValue: 'default' },
    { name: 'n_crv_descuento', defaultValue: 'default' },
    { name: 'n_emisor', defaultValue: 'default' },
    { name: 'n_pais_riesgo', defaultValue: 'default' },
    { name: 'n_moneda', defaultValue: 'default' },
    { name: 'n_pais', defaultValue: 'default' },
    { name: 'n_tipo_mercado', defaultValue: '' },
    { name: 'n_familia_instrumento', defaultValue: 'default' },
    { name: 'n_tipo_instrumento', defaultValue: 'default' }
];

export const fieldsToValidateCorp = [
    { name: 'n_tipo_instrumento', defaultValue: 'default' },
    { name: 'n_familia_instrumento', defaultValue: 'default' },
    { name: 'n_tipo_mercado', defaultValue: 'default' },
    { name: 'n_pais', defaultValue: 'default' },
    { name: 'n_moneda', defaultValue: 'default' },
    { name: 'n_pais_riesgo', defaultValue: 'default' },
    { name: 'n_emisor', defaultValue: 'default' },
    { name: 'd_fecha_emision', defaultValue: '' },
    { name: 'n_plazo', defaultValue: '' },
    { name: 'd_fecha_vto', defaultValue: '' },
    { name: 'd_fecha_vto_estimada', defaultValue: '' },
    { name: 'n_valor_nominal', defaultValue: '' },
    { name: 'n_prima', defaultValue: '' },
    { name: 'b_oferta_publica', defaultValue: '0' },
    { name: 's_inscrito_bolsa', defaultValue: 'default' },
    { name: 'n_crv_descuento', defaultValue: 'default' },
    { name: 'n_tasa_referencia', defaultValue: 'default' },
    { name: 'n_tipo_tasa', defaultValue: 'default' },
    { name: 'n_convencion_dias', defaultValue: 'default' },
    { name: 'n_composicion_yield', defaultValue: 'default' },
    { name: 'n_tipo_deuda', defaultValue: 'default' },
    { name: 'n_calendario', defaultValue: 'default' },
    { name: 'n_tipo_calculo', defaultValue: 'default' },
    { name: 's_tv_ref_1', defaultValue: 'default' },
    { name: 's_emisora_ref_1', defaultValue: 'default' },
    { name: 's_serie_ref_1', defaultValue: 'default' },
    { name: 's_tv_ref_2', defaultValue: 'default' },
    { name: 's_emisora_ref_2', defaultValue: 'default' },
    { name: 's_serie_ref_2', defaultValue: 'default' },
    { name: 'n_titulos_iniciales', defaultValue: '' },
    { name: 'n_titulos_circulacion', defaultValue: '' },
    { name: 'n_monto_emitido', defaultValue: '' },
    { name: 'n_monto_circulacion', defaultValue: '' },
    { name: 'n_frecuencia_cupon', defaultValue: 'default' },
    { name: 'n_tipo_promedio', defaultValue: 'default' },
    { name: 'n_calc_dias', defaultValue: 'default' },
    { name: 'n_dias_deter_tasa', defaultValue: '' },
    { name: 'n_tasa_cupon', defaultValue: '' },
    { name: 'n_tasa_cupon_24h', defaultValue: '' },
    { name: 'n_valor_nominal_act', defaultValue: '' },
    { name: 'd_fecha_ini_cupon', defaultValue: '' },
    { name: 'd_fecha_fin_cupon', defaultValue: '' },
    { name: 'd_fecha_subasta', defaultValue: '' },
    { name: 'n_num_cupones', defaultValue: '' },
    { name: 'n_periodo_cupon', defaultValue: '' },
    { name: 'n_periodo_cupon_v', defaultValue: '' }
];

export interface IsFieldModifiedFvDdGobIns  {
    [key: string]: boolean;
    s_bolsa_emi: boolean;
    s_pais: boolean;
    n_reset_rule: boolean;
    d_fecha_vto_estimada: boolean;
    s_pdf_hr: boolean;
    n_theo_model: boolean;
    d_fec_dbrs: boolean;
    d_fecha_ini_cupon: boolean;
    n_tipo_promedio: boolean;
    n_calc_dias: boolean;
    n_dias_deter_tasa: boolean;
    n_frecuencia_cupon: boolean;
    n_tasa_cupon_24h: boolean;
    n_valor_nominal_act: boolean;
    n_tasa_cupon: boolean;
    d_fecha_primer_cupon: boolean;
    s_moneda: boolean;
    n_precio_mercado: boolean;
    n_calif_hr_g: boolean;
    d_fec_venc: boolean;
    n_tasa_referencia: boolean;
    s_evento_hr: boolean;
    s_emisor: boolean;
    s_pdf_verum: boolean;
    s_rep_comun: boolean;
    s_coupgenmthd: boolean;
    s_emisora: boolean;
    s_serie: boolean;
    s_credit_spread_curve: boolean;
    n_valor_nominal: boolean;
    b_oferta_publica: boolean;
    s_inscrito_bolsa: boolean;
    n_fixed_coupon_date: boolean;
    n_familia_esg: boolean;
    n_calif_sp_n: boolean;
    s_evento_best: boolean;
    s_tv: boolean;
    n_tasa_24h: boolean;
    n_calif_dbrs_g: boolean;
    n_periodo_cupon_v: boolean;
    n_clasificacion_sectorial: boolean;
    n_intereses_md: boolean;
    b_couponpro: boolean;
    n_composicion_yield: boolean;
    n_tipo_deuda: boolean;
    n_monto_circulacion: boolean;
    n_calendario: boolean;
    n_tipo_calculo: boolean;
    n_titulos_iniciales: boolean;
    s_tv_ref_1: boolean;
    s_emisora_ref_1: boolean;
    s_serie_ref_1: boolean;
    s_tv_ref_2: boolean;
    s_emisora_ref_2: boolean;
    s_serie_ref_2: boolean;
    n_monto_emitido: boolean;
    n_calif_moody_g: boolean;
    n_calif_fitch_n: boolean;
    n_calif_verum_g: boolean;
    s_pdf_moody: boolean;
    s_pdf_fitch: boolean;
    n_status: boolean;
    n_tipo_mercado: boolean;
    n_calif_dbrs_n: boolean;
    n_precio_mercado_24h: boolean;
    d_lastregcou: boolean;
    n_last_reset_rate_24h: boolean;
    d_fec_sp: boolean;
    h_flujos: boolean;
    n_rendimiento: boolean;
    s_isin: boolean;
    d_fecha_emision: boolean;
    s_pdf_best: boolean;
    n_plazo: boolean;
    n_intereses_24h: boolean;
    n_agente_colocador: boolean;
    n_calif_hr_n: boolean;
    d_fecha_subasta: boolean;
    d_fec_moody: boolean;
    d_fec_ins: boolean;
    s_cusip: boolean;
    n_pais_riesgo: boolean;
    s_evento_verum: boolean;
    n_nomb_tasa: boolean;
    d_fecha_vto: boolean;
    n_last_reset_rate: boolean;
    s_com_val: boolean;
    n_calif_best_g: boolean;
    n_representante_comun: boolean;
    n_busdayrule: boolean;
    s_agen_col: boolean;
    n_convencion_dias: boolean;
    s_oddlastcoupon: boolean;
    d_fec_best: boolean;
    b_rnv: boolean;
    d_fecha_fin_cupon: boolean;
    s_tipo_papel: boolean;
    n_tipo_tasa: boolean;
    s_pdf_sp: boolean;
    n_calif_sp_g: boolean;
    s_evento_fitch: boolean;
    n_tasa_mercado: boolean;
    d_fec_fitch: boolean;
    n_calif_best_n: boolean;
    n_periodo_cupon: boolean;
    s_evento_dbrs: boolean;
    s_evento_moody: boolean;
    s_evento_sp: boolean;
    n_sobretasa: boolean;
    n_num_cupones: boolean;
    b_not_equal: boolean;
    n_tipo_instrumento: boolean;
    n_prima: boolean;
    n_tasa: boolean;
    d_fec_hr: boolean;
    n_titulos_circulacion: boolean;
    s_pdf_dbrs: boolean;
    s_oddfirstcoupon: boolean;
    n_curve_index: boolean;
    n_tasa_48h: boolean;
    n_emisor: boolean;
    n_forma_cotizacion: boolean;
    n_monto_circ: boolean;
    n_calif_verum_n: boolean;
    n_moneda: boolean;
    b_esg: boolean;
    n_calif_fitch_g: boolean;
    b_runscfphase: boolean;
    n_market_model: boolean;
    n_calif_moody_n: boolean;
    b_proteccion_inflacionaria: boolean;
    d_fec_verum: boolean;
    n_crv_descuento: boolean;
    n_monto_emi: boolean;
    n_familia_instrumento: boolean;
    n_pais: boolean;
    s_sedo: boolean;
}



export interface CalProps {
    isModalOpen: boolean
    instrument: string
    selectedTv: string
    selectedEmisora: string
    selectedSerie: string
    handleModalClose: () => void
}
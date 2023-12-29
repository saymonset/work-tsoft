export interface DividendosData {
    Fecha: string;
    Hora: string;
    Instrumento: string;
    "Fecha Reestructura": string;
    Operacion: string;
    Valor: string;
}

export interface RespAccInstData {
    status: number;
    body: BodyAccInstData;
}

export interface BodyAccInstData {
    acciones: Acciones;
    accionesAdd: AccionesAdd;
}

export interface Acciones {
    [key: string]: string
    b_tipo_crisis_shock: string;
    n_crv_desc: string;
    n_equ_sec: string;
    n_tipo_rv: string;
    n_mat_mc: string;
    n_tipo_instrumento: string;
    s_ric: string;
    n_emisor: string;
    n_tipo_mercado: string;
    n_pais: string;
    s_dividendo: string;
    b_valida_bbva: string;
    s_cusip: string;
    n_crv_ref: string;
    b_precio_cierre: string;
    n_mkt_model: string;
    s_ticker: string;
    n_theo_model: string;
    n_status: string;
    n_fam_instrumento: string;
    n_precio: string;
    n_moneda: string;
    b_rnv: string;
    b_sic: string;
    n_pais_o: string;
    s_sedol: string;
    s_isin: string;
    s_instrumento: string;
    n_carac: string;
    s_tv: string;
    s_emisora: string;
    s_serie: string;
    s_tv_vin: string;
    s_emisora_vin: string;
    s_serie_vin: string;
    s_tv_adr: string;
    s_emisora_adr: string;
    s_serie_adr: string;
    n_proporcion_adr: string;
    d_susp_fecha: string;
    n_fijo_precio: string;
    d_der_corp_fecha: string;
    n_der_corp_monto: string;
    n_info: string;
    s_user: string;
    s_elimina_precalc: string;
}


export interface AccionesAdd {
    s_tv: string;
    s_emisora: string;
    s_serie: string;
    n_vol_ask: string;
    n_precio_ask: string;
    n_volumen: string;
    n_hu: string;
    n_sector: string;
    n_acciones_circulacion: string;
    n_acc_inscritas: string;
    n_movimientos: string
    n_tipo_dvd: string;
    d_fecha_de_pago: string;
    n_precio_bid: string;
    n_factor_de_ajuste: string;
    n_pais_mdo: string;
    n_ramo: string;
    d_fecha_anuncio: string;
    n_foco_geo: string;
    n_cambios_puntos_1d: string;
    n_tipo_pago: string;
    n_mdo_cto: string;
    n_categoria: string;
    n_cambios_puntos_1a: string;
    n_tipo_derecho: string;
    n_proporcion: string;
    n_acciones_nuevas: string;
    d_fecha_de_aplicaion: string;
    n_tipo_fondo: string;
    n_dividendo: string;
    s_bursatilidad: string;
    n_vol_bid: string;
    n_precio_mercado: string;
    n_acciones_anteriores: string;
    d_fecha_aplicacion: string;
    n_mdo_lgo: string;
    n_subramo_ing: string
    n_emisor_bacc: string;
    n_cambio_procentual_1a: string;
    d_fecha_de_anuncio: string;
    d_fecha_de_liquidacion: string;
    n_tipo_instr_bacc: string;
    n_valor_libros: string;
    n_cambio_procentual_1d: string;
    n_subsector: string;
    n_subramo: string;
    d_fecha_uh: string;
    d_fecha_cierre: string;
    n_frec_dvd: string;
    n_actividad: string;
    n_moviemientos: string;
    n_indice_principal: string;
}

export const initAcciones: Acciones = {
    b_tipo_crisis_shock: "",
    n_crv_desc: "",
    n_equ_sec: "",
    n_tipo_rv: "",
    n_mat_mc: "",
    n_tipo_instrumento: "",
    s_ric: "",
    n_emisor: "",
    n_tipo_mercado: "",
    n_pais: "",
    s_dividendo: "",
    b_valida_bbva: "",
    s_cusip: "",
    n_crv_ref: "",
    b_precio_cierre: "",
    n_mkt_model: "",
    s_ticker: "",
    n_theo_model: "",
    n_status: "",
    n_fam_instrumento: "",
    n_precio: "",
    n_moneda: "",
    b_rnv: "",
    b_sic: "",
    n_pais_o: "",
    s_sedol: "",
    s_isin: "",
    s_instrumento: "",
    n_carac: "",
    s_tv: "",
    s_emisora: "",
    s_serie: "",
    s_tv_vin: "",
    s_emisora_vin: "",
    s_serie_vin: "",
    s_tv_adr: "",
    s_emisora_adr: "",
    s_serie_adr: "",
    n_proporcion_adr: "",
    d_susp_fecha: "",
    n_fijo_precio: "",
    d_der_corp_fecha: "",
    n_der_corp_monto: "",
    n_info: "",
    s_user: "",
    s_elimina_precalc: ""
};

export const initAccionesAdd: AccionesAdd = {
    s_tv: '',
    s_emisora: '',
    s_serie: '',
    n_vol_ask: '',
    n_precio_ask: '',
    n_volumen: '',
    n_hu: '',
    n_sector: '',
    n_acciones_circulacion: '',
    n_acc_inscritas: '',
    n_movimientos: '',
    n_tipo_dvd: '',
    d_fecha_de_pago: '',
    n_precio_bid: '',
    n_factor_de_ajuste: '',
    n_pais_mdo: '',
    n_ramo: '',
    d_fecha_anuncio: '',
    n_foco_geo: '',
    n_cambios_puntos_1d: '',
    n_tipo_pago: '',
    n_mdo_cto: '',
    n_categoria: '',
    n_cambios_puntos_1a: '',
    n_tipo_derecho: '',
    n_proporcion: '',
    n_acciones_nuevas: '',
    d_fecha_de_aplicaion: '',
    n_tipo_fondo: '',
    n_dividendo: '',
    s_bursatilidad: '',
    n_vol_bid: '',
    n_precio_mercado: '',
    n_acciones_anteriores: '',
    d_fecha_aplicacion: '',
    n_mdo_lgo: '',
    n_subramo_ing: '',
    n_emisor_bacc: '',
    n_cambio_procentual_1a: '',
    d_fecha_de_anuncio: '',
    d_fecha_de_liquidacion: '',
    n_tipo_instr_bacc: '',
    n_valor_libros: '',
    n_cambio_procentual_1d: '',
    n_subsector: '',
    n_subramo: '',
    d_fecha_uh: '',
    d_fecha_cierre: '',
    n_frec_dvd: '',
    n_actividad: '',
    n_moviemientos: '',
    n_indice_principal: ''
};

export interface RespDownloadCsv {
    status: number;
    message: string;
    body: BodyDownloadCsv;
}

export interface BodyDownloadCsv {
    descargaLog: DescargaLogPrecalNac;
    log: [string];
}

export interface DescargaLogPrecalNac {
    nombre: string;
    extension: string;
    contenido: string;
    contentType: string;
    nombreCompleto: string
}

export interface IsFieldRequiredAccInst {
    [key: string]: boolean;
    b_tipo_crisis_shock: boolean;
    n_crv_desc: boolean;
    n_equ_sec: boolean;
    n_tipo_rv: boolean;
    n_mat_mc: boolean;
    n_tipo_instrumento: boolean;
    s_ric: boolean;
    n_emisor: boolean;
    n_tipo_mercado: boolean;
    n_pais: boolean;
    s_dividendo: boolean;
    b_valida_bbva: boolean;
    s_cusip: boolean;
    n_crv_ref: boolean;
    b_precio_cierre: boolean;
    n_mkt_model: boolean;
    s_ticker: boolean;
    n_theo_model: boolean;
    n_status: boolean;
    n_fam_instrumento: boolean;
    n_precio: boolean;
    n_moneda: boolean;
    b_rnv: boolean;
    b_sic: boolean;
    n_pais_o: boolean;
    s_sedol: boolean;
    s_isin: boolean;
    s_instrumento: boolean;
    n_carac: boolean;
    s_tv: boolean;
    s_emisora: boolean;
    s_serie: boolean;
    s_tv_vin: boolean;
    s_emisora_vin: boolean;
    s_serie_vin: boolean;
    s_tv_adr: boolean;
    s_emisora_adr: boolean;
    s_serie_adr: boolean;
    n_proporcion_adr: boolean;
    d_susp_fecha: boolean;
    n_fijo_precio: boolean;
    d_der_corp_fecha: boolean;
    n_der_corp_monto: boolean;
    n_info: boolean;
    s_user: boolean;
    s_elimina_precalc: boolean;
}

export const fieldToValidateAccionesInst = [
    { name: 'n_tipo_instrumento', defaultValue: 'default'},
    { name: 'n_tipo_mercado', defaultValue: 'default'},
    { name: 'n_pais', defaultValue: 'default'},
    { name: 'n_fam_instrumento', defaultValue: 'default'},
    { name: 'n_precio', defaultValue: 'default'},
    { name: 'n_moneda', defaultValue: 'default'},
    { name: 's_isin', defaultValue: 'default'},
    { name: 'n_status', defaultValue: 'default'},
    { name: 'n_crv_desc', defaultValue: 'default'},
    { name: 'n_crv_ref', defaultValue: 'default'},
    { name: 's_dividendo', defaultValue: 'default'},
    { name: 'n_tipo_rv', defaultValue: 'default'},
    { name: 'n_equ_sec', defaultValue: 'default'},
    { name: 'n_pais_o', defaultValue: 'default'},
    { name: 'n_theo_model', defaultValue: 'default'},
    { name: 'n_mkt_model', defaultValue: 'default'}
]

type InputOrSelect = HTMLInputElement | HTMLSelectElement | null;
type SelectOrNull = HTMLSelectElement | null;
type InputOrNull = HTMLInputElement | null;

export interface RequeridosAcc {
    [key: string]: React.MutableRefObject<InputOrSelect>;
    s_tv: React.MutableRefObject<InputOrSelect>;
    s_emisora: React.MutableRefObject<InputOrSelect>;
    s_serie: React.MutableRefObject<InputOrSelect>;
    n_tipo_instrumento: React.MutableRefObject<SelectOrNull>;
    n_tipo_mercado: React.MutableRefObject<SelectOrNull>;
    n_pais: React.MutableRefObject<SelectOrNull>;
    n_fam_instrumento: React.MutableRefObject<SelectOrNull>;
    n_precio: React.MutableRefObject<InputOrNull>;
    n_moneda: React.MutableRefObject<SelectOrNull>;
    s_isin: React.MutableRefObject<InputOrNull>;
    n_status: React.MutableRefObject<SelectOrNull>;
    n_crv_ref: React.MutableRefObject<SelectOrNull>;
    n_crv_desc: React.MutableRefObject<SelectOrNull>;
    s_dividendo: React.MutableRefObject<InputOrNull>;
    n_tipo_rv: React.MutableRefObject<SelectOrNull>;
    n_equ_sec: React.MutableRefObject<SelectOrNull>;
    n_pais_o: React.MutableRefObject<SelectOrNull>;
    n_theo_model: React.MutableRefObject<SelectOrNull>;
    n_mkt_model: React.MutableRefObject<SelectOrNull>;
}
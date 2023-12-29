export interface RespRegInvData {
    status: number;
    body: BodyRegInvData;
}

export interface BodyRegInvData {
    CALIF_REG: string;
    B_CAMBIO: string;
    NEXO_PATRI: string;
    CLAS_CONSAR_TF: string;
    VALORES_EXT: string;
    EMISOR_REG: string;
    DIVISAS: string;
    RIESGO_CONC: string;
    COMPON_RV: string;
    PROTEC_INFL: string;
    CALIDAD_C: string;
    ENTIDADES_R: string;
    CLAS_CONSAR: string;
    EMISOR: string;
    CORPO: string;
    ESTRUCT_Deuda: string;
    SUBY: string;
    MCIRC_Deuda: string;
    N_SUBY: string;
    BURSA: string;
    TIPO: string;
    N_CORPO: string;
    TIPO_ESTRUCT_Deuda: string;
    S_FIBRAS: string;
    s_instrumento: string;
    [key: string]: string;
}

export interface ResponseDataRegInv {
    status: number;
    body: {
        catalogos: CatalogoRegInv[];
    };
}

export interface CatalogoRegInv {
    catalogo: string;
    registros?: Record<string, string>;
    listado?: Record<string, string>;
}
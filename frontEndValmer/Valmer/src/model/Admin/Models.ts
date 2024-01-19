import {ColumnEditCat} from "../Catalogos";

export interface ResponseApiEditCauClient {
    status: number;
    body: BodyEditCauClient;
}

export interface BodyEditCauClient {
    catalogo: string;
    campos: string;
    registros: ClienteEditCau[];
    niveles_servicio: NivelesServicioEditCau;
}

export interface GenerarArchivoUserWebProps {
    title: string
    n_institucion?: number
    n_nombre?: number
    url: string
    is_hist: boolean
}


export interface ClienteEditCau {
    n_cliente: string;
    n_empresa: string;
    s_nombre: string;
    s_puesto: string;
    s_telefono: string;
    s_email: string;
    s_usuario: string;
    s_password: string;
    s_area: string;
    n_sector: string;
    s_fax: string;
    n_hits: string;
    d_fecha: string;
    n_status: string;
}

export interface NivelesServicioEditCau {
    n_betas: string;
    n_curvas: string;
    n_nivmer: string;
    n_bench: string;
    n_vecafor: string;
    n_basecorp: string;
    n_escenarios: string;
    n_nivel1: string;
    n_nivel2: string;
    n_vecintafor: string;
    n_portafolio: string;
    n_asp: string;
    n_motorbench: string;
    n_indrot: string;
    n_benchlim: string;
    n_volati: string;
    n_baseeur: string;
    n_nivel4: string;
    n_cau: string;
    n_basecalif: string;
    n_vecfecval: string;
    n_vecpromafor: string;
    n_nivel3: string;
    n_indices: string;
    n_nivel5: string;
    n_notasind: string;
    n_notasest: string;
}

export interface ConsultaDataEditCauClient {
    n_emp: string,
    n_cli: string,
    N_CLIENTE: string;
    N_EMPRESA: string;
    S_NOMBRE: string;
    S_PUESTO: string;
    S_TELEFONO: string;
    S_EMAIL: string;
    S_USUARIO: string;
    S_PASSWORD: string;
    S_AREA: string;
    N_SECTOR: string;
    S_FAX: string;
    N_HITS: string;
    D_FECHA: string;
    N_STATUS: string;
    n_betas: string;
    n_curvas: string;
    n_nivmer: string;
    n_bench: string;
    n_vecafor: string;
    n_basecorp: string;
    n_escenarios: string;
    n_nivel1: string;
    n_nivel2: string;
    n_vecintafor: string;
    n_portafolio: string;
    n_asp: string;
    n_motorbench: string;
    n_indrot: string;
    n_benchlim: string;
    n_volati: string;
    n_baseeur: string;
    n_nivel4: string;
    n_cau: string;
    n_basecalif: string;
    n_vecfecval: string;
    n_vecpromafor: string;
    n_nivel3: string;
    n_indices: string;
    n_nivel5: string;
    n_notasind: string;
    n_notasest: string;
}

export interface ResponseTableMail {
    status: number;
    body: {
        registros: RegistrosMail[];
        total_registros: number;
        total_paginas: number;
    };
}

export interface RegistrosMail {
    n_cliente: number;
    s_nomcorto: string;
    s_nombre: string;
    s_email: string;
}

export interface InfoClienteMail {
    length: number;
    body: Array<{
        n_cliente: number;
        n_empresa: string;
        s_email: string;
        s_grupos: string;
        s_nombre: string;
    }>;
}

export interface EditCatalogHookProps {
    nameCatalog: string,
    columns: ColumnEditCat[]
}

export type StateShowEdit = {
    showTable: boolean;
    nameCatalog: string;
    columns: ColumnEditCat[];
    edit: boolean | undefined;
};

export type MemoizedTableProps = {
    registro: any,
    columns: ColumnEditCat[],
    handleRowClick: (registro: { id: string; [key: string]: string }) => void,
    edit: boolean | undefined
};


export interface ResponseCat {
    status: number;
    body: Record<string, string>;
}

export interface CatSector {
    id_sector: number;
    nombre: string;
}

export interface InfoUser {
    id_usuario: number;
    n_tipo_usuario: number;
    s_tipo_usuario: string;
    s_descripcion_tipo_user: string;
    email: string;
    contrasenia_vigente: string;
    contrasenia_fecha_cambio: string;
    token: string;
    cuenta_activada: string;
    estatus: string;
    activo: string;
    fecha_alta: string;
    n_sector: string;
    "n institucion": string;
}

export interface UriInfo {
    archivo: string;
    id_proceso: string;
    id_proceso_padre: string;
}

export interface DataHistoricoTrial {
    nombre: string;
    jerarquia: string;
    d_fecha_inicio: string;
    d_fecha_fin: string;
    d_fecha_contrato: string;
    n_dias: string;
    b_trial: string;
    id_proceso: string;
    id_proceso_padre: string;
    uri: string;
}

export type ParamsEditCauClient = {
    s_nombre_catalogo: string;
    n_cli?: string;
    n_emp?: string;
};

export interface FvCheckbox {
    [key: string]: boolean;
}

export interface FvContratos {
    [key: string]: string;
}

export interface ItemProduct {
    [key: string]: string | ItemProduct;
}
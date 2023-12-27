import {ColumnEditCat, RegistroConstCatAdmin, RegistroEdit} from "../Catalogos";

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

export type StateHookEdit = {
    registroSeleccionado: RegistroEdit | null;
    loadingSave: boolean;
    loadingDelete: boolean;
    loadingNewId: boolean;
    loadingCatalogStatic: boolean;
    loadingNomCorto: boolean;
    loadingCatalog: boolean;
    triggerCatalogs: boolean;
    isNew: boolean,
    catalogs: RegistroConstCatAdmin[];
};

export type ActionHookEdit = | { type: 'UPDATE', payload: Partial<StateHookEdit> };

export interface ResponseCat {
    status: number;
    body: Record<string, string>;
}

export interface ResponseDataSector {
    status: number;
    body: CatSector[];
}

export interface CatSector {
    id_sector: number;
    nombre: string;
}

export interface ResponseDataUser {
    status: number;
    body: InfoUser;
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

export interface ResponseDataUri {
    status: number;
    body: UriInfo;
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

export interface FvCheckbox {
    [key: string]: boolean;
}

export interface FvContratos {
    [key: string]: string;
}

export interface ItemProduct {
    [key: string]: string | ItemProduct;
}
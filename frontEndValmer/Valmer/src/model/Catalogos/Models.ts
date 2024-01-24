import {ConsultaDataEditCauClient} from "../Admin";
import {Catalogo} from "../deuda";

export interface RegistroEdit {
    id: string;
    [key: string]: string;
}

export interface ColumnEditCat {
    name: string;
    columnName?: string;
    field?: string;
    order?: number;
    type: string;
    catalog?: string;
    DisabledFieldTable?: boolean;
    DisabledFieldForm?: boolean;
    isReadOnly ?: boolean;
    isReadOnlyEdit ?: boolean;
    columnIndex?: number;
    isMandatory?: boolean;
}

export interface PropsEdit {
    nameCatalog: string;
    setShowTable: (show: boolean) => void;
    columns: ColumnEditCat[];
    edit?: boolean;
}

export interface EditCatalogProps {
    titleName: string,
    DataCatalog: Array<{text: string, columns: ColumnEditCat[], edit?: boolean}>
}

export interface ResponseConstCatAdmin {
    status: number;
    message: string;
    body: {
        catalogo: string;
        campos: string;
        total_registros: number;
        total_paginas: number;
        registros: RegistroConstCatAdmin[];
    };
}

export interface ResponseCauClientes {
    n_cliente: number;
    s_nomcorto: string;
    s_nombre: string;
    s_usuario: string;
    s_password: string;
}

export interface RegistroConstCatAdmin {
    [key: string]: string;
}

export interface ResponseAdminEnviosMailGrupos {
    status: number;
    body: {
        catalogo: string;
        campos: string;
        total_registros: number;
        total_paginas: number;
        registros: RegistroEnviosMailGrupos[];
    };
}

export interface RegistroEnviosMailGrupos {
    n_grupo: number;
    s_descripcion: string;
}

export type ActionHookEditCauClient = | { type: 'UPDATE', payload: Partial<StateHookEditCauClient> };

export interface StateHookEditCauClient {
    loadingClient: boolean;
    clients: Record<string, string>;
    enterprise: string;
    selectClient: string;
    catalogs: ResponseCauClientes[];
    filteredCatalogs: ResponseCauClientes[];
    enterpriseFilteredCatalogs: ResponseCauClientes[];
    consultaDataClient: ConsultaDataEditCauClient;
    catalogsCau: Catalogo[];
    loadingCatalog: boolean;
    loadingCatalogCau: boolean;
    triggerCatalogs: boolean;
    loadingClientById: boolean;
    loadingCsv: boolean;
    loadingNewId: boolean;
    loadingErase: boolean;
    loadingSave: boolean;
    loadingClientId: number | null;
}

export type ActionHookEdit = | { type: 'UPDATE', payload: Partial<StateHookEdit> };

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
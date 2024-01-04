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
    DisabledFieldForm?: boolean;
    isReadOnly ?: boolean;
    isReadOnlyEdit ?: boolean;
    columnIndex?: number;
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

export interface ResponseCauCLientes {
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
import React from "react";

export type SortableFieldsRevisar1 = 'instrumento' | 'bloomberg' | 'reuters' | 'markit'
    | 'precio_promedio' | 'precio_ayer' | 'variacion' | 'comentario';

export type SortableFieldsRevisar2 = 'isin' | 'instrumento' | 'bloomberg' | 'reuters'
    | 'markit' | 'clientes' | 'precio_promedio' | 'precio_ayer' | 'variacion' | 'comentario';

export type SortableFields = keyof Revisar1Data | keyof Revisar2Data;

export type RevisarData = Revisar1Data | Revisar2Data;

export interface TableWalletProps {
    searchClientWallet: string
    sortedData: DataClient[]
    handleSort: (field: keyof DataClient) => void
    getSortIcon: (field: string) => JSX.Element
    setSearchClient: (e: React.ChangeEvent<HTMLInputElement>) => void
}

export interface Revisar1Data {
    isin: string;
    instrumento: string;
    bloomberg: string;
    reuters: string;
    markit: string;
    precio_promedio: string;
    precio_ayer: string;
    variacion: string;
    comentario: string;
}

export interface stateCheckbox {
    cbx_obtener_carteras: boolean;
    cbx_carga_insumos: boolean;
    cbx_Revisar1: boolean;
    cbx_revisar2: boolean;
    cbx_Act_Precios: boolean;
    cbx_Calcular_Tasas: boolean;
    cbx_Calcula_Precios: boolean;
    cbx_Salidas: boolean
}

export interface Revisar1Response {
    status: number;
    body: Revisar1Data[];
}


export interface ClienteDetails {
    nombre: string;
    precio: string;
    cambiaPrecio: string;
}

export interface Revisar2Response {
    status: number;
    body: Revisar2Data[];
}

export interface Revisar2Data {
    isin: string;
    instrumento: string;
    bloomberg: string;
    reuters: string;
    markit: string;
    clientes: ClienteInfo[];
    precio_promedio: string;
    precio_ayer: string;
    variacion: string;
    comentario: string;
}

export interface ClienteInfo {
    [key: string]: ClienteDetails;
}

export interface ArchivoPreciosCont {
    nombre: string | null;
    extension: string | null;
    contenido: string;
    contentType: string | null;
    nombreCompleto: string;
}


export interface RespCatalogoInt {
    status: number;
    body: Body;
}

export interface Body {
    catalogos: CatalogoIntEurobonos[];
}

export interface CatalogoIntEurobonos {
    catalogo: string;
    registros: {
        [key: string]: string;
    };
}

export interface DataClient {
    fecha: string;
    institucion: string;
    instrumento: string;
    precio: string;
}

export interface RespClientWallet {
    status: number;
    body: DataClient[];
}

export interface ResponseCatalogoEuroCurvas {
    status: number;
    body: {
        catalogo: string;
        campos: string;
        total_registros: number;
        total_paginas: number;
        registros: RegistroEuroCurvas[];
    };
}

export interface RegistroEuroCurvas {
    n_id: number;
    s_instrumento: string;
    s_curva: string;
    n_precio: string;
    n_dxv: string;
    d_vto: string;
    n_insumo: string;
}

export interface SalidaEuro {
    archivo: string;
    fecha: string;
    hora: string;
}

export interface EuroSalidasResponse {
    SALIDAS_EURO: SalidaEuro[];
}

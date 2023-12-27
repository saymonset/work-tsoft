import {Catalogo} from "../../deuda";
import React from "react";

export interface ResponseMantCau {
    status: number;
    body: ElementCau[];
}

export interface ElementCau {
    n_folio: number;
    nombre: string;
    s_servicio: string;
    compania: string;
    s_status: string;
    d_fecha: string;
    d_fecha_cierre: string;
}

export interface ResponseFolioCau {
    status: number;
    body: FolioCau;
}

export interface CauFormsProps {
    loadingSave: boolean
    loadingFolio: boolean
    isEdit: boolean
    catalog: Catalogo[]
    queryFolio: FolioCau
    handleChange: (event: React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>) => void
    handleSave: (e: React.MouseEvent<HTMLButtonElement>) => Promise<void>;
    status: string;
}

export interface CauTableProps {
    status: string
    loadingOpened: boolean
    loadingClosed: boolean
    loadingModifies: boolean
    tableOpened: ElementCau[]
    handleClickFolio: (folio: number) => Promise<void>;
    handleClickFolioCerrados: (folio: number) => Promise<void>;
}

export interface CauHeaderProps {
    catalog: Catalogo[]
    selectedEnterprise: string;
    eraseFilters: () => void;
    handleEnterprise: (e: React.ChangeEvent<HTMLSelectElement>) => Promise<void>;
    handleStatus: (status: string) => Promise<void>;
}

export interface FolioCau {
    n_folio: string;
    n_area: string;
    n_servicio: string;
    n_empresa: string;
    n_status: string;
    s_descripcion: string;
    n_serv_aux: string;
    s_nombre: string;
    s_correo: string;
    s_telefono: string;
    d_fecha_estimada: string;
    s_observaciones: string;
    s_archivo: string;
    s_atendio: string;
}
import { FvInterInstrumentos } from "./instrumentos";
import {CargaArchivoContent} from "../Models";
import {ActionCreatorWithPayload} from "@reduxjs/toolkit";

export interface UseChargeOptions {
    apiUrl: string;
    apiErrorMessage: string;
    data: CargaArchivoContent
    method: ActionCreatorWithPayload<CargaArchivoContent>
    params?: any;
}

export interface UsePersistProps {
    prices: Record<string, string>;
    urlSave: string;
    urlSend: string;
    messageSave: string;
    messageSend: string;
}

export interface UseCustomHookPropsInter {
    consultaData: ResponseConsultaDataInter;
}

export interface ResponseConsultaDataInter {
    body: FvInterInstrumentos;
}

export interface CatalogoInt {
    catalogo: string;
    registros: Record<string, string>;
}

export interface ResponseCalPrecios {
    status: number;
    body: {
        n_precio_rend: number;
        n_precio_mercado_st: number;
        n_precio_mercado_r: number;
    };
}

export interface CalEuroProps {
    isModalOpen: boolean
    instrument: string
    calPrecios: ResponseCalPrecios
    selectedTv: string
    selectedEmisora: string
    selectedSerie: string
    handleModalClose: () => void
}
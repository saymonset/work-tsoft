import React from "react";
import {AnyAction} from "redux";
import { Acciones, RequeridosAcc } from "./Acciones";
import { CalifInstData, CalifProgramas, RefReqInst, RefReqProg } from "./Calificaciones";
import { RefReqLatPanama, formValuesLatPanama } from "./Latam";

export type AccCalifLatam = Acciones | CalifProgramas | CalifInstData | formValuesLatPanama;
export type RefReqAccCalifLatam = RequeridosAcc | RefReqProg | RefReqInst | RefReqLatPanama;
export type InputOrNull = HTMLInputElement | null;
export type SelectOrNull = HTMLSelectElement | null;
export type InputOrSelect = HTMLSelectElement | HTMLInputElement | null;

export interface CargaArchivoContent
{
    contenido: string | undefined
    contentType: string | undefined
    extension: string | undefined
    nombre: string | undefined
    nombreCompleto: string | undefined
}

export interface CargaArchivo
{
    cadena_base_64: string | undefined
    contentType: string | undefined
    extension: string | undefined
    nombre: string | undefined
    nombreCompleto: string | undefined
}

export interface GetEmisorasParams {
    url: string;
    triggerEmisora: boolean;
    selectedTv: string;
    setTriggerEmisora: React.Dispatch<React.SetStateAction<boolean>>;
    setLoadingEmisoras: React.Dispatch<React.SetStateAction<boolean>>;
    setEmisora?: React.Dispatch<React.SetStateAction<string[]>>;
    dispatch?: React.Dispatch<AnyAction>;
    reduxAction?: (data: string[]) => AnyAction;
}

export interface GetSerieParams {
    url: string;
    triggerSerie: boolean;
    selectedTv: string;
    selectedEmisora: string;
    setSerie?: React.Dispatch<React.SetStateAction<string[]>>;
    setTriggerSerie: React.Dispatch<React.SetStateAction<boolean>>;
    setLoadingSerie: React.Dispatch<React.SetStateAction<boolean>>;
    dispatch?: React.Dispatch<AnyAction>;
    reduxAction?: (data: string[]) => AnyAction;
}

export interface GetConsultaDataParams {
    url: string;
    triggerConsultaData: boolean;
    selectedTv: string;
    selectedEmisora: string;
    selectedSerie: string;
    setConsultaData?: React.Dispatch<React.SetStateAction<any>>;
    setTriggerConsultaData: React.Dispatch<React.SetStateAction<boolean>>;
    setLoadingConsultaData: React.Dispatch<React.SetStateAction<boolean>>;
    dispatch?: React.Dispatch<AnyAction>;
    reduxAction?: (data: any) => AnyAction;
}

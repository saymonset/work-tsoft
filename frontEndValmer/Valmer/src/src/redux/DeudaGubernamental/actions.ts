import {
    Catalogo,
    FvDeudaGobInstrumentos,
    IsFieldModifiedFvDdGobIns,
    ResponseConsultaData
} from "../../model";
import {createAction} from "@reduxjs/toolkit";
export const updateFormValuesInst = createAction<FvDeudaGobInstrumentos>('UPDATE_FORM_VALUES_INS');
export const updateConsultaData = createAction<ResponseConsultaData>('UPDATE_CONSULTA_DATA');
export const updateSelectedTv = createAction<string>('UPDATE_SELECTED_TV');
export const updateSelectedEmisora = createAction<string>('UPDATE_SELECTED_EMISORA');
export const updateSelectedSerie = createAction<string>('UPDATE_SELECTED_SERIE');
export const updateTV = createAction<string[]>('UPDATE_TV');
export const updateEmisora = createAction<string[]>('UPDATE_EMISORA');
export const updateSerie = createAction<string[]>('UPDATE_SERIE');
export const updateCatalog = createAction<Catalogo[]>('UPDATE_CATALOG');
export const updateCatalogCalif = createAction<Catalogo[]>('UPDATE_CATALOG_CALIFICACIONES');
export const showCuponesTasas = createAction<boolean>('SHOW_CUPONES_TASAS');
export const updateIsNewFormGub = createAction<boolean>('IS_NEW_FORM_GUB_INST');
export const updateIsNewSerieGub = createAction<boolean>('IS_NEW_SERIE_GUB');
export const updateRequiredTv = createAction<boolean>('REQUIRED_TV');
export const updateRequiredEmisora = createAction<boolean>('REQUIRED_EMISORA');
export const updateRequiredSerie = createAction<boolean>('REQUIRED_SERIE');
export const updateTriggerEraseGub = createAction<boolean>('UPDATE_TRIGGER_ERASE');
export const updateNInfoGuber = createAction<number>('UPDATE_N_INFO_GUBER');
export const updateFieldRequiredGub = createAction<IsFieldModifiedFvDdGobIns>('UPDATE_FIELD_REQUIRED_GUB');
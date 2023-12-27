import { createAction } from "@reduxjs/toolkit";
import {
    Catalogo,
    FvInterInstrumentos,
    IsFieldModifiedFvInterIns,
    ResponseConsultaDataInter,
    RespTableClients
} from "../../../model";

export const updateCatalogInter = createAction<Catalogo[]>('UPDATE_CATALOG_INTER');
export const updateCatalogCalifInter = createAction<Catalogo[]>('UPDATE_CATALOG_CALIF_INTER');
export const updateSelectedTvInter = createAction<string>('UPDATE_SELECTED_TV_INTER');
export const updateSelectedEmisoraInter = createAction<string>('UPDATE_SELECTED_EMISORA_INTER');
export const updateSelectedSerieInter = createAction<string>('UPDATE_SELECTED_SERIE_INTER');
export const updateSelectedEmisorInter = createAction<string>('UPDATE_SELECTED_EMISOR_INTER');
export const updateEmisoraInter = createAction<string[]>('UPDATE_EMISORA_INTER');
export const updateSerieInter = createAction<string[]>('UPDATE_SERIE_INTER');
export const updateIsNewInterForm = createAction<boolean>('UPDATE_IS_NEW_INTER_FORM');
export const updateRequiredTvInter = createAction<boolean>('UPDATE_REQUIRED_TV_INTER');
export const updateRequiredEmisoraInter = createAction<boolean>('UPDATE_REQUIRED_EMISORA_INTER');
export const updateRequiredSerieInter = createAction<boolean>('UPDATE_REQUIRED_SERIE_INTER');
export const updateFormValuesInter = createAction<FvInterInstrumentos>('UPDATE_FORM_VALUES_INTER');
export const updateConsultaDataInter = createAction<ResponseConsultaDataInter>('UPDATE_CONSULTA_DATA_INTER');
export const updateTvInter = createAction<string[]>('UPDATE_TV');
export const updateTriggerEraseInter = createAction<boolean>('UPDATE_TRIGGER_ERASE')
export const updateTableClients = createAction<RespTableClients>('UPDATE_TABLE_CLIENTS')
export const updateFieldRequiredIntern = createAction<IsFieldModifiedFvInterIns>('UPDATE_FIELD_REQUIRED_INTERNACIONAL');
import {createAction} from "@reduxjs/toolkit";
import {CalifProgramas, Catalogo, IsFieldReqCalifProg, RatingAgency} from "../../../model";

export const updateTvCalifPro = createAction<string[]>('UPDATE_TV_CALIF_PRO')
export const updateEmisoraCalifPro = createAction<string[]>('UPDATE_EMISORA_CALIF_PRO')
export const updateSelectedTvCalifPro = createAction<string>('UPDATE_SELECTED_TV_CALIF_PRO')
export const updateTriggerEraseCalifPro = createAction<boolean>('UPDATE_TRIGGER_ERASE_CALIF_PRO')
export const updateCatalogCalifPro = createAction<Catalogo[]>('UPDATE_CATALOG_CALIF_PRO')
export const updateCalifProCarac = createAction<RatingAgency>('UPDATE_CALIF_PRO_CARAC')
export const updateDataCalifProgramas = createAction<CalifProgramas>('UPDATE_DATA_CALIF_PRO_CARAC')
export const updateRequiredCalifProg = createAction<IsFieldReqCalifProg>('UPDATE_REQUIRED_CALIF_PROG')
export const updateRequiredTvCalifProg = createAction<boolean>('UPDATE_REQUIRED_TV_CALIF_PROG')
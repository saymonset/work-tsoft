import { createAction } from "@reduxjs/toolkit";
import { Catalogo, CatalogoCalificadoras, IsFieldReqCalifInst } from "../../../model";

export const updateCatalogCalifInst = createAction<Catalogo[]>('UPDATE_CATALOG_CALIF')
export const updateCalificadorasCatalog = createAction<CatalogoCalificadoras>('UPDATE_CATALOG_CALIFICADORAS')
export const updateRequiredCalifInst = createAction<IsFieldReqCalifInst>('UPDATE_REQUIRED_CALIF_INST')
export const updateRequiredTvCalifInst = createAction<boolean>('UPDATE_REQUIRED_TV_CALIF_INST')
export const updateRequiredEmisoraCalifInst = createAction<boolean>('UPDATE_REQUIRED_EMISORA_CALIF_INST')
export const updateRequiredSerieCalifInst = createAction<boolean>('UPDATE_REQUIRED_SERIE_CALIF_INST')
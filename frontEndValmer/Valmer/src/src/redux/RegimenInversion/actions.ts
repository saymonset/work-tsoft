import {createAction} from "@reduxjs/toolkit";
import { CatalogoRegInv, RespRegInvData } from "../../model/RegimenInversion";

export const updateTvRegInv = createAction<string[]>('UPDATE_TV_REG_INV');
export const updateSelectedEmisoraRegInv = createAction<string>('UPDATE_SELECTED_EMISORA_REG_INV');
export const updateSelectedTvRegInv = createAction<string>('UPDATE_SELECTED_TV_REG_INV');
export const updateSelectedSerieRegInv = createAction<string>('UPDATE_SELECTED_SERIE_REG_INV');
export const updateEmisoraRegInv = createAction<string[]>('UPDATE_EMISORA_REG_INV');
export const updateSerieRegInv = createAction<string[]>('UPDATE_SERIE_REG_INV');
export const updateConsultaDataRegInv = createAction<RespRegInvData>('UPDATE_CONSULTA_DATA_REG_INV');
export const updateCatalogRegInv = createAction<CatalogoRegInv[]>('UPDATE_CATALOG_REG_INV');
export const updateShowCarRvRegInv = createAction<boolean>('UPDATE_SHOW_CAR_RV_REG_INV');
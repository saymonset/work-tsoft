import { createAction } from "@reduxjs/toolkit";
import {Catalogo, IsFieldModifiedFvDerivados, RespDerivados} from "../../model";

export const updateRequiredTvDer = createAction<boolean>('UPDATE_REQUIRED_TV_DER')
export const updateRequiredEmisoraDer = createAction<boolean>('UPDATE_REQUIRED_EMISORA_DER')
export const updateRequiredSerieDer = createAction<boolean>('UPDATE_REQUIRED_SERIE_DER')
export const updateCatalogDer = createAction<Catalogo[]>('UPDATE_CATALOG_DEUDA_DER')
export const updateCatalogDefDer = createAction<Catalogo[]>('UPDATE_CATALOG_DEUDA_DEF_DER')
export const updateCatalogUnderlying = createAction<Catalogo[]>('UPDATE_CATALOG_UNDERLYING')
export const updateCatalogDcsCurveUnd = createAction<Catalogo[]>('UPDATE_CATALOG_DCS_CURVE_UND')
export const updateConsultaDataDer = createAction<RespDerivados>('UPDATE_CONSULTA_DATA_DER')
export const updateFieldRequiredDerivados = createAction<IsFieldModifiedFvDerivados>('UPDATE_FIELD_REQUIRED_DERIVADOS')
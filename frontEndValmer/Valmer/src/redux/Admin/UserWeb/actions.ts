import { createAction } from "@reduxjs/toolkit";
import {Catalogo, ResponseCat, CatSector, InfoUser, UriInfo} from "../../../model";

export const updateCatalogoInst = createAction<Catalogo[]>("UPDATE_CATALOGO_INST")
export const updateCatNom = createAction<ResponseCat>("UPDATE_CAT_NOM")
export const updateCatTipoUser = createAction<ResponseCat>("UPDATE_CAT_TIPO_USER")
export const updateCatUri = createAction<ResponseCat>("UPDATE_CAT_URI")
export const updateCatSector = createAction<CatSector[]>("UPDATE_SECTOR")
export const updateInfoUser = createAction<InfoUser>("UPDATE_INFO_USER")
export const updateUriInfo = createAction<UriInfo>("UPDATE_URI_INFO")
import { createAction } from "@reduxjs/toolkit";
import { Catalogo, CatalogoCalificadoras } from "../../../model";

export const updateCatalogCalifInst = createAction<Catalogo[]>('UPDATE_CATALOG_CALIF')
export const updateCalificadorasCatalog = createAction<CatalogoCalificadoras>('UPDATE_CATALOG_CALIFICADORAS')
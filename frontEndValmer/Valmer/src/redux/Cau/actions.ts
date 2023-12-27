import { createAction } from "@reduxjs/toolkit";
import { Catalogo } from "../../model";

export const updateCatalogCau = createAction<Catalogo[]>('UPDATE_CATALOG_CAU')

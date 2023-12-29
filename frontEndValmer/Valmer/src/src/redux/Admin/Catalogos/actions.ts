import { createAction } from "@reduxjs/toolkit";
import {Catalogo} from "../../../model";

export const updateCatalogStatic = createAction<Catalogo[]>('UPDATE_CATALOG_STATIC')

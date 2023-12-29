import { combineReducers, configureStore, createReducer } from "@reduxjs/toolkit";
import { Catalogo, CatalogoCalificadoras } from "../../../model";
import { updateCalificadorasCatalog, updateCatalogCalifInst } from "./actions";


const catalogoCalifReducer = createReducer<Catalogo[]>(
    [],
    (builder) => {
        builder.addCase(updateCatalogCalifInst, (_state, action) => {
            return action.payload;
        });
    }
);

const catalogoCalificadorasReducer = createReducer<CatalogoCalificadoras>(
    {} as CatalogoCalificadoras,
    (builder) => {
        builder.addCase(updateCalificadorasCatalog, (_state, action) => {
            return action.payload;
        });
    }
);

const rootReducer = combineReducers({
    catalogCalif: catalogoCalifReducer,
    catalogCalificadoras: catalogoCalificadorasReducer
});

export const storeCalifInst = configureStore({
    reducer: rootReducer,
})
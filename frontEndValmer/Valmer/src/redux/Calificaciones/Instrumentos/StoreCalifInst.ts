import { combineReducers, configureStore, createReducer } from "@reduxjs/toolkit";
import { Catalogo, CatalogoCalificadoras, IsFieldReqCalifInst } from "../../../model";
import { 
    updateCalificadorasCatalog, 
    updateCatalogCalifInst, 
    updateRequiredCalifInst, 
    updateRequiredEmisoraCalifInst, 
    updateRequiredSerieCalifInst, 
    updateRequiredTvCalifInst } from "./actions";


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

const isFiledReqCalifInstReducer = createReducer<IsFieldReqCalifInst>(
    {} as IsFieldReqCalifInst,
    (builder) => {
        builder.addCase(updateRequiredCalifInst, (_state, action) => {
            return action.payload;
        });
    }
);

const requiredTvCalifInstReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateRequiredTvCalifInst, (_state, action) => {
            return action.payload;
        });
    }
);

const requiredEmisoraCalifInstReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateRequiredEmisoraCalifInst, (_state, action) => {
            return action.payload;
        });
    }
);

const requiredSerieCalifInst = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateRequiredSerieCalifInst, (_state, action) => {
            return action.payload;
        });
    }
);

const rootReducer = combineReducers({
    catalogCalif: catalogoCalifReducer,
    catalogCalificadoras: catalogoCalificadorasReducer,
    isFieldReqCalifInst: isFiledReqCalifInstReducer,
    requiredTvCalifInst: requiredTvCalifInstReducer,
    requiredEmisoraCalifInst: requiredEmisoraCalifInstReducer,
    requiredSerieCalifInst: requiredSerieCalifInst,
});

export const storeCalifInst = configureStore({
    reducer: rootReducer,
})
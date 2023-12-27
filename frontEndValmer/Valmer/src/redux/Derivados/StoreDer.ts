import { combineReducers, configureStore, createReducer } from "@reduxjs/toolkit";
import {
    updateCatalogDer,
    updateCatalogDefDer,
    updateCatalogUnderlying,
    updateCatalogDcsCurveUnd,
    updateConsultaDataDer,
    updateFieldRequiredDerivados,
    updateRequiredTvDer,
    updateRequiredEmisoraDer,
    updateRequiredSerieDer
} from "./actions"
import {Catalogo, IsFieldModifiedFvDerivados, RespDerivados} from "../../model";

const requiredTvDerReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateRequiredTvDer, (_state, action) => {
            return action.payload;
        });
    }
);

const requiredEmisoraDerReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateRequiredEmisoraDer, (_state, action) => {
            return action.payload;
        });
    }
);

const requiredSerieDerReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateRequiredSerieDer, (_state, action) => {
            return action.payload;
        });
    }
);

const catalogoDerReducer = createReducer<Catalogo[]>(
    [],
    (builder) => {
        builder.addCase(updateCatalogDer, (_state, action) => {
            return action.payload;
        });
    }
);

const catalogoDefDerReducer = createReducer<Catalogo[]>(
    [],
    (builder) => {
        builder.addCase(updateCatalogDefDer, (_state, action) => {
            return action.payload;
        });
    }
);

const catalogoUmderlyingReducer = createReducer<Catalogo[]>(
    [],
    (builder) => {
        builder.addCase(updateCatalogUnderlying, (_state, action) => {
            return action.payload;
        });
    }
);

const catalogoDcsCurveUndReducer = createReducer<Catalogo[]>(
    [],
    (builder) => {
        builder.addCase(updateCatalogDcsCurveUnd, (_state, action) => {
            return action.payload;
        });
    }
);

const consultaDataDerReducer = createReducer<RespDerivados>(
    {} as RespDerivados,
    (builder) => {
        builder.addCase(updateConsultaDataDer, (_state, action) => {
            return action.payload;
        });
    }
);

const FieldRequiredDerivadosReducer = createReducer<IsFieldModifiedFvDerivados>(
    {} as IsFieldModifiedFvDerivados,
    (builder) => {
        builder.addCase(updateFieldRequiredDerivados, (_state, action) => {
            return action.payload;
        });
    }
);

const rootReducer = combineReducers({
    catalogDeudaDer: catalogoDerReducer,
    catalogDefDer: catalogoDefDerReducer,
    catalogUnderlying:catalogoUmderlyingReducer,
    catalogDcsCurveUnd: catalogoDcsCurveUndReducer,
    consultaDataDer: consultaDataDerReducer,
    fieldRequiredDerivados: FieldRequiredDerivadosReducer,
    requiredTvDer: requiredTvDerReducer,
    requiredEmisoraDer: requiredEmisoraDerReducer,
    requiredSerieDer: requiredSerieDerReducer,
});

export const storeDer = configureStore({
    reducer: rootReducer,
})
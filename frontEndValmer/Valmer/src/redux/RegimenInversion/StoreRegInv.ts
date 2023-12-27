import {combineReducers, configureStore, createReducer} from "@reduxjs/toolkit";
import {
    updateTvRegInv,
    updateSelectedEmisoraRegInv,
    updateSelectedTvRegInv,
    updateSelectedSerieRegInv,
    updateEmisoraRegInv,
    updateSerieRegInv,
    updateConsultaDataRegInv,
    updateCatalogRegInv,
    updateShowCarRvRegInv
} from "./actions";
import { CatalogoRegInv, RespRegInvData } from "../../model/RegimenInversion";

const tvRegInvReducer = createReducer<string[]>(
    [],
    (builder) => {
        builder.addCase(updateTvRegInv, (_state, action) => {
            return action.payload;
        });
    }
);

const selectedEmisoraRegInvReducer = createReducer<string>(
    "",
    (builder) => {
        builder.addCase(updateSelectedEmisoraRegInv, (_state, action) => {
            return action.payload;
        });
    }
);

const selectedTvRegInvReducer = createReducer<string>(
    "",
    (builder) => {
        builder.addCase(updateSelectedTvRegInv, (_state, action) => {
            return action.payload;
        });
    }
);

const selectedSerieRegInvReducer = createReducer<string>(
    "",
    (builder) => {
        builder.addCase(updateSelectedSerieRegInv, (_state, action) => {
            return action.payload;
        });
    }
);

const emisorasRegInvReducer = createReducer<string[]>(
    [],
    (builder) => {
        builder.addCase(updateEmisoraRegInv, (_state, action) => {
            return action.payload;
        });
    }
);

const serieRegInvReducer = createReducer<string[]>(
    [],
    (builder) => {
        builder.addCase(updateSerieRegInv, (_state, action) => {
            return action.payload;
        });
    }
);

const consultaDataRegInvReducer = createReducer<RespRegInvData>(
    {} as RespRegInvData,
    (builder) => {
        builder.addCase(updateConsultaDataRegInv, (_state, action) => {
            return action.payload;
        });
    }
);

const catalogRegInvReducer = createReducer<CatalogoRegInv[]>(
    [],
    (builder) => {
        builder.addCase(updateCatalogRegInv, (_state, action) => {
            return action.payload;
        });
    }
);

const showCarRvRegInvReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateShowCarRvRegInv, (_state, action) => {
            return action.payload;
        });
    }
);

const rootReducer = combineReducers({
    tvRegInv: tvRegInvReducer,
    selectedEmisoraRegInv: selectedEmisoraRegInvReducer,
    selectedTvRegInv: selectedTvRegInvReducer,
    selectedSerieRegInv: selectedSerieRegInvReducer,
    emisorasRegInv: emisorasRegInvReducer,
    serieRegInv: serieRegInvReducer,
    consultaDataRegInv: consultaDataRegInvReducer,
    catalog: catalogRegInvReducer,
    showCarRvRegInv: showCarRvRegInvReducer,
});

export const storeRegInv = configureStore({
    reducer: rootReducer,
})
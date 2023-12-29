import { combineReducers } from "redux";
import {CatalogoSubastas, ConsultaSubastasTransformArray} from "../../model";
import {configureStore, createReducer} from "@reduxjs/toolkit";
import {updateCatalogSubastas, updateConsultaSubastas, updateIsConsultaLog, updateIsShowLog} from "./actions";

const catalogSubReducer = createReducer<CatalogoSubastas[]>(
    [],
    (builder) => {
        builder.addCase(updateCatalogSubastas, (_state, action) => {
            return action.payload;
        });
    }
);

const consultaSubReducer = createReducer<ConsultaSubastasTransformArray>(
    [],
    (builder) => {
        builder.addCase(updateConsultaSubastas, (_state, action) => {
            return action.payload;
        });
    }
);

const isShowLogReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateIsShowLog, (_state, action) => {
            return action.payload;
        });
    }
);

const isConsultaLogReducer = createReducer<boolean>(
    false,
    (builder) => {
        builder.addCase(updateIsConsultaLog, (_state, action) => {
            return action.payload;
        });
    }
);

const rootSubReducer = combineReducers({
    catalogSub: catalogSubReducer,
    consultaSub: consultaSubReducer,
    isShowLog: isShowLogReducer,
    isConsultaLog: isConsultaLogReducer
});

export const storeSub = configureStore({
    reducer: rootSubReducer,
});
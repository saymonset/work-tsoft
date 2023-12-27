import {configureStore, createReducer} from "@reduxjs/toolkit";
import {TasasConsultaResponse} from "../../model";
import {updateConsultaTasas} from "./actions";
import {combineReducers} from "redux";

const consultaTasasReducer = createReducer<TasasConsultaResponse>(
    {} as TasasConsultaResponse,
    (builder) => {
        builder.addCase(updateConsultaTasas, (_state, action) => {
            return action.payload;
        });
    }
);

const rootSubReducer = combineReducers({
    consultaTasas: consultaTasasReducer,
});

export const storeTasas = configureStore({
    reducer: rootSubReducer,
});